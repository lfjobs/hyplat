//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package hy.ea.finance.action.NewPhoneOrders;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.bo.UnPayRecord;
import com.tiantai.wfj.util.SessionWrap;
import com.wechat.bo.sft.SubOrders;
import com.wechat.utils.HTTPV3;
import com.wechatpay.bo.WxPayDto;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.BenDis.*;
import hy.ea.bo.finance.*;
import hy.ea.bo.human.Staff;
import hy.ea.finance.service.SetSubsidizeService;
import hy.ea.finance.service.transferService;
import hy.ea.util.MobileMessage;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Scope("prototype")
public class PhoneOrdersAction_Buyers {
	private static final Logger logger = LoggerFactory.getLogger(PhoneOrdersAction_Buyers.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private transferService trService;
	@Resource
	private ServerService serverService;
	@Resource
	private transferService trans;
	@Resource
	private SetSubsidizeService setSubsidizeService;
	private Logger logger = LoggerFactory.getLogger(PhoneOrdersAction_Buyers.class);
	private PageForm pageForm;
	private MobileMessage msg;
	private String result;
	private String staid;
	private String pl;
	private String cbid;
	private String flag;
	private String state;
	private String user;
	private String search;
	private String refund;
	private RefundSheet refundSheet;
	private String sccId;
	private String etype;
	private String exCode;
	private String waybillno;
	private String journalNum;

	public PhoneOrdersAction_Buyers() {
	}

	public String getPhoneOrdersList() {
		return "list";
	}

	/**
	 *
	 * 获取剩余金额
	 * @return
	 */
	public String getUnPayMoney(){

		UnPayRecord unPayRecord = (UnPayRecord)baseBeanService.getBeanByHqlAndParams("from UnPayRecord e where journalNum = ? and status = ?",new Object[]{journalNum,"00"});

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("unPayRecord",unPayRecord);
		JSONObject jsonObject = JSONObject.fromObject(map);
		this.result = jsonObject.toString();

		return "success";
	}
	/**
	 *
	 * 定时完结分账
	 * @return
	 */
	public void finishOrder(){


		try {
			List<BaseBean> list = new ArrayList<>();
			if (journalNum != null && !journalNum.equals("")) {
				list = baseBeanService.getListBeanByHqlAndParams("from HdBackupBill where journalNum = ?", new Object[]{journalNum});
			} else {
				list = baseBeanService.getListBeanByHqlAndParams("from HdBackupBill where status = ? order by createDate", new Object[]{"0"});
			}


			List<String> mainlist = new ArrayList<String>();
			List<String> monthlist = new ArrayList<String>();
			List<String> jourlist = new ArrayList<String>();
			List<Object[]> parmsList = new ArrayList<Object[]>();

			List<String> hqls = new ArrayList<String>();


			String hqldelete = "delete from HdBackupBill where journalNum = ?";
			for (int i = 0; i < list.size(); i++) {


					List<BaseBean> beans = new ArrayList<>();

					HdBackupBill backupBill = (HdBackupBill) list.get(i);
				try {
					WxPayDto wxPayDto = trService.getProfitInfo(backupBill.getJournalNum(), backupBill.getCompanyID());
					String order_id = HTTPV3.finishOrder(wxPayDto);
					int money = 0;
					try {
						JSONObject jo = HTTPV3.profitsharingResult(wxPayDto);

						money = jo.getInt("finish_amount");

					} catch (Exception e) {

						logger.error("操作异常", e);
					}

					if (!"".equals(order_id)) {
						logger.info("完结分账：order_id：: {}", order_id);


						String hqlsf = "from PayCashierBill c where c.payJournalNum = (select p.payJournalNum from PayCashierBill p where p.oriJournalNum = ? ) and c.dtype is not null";
						PayCashierBill payCashierBill = (PayCashierBill) baseBeanService.getBeanByHqlAndParams(hqlsf, new Object[]{backupBill.getJournalNum()});
						if (payCashierBill != null) {
							WxPayDto wxPayDtoyj = trService.getProfitInfo(payCashierBill.getOriJournalNum(), "company201009046vxdyzy4wg0000000065");//company201009046vxdyzy4wg0000000045固定作为佣金的公司暂时用天太投资管理公司后期改成中联园公司
							String order_idyj = HTTPV3.finishOrder(wxPayDtoyj);//佣金单子

							if (!"".equals(order_idyj)) {


								int money2 = 0;
								try {
									JSONObject jo2 = HTTPV3.profitsharingResult(wxPayDtoyj);

									money2 = jo2.getInt("finish_amount");

								} catch (Exception e) {

									logger.error("操作异常", e);
								}

								logger.info("完结佣金分账：order_id：: {}", order_idyj);

								//备份完结记录
								HdBackupBillHistory hdBackupBillHistory = new HdBackupBillHistory();
								BeanUtils.copyProperties(backupBill, hdBackupBillHistory);
								hdBackupBillHistory.setFinishDate(new Date());
								hdBackupBillHistory.setMoney(money + "");
								beans.add(hdBackupBillHistory);
								hqls.add(hqldelete);
								//删除原记录
								parmsList.add(new Object[]{backupBill.getJournalNum()});
								logger.info("1111111111111");

								trService.addWxAccountDetail(hdBackupBillHistory, beans, mainlist, monthlist, jourlist);

								beans.clear();

								//佣金代收记录


								//备份完结记录
								HdBackupBillHistory hdBackupBillHistory2 = new HdBackupBillHistory();
								BeanUtils.copyProperties(backupBill, hdBackupBillHistory2);
								hdBackupBillHistory2.setFinishDate(new Date());
								hdBackupBillHistory2.setMoney(money + "");
								hdBackupBillHistory2.setJournalNum(payCashierBill.getOriJournalNum());
								hdBackupBillHistory2.setStatus("3");
								String hqle = "from SubOrders where out_trade_no = ?";
								SubOrders obj = (SubOrders) baseBeanService.getBeanByHqlAndParams(hqle, new Object[]{payCashierBill.getOriJournalNum()});
								if (obj != null && "1627898266".equals(obj.getSub_mchid())) {
									hdBackupBillHistory2.setCompanyID("company201009046vxdyzy4wg0000000045");//company201009046vxdyzy4wg0000000045
								} else {
									hdBackupBillHistory2.setCompanyID("company201009046vxdyzy4wg0000000065");//company201009046vxdyzy4wg0000000045
								}
								beans.add(hdBackupBillHistory2);
								trService.addWxAccountDetail(hdBackupBillHistory2, beans, mainlist, monthlist, jourlist);

							} else {

								backupBill.setMessage("佣金分账异常" + order_idyj);
								backupBill.setStatus("1");
								logger.info("佣金分账异常：order_idyj：: {}", order_idyj);
								beans.add(backupBill);
							}

						} else {
							logger.info("无佣金分账");
							//备份完结记录
							HdBackupBillHistory hdBackupBillHistory = new HdBackupBillHistory();
							BeanUtils.copyProperties(backupBill, hdBackupBillHistory);
							hdBackupBillHistory.setFinishDate(new Date());
							hdBackupBillHistory.setMoney(money + "");
							beans.add(hdBackupBillHistory);
							hqls.add(hqldelete);
							//删除原记录
							parmsList.add(new Object[]{backupBill.getJournalNum()});
							logger.info("22222222222");
							trService.addWxAccountDetail(hdBackupBillHistory, beans, mainlist, monthlist, jourlist);
						}

					} else {
						backupBill.setMessage("主订单完结分账异常");
						backupBill.setStatus("1");

						logger.info("主订单完结分账异常：order_id：: {}", order_id);
						beans.add(backupBill);
					}

				}catch (Exception e){
					logger.info("调试信息");
					logger.error("操作异常", e);
				}
			}

			baseBeanService.executeHqlsByParamsList(null,hqls.toArray(new String[]{}),parmsList);

		}catch (Exception e){
			logger.error("操作异常", e);
		}


		//批量完成完结分账

		//将已发货且15天内未确认收货的订单加入待完结分账表里
		if (journalNum==null ||journalNum.equals("")) {

			trService.addWqrshOrder();
		}

	}
	public String getAjax() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pl = request.getParameter("pl");
		String staid = request.getParameter("staid");
		String sccid = request.getParameter("sccId");
		this.etype = request.getParameter("etype");
		request.setAttribute("pl", pl);
		request.setAttribute("staid", staid);
		request.setAttribute("sccid", sccid);
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer customery = (TEshopCustomer)sw.getObject(session, "key_customer");
		TEshopCusCom cusy = (TEshopCusCom)sw.getObject(session, "key_shop_cus_com");
		if(customery == null) {
			TEshopCustomer map = (TEshopCustomer)this.baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where staffid = ?", new Object[]{staid});
			sw.setObject(session, "key_customer", map);
		}

		if(cusy == null) {
			TEshopCusCom map1 = (TEshopCusCom)this.baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccid = ?", new Object[]{sccid});
			sw.setObject(session, "key_shop_cus_com", map1);
		}

		this.pageForm = this.getList(sccid, pl);
		HashMap map2 = new HashMap();
		map2.put("pageForm", this.pageForm);
		JSONObject oj = JSONObject.fromObject(map2);
		this.result = oj.toString();
		return "success";
	}

