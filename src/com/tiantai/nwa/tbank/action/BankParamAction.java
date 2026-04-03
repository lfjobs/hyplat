package com.tiantai.nwa.tbank.action;

import hy.ea.bo.CAccount;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.nwa.tbank.bo.BankLinkParam;
import com.tiantai.nwa.tbank.bo.BankSX;
import com.tiantai.nwa.tbank.bo.IpAcl;
import com.tiantai.nwa.tbank.bo.TransConf;
import com.tiantai.nwa.util.DockingBankInitUtil;

@Controller
@Scope("prototype")
public class BankParamAction {
	private String result;
	private JSONObject resultjson;
	private String xmlpath;
	
	private BankLinkParam bankLinkParam;
	private TransConf transConf;
	private IpAcl ipConf;
	
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	
	private int page;
	private int rows;
	private String innerAction;

	/**
	 * 
	 * 从XML文件中读取银行信息
	 * 
	 * @return
	 */	
	public String getBanksFromXml() {
		
		if ("getBankParam".equals(innerAction)) return getBankParam();
		if ("insertBankParam".equals(innerAction)) return setBankParam();
		
		List<BankSX> list = new ArrayList<BankSX>();
		Map<String,String> mapEsx = DockingBankInitUtil.getBankSXMap();
		Iterator<String> iter = mapEsx.keySet().iterator();
		while (iter.hasNext())
		{			
			BankSX bsx = new BankSX();
			String esx = iter.next();
			bsx.setEsx(esx);
			bsx.setName(mapEsx.get(esx));			
			list.add(bsx);
		}		
		ServletActionContext.getRequest().setAttribute("banklist", list);		
		return "showbanklist";

	}
	
	

	/**
	 * 
	 * 提交设置参数保存和更新
	 * 
	 * @return
	 */
	private String setBankParam() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		if (account == null) {
			return "not_login";
		}
		try {
			if (bankLinkParam.getKey() == null
					|| bankLinkParam.getKey().equals("")) {
				bankLinkParam.setBpID(serverService.getServerID("bpID"));
				bankLinkParam.setEnterpriseID(account.getCompanyID());
			}
			baseBeanService.update(bankLinkParam);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * 获取银行参数信息
	 * 
	 * @return
	 */
	private String getBankParam() {
		HttpServletResponse response = ServletActionContext.getResponse();
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "not_login";
		}		
		String hql = "from BankLinkParam where enameSx = ? and enterpriseID = ?";
		BankLinkParam blp = (BankLinkParam) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] {
						bankLinkParam.getEnameSx(), account.getCompanyID() });

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bankLinkParam", blp);
		JSONObject json = JSONObject.fromObject(map);		
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {			
			e.printStackTrace();
		}	
		
		return null;

	}

	/**
	 * 
	 * 
	 * 保存和更新银行交易配置
	 * 
	 * @return
	 */
	public String saveTransConf() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		if (account == null) {
			return "not_login";
		}
		try {
			if (transConf.getKey() == null || transConf.getKey().equals("")) {
				transConf.setConfID(serverService.getServerID("confID"));
				transConf.setEnterpriseID(account.getCompanyID());
			}
			baseBeanService.update(transConf);
			result = "success";
		} catch (Exception e) {
			result = "fail";
			e.printStackTrace();
		}

		return "success";

	}

	/**
	 * 
	 * 根据银行(以及公司ID)加载银行交易配置
	 * 
	 * @return
	 */
	public String getTransConfByBank() {
		try {
			CAccount account = (CAccount) ActionContext.getContext()
					.getSession().get("account");

			if (account == null) {
				return "not_login";
			}
			String hql = "from TransConf where enterpriseID = ? and enameSx = ?";
			hy.plat.bo.PageForm pageForm = baseBeanService.getPageForm(page,
					(rows == 0 ? 5 : rows), hql, new Object[] {
							account.getCompanyID(), transConf.getEnameSx() });
			System.out.println(transConf.getEnameSx());
			if (pageForm != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("total", pageForm.getRecordCount());
				map.put("rows", pageForm.getList());
				resultjson = JSONObject.fromObject(map);
				this.result = resultjson.toString();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";

	}

	/**
	 * 
	 * 删除银行交易配置信息
	 * 
	 * @return
	 */
	public String deleteConf() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			baseBeanService
					.deleteBeanByKey(TransConf.class, transConf.getKey());
			map.put("success", "success");
		} catch (Exception e) {
			map.put("errorMsg", "delete fail");
			e.printStackTrace();
		}

		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return "success";

	}

	////////////////////////////IP控制///////////////////////////////
	
	public String getIpConfByEnterID(){
		
		try {
			CAccount account = (CAccount) ActionContext.getContext()
					.getSession().get("account");

			if (account == null) {
				return "not_login";
			}
			String hql = "from IpAcl where enterpriseID = ?";
			hy.plat.bo.PageForm pageForm = baseBeanService.getPageForm(page,
					(rows == 0 ? 5 : rows), hql, new Object[] {
							account.getCompanyID()});
			if (pageForm != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("total", pageForm.getRecordCount());
				map.put("rows", pageForm.getList());
				resultjson = JSONObject.fromObject(map);
				this.result = resultjson.toString();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
		
	}
	
	
	
	
	/**
	 * 
	 * 
	 * 保存和更新IP控制
	 * 
	 * @return
	 */
	public String saveIpConf() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		if (account == null) {
			return "not_login";
		}
		try {
			if (ipConf.getKey() == null || ipConf.getKey().equals("")) {
				ipConf.setIpcID(serverService.getServerID("ipcID"));
				ipConf.setEnterpriseID(account.getCompanyID());
			}
			baseBeanService.update(ipConf);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";

	}
	
	
	
	/**
	 * 
	 * 删除Ip地址
	 * 
	 * @return
	 */
	public String deleteIpConf() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			baseBeanService
					.deleteBeanByKey(IpAcl.class, ipConf.getKey());
			map.put("success", "success");
		} catch (Exception e) {
			map.put("errorMsg", "delete fail");
			e.printStackTrace();
		}

		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return "success";

	}
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getXmlpath() {
		return xmlpath;
	}

	public void setXmlpath(String xmlpath) {
		this.xmlpath = xmlpath;
	}

	public BankLinkParam getBankLinkParam() {
		return bankLinkParam;
	}

	public void setBankLinkParam(BankLinkParam bankLinkParam) {
		this.bankLinkParam = bankLinkParam;
	}	

	public TransConf getTransConf() {
		return transConf;
	}

	public void setTransConf(TransConf transConf) {
		this.transConf = transConf;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public JSONObject getResultjson() {
		return resultjson;
	}

	public void setResultjson(JSONObject resultjson) {
		this.resultjson = resultjson;
	}

	public IpAcl getIpConf() {
		return ipConf;
	}

	public void setIpConf(IpAcl ipConf) {
		this.ipConf = ipConf;
	}

	public String getInnerAction() {
		return innerAction;
	}

	public void setInnerAction(String innerAction) {
		this.innerAction = innerAction;
	}

}
