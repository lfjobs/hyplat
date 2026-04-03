package hy.ea.human.action.adance;

import hy.base.action.BaseAction;
import hy.ea.bo.Company;
import hy.ea.bo.human.adance.WorkCalendar;
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
 * 工作日期记录表
 * @author lwz
 *
 */
@Controller
@Scope("prototype")
@SuppressWarnings({"serial","rawtypes"})
public class WorkCalendarAction extends BaseAction<WorkCalendar>{
	private WorkCalendar workcalendar = this.getModel();
	private String parameter;
	private List<BaseBean> beans;
	private String search; 
	private List<List> mapwc;
	private List<WorkCalendar> listwc;
	private Map<String , WorkCalendar> mapSaveWC;
	private String seaDate;
	private String stus; //数据保存与否
	
	/**
	 * 保存日历
	 * @return
	 * @throws ParseException 
	 */
	public String save() throws ParseException{
	
		String hql = "delete from WorkCalendar w where w.companyid = ? and w.days between ? and ?";
		Object[] params = {this.getCurrentAccount().getCompanyID(),DateUtil.string2Date(DateUtil.getDateOfMonthBegin(seaDate+"-01","yyyy-MM-dd")),DateUtil.string2Date(DateUtil.getDateOfMonthEnd(seaDate+"-01","yyyy-MM-dd"))};
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, params);
		beans= new ArrayList<BaseBean>();
		if(mapSaveWC!=null){
			for(WorkCalendar wc:mapSaveWC.values()){
				if(!wc.getWeek().equals("")){
					wc.setCompanyid(this.getCurrentAccount().getCompanyID());
					wc.setWorkcalendarid(serverService.getServerID("workc"));
					wc.setCtime(new Date());
					wc.setCname(this.getCurrentAccount().getAccountEmail());
					wc.setGroupCompanySn(this.getCurrentAccount().getCompany().getGroupCompanySn());
					beans.add(wc);
				}
			}
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	
	public String toAdd() throws ParseException{
		int yy = 0;
		int mm = 0;
		if(seaDate.equals("")){
			 yy = DateUtil.getYearOfDate(new Date());
			 mm = DateUtil.getMonthOfDate(new Date());
			 if(mm<10){
				 seaDate = yy+"-0"+mm;
			 }else{
				 seaDate = yy+"-"+mm;
			 }
		}else{
			yy = DateUtil.getYearOfDate(DateUtil.string2Date(seaDate+"-01"));
			mm = DateUtil.getMonthOfDate(DateUtil.string2Date(seaDate+"-01"));
		}
		listwc = new ArrayList<WorkCalendar>();
		String hql = "from WorkCalendar w where w.companyid = ? and w.days between ? and ?  order by w.days";
		Object[] params = { this.getCurrentAccount().getCompanyID(),DateUtil.string2Date(DateUtil.getDateOfMonthBegin(seaDate+"-01","yyyy-MM-dd")),DateUtil.string2Date(DateUtil.getDateOfMonthEnd(seaDate+"-01","yyyy-MM-dd"))};
		beans = baseBeanService.getListBeanByHqlAndParams(hql, params);
		if(beans.size() == 0){
			stus = "00";
		}else{
			grouplist(beans);
			stus = "01";
		}
		return "toAdd";
	}
	
	private void grouplist(List<BaseBean> list){
		int n = 0;
		List<WorkCalendar> l = new ArrayList<WorkCalendar>();
		WorkCalendar wc = null;
		mapwc = new ArrayList<List>();
		for(int i = 0; i <list.size() ; i ++){
			wc = new WorkCalendar();
			wc = (WorkCalendar)list.get(i);
			if(wc.getWeek().equals("星期二") && i==0){
				for(int j=0;j<1;j++){
					l.add(new WorkCalendar());
					n++;
				}
				l.add(wc);
				n++;
			}else if(wc.getWeek().equals("星期三") && i==0){
				for(int j=0;j<2;j++){
					l.add(new WorkCalendar());
					n++;
				}
				l.add(wc);
				n++;
			}else if(wc.getWeek().equals("星期四") && i==0){
				for(int j=0;j<3;j++){
					l.add(new WorkCalendar());
					n++;
				}
				l.add(wc);
				n++;
			}else if(wc.getWeek().equals("星期五") && i==0){
				for(int j=0;j<4;j++){
					l.add(new WorkCalendar());
					n++;
				}
				l.add(wc);
				n++;
			}else if(wc.getWeek().equals("星期六") && i==0){
				for(int j=0;j<5;j++){
					l.add(new WorkCalendar());
					n++;
				}
				l.add(wc);
				n++;
			}else if(wc.getWeek().equals("星期日") && i==0){
				for(int j=0;j<6;j++){
					l.add(new WorkCalendar());
					n++;
				}
				l.add(wc);
				n++;
			}else {
				l.add(wc);
				n++;
			}
			
			if(n == 7 || list.size() == (i+1)){
				mapwc.add(l);
				l = new ArrayList<WorkCalendar>();
				n = 0;
			}
		}
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	public void toAfterDB(){
		String date = new Date().toLocaleString();
		System.err.println(date);
		String hql = "from Company c where c.companyStatus = ? ";
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{"00"});
		DateUtil dutil = new DateUtil();
		int yy = (dutil.getYearOfDate(new Date()))+1;
		int mm = dutil.getMonthOfDate(new Date());
		beans = new ArrayList<BaseBean>();
		for(int iii = 1 ; iii<=12 ;iii++){
			int dnum = 0;
			if(iii == 1 || iii == 3 || iii == 5 || iii == 7 || iii == 8 || 
					iii == 10 || iii == 12 ){ //31天
				dnum = 31;
			}else if(iii == 4 || iii == 6 || iii == 9 || iii == 11){//30天
				dnum = 30;
			}else{
				if(yy%4 == 0){//29天
					dnum = 29;
				}else{//28天
					dnum = 28;
				}
			}
			for(int i = 1;i <= dnum;i++){
				String ii = i+"";
				if(i<10){
					ii = "0"+i;
				}
				String jj = iii+"";
				if(mm<10){
					jj = "0"+iii ;
				}
				
				WorkCalendar wc = new WorkCalendar();
				wc.setDays(dutil.string2Date(yy+"-"+jj+"-"+ii));
				wc.setWeek(dutil.getWeekday(yy+"-"+jj+"-"+ii));
				if(dutil.getWeekday(yy+"-"+jj+"-"+ii).equals("星期六") ||
						dutil.getWeekday(yy+"-"+jj+"-"+ii).equals("星期日")){
					wc.setStatus("01");
				}else{
					wc.setStatus("00");
				}

				wc.setWorkcalendarid(serverService.getServerID("workc"));
				beans.add(wc);
			}
		}
		List<BaseBean> lists = new ArrayList<BaseBean>();
		for(int l = 0 ; l<list.size() ; l++){
			Company c  = (Company)list.get(l);
			for(int k = 0 ; k < beans.size() ; k++){
				WorkCalendar w = (WorkCalendar)beans.get(k);
				w.setCompanyid(c.getCompanyID());
				w.setCtime(new Date());
				w.setCname("computer");
				w.setGroupCompanySn(c.getGroupCompanySn());
				lists.add(w);
			}
		}
		
		
		baseBeanService.saveBeansListAndexecuteHqlsByParams(lists, null, null);
	}
	
	public String toNewDB() throws ParseException{
		String hql0="select count(*) from WorkCalendar w where w.companyid = ?";
		int k = baseBeanService.getConutByByHqlAndParams(hql0, new Object[]{ this.getCurrentAccount().getCompanyID()});
		if(k>0){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("suc", "roo");
			JSONObject oj = JSONObject.fromObject(map);
			result = oj.toString();
			return "success";
		}
		int yy = DateUtil.getYearOfDate(new Date());
		int mm = DateUtil.getMonthOfDate(new Date());
		if(mm<10){
			seaDate = yy+"-0"+mm;
		}else{
			seaDate = yy+"-"+mm;
		}
		int jjj = 0;
		beans = new ArrayList<BaseBean>();
		for(int iii = mm ; iii<=12 ;iii++){
			int dnum = 0;
			if(iii == 1 || iii == 3 || iii == 5 || iii == 7 || iii == 8 || 
					iii == 10 || iii == 12 ){ //31天
				dnum = 31;
			}else if(iii == 4 || iii == 6 || iii == 9 || iii == 11){//30天
				dnum = 30;
			}else{
				if(yy%4 == 0){//29天
					dnum = 29;
				}else{//28天
					dnum = 28;
				}
			}
			for(int i = 1;i <= dnum;i++){
				String ii = i+"";
				if(i<10){
					ii = "0"+i;
				}
				String jj = iii+"";
				if(mm<10){
					jj = "0"+iii ;
				}
				
				WorkCalendar wc = new WorkCalendar();
				wc.setCompanyid(this.getCurrentAccount().getCompanyID());
				wc.setCtime(new Date());
				wc.setCname(this.getCurrentAccount().getAccountEmail());
				wc.setGroupCompanySn(this.getCurrentAccount().getCompany().getGroupCompanySn());
				wc.setDays(DateUtil.string2Date(yy+"-"+jj+"-"+ii));
				wc.setWeek(DateUtil.getWeekday(yy+"-"+jj+"-"+ii));
				if(DateUtil.getWeekday(yy+"-"+jj+"-"+ii).equals("星期六") ||
						DateUtil.getWeekday(yy+"-"+jj+"-"+ii).equals("星期日")){
					wc.setStatus("01");
				}else{
					wc.setStatus("00");
				}

				wc.setWorkcalendarid(serverService.getServerID("workc"));
				beans.add(wc);
			}
			if(iii == 12 && jjj ==0){
				yy++;
				iii = 1;
				jjj++;
			}
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("suc", "suc");
		map.put("seaDate", seaDate);
		
		JSONObject oj = JSONObject.fromObject(map);
		result = oj;
		return "success";
	}
	public WorkCalendar getWorkcalendar() {
		return workcalendar;
	}
	public void setWorkcalendar(WorkCalendar workcalendar) {
		this.workcalendar = workcalendar;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public List<List> getMapwc() {
		return mapwc;
	}

	public void setMapwc(List<List> mapwc) {
		this.mapwc = mapwc;
	}

	public List<WorkCalendar> getListwc() {
		return listwc;
	}

	public void setListwc(List<WorkCalendar> listwc) {
		this.listwc = listwc;
	}

	public Map<String, WorkCalendar> getMapSaveWC() {
		return mapSaveWC;
	}

	public void setMapSaveWC(Map<String, WorkCalendar> mapSaveWC) {
		this.mapSaveWC = mapSaveWC;
	}

	public String getSeaDate() {
		return seaDate;
	}

	public void setSeaDate(String seaDate) {
		this.seaDate = seaDate;
	}

	public String getStus() {
		return stus;
	}

	public void setStus(String stus) {
		this.stus = stus;
	}


	
	
}
