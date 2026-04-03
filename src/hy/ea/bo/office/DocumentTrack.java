package hy.ea.bo.office;

import hy.plat.bo.BaseBean;
/**
 * 
 * 用于跟踪公文的所有操作记录
 * @author mz
 *
 */
public class DocumentTrack implements BaseBean {

	private String key;
	private String trackId;
	private String docId;
	private String operateContent;// 操作内容
	private String operatorID;// 操作人
	private String deptIDofOperator;// 操作人部门
	private String compnayIDofOperator;// 操作人公司
	private String operaterTime;// 操作时间

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getOperateContent() {
		return operateContent;
	}

	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	public String getOperatorID() {
		return operatorID;
	}

	public void setOperatorID(String operatorID) {
		this.operatorID = operatorID;
	}

	public String getOperaterTime() {
		return operaterTime;
	}

	public void setOperaterTime(String operaterTime) {
		this.operaterTime = operaterTime;
	}

	public String getDeptIDofOperator() {
		return deptIDofOperator;
	}

	public void setDeptIDofOperator(String deptIDofOperator) {
		this.deptIDofOperator = deptIDofOperator;
	}

	public String getCompnayIDofOperator() {
		return compnayIDofOperator;
	}

	public void setCompnayIDofOperator(String compnayIDofOperator) {
		this.compnayIDofOperator = compnayIDofOperator;
	}

}
