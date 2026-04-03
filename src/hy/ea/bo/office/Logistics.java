package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.Map;
/**
 * 物流管理
 * @author Administrator
 *
 */
public class Logistics implements BaseBean, ExcelBean,java.io.Serializable {
	private String logisticsID;
	private String logisticsKey;
	private String companyID;
	private String organizationID;
	private String penskelogistics;//物流公司名称
	private String principal;//负责人
	private String goodsName;//货物名称
	private String waybillNum;//运单编号
	private String  quantity;//数量
	private String carrier;//承运人
	private Date startDate;//起运时间
	private Date endDate;//到达时间
	private String staffName;//货物签收人
	private String remark;//备注
	private String state;//是否签收 00签收   01未签收

	public static String[] columnHeadings() {
		String[] titles = { "序号", "物流公司名称", "负责人", "货物名称", "运单编号", "数量",
				"承运人","起运时间","到达时间","货物签收人","备注","是否签收" };
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = { penskelogistics,principal,goodsName,waybillNum,quantity,carrier,
				String.format("%1$tF", startDate), String.format("%1$tF", endDate),staffName,remark, oMap.get(state)};
		return properties;
	}
	private static Map<String, String> oMap;
	public static void setOMap(Map<String, String> map) {
		oMap = map;
	}
	public String getLogisticsID() {
		return logisticsID;
	}
	public void setLogisticsID(String logisticsID) {
		this.logisticsID = logisticsID;
	}
	public String getLogisticsKey() {
		return logisticsKey;
	}
	public void setLogisticsKey(String logisticsKey) {
		this.logisticsKey = logisticsKey;
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
	public String getPenskelogistics() {
		return penskelogistics;
	}
	public void setPenskelogistics(String penskelogistics) {
		this.penskelogistics = penskelogistics;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getWaybillNum() {
		return waybillNum;
	}
	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
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


	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	

}
