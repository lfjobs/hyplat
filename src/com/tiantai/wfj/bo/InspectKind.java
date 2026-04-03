package com.tiantai.wfj.bo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
/**
 * 安全巡查分类
 */
public class InspectKind implements BaseBean, ExcelBean, java.io.Serializable {
    private String ikkey;
    private String ikid;
    private String siID;//安全巡查id (SafetyInspect)
    private String kid;//安全分类id(OASafeKind)
    private String kname;//安全分类名称

    public InspectKind(String ikkey, String ikid, String siID, String kid, String kname) {
        this.ikkey = ikkey;
        this.ikid = ikid;
        this.siID = siID;
        this.kid = kid;
        this.kname = kname;
    }

    public InspectKind() {
    }


    public String getIkkey() {
        return ikkey;
    }

    public void setIkkey(String ikkey) {
        this.ikkey = ikkey;
    }

    public String getIkid() {
        return ikid;
    }

    public void setIkid(String ikid) {
        this.ikid = ikid;
    }

    public String getSiID() {
        return siID;
    }

    public void setSiID(String siID) {
        this.siID = siID;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getKname() {
        return kname;
    }

    public void setKname(String kname) {
        this.kname = kname;
    }

    @Override
    public String toString() {
        return "InspectKind{" +
                "ikkey='" + ikkey + '\'' +
                ", ikid='" + ikid + '\'' +
                ", siID='" + siID + '\'' +
                ", kid='" + kid + '\'' +
                ", kname='" + kname + '\'' +
                '}';
    }

    @Override
    public String[] properties() {
        return new String[0];
    }
}
