package com.kwai.unicodenormalizer;

/**
 * @author qianyujun <qianyujun@kuaishou.com>
 * Created on 2022-02-17
 */
public class EditDistance {
	public int minDistance(String word1, String word2) {
		if (word1.length() == 0)
			return word2.length();

		if (word2.length() == 0)
			return word1.length();

		int m = word1.length();
		int n = word2.length();

		int[][] array = new int [m + 1][n + 1];
		char[] word1Arr = word1.toCharArray();
		char[] word2Arr = word2.toCharArray();

		for (int i = 0; i < m + 1; i++)
			array[i][0] = i;

		for (int i = 0; i < n + 1; i++)
			array[0][i] = i;

		for (int i = 1; i < m + 1; i++) {
			for (int j = 1; j < n + 1; j++) {
				int a, b, c;
				if (word1Arr[i - 1] == word2Arr[j - 1]) {
					a = array[i - 1][j - 1];
				} else {
					a = array[i - 1][j - 1] + 1;
				}

				b = array[i - 1][j] + 1;
				c = array[i][j - 1] + 1;
				array[i][j] = min(min(a, b), c);
			}
		}


		return array[m][n];

	}


	private int min(int a, int b) {
		if (a < b)
			return a;

		return b;
	}
}
