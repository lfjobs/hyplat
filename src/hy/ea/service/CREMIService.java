package hy.ea.service;

import hy.ea.bo.CLogBook;
import hy.ea.bo.CMB;
import hy.ea.bo.CMI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public interface CREMIService {
	
	/**
	 * 通过eaID、roleID取得这个eaID下所有的Menu，并将给此Menu分配的BO的Interface取出，如果某个Interface已经被分配给此role，则置interfaceStatus为98
	 * @param companyID
	 * @param eaID
	 * @param roleID
	 * @return
	 */
	List<CMB> getCMIListByEaIDAndRoleID(String companyID,String eaID,String roleID);
	
	/**
	 * 重载getCMIListByEaIDAndRoleID方法来配置  多条件查询
	 * 通过eaID、roleID取得这个eaID下所有的Menu，并将给此Menu分配的BO的Interface取出，如果某个Interface已经被分配给此role，则置interfaceStatus为98
	 * @param companyID
	 * @param eaID
	 * @param roleID
	 * @return
	 */
	List<CMB> getCMIListByEaIDAndRoleID(String companyID,String eaID,String roleID,Map<String , Object>  maps);
	
	/**
	 * 通过roleID、eaID取得此角色可以访问此EA的Menu，并将给此角色可以访问的Menu下的Interface取出，如果是入口点，则置标志为02，用于登录
	 * @param companyID
	 * @param roleID
	 * @param eaID
	 * @return
	 */
	List<CMI> getCMIListByRoleIDAndEaIDForLogin(String companyID,String roleID,String eaID);
	
	/**
	 * 保存CEMI，权限分配信息
	 * @param companyID
	 * @param roleID
	 * @param eaID
	 * @param mis
	 * @param logBook
	 */
	void saveCREMI(String companyID,String roleID,String eaID,String []mis,CLogBook logBook);
	
	/**
	 * 保存多个角色CEMI，权限分配信息重载saveCREMI方法
	 * @param companyID
	 * @param roleID
	 * @param eaID
	 * @param mis
	 * @param logBook
	 */
	void saveCREMI(String companyID,String[] roleIDS,String eaID,String []mis,String []misno,CLogBook[] logBooks);
	
	/**
	 * 通过roleID取得分配此角色的权限信息
	 * HashMap中Key是interfaceUrl中最后的Struts2中配置的Action Name
	 * HashMap中的Value是roleID
	 * @param companyID
	 * @param roleID
	 * @return
	 */
	HashMap<String,String> getCIRMapByRoleID(String companyID,String roleID);
}
