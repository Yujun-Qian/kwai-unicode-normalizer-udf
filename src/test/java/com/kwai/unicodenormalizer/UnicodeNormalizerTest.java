package com.kwai.unicodenormalizer;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

import com.kwai.unicodenormalizer.IntentParser.IntentParserResult;

public class UnicodeNormalizerTest {

    public UnicodeNormalizerTest() {
    }

    @Test
    public void normalizeTest() {
        UnicodeNormalizer normalizer = new UnicodeNormalizer();
        assertEquals(normalizer.evaluate("快手", true), "快手");
        assertEquals(normalizer.evaluate("快手", false), "快手");
        assertEquals(normalizer.evaluate("㊀㊁㊂㊃㊄㊎㊍㊌㊋㊏", true), "一二三四五金木水火土");
        assertEquals(normalizer.evaluate("㊀㊁㊂㊃㊄㊎㊍㊌㊋㊏", true), "一二三四五金木水火土");
        assertEquals(normalizer.evaluate("𝐯𝐢𝐝𝐞𝐨𝐬 𝐝𝐞 𝐩𝐨𝐫𝐧𝐨", true), "videos de porno");
        assertEquals(normalizer.evaluate("𝐯𝐢𝐝𝐞𝐨𝐬 𝐝𝐞 𝐩𝐨𝐫𝐧𝐨", false), "videos de porno");
        assertEquals(normalizer.evaluate("ｖｉｄｅｏｓ ｄｅ ｐｏｒｎｏ", true), "videos de porno");
        assertEquals(normalizer.evaluate("ｖｉｄｅｏｓ ｄｅ ｐｏｒｎｏ", false), "videos de porno");
        assertEquals(normalizer.evaluate("𝕒𝕣𝕣𝕖𝕡𝕖𝕟𝕥𝕚𝕕𝕒 𝕕𝕖𝕝𝕒 𝕧𝕚𝕕𝕒", true), "arrepentida dela vida");
        assertEquals(normalizer.evaluate("𝕒𝕣𝕣𝕖𝕡𝕖𝕟𝕥𝕚𝕕𝕒 𝕕𝕖𝕝𝕒 𝕧𝕚𝕕𝕒", false), "arrepentida dela vida");
        assertEquals(normalizer.evaluate("𝘷𝘪𝘥𝘦𝘰𝘴 𝘥𝘦 𝘢𝘮𝘰𝘳", true), "videos de amor");
        assertEquals(normalizer.evaluate("𝘷𝘪𝘥𝘦𝘰𝘴 𝘥𝘦 𝘢𝘮𝘰𝘳", false), "videos de amor");
        assertEquals(normalizer.evaluate("São Paulo (state)", true), "São Paulo (state)");
        assertEquals(normalizer.evaluate("São Paulo (state)", false), "São Paulo (state)");
        assertEquals(normalizer.evaluate("Hello?.-_()9", true), "Hello?.-_()9");
        assertEquals(normalizer.evaluate("Hello?.-_()9", false), "Hello?.-_()9");

        assertEquals(normalizer.evaluate("👅💋😳🅥🅘🅓🅔🅞🅢 🅓🅔 🅟🅞🅡🅝🅞", true), "VIDEOS DE PORNO");
        assertEquals(normalizer.evaluate("👅💋😳🅥🅘🅓🅔🅞🅢 🅓🅔 🅟🅞🅡🅝🅞", false), "👅💋😳VIDEOS DE PORNO");
        assertEquals(normalizer.evaluate("👅💋😳🆅🅸🅳🅴🅾🆂 🅳🅴 🅿🅾🆁🅽🅾", true), "VIDEOS DE PORNO");
        assertEquals(normalizer.evaluate("👅💋😳🆅🅸🅳🅴🅾🆂 🅳🅴 🅿🅾🆁🅽🅾", false), "👅💋😳VIDEOS DE PORNO");
        assertEquals(normalizer.evaluate("👅💋😳⒱⒤⒟⒠⒪⒮ ⒟⒠ ⒫⒪⒭⒩⒪",true), "videos de porno");
        assertEquals(normalizer.evaluate("👅💋😳⒱⒤⒟⒠⒪⒮ ⒟⒠ ⒫⒪⒭⒩⒪", false), "👅💋😳videos de porno");

        assertEquals(normalizer.evaluate("ⓈⒺⓍⓄ", true), "SEXO");
        assertEquals(normalizer.evaluate("ⓈⒺⓍⓄ", false), "SEXO");
        assertEquals(normalizer.evaluate("꧁༺ⒻⓇⒺⒺ ⒻⒾⓇⒺ༻꧂", true), "FREE FIRE");
        assertEquals(normalizer.evaluate("꧁༺ⒻⓇⒺⒺ ⒻⒾⓇⒺ༻꧂", false), "꧁༺FREE FIRE༻꧂");
    }

