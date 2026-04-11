package hy.ea.ddsr.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.Remind;
import hy.ea.bo.ddsr.Ddsrcoach;
import hy.ea.bo.ddsr.Ddsrreservationrecord;
import hy.ea.bo.ddsr.Ddsrworktime;
import hy.ea.bo.ddsr.Dssrstudent;
import hy.ea.bo.ddsr.Dssrsubject;
import hy.ea.bo.ddsr.ReDdsrcoachDssrsubject;
import hy.ea.bo.ddsr.ReDssrstudentDdsrresrecord;
import hy.ea.bo.human.Staff;
import hy.ea.service.CLogBookService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.DateUtil;
import hy.ea.util.MobileMessagenew;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import mobile.tiantai.android.bo.DtMicroletterMenu;
import mobile.tiantai.android.bo.DtMicroletterMenuContent;
import net.sf.json.JSONObject;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/**
 * 预约记录详细内容
 */
@Controller
@Scope("prototype")
public class CoachReservationRecordContentAction {
	private static final Logger logger = LoggerFactory.getLogger(CoachReservationRecordContentAction.class);
	private int pageNumber;
	private InputStream excelStream;
	private PageForm pageForm;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ServerService serverService;

	private String search;
	private String parameter;
	@Resource
	private BaseBeanService baseBeanService;
	private DtMicroletterMenuContent dtMicroletterMenuContent;
	private DtMicroletterMenu dtMicroletterMenu;
	private Ddsrreservationrecord ddsrreservationrecord;
	private Ddsrcoach ddsrcoach;
	private Dssrstudent dssrstudent;
	private Staff   dtHrStaff;
	/**
	 * 科目
	 */
	private Dssrsubject dssrsubject;
	private ReDssrstudentDdsrresrecord reDssrstudentDdsrresrecord;
	@Resource
	private UpLoadFileService fileService;
	@Autowired
	private MobileMessagenew mobileMessage;
	
