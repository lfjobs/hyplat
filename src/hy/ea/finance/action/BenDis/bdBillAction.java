package hy.ea.finance.action.BenDis;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.service.GoldOrderService;
import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.finance.BenDis.DtOrderBillAdd;
import hy.ea.bo.finance.BenDis.RefundSheet;
import hy.ea.bo.finance.*;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.finance.vo.GoodsBillsVO;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.bo.office.Document;
import hy.ea.bo.office.TemplateParams;
import hy.ea.bo.office.TimingCharging;
import hy.ea.finance.service.transferService;
import hy.ea.production.service.WarehouseService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.util.JushMain;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Scope("prototype")
public class bdBillAction {
	private Logger logger = LoggerFactory.getLogger(bdBillAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private transferService trService;
	@Resource
	private ServerService serverService;
	@Resource
	private WarehouseService warehouseService;

	@Resource
	private GoldOrderService goldOrderService;
	@Resource
	private ShowExcelService excelService;
	private int pageNumber;
	private PageForm pageForm;
	private String weixinCompanyId;

	private CashierBills cashierBills;
	private List<BaseBean> goodBillslist;
	private List<Object> beans;
	private String result;
	private String  iisnull;//判断是否是点击财务的收款单
	private String id;
	private  RefundSheet rs;
	private RefundSheet refundSheet;
	private String status;
	private String type;
	private List<BaseBean> list;
	private InputStream excelStream;

	private String inforType;
	private String hylb;//对应用户 （wd:我的订单 gys:供应商订单）
	private String sdate;
	private String edate;
	private String count;
	private String nameSearch;//用户名   登录帐号  查询
	private TEshopCusCom tEshopCusCom;//获取账号
	private String user;  //用户账号
	private String ppid;
	private String zzorder;//待审核转账确认标识
	private String reportType;

	private CashierBillsVO cashierBillsVO;
	private List<BaseBean> goodList;

	private Map<String,Object> session=ActionContext.getContext().getSession();
	private CAccount account=(CAccount) ActionContext.getContext().getSession().get("account");

	private List<Object[]> map;

	private String staid;
	@SuppressWarnings("unchecked")
	private List<Object[]> getmember(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object[]> te = baseBeanService
				.getListBeanBySqlAndParams(
						"SELECT sccid,cusType,staffid FROM T_ESHOP_CUSCOM START WITH staffid=? CONNECT BY PRIOR ACCOUNT=Superioragent",
						new Object[] { account.getStaffID()});
		return te;
	}

	private BaseBean getwode(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		TEshopCusCom te = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams(
				"FROM TEshopCusCom WHERE staffid=?",
				new Object[] { account.getStaffID() });
		return te;
	}

	public String getRefundSheetById(){
		String hql=" from RefundSheet where cashierBillsID = ?";
		rs=  (RefundSheet) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{id});

		return "addrefund";
	}

