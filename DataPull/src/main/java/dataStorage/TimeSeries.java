package dataStorage;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.json.*;


public class TimeSeries {
  // Represents a chunk of time.
  public static class Element {
    private String time;
    private double open, high, low, close;
    private long volume;

    public Element(String time, JsonObject rawElem) {
      this.time = time;
      open = Double.parseDouble(rawElem.getString("1. open"));
      high = Double.parseDouble(rawElem.getString("2. high"));
      low = Double.parseDouble(rawElem.getString("3. low"));
      close = Double.parseDouble(rawElem.getString("4. close"));
      volume = Long.parseLong(rawElem.getString("5. volume"));
    }

    public String toString() {
      return "TimeSeries.Element[time="+time+",open="+open+",high="+high
          +",low="+low+",close="+close+",volume="+volume+"]";
    }
  }

  // These could be parsed, but I'm going to leave them as strings.
  private String info, symbol, lastRefreshed, interval, outputSize, timeZone;
  // The actual data.
  private List<Element> elements = new ArrayList<Element>();

  public TimeSeries(InputStream in) {
    this(Json.createReader(in));
  }

  public TimeSeries(Reader reader) {
    this(Json.createReader(reader));
  }

  public TimeSeries(JsonReader reader) {
    this(reader.readObject());
  }

  public TimeSeries(JsonObject raw) {
    parseMetadata(raw.getJsonObject("Meta Data"));
    parseAllElements(raw.getJsonObject("Time Series (" + interval + ")"));
  }

  private final void parseMetadata(JsonObject metadata) {
    info = metadata.getString("1. Information");
    symbol = metadata.getString("2. Symbol");
    lastRefreshed = metadata.getString("3. Last Refreshed");
    interval = metadata.getString("4. Interval");
    outputSize = metadata.getString("5. Output Size");
    timeZone = metadata.getString("6. Time Zone");
  }

  private final void parseAllElements(JsonObject series) {
    for (String key : series.keySet()) {
      elements.add(new Element(key, series.getJsonObject(key)));
    }
  }

  public String toString() {
    return "TimeSeries[info="+info+",symbol="+symbol+",lastRefreshed="+lastRefreshed+",interval="+interval
        +",outputSize="+outputSize+",timeZone="+timeZone+"elements=List of "+elements.size()+"]";
  }

  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Please just provide a filename.");
      System.exit(1);
    }
    try (FileReader fileReader = new FileReader(args[0])) {
      TimeSeries series = new TimeSeries(fileReader);
      System.out.println(series);
      System.out.println("Elements:");
      for (Element element : series.elements) {
        System.out.println("  " + element);
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(2);
    }
  }
}