package lib_test;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;

public class AlchemyTest {
	public static void main(String[] args) throws IOException{
		AlchemyLanguage service = new AlchemyLanguage();
		service.setApiKey("fa233cf98f9fe0c216cf53aab56025b2b1896f06");
		Map<String,Object> params = new HashMap<>();
		params.put(AlchemyLanguage.TEXT, FileUtils.readFileToString(new File("data/new_data.csv"),"UTF-8"));
		System.out.println(service.getConcepts(params).execute());
	}
}
