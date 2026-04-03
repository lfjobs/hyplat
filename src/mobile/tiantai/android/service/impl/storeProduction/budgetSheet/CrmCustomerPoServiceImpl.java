package mobile.tiantai.android.service.impl.storeProduction.budgetSheet;

import cn.hutool.core.collection.CollectionUtil;
import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.DictData;
import hy.ea.bo.human.Staff;
import hy.ea.human.dao.StaffDao;
import hy.ea.service.DictDataService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.action.storeProduction.budgetSheet.CrmCustomerVo;
import mobile.tiantai.android.bo.CrmCustomerPO;
import mobile.tiantai.android.bo.IntendedCustomers;
import mobile.tiantai.android.bo.StaffSend;
import mobile.tiantai.android.service.storeProduction.budgetSheet.CrmCustomerPoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * CrmCustomerPoServiceImpl
 *
 * @author zch
 */
@Service
public class CrmCustomerPoServiceImpl implements CrmCustomerPoService {
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private StaffDao staffDao;
    @Resource
    private ServerService serverService;
    private List<BaseBean> beans;
    @Resource
    private DictDataService dictDataService;

    @Transactional
    @Override
    public List<CrmCustomerPO> selectCrmCustomerPOList() {
        List<CrmCustomerPO> crmCustomerPOList = new ArrayList<CrmCustomerPO>();
        StringBuilder sql = new StringBuilder();
        List<BaseBean> result = new ArrayList<>();
        sql.append("SELECT t FROM CrmCustomerPO t  ORDER BY updatedAt DESC ");
        result = beandao.getListBeanByHqlAndParams(sql.toString(), new Object[]{});
        if (CollectionUtil.isNotEmpty(result)) {
            for (Object obj : result) {
                CrmCustomerPO obj1 = (CrmCustomerPO) obj;
                crmCustomerPOList.add(obj1);
            }
        }
        return crmCustomerPOList;
    }

    @Transactional
    @Override
    public void saveImportData(List<CrmCustomerVo> crmCustomerVoList) {
        for (CrmCustomerVo crmCustomerVo : crmCustomerVoList) {
            CrmCustomerPO crmCustomerPOOld = getCrmCustomerPO(crmCustomerVo.getNumber());
            if (crmCustomerPOOld != null) {
                SessionWrap sw = SessionWrap.getInstance();
                Map<String, Object> session1 = ActionContext.getContext().getSession();
                TEshopCusCom tc = (TEshopCusCom) sw.getObject(session1,
                        SessionWrap.KEY_SHOPCUSCOM);
                Staff staff = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ?",
                        new Object[]{tc.getStaffid()});
                crmCustomerPOOld.setImporterId(staff.getStaffName());
                crmCustomerPOOld.setUserName(crmCustomerVo.getName());
                crmCustomerPOOld.setContactInfo(crmCustomerVo.getContact());
                crmCustomerPOOld.setResidentialAddress(crmCustomerVo.getAddress());
                crmCustomerPOOld.setSocialStatus(crmCustomerVo.getSocial());
                crmCustomerPOOld.setExtendInfo(crmCustomerVo.getExtend());
                crmCustomerPOOld.setUpdatedAt(getTime());
                beandao.update(crmCustomerPOOld);
            } else {
                CrmCustomerPO crmCustomerPO = new CrmCustomerPO();
                String parentId = UUID.randomUUID().toString().replaceAll("-", "");
                crmCustomerPO.setId(parentId);
                //导入人
                //获取当前登录人
                SessionWrap sw = SessionWrap.getInstance();
                Map<String, Object> session1 = ActionContext.getContext().getSession();
                TEshopCusCom tc = (TEshopCusCom) sw.getObject(session1,
                        SessionWrap.KEY_SHOPCUSCOM);
                Staff staff = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ?",
                        new Object[]{tc.getStaffid()});
                crmCustomerPO.setImporterId(staff.getStaffName());
                crmCustomerPO.setImporterMode("批量导入");
                crmCustomerPO.setUserName(crmCustomerVo.getName());
                crmCustomerPO.setContactInfo(crmCustomerVo.getContact());
                crmCustomerPO.setCardNumber(crmCustomerVo.getNumber());
                crmCustomerPO.setCardType("身份证");
                crmCustomerPO.setResidentialAddress(crmCustomerVo.getAddress());
                crmCustomerPO.setSocialStatus(crmCustomerVo.getSocial());
                crmCustomerPO.setMemberLevel("0");
                crmCustomerPO.setExtendInfo(crmCustomerVo.getExtend());
                crmCustomerPO.setCreatedAt(getTime());
                crmCustomerPO.setUpdatedAt(getTime());
                beandao.save(crmCustomerPO);
            }
        }

    }

    @Transactional
    @Override
    public void deleteCustomer(String id) {
        CrmCustomerPO old = (CrmCustomerPO) beandao.getBeanByHqlAndParams("from CrmCustomerPO where id = ?",
                new Object[]{id});
        if (old != null) {
            beandao.deleteBeanByKey(CrmCustomerPO.class, id);
        }
    }

    @Transactional
    @Override
    public void updateData(CrmCustomerPO crmCustomerPO) {
        CrmCustomerPO old = (CrmCustomerPO) beandao.getBeanByHqlAndParams("from CrmCustomerPO where id = ?",
                new Object[]{crmCustomerPO.getId()});
        if (old != null) {
//            old.setUserName(crmCustomerPO.getUserName());
//            old.setContactInfo(crmCustomerPO.getContactInfo());
//            old.setResidentialAddress(crmCustomerPO.getResidentialAddress());
//            old.setSocialStatus(crmCustomerPO.getSocialStatus());
//            old.setCardNumber(crmCustomerPO.getCardNumber());
//            old.setExtendInfo(crmCustomerPO.getExtendInfo());
//            old.setUpdatedAt(getTime());
//            beandao.update(old);
            updateCustomerPO(crmCustomerPO, old);
        }
    }

