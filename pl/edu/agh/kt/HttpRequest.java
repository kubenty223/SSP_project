package pl.edu.agh.kt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
	public List<Integer> load = new ArrayList<>();

	private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    public void start() {
    	
    	Thread thread = new Thread(new Runnable() {
	    	@Override
		    public void run() {
	    		load.add(0);
	    		load.add(0);
	    		load.add(0);
	    		load.add(0);
	    		
	    		while(true)
	    		{
	    			Random random = new Random();
	    			load.set(0, random.nextInt(101));
	    			load.set(1, random.nextInt(101));
	    			load.set(2, random.nextInt(101));
	    			load.set(3, random.nextInt(101));
	    			try {
						TimeUnit.SECONDS.sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}
		        
		    }
		});
    	
		thread.start();
		logger.info("WONTEK ODPALONY");
    }
}
