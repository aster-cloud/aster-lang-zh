package aster.lang.zh;

import aster.core.canonicalizer.SyntaxTransformer;
import aster.core.lexicon.DynamicLexicon;
import aster.core.lexicon.Lexicon;
import aster.core.lexicon.LexiconPlugin;
import aster.lang.zh.transformers.*;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 中文语言包插件 (zh-CN)。
 * <p>
 * 从 JSON 配置加载中文词法表，并通过 SPI 机制将 6 个中文语法变换器注册到对应的注册表。
 * 变换器负责将中文标点、所有格、运算符、函数语法等规范化为英文 IR 形式。
 * <p>
 * SPI 发现流程保证 {@link #getTransformers()} 注册的变换器在 {@link #createLexicon()} 之前完成，
 * 因此 JSON 中引用的变换器名称可以被 {@link aster.core.canonicalizer.TransformerRegistry} 正确解析。
 */
public final class ZhCnPlugin implements LexiconPlugin {

    @Override
    public Lexicon createLexicon() {
        String json = loadResource("lexicons/zh-CN.json");
        return DynamicLexicon.fromJsonString(json);
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

    private String loadResource(String path) {
        try (var is = getClass().getClassLoader().getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalStateException("Resource not found: " + path);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load resource: " + path, e);
        }
    }
}
