package hy.ea.bo.human.salary;

import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.List;


/**
 * 级别列表
 * @author wangshuangni
 *
 */
public class SalaryLevelList implements BaseBean {
	/**
	 * 主键
	 */
	private String levelListKey;
	/**
	 * id
	 */
	private String levelListId;
	/**
	 * 公司id
	 */
	private String companyId;
	/**
	 * 序号
	 */
	private String levelSerial;
	/**
	 * 名称
	 */
	private String levelName;
	/**
	 * 名称
	 */
	private String levelNum;
	/**
	 * 设置人id
	 */
	private String createStaffId;
	/**
	 * 创建时间
	 */
	private Date createDate;

	public String getLevelListKey() {
		return levelListKey;
	}

	public void setLevelListKey(String levelListKey) {
		this.levelListKey = levelListKey;
	}

	public String getLevelListId() {
		return levelListId;
	}

	public void setLevelListId(String levelListId) {
		this.levelListId = levelListId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getLevelSerial() {
		return levelSerial;
	}

	public void setLevelSerial(String levelSerial) {
		this.levelSerial = levelSerial;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getCreateStaffId() {
		return createStaffId;
	}

	public void setCreateStaffId(String createStaffId) {
		this.createStaffId = createStaffId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(String levelNum) {
		this.levelNum = levelNum;
	}
}
