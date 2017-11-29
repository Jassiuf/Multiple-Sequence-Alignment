
public class Result {
	private int id1;
	private int id2;
	private String query;
	private String sequence;
	private int queryStart;
	private int sequenceStart;
	


	public void setScore(int score) {
		this.score = score;
	}

	private int score;

	
	public Result() {
		super();
	}

	public Result(int id1, int id2, String query, String sequence, int queryStart, int sequenceStart, int score) {
		super();
		this.id1 = id1;
		this.id2 = id2;
		this.query = query;
		this.sequence = sequence;
		this.queryStart = queryStart;
		this.sequenceStart = sequenceStart;
		this.score = score;
	}

	public int getId1() {
		return id1;
	}

	public void setId1(int id1) {
		this.id1 = id1;
	}

	public int getId2() {
		return id2;
	}

	public void setId2(int id2) {
		this.id2 = id2;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public int getQueryStart() {
		return queryStart;
	}

	public void setQueryStart(int queryStart) {
		this.queryStart = queryStart;
	}

	public int getSequenceStart() {
		return sequenceStart;
	}

	public void setSequenceStart(int sequenceStart) {
		this.sequenceStart = sequenceStart;
	}

	public int getScore() {
		// TODO Auto-generated method stub
		return score;
	}

}
