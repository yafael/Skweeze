package lib_test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.ClassifiedClass;
import com.ibm.watson.developer_cloud.retrieve_and_rank.v1.RetrieveAndRank;
import helper_classes.RankedJobPosting;
import watson_services.Credentials;
import watson_services.RandR;

public class RandR_Test {
	private static Credentials creds;
	private static RetrieveAndRank service;
	private static String clusterID = "sccf7db882_4e76_49c0_bb10_0d484b286016";
	
	@Before
	public void setUp() throws Exception {
		creds = Credentials.loadCreds("credentials/randr_cred");
		service = new RetrieveAndRank();
		service.setUsernameAndPassword(creds.getUsername(), creds.getPassword());
	}
	@Test
	public void testSolrWorks() { //TODO RandR constructor initalizes static RetrieveandRank object
		String url = service.getSolrUrl(clusterID);
		String username = creds.getUsername();
		String passwd = creds.getPassword();
		assertTrue(RandR.getSolrClient(url, username, passwd) != null);
	}
	@Test
	public void testAccountingResume() throws Exception {
		String test = "Tax Intern "
				+ "• Prepared individual income tax returns. "
				+ "• Prepared compilation financial statements and related bookkeeping. "
				+ "• Participated in several field audits. "
				+ "• Assisted on an Earnings & Profit/Tax Basis study.";
		ClassifiedClass accounting = new ClassifiedClass();
		accounting.setName("Accounting Budget and Finance");
		accounting.setConfidence(1.0);
		List<ClassifiedClass> classes = new ArrayList<>();
		classes.add(accounting);
		List<RankedJobPosting> jobs = new RandR().rank(test, classes);
		for(int i = 0;i < 10;i++){
			String title = jobs.get(i).getTitle();
			String msg = "Title is \"" + title + "\"";
			boolean financial = StringUtils.containsIgnoreCase(title, "financial");
			boolean fiscal = StringUtils.containsIgnoreCase(title, "fiscal");
			boolean accountant = StringUtils.containsIgnoreCase(title, "accountant");
			boolean auditor = StringUtils.containsIgnoreCase(title, "auditor");
			assertTrue(msg, financial || fiscal || accountant || auditor);
		}
	}
	@Test
	public void testReidResume() throws Exception { //TODO breaks
		String test = "TECHNICAL SKILLS "
				+ "Java (5+ yrs): Swing, JDBC, Reflection, DBUnit "
				+ "Other: C/C++, JS, Ruby, MATLAB, Python "
				+ "Working knowledge of Software Life Cycle (agile and traditional) "
				+ "Database: HSQL, Oracle, Postgres, SQLite "
				+ "Basic knowledge of how MapReduce works ";
		ClassifiedClass cse = new ClassifiedClass();
		cse.setName("Information Technology");
		cse.setConfidence(1.0);
		List<ClassifiedClass> classes = new ArrayList<>();
		classes.add(cse);
		List<RankedJobPosting> jobs = new RandR().rank(test, classes);
		System.out.println(jobs);
	}
	//TODO add one more case for Yash's resume
}
