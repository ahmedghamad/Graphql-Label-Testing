package pojos.create;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateResponse{

	@JsonProperty("data")
	private Data data;

	@JsonProperty("errors")
	private List<ErrorsItem> errors;

	public Data getData(){
		return data;
	}

	public List<ErrorsItem> getErrors(){
		return errors;
	}
}