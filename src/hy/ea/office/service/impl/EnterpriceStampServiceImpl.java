package hy.ea.office.service.impl;

import com.stamp.JzqAPI;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.EnterpriseStamp;
import hy.ea.office.service.EnterpriceStampService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EnterpriceStampServiceImpl implements EnterpriceStampService {

	private Logger logger = LoggerFactory.getLogger(EnterpriceStampServiceImpl.class);
	@Resource
	private BaseBeanDao beandao;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;


	/**
	 *
	 * 获取电子印章
	 * @param staffID
	 * @param companyID
	 * @return
	 */
	public List<BaseBean> getStampList(String staffID, String companyID,String parameter){
		List<BaseBean> list = null;
		List<Object> params =new ArrayList<Object>();
		String hql = "";
        if(companyID!=null&&!companyID.equals("")){
        	 hql = "from EnterpriseStamp where companyID = ? and gore = ? and delStatus is null";
			params.add(companyID);
			params.add("e");
        	if(parameter!=null&&!parameter.equals("")){
        		hql += " and stampName like ?";
        		params.add("%"+parameter+"%");
			}

		}else{

			hql = "from EnterpriseStamp where responsibleID = ? and gore = ? and delStatus is null and stampType = ?";
			params.add(staffID);
			params.add("e");
			params.add("个人印章");
			if(parameter!=null&&!parameter.equals("")){
				hql += " and stampName like ?";
				params.add("%"+parameter+"%");
			}

		}

		list = beandao.getListBeanByHqlAndParams(hql,params.toArray());

		return list;
	}

	/**
	 *
	 * 删除印章
	 * @param staffID
	 * @param enterpriseStampID
	 * @return
	 */
	public void deleteStamp(String staffID,String enterpriseStampID){

		String hql = "from EnterpriseStamp where enterpriseStampID = ?";
		EnterpriseStamp enterpriseStamp = (EnterpriseStamp)beandao.getBeanByHqlAndParams(hql,new Object[]{enterpriseStampID});
		if(enterpriseStamp!=null){
			enterpriseStamp.setDelStatus("99");
			enterpriseStamp.setDelDate(new Date());
			enterpriseStamp.setDelStaffID(staffID);
			beandao.update(enterpriseStamp);

		}

	}

	/**
	 *
	 * 使用状态
	 * @param staffID
	 * @param enterpriseStampID
	 * @return
	 */
	public void setUseStamp(String staffID,String enterpriseStampID){

		String hql = "from EnterpriseStamp where enterpriseStampID = ?";
		EnterpriseStamp enterpriseStamp = (EnterpriseStamp)beandao.getBeanByHqlAndParams(hql,new Object[]{enterpriseStampID});
		if(enterpriseStamp!=null){
			String useStatus = enterpriseStamp.getUseStatus();
			if(useStatus==null||useStatus.equals("")||useStatus.equals("use")){
				enterpriseStamp.setUseStatus("unuse");
			}else{
				enterpriseStamp.setUseStatus("use");
			}
			enterpriseStamp.setUseDate(new Date());
			enterpriseStamp.setUseStaffID(staffID);
			beandao.update(enterpriseStamp);

		}
	}


	/**
	 *
	 * 添加修改印章
	 * @param staffID
	 * @param companyID
	 * @param enterpriseStamp
	 */
	public void addUpdateStamp(String staffID, String companyID, EnterpriseStamp enterpriseStamp,String realpath){

		Staff staff = (Staff)beandao.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{staffID});

		if(enterpriseStamp.getEnterpriseStampID()==null||enterpriseStamp.getEnterpriseStampID().equals("")){
			enterpriseStamp.setEnterpriseStampID(serverService.getServerID("stampID"));

			if(enterpriseStamp.getResponsibleID()==null||enterpriseStamp.getResponsibleID().equals("")) {
				enterpriseStamp.setResponsibleID(staffID);
				if (staff != null) {
					enterpriseStamp.setResponsibleName(staff.getStaffName());
				}
			}

			enterpriseStamp.setCreateTime(new Date());
			enterpriseStamp.setGore("e");
			enterpriseStamp.setAuditCompanyID("company201009046vxdyzy4wg0000000025");
			enterpriseStamp.setAuditStatus("01");//提交审核中
			enterpriseStamp.setUseStatus("use");


			if(companyID!=null&&!companyID.equals("")){
				enterpriseStamp.setCompanyID(companyID);
				//说明是企业的签章

				String sql = "select t.emailOrMobile from dt_sft_applyresult t ,dt_sft_applyparam p,Dtcontactcompany c,dt_ccom_com m where t.out_request_no= p.out_request_no and p.applyid = c.applyid and c.ccompanyid = m.ccompany_id and m.compnay_id= ?";
				Object obj = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{companyID});
				String signId = JzqAPI.uploadEntSign(enterpriseStamp.getStampName(),obj.toString(),realpath+enterpriseStamp.getScanningAccessories());
				enterpriseStamp.setSignId(signId);
			}else{
				//说明是个人上传印章

				Staff seal = (Staff)beandao.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{enterpriseStamp.getResponsibleID()});

		        JzqAPI.uploadPersSign(seal.getStaffIdentityCard(),realpath+enterpriseStamp.getScanningAccessories());

			}
			beandao.save(enterpriseStamp);
		}else{
            String hql = "from EnterpriseStamp where enterpriseStampID = ?";
			EnterpriseStamp stamp = (EnterpriseStamp)beandao.getBeanByHqlAndParams(hql,new Object[]{enterpriseStamp.getEnterpriseStampID()});
			stamp.setStampName(enterpriseStamp.getStampName());
			stamp.setStampType(enterpriseStamp.getStampType());
			if(enterpriseStamp.getScanningAccessories()!=null&&!enterpriseStamp.getScanningAccessories().equals("")) {
				stamp.setScanningAccessories(enterpriseStamp.getScanningAccessories());
			}
			if(enterpriseStamp.getResponsibleID()==null||enterpriseStamp.getResponsibleID().equals("")){
				if (staff != null) {
					stamp.setResponsibleName(staff.getStaffName());
				}
			}
			if(companyID!=null&&!companyID.equals("")){
				stamp.setCompanyID(companyID);
				//说明是企业的签章

				String sql = "select t.emailOrMobile from dt_sft_applyresult t ,dt_sft_applyparam p,Dtcontactcompany c,dt_ccom_com m where t.out_request_no= p.out_request_no and p.applyid = c.applyid and c.ccompanyid = m.ccompany_id and m.compnay_id= ?";
				Object obj = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{companyID});
				String signId = JzqAPI.uploadEntSign(enterpriseStamp.getStampName(),obj.toString(),realpath+enterpriseStamp.getScanningAccessories());
				stamp.setSignId(signId);
			}else{
				//说明是个人上传印章

				Staff seal = (Staff)beandao.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{enterpriseStamp.getResponsibleID()});

				JzqAPI.uploadPersSign(seal.getStaffIdentityCard(),realpath+enterpriseStamp.getScanningAccessories());

			}
			stamp.setAuditStatus("01");//提交审核中
            beandao.update(stamp);
		}



	}


	/**
	 *
	 * 获取信息
	 * @param enterpriseStampID
	 * @return
	 */
	public EnterpriseStamp getEditInfo(String enterpriseStampID){

        String hql = "from EnterpriseStamp where enterpriseStampID = ?";
		EnterpriseStamp enterpriseStamp = (EnterpriseStamp)beandao.getBeanByHqlAndParams(hql,new Object[]{enterpriseStampID});

		return enterpriseStamp;
	}

	/**
	 *
	 * 获取审核印章
	 * * @param pageSize
	 * @param pageNumber
	 * @param parameter
	 * @param companyID
	 * @param auditStatus
	 * @return
	 */
	public PageForm getAuditStampList(int pageSize, int pageNumber, String parameter, String companyID, String auditStatus){
       List<Object> params = new ArrayList<Object>();
       String hql = "from EnterpriseStamp where auditCompanyID = ? and useStatus = ?";

       PageForm pageForm = null;

       if("01".equals(auditStatus)){
		   hql+=" and auditStatus = ?";
		   params.add(companyID);
		   params.add("use");
		   params.add("01");


	   }else{
		   hql+=" and (auditStatus = ? or auditStatus = ?)";
		   params.add(companyID);
		   params.add("use");
		   params.add("02");
		   params.add("03");

	   }


		if(parameter!=null&&!parameter.equals("")){
			hql+=" and stampName like ?";
			params.add("%"+parameter+"%");
		}


		pageForm =  baseBeanService.getPageForm(pageSize,pageNumber,hql,params.toArray());

	         return pageForm;

	}
	/**
	 *
	 * 审核
	 * @param enterpriseStampID
	 * @param auditStatus
	 * @param rejectReason
	 * @param staffID
	 * @return
	 */
	public void auditStamp(String enterpriseStampID,String auditStatus,String rejectReason,String staffID){
         String hql = "from EnterpriseStamp where enterpriseStampID = ?";
		EnterpriseStamp enterpriseStamp = (EnterpriseStamp) beandao.getBeanByHqlAndParams(hql,new Object[]{enterpriseStampID});
		if(enterpriseStampID!=null){
			enterpriseStamp.setAuditStatus(auditStatus);
			enterpriseStamp.setAuditID(staffID);
			enterpriseStamp.setAuditDate(new Date());
			enterpriseStamp.setRejectReason(rejectReason);
			beandao.update(enterpriseStamp);
		}



	}

}