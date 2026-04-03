package mobile.tiantai.android.service.impl.storeProduction.budgetSheet;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.finance.BenDis.BonusPoints;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import mobile.tiantai.android.action.SDKTestSendTemplateSMS;
import mobile.tiantai.android.action.storeProduction.budgetSheet.IntendedCustomersDto;
import mobile.tiantai.android.action.storeProduction.budgetSheet.TemplateDto;
import mobile.tiantai.android.bo.CRMShortMessagingTemplatePO;
import mobile.tiantai.android.bo.ImportContacts;
import mobile.tiantai.android.bo.IntendedCustomers;
import mobile.tiantai.android.bo.StaffSend;
import mobile.tiantai.android.service.storeProduction.budgetSheet.CRMShortMessagingTemplatePOServer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * CRMShortMessagingTemplatePOServerImpl
 *
 * @author zch
 */
@Service
public class CRMShortMessagingTemplatePOServerImpl implements CRMShortMessagingTemplatePOServer {

    @Resource
    private BaseBeanDao beandao;
    @Resource
    private BaseBeanService baseBeanService;
    private SDKTestSendTemplateSMS sdk = new SDKTestSendTemplateSMS();

    @Transactional
    @Override
    public String saveTemplate(TemplateDto templateDtoList) {
        CRMShortMessagingTemplatePO crmShortMessagingTemplatePO = new CRMShortMessagingTemplatePO();
        crmShortMessagingTemplatePO.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        crmShortMessagingTemplatePO.setTemplateType(templateDtoList.getTemplateType());
        crmShortMessagingTemplatePO.setTemplateHeadline(templateDtoList.getTemplateHeadline());
        crmShortMessagingTemplatePO.setTemplateText(templateDtoList.getTemplateText());
        //T_ESHOP_CUSTOMER
        //获取当前登录人
        SessionWrap sw = SessionWrap.getInstance();
        Map<String, Object> session1 = ActionContext.getContext().getSession();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session1,
                SessionWrap.KEY_SHOPCUSCOM);
        crmShortMessagingTemplatePO.setCustomerKey(tc.getSccId());
        crmShortMessagingTemplatePO.setCreatedAt(getTime());
        crmShortMessagingTemplatePO.setUpdatedAt(getTime());
        // 短信模板审核状态 0：未提交审核  1：审核中  3：审核驳回  4：审核通过
        crmShortMessagingTemplatePO.setAuditStatus(4);
        // 冻结状态， 0 表示未冻结， 1 表示冻结
        crmShortMessagingTemplatePO.setFreezeStatus(0);
        if (!StringUtils.isNotEmpty(templateDtoList.getMoney())) {
            templateDtoList.setMoney("0");
        }
        crmShortMessagingTemplatePO.setMoney(templateDtoList.getMoney());
        baseBeanService.save(crmShortMessagingTemplatePO);
        return "success";
    }

    @Transactional
    @Override
    public PageForm getTemplate(Integer pageNumber, Integer pageSize) {
        StringBuffer sql = new StringBuffer(128);
        SessionWrap sw = SessionWrap.getInstance();
        Map<String, Object> session1 = ActionContext.getContext().getSession();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session1, SessionWrap.KEY_SHOPCUSCOM);

//        sql.append("SELECT * FROM T_CRM_SMS_TEMPLATE WHERE CUSTOMER_KEY = ? AND AUDIT_STATUS = 4 AND FREEZE_STATUS = 0 ORDER BY UPDATED_AT DESC");
        sql.append("SELECT * FROM T_CRM_SMS_TEMPLATE WHERE  AUDIT_STATUS = 4 AND FREEZE_STATUS = 0 ORDER BY UPDATED_AT DESC");
        String hql2 = "SELECT COUNT(*) FROM ( " + sql + " )";

