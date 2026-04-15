package hy.ea.bo.production;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.io.Serializable;

public class PTrack implements BaseBean, ExcelBean,Serializable {
	private static final long serialVersionUID = 1L;
	private String ptrackekey; // 主键
	private String ptrackeId;
	private String id;
	private String lot; // 生产批次号
	private String productNumber; // 产品编号
	private String productName; // 产品名称
	private String productionDepartment; // 生产部门
	private String departmentID;
	private String projectLeader; // 项目负责人
	private String projectLeaderID;// 项目负责人ID
	private String throughput; // 生产量
	private String percent; // 合格量
	private String yield; // 合格率
	private String trackTime; // 跟踪日期
	private String trackmanId;
	private String trackman; // 跟踪人
	private String remark; // 备注
	private String status;
	private String companyID;

	public static String[] columnHeadings() {
		String[] titles = { "序号", "生产批次号", "产品编号", "产品名称", "生产部门", "项目负责人",
				"生产量", "合格量", "合格率", "跟踪日期", "跟踪人", "状态", "备注" };
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = { lot, productNumber, productName,
				productionDepartment, projectLeader, throughput, percent,
				yield, trackTime, trackman,
				"00".equals(status) ? "未审核" : "已审核", remark };
		return properties;
	}

	public String getPtrackekey() {
		return ptrackekey;
	}

	public void setPtrackekey(String ptrackekey) {
		this.ptrackekey = ptrackekey;
	}

	public String getPtrackeId() {
		return ptrackeId;
	}

	public void setPtrackeId(String ptrackeId) {
		this.ptrackeId = ptrackeId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductionDepartment() {
		return productionDepartment;
	}

	public void setProductionDepartment(String productionDepartment) {
		this.productionDepartment = productionDepartment;
	}

	public String getProjectLeader() {
		return projectLeader;
	}

	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}

	public String getThroughput() {
		return throughput;
	}

	public void setThroughput(String throughput) {
		this.throughput = throughput;
	}

	public String getTrackTime() {
		return trackTime;
	}

	public void setTrackTime(String trackTime) {
		this.trackTime = trackTime;
	}

	public String getTrackman() {
		return trackman;
	}

	public void setTrackman(String trackman) {
		this.trackman = trackman;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrackmanId() {
		return trackmanId;
	}

	public void setTrackmanId(String trackmanId) {
		this.trackmanId = trackmanId;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getYield() {
		return yield;
	}

	public void setYield(String yield) {
		this.yield = yield;
	}

	public String getProjectLeaderID() {
		return projectLeaderID;
	}

	public void setProjectLeaderID(String projectLeaderID) {
		this.projectLeaderID = projectLeaderID;
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

}