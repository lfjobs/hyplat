package hy.ea.dao.impl;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.dao.CAccountDao;
import hy.ea.dao.CCodeDao;
import hy.ea.dao.CEADao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class CAccountDaoImpl implements CAccountDao{
	
	@Resource
	private SessionFactory sessionFactory;
	@Resource
	private CEADao ceasdao;
	@Resource
	private CCodeDao ccodeDao;

	@SuppressWarnings("rawtypes")
	public CAccount getAccountByCompanyIDAndAccountEmail(String companyID,String accountEmail) {
		Query query = sessionFactory.getCurrentSession().createQuery(" from CAccount where companyID = ? and accountEmail = ? " );
		query.setString(0, companyID);
		query.setString(1, accountEmail);
		List list = query.list(); 
		if(null != list && list.size() > 0){
			return (CAccount)list.get(0);
		}
		return null;
	}

	@Override
	public void deleteCAccountByCompanyIDAndaccountID(String companyID,
			String accountID) {
		Query query = sessionFactory.getCurrentSession().createQuery(" delete CAccount where companyID = ? and accountID = ? ");
		query.setString(0,companyID).setString(1,accountID).executeUpdate();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public CAccount getCAccountByCompanyIDAndaccountID(String companyID,
			String accountID) {
		Query query = sessionFactory.getCurrentSession().createQuery(" from CAccount where companyID = ? and accountID = ? ");
		query.setString(0, companyID);
		query.setString(1, accountID);
		List list = query.list(); 
		if(null != list && list.size() > 0){
			return (CAccount)list.get(0);
		}
		return null;
	}

	@Override
	public int getConutByCompanyID(String companyID) {
		Query query = sessionFactory.getCurrentSession().createQuery("select count(*) from CAccount where companyID = ?");
		query.setString(0, companyID);
		Object o = query.uniqueResult();
		return Integer.parseInt(o.toString());
	}

	@Override
	public void updateRelease(CAccount  account,CLogBook logBook) {
		ceasdao.versionUpgrade(account.getCompanyID(), account.getRoleID());
//		List<SEA> cealist =ceasdao.getListSea(account.getCompanyID());
//		if( cealist!= null){
//			for(SEA cea : cealist){
//				ceasdao.updateSEAtoCEA(account.getCompanyID(), cea, account.getRoleID());
//			}
//		}
//		Query query = sessionFactory.getCurrentSession().createQuery( " from SEA s where s.eaStatus = '00' and s.eaType != '01' and not exists (select 1 from CEA c where c.eaID = s.eaID and c.companyID = ?) order by eaSort");
//		query.setString(0, account.getCompanyID());
//		List<SEA> list = query.list(); 
//		for(SEA cea : list){
//			//System.out.println(cea.getEaID());
//			ceasdao.pushSEAtoCEA(account.getCompanyID(),cea.getEaID(),account.getRoleID());
//		}
		ccodeDao.upadateCodeToCCode(account.getCompanyID());
		sessionFactory.getCurrentSession().merge(logBook);
	}

}
