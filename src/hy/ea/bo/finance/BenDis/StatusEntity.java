package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class StatusEntity implements BaseBean, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String statekey;
    private String jnumorder;//订单编号
    private String stordid;//id
    private String supplierstatus;//供应商状态/采购商状态 01:拣货 02：发货 03：送货 04：物流 05:签收 06：收货 07：验货 08：入库 09：分金币
    private String paystatus;//支付状态 01：未支付 02：欠款 03：已支付
    private String cashierbillsid;//订单id
    private Date xddate;//下单时间
    private Date fkdate;//付款时间
    private Date jhdate;//拣货时间
    private Date fhdate;//发货时间
    private Date shdate;//送货时间
    private Date yhdate;//验货时间
    private Date fzdate;//分金币时间

    public String getStatekey() {
        return statekey;
    }

    public void setStatekey(String statekey) {
        this.statekey = statekey;
    }

    public String getJnumorder() {
        return jnumorder;
    }

    public void setJnumorder(String jnumorder) {
        this.jnumorder = jnumorder;
    }

    public String getStordid() {
        return stordid;
    }

    public void setStordid(String stordid) {
        this.stordid = stordid;
    }

    public String getSupplierstatus() {
        return supplierstatus;
    }

    public void setSupplierstatus(String supplierstatus) {
        this.supplierstatus = supplierstatus;
    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }

    public String getCashierbillsid() {
        return cashierbillsid;
    }

    public void setCashierbillsid(String cashierbillsid) {
        this.cashierbillsid = cashierbillsid;
    }

    public Date getXddate() {
        return xddate;
    }

    public void setXddate(Date xddate) {
        this.xddate = xddate;
    }

    public Date getJhdate() {
        return jhdate;
    }

    public void setJhdate(Date jhdate) {
        this.jhdate = jhdate;
    }

    public Date getFhdate() {
        return fhdate;
    }

    public void setFhdate(Date fhdate) {
        this.fhdate = fhdate;
    }

    public Date getShdate() {
        return shdate;
    }

    public void setShdate(Date shdate) {
        this.shdate = shdate;
    }

    public Date getYhdate() {
        return yhdate;
    }

    public void setYhdate(Date yhdate) {
        this.yhdate = yhdate;
    }

    public Date getFkdate() {
        return fkdate;
    }

    public void setFkdate(Date fkdate) {
        this.fkdate = fkdate;
    }

    public Date getFzdate() {
        return fzdate;
    }

    public void setFzdate(Date fzdate) {
        this.fzdate = fzdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusEntity that = (StatusEntity) o;
        return Objects.equals(statekey, that.statekey) &&
                Objects.equals(jnumorder, that.jnumorder) &&
                Objects.equals(stordid, that.stordid) &&
                Objects.equals(supplierstatus, that.supplierstatus) &&
                Objects.equals(paystatus, that.paystatus) &&
                Objects.equals(cashierbillsid, that.cashierbillsid) &&
                Objects.equals(xddate, that.xddate) &&
                Objects.equals(jhdate, that.jhdate) &&
                Objects.equals(fhdate, that.fhdate) &&
                Objects.equals(shdate, that.shdate) &&
                Objects.equals(yhdate, that.yhdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statekey, jnumorder, stordid, supplierstatus, paystatus, cashierbillsid, xddate, jhdate, fhdate, shdate, yhdate);
    }
}
