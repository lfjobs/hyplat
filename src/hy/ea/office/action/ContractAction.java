package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.office.Document;
import hy.ea.office.service.ContractService;
import hy.ea.office.service.DocCommonService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.DateJsonValueProcessor;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
/**
 * 合同管理
 */
public class ContractAction {
	private static final Logger logger = LoggerFactory.getLogger(ContractAction.class);

	@Resource
	private ContractService contractService;
	private PageForm pageForm;
	private String result;
	private String search;
	private int pageNumber;
	private String parameter;
	private Document doc;
	private String fileStr;
	private File photo;
	private String photoFileName;
	private String out_request_no;
	private String state;
	@Resource
	private BaseBeanService baseBeanService;

	@Resource
	private UpLoadFileService fileService;
	@Resource
	private DocCommonService docCommonService;



	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();
	SessionWrap sw = SessionWrap.getInstance();

	TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
			SessionWrap.KEY_SHOPCUSCOM);

	/**
	 *
	 * 获取我的文件
	 * @return
	 */
    public String  getMyFileList(){
    	String staffID = "";
		String sccId = request.getParameter("sccId");
		String module = request.getParameter("module");

		HttpSession session1 = ServletActionContext.getRequest().getSession();


		session1.setAttribute("module",module);
		if(sccId==null||sccId.equals("")){
			if(tc==null){
				return "login";
			}else{
				staffID = tc.getStaffid();
				sccId = tc.getSccId();
			}

		}else{
            tc = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?",new Object[]{sccId});


			sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, tc);
			staffID = tc.getStaffid();
		}

        request.setAttribute("sccId",sccId);
       String companyID = "";
       if(module!=null&&!module.equals("")){

		   CAccount account = (CAccount)session1.getAttribute("account");

		   companyID = account.getCompanyID();
		   staffID = account.getStaffID();
	   }

		pageForm = contractService.getMyFileList(pageNumber,30,parameter,staffID,module,companyID,state);

      String ajax = request.getParameter("ajax");
      if("ajax".equals(ajax)){


		  Map<String,Object> map = new HashMap<String,Object>();

		  JsonConfig cfg = new JsonConfig();
		  cfg.setRootClass(Document.class);
		  cfg.setJsonPropertyFilter(new PropertyFilter() {
			  @Override
			  public boolean apply(Object arg0, String arg1, Object arg2) {
				  if (arg1.equals("attachFiles") || arg1.equals("subscribers")) {
					  return true;
				  } else {
					  return false;
				  }
			  }
		  });
		  map.put("pageForm", pageForm);
		  JSONObject jo = JSONObject.fromObject(map,cfg);
		  this.result = jo.toString();

		  return "success";
	  }

		return "filemanage";
	}




	/**
	 *
	 * 待处理
	 * @return
	 */
	public String getDealFileList(){
		String staffID = "";
		String sccId = request.getParameter("sccId");
		if(sccId==null||sccId.equals("")){
			if(tc==null){
				return "login";
			}else{
				staffID = tc.getStaffid();
				sccId = tc.getSccId();
			}

		}else{
			tc = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?",new Object[]{sccId});


			sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, tc);
			staffID = tc.getStaffid();
		}
		HttpSession session1 = ServletActionContext.getRequest().getSession();

		String module = (String)session1.getAttribute("module");
		String companyID = "";
		if(module!=null&&!module.equals("")){

			CAccount account = (CAccount)session1.getAttribute("account");

			companyID = account.getCompanyID();
			staffID = account.getStaffID();
		}

		pageForm = contractService.getDealFileList(pageNumber,30,parameter,staffID,companyID,module,sccId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageForm", pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

             return "success";
	}


	/**
	 *
	 * 获取草稿箱
	 * @return
	 */
	public String  getDraftList(){

		HttpSession session = ServletActionContext.getRequest().getSession();

		String module = (String)session.getAttribute("module");
		CAccount account = (CAccount)session.getAttribute("account");



		pageForm = contractService.getMyFileList(pageNumber,20,parameter,account.getStaffID(),module,account.getCompanyID(),state);



		pageForm = docCommonService.getFullPageForm(pageForm);


		String ajax = request.getParameter("ajax");
		if("ajax".equals(ajax)){


			Map<String,Object> map = new HashMap<String,Object>();

			JsonConfig cfg = new JsonConfig();
			cfg.setRootClass(Document.class);
			cfg.registerJsonValueProcessor(Date.class,
					new DateJsonValueProcessor("yyyy-MM-dd"));
			cfg.setJsonPropertyFilter(new PropertyFilter() {
				@Override
				public boolean apply(Object arg0, String arg1, Object arg2) {
					if (arg1.equals("attachFiles") || arg1.equals("subscribers")) {
						return true;
					} else {
						return false;
					}
				}
			});
			map.put("pageForm", pageForm);
			JSONObject jo = JSONObject.fromObject(map,cfg);
			this.result = jo.toString();

			return "success";
		}

		return state;
	}



	/**
	 *
	 * 待处理
	 * @return
	 */
	public String getDealFileByState(){

		HttpSession session1 = ServletActionContext.getRequest().getSession();

		String module = (String)session1.getAttribute("module");
		CAccount account = (CAccount)session1.getAttribute("account");
		if(account!=null){
		String companyID = account.getCompanyID();
		String staffID = account.getStaffID();

		pageForm = contractService.getDealFileByState(pageNumber, 30, parameter, staffID, companyID, module, state);

		String ajax = request.getParameter("ajax");
		if("ajax".equals(ajax)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageForm", pageForm);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
		}}
		return state;

	}
	/**
	 *
	 * 获取文件预览
	 * @return
	 */
	public String getFilePre(){
    	if("00".equals(doc.getScene())) {
			fileStr = contractService.getFilePre(doc.getScene(), doc.getCompanyID(), doc.getDocId());
			Object object = contractService.getProduct(doc.getCompanyID(),doc.getScene());
			request.setAttribute("obj",object);
		}
		if(tc!=null){
			request.setAttribute("staffID",tc.getStaffid());
			request.setAttribute("sccId",tc.getSccId());

		}
		return "tmppre";

	}

	/**
	 *
	 * 获取签约链接
	 * @return
	 */
	public String getSignUrl(){
        if(doc==null){
        	String ddid = request.getParameter("ddid");
		}
		String signUrl = contractService.signUrl(doc.getDocId(),doc.getCompanyID(),doc.getScene());
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ path + "/";

		try {
			String urlcode =  URLEncoder.encode(basePath + "ea/contract/ea_updateState.jspa?doc.docId=" + doc.getDocId(), "utf-8");
			String url =  signUrl + "&backUrl=" + urlcode;
			HttpServletResponse response = ServletActionContext.getResponse();


			logger.info("调试信息");
			response.sendRedirect(url);

		} catch (Exception e) {
			logger.error("操作异常", e);
		}

//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("signUrl",signUrl);
//		map.put("docId",doc.getDocId());
//		JSONObject jo = JSONObject.fromObject(map);
//		this.result = jo.toString();

		return "success";

	}

	/**
	 *
	 * 核对学员信息
	 * @return
	 */
	public String checkInfo(){

      CashierBills cashierBills = contractService.checkInfo(doc.getDocId());

      request.setAttribute("cashierBills",cashierBills);

		return  "tocard";

	}

	/**
	 *
	 * 修改学员信息
	 * @return
	 */
	public String updateInfo(){
		String noviceName = request.getParameter("noviceName");
		String noviceCode = request.getParameter("noviceCode");
		String reference = request.getParameter("reference");
		String cashierBillsID = request.getParameter("cashierBillsID");

		contractService.updateInfo(noviceName,noviceCode,reference,cashierBillsID);
		request.setAttribute("message","11");

		return "success";

	}



	/**
	 *
	 * 君子签公司认证
	 * * @return
	 */
	public String synJzqCompany(){
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		contractService.synJzqCompany(out_request_no,path);
		return "success";

	}

	/**
	 *
	 * 君子签公司认证结果
	 * * @return
	 */
	public String searchJzqCompany(){

		contractService.searchJzqCompany(out_request_no);
		return "success";

	}

	/**
	 *
	 * 将文件生成新PDF
	 * @return
	 */
	public String getPdfView(){
		String realpath = ServletActionContext.getRequest()
				.getSession().getServletContext().getRealPath("/");
		String pdfpath = contractService.getPdfView(doc.getDocId(),realpath);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pdfpath",pdfpath);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

       return  "success";
	}




	/**
	 *
	 * 获取签约查看链接
	 * @return
	 */
	public String viewUrl(){

		String viewUrl = contractService.viewUrl(doc.getDocId());

		try {

			HttpServletResponse response = ServletActionContext.getResponse();


			logger.info("调试信息");
			response.sendRedirect(viewUrl);

		} catch (Exception e) {
			logger.error("操作异常", e);
		}
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("viewUrl",viewUrl);
//		JSONObject jo = JSONObject.fromObject(map);
//		this.result = jo.toString();

         return "success";
	}

	/**
	 *
	 * 签约
	 * @return
	 */
	public String updateState(){
		//
		contractService.updateState(doc.getDocId());
          return "qyht";
	}


	public String createDoc(){


		return "filemanage";
	}

	/**
	 *
	 * 处理历史签约数据
	 * @return
     */
	public String dealhistorySign(){
		  contractService.applySign(doc.getDocId(),"00");
		 return "success";

	}


	//////////////////////////////PC端///////////////////////////////////////////////

	/**
	 *
	 * 获取当前驾校全部培训合同
	 * @return
	 */
	public String  getAllFileList(){

		HttpSession session = ServletActionContext.getRequest().getSession();

		CAccount account = (CAccount) session.getAttribute("account");
		if(account==null){

			return "notlogin";
		}
        if(search!=null&&search.equals("search")) {

			parameter = (String)session.getAttribute("parameter");

		}

		pageForm = contractService.getALLFileList((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 20 : pageNumber), parameter, account.getCompanyID());
		return "allfiles";
	}


	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("parameter", parameter);
		return getAllFileList();
	}

	/**
	 *
	 * 获取下载地址
	 */
	public String getLoadLink(){

		String loadUrl = contractService.getLoadLink(doc.getDocId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loadUrl",loadUrl);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return "success";
	}

	/**
	 *
	 *
	 * 上传自定义公章
	 * @return
     */
	public String uploadStamp(){
		HttpServletRequest request = ServletActionContext.getRequest();

		CAccount account = (CAccount) request.getSession().getAttribute("account");
		if(account==null){

			return "notlogin";
		}

		String stampname =request.getParameter("stampname");


		if(photo!=null){
			String path = ServletActionContext.getRequest()
					.getSession().getServletContext().getRealPath("/");
			String photopath = fileService.savePhoto(path, photoFileName, photo, account.getCompanyID(), "/stamp/");

			String data = contractService.uploadStamp(account.getCompanyID(),stampname,path+photopath);
		}

		return "success";
	}


	public String docAagin(){
		HttpServletRequest request = ServletActionContext.getRequest();

		 String journalNum = request.getParameter("journalNum");
		String hql = "from CashierBills where journalNum = ?";
		CashierBills cashierBills = (CashierBills)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{journalNum});
        String hqls = "from GoodsBills where cashierBillsID = ?";
		GoodsBills gb = (GoodsBills)baseBeanService.getBeanByHqlAndParams(hqls,new Object[]{cashierBills.getCashierBillsID()});
		contractService.docTempleateParams(cashierBills,cashierBills.getContactUserID(),journalNum,gb.getPpID(),gb.getMoney());
	    return "success";


	}
	/**
	 *
	 * 获取未注册用户
	 *
	 * @return
	 */
	public String getWregMember(){
		HttpServletRequest request = ServletActionContext.getRequest();

		CAccount account = (CAccount) request.getSession().getAttribute("account");
		if(account==null){

			return "notlogin";
		}

       pageForm =  contractService.getWregMember(pageNumber,30,parameter,account.getCompanyID());


		Map<String,Object> map = new HashMap<String,Object>();

		map.put("pageForm", pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();


		return "success";
	}

	/**
	 *
	 * 查询已经有的模板关联人事入职合同的
	 * @return
	 */
	public String getTempConType(){
		HttpServletRequest request = ServletActionContext.getRequest();

		CAccount account = (CAccount) request.getSession().getAttribute("account");
		if(account==null){

			return "notlogin";
		}

        List<Object> list = baseBeanService.getListBeanBySqlAndParams( "select t.templatePath,t.templateId,t.fileType,t.sysSet,t.fileShowName,t.temptId,y.templateTypeName,t.contractType from DT_OA_DOCUMENT_TEMPLATE t,DT_OA_TEMPLATE_TYPE y where t.temptId = y.temptId and t.companyId = ? and t.contractType is not null order by t.contractType",new Object[]{account.getCompanyID()});
		Map<String,Object> map = new HashMap<String,Object>();
		if("company201009046vxdyzy4wg0000000025".equals(account.getCompanyID())||"company201009046vxdyzy4wg0000000065".equals(account.getCompanyID())){
			map.put("isSet", "0");
		}else{
			map.put("isSet", "1");
		}
		map.put("list", list);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}


	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
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

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public String getFileStr() {
		return fileStr;
	}

	public void setFileStr(String fileStr) {
		this.fileStr = fileStr;
	}

	public String getOut_request_no() {
		return out_request_no;
	}

	public void setOut_request_no(String out_request_no) {
		this.out_request_no = out_request_no;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}