package helper_classes;

public class JobPostingCreator {
	
	public static RankedJobPosting createRankedJobPosting(String postingText, int ranking, String category) {
		RankedJobPosting posting = new RankedJobPosting();
		DatabaseService dbService = DatabaseService.getInstance();
		
		posting.setRanking(ranking);
		posting.setCategory(category);
		setTextSectionsAndId(posting, postingText);
		posting.setUrl(dbService.getUrl(posting.getId()));
		posting.setLocations(dbService.getLocations(posting.getId()));
		
		return posting;
	}
	
	private static void setTextSectionsAndId(RankedJobPosting posting, String postingText) {
		String[] titleAndRest = postingText.split("Job Summary");
		String[] titleAndId = titleAndRest[0].split("%");
		posting.setTitle(titleAndId[0].trim());
		posting.setId(titleAndId[1].trim());
		String[] jobAndQualSummary = titleAndRest[1].split("Qualification Summary");
		posting.setJobSummary(jobAndQualSummary[0].trim());
		if (jobAndQualSummary.length>1) {
			posting.setQualificationSummary(jobAndQualSummary[1].trim());
		}
	}
}
