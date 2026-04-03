package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

import java.util.Date;


/**
 * Dtstatus entity. @author MyEclipse Persistence Tools
 */

public class Dtstatus  implements BaseBean,java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields    

     private String statekey;
     private String jnumorder;
     private String stordid;
     private String supplierstatus;//供应商状态/采购商状态 00:初始订单未拣货 01:拣货 02：发货 03：送货 04：物流 05:签收 06：收货 07：验货 08：入库 09：分金币
     private String paystatus;//支付状态 01：未支付 02：欠款 03：已支付
     private String cashierbillsid;//订单id
     private Date xddate;
     private Date jhdate;
     private Date fhdate;
     private Date shdate;
     private Date yhdate;


    // Constructors

    /** default constructor */
    public Dtstatus() {
    }

    
    /** full constructor */
    public Dtstatus(String jnumorder, String stordid, String supplierstatus, String paystatus, String cashierbillsid, Date xddate, Date jhdate, Date fhdate, Date shdate, Date yhdate) {
        this.jnumorder = jnumorder;
        this.stordid = stordid;
        this.supplierstatus = supplierstatus;
        this.paystatus = paystatus;
        this.cashierbillsid = cashierbillsid;
        this.xddate = xddate;
        this.jhdate = jhdate;
        this.fhdate = fhdate;
        this.shdate = shdate;
        this.yhdate = yhdate;
    }

   
    // Property accessors

    public String getStatekey() {
        return this.statekey;
    }
    
    public void setStatekey(String statekey) {
        this.statekey = statekey;
    }

    public String getJnumorder() {
        return this.jnumorder;
    }
    
    public void setJnumorder(String jnumorder) {
        this.jnumorder = jnumorder;
    }

    public String getStordid() {
        return this.stordid;
    }
    
    public void setStordid(String stordid) {
        this.stordid = stordid;
    }

    public String getSupplierstatus() {
        return this.supplierstatus;
    }
    
    public void setSupplierstatus(String supplierstatus) {
        this.supplierstatus = supplierstatus;
    }

    public String getPaystatus() {
        return this.paystatus;
    }
    
    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }

    public String getCashierbillsid() {
        return this.cashierbillsid;
    }
    
    public void setCashierbillsid(String cashierbillsid) {
        this.cashierbillsid = cashierbillsid;
    }

    public Date getXddate() {
        return this.xddate;
    }

    public void setXddate(Date xddate) {
        this.xddate = xddate;
    }

    public Date getJhdate() {
        return this.jhdate;
    }
    
    public void setJhdate(Date jhdate) {
        this.jhdate = jhdate;
    }

    public Date getFhdate() {
        return this.fhdate;
    }
    
    public void setFhdate(Date fhdate) {
        this.fhdate = fhdate;
    }

    public Date getShdate() {
        return this.shdate;
    }
    
    public void setShdate(Date shdate) {
        this.shdate = shdate;
    }

    public Date getYhdate() {
        return this.yhdate;
    }
    
    public void setYhdate(Date yhdate) {
        this.yhdate = yhdate;
    }
   








}