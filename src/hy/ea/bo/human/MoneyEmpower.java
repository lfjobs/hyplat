package hy.ea.bo.human;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 金额授权管理
 * @author wangshuangni
 *
 */
public class MoneyEmpower implements BaseBean {
	/**
	 * 权限主键
	 */
	private String empowerKey;
	/**
	 * 权限id
	 */
	private String empowerId;
	/**
	 * 权限名称
	 */
	private String empowerName;

	/**
	 * 企业类型（0大型 1中型  2小型  3微型  4微小型   5供应商  6 0元购）
	 */
	private String ccomType;
	/**
	 * 状态（0：删除  1：正常）
	 */
	private Integer status;
	/**
	 * 权限描述
	 */
	private String empowerDesc;
	/**
	 * 创建时间
	 * @return
	 */
	private Date createTime;

	public String getEmpowerKey() {
		return empowerKey;
	}

	public void setEmpowerKey(String empowerKey) {
		this.empowerKey = empowerKey;
	}

	public String getEmpowerId() {
		return empowerId;
	}

	public void setEmpowerId(String empowerId) {
		this.empowerId = empowerId;
	}

	public String getEmpowerName() {
		return empowerName;
	}

	public void setEmpowerName(String empowerName) {
		this.empowerName = empowerName;
	}

	public String getCcomType() {
		return ccomType;
	}

	public void setCcomType(String ccomType) {
		this.ccomType = ccomType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEmpowerDesc() {
		return empowerDesc;
	}

	public void setEmpowerDesc(String empowerDesc) {
		this.empowerDesc = empowerDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
