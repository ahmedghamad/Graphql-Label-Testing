package pojos.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationsItem{

	@JsonProperty("line")
	private int line;

	@JsonProperty("column")
	private int column;

	public int getLine(){
		return line;
	}

	public int getColumn(){
		return column;
	}
}