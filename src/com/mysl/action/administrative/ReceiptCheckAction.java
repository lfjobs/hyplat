package com.mysl.action.administrative;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import hy.ea.bo.CAccount;
import hy.ea.bo.Remind;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;

import com.mysl.bo.administrative.DtMycheck;
import com.mysl.bo.administrative.DtMydo;
import com.mysl.service.RemindService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 单据审核
 * @author lou
 *
 */
public class ReceiptCheckAction {
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
	
	private String auditorstatus;
	private DtMycheck dtMycheck;
	private String id;
	private String history;
	
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
	public String toSearchByDtMycheck() {
		session.put("dtMycheck", dtMycheck);
		return getDtMycheckList();
		
	}
	
	/**
	 * 查询审核列表
	 * @return
	 */
	public String getDtMycheckList(){
	    List<Object> result = getList();
		String hql=result.get(0).toString();
		Object[] parms = (Object[])result.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, parms);
		return "DtMycheckList";
	}
	
	
	/**
	 * 查询审核列表的方法调用
	 * @return
	 */
	public List<Object> getList(){
		List<Object> result = new ArrayList<Object>();
		String hql ="from DtMycheck where auditorid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(account.getStaffID());
		if (auditorstatus != null && !"".equals(auditorstatus)) {
			hql += " and auditorstatus=?";
			params.add(auditorstatus);
		}
		//后加的
		if (receiptType != null && !"".equals(receiptType)) {
			hql += " and receiptType=?";
			params.add(receiptType);
		}
		if (search != null && "search".equals(search)) {
			DtMycheck dtMycheck = (DtMycheck) session.get("dtMycheck");
			if (dtMycheck != null) {
				if (dtMycheck.getSerialnumber() != null
						&& !"".equals(dtMycheck.getSerialnumber())) {
					hql += " and serialnumber like ?";
					params.add("%" + dtMycheck.getSerialnumber().trim() + "%");
				}
				if (dtMycheck.getReceiptType() != null
						&& !"".equals(dtMycheck.getReceiptType())) {
					hql += " and receiptType =?";
					params.add(dtMycheck.getReceiptType());
				}
				if (dtMycheck.getApplyername() != null
						&& !"".equals(dtMycheck.getApplyername())) {
					hql += " and applyername like ?";
					params.add("%" + dtMycheck.getApplyername().trim() + "%");
				}
				if (sdate != null && !"".equals(sdate) && edate != null
						&& !"".equals(edate)) {
					hql += " and addtime between ? and ?";
					params.add(Utilities.getDateFromString(sdate+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
					params.add(Utilities.getDateFromString(edate+" 59:59:59", "yyyy-MM-dd HH:mm:ss"));
				}
			}
		}else{
			if(history!=null&&history.equals("history")){
				hql += " and addtime between ? and ?";
				Calendar   c   =   Calendar.getInstance();
				c.add(Calendar.DAY_OF_MONTH, -30);
				params.add(c.getTime());
				params.add(new Date());
			}
		}
		hql += " order by addtime desc";
		result.add(hql);
		result.add(params.toArray());
		return result;
	}
	
	/**
	 * 审核调用方法
	 * 
	 * @return
	 */
	public String audit() {
		List<BaseBean> beans = new ArrayList<BaseBean>();
		String tabName="";
		List<String> slList=new ArrayList<String>();
		
		List<Object[]> parmsList=new ArrayList<Object[]>();
		for (DtMycheck dma : dtMycheck2Map.values()) {
			DtMycheck dma1=(DtMycheck) baseBeanService.getBeanByHqlAndParams("from DtMycheck where checkid=?", new Object[]{dma.getCheckid()});
			tabName=dma1.getTeablename();
			for (DtMycheck dmd1 : dtMycheckMap.values()) {
				String auditorstatus=dmd1.getAuditorstatus();
				if (auditorstatus != null && "01".equals(auditorstatus)) {
					DtMycheck dmd=new DtMycheck();
					dmd.setCheckid(serverService.getServerID("dtMycheck"));
					dmd.setApplycompanyid(dma1.getApplycompanyid());
					dmd.setApplycompanyname(dma1.getApplycompanyname());
					dmd.setApplyerid(dma1.getApplyerid());
					dmd.setApplyername(dma1.getApplyername());
					dmd.setApplyorg(dma1.getApplyorg());
					dmd.setApplyorgname(dma1.getApplyorgname());
					dmd.setAddtime(new Date());
					dmd.setId(dma1.getId());
					dmd.setSerialnumber(dma1.getSerialnumber());
					dmd.setReceiptType(dma1.getReceiptType());
					dmd.setLookOverurl(dma1.getLookOverurl());
					dmd.setPrinturl(dma1.getPrinturl());
					dmd.setListurl(dma1.getListurl());
					dmd.setTeablename(dma1.getTeablename());
					dmd.setAuditorid(dmd1.getAuditorid());
					dmd.setAuditorname(dmd1.getAuditorname());
					dmd.setAuditororgid(dmd1.getAuditororgid());
					dmd.setAuditororgname(dmd1.getAuditororgname());
					dmd.setAuditorcompanyid(dmd1.getAuditorcompanyid());
					dmd.setAuditorcompanyname(dmd1.getAuditorcompanyname());
					dmd.setAuditorstatus("01");
					beans.add(dmd);
					dma1.setAuditorstatus("02");
					//提醒
					String []str ={"审核管理待办","您个人单据审核管理模块有一个待审核任务，请及时处理！",dmd1.getAuditorid(),
							dmd1.getAuditorname(),dmd1.getAuditororgid(),dmd1.getAuditorcompanyid(),"/ea/receiptcheck/ea_getDtMycheckList.jspaauditorstatus=02"};
					saveRemind(str);
				}else{
					String upsql="update "+tabName+" set status=? where id=?";
					slList.add(upsql);
					if (auditorstatus != null && "02".equals(auditorstatus)){
						String []str ={"您有待办事项待处理",dma1.getReceiptType()+"，请及时处理！",dma1.getApplyerid(),
								dma1.getApplyername(),dma1.getApplyorg(),dma1.getApplycompanyid(),"/ea/personaltodo/ea_getDtMydoList.jspa"};
						saveRemind(str);
						SaveDo(dma1,beans);
					}else{
						String []str ={"您有单据待处理",dma1.getReceiptType()+"被驳回，请及时处理！",dma1.getAuditorid(),
								dma1.getAuditorname(),dma1.getAuditororgid(),dma1.getAuditorcompanyid(),dma1.getListurl()};
						saveRemind(str);
					}
					Object[] parms={auditorstatus.equals("02")?"04":auditorstatus,dma1.getId()};
					parmsList.add(parms);
					dma1.setAuditorstatus(auditorstatus);
				}
				dma1.setAudittime(new Date());
				dma1.setComments(dmd1.getComments());
				beans.add(dma1);
			}
		}
		String [] hqls=new String[slList.size()];
		for (int j = 0; j < slList.size(); j++) {
			hqls[j]=slList.get(j);
		}
		baseBeanService.executeSqlsByParmsList(beans, hqls, parmsList);
		return "success";
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
	 * 保存指派信息
	 */
	public void SaveDo(DtMycheck dtMycheck,List<BaseBean> beans){
		DtMydo dtMydo=new DtMydo();
		dtMydo.setDoid(serverService.getServerID("dtMydo"));
		dtMydo.setId(dtMycheck.getId());
		dtMydo.setSerialnumber(dtMycheck.getSerialnumber());
		dtMydo.setLookOverurl(dtMycheck.getLookOverurl());
		dtMydo.setReceiptType(dtMycheck.getReceiptType());
		dtMydo.setPrinturl(dtMycheck.getPrinturl());
		dtMydo.setTeablename(dtMycheck.getTeablename());
		dtMydo.setPaymentTime(new Date());
		dtMydo.setPaymentid(dtMycheck.getApplyerid());
		dtMydo.setPaymentname(dtMycheck.getApplyername());
		dtMydo.setPaymentorg(dtMycheck.getApplyorg());
		dtMydo.setPaymentorgname(dtMycheck.getApplyorgname());
		dtMydo.setPaymentcompanyid(dtMycheck.getApplycompanyid());
		dtMydo.setPaymentcompanyname(dtMycheck.getApplycompanyname());
		dtMydo.setComplyid(dtMycheck.getApplyerid());
		dtMydo.setComplyname(dtMycheck.getApplyername());
		dtMydo.setComplyorgid(dtMycheck.getApplyorg());
		dtMydo.setComplyorgname(dtMycheck.getApplyorgname());
		dtMydo.setComplycompanyid(dtMycheck.getApplycompanyid());
		dtMydo.setComplycompanyname(dtMycheck.getApplycompanyname());
		dtMydo.setComplystatus("01");
		beans.add(dtMydo);
	}
	
	/**
	 * 导出
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String toExportExcelByCheck() {
		List<Object> result = getList();
		String hql=result.get(0).toString();
		Object[] parms = (Object[])result.get(1);
		List<BaseBean> baseBeanlist = baseBeanService.getListBeanByHqlAndParams(hql, parms);
		excelStream = excelService.showExcel(dtMycheck.columnHeadings(),
				baseBeanlist);
		return "showexcel";
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


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
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
	
}
