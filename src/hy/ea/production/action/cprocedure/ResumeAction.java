package hy.ea.production.action.cprocedure;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffResume;
import hy.ea.bo.production.recruit.CollectThing;
import hy.ea.bo.production.recruit.TalentPool;
import hy.ea.bo.production.resume.Educational;
import hy.ea.bo.production.resume.JobWanted;
import hy.ea.bo.production.resume.ResumeS;
import hy.ea.production.service.BidsRecruitService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zzl 个人简历
 * */
@Controller
@Scope("prototype")
public class ResumeAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private BidsRecruitService bidsRecruitService;
	@Resource
	private UpLoadFileService fileService;
	private List<BaseBean> list, list2, list3;
	private List<Object> listobj;
	private Staff staff;// 人员表
	private JobWanted wanted;// 求职意向
	private Educational educational;// 添加教育背景
	private StaffResume resume;// 添加工作经验
	private ResumeS persion;// 个人简历
	private TalentPool talenPool;// 详情
	private String result;
	private String staffid;// 人员id
	private String evaluate;// 自我【】评价
	private String type;
	private String privacy;// 个人隐私
	private String key;// 主键删除
	private String staffids;
	private Object obj2, obj3, obj4;
	private File[] file; // 图片集合
	private String companyID;
	private String[] fileFileName; // 文件名
	private Date date;
	private String resumeID;
	private String resumeName;
	private String keys1, keys2, keys3, keys4;
	private String resumeIDp;
	private String[] cokey;
	private PageForm pageForm;
	private int pageNumber;
	private String state;
	private String ajax;
	private String riId;
	private String tpId;


     //个人招聘
	 public String candidates(){
		 HttpServletRequest request = ServletActionContext.getRequest();

		 HttpSession session = ServletActionContext.getRequest().getSession();
		 SessionWrap sw = SessionWrap.getInstance();
		 TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				 SessionWrap.KEY_SHOPCUSCOM);

		 request.setAttribute("staffid",tc.getStaffid());
		 request.setAttribute("sccId",tc.getSccId());


	 	return "candidates";
	 }
	/**
	 * 个人简历管理
	 * */
	@SuppressWarnings("unchecked")
	public String resumeManagement() {
		StringBuilder sql = new StringBuilder();
		sql.append(" select re.staffID,re.resumeID,st.staffname,re.resumeName,jo.position,jo.salary,jo.region,st.sex,jo.jobWantedKey,re.resumeKey,re.privacy");
		sql.append(" from dtresumes re");
		sql.append(" left join dtJobWanted jo on jo.resumeID=re.resumeID");
		sql.append(" left join dt_hr_Staff st on st.staffid=re.staffid");
		sql.append(" where re.staffid = ? and re.state is null");
		listobj = baseBeanService.getListBeanBySqlAndParams(sql.toString(),
				new Object[] { staffid });

		return "resumeManagement";
	}

	/**
	 * 删除简历
	 * */
	@SuppressWarnings("unchecked")
	public String deletManagement() {
		String hql = "select educationKey  from dtEducational where resumeID=?";
		list = baseBeanService.getListBeanBySqlAndParams(hql,
				new Object[] { resumeID });
		if (list != null && list.size() > 0) {
			// 删除多个教育背景
			for (int i = 0; i < list.size(); i++) {
				Object ff = (Object) list.get(i);
				baseBeanService.deleteBeanByKey(Educational.class,
						ff.toString());
			}
		}

		String hql2 = "select recordKey  from dt_hr_staff_Resume where resumeID=?";
		list2 = baseBeanService.getListBeanBySqlAndParams(hql2,
				new Object[] { resumeID });

		if (list2 != null && list2.size() > 0) {
			for (int i = 0; i < list2.size(); i++) {
				Object ff = (Object) list2.get(i);
				baseBeanService.deleteBeanByKey(StaffResume.class,
						ff.toString());
			}
		}// 删除多个工作经验

		if (keys1 != null && keys2 != null) {
			baseBeanService.deleteBeanByKey(JobWanted.class, keys1);
			baseBeanService.deleteBeanByKey(ResumeS.class, keys2);
		}

		return resumeManagement();

	}

	/**
	 * 编辑简历
	 * */
	public String editManagement() {
		String hql = "from ResumeS where resumeID=?";
		ResumeS req = (ResumeS) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { resumeID });
		resumeID = req.getResumeID();
		staffid = req.getStaffID();
		return queryPersion();

	}

	/**
	 * 简历投递记录
	 * */
	@SuppressWarnings("unchecked")
	public String getRecord() {


		pageForm = bidsRecruitService.getPostRecordList(pageNumber==0?1:pageNumber,6,staffid,state,type);

		if ("ajax".equals(ajax)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageForm", pageForm);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}

		if (pageForm!=null&&pageForm.getList()!= null&&pageForm.getList().size()>0) {
			return "Record";
		} else {
			evaluate="您好，暂时没有投递记录！！";
			return "Record";
		}

	}

	/**
	 *
	 * 改变状态同意拒绝等
	 * @return
	 */
	public String changeState(){

		bidsRecruitService.changeState(tpId,staffid,state);

		return "success";
	}

	/**
	 * 我关注的职位
	 * */
	@SuppressWarnings("unchecked")
	public String getPostion() {
		StringBuilder sql = new StringBuilder();
		sql.append("select re.companyName,re.workCity,re.jobTitle,re.education,re.salary,re.publishDate,coll.cokey,coll.id");
		sql.append(" from dtCollectThing coll,dtRecruitInfo re");
		sql.append(" where coll.Id=re.riId");
		sql.append(" and coll.staffID = ?");
		list = baseBeanService.getListBeanBySqlAndParams(sql.toString(),
				new Object[] { staffid });
		return "postion";
	}

	/**
	 * 删除关注的职位
	 * */
	public String delPostion() {
		for (int i = 0; i < cokey.length; i++) {
			String coke = cokey[i];
			baseBeanService.deleteBeanByKey(CollectThing.class, coke);
		}
		return "success";
	}

	/**
	 * 
	 * 职位详情
	 * */
	public String getDetails() {

		obj2 = bidsRecruitService.getDetails(tpId,staffid);

		return "Details";
	}

	/**
	 * 进页面插入到人员简历表
	 * 
	 * */
	public String savePersion() {


		String hql = "from ResumeS where staffID=?";
		@SuppressWarnings("unused")
		ResumeS rep = (ResumeS) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { staffid });
		String hqls = "from ResumeS where resumeID=?";
		ResumeS reps = (ResumeS) baseBeanService.getBeanByHqlAndParams(hqls,
				new Object[] { resumeID });
		if ("save".equals(type)) {
			persion = (ResumeS)baseBeanService.getBeanByHqlAndParams("from ResumeS where staffID=? and state =?",new Object[]{staffid,"00"});
			if(persion==null){
				persion = new ResumeS();
				persion.setResumeID(serverService.getServerID("resum"));
				persion.setCreationTime(new Date());
				persion.setStaffID(staffid);
				persion.setPrivacy("01");
				persion.setIsDefault("00");
				persion.setIsNon("00");
				resumeIDp = persion.getResumeID();
				resumeID = persion.getResumeID();
				persion.setState("00");
				baseBeanService.save(persion);
			}else{
				resumeIDp = persion.getResumeID();
				resumeID = persion.getResumeID();
			}

		}
		// 自我评价reps.setOmpletionDegree("1");
		if ("ren".equals(type)) {
			reps.setEvaluate(evaluate);
			reps.setState("");
			baseBeanService.update(reps);

		}
		// 个人隐私
		if ("ajax".equals(type)) {
			reps.setState("");
			reps.setPrivacy(privacy);
			baseBeanService.update(reps);
			return "success";
		}
		// 个人隐私
		if ("asiNon".equals(type)) {
			reps.setState("");
			reps.setIsNon(resumeName);
			baseBeanService.update(reps);
			return "success";
		}
		// 查询简历名称
		if ("query".equals(type)) {

			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("rep", reps.getResumeName());
			return "queryResumes";
		}
		// 简历名称
		if ("ajaxtype".equals(type)) {
			reps.setState("");
			reps.setResumeName(resumeName);
			baseBeanService.update(reps);
			return "success";
		}
		// 自我评价
		if ("queryOne".equals(type)) {
			persion = (ResumeS) baseBeanService.getBeanByHqlAndParams(hqls,
					new Object[] { resumeID });
			return "evaluation";
		}

		return queryPersion();
	}

	/**
	 * 
	 * 人员简历界面的查询
	 * */
	@SuppressWarnings("unchecked")
	public String queryPersion() {

		StringBuilder sql = new StringBuilder();
		sql.append("select s.staffname, r.creationtime, r.ompletiondegree,s.headimage,r.evaluate,r.resumeID,");
		sql.append("s.sex,s.culturalDegree,s.birthday,r.resumeName");
		sql.append(" from dtresumeS r");
		sql.append(" left join dt_hr_Staff s on s.staffid=r.staffid");
		sql.append(" where r.resumeID = ?");
		String hql2 = " select e.degree from dtEducational e where e.resumeID=?";
		String hql3 = " select e.degree from dtJobWanted e where e.resumeID=?";
		String hql4 = " select e.degree from dt_hr_staff_Resume e where e.resumeID=?";
		List<Object> list2 = baseBeanService.getListBeanBySqlAndParams(hql2,
				new Object[] { resumeID });
		obj3 = baseBeanService.getObjectBySqlAndParams(hql3,
				new Object[] { resumeID });
		List<Object> list3 = baseBeanService.getListBeanBySqlAndParams(hql4,
				new Object[] { resumeID });
		List<Object> list = baseBeanService.getListBeanBySqlAndParams(
				sql.toString(), new Object[] { resumeID });
		Object obj = list.size() == 0 ? null : list.get(0);
		Object objs = list2.size() == 0 ? null : list2.get(0);
		Object objsto = list3.size() == 0 ? null : list3.get(0);
		HttpServletRequest request = ServletActionContext.getRequest();
		date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
		String act = sf.format(date);
		request.setAttribute("obj", obj);
		request.setAttribute("obj2", objs);
		request.setAttribute("obj4", objsto);
		request.setAttribute("act", act);

		if ("fina".equals(type)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("obj1", obj);
			map.put("obj2", objs);
			map.put("obj3", obj3);
			map.put("obj4", objsto);
			JSONObject oj = JSONObject.fromObject(map);
			result = oj.toString();
			return "success";
		}

		return "queryPersion";
	}

	/**
	 * 添加教育背景
	 * */
	public String addEducational() {
		String hql = "from ResumeS where resumeID=?";

		ResumeS per = (ResumeS) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { resumeID });
		// 增加或修改教育背景的
		if ("save".equals(type) || "update".equals(type)) {
			if ("save".equals(type)) {
				// 跟新教育背景的完成度

				per.setCreationTime(new Date());
				educational.setEducationID(serverService.getServerID("educat"));
				educational.setResumeID(per.getResumeID());
				educational.setDegree("1");
			}
			educational.setDegree("1");
			baseBeanService.saveOrUpdate(educational);
			per.setState("");
			baseBeanService.update(per);
			return "success";
		}

		// 删除教育背景
		if ("ajax".equals(type)) {
			baseBeanService.deleteBeanByKey(Educational.class, key);
			return "success";
		}

		return queryEdu();
	}

	/**
	 * 修改教育背景的查询
	 * */
	public String updataEducational() {
		String hql = "from Educational where educationKey=?";
		educational = (Educational) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { key });
		type = "修改";
		return "update";
	}

	/**
	 * 查询添加的教育背景
	 * */
	public String queryEdu() {
		String hql = "from Educational where resumeID=?";
		list = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { resumeID });
		type = "修改";
		return "query";
	}

	/**
	 * 添加工作经验
	 * */
	public String addResume() {
		String hql = "from ResumeS where resumeID=?";
		ResumeS per = (ResumeS) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { resumeID });
		// 添加工作经验
		if ("save".equals(type) || "update".equals(type)) {
			per.setCreationTime(new Date());
			if ("save".equals(type)) {
				// 跟新简历进度

				resume.setRecordID(serverService.getServerID("record"));
				resume.setResumeID(per.getResumeID());
				resume.setDegree("1");

			}
			resume.setDegree("1");
			baseBeanService.saveOrUpdate(resume);
			per.setState("");
			baseBeanService.update(per);

			return "success";
		}

		// 删除教育背景
		if ("ajax".equals(type)) {
			baseBeanService.deleteBeanByKey(StaffResume.class, key);
			return "success";
		}
		return queryResume();
	}

	/**
	 * 查询修改工作经验
	 * */
	public String updateResume() {
		String hql = "from StaffResume where recordKey=?";
		resume = (StaffResume) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { key });
		type = "修改";
		return "updateResume";
	}

	/**
	 * 查询添加工作经验 *
	 */
	public String queryResume() {
		String hql = "from StaffResume where resumeID=?";
		list = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { resumeID });
		type = "修改";
		return "queryResume";
	}

	/**
	 * 添加求职意向
	 * */
	public String searchIntention() {
		String hql = "from ResumeS where resumeID=?";
		ResumeS per = (ResumeS) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { resumeID });
		if ("save".equals(type) || "update".equals(type)) {
			per.setCreationTime(new Date());
			if ("save".equals(type)) {
				wanted.setJobWantedId(serverService.getServerID("jobwat"));
				wanted.setResumeID(per.getResumeID());
				wanted.setStaffID(per.getStaffID());
				wanted.setDegree("1");
			}

		}
		wanted.setDegree("1");
		baseBeanService.saveOrUpdate(wanted);
		per.setState("");
		baseBeanService.update(per);

		return "success";
	}

	// 查询求职意向的信息
	public String querySearch() {
		String hql = "from JobWanted where resumeID=?";
		wanted = (JobWanted) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { resumeID });

		return "querySearch";

	}

	/**
	 * 查询隐私的
	 * */
	public String getPrivacys() {
		String hql = "from ResumeS where staffid = ?";
		persion = (ResumeS) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { staffid });
		list = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { staffid });
		return "privacy";
	}

	/**
	 * 编辑简历
	 * */
	public String getEditResume() {
		String hqls = "from ResumeS where resumeID = ?";
		persion = (ResumeS) baseBeanService.getBeanByHqlAndParams(hqls,
				new Object[] { resumeID });
		String hql = "from Staff where staffID = ?";
		staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { persion.getStaffID() });

		return "edit";
	}

	/**
	 * 修改简历是否发布（是否隐私）
	 * */
	public String updateRelease(){
		String sql = "update dtresumeS s set s.privacy = ? where s.resumeid = ? ";
		baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{privacy,resumeID});
		return "success";
	}

	/**
	 * 默认简历
	 *
	 * */
	public String updateIsDefault(){
		String sql1="update dtresumeS s set s.isdefault = ? where s.isdefault = ? and s.staffid = ?";
		String sql2="update dtresumeS s set s.isdefault = ? where s.resumeid = ? ";
		List<Object[]> list = new ArrayList<Object[]>();
		Object[] sqlparam1 = new Object[]{"00","01",staffid};
		Object[] sqlparam2 = new Object[]{"01",resumeID};
		list.add(sqlparam1);
		list.add(sqlparam2);
		baseBeanService.executeSqlsByParmsList(null, new String[]{sql1,sql2},list );
		return "success";
	}

	// 保存编辑头像上传
	public String saveEdit() {
		HttpServletRequest re = ServletActionContext.getRequest();
		String path = re.getSession().getServletContext().getRealPath("/");
		companyID = staffid;
		if (file != null) {
			for (int i = 0; i < file.length; i++) {
				String photopath = fileService.savePhoto(
						path,
						fileFileName[i],
						file[i],
						companyID,
						"/staff/"
								+ Utilities.getDateString(new Date(),
										"yyyy-MM-dd"));
				staff.setHeadimage(photopath);

			}
		}

		baseBeanService.update(staff);

		ResumeS resumeS = (ResumeS)baseBeanService.getBeanByHqlAndParams("from ResumeS where resumeID  = ?",new Object[]{resumeID});
		resumeS.setState("");
		baseBeanService.update(resumeS);


		return queryPersion();
	}

	public JobWanted getWanted() {
		return wanted;
	}

	public void setWanted(JobWanted wanted) {
		this.wanted = wanted;
	}

	public Educational getEducational() {
		return educational;
	}

	public void setEducational(Educational educational) {
		this.educational = educational;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public List<BaseBean> getList() {
		return list;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}

	public StaffResume getResume() {
		return resume;
	}

	public void setResume(StaffResume resume) {
		this.resume = resume;
	}

	public ResumeS getPersion() {
		return persion;
	}

	public void setPersion(ResumeS persion) {
		this.persion = persion;
	}

	public String getStaffid() {
		return staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getStaffids() {
		return staffids;
	}

	public void setStaffids(String staffids) {
		this.staffids = staffids;
	}

	public Object getObj2() {
		return obj2;
	}

	public void setObj2(Object obj2) {
		this.obj2 = obj2;
	}

	public Object getObj3() {
		return obj3;
	}

	public void setObj3(Object obj3) {
		this.obj3 = obj3;
	}

	public Object getObj4() {
		return obj4;
	}

	public void setObj4(Object obj4) {
		this.obj4 = obj4;
	}

	public List<BaseBean> getList2() {
		return list2;
	}

	public void setList2(List<BaseBean> list2) {
		this.list2 = list2;
	}

	public List<BaseBean> getList3() {
		return list3;
	}

	public void setList3(List<BaseBean> list3) {
		this.list3 = list3;
	}

	public File[] getFile() {
		return file;
	}

	public void setFile(File[] file) {
		this.file = file;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String[] getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getResumeName() {
		return resumeName;
	}

	public void setResumeName(String resumeName) {
		this.resumeName = resumeName;
	}

	public String getResumeID() {
		return resumeID;
	}

	public void setResumeID(String resumeID) {
		this.resumeID = resumeID;
	}

	public List<Object> getListobj() {
		return listobj;
	}

	public void setListobj(List<Object> listobj) {
		this.listobj = listobj;
	}

	public String getKeys1() {
		return keys1;
	}

	public void setKeys1(String keys1) {
		this.keys1 = keys1;
	}

	public String getKeys2() {
		return keys2;
	}

	public void setKeys2(String keys2) {
		this.keys2 = keys2;
	}

	public String getKeys3() {
		return keys3;
	}

	public void setKeys3(String keys3) {
		this.keys3 = keys3;
	}

	public String getResumeIDp() {
		return resumeIDp;
	}

	public void setResumeIDp(String resumeIDp) {
		this.resumeIDp = resumeIDp;
	}

	public String getKeys4() {
		return keys4;
	}

	public void setKeys4(String keys4) {
		this.keys4 = keys4;
	}

	public TalentPool getTalenPool() {
		return talenPool;
	}

	public void setTalenPool(TalentPool talenPool) {
		this.talenPool = talenPool;
	}

	public String[] getCokey() {
		return cokey;
	}

	public void setCokey(String[] cokey) {
		this.cokey = cokey;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getAjax() {
		return ajax;
	}

	public void setAjax(String ajax) {
		this.ajax = ajax;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRiId() {
		return riId;
	}

	public void setRiId(String riId) {
		this.riId = riId;
	}

	public String getTpId() {
		return tpId;
	}

	public void setTpId(String tpId) {
		this.tpId = tpId;
	}
}
