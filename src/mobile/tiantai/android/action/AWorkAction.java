package mobile.tiantai.android.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.Staff;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mysl.bo.administrative.DtMycheck;
import com.mysl.bo.administrative.DtMyovertime;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class AWorkAction {
	private static final Logger logger = LoggerFactory.getLogger(AWorkAction.class);

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	
	
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private Object result;
	
	private String type;// 用于汇总
    private String checkids;// 审核id
	private String checkurl;// 审核路径
	private String comments;
	

	private String companyid;
	private String organizationid;
	private String staffid;
	private String auditorid;
	private String auditororgid;
	private String auditorcompanyid;
	private String auditorstatus;
	
	private Object[] department;
	
	private Staff staff;
	
	/**
	 * 加班申请单
	 */
	private DtMyovertime dtMyovertime;
	/**
	 * 
	 */
	private String serialnumber;
	/**
	 * 
	 */
	private DtMycheck dtMycheck;
	/**
	 * 获得account
	 */
	private CAccount account = (CAccount) ActionContext.getContext()
			.getSession().get("account");

	/**
	 * 查询
	 * 
	 * @return
	 */
	public String toSearchByOvertime() {
		return getListByOvertime();
	}

	/**
	 * 默认列表
	 * 
	 * @return
	 */
	public String getListByOvertime() {

		String sql = " from DT_MYovertime d   where d.companyid=? and d.organizationid=? and d.staffid=?";
		List<Object> params = new ArrayList<Object>();
		JSONObject jret = new JSONObject();
		params.add(companyid);
		params.add(organizationid);
		params.add(staffid);

		if (search != null && "search".equals(search)) {
			if (dtMyovertime.getSerialnumber() != null
					&& !"".equals(dtMyovertime.getSerialnumber())) {
				sql += " and serialnumber like ? ";
				params.add("%" + dtMyovertime.getSerialnumber() + "%");
			}
			if (dtMyovertime.getAddtime() != null
					&& !"".equals(dtMyovertime.getAddtime())) {
				sql += " and addtime=? ";
				params.add(dtMyovertime.getAddtime());
			}
			if (dtMyovertime.getStatus() != null
					&& !"".equals(dtMyovertime.getStatus())) {
				sql += " and status=? ";
				params.add(dtMyovertime.getStatus());
			}
		}
		sql += " order by addtime desc";

		pageForm = baseBeanService
				.getPageFormBySQL(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						" select id,companyname,organizationname,staffname,to_char(addtime,'yyyy-mm-dd'),overTimePostName,overTimeSort,overTimeReason,key"
								+ sql, "select count(*)" + sql, params
								.toArray());

		if (pageForm != null && pageForm.getList() != null
				&& pageForm.getList().size() > 0) {
			List<Object> list = new ArrayList<Object>();
			for (Object page : pageForm.getList()) {
				Object[] object = (Object[]) page;
				JSONObject json = new JSONObject();
				json.accumulate("id", object[0] == null ? "" : object[0]);
				json.accumulate("companyname", object[1] == null ? ""
						: object[1]);
				json.accumulate("organizationname", object[2] == null ? ""
						: object[2]);
				json.accumulate("staffname", object[3] == null ? "" : object[3]);
				json.accumulate("addtime", object[4] == null ? "" : object[4]);
				json.accumulate("overTimePostName", object[5] == null ? ""
						: object[5]);
				json.accumulate("overTimeSort", object[6] == null ? ""
						: object[6]);
				json.accumulate("overTimeReason", object[7] == null ? ""
						: object[7]);
				json.accumulate("key", object[8] == null ? "" : object[8]);

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
	 * 保存
	 * 
	 * @return
	 */
	public String saveByOvertime() {
		List<BaseBean> list = new ArrayList<BaseBean>();
		List<Object> params = new ArrayList<Object>();
		JSONObject jret = new JSONObject();
		try {
			String sql = "select dc.companyname,c.organizationname,d.postname,s.staffname"
					+ " from dtcos t"
					+ " left join dtcorganization c on t.organizationid = c.organizationid"
					+ " left join dt_hr_deptpost d on t.deppostid = d.deppostid"
					+ " left join dtcompany dc on dc.companyid = t.companyid"
					+ " left join dt_hr_staff s on s.staffid = t.staffid"
					+ " where t.organizationid=? and t.companyid = ? and t.staffid = ? and t.cosstatus = '50' and t.status = '01'";
			params.add(organizationid);
			params.add(companyid);
			params.add(staffid);
			department = (Object [])baseBeanService.getObjectBySqlAndParams(sql,params.toArray());
			dtMyovertime.setId(serverService.getServerID("overtime"));
			parameter = "增加加班申请单(单据编号:" + "" + ")";
			dtMyovertime.setStatus("01");
			dtMyovertime.setAddtime(new Date());
			dtMyovertime.setCompanyid(companyid);
			dtMyovertime.setOrganizationid(organizationid);
			dtMyovertime.setStaffid(staffid);
			dtMyovertime.setCompanyname( (String) department[0]);
			dtMyovertime.setOrganizationname( (String) department[1]);
			dtMyovertime.setOverTimePostName( (String) department[2]);
			dtMyovertime.setStaffname( (String) department[3]);
			list.add(dtMyovertime);

			dtMycheck.setCheckid(serverService.getServerID("overtime"));
			dtMycheck.setId(dtMyovertime.getId());
			dtMycheck.setReceiptType("加班申请单");
			dtMycheck.setSerialnumber(dtMyovertime.getSerialnumber());
			dtMycheck
					.setLookOverurl("/ea/overtime/ea_getDetailsByOvertime.jspa?dtMytravel.id="
							+ dtMycheck.getId());
			dtMycheck
					.setPrinturl("/ea/overtime/ea_toPrintPreviewByOvertime.jspa?dtMytravel.id="
							+ dtMycheck.getId());
			dtMycheck
					.setListurl("/ea/overtime/ea_getListByOvertime.jspa?dtMytravel.staffid="
							+ dtMyovertime.getStaffid());
			dtMycheck.setTeablename("DT_MYOVERTIME");
			dtMycheck.setAddtime(DateUtil.parseDate(
					DateUtil.C_DATE_PATTON_DEFAULT, DateUtil.getCurrentDate()));
			dtMycheck.setApplyerid(dtMyovertime.getStaffid());
			dtMycheck.setApplyername(dtMyovertime.getStaffname());
			dtMycheck.setApplyorg(dtMyovertime.getOrganizationid());
			dtMycheck.setApplyorgname(dtMyovertime.getOrganizationname());
			dtMycheck.setApplycompanyid(dtMyovertime.getCompanyid());
			dtMycheck.setApplycompanyname(dtMyovertime.getCompanyname());
		    dtMycheck.setAuditorstatus("01");
			list.add(dtMycheck);

			baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null,
					null);
			jret.accumulate("result", "suc");
		} catch (Exception e) {
			logger.error("操作异常", e);
			jret.accumulate("result", "fail");
		}
		result = jret;
        
		return "success";
	}

	/**
	 * 查看待审核详情
	 * 
	 * @return
	 */
	public String getDetailsByOvertime() {
		String sql = " from DT_MYovertime o join DT_MYCHECK c on o.id=c.id where c.auditorid=? and c.auditororgid=? and c.auditorcompanyid=? and c.auditorstatus=?";
		List<Object> params = new ArrayList<Object>();
		JSONObject jret = new JSONObject();
		params.add(auditorid);
		params.add(auditororgid);
		params.add(auditorcompanyid);
		params.add(auditorstatus);

		if (search != null && "search".equals(search)) {
			if (dtMyovertime.getSerialnumber() != null
					&& !"".equals(dtMyovertime.getSerialnumber())) {
				sql += " and serialnumber like ? ";
				params.add("%" + dtMyovertime.getSerialnumber() + "%");
			}
			if (dtMyovertime.getAddtime() != null
					&& !"".equals(dtMyovertime.getAddtime())) {
				sql += " and addtime=? ";
				params.add(dtMyovertime.getAddtime());
			}
			if (dtMyovertime.getStatus() != null
					&& !"".equals(dtMyovertime.getStatus())) {
				sql += " and status=? ";
				params.add(dtMyovertime.getStatus());
			}
		}
		sql += " order by c.addtime desc";

		pageForm = baseBeanService
				.getPageFormBySQL(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select c.checkid,c.applyername,c.applyorgname,c.applycompanyname,to_char(c.addtime,'yyyy-mm-dd'),o.overTimeStartDate,"
								+ "o.overTimeEndDate,o.overTimeReason,o.overTimeSort,c.auditorname,c.auditororgname,c.auditorcompanyname,o.overTimeContent"
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
				json.accumulate("c.applyorgname", object[2] == null ? "" : object[2]);
				json.accumulate("applycompanyname", object[3] == null ? ""
						: object[3]);
				json.accumulate("addtime", object[4] == null ? "" : object[4]);
				json.accumulate("overTimeStartDate", object[5] == null ? ""
						: object[5]);
				json.accumulate("overTimeEndDate", object[6] == null ? ""
						: object[6]);
				json.accumulate("overTimeReason", object[7] == null ? "" : object[7]);
				json.accumulate("overTimeSort", object[8] == null ? ""
						: object[8]);
				json.accumulate("auditorname", object[9] == null ? ""
						: object[9]);
				json.accumulate("auditororgname", object[10] == null ? ""
						: object[10]);
				json.accumulate("auditorcompanyname", object[11] == null ? ""
						: object[11]);
				json.accumulate("overTimeContent", object[12] == null ? ""
						: object[12]);
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
	 * 通过待审核状态（01 待审核 02 已审核 03 驳回）
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
			logger.error("操作异常", e);
		}
		result = jret;
		return "success";
	}

	
	/**
	 * 删除
	 * 
	 * @return
	 */
	public String deleteByOvertime() {
		JSONObject jret = new JSONObject();
		try {
			baseBeanService.deleteBeanByKey(DtMyovertime.class,dtMyovertime.getKey());
			jret.accumulate("result", "suc");
		} catch (Exception e) {
			logger.error("操作异常", e);
			jret.accumulate("result", "fail");
		}
		result = jret;
		return "success";
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

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public DtMycheck getDtMycheck() {
		return dtMycheck;
	}

	public void setDtMycheck(DtMycheck dtMycheck) {
		this.dtMycheck = dtMycheck;
	}

	public String getCheckids() {
		return checkids;
	}

	public void setCheckid(String checkids) {
		this.checkids = checkids;
	}

	public String getCheckurl() {
		return checkurl;
	}

	public void setCheckurl(String checkurl) {
		this.checkurl = checkurl;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getStaffid() {
		return staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getAuditorid() {
		return auditorid;
	}

	public void setAuditorid(String auditorid) {
		this.auditorid = auditorid;
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

	public String getAuditorstatus() {
		return auditorstatus;
	}

	public void setAuditorstatus(String auditorstatus) {
		this.auditorstatus = auditorstatus;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setCheckids(String checkids) {
		this.checkids = checkids;
	}

	public DtMyovertime getDtMyovertime() {
		return dtMyovertime;
	}

	public void setDtMyovertime(DtMyovertime dtMyovertime) {
		this.dtMyovertime = dtMyovertime;
	}

	

	public Object[] getDepartment() {
		return department;
	}

	public void setDepartment(Object[] department) {
		this.department = department;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}
    
	
}
