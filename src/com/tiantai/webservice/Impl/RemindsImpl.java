package com.tiantai.webservice.Impl;

import hy.ea.bo.CAccount;
import hy.ea.bo.Remind;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import com.tiantai.webservice.Reminds;



@WebService(targetNamespace = "http://service.impf2010.com", endpointInterface = "com.tiantai.webservice.Reminds", name = "Reminds", serviceName = "Reminds")
@Component(value="webservice.Reminds")  
public class RemindsImpl implements Reminds{
	private static Logger log = LoggerFactory.getLogger(LoginImpl.class);	
	
	@Resource
	private BaseBeanService baseBeanService;
	
	/**
	 * 提醒
	 */
	@Override
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")
	public String getRemind(@WebParam(name = "remindType", targetNamespace = "http://service.impf2010.com") String remindType,
			@WebParam(name = "staffID", targetNamespace = "http://service.impf2010.com") String staffID) {
		String hql1="from Remind r where receiveDate<=? and (remindStatus=? or remindStatus=?) and staffID=? and remindType=?";
		List<BaseBean> reList=baseBeanService.getListBeanByHqlAndParams(hql1, new Object[]{new Date(),"01","03",staffID,remindType});
		List<BaseBean> erlList=new ArrayList<BaseBean>();
		if(reList.size()>0){
			for (int i = 0; i < reList.size(); i++) {
				Remind remind=(Remind)reList.get(i);
				remind.setRemindID(remind.getRemindID()==null?"":remind.getRemindID());
				remind.setCompanyID(remind.getCompanyID()==null?"":remind.getCompanyID());
				remind.setOrganizationID(remind.getOrganizationID()==null?"":remind.getOrganizationID());
				remind.setDetailedurl(remind.getDetailedurl()==null?"":remind.getDetailedurl());
				remind.setReceiveDate(remind.getReceiveDate()==null?new Date():remind.getReceiveDate());
				remind.setAddDate(remind.getAddDate()==null?remind.getReceiveDate():remind.getAddDate());
				remind.setCircularText(remind.getCircularText()==null?"":remind.getCircularText());
				remind.setCircularTitle(remind.getCircularTitle()==null?"":remind.getCircularTitle());
				remind.setRemindKey(remind.getRemindKey()==null?"":remind.getRemindKey());
				remind.setRemindStatus(remind.getRemindStatus()==null?"":remind.getRemindStatus());
				remind.setRemindType(remind.getRemindType()==null?"":remind.getRemindType());
				remind.setStaffID(remind.getStaffID()==null?"":remind.getStaffID());
				remind.setStaffName(remind.getStaffName()==null?"":remind.getStaffName());
				remind.setCircularType(remind.getCircularType()==null?"":remind.getCircularType());
				erlList.add(remind);
			}
		}
		return JSON.toJSONString(erlList);
	}
	
	/**
	 * 修改消息状态
	 */
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")
	public String upRemind(@WebParam(name = "remindID", targetNamespace = "http://service.impf2010.com") String remindID) {
		String hql="update dtremind r set r.remindStatus=? where r.remindID=?";
		String[] hqls = new String[] { hql };
		Object[] gparams = {"02",remindID};
		List<Object[]> parmsList = new ArrayList<Object[]>();
		parmsList.add(gparams);
		Boolean bl=null;
		try {
			baseBeanService.executeSqlsByParmsList(null, hqls, parmsList);
			bl=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//logger.error("操作异常", e);
			bl=false;
		}
		return JSON.toJSONString(bl);
	}
	
	/**
	 * 登录系统
	 */
	@WebResult(name = "return", targetNamespace = "http://service.impf2010.com")
	public String getLoginUser(
			@WebParam(name = "userName", targetNamespace = "http://service.impf2010.com") String userName,
			@WebParam(name = "loginPwd", targetNamespace = "http://service.impf2010.com") String loginPwd,
			@WebParam(name = "companyIdentity", targetNamespace = "http://service.impf2010.com") String companyIdentity) {
		
		if (null == userName || null == loginPwd || null == companyIdentity)
			return null;
		String pwd = Utilities.MD5(loginPwd);
		String result = null;
		StringBuilder buffer = new StringBuilder();
		buffer.append(" select staff.staffid from dtcaccount account");
		buffer.append(" join dtcos cos on account.staffId = cos.staffid");
		buffer.append(" join dt_hr_staff staff on staff.staffid =cos.staffid");
		buffer.append(" join dtcompany company on cos.companyid = company.companyid");
		buffer.append(" where company.companyidentifier = ? and cos.status = '01' and account.accountemail = ? and account.accountpassword=? and account.accountstatus = ? order by account.accountemail");
		try {
			String obj = baseBeanService.getObjectBySqlAndParams(buffer.toString(),
					new Object[] { companyIdentity, userName, pwd, "00" }).toString();
			if (null == obj)
				result = "result:用户名或密码错误";
			else
				result = obj;
		} catch (Exception e) {
			logger.error("操作异常", e);
			log.error(e.getMessage());
			result = "result:-500";
		}
		return result;
	}
	
	/**
	 * 历史数据
	 */
	//@WebResult(name = "return", targetNamespace = "http://service.impf2010.com")
	public String getReminds(@WebParam(name = "staffID", targetNamespace = "http://service.impf2010.com") String staffID) {
			CAccount cAccount=(CAccount)baseBeanService.getBeanByHqlAndParams("from CAccount c where c.staffID=?", new Object[]{staffID});
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("account", cAccount);
			return "http://localhost:8080/hyplat/ea/remind/ea_serachHistory.jspa";
		/*String hql1="from Remind r where (remindStatus=? or remindStatus=?) and staffID=?";
		List<BaseBean> reList=baseBeanService.getListBeanByHqlAndParams(hql1, new Object[]{"02","03",staffID});
		return JSON.toJSONString(reList);*/
	}
}
