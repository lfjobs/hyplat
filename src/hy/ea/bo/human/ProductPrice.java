/**
 * LogBook
 */
package hy.ea.bo.human;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
/**
 * cxf
 * 产品价格定位
 * @author Administrator
 */
public class ProductPrice implements BaseBean,ExcelBean,java.io.Serializable{
	private String  productpriceKey;
	private String  productpriceID;
	private String  companyID;
	private String 	organizationID;
	private String  goodsID;                 //GoodsManage  ID    
	private String  goodsCoding;             //品名编号
	private String  goodsName;               //品名名称
	private String 	productCost;             //产品基本成本
	private String 	productDistribution;     //产品区域分配
	private String 	profitSales;             //产品利润分析
	private String 	profitSalesProportion;   //产品利润比例
	private String 	productPositioning;      //产品价格定位
	private String 	productsSort;            //产品品种分类
	private String 	remark;                  //备注
	public static String[] columnHeadings() {
		String[] titles = {"序号" , "产品编号", "产品名称", "产品基本成本", "产品区域分配", "产品利润分析", "产品利润比例",
				 "产品价格定位", "产品品种分类", "备注"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = { goodsCoding,goodsName, 
				productCost, productDistribution, profitSales,profitSalesProportion,productPositioning,
				productsSort, remark};
		return properties;
	}
	public String getProductpriceKey() {
		return productpriceKey;
	}
	public void setProductpriceKey(String productpriceKey) {
		this.productpriceKey = productpriceKey;
	}
	public String getProductpriceID() {
		return productpriceID;
	}
	public void setProductpriceID(String productpriceID) {
		this.productpriceID = productpriceID;
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
	public String getProductCost() {
		return productCost;
	}
	public void setProductCost(String productCost) {
		this.productCost = productCost;
	}
	public String getProductDistribution() {
		return productDistribution;
	}
	public void setProductDistribution(String productDistribution) {
		this.productDistribution = productDistribution;
	}
	public String getProfitSales() {
		return profitSales;
	}
	public void setProfitSales(String profitSales) {
		this.profitSales = profitSales;
	}
	public String getProfitSalesProportion() {
		return profitSalesProportion;
	}
	public void setProfitSalesProportion(String profitSalesProportion) {
		this.profitSalesProportion = profitSalesProportion;
	}
	public String getProductPositioning() {
		return productPositioning;
	}
	public void setProductPositioning(String productPositioning) {
		this.productPositioning = productPositioning;
	}
	public String getProductsSort() {
		return productsSort;
	}
	public void setProductsSort(String productsSort) {
		this.productsSort = productsSort;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	public String getGoodsCoding() {
		return goodsCoding;
	}
	public void setGoodsCoding(String goodsCoding) {
		this.goodsCoding = goodsCoding;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
}
