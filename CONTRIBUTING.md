# 贡献 Aster Lang Lexicon

> 帮 Policy-as-Code 支持您的母语。

> **权威英文版**：[aster-lang-en/CONTRIBUTING.md](https://github.com/aster-cloud/aster-lang-en/blob/main/CONTRIBUTING.md)
> 本中文版为便利译本；遇歧义以英文版为准。

---

## 1. 选择贡献路径

| 路径 | 您做什么 | Aster 介入 | 适合 |
|---|---|---|---|
| **官方 lexicon** | Aster team 直接维护 | 100% | en / zh / de（核心市场） |
| **官方背书 lexicon** | Fork template → 翻译 → PR 到 aster-cloud org | Review + 安全审计 + maven 发布 | 主流语种（ja / fr / es / ...） |
| **社区维护 lexicon** | Fork template → 翻译 → 在您自己的 GitHub org 维护 | 仅 docs 收录，不背书 | 长尾语种 / 行业 dialect |

---

## 2. 贡献流程（官方背书路径）

1. 检查 [Wanted Languages 看板](https://aster-lang.dev/community/wanted-languages)，您的语种是否已有 PR 或 paid author。
2. Fork [`aster-lang-template`](https://github.com/aster-cloud/aster-lang-template)，完成 README 的 **15 分钟教程**。
3. Open PR 到 `aster-cloud/aster-lang-<lang>-<region>`（仓库由 Aster team 在收到请求后创建）。
4. CI 校验 lexicon JSON + Aster reviewer 批准 → merge。
5. Aster 发布到 maven central / npm。
6. 您加入 contributor 名录。

---

## 3. 翻译规范

### 3.1 关键字翻译原则

- ✅ **保留行业术语**而非通俗词
  - 例：`Module` 译为 `模块` 而非 `组`
- ✅ keyword 在同一 lexicon 内**唯一**（不同 key 不可映射到同字符串，除非在 `canonicalization.allowedDuplicates` 中列出）
- ❌ keyword 值**不得包含 Aster 保留字符**：`[](),.;:=`
- ❌ keyword 值不可以数字开头
- ✅ 多词 keyword 用空格分隔（不用下划线）

### 3.2 标点

每个 lexicon 必须显式声明三个分隔符：

- `listSeparator`（默认 `,`）
- `enumSeparator`（默认 `,`）
- `statementEnd`（默认 `.`）

### 3.3 Vocabulary（可选，行业术语）

如果您的语种有特定行业 pack（如日语金融 `finance-loan-ja-JP.json`），可一并贡献。Vocabulary IDs 可跨语种同名（如 `finance-loan` 在 en / zh / ja 内容不同）。

---

## 4. Review SLA

| 阶段 | Aster team 承诺 |
|---|---|
| 首次确认 | **24 小时**（label + 指派 reviewer） |
| 首次完整 review | **7 天**（翻译质量 + 技术正确性反馈） |
| 合并或最终决定 | **30 天**（不让 PR 烂在那里） |

---

## 5. Reviewer 资质

- Reviewer **必须是目标语种母语使用者**，或持有专业翻译资质。
- Aster team 至少 1 人作为**副 reviewer** 兜底（SPI 合规 / 构建完整性 / 安全）。
- **两人均批准**方可 merge。

---

## 6. DCO + CLA

- 所有 commit 需 sign-off：`git commit -s`（[Developer Certificate of Origin](https://developercertificate.org/)）
- 重大贡献（≥ 1 完整 lexicon PR）需签 Aster CLA（一次性电子签名）。

---

## 7. 激励 — Aster Language Steward 计划

合并 **≥ 2 个 lexicon** 或维护 1 个 lexicon **≥ 12 个月**：

- 🏷️ docs/contributors 页"Aster Language Steward" 标签
- 💰 **¥3,000 / 年 platform credit**（Steward 限定）
- 📝 公开 [contributor 名录](https://aster-lang.dev/community/contributors)
- 🎙️ 优先参与新 SPI ABI 设计讨论

---

## 8. 维护承诺

**您承诺**：
- 跟随 ABI 升级（v1 → v2 时 6 个月迁移窗口）
- 修复社区报告的翻译 bug
- 季度回应 lexicon 相关 issue

**Aster team 提供**：
- 至少 1 名副 maintainer 兜底
- ABI breaking change 提前 6 个月通告
- 翻译质量 review（非语种细节挑刺）

---

## 9. 行为准则

互相尊重。分歧可以——人身攻击不行。基线参考 [Contributor Covenant v2.1](https://www.contributor-covenant.org/version/2/1/code_of_conduct/)。

---

## License

贡献内容遵循 [Apache License 2.0](LICENSE)。
