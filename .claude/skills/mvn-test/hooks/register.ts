import type { Register } from 'claude-code'

const COMMAND = 'test'
const TIMEOUT_MS = 10 * 60 * 1000
const TAIL_LINES = 80

// mvn 汇总行，例如 "[INFO] Tests run: 18, Failures: 0, Errors: 0, Skipped: 0"（不含 "-- in 类名" 的那一行）
const SUMMARY = /Tests run: (\d+), Failures: (\d+), Errors: (\d+), Skipped: (\d+)\s*$/
// 失败用例明细，例如 "[ERROR]   SortTest.sortsEdgeCases:33->assertSorts:25 ..."
const FAILED_TEST = /^\[ERROR\] {3}(\S.*)$/
// 编译错误，例如 "[ERROR] /path/Sort.java:[27,17] ..."
const COMPILE_ERROR = /^\[ERROR\] (.*\.java:\[\d+,\d+\].*)$/

export type Summary = {
  passed: boolean
  run?: number
  failures?: number
  errors?: number
  skipped?: number
  details: string[]
}

export const summarize = (exitCode: number, output: string): Summary => {
  const lines = output.split('\n').map(line => line.trimEnd())
  const summary = lines.map(line => SUMMARY.exec(line)).filter(m => m !== null).pop()
  const details = lines
    .map(line => FAILED_TEST.exec(line)?.[1] ?? COMPILE_ERROR.exec(line)?.[1])
    .filter((line): line is string => line !== undefined)

  return {
    passed: exitCode === 0,
    run: summary ? Number(summary[1]) : undefined,
    failures: summary ? Number(summary[2]) : undefined,
    errors: summary ? Number(summary[3]) : undefined,
    skipped: summary ? Number(summary[4]) : undefined,
    details: [...new Set(details)],
  }
}

export const format = (result: Summary, seconds: number, target: string): string => {
  const counts =
    result.run === undefined
      ? '没有找到测试汇总'
      : `${result.run} 个测试，${result.failures} 失败，${result.errors} 错误，${result.skipped} 跳过`
  const head = `${result.passed ? '✅ 测试通过' : '❌ 测试失败'}${target}：${counts}（${seconds}s）`
  const shown = result.details.slice(0, 20)
  const more = result.details.length > shown.length ? [`… 另有 ${result.details.length - shown.length} 条`] : []

  return [head, ...shown.map(line => `  • ${line}`), ...more].join('\n')
}

export const register: Register = on => {
  on('session.start', async ($, e, next) => {
    await $.command.register({
      name: COMMAND,
      description: '运行 mvn test 并汇总结果（可跟测试类名，如 /test SortTest）',
      argumentHint: '[测试类名]',
    })

    return next(e)
  })

  on('command.run', { command: COMMAND }, async ($, e) => {
    const filter = (e.args ?? '').trim()
    const argv = filter ? ['mvn', '-B', 'test', `-Dtest=${filter}`] : ['mvn', '-B', 'test']
    const target = filter ? `（${filter}）` : ''
    const startedAt = await $.clock.now()
    $.ui.status('mvn test 运行中…')

    try {
      const ran = await $.process.run(argv, { timeoutMs: TIMEOUT_MS })
      const seconds = Math.round(((await $.clock.now()) - startedAt) / 100) / 10
      const output = `${ran.stdout}\n${ran.stderr}`
      const result = summarize(ran.exitCode, output)
      $.ui.status(result.passed ? `tests ✅ ${result.run ?? ''}`.trim() : 'tests ❌')

      if (result.passed) {
        return { text: format(result, seconds, target) }
      }

      // 失败时把输出末尾交给模型，方便直接让 Claude 排查
      const tail = output.trimEnd().split('\n').slice(-TAIL_LINES).join('\n')

      return {
        text: format(result, seconds, target),
        context: [`\`${argv.join(' ')}\` 失败，退出码 ${ran.exitCode}。输出末尾 ${TAIL_LINES} 行：\n${tail}`],
        exitCode: 1,
      }
    } catch (error) {
      $.ui.status('tests ❌')

      return {
        text: `❌ 无法运行 ${argv.join(' ')}：${error instanceof Error ? error.message : String(error)}`,
        exitCode: 1,
      }
    }
  })
}
