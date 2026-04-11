package com.tiantai.webservice.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List; 
  
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.archives.DtArchivesCheckvouchs;
import hy.ea.service.CompanyService;  
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.tiantai.webservice.ILogin;
import com.tiantai.webservice.bo.CheckVouchs;

@WebService(targetNamespace = "http://service.impf2010.com", endpointInterface = "com.tiantai.webservice.ILogin", name = "Login", serviceName = "Login")
@Component(value="webservice.Login") 
public class LoginImpl implements ILogin {
	private static Logger log = LoggerFactory.getLogger(LoginImpl.class);

	/**
	 * 用户登录，返回当前登录用户信息
	 */
	@WebResult(name = "return", targetNamespace = "http://service.impf2010.com")
	public String getLoginUser(
			@WebParam(name = "userName", targetNamespace = "http://service.impf2010.com") String userName,
			@WebParam(name = "loginPwd", targetNamespace = "http://service.impf2010.com") String loginPwd,
			@WebParam(name = "companyIdentity", targetNamespace = "http://service.impf2010.com") String companyIdentity) {

		if (null == userName || null == loginPwd || null == companyIdentity)
			return null;
		String pwd = Utilities.MD5(loginPwd);
		String result = null;
		StringBuilder buffer = new StringBuilder();
		buffer.append(" select account.companyid, cos.organizationid,account.accountemail,");
		buffer.append(" account.accountpassword,staff.staffname,staff.staffid from dtcaccount account");
		buffer.append(" join dtcos cos on account.staffId = cos.staffid");
		buffer.append(" join dt_hr_staff staff on staff.staffid =cos.staffid");
		buffer.append(" join dtcompany company on cos.companyid = company.companyid");
		buffer.append(" where company.companyidentifier = ? and cos.status = '01' and account.accountemail = ? and account.accountpassword=? and account.accountstatus = ? order by account.accountemail");

		try {
			Object obj = baseService.getObjectBySqlAndParams(buffer.toString(),
					new Object[] { companyIdentity, userName, pwd, "00" });
			if (null == obj)
				result = "result:用户名或密码错误";
			else
				result = JSON.toJSONString(obj);
		} catch (Exception e) {
			logger.error("操作异常", e);
			log.error(e.getMessage());
			result = "result:-500";
		}
		return result;
	}

	/**
	 * 档案分类信息
	 */
	@WebResult(name = "return", targetNamespace = "http://service.impf2010.com")
	public String getArchivesCatalog(
			@WebParam(name = "companyId", targetNamespace = "http://service.impf2010.com") String companyId) {
		if (null == companyId)
			return null;

		StringBuilder buffer = new StringBuilder();
		buffer.append("select bean.archiveid,bean.name  from DtArchivesCataloguearchives bean where bean.comanyid=?");

	    return getString(buffer.toString(),true,companyId);
	}

	

	/**
	 * 登录用户公司信息
	 */
	@WebResult(name = "return", targetNamespace = "http://service.impf2010.com")
	public String getCompany(
			@WebParam(name = "companyIdentity", targetNamespace = "http://service.impf2010.com") String companyIdentity) {
		if (null == companyIdentity)
			return null;
		
		try{
			 return JSON.toJSONString(companyServer.getCompanyByIdentifier(companyIdentity));
		}catch(Exception e){
			log.error(e.getMessage());
			return "result:-500";
		}
	}

	/**
	 * 登录用户公司信息
	 */
	@WebResult(name = "return", targetNamespace = "http://service.impf2010.com")
	public String getCompanyById(
			@WebParam(name = "companyId", targetNamespace = "http://service.impf2010.com") String companyId) {
 
		if (null == companyId)
			return null;

		try {
			return JSON.toJSONString(companyServer.getCompanyByCompanyID(companyId));
		} catch (Exception e) {
			log.error(e.getMessage());
			return "result:-500";			
		}
	}

	/**
	 * 档案位置
	 */
	@WebResult(name = "return", targetNamespace = "http://service.impf2010.com")
	public String getLocationByUser(
			@WebParam(name = "staffId", targetNamespace = "http://service.impf2010.com") String staffId) {
		if (null == staffId)
			return null;
		
		StringBuilder buffer = new StringBuilder();
		buffer.append("select bean.locationid,bean.locationname from dt_archives_inventorylocation bean where bean.userid=?");
		logger.info("调试信息");
		return getString(buffer.toString(),false,staffId);

	}

