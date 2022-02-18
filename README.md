目前版本为1.1, 支持5种转换：
- normalize(String s, boolean removeEmoji)   字符标准化，参数removeEmoji代表是否去除表情符
- remove_nonlatin(String s)                  去除非拉丁字符，保留表情符等特殊符号
- remove_nonlatin_strict(String s)           去除非拉丁字符，不保留表情符等特殊符号
- remove_diacritics(String s, String bucket) 去除重音，但是对去除重音后意思会转变的单词，保留重音符号；参数bucket表示桶，目前支持spa和br
- remove_puncts(String s)                    去除标点符号

对这些转换都支持输入参数为字符串数组的形式。

# 使用方法：
~~~~sql
add jar viewfs:///home/oversea/data/udf/18/kwai-search-unicode-normalizer-udf-1.1-SNAPSHOT.jar;
CREATE TEMPORARY FUNCTION normalize AS 'com.kwai.unicodenormalizer.UnicodeNormalizer';
CREATE TEMPORARY FUNCTION remove_nonlatin AS 'com.kwai.unicodenormalizer.RemoveNonLatin';
CREATE TEMPORARY FUNCTION remove_nonlatin_strict AS 'com.kwai.unicodenormalizer.RemoveNonLatinStrict';
CREATE TEMPORARY FUNCTION remove_diacritics AS 'com.kwai.unicodenormalizer.RemoveDiacritics';
CREATE TEMPORARY FUNCTION remove_puncts AS 'com.kwai.unicodenormalizer.RemovePuncts';

CREATE TEMPORARY FUNCTION normalize_arr AS 'com.kwai.unicodenormalizer.UnicodeNormalizerArr';
CREATE TEMPORARY FUNCTION remove_nonlatin_arr AS 'com.kwai.unicodenormalizer.RemoveNonLatinArr';
CREATE TEMPORARY FUNCTION remove_nonlatin_strict_arr AS 'com.kwai.unicodenormalizer.RemoveNonLatinStrictArr';
CREATE TEMPORARY FUNCTION remove_diacritics_arr AS 'com.kwai.unicodenormalizer.RemoveDiacriticsArr';
CREATE TEMPORARY FUNCTION remove_puncts_arr AS 'com.kwai.unicodenormalizer.RemovePunctsArr';
~~~~
##### 1. normalize(String s, boolean removeEmoji)    字符标准化，参数removeEmoji代表是否去除表情符
~~~~sql
-- "一二三四五金木水火土"
select normalize("㊀㊁㊂㊃㊄㊎㊍㊌㊋㊏", true);
-- "arrepentida dela vida"
select normalize("𝕒𝕣𝕣𝕖𝕡𝕖𝕟𝕥𝕚𝕕𝕒 𝕕𝕖𝕝𝕒 𝕧𝕚𝕕𝕒", true);
-- ""
select normalize("👅💋😳", true);
-- "videos de porno"
select normalize("ｖｉｄｅｏｓ ｄｅ ｐｏｒｎｏ", true);
-- "VIDEOS DE PORNO"
select normalize("👅💋😳🆅🅸🅳🅴🅾🆂 🅳🅴 🅿🅾🆁🅽🅾", true);

-- "一二三四五金木水火土"
select normalize("㊀㊁㊂㊃㊄㊎㊍㊌㊋㊏", false);
-- "arrepentida dela vida"
select normalize("𝕒𝕣𝕣𝕖𝕡𝕖𝕟𝕥𝕚𝕕𝕒 𝕕𝕖𝕝𝕒 𝕧𝕚𝕕𝕒", false);
-- "👅💋😳"
select normalize("👅💋😳", false);
-- "videos de porno"
select normalize("ｖｉｄｅｏｓ ｄｅ ｐｏｒｎｏ", false);
-- "👅💋😳videos de porno"
select normalize("👅💋😳⒱⒤⒟⒠⒪⒮ ⒟⒠ ⒫⒪⒭⒩⒪", false);

