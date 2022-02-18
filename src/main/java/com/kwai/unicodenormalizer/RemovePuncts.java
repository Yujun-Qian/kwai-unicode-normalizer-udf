package com.kwai.unicodenormalizer;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

@Description(name = "unicode_remove_puncts", value = "_FUNC_(s) - UDF that removes punctuation characters in unicode string")
public class RemovePuncts extends UDF {
    public String evaluate(String s) {
        if (s == null) {
            return null;
        }

        return s.replaceAll("\\p{IsPunctuation}", " ");
    }
}
