
package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class BonusPointsDetail implements BaseBean,Cloneable{

	private String bonusPointsDetailKey;
	private String bonusPointsDetailId;
	private String bonusPointsDetailName;//积分明细    记录名称
	private String bonusPointsDetailScore;//积分明细   记录积分数
	private Date bonusPointsDetailDate;//积分明细  记录添加积分明细	
	private String wfjGuizeId;// 规则(外键)
	private String bonusPointsId;//积分id（外键）
	private String wfjCashId; //积分出入库单据id（外键）
	private String wfjGoodId; //订单物品单据id（外键）
	private String wfjOrderId;//订单ID 暂用于货柜积分和金币同时支付的情况，无法确定商品，关联订单ID以便后续查询

	private String ppid;//产品表id()外键
	
	
	@Override
	protected  Object clone() throws CloneNotSupportedException{
		return super.clone();
	}	
	public Object cloneBonusPointsDetail() throws CloneNotSupportedException{
		return this.clone();
	}
	public String getBonusPointsDetailKey() { 
		return bonusPointsDetailKey;
	}
	public void setBonusPointsDetailKey(String bonusPointsDetailKey) {
		this.bonusPointsDetailKey = bonusPointsDetailKey;
	}
	public String getBonusPointsDetailId() {
		return bonusPointsDetailId;
	}
	public void setBonusPointsDetailId(String bonusPointsDetailId) {
		this.bonusPointsDetailId = bonusPointsDetailId;
	}
	public String getBonusPointsDetailName() {
		return bonusPointsDetailName;
	}
	public void setBonusPointsDetailName(String bonusPointsDetailName) {
		this.bonusPointsDetailName = bonusPointsDetailName;
	}
			
	public String getBonusPointsDetailScore() {
		return bonusPointsDetailScore;
	}
	public void setBonusPointsDetailScore(String bonusPointsDetailScore) {
		this.bonusPointsDetailScore = bonusPointsDetailScore;
	}
	public Date getBonusPointsDetailDate() {
		return bonusPointsDetailDate;
	}
	public void setBonusPointsDetailDate(Date bonusPointsDetailDate) {
		this.bonusPointsDetailDate = bonusPointsDetailDate;
	}			
	public String getWfjGuizeId() {
		return wfjGuizeId;
	}
	public void setWfjGuizeId(String wfjGuizeId) {
		this.wfjGuizeId = wfjGuizeId;
	}
	public String getBonusPointsId() {
		return bonusPointsId;
	}
	public void setBonusPointsId(String bonusPointsId) {
		this.bonusPointsId = bonusPointsId;
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
	public String getPpid() {
		return ppid;
	}
	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public String getWfjOrderId() {
		return wfjOrderId;
	}

	public void setWfjOrderId(String wfjOrderId) {
		this.wfjOrderId = wfjOrderId;
	}
}

