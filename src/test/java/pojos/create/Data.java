package pojos.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data{

	@JsonProperty("createLabel")
	private CreateLabel createLabel;

	public CreateLabel getCreateLabel(){
		return createLabel;
	}
}