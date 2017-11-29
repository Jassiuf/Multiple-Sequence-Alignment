
public interface Alignment {

	public Result align(String a, String b, Result res);

	public int traceback(Result result);

}
