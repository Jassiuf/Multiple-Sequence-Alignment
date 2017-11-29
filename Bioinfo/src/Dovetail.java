public class Dovetail extends Bio implements Alignment {
	String s1, s2;
	int mx, my;
	int[][] m;

	public Result align(String k, String l, Result result) {
		s1 = k.toLowerCase();
		s2 = l.toLowerCase();
		mx = 0;
		my = 0;
		int maxsofar = Integer.MIN_VALUE;
		int a = s1.length(), b = s2.length();
		m = new int[a + 1][b + 1];
		// prefill
		for (int i = 0; i < m[0].length; i++) {
			m[0][i] = 0;
		}
		for (int i = 0; i < m.length; i++) {
			m[i][0] = 0;
		}
		// build matrix
		for (int i = 1; i < m.length; i++) {
			for (int j = 1; j < m[0].length; j++) {
				int diag = m[i - 1][j - 1];
				int top = m[i - 1][j];
				int left = m[i][j - 1];
				int v = matrix[alphabet.get(s1.charAt(i - 1))][alphabet.get(s2.charAt(j - 1))];
				diag = diag + v;
				top = top + gap;
				left = left + gap;

				m[i][j] = Math.max(diag, Math.max(top, left));
				if (maxsofar < m[i][j] && (i == m.length - 1 || j == m[0].length - 1)) {
					maxsofar = m[i][j];
					mx = i;
					my = j;
				}

			}
		}

		// print(m);

		int traceback = traceback(result);
		return result;

	}

	public int traceback(Result result) {
		StringBuilder aa = new StringBuilder(), bb = new StringBuilder();

		for (int i = mx, j = my; (i > 0 || j > 0);) {
			// System.out.print( " " + m[i][j] + " > ");
			if (m[i][j] == 0) {
				result.setQueryStart(j);
				result.setSequenceStart(i);
				break;
			}
			int diag = m[i - 1][j - 1];
			int top = m[i - 1][j];
			int left = m[i][j - 1];
			int curr = m[i][j];
			// jab dono mismatch aur gap penalty different ho to go indirection
			// of lower magnitude of penalty
			int mismatch = matrix[alphabet.get(s1.charAt(i - 1))][alphabet.get(s2.charAt(j - 1))];
			boolean goleftright = true;
			if (gap < mismatch) {
				goleftright = false;
			}
			// System.out.println("i =" + i + " j= "+ j);
			if (i > 0 && curr == top + gap && goleftright) {
				aa.append(s1.charAt(--i));
				bb.append(".");
			} else if (j > 0 && curr == left + gap && goleftright) {
				bb.append(s2.charAt(--j));
				aa.append(".");
			} else if (i > 0 && j > 0) {
				aa.append(s1.charAt(--i));
				bb.append(s2.charAt(--j));
			}
		}
		result.setQuery(aa.reverse().toString());
		result.setSequence(bb.reverse().toString());
		result.setScore(m[mx][my]);

		return m[mx][my];

	}

}
