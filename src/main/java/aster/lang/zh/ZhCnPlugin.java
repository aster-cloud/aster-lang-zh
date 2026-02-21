package aster.lang.zh;

import aster.core.canonicalizer.SyntaxTransformer;
import aster.core.lexicon.Lexicon;
import aster.core.lexicon.LexiconPlugin;
import aster.core.lexicon.ZhCnLexicon;
import aster.lang.zh.transformers.*;

import java.util.Map;
import java.util.function.Supplier;

/**
 * 中文语言包插件 (zh-CN)。
 * <p>
 * 通过 SPI 机制将中文词法表和 6 个中文语法变换器注册到对应的注册表。
 * 变换器负责将中文标点、所有格、运算符、函数语法等规范化为英文 IR 形式。
 */
public final class ZhCnPlugin implements LexiconPlugin {

    @Override
    public Lexicon createLexicon() {
        return ZhCnLexicon.INSTANCE;
    }

    @Override
    public Map<String, Supplier<SyntaxTransformer>> getTransformers() {
        return Map.of(
                "chinese-punctuation", () -> ChinesePunctuationTransformer.INSTANCE,
                "chinese-possessive", () -> ChinesePossessiveTransformer.INSTANCE,
                "chinese-operator", () -> ChineseOperatorTransformer.INSTANCE,
                "chinese-function-syntax", () -> ChineseFunctionSyntaxTransformer.INSTANCE,
                "chinese-set-to", () -> ChineseSetToTransformer.INSTANCE,
                "chinese-result-is", () -> ChineseResultIsTransformer.INSTANCE
        );
    }
}
