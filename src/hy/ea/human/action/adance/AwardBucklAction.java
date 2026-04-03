package hy.ea.human.action.adance;

import hy.base.action.BaseAction;
import hy.ea.bo.human.adance.AttendConf;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;




/**
 * 奖扣记录管理
 * 
 * @param args
 */

@Controller
@Scope("prototype")
@SuppressWarnings("serial")
public class AwardBucklAction extends BaseAction<AttendConf>{

	private AttendConf conf = this.getModel(); 
	private List<BaseBean> beans;
	private String seachdate; //查询时间
	
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getAwarBuckl() throws ParseException{
		
		String hql = "from AttendConf c where c.companyId = ? and c.stus in (?,?) and c.confstus = ?";
		beans = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{this.getCurrentAccount().getCompanyID(),"03","04","01"});
		List parms = new ArrayList();
		String sql = "with logstatus as (select lss.staffid lsid," +
				" count(case when lss.al_status = ? then lss.al_status else null end) als1," +
				" count(case when lss.al_status = ? then lss.al_status else null end) als2," +
				" count(case when lss.al_status = ? then lss.al_status else null end) als3" +
				" from dt_hr_attend_logstus lss where lss.companyid = ? and lss.al_date between ? and ? group by lss.staffid)," +
				" extleave as (select ex.staff_id exid";

		parms.add("迟到");
		parms.add("早退");
		parms.add("旷工");
		parms.add(this.getCurrentAccount().getCompanyID());
		
		if(null == seachdate || "".equals(seachdate)){
			parms.add(DateUtil.parseDate("yyyy-MM-dd", DateUtil.getDateOfMonthBegin(DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd")));
			parms.add(DateUtil.parseDate("yyyy-MM-dd", DateUtil.getDateOfMonthEnd(DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd")));
		}else{
			parms.add(DateUtil.parseDate("yyyy-MM-dd", DateUtil.getDateOfMonthBegin(seachdate+"-01","yyyy-MM-dd")));
			parms.add(DateUtil.parseDate("yyyy-MM-dd", DateUtil.getDateOfMonthEnd(seachdate+"-01","yyyy-MM-dd")));
		}
		
		
		for(int i = 0 ; i < beans.size() ; i ++){
			conf = (AttendConf)beans.get(i);
			sql += " , sum(case when ex.leave_work = ? then ex.sum_time else null end)";
			parms.add(conf.getConfname());
		}
        sql += " from dt_hr_attend_extleave ex where ex.company_id =? and ex.esastate = ? and ex.begin_time between ? and ? group by ex.staff_id)" ;
        
        parms.add(this.getCurrentAccount().getCompanyID());
		parms.add("01");

		if(null == seachdate || "".equals(seachdate)){
			parms.add(DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", DateUtil.getDateOfMonthBegin(DateUtil.getCurrentDate("yyyy-MM-dd"),"yyyy-MM-dd")+" 00:00:00"));
			parms.add(DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", DateUtil.getDateOfMonthEnd(DateUtil.getCurrentDate("yyyy-MM-dd"),"yyyy-MM-dd")+" 23:59:59"));
		}else{
			parms.add(DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", DateUtil.getDateOfMonthBegin(seachdate+"-01","yyyy-MM-dd")+" 00:00:00"));
			parms.add(DateUtil.parseDate("yyyy-MM-dd HH:mm:ss", DateUtil.getDateOfMonthEnd(seachdate+"-01","yyyy-MM-dd")+" 23:59:59"));
		}
		
        String sql1 =" select s.staffid, s.staffname, o.organizationname, ls.als1, ls.als2, ls.als3, ext.*" +
        		" from dtcos c left join dt_hr_staff s on s.staffid = c.staffid " +
        		" left join dtcorganization o on c.organizationid = o.organizationid" +
        		" left join logstatus ls on ls.lsid = c.staffid" +
        		" left join extleave ext on ext.exid = c.staffid" +
        		" where c.companyid = ? and c.status = ? and c.cosstatus = ?";
		
        parms.add(this.getCurrentAccount().getCompanyID());
		parms.add("01");
		parms.add("50");
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 20 : pageNumber), sql+sql1,sql + " select count(*) "
				+ sql1.substring(sql1.indexOf("from")),parms.toArray());		
		
		
		
		return "list";
	}

	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public String getSeachdate() {
		return seachdate;
	}

	public void setSeachdate(String seachdate) {
		this.seachdate = seachdate;
	}
	

}
