package hy.ea.invoicing.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.BillCheck;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.RelatedBill;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.CostSheetBill;
import hy.ea.bo.invoicing.CostSheetBillSearch;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.DateJsonValueProcessor;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 物品的查询（预算资金，招标管理，产品清单表）
 * 陈婷
 * 
 * 项目物品已审核未审核两大模块
 * ct
 * @author Administrator
 */
@Controller
@Scope("prototype")
public class ProductAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;

	private CashierBills cashierBills;
	private GoodsBills detail;
	private String cashierBillsID;
	private String goodsBillsID;
	private CostSheetBill costSheetBill;//预算父类表
	private GoodsManage goods;//物品表
	private PageForm pageForm;
	private InputStream excelStream;
	private int pageNumber;
	private String search;//模糊查询
	private String parameter;
	private String treeid;//用于传输id
	private String sdate;//开始时间
	private String edate;//结束时间
	private String type;//单据类型
	private List<BaseBean> beans;
	private List<BaseBean> beanss;//去重复
	private List<BaseBean> staffList;//责任人
	private List<BaseBean> projectlist;
	private String result;
	private CostSheetBillSearch csbSearch;
	private BillCheck billCheck;
	private Staff staff;
	private String bjstatus;//比价状态是在比价管理，还是在未确认比价，还是在已确认比价
	/**
	 * 费用类别--GoodsBills
	 */
	private List<CCode> costTypeList;
	/**
	 * 单价管理--GoodsBills
	 */
	private List<CCode> priceManageList;

	
	
	
	/**
	 * 
	 * 已招标比价物品被选中的物品列表
	 * @return
	 */
	public String getZbPriceedList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		
		List<Object> parmsList = new ArrayList<Object>();
		String sql="select s.cashierbillsid,s.goodsname,s.goodsnum,b.departmentName,b.StaffName,s.typeID ,b.billsType,s.price,s.quantity,s.money," +
				"b.journalNum,b.cashierDate,s.startDate,b.projectname,b.status,s.goodsBillsID " +
				"from dtcashierbills b" +
				" join dtgoodsbills s on b.cashierbillsid=s.cashierbillsid";
		sql += " where b.groupCompanySn = ?";
		parmsList.add(groupCompanySn);
		sql += " and b.companyid = ? ";
		parmsList.add(account.getCompanyID());
		sql += " and b.organizationID=?";
		parmsList.add(organizationID);
		
	       if(search!=null&&search.equals("search")){
	    	   csbSearch = (CostSheetBillSearch)session.get("csbSearch");
			if(csbSearch!=null&&!csbSearch.getSprojectname().equals("")){
				sql+=" and b.projectname like ?";
				parmsList.add("%"+csbSearch.getSprojectname().trim()+"%");
				
			}
			
			if(csbSearch!=null&&!csbSearch.getGoodsnum().equals("")){
				sql+=" and s.goodsNum like ?";
				parmsList.add("%"+csbSearch.getGoodsnum().trim()+"%");
				
			}
			

			if(csbSearch!=null&&!csbSearch.getStaffname().equals("")){
				sql+=" and b.staffName like ?";
				parmsList.add("%"+csbSearch.getStaffname().trim()+"%");
				
			}

			
			if (csbSearch != null && csbSearch.getStart() != null&&!csbSearch.getStart().equals("")) {
				sql += " and b.cashierDate between ? and ? ";
				parmsList.add(Utilities.getDateFromString(csbSearch.getStart(),"yyyy-MM-dd"));
				
				Date enddate = null;
				if(csbSearch.getEnd()==null||csbSearch.getEnd().equals("")){
					enddate= new Date();
				}else{
					enddate = Utilities.getDateFromString(csbSearch.getEnd(), "yyyy-MM-dd");
				}
				parmsList.add(enddate);
			}	
	       }

			sql+=" and s.isSelected=? ";
			parmsList.add("01");

		sql += " order by b.cashierDate desc";
		Object[] parms = parmsList.toArray();
		String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus=? and c.organizationID=?)";
		staffList = baseBeanService.getListBeanByHqlAndParams(staffhql,
				new Object[] { account.getCompanyID(),"50", organizationID });
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);
		return "budgetlist";
	}
	
	
	/**
	 * 
	 * 得到招标里的物品
	 * 
	 * 
	 * 选择 序号 项目名称 项目分类 凭证号 品名编号 品名名称 
	 * 款源日期 单价 数量 金额 方向 产品类别 费用类别 品牌规格 型号 
	 * @return
	 */
	public String getBudgetGoodList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		String companyID = account.getCompanyID();
		
		List<Object> parmsList = new ArrayList<Object>();
		String sql="select s.goodsBillsID,b.projectName,b.xmtypename,b.journalNum," +
				"b.cashierDate,s.goodsnum,s.goodsname,s.startDate," +
				"s.price,s.quantity,s.money,s.standard,s.ccompanyName,s.connectName,s.cashierBillsID,s.cashierBillsID "+ 
				"from dtcashierbills b" +
				" join dtgoodsbills s on b.cashierbillsid=s.cashierbillsid ";
		sql += " where b.groupCompanySn = ?";
		parmsList.add(groupCompanySn);
		sql += " and b.companyid = ? ";
		parmsList.add(account.getCompanyID());
		sql += " and b.organizationID=?";
		parmsList.add(organizationID);
		sql+=" and b.status=? ";
		if(bjstatus!=null&&bjstatus.equals("yqrbj")){
			parmsList.add("04");
		}else{
			parmsList.add("03");
		}
		
		
		sql+=" and b.billsType=?";
		parmsList.add("项目支出预算单");
         if(bjstatus!=null&&bjstatus.equals("wqrbj")){
        	 sql+=" and isSelected =?";
     		parmsList.add("02");
		 }
         if(bjstatus!=null&&bjstatus.equals("yqrbj")){
        	 sql+=" and isSelected =?";
      		parmsList.add("01");
         }
		beanss = baseBeanService.getListBeanBySqlAndParams(sql.replace("select", "select distinct(s.goodsname),"), parmsList.toArray());
		if(search != null && search.equals("search")){
			
			if (cashierBills.getProjectName() != null&&!cashierBills.getProjectName().equals("")) {
				String projectNames = cashierBills.getProjectName();
				String[] arrays = projectNames.split(",");
				sql+=" and (";
                for (int i = 0; i < arrays.length; i++) {
                	sql += "b.projectName = ?";
                	if(i!=arrays.length-1){
                	     sql+=" or ";
                	}
                	parmsList.add(arrays[i]);
				}
                sql+=")";
			}
			if (cashierBills.getXmtype() != null&&!cashierBills.getXmtype().equals("")) {
				String xmtypes = cashierBills.getXmtype();
				String[] arrays = xmtypes.split(",");
				sql+=" and (";
                for (int i = 0; i < arrays.length; i++) {
                	sql += "b.xmtype = ?";
                	if(i!=arrays.length-1){
                	     sql+=" or ";
                	}
                	parmsList.add(arrays[i]);
				}
                sql+=")";
			}
			if (sdate != null&&!sdate.equals("")) {
				String[] arrays = sdate.split(",");
				sql+=" and (";
                for (int i = 0; i < arrays.length; i++) {
                	if(arrays[i].length()>9){
                		Date sd = Utilities.getDateFromString(arrays[i]+" 00:00:00","yyy-MM-dd HH:mm:ss");
                		Date ed = Utilities.getDateFromString(arrays[i]+" 23:59:59","yyy-MM-dd HH:mm:ss");

                		Date sdates = Utilities.getDateFromString(arrays[i],"yyyy-MM-dd");
                		System.out.println(sdates);
                    	if(type.equals("zddate")){
                    		sql+="(b.cashierDate >=? and b.cashierDate<= ?)";
                    	}else{
                    		sql+="(s.cashierDate >=? and s.cashierDate<= ?)";
                    	}
                    	if(i!=arrays.length-1){
                    	     sql+=" or ";
                    	}
                    	parmsList.add(sd);
                		parmsList.add(ed);
                	}
				}
                sql+=")";
			}
			if (detail.getGoodsName() != null&&!detail.getGoodsName().equals("")) {
				String goods= detail.getGoodsName();
				String[] arrays = goods.split(",");
				sql+=" and (";
                for (int i = 0; i < arrays.length; i++) {
                	sql += "s.goodsname = ?";
                	if(i!=arrays.length-1){
                	     sql+=" or ";
                	}
                	parmsList.add(arrays[i]);
                	
				}
                sql+=")";
			}
		}
		sql+=" order by s.goodsname";
		Object[] parms = parmsList.toArray();
		
		String hqlstaff = "from Staff where staffID = ?";
		staff  = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff, new Object[]{account.getStaffID()});



		beans= baseBeanService.getListBeanBySqlAndParams(sql,parms);

		return "bprice";
	}
	//查询
	public String toSearch() {
		ActionContext.getContext().getSession().put("csbSearch", csbSearch);
		return getZbPriceedList();
	}
	
	
	/**
	 * 
	 * 获取审核人列表
	 * @return
	 */
	public String findAuditorList() {

		String hqlc = "from BillCheck where newBillsID = ? order by audittime desc";
		List<BaseBean> bclist = baseBeanService.getListBeanByHqlAndParams(hqlc,
				new Object[] {billCheck.getNewBillsID()});
        
		Map<String, Object> map = new HashMap<String, Object>();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject obj = JSONObject.fromObject(map, jsonConfig);
		this.result = obj.toString();
		map.put("result", bclist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	
	
	//预算以及招标导出
	@SuppressWarnings("unchecked")
	public String showproductExcel(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		List<Object> parmsList = new ArrayList<Object>();
		String sql="select s.goodsname,s.goodsnum,b.departmentName,b.StaffName,s.typeID ,b.billsType,s.price,s.quantity,s.money," +
				"b.journalNum,b.cashierDate,s.startDate,b.projectname, case when b.status='04' then '待主管审核'   when b.status='05' then '待办公室审核'  when b.status='06' then '待会计审核'  when b.status='07' then '三审已通过'  else '三审已归档' end  " +
				"from dtcashierbills b" +
				" join dtgoodsbills s on b.cashierbillsid=s.cashierbillsid";
		sql += " where b.groupCompanySn = ?";
		parmsList.add(groupCompanySn);
		sql += " and b.companyid = ? ";
		parmsList.add(account.getCompanyID());
		sql += " and b.organizationID=?";
		parmsList.add(organizationID);
		 if(search!=null&&search.equals("search")){
	    	   csbSearch = (CostSheetBillSearch)session.get("csbSearch");
			if(csbSearch!=null&&!csbSearch.getSprojectname().equals("")){
				sql+=" and b.projectname like ?";
				parmsList.add("%"+csbSearch.getSprojectname().trim()+"%");
				
			}
			
			if(csbSearch!=null&&!csbSearch.getGoodsnum().equals("")){
				sql+=" and s.goodsNum like ?";
				parmsList.add("%"+csbSearch.getGoodsnum().trim()+"%");
				
			}
			

			if(csbSearch!=null&&!csbSearch.getStaffname().equals("")){
				sql+=" and b.staffName like ?";
				parmsList.add("%"+csbSearch.getStaffname().trim()+"%");
				
			}

			
			if (csbSearch != null && csbSearch.getStart() != null&&!csbSearch.getStart().equals("")) {
				sql += " and b.cashierDate between ? and ? ";
				parmsList.add(Utilities.getDateFromString(csbSearch.getStart(),"yyyy-MM-dd"));
				
				Date enddate = null;
				if(csbSearch.getEnd()==null||csbSearch.getEnd().equals("")){
					enddate= new Date();
				}else{
					enddate = Utilities.getDateFromString(csbSearch.getEnd(), "yyyy-MM-dd");
				}
				parmsList.add(enddate);
			}	
	       }


			
			sql+=" and s.isSelected=? ";
			parmsList.add("01");
	
		sql += " order by b.cashierDate desc";
		Object[] parms = parmsList.toArray();
		List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql,parms);
		String[] tiltes ={"序号","产品名称","产品编号","部门","责任人","产品类型","单据类别","产品预算单价","产品数量","余额","单据凭证号","制单日期","款源日期","项目名称","单据状态"};
		excelStream = excelService.showExcel(tiltes,list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出预算或招标产品管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	/**
	 * 
	 * ajax获取项目以及ID
	 * @return
	 */
	public String ajaxProjectList(){
		
		
 //查询所有该部门下单子
		
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			
			
			String organizationID = (String) session.get("organizationID");
			List<Object> parmsList = new ArrayList<Object>();
			String hql = "from ProductPackaging b";
			hql += " where  b.companyID = ? ";
			parmsList.add(account.getCompanyID());
			hql += " and b.organizationID=?";
			parmsList.add(organizationID);
			


			hql += " and  exists (select c.proID  from CashierBills c  where c.proID = b.ppID and c.companyID=? and c.organizationID=? and c.status = ?)";
			parmsList.add(account.getCompanyID());
			parmsList.add(organizationID);
			parmsList.add("03");


			hql += " order by b.PackagingDate desc";
			
			
			projectlist = baseBeanService.getListBeanByHqlAndParams(hql, parmsList.toArray());
			 
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("projectList", projectlist);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		
		return "success";
		
	}
	
	
	/**
	 * 
	 * 获取时间
	 * @return
	 */
	public String getDateList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		List<String> datelist = new ArrayList<String>();
		List<String> fdatelist = new ArrayList<String>();
		if(type.equals("zddate")){
		
			
		//制单时间
		String hql = "from CashierBills b where b.groupCompanySn = ? and b.companyID = ? and b.organizationID=?";
		hql += " and b.status = ? ";
		hql += " order by b.cashierDate";
		projectlist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{groupCompanySn,account.getCompanyID(),organizationID,"03"});

		for(BaseBean b:projectlist){
			CashierBills cash = (CashierBills) b;
			String dates = Utilities.getDateString(cash.getCashierDate(), "yyyy-MM-dd").substring(0,4);
			String fdates = Utilities.getDateString(cash.getCashierDate(), "yyyy-MM-dd");
			if(!datelist.contains(dates)){
			   datelist.add(dates);
			}
			if(!fdatelist.contains(fdates)){
				   fdatelist.add(fdates);
			}
			
		}
		
		}else{
		//款源时间
		List<Object> parmsList = new ArrayList<Object>();
		String sql="select s.startDate "+ 
				"from dtcashierbills b" +
				" join dtgoodsbills s on b.cashierbillsid=s.cashierbillsid ";
		sql += " where b.groupCompanySn = ?";
		parmsList.add(groupCompanySn);
		sql += " and b.companyid = ? ";
		parmsList.add(account.getCompanyID());
		sql += " and b.organizationID=?";
		parmsList.add(organizationID);
		
		sql+=" and b.status=? ";
		parmsList.add("03");
		sql+=" order by s.startDate";

		
		List<Date> list = baseBeanService.getListBeanBySqlAndParams(sql,parmsList.toArray());
		for(Date s:list){
			if(s!=null&&!s.equals("")){
			String dates = Utilities.getDateString(s, "yyyy-MM-dd").substring(0,4);
			String fdates = Utilities.getDateString(s, "yyyy-MM-dd");
			if(!datelist.contains(dates)){
			   datelist.add(dates);
			}
			if(!fdatelist.contains(fdates)){
				   fdatelist.add(fdates);
				}
			}
		}
		}
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("datelist", datelist);
        map.put("fdatelist", fdatelist);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
		
		return "success";
	}
	
	 /**
		 * @param cashierBills
		 * @return
		 */
		public BillCheck saveCheckbill(CashierBills cash,BillCheck bc){

			BillCheck billCheck=new BillCheck();
			billCheck.setCheckid(serverService.getServerID("billcheck"));
			if(bc!=null){
				billCheck.setOldBillsID(bc.getNewBillsID());
			}else{
				billCheck.setOldBillsID(cash.getCashierBillsID());
			}
			billCheck.setNewBillsID(cash.getCashierBillsID());
			billCheck.setFirstBillsID(cash.getCashierBillsID());
			
			billCheck.setJournalNum(cash.getJournalNum());
			billCheck.setAuditorcompanyid(cash.getExamineComID());
			billCheck.setAuditorcompanyname(cash.getExamineComName());
			billCheck.setAuditororgID(cash.getExamineorgID());
			billCheck.setAuditororgName(cash.getExamineorgName());
			billCheck.setAuditorid(cash.getExamineID());
			billCheck.setAuditorname(cash.getExamineName());
			
			billCheck.setInputid(cash.getInputid());	
			billCheck.setInputname(cash.getInputName());
		
			billCheck.setStaffID(cash.getStaffID());
			billCheck.setStaffName(cash.getStaffName());
			billCheck.setBillsType(cash.getBillsType());
			billCheck.setCashierDate(cash.getCashierDate());
			billCheck.setProjectName(cash.getProjectName());
			billCheck.setAuditorstatus("01");
		
			
			return billCheck;
		}
		

	
	
	
	/**
	 * 使用中
	 * 比价后确定选中物品直接生成出纳单
	 * 改为一个大项目的所有项目生成一个出纳单
	 * @return
	 */
	public String confirmBpirce() {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");

			// 得到所有的物品所在的

			String goodsBillsIDs = goodsBillsID.substring(0, goodsBillsID.length() - 1);
			String cashierBillsIDs = cashierBillsID.substring(0, cashierBillsID.length() - 1);
			String[] csdIDarray = goodsBillsIDs.split(",");
			String[] csbIDarray = cashierBillsIDs.split(",");
			List<String> testlist = new ArrayList<String>();
			List<BaseBean> baseBeanList = new ArrayList<BaseBean>();

			// //////////////////////修改物品、单据的状态
			for (int j = 0; j < csbIDarray.length; j++) {

				if (testlist.contains(csbIDarray[j])) {
					continue;
				} else {
					testlist.add(csbIDarray[j]);
				}
				try {
					
					if(bjstatus!=null&&bjstatus.equals("wqrbj")){
						// 当前单子没选中的物品
						List<BaseBean> listnot = baseBeanService.getListByDC(DetachedCriteria.forClass(GoodsBills.class).add(Restrictions.eq("cashierBillsID",
												csbIDarray[j].trim()))
										.add(Restrictions.not(Restrictions.in(
												"goodsBillsID", csdIDarray))));

						if (listnot.size() != 0) {
							for (BaseBean b : listnot) {
								GoodsBills detailnot = (GoodsBills) b;
								detailnot.setIsSelected("00");
								baseBeanList.add(detailnot);
							}
						}
					}
					
					
					
					// 查询产品保存物品单据表中
					// 当前单子选中的物品
					List<BaseBean> list = baseBeanService
							.getListByDC(DetachedCriteria
									.forClass(GoodsBills.class)
									.add(Restrictions.eq("cashierBillsID",
											csbIDarray[j].trim()))
									.add(Restrictions.in("goodsBillsID", csdIDarray)));
					
					if (list != null) {
						for (int i = 0; i < list.size(); i++) {
							
							GoodsBills detail = (GoodsBills) list.get(i);
							if(bjstatus!=null&&bjstatus.equals("wqrbj")){
							      detail.setIsSelected("01");//已确认的
							}else{
								  detail.setIsSelected("02");//待确认的
							}
							baseBeanList.add(detail);
						   // baseBeanService.update(detail);
							
						
							
							
							//生成审核记录
							BillCheck bc = new BillCheck();
							bc.setCheckid(serverService.getServerID("billcheck"));
						    bc.setOldBillsID(detail.getGoodsBillsID());
							bc.setNewBillsID(detail.getGoodsBillsID());
							bc.setFirstBillsID(detail.getGoodsBillsID());
							
							bc.setBilltatus("比价审核");
							bc.setDeptpost(billCheck.getDeptpost());
							bc.setRemark(billCheck.getRemark());
							bc.setAuditorid(account.getStaffID());
							bc.setAuditororgID((String)session.get("organizationID"));
							bc.setAuditorcompanyid(account.getCompanyID());
							bc.setAudittime(new Date());
							String hqlstaff = "from Staff where staffID = ?";
							Staff staff  = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff, new Object[]{account.getStaffID()});
							bc.setAuditorname(staff.getStaffName());
							String hqlorg = "from COrganization where organizationID = ?";
							COrganization org = (COrganization) baseBeanService.getBeanByHqlAndParams(hqlorg, new Object[]{(String)session.get("organizationID")});
							bc.setAuditororgName(org.getOrganizationName());
							Company c = (Company) session.get("currentcompany");
							bc.setAuditorcompanyname(c.getCompanyName());
							bc.setAuditorstatus("02");
							baseBeanList.add(bc);
						}
					}
					if(bjstatus!=null&&bjstatus.equals("wqrbj")){
					  
					String hql = "from CashierBills where cashierBillsID=?";
					CashierBills cashierBills1 = (CashierBills) baseBeanService
							.getBeanByHqlAndParams(hql,
									new Object[] { csbIDarray[j].trim() });
					
					
					String hqlr = "from RelatedBill where cashid = ?";
					List<BaseBean> ralatelist = baseBeanService.getListBeanByHqlAndParams(hqlr,new Object[]{cashierBills1.getCashierBillsID()});
					
					if(ralatelist.size()==0){
					
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
					String hqlgoods = "from GoodsBills where cashierBillsID  = ? and isSelected = ?";
					List<BaseBean> goodlist = baseBeanService.getListBeanByHqlAndParams(hqlgoods,new Object[]{cashierBills1.getCashierBillsID(),"01"});
					
					for(BaseBean g:goodlist){
						GoodsBills goodf= (GoodsBills) g;
						GoodsBills dbkgood = (GoodsBills) goodf.cloneGoodsBills();
						dbkgood.setGoodsBillsKey(null);
						dbkgood.setGoodsBillsID(serverService.getServerID("goodsbills"));
						dbkgood.setCashierBillsID(dbokuan.getCashierBillsID());
						baseBeanList.add(dbkgood);
					}
					//关联表
					RelatedBill rb = new RelatedBill();
					rb.setRbID(serverService.getServerID("rbID"));
					rb.setCashid(cashierBills1.getCashierBillsID());
					rb.setCashfid(dbokuan.getCashierBillsID());
					baseBeanList.add(rb);
					
					
					
				

					  }
					}

				} catch (RuntimeException e) {

					e.printStackTrace();
				}
				
				
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(
					baseBeanList, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";

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
	public ShowExcelService getExcelService() {
		return excelService;
	}
	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
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
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public CLogBookService getLogBookService() {
		return logBookService;
	}
	public void setLogBookService(CLogBookService logBookService) {
		this.logBookService = logBookService;
	}
	public CostSheetBill getCostSheetBill() {
		return costSheetBill;
	}
	public void setCostSheetBill(CostSheetBill costSheetBill) {
		this.costSheetBill = costSheetBill;
	}

	public String getTreeid() {
		return treeid;
	}
	public void setTreeid(String treeid) {
		this.treeid = treeid;
	}
	public List<BaseBean> getStaffList() {
		return staffList;
	}
	public void setStaffList(List<BaseBean> staffList) {
		this.staffList = staffList;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public GoodsManage getGoods() {
		return goods;
	}
	public void setGoods(GoodsManage goods) {
		this.goods = goods;
	}
	public List<BaseBean> getBeans() {
		return beans;
	}
	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}
	public List<CCode> getCostTypeList() {
		return costTypeList;
	}
	public void setCostTypeList(List<CCode> costTypeList) {
		this.costTypeList = costTypeList;
	}
	public List<CCode> getPriceManageList() {
		return priceManageList;
	}
	public void setPriceManageList(List<CCode> priceManageList) {
		this.priceManageList = priceManageList;
	}




	public List<BaseBean> getProjectlist() {
		return projectlist;
	}




	public void setProjectlist(List<BaseBean> projectlist) {
		this.projectlist = projectlist;
	}




	public String getResult() {
		return result;
	}




	public void setResult(String result) {
		this.result = result;
	}




	public List<BaseBean> getBeanss() {
		return beanss;
	}




	public void setBeanss(List<BaseBean> beanss) {
		this.beanss = beanss;
	}







	public CashierBills getCashierBills() {
		return cashierBills;
	}



	public void setCashierBills(CashierBills cashierBills) {
		this.cashierBills = cashierBills;
	}



	public void setDetail(GoodsBills detail) {
		this.detail = detail;
	}



	public String getCashierBillsID() {
		return cashierBillsID;
	}



	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}



	public String getGoodsBillsID() {
		return goodsBillsID;
	}



	public void setGoodsBillsID(String goodsBillsID) {
		this.goodsBillsID = goodsBillsID;
	}



	public GoodsBills getDetail() {
		return detail;
	}



	public CostSheetBillSearch getCsbSearch() {
		return csbSearch;
	}



	public void setCsbSearch(CostSheetBillSearch csbSearch) {
		this.csbSearch = csbSearch;
	}


	public BillCheck getBillCheck() {
		return billCheck;
	}


	public void setBillCheck(BillCheck billCheck) {
		this.billCheck = billCheck;
	}


	public Staff getStaff() {
		return staff;
	}


	public void setStaff(Staff staff) {
		this.staff = staff;
	}


	public String getBjstatus() {
		return bjstatus;
	}


	public void setBjstatus(String bjstatus) {
		this.bjstatus = bjstatus;
	}
	
	
	
}