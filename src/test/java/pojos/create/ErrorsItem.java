package pojos.create;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorsItem{

	@JsonProperty("path")
	private List<String> path;

	@JsonProperty("locations")
	private List<LocationsItem> locations;

	@JsonProperty("type")
	private String type;

	@JsonProperty("message")
	private String message;

	public List<String> getPath(){
		return path;
	}

	public List<LocationsItem> getLocations(){
		return locations;
	}

	public String getType(){
		return type;
	}

	public String getMessage(){
		return message;
	}
}