
public class Local extends Bio implements Alignment {

	String s1,s2;
	int[][] m;
	int mx,my;
	
	public Result align(String k, String l, Result result) {
		s1 = k;
		s2 = l;
		mx = 0;
		my = 0;
		int maxsofar = 0;
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
				m[i][j] = Math.max(diag, Math.max(0, Math.max( top + gap  , left + gap )));

				if (maxsofar < m[i][j]) {
					maxsofar = m[i][j];
					mx = i;
					my = j;
				}

			}
		}

		// print(m);
		
		int traceback = traceback(result);
		//return new Result( 0,0, result.getQuery(), result.getSequence(), -1, -1, traceback);
		return result;
	
	}
	
	public int traceback(Result result){
		StringBuilder aa = new StringBuilder(), bb = new StringBuilder();

		for (int i = mx, j = my; i > 0 || j > 0;) {
			if(m[i][j] == 0){
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
			//int mismatch  = matrix[alphabet.get(s1.charAt(i - 1))][alphabet.get(s2.charAt(j - 1))];
			int mismatch = -1;
			boolean goleftright = true;
			if (gap < mismatch) {
				goleftright = false;
			}
			// System.out.println("i =" + i + " j= "+ j + " " + m[i][j]);
			if (i > 0 && curr == top + gap) {
				aa.append(s1.charAt(--i));
				bb.append(".");
			} else if (j > 0 && curr == left + gap) {
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
		return (m[mx][my]);
	}
	

}