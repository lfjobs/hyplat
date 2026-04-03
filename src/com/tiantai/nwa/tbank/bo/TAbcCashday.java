package com.tiantai.nwa.tbank.bo;

import hy.plat.bo.BaseBean;

import java.sql.Timestamp;
import java.util.Date;

/**
 * TAbcCashday entity. @author MyEclipse Persistence Tools
 */

public class TAbcCashday implements BaseBean {

	// Fields

	private String pid;
	private String banksx;
	private String accountno;
	private Date getdatadate;
	private String prov;
	private String accno;
	private String cur;
	private Date trdate;
	private String timestab;
	private String trjrn;
	private String trtype;
	private String trbankno;
	private String accname;
	private String amtindex;
	private String oppprov;
	private String oppaccno;
	private String oppcur;
	private String oppname;
	private String oppbkname;
	private String cshindex;
	private Date errdate;
	private String errvchno;
	private Double amt;
	private Double bal;
	private Double preamt;
	private Double totchg;
	private String vouchertype;
	private String voucherprov;
	private String voucherbat;
	private String voucherno;
	private String custref;
	private String transcode;
	private String teller;
	private String vchno;
	private String abs;
	private String postscript;
	private String trfrom;

	// Constructors

	/** default constructor */
	public TAbcCashday() {
	}

	/** full constructor */
	public TAbcCashday(String banksx, String accountno, Date getdatadate,
			String prov, String accno, String cur, Date trdate,
			String timestab, String trjrn, String trtype, String trbankno,
			String accname, String amtindex, String oppprov, String oppaccno,
			String oppcur, String oppname, String oppbkname, String cshindex,
			Date errdate, String errvchno, Double amt, Double bal,
			Double preamt, Double totchg, String vouchertype,
			String voucherprov, String voucherbat, String voucherno,
			String custref, String transcode, String teller, String vchno,
			String abs, String postscript, String trfrom) {
		this.banksx = banksx;
		this.accountno = accountno;
		this.getdatadate = getdatadate;
		this.prov = prov;
		this.accno = accno;
		this.cur = cur;
		this.trdate = trdate;
		this.setTimestab(timestab);
		this.trjrn = trjrn;
		this.trtype = trtype;
		this.trbankno = trbankno;
		this.accname = accname;
		this.amtindex = amtindex;
		this.oppprov = oppprov;
		this.oppaccno = oppaccno;
		this.oppcur = oppcur;
		this.oppname = oppname;
		this.oppbkname = oppbkname;
		this.cshindex = cshindex;
		this.errdate = errdate;
		this.errvchno = errvchno;
		this.amt = amt;
		this.bal = bal;
		this.preamt = preamt;
		this.totchg = totchg;
		this.vouchertype = vouchertype;
		this.voucherprov = voucherprov;
		this.voucherbat = voucherbat;
		this.voucherno = voucherno;
		this.custref = custref;
		this.transcode = transcode;
		this.teller = teller;
		this.vchno = vchno;
		this.abs = abs;
		this.postscript = postscript;
		this.trfrom = trfrom;
	}

	// Property accessors

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getBanksx() {
		return this.banksx;
	}

	public void setBanksx(String banksx) {
		this.banksx = banksx;
	}

	public String getAccountno() {
		return this.accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public Date getGetdatadate() {
		return this.getdatadate;
	}

	public void setGetdatadate(Date getdatadate) {
		this.getdatadate = getdatadate;
	}

	public String getProv() {
		return this.prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getAccno() {
		return this.accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public String getCur() {
		return this.cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	public Date getTrdate() {
		return this.trdate;
	}

	public void setTrdate(Date trdate) {
		this.trdate = trdate;
	}

	

	public String getTrjrn() {
		return this.trjrn;
	}

	public void setTrjrn(String trjrn) {
		this.trjrn = trjrn;
	}

	public String getTrtype() {
		return this.trtype;
	}

	public void setTrtype(String trtype) {
		this.trtype = trtype;
	}

	public String getTrbankno() {
		return this.trbankno;
	}

	public void setTrbankno(String trbankno) {
		this.trbankno = trbankno;
	}

	public String getAccname() {
		return this.accname;
	}

	public void setAccname(String accname) {
		this.accname = accname;
	}

	public String getAmtindex() {
		return this.amtindex;
	}

	public void setAmtindex(String amtindex) {
		this.amtindex = amtindex;
	}

	public String getOppprov() {
		return this.oppprov;
	}

	public void setOppprov(String oppprov) {
		this.oppprov = oppprov;
	}

	public String getOppaccno() {
		return this.oppaccno;
	}

	public void setOppaccno(String oppaccno) {
		this.oppaccno = oppaccno;
	}

	public String getOppcur() {
		return this.oppcur;
	}

	public void setOppcur(String oppcur) {
		this.oppcur = oppcur;
	}

	public String getOppname() {
		return this.oppname;
	}

	public void setOppname(String oppname) {
		this.oppname = oppname;
	}

	public String getOppbkname() {
		return this.oppbkname;
	}

	public void setOppbkname(String oppbkname) {
		this.oppbkname = oppbkname;
	}

	public String getCshindex() {
		return this.cshindex;
	}

	public void setCshindex(String cshindex) {
		this.cshindex = cshindex;
	}

	public Date getErrdate() {
		return this.errdate;
	}

	public void setErrdate(Date errdate) {
		this.errdate = errdate;
	}

	public String getErrvchno() {
		return this.errvchno;
	}

	public void setErrvchno(String errvchno) {
		this.errvchno = errvchno;
	}

	public Double getAmt() {
		return this.amt;
	}

	public void setAmt(Double amt) {
		this.amt = amt;
	}

	public Double getBal() {
		return this.bal;
	}

	public void setBal(Double bal) {
		this.bal = bal;
	}

	public Double getPreamt() {
		return this.preamt;
	}

	public void setPreamt(Double preamt) {
		this.preamt = preamt;
	}

	public Double getTotchg() {
		return this.totchg;
	}

	public void setTotchg(Double totchg) {
		this.totchg = totchg;
	}

	public String getVouchertype() {
		return this.vouchertype;
	}

	public void setVouchertype(String vouchertype) {
		this.vouchertype = vouchertype;
	}

	public String getVoucherprov() {
		return this.voucherprov;
	}

	public void setVoucherprov(String voucherprov) {
		this.voucherprov = voucherprov;
	}

	public String getVoucherbat() {
		return this.voucherbat;
	}

	public void setVoucherbat(String voucherbat) {
		this.voucherbat = voucherbat;
	}

	public String getVoucherno() {
		return this.voucherno;
	}

	public void setVoucherno(String voucherno) {
		this.voucherno = voucherno;
	}

	public String getCustref() {
		return this.custref;
	}

	public void setCustref(String custref) {
		this.custref = custref;
	}

	public String getTranscode() {
		return this.transcode;
	}

	public void setTranscode(String transcode) {
		this.transcode = transcode;
	}

	public String getTeller() {
		return this.teller;
	}

	public void setTeller(String teller) {
		this.teller = teller;
	}

	public String getVchno() {
		return this.vchno;
	}

	public void setVchno(String vchno) {
		this.vchno = vchno;
	}

	public String getAbs() {
		return this.abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	public String getPostscript() {
		return this.postscript;
	}

	public void setPostscript(String postscript) {
		this.postscript = postscript;
	}

	public String getTrfrom() {
		return this.trfrom;
	}

	public void setTrfrom(String trfrom) {
		this.trfrom = trfrom;
	}

	public String getTimestab() {
		return timestab;
	}

	public void setTimestab(String timestab) {
		this.timestab = timestab;
	}

}