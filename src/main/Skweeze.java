package main;
import helper_classes.RankedJobPosting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import org.apache.solr.client.solrj.SolrServerException;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.ClassifiedClass;

import watson_services.*;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

public class Skweeze {
	
	public static void main(String[] args) throws IOException {
		
		// specifies location of static files (like CSS)
		staticFileLocation("/public");
		
		File uploadDir = new File("upload");
		uploadDir.mkdir(); // in case it's not there
		
		// method called to display webpage at http://localhost:4567
		get("/", (request, response) -> {
	        Map<String, Object> attributes = new HashMap<>();
	        return new ModelAndView(attributes, "index.ftl");
	    }, new FreeMarkerEngine());
		
		// method called when file is uploaded
		post("/", (request, response) -> {		
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            
            Part part = request.raw().getPart("resume");
            String submittedName = part.getSubmittedFileName();
			Path tempFile = Files.createTempFile(uploadDir.toPath(), "res", getFileExtension(submittedName));
            
            try (InputStream input = part.getInputStream()) {
                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }
            
            File resume = tempFile.toFile();
            searchForJobs(resume);
            resume.delete();
            
            return "";
		});
		
		/*
		 * Since we're not using this, remember to terminate the program before starting another one,
		 * otherwise you need to terminate the process manually because the server can only start one
		 * process at 4567 at a time.
		 */
		//stop();
	}
	
	private static void searchForJobs(File resume) throws IOException, SolrServerException {
		DocConverter dc = new DocConverter();
		String resumeText = dc.convert(resume.getAbsolutePath());
		
		KeywordExtractor extractor = new KeywordExtractor();
		String keywords = extractor.getKeywords(resumeText, 40);

		CategoryClassifier classifier = new CategoryClassifier();
		List<ClassifiedClass> topClasses = classifier.getTopClasses(keywords, 3);
		
		printClassificationResults(topClasses);
		
		String k = extractor.getKeywords(resumeText, 5);
		RandR retrieve = new RandR();
		ArrayList<RankedJobPosting> topPostings = retrieve.rank(k, topClasses);

		printPostingResults(topPostings);
	}

	private static void printClassificationResults(List<ClassifiedClass> topClasses) {
		for (ClassifiedClass c : topClasses) {
			System.out.println("Class: " + c.getName() + " - Confidence: " + c.getConfidence());
		}
	}
	
	private static void printPostingResults(ArrayList<RankedJobPosting> topPostings) {
		for (int i = 0; i < 5; i++) {
			RankedJobPosting p = topPostings.get(i);
			System.out.println("Text: " + p.getPostingText());
			System.out.println("Ranking: " + p.getRanking());
			System.out.println("Category: " + p.getCategory());
		}
	}
	
	private static String getFileExtension(String fileName) {
		String ext;
		if (fileName.endsWith(".doc")) {
			ext = ".doc";
		}
		else if (fileName.endsWith("docx")){
			ext = ".docx";
		}
		else {
			ext = ".pdf";
		}
		return ext;
	}
}
