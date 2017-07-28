package jobSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dataStorage.StockMetaData;

public class StockGrabThread implements Runnable {

	//Constants
	public static final String 
	BASE_ALPHA_VANTAGE_URL 	= "https://www.alphavantage.co/query?";
	
	//This key storage is temporary and should be updated to use the
	//config file when its implemented.
	public static final String ALPHA_VANTAGE_KEY = "XL1KFQFAAYUE2KRQ";
	
	public static final String INTERDAY = "TIME_SERIES_INTRADAY";
	
	public static final String MINUTE = "1min";
	
	public static final int DELAY_BETWEEN_STOCKS = 500;//in ms
	
	public StockGrabThread() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public static void getStockData(String stockSymbol) {
		//build the url
		String urlString = buildURL(stockSymbol);
		//grab the json (text)
		String json = "";
		 BufferedReader reader = null;
		    try {
		        URL url = new URL(urlString);
		        reader = new BufferedReader(new InputStreamReader(url.openStream()));
		        StringBuffer buffer = new StringBuffer();
		        int read;
		        char[] chars = new char[1024];
		        while ((read = reader.read(chars)) != -1)
		            buffer.append(chars, 0, read); 

		        json = buffer.toString();
		    } catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
		        if (reader != null)
					try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    }
		//process the json
		System.out.println(json);//Testing method
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			StockMetaData metaData = mapper.readValue(json,StockMetaData.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
/**
 * https://www.alphavantage.co/query?
 * function=TIME_SERIES_INTRADAY
 * &symbol=MSFT
 * &interval=1min
 * &apikey=demo
 * @param stockSymbol
 * @return
 */
	private static String buildURL(String stockSymbol) {
		String url = BASE_ALPHA_VANTAGE_URL; //...query?
		url+= "function=" + INTERDAY;
		url+= "&symbol="+stockSymbol;
		url+= "&interval="+MINUTE;
		url+= "&apikey=" + ALPHA_VANTAGE_KEY;
		//Add duration here
		return url;
	}

}