    @Test
    public void normalizeCaseTest() {
        UnicodeNormalizerCase normalizer = new UnicodeNormalizerCase();
        assertEquals(normalizer.evaluate("㊀㊁㊂㊃㊄㊎㊍㊌㊋㊏", false), "㊀㊁㊂㊃㊄㊎㊍㊌㊋㊏");
        assertEquals(normalizer.evaluate("Silsilla Ye सिल्सिल्ला ये", false), "silsilla ye सिल्सिल्ला ये");
        assertEquals(normalizer.evaluate("👅💋😳🅥🅘🅓🅔  🅟🅞🅡🅝🅞ᴍʙ", false), "👅💋😳🅥🅘🅓🅔  🅟🅞🅡🅝🅞ᴍʙ");
        assertEquals(normalizer.evaluate("꧁༺ⒻⓇⒺⒺ ⒻⒾⓇⒺ༻꧂", false), "꧁༺ⓕⓡⓔⓔ ⓕⓘⓡⓔ༻꧂");
        assertEquals(normalizer.evaluate("快手", false), "快手");
        assertEquals(normalizer.evaluate("𝐯𝐢𝐝𝐞𝐨𝐬 𝐝𝐞 𝐩𝐨𝐫𝐧𝐨", false), "𝐯𝐢𝐝𝐞𝐨𝐬 𝐝𝐞 𝐩𝐨𝐫𝐧𝐨");
        assertEquals(normalizer.evaluate("ｖｉｄｅｏｓ ｄｅ ｐｏｒｎｏ", false), "ｖｉｄｅｏｓ ｄｅ ｐｏｒｎｏ");
        assertEquals(normalizer.evaluate("𝕒𝕣𝕣𝕖𝕡𝕖𝕟𝕥𝕚𝕕𝕒 𝕕𝕖𝕝𝕒 𝕧𝕚𝕕𝕒", false), "𝕒𝕣𝕣𝕖𝕡𝕖𝕟𝕥𝕚𝕕𝕒 𝕕𝕖𝕝𝕒 𝕧𝕚𝕕𝕒");
        assertEquals(normalizer.evaluate("𝘷𝘪𝘥𝘦𝘰𝘴 𝘥𝘦 𝘢𝘮𝘰𝘳", false), "𝘷𝘪𝘥𝘦𝘰𝘴 𝘥𝘦 𝘢𝘮𝘰𝘳");
        assertEquals(normalizer.evaluate("São Paulo (state)", false), "são paulo (state)");
        assertEquals(normalizer.evaluate("Hello?.-_()9", false), "hello?.-_()9");
        assertEquals(normalizer.evaluate("👅💋😳🆅🅸🅳🅴🅾🆂 🅳🅴 🅿🅾🆁🅽🅾", false), "👅💋😳🆅🅸🅳🅴🅾🆂 🅳🅴 🅿🅾🆁🅽🅾");
        assertEquals(normalizer.evaluate("👅💋😳⒱⒤⒟⒠⒪⒮ ⒟⒠ ⒫⒪⒭⒩⒪", false), "👅💋😳⒱⒤⒟⒠⒪⒮ ⒟⒠ ⒫⒪⒭⒩⒪");
        assertEquals(normalizer.evaluate("ⓈⒺⓍⓄ", false), "ⓢⓔⓧⓞ");
        assertEquals(normalizer.evaluate("c´est la vie", false), "c´est la vie");
        assertEquals(normalizer.evaluate("!magnic!, Futuristic Swaver", false), "!magnic!  futuristic swaver");
    }

    @Test
    public void normalizeCaseNoDiacriticsTest() {
        UnicodeNormalizerCaseNoDiacritics normalizer = new UnicodeNormalizerCaseNoDiacritics();
        assertEquals(normalizer.evaluate("㊀㊁㊂㊃㊄㊎㊍㊌㊋㊏", false), "一二三四五金木水火土");
        assertEquals(normalizer.evaluate("Silsilla Ye सिल्सिल्ला ये", false), "silsilla ye सिल्सिल्ला ये");
        assertEquals(normalizer.evaluate("👅💋😳🅥🅘🅓🅔  🅟🅞🅡🅝🅞ᴍʙ", false), "👅💋😳🅥🅘🅓🅔  🅟🅞🅡🅝🅞ᴍʙ");
        assertEquals(normalizer.evaluate("꧁༺ⒻⓇⒺⒺ ⒻⒾⓇⒺ༻꧂", false), "꧁༺free fire༻꧂");
        assertEquals(normalizer.evaluate("快手", false), "快手");
        assertEquals(normalizer.evaluate("𝐯𝐢𝐝𝐞𝐨𝐬 𝐝𝐞 𝐩𝐨𝐫𝐧𝐨", false), "videos de porno");
        assertEquals(normalizer.evaluate("ｖｉｄｅｏｓ ｄｅ ｐｏｒｎｏ", false), "videos de porno");
        assertEquals(normalizer.evaluate("𝕒𝕣𝕣𝕖𝕡𝕖𝕟𝕥𝕚𝕕𝕒 𝕕𝕖𝕝𝕒 𝕧𝕚𝕕𝕒", false), "arrepentida dela vida");
        assertEquals(normalizer.evaluate("𝘷𝘪𝘥𝘦𝘰𝘴 𝘥𝘦 𝘢𝘮𝘰𝘳", false), "videos de amor");
        assertEquals(normalizer.evaluate("São Paulo (state)", false), "sao paulo (state)");
        assertEquals(normalizer.evaluate("Hello?.-_()9", false), "hello?.-_()9");
        assertEquals(normalizer.evaluate("👅💋😳🆅🅸🅳🅴🅾🆂 🅳🅴 🅿🅾🆁🅽🅾", false), "👅💋😳🆅🅸🅳🅴🅾🆂 🅳🅴 🅿🅾🆁🅽🅾");
        assertEquals(normalizer.evaluate("👅💋😳⒱⒤⒟⒠⒪⒮ ⒟⒠ ⒫⒪⒭⒩⒪", false), "\uD83D\uDC45\uD83D\uDC8B\uD83D\uDE33(v)(i)"
                + "(d)(e)(o)(s) (d)(e) (p)(o)(r)(n)(o)");
        assertEquals(normalizer.evaluate("ⓈⒺⓍⓄ", false), "sexo");

        assertEquals(normalizer.evaluate("Poursuitenéolithique", false), "poursuiteneolithique");
        assertEquals(normalizer.evaluate("Maycon Jackson", false), "maycon jackson");
        assertEquals(normalizer.evaluate("c´est la vie", false), "c est la vie");
        assertEquals(normalizer.evaluate("!magnic!, Futuristic Swaver", false), "!magnic! futuristic swaver");
        assertEquals(normalizer.evaluate("vallenatos romanticos", false), "vallenatos romanticos");
        assertEquals(normalizer.evaluate("vallenatos románticos", false), "vallenatos romanticos");
        assertEquals(normalizer.evaluate("salsa baul", false), "salsa baul");
        assertEquals(normalizer.evaluate("salsa baúl", false), "salsa baul");
        assertEquals(normalizer.evaluate("musica regueton", false), "musica regueton");
        assertEquals(normalizer.evaluate("música reguetón", false), "musica regueton");
        assertEquals(normalizer.evaluate("musica vallenato", false), "musica vallenato");
        assertEquals(normalizer.evaluate("música vallenato", false), "musica vallenato");
        assertEquals(normalizer.evaluate("reguetón", false), "regueton");
        assertEquals(normalizer.evaluate("músicas románticas", false), "musicas romanticas");
        assertEquals(normalizer.evaluate("salsa romántica", false), "salsa romantica");
        assertEquals(normalizer.evaluate("músicas românticas", false), "musicas romanticas");
        assertEquals(normalizer.evaluate("pagode romântico", false), "pagode romantico");
        assertEquals(normalizer.evaluate("música de cumpleaños", false), "musica de cumpleanos");
    }

