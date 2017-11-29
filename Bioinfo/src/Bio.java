
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

public class Bio {

	static HashMap<Integer, String> queries = new HashMap<>();
	static HashMap<Integer, String> database = new HashMap<>();
	static HashMap<Character, Integer> alphabet = new HashMap<>();
	static int[][] matrix;
	static String ans = "";
	static int method;
	static int k;
	static int gap;
	static Charset utf8 = StandardCharsets.UTF_8;
	static TreeMap<Integer,Result> t = new TreeMap<>();
	static HashMap<Integer,Integer> qidmap = new HashMap<Integer,Integer>();
	static HashMap<Integer,Integer> didmap = new HashMap<Integer,Integer>();
	static List<Integer> top =new ArrayList<Integer>();

	public static void main(String[] args) {
		
		// read values from command line
		Bio obj = new Bio();
		obj.readValues(args);
		Alignment input = obj.checkAlignment(method);
		Result result = new Result();
		System.out.println();

		int globalmax = Integer.MIN_VALUE;
		for (int j = 1; j < queries.size(); j++) {
			long a=System.currentTimeMillis();
			int max = Integer.MIN_VALUE;
			for (int i = 1; i < database.size(); i++) {
				int qid = qidmap.get(j);
				int mid = didmap.get(i);
				Result r = input.align(queries.get(j), database.get(i), new Result(qid, mid, queries.get(j),  database.get(i),0 , 0, 0));
				t.put(r.getScore(), r);
				
				max = Math.max(max, r.getScore());
			}
			
			globalmax = Math.max(globalmax, max);
			//System.out.println("global max for query "+ j  +"  is  " + globalmax);
			long b=System.currentTimeMillis()-a;
		
		}
		
		int index = 1;
		for(int score: t.descendingKeySet()){
			if(index==k+1){
				break;
			}
			Result r = t.get(score);
			System.out.println(  "  score=   "+ score);
			System.out.println( r.getId1()  +  "   " + r.getQueryStart()    + "   " + r.getQuery()  );
			System.out.println( r.getId2()  +  "   " + r.getSequenceStart() + "   " + r.getSequence()  );

			index++;
			
		}
		
		String methodName = "";
		if (method == 1) {
			methodName = "global";
		} else if (method == 2) {
			methodName = "local";
		} else {
			methodName = "dovetail";
		}
		

	}

	public static void readArguments(String[] args) {
		try {
			String query = readFile(args[1], utf8);
			String[] queryArray  = query.toLowerCase().replaceAll("\\s+", "").split(">");
			

			String db = readFile(args[2], utf8);
			String[] databaseArray = db.toLowerCase().replaceAll("\\s+", "").split(">");
		

			String a = readFile(args[3], utf8);
			char[] alphabets = a.toLowerCase().toCharArray();


			for (int i = 0; i < alphabets.length; i++) 
				alphabet.put(alphabets[i], i);
			

			String m = readFile(args[4],utf8);
			String[] valueArray =  m.replaceAll("\\s+", ",").split(",");
			matrix = new int[alphabet.size()][alphabet.size()];

			for (int i = 1; i < queryArray.length; i++) {
				String q = queryArray[i].trim();
				
				setId(q, "query", i);
				int n = q.indexOf("(n)");
				q = q.substring(n+3).trim();

				if (q != null) {
					queries.put(i, q);
				}

			}

			for (int i = 1; i < databaseArray.length; i++) {
				String q = databaseArray[i].trim();
				setId(q, "database", i);
				int n = q.indexOf("(n)");
				q = q.substring(n+3).trim();
				//System.out.println( i + "   " + q.substring(0, 10));
				if (q != null) {
					database.put(i, q);
				}
			}

			for (int i = 0; i < alphabet.size(); i++) {
				for (int j = 0; j < alphabet.size(); j++) {
					String s = valueArray[i * alphabet.size() + j];
					matrix[i][j] = Integer.parseInt(s);

				}
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	static String readFile(String path, Charset encoding) throws IOException {

		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	static void print(int[][] m) {
		System.out.println("-------------------");
		System.out.println("Resultant Matrix");
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				System.out.print("      " + m[i][j] + "    ");
			}
			System.out.println("");
		}
	}

	public void readValues(String[] args) {
		method = Integer.parseInt(args[0]);
		k = Integer.parseInt(args[5]);
		gap = Integer.parseInt(args[6]);
		readArguments(args);
	}
	
	public static void setId(String s,String type, int index){
		int i = s.indexOf("hsa:");
		int j = i+4;
		while(Character.isDigit(s.charAt(j))){
			j++;
		}
		int id = Integer.parseInt(s.substring(i+4, j));
		if(type.equals("query")){
			qidmap.put(index, id);
		}else{
			didmap.put(index, id);
		}
	}

	public Alignment checkAlignment(int method) {
		Alignment inputAlignment = null;
		switch (method) {
		case 1:
			inputAlignment = new Global();
			break;
		case 2:
			inputAlignment = new Local();
			break;
		case 3:
			inputAlignment = new Dovetail();
			break;
		default:
			System.out.println("Invalid input for alignment technique");
		}
		return inputAlignment;
	}
}
