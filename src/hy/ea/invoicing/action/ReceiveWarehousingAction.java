package hy.ea.invoicing.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.company.vo.ContactCompanyView;
import hy.ea.bo.finance.BillCheck;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.GoodsNum;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

/**
 * 领用出库
 * 
 * @author xyz
 * 
 */
public class ReceiveWarehousingAction {
	@Resource
	private BaseBeanService baseBeanService;//基本
	@Resource
	private CLogBookService logBookService;//日志
	@Resource
	private CCodeService codeService;//基本数据，往来单位，往来个人
	@Resource
	private ServerService serverService;//对象主键的生成
	
	private CashierBills cashierBills;//出纳单表
	private FinancialBill financialBill; // 进销存单据子表
	private GoodsBills fBillsGood;// 物品子表
	private stockInv stockinv;//库存盘点表
	private PageForm pageForm;
	private String parameter;//调拨出库查询出库物品参数（条码或物品编号）
	private String depotid;//调拨出库查询出库物品参数（调出仓库）
	private int pageNumber;
	private String search;
	private Map<String, GoodsBills> goodsmapold;//微分金出库物品（不考虑库房）
	private Map<String, GoodsBills> goodsmap;
	private Map<String, String> billcheckmap;//单据审核map
	private List<CCode> logisticsList;
	private List<CCode> connectionlist;//单位往来关系
	private List<CCode> codeRelationList;//个人往来关系
	private List<CCode> typelist;//仓库管理
	private String companyname;
	private String organizationname;
	private String status;
	private String arrgoodsNum;//出库物品编码号
	
