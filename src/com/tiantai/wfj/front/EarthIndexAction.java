package com.tiantai.wfj.front;

import com.alipay.util.httpClient.HttpRequest;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.common.ClientUtils;
import com.tiantai.wfj.common.Response;
import com.tiantai.wfj.service.EarthIndexService;
import com.tiantai.wfj.service.WeChatMiniService;
import com.tiantai.wfj.service.impl.SmsService;
import com.tiantai.wfj.util.SessionWrap;
import com.tiantai.wfj.vo.WxPhone;
import com.tiantai.wfj.vo.WxResult;
import hy.ea.bo.CAccount;
import hy.ea.util.SmsClientSend;
import hy.ea.util.StringUtil;
import hy.ea.util.isms.MobileSMS;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tiantai.wfj.front.constact.WxMiniConstant.ServerId.H5;


@Controller
@Scope("prototype")
public class EarthIndexAction {
	
	@Resource
	private SmsService smsService;
	@Resource
	private EarthIndexService earthIndexService;
	@Resource
	private WeChatMiniService weChatMiniService;
	@Resource
	private BaseBeanService baseBeanService;
	private String code;
	private String state;
	private String user;
	private int pageNumber;
	private String result;
	private PageForm pageForm;
	private String phone;
	private String tips;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = ServletActionContext.getRequest().getSession();
	SessionWrap sw = SessionWrap.getInstance();


	public String bindPhone() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		String json;
		PrintWriter out = response.getWriter();
		HttpSession session1 = ServletActionContext.getRequest().getSession();

		if (StringUtil.isEmpty(phone))
		{
			json = "{\"success\":false,\"message\":\"请输入手机号\"}";
			ClientUtils.sendResponse(out,json);
			return null;
		}
		if (StringUtil.isNotEmpty(phone)&&StringUtil.isNotEmpty(code)){



			if (!session.getAttribute(SessionWrap.KEY_SMS).equals(code)){
				json = "{\"success\":false,\"message\":\"验证码不正确\"}";
				ClientUtils.sendResponse(out,json);
				return null;
			}
			TEshopCusCom cusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account=? and logOff=0", new Object[]{phone});
			if (cusCom!=null){

				if (cusCom != null) {
					// 当前手机号已经存在
					WxResult wxResult =(WxResult) SessionWrap.getInstance().getObject(session1, SessionWrap.KEY_TEMPLATE_WX_RESULT);
					weChatMiniService.bindPhone(wxResult,phone,H5);
					json = "{\"success\":false,\"message\":\"当前手机号已经存在\"}";
				} else {
					// 模拟绑定成功
					json = "{\"success\":true,\"message\":\"绑定成功\"}";
				}
				ClientUtils.sendResponse(out,json);
				return null;
			}
			List<BaseBean> beans=new ArrayList<>();

			TEshopCusCom cus = (TEshopCusCom) SessionWrap.getInstance().getObject(session1, SessionWrap.KEY_SHOPCUSCOM);
			TEshopCustomer comter = (TEshopCustomer) SessionWrap.getInstance().getObject(session1, SessionWrap.KEY_CUSTOMER);
			cus= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where staffid=? and logOff=0",new Object[]{cus.getStaffid()});
			comter= (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where staffid=? and logOff=0",new Object[]{comter.getStaffid()});
			cus.setAccount(phone);
			comter.setAccount(phone);
			comter.setPassword("aaaaaaaaaaaa");
			beans.add(cus);
			beans.add(comter);
			json = "{\"success\":true,\"message\":\"绑定成功\"}";
			ClientUtils.sendResponse(out,json);
			baseBeanService.saveBeansListAndexecuteSqlsByParams(beans,null,null);
		}

		return "earthindex";
	}
	
