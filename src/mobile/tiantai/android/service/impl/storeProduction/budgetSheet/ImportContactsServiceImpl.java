package mobile.tiantai.android.service.impl.storeProduction.budgetSheet;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.human.Staff;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.action.storeProduction.budgetSheet.AllocationContacts;
import mobile.tiantai.android.action.storeProduction.budgetSheet.ImportContactsDto;
import mobile.tiantai.android.bo.ImportContacts;
import mobile.tiantai.android.bo.StaffSend;
import mobile.tiantai.android.service.storeProduction.budgetSheet.ImportContactsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * ImportContactsServiceImpl
 *
 * @author zch
 */
@Service
public class ImportContactsServiceImpl implements ImportContactsService {
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private BaseBeanService baseBeanService;
    private List<BaseBean> beans;
    @Resource
    private ServerService serverService;

    @Transactional
    @Override
    public String importContacts(List<ImportContactsDto> importContactsDto) {
        SessionWrap sw = SessionWrap.getInstance();
        Map<String, Object> session1 = ActionContext.getContext().getSession();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session1,
                SessionWrap.KEY_SHOPCUSCOM);
        for (ImportContactsDto importContactsDto1 : importContactsDto) {
            addData1(importContactsDto1);
        }

        return "success";
    }

    @Transactional
    @Override
    public PageForm selectImportContactsList(Integer pageNumber, Integer pageSize) {
        SessionWrap sw = SessionWrap.getInstance();
        Map<String, Object> session1 = ActionContext.getContext().getSession();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session1,
                SessionWrap.KEY_SHOPCUSCOM);
        StringBuffer sql = new StringBuffer(128);
        sql.append("\n" +
                "SELECT a.STAFFID,a.STAFFNAME,a.REFERENCE,a.SCCID\n" +
                "FROM DT_HR_STAFF a\n" +
                "WHERE NOT EXISTS (\n" +
                "    SELECT 1 \n" +
                "    FROM IMPORT_CONTACTS b \n" +
                "    WHERE a.STAFFID = b.STAFF_ID " +
                ") AND a.SCCID = ? AND a.VIPNO = ? and staffStatus !='98' ORDER BY a.VERIFYTIME DESC");
        String hql2 = " SELECT COUNT(*) FROM ( " + sql + " )";
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql2, new String[]{tc.getSccId(), "1"});
        return pageForm;
    }

    @Transactional
    @Override
    public String deleteImportContacts(String id) {
        Staff staff = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ?",
                new Object[]{id});
        staff.setStaffStatus("98");
        beandao.update(staff);
        return "success";
    }

    @Transactional
    @Override
    public String updateImportContacts(String id, String name, String phone) {
        Staff staff = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ?",
                new Object[]{id});
        staff.setStaffName(name);
        staff.setReference(phone);
        staff.setVerifyTime(new Date());
        beandao.update(staff);
        return "success";
    }

    @Transactional
    @Override
    public String allocationImportContacts(List<StaffSend> staffSendList, List<AllocationContacts> allocationContacts) {
        List<BaseBean> importContactsList = new ArrayList<>();
        for (StaffSend staffSend : staffSendList) {
            for (AllocationContacts allocationContact : allocationContacts) {
                ImportContacts importContacts = new ImportContacts();
                importContacts.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                importContacts.setStaffId(allocationContact.getStaffId());
                importContacts.setName(allocationContact.getName());
                importContacts.setPhone(allocationContact.getPhone());
                importContacts.setIsCall("0");
                importContacts.setImporterId(allocationContact.getSccId());
                importContacts.setAssignTo(staffSend.getStaffId());
                importContacts.setIsAssign("1");
                importContacts.setImportTime(getTime());
                importContacts.setUpdateTime(getTime());
                importContactsList.add(importContacts);
            }

        }
        beandao.executeHqlsByParmsList(importContactsList, null, null);
        return "success";
    }

    @Transactional
    @Override
    public PageForm selectImportContacts(Integer pageNumber, Integer pageSize) {
        SessionWrap sw = SessionWrap.getInstance();
        Map<String, Object> session1 = ActionContext.getContext().getSession();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session1,
                SessionWrap.KEY_SHOPCUSCOM);
        StringBuffer sql = new StringBuffer(128);
        sql.append("SELECT * FROM \"IMPORT_CONTACTS\" WHERE ASSIGN_TO = ? and  IS_CALL != '1'");
        String hql2 = " SELECT COUNT(*) FROM ( " + sql + " )";
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql2, new String[]{tc.getStaffid()});
        return pageForm;
    }

    @Transactional
    public String addData1(ImportContactsDto importContactsDto) {
        List<BaseBean> andParams = beandao.getListBeanByHqlAndParams("from Staff where reference = ? and staffStatus !='98' ", new Object[]{importContactsDto.getPhone()});
        if (andParams.isEmpty()) {
            SessionWrap sw = SessionWrap.getInstance();
            Map<String, Object> session1 = ActionContext.getContext().getSession();
            TEshopCusCom tc = (TEshopCusCom) sw.getObject(session1,
                    SessionWrap.KEY_SHOPCUSCOM);
            Staff staff = new Staff();
            beans = new ArrayList<>();
            staff.setSccid(tc.getSccId());
            //姓名
            staff.setStaffName(importContactsDto.getName());
            //电话
            staff.setReference(importContactsDto.getPhone());
            //身份证
            staff.setStaffIdentityCard(importContactsDto.getCardNumber());
            staff.setVipno("1");
            String flag = saveStaffBaseData(staff);
            if (!flag.equals("success")) {
                return flag;
            }
            baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        }
        return "success";
    }

    private String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA));
    }

    private String saveStaffBaseData(Staff cstaff) {
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
