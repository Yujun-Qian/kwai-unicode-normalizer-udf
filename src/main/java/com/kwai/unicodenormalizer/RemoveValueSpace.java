package com.kwai.unicodenormalizer;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

@Description(name = "unicode_remove_spaces", value = "_FUNC_(s) - UDF that removes space from unicode string")
public class RemoveValueSpace extends UDF {
    public String evaluate(String s) {
        if (s == null) {
            return null;
        }

        //return s.replaceAll("\\s+", "");
        return s.replaceAll("[ \\s]+", "");
    }
}