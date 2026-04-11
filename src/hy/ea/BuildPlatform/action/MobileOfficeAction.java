package hy.ea.BuildPlatform.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.IdClass;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.DrivingSchool.TbJpStudentInfo;
import hy.ea.bo.human.Staff;
import hy.ea.util.Constant;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONArray;
import org.apache.commons.collections.map.HashedMap;
import org.apache.struts2.ServletActionContext;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tiantai.wfj.util.SessionWrap;

import hy.ea.BuildPlatform.service.MobileOfficeService;
import hy.ea.bo.CAccount;
import hy.ea.bo.company.ContactCompany;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

@Controller("MobileOfficeAction")
@Scope("prototype")
public class MobileOfficeAction {
	private static final Logger logger = LoggerFactory.getLogger(MobileOfficeAction.class);
	@Resource
	private MobileOfficeService moservice;

	@Resource
	private BaseBeanService baseBeanService;

	private Logger logger = LoggerFactory.getLogger(MobileOfficeAction.class);
	private String companyIdentifier;
	private CAccount caccount;
	private HttpServletRequest request;
	private String ppId;
	private String flag;
	private String companyId;
	private String ccompanyId;
	private String companyName;
	private String sys;
	private String logoPath;
	private String idCard;
	private String studentId;
	private String subject;
	private String photo1;
	private String photo2;
	private String photo3;
	private String teacherId;
	private String staffId;
	private Object result;
	public String toMobileLogin(){
		if(companyId==null||companyId.equals("")){
			request=ServletActionContext.getRequest();
			HttpSession session=request.getSession();
			SessionWrap sw=SessionWrap.getInstance();
			CAccount account=(CAccount) sw.getObject(session, SessionWrap.KEY_CACCOUNT);
			companyId=account!=null?account.getCompanyID():"";
		}
		List<Object> list=moservice.toMobileLogin(companyId);
		if(!list.isEmpty()){
			Object [] obj=(Object[]) list.get(0);
			companyId=(String) obj[0];
			ccompanyId=(String) obj[1];
			companyName=(String) obj[2];
			logoPath=(String)obj[3];
			companyIdentifier=(String)obj[4];
		}
		if ("yd".equals(flag)) {
			return "mobileLogin";
		}else {
			return "ymobileLogin";
		}
	}
	public String mobileLogin(){
		if(caccount!=null&&caccount.getAccountEmail()!=null&caccount.getAccountPassword()!=null){
			if(!("").equals(companyIdentifier)){
				Integer i=moservice.login(companyIdentifier, caccount.getAccountEmail(), caccount.getAccountPassword());
				JSONObject json=new JSONObject();
				json.accumulate("i", i);
				result=json.toString();
			}
		}
		return "success";
	}
	//任务消息管理
	public String resourceSystem(){
		request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		SessionWrap sw=SessionWrap.getInstance();
		CAccount account=(CAccount) sw.getObject(session, SessionWrap.KEY_CACCOUNT);
		if(null==account){
			return "login";
		}
		List<Object> list=moservice.personalInfo(account.getStaffID());
		request.setAttribute("personalInfo", list);
		return "resourceSystem";
	}
	//教练
	public String CoachingManagement(){
		request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		SessionWrap sw=SessionWrap.getInstance();
		CAccount account=(CAccount) sw.getObject(session, SessionWrap.KEY_CACCOUNT);
		if(null==account){
			return "login";
		}
		List<Object> list=moservice.personalInfo(account.getStaffID());
		request.setAttribute("personalInfo", list);
		return "CoachingManagement";
	}
	//学员
	public String StudentManagement(){
		request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		SessionWrap sw=SessionWrap.getInstance();
		CAccount account=(CAccount) sw.getObject(session, SessionWrap.KEY_CACCOUNT);
		if(null==account){
			if(staffId==null){
				return "login";
			}else {
				account = new CAccount();
				account.setStaffID(staffId);
			}
		}
		List<Object> list=moservice.StudentInfo(account.getStaffID());
		request.setAttribute("StudentInfo", list);
		if("1".equals(flag)) {
			return "StudentManagement";
		}else {
			return "studentInformation";
		}
	}
	//应用操作
	/*public String appOperation(){
		request=ServletActionContext.getRequest();
		String companyId=request.getParameter("companyId");
		String staffId=request.getParameter("staffId");
		if(companyId!=null&&staffId!=null){
			HttpSession session=request.getSession();
			String hql="from CAccount ca where ca.staffID=? and ca.companyID=?";
			List<BaseBean> objectList=(List<BaseBean>)baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{staffId,companyId});
			//CAccount t=(CAccount)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{staffId,companyId});
			CAccount t=(CAccount)objectList.get(0);
			SessionWrap sw = SessionWrap.getInstance();
			sw.setObject(session,SessionWrap.KEY_CACCOUNT,t);
		}
		return "appOperation";
	}*/	//快捷应用
	public String fastApplication(){
		request=ServletActionContext.getRequest();
		String companyId=request.getParameter("companyId");
		String sccId=request.getParameter("sccId");
		HttpSession session=request.getSession();
		SessionWrap sw=SessionWrap.getInstance();
		CAccount account=null;
		if(companyId!=null&&sccId!=null){
			StringBuilder sbhql=new StringBuilder();
			sbhql.append("from CAccount acc where acc.staffID in ");
			sbhql.append("(select ss.staffID from Staff ss,TEshopCusCom cus where cus.sccId =? ) ");
			sbhql.append("and acc.companyID =?");
			List<BaseBean> objectList=(List<BaseBean>)baseBeanService.getListBeanByHqlAndParams(sbhql.toString(),new Object[]{sccId,companyId});
			account=(CAccount)objectList.get(0);
			sw.setObject(session,SessionWrap.KEY_CACCOUNT,account);
		}else{
			account=(CAccount) sw.getObject(session, SessionWrap.KEY_CACCOUNT);
			request.setAttribute("loginType","login");
		}
		if(null==account){
			return "login";
		}
		ContactCompany concom = moservice.authentication(account.getCompanyID());
		request.setAttribute("concom", concom);
		return "fastApplication";
	}
	//快捷应用ajax
	public String fastApplicationAjax(){
		Map<String,Object> map=new HashMap<String,Object>();
		if("toAdd".equals(flag)){
			map=moservice.toAddApplication(ppId);
		}else{
			map =moservice.fastApplication("company201009046vxdyzy4wg0000000025");
		}
		JSONObject json =JSONObject.fromObject(map);
		result=json.toString();
		return "success";
	}
	//增加与删除快捷应用
	public String addOrDelApplication(){
		Boolean	b=true;
		if(null!=ppId&&!"".equals(ppId)){
			try {
				if("add".equals(flag)){
					moservice.addApplication(ppId);
				}else if("del".equals(flag)){
					moservice.delApplication(ppId);
				}
			} catch (Exception e) {
				b=false;
				logger.error("add".equals(flag)?"增加应用失败！":"删除应用失败！");
			}
		}
		JSONObject json=new JSONObject();
		json.accumulate("b", b);
		result=json.toString();
		return "success";
	}

