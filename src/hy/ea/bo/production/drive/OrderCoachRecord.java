package hy.ea.bo.production.drive;

import java.util.Date;

import hy.plat.bo.BaseBean;

/**
 * 
 * 
 * 预约教练记录
 * 
 * @author mz
 *
 */
public class OrderCoachRecord implements BaseBean {

	private String ocrKey;
	private String ocrId;
	private Date orderDate;//预约日期
	private String wotiStrdate;//每节课时间开始时间
	private String wotiEnddate;//每节课时间结束时间
	private String subject;//科目
	private String content;//科目内容
	private String trainArea;//训练场地
	private String wotyID;//预约时间段ID
	private String staffcid;//教练人员ID
	private String staffstID;//学员人员ID
	private String stustate;//学员状态;//e.g. 00:未开始;01:学员签到，02：学员签退 03:学员已取消预约04：已点评
	private Date stuqdDate;//学员签到时间
    private Date stuqtDate;//学员签退日期
    private Date stuqxDate;//学员取消预约时间
    private String stuqdArea;//学员签到地址
    private String stuqtArea;//学员签退地址
	private String coachsate;//教练状态;//e.g. 00:未开始；01:教练签到;02:教练签退 03:教练被取消预约
	private Date coaqdDate;//教练签到时间
    private Date coaqtDate;//教练签退日期
    private String coaqdArea;//教练签到地址
    private String coaqtArea;//教练签退地址
    private String cartype;//车型预约车型；e.g.宝马,e.g.桑塔纳
    private String drivelictype;//驾照类型
    
    
	
    
    public String getStuqdArea() {
		return stuqdArea;
	}
	public void setStuqdArea(String stuqdArea) {
		this.stuqdArea = stuqdArea;
	}
	public String getStuqtArea() {
		return stuqtArea;
	}
	public void setStuqtArea(String stuqtArea) {
		this.stuqtArea = stuqtArea;
	}
	public String getCoaqdArea() {
		return coaqdArea;
	}
	public void setCoaqdArea(String coaqdArea) {
		this.coaqdArea = coaqdArea;
	}
	public String getCoaqtArea() {
		return coaqtArea;
	}
	public void setCoaqtArea(String coaqtArea) {
		this.coaqtArea = coaqtArea;
	}
	public String getOcrKey() {
		return ocrKey;
	}
	public void setOcrKey(String ocrKey) {
		this.ocrKey = ocrKey;
	}
	public String getOcrId() {
		return ocrId;
	}
	public void setOcrId(String ocrId) {
		this.ocrId = ocrId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getWotiStrdate() {
		return wotiStrdate;
	}
	public void setWotiStrdate(String wotiStrdate) {
		this.wotiStrdate = wotiStrdate;
	}
	public String getWotiEnddate() {
		return wotiEnddate;
	}
	public void setWotiEnddate(String wotiEnddate) {
		this.wotiEnddate = wotiEnddate;
	}
	public String getStaffcid() {
		return staffcid;
	}
	public void setStaffcid(String staffcid) {
		this.staffcid = staffcid;
	}
	public String getStaffstID() {
		return staffstID;
	}
	public void setStaffstID(String staffstID) {
		this.staffstID = staffstID;
	}
	public String getStustate() {
		return stustate;
	}
	public void setStustate(String stustate) {
		this.stustate = stustate;
	}
	public Date getStuqdDate() {
		return stuqdDate;
	}
	public void setStuqdDate(Date stuqdDate) {
		this.stuqdDate = stuqdDate;
	}
	public Date getStuqtDate() {
		return stuqtDate;
	}
	public void setStuqtDate(Date stuqtDate) {
		this.stuqtDate = stuqtDate;
	}
	public String getCoachsate() {
		return coachsate;
	}
	public void setCoachsate(String coachsate) {
		this.coachsate = coachsate;
	}
	public Date getCoaqdDate() {
		return coaqdDate;
	}
	public void setCoaqdDate(Date coaqdDate) {
		this.coaqdDate = coaqdDate;
	}
	public Date getCoaqtDate() {
		return coaqtDate;
	}
	public void setCoaqtDate(Date coaqtDate) {
		this.coaqtDate = coaqtDate;
	}
	public String getWotyID() {
		return wotyID;
	}
	public void setWotyID(String wotyID) {
		this.wotyID = wotyID;
	}
	public Date getStuqxDate() {
		return stuqxDate;
	}
	public void setStuqxDate(Date stuqxDate) {
		this.stuqxDate = stuqxDate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTrainArea() {
		return trainArea;
	}
	public void setTrainArea(String trainArea) {
		this.trainArea = trainArea;
	}
	public String getCartype() {
		return cartype;
	}
	public void setCartype(String cartype) {
		this.cartype = cartype;
	}
	public String getDrivelictype() {
		return drivelictype;
	}
	public void setDrivelictype(String drivelictype) {
		this.drivelictype = drivelictype;
	}
	
	
	
}