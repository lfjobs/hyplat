package hy.ea.invoicing.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.invoicing.cashTime;
import hy.ea.service.CLogBookService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;
/**
 * 预算招标金额初始化
 * @author 陈婷
 *
 */
public class cashtimeAction {
	
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private String result;
	private String stime;
	private String etime;
	private String num;
	private String parameter;
	private String cashid;
	public cashTime cashtime;
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private String qb;//00 部门 01 公司 02集团
	private List<Object> lists;  
	private String nian;//01 按年查询 10 按月查询
	private String names;//集团传来的公司名称
	private String level;//集团的公司Id
	
	/**
	 * 页面信息并且模糊查询
	 */
	public String toSearch(){
		ActionContext.getContext().getSession().put("tablesearch", cashtime);
		return getcashList();
	}
	public String getcashList(){
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), sql, "select count(*) "+ sql.substring(sql.indexOf("from")),parms);
		return "topage";
	}
	private List<Object> getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		List<Object> parms = new ArrayList<Object>();
		List<Object> result = new ArrayList<Object>();
		String hql="from cashTime where companyID=? ";
		parms.add(companyID);
		if (search != null && search.equals("search")) {
			cashtime =  (cashTime) session.get("tablesearch");
			if(stime !=null && !"".equals(stime)){
				hql+=" and stime =?";
				parms.add(Utilities.getDateFromString(stime+"-01", "yyyy-MM-dd"));
			}
			if(cashtime.getNum()!=null && !"".equals(cashtime.getNum().trim())){
				hql+=" and num =?";
				parms.add(cashtime.getNum());
			}
		}
		result.add(hql);
		result.add(parms.toArray());
		return result;
	}
	
	/**
	 * 财务初始化保存开启时间 
	 * @return 
	 */
	public String getFunction(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		List<BaseBean> beans=new ArrayList<BaseBean>();
		Company company=(Company)baseBeanService.getBeanByHqlAndParams("from Company where companyID=?", new Object[]{account.getCompanyID()});
		cashTime cashTime=new cashTime();
		cashTime.setCashID(serverService.getServerID("cash"));
		cashTime.setCompanyID(account.getCompanyID());
		cashTime.setCompanyName(company.getCompanyName());
		cashTime.setStime(Utilities.getDateFromString(stime, "yyyy-MM"));
		cashTime.setNum(num);
		beans.add(cashTime);
		parameter = "预算收入金额为"+num;
		CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
		beans.add(logBook);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		return "success";
	}
	
	/**
	 * 判断是否财务初始化
	 * @return
	 */
	public String ajaxFunction(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		String hql="select count(*) from cashTime where companyID=? and stime=?";
		int count=baseBeanService.getConutByByHqlAndParams(hql, new Object[]{account.getCompanyID(),Utilities.getDateFromString(stime, "yyyy-MM")});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}
	
	/**
	 * 修改预算金额
	 * @return
	 */
	public String updatelist(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");	
		List<Object[]> list=new ArrayList<Object[]>();
		List<BaseBean> listbabe=new ArrayList<BaseBean>(); 
		parameter = "修改预算收入金额日期为"+stime;
		CLogBook cLogBook = logBookService.saveCLogBook(null, parameter, account);
		String hql="update cashTime set num=? where companyID=? and  cashID=?";
		String[] hqls={hql};
		list.add(new Object[]{num, account.getCompanyID(),cashid});
		listbabe.add(cLogBook);
		baseBeanService.executeHqlsByParamsList(listbabe,hqls,list);
		return "success";
	}
	/**
	 * 删除预算金额
	 * @return
	 */
	public String deletlist(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");	
		parameter = "删除预算收入金额日期为"+stime;
		CLogBook cLogBook = logBookService.saveCLogBook(null, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		String hql="delete cashTime a where a.companyID=? and cashID=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,new String[] { hql }, new Object[] { account.getCompanyID(),cashid});
		return "success";
	}
	
	/**
	 * 封装sql 用于查询集团的月报表，以及年报表
	 */
	public  List<Object> getsuccList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) session.get("organizationID");
		List<Object> parms = new ArrayList<Object>();
		List<Object> result = new ArrayList<Object>();
		String sql="select sum(dt.forLoan),sum(dt.loan) from goods_cashierVo dt where 1=1";
		if (qb.equals("00")){//部门
			sql+=" and dt.companyid=? ";
			parms.add(companyID);
			sql+=" and dt.organizationID=? ";
			parms.add(organizationID);
			sql+=" and dt.cashierStatus=? ";
			parms.add("04");
		}else if(qb.equals("01")){//公司
			sql+=" and dt.companyid=? ";
			parms.add(companyID);
			sql+=" and dt.cashierStatus=? ";
			parms.add("04");
		}else{//用于集团
			sql+=" and dt.companyid=? ";
			parms.add(level);
			sql+=" and dt.cashierStatus=? ";
			parms.add("04");
		}
		sql+="and dt.billstype in (select  co.codevalue　from dtccode co where co.companyid=? and co.codepid=? )";
		if(qb.equals("02")){
			parms.add(level);
		}else{
			parms.add(companyID);
		}
		parms.add("scode20130104uyj3s8t4b50000000002");
		sql+=" and dt.cashierDate between ? and ?";
		parms.add(Utilities.getDateFromString(stime+"-01", "yyyy-MM-dd"));
		int tt=0;
		if(nian.equals("01")){
			tt+=Utilities.getDayMouth(Integer.parseInt(stime.substring(0, 4)),12);
			stime=stime.substring(0,4)+"-12";
		}else{
			tt+=Utilities.getDayMouth(Integer.parseInt(stime.substring(0, 4)),Integer.parseInt(stime.substring(5, 7)));
		}
		parms.add(Utilities.getDateFromString(stime+"-"+String.valueOf(tt), "yyyy-MM-dd"));
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	/**
	 * 利用递归法查询每个月的使用的金额
	 */
	@SuppressWarnings("unchecked")
	private void shijian(int xiao,int da,List<Object> lists){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		Object[] objs=new Object[5];
		DecimalFormat df = new DecimalFormat("0.000"); 
		if(xiao<=da){
			//************************************************预算后计算**********************************************************
			if(nian.equals("01")){
				stime=String.valueOf(xiao).substring(0,4)+"-01";
			}else{
				stime=String.valueOf(xiao).substring(0,4)+"-"+String.valueOf(xiao).substring(4,6);
			}
			List<Object> list = getsuccList();
			String sql = (String) list.get(0);
			Object[] parms = (Object[]) list.get(1);
			List<Object> sumlist = baseBeanService.getListBeanBySqlAndParams(sql,parms);
			double cc=0.0;//收入后
			double ss=0.0;//收入前
			String gs="";//公司
			double ee=0.0;//成功率
			if(sumlist !=null && sumlist.size()>0){
			for(int i=0;i<sumlist.size();i++){
		        Object[] obj = (Object[])sumlist.get(i);
		            for(int j = 0 ; j < obj.length; j ++){
	                    if((obj[j])!=null){
	                         cc+=Double.parseDouble(obj[j].toString());
	                    }else{
	                        cc+=0.0;
	                    }
	                }
            }
			}
			//************************************************预算前计算**********************************************************
			String sql1="";
			String companyids="";
			if(qb.equals("02")){
				companyids=level;
			}else{
				companyids=account.getCompanyID();
			}
			if(nian.equals("01")){
				//************************************************年预算前计算**********************************************************
				sql1+="select sum(num),companyname from dt_cashtime yu where yu.companyid=? and yu.stime between ? and ?  group by companyname";	
				
				List<Object> sumlists  =  baseBeanService.getListBeanBySqlAndParams(sql1,new Object[]{companyids,Utilities.getDateFromString((stime.substring(0,4)+"-01-01"), "yyyy-MM-dd"),Utilities.getDateFromString((stime+"-01"), "yyyy-MM-dd")});
				if(sumlists !=null && sumlists.size()>0){
					for(int i=0;i<sumlists.size();i++){
				        Object[] objes = (Object[])sumlists.get(i);
				            for(int j = 0 ; j < objes.length; j ++){
				            	if(j==0){
				            		 if((objes[j])!=null){
				                         ss+=Double.parseDouble(objes[j].toString());
				                    }else{
				                        ss+=0.0;
				                    }
				                 }else if(j==1){
				                	 if((objes[j])!=null){
				                		 gs=objes[j].toString();
				                    }else{
				                    	if(qb.equals("02")){
				                    		gs=names;
				                    	}else{
				                    	gs=account.getCompanyName();
				                    	}
				                    }
				                }
			                }
		            }
					}else{
						if(qb.equals("02")){
							gs=names;
                    	}else{
						gs=account.getCompanyName();
                    	}
					}
			}else{
				//************************************************月预算前计算**********************************************************
				 sql1+="from cashTime yu where yu.companyID=? and yu.stime=? order by yu.stime";	
				 cashTime yulist = (cashTime) baseBeanService.getBeanByHqlAndParams(sql1,new Object[]{companyids,Utilities.getDateFromString(stime+"-01", "yyyy-MM-dd")});
				 if(yulist !=null){
						if(yulist.getNum() !=null && !yulist.getNum().equals("")){
							ss+=Double.parseDouble(yulist.getNum());
						}else{
							ss+=0.0;
						}
						if(yulist.getCompanyName() !=null && !yulist.getCompanyName().equals("")){
							gs=yulist.getCompanyName();
						}
					}else{
						if(qb.equals("02")){
							gs=names;
                    	}else{
						gs=account.getCompanyName();
                    	}
					}
			}
			if(cc !=0.0 && ss!=0.0 ){
				ee=(Math.round((cc/ss*100)*100)/100.0);
			}else{
				ee=0.0;
			}
			objs[0]=gs;
			objs[1]=String.valueOf(xiao);
			objs[2]=df.format(ss);
			objs[3]=df.format(cc);
			objs[4]=ee+"%";
			xiao+=1;
			lists.add(objs);
			shijian(xiao,da,lists);
		}
	}
	/**
	 * 部门和公司以及集团的月完成率报表和年完成率报表
	 * @return
	 */
	public String completeList(){
		lists=new ArrayList<Object>();
		shijian(Integer.parseInt(stime.replaceAll("-","")),Integer.parseInt(etime.replaceAll("-","")),lists);
		return "complete";
	}
	public String tosa(){
		return "completes";
	}
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCashid() {
		return cashid;
	}

	public void setCashid(String cashid) {
		this.cashid = cashid;
	}
	public cashTime getCashtime() {
		return cashtime;
	}
	public void setCashtime(cashTime cashtime) {
		this.cashtime = cashtime;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
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
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getQb() {
		return qb;
	}
	public void setQb(String qb) {
		this.qb = qb;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public List<Object> getLists() {
		return lists;
	}
	public void setLists(List<Object> lists) {
		this.lists = lists;
	}
	public String getNian() {
		return nian;
	}
	public void setNian(String nian) {
		this.nian = nian;
	}
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
}
