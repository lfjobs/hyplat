package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

import java.util.Date;
/**
 * 物品条码编码
 * @author lou
 *
 */
public class GoodsNum implements BaseBean{
	private String gnID;
	private String gnKey;
	private String gnPID;				//父id
	private String status;				//状态（00预算  01拨款  02支出凭证  03入库  04出库（入库可能用到）  05物流 06单据占用-用于删除时还原  07彻底出库）
	private String goodsID;				//物品id
	private Date phaseDate;				//阶段时间
	private String companyID;			//公司id
	private String goodsnumber;         //物品序号
	private String goodsCoding;			//物品编码
	private String goodsBillsID;		//物品单据id
	private String cashierBillsID;		//单据id
	private String productId;			//产品ID
	private String goodsBillsOldID;     //保存时保存之前的单据id-用于删除还原使用
	private Date startDate;			//生产日期
	private Date endDate;				//到期日期
	
	
	public String getGnID() {
		return gnID;
	}
	public void setGnID(String gnID) {
		this.gnID = gnID;
	}
	public String getGnKey() {
		return gnKey;
	}
	public void setGnKey(String gnKey) {
		this.gnKey = gnKey;
	}
	public String getGnPID() {
		return gnPID;
	}
	public void setGnPID(String gnPID) {
		this.gnPID = gnPID;
	}
	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}
	public Date getPhaseDate() {
		return phaseDate;
	}
	public void setPhaseDate(Date phaseDate) {
		this.phaseDate = phaseDate;
	}
	public String getGoodsBillsID() {
		return goodsBillsID;
	}
	public void setGoodsBillsID(String goodsBillsID) {
		this.goodsBillsID = goodsBillsID;
	}
	public String getCashierBillsID() {
		return cashierBillsID;
	}
	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getGoodsnumber() {
		return goodsnumber;
	}
	public void setGoodsnumber(String goodsnumber) {
		this.goodsnumber = goodsnumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGoodsCoding() {
		return goodsCoding;
	}
	public void setGoodsCoding(String goodsCoding) {
		this.goodsCoding = goodsCoding;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getGoodsBillsOldID() {
		return goodsBillsOldID;
	}
	public void setGoodsBillsOldID(String goodsBillsOldID) {
		this.goodsBillsOldID = goodsBillsOldID;
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
}
