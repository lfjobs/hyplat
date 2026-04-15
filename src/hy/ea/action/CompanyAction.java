package hy.ea.action;


import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.*;
import hy.ea.bo.company.ContactCompany;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Controller
@Scope("prototype")
public class CompanyAction {
	
	@Resource
	private CompanyService companyService;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	private CDetail detail;
	private String companyName;
	private String companyIdentifier;
	private String result;
	private List<BaseBean> beans;
	private String bookCurrency;//记账币别
	private String accumulated;
	private String codeID; 
	private CCode code;
	private List<CCode> Codebill;
	private List<BaseBean> list;
	private int pageNumber;
	private String subjectId;
	private String codeValue;
	private String subjectName;
	private String codeId;
	private String verification;
	private String showwechat;
	private File photo;
	private String photoFileName;
	private Company company;
	private ContactCompany contactCompany;
	private File logo; // logo文件
	private String logoFileName; // logo文件名
	private String merchant_shortname; // 公司/商家简称

	/**
	 * @Description: 商家认领，保存公司信息
	 * @author: zy
	 * @Date: 2024/06/17 10:33
	 * @param
	 * @return
	 */
	public String saveClaimCompanyInfo() {
		companyService.saveCompanyInfo(contactCompany,logo,logoFileName,merchant_shortname);
		return "success";
	}

	/**
	 * 去修改单位信息页面
	 * 
	 * @author 
	 */
	public String toEditCompanyDetail() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		company = companyService.getCompanyByCompanyID(account.getCompanyID());
		companyName = company.getCompanyName();
		companyIdentifier = company.getCompanyIdentifier();
		detail=companyService.getCDetailByCompanyID(account.getCompanyID());
		
