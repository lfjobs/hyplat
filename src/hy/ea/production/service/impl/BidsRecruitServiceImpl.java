package hy.ea.production.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import hy.ea.bo.company.CcomCom;
import hy.ea.bo.human.Audition;
import hy.ea.bo.human.Staff;
import hy.ea.bo.production.recruit.RecruitInfo;
import hy.ea.bo.production.recruit.TalentPool;
import hy.ea.bo.production.resume.ResumeS;
import hy.ea.production.service.BidsRecruitService;
import hy.ea.util.MobileMessage;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.util.JushMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class BidsRecruitServiceImpl implements BidsRecruitService {

	@Resource
	private BaseBeanDao beandao;

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;

	@Autowired
	private MobileMessage msage;

	@Transactional
	public void addPosition(RecruitInfo recruitInfo,String staffID,String companyID,String companyName){


		if(recruitInfo!=null&&(recruitInfo.getRiId()==null||"".equals(recruitInfo.getRiId()))){
			recruitInfo.setRiId(serverService.getServerID("riId"));

		}
		recruitInfo.setJobRequire(recruitInfo.getJobRequire().replace("\r\n", "<br/>"));
		recruitInfo.setStatus("01");
		recruitInfo.setPublishDate(new Date());

		recruitInfo.setJobRequire(recruitInfo.getJobRequire().replace("\r\n", "<br/>"));
		recruitInfo.setStaffID(staffID);
		recruitInfo.setStaffName(((Staff) beandao.getBeanByHqlAndParams(
				"from Staff where staffID = ?",
				new Object[] { staffID })).getStaffName());
		recruitInfo.setCompanyID(companyID);
		recruitInfo.setCompanyName(companyName);
		recruitInfo.setCreateDate(new Date());
		String hql = "from CcomCom where comanyId = ?";
		CcomCom ccom =(CcomCom) beandao.getBeanByHqlAndParams(hql, new Object[]{companyID});
		recruitInfo.setCcompanyID(ccom.getCcompanyId());

		beandao.update(recruitInfo);

	}

	/**
	 *职位管理
	 *
	 * @param pageNumber
	 * @param pageSize
	 * @param companyID
	 * @return
	 */
	public PageForm findPositionList(int pageNumber, int pageSize, String companyID,String status){
		String hql = "from RecruitInfo where companyID = ? and status = ? order by publishDate desc";
	    PageForm pageForm = baseBeanService.getPageForm(pageNumber==0?1:pageNumber,pageSize,hql,new Object[]{companyID,(status==""||status==null)?"01":status});
            return pageForm;

	}

	/**
	 *
	 * 上线下线
	 * @param riId
	 * @return
	 */
	@Transactional
	public void onOfflineRecruit(String riId,String staffID){

		String hql = "from RecruitInfo where riId = ?";
		RecruitInfo rinfo = (RecruitInfo) baseBeanService
				.getBeanByHqlAndParams(hql,
						new Object[] { riId });


		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
				"from Staff where staffID = ?",
				new Object[] { staffID});

		if("00".equals(rinfo.getStatus())){
			rinfo.setStatus("01");//发布
			rinfo.setPublishDate(new Date());
			rinfo.setStaffID(staffID);
			rinfo.setStaffName(staff.getStaffName());
		}else{
			rinfo.setStatus("00");//取消发布
			rinfo.setOfflineDate(new Date());
			rinfo.setOfflineID(staffID);
			rinfo.setOfflineName(staff.getStaffName());
		}

		baseBeanService.update(rinfo);

	}

	/**
	 *
	 * 删除招聘信息
	 * @param riId
	 */
	@Transactional
	public void deleteRecruitInfo(String riId){
		String hql = "update RecruitInfo set status = ? where riId = ?";
		List<Object[]> paramlist = new ArrayList<Object[]>();
		paramlist.add(new Object[]{"09",riId});
		baseBeanService.executeHqlsByParamsList(null, new String[]{hql},paramlist);

	}

	/**\
	 *
	 * 编辑获取信息
	 * @param riId
	 * @return
	 */
	public RecruitInfo getEditRecruitInfo(String riId){
		RecruitInfo  recruitInfo =(RecruitInfo) beandao.getBeanByHqlAndParams("from RecruitInfo where riId = ?",new Object[]{riId});
		return recruitInfo;
	}

	/**
	 *
	 * 投递的简历
	 * @param pageNumber
	 * @param pageSize
	 * @param companyID
	 * @param state
	 * @param jobTitle
	 * @return
	 */
	public PageForm getTalentResumeList(int pageNumber,int pageSize,String companyID,String state,String jobTitle,String staffID){
		List<Object> obj=new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();

		sql.append("select t.tpId,f.staffName,f.sex,nvl(ri.jobTitle,j.position),round(sum((sr.endTime - sr.startTime)/30)/12,1)");
		sql.append(",f.culturalDegree,to_char(t.postDate,'yyyy-MM-dd HH:mi:ss'),t.state,f.headimage,nvl(floor(months_between(sysdate, to_date(f.birthday, 'yyyy-mm-dd')) / 12),0),r.resumeID,t.type");
		sql.append(" from dtTalentPool t");
		sql.append(" left join dtresumeS r on (t.resumeID=r.resumeID or t.resId = r.resumeid)");
		sql.append(" left join dtJobWanted j on r.resumeID = j.resumeID");
		sql.append(" left join dt_hr_Staff_Resume sr on sr.resumeID=r.resumeID");
		sql.append(" left join dt_hr_staff f on f.staffID = r.staffID");
		sql.append(" left join dtRecruitInfo ri on t.riId=ri.riId");
		sql.append(" where t.companyId = ?");
		obj.add(companyID);
		if(!"".equals(jobTitle)&&jobTitle!=null){
			String[] job = jobTitle.split(",");
			for(int i = 0;i<job.length;i++){
				if(job.length==1){
					sql.append(" and ri.jobTitle like ? ");
					obj.add("%"+job[i]+"%");
				}else{
					if(i==0){
						sql.append(" and (ri.jobTitle like ? ");
						obj.add("%"+job[i]+"%");
					}else if(i==job.length-1){
						sql.append(" or ri.jobTitle like ?) ");
						obj.add("%"+job[i]+"%");
					}else{
						sql.append(" or ri.jobTitle like ? ");
						obj.add("%"+job[i]+"%");
					}
				}
			}

		}
		if(!"".equals(state)&&state!=null){
			String[] states = state.split(",");
			for(int i = 0;i<states.length;i++){
				if(states.length==1){
					sql.append(" and t.state = ? ");
					obj.add(states[i]);
				}else{
					if(i==0){
						sql.append(" and (t.state = ? ");
						obj.add(states[i]);
					}else if(i==states.length-1){
						sql.append(" or t.state = ?) ");
						obj.add(states[i]);
					}else{
						sql.append(" or t.state = ? ");
						obj.add(states[i]);
					}
				}
			}
		}
        if(!"".equals(staffID)&&staffID!=null){
            sql.append(" and f.staffID = ? ");
            obj.add(staffID);
        }
		sql.append(" group by r.resumeID,t.tpId,f.staffName,ri.jobTitle,f.sex,f.educationValue,t.postDate,t.state,j.position,f.headimage,f.birthday,r.resumeID,f.culturalDegree,t.type");
		sql.append(" order by t.postDate desc");
		PageForm pageForm=baseBeanService.getPageFormBySQL(pageNumber,
				pageSize, sql.toString(), "select count(*) from ("+sql.toString()+")",
				obj.toArray());

		return pageForm;

	}

	/**
	 *
	 * 简历详情
	 * @param resumeID
	 * @return
	 */
	public Map<String,Object> resumedetail(String resumeID,String tpId ){

		StringBuilder sb = new StringBuilder();
		sb.append("select r.resumeID,j.position,f.staffname,j.region,f.headimage,f.sex,f.nativePlace,");
		sb.append("f.staffAddress,f.referenceOrganization,f.reference,j.salary,r.evaluate,f.birthday");
		sb.append(",nvl(floor(months_between(sysdate, to_date(birthday, 'yyyy-mm-dd')) / 12),0),ri.jobTitle,f.culturalDegree,j.status,t.state,j.work,f.sccId,t.type");
		sb.append(" from dtresumeS r left join dtTalentPool t");
		sb.append(" on (t.resid = r.resumeID or t.resumeid = r.resumeid) and t.tpId = ?");
		sb.append(" left join dtRecruitInfo ri on t.riId=ri.riId ");
		sb.append(" left join dtJobWanted j on r.resumeID = j.resumeID");
		sb.append(" left join dt_hr_staff f on f.staffID = r.staffID");
		sb.append(" where r.resumeID = ?");

		Object obj = baseBeanService.getObjectBySqlAndParams(sb.toString(), new Object[]{tpId,resumeID});
		//教育经历
		String hqledu = "from Educational where resumeID = ? order by admissionTime";
		List<BaseBean> edulist = baseBeanService.getListBeanByHqlAndParams(hqledu,new Object[]{resumeID});
		//工作经历
		String hqlsr = "from StaffResume where resumeID = ? order by startTime";
		List<BaseBean> srlist = baseBeanService.getListBeanByHqlAndParams(hqlsr,new Object[]{resumeID});

		Object edu = null;
		if(srlist.size()!=0){
			String sql = "select round(sum((sr.endTime - sr.startTime)/30)/12,1) from dt_hr_staff_Resume sr where sr.resumeID = ?  ";
			edu = baseBeanService.getObjectBySqlAndParams(sql,new Object[]{resumeID});
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("obj",obj);
		map.put("edu", edu);
		map.put("edulist", edulist);
		map.put("srlist", srlist);

              return map;

	}

	/**
	 *
	 * 设置状态
	 * @param tpId
	 * @param state
	 */
	@Transactional
	public void setOperate(String tpId,String state,String staffID){
		String hql = "update TalentPool set state = ?,operateID = ? where tpId = ?";
		List<Object[]> paramlist = new ArrayList<Object[]>();
		paramlist.add(new Object[]{state,staffID,tpId});
		baseBeanService.executeHqlsByParamsList(null, new String[]{hql},paramlist);
	}


	/**
	 *
	 * 获取面试邀请已有信息
	 * @param staffID
	 * @param tpId
	 * @return
	 */
	public Map<String,Object> getInventInfo(String staffID,String tpId){
		Map<String,Object>  map = new HashMap<String,Object>();
		Staff staff = (Staff)beandao.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{staffID});
		map.put("staff",staff);

		TalentPool talentPool = (TalentPool) beandao.getBeanByHqlAndParams("from TalentPool where tpId = ?",new Object[]{tpId});

		map.put("talentPool",talentPool);

		if(talentPool!=null&&"01".equals(talentPool.getType())){
			List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams("from RecruitInfo where companyID = ? and status = ?",new Object[]{talentPool.getCompanyId(),"01"});
			map.put("postionlist",list);

		}


           return map;
	}

	/**
	 *
	 *
	 * @param tpId
	 */
	@Transactional
	public void sendInvent(String tpId,TalentPool talentPool){
           String hql = "from TalentPool where tpId = ?";
		TalentPool tt = (TalentPool) beandao.getBeanByHqlAndParams(hql,new Object[]{tpId});
		List<BaseBean> beans = new ArrayList<BaseBean>();
		if(tt!=null){

			tt.setContactor(talentPool.getContactor());
			tt.setContactTel(talentPool.getContactTel());
			tt.setRemark(talentPool.getRemark());
			tt.setNoticeDate(Utilities.getDateString(new Date(),"yyyy-MM-dd HH:mm:ss"));
			tt.setInterviewDate(talentPool.getInterviewDate());
			tt.setInterviewPlace(talentPool.getInterviewPlace());
			tt.setIsSMS("01");
			tt.setState("01");
			if(tt.getRiId()==null||tt.getRiId().equals("")){
				tt.setRiId(talentPool.getRiId());
				ResumeS s = (ResumeS)baseBeanService.getBeanByHqlAndParams("from ResumeS where resumeID = ?",new Object[]{tt.getResumeID()});
				tt.setStaffID(s.getStaffID());
			}
			beans.add(tt);

			noticeMessage("m",tt);

			RecruitInfo r = (RecruitInfo)beandao.getBeanByHqlAndParams("from RecruitInfo where riId = ?",new Object[]{tt.getRiId()});

			Audition audition = new Audition();
			audition.setAuditionID(serverService.getServerID("auditionID"));
			audition.setCompanyID(tt.getCompanyId());
		//	audition.setAuditionDirection();
			audition.setAuditionPost(r.getPositionName());
			audition.setExperience("");
			audition.setStaffID(tt.getStaffID());
			audition.setPlace(tt.getInterviewPlace());
			audition.setAuditionDate(tt.getInterviewDate());
			audition.setContactor(tt.getContactor());
			audition.setContactTel(tt.getContactTel());
			audition.setStatus("01");//待登记
			audition.setTpId(tpId);
			beans.add(audition);

			beandao.saveBeansListAndexecuteHqlsByParams(beans, null, null);

		}


	}

	/**
	 *
	 * 通知
	 * @param tm
	 * @param talentPool
	 */
	public  void noticeMessage(String tm, TalentPool talentPool) {
		RecruitInfo recruitInfo = (RecruitInfo) beandao.getBeanByHqlAndParams("from RecruitInfo where riId = ?",new Object[]{talentPool.getRiId()});

		Staff ff = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{talentPool.getStaffID()});


		List<String> slist = new ArrayList<String>();//极光推送设备号
		String content = "";
		String tel = "";
		String type="";
		String id = "";
		String body = "";
		StringBuilder  smscont = new StringBuilder();
		if ("t".equals(tm)) {

			type = "投简历";
			id = "tjl";
			body = ff.getStaffName()+"投递了简历，应聘职位"+recruitInfo.getJobTitle()+"。请及时查看！";
			//投递简历
			Staff fft = (Staff) beandao.getBeanByHqlAndParams("from Staff s where s.staffID = ?", new Object[]{recruitInfo.getStaffID()});
			if (fft != null) {
				tel = ff.getReference();
			}
			smscont.append(body);
		} else {
			type = "邀请面试";
			id = "msyq";
			//邀请面试

			tel = ff.getReference();

			body = "您有一个面试邀请";
			smscont.append(ff.getStaffName()+"您好！\n");
			smscont.append(recruitInfo.getCompanyName()+"通知邀请您来面试");
			smscont.append(""+recruitInfo.getJobTitle()+"的职位。\n");
			smscont.append("面试时间："+Utilities.getDateString(talentPool.getInterviewDate(),"yyyy-MM-dd HH:mm:ss")+",\n");
			smscont.append("面试地点："+talentPool.getInterviewPlace()+",\n");
			smscont.append("联系人："+talentPool.getContactor()+",\n");
			smscont.append("联系人电话："+talentPool.getContactTel()+"");
			if(!"".equals(talentPool.getRemark())&&talentPool.getRemark()!=null)
				smscont.append(",要求："+talentPool.getRemark()+"。");
		}


		try {
			msage.setMobiles(tel);
			msage.setMessage(smscont.toString());
			msage.sendMsg("【数字地球】");
		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		//极光推送
		JushMain.sendjiguangMessage(smscont.toString(), type, body, id, slist);

	}

	/**
	 *
	 * 收藏的简历
	 * @param pageNumber
	 * @param pageSize
	 * @param staffID
	 * @return
	 */
	public PageForm  getCollectResume(int pageNumber,int pageSize,String staffID){

		StringBuffer sql=new StringBuffer();

		sql.append("select r.resumeID,f.staffName,f.sex,round(sum((sr.endTime - sr.startTime)/30)/12,1)");
		sql.append(",f.culturalDegree,f.headimage,nvl(floor(months_between(sysdate, to_date(f.birthday, 'yyyy-mm-dd')) / 12),0),j.position,to_char(t.colDate, 'yyyy-mm-dd'),j.region");
		sql.append(" from dtCollectThing t");
		sql.append(" left join dtresumeS r on t.Id=r.resumeID");
		sql.append(" left join dtJobWanted j on r.resumeID = j.resumeID");
		sql.append(" left join dt_hr_Staff_Resume sr on sr.resumeID=r.resumeID");
		sql.append(" left join dt_hr_staff f on f.staffID = r.staffID");
		sql.append(" where t.staffID = ?");
		sql.append(" group by f.staffName,f.sex,f.culturalDegree,j.position,f.headimage,f.birthday,t.colDate,r.resumeID,j.region");
		sql.append(" order by t.colDate desc");


		PageForm pageForm=baseBeanService.getPageFormBySQL(pageNumber,
				pageSize, sql.toString(), "select count(*) from ("+sql.toString()+")",
				new Object[]{staffID});

		return  pageForm;

	}

	/**
	 *
	 * 取消简历收藏
	 * @param resumeID
	 * @param staffID
	 */
	public void cancelCollectR(String resumeID,String staffID){

		String hql = "delete CollectThing where id = ? and staffID = ?";
		List<Object[]> paramlist = new ArrayList<Object[]>();
		paramlist.add(new Object[]{resumeID,staffID});
		baseBeanService.executeHqlsByParamsList(null, new String[]{hql},paramlist);

	}


	/**
	 *
	 * 投递的简历或者邀请的职位
	 * @param pageNumber
	 * @param pageSize
	 * @param state
	 * @return
	 */
	public PageForm getPostRecordList(int pageNumber,int pageSize,String staffID,String state,String type){

		List<Object> parms = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();

		sql.append("select p.jobTitle,con.companyName,ta.state,ta.riId,con.logoPath,to_char(nvl(ta.interviewDate, ta.postdate),'yyyy-MM-dd'),p.workCity,p.education,ta.tpId");
		sql.append(" from dtTalentPool ta,DT_ccom_com cco,dtContactCompany con,dtRecruitInfo p");
		sql.append(" where ta.riid=p.riid");
		sql.append(" and cco.ccompany_Id =con.ccompanyId");
		sql.append(" and cco.compnay_id =ta.companyID");
		sql.append(" and ta.staffid = ? and ta.type = ?");

		parms.add(staffID);
		parms.add(type);
		if(state!=null&&!state.equals("")){
			if("04".equals(state)){
				sql.append(" and (ta.state = ? or ta.state = ?)");
				parms.add(state);
				parms.add("05");
			}else{
				sql.append(" and ta.state = ?");
				parms.add(state);
			}

		}


		PageForm pageForm=baseBeanService.getPageFormBySQL(pageNumber,
				pageSize, sql.toString(), "select count(*) from ("+sql.toString()+")",
				parms.toArray());

		return pageForm;


	}

	/**
	 *
	 * 投递详情
	 * @param
	 * @param staffID riId
	 * @return
	 */
	public Object getDetails(String tpId,String staffID){
		StringBuilder sql = new StringBuilder();

		sql.append("select re.workCity,re.workYears,re.education,re.partorfull,re.salary,");
		sql.append("re.jobTitle,to_char(ta.interviewDate,'yyyy-MM-dd HH:mi'),re.remark,re.jobRequire,re.workPlace,ta.interviewPlace,ta.contactor,ta.contactTel,con.companyName,con.logoPath,ta.state");
		sql.append(" from dtTalentPool ta, dtRecruitInfo re,DT_ccom_com cco,dtContactCompany con");
		sql.append(" where ta.riId = re.riId");
		sql.append(" and cco.ccompany_Id =con.ccompanyId");
		sql.append(" and cco.compnay_id =ta.companyID");
		sql.append(" and ta.tpId = ? and ta.staffid = ?");


		Object obj = baseBeanService.getObjectBySqlAndParams(sql.toString(),
				new Object[] { tpId,staffID });

		return obj;


	}



	/**
	 *
	 * 改变状态同意拒绝
	 * @param tpId
	 * @param staffID
	 */
	@Transactional
	public  void changeState(String tpId,String staffID,String state){


		String hql = "update TalentPool set state = ? where tpId = ? and staffID = ?";
		List<Object[]> paramlist = new ArrayList<Object[]>();
		paramlist.add(new Object[]{state,tpId,staffID});
		baseBeanService.executeHqlsByParamsList(null, new String[]{hql},paramlist);
	}

	/**
	 *
	 * 公司操作不合适
	 * @param tpId
	 * @param staffID
	 */
	@Transactional
	public void auditResume(String tpId,String staffID){

		String hql = "update TalentPool set state = ?,operateID = ? where tpId  in(";

        List<Object> params = new ArrayList<Object>();
		String[] tpIDs=tpId.split(",");
		params.add("04");
		params.add(staffID);
		for(int i=0;i<tpIDs.length;i++){
			if(i==0)
				hql+="?";
			else
				hql+=",?";

			params.add(tpIDs[i]);
		}
		hql+=")";
		beandao.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql}, params.toArray());




	}
}
