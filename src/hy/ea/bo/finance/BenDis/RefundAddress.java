package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

public class RefundAddress implements BaseBean{
   
	private String raddressKey;
	private String raddressId;
	private String companyId;
	private String rname;        //接收人姓名
	private String rtel;         //手机号码
	private String rarea;        //地址所在地区
	private String rstreet;      //街道详细地址
	private String rpostcode;    //邮编
	private String rphone;       //电话
	private String rremark;      //备注
	private Integer rtype;    // 0:(默认)退货地址 1:发货地址 2:收货地址
	private String status;   //00为默认地址  01//其他地址
	public String getRaddressKey() {
		return raddressKey;
	}
	public void setRaddressKey(String raddressKey) {
		this.raddressKey = raddressKey;
	}
	public String getRaddressId() {
		return raddressId;
	}
	public void setRaddressId(String raddressId) {
		this.raddressId = raddressId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public String getRtel() {
		return rtel;
	}
	public void setRtel(String rtel) {
		this.rtel = rtel;
	}
	public String getRarea() {
		return rarea;
	}
	public void setRarea(String rarea) {
		this.rarea = rarea;
	}
	public String getRstreet() {
		return rstreet;
	}
	public void setRstreet(String rstreet) {
		this.rstreet = rstreet;
	}
	public String getRpostcode() {
		return rpostcode;
	}
	public void setRpostcode(String rpostcode) {
		this.rpostcode = rpostcode;
	}
	public String getRphone() {
		return rphone;
	}
	public void setRphone(String rphone) {
		this.rphone = rphone;
	}
	public String getRremark() {
		return rremark;
	}
	public void setRremark(String rremark) {
		this.rremark = rremark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getRtype() {
		return rtype;
	}
	public void setRtype(Integer rtype) {
		this.rtype = rtype;
	}
}
