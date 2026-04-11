package hy.tel.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Staff;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import hy.tel.bo.TelInRecord;
import hy.tel.bo.TelInRecordDeal;
import hy.tel.bo.TelOutRecord;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller; 
 

/**
 * 呼叫中心业务处理
 * 
 * @author yaloo
 */
@Controller
@Scope("prototype")
public class TelRecordAction implements SessionAware,ServletContextAware {
	@Autowired
	private BaseBeanService baseBeanService;
	@Autowired
	private CLogBookService logBookService;
	@Autowired
	private CCodeService ccodeService;
	@Autowired
	private UpLoadFileService fileService;
	@Autowired
	private ServerService serverService;
	@Autowired
	private ShowExcelService excelService;
	private InputStream excelStream;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private int pageSize;
	private String result;

	private TelInRecordDeal deal;
	private TelOutRecord telOutRecord;
	private TelInRecord telInRecord;
	private List<CCode> codeList;
	
	private String telcodetype;//区分手机1座机0

	// // 搜索用 ////
	private String queryDateType;
	private String beginDate;
	private String endDate;
	private String search;
	

	// // 用来上传音频文件 ////
	private File doc;
	// 公司id
	private String companyID;
	// 当前用户
	private String userId;
	// 上传文件名 eg:123455.jpg
	private String fileName;
	// 电话号码
	private String reference;
	// 呼入呼出
	private String inOut;
	private String time1;
	private String time2;
	// 个人1 公司 2
	private String inOutType;
	private String type;// 用于区分集团汇总
    
	
	private List<BaseBean> toPrints;
	
	private String staffID;   //个人客户呼叫信息中心
	 
	private String actionName;
	/**
	 * 导出功能
	 * 
	 * @return
	 */
	@SuppressWarnings( { "unchecked", "unused" })
	public String showTelInOutExcel() {
		List<BaseBean> list; 
		CAccount account = (CAccount) session.get("account");
		if(account == null)
			return "not_login";
		
		String organizationID = session.get("organizationID").toString();
		Map<String, String> map = new HashMap<String, String>();
		List<Object> listObj = (List<Object>) session.get("allList");
		String sql = (String) listObj.get(0);
		if (sql.contains(",tel.id,tel.company")) {
			sql = sql.replace(",tel.id,tel.company", "");
		}
		if (sql.contains(",telo.id,telo.company")) {
			sql = sql.replace(",telo.id,telo.company", "");
		}
		Object[] parms = (Object[]) listObj.get(1);
		list = baseBeanService.getListBeanBySqlAndParams(sql, parms);

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Object obj = (Object) list.get(i);
				Object[] objs = (Object[]) obj;
				if (objs[0].toString().equals("0")) {
					objs[0] = "呼入";
				}
				if (objs[0].toString().equals("1")) {
					objs[0] = "呼出";
				}
				objs[3] = objs[3].toString().substring(0, 19);
			}
		}

		excelStream = excelService
				.showExcel(TelInRecord.columnHeadings(), list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出个人呼入呼出记录", account);
		baseBeanService.update(logBook);
		return "showTelInOutExcel";
	} 
	
