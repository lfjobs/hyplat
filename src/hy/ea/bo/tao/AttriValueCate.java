package hy.ea.bo.tao;

import hy.plat.bo.BaseBean;
/*
 * 
 * 
 * 属性类目备选值对应表
 */
public class AttriValueCate implements BaseBean{
	private String avcKey;
	private String avcID;
	private String acID;//属性类目表ID
	private String orders;//排序
	private String value;//属性值
	private String status;//是否停用 0:正常 1：停用 9 删除
	public String getAvcKey() {
		return avcKey;
	}
	public void setAvcKey(String avcKey) {
		this.avcKey = avcKey;
	}
	public String getAvcID() {
		return avcID;
	}
	public void setAvcID(String avcID) {
		this.avcID = avcID;
	}
	public String getAcID() {
		return acID;
	}
	public void setAcID(String acID) {
		this.acID = acID;
	}
	public String getOrders() {
		return orders;
	}
	public void setOrders(String orders) {
		this.orders = orders;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

	
	
	
  
    
    
}
