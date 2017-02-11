import watson_services.*;

public class Skweeze {
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		DocConvert dc = new DocConvert();
		String resumeText = dc.convert("data/CV.pdf");
		KeywordExtract extractor = new KeywordExtract();
		String concepts = extractor.getKeywords(resumeText);
		System.out.println(concepts);
	}
}