	private String result;
	/**
	 * 教练个人预约激励
	 */
	private List<BaseBean> ddsrreservationrecordList;
	/**
	 * 预约记录汇总打印
	 */
	private List<BaseBean> ddsrreservationrecordsummaryList;
	private String rereKeyString;
	Map<String, Object> session = ActionContext.getContext().getSession();
	/**
	 * 个人预约记录详细内容列表 按 时间 教练 公司 查询
	 * @return
	 */
	public String getListCoachReservationRecordContentPersonal() {	
		if(generateListReservationRecord()){
			DetachedCriteria dc=getListCoachReservationRecordContentPersonalByDc();
			if(dssrsubject!=null){
				if(dssrsubject.getSubjType()!=null&&!"".equals(dssrsubject.getSubjType())){
					dc.add(Restrictions.in(dc.getAlias() + "." + "rereSubjects", new Object[]{dssrsubject.getSubjType(),Byte.parseByte("90")}));
				}
			}
			if(dssrstudent!=null){
				if(dssrstudent.getStudKey()!=null&&!"".equals(dssrstudent.getStudKey().trim())){
					dc.add(Restrictions.eq("dssrstudent.studKey", dssrstudent.getStudKey().trim()));
				}
			}
			ddsrreservationrecordList=baseBeanService.getListByDC(dc);
		}
		return "coachreservationrecordcontent_List";	
	}
	/**
	 * 构造个人预约记录离线查询语句
	 */
	public DetachedCriteria getListCoachReservationRecordContentPersonalByDc(){
		CAccount account = (CAccount) session.get("account");
		String alias = "ddsrreservationrecord"; //查的table名   
		DetachedCriteria dc = DetachedCriteria.forClass(Ddsrreservationrecord.class,alias);
		/*
		ProjectionList pList = Projections.projectionList();  
		pList.add(Projections.property(alias + "." + "rereKey").as("rereKey"));
		pList.add(Projections.property(alias + "." + "rereCompanyid").as("rereCompanyid")); 
		pList.add(Projections.property(alias + "." + "rereAppdate").as("rereAppdate")); 
		pList.add(Projections.property(alias + "." + "rereClass").as("rereClass")); 
		pList.add(Projections.property(alias + "." + "rereStadate").as("rereStadate")); 
		pList.add(Projections.property(alias + "." + "rereEnddate").as("rereEnddate")); 
		pList.add(Projections.property(alias + "." + "rerePeoplesum").as("rerePeoplesum")); 
		pList.add(Projections.property(alias + "." + "rereSubjects").as("rereSubjects")); 
		pList.add(Projections.property(alias + "." + "ddsrcoach"+"."+"dtHrStaff.staffName").as(alias));
		pList.add(Projections.property(alias + "." + "reDssrstudentDdsrresrecords").as("reDssrstudentDdsrresrecords"));
		dc.setProjection(pList);  
		dc.setResultTransformer(Transformers.aliasToBean(Ddsrreservationrecord.class));  
		*/
		if(session.get("unionCompanyID")!=null){//区别于  驾校联盟网站传参获得数据 从session中获得companyID;
			dc.add(Restrictions.eq(alias + "." + "rereCompanyid", session.get("unionCompanyID")));
		}else if(account!=null&&null!=account.getCompanyID()&&!"".equals(account.getCompanyID())){
			dc.add(Restrictions.eq(alias + "." + "rereCompanyid", account.getCompanyID()));
		}
		dc.createAlias("ddsrcoach", "ddsrcoach",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("ddsrcoach.dtHrStaff", ".dtHrStaff",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("reDssrstudentDdsrresrecords", "reDssrstudentDdsrresrecords",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("reDssrstudentDdsrresrecords.dssrstudent", "dssrstudent",DetachedCriteria.LEFT_JOIN);
		dc.createAlias("reDssrstudentDdsrresrecords.dssrstudent.dtHrStaff", "dtHrStaff1",DetachedCriteria.LEFT_JOIN);
		if(ddsrcoach!=null&&ddsrcoach.getCoacKey()!=null&&!"".equals(ddsrcoach.getCoacKey().trim())){
			dc.add(Restrictions.eq("ddsrcoach.coacKey", ddsrcoach.getCoacKey().trim()));
		}
		if(ddsrreservationrecord!=null&&ddsrreservationrecord.getRereAppdate()!=null&&!"".equals(ddsrreservationrecord.getRereAppdate())){
			dc.add(Restrictions.eq(alias + "." + "rereAppdate", ddsrreservationrecord.getRereAppdate()));
		}
		dc.addOrder(Order.asc(alias + "." + "rereStadate"));
		return dc;
	}
	/**
	 * 生成个人培训详细记录列表
	 * @author allen
	 */
	@SuppressWarnings("deprecation")
	public boolean generateListReservationRecord(){
		boolean re=false;
		/*
		ddsrreservationrecord=new Ddsrreservationrecord();
		ddsrreservationrecord.setRereAppdate(DateUtil.parseDate("yyyy-MM-dd","2013-12-14"));
		*/
		if(ddsrcoach!=null&&ddsrcoach.getCoacKey()!=null&&!"".equals(ddsrcoach.getCoacKey().trim())){
			Ddsrcoach ddsrcoach_a=(Ddsrcoach) baseBeanService.getBeanByKey(Ddsrcoach.class, ddsrcoach.getCoacKey());
			if(ddsrreservationrecord!=null&&ddsrreservationrecord.getRereAppdate()!=null&&!"".equals(ddsrreservationrecord.getRereAppdate())){
				int count=baseBeanService.getConutByDC(getListCoachReservationRecordContentPersonalByDc());
				/**
				 * 如果没有就生成预约记录
				 */
				if(count==0){
					/**
					 * 获得教练工作时间段设置信息列表
					 */
					List<BaseBean> beans_worktime=getListDdsrworktimePersonal();
					/**
					 * 获得教练可教授科目信息表
					 * 
					 * 修改  2013-12-26 在预约操作时确定预约科目
					 */
					//List<BaseBean> beans_subject=getListReDdsrcoachDssrsubjectPersonal();
					if (beans_worktime!=null&&beans_worktime.size()!=0) {
						List<BaseBean> arrayList=new ArrayList<BaseBean>();
						//for (BaseBean bean_subject : beans_subject) {
							//ReDdsrcoachDssrsubject subject=(ReDdsrcoachDssrsubject)bean_subject;
							for (BaseBean bean_worktime : beans_worktime){
								Ddsrworktime worktime=(Ddsrworktime)bean_worktime;
								Date startdate=DateUtil.parseDate("HH:mm",worktime.getWotiStrdate());
								Date enddate=DateUtil.parseDate("HH:mm",worktime.getWotiEnddate());
								int hour=DateUtil.getHourOfDate(enddate)-DateUtil.getHourOfDate(startdate);
								for (int i = 0; i <hour; i++) {
									Ddsrreservationrecord ddsrrr=new Ddsrreservationrecord();
									ddsrrr.setDdsrcoach(ddsrcoach_a);
									ddsrrr.setRereCompanyid(ddsrcoach_a.getCoacCompanyid());
									ddsrrr.setRereAppdate(ddsrreservationrecord.getRereAppdate());
									ddsrrr.setRereClass(worktime.getWotiClass());
									try {
										Calendar calendar=DateUtil.toCalendarFromUtilDate(startdate);
										calendar.set(Calendar.HOUR_OF_DAY,startdate.getHours()+i);
										ddsrrr.setRereStadate(DateUtil.toStrDateFromUtilDateByFormat(calendar.getTime(),"HH:mm"));
										calendar.set(Calendar.HOUR_OF_DAY,startdate.getHours()+i+1);
										ddsrrr.setRereEnddate(DateUtil.toStrDateFromUtilDateByFormat(calendar.getTime(),"HH:mm"));
									} catch (ParseException e) {
										logger.error("操作异常", e);
									}
									ddsrrr.setRerePeoplesum(Byte.valueOf("0"));
									//ddsrrr.setRereSubjects(subject.getDssrsubject().getSubjType());
									ddsrrr.setRereSubjects(Byte.parseByte("90"));
									ddsrrr.setRereCreatedate(new Date());
									ddsrrr.setRereUpdatedate(new Date());
									arrayList.add(ddsrrr);
								}
							//} 
						}
						baseBeanService.saveBeansListAndexecuteHqlsByParams(arrayList, null, null);
						re=true;
					}
				}else{
					re=true;
				}
			}
		}	
		return re;
	}
	
	/**
	 * 查询工作时间短设置列表 20 自定义时间段 10 驾校常规时间段
	 * @author allen
	 */
	public List<BaseBean> getListDdsrworktimePersonal(){
		List<BaseBean> wpList=null;
		PageForm pageForm=null;
		DetachedCriteria dc=getListDdsrworktimePersonalByDc();
		dc.add(Restrictions.eq("wotiType", Short.parseShort("20")));
		pageForm=baseBeanService.getPageFormByDC(0, 4, dc);
		if(pageForm!=null){
			wpList=pageForm.getList();
		}else{
			DetachedCriteria dc_a=getListDdsrworktimePersonalByDc();
			dc_a.add(Restrictions.eq("wotiType",  Short.parseShort("10")));
			pageForm=baseBeanService.getPageFormByDC(0, 4, dc_a);
			if(pageForm!=null){
				wpList=pageForm.getList();
			}
		}
		return wpList;
	}
	/**
	 * 构造工作时间短设置连线查询语句
	 * @author allen
	 */
	public DetachedCriteria getListDdsrworktimePersonalByDc(){
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(Ddsrworktime.class);
		dc.createAlias("ddsrcoach", "ddsrcoach",CriteriaSpecification.LEFT_JOIN);
		if(session.get("unionCompanyID")!=null){//区别于  驾校联盟网站传参获得数据 从session中获得companyID;
			dc.add(Restrictions.eq("wotiCompanyid", session.get("unionCompanyID")));
		}else if(account!=null&&null!=account.getCompanyID()&&!"".equals(account.getCompanyID())){
			dc.add(Restrictions.eq("wotiCompanyid", account.getCompanyID()));
		}
		dc.add(Restrictions.eq("ddsrcoach.coacKey", ddsrcoach.getCoacKey().trim()));
		dc.add(Restrictions.le("wotiStrdaydate", ddsrreservationrecord.getRereAppdate()));
		dc.add(Restrictions.ge("wotiEnddaydate", ddsrreservationrecord.getRereAppdate()));
		dc.addOrder(Order.desc("wotiCreatedate"));
		dc.addOrder(Order.asc("wotiClass"));
		return dc;
	}
	
	/**
	 * 查询教练科目信息
	 * @author allen
	 */
	public List<BaseBean> getListReDdsrcoachDssrsubjectPersonal(){
		List<BaseBean> ddsrSubjectList=baseBeanService.getListByDC(getListReDdsrcoachDssrsubjectPersonalByDc());
		return ddsrSubjectList;
	}
	/**
	 * 构造教练科目离线查询
	 * @author allen
	 */
	public DetachedCriteria getListReDdsrcoachDssrsubjectPersonalByDc(){
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(ReDdsrcoachDssrsubject.class);
		dc.createAlias("ddsrcoach", "ddsrcoach");
		dc.add(Restrictions.eq("ddsrcoach.coacCompanyid", account.getCompanyID()));
		dc.add(Restrictions.eq("ddsrcoach.coacKey", ddsrcoach.getCoacKey().trim()));
		return dc;
	}
	
	/**
	 * 取消预约功能 个人	
	 * @author allen
	 * @param args
	 */
	public String deleteReservationRecordContentPersonal(){
		boolean status=false;
		List<BaseBean> beans =new ArrayList<BaseBean>();
		Map<String, Object>  map=new HashMap<String, Object>();
		if(ddsrreservationrecord!=null){
			if(ddsrreservationrecord.getRereKey()!=null&&!"".equals(ddsrreservationrecord.getRereKey())){
				
				DetachedCriteria dc = DetachedCriteria.forClass(Ddsrreservationrecord.class);
				dc.createAlias("reDssrstudentDdsrresrecords", "reDssrstudentDdsrresrecords",DetachedCriteria.LEFT_JOIN);
				dc.add(Restrictions.eq("rereKey", ddsrreservationrecord.getRereKey()));
				ddsrreservationrecordList=baseBeanService.getListByDC(dc);
				Ddsrreservationrecord bean=(Ddsrreservationrecord) ddsrreservationrecordList.get(0);
				
				Date t1 = new Date();
		        Calendar cal = Calendar.getInstance();
		        cal.setTime(t1);
		        String dateStr="";
		        try {
		        	dateStr=DateUtil.toStrDateFromUtilDateByFormat(cal.getTime(),"yyyy-MM-dd");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					logger.error("操作异常", e);
				}
		        Date date=DateUtil.parseDate("yyyy-MM-dd", dateStr);
		        if(bean.getRereAppdate().compareTo(date)!=1){
		    		map.put("status", status);
		    		map.put("message", "必须提前24小时取消预约!!");
		    		JSONObject json=JSONObject.fromObject(map);
		    		result=json.toString();
		    		return "success";
		        }
				
				@SuppressWarnings("unchecked")
				Set<BaseBean>  setBeans=bean.getReDssrstudentDdsrresrecords();
				Dssrstudent sd=null;
				for (BaseBean baseBean : setBeans) {
					ReDssrstudentDdsrresrecord redd=(ReDssrstudentDdsrresrecord)baseBean;
					/**
					 * 返还学时
					 */
					sd=redd.getDssrstudent();
				    Object[] objs=toAddOrSubtractDssrstudentByRemhour(sd, 1, 1);
				    sd=(Dssrstudent) objs[0];
				    int result_a=(Integer) objs[1];
				    if(sd==null||result_a==2){
			    		map.put("status", status);
			    		map.put("message", "异常!!请联系管理员!!");
			    		JSONObject json=JSONObject.fromObject(map);
			    		result=json.toString();
			    		return "success";
				    }
				    beans.add(sd);
				}
				bean.setReDssrstudentDdsrresrecords(null);//删除前必须的先清空关联
				beans.add(bean);
				
				bean.setRerePeoplesum(Byte.parseByte("0"));
				bean.setRereSubjects(Byte.parseByte("90"));
				
				String[] hqls={" delete from ReDssrstudentDdsrresrecord  where stureKey=? "};
				baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hqls, new Object[]{reDssrstudentDdsrresrecord.getStureKey()});
				
				map.put("status", true);
				addRemindContent(bean.getDdsrcoach(),sd,ddsrreservationrecordList,0);
				/*if(sendMessage(bean.getDdsrcoach(),sd,ddsrreservationrecordList,0)){
					map.put("message", "操作成功！！短信已发送");
				}else{
					map.put("message", "操作成功！！短信未发送");
				}*/
				map.put("message", "操作成功！！短信未发送");
			}
		}
		JSONObject json=JSONObject.fromObject(map);
		result=json.toString();
		return "success";
	}
	/**
	 * 
	 * 学员预约记录新增
	 * @author zgzg
	 * @param args
	 */
	 
	public String addReservationRecordContentPersonal(){
		
		Map<String, Object>  map=new HashMap<String, Object>();
		boolean status=false;
		if(rereKeyString!=null&&!"".equals(rereKeyString)&&dssrstudent!=null
				&&dssrstudent.getStudKey()!=null&&!"".equals(dssrstudent.getStudKey())){
			List<String> array=Arrays.asList(rereKeyString.split(","));
			DetachedCriteria dc=DetachedCriteria.forClass(ReDssrstudentDdsrresrecord.class);
			dc.createAlias("ddsrreservationrecord", "ddsrreservationrecord");
			dc.add(Restrictions.in("ddsrreservationrecord.rereKey",array));
			List<BaseBean> reDssrstDdsList=baseBeanService.getListByDC(dc);
			if(reDssrstDdsList==null||reDssrstDdsList.size()==0){
				
				/**
				 * 预约学员
				 */
				Dssrstudent sd=(Dssrstudent) baseBeanService.getBeanByKey(Dssrstudent.class, dssrstudent.getStudKey());
				
				/**
				 * 即将预约记录 结果集List
				 * @return reDssrstDdsList2
				 */
				DetachedCriteria dc1 = DetachedCriteria.forClass(Ddsrreservationrecord.class);
				dc1.add(Restrictions.in("rereKey",array));
				List<BaseBean> reDssrstDdsList2=baseBeanService.getListByDC(dc1);
				
				/**
				 * 预约教练
				 */
				Ddsrcoach dca=(Ddsrcoach) baseBeanService.getBeanByKey(Ddsrcoach.class, ((Ddsrreservationrecord) reDssrstDdsList2.get(0)).getDdsrcoach().getCoacKey());
				/**
				 * 判断日期是否合法
				 */
				Date t1 = new Date();
		        Calendar cal = Calendar.getInstance();
		        cal.setTime(t1);
		        String dateStr="";
		        try {
		        	dateStr=DateUtil.toStrDateFromUtilDateByFormat(cal.getTime(),"yyyy-MM-dd");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					logger.error("操作异常", e);
				}
		        Date date=DateUtil.parseDate("yyyy-MM-dd", dateStr);
		        if(((Ddsrreservationrecord) reDssrstDdsList2.get(0)).getRereAppdate().compareTo(date)!=1){
		    		map.put("status", status);
		    		map.put("message", "必须提前24小时预约!!");
		    		JSONObject json=JSONObject.fromObject(map);
		    		result=json.toString();
		    		return "success";
		        }
		        
		        /**
				 * 已经预约记录 结果集List
				 * @return reDssrstDdsList2
				 */
				DetachedCriteria dc1_a = DetachedCriteria.forClass(Ddsrreservationrecord.class);
				dc1_a.createAlias("reDssrstudentDdsrresrecords", "reDssrstudentDdsrresrecords",DetachedCriteria.LEFT_JOIN);
				dc1_a.createAlias("reDssrstudentDdsrresrecords.dssrstudent", "dssrstudent",DetachedCriteria.LEFT_JOIN);
				dc1_a.add(Restrictions.eq("rereAppdate", ((Ddsrreservationrecord) reDssrstDdsList2.get(0)).getRereAppdate()));
				dc1_a.add(Restrictions.eq("dssrstudent.studKey", dssrstudent.getStudKey().trim()));
				List<BaseBean> reDssrstDdsList3=baseBeanService.getListByDC(dc1_a);
				/**
				 * 
				 * 判断是否超过4个学时
				 */
				if(reDssrstDdsList3.size()+reDssrstDdsList2.size()>4){
					map.put("status", status);
		    		map.put("message", "每天最多预约四个学时!!");
		    		JSONObject json=JSONObject.fromObject(map);
		    		result=json.toString();
		    		return "success";
				}
				/**
				 * 判断即将预约记录是否与已预约记录时间段相交
				 */
				Object[] objs= validataPeriodOfTimeByStudent(reDssrstDdsList2,reDssrstDdsList3);
				if(objs[0].equals(true)){
		    		map.put("status", status);
		    		map.put("message", objs[1]+" 请重新选择!!");
		    		JSONObject json=JSONObject.fromObject(map);
		    		result=json.toString();
		    		return "success";
				}
				
				/**
				 * 更新学员学时 addOrdel
				 */
			    Object[] objs_a=toAddOrSubtractDssrstudentByRemhour(sd, array.size(), 0);
			    sd=(Dssrstudent) objs_a[0];
			    int result_a=(Integer) objs_a[1];
			    if(sd==null||result_a==2){
		    		map.put("status", status);
		    		map.put("message", "异常!!请联系管理员!!");
		    		JSONObject json=JSONObject.fromObject(map);
		    		result=json.toString();
		    		return "success";
			    }else if(result_a==0){
		    		map.put("status", status);
		    		map.put("message", "可预约学时不足!!");
		    		JSONObject json=JSONObject.fromObject(map);
		    		result=json.toString();
		    		return "success";
			    }
			    
				List<BaseBean> beans=new ArrayList<BaseBean>();
				for (BaseBean baseBean : reDssrstDdsList2) {
					Ddsrreservationrecord  dd=(Ddsrreservationrecord) baseBean;
			        dd.setRerePeoplesum(Byte.parseByte("1"));
					dd.setRereSubjects(dssrsubject.getSubjType());
					/**
					 * 关联表 ReDssrstudentDdsrresrecord 添加 学员与预约记录的关系
					 */
					ReDssrstudentDdsrresrecord  redd=new ReDssrstudentDdsrresrecord();
					redd.setDssrstudent(sd);
					redd.setDdsrreservationrecord(dd);
					beans.add(redd);
					beans.add(dd);
				}
				beans.add(sd);
				baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
				
				map.put("status", true);//sd.getDtHrStaff().getReference()
				addRemindContent(dca,sd,reDssrstDdsList2,1);
				/*if(sendMessage(dca,sd,reDssrstDdsList2,1)){
					map.put("message", "操作成功！！短信已发送");
				}else{
					map.put("message", "操作成功！！短信未发送");
				}*/
				map.put("message", "操作成功！！短信未发送");
			}
		}
		JSONObject json=JSONObject.fromObject(map);
		result=json.toString();
		return "success";
	}
	
	/**
	 * 
	 * 新增  webservices 提醒内容
	 */
	private void addRemindContent(Ddsrcoach dca,Dssrstudent dsd,List<BaseBean> reDdList,int i){
		List<BaseBean> list =new ArrayList<BaseBean>();
		for (BaseBean baseBean : reDdList) {
			Ddsrreservationrecord  dd=(Ddsrreservationrecord) baseBean;
			
			Remind rm=new Remind();
			rm.setRemindID(serverService.getServerID("remind"));
			rm.setCompanyID(dsd.getStudCompanyid());
			rm.setCircularType("01");
			rm.setCircularTitle("预约通知");
			rm.setStaffID(dsd.getDtHrStaff().getStaffID());
			rm.setStaffName(dsd.getDtHrStaff().getStaffName());
			rm.setRemindType("01");
			rm.setRemindStatus("01");
			rm.setAddDate(new Date());
			
			StringBuffer sb=new StringBuffer(); 
			sb.append(i==1?"新增预约,":"取消预约,");		
			sb.append(" 教练姓名 : "+dca.getDtHrStaff().getStaffName()+";");
			sb.append(" 性别 :" +dca.getDtHrStaff().getSex()+";");
			sb.append(" 电话 :" +dca.getDtHrStaff().getReference()+",");
			sb.append(" 学员姓名 : "+dsd.getDtHrStaff().getStaffName()+";");
			sb.append(" 性别 :" +dsd.getDtHrStaff().getSex()+";");
			sb.append(" 电话 :" +dsd.getDtHrStaff().getReference()+",");
			/**
			 * 构造提醒信息内容
			 */
			sb.append(" 科目 : "+("10".equals(dd.getRereSubjects().toString())?"科一":"20".equals(dd.getRereSubjects())?"科二":"30".equals(dd.getRereSubjects())?"科三":"科四")+";");
			try {
			sb.append(" 日期 : "+DateUtil.toStrDateFromUtilDateByFormat(dd.getRereAppdate(),"yyyy-MM-dd")+";");
			rm.setReceiveDate(dd.getRereAppdate());//接收时间
			} catch (ParseException e) {
			}
			sb.append(" 时间段 : "+dd.getRereStadate()+"~"+dd.getRereEnddate()+",");	
			rm.setCircularText(sb.toString());//接收内容
			list.add(rm);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
	}
	/**
	 * 
	 * 发送短信
	 */
	private boolean sendMessage(Ddsrcoach dca,Dssrstudent dsd,List<BaseBean> reDdList,int i){
		
		StringBuffer sb=new StringBuffer(); 
		sb.append(i==1?"新增预约,":"取消预约,");		
		sb.append(" 教练姓名 : "+dca.getDtHrStaff().getStaffName()+";");
		sb.append(" 性别 :" +dca.getDtHrStaff().getSex()+";");
		sb.append(" 电话 :" +dca.getDtHrStaff().getReference()+",");
		sb.append(" 学员姓名 : "+dsd.getDtHrStaff().getStaffName()+";");
		sb.append(" 性别 :" +dsd.getDtHrStaff().getSex()+";");
		sb.append(" 电话 :" +dsd.getDtHrStaff().getReference()+",");
		for (BaseBean baseBean : reDdList) {
			Ddsrreservationrecord  dd=(Ddsrreservationrecord) baseBean;
			/**
			 * 构造短信信息
			 */
			sb.append(" 科目 : "+("10".equals(dd.getRereSubjects().toString())?"科一":"20".equals(dd.getRereSubjects())?"科二":"30".equals(dd.getRereSubjects())?"科三":"科四")+";");
			try {
			sb.append(" 日期 : "+DateUtil.toStrDateFromUtilDateByFormat(dd.getRereAppdate(),"yyyy-MM-dd")+";");
			} catch (ParseException e) {
			}
			sb.append(" 时间段 : "+dd.getRereStadate()+"~"+dd.getRereEnddate()+",");			
		}
		mobileMessage.setMobile("18600580298"+","+"18600580298");
		mobileMessage.setContent(sb.toString());
		boolean b=false;
		try {
			if("ok".equals(mobileMessage.sendMsg())){
				b=true;
			}
		} catch (IOException e) {
		}
		return b;
		
	}
	/**
	 * 验证 某个学员 某天  某个时间段  是否 存在交集（重叠）
	 * @param  listNo    即将预约时间段
	 * @param  listYes   已预约时间段
	 * @return  Object[0] : true（相交） or false （不相交）, Object[1] : (message)
	 * */
	private Object[] validataPeriodOfTimeByStudent(List<BaseBean> listNo,List<BaseBean> listYes){
		Object[]  result_a=new Object[2];
		result_a[0]=false;
		if(listYes==null||listYes.size()==0){
			result_a[0]=false;
		}else{
			for (int i = 0; i < listNo.size(); i++) {
				Ddsrreservationrecord dd_no= (Ddsrreservationrecord) listNo.get(i);
				Date strDate_no=DateUtil.parseDate("HH:mm", dd_no.getRereStadate());
				Date endDate_no=DateUtil.parseDate("HH:mm",dd_no.getRereEnddate());
				for (int j = 0; j < listYes.size(); j++) {
					Ddsrreservationrecord dd_yes= (Ddsrreservationrecord) listYes.get(j);
					Date strDate_yes=DateUtil.parseDate("HH:mm", dd_yes.getRereStadate());
					Date endDate_yes=DateUtil.parseDate("HH:mm",dd_yes.getRereEnddate());
					if(strDate_yes.equals(strDate_no)||(strDate_yes.after(strDate_no)&&strDate_yes.before(endDate_no))
					   ||(endDate_yes.after(strDate_no)&&endDate_yes.before(endDate_no))){
						result_a[0]=true;
						result_a[1]="即将预约时间段 ("+dd_no.getRereStadate()+"~"+dd_no.getRereEnddate()+") 与 已预约时间段("+dd_yes.getRereStadate()+"~"+dd_yes.getRereEnddate()+") 相交!!";
						return result_a;
					}
				}
			}
		}
		return result_a;
	}
	/**
	 * 
	 * @see 扣除或者返还学员学时
	 * @author zgzg
	 * @param  sd 学员
	 * @param  sum 学时
	 * @param  b (0 ：扣除 ，1 ：返还)
	 * @return Object[0] : sd , Object[1] : ( 1 ：success  0 ：fail   2  : 异常)
	 */
	private Object[] toAddOrSubtractDssrstudentByRemhour(Dssrstudent sd,int sum,int b){
		Object[]  result_a=new Object[2];
		/**
		 * add
		 */
		if(b==0&&sd!=null){
				int remHour=sd.getStudRemhour();
				if(remHour>=sum){
					Short remHour1=(short) (remHour-sum);
					sd.setStudRemhour(remHour1);
					result_a[0]=sd;
					result_a[1]=1;
				}else{
					result_a[0]=sd;
					result_a[1]=0;
				}
		}else if(b==1&&sd!=null){
			/**
			 * del
			 */
			Short remHour1=(short) (sd.getStudRemhour()+sum);	
			sd.setStudRemhour(remHour1);
			result_a[0]=sd;
			result_a[1]=1;
			
		}else{
			/**
			 * other
			 */
			result_a[0]=sd;
			result_a[1]=2;
		}
		return result_a;
	}
	/**
	 * 查询
	 * @return
	 */
	public String toSearchByReservationRecordSummary(){
		session.put("tablesearch", dssrstudent);
		session.put("tablesearch1", ddsrcoach);
		session.put("tablesearch2", ddsrreservationrecord);
		return getListReservationRecordSummary();
	}
	/**
	 * 预约记录汇总列表
	 * @param args
	 */
	public String getListReservationRecordSummary(){
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getReservationRecordSummaryBySql().get(0).toString()
				, "select count(1) from("+getReservationRecordSummaryBySql().get(0).toString()+")",  
				(Object[])getReservationRecordSummaryBySql().get(1));
		return "reservationRecordSummary_List";
	}
	/**
	 * 预约记录汇总打印
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public String getListReservationRecordSummaryPrint(){
		ddsrreservationrecordsummaryList=baseBeanService.getListBeanBySqlAndParams(getReservationRecordSummaryBySql().get(0).toString(), (Object[])getReservationRecordSummaryBySql().get(1));
		return "reservationRecordSummary_List_Print";
	}
	/**
	 * 构造预约汇总查询sql
	 * @param args
	 */
	public List<Object> getReservationRecordSummaryBySql(){
		CAccount account = (CAccount) session.get("account");
		List<Object> sqlAndParam=new ArrayList<Object>();
		List<Object> param=new ArrayList<Object>();
		
		StringBuffer sql=new StringBuffer("");
		StringBuffer sqlSelectStatic=new StringBuffer(" select ");
		
		sqlSelectStatic.append("dsrr.rere_key rere_key,");
		sqlSelectStatic.append("dsrr.rere_companyid rere_companyid,");
		sqlSelectStatic.append("dsrr.rere_appdate rere_appdate,");
		sqlSelectStatic.append("dsrr.rere_class rere_class,");
		sqlSelectStatic.append("dsrr.rere_stadate rere_stadate,");
		sqlSelectStatic.append("dsrr.rere_enddate rere_enddate,");
		sqlSelectStatic.append("dsrr.rere_peoplesum rere_peoplesum,");
		sqlSelectStatic.append("dsrr.rere_subjects rere_subjects,");
		sqlSelectStatic.append("hs.STAFFNAME STAFFNAME0,");
		sqlSelectStatic.append("hs.REFERENCE REFERENCE0,");
		sqlSelectStatic.append("hs1.STAFFNAME STAFFNAME1,");
		sqlSelectStatic.append("hs1.REFERENCE REFERENCE1");
		
		sql.append(sqlSelectStatic);
		StringBuffer sqlFromStatic=new StringBuffer(" from ");
		sqlFromStatic.append(" ddsrreservationrecord dsrr ");
		sqlFromStatic.append(" left join ddsrcoach dsc on dsc.coac_key=dsrr.coac_key ");
		sqlFromStatic.append(" left join dt_hr_staff hs on hs.staffkey=dsc.coac_key ");
		sqlFromStatic.append(" left join Re_Dssrstudent_Ddsrresrecord rddr on rddr.rere_key=dsrr.rere_key ");
		sqlFromStatic.append(" left join dssrstudent dss on dss.stud_key=rddr.stud_key ");
		sqlFromStatic.append(" left join dt_hr_staff hs1 on hs1.staffkey=dss.stud_key ");
		sql.append(sqlFromStatic);
		
		StringBuffer sqlWhereStatic=new StringBuffer(" where ");
		sqlWhereStatic.append(" 1=1 ");
		if(account!=null&&null!=account.getCompanyID()&&!"".equals(account.getCompanyID())){
			sqlWhereStatic.append(" and dsrr.rere_companyid=? ");
			param.add(account.getCompanyID());
		}
		if(dssrsubject!=null){
			if(dssrsubject.getSubjType()!=null&&!"".equals(dssrsubject.getSubjType())){
				sqlWhereStatic.append(" and dsrr.rere_subjects=? ");
				param.add(dssrsubject.getSubjType());
			}
		}
		if(search!=null&&"search".equals(search)){
			Dssrstudent dssrstudent=(Dssrstudent) session.get("tablesearch");
			Ddsrcoach ddsrcoach=(Ddsrcoach) session.get("tablesearch1");
			Ddsrreservationrecord ddsrreservationrecord=(Ddsrreservationrecord) session.get("tablesearch2");
			if(ddsrcoach!=null){
				if(ddsrcoach.getDtHrStaff()!=null){
					if(ddsrcoach.getDtHrStaff().getStaffName()!=null&&!"".equals(ddsrcoach.getDtHrStaff().getStaffName().trim())){
						sqlWhereStatic.append(" and hs.STAFFNAME like ? ");
						param.add("%"+ddsrcoach.getDtHrStaff().getStaffName().trim()+"%");
					}
				}
			}
			if(dssrstudent!=null){
				if(dssrstudent.getDtHrStaff()!=null){
					if(dssrstudent.getDtHrStaff().getStaffName()!=null&&!"".equals(dssrstudent.getDtHrStaff().getStaffName().trim())){
						sqlWhereStatic.append(" and hs1.STAFFNAME like ? ");
						param.add("%"+dssrstudent.getDtHrStaff().getStaffName().trim()+"%");
					}
				}
			}
			if(ddsrreservationrecord!=null){
				if(ddsrreservationrecord.getSearchStaDate()!=null&&!"".equals(ddsrreservationrecord.getSearchStaDate())&&ddsrreservationrecord.getSearchEndDate()!=null&&!"".equals(ddsrreservationrecord.getSearchEndDate()))
				{
					sqlWhereStatic.append(" and dsrr.rere_appdate between ? and ? ");
					param.add(ddsrreservationrecord.getSearchStaDate());
					param.add(ddsrreservationrecord.getSearchEndDate());
				}
			}
		}else{
			Date searchStaDate=null;
			Date searchEndDate=null;
			Calendar calendarStaDate=DateUtil.toCalendarFromUtilDate(new Date());
			Calendar calendarEndDate=DateUtil.toCalendarFromUtilDate(new Date());
			try {
				searchStaDate=DateUtil.parseDate("yyyy-MM-dd", DateUtil.toStrDateFromUtilDateByFormat(calendarStaDate.getTime(),"yyyy-MM-dd"));
				calendarEndDate.add(Calendar.DAY_OF_MONTH, 6);
				searchEndDate=DateUtil.parseDate("yyyy-MM-dd", DateUtil.toStrDateFromUtilDateByFormat(calendarEndDate.getTime(),"yyyy-MM-dd"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.error("操作异常", e);
			}
			sqlWhereStatic.append(" and dsrr.rere_appdate between ? and ? ");
			param.add(searchStaDate);
			param.add(searchEndDate);
			
		}
		sql.append(sqlWhereStatic);
		
		StringBuffer sqlOrderByStatic=new StringBuffer(" order by ");
		sqlOrderByStatic.append(" dsrr.rere_appdate asc,");
		sqlOrderByStatic.append(" dsrr.rere_class asc");
		sql.append(sqlOrderByStatic);
		sqlAndParam.add(sql);
		sqlAndParam.add(param.toArray());
		return sqlAndParam;
	}
	public static void main(String[] args) {
			/*Date date=DateUtil.parseDate("HH:mm", "05:30");
			Date date1=DateUtil.parseDate("HH:mm","07:30");
			logger.info("调试信息");
			Date t1 = new Date();
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(t1);
	        cal.set(Calendar.DAY_OF_MONTH, 1);
	        logger.info("调试信息");
	        cal.add(Calendar.DAY_OF_MONTH, 1);
	        try {
				logger.info("调试信息");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.error("操作异常", e);
			}*/
	      
	        
	        
	        /*try {
	        	SendSMS ss=new SendSMS();
				ss.setServicesRequestAddRess("http://sms.c8686.com/Api/BayouSmsApi.aspx");
				ss.setUsername("8024980142");
				ss.setPassword("9b0616e53eebc7da7c2bb05e0452f65e");
				ss.setMobiles("18600580298");
				ss.setMessage("测试");
				ss.sendSMS().get("message");
				for (String str : ss.sendSMS().values()) {
					logger.info("调试信息");
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
			}*/
		/*logger.info("调试信息");
		logger.info("调试信息");
		logger.info("调试信息");*/
		// TODO Auto-generated method stub
		  String string = "慈";
		  byte [] b = null;
		  try {
		    b = string.getBytes("GBK");
		  } catch (UnsupportedEncodingException e) {
		   // TODO Auto-generated catch block
		   logger.error("操作异常", e);
		  }
		  for(int i=0; i< b.length ; i++){
		   logger.info("调试信息");
		  }
		  String fString = new String(b);
		  System.out.print(fString);

	}
    public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public UpLoadFileService getFileService() {
		return fileService;
	}

	public void setFileService(UpLoadFileService fileService) {
		this.fileService = fileService;
	}

	public DtMicroletterMenuContent getDtMicroletterMenuContent() {
		return dtMicroletterMenuContent;
	}

	public void setDtMicroletterMenuContent(
			DtMicroletterMenuContent dtMicroletterMenuContent) {
		this.dtMicroletterMenuContent = dtMicroletterMenuContent;
	}

	public DtMicroletterMenu getDtMicroletterMenu() {
		return dtMicroletterMenu;
	}

	public void setDtMicroletterMenu(DtMicroletterMenu dtMicroletterMenu) {
		this.dtMicroletterMenu = dtMicroletterMenu;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Ddsrreservationrecord getDdsrreservationrecord() {
		return ddsrreservationrecord;
	}

	public void setDdsrreservationrecord(Ddsrreservationrecord ddsrreservationrecord) {
		this.ddsrreservationrecord = ddsrreservationrecord;
	}

	public Ddsrcoach getDdsrcoach() {
		return ddsrcoach;
	}

	public void setDdsrcoach(Ddsrcoach ddsrcoach) {
		this.ddsrcoach = ddsrcoach;
	}

	public List<BaseBean> getDdsrreservationrecordList() {
		return ddsrreservationrecordList;
	}

	public void setDdsrreservationrecordList(
			List<BaseBean> ddsrreservationrecordList) {
		this.ddsrreservationrecordList = ddsrreservationrecordList;
	}
	public String getRereKeyString() {
		return rereKeyString;
	}
	public void setRereKeyString(String rereKeyString) {
		this.rereKeyString = rereKeyString;
	}
	public Dssrstudent getDssrstudent() {
		return dssrstudent;
	}
	public void setDssrstudent(Dssrstudent dssrstudent) {
		this.dssrstudent = dssrstudent;
	}
	public ReDssrstudentDdsrresrecord getReDssrstudentDdsrresrecord() {
		return reDssrstudentDdsrresrecord;
	}
	public void setReDssrstudentDdsrresrecord(
			ReDssrstudentDdsrresrecord reDssrstudentDdsrresrecord) {
		this.reDssrstudentDdsrresrecord = reDssrstudentDdsrresrecord;
	}
	public Staff getDtHrStaff() {
		return dtHrStaff;
	}
	public void setDtHrStaff(Staff dtHrStaff) {
		this.dtHrStaff = dtHrStaff;
	}
	public Dssrsubject getDssrsubject() {
		return dssrsubject;
	}
	public void setDssrsubject(Dssrsubject dssrsubject) {
		this.dssrsubject = dssrsubject;
	}
	public List<BaseBean> getDdsrreservationrecordsummaryList() {
		return ddsrreservationrecordsummaryList;
	}
	public void setDdsrreservationrecordsummaryList(
			List<BaseBean> ddsrreservationrecordsummaryList) {
		this.ddsrreservationrecordsummaryList = ddsrreservationrecordsummaryList;
	}
	
	
}
