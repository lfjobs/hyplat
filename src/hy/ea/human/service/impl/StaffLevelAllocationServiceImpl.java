package hy.ea.human.service.impl;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.ExaminePersonData;
import hy.ea.bo.ExamineProcessData;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffLevelAllocation;
import hy.ea.human.dao.StaffDao;
import hy.ea.human.dao.StaffLevelAllocationDao;
import hy.ea.human.service.StaffLevelAllocationService;
import hy.ea.human.service.StaffService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Transactional
public class StaffLevelAllocationServiceImpl implements StaffLevelAllocationService {

    @Resource
    private StaffLevelAllocationDao staffLevelAllocationDao;

    @Resource
    private StaffService staffService;

    @Resource
    private ServerService serverService;

    @Resource
    private BaseBeanDao baseBeanDao;

    @Resource
    private StaffDao staffDao;


    @Override
    public PageForm getLevelAllocationList(String companyId,String staffID,Map<String,String> params,Integer pageNumber, Integer pageSize) {
        return staffLevelAllocationDao.getLevelAllocationList(companyId,staffID,params,pageNumber,pageSize);
    }

    @Override
    public StaffLevelAllocation getLevelAllocationById(String levelAllocationId,String companyId) {
        return staffLevelAllocationDao.getLevelAllocationById(levelAllocationId,companyId);
    }

    @Override
    public PageForm getStaffDataList( Integer pageNumber, Integer pageSize) {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        HttpServletRequest request = ServletActionContext.getRequest();
        String queryName = request.getParameter("queryName") == null ? "" : request.getParameter("queryName");
        Map<String,String> param = new HashMap<>();
        param.put("staffName",queryName);
        param.put("reference",queryName);
        return staffService.getPersonDataByParam(companyId,param,pageNumber,pageSize);

    }

    @Override
    public String saveAllocationData(StaffLevelAllocation staffLevelAllocation) {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String levelAllocationKey = staffLevelAllocation.getLevelAllocationKey();
        if ("".equals(levelAllocationKey) || levelAllocationKey == null){
            staffLevelAllocation.setLevelAllocationKey(null);
            staffLevelAllocation.setLevelAllocationId(serverService.getServerID("allocation"));
            staffLevelAllocation.setCreateStaffId(account.getStaffID());
            staffLevelAllocation.setCreateStaffName(account.getStaffName());
            staffLevelAllocation.setCreateDate(new Date());
        } else {
            StaffLevelAllocation staffLevelAllocationOld = staffLevelAllocationDao.getLevelAllocationById(staffLevelAllocation.getLevelAllocationId(),account.getCompanyID());
            staffLevelAllocation.setCreateDate(staffLevelAllocationOld.getCreateDate());
        }
        staffLevelAllocation.setStatus("0");
        staffLevelAllocation.setCompanyId(account.getCompanyID());
        baseBeanDao.saveOrUpdate(staffLevelAllocation);
        return "success";
    }

    @Override
    public String deleteLevelAllocationById(String levelAllocationId, String companyId) {
        StaffLevelAllocation staffLevelAllocation = staffLevelAllocationDao.getLevelAllocationById(levelAllocationId,companyId);
        staffLevelAllocation.setStatus("9");
        baseBeanDao.saveOrUpdate(staffLevelAllocation);
        return "success";
    }

    @Override
    public String examineLevelAllocation(ExamineProcessData examineProcessData,StaffLevelAllocation staffLevelAllocation) {
        List<BaseBean> baseBeansList = new ArrayList<>();
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        ExamineProcessData examineProcess =  staffLevelAllocationDao.getExamineProcessDataById(staffLevelAllocation.getExamineProcessId(),companyId);
        examineProcess.setExamineStaffId(account.getStaffID());
        examineProcess.setExamineStaffName(account.getStaffName());
        examineProcess.setExamineDate(new Date());
        examineProcess.setExamineStatus(examineProcessData.getExamineStatus());
        examineProcess.setExamineRemark(examineProcessData.getExamineRemark());
        baseBeansList.add(examineProcess);
        StaffLevelAllocation staffLevelAllocationOld = staffLevelAllocationDao.getLevelAllocationById(staffLevelAllocation.getLevelAllocationId(),account.getCompanyID());
        staffLevelAllocationOld.setStatus(examineProcessData.getExamineStatus());
        baseBeansList.add(staffLevelAllocationOld);
        baseBeanDao.executeSqlsByParmsList(baseBeansList,null,null);
        return "success";

    }

    @Override
    public String saveTransmitPersonData(String id,String transmitId, String transmitName) {
        String[] idArr = transmitId.split("\\,");
        String[] nameArr = transmitName.split("\\,");
        int length = idArr.length;
        List<BaseBean> dataList = new ArrayList<>();
        ExaminePersonData examinePersonData;
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        ExamineProcessData examineProcessData = new ExamineProcessData();
        String examineProcessId = serverService.getServerID("eprocess");
        examineProcessData.setProcessType("levelAllocation");
        examineProcessData.setExamineProcessId(examineProcessId);
        examineProcessData.setProcessAssociationId(id);
        examineProcessData.setCompanyId(companyId);
        dataList.add(examineProcessData);
        for (int i = 0; i < length; i++){
            examinePersonData = new ExaminePersonData();
            examinePersonData.setExaminePersonId(serverService.getServerID("eperson"));
            examinePersonData.setProcessAssociationId(id);
            examinePersonData.setExamineDataId(idArr[i]);
            examinePersonData.setExamineDataName(nameArr[i]);
            examinePersonData.setExamineProcessId(examineProcessId);
            examinePersonData.setCompanyId(companyId);
            dataList.add(examinePersonData);
        }
        StaffLevelAllocation staffLevelAllocation = staffLevelAllocationDao.getLevelAllocationById(id,companyId);
        staffLevelAllocation.setExamineProcessId(examineProcessId);
        staffLevelAllocation.setStatus("0");
        dataList.add(staffLevelAllocation);
        baseBeanDao.executeSqlsByParmsList(dataList,null,null);
        return "success";
    }

    @Override
    public PageForm getRoleDataByParam(Map<String,String> param, String companyId, Integer pageNumber, Integer pageSize) {
        return staffLevelAllocationDao.getRoleDataByParam(param,companyId,pageNumber,pageSize);
    }

    @Override
    public List<BaseBean> getExamineProcessList(String levelAllocationId,String companyId) {
        return staffLevelAllocationDao.getExamineProcessList(levelAllocationId,companyId);
    }

    @Override
    public String saveStaffLevelByAllocationId(String allocationId) {
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        StaffLevelAllocation staffLevelAllocation = staffLevelAllocationDao.getLevelAllocationById(allocationId,companyId);
        Map<String,String> param = new HashMap<>();
        List<BaseBean> dataList = new ArrayList<>();
        param.put("staffId",staffLevelAllocation.getStaffId());
        param.put("companyId",companyId);
        param.put("cosStatus","50");
        COS cos = staffDao.getCosDataByParam(param);
        staffLevelAllocation.setStatus("3");
        cos.setSalaryLevelId(staffLevelAllocation.getSalaryLevelId());
        dataList.add(staffLevelAllocation);
        dataList.add(cos);
        baseBeanDao.executeSqlsByParmsList(dataList,null,null);
        return "success";
    }


}