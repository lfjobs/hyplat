package hy.ea.service.impl;

import hy.ea.bo.CCode;
import hy.ea.bo.DrivingSchool.TbSysGeography;
import hy.ea.dao.CCodeDao;
import hy.ea.service.CCodeService;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class CCodeServiceImpl implements CCodeService {

    @Resource
    private CCodeDao codeDao;

    @Override
    public CCode getCCodeByID(String companyID, String codeID) {

        return codeDao.getCCodeByID(companyID, codeID);
    }


    @Override
    public List<CCode> getCCodeListByPID(String companyID, String codePID) {
        return  codeDao.getCCodeListByPID(companyID, codePID);
    }

    /**
     * 根据companyID和codePID查询出其子节点
     *
     * @param companyID
     * @param codePID
     * @param codetype
     * @return
     */
    @Override
    public List<CCode> getCCodeListNewByPID(String companyID, String codePID, String codetype) {
        return codeDao.getCCodeListNewByPID(companyID, codePID,codetype);
    }

    @Override
    public List<CCode> getCCodeListByPIDs(String companyID, String[] codePID) {
        return codeDao.getCCodeListByPIDs(companyID, codePID);
    }

    @Override
    public void upadateCodeToCCode(String companyID) {
        codeDao.upadateCodeToCCode(companyID);
    }

    @Override
    public List<CCode> getAllCCodeListByPID(String companyID, String codePID, List<CCode> list) {
        List<CCode> codeList = this.getCCodeListByPID(companyID, codePID);
        if (codeList != null) {
            for (CCode code : codeList) {
                list.add(code);
                if (getCCodeListByPID(companyID, code.getCodeID()) != null) {
                    getAllCCodeListByPID(companyID, code.getCodeID(), list);
                }
            }
        }
        return list;
    }

    @Override
    public String getCodeList(String companyID, String codeID, String codeStr) {
        List<CCode> ccodeList = codeDao.getCCodeListByPID(companyID, codeID);
        String codeList = codeStr;
        if (ccodeList.size() > 0) {
            for (CCode ccode : ccodeList) {
                codeList += ccode.getCodeID() + "-";
                getCodeList(companyID, ccode.getCodeID(), codeList);

            }
        }

        return codeList;
    }

    @Override
    public List<TbSysGeography> getAllTbSysGeographyBygeoLevel(String geoLevel) {
        return codeDao.getAllTbSysGeographyBygeoLevel(geoLevel);
    }


}