package helper_classes;

public class RankedJobPosting {

	private String title;
	private String jobSummary;
	private String qualificationSummary;
	private Double ranking;
	private String category;

	public RankedJobPosting(String postingText, Double ranking, String category) {	
		extractTextSections(postingText);
		this.ranking = ranking;
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public String getJobSummary() {
		return jobSummary;
	}

	public String getQualificationSummary() {
		return qualificationSummary;
	}

	public Double getRanking() {
		return ranking;
	}

	public String getCategory() {
		return category;
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

	private void extractTextSections(String postingText) {
		String[] titleAndRest = postingText.split("Job Summary");
		title = titleAndRest[0].trim();
		String[] jobAndQualSummary = titleAndRest[1].split("Qualification Summary");
		if(jobAndQualSummary.length>1){
			jobSummary = jobAndQualSummary[0].trim();
			qualificationSummary = jobAndQualSummary[1].trim();
		}else{
			jobSummary = jobAndQualSummary[0].trim();
		}
	}
}
