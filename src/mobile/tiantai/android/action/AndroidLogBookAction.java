package mobile.tiantai.android.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import hy.ea.bo.PersonalRelation;
import hy.ea.bo.human.LogBook;
import hy.ea.bo.human.Staff;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller
@Scope("prototype")
public class AndroidLogBookAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	private PageForm pageForm;
	private LogBook logbook;
	private String result;
	
	private Staff staff;
	
	private Map<String, Staff>  staffMap;
	/**
	 * 删除
	 * @return
	 */
	public String delLogBook(){
		
		/**
		 * logbook.getLogBookID() 工作日志id
		 */
		
		Object[] params = { logbook.getLogBookID() };
		String hql = "delete from LogBook l where l.logBookID = ? ";
		JSONObject jsonObjList = new JSONObject();
		try {
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
					new String[] { hql,hql }, params);
			jsonObjList.accumulate("result", "success");
		} catch (Exception e) {
			jsonObjList.accumulate("result", "fail");
			e.printStackTrace();
		}
		result = jsonObjList.toString();
		return "success";
	}
	/**
	 * @author zc
	 * @describe
	 * @return 0 异常  1 成功  2 已存在
	 */
	public String saveStaffAndCreateRelation(){
		JSONObject jsonObjList = new JSONObject();
		try {
			List<BaseBean> beansList=new ArrayList<BaseBean>();
			if(staff!=null){
				Staff userStaff=(Staff) baseBeanService.getBeanByHqlAndParams(" from Staff where staffID=? ", new Object[]{staff.getStaffID()});
				if(userStaff!=null){
					for(Staff s : staffMap.values()) {
						List<BaseBean> beans=baseBeanService.getListBeanByHqlAndParams(" from Staff where reference=? and staffName=?", new Object[]{s.getReference(),s.getStaffName()});
						
						if(beans!=null&&beans.size()>0){
							Staff staffOther=(Staff)beans.get(0);
							int i=baseBeanService.getConutByByHqlAndParams(" select count(*) from PersonalRelation where userid=? and friendid=? ", new Object[]{userStaff.getStaffID(),staffOther.getStaffID()});
							if(i==0){
								PersonalRelation pr=new PersonalRelation();
								pr.setId(serverService.getServerID("personalRelation"));
								pr.setUserid(userStaff.getStaffID());
								pr.setFriendid(staffOther.getStaffID());
								pr.setAddtime(new Date());
								beansList.add(pr);
								jsonObjList.accumulate("result", 1);
							}else{
								jsonObjList.accumulate("result", 2);
							}
						}else{
							Staff cstaff=new Staff();
							String phql = "select count(*) from Staff ";
							int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
							cstaff.setStaffID(serverService.getServerID("cstaff"));
							cstaff.setStaffCode("NO" + pcount);
							cstaff.setRecordCode("NO" + pcount);
							cstaff.setStaffName(s.getStaffName());
							cstaff.setReference(s.getReference());
							cstaff.setGroupCompanySn(userStaff.getGroupCompanySn());
							cstaff.setStaffStatus("00");
							cstaff.setVerifyTime(new Date());
							cstaff.setSource("安卓客户端");
							beansList.add(cstaff);
							
							PersonalRelation pr=new PersonalRelation();
							pr.setId(serverService.getServerID("personalRelation"));
							pr.setUserid(userStaff.getStaffID());
							pr.setFriendid(cstaff.getStaffID());
							pr.setAddtime(new Date());
							beansList.add(pr);
							jsonObjList.accumulate("result", 1);
						}
						
					}
					baseBeanService.saveBeansListAndexecuteHqlsByParams(beansList,null, null);
				}else{
					jsonObjList.accumulate("result", 0);
				}
				
			}else{
				jsonObjList.accumulate("result", 0);
			}
			
		} catch (Exception e) {
			jsonObjList.accumulate("result", 0);
			e.printStackTrace();
		}
		result = jsonObjList.toString();
		return "success";
	}
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public LogBook getLogbook() {
		return logbook;
	}
	public void setLogbook(LogBook logbook) {
		this.logbook = logbook;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Map<String, Staff> getStaffMap() {
		return staffMap;
	}
	public void setStaffMap(Map<String, Staff> staffMap) {
		this.staffMap = staffMap;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	
}