//        String[] params = {tc.getSccId()};
        String[] params = {};
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1),
                (pageSize == 0 ? 20 : pageSize),
                sql.toString(),
                hql2,
                params);
        return pageForm;
    }

    @Transactional
    @Override
    public String deleteTemplate(String id) {
        CRMShortMessagingTemplatePO crmShortMessagingTemplatePO = selectById(id);
        crmShortMessagingTemplatePO.setFreezeStatus(1);
        baseBeanService.update(crmShortMessagingTemplatePO);
        return "success";
    }

    @Transactional
    @Override
    public String updateTemplateDate(CRMShortMessagingTemplatePO old) {
        CRMShortMessagingTemplatePO crmShortMessagingTemplatePO = selectById(old.getId());
        crmShortMessagingTemplatePO.setTemplateType(old.getTemplateType());
        crmShortMessagingTemplatePO.setTemplateHeadline(old.getTemplateHeadline());
        crmShortMessagingTemplatePO.setTemplateText(old.getTemplateText());
        crmShortMessagingTemplatePO.setUpdatedAt(getTime());
        crmShortMessagingTemplatePO.setMoney(old.getMoney());
        baseBeanService.update(crmShortMessagingTemplatePO);
        return "success";
    }

    @Transactional
    @Override
    public CRMShortMessagingTemplatePO selectById(String id) {
        CRMShortMessagingTemplatePO crmShortMessagingTemplatePO = (CRMShortMessagingTemplatePO) beandao.getBeanByKey(CRMShortMessagingTemplatePO.class, id);
        return crmShortMessagingTemplatePO;
    }

    @Transactional
    @Override
    public PageForm selectTemplate(Integer pageNumber, Integer pageSize, String templateHeadline) {
        StringBuffer sql = new StringBuffer(128);
        SessionWrap sw = SessionWrap.getInstance();
        Map<String, Object> session1 = ActionContext.getContext().getSession();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session1, SessionWrap.KEY_SHOPCUSCOM);
        PageForm pageForm = null;
        if (templateHeadline == null || templateHeadline.equals("")) {
            sql.append("SELECT * FROM T_CRM_SMS_TEMPLATE WHERE CUSTOMER_KEY = ? AND AUDIT_STATUS = 4 AND FREEZE_STATUS = 0 ORDER BY UPDATED_AT DESC");
            String hql2 = "SELECT COUNT(*) FROM ( " + sql + " )";
            String[] params = {tc.getSccId()};
            pageForm = baseBeanService.getPageFormBySQL(
                    (null != pageNumber ? pageNumber : 1),
                    (pageSize == 0 ? 20 : pageSize),
                    sql.toString(),
                    hql2,
                    params);
        } else {
            sql.append("SELECT * FROM T_CRM_SMS_TEMPLATE WHERE  TEMPLATE_HEADLINE LIKE ? AND CUSTOMER_KEY = ? AND AUDIT_STATUS = 4 AND FREEZE_STATUS = 0 ORDER BY UPDATED_AT DESC");
            String hql2 = "SELECT COUNT(*) FROM ( " + sql + " )";
            String[] params = {"%" + templateHeadline + "%", tc.getSccId()};
            pageForm = baseBeanService.getPageFormBySQL(
                    (null != pageNumber ? pageNumber : 1),
                    (pageSize == 0 ? 20 : pageSize),
                    sql.toString(),
                    hql2,
                    params);
        }
        return pageForm;
    }

    @Override
    public Map<String, String> getNum() {
        StringBuffer sql = new StringBuffer(128);
        SessionWrap sw = SessionWrap.getInstance();
        Map<String, Object> session1 = ActionContext.getContext().getSession();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session1, SessionWrap.KEY_SHOPCUSCOM);
        sql.append("SELECT COUNT(*) FROM CRMShortMessagingTemplatePO p WHERE customerKey = ? and auditStatus = 4 AND freezeStatus = 0 AND (money IS null OR money = '0')");
        String[] params = {tc.getSccId()};
        //免费短信
        int num = beandao.getConutByByHqlAndParams(sql + "", params);
        //积分余额
        BonusPoints bp = (BonusPoints) baseBeanService.getBeanByHqlAndParams("from BonusPoints where sccid=? ", new Object[]{tc.getSccId()});
        Map<String, String> map = new HashMap<>();
        map.put("num", num + "");
        if (bp == null) {
            map.put("money", "0");
        } else {
            map.put("money", bp.getBonusPointScore());
        }
        return map;
    }

    @Transactional
    @Override
    public PageForm getCompany(Integer pageNumber, Integer pageSize) {
        StringBuffer sql = new StringBuffer(128);
        SessionWrap sw = SessionWrap.getInstance();
        Map<String, Object> session1 = ActionContext.getContext().getSession();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session1, SessionWrap.KEY_SHOPCUSCOM);
        PageForm pageForm = null;
        String[] params = {};
        sql.append("SELECT * FROM T_CRM_SMS_TEMPLATE ");
        String hql2 = "SELECT COUNT(*) FROM ( " + sql + " )";
        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1),
                (pageSize == 0 ? 20 : pageSize),
                sql.toString(),
                hql2,
                params);
        StringBuffer str = new StringBuffer(512);
        List<String> params1 = new ArrayList<String>();
        List<JSONObject> list = new ArrayList<JSONObject>();
        JSONObject jobj = new JSONObject();

        str.append("select * from(SELECT COM.COMPANYNAME,\n" +
                "       COM.COMPANYID,\n" +
                "       CUS.ACCOUNT,\n" +
                "       CT.LOGOPATH,\n" +
                "       COM.CCOMTYPE,\n" +
                "       CT.COMSCALE,\n" +
                "       CT.INDUSTRYTYPE,\n" +
                "       CT.COMPANYADDR,\n" +
                "       CUS.STAFFID,\n" +
                "       CT.CCOMPANYID,ct.seqs FROM T_ESHOP_CUSCOM_COMPANY CUSC\n" +
                "LEFT JOIN  DTCOMPANY COM ON COM.COMPANYID = CUSC.COMPANYID\n" +
                "LEFT JOIN T_ESHOP_CUSCOM CUS ON CUS.SCCID = CUSC.SCCID\n" +
                "LEFT JOIN  DT_CCOM_COM CCC ON CCC.COMPNAY_ID = COM.COMPANYID\n" +
                "LEFT JOIN  DTCONTACTCOMPANY CT ON  CT.CCOMPANYID = CCC.CCOMPANY_ID\n");
        str.append("WHERE CUSC.SCCID = ? union ");
        str.append(" select com.companyname, com.companyid, cus.account, t.logopath,com.CCOMTYPE,t.comScale,t.industryType,t.companyAddr,cus.staffid,t.ccompanyID ,t.seqs from T_ESHOP_CUSCOM cus, dtCompany com, dt_ccom_com d, dtcontactcompany t where cus.companyid = com.companyid ");
        str.append(" and d.ccompany_id = t.ccompanyid and com.companyid = d.compnay_id and cus.account in (select j.account  from T_ESHOP_CUSCOM j  where j.sccid  = ?) union  select com.companyname, com.companyid, ccus.account, t.logopath,com.CCOMTYPE,t.comScale,t.industryType,t.companyAddr,staff.staffid,ccompanyID ,t.seqs from  dt_ccom_com d, ");
        str.append(" dtCompany com,dtcontactcompany t,dtcos cos,T_ESHOP_CUSCOM cus, dt_hr_Staff staff, T_ESHOP_CUSCOM   ccus where staff.staffid = cos.staffid and staff.reference = cus.account and cos.companyid=com.companyid and cos.status='01' and cos.cosStatus='50' and com.companyid=d.compnay_id ");
        str.append(" and d.ccompany_id=t.ccompanyid  and com.companyid = ccus.companyid and cus.sccid = ?");
        str.append(") sc order by to_number(sc.seqs)");
        params1.add(tc.getSccId());
        params1.add(tc.getSccId());
        params1.add(tc.getSccId());
        List<BaseBean> beanlist2 = new ArrayList<BaseBean>();
        List<String> lists = new ArrayList<String>();
        List<BaseBean> beanlist1 = baseBeanService.getListBeanBySqlAndParams(str.toString(), params1.toArray());
        for (int i = 0; i < beanlist1.size(); i++) {
            Object[] obj3 = (Object[]) (Object) beanlist1.get(i);
            if (!lists.contains(isNull(obj3[1]))) {
                if (isNull(obj3[4]).equals("0")) {
                    obj3[4] = "大型企业";
                } else if (isNull(obj3[4]).equals("1")) {
                    obj3[4] = "中型企业";
                } else if (isNull(obj3[4]).equals("2")) {
                    obj3[4] = "小型企业";
                } else if (isNull(obj3[4]).equals("3")) {
                    obj3[4] = "微型企业";
                } else if (isNull(obj3[4]).equals("4")) {
                    obj3[4] = "小微型企业";
                } else if (isNull(obj3[4]).equals("5")) {
                    obj3[4] = "供应商企业";
                }
                JSONObject jsonObj = new JSONObject();
                jsonObj.accumulate("companyname", isNull(obj3[0]));
                jsonObj.accumulate("companyid", isNull(obj3[1]));
                jsonObj.accumulate("account", isNull(obj3[2]));
                jsonObj.accumulate("logopath", isNull(obj3[3]));
                jsonObj.accumulate("hxID", isNull(obj3[4]));
                jsonObj.accumulate("comScale", isNull(obj3[5]));
                jsonObj.accumulate("industryType", isNull(obj3[6]));
                jsonObj.accumulate("companyAddr", isNull(obj3[7]));
                jsonObj.accumulate("staffID", isNull(obj3[8]));
                jsonObj.accumulate("ccompanyID", isNull(obj3[9]));
                lists.add(isNull(obj3[1]).toString());
                list.add(jsonObj);
                beanlist2.add(beanlist1.get(i));
            }
        }
        jobj.accumulate("company", list);
        pageForm.setList(beanlist2);
        return pageForm;
    }

    @Transactional
    @Override
    public PageForm getStaffList(String companyId, Integer pageNumber, Integer pageSize) {
        StringBuffer sql = new StringBuffer(128);
        sql.append("SELECT S.STAFFID,S.STAFFCODE,S.STAFFNAME,S.SEX,O.ORGANIZATIONNAME,S.REFERENCE,")
                .append(" C.COSID ,C.COMPANYID FROM DT_HR_STAFF S")
                .append(" LEFT JOIN DTCOS C ON  C.STAFFID = S.STAFFID")
                .append(" LEFT JOIN DTCORGANIZATION O ON C.ORGANIZATIONID = O.ORGANIZATIONID")
                .append("  WHERE  C.COSSTATUS = '50'AND C.COMPANYID=?")
                .append(" AND  C.STATUS = '01'");

        String hql2 = " SELECT COUNT(*) FROM (" + sql + ")";
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql2, new String[]{companyId});
        return pageForm;
    }

    @Transactional
    @Override
    public PageForm getStaffListByName(String staffName, String companyId, Integer pageNumber, Integer pageSize) {
        StringBuffer sql = new StringBuffer(128);
        sql.append("SELECT S.STAFFID,S.STAFFCODE,S.STAFFNAME,S.SEX,O.ORGANIZATIONNAME,S.REFERENCE,")
                .append(" C.COSID FROM DT_HR_STAFF S")
                .append(" LEFT JOIN DTCOS C ON  C.STAFFID = S.STAFFID")
                .append(" LEFT JOIN DTCORGANIZATION O ON C.ORGANIZATIONID = O.ORGANIZATIONID")
                .append("  WHERE S.STAFFNAME LIKE ?")
                .append(" AND  C.COSSTATUS = '50'AND C.COMPANYID=?")
                .append(" AND C.STATUS = '01'");
        String hql2 = " SELECT COUNT(*) FROM (" + sql + ")";
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql2, new String[]{"%" + staffName + "%", companyId});
        return pageForm;
    }

    @Transactional
    @Override
    public String send(String localTemplateText, String firstName, List<StaffSend> StaffSendList) {
        int i = 0;
        for (StaffSend staffSend : StaffSendList) {
            if (StringUtils.isNotBlank(staffSend.getPhone()) && staffSend.getPhone().length() == 11) {
                String s = sendSms(staffSend.getPhone(), localTemplateText);
                if (s.equals("success")) {
                    i++;
                    StaffSend staffSendOld = (StaffSend) beandao.getBeanByHqlAndParams("from StaffSend where staffId=?", new Object[]{staffSend.getStaffId()});
                    if (staffSendOld != null) {
                        staffSendOld.setSendNum((Integer.parseInt(staffSendOld.getSendNum()) + 1) + "");
                        beandao.update(staffSendOld);
                    } else {
                        staffSend.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                        staffSend.setSendNum(1 + "");
                        beandao.save(staffSend);
                    }
                }
            }
        }
        BigDecimal sum = new BigDecimal(firstName).multiply(new BigDecimal(i));
        SessionWrap sw = SessionWrap.getInstance();
        Map<String, Object> session1 = ActionContext.getContext().getSession();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session1, SessionWrap.KEY_SHOPCUSCOM);
        //积分余额
        BonusPoints bp = (BonusPoints) baseBeanService.getBeanByHqlAndParams("from BonusPoints where sccid=? ", new Object[]{tc.getSccId()});
        if (bp != null) {
            if (new BigDecimal(bp.getBonusPointScore()).subtract(sum).compareTo(new BigDecimal(0)) == 1) {
                String s = new BigDecimal(bp.getBonusPointScore()).subtract(sum) + "";
                bp.setBonusPointScore(s);
                beandao.update(bp);
                return "发送成功";
            } else {
                return "发送失败,积分余额不足,请充值";
            }
        }
        return "发送失败,积分余额不足,请充值";
    }

    @Transactional
    @Override
    public List<String> getCategoryList(String companyId) {
        List<String> list = new ArrayList<String>();
        List<BaseBean> andParams = baseBeanService.getListBeanByHqlAndParams("SELECT DISTINCT relation  FROM ContactRelation WHERE companyID = ? AND relation NOT LIKE '%请%'", new Object[]{companyId});
        JSONArray arr = JSONArray.fromObject(andParams);
        list.add("全部");
        for (int i = 0; i < arr.size(); i++) {
            list.add(arr.get(i).toString());
        }
        return list;
    }

    @Transactional
    @Override
    public PageForm getStaffListByRelation(String relation, String companyId, Integer pageNumber, Integer pageSize) {
        if (relation.equals("全部")) {
            return getStaffList(companyId, pageNumber, pageSize);
        } else {
            PageForm pageForm = null;
            StringBuffer sql = new StringBuffer(128);
            sql.append("  SELECT a.*\n" +
                    "    FROM (\n" +
                    "        SELECT S.STAFFID, S.STAFFCODE, S.STAFFNAME, S.SEX, O.ORGANIZATIONNAME, S.REFERENCE, \n" +
                    "               C.COSID \n" +
                    "        FROM DT_HR_STAFF S \n" +
                    "        LEFT JOIN DTCOS C ON C.STAFFID = S.STAFFID \n" +
                    "        LEFT JOIN DTCORGANIZATION O ON C.ORGANIZATIONID = O.ORGANIZATIONID \n" +
                    "          AND C.COSSTATUS = '50' \n" +
                    "          AND C.COMPANYID = ? \n" +
                    "          AND C.STATUS = '01'\n" +
                    "    ) a\n" +
                    "    INNER JOIN (\n" +
                    "        SELECT STAFFID \n" +
                    "        FROM DTCONTACTRELATION \n" +
                    "        WHERE COMPANYID = ? \n" +
                    "          AND RELATION = ?\n" +
                    "    ) b \n" +
                    "    ON a.STAFFID = b.STAFFID");
            String hql2 = " SELECT COUNT(*) FROM (" + sql + ")";
            pageForm = baseBeanService.getPageFormBySQL(
                    (null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql2, new String[]{companyId, companyId, relation});
            return pageForm;
        }
    }

    @Transactional
    @Override
    public PageForm getSendStaffList(String isSend, String companyId, Integer pageNumber, Integer pageSize) {
        PageForm pageForm = null;
        StringBuffer sql = new StringBuffer(128);
        String hql2 = "";
        if (isSend.equals("已发送短信")) {
            sql.append("SELECT a.* FROM\n" +
                    "(SELECT S.STAFFID,S.STAFFCODE,S.STAFFNAME,S.SEX,O.ORGANIZATIONNAME,S.REFERENCE, C.COSID FROM DT_HR_STAFF S LEFT JOIN DTCOS C ON  C.STAFFID = S.STAFFID LEFT JOIN DTCORGANIZATION O ON C.ORGANIZATIONID = O.ORGANIZATIONID  WHERE  C.COSSTATUS = '50'AND C.COMPANYID=? AND  C.STATUS = '01')a,STAFF_SEND b WHERE a.STAFFID = b.STAFF_ID");
            hql2 = " SELECT COUNT(*) FROM (" + sql + ")";
        } else {
            sql.append("SELECT a.*\n" +
                    "FROM (SELECT S.STAFFID,S.STAFFCODE,S.STAFFNAME,S.SEX,O.ORGANIZATIONNAME,S.REFERENCE, C.COSID FROM DT_HR_STAFF S LEFT JOIN DTCOS C ON  C.STAFFID = S.STAFFID LEFT JOIN DTCORGANIZATION O ON C.ORGANIZATIONID = O.ORGANIZATIONID  WHERE  C.COSSTATUS = '50'AND C.COMPANYID=? AND  C.STATUS = '01')a\n" +
                    "WHERE a.STAFFID NOT IN (SELECT STAFF_ID FROM STAFF_SEND)");
            hql2 = " SELECT COUNT(*) FROM (" + sql + ")";
        }
        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql2, new String[]{companyId});
        return pageForm;
    }

    @Transactional
    @Override
    public String saveIntendedCustomers(String type1, IntendedCustomersDto intendedCustomersList) {
        SessionWrap sw = SessionWrap.getInstance();
        Map<String, Object> session1 = ActionContext.getContext().getSession();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session1, SessionWrap.KEY_SHOPCUSCOM);
        IntendedCustomers ic = new IntendedCustomers();
        BeanUtils.copyProperties(intendedCustomersList, ic);
        ic.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        ic.setCreatTime(getTime());
        ic.setType1(type1);
        ic.setUpdateTime(getTime());
        ic.setInputPerson(tc.getSccId());
        beandao.save(ic);
        if (type1.equals("已接通")) {
            ImportContacts importContacts = (ImportContacts) baseBeanService.getBeanByHqlAndParams("from ImportContacts where staffId = ? and phone = ?", new Object[]{intendedCustomersList.getStaffId(), intendedCustomersList.getPhone()});
            if (importContacts != null) {
                importContacts.setIsCall("1");
                beandao.update(importContacts);
            }
        }

        return "success";
    }

    @Transactional
    @Override
    public PageForm getIntendedCustomersList(String typeValue, String sccId, Integer pageNumber, Integer pageSize) {
        String type = "";
        if (typeValue.equals("拨通记录")) {
            type = "已接通";
        }
        if (typeValue.equals("未通电话")) {
            type = "未接通";
        }
        if (typeValue.equals("意向客户")) {
            type = "已接通,是意向客户";
        }
        PageForm pageForm = null;
        StringBuffer sql = new StringBuffer(128);
        String hql2 = "";
        sql.append("SELECT * FROM INTENDED_CUSTOMERS WHERE INPUT_PERSON = ? AND  type1 LIKE ?  ORDER BY UPDATE_TIME DESC");
        hql2 = " SELECT COUNT(*) FROM (" + sql + ")";
        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql2, new String[]{sccId, "%" + type + "%"});
        return pageForm;
    }


    /**
     * 获取时间
     *
     * @return
     */
    private String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA));
    }

    private Object isNull(Object tep) {
        if (tep == null || "null".equals(tep)) {
            return "";
        } else {
            return tep;
        }
    }

    //阿里云短信发送
    private String sendSms(String phoneNumber, String localTemplateText) {
        //暂时注掉
        //sdk.getduan("18336475516", localTemplateText);
        return "success";
    }
}
