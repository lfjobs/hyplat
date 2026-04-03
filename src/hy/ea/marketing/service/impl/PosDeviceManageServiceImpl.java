package hy.ea.marketing.service.impl;

import com.tiantai.wfj.bo.*;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.marketing.service.PosDeviceManageSerivce;
import hy.ea.marketing.service.ScaleManageSerivce;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 电子秤管理
 *
 * @author [mz]
 * @version [1.0, 2019-01-11]
 * @see
 * @since
 */
@Service

public class PosDeviceManageServiceImpl implements PosDeviceManageSerivce {
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;


    private Logger logger = Logger.getLogger(PosDeviceManageServiceImpl.class);


    @Override
    @Transactional
    public void addOrUpdate(PosDevice pos, String companyID, String staffID) {
        if (pos != null) {

                 String hql = "from Staff where staffID = ?";
                //录入人员
                 Staff input = (Staff) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{staffID});
                 pos.setInputID(staffID);
                 pos.setInputName(input.getStaffName());


                //管理公司
                 Company company = (Company) baseBeanDao.getBeanByHqlAndParams("from Company where companyID = ?",new Object[]{companyID});
                 pos.setComID(companyID);
                 pos.setComName(company.getCompanyName());

                pos.setCreateDate(new Date());
               if (pos.getPosID() == null || pos.getPosID().equals("")) {
                    pos.setPosID(serverService.getServerID("posid"));

                }else{
                   PosDeviceHistory pdh = new PosDeviceHistory();
                    pdh.setPosID(serverService.getServerID("posid"));
                    pdh.setComID(pos.getComID());
                    pdh.setAccessCcomID(pos.getAccessCcomID());
                    pdh.setComName(pos.getComName());
                    pdh.setCreateDate(pos.getCreateDate());
                    pdh.setPosID(pos.getPosID());
                    pdh.setFhform(pos.getFhform());
                    pdh.setInputID(pos.getInputID());
                    pdh.setInputName(pos.getInputName());
                    pdh.setOrganizationID(pos.getOrganizationID());
                    pdh.setOrganizationName(pos.getOrganizationName());
                    pdh.setPosNum(pos.getPosNum());
                    pdh.setStaffID(pos.getStaffID());
                    pdh.setStaffName(pos.getStaffName());
                    pdh.setState(pos.getState());
                    pdh.setPosNum(pos.getPosName());
                    baseBeanDao.save(pdh);
               }

                baseBeanDao.update(pos);
            }


    }

    @Override
    @Transactional
    public void delete(String posKey) {

        baseBeanDao.deleteBeanByKey(PosDevice.class,posKey);

    }

    /**
     * 设备号全网唯一
     * @param posNum
     * @return
     */
    public String checkRepPosNum(String posNum,String posID){
        if(posID==null||posID.equals("")){
            posID= " ";
        }
        String hql = "from PosDevice where posNum = ? and posID!=?";
        List<BaseBean> poslist = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{posNum,posID});
        if(poslist.size()==0){

            return "0";
        }else{
            return "1";
        }

    }

}
