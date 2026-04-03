package hy.ea.ccompany.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class CCCodeAction {
	@Resource
	private CCodeService codeService;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private CCode code;
	private String codeID;
	private List<CCode> codeList;
	private String result;
	private PageForm pageForm;
	private String childrenID;
	private String selectedtreeID;
	private String parmiter;
	private List<CCode> children;
	private String sub;
	private String parameter;
	private int pageNumber;
	private List<BaseBean> beans;

	
	/**
	 * 进入代码树管理
	 * 
	 * @return
	 */
	public String ccodeManage() {
		ActionContext.getContext().getSession().put("session_value",
				Math.random() + "");// 在不使用token的情况下,手动防止重复提交;
		return "ccode_manager";
	}

	/**
	 * 添加或修改一家公司的代码
	 */
	public String saveCCode() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String companyID = (String) ActionContext.getContext().getSession()
				.get("companyID");
		if (null == code.getCodeID() || "".equals(code.getCodeID())) {
			code.setCodeID(serverService.getServerID("ccode"));
			code.setCodeStatus("01");
			parameter = "添加代码";
			parameter += "(代码名称:" + code.getCodeValue() + ")";
		} else {
			parameter = "修改代码";
			parameter += "(代码名称:"
					+ codeService.getCCodeByID(companyID, code.getCodeID())
							.getCodeValue() + ")";
			if (code.getCodeStatus().equals("98"))
				code.setCodeStatus("01");
		}
		codeID = code.getCodeID();
		if (null == parmiter || "".equals(parmiter)) {
			codeID = code.getCodePID();
		}

		code.setCompanyID(companyID);
		beans = new ArrayList<BaseBean>();
		beans.add(code);
		CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
		beans.add(logBook);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		return getCodeListAll();
	}

	/**
	 * 删除一家公司的代码
	 */
	public String delCCode() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String companyID = (String) ActionContext.getContext().getSession()
				.get("companyID");
		CLogBook logBook = logBookService.saveCLogBook(null, "删除代码(代码名称："+ codeService.getCCodeByID(companyID, codeID).getCodeValue()+ ")", account);
		String resultCode = codeService.getCodeList(account.getCompanyID(), codeID, "");
		resultCode += codeID;
		String[] codeArr = resultCode.split("-");
		beans = new ArrayList<BaseBean>();
		beans.add(logBook);
		List<Object[]> parms = new ArrayList<Object[]>();
		String hql="";
		for (int i = 0; i < codeArr.length; i++) {
			hql += "update  CCode set codeStatus = '98' where companyID = ? and codeID = ?-";
			parms.add(new Object[]{account.getCompanyID(),codeArr[i]});
		}
		String[] hqls =hql.split("-");
		baseBeanService.executeHqlsByParamsList(beans, hqls, parms);
		//codeService.deleteCCodeByID(companyID, codeID,logBook);
		if (null == parmiter || "".equals(parmiter)) {
			return "codelist";
		}
		codeID = parmiter;
		return getCodeListAll();
	}

	/**
	 * 根据codeID查看代码的详细信息
	 */
	public String editCCode() {
		String companyID = (String) ActionContext.getContext().getSession()
				.get("companyID");
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		code = (CCode) codeService.getCCodeByID(companyID, codeID);
		Map<String, CCode> map = new HashMap<String, CCode>();
		map.put("code", code);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/**
	 * 根据codeID跳转到修改code的页面
	 */
	public String toEditCCode() {
		String companyID = (String) ActionContext.getContext().getSession()
				.get("companyID");
		code = (CCode) codeService.getCCodeByID(companyID, code.getCodePID());
		return "to_set";
	}

	/**
	 * 根据companyID和codeID查询其子节点
	 */
	public String getListCCodeByPID() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String companyID = (String) ActionContext.getContext().getSession()
				.get("companyID");
		List<CCode> codeList = codeService.getCCodeListByPID(companyID, codeID);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codeList", codeList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 根据companyID和codeID查询其子节点
	 */
	public String getAllListCCodeByPID() {
		String companyID = (String) ActionContext.getContext().getSession()
				.get("companyID");
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List<CCode> olist = new ArrayList<CCode>();
		List<CCode> codeList = codeService.getAllCCodeListByPID(companyID,
				codeID, olist);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codeList", codeList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 去排序子机构页面
	 */
	public String toSortChildCode() {
		String companyID = (String) ActionContext.getContext().getSession()
				.get("companyID");
		children = codeService.getCCodeListByPID(companyID, codeID);
		return "sortchildren";
	}

	/**
	 * 排序子机构
	 * 
	 * @author zg
	 * @verson 2011-4-19
	 */
	public String sortChildCode() {
		String[] ids = childrenID.split("_");
		String hql = "update CCode set codeNumber = ? where codeID = ?";
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			hql += "update CCode set codeNumber = " + i + " where codeID = '"
					+ id + "'_";
		}
		String[] hqls = hql.substring(0, hql.length()-1).split("_");
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, hqls, null);
		return getCodeListAll();
	}

	public String resetCode() {
		// 由于IFrame框架引入的新页面中带有token, 这里手工做防重复提交
		ActionContext actionContext = ActionContext.getContext();
		String companyID = (String) ActionContext.getContext().getSession()
				.get("companyID");
		Map<String, Object> session = actionContext.getSession();
		String obj = (String) session.get("session_value");
		if (sub != null && sub.equals(obj)) {
			codeService.upadateCodeToCCode(companyID);
		}
		return "codelist";
	}

	/**
	 * 递归查询出下面所有的子集代码 lwt
	 */
	public String getCodeListAll() {
		String companyID = (String) ActionContext.getContext().getSession()
				.get("companyID");
		Object[] params = { companyID, codeID };
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						" from CCode where companyID = ? and codePID = ? order by codeNumber",
						params);
		return "codelist";
	}

	/**
	 * ajax新添ccode
	 * 
	 * @return
	 */
	public String saveCCodeByAjax() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String companyID = (String) ActionContext.getContext().getSession()
				.get("companyID");
		parameter = "添加代码";
		parameter += "(代码名称:" + code.getCodeValue() + ")";
		code.setCodeID(serverService.getServerID("ccode"));
		code.setCodeStatus("01");
		code.setCompanyID(companyID);
		beans = new ArrayList<BaseBean>();
		beans.add(code);
		CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
		beans.add(logBook);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		code = (CCode) codeService.getCCodeByID(companyID, code.getCodeID());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
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
	public String getParmiter() {
		return parmiter;
	}

	public void setParmiter(String parmiter) {
		this.parmiter = parmiter;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getChildrenID() {
		return childrenID;
	}

	public void setChildrenID(String childrenID) {
		this.childrenID = childrenID;
	}

	public String getSelectedtreeID() {
		return selectedtreeID;
	}

	public void setSelectedtreeID(String selectedtreeID) {
		this.selectedtreeID = selectedtreeID;
	}

	public List<CCode> getChildren() {
		return children;
	}

	public void setChildren(List<CCode> children) {
		this.children = children;
	}

	public List<CCode> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<CCode> codeList) {
		this.codeList = codeList;
	}

	public CCode getCode() {
		return code;
	}

	public void setCode(CCode code) {
		this.code = code;
	}

	public String getCodeID() {
		return codeID;
	}

	public void setCodeID(String codeID) {
		this.codeID = codeID;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
