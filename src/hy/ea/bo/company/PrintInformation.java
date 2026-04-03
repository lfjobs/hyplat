package hy.ea.bo.company;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 打印信息
 * 
 * @author
 */
public class PrintInformation implements BaseBean, ExcelBean,Serializable {
	private String printInfoKey;
	private String printInfoID;
	private String accountID;
	private String staffID;
	private String staffName;// 人员姓名
	private String staffCode;// 人员编号
	private String staffIdentityCard;// 身份证
	private String companyID;
	private String credentialsTitle;// 标题
	private String credentialsName;// 证件名称
	private String credentialsCode;// 职务
	private String credentialsType;// 组别
	private String photo;// 照片
	private String serveWay;  //服务方式
	private Date dateofissue;  //发证日期
	private String credentialsDate;// 证件时间
	private String credentialsDate2;// 证件时间
	private Date createDate;// 创建时间
	private String address;// 地址
	private String size;

	public static String[] columnHeadings() {
		String[] titles = { "序号", "人员姓名", "人员编号", "身份证", "标题", "证件名称", "职务",
				"组别", "证件时间","创建时间", "地址" };
		return titles;
	}

	@Override
	public String[] properties() {

		String[] properties = { staffName, staffCode, staffIdentityCard,
				credentialsTitle, credentialsName, credentialsCode,
				credentialsType, credentialsDate, credentialsDate2,
				String.format("%1$tF", createDate), address };
		return properties;
	}

	public String getCredentialsTitle() {
		return credentialsTitle;
	}

	public void setCredentialsTitle(String credentialsTitle) {
		this.credentialsTitle = credentialsTitle;
	}

	public String getPrintInfoKey() {
		return printInfoKey;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getStaffIdentityCard() {
		return staffIdentityCard;
	}

	public void setStaffIdentityCard(String staffIdentityCard) {
		this.staffIdentityCard = staffIdentityCard;
	}

	public void setPrintInfoKey(String printInfoKey) {
		this.printInfoKey = printInfoKey;
	}

	public String getPrintInfoID() {
		return printInfoID;
	}

	public void setPrintInfoID(String printInfoID) {
		this.printInfoID = printInfoID;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCredentialsName() {
		return credentialsName;
	}

	public void setCredentialsName(String credentialsName) {
		this.credentialsName = credentialsName;
	}

	public String getCredentialsCode() {
		return credentialsCode;
	}

	public void setCredentialsCode(String credentialsCode) {
		this.credentialsCode = credentialsCode;
	}

	public String getCredentialsType() {
		return credentialsType;
	}

	public void setCredentialsType(String credentialsType) {
		this.credentialsType = credentialsType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getCredentialsDate() {
		return credentialsDate;
	}

	public void setCredentialsDate(String credentialsDate) {
		this.credentialsDate = credentialsDate;
	}

	public String getCredentialsDate2() {
		return credentialsDate2;
	}

	public void setCredentialsDate2(String credentialsDate2) {
		this.credentialsDate2 = credentialsDate2;
	}

	public Date getDateofissue() {
		return dateofissue;
	}

	public void setDateofissue(Date dateofissue) {
		this.dateofissue = dateofissue;
	}

	public String getServeWay() {
		return serveWay;
	}

	public void setServeWay(String serveWay) {
		this.serveWay = serveWay;
	}


}