    @Test
    public void normalizeArrTest() {
        UnicodeNormalizerArr normalizer = new UnicodeNormalizerArr();
        assertEquals(normalizer.evaluate(Arrays.asList(new String[]{
                    "快手", "㊀㊁㊂㊃㊄㊎㊍㊌㊋㊏", "𝐯𝐢𝐝𝐞𝐨𝐬 𝐝𝐞 𝐩𝐨𝐫𝐧𝐨",
                    "ｖｉｄｅｏｓ ｄｅ ｐｏｒｎｏ", "𝕒𝕣𝕣𝕖𝕡𝕖𝕟𝕥𝕚𝕕𝕒 𝕕𝕖𝕝𝕒 𝕧𝕚𝕕𝕒",
                    "𝘷𝘪𝘥𝘦𝘰𝘴 𝘥𝘦 𝘢𝘮𝘰𝘳", "São Paulo (state)", "Hello?.-_()9",
                    "👅💋😳🅥🅘🅓🅔🅞🅢 🅓🅔 🅟🅞🅡🅝🅞", "👅💋😳🆅🅸🅳🅴🅾🆂 🅳🅴 🅿🅾🆁🅽🅾",
                    "👅💋😳⒱⒤⒟⒠⒪⒮ ⒟⒠ ⒫⒪⒭⒩⒪", "ⓈⒺⓍⓄ",
                    "꧁༺ⒻⓇⒺⒺ ⒻⒾⓇⒺ༻꧂"
                }), false),
                Arrays.asList(new String[]{
                    "快手", "一二三四五金木水火土", "videos de porno",
                    "videos de porno", "arrepentida dela vida",
                    "videos de amor", "São Paulo (state)", "Hello?.-_()9",
                    "👅💋😳VIDEOS DE PORNO","👅💋😳VIDEOS DE PORNO",
                    "👅💋😳videos de porno", "SEXO",
                    "꧁༺FREE FIRE༻꧂"
                })
        );

        assertEquals(normalizer.evaluate(Arrays.asList(new String[]{
                    "快手", "㊀㊁㊂㊃㊄㊎㊍㊌㊋㊏", "𝐯𝐢𝐝𝐞𝐨𝐬 𝐝𝐞 𝐩𝐨𝐫𝐧𝐨",
                    "ｖｉｄｅｏｓ ｄｅ ｐｏｒｎｏ", "𝕒𝕣𝕣𝕖𝕡𝕖𝕟𝕥𝕚𝕕𝕒 𝕕𝕖𝕝𝕒 𝕧𝕚𝕕𝕒",
                    "𝘷𝘪𝘥𝘦𝘰𝘴 𝘥𝘦 𝘢𝘮𝘰𝘳", "São Paulo (state)", "Hello?.-_()9",
                    "👅💋😳🅥🅘🅓🅔🅞🅢 🅓🅔 🅟🅞🅡🅝🅞", "👅💋😳🆅🅸🅳🅴🅾🆂 🅳🅴 🅿🅾🆁🅽🅾",
                    "👅💋😳⒱⒤⒟⒠⒪⒮ ⒟⒠ ⒫⒪⒭⒩⒪", "ⓈⒺⓍⓄ",
                    "꧁༺ⒻⓇⒺⒺ ⒻⒾⓇⒺ༻꧂"
                }), true),
                Arrays.asList(new String[]{
                    "快手", "一二三四五金木水火土", "videos de porno",
                    "videos de porno", "arrepentida dela vida",
                    "videos de amor", "São Paulo (state)", "Hello?.-_()9",
                    "VIDEOS DE PORNO","VIDEOS DE PORNO",
                    "videos de porno", "SEXO",
                    "FREE FIRE"
                })
        );
    }

    @Test
    public void removeNonLatinTest() {
        RemoveNonLatin remover = new RemoveNonLatin();
        assertEquals(remover.evaluate("ÃãÀàÁáÂâÄäÅå ẠặẶậẬḁḀ ḉḈ æAa 快手 한국 おか ⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂"),
                "ÃãÀàÁáÂâÄäÅå ẠặẶậẬḁḀ ḉḈ æAa    ⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂");
    }

