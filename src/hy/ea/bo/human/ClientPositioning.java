/**
 * LogBook
 */
package hy.ea.bo.human;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
/**
 * cxf
 * 产品定位展示
 * @author Administrator
 */
public class ClientPositioning implements BaseBean,ExcelBean,java.io.Serializable{
	private String  clientPositioningKey;
	private String  clientPositioningID;
	private String  companyID;
	private String 	organizationID;
	private String  clientCode;             //客户编号
	private String 	clientName;             //客户名称
	private String 	brand;                  //品牌
	private String 	grade;                  //等级
	private String 	model;                  //型号
	private String 	brandSpecification;     //品牌规格
	private String 	unit ;                  //单位
	private String 	weight;                 //重量
	private String  caseSpecification;      //箱规格
	private String  PhotoFile;              //附件
	
	
	private File photo;
	private String photoFileName;
	private String photoContentType;
	public File getPhoto() {
		return photo;
	}
	public void setPhoto(File photo) {
		this.photo = photo;
	}
	public String getPhotoContentType() {
		return photoContentType;
	}
	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}
	public static String[] columnHeadings() {
		String[] titles = {"序号" , "客户编号", "客户名称", "品牌", "等级", "型号", "品牌规格",
				 "单位", "重量","箱规格"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = { clientCode,clientName,brand, 
				grade, model, brandSpecification,unit,weight,
				caseSpecification};
		return properties;
		
	}
	public String getClientPositioningKey() {
		return clientPositioningKey;
	}
	public void setClientPositioningKey(String clientPositioningKey) {
		this.clientPositioningKey = clientPositioningKey;
	}
	public String getClientPositioningID() {
		return clientPositioningID;
	}
	public void setClientPositioningID(String clientPositioningID) {
		this.clientPositioningID = clientPositioningID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getBrandSpecification() {
		return brandSpecification;
	}
	public void setBrandSpecification(String brandSpecification) {
		this.brandSpecification = brandSpecification;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getCaseSpecification() {
		return caseSpecification;
	}
	public void setCaseSpecification(String caseSpecification) {
		this.caseSpecification = caseSpecification;
	}
	public String getPhotoFile() {
		return PhotoFile;
	}
	public void setPhotoFile(String photoFile) {
		PhotoFile = photoFile;
	}
	public String getPhotoFileName() {
		return photoFileName;
	}
	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}
}
