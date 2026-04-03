package hy.ea.bo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class Company implements BaseBean,Serializable{
	private static final long serialVersionUID = 1L;
	private String companyKey;
	private String companyID;
	private String companyPID;
	private String companyMID;//负责管理此公司ID
	private String districtID;//地址表业务主键
	private String companyIdentifier;//组织机构名
	private String companyName;//公司名称
	private Date   companyRegisterDate;//注册时间
	private int    companyLicenseCount;
	private int isst;//是否在驾校联盟网站标识
	private String showwechat;//是否显示在微信
	private String industryType;//注册公司行类别
	private String bookCurrency; //记账币别
	private String accumulated; //累积盈亏科目
	private String subjectsID;//科目ID
	private String codeID;//币别ID
	private String accuracy; //经度
	private String dimension; //纬度
	private String shopname;//店铺命名
	private String pname;//省
	private String cityname;//市
	private String adname;//县或者区
	private String street;//具体地址
	private String gdID;//高德地图获取的POIS的ID
	private String brandInfo;
	/*
	 * 00表示正常  01表示不正常
	 */
	private String companyStatus;
	/*
	 *00表示企业
	 *01表示客户 （其中11表示未付费注册客户 12表示已付费注册客户）
	 */
	private String companyType;//公司类型
	private String groupCompanySn; //集团公司标识
	private Boolean isSt;//是否为驾校
	private String comType;//
	private String ccomtype;//0大型 1中型  2小型  3微型  4微小型   5供应商
	private List<Object>  pklist;//为了购物车   写的 没生成数据库
	private String industryId;//注册公司行业id
	private String totalSales;//公司总销量

	private String hxID;//环信聊天群ID

	private String gdcate;//对应高德分类一级分类
	private String gdcode;//对应ID一级
	private String gdcate2;//对应高德分类二级分类
	private String gdcode2;//对应ID二级
	private String withDrawNum;//12，14，15
	public String getCompanyKey() {
		return companyKey;
	}
	public void setCompanyKey(String companyKey) {
		this.companyKey = companyKey;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyPID() {
		return companyPID;
	}
	public void setCompanyPID(String companyPID) {
		this.companyPID = companyPID;
	}
	public String getDistrictID() {
		return districtID;
	}
	public void setDistrictID(String districtID) {
		this.districtID = districtID;
	}
	public String getCompanyIdentifier() {
		return companyIdentifier;
	}
	public void setCompanyIdentifier(String companyIdentifier) {
		this.companyIdentifier = companyIdentifier;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Date getCompanyRegisterDate() {
		return companyRegisterDate;
	}
	


	public List<Object> getPklist() {
		return pklist;
	}
	public void setPklist(List<Object> pklist) {
		this.pklist = pklist;
	}
	public String getCcomtype() {
		return ccomtype;
	}
	public void setCcomtype(String ccomtype) {
		this.ccomtype = ccomtype;
	}
	public void setCompanyRegisterDate(Date companyRegisterDate) {
		this.companyRegisterDate = companyRegisterDate;
	}
	public int getCompanyLicenseCount() {
		return companyLicenseCount;
	}
	public void setCompanyLicenseCount(int companyLicenseCount) {
		this.companyLicenseCount = companyLicenseCount;
	}
	public String getCompanyStatus() {
		return companyStatus;
	}
	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public String getGroupCompanySn() {
		return groupCompanySn;
	}
	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}

	public void setIsst(int isst) {
		this.isst = isst;
	}
	public int getIsst() {
		return isst;
	}
	public String getShowwechat() {
		return showwechat;
	}
	public void setShowwechat(String showwechat) {
		this.showwechat = showwechat;
	}
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	public String getComType() {
		return comType;
	}
	public void setComType(String comType) {
		this.comType = comType;
	}
	public String getBookCurrency() {
		return bookCurrency;
	}
	public void setBookCurrency(String bookCurrency) {
		this.bookCurrency = bookCurrency;
	}
	public String getAccumulated() {
		return accumulated;
	}
	public void setAccumulated(String accumulated) {
		this.accumulated = accumulated;
	}
	public String getSubjectsID() {
		return subjectsID;
	}
	public void setSubjectsID(String subjectsID) {
		this.subjectsID = subjectsID;
	}
	public String getCodeID() {
		return codeID;
	}
	public void setCodeID(String codeID) {
		this.codeID = codeID;
	}

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public String getCompanyMID() {
		return companyMID;
	}

	public void setCompanyMID(String companyMID) {
		this.companyMID = companyMID;
	}

	public String getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(String totalSales) {
		this.totalSales = totalSales;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
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

	public String getGdID() {
		return gdID;
	}

	public void setGdID(String gdID) {
		this.gdID = gdID;
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

	public String getBrandInfo() {
		return brandInfo;
	}

	public void setBrandInfo(String brandInfo) {
		this.brandInfo = brandInfo;
	}

	public String getHxID() {
		return hxID;
	}

	public void setHxID(String hxID) {
		this.hxID = hxID;
	}

	public String getGdcate() {
		return gdcate;
	}

	public void setGdcate(String gdcate) {
		this.gdcate = gdcate;
	}

	public String getGdcode() {
		return gdcode;
	}

	public void setGdcode(String gdcode) {
		this.gdcode = gdcode;
	}

	public String getGdcate2() {
		return gdcate2;
	}

	public void setGdcate2(String gdcate2) {
		this.gdcate2 = gdcate2;
	}

	public String getGdcode2() {
		return gdcode2;
	}

	public void setGdcode2(String gdcode2) {
		this.gdcode2 = gdcode2;
	}

	public String getWithDrawNum() {
		return withDrawNum;
	}

	public void setWithDrawNum(String withDrawNum) {
		this.withDrawNum = withDrawNum;
	}
}

