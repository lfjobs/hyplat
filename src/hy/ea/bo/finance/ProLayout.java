package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

/**
 * DtProLayout entity. @author MyEclipse Persistence Tools
 */

public class ProLayout implements BaseBean {

	// Fields

	private String lokey;     //主键
	private String loid;	  //伪主键
	private String suid;      //佣金比例设计ID
	private String bsid;      //佣金比例方案id
	private String meid;      //会员id
	private String proMoney;  //佣金比例金额
	private String pmStatu;	  //积分、佣金状态  00:佣金  01:积分
	private String comId;	  //公司ID
	private String statu;	  //资金比例状态 00:比例  01:金额
	
	private String staffname;
	private String acount;


	// Constructors

	/** default constructor */
	public ProLayout() {
	}

	

	public ProLayout(String lokey, String loid, String suid, String bsid,
			String meid, String bsName, String member, String proMoney,
			String pmStatu, String comId, String statu, String stata,
			String bsNumber, String meNumber) {
		super();
		this.lokey = lokey;
		this.loid = loid;
		this.suid = suid;
		this.bsid = bsid;
		this.meid = meid;
		this.proMoney = proMoney;
		this.pmStatu = pmStatu;
		this.comId = comId;
		this.statu = statu;
	}



	// Property accessors

	public String getLokey() {
		return this.lokey;
	}

	public void setLokey(String lokey) {
		this.lokey = lokey;
	}

	public String getSuid() {
		return this.suid;
	}

	public void setSuid(String suid) {
		this.suid = suid;
	}

	public String getProMoney() {
		return this.proMoney;
	}

	public void setProMoney(String proMoney) {
		this.proMoney = proMoney;
	}

	public String getPmStatu() {
		return this.pmStatu;
	}

	public void setPmStatu(String pmStatu) {
		this.pmStatu = pmStatu;
	}

	public String getComId() {
		return this.comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}

	public String getStatu() {
		return this.statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public String getLoid() {
		return this.loid;
	}

	public void setLoid(String loid) {
		this.loid = loid;
	}

	public String getBsid() {
		return bsid;
	}



	public void setBsid(String bsid) {
		this.bsid = bsid;
	}



	public String getMeid() {
		return meid;
	}



	public void setMeid(String meid) {
		this.meid = meid;
	}



	public String getStaffname() {
		return staffname;
	}



	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}



	public String getAcount() {
		return acount;
	}



	public void setAcount(String acount) {
		this.acount = acount;
	}

}