package watson_services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Concept;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Concepts;

public class ConceptExtract {

	private String key = "fa233cf98f9fe0c216cf53aab56025b2b1896f06";
	private AlchemyLanguage service;
	
	public ConceptExtract() {
		service = new AlchemyLanguage();
		service.setApiKey(key);
	}
	
	public String getConcepts(String inputText) {
		Map<String,Object> params = new HashMap<>();
		params.put(AlchemyLanguage.TEXT, inputText);
		Concepts concepts = service.getConcepts(params).execute();
		List<Concept> conceptList = concepts.getConcepts();
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < conceptList.size(); i++) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append(conceptList.get(i).getText());
		}
		
		return sb.toString();
	}
}
