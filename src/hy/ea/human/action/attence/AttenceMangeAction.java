package hy.ea.human.action.attence;


import hy.ea.bo.CAccount;
import hy.ea.bo.finance.BenDis.Sign;
import hy.ea.human.service.AttenceManageService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;

import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author mz
 *
 */
public class AttenceMangeAction {
    @Resource
	private AttenceManageService attenceService;
	private PageForm pageForm;
	private int pageNumber;
	private String parameter;
	private String signType;
	private String start;
	private String end;
	private InputStream excelStream;
	@Resource
	private ShowExcelService excelService;

	/**
	 * 
	 * 考勤组列表
	 * @return
	 */
	public String getAttenceGroupList(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account==null){
			return "nologin";
		}

		pageForm = attenceService.getAttenceGroupList((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ?15 : pageNumber), parameter,account.getCompanyID());
		
		return "grouplist";
		
	}
	
	/**
	 * 
	 * 考勤记录汇总
	 * @return
	 */
	public String getAttenceRcordPage(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account==null){
			return "nologin";
		}
		pageForm = attenceService.getAttenceRecordPage((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ?30 : pageNumber), signType, start, end, parameter, account.getCompanyID());
		
		return "recordlist";
	}
	/**
	 * 
	 * 查询考勤记录
	 * @return
	 */
	public String toSearchRecord(){
		
		return getAttenceRcordPage();
		
	}
	/**
	 * 
	 * 导出excel
	 * @return
	 */
	public String excelExport(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		excelStream = excelService.showExcel(Sign.columnHeadings(),
				attenceService.getAttenceRecordList(signType,start,end,parameter,account.getCompanyID()));
	
		return "excelcate";
	}

	private String result;


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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public AttenceManageService getAttenceService() {
		return attenceService;
	}

	public void setAttenceService(AttenceManageService attenceService) {
		this.attenceService = attenceService;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}


	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	
	
    
}
