package hy.ea.bo.finance;

import java.util.Date;
import java.util.List;
import java.util.Map;

import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.plat.bo.BaseBean;

/**
 * DtProSetup entity. @author MyEclipse Persistence Tools
 */

public class ProSetup implements BaseBean {

    // Fields

    private String sukey;
    private String fcomName;
    private String fcomId;
    private String comId;
    private String ppid;
    private String ppname;
    private String efPrice;//出厂价
    private String rePrice;//零售价
    private String brokerage;//业务佣金
    private String proxyprice;//代理佣金和
    private String suid;
    private Date sjdate;
    private String tzType; //投资类型[设备投资类型 01:教练车 02:创客单车 03:超市 00:无]
    private String principal; //设计零售价责任人
    private String state;// 状态[00:正常/审核通过 01:删除 02:草稿 03:审核中 04:驳回]

    private Map<String, ProLayout> layout;
    private Map<String, ProSetupSub> setupSub;
    private List<BaseBean> pmlist;
    private List<Object> vallist;
    private List<Object> yjlist;//为佣金设计模块0功能准备
    private String photoPath;

    //修改时间ljc
    private Date editDate;
    private String showweixin;
    // Constructors

    /**
     * default constructor
     */
    public ProSetup() {
    }

    public ProSetup(String comId, String ppid, String ppname, String rePrice,
                    String photoPath) {
        super();
        this.comId = comId;
        this.ppid = ppid;
        this.ppname = ppname;
        this.rePrice = rePrice;
        this.photoPath = photoPath;
    }

    /**
     * full constructor
     */
    public ProSetup(String fcomName, String comId, String ppid, String efPrice,
                    String rePrice, String brokerage, String suid) {
        this.fcomName = fcomName;
        this.comId = comId;
        this.ppid = ppid;
        this.efPrice = efPrice;
        this.rePrice = rePrice;
        this.brokerage = brokerage;
        this.suid = suid;
    }

    // Property accessors

    public String getSukey() {
        return this.sukey;
    }

    public void setSukey(String sukey) {
        this.sukey = sukey;
    }

    public String getFcomName() {
        return this.fcomName;
    }

    public void setFcomName(String fcomName) {
        this.fcomName = fcomName;
    }

    public String getComId() {
        return this.comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getPpid() {
        return this.ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getEfPrice() {
        return this.efPrice;
    }

    public void setEfPrice(String efPrice) {
        this.efPrice = efPrice;
    }

    public String getRePrice() {
        return this.rePrice;
    }

    public void setRePrice(String rePrice) {
        this.rePrice = rePrice;
    }

    public String getBrokerage() {
        return this.brokerage;
    }

    public void setBrokerage(String brokerage) {
        this.brokerage = brokerage;
    }

    public String getSuid() {
        return this.suid;
    }

    public void setSuid(String suid) {
        this.suid = suid;
    }


    public String getFcomId() {
        return fcomId;
    }

    public void setFcomId(String fcomId) {
        this.fcomId = fcomId;
    }

    public String getPpname() {
        return ppname;
    }

    public void setPpname(String ppname) {
        this.ppname = ppname;
    }

    public Date getSjdate() {
        return sjdate;
    }

    public void setSjdate(Date sjdate) {
        this.sjdate = sjdate;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Map<String, ProLayout> getLayout() {
        return layout;
    }

    public void setLayout(Map<String, ProLayout> layout) {
        this.layout = layout;
    }

    public Map<String, ProSetupSub> getSetupSub() {
        return setupSub;
    }

    public void setSetupSub(Map<String, ProSetupSub> setupSub) {
        this.setupSub = setupSub;
    }

    public List<BaseBean> getPmlist() {
        return pmlist;
    }

    public void setPmlist(List<BaseBean> pmlist) {
        this.pmlist = pmlist;
    }

    public List<Object> getVallist() {
        return vallist;
    }

    public void setVallist(List<Object> vallist) {
        this.vallist = vallist;
    }

    public String getProxyprice() {
        return proxyprice;
    }

    public void setProxyprice(String proxyprice) {
        this.proxyprice = proxyprice;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public String getShowweixin() {
        return showweixin;
    }

    public void setShowweixin(String showweixin) {
        this.showweixin = showweixin;
    }

    public List<Object> getYjlist() {
        return yjlist;
    }

    public void setYjlist(List<Object> yjlist) {
        this.yjlist = yjlist;
    }

    public String getTzType() {
        return tzType;
    }

    public void setTzType(String tzType) {
        this.tzType = tzType;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}