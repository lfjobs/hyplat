package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Map;

public class ContactType implements BaseBean ,ExcelBean,java.io.Serializable{
	
	private String contactTypeKey;
	private String contactTypeID;
	
	private String ccompanyID;              //往来单位的ID
	private String companyID;			    //登陆系统的公司ID
	
	private String conType;                 //联系方式
	private String conNum;                  //联系号码
	private String conSort;                 //联系类别
	private String conPerson;               //联系人
	private String mark;                    //备注
	
	private static Map<String,String> oMap;
	
	
	public static Map<String, String> getOMap() {
		return oMap;
	}
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	
	public String getContactTypeKey() {
		return contactTypeKey;
	}
	public void setContactTypeKey(String contactTypeKey) {
		this.contactTypeKey = contactTypeKey;
	}
	public String getContactTypeID() {
		return contactTypeID;
	}
	public void setContactTypeID(String contactTypeID) {
		this.contactTypeID = contactTypeID;
	}
	public String getCcompanyID() {
		return ccompanyID;
	}
	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getConType() {
		return conType;
	}
	public void setConType(String conType) {
		this.conType = conType;
	}
	public String getConNum() {
		return conNum;
	}
	public void setConNum(String conNum) {
		this.conNum = conNum;
	}
	public String getConSort() {
		return conSort;
	}
	public void setConSort(String conSort) {
		this.conSort = conSort;
	}
	public String getConPerson() {
		return conPerson;
	}
	public void setConPerson(String conPerson) {
		this.conPerson = conPerson;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	@Override
	public String[] properties() {
		return new String[]{oMap.get(conType),conNum,conSort,conPerson,mark};
	}
	public static String[] columnHeadings() {
		String[] titles = { "序号","联系方式","联系号码","联系类别","联系人","备注"};
		return titles;
	} 
	public String getCcompanyName()
	{
		String companyName="";
		if(oMap!=null)
		{
			companyName = oMap.get(ccompanyID);
		}
		return companyName;
	}
}
