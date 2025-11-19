package rest.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Codes{

	@JsonProperty("ccg_id")
	private String ccgId;

	@JsonProperty("lau2")
	private String lau2;

	@JsonProperty("ced")
	private String ced;

	@JsonProperty("parliamentary_constituency_2024")
	private String parliamentaryConstituency2024;

	@JsonProperty("ccg")
	private String ccg;

	@JsonProperty("admin_ward")
	private String adminWard;

	@JsonProperty("parliamentary_constituency")
	private String parliamentaryConstituency;

	@JsonProperty("admin_county")
	private String adminCounty;

	@JsonProperty("pfa")
	private String pfa;

	@JsonProperty("admin_district")
	private String adminDistrict;

	@JsonProperty("lsoa")
	private String lsoa;

	@JsonProperty("msoa")
	private String msoa;

	@JsonProperty("nuts")
	private String nuts;

	@JsonProperty("parish")
	private String parish;

	public String getCcgId(){
		return ccgId;
	}

	public String getLau2(){
		return lau2;
	}

	public String getCed(){
		return ced;
	}

	public String getParliamentaryConstituency2024(){
		return parliamentaryConstituency2024;
	}

	public String getCcg(){
		return ccg;
	}

	public String getAdminWard(){
		return adminWard;
	}

	public String getParliamentaryConstituency(){
		return parliamentaryConstituency;
	}

	public String getAdminCounty(){
		return adminCounty;
	}

	public String getPfa(){
		return pfa;
	}

	public String getAdminDistrict(){
		return adminDistrict;
	}

	public String getLsoa(){
		return lsoa;
	}

	public String getMsoa(){
		return msoa;
	}

	public String getNuts(){
		return nuts;
	}

	public String getParish(){
		return parish;
	}
}