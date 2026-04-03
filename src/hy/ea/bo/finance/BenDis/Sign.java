package hy.ea.bo.finance.BenDis;

import java.util.Date;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

//签到
public class Sign implements BaseBean,ExcelBean,java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String signKey;
	private String signId;
	private String sccid;//签到人
	private String account;//签到人帐号
	private String companyId;//被签到的公司id
	private String signScore;//签到积分数
	private Date signDate;//签到时间
	private String bonusPointsId;//积分id(外键)
	private String bonusPointsDetailId;//积分明细id(外键)
	
	//签到上传图片发表心情  jcy 20170622
	private String signSite;  //签到地点
	private String signInformation; //签到信息
	private String signImagePath;  //签到图片路径 
	private String signType;//签到方式 00 手机  01 终端机
	private String status;//有效：T,无效：F
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "员工编号", "员工姓名", "员工手机号", "员工身份证", "打卡时间",
				"签到方式","签到地点","签到信息"};
		return titles;
	}
	
	@Override
	public String[] properties() {
		
		return null;
	}
	
	public String getSignKey() {
		return signKey;
	}
	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}
	public String getSignId() {
		return signId;
	}
	public void setSignId(String signId) {
		this.signId = signId;
	}
	public String getSccid() {
		return sccid;
	}
	public void setSccid(String sccid) {
		this.sccid = sccid;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public String getBonusPointsId() {
		return bonusPointsId;
	}
	public void setBonusPointsId(String bonusPointsId) {
		this.bonusPointsId = bonusPointsId;
	}
	public String getBonusPointsDetailId() {
		return bonusPointsDetailId;
	}
	public void setBonusPointsDetailId(String bonusPointsDetailId) {
		this.bonusPointsDetailId = bonusPointsDetailId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSignScore() {
		return signScore;
	}
	public void setSignScore(String signScore) {
		this.signScore = signScore;
	}
	public String getSignSite() {
		return signSite;
	}
	public void setSignSite(String signSite) {
		this.signSite = signSite;
	}
	public String getSignInformation() {
		return signInformation;
	}
	public void setSignInformation(String signInformation) {
		this.signInformation = signInformation;
	}
	public String getSignImagePath() {
		return signImagePath;
	}
	public void setSignImagePath(String signImagePath) {
		this.signImagePath = signImagePath;
	}

	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
