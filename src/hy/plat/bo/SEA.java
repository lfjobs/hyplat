package hy.plat.bo;

import java.util.Date;
/**
 * System Enterprise Application
 * 一个EA标识了一个用户可以使用的企业级应用系统
 *
 */
public class SEA implements BaseBean{
	
	/**
	 * eaKey 主键，与业务无关
	 */
	private String eaKey;
	
	/**
	 * eaID 全局唯一，与业务相关，与eaKey区分开来，以支持不同的数据库
	 */
	private String eaID;
	
	/**
	 * eaName ea名称
	 */
	private String eaName;
	
	/**
	 * eaUsegeDays ea被开发出来时开发组建议用户试用时可以试用的天数
	 */
	private int    eaUsegeDays;
	
	/**
	 * eaCreateDate ea的开发完成日期
	 */
	private Date   eaCreateDate;
	
	/**
	 * eaUpdateDate ea的被开发组最新发布的日期
	 */
	private Date   eaUpdateDate;
	
	/**
	 * eaTeam ea的开发组
	 */
	private String eaTeam;
	
	/**
	 * eaType ea的类型
	 * eaType = 0x: 属于系统的ea，用户看不到系统的ea，不可以购买或试用；x为保留字，现在暂时只用到x=0，没有任何含义
	 * eaType = 1x: 属于应用的ea，x为保留字，当x=0时，是用户注册时就加给用户的ea，比如默认的系统管理ea;当x=1时，是用户可以购买或试用的ea
	 */
	private String eaType;
	
	/**
	 * eaStatus ea的状态
	 * eaStatus = 00: 正常状态，表示此ea已经测试通过，可以被用户注册或试用
	 * eaStatus = 01: 测试状态，表示此ea正在测试阶段，不可以被用户注册或试用
	 * eaStatus = 02: 更新状态，表示此ea已经做了功能上的更新，但并不影响原有的老用户的应用，新用户将购买或试用具有新功能的此ea
	 * 注意：当一个ea的功能需要更新时，为了保证已经使用了此ea的老用户的正常使用及数据安全，此ea开发组要开发新的BO及新BO的Interface，并对新BO及其Interface分配新的菜单及权限
	 * eaStatus = 1x: 非正常状态，x为保留字，处理非正常状态的ea不可以被用户注册或试用，现在暂时只用到x=1，当x=1时表示此ea已经被禁用
	 */
	private String eaStatus;
	
	/**
	 * eaDesc ea的描述
	 */
	private String eaDesc;
	
	/**
	 * ea排序字段
	 */
	private Integer eaSort;

	public String getEaKey() {
		return eaKey;
	}

	public void setEaKey(String eaKey) {
		this.eaKey = eaKey;
	}

	public String getEaID() {
		return eaID;
	}

	public void setEaID(String eaID) {
		this.eaID = eaID;
	}

	public String getEaName() {
		return eaName;
	}

	public void setEaName(String eaName) {
		this.eaName = eaName;
	}

	public int getEaUsegeDays() {
		return eaUsegeDays;
	}

	public void setEaUsegeDays(int eaUsegeDays) {
		this.eaUsegeDays = eaUsegeDays;
	}

	public Date getEaCreateDate() {
		return eaCreateDate;
	}

	public void setEaCreateDate(Date eaCreateDate) {
		this.eaCreateDate = eaCreateDate;
	}

	public Date getEaUpdateDate() {
		return eaUpdateDate;
	}

	public void setEaUpdateDate(Date eaUpdateDate) {
		this.eaUpdateDate = eaUpdateDate;
	}

	public String getEaTeam() {
		return eaTeam;
	}

	public void setEaTeam(String eaTeam) {
		this.eaTeam = eaTeam;
	}

	public String getEaType() {
		return eaType;
	}

	public void setEaType(String eaType) {
		this.eaType = eaType;
	}

	public String getEaStatus() {
		return eaStatus;
	}

	public void setEaStatus(String eaStatus) {
		this.eaStatus = eaStatus;
	}

	public String getEaDesc() {
		return eaDesc;
	}

	public void setEaDesc(String eaDesc) {
		this.eaDesc = eaDesc;
	}

	public Integer getEaSort() {
		return eaSort;
	}

	public void setEaSort(Integer eaSort) {
		this.eaSort = eaSort;
	}
	
}
