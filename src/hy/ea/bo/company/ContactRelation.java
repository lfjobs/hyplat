package hy.ea.bo.company;

import hy.plat.bo.BaseBean;
/**
 * 往来个人实体
 * @author 陈小丰
 */
public class ContactRelation implements BaseBean{
	private String relationKey;//关联键值
	private String relationID;//关系id
	private String staffID;//
	private String companyID;
	private String relation;
	private String depstatue;
	
	
	public String getDepstatue() {
		return depstatue;
	}
	public void setDepstatue(String depstatue) {
		this.depstatue = depstatue;
	}
	public String getRelationKey() {
		return relationKey;
	}
	public void setRelationKey(String relationKey) {
		this.relationKey = relationKey;
	}
	public String getRelationID() {
		return relationID;
	}
	public void setRelationID(String relationID) {
		this.relationID = relationID;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}

	
}
