package com.tiantai.wfj.bo;

import hy.ea.bo.human.Staff;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TEshopCusCom implements BaseBean,java.io.Serializable {//帐号表

	private String scckey;	
	private String sccId;
	private String account;//登录账号
	private String openId;//微信公众行号微信用户的openId
	private String qqId;//qqId授权登陆
	private String appOpenId;//微信elkcAPP应用ID
	private String weiboId;//微博id
	private String staffid;
	// 2-公司<br/>3-合伙人商城业主会员<br/>4-微分金商城业主会员<br/>5-代理商商城业主会员<br/>6-客户
	private String cusType;//登陆类型：     0平台  1税务  2公司  3合伙创业  4 微分金 5代理商  6vip客户 7普通客户
	private String companyId;//购买公司的id
	private String organizationID;//购买店铺的id
	private String Superioragent;//上级代理者 的身份id(就是account)
	private String supperSccId ;//上级代理者的sccId

	private String state;//默认为1   1个人  2 为公司
	private Date teccDate;//时间,根据绑定变更时间为最新
	private String ppid;//物品ID
	private String acquiesce;//多身份默认账户  01为默认
	
	private String qrcodePath;
	private String logOff="0"; //0未注销 1注销
	private String userId; //授权支付宝唯一标识
	private String nickName;//授权支付宝昵称
	private String weNickName;//授权微信昵称
	private String withDraway;//默认提现方式  01 支付宝 02 微信 03 银联

	private String eqId;   //e路快车QQ登录ID
	private String eOpenId;//e路快车微信登录ID
	private String eBlogId;//e路快车微博登录ID

	//其他字段 非数据库字段
	private Staff staff;
	private String pseudoCompanyName;//独立字段,公司名称
	private String industryType;//行业
	private String groupsn;
	private String cusTypeName;

	private String shopid;//个人店铺ID




	private String wxhopenid;
	private String wxminiopenid;
	private String wxunionid;
	public TEshopCusCom() {
	}
	public TEshopCusCom(String account,String industryType,String groupCompanySn,String companyId) {
		this.account=account;
		this.industryType=industryType;
		this.groupsn=groupCompanySn;
		this.companyId=companyId;
	}
	public TEshopCusCom(Staff staff,String cusType, String account,String sccId) {
		super();
		this.staff=staff;
		this.cusType = cusType;
		this.account = account;
		this.sccId=sccId;
		this.cusTypeName=getCusTypeName();
	}
	
	public TEshopCusCom(String sccId, String account, String staffid,
			String cusType, String companyId, String superioragent,
			String state, Date teccDate,  String pseudoCompanyName,String logOff) {
		super();
		this.sccId = sccId;
		this.account = account;
		this.staffid = staffid;
		this.cusType = cusType;
		this.companyId = companyId;
		this.Superioragent = superioragent;
		this.state = state;
		this.teccDate = teccDate;
		this.pseudoCompanyName = pseudoCompanyName;
		this.cusTypeName=getCusTypeName();
		this.logOff=logOff;
	}

	public TEshopCusCom(Staff staff) {
		super();
		this.staff=staff;
	}

	public String getLogOff() {
		return logOff;
	}

	public void setLogOff(String logOff) {
		this.logOff = logOff;
	}

	public String getScckey() {
		return scckey;
	}
	public void setScckey(String scckey) {
		this.scckey = scckey;
	}
	public String getSccId() {
		return sccId;
	}
	public void setSccId(String sccId) {
		this.sccId = sccId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSuperioragent() {
		return Superioragent;
	}
	public void setSuperioragent(String superioragent) {
		Superioragent = superioragent;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getStaffid() {
		return staffid;
	}
	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}
	public String getCusType() {
		return cusType;
	}
	public void setCusType(String cusType) {
		this.cusType = cusType;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public String getPseudoCompanyName() {
		return pseudoCompanyName;
	}
	public void setPseudoCompanyName(String pseudoCompanyName) {
		this.pseudoCompanyName = pseudoCompanyName;
	}
	public String getWxminiopenid() {
		return wxminiopenid;
	}

	public void setWxminiopenid(String wxminiopenid) {
		this.wxminiopenid = wxminiopenid;
	}

	public String getWxunionid() {
		return wxunionid;
	}

	public void setWxunionid(String wxunionid) {
		this.wxunionid = wxunionid;
	}
	public String getCusTypeName() {
		String cusType = getCusType();
		Map<String,String> map = new HashMap<String,String>();
		map.put("0","中联园区平台");
		map.put("0.5","国税");
		map.put("1","地税");
		map.put("2","公司商城业主会员");
		map.put("3","合伙创业商城业主会员");
		map.put("4","微分金商城业主会员");
		map.put("5","代理商商城业主会员");
		map.put("6","Vip客户");
		map.put("7","普通客户");
		cusTypeName = map.get(cusType);
		
		return cusTypeName;
	}
	public void setCusTypeName(String cusTypeName) {
		this.cusTypeName = cusTypeName;
	}

	public String getGroupsn() {
		return groupsn;
	}
	public void setGroupsn(String groupsn) {
		this.groupsn = groupsn;
	}
	public Date getTeccDate() {
		return teccDate;
	}
	public void setTeccDate(Date teccDate) {
		this.teccDate = teccDate;
	}
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	public String getPpid() {
		return ppid;
	}
	public void setPpid(String ppid) {
		this.ppid = ppid;
	}
	public String getAcquiesce() {
		return acquiesce;
	}
	public void setAcquiesce(String acquiesce) {
		this.acquiesce = acquiesce;
	}
	public String getSupperSccId() {
		return supperSccId;
	}
	public void setSupperSccId(String supperSccId) {
		this.supperSccId = supperSccId;
	}
	public String getQrcodePath() {
		return qrcodePath;
	}
	public void setQrcodePath(String qrcodePath) {
		this.qrcodePath = qrcodePath;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getQqId() {
		return qqId;
	}

	public void setQqId(String qqId) {
		this.qqId = qqId;
	}

	public String getAppOpenId() {
		return appOpenId;
	}

	public void setAppOpenId(String appOpenId) {
		this.appOpenId = appOpenId;
	}

	public String getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getWithDraway() {
		return withDraway;
	}

	public void setWithDraway(String withDraway) {
		this.withDraway = withDraway;
	}

	public String getWeNickName() {
		return weNickName;
	}

	public void setWeNickName(String weNickName) {
		this.weNickName = weNickName;
	}

	public String getEqId() {
		return eqId;
	}

	public void setEqId(String eqId) {
		this.eqId = eqId;
	}

	public String geteOpenId() {
		return eOpenId;
	}

	public void seteOpenId(String eOpenId) {
		this.eOpenId = eOpenId;
	}

	public String geteBlogId() {
		return eBlogId;
	}

	public void seteBlogId(String eBlogId) {
		this.eBlogId = eBlogId;
	}

	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}

	public String getWxhopenid() {
		return wxhopenid;
	}

	public void setWxhopenid(String wxhopenid) {
		this.wxhopenid = wxhopenid;
	}
}