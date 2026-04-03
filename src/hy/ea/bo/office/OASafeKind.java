package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * 安全检查类别
 * @author Administrator zc
 *
 */
public class OASafeKind implements BaseBean,ExcelBean,java.io.Serializable {
	private String key; 
	private String id;            	//安全检查类别序号
	private String parentID;        //树ID 001:安全类别顶层父类
	private String companyID;		//公司ID
	private String name;			//安全检查类别名称
	private String descRiption;		//描述
	private String guideline;		//检查指标
	private String attachment;		//检查指标附件存储路劲
	/**
	 * 00可停用 01不可停用 98已停用
	 */
	private String status;          //安全类别状态
	
	
	public static String[] columnHeadings() {
		String[] titles = {"序号","安全检查类别名称","描述","检查指标"};
		return titles;
	}
	
	@Override
	public String[] properties() {
		String[] properties = {name,descRiption,guideline};
		return properties;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescRiption() {
		return descRiption;
	}

	public void setDescRiption(String descRiption) {
		this.descRiption = descRiption;
	}

	public String getGuideline() {
		return guideline;
	}

	public void setGuideline(String guideline) {
		this.guideline = guideline;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
	
	
}
