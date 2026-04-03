package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 合单支付已经完结分账的
 * @author mz
 *
 */
public class HdBackupBillHistory implements BaseBean{
	
	private String hdbkey;
	private String hdbID;
	private String journalNum;//用于支付的凭证号


	private String attach;//附件字段



	private Date createDate;//创建时间

	private Date finishDate;//完结分账时间

	private String message;//未成功原因
	private String companyID;//公司ID
	private  String status;//状态0 正常 1：暂时不完结  2.已处理  3佣金分账


	private String money;//分账金额


	public String getHdbkey() {
		return hdbkey;
	}

	public void setHdbkey(String hdbkey) {
		this.hdbkey = hdbkey;
	}

	public String getHdbID() {
		return hdbID;
	}

	public void setHdbID(String hdbID) {
		this.hdbID = hdbID;
	}

	public String getJournalNum() {
		return journalNum;
	}

	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}


	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
}
