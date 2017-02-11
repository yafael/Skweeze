import watson_services.*;

public class Skweeze {
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		DocConvert dc = new DocConvert();
		String resumeText = dc.convert("data/CV.pdf");
		ConceptExtract extractor = new ConceptExtract();
		String concepts = extractor.getConcepts(resumeText);
		System.out.println(concepts);
	}
}
