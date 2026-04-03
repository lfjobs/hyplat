package hy.ea.dao.impl;

import hy.ea.bo.CCode;
import hy.ea.bo.DrivingSchool.TbSysGeography;
import hy.ea.dao.CCodeDao;
import hy.plat.bo.SCode;
import hy.plat.dao.SCodeDao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CCodeDaoImpl implements CCodeDao {

    @Resource
    private SessionFactory sessionFactory;
    @Resource
    private SCodeDao scodeDao;


    @Override
    public void pushCodeToCCode(String companyID) {
        List<SCode> scodeList = scodeDao.getCodeList();
        for (SCode scode : scodeList) {
            CCode ccode = new CCode();
            ccode.setCodeID(scode.getCodeID());
            ccode.setCodePID(scode.getCodePID());
            ccode.setCompanyID(companyID);
            ccode.setCodeValue(scode.getCodeValue());
            ccode.setCodeStatus(scode.getCodeStatus());
            ccode.setCodeDesc(scode.getCodeDesc());
            ccode.setCodeNumber(scode.getCodeNumber());

            ccode.setCodeSn(scode.getCodeSn());
            ccode.setGroupSn(scode.getGroupSn());
            ccode.setIsLeaf(scode.getIsLeaf());
            ccode.setIconPath(scode.getIconPath());
			ccode.setCodetype(scode.getCodetype());

            sessionFactory.getCurrentSession().persist(ccode);
        }
    }

    @Override
    public void deleteCCodeByID(String companyID, String codeID) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                " update  CCode set codeStatus = '98' where companyID = ? and codeID = ? ");
        query.setString(0, companyID).setString(1, codeID).executeUpdate();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public CCode getCCodeByID(String companyID, String codeID) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                " from CCode where companyID = ? and codeID = ? ");
        query.setString(0, companyID);
        query.setString(1, codeID);
        List list = query.list();
        if (null != list && list.size() > 0) {
            return (CCode) list.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CCode> getCCodeListByPID(String companyID, String codePID) {
        Query query = sessionFactory
                .getCurrentSession()
                .createQuery(
                        "from CCode where  codePID = ? and companyID = ? and ( codeStatus = '00' or codeStatus = '01')  order by codeNumber ");
        query.setString(0, codePID);
        query.setString(1, companyID);
        List<CCode> list = query.list();
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CCode> getCCodeListNewByPID(String companyID, String codePID, String codetype) {
        Query query = sessionFactory
                .getCurrentSession()
                .createQuery(
                        "from CCode where  codePID = ? and companyID = ? " +
                                "and ( codeStatus = '00' or codeStatus = '01') and codetype ='FL'  order by codeNumber ");
        query.setString(0, codePID);
        query.setString(1, companyID);
        List<CCode> list = query.list();
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override

    public List<CCode> getCCodeListByPIDs(String companyID, String[] codePID) {
        String hql = "from CCode where  (";
        for (int i = 0; i < codePID.length; i++) {
            if (i > 0) {
                hql += "or ";
            }
            hql += " codePID=? ";
        }
        hql += ")  and companyID = ? and ( codeStatus = '00' or codeStatus = '01')  order by codeNumber ";
        Query query = sessionFactory
                .getCurrentSession()
                .createQuery(hql);
        int c = 1;
        for (int i = 0; i < codePID.length; i++) {
            query.setString(i, codePID[i]);
            c = i + 1;
        }
        query.setString(c, companyID);
        List<CCode> list = query.list();
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SCode> getCodeListForUpdate(String companyID) {
        Query query = sessionFactory.getCurrentSession().createQuery(" from SCode s where not exists (select 1 from CCode c where c.codeID = s.codeID and c.companyID = ?)");
        query.setString(0, companyID);
//		sessionFactory.close();
        return query.list();
    }

    @Override
    public List<TbSysGeography> getAllTbSysGeographyBygeoLevel(String geoLevel) {
        Query query = sessionFactory
                .getCurrentSession()
                .createQuery(
                        "from  TbSysGeography where geoLevel = ?");
        query.setString(0, geoLevel);
        List<TbSysGeography> list = query.list();
        return list;
    }


    @Override
    public void upadateCodeToCCode(String companyID) {
        List<SCode> scodeList = getCodeListForUpdate(companyID);
        for (SCode scode : scodeList) {
            sessionFactory.getCurrentSession().createQuery("delete CCode d where d.codeID=? and d.companyID = ?").setParameter(0, scode.getCodeID()).setParameter(1, companyID).executeUpdate();
            CCode ccode = new CCode();
            ccode.setCodeID(scode.getCodeID());
            ccode.setCodePID(scode.getCodePID());
            ccode.setCompanyID(companyID);
            ccode.setCodeValue(scode.getCodeValue());
            ccode.setCodeStatus(scode.getCodeStatus());
            ccode.setCodeDesc(scode.getCodeDesc());
            ccode.setCodeNumber(scode.getCodeNumber());
            ccode.setCodeSn(scode.getCodeSn());
            ccode.setGroupSn(scode.getGroupSn());
            ccode.setIsLeaf(scode.getIsLeaf());
            ccode.setIconPath(scode.getIconPath());
            ccode.setCodetype(scode.getCodetype());
            sessionFactory.getCurrentSession().persist(ccode);
        }
    }

}



