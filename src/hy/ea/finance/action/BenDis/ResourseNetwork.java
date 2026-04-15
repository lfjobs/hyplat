package hy.ea.finance.action.BenDis;



import hy.ea.bo.finance.BenDis.JoinFans;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.util.SessionWrap;

/**
 * 商城人脈財源
 * 
 * @author zzl
 * 
 */
@Controller
@Scope("prototype")
public class ResourseNetwork {

	@Resource
	private BaseBeanService baseBeanService;
	
	private String result;//ajax返回的字段
    private List<BaseBean> list;
	private String type;// 状态01个人02公司
	private String user;// 用户
	private String types;// 用户类型
	private String cusType;//判断用户是什么级别的
	private Map<String, Object> obj;

	private String parameter;
	private String chooseCusType;//选择进入的是哪个会员级别
	private PageForm pageForm;
	private int pageNumber;
	private int pageSize;
	
	private JoinFans joinFans;
    private String stype;
	
	private Object isNull(Object tep) {
		if (tep == null) {
			return "";
		} else {
			return tep;
		}
	}


	/**
	 * 
	 * 
	 * 获取会员各级别
	 * @return
	 */
	public String findconWealth() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		if(cus==null){
			if(user!=null){
			    cus  = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0", new Object[]{user});
			}else{
				return "login";
			}
		}
		String hql = "from TEshopCusCom where account=? AND logOff=0";
		TEshopCusCom cucom = (TEshopCusCom) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { cus.getAccount() });
	
		Map<String, Object> sess = ActionContext.getContext().getSession();
		sess.put("type", cucom);
		sess.put("account", cus);
		return "conwealth";
	}
	
	
	/**
	 * 
	 * 获取每一种级别的会员 
	 * @return
	 */
	 @SuppressWarnings("unchecked")
	public String show(){

			HttpSession session = ServletActionContext.getRequest().getSession();
			SessionWrap sw = SessionWrap.getInstance();
			TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
					SessionWrap.KEY_CUSTOMER);
			if(cus==null){
				return "login";
			}
			
		    HttpServletRequest request = ServletActionContext.getRequest();
	        String scroll = request .getParameter("scroll");

			//chooseCusType前台传过来，进入的是哪个会员级别
			//type 前台传过来，代表个人，还是公司
			
			String sql = "";
			List<String> params = new ArrayList<String>();
			

			if(chooseCusType.equals("6")){
				if(type.equals("1")){
					chooseCusType="7";
				}else{
					type ="1";
				}
			}
			if (type.equals("1")) {// 个人
				if(!chooseCusType.equals("8")){
				sql = "select s.staffname,t.cusType,s.headimage,t.account,s.staffPost from dt_hr_Staff s,T_ESHOP_CUSCOM t "
						+ "where t.staffid = s.staffid and  "
						+ "t.cusType = ? and state = ? and s.staffid in (select t.staffid from T_ESHOP_CUSCOM t where  t.cusType = ?  connect by nocycle prior t.account = t.Superioragent start with t.account = ?)";
				      params.add(chooseCusType);
				      params.add("1");
				      params.add(chooseCusType);  
				      
				      
				      
				 } else if(chooseCusType.equals("8")){//粉丝
		    	   sql = "select s.staffname,t.cusType,s.headimage,f.faccount,s.staffPost from dt_hr_Staff s,T_ESHOP_CUSCOM t,dtJoinFans f "
							+ "where s.staffid = t.staffid and t.account = f.faccount and t.state = ? and f.zaccount = ? ";
		    	   params.add("1");
 
		        }
				 params.add(cus.getAccount());  
				
				if(parameter!=null&&!parameter.equals("")){
					
					sql+=" and (t.account like ? or s.staffname like ? or s.staffPost like ?) ";
					  params.add("%"+parameter+"%");
					  params.add("%"+parameter+"%");
					  params.add("%"+parameter+"%");
				}
				
				
				

			} else if (type.equals("2")) {// 公司的
				if(!chooseCusType.equals("8")){
				   sql = "select s.companyname,t.cusType,cc.logoPath,t.account,cc.industryType from dtCompany s,T_ESHOP_CUSCOM t,"
						+ "dt_ccom_com r,dtContactCompany cc "
						+ "where t.companyid = s.companyid "
						+ "and s.companyid=r.compnay_id "
						+ "and r.ccompany_id=cc.ccompanyid "
						+ " and t.cusType = ? and t.state = ? and s.companyid in (select t.companyid from T_ESHOP_CUSCOM t where t.cusType = ?  "
						+ "connect by nocycle prior t.account = t.Superioragent start with t.account = ?)";
				      params.add(chooseCusType);
					  params.add("2");
					  params.add(chooseCusType);  
				}else if(chooseCusType.equals("8")){//粉丝
					   sql = "select s.companyname,t.cusType,cc.logoPath,f.faccount,cc.industryType from dtCompany s,T_ESHOP_CUSCOM t,"
								+ "dt_ccom_com r,dtContactCompany cc,dtJoinFans f "
								+ "where t.companyid = s.companyid "
								+ "and s.companyid=r.compnay_id "
								+ "and r.ccompany_id=cc.ccompanyid "
								+ "and t.account=f.faccount "
								+ " and t.state = ? and f.zaccount = ?";
					   params.add("2");   

			       }
				       
				        params.add(cus.getAccount());  
				        
				        if(parameter!=null&&!parameter.equals("")){
							
							sql+=" and (t.account like ? or s.companyname like ? or cc.industryType like ?) ";
							  params.add("%"+parameter+"%");
							  params.add("%"+parameter+"%");
							  params.add("%"+parameter+"%");
						}
			    }
			   
			   if(chooseCusType.equals("6")){   
					
					type ="2";

			   }else if(chooseCusType.equals("7")){
				   
				   chooseCusType="6";
			   }
			   
			    
			String sqlcount = sql.replace(sql.substring(0,sql.indexOf("from")),"select count(*) ");
			
	        pageForm = baseBeanService.getPageFormBySQL((pageForm!=null?pageForm.getPageNumber():1),18,sql, sqlcount, params.toArray());
	       
	       
	        if(scroll!=null&&!scroll.equals("")){
		    Map<String,Object> map = new HashMap<String,Object>();
		    map.put("pageForm", pageForm);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
	        }
				
				
			return "show";
	 }    
	   /**
	    * 关注资源页面
	    * @return
	    */
	   @SuppressWarnings("unchecked")
	public String attresources(){
		      List<Object> params=new ArrayList<Object>();
			  String sql="  from DTJOINFANS j " +
			  		" join t_eshop_cuscom c on j.faccount= c.account" +
			  		" join dt_hr_Staff s on c.staffid=s.staffid" +
			  		" where  j.zaccount= ?";

			  params.add(user);
			  

			  sql+=" order by s.staffName";
	 		  pageForm= baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 8 : pageNumber),"select s.staffName,s.headimage"+sql, "select count(*)"+sql, params.toArray());
	 		 Map<String,Object> map = new HashMap<String,Object>();
	 		  if(stype.equals("ajax")){
					map.put("pageForm", pageForm);
					JSONObject obj = JSONObject.fromObject(map);
					this.result = obj.toString();
					return "success";
				}else{
			    
		   return "attresources";
				} 
	     }
     /**
      * 好友名片
      * @return
      */
	   @SuppressWarnings("unchecked")
	public String att_1(){
		  List<Object> params=new ArrayList<Object>();
		  String sql="  from DTJOINFANS j " +
		  		" join t_eshop_cuscom c on j.faccount= c.account" +
		  		" join dt_hr_Staff s on c.staffid=s.staffid" +
		  		" where  j.zaccount= ?";

		  params.add(joinFans.getZaccount());
		  if(type.equals("search")){
			  sql+=" and s.staffName like ?";
			  params.add("%"+result+"%");
		  }
		  

		  sql+=" order by s.staffName";
 		  pageForm= baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 2 : pageNumber),"select s.staffName,s.headimage"+sql, "select count(*)"+sql, params.toArray());
		  Map<String,Object> map = new HashMap<String,Object>();
		    
			if(stype.equals("ajax")){
				map.put("pageForm", pageForm);
				JSONObject obj = JSONObject.fromObject(map);
				this.result = obj.toString();
				return "success";
			}else{
		  
		   return "att_1";
			}                                                                                                                                                                                                                                                                                                                                                                                                                                         
	   }
	   
		   

		/**
		 * 获取公司名片 
		 * @return
		 */
	   @SuppressWarnings("unchecked")
	public String att_2(){
				   List<Object> params=new ArrayList<Object>();
				   String sql=" from DTJOINFANS j" +
				   		" join t_eshop_cuscom c on j.faccount= c.account" +
				   		" join DT_ccom_com cc on c.companyid=cc.compnay_id" +
				   		" join dtContactCompany d on cc.ccompany_id=d.ccompanyid " +
				   		" where j.zaccount = ? and c.state = ?";
				   params.add(joinFans.getZaccount());
				   params.add("2");
				   if(type.equals("search")){
						  sql+=" and d.companyname like ?";
						  params.add("%"+result+"%");
					  }
				   sql+=" order by d.companyname";
				   pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 2 : pageNumber), "select d.companyname,d.logoPath"+sql, "select  count(*)"+sql, params.toArray());
				   Map<String,Object> map = new HashMap<String,Object>();
				    
					if(stype.equals("ajax")){
						map.put("pageForm", pageForm);
						JSONObject obj = JSONObject.fromObject(map);
						this.result = obj.toString();
						return "success";
					}else{
				   return "att_2";
					}
			   }
	   /**
	    * 商务园区
	    * @return
	    */
		 @SuppressWarnings("unchecked")
		public String att_3(){
			 String sql=" from DTJOINFANS j" +
				   		" join t_eshop_cuscom c on j.faccount= c.account" +
				   		" join DT_ccom_com cc on c.companyid=cc.compnay_id" +
				   		" join dtContactCompany d on cc.ccompany_id=d.ccompanyid " +
				   		" where j.zaccount = ? and c.state = ? and cusType = ? ";
			  List<Object> params=new ArrayList<Object>();
			  params.add(joinFans.getZaccount());
			  params.add("2");
			  params.add("0");
			  if(type.equals("search")){
				  sql+=" and pseudo_Company_Name like ?";
				  params.add("%"+result+"%");
			  }
			  pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 2 : pageNumber), "select d.companyname,d.logoPath"+sql, "select  count(*)"+sql, params.toArray());
					   Map<String,Object> map = new HashMap<String,Object>();
					    
						if(stype.equals("ajax")){
							map.put("pageForm", pageForm);
							JSONObject obj = JSONObject.fromObject(map);
							this.result = obj.toString();
							return "success";
						}else{
			   
			   
			   return "att_3";
						}
			   
		 }
		 /**
		  * 城乡社区
		  * @return
		  */
		 public String att_4(){
				
			   
			   
			   return "att_4";
			   
		 }
		 
	    /**
	     * 
	     * 
	     * 汇总时获取会员列表
	     * @return
	     */
	    public String findVipList(){
	    	
	    	HttpSession session = ServletActionContext.getRequest().getSession();
			SessionWrap sw = SessionWrap.getInstance();
			TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
					SessionWrap.KEY_CUSTOMER);
			if(cus==null){
				return "login";
			}

			
			try {
				StringBuffer str=new StringBuffer();
				List<String> params = new ArrayList<String>();
				try {
					

					System.out.println(chooseCusType);
					//代理商级别以上
					if (((int)Integer.parseInt(chooseCusType))<=5) {
     
					str.append("with t as(");
					str.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state from T_ESHOP_CUSCOM t where t.cusType = ?");
					str.append(" connect by nocycle prior t.account = t.Superioragent");
					str.append(" start with t.account =?)");
					params.add(chooseCusType);
					params.add(cus.getAccount());
					
					str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost");
					str.append(" from t,dt_hr_staff staff");
					str.append(" where staff.staffid = t.staffid and t.state = '1'");
					
					str.append(" union all");
					str.append(" select t.account, t.cusType,company.companyname,cc.logopath,cc.industryType");
					str.append(" from t,dt_ccom_com r ,dtcompany company,dtContactCompany cc");
					str.append(" where t.companyid=company.companyid and company.companyid=r.compnay_id ");
					str.append(" and r.ccompany_id=cc.ccompanyid and t.state = '2'");

					}else if(chooseCusType.equals("6")){
						//查询VIP客户和客户
						str.append("with t as(");
						str.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state from T_ESHOP_CUSCOM t where (cusType = ? or cusType = ?)");
						
						str.append(" connect by nocycle prior t.account = t.Superioragent");
						str.append(" start with t.account =?)");
						params.add(chooseCusType);
						params.add(String.valueOf((int)Integer.parseInt(chooseCusType)+1));
						params.add(cus.getAccount());
						
						
						str.append(" select t.account,t.custype,staff.staffname, staff.headimage,staff.staffPost");
						str.append(" from t, dt_hr_staff staff");
						str.append(" where staff.staffid = t.staffid and t.state = '1'");

						
					}else if(chooseCusType.equals("8")){
					    //查询粉丝
				        //查询当前用户所有的默认等级的会员
						str.append("with t as(");
						str.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state from T_ESHOP_CUSCOM t,dtJoinFans f");
						str.append(" where f.faccount = t.account and f.zaccount = ?)");
						params.add(cus.getAccount());
						
						str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost");
						str.append(" from t,dt_hr_staff staff");
						str.append(" where staff.staffid = t.staffid and t.state = '1'");
						
						str.append(" union all");
						str.append(" select t.account, t.cusType,company.companyname,cc.logopath,cc.industryType");
						str.append(" from t,dt_ccom_com r ,dtcompany company,dtContactCompany cc");
						str.append(" where t.companyid=company.companyid and company.companyid=r.compnay_id ");
						str.append(" and r.ccompany_id=cc.ccompanyid and t.state = '2'");

					}
   
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				String sqlcount = "select count(*) from ("+str+")";
				pageForm = baseBeanService.getPageFormBySQL((pageForm!=null?pageForm.getPageNumber():1),18, str.toString(), sqlcount, params.toArray());
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("pageForm", pageForm);
				JSONObject jo = JSONObject.fromObject(map);
				this.result = jo.toString();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
	       

	    	return "success";
	    	
	    	
	    	
	    }

	 
	 
	 
		/**
		 * 根据当前会员查询下级及其会员数量，以及第一个默认展示列表
		 * @param user会员
		 * @param cusType 3:合伙人商城业主会员,4:微分金商城业主会员,5:代理商商城业主会员(6:vip客户,7客户),8:粉丝好友
		 * @return
		 */
		public String findVipNum() {
			HttpSession session = ServletActionContext.getRequest().getSession();
			SessionWrap sw = SessionWrap.getInstance();
			TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
					SessionWrap.KEY_CUSTOMER);
			if(cus==null){
				return "login";
			}
			//用户信息
			String hql ="select new TEshopCusCom(s,m.cusType,m.account,m.sccId) from TEshopCusCom m,Staff s where m.staffid=s.staffID and m.account=?";
			TEshopCusCom tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cus.getAccount()});
			
			
			
			List<List<String>> listt = new ArrayList<List<String>>();
			try {
				List<String> params = new ArrayList<String>();
				params.add(cus.getAccount());

				String sql = "select t.account,t.cusType,t.staffid from T_ESHOP_CUSCOM t  connect by nocycle prior t.account = t.Superioragent start with t.account = ?";
				List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql,params.toArray());
		
				
				String fanshql = "from JoinFans f where f.zaccount = ?";
				List<BaseBean> fanslist = baseBeanService.getListBeanByHqlAndParams(fanshql,params.toArray());
				
				int a = 0;
				int b = 0;
				int c = 0;
				int d = 0;
				int e= 0;//客户+VIP客户
				int f= 0;//粉丝
				
				//2-7会员个数
				for (int i = 0; i < list.size(); i++) {

					Object[] obj = (Object[]) (Object) list.get(i);

					if (obj[1].equals("2")) {
						a++;
					}
					if (obj[1].equals("3")) {
						b++;
					}
					if (obj[1].equals("4")) {
						c++;
					}
					if (obj[1].equals("5")) {
						d++;
					}
					if (obj[1].equals("6")||obj[1].equals("7")) {
						e++;
					}

				}
				//粉丝个数
				f=fanslist.size();
				
				
				//根据当前用户等级统计类型和会员等级
				cusType = tcc.getCusType();
