package hy.ea.human.action.entry;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.human.Audition;
import hy.ea.bo.human.Staff;
import hy.ea.bo.production.recruit.TalentPool;
import hy.ea.human.service.EntryService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 入职相关
 * @author Administrator
 *
 */
public class EntryAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private EntryService entryService;
	private PageForm pageForm;
	private int pageNumber;
	private int pageSize;
	private String result;
	private String status;
	private String vm;
	private String parameter;
	private Audition audition;
	private Audition auditionbc;
	private TalentPool talentPool;
	private File personImageInfo;
	private String personImageInfoFileName;
	private Staff staff;
	HttpSession session = ServletActionContext.getRequest().getSession();
	CAccount account = (CAccount)session.getAttribute("account");
	HttpServletRequest request = ServletActionContext.getRequest();

	// 招聘登记管理列表 status 1:面试管理 2：入职管理
	public String getAuditionList() {
		if(account!=null){
			pageForm = entryService.getlist(pageNumber,20,parameter,status,account.getCompanyID());

			String sajax = request.getParameter("sajax");
			if("sajax".equals(sajax)){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("pageForm",pageForm);
				JSONObject jsonObject = JSONObject.fromObject(map);
				this.result = jsonObject.toString();
				return "success";
			}

		}

		return "emplyment";
	}


	/**
	 *
	 * 查询详细
	 * @return
	 */
	public String getView(){
		audition  = entryService.getViewDetail(audition.getAuditionID());
		auditionbc  = entryService.getViewBc(audition.getAuditionID());
		talentPool  = entryService.getTalentPoolDetail(auditionbc.getTpId());
        String vm = request.getParameter("vm");
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom tcom = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
        request.setAttribute("sccId",tcom.getSccId());

		if("v".equals(vm)){
			return "emplydetail";
		} else if("m".equals(vm)||"zt".equals(vm)){
			//获取题库
			List<BaseBean> bstlist =  entryService.getQuestionBase("00",account.getCompanyID());
			List<BaseBean> mstlist =  entryService.getQuestionBase("01",account.getCompanyID());

			request.setAttribute("bstlist",bstlist);
			request.setAttribute("mstlist",mstlist);



			return "emplyopr";
		}else if("not".equals(vm)){
			Staff staff = entryService.getStaffInfo(tcom.getSccId());
			request.setAttribute("staff",staff);
			return "emplynot";
		}else{
			return "emplyexam";
		}


	}

	/**
	 *
	 * 面试登记，来面试的人员先登记一下
	 * @return
	 */
	public String interviewReg(){
		entryService.interviewReg(audition);
		request.setAttribute("message","11");

       return "success";
	}

	/**
	 *
	 * 面试登记，来面试的人员先登记一下
	 * @return
	 */
	public String bdnotice(){
		entryService.bdnotice(audition);
		request.setAttribute("message","11");

		return "success";
	}




	/**
	 * 面试 口试通过
	 * @return
	 */
	public String passExam(){

		entryService.passExam(vm,audition,account.getStaffID());
		request.setAttribute("message","11");
		return "success";
	}


	/**
	 *
	 * 获取社会人力
	 * @return
	 */
	public String getSocietHumanList() {

		if(account!=null){
			pageForm = entryService.getSocietHumanlist(account.getCompanyID(),parameter,pageNumber,30);
			String sajax = request.getParameter("sajax");
			if("sajax".equals(sajax)){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("pageForm",pageForm);
				JSONObject jsonObject = JSONObject.fromObject(map);
				this.result = jsonObject.toString();
				return "success";
			}

		}


		return "human";
	}
	/**
	 *
	 * 从人力直接转到面试待登记，无需通知
	 * @return
	 */
	public String zmsRegister() {
		String staffID = request.getParameter("staffID");
		String r = "";
		if(account!=null){
			r = entryService.zmsRegister(staffID,account.getCompanyID());
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("r",r);
		JSONObject jsonObject = JSONObject.fromObject(map);
		this.result = jsonObject.toString();
		return "success";
	}



	/**
	 *
	 * 获取个人认证信息
	 * @return
	 */
	public String getSBaseInfo(){
		String staffID = request.getParameter("staffID");
		if(staffID!=null&&!staffID.equals("")) {
		 staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staffID});


		}

		return "sbaseinfo";

	}




	/**
	 *
	 * 保存
	 * @return
	 */
	public String saveInfo(){

		if (personImageInfo != null) {
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");

			String photoPath = fileService
					.savePhoto(
							path,
							personImageInfoFileName,
							personImageInfo,
							account.getCompanyID(),
							"/human/personPhotos/"
									+ Utilities.getDateString(new Date(),
									"yyyy-MM-dd"));

			staff.setPhoto(photoPath);
		}
		entryService.saveInfo(staff,account.getStaffID(),account.getCompanyID());
		request.setAttribute("message","11");

		return "success";
	}

	/**
	 *
	 * 验证身份证号
	 * @return
	 */
	public String checkCard(){

		String r = entryService.checkCard(staff.getStaffIdentityCard(),staff.getStaffID());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("r",r);
		JSONObject jsonObject = JSONObject.fromObject(map);
		this.result = jsonObject.toString();
		return "success";

	}


	/**
	 *
	 * 验证账号
	 * @return
	 */
	public String checkWfjAccount(){
        String acc = request.getParameter("acc");
		String sccId = entryService.checkWfjAccount(acc);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sccId",sccId);
		JSONObject jsonObject = JSONObject.fromObject(map);
		this.result = jsonObject.toString();
		return "success";

	}
	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
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

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public CAccount getAccount() {
		return account;
	}

	public void setAccount(CAccount account) {
		this.account = account;
	}

	public Audition getAudition() {
		return audition;
	}

	public void setAudition(Audition audition) {
		this.audition = audition;
	}

	public String getVm() {
		return vm;
	}

	public void setVm(String vm) {
		this.vm = vm;
	}

	public TalentPool getTalentPool() {
		return talentPool;
	}

	public void setTalentPool(TalentPool talentPool) {
		this.talentPool = talentPool;
	}

	public Audition getAuditionbc() {
		return auditionbc;
	}

	public void setAuditionbc(Audition auditionbc) {
		this.auditionbc = auditionbc;
	}

	public File getPersonImageInfo() {
		return personImageInfo;
	}

	public void setPersonImageInfo(File personImageInfo) {
		this.personImageInfo = personImageInfo;
	}

	public String getPersonImageInfoFileName() {
		return personImageInfoFileName;
	}

	public void setPersonImageInfoFileName(String personImageInfoFileName) {
		this.personImageInfoFileName = personImageInfoFileName;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}
}
