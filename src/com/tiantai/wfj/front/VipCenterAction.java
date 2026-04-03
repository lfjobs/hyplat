package com.tiantai.wfj.front;

import hy.ea.bo.CAccount;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.finance.BenDis.JoinFans;
import hy.ea.bo.human.Staff;
import hy.ea.util.Constant;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Request;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tiantai.wfj.bo.MarKeting;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.util.SessionWrap;




@Controller
@Scope("prototype")
public class VipCenterAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	
	private PageForm pageForm;
	private String cusType;
	private String user;
	private Object result;// AJAX使用
	private String sccid;
	private TEshopCusCom cuscom;
	private TEshopCustomer customer;
	private Staff staff;
	private ContactCompany contactCompany;
	private ProductPackaging productPackaging;
	private List<BaseBean> list;
	private Object object;
	private String staffid;
	private String type;
	private String qrcode;
	private Map<Integer, String[]> maplist1;
	private List<Object> list1;
	private String ret;
	public ServletRequest request = ServletActionContext.getRequest();
	
	private String flag;//标识
	private String khd; //0为网页查看  1为APP查看
	
	public String findVipAccout(){
		StringBuffer str=new StringBuffer();
		List<String> params = new ArrayList<String>();
		String sqlcount="select count(1) from ";
		//代理商级别以上
		if (((int)Integer.parseInt(cusType))<=5) {

		str.append("with t as(");
		str.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state,t.sccId from T_ESHOP_CUSCOM t where t.cusType = ?");
		str.append(" connect by nocycle prior t.sccId = t.supperSccId");
		str.append(" start with t.sccId =?)");
		params.add(cusType);
		params.add(sccid);
		
		str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state,t.sccId,pp.goodsname");
		str.append(" from t,dt_hr_staff staff,dt_ProductPackaging pp");
		str.append(" where staff.staffid = t.staffid and t.state = '1' and t.cusType = pp.model and pp.type ='会员类型级别'");
		
		str.append(" union all");
		str.append(" select t.account, t.cusType,company.companyname,cc.logopath,cc.industryType,t.state,t.sccId,pp.goodsname");
		str.append(" from t,dt_ccom_com r ,dtcompany company,dtContactCompany cc,dt_ProductPackaging pp");
		str.append(" where t.companyid=company.companyid and company.companyid=r.compnay_id ");
		str.append(" and r.ccompany_id=cc.ccompanyid and t.state = '2' and t.cusType = pp.model and pp.type ='会员类型级别'");
		sqlcount=sqlcount+"("+str.toString()+")";
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 15, str.toString(), sqlcount, params.toArray());
		}else if(cusType.equals("6")){
			//查询VIP客户和客户
			str.append("with t as(");
			str.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state,t.sccId from T_ESHOP_CUSCOM t where (t.cusType = ? or t.cusType = ?)");
			
			str.append(" connect by nocycle prior t.sccId = t.supperSccId");
			str.append(" start with t.sccId =?)");
			params.add(cusType);
			params.add(String.valueOf((int)Integer.parseInt(cusType)+1));
			params.add(sccid);
			

			str.append(" select t.account,t.custype,staff.staffname, staff.headimage,staff.staffPost,t.state,t.sccId,pp.goodsname");
			str.append(" from t, dt_hr_staff staff,dt_ProductPackaging pp");
			str.append(" where staff.staffid = t.staffid and t.state = '1' and t.cusType = pp.model and pp.type ='会员类型级别'");
			sqlcount=sqlcount+"("+str.toString()+")";
			pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 15, str.toString(), sqlcount, params.toArray());
		}else if(cusType.equals("8")){
		    //查询粉丝
	        //查询当前用户所有的默认等级的会员
			str.append("with t as(");
			str.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state ,t.sccId from T_ESHOP_CUSCOM t,dtJoinFans f");
			str.append(" where f.fsccId = t.sccId and f.zsccId = ? and f.state='00' and f.source!='系统粉丝' and f.source!='移动粉丝')");
			params.add(sccid);
			
			str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state ,t.sccId,pp.goodsname");
			str.append(" from t,dt_hr_staff staff,dt_ProductPackaging pp");
			str.append(" where staff.staffid = t.staffid and t.state = '1' and t.cusType = pp.model and pp.type ='会员类型级别'");
			
			str.append(" union all");
			str.append(" select t.account, t.cusType,company.companyname,cc.logopath,cc.industryType,t.state ,t.sccId,pp.goodsname");
			str.append(" from t,dt_ccom_com r ,dtcompany company,dtContactCompany cc,dt_ProductPackaging pp");
			str.append(" where t.companyid=company.companyid and company.companyid=r.compnay_id ");
			str.append(" and r.ccompany_id=cc.ccompanyid and t.state = '2' and t.cusType = pp.model and pp.type ='会员类型级别'");
			sqlcount=sqlcount+"("+str.toString()+")";
			pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 15, str.toString(), sqlcount, params.toArray());
		}else if(cusType.equals("9")){
			//查询移动粉丝
			str.append("with t as (");
			str.append(" select t.account,t.cusType,t.staffid st,t.Superioragent,t.companyid,t.state,t.sccId,f.staffid sta,f.faccount,f.fsccid");
			str.append(" from T_ESHOP_CUSCOM t right join dtJoinFans f on f.fsccId = t.sccId");
			str.append(" where f.zsccId = ? and f.source = '移动粉丝')");
			params.add(sccid);
			
			str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state ,t.sccId,pp.goodsname");
			str.append(" from t,dt_hr_staff staff,dt_ProductPackaging pp");
			str.append(" where staff.staffid = t.st and t.state = '1'  and t.cusType = pp.model and pp.type ='会员类型级别'");
			
			str.append(" union all");
			str.append(" select t.account, t.cusType,company.companyname,cc.logopath,cc.industryType,t.state ,t.sccId,pp.goodsname");
			str.append(" from t,dt_ccom_com r ,dtcompany company,dtContactCompany cc,dt_ProductPackaging pp");
			str.append(" where t.companyid=company.companyid and company.companyid=r.compnay_id ");
			str.append(" and r.ccompany_id=cc.ccompanyid and t.state = '2' and t.cusType = pp.model and pp.type ='会员类型级别'");
			
			str.append(" union all");
			str.append(" select t.faccount,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state ,t.sccId,t.fsccId");
			str.append(" from dt_hr_staff staff,t where staff.staffid = t.sta");
			
			sqlcount=sqlcount+"("+str.toString()+")";
			pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 15, str.toString(), sqlcount, params.toArray());
		}else if(cusType.equals("10")){
			//查询移动粉丝
			str.append("with t as (");
			str.append(" select t.account,t.cusType,t.staffid st,t.Superioragent,t.companyid,t.state,t.sccId,f.staffid sta,f.faccount,f.fsccid");
			str.append(" from T_ESHOP_CUSCOM t right join dtJoinFans f on f.fsccId = t.sccId");
			str.append(" where f.zsccId = ? and f.source = '系统粉丝')");
			params.add(sccid);
			
			str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state ,t.sccId,pp.goodsname");
			str.append(" from t,dt_hr_staff staff,dt_ProductPackaging pp");
			str.append(" where staff.staffid = t .st and t.state = '1'  and t.cusType = pp.model and pp.type ='会员类型级别'");
			
			str.append(" union all");
			str.append(" select t.account, t.cusType,company.companyname,cc.logopath,cc.industryType,t.state ,t.sccId,pp.goodsname");
			str.append(" from t,dt_ccom_com r ,dtcompany company,dtContactCompany cc,dt_ProductPackaging pp");
			str.append(" where t.companyid=company.companyid and company.companyid=r.compnay_id ");
			str.append(" and r.ccompany_id=cc.ccompanyid and t.state = '2' and t.cusType = pp.model and pp.type ='会员类型级别'");
			
			str.append(" union all");
			str.append(" select t.faccount,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state ,t.sccId,t.fsccid");
			str.append(" from dt_hr_staff staff,t where staff.staffid = t.sta");
			
			sqlcount=sqlcount+"("+str.toString()+")";
			pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 15, str.toString(), sqlcount, params.toArray());
		}else if("15".equals(cusType)){
			String sql = "select ec.account,15,hs.staffname,hs.headimage,hs.staffPost, ec.state,ec.sccId,cast('购物卡' as varchar(10)) as rem, hs.staffid"
						+" from dt_giftcards gc left join t_eshop_cuscom ec on ec.staffid = gc.staffid and ec.state = ?" +
						" left join dt_hr_staff hs on hs.staffid = gc.staffid" +
						" where gc.operator in (select ec.staffid from t_eshop_cuscom ec where ec.sccid  = ?)";
			pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 15, sql, "select count(*) from ("+sql+")", new Object[]{"1",sccid});
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm",pageForm);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	public String findVipNum() {
	    String hql = "from ProductPackaging s where s.type=?";
	    List<BaseBean>  viplist =  baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{"会员类型级别"});
	    Map<String,String> namemap = new HashMap<String,String>();
	    for (int i = 0; i < viplist.size(); i++) {
		    ProductPackaging pp = (ProductPackaging) viplist.get(i);
		    namemap.put(pp.getModel(),pp.getGoodsName());
	    }

		TEshopCusCom tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom d where d.sccId=?",new Object[] { sccid });

		List<List<String>> listt = new ArrayList<List<String>>();
		List<String> params = new ArrayList<String>();
		params.add(sccid);
		String sql = "select t.account,t.cusType,t.staffid,t.sccId from T_ESHOP_CUSCOM t  connect by nocycle prior t.sccid = t.supperSccId start with t.sccid = ?";
		List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql,params.toArray());
			
		String fanssql = "select count(*) from dtJoinFans f where f.zsccId = ? and f.source!='移动粉丝' and f.source!='系统粉丝' and state='00'";
	
		BigDecimal f = baseBeanService.getCountByBDSqlAndParams(fanssql,params.toArray());
		
		String syssql = "select count(*) from dtJoinFans jf where jf.zsccId=? and jf.source='系统粉丝' and jf.state='00'";
		BigDecimal x = baseBeanService.getCountByBDSqlAndParams(syssql, new Object[]{sccid});
		
		String telsql = "select count(*) from dtJoinFans jf where jf.zsccId=? and jf.source='移动粉丝' and jf.state='00'";
		
		BigDecimal t = baseBeanService.getCountByBDSqlAndParams(telsql, new Object[]{sccid});
		
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		int e= 0;//客户+VIP客户
	//	int f= 0;//粉丝
	//	int x = 0;//系统粉丝
		//int t = 0;//移动粉丝
		
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
	//	f=fanslist.size();
		//系统粉丝个数
	//	x=syslist.size();
		//移动粉丝个数
	//	t=tellist.size();
		//根据当前用户等级统计类型和会员等级
		String cusType = tcc.getCusType();
		List<String> temp = new ArrayList<String>();
		  
		 if (cusType.equals("6")||cusType.equals("7")) {
			//等级会员VIP客户或者普通客户 只显示粉丝
			temp = new ArrayList<String>();
			temp.add(namemap.get("8"));
			temp.add(f+"");//粉丝数
			temp.add("8");//临时当做8
			listt.add(temp);
		
			temp = new ArrayList<String>();
			temp.add(namemap.get("9"));
			temp.add(t+"");//移动粉丝数
			temp.add("9");//临时当做9
			listt.add(temp);
			
			temp = new ArrayList<String>();
			temp.add(namemap.get("10"));
			temp.add(x+"");//系统粉丝数
			temp.add("10");//临时当做10
			listt.add(temp);
		 } if (cusType.equals("5")) {
            //等级会员是代理商 显示 客户(普通客户+VIP客户)，粉丝
			temp= new ArrayList<String>();
			temp.add(namemap.get("6"));
			temp.add(e+ "");
			temp.add("6");
			listt.add(temp);
			
			temp = new ArrayList<String>();
		    temp.add(namemap.get("8"));
			temp.add(f+"");//粉丝数
			temp.add("8");//临时当做8
			listt.add(temp);
			
			temp = new ArrayList<String>();
			temp.add(namemap.get("9"));
			temp.add(t+"");//移动粉丝数
			temp.add("9");//临时当做9
			listt.add(temp);
			
			temp = new ArrayList<String>();
			temp.add(namemap.get("10"));
			temp.add(x+"");//系统粉丝数
			temp.add("10");//临时当做10
			listt.add(temp);
		}else if (cusType.equals("4")) {
			//等级会员是微分金显示 代理商商城业主会员 、客户(普通客户+VIP客户)，粉丝
			temp= new ArrayList<String>();
			temp.add(namemap.get("5"));
			temp.add(d + "");
			temp.add("5");
			listt.add(temp);

			temp= new ArrayList<String>();
			temp.add(namemap.get("6"));
			temp.add(e+ "");
			temp.add("6");
			listt.add(temp);
			
			temp = new ArrayList<String>();
			temp.add(namemap.get("8"));
			temp.add(f+"");//粉丝数
			temp.add("8");//临时当做8
			listt.add(temp);
			
			temp = new ArrayList<String>();
			temp.add(namemap.get("9"));
			temp.add(t+"");//移动粉丝数
			temp.add("9");//临时当做9
			listt.add(temp);
			
			temp = new ArrayList<String>();
			temp.add(namemap.get("10"));
			temp.add(x+"");//系统粉丝数
			temp.add("10");//临时当做10
			listt.add(temp);
		}else if (cusType.equals("3")) {
			//等级会员是合伙创业 显示微分金、 代理商商城业主会员 、客户(普通客户+VIP客户)，粉丝
			temp= new ArrayList<String>();
			temp.add(namemap.get("4"));
			temp.add(c + "");
			temp.add("4");
			listt.add(temp);
			
			temp= new ArrayList<String>();
			temp.add(namemap.get("5"));
			temp.add(d + "");
			temp.add("5");
			listt.add(temp);

			temp= new ArrayList<String>();
			temp.add(namemap.get("6"));
			temp.add(e+ "");
			temp.add("6");
			listt.add(temp);
			
			temp = new ArrayList<String>();
			temp.add(namemap.get("8"));
			temp.add(f+"");//粉丝数
			temp.add("8");//临时当做8
			listt.add(temp);
			
			temp = new ArrayList<String>();
			temp.add(namemap.get("9"));
			temp.add(t+"");//移动粉丝数
			temp.add("9");//临时当做9
			listt.add(temp);
			
			temp = new ArrayList<String>();
			temp.add(namemap.get("10"));
			temp.add(x+"");//系统粉丝数
			temp.add("10");//临时当做10
			listt.add(temp);
		}else if (cusType.equals("2")) {
			//等级会员是公司会员 显示合伙创业商城业主会员微分金、 代理商商城业主会员 、客户(普通客户+VIP客户)，粉丝
			temp = new ArrayList<String>();
			temp.add(namemap.get("3"));
			temp.add(b + "");
			temp.add("3");
			listt.add(temp);
			
			temp= new ArrayList<String>();
			temp.add(namemap.get("4"));
			temp.add(c + "");
			temp.add("4");
			listt.add(temp);
			
			temp= new ArrayList<String>();
			temp.add(namemap.get("5"));
			temp.add(d + "");
			temp.add("5");
			listt.add(temp);

			temp= new ArrayList<String>();
			temp.add(namemap.get("6"));
			temp.add(e+ "");
			temp.add("6");
			listt.add(temp);
			
			temp = new ArrayList<String>();
			temp.add(namemap.get("8"));
			temp.add(f+"");//粉丝数
			temp.add("8");//临时当做8
			listt.add(temp);
			
			temp = new ArrayList<String>();
			temp.add(namemap.get("9"));
			temp.add(t+"");//移动粉丝数
			temp.add("9");//临时当做9
			listt.add(temp);
			
			temp = new ArrayList<String>();
			temp.add(namemap.get("10"));
			temp.add(x+"");//系统粉丝数
			temp.add("10");//临时当做10
			listt.add(temp);
		}else if (cusType.equals("0")) {
			//等级会员是税务 显示公司 合伙创业商城业主会员微分金、 代理商商城业主会员 、客户(普通客户+VIP客户)，粉丝
			temp = new ArrayList<String>();
			temp.add(namemap.get("2"));
			temp.add(a+ "");
			temp.add("2");
			listt.add(temp);
			
			temp = new ArrayList<String>();
			temp.add(namemap.get("3"));
			temp.add(b + "");
			temp.add("3");
			listt.add(temp);
			
			temp= new ArrayList<String>();
			temp.add(namemap.get("4"));
			temp.add(c + "");
			temp.add("4");
			listt.add(temp);
			
			temp= new ArrayList<String>();
			temp.add(namemap.get("5"));
			temp.add(d + "");
			temp.add("5");
			listt.add(temp);

			temp= new ArrayList<String>();
			temp.add(namemap.get("6"));
			temp.add(e+ "");
			temp.add("6");
			listt.add(temp);
			
			temp = new ArrayList<String>();
			temp.add(namemap.get("8"));
			temp.add(f+"");//粉丝数
			temp.add("8");//临时当做8
			listt.add(temp);
			
			temp = new ArrayList<String>();
			temp.add(namemap.get("9"));
			temp.add(t+"");//移动粉丝数
			temp.add("9");//临时当做9
			listt.add(temp);
			
			temp = new ArrayList<String>();
			temp.add(namemap.get("10"));
			temp.add(x+"");//系统粉丝数
			temp.add("10");//临时当做10
			listt.add(temp);
		}

		//增加用该用户创建的购物卡

		int count = baseBeanService.getConutByBySqlAndParams("select count(*) from dt_giftcards gc where gc.operator in (select ec.staffid from t_eshop_cuscom ec where ec.sccid  = ?)",new Object[]{sccid});
		temp = new ArrayList<String>();
		temp.add("办理购物卡");
		temp.add(count+"");//开通购物卡数
		temp.add("15");//临时当做15
		listt.add(temp);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("listt",listt);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	//建设我的平台经济
	public String findconWealth() {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		SessionWrap sw = SessionWrap.getInstance();
		
		if(!"".equals(sccid)&&sccid != null){
			customer = (TEshopCustomer) sw.getObject(session,
					SessionWrap.KEY_CUSTOMER);
			if(customer==null){
				String url = request.getHeader("Referer");
				session.setAttribute("url", url);
				return "login";
			}
			conwealth(sccid);		
		}else{
			CAccount cAccount = (CAccount)sw.getObject(session, SessionWrap.KEY_CACCOUNT);
			if(cAccount == null){
				session.setAttribute("url", "page/ea/BuildPlatform/MobileOfficeLogin.jsp");
				return "login";
			}
			String companyId = cAccount.getCompanyID();
			TEshopCusCom te = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where companyId =? ", new Object[]{companyId});	
			request.setAttribute("sccid", te.getSccId());
			flag = "sys";
			conwealth(te.getSccId());						
		}			
		return "conwealth";
	}
	public void conwealth(String sccid){
		HttpServletRequest request=ServletActionContext.getRequest();
		String hql = "from TEshopCusCom where sccId = ?";
		if(sccid != null && !sccid.equals("")){
			cuscom = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{sccid});
			if(cuscom!=null){
				if (cuscom.getState().equals("1")) {
					staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?",
							new Object[] { cuscom.getStaffid() });
				} else {
					contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany t where t.ccompanyID  = (select m.ccompanyId from CcomCom m where m.comanyId = ?)",
							new Object[] { cuscom.getCompanyId()});
				}
				ProductPackaging pp = (ProductPackaging)baseBeanService.getBeanByHqlAndParams("from ProductPackaging where model = ? and type = ?", new Object[]{cuscom.getCusType(),"会员类型级别"});
				request.setAttribute("custypename", pp.getGoodsName());
				request.setAttribute("back", 1);
				productPackaging = (ProductPackaging)baseBeanService.getBeanByHqlAndParams("from ProductPackaging where ppid=?", new Object[]{cuscom.getPpid()});
			}
			
		}
	}
	//切换平台跳转至我的平台经济
	public String changeToConWealth(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		TEshopCusCom tcc=null;
		SessionWrap sw = SessionWrap.getInstance();
		//flag  为标识移动办公     会员中心的标识   flag = sys 移动办公
		if(flag != null && !"".equals(flag)){
			//获取登录者的信息
			CAccount cAccount = (CAccount)sw.getObject(session, SessionWrap.KEY_CACCOUNT);						
			tcc= (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where companyId =? ", new Object[]{cAccount.getCompanyID()});				
		}else{			
			tcc = (TEshopCusCom) sw.getObject(session,SessionWrap.KEY_SHOPCUSCOM);
		}	
		tcc.setAcquiesce("00");
		baseBeanService.update(tcc);
		cuscom = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{sccid});
		cuscom.setAcquiesce("01");
		baseBeanService.update(cuscom);
		sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cuscom);
		conwealth(sccid);
		return "conwealth";
	}
	//跳转订单管理页面
	public String orderManage(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		SessionWrap sw = SessionWrap.getInstance();
		if(sccid!=null&&!sccid.equals("")){
			cuscom = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{sccid});
		}else{
			customer = (TEshopCustomer) sw.getObject(session,
					SessionWrap.KEY_CUSTOMER);
			if(customer==null){
				String url = request.getHeader("Referer");
				session.setAttribute("url", url);
				return "login";
			}
			cuscom = (TEshopCusCom) sw.getObject(session,
					SessionWrap.KEY_SHOPCUSCOM);
		}
		return "ordermanage";
	}
	//跳转联营商城会员需求发布页面
	public String vipDemand(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		SessionWrap sw = SessionWrap.getInstance();
		if(sccid!=null&&!sccid.equals("")){
			cuscom = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{sccid});
		}else{
			customer = (TEshopCustomer) sw.getObject(session,
					SessionWrap.KEY_CUSTOMER);
			if(customer==null){
				String url = request.getHeader("Referer");
				session.setAttribute("url", url);
				return "login";
			}
			cuscom = (TEshopCusCom) sw.getObject(session,
					SessionWrap.KEY_SHOPCUSCOM);
		}
		sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cuscom);
		TEshopCustomer cus = (TEshopCustomer) baseBeanService
				.getBeanByHqlAndParams(
						"from TEshopCustomer where account=? AND logOff=0",
						new Object[]{cuscom.getAccount()});
		sw.setObject(session, SessionWrap.KEY_CUSTOMER, cus);
		return "vipdemand";
	}
	//跳转至二维码页面
	public String QRcode(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String basePath=request.getScheme()+"://"+request.getServerName()+request.getContextPath()+"/";
		HttpSession session = request.getSession();
		SessionWrap sw = SessionWrap.getInstance();
		customer = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		if(customer==null){
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
			return "login";
		}
		qrcode(basePath);
		return "qrcode";
	}
	public void qrcode(String basePath){
		String hql="from TEshopCusCom where staffid=? and acquiesce=?";
		if(staffid != null && !staffid.equals("")){
			cuscom = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{staffid,"01"});
			if (cuscom!=null && cuscom.getState().equals("1")) {
				staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?",
						new Object[] { cuscom.getStaffid() });
				if(staff!=null){
					if(staff.getHeadimage()==null||staff.getHeadimage().equals("")){
						staff.setHeadimage("images/contacts/Network/defaultbig.png");
					}
					String	httpUrl =Constant.HTTP+"text="+basePath+"ea/perinfor/ea_getPageHomeData.jspa?staffId="+staff.getStaffID();				
					String	httpArg = "&logo="+basePath+staff.getHeadimage()+"&key="+Constant.API_KEY;
					qrcode=httpUrl+httpArg;	
				}
			} else {
				contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany t where t.ccompanyID  = (select m.ccompanyId from CcomCom m where m.comanyId = ?)",
						new Object[] { cuscom.getCompanyId()});
				if(contactCompany!=null){
					if(contactCompany.getLogoPath()==null||contactCompany.getLogoPath().equals("")){
						contactCompany.setLogoPath("images/contacts/Network/defaultbig.png");
					}
					String	httpUrl =Constant.HTTP+"text="+basePath+"ea/industry/ea_CompanyCard.jspa?ccompanyId="+contactCompany.getCcompanyID();				
					String	httpArg = "&logo="+basePath+contactCompany.getLogoPath()+"&key="+Constant.API_KEY;
					qrcode=httpUrl+httpArg;	
				}
				
			}
			productPackaging = (ProductPackaging)baseBeanService.getBeanByHqlAndParams("from ProductPackaging where ppid=?", new Object[]{cuscom.getPpid()});
		}	
		
	}
	//切换平台跳转至二维码页面
	public String ChangeToQrcode(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String basePath=request.getScheme()+"://"+request.getServerName()+request.getContextPath()+"/";
		HttpSession session = request.getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom tcc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		tcc.setAcquiesce("00");
		baseBeanService.update(tcc);
		cuscom = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{sccid});
		cuscom.setAcquiesce("01");
		baseBeanService.update(cuscom);
		sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cuscom);
		staffid=tcc.getStaffid();
		qrcode(basePath);
		return "qrcode";
	}
	
	//跳转至切换平台页面
	@SuppressWarnings("unchecked")
	public String Platform(){		
		cuscom = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{sccid});
		String hql = "select tec.sccid,tec.custype,tec.ppid,p.goodsname,p.type,pp.goodsname vipname from t_eshop_cuscom tec left join dt_productpackaging pp on pp.model = tec.custype left join dt_productpackaging p" +
				" on tec.ppid=p.ppid where tec.staffid=? and pp.type=?";
		list = baseBeanService.getListBeanBySqlAndParams(hql, new Object[]{staffid,"会员类型级别"});
		return "platform";
	}
	//跳转至系统数据客户导入页面    
	public String importData(){
		list1 = new ArrayList<Object>();
		cuscom = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?",new Object[]{sccid});
		String hql ="from JoinFans where fsccId=? and state=?";
		list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cuscom.getSccId(),"01"});
		for (int i = 0; i < list.size(); i++) {
			JoinFans jf = (JoinFans)list.get(i);
			//先做的单身份后续在进行修改
			String sql ="select t.sccId,p.goodsname,t.state,t.staffid,t.account,t.companyId from t_eshop_cuscom t,dt_productpackaging p where t.sccid=? and t.custype=p.model and p.type='会员类型级别'";
			Object obj = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{jf.getZsccId()});
			Object[] oo=(Object[]) obj;
			if(oo[2].equals("1")){
				staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?",
						new Object[] { oo[3]});
				if(staff!=null){
					list1.add(new String[]{oo[4].toString(),oo[1].toString(),staff.getStaffName(),staff.getHeadimage(),oo[0].toString()});
				}
			}else{
				contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany t where t.ccompanyID  = (select m.ccompanyId from CcomCom m where m.comanyId = ?)",
						new Object[] { oo[5].toString()});
				if(contactCompany!=null){
					list1.add(new String[]{oo[4].toString(),oo[1].toString(),contactCompany.getCompanyName(),contactCompany.getLogoPath(),oo[0].toString()});
				}
			}
		}
		return "importdata";
	}
	//根据手机号码搜索账户信息
	@SuppressWarnings("unchecked")
	public String ajaxData(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		SessionWrap sw = SessionWrap.getInstance();
		cuscom = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?",new Object[]{sccid});
		customer = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		list1 = new ArrayList<Object>();
		String sql ="select t.sccId,p.goodsname,t.state,t.staffid,t.account,t.companyId from t_eshop_cuscom t,dt_productpackaging p where t.account=? and t.custype=p.model and p.type='会员类型级别'";
		list = baseBeanService.getListBeanBySqlAndParams( sql, new Object[]{user});
		Map<String, Object> map = new HashMap<String, Object>();
		if(list!=null){
			for (int i = 0; i < list.size(); i++) {
				Object obj=(Object)list.get(i);
				Object[] oo=(Object[]) obj;
				if(oo[2].equals("1")){
					staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?",
							new Object[] { oo[3] });
					JoinFans jf = (JoinFans)baseBeanService.getBeanByHqlAndParams("from JoinFans where zsccId=? and fsccId=?", new Object[]{cuscom.getSccId(),oo[0].toString()});
					if(staff!=null){
						if(jf!=null){
							list1.add(new String[]{oo[4].toString(),oo[1].toString(),staff.getStaffName(),staff.getHeadimage(),jf.getState(),oo[0].toString()});
						}else{
							list1.add(new String[]{oo[4].toString(),oo[1].toString(),staff.getStaffName(),staff.getHeadimage(),"",oo[0].toString()});
						}
					}
				}else{
					contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany t where t.ccompanyID  = (select m.ccompanyId from CcomCom m where m.comanyId = ?)",
							new Object[] { oo[5].toString()});
					JoinFans jf = (JoinFans)baseBeanService.getBeanByHqlAndParams("from JoinFans where zsccId=? and fsccId=?", new Object[]{cuscom.getSccId(),
							oo[0].toString()});
					if(contactCompany!=null){
						if(jf!=null){
							list1.add(new String[]{oo[4].toString(),oo[1].toString(),contactCompany.getCompanyName(),contactCompany.getLogoPath(),jf.getState(),oo[0].toString()});
						}else{
							list1.add(new String[]{oo[4].toString(),oo[1].toString(),contactCompany.getCompanyName(),contactCompany.getLogoPath(),"",oo[0].toString()});
						}
					}
				}
			}
		}
		map.put("list",list1);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	//添加好友等待对方确认
	public String ajaxAddFriend(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom tcuscom = (TEshopCusCom)sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		String hql ="from JoinFans where zsccId=? and fsccId=? and state=?";
		JoinFans jf = (JoinFans)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{tcuscom.getSccId(),sccid,"01"});
		if(jf==null){
			jf = new JoinFans();
			jf.setJfID(serverService.getServerID("jfID"));
			jf.setSource("搜索添加粉丝");
			jf.setState("01");
			jf.setZsccId(tcuscom.getSccId());
			jf.setFsccId(sccid);
			baseBeanService.save(jf);
		}
		return "success";
	}
	//接受好友请求
	public String ajaxReceiveFriend(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom tcuscom = (TEshopCusCom)sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		customer = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		String hql ="from JoinFans where zsccId=? and fsccId=? and state=?";
		JoinFans jf = (JoinFans)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{sccid,tcuscom.getSccId(),"01"});
		jf.setState("00");
		
		String hql1 ="from JoinFans where zsccId=? and fsccId=? and state=?";
		JoinFans jf1 = (JoinFans)baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{tcuscom.getSccId(),sccid,"00"});
		if(jf1==null){
			jf1 = new JoinFans();
			jf1.setJfID(serverService.getServerID("jfID"));
			jf1.setSource("搜索添加粉丝");
			jf1.setState("00");
			jf1.setZsccId(tcuscom.getSccId());
			jf1.setFsccId(sccid);
			baseBeanService.save(jf1);
		}
		baseBeanService.update(jf);
		return "success";
	}
	//删除好友请求
	public String deleteFriend(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom tcuscom = (TEshopCusCom)sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		String hql ="delete JoinFans where zsccId = ? and fsccId = ? and state = ?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql},new Object[]{sccid,tcuscom.getSccId(),"01"});
		return "success";
	}
	
	//跳转至抢客户页面
	public String robWfjUser(){
		String hql = "from TEshopCusCom where sccId = ?";
		TEshopCusCom tcuscom = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{sccid});

		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, tcuscom);
		return "robwfjuser";
	}
	//无上级的客户,而且比本人的会员等级低的客户  
	public String ajaxrobWfjUser(){
		StringBuffer sql = new StringBuffer();
		JSONObject ret = new JSONObject();
		List<String> params = new ArrayList<String>();
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom tcuscom = (TEshopCusCom)sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		if(Integer.parseInt(tcuscom.getCusType())<=5){
		sql.append(" select t.sccid,t.account,s.headimage,s.staffname from t_eshop_cuscom t,dt_hr_staff s "); 
		sql.append(" where t.staffid = s.staffid and t.suppersccid = ?");
		params.add("TEshopCusCom20161010W9FXK9NJ450000011682");
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 15, sql.toString(), "select count(1) from (" + sql.toString() +")", params.toArray());
		if(pageForm ==null){
			ret.accumulate("pagm", "0");
			result=ret.toString();
		}else{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm",pageForm);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		}
		}else{
			ret.accumulate("pagm", "1");
			result=ret.toString();
		}
		return "success";
	} 
	
	//已抢到的客户列表
	public String WfjUser(){
		List<String> params = new ArrayList<String>();
		JSONObject ret = new JSONObject();
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom tcuscom = (TEshopCusCom)sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		StringBuffer sql = new StringBuffer();
		//查看个人已抢列表
		sql.append(" select t.sccid,t.account,s.headimage,s.staffname from t_eshop_cuscom t,dt_hr_staff s,dtmarketing d "); 
		sql.append(" where t.staffid = s.staffid and d.usersccid=t.sccid and t.suppersccid = ? and d.source='抢客户' ");
		params.add(tcuscom.getSccId());
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 15, sql.toString(), "select count(1) from (" + sql.toString() +")", params.toArray());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm",pageForm);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	//点击抢客户
	public String getWfjUser(){
		List<BaseBean> beans = new ArrayList<BaseBean>();
		JSONObject ret = new JSONObject();
		String hql = "from TEshopCusCom where sccId = ? and supperSccId =?";
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom t = (TEshopCusCom)sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		
		TEshopCusCom tcuscom = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{sccid,"TEshopCusCom20161010W9FXK9NJ450000011682"});
		if(tcuscom ==null){
			ret.accumulate("pageForm", "1");
			result=ret.toString();
		}else {
			tcuscom.setSupperSccId(t.getSccId());
			tcuscom.setSuperioragent(t.getAccount());
			tcuscom.setPpid(t.getPpid());
			
			JoinFans jf1 = new JoinFans();
			jf1.setJfID(serverService.getServerID("jfID"));
			jf1.setFsccId(tcuscom.getSccId());
			jf1.setFaccount(tcuscom.getAccount());
			jf1.setStaffid(tcuscom.getStaffid());
			jf1.setSource("抢客户");
			jf1.setState("00");
			jf1.setZsccId(t.getSccId());
			jf1.setZaccount(t.getAccount());
	   
			JoinFans jf2 = new JoinFans();
			jf2.setJfID(serverService.getServerID("jfID"));
			jf2.setFsccId(t.getSccId());
			jf2.setFaccount(t.getAccount());
			jf2.setStaffid(t.getStaffid());
			jf2.setSource("抢客户");
			jf2.setState("00");
			jf2.setZsccId(tcuscom.getSccId());
			jf2.setZaccount(tcuscom.getAccount());
			beans.add(jf1);
			beans.add(jf2);
			
			String hql2=" from MarKeting  where userSccId = ?";
			MarKeting mk=(MarKeting)baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{sccid});
			mk.setMkSccId(t.getSccId()); //邀请人（sccId）
			mk.setMkuserID(t.getAccount());
			mk.setMkdate(new Date());
			mk.setSource("抢客户");
		baseBeanService.update(mk);
		baseBeanService.update(tcuscom);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		ret.accumulate("pageForm", "0");
		result=ret.toString();
		
		}
		return "success";
	}
	
	
	
	
	
	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
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

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getSccid() {
		return sccid;
	}

	public void setSccid(String sccid) {
		this.sccid = sccid;
	}

	public TEshopCusCom getCuscom() {
		return cuscom;
	}

	public void setCuscom(TEshopCusCom cuscom) {
		this.cuscom = cuscom;
	}

	public TEshopCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(TEshopCustomer customer) {
		this.customer = customer;
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

	public ProductPackaging getProductPackaging() {
		return productPackaging;
	}

	public void setProductPackaging(ProductPackaging productPackaging) {
		this.productPackaging = productPackaging;
	}

	public List<BaseBean> getList() {
		return list;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getStaffid() {
		return staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public Map<Integer, String[]> getMaplist1() {
		return maplist1;
	}

	public void setMaplist1(Map<Integer, String[]> maplist1) {
		this.maplist1 = maplist1;
	}

	public List<Object> getList1() {
		return list1;
	}

	public void setList1(List<Object> list1) {
		this.list1 = list1;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getKhd() {
		return khd;
	}

	public void setKhd(String khd) {
		this.khd = khd;
	}
	
}  

