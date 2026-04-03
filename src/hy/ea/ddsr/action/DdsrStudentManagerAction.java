package hy.ea.ddsr.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.ddsr.Dssrstudent;
import hy.ea.bo.ddsr.Dssrsubject;
import hy.ea.bo.ddsr.ReDssrstudentDssrsubject;
import hy.ea.bo.human.Staff;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 预约培训系统的学员管理
 * 
 * @author zhb
 * 
 */
@Controller
@Scope("prototype")
public class DdsrStudentManagerAction {

	@Resource
	private BaseBeanService baseBeanService;
	
	@Resource
	private ServerService serverService;

	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private String innerAction;
	
	private Staff staff;//社会人力对象Bean
	private Dssrstudent student;//学员对象Bean
	private Dssrsubject subject;//科目对象Bean
	private ReDssrstudentDssrsubject reStudSubj;//学员和科目对应(一对一)
	
	private List<BaseBean> subjectList;//本公司下的全部科目
		
	private String delStudent;// 删除学员的ID串儿
	private String stud_key2;//学员表的主键,如果有,则说明学员表中已经存在(只能更新);如果没有,说明学员表中不存在(需要插入)
	
	/*查询条件: 姓名、IC卡编号、籍贯、期数、状态、当前科目、证照类型、是否星级*/
	private String searchName;
	private String searchIcNumber;
	private String searchJiguan;
	private String searchQishu;
	private Byte searchZhuangtai;
	private Byte searchDangQian;
	private String searchZhengtype;
	private Byte searchStar;
	
	public String doDdsrStudentManagerAction() {
		if (innerAction == null || "".equals(innerAction.trim()))
			return getDdsrStudentList();
		else if ("showDdsrStudentList".equals(innerAction.trim()))//列表显示
			return getDdsrStudentList();
		else if ("updateDdsrStudent".equals(innerAction.trim()))//修改
			return updateDdsrStudent();
		else if ("delDdsrStudent".equals(innerAction.trim()))//删除(逻辑删除)
			return delDdsrStudent();
		else
			return getDdsrStudentList();		
	}
	
