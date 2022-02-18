package com.kwai.unicodenormalizer;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author qianyujun <qianyujun@kuaishou.com>
 * Created on 2021-01-24
 */
@Description(name = "unicode_remove_puncts_arr", value = "_FUNC_(s) - UDF that removes punctuation characters in unicode string arrays")
public class RemovePunctsArr extends UDF {
    public List<String> evaluate(List<String> sa) {
        if (sa == null) {
            return null;
        }

        RemovePuncts remover = new RemovePuncts();
        return sa.stream().map(t -> remover.evaluate(t)).collect(Collectors.toList());
    }
}
