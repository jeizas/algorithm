import type { On } from 'claude-code'
import { expect, test } from 'claude-code/testing'

import { format, summarize } from '../hooks/register'

const PASS = [
  '[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.1 s -- in com.jeizas.algorithm.SortTest',
  '[INFO] Tests run: 18, Failures: 0, Errors: 0, Skipped: 0',
  '[INFO] BUILD SUCCESS',
].join('\n')

const FAIL = [
  '[ERROR] Tests run: 9, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 0.1 s <<< FAILURE! -- in com.jeizas.algorithm.SortTest',
  '[ERROR] Failures: ',
  '[ERROR]   SortTest.sortsEdgeCases:33->assertSorts:25 expected: <1> but was: <3>',
  '[ERROR] Tests run: 18, Failures: 1, Errors: 0, Skipped: 0',
  '[INFO] BUILD FAILURE',
].join('\n')

// 测试环境里引擎能力需要自己提供：时钟和状态栏
const engine = (on: On) => {
  on('clock.now', () => ({ value: 0 }))
  on('ui.status', () => ({ value: undefined }))
}

// 模拟你在输入框里敲下 /test <args>
const typed = (args: string) => ({
  command: 'test',
  args,
  origin: { kind: 'composer' } as const,
  presentation: { isFullscreen: false, columns: 80 },
})

const ok = (stdout: string, exitCode = 0) => ({
  value: { exitCode, stdout, stderr: '', isStdoutTruncated: false, isStderrTruncated: false },
})

test('summarize reads the final summary line and failed tests', () => {
  expect(summarize(0, PASS)).toEqual({ passed: true, run: 18, failures: 0, errors: 0, skipped: 0, details: [] })
  const failed = summarize(1, FAIL)
  expect(failed.passed).toBe(false)
  expect(failed.failures).toBe(1)
  expect(failed.details).toEqual(['SortTest.sortsEdgeCases:33->assertSorts:25 expected: <1> but was: <3>'])
  expect(format(failed, 2.5, '')).toContain('❌ 测试失败：18 个测试，1 失败')
})

test('/test runs mvn -B test and reports a pass', async ($, on) => {
  const calls: (readonly string[])[] = []
  engine(on)
  on('process.run', (_, e) => {
    calls.push(e.argv)
    return ok(PASS)
  })

  const result = await $.command.run(typed(''))

  expect(calls).toEqual([['mvn', '-B', 'test']])
  expect(result.text).toContain('✅ 测试通过：18 个测试，0 失败，0 错误，0 跳过')
  expect(result.context).toBeUndefined()
})

test('/test SortTest passes -Dtest and hands the failure tail to the model', async ($, on) => {
  const calls: (readonly string[])[] = []
  engine(on)
  on('process.run', (_, e) => {
    calls.push(e.argv)
    return ok(FAIL, 1)
  })

  const result = await $.command.run(typed('SortTest'))

  expect(calls).toEqual([['mvn', '-B', 'test', '-Dtest=SortTest']])
  expect(result.text).toContain('❌ 测试失败（SortTest）')
  expect(result.text).toContain('SortTest.sortsEdgeCases:33')
  expect(result.context?.[0]).toContain('BUILD FAILURE')
})

test('/test reports when mvn cannot start', async ($, on) => {
  engine(on)
  on('process.run', () => ({ deny: 'mvn: not found' }))

  const result = await $.command.run(typed(''))

  expect(result.text).toContain('无法运行 mvn -B test')
})
