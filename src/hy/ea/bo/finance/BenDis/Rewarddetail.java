package hy.ea.bo.finance.BenDis;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2019-06-29.
 */
public class Rewarddetail implements BaseBean, ExcelBean,java.io.Serializable{
    private String rdkey;
    private String rdid;
    private String ppid;//打赏新闻id
    private String fbSccid;//发布帐号id
    private String fbAcount;//发布帐号
    private String rdSccid;//打赏帐号id
    private String rdAcount;//打赏帐号
    private Date rddate;//打赏时间
    private String rdmoney;//打赏金额
    private String payway;//支付方式[00微信支付，01，支付宝支付，02银联支付 03:线下转账 04:钱盒子支付  05：积分支付 06：现金支付（收银机公司用积分代付）07:金币支付]
    private String tradeStatus;//交易类型[00初始状态未生成订单，01已生成订单交易完成，02生产订单失败]
    private String tradeNo;//第三方交易号
    private String ordernum;//订单号

    public String getRdkey() {
        return rdkey;
    }

    public void setRdkey(String rdkey) {
        this.rdkey = rdkey;
    }

    public String getRdid() {
        return rdid;
    }

    public void setRdid(String rdid) {
        this.rdid = rdid;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getFbSccid() {
        return fbSccid;
    }

    public void setFbSccid(String fbSccid) {
        this.fbSccid = fbSccid;
    }

    public String getRdSccid() {
        return rdSccid;
    }

    public void setRdSccid(String rdSccid) {
        this.rdSccid = rdSccid;
    }

    public Date getRddate() {
        return rddate;
    }

    public void setRddate(Date rddate) {
        this.rddate = rddate;
    }

    public String getRdmoney() {
        return rdmoney;
    }

    public void setRdmoney(String rdmoney) {
        this.rdmoney = rdmoney;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getFbAcount() {
        return fbAcount;
    }

    public void setFbAcount(String fbAcount) {
        this.fbAcount = fbAcount;
    }

    public String getRdAcount() {
        return rdAcount;
    }

    public void setRdAcount(String rdAcount) {
        this.rdAcount = rdAcount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rewarddetail that = (Rewarddetail) o;

        if (rdkey != null ? !rdkey.equals(that.rdkey) : that.rdkey != null) return false;
        if (rdid != null ? !rdid.equals(that.rdid) : that.rdid != null) return false;
        if (ppid != null ? !ppid.equals(that.ppid) : that.ppid != null) return false;
        if (fbSccid != null ? !fbSccid.equals(that.fbSccid) : that.fbSccid != null) return false;
        if (rdSccid != null ? !rdSccid.equals(that.rdSccid) : that.rdSccid != null) return false;
        if (rddate != null ? !rddate.equals(that.rddate) : that.rddate != null) return false;
        if (rdmoney != null ? !rdmoney.equals(that.rdmoney) : that.rdmoney != null) return false;
        if (payway != null ? !payway.equals(that.payway) : that.payway != null) return false;
        if (tradeStatus != null ? !tradeStatus.equals(that.tradeStatus) : that.tradeStatus != null) return false;
        if (tradeNo != null ? !tradeNo.equals(that.tradeNo) : that.tradeNo != null) return false;
        if (ordernum != null ? !ordernum.equals(that.ordernum) : that.ordernum != null) return false;

        return true;
    }

    @Override
    public String[] properties() {
        return new String[0];
    }
}
