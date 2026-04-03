package hy.ea.finance.action.BenDis;



import com.tiantai.wfj.service.EarthIndexService;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.Comments;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.finance.BenDis.ConsigneeSheet;
import hy.ea.bo.finance.BenDis.DtOrderBillAdd;
import hy.ea.bo.human.Staff;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.ImageCut;
import hy.ea.util.Utilities;
import hy.ea.util.isms.MobileSMS;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mobile.tiantai.android.action.SDKTestSendTemplateSMS;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hx.httpclient.bi.EasemobIMUsers;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.util.SessionWrap;

/**
 * 收货单管理
 */
/**
 * @author zzl
 *
 */
@Controller
@Scope("prototype")
public class ConsigneeSheetAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private UpLoadFileService fileService;
	private InputStream excelStream;

	private PageForm pageForm;

	private String result;
	private String cskey;
	private int pageNumber;
	private String search;

	private ConsigneeSheet consigneeSheet;
	private Comments comments;
	
	private List<BaseBean> list;
	private List<BaseBean> goodlist;
	private Map<String, Object> map;
	private Map<String, Object> map1;
	private Map<String, Object> map2;
	private String voptype;//打印or查看
	private String stype;//汇总类型
	private String tupn;
	private String stype3;
	
	private String cusType;
	private String user;
	private String uup;
	private String sta;//状态00待评价11交易成功
	private String pingf;
	private String accountName;//暂时做判断
	private Staff staff;
	private ContactCompany contactCompany;
	private TEshopCusCom cuscom;
	private File[] pictureList;  //图片集合
	private String[] pictureListFileName; // 文件名
	private String[] wheelbases;
	private String companyID;
	private String password;
	private ServletRequest request = ServletActionContext.getRequest();
	private SDKTestSendTemplateSMS sdk = new SDKTestSendTemplateSMS();

	private String sccid;
	@Resource
	private EarthIndexService earthIndexService;
	/***
	 * 
	 * 
	 * 收货单列表
	 * 
	 * @return
	 */
	public String getConsigneeSheetList() {
		//insertData();

		pageForm = baseBeanService.getPageFormByDC(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), getLists());
		
		

		return "list";
	}

	@SuppressWarnings("unchecked")
	private DetachedCriteria getLists() {

		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		DetachedCriteria dc = DetachedCriteria.forClass(ConsigneeSheet.class,"p");
		if(stype.equals("01")){
			//买家收货单
		  dc.add(Restrictions.eq("userID", account.getStaffID()));
		}else if(stype.equals("02")){
			//供应商收货单
			DetachedCriteria cri = DetachedCriteria.forClass(DtOrderBillAdd.class, "c");
			cri.add(Property.forName("c.oaBillId").eqProperty("p.cashierBillsID"));
			cri.add(Restrictions.eq("oaGysId", account.getCompanyID()));
		}else if(stype.equals("03")){
			//下级代理
			List<BaseBean> te = baseBeanService
					.getListBeanBySqlAndParams(
							"SELECT account FROM T_ESHOP_CUSCOM where cusType = ? and logOff=0 START WITH staffid=? CONNECT BY PRIOR ACCOUNT=Superioragent",
							new Object[] { cusType,account.getStaffID()});
			if(te.size()==0){
				te.add(null);
			}
			dc.add(Restrictions.in("userAccount", te));
			
		//	
			 
		}else if(stype.equals("04")){
			//整个汇总
		}
		 
		
		dc.addOrder(Order.desc("consignneDate"));

		if (search != null && "search".equals(search)) {
			consigneeSheet = (ConsigneeSheet) session.get("tablesearch");
			if (consigneeSheet.getOrderCode() != null
					&& !consigneeSheet.getOrderCode().equals("")) {
				dc.add(Restrictions.eq("orderCode", consigneeSheet.getOrderCode()));
			}
			if (consigneeSheet.getUserAccount() != null
					&& !consigneeSheet.getUserAccount().equals("")) {
				dc.add(Restrictions.like("userAccount",consigneeSheet.getUserAccount(),
						MatchMode.ANYWHERE));
			}

		}

		return dc;
	}

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", consigneeSheet);
		return getConsigneeSheetList();
	}
	
	public void insertData(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		ConsigneeSheet consigneeSheet = new ConsigneeSheet();
		consigneeSheet.setCsid(serverService.getServerID("csid"));
		consigneeSheet.setCompanyID("company201009046vxdyzy4wg0000000025");
		
		consigneeSheet.setCompanyName(((Company)session.get("currentcompany")).getCompanyName());
		consigneeSheet.setCashierBillsID("111111111111");
		consigneeSheet.setConsigneCode("123456");
		consigneeSheet.setConsigneeAddress("北京东城区东直门外大街42号宇飞大厦801");
		consigneeSheet.setConsigneeName("梦竹");
		consigneeSheet.setConsigneeTel("15210904256");
		consigneeSheet.setConsignneDate(new Date());
		consigneeSheet.setOrderCode("123345656");
		consigneeSheet.setOrderDate(new Date());
		consigneeSheet.setPostcode("100027");
		consigneeSheet.setSendAddress("四川省省委驾校");
		consigneeSheet.setSenderName("娄飞");
		consigneeSheet.setSenderTel("15240254653");
		consigneeSheet.setSendDate(new Date());
		consigneeSheet.setUserAccount("mztests");
		consigneeSheet.setUserID("123");
		consigneeSheet.setUserName("梦竹");
		consigneeSheet.setVipType("普通会员");
		consigneeSheet.setCashierBillsID("cashierBillsID");
		baseBeanService.save(consigneeSheet);
	}

	/**
	 * 
	 * 
	 * 获取查看以及打印页面
	 * 
	 * @return
	 */
	public String getEditOrPrintPage() {
		if (consigneeSheet != null && consigneeSheet.getCsid() != null
				&& !consigneeSheet.getCsid().equals("")) {
			String hql = "from ConsigneeSheet where csid= ?";

			consigneeSheet = (ConsigneeSheet) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { consigneeSheet.getCsid() });
			
			String hqlproduct = "from GoodsBills where cashierBillsID = ? ";
			list  = baseBeanService.getListBeanByHqlAndParams(hqlproduct,new Object[]{consigneeSheet.getCashierBillsID()});
		}
		
		if(voptype!=null&&voptype.equals("e")){
			return "seepage";
			
		}else{
			return "printprev";
		}
		

		
	}


	

	/**
	 * 
	 * 导出
	 * 
	 * @return
	 */
	public String showExcel() {
		excelStream = excelService.showExcel(ConsigneeSheet.columnHeadings(),
				baseBeanService.getListByDC(getLists()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		CLogBook cLogBook = logBookService.saveCLogBook(null, "导出收货单",
				account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}
	//收货单展示
	public String receipt(){
		HttpSession sessions = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer customer = (TEshopCustomer) sw.getObject(sessions,
				SessionWrap.KEY_CUSTOMER);
		String account="";
		if(user!=null&&!user.equals("")){
		     account = user;
		}else{
			if(customer!=null&&!customer.equals("")){
				account = customer.getAccount();
			}else{
				return "login";
			}
		}
		String sql = "select d.cashierBillsID,d.useraccount,d.companyName,"
				+ "t.journalNum,d.state,d.csid,to_char(d.orderDate,'YYYY-MM-DD HH24:MI:SS'),d.consigneeName,d.consigneeAddress,"
				+ "to_char(d.consignneDate,'YYYY-MM-DD HH24:MI:SS'),to_char(d.sendDate,'YYYY-MM-DD HH24:MI:SS'),t.priceSub,d.cskey,t.companyID,t.wlyf"
				+ " from DTCONSIGNEESHEET d,DTCASHIERBILLS t "
				+ " where d.useraccount=? and d.state=? and t.cashierBillsID = d.cashierBillsID";
		
		
		if(tupn.equals("confirm")){
			pageForm= baseBeanService.getPageFormBySQL(
					pageNumber, 8, sql, "select count(*) from ("+sql+")", new Object[]{account,sta});		
			
			
		}else if(tupn.equals("goods")){
			pageForm= baseBeanService.getPageFormBySQL(
					pageNumber, 4, sql, "select count(*) from ("+sql+")", new Object[]{account,sta});		
		}
		
		String ohal = "select d.image,g.goodsName,g.price,g.quantity,g.price,g.goodsNum,g.billsNumbers,d.ppid from dt_ProductPackaging d,dtGoodsBills g "
				+ "where g.cashierBillsID = ? and d.ppID=g.ppID";


		map = new HashMap<String, Object>();
		map1 = new HashMap<String, Object>();
		
		List<BaseBean> goodlist = null;
		if(pageForm!=null){
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object ff = (Object) pageForm.getList().get(i);
				Object[] obj = (Object[]) ff;
				goodlist = baseBeanService.getListBeanBySqlAndParams(ohal,
						new Object[] { obj[0].toString() });
				map1.put(obj[0] + "", goodlist);
				
	
			}
			
		}
		
		
		
		
		if (stype!=null&&!stype.equals("")) {
			if(stype3.equals("pp")){
				map.put("pageForm", pageForm);
				map.put("mapp", map1);
				JSONObject obj = JSONObject.fromObject(map);
				this.result = obj.toString();
				return "success";
			}
			return "seller";
		}else {
			if(stype3!=null&&stype3.equals("ww")){
			
				map.put("pageForm", pageForm);
				map.put("mapp", map1);
				JSONObject obj = JSONObject.fromObject(map);
				this.result = obj.toString();
				return "success";
			}
			return "buyer";
		}
	}
	
	

	/**
	 * 评价保存
	 * */
	public String saveComments() {
		HttpServletRequest re = ServletActionContext.getRequest();
		String path = re.getSession().getServletContext().getRealPath("/");
		String s="1";
		try {
			if (pictureList != null) {
				for (int i = 0; i < pictureList.length; i++) {
					String photopath = fileService.savePhoto(
							path,
							pictureListFileName[i],
							pictureList[i],
							companyID,
							"/material/"
									+ Utilities.getDateString(new Date(),
											"yyyy-MM-dd"));
					String jjPath = photopath.split("\\.")[0] + "small." + photopath.split("\\.")[1];
		    		ImageCut.scale(path+photopath, path+jjPath, 414, 431);
					if (i == 0) {
						comments.setImage1(jjPath);
					} else if (i == 1) {
						comments.setImage2(jjPath);
					} else {
						comments.setImage3(jjPath);
					}
				}
			}
			comments.setComdate(new Date());
			comments.setCommeID(serverService.getServerID("commeid"));
			baseBeanService.save(comments);

			String hql = "from ConsigneeSheet where cskey=?";
			consigneeSheet = (ConsigneeSheet) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { cskey });

			consigneeSheet.setState("11");
			baseBeanService.update(consigneeSheet);
		} catch (Exception e) {
			s="0";
		}
		JSONObject json=new JSONObject();
		json.accumulate("s", s);
		result=json.toString();
		return "success";
	}
	/**
	 * 忘记密码
	 * @return
	 */
	public String forgetPassWord(){
		return "forgetPassWord";
		
	}
	/**重置忘记密码
	 * @return
	 */
	public String confirmPassWord(){
		return "confirmPassWord";
		
	}
	/**
	 * 
	 * 验证方法 掉方法 然后 (new String[]{参数,参数,参数以此类推})
	 * 
	 * @param string
	 * @return
	 */
	private boolean yanzheng(String[] string) {
		boolean b = true;
		for (String s : string) {
			if (s == null || "".equals(s)) {
				b = false;
				break;
			}
		}
		return b;
	}

	/**
	 * 发送 验证码 第一 电话号码 第二 验证码
	 * 
	 * @return
	 * 
	 * 
	 * @throws Exception
	 */
	public String getduanxin() {
		
		String yanz = request.getParameter("yanz");// 验证码
		JSONObject temp = new JSONObject();
		if(yanz!=null)
		{
			
				if (yanzheng(new String[] { cuscom.getAccount(), yanz })) {
					//sdk.getduan( cuscom.getAccount(), yanz);
					MobileSMS.sendMessage(cuscom.getAccount(), yanz);
					temp.accumulate("return", "0");
				} else {
					temp.accumulate("return", "1");
				}
		}else{
			
			java.util.Random random = new java.util.Random();
			int ir=100000;
			int is=999999;
			int i=random.nextInt(is)%(is-ir+1)+ir;
			String yz=""+i;
			//sdk.getduan( cuscom.getAccount(),yz);
			MobileSMS.sendMessage(cuscom.getAccount(), yz);
			temp.accumulate("returna",yz);
		}
		result = temp.toString();

		return "success";

	}
	/**
	 * 修改密码 参数 一个 电话一个 密码 一个密码 密码是新 的w
	 * 
	 */

	public String editPassWord() {
		
		
		String hql = "update TEshopCustomer set password=? where account=? and logOff=0";
		
		
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
				new String[] { hql }, new String[] { password,  cuscom.getAccount() });
		EasemobIMUsers.modifyIMUserPasswordWithAdminToken( cuscom.getAccount(), password);
			
			
		
		
		return "login";
	}
	/**
	 *判断用户名
	 * 
	 */
  public String isacounnt()
  {
	  
	 
		JSONObject temp = new JSONObject();
		String hql = "from TEshopCustomer where account=? AND logOff=0";
	
		TEshopCustomer tc=	(TEshopCustomer) baseBeanService.getBeanByHqlAndParams( hql , new String[] { cuscom.getAccount() });
			if(tc==null)
			{
			temp.accumulate("result", "0");
	         }
			else
			{	
				temp.accumulate("result", "1");
			}
		result = temp.toString();
		return Action.SUCCESS;
	  
	  
  }
	
	/**
	 * 
	 * 
	 * 会员中心
	 */

	public String toVipCenter() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
				.get(ServletActionContext.HTTP_REQUEST);
		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		if(tc!=null) {

			earthIndexService.addBrowseHistory(tc.getSccId(),"会员","");
		}
		if(cus==null){
			if(user!=null){
			    cus  = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0", new Object[]{user});
			}else{
				String url = request.getRequestURL()+"";
				session.setAttribute("url", url);
				session.setAttribute("vipback", request.getParameter("backu")+(request.getParameter("ccompanyId")==null?"":"&ccompanyId="+request.getParameter("ccompanyId")));
				return "login";
			}
		}
        if(request.getParameter("backu")!=null){
		   session.setAttribute("vipback", request.getParameter("backu")+(request.getParameter("ccompanyId")==null?"":"&ccompanyId="+request.getParameter("ccompanyId")));
        }
        cuscom = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
        ProductPackaging pp = (ProductPackaging)baseBeanService.getBeanByHqlAndParams("from ProductPackaging where model = ? and type = ?", new Object[]{cuscom.getCusType(),"会员类型级别"});
        request.setAttribute("custypename", pp.getGoodsName());
        //cuscom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 ", new Object[]{cus.getAccount()});
		if (cuscom.getState().equals("1")) {
			// 个人
			String hql = "from Staff where staffID = ?";
			staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { cuscom.getStaffid() });
		} else {
			String hql = "from ContactCompany t where t.ccompanyID  = (select m.ccompanyId from CcomCom m where m.comanyId = ?)";
			contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { cuscom.getCompanyId()});
		}

		return "tovipcenter";
	}
	
	//中联园区首页二维码显示
	public String queryLog(){
		//ContactCompany往来单位
		String sql = "select erweimaimage from dtContactCompany where ccompanyID=?";
		String compId = "contactCompany20111102YFXHT4NASR0000011516";
		Object objct = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{compId});
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", objct);
	    JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

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

	public String getVoptype() {
		return voptype;
	}

	public void setVoptype(String voptype) {
		this.voptype = voptype;
	}

	public ConsigneeSheet getConsigneeSheet() {
		return consigneeSheet;
	}

	public void setConsigneeSheet(ConsigneeSheet consigneeSheet) {
		this.consigneeSheet = consigneeSheet;
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public String getCusType() {
		return cusType;
	}

	public void setCusType(String cusType) {
		this.cusType = cusType;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public Comments getComments() {
		return comments;
	}

	public void setComments(Comments comments) {
		this.comments = comments;
	}

	public String getUup() {
		return uup;
	}

	public void setUup(String uup) {
		this.uup = uup;
	}
	

	public String getSta() {
		return sta;
	}

	public void setSta(String sta) {
		this.sta = sta;
	}
	public String getPingf() {
		return pingf;
	}

	public void setPingf(String pingf) {
		this.pingf = pingf;
	}
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public ContactCompany getContactCompany() {
		return contactCompany;
	}

	public void setContactCompany(ContactCompany contactCompany) {
		this.contactCompany = contactCompany;
	}

	public TEshopCusCom getCuscom() {
		return cuscom;
	}

	public void setCuscom(TEshopCusCom cuscom) {
		this.cuscom = cuscom;
	}



	public String getCskey() {
		return cskey;
	}

	public void setCskey(String cskey) {
		this.cskey = cskey;
	}




	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public Map<String, Object> getMap1() {
		return map1;
	}

	public void setMap1(Map<String, Object> map1) {
		this.map1 = map1;
	}

	public String getTupn() {
		return tupn;
	}

	public void setTupn(String tupn) {
		this.tupn=tupn;
	}

	public Map<String, Object> getMap2() {
		return map2;
	}

	public void setMap2(Map<String, Object> map2) {
		this.map2 = map2;
	}

	public String getStype3() {
		return stype3;
	}

	public void setStype3(String stype3) {
		this.stype3 = stype3;
	}

	public File[] getPictureList() {
		return pictureList;
	}

	public void setPictureList(File[] pictureList) {
		this.pictureList = pictureList;
	}

	public String[] getWheelbases() {
		return wheelbases;
	}

	public void setWheelbases(String[] wheelbases) {
		this.wheelbases = wheelbases;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String[] getPictureListFileName() {
		return pictureListFileName;
	}

	public void setPictureListFileName(String[] pictureListFileName) {
		this.pictureListFileName = pictureListFileName;
	}

	public String getSccid() {
		return sccid;
	}

	public void setSccid(String sccid) {
		this.sccid = sccid;
	}

	
}
