package com.tiantai.telrec.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
@SuppressWarnings("unused")
public class QueryDao {
	private JdbcTemplate jdbcTemplate;
	// 时间段客户来电阶段统计
	
	private final static String QUERY_CUSTOMER_TEL_TOTAL_FORDATA = "";
	// 单个客户来电统计
	private final static String QUERY_SINGLE_CUSTOMER_TEL_TOTAL_FORDATA = "";
	// 员工时间段电话统计
	private final static String QUERY_USER_TEL_TOTAL_FORDATA = "";
	// 员工信息列表
	private final static String QUERY_SINGEL_USER_TEL_TOTAL_FORDATA = "";
	// 员工工作量统计
	private final static String QUERY_USER_CONTENT_TOTAL_FORDATA = "";

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@SuppressWarnings("rawtypes")
	public List queryCutomerTelTotal(String starttime, String endtime) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	public List querySingelCustomerTelTotal(String starttime, String endtime) {
		return null;
	}
	@SuppressWarnings("rawtypes")
	public List queryUserTotal(String starttime, String endtime) {
		return null;
	}
	@SuppressWarnings("rawtypes")
	public List querySingleUserTotal(String starttime, String endtime) {
		return null;
	}
	@SuppressWarnings("rawtypes")
	public List querySingeContentTotal(String starttime, String endtime) {
		return null;
	}
}
