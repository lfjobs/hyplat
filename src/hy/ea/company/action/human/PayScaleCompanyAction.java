package hy.ea.company.action.human;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.vo.CSPPayScaleVO;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * 集团工作--在职员工工资级别汇总
 * 
 * @author lwz
 * 
 */
@Controller
@Scope("prototype")
public class PayScaleCompanyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private InputStream excelStream;
	private CSPPayScaleVO payScale;

	/**
	 * 导出
	 * 
	 * @return
	 */
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		List<Object> parms = new ArrayList<Object>();
		String sql = "from CSPPayScaleVO j where exists ( select c.companyID from Company c"
				+ " where j.cspcompanyid = c.companyID and (c.companyID = ? or c.companyPID = ?)) and status = '00' ";
		parms.add(companyID);
		parms.add(companyID);
		if (search != null && search.equals("search")){
			payScale = (CSPPayScaleVO) session.get("tablesearch");
			//人员姓名
			if (payScale.getStaffName()!= null
					&& !payScale.getStaffName().trim().equals("")) {
				sql += " and j.staffName like ? ";
				parms.add("%" + payScale.getStaffName().trim() + "%");
			}
			//公司
			if (payScale.getCompanyID()!= null
					&& !payScale.getCompanyID().equals("")) {
				sql += " and j.companyID = ? ";
				parms.add(payScale.getCompanyID());
			}
			//人员编号
			if (payScale.getPosition()!= null
					&& !payScale.getPosition().trim().equals("")) {
				sql += " and j.position like ? ";
				parms.add("%" + payScale.getPosition().trim() + "%");
			}
			//职务
			if (payScale.getStaffCode()!= null
					&& !payScale.getStaffCode().trim().equals("")) {
				sql += " and j.staffCode like ? ";
				parms.add("%" + payScale.getStaffCode().trim() + "%");
			}
			//级差
			if (payScale.getScale()!= null
					&& !payScale.getScale().trim().equals("")) {
				sql += " and j.scale like ? ";
				parms.add("%" + payScale.getScale().trim() + "%");
			}
		}
		sql += " order by j.companyname,j.staffCode";
		
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql,parms.toArray());
		excelStream = excelService.showExcel(CSPPayScaleVO.columnHeadings(),list);
		return "showexcel";
	}

	/**
	 * 查询
	 * 
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch", payScale);
		return getPayScaleList();
	}

	/**
	 * 加载
	 * 
	 * @return
	 */
	public String getPayScaleList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber == 0 ? 10 : pageNumber), hql, params);
		
		return "getPayScaleList";
	}
	private List<Object> getList(Map<String, Object> session,
			CAccount account){
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		String hql = "from CSPPayScaleVO j where exists ( select c.companyID from Company c"
				+ " where j.cspcompanyid = c.companyID and (c.companyID = ? or c.companyPID = ?)) and status = '00' ";
		parms.add(companyID);
		parms.add(companyID);
		if (search != null && search.equals("search")){
			payScale = (CSPPayScaleVO) session.get("tablesearch");
			//人员姓名
			if (payScale.getStaffName()!= null
					&& !payScale.getStaffName().trim().equals("")) {
				hql += " and j.staffName like ? ";
				parms.add("%" + payScale.getStaffName().trim() + "%");
			}
			//公司
			if (payScale.getCompanyID()!= null
					&& !payScale.getCompanyID().equals("")) {
				hql += " and j.companyID = ? ";
				parms.add(payScale.getCompanyID());
			}
			//人员编号
			if (payScale.getPosition()!= null
					&& !payScale.getPosition().trim().equals("")) {
				hql += " and j.position like ? ";
				parms.add("%" + payScale.getPosition().trim() + "%");
			}
			//职务
			if (payScale.getStaffCode()!= null
					&& !payScale.getStaffCode().trim().equals("")) {
				hql += " and j.staffCode like ? ";
				parms.add("%" + payScale.getStaffCode().trim() + "%");
			}
			//级差
			if (payScale.getScale()!= null
					&& !payScale.getScale().trim().equals("")) {
				hql += " and j.scale like ? ";
				parms.add("%" + payScale.getScale().trim() + "%");
			}
		}
		hql += " order by j.companyname,j.staffCode";
		results.add(hql);
		results.add(parms.toArray());
		return results;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public CSPPayScaleVO getPayScale() {
		return payScale;
	}

	public void setPayScale(CSPPayScaleVO payScale) {
		this.payScale = payScale;
	}

	
}
