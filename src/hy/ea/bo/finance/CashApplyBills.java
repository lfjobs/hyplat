package hy.ea.bo.finance;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 现金申请
 * @author lou
 *
 */
public class CashApplyBills implements BaseBean,Cloneable{

	private String cashApplyBillsKey;
	private String cashApplyBillsID;
	private String cashierBillsID; //单据id
	private String goodsBillsID; // 物品单据id
	private String goodsID;// 物品ID
	private String companyID;  //公司id
	private String companyName;  //公司name
	private String organizationName; // 部门名称
	private String csbID;//项目单据id
	private String journalNum; // 黏贴单号
	private String projectName;//项目名称
	private String xmtypename;//项目类型名称
	private String content;  //项目内容
	private String staffName; // 责任人
	private String inputName; // 制单人
	private String goodsName; // 品名名称
	private String quantity; // 数量
	private String price; // 单价
	private String money; // 金额
	private Date cashierDate; // 申请时间
	private String inputDate; // 录入时间
	private String boxStandard; // 资金需求因素
	private String moneySpent; // 规格
	private String status; // 单据状态
	private String ccompanyID;// 往来单位ID
	private String ccompanyName;//往来单位name
	private String UserID;// 往来个人ID
	private String UserName;//往来个人name
	private String canstatus; // 是否作废：作废为00;否则为空
	
	/******************拨款操作字段*****************/
	private String appropriationMoney;//拨款金额
	private String appropriationNote;//拨款备注
	private Date appropriationDate;//拨款时间
	private String appropriationNum;//拨款账号
	private String appropriateStatus;//拨款状态   00：未拨款  01：已拨款  02：暂不拨款
	private String appropriationcompanyID;// 拨款方id
	private String appropriationcompanyName;//拨款方name
	private String receivablesID;// 收款方id
	private String receivablesName;//收款方name
	private String receopenBank;//收款账号开户行
	private String receuserName;//收款账号开户名
	private String recropriationNum;//收款账号
	private String appstyle;  //拨款方式
	

