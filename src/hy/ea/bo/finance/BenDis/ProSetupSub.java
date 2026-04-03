package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

import javax.persistence.*;
import java.util.Date;

/**
 * 代理商佣金设置表
 * @author lf
 * @version 2017/3/14 0014.
 */
public class ProSetupSub implements BaseBean {
    /**
     * 业务主键
     */
    private String susid;
    /**
     * 数据主键
     */
    private String suskey;
    /**
     * 产品id
     */
    private String ppid;
    /**
     * 代理商佣金
     */
    private String amount;
    /**
     * 设置时间
     */
    private Date sjdate;
    /**
     * 产品佣金表id
     */
    private String suid;
    /**
     * 代理类别
     */
    private String typePpid;

    //招商类型 00贴牌01设备安装02省级代理03县级代理04村级代理
    private String agentType;

    //招商公司id
    private String investComId;

    //招商状态00招商中01招商结束
    private String agentState;

    //招商要求
    private String textUrl;

    //招商抢购状态 00未被抢购；01被已抢购
    private String state;

    public String getSusid() {
        return susid;
    }

    public void setSusid(String susid) {
        this.susid = susid;
    }

    public String getSuskey() {
        return suskey;
    }

    public void setSuskey(String suskey) {
        this.suskey = suskey;
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

    public Date getSjdate() {
        return sjdate;
    }

    public void setSjdate(Date sjdate) {
        this.sjdate = sjdate;
    }

    public String getSuid() {
        return suid;
    }

    public void setSuid(String suid) {
        this.suid = suid;
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

}