-- ["一二三四五金木水火土","arrepentida dela vida","","videos de porno","VIDEOS DE PORNO"]
select normalize_arr(array("㊀㊁㊂㊃㊄㊎㊍㊌㊋㊏", "𝕒𝕣𝕣𝕖𝕡𝕖𝕟𝕥𝕚𝕕𝕒 𝕕𝕖𝕝𝕒 𝕧𝕚𝕕𝕒", "👅💋😳", "ｖｉｄｅｏｓ ｄｅ ｐｏｒｎｏ", "👅💋😳🆅🅸🅳🅴🅾🆂 🅳🅴 🅿🅾🆁🅽🅾"), true);
-- ["一二三四五金木水火土","arrepentida dela vida","👅💋😳","videos de porno","👅💋😳VIDEOS DE PORNO"]
select normalize_arr(array("㊀㊁㊂㊃㊄㊎㊍㊌㊋㊏", "𝕒𝕣𝕣𝕖𝕡𝕖𝕟𝕥𝕚𝕕𝕒 𝕕𝕖𝕝𝕒 𝕧𝕚𝕕𝕒", "👅💋😳", "ｖｉｄｅｏｓ ｄｅ ｐｏｒｎｏ", "👅💋😳🆅🅸🅳🅴🅾🆂 🅳🅴 🅿🅾🆁🅽🅾"), false);
~~~~

##### 2. remove_nonlatin(String s)    去除非拉丁字符，保留表情符等特殊符号
~~~~sql
-- "ÃãÀàÁáÂâÄäÅå ẠặẶậẬḁḀ ḉḈ æAa ⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂"
select remove_nonlatin("ÃãÀàÁáÂâÄäÅå ẠặẶậẬḁḀ ḉḈ æAa 快手 한국 おか ⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂");
-- ["ÃãÀàÁáÂâÄäÅå","ẠặẶậẬḁḀ","ḉḈ","æAa",""," ⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂"]
select remove_nonlatin_arr(array("ÃãÀàÁáÂâÄäÅå", "ẠặẶậẬḁḀ", "ḉḈ", "æAa", "快手", "한국 おか ⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂"));
~~~~

##### 3. remove_nonlatin_strict(String s)    去除非拉丁字符，不保留表情符等特殊符号
~~~~sql
-- "ÃãÀàÁáÂâÄäÅå ẠặẶậẬḁḀ ḉḈ æAa 123"
select remove_nonlatin_strict("ÃãÀàÁáÂâÄäÅå ẠặẶậẬḁḀ ḉḈ æAa 123 快手 한국 おか ⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂");
-- ["ÃãÀàÁáÂâÄäÅå","ẠặẶậẬḁḀ","ḉḈ","æAa","123",""," "]
select remove_nonlatin_strict_arr(array("ÃãÀàÁáÂâÄäÅå", "ẠặẶậẬḁḀ", "ḉḈ", "æAa", "123", "快手", "한국 おか ⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂"));
~~~~

##### 4. remove_diacritics(String s, String bucket)    去除重音，但是对去除重音后意思会转变的单词，保留重音符号；参数bucket表示桶，目前支持spa和br
~~~~sql
-- "AaAaAaAaAaAa AaAaAaA cC æAa mono ono moño tómate"
select remove_diacritics("ÃãÀàÁáÂâÄäÅå ẠặẶậẬḁḀ ḉḈ æAa mono oño moño tómaté", "spa");
-- ["AaAaAaAaAaAa","AaAaAaA","cC","æAa","mono","ono","moño","tómate"]
select remove_diacritics_arr(array("ÃãÀàÁáÂâÄäÅå", "ẠặẶậẬḁḀ", "ḉḈ", "æAa", "mono", "oño", "moño", "tómaté"), "spa");

-- "AAaAaAaAaAaAa AaAaAaA cC æAa cara ara cará cará"
select remove_diacritics("ÃÃãÀàÁáÂâÄäÅå ẠặẶậẬḁḀ ḉḈ æAa cara ará cará cára", "br");
-- ["AAaAaAaAaAaAa","AaAaAaA","cC","æAa","cara","ara","cará","cará"]
select remove_diacritics_arr(array("ÃÃãÀàÁáÂâÄäÅå", "ẠặẶậẬḁḀ", "ḉḈ", "æAa", "cara", "ará", "cará", "cára"), "br");
~~~~

##### 5. remove_puncts(String s)    去除标点符号
~~~~sql
-- "ÃãÀàÁáÂâÄäÅå Aa快手⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂"
select remove_puncts("ÃãÀàÁáÂâÄäÅå Aa快手⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂!',;");
-- ["ÃãÀàÁáÂâÄäÅå","Aa快手⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂 "]
select remove_puncts_arr(array("ÃãÀàÁáÂâÄäÅå", "Aa快手⒱⒤⒟⒠⒪⒮👅💋😳🆅🅸🅳🅴🅾🆂!',;"));
~~~~