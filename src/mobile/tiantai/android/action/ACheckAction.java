package mobile.tiantai.android.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import net.sf.json.JSONObject;

import com.mysl.bo.administrative.DtMycheck;
import com.mysl.bo.administrative.DtMyleave;
import com.mysl.service.RemindService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 单据审核
 * 
 * @author lou
 * 
 */
@Controller
@Scope("prototype")
public class ACheckAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private RemindService remindService;

	private PageForm pageForm;

	private InputStream excelStream;
	private String search;
	private int pageNumber;
	private String sdate;
	private String edate;
	private Object result;
	private String type;

	private String auditorstatus;
	private DtMycheck dtMycheck;
	private DtMyleave dtMyleave;
	private String id;
	private String history;

	private Object staffid; // 责任人id
	private String applyerid; // 申请人id
	private String applyername; // 申请人姓名
	private String applyorg; // 申请人部门id
	private String applyorgname; // 申请人部门名称
	private String applycompanyid; // 申请人公司id
	private String applycompanyname;// 申请人公司名称

	private String checkids;
    
	/**
	 * 审核
	 */
	private Map<String, DtMycheck> dtMycheckMap;
	private Map<String, DtMycheck> dtMycheck2Map;
	private String receiptType;

	/**
	 * 获得session
	 */
	private Map<String, Object> session = ActionContext.getContext()
			.getSession();


	private String comments;
	private String auditorid;
	private String auditororgid;
	private String auditorcompanyid;

	/**
	 * 审核有查询条件调用方法
	 * 
	 * @return
	 */
	public String toSearchByDtMycheck() {
		session.put("dtMycheck", dtMycheck);
		return getDtMycheckList();

	}

	/**
	 * 查询审核列表的方法调用
	 * 
	 * @return
	 */
	public String getDtMycheckList() {

		String sql = " from DT_MYCHECK c join DT_MYLEAVE l on c.id=l.id where c.auditorid=? and c.auditororgid=? and c.auditorcompanyid=? and c.auditorstatus=?";
		List<Object> params = new ArrayList<Object>();
		JSONObject jret = new JSONObject();
		params.add(auditorid);
		params.add(auditororgid);
		params.add(auditorcompanyid);
		params.add(auditorstatus);

		if (search != null && "search".equals(search)) {
			DtMycheck dtMycheck = (DtMycheck) session.get("dtMycheck");
			if (dtMycheck != null) {
				if (dtMycheck.getSerialnumber() != null
						&& !"".equals(dtMycheck.getSerialnumber())) {
					sql += " and serialnumber like ?";
					params.add("%" + dtMycheck.getSerialnumber().trim() + "%");// 单据编号
				}
				if (dtMycheck.getReceiptType() != null
						&& !"".equals(dtMycheck.getReceiptType())) {// 单据类型
					sql += " and receiptType =?";
					params.add(dtMycheck.getReceiptType());
				}
				if (dtMycheck.getApplyername() != null
						&& !"".equals(dtMycheck.getApplyername())) { // 申请人姓名
					sql += " and applyername like ?";
					params.add("%" + dtMycheck.getApplyername().trim() + "%");
				}

			}
		}
		sql += " order by c.addtime desc";

		pageForm = baseBeanService
				.getPageFormBySQL(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select c.checkid,c.applyername,c.applyorgname,c.applycompanyname,to_char(c.addtime,'yyyy-mm-dd'), l.leaveStartDate,"
								+ "l.leaveEndDate,l.leaveType,l.leaveReason,c.auditorname,c.auditororgname,c.auditorcompanyname"
								+ sql, "select count(*)" + sql, params
								.toArray());
        
		if (pageForm != null && pageForm.getList() != null
				&& pageForm.getList().size() > 0) {

			List<Object> list = new ArrayList<Object>();
			for (Object page : pageForm.getList()) {
				Object[] object = (Object[]) page;
				JSONObject json = new JSONObject();
				json.accumulate("checkid", object[0] == null ? "" : object[0]);
				json.accumulate("applyername", object[1] == null ? ""
						: object[1]);
				json.accumulate("applyorgname", object[2] == null ? "" : object[2]);
				json.accumulate("applycompanyname", object[3] == null ? ""
						: object[3]);
				json.accumulate("addtime", object[4] == null ? "" : object[4]);
				json.accumulate("leaveStartDate", object[5] == null ? ""
						: object[5]);
				json.accumulate("leaveEndDate", object[6] == null ? ""
						: object[6]);
				json.accumulate("leaveType", object[7] == null ? "" : object[7]);
				json.accumulate("leaveReason", object[8] == null ? ""
						: object[8]);
				json.accumulate("auditorname", object[9] == null ? ""
						: object[9]);
				json.accumulate("auditororgname", object[10] == null ? ""
						: object[10]);
				json.accumulate("auditorcompanyname", object[11] == null ? ""
						: object[11]);

				list.add(json);
			}
			jret.accumulate("list", list);
			jret.accumulate("pageNumber", pageForm.getPageNumber());
			jret.accumulate("pageSize", pageForm.getPageSize());
			jret.accumulate("result", "data");

		} else {
			jret.accumulate("result", "nodata");
		}
		result = jret;

		return "success";
	}

	/**
	 * 通过审核调用方法
	 * 
	 * @return
	 */
	public String getAudit() {
		JSONObject jret = new JSONObject();
		List<BaseBean> beans = new ArrayList<BaseBean>();
		try {
			// 驳回
			if (type.equals("03") || type.equals("02")) {
				List<Object> params = new ArrayList<Object>();
				String[] ids = checkids.split(",");
				for (String id : ids) {
					String hql = "from DtMycheck where checkid=?";
					params.add(id);
					DtMycheck dt = (DtMycheck) baseBeanService
							.getBeanByHqlAndParams(hql, params.toArray());
					dt.setAddtime(new Date());
					dt.setComments(comments);
					dt.setAuditorstatus(type);
					beans.add(dt);

				}
			}

			if (type.equals("01")) {
				String[] ids = checkids.split(",");
				for (String id : ids) {
					List<Object> params = new ArrayList<Object>();
					String hql = "from DtMycheck where checkid=?";
					params.add(id);
					DtMycheck dt = (DtMycheck) baseBeanService
							.getBeanByHqlAndParams(hql, params.toArray());

					DtMycheck dt1 = new DtMycheck();
					dt1.setCheckid(serverService.getServerID("dtMycheck"));
					dt1.setApplycompanyid(dt.getApplycompanyid());
					dt1.setApplycompanyname(dt.getApplycompanyname());
					dt1.setApplyerid(dt.getApplyerid());
					dt1.setApplyername(dt.getApplyername());
					dt1.setApplyorg(dt.getApplyorg());
					dt1.setApplyorgname(dt.getApplyorgname());
					dt1.setAddtime(new Date());
					dt1.setId(dt.getId());
					dt1.setSerialnumber(dt.getSerialnumber());
					dt1.setReceiptType(dt.getReceiptType());
					dt1.setLookOverurl(dt.getLookOverurl());
					dt1.setPrinturl(dt.getPrinturl());
					dt1.setListurl(dt.getListurl());
					dt1.setTeablename(dt.getTeablename());
					dt1.setAuditorid(dt.getAuditorid());
					dt1.setAuditorname(dt.getAuditorname());
					dt1.setAuditororgid(dt.getAuditororgid());
					dt1.setAuditororgname(dt.getAuditororgname());
					dt1.setAuditorcompanyid(dt.getAuditorcompanyid());
					dt1.setAuditorcompanyname(dt.getAuditorcompanyname());
					dt1.setAuditorstatus("01");
					dt.setAuditorstatus("02");
					beans.add(dt);
					beans.add(dt1);
				}

			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
			jret.accumulate("result", "suc");

		} catch (Exception e) {
			jret.accumulate("result", "fail");
			e.printStackTrace();
		}
		result = jret;
		return "success";
	}

	/**
	 * 查询已审核列表的方法调用checked
	 * 
	 * @return
	 */
	public String getDtMycheckedList() {

		String sql = " from DT_MYCHECK c join DT_MYLEAVE l on c.id=l.id where c.auditorid=? and c.auditororgid=? and c.auditorcompanyid=? and c.auditorstatus=?";
		List<Object> params = new ArrayList<Object>();
		JSONObject jret = new JSONObject();
		params.add(auditorid);
		params.add(auditororgid);
		params.add(auditorcompanyid);
		params.add(auditorstatus);

		if (search != null && "search".equals(search)) {
			DtMycheck dtMycheck = (DtMycheck) session.get("dtMycheck");
			if (dtMycheck != null) {
				if (dtMycheck.getSerialnumber() != null
						&& !"".equals(dtMycheck.getSerialnumber())) {
					sql += " and serialnumber like ?";
					params.add("%" + dtMycheck.getSerialnumber().trim() + "%");// 单据编号
				}
				if (dtMycheck.getReceiptType() != null
						&& !"".equals(dtMycheck.getReceiptType())) {// 单据类型
					sql += " and receiptType =?";
					params.add(dtMycheck.getReceiptType());
				}
				if (dtMycheck.getApplyername() != null
						&& !"".equals(dtMycheck.getApplyername())) { // 申请人姓名
					sql += " and applyername like ?";
					params.add("%" + dtMycheck.getApplyername().trim() + "%");
				}

			}
		}
		sql += " order by c.addtime desc";
		System.out.println(sql);
		System.out.println(sql);

		pageForm = baseBeanService
				.getPageFormBySQL(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select c.checkid,c.applyername,c.applyorg,c.applyorgname,to_char(c.addtime,'yyyy-mm-dd'), c.applycompanyname,l.leaveType,l.leaveReason,c.auditorname,c.auditororgname,c.auditorcompanyname "
								+ sql, "select count(*)" + sql, params
								.toArray());

		if (pageForm != null && pageForm.getList() != null
				&& pageForm.getList().size() > 0) {

			List<Object> list = new ArrayList<Object>();
			for (Object page : pageForm.getList()) {
				Object[] object = (Object[]) page;
				JSONObject json = new JSONObject();
				json.accumulate("checkid", object[0] == null ? "" : object[0]);
				json.accumulate("applyername", object[1] == null ? ""
						: object[1]);
				json.accumulate("applyorg", object[2] == null ? "" : object[2]);
				json.accumulate("applyorgname", object[3] == null ? ""
						: object[3]);
				json.accumulate("addtime", object[4] == null ? "" : object[4]);
				json.accumulate("applycompanyname", object[5] == null ? ""
						: object[5]);
				json.accumulate("leaveType", object[6] == null ? "" : object[6]);
				json.accumulate("leaveReason", object[7] == null ? ""
						: object[7]);
				json.accumulate("auditorname", object[8] == null ? ""
						: object[8]);
				json.accumulate("auditororgname", object[9] == null ? ""
						: object[9]);
				json.accumulate("auditorcompanyname", object[10] == null ? ""
						: object[10]);

				list.add(json);
			}
			jret.accumulate("list", list);
			jret.accumulate("pageNumber", pageForm.getPageNumber());
			jret.accumulate("pageSize", pageForm.getPageSize());
			jret.accumulate("result", "data");

		} else {
			jret.accumulate("result", "nodata");
		}
		result = jret;

		return "success";
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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

	public String getAuditorstatus() {
		return auditorstatus;
	}

	public void setAuditorstatus(String auditorstatus) {
		this.auditorstatus = auditorstatus;
	}

	public DtMycheck getDtMycheck() {
		return dtMycheck;
	}

	public void setDtMycheck(DtMycheck dtMycheck) {
		this.dtMycheck = dtMycheck;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public Map<String, DtMycheck> getDtMycheckMap() {
		return dtMycheckMap;
	}

	public void setDtMycheckMap(Map<String, DtMycheck> dtMycheckMap) {
		this.dtMycheckMap = dtMycheckMap;
	}

	public Map<String, DtMycheck> getDtMycheck2Map() {
		return dtMycheck2Map;
	}

	public void setDtMycheck2Map(Map<String, DtMycheck> dtMycheck2Map) {
		this.dtMycheck2Map = dtMycheck2Map;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	public String getApplyerid() {
		return applyerid;
	}

	public void setApplyerid(String applyerid) {
		this.applyerid = applyerid;
	}

	public String getApplyername() {
		return applyername;
	}

	public void setApplyername(String applyername) {
		this.applyername = applyername;
	}

	public String getApplyorg() {
		return applyorg;
	}

	public void setApplyorg(String applyorg) {
		this.applyorg = applyorg;
	}

	public String getApplyorgname() {
		return applyorgname;
	}

	public void setApplyorgname(String applyorgname) {
		this.applyorgname = applyorgname;
	}

	public String getApplycompanyid() {
		return applycompanyid;
	}

	public void setApplycompanyid(String applycompanyid) {
		this.applycompanyid = applycompanyid;
	}

	public String getApplycompanyname() {
		return applycompanyname;
	}

	public void setApplycompanyname(String applycompanyname) {
		this.applycompanyname = applycompanyname;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Object getStaffid() {
		return staffid;
	}

	public void setStaffid(Object staffid) {
		this.staffid = staffid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCheckids() {
		return checkids;
	}

	public void setCheckids(String checkids) {
		this.checkids = checkids;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public DtMyleave getDtMyleave() {
		return dtMyleave;
	}

	public void setDtMyleave(DtMyleave dtMyleave) {
		this.dtMyleave = dtMyleave;
	}

	public Object getAuditorid() {
		return auditorid;
	}

	public String getAuditororgid() {
		return auditororgid;
	}

	public void setAuditororgid(String auditororgid) {
		this.auditororgid = auditororgid;
	}

	public String getAuditorcompanyid() {
		return auditorcompanyid;
	}

	public void setAuditorcompanyid(String auditorcompanyid) {
		this.auditorcompanyid = auditorcompanyid;
	}

	public void setAuditorid(String auditorid) {
		this.auditorid = auditorid;
	}

}
