package hy.ea.bo.company;

import hy.plat.bo.BaseBean;


//库房管理
public class DepotManage implements BaseBean{
	private String depotKey;
	private String depotID;
	private String depotPID;
	private String companyID;
	
	private int depotNum;//序号
	private String depotName;//库房名称
	private String itemID;//项目类别
	private String depotCoding;//类型编码
	private String useState;//使用状态
	private String remark;//备注
	private String depotState;   //库房状态    （00正常  01删除 02预设数据--不可删除）
	private String useraccount;//微分金增加新字段 表示用户账号
	private String usertype;//微分金新增字段表示用户会员类别

	private String depotType;         //类别  1：库  2：区  3：架  4：位

	private String depotAddress;  //定位地址
	private String dwLnglatX;    //经度
	private String dwLnglatY;   //纬度
	private String depotshare;   //是否共享  0：共享  1：不共享


	public String getDepotKey() {
		return depotKey;
	}
	public String getUseraccount() {
		return useraccount;
	}
	public void setUseraccount(String useraccount) {
		this.useraccount = useraccount;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public void setDepotKey(String depotKey) {
		this.depotKey = depotKey;
	}
	public String getDepotID() {
		return depotID;
	}
	public void setDepotID(String depotID) {
		this.depotID = depotID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getDepotName() {
		return depotName;
	}
	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}
	public String getItemID() {
		return itemID;
	}
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	public String getDepotCoding() {
		return depotCoding;
	}
	public void setDepotCoding(String depotCoding) {
		this.depotCoding = depotCoding;
	}
	public String getUseState() {
		return useState;
	}
	public void setUseState(String useState) {
		this.useState = useState;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDepotState() {
		return depotState;
	}
	public void setDepotState(String depotState) {
		this.depotState = depotState;
	}
	public String getDepotPID() {
		return depotPID;
	}
	public void setDepotPID(String depotPID) {
		this.depotPID = depotPID;
	}
	public int getDepotNum() {
		return depotNum;
	}
	public void setDepotNum(int depotNum) {
		this.depotNum = depotNum;
	}

	public String getDepotType() {
		return depotType;
	}

	public void setDepotType(String depotType) {
		this.depotType = depotType;
	}

	public String getDepotAddress() {
		return depotAddress;
	}

	public void setDepotAddress(String depotAddress) {
		this.depotAddress = depotAddress;
	}

	public String getDwLnglatX() {
		return dwLnglatX;
	}

	public void setDwLnglatX(String dwLnglatX) {
		this.dwLnglatX = dwLnglatX;
	}

	public String getDwLnglatY() {
		return dwLnglatY;
	}

	public void setDwLnglatY(String dwLnglatY) {
		this.dwLnglatY = dwLnglatY;
	}

	public String getDepotshare() {
		return depotshare;
	}

	public void setDepotshare(String depotshare) {
		this.depotshare = depotshare;
	}
}
