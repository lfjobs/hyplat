package mobile.tiantai.android.service.impl.storeProduction.budgetSheet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hutool.core.collection.CollectionUtil;
import com.opensymphony.xwork2.ActionContext;
import com.stamp.Office2PdfUtil;
import hy.ea.bo.CAccount;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.*;
import hy.ea.office.service.ContractService;
import hy.ea.office.service.DocCommonService;
import hy.ea.office.service.ZOfficeService;
import hy.ea.util.StringUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.action.storeProduction.budgetSheet.DataDTO;
import mobile.tiantai.android.bo.*;
import mobile.tiantai.android.service.storeProduction.budgetSheet.TrilateralDataService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.awt.util.IdentityArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TrilateralDataServiceImpl implements TrilateralDataService {
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private DocCommonService docCommonService;
    @Resource
    private ZOfficeService zOfficeService;
    @Resource
    private ContractService contractService;
    @Autowired
    private ServerService serverService;


    @Override
    public List<TrilateralData> getTrilateralDataList(String uploadProject) {
        List<TrilateralData> trilateralDataList = new IdentityArrayList<>();
        StringBuilder sql = new StringBuilder();
        List<BaseBean> result = new ArrayList<>();
        if (uploadProject == null) {
            sql.append("SELECT t FROM TrilateralData t WHERE state ='0'");
            result = beandao.getListBeanByHqlAndParams(sql.toString(), new Object[]{});
        } else {
            sql.append("SELECT t FROM TrilateralData t WHERE state ='0' and uploadProject =?");
            result = beandao.getListBeanByHqlAndParams(sql.toString(), new Object[]{uploadProject});
        }
        if (CollectionUtil.isNotEmpty(result)) {
            for (Object obj : result) {
                TrilateralData obj1 = (TrilateralData) obj;
                trilateralDataList.add(obj1);
            }
        }
        return trilateralDataList;
    }

    @Transactional
    @Override
    public void addTrilateralData(TrilateralData addTrilateralData, String photoPath) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        TrilateralData trilateralDataOld = this.findTrilateralDataByPhone(addTrilateralData.getPhone(), addTrilateralData.getRegistrationNo(), addTrilateralData.getRegisterAccount(), addTrilateralData.getUploadProject());
        if (trilateralDataOld != null) {
            BeanUtils.copyProperties(addTrilateralData, trilateralDataOld);
            addTrilateralData.setUpdateTime(getTime());
            saveTrilateralContract(addTrilateralData, photoPath);
            //附件地址  名称
            addTrilateralData.setAttachmentAddress(photoPath);
        }
        String parentId = UUID.randomUUID().toString().replaceAll("-", "");
        addTrilateralData.setId(parentId);
        addTrilateralData.setState("0");
        addTrilateralData.setTime(getTime());
        addTrilateralData.setUpdateTime(getTime());
        addTrilateralData.setInputId(account.getStaffID());
        addTrilateralData.setInputName(account.getStaffName());
        //附件地址  名称
        addTrilateralData.setAttachmentAddress(photoPath);
        //合同
        //baseBeanService.executeHqlsByParamsList(null, hqls, parms);
        String realpath = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
        if (addTrilateralData.getContractAddId() != null) {
            createDoc(realpath, account.getCompanyID(), account.getStaffID(), addTrilateralData.getContractAddId(), addTrilateralData, "01");
        }
        saveTrilateralContract(addTrilateralData, photoPath);
        baseBeanDao.save(addTrilateralData);
    }

    @Transactional
    @Override
    public TrilateralData findTrilateralDataByPhone(String phone, String registrationNo, String registerAccount, String uploadProject) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t FROM TrilateralData t WHERE phone = ? AND registrationNo =? AND registerAccount = ? AND uploadProject  =?");
        TrilateralData result = (TrilateralData) beandao.getBeanByHqlAndParams(sql.toString(), new Object[]{phone, registrationNo, registerAccount, uploadProject});
        if (!Optional.ofNullable(result).isPresent()) {
            return null;
        }
        return result;
    }

    @Transactional
    @Override
    public String delTrilateralData(String id) {
        //根据id查询
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t FROM TrilateralData t WHERE state ='0' and id =?");
        TrilateralData result = (TrilateralData) beandao.getBeanByHqlAndParams(sql.toString(), new Object[]{id});
        if (!Optional.ofNullable(result).isPresent()) {
            return "删除失败";
        }
        //根据id更改状态为1
        result.setState("1");
        baseBeanDao.update(result);
        return "success";
    }

    @Override
    public TrilateralData selTrilateralDataBy(String id) {
        //根据id查询
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t FROM TrilateralData t WHERE state ='0' and id =?");
        TrilateralData result = (TrilateralData) beandao.getBeanByHqlAndParams(sql.toString(), new Object[]{id});
        if (!Optional.ofNullable(result).isPresent()) {
            return null;
        }
        return result;
    }

    @Transactional
    @Override
    public void updateTrilateralData(TrilateralData updateTrilateralData, String photoPath) {
        TrilateralData trilateralData = selTrilateralDataBy(updateTrilateralData.getId());
        if (!Optional.ofNullable(trilateralData).isPresent()) {
            return;
        }
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        updateTrilateralData.setState("0");
        BeanUtils.copyProperties(updateTrilateralData, trilateralData);
        trilateralData.setUpdateTime(getTime());
        trilateralData.setInputId(account.getStaffID());
        trilateralData.setInputName(account.getStaffName());
        trilateralData.setAttachmentAddress(photoPath);
        //合同
        //baseBeanService.executeHqlsByParamsList(null, hqls, parms);
        String realpath = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
        if (trilateralData.getContractAddId() != null) {
            createDoc(realpath, account.getCompanyID(), account.getStaffID(), trilateralData.getContractAddId(), trilateralData, "01");
        }
        saveTrilateralContract(trilateralData, photoPath);
        beandao.update(trilateralData);
    }

    /**
     * 数据导入新增
     *
     * @param dataDTOList
     */
    @Transactional
    @Override
    public void saveImportData(List<DataDTO> dataDTOList) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        for (DataDTO dataDTO : dataDTOList) {
            TrilateralData trilateralData = new TrilateralData();
            BeanUtils.copyProperties(dataDTO, trilateralData);
            String parentId = UUID.randomUUID().toString().replaceAll("-", "");
            trilateralData.setId(parentId);
            trilateralData.setState("0");
            trilateralData.setTime(getTime());
            trilateralData.setUpdateTime(getTime());
            trilateralData.setInputId(account.getStaffID());
            trilateralData.setInputName(account.getStaffName());
            baseBeanDao.save(trilateralData);
        }
    }

    @Transactional
    @Override
    public void saveTrilateralData(TrilateralData trilateralData) {
        TrilateralData trilateralData1 = trilateralData;
        beandao.update(trilateralData);
    }

    @Override
    public List<TrilateralData> getTrilateralData(String companyName) {
        List<TrilateralData> trilateralDataList = new IdentityArrayList<>();
        StringBuilder sql = new StringBuilder();
        List<BaseBean> result = new ArrayList<>();
        sql.append("SELECT t FROM TrilateralData t WHERE state ='0' and companyName LIKE ?");
        result = beandao.getListBeanByHqlAndParams(sql.toString(), new Object[]{"%" + companyName + "%"});
        if (CollectionUtil.isNotEmpty(result)) {
            for (Object obj : result) {
                TrilateralData obj1 = (TrilateralData) obj;
                trilateralDataList.add(obj1);
            }
        }
        return trilateralDataList;
    }

    @Override
    public PageForm getTrilateral(Integer pageNumber, Integer pageSize, String uploadProject, String trusteeship) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        StringBuffer sql = new StringBuffer(128);
        PageForm pageForm;
        if (uploadProject.equals("all")) {
            if (trusteeship.equals("all")) {
                sql.append("select ID, COMPANY_NAME, PERSONNEL_ID, NAME ,SEX, SECTOR, PHONE ,REGISTRATION_PLATFORM_NAME, REGISTRATION_NO ,REGISTER_ACCOUNT, PWD, UPLOAD_PROJECT, PROMOTION_REASONS, DISTRIBUTION_SERVICE ,SERVICE_TRACKING, BUSINESS_PERSONNEL, STATE ,STAFF_ID ,STAFF_NAME ,PRIVATE_MESSAGE, AUDIT1, CONTRACT_ADDRESS, ATTACHMENT_ADDRESS, DISTRIBUTION_STATE, DISTRIBUTION_PERSONID ,DISTRIBUTION_PERSON_NAME ,INPUT_ID ,INPUT_NAME  ,COMPANY_ID from TRILATERAL_DATA d where INPUT_ID = ? and state = '0' ORDER BY  UPDATE_TIME desc");
                String hql = " SELECT COUNT(*) FROM ( " + sql + " )";
                pageForm = baseBeanService.getPageFormBySQL((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 40 : pageSize), sql.toString(), hql, new String[]{account.getStaffID()});
            } else {
                sql.append("select ID, COMPANY_NAME, PERSONNEL_ID, NAME ,SEX, SECTOR, PHONE ,REGISTRATION_PLATFORM_NAME, REGISTRATION_NO ,REGISTER_ACCOUNT, PWD, UPLOAD_PROJECT, PROMOTION_REASONS, DISTRIBUTION_SERVICE ,SERVICE_TRACKING, BUSINESS_PERSONNEL, STATE ,STAFF_ID ,STAFF_NAME ,PRIVATE_MESSAGE, AUDIT1, CONTRACT_ADDRESS, ATTACHMENT_ADDRESS, DISTRIBUTION_STATE, DISTRIBUTION_PERSONID ,DISTRIBUTION_PERSON_NAME ,INPUT_ID ,INPUT_NAME  ,COMPANY_ID from TRILATERAL_DATA d where INPUT_ID = ? and state = '0' and TRUSTEESHIP = ? ORDER BY  UPDATE_TIME desc");
                String hql = " SELECT COUNT(*) FROM ( " + sql + " )";
                pageForm = baseBeanService.getPageFormBySQL((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 40 : pageSize), sql.toString(), hql, new String[]{account.getStaffID(), trusteeship});

            }
        } else {
            if (trusteeship.equals("all")) {
                sql.append("select ID, COMPANY_NAME, PERSONNEL_ID, NAME ,SEX, SECTOR, PHONE ,REGISTRATION_PLATFORM_NAME, REGISTRATION_NO ,REGISTER_ACCOUNT, PWD, UPLOAD_PROJECT, PROMOTION_REASONS, DISTRIBUTION_SERVICE ,SERVICE_TRACKING, BUSINESS_PERSONNEL, STATE ,STAFF_ID ,STAFF_NAME ,PRIVATE_MESSAGE, AUDIT1, CONTRACT_ADDRESS, ATTACHMENT_ADDRESS, DISTRIBUTION_STATE, DISTRIBUTION_PERSONID ,DISTRIBUTION_PERSON_NAME ,INPUT_ID ,INPUT_NAME  ,COMPANY_ID from TRILATERAL_DATA d where upload_Project = ? and INPUT_ID = ? and state = '0' ORDER BY  UPDATE_TIME desc");
                String hql = " SELECT COUNT(*) FROM ( " + sql + " )";
                pageForm = baseBeanService.getPageFormBySQL((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 40 : pageSize), sql.toString(), hql, new String[]{uploadProject, account.getStaffID()});
            } else {
                sql.append("select ID, COMPANY_NAME, PERSONNEL_ID, NAME ,SEX, SECTOR, PHONE ,REGISTRATION_PLATFORM_NAME, REGISTRATION_NO ,REGISTER_ACCOUNT, PWD, UPLOAD_PROJECT, PROMOTION_REASONS, DISTRIBUTION_SERVICE ,SERVICE_TRACKING, BUSINESS_PERSONNEL, STATE ,STAFF_ID ,STAFF_NAME ,PRIVATE_MESSAGE, AUDIT1, CONTRACT_ADDRESS, ATTACHMENT_ADDRESS, DISTRIBUTION_STATE, DISTRIBUTION_PERSONID ,DISTRIBUTION_PERSON_NAME ,INPUT_ID ,INPUT_NAME  ,COMPANY_ID from TRILATERAL_DATA d where upload_Project = ? and INPUT_ID = ? and state = '0' and TRUSTEESHIP = ? ORDER BY  UPDATE_TIME desc");
                String hql = " SELECT COUNT(*) FROM ( " + sql + " )";
                pageForm = baseBeanService.getPageFormBySQL((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 40 : pageSize), sql.toString(), hql, new String[]{uploadProject, account.getStaffID(), trusteeship});

            }
        }
        return pageForm;
    }

    @Override
    public PageForm searchTrilateralByName(Integer pageNumber, Integer pageSize, String name) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        StringBuffer sql = new StringBuffer(128);
        sql.append("select c.companyName,\n" + "c.companyId,\n" + "a.STAFFCODE,\n" + "a.STAFFID ,\n" + "STAFFNAME ,\n" + "sex ,\n" + "REFERENCE , \n" + "industryType \n" + "FROM dt_hr_Staff a\n" + "left JOIN DTCOS b ON a.STAFFID = b.STAFFID \n" + "left join DTCOMPANY c on b.companyId = c.companyId\n" + "where a.STAFFNAME like ? and\n" + "VERIFYTIME is not null order by verifyTime DESC");
        String hql = " SELECT COUNT(*) FROM ( " + sql + " )";
        PageForm pageForm = baseBeanService.getPageFormBySQL((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql, new String[]{"%" + name + "%"});
        return pageForm;
    }

    @Transactional
    @Override
    public String allocationImportContacts(List<StaffSend> staffSendList, String sfpt) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        TrilateralData trilateralData = selTrilateralDataBy(sfpt);
        trilateralData.setDistributionPersonId(staffSendList.get(0).getStaffId());
        trilateralData.setDistributionPersonName(staffSendList.get(0).getName());
        trilateralData.setDistributionPersonCompanyId(staffSendList.get(0).getCompanyId());
        trilateralData.setDistributionPersonPhone(staffSendList.get(0).getPhone());
        trilateralData.setDistributionState("1");
        trilateralData.setUpdateTime(getTime());
        beandao.update(trilateralData);
        TrilateralDataAudit audit = new TrilateralDataAudit();
        audit.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        audit.setTrilateralDataId(sfpt);
        audit.setSendStaffId(staffSendList.get(0).getStaffId());
        audit.setSendStaffName(staffSendList.get(0).getName());
        audit.setAuditStaffId(account.getStaffID());
        audit.setAuditStaffName(account.getStaffName());
        audit.setAuditTime(getTime());
        baseBeanService.save(audit);
        return "success";
    }

    @Override
    public PageForm getTrilateralTotals(Integer pageNumber, Integer pageSize, String uploadProject, String trusteeship, String staffName, String phone) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        StringBuffer sql = new StringBuffer(128);
        PageForm pageForm = null;
        if (StringUtil.isEmpty(staffName) && StringUtil.isEmpty(phone)) {
            sql.append("WITH phone_platform_count AS (SELECT PHONE, COUNT(DISTINCT REGISTRATION_PLATFORM_NAME) AS distinct_platform_count FROM TRILATERAL_DATA WHERE PHONE IS NOT NULL GROUP BY PHONE)SELECT d.id, d.PERSONNEL_ID,d.NAME,d.PHONE,d.REGISTRATION_PLATFORM_NAME,d.REGISTER_ACCOUNT,ppc.distinct_platform_count  FROM TRILATERAL_DATA d\n" + "JOIN phone_platform_count ppc ON d.PHONE = ppc.PHONE WHERE d.INPUT_ID = ?" + "ORDER BY d.UPDATE_TIME DESC");
            String hql = " SELECT COUNT(*) FROM ( " + sql + " )";
            pageForm = baseBeanService.getPageFormBySQL((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 40 : pageSize), sql.toString(), hql, new String[]{account.getStaffID()});

        } else if (staffName != null && StringUtil.isEmpty(phone)) {
            sql.append("WITH phone_platform_count AS (SELECT PHONE, COUNT(DISTINCT REGISTRATION_PLATFORM_NAME) AS distinct_platform_count FROM TRILATERAL_DATA WHERE PHONE IS NOT NULL GROUP BY PHONE)SELECT d.id, d.PERSONNEL_ID,d.NAME,d.PHONE,d.REGISTRATION_PLATFORM_NAME,d.REGISTER_ACCOUNT,ppc.distinct_platform_count  FROM TRILATERAL_DATA d\n" + "JOIN phone_platform_count ppc ON d.PHONE = ppc.PHONE WHERE d.INPUT_ID = ? and d.name like ?" + "ORDER BY d.UPDATE_TIME DESC");
            String hql = " SELECT COUNT(*) FROM ( " + sql + " )";
            pageForm = baseBeanService.getPageFormBySQL((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 40 : pageSize), sql.toString(), hql, new String[]{account.getStaffID(), "%" + staffName + "%"});

        } else if (StringUtil.isEmpty(staffName) && phone != null) {
            sql.append("WITH phone_platform_count AS (SELECT PHONE, COUNT(DISTINCT REGISTRATION_PLATFORM_NAME) AS distinct_platform_count FROM TRILATERAL_DATA WHERE PHONE IS NOT NULL GROUP BY PHONE)SELECT d.id, d.PERSONNEL_ID,d.NAME,d.PHONE,d.REGISTRATION_PLATFORM_NAME,d.REGISTER_ACCOUNT,ppc.distinct_platform_count  FROM TRILATERAL_DATA d\n" + "JOIN phone_platform_count ppc ON d.PHONE = ppc.PHONE WHERE d.INPUT_ID = ? and d.phone like ?" + "ORDER BY d.UPDATE_TIME DESC");
            String hql = " SELECT COUNT(*) FROM ( " + sql + " )";
            pageForm = baseBeanService.getPageFormBySQL((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 40 : pageSize), sql.toString(), hql, new String[]{account.getStaffID(), "%" + phone + "%"});

        }
        return pageForm;
    }

    @Override
    public PageForm getTrilateralAudit(Integer pageNumber, Integer pageSize, String uploadProject, String trusteeship, String staffName, String phone) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        StringBuffer sql = new StringBuffer(128);
        PageForm pageForm = null;
        if (StringUtil.isEmpty(staffName) && StringUtil.isEmpty(phone)) {
            sql.append("select ID, COMPANY_NAME, PERSONNEL_ID, NAME ,SEX, SECTOR, PHONE ,REGISTRATION_PLATFORM_NAME, REGISTRATION_NO ,REGISTER_ACCOUNT, PWD, UPLOAD_PROJECT, PROMOTION_REASONS, DISTRIBUTION_SERVICE ,SERVICE_TRACKING, BUSINESS_PERSONNEL, STATE ,STAFF_ID ,STAFF_NAME ,PRIVATE_MESSAGE, AUDIT1, CONTRACT_ADDRESS, ATTACHMENT_ADDRESS, DISTRIBUTION_STATE, DISTRIBUTION_PERSONID ,DISTRIBUTION_PERSON_NAME ,INPUT_ID ,INPUT_NAME  ,COMPANY_ID from TRILATERAL_DATA d where DISTRIBUTION_PERSONID = ? and state = '0' ORDER BY  UPDATE_TIME desc");
            String hql = " SELECT COUNT(*) FROM ( " + sql + " )";
            pageForm = baseBeanService.getPageFormBySQL((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 40 : pageSize), sql.toString(), hql, new String[]{account.getStaffID()});

        } else if (staffName != null && StringUtil.isEmpty(phone)) {
            sql.append("select ID, COMPANY_NAME, PERSONNEL_ID, NAME ,SEX, SECTOR, PHONE ,REGISTRATION_PLATFORM_NAME, REGISTRATION_NO ,REGISTER_ACCOUNT, PWD, UPLOAD_PROJECT, PROMOTION_REASONS, DISTRIBUTION_SERVICE ,SERVICE_TRACKING, BUSINESS_PERSONNEL, STATE ,STAFF_ID ,STAFF_NAME ,PRIVATE_MESSAGE, AUDIT1, CONTRACT_ADDRESS, ATTACHMENT_ADDRESS, DISTRIBUTION_STATE, DISTRIBUTION_PERSONID ,DISTRIBUTION_PERSON_NAME ,INPUT_ID ,INPUT_NAME  ,COMPANY_ID from TRILATERAL_DATA d where DISTRIBUTION_PERSONID = ?  and d.name like ? and state = '0' ORDER BY  UPDATE_TIME desc");
            String hql = " SELECT COUNT(*) FROM ( " + sql + " )";
            pageForm = baseBeanService.getPageFormBySQL((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 40 : pageSize), sql.toString(), hql, new String[]{account.getStaffID(), "%" + staffName + "%"});

        } else if (StringUtil.isEmpty(staffName) && phone != null) {
            sql.append("select ID, COMPANY_NAME, PERSONNEL_ID, NAME ,SEX, SECTOR, PHONE ,REGISTRATION_PLATFORM_NAME, REGISTRATION_NO ,REGISTER_ACCOUNT, PWD, UPLOAD_PROJECT, PROMOTION_REASONS, DISTRIBUTION_SERVICE ,SERVICE_TRACKING, BUSINESS_PERSONNEL, STATE ,STAFF_ID ,STAFF_NAME ,PRIVATE_MESSAGE, AUDIT1, CONTRACT_ADDRESS, ATTACHMENT_ADDRESS, DISTRIBUTION_STATE, DISTRIBUTION_PERSONID ,DISTRIBUTION_PERSON_NAME ,INPUT_ID ,INPUT_NAME  ,COMPANY_ID from TRILATERAL_DATA d where DISTRIBUTION_PERSONID = ?  and d.phone like ? and state = '0' ORDER BY  UPDATE_TIME desc");
            String hql = " SELECT COUNT(*) FROM ( " + sql + " )";
            pageForm = baseBeanService.getPageFormBySQL((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 40 : pageSize), sql.toString(), hql, new String[]{account.getStaffID(), "%" + phone + "%"});

        }
        return pageForm;
    }

    @Transactional
    @Override
    public String submitAuditOpinion(String auditOpinion, String auditStatus, String id) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        TrilateralData trilateralData = selTrilateralDataBy(id);
        trilateralData.setAudit1("1");
        baseBeanService.update(trilateralData);
        TrilateralDataAudit audit = (TrilateralDataAudit) baseBeanDao.getBeanByHqlAndParams("from TrilateralDataAudit where trilateralDataId = ? AND updateTime IS NULL \n" +
                "ORDER BY auditTime DESC \n" +
                "LIMIT 1", new Object[]{id});

        if (audit != null) {
            audit.setTrilateralDataId(id);
            audit.setAuditStaffId(account.getStaffID());
            audit.setAuditStaffName(account.getStaffName());
            audit.setAuditOpinion(auditOpinion);
            if (auditStatus.equals("同意")) {
                audit.setAuditStatus("1");
            } else {
                audit.setAuditStatus("0");
            }
            audit.setUpdateTime(getTime());
            baseBeanService.update(audit);
            return "审核成功";
        } else {
            return "暂无审核数据";
        }
    }

    @Override
    public List<BaseBean> auditResult(String id) {
        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams("from TrilateralDataAudit where trilateralDataId = ? ORDER BY auditTime DESC", new Object[]{id});
        return list;
    }

    private String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA));
    }

    public String createDoc(String realpath, String companyID, String staffID, String contractTypes, TrilateralData trilateralData, String contractSource) {
        List<BaseBean> beans = new ArrayList<BaseBean>();
        String[] contractType = null;
        if (contractTypes != null && !contractTypes.equals("")) {
            contractType = contractTypes.split(",");//最新的合同协议
        }

        String hqlall = "from DocTrilateral f where f.trilateralId = ? and f.companyID = ? and f.status = ?";
        List<String> strsList = new ArrayList<String>();
        List<BaseBean> listdoc = baseBeanDao.getListBeanByHqlAndParams(hqlall, new Object[]{trilateralData.getId(), companyID, "00"});

        if (listdoc.size() == 0) {
            //说明没有生成过合同，不管是新增还是修改

        } else {
            //说明有合同
            //有合同就删除
            for (int j = 0; j < listdoc.size(); j++) {

                strsList.add(((DocTrilateral) listdoc.get(j)).getContractType());
            }
        }


        String hql = "from DocumentTemplate where companyId = ? and contractType in (";
        List<String> strsList2 = new ArrayList<String>();
        if (contractTypes != null && !contractTypes.equals("")) {

            List<Object> params = new ArrayList<Object>();
            boolean contractBool = false;
            params.add(companyID);
            for (int i = 0; i < contractType.length; i++) {
                strsList2.add(contractType[i]);
                if (!strsList.contains(contractType[i])) {
                    hql += "?,";
                    params.add(contractType[i]);
                    contractBool = true;
                }
            }
            hql = hql.substring(0, hql.length() - 1);
            hql += ")";

            String storepath = "document/" + companyID;
            String officePath = "";
            List<BaseBean> dtlist = new ArrayList<>();
            if (contractBool) {
                dtlist = baseBeanDao.getListBeanByHqlAndParams(hql, params.toArray());
            }
            for (int j = 0; j < dtlist.size(); j++) {

                DocumentTemplate dt = (DocumentTemplate) dtlist.get(j);

                officePath = zOfficeService.createOffice(realpath, dt.getFileType(), dt.getTemplatePath(), storepath);

                Document document = new Document();

                document.setTitle(trilateralData.getName() + trilateralData.getPhone() + "的" + dt.getFileShowName());
                CAccount account = new CAccount();
                account.setCompanyID(companyID);
                account.setStaffID(staffID);
                String checkOrgID = contractService.getCheckOrg(staffID, companyID);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("organizationID", checkOrgID);
                map.put("module", dt.getModule());

                Document doc = storeDocApp(document, account, map);
                DocumentFileAttach docFileAttach = new DocumentFileAttach();
                docFileAttach.setFileType(dt.getFileType());
                docFileAttach.setFilePath(officePath);
                storeAttachDocApp(doc, docFileAttach, staffID, realpath);


                DocTrilateral docTrilateral = new DocTrilateral();
                docTrilateral.setId(serverService.getServerID("id"));
                docTrilateral.setTrilateralId(trilateralData.getId());
                docTrilateral.setCompanyID(companyID);
                docTrilateral.setStaffID(trilateralData.getInputId());
                docTrilateral.setContractType(dt.getContractType());
                docTrilateral.setContractTypeName(dt.getContractTypeName());
                docTrilateral.setDocId(doc.getDocId());
                docTrilateral.setStatus("00");
                docTrilateral.setContractSource(contractSource);
                beans.add(docTrilateral);

            }
        }

        //需要去除没选的
        List<String> hqls = new ArrayList<String>();
        List<Object[]> parms = new ArrayList<Object[]>();

        for (int k = 0; k < listdoc.size(); k++) {
            DocTrilateral docStaff = (DocTrilateral) listdoc.get(k);
            if (!strsList2.contains(docStaff.getContractType())) {
                //说明要删除
                hqls.add("update DocTrilateral set status = ? where id = ?");
                parms.add(new Object[]{"99", docStaff.getId()});
                hqls.add("update Document set status = ? where docId = ?");
                parms.add(new Object[]{"d", docStaff.getDocId()});
            }
        }

        baseBeanService.executeHqlsByParamsList(beans, hqls.toArray(new String[]{}), parms);


        return "";
    }

    public Document storeDocApp(Document document, CAccount account, Map<String, Object> session) {


        String hql = "from Staff where staffID = ?";
        String hql2 = "from ContactCompany c where c.ccompanyID = (select cc.ccompanyId from CcomCom cc where cc.comanyId = ?) ";
        try {
            if (document.getDocId() == null || document.getDocId().equals("")) {
                String docId = serverService.getServerID("docId");
                document.setDocId(docId);
                document.setDocNum(getDocNum(account.getCompanyID() == null ? account.getStaffID() : account.getCompanyID(), "docNum"));//公司可能为空
                document.setDrafterID(account.getStaffID());
                document.setCompanyID(account.getCompanyID());
                document.setStatus("I");
                if (document.getModule() == null || document.getModule().equals("")) {
                    document.setModule((String) session.get("module"));
                }
                document.setOrganizationID((String) session.get("organizationID"));
                document.setDeptIDofDrafter(document.getOrganizationID());
                document.setDraftTime(new Date());
                document.setCreateTime(new Date());
                document.setUpdateTime(new Date());
                //生成档案条码
                document.setBarCode(genArchiveBarcode());

                //添加条码扫描数据

                ScanBarCode scanBarCode = new ScanBarCode();
                scanBarCode.setBarcode(document.getBarCode());
                scanBarCode.setId(serverService.getServerID("scanid"));
                scanBarCode.setUrls("ea/androiddoc/ea_scanDoc.jspa");
                baseBeanDao.save(scanBarCode);
                if (account.getCompanyID() != null && !account.getCompanyID().equals("")) {

                    Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{account.getStaffID()});
                    document.setArchiveCustodian(staff.getStaffName());

                    ContactCompany contactCompany = (ContactCompany) baseBeanDao.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID()});

                    if (contactCompany != null && contactCompany.getCompanyAddr() != null) {
                        document.setArchiveSite(contactCompany.getCompanyAddr());
                    }

                }
                baseBeanDao.save(document);


            } else {
                Document doc = (Document) baseBeanDao.getBeanByHqlAndParams("from Document where docId = ?", new Object[]{document.getDocId()});

                doc.setDraftTime(new Date());
                doc.setTitle(document.getTitle());
                doc.setTheme(document.getTheme());
                doc.setEmergencyType(document.getEmergencyType());
                doc.setDocType(document.getDocType());
                doc.setNumWord(document.getNumWord());
                doc.setNumTime(document.getNumTime());
                doc.setDraftTime(new Date());
                doc.setUpdateTime(new Date());
                doc.setPartyAName(document.getPartyAName());
                doc.setPartyA(document.getPartyA());
                doc.setPartyAstaff(document.getPartyAstaff());
                doc.setPartyAstaffnames(document.getPartyAstaffnames());
                doc.setStaffIdentityCardA(document.getStaffIdentityCardA());

                doc.setPartyBName(document.getPartyBName());
                doc.setPartyB(document.getPartyB());
                doc.setPartyBstaff(document.getPartyBstaff());
                doc.setPartyBstaffnames(document.getPartyBstaffnames());
                doc.setStaffIdentityCardA(document.getStaffIdentityCardB());

                doc.setStartValidity(document.getStartValidity());
                doc.setEndValidity(document.getEndValidity());

                if (doc.getBarCode() == null || doc.getBarCode().equals("")) {
                    doc.setBarCode(genArchiveBarcode());
                }

                if (account.getCompanyID() != null && !account.getCompanyID().equals("")) {
                    if (doc.getArchiveSite() == null || doc.getArchiveSite().equals("")) {

                        if (doc.getArchiveCustodian() == null || doc.getArchiveCustodian().equals("")) {
                            Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{account.getStaffID()});
                            doc.setArchiveCustodian(staff.getStaffName());
                        }
                        ContactCompany contactCompany = (ContactCompany) baseBeanDao.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID()});

                        if (contactCompany != null && contactCompany.getCompanyAddr() != null) {
                            doc.setArchiveSite(contactCompany.getCompanyAddr());
                        }

                    }
                }
                baseBeanDao.update(doc);
            }


        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return document;
    }

    public void storeAttachDocApp(Document document, DocumentFileAttach docFileAttach, String staffID, String realPath) {

        try {

            if (docFileAttach.getFileId() == null || docFileAttach.getFileId().equals("")) {
                docFileAttach.setFileId(serverService.getServerID("fileId"));
                String filePath = docFileAttach.getFilePath();
                String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
                String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
                docFileAttach.setFileName(fileName);
                docFileAttach.setExt(ext);
                docFileAttach.setCreatetime(new Date());
                docFileAttach.setCreator(staffID);
                docFileAttach.setFileShowName(document.getTitle());
                docFileAttach.setDocument(document);


                String pdfPath = filePath.substring(0, filePath.lastIndexOf(".")) + ".pdf";

                document.setPdfUrl(pdfPath);
                baseBeanDao.update(document);
                baseBeanDao.save(docFileAttach);

                if (!"P".equals(docFileAttach.getFileType())) {
                    new Thread(new Runnable() {
                        public void run() {
                            Office2PdfUtil.office2Pdf(realPath + docFileAttach.getFilePath(), realPath + pdfPath);

                        }
                    }).start();

                }
            } else {
                DocumentFileAttach documentFileAttach = (DocumentFileAttach) baseBeanDao.getBeanByHqlAndParams("from DocumentFileAttach where fileId = ?", new Object[]{docFileAttach.getFileId()});

                documentFileAttach.setFileShowName(document.getTitle());

                if (!"P".equals(documentFileAttach.getFileType())) {
                    String filePath = documentFileAttach.getFilePath();
                    String pdfPath = filePath.substring(0, filePath.lastIndexOf(".")) + ".pdf";

                    File file = new File(realPath + filePath);
                    long time = file.lastModified();//当前

                    long newtime = time / 1000L * 1000L;
                    Date date = documentFileAttach.getCreatetime();

                    long times = date.getTime();
                    if (newtime > times) {
                        //说明修改过 改过就重新生成
                        new Thread(new Runnable() {
                            public void run() {
                                Office2PdfUtil.office2Pdf(realPath + filePath, realPath + pdfPath);

                            }
                        }).start();

                        documentFileAttach.setCreatetime(new Date());
                    }


                }
                baseBeanDao.update(documentFileAttach);
            }

        } catch (Exception e) {
            logger.error("操作异常", e);
        }

    }

    public String getDocNum(String companyID, String type) {
        String hql = "from Document where companyID= ? and status!= 'D' ";
        String sql = "";
        if (type == "docNum" || type.equalsIgnoreCase("docNum")) {
            if (companyID.indexOf("staff") != -1) {
                sql = " select max(to_number(docNum)) from DT_OA_DOCUMENT where drafterID= ? and companyID is null and status!='D' ";
            } else {
                sql = " select max(to_number(docNum)) from DT_OA_DOCUMENT where companyID= ? and status!='D' ";

            }
        } else {
            if (companyID.indexOf("staff") != -1) {
                sql = " select max(to_number(numCode)) from DT_OA_DOCUMENT where drafterID= ? and companyID is null and status!='D' ";
            } else {
                sql = " select max(to_number(numCode)) from DT_OA_DOCUMENT where companyID= ? and status!='D' ";

            }
        }
        List<BaseBean> list = (List<BaseBean>) baseBeanDao.getListBeanByHqlAndParams(hql, new Object[]{companyID});
        if (type == "docNum" || type.equalsIgnoreCase("docNum")) {
            if (list.size() == 0) {
                return "0001";
            } else {
                int a = baseBeanDao.getConutByBySqlAndParams(sql, new Object[]{companyID});
                int nextnum = a + 1;
                String s2 = "";
                if (nextnum > 0 && nextnum <= 9) {
                    s2 = "000" + nextnum;
                } else if (nextnum > 9 && nextnum <= 99) {
                    s2 = "00" + nextnum;
                } else if (nextnum > 99 && nextnum <= 999) {
                    s2 = "0" + nextnum;
                } else if (nextnum > 999) {
                    s2 = "" + nextnum;
                }
                return s2;
            }
        } else {
            boolean bol = true;
            for (BaseBean lis : list) {
                Document d = (Document) lis;
                if (d.getNumCode() != null) bol = false;

            }
            if (bol == false) {
                int a = baseBeanDao.getConutByBySqlAndParams(sql, new Object[]{companyID});
                int nextnum = a + 1;
                String s2 = "";
                if (nextnum > 0 && nextnum <= 9) {
                    s2 = "000" + nextnum;
                } else if (nextnum > 9 && nextnum <= 99) {
                    s2 = "00" + nextnum;
                } else if (nextnum > 99 && nextnum <= 999) {
                    s2 = "0" + nextnum;
                } else if (nextnum > 999) {
                    s2 = "" + nextnum;
                }
                return s2;
            } else {
                return "0001";
            }

        }
    }

    public String genArchiveBarcode() {

        DocBarCode docBarCode = new DocBarCode();
        docBarCode.setId(serverService.getServerID("barcode"));
        docBarCode.setCreateDate(new Date());


        String sql = " select max(to_number(seq)) from DT_OA_DocBarCode";
        String seq = "00001";

        try {
            int a = baseBeanDao.getConutByBySqlAndParams(sql, null);

            if (a >= 1) {
                int nextnum = a + 1;
                if (nextnum > 0 && nextnum <= 9) {
                    seq = "0000" + nextnum;
                } else if (nextnum > 9 && nextnum <= 99) {
                    seq = "000" + nextnum;
                } else if (nextnum > 99 && nextnum <= 999) {
                    seq = "00" + nextnum;
                } else if (nextnum > 999 && nextnum <= 9999) {
                    seq = "0" + nextnum;
                } else if (nextnum > 9999) {
                    seq = "" + nextnum;
                }
            }
        } catch (Exception e) {

        }
        docBarCode.setSeq(seq);

        docBarCode.setBarcode(Utilities.getDateString(new Date(), "yyyyMMdd") + seq);
        baseBeanDao.save(docBarCode);

        return docBarCode.getBarcode();
    }

    @Transactional
    public void saveTrilateralContract(TrilateralData trilateralData, String photoPath) {
        String hql = "from TrilateralContract where trilateralDataId = ?";
        TrilateralContract tc = (TrilateralContract) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{trilateralData.getId()});
        HttpSession session = ServletActionContext.getRequest().getSession();
        CAccount account = (CAccount) session.getAttribute("account");

        if (tc == null) {
            // 创建全新的实例，而不是从外部获取
            TrilateralContract trilateralContract = new TrilateralContract();
            trilateralContract.setTemplateId(serverService.getServerID("temp"));
            trilateralContract.setTemplatePath(photoPath);
//            trilateralContract.setKey(UUID.randomUUID().toString().replaceAll("-", ""));
            //三方id
            trilateralContract.setTrilateralDataId(trilateralData.getId());
            trilateralContract.setCompanyId(account.getCompanyID());
            trilateralContract.setCreaterID(trilateralData.getStaffId());
            trilateralContract.setFileShowName(trilateralData.getAttachmentAddressName());
            trilateralContract.setTime(new Date());

            if (photoPath != null) { // 添加空值检查
                String templatePath = photoPath;
                trilateralContract.setExt(templatePath.substring(templatePath.lastIndexOf(".") + 1));
                trilateralContract.setFileSaveName(templatePath.substring(templatePath.lastIndexOf("/") + 1));
            }
            baseBeanService.save(trilateralContract);
        } else {
            tc.setFileShowName(trilateralData.getAttachmentAddressName());
            tc.setTemplatePath(photoPath);
            tc.setCreaterID(trilateralData.getStaffId());
            tc.setCompanyId(account.getCompanyID());
            tc.setTime(new Date());
            if (photoPath != null) { // 添加空值检查
                String templatePath = photoPath;
                tc.setExt(templatePath.substring(templatePath.lastIndexOf(".") + 1));
                tc.setFileSaveName(templatePath.substring(templatePath.lastIndexOf("/") + 1));
            }
            baseBeanService.update(tc); // 使用update而非save
        }
    }
}
