package com.mysl.action.administrative;

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
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mysl.bo.administrative.DtMycertificateapply;
import com.mysl.bo.administrative.DtMycheck;
import com.mysl.service.RemindService;
import com.opensymphony.xwork2.ActionContext;
/*
 * 证书领用申请
 * */
@Controller
@Scope("prototype")
public class CertificateApplyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private RemindService remindService; 
	private String parameter;
    private DtMycertificateapply cateapply;//领用申请单据
    private DtMycheck dtMycheck;//单据审核
	private PageForm pageForm;
	private String search;
	private int pageNumber;
	private String submittype;//提交类型（保存草稿或者提交审核）
	private String Cateusedate;//领用时间
	private String type;//区分部门和个人参数
	private InputStream excelStream;
	
	private String checkid;//审核id
	private String checkurl;//审核路径
	
	@Resource
	private ShowExcelService excelService;
	private String excel;
    //导出证书领用列表
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<BaseBean> list = null;
	    list = baseBeanService.getListByDC(getList());
	    excelStream = excelService.showExcel(DtMycertificateapply.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出证书领用列表", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/**
	 * 领用申请——打印单据
	 */
	public String toPrintCateApply() {
		toSee();
		return "printcate";
	}
	/**
	 * 领用申请——查看
	 */
	public String toDetailcate(){
		toSee();	
		return "tocateDetail";
	}
	/**
	 * 领用申请/审核单据——查看调用
	 * 
	 */
	private void toSee(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql="from DtMycertificateapply where companyid = ? and id=?";
		cateapply=(DtMycertificateapply) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),cateapply.getId()});
		String hqlc="from DtMycheck where id=? order by addtime desc";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				hqlc, new Object[]{cateapply.getId()});
	}
	//根据条件查询领用申请
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", cateapply);
		session.put("Cateusedate", Cateusedate);
		return getDtMycertificateapplyList();
	}
	
	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { account.getStaffID() });
		session.put("StaffName", sta.getStaffName());
		DetachedCriteria dc = DetachedCriteria.forClass(DtMycertificateapply.class);
		dc.add(Restrictions.eq("companyid", account.getCompanyID()));
		if(type.equals("org")){
			dc.add(Restrictions.eq("organizationid",organizationID));
		}else if(type.equals("my")){
			dc.add(Restrictions.eq("staffid", account.getStaffID()));
		}
		dc.addOrder(Order.desc("addtime"));
		
		if (search != null && search.equals("search")) {
			cateapply = (DtMycertificateapply) session.get("tablesearch");
			String Cateusedate=(String)session.get("Cateusedate");
			if (!"".equals(cateapply.getCatename())){
				dc.add(Restrictions.like("catename", cateapply.getCatename(), MatchMode.ANYWHERE));
			}
			if (!"".equals(cateapply.getCateusername())){
				dc.add(Restrictions.like("cateusername",cateapply.getCateusername(),MatchMode.ANYWHERE));
			}
			if (!Cateusedate.equals("")){
				SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
					Date  ttt= matter1.parse(Cateusedate);
					dc.add(Restrictions.eq("cateusedate",ttt));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
	    }
		return dc;
	}
	// 领用证书单据列表
	public String getDtMycertificateapplyList() {
	   pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
	   return "catelist";	
	}
	//领用证书提交审核
		public String tocheck() throws ParseException{
			ActionContext actionContext = ActionContext.getContext();
			Map<String, Object> session = actionContext.getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID=(String) session.get("organizationID");
			DtMycertificateapply dtcate=new DtMycertificateapply();
			if (cateapply.getId()!= null|| !"".equals(cateapply.getId())) {
				 String hql1="from DtMycertificateapply where id=?";
				 dtcate=(DtMycertificateapply) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{cateapply.getId()});
			     parameter = "提交审核(领用证书单据编号:"+dtcate.getSerialNumber()+")";
			}
			List<BaseBean> beans=new ArrayList<BaseBean>();
			dtcate.setStatus("01");
			beans.add(dtcate);
			/** 审核表添数据*/
	        DtMycheck mcheck=new DtMycheck();
			mcheck.setCheckid(serverService.getServerID("dtMycheck"));
			mcheck.setId(dtcate.getId());
			mcheck.setSerialnumber(dtcate.getSerialNumber());
			mcheck.setReceiptType("证书领用单");
			mcheck.setLookOverurl("ea/cateapply/ea_toDetailcate.jspa?cateapply.id="+dtcate.getId());
			mcheck.setPrinturl("ea/cateapply/ea_toPrintCateApply.jspa?cateapply.id="+dtcate.getId());
			mcheck.setListurl("ea/cateapply/ea_getDtMycertificateapplyList.jspa");
			mcheck.setTeablename("dt_Mycertificateapply");
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
			remind.setCircularTitle("证书领用单审核待办");
			remind.setCircularText("您个人审核有一个待办任务，请及时处理！");
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
   //添加领用证书单据
    public String saveCateApply() throws ParseException
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		if (cateapply.getId()== null|| "".equals(cateapply.getId())) {
			cateapply.setId(serverService.getServerID("cateapply"));
			parameter = "添加证书领用单据(单据编号:"+cateapply.getSerialNumber()+")";
		}
		else
		{
			 String hql1="from DtMycertificateapply where id=?";
			 DtMycertificateapply cateapp=(DtMycertificateapply) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{cateapply.getId()});
			 parameter = "修改证书领用单据(单据编号:"+cateapp.getSerialNumber()+")";
		
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
        SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tt=matter1.format(new Date());
        Date  ttt= matter1.parse(tt);
        cateapply.setAddtime(ttt);
        SimpleDateFormat matter=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date datee = matter.parse(Cateusedate);
        cateapply.setCateusedate(datee);
		String hql2 = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { account.getStaffID() });
		cateapply.setStaffid(sta.getStaffID());
		cateapply.setStaffname(sta.getStaffName());
		cateapply.setCateuserid(sta.getStaffID());
		cateapply.setCateusername(sta.getStaffName());
		cateapply.setCompanyid(account.getCompanyID());
		cateapply.setCompanyname(account.getCompanyName());
		//当前登录人员的部门,id/name,以及岗位
		String sql = "select c.organizationid,c.organizationname,d.postname"
			+ " from dtcos t"
			+ " left join dtcorganization c on t.organizationid = c.organizationid"
			+ " left join dt_hr_deptpost d on t.deppostid = d.deppostid"
			+ " where t.companyid = ? and t.staffid = ? and t.cosstatus = '50' and t.status = '01'";
		Object[] orgpost = (Object[])baseBeanService.getObjectBySqlAndParams(sql, new Object[] {
						account.getCompanyID(),sta.getStaffID()});
		cateapply.setOrganizationid(orgpost[0].toString());
		cateapply.setOrganizationname(orgpost[1].toString());
		//orgpost[2].toString();岗位名称
		//单据状态
		if(submittype.equals("savecheck"))
		{
			cateapply.setStatus("01");
			DtMycheck mcheck=new DtMycheck();
			mcheck.setCheckid(serverService.getServerID("dtMycheck"));
			mcheck.setId(cateapply.getId());
			mcheck.setSerialnumber(cateapply.getSerialNumber());
			mcheck.setReceiptType("证书领用单");
			mcheck.setLookOverurl("ea/cateapply/ea_toDetailcate.jspa?cateapply.id="+cateapply.getId());
			mcheck.setPrinturl("ea/cateapply/ea_toPrintCateApply.jspa?cateapply.id="+cateapply.getId());
			mcheck.setListurl("ea/cateapply/ea_getDtMycertificateapplyList.jspa");
			mcheck.setTeablename("dt_Mycertificateapply");
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
			remind.setCircularTitle("证书领用单审核待办");
			remind.setCircularText("您个人审核有一个待办任务，请及时处理！");
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
		 cateapply.setStatus("00");
		}
        beans.add(cateapply);
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "success";
    }
    //删除领用证书单据
	 public String delCateApply()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID");
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {cateapply.getId()};
		    String hql2="from DtMycertificateapply where id=?";
		    DtMycertificateapply cateapp=(DtMycertificateapply) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除证书领用单据(单据编号"+cateapp.getSerialNumber()+")", account);
	    	
		    String hql="delete from DtMycertificateapply where id=?";
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
	public DtMycertificateapply getCateapply() {
		return cateapply;
	}
	public void setCateapply(DtMycertificateapply cateapply) {
		this.cateapply = cateapply;
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
	public String getCateusedate() {
		return Cateusedate;
	}
	public void setCateusedate(String cateusedate) {
		Cateusedate = cateusedate;
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

}
