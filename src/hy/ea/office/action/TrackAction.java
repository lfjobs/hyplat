package hy.ea.office.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Track;
import hy.ea.bo.human.TrackRelation;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;

public class TrackAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	public InputStream excelStream;
	private String parameter;
	private PageForm pageForm;
	private int pageNumber;
	private Track track;
	private String foreignKeyID;
	private String trackrelationID;
	private Map<String, Track> tracksmap;
	private String status;
	private String search;
	private String sdate;
	private String edate;
	private List<BaseBean> connectionlist;
	
	/**
	 * 条件查询（保存条件）
	 * 
	 * @return
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("track", track);
		return getTrackById();
	}

	/**
	 * 条件查询
	 * 
	 * @return
	 */
	public String getTrackById() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(Track.class);
		dc.add(Restrictions.eq("trackrelationID", trackrelationID));
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		if (search != null && search.equals("search")) {
			track = (Track) session.get("track");
			if (sdate != null && !("").equals(sdate)) {
				dc.add(Restrictions.eq("trackStartdate", Utilities
						.getDateFromString(sdate, "yyyy-MM-dd")));
			}
			if (edate != null && !("").equals(edate)) {
				dc.add(Restrictions.eq("tarckEnddate", Utilities
						.getDateFromString(edate, "yyyy-MM-dd")));
			}
			if (track.getServiceMode() != null
					&& !"".equals(track.getServiceMode())) {
				dc.add(Restrictions.eq("serviceMode", track.getServiceMode()));
			}
			if (track.getHandleStatus() != null
					&& !"".equals(track.getHandleStatus())) {
				dc
						.add(Restrictions.eq("handleStatus", track
								.getHandleStatus()));
			}
		}
		dc.addOrder(Order.desc("inputDate"));
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber), dc);
		return "tracklist";
	}

	/**
	 * 保存/修改
	 * 
	 * @return
	 */
	public String trackSave() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (tracksmap != null) {
			List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
			for (Track track2 : tracksmap.values()) {
				track2.setCompanyID(account.getCompanyID());
				if (track2.getTrackID() == null) {
					track2.setTrackID(serverService.getServerID("track"));
					CLogBook logBook = logBookService.saveCLogBook(
							organizationID, "添加责任人为" + account.getStaffName()
									+ "往来个人咨询跟做信息", account);
					baseBeansList.add(logBook);
				} else {
					CLogBook logBook = logBookService.saveCLogBook(
							organizationID, "修改责任人为" + account.getStaffName()
									+ "往来个人咨询跟做信息", account);
					baseBeansList.add(logBook);
				}
				track2.setInputDate(new Date());

				baseBeansList.add(track2);
			}

			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,
					null, null);
		}
		return "success";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delTrack() {
		String[] hqls = { "delete Track where trackID=?" };
		List<Object[]> parmsList = new ArrayList<Object[]>();
		Object[] parms = { track.getTrackID() };
		parmsList.add(parms);
		baseBeanService.executeHqlsByParamsList(null, hqls, parmsList);
		return "success";
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
		List<Object> parms = new ArrayList<Object>();
		String sql = "select tr.inputdate,tr.trackstartdate,tr.tarckenddate,tr.workaddr,"
				+ " case when tr.servicemode='00' then '面谈'"
				+ " when tr.servicemode='01' then '电话'"
				+ " when tr.servicemode='02' then '邮件'"
				+ " when tr.servicemode='03' then '登门拜访'"
				+ " when tr.servicemode='04' then '暗访' end,"
				+ " tr.trackcontent,tr.trackreason,"
				+ " case when tr.handlestatus='00' then '处理'"
				+ " when tr.handlestatus='01' then '未处理' end"
				+ " from dttrack tr"
				+ " left join dttrackrelation t on t.trackrelationid=tr.trackrelationid"
				+ " left join dt_hr_staff cs on cs.staffid = t.foreignkeyid"
				+ " left join dt_hr_staff cu on  cu.staffid = t.staffid "
				+ " left join dtcompany c on  c.companyid = tr.companyid"
				+ " where tr.trackrelationID=?";
		parms.add(trackrelationID);
		if (search != null && search.equals("search")) {
			track = (Track) session.get("track");
			if (sdate != null && !("").equals(sdate)) {
				sql += " and tr.trackstartdate = ?";
				parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
			}
			if (edate != null && !("").equals(edate)) {
				sql += " and tr.tarckenddate = ?";
				parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
			}
			if(track != null){
				if (track.getServiceMode() != null
						&& !"".equals(track.getServiceMode())) {
					sql += " and tr.servicemode = ?";
					parms.add(track.getServiceMode());
				}
				if (track.getHandleStatus() != null
						&& !"".equals(track.getHandleStatus())) {
					sql += " and tr.handlestatus = ?";
					parms.add(track.getHandleStatus());
				}
			}
		}
		sql += " order by tr.inputdate desc";
		excelStream = excelService
				.showExcel(track.columnHeadings3(), baseBeanService
						.getListBeanBySqlAndParams(sql, parms.toArray()));
		 CLogBook logBook = logBookService.saveCLogBook(organizationID,
		 "导出责任人为" + account.getStaffName() + "往来个人咨询跟做信息", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
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

	public String getForeignKeyID() {
		return foreignKeyID;
	}

	public void setForeignKeyID(String foreignKeyID) {
		this.foreignKeyID = foreignKeyID;
	}

	public Map<String, Track> getTracksmap() {
		return tracksmap;
	}

	public void setTracksmap(Map<String, Track> tracksmap) {
		this.tracksmap = tracksmap;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<BaseBean> getConnectionlist() {
		return connectionlist;
	}

	public void setConnectionlist(List<BaseBean> connectionlist) {
		this.connectionlist = connectionlist;
	}

	public String getTrackrelationID() {
		return trackrelationID;
	}

	public void setTrackrelationID(String trackrelationID) {
		this.trackrelationID = trackrelationID;
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

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSearch() {
		return search;
	}

}
