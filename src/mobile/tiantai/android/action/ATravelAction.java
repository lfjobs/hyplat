package mobile.tiantai.android.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mysl.bo.administrative.DtMycheck;
import com.mysl.bo.administrative.DtMytravel;

@Controller
@Scope("prototype")
public class ATravelAction {
	private static final Logger logger = LoggerFactory.getLogger(ATravelAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	
	private PageForm pageForm;
	private int pageNumber;
	private String search;

	private Object result;
	private String checkids;//审核id
	
	private String companyid;
	private String organizationid;
	private String staffid;
	private String auditorid;
	private String auditororgid;
	private String auditorcompanyid;
	private String auditorstatus;
	
	private Object[] department;
	/**
	 * 出差申请单
	 */
	private DtMytravel dtMytravel;
	/**
	 * 审核单
	 */
	private DtMycheck dtMycheck;
	private String parameter;
	private String type;
	private String comments;
	/**
	 * 查询
	 * @return
	 */
	public String toSearchByTravel(){
		return getListByTravel() ;
	}
	/**
	 * 默认列表
	 * @return
	 */
	public String getListByTravel() {

		String sql = " from DT_MYTRAVEL t where t.companyid=? and t.organizationid=? and t.staffid=?";
		List<Object> params = new ArrayList<Object>();
		JSONObject jret = new JSONObject();
		params.add(companyid);
		params.add(organizationid);
		params.add(staffid);
		
		if (search != null && "search".equals(search)) {
			if (dtMytravel.getSerialnumber() != null
					&& !"".equals(dtMytravel.getSerialnumber())) {
				sql += " and serialnumber like ? ";
				params.add("%" + dtMytravel.getSerialnumber() + "%");
			}
			if (dtMytravel.getAddtime() != null
					&& !"".equals(dtMytravel.getAddtime())) {
				sql += " and addtime=? ";
				params.add(dtMytravel.getAddtime());
			}
			if (dtMytravel.getStatus() != null
					&& !"".equals(dtMytravel.getStatus())) {
				sql += " and status=? ";
				params.add(dtMytravel.getStatus());
			}
		}
		sql += " order by addtime desc";
		
		pageForm = baseBeanService
				.getPageFormBySQL(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select id,companyname,organizationname,staffname,to_char(addtime,'yyyy-mm-dd'),postName,travelcause,applyDate,key "
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
				json.accumulate("postName", object[5] == null ? "" : object[5]);
				json.accumulate("travelcause", object[6] == null ? ""
						: object[6]);
				json.accumulate("applyDate", object[7] == null ? "" : object[7]);
				json.accumulate("key", object[8] == null ? "" : object[8]);
				list.add(json);

			}
			jret.accumulate("list", list);
			jret.accumulate("pageNumber", pageForm.getPageNumber());
			jret.accumulate("pagesize", pageForm.getPageSize());
			jret.accumulate("result", "data");
		} else {
			jret.accumulate("result", "nodata");
		}
		result = jret;
		return "success";
	}
	
	/**
	 * 保存
	 * @return
	 */
	public String saveByTravel(){
		   List<BaseBean>   list=new ArrayList<BaseBean>();
		   JSONObject jret=new JSONObject();
		   List<Object> params = new ArrayList<Object>();
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
				dtMytravel.setId(serverService.getServerID("Travel"));
				parameter = "增加出差申请表(单据编号:" + "" + ")";
				dtMytravel.setStatus("01");
				dtMytravel.setAddtime(new Date());
				dtMytravel.setCompanyid(companyid);
				dtMytravel.setOrganizationid(organizationid);
				dtMytravel.setStaffid(staffid);
				dtMytravel.setCompanyname( (String) department[0]);
				dtMytravel.setOrganizationname( (String) department[1]);
				dtMytravel.setPostName( (String) department[2]);
				dtMytravel.setStaffname( (String) department[3]);
				list.add(dtMytravel);
				
				dtMycheck.setCheckid(serverService.getServerID("Travel"));
				dtMycheck.setId(dtMytravel.getId());
				dtMycheck.setReceiptType("出差申请单");
				dtMycheck.setSerialnumber(dtMytravel.getSerialnumber());
				dtMycheck.setLookOverurl("/ea/travelapply/ea_getDetailsByTravel.jspa?dtMytravel.id="+dtMycheck.getId());
				dtMycheck.setPrinturl("/ea/travelapply/ea_toPrintPreviewByTravel.jspa?dtMytravel.id="+dtMycheck.getId());
				dtMycheck.setListurl("/ea/travelapply/ea_getListByTravel.jspa?dtMytravel.staffid="+dtMytravel.getStaffid());
				dtMycheck.setTeablename("DT_MYTravel");
				dtMycheck.setAddtime(DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT, DateUtil.getCurrentDate()));
				dtMycheck.setApplyerid(dtMytravel.getStaffid());
				dtMycheck.setApplyername(dtMytravel.getStaffname());
				dtMycheck.setApplyorg(dtMytravel.getOrganizationid());
				dtMycheck.setApplyorgname(dtMytravel.getOrganizationname());
				dtMycheck.setApplycompanyid(dtMytravel.getCompanyid());
				dtMycheck.setApplycompanyname(dtMytravel.getCompanyname());
				dtMycheck.setAuditorstatus("01");
				
