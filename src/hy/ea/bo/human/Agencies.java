/**
 * Company Organization
 */
package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.File;
import java.util.Date;
/**
 * 机构负责人
 * @author zgzg
 *
 */
public class Agencies implements BaseBean ,ExcelBean{
	private String agenciesKey;
	private String agenciesID;		   //负责人ID
	private Date   statDate;           //岗位开始时间
	private Date   endDate;            //岗位结束时间
	private String staffID;				//负责人ID(FK)
	private String agenciesCode;       //负责人编号
	private String agenciesName;       //负责人名
	private String organizationID;     //部门ID
	private String organizationPID;    //上级部门ID 
	private String tep;                //负责人电话
	private String octep;              //公司电话
	private String agenciesContent;    //负责内容
	private String photoPath;          //图片路劲
	private String remarks;            //备注
	
	private String isHost;            //是否微分金店长
	
	private File   photo;
	private String photoFileName;
	private String photoContentType;
	
	public static String[] columnHeadings() {
		String[] titles = { "序号","负责人编号","岗位开始时间","岗位结束时间","负责人","负责人电话","公司电话","负责内容","备注"};
		return titles;
	}
	
	
	public String[] properties() {
		String[] properties = {agenciesCode,String.format("%1$tF", statDate),String.format("%1$tF", endDate),
				agenciesName,tep,octep,agenciesContent,remarks };
		return properties;
	}
	
	public String getAgenciesKey() {
		return agenciesKey;
	}
	public void setAgenciesKey(String agenciesKey) {
		this.agenciesKey = agenciesKey;
	}
	public String getAgenciesID() {
		return agenciesID;
	}
	public void setAgenciesID(String agenciesID) {
		this.agenciesID = agenciesID;
	}
	public String getAgenciesName() {
		return agenciesName;
	}
	public void setAgenciesName(String agenciesName) {
		this.agenciesName = agenciesName;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getOrganizationPID() {
		return organizationPID;
	}
	public void setOrganizationPID(String organizationPID) {
		this.organizationPID = organizationPID;
	}
	public Date getStatDate() {
		return statDate;
	}
	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getAgenciesContent() {
		return agenciesContent;
	}
	public void setAgenciesContent(String agenciesContent) {
		this.agenciesContent = agenciesContent;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getTep() {
		return tep;
	}
	public void setTep(String tep) {
		this.tep = tep;
	}
	public String getAgenciesCode() {
		return agenciesCode;
	}
	public void setAgenciesCode(String agenciesCode) {
		this.agenciesCode = agenciesCode;
	}
	public String getOctep() {
		return octep;
	}
	public void setOctep(String octep) {
		this.octep = octep;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
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
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}


	public String getPhotoFileName() {
		return photoFileName;
	}


	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}


	public String getIsHost() {
		return isHost;
	}


	public void setIsHost(String isHost) {
		this.isHost = isHost;
	}
}
