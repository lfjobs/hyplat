package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

/**
 * 支付回调时需要的参数
 * @author mz
 *
 */
public class PayBackupBill implements BaseBean{
	
	private String pbbkey;
	private String pbbID;
	private String waiterID;//服务人员，用于扫码助手
	private String sccid;//支付的人的sccid
	private String staffId;//支付的人的staffid
	private String ppid;//支付的产品
	private String companyId;//产品的公司
	private String journalNum;//用于支付的凭证号
	private String attach;//附件字段
	private String coID;//结算订单
	private String carNum;
	private String addressID;
	private String noviceName;//学员报名用姓名
	private String novicePhone;//学员电话
	private String noviceCode;//身份证
	private String noviceAddress;//学员地址
	private String enrollID;//报名ID
	private String ptppid;//选择的促销品ID，多个产品，逗号分隔
	private String ptstandard;//选择促销品规格
	private String privateRoom;//桌号
	private String remark;
	private String cashCompany;//现金收款公司sccid


	private String  hdpay;//1合单
	private String  hdfinish;//已添加到待分账表中
	//刷脸

	private String openId;//用户ID
	private String transaction_id;//第三方
	private String money;//交易额
	private String device_id;//微信背后序列号
	private String nickname;//微信昵称


	//用于购买公司会员临时记录公司信息

	private String industryType;
	private String industryId;
	private String brandInfo;//品牌
	private String companyName;//认证公司名称
	private String shopname;//店铺名称
	private String companyPhone;//企业电话
	private String companyManager;//企业负责人
	private String managertel;//负责人电话
	private String accuracy; //经度
	private String dimension; //纬度
	private String gdID;//高德地图获取的POIS的ID
	private String pname;//省
	private String cityname;//市
	private String adname;//县或者区
	private String street;//具体地址
	private String logo;//logo

	public String getHdfinish() {
		return hdfinish;
	}

	public void setHdfinish(String hdfinish) {
		this.hdfinish = hdfinish;
	}

	public String getPbbkey() {
		return pbbkey;
	}

	public void setPbbkey(String pbbkey) {
		this.pbbkey = pbbkey;
	}

	public String getPbbID() {
		return pbbID;
	}

	public void setPbbID(String pbbID) {
		this.pbbID = pbbID;
	}

	public String getWaiterID() {
		return waiterID;
	}

	public void setWaiterID(String waiterID) {
		this.waiterID = waiterID;
	}

	public String getSccid() {
		return sccid;
	}

	public void setSccid(String sccid) {
		this.sccid = sccid;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getJournalNum() {
		return journalNum;
	}

	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getCoID() {
		return coID;
	}

	public void setCoID(String coID) {
		this.coID = coID;
	}


	public String getAddressID() {
		return addressID;
	}

	public void setAddressID(String addressID) {
		this.addressID = addressID;
	}

	public String getNoviceName() {
		return noviceName;
	}

	public void setNoviceName(String noviceName) {
		this.noviceName = noviceName;
	}

	public String getNovicePhone() {
		return novicePhone;
	}

	public void setNovicePhone(String novicePhone) {
		this.novicePhone = novicePhone;
	}

	public String getNoviceAddress() {
		return noviceAddress;
	}

	public void setNoviceAddress(String noviceAddress) {
		this.noviceAddress = noviceAddress;
	}

	public String getNoviceCode() {
		return noviceCode;
	}

	public void setNoviceCode(String noviceCode) {
		this.noviceCode = noviceCode;
	}

	public String getEnrollID() {
		return enrollID;
	}

	public void setEnrollID(String enrollID) {
		this.enrollID = enrollID;
	}

	public String getPtppid() {
		return ptppid;
	}

	public void setPtppid(String ptppid) {
		this.ptppid = ptppid;
	}

	public String getPtstandard() {
		return ptstandard;
	}

	public void setPtstandard(String ptstandard) {
		this.ptstandard = ptstandard;
	}

	public String getPrivateRoom() {
		return privateRoom;
	}

	public void setPrivateRoom(String privateRoom) {
		this.privateRoom = privateRoom;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCashCompany() {
		return cashCompany;
	}

	public void setCashCompany(String cashCompany) {
		this.cashCompany = cashCompany;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public String getBrandInfo() {
		return brandInfo;
	}

	public void setBrandInfo(String brandInfo) {
		this.brandInfo = brandInfo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getCompanyManager() {
		return companyManager;
	}

	public void setCompanyManager(String companyManager) {
		this.companyManager = companyManager;
	}

	public String getManagertel() {
		return managertel;
	}

	public void setManagertel(String managertel) {
		this.managertel = managertel;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getGdID() {
		return gdID;
	}

	public void setGdID(String gdID) {
		this.gdID = gdID;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getAdname() {
		return adname;
	}

	public void setAdname(String adname) {
		this.adname = adname;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getHdpay() {
		return hdpay;
	}

	public void setHdpay(String hdpay) {
		this.hdpay = hdpay;
	}
}
