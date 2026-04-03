package hy.ea.bo.finance;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * 2017年06月26日
 *
 * @author Administrator
 * dt_pro_setup表的备份数据表
 */
public class ProSetupBackup implements BaseBean {
    private String subKey;
    private String subId;
    private String comName;
    private String comId;           //公司id
    private String ppid;           //产品id
    private String efPrice;           //出厂价
    private String rePrice;       //零售价
    private String brokerage;     //佣金
    private String fcomId;        //分公司id
    private String fcomName;      //分公司名称
    private String ppName;        //产品名称
    private Date sjDate;        //设计时间
    private Date editDate;        //修改时间
    private String principal;        //设计零售价责任人
    private String proxySumPrice; //代理佣金和
    private String tzType;        //设备投资类型 01:教练车 02:创客单车  00:无
    private String psbState;       //本条数据的状态 00:使用   11:停用
    private String state;// 状态[00:正常/审核通过 01:删除 02:草稿 03:审核中 04:驳回]

    public String getSubKey() {
        return subKey;
    }

    public void setSubKey(String subKey) {
        this.subKey = subKey;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getEfPrice() {
        return efPrice;
    }

    public void setEfPrice(String efPrice) {
        this.efPrice = efPrice;
    }

    public String getRePrice() {
        return rePrice;
    }

    public void setRePrice(String rePrice) {
        this.rePrice = rePrice;
    }

    public String getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(String brokerage) {
        this.brokerage = brokerage;
    }

    public String getFcomId() {
        return fcomId;
    }

    public void setFcomId(String fcomId) {
        this.fcomId = fcomId;
    }

    public String getFcomName() {
        return fcomName;
    }

    public void setFcomName(String fcomName) {
        this.fcomName = fcomName;
    }

    public String getPpName() {
        return ppName;
    }

    public void setPpName(String ppName) {
        this.ppName = ppName;
    }

    public Date getSjDate() {
        return sjDate;
    }

    public void setSjDate(Date sjDate) {
        this.sjDate = sjDate;
    }

    public String getProxySumPrice() {
        return proxySumPrice;
    }

    public void setProxySumPrice(String proxySumPrice) {
        this.proxySumPrice = proxySumPrice;
    }

    public String getTzType() {
        return tzType;
    }

    public void setTzType(String tzType) {
        this.tzType = tzType;
    }

    public String getPsbState() {
        return psbState;
    }

    public void setPsbState(String psbState) {
        this.psbState = psbState;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
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