	private String goodsID;
	/**
	 * 仓库列表中--库列表
	 */
	private List<BaseBean> billgoodlist;
	private List<GoodsBills> BillsGoodList;
	private String message;
	private ContactCompanyView contactCompanyView1;
	private String sdate;
	private String edate;    
    private String print;
    private int camount;
	private String result;
	private String subtype;//保存或提交审核
	private String deptpost;//单据审核的职务
	private String remarks;//审核备注
	private int loginstaffcheck;//单据审核人中有没有当前登录人参数
	/**
	 * 责任人
	 */
	private List<BaseBean> stafflist;
	/**
	 * 前台传入参数 作用于调拨出库 状态值 07
	 */
	private String billStatus;
    private CashierBillsVO cashierBillsVO;//出纳单的视图（用于调拨出库获取相关数据）
	/**
	 * 单据类别
	 */
	private List<CCode> billsType;
	private String type;//判断是添加还是修改
	/**
	 * 审核信息
	 */
	private List<BaseBean> BillCheckList;
	/**
	 * 出纳记账列表（领用出库）
	 * 
	 * @param : account.getCompanyID(), organizationID
	 * @return : list
	 */
	public String getWareManagementList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String staffhql = "from Staff s where exists ( select staffID from COS c " 
		+ "where c.staffID=s.staffID and c.companyID=? " 
		+ "and c.cosStatus=? and c.organizationID=? )";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql,
				new Object[] { account.getCompanyID(), "50", organizationID });
		//单位往来关系
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		//个人往来关系
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		List<Object> list = getlists();
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, parms);
		return "warelist";
	}
	/**
	 * 查询列表（可根据条件查询）被调用
	 *  billsType 单据类型（调拨出库单）
	 *  status  已三审通过（07）
	 *  statusbill 收入预算单（01）
	 * @param : CashierBills 查询条件
	 * @return ：beanlist
	 */
	private List<Object> getlists() {
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		String Hql = " from CashierBills";
		Hql += " where 1=1 ";
		Hql += " and groupCompanySn = ? ";
		parms.add(groupCompanySn);
		Hql += " and companyid = ? ";
		parms.add(account.getCompanyID());
		Hql += " and organizationid = ? ";
		parms.add(organizationID);
		if (search != null && search.equals("search")) {
			cashierBills = (CashierBills) session.get("cashierBills");
			if (cashierBills != null) {
				if (null != cashierBills.getJournalNum().trim()
						&& !"".equals(cashierBills.getJournalNum().trim())) {
					Hql += " and journalNum like ? ";
					parms.add("%" + cashierBills.getJournalNum().trim() + "%");
				}
				if (null != cashierBills.getStaffName().trim()
						&& !"".equals(cashierBills.getStaffName().trim())) {
					Hql += " and staffname like ?";
					parms.add("%" + cashierBills.getStaffName().trim()+ "%");
				}
				if (sdate != null && edate != null && !("").equals(sdate)
						&& !("").equals(edate)) {
					Hql += " and cashierDate between ? and ? ";

					parms.add(Utilities.getDateFromString(sdate + " 00:00:00",
							"yyyy-MM-dd hh:mm:ss"));

					parms.add(Utilities.getDateFromString(edate + " 23:59:59",
							"yyyy-MM-dd hh:mm:ss"));
				}
				if (null != cashierBills.getStatus().trim()
						&& !"".equals(cashierBills.getStatus().trim())) {
					Hql += " and status like ? ";
					parms.add(cashierBills.getStatus().trim());
				}
			}
		} else{
			Hql += " and (status = ?";
			parms.add("05");//审核中
			Hql += " or status = ?";
			parms.add("16");//已出库
			Hql += " or status = ?)";
			parms.add("22");//草稿
		}
		Hql += " and billsType = ?";
		parms.add("领用出库单");
		Hql += " and statusbill = ?";
		parms.add("04");//领用出库单
		Hql += " order by cashierDate desc";
		result.add(Hql);
		result.add(parms.toArray());
		return result;
	}
	/**
	 * 调拨出库——条件查询(保存条件)
	 * 
	 * @return
	 */
	public String toSearchWare() {
		ActionContext.getContext().getSession().put("cashierBillsVO",
				cashierBillsVO);
		ActionContext.getContext().getSession().put("sdate",
				sdate);
		ActionContext.getContext().getSession().put("edate",
				edate);
		return getWareManagementList();
	}
	/**
	 * 领用出库管理,添加，修改页面
	 * 
	 * @return
	 */
	public String addWareManagement() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		//责任人name
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		session.put("ManStaffName1", sta.getStaffName()+"---"+sta.getStaffCode());
		session.put("ManStaffName", sta.getStaffName());
		session.put("ManStaffCode", sta.getStaffCode());
		session.put("ManStaffId", sta.getStaffID());
		String companyID = account.getCompanyID();
		//物流方式
		logisticsList = codeService.getCCodeListByPID(companyID,
				"scode20110106hfjes5ucxp0000000021");
		//选择仓库
		Object[] params = {account.getCompanyID()};
		typelist = codeService.getCCodeListByPID(account.getCompanyID(),"scode20101014v5zed7cukk0000000004");
		pageForm = baseBeanService
				.getPageForm(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber==0?10:pageNumber),
					" from DepotManage where companyID = ? and depotState='00'",
					params);
		if(cashierBills!=null)
		{
			//单据父表
			String hql="from CashierBills where groupCompanySn = ? and companyID = ? " +
					"and organizationID=? and cashierBillsID=?";
			cashierBills=(CashierBills)baseBeanService.getBeanByHqlAndParams(hql, 
					new Object[]{groupCompanySn,account.getCompanyID(),
					organizationID,cashierBills.getCashierBillsID()});
			//单据子表
			String hqlf="from FinancialBill where cashierBillsID=?";
			financialBill=(FinancialBill)baseBeanService.getBeanByHqlAndParams(hqlf, 
					new Object[]{cashierBills.getCashierBillsID()});
			String hqlg="from GoodsBills where cashierBillsID=? order by goodsNomber";
			Object[] param = {cashierBills.getCashierBillsID()};
			billgoodlist=baseBeanService.getListBeanByHqlAndParams(hqlg, param);
			type="edit";
			//关联的审核信息
			BillCheck bCheck=(BillCheck)baseBeanService.getBeanByHqlAndParams("from BillCheck " +
					"where newBillsID=?", new Object[]{financialBill.getCashierBillsID()});
			if(bCheck!=null){
				BillCheckList=baseBeanService.getListBeanByHqlAndParams("from BillCheck where " +
						"firstBillsID=? order by audittime desc", new Object[]{bCheck.getFirstBillsID()});
				loginstaffcheck=0;
				if(BillCheckList.size()>0){
					billcheckmap=new HashMap<String, String>();
					for(int i=0;i<BillCheckList.size();i++){
						BillCheck bc=(BillCheck) BillCheckList.get(i);
						if(bc.getDeptpost()!=null){
							billcheckmap.put(bc.getDeptpost(), bc.getAuditorname());
							if((bc.getAuditorid()).equals(sta.getStaffID())){
								loginstaffcheck++;
							}
						}
					}
				}
			}
		}
		else{type="add";}
		return "wareadd";
	}
	/**
	 * 领用出库—保存
	 * @throws ParseException 
	 */
	public String saveWareManagement() throws ParseException {
		System.out.println("开始保存");
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String corhql = "from COrganization where organizationID = ?";
		COrganization corgan = (COrganization) baseBeanService.getBeanByHqlAndParams(corhql,new Object[] {organizationID});
		String groupCompanySn = session.get("groupCompanySn").toString();
		//当前登录人员
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		//主单据数据保存
		CashierBills cashierbill=new CashierBills();
		cashierbill.setCashierBillsID(serverService.getServerID("cashierTally"));
		cashierbill.setGroupCompanySn(groupCompanySn);
		cashierbill.setCompanyID(account.getCompanyID());
		cashierbill.setCompanyName(account.getCompanyName());
		cashierbill.setOrganizationID(organizationID);
		cashierbill.setDepartmentID(organizationID);
		cashierbill.setDepartmentName(corgan.getOrganizationName());
		cashierbill.setCompanyBankNum("");//单位银行账号
		cashierbill.setPcompanyID(account.getCompanyID());//父公司
		cashierbill.setPcompanyName(account.getCompanyName());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(Utilities.getDateString(new Date(),"yyyy-MM-dd HH:mm:ss"));
		cashierbill.setCashierDate(date);// 制单日期
		cashierbill.setBillsType("领用出库单");// 单据类型
		cashierbill.setJournalNum(financialBill.getJournalNum());// 凭证号(单据编号)
		/******责任人******/
		cashierbill.setStaffID(sta.getStaffID());// 责任人ID（制单人）
		cashierbill.setStaffName(sta.getStaffName());//责任人name
		cashierbill.setStaffCode(sta.getStaffCode());// 责任人编号
		/*******制单人*********/		
		cashierbill.setInputid(sta.getStaffID());//制单人员id
		cashierbill.setInputName(sta.getStaffName());//制单人名称
		cashierbill.setInputCompanyid(account.getCompanyID());//制单人公司id
		cashierbill.setInputCompanyName(account.getCompanyName());//制单人公司名称
		String coshql = "from COS where staffID = ?";
		COS cos = (COS) baseBeanService.getBeanByHqlAndParams(coshql,new Object[] { account.getStaffID()});
		cashierbill.setInputOrganizationID(cos.getOrganizationID());//制单人部门id
		COrganization cor = (COrganization) baseBeanService.getBeanByHqlAndParams(corhql,new Object[] { cos.getOrganizationID()});
		cashierbill.setInputOrganizationName(cor.getOrganizationName());//制单人部门名称
		//保存
		if(subtype.equals("save")){
			cashierbill.setStatus("22");
		}else if(subtype.equals("savecheck")){
			cashierbill.setStatus("05");//审核中
		}
		cashierbill.setStatusbill("04");		
		baseBeanList.add(cashierbill);
		
		financialBill.setCashierBillsID(cashierbill.getCashierBillsID());
		financialBill.setFinancialbillID(serverService.getServerID("financial"));
		parameter = "添加领用出库单（凭证号" + financialBill.getJournalNum() + "）";
		financialBill.setOrganizationID(organizationID);
		financialBill.setCompanyID(account.getCompanyID());
		financialBill.setGroupCompanySn(groupCompanySn);
		financialBill.setBillsType("领用出库单");
		if(subtype.equals("save")){
			financialBill.setBillStatus("22");
		}else if(subtype.equals("savecheck")){
			financialBill.setBillStatus("05");//审核中
		}
		financialBill.setBillsDate(new Date());
		financialBill.setBillStaffName(sta.getStaffName());
		financialBill.setBillStaffID(account.getStaffID());
		//调入机构
		if (financialBill.getDrccompanyID() != null
				&& !"".equals(financialBill.getDrccompanyID())) {
			String type=(financialBill.getDrccompanyID()).substring(0,5);
			String hql3="";
			if(type.equals("compa")){
				 hql3 = " from Company where companyID=? ";
			}else if(type.equals("group"))
			{hql3 = " from Company where groupCompanySn=? ";}
			Company company = (Company) baseBeanService
					.getBeanByHqlAndParams(hql3, new Object[] { financialBill
							.getDrccompanyID() });
			financialBill.setDrccompanyName(company.getCompanyName());
		}
		baseBeanList.add(financialBill);
		
		String goodsNumHql = null;
		List<String> sql=new ArrayList<String>();
		List<Object[]> obj=new ArrayList<Object[]>(); 
		if (goodsmap != null) {
			for (GoodsBills goods : goodsmap.values()) {
				List<Object> goodsNumObj = new ArrayList<Object>();
				goods.setCashierBillsID(cashierbill.getCashierBillsID());
				goods.setGoodsBillsID(serverService.getServerID("goodsbills"));
				goods.setCompanyID(account.getCompanyID());
				String hql = "from Inventory where goodsID=?";
				Inventory inventory = (Inventory) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { goods
								.getGoodsID() });
				goods.setGoodsName(inventory.getGoodsName());
				goods.setGoodsNum(inventory.getGoodsCoding());
				goods.setStandard(inventory.getStandard());
				goods.setTypeID(inventory.getGoodsType());
				if(subtype.equals("save")){
					goods.setKcStatus("22");//草稿
				}else if(subtype.equals("savecheck")){
					goods.setKcStatus("05");//审核中
				}
				goods.setGoodstatus("00");
				if (goods.getGoodsID() != null&& !"".equals(goods.getGoodsID())) {
					Inventory inventory1 = new Inventory();
					//查找对应的库存数据
					String hql1 = " from Inventory where inventoryID=? and companyID=? and warehouse=? ";
					inventory1 = (Inventory) baseBeanService
							.getBeanByHqlAndParams(hql1, new Object[] {goods.getInventoryID(),account.getCompanyID(),goods.getDepotID() });
					if (inventory1 == null) {
						message = "所选库房中没有所填物品  " + inventory.getGoodsName()
								+ "!  请核对信息...";
						return "failed";
					} else {
						//判断库存数量是否满足出库
						int invenQuant = Integer.parseInt(inventory.getInvenQuantity());// 结存商品数量
						int quantity = Integer.parseInt(goods.getQuantity());// 出库商品数量
						if (invenQuant >= quantity) {
							goods.setInventoryID(inventory.getInventoryID());//库存表id
						} else {
							message = "所选出库物品 " + inventory.getGoodsName()
									+ " 的数量不足 " + quantity + ", 库存数量为 "
									+ invenQuant;
						    return "failed";
						}
					}
					baseBeanList.add(goods);
				}
				//修改物品序号表中的状态
				goodsNumHql="update dtGoodsNum set goodsBillsOldID=?,status=? where companyID=? and cashierBillsID=? and status=?";
				goodsNumObj.add(goods.getGoodsBillsID());goodsNumObj.add("06");goodsNumObj.add(account.getCompanyID());
				goodsNumObj.add(goods.getInventoryID());goodsNumObj.add("03");
				String[] str=goods.getGoodsnumber().split("-");
                goodsNumHql+=" and goodsnumber=?";
                goodsNumObj.add(goods.getGoodsnumber());
				sql.add(goodsNumHql);
				obj.add(goodsNumObj.toArray());
			}
			//改变物品存货编码状态
			//dd(baseBeanList,goodsmap,arrgoodsNum);
		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		baseBeanList.add(logBook);
		String[] sss=new String[sql.size()];
		//baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,null);
		baseBeanService.executeSqlsByParmsList(baseBeanList, sql.toArray(sss), obj);
		return "success";
	}
	/**
	 * 改变单个物品的状态
	 * @param baseBeanList
	 */
	private void dd(List<BaseBean> baseBeanList,Map<String, GoodsBills> goodsmap,String arrGoodsNum)
	{
		int i =0;
		String[] arr = arrGoodsNum.split(",");
		for (GoodsBills goods : goodsmap.values()) {
			String goodsId = goods.getGoodsID();//物品id
			String inventoryId = goods.getInventoryID();//库房id
			String hql = " from GoodsNum where cashierBillsID = ? and goodsID = ? and goodsnumber = ?";
			GoodsNum goodsNum = (GoodsNum)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{inventoryId,goodsId,arr[i]});
			goodsNum.setStatus("04");
			baseBeanList.add(goodsNum);			
			i++;
		}
	}
	/**
	 * 出库管理——删除
	 */
	public String deleteWareManagement() throws ParseException {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String groupCompanySn = session.get("groupCompanySn").toString();
		if(groupCompanySn==null){
			return "login";
		}
		
		List<String> sql=new ArrayList<String>();
		List<Object[]> obj=new ArrayList<Object[]>(); 
		
		String hqlcashb = "delete CashierBills where cashierBillsID = ?";
		String hqlfinan = "delete FinancialBill where cashierBillsID = ?";
		sql.add(hqlcashb);
		sql.add(hqlfinan);
		obj.add(new Object[]{cashierBills.getCashierBillsID()});
		obj.add(new Object[]{cashierBills.getCashierBillsID()});
		
		String hqlgood = "from GoodBills where cashierBillsID = ?";
		List<BaseBean> goodsList = baseBeanService.getListBeanByHqlAndParams(hqlgood, new Object[]{cashierBills.getCashierBillsID()});
		for(int i=0;i<goodsList.size();i++){
			List<Object> goodsNumObj = new ArrayList<Object>();
			GoodsBills goods = (GoodsBills)goodsList.get(i);
			
			String goodsNumHql1 = "from GoodsNum where where companyID=? and cashierBillsID=? and status=?";
			GoodsNum goodsNum = (GoodsNum)baseBeanService.getBeanByHqlAndParams(goodsNumHql1,new Object[]{account.getCompanyID(),goods.getCashierBillsID(),"06"});
			
			String goodsNumHql="update GoodsNum set goodsBillsID=?,status=? where companyID=? and cashierBillsID=? and status=?";
			goodsNumObj.add(goodsNum.getGoodsBillsOldID());goodsNumObj.add("03");goodsNumObj.add(account.getCompanyID());
			goodsNumObj.add(goods.getCashierBillsID());goodsNumObj.add("06");
			String[] str=goods.getGoodsnumber().split("-");
			if(str[0]!=null&&!"".equals(str[0])){
				goodsNumHql+=" and goodsnumber>=?";
				goodsNumObj.add(str[0]);
			}
			if(str[1]!=null&&!"".equals(str[1])){
				goodsNumHql+=" and goodsnumber<=?";
				goodsNumObj.add(str[1]);
			}
			sql.add(goodsNumHql);
			obj.add(goodsNumObj.toArray());
		}
		String[] sss=new String[sql.size()];
		baseBeanService.executeSqlsByParmsList(null, sql.toArray(sss), obj);
		return "success";
	}
	
	/**
	 * 修改查看
	 */
	public String getcashierBillstatus() throws UnsupportedEncodingException {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Object[] params = { cashierBillsVO.getCashierBillsID()};
		String hql = "from CashierBills where cashierBillsID= ?";
		CashierBills cashier= (CashierBills) baseBeanService.getBeanByHqlAndParams(
				hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("getstatus", cashier.getStatus());
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**
	 * 出库管理——修改
	 */
	public String editWareManagement() throws ParseException {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		if(groupCompanySn==null){
			return "login";
		}
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		//调拨出库父表数据
		String hqlcashb = "from CashierBills where cashierBillsID = ?";
		CashierBills cashierbills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqlcashb,new Object[] { cashierBills.getCashierBillsID() });
		if(subtype.equals("save")){
			cashierbills.setStatus("22");
		}else if(subtype.equals("savecheck")){
			cashierbills.setStatus("05");//审核中
		}
		baseBeanList.add(cashierbills);
		//修改单据子数据
		String hqlcash = "from FinancialBill where cashierBillsID = ?";
		FinancialBill fiBill = (FinancialBill) baseBeanService.getBeanByHqlAndParams(hqlcash,new Object[] { cashierBills.getCashierBillsID() });
		parameter = "修改领用出库单（单据编号" + fiBill.getJournalNum() + "）";
		fiBill.setCcompanyID(financialBill.getCcompanyID());
		fiBill.setCcompanyName(financialBill.getCcompanyName());
		fiBill.setCcompanyTel(financialBill.getCcompanyTel());
		//调入机构
		if (financialBill.getDrccompanyID() != null
				&& !"".equals(financialBill.getDrccompanyID())) {
			String type=(financialBill.getDrccompanyID()).substring(0,5);
			String hql3="";
			if(type.equals("compa")){
				 hql3 = " from Company where companyID=? ";
			}else if(type.equals("group"))
			{hql3 = " from Company where groupCompanySn=? ";}
			Company company = (Company) baseBeanService
					.getBeanByHqlAndParams(hql3, new Object[] { financialBill
							.getDrccompanyID() });
			fiBill.setDrccompanyID(financialBill.getDrccompanyID());
			fiBill.setDrccompanyName(company.getCompanyName());
		}
		fiBill.setDrccompanyTel(financialBill.getDrccompanyTel());
		fiBill.setStaffID(financialBill.getStaffID());
		fiBill.setStaffName(financialBill.getStaffName());
		fiBill.setStaffPhone(financialBill.getStaffPhone());
		fiBill.setPurchaseDate(financialBill.getPurchaseDate());
		fiBill.setSubjectID(financialBill.getSubjectID());
		fiBill.setSubjectName(financialBill.getSubjectName());
		fiBill.setLogisticsType(financialBill.getLogisticsType());
		fiBill.setDepotID(financialBill.getDepotID());
		fiBill.setDepotName(financialBill.getDepotName());
		fiBill.setDrdepotID(financialBill.getDrdepotID());
		fiBill.setDrdepotName(financialBill.getDrdepotName());
		fiBill.setStaffsID(financialBill.getStaffsID());//出库人
		fiBill.setStaffsName(financialBill.getStaffsName());
		fiBill.setGoodsmoney(financialBill.getGoodsmoney());//合计
		if(subtype.equals("save")){
			fiBill.setBillStatus("22");
		}else if(subtype.equals("savecheck")){
			fiBill.setBillStatus("05");//审核中
		}
		baseBeanList.add(fiBill);
		if(goodsmapold!=null){
			for(GoodsBills goods:goodsmapold.values()){
				//更改单据物品信息
				String hqlgb = "from GoodsBills where goodsBillsID = ?";
				GoodsBills goodsbills = (GoodsBills) baseBeanService.getBeanByHqlAndParams(hqlgb,
						new Object[] { goods.getGoodsBillsID() });
				goodsbills.setGoodsNomber(goods.getGoodsNomber());//更改序号
				String hql = "from GoodsManage g where g.goodsID=?";
				GoodsManage goodsManage = (GoodsManage) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { goodsbills
								.getGoodsID() });
				goodsbills.setGoodsName(goodsManage.getGoodsName());
				goodsbills.setGoodsNum(goodsManage.getGoodsCoding());
				goodsbills.setStandard(goodsManage.getMnemonicCode());
				goodsbills.setTypeID(goodsManage.getTypeID());
				if(subtype.equals("save")){ 
					goodsbills.setKcStatus("22");//草稿
				}else if(subtype.equals("savecheck")){
					goodsbills.setKcStatus("05");//审核中
				}
				goodsbills.setGoodstatus("00");
				baseBeanList.add(goodsbills);
			}
		}
		if (goodsmap != null) {
			for (GoodsBills goods : goodsmap.values()) {
				goods.setCashierBillsID(cashierBills.getCashierBillsID());
				goods.setGoodsBillsID(serverService.getServerID("goodsbills"));
				goods.setCompanyID(account.getCompanyID());
				String hql = "from GoodsManage g where g.goodsID=?";
				GoodsManage goodsManage = (GoodsManage) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { goods
								.getGoodsID() });
				goods.setGoodsName(goodsManage.getGoodsName());
				goods.setGoodsNum(goodsManage.getGoodsCoding());
				goods.setStandard(goodsManage.getMnemonicCode());
				goods.setTypeID(goodsManage.getTypeID());
				if(subtype.equals("save")){
					goods.setKcStatus("22");//草稿
				}else if(subtype.equals("savecheck")){
					goods.setKcStatus("05");//审核中
				}
				goods.setGoodstatus("00");
				if (goods.getGoodsID() != null&& !"".equals(goods.getGoodsID())) {
					Inventory inventory = new Inventory();
					String hql1 = " from Inventory where inventoryID=? and companyID=? and weizhi=?";
					inventory = (Inventory) baseBeanService
							.getBeanByHqlAndParams(hql1, new Object[] {goods.getInventoryID(),account.getCompanyID(),goods.getDepotName() });
					if (inventory == null) {
						message = "所选的物品中对应库房中不存在" + goodsManage.getGoodsName()
								+ "!  请核对信息...";
						return "failed";
					} else {
						//改变库存数量和总价
						int invenQuant = Integer.parseInt(inventory.getInvenQuantity());// 结存商品数量
						int quantity = Integer.parseInt(goods.getQuantity());// 出库商品数量
						if (invenQuant >= quantity) {
							goods.setInventoryID(inventory.getInventoryID());//库存表id
						} else {
							message = "所选出库物品 " + goodsManage.getGoodsName()
									+ " 的数量不足 " + quantity + ", 库存数量为 "
									+ invenQuant;
						    return "failed";
						}
					}
					baseBeanList.add(goods);
				}
			}
			//改变物品存货编码状态
			dd(baseBeanList,goodsmap,arrgoodsNum);
		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,null);
		return "success";
	}
	/**
	 *通过单据编号查询出纳单对象
	 * @return 
	 * @return
	 */
	public String toWareManagementCashierBills(){
		//出纳单表
		String hqls = "from CashierBills where  journalNum=?";
		cashierBills = (CashierBills) baseBeanService
				.getBeanByHqlAndParams(hqls, new Object[] { cashierBills.getJournalNum() });
		return toWareManagement();
	}
	/**
	 * 销售出库管理—查看和打印预览
	 * 
	 * @return
	 */
	public String toWareManagement() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		//责任人name
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		session.put("ManStaffName", sta.getStaffName());
		session.put("ManStaffCode", sta.getStaffCode());
		session.put("ManStaffId", sta.getStaffID());
		//出纳单表
		String hqls = "from CashierBills where  cashierBillsID=?";
		cashierBills = (CashierBills) baseBeanService
				.getBeanByHqlAndParams(hqls, new Object[] { cashierBills.getCashierBillsID() });
		//出纳单子表
		String hql = "from FinancialBill where groupCompanySn = ? and companyid = ? and organizationID=? and cashierBillsID=?";
		financialBill = (FinancialBill) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { groupCompanySn, account.getCompanyID(),
						organizationID, cashierBills.getCashierBillsID()});
		
		BillsGoodList = new ArrayList<GoodsBills>();
		String hqlg = "from GoodsBills where cashierBillsID=? order by goodsNomber";
		List<BaseBean> financialBillsGoodList = baseBeanService
				.getListBeanByHqlAndParams(hqlg, new Object[] {cashierBills.getCashierBillsID()});
		//计算总金额
		/*if(financialBillsGoodList!=null){
		    int size = financialBillsGoodList.size();
			for(int i=0;i<size;i++) {
			fBillsGood=(GoodsBills) financialBillsGoodList.get(i); 
			int amount=Integer.parseInt(fBillsGood.getMoney());
			camount+=amount;
		     }
		}*/
		if (financialBillsGoodList != null
				&& financialBillsGoodList.size() != 0
				&& financialBillsGoodList.get(0) != null) {
			for (int i = 0; i < financialBillsGoodList.size(); i++) {
				GoodsBills good = (GoodsBills) financialBillsGoodList
						.get(i);
				String hql1 = " from Inventory where inventoryID=?";
				Inventory inventory = (Inventory) baseBeanService
						.getBeanByHqlAndParams(hql1,
								new Object[] { good.getInventoryID() });
				if (inventory != null) {
					BillsGoodList.add(good);
				}else{
					BillsGoodList.add(good);
				}
			}
		}
		if(financialBill!=null){
			 //调出机构
			String hql3 = " from ContactCompanyView where ccompanyID=? ";
			contactCompanyView1 = (ContactCompanyView) baseBeanService
					.getBeanByHqlAndParams(hql3, new Object[] { financialBill
							.getCcompanyID() });
			/*String hqlcc = " from ContactCompany c where c.ccompanyID=?";
			ContactCompany contactcompany= (ContactCompany) baseBeanService
					.getBeanByHqlAndParams(hqlcc, new Object[] {financialBill
							.getCcompanyID() });*/
			//公司名称
			String comhql = "from Company where companyID=?";
			Company company = (Company) baseBeanService.getBeanByHqlAndParams(
					comhql, new Object[] { financialBill.getCompanyID() });
			companyname = company.getCompanyName();
	        //部门名称
			String orghql = "from COrganization where companyID=? and organizationID=?";
			COrganization cOrganization = (COrganization) baseBeanService
					.getBeanByHqlAndParams(orghql, new Object[] {
							financialBill.getCompanyID(),
							financialBill.getOrganizationID() });
			organizationname = cOrganization.getOrganizationName();
			//审核信息
			BillCheck bCheck=(BillCheck)baseBeanService.getBeanByHqlAndParams("from BillCheck where newBillsID=?", 
					new Object[]{financialBill.getCashierBillsID()});
			if(bCheck!=null){
				BillCheckList=baseBeanService.getListBeanByHqlAndParams("from BillCheck where " +
						"firstBillsID=? order by audittime desc", new Object[]{bCheck.getFirstBillsID()});
				loginstaffcheck=0;
				if(BillCheckList.size()>0){
					billcheckmap=new HashMap<String, String>();
					for(int i=0;i<BillCheckList.size();i++){
						BillCheck bc=(BillCheck) BillCheckList.get(i);
						if(bc.getDeptpost()!=null){
							billcheckmap.put(bc.getDeptpost(), bc.getAuditorname());
							billcheckmap.put(bc.getDeptpost()+"zt",bc.getAuditorstatus());
							if((bc.getAuditorid()).equals(sta.getStaffID())){
								loginstaffcheck++;
							}
						}
					}
				}
			}
		}
		
		if(print!=null&&"print".equals("print")){
			return "wareeditprint";
		}
		return "wareedit";
	}
	/**
	 * 获取可用序列号范围 
	 */
	public String ajaxRangeNumber(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		String hql="from GoodsNum where goodsID=? and companyID=? and status = ? order by goodsnumber";
		List<BaseBean> goodsRangeList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{goodsID,account.getCompanyID(),"04"});
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("goodsRangeList",goodsRangeList);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}
	/**
	 * 获取所填序号中可用数量
	 */
	@SuppressWarnings("unchecked")
	public String ajaxTotalNumber(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		HttpServletRequest re=ServletActionContext.getRequest();
		String start=re.getParameter("start");String end=re.getParameter("end");
		String invID=re.getParameter("invID");
		List<Object> list=new ArrayList<Object>();
		String sql="select goodsnumber from dtGoodsNum where goodsID=? and companyID=? and status=? and cashierBillsID=?";
		list.add(goodsID);list.add(account.getCompanyID());list.add("03");list.add(invID);
		/*字符串类型数字超过10以后,大于或者小于的判断将不再适用*/
		if(start!=null&&!"".equals(start)){
			sql+=" and goodsnumber>=?";
			list.add(start);
		}
		if(end!=null&&!"".equals(end)){
			sql+=" and goodsnumber<=?";
			list.add(end);
		}
		List<BaseBean> goodsNumList=baseBeanService.getListBeanBySqlAndParams(sql, list.toArray());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("list",goodsNumList);
		map.put("number",goodsNumList.size());
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		
		
		return "success";
	}
	/**
	 * 从dtgoodsnum表（对应GoodsNum.java对象）中获取库存商品的明细列表（即需要获取到库存中的每件物品，以达到通过物品编号和每件物品的序列号，跟踪物品的出库与库存情况）。
	 */
	public String getInventoryWithGoodsNum() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");		
		if (account == null) {			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String companyid = account.getCompanyID();
		String sn = session.get("groupCompanySn").toString();
		String hql = "from Inventory where companyID = ? and (groupCompanySn = ? or groupCompanySn is null) and warehouse = ? ";
//		String sql = "select dgs.cashierbillsid,invt.warehouseName,invt.goodsName,decode(invt.barCode,NULL, ' ',invt.barcode) as barCode,invt.goodscoding,invt.goodsType,1/*invt.invenQuantity*/," +
//				"decode(invt.unit,NULL,' ',invt.unit) as unit,invt.goodsID,dgs.gnID,dgs.goodsNumber,invt.subjectsName,invt.subjectsID,invt.warehouse,invt.standard,invt.unitPrice from dtgoodsnum dgs left join dt_inv_invt invt " +
//				"on dgs.cashierBillsID = invt.inventoryID left join dtgoodsmanage man on invt.goodsid=man.goodsid  where dgs.status<'04' and dgs.companyid = ? and (invt.groupCompanySn = ? or invt.groupCompanySn is null) and invt.warehouse=?  and man.goodsid is not null";
		if (!StringUtils.isBlank(parameter.trim())){
			hql += " and (barcode = ? or goodscoding = ?)";
			//sql += " and (invt.barcode = ? or invt.goodscoding = ?) "; 
		}
		//sql += " order by dgs.goodsNumber ";
		List<BaseBean> list = null;
		if (!StringUtils.isBlank(parameter.trim())){
			list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{companyid,sn,depotid,parameter,parameter});
			//list = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{companyid,sn,depotid,parameter,parameter});
		}else{
			list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{companyid,sn,depotid});
			//list = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{companyid,sn,depotid});
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodlist", list);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	/**
	 * （审核通过）出库管理-出库确认
	 * 
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("deprecation")
	public String updatback() throws ParseException {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String corhql = "from COrganization where organizationID = ?";
		COrganization corgan = (COrganization) baseBeanService.getBeanByHqlAndParams(corhql,new Object[] {organizationID});
		String groupCompanySn = session.get("groupCompanySn").toString();
		//当前登录人员
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		List<Object[]> list = new ArrayList<Object[]>();
		List<BaseBean> listbabe = new ArrayList<BaseBean>();
		//出纳单表
		String hqlc = "from CashierBills where  cashierBillsID=?";
		cashierBills = (CashierBills) baseBeanService
				.getBeanByHqlAndParams(hqlc, new Object[] { cashierBills.getCashierBillsID() });
		//出纳单子表
		String hqlf = "from FinancialBill where groupCompanySn = ? and companyid = ? and organizationID=? and cashierBillsID=?";
		financialBill = (FinancialBill) baseBeanService.getBeanByHqlAndParams(
				hqlf, new Object[] { groupCompanySn, account.getCompanyID(),
						organizationID, cashierBills.getCashierBillsID()});
		//状态更改
		String uphqlc = "update CashierBills set status=? where cashierBillsID=? ";
		list.add(new Object[] { "16",cashierBills.getCashierBillsID() });
		String uphqlf = "update FinancialBill set billStatus=? where cashierBillsID=? ";
		list.add(new Object[] { "16",cashierBills.getCashierBillsID() });
		String hql = "update GoodsBills set kcStatus=? where cashierBillsID=? ";
		list.add(new Object[] { "16",cashierBills.getCashierBillsID() });
		String[] hqls = {uphqlc,uphqlf,hql };
		baseBeanService.executeHqlsByParamsList(null, hqls, list);
		//生成归还入库单
		String BillID = serverService.getBillID(account.getCompanyID());
		CashierBills cashierbill=new CashierBills();
		cashierbill.setCashierBillsID(serverService.getServerID("cashierTally"));
		cashierbill.setGroupCompanySn(groupCompanySn);
		cashierbill.setCompanyID(account.getCompanyID());
		cashierbill.setCompanyName(account.getCompanyName());
		cashierbill.setOrganizationID(organizationID);
		cashierbill.setDepartmentID(organizationID);
		cashierbill.setDepartmentName(corgan.getOrganizationName());
		cashierbill.setPcompanyID(account.getCompanyID());//父公司
		cashierbill.setPcompanyName(account.getCompanyName());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(Utilities.getDateString(new Date(),"yyyy-MM-dd HH:mm:ss"));
		cashierbill.setCashierDate(date);// 制单日期
		cashierbill.setBillsType("归还入库单");// 单据类型
		cashierbill.setJournalNum(BillID);// 凭证号(单据编号)
		/*******制单人*********/		
		cashierbill.setInputid(sta.getStaffID());//制单人员id
		cashierbill.setInputName(sta.getStaffName());//制单人名称
		cashierbill.setInputCompanyid(account.getCompanyID());//制单人公司id
		cashierbill.setInputCompanyName(account.getCompanyName());//制单人公司名称
		String coshql = "from COS where staffID = ?";
		COS cos = (COS) baseBeanService.getBeanByHqlAndParams(coshql,new Object[] { account.getStaffID()});
		cashierbill.setInputOrganizationID(cos.getOrganizationID());//制单人部门id
		COrganization cor = (COrganization) baseBeanService.getBeanByHqlAndParams(corhql,new Object[] { cos.getOrganizationID()});
		cashierbill.setInputOrganizationName(cor.getOrganizationName());//制单人部门名称
		cashierbill.setStatus("22");//草稿	
		listbabe.add(cashierbill);
		FinancialBill fbill=new FinancialBill();
		fbill.setCashierBillsID(cashierbill.getCashierBillsID());
		fbill.setFinancialbillID(serverService.getServerID("financial"));
		parameter = "添加归还入库单（凭证号" + BillID + "）";
		fbill.setOrganizationID(organizationID);
		fbill.setCompanyID(account.getCompanyID());
		fbill.setGroupCompanySn(groupCompanySn);
		fbill.setDepartmentID(organizationID);
		fbill.setJournalNum(BillID);
		//调出机构
		fbill.setCcompanyID(financialBill.getCcompanyID());
		fbill.setCcompanyName(financialBill.getCcompanyName());
		fbill.setCcompanyTel(financialBill.getCcompanyTel());
		//调入机构
		fbill.setDrccompanyID(financialBill.getDrccompanyID());
		fbill.setDrccompanyName(financialBill.getDrccompanyName());
		fbill.setDrccompanyTel(financialBill.getDrccompanyTel());
		//对方科目
		fbill.setSubjectID(financialBill.getSubjectID());
		fbill.setSubjectName(financialBill.getSubjectName());
		//物流
		fbill.setLogisticsType(financialBill.getLogisticsType());
		//调入调出仓库
		fbill.setDepotID(financialBill.getDepotID());
		fbill.setDepotName(financialBill.getDepotName());
		fbill.setDrdepotID(financialBill.getDrdepotID());
		fbill.setDrdepotName(financialBill.getDrdepotName());
		
		fbill.setBillsType("归还入库单");
		fbill.setBillStatus("22");//草稿
		fbill.setBillsDate(new Date());
		fbill.setBillStaffName(sta.getStaffName());
		fbill.setBillStaffID(account.getStaffID());
		listbabe.add(fbill);
		//单据物品集合
		String hqlg = "from GoodsBills where cashierBillsID=?";
		List<BaseBean> goodList = baseBeanService
				.getListBeanByHqlAndParams(hqlg, new Object[] {cashierBills.getCashierBillsID()});
		String warehouse=financialBill.getDepotID();//仓库id
		String warehouseName=financialBill.getDepotName();//仓库name
		
		List<String> sql=new ArrayList<String>();
		List<Object[]> obj=new ArrayList<Object[]>();
		for(int i=0;i<goodList.size();i++){
        	GoodsBills good = (GoodsBills) goodList.get(i);
        	List<Object> goodsNumObj = new ArrayList<Object>();
        	//调拨入库单物品保存
        	GoodsBills goodsbill=new GoodsBills();
        	goodsbill.setCashierBillsID(cashierbill.getCashierBillsID());
        	goodsbill.setGoodsBillsID(serverService.getServerID("goodsbills"));
        	goodsbill.setCompanyID(account.getCompanyID());
			String hqlgood = "from GoodsManage g where g.goodsID=?";
			GoodsManage goodsManage = (GoodsManage) baseBeanService
					.getBeanByHqlAndParams(hqlgood, new Object[] { good
							.getGoodsID() });
			goodsbill.setGoodsName(goodsManage.getGoodsName());
			goodsbill.setGoodsNum(goodsManage.getGoodsCoding());
			goodsbill.setStandard(goodsManage.getMnemonicCode());
			goodsbill.setTypeID(goodsManage.getTypeID());
		    goodsbill.setKcStatus("22");//草稿
		    goodsbill.setGoodstatus("00");
		    goodsbill.setDepotID(good.getDepotID());
		    goodsbill.setDepotName(good.getDepotName());
		    goodsbill.setGoodsID(good.getGoodsID());
		    goodsbill.setGoodsName(good.getGoodsName());
		    goodsbill.setGoodsNomber(good.getGoodsNomber());
		    goodsbill.setGoodsNum(good.getGoodsNum());
		    goodsbill.setGoodsnumber(good.getGoodsnumber());
		    goodsbill.setGoodstatus(good.getGoodstatus());//物品状态
		    goodsbill.setGoodsVariableID(good.getGoodsVariableID());
		    goodsbill.setInventoryID(good.getInventoryID());
		    goodsbill.setMoney(good.getMoney());
		    goodsbill.setPrice(good.getPrice());
		    goodsbill.setQuantity(good.getQuantity());
		    goodsbill.setSortCode(good.getSortCode());
		    goodsbill.setTypeID(good.getTypeID());
		    listbabe.add(goodsbill);
		    
        	//库存盘点表
        	stockInv stockinvs = new stockInv();
    		stockinvs.setStockinvID(serverService.getServerID("stockInv"));
    		stockinvs.setCompanyID(account.getCompanyID());
    		stockinvs.setGroupCompanySn(groupCompanySn);
    		stockinvs.setGoodsID(good.getGoodsID());
    		stockinvs.setGoodsType(good.getTypeID());
    		stockinvs.setGoodsName(good.getGoodsName());
    		stockinvs.setInvenQuantity(good.getQuantity());
    		
    		String hql2 = "from Inventory where inventoryID=? and companyID=? and weizhi=? ";
    		Inventory Inventorys = (Inventory) baseBeanService
    				.getBeanByHqlAndParams(hql2, new Object[] {good.getInventoryID(), account.getCompanyID(),good.getDepotName()});
    		if (Inventorys != null) {
    			if (Inventorys.getInvenOnline() != null) {
    				stockinvs.setInvenOnline(Inventorys.getInvenOnline());
    				stockinvs.setInvenUnderline(Inventorys.getInvenUnderline());
    			}
    			int invenQuant = Integer.parseInt(Inventorys.getInvenQuantity());// 结存商品数量
				int quantity = Integer.parseInt(good.getQuantity());// 出库商品数量
				if (invenQuant >= quantity) {
					int sum = invenQuant - quantity;// 出库后剩余商品总数量
					Inventorys.setInvenQuantity(String.valueOf(sum));
					Inventorys.setSumPrice(String.valueOf(Double
							.parseDouble(Inventorys.getUnitPrice())
							* sum));
					listbabe.add(Inventorys);
				} else {
					message = "所选出库物品 " + goodsManage.getGoodsName()
							+ " 的数量不足 " + quantity + ", 库存数量为 "
							+ invenQuant;
				    return "failed";
				}
    		}
    		stockinvs.setWarehouse(warehouse);
			stockinvs.setWarehouseName(warehouseName);
			stockinvs.setStaffID(financialBill.getBillStaffID());//使用人id
			stockinvs.setStaffName(financialBill.getBillStaffName());//使用人name
    		stockinvs.setSummoney(good.getMoney());// 出库商品总金额
    		stockinvs.setIntime(new Date());
    		stockinvs.setType("01");//出库
    		listbabe.add(stockinvs);
    		good.setStockinvID(stockinvs.getStockinvID());
    		listbabe.add(good);
    		
    		String goodsNumHql="update dtGoodsNum set goodsBillsID=?,status=? where companyID=? and cashierBillsID=? and status=?";
			goodsNumObj.add(good.getGoodsBillsID());goodsNumObj.add("04");goodsNumObj.add(account.getCompanyID());
			goodsNumObj.add(good.getInventoryID());goodsNumObj.add("06");
			String[] str=good.getGoodsnumber().split("-");
            goodsNumHql+=" and goodsnumber=?";
            goodsNumObj.add(good.getGoodsnumber());
			sql.add(goodsNumHql);
			obj.add(goodsNumObj.toArray());
        }
		String[] sss=new String[sql.size()];
        //日志记录
        parameter = "员工：" + account.getAccountName()+"，确认出库单据编号是"+cashierBills.getJournalNum();
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		listbabe.add(cLogBook);
		baseBeanService.executeSqlsByParmsList(listbabe, sql.toArray(sss),obj);
		//baseBeanService.saveBeansListAndexecuteHqlsByParams(listbabe, null,null);
		return getWareManagementList();
	}
	/**
	 * 审核信息的保存
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("unused")
	public String saveBillCheck() throws UnsupportedEncodingException {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		//当前登录人员信息
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hqlcash = "from CashierBills where cashierBillsID = ?";
		CashierBills cbills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqlcash,
				new Object[] { cashierBills.getCashierBillsID() });		
		//修改对应物品的状态为驳回      --  03=驳回
		if("03".equals(status)){
			String[] hql={"update CashierBills set status=10 where cashierBillsID=?"};
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null, hql,new Object[]{cashierBills.getCashierBillsID()});
		}
		BillCheck billcheck=new BillCheck();
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		billcheck.setCheckid(serverService.getServerID("billcheck"));
		billcheck.setAuditorcompanyid(cbills.getCompanyID());
		billcheck.setAuditorcompanyname(cbills.getCompanyName());
		billcheck.setAuditororgID(cbills.getDepartmentID());
		billcheck.setAuditororgName(cbills.getDepartmentName());
		billcheck.setNewBillsID(cbills.getCashierBillsID());
		billcheck.setOldBillsID(cbills.getCashierBillsID());
		billcheck.setFirstBillsID(cbills.getCashierBillsID());
		billcheck.setInputid(sta.getStaffID());
		billcheck.setInputname(sta.getStaffName());
		billcheck.setStaffID(sta.getStaffID());
		billcheck.setStaffName(sta.getStaffName());
		billcheck.setJournalNum(cbills.getJournalNum());
		billcheck.setBillsType(cbills.getBillsType());
		billcheck.setProjectName(cbills.getProjectName());
		billcheck.setAudittime(new Date());
		billcheck.setAuditorid(sta.getStaffID());
		billcheck.setAuditorname(sta.getStaffName());
		billcheck.setComments(remarks);// 审核意见 b
		billcheck.setAuditorstatus("02".equals(status)?"02":"03"); // 审核状态 '01'未审核 '02'已审核 '03' 驳回
		billcheck.setDeptpost(deptpost);//职务；
		billcheck.setRemark(remarks);//备注
		billcheck.setCashierDate(new Date());// 单据日期
		baseBeanList.add(billcheck);
		parameter = "添加审核记录（" + billcheck.getJournalNum() + "）";
		CLogBook logBook = logBookService.saveCLogBook(organizationID,parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,null);
		//统计当前登录人员审核次数
		BillCheck bCheck=(BillCheck)baseBeanService.getBeanByHqlAndParams("from BillCheck " +
				"where newBillsID=?", new Object[]{cashierBills.getCashierBillsID()});
		if(bCheck!=null){
			BillCheckList=baseBeanService.getListBeanByHqlAndParams("from BillCheck where " +
					"firstBillsID=? order by audittime desc", new Object[]{bCheck.getFirstBillsID()});
			loginstaffcheck=0;
			if(BillCheckList.size()>0){
				for(int i=0;i<BillCheckList.size();i++){
					BillCheck bc=(BillCheck) BillCheckList.get(i);
					if(bc.getDeptpost()!=null){
						if((bc.getAuditorid()).equals(sta.getStaffID())){
							loginstaffcheck++;
						}
					}
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginstaff", loginstaffcheck);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**
	 * 
	 * 判断单据是否一审核
	 */
	public String AuditAcquisition(){
		String hql="from BillCheck where NewBillsID=?";
		Object[] obj={cashierBills.getCashierBillsID()};
		List<BaseBean> list=new ArrayList<BaseBean>();		
		list=baseBeanService.getListBeanByHqlAndParams(hql, obj);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list",list);
		JSONObject Json = JSONObject.fromObject(map);
		result = Json.toString();
		return "success";		
	}
	
	public CashierBills getCashierBills() {
		return cashierBills;
	}
	public void setCashierBills(CashierBills cashierBills) {
		this.cashierBills = cashierBills;
	}
	public FinancialBill getFinancialBill() {
		return financialBill;
	}

	public void setFinancialBill(FinancialBill financialBill) {
		this.financialBill = financialBill;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getDepotid() {
		return depotid;
	}
	public void setDepotid(String depotid) {
		this.depotid = depotid;
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
	public Map<String, GoodsBills> getGoodsmap() {
		return goodsmap;
	}
	public void setGoodsmap(Map<String, GoodsBills> goodsmap) {
		this.goodsmap = goodsmap;
	}

	public List<CCode> getLogisticsList() {
		return logisticsList;
	}

	public void setLogisticsList(List<CCode> logisticsList) {
		this.logisticsList = logisticsList;
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

	public List<GoodsBills> getBillsGoodList() {
		return BillsGoodList;
	}
	public void setBillsGoodList(List<GoodsBills> billsGoodList) {
		BillsGoodList = billsGoodList;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public ContactCompanyView getContactCompanyView1() {
		return contactCompanyView1;
	}

	public void setContactCompanyView1(ContactCompanyView contactCompanyView1) {
		this.contactCompanyView1 = contactCompanyView1;
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

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public int getCamount() {
		return camount;
	}

	public void setCamount(int camount) {
		this.camount = camount;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<BaseBean> getStafflist() {
		return stafflist;
	}

	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
	}

	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	public GoodsBills getfBillsGood() {
		return fBillsGood;
	}
	public void setfBillsGood(GoodsBills fBillsGood) {
		this.fBillsGood = fBillsGood;
	}
	public stockInv getStockinv() {
		return stockinv;
	}

	public void setStockinv(stockInv stockinv) {
		this.stockinv = stockinv;
	}

	public String getArrgoodsNum() {
		return arrgoodsNum;
	}

	public void setArrgoodsNum(String arrgoodsNum) {
		this.arrgoodsNum = arrgoodsNum;
	}

	public List<CCode> getTypelist() {
		return typelist;
	}

	public void setTypelist(List<CCode> typelist) {
		this.typelist = typelist;
	}
	public CashierBillsVO getCashierBillsVO() {
		return cashierBillsVO;
	}
	public void setCashierBillsVO(CashierBillsVO cashierBillsVO) {
		this.cashierBillsVO = cashierBillsVO;
	}
	public List<CCode> getBillsType() {
		return billsType;
	}
	public void setBillsType(List<CCode> billsType) {
		this.billsType = billsType;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public List<BaseBean> getBillgoodlist() {
		return billgoodlist;
	}
	public void setBillgoodlist(List<BaseBean> billgoodlist) {
		this.billgoodlist = billgoodlist;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<BaseBean> getBillCheckList() {
		return BillCheckList;
	}
	public void setBillCheckList(List<BaseBean> billCheckList) {
		BillCheckList = billCheckList;
	}
	public String getDeptpost() {
		return deptpost;
	}
	public void setDeptpost(String deptpost) {
		this.deptpost = deptpost;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getLoginstaffcheck() {
		return loginstaffcheck;
	}
	public void setLoginstaffcheck(int loginstaffcheck) {
		this.loginstaffcheck = loginstaffcheck;
	}
	public Map<String, String> getBillcheckmap() {
		return billcheckmap;
	}
	public void setBillcheckmap(Map<String, String> billcheckmap) {
		this.billcheckmap = billcheckmap;
	}
	public Map<String, GoodsBills> getGoodsmapold() {
		return goodsmapold;
	}
	public void setGoodsmapold(Map<String, GoodsBills> goodsmapold) {
		this.goodsmapold = goodsmapold;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}	
	
	
}
