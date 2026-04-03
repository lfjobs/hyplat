package hy.ea.bo.tao;

import hy.plat.bo.BaseBean;
/*
 * 
 * 
 * 颜色表
 */
public class AttrColor implements BaseBean{
	private String acoKey;
	private String acoID;
	private String colorname;//颜色名称
	private String colorvalue;//16进制
	private String colortype;//颜色系
	private Integer orders;//排序
	
	public String getAcoKey() {
		return acoKey;
	}
	public void setAcoKey(String acoKey) {
		this.acoKey = acoKey;
	}
	public String getAcoID() {
		return acoID;
	}
	public void setAcoID(String acoID) {
		this.acoID = acoID;
	}
	public String getColorname() {
		return colorname;
	}
	public void setColorname(String colorname) {
		this.colorname = colorname;
	}
	public String getColorvalue() {
		return colorvalue;
	}
	public void setColorvalue(String colorvalue) {
		this.colorvalue = colorvalue;
	}
	public String getColortype() {
		return colortype;
	}
	public void setColortype(String colortype) {
		this.colortype = colortype;
	}
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
    

    
    
    
}
