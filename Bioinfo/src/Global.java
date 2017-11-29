
public class Global extends Bio implements Alignment {
	String s1, s2;
	int[][] m;

	public Result align(String k, String l, Result result) {

		s1 = k.toLowerCase();
		s2 = l.toLowerCase();
		m = new int[s1.length() + 1][s2.length() + 1];

		// prefill
		for (int i = 1; i < m[0].length; i++) {
			m[0][i] = m[0][i-1] + gap;
		}
		for (int i = 1; i < m.length; i++) {
			m[i][0] =  m[i-1][0] + gap;
		}
		// build matrix
		for (int i = 1; i <= s1.length(); i++) {
			for (int j = 1; j <= s2.length(); j++) {
				int diag = m[i - 1][j - 1];
				int top = m[i - 1][j];
				int left = m[i][j - 1];

				int v = matrix[alphabet.get(s1.charAt(i - 1))][alphabet.get(s2.charAt(j - 1))];
				diag = diag + v;

				m[i][j] = Math.max(diag, Math.max(top + gap, left + gap));

			}
		}

		//print(m);

		int traceback = traceback(result);
		return result;

	}

	public int traceback(Result result) {
		StringBuilder aa = new StringBuilder(), bb = new StringBuilder();
		for (int i = m.length - 1, j = m[0].length - 1; i > 0 && j > 0;) {
			int diag = m[i - 1][j - 1];
			int top = m[i - 1][j];
			int left = m[i][j - 1];
			int curr = m[i][j];

			if (i > 0 && curr == top + gap) {
				
				aa.append(s1.charAt(--i));
				bb.append(".");
				
			} else if (j > 0 && curr == left + gap) {
				
				bb.append(s2.charAt(--j));
				aa.append(".");
				
			}

			else if (i > 0 && j > 0) {
				aa.append(s1.charAt(--i));
				bb.append(s2.charAt(--j));
			}
	
		}
		result.setQuery(aa.reverse().toString());
		result.setSequence(bb.reverse().toString());
		result.setQueryStart(0);
		result.setSequenceStart(0);
		result.setScore(m[s1.length()][s2.length()]);
		return m[s1.length()][s2.length()];
	}

}