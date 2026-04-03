package hy.ea.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.util.Constant;
import hy.ea.util.ToChineseFirstLetter;
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
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class CCodeAction {
	@Resource
	private CCodeService codeService;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private List<BaseBean> beans;
	private CCode code;
	private String codeID;
	private String codeName;
	private List<CCode> codeList;
	private String result;
	private PageForm pageForm;
	private String childrenID;
	private String selectedtreeID;
	private String  parmiter;
	private List<CCode> children;
	private String sub ;
	private String parameter;
	private int pageNumber;
	private List<CCode> Codebill;
	
	
	public String getCodeNum(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			this.result = obj.toString();
			return "success";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String hql ="select count(codeNumber) from CCode where companyID = ?";
		int count = baseBeanService.getConutByByHqlAndParams(hql, 
				new Object[]{account.getCompanyID()});
		if(count == 0){
			map.put("maxNum", 0001);
		}else{
			String maxhql ="select max(codeNumber) from CCode where companyID = ?";
			int maxcount = baseBeanService.getConutByByHqlAndParams(maxhql, 
					new Object[]{account.getCompanyID()});
			//判断maxcount+1是否为4位数字，若不足前补0
			if(Integer.toString(maxcount+1).length()>3){
				map.put("maxNum", maxcount+1);
			}else{
				DecimalFormat myformat = new DecimalFormat();   // 格式化对象的类
	            myformat.applyPattern("0000");				    // 修改格式模板
	            map.put("maxNum", myformat.format(maxcount+1)); // 格式化数字
	            
			}
		}
		JSONObject obj = JSONObject.fromObject(map);
		this.result = obj.toString();
		return "success";
	}
	
	/**
	 * 进入代码树管理
	 * 
	 * @return
	 */
	public String ccodeManage() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		ActionContext.getContext().getSession().put("session_value",
				Math.random() + "");// 在不使用token的情况下,手动防止重复提交;
		HttpServletRequest req=ServletActionContext.getRequest();
		String basic =req.getParameter("basic");
		code = (CCode) codeService.getCCodeByID(account.getCompanyID(), basic);
		if(code!=null&&"004".equals(code.getCodePID())){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(basic,code.getCodeValue());
			JSONObject oj = JSONObject.fromObject(map);
			ActionContext.getContext().put("basicMap", oj.toString());
		}
		else{
			ActionContext.getContext().put("basicMap", JSONObject.fromObject(Constant.BASIC_CONFIG).toString());
		}
		ActionContext.getContext().put("basic", basic);
		ActionContext.getContext().put("codeHylb", Constant.CODE_HYLB);
		return "ccode_manager";
	}
	/**
	 * 进入基础数据设置
	 * @return
	 */
	public String basicManage() {
		ActionContext.getContext().getSession().put("session_value",
				Math.random() + "");// 在不使用token的情况下,手动防止重复提交;
		return "basic_manager";
	}
	
	/**
	 * 五部门分类导航显示
	 * @return
	 */
	public String ccodeFive(){
		ActionContext.getContext().getSession().put("session_value",
				Math.random() + "");// 在不使用token的情况下,手动防止重复提交;
		codeName = Constant.CCODES_FIVE.get(codeID).toString();
		return "ccodes_five";
	}
	
	/**
	 * 前台ajax添加往来关系
	 * @return
	 */
	public String ajaxSavaCCode(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		code.setCodeID(serverService.getServerID("ccode"));
		code.setCodeStatus("01");
		parameter = "添加代码";
		parameter += "(代码名称:"+code.getCodeValue()+")";
		code.setCompanyID(account.getCompanyID());
		CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
		beans = new ArrayList<BaseBean>();
		beans.add(code);
		beans.add(logBook);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}
	public String updateParent() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{"update CCode u set u.codePID=? where u.codeID=? and u.companyID=?"}, new Object[]{parmiter,code.getCodeID(),account.getCompanyID()});
		return findOtherList();
	}
	/**
	 * 添加或修改一家公司的代码
	 */
	public String saveCCode() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (null == code.getCodeID() || "".equals(code.getCodeID())) {
			code.setCodeID(serverService.getServerID("ccode"));
			code.setCodeStatus("01");
			parameter = "添加代码";
			parameter += "(代码名称:"+code.getCodeValue()+")";
		}else{
			parameter = "修改代码";
			parameter += "(代码名称:"+codeService.getCCodeByID(account.getCompanyID(), code.getCodeID()).getCodeValue()+")";
			if(code.getCodeStatus().equals("98"))code.setCodeStatus("01");
		}
		codeID = code.getCodeID();
		if(null == parmiter || "".equals(parmiter)){
			codeID = code.getCodePID();
		}
		
		code.setCompanyID(account.getCompanyID());
		CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
		beans = new ArrayList<BaseBean>();
		beans.add(code);
		beans.add(logBook);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		return getCodeListAll();
	}

	/**
	 * 删除一家公司的代码
	 */
	public String delCCode() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		CLogBook logBook = logBookService.saveCLogBook(null,"删除代码(代码名称："+ codeService.getCCodeByID(account.getCompanyID(), codeID).
				getCodeValue()+")", account);
		//codeService.deleteCCodeByID(account.getCompanyID(),codeID,"");
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
		if(null == parmiter || "".equals(parmiter)){
			return "codelist";
		}
		
		codeID = parmiter;
		return getCodeListAll();
	}
	
	/**
	 * 判断后台代码名称（codeValue）是否唯一
	 * @return
	 */
	public String isCodeValue(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		String log = "";
		String hql = "from CCode where companyID = ? and codeValue = ?";
		CCode ccode = (CCode)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),code.getCodeValue()});
		if(ccode != null){
			log = "log";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("log", log);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/**
	 * 根据codeID查看代码的详细信息
	 */
	public String editCCode() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		code = (CCode) codeService.getCCodeByID(account.getCompanyID(), codeID);
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
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		code = (CCode) codeService.getCCodeByID(account.getCompanyID(), code.getCodePID());
		return "to_set";
	}

	/**
	 * 根据companyID和codeID查询其子节点
	 */
	public String getListCCodeByPID() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List<CCode> codeList = codeService.getCCodeListByPID(account
				.getCompanyID(), codeID);
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
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List<CCode> olist = new ArrayList<CCode>();
		List<CCode> codeList = codeService.getAllCCodeListByPID(account.getCompanyID(), codeID, olist);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codeList", codeList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	/**
	 * 查询车品牌
	 */
	public String carModel(){
		StringBuilder sql = new StringBuilder();
		sql.append("select cc.codevalue from dtccode cc where cc.codepid = ?");//ccode20170731G9XSY2G4HM0000000055
		List<BaseBean> carTypeList = baseBeanService.getListBeanBySqlAndParams(sql.toString(),new Object[]{"ccode20170808AV5CZVIYFU0000000016"});
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("carTypeList", carTypeList);
		JSONObject obj=JSONObject.fromObject(map);
		result=obj.toString();
		return "success";
	}
	/**
	 * 查询该公司的培训车型
	 */
	public String getTrainModel(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		String sql = "select p.* from dt_schprocategory p where p.companyid = ?";
		List<BaseBean> carTypeList = baseBeanService.getListBeanBySqlAndParams(sql.toString(),new Object[]{account.getCompanyID()});
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("carTypeList", carTypeList);
		JSONObject obj=JSONObject.fromObject(map);
		result=obj.toString();
		return "success";
	}
	/**
	 * 去排序子机构页面
	 */
	public String toSortChildCode() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		children = codeService
				.getCCodeListByPID(account.getCompanyID(), codeID);
		return "sortchildren";
	}

	/**
	 * 排序子机构
	 */
	public String sortChildCode() {
		List<BaseBean> beans = new ArrayList<BaseBean>();
		String[] ids = childrenID.split("_");
		int i = 0;
		String[] hqls = new String[ids.length];
		List<Object[]> parms = new ArrayList<Object[]>();
		String hql = "update CCode set codeNumber = ? where codeID = ?";
		for (String id : ids) {
			hqls[i] = hql;
			Object[] params = { i++, id };
			parms.add(params);
		}
		baseBeanService.executeHqlsByParamsList(beans, hqls, parms);
		return getCodeListAll();
	}
	/**
	 * 升级
	 * @return
	 */
	public String resetCode() {
		//由于IFrame框架引入的新页面中带有token, 这里手工做防重复提交
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		String obj = (String) session.get("session_value");
		if (sub != null && sub.equals(obj)){
			CAccount account = (CAccount) session.get("account");
			codeService.upadateCodeToCCode(account.getCompanyID());
		}
		return "codelist";
	}
	/**
	 * 递归查询出下面所有的子集代码
	 * lwt
	 */
	public String getCodeListAll(){
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		Object [] params = {account.getCompanyID(),codeID};
		code = (CCode) codeService.getCCodeByID(account.getCompanyID(), codeID);
		if(code!=null){
			CCode codep = (CCode) codeService.getCCodeByID(account.getCompanyID(), code.getCodePID());
			if("004".equals(code.getCodePID())||"scode20150815wygb79q82p0000000005".equals(code.getCodePID())||"ccode20150906AVBHKRVRN80000000002".equals(code.getCodePID())){
				result="1";
			}
			if(codep!=null){
				if("scode20150815wygb79q82p0000000005".equals(codep.getCodePID())){
					result="1";
				}
			}
		}
		if("004".equals(codeID)||"scode20150815wygb79q82p0000000005".equals(codeID)){
			result="1";
		}
		Codebill = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20101101dfs3uhdprp0000000029");
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber():1), (pageNumber==0?10:pageNumber), " from CCode where companyID = ? and codePID = ? order by codeNumber",params);
		
		return "codelist";
	}
	/**
	 * other page
	 * @return
	 */
	public String findOtherList() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		Object [] params = {account.getCompanyID(),codeID};
		code = (CCode) codeService.getCCodeByID(account.getCompanyID(), codeID);
		Codebill = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20101101dfs3uhdprp0000000029");
		ActionContext.getContext().put("basicMap", Constant.BASIC_CONFIG);
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber():1), (pageNumber==0?10:pageNumber), " from CCode where companyID = ? and codePID = ? order by codeNumber",params);
		return "otherList";
	}
	/**
	 * ajax新添ccode
	 * @return
	 */
	public String saveCCodeByAjax() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		Map<String, Object> map = new HashMap<String, Object>();
		if(account == null){
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String Initials=ToChineseFirstLetter.getFirstLetter(code.getCodeValue());
		String hql="from CCode where codePID=? and companyID=?";
		List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{code.getCodePID(),account.getCompanyID()});
		String bool="";
		JSONObject obj = null;
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				CCode cCode=(CCode)list.get(i);
				String Initials2=ToChineseFirstLetter.getFirstLetter(cCode.getCodeValue());
				if(Initials.equals(Initials2)){
					map.put("alert", "名称为'"+code.getCodeValue()+"'的代码名称不可用，请重新输入");
					obj=JSONObject.fromObject(map);
					result = obj.toString();
					bool="1";
					break;
				}
			}
		}
		if(!bool.equals("1")){
			parameter = "添加代码";
			parameter += "(代码名称:"+code.getCodeValue()+")";
			code.setCodeID(serverService.getServerID("ccode"));
			code.setCodeStatus("01");
			code.setCompanyID(account.getCompanyID());
			CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
			beans = new ArrayList<BaseBean>();
			beans.add(code);
			beans.add(logBook);
			baseBeanService.executeHqlsByParamsList(beans, null, null);
			code = (CCode) codeService.getCCodeByID(account.getCompanyID(), code.getCodeID());
			map.put("code", code);
			map.put("alert", "");
			JSONObject oj = JSONObject.fromObject(map);
			result = oj.toString();
		}
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

	public List<CCode> getCodebill() {
		return Codebill;
	}

	public void setCodebill(List<CCode> codebill) {
		Codebill = codebill;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

}
