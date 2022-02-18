package com.kwai.unicodenormalizer;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

@Description(name = "unicode_remove_spaces", value = "_FUNC_(s) - UDF that removes space from unicode string")
public class RemoveKeySpace extends UDF {
	public String evaluate(String s) {
		if (s == null) {
			return null;
		}

		int startIndex = s.indexOf("(");
		int endIndex = s.lastIndexOf(")");
		if (startIndex > 0 && endIndex > 0 && endIndex > startIndex) {
			s = s.substring(0, startIndex) + s.substring(endIndex + 1);

		}

		startIndex = s.indexOf("[");
		endIndex = s.lastIndexOf("]");
		if (startIndex > 0 && endIndex > 0 && endIndex > startIndex) {
			s = s.substring(0, startIndex) + s.substring(endIndex + 1);
		}

		return s.replaceAll("[ \\s]+", "");
	}
}
