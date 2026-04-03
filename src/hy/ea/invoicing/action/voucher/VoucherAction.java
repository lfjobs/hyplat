package hy.ea.invoicing.action.voucher;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.finance.CSubjects;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.vo.GoodsCashierBillsVO;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.voucher.DtInvVoucher;
import hy.ea.bo.invoicing.voucher.DtInvVouchers;
import hy.ea.bo.invoicing.voucher.DtInvVoutype;
import hy.ea.bo.invoicing.voucher.FiscalPeriod;
import hy.ea.bo.invoicing.voucher.Subs;
import hy.ea.bo.invoicing.voucher.TemTb;
import hy.ea.bo.invoicing.voucher.endAccount;
import hy.ea.bo.invoicing.voucher.startTime;
import hy.ea.invoicing.service.McoService;
import hy.ea.invoicing.service.impl.CcpbsglServiceImpl;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.DateUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;


/**
 * 凭证管理
 * 陈婷
 * @author Administrator
 */
@Controller
@Scope("prototype")
public class VoucherAction {
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
	@Resource
	private CcpbsglServiceImpl cpbsglService;
	@Resource
	private McoService mcoService;
	private PageForm pageForm;
	private InputStream excelStream;
	private int pageNumber;
	private String search;
	private String parameter;
	private String typeType;
	private String result;
	private String subjectsID;
	private Map<String, DtInvVouchers> logbookmap;
	private Map<String, DtInvVouchers> goodsmap;
	private List<CCode> billTypes;
	private DtInvVoucher voucher;//凭证主表
	private DtInvVouchers voucherbills;//凭证明细
	private Subs subs;
	private GoodsCashierBillsVO cashierBills;//出纳单明细
	private CSubjects csbjects;
	public String billid;
	private String status;//审核注记（初始1,1: 未审核,2：退审核,3：已审核,4：退记帐,5：记帐）
	private String chushi;//开启时间初始化
	private String stime;//判断时间
	private String etime;//判断时间
	private endAccount endaccount;//结账表数据
	private List<CCode> connectionlist;
	private List<CCode> currencylist;//币别
	private List<CCode> codeRelationList;
	private String organizationname;//部门名称
	private String sdate;//凭证开始日期
	private String edate;//凭证结束日期
	private String snumber;//查询凭证开始号码
	private String enumber;//查询凭证结束号码
	//凭证号区间
	private String sjournalnum;
	private String ejournalnum;
	private List<BaseBean> billgoodlist;//凭证明细
	private String subtype;//添加修改 识别参数
	private String voucherID;//删除凭证id
	private String[] voucherIds;//凭证id数组
	private String otype;//判断凭证录入、凭证审核、凭证记账列表
	/**
	 * 凭证录入主页
	 * 
	 */
	public String toVoucherInput(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		//责任人name
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		session.put("ManStaffName", "("+sta.getStaffCode()+")"+sta.getStaffName());
		session.put("ManStaffId", sta.getStaffID());
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "sode20110106hfjes5ucxp0000000017");
		currencylist = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20150601x66efsem5e0000000078");
		//部门名称
		String orghql = "from COrganization where companyID=? and organizationID=?";
		COrganization cOrganization = (COrganization) baseBeanService
				.getBeanByHqlAndParams(orghql, new Object[] {
						account.getCompanyID(),
						organizationID});
		organizationname = cOrganization.getOrganizationName();
		List<BaseBean> list=new ArrayList<BaseBean>();
		list= baseBeanService.getListByDC(getList());
		session.put("list",list);
		return "tovoucherinput";
	}
	/**
	 * 根据    模糊查询票据列表
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String getListBillByName() throws UnsupportedEncodingException {
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount)session.get("account");
		String orgId="";
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return Action.SUCCESS;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		//查询票据
		DetachedCriteria dc = DetachedCriteria
				.forClass(GoodsCashierBillsVO.class);
		if (cashierBills.getJournalNum() != null && !("").equals(cashierBills.getJournalNum())) {
			//凭证号
			dc.add(Restrictions.like("journalNum",
					URLDecoder.decode(cashierBills.getJournalNum(),"UTF-8"), MatchMode.ANYWHERE));
		}
		if (cashierBills.getProjectName() != null && !("").equals(cashierBills.getProjectName())) {
			//项目名称
			dc.add(Restrictions.like("projectName",
					URLDecoder.decode(cashierBills.getProjectName(),"UTF-8"), MatchMode.ANYWHERE));
		}
		if (cashierBills.getGoodsName() != null && !("").equals(cashierBills.getGoodsName())) {
			//物品名称
			dc.add(Restrictions.like("goodsName",
					URLDecoder.decode(cashierBills.getGoodsName(),"UTF-8"), MatchMode.ANYWHERE));
		}
		if(cashierBills.getDepartmentID()!=null&&!"".equals(cashierBills.getDepartmentID())){
			dc.add(Restrictions.eq("departmentID",
					URLDecoder.decode(cashierBills.getDepartmentID(),"UTF-8")));
			orgId=cashierBills.getDepartmentID();
		}else{
			dc.add(Restrictions.eq("departmentID",
					(String)session.get("organizationID")));
			orgId=(String)session.get("organizationID");
		}
		session.put("orgId",orgId);
		
		dc.add(Restrictions.or(Restrictions.eq("goodsStatus", "01"),Restrictions.isNull("goodsStatus")));
		dc.add(Restrictions.or(Restrictions.not(Restrictions.eq("money"," ")),
			Restrictions.not(Restrictions.eq("realmoney"," "))));
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.addOrder(Order.desc("cashierDate"));
		COrganization org=(COrganization) baseBeanService.getBeanByHqlAndParams("from COrganization where organizationID=? and companyID=?", new Object[]{cashierBills.getDepartmentID()==null||"".equals(cashierBills.getDepartmentID())?(String)session.get("organizationID"):cashierBills.getDepartmentID(),account.getCompanyID()});
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), dc);
		map.put("org",org);
		map.put("pageForm", pageForm);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return Action.SUCCESS;
	}
	
    /**
     * 凭证保存
     * @return
     */
	
	public synchronized String saveVoucher(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpServletRequest re2=ServletActionContext.getRequest();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		String fudanshu=re2.getParameter("fudanshu");
		if(groupCompanySn==null){
			return "login";
		}
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		if(subtype.equals("add")){
			String hql="from DtInvVoucher where companyid=? and orgId=? and voucherdate=?";
			Object[] param1 = {account.getCompanyID(),organizationID,voucher.getVoucherdate()};
			List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(hql, param1);
			int num=list.size()+1;
			String str_m = String.valueOf(num);
			String strf ="0000";
			str_m=strf.substring(0, 4-str_m.length())+str_m;
			voucher.setJournalnum(str_m);
			//凭证主表
			voucher.setVoucherid(serverService.getServerID("VoucherBill"));
			voucher.setCompanyid(account.getCompanyID());
			voucher.setCompanyname(account.getCompanyName());
			voucher.setOrgId(organizationID);
			voucher.setOrgName((String)session.get("organizationName"));
			voucher.setMakedate(Utilities.getDateString(new Date(),"yyyyMMddHHmmss"));
			voucher.setSingular(fudanshu);
			//凭证明细
			String VTId="",VTJc="",VTName="";
			String hqlg="from DtInvVoutype where VTComid=?";
			Object[] param = {account.getCompanyID()};
			List<BaseBean> typelist=baseBeanService.getListBeanByHqlAndParams(hqlg, param);
		    if (goodsmap != null) {
		    	//添加类别
		    	a:for (DtInvVouchers vouchers : goodsmap.values()){
		    		String d=vouchers.getDirection();//借贷方向
					String scode=vouchers.getSubjectscode();//科目
					if("C".equals(d)){
						for(int f=0;f<typelist.size();f++){
							DtInvVoutype dtype=(DtInvVoutype) typelist.get(f);
							String vtcs=dtype.getVTCs();
							if(vtcs!=null){
								String vtc[]=dtype.getVTCs().split(",");
								for (int t = 0; t < vtc.length; t++) {
									if(vtc[t].equals(scode)){
										VTId=dtype.getVTId();
										VTJc=dtype.getVTJc();
										VTName=dtype.getVTName();
										break a;
									}
								}
							}
						}
					}
		    	}
		    	if("".equals(VTId)||VTId==null){
		    		a:for (DtInvVouchers vouchers : goodsmap.values()){
		    			String d=vouchers.getDirection();//借贷方向
						String scode=vouchers.getSubjectscode();//科目
		    			if("D".equals(d)){
		    				for(int f=0;f<typelist.size();f++){
								DtInvVoutype dtype=(DtInvVoutype) typelist.get(f);
								String vtds=dtype.getVTDs();
								if(vtds!=null){
									String vtd[]=dtype.getVTDs().split(",");
									for (int t = 0; t < vtd.length; t++) {
										if(vtd[t].equals(scode)){
											VTId=dtype.getVTId();
											VTJc=dtype.getVTJc();
											VTName=dtype.getVTName();
											break a;
										}
									}
								}
							}
		    			}
		    		}
		    	}
				for (DtInvVouchers vouchers : goodsmap.values()) {
					//发现于2015年11月3号。三个月后如果没有使用，建议删除
//					String d=vouchers.getDirection();//借贷方向
//					String scode=vouchers.getSubjectscode();//科目
//					//贷
//					if(d.equals("C")&&VTId.equals("")){
//						for(int f=0;f<typelist.size();f++){
//							DtInvVoutype dtype=(DtInvVoutype) typelist.get(f);
//							String vtcs=dtype.getVTCs();
//							if(vtcs!=null){
//								String vtc[]=dtype.getVTCs().split(",");
//								for (int t = 0; t < vtc.length; t++) {
//									if(vtc[t].equals(scode)){
//										VTId=dtype.getVTId();
//										VTJc=dtype.getVTJc();
//										VTName=dtype.getVTName();
//										
//									}
//								}
//							}
//						}
//					}
//					//借
//					if(d.equals("D")&&VTId.equals("")){
//						for(int f=0;f<typelist.size();f++){
//							DtInvVoutype dtype=(DtInvVoutype) typelist.get(f);
//							String vtds=dtype.getVTDs();
//							if(vtds!=null){
//								String vtd[]=dtype.getVTDs().split(",");
//								for (int t = 0; t < vtd.length; t++) {
//									if(vtd[t].equals(scode)){
//										VTId=dtype.getVTId();
//										VTJc=dtype.getVTJc();
//										VTName=dtype.getVTName();
//										
//									}
//								}
//							}
//						}
//					}
					if(vouchers.getGoodsbillsid()!=null&&!"".equals(vouchers.getGoodsbillsid())){
						//更改票据物品的状态
						String goodbill = "from GoodsBills where goodsBillsID = ?";
						GoodsBills goodb = (GoodsBills) baseBeanService.getBeanByHqlAndParams(goodbill,
								new Object[] { vouchers.getGoodsbillsid() });		
						goodb.setGoodsStatus("02");
						baseBeanList.add(goodb);					
					}	
					vouchers.setVouchersid(serverService.getServerID("VoucherBills"));
					vouchers.setVoucherid(voucher.getVoucherid());
					vouchers.setGroupcsn(groupCompanySn);//集团标识
					CCode code = (CCode) baseBeanService.getBeanByHqlAndParams("from CCode where codeID = ?",
							new Object[] { vouchers.getCurrencyid() });
					vouchers.setCurrencyname(code.getCodeValue());//币别名称		
					baseBeanList.add(vouchers);
				}
			}
		    if(!VTId.equals("")&&!VTId.equals(null))
		    {
		    	voucher.setVTId(VTId);
			    voucher.setVTJc(VTJc);
			    voucher.setVTName(VTName);
		    }else{
		    	String hqld="from DtInvVoutype where VTPd=?";
				Object[] para = {"Y"};
				DtInvVoutype dti=(DtInvVoutype) baseBeanService.getBeanByHqlAndParams(hqld, para);
		    	if("".equals(dti)||dti==null){
		    		HttpServletRequest re=ServletActionContext.getRequest();
		    		re.setAttribute("leibie", "nul");
		    		return "tovoucherinput";
		    	}
				voucher.setVTId(dti.getVTId());
			    voucher.setVTJc(dti.getVTJc());
			    voucher.setVTName(dti.getVTName());
		    }
			baseBeanList.add(voucher);	
		    parameter = "添加凭证（凭证号码" + voucher.getJournalnum() + "）";
		}else if(subtype.equals("edit")){
			//凭证明细
		    if (goodsmap != null) {
				for (DtInvVouchers vouchers : goodsmap.values()) {
					if(vouchers.getCurrencyid()!=null&&!vouchers.getCurrencyid().equals("")){
						parameter = "修改凭证（凭证明细id" + vouchers.getVouchersid() + "）";
						String hql = "from CCode where codeID = ?";
						CCode code = (CCode) baseBeanService.getBeanByHqlAndParams(hql,
								new Object[] { vouchers.getCurrencyid() });
						vouchers.setCurrencyname(code.getCodeValue());//币别名称
						baseBeanList.add(vouchers);
					}
				}
			}
		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,null);
		return "success";
	}
	/**
     * 凭证删除
     * @return
     */
	public String deleteVoucher(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		String vid=voucherID.substring(0, 12);
		if(vid.equals("VoucherBills")){
			String hql = "from DtInvVouchers where vouchersid = ?";
			DtInvVouchers dtiv = (DtInvVouchers) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { voucherID });
			String goodsbillsid=dtiv.getGoodsbillsid();//物品单据id
			String voucherid=dtiv.getVoucherid();//物品单据id
			//删除
			String hqlgb = "delete from DtInvVouchers where goodsbillsid=?";
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hqlgb}, 
					new Object[] {goodsbillsid});
			//更改票据物品的状态
			String goodbill = "from GoodsBills where goodsBillsID = ?";
			GoodsBills goodb = (GoodsBills) baseBeanService.getBeanByHqlAndParams(goodbill,
					new Object[] { goodsbillsid });	
			if(goodb!=null){
				goodb.setGoodsStatus("01");//已归档
				baseBeanList.add(goodb);
			}
			
			List<BaseBean> bgoodlist = new ArrayList<BaseBean>();
			String hqlg="from DtInvVouchers where voucherid=? order by vouchersnum";
			Object[] param = {voucherid};
			bgoodlist=baseBeanService.getListBeanByHqlAndParams(hqlg, param);
			if(bgoodlist.size()==0){
				String hqlvi = "delete from DtInvVoucher where voucherid = ?";
				baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hqlvi}, 
						new Object[] { voucherid});
				parameter = "删除凭证（凭证id" + voucherid + "）";
			}else{
				parameter = "删除凭证（凭证明细物品单据id" + goodsbillsid + "）";
				for(int i=0;i<bgoodlist.size();i++){
					DtInvVouchers dtv=(DtInvVouchers) bgoodlist.get(i);	
					BigDecimal num=new BigDecimal(i+1);
					dtv.setVouchersnum(num);
					baseBeanList.add(dtv);
				}
				
			}
		}else{
			DtInvVoucher dti=(DtInvVoucher)baseBeanService.getBeanByHqlAndParams("from DtInvVoucher where voucherid = ?", 
					new Object[] { voucherID });
			String hql1 = "delete from DtInvVoucher where voucherid = ?";
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql1}, 
					new Object[] { voucherID });
			
			List<BaseBean> bgoodlist = new ArrayList<BaseBean>();
			String hqlg="from DtInvVouchers where voucherid=?";
			Object[] param = {voucherID};
			bgoodlist=baseBeanService.getListBeanByHqlAndParams(hqlg, param);
			for(int i=0;i<bgoodlist.size();i++){
				DtInvVouchers dtv=(DtInvVouchers) bgoodlist.get(i);
				//更改票据物品的状态
				String goodbill = "from GoodsBills where goodsBillsID = ?";
				GoodsBills goodb = (GoodsBills) baseBeanService.getBeanByHqlAndParams(goodbill,
						new Object[] { dtv.getGoodsbillsid() });
				if(goodb!=null){
					goodb.setGoodsStatus("01");//已归档
					baseBeanList.add(goodb);
				}
			}
			String hql2 = "delete from DtInvVouchers where voucherid = ?";
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql2}, 
					new Object[] { voucherID });
			//对凭证号重新排序
			String hql="from DtInvVoucher where companyid=? and orgId=? and voucherdate=? order by makedate desc";
			List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(hql, 
					new Object[] {account.getCompanyID(),organizationID,dti.getVoucherdate()});
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					DtInvVoucher voucher=(DtInvVoucher) list.get(i);
					int num=i+1;
					String str_m = String.valueOf(num);
					String strf ="0000";
					str_m=strf.substring(0, 4-str_m.length())+str_m;
					voucher.setJournalnum(str_m);
					baseBeanList.add(voucher);
				}
			}
		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,null);
		return Action.SUCCESS;
	}
	/**
	 * 打印预览
	 * @return
	 */
	public String toPrint(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest request=ServletActionContext.getRequest();
		String sta=request.getParameter("sta");
		if("voucher".equals(sta)){
			//凭证单据主数据
			String hql="from  DtInvVoucher where companyid=? and orgId=? and voucherid=?";
			voucher= (DtInvVoucher) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[]{account.getCompanyID(),(String) session.get("organizationID"),voucherID});
			
			FiscalPeriod fi=(FiscalPeriod)baseBeanService.getBeanByHqlAndParams("from FiscalPeriod order by year desc",null);
			request.setAttribute("fp",fi);
			request.setAttribute("vdate",DateUtil.getCurrentDate("yyyy-MM-dd"));
			return "print";
		}else{
			//凭证子表
			String hql1="from DtInvVouchers where  groupcsn=? and voucherid=? order by vouchersnum";
			List<BaseBean> list=new ArrayList<BaseBean>();
			list = baseBeanService.getListBeanByHqlAndParams(hql1, new Object[]{session.get("groupCompanySn").toString(),voucherID});
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("list",list);
		JSONObject obj=JSONObject.fromObject(map);
		result = obj.toString();
		return Action.SUCCESS;
		}		
	}
	/**
	 * 供打印预览和查看功能
	 */
	public void toSees(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		//凭证单据主数据
		String hql="from DtInvVoucher where companyid=? and orgId=? and voucherid=?";
		voucher= (DtInvVoucher) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[]{companyID,organizationID,voucherID});
		//凭证子表
		String hql1="from DtInvVouchers where  groupcsn=? and voucherid=? order by vouchersnum";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				hql1, new Object[]{groupCompanySn,voucherID});
	}
	/**
     * 凭证查询
     * @return
     */
	public String toSearch(){
		ActionContext.getContext().getSession().put("sdate",sdate);
		ActionContext.getContext().getSession().put("edate",edate);
		ActionContext.getContext().getSession().put("sjournalnum",sjournalnum);
		ActionContext.getContext().getSession().put("ejournalnum",ejournalnum);
		return toVoucherInput();
	}
	/**
	 * 凭证查询页面
	 * @return
	 */
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(DtInvVoucher.class);
		dc.add(Restrictions.eq("companyid", companyID));
		dc.add(Restrictions.eq("orgId", organizationID));
		dc.add(Restrictions.eq("voucherorigin", "凭证录入"));
		dc.addOrder(Order.desc("makedate"));
		if("pzlr".equals(otype)){
			dc.add(Restrictions.or(Restrictions.eq("vouchercategory",new BigDecimal("1")),Restrictions.eq("vouchercategory",new BigDecimal("2"))));
		}else if("pzsh".equals(otype)){
			dc.add(Restrictions.in("vouchercategory", new Object[]{new BigDecimal("1"),new BigDecimal("2"),new BigDecimal("3")}));
		}else if("pzjz".equals(otype)){
			dc.add(Restrictions.in("vouchercategory", new Object[]{new BigDecimal("3"),new BigDecimal("4"),new BigDecimal("5")}));
		}
		if (search != null && search.equals("search")) {
			if (sdate != null && edate != null && !("").equals(sdate)
					&& !("").equals(edate)) {
				dc.add(Restrictions.between("voucherdate", sdate, edate));
			}
			if (sjournalnum != null && ejournalnum != null && !("").equals(sjournalnum)
					&& !("").equals(ejournalnum)) {
				dc.add(Restrictions.between("journalnum", sjournalnum, ejournalnum));
			}
		}
		return dc;
	}
	/**
	 * 通过凭证id 获得凭证明细
	 */
	public String getInvVouchersById(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		String organizationID = (String) session.get("organizationID");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return Action.SUCCESS;
		}
		//部门名称
		String orghql = "from COrganization where companyID=? and organizationID=?";
		COrganization cOrganization = (COrganization) baseBeanService
				.getBeanByHqlAndParams(orghql, new Object[] {
						account.getCompanyID(),
						organizationID});
		organizationname = cOrganization.getOrganizationName();

		String hqlg="from DtInvVouchers where voucherid=? order by vouchersnum";
		Object[] param = {voucher.getVoucherid()};
		billgoodlist=baseBeanService.getListBeanByHqlAndParams(hqlg, param);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("billgoodlist", billgoodlist);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return Action.SUCCESS;
	}
	//-------------------------------------zj------------------------------------------------------
		/**
		 * 凭证审核和记账主表查询
		 */
		public String getVoucherExamineList(){		
			pageForm= baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), 
					(pageNumber==0?5:pageNumber), getList());
			if("pzsh".equals(otype)){
				return "voucherExamine";}
			else{
				return "voucherAccounting";
			}
		}
		/**
		 * 凭证审核与记账主表条件查询
		 */
		public String getVoucherCondition(){
			Map<String, Object> map=new HashMap<String, Object>();
			map=list();
			String hql=(String) map.get("hql");
			Object[] obj=(Object[]) map.get("obj");
			pageForm=baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), 
					(pageNumber==0?10:pageNumber), hql, obj);
			if("pzsh".equals(otype)){
				return "voucherExamine";
			}else{
				return "voucherAccounting";
			}
			
		}
		
		/**
		 * ajax凭证明细表查询
		 */
		public String getVouchersExamineList(){
			String hql="from DtInvVouchers where voucherid=?";
			Object[] obj={voucherID};
			Map<String,Object> map=new HashMap<String, Object>();
			List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(hql, obj);
			map.put("list",list);
			JSONObject oj = JSONObject.fromObject(map);
			result = oj.toString();
			return Action.SUCCESS;
		}
		/**
		 * 审核和退审核，记账与退记账
		 */
		public String examineVoucher(){
			Map<String,Object> session=ActionContext.getContext().getSession();
			CAccount account=(CAccount)session.get("account");
			String[] hqls=new String[voucherIds.length];
			List<Object[]> parmsList=new ArrayList<Object[]>();
			if("5".equals(status)||"4".equals(status)){
				for(int i=0;i<voucherIds.length;i++){
					hqls[i]="update DtInvVoucher set vouchercategory=?,tallypeople=?,tallydate=?," +
							"tallypeopleid=? where voucherid=? and companyid=?";
					Object[] obj={new BigDecimal(status),account.getStaffName(),Utilities.getDateString(new Date(),"yyyyMMddHHmmss"),
								  account.getStaffID(),voucherIds[i],account.getCompanyID()};	
					parmsList.add(obj);
				}	
			}else if("2".equals(status)||"3".equals(status)){
				for(int i=0;i<voucherIds.length;i++){
					hqls[i]="update DtInvVoucher set vouchercategory=?,auditingpeo=?,auditingdate=?," +
							"auditingpeoid=? where voucherid=? and companyid=?";
					Object[] obj={new BigDecimal(status),account.getStaffName(),Utilities.getDateString(new Date(),"yyyyMMddHHmmss"),
							account.getStaffID(),voucherIds[i],account.getCompanyID()};
					parmsList.add(obj);				
				}		
			}
			
			String organizationID = session.get("organizationID").toString();
			CLogBook logBook = logBookService.saveCLogBook(organizationID, "修改审核或记账状态", account);
			List<BaseBean> list=new ArrayList<BaseBean>();
			list.add(logBook);
			baseBeanService.executeHqlsByParamsList(list,hqls,parmsList);		
			return Action.SUCCESS;
		}
		
		/**
		 * 审核与记账的打印预览
		 */
		public String examineVoucherToPrint(){
			Map<String,Object> session=ActionContext.getContext().getSession();
			CAccount account=(CAccount)session.get("account");
			HttpServletRequest re=ServletActionContext.getRequest();
			String sta=re.getParameter("sta");
			String voucherId=re.getParameter("voucherId");
			String[] vouId=voucherId.split(",");
			List<Object> list=new ArrayList<Object>();
			List<BaseBean> baseList=new ArrayList<BaseBean>();
			if("lord".equals(sta)){
				String hql="from DtInvVoucher where companyid=? and orgId=?";
				list.add(account.getCompanyID());
				list.add((String)session.get("organizationID"));
				for(int i=0;i<vouId.length;i++){
					if(i==0){
						hql+=" and (voucherid=?";
					}else{
						hql+=" or voucherid=?";
					}
					list.add(vouId[i]);
					if(i==(vouId.length-1)){
						hql+=")";
					}
				}
				baseList=baseBeanService.getListBeanByHqlAndParams(hql,list.toArray());
				FiscalPeriod fi=(FiscalPeriod)baseBeanService.getBeanByHqlAndParams("from FiscalPeriod order by year desc",null);
				re.setAttribute("fp",fi);
				re.setAttribute("vdate",DateUtil.getCurrentDate("yyyy-MM-dd"));
				re.setAttribute("baseList",baseList);
				return "examinePrint";
			}else{
				String hql="from DtInvVouchers where voucherid=?";
				Object[] obj={voucherId};
				baseList=baseBeanService.getListBeanByHqlAndParams(hql,obj);
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("baseList",baseList);
				JSONObject js=JSONObject.fromObject(map);
				result=js.toString();
				return Action.SUCCESS;
			}
		}
				
		/**
		 * 查询是否已月结
		 */
		public String getMonthly(){
			CAccount account=(CAccount) ActionContext.getContext().getSession().get("account");
			HttpServletRequest request = ServletActionContext.getRequest();
			String mco_ym=request.getParameter("mco_ym");
			String hql="select count(*) from DtInvMco where yearmonth=? and companyid=?";
			int count=baseBeanService.getConutByByHqlAndParams(hql, new Object[]{mco_ym,account.getCompanyID()});
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("number",count);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return Action.SUCCESS;
		}
		
		/**
		 * 获取hql语句和参数
		 */
		public Map<String, Object> list(){
			CAccount account=(CAccount)ActionContext.getContext().getSession().get("account");
			String hql="from DtInvVoucher where companyid=?";
			List<Object> list=new ArrayList<Object>();
			list.add(account.getCompanyID());
			if(sdate!=null&&!"".equals(sdate)){
				hql+=" and voucherdate>=?";
				list.add(sdate);
			}
			if(edate!=null&&!"".equals(edate)){
				hql+=" and voucherdate<=?";
				list.add(edate);
			}
			if(snumber!=null&&!"".equals(snumber)){
				hql+=" and journalnum>=?";
				list.add(snumber);
			}
			if(enumber!=null&&!"".equals(enumber)){
				hql+=" and journalnum<=?";
				list.add(enumber);
			}
			//organizationname 在这里代表部门Id
			if(organizationname!=null&&!"".equals(organizationname)){
				hql+=" and orgId=?";
				list.add(organizationname);
			}
			if(status!=null&&!"".equals(status)){
				if("3".equals(status)||"4".equals(status)){
					hql+=" and (vouchercategory=? or vouchercategory=?)";
					list.add(new BigDecimal("3"));list.add(new BigDecimal("4"));
				}else if("1".equals(status)||"2".equals(status)){
					hql+=" and (vouchercategory=? or vouchercategory=?)";
					list.add(new BigDecimal("1"));list.add(new BigDecimal("2"));
				}else if("5".equals(status)){
					hql+=" and vouchercategory=?";
					list.add(new BigDecimal("5"));
				}
			}
			hql+=" order by voucherdate,journalnum";
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("hql", hql);
			map.put("obj",list.toArray());
			return map;
		}
		
		/**
		 * 凭证列印
		 */
		public String voucherPrint(){
			CAccount account=(CAccount)ActionContext.getContext().getSession().get("account");
			Map<String,Object> map=list();
			String hql=(String)map.get("hql");
			Object[] obj=(Object[])map.get("obj");	
			//不想在添加字段了，所以把凭证明细的字段来用用
			billgoodlist=baseBeanService.getListBeanByHqlAndParams(hql, obj);
			FiscalPeriod fi=(FiscalPeriod)baseBeanService.getBeanByHqlAndParams("from FiscalPeriod where companyID=? order by year desc",new Object[]{account.getCompanyID()});
			HttpServletRequest request=ServletActionContext.getRequest();
			request.setAttribute("fp",fi);
			request.setAttribute("vdate",DateUtil.getCurrentDate("yyyyMMdd"));
			return "printing";
				
		}
		
		/**
		 * ajax查询列印是否有数据
		 */
		
		public String ajaxVoucherPrint(){
			Map<String,Object> list=list();
			String hql=(String)list.get("hql");
			Object[] obj=(Object[])list.get("obj");
			billgoodlist=baseBeanService.getListBeanByHqlAndParams(hql, obj);
			Map<String,Object> map=new HashMap<String, Object>();
			if(billgoodlist.size()<1){
				map.put("ot","nul");
			}else{
				map.put("ot","notnul");
			}
			JSONObject json = JSONObject.fromObject(map);
			result = json.toString();
			return Action.SUCCESS;
		}
		
		/**
		 * 试算表
		 */
	public String trialBalance(){
		HttpServletRequest request = ServletActionContext.getRequest();
		CAccount account=(CAccount)ActionContext.getContext().getSession().get("account");
		String check=request.getParameter("check");
		String ssub=request.getParameter("ssub");
		String esub=request.getParameter("esub");
		//试算表的存储过程调用
		Object[] obj={serverService.getServerID("TemTb"),account.getCompanyID(),
					sdate,edate,"".equals(ssub)?null:ssub,"".equals(esub)?null:esub,
							"".equals(organizationname)?null:organizationname,"总额式".equals(check)?"A":"B"};
		List<BaseBean> li=mcoService.ProBalances(obj,TemTb.class);
		
		if(li==null||li.size()==0){
			request.setAttribute("trial","null");
		}
		request.setAttribute("date",DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss aa"));
		request.setAttribute("list",li);
		return "trialBalance";
	}
		
	
	//试算表导出excel表格
public String voucherExcel() throws Exception{	
	HttpServletRequest request = ServletActionContext.getRequest();
	//设置字符集
	request.setCharacterEncoding("utf-8");
	String json=request.getParameter("json");
	excelStream=cpbsglService.syVoucherExcel(json);
	return "showexcel";
}	

/**
 * 判断从试算表过来的日期内是否已年结或月结
 */
public String notNullData(){
	HttpServletRequest re=ServletActionContext.getRequest();
	CAccount account=(CAccount)ActionContext.getContext().getSession().get("account");
	String comid=account.getCompanyID();
	String n=re.getParameter("sdate");
	String m=re.getParameter("edate");
	//分割填写的起始和结束日期
	String nd=n.substring(0,6);
	String md=m.substring(0,6);
	//查询填写的起始日期在年度结转表中是否有年结记录
	String hql="from FiscalPeriod where companyID=? and ? between startDate and endDate";
	Object[] obj={comid,nd};
	FiscalPeriod fp=new FiscalPeriod();
	fp=(FiscalPeriod)baseBeanService.getBeanByHqlAndParams(hql,obj);
	//判断如果未有年结记录 或者 填写的起始日期不等于年结记录的起始日期
	if(fp==null||!nd.equals(fp.getStartDate())){
		String hqlt="from DtInvMco where companyid=? and yearmonth>=? and yearmonth<=?";
		Object[] objt={comid,nd,md};
		List<BaseBean> list=new ArrayList<BaseBean>();
		list=baseBeanService.getListBeanByHqlAndParams(hqlt,objt);
		//判断填写的起始日期和结束日期 在月结表中是否有对应日期的记录
		if(list.size()==0){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("n","no");
			JSONObject js=JSONObject.fromObject(map);
			result=js.toString();
			return "success";
		}
	}
	Map<String,Object> map=new HashMap<String, Object>();
	map.put("n","yes");
	JSONObject js=JSONObject.fromObject(map);
	result=js.toString();
	return Action.SUCCESS;
}
		
	//--------------------------------------历史方法---------------------------------
	/**
	 * ajax查询所有已审核票据
	 * @return
	 */
	public List<Object> getProList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> result = new ArrayList<Object>();
		String sql = "from GoodsCashierBillsVO t";
		List<Object> parms = new ArrayList<Object>();
		sql += " where t.companyID = ?";
		parms.add(account.getCompanyID().trim());
		sql += " and t.status = ? ";
		parms.add("04");
			if (null != typeType && !"".equals(typeType)) {
				sql += " and t.journalNum like ?";
				parms.add("%" + typeType+ "%");
			}
		sql += " order by t.cashierDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	@SuppressWarnings("unchecked")
	public String getVoucherList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql="select count(*) from startTime where companyID=?";
		int count=baseBeanService.getConutByByHqlAndParams(hql,new Object[]{account.getCompanyID()});
		if(count>0){
			String sql="select endtime from (select dt.*,ROW_NUMBER() OVER(ORDER BY dt.endtime desc)ru from dt_inv_endaccount dt ) where ru=1 and companyid=?";
			List<BaseBean> list=baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{account.getCompanyID()});
			if(list !=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					Object  obj=(Object)list.get(i);
					if(obj.toString().substring(5,7).equals("12")){
						stime=String.valueOf(Integer.parseInt(obj.toString().substring(0,4))+1)+"-01-01";
						etime=String.valueOf(Integer.parseInt(obj.toString().substring(0,4))+1)+"-01-31";
					}else{
						int tt=Utilities.getDayMouth(Integer.parseInt(obj.toString().substring(0,4)), Integer.parseInt(obj.toString().substring(5,7)));
						String month;
						if((Integer.parseInt(obj.toString().substring(5,7))+1)<10){
							month="0"+String.valueOf(Integer.parseInt(obj.toString().substring(5,7))+1);
						}else{
							month=String.valueOf(Integer.parseInt(obj.toString().substring(5,7))+1);
						}
						stime=String.valueOf(obj.toString().substring(0,5))+month+"-01";
						etime=String.valueOf(obj.toString().substring(0,5))+month+"-"+String.valueOf(tt);
					}
				}
			}else{
				String hql1="from startTime where companyID =? ";
				startTime at=(startTime) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{account.getCompanyID()});
				int tt=Utilities.getDayMouth(Integer.parseInt((Utilities.getDateString(at.getStartTime(), "yyyy-MM")).substring(0,4)), Integer.parseInt((Utilities.getDateString(at.getStartTime(), "yyyy-MM")).substring(5,7)));
				stime=Utilities.getDateString(at.getStartTime(), "yyyy-MM")+"-01";
				etime=(Utilities.getDateString(at.getStartTime(), "yyyy-MM"))+"-"+String.valueOf(tt);
			}
		}
		if(status.equals("00")){
			chushi=String.valueOf(count);
		}
		pageForm= baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber==0?10:pageNumber), getList());
		return "list";
	}
	/**
	 * 查看功能
	 */
	public String toSee(){
		toSees();
		return "see";
	}
	/**
	 * 导出功能
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showExcel(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		String hql="select companyName,organizationName,voucherNum,sum,makePeople,makeDate,auditingPeo,auditingDate,tallyPeople,tallyDate,case when status='00' then '未审核' when status='01' then '已审核' else '已记账' end, appendixNum from dt_inv_voucher t where t.companyID=?";
		Object[] params={account.getCompanyID()};
		List<BaseBean> list=baseBeanService.getListBeanBySqlAndParams(hql, params);
		//excelStream = excelService.showExcel(VoucherBill.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出凭证管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	public String getbill(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		billTypes = codeService.getCCodeListByPID(account.getCompanyID(),"scode20101101dfs3uhdprp0000000029");
		List<Object> list = getProList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1),(pageNumber == 0 ? 10 : pageNumber), sql, "select count(*) "+ sql.substring(sql.indexOf("from")), parms);
		//用于判断票据是否被添加
		String sql1="select count(zi.cashierbillsid) from dt_inv_vouchers zi left join dt_inv_voucher fu on zi.voucherid=fu.voucherid  where fu.companyid=?  and zi.cashierbillsid in (select cashierbillsid from goods_cashiervo where goods_cashiervo.companyID=? and goods_cashiervo.status='04' and journalNum like ?)";
		int count=baseBeanService.getConutByBySqlAndParams(sql1,new Object[]{account.getCompanyID(),account.getCompanyID(),"%"+typeType+"%"});
		String counts="00";
		if(count>0){
			counts="01";	
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("counts", counts);
		map.put("pageForm", pageForm);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	/**
	 * 科目添加操作
	 */
	@SuppressWarnings("unchecked")
	public String getListCsubejstsByPID() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return Action.SUCCESS;
		}
		String sql=" select dt.subjectsid from dtcsubjects dt where  dt.companyid =? and dt.subjectsStatus != '02' " +
				" and dt.subjectsid not in (select ds.subjectspid from dtcsubjects ds where  ds.companyid = ? and ds.subjectsStatus != '02' )";
		List<Subs> idList=baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{account.getCompanyID(),account.getCompanyID()});
		StringBuffer suber=new StringBuffer();
		if(idList!=null && idList.size()>0){
		for(int i = 0;i<idList.size();i++){
			suber.append("'");
			suber.append(idList.get(i)).append("',");
		}
		 suber.deleteCharAt(suber.lastIndexOf(","));
		}else{
			suber.append("'0'");
		}
		String hql="from CSubjects where companyID = ?  and subjectsStatus !='02'and subjectsID in("+ suber.toString()+") order by subjectsNumbers";
		List<BaseBean> subjectsList = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID()});
		Map<String, Object> map = new HashMap<String, Object>();
	    map.put("subjectsList", subjectsList);
	    JSONObject oj = JSONObject.fromObject(map);
	    result = oj.toString();
	    return Action.SUCCESS;
	}
	/**
	 * 添加功能
	 * @return
	 */
	public String toSave(){
		return "add";
	}
	public String save(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		DtInvVoucher vouchers=new DtInvVoucher();
		vouchers.setVoucherid(serverService.getServerID("VoucherBill"));
		vouchers.setCompanyid(account.getCompanyID());
		vouchers.setCompanyname(account.getCompanyName());
		vouchers.setOrgId(organizationID);
		vouchers.setOrgName((String)session.get("organizationName"));
		vouchers.setJournalnum(serverService.getBillID(account.getCompanyID()));//凭证号
		vouchers.setMakedate(voucher.getMakedate());
		vouchers.setMakepeople(account.getAccountEmail());
		vouchers.setAuditingdate(Utilities.getDateString(new Date(),"yyyy-MM-dd HH:mm:ss"));
		Float tt= 0f;
		if(logbookmap !=null){
			for(DtInvVouchers voucherbills:logbookmap.values()){
				voucherbills.setVouchersid(serverService.getServerID("VoucherBills"));
				voucherbills.setVoucherid(vouchers.getVoucherid());
				/*if(voucherbills.getLoan() !=null && !("").equals(voucherbills.getLoan())){
					 tt+=Float.parseFloat(voucherbills.getLoan());
				}*/
				baseBeanList.add(voucherbills);
			}
		}
		//vouchers.setSum(String.valueOf(tt));
		baseBeanList.add(vouchers);
		CLogBook logBook = logBookService.saveCLogBook(organizationID,parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,null);
		return "success";
	}
	
	/**
	 * 删除功能
	 */
	public String deletlist(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除凭证记录管理", account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		String hql="delete VoucherBills a where a.voucherID=? ";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,new String[] { hql }, new Object[] { voucher.getVoucherid()});
		String hqls="delete VoucherBill f where f.voucherID=? and f.companyID=? ";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,new String[] { hqls }, new Object[] { voucher.getVoucherid(),account.getCompanyID()});
		return "success";
	}
	/**
	 * 审核功能
	 * @return
	 */
	public String auditing(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object[]> list=new ArrayList<Object[]>();
		List<BaseBean> listbabe=new ArrayList<BaseBean>(); 
		parameter = "员工："+account.getAccountName();
		CLogBook cLogBook=logBookService.saveCLogBook(organizationID,parameter, account);
		String hql="update VoucherBill set status=?,auditingPeo=?,auditingDate=? where voucherID=? and companyID=?";
		String[] hqls={hql};
		list.add(new Object[]{"01",account.getAccountEmail(),voucher.getAuditingdate(),voucher.getVoucherid(),account.getCompanyID()});
		listbabe.add(cLogBook);
		baseBeanService.executeHqlsByParamsList(listbabe,hqls,list);
		return "success";
	}
	/**
	 * 记账功能
	 * @return
	 */
	public String tally(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<BaseBean> bean=new ArrayList<BaseBean>();
		List<Object[]> list=new ArrayList<Object[]>();
		parameter = "员工："+account.getAccountName();
		CLogBook cLogBook=logBookService.saveCLogBook(organizationID,parameter, account);
		//查询所已记账凭证的子表
		String hql2="from VoucherBills where voucherID=?";
		List<BaseBean> lists=baseBeanService.getListBeanByHqlAndParams(hql2,new Object[]{voucher.getVoucherid()});
		if(lists !=null){
			for(int i=0;i<lists.size();i++){
				List<BaseBean> listbabe=new ArrayList<BaseBean>();
				Subs subs=new Subs();
				DtInvVouchers bills=(DtInvVouchers) lists.get(i);
				int year=Integer.parseInt(voucher.getTallydate().substring(0,4));
				String month=voucher.getTallydate().substring(5,7);
				//查询是否存在科目期初余额表数据
				String hql1="from Subs where companyID=? and subjectsNumbers=? and subjectsName=? and datess=?";
				subs=(Subs) baseBeanService.getBeanByHqlAndParams(hql1,new Object[]{account.getCompanyID(),bills.getSubjectsid(),bills.getSubjectsname(),Utilities.getDateFromString(year+"-"+month+"-01", "yyyy-MM-dd")});
				String hql3="from CSubjects where companyID=? and subjectsNumbers=? and  subjectsName=?";
				CSubjects zsub=new CSubjects();
				zsub=(CSubjects) baseBeanService.getBeanByHqlAndParams(hql3,new Object[]{account.getCompanyID(),bills.getSubjectsid(),bills.getSubjectsname()});
				if(subs==null){
					subs=new Subs();
					subs.setSubearlyID(serverService.getServerID("Subs"));
					subs.setCompanyID(account.getCompanyID());
					subs.setSdirection("平");
					subs.setStartCash("0.0");
					subs.setSubjectsID(zsub.getSubjectsID());
					subs.setSubjectsPID(zsub.getSubjectsPID());
					subs.setSubjectsName(zsub.getSubjectsName());
					subs.setSubjectsNumbers(zsub.getSubjectsNumbers());
					subs.setCurrentLevel(zsub.getCurrentLevel());
					/*if(bills.getLoan()!=null && !bills.getLoan().equals("")){
						subs.setCloan(bills.getLoan());
					}else{
						subs.setCloan("0.0");
					}
					if(bills.getForLoan()!=null && !bills.getForLoan().equals("")){
						subs.setCforLoan(bills.getForLoan());
					}else{
						subs.setCforLoan("0.0");	
					}*/
					float ss1=Float.parseFloat(subs.getCloan())-Float.parseFloat(subs.getCforLoan());
					float ss=(float)(Math.round(ss1*100))/100;//保留两位小数
					if(ss>0){
						subs.setEndCash(String.valueOf(ss));
						subs.setEdirection("借");
					}else if(ss<0){
						subs.setEndCash(String.valueOf(ss).replace("-",""));
						subs.setEdirection("贷");
					}else{
						subs.setEndCash("0.0");
						subs.setEdirection("平");
					}
					subs.setDatess(Utilities.getDateFromString(year+"-"+month+"-01", "yyyy-MM-dd"));
					listbabe.add(subs);
					saveSubsByPid(listbabe, subs.getSubjectsID(),subs.getStartCash(),subs.getCloan(),subs.getCforLoan(),Utilities.getDateFromString(year+"-"+month+"-01", "yyyy-MM-dd"));
				}else{
					float bjie=0f;
					float bdai=0f;
					float sjie=0f;
					float sdai=0f;
					float ss=0f;
					/*if(bills.getLoan() !=null && !bills.getLoan().equals("")){
						bjie=Float.parseFloat(bills.getLoan());
					}
					if(bills.getForLoan() !=null && !bills.getForLoan().equals("")){
						bdai=Float.parseFloat(bills.getForLoan());
					}*/
					if(subs.getCloan() !=null && !subs.getCloan().equals("")){
						sjie=Float.parseFloat(subs.getCloan());
					}
					if(subs.getCforLoan() !=null && !subs.getCforLoan().equals("")){
						sdai=Float.parseFloat(subs.getCforLoan());
					}
					float jie=((float)(Math.round((bjie+sjie)*100))/100);
					float dai=((float)(Math.round((bdai+sdai)*100))/100);
					subs.setCloan(String.valueOf(jie));
					subs.setCforLoan(String.valueOf(dai));
					
					if(subs.getSdirection().equals("贷")){
						ss=(float)(Math.round((Float.parseFloat("-"+subs.getStartCash())+jie-dai)*100))/100;
					}else{
						ss=(float)(Math.round((Float.parseFloat(subs.getStartCash())+jie-dai)*100))/100;
					}
					if(ss>0){
						subs.setEdirection("借");
						subs.setEndCash(String.valueOf(ss));
					}else if(ss<0){
						subs.setEdirection("贷");
						subs.setEndCash(String.valueOf(ss).replace("-", ""));
					}else{
						subs.setEdirection("平");
						subs.setEndCash("0.0");
					}
					
					listbabe.add(subs);
					saveSubsByPid(listbabe, subs.getSubjectsID(),"0.0",String.valueOf(bjie),String.valueOf(bdai),Utilities.getDateFromString(year+"-"+month+"-01", "yyyy-MM-dd"));
				}
				baseBeanService.saveBeansListAndexecuteHqlsByParams(listbabe,null,null);
			}
		}
		String hql="update VoucherBill set status=?,tallyPeople=?,tallyDate=? where voucherID=? and companyID=?";
		String[] hqls={hql};
		list.add(new Object[]{"10",account.getAccountEmail(),voucher.getTallydate(),voucher.getVoucherid(),account.getCompanyID()});
		bean.add(cLogBook);
		baseBeanService.executeHqlsByParamsList(bean,hqls,list);
		return "success";
	}
	/**
	 * 驳回功能
	 */
	/*public String reject(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object[]> list=new ArrayList<Object[]>();
		List<BaseBean> listbabe=new ArrayList<BaseBean>(); 
		parameter = "员工："+account.getAccountName();
		CLogBook cLogBook=logBookService.saveCLogBook(organizationID,parameter, account);
		String hql="update VoucherBill set status=? where voucherID=? and companyID=?";
		String[] hqls={hql};
		list.add(new Object[]{"00",voucher.getVoucherid(),account.getCompanyID()});
		listbabe.add(cLogBook);
		baseBeanService.executeHqlsByParamsList(listbabe,hqls,list);
		return getVoucherList();
	}*/

	/**
	 * 用于判断期初余额值以及末级科目
	 * @return
	 */
	public String getsubm(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String sql="select count(*) from (select goods_cashiervo.subjectsID subjectsID from goods_cashiervo where  goods_cashiervo.companyID=? and goods_cashiervo.journalNum like ? ) s " +
				" where  s.subjectsID not in( select dt.subjectsnumbers from dtcsubjects dt " +
				" where  dt.companyid =? and dt.subjectsStatus != '02' " +
				" and dt.subjectsid not in (select ds.subjectspid from dtcsubjects ds where  ds.companyid = ? and ds.subjectsStatus != '02' ))";
		int count=baseBeanService.getConutByBySqlAndParams(sql,new Object[]{account.getCompanyID(),"%"+typeType+"%",account.getCompanyID(),account.getCompanyID()});
		String counts=String.valueOf(count);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("counts", counts);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	/**
	 * 算科目父级科目(递归)
	 * @return
	 */
	private void saveSubsByPid(List<BaseBean> beans, String subjectsID,String prices,String loan,String floan,Date dates) {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		// 根据子级科目id获取科目父级id
		String hql = "from CSubjects where subjectsID=? and companyID=?";
		csbjects = (CSubjects) baseBeanService.getBeanByHqlAndParams(hql,new Object[] { subjectsID, account.getCompanyID() });
		// 判断父级是否是002（科目树顶级）
		if (csbjects.getSubjectsNumbers().length()>4) {
			float price = Float.valueOf(prices);
			float loans=Float.valueOf(loan);
			float floans=Float.valueOf(floan);
			parameter = "";
			CLogBook logbook = new CLogBook();
			csbjects = (CSubjects) baseBeanService.getBeanByHqlAndParams(hql,new Object[] { csbjects.getSubjectsPID(),account.getCompanyID() });
			// 查询该月父级科目是否有期初余额，有则修改该条数据，没有添加一天新数据
			String hqlsu = "from Subs where subjectsID=? and companyID=? and datess=?";
			subs = (Subs) baseBeanService.getBeanByHqlAndParams(hqlsu,new Object[] {csbjects.getSubjectsID(),account.getCompanyID(),dates });
			if (subs == null) {
				subs = new Subs();
				subs.setCompanyID(account.getCompanyID());
				subs.setSubearlyID(serverService.getServerID("subs"));
				subs.setDatess(dates);
				subs.setSubjectsID(csbjects.getSubjectsID());
				subs.setSubjectsName(csbjects.getSubjectsName());
				subs.setSubjectsNumbers(csbjects.getSubjectsNumbers());
				subs.setSubjectsPID(csbjects.getSubjectsPID());
				subs.setCurrentLevel(String.valueOf(Integer.parseInt(csbjects.getCurrentLevel())-1));
				parameter = "保存科目期初余额(公司代码名称:" + subs.getSubjectsName() + ")";
				logbook = logBookService.saveCLogBook(null, parameter, account);
			} else {
				if (Float.valueOf(subs.getStartCash()) != 0) {
					if (subs.getSdirection().equals("贷")) {
						subs.setStartCash("-" + subs.getStartCash());
					}
					price = price + Float.valueOf(subs.getStartCash());
				}
				loans=loans+Float.valueOf(subs.getCloan());
				floans=floans+Float.valueOf(subs.getCforLoan());
				parameter = "修改科目期初余额(公司代码名称:" + subs.getSubjectsName() + ")";
				logbook = logBookService.saveCLogBook(null, parameter, account);
			}
			String tos = Float.toString((float)(Math.round(price*100))/100);
			String jie=Float.toString((float)(Math.round(loans*100))/100);
			String dai=Float.toString((float)(Math.round(floans*100))/100);
			String tt=Float.toString((float)(Math.round((price+loans-floans)*100))/100);
			if (tos.substring(0, 1).equals("-")) {
				subs.setSdirection("贷");
				subs.setStartCash(tos.substring(1));
			} else if (tos.equals("0.0")) {
				subs.setSdirection("平");
				subs.setStartCash(Float.toString(price));
			} else {
				subs.setSdirection("借");
				subs.setStartCash(Float.toString(price));
			}
			if (tt.substring(0, 1).equals("-")) {
				subs.setEdirection("贷");
				subs.setEndCash(tt.substring(1));
			} else if (tt.equals("0.0")) {
				subs.setEdirection("平");
				subs.setEndCash(tt);
			} else {
				subs.setEdirection("借");
				subs.setEndCash(tt);
			}
			subs.setCforLoan(dai);
			subs.setCloan(jie);
			beans.add(subs);
			beans.add(logbook);
			saveSubsByPid(beans, csbjects.getSubjectsID(), prices,loan,floan,dates);
		}
	}
	
	/**
	 * 结账功能
	 * @return
	 */
	public String  jiezhang(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		//首先判断结账月份
		String sql="select endtime from (select dt.*,ROW_NUMBER() OVER(ORDER BY dt.endtime desc)ru from dt_inv_endaccount dt ) where ru=1 and companyid=?";
		List<BaseBean> list=baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{account.getCompanyID()});
		if(list !=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Object  obj=(Object)list.get(i);
				//用于判断要结账日期
				if(obj.toString().substring(5,7).equals("12")){
					stime=String.valueOf(Integer.parseInt(obj.toString().substring(0,4))+1)+"-01-01";
					etime=String.valueOf(Integer.parseInt(obj.toString().substring(0,4))+1)+"-01-31";
				}else{
					int tt=Utilities.getDayMouth(Integer.parseInt(obj.toString().substring(0,4)), Integer.parseInt(obj.toString().substring(5,7)));
					String month;
					if((Integer.parseInt(obj.toString().substring(5,7))+1)<10){
						month="0"+String.valueOf(Integer.parseInt(obj.toString().substring(5,7))+1);
					}else{
						month=String.valueOf(Integer.parseInt(obj.toString().substring(5,7))+1);
					}
					stime=String.valueOf(obj.toString().substring(0,5))+month+"-01";
					etime=String.valueOf(obj.toString().substring(0,5))+month+"-"+String.valueOf(tt);
				}
			}
		}else{
			String hql1="from startTime where companyID =? ";
			startTime at=(startTime) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{account.getCompanyID()});
			int tt=Utilities.getDayMouth(Integer.parseInt((Utilities.getDateString(at.getStartTime(), "yyyy-MM-dd")).substring(0,4)), Integer.parseInt((Utilities.getDateString(at.getStartTime(), "yyyy-MM-dd")).substring(5,7)));
			stime=Utilities.getDateString(at.getStartTime(), "yyyy-MM")+"-01";
			etime=(Utilities.getDateString(at.getStartTime(), "yyyy-MM"))+"-"+String.valueOf(tt);
		}
		String  hql="select count(*) from VoucherBill where companyID=? and makeDate  between ? and ? and (status='00' or status='01')";
		int count=baseBeanService.getConutByByHqlAndParams(hql, new Object[]{account.getCompanyID(),Utilities.getDateFromString(stime, "yyyy-MM-dd"),Utilities.getDateFromString(etime, "yyyy-MM-dd")});
		if(count==0){
			List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
			endAccount eaccout=new endAccount();
			eaccout.setCompanyID(account.getCompanyID());
			eaccout.setCompanyName(account.getCompanyName());
			eaccout.setEndaID(serverService.getServerID("endAccount"));
			eaccout.setEndTime(Utilities.getDateFromString(stime, "yyyy-MM-dd"));
			baseBeanList.add(eaccout);
			String sstime;
			if(stime.substring(5, 7).equals("12")){
				sstime=String.valueOf(Integer.parseInt(stime.substring(0,4))+1)+"-01-01";
			}else{
				int tt=Utilities.getDayMouth(Integer.parseInt(stime.substring(0,4)), Integer.parseInt(stime.substring(5,7)));
				String month;
				if((Integer.parseInt(stime.substring(5,7))+1)<10){
					month="0"+String.valueOf(Integer.parseInt(stime.substring(5,7))+1);
				}else{
					month=String.valueOf(Integer.parseInt(stime.substring(5,7))+1);
				}
				sstime=String.valueOf(stime.substring(0,5))+month+"-01";
			}
			//用于保存下个月的期初余额
			String hql1="from Subs where companyID=? and datess=?";
			List<BaseBean> lists=baseBeanService.getListBeanByHqlAndParams(hql1,new Object[]{account.getCompanyID(),Utilities.getDateFromString(stime, "yyyy-MM-dd")});
			if(lists !=null){
				for(int i=0;i<lists.size();i++){
					Subs subs=new Subs();
					Subs sub=(Subs) lists.get(i);
					subs.setSubearlyID(serverService.getServerID("Subs"));//id
					subs.setCompanyID(account.getCompanyID());//公司id
					subs.setSubjectsID(sub.getSubjectsID());//科目id
					subs.setSubjectsPID(sub.getSubjectsPID());//科目父id
					subs.setSubjectsNumbers(sub.getSubjectsNumbers());//科目编号
					subs.setSubjectsName(sub.getSubjectsName());//科目名称
					subs.setCurrentLevel(sub.getCurrentLevel());//科目级别
					subs.setDatess(Utilities.getDateFromString(sstime, "yyyy-MM-dd"));//日期
					subs.setStartCash(sub.getEndCash());//期初余额
					subs.setSdirection(sub.getEdirection());//期初方向
					subs.setCloan("0.0");
					subs.setCforLoan("0.0");
					subs.setEdirection(sub.getEdirection());
					subs.setEndCash(sub.getEndCash());
					baseBeanList.add(subs);
				}
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,null);
		}
		return "jiezhang";
	}
	/**
	 * 用于结账的计算平衡
	 * @return
	 */
	public String getjie(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		//用于判断要结账的日期
		String sql="select endtime from (select dt.*,ROW_NUMBER() OVER(ORDER BY dt.endtime desc)ru from dt_inv_endaccount dt ) where ru=1 and companyid=?";
		List<BaseBean> list=baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{account.getCompanyID()});
		if(list !=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Object  obj=(Object)list.get(i);
				//用于判断要结账日期
				if(obj.toString().substring(5,7).equals("12")){
					stime=String.valueOf(Integer.parseInt(obj.toString().substring(0,4))+1)+"-01-01";
					etime=String.valueOf(Integer.parseInt(obj.toString().substring(0,4))+1)+"-01-31";
				}else{
					int tt=Utilities.getDayMouth(Integer.parseInt(obj.toString().substring(0,4)), Integer.parseInt(obj.toString().substring(5,7)));
					String month;
					if((Integer.parseInt(obj.toString().substring(5,7))+1)<10){
						month="0"+String.valueOf(Integer.parseInt(obj.toString().substring(5,7))+1);
					}else{
						month=String.valueOf(Integer.parseInt(obj.toString().substring(5,7))+1);
					}
					stime=String.valueOf(obj.toString().substring(0,5))+month+"-01";
					etime=String.valueOf(obj.toString().substring(0,5))+month+"-"+String.valueOf(tt);
				}
			}
		}else{
			String hql1="from startTime where companyID =? ";
			startTime at=(startTime) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{account.getCompanyID()});
			int tt=Utilities.getDayMouth(Integer.parseInt((Utilities.getDateString(at.getStartTime(), "yyyy-MM-dd")).substring(0,4)), Integer.parseInt((Utilities.getDateString(at.getStartTime(), "yyyy-MM-dd")).substring(5,7)));
			stime=Utilities.getDateString(at.getStartTime(), "yyyy-MM")+"-01";
			etime=(Utilities.getDateString(at.getStartTime(), "yyyy-MM"))+"-"+String.valueOf(tt);
		}
		String counts="00";
		String  hql="select count(*) from VoucherBill where companyID=? and makeDate  between ? and ? and (status='00' or status='01')";
		int count=baseBeanService.getConutByByHqlAndParams(hql, new Object[]{account.getCompanyID(),Utilities.getDateFromString(stime, "yyyy-MM-dd"),Utilities.getDateFromString(etime, "yyyy-MM-dd")});
		if(count>0){
			 counts="01";//有未记账的凭证
		}else{
			String sql1="select  sum(zi.loan),sum(zi.forloan) from dt_inv_vouchers  zi left join dt_inv_voucher fu on  zi.voucherid=fu.voucherid where fu.companyid=? and fu.tallydate between ? and ?";
			List<Object> lists=baseBeanService.getListBeanBySqlAndParams(sql1, new Object[]{account.getCompanyID(),Utilities.getDateFromString(stime, "yyyy-MM-dd"),Utilities.getDateFromString(etime, "yyyy-MM-dd")});
			if(lists !=null && lists.size()>0){
				for(int j=0;j<lists.size();j++){
					Object[] obj = (Object[])lists.get(j);
					float nian1=0f;
					float nian2=0f;
					 for(int n = 0 ; n < obj.length; n ++){
			                if(n==0){
			                    if((obj[j])!=null){
			                         nian1= Float.valueOf(obj[j].toString());
			                    }
			                 }else if(n==1){
			                     if((obj[j])!=null){
			                        nian2=Float.valueOf(obj[j].toString()); 
			                     }
			                }
			            }
					 if(nian1 !=nian2){
						 counts="02";//账目余额不平 
					 }
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("counts", counts);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public GoodsCashierBillsVO getCashierBills() {
		return cashierBills;
	}
	public void setCashierBills(GoodsCashierBillsVO cashierBills) {
		this.cashierBills = cashierBills;
	}
	public List<CCode> getBillTypes() {
		return billTypes;
	}
	public void setBillTypes(List<CCode> billTypes) {
		this.billTypes = billTypes;
	}
	public String getTypeType() {
		return typeType;
	}
	public void setTypeType(String typeType) {
		this.typeType = typeType;
	}
	public String getSubjectsID() {
		return subjectsID;
	}
	public void setSubjectsID(String subjectsID) {
		this.subjectsID = subjectsID;
	}
	public String getBillid() {
		return billid;
	}
	public void setBillid(String billid) {
		this.billid = billid;
	}
	public Subs getSubs() {
		return subs;
	}
	public void setSubs(Subs subs) {
		this.subs = subs;
	}
	public CSubjects getCsbjects() {
		return csbjects;
	}
	public void setCsbjects(CSubjects csbjects) {
		this.csbjects = csbjects;
	}
	public String getChushi() {
		return chushi;
	}
	public void setChushi(String chushi) {
		this.chushi = chushi;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public endAccount getEndaccount() {
		return endaccount;
	}
	public void setEndaccount(endAccount endaccount) {
		this.endaccount = endaccount;
	}

	public Map<String, DtInvVouchers> getLogbookmap() {
		return logbookmap;
	}

	public void setLogbookmap(Map<String, DtInvVouchers> logbookmap) {
		this.logbookmap = logbookmap;
	}
	public Map<String, DtInvVouchers> getGoodsmap() {
		return goodsmap;
	}
	public void setGoodsmap(Map<String, DtInvVouchers> goodsmap) {
		this.goodsmap = goodsmap;
	}
	public DtInvVoucher getVoucher() {
		return voucher;
	}

	public void setVoucher(DtInvVoucher voucher) {
		this.voucher = voucher;
	}

	public DtInvVouchers getVoucherbills() {
		return voucherbills;
	}

	public void setVoucherbills(DtInvVouchers voucherbills) {
		this.voucherbills = voucherbills;
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
	public List<CCode> getCurrencylist() {
		return currencylist;
	}
	public void setCurrencylist(List<CCode> currencylist) {
		this.currencylist = currencylist;
	}
	public String getOrganizationname() {
		return organizationname;
	}
	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
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
	public String getSjournalnum() {
		return sjournalnum;
	}
	public void setSjournalnum(String sjournalnum) {
		this.sjournalnum = sjournalnum;
	}
	public String getEjournalnum() {
		return ejournalnum;
	}
	public void setEjournalnum(String ejournalnum) {
		this.ejournalnum = ejournalnum;
	}
	public List<BaseBean> getBillgoodlist() {
		return billgoodlist;
	}
	public void setBillgoodlist(List<BaseBean> billgoodlist) {
		this.billgoodlist = billgoodlist;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public String getVoucherID() {
		return voucherID;
	}
	public void setVoucherID(String voucherID) {
		this.voucherID = voucherID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSnumber() {
		return snumber;
	}
	public void setSnumber(String snumber) {
		this.snumber = snumber;
	}
	public String getEnumber() {
		return enumber;
	}
	public void setEnumber(String enumber) {
		this.enumber = enumber;
	}
	public String[] getVoucherIds() {
		return voucherIds;
	}
	public void setVoucherIds(String[] voucherIds) {
		this.voucherIds = voucherIds;
	}
	public String getOtype() {
		return otype;
	}
	public void setOtype(String otype) {
		this.otype = otype;
	}

	
}