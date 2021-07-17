package agoda.resources;

public class EndPoints {

	public static String getfamousQoutesAuthor(String tag, String author) {
		String endPoint = "/quotes?tags="+tag+"|wisdom&author="+author;
		return endPoint;
	}

	public static String getFamousQoutesPage(String tag, String page) {
		String endPoint = "/quotes?tags="+tag+"&page="+page+"";
		return endPoint;
	}
	

	
}
