package hy.ea.bo.production;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;


/**
 * 
 * 计划生产量
 * 
 * @author mz
 *
 */
public class PlanAmount  implements BaseBean,ExcelBean,java.io.Serializable{

	
    private String pakey;
    private String paid;
    private String tradeCode;
    private String barCode;//条码
    private String productCode;//产品编号
    private String productName;//产品名称
    private String productID;//产品ID
    private String quantity;//数量(结构数量)
    private String planProductNum;//生产量
    private String hours;//每日工作多长时间/时
    private String dayProductNum;//日生产量
    private String dutorID;//负责人ID 
    private String dutorName;//负责人Name
    private Date createDate;//制定日期
    private String companyID;//公司ID
    private String remark;
    
    
	 public static String[] columnHeadings() {
			String[] titles = { "序号","行业","产品编号","产品名称","结构数量","项目产品计划生产量(个/时)","每日工作时间","日生产量","负责人","制定日期"};
			return titles;
		}
		
	 @Override
	public String[] properties() {
		
		 String[] properties = {productCode,tradeCode,productName,quantity,planProductNum,hours,dayProductNum,dutorName,String.format("%1$tF", createDate)};
			return properties;
	}
	public String getPakey() {
		return pakey;
	}
	public void setPakey(String pakey) {
		this.pakey = pakey;
	}
	public String getPaid() {
		return paid;
	}
	public void setPaid(String paid) {
		this.paid = paid;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getPlanProductNum() {
		return planProductNum;
	}
	public void setPlanProductNum(String planProductNum) {
		this.planProductNum = planProductNum;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getDayProductNum() {
		return dayProductNum;
	}
	public void setDayProductNum(String dayProductNum) {
		this.dayProductNum = dayProductNum;
	}
	 
	public String getDutorID() {
		return dutorID;
	}
	public void setDutorID(String dutorID) {
		this.dutorID = dutorID;
	}
	public String getDutorName() {
		return dutorName;
	}
	public void setDutorName(String dutorName) {
		this.dutorName = dutorName;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

   
    
	
    
    
}
