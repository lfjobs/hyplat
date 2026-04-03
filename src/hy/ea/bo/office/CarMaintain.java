package hy.ea.bo.office;
import hy.ea.bo.Company;
import hy.ea.bo.ExcelBean;
import hy.ea.bo.human.COrganization;
import hy.plat.bo.BaseBean;

import java.util.Date;
/**
 * 车辆保养
 * @author Administrator
 *
 */
public class CarMaintain implements BaseBean ,ExcelBean,java.io.Serializable{
	public Company company;    //为了获得公司的name
    public Company getCompany() {
		return company;
	}
    public COrganization corganization;//获得组织部门的机构名称；
	public COrganization getCorganization() {
		return corganization;
	}

	public void setCorganization(COrganization corganization) {
		this.corganization = corganization;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	private String maintainID;//保养编号
    private String maintainKey;  //业务主键
	private String carID; // 车辆编号
	private String companyID;
	private String organizationID;
	private String repairpeople;//托修方
	private String carnumber;  //车牌号
	private String carType;//车型
	private String maintenanceAddress;//保养地点
	private String engineNum;//发动机编号
	private String frameNumber;//车架号
	private String maintainType;//维护类型
	private String maintainISBN;// 保养合同编号
	private float totalmileage;//出厂里程表示值
	private String qualitysurveyor;// 质量检验员
	private String repaireCompany;//  承保养单位
	private Date intodate;//  进厂日期
	private Date outdate;// 出厂日期
	private String connectcarpeople; //托保方接车人
	private Date takebackdate;// 接车日期
	private String maintainContext ;// 保养内容
    private String maintainCost;//保养费用
    private String remark;//备注
	private String carPeople;//负责人
	public static String[] columnHeadings() {
		String[] titles = { "序号", "车牌号","车型","车驾号","发动机号", "责任人", "保养人", 
				"保养地点", "保养类型","保养合同编号","出厂里程标识值","质量检验员","承保养单位","进场日起","出厂日期","保养方接车人","接车日期","保养内容",
				"保养费用","备注"};
		return titles;
	}

	public String[] properties() {
		String[] properties = {company.getCompanyName(),corganization.getOrganizationName(), carnumber,connectcarpeople
				,maintainContext,repairpeople,String.format("maintainCost", maintainCost),repaireCompany,repaireCompany,remark};
		return properties;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getRepairpeople() {
		return repairpeople;
	}

	public void setRepairpeople(String repairpeople) {
		this.repairpeople = repairpeople;
	}

	public String getCarnumber() {
		return carnumber;
	}

	public void setCarnumber(String carnumber) {
		this.carnumber = carnumber;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getMaintenanceAddress() {
		return maintenanceAddress;
	}

	public void setMaintenanceAddress(String maintenanceAddress) {
		this.maintenanceAddress = maintenanceAddress;
	}

	public String getEngineNum() {
		return engineNum;
	}

	public void setEngineNum(String engineNum) {
		this.engineNum = engineNum;
	}

	public String getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(String frameNumber) {
		this.frameNumber = frameNumber;
	}

	public String getMaintainISBN() {
		return maintainISBN;
	}

	public void setMaintainISBN(String maintainISBN) {
		this.maintainISBN = maintainISBN;
	}

	public float getTotalmileage() {
		return totalmileage;
	}

	public void setTotalmileage(float totalmileage) {
		this.totalmileage = totalmileage;
	}

	public String getQualitysurveyor() {
		return qualitysurveyor;
	}

	public void setQualitysurveyor(String qualitysurveyor) {
		this.qualitysurveyor = qualitysurveyor;
	}

	public String getRepaireCompany() {
		return repaireCompany;
	}

	public void setRepaireCompany(String repaireCompany) {
		this.repaireCompany = repaireCompany;
	}


	public Date getIntodate() {
		return intodate;
	}

	public void setIntodate(Date intodate) {
		this.intodate = intodate;
	}

	public Date getOutdate() {
		return outdate;
	}

	public void setOutdate(Date outdate) {
		this.outdate = outdate;
	}

	public String getConnectcarpeople() {
		return connectcarpeople;
	}

	public void setConnectcarpeople(String connectcarpeople) {
		this.connectcarpeople = connectcarpeople;
	}

	public Date getTakebackdate() {
		return takebackdate;
	}

	public void setTakebackdate(Date takebackdate) {
		this.takebackdate = takebackdate;
	}

	public String getMaintainContext() {
		return maintainContext;
	}

	public void setMaintainContext(String maintainContext) {
		this.maintainContext = maintainContext;
	}

	public String getMaintainID() {
		return maintainID;
	}

	public void setMaintainID(String maintainID) {
		this.maintainID = maintainID;
	}

	public String getMaintainKey() {
		return maintainKey;
	}

	public void setMaintainKey(String maintainKey) {
		this.maintainKey = maintainKey;
	}

	public String getCarID() {
		return carID;
	}

	public void setCarID(String carID) {
		this.carID = carID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getMaintainType() {
		return maintainType;
	}

	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMaintainCost() {
		return maintainCost;
	}

	public void setMaintainCost(String maintainCost) {
		this.maintainCost = maintainCost;
	}

	public String getCarPeople() {
		return carPeople;
	}

	public void setCarPeople(String carPeople) {
		this.carPeople = carPeople;
	}
	
}
