package rest.pojos;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BulkPostcodeResponse{

	@JsonProperty("result")
	private List<BulkResult> result;

	@JsonProperty("status")
	private int status;

	public List<BulkResult> getResult(){
		return result;
	}

	public int getStatus(){
		return status;
	}
}