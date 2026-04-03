package hy.ea.bo.human.salary;

import hy.plat.bo.BaseBean;

import java.util.Date;


/**
 * 工资字典
 * @author wangshuangni
 *
 */
public class SalaryDictData implements BaseBean {
	/**
	 * 主键
	 */
	private String dictKey;
	/**
	 * id
	 */
	private String dictId;
	/**
	 * 类型
	 */
	private String dictType;
	/**
	 * 名称
	 */
	private String dictName;
	/**
	 * 值
	 */
	private String dictValue;
	/**
	 * 公司
	 */
	private String companyId;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 设置人id
	 */
	private String createStaffId;
	/**
	 * 创建时间
	 */
	private String createDate;

	public String getDictKey() {
		return dictKey;
	}

	public void setDictKey(String dictKey) {
		this.dictKey = dictKey;
	}

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateStaffId() {
		return createStaffId;
	}

	public void setCreateStaffId(String createStaffId) {
		this.createStaffId = createStaffId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}
