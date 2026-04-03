package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * 企业合同管理
 * @author Administrator
 *
 */
public class EnterpriseAgreement implements BaseBean,ExcelBean,java.io.Serializable{

	private String enterpriseAgreementKey;
	private String enterpriseAgreementID;
	private String companyID;
	private String organizationID;
	
	private String serialNumber;			  //合同编号
	private String enName;					  //合同名称
	private String enType;					  //合同类别
	private String subject;					  //合同概要
	private String enFile;					  //合同文件
	private String firstCompany;			  //甲方单位名称
	private String firstPerson;				  //甲方法人代表
	private String firstSignName;			  //甲方签约人
	private String firstSignTel;			  //甲方签约人电话
	
	private String otherCompany;			  //乙方单位名称
	private String otherPerson;				  //乙方法人代表
	private String otherSignName;			  //乙方签约人
	private String otherSignTel;			  //乙方签约人电话
	
	private Date startDate;					  //合同生效日期
	private Date endDate;					  //合同终止日期
	
	private String remind;					  //合同到期提醒
	
	private String enEdit;					  //编辑中存储的路径
	private String fileContent;		  //编辑中用于存储的内容
	private String enScan;					  //合同扫描件
	private File    photo;
	private String photoContentType;
	private String photoFileName;
	
	private String enSource;				  //合同源文件
	private File   sourcePhoto;
	private String sourcePhotoContentType;
	private String sourcePhotoFileName;
	
	/*添加文件盒*/
	private Date managesStartDate;			  //管理起时间
	private Date managesEndDate;			  //管理止时间
	private String responsibler;			  //责任人
	private String fileBoxName;				  //文件盒名称
	private String fileFrame;                 //文件框
	private String numbers;					  //编号
	
	public static String[] columnHeadings(){
		String[] titles = {"序号","合同编号","合同名称","合同类别","合同概要","合同文件","甲方单位名称","甲方法人代表","甲方签约人","甲方签约人电话","乙方单位名称","乙方法人代表","乙方签约人","乙方签约人电话","合同生效日期","合同终止日期","合同到期提醒","管理起时间","管理止时间","责任人","文件盒名称","文件框","编号"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] titles = {serialNumber,enName,enType,subject,enFile,firstCompany,firstPerson,firstSignName,firstSignTel,otherCompany,otherPerson,otherSignName,otherSignTel,String.format("%1$tF", startDate),String.format("%1$tF", endDate),remind,
				String.format("%1$tF", managesStartDate),String.format("%1$tF", managesEndDate),responsibler,fileBoxName,fileFrame,numbers};
		return titles;
	} 
	public String getEnterpriseAgreementID() {
		return enterpriseAgreementID;
	}
	public void setEnterpriseAgreementID(String enterpriseAgreementID) {
		this.enterpriseAgreementID = enterpriseAgreementID;
	}
	public String getEnterpriseAgreementKey() {
		return enterpriseAgreementKey;
	}
	public void setEnterpriseAgreementKey(String enterpriseAgreementKey) {
		this.enterpriseAgreementKey = enterpriseAgreementKey;
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
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	 
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	 
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getEnType() {
		return enType;
	}
	public void setEnType(String enType) {
		this.enType = enType;
	}
	public String getEnFile() {
		return enFile;
	}
	public void setEnFile(String enFile) {
		this.enFile = enFile;
	}
	public String getFirstCompany() {
		return firstCompany;
	}
	public void setFirstCompany(String firstCompany) {
		this.firstCompany = firstCompany;
	}
	public String getFirstPerson() {
		return firstPerson;
	}
	public void setFirstPerson(String firstPerson) {
		this.firstPerson = firstPerson;
	}
	public String getFirstSignName() {
		return firstSignName;
	}
	public void setFirstSignName(String firstSignName) {
		this.firstSignName = firstSignName;
	}
	public String getFirstSignTel() {
		return firstSignTel;
	}
	public void setFirstSignTel(String firstSignTel) {
		this.firstSignTel = firstSignTel;
	}
	public String getOtherCompany() {
		return otherCompany;
	}
	public void setOtherCompany(String otherCompany) {
		this.otherCompany = otherCompany;
	}
	public String getOtherPerson() {
		return otherPerson;
	}
	public void setOtherPerson(String otherPerson) {
		this.otherPerson = otherPerson;
	}
	public String getOtherSignName() {
		return otherSignName;
	}
	public void setOtherSignName(String otherSignName) {
		this.otherSignName = otherSignName;
	}
	public String getOtherSignTel() {
		return otherSignTel;
	}
	public void setOtherSignTel(String otherSignTel) {
		this.otherSignTel = otherSignTel;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getRemind() {
		return remind;
	}
	public void setRemind(String remind) {
		this.remind = remind;
	}
	public static Map<String, String> getOMap() {
		return oMap;
	}
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	public Date getManagesStartDate() {
		return managesStartDate;
	}
	public void setManagesStartDate(Date managesStartDate) {
		this.managesStartDate = managesStartDate;
	}
	public Date getManagesEndDate() {
		return managesEndDate;
	}
	public void setManagesEndDate(Date managesEndDate) {
		this.managesEndDate = managesEndDate;
	}
	public String getResponsibler() {
		return responsibler;
	}
	public void setResponsibler(String responsibler) {
		this.responsibler = responsibler;
	}
	public String getFileBoxName() {
		return fileBoxName;
	}
	public void setFileBoxName(String fileBoxName) {
		this.fileBoxName = fileBoxName;
	}
	public String getNumbers() {
		return numbers;
	}
	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}
	public String getEnEdit() {
		return enEdit;
	}
	public void setEnEdit(String enEdit) {
		this.enEdit = enEdit;
	}
	public String getEnScan() {
		return enScan;
	}
	public void setEnScan(String enScan) {
		this.enScan = enScan;
	}
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
	public String getPhotoFileName() {
		return photoFileName;
	}
	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}
	public String getEnSource() {
		return enSource;
	}
	public void setEnSource(String enSource) {
		this.enSource = enSource;
	}
	public File getSourcePhoto() {
		return sourcePhoto;
	}
	public void setSourcePhoto(File sourcePhoto) {
		this.sourcePhoto = sourcePhoto;
	}
	public String getSourcePhotoContentType() {
		return sourcePhotoContentType;
	}
	public void setSourcePhotoContentType(String sourcePhotoContentType) {
		this.sourcePhotoContentType = sourcePhotoContentType;
	}
	public String getSourcePhotoFileName() {
		return sourcePhotoFileName;
	}
	public void setSourcePhotoFileName(String sourcePhotoFileName) {
		this.sourcePhotoFileName = sourcePhotoFileName;
	}
	private static Map<String,String> oMap;
	public String getFileFrame() {
		return fileFrame;
	}
	public void setFileFrame(String fileFrame) {
		this.fileFrame = fileFrame;
	}
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

}
