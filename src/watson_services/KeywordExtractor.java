package watson_services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Keyword;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Keywords;

public class KeywordExtractor {
	private AlchemyLanguage service;
	
	public KeywordExtractor() throws IOException {
		Credentials creds = Credentials.loadCreds("credentials/alchemy_creds");
		service = new AlchemyLanguage();
		service.setApiKey(creds.getAPI());
	}
	
	public String getKeywords(String inputText) {
		Map<String,Object> params = new HashMap<>();
		params.put(AlchemyLanguage.TEXT, inputText);
		params.put(AlchemyLanguage.MAX_RETRIEVE, 40);
		Keywords keywords = service.getKeywords(params).execute();
		List<Keyword> keywordList = keywords.getKeywords();
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keywordList.size(); i++) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append(keywordList.get(i).getText());
		}
		
		return sb.toString();
	}
}
