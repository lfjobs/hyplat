package hy.ea.bo.invoicing;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

public class EarnBudgetDetail implements BaseBean,ExcelBean,java.io.Serializable{

	private String ebdKey;					//主键
	private String ebdID;					//逻辑主键
	private String ebbID;       			//主单据外键
	private String productNum;              	//产品编号=物品编号
	private String productName;             	//产品名称=物品名称
	private String productUnit;             //产品单位
	private String productStandard;           //产品规格
	
	private String bunitPrice;             	//预算单价
	private String bdquantity;              	//日预算数量现当做月预算数量
	private String month;              	//预算月份
    private String bdamount;//日预算金额
    private String bwamount;//周预算金额
    private String bmamount;//月预算金额
    private String bsamount;//季度预算金额
    private String byamount;//年预算金额
    
	private String tunitPrice;             	//调整单价
	private String tdquantity;              	//日调整数量现当做月调整数量
	private String tdamount;//日预算金额
	private String twamount;//周预算金额
	private String tmamount;//月预算金额
	private String tsamount;//季度预算金额
	private String tyamount;//年预算金额
	    
	
	
	private String weight;                 // 重量
    private String goodsID;					//物品外键
    private String manual;                //产品说明书
    private String productPublicity;    //产品宣传 
    private String companyFile;//公司文件
    
    private String goodsNomber;
	private String delStatus;//调整模块删除状态预算00未删除 01调整删除 02调整新增
	private String sztype;//区分收入支出单子

	public static String[] columnHeadings() {
		String[] titles = { "序号", "产品名称","产品编号", "部门", "责任人", "产品类型","产品预算单价","产品数量","产品预算总价"};
		return titles;
	}
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getEbdKey() {
		return ebdKey;
	}
	public void setEbdKey(String ebdKey) {
		this.ebdKey = ebdKey;
	}
	public String getEbdID() {
		return ebdID;
	}
	public void setEbdID(String ebdID) {
		this.ebdID = ebdID;
	}
	public String getEbbID() {
		return ebbID;
	}
	public void setEbbID(String ebbID) {
		this.ebbID = ebbID;
	}
	public String getProductNum() {
		return productNum;
	}
	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductUnit() {
		return productUnit;
	}
	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}
	public String getProductStandard() {
		return productStandard;
	}
	public void setProductStandard(String productStandard) {
		this.productStandard = productStandard;
	}
	
	public String getBunitPrice() {
		return bunitPrice;
	}
	public void setBunitPrice(String bunitPrice) {
		this.bunitPrice = bunitPrice;
	}
	public String getBdquantity() {
		return bdquantity;
	}
	public void setBdquantity(String bdquantity) {
		this.bdquantity = bdquantity;
	}
	public String getBdamount() {
		return bdamount;
	}
	public void setBdamount(String bdamount) {
		this.bdamount = bdamount;
	}
	public String getBwamount() {
		return bwamount;
	}
	public void setBwamount(String bwamount) {
		this.bwamount = bwamount;
	}
	public String getBmamount() {
		return bmamount;
	}
	public void setBmamount(String bmamount) {
		this.bmamount = bmamount;
	}
	public String getBsamount() {
		return bsamount;
	}
	public void setBsamount(String bsamount) {
		this.bsamount = bsamount;
	}
	public String getByamount() {
		return byamount;
	}
	public void setByamount(String byamount) {
		this.byamount = byamount;
	}
	public String getTunitPrice() {
		return tunitPrice;
	}
	public void setTunitPrice(String tunitPrice) {
		this.tunitPrice = tunitPrice;
	}
	public String getTdquantity() {
		return tdquantity;
	}
	public void setTdquantity(String tdquantity) {
		this.tdquantity = tdquantity;
	}
	public String getTdamount() {
		return tdamount;
	}
	public void setTdamount(String tdamount) {
		this.tdamount = tdamount;
	}
	public String getTwamount() {
		return twamount;
	}
	public void setTwamount(String twamount) {
		this.twamount = twamount;
	}
	public String getTmamount() {
		return tmamount;
	}
	public void setTmamount(String tmamount) {
		this.tmamount = tmamount;
	}
	public String getTsamount() {
		return tsamount;
	}
	public void setTsamount(String tsamount) {
		this.tsamount = tsamount;
	}
	public String getTyamount() {
		return tyamount;
	}
	public void setTyamount(String tyamount) {
		this.tyamount = tyamount;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
    
	public String getManual() {
		return manual;
	}
	public void setManual(String manual) {
		this.manual = manual;
	}
	public String getProductPublicity() {
		return productPublicity;
	}
	public void setProductPublicity(String productPublicity) {
		this.productPublicity = productPublicity;
	}
	public String getCompanyFile() {
		return companyFile;
	}
	public void setCompanyFile(String companyFile) {
		this.companyFile = companyFile;
	}

	public String getGoodsNomber() {
		return goodsNomber;
	}
	public void setGoodsNomber(String goodsNomber) {
		this.goodsNomber = goodsNomber;
	}
	public String getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getSztype() {
		return sztype;
	}
	public void setSztype(String sztype) {
		this.sztype = sztype;
	}
	
	
}
