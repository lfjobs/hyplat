package hy.ea.bo.production;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;


/**
 * 
 * 预算计划
 * 
 * @author mz
 *
 */
public class BudgetPlan  implements BaseBean,ExcelBean,java.io.Serializable {

	
    private String bpkey;
    private String bpid;
    private String barCode;//条码
    private String tradeCode;//行业类别
    private String productCode;//产品编号
    private String productName;//产品名称
    private String productID;//产品ID
    private String price;//单价
    private String quantity;//数量
    private String money;//金额
    private String deviceNum;//流水线设备套数
    private String maxBydevice;//单套设备最大用人量
    private String maxByhbyd;//单套设备每小时最大生产量
    private String worktimeByd;//单套设备每天工作多少时间hour
    private String maxDay;//日最大生产量
    private String maxWeek;//周多大生产量
    private String maxMonth;//月最大生产量
    private String maxSeason;//周最大生产量
    private String maxYear;//年最大生产量
    private String companyID;//公司ID
    private Date createDate;//制定日期
    private String type;//类型 01 预算 02 计划
    private String year;//预算年份
    private String producttype;//项目产品分类
    private String remark;
    private String category;	 	//产品类型     00:单产品   01：组装产品
    
    public static String[] columnHeadings() {
		String[] titles = { "序号","行业","产品条码","产品编号","产品名称","流水线设备套数","单套设备最大用人量","单设备每小时最大生产量","设备每天工作时间"
				,"数量","金额","日最大生产量","周最大生产量","月最大生产量","季最大生产量","年最大生产量","计划年份","制定日期",};
		return titles;
	}
	
    @Override
    public String[] properties() {
	
	 String[] properties = {tradeCode,barCode,productCode,productName,deviceNum,maxBydevice,maxByhbyd,worktimeByd,quantity,money,maxDay,maxWeek,maxMonth,maxSeason,maxYear,year,String.format("%1$tF", createDate)};
		return properties;
    }
	public String getBpkey() {
		return bpkey;
	}
	public void setBpkey(String bpkey) {
		this.bpkey = bpkey;
	}
	public String getBpid() {
		return bpid;
	}
	public void setBpid(String bpid) {
		this.bpid = bpid;
	}
	public String getTradeCode() {
		return tradeCode;
	}
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getDeviceNum() {
		return deviceNum;
	}
	public void setDeviceNum(String deviceNum) {
		this.deviceNum = deviceNum;
	}
	public String getMaxBydevice() {
		return maxBydevice;
	}
	public void setMaxBydevice(String maxBydevice) {
		this.maxBydevice = maxBydevice;
	}
	public String getMaxByhbyd() {
		return maxByhbyd;
	}
	public void setMaxByhbyd(String maxByhbyd) {
		this.maxByhbyd = maxByhbyd;
	}
	public String getWorktimeByd() {
		return worktimeByd;
	}
	public void setWorktimeByd(String worktimeByd) {
		this.worktimeByd = worktimeByd;
	}
	public String getMaxDay() {
		return maxDay;
	}
	public void setMaxDay(String maxDay) {
		this.maxDay = maxDay;
	}
	public String getMaxWeek() {
		return maxWeek;
	}
	public void setMaxWeek(String maxWeek) {
		this.maxWeek = maxWeek;
	}
	public String getMaxMonth() {
		return maxMonth;
	}
	public void setMaxMonth(String maxMonth) {
		this.maxMonth = maxMonth;
	}
	public String getMaxSeason() {
		return maxSeason;
	}
	public void setMaxSeason(String maxSeason) {
		this.maxSeason = maxSeason;
	}
	public String getMaxYear() {
		return maxYear;
	}
	public void setMaxYear(String maxYear) {
		this.maxYear = maxYear;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}



	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	  

	
}
