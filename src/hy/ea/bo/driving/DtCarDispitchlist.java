package hy.ea.bo.driving;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtCarDispitchlist entity. @author MyEclipse Persistence Tools
 */

public class DtCarDispitchlist implements BaseBean{

	// Fields
	//气瓶检测派工单记录

	private String dispitchKey;
	private String dispitchId;
	private String companyId;
	private String organizationId;
	private String cylinderNum;
	private String checkoutNum;  //检验编号
	private String roomTemperature;// 室温
	private String waterTemperature;//水温
	private Date checkDate;//检验日期
	private Date nextcheckDate;//下次检验日期
	private String staffId; //检验人ID
	private String staffIds; //经手人ID

	// Constructors

	/** default constructor */
	public DtCarDispitchlist() {
	}

	/** full constructor */
	public DtCarDispitchlist(String dispitchId,String companyId,String organizationId, String cylinderNum,
			String checkoutNum, String roomTemperature,
			String waterTemperature, Date checkDate,Date nextcheckDate, String staffId,
			String staffIds) {
		this.dispitchId = companyId;
		this.companyId = organizationId;
		this.organizationId = organizationId;
		this.cylinderNum = cylinderNum;
		this.checkoutNum = checkoutNum;
		this.roomTemperature = roomTemperature;
		this.waterTemperature = waterTemperature;
		this.checkDate = checkDate;
		this.nextcheckDate = nextcheckDate;
		this.staffId = staffId;
		this.staffIds = staffIds;
	}

	// Property accessors

	public String getDispitchKey() {
		return this.dispitchKey;
	}

	public void setDispitchKey(String dispitchKey) {
		this.dispitchKey = dispitchKey;
	}

	public String getDispitchId() {
		return this.dispitchId;
	}

	public void setDispitchId(String dispitchId) {
		this.dispitchId = dispitchId;
	}

	public String getCylinderNum() {
		return this.cylinderNum;
	}

	public void setCylinderNum(String cylinderNum) {
		this.cylinderNum = cylinderNum;
	}

	public String getCheckoutNum() {
		return this.checkoutNum;
	}

	public void setCheckoutNum(String checkoutNum) {
		this.checkoutNum = checkoutNum;
	}

	public String getRoomTemperature() {
		return this.roomTemperature;
	}

	public void setRoomTemperature(String roomTemperature) {
		this.roomTemperature = roomTemperature;
	}

	public String getWaterTemperature() {
		return this.waterTemperature;
	}

	public void setWaterTemperature(String waterTemperature) {
		this.waterTemperature = waterTemperature;
	}

	public Date getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Date getNextcheckDate() {
		return nextcheckDate;
	}

	public void setNextcheckDate(Date nextcheckDate) {
		this.nextcheckDate = nextcheckDate;
	}

	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffIds() {
		return this.staffIds;
	}

	public void setStaffIds(String staffIds) {
		this.staffIds = staffIds;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}



}