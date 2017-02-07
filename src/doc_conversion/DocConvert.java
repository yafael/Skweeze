package doc_conversion;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import com.ibm.watson.developer_cloud.document_conversion.v1.DocumentConversion;
import com.ibm.watson.developer_cloud.document_conversion.v1.model.Answers;

public class DocConvert {
	public static void convert(String fileName){
		File resume = new File(fileName);
		DocumentConversion service = new DocumentConversion("2015-12-01","f9b79c9f-8a20-4a1f-aaa7-310d74edb32c", "vbPJHMIMOJYw");
		String[] outFile = fileName.split("\\.");
		String outName = outFile[0] + ".json";
		Answers text = service.convertDocumentToAnswer(resume).execute();
		try {
			PrintWriter output = new PrintWriter(outName);
			output.print(text);
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
