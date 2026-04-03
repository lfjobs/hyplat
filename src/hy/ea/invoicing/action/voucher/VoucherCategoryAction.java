package hy.ea.invoicing.action.voucher;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import hy.ea.bo.CAccount;
import hy.ea.bo.invoicing.voucher.DtInvVoutype;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;


import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.sun.star.text.SetVariableType;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * 凭证类别
 * 赵先杰
 * @author Administrator
 */
@Controller
@Scope("prototype")
public class VoucherCategoryAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	private PageForm pageForm;
	private List<BaseBean> list;
	private String abbreviation;
	private String name;
	private String code;
	private Map<String,DtInvVoutype> invt;
	private String companyId;
	private String result;
	private int pageNumber;
	private int search;
	
	/**
	 * 获取主页面信息
	 */	
	public String getVoucherCategory(){
		CAccount account=(CAccount) ActionContext.getContext().getSession().get("account");
		Map<String,Object> map=list(account.getCompanyID());
		String hql=(String)map.get("hql");
		Object[] obj=(Object[])map.get("obj");
		pageForm=baseBeanService.getPageForm((pageForm==null?1:pageForm.getPageNumber()),pageNumber==0?10:pageNumber, hql,obj);

		return "categoryList";	
	}
	
	
	/**
	 * 
	 * ajax获取主页面信息
	 */
	public String getVoucherCategorys(){
		CAccount account=(CAccount) ActionContext.getContext().getSession().get("account");
		String hql="from DtInvVoutype where VTComid=?";
		Object[] obj={account.getCompanyID()};
		list=baseBeanService.getListBeanByHqlAndParams(hql, obj);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("list",list);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}
	
	/** 
	 * 添加类别信息
	 * @throws UnsupportedEncodingException 
	 */
	
	public String addVoucherCategory() throws UnsupportedEncodingException{
		CAccount account=(CAccount) ActionContext.getContext().getSession().get("account");		
		for(Entry<String, DtInvVoutype> en:invt.entrySet()){
			DtInvVoutype dt=en.getValue();
			dt.setVTId(serverService.getServerID("DtInvVoutype"));
			dt.setVTComid(account.getCompanyID());
			dt.setVTCid(account.getStaffID());
			dt.setVTCname(account.getStaffName());
			dt.setVTCtime(Utilities.getDateString(new Date(),"yyyyMMddHHmmss"));
			baseBeanService.save(dt);
		}
		return "success";
	}
	
	/**
	 * 删除类别信息
	 */
	public String delVoucherCategory(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id=request.getParameter("vtId");
		String[] hql={"delete DtInvVoutype where VTId=?"};
		Object[] obj={id};
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, hql,obj);
		return "success";
	}

	/**
	 * 修改类别信息
	 * @throws UnsupportedEncodingException 
	 */
	public String updVoucherCategory() throws UnsupportedEncodingException{
		CAccount account=(CAccount) ActionContext.getContext().getSession().get("account");
		HttpServletRequest request = ServletActionContext.getRequest();
		String id=request.getParameter("vtId");
		for(Entry<String, DtInvVoutype> en:invt.entrySet()){
			DtInvVoutype dt=en.getValue();
			if(!id.equals(dt.getVTId())){
				continue;
			}
			String[] hql={"update DtInvVoutype set VTDh=?,VTJc=?,VTName=?" +
				",VTDs=?,VTCs=?,VTPd=?,VTR=?,VTUid=?,VTUname=?,VTUtime=? where VTId=?"};
			Object[] obj={dt.getVTDh(),dt.getVTJc(),dt.getVTName(),dt.getVTDs(),dt.getVTCs(),dt.getVTPd(),dt.getVTR(),
					account.getStaffID(),account.getStaffName(),Utilities.getDateString(new Date(),"yyyyMMddHHmmss"),dt.getVTId()};
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null, hql,obj);
		}
		
		return "success";
	}

	/**
	 * 获取查询语句和参数
	 */
	public Map<String, Object> list(String comId){
		String hql="from DtInvVoutype where VTComid=?";
		List<Object> list=new ArrayList<Object>();
		list.add(comId);
		if(code!=null&&!"".equals(code)){
			hql+=" and VTDh like ?";
			list.add("%"+code+"%");
		}
		if(abbreviation!=null&&!"".equals(abbreviation)){
			hql+=" and VTJc like ?";
			list.add("%"+abbreviation+"%");
		}
		if(name!=null&&!"".equals(name)){
			hql+=" and VTName like ?";
			list.add("%"+name+"%");
		}
		hql+=" ORDER BY VTCtime";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("hql", hql);
		map.put("obj",list.toArray());
		return map;
	}
	/**
	 * ajax判断登录人员的账号是否是'sa'
	 */
	public String authority(){
		CAccount account=(CAccount) ActionContext.getContext().getSession().get("account");
		String authority;
		if(!"sa".equals(account.getAccountEmail())){
			authority="not";		
		}else{
			authority="yes";
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("authority",authority);
		JSONObject obj=JSONObject.fromObject(map);
		result=obj.toString();
		return "success";
	}
	
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public List<BaseBean> getList() {
		return list;
	}
	public void setList(List<BaseBean> list) {
		this.list = list;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getSearch() {
		return search;
	}

	public void setSearch(int search) {
		this.search = search;
	}
	public Map<String, DtInvVoutype> getInvt() {
		return invt;
	}
	public void setInvt(Map<String, DtInvVoutype> invt) {
		this.invt = invt;
	}

}
