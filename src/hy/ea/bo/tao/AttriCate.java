package hy.ea.bo.tao;

import hy.plat.bo.BaseBean;
/*
 * 
 * 
 * 属性类目对应表
 */
public class AttriCate implements BaseBean{
	private String acKey;
	private String acID;
	private String attriname;//类目名称
	private Integer orders;//排序
	private String cateID;//所属类目
	private String controltype;//控件类型 如：checkbox select input
	private String isMultiValue;//是否有备选值 0 有：1：无
	private String valueType;//值类型 为了进行验证；
	private String required;//是否必填 0 ：不必填 1：必填
	private String status;//是否停用 0:正常 1：停用 9 删除
	
	public String getAcKey() {
		return acKey;
	}
	public void setAcKey(String acKey) {
		this.acKey = acKey;
	}
	public String getAcID() {
		return acID;
	}
	public void setAcID(String acID) {
		this.acID = acID;
	}
	public String getAttriname() {
		return attriname;
	}
	public void setAttriname(String attriname) {
		this.attriname = attriname;
	}
    
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	public String getCateID() {
		return cateID;
	}
	public void setCateID(String cateID) {
		this.cateID = cateID;
	}
	public String getControltype() {
		return controltype;
	}
	public void setControltype(String controltype) {
		this.controltype = controltype;
	}
	public String getIsMultiValue() {
		return isMultiValue;
	}
	public void setIsMultiValue(String isMultiValue) {
		this.isMultiValue = isMultiValue;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
	
	
	
  
    
    
}
