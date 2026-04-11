package hy.ea.production.action.cprocedure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.production.recruit.TalentPool;
import hy.ea.bo.production.resume.ResumeS;
import hy.ea.production.service.BidsRecruitService;
import hy.ea.util.MobileMessage;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TalentResumeAction {
	private static final Logger logger = LoggerFactory.getLogger(TalentResumeAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Autowired
	private MobileMessage msg;// 发短信zzl
	private TalentPool talentPool;
	private PageForm pageForm;
	private String result;
	private int pageNumber;
	private List<BaseBean> list;
	@Resource
	private BidsRecruitService bidsRecruitService;
	
	/*
	 * 获取列表
	 */
	public String getListPage() throws ParseException{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		String jobTitle=re.getParameter("jobTitle");
		String sex=re.getParameter("sex");
		String parameter=re.getParameter("parameter");
		String educationValue=re.getParameter("educationValue");
		String postDates=re.getParameter("postDates");
		String postDatee=re.getParameter("postDatee");
		String sajax = re.getParameter("sajax");
		StringBuffer sql=new StringBuffer();
		List<Object> obj=new ArrayList<Object>();
		sql.append("select t.tpId,f.staffName,f.sex,nvl(ri.jobTitle,j.position),round(sum((sr.endTime - sr.startTime)/30)/12,1)");
		sql.append(",f.educationValue,to_char(t.postDate,'yyyy-MM-dd HH:mi:ss'),t.state,t.type,r.staffID,f.staffIdentityCard,f.reference,t.resId,t.resumeID");
		sql.append(" from dtTalentPool t");
		sql.append(" left join dtresumeS r on (t.resumeID=r.resumeID or t.resId = r.resumeid)");
		sql.append(" left join dtJobWanted j on r.resumeID = j.resumeID");
		sql.append(" left join dt_hr_Staff_Resume sr on sr.resumeID=r.resumeID");
		sql.append(" left join dt_hr_staff f on f.staffID = r.staffID");
		sql.append(" left join dtRecruitInfo ri on t.riId=ri.riId");
		sql.append(" where t.companyId=?");
		obj.add(account.getCompanyID());
		if(!"".equals(jobTitle)&&jobTitle!=null){
			sql.append(" and ri.jobTitle like ?");
			obj.add("%"+jobTitle+"%");
		}
		if(!"".equals(sex)&&sex!=null){
			sql.append(" and f.sex like ?");
			obj.add("%"+sex+"%");
		}
		if(!"".equals(educationValue)&&educationValue!=null){
			sql.append(" and f.educationValue like ?");
			obj.add("%"+educationValue+"%");
		}
		if(!"".equals(parameter)&&parameter!=null){
			sql.append(" and (f.staffName like ? or f.staffIdentityCard like ? or f.reference like ?)");
			obj.add("%"+parameter+"%");
			obj.add("%"+parameter+"%");
			obj.add("%"+parameter+"%");
		}
		if(talentPool.getState()!=null&&!talentPool.getState().equals("")){
			if(talentPool.getState().equals("1")){
				sql.append(" and (t.state =  ? or t.state = ?)");
				obj.add("00");
				obj.add("01");
			}else if(talentPool.getState().equals("3")){
				sql.append(" and t.state =  ? ");
				obj.add("00");
			}

		}
		if(!"".equals(postDates)&&postDates!=null){
			sql.append(" and t.postDate >= ?");
			obj.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(postDates));
		}
		if(!"".equals(postDatee)&&postDatee!=null){
			sql.append(" and t.postDate <= ?");
			obj.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(postDatee));
		}
		sql.append(" group by r.resumeID,t.tpId,f.staffName,ri.jobTitle,f.sex,f.educationValue,t.postDate,t.state,j.position,t.type,r.staffID,f.staffIdentityCard,f.reference,t.resId,t.resumeID");
		sql.append(" order by t.postDate desc");
		pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql.toString(), "select count(*) from ("+sql.toString()+")",
				obj.toArray());

          if("sajax".equals(sajax)){
          	Map<String,Object> map = new HashMap<String,Object>();
          	map.put("pageForm",pageForm);
			  JSONObject jsonObject = JSONObject.fromObject(map);
			  this.result = jsonObject.toString();
			  return "success";
		  }
	     list = baseBeanService.getListBeanByHqlAndParams("from RecruitInfo where companyID = ? and status = ?",new Object[]{account.getCompanyID(),"01"});
         if(talentPool.getState()!=null&&!talentPool.getState().equals("")){
			 HttpSession session1 = ServletActionContext.getRequest().getSession();
			 SessionWrap sw = SessionWrap.getInstance();
			 HttpServletRequest request=ServletActionContext.getRequest();

			 TEshopCusCom scc= (TEshopCusCom) sw.getObject(session1, SessionWrap.KEY_SHOPCUSCOM);
			 if (scc!=null) {
				 re.setAttribute("sccId", scc.getSccId());
			 }
         	return  "nt";
		 }
		return "list";
	}

	/*
	 * 获取详情页面
	 */
	public String getPageData(){
		HttpServletRequest re=ServletActionContext.getRequest();
		String str=re.getParameter("str");
		StringBuffer sql=new StringBuffer();
		sql.append("select t.tpId,j.position,f.staffname,j.region,f.headimage,f.sex,f.nativePlace,");
		sql.append(" f.localAreaValue,f.referenceOrganization,f.reference,j.salary,r.evaluate,f.birthday");
		sql.append(",round(sum((sr.endTime - sr.startTime)/30)/12,1),r.resumeID,t.state");
		sql.append(" from  dtTalentPool t");
		sql.append(" left join  dtresumeS r on(t.resumeID=r.resumeID or t.resId = r.resumeid)");
		sql.append(" left join dt_hr_Staff_Resume sr on sr.resumeID=r.resumeID");
		sql.append(" left join dtJobWanted j on r.resumeID = j.resumeID");
		sql.append(" left join dt_hr_staff f on f.staffID = r.staffID");
		sql.append(" where t.tpId = ?");
		sql.append(" group by t.tpId,j.position,f.staffname,j.region,f.headimage,f.sex,f.nativePlace,f.localAreaValue,");
		sql.append("f.referenceOrganization,f.reference,j.salary,r.evaluate,f.birthday,r.resumeID,t.state");
		Object[] obj=(Object[]) baseBeanService.getObjectBySqlAndParams(sql.toString(),new Object[]{str});
		
		//教育经历
		String hqledu = "from Educational where resumeID = ? order by admissionTime";
		List<BaseBean> edulist = baseBeanService.getListBeanByHqlAndParams(hqledu,new Object[]{obj[14]});
		//工作经历
		String hqlsr = "from StaffResume where resumeID = ? order by startTime";
		List<BaseBean> srlist = baseBeanService.getListBeanByHqlAndParams(hqlsr,new Object[]{obj[14]});
		
		re.setAttribute("obj",obj); re.setAttribute("srlist",srlist); re.setAttribute("edulist",edulist); 
		return "data";
	}
	/*
	 * 发送短信通知
	 */
	public String sendMessages() throws ParseException, IOException{
		 HttpServletRequest re=ServletActionContext.getRequest();
		 Map<String, Object> session = ActionContext.getContext().getSession();
		 CAccount account = (CAccount) session.get("account");
		 Company company = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?",new Object[]{account.getCompanyID()});
		 String ws=re.getParameter("ws");String sj=re.getParameter("sj");
		 String tpID=re.getParameter("tpID");String rq=re.getParameter("rq");
		 String isSMS=re.getParameter("isSMS");String xm=re.getParameter("xm");
		 String[] tpIDs=tpID.split(",");String[] xms=xm.split(",");
		 List<BaseBean> list=new ArrayList<BaseBean>();
		 String[] sjs=sj.split(":");
		 if("下午".equals(ws))
			 sjs[0]=Integer.parseInt(sjs[0])+12+"";
		 if(sjs[0].length()==1)
			 sjs[0]="0"+sjs[0];
		 Date d=new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(rq+" "+sjs[0]+":"+sjs[1]);
		 StringBuilder sqlr=new StringBuilder();

		 sqlr.append("select t.tpId,f.staffName,f.reference,nvl(ri.jobTitle,j.position)");
		 sqlr.append(" from dtTalentPool t");
		 sqlr.append(" left join dtresumeS r on (t.resumeID=r.resumeID or t.resId = r.resumeid)");
		 sqlr.append(" left join dtJobWanted j on r.resumeID = j.resumeID");
		 sqlr.append(" left join dt_hr_staff f on f.staffID = r.staffID");
		 sqlr.append(" left join dtRecruitInfo ri on t.riId=ri.riId");
		 sqlr.append(" where t.tpId in(");
		 
		
		 for(int i=0;i<tpIDs.length;i++){
			 TalentPool tp=(TalentPool) baseBeanService.getBeanByHqlAndParams(
					 " from  TalentPool where tpId=?", new Object[]{tpIDs[i]});
			 tp.setState("01");
			 tp.setContent(xms[i]+talentPool.getContent());
			 tp.setInterviewPlace(talentPool.getInterviewPlace());
			 tp.setContactor(talentPool.getContactor());
			 tp.setContactTel(talentPool.getContactTel());
			 tp.setRemark(talentPool.getRemark());
			 tp.setNoticeDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			 tp.setInterviewDate(d);
			 tp.setIsSMS(isSMS);
			 if(tp.getRiId()==null||tp.getRiId().equals("")){
			 	tp.setRiId(talentPool.getRiId());
				 ResumeS s = (ResumeS)baseBeanService.getBeanByHqlAndParams("from ResumeS where resumeID = ?",new Object[]{tp.getResumeID()});
				 tp.setStaffID(s.getStaffID());
			 }
			 list.add(tp);
			 if(i==0)
				 sqlr.append("?");
			 else
				 sqlr.append(",?");

		 }
		 sqlr.append(")");
		 sqlr.append(" group by t.tpId,f.staffName,ri.jobTitle,j.position,f.reference");
		 if("00".equals(isSMS)){
			 List<Object> staffList=baseBeanService.getListBeanBySqlAndParams(sqlr.toString(), tpIDs);
			 
			 for(int i=0;i<staffList.size();i++){
				 StringBuilder  smscont = new StringBuilder();
				 
				 Object[] obj = (Object[])staffList.get(i);
				 if(obj[2]!=null&&!"".equals(obj[2])){
				 msg.setMobiles(obj[2].toString());
				 smscont.append((obj[1]==null?"":obj[1])+"您好！\n");
				 smscont.append(company.getCompanyName()+"通知邀请您来应-试");
				 smscont.append(""+obj[3]+"的职位。\n");
				 smscont.append("应-试时间："+rq+" "+ws+sj+",\n");
				 smscont.append("应-试地点："+talentPool.getInterviewPlace()+",\n");
				 smscont.append("联系人："+talentPool.getContactor()+",\n");
				 smscont.append("联系人电话："+talentPool.getContactTel()+"");
				 if(!"".equals(talentPool.getRemark())&&talentPool.getRemark()!=null)
					 smscont.append(",要求："+talentPool.getRemark()+"。");
				 
				 msg.setMessage(smscont.toString());
				 String reStr = msg.sendMsg();
				 logger.info("值：{}", reStr);
				 }
			 }
		 }
		  baseBeanService.executeHqlsByParamsList(list, null, null);
		return "success";
	}

	/**
	 *
	 * 简历不同意
	 * @return
	 */
	public String auditResume(){
	  	CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");

        bidsRecruitService.auditResume(talentPool.getTpId(),account.getStaffID());

		 return "success";

	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public TalentPool getTalentPool() {
		return talentPool;
	}

	public void setTalentPool(TalentPool talentPool) {
		this.talentPool = talentPool;
	}

	public List<BaseBean> getList() {
		return list;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}
}
