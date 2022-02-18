package com.kwai.unicodenormalizer;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

@Description(name = "unicode_remove_tab", value = "_FUNC_(s) - UDF that removes tab from unicode string")
public class RemoveTab extends UDF {
    public String evaluate(String s) {
        if (s == null) {
            return null;
        }

        return s.replaceAll("\\t+", "");
    }
}