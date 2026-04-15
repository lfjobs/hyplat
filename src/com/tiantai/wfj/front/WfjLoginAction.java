package com.tiantai.wfj.front;

import com.tiantai.wfj.bo.MarKeting;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.WfjAccountService;
import com.tiantai.wfj.service.WfjJifenService;
import com.tiantai.wfj.util.SessionWrap;
import com.wechat.bo.WxUserInfo;
import com.wechat.utils.WeixinUtil;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.finance.BenDis.JoinFans;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.finance.service.BonusPointsService;
import hy.ea.office.service.ContractService;
import hy.ea.util.MobileMessage;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.util.JushMain;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@Scope("prototype")
public class WfjLoginAction {
	
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private BonusPointsService bonusPointsService;

	@Resource
	private WfjJifenService wfjJifenService;
	@Resource
	private WfjAccountService wfjAccountService;

	@Resource
	private ContractService  contractService;
	private String phones;
	private String user;
	private Staff staff;
	private String intf;
	private String sccid;
	private String companyId;
	private String ccompanyId;
	private String activity;
	private String ppid;
	private String result;// AJAX使用
	private String ptppid;
	private Logger logger = LoggerFactory.getLogger(WfjLoginAction.class);
	
	@Autowired
	private MobileMessage msage;//发短信

	private String identify;//识别标识,用于判断是否是车辆推荐进来
	private String code;//微信Code


