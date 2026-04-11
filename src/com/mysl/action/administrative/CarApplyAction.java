package com.mysl.action.administrative;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Remind;
import hy.ea.bo.human.Staff;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mysl.bo.administrative.DtMycheck;
import com.mysl.bo.administrative.DtMyusecarapply;
import com.mysl.bo.administrative.DtMyusecarlog;
import com.mysl.service.RemindService;
import com.opensymphony.xwork2.ActionContext;
/*
 *  用车申请
 * */
@Controller
@Scope("prototype")
public class CarApplyAction {
	private static final Logger logger = LoggerFactory.getLogger(CarApplyAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private RemindService remindService; 
	private String parameter;
    private DtMyusecarapply usecarapply;//用车申请单据
    private DtMyusecarlog usecarlog;//行车记录
    private DtMycheck dtMycheck;//单据审核
	private PageForm pageForm;
	private String search;
	private int pageNumber;
	private String submittype;//提交类型（保存草稿或者提交审核）
	private String Dvusetime;//借用时间
	private String Dvbacktime;//计划归还时间
	private String type;//区分部门和个人参数
	private InputStream excelStream;
	private boolean blo; //判断是查询列表还是导出查询
	
	private String checkid;//审核id
	private String checkurl;//审核路径
	@Resource
	private ShowExcelService excelService;
	private String excel;
	
	private String result;//ajax返回结果
	private String carcode;//ajax参数
    //导出用车申请列表
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<BaseBean> list = null;
	    list = baseBeanService.getListByDC(getList());
	    excelStream = excelService.showExcel(DtMyusecarapply.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出用车申请列表", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/**
	 * 用车申请——打印单据
	 */
	public String toPrintUseCarApply() {
		toSee();
		return "printcarapply";
	}
	/**
	 * 用车申请——查看
	 */
	public String toDetailUseCarApply(){
		toSee();	
		return "tocarapplyDetail";
	}
	/**
	 * 用车申请/审核单据——查看调用
	 * 
	 */
	private void toSee(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql="from DtMyusecarapply where companyid = ? and id=?";
		usecarapply=(DtMyusecarapply) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),usecarapply.getId()});
		String hqlc="from DtMycheck where id=? order by addtime desc";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				hqlc, new Object[]{usecarapply.getId()});
	}
	//根据条件查询用车申请
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", usecarapply);
		session.put("Dvusetime", Dvusetime);
		return getDtMyusecarapplyList();
	}
	
	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { account.getStaffID() });
		session.put("StaffName", sta.getStaffName());
		DetachedCriteria dc = DetachedCriteria.forClass(DtMyusecarapply.class);
		dc.add(Restrictions.eq("companyid", account.getCompanyID()));
		if(type.equals("org")){
			dc.add(Restrictions.eq("organizationid",organizationID));
		}else if(type.equals("my")){
			dc.add(Restrictions.eq("staffid", account.getStaffID()));
		}
		dc.addOrder(Order.desc("addtime"));
		
		if (search != null && search.equals("search")) {
			usecarapply = (DtMyusecarapply) session.get("tablesearch");
			String Dvusetime=(String)session.get("Dvusetime");
			if (!"".equals(usecarapply.getCarcode())){
				dc.add(Restrictions.like("carcode", usecarapply.getCarcode(), MatchMode.ANYWHERE));
			}
			if (!"".equals(usecarapply.getCarusername())){
				dc.add(Restrictions.like("carusername",usecarapply.getCarusername(),MatchMode.ANYWHERE));
			}
			if (!Dvusetime.equals("")){
				SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
					Date  ttt= matter1.parse(Dvusetime);
					dc.add(Restrictions.eq("carusetime",ttt));
				} catch (ParseException e) {
					logger.error("操作异常", e);
				}
			}
	    }
		return dc;
	}
	// 用车申请单据列表
	public String getDtMyusecarapplyList() {
	   pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
	   return "carapplylist";	
	}
