package hy.ea.bo.office;
import hy.plat.bo.BaseBean;

import java.util.Date;


public class CardInfo implements BaseBean,java.io.Serializable{
 	private String ID;
	private String cardInfoKey;
	private String cardCode;//卡编号
	private int validityTime;//有效期数字
	private String status;//Status分为三种情况 F:正常使用中 L：已挂失 O：已注销 E：进 T：出
	private String statesType;//分为两种类型 F:正式卡；T：临时卡
	private String cardType;// cardType分为三种类型: epc（01）、12.4G（02）,mifare（03）
	private String metaInfo;// metaInfo由三个部分组成：e.g:020411112388 / 02:卡类型；04：卡长度；11112388：卡号
	private String carInfomation;//所属车ID
	private String carNum;//车牌号
	private String createUser;//发卡人
	private Date createTime;//发卡时间
	private String descs;//描述
	private String synData;
	private String companyID;
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getID() {
		return ID;
	}
	public void setID(String ID) {
		this.ID = ID;
	}
	public String getCardInfoKey() {
		return cardInfoKey;
	}
	public void setCardInfoKey(String cardInfoKey) {
		this.cardInfoKey = cardInfoKey;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public int getValidityTime() {
		return validityTime;
	}
	public void setValidityTime(int validityTime) {
		this.validityTime = validityTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatesType() {
		return statesType;
	}
	public void setStatesType(String statesType) {
		this.statesType = statesType;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getMetaInfo() {
		return metaInfo;
	}
	public void setMetaInfo(String metaInfo) {
		this.metaInfo = metaInfo;
	}
	public String getCarInfomation() {
		return carInfomation;
	}
	public void setCarInfomation(String carInfomation) {
		this.carInfomation= carInfomation;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	public String getSynData() {
		return synData;
	}
	public void setSynData(String synData) {
		this.synData = synData;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	
	


}
