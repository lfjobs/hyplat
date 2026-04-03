package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Track;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionContext;

public class CollectPersonalAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	private PageForm pageForm;
	private int pageNumber;
	private Track track;
	private String staffname;
	private String search;
	private String sdate;
	private String edate;
	
	/**
	 * 保存查询条件
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("track", track);
		return getTracklist();
	}
	
	/**
	 * 查询列表
	 * @return
	 */
	public String getTracklist() {
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);
		return "list";
	}
	
	/**
	 * 通用查询
	 * @return
	 */
	public List<Object> getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		String sql = "select t.trackid,c.companyname,co.organizationname,cu.staffname as staname,"
				+ " tr.contactconnections,cs.staffname,cs.reference,sc.districtname,"
				+ " t.inputdate,t.trackstartdate,t.tarckenddate,t.workaddr,"
				+ " case when t.servicemode='00' then '面谈'"
				+ " when t.servicemode='01' then '电话'"
				+ " when t.servicemode='02' then '邮件'"
				+ " when t.servicemode='03' then '登门拜访'"
				+ " when t.servicemode='04' then '暗访' end,"
				+ " t.trackcontent,t.trackreason,"
				+ " case when t.handlestatus='00' then '处理'"
				+ " when t.handlestatus='01' then '未处理' end"
				+ " from dttrack t"
				+ " left join dttrackrelation tr on tr.trackrelationid=t.trackrelationid"
				+ " left join dt_hr_staff cs on cs.staffid = tr.foreignkeyid"
				+ " left join dt_hr_staff cu on  cu.staffid = tr.staffid"
				+ " left join dtsdistrict sc on sc.districtid = cs.address"
				+ " left join dtcompany c on  c.companyid = tr.companyid"
				+ " left join dtcorganization co on  co.organizationid = tr.organizationid"
				+ " where tr.status=?";
		parms.add("01");
		sql += " and tr.companyid=?";
		parms.add(account.getCompanyID());
		sql += " and tr.staffid=?";
		parms.add(account.getStaffID());
		if (search != null && search.equals("search")) {
			track = (Track) session.get("track");
			if (staffname != null && !"".equals(staffname.trim())) {
				sql += " and cs.staffname like ?";
				parms.add("%" + staffname.trim() + "%");
			}
			if (sdate != null && !("").equals(sdate)) {
				sql += " and t.trackstartdate = ?";
				parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
			}
			if (edate != null && !("").equals(edate)) {
				sql += " and t.tarckenddate = ?";
				parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
			}
			if (track.getServiceMode() != null
					&& !"".equals(track.getServiceMode())) {
				sql += " and t.servicemode = ?";
				parms.add(track.getServiceMode());
			}
			if (track.getHandleStatus() != null
					&& !"".equals(track.getHandleStatus())) {
				sql += " and t.handlestatus = ?";
				parms.add(track.getHandleStatus());
			}
		}
		sql += " order by t.inputdate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	
	/**
	 * 导出
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getList();
		String sql = (String) list.get(0);
		sql = "select " + sql.substring(sql.indexOf(",") + 1);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(track.columnHeadings1(),
				baseBeanService.getListBeanBySqlAndParams(sql, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出责任人为"
				+ account.getStaffName() + "往来个人咨询跟做信息", account);
		baseBeanService.update(logBook);
		return "showexcel";
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

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

}
