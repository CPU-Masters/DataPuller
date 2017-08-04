package dataStorage;

import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.json.*;

import org.atmosphere.util.ReaderInputStream;

import dataStorage.TimeSeries.Element;

/**
 * Holds all the data for a given stock
 * Data is made up of StockTimePoints
 * each of which represents market forces for a given time.
 * @author josh.benton
 *
 */
public class Stock {

	public Map<String,StockTimePoint> stockTimePoints = new HashMap<String,StockTimePoint>();
	
	private String info, symbol, lastRefreshed, interval, outputSize, timeZone;
	
	public void loadInStockData(InputStream in) {
		this.loadInStockData(Json.createReader(in));
	}
	
	public void loadInStockData(String in) {
		InputStream inStream = new ReaderInputStream(new StringReader(in));
		this.loadInStockData(Json.createReader(inStream));
	}
	
	public void loadInStockData(JsonReader reader) {
		JsonObject obj = reader.readObject();
		parseMetadata(obj.getJsonObject("Meta Data"));
		parseAllStocks(obj.getJsonObject("Time Series (" + interval + ")"));
	}
	
	private final void parseAllStocks(JsonObject series) {
	    for (String key : series.keySet()) {
	      stockTimePoints.put(key,new StockTimePoint(key, series.getJsonObject(key)));
	    }
	  }

	 private final void parseMetadata(JsonObject metadata) {
		    info = metadata.getString("1. Information");
		    symbol = metadata.getString("2. Symbol");
		    lastRefreshed = metadata.getString("3. Last Refreshed");
		    interval = metadata.getString("4. Interval");
		    outputSize = metadata.getString("5. Output Size");
		    timeZone = metadata.getString("6. Time Zone");
		  }

	public class StockTimePoint {
		private String time;
	    private double open, high, low, close;
	    private long volume;
	    
	    //private String info, symbol, lastRefreshed, interval, outputSize, timeZone;
	    public StockTimePoint(String time, JsonObject rawElem) {
	        this.time = time;
	        open = Double.parseDouble(rawElem.getString("1. open"));
	        high = Double.parseDouble(rawElem.getString("2. high"));
	        low = Double.parseDouble(rawElem.getString("3. low"));
	        close = Double.parseDouble(rawElem.getString("4. close"));
	        volume = Long.parseLong(rawElem.getString("5. volume"));
	      }
	    
	}
}
