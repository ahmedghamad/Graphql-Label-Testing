package rest.pojos;


import com.fasterxml.jackson.annotation.JsonProperty;

public class BulkResult {

    @JsonProperty("result")
    private SingleResult result;

    @JsonProperty("query")
    private String query;

    public SingleResult getResult(){
        return result;
    }

    public String getQuery(){
        return query;
    }
}