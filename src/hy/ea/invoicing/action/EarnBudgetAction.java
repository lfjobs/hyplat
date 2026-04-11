package hy.ea.invoicing.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.EarnBudgetBill;
import hy.ea.bo.invoicing.EarnBudgetDetail;
import hy.ea.bo.invoicing.view.ViewInvEbd;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.opensymphony.xwork2.ActionContext;

/**
 * EarnBudgetAction 收入预算
 * @author mz
 *
 */
@Controller
@Scope("prototype")
public class EarnBudgetAction {
	private static final Logger logger = LoggerFactory.getLogger(EarnBudgetAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	@Resource
	private ServerService serverService;
	private InputStream excelStream;
	@Resource
	private ShowExcelService excelService;

	private List<BaseBean> list;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private Map<String, EarnBudgetDetail> logbookmap;
	private Map<String, EarnBudgetDetail> earnBudgetMap;

	private List<CCode> connectionlist;   //单位往来关系
	private List<CCode> codeRelationList;	//个人往来关系
	private Staff contactUser;		//往来个人
	private ContactCompany contactCompanyView;		//往来单位
	private String companyname;		//公司名称
	private String organizationname;	//部门名称
	
	private List<BaseBean> earnBudgetDetailList;
	private List<BaseBean> earnBudgetBillList;
	private EarnBudgetBill earnBudgetBill;//收入预算单子
	private EarnBudgetDetail earnBudgetDetail;
	private ViewInvEbd viewInvEbd;


	private String result ;  //Ajax 返回值
	
	private Staff staff ; //责任人
	private List<BaseBean> bankL;//公司银行帐号
	private List<BaseBean> userBankL;//个人银行帐号
	private String toSee;
	private String addType;//添加类型，是添加还是更新；
	/**
	 * 单据类型 
	 * '00' 收入预算
	 * '01' 调整预算
	 * 
	 */
	private String type;
	
	/**
	 * 区分是收入预算，支出预算
	 * 's'  收入预算
	 * 'z' 支出预算
	 */
	private String sztype;
	


	/**
	 * 责任人
	 */
	private List<BaseBean> staffList;
	/**
	 * 预算订单——列表
	 * 
	 * @return
	 */
	
	/**
	 * 仓库类别
	 */
	private List<CCode> typelist;
	
	private String comp;//用于区分完成率

    private String allfamount;
    private Map<Integer,String> mapfact;
    Map<String,Map<Integer,String>> budgetofxm;
    Map<String,Map<Integer,String>> tiaotofxm;
    Map<String,Map<Integer,String>> fxm;
    Map<String,Map<Long,String>> fam;
    Map<String,Map<Long,String>>  bttoxm;//
    List<BaseBean>  goodbilllist;
    Map<String,Map<Integer,String>> factofxm;
    Map<String,Map<Integer,String>> factofam;
    
    
    Map<Integer,String> ballmonth;
    Map<Integer,String> tallmonth;
    Map<Long,String> btallmonth;//

    Map<String,Map<Integer,Map<Long,String>>> productnummap;
    Map<String,Map<Long,Map<Long,String>>> seasonnummap;
    List<Object> productlist;
    Map<String,List<Object>> productmap;
    Map<String,Map<String,Map<Integer,Map<Long,String>>>>  detailmap;
    Map<String, Map<String,Map<Long,Map<Long,String>>>> seasonmap;
    
    Map<String, Map<Long,Map<Long,String>>> seasonxmap;
    Map<String, Map<String,Map<Integer,String>>> factmap;
    Map<Long,String> factseasonm;
    private String excel;


	@SuppressWarnings("unchecked")
	public String showExcel() {
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<BaseBean> list = null;
	    if(excel!=null&&excel.equals("detail")){
	    	list = baseBeanService.getListByDC(getListByDetail(sztype, type));
	    	excelStream = excelService.showExcel(ViewInvEbd.columnHeadings(),list);
	    }else{
	    	list = baseBeanService.getListByDC(getlist(type,sztype));
	    	excelStream = excelService.showExcel(EarnBudgetBill.columnHeadings(),list);
	    }
		
	   
		
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出收入预算单", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	
	/**
	 * 
	 * 
	 * @return
	 */
	public String earnBudgetList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		//责任人name
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
						new Object[] { account.getStaffID() });
		session.put("ManStaffName", sta.getStaffName()+"---"+sta.getStaffCode());
		session.put("ManStaffId", sta.getStaffID());
		session.put("ManStaffCode", sta.getStaffCode());
		pageForm = baseBeanService.getPageFormByDC(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber),getlist(type,sztype));
		
		String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus=? and c.organizationID=?)";
		staffList = baseBeanService.getListBeanByHqlAndParams(staffhql,
				new Object[] { account.getCompanyID(),"50", session.get("organizationID") });
		return "list";
	}
	
	
	public DetachedCriteria getlist(String type,String sztype){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(EarnBudgetBill.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("organizationID",session.get("organizationID")));
		dc.add(Restrictions.like("sztype", sztype, MatchMode.ANYWHERE));
		if(type!=null&&type.equals("01")){
			dc.add(Restrictions.eq("billStatus", type));
		}
		dc.addOrder(Order.desc("billsDate"));
		if(search!=null&&!search.equals("")){
			earnBudgetBill = (EarnBudgetBill) session.get("earnBudgetBill");
			if(earnBudgetBill.getBillNum()!=null&&!earnBudgetBill.getBillNum().equals("")){
				dc.add(Restrictions.like("billNum", earnBudgetBill.getBillNum().trim(), MatchMode.ANYWHERE));
			}
			if(earnBudgetBill.getBillStaffName()!=null&&!earnBudgetBill.getBillStaffName().equals("")){
				dc.add(Restrictions.like("billStaffName", earnBudgetBill.getBillStaffName().trim(), MatchMode.ANYWHERE));
			}
			if(earnBudgetBill.getStaffID()!=null&&!earnBudgetBill.getStaffID().equals("")){
				dc.add(Restrictions.eq("staffID", earnBudgetBill.getStaffID().trim()));
			}
			Date sdated =null;
			Date edated = null;
			if(earnBudgetBill.getSdate()!=null&&!earnBudgetBill.getSdate().equals("")){
				 sdated = Utilities.getDateFromString(earnBudgetBill.getSdate(),"yyyy-MM-dd HH:mm:ss");
				if(earnBudgetBill.getEdate()!=null&&!earnBudgetBill.getEdate().equals("")){
					 edated = Utilities.getDateFromString(earnBudgetBill.getEdate(),"yyyy-MM-dd HH:mm:ss");
					
				}else{
					edated = new Date();
				}
				
				dc.add(Restrictions.between("billsDate", sdated, edated));
			}
			if(earnBudgetBill.getBillStatus()!=null&&!earnBudgetBill.getBillStatus().equals("")){
				dc.add(Restrictions.eq("billStatus", earnBudgetBill.getBillStatus().trim()));
			}
		}
		
		
		
		return dc;
	}

	/**
	 * 收入预算添加
	 * 
	 */
	public String toGetBudgetBills() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();

		//查询单位往来关系
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		//查询个人往来关系
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		
		//仓库类别
		typelist = codeService.getCCodeListByPID(companyID,
				"scode20101014v5zed7cukk0000000004");
		//责任人name
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		session.put("ManStaffName", sta.getStaffName()+"---"+sta.getStaffCode());
		session.put("ManStaffId", sta.getStaffID());
		session.put("ManStaffCode", sta.getStaffCode());
		return "add";
	}
	
	
	/**
	 * 根据companyID和codeID查询其子节点
	 */
	public String getListProductList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		
		
		List<Object> parms = new ArrayList<Object>();
		String sql="select pp.ppid,cp.companyname,co.organizationname,hs.staffname, pp.packagingdate,gm.goodscoding,pp.goodsname," +
		" gm.goodsvariable,gm.acquiescestandard,pp.quantity,pp.weight,pr.price,pr.money,pp.manualurl,pp.propagandaurl,pp.fileurl," +
		"gm.goodsid,pr.category,gm.mnemonicCode,gm.typeID,gm.model,gm.standard,gm.variableid,gm.variable1id,gm.variable2id,gm.variable3id,gm.variable4id" +
		" from dt_productpackaging pp" +
		" left join dtcompany cp on cp.companyid=pp.companyid" +
		" left join dtcorganization co on co.organizationid=pp.organizationid" +
		" left join dt_hr_staff hs on hs.staffid=pp.staffid" +
		" left join dtgoodsmanage gm on gm.goodsid=pp.goodsid " +
		" left join dt_productpricecategory pr on  pr.ppid=pp.ppid";
        sql+=" where pp.companyid=?";
        sql+=" and pr.category='零售价'";
		parms.add(account.getCompanyID());
