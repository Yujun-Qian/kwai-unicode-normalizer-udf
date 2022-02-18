package com.kwai.unicodenormalizer;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

@Description(name = "unicode_remove_diacritics", value = "_FUNC_(s) - UDF that removes diacritics in unicode string")
public class RemoveDiacritics extends UDF {
    private static String removeDiacritics(String s) {
        if (s == null) {
            return null;
        }

        String ret = Normalizer.normalize(s, Normalizer.Form.NFD);
        String regex = "[\\p{InCombiningDiacriticalMarks}]";
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(ret);
        ret = matcher.replaceAll("");
        return ret;
    }

    private static boolean containsDiacrictics(String s) {
        if (s == null) {
            return false;
        }

        String s2 = Normalizer.normalize(s, Normalizer.Form.NFD);
        return s2.matches("(?s).*\\p{InCombiningDiacriticalMarks}.*");
    }

    private static String termProcessDiacritics(String s, String bkt) {
        if (s == null) {
            return null;
        }

        final Map<String, Map<String, String> > ms = new HashMap<String, Map<String, String> >() {{
            put("spa", new HashMap<String, String>() {{
                put("ano", "año");
                put("cana", "caña");
                put("una", "uña");
                put("sonar", "soñar");
                put("pina", "piña");
                put("mano", "maño");
                put("mono", "moño");
                put("cual", "cuál");
                put("de", "dé");
                put("tomate", "tómate");
                put("te", "té");
                put("tu", "tú");
                put("el", "él");
                put("esta", "está");
                put("que", "qué");
                put("se", "sé");
                put("si", "sí");
                put("mas", "más");
                put("mi", "mí");
                put("papa", "papá");
            }});
            put("br", new HashMap<String, String>() {{
                put("ai", "aí");
                put("avos", "avós");
                put("baba", "babá");
                put("para", "pará");
                put("do", "dó");
                put("baia", "baía");
                put("cara", "cará");
                put("carne", "carnê");
                put("camelo", "camelô");
                put("coco", "cocô");
                put("caqui", "cáqui");
                put("vivido", "vívido");
                put("da", "dá");
                put("de", "dé");
                put("esta", "está");
                put("forro", "forró");
                put("publico", "público");
                put("porque", "porquê");
                put("pais", "país");
                put("sabiá", "sábia");
                put("se", "sé");
                put("mas", "más");
                put("manga", "mangá");
                put("maio", "maiô");
                put("secretária", "secretaria");
            }});
        }};
        Map<String, String> m = ms.get(bkt.toLowerCase());
        String t = removeDiacritics(s);

        if (m == null) {
            return t;
        }
        return m.containsKey(t) ? (containsDiacrictics(s) ? m.get(t) : s) : t;
    }

    public String evaluate(String s, String bkt) {
        if (s == null) {
            return null;
        }
        String[] sa = s.split("[ \\s]+");
        List<String> al = Arrays.stream(sa).map(t -> termProcessDiacritics(t, bkt)).collect(Collectors.toList());

        return String.join(" ", al);
    }

}