//-------------------------------------------------------行车记录开始-------------------------------------
	//导出行车
	@SuppressWarnings("unchecked")
	public String showExcelcarlog() {
		blo=false;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<BaseBean> lists=new ArrayList<BaseBean>();
		List<Object> result = getlista();
		String hql=result.get(0).toString();
		Object[] parms = (Object[])result.get(1);
		lists=baseBeanService.getListBeanBySqlAndParams(hql, parms);
	    excelStream = excelService.showExcel(DtMyusecarlog.columnHeadings(),lists);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出行车记录", account);
		baseBeanService.update(logBook);
		return "showexcelcarlog";
	}
	//ajax获取选中行车单对应的车俩使用前的公里数
	public String getcarkm() {
		List<String> params = new ArrayList<String>();
		String hql= "from DtMyusecarlog c where c.carcode = ? order by c.endnum desc";
		params.add(carcode);
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(
				hql, params.toArray());
		Map<String, Object> map = new HashMap<String, Object>();
		int a=list.size();
		if(a==0){
			map.put("list", "no");
		}else{map.put("list", list);}
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	//根据条件查询行车日志
	public String toSearchysh() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", usecarapply);
			return getDtMyusecarapplyListysh();
	}
	// 行车日志列表
	public String getDtMyusecarapplyListysh() {
		blo=true;
		List<Object> result = getlista();
		String hql=result.get(0).toString();
		Object[] parms = (Object[])result.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql,"select count(1) from ("
						+ hql +" )", parms);
		return "carapplylistysh";	
	}
	public List<Object> getlista(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		List<Object> result = new ArrayList<Object>();
		String hql =null;
		if(blo){
			hql= "select a.id,a.companyname,a.companyid,a.organizationname,a.organizationid,a.status,a.staffid,"+
		            "a.staffname,a.addtime,a.serialNumber,a.carcode,a.carusetime,a.carbacktime,"+
				    "a.carusereason,a.destination,a.carusername,a.caruserid,a.cardriver,a.cardriverid,"+
					"a.cardriverorgid,a.cardriverorgname,a.cardrivercompanyid,a.cardrivercompanyname,a.remarks,"+
				    "l.key,l.lid,l.starnum,l.endnum,l.countnum,l.roadtoll,l.parkingfees,l.driverremarks"+
				    " from dt_Myusecarapply a" +
					" left join dt_Myusecarlog l on l.carapplyid=a.id" +
					" where a.companyid=? and a.status=?";
		}else{
			hql= "select a.companyname,a.carcode,a.carusereason,a.carusername,a.destination,a.remarks,a.cardriver,"+
				    "l.starnum,l.endnum,l.countnum,l.roadtoll,l.parkingfees,l.driverremarks"+
				    " from dt_Myusecarapply a" +
					" left join dt_Myusecarlog l on l.carapplyid=a.id" +
					" where a.companyid=? and a.status=?";
		}
		List<Object> params = new ArrayList<Object>();
		params.add(account.getCompanyID());
		params.add("05");
		if(type.equals("org")){
			hql += " and a.organizationid=?";
			params.add(organizationID);
		}else if(type.equals("my")){
			hql += " and a.cardriverid=?";
			params.add(account.getStaffID());
		}
		if (search != null && "search".equals(search)) {
			if (usecarapply != null) {
				if (usecarapply.getCarcode() != null
						&& !"".equals(usecarapply.getCarcode())) {
					hql += " and a.carcode like ?";
					params.add("%" + usecarapply.getCarcode().trim() + "%");
				}
				if (usecarapply.getCarusername() != null
						&& !"".equals(usecarapply.getCarusername())) {
					hql += " and a.carusername like ?";
					params.add("%" + usecarapply.getCarusername().trim()+ "%");
				}
			}
		}
		hql += "order by a.addtime desc";
		result.add(hql);
		result.add(params.toArray());
		return result;
	} 
	//保存行车记录
    public String saveCarlog() throws ParseException
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		if (usecarlog.getLid()== null|| "".equals(usecarlog.getLid())) {
			usecarlog.setLid(serverService.getServerID("usecarlog"));
			parameter = "添加行车记录(车牌号:"+usecarlog.getCarcode()+")";
		}
		else
		{
			 String hql1="from DtMyusecarlog where lid=?";
			 DtMyusecarlog car=(DtMyusecarlog) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{usecarlog.getLid()});
			 parameter = "修改行车记录(车牌号:"+car.getCarcode()+")";
		
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
        beans.add(usecarlog);
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "success";
    }
  //-------------------------------------------------------行车记录结束-------------------------------------
	//用车申请提交审核
		public String tocheck() throws ParseException{
			ActionContext actionContext = ActionContext.getContext();
			Map<String, Object> session = actionContext.getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID=(String) session.get("organizationID");
			DtMyusecarapply carapply=new DtMyusecarapply();
			if (usecarapply.getId()!= null|| !"".equals(usecarapply.getId())) {
				 String hql1="from DtMyusecarapply where id=?";
				 carapply=(DtMyusecarapply) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{usecarapply.getId()});
			     parameter = "提交审核(用车申请单据编号:"+carapply.getSerialNumber()+")";
			}
			List<BaseBean> beans=new ArrayList<BaseBean>();
			carapply.setStatus("01");
			beans.add(carapply);
			/** 审核表添数据*/
	        DtMycheck mcheck=new DtMycheck();
	        mcheck.setCheckid(serverService.getServerID("dtMycheck"));
			mcheck.setId(carapply.getId());
			mcheck.setSerialnumber(carapply.getSerialNumber());
			mcheck.setReceiptType("用车申请单");
			mcheck.setLookOverurl("ea/carapply/ea_toDetailUseCarApply.jspa?usecarapply.id="+carapply.getId());
			mcheck.setPrinturl("ea/carapply/ea_toPrintUseCarApply.jspa?usecarapply.id="+carapply.getId());
			mcheck.setListurl("ea/carapply/ea_getDtMyusecarapplyList.jspa");
			mcheck.setTeablename("dt_Myusecarapply");
			//申请审核时间
			SimpleDateFormat matter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dat1=matter.format(new Date());
		    Date  dat= matter.parse(dat1);
		    mcheck.setAddtime(dat);
			//审核人信息
			String hql = "from Staff where staffID = ?";
			Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { account.getStaffID() });
			mcheck.setApplyerid(sta.getStaffID());
			mcheck.setApplyername(sta.getStaffName());
			mcheck.setApplycompanyid(account.getCompanyID());
			mcheck.setApplycompanyname(account.getCompanyName());
			//当前登录人员的部门,id/name,以及岗位
			String sql = "select c.organizationid,c.organizationname,d.postname"
				+ " from dtcos t"
				+ " left join dtcorganization c on t.organizationid = c.organizationid"
				+ " left join dt_hr_deptpost d on t.deppostid = d.deppostid"
				+ " where t.companyid = ? and t.staffid = ? and t.cosstatus = '50' and t.status = '01'";
			Object[] orgpost = (Object[])baseBeanService.getObjectBySqlAndParams(sql, new Object[] {
							account.getCompanyID(),sta.getStaffID()});
			mcheck.setApplyorg(orgpost[0].toString());
			mcheck.setApplyorgname(orgpost[1].toString());
			mcheck.setAuditorstatus("01");//未审核
			mcheck.setAuditorid(dtMycheck.getAuditorid());
			mcheck.setAuditorname(dtMycheck.getAuditorname());
			mcheck.setAuditororgid(dtMycheck.getAuditororgid());
			mcheck.setAuditororgname(dtMycheck.getAuditororgname());
			mcheck.setAuditorcompanyid(dtMycheck.getAuditorcompanyid());
			mcheck.setAuditorcompanyname(dtMycheck.getAuditorcompanyname());
			beans.add(mcheck);
			//行政提醒
			Remind remind = new Remind();
			remind.setRemindID(serverService.getServerID("remind"));
			remind.setCircularType("02");
			remind.setCircularTitle("用车申请单审核待办");
			remind.setCircularText("您项个人审核有一个待办任务，请及时处理！");
			remind.setStaffID(mcheck.getAuditorid());
			remind.setStaffName(mcheck.getAuditorname());
			remind.setOrganizationID(mcheck.getApplyorg());
			remind.setCompanyID(mcheck.getApplycompanyid());
			remind.setAddDate(new Date());
			remind.setRemindStatus("01");
			remind.setRemindType("01");
			remind.setDetailedurl("page/mysl/administrative/ReceiptCheck.jsp");
			remindService.addremind(remind);
			CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
			beans.add(logbook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
	    	return "success";
		}
   //添加用车申请单据
    public String saveCarApply() throws ParseException
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		if (usecarapply.getId()== null|| "".equals(usecarapply.getId())) {
			usecarapply.setId(serverService.getServerID("usecarapply"));
			parameter = "添加用车申请单据(单据编号:"+usecarapply.getSerialNumber()+")";
		}
		else
		{
			 String hql1="from DtMyusecarapply where id=?";
			 DtMyusecarapply car=(DtMyusecarapply) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{usecarapply.getId()});
			 parameter = "修改用车申请单据(单据编号:"+car.getSerialNumber()+")";
		
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
        SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tt=matter1.format(new Date());
        Date  ttt= matter1.parse(tt);
        usecarapply.setAddtime(ttt);
        SimpleDateFormat matter=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date datee1 = matter.parse(Dvusetime);
		Date datee2 = matter.parse(Dvbacktime);
		usecarapply.setCarusetime(datee1);
		usecarapply.setCarbacktime(datee2);
		String hql2 = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { account.getStaffID() });
		usecarapply.setStaffid(sta.getStaffID());
		usecarapply.setStaffname(sta.getStaffName());
		usecarapply.setCaruserid(sta.getStaffID());
		usecarapply.setCarusername(sta.getStaffName());
		usecarapply.setCompanyid(account.getCompanyID());
		usecarapply.setCompanyname(account.getCompanyName());
		//当前登录人员的部门,id/name,以及岗位
		String sql = "select c.organizationid,c.organizationname,d.postname"
			+ " from dtcos t"
			+ " left join dtcorganization c on t.organizationid = c.organizationid"
			+ " left join dt_hr_deptpost d on t.deppostid = d.deppostid"
			+ " where t.companyid = ? and t.staffid = ? and t.cosstatus = '50' and t.status = '01'";
		Object[] orgpost = (Object[])baseBeanService.getObjectBySqlAndParams(sql, new Object[] {
						account.getCompanyID(),sta.getStaffID()});
		usecarapply.setOrganizationid(orgpost[0].toString());
		usecarapply.setOrganizationname(orgpost[1].toString());
		//orgpost[2].toString();岗位名称
		//单据状态
		if(submittype.equals("savecheck"))
		{
			usecarapply.setStatus("01");
			DtMycheck mcheck=new DtMycheck();
			mcheck.setCheckid(serverService.getServerID("dtMycheck"));
			mcheck.setId(usecarapply.getId());
			mcheck.setSerialnumber(usecarapply.getSerialNumber());
			mcheck.setReceiptType("用车申请单");
			mcheck.setLookOverurl("ea/carapply/ea_toDetailUseCarApply.jspa?usecarapply.id="+usecarapply.getId());
			mcheck.setPrinturl("ea/carapply/ea_toPrintUseCarApply.jspa?usecarapply.id="+usecarapply.getId());
			mcheck.setListurl("ea/carapply/ea_getDtMyusecarapplyList.jspa");
			mcheck.setTeablename("dt_Myusecarapply");
			String dat1=matter1.format(new Date());
		    Date  dat= matter1.parse(dat1);
		    mcheck.setAddtime(dat);
		    mcheck.setApplyerid(sta.getStaffID());
		    mcheck.setApplyername(sta.getStaffName());
		    mcheck.setApplycompanyid(account.getCompanyID());
		    mcheck.setApplycompanyname(account.getCompanyName());
		    mcheck.setApplyorg(orgpost[0].toString());
		    mcheck.setApplyorgname(orgpost[1].toString());
		    mcheck.setAuditorstatus("01");//未审核
			mcheck.setAuditorid(dtMycheck.getAuditorid());
			mcheck.setAuditorname(dtMycheck.getAuditorname());
			mcheck.setAuditororgid(dtMycheck.getAuditororgid());
			mcheck.setAuditororgname(dtMycheck.getAuditororgname());
			mcheck.setAuditorcompanyid(dtMycheck.getAuditorcompanyid());
			mcheck.setAuditorcompanyname(dtMycheck.getAuditorcompanyname());
			beans.add(mcheck);
			//行政提醒
			Remind remind = new Remind();
			remind.setRemindID(serverService.getServerID("remind"));
			remind.setCircularType("02");
			remind.setCircularTitle("用车申请单审核待办");
			remind.setCircularText("您项个人审核有一个待办任务，请及时处理！");
			remind.setStaffID(mcheck.getAuditorid());
			remind.setStaffName(mcheck.getAuditorname());
			remind.setOrganizationID(mcheck.getApplyorg());
			remind.setCompanyID(mcheck.getApplycompanyid());
			remind.setAddDate(new Date());
			remind.setRemindStatus("01");
			remind.setRemindType("01");
			remind.setDetailedurl("page/mysl/administrative/ReceiptCheck.jsp");
			remindService.addremind(remind);
		}
		else if(submittype.equals("save")){
			usecarapply.setStatus("00");
		}
        beans.add(usecarapply);
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "success";
    }
    //删除用车申请
	 public String delusecarapply()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID");
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {usecarapply.getId()};
		    String hql2="from DtMyusecarapply where id=?";
		    DtMyusecarapply car=(DtMyusecarapply) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除用车申请单据(单据编号"+car.getSerialNumber()+")", account);
	    	
		    String hql="delete from DtMyusecarapply where id=?";
	    	List<BaseBean> beans=new ArrayList<BaseBean>();
	    	beans.add(logbook);
	    	baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
			return "success";
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

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public String getExcel() {
		return excel;
	}
	public void setExcel(String excel) {
		this.excel = excel;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public DtMyusecarapply getUsecarapply() {
		return usecarapply;
	}
	public void setUsecarapply(DtMyusecarapply usecarapply) {
		this.usecarapply = usecarapply;
	}
	public DtMyusecarlog getUsecarlog() {
		return usecarlog;
	}
	public void setUsecarlog(DtMyusecarlog usecarlog) {
		this.usecarlog = usecarlog;
	}
	public DtMycheck getDtMycheck() {
		return dtMycheck;
	}
	public void setDtMycheck(DtMycheck dtMycheck) {
		this.dtMycheck = dtMycheck;
	}
	public String getSubmittype() {
		return submittype;
	}
	public void setSubmittype(String submittype) {
		this.submittype = submittype;
	}
	public String getDvusetime() {
		return Dvusetime;
	}
	public void setDvusetime(String dvusetime) {
		Dvusetime = dvusetime;
	}
	public String getDvbacktime() {
		return Dvbacktime;
	}
	public void setDvbacktime(String dvbacktime) {
		Dvbacktime = dvbacktime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCheckid() {
		return checkid;
	}
	public void setCheckid(String checkid) {
		this.checkid = checkid;
	}
	public String getCheckurl() {
		return checkurl;
	}
	public void setCheckurl(String checkurl) {
		this.checkurl = checkurl;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getCarcode() {
		return carcode;
	}
	public void setCarcode(String carcode) {
		this.carcode = carcode;
	}
}
