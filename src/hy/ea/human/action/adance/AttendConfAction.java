package hy.ea.human.action.adance;

import hy.base.action.BaseAction;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.adance.AttendConf;
import hy.ea.service.CCodeService;
import hy.ea.util.Constant;
import hy.plat.bo.BaseBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;



/**
 * 
 * 考勤项目预设
 * @author lwz
 *
 */
@Controller
@Scope("prototype")
@SuppressWarnings("serial")
public class AttendConfAction extends BaseAction<AttendConf>{
	@Resource
	private CCodeService codeService;
	private AttendConf conf = this.getModel(); 
	private List<BaseBean> beans;
	private String parameter;
	
	
	/**
	 * 加载
	 * @return
	 */
	public String getAttendConf(){
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber==0?20:pageNumber), getList());
		if(null == pageForm || pageForm.getList().size() == 0){
			preinstall();
		}else{
			return "list";
		}
		return null;
	}
	
	private DetachedCriteria getList(){
		DetachedCriteria dc=DetachedCriteria.forClass(AttendConf.class);
		dc.add(Restrictions.eq("companyId", this.getCurrentAccount().getCompanyID()));
		dc.addOrder(Order.asc("confstus"));
		return dc;
	}
	
	/**
	 * 验证唯一项目名称
	 * @return
	 */
	public String valConfName(){
		
		String contHql = " select count(*) from AttendConf c where c.companyId = ? and c.confname = ?";
		int i = baseBeanService.getConutByByHqlAndParams(contHql, new Object[]{this.getCurrentAccount().getCompanyID(),conf.getConfname().trim()});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("suc",i);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj;
		return "success";
	}
	
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		beans= new ArrayList<BaseBean>(); 
		if(conf.getAttendConfId().equals("")){
			conf.setAttendConfId(serverService.getServerID("conf"));
			conf.setCompanyId(this.getCurrentAccount().getCompanyID());
			conf.setCtime(new Date());
			conf.setCname(this.getCurrentAccount().getAccountEmail());
			conf.setGroupCompanySn(this.getCurrentAccount().getCompany().getGroupCompanySn());
			conf.setConfstus("01");
			beans.add(conf);
			parameter += "添加考勤预设:(帐号名称:"+this.getCurrentAccount().getAccountEmail()+")";
		}else{
			conf.setUtime(new Date());
			conf.setUname(this.getCurrentAccount().getAccountEmail());
			parameter += "修改考勤预设:(帐号名称:"+this.getCurrentAccount().getAccountEmail()+")";
			beans.add(conf);
		}
		CLogBook logBook = logBookService.saveCLogBook(this.getOrganizationId(), parameter, this.getCurrentAccount());
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	/**
	 * 删除
	 * @return
	 */
	public String del(){
		String hql = " delete from AttendConf a where a.attendConfId = ?";
		beans= new ArrayList<BaseBean>(); 
		CLogBook logBook = logBookService.saveCLogBook(this.getOrganizationId(), "删除考勤预设:(帐号名称:"+this.getCurrentAccount().getAccountEmail()+")", this.getCurrentAccount());
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, new Object[]{conf.getAttendConfId()});
		return "success";
	}

	/**
	 * 预设
	 * @return
	 */
	private String preinstall(){
		
		String hql = "delete from AttendConf c where c.companyId = ? and c.confstus = ?";
		Object[] param = new Object[]{this.getCurrentAccount().getCompanyID(),"00"}; 
		
		beans= new ArrayList<BaseBean>(); 
		for(int i=0 ; i<5 ; i++){
			conf = new AttendConf();
			conf.setConfname(Constant.CONFTYPE.get(i));
			conf.setHappents("00");
			conf.setMinstus("00");
			conf.setMaxstus("00");	
			conf.setStus("00");
			conf.setConfstus("00");
			conf.setAttendConfId(serverService.getServerID("conf"));
			conf.setCompanyId(this.getCurrentAccount().getCompanyID());
			conf.setGroupCompanySn(this.getCurrentAccount().getCompany().getGroupCompanySn());
			conf.setCtime(new Date());
			conf.setCname(this.getCurrentAccount().getAccountEmail());
			
			beans.add(conf);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,new String[]{hql},param);
		
		return getAttendConf();
	}

}