//		sql+=" and pp.organizationid=?";
//		parms.add(session.get("organizationID"));
		if (search != null && search.equals("search")) {
			
			if(parameter!=null&&!parameter.equals("")){
				sql+=" and ( pp.goodsname like ? or gm.goodscoding like ?";
				parms.add("%"+parameter+"%");
				parms.add("%"+parameter+"%");
				sql+=" )";
			}
	
		}
		
		sql+=" order by pp.packagingdate desc";


		 pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber==0?10:pageNumber), sql, "select count(1) "
					+ sql.substring(sql.indexOf("from")), parms.toArray());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm",pageForm);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public String saveEarnBudget(){
	try{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		if (groupCompanySn == null) {
			return "login";
		}
		earnBudgetBill.setEbbID(serverService.getServerID("ebbID"));
		earnBudgetBill.setBillNum(serverService.getBillID(account
				.getCompanyID()));
		parameter = "添加收入预算单据（凭证号：" + earnBudgetBill.getBillNum() + "）";
		earnBudgetBill.setOrganizationID(organizationID);
		earnBudgetBill.setCompanyID(account.getCompanyID());
		earnBudgetBill.setCompanyName(account.getCompanyName());
		earnBudgetBill.setGroupCompanySn(groupCompanySn);
		earnBudgetBill.setBillsDate(new Date());
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		earnBudgetBill.setBillStaffName(sta.getStaffName());
		earnBudgetBill.setBillStaffID(sta.getStaffID());
		
		if(sztype.equals("s")){
			earnBudgetBill.setBillsType("收入预算单");
		}else if(sztype.equals("z")){
			earnBudgetBill.setBillsType("支出预算单");
		}
		earnBudgetBill.setSztype(sztype);
		earnBudgetBill.setBillDeptID(organizationID);
		earnBudgetBill.setBillComppanyID(account.getCompanyID());
		
		//加上制单人的部门和公司信息
		String hqlForDept = "from COrganization where organizationID = ?";
		COrganization org = (COrganization) baseBeanService.getBeanByHqlAndParams(hqlForDept, new Object[]{organizationID});
		earnBudgetBill.setBillDeptName(org.getOrganizationName());
		String hqlForCompany = "from Company where companyID = ?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(hqlForCompany, new Object[]{account.getCompanyID()});
		earnBudgetBill.setBillCompanyName(company.getCompanyName());
		
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		baseBeanList.add(earnBudgetBill);
		if (logbookmap != null) {
			for (EarnBudgetDetail detail : logbookmap.values()) {
				detail.setEbbID(earnBudgetBill.getEbbID());
				detail.setEbdID(serverService.getServerID("ebdID"));
                detail.setDelStatus("00");
                detail.setMonth(detail.getMonth().replace("0",""));
                detail.setTdamount(detail.getBdamount());
                detail.setTdquantity(detail.getBdquantity());
                detail.setTmamount(detail.getBmamount());
                detail.setTsamount(detail.getBsamount());
                detail.setTunitPrice(detail.getBunitPrice());
                detail.setTwamount(detail.getBwamount());
                detail.setTyamount(detail.getByamount());
                detail.setSztype(sztype);
				baseBeanList.add(detail);
			}
		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,
				null);
	}catch(Exception e){
		logger.error("操作异常", e);
	}
		return "success";
	}
	
	/**
	 * 
	 * 确认预算
	 * @return
	 */
	public String confirmBudget(){
		String hql = "from EarnBudgetBill where ebbID = ?";
		EarnBudgetBill ebb = (EarnBudgetBill) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{earnBudgetBill.getEbbID()});
		ebb.setBillStatus("01");
		baseBeanService.update(ebb);
		return "success";
	}
	

	
	
	
	/**
	 * 收入预算单删除
	 * @return
	 */
	
	public String deteleEarnBudgetBill(){
		String hql = "delete from EarnBudgetBill c where c.ebbID = ? ";
		String hql1 = "delete from EarnBudgetDetail d where d.ebbID = ?";
		List parmList = new ArrayList();
		parmList.add(new Object[]{earnBudgetBill.getEbbID()});
		parmList.add(new Object[]{earnBudgetBill.getEbbID()});
		
		baseBeanService.executeHqlsByParamsList(null,new String[]{hql,hql1},parmList);
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	

	
	/**
	 * 收入预算订单——条件查询(保存条件)
	 * 
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("earnBudgetBill",
				earnBudgetBill);
		return earnBudgetList();
	}
	
	

	/**
	 * 单据修改
	 * @return
	 */
	public String toEditEarnBudget(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		//查询单位往来关系
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		//查询个人往来关系
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");

		
		//仓库类别
		typelist = codeService.getCCodeListByPID(companyID,
				"scode20101014v5zed7cukk0000000004");
		//单据
		String hql = " from EarnBudgetBill where ebbID = ?";
		earnBudgetBill = (EarnBudgetBill)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{earnBudgetBill.getEbbID()} );
		//产品
		String hql0 = "";
		if(type!=null&&type.equals("01")){ //调整模块
			hql0 = "from EarnBudgetDetail where  ebbID=? and delStatus != ? order by goodsNomber";
			earnBudgetDetailList = baseBeanService.getListBeanByHqlAndParams(hql0, new Object[]{earnBudgetBill.getEbbID(),"01"});
		}else{
			hql0 = "from EarnBudgetDetail where  ebbID=? and delStatus != ? order by goodsNomber";
			earnBudgetDetailList = baseBeanService.getListBeanByHqlAndParams(hql0, new Object[]{earnBudgetBill.getEbbID(),"02"});
		}
		
		//往来个人
		String hql2 = " from Staff where staffID= ? ";
		contactUser=(Staff)baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{earnBudgetBill.getCstaffID()});
		//往来单位
		String hql3 = " from ContactCompany where ccompanyID=? ";
		contactCompanyView=(ContactCompany)baseBeanService.getBeanByHqlAndParams(hql3, new Object[]{earnBudgetBill.getCcompanyID()});
		// 责任人
		String hql1 = " from Staff s where s.staffID = ?";
		staff = (Staff)baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{earnBudgetBill.getStaffID()});
		//公司
		String comhql = "from Company where companyID=?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(
				comhql, new Object[] { earnBudgetBill.getCompanyID() });
		companyname = company.getCompanyName();
		//部门
		String orghql = "from COrganization where companyID=? and organizationID=?";
		COrganization cOrganization = (COrganization) baseBeanService
				.getBeanByHqlAndParams(orghql, new Object[] {
						account.getCompanyID(),earnBudgetBill.getOrganizationID()});
		organizationname = cOrganization.getOrganizationName();
		// 公司银行账户List
		String hql4 = "from Registration where ccompanyID= ?";
		bankL = baseBeanService.getListBeanByHqlAndParams(
				hql4, new Object[]{earnBudgetBill.getCcompanyID()});
		// 个人银行账户List
		String hql5="from StaffBankAccount where staffID= ?";
		userBankL = baseBeanService.getListBeanByHqlAndParams(
				hql5, new Object[]{earnBudgetBill.getCstaffID()});
		
		return "add";
	}
	

	/**
	 *  物品删除
	 * @return
	 */

	public String delGoodsBill(){
		if(earnBudgetBill.getBillStatus().equals("00")){
		String hql = "delete from EarnBudgetDetail d where d.ebdID = ?";
		List parmList = new ArrayList();
		parmList.add(new Object[]{earnBudgetDetail.getEbdID()});
		baseBeanService.executeHqlsByParamsList(null,new String[]{hql},parmList);
		
		}else{
			String hql2 = "from EarnBudgetDetail where ebdID = ?";
			EarnBudgetDetail ebd = (EarnBudgetDetail) baseBeanService.getBeanByHqlAndParams(hql2,new Object[]{earnBudgetDetail.getEbdID()});
			ebd.setDelStatus("01");
			baseBeanService.update(ebd);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	/**
	 * 修改 
	 * @return
	 */

	public String saveEarnBudgettoEdit(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		earnBudgetBill.setBillStaffName(sta.getStaffName());
		earnBudgetBill.setBillStaffID(sta.getStaffID());
		earnBudgetBill.setBillsDate(new Date());
		earnBudgetBill.setBillDeptID(organizationID);
		earnBudgetBill.setBillComppanyID(account.getCompanyID());
		if(sztype.equals("s")){
			earnBudgetBill.setBillsType("收入预算单");
		}else if(sztype.equals("z")){
			earnBudgetBill.setBillsType("支出预算单");
		}
		
		earnBudgetBill.setSztype(sztype);
		//加上制单人的部门和公司信息
		String hqlForDept = "from COrganization where organizationID = ?";
		COrganization org = (COrganization) baseBeanService.getBeanByHqlAndParams(hqlForDept, new Object[]{organizationID});
		earnBudgetBill.setBillDeptName(org.getOrganizationName());
		String hqlForCompany = "from Company where companyID = ?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(hqlForCompany, new Object[]{account.getCompanyID()});
		earnBudgetBill.setBillCompanyName(company.getCompanyName());

		parameter = "修改收入预算单据（凭证号：" + earnBudgetBill.getBillNum() + "）";
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		baseBeanList.add(earnBudgetBill);
		//修改原来的产品
		if (earnBudgetMap != null) {
			for (EarnBudgetDetail detail : earnBudgetMap.values()) {
				 detail.setMonth(detail.getMonth().replace("0",""));
	                detail.setTdamount(detail.getBdamount());
	                detail.setTdquantity(detail.getBdquantity());
	                detail.setTmamount(detail.getBmamount());
	                detail.setTsamount(detail.getBsamount());
	                detail.setTunitPrice(detail.getBunitPrice());
	                detail.setTwamount(detail.getBwamount());
	                detail.setTyamount(detail.getByamount());
	                detail.setSztype(sztype);
				baseBeanList.add(detail);
			}
		}
		//增加新产品
		if (logbookmap != null) {
			for (EarnBudgetDetail detail : logbookmap.values()) {
				if( detail.getEbbID() == null ){
					detail.setEbdID(serverService.getServerID("ebdID"));
				}
				detail.setEbbID(earnBudgetBill.getEbbID());
				detail.setMonth(detail.getMonth().replace("0",""));
				detail.setSztype(sztype);
				if(type!=null&&type.equals("01")){
				   detail.setDelStatus("02");//调整新增
				}else{
			       detail.setDelStatus("00");//预算新增
				}
				baseBeanList.add(detail);
			}
		}
		
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,
				null);
		
		return "success";
	}
	
	
	
	
	
	

	
	/**
	 * 预算审核——打印单据
	 */
	public String toprintcsb() {
		try{
				Map<String, Object> session = ActionContext.getContext().getSession();
				CAccount account = (CAccount) session.get("account");
				if(account==null){
					return "not_login";
				}


				String hql = " from EarnBudgetBill where ebbID = ? ";
				earnBudgetBill = (EarnBudgetBill) baseBeanService
							.getBeanByHqlAndParams(hql, new Object[]{earnBudgetBill.getEbbID()});
				
				//产品
				String hql1 = "from EarnBudgetDetail where ebbID=?";
				earnBudgetDetailList = baseBeanService.getListBeanByHqlAndParams(hql1, new Object[]{earnBudgetBill.getEbbID()});
				
			
			}catch(Exception e){
				logger.error("操作异常", e);
			}
		return "printebb";
	}
	
	
	/**
	 * 
	 * 根据部门获取收入调整单子
	 * @return
	 */
	public String getEarnBudgetByOrg(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account==null){
			return "not_login";
		}
		
		String hql = "from EarnBudgetBill where companyID = ? and organizationID = ? and billStatus = ? and sztype like ?";
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{account.getCompanyID(),session.get("organizationID"),"01","%"+sztype+"%"});
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("result", list);
		JSONObject jo=JSONObject.fromObject(map);
		this.result = jo.toString();
		
		return "success";
		
	}
	
	/**
	 * 
	 * 根据收入预算单子ID获取详细的调整后的产品
	 * 
	 * @return
	 */
	public String getProductDetails() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			return "not_login";
		}

		try {
			List<Object> paramlist = new ArrayList<Object>();
			String hql = "from EarnBudgetDetail d where exists(select ebbID from EarnBudgetBill b where d.ebbID = b.ebbID and b.companyID = ? and b.organizationID = ? and b.billStatus = ? and sztype like ?) and d.delStatus != ?";
			paramlist.add(account.getCompanyID());
			paramlist.add(session.get("organizationID"));
			paramlist.add("01");
			paramlist.add("%"+sztype+"%");
			paramlist.add("01");//删除的
			if (earnBudgetBill != null && earnBudgetBill.getEbbID() != null
					&& !earnBudgetBill.getEbbID().equals("")) {

				hql = hql + " and ebbID = ?";
				paramlist.add(earnBudgetBill.getEbbID());

			}
			if (parameter != null && !parameter.trim().equals("")) {
				hql = hql + " and productName like ? or productNum like ?";
				paramlist.add("%" + parameter + "%");
				paramlist.add("%" + parameter + "%");
			}
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql, paramlist.toArray());

		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	
	/**
	 * 
	 * 完成率页面跳转
	 * @return
	 */
	public String toCompeptePage(){
		
		return "tocompelete";
	}
	
	
	/**
	 * 
	 * 查询完成率
	 * @return
	 */
	public String  searchCompleteRate(){
		try{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			return "not_login";
		}
		//某个部门下的某一年的所有的预算项目
		String hql = "from EarnBudgetBill where organizationID = ? and year = ? and sztype = ?";
		earnBudgetBillList = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{session.get("organizationID"),earnBudgetBill.getYear().trim(),sztype});
		//某个部门下的某一年的所有的产品
		
		String sql = "select distinct d.productNum,d.productName,d.ebbID from dt_inv_ebd d,dt_inv_ebb b where b.ebbID = d.ebbID and b.organizationID = ? and b.year = ? and d.ebbID = ?";
		productmap = new HashMap<String, List<Object>>();
        
		List<BaseBean> ebdlist = null;
		
		detailmap = new HashMap<String, Map<String,Map<Integer,Map<Long,String>>>>();
		seasonmap = new HashMap<String, Map<String,Map<Long,Map<Long,String>>>>();
		String hql2= "from EarnBudgetDetail d where exists(select b.ebbID from EarnBudgetBill b where b.ebbID = d.ebbID and b.organizationID = ? and b.year = ? ) and d.productNum = ? and d.ebbID = ?";
		ballmonth = new HashMap<Integer, String>();//部门下的全部
		tallmonth = new HashMap<Integer, String>();//部门下的全部
		mapfact= new HashMap<Integer, String>();//部门下的全部
		factseasonm = new HashMap<Long, String>();
		//初始化
		for (int i = 1; i < 13; i++) {
			ballmonth.put(i, "0.0");
			tallmonth.put(i, "0.0");
			mapfact.put(i, "0.0");
		}

		
		btallmonth = new HashMap<Long, String>(); //部门季度
		
		budgetofxm = new HashMap<String,Map<Integer,String>>();
		tiaotofxm = new HashMap<String,Map<Integer,String>>();
		fxm = new HashMap<String, Map<Integer,String>>();
		seasonxmap = new HashMap<String, Map<Long,Map<Long,String>>>();
        for(BaseBean b:earnBudgetBillList){
        	productnummap = new HashMap<String, Map<Integer,Map<Long,String>>>();
        	seasonnummap = new HashMap<String, Map<Long,Map<Long,String>>>();
        	factmap = new HashMap<String, Map<String,Map<Integer,String>>>();
        	Map<String,Map<Integer,String>> factmaps = new HashMap<String,Map<Integer,String>>();
        	
        	EarnBudgetBill ebb=(EarnBudgetBill) b;
        	productlist = baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{session.get("organizationID"),earnBudgetBill.getYear().trim(),ebb.getEbbID()});
            
        	productmap.put(ebb.getEbbID(),productlist);
        	
        	
   			Map<Integer,String> budgetxm = new HashMap<Integer,String>();
   			Map<Integer,String> tiaotxm = new HashMap<Integer,String>();
   			Map<Integer,String> facttxm = new HashMap<Integer,String>();
   			Map<Long,Map<Long,String>> sesionx = new HashMap<Long,Map<Long,String>>();
   			//初始化
   			for (int i = 1; i < 13; i++) {
   				budgetxm.put(i, "0.0");
   				tiaotxm.put(i, "0.0");
   				facttxm.put(i, "0.0");
   			}

        	for (int i = 0; i < productlist.size(); i++) {
   			 Object[] product =(Object[])productlist.get(i);
   		
   			 ebdlist = baseBeanService.getListBeanByHqlAndParams(hql2,new Object[]{session.get("organizationID"),earnBudgetBill.getYear().trim(),product[0].toString(),ebb.getEbbID()});
 
   			 
   			 //为 productnummap初始化
   			Map<Long,String> strmap1 = null;
   			Map<Integer,Map<Long,String>> strmap = null;
   			strmap = new HashMap<Integer,Map<Long,String>>();
   			

   			 for (int j = 1; j < 13; j++) {
   				strmap1 =new HashMap<Long,String>();
   				
   				strmap1.put(1L, "0.0");
   				strmap1.put(2L, "0.0");
   				strmap1.put(3L, "0.0");
   				strmap1.put(4L, "0.0");
   				strmap.put(j,strmap1);
   			
   				
			}
   			for (BaseBean ss:ebdlist) {
   				strmap1 =new HashMap<Long,String>();
   				
				EarnBudgetDetail ed = (EarnBudgetDetail) ss;
				strmap1.put(1L,ed.getBmamount());
				strmap1.put(2L,ed.getTmamount());
				strmap1.put(3L,"0.0");
				strmap1.put(4L,"0.0");
				strmap.put(Integer.parseInt(ed.getMonth()),strmap1);
				float zz=0;
				if(ballmonth.get(Integer.parseInt(ed.getMonth()))!=null&&!ballmonth.get(Integer.parseInt(ed.getMonth())).equals("")){
					zz = Float.parseFloat(ballmonth.get(Integer.parseInt(ed.getMonth())));
					zz+=Float.parseFloat(ed.getBmamount());
					ballmonth.put(Integer.parseInt(ed.getMonth()),zz+"");
				}else{
					ballmonth.put(Integer.parseInt(ed.getMonth()),ed.getBmamount());
				}
				
				
				float ee=0;
				if(tallmonth.get(Integer.parseInt(ed.getMonth()))!=null&&!tallmonth.get(Integer.parseInt(ed.getMonth())).equals("")){
					ee = Float.parseFloat(tallmonth.get(Integer.parseInt(ed.getMonth())));
					ee+=Float.parseFloat(ed.getTmamount());
					tallmonth.put(Integer.parseInt(ed.getMonth()),ee+"");
				}else{
					tallmonth.put(Integer.parseInt(ed.getMonth()),ed.getTmamount());
				}
				
				
				   float yy=0;
			
					yy = Float.parseFloat(budgetxm.get(Integer.parseInt(ed.getMonth())));
					yy+=Float.parseFloat(ed.getBmamount());
					budgetxm.put(Integer.parseInt(ed.getMonth()),yy+"");
				
				
				
				float jj=0;
				if(tiaotxm.get(Integer.parseInt(ed.getMonth()))!=null&&!tiaotxm.get(Integer.parseInt(ed.getMonth())).equals("")){
					jj = Float.parseFloat(tiaotxm.get(Integer.parseInt(ed.getMonth())));
					jj+=Float.parseFloat(ed.getTmamount());
					tiaotxm.put(Integer.parseInt(ed.getMonth()),jj+"");
				}else{
					tiaotxm.put(Integer.parseInt(ed.getMonth()),ed.getTmamount());
				}
				
			}
   			
   			productnummap.put(product[0].toString(), strmap);
   			
   			
   		//实际
			//某个部门下的某一年的所有实际物品
			String hql3 = "from GoodsBills gb where gb.ebbID = ? and exists(select ebdID from EarnBudgetDetail ebd where ebd.productNum = ? and ebd.ebdID = gb.ebdID )  and exists(select ebb.ebbID from EarnBudgetBill  ebb where ebb.ebbID = gb.ebbID and ebb.organizationID = ? and ebb.companyID = ? and ebb.year = ?)";
		
			List<BaseBean> goodbilllist = baseBeanService.getListBeanByHqlAndParams(hql3, new Object[]{ebb.getEbbID(),product[0].toString(),session.get("organizationID"),account.getCompanyID(),earnBudgetBill.getYear()});
            
			
			Map<Integer,String> factmap1= new HashMap<Integer, String>();
			//初始化
			for (int j = 1; j < 13; j++) {
				factmap1.put(j, "0.0");
			}
			for(BaseBean gb:goodbilllist){
				GoodsBills goods = (GoodsBills) gb;
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(goods.getStartDate());
				int month = cal.get(Calendar.MONTH) + 1;// 获取月份

				String a =  factmap1.get(month + "");

				if (a != null && !a.equals("")) {
					float aa = (float) Float.parseFloat(a);
					float cc = aa
							+ (float) Float.parseFloat(goods.getLoan());
					factmap1.put(month, cc + "");
				} else {
					factmap1.put(month, goods.getLoan());
				}
				
				
				float gg=0;
				if(mapfact.get(month)!=null&&!mapfact.get(month).equals("")){
					gg = Float.parseFloat(tallmonth.get(month));
					gg+=Float.parseFloat(goods.getLoan());
					mapfact.put(month,gg+"");
				}else{
					mapfact.put(month,goods.getLoan());
				}
				
				
				float rr = 0;

				rr = Float.parseFloat(facttxm.get(month));
				rr += Float.parseFloat(goods.getLoan());
				facttxm.put(month, rr + "");
				

			   }
			
			  factmaps.put(product[0].toString(), factmap1);
   			
   			
   			
   			
   			
   			
   			//季度全年
   
				
   				
   			float sb1 = 0,st1 = 0;
   			float sb2 = 0,st2 = 0;
   			float sb3 = 0,st3 = 0;
   			float sb4 = 0,st4 = 0;
   			float sb5 = 0,st5 = 0;
   			for (BaseBean ss:ebdlist) {

   				
				EarnBudgetDetail ed = (EarnBudgetDetail) ss;
				if(Integer.parseInt(ed.getMonth())<=3){
					sb1+=Float.parseFloat(ed.getBmamount());
					st1+=Float.parseFloat(ed.getTmamount());
				}else if(Integer.parseInt(ed.getMonth())<=6){
					sb2+=Float.parseFloat(ed.getBmamount());
					st2+=Float.parseFloat(ed.getTmamount());
				}else if(Integer.parseInt(ed.getMonth())<=9){
					sb3+=Float.parseFloat(ed.getBmamount());
					st3+=Float.parseFloat(ed.getTmamount());
				}else if(Integer.parseInt(ed.getMonth())<=10){
					sb4+=Float.parseFloat(ed.getBmamount());
					st4+=Float.parseFloat(ed.getTmamount());
				}
				
				
              }
			sb5+=sb1+sb2+sb3+sb4;
			st5+=st1+st2+st3+st4;
			HashMap<Long,Map<Long,String>> s = new HashMap<Long,Map<Long,String>>();
			Map<Integer,String> fact = factmaps.get(product[0].toString());
			float sf1 = Float.parseFloat(fact.get(1))+Float.parseFloat(fact.get(2))+Float.parseFloat(fact.get(3));
			float sf2 = Float.parseFloat(fact.get(4))+Float.parseFloat(fact.get(5))+Float.parseFloat(fact.get(6));
			float sf3 = Float.parseFloat(fact.get(7))+Float.parseFloat(fact.get(8))+Float.parseFloat(fact.get(9));
			float sf4 = Float.parseFloat(fact.get(10))+Float.parseFloat(fact.get(11))+Float.parseFloat(fact.get(12));
			float sf5 = sf1+sf2+sf3+sf4;
			for (long k = 1L; k < 6L; k++) {
			Map<Long, String>	strmap2= new HashMap<Long, String>();
				if(k==1L){
				strmap2.put(1L, sb1+"");
				strmap2.put(2L, st1+"");
				strmap2.put(3L, sf1+"");
				strmap2.put(4L, "预算完成率");
				}
				if(k==2L){
					strmap2.put(1L, sb2+"");
					strmap2.put(2L, st2+"");
					strmap2.put(3L, sf2+"");
					strmap2.put(4L, "预算完成率");
					}
				
				if(k==3L){
					strmap2.put(1L, sb3+"");
					strmap2.put(2L, st3+"");
					strmap2.put(3L, sf3+"");
					strmap2.put(4L, "预算完成率");
					}
				
				if(k==4L){
					strmap2.put(1L, sb4+"");
					strmap2.put(2L, st4+"");
					strmap2.put(3L, sf4+"");
					strmap2.put(4L, "预算完成率");
					}
				if (k == 5L) {
							strmap2.put(1L, sb5 + "");
							strmap2.put(2L, st5 + "");
							strmap2.put(3L,sf5+"");
							strmap2.put(4L, "预算完成率");
						}
				s.put(k, strmap2);
		
			}
			
			seasonnummap.put(product[0].toString(), s);
			
			
			
			
   		    }
        	
        	detailmap.put(ebb.getEbbID(), productnummap);
        	seasonmap.put(ebb.getEbbID(), seasonnummap);	
        	factmap.put(ebb.getEbbID(), factmaps);
        	
        	budgetofxm.put(ebb.getEbbID(), budgetxm);
        	tiaotofxm.put(ebb.getEbbID(), tiaotxm);
        	fxm.put(ebb.getEbbID(), facttxm);
        	
        	
        	//项目季度
        	float xb1,xt1,xf1;
        	float xb2,xt2,xf2;
        	float xb3,xt3,xf3;
        	float xb4,xt4,xf4;
        	float xb5,xt5,xf5;
            
        	xb1 = Float.parseFloat(budgetxm.get(1))+Float.parseFloat(budgetxm.get(2))+Float.parseFloat(budgetxm.get(3));
          	xb2 = Float.parseFloat(budgetxm.get(4))+Float.parseFloat(budgetxm.get(5))+Float.parseFloat(budgetxm.get(6));
          	xb3 = Float.parseFloat(budgetxm.get(7))+Float.parseFloat(budgetxm.get(8))+Float.parseFloat(budgetxm.get(9));
          	xb4 = Float.parseFloat(budgetxm.get(10))+Float.parseFloat(budgetxm.get(11))+Float.parseFloat(budgetxm.get(12));
            xb5 = xb1+xb2+xb3+xb4;
          	
          	xt1 = Float.parseFloat(tiaotxm.get(1))+Float.parseFloat(tiaotxm.get(2))+Float.parseFloat(tiaotxm.get(3));
          	xt2 = Float.parseFloat(tiaotxm.get(4))+Float.parseFloat(tiaotxm.get(5))+Float.parseFloat(tiaotxm.get(6));
          	xt3 = Float.parseFloat(tiaotxm.get(5))+Float.parseFloat(tiaotxm.get(8))+Float.parseFloat(tiaotxm.get(9));
          	xt4 = Float.parseFloat(tiaotxm.get(10))+Float.parseFloat(tiaotxm.get(11))+Float.parseFloat(tiaotxm.get(12));
            xt5 = xt1+xt2+xt3+xt4;
            
        	xf1 = Float.parseFloat(facttxm.get(1))+Float.parseFloat(facttxm.get(2))+Float.parseFloat(facttxm.get(3));
          	xf2 = Float.parseFloat(facttxm.get(4))+Float.parseFloat(facttxm.get(5))+Float.parseFloat(facttxm.get(6));
          	xf3 = Float.parseFloat(facttxm.get(5))+Float.parseFloat(facttxm.get(8))+Float.parseFloat(facttxm.get(9));
          	xf4 = Float.parseFloat(facttxm.get(10))+Float.parseFloat(facttxm.get(11))+Float.parseFloat(facttxm.get(12));
            xf5 = xf1+xf2+xf3+xf4;
            Map<Long,String> xs = null;
            for(long x = 1L;x<6L;x++){
            	xs = new HashMap<Long, String>();
            	if(x==1L){
            		 xs.put(1L, xb1+"");
            		 xs.put(2L, xt1+"");
            		 xs.put(3L, xf1+"");
            		 xs.put(4L, "预算完成率");
    				}
    				if(x==2L){
    					xs.put(1L, xb2+"");
    					xs.put(2L, xt2+"");
    					xs.put(3L, xf2+"");
    					xs.put(4L, "预算完成率");
    					}
    				
    				if(x==3L){
    					xs.put(1L, xb3+"");
    					xs.put(2L, xt3+"");
    					xs.put(3L, xf3+"");
    					xs.put(4L, "预算完成率");
    					}
    				
    				if(x==4L){
    					xs.put(1L, xb4+"");
    					xs.put(2L, xt4+"");
    					xs.put(3L, xf4+"");
    					xs.put(4L, "预算完成率");
    					}
    				if (x == 5L) {
    							xs.put(1L, xb5 + "");
    							xs.put(2L, xt5 + "");
    							xs.put(3L,xf5+"");
    							xs.put(4L, "预算完成率");
    						}
    				sesionx.put(x, xs);
            }
            
            
            seasonxmap.put(ebb.getEbbID(), sesionx);
            
        
        }
        //的季部门季度全年
        float ob1 = 0,ot1=0;
        float ob2 = 0,ot2=0;
        float ob3 = 0,ot3=0;
        float ob4 = 0,ot4=0;
        float ob5 = 0,ot5=0;
        ob1 = Float.parseFloat(ballmonth.get(1))+Float.parseFloat(ballmonth.get(2))+Float.parseFloat(ballmonth.get(3));
        ob2 = Float.parseFloat(ballmonth.get(4))+Float.parseFloat(ballmonth.get(5))+Float.parseFloat(ballmonth.get(6));
        ob3 = Float.parseFloat(ballmonth.get(7))+Float.parseFloat(ballmonth.get(8))+Float.parseFloat(ballmonth.get(9));
        ob4 = Float.parseFloat(ballmonth.get(10))+Float.parseFloat(ballmonth.get(11))+Float.parseFloat(ballmonth.get(12));
        ob5 = ob1+ob2+ob3+ob4;
        
        ot1 = Float.parseFloat(tallmonth.get(1))+Float.parseFloat(tallmonth.get(2))+Float.parseFloat(tallmonth.get(3));
        ot2 = Float.parseFloat(tallmonth.get(4))+Float.parseFloat(tallmonth.get(5))+Float.parseFloat(tallmonth.get(6));
        ot3 = Float.parseFloat(tallmonth.get(7))+Float.parseFloat(tallmonth.get(8))+Float.parseFloat(tallmonth.get(9));
        ot4 = Float.parseFloat(tallmonth.get(10))+Float.parseFloat(tallmonth.get(11))+Float.parseFloat(tallmonth.get(12));
        ot5 = ot1+ot2+ot3+ot4;
        
        
        btallmonth.put(1L, ob1+"");
        btallmonth.put(2L, ob2+"");
        btallmonth.put(3L, ob3+"");
        btallmonth.put(4L, ob4+"");
        btallmonth.put(5L, ob5+"");
        
        btallmonth.put(6L, ot1+"");
        btallmonth.put(7L, ot2+"");
        btallmonth.put(8L, ot3+"");
        btallmonth.put(9L, ot4+"");
        btallmonth.put(10L, ot5+"");
        
        
        //实际的季度全年
        float fs1 = 0;
        float fs2 = 0;
        float fs3 = 0;
        float fs4 = 0;
        float fs5 = 0;
        fs1 = Float.parseFloat(mapfact.get(1))+Float.parseFloat(mapfact.get(2))+Float.parseFloat(mapfact.get(3));
        fs2 = Float.parseFloat(mapfact.get(4))+Float.parseFloat(mapfact.get(5))+Float.parseFloat(mapfact.get(6));
        fs3 = Float.parseFloat(mapfact.get(7))+Float.parseFloat(mapfact.get(8))+Float.parseFloat(mapfact.get(9));
        fs4 = Float.parseFloat(mapfact.get(10))+Float.parseFloat(mapfact.get(11))+Float.parseFloat(mapfact.get(12));
        fs5 =fs1+fs2+fs3+fs4;
        factseasonm.put(1L, fs1+"");
        factseasonm.put(2L, fs2+"");
        factseasonm.put(3L, fs3+"");
        factseasonm.put(4L, fs4+"");
        factseasonm.put(5L, fs5+"");
        
      
		
		}catch(Exception e){
			logger.error("操作异常", e);
		}
		
		return comp;
	}
	
	
	/**
	 * 
	 * 查询完成率
	 * @return
	 */
	public String  searchCompleteRate1(){
		try{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			return "not_login";
		}
		//某个部门下的某一年的所有的预算项目
		String hql = "from EarnBudgetBill where organizationID = ? and year = ?";
		earnBudgetBillList = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{session.get("organizationID"),earnBudgetBill.getYear().trim()});
		//某个部门下的某一年的所有的产品
		String hql1 = "from EarnBudgetDetail d where exists(select b.ebbID from EarnBudgetBill b where b.ebbID = d.ebbID and b.organizationID = ? and year = ? )";
		earnBudgetDetailList = baseBeanService.getListBeanByHqlAndParams(hql1,new Object[]{session.get("organizationID"),earnBudgetBill.getYear().trim()});
		budgetofxm = new HashMap<String,Map<Integer,String>>();
		tiaotofxm = new HashMap<String,Map<Integer,String>>();
		bttoxm = new HashMap<String,Map<Long,String>>();
		for(BaseBean bb:earnBudgetBillList){
			Map<Integer,String> budgetofxl = new HashMap<Integer,String>();
			Map<Integer,String> tiaotofxl = new HashMap<Integer,String>();
			EarnBudgetBill ebb = (EarnBudgetBill) bb;

			for(BaseBean cc:earnBudgetDetailList){
				
				EarnBudgetDetail ebd = (EarnBudgetDetail) cc;
				if(ebb.getEbbID().equals(ebd.getEbbID())){
					if(ebd.getBmamount()!=null){
						 float gg = 0;
						if(budgetofxl.get(ebd.getMonth())!=null){
							float ss = Float.parseFloat(budgetofxl.get(ebd.getMonth()));
							gg += ss+Float.parseFloat(ebd.getBmamount());
						}else{
							gg = Float.parseFloat(ebd.getBmamount());
						}
						logger.info("值：{}", gg);
						budgetofxl.put(Integer.parseInt(ebd.getMonth()),gg+"");
					}
					if(ebd.getTmamount()!=null){
						 float hh = 0;
							if(tiaotofxl.get(ebd.getMonth())!=null){
								float jj = Float.parseFloat(tiaotofxl.get(ebd.getMonth()));
								hh += jj+Float.parseFloat(ebd.getTmamount());
							}else{
								hh = Float.parseFloat(ebd.getTmamount());
							}
							logger.info("值：{}", hh);
							tiaotofxl.put(Integer.parseInt(ebd.getMonth()),hh+"");
					}
				}
				
			}
			tiaotofxm.put(ebb.getEbbID(),tiaotofxl);
			budgetofxm.put(ebb.getEbbID(),budgetofxl);
		    Map<Long,String> tz = new HashMap<Long, String>();

		    Map<Integer,String> bm = budgetofxm.get(ebb.getEbbID());
	    	Map<Integer,String> tm = tiaotofxm.get(ebb.getEbbID());
		    for (int i = 1; i < 13; i++) {
		    	
		    	if(bm.get(i)==null){
		    		bm.put(i, 0+"");
		    	}
                 if(tm.get(i)==null){
                	tm.put(i, 0+"");
		    	}
			}
		    tiaotofxm.put(ebb.getEbbID(),bm);
			budgetofxm.put(ebb.getEbbID(),tm);
		    
		    tz.put(1L, String.valueOf(Float.parseFloat(budgetofxl.get(1))+Float.parseFloat(budgetofxl.get(2))+Float.parseFloat(budgetofxl.get(3))));
		    tz.put(2L, String.valueOf(Float.parseFloat(budgetofxl.get(4))+Float.parseFloat(budgetofxl.get(5))+Float.parseFloat(budgetofxl.get(6))));
		    tz.put(3L, String.valueOf(Float.parseFloat(budgetofxl.get(7))+Float.parseFloat(budgetofxl.get(8))+Float.parseFloat(budgetofxl.get(9))));
		    tz.put(4L, String.valueOf(Float.parseFloat(budgetofxl.get(10))+Float.parseFloat(budgetofxl.get(11))+Float.parseFloat(budgetofxl.get(12))));
		    
		    tz.put(5L, String.valueOf(Float.parseFloat(tiaotofxl.get(1))+Float.parseFloat(tiaotofxl.get(2))+Float.parseFloat(tiaotofxl.get(3))));
		    tz.put(6L, String.valueOf(Float.parseFloat(tiaotofxl.get(4))+Float.parseFloat(tiaotofxl.get(5))+Float.parseFloat(tiaotofxl.get(6))));
		    tz.put(7L, String.valueOf(Float.parseFloat(tiaotofxl.get(7))+Float.parseFloat(tiaotofxl.get(8))+Float.parseFloat(tiaotofxl.get(9))));
		    tz.put(8L, String.valueOf(Float.parseFloat(tiaotofxl.get(10))+Float.parseFloat(tiaotofxl.get(11))+Float.parseFloat(tiaotofxl.get(12))));
		    
			
			float t = 0;
			float z = 0;
			for(int i=1;i<13;i++){
				if(budgetofxl.get(i)!=null&&!budgetofxl.get(i).equals("")){
				    t+= Float.parseFloat(budgetofxl.get(i));
				}
				if(tiaotofxl.get(i)!=null&&!tiaotofxl.get(i).equals("")){
					z+= Float.parseFloat(tiaotofxl.get(i));
				}
			}
			tz.put(10L, t+"");
			tz.put(11L, z+"");
		    bttoxm.put(ebb.getEbbID(),tz);
//		     logger.info("调试信息");
			
			
		}
		
		
		 mapfact= new HashMap<Integer, String>();
		 ballmonth = new HashMap<Integer, String>();
		 tallmonth = new HashMap<Integer, String>();
		 btallmonth =new HashMap<Long, String>();
		for(BaseBean b:earnBudgetDetailList){
			EarnBudgetDetail ebd = (EarnBudgetDetail) b;
			if(ebd.getBmamount()!=null){
				float zz;
				if(ballmonth.get(ebd.getMonth())!=null){
				float dd = Float.parseFloat(ballmonth.get(ebd.getMonth()));
				     zz = dd+Float.parseFloat(ebd.getBmamount());
				}else{
			        zz = Float.parseFloat(ebd.getBmamount());	
				}
				ballmonth.put((int)Integer.parseInt(ebd.getMonth()), zz+"");
				
			}
		    if(ebd.getTmamount()!=null){
		    	float zz;
				if(tallmonth.get(ebd.getMonth())!=null){
				float dd = Float.parseFloat(tallmonth.get(ebd.getMonth()));
				     zz = dd+Float.parseFloat(ebd.getTmamount());
				}else{
			        zz = Float.parseFloat(ebd.getTmamount());	
				}
				tallmonth.put((int)Integer.parseInt(ebd.getMonth()), zz+"");
		    }
		    
		}
		float allbmonth = 0;
		float alltmonth = 0;
		for(int i=1;i<13;i++){
			if(ballmonth.get(i)!=null&&!ballmonth.get(i).equals("")){
			    allbmonth+= Float.parseFloat(ballmonth.get(i));
			}
			if(tallmonth.get(i)!=null&&!tallmonth.get(i).equals("")){
				alltmonth+= Float.parseFloat(tallmonth.get(i));
			}
		}
		
		

		btallmonth.put(21L,allbmonth+"");
		btallmonth.put(22L,allbmonth+"");
		
		
		
		    for (int i = 1; i < 13; i++) {
		    	
		    	if(ballmonth.get(i)==null){
		    		ballmonth.put(i, 0+"");
		    	}
		    	
		    	if(tallmonth.get(i)==null){
		    		tallmonth.put(i, 0+"");
		    	}
            
			}
		
		btallmonth.put(13L,String.valueOf(Float.parseFloat(ballmonth.get(1))+Float.parseFloat(ballmonth.get(2))+Float.parseFloat(ballmonth.get(3))));
		btallmonth.put(14L,String.valueOf(Float.parseFloat(ballmonth.get(4))+Float.parseFloat(ballmonth.get(5))+Float.parseFloat(ballmonth.get(6))));
		btallmonth.put(15L,String.valueOf(Float.parseFloat(ballmonth.get(7))+Float.parseFloat(ballmonth.get(8))+Float.parseFloat(ballmonth.get(9))));
		btallmonth.put(16L,String.valueOf(Float.parseFloat(ballmonth.get(10))+Float.parseFloat(ballmonth.get(11))+Float.parseFloat(ballmonth.get(12))));
		btallmonth.put(17L,String.valueOf(Float.parseFloat(tallmonth.get(1))+Float.parseFloat(tallmonth.get(2))+Float.parseFloat(tallmonth.get(3))));
		btallmonth.put(18L,String.valueOf(Float.parseFloat(tallmonth.get(4))+Float.parseFloat(tallmonth.get(5))+Float.parseFloat(tallmonth.get(6))));
		btallmonth.put(19L,String.valueOf(Float.parseFloat(tallmonth.get(7))+Float.parseFloat(tallmonth.get(8))+Float.parseFloat(tallmonth.get(9))));
		btallmonth.put(20L,String.valueOf(Float.parseFloat(tallmonth.get(10))+Float.parseFloat(tallmonth.get(11))+Float.parseFloat(tallmonth.get(12))));

		//某个部门下的某一年的所有实际物品
		String hql2 = "from GoodsBills gb where exists(select ebdID from EarnBudgetDetail ebd where exists(select ebb.ebbID from EarnBudgetBill  ebb where ebb.ebbID = ebd.ebbID and organizationID = ? and companyID = ? and year = ? ) and ebd.ebdID = gb.ebdID)";
	

		goodbilllist = baseBeanService.getListBeanByHqlAndParams(hql2, new Object[]{session.get("organizationID"),account.getCompanyID(),earnBudgetBill.getYear()});
	    
		//实际的总的每月的
		for (BaseBean b : goodbilllist) {
			GoodsBills goods = (GoodsBills) b;

				Calendar cal = Calendar.getInstance();
				cal.setTime(goods.getStartDate());
				int month = cal.get(Calendar.MONTH) + 1;// 获取月份

				String a =  mapfact.get(month + "");

				if (a != null && !a.equals("")) {
					float aa = (float) Float.parseFloat(a);
					float cc = aa
							+ (float) Float.parseFloat(goods.getLoan());
					 mapfact.put(month, cc + "");
				} else {
					 mapfact.put(month, goods.getLoan());
				}

		}

		factofxm = new HashMap<String, Map<Integer,String>>();
		for (BaseBean ee : earnBudgetDetailList) {

			EarnBudgetDetail ebd = (EarnBudgetDetail) ee;
			Map<Integer, String> mm = new HashMap<Integer, String>();
			for (BaseBean b : goodbilllist) {
				GoodsBills goods = (GoodsBills) b;

				if (ebd.getEbdID().equals(goods.getEbdID())) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(goods.getStartDate());
					int month = cal.get(Calendar.MONTH) + 1;// 获取月份

					String a = mm.get(month + "");

					if (a != null && !a.equals("")) {
						float aa = (float) Float.parseFloat(a);
						float cc = aa
								+ (float) Float.parseFloat(goods.getLoan());
						mm.put(month, cc + "");
					} else {
						mm.put(month, goods.getLoan());
					}
                    
					
				}
				
				

			}
			factofxm.put(ebd.getEbdID(),mm);
			
		}
		
		factofam = new HashMap<String, Map<Integer,String>>();
		for(BaseBean zz:earnBudgetBillList){
			EarnBudgetBill ebb = (EarnBudgetBill) zz;

			Map<Integer, String> mm = new HashMap<Integer, String>();
			for (BaseBean b : goodbilllist) {
				GoodsBills goods = (GoodsBills) b;

				if (ebb.getEbbID().equals(goods.getEbbID())) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(goods.getStartDate());
					int month = cal.get(Calendar.MONTH) + 1;// 获取月份

					String a = mm.get(month + "");

					if (a != null && !a.equals("")) {
						float aa = (float) Float.parseFloat(a);
						float cc = aa
								+ (float) Float.parseFloat(goods.getLoan());
						mm.put(month, cc + "");
					} else {
						mm.put(month, goods.getLoan());
					}
					

				}
				
				

			}
			factofam.put(ebb.getEbbID(),mm);
			
	
	     
		}
		

		
		}catch(Exception e){
			logger.error("操作异常", e);
		}
		
		return comp;
	}
	
	
	
	/**
	 * 
	 * 获取明细
	 * @return
	 */
    public String getEarnBudgetDetails() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				getListByDetail(sztype, type));

		String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus=? and c.organizationID=?)";
		staffList = baseBeanService.getListBeanByHqlAndParams(staffhql,
				new Object[] { account.getCompanyID(), "50",
						session.get("organizationID") });
		return "detailist";
	}
    
    
    public DetachedCriteria getListByDetail(String sztype,String type){
        Map<String,Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
    	DetachedCriteria dc = DetachedCriteria.forClass(ViewInvEbd.class);	
    	dc.add(Restrictions.like("sztype", sztype, MatchMode.ANYWHERE));
   	    dc.add(Restrictions.eq("companyid",account.getCompanyID()));
     	dc.add(Restrictions.eq("organizationid", (String)session.get("organizationID")));
     
     	if(type.equals("00")){
     		dc.add(Restrictions.eq("delstatus", "00"));
     	}else{
     		dc.add(Restrictions.not(Restrictions.eq("delstatus","01")));
     		dc.add(Restrictions.eq("billstatus",type));
     	}
     	
    	if(search!=null&&search.equals("search")){
    		viewInvEbd  = (ViewInvEbd) session.get("searchview");
    		
    		if(viewInvEbd.getBillnum()!=null&&!viewInvEbd.getBillnum().equals("")){
    			dc.add(Restrictions.eq("billnum",viewInvEbd.getBillnum()));
    		}
            if(viewInvEbd.getStaffid()!=null&&!viewInvEbd.getStaffid().equals("")){
    			dc.add(Restrictions.eq("staffid", viewInvEbd.getStaffid()));
            	
    		}
            Date sdated = null;
            Date edated = null;

            if(viewInvEbd.getEdate()!=null&&!viewInvEbd.getEdate().equals("")){
            	edated = Utilities.getDateFromString(viewInvEbd.getEdate(),"yyyy-MM-ss dd:mm:ss");
            }
            if(edated==null){
            	edated = new Date();
            }
            if(viewInvEbd.getSdate()!=null&&!viewInvEbd.getSdate().equals("")){
            	sdated = Utilities.getDateFromString(viewInvEbd.getSdate(),"yyyy-MM-ss dd:mm:ss");
            	dc.add(Restrictions.between("billsdate", sdated, edated));
            }
            

    	}
    	
    	return dc;
    	
    }
    
    
    public String toSearchByDetail(){
    	 Map<String,Object> session = ActionContext.getContext().getSession();
    	 session.put("searchview", viewInvEbd);
    	
    	
    	return getEarnBudgetDetails();
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

	public Map<String, EarnBudgetDetail> getLogbookmap() {
		return logbookmap;
	}

	public void setLogbookmap(Map<String, EarnBudgetDetail> logbookmap) {
		this.logbookmap = logbookmap;
	}


	public List<CCode> getConnectionlist() {
		return connectionlist;
	}

	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}

	public List<CCode> getCodeRelationList() {
		return codeRelationList;
	}

	public void setCodeRelationList(List<CCode> codeRelationList) {
		this.codeRelationList = codeRelationList;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<BaseBean> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<BaseBean> staffList) {
		this.staffList = staffList;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public Staff getContactUser() {
		return contactUser;
	}

	public void setContactUser(Staff contactUser) {
		this.contactUser = contactUser;
	}

	public ContactCompany getContactCompanyView() {
		return contactCompanyView;
	}

	public void setContactCompanyView(ContactCompany contactCompanyView) {
		this.contactCompanyView = contactCompanyView;
	}


	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}


	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}


	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public List<BaseBean> getBankL() {
		return bankL;
	}

	public void setBankL(List<BaseBean> bankL) {
		this.bankL = bankL;
	}

	public List<BaseBean> getUserBankL() {
		return userBankL;
	}

	public void setUserBankL(List<BaseBean> userBankL) {
		this.userBankL = userBankL;
	}

	public String getToSee() {
		return toSee;
	}

	public void setToSee(String toSee) {
		this.toSee = toSee;
	}



	public List<BaseBean> getList() {
		return list;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}
	public List<CCode> getTypelist() {
		return typelist;
	}


	public void setTypelist(List<CCode> typelist) {
		this.typelist = typelist;
	}


	public EarnBudgetBill getEarnBudgetBill() {
		return earnBudgetBill;
	}


	public void setEarnBudgetBill(EarnBudgetBill earnBudgetBill) {
		this.earnBudgetBill = earnBudgetBill;
	}


	public String getParameter() {
		return parameter;
	}


	public void setParameter(String parameter) {
		this.parameter = parameter;
	}


	public List<BaseBean> getEarnBudgetDetailList() {
		return earnBudgetDetailList;
	}


	public void setEarnBudgetDetailList(List<BaseBean> earnBudgetDetailList) {
		this.earnBudgetDetailList = earnBudgetDetailList;
	}


	public Map<String, EarnBudgetDetail> getEarnBudgetMap() {
		return earnBudgetMap;
	}


	public void setEarnBudgetMap(Map<String, EarnBudgetDetail> earnBudgetMap) {
		this.earnBudgetMap = earnBudgetMap;
	}


	public EarnBudgetDetail getEarnBudgetDetail() {
		return earnBudgetDetail;
	}


	public void setEarnBudgetDetail(EarnBudgetDetail earnBudgetDetail) {
		this.earnBudgetDetail = earnBudgetDetail;
	}


	public String getAddType() {
		return addType;
	}


	public void setAddType(String addType) {
		this.addType = addType;
	}
	
	public String getComp() {
		return comp;
	}


	public void setComp(String comp) {
		this.comp = comp;
	}


	public List<BaseBean> getEarnBudgetBillList() {
		return earnBudgetBillList;
	}


	public void setEarnBudgetBillList(List<BaseBean> earnBudgetBillList) {
		this.earnBudgetBillList = earnBudgetBillList;
	}


	

	public Map<Integer, String> getBallmonth() {
		return ballmonth;
	}


	public void setBallmonth(Map<Integer, String> ballmonth) {
		this.ballmonth = ballmonth;
	}


	public Map<Integer, String> getTallmonth() {
		return tallmonth;
	}


	public void setTallmonth(Map<Integer, String> tallmonth) {
		this.tallmonth = tallmonth;
	}


	public Map<Integer, String> getMapfact() {
		return mapfact;
	}


	public void setMapfact(Map<Integer, String> mapfact) {
		this.mapfact = mapfact;
	}


	public String getAllfamount() {
		return allfamount;
	}


	public void setAllfamount(String allfamount) {
		this.allfamount = allfamount;
	}


   


	public Map<String, Map<Integer, String>> getBudgetofxm() {
		return budgetofxm;
	}


	public void setBudgetofxm(Map<String, Map<Integer, String>> budgetofxm) {
		this.budgetofxm = budgetofxm;
	}


	public Map<String, Map<Integer, String>> getTiaotofxm() {
		return tiaotofxm;
	}


	public void setTiaotofxm(Map<String, Map<Integer, String>> tiaotofxm) {
		this.tiaotofxm = tiaotofxm;
	}


	public List<BaseBean> getGoodbilllist() {
		return goodbilllist;
	}


	public void setGoodbilllist(List<BaseBean> goodbilllist) {
		this.goodbilllist = goodbilllist;
	}


	public Map<String, Map<Integer, String>> getFactofxm() {
		return factofxm;
	}


	public void setFactofxm(Map<String, Map<Integer, String>> factofxm) {
		this.factofxm = factofxm;
	}


	public Map<String, Map<Integer, String>> getFactofam() {
		return factofam;
	}


	public void setFactofam(Map<String, Map<Integer, String>> factofam) {
		this.factofam = factofam;
	}


	public Map<Long, String> getBtallmonth() {
		return btallmonth;
	}


	public void setBtallmonth(Map<Long, String> btallmonth) {
		this.btallmonth = btallmonth;
	}


	public Map<String, Map<Long, String>> getBttoxm() {
		return bttoxm;
	}


	public void setBttoxm(Map<String, Map<Long, String>> bttoxm) {
		this.bttoxm = bttoxm;
	}

    public List<Object> getProductlist() {
		return productlist;
	}


	public void setProductlist(List<Object> productlist) {
		this.productlist = productlist;
	}


	public Map<String, List<Object>> getProductmap() {
		return productmap;
	}


	public void setProductmap(Map<String, List<Object>> productmap) {
		this.productmap = productmap;
	}


	public Map<String, Map<Integer, Map<Long, String>>> getProductnummap() {
		return productnummap;
	}


	public void setProductnummap(
			Map<String, Map<Integer, Map<Long, String>>> productnummap) {
		this.productnummap = productnummap;
	}


	public Map<String, Map<String, Map<Integer, Map<Long, String>>>> getDetailmap() {
		return detailmap;
	}


	public void setDetailmap(
			Map<String, Map<String, Map<Integer, Map<Long, String>>>> detailmap) {
		this.detailmap = detailmap;
	}


	public Map<String, Map<String, Map<Long, Map<Long, String>>>> getSeasonmap() {
		return seasonmap;
	}


	public void setSeasonmap(
			Map<String, Map<String, Map<Long, Map<Long, String>>>> seasonmap) {
		this.seasonmap = seasonmap;
	}


	public Map<String, Map<Long, Map<Long, String>>> getSeasonnummap() {
		return seasonnummap;
	}


	public void setSeasonnummap(
			Map<String, Map<Long, Map<Long, String>>> seasonnummap) {
		this.seasonnummap = seasonnummap;
	}


	public Map<String, Map<String, Map<Integer, String>>> getFactmap() {
		return factmap;
	}


	public void setFactmap(Map<String, Map<String, Map<Integer, String>>> factmap) {
		this.factmap = factmap;
	}


	public Map<Long, String> getFactseasonm() {
		return factseasonm;
	}


	public void setFactseasonm(Map<Long, String> factseasonm) {
		this.factseasonm = factseasonm;
	}


	public Map<String, Map<Integer, String>> getFxm() {
		return fxm;
	}


	public void setFxm(Map<String, Map<Integer, String>> fxm) {
		this.fxm = fxm;
	}


	public Map<String, Map<Long, String>> getFam() {
		return fam;
	}


	public void setFam(Map<String, Map<Long, String>> fam) {
		this.fam = fam;
	}


	public Map<String, Map<Long, Map<Long, String>>> getSeasonxmap() {
		return seasonxmap;
	}


	public void setSeasonxmap(Map<String, Map<Long, Map<Long, String>>> seasonxmap) {
		this.seasonxmap = seasonxmap;
	}


	public String getSztype() {
		return sztype;
	}


	public void setSztype(String sztype) {
		this.sztype = sztype;
	}


	public ViewInvEbd getViewInvEbd() {
		return viewInvEbd;
	}


	public void setViewInvEbd(ViewInvEbd viewInvEbd) {
		this.viewInvEbd = viewInvEbd;
	}


	public String getExcel() {
		return excel;
	}


	public void setExcel(String excel) {
		this.excel = excel;
	}



   
  
    
  
	

   
	
}
