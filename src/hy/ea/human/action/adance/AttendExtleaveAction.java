package hy.ea.human.action.adance;

import hy.base.action.BaseAction;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.adance.AttendExtleave;
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
public class AttendExtleaveAction extends BaseAction<AttendExtleave>{

	private AttendExtleave extleave = this.getModel();
	private List<BaseBean> beans;
	
	/**
	 * 审核加载数据
	 * @return
	 */
	public String getExtleaveExam(){
		
		pageForm = baseBeanService
				.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber==0?10:pageNumber),
						"from AttendExtleave e where e.companyId=? and e.approveId = ? and e.esastate = ? order by e.days desc",
						new String[]{this.getCurrentAccount().getCompanyID(),this.getCurrentAccount().getStaffID(),"00"});
		
		return "list_exam";
	}
	/**
	 * 审核
	 * @return
	 */
	public String upExtleaveExam(){
		
		String hql = "update AttendExtleave e set e.esastate = ? where e.extleaveId = ?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, new Object[]{extleave.getEsastate(),extleave.getExtleaveId()});
		return "success";
	}
	
	
	/**
	 * 加载数据
	 * @return
	 */
	public String getAttendExtleave(){
		pageForm = baseBeanService
				.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber==0?10:pageNumber),
						"from AttendExtleave e where e.companyId=? order by e.days desc",
						new String[]{this.getCurrentAccount().getCompanyID()});
		return "list";
	}
	/**
	 * 申请加班\请假
	 * @return
	 * @throws ParseException 
	 */
	public String ExtLeave() throws ParseException{
		String hqlcos = " from COS c where c.companyID = ? and c.staffID = ? and c.cosStatus = ? and c.status = ?";
		COS cos = (COS)baseBeanService.getBeanByHqlAndParams(hqlcos, new Object[]{this.getCurrentAccount().getCompanyID(),this.getCurrentAccount().getStaffID(),"50","01"});
		String hqlstaff = " from Staff s where s.staffID = ? ";
		Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams(hqlstaff, new Object[]{this.getCurrentAccount().getStaffID()});
		COrganization org = (COrganization)baseBeanService.getBeanByHqlAndParams("from COrganization o where o.organizationID = ? ", new Object[]{cos.getOrganizationID()});

		beans = new ArrayList<BaseBean>();
		if(null == extleave.getExtleaveId() || "".equals(extleave.getExtleaveId())){
			extleave.setBeginTime(DateUtil.string2Time(extleave.getBegin()));
			extleave.setEndTime(DateUtil.string2Time(extleave.getEnd()));
			extleave.setExtleaveId(serverService.getServerID("extleave"));
			extleave.setGroupCompanySn(this.getCurrentAccount().getCompany().getGroupCompanySn());
			extleave.setCompanyId(this.getCurrentAccount().getCompanyID());
			extleave.setStaffId(staff.getStaffID());
			extleave.setStaffCode(staff.getStaffCode());
			extleave.setStaffName(staff.getStaffName());
			extleave.setOrganizationId(org.getOrganizationID());
			extleave.setOrgName(org.getOrganizationName());
			extleave.setDays(DateUtil.string2Date(DateUtil.toStrDateFromUtilDateByFormat(new Date(), "yyyy-MM-dd")));
			extleave.setEsastate("00");
			extleave.setCtime(new Date());
			extleave.setCname(this.getCurrentAccount().getAccountEmail());
			beans.add(extleave);
		}else{
			extleave.setUtime(new Date());
			extleave.setUname(this.getCurrentAccount().getAccountEmail());
			beans.add(extleave);
		}
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("suc", "操作成功!");
		JSONObject oj = JSONObject.fromObject(map);
		result = oj;
		return "success";
	}

}
