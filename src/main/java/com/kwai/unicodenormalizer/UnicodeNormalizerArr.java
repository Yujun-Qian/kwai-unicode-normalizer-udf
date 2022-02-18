package com.kwai.unicodenormalizer;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author qianyujun <qianyujun@kuaishou.com>
 * Created on 2021-01-24
 */
@Description(name = "unicode_normalizer_arr", value = "_FUNC_(s) - UDF that returns the normalized unicode string array")
public class UnicodeNormalizerArr extends UDF {
    public List<String> evaluate(List<String> sa, Boolean removeEmoji) {
        if (sa == null) {
            return null;
        }

        UnicodeNormalizer normalizer = new UnicodeNormalizer();
        return sa.stream().map(t -> normalizer.evaluate(t, removeEmoji)).collect(Collectors.toList());
    }
}
