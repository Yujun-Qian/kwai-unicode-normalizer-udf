package com.kwai.unicodenormalizer;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author qianyujun <qianyujun@kuaishou.com>
 * Created on 2021-01-24
 */
@Description(name = "unicode_remove_diacritics_arr", value = "_FUNC_(s) - UDF that removes diacritics in unicode string arrays")
public class RemoveDiacriticsArr extends UDF {
    public List<String> evaluate(List<String> sa, String bkt) {
        if (sa == null) {
            return null;
        }

        RemoveDiacritics remover = new RemoveDiacritics();
        return sa.stream().map(t -> remover.evaluate(t, bkt)).collect(Collectors.toList());
    }
}
