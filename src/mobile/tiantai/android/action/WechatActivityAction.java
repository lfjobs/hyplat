package mobile.tiantai.android.action;

import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.ZXingCode;
import hy.ea.websitemall.card.service.QRCodeService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mobile.tiantai.android.bo.DtActivity;
import mobile.tiantai.android.bo.DtActivityAddress;
import mobile.tiantai.android.bo.DtActivityEnroll;
import mobile.tiantai.android.bo.DtEnrollVO;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.util.SessionWrap;

public class WechatActivityAction {

	@Resource
	private ShowExcelService excelService;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private QRCodeService qrCodeService;
	public InputStream excelStream;
	private File image;
	private String imageFileName;
	private int pageNumber;
	private PageForm pageForm;
	private Staff staff;
	private DtActivity dtActivity;
	private StaffAddress address;
	private DtActivityAddress acAddress;
	private String activityId;
	private List<BaseBean> beans;
	private List<BaseBean> bean;
	private List<Object> objlist;
	private List<BaseBean> addressBean;
	private List<BaseBean> timeBeans;
	private List<BaseBean> actDateAddr;
	private String ACTIVITY_ROOT = "upload_files/activity/";
	private String SUFFIX = ".txt";
	private char SPT = '/';
	private String ENCODING = "GBK";
	private String search;
	private String staffName;
	private String weixinCompanyId;
	private COrganization org;
	// private String orgnameId;//所选微店的id（部门id）
	private String price;// 报名价格
	private String result;
	private String inforType;
	private String enrollsuc;
	private int pageSize;

