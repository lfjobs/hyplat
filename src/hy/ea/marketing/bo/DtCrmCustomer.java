package hy.ea.marketing.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * DtCrmCustomer entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class DtCrmCustomer implements java.io.Serializable,BaseBean {

	// Fields

	private String customerkey;
	private String customerid;
	private String customercode;
	private String customername;
	private String status;
	private String usednmae;
	private String sex;
	private String idtype;
	private String identitycard;
	private String reference;
	private Date verifytime;
	private String ddesc;
	private String birthday;
	private String address;
	private String area;
	private String photo;
	private String productid;
	private String staffid;
	private String groupcompanysn;
	private Set dtCrmCustomerOffers = new HashSet(0);
	private Set dtCrmCustomerCompetitives = new HashSet(0);
	private Set dtCrmCustomerActivities = new HashSet(0);
	private Set dtCrmCustomerProducts = new HashSet(0);

	// Constructors

	/** default constructor */
	public DtCrmCustomer() {
	}

	/** minimal constructor */
	public DtCrmCustomer(String customerid) {
		this.customerid = customerid;
	}

	/** full constructor */
	public DtCrmCustomer(String customerid, String customercode,
			String customername, String status, String usednmae, String sex,
			String idtype, String identitycard, String reference,
			Date verifytime, String ddesc, String birthday, String address,
			String area, String photo, String productid, String staffid,
			String groupcompanysn, Set dtCrmCustomerOffers,
			Set dtCrmCustomerCompetitives, Set dtCrmCustomerActivities,
			Set dtCrmCustomerProducts) {
		this.customerid = customerid;
		this.customercode = customercode;
		this.customername = customername;
		this.status = status;
		this.usednmae = usednmae;
		this.sex = sex;
		this.idtype = idtype;
		this.identitycard = identitycard;
		this.reference = reference;
		this.verifytime = verifytime;
		this.ddesc = ddesc;
		this.birthday = birthday;
		this.address = address;
		this.area = area;
		this.photo = photo;
		this.productid = productid;
		this.staffid = staffid;
		this.groupcompanysn = groupcompanysn;
		this.dtCrmCustomerOffers = dtCrmCustomerOffers;
		this.dtCrmCustomerCompetitives = dtCrmCustomerCompetitives;
		this.dtCrmCustomerActivities = dtCrmCustomerActivities;
		this.dtCrmCustomerProducts = dtCrmCustomerProducts;
	}

	// Property accessors

	public String getCustomerkey() {
		return this.customerkey;
	}

	public void setCustomerkey(String customerkey) {
		this.customerkey = customerkey;
	}

	public String getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getCustomercode() {
		return this.customercode;
	}

	public void setCustomercode(String customercode) {
		this.customercode = customercode;
	}

	public String getCustomername() {
		return this.customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsednmae() {
		return this.usednmae;
	}

	public void setUsednmae(String usednmae) {
		this.usednmae = usednmae;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdtype() {
		return this.idtype;
	}

	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}

	public String getIdentitycard() {
		return this.identitycard;
	}

	public void setIdentitycard(String identitycard) {
		this.identitycard = identitycard;
	}

	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Date getVerifytime() {
		return this.verifytime;
	}

	public void setVerifytime(Date verifytime) {
		this.verifytime = verifytime;
	}

	public String getDdesc() {
		return this.ddesc;
	}

	public void setDdesc(String ddesc) {
		this.ddesc = ddesc;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getProductid() {
		return this.productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getStaffid() {
		return this.staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getGroupcompanysn() {
		return this.groupcompanysn;
	}

	public void setGroupcompanysn(String groupcompanysn) {
		this.groupcompanysn = groupcompanysn;
	}

	public Set getDtCrmCustomerOffers() {
		return this.dtCrmCustomerOffers;
	}

	public void setDtCrmCustomerOffers(Set dtCrmCustomerOffers) {
		this.dtCrmCustomerOffers = dtCrmCustomerOffers;
	}

	public Set getDtCrmCustomerCompetitives() {
		return this.dtCrmCustomerCompetitives;
	}

	public void setDtCrmCustomerCompetitives(Set dtCrmCustomerCompetitives) {
		this.dtCrmCustomerCompetitives = dtCrmCustomerCompetitives;
	}

	public Set getDtCrmCustomerActivities() {
		return this.dtCrmCustomerActivities;
	}

	public void setDtCrmCustomerActivities(Set dtCrmCustomerActivities) {
		this.dtCrmCustomerActivities = dtCrmCustomerActivities;
	}

	public Set getDtCrmCustomerProducts() {
		return this.dtCrmCustomerProducts;
	}

	public void setDtCrmCustomerProducts(Set dtCrmCustomerProducts) {
		this.dtCrmCustomerProducts = dtCrmCustomerProducts;
	}

}