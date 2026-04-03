package com.tiantai.telrec.action;

import hy.ea.bo.human.Staff;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.tiantai.telrec.bo.TelrecCustomerInfo;
import com.tiantai.telrec.bo.TelrecTelInInfo;
import com.tiantai.telrec.service.ClientQueryCustomerService;
import com.tiantai.telrec.tool.QuerySocietalCustomerImpl;
import com.tiantai.telrec.tool.TelAreaCode;

public class ClientQueryCustomer extends ActionSupport {
	/**
	 * 根据电话号码查询客户信息
	 */
	private static final long serialVersionUID = -6390776374792119252L;
	// private TelrecCustomerDaoImpl customerDao;
	private String telno;
	private String issave;
	// private TelInInfoDaoImpl telInDao;
	private String userid;
	private String companyid;
	private String username;
	private String companyname;
	private String orgsimple;
	private String password;
	private ClientQueryCustomerService telInService;

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public String execute() throws Exception {
		// Object obj = UserCheck.Instance().checkUser(orgsimple, username,
		// password);

		TelrecCustomerInfo info = null;
		//System.out.println(telno);
		HttpServletResponse response = ServletActionContext.getResponse();
		telno = telnoSet(telno);
		// 保存来电信息
		String telinId = "";
		if (issave == null)
			telinId = saveTelIn();
		QuerySocietalCustomerImpl impl = QuerySocietalCustomerImpl.instance();
        	
		List list1 = impl.queryCompany(telno);
		List list2 = impl.queryStaff(telno);
		/*
		 * String type = ""; if (list.isEmpty()) {
		 * 
		 * list = impl.queryStaff(telno); if (!list.isEmpty()) { type = "staff"; } }
		 * else {
		 * 
		 * type = "company"; } Object obj = new Object(); if (!list.isEmpty()) {
		 * obj = list.get(0); }
		 */
		Map map = new HashMap();
		if (list1.size() > 0) {
			map.put("company", list1.get(0));
		} else {
			map.put("company", new Object());
		}
		if (list2.size() > 0) {
			map.put("staff", list2.get(0));
			//Staff stf = (Staff)list2.get(0);
			//System.out.println(stf.getStaffName() + "\t" + stf.getReference());
		} else {
			map.put("staff", new Object());
		}
		try {

			JSONObject json = JSONObject.fromObject(map);
			json.put("telinId", telinId);

			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(json.toString());
			//System.out.print(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String saveTelIn() {
		TelrecTelInInfo info = new TelrecTelInInfo();
		info.setTelno(telno);
		info.setUserid(userid);
		info.setUsername(username);
		info.setCompanyid(companyid);
		info.setCompanyname(companyname);
		info.setIn_time(new Date());
		return this.telInService.insertTelInInfo(info);
	}

	// 处理电话号码
	private String telnoSet(String telno) {
		// 1790901065075285
		// 17909013683166721
		TelAreaCode tac = TelAreaCode.getInstance();
		String substr;
		if (telno.length() == 7 || telno.length() == 8) {
			return telno;
		}
		if (telno.length() > 12 && telno.length() == 17) {
			telno = telno.substring(4);
		}
		if (telno.length() == 12) {
			// 处理区号问题
			substr = telno.substring(0, 4);
			if (tac.isExist(substr)) {
				return telno.substring(3);
			}

			Pattern ptn = Pattern
					.compile("[0]{13[0-9]\\d{8}|15[0-9]\\d{8}|18[0-9]\\d{8}");
			Matcher mtc = ptn.matcher(telno);
			if (mtc.matches())
				return telno.substring(1);
		} else if (telno.length() == 11) {
			substr = telno.substring(0, 3);
			if (tac.isExist(substr)) {
				return telno.substring(2);
			}
			try {
				Pattern ptn = Pattern
						.compile("13[0-9]\\d{8}|15[0-9]\\d{8}|18[0-9]\\d{8}");
				Matcher mtc = ptn.matcher(telno);
				if (mtc.matches())
					return telno;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return telno;
	}

	// 将staff对象信息保存到customer对象
	@SuppressWarnings("unused")
	private TelrecCustomerInfo swapBean(Object obj, String telnum) {
		Staff staff = (Staff) obj;
		TelrecCustomerInfo info = new TelrecCustomerInfo();
		info.setCustomer_address(staff.getAddress());
		info.setCustomer_birthday(staff.getBirthday());
		// info.setCustomer_email(staff.)
		// info.setCustomer_fax(staff.get)
		// info.setCustomer_hometel(staff.get)
		info.setCustomer_name(staff.getStaffName());
		info.setCustomer_tel(telnum);
		return info;
	}

	@SuppressWarnings("unused")
	private void saveCustomerInfo(TelrecCustomerInfo info) {
		this.telInService.insertCustomerInfo(info);
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getOrgsimple() {
		return orgsimple;
	}

	public void setOrgsimple(String orgsimple) {
		this.orgsimple = orgsimple;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ClientQueryCustomerService getTelInService() {
		return telInService;
	}

	public void setTelInService(ClientQueryCustomerService telInService) {
		this.telInService = telInService;
	}

	public String getIssave() {
		return issave;
	}

	public void setIssave(String issave) {
		this.issave = issave;
	}

}