	/**
	 * 微信活动报名
	 */
	public void activityEnroll() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletResponse response = ServletActionContext.getResponse();
		bean = new ArrayList<BaseBean>();
		String sid = serverService.getServerID("staff");
		if (staff.getReference() != null && staff.getReference().length() > 0) {
			String shql = "from Staff where reference=? and status='00'";
			BaseBean bb = baseBeanService.getBeanByHqlAndParams(shql,
					new Object[] { staff.getReference().trim() });
			if (bb != null) {
				Staff s = (Staff) bb;
				if (staff.getStaffName() != null
						&& !staff.getStaffName().trim().equals("")) {
					s.setStaffName(staff.getStaffName());
				}
				if (staff.getReference() != null
						&& !staff.getReference().trim().equals("")) {
					s.setReference(staff.getReference());
				}
				if (staff.getStaffIdentityCard() != null
						&& !staff.getStaffIdentityCard().trim().equals("")) {
					s.setStaffIdentityCard(staff.getStaffIdentityCard().trim()
							.toUpperCase());
				}
				s.setStaus("00");
				sid = s.getStaffID();
				bean.add(s);
			} else {
				staff.setStaffID(sid);
				staff.setStaffStatus("00");
				staff.setStaus("00");
				staff.setSource("微信活动");
				String phql = "select count(*) from Staff ";
				int pcount = baseBeanService.getConutByByHqlAndParams(phql,
						null);
				staff.setStaffCode("NO" + pcount);
				staff.setRecordCode("NO" + pcount);
				staff.setVerifyTime(new Date());
				staff.setStaus("01");
				staff.setGroupCompanySn("groupcompany20120523G3VR9PXHZD0000000021");
				bean.add(staff);
			}
		}
		if ("".equals(weixinCompanyId)) {
			weixinCompanyId = account.getCompanyID();
		}
		String enHql = "from DtActivityEnroll where staffId=? and activityId=?";
		BaseBean en = baseBeanService.getBeanByHqlAndParams(enHql,
				new Object[] { sid, dtActivity.getId() });
		if (en == null) {
			DtActivityEnroll enroll = new DtActivityEnroll();
			enroll.setEnrollId(serverService.getServerID("enroll"));
			enroll.setActivityId(dtActivity.getId());
			enroll.setStaffId(sid);
			if (acAddress != null && acAddress.getAddressId() != null
					&& !acAddress.getAddressId().trim().equals("")) {
				enroll.setAddressId(acAddress.getAddressId());
			}
			if (org.getOrganizationID() != null
					&& !org.getOrganizationID().trim().equals("")) {
				enroll.setOrganizationID(org.getOrganizationID());
			}
			bean.add(enroll);
		} else {
			DtActivityEnroll e = (DtActivityEnroll) en;
			if (acAddress != null && acAddress.getAddressId() != null
					&& !acAddress.getAddressId().trim().equals("")) {
				e.setAddressId(acAddress.getAddressId());
			}
			if (org.getOrganizationID() != null
					&& !org.getOrganizationID().trim().equals("")) {
				e.setOrganizationID(org.getOrganizationID());
			}
			bean.add(e);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(bean, null, null);
		try {
			response.sendRedirect("ea/activity/ea_getSameDayList.jspa?&weixinCompanyId="
					+ weixinCompanyId
					+ "&inforType="
					+ inforType
					+ "&enrollsuc=1");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 查询微店所属顶级部门
	public String getPorg(String org) {
		String hql = "from COrganization where organizationID=?";
		COrganization organization = (COrganization) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { org });
		if (organization.getOrganizationPID().equals(
				organization.getCompanyID())) {
			return org;
		} else {
			return getPorg(organization.getOrganizationPID());
		}
	}

	/**
	 * 发布活动
	 */
	public void activityPublish() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		TEshopCustomer cus = (TEshopCustomer) SessionWrap.getInstance()
				.getObject(session, SessionWrap.KEY_CUSTOMER);
		beans = new ArrayList<BaseBean>();
		bean = new ArrayList<BaseBean>();

		String activityId = serverService.getServerID("dtActivity");
		if (dtActivity.getId() == null) {
			dtActivity.setId(activityId);
		}
		if (weixinCompanyId == null || "".equals(weixinCompanyId)) {
			if (account.getCompanyID() == "company20140902VYQT9VNWJR0000014400"
					|| account.getCompanyID() == "pcompany201007133na5ggn2u30000000001") {
				dtActivity
						.setWeixinCompanyId("company201009046vxdyzy4wg0000000025");// 如果是虚拟公司或者是天太胜威集团总部存在北京天太世统公司下
			} else {
				dtActivity.setWeixinCompanyId(account.getCompanyID());
			}
		} else {
			dtActivity.setWeixinCompanyId(weixinCompanyId);
		}
		if (account == null || account.getStaffID() == null) {
			dtActivity.setStaffID(cus.getStaffid());
		} else {
			dtActivity.setStaffID(account.getStaffID());
		}

		Date time = new Timestamp(System.currentTimeMillis());
		dtActivity.setPublishTime(time);
		dtActivity.setInforType(dtActivity.getInforType());
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String url = request.getSession().getServletContext().getRealPath("/");
		String c = dtActivity.getContent();
		if (c == null) {
			c = "";
		}
		String realPath = getRealPath(url, dtActivity.getId());
		File f = new File(realPath);
		try {
			FileUtils.writeStringToFile(f, c, ENCODING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		String path = sb.append(dtActivity.getId()).append(SUFFIX).toString();
		dtActivity.setContent(path);
		// 保存会议地址
		if (acAddress != null && acAddress.getCompanyId().length() > 0) {
			String[] companyIds = acAddress.getCompanyId().split(",");
			String[] comNames = acAddress.getCompanyName().split(",");
			String[] addresss = acAddress.getCompanyAddr().split(",");
			String[] activityTypes = acAddress.getActivityType().split(",");
			String[] actDates = acAddress.getActDate().split(",");
			String[] ensDates = acAddress.getEnsDate().split(",");
			String[] eneDates = acAddress.getEneDate().split(",");
			String[] pDates = acAddress.getPaDate().split(",");
			DtActivityAddress address = null;
			for (int i = 0; i < actDates.length - 1; i++) {
				for (int j = 0; j <= companyIds.length - 1; j++) {
					address = new DtActivityAddress();
					address.setAddressId(serverService.getServerID("address"));
					if (pDates[j].trim().equals(actDates[i].trim())) {
						address.setActDate(actDates[i]);
						address.setEnsDate(ensDates[i]);
						address.setEneDate(eneDates[i]);
						address.setActivityType(activityTypes[i]);
						address.setPaDate(pDates[j]);
						address.setCompanyId(companyIds[j]);
						address.setCompanyName(comNames[j]);
						address.setCompanyAddr(addresss[j]);
						address.setActivityId(activityId);
						dtActivity.setActivityType(address.getActivityType());
						bean.add(address);
					}
				}
			}
		}
		bean.add(dtActivity);
		baseBeanService.executeHqlsByParamsList(bean, null, null);

		try {
			response.sendRedirect("ea/activity/ea_activityDetails.jspa?activityId="
					+ activityId
					+ "&weixinCompanyId="
					+ weixinCompanyId
					+ "&inforType=02");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * wk 积分管理导出功能
	 * 
	 */

	public String showClientPositioningExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		excelStream = excelService.showExcel(Staff.columnHeadings3(),
				baseBeanService.getListByDC(getCAList()));

		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出积分管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * WK 提供导出查询数据
	 * 
	 */
	@SuppressWarnings("unchecked")
	private DetachedCriteria getCAList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(Staff.class);
		String sql = "  select d.staff_id  from DT_WFJ_JIFEN d where d.company_id = ? and d.wfj_jifen_state = '0'";
		List<Object> list = baseBeanService.getListBeanBySqlAndParams(sql,
				new String[] { account.getCompanyID() });
		dc.add(Restrictions.in("staffID", list.toArray()));

		return dc;
	}

	/**
	 * 
	 * 
	 * wk 功能介绍 根据 公司查询 公司下人员积分 剩余情况 参数 公司ID 根据 登陆的人获取
	 * 
	 * @return
	 */
	public String getjifenlist() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String getdetail = "select  ds.staffkey ,ds.staffid,ds.staffname,ds.staffcode,ds.reference,dj.wfj_jifen_score,dj.wfj_jifen_id from dt_hr_Staff ds, DT_WFJ_JIFEN dj  where ds.staffid in (select d.staff_id from DT_WFJ_JIFEN d where d.company_id = ? and d.wfj_jifen_state='0') and ds.staffstatus='00'";
		String getcountdetaile = "select  count( ds.staffkey) from dt_hr_Staff ds, DT_WFJ_JIFEN dj  where ds.staffid in (select d.staff_id from DT_WFJ_JIFEN d where d.company_id = ? and d.wfj_jifen_state='0') and ds.staffstatus='00'";
		if (inforType != null) {
			getdetail += "and ds.staffname like '%" + inforType + "%'";
			getcountdetaile += "and ds.staffname like '%" + inforType + "%'";
		}
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				pageNumber == 0 ? 10 : pageNumber, getdetail, getcountdetaile,
				new String[] { account.getCompanyID() });
		return "getjifendeteil";
	}

	/*
	 * 
	 * 根据 公司ID 查询 数据公司的订单项目收入预算单 参数 search1订单 2出库单3 收货单4订单收款单
	 */

	public String getcomporder() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		// 位置 0:订单数据数据状态 1:数据KEY值 2:订单系统ID 3:公司名称 4:订单状态 5:订单号* 6系统订单状态7:订单生成时间
		// 8店主负责人 9:店主负责人:10:单价 11:数量
		String hql = "select cb.cashierbillskey, cb.cashierbillsid, cb.companyname,cb.billstype,cb.Journalnum,cb.status,cb.cashierDate,cb.inputName,cb.Organizationid, gb.Goodsid,  cb.departmentName, gb.price, gb.quantity  from dtCashierBills cb ,dtGoodsBills gb where cb.cashierbillsid=gb.cashierbillsid and cb.billsType=?";
		String contsql = "select count(cb.cashierbillskey) from dtCashierBills cb ,dtGoodsBills gb where cb.cashierbillsid=gb.cashierbillsid and cb.billsType=? ";
		Object[] params;
		
		if (account == null) {
			hql += "and cb.companyid in(select d.companyid  from dtcompany d where comgpanyStatus = '00' and d.companypid = 'company201007133na5ggn2u30000000001')";
			contsql += "and cb.companyid in(select d.companyid  from dtcompany d where companyStatus = '00' and d.companypid = 'company201007133na5ggn2u30000000001')";
		}
		{
			hql += "and cb.companyid=? ";
			contsql += "and cb.companyid=? ";
		}
		if (weixinCompanyId != null && weixinCompanyId.equals("1")) {
			hql += "and cb.status = ?";
			contsql += "and cb.status = ?";
			params = new Object[] { "项目收入预算单", account.getCompanyID(), 40 };
		}

		else if (weixinCompanyId != null && weixinCompanyId.equals("2"))
			params = new Object[] { "销售出库单", account.getCompanyID() };
		else if (weixinCompanyId != null && weixinCompanyId.equals("3"))
			params = new Object[] { "采购单", account.getCompanyID() };
		else
			params = new Object[] { "收款单", account.getCompanyID() };

		if (inforType != null && !"".equals(inforType)) {
			hql += "and cb.inputName like '%" + inforType + "%'";
			contsql += "and cb.inputName like '%" + inforType + "%'";
		}
		hql += " and (cb.wfStatus1='01' or cb.wfStatus1='00' )  order by cb.cashierDate DESC";
		contsql += " and (cb.wfStatus1='01' or cb.wfStatus1='00' ) order by cb.cashierDate DESC";

		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				pageNumber == 0 ? 10 : pageNumber, hql, contsql, params);

