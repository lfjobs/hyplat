package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.Map;
/**
 * 印章使用日志管理
 * @author Administrator
 *
 */
public class StampLog implements BaseBean,ExcelBean,java.io.Serializable{
	private String stampLogKey;
	private String stampLogID;
	
	private String companyID;
	private String organizationID;
	private String enterpriseStampID;//印章ID
	
	private Date  stampDate;//盖章日期 
	private String stampDateStr;
	private String corganizationID;//部门名称ID
	private String documentsTheme;//盖印文件主题
	private String contractorsCode;//人员编号
	private String contractors;//承办单位人
	private String contractorsId;
	private String stampCopies;//盖章份数
	private String note;//备注
	//后加的
	private String fileId;//具体word文件附件
	private String fileName;//文件名称
	private String docTitle;//所属公文标题
	private String operateType;//操作类型；加盖印章、手写签批
    private String addType;//添加记录的方式；分为自动添加、手动添加。
    private String scanAttach;// 扫描附件
    private String ext;//文件扩展是是word，还是Excel;W,E
    private String gore;
    
    private Date createTime;//创建记录的时间
    private String creator;
    
	
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getAddType() {
		return addType;
	}
	public void setAddType(String addType) {
		this.addType = addType;
	}
	public static String[] columnHeadings() {
		String[] titles = { "序号", "盖章日期", "盖章人编号", "盖章人",
				"签署文件","所属公文"};
		return titles;
	}
	@Override
	public String[] properties() {
		 String[] properties = {String.format("%1$tF", stampDate),contractorsCode,contractors,fileName,docTitle};
			return properties;
	}
	private static Map<String,String> oMap; 
	public static Map<String, String> getOMap() {
		return oMap;
	}
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	public String getCorganizationName(){
		String coName = "";
		if(null!=oMap){
			coName = oMap.get(corganizationID);
		}
		return coName;
	}	
	public String getStampLogKey() {
		return stampLogKey;
	}
	public void setStampLogKey(String stampLogKey) {
		this.stampLogKey = stampLogKey;
	}
	public String getStampLogID() {
		return stampLogID;
	}
	public void setStampLogID(String stampLogID) {
		this.stampLogID = stampLogID;
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
	public String getEnterpriseStampID() {
		return enterpriseStampID;
	}
	public void setEnterpriseStampID(String enterpriseStampID) {
		this.enterpriseStampID = enterpriseStampID;
	}
	public Date getStampDate() {
		return stampDate;
	}
	public void setStampDate(Date stampDate) {
		this.stampDate = stampDate;
	}
	public String getCorganizationID() {
		return corganizationID;
	}
	public void setCorganizationID(String corganizationID) {
		this.corganizationID = corganizationID;
	}
	public String getDocumentsTheme() {
		return documentsTheme;
	}
	public void setDocumentsTheme(String documentsTheme) {
		this.documentsTheme = documentsTheme;
	}
	public String getContractors() {
		return contractors;
	}
	public void setContractors(String contractors) {
		this.contractors = contractors;
	}
	public String getStampCopies() {
		return stampCopies;
	}
	public void setStampCopies(String stampCopies) {
		this.stampCopies = stampCopies;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getContractorsId() {
		return contractorsId;
	}
	public void setContractorsId(String contractorsId) {
		this.contractorsId = contractorsId;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDocTitle() {
		return docTitle;
	}
	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}
	public String getContractorsCode() {
		return contractorsCode;
	}
	public void setContractorsCode(String contractorsCode) {
		this.contractorsCode = contractorsCode;
	}
	public String getScanAttach() {
		return scanAttach;
	}
	public void setScanAttach(String scanAttach) {
		this.scanAttach = scanAttach;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getStampDateStr() {
		return stampDateStr;
	}
	public void setStampDateStr(String stampDateStr) {
		this.stampDateStr = stampDateStr;
	}
	public String getGore() {
		return gore;
	}
	public void setGore(String gore) {
		this.gore = gore;
	}
	
	
	
}