	//查询会员类别树
	@SuppressWarnings("unchecked")
	public String gethylb(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		TEshopCusCom a=(TEshopCusCom)getwode();
		List<Object[]> hylb=baseBeanService.getListBeanBySqlAndParams("select p.model,p.goodsname from dt_productpackaging p where p.type =? and p.goodsname !=? order by p.model", new Object[]{"会员类型级别","会员中心",});
		List<Object[]> tesccs= getmember();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a", a);
		map.put("b", tesccs);
		map.put("c", hylb);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	//查询订单是否分配佣金比例
	public String getjbfp(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		boolean b=true;
		String hqlys = "from CashierBills where cashierBillsID = ? and status!=?";
		cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(
				hqlys, new Object[] { cashierBills.getCashierBillsID(),"99" });
		DtOrderBillAdd orderBillAdd=(DtOrderBillAdd)baseBeanService.getBeanByHqlAndParams("from DtOrderBillAdd where oaBillId = ?", new Object[] { cashierBills.getCashierBillsID()});
		Company cp=(Company)baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?", new Object[] {orderBillAdd.getOaGysId()});
		if (cashierBills != null) {
			String hqlg = "from GoodsBills where cashierBillsID = ? and canstatus is null";
			List<BaseBean> goList = baseBeanService
					.getListBeanByHqlAndParams(
							hqlg,
							new Object[] { cashierBills.getCashierBillsID() });
			goodBillslist = new ArrayList<BaseBean>();
			for (int i = 0; i < goList.size(); i++) {
				GoodsBills goodsBills = (GoodsBills) goList.get(i);

				String pc = "from ProSetup where ppid=? and (comId=? and fcomId is null)";
				List<BaseBean> pclist = baseBeanService
						.getListBeanByHqlAndParams(pc,
								new Object[] { goodsBills.getPpID(),
										cp.getCompanyID() });
				if (pclist.size()<=0) {
					pc = "from ProSetup where ppid=? and (comId=? and fcomId=?)";
					pclist = baseBeanService.getListBeanByHqlAndParams(
							pc,
							new Object[] { goodsBills.getPpID(),
									cp.getCompanyPID(),
									cp.getCompanyID() });
					if (pclist.size()<=0) {
						pc = "from ProSetup where ppid=?";
						pclist = baseBeanService.getListBeanByHqlAndParams(
								pc, new Object[] { goodsBills.getPpID()});
					}
				}
				if(pclist.size()<=0){
					b=false;
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("b", b);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/*
	 * 查询订单（我的订单、其他会员订单、批量发货）
	 */
	public String getcomporder()
	{




		long a= System.currentTimeMillis();//获取当前系统时间(毫秒)
		HttpServletRequest request = ServletActionContext.getRequest();
		String pl = request.getParameter("pl");
		String fk = request.getParameter("fk");
		request.setAttribute("pl", pl);
		request.setAttribute("fk", fk);
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount)session.get("account");
		TEshopCusCom cusCom = (TEshopCusCom)getwode();
		List<Object> params = new ArrayList();
		StringBuilder hql = new StringBuilder();


		String tableName = "Dtcashierbills";
          System.out.println("reportType"+reportType);
		System.out.println("pl"+pl);
		System.out.println("hylb"+hylb);
		System.out.println("zzorder"+zzorder);
		System.out.println("inforType"+inforType);
		if((reportType==null||reportType.equals(""))&&(pl==null||pl.equals("")||pl.equals("dd"))&&(hylb==null||hylb.equals("")||hylb.equals("gys"))&&!"zz".equals(zzorder)) {
			String year = "";
			if ("".equals(sdate) || sdate == null) {
				Date currentDate = new Date();

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
				year = dateFormat.format(currentDate);


			} else {

				year = sdate.substring(0, 4);


			}
			System.out.println("year"+year);
			TableRalate tableRelate = goldOrderService.getTableName(account.getCompanyID(), "CashierBills", year);

			if (tableRelate != null) {
				tableName = tableRelate.getTablename();
			}
			System.out.println("tableName"+tableName);
		}

		if("oldOrder".equals(reportType)){
			hql.append("select cb.companyname,cb.Journalnum,");
			hql.append("cb.cashierDate,cb.inputName,hs.staffname,");
			hql.append("p.goodsname,o.receivetel,o.receiveaddress,cb.priceSub,case when cb.fkStatus=00 then '已付款未发货' when cb.fkStatus=01 then '未付款' when cb.fkStatus=02 then '已出库正在物流' when cb.fkStatus=03 then '用户已收货' when cb.fkStatus=04 then '已分配金币' when cb.fkStatus=05 then '申请退货中' when cb.fkStatus=06 then '同意退货' when cb.fkStatus=07 then '申请退货中' else '确认收货'  end");
		}else {
			hql.append("select cb.cashierbillsid,cb.companyname,cb.billstype,cb.Journalnum,");
			hql.append("cb.fkStatus,cb.cashierDate,cb.inputName,cb.departmentName,cb.ccompanyName,hs.staffname,");
			hql.append("p.goodsname,o.receivetel,o.receiveaddress,cb.priceSub");
		}
		hql.append(" from "+tableName+" cb,dt_order_bill_add o,t_eshop_cuscom ec,dt_hr_staff hs,dt_productpackaging p");
		hql.append(" where cb.cashierbillsid = o.oa_bill_id");
		hql.append(" and o.oa_sccid=ec.sccid");
		hql.append(" and ec.staffid=hs.staffid");
		hql.append(" and p.model=ec.custype");
		hql.append(" and p.type=?");
		hql.append(" and cb.billsType = ?");
		hql.append(" and cb.statusbill = ?");
		hql.append(" and cb.status != ?");
		StringBuilder contsql = new StringBuilder("select count(cb.cashierbillskey)");
		contsql.append(" from "+tableName+" cb,dt_order_bill_add o,t_eshop_cuscom ec,dt_hr_staff hs,dt_productpackaging p");
		contsql.append(" where cb.cashierbillsid = o.oa_bill_id");
		contsql.append(" and o.oa_sccid=ec.sccid");
		contsql.append(" and ec.staffid=hs.staffid");
		contsql.append(" and p.model=ec.custype");
		contsql.append(" and p.type=?");
		contsql.append(" and cb.billsType = ?");
		contsql.append(" and cb.statusbill = ?");
		contsql.append(" and cb.status != ?");
		params.add("会员类型级别");
		params.add("项目收入预算单");
		params.add("04");
		params.add("99");
		if("oldOrder".equals(reportType)){
			nameSearch = (String) ActionContext.getContext().getSession()
					.get("nameSearch");
			inforType = (String) ActionContext.getContext().getSession()
					.get("inforType");
			sdate = (String) ActionContext.getContext().getSession()
					.get("sdate");
			edate = (String) ActionContext.getContext().getSession()
					.get("edate");
			fk = (String) ActionContext.getContext().getSession()
					.get("fk");
		}
		if ((("".equals(fk)) || (fk == null)) && (("".equals(this.nameSearch)) || (this.nameSearch == null)) && (("".equals(this.inforType)) || (this.inforType == null)))
		{
			hql.append(" and cb.cashierDate between ? and ?");
			contsql.append(" and cb.cashierDate between ? and ?");
			if (("".equals(this.sdate)) || (this.sdate == null))
			{
				Calendar calendarM = Calendar.getInstance();
				int year1 = calendarM.get(Calendar.YEAR);
				calendarM.add(2, -3);
				int year2 =calendarM.get(Calendar.YEAR);
				if(year1>year2){
					this.sdate = year1+"-01-01 00:00:00";
				}else{
					this.sdate = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendarM.getTime());
				}

				params.add(Utilities.getDateFromString(this.sdate + " 00:00:00", "yyyy-MM-dd HH:ss:mm"));
			}
			else
			{
				params.add(Utilities.getDateFromString(this.sdate + " 00:00:00", "yyyy-MM-dd HH:ss:mm"));
			}
			if (("".equals(this.edate)) || (this.edate == null))
			{
				Calendar calendarR = Calendar.getInstance();
				calendarR.add(5, 0);
				this.edate = new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(calendarR.getTime());
				params.add(Utilities.getDateFromString(this.edate + " 23:59:59", "yyyy-MM-dd HH:ss:mm"));
			}
			else
			{
				params.add(Utilities.getDateFromString(this.edate + " 23:59:59", "yyyy-MM-dd HH:ss:mm"));
			}
		}
		else if ((this.sdate != null) && (!this.sdate.equals("")) && (this.edate != null) && (!this.edate.equals("")))
		{
			hql.append(" and cb.cashierDate between ? and ?");
			contsql.append(" and cb.cashierDate between ? and ?");
			params.add(Utilities.getDateFromString(this.sdate + " 00:00:00", "yyyy-MM-dd HH:ss:mm"));
			params.add(Utilities.getDateFromString(this.edate + " 23:59:59", "yyyy-MM-dd HH:ss:mm"));
		}
		if ("pl".equals(pl))
		{
			hql.append("and cb.fkStatus = ?");
			contsql.append("and cb.fkStatus = ?");
			params.add("00");
		}
		else if ("fl".equals(pl))
		{
			hql.append("and cb.fkStatus = ?");
			contsql.append("and cb.fkStatus = ?");
			params.add("03");
		}
		else if ((fk != null) && (!fk.equals("")))
		{
			hql.append("and cb.fkStatus = ?");
			contsql.append("and cb.fkStatus = ?");
			params.add(fk);
		}
		if ((this.hylb != null) && (this.hylb.equals("wd")) && (cusCom != null))
		{
			hql.append(" and o.oa_sccid=?");
			contsql.append(" and o.oa_sccid=?");
			params.add(cusCom.getSccId());
		}
		else if ((this.hylb != null) && (this.hylb.equals("gys")))
		{
			hql.append(" and o.OA_GYS_ID = ?");
			contsql.append(" and o.OA_GYS_ID = ?");
			params.add(account.getCompanyID());
		}
		else if ((this.hylb != null) && (!this.hylb.equals("")) && (!this.hylb.equals("wd")))
		{
			Object[] sccids = this.hylb.split(",");
			hql.append(" and (");
			contsql.append(" and (");
			for (int i = 0; i < sccids.length; i++)
			{
				String sccid = sccids[i].toString();
				if (i > 0)
				{
					hql.append(" or");
					contsql.append(" or");
				}
				hql.append(" o.oa_sccid=?");
				contsql.append(" o.oa_sccid=?");
				params.add(sccid);
			}
			hql.append(")");
			contsql.append(")");
		}
		if ((this.inforType != null) && (!"".equals(this.inforType)))
		{
			this.inforType = this.inforType.trim();
			hql.append(" and cb.journalNum like ?");
			contsql.append(" and cb.journalNum like ?");
			params.add("%" + this.inforType + "%");
		}
		if ((this.nameSearch != null) && (!"".equals(this.nameSearch)))
		{
			hql.append(" and (ec.account like ? or  hs.staffname like ? )");
			contsql.append(" and (ec.account like ? or  hs.staffname like ? )");
			params.add("%" + this.nameSearch + "%");
			params.add("%" + this.nameSearch + "%");
		}
		if ((this.zzorder != null) && (this.zzorder.equals("zz")))
		{
			hql.append(" and cb.fkStatus = ?");
			contsql.append(" and cb.fkStatus = ?");
			params.add("09");
		}
		hql.append(" and cb.status!=27");
		hql.append("  order by cb.cashierDate DESC");
		contsql.append(" order by cb.cashierDate DESC");

		if("oldOrder".equals(reportType)){
			String[] titles ={"序号","公司名称","订单编号","下单时间","订单负责人","用户名称","用户会员类别","收货联系方式","收货地址","产品总金额","状态"};
			List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(hql.toString(),params.toArray());
			Object sum   =  baseBeanService.getObjectBySqlAndParams("select sum(tab.priceSub) from ("+hql.toString()+") tab",params.toArray());
			excelStream = excelService.showExcelByReport(titles,list,sum,"",reportType);
			return "showexcel";
		}
		this.pageForm = this.baseBeanService.getPageFormBySQL(
				this.pageForm != null ? this.pageForm.getPageNumber() : 1,
				this.pageNumber == 0 ? 10 : this.pageNumber, hql.toString(), contsql.toString(), params.toArray());
		ActionContext.getContext().getSession()
				.put("nameSearch", nameSearch);
		ActionContext.getContext().getSession()
				.put("inforType", inforType);
		ActionContext.getContext().getSession()
				.put("sdate", sdate);
		ActionContext.getContext().getSession()
				.put("edate", edate);
		ActionContext.getContext().getSession()
				.put("fk", fk);
		return "list";
	}
	/*
	 * 查询订单（我的订单、其他会员订单、批量发货）
	 */
	public String getcomporderNEW()
	{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount)session.get("account");
		HttpServletRequest request = ServletActionContext.getRequest();

		String fk = request.getParameter("fk");

		pageForm = baseBeanService.getPageFormByDC(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 20 : pageNumber), finddcBy(account.getCompanyID(),fk));
//		List<Object> params = new ArrayList();
//		StringBuilder sql = new StringBuilder();
//		sql.append("select cb.cashierbillsid,cb.companyname,cb.Journalnum,cb.fkStatus,cb.cashierDate,cb.CTUSERNAME,cb.PRICESUB,cb.billsType from dtCashierBills cb");
//		sql.append(" where cb.billsType = '项目收入预算单' and cb.statusbill = '04' and cb.status != '99' and cb.companyid=?");
//		params.add(account.getCompanyID());
//		sql.append(" and (cb.fkStatus = '00' or cb.fkStatus = '02' or cb.fkStatus = '03' or cb.fkStatus = '04' )");
//		sql.append(" and cb.cashierDate between ? and ?");
//			if (("".equals(this.sdate)) || (this.sdate == null))
//			{
//				Calendar calendarM = Calendar.getInstance();
//				calendarM.add(2, -1);
//				this.sdate = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendarM.getTime());
//				params.add(Utilities.getDateFromString(this.sdate + " 00:00:00", "yyyy-MM-dd HH:ss:mm"));
//			}
//			else
//			{
//				params.add(Utilities.getDateFromString(this.sdate + " 00:00:00", "yyyy-MM-dd HH:ss:mm"));
//			}
//			if (("".equals(this.edate)) || (this.edate == null))
//			{
//				Calendar calendarR = Calendar.getInstance();
//				calendarR.add(5, 0);
//				this.edate = new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(calendarR.getTime());
//				params.add(Utilities.getDateFromString(this.edate + " 23:59:59", "yyyy-MM-dd HH:ss:mm"));
//			}
//			else
//			{
//				params.add(Utilities.getDateFromString(this.edate + " 23:59:59", "yyyy-MM-dd HH:ss:mm"));
//			}
//
//		if ((this.nameSearch != null) && (!"".equals(this.nameSearch)))
//		{
//			sql.append(" and cb.CTUSERNAME like ? ");
//
//
//			params.add("%" + this.nameSearch + "%");
//		}
//		if ((this.inforType != null) && (!"".equals(this.inforType)))
//		{
//			this.inforType = this.inforType.trim();
//			sql.append(" and cb.journalNum like ?");
//			params.add("%" + this.inforType + "%");
//		}
//		HttpServletRequest request = ServletActionContext.getRequest();
//
//		String fk = request.getParameter("fk");
//
//		 if (fk != null && !fk.equals(""))
//		{
//			sql.append("and cb.fkStatus = ?");
//			params.add(fk);
//		}
//		sql.append(" order by cb.cashierDate DESC");
//
//
//		pageForm = baseBeanService.getPageFormBySQL(
//				this.pageForm != null ? this.pageForm.getPageNumber() : 1,
//				this.pageNumber == 0 ? 20 : this.pageNumber, sql.toString(), "select count(*) from (" + sql.toString() + ")", params.toArray());

		return "list2";
	}

	private DetachedCriteria finddcBy(String companyID,String fk) {
		DetachedCriteria dc = DetachedCriteria.forClass(CashierBills.class);





		Date startDate  = null;
		Date endDate  = null;

		if (("".equals(this.sdate)) || (this.sdate == null))
		{
			Calendar calendarM = Calendar.getInstance();
			// calendarM.add(2, -1);
			calendarM.set(Calendar.DAY_OF_YEAR,calendarM.get(Calendar.DAY_OF_YEAR) -2);

			this.sdate = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendarM.getTime());
			startDate= Utilities.getDateFromString(this.sdate + " 00:00:00", "yyyy-MM-dd HH:ss:mm");

		}
		else
		{
			startDate = Utilities.getDateFromString(this.sdate + " 00:00:00", "yyyy-MM-dd HH:ss:mm");
		}
		if (("".equals(this.edate)) || (this.edate == null))
		{
			Calendar calendarR = Calendar.getInstance();
			calendarR.add(5, 0);
			this.edate = new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(calendarR.getTime());
			endDate = Utilities.getDateFromString(this.edate + " 23:59:59", "yyyy-MM-dd HH:ss:mm");
		}
		else
		{
			endDate = Utilities.getDateFromString(this.edate + " 23:59:59", "yyyy-MM-dd HH:ss:mm");
		}
		dc.add(Restrictions.between("cashierDate", startDate, endDate));

//		dc.addOrder(Order.desc("cashierDate"));

		dc.add(Restrictions.eq("statusbill", "04"));
		dc.add(Restrictions.ne("status", "99"));
		dc.add(Restrictions.in("fkStatus",new Object[]{"00","02","03","04"}));
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("billsType", "项目收入预算单"));



		if (inforType != null
				&& !inforType.equals("")) {
			dc.add(Restrictions.like("journalNum",
					inforType, MatchMode.ANYWHERE));
		}

		if (nameSearch != null
				&& !nameSearch.equals("")) {
			dc.add(Restrictions.like("ctUserName",
					nameSearch, MatchMode.ANYWHERE));

		}

		if (fk != null
				&& !fk.equals("")) {
			dc.add(Restrictions.eq("fkStatus", fk));
		}