	public PageForm getList(String sccid, String pl) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ljly = request.getParameter("ljly");
		request.setAttribute("ljly", ljly);
		DetachedCriteria dc = DetachedCriteria.forClass(PhoneBill.class, "p");
		dc.add(Restrictions.eq("sccid", sccid));
		if(pl != null && !pl.equals("")) {
			dc.add(Restrictions.eq("fkStatus", pl));
		}

		if(this.search != null && this.search.length() > 0) {
			dc.add(Restrictions.or(Restrictions.eq("journalNum", this.search), Restrictions.like("projectName", this.search, MatchMode.ANYWHERE).ignoreCase()));
		}

		DetachedCriteria goodlist;
		if(this.etype != null && this.etype.equals("elu")) {
			goodlist = DetachedCriteria.forClass(CcomCom.class);
			goodlist.setProjection(Property.forName("comanyId"));
			dc.add(Property.forName("companyid").in(goodlist));
			DetachedCriteria sql = DetachedCriteria.forClass(ContactCompany.class);
			sql.setProjection(Property.forName("ccompanyID"));
			goodlist.add(Property.forName("ccompanyId").in(sql));
			sql.add(Restrictions.like("industryType", "汽车驾校", MatchMode.ANYWHERE));
		}

		dc.addOrder(Order.desc("cashierdate"));

