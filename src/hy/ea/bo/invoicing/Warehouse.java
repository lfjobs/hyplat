package hy.ea.bo.invoicing;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * 库房表
 *
 */
public class Warehouse implements BaseBean,ExcelBean,java.io.Serializable{
	private String wareKey;          //主键
	private String wareID;           //业务主键
	private String companyID;        //公司外键
	private String groupCompanySn;   //集团公司外键
	private String wareName;         //名称
	private String wareType;         //类别  1：库  2：区  3：架  4：位
	private String pware;            //父库房外键
	private String parea;            //父区外键
	private String pframe;           //父架外键
	private String states;			// 00正常 01 删除
	
	@Override
	public String[] properties() { 
		return null;
	}
	
	public static String[] columnHeadings() {
		String[] titles = {"序号","公司","库","区","架","位"};
		return titles;
	}
	/**
	 * 主键
	 * @return
	 */
	public String getWareKey() {
		return wareKey;
	}
	/**
	 * 主键
	 * @param wareKey
	 */
	public void setWareKey(String wareKey) {
		this.wareKey = wareKey;
	}
	
	/**
	 * 业务主键
	 * @return
	 */
	public String getWareID() {
		return wareID;
	}
	/**
	 * 业务主键
	 * @param wareID
	 */
	public void setWareID(String wareID) {
		this.wareID = wareID;
	}
	
	/**
	 * 公司外键
	 * @return
	 */
	public String getCompanyID() {
		return companyID;
	}
	/**
	 * 公司外键
	 * @param companyID
	 */
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	
	/**
	 * groupCompanySn
	 * @return
	 */
	public String getGroupCompanySn() {
		return groupCompanySn;
	}
	/**
	 * groupCompanySn
	 * @param groupCompanySn
	 */
	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}
	
	/**
	 * 名称
	 * @return
	 */
	public String getWareName() {
		return wareName;
	}
	/**
	 * 名称
	 * @param wareName
	 */
	public void setWareName(String wareName) {
		this.wareName = wareName;
	}
	
	/**
	 * 类别  1：库  2：区  3：架  4：位
	 * @return
	 */
	public String getWareType() {
		return wareType;
	}
	/**
	 * 类别  1：库  2：区  3：架  4：位
	 * @param wareType
	 */
	public void setWareType(String wareType) {
		this.wareType = wareType;
	}
	
	/**
	 * 父库房外键
	 * @return
	 */
	public String getPware() {
		return pware;
	}
	/**
	 * 父库房外键
	 * @param pware
	 */
	public void setPware(String pware) {
		this.pware = pware;
	}
	
	/**
	 * 父区外键
	 * @return
	 */
	public String getParea() {
		return parea;
	}
	/**
	 * 父区外键
	 * @param parea
	 */
	public void setParea(String parea) {
		this.parea = parea;
	}
	
	/**
	 * 父架外键
	 * @return
	 */
	public String getPframe() {
		return pframe;
	}
	/**
	 * 父架外键
	 * @param pframe
	 */
	public void setPframe(String pframe) {
		this.pframe = pframe;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}
}
