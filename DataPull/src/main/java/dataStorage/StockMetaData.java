package dataStorage;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;


public class StockMetaData {
String Information
,Symbol
,LastRefreshed
,Interval
,OutputSize
,TimeZone;

@JsonAlias("Meta Data")

@JsonProperty("1. Information")
public void setInfo(String info) {
	this.Information = info;
	System.out.println("Sent info to: " + info);
}

}
