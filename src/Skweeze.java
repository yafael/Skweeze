import doc_conversion.*;
import watson_wrapper.*;


public class Skweeze {
	public static void main(String[] args) {
		DocConvert dc = new DocConvert();
		dc.convert("data/CV.pdf");
	}
}
