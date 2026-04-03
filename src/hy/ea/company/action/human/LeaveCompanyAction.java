package hy.ea.company.action.human;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.publicreceipts.Publicreceipts;
import hy.ea.bo.human.publicreceipts.PublicreceiptsChild;
import hy.ea.service.CLogBookService;
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
 * 请假单公司汇总
 * @author zl
 *
 */
@Controller
@Scope("prototype")
public class LeaveCompanyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	
	private Publicreceipts publicreceipts;
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	public InputStream excelStream;
	
	/**
	 * 员工请假单公司汇总——根据条件查询(保存条件)
	 * @return
	 */
	public String toLeaveCompanySearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", publicreceipts);
		return getLeaveCompanyList();
	}

	/**
	 * 员工请假单公司汇总——列表
	 * @return
	 */
	public String getLeaveCompanyList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		List<Object> list = LeaveList(session, account);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1)"
						+ sql.substring(sql.indexOf("from")), params);
		return "leaveBillslist";
	}

	/**
	 * 员工请假单公司汇总——查询列表（可根据条件查询）被调用
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object> LeaveList(Map<String, Object> session,
			CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		String sql = "select s.prid,c.companyname,cr.organizationname as cccc,s.vouchernum,s.principal,"
				+ " s.type,s.applydate,s.operator,d.leavePostName,d.leaveDays,d.leaveHour,d.leavecasual,"
				+ " d.leavesick,d.checkdiscount,d.leaveStartDate,d.Signdate,d.Terminatedate,"
				+ " d.leaveEndDate,d.leaveReceiver, d.leaveType,d.leaveReason,s.firstauditor,s.secondauditor,"
				+ " case when s.receiptsstatus='P' then '待审'"
				+ " when s.receiptsstatus='F' then '部门主管审核通过'"
				+ " when s.receiptsstatus='S' then '人力资源部审核通过'"
				+ " when s.receiptsstatus='A' then '总经理审核通过'"
				+ " when s.receiptsstatus='R' then '驳回作废'"
				+ " when s.receiptsstatus='B' then '撤销' end,"
				+ " s.accessory"
				+ " from dtpublicreceiptschild d"
				+ " left join dtpublicreceipts s on s.prid=d.prid"
				+ " left join dtcompany c on  c.companyid = s.companyid"
				+ " left join dtcorganization cr on  cr.organizationid = s.principalorganizationid";
		sql += " where 1=1";

		sql += " and s.type=?";
		parms.add("员工请假申请单");
		sql += " and s.companyID=?";
		parms.add(companyID);

		if (search != null && search.equals("search")) {
			publicreceipts = (Publicreceipts) session.get("tablesearch");
			if (publicreceipts.getReceiptsStatus() != null
					&& !publicreceipts.getReceiptsStatus().equals("")) {
				sql += " and s.receiptsStatus = ? ";
				parms.add(publicreceipts.getReceiptsStatus());
			}
			if (!publicreceipts.getPrincipal().trim().equals("")
					&& publicreceipts.getPrincipal() != null) {
				sql += " and s.principal like ?";
				parms.add("%" + publicreceipts.getPrincipal().trim() + "%");
			}
			if (!publicreceipts.getPrincipalOrganizationID().equals("")
					&& publicreceipts.getPrincipalOrganizationID() != null) {
				sql += " and s.principalOrganizationID=?";
				parms.add(publicreceipts.getPrincipalOrganizationID());
			}
			if (publicreceipts.getVoucherNum() != null
					&& !"".equals(publicreceipts.getVoucherNum().trim())) {
				sql += " and s.voucherNum like ?";
				parms.add("%" + publicreceipts.getVoucherNum().trim() + "%");
			}
		}
		sql += " order by s.applyDate desc";
		results.add(sql);
		results.add(parms.toArray());
		return results;
	}

	/**
	 * 员工请假单公司汇总导出
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showLeaveCompanyExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		List<Object> list = LeaveList(session, account);
		String sql = (String) list.get(0);
		String sql1 = "select "
				+ sql.substring(sql.indexOf(",") + 1, sql
						.indexOf("s.accessory"));
		sql1 = sql1.replace("end,", "end");
		sql1 += sql.substring(sql.indexOf("from") - 1);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(PublicreceiptsChild
				.columnLeaveHeadings(), baseBeanService
				.getListBeanBySqlAndParams(sql1, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出员工请假单公司汇总", account);
		baseBeanService.update(logBook);
		return "showleaveexcel";
	}

	public Publicreceipts getPublicreceipts() {
		return publicreceipts;
	}

	public void setPublicreceipts(Publicreceipts publicreceipts) {
		this.publicreceipts = publicreceipts;
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
}