	/**
	 * 登录用户公司部门信息
	 */
	@WebResult(name = "return", targetNamespace = "http://service.impf2010.com")
	public String getOrg(
			@WebParam(name = "companyId", targetNamespace = "http://service.impf2010.com") String companyId) {
		if (null == companyId)
			return null;
		StringBuilder buffer = new StringBuilder();
		buffer.append("select bean.organizationID,bean.organizationName,bean.organizationPID from COrganization bean where bean.companyID = ?");
         
		return getString(buffer.toString(),true,companyId);
	}

	/**
	 * 登录用户公司某部门在职员工
	 */ 
	@WebResult(name = "return", targetNamespace = "http://service.impf2010.com")
	public String getStaffsByOrganizationId(
			@WebParam(name = "orgId", targetNamespace = "http://service.impf2010.com") String orgId) {

		if (null == orgId)
			return null;

		StringBuilder buffer = new StringBuilder();

		buffer.append("select staff.staffid,staff.staffName from dtcorganization org  join dtcos cos on cos.organizationid = org.organizationid");
		buffer.append(" join dt_hr_staff staff on staff.staffid = cos.staffid where org.organizationid =?");

		return getString(buffer.toString(),false,orgId);
	}
	
	/**
	 * 档案盘点
	 */
	@WebResult(name = "return", targetNamespace = "http://service.impf2010.com")
	public String getChipsIdByCondition(
			@WebParam(name = "checkVouchs", targetNamespace = "http://service.impf2010.com") CheckVouchs checkVouchs) {
		
		if(null ==checkVouchs){
			return null;
		}
		
		checkVouchs.getChecks().setCheckid(serverService.getServerID("checkVouchsId"));
        try{
        	
    		String result = checkVouchs(checkVouchs);
    		DtArchivesCheckvouchs checks = checkVouchs.getChecks(); 
    		if (StringUtils.equals("0", result)) {
    			checks.setResults("盘点结果:盘点完全符合库存");
    		} else {
    			checks.setResults("盘点结果:" + result);
    		}
    		checks.setChecktime(new Date());
    		baseService.save(checks);
        }catch(Exception e){
        	log.error(e.getMessage());
        	return e.getMessage();
        }
        
		return null;
	}
	
	/**
	 * 根据芯片号获取档案信息
	 */
	@SuppressWarnings("null")
	@WebResult(name = "return", targetNamespace = "http://service.impf2010.com")
	public String getArchives(
			@WebParam(name = "chip", targetNamespace = "http://service.impf2010.com") String chip,
			@WebParam(name = "barCode", targetNamespace = "http://service.impf2010.com") String barCode) {
		if(null == chip && barCode == null){
			return null;
		}

        StringBuilder buffer = new StringBuilder();
		buffer.append("select c.name, a.name,a.chipid,a.barcode from dt_archives_archives a");
		buffer.append(" join dt_archives_cataloguearchives c");
		buffer.append(" on c.archivekey = a.category");
		buffer.append(" where 1=1 ");
		
		List<String> list = new ArrayList<String>();
		
		if(null != chip || chip.length() > 0){
			buffer.append(" and  a.chipid = ? ");
			list.add(chip);
		}
		
		if(null != barCode && barCode.length() > 0){
			buffer.append(" and a.barcode = ? ");
			list.add(barCode);
		}
		
		return this.getString(buffer.toString(), false, list.toArray());
	}
	

	/**
	 * 获取档案盒及档案盒中档案信息
	 */
	@Override
	@WebResult(name = "return", targetNamespace = "http://service.impf2010.com")
	public String getChivesInfo(@WebParam(name = "chip",targetNamespace="http://service.impf2010.com")String chip) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("select c.recordboxcode,c.recordboxname,s.staffname,");
		//TODO wmsys.wm_concat() Oracle10g提供的新函数 ，可以完成行转列后列以字符串的形式进行拼接
		buffer.append("wmsys.wm_concat(p.codename) as codename ");
		buffer.append("from dt_hr_staff_perarchive c ");
		buffer.append("join dt_hr_staff_pcode p  on p.archivebid = c.archivebid ");
		buffer.append("join dt_hr_staff s on c.dutyofficer = s.staffid ");
		buffer.append("where c.chipid = ? ");
		buffer.append("group by c.recordboxcode,c.recordboxname,s.staffname ");
		
