package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 快捷应用点击添加排行
 * Created by Administrator on 2017/11/7 0007.
 */
public class KuaiJieTianJia implements BaseBean,java.io.Serializable{

    private  String kuaiJiekey;
    private  String kuaiJieId;
    private  String companyId;  //公司id
    private String sccid;   //使用人sccid
    private Date mingciDate; //更换排行，按时间查询

    public KuaiJieTianJia() {
    }

    public KuaiJieTianJia(String kuaiJiekey, String kuaiJieId, String companyId, String sccid, Date mingciDate) {
        this.kuaiJiekey = kuaiJiekey;
        this.kuaiJieId = kuaiJieId;
        this.companyId = companyId;
        this.sccid = sccid;
        this.mingciDate = mingciDate;
    }

    public String getKuaiJiekey() {
        return kuaiJiekey;
    }

    public void setKuaiJiekey(String kuaiJiekey) {
        this.kuaiJiekey = kuaiJiekey;
    }

    public String getKuaiJieId() {
        return kuaiJieId;
    }

    public void setKuaiJieId(String kuaiJieId) {
        this.kuaiJieId = kuaiJieId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getSccid() {
        return sccid;
    }

    public void setSccid(String sccid) {
        this.sccid = sccid;
    }

    public Date getMingciDate() {
        return mingciDate;
    }

    public void setMingciDate(Date mingciDate) {
        this.mingciDate = mingciDate;
    }
}
