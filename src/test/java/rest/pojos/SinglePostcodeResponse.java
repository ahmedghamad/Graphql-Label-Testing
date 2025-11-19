package rest.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SinglePostcodeResponse{

    @JsonProperty("result")
    private SingleResult result;

    @JsonProperty("status")
    private int status;

    public SingleResult getResult(){
        return result;
    }

    public int getStatus(){
        return status;
    }
}