				list.add(dtMycheck);
				
                 baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
                 jret.accumulate("result", "suc");
			} catch (Exception e) {
				logger.error("操作异常", e);
				jret.accumulate("result", "fail");
			}
		result=jret;
		return "success";
	}
	/**
	 * 删除
	 * @return
	 */
	public String deleteByTravel(){
		JSONObject jret=new JSONObject();
       try {
			baseBeanService.deleteBeanByKey(DtMytravel.class, dtMytravel.getKey());
			 jret.accumulate("result", "suc");
		} catch (Exception e) {
			logger.error("操作异常", e);
			jret.accumulate("result", "fail");
		}	
         result=jret;
		return "success";
	}
	/**
	 * 待审核详情
	 * @return
	 */
	public String getDetailsByTravel() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String sql = " from DT_MYTRAVEL t join DT_MYCHECK c on c.id=t.id where c.auditorid=? and c.auditororgid=? and c.auditorcompanyid=? and c.auditorstatus=?";
		List<Object> params = new ArrayList<Object>();
		JSONObject jret = new JSONObject();
		params.add(auditorid);
		params.add(auditororgid);
		params.add(auditorcompanyid);
		params.add(auditorstatus);
		
		if (search != null && "search".equals(search)) {
			if (dtMytravel.getSerialnumber() != null
					&& !"".equals(dtMytravel.getSerialnumber())) {
				sql += " and serialnumber like ? ";
				params.add("%" + dtMytravel.getSerialnumber() + "%");
			}
			if (dtMytravel.getAddtime() != null
					&& !"".equals(dtMytravel.getAddtime())) {
				sql += " and addtime=? ";
				params.add(dtMytravel.getAddtime());
			}
			if (dtMytravel.getStatus() != null
					&& !"".equals(dtMytravel.getStatus())) {
				sql += " and status=? ";
				params.add(dtMytravel.getStatus());
			}
		}
		
		sql += " order by c.addtime desc";
		pageForm = baseBeanService
				.getPageFormBySQL(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select c.checkid,c.applyername,c.applyorgname,c.applycompanyname,to_char(c.addtime,'yyyy-mm-dd'),t.startDate,"
								+ "t.endDate,t.travelcause,t.postName,c.auditorname,c.auditororgname,c.auditorcompanyname"
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
				json.accumulate("applyorgname", object[2] == null ? ""
						: object[2]);
				json.accumulate("applycompanyname", object[3] == null ? "" : object[3]);
				json.accumulate("addtime", object[4] == null ? "" : object[4]);
				json.accumulate("startDate", object[5] == null ? "" : sdf.format(object[5]));
				json.accumulate("endDate", object[6] == null ? ""
						: sdf.format(object[6]));
				json.accumulate("travelcause", object[7] == null ? "" : object[7]);
				json.accumulate("postName", object[8] == null ? "" : object[8]);
				json.accumulate("auditorname", object[9] == null ? "": object[9]);
				json.accumulate("auditororgname", object[10] == null ? "": object[10]);
				json.accumulate("auditorcompanyname", object[11] == null ? "": object[11]);
				list.add(json);

			}
			jret.accumulate("list", list);
			jret.accumulate("pageNumber", pageForm.getPageNumber());
			jret.accumulate("pagesize", pageForm.getPageSize());
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
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public String getCheckids() {
		return checkids;
	}
	public void setCheckids(String checkids) {
		this.checkids = checkids;
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
	public DtMytravel getDtMytravel() {
		return dtMytravel;
	}
	public void setDtMytravel(DtMytravel dtMytravel) {
		this.dtMytravel = dtMytravel;
	}
	public DtMycheck getDtMycheck() {
		return dtMycheck;
	}
	public void setDtMycheck(DtMycheck dtMycheck) {
		this.dtMycheck = dtMycheck;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Object[] getDepartment() {
		return department;
	}
	public void setDepartment(Object[] department) {
		this.department = department;
	}
	
}

