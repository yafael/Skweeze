package watson_services;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import com.google.gson.Gson;

public class Credentials {
	private String url;
	private String username;
	private String password;
	private String date;
	private String apikey;
	
	public static Credentials loadCreds(String filePath) throws IOException{
		String fileContents = FileUtils.readFileToString(new File(filePath), "UTF-8");
		Credentials creds = new Gson().fromJson(fileContents, Credentials.class);
		return creds;
	}
	public String getUrl() {
		return url;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getDate(){
		return date;
	}
	public String getAPI(){
		return apikey;
	}
}
