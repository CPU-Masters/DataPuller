package dataStorage;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds all the data for a given stock
 * Data is made up of StockTimePoints
 * each of which represents market forces for a given time.
 * @author josh.benton
 *
 */
public class Stock {

	public Map<String,StockTimePoint> StockTimePoints = new HashMap<String,StockTimePoint>();
	
	
	public void loadInStockData(InputStream in) {
		
	}
	
	
	public class StockTimePoint {
		private String time;
	    private double open, high, low, close;
	    private long volume;
	    
	    
	}
}
