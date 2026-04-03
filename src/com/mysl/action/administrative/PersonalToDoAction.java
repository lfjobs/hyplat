package com.mysl.action.administrative;

import hy.ea.bo.CAccount;
import hy.ea.bo.Remind;
import hy.ea.bo.human.COrganization;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.mysl.bo.administrative.DtMydo;
import com.mysl.service.RemindService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 个人待办
 * @author lou
 *
 */
public class PersonalToDoAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private RemindService remindService;
	@Resource
	private ShowExcelService excelService;
	
	private PageForm pageForm;
	
	private InputStream excelStream;
	private String search;
	private int pageNumber;
	private String sdate;
	private String edate;
	private String result;
	
	private DtMydo mydo;
	private String id;
	private String doid;
	private String staffid;
	private String staffname;
	private String history;
	
	/**
	 * 获得session
	 */
	private Map<String, Object> session = ActionContext.getContext()
			.getSession();
	/**
	 * 获得account
	 */
	private CAccount account = (CAccount) ActionContext.getContext()
			.getSession().get("account");
	
	/**
	 * 审核有查询条件调用方法
	 * 
	 * @return
	 */
	public String toSearchByDtMydo() {
		session.put("mydo", mydo);
		return getDtMydoList();
		
	}
	
	/**
	 * 查询审核列表
	 * @return
	 */
	public String getDtMydoList(){
	    List<Object> result = getList();
		String hql=result.get(0).toString();
		Object[] parms = (Object[])result.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, parms);
		return "DtMydoList";
	}
	
	
	/**
	 * 查询审核列表的方法调用
	 * @return
	 */
	public List<Object> getList(){
		List<Object> result = new ArrayList<Object>();
		String hql ="from DtMydo where complyid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(account.getStaffID());
		if (search != null && "search".equals(search)) {
			DtMydo dtMydo = (DtMydo) session.get("mydo");
			if (dtMydo != null) {
				if (dtMydo.getSerialnumber() != null
						&& !"".equals(dtMydo.getSerialnumber())) {
					hql += " and serialnumber like ?";
					params.add("%" + dtMydo.getSerialnumber().trim() + "%");
				}
				if (dtMydo.getReceiptType() != null
						&& !"".equals(dtMydo.getReceiptType())) {
					hql += " and receiptType =?";
					params.add(dtMydo.getReceiptType());
				}
				if (dtMydo.getComplystatus() != null
						&& !"".equals(dtMydo.getComplystatus())) {
					hql += " and complystatus =?";
					params.add(dtMydo.getComplystatus());
				}
				if (dtMydo.getPaymentname() != null
						&& !"".equals(dtMydo.getPaymentname())) {
					hql += " and paymentname like ?";
					params.add("%" + dtMydo.getPaymentname().trim() + "%");
				}
				if (sdate != null && !"".equals(sdate) && edate != null
						&& !"".equals(edate)) {
					hql += " and paymentTime between ? and ?";
					params.add(Utilities.getDateFromString(sdate+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
					params.add(Utilities.getDateFromString(edate+" 59:59:59", "yyyy-MM-dd HH:mm:ss"));
				}
			}
		}else{
			if(history!=null&&history.equals("history")){
				hql += " and paymentTime between ? and ?";
				Calendar   c   =   Calendar.getInstance();
				c.add(Calendar.DAY_OF_MONTH, -30);
				params.add(c.getTime());
				params.add(new Date());
			}
		}
		hql += " order by complystatus,paymentTime desc";
		result.add(hql);
		result.add(params.toArray());
		return result;
	}
	
	/**
	 * 派发方法
	 */
	public String appoint(){
		if(doid!=null&&!doid.equals("")){
			List<BaseBean> params=new ArrayList<BaseBean>();
			String [] doids=doid.split(",");
			for (int i = 0; i < doids.length; i++) {
				DtMydo dMydo =(DtMydo)baseBeanService.getBeanByHqlAndParams("from DtMydo where doid=?", new Object[]{doids[i]});
				if(staffid!=null&&!staffid.equals("")){
					String [] staffids=staffid.split(",");
					String [] staffnames=staffname.split(";");
					for (int j = 0; j < staffids.length; j++) {
						String[] idorg=staffids[i].split("-");
						String dosid=idorg[0];
						String orgid=idorg[1];
						COrganization cOrganization=(COrganization)baseBeanService.getBeanByHqlAndParams("from COrganization where organizationID=?", new Object[]{orgid});
						DtMydo dtMydo=new DtMydo();
						dtMydo.setDoid(serverService.getServerID("dtMydo"));
						dtMydo.setId(dMydo.getId());
						dtMydo.setSerialnumber(dMydo.getSerialnumber());
						dtMydo.setLookOverurl(dMydo.getLookOverurl());
						dtMydo.setReceiptType(dMydo.getReceiptType());
						dtMydo.setPrinturl(dMydo.getPrinturl());
						dtMydo.setTeablename(dMydo.getTeablename());
						dtMydo.setPaymentTime(new Date());
						dtMydo.setPaymentid(dMydo.getComplyid());
						dtMydo.setPaymentname(dMydo.getComplyname());
						dtMydo.setPaymentorg(dMydo.getComplyorgid());
						dtMydo.setPaymentorgname(dMydo.getComplyorgname());
						dtMydo.setPaymentcompanyid(dMydo.getComplycompanyid());
						dtMydo.setPaymentcompanyname(dMydo.getComplycompanyname());
						dtMydo.setComplyid(dosid);
						dtMydo.setComplyname(staffnames[i]);
						dtMydo.setComplyorgid(orgid);
						dtMydo.setComplyorgname(cOrganization.getOrganizationName());
						dtMydo.setComplycompanyid(account.getCompanyID());
						dtMydo.setComplycompanyname(account.getCompanyName());
						dtMydo.setComplystatus("01");
						params.add(dtMydo);
						String []str ={"您有待办事项",dMydo.getReceiptType(),dosid,
								staffnames[i],orgid,account.getCompanyID(),"/ea/personaltodo/ea_getDtMydoList.jspa"};
						saveRemind(str);
					}
					dMydo.setComplystatus("03");
					params.add(dMydo);
				}
			}
			baseBeanService.executeHqlsByParamsList(params, null, null);
		}
		return "success";
	}
	
	/**
	 * 待办事项完成方法
	 */
	public String accomplish(){
		if(doid!=null&&!doid.equals("")){
			List<BaseBean> params=new ArrayList<BaseBean>();
			String [] doids=doid.split(",");
			List<String> slList=new ArrayList<String>();
			List<Object[]> parmsList=new ArrayList<Object[]>();
			for (int i = 0; i < doids.length; i++) {
				DtMydo dMydo =(DtMydo)baseBeanService.getBeanByHqlAndParams("from DtMydo where doid=?", new Object[]{doids[i]});
				List<BaseBean> dList=baseBeanService.getListBeanByHqlAndParams("from DtMydo where id=?", new Object[]{dMydo.getId()});
				String sql="update "+dMydo.getTeablename()+" set status=? where id=?";
				slList.add(sql);
				parmsList.add(new Object[]{"05",dMydo.getId()});
				for (int j = 0; j < dList.size(); j++) {
					DtMydo dtMydo=new DtMydo();
					dtMydo=(DtMydo)dList.get(j);
					dtMydo.setAudittime(new Date());
					dtMydo.setComplystatus("02");
					params.add(dtMydo);
				}
			}
			String [] hqls=new String[slList.size()];
			for (int j = 0; j < slList.size(); j++) {
				hqls[j]=slList.get(j);
			}
			baseBeanService.executeSqlsByParmsList(params, hqls, parmsList);
		}
		return"success";
	}
	
	/**
	 * 保存通知信息
	 * @param struta
	 * @param str
	 */
	public void saveRemind(String [] str){
		Remind remind = new Remind();
		remind.setRemindID(serverService.getServerID("remind"));
		remind.setCircularTitle(str[0]);
		remind.setCircularText(str[1]);
		remind.setStaffID(str[2]);
		remind.setStaffName(str[3]);
		remind.setOrganizationID(str[4]);
		remind.setCompanyID(str[5]);
		remind.setDetailedurl(str[6]);
		remind.setCircularType("02");
		remind.setAddDate(new Date());
		remind.setRemindStatus("01");
		remind.setRemindType("02");
		remindService.addremind(remind);
	}
	
	/**
	 * 导出
	 * @return
	 */
	public String toExportExcelByDo() {
		List<Object> result = getList();
		String hql=result.get(0).toString();
		Object[] parms = (Object[])result.get(1);
		List<BaseBean> baseBeanlist = baseBeanService.getListBeanByHqlAndParams(hql, parms);
		excelStream = excelService.showExcel(DtMydo.columnHeadings(),baseBeanlist);
		return "showexcel";
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public DtMydo getMydo() {
		return mydo;
	}

	public void setMydo(DtMydo mydo) {
		this.mydo = mydo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDoid() {
		return doid;
	}

	public void setDoid(String doid) {
		this.doid = doid;
	}

	public String getStaffid() {
		return staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
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
}
