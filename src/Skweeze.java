import watson_services.*;

public class Skweeze {
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		DocConvert dc = new DocConvert();
		dc.convert("data/CV.pdf");
	}
}
