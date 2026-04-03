package hy.ea.human.action.adance;

import hy.base.action.BaseAction;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.adance.AttendCycle;
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
public class AttendCycleAction extends BaseAction<AttendCycle>{

	private AttendCycle cycle = this.getModel(); 
	private List<BaseBean> beans;
	private String parameter;
	
	/**
	 * 加载
	 * @return
	 */
	public String getAttendCycle(){
		BaseBean bb = baseBeanService.getBeanByHqlAndParams("from AttendCycle c where c.companyId = ?", new Object[]{this.getCurrentAccount().getCompanyID()});
		if(bb == null){
			cycle = new AttendCycle();
			cycle.setStatus("00");
		}else{
			cycle = (AttendCycle)bb;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cycle", cycle);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj;
		
		return "success";
	}
	
	public String save(){
		String hql = "delete from AttendCycle c where c.companyId = ?";
		beans = new ArrayList<BaseBean>();
		cycle.setCycleId(serverService.getServerID("cycle"));
		cycle.setCompanyId(this.getCurrentAccount().getCompanyID());
		cycle.setGroupCompanySn(this.getCurrentAccount().getCompany().getGroupCompanySn());
		cycle.setCtime(new Date());
		cycle.setCname(this.getCurrentAccount().getAccountEmail());
		beans.add(cycle);
			
		parameter += "保存考勤周期预设:(帐号名称:"+this.getCurrentAccount().getAccountEmail()+")";
		CLogBook logBook = logBookService.saveCLogBook(this.getOrganizationId(), parameter, this.getCurrentAccount());
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, new Object[]{this.getCurrentAccount().getCompanyID()});
		
		return "success";
	}
}