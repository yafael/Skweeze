package watson_wrapper;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public abstract class WatsonWrapper {
	protected Credentials creds;
	protected URLConnection conn;
	protected BufferedReader in;
	protected OutputStreamWriter out;
	
	public WatsonWrapper(String credFile) throws IOException{
		creds = Credentials.loadCreds(credFile);
		conn = new URL(creds.getUrl()).openConnection();
		conn.setDoOutput(true);
		in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		out = new OutputStreamWriter(conn.getOutputStream());
	}
}
