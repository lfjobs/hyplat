package hy.ea.bo.human.salary;

import hy.plat.bo.BaseBean;

import java.util.Date;


/**
 * 日期表
 * @author wangshuangni
 *
 */
public class DateInfo implements BaseBean {
	/**
	 * 主键
	 */
	private String id;

	/**
	 * 日期
	 */
	private String date;
	/**
	 * 星期几
	 */
	private String week;
	/**
	 * 类型（0：工作日 1：周末  2：调休日）
	 */
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
