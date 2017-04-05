package main;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;
import helper_classes.RankedJobPosting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import com.google.gson.Gson;

public class SkweezeService {
	
	private File resume;
	
	public SkweezeService() {
		
		// specifies location of static files (like CSS)
		staticFileLocation("/public");
		
		File uploadDir = new File("upload");
		uploadDir.mkdir(); // in case it's not there		
		
		// called to display webpage at http://localhost:4567
		get("/", (request, response) -> renderContent("index.html"));
		
		// called when file is uploaded
		post("/", (request, response) -> {		
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            
            Part part = request.raw().getPart("resume");
            String submittedName = part.getSubmittedFileName();
			Path tempFile = Files.createTempFile(uploadDir.toPath(), "res", getFileExtension(submittedName));
            
            try (InputStream input = part.getInputStream()) {
                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }
            resume = tempFile.toFile();
            
            response.redirect("/results");
            return "";
		});
		
		// called from js code to get the job posting list
		post("/results", (request, response) -> {
			ArrayList<RankedJobPosting> postingList = JobFinder.searchForJobs(resume);
			resume.delete();
			Gson gson = new Gson();
			return gson.toJson(postingList);
	    });
		
		// called after resume is submitted
		get("/results", (request, response) -> renderContent("results.html"));
	}
	
	private String getFileExtension(String fileName) {
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
	
	private String renderContent(String htmlFile) throws URISyntaxException, IOException {
        URL url = getClass().getResource("../" + htmlFile);
        Path path = Paths.get(url.toURI());
        return new String(Files.readAllBytes(path), Charset.defaultCharset());
	}
}
