package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.office.Meeting;
import hy.ea.human.service.COrganizationService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.FileUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
/**
 * 现场会议
 * @author Administrator
 *
 */
public class MeetingAction {
	
	@Resource
	private COrganizationService organizationService;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;  
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private CLogBookService logBookService; 
	@Resource
	private ShowExcelService excelService;
	
	private Meeting meeting;
	private String parameter; 
	private PageForm pageForm;
	private List<COrganization> orgnizationList;
	private String search; 
	private int pageNumber;
	 
	private String downLoadPath;
	private InputStream excelStream;
	 
	private String sDate;
	private String eDate;
	
	//保存会议信息
	public String saveMeeting(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) map.get("organizationID");
		
		//保存文件
		if(null!=meeting.getMtFile()){
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String filePath = fileService.savePhoto(path, meeting.getMtFileFileName(),
					           meeting.getMtFile(), account.getCompanyID(), "/office/meeting/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			meeting.setMtFilePath(filePath);
		}
		//保存其他数据
		if(null==meeting.getMeetingID()||"".equals(meeting.getMeetingID())){
			meeting.setMeetingID(serverService.getServerID("meeting"));
			parameter = "添加现场会议(会议名:"+meeting.getMtName()+")";
		}else{
			String hql = "from Meeting where companyID = ? and  meetingID = ? ";
			Meeting q0 = (Meeting) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),meeting.getMeetingID()});
			parameter = "修改会议信息(会议名:"+q0.getMtName()+")";
		} 
		List<BaseBean> beans=new ArrayList<BaseBean>();
		meeting.setCompanyID(companyID);
		meeting.setOrganizationID(organizationID);
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(meeting);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	//删除会议信息
	 public String delMeeting()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID"); 
			
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),meeting.getMeetingID()};
		    String hql2="from Meeting where companyID=?  and meetingID = ? ";
		    Meeting q0=(Meeting) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除会议信息(会议名:"+q0.getMtName()+")", account);
	    	String[] hql={"delete from Meeting where companyID=?  and meetingID = ?"};
	    	List<BaseBean> beans=new ArrayList<BaseBean>();
	    	beans.add(logbook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
			
		    return "success";
	    }
	 
	 //根据条件查询现场会议列表
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", meeting);
			return getMeetingList();
		}
	 //现场会议列表
		public String getMeetingList() {
		    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			return "meetinglist";	
		}
		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String companyID = account.getCompanyID();
			
			Map<String,String> ccmap = new HashMap<String,String>();
			orgnizationList =	organizationService.getOrganizationList(organizationID,companyID);
			COrganization    organization=organizationService.getOrganization(companyID, organizationID);
			if(null!=orgnizationList){
				for(COrganization o:orgnizationList){  
					ccmap.put(o.getOrganizationID(), o.getOrganizationName());
				}
				
			}
			ccmap.put(organization.getOrganizationID(), organization.getOrganizationName());
			ccmap.put("00", "未完成");
			ccmap.put("01", "已完成");
			Meeting.setOMap(ccmap);
			
			DetachedCriteria dc = DetachedCriteria.forClass(Meeting.class);
			dc.add(Restrictions.eq("companyID", companyID));
			dc.add(Restrictions.eq("organizationID", organizationID));
			if (search != null && search.equals("search")) {
				meeting = (Meeting) session.get("tablesearch");
				if(null!=meeting.getMtOrganizationID()&&!"".equals(meeting.getMtOrganizationID()))
				{
					dc.add(Restrictions.eq("mtOrganizationID",meeting.getMtOrganizationID()));
				} 
				if(null!=meeting.getMtName()&&!"".equals(meeting.getMtName()))
				{
					dc.add(Restrictions.like("mtName", meeting.getMtName(), MatchMode.ANYWHERE));
				} 
				if(null!=meeting.getMtStatus()&&!"".equals(meeting.getMtStatus()))
				{
					dc.add(Restrictions.eq("mtStatus", meeting.getMtStatus()));
				}
				if(null!=sDate&&!"".equals(sDate)&&null!=eDate&&!"".equals(eDate)){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						dc.add(Restrictions.between("mtDate", sdf.parse(sDate), sdf.parse(eDate)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} 
			} 
			return dc;
		}
		
		// 导出现场会议
		public String showMeetingExcel() {
			excelStream = excelService.showExcel(Meeting.columnHeadings(), baseBeanService.getListByDC(getList()));
			CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
			CLogBook  logbook=logBookService.saveCLogBook(null,"现场会议列表", account);
			baseBeanService.update(logbook);
			return "showexcel";
		}	 

		//下载文件
		public void downFile(){ 
			FileUtil fu = new FileUtil();
			try {
				fu.downFile(downLoadPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		public Meeting getMeeting() {
			return meeting;
		}
		public void setMeeting(Meeting meeting) {
			this.meeting = meeting;
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
		public InputStream getExcelStream() {
			return excelStream;
		}
		public void setExcelStream(InputStream excelStream) {
			this.excelStream = excelStream;
		}
		public String getDownLoadPath() {
			return downLoadPath;
		}
		public void setDownLoadPath(String downLoadPath) {
			this.downLoadPath = downLoadPath;
		}
		public String getSDate() {
			return sDate;
		}
		public void setSDate(String date) {
			sDate = date;
		}
		public String getEDate() {
			return eDate;
		}
		public void setEDate(String date) {
			eDate = date;
		}
		public List<COrganization> getOrgnizationList() {
			return orgnizationList;
		}
		public void setOrgnizationList(List<COrganization> orgnizationList) {
			this.orgnizationList = orgnizationList;
		} 
		
}
