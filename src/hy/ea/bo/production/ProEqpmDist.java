package hy.ea.bo.production;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 设备分配表
 * @author zgzg
 *
 */
public class ProEqpmDist implements BaseBean,ExcelBean ,java.io.Serializable{

	private String pedKey;
	private String pedId;
	private String companyId;//公司Id
	private Date pedStartDate;//开始时间
	private Date pedEndDate;//结束时间
	
	private String ppID;//产品包装业务主键
	private String goodsName;//产品名称
	private String prodctSn;//项目产品编号
	
	
	private String staffId;//社会人力业务主键
	private String staffName;//分配责任人
	private String duty;//职责
	private Date distDate;//分配时间
	private String distRemark;//分配备注
	private String devices;
	
	private String type;		//生产类别 00：订单生产  01：计划生产
	private String category;		//产品类型 00：单产品  01：组装产品
	private String fiveClear;		//组织机构
	
	public static String[] columnHeadings() {
		String[] titles = { "序号", "项目产品编号", "项目产品名称", "开始时间", "结束时间", "分配责任人",
				"职责", "分配时间","设备", "分配备注" };
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = { prodctSn,goodsName,
				String.format("%1$tF", pedStartDate),
				String.format("%1$tF", pedEndDate),
				staffName, duty,
				String.format("%1$tF", distDate),devices,distRemark };
		return properties;
	}
	
	private String sdate;
	private String edate;
	private String odate;
	
	
	
	public String getPedKey() {
		return pedKey;
	}
	public void setPedKey(String pedKey) {
		this.pedKey = pedKey;
	}
	public String getPedId() {
		return pedId;
	}
	public void setPedId(String pedId) {
		this.pedId = pedId;
	}
	public String getPpID() {
		return ppID;
	}
	public void setPpID(String ppID) {
		this.ppID = ppID;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getDistRemark() {
		return distRemark;
	}
	public void setDistRemark(String distRemark) {
		this.distRemark = distRemark;
	}
	public String getProdctSn() {
		return prodctSn;
	}
	public void setProdctSn(String prodctSn) {
		this.prodctSn = prodctSn;
	}
	public Date getPedStartDate() {
		return pedStartDate;
	}
	public void setPedStartDate(Date pedStartDate) {
		this.pedStartDate = pedStartDate;
	}
	public Date getPedEndDate() {
		return pedEndDate;
	}
	public void setPedEndDate(Date pedEndDate) {
		this.pedEndDate = pedEndDate;
	}
	public Date getDistDate() {
		return distDate;
	}
	public void setDistDate(Date distDate) {
		this.distDate = distDate;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	public String getOdate() {
		return odate;
	}
	public void setOdate(String odate) {
		this.odate = odate;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getDevices() {
		return devices;
	}
	public void setDevices(String devices) {
		this.devices = devices;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFiveClear() {
		return fiveClear;
	}
	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}
	
	
}
