package hy.ea.bo.tao;

/**
 * 
 * @mz
 * 商品选择的属性 对应表
 * 
 */



import hy.plat.bo.BaseBean;

public class AttriBaby implements BaseBean{
	private String abKey;
	private String abID;
	private String attriname;//属性名称
	private String attrvalue;//选择的属性值(复选框可能选多个值用逗号隔开)或者输入的属性值
	private String acID;//类目属性ID；
	private String orders;//排序
	private String babyID;//所属宝贝ID 外键
	public String getAbKey() {
		return abKey;
	}
	public void setAbKey(String abKey) {
		this.abKey = abKey;
	}
	public String getAbID() {
		return abID;
	}
	public void setAbID(String abID) {
		this.abID = abID;
	}
	
	public String getAttriname() {
		return attriname;
	}
	public void setAttriname(String attriname) {
		this.attriname = attriname;
	}
	public String getAttrvalue() {
		return attrvalue;
	}
	public void setAttrvalue(String attrvalue) {
		this.attrvalue = attrvalue;
	}
	public String getOrders() {
		return orders;
	}
	public void setOrders(String orders) {
		this.orders = orders;
	}
	public String getBabyID() {
		return babyID;
	}
	public void setBabyID(String babyID) {
		this.babyID = babyID;
	}
	public String getAcID() {
		return acID;
	}
	public void setAcID(String acID) {
		this.acID = acID;
	}
	

  
    
    
}
