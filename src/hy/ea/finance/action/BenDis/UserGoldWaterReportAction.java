package hy.ea.finance.action.BenDis;

import hy.ea.bo.CAccount;
import hy.ea.bo.CDetail;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.BenDis.DtOrderBillAdd;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.ea.production.service.WarehouseService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.WfjJifenDetail;
/**
 *  金币流水
 */
public class UserGoldWaterReportAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private WarehouseService warehouseService;
	private InputStream excelStream;
	private CashierBills cashierBills;
	private List<BaseBean> goodBillslist;
	private PageForm pageForm;
	private int pageNumber;
	private String userId;
	private String result;
	private String type;
	/*
	 * 获取主页面信息
	 */
	public String getHomePageInformation() throws ParseException{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		List<Object> obj=new ArrayList<Object>();
		String sql1="select wd.Jifen_Detail_Name,wd.Jifen_Detail_Date,cast(wg.Wfj_Guize_Calc as varchar(2))" +
				" Wfj_Guize_Calc,wd.Jifen_Detail_Score,wg.Wfj_Guize_Name,wd.Jifen_Detail_Id";
		String sql2=" from Dt_Wfj_Jifen wj left join Dt_Wfj_Jifen_Detail wd" +
						 " on wj.wfj_jifen_id = wd.wfj_jifen_id left join Dt_Wfj_Guize wg" +
						 " on wd.wfj_guize_id = wg.wfj_guize_id where wj.staff_id=? and wj.wfj_jifen_state=?";
		obj.add(userId!=null&&!"".equals(userId)?userId:account.getStaffID());obj.add("0");
		if("like".equals(type)){
			String sdate=re.getParameter("sdate");
			String edate=re.getParameter("edate");
			String category=re.getParameter("category");
			if(!"".equals(sdate)&&sdate!=null){
				sql2+=" and wd.jifen_detail_date>=?";
				obj.add(new SimpleDateFormat("yyyyMMdd").parse(sdate));
				String balance=getBeforeTheBalanceOfQuantity(
						new SimpleDateFormat("yyyyMMdd").parse(sdate),category,
						userId!=null&&!"".equals(userId)?userId:account.getStaffID());
				re.setAttribute("balance", balance);
				StringBuilder str=new StringBuilder(sdate);
				if('1'==sdate.charAt(7)&&'0'==sdate.charAt(6)){
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.DAY_OF_MONTH, 1); 
					calendar.add(Calendar.DATE, -1);
					str=new StringBuilder(new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));
				}else{
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(new SimpleDateFormat("yyyyMMdd").parse(sdate));
					calendar.add(Calendar.DATE, -1);
					str=new StringBuilder(new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));
				}
				str.insert(4,'-');
				str.insert(7,'-');
				re.setAttribute("sdate",str);
			}
			if(!"".equals(edate)&&edate!=null){
				sql2+=" and wd.jifen_detail_date<=?";
				obj.add(new SimpleDateFormat("yyyyMMdd").parse(edate));
			}
			if(!"".equals(category)&&category!=null){
				sql2+=" and wg.Wfj_Guize_Name like ?";
				obj.add("%"+category+"%");
			}
		}else{
			Calendar calendar = Calendar.getInstance();   
			calendar.add(Calendar.MONTH, 0);
			calendar.set(Calendar.DAY_OF_MONTH,1);
		    Calendar cale = Calendar.getInstance();   
	        cale.set(Calendar.DAY_OF_MONTH,0);
			String balance=getBeforeTheBalanceOfQuantity(calendar.getTime(),null,
					userId!=null&&!"".equals(userId)?userId:account.getStaffID());
			re.setAttribute("balance", balance);
			re.setAttribute("sdate", new SimpleDateFormat("yyyy-MM-dd").format(cale.getTime()));
			sql2+=" and wd.jifen_detail_date>=?";
			obj.add(calendar.getTime());
		}
		sql2+=" order by wd.jifen_detail_date";
		pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql1+sql2, "select count(*) "+sql2, obj.toArray());
		return "pageData";
	}
	/*
	 * 获取开始之前的总结余数量
	 */
	public String getBeforeTheBalanceOfQuantity(Date sdate,String category,String staffid){
		List<Object> params =new ArrayList<Object>();
		String sql="select case when i.income is null then 0 else i.income end - case when" +
				" e.expenditure is null then 0 else e.expenditure end Balance from" +
				" (select sum(cast(wd.jifen_detail_score as int)) income from Dt_Wfj_Jifen wf" +
				" left join Dt_Wfj_Jifen_Detail wd on wf.wfj_jifen_id =wd.wfj_jifen_id" +
				" left join Dt_Wfj_Guize wg on wd.wfj_guize_id = wg.wfj_guize_id where" +
				" wf.staff_id = ? and wf.wfj_jifen_state = ? and wg.wfj_guize_calc = 'A' and" +
				" wd.jifen_detail_date < ?";
		params.add(staffid);params.add(0);params.add(sdate);
		if(!"".equals(category)&&category!=null){
			sql+=" and wg.Wfj_Guize_Name like ?";
			params.add("%"+category+"%");
		}
		sql+=") i, (select sum(cast(wd.jifen_detail_score as int))" +
				" expenditure from Dt_Wfj_Jifen wf left join Dt_Wfj_Jifen_Detail wd on" +
				" wf.wfj_jifen_id = wd.wfj_jifen_id left join Dt_Wfj_Guize wg on" +
				" wd.wfj_guize_id = wg.wfj_guize_id where wf.staff_id = ? and " +
				"wf.wfj_jifen_state = ? and wg.wfj_guize_calc = 'B' and wd.jifen_detail_date < ?";
		params.add(staffid);params.add(0);params.add(sdate);
		if(!"".equals(category)&&category!=null){
			sql+=" and wg.Wfj_Guize_Name like ?";
			params.add("%"+category+"%");
		}
		sql+=") e";
		BigDecimal balance=(BigDecimal)baseBeanService.getObjectBySqlAndParams(sql, params.toArray());
		return balance.toString();
	}
	
	/**
	 * 导出EXCEL表格
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	public String exportExcelTable() throws UnsupportedEncodingException, ParseException{
		HttpServletRequest re=ServletActionContext.getRequest();
		String title=re.getParameter("title");
		String[] str=re.getParameter("str").split(",");
		String json=re.getParameter("json");
		JSONArray jsonArray = JSONArray.fromObject(json);  
        List<List<Object>> list = (List<List<Object>>) JSONArray.toCollection(jsonArray,  
        		Object.class);
        List<Object> obj=new ArrayList<Object>();
        for(int i=0;i<list.size();i++){
        	List<Object> l=list.get(i);
        	Object[] o=new Object[l.size()];
        	for(int r=0;r<l.size();r++){
        		o[r]=l.get(r);
        	}
        	obj.add(o);
        }
		excelStream=warehouseService.OutOrderExcel(title, str, obj);
		return "showexcel";
	}
	/*
	 * ajax获取单据ID
	 */
	public String ajaxGetBillID(){
		HttpServletRequest re=ServletActionContext.getRequest();
		String hql="from WfjJifenDetail where jifenDetailId=?";
		String id=re.getParameter("jifenId");
		WfjJifenDetail wfj=(WfjJifenDetail)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{id});
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("wfj",wfj);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}
	public String getBillDInformation(){
		HttpServletRequest request=ServletActionContext.getRequest();
			String goodsID=request.getParameter("goodsId");
			String goodsHql="from GoodsBills where goodsBillsID=?";
			GoodsBills goods=(GoodsBills)baseBeanService.getBeanByHqlAndParams(goodsHql, new Object[]{goodsID});
			// 项目预算单
			String hqlys = "from CashierBills where cashierBillsID = ?";
			cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(
					hqlys, new Object[] { goods.getCashierBillsID() });
			
			DtOrderBillAdd orderBillAdd= (DtOrderBillAdd) baseBeanService.getBeanByHqlAndParams(
					"from DtOrderBillAdd where oaBillJounum = ?", new Object[] { cashierBills.getjNumOrder() });
			TEshopCusCom cusCom=(TEshopCusCom)baseBeanService.getBeanByHqlAndParams(
					"from TEshopCusCom where sccId = ?", new Object[]{orderBillAdd.getOaSccId()});
			request.setAttribute("wfjzh", cusCom);
//			//收货地址
//			StaffAddress address= (StaffAddress) baseBeanService.getBeanByHqlAndParams(
//					"from StaffAddress where addressID = ?", new Object[] { orderBillAdd.getOaAddShId()});
			request.setAttribute("sh", orderBillAdd);
//			//发货地址
//			CDetail detail= (CDetail) baseBeanService.getBeanByHqlAndParams(
//					"from CDetail where companyID = ?", new Object[] { orderBillAdd.getOaAddFhId()});
//			request.setAttribute("fh", detail);
			//收货方信息
			Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{cusCom.getStaffid()});
			request.setAttribute("shr", staff);
			//发货方信息
			if (cashierBills != null) {
				// 项目预算单中的物品
				String hqlg = "from GoodsBills where cashierBillsID = ?";
				goodBillslist = baseBeanService.getListBeanByHqlAndParams(hqlg,
						new Object[] { cashierBills.getCashierBillsID() });

			}
			return "edit";
	}
	
	/*
	 * 获取下级代理
	 */
	@SuppressWarnings("unchecked")
	public String getLowerMemberType(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		if("ajax".equals(type)){
			String cusType=re.getParameter("cusType");
			String sql="select s.staffid, s.staffname from (select staffid from T_ESHOP_CUSCOM" +
					" where cusType =?  connect by Superioragent = prior account start with" +
					" staffid = ?) c left join dt_hr_Staff s on c.staffid = s.staffid where c.staffid is not null";
			List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{
						cusType,account.getStaffID()});
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("list",list);
			JSONObject js=JSONObject.fromObject(map);
			result=js.toString();
			return "success";
		}else{
			String hql="from TEshopCusCom where staffid=?";
			TEshopCusCom te=(TEshopCusCom)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getStaffID()});
			if(te!=null){
				String codeHql="from CCode where codePID=? and companyID=? and codeNumber>? order by codeNumber";
				List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(codeHql, new Object[]{"scode20150911hvgnyqbv8g0000000004",
						account.getCompanyID(),Integer.parseInt(te.getCusType())});
				re.setAttribute("list",list);
			}else{
				re.setAttribute("list",null);
			}
			return "member";
		}
	}
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public void setGoodBillslist(List<BaseBean> goodBillslist) {
		this.goodBillslist = goodBillslist;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
