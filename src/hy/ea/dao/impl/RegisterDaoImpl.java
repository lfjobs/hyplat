package hy.ea.dao.impl;

import com.aliyuncs.ecs.model.v20140526.DescribeImageSharePermissionResponse;
import hy.ea.bo.*;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.human.*;
import hy.ea.dao.CCodeDao;
import hy.ea.dao.CompanyDao;
import hy.ea.dao.RegisterDao;
import hy.ea.finance.dao.CSubjectsDao;
import hy.ea.service.CEAService;
import hy.ea.service.DictDataService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.SMenu;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class RegisterDaoImpl implements RegisterDao {

	@Resource
	private SessionFactory sessionFactory;
	@Resource
	private CompanyDao companyDao; 
	@Resource
	private ServerService serverService;
	@Resource
	private CEAService ceaService;
	@Resource
	private CCodeDao ccodeDao;
	@Resource
	private CSubjectsDao csubjectsdao;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private BaseBeanDao baseBeanDao;
    @Resource
    private DictDataService dictDataService;

	public boolean register(Company company, CDetail companyDetail, CAccount account) {
		//保存company数据
		sessionFactory.getCurrentSession().persist(company);

		//保存company detail数据
		sessionFactory.getCurrentSession().persist(companyDetail);
		
		//创建一个管理的sa角色，并保存
		CRole role = new CRole();
		role.setRoleID(serverService.getServerID("crole"));
		role.setCompanyID(company.getCompanyID());
		role.setRoleName("管理员");
		role.setRoleDesc("管理员");
		role.setCreateDate(new Date());
		sessionFactory.getCurrentSession().persist(role);
		// 保存公司菜单
		saveCompanyMenu(company,role,account);

		//指定管理员的账号的角色的为管理员角色，并保存管理员数据
		account.setRoleID(role.getRoleID());
		sessionFactory.getCurrentSession().persist(account);
		//将系统管理EA加入
		ceaService.pushSEAtoCEA(company.getCompanyID(),"sea201003304z2f43vc4t0000000124",role.getRoleID());
		//测试，将人力资源EA加入
		ceaService.pushSEAtoCEA(company.getCompanyID(),"sea20100401z234fi83hd0000000002",role.getRoleID());
		//测试，将财务系统EA加入
		ceaService.pushSEAtoCEA(company.getCompanyID(),"sea20100701smjw4wqg340000000001",role.getRoleID());
		//办公系统载入
		ceaService.pushSEAtoCEA(company.getCompanyID(),"sea201006045de72ajn3d0000000001",role.getRoleID());
		//办公室办公载入
		ceaService.pushSEAtoCEA(company.getCompanyID(),"sea20100810e4f9tsznfy0000000001",role.getRoleID());
		//公司汇总载入
		ceaService.pushSEAtoCEA(company.getCompanyID(),"sea201009107upckhe98z0000000577",role.getRoleID());
		//教务处载入
		ceaService.pushSEAtoCEA(company.getCompanyID(),"sea201108188nzghnvyj60000000073",role.getRoleID());
		//将Code中的数据导入到CCode中
		ccodeDao.pushCodeToCCode(company.getCompanyID());
		//财务将Subjects中的数据导入到CSubjects中
		csubjectsdao.pushSubjectsToCSubjects(company.getCompanyID());
		return true;
	}
	
	public boolean register(Company company,ContactCompany contactCompany, CDetail companyDetail, CAccount account) {
		//保存company数据
		sessionFactory.getCurrentSession().persist(company);

		//保存company数据
		sessionFactory.getCurrentSession().saveOrUpdate(contactCompany);
		
		//保存company detail数据
		sessionFactory.getCurrentSession().persist(companyDetail);
		
		//创建一个管理的sa角色，并保存
		CRole role = new CRole();
		role.setRoleID(serverService.getServerID("crole"));
		role.setCompanyID(company.getCompanyID());
		role.setRoleName("管理员");
		role.setRoleDesc("管理员");
		role.setCreateDate(new Date());
		sessionFactory.getCurrentSession().persist(role);
		//保存公司菜单
		saveCompanyMenu(company,role,account);
		//指定管理员的账号的角色的为管理员角色，并保存管理员数据
		account.setRoleID(role.getRoleID());
		sessionFactory.getCurrentSession().persist(account);
		//将系统管理EA加入
		ceaService.pushSEAtoCEA(company.getCompanyID(),"sea201003304z2f43vc4t0000000124",role.getRoleID());
		//测试，将人力资源EA加入
		ceaService.pushSEAtoCEA(company.getCompanyID(),"sea20100401z234fi83hd0000000002",role.getRoleID());
		//测试，将财务系统EA加入
		ceaService.pushSEAtoCEA(company.getCompanyID(),"sea20100701smjw4wqg340000000001",role.getRoleID());
		//办公系统载入
		ceaService.pushSEAtoCEA(company.getCompanyID(),"sea201006045de72ajn3d0000000001",role.getRoleID());
		//办公室办公载入
		ceaService.pushSEAtoCEA(company.getCompanyID(),"sea20100810e4f9tsznfy0000000001",role.getRoleID());
		//公司汇总载入
		ceaService.pushSEAtoCEA(company.getCompanyID(),"sea201009107upckhe98z0000000577",role.getRoleID());
		//教务处载入
		ceaService.pushSEAtoCEA(company.getCompanyID(),"sea201108188nzghnvyj60000000073",role.getRoleID());
		
		//将Code中的数据导入到CCode中
		ccodeDao.pushCodeToCCode(company.getCompanyID());
		//财务将Subjects中的数据导入到CSubjects中
		csubjectsdao.pushSubjectsToCSubjects(company.getCompanyID());
		return true;
	}

	@Override
	public boolean isRegister(String companyIdentifier) {
		
		return null == companyDao.getCompanyByIdentifier(companyIdentifier)?false:true;
	}

	/**
	 * 保存权限
	 * @param company
	 * @param role
	 * @param account
	 */
	public void saveCompanyMenu(Company company, CRole role, CAccount account){
		String companyId = company.getCompanyID();
		StringBuffer sql = new StringBuffer(128);
		sql.append(" from MoneyEmpower where ccomType=? and status = 1");
		MoneyEmpower moneyEmpower = (MoneyEmpower) baseBeanDao.getBeanByHqlAndParams(sql.toString(), new Object[]{company.getCcomtype()});
		String empowerId = moneyEmpower.getEmpowerId();
		sql.setLength(0);
		sql.append("from MoneyEmpowerMenu where empowerId = ?");
		List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(sql.toString(), new Object[]{empowerId});
		List<BaseBean> dataList = new ArrayList<>();
		CompanyMenu companyMenu = null;
		MoneyEmpowerMenu moneyEmpowerMenu = null;
		for(BaseBean baseBean : list){
			moneyEmpowerMenu = (MoneyEmpowerMenu) baseBean;
			companyMenu = new CompanyMenu();
			companyMenu.setCompanyId(company.getCompanyID());
			companyMenu.setMenuId(moneyEmpowerMenu.getMenuId());
			dataList.add(companyMenu);
		}
		sql.setLength(0);
		sql.append("delete from CompanyMenu where companyId = ? ");
		List<Object[]> params = new ArrayList<>();
		params.add(new Object[]{company.getCompanyID()});
		baseBeanService.executeHqlsByParamsList(null, new String[]{sql.toString()}, params);
		baseBeanDao.executeSqlsByParmsList(dataList,null,null);
		//角色菜单
		String roleId = role.getRoleID();
		RoleMenu roleMenu;
		dataList = new ArrayList<>();
		for (BaseBean baseBean : list ){
			moneyEmpowerMenu = (MoneyEmpowerMenu) baseBean;
			roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setCompanyId(companyId);
			roleMenu.setMenuId(moneyEmpowerMenu.getMenuId());
			dataList.add(roleMenu);
		}
		baseBeanDao.executeSqlsByParmsList(dataList,null,null);
		// 员工角色
		StaffRole staffRole = new StaffRole();
		staffRole.setStaffId(account.getStaffID());
		staffRole.setRoleId(roleId);
		staffRole.setCompanyId(companyId);
		sessionFactory.getCurrentSession().persist(staffRole);
		//添加标准岗位
		sql.setLength(0);
		sql.append("from RoleStandard where roleId in ( select roleId from MoneyEmpowerRole where empowerId=?)");
		list = baseBeanDao.getListBeanByHqlAndParams(sql.toString(), new Object[]{empowerId});
		dataList = new ArrayList<>();
		CRole crole;
		RoleStandard roleStandard;
		for(BaseBean baseBean : list){
			roleStandard = (RoleStandard) baseBean;
			crole = new CRole();
			crole.setRoleID(roleStandard.getRoleId());
			crole.setCompanyID(companyId);
			crole.setRoleName(roleStandard.getRoleName());
			crole.setRoleDesc(roleStandard.getRoleDesc());
			crole.setCreateDate(new Date());
			dataList.add(crole);
		}
		baseBeanDao.executeSqlsByParmsList(dataList,null,null);
		sql.setLength(0);
		sql.append("from RoleStandardMenu where roleId in (select roleId from MoneyEmpowerRole where empowerId=?)")
				.append(" and menuId in (select menuId from MoneyEmpowerMenu where empowerId=?)");
		list = baseBeanDao.getListBeanByHqlAndParams(sql.toString(), new Object[]{empowerId,empowerId});
		dataList = new ArrayList<>();
		RoleStandardMenu roleStandardMenu = null;
		for (BaseBean baseBean : list ){
			roleStandardMenu = (RoleStandardMenu) baseBean;
			roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleStandardMenu.getRoleId());
			roleMenu.setCompanyId(companyId);
			roleMenu.setMenuId(roleStandardMenu.getMenuId());
			dataList.add(roleMenu);
		}
		baseBeanDao.executeSqlsByParmsList(dataList,null,null);
        DictData dictData = dictDataService.getDictDataByParam("menuOrg","01","");
        if (dictData != null) {
            sql.setLength(0);
            sql.append(" INSERT INTO dtCOrganization(ORGANIZATIONKEY,ORGANIZATIONID,COMPANYID,ORGANIZATIONNAME,")
                    .append(" ORGANIZATIONNUMBER,ORGANIZATIONPID,STATUS,ORGANIZATIONCREATEDATE,OCODE,ORGANIZATIONLEVEL)")
                    .append(" SELECT  CONCAT(MENU_KEY,'" + companyId.substring(companyId.length() - 3))
                    .append(" ')ORGANIZATIONKEY,o.MENU_ID ORGANIZATIONID,'" + companyId + "' as COMPANYID,")
                    .append(" menu_name as ORGANIZATIONNAME,SORT_NUM AS ORGANIZATIONNUMBER,nvl(menu_pid,'" + companyId + "') as ORGANIZATIONPID,")
                    .append(" '00' as status, CURRENT_TIMESTAMP as ORGANIZATIONCREATEDATE,(cast(SORT_NUM as number) + 1) as OCODE, ")
                    .append(" menu_level as ORGANIZATIONLEVEL from DT_MONEY_EMPOWER_MENU c left join DT_Menu o on c.MENU_ID = o.MENU_ID  ")
                    .append(" where o.status = 1 and empower_Id=? and menu_level < 3");
            List<Object[]> paramslist = new ArrayList<>();
            Object[] obj = {empowerId};
            paramslist.add(obj);
            baseBeanDao.executeSqlsByParmsList(null,new String[]{sql.toString()},paramslist);
        }

	}
}