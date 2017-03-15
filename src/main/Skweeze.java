package main;

import java.io.IOException;

public class Skweeze {
	
	public static void main(String[] args) throws IOException {
		
		new SkweezeService();
		
		/*
		 * Since we're not using this, remember to terminate the program before starting another one,
		 * otherwise you need to terminate the process manually because the server will complain that
		 * address 4567 is already in use.
		 */
		//stop();
	}
}
