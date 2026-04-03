package hy.ea.driving.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.driving.DtDrivingAllInformation;
import hy.ea.bo.driving.DtDrivingOperationPeople;
import hy.ea.bo.driving.DtDrivingPrincipal;
import hy.ea.bo.driving.DtDrivingPrincipalType;
import hy.ea.bo.driving.DtDrivingTest;
import hy.ea.bo.driving.DtDrivingTestOther;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.CostSheetBill;
import hy.ea.bo.invoicing.CostSheetDetail;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.archives.DtArchivesArchives;
import hy.ea.bo.office.archives.DtArchivesArchives.ArchiveTemp;
import hy.ea.bo.office.archives.DtArchivesArchiveshistory;
import hy.ea.bo.office.archives.DtArchivesCataloguearchives;
import hy.ea.office.service.IArchiveService;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.DateUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 教务
 * 
 * @author zc
 * 
 */
@Controller
@Scope("prototype")
public class DrivingAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CCodeService codeService;
	@Resource
	private IArchiveService archiveService;
	@Resource
	private CompanyService companyserverService;
	public InputStream excelStream;
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private String sdate;
	private String edate;
	private List<BaseBean> beans;
	private ArchiveTemp archiveTemp;

	/** *************************教务******************************** */

	private DtDrivingPrincipal dtDrivingPrincipal;

	private DtDrivingOperationPeople dtDrivingOperationPeople;

	private DtDrivingTest dtDrivingTest;

	private DtDrivingPrincipalType dtDrivingPrincipalType;

	private String companyName;

	private List<CCode> examinationList;
	
	private List<CCode> appointmentList;

	public String dateYuJing;

	/**
	 * 01 科一 02 科二桩考 03 科二场地 04 科三路考
	 */
	private String docstatus;
	/**
	 * 学员状态 ⦁ 00 未报名 ⦁ 01 已报名-------未收集 ⦁ 02 已报名-------未分车 ⦁ 03
	 * 已收集-------已分车--------未培训 ⦁ 04 已培训-------未报开学 ⦁ 05 已报开学----未约考 ⦁ 06 已约考 07
	 * 已合格
	 */
	private String studentstatus;

	/**
	 * 标识跳转页面
	 */
	private String title;
	/**
	 * 车辆信息
	 */
	private CarInformation carInformation;

	/**
	 * 多选框参数传递
	 * 
	 * @return
	 */
	private String str;
	/**
	 * 查询条件 conditions="" 待处理 conditions="conditions" 已处理
	 * 
	 * @return
	 */
	private String conditions;
	/**
	 * 导出
	 * 
	 */
	private String excel;
	/**
	 * 
	 * 综合查询
	 */
	private String total;
	/**
	 * 
	 * 区分 培训组 与 考试管理
	 */
	private String other;

	/**
	 * 条件查询 保存查询条件
	 * 
	 * @return
	 */
	private String extensionStaffCoach;
	
	/**
	 * 
	 * @是否  作用于档案编辑
	 * @return
	 */
	private String isShowDangAn;
	
	/**
	 * 学员得分
	 * @return
	 */
	private String strsScore;
	/**
	 * 标识符 区分跳转页面或者模块入口 等
	 * identifier=drivingListYueKaoTestOfList;  预约测试
	 * identifier=dataIsComplete;  资料是否齐全
	 * identifier=baikaixue;  学员是否报开学
	 */
	private String identifier;
	/**
	 * 汇总查询预约测试以及预约考试 传参 多个科目标识符字符串
	 */
	private String docstatusStr;
	/**
	 * request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	Map<String, Object> request = (Map<String, Object>) ActionContext.getContext().get("request");
	/**
	 * 
	 * @return
	 */
	private Staff cstaff;
	/**
	 * 测试
	 * @return
	 */
	private Map<String, DtDrivingTestOther> dtDrivingTestOtherMap;
	/**
	 * 考试
	 * @return
	 */
	private Map<String, DtDrivingTest> dtDrivingTestMap;
	
	/**
	 * @time 2015-01-12
	 * @author zc
	 * @describe  动态生成导出excel 文件名称
	 * @return String
	 */
	private String filename;
	/**
	 * 学员基本信息查询
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch",
				dtDrivingPrincipal);
		ActionContext.getContext().getSession().put("tablesearch1",
				dtDrivingPrincipalType);
		return getDrivingList();
	}

	/**
	 * 默认获取科一收集的数据list
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDrivingList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		// String ogtID = (String) session.get("organizationID");
		try {
			if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
				return "syserror";
			}
		} catch (Exception e) {
			return "syserror";
		}
		Map<String, Object> map_objects=getSqlAndParamsOfDriving();
		String sql=(String)map_objects.get("sql");
		List<Object> params=(List<Object>)map_objects.get("params");
		String returns=(String)map_objects.get("return");
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber),sql, " select count(*) "
						+ sql.substring(sql.indexOf("from")), params.toArray());

		return returns;
	}
	/**
	 * @time 2015-01-12
	 * @author zc
	 * @describe 导出excel 证件合格与否学员与是否报开学学员
	 * @return excelStream
	 */
	@SuppressWarnings("unchecked")
	public String exportOfExcelOfDriving(){
		
		String[] title={}; 
		String   sql_select="";
		if("dataIsComplete".equals(identifier)){
			filename="学员证件";
			title=DtDrivingPrincipal.columnHeadingsOfdataIsComplete();
			sql_select= "select dp.registrationdate,dp.studentname,dp.studentsex,dp.studentphone,dp.studentcard,dp.registrationcarname ";
		}else if("baokaixue".equals(identifier)){
			filename="学员报开学";
			title=DtDrivingPrincipal.columnHeadingsOfBaoKaiXue();
			sql_select= "select dp.registrationdate,dp.schoolopendate,dp.studentCode,dp.studentPeriods,dp.studentname,dp.studentsex,dp.studentphone,dp.studentcard,dp.registrationcarname ";
		}
		Map<String, Object> map_objects=getSqlAndParamsOfDriving();
		String sql=(String)map_objects.get("sql");
		List<Object> params=(List<Object>)map_objects.get("params");
		String sql_rewrite=sql_select+sql.substring(sql.indexOf("from"));
		excelStream = excelService.showExcel(title,
				baseBeanService.getListBeanBySqlAndParams(sql_rewrite, params.toArray()));
		return "showexcel";
	}
	/**
	 * @time 2015-01-12
	 * @author zc
	 * @return sql:String,params: List,return:String
	 */
	public Map<String, Object> getSqlAndParamsOfDriving(){
		 Map<String, Object> map_objects=new HashMap<String, Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		examinationList = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode201211095p8932ixtz0000000014");
		appointmentList= codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20141215fa6d3b3kc80000000002");
		companyName = ((Company) baseBeanService.getBeanByHqlAndParams(
				" from Company where companyid=?", new Object[] { account
						.getCompanyID() })).getCompanyName();

		String sql = "select dp.drivingprincipalkey,dp.drivingprincipalid,dp.companyid,c.companyname,dp.organizationid,cg.organizationname,dp.responsiblePersonsID,dp.registrationdate,dp.registrationcarid,dp.registrationcarname,"
				+ "dp.studentname,dp.studentsex,dp.studentphone,dp.studentcard,dp.resgistrationmedicaldate,dp.istrues,"
				+ "dp.operationdate,dp.reason,dp.studentid,dj.coachid,dj.coachname,dj.studentstatus,dp.schoolopendate, "
				+ "dj.testresult,dj.testsum,dj.subjectsstatus,dh.staffname,dj.carNumber,dj.docstatus,dh2.reference,dh2.staffIdentityCard,dp.studentCode,dp.studentPeriods "
				+ " ,dj.toTestOther,dj.testOtherResult,dp.dataIsComplete "
				+ " from dt_driving_principal_type dj "
				+ " left join dt_driving_principal dp on  dp.drivingprincipalid=dj.drivingprincipalid "
				+ " left join dtcompany  c on c.companyid=dp.companyid "
				+ " left join dtccode cd on cd.codeid=dp.registrationcarid "
				+ " left join dt_hr_staff dh on dh.staffid=dp.responsiblePersonsID "
				+ " left join dt_Car_CarInformation dh1 on dh1.carID=dj.coachid "
				+ " left join dt_hr_staff dh2 on dh2.staffID=dh1.staffID "
				+ " left join dtcorganization cg on cg.organizationid=dp.organizationid where 1=1";
		List<Object> array = new ArrayList<Object>();
		sql += " and dp.companyid=? ";
		array.add(account.getCompanyID());
		/** 收集中体检时间预警* */
		if (dateYuJing != null && "dateYuJing".equals(dateYuJing)) {
			sql += " and dp.resgistrationmedicaldate <=(sysdate-10)";
		}
		/** 科目状态* */
		if (docstatus != null && !"".equals(docstatus.trim())) {
			sql += " and dj.docstatus = ? ";
			array.add(docstatus.trim());
		}
		if (search != null && "search".equals(search)) {
			dtDrivingPrincipal = (DtDrivingPrincipal) session
					.get("tablesearch");
			dtDrivingPrincipalType = (DtDrivingPrincipalType) session
					.get("tablesearch1");
			if(dtDrivingPrincipal!=null){
				if (dtDrivingPrincipal.getStudentname() != null
						&& !"".equals(dtDrivingPrincipal.getStudentname().trim())) {
					sql += " and dp.studentname like '%"
							+ dtDrivingPrincipal.getStudentname().trim() + "%'";
				}
				if (dtDrivingPrincipal.getStudentcard() != null
						&& !"".equals(dtDrivingPrincipal.getStudentcard().trim())) {
					sql += " and dp.studentcard like '%"
							+ dtDrivingPrincipal.getStudentcard().trim() + "%'";
				}
				if (dtDrivingPrincipal.getReason() != null
						&& !"".equals(dtDrivingPrincipal.getReason().trim())) {
					sql += " and dp.reason like ? ";
					array.add("%" + dtDrivingPrincipal.getReason().trim() + "%");
				}
				if (dtDrivingPrincipal.getSearchStaDate() != null && dtDrivingPrincipal.getSearchEndDate()  != null && !("").equals(dtDrivingPrincipal.getSearchStaDate() )
						&& !("").equals(dtDrivingPrincipal.getSearchEndDate() )) {
					sql += " and dp.schoolopendate between ? and ? ";
					array.add(dtDrivingPrincipal.getSearchStaDate() );
					dtDrivingPrincipal.getSearchEndDate().setDate(dtDrivingPrincipal.getSearchEndDate().getDate()+1);
					array.add(dtDrivingPrincipal.getSearchEndDate() );
				}
				if (dtDrivingPrincipal.getSearchStaDateOne() != null && dtDrivingPrincipal.getSearchEndDateOne()  != null && !("").equals(dtDrivingPrincipal.getSearchStaDateOne() )
						&& !("").equals(dtDrivingPrincipal.getSearchEndDateOne() )) {
					sql += " and dp.registrationdate between ? and ? ";
					array.add(dtDrivingPrincipal.getSearchStaDateOne() );
					dtDrivingPrincipal.getSearchEndDateOne().setDate(dtDrivingPrincipal.getSearchEndDateOne().getDate()+1);
					array.add(dtDrivingPrincipal.getSearchEndDateOne() );
				}
				if (dtDrivingPrincipal.getRegistrationcarname() != null
						&& !"".equals(dtDrivingPrincipal.getRegistrationcarname()
								.trim())) {
					sql += " and dp.registrationcarname like ? ";
					array.add("%"+dtDrivingPrincipal.getRegistrationcarname().trim().split("/")[0]+"%");
				}
				if(dtDrivingPrincipal.getDataIsComplete() != null
						&& !"".equals(dtDrivingPrincipal.getDataIsComplete())){
					if("00".equals(dtDrivingPrincipal.getDataIsComplete())){
						sql += " and dp.dataIsComplete = ? ";
						array.add(dtDrivingPrincipal.getDataIsComplete());
					}else{
						sql += " and (dp.dataIsComplete = ? or dp.dataIsComplete is null) ";
						array.add(dtDrivingPrincipal.getDataIsComplete());
					}
				}
			}
			if(dtDrivingPrincipalType!=null){
				if (dtDrivingPrincipalType != null
						&& dtDrivingPrincipalType.getCoachname() != null
						&& !"".equals(dtDrivingPrincipalType.getCoachname().trim())) {
					sql += " and dj.coachname like ? ";
					array.add("%" + dtDrivingPrincipalType.getCoachname().trim()
							+ "%");
				}
				if (dtDrivingPrincipalType != null
						&& dtDrivingPrincipalType.getCarNumber() != null
						&& !"".equals(dtDrivingPrincipalType.getCarNumber().trim())) {
					sql += " and dj.carNumber like ? ";
					array.add("%" + dtDrivingPrincipalType.getCarNumber().trim()
							+ "%");
				}
				
				
				if (dtDrivingPrincipalType.getToTestOther() != null
						&& !"".equals(dtDrivingPrincipalType.getToTestOther()
								.trim())) {
					if("已预约".equals(dtDrivingPrincipalType.getToTestOther())){
						sql += " and dj.toTestOther = ? ";
						array.add("01");
					}else{
						sql += " and dj.toTestOther is null ";
					}
					
				}
				/**
				 * 测试结果合格与否
				 */
				if (dtDrivingPrincipalType.getTestOtherResult() != null
						&& !"".equals(dtDrivingPrincipalType.getTestOtherResult()
								.trim())) {
					if("合格".equals(dtDrivingPrincipalType.getTestOtherResult())){
						sql += " and dj.testOtherResult = ? ";
						array.add("02");
					}else{
						sql += " and (dj.testOtherResult <> ?  or dj.testOtherResult is null)";
						array.add("02");
					}
					
				}
				/**
				 * 考试结果合格与否
				 */
				if (dtDrivingPrincipalType.getTestresult() != null
						&& !"".equals(dtDrivingPrincipalType.getTestresult()
								.trim())) {
					if("02".equals(dtDrivingPrincipalType.getTestresult())){
						sql += " and dj.testresult = ? ";
						array.add("02");
					}else if("01".equals(dtDrivingPrincipalType.getTestresult())){
						sql += " and dj.testresult = ?  ";
						array.add("01");
					}else{
						sql += " and (dj.testresult = ?  or dj.testresult is null ) ";
						array.add("00");
					}
					
				}
				if ("total".equals(total)) {

					if (dtDrivingPrincipalType.getDocstatus() != null
							&& !"".equals(dtDrivingPrincipalType.getDocstatus()
									.trim())) {
						sql += " and dj.docstatus = ? ";
						array.add(dtDrivingPrincipalType.getDocstatus().trim());
					}
					if (dtDrivingPrincipalType.getStudentstatus() != null
							&& !"".equals(dtDrivingPrincipalType.getStudentstatus()
									.trim())) {
						sql += " and dj.studentstatus = ? ";
						array.add(dtDrivingPrincipalType.getStudentstatus().trim());
					}
				}
			}
		}
		String returns = null;// 返回结果页面
		 if ("01".equals(docstatus) && "04".equals(studentstatus)) {
			if("baokaixue".equals(identifier)){
				if ("conditions".equals(conditions)) {
					sql += " and  dj.studentstatus <> '04' "; //  已报开学
				}else if("conditionsFalse".equals(conditions)){
					sql += " and dj.studentstatus ='04' "; //   未报开学
				}
			}else{
				if ("conditions".equals(conditions)) {
					sql += " and  dj.studentstatus ='05' "; //  已报开学
				}else if("conditionsFalse".equals(conditions)){
					sql += " and dj.studentstatus ='04' "; //   未报开学
				}  else {
					sql += " and (dj.studentstatus ='04' or dj.studentstatus ='05') ";	
				}
			}
			returns = "driving_list_baokaixue";
		}else if ("01".equals(docstatus) && "05".equals(studentstatus)
				&& "01".equals(title)) {
			if ("conditions".equals(conditions)) {
				sql += " and istrues='合格' and dj.studentstatus ='03' "; //  科一预约培训  已预约培训
			}else if("conditionsFalse".equals(conditions)){
				sql += " and (istrues is null or istrues ='不合格') and dj.studentstatus ='05' ";  //  科一预约培训  未预约培训
			}  else {
				sql+="  and (dj.studentstatus ='05' or dj.studentstatus ='03')";
			}
			returns = "driving_list";
		} else if ("01".equals(docstatus) && "03".equals(studentstatus)) {
			if ("conditions".equals(conditions)) {
				sql += " and  dj.studentstatus ='08' ";// 科一培训     已培训
			}else if("conditionsFalse".equals(conditions)){
				sql += " and dj.studentstatus ='03' "; // 科一培训     未培训
			} else {
				sql+=" and (dj.studentstatus ='08' or dj.studentstatus = '03') ";
			}
			returns = "driving_list_peixun";
		} else if ("01".equals(docstatus) && "02".equals(studentstatus)) {
			if ("conditions".equals(conditions)) {
				sql += " and  ( (dj.studentstatus = '06' and dj.testresult is not null)   or dj.studentstatus ='07') ";// 科一约考  已约考
			}else if("conditionsFalse".equals(conditions)){
				sql += " and ( (dj.studentstatus = '06' and dj.testresult is  null)   or dj.studentstatus ='08' )";// 科一约考  未约考
			}  else {
				sql += " and ( dj.studentstatus = '06'  or dj.studentstatus ='08' or dj.studentstatus ='07')";
			}
			returns = "driving_list_yuekao";
		} else if (!"01".equals(docstatus) && "02".equals(studentstatus)) {//---------------------------以下是 科二 到 科四学员状态
			if ("conditions".equals(conditions)) {
				sql += " and  dj.studentstatus = '03' ";// 科二，科三，科四  已预约培训
			} else if("conditionsFalse".equals(conditions)){
				sql += " and dj.studentstatus ='02' ";// 科二，科三，科四  未预约培训
			} else {
				sql += " and (dj.studentstatus ='02' or dj.studentstatus = '03') ";	
			}
			returns = "driving_list2";
		} else if (!"01".equals(docstatus) && "03".equals(studentstatus)) {
			if ("conditions".equals(conditions)) {
				sql += " and  dj.studentstatus = '05' ";// 科二，科三，科四  已培训
			}else if("conditionsFalse".equals(conditions)){
				sql += " and dj.studentstatus ='03' ";// 科二，科三，科四  未培训
			}  else {
				sql += " and (dj.studentstatus ='03' or dj.studentstatus = '05') ";
			}
			returns = "driving_list_peixun";
		} else if (!"01".equals(docstatus) && "04".equals(studentstatus)) {
			if ("conditions".equals(conditions)) {
				sql += " and  ( (dj.studentstatus = '06' and dj.testresult is not null)   or dj.studentstatus ='07') "; // 科二，科三，科四  （已约考不合格） or 已合格
			}else if("conditionsFalse".equals(conditions)){
				sql += " and ( (dj.studentstatus = '06' and dj.testresult is  null)   or dj.studentstatus ='05' )";// 科二，科三，科四  未约考，未处理
			}  else {
				sql += " and ( dj.studentstatus = '06'  or dj.studentstatus ='05' or dj.studentstatus ='07')";
			}
			returns = "driving_list_yuekao";
		}else if("07".equals(studentstatus)){
			sql += " and  dj.studentstatus ='07' "; // 科一，二，三，四合格归档 
			returns = "driving_list_archive";
			
		} else if("total".equals(total)){/** 2014-11-19  新增 预约测试以及预约考试所有科目汇总 查询条件 由于（科一与科二，三，四）预约测试以及预约考试数据的查询条件不同 */
			if ("conditions".equals(conditions)) {
				sql += " and  ( (dj.studentstatus = '06' and dj.testresult is not null)   or dj.studentstatus ='07') "; // 科二，科三，科四  （已约考不合格） or 已合格
			}else if("conditionsFalse".equals(conditions)){
				sql += " and ( (dj.studentstatus = '06' and dj.testresult is  null)   or dj.studentstatus ='05' or dj.studentstatus ='08' )";// 科二，科三，科四  未约考，未处理
			}  else {
				sql += " and ( dj.studentstatus = '06'  or dj.studentstatus ='05' or dj.studentstatus ='07' or dj.studentstatus ='08')";
			}
		}
		/**
		 * 根据学员ID查询教练信息--新版学员报名模块调用
		 */
		if("extensionStaffCoach".equals(extensionStaffCoach)){
			sql += " and dp.studentid=? ";
			array.add(dtDrivingPrincipal.getStudentid());
			returns = "extension_staff_coach";
		}
		/**
		 * 根据学员ID查询学员--新版学员报名调用
		 */
		if("extensionStaffStudent".equals(extensionStaffCoach)){
			sql += " and dp.studentid=? ";
			array.add(dtDrivingPrincipal.getStudentid());
		}
		/**
		 * 预约测试 跳转页面
		 * @return jsp
		 * @data List
		 * @time 2014-11-18
		 */
		if("drivingListYueKaoTestOfList".equals(identifier)){
			returns="drivingListYueKaoTestOfList";
		}
		if ("total".equals(total)) {
			if("drivingListYueKaoTestOfList".equals(identifier)){
				returns = "driving_list_total_test";
			}else{
				returns = "driving_list_total";
			}
			
		}
		/**
		 * 资料是否齐全 跳转页面
		 * @return jsp
		 * @data List
		 * @time 2015-01-06
		 */
		if("dataIsComplete".equals(identifier)){
			returns="dataIsComplete";
		}
		/**
		 * 资料是否齐全 跳转页面
		 * @return jsp
		 * @data List
		 * @time 2015-01-06
		 */
		if("baokaixue".equals(identifier)){
			returns="baokaixue";
		}
		sql += " order by dp.registrationdate desc nulls last,dp.schoolopendate desc nulls last,dj.docstatus asc  ";
		
		map_objects.put("sql", sql);
		map_objects.put("params", array);
		map_objects.put("return", returns);
		return map_objects;
	}
	/**
	 * 修改学员信息
	 * 
	 * @return
	 */
	public String saveDriving() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		beans = new ArrayList<BaseBean>();
		/**
		 * 收集-学员信息
		 */
		String hql = " from DtDrivingPrincipal where  companyid=? and drivingprincipalid = ?";
		DtDrivingPrincipal divingPripal = (DtDrivingPrincipal) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] {
						account.getCompanyID(),
						dtDrivingPrincipal.getDrivingprincipalid() });

		divingPripal.setOperationdate(new Date());
		divingPripal.setRegistrationdate(dtDrivingPrincipal
				.getRegistrationdate());
		divingPripal.setRegistrationcarid(dtDrivingPrincipal
				.getRegistrationcarid());
		divingPripal.setResgistrationmedicaldate(dtDrivingPrincipal
				.getResgistrationmedicaldate());
		divingPripal.setIstrues(dtDrivingPrincipal.getIstrues());
		divingPripal.setReason(dtDrivingPrincipal.getReason());

		String hql1 = " from DtDrivingPrincipalType where companyid=? and docstatus =? and drivingprincipalid=?";
		DtDrivingPrincipalType dtDringPrialType = (DtDrivingPrincipalType) baseBeanService
				.getBeanByHqlAndParams(hql1, new Object[] {
						account.getCompanyID(), "01",
						divingPripal.getDrivingprincipalid() });
		if("合格".equals(divingPripal.getIstrues())){
			dtDringPrialType.setStudentstatus("03");
		}
		/**
		 * 收集-操作人记录保存
		 */
		List<BaseBean> beans_part=saveOperationPeople(dtDringPrialType, divingPripal);
		/**
		 * 保存beans
		 */
		CLogBook logBook = logBookService.saveCLogBook(null, "修改学员信息", account);

		beans.add(logBook);
		beans.add(dtDringPrialType);
		beans.add(divingPripal);
		beans.addAll(beans_part);

		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		if (dtDrivingPrincipal.getIstrues() != null
				&& dtDrivingPrincipal.getIstrues().equals("合格")&&"是".equals(isShowDangAn)) {

			// 存储档案信息
			String hqlcc = "from DtArchivesCataloguearchives where catemodule = ? and comanyid = ?";

			DtArchivesCataloguearchives addcatalogue = null;
			DtArchivesCataloguearchives cataloguetest = (DtArchivesCataloguearchives) baseBeanService
					.getBeanByHqlAndParams(hqlcc, new Object[] {
							organizationID, account.getCompanyID() });
			if (cataloguetest == null) {

				String parentid = "";
				for (int i = 0; i < 5; i++) {
					addcatalogue = new DtArchivesCataloguearchives();
					if (i == 0) {
						addcatalogue.setName("教务处");
						addcatalogue.setCatemodule(organizationID);
					} else if (i == 1) {
						addcatalogue.setName("理论");
						addcatalogue.setCatemodule("theory");
					} else if (i == 2) {
						addcatalogue.setName("桩考");
						addcatalogue.setCatemodule("piletest");
					} else if (i == 3) {
						addcatalogue.setName("场地");
						addcatalogue.setCatemodule("yard");
					} else {
						addcatalogue.setName("路考");
						addcatalogue.setCatemodule("roadtest");
					}
					if (i == 0) {
						parentid = serverService.getServerID("catalogueid");
						addcatalogue.setArchiveid(parentid);
					} else {
						addcatalogue.setArchiveid(serverService
								.getServerID("catalogueid"));
						addcatalogue.setParent(parentid);
					}
					addcatalogue.setComanyid(account.getCompanyID());

					Date cur = new Date();
					addcatalogue.setCreatetime(cur);
					addcatalogue.setCreateuser(account.getStaffID());
					addcatalogue.setModifytime(cur);
					addcatalogue.setModifyuser(account.getStaffID());
					baseBeanService.save(addcatalogue);

				}

			}
			DtArchivesCataloguearchives catalogue = (DtArchivesCataloguearchives) baseBeanService
					.getBeanByHqlAndParams(hqlcc, new Object[] { "theory",
							account.getCompanyID() });

			DtArchivesArchives archive = new DtArchivesArchives();
			DtArchivesArchiveshistory history = new DtArchivesArchiveshistory();

			// 对档案的存储
			String archivesid = serverService.getServerID("archiveid");
			archive.setArchivesid(archivesid);
			archive.setBarcode(archiveTemp.getBarcode());
			archive.setChipid(archiveTemp.getChipid());
			archive.setStaffID(divingPripal.getStudentid());
			if (archiveTemp.getStartDate() != null
					&& !archiveTemp.getStartDate().equals("")) {
				archive.setStartValidity(Utilities.getDateFromString(
						archiveTemp.getStartDate(), "yyyy-MM-dd"));
			}
			if (archiveTemp.getEndDate() != null
					&& !archiveTemp.getEndDate().equals("")) {
				archive.setEndValidity(Utilities.getDateFromString(archiveTemp
						.getEndDate(), "yyyy-MM-dd"));
			}
			String hqlstaff = "from Staff where staffID = ?";
			Staff student = (Staff) baseBeanService.getBeanByHqlAndParams(
					hqlstaff,
					new Object[] { dtDrivingPrincipal.getStudentid() });

			archive.setName(student.getStaffName() + "("
					+ student.getStaffCode() + ")档案");
		    archive.setArchiveCode(student.getRecordCode());
			archive.setCompanyid(account.getCompanyID());
			archive.setCreatedate(new Date());
			archive.setCreatuser(account.getStaffID());
			archive.setDtArchivesCataloguearchives(catalogue);
			archive.setArchiveCode(archiveService.getCode(account
					.getCompanyID()));
			archive.setSecuritylevel("03");
			archive.setObsoletestatus("00");
			baseBeanService.save(archive);

			String hqlarchive = "from DtArchivesArchives where archivesid = ?";
			DtArchivesArchives oldArchive = (DtArchivesArchives) baseBeanService
					.getBeanByHqlAndParams(hqlarchive,
							new Object[] { archivesid });

			// 对历史记录的存储
			history.setHistoryid(serverService.getServerID("histoyid"));
			history.setIntime(new Date());
			history.setInuser(account.getStaffID());
			history.setState("1");
			history.setDtArchivesInventorylocation(null);
			history.setDtArchivesArchives(oldArchive);
			baseBeanService.save(history);
		}
		return "success";
	}
	
	/**
	 * 操作记录（DtDrivingOperationPeople）以及学员主表（DtDrivingPrincipal）最终操作时间处理保存
	 * @param drivingPrincipalType
	 * @param dtDrivingPrincipal
	 * @return
	 */
	public List<BaseBean>  saveOperationPeople(DtDrivingPrincipalType  drivingPrincipalType,DtDrivingPrincipal dtDrivingPrincipal){
		List<BaseBean>  beans_part=new ArrayList<BaseBean>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		/**
		 * 修改主表DtDrivingPrincipal最后操作时间
		 */
		if(dtDrivingPrincipal==null){
			String hql = " from DtDrivingPrincipal where  companyid=? and drivingprincipalid = ?";
			dtDrivingPrincipal= (DtDrivingPrincipal) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] {
							account.getCompanyID(),
							drivingPrincipalType.getDrivingprincipalid() });
			dtDrivingPrincipal.setOperationdate(new Date());
			beans_part.add(dtDrivingPrincipal);
		}
		/**
		 * 收集-操作人记录保存
		 */
		dtDrivingOperationPeople = new DtDrivingOperationPeople();
		dtDrivingOperationPeople.setDrivingoperationpropleid(serverService
				.getServerID("dtDringOpatieople"));
		dtDrivingOperationPeople.setDrivingprincipalid(dtDrivingPrincipal
				.getDrivingprincipalid());
		dtDrivingOperationPeople.setOperatinid(account.getStaffID());// 操作人ID
		dtDrivingOperationPeople.setOperationname(account.getStaffName());// 操作人名称
		dtDrivingOperationPeople.setOperationdate(dtDrivingPrincipal
				.getOperationdate());
		dtDrivingOperationPeople.setOperationstatus(drivingPrincipalType
				.getDocstatus());// 记录主表科目状态
		dtDrivingOperationPeople.setOperationstatus1(drivingPrincipalType
				.getStudentstatus());// 记录科目下子模块状态
		dtDrivingOperationPeople.setIstrues(dtDrivingPrincipal.getIstrues());// 记录合格与否
		dtDrivingOperationPeople.setNotes(dtDrivingPrincipal.getReason());// 记录不合格原因
		beans_part.add(dtDrivingOperationPeople);
		return beans_part;
	}
	/**
	 * 删除科一收集
	 */

	public String delDriving() {
		String hql = " delete from DtDrivingPrincipal where  drivingprincipalid=?";
		String hql1 = " delete from DtDrivingOperationPeople where  drivingprincipalid=?";
		String hql2 = " delete from DtDrivingPrincipalType where drivingprincipalid=?";
		Object[] param = { dtDrivingPrincipal.getDrivingprincipalid() };
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[] {
				hql, hql1, hql2 }, param);

		return "success";
	}

	/**
	 * 车辆信息列表
	 */
	public String getCarInformationList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		String hqlcarsearch = "from CarInformation cars where 1=1";
		List<Object> parms = new ArrayList<Object>();
		hqlcarsearch += "and cars.companyID=?";
		parms.add(companyID);
		hqlcarsearch += "and cars.state3 = ?";
		parms.add("01");
		if (search != null && search.equals("search")) {
			carInformation = (CarInformation) session.get("tablesearch");
			if (carInformation.getCarNum() != null
					&& !"".equals(carInformation.getCarNum().trim())) {
				hqlcarsearch += "and cars.carNum like ?";
				parms.add("%" + carInformation.getCarNum().trim() + "%");
			}
			if (carInformation.getCarType() != null
					&& !"".equals(carInformation.getCarType().trim())) {
				hqlcarsearch += "and cars.carType like ?";
				parms.add("%" + carInformation.getCarType().trim() + "%");
			}
			if (carInformation.getStaffName() != null
					&& !"".equals(carInformation.getStaffName().trim())) {
				hqlcarsearch += "and cars.staffName like ?";
				parms.add("%" + carInformation.getStaffName().trim() + "%");
			}
			if (carInformation.getState1() != null
					&& !"".equals(carInformation.getState1().trim())) {
				hqlcarsearch += "and cars.state1 = ?";
				parms.add(carInformation.getState1().trim());
			}
		}
		hqlcarsearch += "order by cars.engineNum desc ";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
				hqlcarsearch, parms.toArray());
		return "driving_carInformationlist";
	}

	/**
	 * 根据条件查询车辆信息
	 */
	public String toSearchChe() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", carInformation);
		return getCarInformationList();
	}

	/**
	 * 保存分车方法
	 */
	public String saveDrivingFenche() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		beans = new ArrayList<BaseBean>();
		if (str != null && !"".equals(str)) {
			String[] strs = str.substring(0, str.lastIndexOf(",")).split(",");
			String hql = " from DtDrivingPrincipalType where companyid=? and docstatus =? and drivingprincipalid=?";
			for (int i = 0; i < strs.length; i++) {
				DtDrivingPrincipalType dtDringPrialType = (DtDrivingPrincipalType) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] {
								account.getCompanyID(), docstatus.trim(),
								strs[i].trim() });
				dtDringPrialType
						.setCoachid(dtDrivingPrincipalType.getCoachid());
				dtDringPrialType.setCoachname(dtDrivingPrincipalType
						.getCoachname());
				dtDringPrialType.setCarNumber(dtDrivingPrincipalType
						.getCarNumber());
				if ("01".equals(docstatus.trim())) {
					dtDringPrialType.setStudentstatus("03");
				}
				/**
				 * 收集-操作人记录保存
				 */
				List<BaseBean> beans_part=saveOperationPeople(dtDringPrialType, null);
				beans.addAll(beans_part);
				beans.add(dtDringPrialType);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		}

		return "success";
	}

	/**
	 * 培训状态操作
	 */
	public String saveDrivingPeixun() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		beans = new ArrayList<BaseBean>();
		if (str != null && !"".equals(str)) {
			String[] strs = str.substring(0, str.lastIndexOf(",")).split(",");
			String hql = " from DtDrivingPrincipalType where companyid=? and  docstatus =? and drivingprincipalid=?";
			for (int i = 0; i < strs.length; i++) {
				DtDrivingPrincipalType dtDringPrialType = (DtDrivingPrincipalType) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] {
								account.getCompanyID(), docstatus.trim(),
								strs[i].trim() });
				if ("01".equals(docstatus)) { // 判断是否是 科一培训操作
					dtDringPrialType.setStudentstatus("08");
				} else if (!"01".equals(docstatus)
						&& "02".equals(studentstatus)) {// 判断是否是
					// 科二（桩考），科二（场地），科三（路考）收集模块
					// 中操作
					dtDringPrialType.setStudentstatus("03");
				} else if (!"01".equals(docstatus)
						&& "03".equals(studentstatus)) {// 判断是否是
					// 科二（桩考），科二（场地），科三（路考）培训模块
					// 中操作
					dtDringPrialType.setStudentstatus("05");// 设置为 "05" 因为
					// 科二（桩考），科二（场地），科三（路考）中
					// 无 报开学模块，直接进去约考模块
				}
				/**
				 * 收集-操作人记录保存
				 */
				List<BaseBean> beans_part=saveOperationPeople(dtDringPrialType, null);
				beans.addAll(beans_part);
				beans.add(dtDringPrialType);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		}

		return "success";
	}

	/**
	 * 报开学保存
	 */
	public String saveDrivingBaokaixue() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String organizationName = (String) session.get("organizationName");
		String groupCompanySn = (String) session.get("groupCompanySn");
		int sumStudent=baseBeanService.getConutByByHqlAndParams("select count(*) from DtDrivingPrincipal where  companyid=? ", new Object[]{account.getCompanyID()});
		beans = new ArrayList<BaseBean>();
		if (str != null && !"".equals(str)) {
			String[] strs = str.substring(0, str.lastIndexOf(",")).split(",");
			String hql = " from DtDrivingPrincipal where  companyid=? and drivingprincipalid = ?";
			String hql1 = " from DtDrivingPrincipalType where companyid=? and docstatus =? and drivingprincipalid=?";
			for (int i = 0; i < strs.length; i++) {
				DtDrivingPrincipal DivingPripal = (DtDrivingPrincipal) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] {
								account.getCompanyID(), strs[i].trim() });
				DivingPripal.setSchoolopendate(dtDrivingPrincipal
						.getSchoolopendate());
				String newString = String.format("%0"+5+"d", sumStudent);  
				DivingPripal.setStudentCode(newString);
				DivingPripal.setStudentPeriods(dtDrivingPrincipal.getStudentPeriods());
				DtDrivingPrincipalType dtDringPrialType = (DtDrivingPrincipalType) baseBeanService
						.getBeanByHqlAndParams(hql1, new Object[] {
								account.getCompanyID(), "01", strs[i].trim() });
				dtDringPrialType.setStudentstatus("05");
				/**
				 * 收集-操作人记录保存
				 */
				List<BaseBean> beans_part=saveOperationPeople(dtDringPrialType, null);
				/**
				 * 报开学后自动生成缴费记录---开始----
				 */
				if(dtDrivingPrincipal.getIfPay()){
				
					CostSheetBill costSheetBill=new CostSheetBill();
					costSheetBill.setCsbID(serverService.getServerID("costSheetBill"));
					
					
					costSheetBill.setCompanyID(account.getCompanyID());
					costSheetBill.setCompanyName(account.getCompanyName());
					costSheetBill.setOrganizationID(organizationID);
					
					costSheetBill.setBillsDate(new Date());
					costSheetBill.setJournalNum(serverService.getBillID(account.getCompanyID()));
					costSheetBill.setBillsType("项目预算招标申请单");
					
					/**
					 * 
					 * @param  项目类型  项目类型名称
					 */
					costSheetBill.setXmtype("044011");
					costSheetBill.setXmtypename("进度跟踪");
					/**
					 * 根据单据类别 codeName 查询单据类别 codeID
					 */
					/*CCode cCode=(CCode)baseBeanService.getBeanByHqlAndParams("from CCode cc where cc.codeValue=?",new Object[]{cashierBills.getBillsType()});
					if(cCode!=null){
						cashierBills.setPbillsTypeID(cCode.getCodePID());
					}*/
					
					/*Company company = companyserverService.getCompanyByCompanyID(account
							.getCompanyID());
					if (!company.getCompanyPID().startsWith("pcompany")) {
						company = companyserverService.getCompanyByCompanyID(company.getCompanyPID());
					}*/
					costSheetBill.setGroupCompanySn(groupCompanySn);
					
					costSheetBill.setCstaffID(DivingPripal.getStudentid());
					costSheetBill.setCstaffName(DivingPripal.getStudentname());
					costSheetBill.setReference(DivingPripal.getStudentphone());
					costSheetBill.setCstaffRelationship("学员");
					costSheetBill.setStaffIdentityCard(DivingPripal.getStudentcard());
					
					costSheetBill.setDepartmentID(organizationID);
					costSheetBill.setDepartmentName(organizationName);
					
					Staff  staff=(Staff) baseBeanService.getBeanByHqlAndParams(" from Staff where staffID=?", new Object[]{account.getStaffID()});
					
					costSheetBill.setStaffID(staff.getStaffID());
					costSheetBill.setStaffName(staff.getStaffName());
					costSheetBill.setStaffCode(staff.getStaffCode());
					
					costSheetBill.setBillStaffID(staff.getStaffID());
					costSheetBill.setBillStaffName(staff.getStaffName());
					
					costSheetBill.setBillStatus("00"); 
					
					CostSheetDetail  costSheetDetail=new CostSheetDetail();
					costSheetDetail.setCsdID(serverService.getServerID("costSheetDetail"));
					costSheetDetail.setEndDate(new Date());
					costSheetDetail.setCsbID(costSheetBill.getCsbID());
					
					costSheetDetail.setBalance(String.valueOf(dtDrivingPrincipal.getMoney()));//报开学提交  传递后的参数
					costSheetDetail.setCostType("现金");//费用类别
					costSheetDetail.setDepotType("出库");// 库房类型
					costSheetDetail.setDirection("贷");// 方向
					costSheetDetail.setForLoan("1");// 贷方金额
					costSheetDetail.setAmount("1");//余额
					costSheetDetail.setPeriodDate(new Date());// 有效日期
					costSheetDetail.setUnitPrice(String.valueOf(dtDrivingPrincipal.getMoney()));// 单价
					costSheetDetail.setPriceManage("出厂价");// 单价管理
					costSheetDetail.setQuantity("1");// 数量
					costSheetDetail.setStartDate(new Date());// 款源日期
					costSheetDetail.setSubjectsID("1");// 科目管理ID
					costSheetDetail.setSubjectsName("资产类");// 科目管理名称
					costSheetDetail.setWeight("1");// 重量
					beans.add(costSheetBill);
					beans.add(costSheetDetail);
				}	
				/**
				 * 报开学后自动生成缴费记录---结束
				 */
				beans.addAll(beans_part);
				beans.add(dtDringPrialType);
				beans.add(DivingPripal);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		}
		return "success";
	}

	/**
	 * 预约考试保存
	 */
	public String saveDrivingYuekao() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		beans = new ArrayList<BaseBean>();
		if (str != null && !"".equals(str)) {
			String[] strs = str.substring(0, str.lastIndexOf(",")).split(",");
			String[] docstatusStrs = docstatusStr.substring(0, docstatusStr.lastIndexOf(",")).split(",");
			String hql = " from DtDrivingPrincipalType where companyid=? and docstatus =? and drivingprincipalid=?";
			for (int i = 0; i < strs.length; i++) {
				DtDrivingPrincipalType dtDringPrialType = (DtDrivingPrincipalType) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] {
								account.getCompanyID(), docstatusStrs[i].trim(),
								strs[i].trim() });
				dtDringPrialType.setStudentstatus("06");
				dtDringPrialType
						.setTestsum(dtDringPrialType.getTestsum() == null ? "1"
								: String
										.valueOf(Integer
												.parseInt(dtDringPrialType
														.getTestsum()) + 1));// 约考次数+1
				dtDringPrialType.setTestresult(null);// 设置默认学员结果为 null 00 未考
				// 01 不合格
				// 02 合格
				// dtDringPrialType.setSubjectsstatus("00");// 默认设置 是否进入下一科目 00
				// 否 01 是

				DtDrivingTest drivingTest = new DtDrivingTest();// 新增考试信息
				drivingTest.setDrivingprincipalid(dtDringPrialType
						.getDrivingprincipalid());// 主表外键
				drivingTest.setPrincipaltypeid(dtDringPrialType
						.getPrincipaltypeid());
				drivingTest.setCompanyid(account.getCompanyID());
				drivingTest.setDrivingtestid(serverService
						.getServerID("DtDrivingTest"));
				drivingTest.setTestdate(dtDrivingTest.getTestdate());// 约考时间
				drivingTest.setExamtype(docstatusStrs[i].trim());// 考试类型 01 科一约考 02
				// 科二桩考 03 科二场地 04
				// 科三约考
				drivingTest.setAppointmenType(dtDrivingTest.getAppointmenType());
				drivingTest.setAppointmenPeopleID(dtDrivingTest.getAppointmenPeopleID());
				drivingTest.setAppointmenPeople(dtDrivingTest.getAppointmenPeople());
				DtDrivingAllInformation  dtDrivingAllInformation=null;
				if(dtDrivingTest.getIfPay()!=null&&!"".equals(dtDrivingTest.getIfPay())&&"01".equals(dtDrivingTest.getIfPay())){
					dtDrivingAllInformation=new DtDrivingAllInformation();
					dtDrivingAllInformation.setDrivingAllInformationID(serverService.getServerID("DtDrivingAllInfor"));
					dtDrivingAllInformation.setCompanyID(account.getCompanyID());
					dtDrivingAllInformation.setStaffID(dtDrivingPrincipal.getStudentid());
					dtDrivingAllInformation.setDataTitle("05");
					dtDrivingAllInformation.setChargeName(dtDrivingTest.getPayName());
					dtDrivingAllInformation.setChargeTime(dtDrivingTest.getTestdate());
					dtDrivingAllInformation.setChargeMoney(dtDrivingTest.getPayMoney());
					beans.add(dtDrivingAllInformation);
				}
				/**
				 * 收集-操作人记录保存
				 */
				List<BaseBean> beans_part=saveOperationPeople(dtDringPrialType, null);
				beans.addAll(beans_part);
				beans.add(dtDringPrialType);
				beans.add(drivingTest);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		}
		return "success";
	}
	
	/**
	 * 预约测试保存
	 * @return success
	 * @data  无
	 * @time 2014-11-18
	 */
	public String saveDrivingYuekaoOfTest() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		beans = new ArrayList<BaseBean>();
		if (str != null && !"".equals(str)) {
			String[] strs = str.substring(0, str.lastIndexOf(",")).split(",");
			String[] docstatusStrs = docstatusStr.substring(0, docstatusStr.lastIndexOf(",")).split(",");
			String hql = " from DtDrivingPrincipalType where companyid=? and docstatus =? and drivingprincipalid=?";
			for (int i = 0; i < strs.length; i++) {
				DtDrivingPrincipalType dtDringPrialType = (DtDrivingPrincipalType) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] {
								account.getCompanyID(), docstatusStrs[i].trim(),
								strs[i].trim() });
				dtDringPrialType.setToTestOther("01");// 01已预约测试 null 为预约测试
				dtDringPrialType.setTestOtherResult(null);//初始状态  未统计

				DtDrivingTestOther drivingTestOther = new DtDrivingTestOther();// 新增考试信息
				drivingTestOther.setDrivingprincipalid(dtDringPrialType
						.getDrivingprincipalid());// 主表外键
				drivingTestOther.setPrincipaltypeid(dtDringPrialType
						.getPrincipaltypeid());
				drivingTestOther.setCompanyid(account.getCompanyID());
				drivingTestOther.setId(serverService
						.getServerID("DtDrivingTestOther"));
				drivingTestOther.setTestdate(dtDrivingTest.getTestdate());// 约考时间
				drivingTestOther.setExamtype(docstatusStrs[i].trim());// 考试类型 01 科一约考 02 科二桩考 03 科二场地 04 科三约考
				drivingTestOther.setAppointmenType(dtDrivingTest.getAppointmenType());
				drivingTestOther.setAppointmenPeopleID(dtDrivingTest.getAppointmenPeopleID());
				drivingTestOther.setAppointmenPeople(dtDrivingTest.getAppointmenPeople());
				DtDrivingAllInformation  dtDrivingAllInformation=null;
				if(dtDrivingTest.getIfPay()!=null&&!"".equals(dtDrivingTest.getIfPay())&&"01".equals(dtDrivingTest.getIfPay())){
					dtDrivingAllInformation=new DtDrivingAllInformation();
					dtDrivingAllInformation.setDrivingAllInformationID(serverService.getServerID("DtDrivingAllInfor"));
					dtDrivingAllInformation.setCompanyID(account.getCompanyID());
					dtDrivingAllInformation.setStaffID(dtDrivingPrincipal.getStudentid());
					dtDrivingAllInformation.setDataTitle("05");
					dtDrivingAllInformation.setChargeName(dtDrivingTest.getPayName());
					dtDrivingAllInformation.setChargeTime(dtDrivingTest.getTestdate());
					dtDrivingAllInformation.setChargeMoney(dtDrivingTest.getPayMoney());
					beans.add(dtDrivingAllInformation);
				}
				/**
				 * 收集-操作人记录保存
				 */
				List<BaseBean> beans_part=saveOperationPeople(dtDringPrialType, null);
				beans.addAll(beans_part);
				beans.add(dtDringPrialType);
				beans.add(drivingTestOther);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		}
		return "success";
	}
	
	/**
	 * 查询学员考试信息
	 * 
	 * @return
	 */
	/*
	 * public String getDtDrivingTestList() {
	 * 
	 * String hql = " from DtDrivingTest where drivingprincipalid=? and
	 * examtype=?"; Object[] params = {
	 * dtDrivingPrincipal.getDrivingprincipalid(), docstatus.trim() }; pageForm =
	 * baseBeanService.getPageForm((null != pageForm ? pageForm .getPageNumber() :
	 * 1), (pageNumber == 0 ? 5 : pageNumber), hql, params); return
	 * "driving_list_kaoshiinfors"; }
	 */

	/**
	  * 统计考试结果
	  * @return jsp 
	  * @data  List
	  * @time 2014-11-18 之前
	 */
	public String updateDrivingTest() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		
		beans = new ArrayList<BaseBean>();
		String hql = " from  DtDrivingTest where companyid=? and drivingtestid =?";
		for (DtDrivingTest dtDrivingTest1 : dtDrivingTestMap.values()) {
			DtDrivingTest dtDrivingTest2 = (DtDrivingTest) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] {
							account.getCompanyID(), dtDrivingTest1.getDrivingtestid() });
			
			dtDrivingTest2.setExamresult(dtDrivingTest.getExamresult());
			dtDrivingTest2.setExamScore(dtDrivingTest1.getExamScore());
			dtDrivingTest2.setFirstExaminer(dtDrivingTest1.getFirstExaminer());
			dtDrivingTest2.setFirstExaminerID(dtDrivingTest1.getFirstExaminerID());
			dtDrivingTest2.setFirstExamPoints(dtDrivingTest1.getFirstExamPoints());
			
			dtDrivingTest2.setFirstExamresult(dtDrivingTest1.getFirstExamresult());
			dtDrivingTest2.setFirstExamTime(dtDrivingTest1.getFirstExamTime());
			
			dtDrivingTest2.setSecondExaminer(dtDrivingTest1.getSecondExaminer());
			dtDrivingTest2.setSecondExaminerID(dtDrivingTest1.getSecondExaminerID());
			dtDrivingTest2.setSecondExamPoints(dtDrivingTest1.getSecondExamPoints());
			
			dtDrivingTest2.setSecondExamresult(dtDrivingTest1.getSecondExamresult());
			dtDrivingTest2.setSecondExamTime(dtDrivingTest1.getSecondExamTime());
			dtDrivingTest2.setSecondExamScore(dtDrivingTest1.getSecondExamScore());
			

			String hql1 = " from DtDrivingPrincipalType where companyid=? and docstatus =? and drivingprincipalid=?";
			DtDrivingPrincipalType dtDringPrialType1 = (DtDrivingPrincipalType) baseBeanService
					.getBeanByHqlAndParams(hql1, new Object[] {
							account.getCompanyID(), dtDrivingTest2.getExamtype(),
							dtDrivingTest2.getDrivingprincipalid() });
			if ("00".equals(dtDrivingTest2.getExamresult())) {// 合格
				
				dtDringPrialType1.setTestresult("02");// 学员考试结果 状态 合格
				dtDringPrialType1.setStudentstatus("07");// 主表 总流程学员状态
				
				if (!"04".equals(dtDrivingTest2.getExamtype())) {// 提交到下一科目
					DtDrivingPrincipalType dtDringPrialType = new DtDrivingPrincipalType(); // 新增下级科目关系表数据
					dtDringPrialType.setPrincipaltypeid(serverService
							.getServerID("principaltype"));
					dtDringPrialType
							.setDrivingprincipalid(dtDringPrialType1
									.getDrivingprincipalid());
					dtDringPrialType.setCompanyid(account.getCompanyID());
					dtDringPrialType.setCoachid(dtDringPrialType1
							.getCoachid());
					dtDringPrialType.setCoachname(dtDringPrialType1
							.getCoachname());
					dtDringPrialType.setCarNumber(dtDringPrialType1
							.getCarNumber());
					dtDringPrialType.setTestsum("0");// 约考次数 默认为0
					dtDringPrialType.setTestresult("00");// 考试结果 默认未考
					dtDringPrialType.setDocstatus("01".equals(dtDrivingTest2.getExamtype()) ? "02"
							: "02".equals(dtDrivingTest2.getExamtype()) ? "03" : "04");
					dtDringPrialType.setStudentstatus("02");
					dtDringPrialType.setInputTime(new Date());
					// dtDringPrialType.setSubjectsstatus("00");// 默认设置
					// 是否进入下一科目 00
					// 否 01 是
					
					/**
					 * 收集-操作人记录保存
					 */
					List<BaseBean> beans_part1=saveOperationPeople(dtDringPrialType, null);
					beans.addAll(beans_part1);
					beans.add(dtDringPrialType);
				}

			} else if ("01".equals(dtDrivingTest2.getExamresult())) {// 不合格
				dtDringPrialType1.setTestresult("01");// 不合格
				dtDringPrialType1.setStudentstatus("06");// 主表 总流程学员状态
				
			} else {// 02 缺考 03 误报
				dtDringPrialType1.setTestresult("00");// 未考
				dtDringPrialType1.setStudentstatus("06");// 主表 总流程学员状态
			}
			/**
			 * 收集-操作人记录保存
			 */
			List<BaseBean> beans_part=saveOperationPeople(dtDringPrialType1, null);
			beans.addAll(beans_part);
			
			beans.add(dtDrivingTest2);
			beans.add(dtDringPrialType1);
			
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,null);
		return "success";
	}
	
	/**
	 * 统计测试结果
	 * @return jsp 
	 * @data  List
	 * @time 2014-11-18
	 */
	public String updateDrivingTestOfTest() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		beans = new ArrayList<BaseBean>();
		String hql = " from  DtDrivingTestOther where companyid=? and id =?";
		for (DtDrivingTestOther dtDrivingTestOther1 : dtDrivingTestOtherMap.values()) {
			DtDrivingTestOther dtDrivingTestOther = (DtDrivingTestOther) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] {
							account.getCompanyID(), dtDrivingTestOther1.getId() });
			dtDrivingTestOther.setExamresult(dtDrivingTest.getExamresult());
			dtDrivingTestOther.setExamScore(dtDrivingTestOther1.getExamScore());
			dtDrivingTestOther.setFirstExaminer(dtDrivingTestOther1.getFirstExaminer());
			dtDrivingTestOther.setFirstExaminerID(dtDrivingTestOther1.getFirstExaminerID());
			dtDrivingTestOther.setFirstExamPoints(dtDrivingTestOther1.getFirstExamPoints());
			
			dtDrivingTestOther.setFirstExamresult(dtDrivingTestOther1.getFirstExamresult());
			dtDrivingTestOther.setFirstExamTime(dtDrivingTestOther1.getFirstExamTime());
			
			dtDrivingTestOther.setSecondExaminer(dtDrivingTestOther1.getSecondExaminer());
			dtDrivingTestOther.setSecondExaminerID(dtDrivingTestOther1.getSecondExaminerID());
			dtDrivingTestOther.setSecondExamPoints(dtDrivingTestOther1.getSecondExamPoints());
			
			dtDrivingTestOther.setSecondExamresult(dtDrivingTestOther1.getSecondExamresult());
			dtDrivingTestOther.setSecondExamTime(dtDrivingTestOther1.getSecondExamTime());
			dtDrivingTestOther.setSecondExamScore(dtDrivingTestOther1.getSecondExamScore());
			
			String hql1 = " from DtDrivingPrincipalType where companyid=? and docstatus =? and drivingprincipalid=?";
			DtDrivingPrincipalType dtDringPrialType1 = (DtDrivingPrincipalType) baseBeanService
					.getBeanByHqlAndParams(hql1, new Object[] {
							account.getCompanyID(), dtDrivingTestOther.getExamtype(),
							dtDrivingTestOther.getDrivingprincipalid() });
			if ("00".equals(dtDrivingTestOther.getExamresult())) {// 合格
				dtDringPrialType1.setTestOtherResult("02");
			} else if ("01".equals(dtDrivingTestOther.getExamresult())) {// 不合格
				dtDringPrialType1.setTestOtherResult("01");
			} else {// 02 缺考 03 误报
				dtDringPrialType1.setTestOtherResult("00");
			}
			/**
			 * 收集-操作人记录保存
			 */
			List<BaseBean> beans_part=saveOperationPeople(dtDringPrialType1, null);
			beans.addAll(beans_part);
			
			beans.add(dtDrivingTestOther);
			beans.add(dtDringPrialType1);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
				null);
		return "success";
	}

	/**
	 * 测试查询
	 * 
	 * @return jsp 
	 * @data  List
	 * @time 2014-11-18
	 */
	public String toSearchStatisticsOfTest() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch1", dtDrivingPrincipal);
		session.put("tablesearch2", dtDrivingPrincipalType);
		session.put("tablesearch3", dtDrivingTest);
		return getStatisticsOfTestList();
	}
	
	/**
	 * 
	 * 测试列表
	 * 
	 * @return jsp 
	 * @data  List
	 * @time 2014-11-18
	 */
	public String getStatisticsOfTestList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> array = new ArrayList<Object>();
		String sql = "select dp.registrationdate,dp.registrationcarname,dp.studentname,dp.studentsex,dp.studentcard,dp.studentphone,dpt.coachname,dt.testdate,dt.examScore,"
				+ " case  when dt.examresult='00' then '合格'  when dt.examresult='01' then '不合格' "
				+ " when dt.examresult='02' then '缺考' when dt.examresult='03' then '误报' else  '未统计' end ,dt.id,dp.studentid "
				+" ,case when dt.examtype='01' then '科一' when dt.examtype='02' then '科二'when dt.examtype='03' then '科三'when dt.examtype='04' then '科四' else '' end,"
				+" dt.appointmenType,dt.appointmenPeople,dt.firstExamTime,dt.firstExamPoints,dt.firstExaminer,"
				+" dt.secondExamTime,dt.secondExamPoints,dt.secondExamScore,dt.secondExamresult,dt.secondExaminer,"
				+" dt.firstExamresult,dt.firstExaminerID,dt.secondExaminerID "
				+ " from DT_DRIVING_TEST_OTHER dt"
				+ " left join dt_driving_principal_type dpt on dpt.principaltypeid =dt.principaltypeid"
				+ " left join dt_driving_principal dp on dp.drivingprincipalid=dt.drivingprincipalid"
				+ " left join dtccode dc on dc.codeid=dp.registrationcarid where 1=1 ";
		if (excel != null && "excel".equals(excel)) {
			sql = "select dp.registrationdate,"
					+" case when dt.examtype='01' then '科一' when dt.examtype='02' then '科二'when dt.examtype='03' then '科三'when dt.examtype='04' then '科四' else '' end,"
					+"dp.registrationcarname,dp.studentname,dp.studentsex,dp.studentcard,dp.studentphone,dpt.coachname,dt.testdate,"
					+" dt.appointmenType,dt.appointmenPeople," 
					+" case  when dt.examresult='00' then '合格'  when dt.examresult='01' then '不合格' "
					+ " when dt.examresult='02' then '缺考' when dt.examresult='03' then '误报' else  '未统计' end,  "
					+ "dt.firstExamTime,dt.firstExamPoints,dt.examScore,dt.firstExamresult,dt.firstExaminer,"
					+" dt.secondExamTime,dt.secondExamPoints,dt.secondExamScore,dt.secondExamresult,dt.secondExaminer"
					+ " from DT_DRIVING_TEST_OTHER dt"
					+ " left join dt_driving_principal_type dpt on dpt.principaltypeid =dt.principaltypeid"
					+ " left join dt_driving_principal dp on dp.drivingprincipalid=dt.drivingprincipalid"
					+ " left join dtccode dc on dc.codeid=dp.registrationcarid where 1=1 ";
		}
		sql += " and dt.companyid=? ";
		array.add(account.getCompanyID());
		if(null!=docstatus&&!"".equals(docstatus.trim())){
			sql += " and dt.examtype=? ";
			array.add(docstatus.trim());
		}
		if (search != null && "search".equals(search)) {
			dtDrivingPrincipal = (DtDrivingPrincipal) session
					.get("tablesearch1");
			dtDrivingPrincipalType = (DtDrivingPrincipalType) session
					.get("tablesearch2");
			dtDrivingTest = (DtDrivingTest) session.get("tablesearch3");
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				sql += " and dt.testdate between ? and ? ";
				array.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
				array.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
			}
			if (dtDrivingPrincipal != null
					&& dtDrivingPrincipal.getStudentname() != null
					&& !"".equals(dtDrivingPrincipal.getStudentname())) {
				sql += " and dp.studentname like '%"
						+ dtDrivingPrincipal.getStudentname().trim() + "%'";
			}
			if (dtDrivingPrincipalType != null
					&& dtDrivingPrincipalType.getCoachname() != null
					&& !"".equals(dtDrivingPrincipalType.getCoachname())) {
				sql += " and dpt.coachname like '%"
						+ dtDrivingPrincipalType.getCoachname().trim() + "%'";
			}
			if (dtDrivingTest != null && dtDrivingTest.getExamtype() != null
					&& !"".equals(dtDrivingTest.getExamtype())) {
				sql += " and dt.examtype ='"
						+ dtDrivingTest.getExamtype().trim() + "'";
			}
			if (dtDrivingTest != null && dtDrivingTest.getExamresult() != null
					&& !"".equals(dtDrivingTest.getExamresult())) {
				if(!"全部".equals(dtDrivingTest.getExamresult())){
				sql += " and dt.examresult ='"
						+ dtDrivingTest.getExamresult().trim() + "'";
				}
			} else {
				sql += " and dt.examresult is null ";
			}
		} 
		/**
		 * 根据学员ID查询学员--新版学员报名调用extensionStaffCoach
		 */
		if(extensionStaffCoach!=null&&"extensionStaffStudent".equals(extensionStaffCoach)){
			if(dtDrivingPrincipal!=null&&dtDrivingPrincipal.getStudentid()!=null&&!"".equals(dtDrivingPrincipal.getStudentid())){
				sql += " and dp.studentid=? ";
				array.add(dtDrivingPrincipal.getStudentid());
			}
		}
		sql += " order by  dt.testdate desc ";

		if (excel != null && "excel".equals(excel)) {
			@SuppressWarnings("unchecked")
			List<BaseBean> beans = baseBeanService.getListBeanBySqlAndParams(
					sql, array.toArray());
			excelStream = excelService.showExcel(DtDrivingPrincipal
					.columnHeadings1(), beans);
			return "showexcel";
		} else {
			pageForm = baseBeanService.getPageFormBySQL(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 10 : pageNumber), sql,
					" select count(*) " + sql.substring(sql.indexOf("from")),
					array.toArray());
			return "driving_list_tongji_test";
		}
	}
	
	/**
	 * 考试查询
	 * @return jsp
	 * @data List
	 * @time 2014-11-18
	 */
	public String toSearchStatistics() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch1", dtDrivingPrincipal);
		session.put("tablesearch2", dtDrivingPrincipalType);
		session.put("tablesearch3", dtDrivingTest);
		return getStatisticsList();
	}
	
	/**
	 * 
	 * 考试列表
	 * @return jsp
	 * @data List
	 * @time 2014-11-18
	 */
	public String getStatisticsList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> array = new ArrayList<Object>();
		String sql = "select dp.registrationdate,dp.registrationcarname,dp.studentname,dp.studentsex,dp.studentcard,dp.studentphone,dpt.coachname,dt.testdate,dt.examScore,"
				+ " case  when dt.examresult='00' then '合格'  when dt.examresult='01' then '不合格' "
				+ " when dt.examresult='02' then '缺考' when dt.examresult='03' then '误报' else  '未统计' end ,dt.drivingtestid,dp.studentid "
				+" ,case when dt.examtype='01' then '科一' when dt.examtype='02' then '科二'when dt.examtype='03' then '科三'when dt.examtype='04' then '科四' else '' end,"
				+" dt.appointmenType,dt.appointmenPeople,dt.firstExamTime,dt.firstExamPoints,dt.firstExaminer,"
				+" dt.secondExamTime,dt.secondExamPoints,dt.secondExamScore,dt.secondExamresult,dt.secondExaminer,"
				+" dt.firstExamresult,dt.firstExaminerID,dt.secondExaminerID "
				+ " from dt_driving_test dt"
				+ " left join dt_driving_principal_type dpt on dpt.principaltypeid =dt.principaltypeid"
				+ " left join dt_driving_principal dp on dp.drivingprincipalid=dt.drivingprincipalid"
				+ " left join dtccode dc on dc.codeid=dp.registrationcarid where 1=1 ";
		if (excel != null && "excel".equals(excel)) {
			sql = "select dp.registrationdate,"
					+" case when dt.examtype='01' then '科一' when dt.examtype='02' then '科二'when dt.examtype='03' then '科三'when dt.examtype='04' then '科四' else '' end,"
					+"dp.registrationcarname,dp.studentname,dp.studentsex,dp.studentcard,dp.studentphone,dpt.coachname,dt.testdate,"
					+" dt.appointmenType,dt.appointmenPeople," 
					+" case  when dt.examresult='00' then '合格'  when dt.examresult='01' then '不合格' "
					+ " when dt.examresult='02' then '缺考' when dt.examresult='03' then '误报' else  '未统计' end,  "
					+ "dt.firstExamTime,dt.firstExamPoints,dt.examScore,dt.firstExamresult,dt.firstExaminer,"
					+" dt.secondExamTime,dt.secondExamPoints,dt.secondExamScore,dt.secondExamresult,dt.secondExaminer"
					+ " from dt_driving_test dt"
					+ " left join dt_driving_principal_type dpt on dpt.principaltypeid =dt.principaltypeid"
					+ " left join dt_driving_principal dp on dp.drivingprincipalid=dt.drivingprincipalid"
					+ " left join dtccode dc on dc.codeid=dp.registrationcarid where 1=1 ";
		}
		sql += " and dt.companyid=? ";
		array.add(account.getCompanyID());
		if(null!=docstatus&&!"".equals(docstatus.trim())){
			sql += " and dt.examtype=? ";
			array.add(docstatus.trim());
		}
		if (search != null && "search".equals(search)) {
			dtDrivingPrincipal = (DtDrivingPrincipal) session
					.get("tablesearch1");
			dtDrivingPrincipalType = (DtDrivingPrincipalType) session
					.get("tablesearch2");
			dtDrivingTest = (DtDrivingTest) session.get("tablesearch3");
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				sql += " and dt.testdate between ? and ? ";
				array.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
				array.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
			}
			if (dtDrivingPrincipal != null
					&& dtDrivingPrincipal.getStudentname() != null
					&& !"".equals(dtDrivingPrincipal.getStudentname())) {
				sql += " and dp.studentname like '%"
						+ dtDrivingPrincipal.getStudentname().trim() + "%'";
			}
			if (dtDrivingPrincipalType != null
					&& dtDrivingPrincipalType.getCoachname() != null
					&& !"".equals(dtDrivingPrincipalType.getCoachname())) {
				sql += " and dpt.coachname like '%"
						+ dtDrivingPrincipalType.getCoachname().trim() + "%'";
			}
			if (dtDrivingTest != null && dtDrivingTest.getExamtype() != null
					&& !"".equals(dtDrivingTest.getExamtype())) {
				sql += " and dt.examtype ='"
						+ dtDrivingTest.getExamtype().trim() + "'";
			}
			if (dtDrivingTest != null && dtDrivingTest.getExamresult() != null
					&& !"".equals(dtDrivingTest.getExamresult())) {
				if(!"全部".equals(dtDrivingTest.getExamresult())){
				sql += " and dt.examresult ='"
						+ dtDrivingTest.getExamresult().trim() + "'";
				}
			} else {
				sql += " and dt.examresult is null ";
			}
		} 
		/**
		 * 根据学员ID查询学员--新版学员报名调用extensionStaffCoach
		 */
		if(extensionStaffCoach!=null&&"extensionStaffStudent".equals(extensionStaffCoach)){
			if(dtDrivingPrincipal!=null&&dtDrivingPrincipal.getStudentid()!=null&&!"".equals(dtDrivingPrincipal.getStudentid())){
				sql += " and dp.studentid=? ";
				array.add(dtDrivingPrincipal.getStudentid());
			}
		}
		sql += " order by  dt.testdate desc ";

		if (excel != null && "excel".equals(excel)) {
			@SuppressWarnings("unchecked")
			List<BaseBean> beans = baseBeanService.getListBeanBySqlAndParams(
					sql, array.toArray());
			excelStream = excelService.showExcel(DtDrivingPrincipal
					.columnHeadings1(), beans);
			return "showexcel";
		} else {
			pageForm = baseBeanService.getPageFormBySQL(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 10 : pageNumber), sql,
					" select count(*) " + sql.substring(sql.indexOf("from")),
					array.toArray());
			return "driving_list_tongji";
		}
	}
	
	/**
	 * 
	 * @time 2014-10-13
	 * @author zc
	 * @describe 按(时间(必选),教练(必选),科目(必选))统计学员考试信息;打印预览.-(个人)教练业绩统计明细表
	 * @return list
	 */
	public String getListOfStudent() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> array = new ArrayList<Object>();
		String sql = "select dt.testdate,dp.studentname,dp.registrationcarname,dp.studentcard,dpt.coachname,dcc.carNum,dt.examScore,"
				+ " case  when dt.examresult='00' then '合格'   else '' end,"
				+ " case  when dt.examresult='01' then '不合格' else '' end,"
				+ " case  when dt.examresult='02' then '缺考'   else '' end,"
				+ " case  when dt.examresult='03' then '误报'   else '' end,"
				+ " ddaa.Chargemoney "
				+ " from dt_driving_test dt"
				+ " left join dt_driving_principal_type dpt on dpt.principaltypeid =dt.principaltypeid"
				+ " left join dt_driving_principal dp on dp.drivingprincipalid=dt.drivingprincipalid"
				+ " left join dt_Car_CarInformation dcc on dcc.carID=dpt.coachid "
				+ " left join dtccode dc on dc.codeid=dp.registrationcarid "
				+ " left join (select dda.companyid,dda.staffid ,sum(dda.Chargemoney) Chargemoney from DT_DRIVING_AllINFORMATION  dda where dda.datatitle='05' group by dda.companyid,dda.staffid) ddaa on ddaa.staffid=dp.studentid and ddaa.companyid=dp.companyid"
				+ " where 1=1 ";
		sql += " and dt.companyid=? ";
		array.add(account.getCompanyID());
		if(null!=docstatus&&!"".equals(docstatus.trim())){
			sql += " and dt.examtype=? ";
			array.add(docstatus.trim());
		}
		if (sdate != null && edate != null && !("").equals(sdate)
				&& !("").equals(edate)) {
			sql += " and dt.testdate between ? and ? ";
			array.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
			array.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		}
		if (dtDrivingPrincipalType != null
				&& dtDrivingPrincipalType.getCoachname() != null
				&& !"".equals(dtDrivingPrincipalType.getCoachname())) {
			try {
				sql += " and dpt.coachname like ? ";
				array.add("%"+dtDrivingPrincipalType.getCoachname().trim().split("---")[0]+"%");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sql += " order by  dt.testdate desc ";
		List<BaseBean> listOfStudent=baseBeanService.getListBeanBySqlAndParams(sql, array.toArray());
		request.put("listOfStudent", listOfStudent);
		return "getListOfStudent";
	}
	/**
	 * 
	 * @time 2014-10-14
	 * @author zc
	 * @describe 按(公司ID(必选),月份(必选),科目(必选))分组统计考试合格率信息;打印预览.-(年度)各科目合格率统计表
	 * @return list
	 */
	@SuppressWarnings("unused")
	public String getListQualificationOfSubjects() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> array = new ArrayList<Object>();
		String sql =" select to_char(dt.testdate,'yyyy-MM') testdate,decode(dt.examtype,'01','科目一','02','科目二','03','科目三','科目四') examtype," +
					" count(dt.drivingtestid) zongshu," +
					" sum(case when dt.examresult='00' then '1' else '0' end) hege," +
					" sum(case when dt.examresult='01' then '1' else '0' end) nohege," +
					" sum(case when dt.examresult='02' then '1' else '0' end) quekao," +
					" sum(case when dt.examresult='03' then '1' else '0' end) wubao," +
					" ROUND(sum(case when dt.examresult='00' then '1' else '0' end)/count(dt.drivingtestid), 2) * 100 || '%' baifenbi" +
					" from dt_driving_test dt" +
					" left join dt_driving_principal_type dpt on dt.principaltypeid=dpt.principaltypeid";
		sql += " where  1=1 ";
		sql += " and dt.companyid=? ";
		array.add(account.getCompanyID());
		if (sdate != null && edate != null && !("").equals(sdate)
				&& !("").equals(edate)) {
			sql += " and dt.testdate between ? and ? ";
			array.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
			array.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		}
		sql += " and dt.examresult is not null ";
		sql += " group by to_char(dt.testdate,'yyyy-MM'),dt.examtype ";
		sql += " order by to_char(dt.testdate,'yyyy-MM') asc, dt.examtype asc";
		List<BaseBean> listQualificationOfSubjects=baseBeanService.getListBeanBySqlAndParams(sql, array.toArray());
		request.put("listQualificationOfSubjects", listQualificationOfSubjects);
		return "getListQualificationOfSubjects";
	}
	/**
	 * 
	 * @time 2014-10-15
	 * @author zc
	 * @describe 按(公司ID(必选),月份(必选),科目(必选)，教练(必选))时间行转列显示方式统计;打印预览.-(月度)教练培训质量排行榜
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public String getListOfCoachsOfqualified() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> array1 = new ArrayList<Object>();
		
		String sql1 = " select dt.testdate from dt_driving_test dt";
			   sql1 +=" where 1=1"	;
		sql1 += " and dt.companyid=? ";
		array1.add(account.getCompanyID());
		if(null!=docstatus&&!"".equals(docstatus.trim())){
			sql1 += " and dt.examtype=? ";
			array1.add(docstatus.trim());
		}
		if (sdate != null && edate != null && !("").equals(sdate)
				&& !("").equals(edate)) {
			sql1 += " and dt.testdate between ? and ? ";
			array1.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
			array1.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		}
		sql1 += " group by dt.testdate order by dt.testdate asc ";
		List<BaseBean> testDateList=baseBeanService.getListBeanBySqlAndParams(sql1, array1.toArray());
		request.put("testDateList", testDateList);
		List<Object> array = new ArrayList<Object>();
		String sql="select dpt.coachid,dpt.coachname coachname,";
		for (int i = 0; i < testDateList.size(); i++) {
			sql+=" sum(case when dt.testdate=to_date('"+testDateList.get(i)+"','yyyy-MM-dd') then 1 else 0 end) totle"+i+",";
			sql+=" sum(case when dt.testdate=to_date('"+testDateList.get(i)+"','yyyy-MM-dd') and dt.examresult='00' then 1 else 0 end) qualified"+i+",";
			sql+=" sum(case when dt.testdate=to_date('"+testDateList.get(i)+"','yyyy-MM-dd') and dpt.testsum>1 then 1 else 0 end) anunqualified"+i+",";
			sql+="  '*' amount"+i+",";
		}
		sql	+=" count(1) totle,";
		sql	+=" sum(case when  dt.examresult='00'  then 1 else 0 end)  qualified,";
		sql	+=" sum(case when  dpt.testsum>1  then 1 else 0 end) anunqualified, ";
		sql+="   '*' amount ,";
		sql +=" ROUND(sum(case when  dt.examresult='00'  then 1 else 0 end)/count(1), 2) * 100 || '%' baifenbi ";
		sql	+=" from dt_driving_test dt";
		sql	+=" left join dt_driving_principal_type dpt on dt.principaltypeid=dpt.principaltypeid";
		sql += " where  1=1 ";
		sql += " and dt.companyid=? ";
		array.add(account.getCompanyID());
		if(null!=docstatus&&!"".equals(docstatus.trim())){
			sql += " and dt.examtype=? ";
			array.add(docstatus.trim());
		}
		if (sdate != null && edate != null && !("").equals(sdate)
				&& !("").equals(edate)) {
			sql += " and dt.testdate between ? and ? ";
			array.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
			array.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		}
		sql += " group by dpt.coachid,dpt.coachname ";
		sql += " order by count(1) desc,ROUND(sum(case when  dt.examresult='00'  then 1 else 0 end)/count(1), 2) * 100 desc ";
		List<BaseBean> listOfListOfCoachsOfqualified=baseBeanService.getListBeanBySqlAndParams(sql, array.toArray());
		request.put("listOfListOfCoachsOfqualified", listOfListOfCoachsOfqualified);
		return "getListOfCoachsOfqualified";
	}
	
	/**
	 * 
	 * @time 2014-10-15
	 * @author zc
	 * @describe 按(公司ID(必选),月份(必选),科目(必选)，教练(必选))时间行转列显示方式统计;打印预览.-(年度)教练培训质量排行榜
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public String getListOfCoachsOfqualifiedOfAnnual() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> array1 = new ArrayList<Object>();
		
		String sql1 = " select to_char(dt.testdate,'yyyy-MM') from dt_driving_test dt";
			   sql1 +=" where 1=1"	;
		sql1 += " and dt.companyid=? ";
		array1.add(account.getCompanyID());
		if(null!=docstatus&&!"".equals(docstatus.trim())){
			sql1 += " and dt.examtype=? ";
			array1.add(docstatus.trim());
		}
		if (sdate != null && edate != null && !("").equals(sdate)
				&& !("").equals(edate)) {
			sql1 += " and dt.testdate between ? and ? ";
			array1.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
			array1.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		}
		sql1 += " group by to_char(dt.testdate,'yyyy-MM') order by to_char(dt.testdate,'yyyy-MM') asc  ";
		List<BaseBean> testMonthlyList=baseBeanService.getListBeanBySqlAndParams(sql1, array1.toArray());
		request.put("testMonthlyList", testMonthlyList);
		List<Object> array = new ArrayList<Object>();
		String sql="select dpt.coachid,dpt.coachname coachname,";
		for (int i = 0; i < testMonthlyList.size(); i++) {
			sql+=" sum(case when to_char(dt.testdate,'yyyy-MM')='"+testMonthlyList.get(i)+"' and dt.examresult='00' then 1 else 0 end) qualified"+i+",";
			sql+=" sum(case when to_char(dt.testdate,'yyyy-MM')='"+testMonthlyList.get(i)+"' then 1 else 0 end) totle"+i+",";
			sql+=" ROUND(sum(case when to_char(dt.testdate,'yyyy-MM')='"+testMonthlyList.get(i)+"' and dt.examresult='00' then 1 else 0 end)/decode(sum(case when to_char(dt.testdate,'yyyy-MM')='"+testMonthlyList.get(i)+"' then 1 else 0 end),0,1,sum(case when to_char(dt.testdate,'yyyy-MM')='"+testMonthlyList.get(i)+"' then 1 else 0 end)), 2) * 100 || '%' baifenbi"+i+",";
		}
		sql	+=" sum(case when  dt.examresult='00'  then 1 else 0 end)  qualified,";
		sql	+=" count(1) totle,";
		sql +=" ROUND(sum(case when  dt.examresult='00'  then 1 else 0 end)/decode(count(1),0,1,count(1)), 2) * 100 || '%' baifenbi   ";
		sql	+=" from dt_driving_test dt";
		sql	+=" left join dt_driving_principal_type dpt on dt.principaltypeid=dpt.principaltypeid";
		sql += " where  1=1 ";
		sql += " and dt.companyid=? ";
		array.add(account.getCompanyID());
		if(null!=docstatus&&!"".equals(docstatus.trim())){
			sql += " and dt.examtype=? ";
			array.add(docstatus.trim());
		}
		if (sdate != null && edate != null && !("").equals(sdate)
				&& !("").equals(edate)) {
			sql += " and dt.testdate between ? and ? ";
			array.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
			array.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		}
		sql += " group by dpt.coachid,dpt.coachname ";
		sql += " order by count(1) desc ,ROUND(sum(case when  dt.examresult='00'  then 1 else 0 end)/decode(count(1),0,1,count(1)), 2) * 100 desc  ";
		List<BaseBean> listOfListOfCoachsOfqualifiedOfAnnual=baseBeanService.getListBeanBySqlAndParams(sql, array.toArray());
		request.put("listOfListOfCoachsOfqualifiedOfAnnual", listOfListOfCoachsOfqualifiedOfAnnual);
		return "getListOfCoachsOfqualifiedOfAnnual";
	}

	
	
	
	/**
	 * 
	 * 测试合格率分析列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getAnalysisListOfTest() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> array = new ArrayList<Object>();
		String sql = "select  dpt.coachid,dpt.coachname coachname,";
		if (search != null && "search".equals(search)) {
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				sql += "to_date('" + sdate + "','yyyy-MM-dd') startdate,to_date('"
						+ edate + "','yyyy-MM-dd') enddate,";
			}else{
				try {
					sql += "to_date('" + DateUtil.getDateOfMonthBegin(DateUtil.getCurrentDate(), "yyyy-MM-dd") + "','yyyy-MM-dd') startdate,to_date('"
							+ DateUtil.getDateOfMonthEnd(DateUtil.getCurrentDate(), "yyyy-MM-dd") + "','yyyy-MM-dd') enddate,";
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		sql += " count(dt.id) zongshu,"
				+ " sum(case when dt.examresult='00' then '1' else '0' end) hege,"
				+ " sum(case when dt.examresult='01' then '1' else '0' end) nohege,"
				+ " sum(case when dt.examresult='02' then '1' else '0' end) quekao,"
				+ " sum(case when dt.examresult='03' then '1' else '0' end) wubao,"
				+ " ROUND(sum(case when dt.examresult='00' then '1' else '0' end)/count(dt.id), 2) * 100 || '%' baifenbi,"
				+" case when dt.examtype='01' then '科一' when dt.examtype='02' then '科二' when dt.examtype='03' then '科三' when dt.examtype='04' then '科四' else '' end kemu"
				+ " from DT_DRIVING_TEST_OTHER dt"
				+ " left join dt_driving_principal_type dpt on dt.principaltypeid=dpt.principaltypeid "
				+ " where  dt.examresult is not null ";
		sql += " and dt.companyid=? ";
		array.add(account.getCompanyID());
		if(null!=docstatus&&!"".equals(docstatus.trim())){
			sql += " and dt.examtype=? ";
			array.add(docstatus.trim());
		}
		if (search != null && "search".equals(search)) {
			dtDrivingPrincipalType = (DtDrivingPrincipalType) session
					.get("tablesearch2");
			dtDrivingTest = (DtDrivingTest) session.get("tablesearch3");
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				sql += " and dt.testdate between ? and ? ";
				array.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
				array.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
			}else{
				sql += " and dt.testdate between ? and ? ";
				try {
					array.add(DateUtil.parseDate("yyyy-MM-dd",DateUtil.getDateOfMonthBegin(DateUtil.getCurrentDate(), "yyyy-MM-dd")));
					array.add(DateUtil.parseDate("yyyy-MM-dd",DateUtil.getDateOfMonthEnd(DateUtil.getCurrentDate(), "yyyy-MM-dd")));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (dtDrivingPrincipalType != null
					&& dtDrivingPrincipalType.getCoachname() != null
					&& !"".equals(dtDrivingPrincipalType.getCoachname())) {
				sql += " and dpt.coachname like '%"
						+ dtDrivingPrincipalType.getCoachname().trim() + "%'";
			}
			if (dtDrivingTest != null && dtDrivingTest.getExamtype() != null
					&& !"".equals(dtDrivingTest.getExamtype())) {
				sql += " and dt.examtype ='"
						+ dtDrivingTest.getExamtype().trim() + "'";
			}
		}
		sql += "  group by dt.examtype,dpt.coachid,dpt.coachname ";
		if (excel != null && "excel".equals(excel)) {
			List<BaseBean> beans = baseBeanService
					.getListBeanBySqlAndParams(
							"select j.kemu,j.coachname,j.startdate,j.enddate,j.zongshu,j.hege,j.nohege,j.quekao,j.wubao,j.baifenbi from ("
									+ sql + ") j", array.toArray());
			excelStream = excelService.showExcel(DtDrivingPrincipal
					.columnHeadings(), beans);
			return "showexcel";
		} else {
			pageForm = baseBeanService.getPageFormBySQL(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 10 : pageNumber), sql,
					"select count(*) from (" + sql + ")", array.toArray());
			return "driving_list_fenxi_test";
		}
	}

	/**
	 * 测试合格率分析查询
	 * 
	 * @return
	 */
	public String toSearchAnalysisOfTest() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch2", dtDrivingPrincipalType);
		session.put("tablesearch3", dtDrivingTest);
		return getAnalysisListOfTest();
	}
	
	
	/**
	 * 
	 * 考试合格率分析列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getAnalysisList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> array = new ArrayList<Object>();
		String sql = "select  dpt.coachid,dpt.coachname coachname,";
		if (search != null && "search".equals(search)) {
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				sql += "to_date('" + sdate + "','yyyy-MM-dd') startdate,to_date('"
						+ edate + "','yyyy-MM-dd') enddate,";
			}else{
				try {
					sql += "to_date('" + DateUtil.getDateOfMonthBegin(DateUtil.getCurrentDate(), "yyyy-MM-dd") + "','yyyy-MM-dd') startdate,to_date('"
							+ DateUtil.getDateOfMonthEnd(DateUtil.getCurrentDate(), "yyyy-MM-dd") + "','yyyy-MM-dd') enddate,";
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		sql += " count(dt.drivingtestid) zongshu,"
				+ " sum(case when dt.examresult='00' then '1' else '0' end) hege,"
				+ " sum(case when dt.examresult='01' then '1' else '0' end) nohege,"
				+ " sum(case when dt.examresult='02' then '1' else '0' end) quekao,"
				+ " sum(case when dt.examresult='03' then '1' else '0' end) wubao,"
				+ " ROUND(sum(case when dt.examresult='00' then '1' else '0' end)/count(dt.drivingtestid), 2) * 100 || '%' baifenbi,"
				+" case when dt.examtype='01' then '科一' when dt.examtype='02' then '科二' when dt.examtype='03' then '科三' when dt.examtype='04' then '科四' else '' end kemu"
				+ " from dt_driving_test dt"
				+ " left join dt_driving_principal_type dpt on dt.principaltypeid=dpt.principaltypeid "
				+ " where  dt.examresult is not null ";
		sql += " and dt.companyid=? ";
		array.add(account.getCompanyID());
		if(null!=docstatus&&!"".equals(docstatus.trim())){
			sql += " and dt.examtype=? ";
			array.add(docstatus.trim());
		}
		if (search != null && "search".equals(search)) {
			dtDrivingPrincipalType = (DtDrivingPrincipalType) session
					.get("tablesearch2");
			dtDrivingTest = (DtDrivingTest) session.get("tablesearch3");
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				sql += " and dt.testdate between ? and ? ";
				array.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
				array.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
			}else{
				sql += " and dt.testdate between ? and ? ";
				try {
					array.add(DateUtil.parseDate("yyyy-MM-dd",DateUtil.getDateOfMonthBegin(DateUtil.getCurrentDate(), "yyyy-MM-dd")));
					array.add(DateUtil.parseDate("yyyy-MM-dd",DateUtil.getDateOfMonthEnd(DateUtil.getCurrentDate(), "yyyy-MM-dd")));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (dtDrivingPrincipalType != null
					&& dtDrivingPrincipalType.getCoachname() != null
					&& !"".equals(dtDrivingPrincipalType.getCoachname())) {
				sql += " and dpt.coachname like '%"
						+ dtDrivingPrincipalType.getCoachname().trim() + "%'";
			}
			if (dtDrivingTest != null && dtDrivingTest.getExamtype() != null
					&& !"".equals(dtDrivingTest.getExamtype())) {
				sql += " and dt.examtype ='"
						+ dtDrivingTest.getExamtype().trim() + "'";
			}
		}
		sql += "  group by dt.examtype,dpt.coachid,dpt.coachname ";
		if (excel != null && "excel".equals(excel)) {
			List<BaseBean> beans = baseBeanService
					.getListBeanBySqlAndParams(
							"select j.kemu,j.coachname,j.startdate,j.enddate,j.zongshu,j.hege,j.nohege,j.quekao,j.wubao,j.baifenbi from ("
									+ sql + ") j", array.toArray());
			excelStream = excelService.showExcel(DtDrivingPrincipal
					.columnHeadings(), beans);
			return "showexcel";
		} else {
			pageForm = baseBeanService.getPageFormBySQL(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 10 : pageNumber), sql,
					"select count(*) from (" + sql + ")", array.toArray());
			return "driving_list_fenxi";
		}
	}

	/**
	 * 考试合格率分析查询
	 * 
	 * @return
	 */
	public String toSearchAnalysis() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch2", dtDrivingPrincipalType);
		session.put("tablesearch3", dtDrivingTest);
		return getAnalysisList();
	}
	/**
	 * 测试记录登记表打印预览
	 * @return
	 */
	public String toPrintOfDrivingTest(){
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String  hql=" from DtDrivingPrincipal where drivingprincipalid=? ";
			dtDrivingPrincipal=(DtDrivingPrincipal) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cstaff.getStaffID()});
			
			cstaff =(Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ? ", new Object[]{dtDrivingPrincipal.getStudentid()});
			List<Object> array = new ArrayList<Object>();
			String sql = "select dp.registrationdate,dp.registrationcarname,dp.studentname,dp.studentsex,dp.studentcard,dp.studentphone,dpt.coachname,dt.testdate,dt.examScore,"
					+ " case  when dt.examresult='00' then '合格'  when dt.examresult='01' then '不合格' "
					+ " when dt.examresult='02' then '缺考' when dt.examresult='03' then '误报' else  '未统计' end ,dt.id,dp.studentid "
					+" ,case when dt.examtype='01' then '科一' when dt.examtype='02' then '科二'when dt.examtype='03' then '科三'when dt.examtype='04' then '科四' else '' end"
					+ " from DT_DRIVING_TEST_OTHER dt"
					+ " left join dt_driving_principal_type dpt on dpt.principaltypeid =dt.principaltypeid"
					+ " left join dt_driving_principal dp on dp.drivingprincipalid=dt.drivingprincipalid"
					+ " left join dtccode dc on dc.codeid=dp.registrationcarid where 1=1 ";
			sql += " and dt.companyid=? ";
			array.add(account.getCompanyID());
			if(null!=docstatus&&!"".equals(docstatus.trim())){
				sql += " and dt.examtype=? ";
				array.add(docstatus.trim());
			}
			/**
			 * 根据学员ID查询学员--新版学员报名调用extensionStaffCoach
			 */
			if(extensionStaffCoach!=null&&"extensionStaffStudent".equals(extensionStaffCoach)){
				sql += " and dp.studentid=? ";
				array.add(cstaff.getStaffID());
			}
			sql += " order by  dt.testdate desc ";
			@SuppressWarnings("unchecked")
			List<BaseBean> beanList=baseBeanService.getListBeanBySqlAndParams(sql, array.toArray());
			ActionContext.getContext().put("beanList", beanList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		
		return "toPrintOfDrivingTest";
	}
	/**
	 * 考试记录登记表打印预览
	 * @return
	 */
	public String toPrintOfDrivingExam(){
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String  hql=" from DtDrivingPrincipal where drivingprincipalid=? ";
			dtDrivingPrincipal=(DtDrivingPrincipal) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cstaff.getStaffID()});
			
			cstaff =(Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ? ", new Object[]{dtDrivingPrincipal.getStudentid()});
			List<Object> array = new ArrayList<Object>();
			String sql = "select dp.registrationdate,dp.registrationcarname,dp.studentname,dp.studentsex,dp.studentcard,dp.studentphone,dpt.coachname,dt.testdate,dt.examScore,"
					+ " case  when dt.examresult='00' then '合格'  when dt.examresult='01' then '不合格' "
					+ " when dt.examresult='02' then '缺考' when dt.examresult='03' then '误报' else  '未统计' end ,dt.drivingtestid,dp.studentid "
					+" ,case when dt.examtype='01' then '科一' when dt.examtype='02' then '科二'when dt.examtype='03' then '科三'when dt.examtype='04' then '科四' else '' end"
					+ " from DT_DRIVING_TEST dt"
					+ " left join dt_driving_principal_type dpt on dpt.principaltypeid =dt.principaltypeid"
					+ " left join dt_driving_principal dp on dp.drivingprincipalid=dt.drivingprincipalid"
					+ " left join dtccode dc on dc.codeid=dp.registrationcarid where 1=1 ";
			sql += " and dt.companyid=? ";
			array.add(account.getCompanyID());
			if(null!=docstatus&&!"".equals(docstatus.trim())){
				sql += " and dt.examtype=? ";
				array.add(docstatus.trim());
			}
			/**
			 * 根据学员ID查询学员--新版学员报名调用extensionStaffCoach
			 */
			if(extensionStaffCoach!=null&&"extensionStaffStudent".equals(extensionStaffCoach)){
				sql += " and dp.studentid=? ";
				array.add(cstaff.getStaffID());
			}
			sql += " order by  dt.testdate desc ";
			@SuppressWarnings("unchecked")
			List<BaseBean> beanList=baseBeanService.getListBeanBySqlAndParams(sql, array.toArray());
			ActionContext.getContext().put("beanList", beanList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		
		return "toPrintOfDrivingExam";
	}

	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}

	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
	}

	public ServerService getServerService() {
		return serverService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

	public CLogBookService getLogBookService() {
		return logBookService;
	}

	public void setLogBookService(CLogBookService logBookService) {
		this.logBookService = logBookService;
	}

	public ShowExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}

	public CCodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CCodeService codeService) {
		this.codeService = codeService;
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

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public DtDrivingPrincipal getDtDrivingPrincipal() {
		return dtDrivingPrincipal;
	}

	public void setDtDrivingPrincipal(DtDrivingPrincipal dtDrivingPrincipal) {
		this.dtDrivingPrincipal = dtDrivingPrincipal;
	}

	public DtDrivingOperationPeople getDtDrivingOperationPeople() {
		return dtDrivingOperationPeople;
	}

	public void setDtDrivingOperationPeople(
			DtDrivingOperationPeople dtDrivingOperationPeople) {
		this.dtDrivingOperationPeople = dtDrivingOperationPeople;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<CCode> getExaminationList() {
		return examinationList;
	}

	public void setExaminationList(List<CCode> examinationList) {
		this.examinationList = examinationList;
	}

	public String getDocstatus() {
		return docstatus;
	}

	public void setDocstatus(String docstatus) {
		this.docstatus = docstatus;
	}

	public String getStudentstatus() {
		return studentstatus;
	}

	public void setStudentstatus(String studentstatus) {
		this.studentstatus = studentstatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public CarInformation getCarInformation() {
		return carInformation;
	}

	public void setCarInformation(CarInformation carInformation) {
		this.carInformation = carInformation;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public DtDrivingTest getDtDrivingTest() {
		return dtDrivingTest;
	}

	public void setDtDrivingTest(DtDrivingTest dtDrivingTest) {
		this.dtDrivingTest = dtDrivingTest;
	}

	public DtDrivingPrincipalType getDtDrivingPrincipalType() {
		return dtDrivingPrincipalType;
	}

	public void setDtDrivingPrincipalType(
			DtDrivingPrincipalType dtDrivingPrincipalType) {
		this.dtDrivingPrincipalType = dtDrivingPrincipalType;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getExcel() {
		return excel;
	}

	public void setExcel(String excel) {
		this.excel = excel;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public ArchiveTemp getArchiveTemp() {
		return archiveTemp;
	}

	public void setArchiveTemp(ArchiveTemp archiveTemp) {
		this.archiveTemp = archiveTemp;
	}

	public String getExtensionStaffCoach() {
		return extensionStaffCoach;
	}

	public void setExtensionStaffCoach(String extensionStaffCoach) {
		this.extensionStaffCoach = extensionStaffCoach;
	}

	public String getIsShowDangAn() {
		return isShowDangAn;
	}

	public void setIsShowDangAn(String isShowDangAn) {
		this.isShowDangAn = isShowDangAn;
	}

	public String getStrsScore() {
		return strsScore;
	}

	public void setStrsScore(String strsScore) {
		this.strsScore = strsScore;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getDocstatusStr() {
		return docstatusStr;
	}

	public void setDocstatusStr(String docstatusStr) {
		this.docstatusStr = docstatusStr;
	}

	public Staff getCstaff() {
		return cstaff;
	}

	public void setCstaff(Staff cstaff) {
		this.cstaff = cstaff;
	}

	public List<CCode> getAppointmentList() {
		return appointmentList;
	}

	public void setAppointmentList(List<CCode> appointmentList) {
		this.appointmentList = appointmentList;
	}

	public Map<String, DtDrivingTestOther> getDtDrivingTestOtherMap() {
		return dtDrivingTestOtherMap;
	}

	public void setDtDrivingTestOtherMap(
			Map<String, DtDrivingTestOther> dtDrivingTestOtherMap) {
		this.dtDrivingTestOtherMap = dtDrivingTestOtherMap;
	}

	public Map<String, DtDrivingTest> getDtDrivingTestMap() {
		return dtDrivingTestMap;
	}

	public void setDtDrivingTestMap(Map<String, DtDrivingTest> dtDrivingTestMap) {
		this.dtDrivingTestMap = dtDrivingTestMap;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	

	
	
}
