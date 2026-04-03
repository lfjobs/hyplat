package hy.ea.human.action.adance;

import hy.base.action.BaseAction;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.adance.AttendWorkHour;
import hy.plat.bo.BaseBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;



/**
 * 
 * 考勤核算周期管理
 * @author lwz
 *
 */
@Controller
@Scope("prototype")
@SuppressWarnings("serial")
public class AttendWorkHourAction extends BaseAction<AttendWorkHour>{

	private AttendWorkHour workhour = this.getModel(); 
	private List<BaseBean> beans;
	private String parameter;
	private Map<String ,AttendWorkHour> workhourmap;
	
	/**
	 * 加载
	 * @return
	 */
	public String getAttendWorkHour(){
		
		beans = baseBeanService.getListBeanByHqlAndParams("from AttendWorkHour wh where wh.companyId = ? ", new Object[]{this.getCurrentAccount().getCompanyID()});
		Map<String, Object> map = new HashMap<String, Object>();
		if(beans.size() == 0){
			AttendWorkHour workhour1 = new AttendWorkHour();
			workhour1.setWorkName("01");
			map.put("workhour01", workhour1);
			AttendWorkHour workhour2 = new AttendWorkHour();
			workhour2.setWorkName("02");
			map.put("workhour02", workhour2);
			AttendWorkHour workhour3 = new AttendWorkHour();
			workhour3.setWorkName("03");
			map.put("workhour03", workhour3);
		}else{
			for(int i=0 ; i<beans.size() ; i++){
				workhour = (AttendWorkHour)beans.get(i);
				map.put("workhour"+workhour.getWorkName(), workhour);
			}
		}
		
		JSONObject oj = JSONObject.fromObject(map);
		result = oj;
		return "success";
	}
	
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		String hql = "delete from AttendWorkHour wh where wh.companyId = ?";
		List<Object[]> paramlist = new ArrayList<Object[]>();
		    Object[] param = new Object[]{this.getCurrentAccount().getCompanyID()}; 
		    paramlist.add(param);
		baseBeanService.executeHqlsByParamsList(null, new String[]{hql}, paramlist);
		beans = new ArrayList<BaseBean>();
		if(workhourmap!=null){
			for(AttendWorkHour wk : workhourmap.values()){
				wk.setWorkHourId(serverService.getServerID("workhour"));
				wk.setCompanyId(this.getCurrentAccount().getCompanyID());
				wk.setGroupCompanySn(this.getCurrentAccount().getCompany().getGroupCompanySn());
				wk.setCtime(new Date());
				wk.setCname(this.getCurrentAccount().getAccountEmail());
				beans.add(wk);
			}
			
			
		}
		parameter += "保存工作时间预设:(帐号名称:"+this.getCurrentAccount().getAccountEmail()+")";
		CLogBook logBook = logBookService.saveCLogBook(this.getOrganizationId(), parameter, this.getCurrentAccount());
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	public Map<String, AttendWorkHour> getWorkhourmap() {
		return workhourmap;
	}

	public void setWorkhourmap(Map<String, AttendWorkHour> workhourmap) {
		this.workhourmap = workhourmap;
	}

	
}