    @Test
    public void StringLengthTest() {
        // The input string for this test
        //final String string = "hello world";
        final String string = "不知道大家有没有注意到一个事实：没有任何一个法师会先出虚无法杖或者日暮之流等法穿装，而绝大多数的近战英雄都会先出暗影战斧这件带有物穿属性的装备。";
        //final String string = "\n";

        // Check length, in characters
        System.out.println("length of string(i.e. number of characters): " + string.length()); // prints "11"

        try {
            // Check encoded sizes
            final byte[] utf8Bytes = string.getBytes("UTF-8");
            System.out.println("length of UTF-8 encoding: " + utf8Bytes.length); // prints "11"

            final byte[] utf16Bytes = string.getBytes("UTF-16");
            System.out.println("length of UTF-16 encoding: " + utf16Bytes.length); // prints "24"
            System.out.println("\n");
            for(int i = 0; i < utf16Bytes.length; i++){
                System.out.println(Integer. toHexString(utf16Bytes[i]));
            }
            System.out.println("\n");

            final byte[] utf32Bytes = string.getBytes("UTF-32");
            System.out.println("length of UTF-32 encoding: " + utf32Bytes.length); // prints "44"

            final byte[] isoBytes = string.getBytes("ISO-8859-1");
            System.out.println("length of ISO-8859-1 encoding: " + isoBytes.length); // prints "11"
            System.out.println("\n");
            for(int i = 0; i < isoBytes.length; i++){
                System.out.println(Integer. toHexString(isoBytes[i]));
            }
            System.out.println("\n");

            final byte[] winBytes = string.getBytes("CP1252");
            System.out.println("length of CP1252 encoding: " + winBytes.length); // prints "11"
            System.out.println("\n");
            for(int i = 0; i < winBytes.length; i++){
                System.out.println(Integer. toHexString(winBytes[i]));
            }
            System.out.println("\n");
        } catch (Exception e) {
        }
    }

    @Test
    public void removeNonLatinArrTest() {
        RemoveNonLatinArr remover = new RemoveNonLatinArr();
        assertEquals(remover.evaluate(Arrays.asList(new String[]{"ÃãÀàÁáÂâÄäÅå ẠặẶậẬḁḀ ḉḈ æAa 快手 한국 おか ⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂"})),
                Arrays.asList(new String[]{"ÃãÀàÁáÂâÄäÅå ẠặẶậẬḁḀ ḉḈ æAa    ⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂"}));
    }

    @Test
    public void removeNonLatinStrictTest() {
        RemoveNonLatinStrict remover = new RemoveNonLatinStrict();
        assertEquals(remover.evaluate("ÃãÀàÁáÂâÄäÅå ẠặẶậẬḁḀ ḉḈ æAa 123 ?!' 快手 한국 おか ⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂"),
                "ÃãÀàÁáÂâÄäÅå ẠặẶậẬḁḀ ḉḈ æAa 123     ");
    }

    @Test
    public void removeNonLatinStrictArrTest() {
        RemoveNonLatinStrictArr remover = new RemoveNonLatinStrictArr();
        assertEquals(remover.evaluate(Arrays.asList(new String[]{"ÃãÀàÁáÂâÄäÅå ẠặẶậẬḁḀ ḉḈ æAa 123 ?!' 快手 한국 おか ⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂"})),
                Arrays.asList(new String[]{"ÃãÀàÁáÂâÄäÅå ẠặẶậẬḁḀ ḉḈ æAa 123     "}));
    }

    /*
    @Test
    public void removeDiacriticsTest() {
        RemoveDiacritics remover = new RemoveDiacritics();
        assertEquals(remover.evaluate("ÃãÀàÁáÂâÄäÅå Aa快手⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂"),
                "AaAaAaAaAaAa Aa快手⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂");
    }
    */

    @Test
    public void removePuncts() {
        RemovePuncts remover = new RemovePuncts();
        assertEquals(remover.evaluate("ÃãÀàÁáÂâÄäÅå Aa快手⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂!',;"),
                "ÃãÀàÁáÂâÄäÅå Aa快手⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂    ");
    }

    @Test
    public void removeValueSpaces() {
        RemoveValueSpace remover = new RemoveValueSpace();
        assertEquals(remover.evaluate("a b  c   d       "), "abcd");
        assertEquals(remover.evaluate("a sigh at twilight"), "asighattwilight");
        assertEquals(remover.evaluate("!magnic!, futuristic swaver"), "!magnic!,futuristicswaver");
        assertEquals(remover.evaluate("!magnic!," + "\r\n" + "futuristic swaver"), "!magnic!,futuristicswaver");
    }

    @Test
    public void editDistance() {
        EditDistance distance = new EditDistance();
        assertEquals(distance.minDistance("两个黄鹂鸣翠柳", "两个黄鹂ÃãÀàÁ鸣翠柳"), 5);
        assertEquals(distance.minDistance("两个黄鹂鸣翠柳", "十年生死两茫茫"), 7);
        assertEquals(distance.minDistance("pagode romântico", "o人mân"), 12);
    }

