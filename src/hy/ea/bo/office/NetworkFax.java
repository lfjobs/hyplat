package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
/**
 * 网络传真管理
 * @author Administrator
 *
 */

public class NetworkFax implements BaseBean, ExcelBean,java.io.Serializable {
	private String networkFaxID;
	private String networkFaxKey;
	
	private String companyID;  //登录公司ID
	private String organizationID;//部门ID
	
	private String faxCompanyID;//单位名称
	private String faxCode; //传真编号
	private String faxNum; //传真号
	private Date   faxDate;//传真日期
	private String operator;//操作人员    
	private String faxCategory;//传真类别
    private String faxPhoto;//传真附件-图片
    private String faxNote;//备注
    
    public static String[] columnHeadings() {
		String[] titles = { "序号","传真编号","单位名称","传真号","日期","操作人员","传真类别","备注"};
		return titles;
	}
    
	@Override
	public String[] properties() {
	    String[] properties = {faxCode,faxCompanyID,faxNum,String.format("%1$tF", faxDate),operator,faxCategory,faxNote};
		return properties;
	}
	
    


	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getNetworkFaxID() {
		return networkFaxID;
	}
	public void setNetworkFaxID(String networkFaxID) {
		this.networkFaxID = networkFaxID;
	}
	public String getNetworkFaxKey() {
		return networkFaxKey;
	}
	public void setNetworkFaxKey(String networkFaxKey) {
		this.networkFaxKey = networkFaxKey;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getFaxCompanyID() {
		return faxCompanyID;
	}
	public void setFaxCompanyID(String faxCompanyID) {
		this.faxCompanyID = faxCompanyID;
	}

	public String getFaxCode() {
		return faxCode;
	}
	public void setFaxCode(String faxCode) {
		this.faxCode = faxCode;
	}
	public String getFaxNum() {
		return faxNum;
	}
	public void setFaxNum(String faxNum) {
		this.faxNum = faxNum;
	}
	public Date getFaxDate() {
		return faxDate;
	}
	public void setFaxDate(Date faxDate) {
		this.faxDate = faxDate;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getFaxCategory() {
		return faxCategory;
	}
	public void setFaxCategory(String faxCategory) {
		this.faxCategory = faxCategory;
	}

	public String getFaxPhoto() {
		return faxPhoto;
	}
	public void setFaxPhoto(String faxPhoto) {
		this.faxPhoto = faxPhoto;
	}
	public String getFaxNote() {
		return faxNote;
	}
	public void setFaxNote(String faxNote) {
		this.faxNote = faxNote;
	}

	
}
