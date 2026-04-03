package hy.ea.ddsr.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.ddsr.Ddsrcoach;
import hy.ea.bo.ddsr.Ddsrreservationrecord;
import hy.ea.bo.ddsr.Dssrstudent;
import hy.ea.bo.ddsr.Dssrsubject;
import hy.ea.bo.human.Staff;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class CoachReservationRecordAction {
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	private Ddsrcoach ddsrcoach;
	private Dssrstudent dssrstudent;
	private Staff dtHrStaff;
	private int pageNumber;
	private List<BaseBean> beans;
	private PageForm pageForm;
	
	private String result;
	
	private String search;
	/**
	 * 科目
	 */
	private Dssrsubject dssrsubject;
	/**
	 * 
	 * 预约记录
	 */
	private Ddsrreservationrecord ddsrreservationrecord;
	private List<Object> arrayDateList=new ArrayList<Object>();
	Map<String, Object> session = ActionContext.getContext().getSession();
	private String unionCompanyID;
	/**
	 * 查询
	 * @return
	 */
	public String toSearch(){
		if(unionCompanyID!=null&&!"".equals(unionCompanyID.trim())){//驾校联盟网站 中 将companyID存放在session中
			session.put("unionCompanyID", unionCompanyID.trim());
		}
		session.put("tablesearch", dssrstudent);
		session.put("tablesearch1", ddsrcoach);
		session.put("tablesearch2", ddsrreservationrecord);
		return getListCoachReservationRecord();
	}
	/**
	 * 
	 * 参数：
	 * 返回：coachreservationrecord_list
	 */
	public String getListCoachReservationRecord() {
		
		if(search!=null&&"search".equals(search)){
			Ddsrreservationrecord ddsrreservationrecord=(Ddsrreservationrecord) session.get("tablesearch2");
			Ddsrreservationrecord ddsrreservationrecord1=validationOrConstructionDateParam(ddsrreservationrecord);
			arrayDateList=setTheDateHead(ddsrreservationrecord1.getSearchStaDate(),ddsrreservationrecord1.getSearchEndDate());
		}else{
			Ddsrreservationrecord ddsrreservationrecord1=validationOrConstructionDateParam(ddsrreservationrecord);
			arrayDateList=setTheDateHead(ddsrreservationrecord1.getSearchStaDate(),ddsrreservationrecord1.getSearchEndDate());
		}
		
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getSqlAndParam().get(0).toString()
				, "select count(1) from("+getSqlAndParam().get(0).toString()+")",  
				(Object[])getSqlAndParam().get(1));
		return "coachreservationrecord_list";
	}
	/**
	 * 构造sql语句
	 * @author allen
	 */
	private List<Object> getSqlAndParam(){
		CAccount account = (CAccount) session.get("account");
		
		List<Object> sqlAndParam=new ArrayList<Object>();
		List<Object> param=new ArrayList<Object>();
		
		StringBuffer sql=new StringBuffer("");
		StringBuffer sqlSelectStatic=new StringBuffer(" select ");
		
		sqlSelectStatic.append("max(dsrr.rere_key) rere_key,");
		sqlSelectStatic.append("dsc.coac_companyid coac_companyid,");
		sqlSelectStatic.append("dsc.coac_key  coac_key,");
		sqlSelectStatic.append("max(hs.staffname) staffname ,");
		sqlSelectStatic.append("max(dsc.coac_carNumber) coac_carNumber,");
		sqlSelectStatic.append("max(dsc.coac_status) coac_status,");
		sqlSelectStatic.append("max(dsc.coac_level) coac_level,");
		sqlSelectStatic.append("max(dsc.coac_teachtype) coac_teachtype,");
		sqlSelectStatic.append("max(dsc.coac_star) coac_star,");
		
		sql.append(sqlSelectStatic);
		
		
		StringBuffer sqlSelectDynamic=new StringBuffer("");
		if(arrayDateList!=null&&arrayDateList.size()>0){
			for (int i = 0; i < arrayDateList.size(); i++) {
				sqlSelectDynamic.append("sum(case when to_char(dsrr.rere_appdate,'yyyy-MM-DD')='"+arrayDateList.get(i).toString()+"' and dsrr.rere_class='10' then dsrr.rere_peoplesum else 0 end)");
				sqlSelectDynamic.append(" || ' | ' || ");
				sqlSelectDynamic.append("sum(case when to_char(dsrr.rere_appdate,'yyyy-MM-DD')='"+arrayDateList.get(i).toString()+"' and dsrr.rere_class='20' then dsrr.rere_peoplesum else 0 end)");
				sqlSelectDynamic.append(" || ' | ' || ");
				sqlSelectDynamic.append("sum(case when to_char(dsrr.rere_appdate,'yyyy-MM-DD')='"+arrayDateList.get(i).toString()+"' and dsrr.rere_class='30' then dsrr.rere_peoplesum else 0 end)");
				sqlSelectDynamic.append(" || ' | ' || ");
				sqlSelectDynamic.append("sum(case when to_char(dsrr.rere_appdate,'yyyy-MM-DD')='"+arrayDateList.get(i).toString()+"' and dsrr.rere_class='40' then dsrr.rere_peoplesum else 0 end)");
				sqlSelectDynamic.append(" || ' @ '||");
				sqlSelectDynamic.append("case when count(case when to_char(dsrr.rere_appdate,'yyyy-MM-DD')='"+arrayDateList.get(i).toString()+"'  then 1 end)=sum(case when to_char(dsrr.rere_appdate,'yyyy-MM-DD')='"+arrayDateList.get(i).toString()+"' then dsrr.rere_peoplesum end) and sum(case when to_char(dsrr.rere_appdate,'yyyy-MM-DD')='"+arrayDateList.get(i).toString()+"' then dsrr.rere_peoplesum end)>0 then 1 else 0 end");
				sqlSelectDynamic.append(" sum"+i); 
				sqlSelectDynamic.append(",");
			}
		}
		sql.append(sqlSelectDynamic.substring(0, sqlSelectDynamic.lastIndexOf(",")));
		
		
		StringBuffer sqlFromStatic=new StringBuffer(" from ");
		sqlFromStatic.append(" ddsrcoach dsc ");
		sqlFromStatic.append(" left join Re_ddsrCoach_dssrSubject rdds on rdds.coac_key=dsc.coac_key ");
		sqlFromStatic.append(" left join dssrSubject ds on ds.subj_key=rdds.subj_key ");
		sqlFromStatic.append(" left join dt_hr_staff hs on hs.staffkey=dsc.coac_key ");
		if(search!=null&&"search".equals(search)){
			Ddsrreservationrecord ddsrreservationrecordSessionScope=(Ddsrreservationrecord) session.get("tablesearch2");
			Ddsrreservationrecord ddsrreservationrecord1=validationOrConstructionDateParam(ddsrreservationrecordSessionScope);
			sqlFromStatic.append(" left join ddsrreservationrecord dsrr on dsrr.coac_key=dsc.coac_key and dsrr.rere_appdate between ? and ? ");
			param.add(ddsrreservationrecord1.getSearchStaDate());
			param.add(ddsrreservationrecord1.getSearchEndDate());
		}else{
			Ddsrreservationrecord ddsrreservationrecord1=validationOrConstructionDateParam(ddsrreservationrecord);
			sqlFromStatic.append(" left join ddsrreservationrecord dsrr on dsrr.coac_key=dsc.coac_key and dsrr.rere_appdate between ? and ? ");
			param.add(ddsrreservationrecord1.getSearchStaDate());
			param.add(ddsrreservationrecord1.getSearchEndDate());
		}
		sqlFromStatic.append(" left join Re_Dssrstudent_Ddsrresrecord rddr on rddr.rere_key=dsrr.rere_key ");
		sqlFromStatic.append(" left join dssrstudent dss on dss.stud_key=rddr.stud_key ");
		sqlFromStatic.append(" left join dt_hr_staff hs1 on hs1.staffkey=dss.stud_key ");
		sql.append(sqlFromStatic);
		
		StringBuffer sqlWhereStatic=new StringBuffer(" where ");
		sqlWhereStatic.append(" 1=1 ");
		
		if(session.get("unionCompanyID")!=null){//区别于  驾校联盟网站传参获得数据 从session中获得companyID;
			sqlWhereStatic.append(" and dsc.coac_companyid=? ");
			param.add(session.get("unionCompanyID"));
		}else if(account!=null&&null!=account.getCompanyID()&&!"".equals(account.getCompanyID())){
			sqlWhereStatic.append(" and dsc.coac_companyid=? ");
			param.add(account.getCompanyID());
		}
		
		if(dssrsubject!=null){
			if(dssrsubject.getSubjType()!=null&&!"".equals(dssrsubject.getSubjType())){
				sqlWhereStatic.append(" and ds.subj_type=? ");
				param.add(dssrsubject.getSubjType());
			}
		}
		if(search!=null&&"search".equals(search)){
			Dssrstudent dssrstudent=(Dssrstudent) session.get("tablesearch");
			Ddsrcoach ddsrcoach=(Ddsrcoach) session.get("tablesearch1");
			if(ddsrcoach!=null){
				if(ddsrcoach.getDtHrStaff()!=null){
					if(ddsrcoach.getDtHrStaff().getStaffName()!=null&&!"".equals(ddsrcoach.getDtHrStaff().getStaffName().trim())){
						sqlWhereStatic.append(" and hs.STAFFNAME like ? ");
						param.add("%"+ddsrcoach.getDtHrStaff().getStaffName().trim()+"%");
					}
					if(ddsrcoach.getDtHrStaff().getStaffID()!=null&&!"".equals(ddsrcoach.getDtHrStaff().getStaffID().trim())){
						sqlWhereStatic.append(" and hs.staffID = ? ");
						param.add(ddsrcoach.getDtHrStaff().getStaffID().trim());
					}
				}
				if(ddsrcoach.getCoacLevel()!=null&&!"".equals(ddsrcoach.getCoacLevel().trim())){
						sqlWhereStatic.append(" and dsc.coac_level like ? ");
						param.add("%"+ddsrcoach.getCoacLevel().trim()+"%");
				}
				if(ddsrcoach.getCoacTeachtype()!=null&&!"".equals(ddsrcoach.getCoacTeachtype().trim())){
						sqlWhereStatic.append(" and dsc.coac_teachtype like ? ");
						param.add("%"+ddsrcoach.getCoacTeachtype().trim()+"%");
				}
				if(ddsrcoach.getCoacStar()!=null&&!"".equals(ddsrcoach.getCoacStar())){
					sqlWhereStatic.append(" and dsc.coac_star = ? ");
					param.add(ddsrcoach.getCoacStar());
				}
				if(ddsrcoach.getCoacStatus()!=null&&!"".equals(ddsrcoach.getCoacStatus())){
					sqlWhereStatic.append(" and dsc.coac_status = ? ");
					param.add(ddsrcoach.getCoacStatus());
				}
				if(ddsrcoach.getCoacKey()!=null&&!"".equals(ddsrcoach.getCoacKey().trim())){
					sqlWhereStatic.append(" and dsc.coac_key = ? ");
					param.add(ddsrcoach.getCoacKey().trim());
				}
			}
			if(dssrstudent!=null){
				if(dssrstudent.getDtHrStaff()!=null){
					if(dssrstudent.getDtHrStaff().getStaffName()!=null&&!"".equals(dssrstudent.getDtHrStaff().getStaffName().trim())){
						sqlWhereStatic.append(" and hs1.STAFFNAME like ? ");
						param.add("%"+dssrstudent.getDtHrStaff().getStaffName().trim()+"%");
					}
					if(dssrstudent.getDtHrStaff().getStaffID()!=null&&!"".equals(dssrstudent.getDtHrStaff().getStaffID().trim())){
						sqlWhereStatic.append(" and hs1.staffID = ? ");
						param.add(dssrstudent.getDtHrStaff().getStaffID().trim());
					}
				}
				if(dssrstudent.getStudKey()!=null&&!"".equals(dssrstudent.getStudKey().trim())){
					sqlWhereStatic.append(" and dss.stud_key = ? ");
					param.add(dssrstudent.getStudKey().trim());
				}
			}
		}else{
			sqlWhereStatic.append(" and dsc.coac_status=? ");
			param.add(10);
		}
			
		sql.append(sqlWhereStatic);
		
		StringBuffer sqlGroupByStatic=new StringBuffer(" group by ");
		sqlGroupByStatic.append(" dsc.coac_companyid, ");
		sqlGroupByStatic.append(" dsc.coac_key ");
		sql.append(sqlGroupByStatic);
		
		if(search!=null&&"search".equals(search)){
			Ddsrreservationrecord ddsrreservationrecordSessionScope=(Ddsrreservationrecord) session.get("tablesearch2");
			if(ddsrreservationrecordSessionScope!=null){
				if(ddsrreservationrecordSessionScope.getRerePeoplesum()!=null&&!"".equals(ddsrreservationrecordSessionScope.getRerePeoplesum())){
					StringBuffer sqlHavingStatic=new StringBuffer(" having ");
					sqlHavingStatic.append(" (case when count(dsrr.rere_peoplesum)=sum(dsrr.rere_peoplesum) and sum(dsrr.rere_peoplesum)>0 then 1 else 0 end) = ?");
					param.add(ddsrreservationrecordSessionScope.getRerePeoplesum());
					sql.append(sqlHavingStatic);
				}
			}
		}
		
		sqlAndParam.add(sql);
		sqlAndParam.add(param.toArray());
		return sqlAndParam;
	}
	/**
	 * 构造或验证时间是否符合规定
	 */
	private Ddsrreservationrecord  validationOrConstructionDateParam(Ddsrreservationrecord  ddsrreservationrecord){
		Date searchStaDate=null;
		Date searchEndDate=null;
		Calendar calendarStaDate=DateUtil.toCalendarFromUtilDate(new Date());
		Calendar calendarEndDate=DateUtil.toCalendarFromUtilDate(new Date());
		if(ddsrreservationrecord!=null){
			if((ddsrreservationrecord.getSearchStaDate()==null||"".equals(ddsrreservationrecord.getSearchStaDate()))
					&&(ddsrreservationrecord.getSearchEndDate()==null||"".equals(ddsrreservationrecord.getSearchEndDate()))){
				try {
					searchStaDate=DateUtil.parseDate("yyyy-MM-dd", DateUtil.toStrDateFromUtilDateByFormat(calendarStaDate.getTime(),"yyyy-MM-dd"));
					calendarEndDate.add(Calendar.DAY_OF_MONTH, 7);
					searchEndDate=DateUtil.parseDate("yyyy-MM-dd", DateUtil.toStrDateFromUtilDateByFormat(calendarEndDate.getTime(),"yyyy-MM-dd"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ddsrreservationrecord.setSearchStaDate(searchStaDate);
				ddsrreservationrecord.setSearchEndDate(searchEndDate);
			}else if((ddsrreservationrecord.getSearchStaDate()==null||"".equals(ddsrreservationrecord.getSearchStaDate()))
					&&(ddsrreservationrecord.getSearchEndDate()!=null&&!"".equals(ddsrreservationrecord.getSearchEndDate()))){
				ddsrreservationrecord.setSearchStaDate(ddsrreservationrecord.getSearchEndDate());
				
			}else if((ddsrreservationrecord.getSearchEndDate()==null||"".equals(ddsrreservationrecord.getSearchEndDate()))
					&&(ddsrreservationrecord.getSearchStaDate()!=null&&!"".equals(ddsrreservationrecord.getSearchStaDate()))){
				ddsrreservationrecord.setSearchEndDate(ddsrreservationrecord.getSearchStaDate());
			}
			if(ddsrreservationrecord.getSearchEndDate().compareTo(ddsrreservationrecord.getSearchStaDate())==-1){
				try {
					searchStaDate=DateUtil.parseDate("yyyy-MM-dd", DateUtil.toStrDateFromUtilDateByFormat(calendarStaDate.getTime(),"yyyy-MM-dd"));
					calendarEndDate.add(Calendar.DAY_OF_MONTH, 7);
					searchEndDate=DateUtil.parseDate("yyyy-MM-dd", DateUtil.toStrDateFromUtilDateByFormat(calendarEndDate.getTime(),"yyyy-MM-dd"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ddsrreservationrecord.setSearchStaDate(searchStaDate);
				ddsrreservationrecord.setSearchEndDate(searchEndDate);
			}
		}else{
			
			try {
				searchStaDate=DateUtil.parseDate("yyyy-MM-dd", DateUtil.toStrDateFromUtilDateByFormat(calendarStaDate.getTime(),"yyyy-MM-dd"));
				calendarEndDate.add(Calendar.DAY_OF_MONTH, 7);
				searchEndDate=DateUtil.parseDate("yyyy-MM-dd", DateUtil.toStrDateFromUtilDateByFormat(calendarEndDate.getTime(),"yyyy-MM-dd"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ddsrreservationrecord=new Ddsrreservationrecord();
			ddsrreservationrecord.setSearchStaDate(searchStaDate);
			ddsrreservationrecord.setSearchEndDate(searchEndDate);
			
		}
		
		return ddsrreservationrecord;
	}
	
	/**
	 * 设置表头参数
	 * @author allen
	 */
	public List<Object> setTheDateHead(Date startDate,Date endDate){
		int i=0;
		try {
			i = DateUtil.compareDate(DateUtil.toStrDateFromUtilDateByFormat(startDate,"yyyy-MM-dd"), DateUtil.toStrDateFromUtilDateByFormat(endDate,"yyyy-MM-dd"), 0);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Calendar calendar=DateUtil.toCalendarFromUtilDate(startDate);
		for (int j = 0; j <= i; j++) {
			calendar.add(Calendar.DAY_OF_MONTH,j);
			try {
				arrayDateList.add(DateUtil.toStrDateFromUtilDateByFormat(calendar.getTime(),"yyyy-MM-dd"));
				calendar=DateUtil.toCalendarFromUtilDate(startDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return arrayDateList;
	}
	
	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}


	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Ddsrcoach getDdsrcoach() {
		return ddsrcoach;
	}
	public void setDdsrcoach(Ddsrcoach ddsrcoach) {
		this.ddsrcoach = ddsrcoach;
	}
	public List<Object> getArrayDateList() {
		return arrayDateList;
	}
	public void setArrayDateList(List<Object> arrayDateList) {
		this.arrayDateList = arrayDateList;
	}
	public Staff getDtHrStaff() {
		return dtHrStaff;
	}
	public void setDtHrStaff(Staff dtHrStaff) {
		this.dtHrStaff = dtHrStaff;
	}
	public Dssrsubject getDssrsubject() {
		return dssrsubject;
	}
	public void setDssrsubject(Dssrsubject dssrsubject) {
		this.dssrsubject = dssrsubject;
	}
	public Ddsrreservationrecord getDdsrreservationrecord() {
		return ddsrreservationrecord;
	}
	public void setDdsrreservationrecord(Ddsrreservationrecord ddsrreservationrecord) {
		this.ddsrreservationrecord = ddsrreservationrecord;
	}
	public Dssrstudent getDssrstudent() {
		return dssrstudent;
	}
	public void setDssrstudent(Dssrstudent dssrstudent) {
		this.dssrstudent = dssrstudent;
	}
	public String getUnionCompanyID() {
		return unionCompanyID;
	}
	public void setUnionCompanyID(String unionCompanyID) {
		this.unionCompanyID = unionCompanyID;
	}
	
	
	
}
