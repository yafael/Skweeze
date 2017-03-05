package helper_classes;

public class RankedJobPosting {
	
	private String postingText;
	private Double ranking;
	
	public RankedJobPosting(String postingText, Double ranking) {	
		this.postingText = postingText;
		this.ranking = ranking;
	}

	public String getPostingText() {
		return postingText;
	}
	
	public Double getRanking() {
		return ranking;
	}
	
	public static int comparison(RankedJobPosting a, RankedJobPosting b) {
		if (a.ranking > b.ranking) {
			return -1;
		}
		else if (a.ranking < b.ranking) {
			return 1;
		}
		else {
			return 0;
		}
	}
}
