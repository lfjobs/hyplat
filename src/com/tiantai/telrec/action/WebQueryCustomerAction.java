package com.tiantai.telrec.action;

import hy.ea.bo.CAccount;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tiantai.telrec.bean.FlexiGridJSonBean;

@SuppressWarnings("serial")
public class WebQueryCustomerAction extends ActionSupport {

	private com.tiantai.telrec.service.ClientQueryCustomerService queryCustomerServie;

	// public com.tiantai.telrec.service.ClientQueryCustomerService
	// getQueryCustomerServie() {
	// return queryCustomerServie;
	// }

	public void setQueryCustomerServie(
			com.tiantai.telrec.service.ClientQueryCustomerService queryCustomerServie) {
		this.queryCustomerServie = queryCustomerServie;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public String execute() throws Exception {
		// AllDao dao = new AllDao();
		FlexiGridJSonBean bean=new FlexiGridJSonBean(); 
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String companyid = account.getCompanyID();
		int pages = Integer.valueOf(request.getParameter("page"));

		int rp = Integer.valueOf(request.getParameter("rp"));
		String sortname = request.getParameter("sortname");
		String sortorder = request.getParameter("sortorder");
		bean.rows = queryCustomerServie.queryCustomerForSplit(pages, rp, companyid);
		// rows = dao.findByIpDate(sortname, sortorder, (pages - 1) * rp,
		// rp);
		try{
		bean.total = queryCustomerServie.total(companyid, rp);
		bean.page = pages;
		JSONObject jsonArray = JSONObject.fromObject(bean);
		String outString = jsonArray.toString();
		//System.out.println(outString);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(outString);
		response.flushBuffer();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * public String add() { AllDao dao = new AllDao(); Goods good = new
	 * Goods(); HttpServletRequest request = ServletActionContext.getRequest();
	 * String name = request.getParameter("name"); String stand =
	 * request.getParameter("stand"); int money =
	 * Integer.valueOf(request.getParameter("money")); int leavings =
	 * Integer.valueOf(request.getParameter("leavings")); int orders =
	 * Integer.valueOf(request.getParameter("orders"));
	 * good.setLeavings(leavings); good.setMoney(money); good.setName(name);
	 * good.setOrders(orders); good.setStand(stand); dao.add(good); return
	 * SUCCESS; }
	 * 
	 * public String modify() { AllDao dao = new AllDao(); Goods good = new
	 * Goods(); HttpServletRequest request = ServletActionContext.getRequest();
	 * String name = request.getParameter("name"); String stand =
	 * request.getParameter("stand"); int id =
	 * Integer.valueOf(request.getParameter("id")); int money =
	 * Integer.valueOf(request.getParameter("money")); int leavings =
	 * Integer.valueOf(request.getParameter("leavings")); int orders =
	 * Integer.valueOf(request.getParameter("orders")); good.setId(id);
	 * good.setLeavings(leavings); good.setMoney(money); good.setName(name);
	 * good.setOrders(orders); good.setStand(stand); dao.modify(good); return
	 * SUCCESS; }
	 */
	// public List getRows() {
	// return rows;
	// }
}