    @Test
    public void intentParser() {
        IntentParser parser = new IntentParser();
        assertEquals(parser.parse("joao%20gomes 10)]}'", "[[[\"joao gomes\",46,[512,433],{\"zh\":\"João Gomes\","
                + "\"zi\":\"Cantor\",\"zp\":{\"gs_ssp\":\"eJzj4tVP1zc0TMsyMS-0NCgyYPTiyspPzFdIz89NLQYAamIILw\"},"
                + "\"zs\":\"https://encrypted-tbn0.gstatic"
                + ".com/images?q=tbn:ANd9GcQ2qhpNqLVZthy8vV90_h3SKKPDvB790erRsEd5EOB0OQ&s=10\"}],[\"joao gomes<b> sua"
                + " musica<\\/b>\",0,[512,433]],[\"<b>joão <\\/b>gomes<b> idade<\\/b>\",0,[512]],[\"joao gomes "
                + "flamengo\",46,[512,433],{\"zh\":\"João Gomes\",\"zi\":\"Jogador de futebol\","
                + "\"zp\":{\"gs_ssp\":\"eJzj4tVP1zc0zDKsSrYsySkxYPQSzspPzFdIz89NLVZIy0nMTc1LzwcA0TEMHw\"},"
                + "\"zs\":\"https://encrypted-tbn0.gstatic"
                + ".com/images?q=tbn:ANd9GcQv5RrU9oWVBkaJuJcjdcmFl56BFhfjTjHHZHEMKxSdDsiwuspZ3ClpCGL37Q&s=10\"}],"
                + "[\"joao gomes<b> musicas<\\/b>\",0,[512,433]],[\"joao gomes<b> agenda<\\/b>\",0,[512]],[\"joao "
                + "gomes<b> cifra<\\/b>\",0,[512]],[\"joao gomes<b> show<\\/b>\",0,[512,433]],[\"<b>joão "
                + "<\\/b>gomes<b> letra<\\/b>\",0,[512]],[\"joao gomes<b> e simaria<\\/b>\",0,[512]]],"
                + "{\"i\":\"biaot\",\"q\":\"fBcYfc0egZsnyBakLgkTYnMDsSU\"}]"), new IntentParserResult("joao gomes", 0, "João Gomes"));
        assertEquals(parser.parse("marilia%20mendonca%20e%20mayara%20e%20maraisa 35)]}'", "[[[\"marilia mendonça e "
                + "maiara e maraisa\",46,[10],{\"zh\":\"marilia mendonça e maiara e maraisa\",\"zi\":\"Maiara &amp; "
                + "Maraisa — Dupla sertaneja\","
                + "\"zp\":{\"gs_ssp"
                + "\":\"eJzj4tVP1zc0TCqpio_PMig2YPRSyU0syszJTFTITc1Lyc87vDxRIVUhNzEzsQjCKErMLE4EAOiUEtc\"},"
                + "\"zs\":\"https://encrypted-tbn0.gstatic"
                + ".com/images?q=tbn:ANd9GcQypQ4l2fEAV2Dy_JwLHwpc2Vm9QI59GE9F7KKH6uUYhaZ97gES6sJMTnFojQ&s=10\"}],"
                + "[\"marilia<b> mendonça <\\/b>e<b> maiara <\\/b>e maraisa<b> musicas<\\/b>\",0,[10]],[\"marília "
                + "mendonça e maiara e maraísa as patroas\",46,[10],{\"zh\":\"Patroas\",\"zi\":\"Álbum de estúdio de "
                + "Maiara &amp; Maraisa e Marília Mendonça\","
                + "\"zp\":{\"gs_ssp"
                +
                "\":\"eJzj4tVP1zc0zMguNE4ryTY2YPQyzE0sOrw2JzNRITc1LyU_7_DyRIVUhdzEzMQiCKMo8fDa4kSFxGKFgsSSovzEYgAcuxjU\"},"
                + "\"zs\":\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSKmi2qKEwl5kZx696cqmP3R2gmVPUuJjWwT3g78D08dKWhKsfV35FNOy4GZg&s=10\"}],"
                + "[\"marilia<b> mendonça <\\/b>e<b> maiara <\\/b>e maraisa<b> 2021<\\/b>\",0,[10]],[\"marilia mendonça e maiara e maraisa todo mundo\",46,[10],"
                + "{\"zh\":\"Todo mundo menos você\",\"zi\":\"Canção de Maiara &amp; Maraisa e Marília Mendonça\","
                + "\"zp\":{\"gs_ssp\":\"eJzj4tVP1zc0LKo0zqrKqDA0YPTSz00syszJTFTITc1Lyc87vDxRIVUhNzEzsQjCKErMLE5UKMlPyVfILQWqAADPahbz\"},"
                + "\"zs\":\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSwpE_wIo6i3XD8-yhXjQYUjblGh1vBOwFsAbfRWdT7ZJKhVpWxiXN9qJsREsI&s=10\"}],"
                + "[\"marilia mendonca e <b>maiara<\\/b> e maraisa<b> no domingo<\\/b>\",0,[10]],[\"marília mendonça e maiara e maraísa fã clube\",46,[10],"
                + "{\"zh\":\"Fã clube\",\"zi\":\"Canção de Maiara &amp; Maraisa e Marília Mendonça\",\"zp\":{\"gs_ssp\":\"eJzj4tVP1zc0LIovMUvJSTIyYPQyyE0sOrw2JzNRITc1LyU_7_DyRIVUhdzEzMQiCKMo8fDa4kSFtMOLFZJzSpNSAQSfGMw\"}}],"
                + "[\"<b>marília mendonça <\\/b>e<b> maiara <\\/b>e<b> maraísa no domingão<\\/b>\",0,[10]],[\"marilia mendonca e <b>maiara<\\/b> e maraisa<b> no domingao do hulk<\\/b>\",0,[10]],"
                + "[\"marilia<b> mendonça <\\/b>e<b> maiara <\\/b>e maraisa<b> no altas horas<\\/b>\",0,[10]]],{\"i\":\"marilia mendonca e mayara e maraisa\",\"q\":\"bdHip6IbaY435JvLu4cN5M_colY\"}]"),
                new IntentParserResult("marilia mendonca e mayara e maraisa", 0, "marilia mendonca e mayara e maraisa"));
        assertEquals(parser.parse("stay 4)]}'", "[[[\"stay\",46,[512,433],{\"zh\":\"STAY\",\"zi\":\"Canção de Justin "
                + "Bieber e The Kid Laroi\",\"zp\":{\"gs_ssp\":\"eJzj4tVP1zc0LDSvSimrMjUyYPRiKS5JrAQARI8GXA\"},"
                + "\"zs\":\"https://encrypted-tbn0.gstatic"
                + ".com/images?q=tbn:ANd9GcRiX4239pW-llQO3SN2nUy88K1cwdQu7jBZpQs9PuLKnE0yp_jaODf6oa_ta1k&s=10\"}],"
                + "[\"stay with me\",46,[512,433],{\"zh\":\"Stay with Me\",\"zi\":\"Canção de Sam Smith\","
                + "\"zp\":{\"gs_ssp\":\"eJzj4tVP1zc0TEoqLDRKq8g1YPTiKS5JrFQozyzJUMhNBQCIpgl4\"},"
                + "\"zs\":\"https://encrypted-tbn0.gstatic"
                + ".com/images?q=tbn:ANd9GcTVdJ4Mp66JRMJA_wpJz5fQN_lDZgYUoOhiFjAWkIlVGWukcHCi0_jt5I2XLA&s=10\"}],"
                + "[\"stay close\",46,[433,131],{\"zh\":\"Stay Close\",\"zi\":\"Videogame\","
                + "\"zp\":{\"gs_ssp\":\"eJzj4tVP1zc0TDfITjExLs4yYPTiKi5JrFRIzskvTgUAbSIIZQ\"},"
                + "\"zs\":\"https://encrypted-tbn0.gstatic"
                + ".com/images?q=tbn:ANd9GcT5cldlLsPr-bYgY9Bk391bjK5dnbj1msnsoInrpsemBkSyd8bT1rjWNoE9KA&s=10\"}],"
                + "[\"stay<b> tradução<\\/b>\",0,[512,433]],[\"stay close netflix\",46,[433,131],{\"zh\":\"Stay "
                + "Close\",\"zi\":\"Programa de TV\","
                + "\"zp\":{\"gs_ssp\":\"eJzj4tVP1zc0LDIsqEiySDcyYPQSKi5JrFRIzskvTlXISy1Jy8msAADAvAub\"},"
                + "\"zs\":\"https://encrypted-tbn0.gstatic"
                + ".com/images?q=tbn:ANd9GcQykGsOKel5TlGr1H-m8FJB9zXch9tpbXYy5KFOR9uX-EqV1tc3QvxkaP2hCw&s=10\"}],"
                + "[\"stayc\",46,[512,433],{\"zh\":\"STAYC\",\"zi\":\"Girl group\","
                + "\"zp\":{\"gs_ssp\":\"eJzj4tVP1zc0LMyJN7aIzzYxYPRiLS5JrEwGAEhdBog\"},"
                + "\"zs\":\"https://encrypted-tbn0.gstatic"
                + ".com/images?q=tbn:ANd9GcSVaWfokL_POospSSS5msH7-a_ulVrS0fSyPqURYRr_JQ&s=10\"}],[\"stay rihanna\","
                + "46,[512,433],{\"zh\":\"Stay\",\"zi\":\"Canção de Rihanna\","
                + "\"zp\":{\"gs_ssp\":\"eJzj4tFP1zcsNM0ytTDJMTVg9OIpLkmsVCjKzEjMy0sEAHMoCKg\"},"
                + "\"zs\":\"https://encrypted-tbn0.gstatic"
                + ".com/images?q=tbn:ANd9GcQCRx8pWstpLBue3DhUaQ3xrYm0WE2kjO1QtAqFgDrj&s=10\"}],[\"stay<b> "
                + "strong<\\/b>\",0,[512,433]],[\"stay with me miki matsubara\",46,[512],{\"zh\":\"Mayonaka no Door /"
                + " Stay With Me\",\"zi\":\"Canção de Miki Matsubara\","
                + "\"zp\":{\"gs_ssp\":\"eJzj4tFP1zcsNM2qKsq1KDZg9JIuLkmsVCjPLMlQyE1VyM3MzlTITSwpLk1KLEoEADhvDvA\"},"
                + "\"zs\":\"https://encrypted-tbn0.gstatic"
                + ".com/images?q=tbn:ANd9GcSf_GGDUN_CjII1sudmgBQP5gBEl_W2ZyJL6FaKy31JFQ&s=10\"}],[\"stayin alive\","
                + "46,[512,433],{\"zh\":\"Stayin&#39; Alive\",\"zi\":\"Canção de Bee Gees\","
                + "\"zp\":{\"gs_ssp\":\"eJzj4tVP1zc0TDMyMy8xrzI2YPTiKS5JrMzMU0jMySxLBQB5yAjs\"},"
                + "\"zs\":\"https://encrypted-tbn0.gstatic"
                + ".com/images?q=tbn:ANd9GcTU31MiAS5qs3nPOfbHTufXzyZXyRSPjXSRLHNte8ZD0kgPhkYdNBFTT2cJZ3w&s=10\"}]],"
                + "{\"i\":\"biaot\",\"q\":\"kQSyteDjolYBmrCu1M-oIsQf3_E\"}]"), new IntentParserResult("stay", 1, "STAY ^ Justin Bieber e The Kid Laroi"));
        assertEquals(parser.parse("se%20todo%20mundo%20viu%20porque%20voce%20nao%20ta%20vendo 42)]}'", "[[[\"se todo "
                + "mundo viu porque você não tá vendo\",46,[512],{\"zh\":\"se todo mundo viu porque você não tá vendo\","
                + "\"zi\":\"Todo mundo menos você — Canção de Maiara &amp; Maraisa e Marília Mendonça\",\"zp\":{\"gs_ssp\":\"eJzj4tVP1zc0LKo0zqrKqDA0YPTSLU5VKMlPyVfILc0DkmWZpQoF-UWFpakKZfnJh1cp5B1enK9QcnihQlkqUB4Apn0Xhw\"},"
                + "\"zs\":\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSwpE_wIo6i3XD8-yhXjQYUjblGh1vBOwFsAbfRWdT7ZJKhVpWxiXN9qJsREsI&s=10\"}],"
                + "[\"se todo mundo viu porque<b> você não tá <\\/b>vendo<b> cifra<\\/b>\",0,[512]],"
                + "[\"se todo mundo viu porque<b> você não tá <\\/b>vendo<b> letra<\\/b>\",0,[22,30]],"
                + "[\"todo mundo viu porque<b> você não tá <\\/b>vendo todo<b> esse esforço<\\/b>\",0,[22,30]]],"
                + "{\"i\":\"se todo mundo viu porque voce nao ta vendo\",\"q\":\"yldfMBOaZoL_cPjsUqGdxy-P8Go\"}]"),
                new IntentParserResult("se todo mundo viu porque voce nao ta vendo", 1, "Todo mundo menos você ^ Maiara & Maraisa e Marília Mendonça"));
        assertEquals(parser.parse("maicon%20jakson 13)]}'", "[[[\"maicon jackson\",46,[433,10],{\"zh\":\"maicon "
                + "jackson\",\"zi\":\"Michael Jackson — Cantor-compositor\","
                + "\"zp\":{\"gs_ssp\":\"eJzj4tTP1TewtLCwTDdg9OLLTcxMzs9TyEpMzi7OzwMAZ4wIYQ\"},"
                + "\"zs\":\"https://encrypted-tbn0.gstatic"
                + ".com/images?q=tbn:ANd9GcQ_Rcxau6Pn2Q1UEkeNY5aDoa_hnY5FeZhuV7-AtzX12PmFa2LE7hKOPCHkVA&s=10\"}],"
                + "[\"maicon <b>jackson<\\/b><b> esta vivo<\\/b>\",0,[10]],[\"maicon <b>jackson<\\/b><b> "
                + "morreu<\\/b>\",0,[10]],[\"maicon <b>jackson<\\/b><b> morreu com quantos anos<\\/b>\",0,[10]],"
                + "[\"maicon jackson thriller\",46,[10],{\"zh\":\"maicon jackson thriller\",\"zi\":\"Thriller — Álbum"
                + " de estúdio de Michael Jackson\","
                + "\"zp\":{\"gs_ssp\":\"eJzj4tTP1TcwKi6qMjNg9BLPTcxMzs9TyEpMzi4G0iUZRZk5OalFANNgDG4\"},"
                + "\"zs\":\"https://encrypted-tbn0.gstatic"
                + ".com/images?q=tbn:ANd9GcQ0ItyX98KOgkv2OSY9kA-JGnCiZCStSmYrGAXc9hYKRA&s=10\"}],[\"maicon "
                + "<b>jackson<\\/b><b> musicas<\\/b>\",0,[10]],[\"maicon <b>jackson<\\/b><b> antes e depois<\\/b>\","
                + "0,[10]],[\"maicon <b>jackson<\\/b><b> morreu em que ano<\\/b>\",0,[10]],[\"maicon "
                + "<b>jackson<\\/b><b> idade<\\/b>\",0,[10]],[\"maicon <b>jackson<\\/b><b> não morreu<\\/b>\",0,"
                + "[10]]],{\"i\":\"maicon jakson\",\"o\":\"maicon <sc>jackson<\\/sc>\",\"p\":\"maicon "
                + "<se>jakson<\\/se>\",\"q\":\"JYiTunSGagSZQMZgpuLzVKkdm9s\"}]"),
                new IntentParserResult("maicon jakson", 0, "Michael Jackson"));
    }

