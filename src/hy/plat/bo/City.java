package hy.plat.bo;

public class City implements BaseBean
{
    private String cid;
    private String cityID;
    private String city;
    private String father;

    public City() {}

    public City( String cid, String cityID, String city, String father)
    {
        this.cid = cid;
        this.cityID = cityID;
        this.city = city;
        this.father = father;
    }


    public String getCid()
    {
        return this.cid;
    }

    public void setCid(String cid)
    {
        this.cid = cid;
    }

    public String getCityID()
    {
        return this.cityID;
    }

    public void setCityID(String cityID)
    {
        this.cityID = cityID;
    }

    public String getCity()
    {
        return this.city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getFather()
    {
        return this.father;
    }

    public void setFather(String father)
    {
        this.father = father;
    }

    private String id;


}
