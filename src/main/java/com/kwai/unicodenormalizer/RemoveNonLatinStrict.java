package com.kwai.unicodenormalizer;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

@Description(name = "unicode_remove_non_latin_strict", value = "_FUNC_(s) - UDF that removes non Latin characters strictly in unicode string")
public class RemoveNonLatinStrict extends UDF {
    public String evaluate(String s) {
        if (s == null) {
            return null;
        }

        String ret = Normalizer.normalize(s, Normalizer.Form.NFD);
        String regex = "[^-\\p{IsLatin}\\p{N}\\p{Z}\\p{M}]";
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(ret);
        ret = matcher.replaceAll("");
        return ret;
    }
}