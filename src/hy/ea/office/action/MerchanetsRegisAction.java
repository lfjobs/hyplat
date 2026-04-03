package hy.ea.office.action;


import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.WxMainAccount;
import com.wechat.bo.sft.*;
import hy.ea.bo.CAccount;
import hy.ea.bo.company.ContactCompany;
import hy.ea.service.MerchanetsRegisService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.ea.util.baidu.BankCard;
import hy.ea.util.baidu.BusinessLicense;
import hy.ea.util.baidu.Idcard;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  商户入驻 二级商户进件
 * 
 * @author : mz
 */
@Controller
@Scope("prototype")
public class MerchanetsRegisAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private MerchanetsRegisService merchanetsRegisService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private ServerService serverService;
	@Resource
	private UpLoadFileService fileService;
	private PageForm pageForm;
	private String search;
	private int pageNumber;

	private ApplyParam applyParam;

    private  SalesSceneInfo sales_scene_info;

    private IdCardInfo id_card_info;

    private BusinessLicenseInfo business_license_info;


    private OrganizationCertInfo organization_cert_info;

    private AccountInfo account_info;

    private ContactInfo contact_info;


	private WxMainAccount wxMainAccount;

    private ContactCompany contactCompany;
	
	private InputStream excelStream;

	private String auditResult;

	private ApplyResult applyResult;

	private String mode;

	private File logo; // logo文件
	private String logoFileName; // logo文件名

    private File cardnational;
    private String cardnationalFileName;


	private File cardcopy;
	private String cardcopyFileName;

	private File licensecopy;
	private String licensecopyFileName;

	private File orgcopy;
	private String orgcopyFileName;

	private File[] qualifications;
	private String[] qualificationsFileName;

	private File[] businessadpics;
	private String[] businessadpicsFileName;


	private File accountcopy;
	private String accountcopyFileName;

	private List<BaseBean> list;

	private String result;

	private String companyID;
	private String staffID;
	private String certType;//证件类型 00营业执照 01 身份证人像 02身份证国徽  03银行卡


	/**
	 *
	 * 微信进件
	 * @return
	 */
	public String synwx(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if(account==null){

			return  "not_login";
		}
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		ApplyResult applyResult = merchanetsRegisService.synwx(applyParam.getOut_request_no(),account.getStaffID(),path);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("applyResult",applyResult);
		map.put("account_validation",applyResult.getAccount_validation());
		map.put("audit_detail",applyResult.getAudit_detail());
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();

		 return "success";
	}

	/**
	 *
	 * 查询进件结果
	 * @return
	 */
	public String searchSyncResult(){

		ApplyResult applyResult = merchanetsRegisService.searchSyncResult(applyParam.getOut_request_no());

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("applyResult",applyResult);
		map.put("account_validation",applyResult.getAccount_validation());
		map.put("audit_detail",applyResult.getAudit_detail());
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();

		return "success";
	}

	/**
	 * 企业注册资料管理列表
	 * @return
	 */
	public String getMerchanetsRegistList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if(account==null){

              return  "not_login";
		}
		pageForm = merchanetsRegisService.getMerchanetsList(pageNumber,20,applyParam,account.getCompanyID(),applyResult.getApplyment_state());
		return "merchlist";
	}

	
	//根据条件查询企业注册资料
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("ApplyParam", applyParam);
		return getMerchanetsRegistList();
	}

	/**
	 *
	 * 完善资料
	 * @return
	 */
	public String improveMaterial(){
		applyParam = merchanetsRegisService.getMaterialPage(applyParam.getOut_request_no());
		sales_scene_info = applyParam.getSales_scene_info();
		id_card_info = applyParam.getId_card_info();
		business_license_info  = applyParam.getBusiness_license_info();
		organization_cert_info = applyParam.getOrganization_cert_info();
		account_info = applyParam.getAccount_info();
		contact_info = applyParam.getContact_info();
		contactCompany = merchanetsRegisService.getContactC(applyParam.getApplyID());
		wxMainAccount  = (WxMainAccount)baseBeanService.getBeanByHqlAndParams("from WxMainAccount c where c.companyID = (select cc.comanyId from CcomCom cc where cc.ccompanyId = ?)",new Object[]{contactCompany.getCcompanyID()});

		return "edit";
	}

	/**
	 *
	 * 获取已有资料
	 * @return
	 */
	public String  getMaterialPage(){

		applyParam = merchanetsRegisService.getMaterialPage(applyParam.getOut_request_no());
		sales_scene_info = applyParam.getSales_scene_info();
		id_card_info = applyParam.getId_card_info();
		business_license_info  = applyParam.getBusiness_license_info();
		organization_cert_info = applyParam.getOrganization_cert_info();
		account_info = applyParam.getAccount_info();
		contact_info = applyParam.getContact_info();
		contactCompany = merchanetsRegisService.getContactC(applyParam.getApplyID());

		wxMainAccount  = (WxMainAccount)baseBeanService.getBeanByHqlAndParams("from WxMainAccount  c where c.companyID = (select cc.comanyId from CcomCom cc where cc.ccompanyId = ?)",new Object[]{contactCompany.getCcompanyID()});

         return "material";

	}



	/**
	 *
	 * 保存资料
	 * @return
	 */
	public String saveMaterial(){
		System.out.println("companyID:"+companyID);
		System.out.println("mode:"+mode);

		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if(account==null){
			if(companyID!=null&&!companyID.equals("")){
				account = new CAccount();
				account.setCompanyID(companyID);
			}else {

				return "not_login";
			}
		}
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		//logo
		if (logo != null) {

			String photoPath = fileService.savePhoto(path, logoFileName,
					logo, account.getCompanyID(),
					"logo/"
							+ Utilities.getDateString(new Date(),
							"yyyy-MM-dd"));
			contactCompany.setLogoPath(photoPath);
		}


       //身份证人像
		if (cardcopy != null) {

			String photoPath = fileService.savePhoto(path, cardcopyFileName,
					cardcopy, account.getCompanyID(),
					"info/"
							+ Utilities.getDateString(new Date(),
							"yyyy-MM-dd"));
			id_card_info.setId_card_copy(photoPath);
		}
		//身份证国徽
		if (cardnational != null) {

			String photoPath = fileService.savePhoto(path, cardnationalFileName,
					cardnational, account.getCompanyID(),
					"info/"
							+ Utilities.getDateString(new Date(),
							"yyyy-MM-dd"));
			id_card_info.setId_card_national(photoPath);
		}
        //营业执照
		if (licensecopy != null) {

			String photoPath = fileService.savePhoto(path, licensecopyFileName,
					licensecopy, account.getCompanyID(),
					"info/"
							+ Utilities.getDateString(new Date(),
							"yyyy-MM-dd"));
			business_license_info.setBusiness_license_copy(photoPath);
		}

		//组织机构代码
		if (orgcopy != null) {

			String photoPath = fileService.savePhoto(path, orgcopyFileName,
					orgcopy, account.getCompanyID(),
					"info/"
							+ Utilities.getDateString(new Date(),
							"yyyy-MM-dd"));
			organization_cert_info.setOrganization_copy(photoPath);
		}

		//特殊资质
		if (qualifications != null) {
			String storedpath = "/upload_files/info/"
					+ Utilities.getDateString(new Date(),
					"yyyy-MM-dd");

				String[] photoPath = fileService.mutiFileUpload(path, qualificationsFileName,
						qualifications, account.getCompanyID(),storedpath);
				String qu = "";
			      for(int i=0;i<photoPath.length;i++){
			      	  if(i!=photoPath.length-1) {
						  qu += storedpath +"/"+account.getCompanyID()+"/"+ photoPath[i] + ",";
					  }else{
						  qu += storedpath +"/"+account.getCompanyID()+"/"+ photoPath[i];
					  }
			       }


			applyParam.setQualifications(qu);
		}

		//补充资质
		if (businessadpics != null) {
			String storedpath = "/upload_files/info/"
					+ Utilities.getDateString(new Date(),
					"yyyy-MM-dd");
			String[] photoPath = fileService.mutiFileUpload(path, businessadpicsFileName,
					businessadpics, account.getCompanyID(),
					storedpath);
			String bc = "";
			for(int i=0;i<photoPath.length;i++){
				if(i!=photoPath.length-1) {
					bc += storedpath +"/"+account.getCompanyID()+"/"+ photoPath[i] + ",";
				}else{
					bc += storedpath +"/"+account.getCompanyID()+"/"+ photoPath[i];
				}
			}

			applyParam.setBusiness_addition_pics(bc);
		}


		//银行卡
		if (accountcopy != null) {

			String photoPath = fileService.savePhoto(path, accountcopyFileName,
					accountcopy, account.getCompanyID(),
					"info/"
							+ Utilities.getDateString(new Date(),
							"yyyy-MM-dd"));

			account_info.setAccount_copy(photoPath);
		}

		if(contactCompany==null){
			if(companyID!=null&&!companyID.equals("")){
				contactCompany = (ContactCompany)merchanetsRegisService.getApplydp(companyID).get("contactCompany");

			}
		}

	//	if(companyID!=null&&!companyID.equals("")){
			Map<String,Object>  mp =  saveInfo(business_license_info,organization_cert_info,id_card_info,account_info,contact_info,sales_scene_info);


			BusinessLicenseInfo business_license_info1=(BusinessLicenseInfo)mp.get("business_license_info");
			if(business_license_info1!=null) {
				business_license_info.setBliID(business_license_info1.getBliID());
				business_license_info.setBliKey(business_license_info1.getBliKey());
			}
			OrganizationCertInfo organization_cert_info1 =(OrganizationCertInfo)mp.get("organization_cert_info");
			if(organization_cert_info1!=null) {
				organization_cert_info.setOciID(organization_cert_info1.getOciID());
				organization_cert_info.setOciKey(organization_cert_info1.getOciKey());
			}
			IdCardInfo id_card_info1 =(IdCardInfo)mp.get("id_card_info");
			if(id_card_info1!=null) {
				id_card_info.setCardId(id_card_info1.getCardId());
				id_card_info.setCardKey(id_card_info1.getCardKey());
			}
			AccountInfo account_info1 =(AccountInfo)mp.get("account_info");
			if(account_info1!=null) {
				account_info.setAcId(account_info1.getAcId());
				account_info.setAcKey(account_info1.getAcKey());
			}

			ContactInfo  contact_info1 =(ContactInfo)mp.get("contact");
			if(contact_info1!=null) {
				contact_info.setCoId(contact_info1.getCoId());
				contact_info.setCoKey(contact_info1.getCoKey());
			}

			SalesSceneInfo sales_scene_info1 =(SalesSceneInfo)mp.get("sales_scene_info");
			if(sales_scene_info1!=null) {
				sales_scene_info.setSsId(sales_scene_info1.getSsId());
				sales_scene_info.setSsKey(sales_scene_info1.getSsKey());
			}



	//	}

		applyParam = merchanetsRegisService.saveMaterial(applyParam,business_license_info,organization_cert_info,id_card_info,account_info,contact_info,sales_scene_info,contactCompany,mode);
		HttpServletRequest request = ServletActionContext.getRequest();
		String ajax = request.getParameter("ajax");
		if("ajax".equals(ajax)){

			Map<String,Object> map = new HashMap<String,Object>();
			map.put("applyParam",applyParam);
			JSONObject jo = JSONObject.fromObject(map);
			result = jo.toString();
		}

      return "success";
	}

	/**
	 * 提交审核
	 * @return
	 */
	public  String submitAudit(){


		contactCompany = merchanetsRegisService.submitAudit(staffID,companyID,merchanetsRegisService.getCCompany(companyID));

		return "queryState";

	}


	private Map<String,Object> saveInfo(BusinessLicenseInfo business_license_info,OrganizationCertInfo organization_cert_info,IdCardInfo id_card_info,AccountInfo account_info,ContactInfo contact_info,SalesSceneInfo sales_scene_info){
		//店铺信息已经放到认领的时候添加了，故此处注释掉，由后续证件认领逻辑修改
		/*if(sales_scene_info!=null&&(sales_scene_info.getSsId()==null||sales_scene_info.getSsId().equals(""))){
			sales_scene_info.setSsId(serverService.getServerID("ssid"));
			merchanetsRegisService.saveInfo(business_license_info,organization_cert_info,id_card_info,account_info,contact_info,sales_scene_info);

		}*/
		if(business_license_info!=null){
			   if(business_license_info.getBliID()==null||business_license_info.getBliID().equals("")) {
				   business_license_info.setBliID(serverService.getServerID("bliid"));
			   }

			if(organization_cert_info!=null&&organization_cert_info.getOrganization_number()!=null&&!organization_cert_info.getOrganization_number().equals("")&&(organization_cert_info.getOciID()==null||organization_cert_info.getOciID().equals(""))){
				organization_cert_info.setOciID(serverService.getServerID("ociid"));


			}

			merchanetsRegisService.saveInfo(business_license_info,organization_cert_info,id_card_info,account_info,contact_info,sales_scene_info);

		}

		if(id_card_info!=null&&(id_card_info.getCardId()==null||id_card_info.getCardId().equals(""))){
			id_card_info.setCardId(serverService.getServerID("cardid"));
			merchanetsRegisService.saveInfo(business_license_info,organization_cert_info,id_card_info,account_info,contact_info,sales_scene_info);

		}
		if(account_info!=null&&(account_info.getAcId()==null||account_info.getAcId().equals(""))){
			account_info.setAcId(serverService.getServerID("acid"));
			merchanetsRegisService.saveInfo(business_license_info,organization_cert_info,id_card_info,account_info,contact_info,sales_scene_info);

		}
		if(contact_info!=null&&(contact_info.getCoId()==null||contact_info.getCoId().equals(""))){
			if(contact_info.getCoId()==null||contact_info.getCoId().equals("")) {
				contact_info.setCoId(serverService.getServerID("coid"));
			}

			merchanetsRegisService.saveInfo(business_license_info,organization_cert_info,id_card_info,account_info,contact_info,sales_scene_info);

		}


		Map<String,Object> map = merchanetsRegisService.getInfo(business_license_info,organization_cert_info,id_card_info,account_info,contact_info,sales_scene_info);


	      return	map;

	}

	/**
	 *
	 * 获取银行类型
	 * @return
	 */
	public String getBackTypeList(){
		 list = merchanetsRegisService.getBackTypeList();
        return "success";

	}

	
	/**
	 * 导出企业资料
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showExcel() {
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		CAccount account = (CAccount) session.get("account");
//		String organizationID=(String) session.get("organizationID");
//		Company company = companyService.getCompanyByCompanyID(account.getCompanyID());
//
//		if ("lovers".equals(company.getCompanyIdentifier()) && "sa".equals(account.getAccountEmail())) {
//			List<Object> list = getList();
//			String sql = (String) list.get(0);
//			Object[] parms = (Object[]) list.get(1);
//
//			excelStream = excelService.showExcel(ContactCompany.columnHeadings1(),
//					baseBeanService.getListBeanBySqlAndParams(sql, parms));
//			CLogBook logBook = logBookService.saveCLogBook(organizationID,"导出企业资料", account);
//			baseBeanService.update(logBook);
//			return "showexcel";
//		}
		return "no_authority";
	}




	/////////////////////////////////////////////////手机版认证//////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 *
	 * 获取认证主题类型
	 * @return
	 */
	public String getApplyTypeInfo(){
		contactCompany = merchanetsRegisService.getCCompany(companyID);

		if("01".equals(contactCompany.getAuthState())){

			return "queryState";
		}
		applyParam =  merchanetsRegisService.getApplyInfo(companyID);
		HttpServletRequest request = ServletActionContext.getRequest();
		String reAudit = request.getParameter("reAudit");
		if("1".equals(reAudit)){

			sales_scene_info = applyParam.getSales_scene_info();

			return   "mbapply_dp";
		}






            return   "mbapply_type";



	}


	/**
	 *
	 * 补充或者特殊资质
	 * @return
	 */
	public String getApplyBcInfo(){
		  contactCompany = merchanetsRegisService.getCCompany(companyID);

		if("01".equals(contactCompany.getAuthState())){

			return "queryState";
		}

		applyParam =  merchanetsRegisService.getApplyInfo(companyID);
		return   "mbapply_bc";



	}


	/**
	 *
	 * 店铺信息
	 * @return
	 */
	public String getApplydp(){



		Map<String,Object> map =  merchanetsRegisService.getApplydp(companyID);

		applyParam = (ApplyParam)map.get("applyParam");
		contactCompany = (ContactCompany)map.get("contactCompany");

		if("01".equals(contactCompany.getAuthState())){

			return "queryState";
		}
		sales_scene_info = applyParam.getSales_scene_info();

		return   "mbapply_dp";
	}

	/**
	 *
	 *  营业执照
	 * @return
	 */
	public String getApplyLicense(){
		  contactCompany = merchanetsRegisService.getCCompany(companyID);

		if("01".equals(contactCompany.getAuthState())){

			return "queryState";
		}

		applyParam = merchanetsRegisService.getApply(applyParam.getOut_request_no());
		business_license_info = applyParam.getBusiness_license_info();
		organization_cert_info = applyParam.getOrganization_cert_info();
		return   "mbapply_license";
	}




	/**
	 *
	 *  法人身份证
	 * @return
	 */
	public String getApplyCard(){
		  contactCompany = merchanetsRegisService.getCCompany(companyID);

		if("01".equals(contactCompany.getAuthState())){

			return "queryState";
		}

		applyParam = merchanetsRegisService.getApply(applyParam.getOut_request_no());
		id_card_info = applyParam.getId_card_info();

		return   "mbapply_card";
	}

	/**
	 *
	 *  结算银行账户
	 * @return
	 */
	public String getApplyAccount(){
		  contactCompany = merchanetsRegisService.getCCompany(companyID);

		if("01".equals(contactCompany.getAuthState())){

			return "queryState";
		}

		applyParam = merchanetsRegisService.getApply(applyParam.getOut_request_no());
		account_info = applyParam.getAccount_info();

		return   "mbapply_account";
	}


	/**
	 *
	 *  超级管理员
	 * @return
	 */
	public String getApplyContact(){
		  contactCompany = merchanetsRegisService.getCCompany(companyID);

		if("01".equals(contactCompany.getAuthState())){

			return "queryState";
		}

		applyParam = merchanetsRegisService.getApply(applyParam.getOut_request_no());
		contact_info = applyParam.getContact_info();

		return   "mbapply_contact";
	}




	/**
	 *
	 * 识别证件
	 * @return
	 */
	public String shibieCert(){
		Map<String,Object> map = new HashMap<String,Object>();
           if("00".equals(certType)){
			   business_license_info = BusinessLicense.businessLicense(business_license_info.getBusiness_license_copy());
			   map.put("business_license_info",business_license_info);
		   }else if("01".equals(certType)||"02".equals(certType)){
			   id_card_info = Idcard.idcard(id_card_info.getId_card_copy(),certType);
			   map.put("id_card_info",id_card_info);
		   }else if("03".equals(certType)){
			   account_info = BankCard.bankCard(account_info.getAccount_copy());
			   map.put("account_info",account_info);
		   }

		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		   return  "success";
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

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public ApplyParam getApplyParam() {
		return applyParam;
	}

	public void setApplyParam(ApplyParam applyParam) {
		this.applyParam = applyParam;
	}

    public SalesSceneInfo getSales_scene_info() {
        return sales_scene_info;
    }

    public void setSales_scene_info(SalesSceneInfo sales_scene_info) {
        this.sales_scene_info = sales_scene_info;
    }

    public ContactCompany getContactCompany() {
        return contactCompany;
    }

    public void setContactCompany(ContactCompany contactCompany) {
        this.contactCompany = contactCompany;
    }

    public IdCardInfo getId_card_info() {
        return id_card_info;
    }

    public void setId_card_info(IdCardInfo id_card_info) {
        this.id_card_info = id_card_info;
    }


    public BusinessLicenseInfo getBusiness_license_info() {
        return business_license_info;
    }

    public void setBusiness_license_info(BusinessLicenseInfo business_license_info) {
        this.business_license_info = business_license_info;
    }

    public OrganizationCertInfo getOrganization_cert_info() {
        return organization_cert_info;
    }

    public void setOrganization_cert_info(OrganizationCertInfo organization_cert_info) {
        this.organization_cert_info = organization_cert_info;
    }

    public AccountInfo getAccount_info() {
        return account_info;
    }

    public void setAccount_info(AccountInfo account_info) {
        this.account_info = account_info;
    }

	public ContactInfo getContact_info() {
		return contact_info;
	}

	public void setContact_info(ContactInfo contact_info) {
		this.contact_info = contact_info;
	}

	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public File getLogo() {
		return logo;
	}

	public void setLogo(File logo) {
		this.logo = logo;
	}

	public String getLogoFileName() {
		return logoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}

	public File getCardnational() {
		return cardnational;
	}

	public void setCardnational(File cardnational) {
		this.cardnational = cardnational;
	}

	public String getCardnationalFileName() {
		return cardnationalFileName;
	}

	public void setCardnationalFileName(String cardnationalFileName) {
		this.cardnationalFileName = cardnationalFileName;
	}

	public File getCardcopy() {
		return cardcopy;
	}

	public void setCardcopy(File cardcopy) {
		this.cardcopy = cardcopy;
	}

	public String getCardcopyFileName() {
		return cardcopyFileName;
	}

	public void setCardcopyFileName(String cardcopyFileName) {
		this.cardcopyFileName = cardcopyFileName;
	}

	public File getLicensecopy() {
		return licensecopy;
	}

	public void setLicensecopy(File licensecopy) {
		this.licensecopy = licensecopy;
	}

	public String getLicensecopyFileName() {
		return licensecopyFileName;
	}

	public void setLicensecopyFileName(String licensecopyFileName) {
		this.licensecopyFileName = licensecopyFileName;
	}

	public File getOrgcopy() {
		return orgcopy;
	}

	public void setOrgcopy(File orgcopy) {
		this.orgcopy = orgcopy;
	}

	public String getOrgcopyFileName() {
		return orgcopyFileName;
	}

	public void setOrgcopyFileName(String orgcopyFileName) {
		this.orgcopyFileName = orgcopyFileName;
	}


	public File[] getQualifications() {
		return qualifications;
	}

	public void setQualifications(File[] qualifications) {
		this.qualifications = qualifications;
	}

	public String[] getQualificationsFileName() {
		return qualificationsFileName;
	}

	public void setQualificationsFileName(String[] qualificationsFileName) {
		this.qualificationsFileName = qualificationsFileName;
	}

	public File[] getBusinessadpics() {
		return businessadpics;
	}

	public void setBusinessadpics(File[] businessadpics) {
		this.businessadpics = businessadpics;
	}

	public String[] getBusinessadpicsFileName() {
		return businessadpicsFileName;
	}

	public void setBusinessadpicsFileName(String[] businessadpicsFileName) {
		this.businessadpicsFileName = businessadpicsFileName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public ApplyResult getApplyResult() {
		return applyResult;
	}

	public void setApplyResult(ApplyResult applyResult) {
		this.applyResult = applyResult;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public File getAccountcopy() {
		return accountcopy;
	}

	public void setAccountcopy(File accountcopy) {
		this.accountcopy = accountcopy;
	}

	public String getAccountcopyFileName() {
		return accountcopyFileName;
	}

	public void setAccountcopyFileName(String accountcopyFileName) {
		this.accountcopyFileName = accountcopyFileName;
	}

	public WxMainAccount getWxMainAccount() {
		return wxMainAccount;
	}

	public void setWxMainAccount(WxMainAccount wxMainAccount) {
		this.wxMainAccount = wxMainAccount;
	}

}