//    @Transactional
//    @Override
//    public CrmCustomerPO updCustomer(String id) {
//        CrmCustomerPO crmCustomerPO = (CrmCustomerPO) beandao.getBeanByHqlAndParams("from CrmCustomerPO where id = ?",
//                new Object[]{id});
//        if (crmCustomerPO != null) {
//            return crmCustomerPO;
//        }
//        return null;
//    }

    @Transactional
    @Override
    public CrmCustomerPO updCustomer(String id) {
        CrmCustomerPO crmCustomerPO = new CrmCustomerPO();
        Staff staff = staffDao.getStaffByStaffId(id);
        crmCustomerPO.setId(staff.getStaffID());
        crmCustomerPO.setUserName(staff.getStaffName());
        crmCustomerPO.setContactInfo(staff.getReference());
        crmCustomerPO.setResidentialAddress(staff.getStaffAddress());
        crmCustomerPO.setCardNumber(staff.getStaffIdentityCard());
        crmCustomerPO.setExtendInfo(staff.getStaffDesc());
        crmCustomerPO.setSocialStatus(staff.getPoliticsStatus());
        crmCustomerPO.setCardType("身份证");
        StaffSend staffSend = (StaffSend) beandao.getBeanByHqlAndParams("from StaffSend where staffId = ?",
                new Object[]{id});
        if (staffSend != null) {
            crmCustomerPO.setSendMessage("已发送短信");
        } else {
            crmCustomerPO.setSendMessage("未发送短信");
        }
        List<BaseBean> andParams = beandao.getListBeanByHqlAndParams("from IntendedCustomers where staffId = ? order by updateTime desc",
                new Object[]{id});
        if (!andParams.isEmpty()) {
            crmCustomerPO.setByPhone("已拨打电话");
            List<IntendedCustomers> intendedCustomersList = new ArrayList<>();
            for (BaseBean andParam : andParams) {
                IntendedCustomers intendedCustomers = new IntendedCustomers();
                BeanUtils.copyProperties(andParam, intendedCustomers);
                intendedCustomersList.add(intendedCustomers);
            }
            if (intendedCustomersList != null) {
                crmCustomerPO.setInterestedParties(intendedCustomersList.get(0).getType1());
            }
        } else {
            crmCustomerPO.setByPhone("未拨打电话");
        }
        return crmCustomerPO;
    }

    @Transactional
    @Override
    public void addData(CrmCustomerPO addCustomerPO) {
        CrmCustomerPO crmCustomerPOOld = getCrmCustomerPO(addCustomerPO.getCardNumber());
        if (crmCustomerPOOld == null) {
            CrmCustomerPO crmCustomerPO = new CrmCustomerPO();
            String parentId = UUID.randomUUID().toString().replaceAll("-", "");
            crmCustomerPO.setId(parentId);
            //导入人
            //获取当前登录人
            SessionWrap sw = SessionWrap.getInstance();
            Map<String, Object> session1 = ActionContext.getContext().getSession();
            TEshopCusCom tc = (TEshopCusCom) sw.getObject(session1,
                    SessionWrap.KEY_SHOPCUSCOM);
            Staff staff = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ?",
                    new Object[]{tc.getStaffid()});
            crmCustomerPO.setImporterId(staff.getStaffName());
            crmCustomerPO.setImporterMode("添加");
            crmCustomerPO.setUserName(addCustomerPO.getUserName());
            crmCustomerPO.setContactInfo(addCustomerPO.getContactInfo());
            crmCustomerPO.setCardNumber(addCustomerPO.getCardNumber());
            crmCustomerPO.setCardType("身份证");
            crmCustomerPO.setResidentialAddress(addCustomerPO.getResidentialAddress());
            crmCustomerPO.setSocialStatus(addCustomerPO.getSocialStatus());
            crmCustomerPO.setMemberLevel("0");
            crmCustomerPO.setExtendInfo(addCustomerPO.getExtendInfo());
            crmCustomerPO.setCreatedAt(getTime());
            crmCustomerPO.setUpdatedAt(getTime());
            beandao.save(crmCustomerPO);
        } else {
            updateCustomerPO(addCustomerPO, crmCustomerPOOld);
        }
    }

    @Transactional
    @Override
    public PageForm selectCrmCustomerPOList1(Integer pageNumber, Integer pageSize, String type) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String roleName = account.getRoleName();
        String[] roles = roleName.split(("\\s*,\\s*"));
        DictData data = dictDataService.getDictDataByType("orderAll", "");
        String[] dataValues = data.getDictValue().split(("\\s*,\\s*"));
        boolean hasCommonData = false;
        for (String role : roles) {
            if (Arrays.asList(dataValues).contains(role)) {
                hasCommonData = true;
                break;
            }
        }
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        StringBuffer sql = new StringBuffer(128);
        PageForm pageForm = null;
        if (type.equals("import")) {
            if (hasCommonData) {
                sql.append("SELECT e.* FROM(SELECT b.* FROM (SELECT  a.STAFFKEY, a.STAFFIDENTITYCARD,a.STAFFID STAFFID,a.STAFFNAME,a.SEX,a.REFERENCE REFERENCE ,SCCID,STAFFADDRESS,TO_CHAR(VERIFYTIME, 'YYYY-MM-DD HH24:MI:SS')VERIFYTIME ,IMPORTPERSON  FROM DT_HR_STAFF a WHERE staffStatus ='00' AND SCCID = ? AND IMPORTPERSON IS NOT NULL)b WHERE NOT EXISTS (SELECT 1 FROM T_ESHOP_CUSTOMER c WHERE  b.REFERENCE = c.ACCOUNT ) ORDER BY b.VERIFYTIME DESC)e WHERE NOT EXISTS (SELECT 1 FROM IMPORT_CONTACTS b  WHERE e.STAFFID = b.STAFF_ID  )");
                String hql2 = " SELECT COUNT(*) FROM ( " + sql + " )";
                pageForm = baseBeanService.getPageFormBySQL(
                        (null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql2, new String[]{tc.getSccId()});

            } else if (Arrays.asList(roles).contains("经理")) {

            } else {
                sql.append("SELECT e.* FROM(SELECT b.* FROM (SELECT  a.STAFFKEY, a.STAFFIDENTITYCARD,a.STAFFID STAFFID,a.STAFFNAME,a.SEX,a.REFERENCE REFERENCE ,SCCID,STAFFADDRESS,TO_CHAR(VERIFYTIME, 'YYYY-MM-DD HH24:MI:SS')VERIFYTIME ,IMPORTPERSON  FROM DT_HR_STAFF a WHERE staffStatus ='00' AND SCCID = ? AND IMPORTPERSON IS NOT NULL)b WHERE NOT EXISTS (SELECT 1 FROM T_ESHOP_CUSTOMER c WHERE  b.REFERENCE = c.ACCOUNT ) ORDER BY b.VERIFYTIME DESC)e WHERE NOT EXISTS (SELECT 1 FROM IMPORT_CONTACTS b  WHERE e.STAFFID = b.STAFF_ID  )");
                String hql2 = " SELECT COUNT(*) FROM ( " + sql + " )";
                pageForm = baseBeanService.getPageFormBySQL(
                        (null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql2, new String[]{tc.getSccId()});
            }
        } else if (type.equals("system")) {
            sql.append("SELECT e.* FROM(SELECT b.* FROM (SELECT  a.STAFFKEY, a.STAFFIDENTITYCARD,a.STAFFID STAFFID,a.STAFFNAME,a.SEX,a.REFERENCE REFERENCE ,SCCID,STAFFADDRESS,TO_CHAR(VERIFYTIME, 'YYYY-MM-DD HH24:MI:SS')VERIFYTIME ,IMPORTPERSON  FROM DT_HR_STAFF a WHERE staffStatus ='00'  AND IMPORTPERSON IS NULL)b WHERE NOT EXISTS (SELECT 1 FROM T_ESHOP_CUSTOMER c WHERE  b.REFERENCE = c.ACCOUNT ) ORDER BY b.VERIFYTIME DESC)e WHERE NOT EXISTS (SELECT 1 FROM IMPORT_CONTACTS b  WHERE e.STAFFID = b.STAFF_ID)");
            String hql2 = " SELECT COUNT(*) FROM ( " + sql + " )";
            pageForm = baseBeanService.getPageFormBySQL(
                    (null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql2, new String[]{});
        }
        return pageForm;
    }

    @Transactional
    @Override
    public String deleteCrmCustomerPO(String id) {
        Staff staff = staffDao.getStaffByStaffId(id);
        staff.setStaffStatus("98");
        baseBeanService.update(staff);
        return "success";
    }

    @Transactional
    @Override
    public String updateData1(CrmCustomerPO old) {
        Staff staff = staffDao.getStaffByStaffId(old.getId());
        //姓名
        staff.setStaffName(old.getUserName());
        //电话
        staff.setReference(old.getContactInfo());
        //身份证
        staff.setStaffIdentityCard(old.getCardNumber());
        //地址
        staff.setStaffAddress(old.getResidentialAddress());
        //备注
        staff.setStaffDesc(old.getExtendInfo());
        //政治面貌
        staff.setPoliticsStatus(old.getSocialStatus());
        staff.setVerifyTime(new Date());
        baseBeanService.update(staff);
        return "success";
    }

    @Transactional
    @Override
    public String addData1(CrmCustomerPO addCustomerPO) {
        SessionWrap sw = SessionWrap.getInstance();
        Map<String, Object> session = ActionContext.getContext().getSession();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        Staff staff1 = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ?",
                new Object[]{tc.getStaffid()});
        Staff staffOld = (Staff) beandao.getBeanByHqlAndParams("from Staff where reference = ?",
                new Object[]{addCustomerPO.getContactInfo()});

        if (staffOld == null) {
            Staff staff = new Staff();
            beans = new ArrayList<>();
            CAccount account = (CAccount) ActionContext.getContext().getSession()
                    .get("account");
            //姓名
            staff.setStaffName(addCustomerPO.getUserName());
            staff.setSccid(tc.getSccId());
            staff.setImportPerson(staff1.getStaffName());
            staff.setVipno("1");
            //电话
            staff.setReference(addCustomerPO.getContactInfo());
            //身份证
            staff.setStaffIdentityCard(addCustomerPO.getCardNumber());
            //地址
            staff.setStaffAddress(addCustomerPO.getResidentialAddress());
            //备注
            staff.setStaffDesc(addCustomerPO.getExtendInfo());
            //导入的单位
            staff.setUnitCompany(addCustomerPO.getUnitCompany());
            //政治面貌
            staff.setPoliticsStatus(addCustomerPO.getSocialStatus());
            String flag = saveStaffBaseData(staff);
            if (!flag.equals("success")) {
                return flag;
            }
            baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        } else {
            staffOld.setVerifyTime(new Date());
            staffOld.setStaffStatus("00");
            baseBeanService.update(staffOld);
        }
        return "success";
    }

    @Transactional
    @Override
    public String saveImportData1(List<CrmCustomerVo> dataDTOList) {
        for (CrmCustomerVo crmCustomerVo : dataDTOList) {
            CrmCustomerPO addCustomerPO = new CrmCustomerPO();
            addCustomerPO.setUserName(crmCustomerVo.getName());
            addCustomerPO.setContactInfo(crmCustomerVo.getContact());
            addCustomerPO.setCardNumber(crmCustomerVo.getNumber());
            addCustomerPO.setResidentialAddress(crmCustomerVo.getAddress());
            addCustomerPO.setSocialStatus(crmCustomerVo.getSocial());
            addCustomerPO.setExtendInfo(crmCustomerVo.getExtend());
            addCustomerPO.setUnitCompany(crmCustomerVo.getUnitCompany());
            addData1(addCustomerPO);
        }
        return "success";
    }

    @Transactional
    @Override
    public PageForm selectCrmCustomerPOList2(String type, Integer pageNumber, Integer pageSize) {
        StringBuffer sql = new StringBuffer(128);
        if (type.equals("is")) {
            sql.append("SELECT b.* FROM (SELECT  a.STAFFKEY, a.STAFFIDENTITYCARD,a.STAFFID STAFFID,a.STAFFNAME,a.SEX,a.REFERENCE REFERENCE ,SCCID,STAFFADDRESS,TO_CHAR(VERIFYTIME, 'YYYY-MM-DD HH24:MI:SS')VERIFYTIME ,IMPORTPERSON  FROM DT_HR_STAFF a WHERE staffStatus ='00'  AND IMPORTPERSON IS  NULL )b WHERE  EXISTS (SELECT 1 FROM T_ESHOP_CUSTOMER c WHERE  b.REFERENCE = c.ACCOUNT ) ORDER BY b.VERIFYTIME DESC");
        } else {
            sql.append("SELECT b.* FROM (SELECT  a.STAFFKEY, a.STAFFIDENTITYCARD,a.STAFFID STAFFID,a.STAFFNAME,a.SEX,a.REFERENCE REFERENCE ,SCCID,STAFFADDRESS,TO_CHAR(VERIFYTIME, 'YYYY-MM-DD HH24:MI:SS')VERIFYTIME ,IMPORTPERSON  FROM DT_HR_STAFF a WHERE staffStatus ='00'  AND IMPORTPERSON IS NOT NULL )b WHERE  EXISTS (SELECT 1 FROM T_ESHOP_CUSTOMER c WHERE  b.REFERENCE = c.ACCOUNT ) ORDER BY b.VERIFYTIME DESC");
        }
//        sql.append("SELECT b.*\n" +
//                "                FROM (SELECT  MAX(a.STAFFKEY), a.STAFFIDENTITYCARD,MAX(a.STAFFID),MAX(a.STAFFNAME),MAX(a.SEX),MAX(a.REFERENCE) REFERENCE ,MAX(SCCID),MAX(STAFFADDRESS),TO_CHAR(MAX(VERIFYTIME), 'YYYY-MM-DD HH:MM:SS')VERIFYTIME ,MAX(IMPORTPERSON)  FROM DT_HR_STAFF a WHERE staffStatus ='00' \n" +
//                "                GROUP BY STAFFIDENTITYCARD\n" +
//                "                )b\n" +
//                "                WHERE NOT EXISTS (\n" +
//                "                   SELECT 1\n" +
//                "                    FROM T_ESHOP_CUSTOMER c \n" +
//                "                    WHERE  b.REFERENCE = c.ACCOUNT\n" +
//                "                ) ORDER BY b.VERIFYTIME DESC");
        String hql2 = " SELECT COUNT(*) FROM ( " + sql + " )";
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql2, new String[]{});
        return pageForm;
    }

    @Transactional
    @Override
    public CrmCustomerPO queryCustomer(String id) {

        return null;
    }

    @Override
    public List<CrmCustomerVo> contrastStaff(List<CrmCustomerVo> dataDTOList) {
        // Step 1: 获取总记录数并正确解析
        Object countResult = beandao.getObjectBySqlAndParams(
                "SELECT count(*) FROM DT_HR_STAFF WHERE staffStatus = '00' AND VIPNO = '1' AND REFERENCE IS NOT NULL",
                new Object[]{});

        // Step 2: 计算分页总数
        int pageSize = 20;
        BigDecimal totalCount = new BigDecimal(countResult.toString());
        BigDecimal totalPages = totalCount.divide(
                BigDecimal.valueOf(pageSize),
                0,
                RoundingMode.CEILING
        );
        // Step 3: 存储 REFERENCE 的集合
        Set<String> referenceSet = new HashSet<>();

        // Step 4: 分页查询 REFERENCE
        for (int pageNum = 0; pageNum < totalPages.intValue(); pageNum++) {
            int offset = pageNum * pageSize;
            String paginatedSql =
                    "SELECT REFERENCE FROM ( " +
                            "    SELECT REFERENCE, ROWNUM rn FROM ( " +
                            "        SELECT REFERENCE FROM DT_HR_STAFF " +
                            "        WHERE staffStatus = '00' AND VIPNO = '1' AND REFERENCE IS NOT NULL " +
                            "        ORDER BY REFERENCE " +
                            "    ) WHERE ROWNUM <= ? " +
                            ") WHERE rn > ?";

            // 使用参数化查询（推荐），避免 SQL 注入
            List<?> objectBySqlAndParams = beandao.getListObjectBySqlAndParams(paginatedSql,
                    new Object[]{offset + pageSize, offset});

            // 解析查询结果
            if (objectBySqlAndParams instanceof List) {
                for (Object row : (List<?>) objectBySqlAndParams) {
                    if (row instanceof Object[] && ((Object[]) row).length > 0) {
                        Object refObj = ((Object[]) row)[0];
                        if (refObj != null) {
                            referenceSet.add(refObj.toString().trim());
                        }
                    } else if (row != null) {
                        referenceSet.add(row.toString().trim());
                    }
                }
            }
        }

        // Step 5: 筛选匹配的 CrmCustomerVo
        List<CrmCustomerVo> result = new ArrayList<>();
        for (CrmCustomerVo vo : dataDTOList) {
            if (vo != null && vo.getContact() != null && !vo.getContact().trim().isEmpty()) {
                if (referenceSet.contains(vo.getContact().trim())) {
                    result.add(vo);
                }
            }
        }
        return result;
    }

    @Override
    public PageForm selectreferenceList(Integer pageNumber, Integer pageSize) {
        StringBuffer sql = new StringBuffer(128);
        sql.append("SELECT REFERENCE\n" +
                "FROM DT_HR_STAFF\n" +
                "WHERE staffStatus = '00' \n" +
                "  AND VIPNO = '1' \n" +
                "  AND REFERENCE IS NOT NULL\n" +
                "GROUP BY REFERENCE\n" +
                "ORDER BY MAX(verifyTime) DESC");
//        sql.append("SELECT DISTINCT REFERENCE FROM DT_HR_STAFF WHERE staffStatus = '00' AND VIPNO = '1' AND REFERENCE IS NOT NULL");
        String hql2 = " SELECT COUNT(*) FROM ( " + sql + " )";
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql2, new String[]{});
        return pageForm;
    }


    /**
     * 更新数据
     *
     * @param addCustomerPO
     * @param crmCustomerPOOld
     */
    private void updateCustomerPO(CrmCustomerPO addCustomerPO, CrmCustomerPO crmCustomerPOOld) {
        crmCustomerPOOld.setUserName(addCustomerPO.getUserName());
        crmCustomerPOOld.setContactInfo(addCustomerPO.getContactInfo());
        crmCustomerPOOld.setResidentialAddress(addCustomerPO.getResidentialAddress());
        crmCustomerPOOld.setSocialStatus(addCustomerPO.getSocialStatus());
        crmCustomerPOOld.setCardNumber(addCustomerPO.getCardNumber());
        crmCustomerPOOld.setExtendInfo(addCustomerPO.getExtendInfo());
        crmCustomerPOOld.setUpdatedAt(getTime());
        beandao.update(crmCustomerPOOld);
    }

    /**
     * 获取时间
     *
     * @return
     */
    private String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA));
    }

    private CrmCustomerPO getCrmCustomerPO(String cardNumber) {
        return (CrmCustomerPO) beandao.getBeanByHqlAndParams("from CrmCustomerPO where cardNumber = ?",
                new Object[]{cardNumber});
    }

    public String saveStaffBaseData(Staff cstaff) {
        String staffId = cstaff.getStaffID();
        if (staffId == null || "".equals(staffId)) {
            String phql = "select count(*) from Staff ";
            int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
            cstaff.setStaffCode("NO" + pcount);
            cstaff.setRecordCode("NO" + pcount);
            cstaff.setVerifyTime(new Date());
            cstaff.setStaffID(serverService.getServerID("cstaff"));
        } else {
            cstaff.setVerifyTime(new Date());
        }
        cstaff.setStaffStatus("00");
        beans.add(cstaff);
        return "success";
    }

}
