package aster.lang.zh.transformers;

import aster.core.canonicalizer.StringSegmenter;
import aster.core.canonicalizer.SyntaxTransformer;
import aster.core.lexicon.CanonicalizationConfig;

/**
 * 中文运算符和控制流关键词变换器。
 * <p>
 * 翻译规则：
 * <ul>
 *   <li>{@code 大于等于} → {@code >=}</li>
 *   <li>{@code 小于等于} → {@code <=}</li>
 *   <li>{@code 不等于} → {@code !=}</li>
 *   <li>{@code 等于} → {@code ==}</li>
 *   <li>{@code 则} (行尾) → {@code :}</li>
 *   <li>{@code 设置 X 为 Y} → {@code 令 X 为 Y}</li>
 * </ul>
 * <p>
 * 注意：不再自动添加句号。中文用户使用「。」作为语句终止符，
 * {@link ChinesePunctuationTransformer} 已在更早阶段将其转换为「.」。
 */
public final class ChineseOperatorTransformer implements SyntaxTransformer {

    public static final ChineseOperatorTransformer INSTANCE = new ChineseOperatorTransformer();

    private ChineseOperatorTransformer() {}

    @Override
    public String transform(String source, CanonicalizationConfig config, StringSegmenter segmenter) {
        return segmenter.transformOutsideStrings(source, ChineseOperatorTransformer::translateOperators);
    }

    private static String translateOperators(String s) {
        // 比较运算符（先长后短，避免部分匹配）
        s = s.replace("\u5927\u4E8E\u7B49\u4E8E", " >= ")   // 大于等于
             .replace("\u5C0F\u4E8E\u7B49\u4E8E", " <= ")   // 小于等于
             .replace("\u4E0D\u7B49\u4E8E", " != ")           // 不等于
             .replace("\u7B49\u4E8E", " == ");                 // 等于

        // "则" 在行尾 → ":"
        s = s.replaceAll("\\s+\u5219\\s*$", ":");
        s = s.replaceAll("\\s+\u5219\\s*\\n", ":\n");

        // "设置 X 为 Y" → "令 X 为 Y"（不添加句号，由 ChinesePunctuationTransformer 处理）
        s = s.replaceAll("\u8BBE\u7F6E\\s+([^\u4E3A]+)\\s+\u4E3A\\s+",
                "\u4EE4 $1 \u4E3A ");

        return s;
    }
}
