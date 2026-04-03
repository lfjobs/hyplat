package hy.ea.marketing.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Created by Administrator on 2019-07-18.
 */
public class ProProxy implements BaseBean,java.io.Serializable{
    private String pkey;
    private String pid;
    private String ppid;
    private Date sjdate;
    private String typePpid;
    private String agenttype;
    private String investcomid;
    private String agentstate;
    private String texturl;
    private String state;

    public String getPkey() {
        return pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public Date getSjdate() {
        return sjdate;
    }

    public void setSjdate(Date sjdate) {
        this.sjdate = sjdate;
    }

    public String getTypePpid() {
        return typePpid;
    }

    public void setTypePpid(String typePpid) {
        this.typePpid = typePpid;
    }

    public String getAgenttype() {
        return agenttype;
    }

    public void setAgenttype(String agenttype) {
        this.agenttype = agenttype;
    }

    public String getInvestcomid() {
        return investcomid;
    }

    public void setInvestcomid(String investcomid) {
        this.investcomid = investcomid;
    }

    public String getAgentstate() {
        return agentstate;
    }

    public void setAgentstate(String agentstate) {
        this.agentstate = agentstate;
    }

    public String getTexturl() {
        return texturl;
    }

    public void setTexturl(String texturl) {
        this.texturl = texturl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProProxy proProxy = (ProProxy) o;

        if (pkey != null ? !pkey.equals(proProxy.pkey) : proProxy.pkey != null) return false;
        if (pid != null ? !pid.equals(proProxy.pid) : proProxy.pid != null) return false;
        if (ppid != null ? !ppid.equals(proProxy.ppid) : proProxy.ppid != null) return false;
        if (sjdate != null ? !sjdate.equals(proProxy.sjdate) : proProxy.sjdate != null) return false;
        if (typePpid != null ? !typePpid.equals(proProxy.typePpid) : proProxy.typePpid != null) return false;
        if (agenttype != null ? !agenttype.equals(proProxy.agenttype) : proProxy.agenttype != null) return false;
        if (investcomid != null ? !investcomid.equals(proProxy.investcomid) : proProxy.investcomid != null)
            return false;
        if (agentstate != null ? !agentstate.equals(proProxy.agentstate) : proProxy.agentstate != null) return false;
        if (texturl != null ? !texturl.equals(proProxy.texturl) : proProxy.texturl != null) return false;
        if (state != null ? !state.equals(proProxy.state) : proProxy.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pkey != null ? pkey.hashCode() : 0;
        result = 31 * result + (pid != null ? pid.hashCode() : 0);
        result = 31 * result + (ppid != null ? ppid.hashCode() : 0);
        result = 31 * result + (sjdate != null ? sjdate.hashCode() : 0);
        result = 31 * result + (typePpid != null ? typePpid.hashCode() : 0);
        result = 31 * result + (agenttype != null ? agenttype.hashCode() : 0);
        result = 31 * result + (investcomid != null ? investcomid.hashCode() : 0);
        result = 31 * result + (agentstate != null ? agentstate.hashCode() : 0);
        result = 31 * result + (texturl != null ? texturl.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
