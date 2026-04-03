package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 收付码商城子订单
 * @author mz
 *
 */
public class ClientOrderDetail implements BaseBean{
	
	private String codkey;
	private String codID;//子订单
	private String coID;//总订单
    private String waiterID;//服务人员ＩＤ
	private String waiterName;//人员名称
	private Date orderDate;//下单时间
	private String remark;//备注
    private String sn;//顺序号
	private String eatType;//就餐类型 堂食 打包
	private String dtotalMoney;
	public String getCodkey() {
		return codkey;
	}

	public void setCodkey(String codkey) {
		this.codkey = codkey;
	}

	public String getCodID() {
		return codID;
	}

	public void setCodID(String codID) {
		this.codID = codID;
	}

	public String getCoID() {
		return coID;
	}

	public void setCoID(String coID) {
		this.coID = coID;
	}

	public String getWaiterID() {
		return waiterID;
	}

	public void setWaiterID(String waiterID) {
		this.waiterID = waiterID;
	}

	public String getWaiterName() {
		return waiterName;
	}

	public void setWaiterName(String waiterName) {
		this.waiterName = waiterName;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getEatType() {
		return eatType;
	}

	public void setEatType(String eatType) {
		this.eatType = eatType;
	}

	public String getDtotalMoney() {
		return dtotalMoney;
	}

	public void setDtotalMoney(String dtotalMoney) {
		this.dtotalMoney = dtotalMoney;
	}
}