		try {
			this.pageForm = this.baseBeanService.getPageFormByDC(null != this.pageForm?this.pageForm.getPageNumber():1, 6, dc);
		} catch (Exception var22) {
			this.logger.error("PhoneBill获取失败");
		}

		goodlist = null;
		StringBuffer var24 = new StringBuffer();
		StringBuffer ptsql = new StringBuffer();
		StringBuffer comsql = new StringBuffer();
		String goodhql = "";
		String psql = "";
		String csql = "";
		var24.append("select g.goodsbillsid,g.goodsbillskey,");
		var24.append(" g.quantity,g.price,g.goodsnum,g.goodsname,p.image,g.ppid,c.companyid,c.companyname,g.cashierbillsid,");
		var24.append(" g.standard,c.jnumorder,p.tradecode,nvl2(s.orderdate,to_char(s.orderdate,\'YYYY/MM/DD\'),\'\'),p.type ,g.pricetype ");
		var24.append(" from dtgoodsbills g,dt_productpackaging p,dtcashierbills c");
		var24.append(" left join dtConsigneeSheet s on c.jnumorder=s.ordercode ");
		var24.append(" where g.ppid=p.ppid and g.cashierbillsid=c.cashierbillsid and g.cashierbillsid in(");
		ptsql.append("select pa.cashierbillsid,pa.ppid,pa.ppname,pa.companyid,pa.price,pa.standard,p.image,pa.count");
		ptsql.append(" from dt_promotionassociation pa,dtcashierbills cb,dt_productpackaging p");
		ptsql.append(" where pa.cashierbillsid=cb.cashierbillsid and p.ppid=pa.ppid and pa.cashierbillsid in( ");
		comsql.append("select cc.logopath,ccom.compnay_id from dtcontactcompany cc, dt_ccom_com ccom,dtcashierbills cb where ccom.ccompany_id = cc.ccompanyid");
		comsql.append(" and ccom.compnay_id = cb.companyid and cb.cashierbillsid in(");
		ArrayList parmsforbillId = new ArrayList();
		List ptlist = null;
		List comlist = null;
		if(this.pageForm != null && this.pageForm.getList() != null) {
			PhoneBill plist;
			for(int glist = 0; glist < this.pageForm.getList().size(); ++glist) {
				plist = (PhoneBill)this.pageForm.getList().get(glist);
				var24.append("?,");
				ptsql.append("?,");
				comsql.append("?,");
				parmsforbillId.add(plist.getCashierBillsID());
			}

			goodhql = var24.toString();
			goodhql = var24.substring(0, var24.length() - 1) + ")";
			psql = ptsql.toString();
			psql = ptsql.substring(0, ptsql.toString().length() - 1) + ")";
			csql = comsql.toString();
			csql = csql.substring(0, csql.length() - 1) + ")";
			List var23 = this.baseBeanService.getListBeanBySqlAndParams(goodhql.toString(), parmsforbillId.toArray());
			ptlist = this.baseBeanService.getListBeanBySqlAndParams(psql, parmsforbillId.toArray());
			comlist = this.baseBeanService.getListBeanBySqlAndParams(csql, parmsforbillId.toArray());
			ArrayList var25 = null;
			plist = null;

			for(int i = 0; i < this.pageForm.getList().size(); ++i) {
				PhoneBill pb = (PhoneBill)this.pageForm.getList().get(i);
				var25 = new ArrayList();
				Iterator var20 = var23.iterator();

				Object[] obj;
				while(var20.hasNext()) {
					obj = (Object[])var20.next();
					if(pb.getCashierBillsID().equals(obj[10].toString())) {
						var25.add(obj);
					}
				}

				pb.setGoodsList(var25);
				ArrayList var26 = new ArrayList();
				var20 = ptlist.iterator();

				while(var20.hasNext()) {
					obj = (Object[])var20.next();
					if(pb.getCashierBillsID().equals(obj[0].toString())) {
						var26.add(obj);
					}
				}

				pb.setPtgoodsList(var26);
				var20 = comlist.iterator();

				while(var20.hasNext()) {
					obj = (Object[])var20.next();
					if(pb.getCompanyid().equals(obj[1].toString())) {
						pb.setCompanyLogo(obj[0] == null?"":obj[0].toString());
					}
				}

				this.pageForm.getList().set(i, pb);
			}
		}

