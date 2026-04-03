package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * 代理商和商品绑定表
 * @author lf
 * @version  2017/3/15 0015.
 */

public class TCuscomProduct implements BaseBean{
    private String cpid;
    private String cpkey;
    /**
     *产品id
     */
    private String ppid;
    /**
     * 时间
     */
    private Date sjdate;
    /**
     * 代理商id
     */
    private String sccsid;


    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    public String getCpkey() {
        return cpkey;
    }

    public void setCpkey(String cpkey) {
        this.cpkey = cpkey;
    }

    /**
     *产品id
     */
    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    /**
     * 时间
     */
    public Date getSjdate() {
        return sjdate;
    }

    public void setSjdate(Date sjdate) {
        this.sjdate = sjdate;
    }

    /**
     * 代理商id
     */
    public String getSccsid() {
        return sccsid;
    }

    public void setSccsid(String sccsid) {
        this.sccsid = sccsid;
    }
}