    @Test
    public void removeKeySpaces() {
        RemoveKeySpace remover = new RemoveKeySpace();
        assertEquals(remover.evaluate("a b  c   d       "), "abcd");
        assertEquals(remover.evaluate("اغاني حزينه"), "اغانيحزينه");
        assertEquals(remover.evaluate("اغاني رومانسيه"), "اغانيرومانسيه");
        assertEquals(remover.evaluate("اغاني حب"), "اغانيحب");
        assertEquals(remover.evaluate("اغاني عيد ميلاد"), "اغانيعيدميلاد");
        assertEquals(remover.evaluate("اغاني اطفال"), "اغانياطفال");
        assertEquals(remover.evaluate("اغاني مهرجانات"), "اغانيمهرجانات");
        assertEquals(remover.evaluate("اغاني حزينه عن الفراق"), "اغانيحزينهعنالفراق");
        assertEquals(remover.evaluate("اغنيه رومانسيه"), "اغنيهرومانسيه");
        assertEquals(remover.evaluate("شيلات حزينه"), "شيلاتحزينه");
        assertEquals(remover.evaluate("اغنيه"), "اغنيه");
        assertEquals(remover.evaluate("اغاني حب ورومانسيه"), "اغانيحبورومانسيه");
        assertEquals(remover.evaluate("اغاني حزينه عن الحب"), "اغانيحزينهعنالحب");
        assertEquals(remover.evaluate("اغاني حزينه جدا"), "اغانيحزينهجدا");
        assertEquals(remover.evaluate("اغانى حزينه"), "اغانىحزينه");
        assertEquals(remover.evaluate("راب"), "راب");
        assertEquals(remover.evaluate("اغانى"), "اغانى");
        assertEquals(remover.evaluate("موال حزين"), "موالحزين");
        assertEquals(remover.evaluate("اغاني رومانسيه لحبيبي"), "اغانيرومانسيهلحبيبي");
        assertEquals(remover.evaluate("موسيقى"), "موسيقى");
        assertEquals(remover.evaluate("غاني حزينه"), "غانيحزينه");
        assertEquals(remover.evaluate("a sigh at twilight"), "asighattwilight");
        assertEquals(remover.evaluate("!magnic!, futuristic swaver"), "!magnic!,futuristicswaver");
        assertEquals(remover.evaluate("!magnic!," + "\r\n" + "futuristic swaver"), "!magnic!,futuristicswaver");
        assertEquals(remover.evaluate("agapo tin kiki  gamo to naftiko (sto peitharcheio m' esteile o lohagos) [feat. giannis giokarinis]"), "agapotinkikigamotonaftiko");
    }

