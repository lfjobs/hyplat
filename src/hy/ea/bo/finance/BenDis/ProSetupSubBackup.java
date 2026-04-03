package hy.ea.bo.finance.BenDis;

import java.util.Date;

import hy.plat.bo.BaseBean;

public class ProSetupSubBackup implements BaseBean {
    private String susbKey;
    private String susbId;
    private String ppid;        //产品id
    private String amount;        //代理商佣金
   /* private String brokerage;        //代理商佣金[新]*/
    private Date sjDate;        //设置时间
    private String subId;        //产品代理id
    private String typePpid;    //代理类别id
    private String agentType;    //招商类型 00贴牌01设备安装02省级代理03县级代理04村级代理
    private String investComId;    //招商公司id
    private String agentState;    //招商状态00招商中01招商结束
    private String textUrl;        //招商要求
    private String state;        //招商抢购状态 00未被抢购；01被已抢购
    private String pssbState;       //本条数据的状态 00:使用   11:停用

    public String getSusbKey() {
        return susbKey;
    }

    public void setSusbKey(String susbKey) {
        this.susbKey = susbKey;
    }

    public String getSusbId() {
        return susbId;
    }

    public void setSusbId(String susbId) {
        this.susbId = susbId;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Date getSjDate() {
        return sjDate;
    }

    public void setSjDate(Date sjDate) {
        this.sjDate = sjDate;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getTypePpid() {
        return typePpid;
    }

    public void setTypePpid(String typePpid) {
        this.typePpid = typePpid;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getInvestComId() {
        return investComId;
    }

    public void setInvestComId(String investComId) {
        this.investComId = investComId;
    }

    public String getAgentState() {
        return agentState;
    }

    public void setAgentState(String agentState) {
        this.agentState = agentState;
    }

    public String getTextUrl() {
        return textUrl;
    }

    public void setTextUrl(String textUrl) {
        this.textUrl = textUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPssbState() {
        return pssbState;
    }

    public void setPssbState(String pssbState) {
        this.pssbState = pssbState;
    }

}