		return this.getString(buffer.toString(), false, chip);
	}

	/**
	 * 获取人事档案
	 * @param chipOrBarCode　芯片号或条码
	 * @return
	 */
	@Override
	@WebResult(name="return",targetNamespace="http://service.impf2010.com")  
	public String getHrArchives(@WebParam(name = "chipOrBarCode",targetNamespace="http://service.impf2010.com")String chipOrBarCode) {
		StringBuilder buff = new StringBuilder();
		buff.append(" from Staff as bean where bean.recordCode = ? or bean.chipid = ?");
		
		Staff base = (Staff)baseService.getBeanByHqlAndParams(buff.toString(), new String[]{chipOrBarCode ,chipOrBarCode});
		base.setStaffIdentityCard(" ");
		base.setStaffAddress(" ");
		return JSON.toJSONString(base);
	}

	/**
	 * 生成盘点结果
	 * @param archives
	 * @return
	 */ 
	@SuppressWarnings("rawtypes")
	private String checkVouchs(CheckVouchs archives){
		
		StringBuilder buff = new StringBuilder();

		buff.append("select ar.archivesid,ar.name,ar.chipid from  dt_archives_archives ar");
		buff.append(" join dt_archives_archiveshistory his on ar.archiveskey = his.archivesid ");
		buff.append(" join dt_archives_inventorylocation loca on loca.locationkey = his.location ");
		buff.append(" join dt_archives_cataloguearchives cate on cate.archivekey = ar.category ");
        buff.append(" where loca.locationid = ? and his.state = 1 ");
		buff.append(" and ar.companyid = ? and cate.archiveid = ? and his.inuser = ? "); 
		
		List list = baseService.getListBeanBySqlAndParams(buff.toString(), new Object[]{archives.getChecks().getChecklocation(),archives.getChecks().getCompanyid(),archives.getChecks().getCategoryid(),archives.getChecks().getCheckuser()});
		
		if(null == list || list.size() < 1){
			return "该位置无档案信息。";
		}
		/**
		 * 数据格式    id,name,chipid
		 * [
		 *  ["archiveid20121207G3XUUNVQXC0000000048","娄飞档案","E2009366660D01351080A83D"],
		 *  ["archiveid20121207G3XUUNVQXC0000000054","范宏伟档案","E2009366660D02510760CA81"],
		 *  ["archiveid20121207G3XUUNVQXC0000000051","张灿档案","E2009366660D01361140A3FF"]
		 * ]
		 */
		String jsonStr = JSON.toJSONString(list); 
		String[] arrStr = jsonStr.replace("\"", "").replace("]]", "").replace("[", "").split("]"); 
		List<String> listStr = arr2List(arrStr);
		for(String item : listStr){ 
			String[] subArr = item.split(",");
			if(subArr == null || subArr.length < 1)
				break;
			
			for(String chip : archives.getChips()){
				if(StringUtils.equals(chip,subArr[2])){  
					listStr.remove(item);
				}
			}
		}
		
		String result =  "";
		if(null == listStr || listStr.size() < 1){
			result = "0";
		}else{
			StringBuilder buffer = new StringBuilder();
			for(String item : listStr){ 
				String[] subArr = item.split(",");
				if(subArr == null || subArr.length < 1)
					continue;
				
				buffer.append(subArr[0]);
				buffer.append(":");
				buffer.append(subArr[1]);
				buffer.append(",");
			}
			buffer.append("丢失。");
			result = buffer.toString();
			buffer = null;
		}
		logger.info("调试信息");
		return result;
	}
	/**
	 * 根据 SQL或HQL语句查询返回JSON格式数据
	 * @param hql
	 *            Hql语句或sql语句
	 * @param isHql
	 *            <b>@param hql</b>是否为HQL语句
	 * @param params
	 *            参数列表
	 * @return
	 */
	@SuppressWarnings({ "unchecked"})
	private String getString(String hql, boolean isHql, Object... params) {
		
		List<BaseBean> list = null;
		
		if (isHql) {
			list = baseService.getListBeanByHqlAndParams(hql, params);
		} else {
			list = baseService.getListBeanBySqlAndParams(hql, params);
		}

		if (null == list) {
			return "result:没有数据";
		}
		
		StringBuilder buffer = new StringBuilder();
		
		for (Object obj : list) {
			buffer.append(JSON.toJSONString(obj)).append(",");
		} 

		return buffer.toString().replace(",,", "");
	}
	 private List<String> arr2List(String[] str){
		 if(str == null || str.length < 1){
			 return null;
		 }
		 
		 List<String> list = new ArrayList<String>(); 
		 
		 for(String item : str){
			 list.add(item);
		 }
		 return list;
	 }
	@Autowired
	private BaseBeanService baseService;
	
	@Autowired
	private CompanyService companyServer;
	
	@Autowired
	private ServerService serverService;
		
}
