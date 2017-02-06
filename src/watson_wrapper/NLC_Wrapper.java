package watson_wrapper;
import java.io.IOException;

public class NLC_Wrapper {
	@SuppressWarnings("unused")
	private Credentials creds;
	
	public NLC_Wrapper() throws IOException{
		creds = Credentials.loadCreds("credentials/nlc_cred");
	}
}
