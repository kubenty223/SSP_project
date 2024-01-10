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
        final String serverUrl = "http://10.0.0.101:6789/load";
		logger.info("ODPALANKO WONTKU");
    	Thread thread = new Thread(new Runnable() {
	    	@Override
		    public void run() {
		        try {
		        	logger.info("ODPALAMY PYTANIE");
					sendAsyncHttpGetRequest(serverUrl);
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		});
    	
    	Thread thread2 = new Thread(new Runnable() {
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
		thread2.start();
		logger.info("WONTEK ODPALONY");
    }

    private static void sendAsyncHttpGetRequest(String url) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL serverUrl = new URL(url);
            connection = (HttpURLConnection) serverUrl.openConnection();
            connection.setRequestMethod("GET");
            
            logger.info("PYTAMY SOBIE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    logger.info("APPEND RESPONSEE");
                }
                logger.info("KONIEC CZYTANIA");
                in.close();
				logger.info("CZEMU TEN LOGGER SIE NIE WYSWIETLA VVVV?");
                logger.info("Response: " + response.toString());
            } else {
                logger.info("HTTP request failed with response code: " + responseCode);
            }
        } 
        catch (IOException e){
        	logger.info("EKSEPSZYN: ", e);
        }
         finally {
         	logger.info("KONCZYMY IMPREZE: ");
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