		return this.pageForm;
	}

	public String delBill() {
		ArrayList objs = new ArrayList();
		ArrayList hqlslist = new ArrayList();
		String hql = "from CashierBills where cashierBillsID = ? ";
		CashierBills cashierBills = (CashierBills)this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{this.cbid});
		String[] hqls = new String[]{"delete from GoodsBills where cashierBillsID = ?", "delete from CashierBills where cashierBillsID = ?", "delete from DtOrderBillAdd where oaBillId = ?", "delete from PayCashierBill where oriJournalNum= ?"};
		Collections.addAll(hqlslist, hqls);
		objs.add(new Object[]{this.cbid});
		objs.add(new Object[]{this.cbid});
		objs.add(new Object[]{this.cbid});
		objs.add(new Object[]{cashierBills.getJournalNum()});
		if(this.flag != null && this.flag.equals("sh")) {
			hqlslist.add("delete from Comments where cashierBillsID= ?");
			objs.add(new Object[]{this.cbid});
		}

		try {
			this.baseBeanService.executeHqlsByParamsList((List)null, (String[])hqlslist.toArray(new String[0]), objs);
		} catch (Exception var7) {
			this.logger.error(this.cbid + ":删除失败!");
		}

		return this.flag != null && this.flag.equals("sh")?this.getReceiptList():this.getPhoneOrdersList();
	}

	public String accountInfo() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		SessionWrap sw = SessionWrap.getInstance();
		Staff staff = (Staff)sw.getObject(session, "key_staff");
		String cashid = request.getParameter("cashid");
		String sql = "select p.type,gb.companyid from dtgoodsbills gb ,dt_productpackaging p where p.ppid = gb.ppid and gb.cashierbillsid = ?";
		List ptlist = this.baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{cashid});

		for(int i = 0; i < ptlist.size(); ++i) {
			Object obj1 = ptlist.get(i);
			Object[] objarr = (Object[])((Object[])obj1);
			if(objarr[0].equals("学员报名")) {
				String sql2 = "select ca.accountemail,ca.accountpassword,y.companyidentifier from dtcaccount ca,dtcompany y where ca.staffid = ? and ca.companyid = ?  and ca.companyid = y.companyid and  rownum = 1";
				Object obj2 = this.baseBeanService.getObjectBySqlAndParams(sql2, new Object[]{staff.getStaffID(), objarr[1]});
				HashMap map = new HashMap();
				map.put("caccount", obj2);
				JSONObject obj = JSONObject.fromObject(map);
				this.result = obj.toString();
				break;
			}
		}

		return "success";
	}

	public String getCashBill() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pl = request.getParameter("pl");
		String staid = request.getParameter("staid");
		String entrance = request.getParameter("entrance");
		String journalNum = request.getParameter("journalNum");
		request.setAttribute("pl", pl);
		request.setAttribute("staid", staid);
		request.setAttribute("entrance", entrance);
		TEshopCusCom r  = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?",new Object[]{sccId});
		if(r!=null) {
			request.setAttribute("user", r.getAccount());
		}

		PhoneBill pb = (PhoneBill)this.baseBeanService.getBeanByHqlAndParams("from PhoneBill where cashierBillsID=?", new Object[]{this.cbid});
		if(journalNum != null && !"".equals(journalNum)) {
			pb = (PhoneBill)this.baseBeanService.getBeanByHqlAndParams("from PhoneBill where journalNum=?", new Object[]{journalNum});

		}
		CcomCom ccomCom =(CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where comanyId = ?",new Object[]{pb.getCompanyid()});

		if(ccomCom!=null){
			request.setAttribute("ccompanyID",ccomCom.getCcompanyId());
		}
		List billList = this.baseBeanService.getListBeanByHqlAndParams("from CashierBills where jNumOrder=?", new Object[]{pb.getJournalNum()});
		String sql = "select oa.receivename,oa.receivetel,oa.receiveaddress from dt_order_bill_add oa where oa.oa_bill_id=?";
		List rlist = this.baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{this.cbid});
		request.setAttribute("rlist", rlist);
		String lsql = "select cc.logopath from dt_ccom_com com ,dtcontactcompany cc where cc.ccompanyid=com.ccompany_id and com.compnay_id=?";
		Object obj = this.baseBeanService.getObjectBySqlAndParams(lsql, new Object[]{pb.getCompanyid()});
		pb.setCompanyLogo(obj != null?obj.toString():null);
		ConsigneeSheet consign = (ConsigneeSheet)this.baseBeanService.getBeanByHqlAndParams("from ConsigneeSheet where orderCode=?", new Object[]{pb.getJournalNum()});
		if(billList.size() > 0) {
			HashMap goodhql = new HashMap();

			for(int goodlist = 0; goodlist < billList.size(); ++goodlist) {
				CashierBills tobj = (CashierBills)billList.get(goodlist);
				if(tobj.getBillsType().equals("收款单")) {
					goodhql.put("付款时间", tobj.getCashierDate());
				} else if(tobj.getBillsType().equals("收款单") && tobj.getProjectName().equals("供应商成本")) {
					goodhql.put("发货时间", tobj.getCashierDate());
				} else if(tobj.getBillsType().equals("收款单")) {
					goodhql.put("发货时间", tobj.getCashierDate());
				} else if(consign != null) {
					goodhql.put("成交时间", consign.getSendDate());
				} else if(tobj.getBillsType().equals("销售出库单")) {
					Calendar list = Calendar.getInstance();
					list.setTime(tobj.getCashierDate());
					list.add(5, 15 + Integer.parseInt(pb.getExtendedTime() == null?"0":pb.getExtendedTime()));
					int df = (new Date()).compareTo(list.getTime());
					request.setAttribute("deliverDate", Integer.valueOf(df));
				}
			}

			request.setAttribute("dateMap", goodhql);
		}

		StringBuffer var38 = new StringBuffer();
		var38.append("select g.goodsbillsid,g.goodsbillskey,g.quantity,g.price,g.goodsnum,g.goodsname,p.image,g.ppid,");
		var38.append("c.ccompanyid,c.ccompanyname,g.goodsID,g.standard,g.companyid ,c.journalnum,c.projectName,p.tradecode,");
		var38.append(" g.pricetype from dtgoodsbills g,dt_productpackaging p,dtcashierbills c");
		var38.append(" where g.ppid=p.ppid and g.cashierbillsid=c.cashierbillsid and g.cashierbillsid=?");
		List var39 = this.baseBeanService.getListBeanBySqlAndParams(var38.toString(), new Object[]{pb.getCashierBillsID()});
		pb.setGoodsList(var39);
		Object[] var40 = (Object[])var39.get(0);
		pb.setTradeCode(var40[15] != null?var40[15].toString():"");
		request.setAttribute("pb", pb);
		var38.delete(0, var38.length());
		var38.append("select pa.cashierbillsid,pa.ppid,pa.ppname,pa.companyid,pa.price,pa.standard,p.image,gm.goodsid,pa.count");
		var38.append(" from dt_promotionassociation pa,dtcashierbills cb,dt_productpackaging p,dtgoodsmanage gm");
		var38.append(" where pa.cashierbillsid=cb.cashierbillsid and p.ppid=pa.ppid and p.goodsid=gm.goodsid and pa.cashierbillsid =?");
		List var41 = this.baseBeanService.getListBeanBySqlAndParams(var38.toString(), new Object[]{pb.getCashierBillsID()});
		pb.setPtgoodsList(var41);
		request.setAttribute("pt", pb);
		SimpleDateFormat var42 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = var42.format(new Date());
		StringBuilder sqls = new StringBuilder();
		sqls.append("select p.ppid,p.companyid,p.goodsname,p.quantity,ps.re_price,p.image,p.stocksize,p.monthsales, ");
		sqls.append("p.standard,p.shangjiastatus,p.logo,p.photo,p.tradecode,p.tradename,p.tradeid,p.brand,p.goodsID ");
		sqls.append(",pap.actPrice,pv.vip,pa.type from DT_PRO_SETUP ps ");
		sqls.append(" inner join dt_ProductPackaging p on p.ppID=ps.ppid ");
		sqls.append(" left join dt_pro_vip pv on pv.ppid = p.ppid and pv.state =\'00\' ");
		sqls.append(" left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state =\'00\' ");
		sqls.append(" left join dt_pro_activity pa on pa.activityId = pap.activityid ");
		sqls.append(" and pa.endtime>=to_date(?,\'yyyy-MM-dd HH24:MI:SS\') and pa.starttime<=to_date(?,\'yyyy-MM-dd HH24:MI:SS\') ");
		sqls.append(" and pa.state <> \'04\' and pa.state <> \'03\' and pa.state <> \'02\' ");
		sqls.append(" where p.goodsid in ");
		sqls.append(" (select d.goodsid from dt_inv_invt d where d.warehouse in (select d.depotid from dtDepotManage d where d.companyid=? and d.depotname = \'销售库\')) ");
		sqls.append(" and p.yjstatus=\'01\' and p.showweixin=\'01\' and rownum < 7 and p.companyID = ? ");
		sqls.append(" and ps.state =\'00\' ");
		List cplist = this.baseBeanService.getListBeanBySqlAndParams(sqls.toString(), new Object[]{currentTime, currentTime, pb.getCompanyid(), pb.getCompanyid()});
		request.setAttribute("cplist", cplist);
		SetSubsidize setSubsidize = (SetSubsidize)this.setSubsidizeService.setSubsidizeByTypeGetcid(pb.getCompanyid());
		BigDecimal pct = BigDecimal.ONE;
		if(setSubsidize != null) {
			BigDecimal hql1 = new BigDecimal(setSubsidize.getTotalPct());
			pct = hql1.divide(new BigDecimal(100)).add(pct);
		}

		request.setAttribute("pct", pct);
		String var43 = " from CashierBills c where c.cashierBillsID=?";
		CashierBills cashierBills = (CashierBills)this.baseBeanService.getBeanByHqlAndParams(var43, new Object[]{this.cbid});
		request.setAttribute("cashierBills", cashierBills);
		String hql = " from DtOrderBillAdd where oaBillId=?";
		DtOrderBillAdd ba = (DtOrderBillAdd)this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{this.cbid});
		HashMap outMap = new HashMap();
		ArrayList wllist = new ArrayList();

		String rs = "";
		String rhql;
		if(ba != null) {
			try {
				if("1".equals(this.refund)) {
					rhql = "from RefundSheet where cashierBillsID = ?";
					this.refundSheet = (RefundSheet)this.baseBeanService.getBeanByHqlAndParams(rhql, new Object[]{this.cbid});
					if(this.refundSheet.getExpCode() != null && this.refundSheet.getWaybillno() != null) {
//						rs = this.trService.wuLiu(this.refundSheet.getExpCode(), this.refundSheet.getWaybillno());
						rs = this.trService.wuLiu(this.refundSheet.getWaybillno());
					}
//				} else if(ba.getExCode() != null && ba.getWaybillno() != null) {
				} else if(ba.getWaybillno() != null) {
//					rs = this.trService.wuLiu(ba.getExCode(), ba.getWaybillno());
					rs = this.trService.wuLiu( ba.getWaybillno());
				}

				if(rs.length() > 0) {
					JSONObject var44 = JSONObject.fromObject(rs);
					Iterator rf = var44.keys();

					while(rf.hasNext()) {
						String name = (String)rf.next();
						outMap.put(name, var44.getString(name));
					}

					JSONObject listJson = JSONObject.fromObject(outMap.get("result"));
					JSONArray jsonArray = JSONArray.fromObject(listJson.get("list"));
					Object[] wobj = jsonArray.toArray();
					if(wobj!=null&&wobj.length>0){
						Object[] temp = new Object[2];
						var44 = JSONObject.fromObject(wobj[0]);
						temp[0] = var44.get("time");
						temp[1] = var44.get("status");
//						logger.info("调试信息");
//						logger.info("调试信息");

						wllist.add(temp);
//						logger.info("调试信息");
					}
					request.setAttribute("wllist", wllist);
				}
			} catch (Exception var37) {
				this.logger.error("查询物流失败");
			}
		}


		if("01".equals(pb.getFkStatus())){
			try {
				UnPayRecord unPayRecord = (UnPayRecord) baseBeanService.getBeanByHqlAndParams("from UnPayRecord where journalNum = ? and status= ?", new Object[]{pb.getJournalNum(),"00"});

				request.setAttribute("unPayRecord", unPayRecord);

			}catch (Exception e){
				logger.error("操作异常", e);
			}

			}
		if("1".equals(this.refund)) {
			rhql = " from RefundSheet where cashierBillsID=? ";
			RefundSheet var45 = (RefundSheet)this.baseBeanService.getBeanByHqlAndParams(rhql, new Object[]{this.cbid});
			request.setAttribute("tp", var45.getRefundType());
			return "refund";
		} else {
			return "edit";
		}
	}

	public String toGetWuliu() {
		try {
			this.refundSheet = (RefundSheet)this.baseBeanService.getBeanByHqlAndParams("from RefundSheet o  where o.cashierBillsID = ? ", new Object[]{this.cbid});
		} catch (Exception var2) {
			var2.printStackTrace();
		}
        HttpServletRequest request = ServletActionContext.getRequest();
        String hql = " from DtOrderBillAdd where oaBillId=?";
        DtOrderBillAdd dt = (DtOrderBillAdd) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cbid});//cbid
        request.setAttribute("dt",dt);
        String exCode=dt.getExCode();
        String waybillno=dt.getWaybillno();
		String hq2 = "select waybillno from expressCompany where exCode=?";

		return "getWuliuDetails";
	}

	public String getWuliuDetails() {
		//卖家的物流信息DtOrderBillAdd
		HttpServletRequest request = ServletActionContext.getRequest();
		String hql = " from DtOrderBillAdd where oaBillId=?";

		DtOrderBillAdd dt = (DtOrderBillAdd) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cbid});//cbid
		request.setAttribute("dt",dt);
