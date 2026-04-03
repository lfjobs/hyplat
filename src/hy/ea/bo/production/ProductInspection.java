package hy.ea.bo.production;

import hy.plat.bo.BaseBean;

public class ProductInspection implements BaseBean{
	private String proInspectionKey;
	private String proInspectionID;
	private String proAssemblyID;					//组装ID
	private String qualifiedQuantity;				//合格数量
	private String unqualifiedQuantity;		//不合格数量
	private String inspectionManID;				//检验人ID
	private String inspectionManName;		//检验人名称
	private String inspectionTime;					//检验时间
	private String residualQuantity;				//合格剩余数量，用于成品入库
	private String status;										//状态      00:检验成功   01：检验失败
	private String singleID;								//制单人ID
	private String singleName;							//制单人名称
	private String singleTime;							//制单时间
	private String remarks;								//备注

	public String getProInspectionKey() {
		return proInspectionKey;
	}
	public void setProInspectionKey(String proInspectionKey) {
		this.proInspectionKey = proInspectionKey;
	}
	public String getProInspectionID() {
		return proInspectionID;
	}
	public void setProInspectionID(String proInspectionID) {
		this.proInspectionID = proInspectionID;
	}
	public String getProAssemblyID() {
		return proAssemblyID;
	}
	public void setProAssemblyID(String proAssemblyID) {
		this.proAssemblyID = proAssemblyID;
	}
	public String getQualifiedQuantity() {
		return qualifiedQuantity;
	}
	public void setQualifiedQuantity(String qualifiedQuantity) {
		this.qualifiedQuantity = qualifiedQuantity;
	}
	public String getUnqualifiedQuantity() {
		return unqualifiedQuantity;
	}
	public void setUnqualifiedQuantity(String unqualifiedQuantity) {
		this.unqualifiedQuantity = unqualifiedQuantity;
	}
	public String getInspectionManID() {
		return inspectionManID;
	}
	public void setInspectionManID(String inspectionManID) {
		this.inspectionManID = inspectionManID;
	}
	public String getInspectionManName() {
		return inspectionManName;
	}
	public void setInspectionManName(String inspectionManName) {
		this.inspectionManName = inspectionManName;
	}
	public String getInspectionTime() {
		return inspectionTime;
	}
	public void setInspectionTime(String inspectionTime) {
		this.inspectionTime = inspectionTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSingleID() {
		return singleID;
	}
	public void setSingleID(String singleID) {
		this.singleID = singleID;
	}
	public String getSingleName() {
		return singleName;
	}
	public void setSingleName(String singleName) {
		this.singleName = singleName;
	}
	public String getSingleTime() {
		return singleTime;
	}
	public void setSingleTime(String singleTime) {
		this.singleTime = singleTime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getResidualQuantity() {
		return residualQuantity;
	}
	public void setResidualQuantity(String residualQuantity) {
		this.residualQuantity = residualQuantity;
	}
	
}
