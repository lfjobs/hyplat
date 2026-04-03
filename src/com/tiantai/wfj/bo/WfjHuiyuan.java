package com.tiantai.wfj.bo;

import java.util.Date;

import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;


public class WfjHuiyuan implements BaseBean{

	private String huiyuanKey;
	private String huiyuanId;
	private String staffId;//default:root
	private Long huiyuanSn;//卡号
	private String huiyuanPas;//卡密md5
	private Integer huiyuanState;//default:0 未激活 ，1已激活
	private Date huiyuanDate;//default:sysdate
	private String jihuoDate;
	private Integer huiyuanLevel;//default:1 预留
	public WfjHuiyuan() {
		super();
	}
	public WfjHuiyuan(String huiyuanId, String staffId, Long huiyuanSn, Integer huiyuanState, Date huiyuanDate)throws Exception {
		super();
		this.huiyuanId = huiyuanId;
		this.staffId = staffId;
		this.huiyuanSn = huiyuanSn;
		this.huiyuanState = huiyuanState;
		this.huiyuanDate = huiyuanDate;
		
		this.jihuoDate=DateUtil.toStrDateFromUtilDateByFormat(huiyuanDate, "yyyy-MM-dd");
	}
	
	public String getHuiyuanKey() {
		return huiyuanKey;
	}
	public void setHuiyuanKey(String huiyuanKey) {
		this.huiyuanKey = huiyuanKey;
	}
	public String getHuiyuanId() {
		return huiyuanId;
	}
	public void setHuiyuanId(String huiyuanId) {
		this.huiyuanId = huiyuanId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public Long getHuiyuanSn() {
		return huiyuanSn;
	}
	public void setHuiyuanSn(Long huiyuanSn) {
		this.huiyuanSn = huiyuanSn;
	}
	public String getHuiyuanPas() {
		return huiyuanPas;
	}
	public void setHuiyuanPas(String huiyuanPas) {
		this.huiyuanPas = huiyuanPas;
	}
	public Integer getHuiyuanState() {
		return huiyuanState;
	}
	public void setHuiyuanState(Integer huiyuanState) {
		this.huiyuanState = huiyuanState;
	}
	public Date getHuiyuanDate() {
		return huiyuanDate;
	}
	public void setHuiyuanDate(Date huiyuanDate) {
		this.huiyuanDate = huiyuanDate;
	}
	public String getJihuoDate() {
		return jihuoDate;
	}
	public void setJihuoDate(String jihuoDate) {
		this.jihuoDate = jihuoDate;
	}
	public Integer getHuiyuanLevel() {
		return huiyuanLevel;
	}
	public void setHuiyuanLevel(Integer huiyuanLevel) {
		this.huiyuanLevel = huiyuanLevel;
	}
	
	
}