	/**
	 * 微信登陆
	 * @return
	 */
	public String wxlogin(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String redirectUrl = request.getParameter("redirectUrl");//授权后获取用户信息后要跳转的页面
		String tjsccid = request.getParameter("tjsccid");

		String url = request.getRequestURL() + "?" + request.getQueryString();   //当前链接
		System.out.println("redirectUrl:"+redirectUrl);
		System.out.println("url:"+url);
		try {
		if (code == null || code.equals("")) {//如果第一次进来

			String urlmicro = wfjAccountService.isWxLogin(request, url);
			if (urlmicro != null && !urlmicro.equals("")) {
				System.out.println("urlmicro:"+urlmicro);
				response.sendRedirect(urlmicro);//跳转授权
				return null;


			}
		}else {//授权后跳转
			String urlmicro = wfjAccountService.isWxLogin(request, url);
			if (!"".equals(urlmicro)) {
				System.out.print("code:" + code);
				logger.error("code:" + code);
				WxUserInfo wxUserInfo = WeixinUtil.getWxUserInfoByCode(code, "wxa1b3f84c027804c3");
				if (wxUserInfo == null) {
					System.out.print("wxUserInfo:" + wxUserInfo);
					logger.error("wxUserInfo:" + wxUserInfo);
				}
				sccid = wfjAccountService.createAccount(wxUserInfo, tjsccid, null);
				TEshopCusCom cus = (TEshopCusCom) baseBeanService.getObjectByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccid});
				TEshopCustomer customer = null;
				SessionWrap sw = SessionWrap.getInstance();
				HttpSession session = request.getSession();
				if (cus.getAccount() != null && !cus.getAccount().equals("")) {
					customer = (TEshopCustomer) baseBeanService
							.getBeanByHqlAndParams(
									"from TEshopCustomer where account=? AND logOff=0",
									new Object[]{cus.getAccount()});
				} else {

					customer = (TEshopCustomer) baseBeanService
							.getBeanByHqlAndParams(
									"from TEshopCustomer where openId=?",
									new Object[]{cus.getOpenId()});

				}

				sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
				sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cus);
			}
			response.sendRedirect(redirectUrl);//跳转授权
		 }
		} catch (IOException e) {
			e.printStackTrace();
		}
         return null;
	}


	/**
	 * 微信登陆
	 * @return
	 */
	public String wxloginNew(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String redirectUrl = request.getParameter("redirectUrl");//授权后获取用户信息后要跳转的页面
		String tjsccid = request.getParameter("tjsccid");

		String url = request.getRequestURL() + "?" + request.getQueryString();   //当前链接
//		System.out.println("redirectUrl:"+redirectUrl);
//		System.out.println("url:"+url);
		try {
//			response.sendRedirect(redirectUrl+"?nickName=11&openid=21212");//跳转授权

			if (code == null || code.equals("")) {//如果第一次进来

				String urlmicro = wfjAccountService.isWxLogin(request, url);
				if (urlmicro != null && !urlmicro.equals("")) {
					System.out.println("urlmicro:"+urlmicro);
					response.sendRedirect(urlmicro);//跳转授权
					return null;


				}
			}else {//授权后跳转
				String urlmicro = wfjAccountService.isWxLogin(request, url);
				if (!"".equals(urlmicro)) {
					System.out.print("code:" + code);
					logger.error("code:" + code);
					WxUserInfo wxUserInfo = WeixinUtil.getWxUserInfoByCode(code, "wxa1b3f84c027804c3");
					if (wxUserInfo == null) {
						System.out.print("wxUserInfo:" + wxUserInfo);
						logger.error("wxUserInfo:" + wxUserInfo);
					}
					String nickName = URLEncoder.encode(wxUserInfo.getNickname(), "UTF-8"); // 编码参数

					response.sendRedirect(redirectUrl+"?nickName="+nickName+"&openid="+wxUserInfo.getOpenid());//跳转授权


				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
// APP注册新用户 seve
	/**
	 * @param 
	 * @param @user推荐人或者推荐网站的账号
	 * @return
	 */
	public synchronized String seves() {
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpServletRequest request=ServletActionContext.getRequest();

		//注册的新用户
		TEshopCusCom zccus= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0", new Object[]{phones});
		if(zccus==null&&phones!=null){
			//推荐人
			if(sccid==null || sccid.equals("")){
				sccid="TEshopCusCom20161010W9FXK9NJ450000011682";
			}
			TEshopCusCom tuicus= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{sccid});

           String stf =  wfjJifenService.zhuCe(tuicus,sccid, phones, intf,staff);

			if(sccid.equals("TEshopCusCom20161010W9FXK9NJ450000011682")){
				String sqlslist=" select t.account from t_eshop_cuscom t  where t.custype<'6' and t.custype>'1' and t.sccid != ? ";
				List<String> list=new ArrayList<String>();
				SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
				String date = sdf.format(new Date());
				list=baseBeanService.getListBeanBySqlAndParams(sqlslist, new Object[]{"TEshopCusCom20161010W9FXK9NJ450000011682"});
				JushMain.sendjiguangMessage("有新用户注册，点击立即抢单，手慢无，快来抢！"+date, "抢下级", "有新的客户注册哦！", "lowerlevel", list);
			}
				
				//pushMwssage2();
				
				//发展下级，获得500个积分,
				/*if(!"TEshopCusCom20161010W9FXK9NJ450000011682".equals(sccid)){
					String hql = "from Staff where staffID = ? ";
					Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{tuicus.getStaffid()});					
					String jiFen = "500";
					String flag = bonusPointsService.shareCode(sccid, tuicus.getStaffid(), tuicus.getAccount(), jiFen);
					if("ok".equals(flag))
					{
						logger.error("分享二维码，成功发展下级");
						List<String> slist=new ArrayList<String>() ;
					    slist.add(tuicus.getAccount());
						JushMain.sendjiguangMessage("分享二维码，成功发展下级，赠送"+jiFen+"积分","", "", "shareCode", slist);
						logger.error(staff.getStaffName()+"分享二维码，成功发展下级，赠送"+jiFen+"积分");
					}													
				}	*/


				//
			//接收公文数据
			//phones
			contractService.receiveDoc(phones);
		}
		if (identify!=null && identify.equals("cltjzc"))
		{
			return "cltjzc";
		}
		else
		{
			return "zcok";
		}
	}

	/**
	 *
	 * 处理接收公文数据
	 * @return
	 */
	public String dealDoc(){

		 contractService.receiveDoc(phones);

		return "success";
	}
	public String personGiveCard(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		if(session.getAttribute(SessionWrap.KEY_CUSTOMER)==null){//如果没登陆,跳转登录,下面url是个人交换名片路径
			String url = ServletActionContext.getRequest().getRequestURL()+"?sccid="+sccid;
			session.setAttribute("url", url);
			return "login";
		}else{
			SessionWrap sw = SessionWrap.getInstance();
			TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
					SessionWrap.KEY_SHOPCUSCOM);
			activity = cus.getSccId();
		}
		String hql ="select m from JoinFans m where m.fsccId=? and m.zsccId=?";
		List<BaseBean> lst=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{sccid,activity});
		if((lst!=null&&lst.size()>0)||sccid.equalsIgnoreCase(activity)){//存在关系.或者互粉对象一样,均跳出
			return "zcok";
		}else{
			List<BaseBean> list=new ArrayList<BaseBean>();
			JoinFans jf1 = new JoinFans();
			jf1.setJfID(serverService.getServerID("jfID"));
			jf1.setFsccId(sccid);
			jf1.setSource("新版粉丝好友名片");
			jf1.setState("00");
			jf1.setZsccId(activity);
			
			JoinFans jf2 = new JoinFans();
			jf2.setJfID(serverService.getServerID("jfID"));
			jf2.setZsccId(sccid);
			jf2.setSource("新版粉丝好友名片");
			jf2.setState("00");
			jf2.setFsccId(activity);
			
			list.add(jf1);
			list.add(jf2);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
			return "zcok";
		}
	}
	
	/**
	 * 
	 * 注册成功推送短信
	 * 
	 */
	private void pushMwssage2(){
		try {
			msage.setMobiles(phones);
			msage.setMessage("恭喜您已成功注册数字地球，欢迎您使用！！！");
			msage.sendMsg("【数字地球】");
			phones="";
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void findWebUser(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String sccid="";
		if(ccompanyId!=null&&ccompanyId.length()>0){
			String hql="select new CcomCom(cc.comanyId) from CcomCom cc where cc.ccompanyId=?";
			CcomCom cc=(CcomCom)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ccompanyId});
			if(cc!=null&&cc.getComanyId()!=null){
				companyId=cc.getComanyId();
			}
			if(companyId!=null){
				hql="select m from TEshopCusCom m where m.state=? and m.companyId=?";
				TEshopCusCom te=(TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{"2",companyId});
				if(te!=null){
					user=te.getAccount();//记录邀请人
					sccid=te.getSccId();
				}
			}
			
		}
		
		try {
			HttpSession session = ServletActionContext.getRequest().getSession();
	    	session.setAttribute("session_value", Math.random() + "");
			response.sendRedirect(request.getContextPath()+"/ea/wfjshop/ea_getjspzc.jspa?sccid="+sccid);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * 
	 * 验证是否有权利购买会员产品
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String validatecanBuy() {

		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		Map<String, Object> map = new HashMap<String, Object>();
		if (cus == null) {
			return "login";
		}

		String hqlcus = "from TEshopCusCom t where account = ?";
		TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(
				hqlcus, new Object[] { cus.getAccount() });

		String hql = "from ProductPackaging where ppID = ?";
		ProductPackaging pp = (ProductPackaging) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { ppid });

		String cusType = tc.getCusType();
		String model = pp.getModel();
		String type = pp.getType();
		TEshopCusCom tcweb = (TEshopCusCom) baseBeanService
				.getBeanByHqlAndParams(
						"from TEshopCusCom t where t.companyId = (select c.comanyId from CcomCom c where c.comanyId = t.companyId and c.ccompanyId = ?)",
						new Object[] { ccompanyId });
		JoinFans jfans = null;
		if (tcweb != null) {
			jfans = (JoinFans) baseBeanService.getBeanByHqlAndParams("from JoinFans where zsccId=? and fsccId=?",
					new Object[]{tcweb.getSccId(),tc.getSccId()});
		} else {
			map.put("result", "该网站尚未付款加入微分金平台系统");
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
		}

		String sql = "select t.sccId,t.cusType,t.pseudo_Company_Name from T_ESHOP_CUSCOM t where t.state = '2' connect by nocycle prior  t.supperSccId=t.sccId start with t.sccId = ?";
		List<Object> listp = baseBeanService.getListBeanBySqlAndParams(sql,
				new Object[] { tc.getSccId() });

		MarKeting mk = (MarKeting) baseBeanService.getBeanByHqlAndParams(
				"from MarKeting where userSccId = ?",
				new Object[] { tc.getSccId() });

		List<String> sb = new ArrayList<String>();
		String comname = "";
		
		boolean t3 = true;
		String tip = "您的账号有问题，请联系工作人员!";
		for (int i = 0; i < listp.size(); i++) {
			Object[] obj = (Object[]) listp.get(i);
			sb.add(obj[0].toString());
			if (obj[1].toString().equals("2")) {
				comname = obj[2].toString();
			}
		}
		if (!tcweb.getCusType().equals("0") && !model.equals("8")
				&& tc.getSupperSccId() != null
				&& !tc.getSupperSccId().equals("")
				&& !sb.contains(tcweb.getSccId())) {
			tip = "您已是" + comname
					+ "会员，请在该公司下购买升级，只能在其他公司购买其他产品。如果要购买只有用另一个手机号重新注册购买";
			t3 = false;
		} else if (cusType.equals("0.5") || cusType.equals("1")) {
			tip =  "税务相关会员无法购买其他会员级别";// 税务相关用户无法购买其他会员级别
			t3 = false;
		} else if (!model.equals("8")
				&& (Float.parseFloat(cusType) < Float.parseFloat(model))) {
            tip="对不起，无法降级购买会员";// 不可降级购买
            t3 = false;

		} else if ((model.equals("0.5") || model.equals("1"))
				&& !cusType.equals("7")) {
			tip = "对不起，只有新注册用户才能购买税务相关会员级别";// 只有7用户才能购买税务
			t3 = false;

		} else if (model.equals("0") && !cusType.equals("7")) {// 平台
			tip ="对不起，只有新注册用户才能购买平台";
			t3 = false;
			

		} else if (model.equals(cusType)) {
			tip= "对不起，不能购买相同的会员级别";
			t3 = false;


		} else if (!model.equals("8") && tc.getState().equals("2")
				&& type.equals("个人会员")) {// 平台
			tip = "对不起，您当前是公司会员不能转为个人会员";
			t3 = false;

		} else if (model.equals("8") && jfans != null) {
			tip = "对不起，您与该公司已经是粉丝好友，不可重复添加";
			t3 = false;

		} else if (!model.equals("6")) {
			String hqls = "from TEshopCusCom where cusType = ?";
			List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(
					hqls, new Object[] { tc.getCusType() });
			if (list.size() == 1) {
				String hqlsub = "from TEshopCusCom where supperSccId = ?";
				List<BaseBean> listsub = baseBeanService
						.getListBeanByHqlAndParams(hqlsub,
								new Object[] { tc.getSccId() });
				if (listsub.size() != 0) {
					tip = "对不起，暂时没有权限购买该会员级别";
					t3 = false;
				}
			}
		} else if (model.equals("0") && !cusType.equals("7")) {// 平台
			tip = "对不起，只有新注册用户才能购买平台";
			t3 = false;
		}

		StringBuilder cushql = new StringBuilder(
				"SELECT t.staffid, t.companyid, t.state,t.supperSccId,");
		cushql.append("t.pseudo_company_name,t.custype,t.sccid,t.account");
		cushql.append(" FROM T_ESHOP_CUSCOM t START WITH t.sccId = ?");
		cushql.append(" CONNECT BY nocycle PRIOR t.supperSccId = t.sccId");
		List<Object[]> cusList = baseBeanService.getListBeanBySqlAndParams(
				cushql.toString(), new Object[] { tc.getSccId() });
		if (mk == null && Float.parseFloat(cusList.get(0)[5].toString()) != 0f) {
			t3 = false;
		}
/*		else if(mk!=null&&mk.getMkuserID().equals("15810799888")&&tc.getCusType().equals("7")){
			t3 = false;
		}*/
		else {
			if (cusList.size() > 1) {
				float t1 = Float.parseFloat(cusList.get(0)[5].toString());
				if (t1 != 0f) {
					float t2 = Float.parseFloat(cusList.get(1)[5].toString());
					if (t2 == 0f && t1 != 0.5f) {
						t3 = false;
					} else if (t1 == 7f && t2 == 6f) {
						t3 = false;
					} else if (t1 <= t2) {
						t3 = false;
					} else if (t2 == 0.5f && t1 != 1f) {
						t3 = false;
					}
				} else {
					t3 = false;
				}
			} else if (cusList.size() == 1) {
				float t1 = Float.parseFloat(cusList.get(0)[5].toString());
				if (t1 != 0f) {
					t3 = false;
				}
			} else {
				t3 = false;
			}
		}
		if (!t3) {
			map.put("result", tip);
		} else {
			map.put("result", "success");
		}

		/*
		 * if(tc.getCusType()!=null&&tc.getCusType().equals("7")){
		 * if(mk==null){//没有推荐人 map.put("mk", "nomk"); }else{//有推荐人
		 * map.put("mk", "yesmk"); } }else{ map.put("mk", "yesmk"); }
		 */
		cushql.delete(0, cushql.length());
		cushql.append("select ps.ppid");
		cushql.append(" from DT_PRO_SETUP ps, dt_ProductPackaging p");
		cushql.append(" where p.ppid = ps.ppid and ps.fcom_id is null and ps.ppid in");
		cushql.append(" (select pt.ptppid from dt_promotion pt");
		cushql.append(" where pt.ppid = ?)");
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(cushql.toString(), new Object[]{ppid});
		if(list!=null&&list.size()>0){
			ptppid="";
			for(int i=0;i<list.size();i++){
				ptppid+=list.get(i)+",";
			}
		}
		map.put("ptppid", ptppid);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return "success";

	}
	/**
	 *
	 * 绑定账号
	 * @return
	 */
	public String validateAccount(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletResponse response=ServletActionContext.getResponse();

		String openid = request.getParameter("openid");
		String nickName = request.getParameter("nickName");

            String re = wfjAccountService.validateAccount(openid);

            if(re=="0"){
            	request.setAttribute("openid",openid);
            	request.setAttribute("nickName",nickName);
				return "bintel";
			}else {
            	System.out.println("scid:"+re);
			TEshopCusCom cus = (TEshopCusCom) baseBeanService.getObjectByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{re});
			TEshopCustomer customer = null;
			SessionWrap sw = SessionWrap.getInstance();
			if (cus.getAccount() != null && !cus.getAccount().equals("")) {
				customer = (TEshopCustomer) baseBeanService
						.getBeanByHqlAndParams(
								"from TEshopCustomer where account=? AND logOff=0",
								new Object[]{cus.getAccount()});
			}

			sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
			sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cus);
		}
           //如果有账号，跳转


            return "earth";
	}

	/**
	 *
	 * 绑定账号
	 * @return
	 */
	public String bindTel(){

		HttpServletRequest request=ServletActionContext.getRequest();
		String openid = request.getParameter("openid");
		String nickName = request.getParameter("nickName");
		String tel = request.getParameter("tel");
		String r =  wfjAccountService.bindTel(openid,tel,nickName);
		if(r.length()>1){
			TEshopCusCom cus = (TEshopCusCom) baseBeanService.getObjectByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{r});
			TEshopCustomer customer = null;
			SessionWrap sw = SessionWrap.getInstance();
			HttpSession session = request.getSession();
			if (cus.getAccount() != null && !cus.getAccount().equals("")) {
				customer = (TEshopCustomer) baseBeanService
						.getBeanByHqlAndParams(
								"from TEshopCustomer where account=? AND logOff=0",
								new Object[]{cus.getAccount()});
			}

			sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
			sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cus);

			try {
				contractService.receiveDoc(cus.getAccount());
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("result",r);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";

	}


	public ServerService getServerService() {
		return serverService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getIntf() {
		return intf;
	}

	public void setIntf(String intf) {
		this.intf = intf;
	}

	public String getSccid() {
		return sccid;
	}

	public void setSccid(String sccid) {
		this.sccid = sccid;
	}

	public MobileMessage getMsage() {
		return msage;
	}

	public void setMsage(MobileMessage msage) {
		this.msage = msage;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCcompanyId() {
		return ccompanyId;
	}

	public void setCcompanyId(String ccompanyId) {
		this.ccompanyId = ccompanyId;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
