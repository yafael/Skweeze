package helper_classes;


public class RankedJobPosting {

	private String id;
	private String title;
	private String jobSummary;
	private String qualificationSummary;
	private String url;
	private String[] locations;
	
	private int ranking;
	private String category;
	
	public String getId() {
		return id;
	}
	
	protected void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	
	protected void setTitle(String title) {
		this.title = title;
	}

	public String getJobSummary() {
		return jobSummary;
	}
	
	protected void setJobSummary(String jobSummary) {
		this.jobSummary = jobSummary;
	}

	public String getQualificationSummary() {
		return qualificationSummary;
	}
	
	protected void setQualificationSummary(String qualificationSummary) {
		this.qualificationSummary = qualificationSummary;
	}
	
	public String getUrl() {
		return url;
	}
	
	protected void setUrl(String url) {
		this.url = url;
	}
	
	public String[] getLocations() {
		return locations;
	}
	
	protected void setLocations(String[] locations) {
		this.locations = locations;
	}

	public int getRanking() {
		return ranking;
	}
	
	protected void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public String getCategory() {
		return category;
	}
	
	protected void setCategory(String category) {
		this.category = category;
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
