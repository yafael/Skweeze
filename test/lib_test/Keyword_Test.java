package lib_test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import watson_services.Credentials;

import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.service.exception.UnauthorizedException;

public class Keyword_Test {

	@Test
	public void testCreds() throws UnauthorizedException {
		try {
			Credentials creds = Credentials.loadCreds("credentials/alchemy_creds");
			Map<String,Object> params = new HashMap<>();
			params.put(AlchemyLanguage.TEXT, "test");
			AlchemyLanguage service = new AlchemyLanguage(creds.getAPI());
			service.getConcepts(params);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