		//获取货币类别
		codeID="scode20150601x66efsem5e0000000078";
		CAccount account2= (CAccount)ActionContext.getContext().getSession().get("account");
		Object [] params = {account2.getCompanyID(),codeID};
		code = (CCode) codeService.getCCodeByID(account2.getCompanyID(), codeID);
		Codebill = codeService.getCCodeListByPID(account2
				.getCompanyID(), "scode20101101dfs3uhdprp0000000029");
		list=baseBeanService.getListBeanByHqlAndParams("from CCode where companyID = ? and codePID = ? order by codeNumber", params);
		return "to_edit";
	}

	/**
	 * 保存单位信息修改
	 * 
	 * @author 
	 */
	public String saveCompanyDetail() {
		String str=(String)ActionContext.getContext().getSession().get("invalidImge");
		if(!str.equalsIgnoreCase(verification)){
			verification="验证码不正确";
			return toEditCompanyDetail();
		}
		
		try{
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		String companyID=account.getCompanyID();
		Company company = companyService.getCompanyByCompanyID(companyID);
		CLogBook logBook =logBookService.saveCLogBook(null,"修改单位信息(单位名称："+ company.getCompanyName()+")", account);
		company.setCompanyName(companyName);
		company.setShowwechat(showwechat);
		if(!"".equals(codeValue)&&codeValue!=null){
			company.setBookCurrency(codeValue);
			company.setCodeID(codeId);
			company.setSubjectsID(subjectId);
			company.setAccumulated(subjectName);	
		}
		detail.setCompanyID(companyID);
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path, photoFileName,
					photo, account.getCompanyID(),
					"logo/"
							+ Utilities.getDateString(new Date(),
									"yyyy-MM-dd"));
			detail.setLogo(photoPath);
		}
		if(detail.getDetailID() == null ||"".equals(detail.getDetailID()) ){
			detail.setDetailID(serverService.getServerID("cdetail"));
		}
		beans = new ArrayList<BaseBean>();
		beans.add(company);
		beans.add(detail);
		beans.add(logBook);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return toEditCompanyDetail();
	}
	/**
	 * 单位用户前台自行单位邦定
	 * @author 
	 */
	public String companyBonding() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		Company company =companyService.getCompanyByIdentifier(companyIdentifier);
		beans = new ArrayList<BaseBean>(); 
		if(null == company){//解绑
			 company = companyService.getCompanyByCompanyID(account.getCompanyID());
			 company.setShowwechat("00");
			 company.setCompanyPID(account.getCompanyID());
			 String grn=serverService.getServerID("groupCompanySn");
			 company.setGroupCompanySn(grn);
			 String hql = "update CashierBills set pcompanyID = ? where companyID = ?";
			 beans.add(company);
			 baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql},new Object[]{account.getCompanyID(),account.getCompanyID()} );
			 ActionContext.getContext().put("success", "解除绑定成功");
			 return "success";
		    }
	    String companyPID =companyService.getCompanyByIdentifier(companyIdentifier).getCompanyID();
	    if(companyPID.equals(account.getCompanyID())){
	    	ActionContext.getContext().put("success", "不能绑定自己！");
	    	return "success";
	    }
	    ActionContext.getContext().put("success", "绑定成功");
	    company = companyService.getCompanyByCompanyID(account.getCompanyID());
	    String hql = "update CashierBills set pcompanyID = ? where companyID = ?";
	    company.setCompanyPID(companyPID);
	    String companyID=companyService.getCompanyByIdentifier(companyIdentifier).getCompanyPID();
	    if(!"p".equals(companyID.substring(0, 1))){
		    while(!"p".equals(companyID.substring(0, 1))){
		    	String hqls="from Company dt where dt.companyID=?";
		    	Company companys=new Company();
		    	companys=(Company) baseBeanService.getBeanByHqlAndParams(hqls,new Object[] {companyID });
		    	companyID=companys.getCompanyPID();
		    }
	    }
	    CLogBook logBook = logBookService.saveCLogBook(null,"邦定单位(单位名称："+ company.getCompanyName()+")", account);
	    
	    beans.add(company);
	    baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, new String[]{companyPID,account.getCompanyID()});
	    beans.add(logBook);
	    baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);	 
	   
	    
	return "success"; 
}
	/**
	 * 用户去绑定上级页面
	 * 绑定状态默认00, 天太公司梆定上级状态设为01, 公司自行梆定设为02
	 * @author lwt
	 */
	public String toCompanyBonding() {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		Company company =companyService.getCompanyByCompanyID(account.getCompanyID());
		Company Pcompany = companyService.getCompanyByCompanyID(company.getCompanyPID());
		if(Pcompany != null){
		companyIdentifier = Pcompany.getCompanyIdentifier();
		companyName = Pcompany.getCompanyName();
		}
		return "to_bonding";
	}
	/**
	 * 根据companyID查询公司的所有子公司
	 * @author lwt
	 */
	public String getCompanyList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String  companyID = account.getCompanyID();
		if(null!=companyIdentifier&&!"".equals(companyIdentifier))
		{
			companyID=companyIdentifier;
		}
		List<BaseBean> companylist = companyService.getCompanyAndItsChildren(companyID);
		
		//-----------------//仅查询本公司 
		HttpServletRequest  request=ServletActionContext.getRequest();
		String series=request.getParameter("series");
		if("one".equals(series)){
			String hql = "from Company where companyID=? ";
			companylist =baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID()});
		}
		//-----------------//
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companylist", companylist);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}



	public String getCompanyIdentifier() {
		return companyIdentifier;
	}

	public void setCompanyIdentifier(String companyIdentifier) {
		this.companyIdentifier = companyIdentifier;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public CDetail getDetail() {
		return detail;
	}

	public void setDetail(CDetail detail) {
		this.detail = detail;
	}

	public String getBookCurrency() {
		return bookCurrency;
	}

	public void setBookCurrency(String bookCurrency) {
		this.bookCurrency = bookCurrency;
	}

	public String getAccumulated() {
		return accumulated;
	}

	public void setAccumulated(String accumulated) {
		this.accumulated = accumulated;
	}

	public String getCodeID() {
		return codeID;
	}

	public void setCodeID(String codeID) {
		this.codeID = codeID;
	}

	public CCodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CCodeService codeService) {
		this.codeService = codeService;
	}

	public CCode getCode() {
		return code;
	}

	public void setCode(CCode code) {
		this.code = code;
	}

	public List<CCode> getCodebill() {
		return Codebill;
	}

	public void setCodebill(List<CCode> codebill) {
		Codebill = codebill;
	}

	public List<BaseBean> getList() {
		return list;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
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

	public String getShowwechat() {
		return showwechat;
	}

	public void setShowwechat(String showwechat) {
		this.showwechat = showwechat;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	public ContactCompany getContactCompany() {
		return contactCompany;
	}

	public void setContactCompany(ContactCompany contactCompany) {
		this.contactCompany = contactCompany;
	}
	
}
