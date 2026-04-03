package hy.ea.production.action.finishproduct;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.finance.BillCheck;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.bo.production.view.UtboundOrderVo;
import hy.ea.service.CCodeService;
import hy.ea.util.StringUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

/**
 * 产品库存管理
 * @author zj
 *
 */
public class FinishedProductAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CCodeService codeService;//基本数据，往来单位，往来个人
	private PageForm pageForm;
	private int pageNumber;				//每页显示的条数
	private String result;					//ajax返回字段
	private UtboundOrderVo utboundOrderVo;
	private String type;						//判断单据是从什么类型过来的
	private List<GoodsBills> goodsList;
	private String category;  //产品类型  00：单产品  01：组装产品
	private String fiveClear;  //组织机构
	private String fiveClearName;

	/**
	 * 获取主页面信息
	 */
	public String getHomePageData(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> obj=new ArrayList<Object>();
		String title="select c.cashierbillsid,c.journalnum,' - ',f.drdepotid,f.drdepotname,' -- ',c.status," +
				"c.Contactuserid,c.ctUserName,c.staffid,c.staffname,c.cashierdate";
		String sql=" from dtcashierbills c left join dt_inv_fb f on c.cashierbillsid = f.cashierbillsid " +
				"where c.billstype =? and c.companyid=? ";
		obj.add("成品移库单");obj.add(account.getCompanyID());
		if(utboundOrderVo!=null){
			if(!"".equals(utboundOrderVo.getJournalnum())&&utboundOrderVo.getJournalnum()!=null){
				sql+=" and c.journalnum=?";
				obj.add(utboundOrderVo.getJournalnum());
			}
			if(!"".equals(utboundOrderVo.getCtUserName())&&utboundOrderVo.getCtUserName()!=null){
				sql+=" and c.ctUserName=?";
				obj.add(utboundOrderVo.getCtUserName());
			}
		}
		if(fiveClear!=null&&!"".equals(fiveClear)){
			 sql+=" and c.fiveClear=?";
			 obj.add(fiveClear);
		}
		if(category!=null&&!"".equals(category)){
			 sql+=" and f.category=?";
			 obj.add(category);
		}
		sql+=" order by c.cashierdate desc";
		pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber <1 ? 10 : pageNumber), title+sql, "select count(*) "+sql, obj.toArray());
		return "pageData";
	}
	/**
	 * 获取查看审核页面信息
	 */
	public String getReviewAuditPageData(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		String sql="select f.subjectName,c.journalnum,c.cashierdate,f.logisticsType,c.ctUserName" +
				",f.depotName,f.drdepotName,c.reference,c.cashierbillsid,c.staffname,f.purchaseDate,c.staffCode" +
				" from dtcashierbills c left join dt_inv_fb f on c.cashierbillsid = f.cashierbillsid where " +
				" c.companyid=? and c.cashierbillsid=?";
		Object[] o=(Object[]) baseBeanService.getObjectBySqlAndParams(sql, new Object[]{account.getCompanyID(),utboundOrderVo.getCashierbillsid()});
		String hql="from GoodsBills where cashierBillsID=? and companyID=?";
		List<BaseBean> goods=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{utboundOrderVo.getCashierbillsid(),account.getCompanyID()});

		//审核信息
				BillCheck bCheck=(BillCheck)baseBeanService.getBeanByHqlAndParams("from BillCheck where newBillsID=?",
						new Object[]{utboundOrderVo.getCashierbillsid()});
				if(bCheck!=null){
					List<BaseBean> BillCheckList=baseBeanService.getListBeanByHqlAndParams("from BillCheck where " +
							"firstBillsID=? order by audittime desc", new Object[]{bCheck.getFirstBillsID()});
					String str="";
					Map<String,String> billcheckmap=new HashMap<String, String>();
					if(BillCheckList.size()>0){
						for(int i=0;i<BillCheckList.size();i++){
							BillCheck bc=(BillCheck) BillCheckList.get(i);
							if(bc.getDeptpost()!=null){
								billcheckmap.put(bc.getDeptpost(), bc.getAuditorname());
								billcheckmap.put(bc.getDeptpost()+"zt",bc.getAuditorstatus());
								str+=bc.getAuditorname()+" : "+(bc.getComments()==null?" ":bc.getComments())+" ; ";
							}
						}
					}
					re.setAttribute("str",str);
					re.setAttribute("billcheckmap", billcheckmap);
				}else{
					re.setAttribute("str",null);
					re.setAttribute("billcheckmap", null);
				}
		re.setAttribute("comName",account.getCompanyName());
		re.setAttribute("list",goods);
		re.setAttribute("ut",o);
		return "ReviewAudit";
	}

	/**
	 * 出库单审核
	 */
	public String StorageSingleAudit(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		String status=re.getParameter("status");
		String audit=re.getParameter("audit");
		String deptpost=re.getParameter("deptpost");
		List<BaseBean> list=new ArrayList<BaseBean>();
		BillCheck bc=new BillCheck();
		bc.setCheckid(serverService.getServerID("BillCheck"));
		bc.setAuditorcompanyid(account.getCompanyID());
		bc.setAuditorcompanyname(account.getCompanyName());
		bc.setFirstBillsID(utboundOrderVo.getCashierbillsid());
		bc.setInputid(account.getStaffID());
		bc.setNewBillsID(utboundOrderVo.getCashierbillsid());
		bc.setInputname(account.getStaffName());
		bc.setAudittime(new Date());
		bc.setAuditorid(account.getStaffID());
		bc.setAuditorname(account.getStaffName());
		bc.setComments(audit);
		bc.setDeptpost(deptpost);
		bc.setAuditorstatus("yes".equals(status)?"02":"03");
		list.add(bc);
		if(!"yes".equals(status)){
			CashierBills ca=(CashierBills)baseBeanService.getBeanByHqlAndParams(" from CashierBills where cashierBillsID=? and companyID=?",
								new Object[]{utboundOrderVo.getCashierbillsid(),account.getCompanyID()});
			ca.setStatus("10");
			list.add(ca);
		}

		baseBeanService.saveBeansListAndexecuteHqlsByParams(list,null,null);
		return "success";
	}
	/**
	 * 获取是否审核
	 */
	public String ajaxCheckWhetherAudit(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String BillHql="from BillCheck where newBillsID=? and auditorcompanyid=?";
		List<BaseBean> billList=baseBeanService.getListBeanByHqlAndParams(BillHql, new Object[]{utboundOrderVo.getCashierbillsid(),account.getCompanyID()});
		String cashiHql="from CashierBills where cashierbillsid=? and companyid=?";
		CashierBills ut=(CashierBills)baseBeanService.getBeanByHqlAndParams(cashiHql, new Object[]{utboundOrderVo.getCashierbillsid(),account.getCompanyID()});
		Map<String,Object> map=new HashMap<String, Object>();
		if(billList.size()==0||"10".equals(ut.getStatus())){
			map.put("sta","0");
		}else{
			map.put("sta","1");
		}
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return Action.SUCCESS;
	}
	/**
	 * 确认出库
	 */
	public String ConfirmationOfLibrary(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<BaseBean> list=new ArrayList<BaseBean>();
		String caHql="from CashierBills where companyID=? and cashierBillsID=?";
		CashierBills ca=(CashierBills)baseBeanService.getBeanByHqlAndParams(caHql, new Object[]{account.getCompanyID(),utboundOrderVo.getCashierbillsid()});
		ca.setStatus("16");
		list.add(ca);
		String finHql="from FinancialBill where companyID=? and cashierBillsID=?";
		FinancialBill fin=(FinancialBill)baseBeanService.getBeanByHqlAndParams(finHql, new Object[]{account.getCompanyID(),utboundOrderVo.getCashierbillsid()});
		fin.setBillStatus("19");
		list.add(fin);
		String depotHql="from DepotManage where companyID=? and depotPID=? and depotName=? and depotState=?";
		DepotManage depot=(DepotManage)baseBeanService.getBeanByHqlAndParams(depotHql, new Object[]{account.getCompanyID(),"001","成品库","00"});
		String goodsHql="from GoodsBills where companyID=? and cashierBillsID=?";
		List<BaseBean> goodsList=baseBeanService.getListBeanByHqlAndParams(goodsHql, new Object[]{account.getCompanyID(),utboundOrderVo.getCashierbillsid()});
		String[] numHql=new String[goodsList.size()];
		List<Object[]> obj=new ArrayList<Object[]>();
		String[] ppHql=new String[goodsList.size()];
		List<Object[]> ppObj=new ArrayList<Object[]>();
		for(int i=0;i<goodsList.size();i++){
			GoodsBills goods=(GoodsBills)goodsList.get(i);
			String invHql="from Inventory where companyID=? and warehouse=? and goodsID=? and productId=?";
			Inventory inv=(Inventory)baseBeanService.getBeanByHqlAndParams(invHql, new Object[]{account.getCompanyID(),depot.getDepotID(),goods.getGoodsID(),goods.getPpID()});
			stockInv sto1=new stockInv();
			sto1.setStockinvID(serverService.getServerID("stockInv"));
			sto1.setCompanyID(account.getCompanyID());
			sto1.setGoodsBillsId(goods.getGoodsBillsID());
			sto1.setGoodsID(goods.getGoodsID());
			sto1.setGoodsName(goods.getGoodsName());
			sto1.setGoodsType(goods.getTypeID());
			sto1.setIntime(new Date());
			sto1.setStaffID(ca.getUnionpayQueryID());
			sto1.setStaffName(ca.getUserAccountNum());
			sto1.setWarehouse(depot.getDepotID());
			sto1.setWarehouseName(depot.getDepotName());
			sto1.setType("01");
			sto1.setInvenQuantity(goods.getQuantity());
			sto1.setSummoney(goods.getMoney());
			list.add(sto1);
			inv.setInvenQuantity(Double.parseDouble(inv.getInvenQuantity())-Double.parseDouble(goods.getQuantity())+"");
			inv.setSumPrice(StringUtil.formatFloatNumber(Double.parseDouble(inv.getSumPrice())-Double.parseDouble(goods.getMoney())));
			list.add(inv);
			String inHql="from Inventory where companyID=? and warehouse=? and goodsID=? and productId=?";
			Inventory in=(Inventory)baseBeanService.getBeanByHqlAndParams(inHql, new Object[]{account.getCompanyID(),goods.getDepotID(),goods.getGoodsID(),goods.getPpID()});
			if(in==null){
				in=new Inventory();
				in.setInventoryID(serverService.getServerID("Inventory"));
				in.setCompanyID(account.getCompanyID());
				in.setWarehouse(goods.getDepotID());
				in.setWarehouseName(goods.getDepotName());
				in.setGoodsID(goods.getGoodsID());
				in.setGoodsType(goods.getTypeID());
				in.setGoodsName(goods.getGoodsName());
				in.setStandard(goods.getStandard());
				in.setGoodsCoding(goods.getGoodsNum());
				in.setUnitPrice("销售库".equals(goods.getDepotName())?goods.getPretium():goods.getPrice());
				in.setInvenQuantity(goods.getQuantity());
				in.setGoodstatus("00");
				in.setSumPrice(StringUtil.formatFloatNumber(
						Double.parseDouble("销售库".equals(goods.getDepotName())?goods.getPretium():goods.getPrice())*Double.parseDouble(goods.getQuantity())));
				in.setSubjectsID(fin.getSubjectID());
				in.setSubjectsName(fin.getSubjectName());
				in.setProductId(goods.getPpID());
				in.setCategory(fin.getCategory());
				list.add(in);
			}else{
				in.setInvenQuantity(Double.parseDouble(in.getInvenQuantity())+Double.parseDouble(goods.getQuantity())+"");
				in.setSumPrice(StringUtil.formatFloatNumber(Double.parseDouble(in.getSumPrice())+
							(Double.parseDouble(goods.getQuantity())*Double.parseDouble("销售库".equals(goods.getDepotName())?goods.getPretium():goods.getPrice()))));
				list.add(in);
			}
			stockInv sto2=new stockInv();
			sto2.setCompanyID(account.getCompanyID());
			sto2.setGoodsBillsId(goods.getGoodsBillsID());
			sto2.setGoodsID(goods.getGoodsID());
			sto2.setGoodsName(goods.getGoodsName());
			sto2.setGoodsType(goods.getTypeID());
			sto2.setIntime(new Date());
			sto2.setInvenQuantity(goods.getQuantity());
			sto2.setStaffID(ca.getUnionpayQueryID());
			sto2.setStaffName(ca.getUserAccountNum());
			sto2.setStockinvID(serverService.getServerID("stockInv"));
			sto2.setSummoney(StringUtil.formatFloatNumber(
					Double.parseDouble("销售库".equals(goods.getDepotName())?goods.getPretium():goods.getPrice())*Double.parseDouble(goods.getQuantity())));
			sto2.setType("00");
			sto2.setWarehouse(goods.getDepotID());
			sto2.setWarehouseName(goods.getDepotName());
			list.add(sto2);
			numHql[i]="update dtGoodsNum set cashierBillsID=?,goodsBillsID=? where goodsID=? and cashierBillsID=? and productId=? and status=? and rownum <=?";
			obj.add(new Object[]{in.getInventoryID(),goods.getGoodsBillsID(),goods.getGoodsID(),inv.getInventoryID(),inv.getProductId(),"03",goods.getQuantity()});
			ppHql[i]="update ProductPackaging set productstate=? where companyID=? and ppID=?";
			ppObj.add(new Object[]{"05",account.getCompanyID(),goods.getPpID()});
		}
		baseBeanService.executeSqlsByParmsList(list, numHql, obj);
		baseBeanService.executeHqlsByParamsList(null, ppHql, ppObj);
		return "success";
	}
	/**
	 * 获取添加页面信息
	 */
	public String getAddPageData(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		SimpleDateFormat  df = new SimpleDateFormat("yyyy-MM-dd");
		utboundOrderVo=new UtboundOrderVo();
		utboundOrderVo.setCashierDate(df.format(new Date()));
		utboundOrderVo.setJournalnum(serverService.getBillID(account.getCompanyID()));
		String hql="from DepotManage where depotPID=? and companyID=? and depotName=? and depotState=?";
		List<BaseBean> depotList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{"001",account.getCompanyID(),"成品库","00"});
		String staffHql="from Staff where groupCompanySn=? and staffID=?";
		Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams(staffHql, new Object[]{session.get("groupCompanySn").toString(),account.getStaffID()});
		//物流方式
		List<CCode> logisticsList = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110106hfjes5ucxp0000000021");
		re.setAttribute("logisticsList", logisticsList);
		re.setAttribute("staff",staff);
		re.setAttribute("orgId", session.get("organizationID").toString());
		re.setAttribute("depot", (DepotManage)depotList.get(0));
		re.setAttribute("ut",utboundOrderVo);
		re.setAttribute("companyName",account.getCompanyName());
		return "addPageData";
	}

	/**
	 * 获取修改页面信息
	 */
	public String getEditPageData(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		String cashiHql="from CashierBills where cashierBillsID=?";
		CashierBills cashierBills=(CashierBills)baseBeanService.getBeanByHqlAndParams(cashiHql, new Object[]{utboundOrderVo.getCashierbillsid()});
		String finHql="from FinancialBill where cashierBillsID=?";
		FinancialBill financialBill=(FinancialBill)baseBeanService.getBeanByHqlAndParams(finHql, new Object[]{utboundOrderVo.getCashierbillsid()});
		String hql="from GoodsBills where cashierbillsID=?";
		List<BaseBean> goods=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{utboundOrderVo.getCashierbillsid()});
		//物流方式
		List<CCode> logisticsList = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110106hfjes5ucxp0000000021");
		re.setAttribute("logisticsList", logisticsList);re.setAttribute("orgId", session.get("organizationID").toString());
		re.setAttribute("ca", cashierBills);re.setAttribute("goods",goods);re.setAttribute("fin", financialBill);

		return "editPageData";
	}
	/**
	 * 获取打印页面信息
	 */
	public String getToPrintPageData(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpServletRequest re=ServletActionContext.getRequest();
		String sql="select c.cashierbillsid,com.companyname,c.journalNum,f.depotName,f.drdepotName,c.ctUserName" +
				 	  ",f.purchaseDate,f.subjectName,c.reference,f.logisticsType,c.staffName,c.cashierDate,staffCode" +
				 	  " from dtcashierbills c left join dt_inv_fb f on c.cashierbillsid=f.cashierbillsid" +
					  " left join dtCompany com on c.companyid=com.companyid where c.cashierbillsid=?";
		Object[] obj=(Object[]) baseBeanService.getObjectBySqlAndParams(sql, new Object[]{utboundOrderVo.getCashierbillsid()});
		String hql="from GoodsBills where cashierbillsID=?";
		List<BaseBean> goods=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{utboundOrderVo.getCashierbillsid()});
		re.setAttribute("obj", obj);re.setAttribute("goods",goods);
		re.setAttribute("orgName", session.get("organizationName").toString());
		return "toPrint";
	}
	/**
	 * 获取成品库中物品信息
	 */
	public String ajaxGetGoodsInformation(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		String likeGoodsName=re.getParameter("likeGoodsName");
		String likeCategory=re.getParameter("likeCategory");
		String depotHql="from DepotManage where depotPID=? and companyID=? and depotName=? and depotState=?";
		List<BaseBean> depotList=baseBeanService.getListBeanByHqlAndParams(depotHql, new Object[]{"001",account.getCompanyID(),"成品库","00"});
		List<Object> obj=new ArrayList<Object>();
		String invHql="from Inventory where companyId=? and goodstatus=? and fiveClear=? and category=? and warehouse=? and invenQuantity>? and status=? ";
		obj.add(account.getCompanyID());obj.add("00");obj.add(fiveClear);obj.add(category);
		obj.add(((DepotManage)depotList.get(0)).getDepotID());obj.add("0");obj.add("03");
		if(!"".equals(likeGoodsName)&&likeGoodsName!=null){
			invHql+=" and goodsName like ?";
			obj.add("%"+likeGoodsName+"%");
		}
		if(!"".equals(likeCategory)&&likeCategory!=null){
			invHql+=" and goodsType like ?";
			obj.add("%"+likeCategory+"%");
		}
		List<BaseBean> invList=baseBeanService.getListBeanByHqlAndParams(invHql, obj.toArray());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list",invList);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}
	/**
	 * 修改出库单信息
	 */
	public String editOutboundOrder(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		List<BaseBean> list=new ArrayList<BaseBean>();
		String[] delGoodsBills=re.getParameter("delGoodsBills").split(",");
		CashierBills ca=(CashierBills)baseBeanService.getBeanByHqlAndParams("from CashierBills where cashierBillsID=?",
							new Object[]{utboundOrderVo.getCashierbillsid()});
		FinancialBill fin=(FinancialBill)baseBeanService.getBeanByHqlAndParams("from FinancialBill where cashierBillsID=?",
							new Object[]{utboundOrderVo.getCashierbillsid()});
		ca.setContactUserID(utboundOrderVo.getContactUserID());
		ca.setCtUserName(utboundOrderVo.getCtUserName());
		ca.setReference(utboundOrderVo.getReference());
		fin.setPurchaseDate(utboundOrderVo.getPurchaseDate());
		fin.setSubjectID(utboundOrderVo.getSubjectID());
		fin.setSubjectName(utboundOrderVo.getSubjectName());
		fin.setLogisticsType(utboundOrderVo.getLogisticsType());
		fin.setDrdepotID(utboundOrderVo.getDrdepotID());
		fin.setDrdepotName(utboundOrderVo.getDrdepotName());
		fin.setStaffID(utboundOrderVo.getContactUserID());
		fin.setStaffName(utboundOrderVo.getCtUserName());
		list.add(ca);list.add(fin);

		String str="";
		List<Object> strObj=new ArrayList<Object>();
		if(delGoodsBills.length>0){
			for(int i=0;i<delGoodsBills.length;i++){
				if(i==0){
					str+="delete GoodsBills where goodsBillsID=?";
				}else{
					str+=" or goodsBillsID=?";
				}
				strObj.add(delGoodsBills[i]);
			}
		}

		//物品单据这边目前只做了销售库的功能
		for(int i=0;i<goodsList.size();i++){
			GoodsBills goods=goodsList.get(i);
			if(goods.getGoodsBillsID()!=null&&!"".equals(goods.getGoodsBillsID())){
				GoodsBills g=(GoodsBills)baseBeanService.getBeanByHqlAndParams("from GoodsBills where goodsBillsID=?",
								new Object[]{goods.getGoodsBillsID()});
				g.setProfitMargin(goods.getProfitMargin());
				g.setProfitAmount(goods.getProfitAmount());
				g.setPretium(goods.getPretium());
				list.add(g);
			}else{
				goods.setGoodsBillsID(serverService.getServerID("GoodsBills"));
				goods.setCompanyID(account.getCompanyID());
				goods.setCashierBillsID(ca.getCashierBillsID());
				goods.setMoney(StringUtil.formatFloatNumber(Double.parseDouble(goods.getPrice())*Double.parseDouble(goods.getQuantity())));
				goods.setSubjectsID(utboundOrderVo.getSubjectID());
				goods.setSubjectsName(utboundOrderVo.getSubjectName());
				goods.setDepotID(utboundOrderVo.getDrdepotID());
				goods.setDepotName(utboundOrderVo.getDrdepotName());
				goods.setLogistics(utboundOrderVo.getLogisticsType());
				list.add(goods);
			}
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(list,new String[]{str}, strObj.toArray());
		return "success";
	}
	/**
	 * 添加出库单信息
	 */
	public String addOutboundOrder(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String cashierbillsId=serverService.getServerID("CashierBills");
		List<BaseBean> list=new ArrayList<BaseBean>();

		CashierBills ca=new CashierBills();
		ca.setJournalNum(utboundOrderVo.getJournalnum());
		ca.setCashierBillsID(cashierbillsId);
		ca.setCompanyID(account.getCompanyID());
		ca.setCompanyName(account.getCompanyName());
		ca.setCashierDate(new Date());
		ca.setBillsType("成品移库单");
		ca.setJournalNum(utboundOrderVo.getJournalnum());
		ca.setStaffID(utboundOrderVo.getStaffID());
		ca.setStaffName(utboundOrderVo.getStaffName().split(" - ")[0]);
		ca.setStaffCode(utboundOrderVo.getStaffName().split(" - ")[1]);
		ca.setStatus("22");
		ca.setContactUserID(utboundOrderVo.getContactUserID());
		ca.setCtUserName(utboundOrderVo.getCtUserName());
		ca.setReference(utboundOrderVo.getReference());
		ca.setFiveClear(fiveClear);
		list.add(ca);

		FinancialBill fin=new FinancialBill();
		fin.setFinancialbillID(serverService.getServerID("FinancialBill"));
		fin.setCashierBillsID(cashierbillsId);
		fin.setCompanyID(account.getCompanyID());
		fin.setPurchaseDate(utboundOrderVo.getPurchaseDate());
		fin.setSubjectID(utboundOrderVo.getSubjectID());
		fin.setSubjectName(utboundOrderVo.getSubjectName());
		fin.setLogisticsType(utboundOrderVo.getLogisticsType());
		fin.setDepotID(utboundOrderVo.getDepotid());
		fin.setDepotName(utboundOrderVo.getDepotname());
		fin.setDrdepotID(utboundOrderVo.getDrdepotID());
		fin.setDrdepotName(utboundOrderVo.getDrdepotName());
		fin.setStaffID(utboundOrderVo.getContactUserID());
		fin.setStaffName(utboundOrderVo.getCtUserName());
		fin.setBillsType("成品移库单");
		fin.setBillsDate(new Date());
		fin.setBillStaffID(utboundOrderVo.getStaffID());
		fin.setBillStaffName(utboundOrderVo.getStaffName());
		fin.setBillStatus("22");
		fin.setCategory(category);
		list.add(fin);
		String[] str=new String[goodsList.size()];
		List<Object[]> obj=new ArrayList<Object[]>();
		for(int i=0;i<goodsList.size();i++){
			GoodsBills gb=goodsList.get(i);
			gb.setGoodsBillsID(serverService.getServerID("GoodsBills"));
			gb.setCompanyID(account.getCompanyID());
			gb.setCashierBillsID(cashierbillsId);
			gb.setMoney(StringUtil.formatFloatNumber(Double.parseDouble(gb.getPrice())*Double.parseDouble(gb.getQuantity())));
			gb.setSubjectsID(utboundOrderVo.getSubjectID());
			gb.setSubjectsName(utboundOrderVo.getSubjectName());
			gb.setDepotID(utboundOrderVo.getDrdepotID());
			gb.setDepotName(utboundOrderVo.getDrdepotName());
			gb.setLogistics(utboundOrderVo.getLogisticsType());
			gb.setCategory(category);
			list.add(gb);
			str[i]="update Inventory set status=? where companyID=? and inventoryID=?";
			obj.add(new Object[]{"09",account.getCompanyID(),gb.getInventoryID()});
		}
		baseBeanService.executeHqlsByParamsList(list, str, obj);
		return "success";
	}
	/*
	 * ajax获取该物品是否已设置过利润率
	 */
	public String ajaxObtainGoodsHaveBeenSetUpToProfit(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		ProSetup ps=(ProSetup)baseBeanService.getBeanByHqlAndParams(
				"from ProSetup where ppid=? and comId=?",new Object[]{utboundOrderVo.getPpId(),account.getCompanyID()});
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("ps",ps);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}

	/**
	 * 根据companyID和depotID查询其子节点
	 */
	public String getListDepotmanageByPID() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		List<Object> params=new ArrayList<Object>();
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}

		// 调拨出库里面根据选择的公司来选择对应仓库
		HttpServletRequest re=ServletActionContext.getRequest();
		String companyID=re.getParameter("comIdOne");
		if(companyID==null||"".equals(companyID)){
			companyID=account.getCompanyID();
		}
		String depotID=re.getParameter("depotID");
		String hql=" from DepotManage where  depotPID = ? and companyID = ? and depotState='00' ";
		params.add(depotID);params.add(companyID);

		hql+=" order by depotNum";
		List<BaseBean>  depotManagelist= baseBeanService.getListBeanByHqlAndParams(hql, params.toArray());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("depotManagelist", depotManagelist);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}




	/*----------------------------------------------------------------------库存管理-------------------------------------------------------------------*/

	public String getInventoryManagement(){

		return "invList";
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public UtboundOrderVo getUtboundOrderVo() {
		return utboundOrderVo;
	}
	public void setUtboundOrderVo(UtboundOrderVo utboundOrderVo) {
		this.utboundOrderVo = utboundOrderVo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<GoodsBills> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<GoodsBills> goodsList) {
		this.goodsList = goodsList;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFiveClear() {
		return fiveClear;
	}
	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}
	public String getFiveClearName() {
		return fiveClearName;
	}
	public void setFiveClearName(String fiveClearName) {
		this.fiveClearName = fiveClearName;
	}

}
