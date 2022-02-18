package com.kwai.unicodenormalizer;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

@Description(name = "unicode_normalizer", value = "_FUNC_(s) - UDF that returns the normalized unicode string")
public class UnicodeNormalizer extends UDF {
    /*
     * Enclosed Alphanumerics
     *   Parenthesized Latin Small Letter ⒜ -> ⒵ (U+249C -> U+24B5): NFKC/NFKD 均得到三个字符，如 "(a)"。需要先处理再 normalize。
     *   Circled Latin Capital Letter Ⓐ -> Ⓩ (U+24B6 -> U+24CF): NFCK/NFKD 均可正常处理。
     *   Circled Latin Small Letter ⓐ -> ⓩ (U+24D0 -> U+24E9): NFCK/NFKD 均可正常处理。
     * Enclosed Alphanumeric Supplement
     *   Parenthesized Latin Capital Letter 🄐 -> 🄩 (U+1F110 -> U+1F129): NFKC/NFKD 均得到三个字符，如 "(A)"。需要先处理再 normalize。
     *   Squared Latin Capital Letter 🄰 -> 🅉 (U+1F130 -> U+1F149): NFCK/NFKD 均可正常处理。
     *   Negative Circled Latin Capital Letter 🅐 -> 🅩 (U+1F150 -> U+1F169): NFCK/NFKD 转换后跟原来一样。
     *   Negative Squared Latin Capital Letter 🅰 -> 🆉 (U+1F170 -> U+1F189): NFCK/NFKD 转换后跟原来一样。
     *   Regional Indicator Symbol Letter 🇦 -> 🇿 (U+1F1E6 -> U+1F1FF): NFCK/NFKD 转换后跟原来一样。
     */
    private String preprocess(String s) {
        if (s == null) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        boolean atLowSurrogate = false;
        for (int i = 0; i < s.length(); i++) {
            if (atLowSurrogate) {
                atLowSurrogate = false;
                continue;
            }
            int cp = s.codePointAt(i);
            atLowSurrogate = (cp > 0xffff);
            if (cp >= 0x249C && cp <= 0x24B5) {
                // Parenthesized Latin Small Letter ⒜ -> ⒵ (U+249C -> U+24B5)
                cp = cp - 0x249c + 0x61;
            } else if (cp >= 0x1F110 && cp <= 0x1F129) {
                // Parenthesized Latin Capital Letter 🄐 -> 🄩 (U+1F110 -> U+1F129)
                cp = cp - 0x1F110 + 0x41;
            } else if (cp >= 0x1F150 && cp <= 0x1F169) {
                // Negative Circled Latin Capital Letter 🅐 -> 🅩 (U+1F150 -> U+1F169)
                cp = cp - 0x1F150 + 0x41;
            } else if (cp >= 0x1F170 && cp <= 0x1F189) {
                // Negative Squared Latin Capital Letter 🅰 -> 🆉 (U+1F170 -> U+1F189)
                cp = cp - 0x1F170 + 0x41;
            } else if (cp >= 0x1F1E6 && cp <= 0x1F1FF) {
                // Regional Indicator Symbol Letter 🇦 -> 🇿 (U+1F1E6 -> U+1F1FF)
                cp = cp - 0x1F1E6 + 0x41;
            }
            sb.appendCodePoint(cp);
            // r += codePointToString(cp);
        }
        return sb.toString();
    }

    public String evaluate(String s, Boolean removeEmoji) {
        if (s == null) {
            return null;
        }

        s = preprocess(s);
        String ret = Normalizer.normalize(s, Normalizer.Form.NFKD);

        if (removeEmoji) {
            // filter out emoji
            //p{M} or p{Mark}: a character intended to be combined with another character (e.g. accents, umlauts, enclosing boxes, etc.)
            String regex = "[^-\\p{L}\\p{N}\\p{Z}\\p{M}!-\\.:-@_]";
            Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(ret);
            ret = matcher.replaceAll("");
        }
        return ret;
    }
}
