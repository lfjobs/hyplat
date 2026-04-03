package hy.ea.human.service.impl;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.human.salary.*;
import hy.ea.human.dao.SalaryLevelDao;
import hy.ea.human.service.CSalaryLevelService;
import hy.ea.office.bo.HrSalaryLevel;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class CSalaryLevelServiceImpl implements CSalaryLevelService {
    @Resource
    private ServerService serverService;

    @Resource
    private BaseBeanDao baseBeanDao;

    @Resource
    private SalaryLevelDao salaryLevelDao;

    @Resource
    private BaseBeanDao basebeandao;

    @Override
    public String saveSalaryLevel() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        String staffId = account.getStaffID();
        List<BaseBean> baseBeansList = new ArrayList<>();
        Integer levelNum = Integer.parseInt(request.getParameter("levelNum"));
        Integer oldLevelNum = Integer.parseInt(request.getParameter("oldLevelNum"));
        String levelNumStr;
        // 保存级别列表
        SalaryLevelList levelList = new SalaryLevelList();
        levelList.setLevelListId(serverService.getServerID("bLevelList"));
        levelList.setLevelSerial(levelNum.toString());
        levelList.setLevelName("1级 ~ " + levelNum + "级");
        levelList.setCompanyId(companyId);
        levelList.setCreateStaffId(staffId);
        levelList.setCreateDate(new Date());
        levelNumStr = "HRSA-001 ~ HRSA-" + levelNum;
        if (levelNum < 10){
            levelNumStr = "HRSA-001 ~ HRSA-00" + levelNum;
        } else if (levelNum < 100){
            levelNumStr = "HRSA-001 ~ HRSA-0" + levelNum;
        } else {
            levelNumStr = "HRSA-001 ~ HRSA-" + levelNum;
        }
        levelList.setLevelNum(levelNumStr);
        baseBeansList.add(levelList);
        // 保存级别
        HrSalaryLevel salaryLevel;
        String salaryLevelNum;
        Map<String,String> map = new HashMap<>();
        getSalaryDictData(companyId,map);
        String salaryLevelId = "";
        Double dayNum = (double) 30;
        if (oldLevelNum > 0) {
            SalaryDictData salaryDictData = salaryLevelDao.getDictDataByDictName("dateSet","basic",companyId);
            if (salaryDictData != null){
                String dictValue =salaryDictData.getDictValue();
                if ("1".equals(dictValue)){
                    dayNum = Double.parseDouble("21.75");
                } else {
                    dayNum = Double.parseDouble("30");
                }

            }
        }

        for (int i = oldLevelNum + 1; i <= levelNum; i++){
            salaryLevel = new HrSalaryLevel();
            salaryLevelId = serverService.getServerID("bLevel");
            salaryLevel.setSalaryLevelId(salaryLevelId);
            if (i < 10){
                salaryLevelNum = "HRSA-00" + i;
            } else if (i < 100){
                salaryLevelNum = "HRSA-0" + i;
            } else {
                salaryLevelNum = "HRSA-" + i;
            }
            salaryLevel.setSalaryLevelNum(salaryLevelNum);
            salaryLevel.setSalaryLevelSerial(String.valueOf(i));
            salaryLevel.setCompanyId(companyId);
            salaryLevel.setCreateStaffId(staffId);
            salaryLevel.setCreateDate(new Date());
            baseBeansList.add(salaryLevel);
            if (oldLevelNum > 0){
                // 级别工资数据
                SalaryData salaryData = new SalaryData();
                salaryData.setSalaryId(serverService.getServerID("bSalary"));
                salaryData.setSalaryLevelId(salaryLevelId);
                salaryData.setSalaryLevelSerial(salaryLevel.getSalaryLevelSerial());
                salaryData.setSalaryLevelNum(salaryLevel.getSalaryLevelNum());
                salaryData.setCompanyId(companyId);
                addSalaryData(salaryData,i-1,map,"",dayNum);
                baseBeansList.add(salaryData);
            }
        }
        baseBeanDao.executeSqlsByParmsList(baseBeansList,null,null);
        return "success";
    }

    @Override
    public PageForm getSalaryLevelList(String companyId,Integer pageNumber, Integer pageSize) {
        return salaryLevelDao.getSalaryLevelList(companyId,pageNumber,pageSize);
    }

    @Override
    public PageForm getSalaryLevelByNum(String levelListId, String companyId, Integer pageNumber, Integer pageSize) {
        SalaryLevelList data = salaryLevelDao.getSalaryLevelListById(companyId,levelListId);
        Integer serialNum = Integer.parseInt(data.getLevelSerial());
        return salaryLevelDao.getSalaryLevelByNum(serialNum,companyId,pageNumber,pageSize);
    }

    @Override
    public PageForm getLevelSalaryDataListByCompanyId(String companyId, Integer pageNumber, Integer pageSize) {
        PageForm pageForm = salaryLevelDao.getLevelSalaryDataListByCompanyId(companyId, pageNumber, pageSize);
        if (pageForm == null){
            pageForm = getLevelDataPageFormByCompanyId(companyId,pageNumber,pageSize);
        }
        return pageForm;
    }

    @Override
    public String saveDictData() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        String staffId = account.getStaffID();
        String type = request.getParameter("type");
        List<String> fieldData = getFieldDataByType(type);
        SalaryDictData salaryDictData;
        SalaryDictHistory salaryDictHistory;
        String date = Utilities.getDateString(new Date(),"yyyy-MM-dd HH:mm:ss");
        List<BaseBean> baseBeansList = new ArrayList<>();
        List<BaseBean> salaryDictList = new ArrayList<>();
        List<BaseBean> levelDictList = new ArrayList<>();
        for (String field : fieldData) {
            salaryDictData = new SalaryDictData();
            String dictKey = request.getParameter(field + "DictKey");
            String dictId = request.getParameter(field + "DictId");
            if (dictKey == null || "".equals(dictKey)) {
                dictId = serverService.getServerID("bDict");
            }
            salaryDictData.setDictKey(dictKey);
            salaryDictData.setDictId(dictId);
            salaryDictData.setDictType(type);
            salaryDictData.setDictName(field);
            salaryDictData.setDictValue(request.getParameter(field));
            salaryDictData.setCompanyId(companyId);
            salaryDictData.setCreateStaffId(staffId);
            salaryDictData.setCreateDate(date);
            salaryDictHistory = new SalaryDictHistory();
            salaryDictHistory.setDictId(dictId);
            salaryDictHistory.setDictType(type);
            salaryDictHistory.setDictName(field);
            salaryDictHistory.setDictValue(request.getParameter(field));
            salaryDictHistory.setCompanyId(companyId);
            salaryDictHistory.setCreateStaffId(staffId);
            salaryDictHistory.setCreateDate(date);
            baseBeansList.add(salaryDictData);
            baseBeansList.add(salaryDictHistory);
            salaryDictList.add(salaryDictData);
        }

        if ("basic".equals(type)){
            String basicSalary = request.getParameter("basicSalary");
            String basicSalaryOld = request.getParameter("basicSalaryOld");
            if (!"".equals(basicSalaryOld) && !basicSalary.equals(basicSalaryOld)){
                levelDictList = getLevelSalaryDataByBasicSalary(basicSalary,basicSalaryOld,companyId,staffId);
                baseBeansList.addAll(levelDictList);
                levelDictList = saveLevelSalaryData("level",levelDictList,new ArrayList<>());
            }

        }
        saveLevelSalaryData(type,salaryDictList,levelDictList);
        baseBeanDao.executeSqlsByParmsList(baseBeansList,null,null);
        return "success";
    }


    @Override
    public List<BaseBean> getDictDataListByType(String type,String companyId) {
        return salaryLevelDao.getDictDataListByType(type,companyId);
    }

    @Override
    public List<BaseBean> getDictDataListByCompanyId(String companyId) {
        return salaryLevelDao.getDictDataListByCompanyId(companyId);
    }

    /**
     * 分页获取级别表数据
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public PageForm getLevelDataPageFormByCompanyId(String companyId,Integer pageNumber, Integer pageSize) {
        return salaryLevelDao.getLevelDataPageFormByCompanyId(companyId,pageNumber,pageSize);
    }

    @Override
    public String saveDate() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        Integer year = Integer.parseInt(request.getParameter("year"));
        DateInfo info = salaryLevelDao.getDateInfoByDate(year + "-01-01");
        if (info == null){
            LocalDate yearStart = LocalDate.of(year, 1, 1);
            LocalDate yearEnd = LocalDate.of(year, 12, 31);
            DateInfo dateInfo;
            Map<String,String> dateWeek = new HashMap<>();
            dateWeek.put("MONDAY","星期一");
            dateWeek.put("TUESDAY","星期二");
            dateWeek.put("WEDNESDAY","星期三");
            dateWeek.put("THURSDAY","星期四");
            dateWeek.put("FRIDAY","星期五");
            dateWeek.put("SATURDAY","星期六");
            dateWeek.put("SUNDAY","星期天");
            List<BaseBean> baseBeansList = new ArrayList<>();
            for (LocalDate date = yearStart; date.isBefore(yearEnd.plusDays(1)); date = date.plusDays(1)) {
                dateInfo = new DateInfo();
                dateInfo.setDate(date.toString());
                dateInfo.setWeek(dateWeek.get(date.getDayOfWeek().name()));
                String type = "0";
                if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    type = "1";
                }
                dateInfo.setType(type);
                baseBeansList.add(dateInfo);
            }
            baseBeanDao.executeSqlsByParmsList(baseBeansList,null,null);
        }
        return "success";
    }

    @Override
    public List<BaseBean> saveLevelSalaryData(String type,List<BaseBean>  dictDataList,List<BaseBean> levelSalaryList) {
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        Map<String,String> map = new HashMap<>();
        if ("".equals(type)){
            getSalaryDictData(companyId,map);
        } else {
            SalaryDictData salaryDictData;
            for (BaseBean baseBean : dictDataList){
                salaryDictData = (SalaryDictData) baseBean ;
                map.put(type + "_" + salaryDictData.getDictName(),salaryDictData.getDictValue());
            }
        }

        return saveLevelSalaryDataByMap(levelSalaryList,companyId,type,map);
    }



    @Override
    public HrSalaryLevel getSalaryLevelById(String salaryLevelId) {
        return salaryLevelDao.getSalaryLevelById(salaryLevelId);
    }
    @Override
    public HrSalaryLevel getSalaryLevelBySerial(String companyId,String salaryLevelSerial) {
        return salaryLevelDao.getSalaryLevelBySerial(companyId,salaryLevelSerial);
    }

    @Override
    public SalaryData getSalaryLevelDataBySalaryLevelId(String salaryLevelId,String companyId) {
        return salaryLevelDao.getSalaryLevelDataBySalaryLevelId(salaryLevelId,companyId);
    }

    @Override
    public SalaryData getSalaryLevelDataBySalaryLevelSerial(String salaryLevelSerial,String companyId) {
        return salaryLevelDao.getSalaryLevelDataBySalaryLevelSerial(salaryLevelSerial,companyId);
    }


    @Override
    public SalaryData getSalaryLevelDataByStaffId(String staffId, String companyId) {
        SalaryData salaryData = salaryLevelDao.getSalaryLevelDataByStaffId(staffId,companyId);
        if (salaryData == null){
            salaryData = salaryLevelDao.getSalaryLevelDataBySalaryLevelSerial("4",companyId);
        }
        return salaryData;
    }

    @Override
    public String saveGuaranteeSalary() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        String staffId = account.getStaffID();
        String[] typeArray = new String[]{"basic","role","duty","compete","secrecy","level"};
        String[] fieldArray;
        int length = typeArray.length;
        SalaryDictData salaryDictData;
        SalaryDictHistory salaryDictHistory;
        List<BaseBean> baseBeansList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        String date = Utilities.getDateString(new Date(),"yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < length; i++){
            String type = typeArray[i];
            if ("basic".equals(type)){
                fieldArray = new String[]{"basicSalary","dateSet"};
            } else if ("level".equals(type)){
                fieldArray = new String[]{"levelSalary"};
            } else{
                fieldArray = new String[]{"salary","multiplier","startValue"};
            }
            int fieldLength = fieldArray.length;
            for (int j = 0;j < fieldLength; j++){
                String field = fieldArray[j];
                String id = type + "_" + field;
                String value = request.getParameter(id);
                if (value != null){
                    salaryDictData = new SalaryDictData();
                    String dictKey = request.getParameter(field + "DictKey");
                    String dictId = request.getParameter(field + "DictId");
                    if (dictKey == null || "".equals(dictKey)) {
                        dictId = serverService.getServerID("bDict");
                    }
                    map.put(type + "_" + field,value);
                    salaryDictData.setDictKey(dictKey);
                    salaryDictData.setDictId(dictId);
                    salaryDictData.setDictType(type);
                    salaryDictData.setDictName(field);
                    salaryDictData.setDictValue(value);
                    salaryDictData.setCompanyId(companyId);
                    salaryDictData.setCreateStaffId(staffId);
                    salaryDictData.setCreateDate(date);
                    salaryDictHistory = new SalaryDictHistory();
                    salaryDictHistory.setDictId(dictId);
                    salaryDictHistory.setDictType(type);
                    salaryDictHistory.setDictName(field);
                    salaryDictHistory.setDictValue(request.getParameter(field));
                    salaryDictHistory.setCompanyId(companyId);
                    salaryDictHistory.setCreateStaffId(staffId);
                    salaryDictHistory.setCreateDate(date);
                    baseBeansList.add(salaryDictData);
                    baseBeansList.add(salaryDictHistory);
                } else {
                    map.put(type + "_" + field,"0");
                }
            }
            getDictData("welfare",companyId,map);
        }
        saveLevelSalaryDataByMap(new ArrayList<>(),companyId,"",map);
            String sql = "delete from SalaryDictData  where companyId = ? ";
        basebeandao.saveBeansListAndexecuteHqlsByParams(null,new String[]{sql}, new Object[]{companyId});
        baseBeanDao.executeSqlsByParmsList(baseBeansList,null,null);
        return "success";
    }


    public List<BaseBean> saveLevelSalaryDataByMap(List<BaseBean> levelSalaryList,String companyId,String type,Map<String,String> map){
        SalaryData salaryData;
        HrSalaryLevel salaryLevel;
        List<BaseBean> baseBeansList = new ArrayList<>();
        if(levelSalaryList.isEmpty()){
            levelSalaryList = getLevelSalaryByCompanyId(companyId);
        }
        List<BaseBean> levelDataList = getAllSalaryLevelList(companyId);
        int levelDataSize = levelDataList.size();
        Double dayNum = 30.0;
        String dictValue = map.get("basic_dateSet");
        if (dictValue != null){
            dictValue = map.get("basic_dateSet");
            if ("1".equals(dictValue)){
                dayNum = Double.parseDouble("21.75");
            } else {
                dayNum = Double.parseDouble("30");
            }
        } else {
            SalaryDictData salaryDictData = salaryLevelDao.getDictDataByDictName("dateSet","basic",companyId);
            if (salaryDictData != null){
                dictValue =salaryDictData.getDictValue();
                if ("1".equals(dictValue)){
                    dayNum = Double.parseDouble("21.75");
                } else {
                    dayNum = Double.parseDouble("30");
                }

            }
        }

        for(int i = 0; i < levelDataSize;i++){
            // 级别工资数据
            SalaryData salaryLevelData = null;
            if(levelSalaryList.size() > i){
                salaryLevelData = (SalaryData)levelSalaryList.get(i);
            }
            if(salaryLevelData == null){
                salaryData = new SalaryData();
                salaryData.setSalaryId(serverService.getServerID("bSalary"));
            } else {
                salaryData = salaryLevelData;
            }
            salaryLevel = (HrSalaryLevel)levelDataList.get(i);
            salaryData.setSalaryLevelId(salaryLevel.getSalaryLevelId());
            salaryData.setSalaryLevelSerial(salaryLevel.getSalaryLevelSerial());
            salaryData.setSalaryLevelNum(salaryLevel.getSalaryLevelNum());
            salaryData.setCompanyId(companyId);
            addSalaryData(salaryData,i,map,type,dayNum);
            baseBeansList.add(salaryData);
        }
        baseBeanDao.executeSqlsByParmsList(baseBeansList,null,null);
        return baseBeansList;
    }

    public List<BaseBean> getLevelSalaryDataByBasicSalary(String basicSalary,String basicSalaryOld,String companyId,String staffId){
        List<BaseBean> baseBeansList = new ArrayList<>();
        SalaryDictData salaryDictData;
        Integer levelSalary = Integer.parseInt(basicSalaryOld) - Integer.parseInt(basicSalary);
        List<BaseBean> list = salaryLevelDao.getDictDataListByType("level",companyId);
        String date = Utilities.getDateString(new Date(),"yyyy-MM-dd HH:mm:ss");
        if (list.isEmpty()){
            salaryDictData = new SalaryDictData();
            String dictId = serverService.getServerID("bDict");
            salaryDictData.setDictId(dictId);
            salaryDictData.setDictType("level");
            salaryDictData.setDictName("levelSalary");
            salaryDictData.setDictValue(levelSalary+ "");
            salaryDictData.setCompanyId(companyId);
            salaryDictData.setCreateStaffId(staffId);
            salaryDictData.setCreateDate(date);
            baseBeansList.add(salaryDictData);
        } else {
            for (BaseBean baseBean : list){
                salaryDictData = (SalaryDictData)baseBean;
                salaryDictData.setDictValue(levelSalary + "");
                baseBeansList.add(salaryDictData);
            }
        }
        return baseBeansList;
    }

    public void getSalaryDictData(String companyId,Map<String,String> map){
        getDictData("basic",companyId,map);
        getDictData("role",companyId,map);
        getDictData("duty",companyId,map);
        getDictData("compete",companyId,map);
        getDictData("secrecy",companyId,map);
        getDictData("level",companyId,map);
        getDictData("welfare",companyId,map);
    }

    public void addSalaryData(SalaryData salaryData,int num,Map<String,String> map,String type,Double dayNum){
        String[] nameArray = {"劳务工","小时工","承包工"};
        if (num <= 2){
            salaryData.setBaseSalary(nameArray[num]);
            salaryData.setRoleSalary(nameArray[num]);
            salaryData.setDutySalary(nameArray[num]);
            salaryData.setCompeteSalary(nameArray[num]);
            salaryData.setSecrecySalary(nameArray[num]);
            salaryData.setLevelSalary(nameArray[num]);
            salaryData.setGuaranteeSalary(nameArray[num]);
            salaryData.setWelfareSalary(nameArray[num]);
            salaryData.setTotalSalary(nameArray[num]);
            salaryData.setOvertimeSalary(nameArray[num]);
            salaryData.setSummarySalary(nameArray[num]);
        } else {
            checkSalaryData(salaryData);
            fillSalaryData(type,map,salaryData, num);
            Double guaranteeSalary = Double.parseDouble(salaryData.getBaseSalary()) + Double.parseDouble(salaryData.getRoleSalary())
                    + Double.parseDouble(salaryData.getDutySalary()) + Double.parseDouble(salaryData.getCompeteSalary())
                    + Double.parseDouble(salaryData.getSecrecySalary()) + Double.parseDouble(salaryData.getLevelSalary());
            salaryData.setGuaranteeSalary(String.format("%.2f",guaranteeSalary));
            Double totalSalary = guaranteeSalary + Double.parseDouble(salaryData.getWelfareSalary());
            salaryData.setTotalSalary(String.format("%.2f",totalSalary));
            Double overtimeSalary = (guaranteeSalary/dayNum)*2*4;
            salaryData.setOvertimeSalary(String.format("%.2f",overtimeSalary));
            salaryData.setSummarySalary(String.format("%.2f",(totalSalary + overtimeSalary)));
        }


    }

    /**
     * 填充数据
     */
    public void fillSalaryData(String type,Map<String,String> map,SalaryData salaryData,int num){
        if ("basic".equals(type) || "".equals(type)){
            if (map.get("basic_basicSalary") == null){
                salaryData.setBaseSalary(0 + "");
            } else {
                salaryData.setBaseSalary(map.get("basic_basicSalary") );
            }
        }
        if ("level".equals(type) || "".equals(type)){
            if (map.get("level_levelSalary") == null){
                salaryData.setLevelSalary(0 + "");
            } else {
                salaryData.setLevelSalary(map.get("level_levelSalary"));
            }
        }

        if ("role".equals(type) || "".equals(type)){
            if (map.get("role_salary") == null){
                salaryData.setRoleSalary(0 + "");
            } else {
                salaryData.setRoleSalary(getSalaryData("role",map,num));
            }
        }
        if ("duty".equals(type) || "".equals(type)){
            if (map.get("duty_salary") == null){
                salaryData.setDutySalary(0 + "");
            } else {
                salaryData.setDutySalary(getSalaryData("duty",map,num));
            }
        }
        if ("compete".equals(type) || "".equals(type)){
            if (map.get("compete_salary") == null){
                salaryData.setCompeteSalary(0 + "");
            } else {
                salaryData.setCompeteSalary(getSalaryData("compete",map,num));
            }
        }
        if ("secrecy".equals(type) || "".equals(type)){
            if (map.get("secrecy_salary") == null){
                salaryData.setSecrecySalary(0 + "");
            } else {
                salaryData.setSecrecySalary(getSalaryData("secrecy",map,num));
            }
        }

        if ("welfare".equals(type) || "".equals(type)){
            if (map.get("welfare_salary") == null){
                salaryData.setWelfareSalary(0 + "");
            } else {
                salaryData.setWelfareSalary(String.format("%.2f",Double.parseDouble(getSalaryData("welfare",map,num))));
            }
        }
    }
    public String getSalaryData(String type,Map<String,String> map,int num){
        Integer value = Integer.parseInt(map.get(type + "_salary"));
        Integer multiplier = Integer.parseInt(map.get(type + "_multiplier"));
        Integer startValue = Integer.parseInt(map.get(type + "_startValue"));
        return String.valueOf((startValue + (num-3) * value * multiplier));
    }
    /**
     * 检查工资数据
     * @param salaryData
     */
    public void checkSalaryData(SalaryData salaryData){
        if (salaryData.getBaseSalary() == null){
            salaryData.setBaseSalary(0 + "");
        }
        if (salaryData.getRoleSalary() == null){
            salaryData.setRoleSalary(0 + "");
        }
        if (salaryData.getDutySalary() == null){
            salaryData.setDutySalary(0 + "");
        }
        if (salaryData.getCompeteSalary() == null){
            salaryData.setCompeteSalary(0 + "");
        }
        if (salaryData.getSecrecySalary() == null){
            salaryData.setSecrecySalary(0 + "");
        }
        if (salaryData.getLevelSalary() == null){
            salaryData.setLevelSalary(0 + "");
        }
        if (salaryData.getWelfareSalary() == null){
            salaryData.setWelfareSalary(0 + "");
        }
    }

    public List<BaseBean> getLevelSalaryByCompanyId(String companyId){
        return salaryLevelDao.getLevelSalaryByCompanyId(companyId);
    }

    /**
     * 获取字典数据Map
     * @param type
     * @param companyId
     * @return
     */
    public Map<String,String> getDictData(String type,String companyId,Map<String,String> map){
        List<BaseBean> dictDataList = getDictDataListByType(type,companyId);
        SalaryDictData salaryDictData;
        for (BaseBean baseBean : dictDataList){
            salaryDictData = (SalaryDictData) baseBean ;
            map.put(type + "_" + salaryDictData.getDictName(),salaryDictData.getDictValue());
        }
        return map;
    }

    public List<BaseBean> getDictDataListByDictName(String dictName) {
        return salaryLevelDao.getDictDataListByDictName(dictName);
    }

    public SalaryDictData getDictDataByDictName(String dictName,String dictType,String companyId) {
        return salaryLevelDao.getDictDataByDictName(dictName,dictType,companyId);
    }

    /**
     * 获取所有公司级别数据
     * @param companyId
     * @return
     */
    public List<BaseBean> getAllSalaryLevelList(String companyId) {
        return salaryLevelDao.getAllSalaryLevelList(companyId);
    }
    /**
     * 获取字段数据
     * @param type
     * @return
     */
    public List<String> getFieldDataByType(String type){
        String[] fieldArray = null;
        //基本工资
        if ("basic".equals(type)){
            fieldArray = new String[]{"basicSalary","dateSet"};
        } else if ("level".equals(type)){
            fieldArray = new String[]{"levelSalary"};
        } else {
            fieldArray = new String[]{"salary","multiplier","startValue"};
        }
        return Arrays.asList(fieldArray);
    }
}