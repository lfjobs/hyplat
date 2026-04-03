package hy.ea.bo.invoicing;

public class FinancialBillVO implements java.io.Serializable{
	private String goodsName;             	//品名名称
	private String type;                  	//类型
	private String journalNum;              //凭证号
	private String staffID;                 //限定部门内的责任人ID  外键
	private String cstaffRelationship;      //个人往来关系
	private String ccompanyRelationship;    //往来单位关系
	private String cstaffName;          	//往来个人名称
	private String ccompanyName;       		//往来单位名称
	
	/**
	 * 品名名称
	 * @return
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * 品名名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	/**
	 * 类型
	 * @return
	 */
	public String getType() {
		return type;
	}
	/**
	 * 类型
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 凭证号
	 * @return
	 */
	public String getJournalNum() {
		return journalNum;
	}
	/**
	 * 凭证号
	 */
	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}
	/**
	 * 限定部门内的责任人ID  外键
	 * @return
	 */
	public String getStaffID() {
		return staffID;
	}
	/**
	 * 限定部门内的责任人ID  外键
	 */
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	/**
	 * 个人往来关系
	 * @return
	 */
	public String getCstaffRelationship() {
		return cstaffRelationship;
	}
	/**
	 * 个人往来关系
	 */
	public void setCstaffRelationship(String cstaffRelationship) {
		this.cstaffRelationship = cstaffRelationship;
	}
	/**
	 * 往来单位关系
	 * @return
	 */
	public String getCcompanyRelationship() {
		return ccompanyRelationship;
	}
	/**
	 * 往来单位关系
	 */
	public void setCcompanyRelationship(String ccompanyRelationship) {
		this.ccompanyRelationship = ccompanyRelationship;
	}
	/**
	 * 往来个人名称
	 * @return
	 */
	public String getCstaffName() {
		return cstaffName;
	}
	/**
	 * 往来个人名称
	 */
	public void setCstaffName(String cstaffName) {
		this.cstaffName = cstaffName;
	}
	/**
	 * 往来单位名称
	 * @return
	 */
	public String getCcompanyName() {
		return ccompanyName;
	}
	/**
	 * 往来单位名称
	 */
	public void setCcompanyName(String ccompanyName) {
		this.ccompanyName = ccompanyName;
	}
	
	
}