		return dc;
	}
	/**
	 * 买家确认收货
	 * @return
	 */
	public String confirmGood(){
		String hql=" from CashierBills where cashierBillsID = ? and cb.status != ?";
		cashierBills=(CashierBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{id,"99"});
		cashierBills.setFkStatus("03");
		baseBeanService.update(cashierBills);
		return "success";
	}

	public String applyMobile(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String hql=" from CashierBills where cashierBillsID = ? and cb.status != ?";
		cashierBills=(CashierBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{id,"99"});
		request.setAttribute("cashierBills", cashierBills);
		return "mobilerefund";

	}
	/**
	 * 退货申请
	 * @return
	 */
	public String refundApply(){
		SimpleDateFormat  df = new SimpleDateFormat("yyyy-MM-dd");
		List<BaseBean> list=new ArrayList<BaseBean>();

		//String groupCompanySn=session.get("groupCompanySn").toString();
		String hql=" from CashierBills where cashierBillsID = ? and cb.status != ?";
		cashierBills=(CashierBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{id,"99"});
		String hql1=" from TEshopCusCom where staffid = ?";
		tEshopCusCom= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{staid});
		status=cashierBills.getFkStatus();
		if(cashierBills.getFkStatus()!=""&&(cashierBills.getFkStatus().equals("02")||cashierBills.getFkStatus().equals("03")||cashierBills.getFkStatus().equals("00"))){
			refundSheet.setRefundCode(serverService.getBillID(cashierBills.getCompanyID()));
			refundSheet.setRsid(serverService.getServerID("rsid"));
			refundSheet.setCashierBillsID(id);
			refundSheet.setPpid(ppid);
			refundSheet.setUserAccount(tEshopCusCom.getAccount());
			refundSheet.setOrderCode(cashierBills.getjNumOrder());
			refundSheet.setOrderDate(cashierBills.getCashierDate());
			refundSheet.setCompanyID(tEshopCusCom.getCompanyId());
			refundSheet.setCompanyName(tEshopCusCom.getPseudoCompanyName());
			refundSheet.setRefundDate(new Date());
			refundSheet.setRefundstate("00");
			refundSheet.setUserName(cashierBills.getStaffName());
			//refundSheet.setVipType(cashierBills.getWfthytpe());
			cashierBills.setFkStatus("05");
			list.add(refundSheet);
		}
		//添加运单信息
		if(cashierBills.getFkStatus()!=""&&cashierBills.getFkStatus().equals("06")){
			String hql2=" from RefundSheet where cashierBillsID = ?";
			rs=  (RefundSheet) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{id});
			rs.setExpress(refundSheet.getExpress());
			rs.setWaybillno(refundSheet.getWaybillno());
			cashierBills.setFkStatus("07");
			rs.setRefundstate("03");
			list.add(rs);
			 /*
			  * 入库单据
			  */
			//单据表
			CashierBills ca=new CashierBills();
			ca.setCashierBillsID(serverService.getServerID("cashierTally"));
			ca.setJournalNum(serverService.getBillID(tEshopCusCom.getCompanyId()));
			//ca.setGroupCompanySn((session.get("groupCompanySn").toString()));
			ca.setCompanyID(tEshopCusCom.getCompanyId());
			//ca.setCompanyName(((Company)session.get("currentcompany")).getCompanyName());

			//ca.setOrganizationID(session.get("organizationID").toString());

			ca.setCashierDate(new Date());
			ca.setBillsType("物流入库单");

			ca.setStaffID(rs.getReceiverID());   		// 接收人Id
			ca.setStaffName(rs.getReceiverName());	//接收人Name
			ca.setStaffCode(""); 		//接收人编号
			ca.setInputid(tEshopCusCom.getStaffid());
			//ca.setInputName(tEshopCusCom.getStaff);
			ca.setStatus("15");
			list.add(ca);

			//获取库存名称为物流库的信息
			String depotHql="from DepotManage where depotPID=? and companyID=? and depotName=? and depotState=?";
			DepotManage depot=(DepotManage)baseBeanService.getBeanByHqlAndParams(depotHql, new Object[]{"001",tEshopCusCom.getCompanyId(),"物流库","00"});
			//进销存单据
			FinancialBill fin=new FinancialBill();
			fin.setFinancialbillID(serverService.getServerID("FinancialBill"));
			fin.setCashierBillsID(ca.getCashierBillsID());
			//fin.setGroupCompanySn(session.get("groupCompanySn").toString());
			fin.setCompanyID(tEshopCusCom.getCompanyId());
			//fin.setOrganizationID(session.get("organizationID").toString());
			fin.setDepotID(depot.getDepotID());
			fin.setDepotName(depot.getDepotName());
			fin.setStaffsID(rs.getReceiverID());			//接收人Id
			fin.setStaffsName(rs.getReceiverName());      //接收人Name
			fin.setJournalNum(serverService.getBillID(tEshopCusCom.getCompanyId()));
			fin.setBillsDate(new Date());
			fin.setBillStaffID(tEshopCusCom.getStaffid());
			//fin.setBillStaffName(account.getStaffName());
			fin.setBillsType("物流入库单");
			list.add(fin);


			 /*
			  * 入库动作
			  */

			//物流入库

			String hqlgb = "from GoodsBills where cashierBillsID = ? and ppID = ? and canstatus is null";
			GoodsBills gb = (GoodsBills) baseBeanService.getBeanByHqlAndParams(hqlgb,new Object[]{rs.getCashierBillsID(),rs.getPpid()});

			//查询每种物品的库存
			String invHql="from Inventory where companyID=? and goodsID=? and warehouse=? and productId=?";
			Inventory inv=(Inventory)baseBeanService.getBeanByHqlAndParams(invHql, new Object[]{tEshopCusCom.getCompanyId(),gb.getGoodsID(),depot.getDepotID(),gb.getPpID()});

			//库存表
			if(inv==null){
				inv=new Inventory();
				inv.setInventoryID(serverService.getBillID("inventoryID"));
				inv.setCompanyID(tEshopCusCom.getCompanyId());
				//inv.setGroupCompanySn(groupCompanySn);
				inv.setWarehouse(depot.getDepotID());
				inv.setWarehouseName(depot.getDepotName());
				inv.setGoodsID(gb.getGoodsID());
				inv.setGoodsName(gb.getGoodsName());
				inv.setGoodsType(gb.getTypeID());
				inv.setStandard(gb.getStandard());
				inv.setGoodsCoding(gb.getGoodsNum());
				inv.setUnitPrice(gb.getPrice());
				inv.setProductId(gb.getPpID());//物品单价
				inv.setInvenQuantity(gb.getQuantity());	//物品数量
				inv.setSumPrice(gb.getMoney());			//总价
				inv.setGoodstatus("00");
				list.add(inv);
			}else{
				inv.setInvenQuantity(Integer.parseInt(inv.getInvenQuantity())+Integer.parseInt(gb.getQuantity())+"");
				inv.setSumPrice(Double.parseDouble(inv.getSumPrice())+Double.parseDouble(gb.getMoney())+"");
				list.add(inv);
			}


			//库存盘点表
			stockInv sto=new stockInv();
			sto.setStockinvID(serverService.getServerID("stockInv"));
			sto.setCompanyID(tEshopCusCom.getCompanyId());
			//sto.setGroupCompanySn(groupCompanySn);
			sto.setGoodsBillsId(gb.getGoodsBillsID());
			sto.setGoodsID(gb.getGoodsID());
			sto.setGoodsType(gb.getTypeID());
			sto.setGoodsName(gb.getGoodsName());
			sto.setInvenQuantity(gb.getQuantity());			//物品数量
			sto.setSummoney(gb.getMoney());				//总价
			sto.setIntime(new Date());
			sto.setType("00");
			sto.setWarehouse(depot.getDepotID());
			sto.setWarehouseName(depot.getDepotName());
			list.add(sto);

			//物品单据
			GoodsBills newgoodsBill = null;
			try {
				newgoodsBill = (GoodsBills) gb.cloneGoodsBills();
			} catch (Exception e) {

				e.printStackTrace();
			}
			newgoodsBill.setGoodsBillsID(serverService.getServerID("goodsBillsID"));
			newgoodsBill.setGoodsBillsKey(null);
			newgoodsBill.setInventoryID(inv.getInventoryID());//库存ID
			newgoodsBill.setStockinvID(sto.getStockinvID());
			newgoodsBill.setCashierBillsID(ca.getCashierBillsID());
			newgoodsBill.setCostType("退货");
			newgoodsBill.setDepotID(depot.getDepotID());
			newgoodsBill.setDepotName(depot.getDepotName());
			newgoodsBill.setKcStatus("15");

			list.add(newgoodsBill);
		}


		list.add(cashierBills);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
		return "success";
	}


	/*
	 * 根据 公司ID 查询 数据公司的收入预算（佣金分配明细）
	 */

	public String getjbfpmx() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String hylb = request.getParameter("hylb");
		String pl = request.getParameter("pl");
		request.setAttribute("pl", pl);
		request.setAttribute("hylb", hylb);

		List<Object[]> te=getmember();
		// 位置 0订单系统ID 1:公司名称 2:单据类别 3:订单号
		// 4系统订单状态 5:订单生成时间 6店主负责人 7:产品名称 8:部门 9:单价 10:数量 11:金额 12:总金额

		StringBuilder hql = new StringBuilder("select cb.cashierbillsid, cb.companyname,");
		hql.append("cb.billstype,cb.Journalnum,cb.status,cb.cashierDate,cb.inputName,");
		hql.append("gb.goodsName,cb.departmentName, gb.price, gb.quantity,");
		hql.append("gb.money,cb.priceSub from dtCashierBills cb ,");
		hql.append("dtGoodsBills gb,dt_order_bill_add o where cb.cashierbillsid=gb.cashierbillsid");
		hql.append(" and cb.cashierbillsid = o.oa_bill_id and cb.billsType=? and cb.statusbill=? and gb.canstatus is null and cb.status!=?");

		StringBuilder contsql = new StringBuilder("select count(cb.cashierbillskey) from dtCashierBills cb ,");
		contsql.append( "dtGoodsBills gb,dt_order_bill_add o where cb.cashierbillsid=gb.cashierbillsid");
		contsql.append(" and cb.cashierbillsid = o.oa_bill_id and cb.billsType=? and cb.statusbill=? and gb.canstatus is null and cb.status!=?");


		List<Object> params=new ArrayList<Object>();
		params.add("项目收入预算单");
		params.add("04");
		params.add("99");
		if (hylb != null && hylb.equals("hy")&&te.size()>0) {
			hql.append(" and (");
			contsql.append(" and (");
			for (int i = 0; i < te.size(); i++) {
				String sccid=te.get(i)[0].toString();
				if(i>0){
					hql.append(" or");
					contsql.append(" or");
				}
				hql.append(" o.oa_sccid=?");
				contsql.append(" o.oa_sccid=?");
				params.add(sccid);
			}
			hql.append(")");
			contsql.append(")");
		}else{
			hql.append("and cb.fkStatus = ?");
			contsql.append( "and cb.fkStatus = ?");
			params.add("03");
		}
		if (inforType != null && !"".equals(inforType)) {
			hql.append(" and cb.journalNum like ?");
			contsql.append( " and cb.journalNum like ?");
			params.add("%"+inforType+"%");
		}
		if(sdate!=null&&!sdate.equals("")&&edate!=null&&!edate.equals("")){
			hql.append("and cb.cashierDate between ? and ?");
			contsql.append( "and cb.cashierDate between ? and ?");
			params.add(Utilities.getDateFromString(sdate+" 00:00:00","yyyy-MM-dd HH:ss:mm"));
			params.add(Utilities.getDateFromString(edate+" 23:59:59", "yyyy-MM-dd HH:ss:mm"));
		}
		hql.append(" order by cb.cashierDate DESC");
		contsql.append( " order by cb.cashierDate DESC");

		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				pageNumber == 0 ? 10 : pageNumber, hql.toString(), contsql.toString(), params.toArray());

		return "fpjinbi";

	}

	/*
	 * 收款单EXCEL导出
	 */
	@SuppressWarnings("unchecked")
	public String exportExcelTable() throws UnsupportedEncodingException{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> params = new ArrayList<Object>();

		StringBuilder hql =new StringBuilder("select c.Journalnum,c.jNumOrder,cc.companyname,to_char(cc.cashierDate,'yyyy-mm-dd hh24:mi:ss'),to_char(c.cashierDate,'yyyy-mm-dd hh24:mi:ss'),cc.projectname,c.pricesub," );
		hql.append("decode(cc.wfstatus4,'00','微信支付','01','支付宝支付','02','银联支付','03','线下转账','04','钱盒子支付','05','积分支付'),");
		hql.append("cc.staffname,cc.account,cc.goodsname,cc.receivename,cc.receivetel,cc.receiveaddress");
		hql.append(" from dtcashierbills c,");
		hql.append("(select c.cashierbillsid,c.journalnum,c.companyname,c.trade_no,c.wfstatus4,c.pricesub,c.cashierdate,");
		hql.append("o.receivename,t.account,c.fkstatus,s.staffname,s.staffid,p.goodsname,o.receivetel,o.receiveaddress,c.projectname");
		hql.append(" from dtcashierbills c,dt_order_bill_add o,t_eshop_cuscom t,dt_hr_staff s,dt_productpackaging p" );
		hql.append(" where c.cashierbillsid = o.oa_bill_id and t.sccid = o.oa_sccid and t.staffid = s.staffid and t.custype = p.model" );
		hql.append(" and c.status <> ? and (c.fkstatus = ? or c.fkstatus = ? or c.fkstatus = ? or c.fkstatus = ?)");
		hql.append(" and p.type = ? and c.statusbill = ? and c.billstype = ? and c.companyid=?) cc");
		hql.append(" where c.jnumorder = cc.journalnum and ((c.billstype = ? and c.projectname != ?)");
		hql.append(" or (c.billstype = ? and c.projectname = ?) or (c.billstype = ? and c.projectname = ?))");


		params.add("99");
		params.add("00");
		params.add("02");
		params.add("03");
		params.add("04");
		params.add("会员类型级别");
		params.add("04");
		params.add("项目收入预算单");
		params.add(account.getCompanyID());
		params.add("收款单");
		params.add("供应商成本");
		params.add("积分入库单");
		params.add("积分购物");
		params.add("金币入库单");
		params.add("金币购物");

		hql.append(" and c.cashierDate between ? and ?" );

		if("".equals(sdate) || sdate == null){
			Calendar calendarM = Calendar.getInstance();//此时打印它获取的是系统当前时间
			calendarM.add(Calendar.MONTH,-1);
			sdate= new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendarM.getTime());
			params.add(Utilities.getDateFromString(sdate+" 00:00:00","yyyy-MM-dd HH:ss:mm"));
		}else{
			params.add(Utilities.getDateFromString(sdate+" 00:00:00","yyyy-MM-dd HH:ss:mm"));
		}

		if("".equals(edate) || edate == null){
			Calendar calendarR  = Calendar.getInstance();//此时打印它获取的是系统当前时间
			calendarR.add(Calendar.DATE,-0);    //得到当天
			edate= new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(calendarR.getTime());
			params.add(Utilities.getDateFromString(edate+" 23:59:59", "yyyy-MM-dd HH:ss:mm"));
		}else{
			params.add(Utilities.getDateFromString(edate+" 23:59:59", "yyyy-MM-dd HH:ss:mm"));
		}

		if(hylb!=null&&hylb.equals("wd")){
			hql.append(" and c.staffid=?");
			params.add(account.getStaffID());
		}else if(hylb!=null&&hylb.equals("gys")){
			//查询供货商相关订单
			hql.append(" and c.companyid = ? and (c.proID=? or c.proID=?)");
			params.add(account.getCompanyID());
			params.add("001");
			params.add("010");
		}else if(hylb!=null&&!hylb.equals("")){
			//查询其他会员相关订单
			Object[] sccids=hylb.split(",");
			hql.append(" and (");
			for (int i = 0; i < sccids.length; i++) {
				String sccid=sccids[i].toString();
				if(i>0){
					hql.append(" or");
				}
				hql.append(" cb.staffid=?");
				params.add(sccid);
			}
			hql.append(")");
		}
		if (inforType != null && !"".equals(inforType)) {
			hql.append( "and c.jNumOrder like ?");
			params.add("%"+inforType+"%");
		}
		//用户名  或   登录帐号     模糊查询    (ec.account like '' or  hs.staffname like '%周%')
		if(nameSearch != null && !"".equals(nameSearch)){
			hql.append(" and (cc.account like ? or  cc.staffname like ? )" );
			params.add("%"+nameSearch+"%");
			params.add("%"+nameSearch+"%");
		}
		hql.append(" order by c.cashierDate desc");

		List<Object> list=baseBeanService.getListBeanBySqlAndParams(hql.toString(), params.toArray());
		String[] str={"收款单编号","订单号","商家公司名称","下单时间","收款时间","商品","总金额","支付方式","购买人","购买人帐号","会员级别","收货人","收货电话","收货地址"};
		excelStream=warehouseService.OutOrderExcel(account.getCompanyName()+"微分金收入流水", str, list);
		return "showexcel";
	}

	/*
	 * 查询支款单汇总
	 */

	public String getskd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String hylb = request.getParameter("hylb");
		String pl = request.getParameter("pl");
		request.setAttribute("pl", pl);
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		// 位置 0订单系统ID 1:公司名称 2:单据类别 3:收款单号
		// 4系统收款单状态 5:生成时间 6店主负责人  7:部门
		// 8:商家  9:买方人名 10:买方类别 11:收货联系方式 12:收货地址 13:总金额 14:付款id 15:付款人名称
		// 16:订单号

		StringBuilder hql =new StringBuilder("select cb.cashierbillsid, cb.companyname,");
		hql.append("cb.billstype,cb.Journalnum,cb.fkStatus,cb.cashierDate,cb.inputName,");
		hql.append("cb.departmentName,cb.ccompanyName, hs.staffname,p.goodsname,o.receivetel,o.receiveaddress,");
		hql.append("cb.pricesub,ca.appropriationcompanyID,ca.appropriationcompanyName,cb.jNumOrder");
		hql.append(" from dtCashierBills cb,dtcashapplybills ca,dt_order_bill_add o,");
		hql.append("t_eshop_cuscom ec,dt_hr_staff hs,dt_productpackaging p");
		hql.append(" where cb.jNumOrder = o.oa_bill_jounum");
		hql.append(" and cb.cashierbillsid = ca.cashierbillsid and ec.sccid=o.oa_sccid");
		hql.append(" and ec.staffid=hs.staffid and p.model=ec.custype");
		hql.append(" and p.type=? and cb.billsType = ? and ca.canstatus is null and cb.status!=? ");

		StringBuilder contsql =new StringBuilder("select count(cb.cashierbillskey)");
		contsql.append(" from dtCashierBills cb,dtcashapplybills ca,dt_order_bill_add o,");
		contsql.append("t_eshop_cuscom ec,dt_hr_staff hs,dt_productpackaging p");
		contsql.append(" where cb.jNumOrder = o.oa_bill_jounum");
		contsql.append(" and cb.cashierbillsid = ca.cashierbillsid and ec.sccid=o.oa_sccid");
		contsql.append(" and ec.staffid=hs.staffid and p.model=ec.custype");
		contsql.append(" and p.type=? and cb.billsType = ? and ca.canstatus is null and cb.status!=? ");

		List<Object> params=new ArrayList<Object>();
		params.add("会员类型级别");

		if(iisnull!=null&&iisnull.equals("2")){
			hql.append("and  cb.fkstatus='04'");
			contsql.append("and  cb.fkstatus='04'");
			params.add("支款单");
		}else if(iisnull!=null&&iisnull.equals("1")){
			params.add("收款单");
		}else{
			params.add("收款单");
		}

		params.add("99");

		if(("".equals(inforType) || inforType == null) && ("".equals(nameSearch) || nameSearch == null)){

			hql.append(" and cb.cashierDate between ? and ?" );
			contsql.append(" and cb.cashierDate between ? and ?" );
			if("".equals(sdate) || sdate == null){
				Calendar calendarM = Calendar.getInstance();//此时打印它获取的是系统当前时间
				calendarM.add(Calendar.MONTH,-1);
				sdate= new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendarM.getTime());
				params.add(Utilities.getDateFromString(sdate+" 00:00:00","yyyy-MM-dd HH:ss:mm"));
			}else{
				params.add(Utilities.getDateFromString(sdate+" 00:00:00","yyyy-MM-dd HH:ss:mm"));
			}

			if("".equals(edate) || edate == null){
				Calendar calendarR  = Calendar.getInstance();//此时打印它获取的是系统当前时间
				calendarR.add(Calendar.DATE,-0);    //得到当天
				edate= new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(calendarR.getTime());
				params.add(Utilities.getDateFromString(edate+" 23:59:59", "yyyy-MM-dd HH:ss:mm"));
			}else{
				params.add(Utilities.getDateFromString(edate+" 23:59:59", "yyyy-MM-dd HH:ss:mm"));
			}

		}else{
			if(sdate!=null&&!sdate.equals("")&&edate!=null&&!edate.equals("")){
				hql.append(" and cb.cashierDate between ? and ?" );
				contsql.append(" and cb.cashierDate between ? and ?" );
				params.add(Utilities.getDateFromString(sdate+" 00:00:00","yyyy-MM-dd HH:ss:mm"));
				params.add(Utilities.getDateFromString(edate+" 23:59:59", "yyyy-MM-dd HH:ss:mm"));
			}
		}

		if(hylb!=null&&hylb.equals("wd")){
			hql.append(" and cb.staffid=?");
			contsql.append(" and cb.staffid=?");
			params.add(account.getStaffID());
		}else if(hylb!=null&&hylb.equals("gys")){
			//查询供货商相关订单
			hql.append(" and cb.companyid = ? and cb.proID=?");
			contsql.append(" and cb.companyid = ? and cb.proID=?");
			params.add(account.getCompanyID());
			params.add("001");
		}else if(hylb!=null&&!hylb.equals("")){
			//查询其他会员相关订单
			Object[] sccids=hylb.split(",");
			hql.append(" and (");
			contsql.append(" and (");
			for (int i = 0; i < sccids.length; i++) {
				String sccid=sccids[i].toString();
				if(i>0){
					hql.append(" or");
					contsql.append(" or");
				}
				hql.append(" cb.staffid=?");
				contsql.append(" cb.staffid=?");
				params.add(sccid);
			}
			hql.append(")");
			contsql.append(")");
		}
		if (inforType != null && !"".equals(inforType)) {
			hql.append( "and cb.jNumOrder like ?");
			contsql.append("and cb.jNumOrder like ?");
			params.add("%"+inforType+"%");
		}


		//用户名  或   登录帐号     模糊查询    (ec.account like '' or  hs.staffname like '%周%')
		if(nameSearch != null && !"".equals(nameSearch)){
			hql.append(" and (ec.account like ? or  hs.staffname like ? )" );
			contsql.append( " and (ec.account like ? or  hs.staffname like ? )");
			params.add("%"+nameSearch+"%");
			params.add("%"+nameSearch+"%");
		}


		hql.append( " order by cb.cashierDate DESC");
		contsql.append(" order by cb.cashierDate DESC");

		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				pageNumber == 0 ? 10 : pageNumber, hql.toString(), contsql.toString(), params.toArray());
		if(iisnull!=null&&iisnull.equals("2")){
			return "getzkd";
		}else{
			return "getskd";
		}
	}

	/*
	 * 查询收款单、金币入库单汇总
	 */

	public String skd() {
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "nologin";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String hylb = request.getParameter("hylb");
		String pl = request.getParameter("pl");
		request.setAttribute("pl", pl);
		Map<String, Object> session = ActionContext.getContext().getSession();
		// 位置 0订单系统ID 1:公司名称 2:单据类别 3:收款单号
		// 4系统收款单状态 5:生成时间 6店主负责人  7:部门
		// 8:商家  9:买方人名 10:买方类别 11:收货联系方式 12:收货地址 13:总金额 14:付款id 15:付款人名称
		// 16:订单号

		String tableName = "Dtcashierbills";


		String year = "";
		if("".equals(sdate) || sdate == null) {
			Date currentDate = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		     year = dateFormat.format(currentDate);


		}else {

             year = sdate.substring(0,4);


		}

		TableRalate tableRelate = goldOrderService.getTableName(account.getCompanyID(), "CashierBills",year);

		if(tableRelate!=null){
			tableName = tableRelate.getTablename();
		}

		StringBuilder hql =new StringBuilder("select s.cashierbillsid,s.companyname,s.billstype,s.Journalnum,d.fkStatus,s.cashierDate,d.inputName," );
		hql.append("d.departmentName,d.ccompanyName,s.ctusername staffname,d.ctusername,o.receivetel,o.receiveaddress,d.pricesub, d.contactuserid, d.ctusername sn,s.jNumOrder");
		hql.append(" from "+tableName+" s, "+tableName+" d,dt_order_bill_add o");
		hql.append(" where s.jnumorder = d.jnumorder and o.oa_bill_jounum = d.jnumorder");
//		hql.append(" and m.staffid = d.contactuserid and m.custype=p.model and p.type='会员类型级别'");
		hql.append(" and d.companyid = ?" );
		hql.append(" and d.billstype = '项目收入预算单'" );
		hql.append(" and s.status <> '99'");
		hql.append(" and s.statusbill = '04'");
		hql.append(" and (d.fkstatus = '00' or d.fkstatus = '02' or d.fkstatus = '03' or  d.fkstatus = '04')");
		hql.append(" and ((s.billstype = '收款单' and d.projectname != '金币兑换' and d.projectname != '供应商成本') or (s.billstype = '积分入库单' and s.projectname = '积分购物') or  s.billstype = '金币入库单' and s.projectname = '金币购物')");


		StringBuilder contsql =new StringBuilder("select count(s.cashierbillsid)" );
		contsql.append(" from "+tableName+" s, "+tableName+" d,dt_order_bill_add o");
		contsql.append(" where s.jnumorder = d.jnumorder and o.oa_bill_jounum = d.jnumorder");
//		contsql.append(" and m.staffid = d.contactuserid and m.custype=p.model and p.type='会员类型级别'");
		contsql.append(" and d.companyid = ?" );
		contsql.append(" and d.billstype = '项目收入预算单'" );
		contsql.append(" and s.status <> '99'");
		contsql.append(" and s.statusbill = '04'");
		contsql.append(" and (d.fkstatus = '00' or d.fkstatus = '02' or d.fkstatus = '03' or  d.fkstatus = '04')");
		contsql.append(" and ((s.billstype = '收款单' and d.projectname != '金币兑换' and d.projectname != '供应商成本') or (s.billstype = '积分入库单' and s.projectname = '积分购物') or  s.billstype = '金币入库单' and s.projectname = '金币购物')");

		/*      StringBuilder hql =new StringBuilder("select c.cashierbillsid,c.companyname,c.billstype,c.Journalnum,c.fkStatus,c.cashierDate,c.inputName," );
        hql.append("c.departmentName,c.ccompanyName,cc.staffname,cc.goodsname,cc.receivetel,cc.receiveaddress,c.pricesub,cc.staffid,cc.staffname sn,c.jNumOrder");
        hql.append(" from dtcashierbills c,");
        hql.append("(select c.cashierbillsid,c.journalnum,c.companyname,c.trade_no,c.wfstatus4,c.pricesub,c.cashierdate,");
        hql.append("o.receivename,t.account,c.fkstatus,s.staffname,s.staffid,p.goodsname,o.receivetel,o.receiveaddress");
        hql.append(" from dtcashierbills c,dt_order_bill_add o,t_eshop_cuscom t,dt_hr_staff s,dt_productpackaging p" );
        hql.append(" where c.cashierbillsid = o.oa_bill_id and t.sccid = o.oa_sccid and t.staffid = s.staffid and t.custype = p.model" );
        hql.append(" and c.status <> ? and (c.fkstatus = ? or c.fkstatus = ? or c.fkstatus = ? or c.fkstatus = ?)");
        hql.append(" and p.type = ? and c.statusbill = ? and c.billstype = ? and c.companyid=?) cc");
        hql.append(" where c.jnumorder = cc.journalnum and ((c.billstype = ? and c.projectname != ?)");
        hql.append(" or (c.billstype = ? and c.projectname = ?)or (c.billstype = ? and c.projectname = ?))");

        StringBuilder contsql =new StringBuilder("select count(c.cashierbillsid) from dtcashierbills c,");
        contsql.append("(select c.cashierbillsid,c.journalnum,c.companyname,c.trade_no,c.wfstatus4,c.pricesub,c.cashierdate,");
        contsql.append("o.receivename,t.account,c.fkstatus,s.staffname,p.goodsname,o.receivetel,o.receiveaddress");
        contsql.append(" from dtcashierbills c,dt_order_bill_add o,t_eshop_cuscom t,dt_hr_staff s,dt_productpackaging p" );
        contsql.append(" where c.cashierbillsid = o.oa_bill_id and t.sccid = o.oa_sccid and t.staffid = s.staffid and t.custype = p.model" );
        contsql.append(" and c.status <> ? and (c.fkstatus = ? or c.fkstatus = ? or c.fkstatus = ? or c.fkstatus = ?)");
        contsql.append(" and p.type = ? and c.statusbill = ? and c.billstype = ? and c.companyid=?) cc");
        contsql.append(" where c.jnumorder = cc.journalnum and ((c.billstype = ? and c.projectname != ?)");
        contsql.append(" or (c.billstype = ? and c.projectname = ?)or (c.billstype = ? and c.projectname = ?))");*/

		List<Object> params=new ArrayList<Object>();

		params.add(account.getCompanyID());


		hql.append(" and s.cashierDate between ? and ?" );
		contsql.append(" and s.cashierDate between ? and ?" );
		if("".equals(sdate) || sdate == null){
			Calendar calendarM = Calendar.getInstance();//此时打印它获取的是系统当前时间
			   int year1 = calendarM.get(Calendar.YEAR);
			   calendarM.add(Calendar.MONTH,-3);
			   int year2 =calendarM.get(Calendar.YEAR);

			   if(year1>year2){

				   sdate = year1+"-01-01 00:00:00";
			   }else{
				   sdate= new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendarM.getTime());

			   }

			//calendarM.set(Calendar.DAY_OF_YEAR,calendarM.get(Calendar.DAY_OF_YEAR) -2);




			params.add(Utilities.getDateFromString(sdate+" 00:00:00","yyyy-MM-dd HH:ss:mm"));
		}else{
			params.add(Utilities.getDateFromString(sdate+" 00:00:00","yyyy-MM-dd HH:ss:mm"));
		}

		if("".equals(edate) || edate == null){
			Calendar calendarR  = Calendar.getInstance();//此时打印它获取的是系统当前时间
			calendarR.add(Calendar.DATE,-0);    //得到当天
			edate= new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(calendarR.getTime());
			params.add(Utilities.getDateFromString(edate+" 23:59:59", "yyyy-MM-dd HH:ss:mm"));
		}else{
			params.add(Utilities.getDateFromString(edate+" 23:59:59", "yyyy-MM-dd HH:ss:mm"));
		}

		if(hylb!=null&&hylb.equals("wd")){
			hql.append(" and s.staffid=?");
			contsql.append(" and s.staffid=?");
			params.add(account.getStaffID());
		}else if(hylb!=null&&hylb.equals("gys")){
			//查询供货商相关订单
			hql.append(" and s.companyid = ? and (s.proID=? or s.proID=?)");
			contsql.append(" and s.companyid = ? and (s.proID=? or s.proID=?)");
			params.add(account.getCompanyID());
			params.add("001");
			params.add("010");
		}
       /* else if(hylb!=null&&!hylb.equals("")){  //暂时注释掉，里面没有cb表
            //查询其他会员相关订单
            Object[] sccids=hylb.split(",");
            hql.append(" and (");
            contsql.append(" and (");
            for (int i = 0; i < sccids.length; i++) {
                String sccid=sccids[i].toString();
                if(i>0){
                    hql.append(" or");
                    contsql.append(" or");
                }
                hql.append(" cb.staffid=?");
                contsql.append(" cb.staffid=?");
                params.add(sccid);
            }
            hql.append(")");
            contsql.append(")");
        }*/
		if (inforType != null && !"".equals(inforType)) {
			hql.append( "and s.jNumOrder like ?");
			contsql.append("and s.jNumOrder like ?");
			params.add("%"+inforType+"%");
		}


		//用户名  或   登录帐号     模糊查询    (ec.account like '' or  hs.staffname like '%周%')
		if(nameSearch != null && !"".equals(nameSearch)){
			hql.append(" and (o.receivetel like ? or  s.ctusername like ? )" );
			contsql.append( " and (o.receivetel like ? or  s.ctusername like ? )");
			params.add("%"+nameSearch+"%");
			params.add("%"+nameSearch+"%");
		}


		hql.append( " order by s.cashierDate DESC");
		contsql.append(" order by s.cashierDate DESC");

		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				pageNumber == 0 ? 10 : pageNumber, hql.toString(), contsql.toString(), params.toArray());

		return "skd";
	}
    /*
	 * 查询支款单汇总
	 */

	public String getdxskd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String hylb = request.getParameter("hylb");
		String pl = request.getParameter("pl");
		request.setAttribute("pl", pl);
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		// 位置 0订单系统ID 1:公司名称 2:单据类别 3:收款单号
		// 4系统收款单状态 5:生成时间 6店主负责人  7:部门
		// 8:商家  9:买方人名 10:买方类别 11:收货联系方式 12:收货地址 13:总金额 14:付款id 15:付款人名称
		// 16:订单号

		StringBuilder hql =new StringBuilder("select cb.cashierbillsid, cb.companyname,");
		hql.append("cb.billstype,cb.Journalnum,cb.fkStatus,cb.cashierDate,cb.inputName,");
		hql.append("cb.departmentName,cb.ccompanyName, hs.staffname,p.goodsname,o.receivetel,o.receiveaddress,");
		hql.append("cb.pricesub,ca.appropriationcompanyID,ca.appropriationcompanyName,cb.jNumOrder");
		hql.append(" from dtCashierBills cb,dtcashapplybills ca,dt_order_bill_add o,");
		hql.append("t_eshop_cuscom ec,dt_hr_staff hs,dt_productpackaging p");
		hql.append(" where cb.jNumOrder = o.oa_bill_jounum");
		hql.append(" and cb.cashierbillsid = ca.cashierbillsid and ec.sccid=o.oa_sccid");
		hql.append(" and ec.staffid=hs.staffid and p.model=ec.custype");
		hql.append(" and p.type=? and cb.billsType = ? and ca.canstatus is null and cb.status!=? ");

		StringBuilder contsql =new StringBuilder("select count(cb.cashierbillskey)");
		contsql.append(" from dtCashierBills cb,dtcashapplybills ca,dt_order_bill_add o,");
		contsql.append("t_eshop_cuscom ec,dt_hr_staff hs,dt_productpackaging p");
		contsql.append(" where cb.jNumOrder = o.oa_bill_jounum");
		contsql.append(" and cb.cashierbillsid = ca.cashierbillsid and ec.sccid=o.oa_sccid");
		contsql.append(" and ec.staffid=hs.staffid and p.model=ec.custype");
		contsql.append(" and p.type=? and cb.billsType = ? and ca.canstatus is null and cb.status!=? ");

		List<Object> params=new ArrayList<Object>();
		params.add("会员类型级别");

		if(iisnull!=null&&iisnull.equals("2")){
			hql.append("and  cb.fkstatus='04'");
			contsql.append("and  cb.fkstatus='04'");
			params.add("支款单");
		}else if(iisnull!=null&&iisnull.equals("1")){
			params.add("收款单");
		}else{
			params.add("收款单");
		}

		params.add("99");

		if(("".equals(inforType) || inforType == null) && ("".equals(nameSearch) || nameSearch == null)){

			hql.append(" and cb.cashierDate between ? and ?" );
			contsql.append(" and cb.cashierDate between ? and ?" );
			if("".equals(sdate) || sdate == null){
				Calendar calendarM = Calendar.getInstance();//此时打印它获取的是系统当前时间
				calendarM.add(Calendar.MONTH,-1);
				sdate= new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendarM.getTime());
				params.add(Utilities.getDateFromString(sdate+" 00:00:00","yyyy-MM-dd HH:ss:mm"));
			}else{
				params.add(Utilities.getDateFromString(sdate+" 00:00:00","yyyy-MM-dd HH:ss:mm"));
			}

			if("".equals(edate) || edate == null){
				Calendar calendarR  = Calendar.getInstance();//此时打印它获取的是系统当前时间
				calendarR.add(Calendar.DATE,-0);    //得到当天
				edate= new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(calendarR.getTime());
				params.add(Utilities.getDateFromString(edate+" 23:59:59", "yyyy-MM-dd HH:ss:mm"));
			}else{
				params.add(Utilities.getDateFromString(edate+" 23:59:59", "yyyy-MM-dd HH:ss:mm"));
			}

		}else{
			if(sdate!=null&&!sdate.equals("")&&edate!=null&&!edate.equals("")){
				hql.append(" and cb.cashierDate between ? and ?" );
				contsql.append(" and cb.cashierDate between ? and ?" );
				params.add(Utilities.getDateFromString(sdate+" 00:00:00","yyyy-MM-dd HH:ss:mm"));
				params.add(Utilities.getDateFromString(edate+" 23:59:59", "yyyy-MM-dd HH:ss:mm"));
			}
		}

		if(hylb!=null&&hylb.equals("wd")){
			hql.append(" and cb.staffid=?");
			contsql.append(" and cb.staffid=?");
			params.add(account.getStaffID());
		}else if(hylb!=null&&hylb.equals("gys")){
			//查询供货商相关订单
			hql.append(" and cb.companyid = ? and cb.proID=?");
			contsql.append(" and cb.companyid = ? and cb.proID=?");
			params.add(account.getCompanyID());
			params.add("001");
		}else if(hylb!=null&&!hylb.equals("")){
			//查询其他会员相关订单
			Object[] sccids=hylb.split(",");
			hql.append(" and (");
			contsql.append(" and (");
			for (int i = 0; i < sccids.length; i++) {
				String sccid=sccids[i].toString();
				if(i>0){
					hql.append(" or");
					contsql.append(" or");
				}
				hql.append(" cb.staffid=?");
				contsql.append(" cb.staffid=?");
				params.add(sccid);
			}
			hql.append(")");
			contsql.append(")");
		}
		if (inforType != null && !"".equals(inforType)) {
			hql.append( "and cb.jNumOrder like ?");
			contsql.append("and cb.jNumOrder like ?");
			params.add("%"+inforType+"%");
		}


		//用户名  或   登录帐号     模糊查询    (ec.account like '' or  hs.staffname like '%周%')
		if(nameSearch != null && !"".equals(nameSearch)){
			hql.append(" and (ec.account like ? or  hs.staffname like ? )" );
			contsql.append( " and (ec.account like ? or  hs.staffname like ? )");
			params.add("%"+nameSearch+"%");
			params.add("%"+nameSearch+"%");
		}


		hql.append( " order by cb.cashierDate DESC");
		contsql.append(" order by cb.cashierDate DESC");

		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				pageNumber == 0 ? 10 : pageNumber, hql.toString(), contsql.toString(), params.toArray());
		if(iisnull!=null&&iisnull.equals("2")){
			return "getzkd";
		}else{
			return "getdxskd";
		}
	}


    /*
	 * 查询收款单、金币入库单汇总
	 */

	public String dxskd() {
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "nologin";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String hylb = request.getParameter("hylb");
		String pl = request.getParameter("pl");
		request.setAttribute("pl", pl);
		Map<String, Object> session = ActionContext.getContext().getSession();
		// 位置 0订单系统ID 1:公司名称 2:单据类别 3:收款单号
		// 4系统收款单状态 5:生成时间 6店主负责人  7:部门
		// 8:商家  9:买方人名 10:买方类别 11:收货联系方式 12:收货地址 13:总金额 14:付款id 15:付款人名称
		// 16:订单号

		StringBuilder hql =new StringBuilder("select c.cashierbillsid,c.companyname,c.billstype,c.Journalnum,c.fkStatus,c.cashierDate,c.inputName," );
		hql.append("c.departmentName,c.ccompanyName,cc.staffname,cc.goodsname,cc.receivetel,cc.receiveaddress,c.pricesub,cc.staffid,cc.staffname sn,c.jNumOrder");
		hql.append(" from dtcashierbills c,");
		hql.append("(select c.cashierbillsid,c.journalnum,c.companyname,c.trade_no,c.wfstatus4,c.pricesub,c.cashierdate,");
		hql.append("o.receivename,t.account,c.fkstatus,s.staffname,s.staffid,p.goodsname,o.receivetel,o.receiveaddress");
		hql.append(" from dtcashierbills c,dt_order_bill_add o,t_eshop_cuscom t,dt_hr_staff s,dt_productpackaging p" );
		hql.append(" where c.cashierbillsid = o.oa_bill_id and t.sccid = o.oa_sccid and t.staffid = s.staffid and t.custype = p.model" );
		hql.append(" and c.status <> ? and (c.fkstatus = ? or c.fkstatus = ? or c.fkstatus = ? or c.fkstatus = ?)");
		hql.append(" and p.type = ? and c.statusbill = ? and c.billstype = ? and c.companyid=?) cc");
		hql.append(" where c.jnumorder = cc.journalnum and ((c.billstype = ? and c.projectname != ?)");
		hql.append(" or (c.billstype = ? and c.projectname = ?)or (c.billstype = ? and c.projectname = ?))");

		StringBuilder contsql =new StringBuilder("select count(c.cashierbillsid) from dtcashierbills c,");
		contsql.append("(select c.cashierbillsid,c.journalnum,c.companyname,c.trade_no,c.wfstatus4,c.pricesub,c.cashierdate,");
		contsql.append("o.receivename,t.account,c.fkstatus,s.staffname,p.goodsname,o.receivetel,o.receiveaddress");
		contsql.append(" from dtcashierbills c,dt_order_bill_add o,t_eshop_cuscom t,dt_hr_staff s,dt_productpackaging p" );
		contsql.append(" where c.cashierbillsid = o.oa_bill_id and t.sccid = o.oa_sccid and t.staffid = s.staffid and t.custype = p.model" );
		contsql.append(" and c.status <> ? and (c.fkstatus = ? or c.fkstatus = ? or c.fkstatus = ? or c.fkstatus = ?)");
		contsql.append(" and p.type = ? and c.statusbill = ? and c.billstype = ? and c.companyid=?) cc");
		contsql.append(" where c.jnumorder = cc.journalnum and ((c.billstype = ? and c.projectname != ?)");
		contsql.append(" or (c.billstype = ? and c.projectname = ?)or (c.billstype = ? and c.projectname = ?))");

		List<Object> params=new ArrayList<Object>();
		params.add("99");
		params.add("00");
		params.add("02");
		params.add("03");
		params.add("04");
		params.add("会员类型级别");
		params.add("04");
		params.add("项目收入预算单");
		params.add(account.getCompanyID());
		params.add("收款单");
		params.add("供应商成本");
		params.add("积分入库单");
		params.add("积分购物");
		params.add("金币入库单");
		params.add("金币购物");


		hql.append(" and c.cashierDate between ? and ?" );
		contsql.append(" and c.cashierDate between ? and ?" );
		if("".equals(sdate) || sdate == null){
			Calendar calendarM = Calendar.getInstance();//此时打印它获取的是系统当前时间
			calendarM.add(Calendar.MONTH,-1);
			sdate= new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(calendarM.getTime());
			params.add(Utilities.getDateFromString(sdate+" 00:00:00","yyyy-MM-dd HH:ss:mm"));
		}else{
			params.add(Utilities.getDateFromString(sdate+" 00:00:00","yyyy-MM-dd HH:ss:mm"));
		}

		if("".equals(edate) || edate == null){
			Calendar calendarR  = Calendar.getInstance();//此时打印它获取的是系统当前时间
			calendarR.add(Calendar.DATE,-0);    //得到当天
			edate= new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(calendarR.getTime());
			params.add(Utilities.getDateFromString(edate+" 23:59:59", "yyyy-MM-dd HH:ss:mm"));
		}else{
			params.add(Utilities.getDateFromString(edate+" 23:59:59", "yyyy-MM-dd HH:ss:mm"));
		}

		if(hylb!=null&&hylb.equals("wd")){
			hql.append(" and c.staffid=?");
			contsql.append(" and c.staffid=?");
			params.add(account.getStaffID());
		}else if(hylb!=null&&hylb.equals("gys")){
			//查询供货商相关订单
			hql.append(" and c.companyid = ? and (c.proID=? or c.proID=?)");
			contsql.append(" and c.companyid = ? and (c.proID=? or c.proID=?)");
			params.add(account.getCompanyID());
			params.add("001");
			params.add("010");
		}else if(hylb!=null&&!hylb.equals("")){
			//查询其他会员相关订单
			Object[] sccids=hylb.split(",");
			hql.append(" and (");
			contsql.append(" and (");
			for (int i = 0; i < sccids.length; i++) {
				String sccid=sccids[i].toString();
				if(i>0){
					hql.append(" or");
					contsql.append(" or");
				}
				hql.append(" cb.staffid=?");
				contsql.append(" cb.staffid=?");
				params.add(sccid);
			}
			hql.append(")");
			contsql.append(")");
		}
		if (inforType != null && !"".equals(inforType)) {
			hql.append( "and c.jNumOrder like ?");
			contsql.append("and c.jNumOrder like ?");
			params.add("%"+inforType+"%");
		}


		//用户名  或   登录帐号     模糊查询    (ec.account like '' or  hs.staffname like '%周%')
		if(nameSearch != null && !"".equals(nameSearch)){
			hql.append(" and (cc.account like ? or  cc.staffname like ? )" );
			contsql.append( " and (cc.account like ? or  cc.staffname like ? )");
			params.add("%"+nameSearch+"%");
			params.add("%"+nameSearch+"%");
		}


		hql.append( " order by c.cashierDate DESC");
		contsql.append(" order by c.cashierDate DESC");

		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				pageNumber == 0 ? 10 : pageNumber, hql.toString(), contsql.toString(), params.toArray());

		return "dxskd";
	}

	/**
	 * 保存学员信息
	 * @return
	 */
	public String addNovice(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String noviceName = request.getParameter("noviceName");
		String novicePhone = request.getParameter("novicePhone");
		String noviceCode = request.getParameter("noviceCode");
		String noviceAddress = request.getParameter("noviceAddress");

		String jo = request.getParameter("jo");

		if(jo!=null&&!jo.equals("")){
			CashierBills cashierBills=(CashierBills)baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum=? and status!=?", new Object[]{jo,"99"});
			cashierBills.setCtUserName(noviceName);
			cashierBills.setReference(novicePhone);
			cashierBills.setStaffIdentityCard(noviceCode);
			cashierBills.setReferrerAddress(noviceAddress);
			baseBeanService.update(cashierBills);
		}
		return "success";
	}

	//修改学员信息(ajax)
	public String updateNovice(){

		HttpServletRequest request = ServletActionContext.getRequest();
		JSONObject ret =new JSONObject();

		String noviceName = request.getParameter("noviceName");
		String novicePhone = request.getParameter("novicePhone");
		String noviceCode = request.getParameter("noviceCode");
		String noviceAddress = request.getParameter("noviceAddress");
		String jo = request.getParameter("jo");

		CashierBills cashierBills=(CashierBills)baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum=? and status!=?", new Object[]{jo,"99"});
		cashierBills.setCtUserName(noviceName);
		cashierBills.setReference(novicePhone);
		cashierBills.setStaffIdentityCard(noviceCode);
		cashierBills.setReferrerAddress(noviceAddress);
		baseBeanService.update(cashierBills);
		ret.accumulate("type", 1);


		try {
			TemplateParams templateParams = (TemplateParams) baseBeanService.getBeanByHqlAndParams("from TemplateParams where cashierBillsID = ?", new Object[]{cashierBills.getCashierBillsID()});
			if (templateParams != null) {
				templateParams.setReference(cashierBills.getReference());
				templateParams.setStaffIdentityCard(cashierBills.getStaffIdentityCard());
				templateParams.setStaffName(cashierBills.getCtUserName());
				templateParams.setReferrerAddress(cashierBills.getReferrerAddress());
				baseBeanService.update(templateParams);
				Document t  = (Document)baseBeanService.getBeanByHqlAndParams("from Document where docId = ?",new Object[]{templateParams.getDocID()});
				t.setTitle(cashierBills.getCtUserName()+"学员协议");
				t.setApplyNo("");
				baseBeanService.update(t);
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		result=ret.toString();
		return "success";

	}

	//查询学员信息
	public String getNovice(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String jo = request.getParameter("jo");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if(jo!=null&&!jo.equals("")){
			CashierBills cashierBills=(CashierBills)baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum=? and status!=?", new Object[]{jo,"99"});
			map.put("UserName", cashierBills.getCtUserName());
			map.put("Reference", cashierBills.getReference());
			map.put("ReferenceCode", cashierBills.getStaffIdentityCard());
			map.put("ReferrerAddress", cashierBills.getReferrerAddress());
		}
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	};

	//查看学员信息
	public String getInformation(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String jo = request.getParameter("jo");
		Map<String, Object> map = new HashMap<String, Object>();
		CashierBills cashierBills=(CashierBills)baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum=? and status!=?", new Object[]{jo,"99"});
		map.put("UserName", cashierBills.getCtUserName());
		map.put("Reference", cashierBills.getReference());
		map.put("ReferenceCode", cashierBills.getStaffIdentityCard());
		map.put("ReferrerAddress", cashierBills.getReferrerAddress());
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}




	/**
	 * 订单单据查看
	 *
	 * @return
	 */
	public String toEditCostSheet() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpServletRequest request=ServletActionContext.getRequest();
		String cashid="";
		// 项目预算单
		String hqlys = "from CashierBills where cashierBillsID = ? and status<>?";
		cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(
				hqlys, new Object[] { cashierBills.getCashierBillsID(),"99"});
		cashid=cashierBills.getCashierBillsID();
		if(cashierBills.getBillsType().equals("积分入库单"))
		{
			CashierBills cb=(CashierBills)baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum = ? and status<> ? ", new Object[] { cashierBills.getjNumOrder(),"99"});
			cashid=cb.getCashierBillsID();
		}

		DtOrderBillAdd orderBillAdd= (DtOrderBillAdd) baseBeanService.getBeanByHqlAndParams(
				"from DtOrderBillAdd where oaBillJounum = ?", new Object[] { cashierBills.getjNumOrder() });
		TEshopCusCom cusCom=(TEshopCusCom)baseBeanService.getBeanByHqlAndParams(
				"from TEshopCusCom where sccId = ?", new Object[]{orderBillAdd.getOaSccId()});
		request.setAttribute("wfjzh", cusCom);
		request.setAttribute("sh", orderBillAdd);
		//收货方信息
		Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{cusCom.getStaffid()});
		request.setAttribute("shr", staff);
		//发货方信息
		if (cashierBills != null) {
			// 项目预算单中的物品
			String hqlg = "from GoodsBills where cashierBillsID = ? and canstatus is null";
			if(type.equals("invoice")){
				hqlg = "from GoodsBills g,ProductPackaging p where p.goodsID = g.goodsID and g.cashierBillsID = ? and g.canstatus is null";
			}
			goodBillslist = baseBeanService.getListBeanByHqlAndParams(hqlg,
					new Object[] {cashid});

		}
		if(type.equals("mobile")){
			return "look";
		}else if(type.equals("invoice")){
			return "invoice";
		}else {
			return "edit";
		}
	}


	// 原单据打印方法
	public String toprintCashier() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String hql1 = " from GoodsBillsVO where cashierBillsID=? ";
		String cassid=request.getParameter("cassid");
		cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where cashierBillsID=?",new Object[]{cassid});
		DtOrderBillAdd dto = (DtOrderBillAdd) baseBeanService.getBeanByHqlAndParams("from DtOrderBillAdd where oaBillId=?", new Object[]{cassid});
		String radioType = request.getParameter("radioType");
		if(cashierBills.getjNumOrder()!=null&&!cashierBills.getjNumOrder().equals("")){
			CashierBills cashierBills1 = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum=?",new Object[]{cashierBills.getjNumOrder()});
			cashierBillsVO=new CashierBillsVO();
			cashierBillsVO.setjNumOrder(cashierBills.getjNumOrder());
			cashierBillsVO.setJournalNum(cashierBills.getJournalNum());
			cashierBillsVO.setCompanyname(cashierBills.getCompanyName());
			cashierBillsVO.setStatusbill(cashierBills.getStatusbill());
			cashierBillsVO.setCashierDate(cashierBills.getCashierDate());
			cashierBillsVO.setContactUserName(cashierBills1.getCtUserName());
			goodList = baseBeanService.getListBeanByHqlAndParams(hql1, new Object[]{cashierBills1.getCashierBillsID()});

			//获取凭证号和订单号一致的公司名字
			StringBuilder sql = new StringBuilder();
			sql.append(" select cb.companyname,dc.responsibletel ");
			sql.append(" from dtcashierbills cb,DT_ccom_com cc,dtContactCompany dc ");
			sql.append(" where cb.companyid=cc.compnay_id and cc.ccompany_id = dc.ccompanyid ");
			sql.append(" and cb.journalnum=cb.JNUMORDER ");
			sql.append(" and cb.JNUMORDER=? ");

			List<Object> obj= baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{cashierBills1.getjNumOrder()});
			for(int i = 0; i<obj.size(); i++){
				Object[] para = (Object[]) obj.get(i);
				request.setAttribute("companyname", para[0]);
				request.setAttribute("responsibletel", para[1]);
			}

			//判断是否是驾校
			String hql = " from GoodsBills where cashierBillsID=? ";
			List<BaseBean> gs = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cashierBills1.getCashierBillsID()});
			String phql = " from ProductPackaging where ppID=? ";
			List<BaseBean> p=null;
			if (gs!=null&&gs.size() > 0) {
				p=new ArrayList<BaseBean>();
				for (int i=0;i<gs.size();i++) {
					GoodsBills g=(GoodsBills)gs.get(i);
					ProductPackaging pp = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(phql, new Object[]{g.getPpID()});
					p.add(pp);
				}
			}
			if (p!=null&&p.size() > 0) {
				for (int i=0;i<p.size();i++) {
					ProductPackaging pp =(ProductPackaging)p.get(i);
					if ("z30001汽车驾校".equals(pp.getTradeCode())) {
						request.setAttribute("tradeCode", pp.getTradeCode());
						break;
					}
				}
				for (int i=0;i<p.size();i++) {
					ProductPackaging pp =(ProductPackaging)p.get(i);
					request.setAttribute("tradeCode", pp.getTradeCode());
					if ("z30001汽车驾校".equals(pp.getTradeCode())) {
						request.setAttribute("contactUserName", cashierBills1.getCtUserName());
						request.setAttribute("tel", cashierBills1.getReference());
						request.setAttribute("staffIdentityCard", cashierBills1.getStaffIdentityCard());
						request.setAttribute("ReferrerAddress", cashierBills1.getReferrerAddress());
					}
					if (pp.getType() != null && (pp.getType().equals("包天计时") || pp.getType().equals("包月计时")
							|| pp.getType().equals("包年计时"))) {
						TimingCharging tcg = (TimingCharging) baseBeanService.getBeanByHqlAndParams
								("from TimingCharging where journalnum = ?", new Object[]{cashierBills.getjNumOrder()});
						String carNum = "";
						if (tcg != null) {
							carNum = tcg.getCarNumber();
						}
						request.setAttribute("carNum", carNum);
					}
				}
			}
		}
		if (radioType != null && radioType.equals("xp")
				|| radioType != null && radioType.equals("Rxp")) {
			if (goodList.size() > 0) {
				BigDecimal b = new BigDecimal(0);
				for (int i = 0; i < goodList.size(); i++) {
					GoodsBillsVO g = (GoodsBillsVO) goodList.get(i);
					BigDecimal big = new BigDecimal(g.getRealMoney());
					b.add(big);
				}
				request.setAttribute("b", b);
			}
			if (radioType.equals("xp")) {
				return "xp";
			} else {
				return "Rxp";
			}
		} else {
			return "printcashier4CashAccept";
		}
	}

	/**
	 *支款单查看 WK
	 */
	public String toeditzkd(){
		// 项目预算单
		String hqlys = "from CashierBills where cashierBillsID = ? and status!=?";
		cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(
				hqlys, new Object[] { cashierBills.getCashierBillsID(),"99" });
		//发货方信息
		if (cashierBills != null) {
			// 项目预算单中的物品
			String hqlg = "from GoodsBills where cashierBillsID = ?  canstatus is null";
			goodBillslist = baseBeanService.getListBeanByHqlAndParams(hqlg,
					new Object[] { cashierBills.getCashierBillsID() });

		}
		return "zkdck";
	}

	public String getRefund(){

		return "refund";

	}

	public String addRefund(){

		return "addrefund";

	}

	/**
	 * 单据查看(金币分配明细查看)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toEditjinbimx() {
		HttpServletRequest request=ServletActionContext.getRequest();

		// 项目预算单
		String hqlys = "from CashierBills where cashierBillsID = ? and status!=?";
		cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(
				hqlys, new Object[] { cashierBills.getCashierBillsID(),"99"});
		String cashid = cashierBills.getCashierBillsID();
		DtOrderBillAdd orderBillAdd = (DtOrderBillAdd) baseBeanService.getBeanByHqlAndParams(
				"from DtOrderBillAdd where oaBillId = ?", new Object[] { cashid });
		request.setAttribute("ord", orderBillAdd);

		TEshopCusCom te=(TEshopCusCom)baseBeanService.getBeanByHqlAndParams(
				"from TEshopCusCom where sccId = ?", new Object[] { orderBillAdd.getOaSccId() });
		request.setAttribute("te", te);
		//收货方信息
		Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{te.getStaffid()});
		request.setAttribute("shr", staff);
		ProductPackaging packaging = new ProductPackaging();
		try {
			packaging = (ProductPackaging) baseBeanService
					.getBeanByHqlAndParams("from ProductPackaging p where p.type =?  and p.model=?",
							new Object[] {"会员类型级别",orderBillAdd.getYjid()});
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (cashierBills != null)
		{
			map = baseBeanService
					.getListBeanBySqlAndParams(
							"select m.m_type, s.staffname sss, m.jb_bl, m.jb_num,m.m_zh from dt_member_backup m,dt_hr_staff s where m.staff_id=s.staffid and m.cash_id = ? and m.status is null order by m.m_type",
							new Object[] { cashid });
			// 项目预算单中的物品
			String hqlg = "from GoodsBills where cashierBillsID = ? and canstatus is null";
			List<BaseBean> goList = baseBeanService.getListBeanByHqlAndParams(
					hqlg, new Object[] { cashid });
			goodBillslist = new ArrayList<BaseBean>();
			for (int i = 0; i < goList.size(); i++)
			{
				GoodsBills goodsBills = (GoodsBills) goList.get(i);
				String ppid = goodsBills.getPpID();
				ProSetup pst = (ProSetup) baseBeanService.getBeanByHqlAndParams
						("from ProSetup where ppid = ?", new Object[]{ppid});
				if(pst != null)
				{
					goodsBills.setProSetup(pst);
					if(pst.getProxyprice() != null)
					{
						List<BaseBean> backList = baseBeanService.getListBeanBySqlAndParams
								("select * from dt_dailimember d where d.cashid = ? and d.ppid = ?", new Object[]{cashid,ppid});
						if(backList != null)
						{
							goodsBills.setBackList(backList);
						}
					}

				}
				List<BaseBean> pBeans = baseBeanService
						.getListBeanByHqlAndParams(
								"from ProductPackaging p where type=? and model!=? and model!=? order by model",
								new Object[] { "会员类型级别", "会员中心", "8" });

				goodsBills.setpBeans(pBeans);
				goodsBills.setObjects(map);
				goodBillslist.add(goodsBills);
			}
		}
		return "jbedit";
	}

	/**
	 * 一键发货
	 * 2019-4-15 like查询耗费资源大 弃用 后续存储过程以调整
	 * @return
	 */
	/*@Deprecated
	public String toShip() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		try {
		    //like查询耗费资源大弃用
			//state=trService.onkeyfh(account.getCompanyID(),account.getStaffID(),null);
			if(state==null||(state!=null&&"01".equals(state.get(0)))){
				return getcomporder();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}*/

	/*
	 * 单个发货
	 */
	public String toShipOne() {
		long a= System.currentTimeMillis();//获取当前系统时间(毫秒)
		Map<String, Object> session = ActionContext.getContext().getSession();
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		String cashiId=re.getParameter("cashiId");
		String fhStaffId =  account.getStaffID();
		List<String> state=trService.onkeyfh(account.getCompanyID(),fhStaffId,cashiId);

		DtOrderBillAdd billAdd = (DtOrderBillAdd) baseBeanService
				.getBeanByHqlAndParams("from DtOrderBillAdd where oaBillId=?", new Object[]{cashiId});
		trService.updateCashState(billAdd.getOaComId(),"02",billAdd.getFkDate(),cashiId);//更改新标状态


		if(state!=null&&"00".equals(state.get(0))){
			//获取收货人账号
			StringBuilder sb = new StringBuilder();
			sb.append("select m.account,s.projectname ");
			sb.append("from dtCashierBills s, DT_ORDER_BILL_ADD d, T_Eshop_CusCom m ");
			sb.append("where s.cashierbillsid = ? and s.status != ? ");
			sb.append("and s.jnumorder = d.oa_bill_jounum and d.oa_sccid = m.sccid");
			Object obj = baseBeanService.getObjectBySqlAndParams(sb.toString(), new Object[] { cashiId,"99"});
			Object[] objs = (Object[])obj;
			logger.error("单个发货-----账号:"+objs[0]);
			List<String> slist = new ArrayList<String>();
			slist.add((String)objs[0]);
			//极光推送
			JushMain.sendjiguangMessage("您购买的"+objs[1]+"已发货", "发货", "buyerindent", "deliverGoods", slist);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", state);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/*
	 * 一键发货时，库存不足的转入生产
	 */
	public String transferred(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		String journalNum=re.getParameter("journalNum");
		String goodsBillsID=re.getParameter("goodsBillsID");
		re.setAttribute("pl",re.getParameter("pl"));
		List<String> sqls=new ArrayList<String>();
		List<Object[]> objs=new ArrayList<Object[]>();
		CashierBills c=(CashierBills)baseBeanService.getBeanByHqlAndParams(
				"from CashierBills where journalNum=? and companyID=? and status !=?", new Object[]{journalNum,account.getCompanyID(),"99"});
		GoodsBills g=(GoodsBills)baseBeanService.getBeanByHqlAndParams(
				"from GoodsBills where goodsBillsID=? and canstatus is null",new Object[]{goodsBillsID});
		String str="";
		List<BaseBean> ppList=baseBeanService.getListBeanByHqlAndParams(
				"from ProductPackaging where parentId=? and companyID=?", new Object[]{g.getPpID(),account.getCompanyID()});
		if(ppList.size()==0)
			str="00";
		else
			str="01";
		sqls.add("update dtcashierbills set status=? where cashierBillsID=? and companyID=? and status !=?");
		objs.add(new Object[]{"27",c.getCashierBillsID(),account.getCompanyID(),"99"});
		sqls.add("update dtgoodsBills set status=?,category=? where goodsBillsID=? and cashierBillsID=? canstatus is null");
		objs.add(new Object[]{"27",str,goodsBillsID,c.getCashierBillsID()});
		baseBeanService.executeSqlsByParmsList(null, sqls.toArray(new String[sqls.size()]), objs);
		return "success";
	}

	/**
	 * 佣金分配
	 * @return
	 */
	public String toDistribution() {
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String str=trService.Distribution(cashierBills.getCashierBillsID(),account.getStaffID());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("str", str);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 用户确认收货
	 * @return
	 */
	public synchronized String userConfirmationReceipt(){
		trService.recognitionHarvest(cashierBills.getCashierBillsID());
		return "success";
	}


	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getWeixinCompanyId() {
		return weixinCompanyId;
	}

	public void setWeixinCompanyId(String weixinCompanyId) {
		this.weixinCompanyId = weixinCompanyId;
	}

	public String getIisnull() {
		return iisnull;
	}

	public void setIisnull(String iisnull) {
		this.iisnull = iisnull;
	}

	public String getInforType() {
		return inforType;
	}

	public void setInforType(String inforType) {
		this.inforType = inforType;
	}

	public CashierBills getCashierBills() {
		return cashierBills;
	}

	public void setCashierBills(CashierBills cashierBills) {
		this.cashierBills = cashierBills;
	}

	public List<BaseBean> getGoodBillslist() {
		return goodBillslist;
	}

	public List<Object> getBeans() {
		return beans;
	}

	public void setBeans(List<Object> beans) {
		this.beans = beans;
	}

	public void setGoodBillslist(List<BaseBean> goodBillslist) {
		this.goodBillslist = goodBillslist;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RefundSheet getRs() {
		return rs;
	}

	public void setRs(RefundSheet rs) {
		this.rs = rs;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getHylb() {
		return hylb;
	}

	public void setHylb(String hylb) {
		this.hylb = hylb;
	}


	public RefundSheet getRefundSheet() {
		return refundSheet;
	}

	public void setRefundSheet(RefundSheet refundSheet) {
		this.refundSheet = refundSheet;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<BaseBean> getList() {
		return list;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
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


	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public String getZzorder() {
		return zzorder;
	}

	public void setZzorder(String zzorder) {
		this.zzorder = zzorder;
	}

	public String getStaid() {
		return staid;
	}

	public void setStaid(String staid) {
		this.staid = staid;
	}
	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public List<Object[]> getMap() {
		return map;
	}

	public void setMap(List<Object[]> map) {
		this.map = map;
	}

	public String getNameSearch() {
		return nameSearch;
	}

	public void setNameSearch(String nameSearch) {
		this.nameSearch = nameSearch;
	}

	public CashierBillsVO getCashierBillsVO() {
		return cashierBillsVO;
	}

	public void setCashierBillsVO(CashierBillsVO cashierBillsVO) {
		this.cashierBillsVO = cashierBillsVO;
	}

	public List<BaseBean> getGoodList() {
		return goodList;
	}

	public void setGoodList(List<BaseBean> goodList) {
		this.goodList = goodList;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
}
