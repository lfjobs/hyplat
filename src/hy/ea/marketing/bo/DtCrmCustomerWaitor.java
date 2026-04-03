package hy.ea.marketing.bo;

/**
 * DtCrmCustomerWaitor entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class DtCrmCustomerWaitor implements java.io.Serializable {

	// Fields

	private String rkey;
	private DtCrmCustomerActivity dtCrmCustomerActivity;
	private String staffid;
	private String staffname;

	// Constructors

	/** default constructor */
	public DtCrmCustomerWaitor() {
	}

	/** full constructor */
	public DtCrmCustomerWaitor(DtCrmCustomerActivity dtCrmCustomerActivity,
			String staffid, String staffname) {
		this.dtCrmCustomerActivity = dtCrmCustomerActivity;
		this.staffid = staffid;
		this.staffname = staffname;
	}

	// Property accessors

	public String getRkey() {
		return this.rkey;
	}

	public void setRkey(String rkey) {
		this.rkey = rkey;
	}

	public DtCrmCustomerActivity getDtCrmCustomerActivity() {
		return this.dtCrmCustomerActivity;
	}

	public void setDtCrmCustomerActivity(
			DtCrmCustomerActivity dtCrmCustomerActivity) {
		this.dtCrmCustomerActivity = dtCrmCustomerActivity;
	}

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getStaffname() {
		return this.staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

}