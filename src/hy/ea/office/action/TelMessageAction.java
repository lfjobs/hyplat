package hy.ea.office.action;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.TelMessage;
import hy.ea.bo.office.TquickMessage;
import hy.ea.collage.service.PhlIndexSerivce;
import hy.ea.office.service.TelMessageService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.*;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TelMessageAction {
    private Log log = LogFactory.getFactory().getInstance(TelMessageAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;  
	@Resource
	private CLogBookService logBookService; 
	@Resource
	private ShowExcelService excelService;
	@Resource
	private TelMessageService telMessageService;
	@Resource
	private PhlIndexSerivce phlIndexSerivce;
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
	
	private String result;
	private Object results;
	private InputStream excelStream;
	private String message;//提示信息
	private String chainParam;//chainParam="chainParam";判断是否为android请求
	
	private String isforward;
	
	private String type;
	private String telMessageID;
	@Autowired
	private MobileMessagenew mobileMessage;//短信接口 
	private String companyID;
	private String title;
	
	private String  orgDetail;
	private String fulldata;
	private String companyName;
	private String relation;
	private String orgID;
	private String filename;
	

 public String goMessageIndex() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String hql1 = "select count(*) from TelMessage where accountID = ? and orgDetail = ?";
		String hql2 = "select count(*) from TelMessage where accountID = ? and orgDetail = ? and msgStatus = ?";
		Object[] params1 = new Object[] { account.getAccountID(), orgDetail };
		Object[] params2 = new Object[] { account.getAccountID(), orgDetail,
				"0" };
		if (type!=null&&type.equals("1")) {

			hql1 += " and organizationID = ?";
			hql2 += " and organizationID = ?";
			params1 = new Object[] { account.getAccountID(), orgDetail,
					organizationID };
			params2 = new Object[] { account.getAccountID(), orgDetail, "0",
					organizationID };
		}
		accountAll = baseBeanService.getConutByByHqlAndParams(hql1, params1);
		accountSuccess = baseBeanService
				.getConutByByHqlAndParams(hql2, params2);
		return "telmessageindex";
	}
	
	
	/**
	 * 
	 * 
	 * 失败的短信重新发送；
	 * 
	 * @return
	 */
	public String repeatSend(){
		String hql = "from TelMessage where telMessageID = ?";
		telMessage = (TelMessage) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{telMessageID});
		telNumber = telMessage.getTelNum();
		return sendMessage();
		
	}
	
	//发送短消息
	public String sendMessage(){
		//发送消息到外部系统
		String tels  =	telNumber;
		
		if(null != txtFile)
			tels = tels.concat(FileUtil.getStrFromText(txtFile));
		
		String reStr = "";	//消息发送返回的状态
		String t = "";
		try { 	 
			String d = tels.replaceAll("[^0-9-]+", " ").trim();
			       t = d.replaceAll("[ ]+", ","); //第二个参数是短信发送需要的电话号码分割url。etc.136855,11255
			
			mobileMessage.setContent(telMessage.getContent());
			mobileMessage.setMobile(t);
			reStr = mobileMessage.sendMsg(); 
			System.out.println(reStr);
			 
		} catch (Exception e) {
			log.error("TelMessageAction.sendMessage()" + e.getMessage());
			e.printStackTrace();
		}
		String[] fulldatas = fulldata.split(",");
		
		// 记录消息到系统
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<BaseBean> beans = new ArrayList<BaseBean>();
		List<String> telList = new ArrayList<String>(); 
		// 查询登录用户信息
		String hqlForUser = "from Staff where staffID = ?";
		userInfo = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForUser,
				new Object[] { account.getStaffID() });
		if(null!=telMessageMap){
			
			 for(TelMessage telmsg:telMessageMap.values()){
				
				 telmsg.setTelMessageID(serverService.getServerID("telmessage"));
				
				 telmsg.setCompanyID(account.getCompanyID());
				 
				 telmsg.setCompanyName(telMessage.getCompanyName());
				 
				 telmsg.setAccountID(account.getAccountID());
				 
				 telmsg.setStaffName(userInfo.getStaffName());
				 
				 telmsg.setContent(telMessage.getContent());
				 
				 telmsg.setStatus("00");
				 
				 telmsg.setSendDate(new Date());
				 
				 telmsg.setMsgStatus(reStr);
				 
				 telList.add(telmsg.getTelNum().trim()); 
				 
				 telmsg.setSendTime(new Date());
				 
				 beans.add(telmsg);
				 CLogBook  logbook1= logBookService.saveCLogBook(null, "保存单位/个人短消息:(电话号码:"+telmsg.getTelNum()+")", account);
				 beans.add(logbook1);
			 } 
		} 
		
		//记录系统外的短消息
		String[] tel = tels.split(";");
		String telIDs="";
		for(int i = 0 ;i<tel.length;i++){
			TelMessage tmsg = new TelMessage();
			String TEL = tel[i].trim();
			if(TEL.length()>0){
				if(null==telMessageMap||!telList.contains(TEL)){
					if(type!=null&&type.equals("repeat")){
						 telMessage.setSendDate(new Date());
						 
						 telMessage.setMsgStatus(reStr);
						 
						 telMessage.setSendTime(new Date());
						 
						 telMessage.setAccountID(account.getAccountID());
						 
						 telMessage.setStaffName(userInfo.getStaffName());
						 
						 telMessage.setStatus(telMessage.getStatus());
						 //后加
						 telMessage.setOrganizationID(organizationID);
						 telMessage.setOrgDetail(orgDetail);
						 
						 baseBeanService.update(telMessage);
					}else{
				      String telID = serverService.getServerID("telmessage");
 					  tmsg.setTelMessageID(telID);
				      telIDs+=telID+",";
					
					 tmsg.setCompanyID(account.getCompanyID());
					 
					 tmsg.setCompanyName(telMessage.getCompanyName());
					 
					 tmsg.setAccountID(account.getAccountID());
					 
					 tmsg.setStaffName(userInfo.getStaffName());
					 //后加
					 tmsg.setOrganizationID(organizationID);
					 tmsg.setOrgDetail(orgDetail);
					 
					 tmsg.setContent(telMessage.getContent());
					 
					 String data = fulldatas[i];
					 String[] datas = data.split("_");
					 tmsg.setReceiverCompanyName(datas[1]);
					 tmsg.setReceiverName(datas[2]);
					 if(datas[4].equals("company")){
						 tmsg.setConnection(datas[3]);
					 }
					 if(datas[4].equals("person")){
						 tmsg.setRalation(datas[3]);
					 }
					 
					 
					 
					 
					 
					 tmsg.setTelNum(TEL);
					 
					 tmsg.setStatus("01");
					 
					 tmsg.setSendDate(new Date());
					 
					 tmsg.setMsgStatus(reStr);
					 
					 tmsg.setSendTime(new Date());
					 
					 beans.add(tmsg);
					 
					 CLogBook  logbook1=logBookService.saveCLogBook(null, "保存外部短消息:(电话号码:"+tmsg.getTelNum()+")", account);
					 beans.add(logbook1);
					}
				}
			}
			
		}
		if(type==null||type.equals("1")||type.equals("undefined")){
		   baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		 }
		
		
		//记录失败
		
		
		//将电话号码，发送内容，记录的ID都记录下
		
		if(!reStr.equals("ok")){
			
			String writecontent = "";
			writecontent+="telnumber:"+t+"\ntelID:"+telIDs.substring(0,telIDs.lastIndexOf(","))+"\nsendcontent:"+telMessage.getContent()+"\nerror:"+reStr;
			
			createErrorFile(writecontent,telMessage.getContent());
			
		}
		
		
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
			System.out.println(reStr);
			
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
			e.printStackTrace();
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

			e.printStackTrace();
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
				
				e.printStackTrace();
			}
	    }
		
	}
	
	
	/****************************短消息操作START************************************/
	//清空短消息
	 public String delTelMessageAll()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),account.getAccountID(),session.get("organizationID"),orgDetail}; 
		    
		    CLogBook  logbook=logBookService.saveCLogBook(null, "删除短信息(账户ID:"+account.getAccountID()+")", account);
	    	String[] hql={"delete from TelMessage where companyID=?  and accountID = ? and organizationID = ? and orgDetail= ?"};
	    	List<BaseBean> beans=new ArrayList<BaseBean>();
	    	beans.add(logbook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
		    return "success";
	    }
	//删除短信息
	 public String delTelMessage()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),account.getAccountID(),telMessage.getTelMessageID()};
		    String hql2="from TelMessage where companyID=? and accountID = ?  and telMessageID = ? ";
		    TelMessage mr=(TelMessage) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    if(mr==null){
		    	message = "请删除当前用户发送的短信！";
		    	return "fail";
		    }
		    CLogBook  logbook=logBookService.saveCLogBook(null, "删除短信息(信息号:"+mr.getTelNum()+")", account);
	    	String[] hql={"delete from TelMessage where companyID=? and accountID = ? and telMessageID = ?"};
	    	List<BaseBean> beans=new ArrayList<BaseBean>();
	    	beans.add(logbook);
	    	baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);	    	
	    	if(chainParam!=null&&"chainParam".equals(chainParam)){//区别android客户端请求 转发
		    	return getTelMessageList();
		    }else{
		    	return "success";
		    }	
	    }
	 
	 //根据条件查询短信息列表
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", telMessage);
			return getTelMessageList();
		}
	 //短信息列表
		public String getTelMessageList() {
			
		    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		    isforward="01";
		    if(chainParam!=null&&"chainParam".equals(chainParam)){ //区别android客户端请求 转发
		    	return "success_android";
		    }else{
		    	return "telmessagelist";
		    }	
		}
		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String companyID1 = "";
			String organizationID1 = "";
			if("chainParam".equals(chainParam)){
				companyID1=companyID;
			}else{
				companyID1 = account.getCompanyID();
				organizationID1 = (String) session.get("organizationID");
			}
			DetachedCriteria dc = DetachedCriteria.forClass(TelMessage.class);
			
			if(type!=null&&type.equals("group")){
				String hql = "from Company where companyID = ?";

				hql = "select companyID from Company where companyPID = ? or companyID = ?";

				List<BaseBean> list = baseBeanService
						.getListBeanByHqlAndParams(hql,
								new Object[] {companyID1,
								companyID1 });

				dc.add(Restrictions.in("companyID", list));
				dc.add(Restrictions.eq("orgDetail", orgDetail));
				
				
				
			}else if("chainParam".equals(chainParam)){
				dc.add(Restrictions.eq("companyID", companyID1));
			}else{
			  dc.add(Restrictions.eq("companyID", companyID1));
	          dc.add(Restrictions.eq("orgDetail", orgDetail));
			   if(type==null||!type.equals("current")||type.equals("1")){
	               dc.add(Restrictions.eq("organizationID", organizationID1));
			   }
			  
			}
			if (search != null && search.equals("search")) {
				telMessage = (TelMessage) session.get("tablesearch");
				if(null!=telMessage.getTelNum()&&!"".equals(telMessage.getTelNum()))
				{
					dc.add(Restrictions.like("telNum", telMessage.getTelNum(), MatchMode.ANYWHERE));
				} 
				if(null!=telMessage.getStatus()&&!"".equals(telMessage.getStatus()))
				{
					dc.add(Restrictions.eq("status", telMessage.getStatus()));
				}
				if(null!=telMessage.getCompanyName()&&!"".equals(telMessage.getCompanyName()))
				{
					dc.add(Restrictions.like("companyName", telMessage.getCompanyName().trim(),MatchMode.ANYWHERE));
				}
				if(null!=telMessage.getStaffName()&&!"".equals(telMessage.getStaffName()))
				{
					dc.add(Restrictions.eq("staffName", telMessage.getStaffName().trim()));
				}
				
				if(null!=telMessage.getMsgStatus()&&!"".equals(telMessage.getMsgStatus())&&!"all".endsWith(telMessage.getMsgStatus()))
				{
					if(telMessage.getMsgStatus().equals("suc")){
						dc.add(Restrictions.eq("msgStatus", "0"));
					}
					if(telMessage.getMsgStatus().equals("fail")){
						dc.add(Restrictions.not(Restrictions.eq("msgStatus","0")));
						dc.add(Restrictions.not(Restrictions.eq("msgStatus","1")));
						dc.add(Restrictions.not(Restrictions.eq("msgStatus","2")));
					}
					
					if(telMessage.getMsgStatus().equals("other")){
						dc.add(Restrictions.or(Restrictions.eq("msgStatus","1"),Restrictions.eq("msgStatus", "2")));
						
					}
				}
				if(!("").equals(sdate)&&sdate!=null&&edate!=null&&!("").equals(edate))
				{
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					try {
						dc.add(Restrictions.between("sendDate",dateFormat.parse(sdate),dateFormat.parse(edate)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if(telMessage.getRalation()!=null&&!telMessage.getRalation().equals("")){
					dc.add(Restrictions.like("ralation",telMessage.getRalation(),MatchMode.ANYWHERE));
				}
				
				if(telMessage.getConnection()!=null&&!telMessage.getConnection().equals("")){
					dc.add(Restrictions.like("connection",telMessage.getConnection(),MatchMode.ANYWHERE));
				}
			} 
			dc.addOrder(Order.desc("sendDate"));
			return dc;
		}
		
		// 导出列表
		public String showTelMessageExcel() {
			excelStream = excelService.showExcel(TelMessage.columnHeadings(), baseBeanService.getListByDC(getList()));
			CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
			CLogBook  logbook=logBookService.saveCLogBook(null,"导出短消息表", account);
			baseBeanService.update(logbook);
			return "showexcel";
		}
		
		
		//获取往来单位关系
		public String getCCompanyRalation(){
			List<BaseBean> list = null;
			if (companyID == null || companyID.equals("")) {

				Map<String, Object> session = ActionContext.getContext().getSession();
				CAccount account = (CAccount) session.get("account");
				String hqlcom = "from Company where companyID = ?";
				String groupCompanySn = ((Company) baseBeanService
						.getBeanByHqlAndParams(hqlcom, new Object[] { account
								.getCompanyID() })).getGroupCompanySn();
				
				String sql = "select distinct c.codeValue from dtCCode c where exists (select companyID from dtCompany s where groupCompanySn = ? and c.companyID = s.companyID) and codePID = ? order by codeValue";
				list = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{groupCompanySn,"scode20110224xpd2t2jvda0000000002"});

			}else{
			
			
			Object [] params = {companyID,"scode20110224xpd2t2jvda0000000002"};
			String hql = "from CCode where companyID = ? and codePID = ? order by codeNumber";
			list = baseBeanService.getListBeanByHqlAndParams(hql,params);
			}
			
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("relationlist", list);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
			return "success";
		}
		
		//获取往来往来个人关系
		public String getCodeCmRalation() {
	try{
		List<BaseBean> list = null;

		if (companyID == null || companyID.equals("")) {

			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String hqlcom = "from Company where companyID = ?";
			String groupCompanySn = ((Company) baseBeanService
					.getBeanByHqlAndParams(hqlcom, new Object[] { account
							.getCompanyID() })).getGroupCompanySn();
			
			String sql = "select distinct c.codeValue from dtCCode c where exists (select companyID from dtCompany s where groupCompanySn = ? and c.companyID = s.companyID) and codePID = ? order by codeValue";
			list = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{groupCompanySn,"scode20110106hfjes5ucxp0000000017"});

		}else{
		Object[] params = { companyID, "scode20110106hfjes5ucxp0000000017"};
		String hql = "from CCode where companyID = ? and codePID = ? order by codeNumber";
		list = baseBeanService.getListBeanByHqlAndParams(hql,
				params);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("relationlist", list);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
	}catch(Exception e){
		e.printStackTrace();
	}
		return "success";
	}
		
		// 根据companyID 查询所有在职员工
		
		public String getAllStaffOfCompany(){
			Map<String, Object> session = ActionContext.getContext().getSession();
			String hql = "from  Staff s where exists(select staffID from COS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ?)";
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), hql, new Object[]{companyID,"50"});
			relation = "在职员工";
			if(pageForm!=null)
			{
				session.put("RecordCount", pageForm.getRecordCount());
			}else{
				session.put("RecordCount", 0);
			}
			return "listuser";
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

			Object[] params = new Object[] { companyID, "50", "%"+orgID+"%",
					"%"+orgID+"%" };
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), hql, params);
		    relation = "在职员工";
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
    		 e.printStackTrace();
    		
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
     		 e.printStackTrace();
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

      ///////////////////////////////////////////////手机端接口//////////////////////////////////////////////////////////////////////////////


	/**
			*
			* 增加短信模板
	 * @return
	 */
	public String addTelTemp(){
		HttpServletRequest  request = ServletActionContext.getRequest();
		String qmsID  = request.getParameter("qmsID");
		String titleName = request.getParameter("titleName");
		String content = request.getParameter("content");
		String staffID = request.getParameter("staffID");
		String surl = request.getParameter("surl");
		String cate = request.getParameter("cate");
		String companyID = request.getParameter("companyID");
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			telMessageService.addTelTemp(qmsID, titleName, content,staffID,surl,cate,companyID);
			map.put("result", "0");
		}catch(Exception e){
			map.put("result", "1");
			e.printStackTrace();
		}
		JSONObject jo = JSONObject.fromObject(map);
		this.results = jo;
		return "success";
	}




	/**
	 *
	 * 生成短链
	 * @return
	 */
	public String addUrl(){
		HttpServletRequest  request = ServletActionContext.getRequest();
		String curl = request.getParameter("curl"); //多条逗号隔开
		String staffID = request.getParameter("staffID");
		Map<String,Object> map = new HashMap<String, Object>();

		String surl = telMessageService.saveShortUrl(curl,staffID);
		map.put("result", surl);

		JSONObject jo = JSONObject.fromObject(map);
		this.results = jo;
		return "success";
	}

	/**
	 *
	 * 群发短信
	 * @return
	 */
	public String groupSendTel(){
		HttpServletRequest  request = ServletActionContext.getRequest();

		String content = request.getParameter("content");

		String tels = request.getParameter("tels"); //多条逗号隔开
		String staffID = request.getParameter("staffID");
		String sccid = request.getParameter("sccid");
		String companyID = request.getParameter("companyID");
		String ret = telMessageService.groupSendTel(tels,content,staffID,sccid,companyID);
		Map<String,Object> map = new HashMap<String, Object>();
		if(!"ok".equals(ret)){

			map.put("result", "1");
		}else{
			map.put("result", "0");
		}
		JSONObject jo = JSONObject.fromObject(map);
		this.results = jo;
		return "success";
	}

	/**
	 *
	 * 删除短信模板
	 * @return
	 */
	public String deleteTemp(){
		HttpServletRequest  request = ServletActionContext.getRequest();
		String qmsID  = request.getParameter("qmsID");

		Map<String,Object> map = new HashMap<String, Object>();
		try {
			telMessageService.deleteTelTemp(qmsID);
			map.put("result", "0");
		}catch(Exception e){
			map.put("result", "1");
			e.printStackTrace();
		}
		JSONObject jo = JSONObject.fromObject(map);
		this.results = jo;
		return "success";

	}

	/**
	 *
	 * 获取模板列表不分页
	 * @return
	 */
	public String getdxTemp(){

		HttpServletRequest  request = ServletActionContext.getRequest();
		String staffID  = request.getParameter("staffID");
		String companyID  = request.getParameter("companyID");
		String cate  = request.getParameter("cate");

		Map<String,Object> map = new HashMap<String, Object>();

		List<BaseBean> list = telMessageService.getdxTemp(staffID,companyID,parameter,cate);
		JSONObject jsonObjList = new JSONObject();
		List<Object> lists = new ArrayList<Object>();
			for (int i = 0; i < list.size(); i++) {
				TquickMessage tm = (TquickMessage)list.get(i);
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("qmsID", tm.getQmsID());//模板ID
				jsonObj.accumulate("titleName",tm.getTitleName()==null?"":tm.getTitleName());//模板标题
				jsonObj.accumulate("content", tm.getContent()==null?"":tm.getContent()); //模板内容
				jsonObj.accumulate("surl", tm.getSurl()==null?"":tm.getSurl()); //短链接
				jsonObj.accumulate("cate", tm.getCate()==null?"":tm.getCate()); //高德分类
				lists.add(jsonObj);
			}


		jsonObjList.accumulate("templist", lists);

		results = jsonObjList;

		return "success";

	}

	/**
	 *
	 * 短链变长链
	 * @return
	 */
     public String v(){
		 HttpServletResponse response = ServletActionContext.getResponse();
		 HttpServletRequest request = ServletActionContext.getRequest();

		 String surl = request.getRequestURL()+"?"+request.getQueryString();

		 String curl = telMessageService.accessSurl(surl);

		 try {

			 response.sendRedirect(StringUtil.convertUrl(curl));


		 } catch (Exception e) {
			 e.printStackTrace();
		 }
          return  null;

	 }


	/**
	 *
	 *
	 * 获取剩余短信条数
	 * @return
	 */
	public String  getSyMessage(){
		HttpServletRequest  request = ServletActionContext.getRequest();
		String staffID  = request.getParameter("staffID");
		String sccid  = request.getParameter("sccid");
		Map<String,Object> map = telMessageService.getSyMessage(staffID,sccid);
		JSONObject jo = JSONObject.fromObject(map);
		this.results = jo;
		return "success";
	 }

	/**
	 *
	 * 获取平台短信在第三方剩余金额
	 * @return
	 */
	public String  getPlatMes(){
		Map<String,Object> map = new HashMap<String, Object>();

		String overage = telMessageService.getPlatMes();
		if(Float.parseFloat(overage)<100){
			map.put("result","0");//余额不足
		}else{
			map.put("result","1");
		}


		JSONObject jo = JSONObject.fromObject(map);
		this.results = jo;
		return "success";

	 }


	/**
	 *  短信发送记录
	 *
	 * @return
	 */
	public String getDxRecordlist() {
		HttpServletRequest  request = ServletActionContext.getRequest();
		String staffID  = request.getParameter("staffID");
		String companyID  = request.getParameter("companyID");
		if(pageNumber == 0){
			pageNumber = 1;
		}

		pageForm = telMessageService.getDxRecordlist(pageNumber, 20, parameter,staffID,companyID);
		JSONObject jsonObjList = new JSONObject();
		List<Object> lists = new ArrayList<Object>();
		if (pageForm != null && pageForm.getList().size() > 0) {
			for (int i = 0; i < pageForm.getList().size(); i++) {
                TelMessage telMessage = (TelMessage)pageForm.getList().get(i);
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("tel",telMessage.getTelNum());
				jsonObj.accumulate("sendDate", Utilities.getDateString(telMessage.getSendDate(),"yyyy-MM-dd HH:mm:ss"));
				jsonObj.accumulate("content", telMessage.getContent()==null?"":telMessage.getContent());
				lists.add(jsonObj);
			}
		}

		jsonObjList.accumulate("dxrlist", lists);
		jsonObjList.accumulate("pagecount", pageForm!=null?pageForm.getPageCount():0);

		results = jsonObjList;
		return "success";
	}


	/**
	 *  获取搜索有公司
	 *
	 * @return
	 */
	public String getDxCompanylist() {
		if(pageNumber == 0){
			pageNumber = 1;
		}

		pageForm = telMessageService.getDxCompanylist(pageNumber, 20, parameter);
		JSONObject jsonObjList = new JSONObject();
		List<Object> lists = new ArrayList<Object>();
		if (pageForm != null && pageForm.getList().size() > 0) {
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object obj = pageForm.getList().get(i);
				Object[] objs = (Object[])obj;
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("ccompanyId",objs[0].toString());
				jsonObj.accumulate("companyName", objs[1].toString());
				jsonObj.accumulate("logoPath", objs[2]==null?"":objs[2].toString());
				lists.add(jsonObj);
			}
		}

		jsonObjList.accumulate("complist", lists);
		jsonObjList.accumulate("pagecount", pageForm!=null?pageForm.getPageCount():0);

		results = jsonObjList;
		return "success";
	}

	/**
	 * 获取模板分类
	 * @return
	 */
	public String getTempCate(){
		Map<String,Object> map = new HashMap<String, Object>();

		List<Object> list  = telMessageService.getCateList();

         map.put("gdcate",list);

		JSONObject jo = JSONObject.fromObject(map);
		this.results = jo;
		return "success";

	}


	/**
	 * 获取手机号分类及往来单位和往来个人
	 * @return
	 */
	public String getRelatelist(){
		Map<String,Object> map = new HashMap<String, Object>();
		List<Object> list  = telMessageService.getRelatelist(companyID);

		map.put("relatelist",list);

		JSONObject jo = JSONObject.fromObject(map);
		this.results = jo;
		return "success";

	}

	/***
	 *
	 * 根据分类查询手机号
	 * @return
	 */
	public String getTellistByCate(){

		PageForm pageForm  = telMessageService.getTellistByCate(relation,companyID,pageNumber,20,parameter);

		JSONObject jsonObjList = new JSONObject();
		List<Object> lists = new ArrayList<Object>();
		if(pageForm!=null) {
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("companyName", obj[0] == null ? "" : obj[0]);
				jsonObj.accumulate("companyTel", obj[1] == null ? "" : obj[1]);
				jsonObj.accumulate("staffName", obj[2] == null ? "" : obj[2]);
				jsonObj.accumulate("tel", obj[3] == null ? "" : obj[3]);
				lists.add(jsonObj);
			}
		}
		jsonObjList.accumulate("tellist", lists);
		jsonObjList.accumulate("pagecount", pageForm!=null?pageForm.getPageCount():0);

		this.results = jsonObjList;
		return "success";

	}

	/**
	 *
	 *  获取公司员工通讯录
	 * @return
	 */
	public String getTellist(){

		PageForm pageForm  = telMessageService.getStaffTelList(companyID,pageNumber,20,parameter);

		JSONObject jsonObjList = new JSONObject();
		List<Object> lists = new ArrayList<Object>();

		if(pageForm!=null) {
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("staffName", obj[0] == null ? "" : obj[0]);
				jsonObj.accumulate("tel", obj[1] == null ? "" : obj[1]);
				jsonObj.accumulate("postName", obj[2] == null ? "" : obj[2]);
				lists.add(jsonObj);
			}
		}

		jsonObjList.accumulate("tellist", lists);
		jsonObjList.accumulate("pagecount", pageForm!=null?pageForm.getPageCount():0);

		this.results = jsonObjList;
		return "success";
	}

	/**
	 *
	 * 验证非法字符
	 * @return
	 */
	public String checkkeyword(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			Map<String, Object> map = new HashMap<String, Object>();
			String content = request.getParameter("content");
			mobileMessage.setContent(content);
			String reStr = mobileMessage.checkkeyword();
			System.out.println(reStr);
			map.put("result", reStr);

			JSONObject jo = JSONObject.fromObject(map);
			this.results = jo;
		}catch (Exception e){
            e.printStackTrace();
		}
		return "success";
	}



	/**
	 *  获取搜索有公司
	 *
	 * @return
	 */
	public String getAllNmMarke() {
		if(pageNumber == 0){
			pageNumber = 1;
		}

		HttpServletRequest request = ServletActionContext.getRequest();
		String  staffID = request.getParameter("staffID");
		pageForm = phlIndexSerivce.getPageFormMarket(pageNumber, 20, Constant.NMSC_ID,staffID);
		JSONObject jsonObjList = new JSONObject();
		List<Object> lists = new ArrayList<Object>();
		if (pageForm != null && pageForm.getList().size() > 0) {
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object obj = pageForm.getList().get(i);
				Object[] objs = (Object[])obj;
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("ccompanyId",objs[0].toString());
				jsonObj.accumulate("companyName",objs[1]==null?"":objs[1].toString());
				jsonObj.accumulate("logoPath", objs[2]==null?"":objs[2].toString());
				jsonObj.accumulate("address", objs[3]==null?"":objs[3].toString());
				String d = "";
				if(objs[4]!=null&&objs[4]!=""){
					float distance = Float.parseFloat(objs[4].toString());

					if(distance<1){
						d  = (int)Math.floor(distance*1000)+"m";//小于1KM 转成米
					}else{
						d =   String.format("%.1f",distance)+"km"; //大于1KM 不转化
					}

				}

				jsonObj.accumulate("distance", d);
				lists.add(jsonObj);
			}
		}

		jsonObjList.accumulate("marketlist", lists);
		jsonObjList.accumulate("pagecount", pageForm!=null?pageForm.getPageCount():0);

		results = jsonObjList;
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
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
	public String getCompanyID() {
		return companyID;
	}


	public void setCompanyID(String companyID) {
		this.companyID = companyID;
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
		return relation;
	}


	public void setRelation(String relation) {
		this.relation = relation;
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

	public Object getResults() {
		return results;
	}

	public void setResults(Object results) {
		this.results = results;
	}
}