	public CashApplyBills() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected  Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	
	public Object cloneCashApplyBills() throws CloneNotSupportedException{		
		return this.clone();
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((UserID == null) ? 0 : UserID.hashCode());
		result = prime * result
				+ ((UserName == null) ? 0 : UserName.hashCode());
		result = prime
				* result
				+ ((appropriateStatus == null) ? 0 : appropriateStatus
						.hashCode());
		result = prime
				* result
				+ ((appropriationDate == null) ? 0 : appropriationDate
						.hashCode());
		result = prime
				* result
				+ ((appropriationMoney == null) ? 0 : appropriationMoney
						.hashCode());
		result = prime
				* result
				+ ((appropriationNote == null) ? 0 : appropriationNote
						.hashCode());
		result = prime
				* result
				+ ((appropriationNum == null) ? 0 : appropriationNum.hashCode());
		result = prime
				* result
				+ ((appropriationcompanyID == null) ? 0
						: appropriationcompanyID.hashCode());
		result = prime
				* result
				+ ((appropriationcompanyName == null) ? 0
						: appropriationcompanyName.hashCode());
		result = prime * result
				+ ((appstyle == null) ? 0 : appstyle.hashCode());
		result = prime * result
				+ ((boxStandard == null) ? 0 : boxStandard.hashCode());
		result = prime * result
				+ ((canstatus == null) ? 0 : canstatus.hashCode());
		result = prime
				* result
				+ ((cashApplyBillsID == null) ? 0 : cashApplyBillsID.hashCode());
		result = prime
				* result
				+ ((cashApplyBillsKey == null) ? 0 : cashApplyBillsKey
						.hashCode());
		result = prime * result
				+ ((cashierBillsID == null) ? 0 : cashierBillsID.hashCode());
		result = prime * result
				+ ((cashierDate == null) ? 0 : cashierDate.hashCode());
		result = prime * result
				+ ((ccompanyID == null) ? 0 : ccompanyID.hashCode());
		result = prime * result
				+ ((ccompanyName == null) ? 0 : ccompanyName.hashCode());
		result = prime * result
				+ ((companyID == null) ? 0 : companyID.hashCode());
		result = prime * result
				+ ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((csbID == null) ? 0 : csbID.hashCode());
		result = prime * result
				+ ((goodsBillsID == null) ? 0 : goodsBillsID.hashCode());
		result = prime * result + ((goodsID == null) ? 0 : goodsID.hashCode());
		result = prime * result
				+ ((goodsName == null) ? 0 : goodsName.hashCode());
		result = prime * result
				+ ((inputDate == null) ? 0 : inputDate.hashCode());
		result = prime * result
				+ ((inputName == null) ? 0 : inputName.hashCode());
		result = prime * result
				+ ((journalNum == null) ? 0 : journalNum.hashCode());
		result = prime * result + ((money == null) ? 0 : money.hashCode());
		result = prime * result
				+ ((moneySpent == null) ? 0 : moneySpent.hashCode());
		result = prime
				* result
				+ ((organizationName == null) ? 0 : organizationName.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result
				+ ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result
				+ ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result
				+ ((receivablesID == null) ? 0 : receivablesID.hashCode());
		result = prime * result
				+ ((receivablesName == null) ? 0 : receivablesName.hashCode());
		result = prime
				* result
				+ ((recropriationNum == null) ? 0 : recropriationNum.hashCode());
		result = prime * result
				+ ((staffName == null) ? 0 : staffName.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((xmtypename == null) ? 0 : xmtypename.hashCode());
		return result;
	}





	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CashApplyBills other = (CashApplyBills) obj;
		if (UserID == null) {
			if (other.UserID != null)
				return false;
		} else if (!UserID.equals(other.UserID))
			return false;
		if (UserName == null) {
			if (other.UserName != null)
				return false;
		} else if (!UserName.equals(other.UserName))
			return false;
		if (appropriateStatus == null) {
			if (other.appropriateStatus != null)
				return false;
		} else if (!appropriateStatus.equals(other.appropriateStatus))
			return false;
		if (appropriationDate == null) {
			if (other.appropriationDate != null)
				return false;
		} else if (!appropriationDate.equals(other.appropriationDate))
			return false;
		if (appropriationMoney == null) {
			if (other.appropriationMoney != null)
				return false;
		} else if (!appropriationMoney.equals(other.appropriationMoney))
			return false;
		if (appropriationNote == null) {
			if (other.appropriationNote != null)
				return false;
		} else if (!appropriationNote.equals(other.appropriationNote))
			return false;
		if (appropriationNum == null) {
			if (other.appropriationNum != null)
				return false;
		} else if (!appropriationNum.equals(other.appropriationNum))
			return false;
		if (appropriationcompanyID == null) {
			if (other.appropriationcompanyID != null)
				return false;
		} else if (!appropriationcompanyID.equals(other.appropriationcompanyID))
			return false;
		if (appropriationcompanyName == null) {
			if (other.appropriationcompanyName != null)
				return false;
		} else if (!appropriationcompanyName
				.equals(other.appropriationcompanyName))
			return false;
		if (appstyle == null) {
			if (other.appstyle != null)
				return false;
		} else if (!appstyle.equals(other.appstyle))
			return false;
		if (boxStandard == null) {
			if (other.boxStandard != null)
				return false;
		} else if (!boxStandard.equals(other.boxStandard))
			return false;
		if (canstatus == null) {
			if (other.canstatus != null)
				return false;
		} else if (!canstatus.equals(other.canstatus))
			return false;
		if (cashApplyBillsID == null) {
			if (other.cashApplyBillsID != null)
				return false;
		} else if (!cashApplyBillsID.equals(other.cashApplyBillsID))
			return false;
		if (cashApplyBillsKey == null) {
			if (other.cashApplyBillsKey != null)
				return false;
		} else if (!cashApplyBillsKey.equals(other.cashApplyBillsKey))
			return false;
		if (cashierBillsID == null) {
			if (other.cashierBillsID != null)
				return false;
		} else if (!cashierBillsID.equals(other.cashierBillsID))
			return false;
		if (cashierDate == null) {
			if (other.cashierDate != null)
				return false;
		} else if (!cashierDate.equals(other.cashierDate))
			return false;
		if (ccompanyID == null) {
			if (other.ccompanyID != null)
				return false;
		} else if (!ccompanyID.equals(other.ccompanyID))
			return false;
		if (ccompanyName == null) {
			if (other.ccompanyName != null)
				return false;
		} else if (!ccompanyName.equals(other.ccompanyName))
			return false;
		if (companyID == null) {
			if (other.companyID != null)
				return false;
		} else if (!companyID.equals(other.companyID))
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (csbID == null) {
			if (other.csbID != null)
				return false;
		} else if (!csbID.equals(other.csbID))
			return false;
		if (goodsBillsID == null) {
			if (other.goodsBillsID != null)
				return false;
		} else if (!goodsBillsID.equals(other.goodsBillsID))
			return false;
		if (goodsID == null) {
			if (other.goodsID != null)
				return false;
		} else if (!goodsID.equals(other.goodsID))
			return false;
		if (goodsName == null) {
			if (other.goodsName != null)
				return false;
		} else if (!goodsName.equals(other.goodsName))
			return false;
		if (inputDate == null) {
			if (other.inputDate != null)
				return false;
		} else if (!inputDate.equals(other.inputDate))
			return false;
		if (inputName == null) {
			if (other.inputName != null)
				return false;
		} else if (!inputName.equals(other.inputName))
			return false;
		if (journalNum == null) {
			if (other.journalNum != null)
				return false;
		} else if (!journalNum.equals(other.journalNum))
			return false;
		if (money == null) {
			if (other.money != null)
				return false;
		} else if (!money.equals(other.money))
			return false;
		if (moneySpent == null) {
			if (other.moneySpent != null)
				return false;
		} else if (!moneySpent.equals(other.moneySpent))
			return false;
		if (organizationName == null) {
			if (other.organizationName != null)
				return false;
		} else if (!organizationName.equals(other.organizationName))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (receivablesID == null) {
			if (other.receivablesID != null)
				return false;
		} else if (!receivablesID.equals(other.receivablesID))
			return false;
		if (receivablesName == null) {
			if (other.receivablesName != null)
				return false;
		} else if (!receivablesName.equals(other.receivablesName))
			return false;
		if (recropriationNum == null) {
			if (other.recropriationNum != null)
				return false;
		} else if (!recropriationNum.equals(other.recropriationNum))
			return false;
		if (staffName == null) {
			if (other.staffName != null)
				return false;
		} else if (!staffName.equals(other.staffName))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (xmtypename == null) {
			if (other.xmtypename != null)
				return false;
		} else if (!xmtypename.equals(other.xmtypename))
			return false;
		return true;
	}





	public String getCashApplyBillsKey() {
		return cashApplyBillsKey;
	}

	public void setCashApplyBillsKey(String cashApplyBillsKey) {
		this.cashApplyBillsKey = cashApplyBillsKey;
	}

	public String getCashApplyBillsID() {
		return cashApplyBillsID;
	}

	public void setCashApplyBillsID(String cashApplyBillsID) {
		this.cashApplyBillsID = cashApplyBillsID;
	}

	public String getGoodsBillsID() {
		return goodsBillsID;
	}

	public void setGoodsBillsID(String goodsBillsID) {
		this.goodsBillsID = goodsBillsID;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getJournalNum() {
		return journalNum;
	}

	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public Date getCashierDate() {
		return cashierDate;
	}

	public void setCashierDate(Date cashierDate) {
		this.cashierDate = cashierDate;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public String getBoxStandard() {
		return boxStandard;
	}

	public void setBoxStandard(String boxStandard) {
		this.boxStandard = boxStandard;
	}

	public String getMoneySpent() {
		return moneySpent;
	}

	public void setMoneySpent(String moneySpent) {
		this.moneySpent = moneySpent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAppropriationMoney() {
		return appropriationMoney;
	}

	public void setAppropriationMoney(String appropriationMoney) {
		this.appropriationMoney = appropriationMoney;
	}

	public String getAppropriationNote() {
		return appropriationNote;
	}

	public void setAppropriationNote(String appropriationNote) {
		this.appropriationNote = appropriationNote;
	}

	public Date getAppropriationDate() {
		return appropriationDate;
	}

	public void setAppropriationDate(Date appropriationDate) {
		this.appropriationDate = appropriationDate;
	}

	public String getAppropriateStatus() {
		return appropriateStatus;
	}

	public void setAppropriateStatus(String appropriateStatus) {
		this.appropriateStatus = appropriateStatus;
	}
	
	public String getAppropriationcompanyID() {
		return appropriationcompanyID;
	}

	public void setAppropriationcompanyID(String appropriationcompanyID) {
		this.appropriationcompanyID = appropriationcompanyID;
	}

	public String getAppropriationcompanyName() {
		return appropriationcompanyName;
	}

	public void setAppropriationcompanyName(String appropriationcompanyName) {
		this.appropriationcompanyName = appropriationcompanyName;
	}

	public String getAppropriationNum() {
		return appropriationNum;
	}

	public void setAppropriationNum(String appropriationNum) {
		this.appropriationNum = appropriationNum;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public String getCsbID() {
		return csbID;
	}

	public void setCsbID(String csbID) {
		this.csbID = csbID;
	}

	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getXmtypename() {
		return xmtypename;
	}

	public void setXmtypename(String xmtypename) {
		this.xmtypename = xmtypename;
	}

	public String getCanstatus() {
		return canstatus;
	}

	public void setCanstatus(String canstatus) {
		this.canstatus = canstatus;
	}

	public String getCcompanyID() {
		return ccompanyID;
	}

	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}

	public String getCcompanyName() {
		return ccompanyName;
	}

	public void setCcompanyName(String ccompanyName) {
		this.ccompanyName = ccompanyName;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReceivablesID() {
		return receivablesID;
	}
	
	public void setReceivablesID(String receivablesID) {
		this.receivablesID = receivablesID;
	}

	public String getReceivablesName() {
		return receivablesName;
	}

	public void setReceivablesName(String receivablesName) {
		this.receivablesName = receivablesName;
	}
	
	public String getRecropriationNum() {
		return recropriationNum;
	}

	public void setRecropriationNum(String recropriationNum) {
		this.recropriationNum = recropriationNum;
	}
	
	public String getAppstyle() {
		return appstyle;
	}
	
	public void setAppstyle(String appstyle) {
		this.appstyle = appstyle;
	}

	public String getReceopenBank() {
		return receopenBank;
	}

	public void setReceopenBank(String receopenBank) {
		this.receopenBank = receopenBank;
	}

	public String getReceuserName() {
		return receuserName;
	}

	public void setReceuserName(String receuserName) {
		this.receuserName = receuserName;
	}
	
}
