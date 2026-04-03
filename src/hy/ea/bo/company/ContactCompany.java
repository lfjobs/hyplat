package hy.ea.bo.company;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.io.Serializable;

/**
 * 往来单位
 * @author 王汝明
 */
public class ContactCompany implements BaseBean,ExcelBean,Serializable {
	private String ccompanyKey;
	private String ccompanyID;
	private String companyName;         //认证公司名称
	private String shopname;//店铺命名
	private String address;                //公司地址ID
	private String companyAddr;         //具体地址
	private String pname;//省
	private String cityname;//市
	private String adname;//县或者区
	private String street;//具体地址
	private String companyTel;          //公司电话
	private String cresponsible;        //负责人
	private String sex;                 //负责人性别zzl

	private String representative;      //法人代表xgb
	private String idCard;              //负责人身份证号码xgb


	private String responsibleTel;      //负责人电话
	private String remark;              //备注信息
	private String dealIn;              //经营范围 ,主营项目
	private String industryType;        //行业类别
	private String gdcate;//对应高德分类一级分类
	private String gdcode;//对应ID一级
	private String gdcate2;//对应高德分类二级分类
	private String gdcode2;//对应ID二级
	private String custStatus;          //单位状态             01：未注册单位(社会单位客户)  02：已注册单位
	private String entryPersonnel;      //信息录入人员姓名

	private String entryoName;        //录入部门
	//mz
	private String comPro;//公司性质 e.g.国企，民营等
	private String comScale;//公司规模 e.g. 20人以下，20-99人等
	/**
	 * ljc添加
	 */
	private String brandInfo;//品牌信息
	private String comPurpose;//公司宗旨
	private String logoPath;//公司logo小图片 45*45
	private String jjPath;//公司简介小图片 230*138

	private File lg;//公司Logo
	private String lgFileName;//logo名称
	private String companyWeb;//公司网址;
	private String erweimaImage;//二维码路径
	private String accountID;//账号
	private String accountName;//人员姓名； 
	private String webstatus;//网站状态 00 停用 01 ：启用
	private String qrCodePath;        //公司名片二维码图片路径
	private String pcState;  //1个人0元加入null公司0元加入
	private String jingdu; //经纬度
	/**
	 * xgb添加
	 */
	public String pmCodePath;//公司公众号二维码图片路径
	private String authState;  //认证状态00:未认证,01:认证审核中,02:已认证,03:认证失败
	private String accuracy; //经度
	private String dimension; //纬度
	private String industryId;        //行业id
	private String gdID;//高德地图获取的POIS的ID
	private String applyID;
	private String companyCode;//公司（商家）编码
	private String seqs;//排序 总部设置为0；

	public ContactCompany() {
		super();
	}

	public ContactCompany(String cresponsible, String responsibleTel,
						  String industryType, String companyName, String companyAddr,
						  String dealIn, String companyTel, String companyWeb, String address,
						  String ccompanyID) {
		super();
		this.cresponsible = cresponsible;
		this.responsibleTel = responsibleTel;
		this.industryType = industryType;
		this.companyName = companyName;
		this.companyAddr = companyAddr;
		this.dealIn = dealIn;
		this.companyTel = companyTel;
		this.companyWeb = companyWeb;
		this.address = address;
		this.ccompanyID = ccompanyID;

	}

	public ContactCompany(String ccompanyKey, String ccompanyID, String companyName, String address, String companyAddr, String companyTel, String cresponsible, String sex, String representative, String idCard, String responsibleTel, String remark, String dealIn, String industryType, String custStatus, String entryPersonnel, String entryoName, String comPro, String comScale, String brandInfo, String comPurpose, String logoPath, String jjPath, File lg, String lgFileName, String companyWeb, String erweimaImage, String accountID, String accountName, String webstatus, String qrCodePath, String pcState, String jingdu, String pmCodePath, String authState, String accuracy, String dimension, String industryId) {
		this.ccompanyKey = ccompanyKey;
		this.ccompanyID = ccompanyID;
		this.companyName = companyName;
		this.address = address;
		this.companyAddr = companyAddr;
		this.companyTel = companyTel;
		this.cresponsible = cresponsible;
		this.sex = sex;
		this.representative = representative;
		this.idCard = idCard;
		this.responsibleTel = responsibleTel;
		this.remark = remark;
		this.dealIn = dealIn;
		this.industryType = industryType;
		this.custStatus = custStatus;
		this.entryPersonnel = entryPersonnel;
		this.entryoName = entryoName;
		this.comPro = comPro;
		this.comScale = comScale;
		this.brandInfo = brandInfo;
		this.comPurpose = comPurpose;
		this.logoPath = logoPath;
		this.jjPath = jjPath;
		this.lg = lg;
		this.lgFileName = lgFileName;
		this.companyWeb = companyWeb;
		this.erweimaImage = erweimaImage;
		this.accountID = accountID;
		this.accountName = accountName;
		this.webstatus = webstatus;
		this.qrCodePath = qrCodePath;
		this.pcState = pcState;
		this.jingdu = jingdu;
		this.pmCodePath = pmCodePath;
		this.authState = authState;
		this.accuracy = accuracy;
		this.dimension = dimension;
		this.industryId = industryId;
	}