		return "getorderlist";

	}
	
	/**
	 * 查询当前级别会员个数
	 * @return
	 */
	public String getCount(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String sql="select custype from t_eshop_cuscom t where t.companyid = ?";
		Object custype=baseBeanService.getObjectBySqlAndParams(sql, new Object[]{account.getCompanyID()});
		List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams("from ProductPackaging where type=? and model>=? and model<>? and model<> ? order by model", new Object[]{"会员类型级别",custype,"会员中心","8"});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		List<Integer> list2=new ArrayList<Integer>();
		List<Integer> list3=new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			ProductPackaging pp=(ProductPackaging) list.get(i);
			StringBuffer sql2=new StringBuffer("SELECT count(*) FROM T_ESHOP_CUSCOM t where t.custype = ? and t.state=?  START WITH t.ACCOUNT =(select d.account from T_ESHOP_CUSCOM d" +
				" where d.companyid = ?) CONNECT BY PRIOR t.ACCOUNT=t.SUPERIORAGENT");
			
			int count1=baseBeanService.getConutByBySqlAndParams(sql2.toString(),new Object[]{pp.getModel(),"1",account.getCompanyID()});
			StringBuffer sql3=new StringBuffer("SELECT count(*) FROM T_ESHOP_CUSCOM t where t.custype = ? and t.state=?  START WITH t.ACCOUNT =(select d.account from T_ESHOP_CUSCOM d" +
					" where d.companyid = ?) CONNECT BY PRIOR t.ACCOUNT=t.SUPERIORAGENT");
				
			int count2=baseBeanService.getConutByBySqlAndParams(sql3.toString(),new Object[]{pp.getModel(),"2",account.getCompanyID()});
			list2.add(count1);
			list3.add(count2);
		}
		map.put("list2", list2);
		map.put("list3", list3);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 查询企业会员
	 */
	public String findCompord() {	
		List<Object> result=getlist();
		String sql = result.get(0).toString();
		String countsql = result.get(1).toString();
		Object[] obj = (Object[])result.get(2);
		pageForm = baseBeanService.getPageFormBySQL(
				null != pageForm ? pageForm.getPageNumber() : 1,
				pageNumber == 0 ? 10 : pageNumber, sql, countsql, obj);
		return "complist";
	}
	
	/**
	 * 企业会员
	 * @return
	 */
	private List<Object> getlist(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest req= ServletActionContext.getRequest();
		String custype=req.getParameter("custype");
		String status=req.getParameter("status");
		req.setAttribute("custype", custype);
		req.setAttribute("status", status);
		List<Object> result=new ArrayList<Object>();
		// 位置 0 公司ID  1登陆的组织机构名字 3 公司名字 4 公司生成时间 5 公司状态 6 表示是不是驾校 7表示
		// 集团的还是客户的
		String sql = "select d.companyid,d.companyidentifier,d.companyname, d.companyregisterdate," +
				" case when d.companystatus='00' then '正常使用'" +
				" else '已欠费或者已冻结' end," +
				/*" case when d.industrytype=0 then '是'" +
				" else '否' end," +*/
				" case when d.isst=0 then '否'" +
				" else '是' end," +
				" case when d.companytype='00' then '企业用户'" +
				" when d.companytype='11' then '客户(未付费)' " +
				" else '客户' end  from dtcompany d where d.companyid in(select t.companyid from t_eshop_cuscom t where t.custype = ?" +
				" and t.state = ? and t.pseudo_company_name like ? START WITH t.ACCOUNT =(select d.account from T_ESHOP_CUSCOM d" +
				" where d.companyid = ?) CONNECT BY PRIOR t.ACCOUNT = t.SUPERIORAGENT)";
		String countsql = " select count(d.companykey) from dtcompany d" +
				" where d.companyid in(select t.companyid from t_eshop_cuscom t where t.custype = ?" +
				" and t.state = ? and t.pseudo_company_name like ? START WITH t.ACCOUNT =(select d.account from T_ESHOP_CUSCOM d" +
				" where d.companyid = ?) CONNECT BY PRIOR t.ACCOUNT = t.SUPERIORAGENT) order by d.companyregisterdate desc Nulls last";

		Object[] obj = {custype,status,
				weixinCompanyId != null && !"".equals(weixinCompanyId) ? "%"
						+ weixinCompanyId + "%" : "%%",account.getCompanyID()};
		result.add(sql);
		result.add(countsql);
		result.add(obj);
		return result;
	}

	/**
	 * 查询个人会员
	 * @return
	 */
	public String findhehuochuangye() {
		List<Object> result=getlistgr();
		String sql = result.get(0).toString();
		String contsql = result.get(1).toString();
		String sql2 = result.get(2).toString();
		Object[] obj = (Object[])result.get(3);
		pageForm = baseBeanService.getPageFormBySQL(
				null != pageForm ? pageForm.getPageNumber() : 1,
				pageNumber == 0 ? 10 : pageNumber, sql+sql2, contsql+sql2, obj);
		return "hhlist";
	}
	
	/**
	 * 个人会员
	 * @return
	 */
	private List<Object> getlistgr(){
		List<Object> result=new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest req= ServletActionContext.getRequest();
		String custype=req.getParameter("custype");
		String getcomp = account.getCompanyID();
		String status=req.getParameter("status");
		req.setAttribute("custype", custype);
		req.setAttribute("status", status);
		staffName="2";//设置页面显示不同的标题
		String sql = "SELECT z.sccId,z.account,d.staffname," +
				"d.staffidentitycard,d.reference,d.verifytime,d.source" +
				" FROM dt_hr_staff d,";


		String contsql = "SELECT count(z.account)" +
				" FROM dt_hr_Staff d,";
		Object[] obj = new Object[] {custype,status,getcomp};

		String sql2 = "(SELECT t.sccId,t.staffid,t.account FROM T_ESHOP_CUSCOM t where  t.custype = ? and t.state = ? START WITH t.ACCOUNT =(select d.account from T_ESHOP_CUSCOM d" +
				" where d.companyid = ?) CONNECT BY PRIOR t.ACCOUNT=t.SUPERIORAGENT) z where z.staffid = d.staffid ";


		if (weixinCompanyId != null && !"".equals(weixinCompanyId)) {
			sql2 += "and d.staffname like ?";
			obj = new Object[] {custype,status,getcomp,"%" + weixinCompanyId + "%"};
		}

		sql2+=" order by d.verifytime desc Nulls last";

		result.add(sql);
		result.add(contsql);
		result.add(sql2);
		result.add(obj);
		return result;
	}
	
	/**
	 * 运营商会员导出
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public String exportExcelTable() throws UnsupportedEncodingException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String status=request.getParameter("status");
		// 位置 0订单系统ID 1:公司名称 2:单据类别 3:订单号
		// 4系统收款单状态 5:生成时间 6店主负责人 7:物品类别 8:部门 9:单价 10:数量
		// 11:商家  12:收货联系方式 13:收货地址 14:总金额 15:付款id 16:付款人名称
		// 17:订单号
		if("2".equals(status)){
			List<Object> result=getlist();
			String sql = result.get(0).toString();
			sql="select "+sql.substring(sql.indexOf(",")+1);
			Object[] obj = (Object[])result.get(2);
			//List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql,obj);
			String[] str={"序号","组织机构","公司名称","公司注册日期","公司状态","是否是驾校","公司状态(客户/集团)"};
			excelStream=excelService.showExcel(str, baseBeanService.getListBeanBySqlAndParams(sql,obj));
		}else{
			List<Object> result=getlistgr();
			String sql = result.get(0).toString();
			sql="select "+sql.substring(sql.indexOf(",")+1);
			String sql2=result.get(2).toString();
			Object[] obj = (Object[])result.get(3);
			//List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql+sql2,obj);
			String[] str={"序号","登陆账号","姓名","身份证号","电话号码","购买注册日期","来源"};
			excelStream=excelService.showExcel(str, baseBeanService.getListBeanBySqlAndParams(sql+sql2,obj));
		}
		return "showexcel";
	}
	

	/**
	 * 活动名称、主题
	 * 
	 * @author l
	 * 
	 *         select * from dtccode c where c.codedesc like '%微信活动管理%';
	 *         --scode20141030BZQXMJ7FCR0000003079
	 */
	@SuppressWarnings("rawtypes")
	public String getAjaxThemes() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "select pp.goodsname, pp.ppid from dt_ProductPackaging pp "
				+ " where pp.xmtype = ? and pp.xmtypename = ? and pp.companyID = ?";
		Object[] obj = null;
		String com;
		if (account != null) {
			com = account.getCompanyID();
		} else {
			com = weixinCompanyId;
		}
		if (!result.equals("")) {
			hql += " and pp.goodsname like ?";
			obj = new Object[] { "052241", "微信活动管理", com, "%" + result + "%" };
		} else {
			obj = new Object[] { "052241", "微信活动管理", com };
		}
		List list = baseBeanService.getListBeanBySqlAndParams(hql, obj);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bl", list);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	private String getRealPath(String root, String id) {
		StringBuilder sb = new StringBuilder(root);
		sb.append(relPath(id));
		return sb.toString().replace(SPT, File.separatorChar);
	}

	public String relPath(String id) {
		StringBuilder sb = new StringBuilder(ACTIVITY_ROOT);
		sb.append(dtActivity.getId()).append(SUFFIX);
		return sb.toString();
	}

	/**
	 * 微信活动查询列表
	 * 
	 * @return
	 */
	public String activityList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyId;
		if (account == null) {
			companyId = "company201009046vxdyzy4wg0000000025";
		} else {
			companyId = account.getCompanyID();
		}
		String hql = " from DtActivity t where weixinCompanyId =? and inforType=? order by t.publishTime desc ";
		Object[] param = { companyId, inforType };
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, param);
		return "list";
	}

	/**
	 * 查询在有效期内的活动列表（手机列表） 根据发布人名称查询活动（erp后台管理）
	 */
	@SuppressWarnings("unchecked")
	public String getSameDayList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String sql = "";
		if (null != search && search.equals("byname")) {
			sql = "select t.id,t.theme,t.publishtime,s.staffname,t.weixincompanyid,t.ppid from dtactivity t,  dt_hr_staff s "
					+ "where t.staffid=s.staffid and s.staffname like ? and t.inforType=? and t.weixinCompanyId=? order by t.publishtime desc";
			Object[] param = { "%" + staffName + "%", inforType,
					weixinCompanyId };

			addressBean = baseBeanService.getListBeanBySqlAndParams(sql, param);

		} else {

			// sql="select t.id,t.theme,t.publishtime,s.staffname,t.weixincompanyid,t.ppid from dtactivity t,  dt_hr_staff s where t.staffid=s.staffid and t.weixinCompanyId=? and t.inforType=? "
			// +
			// " and to_char(sysdate,'YYYYMMDD') between to_char(t.publishtime,'YYYYMMDD') and to_char(t.endtime,'YYYYMMDD') order by t.publishtime desc";
			sql = "select t.id,t.theme,t.publishtime,s.staffname,t.weixincompanyid,t.ppid from dtactivity t,  dt_hr_staff s where t.staffid=s.staffid and t.weixinCompanyId=? and t.inforType=? "
					+ " order by t.publishtime desc";

			addressBean = baseBeanService.getListBeanBySqlAndParams(sql,
					new Object[] { weixinCompanyId, inforType });

		}
		request.setAttribute("addressBean", addressBean);
		return "SameDayList";
	}

	/**
	 * ERP后台管理按活动id查询报名人员信息
	 * 
	 * @return
	 */
	public String listByActivityId() {

		/*
		 * String sql =
		 * "select s.staffName,s.reference,s.staffIdentityCard,s.staffDesc,org.organizationname,"
		 * + "ei.owner,ei.telephone" +
		 * " from dt_hr_staff s,dtcorganization org,t_eshop_extinfo ei where " +
		 * " ei.organizationid=org.organizationid" +
		 * " and exists(select 1 from dtactivityenroll t where t.staffid=s.staffid and org.organizationid=t.organizationid"
		 * + " and t.activityid=?)";
		 */
		String sql = "select s.staffName,s.reference,s.staffIdentityCard,s.staffDesc,org.organizationname,ei.owner,"
				+ "ei.telephone,a.companyaddr,a.companyname,a.actdate "
				+ "from dt_hr_staff s,dtcorganization org,t_eshop_extinfo ei,dtactivityenroll t,dtactivityaddress a "
				+ "where ei.organizationid=org.organizationid and t.staffid=s.staffid and org.organizationid=t.organizationid "
				+ "and t.addressid=a.addressid and t.activityid=?";
		Object[] param = { activityId };
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), param);
		return "enrollList";
	}

	/**
	 * 按活动id查看活动详情
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String activityDetails() {
		String hql = " from DtActivity t where id=?";
		Object[] param = { activityId };
		dtActivity = (DtActivity) baseBeanService.getBeanByHqlAndParams(hql,
				param);
		if (dtActivity.getPpid() != null && !"".equals(dtActivity.getPpid())) {
			beans = baseBeanService.getListBeanByHqlAndParams(
					"from ProductPriceCategory where  ppID=? and category=? ",
					new Object[] { dtActivity.getPpid(), "零售价" });
		}
		;
		String addressSql = "select distinct ad.actDate  from DtActivityAddress ad where activityId=? order by ad.actDate desc";
		addressBean = baseBeanService.getListBeanBySqlAndParams(addressSql,
				param);
		String sql = "select s.staffname,s.reference from dtactivity a ,dt_hr_staff s ,dtactivityenroll e "
				+ "where a.id=e.activityid and e.staffid=s.staffid and a.id=?";
		bean = baseBeanService.getListBeanBySqlAndParams(sql, param);
		return "success";
	}

	public String publishLogin() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		CAccount account = (CAccount) session.get("account");
		if ("02".equals(inforType)) {
			return "notice";
		}
		if (account == null && cus == null) {
			return "login";
		} else if ("00".equals(inforType)) {
			return "publisha";
		} else {
			return "notice";
		}
	}

	/**
	 * ERP后台管理删除微信活动
	 * 
	 * @return
	 */
	public String activityDelete() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		beans = new ArrayList<BaseBean>();
		Object[] param = { activityId };
		String hql2 = "from DtActivity t where id=?";
		DtActivity aff = (DtActivity) baseBeanService.getBeanByHqlAndParams(
				hql2, param);
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"删除微信活动(活动主题:" + aff.getTheme() + ")", account);
		beans.add(logBook);
		String hql3 = " delete from Staff s where activityId=?";
		String hql = " delete from DtActivity t where id=?";
		String hql4 = "delete from DtActivityAddress where activityId=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[] {
				hql3, hql, hql4 }, new Object[] { activityId });
		return activityList();
	}

	/**
	 * 导出
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showStaffExcel() {
		String sql = "select s.staffName,s.reference,s.staffIdentityCard,org.organizationname,"
				+ "ei.owner,ei.telephone,s.staffDesc"
				+ " from dt_hr_staff s,dtcorganization org,t_eshop_extinfo ei where "
				+ " ei.organizationid=org.organizationid"
				+ " and exists(select 1 from dtactivityenroll t where t.staffid=s.staffid and org.organizationid=t.organizationid"
				+ " and t.activityid=?)";
		Object[] param = { activityId };
		excelStream = excelService.showExcel(DtEnrollVO.columnHeadings(),
				baseBeanService.getListBeanBySqlAndParams(sql, param));
		return "showexcel";
	}

	/**
	 * ajax根据活动时间查询活动地址
	 * 
	 * @return
	 */
	public String getAddressByTime() {
		String hql = "from DtActivityAddress where activityId=? and actDate=?";
		actDateAddr = baseBeanService.getListBeanByHqlAndParams(
				hql,
				new Object[] { acAddress.getActivityId(),
						acAddress.getActDate() });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adr", actDateAddr);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}


	/**
	 *
	 * 创建名片二维码并返回地址
	 * @return
	 */
	public  String createEwCode(){
		HttpServletRequest request = ServletActionContext.getRequest();

		String companyID = request.getParameter("companyID");
		String sccId = request.getParameter("sccId");
		String logo = "images\\ea\\production\\head2x.png";
		TEshopCusCom cuscom = null;
		if(companyID!=null&&!companyID.equals("")) {
			 cuscom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where companyId = ?", new Object[]{companyID});

			ContactCompany ctc = (ContactCompany) baseBeanService
					.getBeanByHqlAndParams(
							"select y from CcomCom c,ContactCompany y where c.comanyId = ? and c.ccompanyId = y.ccompanyID",
							new Object[]{cuscom.getCompanyId()});
			if (ctc.getLogoPath() != null && !ctc.getLogoPath().equals("")) {
				logo = ctc.getLogoPath();
			}
		}else{
			 cuscom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});

			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
					"from Staff f where f.staffID = ?",
					new Object[]{cuscom.getStaffid()});
			if (staff.getHeadimage() != null
					&& !staff.getHeadimage().equals("")) {
				logo = staff.getHeadimage();
			}

		}
		String basePath = request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath() + "/";

		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		String qrcodePath = ZXingCode.drawLogoQRCode(path,logo, "upload_files\\image\\qrcode\\vipNameCard",cuscom.getAccount(),basePath
				+ "ea/wfjshop/ea_getjspzc.jspa?sccid=" + cuscom.getSccId(), "",300,300);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("qrcodePath", qrcodePath);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
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

	public Staff getStaff() {
		return staff;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public DtActivity getDtActivity() {
		return dtActivity;
	}

	public void setDtActivity(DtActivity dtActivity) {
		this.dtActivity = dtActivity;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getWeixinCompanyId() {
		return weixinCompanyId;
	}

	public void setWeixinCompanyId(String weixinCompanyId) {
		this.weixinCompanyId = weixinCompanyId;
	}

	public StaffAddress getAddress() {
		return address;
	}

	public void setAddress(StaffAddress address) {
		this.address = address;
	}

	public COrganization getOrg() {
		return org;
	}

	public void setOrg(COrganization org) {
		this.org = org;
	}

	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public DtActivityAddress getAcAddress() {
		return acAddress;
	}

	public void setAcAddress(DtActivityAddress acAddress) {
		this.acAddress = acAddress;
	}

	public List<BaseBean> getAddressBean() {
		return addressBean;
	}

	public void setAddressBean(List<BaseBean> addressBean) {
		this.addressBean = addressBean;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getInforType() {
		return inforType;
	}

	public void setInforType(String inforType) {
		this.inforType = inforType;
	}

	public String getEnrollsuc() {
		return enrollsuc;
	}

	public void setEnrollsuc(String enrollsuc) {
		this.enrollsuc = enrollsuc;
	}

	public List<BaseBean> getBean() {
		return bean;
	}

	public void setBean(List<BaseBean> bean) {
		this.bean = bean;
	}

	public List<BaseBean> getTimeBeans() {
		return timeBeans;
	}

	public void setTimeBeans(List<BaseBean> timeBeans) {
		this.timeBeans = timeBeans;
	}

	public List<BaseBean> getActDateAddr() {
		return actDateAddr;
	}

	public List<Object> getObjlist() {
		return objlist;
	}

	public void setObjlist(List<Object> objlist) {
		this.objlist = objlist;
	}

	public void setActDateAddr(List<BaseBean> actDateAddr) {
		this.actDateAddr = actDateAddr;
	}

}
