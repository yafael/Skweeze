package watson_services;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.TradeoffAnalytics;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.Dilemma;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.Problem;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.column.CategoricalColumn;
import com.ibm.watson.developer_cloud.tradeoff_analytics.v1.model.column.Column;

public class TradeoffHandler {
	private TradeoffAnalytics service;
	private Problem problem;
	private Dilemma dilemma;
	
	public TradeoffHandler() throws Exception{
		Credentials creds = Credentials.loadCreds("credentials/tradeoff_cred");
		service = new TradeoffAnalytics(creds.getUsername(), creds.getPassword());
		problem = new Problem("jobs");
		problem.setColumns(criteria());
		dilemma = service.dilemmas(problem).execute();
	}
	private List<Column> criteria() throws Exception{
		List<Column> criteria = new ArrayList<>();
		CategoricalColumn location = new CategoricalColumn();
		location.setKey("location");
		location.setRange(loadCities());
		criteria.add(location);
		return criteria;
	}
	private List<String> loadCities() throws Exception{
		List<String> cities = new ArrayList<>(289);
		Scanner s = new Scanner(new File("data/us_cities.csv"));
		while(s.hasNext()){
			String city = s.nextLine();
			String[] cityState = city.split(",");
			cities.add(cityState[0] + ", " + cityState[1]);
		}
		s.close();
		return cities;
	}
	public void something(){
		dilemma.getResolution().getSolutions();
	}
}
