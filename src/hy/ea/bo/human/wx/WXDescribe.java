package hy.ea.bo.human.wx;

import hy.plat.bo.BaseBean;

/**
 * WXDescribe entity. 
 * @author MyEclipse Persistence Tools
 */

public class WXDescribe implements BaseBean {

	// Fields

	private String wxDesKey;
	private String wxDesID;
	private String wxRecID;
	private String describeName;
	private String describeContent;
	private String companyID;
	private String groupCompanySn;

	// Constructors

	/** default constructor */
	public WXDescribe() {
	}

	/** minimal constructor */
	public WXDescribe(String wxDesID, String wxRecID, String companyID,
			String groupCompanySn) {
		this.wxDesID = wxDesID;
		this.wxRecID = wxRecID;
		this.companyID = companyID;
		this.groupCompanySn = groupCompanySn;
	}

	/** full constructor */
	public WXDescribe(String wxDesID, String wxRecID, String describeName,
			String describeContent, String companyID, String groupCompanySn) {
		this.wxDesID = wxDesID;
		this.wxRecID = wxRecID;
		this.describeName = describeName;
		this.describeContent = describeContent;
		this.companyID = companyID;
		this.groupCompanySn = groupCompanySn;
	}

	// Property accessors

	public String getWxDesKey() {
		return this.wxDesKey;
	}

	public void setWxDesKey(String wxDesKey) {
		this.wxDesKey = wxDesKey;
	}

	public String getWxDesID() {
		return this.wxDesID;
	}

	public void setWxDesID(String wxDesID) {
		this.wxDesID = wxDesID;
	}

	public String getWxRecID() {
		return this.wxRecID;
	}

	public void setWxRecID(String wxRecID) {
		this.wxRecID = wxRecID;
	}

	public String getDescribeName() {
		return this.describeName;
	}

	public void setDescribeName(String describeName) {
		this.describeName = describeName;
	}

	public String getDescribeContent() {
		return this.describeContent;
	}

	public void setDescribeContent(String describeContent) {
		this.describeContent = describeContent;
	}

	public String getCompanyID() {
		return this.companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getGroupCompanySn() {
		return this.groupCompanySn;
	}

	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}

}