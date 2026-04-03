package hy.plat.bo;

public class Province
        implements BaseBean
{
    private String sid;
    private String provinceID;
    private String province;

    public Province() {}

    public Province( String sid, String provinceID, String province)
    {
        this.sid = sid;
        this.provinceID = provinceID;
        this.province = province;
    }

    public String getSid()
    {
        return this.sid;
    }

    public void setSid(String sid)
    {
        this.sid = sid;
    }

    public String getProvinceID()
    {
        return this.provinceID;
    }

    public void setProvinceID(String provinceID)
    {
        this.provinceID = provinceID;
    }

    public String getProvince()
    {
        return this.province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }


}
