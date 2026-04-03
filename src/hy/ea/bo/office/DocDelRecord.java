package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

public class DocDelRecord implements BaseBean {

	private String key;
	private String delId;
	private String docId;
	private String stage;//所处阶段例如拟稿，，审批，盖章等；
	private String delstate;//是否删除;删除del;
	private String operator;//操作人
	private String deptOperate;//操作部门;
	private Date operateTime;//操作时间
	
	
	private String orgmark;//组织机构标识符
	
	public String getOrgmark() {
		return orgmark;
	}
	public void setOrgmark(String orgmark) {
		this.orgmark = orgmark;
	}
	public String getDeptOperate() {
		return deptOperate;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public void setDeptOperate(String deptOperate) {
		this.deptOperate = deptOperate;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDelId() {
		return delId;
	}
	public void setDelId(String delId) {
		this.delId = delId;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getDelstate() {
		return delstate;
	}
	public void setDelstate(String delstate) {
		this.delstate = delstate;
	}

	

	
	

}
