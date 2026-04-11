package hy.ea.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiantai.wfj.bo.WxMainAccount;
import com.wechat.bo.sft.*;
import com.wechat.utils.HTTPV3;
import com.wechat.utils.RsaCryptoUtil;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.human.Staff;
import hy.ea.service.MerchanetsRegisService;
import hy.ea.util.Constant;
import hy.ea.util.MobileMessage;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.bo.AuditRecord;
import mobile.tiantai.android.util.JushMain;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MerchanetsRegisServiceImpl implements MerchanetsRegisService {

   @Resource
   private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
    private BaseBeanDao baseBeanDao;

	@Autowired
	private MobileMessage msage;

	/**
	 *
	 *入驻的商户
	 * @param pageNumber
	 * @param pageSize
	 * @param applyParam
	 * @param companyID
	 * @return
	 */
	@Override
	public PageForm  getMerchanetsList(int pageNumber,int pageSize,ApplyParam applyParam,String companyID,String state){
		PageForm pageForm = null;
		if(Constant.COMPAYN_ID.equals(companyID)){
			 StringBuilder sb = new StringBuilder();
			 sb.append("select c.ccompanyid, c.companyname, p.out_request_no, p.organization_type,p.merchant_shortname,i.id_card_name,t.submitdate,t.state,a.applyment_state,a.applyment_state_desc,a.applyment_id,r.reason,t.auid,t.auditComment,a.jzqresultCode,a.jzqmsg,a.emailOrMobile");
			 sb.append(" from dtContactCompany c left join DT_AUDITRECORD t on c.ccompanyid = t.thirdid  left join dt_sft_ApplyParam p on c.applyid = p.applyid  left join dt_sft_IdCardInfo i on p.cardkey = i.cardkey");
             sb.append(" left join dt_sft_ApplyResult a on a.out_request_no = p.out_request_no left join (select  wmsys.wm_concat(s.reject_reason) reason,s.arid from dt_sft_applyresult t left join dt_sft_auditdetail s on t.arid = s.arid group by s.arid) r on a.arid = r.arid");
			 sb.append(" where t.module = ?");
             List<Object> params = new ArrayList<Object>();

			params.add("公司认证");
			if(state!=null){

//				if("FINISH".equals(state)) {
//					sb.append(" and a.applyment_state = ?");
//					params.add(state);
//				}else{
//					sb.append(" and (a.applyment_state != ? or a.applyment_state is null)");
//					params.add("FINISH");
//				}

				if("FINISH".equals(state)){
					sb.append(" and c.authState = ?");
					params.add("02");
				}else{
					sb.append(" and (c.authState = ? or c.authState = ? or c.authState = ?)");
					params.add("00");
					params.add("01");
					params.add("03");

				}
			}


			if(applyParam!=null&&applyParam.getMerchant_shortname()!=null&&!applyParam.getMerchant_shortname().equals("")){
                   sb.append(" and (c.companyName like ? or p.merchant_shortname like ?)");
                   params.add("%"+applyParam.getMerchant_shortname()+"%");
			     	params.add("%"+applyParam.getMerchant_shortname()+"%");
			}
			if(applyParam!=null&&applyParam.getOrganization_type()!=null&&!applyParam.getOrganization_type().equals("")){

				if("2401".equals(applyParam.getOrganization_type())){
					sb.append(" and p.organization_type = ?");
					params.add(applyParam.getOrganization_type());
				}else{
					sb.append(" and (p.organization_type != ? or p.organization_type is null)");
					params.add("2401");
				}



			}

			sb.append(" order by t.submitdate desc");
			pageForm  = baseBeanService.getPageFormBySQL(pageNumber,pageSize,sb.toString(),"select count(*) from (" + sb.toString() + ")",params.toArray());

		}

       return  pageForm;
	}


	/**
	 *
	 * 微信进件
	 * @param out_request_no
	 * @param staffID
	 * @return
	 */
	@Transactional
	public ApplyResult  synwx(String out_request_no,String staffID,String path){
		 ApplyParam applyParam = (ApplyParam)baseBeanDao.getBeanByHqlAndParams("from ApplyParam where out_request_no = ?",new Object[]{out_request_no});

		  logger.info("调试信息");
		    //默认值设定
		 //  applyParam.setNeed_account_info(true);
		   applyParam.setId_doc_type("IDENTIFICATION_TYPE_MAINLAND_IDCARD");

		   JSONArray jsonArray  = new JSONArray();
		   if(applyParam.getQualifications()!=null&&!applyParam.getQualifications().equals("")) {
			   String[] qa = applyParam.getQualifications().split(",");
			   for (String q : qa) {
				   String s = HTTPV3.httpImage(path + q);
				   jsonArray.add(s);

			   }
		   }
		if(jsonArray.size()>0) {
			applyParam.setQualifications(jsonArray.toString());
		}

	   	JSONArray jsonArray2  = new JSONArray();
		if(applyParam.getBusiness_addition_pics()!=null&&!applyParam.getBusiness_addition_pics().equals("")) {
			String[] qa2 = applyParam.getBusiness_addition_pics().split(",");
			for (String q : qa2) {
				String s = HTTPV3.httpImage(path + q);
				jsonArray2.add(s);

			}
		}
		if(jsonArray2.size()>0) {
			applyParam.setBusiness_addition_pics(jsonArray2.toString());
		}


		   BusinessLicenseInfo businessLicenseInfo = applyParam.getBusiness_license_info();

		if(businessLicenseInfo!=null) {
			logger.info("blikey:: {}", businessLicenseInfo.getBliKey());
			logger.info("调试信息");
			businessLicenseInfo.setBusiness_license_copy(HTTPV3.httpImage(path + businessLicenseInfo.getBusiness_license_copy()));
			logger.info("Business_license_copy: {}", businessLicenseInfo.getBusiness_license_copy());
			applyParam.setBusiness_license_info(businessLicenseInfo);
		}
		   OrganizationCertInfo organization_cert_info = applyParam.getOrganization_cert_info();
		   if(organization_cert_info!=null&&organization_cert_info.getOrganization_copy()!=null&&!organization_cert_info.getOrganization_copy().equals("")) {
			   organization_cert_info.setOrganization_copy(HTTPV3.httpImage(path + organization_cert_info.getOrganization_copy()));//主体为“企业/党政、机关及事业单位/其他组织”，且营业执照/登记证书号码不是18位时必填
			   applyParam.setOrganization_cert_info(organization_cert_info);
		   }
		  IdCardInfo idCardInfo = applyParam.getId_card_info();
		logger.info("调试信息");
		logger.info("调试信息");
		  idCardInfo.setId_card_copy(HTTPV3.httpImage(path+idCardInfo.getId_card_copy()));  //身份证人像面照片
		  idCardInfo.setId_card_national(HTTPV3.httpImage(path+idCardInfo.getId_card_national()));//身份证国徽面照片
		 //该字段需进行加密处理
		  idCardInfo.setId_card_name(RsaCryptoUtil.ncrypt(idCardInfo.getId_card_name()));
		 //该字段需进行加密处理
		  idCardInfo.setId_card_number(RsaCryptoUtil.ncrypt(idCardInfo.getId_card_number()));
		  idCardInfo.setId_card_address(RsaCryptoUtil.ncrypt(idCardInfo.getId_card_address()));
		  applyParam.setId_card_info(idCardInfo);

		AccountInfo accountInfo = applyParam.getAccount_info();
		logger.info("调试信息");
		//该字段需进行加密处理
		accountInfo.setAccount_name(RsaCryptoUtil.ncrypt(accountInfo.getAccount_name()));
		//该字段需进行加密处理
		accountInfo.setAccount_number(RsaCryptoUtil.ncrypt(accountInfo.getAccount_number()));
		applyParam.setAccount_info(accountInfo);

		ContactInfo contactInfo = applyParam.getContact_info();
		//该字段需进行加密处理
		contactInfo.setContact_name(RsaCryptoUtil.ncrypt(contactInfo.getContact_name()));
		//该字段需进行加密处理
		contactInfo.setContact_id_card_number(RsaCryptoUtil.ncrypt(contactInfo.getContact_id_card_number()));
		//该字段需进行加密处理
		contactInfo.setMobile_phone(RsaCryptoUtil.ncrypt(contactInfo.getMobile_phone()));
		//该字段需进行加密处理
		contactInfo.setContact_email(RsaCryptoUtil.ncrypt(contactInfo.getContact_email()));
		applyParam.setContact_info(contactInfo);

		SalesSceneInfo salesSceneInfo = applyParam.getSales_scene_info();
		salesSceneInfo.setStore_url("http://www.impf2010.com");
		 applyParam.setSales_scene_info(salesSceneInfo);
         logger.info("调试信息");
		 JSONObject body = HTTPV3.applyments(applyParam);
		logger.info("调试信息");
//		 String code = body.getString("code");
//		 String message = body.getString("message");

		List<BaseBean>  beans =  new ArrayList<BaseBean>();

		ApplyResult applyResult = (ApplyResult)baseBeanDao.getBeanByHqlAndParams("from ApplyResult where out_request_no = ?",new Object[]{out_request_no});
		try {
			applyResult = HTTPV3.applymentsQuery(out_request_no,applyResult);

			if(applyResult.getArId()==null||applyResult.getArId().equals("")){
				applyResult.setArId(serverService.getServerID("arid"));
			}

			AccountValidation accountValidation = applyResult.getAccount_validation();
			if(accountValidation!=null){
				accountValidation.setAvId(serverService.getServerID("avid"));
			}
			beans.add(accountValidation);
			beans.add(applyResult);
			List<AuditDetail> audit_detail = applyResult.getAudit_detail();
			String hql = "delete from AuditDetail where arId = ?";
			List<Object[]> params = new ArrayList<Object[]>();
			Object[] obj = new Object[]{applyResult.getArId()};
			params.add(obj);
			if(audit_detail!=null&&audit_detail.size()>0) {
				for (AuditDetail detail : audit_detail) {
					detail.setArId(applyResult.getArId());
					detail.setAdId(serverService.getServerID("adid"));
					beans.add(detail);
				}
			}

			baseBeanDao.executeHqlsByParmsList(beans,new String[]{hql},params);

		}catch (Exception e){
			logger.error("操作异常", e);
		}




		return applyResult;
	}

	/**
	 *
	 * 查询进件结果
	 * @param out_request_no
	 * @return
	 */
	@Transactional
	public ApplyResult  searchSyncResult(String out_request_no){
		List<BaseBean>  beans =  new ArrayList<BaseBean>();
		ApplyResult applyResult = (ApplyResult)baseBeanDao.getBeanByHqlAndParams("from ApplyResult where out_request_no = ?",new Object[]{out_request_no});
		String avKey = "";
		String avId = "";
		if(applyResult!=null) {
			 AccountValidation accountValidation = (AccountValidation)baseBeanDao.getBeanByHqlAndParams("from AccountValidation where avId = ?",new Object[]{applyResult.getAccount_validationID()});
			 if(accountValidation!=null) {
				 avKey = accountValidation.getAvKey();
				 avId = accountValidation.getAvId();
			 }
		}
		try {
			applyResult = HTTPV3.applymentsQuery(out_request_no,applyResult);

			if(applyResult.getArId()==null||applyResult.getArId().equals("")){
				applyResult.setArId(serverService.getServerID("arid"));
			}

			AccountValidation accountValidation = applyResult.getAccount_validation();

			if(accountValidation!=null) {
				if (avKey.equals("")) {
					accountValidation.setAvId(serverService.getServerID("avid"));
				} else {
					accountValidation.setAvId(avId);
					accountValidation.setAvKey(avKey);
				}
				applyResult.setAccount_validationID(accountValidation.getAvId());
			}

			beans.add(accountValidation);
	    	beans.add(applyResult);
			List<AuditDetail> audit_detail = applyResult.getAudit_detail();
			String hql = "delete from AuditDetail where arId = ?";
            List<Object[]> params = new ArrayList<Object[]>();
            Object[] obj = new Object[]{applyResult.getArId()};
			params.add(obj);
			if(audit_detail!=null&&audit_detail.size()>0) {
				for (AuditDetail detail : audit_detail) {
					detail.setArId(applyResult.getArId());
					detail.setAdId(serverService.getServerID("adid"));
					beans.add(detail);
				}
			}

            baseBeanDao.executeHqlsByParmsList(beans,new String[]{hql},params);

		}catch (Exception e){
			logger.error("操作异常", e);
		}





		return applyResult;
	}

	/**
	 *
	 * 完善资料
	 * @param applyParam
	 * @return
	 */
	public ApplyParam improveMaterial(ApplyParam applyParam){


               return null;
	}


	/**
	 *
	 * 获取资料
	 * @param out_request_no
	 * @return
	 */
	public ApplyParam getMaterialPage(String out_request_no){

		ApplyParam applyParam = (ApplyParam)baseBeanDao.getBeanByHqlAndParams("from ApplyParam where out_request_no = ?",new Object[]{out_request_no});
            return applyParam;
	}



	/**
	 *
	 * 获取往来单位
	 * @param applyID
	 * @return
	 */
	public ContactCompany getContactC(String applyID){

		ContactCompany cc = (ContactCompany)baseBeanDao.getBeanByHqlAndParams("from ContactCompany where applyID = ?",new Object[]{applyID});

		return cc;
	}





	/**
	 *
	 * 保存资料
	 * @param applyParam
	 * @param business_license_info
	 * @param organization_cert_info
	 * @param id_card_info
	 * @param account_info
	 * @param contact_info
	 * @param sales_scene_info
	 * @return
	 */
	@Transactional
	public ApplyParam saveMaterial(ApplyParam applyParam, BusinessLicenseInfo business_license_info, OrganizationCertInfo organization_cert_info, IdCardInfo id_card_info,AccountInfo account_info, ContactInfo contact_info, SalesSceneInfo sales_scene_info, ContactCompany contactCompany,String mode) {
		ApplyParam app = (ApplyParam)baseBeanDao.getBeanByHqlAndParams("from ApplyParam where out_request_no = ?",new Object[]{applyParam.getOut_request_no()});
		List<BaseBean>    beans = new ArrayList<BaseBean>();
		if("organization_type".equals(mode)){
           //修改主体类型

			if(app==null){
				app = new ApplyParam();
				app.setApplyID(serverService.getServerID("applyID"));
				app.setOut_request_no(serverService.getServerID("wfj"));
				contactCompany.setApplyID(app.getApplyID());
				beans.add(contactCompany);
			}

			app.setOrganization_type(applyParam.getOrganization_type());
			beans.add(app);

		}else if("id_card_info".equals(mode)){
			//身份证
			IdCardInfo cardInfo =  app.getId_card_info();
			if(cardInfo==null){
				cardInfo = new IdCardInfo();
			}
			if(id_card_info.getId_card_copy()!=null&&!id_card_info.getId_card_copy().equals("")) {
				cardInfo.setId_card_copy(id_card_info.getId_card_copy());
			}
			if(id_card_info.getId_card_national()!=null&&!id_card_info.getId_card_national().equals("")) {
				cardInfo.setId_card_national(id_card_info.getId_card_national());
			}
			cardInfo.setCardId(id_card_info.getCardId());
			cardInfo.setCardKey(id_card_info.getCardKey());
			cardInfo.setId_card_name(id_card_info.getId_card_name());
			cardInfo.setId_card_number(id_card_info.getId_card_number());
			cardInfo.setId_card_valid_time(id_card_info.getId_card_valid_time());
			cardInfo.setId_card_address(id_card_info.getId_card_address());
			cardInfo.setId_card_valid_time_begin(id_card_info.getId_card_valid_time_begin());
			//cardInfo.setLegal_tel(id_card_info.getLegal_tel());
			app.setId_card_info(cardInfo);
			beans.add(app);
			beans.add(cardInfo);

		}else if("business_license_info".equals(mode)){
			//ApplyParam appOld = (ApplyParam) this.baseBeanDao.getBeanByHqlAndParams("from ApplyParam where applyID = ?",new Object[]{contactCompany.getApplyID()});
			//店铺基本信息
			SalesSceneInfo sale =  app.getSales_scene_info();
			if(sale==null){
				sale = new SalesSceneInfo();
			}
			sale.setStore_name(sales_scene_info.getStore_name());
			sale.setSsId(serverService.getServerID("ssid"));
			//sale.setSsKey(sales_scene_info.getSsKey());
			beans.add(sale);
			app.setMerchant_shortname(applyParam.getMerchant_shortname());
			app.setSales_scene_info(sale);
			Company company = (Company)baseBeanDao.getBeanByHqlAndParams("from Company c where c.companyID = (select cc.comanyId from CcomCom cc where cc.ccompanyId = ?)",new Object[]{contactCompany.getCcompanyID()});
			WxMainAccount wxMainAccount = (WxMainAccount)baseBeanDao.getBeanByHqlAndParams("from WxMainAccount wc where companyID = ?",new Object[]{company.getCompanyID()});
			if(wxMainAccount==null){
				wxMainAccount = new WxMainAccount();
				wxMainAccount.setWxmId(serverService.getServerID("wxmid"));
				wxMainAccount.setCompanyID(company.getCompanyID());
				wxMainAccount.setSr("0");
				wxMainAccount.setYe("0");
				wxMainAccount.setZc("0");
				wxMainAccount.setRate((contactCompany.getRemark()==null||contactCompany.getRemark().equals(""))?"0.006":contactCompany.getRemark());
			}else{
				wxMainAccount.setRate((contactCompany.getRemark()==null||contactCompany.getRemark().equals(""))?"0.006":contactCompany.getRemark());
			}
			beans.add(wxMainAccount);
			//营业执照
			BusinessLicenseInfo businessLicenseInfo = app.getBusiness_license_info();
			if(businessLicenseInfo==null){
				businessLicenseInfo = new BusinessLicenseInfo();
			}

			if(business_license_info.getBusiness_license_copy()!=null&&!business_license_info.getBusiness_license_copy().equals("")) {
				businessLicenseInfo.setBusiness_license_copy(business_license_info.getBusiness_license_copy());
			}
			businessLicenseInfo.setBliID(business_license_info.getBliID());
			businessLicenseInfo.setBliKey(business_license_info.getBliKey());
			businessLicenseInfo.setBusiness_license_number(business_license_info.getBusiness_license_number());
			businessLicenseInfo.setMerchant_name(business_license_info.getMerchant_name());
			businessLicenseInfo.setLegal_person(business_license_info.getLegal_person());
			businessLicenseInfo.setCompany_address(business_license_info.getCompany_address());
			//证件编号
			businessLicenseInfo.setBusiness_license_name(business_license_info.getBusiness_license_name());
			//经营范围
			businessLicenseInfo.setBusiness_scope(business_license_info.getBusiness_scope());
			//发证单位
			businessLicenseInfo.setIssueUnit(business_license_info.getIssueUnit());
			//年审管理
			businessLicenseInfo.setAnnual(business_license_info.getAnnual());
			//审核人
			businessLicenseInfo.setAuditor(business_license_info.getAuditor());

			businessLicenseInfo.setBusiness_time(business_license_info.getBusiness_time().replace("a","\\"));//主体为“党政、机关及事业单位/其他组织”时必填

			beans.add(businessLicenseInfo);
			if(businessLicenseInfo.getBusiness_license_number().length()==15) {
				//组织机构代码  主体为“企业/党政、机关及事业单位/其他组织”，且营业执照/登记证书号码不是18位时必填。
				OrganizationCertInfo organizationCertInfo = app.getOrganization_cert_info();
				if (organizationCertInfo == null) {
					organizationCertInfo = new OrganizationCertInfo();
				}
				if (organization_cert_info != null && organization_cert_info.getOrganization_copy() != null && !organization_cert_info.getOrganization_copy().equals("")) {
					organizationCertInfo.setOrganization_copy(organization_cert_info.getOrganization_copy());
				}
				if (organization_cert_info != null && organization_cert_info.getOrganization_number() != null) {
					organizationCertInfo.setOrganization_number(organization_cert_info.getOrganization_number());
					organizationCertInfo.setOrganization_time(organization_cert_info.getOrganization_time().replace("a", "\\"));//[\"2014-01-01\",\"长期\"]
					organizationCertInfo.setOciID(organization_cert_info.getOciID());
					organizationCertInfo.setOciKey(organization_cert_info.getOciKey());
					beans.add(organizationCertInfo);
					app.setOrganization_cert_info(organizationCertInfo);
				}
			}

			app.setBusiness_license_info(businessLicenseInfo);
			beans.add(app);
		}else if("account_info".equals(mode)){
			//结算账户
			AccountInfo   accountInfo = app.getAccount_info();
			if(accountInfo==null){
				accountInfo = new AccountInfo();
			}
			accountInfo.setBank_account_type(account_info.getBank_account_type());
			accountInfo.setAccount_bank(account_info.getAccount_bank());
			accountInfo.setAccount_name(account_info.getAccount_name());


			if(account_info.getAccount_copy()!=null&&!account_info.getAccount_copy().equals("")) {
				accountInfo.setAccount_copy(account_info.getAccount_copy());
			}

			accountInfo.setBank_branch_id(account_info.getBank_branch_id());
			accountInfo.setBank_name(account_info.getBank_name());
			accountInfo.setAccount_number(account_info.getAccount_number());
			accountInfo.setAcId(account_info.getAcId());
			accountInfo.setAcKey(account_info.getAcKey());
			accountInfo.setBank_address(account_info.getBank_address());
            String[] address = account_info.getBank_address().split(",");
            String city = address[1];
			Object obj = baseBeanDao.getObjectBySqlAndParams("select cityID from city where city = ?",new Object[]{city});

			if(obj==null){
				obj = baseBeanDao.getObjectBySqlAndParams("select provinceid from province where province = ?",new Object[]{city});
			}
			accountInfo.setBank_address_code(obj!=null?obj.toString():"");
			beans.add(accountInfo);
			app.setAccount_info(accountInfo);
			beans.add(app);

		}else if("contact_info".equals(mode)){
              //超级管理员
			ContactInfo contactInfo = app.getContact_info();
			if(contactInfo==null){
                 contactInfo = new ContactInfo();
			}
			contactInfo.setContact_type(contact_info.getContact_type());
			contactInfo.setContact_name(contact_info.getContact_name());
			contactInfo.setContact_id_card_number(contact_info.getContact_id_card_number());
			contactInfo.setMobile_phone(contact_info.getMobile_phone());
			contactInfo.setContact_email(contact_info.getContact_email());
			contactInfo.setCoId(contact_info.getCoId());
			contactInfo.setCoKey(contact_info.getCoKey());
			beans.add(contactInfo);
			app.setContact_info(contactInfo);
			beans.add(app);


		}else if("business_addition".equals(mode)){
			//补充
			if(applyParam.getQualifications()!=null&&!applyParam.getQualifications().equals("")) {
				app.setQualifications(applyParam.getQualifications());
			}
			if(applyParam.getBusiness_addition_pics()!=null&&!applyParam.getBusiness_addition_pics().equals("")) {
				app.setBusiness_addition_pics(applyParam.getBusiness_addition_pics());
			}

			app.setBusiness_addition_desc(applyParam.getBusiness_addition_desc());
			beans.add(app);

		}


		baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans,null,null);

        return app;
	}

	@Transactional
	public void saveInfo(BusinessLicenseInfo business_license_info,OrganizationCertInfo organization_cert_info,IdCardInfo id_card_info,AccountInfo account_info,ContactInfo contact_info,SalesSceneInfo sales_scene_info){

		if(business_license_info!=null) {
			    business_license_info.setBusiness_time(business_license_info.getBusiness_time().replace("a","\\"));
				baseBeanDao.update(business_license_info);
		}
		if(organization_cert_info!=null&&business_license_info.getBusiness_license_number().length()==15) {
			    organization_cert_info.setOrganization_time(organization_cert_info.getOrganization_time().replace("a","\\"));
				baseBeanDao.update(organization_cert_info);

		}
		if(id_card_info!=null) {
				baseBeanDao.update(id_card_info);
		}
		if(account_info!=null) {
				baseBeanDao.update(account_info);
		}
		if(contact_info!=null) {
				baseBeanDao.update(contact_info);
		}
		if(contact_info!=null) {
			baseBeanDao.update(contact_info);
		}
		//店铺信息已经放到认领的时候添加了，故此处注释掉，由后续证件认领逻辑修改
		/*if(sales_scene_info!=null) {
			baseBeanDao.update(sales_scene_info);
		}*/
	}

	/**'
	 * 先保存
	 * @param business_license_info
	 * @param organization_cert_info
	 * @param id_card_info
	 * @param account_info
	 * @param contact
	 * @param sales_scene_info
	 * @return
	 */
	public Map<String,Object> getInfo(BusinessLicenseInfo business_license_info,OrganizationCertInfo organization_cert_info,IdCardInfo id_card_info,AccountInfo account_info,ContactInfo contact,SalesSceneInfo sales_scene_info){
		Map<String,Object> map = new HashMap<String,Object>();
		if(sales_scene_info!=null&&sales_scene_info.getSsId()!=null&&!sales_scene_info.getSsId().equals("")) {
			SalesSceneInfo salesSceneInfo = (SalesSceneInfo) baseBeanDao.getBeanByHqlAndParams("from SalesSceneInfo where ssId = ?", new Object[]{sales_scene_info.getSsId()});
			map.put("sales_scene_info", salesSceneInfo);
		}else{
			map.put("sales_scene_info", null);
		}
		if(business_license_info!=null&&business_license_info.getBliID()!=null&&!business_license_info.getBliID().equals("")) {
			BusinessLicenseInfo businessLicenseInfo = (BusinessLicenseInfo) baseBeanDao.getBeanByHqlAndParams("from BusinessLicenseInfo where bliID = ?", new Object[]{business_license_info.getBliID()});
			map.put("business_license_info", businessLicenseInfo);
		}else{
			map.put("business_license_info", null);
		}

		if(organization_cert_info!=null&&organization_cert_info.getOciID()!=null&&!organization_cert_info.getOciID().equals("")) {
			OrganizationCertInfo organizationCertInfo = (OrganizationCertInfo) baseBeanDao.getBeanByHqlAndParams("from OrganizationCertInfo where ociID = ?", new Object[]{organization_cert_info.getOciID()});
			map.put("organization_cert_info", organizationCertInfo);
		}else{
			map.put("organization_cert_info", null);
		}

		if(id_card_info!=null&&id_card_info.getCardId()!=null&&!id_card_info.getCardId().equals("")) {
			IdCardInfo idCardInfo = (IdCardInfo) baseBeanDao.getBeanByHqlAndParams("from IdCardInfo where cardId = ?", new Object[]{id_card_info.getCardId()});
			map.put("id_card_info", idCardInfo);
		}else{
			map.put("id_card_info", null);
		}

		if(account_info!=null&&account_info.getAcId()!=null&&!account_info.getAcId().equals("")) {
			AccountInfo accountInfo = (AccountInfo) baseBeanDao.getBeanByHqlAndParams("from AccountInfo where acId = ?", new Object[]{account_info.getAcId()});
			map.put("account_info", accountInfo);
		}else{
			map.put("account_info", null);
		}

		if(contact!=null&&contact.getCoId()!=null&&!contact.getCoId().equals("")) {
			ContactInfo contactInfo = (ContactInfo) baseBeanDao.getBeanByHqlAndParams("from ContactInfo where coId = ?", new Object[]{contact.getCoId()});
			map.put("contact", contactInfo);
		}else{
			map.put("contact", null);
		}

		return map;
	}

	/**
	 *
	 * 获取银行类型
	 * @return
	 */
	public List<BaseBean> getBackTypeList(){
            String hql = "from Scode where codePID = ?";
            List<BaseBean> codelist = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{Constant.bank_type});

            return codelist;
	}


	/**
	 *
	 * 获取认证类型
	 * @param companyID
	 * @return
	 */
	public ApplyParam getApplyInfo(String companyID){
          String hql = "from ApplyParam a where a.applyID = (select c.applyID from ContactCompany c where c.ccompanyID = (select cc.ccompanyId from CcomCom cc where cc.comanyId = ?))";
	     	ApplyParam applyParam = (ApplyParam)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{companyID});


                 return applyParam;
	}


	/**
	 *
	 *店铺信息
	 * @param companyID
	 * @return
	 */
	public Map<String,Object> getApplydp(String companyID){
		Map<String,Object> map = new HashMap<String,Object>();
		String hql = "from ApplyParam a where a.applyID = (select c.applyID from ContactCompany c where c.ccompanyID = (select cc.ccompanyId from CcomCom cc where cc.comanyId = ?))";
		ApplyParam applyParam = (ApplyParam)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{companyID});

		map.put("applyParam",applyParam);

        String hqls = "from ContactCompany c where c.ccompanyID = (select cc.ccompanyId from CcomCom cc where cc.comanyId = ?)";
		ContactCompany  contactCompany = (ContactCompany)baseBeanDao.getBeanByHqlAndParams(hqls,new Object[]{companyID});
		map.put("contactCompany",contactCompany);

		return map;
	}


	/**
	 *
	 *  申请
	 * @return
	 */
	public ApplyParam getApply(String out_request_no){
		ApplyParam applyParam = (ApplyParam)baseBeanDao.getBeanByHqlAndParams("from ApplyParam where out_request_no = ?",new Object[]{out_request_no});

		return applyParam;

	}

	/**
	 *
	 * 提交审核
	 * @param staffID
	 * @param companyID
	 * @param contactCompany
	 * @return
	 */
	@Transactional
	public ContactCompany submitAudit(String staffID,String companyID,ContactCompany contactCompany){

		String hql = "from AuditRecord where thirdId = ? and module = ?";
		AuditRecord ar = (AuditRecord)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{contactCompany.getCcompanyID(),"公司认证"});
		if(ar==null) {
			//提交审核
			ar = new AuditRecord();
			ar.setAuid(serverService.getServerID("au"));
			ar.setBatchNum(serverService.getServerID("bat"));
			ar.setAuditID("cstaff20110712KAX2RHUQZI0000025385");
			ar.setAuditName("孟竹");
			ar.setAuditOrgID("organization20100909zijmxn7qze0000001402");
			ar.setAuditOrgName("教务处");
			ar.setAuditComID("company201009046vxdyzy4wg0000000025");
			ar.setAuditComName("北京天太世统科技有限公司");
			ar.setPosition("开发工程师");
			ar.setSorts("1");
			//详情链接
			String viewUrl = "ea/qrshare/ea_perfectionMessage.jspa?caccount.staffID=" + staffID + "&caccount.companyID=" + companyID + "&auditSkip=01" + "&auditRecord.auid=" + ar.getAuid() + "&auditRecord.batchNum=" + ar.getBatchNum();
			ar.setViewUrl(viewUrl);
			ar.setModule("公司认证");
			ar.setThirdId(contactCompany.getCcompanyID());

		}
		if(!"01".equals(ar.getState())) {
			ar.setStartID(staffID);
			Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{staffID});
			ar.setStartName(staff.getStaffName());
			ar.setSubmitDate(new Date());
			ar.setState("01");
			contactCompany.setAuthState("01");

			baseBeanDao.update(contactCompany);
			baseBeanDao.update(ar);
			pushMessage("15210904250","有商家提交认证信息请及时处理");
		}
		return contactCompany;

	}

	/**
	 * 发短息推送
	 */
	private void pushMessage(String tel,String content) {
		try {

			msage.setMobiles(tel);
			msage.setMessage(content);
			msage.sendMsg("【微分金平台】");
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		//保存账号
		List<String> slist = new ArrayList<String>();//极光推送设备号
		slist = new ArrayList<String>();
		slist.add(tel);
		//极光推送
		JushMain.sendjiguangMessage(content, "会员", "homepage", "member", slist);
	}


	/**
	 * 获取往来单位
	 * @param companyID
	 * @return
	 */
	public ContactCompany getCCompany(String companyID){

        String hql  ="from ContactCompany c where c.ccompanyID = (select m.ccompanyId from CcomCom  m where m.comanyId = ?)";
		ContactCompany cc = (ContactCompany)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{companyID});
		return  cc;

	}

	/**
	 *
	 * 获取认证结果
	 * @param ccompanyID
	 * @return
	 */
	public AuditRecord queryAudit(String ccompanyID){
        String hql = "from AuditRecord where thirdId = ? and module = ? order by submitDate desc";
		List<BaseBean> list= baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{ccompanyID,"公司认证"});
		AuditRecord auditRecord = new AuditRecord();
		if(list.size()>0){
			auditRecord = (AuditRecord)list.get(0);
		}
		return auditRecord;
	}
}
