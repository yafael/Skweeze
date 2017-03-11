package main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

public class Skweeze {
	
	public static void main(String[] args) throws IOException {
		
		JobFinder finder = new JobFinder();
		
		// specifies location of static files (like CSS)
		staticFileLocation("/public");
		
		File uploadDir = new File("upload");
		uploadDir.mkdir(); // in case it's not there
		
		// called to display webpage at http://localhost:4567
		get("/", (request, response) -> {
	        Map<String, Object> attributes = new HashMap<>();
	        return new ModelAndView(attributes, "index.ftl");
	    }, new FreeMarkerEngine());
		
		// called when file is uploaded
		post("/", (request, response) -> {		
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            
            Part part = request.raw().getPart("resume");
            String submittedName = part.getSubmittedFileName();
			Path tempFile = Files.createTempFile(uploadDir.toPath(), "res", getFileExtension(submittedName));
            
            try (InputStream input = part.getInputStream()) {
                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }
            
            File resume = tempFile.toFile();
            finder.searchForJobs(resume);
            
            response.redirect("/results");
            resume.delete();
            
            return "";
		});
		
		// called after resume is submitted
		get("/results", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("jobPostings", finder.getTopPostings());
	        return new ModelAndView(attributes, "results.ftl");
	    }, new FreeMarkerEngine());
		
		/*
		 * Since we're not using this, remember to terminate the program before starting another one,
		 * otherwise you need to terminate the process manually because the server will complain that
		 * address 4567 is already in use.
		 */
		//stop();
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
