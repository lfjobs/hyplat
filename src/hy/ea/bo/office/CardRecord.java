package hy.ea.bo.office;
import hy.plat.bo.BaseBean;

import java.util.Date;


public class CardRecord implements BaseBean,java.io.Serializable{
 	private String ID;
	private String cardRecordKey;
	private String cardCode;//
	private String carNum;//所属车牌号
	private String readerEnter;//进读卡器ID
	private String readerEnterName;
	private Date enterTime;
	private Date createDate;//卡记录创建时间
	private String descs;
	private String createUser;
	private String companyId;
	private String videoPic;//保存图片
	
	
	//辅助查询字段
	private String startTime;
	private String endTime;
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getID() {
		return ID;
	}
	public void setID(String id) {
		ID = id;
	}
	public String getCardRecordKey() {
		return cardRecordKey;
	}
	public void setCardRecordKey(String cardRecordKey) {
		this.cardRecordKey = cardRecordKey;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getReaderEnter() {
		return readerEnter;
	}
	public void setReaderEnter(String readerEnter) {
		this.readerEnter = readerEnter;
	}
	public Date getEnterTime() {
		return enterTime;
	}
	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getVideoPic() {
		return videoPic;
	}
	public void setVideoPic(String videoPic) {
		this.videoPic = videoPic;
	}
	public String getReaderEnterName() {
		return readerEnterName;
	}
	public void setReaderEnterName(String readerEnterName) {
		this.readerEnterName = readerEnterName;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	
	


}
