### 算法练习

运行单元测试：`mvn test`

### 在 Claude Code 里跑测试

仓库自带一个 Claude Code 插件 `.claude/skills/mvn-test`，提供 `/test` 命令：

- `/test`：运行 `mvn -B test`，汇总通过/失败数量，失败时列出失败的用例
- `/test SortTest`：只跑指定的测试类（`-Dtest=SortTest`）

用 Claude Code 打开本仓库并信任该文件夹后会自动加载；已经打开的会话里执行 `/reload-plugins`。插件自己的测试：`claude plugin test .claude/skills/mvn-test`。
