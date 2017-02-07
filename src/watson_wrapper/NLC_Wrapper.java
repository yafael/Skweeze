package watson_wrapper;
import java.io.IOException;

public class NLC_Wrapper extends WatsonWrapper {
	public NLC_Wrapper() throws IOException{
		super("credentials/nlc_cred");
	}
}
