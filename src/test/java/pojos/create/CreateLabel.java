package pojos.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateLabel{

	@JsonProperty("label")
	private Label label;

	public Label getLabel(){
		return label;
	}
}