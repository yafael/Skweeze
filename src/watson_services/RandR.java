package watson_services;
import java.io.IOException;

import com.ibm.watson.developer_cloud.retrieve_and_rank.v1.*;
import com.ibm.watson.developer_cloud.retrieve_and_rank.v1.model.SolrCluster;

public class RandR {
	
	private RetrieveAndRank service;
	private String clusterID = "sccf7db882_4e76_49c0_bb10_0d484b286016";
	
	public RandR() throws IOException{
		Credentials creds = Credentials.loadCreds("credentials/randr_cred");
		service = new RetrieveAndRank();
		service.setUsernameAndPassword(creds.getUsername(), creds.getPassword());
	}
	
	public void rank(){
	}
}