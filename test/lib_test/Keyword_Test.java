package lib_test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import watson_services.Credentials;

import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.service.exception.UnauthorizedException;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Keyword_Test {

	@Test
	public void aTestCreds() throws UnauthorizedException {
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
