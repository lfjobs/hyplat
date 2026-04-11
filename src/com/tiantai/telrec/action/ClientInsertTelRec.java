package com.tiantai.telrec.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.human.Staff;
import hy.ea.service.impl.CLoginServiceImpl;
import hy.ea.util.DateUtil;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import hy.tel.bo.TelInRecord;
import hy.tel.bo.TelOutRecord;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.tiantai.telrec.service.ClientInsertTelInfoService;

public class ClientInsertTelRec extends ActionSupport {

	private static final long serialVersionUID = 3069811078224378198L;

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;

	private ClientInsertTelInfoService telInfoService;
	private CLoginServiceImpl login;

	private String user_id;
	private String customer_id;
	private String start_time;
	private String end_time;
	private String content;
	private String telNumber;
	private String in_or_out;

	private String teltype;
	private String user_name;
	private String customer_name;
	private String customer_type;
	private String companyId; 
	
	public String execute() {
		HttpServletResponse response = ServletActionContext.getResponse(); 
		
		try {
			Staff staffer = (Staff)baseBeanService.getBeanByHqlAndParams(" from Staff s where s.staffID in( select t.staffID from CAccount t where t.accountID = ? ) ", new Object[]{this.user_id});
			
			if("呼入".equals(in_or_out)){
				TelInRecord telIn = new TelInRecord();
				telIn.setId(serverService.getServerID("telIn"));
				telIn.setCompany(this.companyId);
				telIn.setCustomId(this.customer_id);
				telIn.setCustomName(this.customer_name);
				telIn.setCustomType(this.customer_type);
				telIn.setIsDel(0);
				telIn.setUser(staffer);
				telIn.setTelNumber(this.telNumber);
				telIn.setRecodeDate(DateUtil.parseDate(DateUtil.C_TIME_PATTON_DEFAULT, DateUtil.getCurrentDate())); 
				telIn.setRecordContent(this.content);
				telIn.setRecordType(this.teltype);
				telIn.setBeginTime(DateUtil.parseDate(DateUtil.C_TIME_PATTON_DEFAULT, this.start_time));
				telIn.setEndTime(DateUtil.parseDate(DateUtil.C_TIME_PATTON_DEFAULT, this.end_time));
				telIn.setIsDeal(0);
				
				baseBeanService.save(telIn); 
				
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(telIn.getId());
				response.flushBuffer();
			}else{
				TelOutRecord telOut = new TelOutRecord();
				telOut.setCompany(this.companyId);
				telOut.setIsDel(0);
				telOut.setVisitedContent(this.content);
				telOut.setVisitedTime(DateUtil.parseDate(DateUtil.C_TIME_PATTON_DEFAULT, DateUtil.getCurrentDate()));
				telOut.setTelType(this.teltype);
				telOut.setVisitedUser(this.customer_name);
				telOut.setTelNumber(this.telNumber);
				telOut.setUser(staffer);
				telOut.setBeginTime(DateUtil.parseDate(DateUtil.C_TIME_PATTON_DEFAULT, this.start_time));
				telOut.setEndTime(DateUtil.parseDate(DateUtil.C_TIME_PATTON_DEFAULT, this.end_time));
				telOut.setId(serverService.getServerID("telOut"));
				
				baseBeanService.save(telOut); 
				
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(telOut.getId());
				response.flushBuffer();
			}
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return null;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getTeltype() {
		return teltype;
	}

	public void setTeltype(String teltype) {
		this.teltype = teltype;
	}
 
	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIn_or_out() {
		return in_or_out;
	}

	public void setIn_or_out(String in_or_out) {
		this.in_or_out = in_or_out;
	}

	public CLoginServiceImpl getLogin() {
		return login;
	}

	public void setLogin(CLoginServiceImpl login) {
		this.login = login;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public ClientInsertTelInfoService getTelInfoService() {
		return telInfoService;
	}

	public void setTelInfoService(ClientInsertTelInfoService telInfoService) {
		this.telInfoService = telInfoService;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

}
