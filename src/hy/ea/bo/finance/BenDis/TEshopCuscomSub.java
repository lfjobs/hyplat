package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * 帐号代理商关系表
 * @author  lf
 * @version  2017/3/15 0015.
 */

public class TEshopCuscomSub  implements BaseBean {
    private String sccsid;
    private String sccskey;
    /**
     * 账号表id
     */
    private String sccid;
    /**
     * 账号
     */
    private String account;

    /**
     * 佣金分配代理类别产品id
     */
    private String tyepPpid;

    /**
     * 区域产品id
     */
    private String areappid;

    /**
     * 时间
     */
    private Date sjdate;

    public String getSccsid() {
        return sccsid;
    }

    public void setSccsid(String sccsid) {
        this.sccsid = sccsid;
    }

    public String getSccskey() {
        return sccskey;
    }

    public void setSccskey(String sccskey) {
        this.sccskey = sccskey;
    }

    /**
     * 账号表id
     * @return
     */
    public String getSccid() {
        return sccid;
    }

    public void setSccid(String sccid) {
        this.sccid = sccid;
    }

    /**
     * 账号
     * @return
     */
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 时间
     * @return
     */
    public Date getSjdate() {
        return sjdate;
    }

    public void setSjdate(Date sjdate) {
        this.sjdate = sjdate;
    }

    /**
     * 佣金分配代理类别产品id
     */
    public String getTyepPpid() {
        return tyepPpid;
    }

    public void setTyepPpid(String tyepPpid) {
        this.tyepPpid = tyepPpid;
    }

    /**
     * 区域产品id
     */
    public String getAreappid() {
        return areappid;
    }

    public void setAreappid(String areappid) {
        this.areappid = areappid;
    }
}