//				cusType="2";
				List<String> temp = new ArrayList<String>();
				
				  
				 if (cusType.equals("6")||cusType.equals("7")) {
					//等级会员VIP客户或者普通客户 只显示粉丝
						temp = new ArrayList<String>();
						temp.add("粉丝名片");
						temp.add(f+"");//粉丝数
						temp.add("8");//临时当做8
						listt.add(temp);

					
				 } if (cusType.equals("5")) {
                    //等级会员是代理商 显示 客户(普通客户+VIP客户)，粉丝
					temp= new ArrayList<String>();
					temp.add("客户");
					temp.add(e+ "");
					temp.add("6");
					listt.add(temp);
					
					temp = new ArrayList<String>();
					temp.add("粉丝名片");
					temp.add(f+"");//粉丝数
					temp.add("8");//临时当做8
					listt.add(temp);


				}else if (cusType.equals("4")) {
					//等级会员是微分金显示 代理商商城业主会员 、客户(普通客户+VIP客户)，粉丝
					temp= new ArrayList<String>();
					temp.add("代理商会员");
					temp.add(d + "");
					temp.add("5");
					listt.add(temp);

					temp= new ArrayList<String>();
					temp.add("客户");
					temp.add(e+ "");
					temp.add("6");
					listt.add(temp);
					
					temp = new ArrayList<String>();
					temp.add("粉丝名片");
					temp.add(f+"");//粉丝数
					temp.add("8");//临时当做8
					listt.add(temp);
					

				}else if (cusType.equals("3")) {
					//等级会员是合伙创业 显示微分金、 代理商商城业主会员 、客户(普通客户+VIP客户)，粉丝
					temp= new ArrayList<String>();
					temp.add("微分金会员");
					temp.add(c + "");
					temp.add("4");
					listt.add(temp);
					
					
					temp= new ArrayList<String>();
					temp.add("代理商会员");
					temp.add(d + "");
					temp.add("5");
					listt.add(temp);

					temp= new ArrayList<String>();
					temp.add("客户");
					temp.add(e+ "");
					temp.add("6");
					listt.add(temp);
					
					temp = new ArrayList<String>();
					temp.add("粉丝名片");
					temp.add(f+"");//粉丝数
					temp.add("8");//临时当做8
					listt.add(temp);
					

				}else if (cusType.equals("2")) {
					//等级会员是公司会员 显示合伙创业商城业主会员微分金、 代理商商城业主会员 、客户(普通客户+VIP客户)，粉丝
					temp = new ArrayList<String>();
					temp.add("合伙创业会员");
					temp.add(b + "");
					temp.add("3");
					listt.add(temp);
					
					
					temp= new ArrayList<String>();
					temp.add("微分金会员");
					temp.add(c + "");
					temp.add("4");
					listt.add(temp);
					
					
					temp= new ArrayList<String>();
					temp.add("代理商会员");
					temp.add(d + "");
					temp.add("5");
					listt.add(temp);

					temp= new ArrayList<String>();
					temp.add("客户");
					temp.add(e+ "");
					temp.add("6");
					listt.add(temp);
					
					temp = new ArrayList<String>();
					temp.add("粉丝名片");
					temp.add(f+"");//粉丝数
					temp.add("8");//临时当做8
					listt.add(temp);

				}else if (cusType.equals("0")) {
					//等级会员是税务 显示公司 合伙创业商城业主会员微分金、 代理商商城业主会员 、客户(普通客户+VIP客户)，粉丝
					temp = new ArrayList<String>();
					temp.add("公司会员");
					temp.add(a+ "");
					temp.add("2");
					listt.add(temp);
					
					
					temp = new ArrayList<String>();
					temp.add("合伙创业会员");
					temp.add(b + "");
					temp.add("3");
					listt.add(temp);
					
					
					temp= new ArrayList<String>();
					temp.add("微分金会员");
					temp.add(c + "");
					temp.add("4");
					listt.add(temp);
					
					
					temp= new ArrayList<String>();
					temp.add("代理商会员");
					temp.add(d + "");
					temp.add("5");
					listt.add(temp);

					temp= new ArrayList<String>();
					temp.add("客户");
					temp.add(e+ "");
					temp.add("6");
					listt.add(temp);
					
					temp = new ArrayList<String>();
					temp.add("粉丝名片");
					temp.add(f+"");//粉丝数
					temp.add("8");//临时当做8
					listt.add(temp);

				}
				temp=null;
				
				//查询默认
				List<String> paramsdefault = new ArrayList<String>();
				int cusTypeint=Integer.parseInt(cusType);
				String defaultcusType ="";
				if(cusTypeint==0){
					defaultcusType = (cusTypeint+2) + "";
				}else{
					defaultcusType = (cusTypeint+1) + "";
				}
			 
                
				
				StringBuffer str=new StringBuffer();
				//代理商级别以上
				if (!defaultcusType.equals("8")&&!defaultcusType.equals("7")&&!defaultcusType.equals("6")) {
                //查询当前用户所有的默认等级的会员
				str.append("with t as(");
				str.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state from T_ESHOP_CUSCOM t where t.cusType = ?");
				str.append(" connect by nocycle prior t.account = t.Superioragent");
				str.append(" start with t.account =?)");
				paramsdefault.add(defaultcusType);
				paramsdefault.add(cus.getAccount());
				
				str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost");
				str.append(" from t,dt_hr_staff staff");
				str.append(" where staff.staffid = t.staffid and t.state = '1'");
				
				str.append(" union all");
				str.append(" select t.account, t.cusType,company.companyname,cc.logopath,cc.industryType");
				str.append(" from t,dt_ccom_com r ,dtcompany company,dtContactCompany cc");
				str.append(" where t.companyid=company.companyid and company.companyid=r.compnay_id ");
				str.append(" and r.ccompany_id=cc.ccompanyid and t.state = '2'");

				}else if(defaultcusType.equals("6")){
					//查询VIP客户和客户
					str.append("with t as(");
					str.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state from T_ESHOP_CUSCOM t where (cusType = ? or cusType = ?)");
					
					str.append(" connect by nocycle prior t.account = t.Superioragent");
					str.append(" start with t.account =?)");
					paramsdefault.add("6");
					paramsdefault.add("7");
					paramsdefault.add(cus.getAccount());
					
					
					str.append(" select t.account,t.custype,staff.staffname, staff.headimage,staff.staffPost");
					str.append(" from t, dt_hr_staff staff");
					str.append(" where staff.staffid = t.staffid and t.state = '1'");

					
				}else if(defaultcusType.equals("7")||defaultcusType.equals("8")){
				    //查询粉丝
	                //查询当前用户所有的默认等级的会员
					str.append("with t as(");
					str.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state from T_ESHOP_CUSCOM t,dtJoinFans f");
					str.append(" where f.faccount = t.account and f.zaccount = ?)");
					paramsdefault.add(cus.getAccount());
					
					str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost");
					str.append(" from t,dt_hr_staff staff");
					str.append(" where staff.staffid = t.staffid and t.state = '1'");
					
					str.append(" union all");
					str.append(" select t.account, t.cusType,company.companyname,cc.logopath,cc.industryType");
					str.append(" from t,dt_ccom_com r ,dtcompany company,dtContactCompany cc");
					str.append(" where t.companyid=company.companyid and company.companyid=r.compnay_id ");
					str.append(" and r.ccompany_id=cc.ccompanyid and t.state = '2'");

				}
				
				
				String sqlcount = "select count(*) from ("+str+")";
		        pageForm = baseBeanService.getPageFormBySQL((pageForm!=null?pageForm.getPageNumber():1),18, str.toString(), sqlcount, paramsdefault.toArray());

			

			} catch (Exception e) {
				e.printStackTrace();
			}
			HttpServletRequest  request = ServletActionContext.getRequest();
			request.setAttribute("countlist",listt);
			request.setAttribute("pageForm",pageForm);
			request.setAttribute("tcc",tcc);
			return "ttresource";
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public Map<String, Object> getObj() {
		return obj;
	}

	public void setObj(Map<String, Object> obj) {
		this.obj = obj;
	}

	public List<BaseBean> getList() {
		return list;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}
	public String getChooseCusType() {
		return chooseCusType;
	}
	public void setChooseCusType(String chooseCusType) {
		this.chooseCusType = chooseCusType;
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


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}



	public String getCusType() {
		return cusType;
	}


	public void setCusType(String cusType) {
		this.cusType = cusType;
	}


	public JoinFans getJoinFans() {
		return joinFans;
	}


	public void setJoinFans(JoinFans joinFans) {
		this.joinFans = joinFans;
	}


	public String getStype() {
		return stype;
	}


	public void setStype(String stype) {
		this.stype = stype;
	}
    
	
	
	
	 

}
