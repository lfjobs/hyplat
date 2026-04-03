package hy.ea.bo.tao;

import hy.plat.bo.BaseBean;
/*
 * 
 * 
 * 属性尺码表
 */
public class AttrSize implements BaseBean{
	private String asKey;
	private String asID;
	private String asvalue;//尺码值
	private String astype;//尺码类型
	private Integer orders;//排序
	public String getAsKey() {
		return asKey;
	}
	public void setAsKey(String asKey) {
		this.asKey = asKey;
	}
	public String getAsID() {
		return asID;
	}
	public void setAsID(String asID) {
		this.asID = asID;
	}

	public String getAsvalue() {
		return asvalue;
	}
	public void setAsvalue(String asvalue) {
		this.asvalue = asvalue;
	}
	public String getAstype() {
		return astype;
	}
	public void setAstype(String astype) {
		this.astype = astype;
	}
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}

  
    
    
}
