package main;

import helper_classes.DatabaseService;

import java.io.IOException;

public class Skweeze {
	
	public static void main(String[] args) throws IOException {
	
		new SkweezeService();
		
		// This starts grabbing our db data from the start so we get minimum wait later
		DatabaseService.getInstance();
		
		/*
		 * Since we're not using this, remember to terminate the program before starting another one,
		 * otherwise you need to terminate the process manually because the server will complain that
		 * address 4567 is already in use.
		 */
		//stop();
	}
}
