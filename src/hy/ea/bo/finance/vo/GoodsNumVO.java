package hy.ea.bo.finance.vo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

public class GoodsNumVO implements BaseBean , ExcelBean {
	private String goodsBillsKey;
	private String goodsBillsID;
	private String companyid;
	private String companyname;
	private String organizationid;
	private String departmentname;
	private String staffid;
	private String staffname;
	private String postname;
	private Date startDate;
	private Date endDate;
	private String journalNum;
	private String goodsname;
	private String costType;
	private String goodsVariableID;
	private String quantity;
	private String price;
	private String status;
	private String Billcheck;
	private String preDeposit;//预入金
	private String receivables;//应收款
	private String Payables;//应付款
	private String bxcz;//报销冲账
	private String bxf;//报消费
	private String crxj;//存入库现金
	private String qcxj;//取出现金
	private String kcxj;//库存现金余额
	private String cryh;//存入银行卡现金
	private String qcyh;//支出银行卡现金
	private String yhxj;//银行卡现金余额
	private String xjye;//现金总余额
	private String xjzye;//公司现金总余额

	public static String[] columnHeadings() {
		String[] titles = {"公司库","部门库", "岗位库", "责任库", "款源日期", "报账日期", "凭证号",
				"附件名称", "费用名称", "费用类别","单位", "数量", "单价", "预入金", "应收款","应付款","报销冲账","报消费", "存入库现金", "取出现金","库存现金余额",
				"存入银行卡现金","支出银行卡现金","银行卡余额","现金总余额","公司现金总余额" };
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = {
				companyname,
				departmentname,
				postname,
				staffname,
				String.format("%1$tF",startDate),
				String.format("%1$tF", endDate),
				journalNum,
				Billcheck,
				goodsname,
				costType,
				goodsVariableID,
				quantity,
				price,
				preDeposit,
				receivables,
				Payables,
				bxcz,
				bxf,
				crxj,
				qcxj,
				kcxj,
				cryh,
				qcyh,
				yhxj,
				xjye,
				xjzye};
		return properties;
	}
	public float nums(String costType,int c,String quantity, String price){
		float numss=0;
		if(costType.equals("预入金")){
			numss=Float.parseFloat(quantity)*Float.parseFloat(price);
		}else if(costType.equals("应收款")){
			if(c==1){
				numss=Float.parseFloat(quantity)*Float.parseFloat(price);
			}
		}else if(costType.equals("应付款")){
			if(c==2){
				numss=Float.parseFloat(quantity)*Float.parseFloat(price);
			}
		}else if(costType.equals("报销冲账")){
			if(c==3){
				numss=Float.parseFloat(quantity)*Float.parseFloat(price);
			}
		}else{
			if(c==4){
				numss=Float.parseFloat(quantity)*Float.parseFloat(price);
			}
		}
		return numss;
	}
	
	public String getGoodsBillsKey() {
		return goodsBillsKey;
	}
	public void setGoodsBillsKey(String goodsBillsKey) {
		this.goodsBillsKey = goodsBillsKey;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	public String getStaffname() {
		return staffname;
	}
	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}
	public String getPostname() {
		return postname;
	}
	public void setPostname(String postname) {
		this.postname = postname;
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
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	public String getGoodsVariableID() {
		return goodsVariableID;
	}
	public void setGoodsVariableID(String goodsVariableID) {
		this.goodsVariableID = goodsVariableID;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getGoodsBillsID() {
		return goodsBillsID;
	}
	public void setGoodsBillsID(String goodsBillsID) {
		this.goodsBillsID = goodsBillsID;
	}
	public String getJournalNum() {
		return journalNum;
	}
	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}
	public String getPreDeposit() {
		return preDeposit;
	}
	public void setPreDeposit(String preDeposit) {
		this.preDeposit = preDeposit;
	}
	public String getReceivables() {
		return receivables;
	}
	public void setReceivables(String receivables) {
		this.receivables = receivables;
	}
	public String getPayables() {
		return Payables;
	}
	public void setPayables(String payables) {
		Payables = payables;
	}
	public String getBxcz() {
		return bxcz;
	}
	public void setBxcz(String bxcz) {
		this.bxcz = bxcz;
	}
	public String getBxf() {
		return bxf;
	}
	public void setBxf(String bxf) {
		this.bxf = bxf;
	}
	public String getCrxj() {
		return crxj;
	}
	public void setCrxj(String crxj) {
		this.crxj = crxj;
	}
	public String getQcxj() {
		return qcxj;
	}
	public void setQcxj(String qcxj) {
		this.qcxj = qcxj;
	}
	public String getKcxj() {
		return kcxj;
	}
	public void setKcxj(String kcxj) {
		this.kcxj = kcxj;
	}
	public String getCryh() {
		return cryh;
	}
	public void setCryh(String cryh) {
		this.cryh = cryh;
	}
	public String getQcyh() {
		return qcyh;
	}
	public void setQcyh(String qcyh) {
		this.qcyh = qcyh;
	}
	public String getYhxj() {
		return yhxj;
	}
	public void setYhxj(String yhxj) {
		this.yhxj = yhxj;
	}
	public String getXjye() {
		return xjye;
	}
	public void setXjye(String xjye) {
		this.xjye = xjye;
	}
	public String getXjzye() {
		return xjzye;
	}
	public void setXjzye(String xjzye) {
		this.xjzye = xjzye;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getStaffid() {
		return staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBillcheck() {
		return Billcheck;
	}

	public void setBillcheck(String billcheck) {
		Billcheck = billcheck;
	}
}
