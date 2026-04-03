package hy.ea.human.action.adance;

import hy.base.action.BaseAction;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.adance.AttendLog;
import hy.ea.bo.human.adance.AttendLogstus;
import hy.ea.bo.human.adance.AttendTrim;
import hy.ea.bo.human.adance.WorkCalendar;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
@SuppressWarnings("serial")
public class AttendTrimAction extends BaseAction<AttendTrim>{

	
	AttendTrim attendTrim = this.getModel();
	private List<BaseBean> beans;
	
	/**
	 * 加载数据
	 * @return
	 */
	public String getAttendTrim(){
		pageForm = baseBeanService
				.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber==0?10:pageNumber),
						"from AttendTrim t where t.companyId = ? order by t.status desc",
						new String[]{this.getCurrentAccount().getCompanyID()});
		return "list";
	}
	
	/**
	 * 申请补签
	 * @return
	 * @throws ParseException 
	 */
	public String appTrim() throws ParseException{
		
		String hqlcos = " from COS c where c.companyID = ? and c.staffID = ? and c.cosStatus = ? and c.status = ?";
		COS cos = (COS)baseBeanService.getBeanByHqlAndParams(hqlcos, new Object[]{this.getCurrentAccount().getCompanyID(),this.getCurrentAccount().getStaffID(),"50","01"});
		String hqlstaff = " from Staff s where s.staffID = ? ";
		Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams(hqlstaff, new Object[]{this.getCurrentAccount().getStaffID()});
		COrganization org = (COrganization)baseBeanService.getBeanByHqlAndParams("from COrganization o where o.organizationID = ? ", new Object[]{cos.getOrganizationID()});
		
		String hql = " select count(*) from AttendTrim a where a.companyId = ? and a.staffId = ? and a.status = ? and a.days = ? and a.audstate = ?";
		Object[] obj = new Object[]{this.getCurrentAccount().getCompanyID(),staff.getStaffID(),attendTrim.getStatus(),attendTrim.getDays(),"00"};
		int i = baseBeanService.getConutByByHqlAndParams(hql, obj);
		if(i == 0 ){
			beans = new ArrayList<BaseBean>();
			if(null == attendTrim.getAttendTrimId() || "".equals(attendTrim.getAttendTrimId())){
				attendTrim.setAttendTrimId(serverService.getServerID("trim"));
				attendTrim.setCompanyId(this.getCurrentAccount().getCompanyID());
				attendTrim.setStaffId(staff.getStaffID());
				attendTrim.setStaffCode(staff.getStaffCode());
				attendTrim.setStaffName(staff.getStaffName());
				attendTrim.setOrganizationId(org.getOrganizationID());
				attendTrim.setOrgName(org.getOrganizationName());
				attendTrim.setAudstate("00");
				attendTrim.setCtime(new Date());
				attendTrim.setCname(this.getCurrentAccount().getAccountEmail());
				beans.add(attendTrim);
			}
			baseBeanService.executeHqlsByParamsList(beans, null, null);
			result = "操作成功";
		}else{
			result = "补签已经申请!";
		}
		return "success";
	}
	
	/**
	 * 审核 
	 * @return
	 * @throws ParseException 
	 */
	public String audit() throws ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		beans = new ArrayList<BaseBean>();
		String state =attendTrim.getAudstate();
		if(attendTrim.getAudstate().equals("01")){
			String hqltrim = " from AttendTrim t where t.attendTrimId = ?";
			attendTrim = (AttendTrim)baseBeanService.getBeanByHqlAndParams(hqltrim, new Object[]{attendTrim.getAttendTrimId()});
			
			String hqllog =" select l from AttendLog l,WorkCalendar w where l.workcalen = w.workcalendarid and " +
					" w.companyid = ? and w.days = ? and l.staffId = ?";
			Object[] obj = new Object[]{this.getCurrentAccount().getCompanyID(),DateUtil.string2Date(DateUtil.toStrDateFromUtilDateByFormat(attendTrim.getDays(), "yyyy-MM-dd")),attendTrim.getStaffId()};
			AttendLog log = (AttendLog)baseBeanService.getBeanByHqlAndParams(hqllog, obj);
			
			if( log == null){
				log = new AttendLog();
				
				String hqlwc = "from WorkCalendar w where w.companyid = ? and w.days = ?";
				WorkCalendar wc = (WorkCalendar)baseBeanService.getBeanByHqlAndParams(hqlwc, new Object[]{this.getCurrentAccount().getCompanyID(),DateUtil.string2Date(DateUtil.toStrDateFromUtilDateByFormat(attendTrim.getDays(), "yyyy-MM-dd"))});
				String hqlcos = " from COS c where c.companyID = ? and c.staffID = ? and c.cosStatus = ? and c.status = ?";
				COS cos = (COS)baseBeanService.getBeanByHqlAndParams(hqlcos, new Object[]{this.getCurrentAccount().getCompanyID(),attendTrim.getStaffId(),"50","01"});
				
				log.setStaffId(attendTrim.getStaffId());
				log.setWorkcalen(wc.getWorkcalendarid());
				log.setOrganizationid(cos.getOrganizationID());
				log.setCtime(new Date());
				log.setCname(this.getCurrentAccount().getAccountEmail());
				log.setGroupcompanysn(this.getCurrentAccount().getCompany().getGroupCompanySn());
				log.setCompanyid(attendTrim.getCompanyId());
				log.setAttendLogId(serverService.getServerID("log"));
				log.setStatus("补签");
				
				if(attendTrim.getStatus().equals("00")){
					log.setSigncome(attendTrim.getNewtime());
				}else{
					log.setSigngo(attendTrim.getNewtime());
				}
				
				beans.add(log);
			}else{
				if(attendTrim.getStatus().equals("00")){
					log.setSigncome(attendTrim.getNewtime());
				}else{
					log.setSigngo(attendTrim.getNewtime());
				}

				log.setStatus("补签");
				beans.add(log);
			}
			AttendLogstus logstus = new AttendLogstus();
			logstus.setCtime(new Date());
			logstus.setCname(this.getCurrentAccount().getAccountEmail());
			logstus.setGroupcompanysn(this.getCurrentAccount().getCompany().getGroupCompanySn());
			logstus.setCompanyid(attendTrim.getCompanyId());
			logstus.setStaffid(attendTrim.getStaffId());
			logstus.setAlStatus("补签");
			if(attendTrim.getStatus().equals("00")){
				logstus.setAltime("早补签");
			}else{
				logstus.setAltime("晚补签");
			}
			logstus.setAttendLogstusId(serverService.getServerID("logstus"));
			logstus.setAlDate(DateUtil.string2Date(DateUtil.toStrDateFromUtilDateByFormat(attendTrim.getDays(), "yyyy-MM-dd")));
			beans.add(logstus);
			attendTrim.setAudname(this.getCurrentAccount().getAccountEmail());
			attendTrim.setAudstate(state);
			beans.add(attendTrim);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
			map.put("suc", "suc");
		}else if(attendTrim.getAudstate().equals("02")){
			String hql = "update AttendTrim t set t.audstate = ?,t.audname = ? where t.attendTrimId = ?";
			Object[] obj = new Object[]{attendTrim.getAudstate(),this.getCurrentAccount().getAccountEmail(),attendTrim.getAttendTrimId()};
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, obj);
			map.put("suc", "err");
		}
		
		JSONObject oj = JSONObject.fromObject(map);
		result = oj;
		return "success";
	}

}

