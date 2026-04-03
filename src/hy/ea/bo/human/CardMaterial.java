package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

/**
 * 
 * 获取素材列表提交的数据
 * @author mz
 *
 */
public class CardMaterial  implements BaseBean{
	
	private String materialKey;  //逻辑主键
	private String materialID;	 //业务主键
	private String companyID;   //上传公司
	private String staffID; 		 //上传人员
	private String title;  			 //标题
	private String elaborate;     //描述说明
	private String route;			 //路径
	private String status;         //状态    00：正常  01：删除
	private String type; 			//类型    00：图片  01：视频  02：音频
	public String getMaterialKey() {
		return materialKey;
	}
	public void setMaterialKey(String materialKey) {
		this.materialKey = materialKey;
	}
	public String getMaterialID() {
		return materialID;
	}
	public void setMaterialID(String materialID) {
		this.materialID = materialID;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getElaborate() {
		return elaborate;
	}
	public void setElaborate(String elaborate) {
		this.elaborate = elaborate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