    @Test
    public void removeTabs() {
        RemoveTab remover = new RemoveTab();
        assertEquals(remover.evaluate("a b c" + "\t" +  "d"  + "\t\t"), "a b cd");
        assertEquals(remover.evaluate("!magnic!, futuristic swaver"), "!magnic!, futuristic swaver");
    }

    @Test
    public void split() {
        //String test = " a b    cc  dd";
        String test = "abc     def    ";
        System.out.println("TypeName of String: "
                + test.getClass().getTypeName());
        String[] result = test.split("\\s", -1);
        System.out.println("size of result: " + result.length);
        for (String s : result) {
            System.out.println("element: " + s + "^");
        }

        String[] result1 = test.split("\\s+", -1);
        System.out.println("size of result: " + result1.length);
        for (String s : result1) {
            System.out.println("element: " + s + "^");
        }
    }

    @Test
    public void ConcatWithTab() {
        ConcatWithTab concater = new ConcatWithTab();
        assertEquals(concater.evaluate("Maycon", "Michael"), "Maycon\tMichael");
        assertEquals(concater.evaluate("music", "Michael"), "");
        assertEquals(concater.evaluate("!magnic!,futuristicswaver", "!magnic!, futuristic swaver"), "!magnic!,"
                + "futuristicswaver\t!magnic!, futuristic swaver");
        assertEquals(concater.evaluate("两个黄鹂鸣翠柳", "Michael"), "两个黄鹂鸣翠柳\tMichael");
        assertEquals(concater.evaluate("两个黄鹂鸣翠柳两个黄鹂", "Michael"), "");
        assertEquals(concater.evaluate("Michael", "两个黄鹂鸣翠柳两个黄鹂"), "Michael\t两个黄鹂鸣翠柳两个黄鹂");
    }

