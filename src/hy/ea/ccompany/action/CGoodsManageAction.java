package hy.ea.ccompany.action;
//商品管理
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.GoodsManage;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.UpLoadFileService;
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

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;


@Controller
@Scope("prototype")
public class CGoodsManageAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CCodeService codeService;
	@Resource
	private CLogBookService logBookService;
	private GoodsManage goodsManage;
	private PageForm pageForm;
	private List<CCode> typelist;
	private List<CCode> variablelist;
	private List<CCode> standardslist;
	private String parameter;
	private String result;
	private int pageNumber;
	private List<BaseBean> beans;
	
	/**
	 * 物品状态：00表示正常，01表示删除
	 */
	@Resource
	private UpLoadFileService fileService;
	// 添加或修改商品管理
	public String saveGoodsManage() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		if (goodsManage.getFilePhoto() != null) {
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path, goodsManage.getFilePhotoFileName(),
					goodsManage.getFilePhoto(),companyID, "upload_files/ea/human/goodsmanage/");
					goodsManage.setPhotoPath(photoPath); 
		}
		if (null == goodsManage.getGoodsID() || "".equals(goodsManage.getGoodsID())) {
			goodsManage.setGoodsID(serverService.getServerID("goodsManage"));
			parameter = "添加商品管理(商品名称:"+goodsManage.getGoodsName()+")";
		}
		else
		{
			Object[] params = { companyID,goodsManage.getGoodsID()};
			String hql1="from GoodsManage where companyID=? and goodsID=? and goodsState ='00'";
			GoodsManage goods=(GoodsManage) baseBeanService.getBeanByHqlAndParams(hql1, params);
			parameter = "修改商品管理(商品名称:"+goods.getGoodsName()+")";
		}
		
		goodsManage.setCompanyID(companyID);
		goodsManage.setGoodsState("00");
		beans = new ArrayList<BaseBean>();
		beans.add(goodsManage);
		CLogBook logBook = logBookService.saveCLogBook(companyID, parameter, account);
		beans.add(logBook);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		return "succ";
	}
	// 删除某条商品管理
	public String delGoodsManage() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		Object[] params = {companyID, goodsManage.getGoodsID()};
		beans = new ArrayList<BaseBean>();
		String hqlForSearch="from GoodsManage where goodsState = '00' and companyID=? and goodsID=? ";		
		GoodsManage goods=(GoodsManage) baseBeanService.getBeanByHqlAndParams(hqlForSearch, params);
		CLogBook logBook = logBookService.saveCLogBook(companyID," 删除商品管理(商品名称："+ goods.getGoodsName()+")", account);		
		beans.add(logBook);
		String hqlForUpdate = "update GoodsManage set goodsState='01' where companyID = ?  and goodsID = ?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hqlForUpdate}, params);
		goods = null;
		return "succ";
	}

	// 商品管理列表
	public String getListGoodsManage() {
		String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		typelist = codeService.getCCodeListByPID(companyID,"scode20101014v5zed7cukk0000000002");
		standardslist = codeService.getCCodeListByPID(companyID,"scode20101216zgkfwy4y8p0000000002");
		variablelist = codeService.getCCodeListByPID(companyID,"scode20101014v5zed7cukk0000000003");
		Object[] params = {companyID};
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber==0?10:pageNumber),
						" from GoodsManage where companyID = ? and goodsState = '00'",
						params);
		return "list";
	}
	
	public String getListGoodsManageBygoodsCodingOrgoodsName() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = " from GoodsManage where ( goodsCoding like ? or goodsName like ? ) and companyID = ? ";
		Object[] parms = {"%"+parameter+"%","%"+parameter+"%",companyID} ;
		List<BaseBean> baseBeanList = baseBeanService.getListBeanByHqlAndParams(hql,parms );
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsManageList", baseBeanList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	public GoodsManage getGoodsManage() {
		return goodsManage;
	}
	public void setGoodsManage(GoodsManage goodsManage) {
		this.goodsManage = goodsManage;
	}
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public List<CCode> getTypelist() {
		return typelist;
	}
	public void setTypelist(List<CCode> typelist) {
		this.typelist = typelist;
	}
	public List<CCode> getVariablelist() {
		return variablelist;
	}
	public void setVariablelist(List<CCode> variablelist) {
		this.variablelist = variablelist;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
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
	public List<CCode> getStandardslist() {
		return standardslist;
	}
	public void setStandardslist(List<CCode> standardslist) {
		this.standardslist = standardslist;
	}
	
}
