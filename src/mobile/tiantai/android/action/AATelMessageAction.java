package mobile.tiantai.android.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.vo.ContactCompanyView;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.TelMessage;
import hy.ea.bo.office.TquickMessage;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.FileUtil;
import hy.ea.util.MobileMessagenew;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.aspectj.apache.bcel.generic.Tag;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class AATelMessageAction{
	private static final Logger logger = LoggerFactory.getLogger(AATelMessageAction.class);
    private Log log = LogFactory.getFactory().getInstance(AATelMessageAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;   
	
	private TquickMessage tquickmessage;
	private Map<String,TelMessage> telMessageMap;
	private TelMessage telMessage;
	private File txtFile;				  //前台电话号码文件
	private String txtContentType;		  //前台电话号码文件类型
	private String telNumber;             //前台电话号码
	
	private int accountAll;			//发送的电话号码总数
	private int accountSuccess;		//发送成功的号码总数
	private String parameter; 
	private PageForm pageForm;
	private String search; 
	private String sdate;
	private String edate;
	private int pageNumber;
	private Staff userInfo;
	
	private Object result;
	private InputStream excelStream;
	private String message;//提示信息
	private String chainParam;//chainParam="chainParam";判断是否为android请求
	
	private String isforward;
	
	private String type;
	private String telMessageID;
	@Autowired
	private MobileMessagenew mobileMessage;//短信接口 
	private String title;
	
	private String  orgDetail;
	private String fulldata;
	private String companyName;
	private String ralation;
	private String orgID;
	private String filename;
	private String organizationid;
	private String telMessageid;
	private String companyid;
	private String accountid;
	private String staffid;
	private String receiverCompanyName;
	private String receiverName;
	private String data;
	
	
		//短信息列表
		public String getTelMessageList() {
			JSONObject jret = new JSONObject();
			List<Object> param = new ArrayList<Object>();
			String sql = " from DTTELMESSAGE t where companyid=? and orgDetail=? ";
			param.add(companyid);
			param.add(orgDetail);
			if (type == null || !type.equals("current") || type.equals("1")) {
				sql += " and organizationid=?";
				param.add(organizationid);
			}
			
			if (search != null && "search".equals(search)) {
				if (telMessage.getStatus() != null
						&& !"".equals(telMessage.getStatus())) {
					sql += " and status like ? ";
					param.add("%" + telMessage.getStatus() + "%");
				}
			}
			
			sql += " order by sendTime desc";
			pageForm = baseBeanService
					.getPageFormBySQL(
							(null != pageForm ? pageForm.getPageNumber() : 1),
							(pageNumber == 0 ? 10 : pageNumber),
							"select t.key,t.telMessageID,t.staffName,to_char(t.sendTime,'yyyy-mm-dd'),t.msgStatus,t.receiverName,t.receiverCompanyName,t.telNum,t.content"
									+ sql, "select count(*)" + sql, param.toArray());
			if (pageForm != null && pageForm.getList() != null
					&& pageForm.getList().size() > 0) {
				List<Object> list = new ArrayList<Object>();
				for (Object page : pageForm.getList()) {
					Object[] object = (Object[]) page;
					JSONObject json = new JSONObject();
					json.accumulate("key", object[0] == null ? "" : object[0]);
					json.accumulate("telMessageID", object[1] == null ? ""
							: object[1]);
					json.accumulate("staffname", object[2] == null ? "" : object[2]);
					json.accumulate("sendTime", object[3] == null ? "" : object[3]);
					json.accumulate("msgStatus", object[4] == null ? "" : object[4]);
					json.accumulate("receiverName", object[5] == null ? ""
							: object[5]);
					json.accumulate("receiverCompanyName", object[6] == null ? ""
							: object[6]);
					json.accumulate("telNum", object[7] == null ? "" : object[7]);
					json.accumulate("content", object[8] == null ? "" : object[8]);
					
					list.add(json);
					
				}
				jret.accumulate("list", list);
				jret.accumulate("pageNumber", pageForm.getPageNumber());
				jret.accumulate("pagesize", pageForm.getPageSize());
				jret.accumulate("result", "data");
			} else {
				jret.accumulate("result", "nodata");
			}
			result = jret;
			return "success";
		}
		
		
	/**
	 * 查询公司
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCompany() {
		List<Object> params = new ArrayList<Object>();
		JSONObject jret = new JSONObject();
		
		String sql =" from DTCOMPANY t where t.groupCompanySn=(select groupCompanySn from DTCOMPANY d where  d.companyid = ?)";
		params.add(companyid);

		List<Object> lists = baseBeanService.getListBeanBySqlAndParams(
						"select t.companyID,t.companyName"
								+ sql, params
								.toArray());
		
		if (lists.size() > 0) {
		    for (Object object : lists) {
		    	JSONObject json = new JSONObject();
		    	Object[] o=(Object[])object;
		    	json.accumulate("companyID",o[0]==null?"": o[0]);
		    	json.accumulate("companyName",o[1]==null?"": o[1]);
		    	jret.accumulate("list", json);
			}
		  
			jret.accumulate("result", "data");
		} else {
			jret.accumulate("result", "nodata");
		}
		result = jret;
		return "success";

	}
	/**
	 * 查询往来单位列表
	 * @return
	 */
	public String getContactCompanyList() {
		List<Object> params = new ArrayList<Object>();
		JSONObject jret = new JSONObject();

		String sql = " from VIEW_CONCOM  where companyid=?";
		params.add(companyid);

		pageForm = baseBeanService
				.getPageFormBySQL(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber == 0 ? 10 : pageNumber),
						"select companyName,responsibleTel,relation"
								+ sql, "select count(*)" + sql, params.toArray());
		if (pageForm != null && pageForm.getList() != null
				&& pageForm.getList().size() > 0) {
			List<Object> list = new ArrayList<Object>();
			for (Object page : pageForm.getList()) {
				Object[] object = (Object[]) page;
				JSONObject json = new JSONObject();
				json.accumulate("companyName", object[0] == null ? ""
						: object[0]);
				json.accumulate("responsibleTel", object[1] == null ? ""
						: object[1]);
				json.accumulate("relation",object[2]==null?"":object[2] );
				list.add(json);

			}
			jret.accumulate("list", list);
			jret.accumulate("pageNumber", pageForm.getPageNumber());
			jret.accumulate("pagesize", pageForm.getPageSize());
			jret.accumulate("result", "data");

		} else {
			jret.accumulate("result", "nodata");
		}
		result = jret;
		return "success";

	}
		
	/**
	 * 获取在职员工
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCompanyPeople() {
		List<Object> params = new ArrayList<Object>();
		JSONObject jret = new JSONObject();
		String sql =" from DTCOS d "
				+ "join DT_HR_STAFF s on d.staffid=s.staffid "
				+ "join T_ACCOUNT t on d.staffid=s.staffid "
				+ "where d.companyid = ?  and d.cosStatus = '50' group by s.staffName,s.reference";
		params.add(companyid);
		List<Object> lists = baseBeanService
				.getListBeanBySqlAndParams("select s.staffName,s.reference"
						+ sql, params.toArray());
		ralation="在职员工";
		if(lists.size()>0){
		for(Object o:lists){
			JSONObject  temp = new JSONObject();
		   Object[] a = (Object[])o;
		   temp.accumulate("staffName", a[0]==null?"":a[0]);
		   temp.accumulate("reference", a[1]==null?"":a[1]);
		   temp.accumulate("relation", ralation);
		   jret.accumulate("list",temp);
 		}
			jret.accumulate("result", "data");
		} else {
			jret.accumulate("result", "nodata");
		}
		result = jret;
		return "success";

	}
	
	/**
	 * 获取个人
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCodeCmRalation() {
		JSONObject jret = new JSONObject();
            List<Object> params= new ArrayList<Object>();
            String sql = " from view_conuser c  where c.companyID = ? group by c.staffname,c.reference,c.relation";
            	
            		
			params.add(companyid);
			List<Object> lists = baseBeanService.getListBeanBySqlAndParams("select c.staffname,c.reference,c.relation"+sql,params.toArray());
			int count =baseBeanService.getConutByBySqlAndParams("select count(*)"+sql, params.toArray());
             if(lists.size()>0){
            	 for (Object page : lists) {
            		JSONObject temp=new JSONObject();
					Object[] o=(Object[]) page;
					temp.accumulate("staffname",o[0]==null?"":o[0] );
					temp.accumulate("refrence",o[1]==null?"":o[1] );
					temp.accumulate("relation",o[2]==null?"":o[2] );
					jret.accumulate("list", temp);
				}
            	 jret.accumulate("count", count);
            	 jret.accumulate("result", "data");
             }else{
            	 
            	 jret.accumulate("result", "nodata");
             }
		    result=jret;
			return "success";
		}
	
	/**
	 * 
	 * 
	 * 失败的短信重新发送；
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String repeatSend() {
		String hql = "from TelMessage where telMessageID = ?";
		telMessage = (TelMessage) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{telMessageID});
		telNumber = telMessage.getTelNum();
		return sendMessage();
		
	}
	
	/**
	 * 发送短消息接口
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("unchecked")
	public String sendMessage() {
		List<BaseBean> beans=new ArrayList<BaseBean>();
		JSONObject jret = new JSONObject();
		List<Object> params=new ArrayList<Object>();
		//发送消息到外部系统
		String tels  =	telNumber;
		
		String reStr = "";	//消息发送返回的状态
		String t = "";
		try { 	 
			String d = tels.replaceAll("[^0-9-]+", " ").trim();
			       t = d.replaceAll("[ ]+", ","); 
			
			mobileMessage.setContent(telMessage.getContent());
			mobileMessage.setMobile(telNumber);
			reStr = mobileMessage.sendMsg(); 
			logger.info("值：{}", reStr);
			 
		} catch (Exception e) {
			log.error("TelMessageAction.sendMessage()" + e.getMessage());
			logger.error("操作异常", e);
		}
			beans = new ArrayList<BaseBean>();
			List<String> telList = new ArrayList<String>(); 
			
				String sql = "select t.accountid,s.staffname,m.companyname"
						+ " from DTCACCOUNT t"
						+ " left join dtcorganization c on t.companyid = c.companyid"
						+ " left join dt_hr_staff s on s.staffid = t.staffid"
						+ " left join DTCOMPANY m  on t.companyid = m.companyid"
						+ " where  t.companyid = ? and c.organizationid=? and s.staffid=?";
				params.add(companyid);
				params.add(organizationid);
				params.add(staffid);
				List<Object> bean= baseBeanService.getListBeanBySqlAndParams(sql, params.toArray());
			//记录系统外的短消息
			String[] tel = tels.split(";");
			String telIDs = "";
			if(bean.size()>0){
				for (Object b : bean) {
					Object[] object=(Object[]) b;
					
				
			for(int i = 0 ;i<tel.length;i++){
				String TEL = tel[i].trim();
				if(TEL.length()>0){
					if(null==telMessageMap||!telList.contains(TEL)){
						if(type!=null&&type.equals("repeat")){
							 telMessage.setSendDate(new Date());
							 
							 telMessage.setMsgStatus(reStr);
							 
							 telMessage.setSendTime(new Date());
							  telMessage.setAccountID((String) object[0]);
						      telMessage.setStaffName((String) object[1]);
							 
							 telMessage.setStatus(telMessage.getStatus());
							 
							 baseBeanService.update(telMessage);
						}else{
					      String telID = serverService.getServerID("telmessage");
					      telMessage.setTelMessageID(telID);
					      telIDs+=telID+",";
						
					      telMessage.setAccountID((String) object[0]);
					      telMessage.setStaffName((String) object[1]);
					      telMessage.setCompanyName((String) object[2]);
					      telMessage.setCompanyID(companyid);
					      telMessage.setOrganizationID(organizationid);
					      telMessage.setStaffID(staffid);
					      telMessage.setOrgDetail("office");
					      
					  
						 telMessage.setReceiverCompanyName(receiverCompanyName);
						 telMessage.setReceiverName(receiverName);
						 if(data.equals("company")){
							 telMessage.setConnection(ralation);
						 }
						 if(data.equals("person")){
							 telMessage.setRalation(ralation);
						 }
						 telMessage.setTelNum(TEL);
						 
						 telMessage.setStatus("01");
						 
						 telMessage.setSendDate(new Date());
						 
						 telMessage.setMsgStatus(reStr);
						 
						 telMessage.setSendTime(new Date());
						 
						 beans.add(telMessage);
						 
						} 
					}
				}
				}
				}
			}
			if(type==null||type.equals("1")||type.equals("undefined")){
			   baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
			   jret.accumulate("result", "suc");
			 }
     	//将电话号码，发送内容，记录的ID都记录下
			
			if(!reStr.equals("ok")){
				
				String writecontent = "";
				writecontent+="telnumber:"+t+"\ntelID:"+telIDs.substring(0,telIDs.lastIndexOf(","))+"\nsendcontent:"+telMessage.getContent()+"\nerror:"+reStr;
				
				createErrorFile(writecontent,telMessage.getContent());
				jret.accumulate("result", "fail");
			}
			
			 result=jret;
			 return "success";
		}
		
			
		

	//批量再次发送
	public String resendMessage(){
	   CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		
       String tels  =	telNumber;
		

		String reStr = "";	//消息发送返回的状态
		try { 	 
		
			mobileMessage.setContent(telMessage.getContent());
			mobileMessage.setMobile(tels);
			reStr = mobileMessage.sendMsg(); 
			logger.info("值：{}", reStr);
			
			//根据返回状态修改
			
			if(reStr.equals("ok")){
			String telIDs = telMessage.getTelMessageID();
			String[] arraytelID = telIDs.split(",");
			String hql = "from TelMessage where telMessageID = ?";
			TelMessage telm = null;
			for (int i = 0; i < arraytelID.length; i++) {
			  telm = (TelMessage) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{arraytelID[i]});
			  if(telm!=null){
			    telm.setMsgStatus(reStr);
			    telm.setContent(telMessage.getContent());
			    baseBeanService.update(telm);
			  }
			  
			  }
			
			//删除日志文件
			String realpath = ServletActionContext.getRequest()
			.getSession().getServletContext().getRealPath("/");
			String errordirfile=realpath+"\\upload_files\\telmessage\\error\\"+account.getCompanyID();
			
			File file = new File(errordirfile+"\\"+filename);
			if(file.exists()){
				file.delete();
			}
			
			
			
			}
			 
		} catch (Exception e) {
			log.error("TelMessageAction.sendMessage()" + e.getMessage());
			logger.error("操作异常", e);
		}
	
		return "success";
	}
	//获取失败文件名称列表与地址
	
	public String getFailLog(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String realpath = ServletActionContext.getRequest()
		.getSession().getServletContext().getRealPath("/");
		String errordirfile=realpath+"\\upload_files\\telmessage\\error\\"+account.getCompanyID();
		File filedir = new File(errordirfile);
		List<String> filelist = new ArrayList<String>();
		if(filedir.isDirectory()){
			 File[] t = filedir.listFiles();
			 for(int i=0;i<t.length;i++){
				 filelist.add(t[i].getName());
			 }
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("filelist", filelist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		
		return "success";
	}
	
	//获取某一个列表的详细信息
	public String getFailDetail(){
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		HttpServletRequest  request = ServletActionContext.getRequest();
		String realpath = request.getSession().getServletContext().getRealPath("/");
		String errordirfile=realpath+"\\upload_files\\telmessage\\error\\"+account.getCompanyID();
		
		String filename = request.getParameter("filename");
		File file = new File(errordirfile+"\\"+filename);

		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file), "utf-8");// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			StringBuffer sb = new StringBuffer();
			while ((lineTxt = bufferedReader.readLine()) != null) {
				sb.append(lineTxt);
			}
			read.close();
			
			//对取出来的内容进行处理
			String content = sb.toString();
			JSONObject jsonObj = new JSONObject();
			jsonObj.accumulate("telnumber", content.substring(content.indexOf("telnumber")+10, content.indexOf("telID")));
			jsonObj.accumulate("telID", content.substring(content.indexOf("telID")+6, content.indexOf("sendcontent")));
			jsonObj.accumulate("sendcontent", content.substring(content.indexOf("sendcontent")+12,content.indexOf("error")));
			jsonObj.accumulate("error", content.substring(content.indexOf("error")+6));
			
            this.result = jsonObj.toString();

		} catch (Exception e) {

			logger.error("操作异常", e);
		}
		
		return "success";
	}
	
	
	
	// 创建失败记录txt文件
	public void createErrorFile(String writecontent,String sendcontent){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		
		String realpath = ServletActionContext.getRequest()
		.getSession().getServletContext().getRealPath("/");
		String errordirfile=realpath+"\\upload_files\\telmessage\\error\\"+account.getCompanyID();
		
		File errordir = new File(errordirfile);
		if(!errordir.exists()){
			errordir.mkdirs();
		}
		String filename ="";

		filename = "发送人"+userInfo.getStaffName()+Utilities.getDateString(new Date(),"yyyy-MM-dd-HH-mm-ss")+".txt";

		 
		File errortxt = new File(errordirfile+"\\"+filename);
		
	    if(!errortxt.exists()){
	    	try {
				errortxt.createNewFile();
				
				 FileOutputStream os = new FileOutputStream(errortxt, true);
				 os.write(writecontent.getBytes("utf-8"));
				
				
			} catch (IOException e) {
				
				logger.error("操作异常", e);
			}
	    }
		
	}
	
	
	
	/****************************短消息操作START************************************/
	//清空短消息
	 public String delTelMessageAll() {
		  JSONObject jret=new JSONObject();
		   try {
			List<Object> params=new ArrayList<Object>();  
			   String[] hql={"delete from TelMessage where companyID=?  and accountID = ? and organizationID = ? and orgDetail= ?"};
			   params.add(companyid);
			   params.add(accountid);
			   params.add(organizationid);
			   params.add(orgDetail);
			   List<BaseBean> beans=new ArrayList<BaseBean>();
			   baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params.toArray());
			   jret.accumulate("result", "suc");
		} catch (Exception e) {
			
			logger.error("操作异常", e);
			jret.accumulate("result", "fail");
		}  
		   result=jret;
		   return "success";
	    }
	//删除短信息
	 public String delTelMessage(){
		    JSONObject jret=new JSONObject();
		    try {
				List<Object> params=new ArrayList<Object>();
   
				String[] hql={"delete from TelMessage where companyID=?  and telMessageID = ?"};
				params.add(companyid);
				params.add(telMessageid);
				List<BaseBean> beans=new ArrayList<BaseBean>();
				baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params.toArray());
				jret.accumulate("result", "suc");
			} catch (Exception e) {
				logger.error("操作异常", e);
				jret.accumulate("result", "suc");
			}	    	
 	    	    result=jret;
		    	return "success";
		   	
	    }
	 
		
	   // 根据companyId orgID 查询部门在职员工
		public String getPersonByDept() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			if (account == null) {

				return "not_login";
			}
			String hql = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ? and (c.organizationID like ? "
					+ "or exists(select d.depPostID from DepartmentPost d where d.depPostID = c.depPostID and d.leveloneOrgID like ?)))";

			Object[] params = new Object[] { companyid, "50", "%"+orgID+"%",
					"%"+orgID+"%" };
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), hql, params);
		    ralation = "在职员工";
		    if(pageForm!=null)
			{
				session.put("RecordCount", pageForm.getRecordCount());
			}else{
				session.put("RecordCount", 0);
			}
			return "listuser";

		}
		
		
		
		
		
		/****************************短消息操作END************************************/

	
		
	//常用短信添加操作
		
		
      public String addQuickMessage(){
    	 Map<String,Object> map = new HashMap<String, Object>();
    	 Map<String, Object> session = ActionContext.getContext().getSession();
	     CAccount account = (CAccount) session.get("account"); 
	     if(account==null){
	    	 return "no_login";
	     }
    	 try{
         String qmsID = serverService.getServerID("qmsID");
	     tquickmessage.setQmsID(qmsID);
	     tquickmessage.setCompanyID(account.getCompanyID());
	     tquickmessage.setCreaterID(account.getStaffID());
	     tquickmessage.setCreateTime(new Date());
	     baseBeanService.save(tquickmessage);
	     map.put("result",qmsID);
    	 }catch(Exception e){
    		 map.put("result","fail"); 
    		 logger.error("操作异常", e);
    		
    	 }
         JSONObject jo = JSONObject.fromObject(map);
         this.result = jo.toString();
		 return "success";
	  }
      
      //删除常用短信
      public String deleteQuickMessage(){
     	 Map<String,Object> map = new HashMap<String, Object>();
     	 try{

	     String hql = "from TquickMessage where qmsID = ?";
	     TquickMessage tqms =  (TquickMessage) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{tquickmessage.getQmsID()});
         baseBeanService.deleteBeanByKey(TquickMessage.class,tqms.getKey()); 
     	 map.put("result", "suc");
     	 }catch(Exception e){
     		 map.put("result", "fail");
     		 logger.error("操作异常", e);
     	 }
         JSONObject jo = JSONObject.fromObject(map);
         this.result = jo.toString();
 		 return "success";
 	  }
      
      
      //获取常用短信
      
      public String getQuickMessage(){
     	  Map<String, Object> session = ActionContext.getContext().getSession();
 	      CAccount account = (CAccount) session.get("account"); 
 	     if(account==null){
 	    	 return "no_login";
 	     }
 	     
 	     String hql = "from TquickMessage where companyID = ?";
 	     List<BaseBean> qmslist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID()});
    	 Map<String,Object> map = new HashMap<String, Object>();
    	 map.put("qmslist", qmslist);
    	 JSONObject jo = JSONObject.fromObject(map);
    	 this.result = jo.toString();
    	 return "success";
      }
      
      
      
		
		
	public Map<String, TelMessage> getTelMessageMap() {
			return telMessageMap;
		}
		public void setTelMessageMap(Map<String, TelMessage> telMessageMap) {
			this.telMessageMap = telMessageMap;
		}
	public TelMessage getTelMessage() {
		return telMessage;
	}
	public void setTelMessage(TelMessage telMessage) {
		this.telMessage = telMessage;
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
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}


	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public File getTxtFile() {
		return txtFile;
	}
	public void setTxtFile(File txtFile) {
		this.txtFile = txtFile;
	}
	public String getTxtContentType() {
		return txtContentType;
	}
	public void setTxtContentType(String txtContentType) {
		this.txtContentType = txtContentType;
	}
	public String getTelNumber() {
		return telNumber;
	}
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	public int getAccountAll() {
		return accountAll;
	}
	public void setAccountAll(int accountAll) {
		this.accountAll = accountAll;
	}
	public int getAccountSuccess() {
		return accountSuccess;
	}
	public void setAccountSuccess(int accountSuccess) {
		this.accountSuccess = accountSuccess;
	}
	public Staff getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(Staff userInfo) {
		this.userInfo = userInfo;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getChainParam() {
		return chainParam;
	}
	public void setChainParam(String chainParam) {
		this.chainParam = chainParam;
	}
	public String getIsforward() {
		return isforward;
	}
	public void setIsforward(String isforward) {
		this.isforward = isforward;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getTelMessageID() {
		return telMessageID;
	}


	public void setTelMessageID(String telMessageID) {
		this.telMessageID = telMessageID;
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}
	

	public String getOrgDetail() {
		return orgDetail;
	}


	public void setOrgDetail(String orgDetail) {
		this.orgDetail = orgDetail;
	}
	
	public String getFulldata() {
		return fulldata;
	}


	public void setFulldata(String fulldata) {
		this.fulldata = fulldata;
	}
    
	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getRelation() {
		return ralation;
	}


	public void setRelation(String relation) {
		this.ralation = relation;
	}
	
	public String getOrgID() {
		return orgID;
	}


	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}


	public TquickMessage getTquickmessage() {
		return tquickmessage;
	}


	public void setTquickmessage(TquickMessage tquickmessage) {
		this.tquickmessage = tquickmessage;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}



	public String getTelMessageid() {
		return telMessageid;
	}


	public void setTelMessageid(String telMessageid) {
		this.telMessageid = telMessageid;
	}
	public String getOrganizationid() {
		return organizationid;
	}
	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}


	public String getAccountid() {
		return accountid;
	}


	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}


	public String getStaffid() {
		return staffid;
	}


	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}


	public String getRalation() {
		return ralation;
	}


	public void setRalation(String ralation) {
		this.ralation = ralation;
	}


	public String getReceiverCompanyName() {
		return receiverCompanyName;
	}


	public void setReceiverCompanyName(String receiverCompanyName) {
		this.receiverCompanyName = receiverCompanyName;
	}


	public String getReceiverName() {
		return receiverName;
	}


	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}


	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}


	
   

	
    
}
