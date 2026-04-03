package hy.ea.bo.finance;


import java.util.Date;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

public class Profitshare  implements BaseBean,ExcelBean{


	private String idkey;
	private String id;
	private String ppid;
	private String agent;//代理商
	private String shop;//店铺
	private String company;//公司
	private String partner;//合伙人
	private String integral;//消费者积分
	private String salesman;//营销员
	private Date publishDate;
	private String remark;
	// Constructors

	/** default constructor */
	public Profitshare() {
	}

	/** full constructor */
	public Profitshare(String ppid, String agent, String shop, String company,
			String partner, String integral, String salesman) {
		this.ppid = ppid;
		this.agent = agent;
		this.shop = shop;
		this.company = company;
		this.partner = partner;
		this.integral = integral;
		this.salesman = salesman;
	}
	// Property accessors
	public String getAgent() {
		return this.agent;
	}

	public String getIdkey() {
		return idkey;
	}

	public void setIdkey(String idkey) {
		this.idkey = idkey;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getShop() {
		return this.shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPartner() {
		return this.partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getIntegral() {
		return this.integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	public String getSalesman() {
		return this.salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}