package lib_test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import main.SkweezeService;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*
 * Note that to run these properly on your machine, you need to have the ChromeDriver 
 * set up properly in your system.
 * 
 * You also would have to change the files that are used in the upload tests...
 * 
 * Basically, it's probably not worth messing with.
 */
public class Selenium_Test {

	private static WebDriver driver;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		new SkweezeService();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}
	
	@Before
	public void setUp() {
		driver.get("http://localhost:4567/");
	}
	
	@Test
	public void testUploadButtonIsEnabled() {
		WebElement chooseFileButton = driver.findElement(By.id("resumeUpload"));
		assertTrue(chooseFileButton.isEnabled());
	}
	
	@Test
	public void testUploadDocx() {
		WebElement chooseFileButton = driver.findElement(By.id("resumeUpload"));
		chooseFileButton.sendKeys("/Users/loganpatino/GoogleDrive/Documents/Patino-Logan-Resume.docx");
		chooseFileButton.submit();
		
		// checking that we got to the right page
		assertEquals("http://localhost:4567/results", driver.getCurrentUrl());
		
		// checking that we actually get some results
		List<WebElement> results = driver.findElements(By.className("collapse"));
		assertTrue(results.size() > 0);
	}
	
	@Test
	public void testButtonToHomePage() {
		driver.get("http://localhost:4567/results");
		driver.findElement(By.id("menu-toggle")).click();
		driver.findElement(By.id("homeButton")).click();
		assertEquals("http://localhost:4567/", driver.getCurrentUrl());
	}
	
	@Test
	public void testUploadPdf() {
		WebElement chooseFileButton = driver.findElement(By.name("resume"));
		chooseFileButton.sendKeys("/Users/loganpatino/GoogleDrive/Documents/Patino-Logan-Resume.pdf");
		chooseFileButton.submit();
		
		// checking that we got to the right page
		assertEquals("http://localhost:4567/results", driver.getCurrentUrl());
		
		// checking that we actually get some results
		List<WebElement> results = driver.findElements(By.className("collapse"));
		assertTrue(results.size() > 0);
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		driver.close();
	}
}
