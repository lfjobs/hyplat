package hy.ea.office.service.impl;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.salary.SalaryData;
import hy.ea.office.bo.CheckOnSetup;
import hy.ea.office.service.CheckOnSetupService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class CheckOnSetupServiceImpl implements CheckOnSetupService {
    @Resource
    private BaseBeanService baseBeanService;
    @Override
    public String findCheckOnSetupList(PageForm pageForm) {
        pageForm = getList(pageForm);
        if(Objects.nonNull(pageForm)){
            List<BaseBean> checkOnSetupList = new ArrayList<>();
            List<BaseBean> objectList = pageForm.getList();
            if(Objects.nonNull(objectList) && !objectList.isEmpty()){
                for (Object obj : objectList) {
                    Object[] arr = (Object[]) obj;
                    CheckOnSetup checkOnSetup = new CheckOnSetup(arr[0].toString(),arr[1].toString(),arr[2].toString(),arr[3].toString(),
                            arr[4].toString(),arr[5].toString(),arr[6].toString(),arr[7].toString(),0D,
                            Double.valueOf(arr[9].toString()), arr[10].toString(),arr[11].toString());
                    checkOnSetupList.add(checkOnSetup);
                }
                pageForm.setList(checkOnSetupList);
            }

            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("pageForm", pageForm);
            JSONObject obj = JSONObject.fromObject(resultMap);
            return obj.toString();
        }
        return null;
    }

    @Override
    public void addCheckOnSetup(CheckOnSetup checkOnSetup) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return;
        }
        checkOnSetup.setCompanyId(account.getCompanyID());
        checkOnSetup.setStaffId(account.getStaffID());
        baseBeanService.save(checkOnSetup);
    }

    @Override
    public void deleteCheckOnSetupById(String id) {
        CheckOnSetup checkOnSetup =  (CheckOnSetup) baseBeanService
                .getBeanByHqlAndParams("from CheckOnSetup where checkOnSetupId = ?", new Object[]{id});
        if(Objects.nonNull(checkOnSetup)){
            baseBeanService.deleteBeanByKey(CheckOnSetup.class,checkOnSetup.getCheckOnSetupId());
        }
    }

    @Override
    public String findCheckOnSetupById(String id) {
        CheckOnSetup checkOnSetup =  (CheckOnSetup) baseBeanService
                .getBeanByHqlAndParams("from CheckOnSetup where checkOnSetupId = ?", new Object[]{id});
        if(Objects.nonNull(checkOnSetup)){
            String staffId = checkOnSetup.getStaffId();
            if(Objects.nonNull(staffId)){
                Staff staff =  (Staff) baseBeanService
                        .getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staffId});
                checkOnSetup.setStaffName(staff.getStaffName());
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("checkOnSetup", checkOnSetup);
            JSONObject obj = JSONObject.fromObject(map);
            return obj.toString();
        }
        return null;
    }

    @Override
    public String findCurrentUser() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if(Objects.nonNull(account)){
            Staff staff =  (Staff) baseBeanService
                    .getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{account.getStaffID()});
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("staff", staff);
            JSONObject obj = JSONObject.fromObject(map);
            return obj.toString();
        }
        return null;
    }

    @Override
    public String findRankSalary(String rankId) {
        if(Objects.nonNull(rankId)){
            SalaryData salaryData =  (SalaryData) baseBeanService
                    .getBeanByHqlAndParams("from SalaryData where salaryLevelId = ?", new Object[]{rankId});
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("salaryData", salaryData);
            JSONObject obj = JSONObject.fromObject(map);
            return obj.toString();
        }
        return null;
    }

    @Override
    public void updateCheckOnSetup(CheckOnSetup checkOnSetup) {
        CheckOnSetup checkOnSetupOld =  (CheckOnSetup) baseBeanService
                .getBeanByHqlAndParams("from CheckOnSetup where checkOnSetupId = ?", new Object[]{checkOnSetup.getCheckOnSetupId()});
        if(Objects.nonNull(checkOnSetup)){
            Map<String, Object> session = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session.get("account");
            if (account == null) {
                return;
            }
            checkOnSetup.setStaffId(account.getStaffID());
            checkOnSetup.setSetupDate(new Date());
            checkOnSetup.setCompanyId(checkOnSetupOld.getCompanyId());
            baseBeanService.update(checkOnSetup);
        }
    }

    @Override
    public String findCheckOnType() {
        PageForm pageForm =  baseBeanService.getPageForm(1,1000,"from CheckOnType " +
                "order by checkOnTypeSerial",new Object[]{});
        if(Objects.nonNull(pageForm)){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("checkOnType", pageForm);
            JSONObject obj = JSONObject.fromObject(map);
            return obj.toString();
        }
        return null;
    }

    @Override
    public String findSalaryLevelByCompanyID() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.nonNull(account)) {
            PageForm pageForm =  baseBeanService.getPageForm(1,1000,"from HrSalaryLevel where companyId = ?" +
                            " order by salaryLevelNum"
                    ,new Object[]{account.getCompanyID()});
            if(Objects.nonNull(pageForm)){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("salaryLevel", pageForm);
                JSONObject obj = JSONObject.fromObject(map);
                return obj.toString();
            }
        }
        return null;
    }

    public PageForm getList(PageForm pageForm) {
        Object[] param = new Object[]{null};
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.nonNull(account)) {
            if(Objects.nonNull(account.getCompanyID()) && StringUtils.isNotBlank(account.getCompanyID()))
            {
                param[0] = account.getCompanyID();
            }
        }
        String sql = getDataUseSql();
        pageForm = baseBeanService.getPageFormBySQL ((null != pageForm ? pageForm.getPageNumber() : 1),
                (null != pageForm ? pageForm.getPageSize() : 10), sql,"select count(1) "
                + sql.substring(sql.indexOf("FROM")),param);
        return pageForm;
    }

    private String getDataUseSql() {
        String sql = "SELECT C.CHECKON_SETUP_ID checkOnSetupId,C.COMPANY_ID companyId,C.RANK_ID rankId,C.CHECKON_TYPE_ID checkOnTypeId," +
                "C.REFERENCE_BASE_SALARY referenceBaseSalary,C.REFERENCE_GUARANTEE_SALARY referenceGuaranteeSalary," +
                "C.REFERENCE_WELFARE_SALARY referenceWelfareSalary,C.AMOUNT_TYPE amountType,C.CHECKON_AMOUNT checkOnAmount,C.DISCOUNT_RATE discountRate," +
                "S.SALARY_LEVEL_NUM salaryLevelNum,T.CHECKON_TYPE_NAME checkOnTypeName " +
                "FROM DT_CHECKON_SETUP C,DT_HR_SALARY_LEVEL S,DT_CHECKON_TYPE T " +
                "WHERE C.CHECKON_TYPE_ID = T.CHECKON_TYPE_ID " +
                "AND C.RANK_ID = S.SALARY_LEVEL_ID " +
                "AND C.COMPANY_ID = ? " +
                "ORDER BY S.SALARY_LEVEL_SERIAL,T.CHECKON_TYPE_SERIAL,C.AMOUNT_TYPE";
        return sql;
    }
}
