package helper_classes;

import java.util.ArrayList;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class RankedJobPosting {

	private String id;
	private String title;
	private String jobSummary;
	private String qualificationSummary;
	private String url;
	private String[] locations;
	
	private Double ranking;
	private String category;

	public RankedJobPosting(String postingText, Double ranking, String category) {
		extractTextSectionsAndId(postingText);
		setDbInfo();
		this.ranking = ranking;
		this.category = category;
	}
	
	public String getId() {
		return id;
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
	
	public String getUrl() {
		return url;
	}
	
	public String[] getLocations() {
		return locations;
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

	private void extractTextSectionsAndId(String postingText) {
		String[] titleAndRest = postingText.split("Job Summary");
		String[] titleAndId = titleAndRest[0].split("%");
		title = titleAndId[0].trim();
		id = titleAndId[1].trim();
		String[] jobAndQualSummary = titleAndRest[1].split("Qualification Summary");
		jobSummary = jobAndQualSummary[0].trim();
		if (jobAndQualSummary.length>1) {
			qualificationSummary = jobAndQualSummary[1].trim();
		}
	}
	
	// this method is to set all the extra data not contained in the Watson clusters
	private void setDbInfo() {
		Firebase db = DatabaseService.GetDatabaseReference();
		
		// query to get the specific object in the database corresponding to the current posting
		Query idQuery = db.orderByKey().equalTo(id);
		
		/*
		 * This whole thing basically runs the whole query and gives us back
		 * results in the onDataChange method and an error in the onCancelled
		 * method.
		 */
		idQuery.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				
				/*
				 * We're iterating but really there's just one child since it's a unique ID.
				 * Here is where we add more info later on (like location).
				 */
				for (DataSnapshot postingSnapshot : dataSnapshot.getChildren()) {
					url = postingSnapshot.child("url").getValue().toString();
					
					ArrayList<String> locationList = new ArrayList<>();
					if (postingSnapshot.hasChild("locations")) {
						for (DataSnapshot locationSnapshot : postingSnapshot.child("locations").getChildren()) {
							//String locationName = locationSnapshot.getValue().toString().trim();
							//System.out.println(locationName);
							locationList.add(locationSnapshot.getValue().toString().trim());
						}
					}
					locations = locationList.toArray(new String[0]);
				}
				
			}
			
			@Override
			public void onCancelled(FirebaseError error) {
				System.out.println(error);
			}
			
		});
	}
}
