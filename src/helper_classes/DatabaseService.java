package helper_classes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.tasks.Task;
import com.google.firebase.tasks.TaskCompletionSource;
import com.google.firebase.tasks.Tasks;


public class DatabaseService {
	
	private static HashMap<String, String> urlMap;
	private static HashMap<String, String[]> locationsMap;
	
	private static final String DATABASE_URL = "https://skweeze-39a59.firebaseio.com/";
	private static final String JOB_POSTINGS_PATH = "jobPostings";
	private static DatabaseReference service;
	private static DatabaseService instance;
	
	private DatabaseService() {}
	
	public static DatabaseService getInstance() {
		if (instance != null) {
			return instance;
		}
		
		instance = initialize();
		return instance;
	}
	
	public static DatabaseService initialize() {
		
		FileInputStream serviceAccount = null;
		try {
			serviceAccount = new FileInputStream("credentials/skweeze-39a59-firebase-adminsdk-wg951-065c471702.json");
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		FirebaseOptions options = new FirebaseOptions.Builder()
		  .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
		  .setDatabaseUrl(DATABASE_URL)
		  .build();

		FirebaseApp.initializeApp(options);
		
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference ref = database.getReference(JOB_POSTINGS_PATH);
		
		service = ref;
		
		/*
		 * The rest of this method gets the data from Firebase to make it quicker to access
		 * later. The weird stuff is just code to make sure that we don't
		 * skip ahead before we have the actual data and end up missing
		 * values on the UI.
		 */
		final TaskCompletionSource<Boolean> tcs = new TaskCompletionSource<>();
		Task<Boolean> tcsTask = tcs.getTask();
		
		retrieveData(tcs);
		
		try {
			Tasks.await(tcsTask);
		}
		catch(ExecutionException e){
		    e.printStackTrace();
		}
		catch (InterruptedException e){
		    e.printStackTrace();
		}
		
		return new DatabaseService();
	}
	
	private static void retrieveData(TaskCompletionSource<Boolean> tcs) {
		
		urlMap = new HashMap<>();
		locationsMap = new HashMap<>();
		
		/*
		 * This whole thing basically runs the database and gives us back
		 * results in the onDataChange method and an error in the onCancelled
		 * method.
		 */
		service.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				
				for (DataSnapshot postingSnapshot : dataSnapshot.getChildren()) {
					urlMap.put(postingSnapshot.getKey(), postingSnapshot.child("url").getValue().toString());
					
					ArrayList<String> locationList = new ArrayList<>();
					if (postingSnapshot.hasChild("locations")) {
						for (DataSnapshot locationSnapshot : postingSnapshot.child("locations").getChildren()) {
							//String locationName = locationSnapshot.getValue().toString().trim();
							//System.out.println(locationName);
							locationList.add(locationSnapshot.getValue().toString().trim());
						}
					}
					locationsMap.put(postingSnapshot.getKey(), locationList.toArray(new String[0]));
				}
				
				tcs.setResult(true);
			}

			@Override
			public void onCancelled(DatabaseError arg0) {
				
			}
			
		});
	}
	
	public String getUrl(String id) {
		return urlMap.get(id);
	}
	
	public String[] getLocations(String id) {
		return locationsMap.get(id);
	}
	
}
