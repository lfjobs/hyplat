package hy.ea.st.service;

import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.CAccount;
import hy.ea.bo.Enroll;
import hy.ea.bo.company.ContactRelation;
import hy.plat.bo.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */



public interface EnrollsService {
    List<BaseBean> getProductByType(String sql,String ccompanyId);

    List<BaseBean> findPro(String sql,String ccompanyId);

    void saveEnroll(Enroll enroll);

    void saveAccount(CAccount account);

    void addRelation(ContactRelation contactRelation);

    /**
     *
     * 获取促销品
     * @param ppid
     * @param ptppid
     * @return
     */
    List<Object> getPromotionList(String ppid,String ptppid);

    /**
     * 根据人员ID查询StaffID
     * @return
     */
    TEshopCusCom getTEshopByStaffID(String staffID);

    /**
     *
     * 生成5l5C账号
     * @param phone
     * @param companyID
     */
    void generateAccount(String phone,String companyID,String coachStaffID);

    /**
     *
     * @param sccid
     * @return
     */
    String validateEnroll(String sccid,String licenceType,String companyID);

    String getSccIdByAccount(String account);

}
