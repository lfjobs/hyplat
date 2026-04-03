package hy.ea.bo.company;

import hy.plat.bo.BaseBean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/1.
 */
public class IndustryRelation implements BaseBean,Serializable{
    private String industryKey;
    private String industryId;
    private String industryName;
    private String platforReturn;
    private String ppId;

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    public String getIndustryKey() {
        return industryKey;
    }

    public void setIndustryKey(String industryKey) {
        this.industryKey = industryKey;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getPlatforReturn() {
        return platforReturn;
    }

    public void setPlatforReturn(String platforReturn) {
        this.platforReturn = platforReturn;
    }
}
