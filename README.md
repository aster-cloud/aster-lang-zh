# aster-lang-zh -- Aster CNL 中文语言包

## 概述

提供简体中文（zh-CN）词法表、领域词汇和规范化规则。
通过 Java SPI 机制自动注册到 `aster-lang-core` 的词法表、词汇表和变换器注册中心。

## 包含内容

| 类别 | 数量 | 说明 |
|------|------|------|
| 词法表 (lexicon) | 74 关键词 | `lexicons/zh-CN.json` |
| 领域词汇 | 2 | 汽车保险 (`insurance-auto`)、贷款金融 (`finance-loan`) |
| 叠加层 (overlay) | 5 | 类型推断、诊断帮助、诊断消息、输入生成、LSP 界面文本 |

## SPI 插件

`ZhCnPlugin` 同时实现 `LexiconPlugin` 和 `VocabularyPlugin` 两个接口，
并通过 `getTransformers()` 注册 6 个中文专用语法变换器。

## 规范化规则 (Canonicalization)

中文语言包注册了 6 个自定义 `SyntaxTransformer`，将中文语法规范化为英文 IR 形式：

| 变换器 | 注册名 | 功能 |
|--------|--------|------|
| ChinesePunctuationTransformer | `chinese-punctuation` | 全角标点转半角 |
| ChinesePossessiveTransformer | `chinese-possessive` | `的` 转换为 `.` 属性访问 |
| ChineseOperatorTransformer | `chinese-operator` | 中文运算符规范化 |
| ChineseFunctionSyntaxTransformer | `chinese-function-syntax` | 中文函数语法规范化 |
| ChineseSetToTransformer | `chinese-set-to` | `设置 X 为 Y` 规范化 |
| ChineseResultIsTransformer | `chinese-result-is` | `结果是 X` 规范化 |

变换器在 SPI 发现阶段先于词法表加载完成注册，确保 JSON 中引用的变换器名称可被正确解析。

## 构建与测试

```bash
./gradlew build
./gradlew test
```

依赖：`aster-lang-core:0.0.1`，Java 25，JUnit 6，AssertJ 3.27。
测试时需要 `aster-lang-en` 作为运行时依赖（Canonicalizer 翻译目标）。

## 发布

通过 GitHub Packages 发布：

```
cloud.aster-lang:aster-lang-zh:0.0.1
```

## 许可证

Apache License 2.0