	/**
	 * 
	 *
	 * @return
	 */
	public String  earthIndex(){
		if (StringUtil.isNotEmpty(code)&&state.equals("STATE")){
			System.out.println(state);
			Response<Object> result = weChatMiniService.WxH5Login(code);
			if (result.getCode() !=-1){
				if(result.getData() instanceof TEshopCusCom)
				{
					TEshopCusCom tEshopCusCom= (TEshopCusCom) result.getData();
					if (StringUtil.isNotEmpty(tEshopCusCom.getAccount())){
						phone=tEshopCusCom.getAccount();
					}
				}
			}
		}

		String pc = request.getParameter("pc");
		String login = request.getParameter("login");
		if(user!=null&&!user.equals("")){
			 TEshopCusCom tc = earthIndexService.getCusCom(user);
			 TEshopCustomer customer = earthIndexService.getCustomer(user);
			 sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
	         sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, tc);


		}

		TEshopCustomer customer = (TEshopCustomer) sw.getObject(session,
	            SessionWrap.KEY_CUSTOMER);
		 TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
	             SessionWrap.KEY_SHOPCUSCOM);


		if(tc==null&&"login".equals(login)){
			String url = request.getRequestURL() + "?" + request.getQueryString();
			session.setAttribute("url", url);
			return "login";
		}else{

           if(tc!=null) {
			   phone=tc.getAccount();
			   CAccount account = new CAccount();
			   account.setStaffID(tc.getStaffid());
			   HttpSession session1 = ServletActionContext.getRequest().getSession();
			   session1.setAttribute("account", account);
		   }
		}


		 return "earthindex";
	}

	public String sendMessage(){
		return smsService.sendSms();
	}

	/**
	 *
	 * 获取浏览的商家记录
	 * @return
     */
	public String getBrowseCompanyList(){
		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);

		if(tc==null){
			String url = request.getRequestURL() + "?" + request.getQueryString();
			session.setAttribute("url", url);
			return "login";

		}
		String ajax = request.getParameter("ajax");


		if("ajax".equals(ajax)){
			pageForm = earthIndexService.getBrowseCompanyList(pageNumber,10,tc.getSccId());
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("pageForm",pageForm);
			JSONObject jo = JSONObject.fromObject(map);
			this.result  = jo.toString();
			return "success";
		}

		return "tobrowse";
	}


	/**
	 *
	 * 获取浏览的资讯列表
	 * @return
	 */
	public String getBrowseNewsList(){
		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);

		if(tc==null){
			String url = request.getRequestURL() + "?" + request.getQueryString();
			session.setAttribute("url", url);
			return "login";

		}
		pageForm = earthIndexService.getBrowseNewsList(pageNumber,30,tc.getSccId());

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageForm",pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result  = jo.toString();
		return "success";
	}

	/**
	 *
	 * 获取浏览小视频列表
	 * @return
	 */
	public String getBrowseVideoList(){
		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);

		if(tc==null){
			String url = request.getRequestURL() + "?" + request.getQueryString();
			session.setAttribute("url", url);
			return "login";

		}
		pageForm = earthIndexService.getBrowseVideoList(pageNumber,30,tc.getSccId());

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageForm",pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result  = jo.toString();
		return "success";
	}


	/**
	 *
	 * 获取浏览小视频列表
	 * @return
	 */
	public String getBrowseGoodsList(){
		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);

		if(tc==null){
			String url = request.getRequestURL() + "?" + request.getQueryString();
			session.setAttribute("url", url);
			return "login";

		}
		pageForm = earthIndexService.getBrowseGoodsList(pageNumber,6,tc.getSccId());

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageForm",pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result  = jo.toString();
		return "success";
	}


	/**
	 *
	 * 获取浏览小视频列表
	 * @return
	 */
	public String getBrowseAppList(){
		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);

		if(tc==null){
			String url = request.getRequestURL() + "?" + request.getQueryString();
			session.setAttribute("url", url);
			return "login";

		}
		List<BaseBean> list  = earthIndexService.getBrowseAppList(tc.getSccId());

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list",list);
		JSONObject jo = JSONObject.fromObject(map);
		this.result  = jo.toString();
		return "success";
	}



	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
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

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}
}
