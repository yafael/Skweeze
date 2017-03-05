package watson_services;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.json.JSONException;
import org.json.JSONObject;

import com.ibm.watson.developer_cloud.retrieve_and_rank.v1.RetrieveAndRank;


public class RandR {

	private static RetrieveAndRank service;
	private static String clusterID = "sccf7db882_4e76_49c0_bb10_0d484b286016";
	private static HttpSolrClient solrClient;

	@SuppressWarnings("deprecation")
	private static HttpSolrClient getSolrClient(String uri, String username, String password) {
		return new HttpSolrClient(service.getSolrUrl(clusterID), createHttpClient(uri, username, password));
	}
	private static HttpClient createHttpClient(String uri, String username, String password) {
		final URI scopeUri = URI.create(uri);

		final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(new AuthScope(scopeUri.getHost(), scopeUri.getPort()),
				new UsernamePasswordCredentials(username, password));

		final HttpClientBuilder builder = HttpClientBuilder.create()
				.setMaxConnTotal(128)
				.setMaxConnPerRoute(32)
				.setDefaultRequestConfig(RequestConfig.copy(RequestConfig.DEFAULT).setRedirectsEnabled(true).build())
				.setDefaultCredentialsProvider(credentialsProvider);
		return builder.build();
	}
	public RandR() throws IOException{
		Credentials creds = Credentials.loadCreds("credentials/randr_cred");
		service = new RetrieveAndRank();
		service.setUsernameAndPassword(creds.getUsername(), creds.getPassword());
	}

	public void formatRanking(SolrDocumentList results){
		Map<Integer, Object> solrDocMap = new HashMap<Integer, Object>();
		int counter = 0;
		for(SolrDocument singleDoc : results)
		{
			solrDocMap.put(counter, new JSONObject(singleDoc));
			counter++;
		}
	}
	public SolrDocumentList rank(String keywords, String cluster) throws IOException, SolrServerException{
		Credentials creds = Credentials.loadCreds("credentials/randr_cred");
		solrClient = getSolrClient(service.getSolrUrl(clusterID),creds.getUsername(),creds.getPassword());
		SolrQuery query = new SolrQuery(keywords);
		SolrDocumentList response = solrClient.query(cluster, query).getResults();
		return response;
	}
}