package hy.ea.human.action.adance;

import hy.base.action.BaseAction;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.adance.AttendConf;
import hy.ea.bo.human.adance.AttendLog;
import hy.ea.bo.human.adance.AttendLogstus;
import hy.ea.bo.human.adance.CalLogStusVO;
import hy.ea.bo.human.adance.WorkCalendar;
import hy.ea.human.action.ContactAction;
import hy.ea.util.Constant;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;

import java.text.ParseException;
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
 * 个人考勤
 * @author lwz
 *
 */
@Controller
@Scope("prototype")
@SuppressWarnings({"serial","rawtypes"})
public class AttendLogAction extends BaseAction<AttendLog>{

	private AttendLog log = this.getModel(); 
	private WorkCalendar wc = new WorkCalendar();
	private List<BaseBean> beans;
	private List<List> mapwc;
	private List<BaseBean> logstusList;
	private List listls;
	private String sign; //签到签退识别
	private String seaDate;
	private CalLogStusVO clsvo;
	private AttendConf conf ;
	private List<BaseBean> confList;
	private String seachdate; //查询时间
	
	/**
	 * 签到签退
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings({ "deprecation" })
	public String sign() throws ParseException{
		String hqllog =" select l from AttendLog l,WorkCalendar w where l.workcalen = w.workcalendarid and " +
						" w.companyid = ? and w.days = ? and l.staffId = ?";
		Object[] obj = new Object[]{this.getCurrentAccount().getCompanyID(),DateUtil.string2Date(DateUtil.toStrDateFromUtilDateByFormat(new Date(), "yyyy-MM-dd")),this.getCurrentAccount().getStaffID()};
		log = (AttendLog)baseBeanService.getBeanByHqlAndParams(hqllog, obj);
		Map<String, Object> map = new HashMap<String, Object>();
		if(log == null){
			log = new AttendLog();
		}
		if(log.getSigncome() == null || log.getSigngo() == null){
			String hqlwc = "from WorkCalendar w where w.companyid = ? and w.days = ?";
			try {
				wc = (WorkCalendar)baseBeanService.getBeanByHqlAndParams(hqlwc, new Object[]{this.getCurrentAccount().getCompanyID(),DateUtil.string2Date(DateUtil.toStrDateFromUtilDateByFormat(new Date(), "yyyy-MM-dd"))});
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			String hqlcos = " from COS c where c.companyID = ? and c.staffID = ? and c.cosStatus = ? and c.status = ?";
			COS cos = (COS)baseBeanService.getBeanByHqlAndParams(hqlcos, new Object[]{this.getCurrentAccount().getCompanyID(),this.getCurrentAccount().getStaffID(),"50","01"});
			
			beans = new ArrayList<BaseBean>();
			AttendLogstus als = new AttendLogstus();
			als.setAttendLogstusId(serverService.getServerID("logstus"));
			String hql = "from AttendConf c where c.companyId = ? and c.confname = ? " ;
			
			String hour = new Date().getHours()+"";
			if(hour.length() == 1){
				hour = "0" + hour ;
			}
			String minute = new Date().getMinutes()+"";
			if(minute.length() == 1){
				minute = "0" + minute;
			}
			als.setCtime(new Date());
			als.setCname(this.getCurrentAccount().getAccountEmail());
			als.setGroupcompanysn(this.getCurrentAccount().getCompany().getGroupCompanySn());
			als.setCompanyid(this.getCurrentAccount().getCompanyID());
			als.setStaffid(this.getCurrentAccount().getStaffID());
			try {
				als.setAlDate(DateUtil.string2Date(DateUtil.toStrDateFromUtilDateByFormat(new Date(), "yyyy-MM-dd")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			AttendConf conf = new AttendConf();
			if(sign.equals("01")){
				if(log.getSigncome() == null ){
					log.setSigncome(hour+":"+minute);
					conf = (AttendConf)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{this.getCurrentAccount().getCompanyID(),"正常"});
					if(null == conf.getPlaytime() || "".equals(conf.getPlaytime())){
						map.put("suc", "请先设置考勤预设项'正常'!");
						JSONObject oj = JSONObject.fromObject(map);
						result = oj;
						return "success";
					}
					Long playtime = DateUtil.parseDate("hh:mm", conf.getPlaytime()).getTime();//早时间
					Long play = DateUtil.parseDate("hh:mm", log.getSigncome()).getTime();//到达时间
					long pl = play - playtime;
					
					if(pl > 0){//迟到Thu Jan 01 17:30:00 CST 1970
						long k = pl / 1000; 
						int m = (int) (k / 60 % 60); 
						int h = (int) (k / 3600); 
						if(m<30 && h == 0){
							als.setAlStatus("迟到");
							log.setStatus("迟到");
							als.setAltime(h+":"+m);
						}else{
							als.setAlStatus("旷工");
							log.setStatus("旷工");
							als.setAltime(h+":"+m);
						}
					}else{
						als.setAlStatus(conf.getConfname());
						log.setStatus(conf.getConfname());
					}
					log.setStaffId(this.getCurrentAccount().getStaffID());
					log.setWorkcalen(wc.getWorkcalendarid());
					log.setOrganizationid(cos.getOrganizationID());
					log.setCtime(new Date());
					log.setCname(this.getCurrentAccount().getAccountEmail());
					log.setGroupcompanysn(this.getCurrentAccount().getCompany().getGroupCompanySn());
					log.setCompanyid(this.getCurrentAccount().getCompanyID());
					log.setAttendLogId(serverService.getServerID("log"));
					beans.add(log); //个人工作日志
					beans.add(als); //个人日志工作结果

					map.put("suc", log.getStatus());
				}else{
					map.put("suc", "今日已签到!");
				}
			}else{
				if(log.getSigngo() == null ){
					if(log.getAttendLogId() == null){
						log.setStaffId(this.getCurrentAccount().getStaffID());
						log.setWorkcalen(wc.getWorkcalendarid());
						log.setOrganizationid(cos.getOrganizationID());
						log.setCtime(new Date());
						log.setCname(this.getCurrentAccount().getAccountEmail());
						log.setGroupcompanysn(this.getCurrentAccount().getCompany().getGroupCompanySn());
						log.setCompanyid(this.getCurrentAccount().getCompanyID());
						log.setAttendLogId(serverService.getServerID("log"));
					}
					log.setSigngo(hour+":"+minute);
					conf = (AttendConf)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{this.getCurrentAccount().getCompanyID(),"正常"});
					Long stoptime = DateUtil.parseDate("hh:mm", conf.getStoptime()).getTime(); //晚时间
					Long stop = DateUtil.parseDate("hh:mm", log.getSigngo()).getTime();		//到达时间
					long st = stoptime - stop;
					if(st > 0){//早退
						String hqlals = "delete from AttendLogstus s where s.staffid = ? and s.companyid = ? and s.alDate = ? and s.alStatus = ?";
						Object[] o = new Object[]{this.getCurrentAccount().getStaffID(),this.getCurrentAccount().getCompanyID(),DateUtil.string2Date(DateUtil.toStrDateFromUtilDateByFormat(new Date(), "yyyy-MM-dd")),"正常"};
						List<Object[]> paramlist = new ArrayList<Object[]>();
						paramlist.add(o);
						baseBeanService.executeHqlsByParamsList(null, new String[]{hqlals},paramlist);
						long k = st / 1000; 
						int m = (int) (k / 60 % 60); 
						int h = (int) (k / 3600); 
						if(m<30 && h == 0){
							als.setAlStatus("早退");
							log.setStatus("早退");
							als.setAltime(h+":"+m);
						}else{
							als.setAlStatus("旷工");
							log.setStatus("旷工");
							als.setAltime(h+":"+m);
						}
						beans.add(als); //个人日志工作结果
					}
					log.setUtime(new Date());
					log.setUname(this.getCurrentAccount().getAccountEmail());
					beans.add(log); //个人工作日志
					map.put("suc", log.getStatus());
				}else{
					map.put("suc", "今日已签退");
				}
			}
			baseBeanService.executeHqlsByParamsList(beans, null, null);
		}else{
			map.put("suc", "今日已签到已签退!");
		}
		
		JSONObject oj = JSONObject.fromObject(map);
		result = oj;
		return "success";
	}
	
	/**
	 * 加载月份数据
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings({ "deprecation" })
	public String getAttendLog() throws ParseException{
		String d = "";
		if(seaDate.equals("")){
			d = new Date().toLocaleString();
			d = d.substring(0,d.indexOf(" "));
		}else{
			d = seaDate+"-01";
		}
		String hql = "from AttendConf c where c.companyId = ? and c.confname = ? " ;
		conf = (AttendConf)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{this.getCurrentAccount().getCompanyID(),"正常"});
		grouplist(getlist(d));
		getAttendLogstus(d);
		return "list";
	}
	/**
	 * 获取月份个人考勤状态记录汇总   AttendLogstus
	 * @throws ParseException 
	 */
	@SuppressWarnings({ "unchecked"})
	private void getAttendLogstus(String date) throws ParseException{

 		String hql = " from AttendConf a where a.companyId = ? and a.confstus = ? ";
		List list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{this.getCurrentAccount().getCompanyID(),"00"});
		String hqls = " from AttendConf a where a.companyId = ? and a.confstus = ? and a.stus in (?,?)";
		logstusList = baseBeanService.getListBeanByHqlAndParams(hqls, new Object[]{this.getCurrentAccount().getCompanyID(),"01","03","04"});
		
