package aster.lang.zh.transformers;

import aster.core.canonicalizer.StringSegmenter;
import aster.core.canonicalizer.SyntaxTransformer;
import aster.core.lexicon.CanonicalizationConfig;

import java.util.regex.Pattern;

/**
 * 中文 {@code 令 X 为 Y} → {@code Let X be Y} 重写变换器。
 * <p>
 * 将中文"令...为..."变量绑定语法翻译为英文 Let...be... 语法。
 * 必须作为预翻译变换器运行，在关键词翻译之前消除 {@code 为} 的歧义
 * （{@code 为} 同时映射到 BE 和 WHEN，预翻译可避免 HashMap 覆盖导致的误译）。
 */
public final class ChineseLetBeTransformer implements SyntaxTransformer {

    public static final ChineseLetBeTransformer INSTANCE = new ChineseLetBeTransformer();

    /**
     * 匹配 {@code 令 X 为 Y} 模式。
     * <p>
     * X 可以是中文标识符（字母、数字、下划线），不贪婪匹配到第一个 {@code 为}。
     */
    private static final Pattern CHINESE_LET_BE = Pattern.compile(
            "^(\\s*)\u4EE4\\s+([\\p{L}][\\p{L}0-9_]*)\\s+\u4E3A\\s+",
            Pattern.MULTILINE | Pattern.UNICODE_CHARACTER_CLASS
    );

    private ChineseLetBeTransformer() {}

    @Override
    public String transform(String source, CanonicalizationConfig config, StringSegmenter segmenter) {
        return segmenter.replaceOutsideStrings(source, CHINESE_LET_BE, "$1Let $2 be ");
    }
}
