package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

/**
 * 客户报表编辑菜单
 * @author Administrator
 *
 */
public class CustomersForms  implements BaseBean{

	// Fields

	private String customersFormskey; 
	private String customersFormsid;
	private String companyid;    //公司
	
	private String latentCustomers; //潜在客户报表
	private String latentHighCustomers; //潜在优质客户
	private String customersContact; //联系方式报表
	private String customersort; //客户分类报表
	private String belongUnit; //所属单位报表
	private String customersFollow; //客户跟踪报表
	private String callingMessage; //呼叫信息报表
	private String personalPapers; //个人证件报表
	private String productInterest; //产品兴趣报表
	private String customersNeed; //客户需求报表
	private String customerSource; //客户来源报表
	private String customersRecord; //客户档案报表

	// Constructors

	/** default constructor */
	public CustomersForms() {
	}
	
	/** full constructor */
	public CustomersForms(String customersFormskey, String customersFormsid,
			String companyid, String latentCustomers,
			String latentHighCustomers, String customersContact,
			String customersort, String belongUnit, String customersFollow,
			String callingMessage, String personalPapers,
			String productInterest, String customersNeed,
			String customerSource, String customersRecord) {
		super();
		this.customersFormskey = customersFormskey;
		this.customersFormsid = customersFormsid;
		this.companyid = companyid;
		this.latentCustomers = latentCustomers;
		this.latentHighCustomers = latentHighCustomers;
		this.customersContact = customersContact;
		this.customersort = customersort;
		this.belongUnit = belongUnit;
		this.customersFollow = customersFollow;
		this.callingMessage = callingMessage;
		this.personalPapers = personalPapers;
		this.productInterest = productInterest;
		this.customersNeed = customersNeed;
		this.customerSource = customerSource;
		this.customersRecord = customersRecord;
	}

	public String getCustomersFormskey() {
		return customersFormskey;
	}

	public void setCustomersFormskey(String customersFormskey) {
		this.customersFormskey = customersFormskey;
	}

	public String getCustomersFormsid() {
		return customersFormsid;
	}

	public void setCustomersFormsid(String customersFormsid) {
		this.customersFormsid = customersFormsid;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getLatentCustomers() {
		return latentCustomers;
	}

	public void setLatentCustomers(String latentCustomers) {
		this.latentCustomers = latentCustomers;
	}

	public String getLatentHighCustomers() {
		return latentHighCustomers;
	}

	public void setLatentHighCustomers(String latentHighCustomers) {
		this.latentHighCustomers = latentHighCustomers;
	}

	public String getCustomersContact() {
		return customersContact;
	}

	public void setCustomersContact(String customersContact) {
		this.customersContact = customersContact;
	}

	public String getCustomersort() {
		return customersort;
	}

	public void setCustomersort(String customersort) {
		this.customersort = customersort;
	}

	public String getBelongUnit() {
		return belongUnit;
	}

	public void setBelongUnit(String belongUnit) {
		this.belongUnit = belongUnit;
	}

	public String getCustomersFollow() {
		return customersFollow;
	}

	public void setCustomersFollow(String customersFollow) {
		this.customersFollow = customersFollow;
	}

	public String getCallingMessage() {
		return callingMessage;
	}

	public void setCallingMessage(String callingMessage) {
		this.callingMessage = callingMessage;
	}

	public String getPersonalPapers() {
		return personalPapers;
	}

	public void setPersonalPapers(String personalPapers) {
		this.personalPapers = personalPapers;
	}

	public String getProductInterest() {
		return productInterest;
	}

	public void setProductInterest(String productInterest) {
		this.productInterest = productInterest;
	}

	public String getCustomersNeed() {
		return customersNeed;
	}

	public void setCustomersNeed(String customersNeed) {
		this.customersNeed = customersNeed;
	}

	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	public String getCustomersRecord() {
		return customersRecord;
	}

	public void setCustomersRecord(String customersRecord) {
		this.customersRecord = customersRecord;
	}
}