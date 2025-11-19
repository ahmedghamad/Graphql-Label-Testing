package rest.pojos;



import com.fasterxml.jackson.annotation.JsonProperty;

public class SingleResult {

    @JsonProperty("country")
    private String country;

    @JsonProperty("codes")
    private Codes codes;

    @JsonProperty("ced")
    private String ced;

    @JsonProperty("parliamentary_constituency_2024")
    private String parliamentaryConstituency2024;

    @JsonProperty("ccg")
    private String ccg;

    @JsonProperty("latitude")
    private Object latitude;

    @JsonProperty("admin_county")
    private String adminCounty;

    @JsonProperty("msoa")
    private String msoa;

    @JsonProperty("primary_care_trust")
    private String primaryCareTrust;

    @JsonProperty("parish")
    private String parish;

    @JsonProperty("nhs_ha")
    private String nhsHa;

    @JsonProperty("date_of_introduction")
    private String dateOfIntroduction;

    @JsonProperty("longitude")
    private Object longitude;

    @JsonProperty("postcode")
    private String postcode;

    @JsonProperty("european_electoral_region")
    private String europeanElectoralRegion;

    @JsonProperty("parliamentary_constituency")
    private String parliamentaryConstituency;

    @JsonProperty("admin_ward")
    private String adminWard;

    @JsonProperty("eastings")
    private int eastings;

    @JsonProperty("pfa")
    private String pfa;

    @JsonProperty("lsoa")
    private String lsoa;

    @JsonProperty("admin_district")
    private String adminDistrict;

    @JsonProperty("quality")
    private int quality;

    @JsonProperty("nuts")
    private String nuts;

    @JsonProperty("outcode")
    private String outcode;

    @JsonProperty("northings")
    private int northings;

    @JsonProperty("incode")
    private String incode;

    @JsonProperty("region")
    private String region;

    public String getCountry(){
        return country;
    }

    public Codes getCodes(){
        return codes;
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

    public Object getLatitude(){
        return latitude;
    }

    public String getAdminCounty(){
        return adminCounty;
    }

    public String getMsoa(){
        return msoa;
    }

    public String getPrimaryCareTrust(){
        return primaryCareTrust;
    }

    public String getParish(){
        return parish;
    }

    public String getNhsHa(){
        return nhsHa;
    }

    public String getDateOfIntroduction(){
        return dateOfIntroduction;
    }

    public Object getLongitude(){
        return longitude;
    }

    public String getPostcode(){
        return postcode;
    }

    public String getEuropeanElectoralRegion(){
        return europeanElectoralRegion;
    }

    public String getParliamentaryConstituency(){
        return parliamentaryConstituency;
    }

    public String getAdminWard(){
        return adminWard;
    }

    public int getEastings(){
        return eastings;
    }

    public String getPfa(){
        return pfa;
    }

    public String getLsoa(){
        return lsoa;
    }

    public String getAdminDistrict(){
        return adminDistrict;
    }

    public int getQuality(){
        return quality;
    }

    public String getNuts(){
        return nuts;
    }

    public String getOutcode(){
        return outcode;
    }

    public int getNorthings(){
        return northings;
    }

    public String getIncode(){
        return incode;
    }

    public String getRegion(){
        return region;
    }
}