///////////////////////////////客户端功能开始////////////////////////////////////////////
	/**
	 * 保存或修改打入记录
	 * 
	 * @return
	 */
	public String saveTelInRec() {
		CAccount account = (CAccount) session.get("account");
		try {

			if (telInRecord != null
					&& (null == telInRecord.getId() || "".equals(telInRecord
							.getId()))) {
				if (telInRecord != null) {
					Staff user = (Staff) baseBeanService
							.getBeanByHqlAndParams(
									" from Staff s where s.staffID in( select t.staffID from CAccount t where t.accountID = ? ) ",
									new Object[] { this.userId });
					//保存打入记录
					telInRecord.setUser(user);
					//判断手机号是座机0还是手机1
					String regExp = "^[1]([3][0-9]{1}|59|58|88|89|51)[0-9]{8}$";
					Pattern p = Pattern.compile(regExp);
					Matcher m = p.matcher(telInRecord.getTelNumber());
					if(m.find()){
						telInRecord.setTelcodeType("1");
					}else{
						telInRecord.setTelcodeType("0");
					}
					telInRecord.setId(serverService.getServerID("TelInRecord"));
					baseBeanService.save(telInRecord);
					
					//保存问题处理
					TelInRecordDeal deal = new TelInRecordDeal();
					deal.setCompany(telInRecord.getCompany());
					
					if(account != null && ! StringUtils.isBlank(account.getAccountEmail())){
					    deal.setCompanyname(account.getCompanyName());
					}
					
					deal.setDealcontent(telInRecord.getDealContent());
					deal.setDealdate(telInRecord.getDealDate());
					deal.setDealuser(telInRecord.getDealUser());
					deal.setIsdeal(0);
					deal.setTelInRecord(telInRecord);
					deal.setId(serverService.getServerID("TelInRecordDeal")); 
					baseBeanService.save(deal);
					
					
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("result", telInRecord.getId());
					HttpServletResponse response = ServletActionContext
							.getResponse();
					JSONObject json = JSONObject.fromObject(map);

					response.setCharacterEncoding("UTF-8");
					response.getWriter().print(json.toString());
				}
			}

		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return null;
	}

	/**
	 * 保存或修改打出记录
	 * 
	 * @return
	 */
	public String saveTelOutRec() {
		CAccount account = (CAccount) session.get("account");
		try {
			if (telOutRecord != null
					&& (null == telOutRecord.getId() || "".equals(telOutRecord
							.getId()))) {
				Staff user = (Staff) baseBeanService
						.getBeanByHqlAndParams(
								" from Staff s where s.staffID in( select t.staffID from CAccount t where t.accountID = ? ) ",
								new Object[] { this.userId });

				telOutRecord.setUser(user);
				//判断手机号是座机0还是手机1
				String regExp = "^[1]([3][0-9]{1}|59|58|88|89|51)[0-9]{8}$";
				Pattern p = Pattern.compile(regExp);
				Matcher m = p.matcher(telOutRecord.getTelNumber());
				if(m.find()){
					telOutRecord.setTelcodeType("1");
				}else{
					telOutRecord.setTelcodeType("0");
				}
				telOutRecord.setId(serverService.getServerID("TelOutRecord"));
				baseBeanService.save(telOutRecord);
				
				//保存问题处理
				TelInRecordDeal deal = new TelInRecordDeal();
				deal.setCompany(telOutRecord.getCompany());
				
				if(account != null && ! StringUtils.isBlank(account.getAccountEmail())){
				    deal.setCompanyname(account.getCompanyName());
				}
				
				deal.setIsdeal(0);
				deal.setTelOutRecord(telOutRecord);
				deal.setId(serverService.getServerID("TelOutRecordDeal")); 
				baseBeanService.save(deal);
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("result", telOutRecord.getId());
				HttpServletResponse response = ServletActionContext
						.getResponse();
				JSONObject json = JSONObject.fromObject(map);

				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(json.toString());
			}
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return null;
	}

	/**
	 * 提交音频文件及记录
	 * 
	 * @return
	 */
	public String upLoadWaveFile() {
		try {
			String path = servletContext.getRealPath("\\");

			String urlPath = fileService.savePhoto(path, this.getFileName(),
					doc, this.getCompanyID(), "telSys/".concat(DateUtil
							.getCurrentDate(DateUtil.C_DATE_PATTON_DEFAULT)));

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", urlPath);

			HttpServletResponse response = ServletActionContext.getResponse();
			JSONObject json = JSONObject.fromObject(map);

			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(json.toString());

		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return null;
	}
/////////////////////////////////客户端功能结束//////////////////////////////////////////
	/**
	 * 接待个人记录
	 * 
	 * @return
	 */
	public String personRecords() {
		CAccount account = (CAccount) session.get("account");

		if(account == null)
			return "not_login";
		
		
		List<Object> list = new ArrayList<Object>();
		list.add("1");
		if (search == null || search.length() < 1) {
			String Obj = " TelInRecord as s inner join s.user as user ";
			String where = " and s.customType = ? order by s.recodeDate desc ";
			if (type != null && type.equals("group")) {
				where = "and s.customType = ? and s.company in(select c.companyID from Company c where c.companyID = ? or c.companyPID = ?) order by s.recodeDate desc";
				list.add(account.getCompanyID());
				list.add(account.getCompanyID());
			}
			getDataByCustmerType(Obj, where, list.toArray());
		} else {
			getRecords(list);
		}

		return "person";
	}

	/**
	 * 接待公司记录
	 * 
	 * @return
	 */
	public String companyRecords() {
		CAccount account = (CAccount) session.get("account");

		if(account == null)
			return "not_login";
		
		List<Object> list = new ArrayList<Object>();
		list.add("2");

		if (search == null || search.length() < 1) {
			String Obj = " TelInRecord as s inner join s.user as user ";
			String where = " and s.customType = ? order by s.recodeDate desc ";
			if (type != null && type.equals("group")) {
				where = "and s.customType = ? and s.company in(select c.companyID from Company c where c.companyID = ? or c.companyPID = ?) order by s.recodeDate desc";
				list.add(account.getCompanyID());
				list.add(account.getCompanyID());
			}
			getDataByCustmerType(Obj, where, list.toArray());
		} else {
			getRecords(list);
		}

		return "company";
	}

	/**
	 * 电话接待语音查询
	 * 
	 * @return
	 */
	public String soundInRecords() {
		CAccount account = (CAccount) session.get("account");

		if(account == null)
			return "not_login";
		
		String Obj = "TelInRecord as s inner join s.user as user";
		String where = " order by s.recodeDate desc ";
		Object[] obj = null;
		if (type != null && type.equals("group")) {
			where = "and s.company in(select c.companyID from Company c where c.companyID = ? or c.companyPID = ?) order by s.recodeDate desc";
			obj = new Object[] { account.getCompanyID(), account.getCompanyID() };
		}

		if (search == null || search.length() < 1) { 
			getDataByCustmerType(Obj, where, obj);
		} else {
			getSoundRecords(Obj, where);
		}
		return "soundsIn";
	}

	/**
	 * 电话打出声音记录
	 * 
	 * @return
	 */
	public String soundOutRecords() {
		CAccount account = (CAccount) session.get("account");

		if(account == null)
			return "not_login";
		
		String Obj = " TelOutRecord as s inner join s.user as user ";
		String where = " order by s.visitedTime desc ";
		Object[] obj = null;
		if (type != null && type.equals("group")) {
			where = "and s.company in(select c.companyID from Company c where c.companyID = ? or c.companyPID = ?) order by s.visitedTime desc";
			obj = new Object[] { account.getCompanyID(), account.getCompanyID() };
		}
		if (search == null || search.length() < 1) {
			getDataByCustmerType(Obj, where, obj);
		} else {
			getSoundRecords(Obj, where);
		}
		return "soundsOut";
	}

	/**
	 * 电话打出记录
	 * 
	 * @return
	 */
	public String returnsVisit() {
		CAccount account = (CAccount) session.get("account");

		if(account == null)
			return "not_login";
		
		if (search == null || search.length() < 1) {
			String Obj = " TelOutRecord as s ";
			String where = " order by s.visitedTime desc ";
			Object[] obj = null;
			if (type != null && type.equals("group")) {
				where = "and s.company in(select c.companyID from Company c where c.companyID = ? or c.companyPID = ?) order by s.visitedTime desc";
				obj = new Object[] { account.getCompanyID(), account.getCompanyID() };
			}
			getDataByCustmerType(Obj,where, obj);
		} else {
			StringBuilder buffer = new StringBuilder(); 
			Object returnVisits = session.get("returnVisits");
			Object begin = session.get("beginDate");
			Object end = session.get("endDate");

			List<String> list = new ArrayList<String>();
			if (null != returnVisits && returnVisits.toString().length() > 0) {
				buffer.append("  and telType = ? ");
				list.add(returnVisits.toString());
			}
			// 日期
			Date from = DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT,
					null == begin ? null : begin.toString());
			Date to = DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT,
					null == end ? null : end.toString());
			if (from != null && to != null) {
				if (from.compareTo(to) > 0) {
					return "";
				}
			}

			if (from != null) {
				buffer
						.append(" and visitedtime > to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
				list.add(begin.toString());
			}

			if (to != null) {
				buffer.append(" and visitedtime < to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
				list.add(end.toString());
			}
			if (type != null && type.equals("group")) {
				buffer.append(" and s.company in(select c.companyID from Company c where c.companyID = ? or c.companyPID = ?) order by s.visitedTime desc");

				list.add(account.getCompanyID());
				list.add(account.getCompanyID());

			} else {
				buffer.append(" order by s.visitedTime desc ");
			}

			getDataByCustmerType("TelOutRecord as s ", buffer.toString(), list.toArray());
		}

		return "returnsVisit";
	}
    
	public String infoDealCenter(){ 
		
//		String hql1 = "from TelInRecord";
//		String hql = "from TelInRecordDeal where telInRecord = ?";
//		List<BaseBean> list= baseBeanService.getListBeanByHqlAndParams(hql1, null);
//		int n = 0;
//		for(BaseBean b:list){
//			TelInRecord telRe= (TelInRecord) b;
//		  List<BaseBean> list2 = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{telRe});
//		  if(list2.size()==0){
//			  logger.info("调试信息");
//			  TelInRecordDeal newDeal = new TelInRecordDeal();
//				newDeal.setId(serverService.getServerID("TelInRecordDeal")); 
//				newDeal.setCompany(telRe.getCompany());
//				newDeal.setIsdeal(0);
//				newDeal.setTelInRecord(telRe);  
//				baseBeanService.save(newDeal); 
//			  n++;
//		  }
//		}
//		logger.info("值：{}", n);
		
		
		
		CAccount account = (CAccount) session.get("account");
		if(account == null)
			return "not_login";
		
		String where = "";
		List<String> list = new ArrayList<String>(); 
		
		if (search != null && search.equals("search")) {
			telInRecord = (TelInRecord) session.get("telInRecord"); 
			where = this.buildSearchInfoDeal(list);
		}
		
		where = StringUtils.isBlank(where) ? "" : where;
		if(telInRecord!=null){
			where = where+" and telcodetype = ?";
			list.add(telcodetype);
		}
		if (type != null && type.equals("group")) { 
			where += "and s.company in(select c.companyID from Company c where c.groupCompanySn = ?)";
			list.add(account.getCompany().getGroupCompanySn()); 
		}
		getDataByCustmerType(" ViewTelInfodeal as s",where,list.toArray());
		
		return "infoDealCenter";
	}	

    public String searchInfoDeal(){
        session.put("telInRecord", telInRecord);

		return this.infoDealCenter(); 
    }
	
	/**
	 * 处理电话提出问题记录
	 * 
	 * @return
	 */
	public String dealTel() { 
		CAccount account = (CAccount) session.get("account");
		if(account == null)
			return "not_login";
		
		if (deal == null) {
			return "infoDealCenter";
		} 
		
		deal.setDealdate(DateUtil.parseDate(DateUtil.C_TIME_PATTON_DEFAULT,DateUtil.getCurrentDate())); 
		
		//问题转发
		if(!StringUtils.isBlank(deal.getDealuser())){

			TelInRecordDeal newDeal = new TelInRecordDeal();
			newDeal.setId(serverService.getServerID("TelInRecordDeal")); 
			TelInRecord telIn = (TelInRecord)baseBeanService.getBeanByKey(TelInRecord.class, deal.getTelInRecord().getId());
			newDeal.setCompany(deal.getCompany());
//			newDeal.setCompanyname(deal.getCompanyname()); 
			newDeal.setIsdeal(0);
			newDeal.setTelInRecord(telIn);  
			baseBeanService.save(newDeal); 
		} 
		
		deal.setCompany(account.getCompanyID());
		deal.setCompanyname(account.getCompanyName());
		deal.setDealuser(account.getStaffName() == null ?  account.getAccountEmail() : account.getStaffName());
		deal.setIsdeal(1); 
		//如果处理的问题是别人转发过来的　update 否则create(save)
//		if(!StringUtils.isBlank(deal.getId())){ 
			baseBeanService.update(deal);
//		} 
//		else{ 
//			deal.setId(serverService.getServerID("TelInRecordDeal"));
//			baseBeanService.save(deal);
//		}
		
		return "success";
	}

	/**
	 * ********************************** * 搜索功能 *
	 * *********************************
	 */
	// 电话回访记录
	public String searchReturnsVisit() { 

		CAccount account = (CAccount) session.get("account");
		if(account == null)
			return "not_login";
		
		session.put("beginDate", beginDate);
		session.put("endDate", endDate);
		session.put("returnVisits", telOutRecord.getTelType());
		return returnsVisit();
	}

	/**
	 * 接待个人记录
	 * 
	 * @return
	 */
	public String searchPerson() { 
		CAccount account = (CAccount) session.get("account");
		if(account == null)
			return "not_login";
		
		session.put("beginDate", beginDate);
		session.put("endDate", endDate);
		session.put("telInRecord", telInRecord);
		session.put("queryDateType", queryDateType);
		return personRecords();
	} 
    
    private String buildSearchInfoDeal(List<String> list){
        
		// 日期
		Date from = DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT,
				null == beginDate ? null : beginDate.toString());
		Date to = DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT,
				null == endDate ? null : endDate.toString());
		StringBuilder buf = new StringBuilder();
		
		//客户电话
		if(!StringUtils.isBlank(telInRecord.getTelNumber())){
		    buf.append(" and telNumber = ? ");
		    list.add(telInRecord.getTelNumber());
		}		

		//接待类型
		if(!StringUtils.isBlank(telInRecord.getRecordType())){
		    buf.append(" and recordType = ? ");
		    list.add(telInRecord.getRecordType());
		}	

		//责任人
		if(!StringUtils.isBlank(telInRecord.getUser().getStaffKey())){
			buf.append(" and userid = ? ");
			list.add(telInRecord.getUser().getStaffKey());
		}
		
		//责任人电话
		if(!StringUtils.isBlank(telInRecord.getUser().getReference())){
			buf.append(" and reference = ? ");
			list.add(telInRecord.getUser().getReference());
		}

		//是否处理
		if(telInRecord.getIsDeal() == 0){
		    buf.append(" and isdeal = 0 "); 
		}else if(telInRecord.getIsDeal() == 1){
			buf.append(" and isdeal = 1 ");
		}
		
		//客户名
		if(!StringUtils.isBlank(telInRecord.getCustomName()) 
				&& !StringUtils.isBlank(telInRecord.getCustomName().replace(", ", ""))){
		    buf.append(" and customName like ? ");
		    list.add(telInRecord.getCustomName());
		}
		
       //处理时间
		if (from != null) {
			buf.append(" and s.dealdate > to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
			list.add(beginDate.toString().trim());
		}

		if (to != null) {
			buf.append(" and s.dealdate < to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
			list.add(endDate.toString().trim());
		}	
		
		return buf.toString();
    }
	/**
	 * 接待公司记录
	 * 
	 * @return
	 */
	public String searchCompany() { 

		CAccount account = (CAccount) session.get("account");
		if(account == null)
			return "not_login";
		
		session.put("beginDate", beginDate);
		session.put("endDate", endDate);
		session.put("telInRecord", telInRecord);
		session.put("queryDateType", queryDateType);
		return companyRecords();
	}

	/**
	 * 打印信息处理报表
	 * @return
	 */
	public String toPrint(){ 

		CAccount account = (CAccount) session.get("account");
		if(account == null)
			return "not_login";
		
		String tabsql = "from ViewTelInfodeal as s where 1=1 ";
	    List<String> list = new ArrayList<String>(); 
	    String where  = buildSearchInfoDeal(list); 
	    
        if (type != null && type.equals("group")) { 
        	where = where + "and s.company in(select c.companyID from Company c where c.companyID = ? or c.companyPID = ?)";
			
			list.add(account.getCompanyID());
			list.add(account.getCompanyID());
		}else {
			where = where + " and s.company = ? ";
			list.add(account.getCompanyID());
		} 
        where = where+" and telcodetype = ?";

		list.add(telcodetype);
		toPrints = baseBeanService.getListBeanByHqlAndParams(tabsql + where, list.toArray()); 
		return "toPrint";
	}
	/**
	 * 搜索电话打入声音记录
	 * 
	 * @return
	 */
	public String searchSoundInRecords() { 

		CAccount account = (CAccount) session.get("account");
		if(account == null)
			return "not_login";
		
		session.put("beginDate", beginDate);
		session.put("endDate", endDate);
		session.put("telNumber", this.telInRecord.getTelNumber());
		String userName = this.telInRecord.getUser().getStaffName();
		session.put("userName", userName);
		return soundInRecords();
	}

	/**
	 * 搜索电话打出声音记录
	 * 
	 * @return
	 */
	public String searchSoundOutRecords() { 

		CAccount account = (CAccount) session.get("account");
		if(account == null)
			return "not_login";
		
		session.put("beginDate", beginDate);
		session.put("endDate", endDate);
		session.put("telNumber", this.telOutRecord.getTelNumber());
		String userName = this.telOutRecord.getUser().getStaffName();
		session.put("userName", userName);
		return soundOutRecords();
	}

	/**
	 * ********************************** * 删除功能 * *********************************
	 */
	/* 删除回访记录 */
	public String delReturnVisit() { 

		CAccount account = (CAccount) session.get("account");
		if(account == null)
			return "not_login";

		CLogBook logbook = logBookService.saveCLogBook(null, "删除回访记录(管理员号:"
				+ account.getAccountID() + ")", account);
		List<BaseBean> beans = new ArrayList<BaseBean>();
		beans.add(logbook);

		String[] hql = { "delete from TelOutRecord t where t.id=? and t.company = ? " };
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql,
				new Object[] { telOutRecord.getId(), account.getCompanyID() });

		return "success";
	}

	/**
	 * 删除接待记录
	 * 
	 */
	public String delVisit() { 

		CAccount account = (CAccount) session.get("account");
		if(account == null)
			return "not_login";

		CLogBook logbook = logBookService.saveCLogBook(null, "删除接待记录(管理员号:"
				+ account.getAccountID() + ")", account);
		List<BaseBean> beans = new ArrayList<BaseBean>();
		beans.add(logbook);

		String[] hql = { "delete from TelInRecord t where t.id=? and t.company = ? " };
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql,
				new Object[] { telInRecord.getId(), account.getCompanyID() });
		return "success";
	}

	/**
	 * 构建分页
	 * 
	 * @param Obj
	 *            查询所属对象
	 * @param where
	 *            更多条件
	 * @param obj
	 *            where参数列表
	 */
	private void getDataByCustmerType(String Obj, String where, Object[] obj) {
		CAccount account = (CAccount) session.get("account");

		getCCodeList(account.getCompanyID());
 
		String hql = " from @@ where s.company = ? ### "
				.replace("@@", Obj);
		
		List<Object> list = new ArrayList<Object>();
		if (type == null || type.equals("null") || type.equals("")) {
			list.add(account.getCompanyID());
		}
		if(type != null && type.equals("customer")){ //个人客户呼叫信息中心
			list.add(account.getCompanyID());
			hql = " from @@ where s.company = ? and s.telInRecord.customId = ? ### "
				.replace("@@", Obj);
			list.add(staffID);
		}
		
		if (where != null && where.length() > 0) {
			hql = hql.replace("###", where);

			if (obj != null) {
				for (Object item : obj) {
					list.add(item);
				}
				obj = null;
			}
		} else {
			hql = hql.replace("###", "");
		}
		
		if (type != null && type.equals("group")) {
			hql = hql.replace("s.company = ? and", "");
			hql = hql.replace("s.company = ?   and","");
			hql = hql.replace("s.company = ?  and","");			                   
		}
		pageForm = baseBeanService.getPageForm((pageNumber == 0 ? 1 : pageNumber), pageSize == 0 ? 5 : pageSize, hql,
				list.toArray());
		if (pageForm != null) {
			session.put("RecordCount", pageForm.getRecordCount());
		}else{
			pageForm = new PageForm();
		}
	}

	/**
	 * 电话声音文件查询
	 * 
	 * @param table
	 * @param orderBy
	 */
	private void getSoundRecords(String table, String orderBy) {
		List<String> list = new ArrayList<String>();

		StringBuilder buffer = new StringBuilder(); 
		CAccount account = (CAccount) session.get("account");
		Object begin = session.get("beginDate");
		Object end = session.get("endDate");
		Object telNumber = session.get("telNumber");
		Object userName = session.get("userName");

		// 日期
		Date from = DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT,
				null == begin ? null : begin.toString());
		Date to = DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT,
				null == end ? null : end.toString());
		if (from != null && to != null) {
			if (from.compareTo(to) > 0) {
				return;
			}
		}

		// 用户名
		if (null != userName && !"".equals(userName)) {
			buffer.append(" and user.staffName like ?");
			list.add("%" + userName.toString().trim() + "%");
		}

		// 电话号
		if (null != telNumber && !"".equals(telNumber)) {
			buffer.append(" and telNumber = ?");
			list.add(telNumber.toString().trim());
		}

		if (from != null) {
			buffer
					.append(" and beginTime > to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
			list.add(begin.toString().trim());
		}

		if (to != null) {
			buffer
					.append(" and beginTime < to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
			list.add(end.toString().trim());
		}

		buffer.append(orderBy);
		if (type != null && type.equals("group")) {
			list.add(account.getCompanyID());
			list.add(account.getCompanyID());

		}

		getDataByCustmerType(table, buffer.toString(), list.toArray());
	}

	/**
	 * 构建接待记录查询
	 * 
	 * @param list
	 */
	private void getRecords(List<Object> list) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("and customType = ? "); 
		CAccount account = (CAccount) session.get("account");
		TelInRecord telInRecord = (TelInRecord) session.get("telInRecord");
		Object begin = session.get("beginDate");
		Object end = session.get("endDate");
		Object queryDateType = session.get("queryDateType");

		// 日期
		Date from = DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT,
				null == begin ? null : begin.toString());
		Date to = DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT,
				null == end ? null : end.toString());
		if (from != null && to != null) {
			if (from.compareTo(to) > 0) {
				return;
			}
		}

		// 接待类型
		if (null != telInRecord && telInRecord.getRecordType() != null
				&& telInRecord.getRecordType().length() > 0) {
			buffer.append(" and recordType = ?");
			list.add(telInRecord.getRecordType());

		}

		// 是否处理
		/*if (null != telInRecord) {
			buffer.append(" and isDeal = ?");
			list.add(telInRecord.getIsDeal());

		}*/

		// 客户名称
		if (null != telInRecord && telInRecord.getCustomName() != null
				&& telInRecord.getCustomName().length() > 0) {
			buffer.append(" and customName = ?");
			list.add(telInRecord.getCustomName());
		}

		// 客户电话
		if (null != telInRecord && telInRecord.getTelNumber() != null
				&& telInRecord.getTelNumber().length() > 0) {
			buffer.append(" and customTel = ?");
			list.add(telInRecord.getTelNumber());
		}

		if (from != null) {
			// 时间处理
			if (null != queryDateType && "1".equals(queryDateType)) {
				buffer.append("  and recodeDate "); // 接待时间
			/*}
			
			else if (null != queryDateType && "2".equals(queryDateType)) {
				buffer.append("  and dealDate "); // 处理时间
			}*/

			buffer.append(" > to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
			list.add(begin.toString());
			}
		}

		if (to != null) {
			// 时间处理
			if (null != queryDateType && "1".equals(queryDateType)) {
				buffer.append("  and recodeDate "); // 接待时间
			/*} else if (null != queryDateType && "2".equals(queryDateType)) {
				buffer.append("  and dealDate "); // 处理时间
			}*/

			buffer.append(" < to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
			list.add(end.toString());
			}
		}
		if (type != null && type.equals("group")) {
			buffer
					.append(" and s.company in(select c.companyID from Company c where c.companyID = ? or c.companyPID = ?) order by s.recodeDate desc");

			list.add(account.getCompanyID());
			list.add(account.getCompanyID());

		} else {
			buffer.append(" order by s.recodeDate desc ");
		}

		getDataByCustmerType("TelInRecord as s inner join s.user as user ",
				buffer.toString(), list.toArray());
	}

	/**
	 * 获取电话通话类型
	 * 
	 * @param company
	 * @param pcode
	 */
	private void getCCodeList(String companyID) {
		codeList = ccodeService.getCCodeListByPID(companyID,
				"scode20120511cyyypnpchw0000000002");
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public int getPageNumber() {
		return pageNumber;
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getQueryDateType() {
		return queryDateType;
	}

	public void setQueryDateType(String queryDateType) {
		this.queryDateType = queryDateType;
	}

	public List<CCode> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<CCode> codeList) {
		this.codeList = codeList;
	}

	public TelOutRecord getTelOutRecord() {
		return telOutRecord;
	}

	public void setTelOutRecord(TelOutRecord telOutRecord) {
		this.telOutRecord = telOutRecord;
	}

	public TelInRecord getTelInRecord() {
		return telInRecord;
	}

	public void setTelInRecord(TelInRecord telInRecord) {
		this.telInRecord = telInRecord;
	}

	public File getDoc() {
		return doc;
	}

	public void setDoc(File doc) {
		this.doc = doc;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getInOut() {
		return inOut;
	}

	public void setInOut(String inOut) {
		this.inOut = inOut;
	}

	public String getTime1() {
		return time1;
	}

	public void setTime1(String time1) {
		this.time1 = time1;
	}

	public String getTime2() {
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}

	public ShowExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getInOutType() {
		return inOutType;
	}

	public void setInOutType(String inOutType) {
		this.inOutType = inOutType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public TelInRecordDeal getDeal() {
		return deal;
	}

	public void setDeal(TelInRecordDeal deal) {
		this.deal = deal;
	}
	
	public List<BaseBean> getToPrints() {
		return toPrints;
	} 
	
	protected Map<String, Object> session;
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	} 

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getTelcodetype() {
		return telcodetype;
	}

	public void setTelcodetype(String telcodetype) {
		this.telcodetype = telcodetype;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	private ServletContext servletContext;
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
}
