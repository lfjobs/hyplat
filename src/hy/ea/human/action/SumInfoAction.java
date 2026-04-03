package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/**
 * 在职员工汇总信息
 * @author SumInfoAction
 *
 */
@Controller
@Scope("prototype")
public class SumInfoAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private List<String>  headerList=new ArrayList<String>();
	private String actionName;//信息列表方法名称
	private String actionNameExcel;//信息导出方法名称
	private String basicInfo;//单位人员信息名称
	private String feildName;
	private String conditions;
	/**
	 *  动态导出以选的的字段名称信息
	 * @param feildName
	 * @param hql1
	 * @param params
	 */
	@SuppressWarnings("unchecked")
	public  void ShowExcel(String feildName,String hql1,Object[] params ,String searchWay) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String[] arrayFeild=feildName.split(",");
		String[] hearer= (String[]) session.get("hearer");
		String[] feilds= (String[]) session.get("feilds");
		String[] hearer1=new String[arrayFeild.length+1];
		hearer1[0]="序号";
		String hql = "select ";
			for (int i = 0; i < arrayFeild.length; i++) {
				hql+=feilds[Integer.parseInt(arrayFeild[i].trim())]+",";
				hearer1[i+1]=hearer[Integer.parseInt(arrayFeild[i].trim())];
			}
		hql=hql.substring(0, hql.length()-1);
		List<BaseBean> list;
		if (searchWay.equals("hql")) {
			list = baseBeanService.getListBeanByHqlAndParams(hql+hql1, params);
		}else {
			list = baseBeanService.getListBeanBySqlAndParams(hql+hql1, params);
		}
		
		if(basicInfo.equals("合同管理")){
			if(list != null){
				@SuppressWarnings("rawtypes")
				ArrayList alist = new ArrayList();
				for(int j=0;j<list.size();j++){
					Object obj = list.get(j);
					Object[] objl = (Object[])obj;
					String objs = objl[1].toString();
					if(objs.equals("00")){
						objl[1] = "参观期协议";
					}else if(objs.equals("01")){
						objl[1] = "培训期合同";
					}else if(objs.equals("02")){
						objl[1] = "劳动合同";
					}else if(objs.equals("03")){
						objl[1] = "竞职协议";
					}else if(objs.equals("04")){
						objl[1] = "保密协议";
					}else if(objs.equals("05")){
						objl[1] = "安全责任协议";
					}
					if(objl[6]== null){
						objl[6] = "无";
					}else{
						objl[6] = "有";
					}	
					
					alist.add(objl);
				}
				list = new ArrayList<BaseBean>();
				list = alist;
			}
		}
		
		excelStream = excelService.showExcel(hearer1, list);
	}
	/*------------------------------------------------------------------职工名册汇总-----------------------------------------------*/
	/**
	 * 职工名册汇总列表
	 * 
	 * @return
	 */
	public String getSumRoster() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = " from dt_hr_staff_address ad, dt_hr_staff sa where sa.staffid = ad.staffid and ad.addresskey in (select  max(t.addresskey) from dt_hr_staff_address t group by  t.staffid)" +
				  "and sa.staffID in (select staffID from dtcos where companyID = ?  and cosStatus =? )";
		actionName="getSumRoster";
		actionNameExcel="rosterShowExcel";
		String[] hearer={"姓名","性别","民族","政治面貌","手机号码","身份证号码","出生年月日","户籍地址","现住地址","文化程度"};
		for (int i = 0; i < hearer.length; i++) {
			headerList.add(hearer[i]);
		}
		if (search!=null&&search.equals("search")) {
			if (conditions!=null&&!"".equals(conditions.trim())) {
				hql+=" and sa.staffName='"+conditions.trim()+"'";
			}
		}
		session.put("stringHql", hql);
		String[] feilds={"sa.staffName","sa.sex","sa.nation","sa.politicsStatus","sa.reference","sa.staffIdentityCard","sa.birthday","sa.staffAddress","ad.addressdetailed","sa.culturalDegree"};
		session.put("feilds", feilds);
		session.put("hearer", hearer);
		pageForm = baseBeanService
				.getPageFormBySQL(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select sa.staffName,sa.sex,sa.nation,sa.politicsStatus,sa.reference,sa.staffIdentityCard,sa.birthday,sa.staffAddress,ad.addressdetailed,sa.culturalDegree "+hql,
						"select count(1) "+hql,
						new Object[] { account.getCompanyID(), "50" });
		return "list";
	}
	
	/**
	 * 职工名册汇总导出
	 * 
	 * @return
	 */
	public String rosterShowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql=session.get("stringHql").toString();
		Object[] params = { account.getCompanyID(), "50"   };
		ShowExcel(feildName, hql,params, "sql");
		CLogBook logBook = logBookService.saveCLogBook(null, "职工名册汇总导出", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/*------------------------------------------------------------------地址管理汇总-----------------------------------------------*/
	/**
	 * 地址管理汇总列表
	 * 
	 * @return
	 */
	public String getSumAddress() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = " from Staff  sa,StaffAddress se,CCode cc where sa.staffID in (select staffID from COS where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID and se.addressType=cc.codeID and cc.companyID=? and cc.codeStatus=? ";
		actionName="getSumAddress";
		actionNameExcel="addressShowExcel";
		String[] hearer={"姓名","身份证号","开始居住日起","结束居住日期","地址类别","详细地址","备注"};
		for (int i = 0; i < hearer.length; i++) {
			headerList.add(hearer[i]);
		}
		if (search!=null&&search.equals("search")) {
			if (conditions!=null&&!"".equals(conditions.trim())) {
				hql+=" and sa.staffName='"+conditions.trim()+"'";
			}
		}
		session.put("stringHql", hql);
		String[] feilds={"sa.staffName","sa.staffIdentityCard","to_char(se.livestartDate,'yyyy-mm-dd')","to_char(se.liveendDate,'yyyy-mm-dd')","cc.codeValue","se.addressDetailed","se.addressDesc"};
		session.put("feilds", feilds);
		session.put("hearer", hearer);
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select	sa.staffName,sa.staffIdentityCard,to_char(se.livestartDate,'yyyy-mm-dd'), to_char(se.liveendDate,'yyyy-mm-dd'),cc.codeValue,se.addressDetailed,se.addressDesc "+hql,
						"select count(sa) "+hql,
						new Object[] { account.getCompanyID(), "50",account.getCompanyID(),"00" });
		return "list";
	}
	
	/**
	 * 地址管理汇总导出
	 * 
	 * @return
	 */
	public String addressShowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql1=session.get("stringHql").toString();
		Object[] params = { account.getCompanyID(), "50" ,account.getCompanyID(),"00"  };
		ShowExcel(feildName, hql1,params, "hql");
		CLogBook logBook = logBookService.saveCLogBook(null,  "导出地址管理汇总", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/*------------------------------------------------------------------联系方式汇总-----------------------------------------------*/
	/**
	 * 联系方式汇总列表
	 * 
	 * @return
	 */
	public String getSumContact() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = " from Staff  sa,StaffContact se,CCode cc where sa.staffID in (select staffID from COS where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID and se.contactType=cc.codeID and cc.companyID=? and cc.codeStatus=? ";
		actionName="getSumContact";
		actionNameExcel="contactShowExcel";
		String[] hearer={"姓名","身份证号","联系号码","登记日期","联系类型"};
		for (int i = 0; i < hearer.length; i++) {
			headerList.add(hearer[i]);
		}
		if (search!=null&&search.equals("search")) {
			if (conditions!=null&&!"".equals(conditions.trim())) {
				hql+=" and sa.staffName='"+conditions.trim()+"'";
			}
		}
		session.put("stringHql", hql);
		String[] feilds={"sa.staffName","sa.staffIdentityCard","se.contactWay","to_char(se.contactDate,'yyyy-mm-dd')","cc.codeValue"};
		session.put("feilds", feilds);
		session.put("hearer", hearer);
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select	sa.staffName,sa.staffIdentityCard,se.contactWay, to_char(se.contactDate,'yyyy-mm-dd'),cc.codeValue"+hql,
						"select count(sa) "+hql,
						new Object[] { account.getCompanyID(), "50",account.getCompanyID(),"00" });
		return "list";
	}
	
	/**
	 * 联系方式汇总导出
	 * 
	 * @return
	 */
	public String contactShowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql1=session.get("stringHql").toString();
		Object[] params = { account.getCompanyID(), "50" ,account.getCompanyID(),"00"  };
		ShowExcel(feildName, hql1,params, "hql");
		CLogBook logBook = logBookService.saveCLogBook(null, "导出联系方式汇总", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	/*------------------------------------------------------------------学历学位汇总-----------------------------------------------*/
	/**
	 * 学历学位汇总列表
	 * 
	 * @return
	 */
	public String getSumEducation() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String	sql=" from dt_hr_staff_education se left join dt_hr_staff sa on sa.staffid=se.staffid left join dtccode cc on cc.codeid=se.majortype";
				sql+=" left join dtccode cc1 on cc1.codeid=se.educationHistory left join dtccode cc2 on cc2.codeid=se.educationStyle left join dtccode cc3 on cc3.codeid=se.educationType";
				sql+=" where cc.companyID=? and cc.codeStatus='00' and cc1.companyID=? and cc1.codeStatus='00' and cc2.companyID=? and cc2.codeStatus='00'";
				sql+=" and cc3.companyID=? and cc3.codeStatus='00' and sa.staffID in (select staffID from dtcos where companyID = ?  and cosStatus ='50' )";
		actionName="getSumEducation";
		actionNameExcel="educationShowExcel";
		String[] hearer={"姓名","身份证号","学校(培训机构)","入学时期","毕业日期","所学专业类型","学历","学习形式","教育类别","证明人","证明人电话"};
		for (int i = 0; i < hearer.length; i++) {
			headerList.add(hearer[i]);
		}
		if (search!=null&&search.equals("search")) {
			if (conditions!=null&&!"".equals(conditions.trim())) {
				sql+=" and sa.staffName='"+conditions.trim()+"'";
			}
		}
		session.put("stringHql", sql);
		String[] feilds={"sa.staffName","sa.staffIdentityCard","se.educationName","se.educationStart","se.educationEnd","cc.codeValue","cc1.codeValue codeValue1","cc2.codeValue codeValue2","cc3.codeValue codeValue3","se.provePerson","se.provePhone"};
		session.put("feilds", feilds);
		session.put("hearer", hearer);
		pageForm = baseBeanService
				.getPageFormBySQL(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select sa.staffName,sa.staffIdentityCard,se.educationName," +
						" se.educationStart,se.educationEnd,cc.codeValue,cc1.codeValue " +
						"codeValue1,cc2.codeValue codeValue2,cc3.codeValue codeValue3,se.provePerson,se.provePhone "+sql,
						"select count(1) "+sql,
						new Object[] { account.getCompanyID(), account.getCompanyID(),account.getCompanyID(),account.getCompanyID(),account.getCompanyID() });
		return "list";
	}
	
	/**
	 * 学历学位汇总导出
	 * 
	 * @return
	 */
	public String educationShowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String	sql=session.get("stringHql").toString();
		Object[] params = { account.getCompanyID(), account.getCompanyID(),account.getCompanyID(),account.getCompanyID(),account.getCompanyID() };
		ShowExcel(feildName, sql,params, "sql");
		CLogBook logBook = logBookService.saveCLogBook(null, "导出学历学位汇总", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/*------------------------------------------------------------------个人履历汇总-----------------------------------------------*/
	/**
	 * 个人履历汇总列表
	 * 
	 * @return
	 */
	public String getSumResume() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = " from Staff  sa,StaffResume se,CCode cc where sa.staffID in (select staffID from COS where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID and se.postCase=cc.codeID and cc.companyID=? and cc.codeStatus=?";
		actionName="getSumResume";
		actionNameExcel="resumeShowExcel";
		String[] hearer={"姓名","身份证号","起始时间","截止时间","工作单位","岗位名称","岗位情况","职务","单位地址"};
		for (int i = 0; i < hearer.length; i++) {
			headerList.add(hearer[i]);
		}
		if (search!=null&&search.equals("search")) {
			if (conditions!=null&&!"".equals(conditions.trim())) {
				hql+=" and sa.staffName like '%"+conditions.trim()+"%'";
			}
		}
		session.put("stringHql", hql);
		String[] feilds={"sa.staffName","sa.staffIdentityCard","to_char(se.startTime,'yyyy-mm-dd')","to_char(se.startTime,'yyyy-mm-dd')","se.companyName","se.postName","cc.codeValue","se.position","se.postAddress"};
		session.put("feilds", feilds);
		session.put("hearer", hearer);
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select	sa.staffName,sa.staffIdentityCard,to_char(se.startTime,'yyyy-mm-dd'), to_char(se.endTime,'yyyy-mm-dd'),se.companyName,se.postName,cc.codeValue,se.position,se.postAddress "+hql,
						"select count(sa) "+hql+" order by sa.staffName desc",
						new Object[] { account.getCompanyID(), "50",account.getCompanyID(),"00" });
		return "list";
	}
	
	/**
	 * 个人履历汇总导出
	 * 
	 * @return
	 */
	public String resumeShowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql1=session.get("stringHql").toString();
		Object[] params = { account.getCompanyID(), "50" ,account.getCompanyID(),"00"  };
		ShowExcel(feildName, hql1,params, "hql");
		CLogBook logBook = logBookService.saveCLogBook(null, "导出个人履历汇总", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/*------------------------------------------------------------------家庭成员汇总-----------------------------------------------*/
	/**
	 * 家庭成员汇总列表
	 * 
	 * @return
	 */
	public String getSumFamilyMember() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		String sql=" from dt_hr_staff_familymember se left join dt_hr_staff sa on se.staffid=sa.staffid left join dtccode cc on cc.codeid=se.memberRelationship";
 			   sql+=" left join dtccode dtcc on dtcc.codeid=se.posttype where cc.companyID=? and cc.codeStatus='00' and  dtcc.companyID=? and dtcc.codeStatus='00' ";
			   sql+=" and sa.staffID in (select staffID from dtcos  where companyID = ?  and cosStatus = '50' ) ";
		actionName="getSumFamilyMember";
		actionNameExcel="familyMemberShowExcel";
		String[] hearer={"姓名","身份证号","与本人的关系","出生日期","工作单位","职务","岗位类型","工作内容","住址"};
		for (int i = 0; i < hearer.length; i++) {
			headerList.add(hearer[i]);
		}
		if (search!=null&&search.equals("search")) {
			if (conditions!=null&&!"".equals(conditions.trim())) {
				sql+=" and sa.staffName='"+conditions.trim()+"'";
			}
		}
		session.put("stringHql", sql);
		String[] feilds={"sa.staffName","sa.staffIdentityCard","cc.codeValue","se.memberBirthDay","se.memberCompanyName","se.memberPosition","dtcc.codevalue codevalues","se.memberDuties","se.memberAddress"};
		session.put("feilds", feilds);
		session.put("hearer", hearer);
			pageForm = baseBeanService
					.getPageFormBySQL(
							(null != pageForm ? pageForm.getPageNumber() : 1),
							(pageNumber == 0 ? 10 : pageNumber),
							"select sa.staffName,sa.staffIdentityCard,cc.codeValue, se.memberBirthDay,se.memberCompanyName,se.memberPosition,dtcc.codevalue codevalues,se.memberDuties,se.memberAddress  "+sql,
							"select count(1) "+sql,
							new Object[] { account.getCompanyID(), account.getCompanyID(),account.getCompanyID()});
		return "list";
	}
	
	/**
	 * 家庭成员汇总导出
	 * 
	 * @return
	 */
	public String familyMemberShowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String sql=session.get("stringHql").toString();
		Object[] params = { account.getCompanyID(), account.getCompanyID() ,account.getCompanyID()  };
		ShowExcel(feildName, sql,params, "sql");
		CLogBook logBook = logBookService.saveCLogBook(null, "导出家庭成员汇总", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/*------------------------------------------------------------------健康状况汇总-----------------------------------------------*/
	/**
	 * 健康状况汇总列表
	 * 
	 * @return
	 */
	public String getSumPhysicalCondition() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = " from Staff  sa,StaffPhysicalCondition se,CCode cc where sa.staffID in (select staffID from COS where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID and se.details=cc.codeID and cc.companyID=? and cc.codeStatus=?";
		actionName="getSumPhysicalCondition";
		actionNameExcel="physicalConditionShowExcel";
		String[] hearer={"姓名","身份证号","体检时间","体检医院","体检内容","体检指标","是否正常","医生意见","医生姓名"};
		for (int i = 0; i < hearer.length; i++) {
			headerList.add(hearer[i]);
		}
		if (search!=null&&search.equals("search")) {
			if (conditions!=null&&!"".equals(conditions.trim())) {
				hql+=" and sa.staffName='"+conditions.trim()+"'";
			}
		}
		session.put("stringHql", hql);
		String[] feilds={"sa.staffName","sa.staffIdentityCard","to_char(se.examinationTime,'yyyy-mm-dd')","se.examinationHospital","cc.codeValue","se.indicator","se.normal","se.doctorAdvice","se.doctorName"};
		session.put("feilds", feilds);
		session.put("hearer", hearer);
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select sa.staffName,sa.staffIdentityCard,to_char(se.examinationTime,'yyyy-mm-dd'), se.examinationHospital,cc.codeValue,se.indicator,se.normal,se.doctorAdvice,se.doctorName "+hql,
						"select count(sa) "+hql,
						new Object[] { account.getCompanyID(), "50",account.getCompanyID(),"00" });
		return "list";
	}
	
	/**
	 * 健康状况汇总导出
	 * 
	 * @return
	 */
	public String physicalConditionShowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql=session.get("stringHql").toString();
		Object[] params = { account.getCompanyID(), "50" ,account.getCompanyID(),"00"  };
		ShowExcel(feildName, hql,params, "hql");
		CLogBook logBook = logBookService.saveCLogBook(null, "导出健康状况汇总", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/*------------------------------------------------------------------政治面貌汇总-----------------------------------------------*/
	/**
	 * 政治面貌汇总列表
	 * 
	 * @return
	 */
	public String getSumPoliticalStatus() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = " from Staff  sa,StaffPoliticalStatus se,CCode cc where sa.staffID in (select staffID from COS where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID and se.politicalStatus=cc.codeID and cc.companyID=? and cc.codeStatus=?";
		actionName="getSumPoliticalStatus";
		actionNameExcel="politicalStatusShowExcel";
		String[] hearer={"姓名","身份证号","政治面貌","参加组织日期","参加党派是所在单位","介绍人","转正时间","党(团)龄"};
		for (int i = 0; i < hearer.length; i++) {
			headerList.add(hearer[i]);
		}
		if (search!=null&&search.equals("search")) {
			if (conditions!=null&&!"".equals(conditions.trim())) {
				hql+=" and sa.staffName='"+conditions.trim()+"'";
			}
		}
		session.put("stringHql", hql);
		String[] feilds={"sa.staffName","sa.staffIdentityCard","cc.codeValue","to_char(se.joinDate,'yyyy-mm-dd')","se.unit","se.introducer","to_char(se.probatePassDate,'yyyy-mm-dd')","se.partyStand"};
		session.put("feilds", feilds);
		session.put("hearer", hearer);
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select sa.staffName,sa.staffIdentityCard,cc.codeValue, to_char(se.joinDate,'yyyy-mm-dd'),se.unit,se.introducer,to_char(se.probatePassDate,'yyyy-mm-dd'),se.partyStand "+hql,
						"select count(sa) "+hql,
						new Object[] { account.getCompanyID(), "50",account.getCompanyID(),"00" });
		return "list";
	}
	
	/**
	 * 政治面貌汇总导出
	 * 
	 * @return
	 */
	public String politicalStatusShowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql=session.get("stringHql").toString();
		Object[] params = { account.getCompanyID(), "50" ,account.getCompanyID(),"00"  };
		ShowExcel(feildName, hql,params, "hql");
		CLogBook logBook = logBookService.saveCLogBook(null, "导出政治面貌汇总", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
			/*------------------------------------------------------------------奖励情况汇总-----------------------------------------------*/
	/**
	 * 奖励情况汇总列表
	 * 
	 * @return
	 */
	public String getSumEncourage() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = " from Staff  sa,StaffEncourage se,CCode cc where sa.staffID in (select staffID from COS where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID and se.encourageType=cc.codeID and cc.companyID=? and cc.codeStatus=?";
		actionName="getSumEncourage";
		actionNameExcel="encourageShowExcel";
		String[] hearer={"姓名","身份证号","奖励类别","奖励名称","奖励原因","奖励日期","奖励批准机关名称","荣誉称号","荣誉称号名称","奖励批准日期"};
		for (int i = 0; i < hearer.length; i++) {
			headerList.add(hearer[i]);
		}
		if (search!=null&&search.equals("search")) {
			if (conditions!=null&&!"".equals(conditions.trim())) {
				hql+=" and sa.staffName='"+conditions.trim()+"'";
			}
		}
		session.put("stringHql", hql);
		String[] feilds={"sa.staffName","sa.staffIdentityCard","cc.codeValue","se.encourageName","se.encourageReason","se.encourageDate","se.encourageOrgan","se.honoraryTitle","se.encourageSanction","se.auditor"};
		session.put("feilds", feilds);
		session.put("hearer", hearer);
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select sa.staffName,sa.staffIdentityCard,cc.codeValue,se.encourageName,se.encourageReason,se.encourageDate,se.encourageOrgan,se.honoraryTitle,se.encourageSanction,se.auditor "+hql,
						"select count(sa) "+hql,
						new Object[] { account.getCompanyID(), "50",account.getCompanyID(),"00" });
		return "list";
	}
	
	/**
	 * 奖励情况汇总导出
	 * 
	 * @return
	 */
	public String encourageShowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql=session.get("stringHql").toString();
		Object[] params = { account.getCompanyID(), "50" ,account.getCompanyID(),"00"  };
		ShowExcel(feildName, hql,params, "hql");
		CLogBook logBook = logBookService.saveCLogBook(null, "导出奖励情况汇总", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
					/*------------------------------------------------------------------处分情况汇总-----------------------------------------------*/
	/**
	 * 处分情况汇总
	 * 
	 * @return
	 */
	public String getSumPunishment() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = " from Staff  sa,StaffPunishment se,CCode cc where sa.staffID in (select staffID from COS where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID and se.punishmentType=cc.codeID and cc.companyID=? and cc.codeStatus=?";
		actionName="getSumPunishment";
		actionNameExcel="punishmentShowExcel";
		String[] hearer={"姓名","身份证号","处分类别","处分名称","处分原因","处分批准日期","处分批准机关名称","处分撤销日期"};
		for (int i = 0; i < hearer.length; i++) {
			headerList.add(hearer[i]);
		}
		if (search!=null&&search.equals("search")) {
			if (conditions!=null&&!"".equals(conditions.trim())) {
				hql+=" and sa.staffName='"+conditions.trim()+"'";
			}
		}
		session.put("stringHql", hql);
		String[] feilds={"sa.staffName","sa.staffIdentityCard","cc.codeValue","se.punishmentName","se.punishmentReason","to_char(se.punishmentDate,'yyyy-mm-dd')","se.punishmentOrgan","to_char(se.dischargeDate,'yyyy-mm-dd')"};
		session.put("feilds", feilds);
		session.put("hearer", hearer);
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select sa.staffName,sa.staffIdentityCard,cc.codeValue,se.punishmentName,se.punishmentReason,to_char(se.punishmentDate,'yyyy-mm-dd'),se.punishmentOrgan,to_char(se.dischargeDate,'yyyy-mm-dd') "+hql,
						"select count(sa) "+hql,
						new Object[] { account.getCompanyID(), "50",account.getCompanyID(),"00"  });
		return "list";
	}
	
	/**
	 * 处分情况汇总导出
	 * 
	 * @return
	 */
	public String punishmentShowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql=session.get("stringHql").toString();
		Object[] params = { account.getCompanyID(), "50" ,account.getCompanyID(),"00"  };
		ShowExcel(feildName, hql,params, "hql");
		CLogBook logBook = logBookService.saveCLogBook(null, "导出处分情况汇总", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/*------------------------------------------------------------------调查情况汇总-----------------------------------------------*/
	/**
	 * 调查情况汇总
	 * 
	 * @return
	 */
	public String getSumInvestigation() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = " from Staff  sa,StaffInvestigation se where sa.staffID in (select staffID from COS where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID";
		actionName="getSumInvestigation";
		actionNameExcel="investigationShowExcel";
		String[] hearer={"姓名","身份证号","调查时间","调查项目","调查结论","调查责任人","处理意见","处理责任人","处理时间"};
		for (int i = 0; i < hearer.length; i++) {
			headerList.add(hearer[i]);
		}
		if (search!=null&&search.equals("search")) {
			if (conditions!=null&&!"".equals(conditions.trim())) {
				hql+=" and sa.staffName='"+conditions.trim()+"'";
			}
		}
		session.put("stringHql", hql);
		String[] feilds={"sa.staffName","sa.staffIdentityCard","to_char(se.investigationDate,'yyyy-mm-dd')","se.investigationItem","se.investigationPeroration","se.investigationPrincipal","se.conductIdea","se.conductPrincipal","to_char(se.conductDate,'yyyy-mm-dd')"};
		session.put("feilds", feilds);
		session.put("hearer", hearer);
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select sa.staffName,sa.staffIdentityCard,to_char(se.investigationDate,'yyyy-mm-dd'),se.investigationItem,se.investigationPeroration,se.investigationPrincipal,se.conductIdea,se.conductPrincipal,to_char(se.conductDate,'yyyy-mm-dd') "+hql,
						"select count(sa) "+hql,
						new Object[] { account.getCompanyID(), "50" });
		return "list";
	}
	
	/**
	 * 调查情况汇总导出
	 * 
	 * @return
	 */
	public String investigationShowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql=session.get("stringHql").toString();
		Object[] params = { account.getCompanyID(), "50"  };
		ShowExcel(feildName, hql,params, "hql");
		CLogBook logBook = logBookService.saveCLogBook(null, "导出调查情况汇总", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/*------------------------------------------------------------------证件列表汇总-----------------------------------------------*/
	/**
	 * 证件列表汇总
	 * 
	 * @return
	 */
	public String getSumCertificate() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = " from Staff  sa,StaffCertificate se where sa.staffID in (select staffID from COS where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID";
		actionName="getSumCertificate";
		actionNameExcel="certificateShowExcel";
		String[] hearer={"姓名","身份证号","有效开始时间","有效终止时间","证件名称","证件类型","证件编号","发证(机关)单位"};
		for (int i = 0; i < hearer.length; i++) {
			headerList.add(hearer[i]);
		}
		if (search!=null&&search.equals("search")) {
			if (conditions!=null&&!"".equals(conditions.trim())) {
				hql+=" and sa.staffName='"+conditions.trim()+"'";
			}
		}
		session.put("stringHql", hql);
		String[] feilds={"sa.staffName","sa.staffIdentityCard","to_char(se.invalidateStart,'yyyy-mm-dd')","to_char(se.invalidateEnd,'yyyy-mm-dd')","se.credentialsName","se.credentialsType","se.credentialsNo","se.organ"};
		session.put("feilds", feilds);
		session.put("hearer", hearer);
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select sa.staffName,sa.staffIdentityCard,to_char(se.invalidateStart,'yyyy-mm-dd'),to_char(se.invalidateEnd,'yyyy-mm-dd'),se.credentialsName,se.credentialsType,se.credentialsNo,se.organ "+hql,
						"select count(sa) "+hql,
						new Object[] { account.getCompanyID(), "50" });
		return "list";
	}
	
	/**
	 * 证件列表汇总导出
	 * 
	 * @return
	 */
	public String certificateShowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql=session.get("stringHql").toString();
		Object[] params = { account.getCompanyID(), "50"  };
		ShowExcel(feildName, hql,params, "hql");
		CLogBook logBook = logBookService.saveCLogBook(null, "导出证件列表汇总", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/*------------------------------------------------------------------资料列表汇总-----------------------------------------------*/
	/**
	 * 资料列表汇总
	 * 
	 * @return
	 */
	public String getSumDocumentation() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = " from Staff  sa,StaffDocumentation se where sa.staffID in (select staffID from COS where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID";
		actionName="getSumDocumentation";
		actionNameExcel="documentationShowExcel";
		String[] hearer={"姓名","身份证号","资料编号","资料名称","资料管理开始时间","资料管理终止时间","资料盒编号","资料盒名称"};
		for (int i = 0; i < hearer.length; i++) {
			headerList.add(hearer[i]);
		}
		if (search!=null&&search.equals("search")) {
			if (conditions!=null&&!"".equals(conditions.trim())) {
				hql+=" and sa.staffName='"+conditions.trim()+"'";
			}
		}
		session.put("stringHql", hql);
		String[] feilds={"sa.staffName","sa.staffIdentityCard","se.documentationNumber","se.documentationName","to_char(se.documentationManagerStart,'yyyy-mm-dd')","to_char(se.documentationManagerEnd,'yyyy-mm-dd')","se.documentationBoxNumber","se.documentationBoxName"};
		session.put("feilds", feilds);
		session.put("hearer", hearer);
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select sa.staffName,sa.staffIdentityCard,se.documentationNumber,se.documentationName,to_char(se.documentationManagerStart,'yyyy-mm-dd'),to_char(se.documentationManagerEnd,'yyyy-mm-dd'),se.documentationBoxNumber,se.documentationBoxName "+hql,
						"select count(sa) "+hql,
						new Object[] { account.getCompanyID(), "50" });
		return "list";
	}
	
	/**
	 * 资料列表汇总导出
	 * 
	 * @return
	 */
	public String documentationShowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql=session.get("stringHql").toString();
		Object[] params = { account.getCompanyID(), "50" };
		ShowExcel(feildName, hql,params, "hql");
		CLogBook logBook = logBookService.saveCLogBook(null, "导出资料列表汇总", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/*------------------------------------------------------------------人事档案汇总-----------------------------------------------*/
	/**
	 * 人事档案汇总
	 * 
	 * @return
	 */
	public String getSumPersonalFile() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = " from Staff  sa,StaffPersonalFile se where sa.staffID in (select staffID from COS where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID";
		actionName="getSumPersonalFile";
		actionNameExcel="personalFileShowExcel";
		String[] hearer={"姓名","身份证号","档案编号","档案名称","管理开始时间","管理终止时间","档案责任人","档案盒编号","档案盒名称"};
		for (int i = 0; i < hearer.length; i++) {
			headerList.add(hearer[i]);
		}
		if (search!=null&&search.equals("search")) {
			if (conditions!=null&&!"".equals(conditions.trim())) {
				hql+=" and sa.staffName='"+conditions.trim()+"'";
			}
		}
		session.put("stringHql", hql);
		String[] feilds={"sa.staffName","sa.staffIdentityCard","se.recordCode","se.recordName","to_char(se.controlStartDate,'yyyy-mm-dd')","to_char(se.controlEndDate,'yyyy-mm-dd')","se.dutyOfficer","se.recordBoxCode","se.recordBoxName"};
		session.put("feilds", feilds);
		session.put("hearer", hearer);
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select sa.staffName,sa.staffIdentityCard,se.recordCode,se.recordName,to_char(se.controlStartDate,'yyyy-mm-dd'),to_char(se.controlEndDate,'yyyy-mm-dd'),se.dutyOfficer,se.recordBoxCode,se.recordBoxName "+hql,
						"select count(sa) "+hql,
						new Object[] { account.getCompanyID(), "50" });
		return "list";
	}
	
	/**
	 * 人事档案汇总导出
	 * 
	 * @return
	 */
	public String personalFileShowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql=session.get("stringHql").toString();
		Object[] params = { account.getCompanyID(), "50"  };
		ShowExcel(feildName, hql,params, "hql");
		CLogBook logBook = logBookService.saveCLogBook(null, "导出人事档案汇总", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/*------------------------------------------------------------------银行帐号汇总-----------------------------------------------*/
	/**
	 * 银行帐号汇总列表
	 * 
	 * @return
	 */
	public String getSumBankAccount() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = " from Staff  sa,StaffBankAccount se where sa.staffID in (select staffID from COS where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID";
		actionName="getSumBankAccount";
		actionNameExcel="bankAccountShowExcel";
		String[] hearer={"姓名","身份证号","银行编号","证明号","银行名称","发证日期","账户性质","银行帐号","开户时间","银行联系方式","银行地址"};
		for (int i = 0; i < hearer.length; i++) {
			headerList.add(hearer[i]);
		}
		if (search!=null&&search.equals("search")) {
			if (conditions!=null&&!"".equals(conditions.trim())) {
				hql+=" and sa.staffName='"+conditions.trim()+"'";
			}
		}
		session.put("stringHql", hql);
		String[] feilds={"sa.staffName","sa.staffIdentityCard","se.bankCode","se.approvalNumber","se.bankName","to_char(se.theDate,'yyyy-mm-dd')","se.accountNature","se.bankAccount","to_char(se.openDate,'yyyy-mm-dd')","se.bankContact","se.bankAddress"};
		session.put("feilds", feilds);
		session.put("hearer", hearer);
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select sa.staffName,sa.staffIdentityCard,se.bankCode,se.approvalNumber,se.bankName,to_char(se.theDate,'yyyy-mm-dd'),se.accountNature,se.bankAccount,to_char(se.openDate,'yyyy-mm-dd'),se.bankContact,se.bankAddress "+hql,
						"select count(sa) "+hql,
						new Object[] { account.getCompanyID(), "50" });
		return "list";
	}
	
	/**
	 * 银行帐号汇总导出
	 * 
	 * @return
	 */
	public String bankAccountShowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql=session.get("stringHql").toString();
		Object[] params = { account.getCompanyID(), "50"   };
		ShowExcel(feildName, hql,params, "hql");
		CLogBook logBook = logBookService.saveCLogBook(null, "银行帐号汇总导出", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	/*------------------------------------------------------------------合同汇总-----------------------------------------------*/
	/**
	 * 合同汇总列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getSumAgreement() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = " from Staff sa,StaffAgreement se where sa.staffID in (select staffID from COS where companyID = ?  and cosStatus = ? ) and sa.staffID=se.staffID";
		actionName="getSumAgreement";
		actionNameExcel="agreementShowExcel";
		String[] hearer={"姓名","合同类型","合同编号","签订日期","终止日期","解除日期","附件","备注"};
		for (int i = 0; i < hearer.length; i++) {
			headerList.add(hearer[i]);
		}
		if (search!=null&&search.equals("search")) {
			if (conditions!=null&&!"".equals(conditions.trim())) {
				hql+=" and sa.staffName like'%"+conditions.trim()+"%'";
			}
		}

		session.put("stringHql", hql);
		String[] feilds={"sa.staffName","se.status","se.contractCode","to_char(se.contractSignDate,'yyyy-mm-dd')","to_char(se.relieveContractDate,'yyyy-mm-dd')","to_char(se.renewalDate,'yyyy-mm-dd')","se.attachUrl","se.remark"};
		session.put("feilds", feilds);
		session.put("hearer", hearer);
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 15 : pageNumber),
						"select sa.staffName,case se.status when '00' then '参观期协议' " +
						"when '01' then '培训期合同' when '02' then '劳动合同'  when '03' then '竞职协议' when '04' " +
						"then '保密协议' when '05' then '安全责任协议' " +
						"end,se.contractCode,to_char(se.contractSignDate,'yyyy-mm-dd'),to_char(se.relieveContractDate,'yyyy-mm-dd'),to_char(se.renewalDate,'yyyy-mm-dd'),se.attachUrl,se.remark "+hql,
						"select count(sa) "+hql,
						new Object[] { account.getCompanyID(), "50" });
		

		if(pageForm != null){
			@SuppressWarnings("rawtypes")
			ArrayList list = new ArrayList();
			for(int i = 0; i<pageForm.getList().size();i++){
				Object obj=(Object)pageForm.getList().get(i);
				Object[] objArray=(Object[])obj;
				if(objArray[6]== null){
					objArray[6] = "无";
				}else{
					objArray[6] = "<a href='#' class='aUrl' id='"+objArray[6].toString()+"'>查看</a>";
				}	
				list.add(objArray);
			}
			pageForm.setList(list);
		}
		
		
		return "list";
	}
	
	/**
	 * 合同汇总导出
	 * 
	 * @return
	 */
	public String agreementShowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql=session.get("stringHql").toString();
		Object[] params = { account.getCompanyID(), "50"   };
		ShowExcel(feildName, hql,params, "hql");
		CLogBook logBook = logBookService.saveCLogBook(null, "银行帐号汇总导出", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
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
	public List<String> getHeaderList() {
		return headerList;
	}
	public void setHeaderList(List<String> headerList) {
		this.headerList = headerList;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionNameExcel() {
		return actionNameExcel;
	}

	public void setActionNameExcel(String actionNameExcel) {
		this.actionNameExcel = actionNameExcel;
	}

	public String getBasicInfo() {
		return basicInfo;
	}

	public void setBasicInfo(String basicInfo) {
		this.basicInfo = basicInfo;
	}
	
	public String getFeildName() {
		return feildName;
	}

	public void setFeildName(String feildName) {
		this.feildName = feildName;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
}
