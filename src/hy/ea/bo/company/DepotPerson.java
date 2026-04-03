package hy.ea.bo.company;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
/**
 * 库房责任人
 * 
 * @author Administrator
 *
 */

public class DepotPerson implements BaseBean, ExcelBean,Serializable {
	private String depotPersonID;
	private String depotPersonKey;
	
	private String companyID;  //登录公司ID
	private String organizationID;//部门ID
	private String depotID;//库房ID
	
	private Date SinceTime;//起时间
	private Date StopTime;//止时间
	private String responsible;//责任人
	private String responsibleCode;//责任人编号
	private String responsiblePhone;//责任人电话
	
	
	 public static String[] columnHeadings() {
			String[] titles = { "序号","起时间","止时间","部门","责任人","责任人编号","责任人电话"};
			return titles;
		}
	    
		@Override
		public String[] properties() {
		    String[] properties = {String.format("%1$tF", SinceTime),String.format("%1$tF", StopTime),getCorganizationName(),responsible,responsibleCode,responsiblePhone};
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
				coName = oMap.get(organizationID);
			}
			return coName;
		}
	public String getDepotID() {
			return depotID;
		}

		public void setDepotID(String depotID) {
			this.depotID = depotID;
		}

	public String getDepotPersonID() {
		return depotPersonID;
	}


	public void setDepotPersonID(String depotPersonID) {
		this.depotPersonID = depotPersonID;
	}


	public String getDepotPersonKey() {
		return depotPersonKey;
	}


	public void setDepotPersonKey(String depotPersonKey) {
		this.depotPersonKey = depotPersonKey;
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


	public Date getSinceTime() {
		return SinceTime;
	}


	public void setSinceTime(Date sinceTime) {
		SinceTime = sinceTime;
	}


	public Date getStopTime() {
		return StopTime;
	}


	public void setStopTime(Date stopTime) {
		StopTime = stopTime;
	}

	public String getResponsible() {
		return responsible;
	}


	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}


	public String getResponsibleCode() {
		return responsibleCode;
	}


	public void setResponsibleCode(String responsibleCode) {
		this.responsibleCode = responsibleCode;
	}


	public String getResponsiblePhone() {
		return responsiblePhone;
	}


	public void setResponsiblePhone(String responsiblePhone) {
		this.responsiblePhone = responsiblePhone;
	}
	
	
}
