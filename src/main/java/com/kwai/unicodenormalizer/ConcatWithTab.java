package com.kwai.unicodenormalizer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

@Description(name = "concat_with_tab", value = "_FUNC_(s) - UDF that concatenate strings with tab")
public class ConcatWithTab extends UDF {
	public static Set<String> LABELS = Stream.of(
			"funk", "musicasfunk", "musicasromanticas", "pagode", "louvoresgospel", "fank", "musicagospel", "gospel",
			"pagoderomantico", "reggae", "trapedit", "bregafunk", "vallenato", "vallenatosromanticos",
			"salsaromantica", "vallenatos", "musicadeamor", "cancionesdeamor", "salsabaul",
			"cancionestristes", "regueton", "rock", "musicadecumpleanos", "musicavallenato",
			"musicaregueton", "cumbias", "electronica", "musicatriste", "rap", "musicasalsa",
			"romanticas", "regeton", "ballenato", "cancionesromanticas", "vallenatosdeamor", "cumpleanos",
			"romantico", "love", "aniversario", "romanticos",
			"triste", "musica", "musicas", "canciones", "cansiones", "music", "musik",
			"غانيحزينه","موسيقى","اغانيرومانسيهلحبيبي",
			"موالحزين","اغانى", "راب", "اغانىحزينه", "اغانيحزينهجدا","اغانيحزينهعنالحب" ,"اغانيحبورومانسيه","اغنيه",
			"شيلاتحزينه","اغنيهرومانسيه","اغانيحزينهعنالفراق","اغانيعيدميلاد","اغانيحب","اغانيرومانسيه", "اغاني", "اغانيحزينه")
			.collect(Collectors.toCollection(HashSet::new));

	public String evaluate(String s1, String s2) {
		if (s1 == null || s2 == null) {
			return null;
		}

		if (s1.equals("") || s2.equals("")) {
			return "";
		}

		if (LABELS.contains(s1) || LABELS.contains(s2)) {
			return "";
		}

		if (s1.length() <= 1 || s2.length() <= 1) {
			return "";
		}

		try {
			if (s1.getBytes("UTF-8").length > 30) {
				return "";
			}
		} catch (Exception e) {
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
