package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

/*
 * 车辆停车管理
 * */
public class CarManage implements BaseBean{
	
	private String carmID;//主键
	private String carmkey;
	private String carmNumber;//收费编号
	private String carNumber;//车牌号
	private Date indate;//车辆进入时间
	private Date outdate;//车辆出去时间
	private String equipin;//进入设备编号
	private String equipout;//出去设备编号
	private String equipmentNumber;//当前设备编号
	
	//0:未知车牌,1:蓝牌,2:黑牌,3:单排黄牌,4:双排黄牌（大车尾牌，农用车）,5:警车车牌,6:武警车牌,7:个性化车牌,
	//8:单排军车,9:双排军车,10:使馆牌,11:香港牌,12:拖拉机,13:澳门牌,14:厂内牌,15:民航牌
	private String numberType;//车牌类型
	
	private String money;//总收费
	private String time;//总时间
	private String panorama;//全景图
	private String picture;//车牌图
	private Date createdate;//创建时间
	private String serialNumber;//停车流水号
	private String status;//状态0:离开,1:进入,2:错误数据
	private String model;//录入类型:0:自动,1:人工
	private String chargeType;//收费类型00:现金收费,01:积分金币收费； 02:扫码支付 03 免费
	private String chargeState;//收费具体00 未支付，01:包月或包年 02：积分金币  03：扫码支付 04：现金支付 05 时间太短消费0元 06 免费临时车
	private String chargeState1;//进口的收费 00 未支付，01:包月或包年 02：积分金币  03：扫码支付 04：现金支付 05 时间太短消费0元 06 免费临时车
	private String journalNum;
    private String parksId;//停车位ID
    private String open;// 0没抬杆 1抬杆
	private String open1;//进入记录抬过杆没
	private String auditStatus;//审核状态 默认空或者为自动审核 ，00 未提交审核，01 提交审核  02 人工审核通过 03 人工审核不通过

	
	
	public String getCarmID() {
		return carmID;
	}
	public void setCarmID(String carmID) {
		this.carmID = carmID;
	}
	public String getCarmkey() {
		return carmkey;
	}
	public void setCarmkey(String carmkey) {
		this.carmkey = carmkey;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public Date getIndate() {
		return indate;
	}
	public void setIndate(Date indate) {
		this.indate = indate;
	}
	public Date getOutdate() {
		return outdate;
	}
	public void setOutdate(Date outdate) {
		this.outdate = outdate;
	}
	public String getEquipmentNumber() {
		return equipmentNumber;
	}
	public void setEquipmentNumber(String equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getCarmNumber() {
		return carmNumber;
	}
	public void setCarmNumber(String carmNumber) {
		this.carmNumber = carmNumber;
	}
	public String getPanorama() {
		return panorama;
	}
	public void setPanorama(String panorama) {
		this.panorama = panorama;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getNumberType() {
		return numberType;
	}
	public void setNumberType(String numberType) {
		this.numberType = numberType;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public String getJournalNum() {
		return journalNum;
	}
	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}
	public String getParksId() {
		return parksId;
	}
	public void setParksId(String parksId) {
		this.parksId = parksId;
	}
	public String getChargeState() {
		return chargeState;
	}
	public void setChargeState(String chargeState) {
		this.chargeState = chargeState;
	}
	public String getEquipin() {
		return equipin;
	}
	public void setEquipin(String equipin) {
		this.equipin = equipin;
	}
	public String getEquipout() {
		return equipout;
	}
	public void setEquipout(String equipout) {
		this.equipout = equipout;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getOpen1() {
		return open1;
	}

	public void setOpen1(String open1) {
		this.open1 = open1;
	}

	public String getChargeState1() {
		return chargeState1;
	}

	public void setChargeState1(String chargeState1) {
		this.chargeState1 = chargeState1;
	}


}
