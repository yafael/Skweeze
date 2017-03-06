package helper_classes;

public class RankedJobPosting {
	
	private String postingText;
	private Double ranking;
	private String category;
	
	public RankedJobPosting(String postingText, Double ranking, String category) {	
		this.postingText = postingText;
		this.ranking = ranking;
		this.category = category;
	}

	public String getPostingText() {
		return postingText;
	}
	
	public Double getRanking() {
		return ranking;
	}
	
	public String getCategory() {
		return category;
	}
}
