package com.kwai.unicodenormalizer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

@Description(name = "concat_with_tab", value = "_FUNC_(s) - UDF that concatenate strings with tab")
public class ConcatWithTabForLabels extends UDF {
	public String evaluate(String s1, String s2) {
		if (s1 == null || s2 == null) {
			return null;
		}

		if (s1.equals("") || s2.equals("")) {
			return "";
		}

		if (s1.length() <= 1 || s2.length() <= 1) {
			return "";
		}

		String ret = s1 + "\t" + s2;
		try {
			if (ret.getBytes("UTF-8").length > 200) {
				return "";
			}
		} catch (Exception e) {
			return "";
		}

		return ret;
	}
}
