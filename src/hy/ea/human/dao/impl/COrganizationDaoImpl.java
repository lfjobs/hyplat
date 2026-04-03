package hy.ea.human.dao.impl;

import hy.ea.bo.human.COrganization;
import hy.ea.human.dao.COrganizationDao;
import hy.plat.bo.BaseBean;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class COrganizationDaoImpl implements COrganizationDao{
	
	@Resource
	private SessionFactory sessionFactory;

	@Override
	public void updateOrganizationByID(String organizationID,String unitsID){
		Query  query=sessionFactory.getCurrentSession().createQuery(" update  COrganization set Status = '98' where organizationID = ? and companyID = ? ");
		query.setString(0, organizationID);
		query.setString(1, unitsID);
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<COrganization> getOrganizationList(String organizationPID,String unitsID){
		Query query = sessionFactory.getCurrentSession().createQuery(" from COrganization where organizationPID = ? and companyID = ? and Status = '00' order by organizationNumber ");
		query.setString(0, organizationPID);
		query.setString(1, unitsID);
		return  query.list(); 
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<COrganization> findOrgByAcc(String accId){
		StringBuffer hqlstr=new StringBuffer();
		hqlstr.append("select c from COrganization c,COA coa");
		hqlstr.append(" where coa.accountID = ?");
		hqlstr.append(" and coa.organizationID=c.organizationID and c.companyID=coa.companyID");
		hqlstr.append(" order by c.organizationNumber");
		Query query = sessionFactory.getCurrentSession().createQuery(hqlstr.toString());
		query.setString(0, accId);
		return  query.list(); 
	}
	@Override
	public COrganization getOrganization(String organizationID, String companyID) {
		Query query = sessionFactory.getCurrentSession().createQuery(" from COrganization where organizationID = ? and companyID = ? and Status = '00' order by organizationNumber");
		query.setString(0, organizationID);
		query.setString(1, companyID);
		return  (COrganization) query.uniqueResult(); 
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<COrganization> getOrganizationListForTree(
			String organizationPID, String CompanyID, String RoleID) {
		Query query = sessionFactory.getCurrentSession().createQuery(" from COrganization where companyID = ? and organizationPID = ?  and Status = '00' order by organizationNumber ");
		query.setString(0, CompanyID);
		query.setString(1, organizationPID);
		return  query.list(); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<COrganization> getCOrganizationListForOTree(
			String organizationPID, String RoleID) {
		Query query = sessionFactory.getCurrentSession().createQuery(" from COrganization where  organizationPID = ?  and Status = '00' order by organizationNumber ");
		query.setString(0, organizationPID);
		return  query.list(); 
	}

	@Override
	public COrganization getCoranizationForOTree(String organizationID) {
		Query query = sessionFactory.getCurrentSession().createQuery(" from COrganization where organizationID = ? and Status = '00' order by organizationNumber");
		query.setString(0, organizationID);
		return  (COrganization) query.uniqueResult(); 
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<COrganization> getOrganizationList(String companyID) {
		Query query = sessionFactory.getCurrentSession().createQuery(" from  COrganization where  companyID= ? and Status = '00' order by organizationNumber");
		query.setString(0, companyID);
		return query.list(); 
	}

    @Override
    public List<COrganization> getOrganizationListAll(String companyID) {
        Query query = sessionFactory.getCurrentSession().createQuery(" from  COrganization where  companyID= ? and Status = '00' order by organizationLevel,organizationNumber");
        //Query query = sessionFactory.getCurrentSession().createQuery(" from  COrganization where  companyID= ? and Status = '00' order by organizationpid desc,organizationNumber");
        query.setString(0, companyID);
        return query.list();
    }
	@SuppressWarnings("unchecked")
	@Override
	public List<BaseBean> getThenFiveDepartments(String companyId){
		Query query = sessionFactory.getCurrentSession().createQuery("from COrganization" +
				" where companyID =? and organizationPID =?" +
				" and (organizationUrl = ? or organizationUrl = ? or" +
				" organizationUrl = ? or organizationUrl = ? or" +
				" organizationUrl = ?) and Status=? order by organizationNumber");
		query.setString(0, companyId);
		query.setString(1, companyId);
		query.setString(2, "/ea/office/ea_toSersonnelSystem");
		query.setString(3,"/ea/office/ea_toOffice");
		query.setString(4, "/ea/office/ea_toFinancial");
		query.setString(5, "/ea/office/ea_toTeachingAffairsDepartment");
		query.setString(6, "/ea/office/ea_toJobDepartment");
		query.setString(7,"00");
		return query.list();
	}
}
