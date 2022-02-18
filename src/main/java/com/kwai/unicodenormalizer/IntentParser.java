package com.kwai.unicodenormalizer;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.hadoop.yarn.webapp.hamlet.Hamlet.P;
import org.json.*;

/**
 * @author qianyujun <qianyujun@kuaishou.com>
 * Created on 2022-02-18
 */
public class IntentParser {
	public static Set<String> SINGERS = Stream.of(
			"Artista musical", "Cantora", "Dupla sertaneja", "Cantor",
			"Coro", "Cantor-compositor", "Grupo musical", "Rapper", "Músico",
			"Cantora-compositora", "Banda", "Musicista", "Grupo de música pop",
			"Quarteto", "Quinteto")
			.collect(Collectors.toCollection(HashSet::new));

	public static Set<String> MUSICS = Stream.of("Álbum musical de ", "Canção de ")
			.collect(Collectors.toCollection(HashSet::new));

	private String delimeter = " — ";

	public static class IntentParserResult {
		private String key;
		private int type;
		private String value;

		public IntentParserResult(String key, int type, String value) {
			this.key = key;
			this.type = type;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			IntentParserResult that = (IntentParserResult) o;
			return type == that.type &&
					key.equals(that.key) &&
					value.equals(that.value);
		}

		@Override
		public int hashCode() {
			return Objects.hash(key, type, value);
		}
	}

	public IntentParserResult parse(String query, String sug) {
		String key = "";
		int type = -1;
		String value = "";
		IntentParserResult empty = new IntentParserResult(key, type, value);

		boolean found = false;

		String[] queryArr = query.split("\\s+", -1);
		if (queryArr.length == 0) {
			return empty;
		}

		query = queryArr[0];
		try {
			query = java.net.URLDecoder.decode(query, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			// not going to happen - value came from JDK's own StandardCharsets
		}

		UnicodeNormalizerCaseNoDiacritics normalizer = new UnicodeNormalizerCaseNoDiacritics();
		query = normalizer.evaluate(query, false);

		RemoveKeySpace remover = new RemoveKeySpace();
		String queryNoSpace = remover.evaluate(query);
		//System.out.println("query: " + query);
		//System.out.println("queryNoSpace: " + queryNoSpace);

		EditDistance distance = new EditDistance();
		if (ConcatWithTab.LABELS.contains(queryNoSpace)) {
			return empty;
		}

		try {
			JSONArray arr = new JSONArray(sug);
			for (int i = 0; i < arr.length(); i++) {
				//System.out.println("arr length: " + arr.length());
				try {
					JSONArray item = arr.getJSONArray(i);
					//System.out.println("item length: " + item.length());

					for (int k = 0; k < item.length(); k++) {
						try {
							JSONArray sugArr = item.getJSONArray(k);
							//System.out.println("sugArr length: " + sugArr.length());
							for (int j = 0; j < sugArr.length(); j++) {
								try {
									JSONObject zp = sugArr.getJSONObject(j).getJSONObject("zp");
									String zh = sugArr.getJSONObject(j).getString("zh");
									String zi = sugArr.getJSONObject(j).getString("zi");

									//System.out.println("j: " + j);

									//System.out.println("zh: " + zh);
									//System.out.println("zi: " + zi);

									zh = zh.replaceAll("&amp;", "&");
									zi = zi.replaceAll("&amp;", "&");

									String[] cadidateArr = zi.split(delimeter, -1);
									String flag = cadidateArr[cadidateArr.length - 1];

									boolean matched = false;
									String matchedStr = "";
									for (String singerFlag : SINGERS) {
										if (flag.startsWith(singerFlag)) {
											matched = true;
											type = 0;
											matchedStr = singerFlag;
											break;
										}
									}

									if (!matched) {
										for (String musicFlag : MUSICS) {
											if (flag.startsWith(musicFlag)) {
												matched = true;
												type = 1;
												matchedStr = musicFlag;
												break;
											}
										}
									}

									if (!matched) {
										continue;
									}

									String sugNoSpace = remover.evaluate(normalizer.evaluate(zh, false));
									if (queryNoSpace.length() >  sugNoSpace.length() || distance.minDistance(queryNoSpace, sugNoSpace) < sugNoSpace.length() / 2) {
										found = true;
									}

									if (!found) {
										continue;
									}

									if (type == 1) {
										//music intent
										String music = "";
										String singer = "";
										if (cadidateArr.length > 1) {
											music = cadidateArr[0];
											singer = cadidateArr[1].substring(matchedStr.length());
										} else {
											music = zh;
											singer = cadidateArr[0].substring(matchedStr.length());
										}

										if (singer.length() > 0) {
											music = music + " ^ " + singer;
										}
										//System.out.println("query: " + query);
										//System.out.println("type: " + type);
										//System.out.println("value: " + music);
										return new IntentParserResult(query, type, music);
									} else if (type == 0) {
										// singer intent
										String intent;
										if (cadidateArr.length > 1) {
											intent = cadidateArr[0];
										} else {
											intent = zh;
										}

										//System.out.println("intent: " + intent);
										//System.out.println("distance: " + distance.minDistance(queryNoSpace, intent));
										if (distance.minDistance(queryNoSpace, intent) < intent.length() * 0.7) {
											//System.out.println("query: " + query);
											//System.out.println("type: " + type);
											//System.out.println("value: " + intent);
											return new IntentParserResult(query, type, intent);
										} else {
											//System.out.println("query: " + query);
											//System.out.println("type: " + type);
											//System.out.println("value: " + query);
											return new IntentParserResult(query, type, query);
										}
									}
								} catch (Exception e) {
								}
								if (found) {
									break;
								}
							}
						} catch (Exception e) {
						}
						if (found) {
							break;
						}
					}
				} catch (Exception e) {
				}
				if (found) {
					break;
				}
			}
		} catch (Exception e) {
		}

		return new IntentParserResult(key, type, value);
	}
}
