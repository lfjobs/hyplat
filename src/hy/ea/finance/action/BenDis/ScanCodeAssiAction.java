package hy.ea.finance.action.BenDis;

import com.tiantai.wfj.bo.MarKeting;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.WfjAccountService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.BenDis.JoinFans;
import hy.ea.bo.finance.ClientOrderDetail;
import hy.ea.bo.finance.PayBackupBill;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.Cater;
import hy.ea.bo.production.AttriProduction;
import hy.ea.finance.service.AssiCartService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 选择产品生成二维码
 */
@Controller
@Scope("prototype")
public class ScanCodeAssiAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;

	@Resource
	WfjAccountService wfjAccountService;
	@Resource
	AssiCartService assiCartService;
	private String sccid;
	private PayBackupBill payBackupBill;
	private String result;
	private PageForm pageForm;
	private int pageNumber;
	private String state;
	private String type;//调用方式
    private String parameter;//
	private String coID;
	private ClientOrderDetail clientOrderDetail;
	private Cater cater;//餐桌
	private String account;
	private String password;



	/**
	 *
	 * 公司列表
	 * @return
	 */
	public String getAssiCompanyList(){

		HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = ServletActionContext.getRequest().getSession();

		session.setAttribute("coID","");


        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        if (cus == null) {
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
            return "login";
        }
		List<BaseBean> companylist = assiCartService.getListCompanyBySccid(cus.getSccId());
		if(companylist.size()>1||companylist.size()==0){

			request.setAttribute("companylist",companylist);
			return "assicom";

		}
		ContactCompany contactCompany = (ContactCompany)companylist.get(0);
		String ccompanyId = contactCompany.getCcompanyID();
		CcomCom cc = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where ccompanyId = ?",new Object[]{ccompanyId});


		request.setAttribute("ccompanyId",ccompanyId);
		request.setAttribute("companyId",cc.getComanyId());
		request.setAttribute("pos","1");
		return "companyproducts";
	}

	/**
	 *
	 * 点击公司列表进入商城
	 * @return
	 */
	public String getCompanyProList(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		String ccompanyId = request.getParameter("ccompanyId");
		String coID = request.getParameter("coID");
		if(coID!=null&&!coID.equals("")){
			session.setAttribute("coID",coID);

		}

		ContactCompany contactCompany = (ContactCompany)baseBeanService.getBeanByHqlAndParams("from ContactCompany where ccompanyId = ?",new Object[]{ccompanyId});

		CcomCom cc = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where ccompanyId = ?",new Object[]{ccompanyId});

		request.setAttribute("ccompanyId",ccompanyId);
		request.setAttribute("companyId",cc.getComanyId());
		request.setAttribute("pos","1");
		return "companyproducts";
	}


	/**
	 *
	 * 生成购物车结算收款码
	 * @return
	 */
	public String genJieSunCode(){
		HttpServletRequest request = ServletActionContext.getRequest();

		String companyId = request.getParameter("companyId");

		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		if (cus == null) {
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
			return "login";
		}
		List<Object> list = null;
		if(coID!=null&&!coID.equals("")){
			list = assiCartService.getClientGoods(coID);
		}else{
		    list = assiCartService.getCartList(cus.getStaffid(),companyId);
		}



		ContactCompany contactCompany = assiCartService.getContactCompany(companyId);
		request.setAttribute("list",list);
		request.setAttribute("waiterID",cus.getStaffid());
		request.setAttribute("sccid",cus.getSccId());
		request.setAttribute("companyId",companyId);
		request.setAttribute("contactCompany",contactCompany);
		request.setAttribute("dp","2");
		request.setAttribute("coID",coID);

		return "jscode";
	}

	/**
	 *
	 * 生成单品结算码
	 * @return
	 */
	public String genDpJieSunCode(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String companyId = request.getParameter("companyId");
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		if (cus == null) {
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
			return "login";
		}
		String goodsName = request.getParameter("goodsName");//物品名称
		String quality = request.getParameter("quality");//shuliang
		String price = request.getParameter("price");//零售单价
		String ppid = request.getParameter("ppid");//产品ID
        List<Object> list = new ArrayList<Object>();
        Object[] obj = new Object[3];
		obj[0] = goodsName;
		obj[1] = price;
		obj[2] = quality;
		list.add(obj);
		ContactCompany contactCompany = assiCartService.getContactCompany(companyId);
		request.setAttribute("list",list);
		request.setAttribute("waiterID",cus.getStaffid());
		request.setAttribute("sccid",cus.getSccId());
		request.setAttribute("ppid",ppid);
		request.setAttribute("quality",quality);
		request.setAttribute("dp","1");
		request.setAttribute("contactCompany",contactCompany);


		return "jscode";
	}

	/**
	 * 生成订单号存储支付回调时需要的参数
	 * @return
	 */
	public String getJum(){
		try {


		HttpServletRequest request = ServletActionContext.getRequest();
		String sccid=request.getParameter("sccid");
		String posNum = request.getParameter("posNum");

		TEshopCusCom cus=(TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccid=?", new Object[]{sccid});
		if(cus!=null||(posNum!=null&&!posNum.equals(""))){
			String jum=serverService.getBillID("company201009046vxdyzy4wg0000000025");

			payBackupBill.setPbbID(serverService.getServerID("pbbID"));
			payBackupBill.setJournalNum(jum);
			if(cus!=null){
			payBackupBill.setStaffId(cus.getStaffid());
			}
			baseBeanService.save(payBackupBill);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("jum", jum);
			if(cus!=null){
			map.put("wfj_staffid",cus.getStaffid());
			}
			JSONObject oj = JSONObject.fromObject(map);
			result = oj.toString();

		}
		}catch (Exception e){
			e.printStackTrace();;
		}

		return "success";
	}

	/**
	 *
	 *
	 * 公司商城
	 * @return
	 */
	public String shopAssiCartGoodNum()
	{
		HttpServletRequest request = ServletActionContext.getRequest();

		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, sw.KEY_CUSTOMER);
		Map<String, Object> map = new HashMap<String, Object>();
		int goodNum = 0;
		String companyId = request.getParameter("companyId");
		String pos = request.getParameter("pos");
		String posNum = request.getParameter("posNum");
		try {
		 if(posNum!=null&&!posNum.equals("")){
				String sql = "select nvl(sum(to_number(c.itemNum)),0) from DT_SqSelfCart c,dt_ProductPackaging  p where p.ppID = c.pid and c.posNum = ? and p.showweixin = ?";
				goodNum = baseBeanService.getConutByBySqlAndParams(sql, new Object[]{posNum, "01"});

			} else if (cus != null) {


				String sql = "select sum(c.itemNum) from dt_Cart c,dt_ProductPackaging  p where p.ppID = c.pid and c.staff_Id = ? and p.showweixin = ? ";
				List<Object> param = new ArrayList<Object>();
				param.add(cus.getStaffid());
				param.add("01");
				if(pos!=null&&pos.equals("1")){
                    sql+=" and c.companyId = ?";
					param.add(companyId);
					sql+=" and c.pos = '1'";

				}else{
					sql+=" and (c.pos is null or c.pos = '0')";

				}
				goodNum = baseBeanService.getConutByBySqlAndParams(sql,param.toArray());

		   }
			map.put("goodNum",goodNum);

		} catch (Exception e) {
			map.put("goodNum",goodNum);
			//e.printStackTrace();
		}
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 *
	 * 根据物品id 获取产品属性
	 * @return
	 */
	public String getProAttriList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String goodsid = request.getParameter("goodsid");
		//颜色尺码
		String hqlattr = "from AttriProduction where type = ? and goodsid = ?";
		List<BaseBean> listsize = baseBeanService.getListBeanByHqlAndParams(hqlattr, new Object[]{"0", goodsid});
		List<BaseBean> listcolor = baseBeanService.getListBeanByHqlAndParams(hqlattr, new Object[]{"1", goodsid});
		request.setAttribute("size", listsize.size() == 0 ? null : ((AttriProduction) listsize.get(0)).getAttriname());
		request.setAttribute("color", listcolor.size() == 0 ? null : ((AttriProduction) listcolor.get(0)).getAttriname());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("size",listsize.size() == 0 ? "" : ((AttriProduction) listsize.get(0)).getAttriname());
		map.put("color", listcolor.size() == 0 ? "" : ((AttriProduction) listcolor.get(0)).getAttriname());
		map.put("listsize", listsize);
		map.put("listcolor", listcolor);
		JSONObject jo = JSONObject.fromObject(map);
		this.result  = jo.toString();
		return "success";
	}


	///////////////////////////////////////////////////////////////客户订单///////////////////////////////////////////////

	/***
	 * 客户订单列表
	 *
	 * @return
	 */
   public String getClientOrderList(){
       HttpServletRequest request = ServletActionContext.getRequest();
	   HttpSession session = ServletActionContext.getRequest().getSession();
	   SessionWrap sw = SessionWrap.getInstance();
	   TEshopCusCom cus = (TEshopCusCom) sw.getObject(session, sw.KEY_SHOPCUSCOM);
       Map<String, Object> map = new HashMap<String, Object>();
	   if(cus==null) {
           String url = request.getHeader("Referer");
           session.setAttribute("url", url);
           if(type!=null&&type.equals("ajax")){
               map.put("login", "login");
               JSONObject json = JSONObject.fromObject(map);
               this.result = json.toString();
               return "success";
           }

           return "login";
	   }
       pageForm = getPageFormList(cus.getSccId(),state);
	   if(type!=null&&type.equals("ajax")){

           map.put("pageForm",pageForm);
           JSONObject jo = JSONObject.fromObject(map);
           this.result  = jo.toString();
           return "success";
       }



   	  return "clientlist";
   }


   public PageForm getPageFormList(String sccid,String state){
       List<String> companylist = assiCartService.getCompanyIDBySccid(sccid);
       List<Object> param = new ArrayList<Object>();
       StringBuilder sql = new StringBuilder();
       sql.append("select r.coID,r.orderNum,r.boardNo,r.companyID,cc.logopath,cc.companyName,trunc(r.totalMoney, 2),cc.ccompanyID from dtClientOrder r,dtContactCompany cc,DT_ccom_com mm");
       sql.append(" where r.companyID = mm.compnay_id and cc.ccompanyID = mm.ccompany_Id and r.companyID in(");
       if(companylist.size()!=0) {
		   for (int i = 0; i < companylist.size(); i++) {
			   if (i != companylist.size() - 1) {
				   sql.append("?,");
			   } else {
				   sql.append("?)");
			   }
			   param.add(companylist.get(i));


		   }
		   if (parameter != null && !parameter.equals("")) {
			   sql.append(" and (cc.companyName like ? or r.boardNo = ? or r.orderNum = ?");
			   param.add("%" + parameter + "%");
			   param.add("%" + parameter + "%");
			   param.add("%" + parameter + "%");
		   }
		   sql.append(" and r.state = ?");
		   param.add(state);

		   sql.append(" order by r.createDate desc,r.companyID asc");
		   pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 3 : pageNumber), sql.toString(), "select count(*) " + sql.toString().substring(sql.toString().indexOf("from")), param.toArray());
	   }
       return pageForm;
   }

    /**
     *
     * 收付商城导航
     * @return
     */
   public String navsfShop(){

       if(sccid!=null){
           TEshopCusCom cus = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccid = ?",new Object[]{sccid});
           TEshopCustomer customer = (TEshopCustomer)baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0",new Object[]{cus.getAccount()});

           HttpSession session = ServletActionContext.getRequest().getSession();
           SessionWrap sw = SessionWrap.getInstance();
           sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
           sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cus);
       }
       return "navsf";
   }

    /**
     *
     * 查看详情
     * @return
     */
   public String getClientOrderDetail(){
	   HttpServletRequest request = ServletActionContext.getRequest();
	  Map<String,Object> map =  assiCartService.getOrderDetail(coID);
      String state = (String)map.get("state");
      Object clientOrder = map.get("clientOrder");
	  List<BaseBean> clientDetaillist = (List<BaseBean>)map.get("clientDetail");
	  List<BaseBean> clientGoodslist = (List<BaseBean>)map.get("clientGoods");
      request.setAttribute("clientOrder",clientOrder);
      request.setAttribute("clientDetaillist",clientDetaillist);
	  request.setAttribute("clientGoodslist",clientGoodslist);
      if(state!=null&&state.equals("01")){
		  return "orderdetail";

	  }else{
		  return "finidetail";
	  }


   }

	/**
	 *
	 * 生成订单前的页面
	 * @return
	 */
	public String getClientShopDetail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		String companyId = request.getParameter("companyId");
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom cus = (TEshopCusCom) sw.getObject(session, sw.KEY_SHOPCUSCOM);
		if(cus==null){
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
			return "login";
		}

		String coID = (String)session.getAttribute("coID");
		Object clientOrder = null;
		List<BaseBean> clientDetaillist = null;
		List<BaseBean> clientGoodslist = null;
		float money = 0;
		if(coID!=null&&!coID.equals("")) {
			Map<String, Object> map = assiCartService.getOrderDetail(coID);
			 String state = (String) map.get("state");
		     clientOrder = map.get("clientOrder");
		     clientDetaillist = (List<BaseBean>) map.get("clientDetail");
			 clientGoodslist = (List<BaseBean>) map.get("clientGoods");
			 Object[] objs = (Object[])clientOrder;
			 money=money+Float.parseFloat(objs[6].toString());
		}
		request.setAttribute("clientOrder", clientOrder);
		request.setAttribute("clientDetaillist", clientDetaillist);
		request.setAttribute("clientGoodslist", clientGoodslist);



        //获取购物车新增产品内容
		List<Object> list = assiCartService.getCartList(cus.getStaffid(),companyId);

		//获取总金额

		money = money+assiCartService.getTotalMoney(list);
		DecimalFormat fnum   =   new   DecimalFormat("##0.00");
		request.setAttribute("money",fnum.format(money));

		String hql = "from Staff where staffID = ?";
		Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{cus.getStaffid()});
		request.setAttribute("staff",staff);
		request.setAttribute("addGoodslist",list);
		request.setAttribute("companyId",companyId);
		request.setAttribute("coID",coID);


		//获取餐桌列表
		List<BaseBean> catelist = assiCartService.getCateList(companyId);
		request.setAttribute("catelist",catelist);
		String url = request.getRequestURL()+"?"+request.getQueryString();
		request.setAttribute("callurl",url);



		 return  "shopdetail";

   }

	/**
	 *  下单
	 *
	 * @return
	 */
	public String genClientOrderDetail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		String companyId = request.getParameter("companyId");
		String remark = request.getParameter("remark");
		String eatType = request.getParameter("eatType");

		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom cus = (TEshopCusCom) sw.getObject(session, sw.KEY_SHOPCUSCOM);

		assiCartService.genClientOrder(cus.getStaffid(),companyId,cater,coID,remark,eatType);

		return "success";
   }

   public  String PurchaseSuccess(){
	   HttpSession session = ServletActionContext.getRequest().getSession();
	   SessionWrap sw = SessionWrap.getInstance();
	   TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
	   TEshopCusCom cuscom=(TEshopCusCom)sw.getObject(session,SessionWrap.KEY_SHOPCUSCOM);
	   HttpServletRequest request = ServletActionContext.getRequest();
	   request.setAttribute("mealNum",request.getParameter("mealNum"));
		   if (cus!=null&&!"".equals(cus)) {
			   List<BaseBean> beans = new ArrayList<BaseBean>();
			   cus.setAccount(account);
			   cus.setPassword(password);
			   beans.add(cus);
			   cuscom.setAccount(account);
			   beans.add(cuscom);
			   String hql = "from MarKeting where usersccid = ?";
			   MarKeting mk = (MarKeting) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{cuscom.getSccId()});
			   mk.setUserID(account);
			   beans.add(mk);
			   String hqlfs1 = "from JoinFans where zsccId = ?";
			   String hqlfs2 = "from JoinFans where fsccId = ?";
			   JoinFans jf1 = (JoinFans)baseBeanService.getBeanByHqlAndParams(hqlfs1,new Object[]{cuscom.getSccId()});
			   if(jf1!=null){
			    jf1.setZaccount(account);
			   }
			   beans.add(jf1);
			   JoinFans jf2 = (JoinFans)baseBeanService.getBeanByHqlAndParams(hqlfs2,new Object[]{cuscom.getSccId()});
			   if(jf2!=null) {
				   jf2.setFaccount(account);
			   }
			   beans.add(jf2);
			   baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,null);
		   }
	   return "bindingSuccess";
   }

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public  String Purchase(){
   	return "purchaseSuccess";
	}



	public String getSccid() {
		return sccid;
	}

	public void setSccid(String sccid) {
		this.sccid = sccid;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public PayBackupBill getPayBackupBill() {
		return payBackupBill;
	}

	public void setPayBackupBill(PayBackupBill payBackupBill) {
		this.payBackupBill = payBackupBill;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

	public String getCoID() {
		return coID;
	}

	public void setCoID(String coID) {
		this.coID = coID;
	}

	public void setClientOrderDetail(ClientOrderDetail clientOrderDetail) {
		this.clientOrderDetail = clientOrderDetail;
	}

	public Cater getCater() {
		return cater;
	}

	public void setCater(Cater cater) {
		this.cater = cater;
	}
}
