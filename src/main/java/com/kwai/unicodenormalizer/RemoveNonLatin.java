package com.kwai.unicodenormalizer;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

@Description(name = "unicode_removeNonLatin", value = "_FUNC_(s) - UDF that removes non Latin characters in unicode string")
public class RemoveNonLatin extends UDF {
    public String evaluate(String s) {
        if (s == null) {
            return null;
        }

        String ret = Normalizer.normalize(s, Normalizer.Form.NFD);
        String regex = "[^-\\p{IsLatin}\\p{N}\\p{Z}\\p{M}\\p{S}!-\\.:-@_\\uD83C-\\uDBFF\\uDC00-\\uDFFF]";
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(ret);
        ret = matcher.replaceAll("");
        return ret;
    }
}