	public String buyPeriod(){
		String sql = "select pp.ppid,pp.goodsid,pp.companyid,cc.ccompany_id from tb_jp_student_info ts"+
						" inner join dt_productpackaging pp on ts.traintype=pp.categoryname " +
						" inner join dt_ccom_com cc on pp.companyid=cc.compnay_id " +
						" where pp.type = ? and ts.cardnum = ? ";
		Object detailsParameter = baseBeanService.getObjectBySqlAndParams(sql,new Object[]{"学时",idCard});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("detailsParameter", detailsParameter);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	public String learningProcess(){
		return "learningProcess";
	}

	public String findStudent(){
		String sql = "select r.studentname,max(r.subject),s.photo,s.cardnum,s.phone,s.student_id " +
				"from tb_elyc_order_record r,tb_jp_teacher tc,tb_jp_student_info s " +
				"where s.student_id = r.studentid and tc.teacherid = r.teacherid " +
				"and tc.staffid = ? group by r.studentname,s.photo,s.cardnum,s.phone,s.student_id having  max(r.subject) =? ";
		List<BaseBean> listStudent =  baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{staffId,subject});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("listStudent", listStudent);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	public String queryPeriod() throws IOException {
		String url = Constant.TIMING_DOMAIN+"/jptrainperiod/queryTimeNew";
		NameValuePair[] data = {
				new NameValuePair("personNumber", idCard),// 513901198701257015
		};
		String resMes = callInterface(url,data);
		if(resMes.equals("[]") || "".equals(resMes)){
			return "queryPeriod";
		}
		String str[] = resMes.split(",");
		Map<String,Object> map=new HashMap<String,Object>();
		//学员ID
		String studentId = str[0].substring(str[0].indexOf(":")+2,str[0].toString().length()-1);
		logger.info("调试信息");
		ActionContext.getContext().put("studentId", studentId);
		//科一理论么么
		int LLLearnTime1 = Integer.parseInt(str[11].substring(str[11].indexOf(":")+1, str[11].toString().length()));
		logger.info("调试信息");
		ActionContext.getContext().put("LLLearnTime1",LLLearnTime1);
		//科三理论
		int LLLearnTime3 = Integer.parseInt(str[13].substring(str[13].indexOf(":")+1, str[13].toString().length()));
		logger.info("调试信息");
		ActionContext.getContext().put("LLLearnTime3",LLLearnTime3);
		//学员姓名
		String name = str[1].substring(str[1].indexOf(":")+2,str[1].toString().length()-1);
		logger.info("调试信息");
		ActionContext.getContext().put("name",name);
		//科二实操
		int SelearnTime2 = Integer.parseInt(str[15].substring(str[15].indexOf(":")+1, str[15].toString().length()));
		logger.info("调试信息");
		ActionContext.getContext().put("SelearnTime2",SelearnTime2);
		//科三实操
		int SelearnTime3 = Integer.parseInt(str[16].substring(str[16].indexOf(":")+1, str[16].toString().length()));
		logger.info("调试信息");
		ActionContext.getContext().put("SelearnTime3",SelearnTime3);
		//科一理论剩余
		int LLSurplusTime1 = Integer.parseInt(str[17].substring(str[17].indexOf(":")+1,str[17].toString().length()));
		logger.info("调试信息");
		ActionContext.getContext().put("LLSurplusTime1",LLSurplusTime1);
		//科三理论剩余
		int LLSurplusTime3 = Integer.parseInt(str[19].substring(str[19].indexOf(":")+1, str[19].toString().length()));
		logger.info("调试信息");
		ActionContext.getContext().put("LLSurplusTime3",LLSurplusTime3);
		//科二实操剩余
		int SCSurplusTime2 = Integer.parseInt(str[21].substring(str[21].indexOf(":")+1, str[21].toString().length()));
		logger.info("调试信息");
		ActionContext.getContext().put("SCSurplusTime2",SCSurplusTime2);
		//科三实操剩余
		int SCSurplusTime3 = Integer.parseInt(str[22].substring(str[22].indexOf(":")+1, str[22].toString().length()));
		logger.info("调试信息");
		ActionContext.getContext().put("SCSurplusTime3",SCSurplusTime3);
		//科三里程
		int K3XSLCS = Integer.parseInt(str[30].substring(str[30].indexOf(":")+1, str[30].toString().length()-2));
		logger.info("调试信息");
		ActionContext.getContext().put("K3XSLCS",K3XSLCS/1000);



		return "queryPeriod";
	}
	//学时详情
	public String periodDetails(){

		return "periodDetails";
	}
	public String getAjaxPeriodDetails() throws IOException {
		String url = Constant.TIMING_DOMAIN+"/jptrainperiod/queryStudent";
		NameValuePair[] data = {
				new NameValuePair("studentId",studentId),
				new NameValuePair("subject",subject)
		};
		String resMes = callInterface(url,data);
		JSONArray json_test = JSONArray.fromObject(resMes);
		result=json_test.toString();
		return "success";
	}
	public String getAjaxPhoto() throws IOException {
		String url = Constant.TIMING_DOMAIN+"/jptrainperiod/queryPhoto";
		NameValuePair[] data = {
				new NameValuePair("photo1",photo1),
				new NameValuePair("photo2",photo2),
				new NameValuePair("photo3",photo3)
		};
		String resMes = callInterface(url,data);
		JSONArray json_test = JSONArray.fromObject(resMes);
		result=json_test.toString();
		return "success";
	}


	public String callInterface(String url,NameValuePair [] data) throws IOException {
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestBody(data);
		httpClient.executeMethod(postMethod);
		// 读取内容
		InputStream resInputStream = null;
		resInputStream = postMethod.getResponseBodyAsStream();
		InputStreamReader inStream = new InputStreamReader(resInputStream, "UTF8");
		// 处理内容
		BufferedReader reader = new BufferedReader(inStream);
		String tempBf = null;
		StringBuffer html = new StringBuffer();
		while ((tempBf = reader.readLine()) != null) {

			html.append(tempBf);
		}
		String resMes = html.toString();
		return resMes;
	}



	//练车照片
	public String drivingPic(){

		return "drivingPic";
	}


	/**
	 * 判断学员信息是否完善
	 * @return
	 */
	public String studentInfor(){
		request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		SessionWrap sw=SessionWrap.getInstance();
		CAccount account=(CAccount) sw.getObject(session, SessionWrap.KEY_CACCOUNT);
		Map<String,Object> map1 = moservice.StudentCard(account.getStaffID());
		TbJpStudentInfo tbJpStudentInfo =(TbJpStudentInfo)map1.get("student");
		List<Object> list = new ArrayList<>();
		list.add(tbJpStudentInfo.getCardNum());
		JSONObject json=new JSONObject();
		json.accumulate("list",list);
		result=json.toString();
		return "success";

	}
	/**
	 * 驾校app判断学员信息是否完善
	 * @return
	 */
	public String studentInforAPP(){
		Map<String,Object> map1=moservice.StudentCard(staffId);
		Map<String,Object> map=new HashMap<>();
		TbJpStudentInfo tbJpStudentInfo = (TbJpStudentInfo)map1.get("student");
        Staff staff = (Staff)map1.get("staff");
		List<Object[]> list = (List<Object[]>) map1.get("list");
		if(staffId!=null&&!"".equals(staffId)){
			if(tbJpStudentInfo!=null){
				map.put("cardNum",tbJpStudentInfo.getCardNum());
				map.put("companyID",tbJpStudentInfo.getCompanyId());
				map.put("type","1");//报名且已经完善信息
			}else if(list.size()>0){
				Object [] OB = list.get(0);
				map.put("cardNum",OB[0]);
				map.put("companyID",OB[1]);
				map.put("type","2");//报名未完善信息
			}else {
				map.put("cardNum","");
				map.put("companyID","");
				map.put("type","3");//未报名
			}
			map.put("name",staff.getStaffName());
		}else {
			map.put("cardNum","");
			map.put("companyID","");
			map.put("type","3");//未报名
			map.put("name","");
		}
		result=JSONObject.fromObject(map);
		return "success";

	}

	public String stCompanyByStudent(){
		//HttpServletRequest req = ServletActionContext.getRequest();
		//List<Object> params = new ArrayList<>();
		//String companyId = req.getParameter("companyId");
		String sql ="select r.studentname,max(r.subject),s.photo,s.cardnum,s.phone,s.student_id,s.company_id " +
					"from tb_elyc_order_record r,tb_jp_student_info s " +
					"where s.student_id = r.studentid and s.company_id = ? " +
					"group by r.studentname,s.photo,s.cardnum,s.phone,s.student_id,s.company_id having  max(r.subject) = ?";
		List<Object> list = baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{companyId,subject});
		JSONObject json=new JSONObject();
		json.accumulate("listStudent",list);
		result = json.toString();
		return "success";
	}

	public String getCompanyIdentifier() {
		return companyIdentifier;
	}

	public void setCompanyIdentifier(String companyIdentifier) {
		this.companyIdentifier = companyIdentifier;
	}

	public CAccount getCaccount() {
		return caccount;
	}

	public void setCaccount(CAccount caccount) {
		this.caccount = caccount;
	}
	public String getPpId() {
		return ppId;
	}
	public void setPpId(String ppId) {
		this.ppId = ppId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCcompanyId() {
		return ccompanyId;
	}
	public void setCcompanyId(String ccompanyId) {
		this.ccompanyId = ccompanyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getSys() {
		return sys;
	}
	public void setSys(String sys) {
		this.sys = sys;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPhoto1() {
		return photo1;
	}

	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}

	public String getPhoto2() {
		return photo2;
	}

	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}

	public String getPhoto3() {
		return photo3;
	}

	public void setPhoto3(String photo3) {
		this.photo3 = photo3;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
