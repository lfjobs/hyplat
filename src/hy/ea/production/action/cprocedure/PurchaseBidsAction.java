package hy.ea.production.action.cprocedure;

import com.tiantai.wfj.service.EarthIndexService;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.GoodsNum;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.finance.RelatedBill;
import hy.ea.bo.finance.productPackajiage;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.bo.production.Bidsinformation;
import hy.ea.bo.production.FieldConStor;
import hy.ea.bo.production.GoodFunction;
import hy.ea.bo.production.InviteBids;
import hy.ea.bo.production.PlanAmount;
import hy.ea.bo.production.PurchaseCheck;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;

/**
 * 采购招标整个流程
 */
@Controller
@Scope("prototype")
public class PurchaseBidsAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	private List<FieldConStor>  fieldConStors;
	@Resource
	private EarthIndexService earthIndexService;
	private InputStream excelStream;

	private PageForm pageForm;

	private String result;

	private int pageNumber;
	private String search;

	private PlanAmount plan;

	private GoodFunction goodFunction;

	private List<BaseBean> list;
	private String fiveClear;
	private String billID;
	private Staff staff;
	private String ppid;
	private CashierBills cashierBills;
	private Map<String, GoodsBills> logbookmap;
	private String parameter;
	private PurchaseCheck purchaseCheck;
	private InviteBids inviteBids;
	private String startTime;
	private String endTime;
	private Bidsinformation bidsinfo;
	private String mainpid;
	private String tradeID;//行业ID
	private String tradeName;//行业名称
	private String type;

	
	 /*************************************采购申请Start************************************************************/

	/***
	 * 
	 * 
	 * 采购预算申请
	 * 
	 * @return
	 */
	public String getPurcBugetSheetList() {

		pageForm = baseBeanService.getPageFormByDC(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), getLists());

		return "list";
	}

	private DetachedCriteria getLists() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		DetachedCriteria dc = DetachedCriteria.forClass(CashierBills.class);
		dc.add(Restrictions.eq("billsType", "生产采购单"));
		dc.add(Restrictions.eq("organizationID", (String)session.get("organizationID")));
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		if(fiveClear!=null&&!"".equals(fiveClear))
			dc.add(Restrictions.eq("fiveClear", fiveClear));
		
		dc.addOrder(Order.desc("cashierDate"));
	

		if (search != null && search.equals("search")) {
			cashierBills = (CashierBills) session.get("tablesearch");
			if (cashierBills.getProjectName() != null
					&& !cashierBills.getProjectName().equals("")) {
				dc.add(Restrictions.like("projectName", cashierBills
						.getProjectName().trim(), MatchMode.ANYWHERE));

			}
			
			if (cashierBills.getProjectCode() != null
					&& !cashierBills.getProjectCode().equals("")) {
				dc.add(Restrictions.like("projectCode", cashierBills
						.getProjectCode().trim(), MatchMode.ANYWHERE));

			}


			if (cashierBills.getJournalNum() != null
					&& !cashierBills.getJournalNum().equals("")) {
				dc.add(Restrictions.like("journalNum",
						cashierBills.getJournalNum(), MatchMode.ANYWHERE));
			}

			if (cashierBills.getStaffName() != null
					&& !cashierBills.getStaffName().equals("")) {
				dc.add(Restrictions.or(
						Restrictions.like("staffCode",
								cashierBills.getStaffCode(), MatchMode.ANYWHERE),
						Restrictions.like("staffName",
								cashierBills.getStaffName(), MatchMode.ANYWHERE)));
			}

			if (cashierBills.getInputName() != null
					&& !cashierBills.getInputName().equals("")) {
				dc.add(Restrictions.like("inputName",
						cashierBills.getInputName(), MatchMode.ANYWHERE));
			}


			if (cashierBills.getStatus() != null
					&& !cashierBills.getStatus().equals("")) {
				dc.add(Restrictions.eq("status", cashierBills.getStatus()));
			}

		}
		  return dc;
		
	}

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", cashierBills);
		return getPurcBugetSheetList();
	}

	/**
	 * 
	 * 
	 * 获取添加修改页面
	 * 
	 * @return
	 */
	public String getAddPage() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(cashierBills==null||cashierBills.getCashierBillsID()==null||cashierBills.getCashierBillsID().equals("")){
		  billID = serverService.getBillID(account.getCompanyID());
		 // 责任人name
		 String hqlForMan = "from Staff where staffID = ?";
		  staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		  return "addpage";
		}else{
			String hqlcash = "from CashierBills where cashierBillsID = ?";
			cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqlcash,new Object[]{cashierBills.getCashierBillsID()});
		    String hqlgoodb = "from GoodsBills where cashierBillsID = ?";
		    list = baseBeanService.getListBeanByHqlAndParams(hqlgoodb, new Object[]{cashierBills.getCashierBillsID()});
		   return "editpage";
		}
		
	}

	/**
	 * 
	 * 
	 * 保存和修改计划
	 * 
	 * @return
	 */
	public String savePurchaseSheet() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");


		String organizationID = (String) session.get("organizationID");
		List<String> hqls=new ArrayList<String>();
		List<Object> parms=new ArrayList<Object>();
		if (cashierBills.getCashierBillsID() == null
				|| cashierBills.getCashierBillsID().equals("")) {
			cashierBills.setCashierBillsID(serverService
					.getServerID("cashierTally"));
			
			parameter = "添加预算单据（凭证号：" + cashierBills.getJournalNum() + "）";

		} else {
			  
				// 删除原GoodsBills
				String hql = "delete from GoodsBills  where cashierBillsID = ?";
				hqls.add(hql);
				parms.add(cashierBills.getCashierBillsID());
				parameter = "修改预算单据（凭证号：" + cashierBills.getJournalNum() + "）";

		}
		cashierBills.setGroupCompanySn((String) session.get("groupCompanySn"));
		cashierBills.setCompanyID(account.getCompanyID());
		cashierBills.setCompanyName(account.getCompanyName());
		cashierBills.setCashierDate(new Date());
		cashierBills.setOrganizationID(organizationID);
		cashierBills.setDepartmentID(organizationID);
		cashierBills.setBillsType("生产采购单");
		cashierBills.setStatus("00");
		cashierBills.setZctype("cg");
		cashierBills.setFiveClear(fiveClear);
		// 制单人信息
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		cashierBills.setStaffID(account.getStaffID());
		cashierBills.setStaffName(sta.getStaffName());
		cashierBills.setStaffCode(sta.getStaffCode());
		cashierBills.setInputName(sta.getStaffName());
		cashierBills.setInputid(account.getStaffID());
		cashierBills.setInputOrganizationID(organizationID);
		
		String hqlForOrg = "from COrganization where organizationID = ?";
		COrganization org = (COrganization) baseBeanService
				.getBeanByHqlAndParams(hqlForOrg,
						new Object[] { organizationID });
		
		cashierBills.setInputOrganizationName(org.getOrganizationName());
		cashierBills.setOorgname(org.getOrganizationName());
		cashierBills.setDepartmentName(org.getOrganizationName());
		cashierBills.setInputCompanyid(account.getCompanyID());
		cashierBills.setInputCompanyName(account.getCompany().getCompanyName());

		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		baseBeanList.add(cashierBills);
        String hqlgoods = "from GoodsManage where goodsID = ?";
        GoodsManage goodsManage = null;
		if (logbookmap != null) {
			
			for (GoodsBills goodsBills : logbookmap.values()) {
				ProductPackaging pp=(ProductPackaging) baseBeanService.getBeanByHqlAndParams(
							"from ProductPackaging where ppID=?", new Object[]{goodsBills.getGoodsID()});
				    goodsManage  =  (GoodsManage) baseBeanService.getBeanByHqlAndParams(hqlgoods,new Object[]{pp.getGoodsID()});
					goodsBills.setCashierBillsID(cashierBills
							.getCashierBillsID());
					goodsBills.setGoodsBillsID(serverService
							.getServerID("goodsbills"));
					goodsBills.setGoodsID(pp.getGoodsID());
					goodsBills.setPpID(pp.getPpID());
					goodsBills.setGoodsNum(goodsManage.getGoodsCoding());
					goodsBills.setGoodsName(goodsManage.getGoodsName());
					goodsBills.setGoodsVariableID(goodsManage.getVariableID());
					goodsBills.setBoxStandard(goodsManage.getModel());
					goodsBills.setCompanyID(account.getCompanyID());
					goodsBills.setEndDate(new Date());// 入账时间
					goodsBills.setTiaoQuantity(goodsBills.getQuantity());
					goodsBills.setTiaoPrice(goodsBills.getPrice());
					goodsBills.setTiaoMoney(goodsBills.getMoney());
					baseBeanList.add(goodsBills);
			
			}

		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, hqls.toArray(new String[]{}), parms.toArray());

		return Action.SUCCESS;
	}
	
	
	/**
	 * 
	 * 根据产品获取子产品以及本身
	 * @return
	 */
	public String findSubProducts(){
		String sql = "select t.ppID,t.goodsID,t.goodsNum,t.goodsName,t.model,t.variableID,t.price from dt_ProductPackaging t  connect by nocycle prior t.ppID = t.parentId start with t.ppID = ?";

		List<BaseBean> sublist  = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{ppid});
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sublist", sublist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
		
		
	}

	/**
	 * 
	 * 删除生产采购单
	 * 
	 * @return
	 */
	public String deletePursheet() {
		String hql = "delete from CashierBills c where c.cashierBillsID = ? ";
		String hql1 = "delete from GoodsBills d where d.cashierBillsID = ?";
		List<Object[]> parmList = new ArrayList<Object[]>();
		parmList.add(new Object[] { cashierBills.getCashierBillsID() });
		parmList.add(new Object[] { cashierBills.getCashierBillsID() });
		baseBeanService.executeHqlsByParamsList(null,
				new String[] { hql, hql1 }, parmList);

		return Action.SUCCESS;
	}

	/**
	 * 
	 * 导出
	 * 
	 * @return
	 */
	public String showExcel() {
		List<BaseBean> list = baseBeanService.getListByDC(getLists());
		excelStream = excelService.showExcel(cashierBills.columnHeadings(),
				list);

		return "showexcel";

	}

	/**
	 * 
	 * 打印按照查询结构打印
	 * 
	 * @return
	 */
	public String printPrev() {
		String hqlcash = "from CashierBills where cashierBillsID = ?";
		cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqlcash,new Object[]{cashierBills.getCashierBillsID()});
	    String hqlgoodb = "from GoodsBills where cashierBillsID = ?";
	    list = baseBeanService.getListBeanByHqlAndParams(hqlgoodb, new Object[]{cashierBills.getCashierBillsID()});

	    String hqlcheck = "from PurchaseCheck where cashierBillsID = ?";
	    purchaseCheck = (PurchaseCheck) baseBeanService.getBeanByHqlAndParams(hqlcheck,new Object[]{cashierBills.getCashierBillsID()}); 
		return "printprev";
	}
	
	/**
	 * 
	 * 
	 * 提交审核
	 * @return
	 */
     public String submitExamine(){

	     String hql = "from CashierBills where cashierBillsID = ?";
	     CashierBills cash = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{cashierBills.getCashierBillsID()});
    	 cash.setStatus("01");//审核中
    	 List<BaseBean>  beanlist = new ArrayList<BaseBean>();
    	 PurchaseCheck pcheck = new PurchaseCheck();
    	 pcheck.setPcId(serverService.getServerID("pcid"));
    	 pcheck.setJournalNum(cash.getJournalNum());
    	 pcheck.setCashierBillsID(cash.getCashierBillsID());
    	 pcheck.setBillsType(cash.getBillsType());
    	 pcheck.setApplyId(cash.getStaffID());
    	 pcheck.setApplyor(cash.getStaffName());
    	 pcheck.setComapplyName(cash.getCompanyName());
    	 pcheck.setComapplyId(cash.getCompanyID());
    	 pcheck.setOrgapplyId(cash.getOrganizationID());
    	 pcheck.setOrgapplyName(cash.getDepartmentName());
    	 pcheck.setApplyTime(new Date());
    	 pcheck.setType(type);
    	 pcheck.setOrganizationId(cash.getOrganizationID());
    	 pcheck.setOrganizationName(cash.getDepartmentName());
    	 pcheck.setCompanyId(cash.getCompanyID());
    	 pcheck.setCompanyName(cash.getCompanyName());
    	 
    	 pcheck.setStatus("00");
    	 pcheck.setFiveClear(fiveClear);
    	 beanlist.add(pcheck);
    	 beanlist.add(cash);
    	 
    	 baseBeanService.saveBeansListAndexecuteHqlsByParams(beanlist, null,null);
    	 
    	 return "success";
	   
     }
     
     
     /*************************************采购申请End************************************************************/

     
     /*************************************采购审核start************************************************************/
    
     /**
      * 
      * 
      * 审核生产采购单
      * @return
      */
     public String examinePurchaseSheet(){
    	 Map<String, Object> session = ActionContext.getContext().getSession();
 		 CAccount account = (CAccount) session.get("account");
 		 List<BaseBean>  beanlist = new ArrayList<BaseBean>();
 		 String hqlcash="from CashierBills where cashierBillsID = ?";
    	 String hql = "from PurchaseCheck where pcId = ?";
    	 PurchaseCheck pcheck = (PurchaseCheck) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{purchaseCheck.getPcId()});

    	 String hqlstaff = "from Staff where staffID = ?";
    	 Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff, new Object[]{account.getStaffID()});
    	 pcheck.setAuditorId(account.getStaffID());
    	 pcheck.setAuditor(staff.getStaffName());
    	 pcheck.setStatus(purchaseCheck.getStatus());
    	 pcheck.setDcheckTime(new Date());
    	 pcheck.setAuditoroption(purchaseCheck.getAuditoroption());
    	 beanlist.add(pcheck);
    	
    	 CashierBills cash = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqlcash, new Object[]{purchaseCheck.getCashierBillsID()});
    	 cash.setStatus("01");
    	 beanlist.add(cash);
    	 
    	 //若通过审核，进入发布招标
    	 if(purchaseCheck.getStatus()!=null&&purchaseCheck.getStatus().equals("01")){
    		 InviteBids inviteBids = new InviteBids();
    		 inviteBids.setIbId(serverService.getServerID("ibid"));
    		 inviteBids.setStaffID(cash.getStaffID());
             inviteBids.setStaffName(cash.getStaffName());
             inviteBids.setInputID(cash.getInputid());
             inviteBids.setInputName(cash.getInputName());
             inviteBids.setOrganizationId(cash.getOrganizationID());
             inviteBids.setOrganizationName(cash.getOorgname());
             inviteBids.setCompanyId(cash.getCompanyID());
             inviteBids.setCompanyName(cash.getCompanyName());
             inviteBids.setApplyPubDate(pcheck.getDcheckTime());
             inviteBids.setBillsType(cash.getBillsType());
             inviteBids.setCashierBillsID(cash.getCashierBillsID());
             inviteBids.setJournalNum(cash.getJournalNum());
             inviteBids.setStatus("00");
             inviteBids.setBidcount("0");
             inviteBids.setPublishType("00");
             inviteBids.setPpID(cash.getProID());
             inviteBids.setApplyPubDate(new Date());
             inviteBids.setFiveClear(fiveClear);
             inviteBids.setType(pcheck.getType());
             beanlist.add(inviteBids);
    	 }
    	 
    	 baseBeanService.saveBeansListAndexecuteHqlsByParams(beanlist, null, null);
    	 return "success";
     }
     
     
     /**
      * 
      * 获取采购申请审核列表
      * @return
      */
     public String findAuditPurSheetList() {

 		pageForm = baseBeanService.getPageFormByDC(
 				(null != pageForm ? pageForm.getPageNumber() : 1),
 				(pageNumber == 0 ? 10 : pageNumber), getAuditList());

 		return "listcheck";
 	}

 	private DetachedCriteria getAuditList() {

 		Map<String, Object> session = ActionContext.getContext().getSession();
 		CAccount account = (CAccount) session.get("account");

 		DetachedCriteria dc = DetachedCriteria.forClass(PurchaseCheck.class);
 		dc.add(Restrictions.eq("organizationId", (String)session.get("organizationID")));
 		dc.add(Restrictions.eq("companyId", account.getCompanyID()));
 		if(fiveClear!=null&&!"".equals(fiveClear))
 			dc.add(Restrictions.eq("fiveClear", fiveClear));
 		dc.addOrder(Order.desc("applyTime"));
 	

 		if (search != null && search.equals("search")) {
 			purchaseCheck = (PurchaseCheck) session.get("tablesearch");
 			if (purchaseCheck.getJournalNum() != null
 					&& !purchaseCheck.getJournalNum().equals("")) {
 				dc.add(Restrictions.like("journalNum", purchaseCheck.getJournalNum().trim(), MatchMode.ANYWHERE));

 			}
 			if (purchaseCheck.getApplyor() != null
 					&& !purchaseCheck.getApplyor().equals("")) {
 				dc.add(Restrictions.like("applyor", purchaseCheck.getApplyor().trim(), MatchMode.ANYWHERE));

 			}
 			if (purchaseCheck.getStatus() != null
 					&& !purchaseCheck.getStatus().equals("")) {
 				dc.add(Restrictions.like("status", purchaseCheck.getStatus().trim(), MatchMode.ANYWHERE));

 			}
 			
 			
 		}
 		  return dc;
 		
 	}
 	
 	
 	public String toAduitSearch() {
 		Map<String, Object> session = ActionContext.getContext().getSession();
 		session.put("tablesearch", purchaseCheck);
 		return findAuditPurSheetList();
 	}
 	
 	/**
 	 * 
 	 * 
 	 * 查看审核
 	 * @return
 	 */
 	public String viewCheck(){
 		Map<String, Object> session = ActionContext.getContext().getSession();
 		CAccount account = (CAccount) session.get("account");
 		String hqlcheck = "from PurchaseCheck where cashierBillsID = ?";
	    purchaseCheck = (PurchaseCheck) baseBeanService.getBeanByHqlAndParams(hqlcheck,new Object[]{purchaseCheck.getCashierBillsID()});
	    
	    if(purchaseCheck.getStatus().equals("00")){
	    	String hqlForMan = "from Staff where staffID = ?";
	  		staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
	  				new Object[] { account.getStaffID() });
	    }
 		
 		String hqlcash = "from CashierBills where cashierBillsID = ?";
		cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqlcash,new Object[]{purchaseCheck.getCashierBillsID()});
	    String hqlgoodb = "from GoodsBills where cashierBillsID = ?";
	    list = baseBeanService.getListBeanByHqlAndParams(hqlgoodb, new Object[]{purchaseCheck.getCashierBillsID()});
 	    
	    return "viewcheck";
 	}
 	
	
 	


 	

 	 /*************************************采购审核End************************************************************/

 	 /*************************************招标发布Start************************************************************/

 	/**
 	 * 
 	 * 
 	 * 招标发布列表
 	 * @return
 	 */

    public String findInviteBidsList() {

		pageForm = baseBeanService.getPageFormByDC(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), getInviteBList());

		return "listinvite";
	}

	private DetachedCriteria getInviteBList() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		DetachedCriteria dc = DetachedCriteria.forClass(InviteBids.class);
		dc.add(Restrictions.eq("organizationId", (String)session.get("organizationID")));
		dc.add(Restrictions.eq("companyId", account.getCompanyID()));
		if(fiveClear!=null&&!"".equals(fiveClear))
			dc.add(Restrictions.eq("fiveClear", fiveClear));
		dc.add(Restrictions.eq("type", type));
		dc.addOrder(Order.desc("applyPubDate"));
	

		if (search != null && search.equals("search")) {
			inviteBids = (InviteBids) session.get("tablesearch");
			if (inviteBids.getJournalNum() != null
					&& !inviteBids.getJournalNum().equals("")) {
				dc.add(Restrictions.like("journalNum", inviteBids.getJournalNum().trim(), MatchMode.ANYWHERE));

			}
			if (inviteBids.getStaffName() != null
					&& !inviteBids.getStaffName().equals("")) {
				dc.add(Restrictions.like("staffName", inviteBids.getStaffName().trim(), MatchMode.ANYWHERE));

			}
			if (inviteBids.getStatus() != null
					&& !inviteBids.getStatus().equals("")) {
				dc.add(Restrictions.like("status", inviteBids.getStatus().trim(), MatchMode.ANYWHERE));

			}
			
			
		}
		  return dc;
		
	}
	
	
	/**
 	 * 
 	 * 
 	 * 查看发布
 	 * @return
 	 */
 	public String viewInviteBids(){
 		Map<String, Object> session = ActionContext.getContext().getSession();
 		CAccount account = (CAccount) session.get("account");
 		String hqlcheck = "from InviteBids where cashierBillsID = ?";
 		inviteBids = (InviteBids) baseBeanService.getBeanByHqlAndParams(hqlcheck,new Object[]{inviteBids.getCashierBillsID()});
	    
	    if(inviteBids.getStatus().equals("00")){
	    	String hqlForMan = "from Staff where staffID = ?";
	  		staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
	  				new Object[] { account.getStaffID() });
	    }
 		
 		String hqlcash = "from CashierBills where cashierBillsID = ?";
		cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqlcash,new Object[]{inviteBids.getCashierBillsID()});
	    String hqlgoodb = "from GoodsBills where cashierBillsID = ?";
	    list = baseBeanService.getListBeanByHqlAndParams(hqlgoodb, new Object[]{inviteBids.getCashierBillsID()});
 	    
	    return "viewinvite";
 	}
 	
 	
 	  
    /**
     * 
     * 
     * 发布招标
     * @return
     */
    public String publishInviteBids(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		String hql = "from InviteBids where ibId = ?";
		InviteBids ibids = (InviteBids) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { inviteBids.getIbId() });

		if (inviteBids.getStatus() != null && inviteBids.getStatus().equals("01")) {
			String hqlstaff = "from Staff where staffID = ?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
					hqlstaff, new Object[] { account.getStaffID() });

			ibids.setRemark(inviteBids.getRemark());
			ibids.setPublishID(staff.getStaffID());
			ibids.setPublishName(staff.getStaffName());
			ibids.setPublishDate(new Date());
			if (startTime != null && !startTime.equals("")) {
				ibids.setStartDate(Utilities.getDateFromString(startTime,
						"yyyy-MM-dd HH:mm:ss"));
			}
			if (endTime != null && !endTime.equals("")) {
				ibids.setEndDate(Utilities.getDateFromString(endTime,
						"yyyy-MM-dd HH:mm:ss"));
			}
		}else{
			ibids.setCancelDate(new Date());
		}
		ibids.setStatus(inviteBids.getStatus());
		baseBeanService.update(ibids);
		return "success";
    }
 	
    
    /**
     * 
     * 
     * 招标发布导出
     * @return
     */
    public String showExcelByInvite() {
		List<BaseBean> list = baseBeanService.getListByDC(getInviteBList());
		excelStream = excelService.showExcel(InviteBids.columnHeadings(),
				list);

		return "showexcel";

	} 
    
    public String toInviteBSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", inviteBids);
		return findInviteBidsList();
	}
	
	 /*************************************招标发布End************************************************************/

	
    
    
	 /*************************************比价选标Start************************************************************/

	/**
	 * 
	 * 
	 * 比价选标
	 * @return
	 */

   public String findSelectBidsList() {

		pageForm = baseBeanService.getPageFormByDC(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), getSelectBList());

		return "listselectb";
	}

	private DetachedCriteria getSelectBList() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		DetachedCriteria dc = DetachedCriteria.forClass(InviteBids.class);
		dc.add(Restrictions.eq("organizationId", (String)session.get("organizationID")));
		dc.add(Restrictions.eq("companyId", account.getCompanyID()));
		dc.add(Restrictions.eq("fiveClear", fiveClear));
		dc.add(Restrictions.ne("status","00"));
		dc.addOrder(Order.desc("publishDate"));
	

		if (search != null && search.equals("search")) {
			inviteBids = (InviteBids) session.get("tablesearch");
			if (inviteBids.getJournalNum() != null
					&& !inviteBids.getJournalNum().equals("")) {
				dc.add(Restrictions.like("journalNum", inviteBids.getJournalNum().trim(), MatchMode.ANYWHERE));

			}
			if (inviteBids.getStaffName() != null
					&& !inviteBids.getStaffName().equals("")) {
				dc.add(Restrictions.like("staffName", inviteBids.getStaffName().trim(), MatchMode.ANYWHERE));

			}
			if (inviteBids.getStatus() != null
					&& !inviteBids.getStatus().equals("")) {
				dc.add(Restrictions.like("status", inviteBids.getStatus().trim(), MatchMode.ANYWHERE));

			}
			
			
			
		}
		  return dc;
		
	}
	
	
	/**
	 * 
	 * 
	 * 查看比价选标
	 * @return
	 */
	public String viewSelectBids(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hqlcheck = "from InviteBids where cashierBillsID = ?";
		inviteBids = (InviteBids) baseBeanService.getBeanByHqlAndParams(hqlcheck,new Object[]{inviteBids.getCashierBillsID()});
	    
	    if(inviteBids.getStatus().equals("00")){
	    	String hqlForMan = "from Staff where staffID = ?";
	  		staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
	  				new Object[] { account.getStaffID() });
	    }
		
		String hqlcash = "from CashierBills where cashierBillsID = ?";
		cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqlcash,new Object[]{inviteBids.getCashierBillsID()});
	    String hqlgoodb = "from GoodsBills where cashierBillsID = ?";
	    list = baseBeanService.getListBeanByHqlAndParams(hqlgoodb, new Object[]{inviteBids.getCashierBillsID()});
	    String sqlbid = "select goodsID,case when max(iszbid)='01' then ? else ? end  from dtBidsinformation where cashierBillsID = ? group by goodsID";
	    List<Object> bidlist = baseBeanService.getListBeanBySqlAndParams(sqlbid,new Object[]{"已选标","未选标",inviteBids.getCashierBillsID()});
	    Map<String,String> mapbid = new HashMap<String, String>();
	    for (int i = 0; i < bidlist.size(); i++) {
			Object[] obj = (Object[])bidlist.get(i);
			mapbid.put(obj[0].toString(),obj[1].toString());
		}
	    HttpServletRequest request = ServletActionContext.getRequest();
	    request.setAttribute("mapbid",mapbid);
	    
	    return "viewselectb";
	}
	
	
	/**
	 * 
	 * 产品比较
	 * @return
	 */
	public String productPriceMatch(){
		
		String hql = "from Bidsinformation where cashierBillsID = ? and goodsID = ? order by iszbid desc,bidDate";
		list = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{inviteBids.getCashierBillsID(),bidsinfo.getGoodsID()});
		return "pricematch";
		
	}
	
	
	  
   /**
    * 
    * 
    * 选标
    * @return
    */
   public String  SelectInviteBids(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		String hql = "from InviteBids where ibId = ?";
		InviteBids ibids = (InviteBids) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { inviteBids.getIbId() });

		if (ibids.getStatus() != null && ibids.getStatus().equals("01")) {
			String hqlstaff = "from Staff where staffID = ?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
					hqlstaff, new Object[] { account.getStaffID() });

			ibids.setRemark(inviteBids.getRemark());
			ibids.setPublishID(staff.getStaffID());
			ibids.setPublishName(staff.getStaffName());
			ibids.setPublishDate(new Date());
			if (startTime != null && !startTime.equals("")) {
				ibids.setStartDate(Utilities.getDateFromString(startTime,
						"yyyy-MM-dd HH:mm:ss"));
			}
			if (endTime != null && !endTime.equals("")) {
				ibids.setEndDate(Utilities.getDateFromString(endTime,
						"yyyy-MM-dd HH:mm:ss"));
			}
		}else{
			ibids.setCancelDate(new Date());
		}
		ibids.setStatus(inviteBids.getStatus());
		baseBeanService.update(ibids);
		return "success";
   }
	
   
   /**
    * 
    * 
    * 招标发布导出
    * @return
    */
   public String showExcelBySelectBids() {
		List<BaseBean> list = baseBeanService.getListByDC(getSelectBList());
		excelStream = excelService.showExcel(InviteBids.columnHeadings(),
				list);

		return "showexcel";

	} 
   
   public String toSelectBSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", inviteBids);
		return findSelectBidsList();
	}
   
   /**
    * 
    * 
    * 确定中标
    * @return
    */
   public String confirmBids(){
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");

			String hqlstaff = "from Staff where staffID = ?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff,
					new Object[] { account.getStaffID() });
			Date cur = new Date();
			String bfIds = bidsinfo.getBfId();
			String[] array = bfIds.split(",");

			List<String> hqls = new ArrayList<String>();

			List<Object[]> parmsList = new ArrayList<Object[]>();

			// 更新中标信息
			String hql = "update Bidsinformation set iszbid = ?,selectID = ?,selectName = ?,zbDate = ? where bfId in(";
			for (int i = 0; i < array.length; i++) {
				hql += "'"+array[i].trim()+"'";
				if (i != array.length - 1) {
					hql += ",";
				} else {
					hql += ")";
				}
			}

			hqls.add(hql);
			Object[] param1 = new Object[] { "01", account.getStaffID(),
					staff.getStaffName(), cur };

			parmsList.add(param1);
			// 更新整个单子的发布状态
			String hqlq = "from InviteBids where cashierBillsID = ?";
			InviteBids ibis = (InviteBids) baseBeanService.getBeanByHqlAndParams(
					hqlq, new Object[] { inviteBids.getCashierBillsID() });
			if (ibis != null && !"02".equals(ibis.getStatus())) {
				String hqlin = "update InviteBids set status = ? where cashierBillsID = ?";
				hqls.add(hqlin);
				Object[] param2 = new Object[] { "02",
						inviteBids.getCashierBillsID() };
				parmsList.add(param2);
			}
			baseBeanService.executeHqlsByParamsList(null,
					hqls.toArray(new String[hqls.size()]), parmsList);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	   
	   return "success";
   }
   
   
   
	
	/**
	 * 
	 * 
	 * 提交资金申请
	 * 
	 */
	public String submitFundApply() {
		

		HttpServletRequest re=ServletActionContext.getRequest();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<BaseBean> list=new ArrayList<BaseBean>();
		ProductPackaging pp=(ProductPackaging) baseBeanService.getBeanByHqlAndParams(
				"from ProductPackaging where goodsName=? and fiveClear=?",new Object[]{"资金申请单","ProductPackaging20160817DZZKKJD29J0000005828"});
		
		Inventory inv=(Inventory) baseBeanService.getBeanByHqlAndParams(
				"from Inventory where productId=? and warehouse=?",new Object[]{pp.getPpID(),"ProductPackaging20160817DZZKKJD29J0000005828"});
		inv.setInvenQuantity(Integer.parseInt(inv.getInvenQuantity())-1+"");		
		list.add(inv);
		
		GoodsNum gn=(GoodsNum) baseBeanService.getBeanByHqlAndParams(
				"from GoodsNum where cashierbillsID=? and goodsID=? and status=? and companyID=? and rownum <2",
				new Object[]{inv.getInventoryID(),pp.getGoodsID(),"03",account.getCompanyID()});
		if(gn!=null){ 
			gn.setStatus("04");
			list.add(gn);
		}else{
			gn=new GoodsNum();
			gn.setGoodsnumber("0001");
		}
		Inventory it=new Inventory();
		it.setInventoryID(serverService.getServerID("Inventory"));
		it.setCompanyID(account.getCompanyID());
		it.setStaffID(account.getStaffID());
		it.setStaffName(account.getStaffName());
		it.setWarehouse(account.getStaffID());
		it.setWarehouseName(account.getStaffName());
		it.setGoodsID(inv.getGoodsID());
		it.setGoodsName(inv.getGoodsName());
		it.setGoodsType(inv.getGoodsType());
		it.setBarcode(inv.getBarcode());
		it.setStandard(inv.getStandard());
		it.setGoodsCoding(inv.getGoodsCoding());
		it.setUnit(inv.getUnit());
		it.setUnitPrice(inv.getUnitPrice());
		it.setInvenQuantity("1");
		it.setGoodstatus("00");
		it.setStatus("07");
		it.setWeizhi(inv.getWarehouse());
		it.setProductId(inv.getProductId());
		it.setCategory(inv.getCategory());
		it.setFiveClear(inv.getFiveClear());
		list.add(it);
		String ppID=re.getParameter("ppID");
		GoodsBills pt=(GoodsBills) baseBeanService.getBeanByHqlAndParams(
				"from GoodsBills where goodsBillsID=?",new Object[]{ppID});
		CashierBills c=new CashierBills();
		c.setCashierBillsID(serverService.getServerID("CashierBills"));
		c.setCompanyID(account.getCompanyID());
		c.setCompanyName(account.getCompanyName());
		c.setDepartmentID("organization20100909zijmxn7qze0000001401");
		c.setDepartmentName("财务部");
		c.setOrganizationID("organization20100909zijmxn7qze0000001401");
		c.setBillsType("拨款单");
		c.setProjectName(pp.getGoodsName());
		c.setJournalNum(serverService.getBillID(account.getCompanyID()));
		c.setStaffID(account.getStaffID());
		c.setStaffName(account.getStaffName());
		c.setInputName(account.getStaffName());
		c.setInputid(account.getStaffID());
		c.setCashierDate(new Date());
		c.setProID(pt.getPpID());
		c.setStatus("41");
		c.setRemark("生产资金申请单");
		c.setFiveClear(pp.getFiveClear());
		list.add(c);
		

		GoodsBills goods=new GoodsBills();
		goods.setGoodsBillsID(serverService.getServerID("GoodsBills"));
		goods.setCompanyID(account.getCompanyID());
		goods.setInventoryID(it.getInventoryID());
		goods.setGoodsnumber(gn.getGoodsnumber());
		goods.setCashierBillsID(c.getCashierBillsID());
		goods.setGoodsID(pt.getGoodsID());
		goods.setTypeID(pt.getTypeID());
		goods.setGoodsNum(pt.getGoodsNum());
		goods.setGoodsName(pt.getGoodsName());
		goods.setStandard(pt.getStandard());
		goods.setPrice(pt.getPrice());
		goods.setMoney(pt.getPrice());	
		goods.setQuantity("1");
		goods.setCategory(pt.getCategory());
		goods.setPpID(pt.getPpID());
		list.add(goods);
		
		
		stockInv sto=new stockInv();
		sto.setStockinvID(serverService.getServerID("stockInv"));
		sto.setCompanyID(account.getCompanyID());
		sto.setGoodsBillsId(goods.getGoodsBillsID());
		sto.setGoodsID(goods.getGoodsID());
		sto.setGoodsType(goods.getTypeID());
		sto.setGoodsName(goods.getGoodsName());
		sto.setInvenQuantity("1");
		sto.setIntime(new Date());
		sto.setType("01");
		sto.setStaffID(account.getStaffID());
		sto.setStaffName(account.getStaffName());
		list.add(sto);
		
		List<BaseBean> ppList=baseBeanService.getListBeanByHqlAndParams(
				"from ProductPackaging where companyID=? and  parentId=? and field=?",
				new Object[]{account.getCompanyID(),pp.getPpID(),"00"});
		Map<String,Object> map=new HashMap<String, Object>();
		for(int i=0;i<ppList.size();i++){
			ProductPackaging pr=(ProductPackaging) ppList.get(i);
			map.put(pr.getPpID(),pr);
		}
		
		
		if(fieldConStors!=null){
			for(int i=0;i<fieldConStors.size();i++){
				FieldConStor f=(FieldConStor) fieldConStors.get(i);
				ProductPackaging pr=(ProductPackaging) map.get(f.getFieldPpID());
				if(pr.getPrimaryField()!="所属产品"){
					f.setFieldConID(serverService.getServerID("FieldConStor"));
					f.setPpID(pp.getPpID());
					f.setGoodsName(pp.getGoodsName());
					f.setFieldPpID(pr.getPpID());
					f.setFieldPpName(pr.getGoodsName());
					f.setPrimaryField(pr.getPrimaryField());
					f.setTwoLevelField(pr.getTwoLevelField());
					f.setCashierBillsID(c.getCashierBillsID());
					list.add(f);
				}else{
					
				}
			}
		}
		
		/*for(int i=0;i<ppList.size();i++){
			productPackajiage pr=(productPackajiage) ppList.get(i);
			if(pp.getPrimaryField()!="所属产品"){
				
				f.setFieldConID(serverService.getServerID("FieldConStor"));
				f.setPpID(pp.getPpID());
				f.setGoodsName(pp.getGoodsName());
				f.setFieldPpID(pr.getPpID());
				f.setFieldPpName(pr.getGoodsName());
				f.setPrimaryField(pr.getPrimaryField());
				f.setTwoLevelField(pr.getTwoLevelField());
				f.setCashierBillsID(c.getCashierBillsID());
				list.add(f);
			}else{
				
			}
		}*/
		if(fieldConStors!=null){
			for(int i=0;i<fieldConStors.size();i++){
				FieldConStor f=(FieldConStor) fieldConStors.get(i);
				ProductPackaging pr=(ProductPackaging) map.get(f.getFieldPpID());
				
				f.setFieldConID(serverService.getServerID("FieldConStor"));
				f.setPpID(pp.getPpID());
				f.setGoodsName(pp.getGoodsName());
				f.setFieldPpID(pr.getPpID());
				f.setFieldPpName(pr.getGoodsName());
				f.setPrimaryField(pr.getPrimaryField());
				f.setTwoLevelField(pr.getTwoLevelField());
				f.setCashierBillsID(c.getCashierBillsID());
				list.add(f);
			}
		}
	
		
		baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
		return "success";
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*try {
			List<BaseBean>  baseBeanList = new ArrayList<BaseBean>();
			
			String hql = "from CashierBills where cashierBillsID = ?";

			CashierBills cashierBills1 = (CashierBills) baseBeanService
								.getBeanByHqlAndParams(hql,
										new Object[] { inviteBids.getCashierBillsID()});
			
			cashierBills1.setStatus("04");
			baseBeanList.add(cashierBills1);
			//生成一个待拨款的单子
			CashierBills dbokuan = new CashierBills();
			dbokuan = (CashierBills) cashierBills1.cloneCashierBills();
			dbokuan.setCashierBillsKey(null);
			dbokuan.setCashierBillsID(serverService.getServerID("cashierTally"));
			dbokuan.setStatus("41");
			dbokuan.setBillsType("拨款单");
			dbokuan.setInputid(null);
			dbokuan.setInputName("系统生成");
			dbokuan.setInputOrganizationID(null);
			dbokuan.setInputOrganizationName(null);
			dbokuan.setInputCompanyid(null);
			dbokuan.setInputCompanyName(null);
			dbokuan.setCashierDate(new Date());
			dbokuan.setJournalNum(serverService.getBillID(dbokuan.getCompanyID()));
			baseBeanList.add(dbokuan);

			String hqlbid = "from Bidsinformation where cashierBillsID = ? and iszbid = ?";
			List<BaseBean> bidlist = baseBeanService.getListBeanByHqlAndParams(hqlbid,new Object[]{inviteBids.getCashierBillsID(),"01"});
			
			
			
			for(BaseBean g:bidlist){
				Bidsinformation goodf = (Bidsinformation) g;
				GoodsBills dbkgood = new GoodsBills();
				dbkgood.setGoodsBillsID(serverService.getServerID("goodsbills"));
				dbkgood.setCashierBillsID(dbokuan.getCashierBillsID());
				dbkgood.setGoodsID(goodf.getTgoodsID());
				dbkgood.setGoodsName(goodf.getTgoodsName());
				dbkgood.setGoodsNum(goodf.getTgoodsNum());
				dbkgood.setQuantity(goodf.getTquantity());
				dbkgood.setPrice(goodf.getTprice());
				dbkgood.setMoney(goodf.getTmoney());
				dbkgood.setBoxStandard(goodf.getBoxStandard());
				dbkgood.setPpID(goodf.getPpID());
				dbkgood.setTargetSalerID(cashierBills1.getStaffID());
				dbkgood.setTargetSalerName(cashierBills1.getStaffName());
				
				baseBeanList.add(dbkgood);
			}
			//关联表
			RelatedBill rb = new RelatedBill();
			rb.setRbID(serverService.getServerID("rbID"));
			rb.setCashid(cashierBills1.getCashierBillsID());
			rb.setCashfid(dbokuan.getCashierBillsID());
			baseBeanList.add(rb);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(
								baseBeanList, null, null);
		} catch (Exception e) {
			
			e.printStackTrace();
		}*/
			
		
		/*return "success";*/

	}
	
	
	
	 /*************************************比价选标End************************************************************/
	/*************************************投标Start************************************************************/
	
	/**
	 * 
	 * 获取商品招标列表
	 * @return
	 */
	public String findGoodbidList(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		if(tc!=null) {

			earthIndexService.addBrowseHistory(tc.getSccId(),"招标","");
		}

		String sql  = "select p.goodsName,p.ppID,p.goodsID,p.image,b.bidcount,p.price,b.endDate,b.ibId,b.cashierBillsID from dt_ProductPackaging p,dtInviteBids b where p.ppID=b.ppID and status != ?";
		
		List<Object> params = new ArrayList<Object>();
		params.add("00");
		if(search!=null&&search.equals("search")){
			
		  if(parameter!=null&&!parameter.equals("")){
			  sql+=" and p.goodsName  like ?";
			  params.add("%"+parameter+"%");
		  }
		  if(tradeID!=null&&!tradeID.equals("")){
			  sql+=" and p.tradeID  =  ?";
			  params.add(tradeID);
		  }else if(tradeName!=null&&!tradeName.equals("")){
			  sql+=" and (";
			 String[] arary = tradeName.split(",");
			 for (int i = 0; i < arary.length; i++) {
				 sql+="p.tradeName  like  ?";
				 if(i!=arary.length-1){
					 sql+=" or "; 
				 }
				 params.add("%"+arary[i]+"%");
				
			}
			 sql+=")";
		  }
		}
		sql+=" order by publishDate desc";
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 8 : pageNumber),sql,"select count(*) from ("+sql+")", params.toArray());
		
		
		
		
		return "tozb";
	}
	/**
	 * 
	 * ajax获取商品招标列表
	 * @return
	 */
	
	public String ajaxGoodbidList(){
        String sql  = "select p.goodsName,p.ppID,p.goodsID,p.image,b.bidcount,p.price,to_char(b.endDate,'yyyy-mm-dd hh24:mi:ss'),b.ibId,b.cashierBillsID  from dt_ProductPackaging p,dtInviteBids b where p.ppID=b.ppID and status != ?";
		
		List<Object> params = new ArrayList<Object>();
		params.add("00");
		if(search!=null&&search.equals("search")){
			
		  if(parameter!=null&&!parameter.equals("")){
			  sql+=" and p.goodsName  like ?";
			  params.add("%"+parameter+"%");
		  }
		  if(tradeID!=null&&!tradeID.equals("")){
			  sql+=" and p.tradeID  =  ?";
			  params.add(tradeID);
		  }else if(tradeName!=null&&!tradeName.equals("")){
			  sql+=" and (";
			 String[] arary = tradeName.split(",");
			 for (int i = 0; i < arary.length; i++) {
				 sql+="p.tradeName  like  ?";
				 if(i!=arary.length-1){
					 sql+=" or "; 
				 }
				 params.add("%"+arary[i]+"%");
				
			}
			 sql+=")";
		  }
		}
		sql+=" order by publishDate desc";
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 8 : pageNumber),sql,"select count(*) from ("+sql+")", params.toArray());
		
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	
	
	
	/**
	 * 
	 * 
	 * 招标首页招标物品列表
	 * @return
	 */
	public String findbidIndexList(){
		
		String sql  = "select p.goodsName,p.ppID,p.goodsID,p.image,b.bidcount,p.price,b.endDate,b.ibId,b.cashierBillsID from dt_ProductPackaging p,dtInviteBids b where p.ppID=b.ppID and status != ?";
		
		List<Object> params = new ArrayList<Object>();
		params.add("00");
		if(search!=null&&search.equals("search")){
			
		  if(parameter!=null&&!parameter.equals("")){
			  sql+=" and p.goodsName  like ?";
			  params.add("%"+parameter+"%");
		  }
		}
		sql+=" order by publishDate desc";
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 8 : pageNumber),sql,"select count(*) from ("+sql+")", params.toArray());
		
		
		//todo
		//获取热门行业
		StringBuilder sb = new StringBuilder();
		sb.append("select t.num,t.iconpath,t.codevalue,t.tradeid,t.codeDesc from (select count(p.tradeid) num,f.iconpath,f.Codevalue,p.tradeid,f.codeDesc");
		sb.append(" from dtInviteBids b");
		sb.append(" left join dt_ProductPackaging p on b.ppid = p.ppid");
		sb.append(" left join dtscode f on p.tradeid = f.codeid");
		sb.append(" where b.status = ? and p.tradeid is not null  group by p.tradeid,f.iconpath,f.Codevalue,f.codeDesc");
		sb.append(" order by count(p.tradeid) desc) t where rownum<4");
		List<Object> list = baseBeanService.getListBeanBySqlAndParams(sb.toString(), new Object[]{"01"});
						 
						  
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("hotindus", list);	 

		return "zbindex";
	}
	
	
	/**
	 * 
	 * 点击查看项目详细信息
	 * @return
	 */
	public String viewMainProduct(){
		
		 String sql  = "select p.goodsName,p.ppID,p.goodsID,p.image,b.bidcount,p.price,b.endDate,b.ibId,b.publishName,b.remark,b.publishDate,case when b.publishType ='00' then ? else ? end from dt_ProductPackaging p,dtInviteBids b where p.ppID = ? and b.cashierBillsID = ?";
		 List<Object> list = baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{"公司","个人",ppid,inviteBids.getCashierBillsID()});
		 Object obj = list.get(0);
		 
		 String hqlattr = "from AttriProduction where goodsid = ? and  type = ? and sort > ? order by sort";
		 List<BaseBean> attrlist = baseBeanService.getListBeanByHqlAndParams(hqlattr, new Object[]{bidsinfo.getGoodsID(),"1",0});
		 
		 String sqlsub = "select t.ppID,t.goodsID,t.goodsNum,t.goodsName,t.price,t.image from dt_ProductPackaging t  connect by nocycle prior t.ppID = t.parentId start with t.ppID = ?";

		 List<BaseBean> sublist  = baseBeanService.getListBeanBySqlAndParams(sqlsub, new Object[]{ppid});
		 
		 HttpServletRequest request = ServletActionContext.getRequest();
		 request.setAttribute("mainobj", obj);
		 request.setAttribute("attrlist", attrlist);
		 request.setAttribute("sublist", sublist);
		
		 return "zbbids";
		
		
	}
	
	/**
	 * 
	 * 点击查看子产品详细信息
	 * @return
	 */
	public String viewSubProduct(){
		
		 String sql  = "select p.goodsName,p.ppID,p.goodsID,p.image,b.bidcount,p.price,b.endDate,b.ibId,b.publishName,b.remark,b.publishDate,case when b.publishType ='00' then ? else ? end from dt_ProductPackaging p,dtInviteBids b where p.ppID = ? and b.cashierBillsID = ?";
		 List<Object> list = baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{"公司","个人",ppid,inviteBids.getCashierBillsID()});
		 Object obj = list.get(0);
		 
		 String hqlattr = "from AttriProduction where goodsid = ? and type = ? and sort > ? order by sort";
		 List<BaseBean> attrlist = baseBeanService.getListBeanByHqlAndParams(hqlattr, new Object[]{bidsinfo.getGoodsID(),"1",0});
		 
		 String sqlgood = "select b.goodsName,b.price,b.boxStandard,g.photopath from dtGoodsBills b,dtGoodsManage g where b.cashierBillsID = ? and b.goodsID= ? and g.goodsid=b.goodsid";
		 List<Object> goodlist = baseBeanService.getListBeanBySqlAndParams(sqlgood, new Object[]{inviteBids.getCashierBillsID(),bidsinfo.getGoodsID()});
		 Object objgood = goodlist.get(0);
		 
		 String sqlsub = "select t.mainpicurl,t.tgoodsName,t.tprice,t.ppID,t.companyID,t.iszbid,c.ccompany_Id,t.tgoodsID from dtBidsinformation t,DT_ccom_com c where t.companyID = c.compnay_id and t.goodsID = ? order by bidDate desc";
		 List<Object> sublist = baseBeanService.getListBeanBySqlAndParams(sqlsub, new Object[]{bidsinfo.getGoodsID()});
		 
		 HttpServletRequest request = ServletActionContext.getRequest();
		 request.setAttribute("mainobj", obj);
		 request.setAttribute("attrlist", attrlist);
		 request.setAttribute("sublist", sublist);
		 request.setAttribute("good", objgood);
		
		return "tosub";
	}
	
	/**
	 * 
	 * 投标页面
	 * @return
	 */
	public String enterBids(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		
		if (cus == null) {
			String url = ServletActionContext.getRequest().getRequestURL()+"?bidsinfo.goodsID="+bidsinfo.getGoodsID()+"&inviteBids.cashierBillsID="+inviteBids.getCashierBillsID()+"&mainpid="+request.getParameter("mainpid");
			session.setAttribute("url", url);
			return "login";
		}

		
		
		String sqlgood = "select b.goodsName,b.price,g.photopath,b.goodsID,b.cashierBillsID from dtGoodsBills b,dtGoodsManage g where b.cashierBillsID = ? and b.goodsID= ? and g.goodsid=b.goodsid";
		List<Object> goodlist = baseBeanService.getListBeanBySqlAndParams(sqlgood, new Object[]{inviteBids.getCashierBillsID(),bidsinfo.getGoodsID()});
		Object objgood = goodlist.get(0);
		request.setAttribute("good", objgood);
		List<Object>  params = new ArrayList<Object>();
		params.add(cus.getCompanyId());
		if(ppid!=null&&!ppid.equals("")){
			String[] ppida = ppid.split(",");
			
			String sqlf = "";
			
			for (int i = 0; i < ppida.length; i++) {
				params.add(ppida[i]);
				if(i!=ppida.length-1){
					sqlf +="?,"; 
				}else{
					sqlf +="?"; 
				}
			}
			String sqlp = "select ps.ppid,p.goodsname,p.image,p.goodsid,ps.re_price from DT_PRO_SETUP ps,dt_ProductPackaging p where "
					+ " ps.com_id= ? and p.ppid=ps.ppid and ps.fcom_id is null and ps.ppid in("+sqlf+")";
			List<Object> bidlist = baseBeanService.getListBeanBySqlAndParams(sqlp, params.toArray());
			request.setAttribute("bidlist", bidlist);
		}
		
	    
		
		return "enterbids";
		
		
	}
	
	/**
	 * 
	 * 选择我店铺里拿来竞标的产品
	 * @return
	 */
	public String selectMybidGoods(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		
		if (cus == null) {
			return "login";
		}
		String sqlgood = " from GoodsManage g where g.goodsID= ?";
		GoodsManage  gm = (GoodsManage) baseBeanService.getBeanByHqlAndParams(sqlgood, new Object[]{bidsinfo.getGoodsID()});
		
		String companyID = cus.getCompanyId();
		String sqlp = "select ps.ppid,p.goodsname,p.goodsid,p.type,ps.re_price,p.companyid,p.productCode,p.model from DT_PRO_SETUP ps,dt_ProductPackaging p where ps.com_id= ?"
				+ " and p.ppid=ps.ppid and ps.fcom_id is null and p.showweixin = ?";
		
		
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 8 : pageNumber),sqlp,"select count(*) from ("+sqlp+")", new Object[]{companyID,"01"});
         
		request.setAttribute("gm", gm);
		return "mybidgood";
	}
	
	
	/**
	 * 
	 * 验证是否有权限投标
	 * @return
	 */
	public String ajaxValidate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		String valtype = request.getParameter("valtype");
		Map<String, Object> map = new HashMap<String, Object>();
		if (cus == null) {
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
			map.put("login", "login");
		} else {
			if ("tb".equals(valtype)) {
				map.put("result", cus.getState());

			} else {
				String sqlp = "select count(*) from DT_PRO_SETUP ps,dt_ProductPackaging p where  p.ppid=ps.ppid and ps.com_id= ? and ps.fcom_id is null and p.showweixin = ?";
				int count = baseBeanService.getConutByBySqlAndParams(sqlp,
						new Object[] { cus.getCompanyId(), "01" });
				if (count == 0) {
					map.put("result", count);
				}
				map.put("user", cus.getAccount());
			}
		}
		
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return "success";
	}
	
	/**
	 * 
	 * 上拉分页
	 * @return
	 */
	public String ajaxMybidGoods(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		
		if (cus == null) {
			return "login";
		}
		String companyID = cus.getCompanyId();
		String sqlp = "select ps.ppid,p.goodsname,p.image,p.goodsid,p.type,ps.re_price,p.companyid,p.productCode,p.model from DT_PRO_SETUP ps,dt_ProductPackaging p where ps.com_id= ?"
				+ " and p.ppid=ps.ppid and ps.fcom_id is null and p.showweixin = ?";
		
		
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 8 : pageNumber),sqlp,"select count(*) from ("+sqlp+")", new Object[]{companyID,"01"});

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	
	
	/**
	 * 
	 * 提交投标
	 * @return
	 */
	public String submitEnterBids(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		
		if (cus == null) {
			return "login";
		}
		List<BaseBean> beanlist = new ArrayList<BaseBean>();
		Bidsinformation bf = null;
		ProductPackaging pp = null;
		String hqlstaff="from staff where staffID = ?";
		String hqlpro = "from ProductPackaging where ppID = ?";
		String hqlgb = "from GoodsBills where cashierBillsID = ? and goodsID = ?";
		GoodsBills goodsBills = (GoodsBills) baseBeanService.getBeanByHqlAndParams(hqlgb,new Object[]{bidsinfo.getCashierBillsID(),bidsinfo.getGoodsID()});
        String hqlinv = "from InviteBids where cashierBillsID = ? and ppID = ?";
      
        InviteBids inv = (InviteBids) baseBeanService.getBeanByHqlAndParams(hqlinv,new Object[]{bidsinfo.getCashierBillsID(),mainpid});
	
		List<Object> para = new ArrayList<Object>();
		String hqlbs = "from Bidsinformation where cashierBillsID = ?";
		para.add(bidsinfo.getCashierBillsID());
		if(cus.getState().equals("2")){
			hqlbs+= " and companyID = ?";
			para.add(cus.getCompanyId());
		}else{
			hqlbs+= " and staffID = ?";
			para.add(cus.getStaffid());
			
		}
	    List<BaseBean> listbs =baseBeanService.getListBeanByHqlAndParams(hqlbs, para.toArray());
		if(listbs.size()==0){
			inv.setBidcount(String.valueOf(Integer.parseInt(inv.getBidcount())+1));
			beanlist.add(inv);
		}

		if(ppid!=null&&!ppid.equals("")){
			String[] ppida = ppid.split(",");
			for (int i = 0; i < ppida.length; i++) {
				bf = new Bidsinformation();
				bf.setBfId(serverService.getServerID("bfid"));
				bf.setBidDate(new Date());
				if(cus.getState().equals("1")){
					Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff, new Object[]{cus.getStaffid()});
					bf.setBidperson(staff.getStaffName());
					bf.setBidtype("01");
				}else{
					bf.setBidperson(cus.getPseudoCompanyName());
					bf.setCompanyID(cus.getCompanyId());
					bf.setBidtype("00");
				}
				
				bf.setBidremark(bidsinfo.getBidremark());
				bf.setCashierBillsID(bidsinfo.getCashierBillsID());
				bf.setGoodsID(bidsinfo.getGoodsID());
				bf.setStaffID(cus.getStaffid());
				bf.setIszbid("00");
				pp = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hqlpro, new Object[]{ppida[i].trim()});
				
				bf.setPpID(ppida[i].trim());
				bf.setMainpicurl(pp.getImage());
				bf.setTgoodsID(pp.getGoodsID());
				bf.setTgoodsName(pp.getGoodsName());
				bf.setTgoodsNum(pp.getGoodsNum());
				bf.setTprice(pp.getPrice());
				bf.setTquantity(goodsBills.getQuantity());
				if(goodsBills.getQuantity()==null){
					goodsBills.setQuantity("1");
				}
				bf.setTmoney(Float.parseFloat(goodsBills.getQuantity())*Float.parseFloat(pp.getPrice())+"");//金额)
				bf.setGoodsVariableID(pp.getVariableID());
				bf.setBoxStandard(pp.getModel());
				beanlist.add(bf);
			}
			
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beanlist,null,null);
		
		return "success";
	}
	
	/*************************************投标End************************************************************/

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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public GoodFunction getGoodFunction() {
		return goodFunction;
	}

	public void setGoodFunction(GoodFunction goodFunction) {
		this.goodFunction = goodFunction;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}

	public List<BaseBean> getList() {
		return list;
	}

	public PlanAmount getPlan() {
		return plan;
	}

	public void setPlan(PlanAmount plan) {
		this.plan = plan;
	}
	public String getFiveClear() {
		return fiveClear;
	}

	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}

	public String getBillID() {
		return billID;
	}

	public void setBillID(String billID) {
		this.billID = billID;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public CashierBills getCashierBills() {
		return cashierBills;
	}

	public void setCashierBills(CashierBills cashierBills) {
		this.cashierBills = cashierBills;
	}

	public Map<String, GoodsBills> getLogbookmap() {
		return logbookmap;
	}

	public void setLogbookmap(Map<String, GoodsBills> logbookmap) {
		this.logbookmap = logbookmap;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public PurchaseCheck getPurchaseCheck() {
		return purchaseCheck;
	}

	public void setPurchaseCheck(PurchaseCheck purchaseCheck) {
		this.purchaseCheck = purchaseCheck;
	}

	public InviteBids getInviteBids() {
		return inviteBids;
	}

	public void setInviteBids(InviteBids inviteBids) {
		this.inviteBids = inviteBids;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Bidsinformation getBidsinfo() {
		return bidsinfo;
	}

	public void setBidsinfo(Bidsinformation bidsinfo) {
		this.bidsinfo = bidsinfo;
	}

	public String getMainpid() {
		return mainpid;
	}

	public void setMainpid(String mainpid) {
		this.mainpid = mainpid;
	}

	public String getTradeID() {
		return tradeID;
	}

	public void setTradeID(String tradeID) {
		this.tradeID = tradeID;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<FieldConStor> getFieldConStors() {
		return fieldConStors;
	}

	public void setFieldConStors(List<FieldConStor> fieldConStors) {
		this.fieldConStors = fieldConStors;
	}

	
}