//		exCode=dt.getExCode();
		waybillno=dt.getWaybillno();
//		System.err.println("走了这个方法哦");
		//ExCode也是卖家的编码，Waybillno卖家的快递运单号
		if (dt.getWaybillno() != null) {
			try {
				//result卖家调用的物流信息
//				result = trService.wuLiu(dt.getExCode(), dt.getWaybillno());//dt.getExCode()
				result = trService.wuLiu(dt.getWaybillno());//dt.getExCode()
				logger.info("值：{}", result);

			} catch (Exception e) {
				logger.error("操作异常", e);
			}
//			logger.info("数据库物流公司编码和运单号有值，调用第三方接口成功");
			return "success";
		} else {
//			logger.info("数据库物流公司编码和运单号没有值，调用第三方接口失败");
			result = "{ 'msg' : false}";
		}
		return "success";
	}
	public Map<String, Object> receiptPageForm(String user, String state) {
		HashMap remap = new HashMap();
		StringBuffer sql = new StringBuffer();
		sql.append("select d.cashierBillsID,d.useraccount,d.companyName,t.journalNum,d.state,d.csid,t.priceSub,d.cskey,t.companyID,cc.logopath");
		sql.append(" from DTCONSIGNEESHEET d, DTCASHIERBILLS t left join dt_ccom_com com on com.compnay_id=t.companyid");
		sql.append(" left join dtcontactcompany cc on com.ccompany_id=cc.ccompanyid where d.useraccount = ? and t.status!=? and t.cashierBillsID = d.cashierBillsID");
		ArrayList params = new ArrayList();
		params.add(user);
		params.add("99");
		if(this.search != null && this.search.length() > 0) {
			sql.append(" and (t.journalNum = ? or t.projectname like ?)");
			params.add(this.search);
			params.add(this.search);
		} else if(state != null && state.length() > 0) {
			sql.append(" and d.state = ?");
			params.add(state);
		}

		sql.append(" order by d.orderdate desc");
		this.pageForm = this.baseBeanService.getPageFormBySQL(this.pageForm != null?this.pageForm.getPageNumber():1, 10, sql.toString(), "select count(*) from (" + sql.toString() + ")", params.toArray());
		sql.delete(0, sql.length());
		params.clear();
		sql.append("select d.image,g.goodsName,g.price,g.quantity,g.standard,d.ppid,g.cashierbillsid,g.pricetype from dt_ProductPackaging d,dtGoodsBills g");
		sql.append(" where d.ppID=g.ppID and g.cashierBillsID in(");
		StringBuffer ptsql = new StringBuffer();
		ptsql.append("select p.image,pa.ppname,pa.price,pa.ppid,pa.companyid,pa.standard,pa.cashierbillsid");
		ptsql.append(" from dt_promotionassociation pa,dtcashierbills cb,dt_productpackaging p");
		ptsql.append(" where pa.cashierbillsid=cb.cashierbillsid and p.ppid=pa.ppid and pa.cashierbillsid in( ");
		String goodsql = "";
		String ptssql = "";
		if(this.pageForm != null) {
			Object[] goodslist;
			for(int list = 0; list < this.pageForm.getList().size(); ++list) {
				Object ptlist = this.pageForm.getList().get(list);
				goodslist = (Object[])((Object[])ptlist);
				sql.append("?,");
				ptsql.append("?,");
				params.add(goodslist[0]);
			}

			goodsql = sql.toString();
			goodsql = sql.substring(0, sql.length() - 1) + ")";
			ptssql = ptsql.toString();
			ptssql = ptssql.substring(0, ptssql.length() - 1) + ")";
			List var20 = this.baseBeanService.getListBeanBySqlAndParams(goodsql, params.toArray());
			List var21 = this.baseBeanService.getListBeanBySqlAndParams(ptssql, params.toArray());
			goodslist = null;
			ArrayList ptgoodslist = null;
			HashMap gmp = new HashMap();
			HashMap ptmp = new HashMap();
			if(!var20.isEmpty()) {
				for(int i = 0; i < this.pageForm.getList().size(); ++i) {
					ArrayList var22 = new ArrayList();
					ptgoodslist = new ArrayList();
					Object obj = this.pageForm.getList().get(i);
					Object[] o = (Object[])((Object[])obj);

					int x;
					Object[] o2;
					for(x = 0; x < var20.size(); ++x) {
						o2 = (Object[])((Object[])var20.get(x));
						if(o[0].equals(o2[6])) {
							var22.add(o2);
						}
					}

					if(!var21.isEmpty()) {
						for(x = 0; x < var21.size(); ++x) {
							o2 = (Object[])((Object[])var21.get(x));
							if(o[0].equals(o2[6])) {
								ptgoodslist.add(o2);
							}
						}
					}

					gmp.put(o[0].toString(), var22);
					if(!ptgoodslist.isEmpty()) {
						ptmp.put(o[0].toString(), ptgoodslist);
					}
				}
			}

			remap.put("gmp", gmp);
			remap.put("ptmp", ptmp);
		}

		remap.put("pageForm", this.pageForm);
		return remap;
	}

	public String AjaxReceipt() {
		new HashMap();
		if(this.user != null && this.user.length() > 0 && this.state != null && this.state.length() > 0) {
			Map map = this.receiptPageForm(this.user, this.state);
			JSONObject json = JSONObject.fromObject(map);
			this.result = json.toString();
		}

		return "success";
	}

	public String getReceiptList() {
		return "buyer";
	}

	public String Assess() {
		StringBuffer sql = new StringBuffer();
		sql.append("select d.cashierBillsID,d.csid,g.money,d.cskey,t.companyID,g.ppid,p.image,g.goodsName,g.standard,g.quantity,d.userAccount");
		sql.append(" from DTCONSIGNEESHEET d,DTCASHIERBILLS t,dtgoodsbills g,dt_productpackaging p");
		sql.append(" where d.cashierbillsid=g.cashierbillsid");
		sql.append(" and t.cashierBillsID = d.cashierBillsID");
		sql.append(" and g.ppid=p.ppid and d.cashierbillsid=?");
		HttpServletRequest request = ServletActionContext.getRequest();
		List list = this.baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{this.cbid});
		request.setAttribute("list", list);
		return "assess";
	}

	public void automaticReceipt() throws IOException {
		List list = this.baseBeanService.getListBeanByHqlAndParams("from PhoneBill where fkStatus=? and cashierdate > ?", new Object[]{"02", Utilities.getDateFromString("2016-12-01 23:59:59", "yyyy-MM-dd hh:mm:ss")});
		TEshopCusCom cus = null;

		for(int i = 0; i < list.size(); ++i) {
			PhoneBill phone = (PhoneBill)list.get(i);
			List ccList = this.baseBeanService.getListBeanByHqlAndParams("from CashierBills where jNumOrder=? and billsType=?", new Object[]{phone.getJournalNum(), "销售出库单"});
			CashierBills cc = (CashierBills)ccList.get(0);
			Calendar c = Calendar.getInstance();
			int i1 = -15 - Integer.parseInt(phone.getExtendedTime() == null?"0":phone.getExtendedTime());
			c.add(5, i1);
			Date dateToo = c.getTime();
			Calendar c1 = Calendar.getInstance();
			c1.add(5, i1 - 3);
			Date dateToo1 = c1.getTime();
			if(dateToo1.compareTo(cc.getCashierDate()) == 0) {
				cus = (TEshopCusCom)this.baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where companyId=? and acquiesce=?", new Object[]{phone.getCompanyid(), "01"});
				if(cus != null) {
					this.msg.setMobiles(cus.getAccount());
					this.msg.setMessage("订单号为：" + phone.getJournalNum() + "的订单还有3天系统将自动收货");
				}
			}

			if(dateToo.compareTo(cc.getCashierDate()) >= 0) {
				try {
					this.trService.confirmReceipt(phone.getCashierBillsID());
				} catch (Exception var14) {
					this.msg.setMobiles("15210904250");
					this.msg.setMessage("订单号为：" + phone.getJournalNum() + "的订单，系统自动收货时出错，错误代码为：" + var14.getClass().getName());
					String reStr = this.msg.sendMsg();
					logger.info("值：{}", reStr);
				}
			}

			logger.info("自动收货完成");
		}

	}

	public void automaticDistribution() throws IOException {
		List list = this.baseBeanService.getListBeanByHqlAndParams("from PhoneBill where fkStatus=? and cashierdate > ?", new Object[]{"03", Utilities.getDateFromString("2016-12-01 23:59:59", "yyyy-MM-dd hh:mm:ss")});

		for(int i = 0; i < list.size(); ++i) {
			PhoneBill phone = (PhoneBill)list.get(i);
			List ccList = this.baseBeanService.getListBeanByHqlAndParams("from ConsigneeSheet where orderCode=? and state=?", new Object[]{phone.getJournalNum(), "00"});
			ConsigneeSheet cs = (ConsigneeSheet)ccList.get(0);
			Calendar c = Calendar.getInstance();
			Date dateToo = c.getTime();
			if(dateToo.compareTo(cs.getOrderDate()) >= 0) {
				String str = this.trService.Distribution(phone.getCashierBillsID(), "cstaff20160325ZAUAJEU6JH6192643691");
				if(!"操作成功".equals(str)) {
					this.msg.setMobiles("15210904250");
					this.msg.setMessage("订单号为：" + phone.getJournalNum() + "的订单，系统自动分配金币时出错，错误代码为：" + str);
					String reStr = this.msg.sendMsg();
					logger.info("值：{}", reStr);
					break;
				}
			}
		}

	}

	public synchronized String userConfirmationReceipt() {
		Map session = ActionContext.getContext().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer)sw.getObject(session, "key_customer");
		String staid = request.getParameter("staid");
		String id = request.getParameter("id");
		String wfStatus4 = request.getParameter("wfStatus4");
		String journalNum = request.getParameter("journalNum");
		String companyID = request.getParameter("companyID");
		request.setAttribute("staid", staid);
		JSONObject json = new JSONObject();
		String hql = "from PromotionAssociation where ptcashierBillsID=?";
		PromotionAssociation pa = (PromotionAssociation)this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{id});

		String s;
		if(pa != null) {
			s = "from CashierBills where cashierBillsID=?";
			CashierBills cb = (CashierBills)this.baseBeanService.getBeanByHqlAndParams(s, new Object[]{pa.getCashierBillsID()});
			if(cb != null && !"03".equals(cb.getFkStatus()) && !"04".equals(cb.getFkStatus())) {
				json.accumulate("s", "2");
				this.result = json.toString();
				return "success";
			}
		}

		s = this.trService.recognitionHarvest(id);
		if(s != null && s.equals("操作成功")) {
			json.accumulate("s", "1");



		} else if(s!=null&&s.equals("账款结清才可获取金币！")){
			json.accumulate("s", "3");
		}else {
			json.accumulate("s", "0");
		}

		json.accumulate("id", id);
		json.accumulate("staid", staid);
		if(cus != null) {
			request.setAttribute("user", cus.getAccount());
			json.accumulate("user", cus.getAccount());
		}

		this.result = json.toString();
		return "success";
	}

	public void modifyState(List<BaseBean> list) {
		ArrayList obj = new ArrayList();
		ArrayList sqls = new ArrayList();

		for(int str = 0; str < list.size(); ++str) {
			PhoneBill phone = (PhoneBill)list.get(str);
			Calendar c = Calendar.getInstance();
			int i1 = 0 - Integer.parseInt(phone.getExtendedTime() == null?"0":phone.getExtendedTime());
			c.add(5, i1);
			Date dateToo = c.getTime();
			if(dateToo.compareTo(phone.getCashierdate()) >= 0) {
				sqls.add("update dtcashierbills set fkStatus=? where cashierbillsid=?");
				obj.add(new Object[]{"03", phone.getCashierBillsID()});
			}
		}

		String[] var9 = new String[sqls.size()];
		this.baseBeanService.executeSqlsByParmsList((List)null, (String[])sqls.toArray(var9), obj);
	}

	public PageForm getPageForm() {
		return this.pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public MobileMessage getMsg() {
		return this.msg;
	}

	public void setMsg(MobileMessage msg) {
		this.msg = msg;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStaid() {
		return this.staid;
	}

	public void setStaid(String staid) {
		this.staid = staid;
	}

	public String getPl() {
		return this.pl;
	}

	public void setPl(String pl) {
		this.pl = pl;
	}

	public String getCbid() {
		return this.cbid;
	}

	public void setCbid(String cbid) {
		this.cbid = cbid;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSearch() {
		return this.search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getRefund() {
		return this.refund;
	}

	public void setRefund(String refund) {
		this.refund = refund;
	}

	public RefundSheet getRefundSheet() {
		return this.refundSheet;
	}

	public void setRefundSheet(RefundSheet refundSheet) {
		this.refundSheet = refundSheet;
	}

	public String getSccId() {
		return this.sccId;
	}

	public void setSccId(String sccId) {
		this.sccId = sccId;
	}

	public String getEtype() {
		return this.etype;
	}

	public void setEtype(String etype) {
		this.etype = etype;
	}

	public String getExCode() {
		return this.exCode;
	}

	public void setExCode(String exCode) {
		this.exCode = exCode;
	}

	public String getWaybillno() {
		return this.waybillno;
	}

	public void setWaybillno(String waybillno) {
		this.waybillno =waybillno;
	}

	public String getJournalNum() {
		return journalNum;
	}

	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}
}
