package com.tiantai.wfj.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class WfjJifenDetail implements BaseBean {

	private String jifenDetailKey;
	private String jifenDetailId;
	private String jifenDetailName;// 记录名称
	private Date jifenDetailDate;// 记录时间
	private String jifenDetailScore;// 记录积分量
	private Integer jifenDetailState;// 0：规则来源1:佣金来源
	private String wfjGuizeId;// 规则外键 WfjGuize
	private String wfjJifenId;// 积分外键 WfjJifen
	private String wfjGuizeCalc;//A加(默认)或者M 减
	private String wfjGuizeName;//规则名称
	private String wfjCashId; //金币出入库单据id
	private String wfjOrderId;//订单ID 暂用于货柜积分和金币同时支付的情况，无法确定商品，关联订单ID以便后续查询
	private String wfjGoodId; //订单物品单据id
	private String redPacketId;//红包id   
	private String status;//状态 作废为0;否则为空

	public WfjJifenDetail() {
		super();
	}

	public WfjJifenDetail(String jifenDetailKey, String jifenDetailId,
			String jifenDetailName, Date jifenDetailDate,
			String jifenDetailScore, Integer jifenDetailState,
			String wfjGuizeId, String wfjJifenId, String wfjGuizeCalc,
			String wfjGuizeName, String wfjCashId, String wfjGoodId) {
		super();
		this.jifenDetailKey = jifenDetailKey;
		this.jifenDetailId = jifenDetailId;
		this.jifenDetailName = jifenDetailName;
		this.jifenDetailDate = jifenDetailDate;
		this.jifenDetailScore = jifenDetailScore;
		this.jifenDetailState = jifenDetailState;
		this.wfjGuizeId = wfjGuizeId;
		this.wfjJifenId = wfjJifenId;
		this.wfjGuizeCalc = wfjGuizeCalc;
		this.wfjGuizeName = wfjGuizeName;
		this.wfjCashId = wfjCashId;
		this.wfjGoodId = wfjGoodId;
	}

	public WfjJifenDetail(String jifenDetailKey, String jifenDetailId,
			String jifenDetailName, Date jifenDetailDate,
			String jifenDetailScore, Integer jifenDetailState, String wfjGuizeId, String wfjGuizeCalc,String wfjGuizeName) {
		super();
		this.jifenDetailKey = jifenDetailKey;
		this.jifenDetailId = jifenDetailId;
		this.jifenDetailName = jifenDetailName;
		this.jifenDetailDate = jifenDetailDate;
		this.jifenDetailScore = jifenDetailScore;
		this.jifenDetailState = jifenDetailState;
		this.wfjGuizeId=wfjGuizeId;
		this.wfjGuizeCalc = wfjGuizeCalc;
		this.wfjGuizeName=wfjGuizeName;
	}

	public String getWfjJifenId() {
		return wfjJifenId;
	}

	public void setWfjJifenId(String wfjJifenId) {
		this.wfjJifenId = wfjJifenId;
	}

	public String getJifenDetailKey() {
		return jifenDetailKey;
	}

	public void setJifenDetailKey(String jifenDetailKey) {
		this.jifenDetailKey = jifenDetailKey;
	}

	public String getJifenDetailId() {
		return jifenDetailId;
	}

	public void setJifenDetailId(String jifenDetailId) {
		this.jifenDetailId = jifenDetailId;
	}

	public String getJifenDetailName() {
		return jifenDetailName;
	}

	public void setJifenDetailName(String jifenDetailName) {
		this.jifenDetailName = jifenDetailName;
	}

	public Date getJifenDetailDate() {
		return jifenDetailDate;
	}

	public void setJifenDetailDate(Date jifenDetailDate) {
		this.jifenDetailDate = jifenDetailDate;
	}

	public String getJifenDetailScore() {
		return jifenDetailScore;
	}

	public void setJifenDetailScore(String jifenDetailScore) {
		this.jifenDetailScore = jifenDetailScore;
	}

	public Integer getJifenDetailState() {
		return jifenDetailState;
	}

	public void setJifenDetailState(Integer jifenDetailState) {
		this.jifenDetailState = jifenDetailState;
	}

	public String getWfjGuizeId() {
		return wfjGuizeId;
	}

	public void setWfjGuizeId(String wfjGuizeId) {
		this.wfjGuizeId = wfjGuizeId;
	}

	public String getWfjGuizeCalc() {
		return wfjGuizeCalc;
	}

	public void setWfjGuizeCalc(String wfjGuizeCalc) {
		this.wfjGuizeCalc = wfjGuizeCalc;
	}

	public String getWfjCashId() {
		return wfjCashId;
	}

	public void setWfjCashId(String wfjCashId) {
		this.wfjCashId = wfjCashId;
	}

	public String getWfjGoodId() {
		return wfjGoodId;
	}

	public void setWfjGoodId(String wfjGoodId) {
		this.wfjGoodId = wfjGoodId;
	}

	public String getRedPacketId() {
		return redPacketId;
	}

	public void setRedPacketId(String redPacketId) {
		this.redPacketId = redPacketId;
	}
	
	public String getWfjGuizeName() {
		return wfjGuizeName;
	}

	public void setWfjGuizeName(String wfjGuizeName) {
		this.wfjGuizeName = wfjGuizeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWfjOrderId() {
		return wfjOrderId;
	}

	public void setWfjOrderId(String wfjOrderId) {
		this.wfjOrderId = wfjOrderId;
	}
}
