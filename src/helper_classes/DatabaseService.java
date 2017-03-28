package helper_classes;

import com.firebase.client.Firebase;

public class DatabaseService {
	
	private static final String DATABASE_URL = "https://skweeze-39a59.firebaseio.com/jobPostings";
	public static Firebase service;
	
	public static Firebase GetDatabaseReference() {
		if (service != null) {
			return service;
		}
		
		Firebase firebase = new Firebase(DATABASE_URL);
		return firebase;
	}
	
}