    @Test
    public void ConcatWithTabForLabels() {
        ConcatWithTabForLabels concater = new ConcatWithTabForLabels();
        assertEquals(concater.evaluate("Maycon", "Michael"), "Maycon\tMichael");
        assertEquals(concater.evaluate("music", "Michael"), "music\tMichael");
        assertEquals(concater.evaluate("!magnic!,futuristicswaver", "!magnic!, futuristic swaver"), "!magnic!,"
                + "futuristicswaver\t!magnic!, futuristic swaver");
    }

    @Test
    public void removePunctsArr() {
        RemovePunctsArr remover = new RemovePunctsArr();
        assertEquals(remover.evaluate(Arrays.asList(new String[]{"ÃãÀàÁáÂâÄäÅå Aa快手⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂!',;"})),
                Arrays.asList(new String[]{"ÃãÀàÁáÂâÄäÅå Aa快手⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂    "}));
    }

    @Test
    public void removeDiacriticsTest() {
        RemoveDiacritics remover = new RemoveDiacritics();
        assertEquals(remover.evaluate("ÃãÀàÁáÂâÄäÅå ẠặẶậẬḁḀ ḉḈ æAa mono oño moño tómaté", "spa"),
                "AaAaAaAaAaAa AaAaAaA cC æAa mono ono moño tómate");

        assertEquals(remover.evaluate("ÃãÀàÁáÂâÄäÅå ẠặẶậẬḁḀ ḉḈ æAa cara ará cará cára", "br"),
                "AaAaAaAaAaAa AaAaAaA cC æAa cara ara cará cará");
    }

    @Test
    public void removeDiacriticsArrTest() {
        RemoveDiacriticsArr remover = new RemoveDiacriticsArr();
        assertEquals(remover.evaluate(Arrays.asList(new String[]{"ÃãÀàÁáÂâÄäÅå ẠặẶậẬḁḀ ḉḈ æAa mono oño moño tómaté"}), "spa"),
                Arrays.asList(new String[]{"AaAaAaAaAaAa AaAaAaA cC æAa mono ono moño tómate"}));

        assertEquals(remover.evaluate(Arrays.asList(new String[]{"ÃãÀàÁáÂâÄäÅå ẠặẶậẬḁḀ ḉḈ æAa cara ará cará cára"}), "br"),
                Arrays.asList(new String[]{"AaAaAaAaAaAa AaAaAaA cC æAa cara ara cará cará"}));
    }
}