		String sql = "select c.confname,count(s.al_status)  from dt_hr_attend_conf c left join dt_hr_attend_logstus s on c.confname = s.al_status" +
				" where  c.company_id = ? and s.staffid = ? and s.al_date between ? and ? " +
				" group by c.confname";
		List<Object> parms = new ArrayList<Object>();
		parms.add(this.getCurrentAccount().getCompanyID());
		parms.add(this.getCurrentAccount().getStaffID());
		parms.add(DateUtil.parseDate("yyyy-MM-dd", DateUtil.getDateOfMonthBegin(date,"yyyy-MM-dd")));
		parms.add(DateUtil.parseDate("yyyy-MM-dd", DateUtil.getDateOfMonthEnd(date,"yyyy-MM-dd")));
		
		listls = baseBeanService.getListBeanBySqlAndParams(sql, parms.toArray());
		if(listls != null && listls.size() > 0){
			for(int i = 0 ; i < list.size() ; i++){
				AttendConf ac = (AttendConf)list.get(i);
				String str = "";
				for(int j = 0 ; j < listls.size() ; j++){
					Object[] obj = (Object[])listls.get(j);;
					str += obj[0];
				}
				if(str.contains(ac.getConfname())){
					continue;
				}else{
					Object[] ob = new Object[]{ac.getConfname(),"0"};
					listls.add(ob);
				}
			}
		}else{
			for(int i = 0 ; i < list.size() ; i++){
				AttendConf ac = (AttendConf)list.get(i);
				Object[] ob = new Object[]{ac.getConfname(),"0"};
				listls.add(ob);
			}
		}
		System.err.println();
	} 
	/**
	 * 获取 / beans
	 * @param session
	 * @param account
	 * @param date  2014-09 to_date('2014-09-01','yyyy-MM-dd')
	 * @return
	 * @throws ParseException 
	 */
	private List<BaseBean> getlist(String date) throws ParseException {
		List<Object> parms = new ArrayList<Object>();
		String hql = "from WorkCalendar w where w.companyid = ? and w.days between ? and ? order by w.days";
		parms.add(this.getCurrentAccount().getCompanyID());
		parms.add(DateUtil.parseDate("yyyy-MM-dd", DateUtil.getDateOfMonthBegin(date,"yyyy-MM-dd")));
		parms.add(DateUtil.parseDate("yyyy-MM-dd", DateUtil.getDateOfMonthEnd(date,"yyyy-MM-dd")));
		List list1 = baseBeanService.getListBeanByHqlAndParams(hql, parms.toArray());
		beans = new ArrayList<BaseBean>();
		
		for(int i = 0 ; i < list1.size() ; i++){
			WorkCalendar wc = (WorkCalendar)list1.get(i);
			clsvo = new CalLogStusVO();
			clsvo.setCompanyid(wc.getCompanyid());
			clsvo.setDays(wc.getDays().toLocaleString().split(" ")[0]);
			clsvo.setStatus(wc.getStatus());
			clsvo.setWeek(wc.getWeek());
			clsvo.setWorkcalendarid(wc.getWorkcalendarid());
			
			String hqllog = "from AttendLog l where l.companyid = ? and l.staffId = ? and l.workcalen = ?";
			Object[] obj = new Object[]{this.getCurrentAccount().getCompanyID(),this.getCurrentAccount().getStaffID(),wc.getWorkcalendarid()};
			clsvo.setListLog((AttendLog)baseBeanService.getBeanByHqlAndParams(hqllog, obj));
			
			String hqlstus = "from AttendLogstus s where s.companyid = ? and s.staffid = ? and s.alDate = ?";
			Object[] objs = new Object[]{this.getCurrentAccount().getCompanyID(),this.getCurrentAccount().getStaffID(),
					DateUtil.parseDate("yyyy-MM-dd",wc.getDays().toString().substring(0,10))};
			clsvo.setListStus(baseBeanService.getListBeanByHqlAndParams(hqlstus, objs));
			beans.add(clsvo);
		}
		
		return  beans;
	}
	
	/**
	 *  星期list分组
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	private void grouplist(List list){
		int n = 0;
		List l = new ArrayList();
		mapwc = new ArrayList<List>();
		for(int i = 0; i <list.size() ; i ++){
			clsvo = new CalLogStusVO();
			clsvo = (CalLogStusVO)list.get(i);
			if(clsvo.getWeek().equals("星期二") && i==0){
				for(int j=0;j<1;j++){
					l.add(null);
					n++;
				}
				l.add(clsvo);
				n++;
			}else if(clsvo.getWeek().equals("星期三") && i==0){
				for(int j=0;j<2;j++){
					l.add(null);
					n++;
				}
				l.add(clsvo);
				n++;
			}else if(clsvo.getWeek().equals("星期四") && i==0){
				for(int j=0;j<3;j++){
					l.add(null);
					n++;
				}
				l.add(clsvo);
				n++;
			}else if(clsvo.getWeek().equals("星期五") && i==0){
				for(int j=0;j<4;j++){
					l.add(null);
					n++;
				}
				l.add(clsvo);
				n++;
			}else if(clsvo.getWeek().equals("星期六") && i==0){
				for(int j=0;j<5;j++){
					l.add(null);
					n++;
				}
				l.add(clsvo);
				n++;
			}else if(clsvo.getWeek().equals("星期日") && i==0){
				for(int j=0;j<6;j++){
					l.add(null);
					n++;
				}
				l.add(clsvo);
				n++;
			}else {
				l.add(clsvo);
				n++;
			}
			
			if(n == 7 || list.size() == (i+1)){
				mapwc.add(l);
				l = new ArrayList();
				n = 0;
			}
		}
	}
	
	
	
	/**
	 * 
	 * 考勤汇总  
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	public String getLogCom() throws ParseException{
		
		String hql = "from AttendConf c where c.companyId = ? and c.confstus = ?";
		confList = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{this.getCurrentAccount().getCompanyID(),"00"});
		List parms = new ArrayList();
		String sql = "with logstatus as (select lss.staffid stid";
		for(int i = 0 ; i < confList.size() ; i ++){
			conf = (AttendConf)confList.get(i);
			sql += ", count(case when lss.al_status = '" + conf.getConfname() + "' then" +
				" lss.al_status else null end) als"+ i;
		}
		sql += " from dt_hr_attend_logstus lss where lss.companyid = ?" +
				" and lss.al_date between ? and ? group by lss.staffid)";

		parms.add(this.getCurrentAccount().getCompanyID());
		if(null == seachdate || "".equals(seachdate)){
			parms.add(DateUtil.parseDate("yyyy-MM-dd", DateUtil.getDateOfMonthBegin(DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd")));
			parms.add(DateUtil.parseDate("yyyy-MM-dd", DateUtil.getDateOfMonthEnd(DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd")));
		}else{
			parms.add(DateUtil.parseDate("yyyy-MM-dd", DateUtil.getDateOfMonthBegin(seachdate+"-01","yyyy-MM-dd")));
			parms.add(DateUtil.parseDate("yyyy-MM-dd", DateUtil.getDateOfMonthEnd(seachdate+"-01","yyyy-MM-dd")));
		}
		String sql1 = " select s.staffid, s.staffname, o.organizationname, ls.*" +
				" from dtcos c left join dt_hr_staff s  on s.staffid = c.staffid" +
				" left join dtcorganization o on c.organizationid = o.organizationid" +
				" left join logstatus ls on ls.stid = c.staffid";
		sql1 += " where c.companyid = ? and c.status = ? and c.cosstatus = ?";
		parms.add(this.getCurrentAccount().getCompanyID());
		parms.add("01");
		parms.add("50");
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 20 : pageNumber), sql+sql1,sql + " select count(*) "
				+ sql1.substring(sql1.indexOf("from")),parms.toArray());
		seaDate = DateUtil.toStrDateFromUtilDateByFormat(new Date(),"yyyy-MM");
		return "list_com";
	}
	
	/**
	 * 
	 * @return
	 * @throws ParseException 
	 */
	public String getLogsCom() throws ParseException{
		String d = "";
		if(null == seaDate || seaDate.equals("")){
			d = DateUtil.toStrDateFromUtilDateByFormat(new Date(),"yyyy-MM-dd");
			seaDate = DateUtil.toStrDateFromUtilDateByFormat(new Date(),"yyyy-MM");
		}else{
			d = seaDate+"-01";
		}
		List<Object> parms = new ArrayList<Object>();
		String hql = "from WorkCalendar w where w.companyid = ? and w.days between ? and ? order by w.days";
		parms.add(this.getCurrentAccount().getCompanyID());
		parms.add(DateUtil.parseDate("yyyy-MM-dd", DateUtil.getDateOfMonthBegin(d,"yyyy-MM-dd")));
		parms.add(DateUtil.parseDate("yyyy-MM-dd", DateUtil.getDateOfMonthEnd(d,"yyyy-MM-dd")));
		List list1 = baseBeanService.getListBeanByHqlAndParams(hql, parms.toArray());
		beans = new ArrayList<BaseBean>();
		
		for(int i = 0 ; i < list1.size() ; i++){
			WorkCalendar wc = (WorkCalendar)list1.get(i);
			clsvo = new CalLogStusVO();
			clsvo.setCompanyid(wc.getCompanyid());
			clsvo.setDays(wc.getDays().toLocaleString().split(" ")[0]);
			clsvo.setStatus(wc.getStatus());
			clsvo.setWeek(wc.getWeek());
			clsvo.setWorkcalendarid(wc.getWorkcalendarid());
			
			String hqllog = "from AttendLog l where l.companyid = ? and l.staffId = ? and l.workcalen = ?";
			Object[] obj = new Object[]{this.getCurrentAccount().getCompanyID(),log.getStaffId(),wc.getWorkcalendarid()};
			clsvo.setListLog((AttendLog)baseBeanService.getBeanByHqlAndParams(hqllog, obj));
			
			String hqlstus = "from AttendLogstus s where s.companyid = ? and s.staffid = ? and s.alDate = ?";
			Object[] objs = new Object[]{this.getCurrentAccount().getCompanyID(),this.getCurrentAccount().getStaffID(),
					DateUtil.parseDate("yyyy-MM-dd",wc.getDays().toString().substring(0,10))};
			clsvo.setListStus(baseBeanService.getListBeanByHqlAndParams(hqlstus, objs));
			beans.add(clsvo);
		}
		
		grouplist(getlist(d));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mapwc", mapwc);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj;
		return "success";
	}
	public List<List> getMapwc() {
		return mapwc;
	}
	public void setMapwc(List<List> mapwc) {
		this.mapwc = mapwc;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

	public List getListls() {
		return listls;
	}

	public void setListls(List listls) {
		this.listls = listls;
	}

	public String getSeaDate() {
		return seaDate;
	}

	public void setSeaDate(String seaDate) {
		this.seaDate = seaDate;
	}

	public CalLogStusVO getClsvo() {
		return clsvo;
	}

	public void setClsvo(CalLogStusVO clsvo) {
		this.clsvo = clsvo;
	}

	public List<BaseBean> getLogstusList() {
		return logstusList;
	}

	public void setLogstusList(List<BaseBean> logstusList) {
		this.logstusList = logstusList;
	}

	public AttendConf getConf() {
		return conf;
	}

	public void setConf(AttendConf conf) {
		this.conf = conf;
	}

	public List<BaseBean> getConfList() {
		return confList;
	}

	public void setConfList(List<BaseBean> confList) {
		this.confList = confList;
	}

	public String getSeachdate() {
		return seachdate;
	}

	public void setSeachdate(String seachdate) {
		this.seachdate = seachdate;
	}
	
	
}