	private void getAllSubjects()
	{
		String hql = " from Dssrsubject where subjCompanyid = ?";
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		String companyID = account.getCompanyID();
		subjectList = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{companyID});
	}
	
	/*private Dssrsubject getSubjectByType(String type)
	{
		String hql = " from Dssrsubject where subjCompanyid = ? and subjType = ?";
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		String companyID = account.getCompanyID();
		return (Dssrsubject)baseBeanService.getObjectByHqlAndParams(hql, new Object[]{companyID,type});
	}*/
	
	private String delDdsrStudent() {
		if (delStudent!=null && !delStudent.trim().equals(""))
		{
			String[] ids = delStudent.split(",");			
			String[] sqls = new String[ids.length];
			List<Object[]> list = new ArrayList<Object[]>(ids.length);
			int i = 0;
			for (String id : ids) {
				sqls[i] = "update dt_hr_staff set staffstatus='98' where staffkey = ? ";	//逻辑删除
				list.add(new Object[]{id});
				i++;
			}
			baseBeanService.executeSqlsByParmsList(null, sqls, list);
		}		
		return null;
	}
	
	/**
	 * 修改
	 * @return
	 */
	private String updateDdsrStudent() {
		//修改要做的事比较多，需要更新Staff，需要更新DdsrStudent，需要修改DdsrStudent与科目的关联关系（ReDssrstudentDssrsubject）
		//baseBeanService.update(staff);
		
		String sqlUpdateStaff = "update dt_hr_Staff set staffName=?,usedNmae=?,sex=?,staffIdentityCard=?,birthday=?,reference=?,staffAddress=?,nativePlace=?,staffDesc=? where staffKey=?";
		Object[] paramsUpdateStaff = {staff.getStaffName(),staff.getUsedNmae(),staff.getSex(),staff.getStaffIdentityCard(),staff.getBirthday(),
				staff.getReference(),staff.getStaffAddress(),staff.getNativePlace(),staff.getStaffDesc(),staff.getStaffKey()};
		
		String sqlDelReDssrstudentDssrsubject = "delete Re_dssrStudent_dssrSubject where stud_key = ? and subj_key = ?";
		Object[] paramsDelReDssrstudentDssrsubject = {staff.getStaffKey(),subject.getSubjKey()};		

		//默认是插入student		
		String sqlAddOrUpdStudent = "insert into dssrstudent(stud_key, stud_companyid, stud_icnumber, stud_inforclass, stud_status, stud_star, stud_sumhour, stud_nper, stud_remhour, stud_stonumber, stud_credentials)" + 
                               "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] paramsAddOrUpdStudent = {student.getStudKey(),student.getStudCompanyid(),student.getStudIcnumber(),student.getStudInforclass(),student.getStudStatus(),student.getStudStar(),
									 student.getStudSumhour(),student.getStudNper(),student.getStudRemhour(),student.getStudStonumber(),student.getStudCredentials()};
		if (stud_key2!=null && !"".equals(stud_key2.trim()))//学员表的主键,如果有,则说明学员表中已经存在(只能更新);如果没有,说明学员表中不存在(需要插入)
		{
			sqlAddOrUpdStudent = "update dssrstudent set stud_icnumber=?, stud_inforclass=?, stud_status=?, stud_star=?, stud_sumhour=?, stud_nper=?, stud_remhour=?, stud_stonumber=?, stud_credentials=?,stud_updatedate=? where stud_key = ?";
			paramsAddOrUpdStudent[0] =  student.getStudIcnumber();
			paramsAddOrUpdStudent[1] =  student.getStudInforclass();
			paramsAddOrUpdStudent[2] =  student.getStudStatus();
			paramsAddOrUpdStudent[3] =  student.getStudStar();
			paramsAddOrUpdStudent[4] =  student.getStudSumhour();
			paramsAddOrUpdStudent[5] =  student.getStudNper();
			paramsAddOrUpdStudent[6] =  student.getStudRemhour();
			paramsAddOrUpdStudent[7] =  student.getStudStonumber();
			paramsAddOrUpdStudent[8] =  student.getStudCredentials();
			paramsAddOrUpdStudent[9] =  Calendar.getInstance().getTime();
			paramsAddOrUpdStudent[10] =  student.getStudKey();			
		}
		
		String sqlAddReDssrstudentDssrsubject = "insert into re_dssrstudent_dssrsubject(stsu_key, stud_key, subj_key) values(?, ?, ?)";
		Object[] paramsAddReDssrstudentDssrsubject = {serverService.getServerID("stsu_key"),student.getStudKey(),subject.getSubjKey()};		
		
		String[] sqls = {sqlUpdateStaff,sqlDelReDssrstudentDssrsubject,sqlAddOrUpdStudent,sqlAddReDssrstudentDssrsubject};
		List<Object[]> list = new ArrayList<Object[]>();
		list.add(paramsUpdateStaff);
		list.add(paramsDelReDssrstudentDssrsubject);		
		list.add(paramsAddOrUpdStudent);
		list.add(paramsAddReDssrstudentDssrsubject);
		
		baseBeanService.executeSqlsByParmsList(null, sqls, list);
		
		return "success";
	}

	private String getDdsrStudentList() {
		getAllSubjects();		
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.lastIndexOf("from")), parms);
		
		return "showDdsrStudentList";
	}
	
	private List<Object> getList()
	{
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		//查询条件是:  姓名、IC卡编号、籍贯、期数、状态、当前科目、证照类型、是否星级
		if (search != null && search.equals("search")) {// 点击查询按钮过来的			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("searchName", searchName);
			map.put("searchIcNumber", searchIcNumber);
			map.put("searchJiguan", searchJiguan);
			map.put("searchQishu", searchQishu);
			map.put("searchZhuangtai", searchZhuangtai);
			map.put("searchDangQian", searchDangQian);
			map.put("searchZhengtype", searchZhengtype);
			map.put("searchStar", searchStar);			
			session.put("studentSearchCriteriaInMap", map);// 将查询条件保存在session中						
		}else if (search != null && search.equals("turnpage")){// 翻页过来的
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) session.get("studentSearchCriteriaInMap");
			if (map!=null){
				searchName = (String)map.get("searchName");
				searchIcNumber = (String)map.get("searchIcNumber");
				searchJiguan = (String)map.get("searchJiguan");
				searchQishu = (String)map.get("searchQishu");
				searchZhuangtai = (Byte)map.get("searchZhuangtai");
				searchDangQian = (Byte)map.get("searchDangQian");
				searchZhengtype = (String)map.get("searchZhengtype");
				searchStar = (Byte)map.get("searchStar");
			}
		}else{//第一次进来的
			
		}
		
		StringBuffer sql = new StringBuffer("");
		sql.append("select hrst.staffkey     as stud_key,"); //人员主键  0
		sql.append("hrst.staffname    as staffname,"); //姓名 1
		sql.append("hrst.usednmae     as usedname,");//曾用名 2
		sql.append("hrst.sex          as sex,");//性别 3
		sql.append("st.stud_icnumber  as stud_icnumber,");//IC卡编号 4
		sql.append("hrst.staffidentitycard as staffidentitycard,");//身份证 5
		sql.append("hrst.birthday     as birthday,");//生日 6
		sql.append("dsu.subj_type     as subj_type,");//科目类别 7
		sql.append("hrst.staffaddress as staffaddress,");//地址 8
		sql.append("st.stud_status    as stud_status,");//学员状态 9		
		sql.append("st.stud_credentials as stud_credentials,");//证照类别 10
		sql.append("st.stud_remhour        as stud_remhour,");//剩余学时 11
		sql.append("hrst.photo             as photo,");//图片路径 12
		sql.append("hrst.reference         as reference,");//电话 13
		sql.append("hrst.nativeplace       as nativeplace,");//籍贯 14
		sql.append("st.stud_inforclass     as stud_inforclass,");//信息类别 15
		sql.append("st.stud_star           as stud_star,");//学员星级 16
		sql.append("st.stud_nper           as stud_nper,");//开课期数 17
		sql.append("st.stud_sumhour        as stud_sumhour,");//学时总数 18
		sql.append("st.stud_stonumber      as stud_stonumber,");//补时次数 19
		sql.append("hrst.staffdesc         as staffdesc,");//备注	 20	
		sql.append("hrst.staffid           as staffid,");//staffid	21	
		sql.append("dsu.subj_key           as subj_key,");//科目主键  22
		sql.append("dtc.companyid          as companyid,");//所属公司  23
		sql.append("st.stud_key            as stud_key2,");//学员表的主键,如果有,则说明学员表中已经存在(只能更新);如果没有,说明学员表中不存在(需要插入) 24		
		sql.append("hrst.STATUS            AS STATUS"); // 25
		
		sql.append(" from dt_hr_staff hrst ");
		sql.append(" left join dssrstudent st ");
		sql.append(" on hrst.staffkey = st.stud_key ");
		sql.append(" left join dtContactRelation dtc ");
		sql.append(" on hrst.staffid = dtc.staffid ");
		sql.append(" left join Re_dssrStudent_dssrSubject rdsds ");
		sql.append(" on st.stud_key = rdsds.stud_key ");
		sql.append(" left join dssrSubject dsu ");
		sql.append(" on rdsds.subj_key = dsu.subj_key ");
		sql.append(" where dtc.relation = '学员' "); //往来关系必须是学员
		//sql.append(" and (st.stud_inforclass = 10 or st.stud_inforclass is null) ");//信息类别
		sql.append(" and hrst.staffstatus <> '98' ");
		sql.append(" and dtc.companyid = ? ");
		parms.add(((CAccount) session.get("account")).getCompanyID());
		
		
		//查询条件
		if (null != searchName && !"".equals(searchName.trim())) {
			sql.append(" and hrst.staffname like '%'||?||'%' ");
			parms.add(searchName);
		}
		
		if (null != searchIcNumber && !"".equals(searchIcNumber.trim())) {
			sql.append(" and st.stud_icnumber like '%'||?||'%' ");
			parms.add(searchIcNumber);
		}
		
		if (null != searchJiguan && !"".equals(searchJiguan.trim())) {
			sql.append(" and hrst.nativeplace like '%'||?||'%' ");
			parms.add(searchJiguan);
		}
		
		if (null != searchQishu && !"".equals(searchQishu.trim())) {
			sql.append(" and st.stud_nper = ? ");
			parms.add(searchQishu);
		}
		
		if (null != searchZhuangtai && searchZhuangtai != 0) {
			sql.append(" and st.stud_status = ? ");
			parms.add(searchZhuangtai);
		}
		
		if (null != searchDangQian && searchDangQian != 0) {
			sql.append(" and dsu.subj_type = ? ");
			parms.add(searchDangQian);
		}
		
		if (null != searchZhengtype && !"".equals(searchZhengtype.trim())) {
			sql.append(" and st.stud_credentials = ? ");
			parms.add(searchZhengtype);
		}
		
		if (null != searchStar && searchStar != 0) {
			sql.append(" and st.stud_star = ? ");
			parms.add(searchStar);
		}	
		
		result.add(sql.toString());
		result.add(parms.toArray());
		return result;
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
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getInnerAction() {
		return innerAction;
	}
	public void setInnerAction(String innerAction) {
		this.innerAction = innerAction;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getSearchIcNumber() {
		return searchIcNumber;
	}

	public void setSearchIcNumber(String searchIcNumber) {
		this.searchIcNumber = searchIcNumber;
	}

	public String getSearchJiguan() {
		return searchJiguan;
	}

	public void setSearchJiguan(String searchJiguan) {
		this.searchJiguan = searchJiguan;
	}

	public String getSearchQishu() {
		return searchQishu;
	}

	public void setSearchQishu(String searchQishu) {
		this.searchQishu = searchQishu;
	}

	public Byte getSearchZhuangtai() {
		return searchZhuangtai;
	}

	public void setSearchZhuangtai(Byte searchZhuangtai) {
		this.searchZhuangtai = searchZhuangtai;
	}

	public Byte getSearchDangQian() {
		return searchDangQian;
	}

	public void setSearchDangQian(Byte searchDangQian) {
		this.searchDangQian = searchDangQian;
	}

	public String getSearchZhengtype() {
		return searchZhengtype;
	}

	public void setSearchZhengtype(String searchZhengtype) {
		this.searchZhengtype = searchZhengtype;
	}

	public Byte getSearchStar() {
		return searchStar;
	}

	public void setSearchStar(Byte searchStar) {
		this.searchStar = searchStar;
	}

	public String getDelStudent() {
		return delStudent;
	}

	public void setDelStudent(String delStudent) {
		this.delStudent = delStudent;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Dssrstudent getStudent() {
		return student;
	}

	public void setStudent(Dssrstudent student) {
		this.student = student;
	}

	public ReDssrstudentDssrsubject getReStudSubj() {
		return reStudSubj;
	}

	public void setReStudSubj(ReDssrstudentDssrsubject reStudSubj) {
		this.reStudSubj = reStudSubj;
	}

	public Dssrsubject getSubject() {
		return subject;
	}

	public void setSubject(Dssrsubject subject) {
		this.subject = subject;
	}

	public List<BaseBean> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<BaseBean> subjectList) {
		this.subjectList = subjectList;
	}

	public String getStud_key2() {
		return stud_key2;
	}

	public void setStud_key2(String stud_key2) {
		this.stud_key2 = stud_key2;
	}
	

}
