package hy.ea.bo;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;
/**
 * 系统提醒功能
 * @author xyz
 *
 */
public class SystemRemind implements BaseBean,Serializable{
	private String sremindKey;         //主键
	private String sremindID;          //业务id
	private String scircularType;      //通知类型  '01'待阅  '02'待办
	private String scircularTitle;     //通知标题
	private String scircularText;      //通知内容
	private String sreceiveType;       //接收人分类
	private Date   sreceiveDate;       //接收时间
	private String sremindType;        //提醒方式   '01'页面弹框  '02'电脑客户端  '03'手机客户端提醒
	private String sremindStatus;      //提醒状态   '01'未发送  '02'已发送  '03'发送失败
	private Date   saddDate;           //添加时间
	public String getSremindKey() {
		return sremindKey;
	}
	public void setSremindKey(String sremindKey) {
		this.sremindKey = sremindKey;
	}
	public String getSremindID() {
		return sremindID;
	}
	public void setSremindID(String sremindID) {
		this.sremindID = sremindID;
	}
	public String getScircularType() {
		return scircularType;
	}
	public void setScircularType(String scircularType) {
		this.scircularType = scircularType;
	}
	public String getScircularTitle() {
		return scircularTitle;
	}
	public void setScircularTitle(String scircularTitle) {
		this.scircularTitle = scircularTitle;
	}
	public String getScircularText() {
		return scircularText;
	}
	public void setScircularText(String scircularText) {
		this.scircularText = scircularText;
	}
	public Date getSreceiveDate() {
		return sreceiveDate;
	}
	public void setSreceiveDate(Date sreceiveDate) {
		this.sreceiveDate = sreceiveDate;
	}
	public String getSremindType() {
		return sremindType;
	}
	public void setSremindType(String sremindType) {
		this.sremindType = sremindType;
	}
	public String getSremindStatus() {
		return sremindStatus;
	}
	public void setSremindStatus(String sremindStatus) {
		this.sremindStatus = sremindStatus;
	}
	public Date getSaddDate() {
		return saddDate;
	}
	public void setSaddDate(Date saddDate) {
		this.saddDate = saddDate;
	}
	public String getSreceiveType() {
		return sreceiveType;
	}
	public void setSreceiveType(String sreceiveType) {
		this.sreceiveType = sreceiveType;
	}
	
	
}