	public static String[] columnHeadings() {
		String[] titles = {"序号", "单位名称", "单位地址", "单位电话", "单位负责人", "负责人电话", "行业类别", "备注信息", "经营范围 ", "单位状态"};
		return titles;
	}

	public String[] properties() {
		String[] properties = {companyName, companyAddr, companyTel, cresponsible, responsibleTel, industryType, remark, dealIn};
		return properties;
	}

	public static String[] columnHeadings1() { //导出企业/客户资料
		String[] titles = {"序号", "父公司名称", "组织机构名", "公司名称", "公司地址", "负责人", "公司电话", "邮箱"};
		return titles;
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

	public String getEntryoName() {
		return entryoName;
	}

	public void setEntryoName(String entryoName) {
		this.entryoName = entryoName;
	}

	public String getCompanyAddr() {
		return companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	public String getCcompanyKey() {
		return ccompanyKey;
	}

	public void setCcompanyKey(String ccompanyKey) {
		this.ccompanyKey = ccompanyKey;
	}

	public String getCcompanyID() {
		return ccompanyID;
	}

	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
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

	public String getEntryPersonnel() {
		return entryPersonnel;
	}

	public void setEntryPersonnel(String entryPersonnel) {
		this.entryPersonnel = entryPersonnel;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBrandInfo() {
		return brandInfo;
	}

	public void setBrandInfo(String brandInfo) {
		this.brandInfo = brandInfo;
	}

	public String getComPurpose() {
		return comPurpose;
	}

	public void setComPurpose(String comPurpose) {
		this.comPurpose = comPurpose;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public File getLg() {
		return lg;
	}

	public void setLg(File lg) {
		this.lg = lg;
	}

	public String getLgFileName() {
		return lgFileName;
	}

	public void setLgFileName(String lgFileName) {
		this.lgFileName = lgFileName;
	}

	public String getWebstatus() {
		return webstatus;
	}

	public void setWebstatus(String webstatus) {
		this.webstatus = webstatus;
	}

	public String getCompanyWeb() {
		return companyWeb;
	}

	public void setCompanyWeb(String companyWeb) {
		this.companyWeb = companyWeb;
	}

	public String getErweimaImage() {
		return erweimaImage;
	}

	public void setErweimaImage(String erweimaImage) {
		this.erweimaImage = erweimaImage;
	}

	public String getJjPath() {
		return jjPath;
	}

	public void setJjPath(String jjPath) {
		this.jjPath = jjPath;
	}

	public String getQrCodePath() {
		return qrCodePath;
	}

	public void setQrCodePath(String qrCodePath) {
		this.qrCodePath = qrCodePath;
	}

	public String getComPro() {
		return comPro;
	}

	public void setComPro(String comPro) {
		this.comPro = comPro;
	}

	public String getComScale() {
		return comScale;
	}

	public void setComScale(String comScale) {
		this.comScale = comScale;
	}

	public String getPmCodePath() {
		return pmCodePath;
	}

	public void setPmCodePath(String pmCodePath) {
		this.pmCodePath = pmCodePath;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getRepresentative() {
		return representative;
	}

	public void setRepresentative(String representative) {
		this.representative = representative;
	}

	public String getAuthState() {
		return authState;
	}

	public void setAuthState(String authState) {
		this.authState = authState;
	}

	public String getPcState() {
		return pcState;
	}

	public void setPcState(String pcState) {
		this.pcState = pcState;
	}

	public String getJingdu() {
		return jingdu;
	}

	public void setJingdu(String jingdu) {
		this.jingdu = jingdu;
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

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
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

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getGdcate() {
		return gdcate;
	}

	public void setGdcate(String gdcate) {
		this.gdcate = gdcate;
	}

	public String getApplyID() {
		return applyID;
	}

	public void setApplyID(String applyID) {
		this.applyID = applyID;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getSeqs() {
		return seqs;
	}

	public void setSeqs(String seqs) {
		this.seqs = seqs;
	}
}
