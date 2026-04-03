package hy.ea.bo.company.vo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;


/**
 * 往来单位关系
 * @author 
 */
public class ContactCompanyView implements BaseBean,ExcelBean,Serializable {
	private String contactConnectionKey;
	private String contactConnectionID;
	private String companyID;
	private String ccompanyID;      
	private String contactConnections;  //往来单位关系
	private String companyName;         //公司名称
	private String address;	            //公司地址ID
	private String companyAddr;         //具体地址
	private String companyTel;          //公司电话
	private String cresponsible;        //负责人
	private String responsibleTel;      //负责人电话
	private String remark;              //备注信息
	private String dealIn;              //经营范围 
	private String industryType;        //行业类别
	private String custStatus;          //单位状态              01：未注册单位(社会单位客户) 02：已注册单位
	
	public static String[] columnHeadings() {
		String[] titles = {"序号","单位名称","单位往来关系","单位地址" ,"单位电话" ,"单位负责人","负责人电话","行业类别","备注信息", "单位状态"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = { companyName,contactConnections,companyAddr,companyTel,cresponsible,responsibleTel,industryType,remark};
		return properties;
	}
	
	
	public String getContactConnectionKey() {
		return contactConnectionKey;
	}
	public void setContactConnectionKey(String contactConnectionKey) {
		this.contactConnectionKey = contactConnectionKey;
	}
	public String getContactConnectionID() {
		return contactConnectionID;
	}
	public void setContactConnectionID(String contactConnectionID) {
		this.contactConnectionID = contactConnectionID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCcompanyID() {
		return ccompanyID;
	}
	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}
	public String getContactConnections() {
		return contactConnections;
	}
	public void setContactConnections(String contactConnections) {
		this.contactConnections = contactConnections;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCompanyAddr() {
		return companyAddr;
	}
	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}
	public String getCompanyTel() {
		return companyTel;
	}
	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}
	public String getCresponsible() {
		return cresponsible;
	}
	public void setCresponsible(String cresponsible) {
		this.cresponsible = cresponsible;
	}
	public String getResponsibleTel() {
		return responsibleTel;
	}
	public void setResponsibleTel(String responsibleTel) {
		this.responsibleTel = responsibleTel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDealIn() {
		return dealIn;
	}
	public void setDealIn(String dealIn) {
		this.dealIn = dealIn;
	}
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	public String getCustStatus() {
		return custStatus;
	}
	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}
	
}
