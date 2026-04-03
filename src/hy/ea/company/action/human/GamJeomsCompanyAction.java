package hy.ea.company.action.human;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.publicreceipts.Publicreceipts;
import hy.ea.bo.human.publicreceipts.PublicreceiptsChild;
import hy.ea.service.ShowExcelService;
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
 * 集团工作--奖罚单公司汇总
 * 
 * @author lwz
 * 
 */
@Controller
@Scope("prototype")
public class GamJeomsCompanyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private InputStream excelStream;
	private Publicreceipts publicreceipts;
	/**
	 * 导出
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		List<Object> list = getList(session, account);
		String sql = (String) list.get(0);
		String sql1 = "select "
				+ sql.substring(sql.indexOf(",") + 1, sql
						.indexOf("s.accessory"));
		sql1 = sql1.replace("end,", "end");
		sql1 += sql.substring(sql.indexOf("from") - 1);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(PublicreceiptsChild
				.columnRewardPunishment(), baseBeanService
				.getListBeanBySqlAndParams(sql1, parms));
		return "showexcel";
	}

	/**
	 * 查询
	 * 
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch", publicreceipts);
		return getGamJeomsList();
	}

	/**
	 * 加载
	 * 
	 * @return
	 */
	public String getGamJeomsList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1)"
						+ sql.substring(sql.indexOf("from")), params);
		
		return "getGamJeomsList";
	}
	/**
	 * 奖励扣分单——查询列表（可根据条件查询）被调用
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object> getList(Map<String, Object> session,
			CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> result = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		String sql = "select s.prid,c.companyname,co.organizationname,s.vouchernum,s.principal,cr.organizationname as cccc,"
				+ " s.type,s.applydate,s.operator,d.rorpDeductPoint,d.rorpDate,"
				+ " d.rorpMyriad,d.rorpReason,s.firstauditor,s.secondauditor,s.finalauditor,"
				+ " case when s.receiptsstatus='p' then '待审'"
				+ " when s.receiptsstatus='f' then '部门主管审核通过'"
				+ " when s.receiptsstatus='s' then '人力资源部审核通过'"
				+ " when s.receiptsstatus='a' then '总经理审核通过'"
				+ " when s.receiptsstatus='r' then '驳回作废'"
				+ " when s.receiptsstatus='b' then '撤销' end," + "s.accessory";
		sql += " from dtpublicreceiptschild d"
				+ " left join dtpublicreceipts s on s.prid=d.prid"
				+ " left join dtcompany c on  c.companyid = s.companyid"
				+ " left join dtcorganization co on s.principalOrganizationID=co.organizationid"
				+ " left join dtcorganization cr on  cr.organizationid = s.principalorganizationid"
				+ " where (s.type='奖励单' or s.type='扣分单')";
		sql += " and exists(select g.companyid from dtcompany" +
				" g where s.companyid =  g.companyid and (g.companyID = ? or g.companyPID = ?))";
		parms.add(companyID);
		parms.add(companyID);
		if (search != null && search.equals("search")) {
			publicreceipts = (Publicreceipts) session.get("tablesearch");
			if (publicreceipts.getType() != null
					&& !publicreceipts.getType().equals("")) {
				sql += " and s.type = ? ";
				parms.add(publicreceipts.getType());
			}
			if (publicreceipts.getVoucherNum() != null
					&& !"".equals(publicreceipts.getVoucherNum().trim())) {
				sql += " and s.voucherNum like ?";
				parms.add("%" + publicreceipts.getVoucherNum().trim() + "%");
			}
			//公司
			if (publicreceipts.getCompanyID()!= null
					&& !publicreceipts.getCompanyID().equals("")) {
				sql += " and s.companyID = ? ";
				parms.add(publicreceipts.getCompanyID());
			}
			if (!publicreceipts.getPrincipal().trim().equals("")
					&& publicreceipts.getPrincipal() != null) {
				sql += " and s.principal like ?";
				parms.add("%" + publicreceipts.getPrincipal().trim() + "%");
			}
			if (!publicreceipts.getOrganizationID().equals("")
					&& publicreceipts.getOrganizationID() != null  && !publicreceipts.getCompanyID().equals(publicreceipts.getOrganizationID())) {
				sql += " and s.organizationID = ?";
				parms.add(publicreceipts.getOrganizationID());
			}
			if (publicreceipts.getReceiptsStatus() != null
					&& !publicreceipts.getReceiptsStatus().equals("")) {
				sql += " and s.receiptsStatus = ? ";
				parms.add(publicreceipts.getReceiptsStatus());
			}
		}
		sql += " order by s.applyDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
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

	public Publicreceipts getPublicreceipts() {
		return publicreceipts;
	}

	public void setPublicreceipts(Publicreceipts publicreceipts) {
		this.publicreceipts = publicreceipts;
	}

	
}
