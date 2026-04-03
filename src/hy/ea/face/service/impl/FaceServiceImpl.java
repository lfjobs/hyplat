package hy.ea.face.service.impl;

import com.faceSDK.faceUtil.DateUtils;
import hy.ea.bo.office.face.OfficeFaceEmpower;
import hy.ea.bo.office.face.OfficeFaceResult;
import hy.ea.bo.office.face.TbFaceDevice;
import hy.ea.face.service.FaceService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class FaceServiceImpl implements FaceService {
    @Resource
    private BaseBeanDao beandao;

    public OfficeFaceEmpower beanByHqlAndParams;
    @Override
    public Integer updateDeviceById(TbFaceDevice tbFaceDevice) {
        try {
            String hql = "update TbFaceDevice set faceDeviceId = ? where id = ?";
            beandao.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql},new String[] { tbFaceDevice.getFaceDeviceId(), tbFaceDevice.getId() });
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public PageForm findFaceDevice(String companyName) {
        PageForm pageForm=new PageForm();
        String params[];
        String hql="";
        if(companyName!=null){
            hql = "select id,deviceName,deviceSN,companyName,createTime from TbFaceDevice where faceDeviceId is not NULL and companyName like ?";
            params=new String[]{"%"+companyName+"%"};
        }else{
            hql = "select id,deviceName,deviceSN,companyName,createTime from TbFaceDevice where faceDeviceId is not NULL";
            params=new String[]{};
        }
        List<BaseBean> deviceList=beandao.getListBeanByHqlAndParams(hql,params);
        pageForm.setList(deviceList);
        return pageForm;
    }

    @Override
    public PageForm findPersonEmpower(Integer empoderState,String personName,List<String> companyList) {
        PageForm pageForm=new PageForm();
        List<BaseBean> listBeanBySqlAndParams;
        List<Object> list=new ArrayList<>();
        String sql = "SELECT empower.STAFF_KEY,empower.STAFF_NAME,empower.PERSON_IMAGE,empower.empower_state,company.COMPANYNAME,empower.SITE_ID FROM OFFICE_FACE_EMPOWER  empower LEFT JOIN DT_HR_STAFF staff ON empower.STAFF_ID = staff.STAFFKEY " +
                "LEFT JOIN DTCOMPANY company on  company.COMPANYID=empower.COMPANY_ID" +
                " WHERE 1 = 1 and company.COMPANYID in(";
        for(int i=0;i<companyList.size();i++){
            sql+= companyList.size()==i+1?"?":"?,";
            list.add(companyList.get(i));
        }
        sql+=")";
        if(empoderState!=0){
            sql+=" and empower.EMPOWER_STATE=?";
            list.add(empoderState);

        }
        if(personName!=null){
            sql+=" and  empower.STAFF_NAME like ?";
            list.add("%"+personName+"%");
        }
        listBeanBySqlAndParams = beandao.getListBeanBySqlAndParams(sql, list.toArray());
        pageForm.setList(listBeanBySqlAndParams);
        return pageForm;
    }
    @Override
    public PageForm findPersonEmpowerInfo(Integer empoderState,String personName,List<String> companyList,String pageNumber,String pageSize,String timeNumber) {
        PageForm pageForm=new PageForm();
        List<BaseBean> listBeanBySqlAndParams;
        List<Object> list=new ArrayList<>();
        String sql = "SELECT empower.STAFF_KEY,empower.STAFF_NAME,empower.PERSON_IMAGE,empower.empower_state,company.COMPANYNAME,empower.SITE_ID,EMPOWER.CREATE_TIME FROM OFFICE_FACE_EMPOWER  empower LEFT JOIN DT_HR_STAFF staff ON empower.STAFF_ID = staff.STAFFKEY " +
                "LEFT JOIN DTCOMPANY company on  company.COMPANYID=empower.COMPANY_ID" +
                " WHERE 1 = 1 and company.COMPANYID in(";
        for(int i=0;i<companyList.size();i++){
            sql+= companyList.size()==i+1?"?":"?,";
            list.add(companyList.get(i));
        }
        sql+=")";
        if(empoderState!=0){
            sql+=" and empower.EMPOWER_STATE=?";
            list.add(empoderState);

        }
        if(personName!=null){
            sql+=" and  empower.STAFF_NAME like ?";
            list.add("%"+personName+"%");
        }
        sql+="ORDER BY EMPOWER.CREATE_TIME DESC";
        /*listBeanBySqlAndParams = beandao.getListBeanBySqlAndParams(sql, list.toArray());
        pageForm.setList(listBeanBySqlAndParams);*/

        PageForm pageFormBySQL=new PageForm();

        if(pageNumber!=null && pageSize!=null){
            pageFormBySQL= getPageFormBySQL(Integer.parseInt(pageNumber), Integer.parseInt(pageSize), sql, "select count(*) from (" + sql + ")", list.toArray());
        }
        return pageFormBySQL;
    }
    @Override
    public void savePersonEmpower(OfficeFaceEmpower officeFaceEmpower) {
        beandao.save(officeFaceEmpower);
    }

    @Override
    public List<Object> findContactCompanyDevice(List<String> listInfo) {
        List<Object> list=new ArrayList<>();
        String sql="select dv.SITEID,dv.SITENAME from OFFICE_FACEDEVICE device LEFT JOIN dt_venueinformation dv on device.SITE_ID=dv.SITEID where device.COMPANY_ID in(";
        //String sql = "select em.ERID,em.ERNAME from OFFICE_FACEDEVICE device LEFT JOIN DT_EXAMINATIONROOM em on device.ER_ID=em.ERID where device.COMPANY_ID in(";
        for(int i=0;i<listInfo.size();i++){
            sql+= listInfo.size()==i+1?"?":"?,";
            list.add(listInfo.get(i));
        }
        sql+=") and dv.SITETYPE='2' and dv.SITEID is NOT NULL and device.FACE_DEVICE_ID is not NULL GROUP BY dv.SITEID,dv.SITENAME";
        List<Object> listObjectBySqlAndParams = beandao.getListObjectBySqlAndParams(sql, list.toArray());
        return listObjectBySqlAndParams;
    }

    @Override
    public OfficeFaceEmpower findPersonEmpowerByStaffId(String staffId) {
        String hql="from OfficeFaceEmpower where staffKey=?";
        beanByHqlAndParams =(OfficeFaceEmpower) beandao.getBeanByHqlAndParams(hql, new Object[]{staffId});
        return beanByHqlAndParams;
    }

    @Override
    public List<TbFaceDevice> findCompanyDeviceByCompanyKey(String erId) {
        List<TbFaceDevice> deviceList=new ArrayList<>();
        String hql="FROM TbFaceDevice where siteId=? and faceDeviceId is not NULL";
        List<BaseBean> listBeanByHqlAndParams = beandao.getListBeanByHqlAndParams(hql, new Object[]{erId});
        if(listBeanByHqlAndParams!=null && listBeanByHqlAndParams.size()!=0){
            for (int i=0;i<listBeanByHqlAndParams.size();i++){
                TbFaceDevice baseBean = (TbFaceDevice)listBeanByHqlAndParams.get(i);
                deviceList.add(baseBean);
            }
        }
        return deviceList;
    }

    @Override
    public void updateFaceEmpwer(OfficeFaceEmpower officeFaceEmpower) {
        String sql="update OFFICE_FACE_EMPOWER set PERSON_ID=?,EMPOWER_STATE=?,EMPOWER_TIME=?  where STAFF_KEY=?";
        beandao.saveBeansListAndexecuteSqlsByParams(null,new String[]{sql},new Object[]{officeFaceEmpower.getPersonId(),officeFaceEmpower.getEmpowerState(),officeFaceEmpower.getEmpowerTime(),officeFaceEmpower.getStaffKey()});
    }

    @Override
    public void saveFaceDevice(TbFaceDevice tbFaceDevice) {
        beandao.save(tbFaceDevice);
    }

    @Override
    public List<BaseBean> findCompanyList(String companyName) {
        //String sql="select CCOMPANYKEY,COMPANYNAME from DTCONTACTCOMPANY where COMPANYNAME like ? and ROWNUM <= 10";
        String sql="select COMPANYID,COMPANYNAME from DTCOMPANY where COMPANYNAME like ? and ROWNUM <= 10";
        List<BaseBean> listBeanBySqlAndParams = beandao.getListBeanBySqlAndParams(sql, new Object[]{"%"+companyName+"%"});
        return listBeanBySqlAndParams;
    }

    @Override
    public List<BaseBean> findDeviceInfo(String deviceSN) {
        String sql="select COMPANY_NAME from OFFICE_FACEDEVICE where DEVICE_SN=? and ROWNUM <= 1";
        List<BaseBean> listBeanBySqlAndParams = beandao.getListBeanBySqlAndParams(sql, new Object[]{deviceSN});
        return listBeanBySqlAndParams;
    }

    @Override
    public void saveFaceResult(OfficeFaceResult faceResult) {
        beandao.save(faceResult);
    }

    @Override
    public PageForm findFaceResult(String presonName,String pageNumber,String pageSize,String timeNumber){
        Map<String,Date> timeMap = DateUtils.determineTimeNumber(timeNumber);
        List<Object> list=new ArrayList<>();
        String sql="";
        if(presonName!=null){
            sql="select SNAP_IMAGE,PERSON_NAME,DEVICE_SN,TO_CHAR(SNAP_TIME, 'YYYY-MM-DD HH24:MI:SS') AS SNAPTIME,INOUT_FLAG,ID from OFFICE_FACE_RESULT where  PERSON_NAME like ? and SNAP_TIME BETWEEN ? and ? ORDER BY SNAPTIME DESC";
            list.add("%"+presonName+"%");
            list.add(timeMap.get("startTime"));
            list.add(timeMap.get("endTime"));
        }else{
            sql="select SNAP_IMAGE,PERSON_NAME,DEVICE_SN,TO_CHAR(SNAP_TIME, 'YYYY-MM-DD HH24:MI:SS') AS SNAPTIME,INOUT_FLAG,ID from OFFICE_FACE_RESULT where SNAP_TIME BETWEEN ? and ? ORDER BY SNAPTIME DESC";
            list.add(timeMap.get("startTime"));
            list.add(timeMap.get("endTime"));
        }
        PageForm pageFormBySQL=new PageForm();

        if(pageNumber!=null && pageSize!=null){
            pageFormBySQL= getPageFormBySQL(Integer.parseInt(pageNumber), Integer.parseInt(pageSize), sql, "select count(*) from (" + sql + ")", list.toArray());
        }
        return pageFormBySQL;
    }

    @Override
    public List<BaseBean> findFaceResultById(String resultId) {
        String sql="select re.PERSON_NAME,re.DEVICE_SN,TO_CHAR(re.SNAP_TIME, 'YYYY-MM-DD HH24:MI:SS') AS snapTime,re.INOUT_FLAG,re.STATUS,re.SNAP_IMAGE,em.PAY_MONEY,em.PAY_STATUS from OFFICE_FACE_RESULT re LEFT JOIN OFFICE_FACE_EMPOWER em on re.PERSON_ID=em.PERSON_ID where re.ID=?";
        List<BaseBean> listBeanByHqlAndParams = beandao.getListBeanBySqlAndParams(sql, new Object[]{resultId});
        return listBeanByHqlAndParams;
    }

    @Override
    public List<BaseBean> findEmpowerById(String staffKey) {
        /*String sql="select empower.STAFF_NAME,empower.PERSON_IMAGE,empower.PAY_MONEY,empower.PAY_STATUS,empower.NOTES,empower.PERSON_TYPE,empower.PERSON_ID," +
                "TO_CHAR(empower.CREATE_TIME, 'YYYY-MM-DD HH24:MI:SS') AS CREATETIME," +
                "TO_CHAR(empower.EMPOWER_TIME, 'YYYY-MM-DD HH24:MI:SS') AS EMPOWERTIME,empower.COMPANY_ID,company.COMPANYNAME,staff.STAFFNAME,empower.EMPOWER_STATE,em.ERNAME " +
                " from OFFICE_FACE_EMPOWER empower LEFT JOIN DTCOMPANY company on empower.COMPANY_ID=company.COMPANYID " +
                "LEFT JOIN DT_HR_STAFF staff on empower.EMPOWER_STAFF_ID=staff.STAFFID " +
                "LEFT JOIN DT_EXAMINATIONROOM em on em.ERID=empower.ER_ID where empower.STAFF_KEY=?";*/
        String sql="select empower.STAFF_NAME,empower.PERSON_IMAGE,empower.PAY_MONEY,empower.PAY_STATUS,empower.NOTES,empower.PERSON_TYPE,empower.PERSON_ID," +
                "TO_CHAR(empower.CREATE_TIME, 'YYYY-MM-DD HH24:MI:SS') AS CREATETIME," +
                "TO_CHAR(empower.EMPOWER_TIME, 'YYYY-MM-DD HH24:MI:SS') AS EMPOWERTIME,empower.COMPANY_ID,company.COMPANYNAME,staff.STAFFNAME,empower.EMPOWER_STATE,dv.SITENAME " +
                " from OFFICE_FACE_EMPOWER empower LEFT JOIN DTCOMPANY company on empower.COMPANY_ID=company.COMPANYID " +
                "LEFT JOIN DT_HR_STAFF staff on empower.EMPOWER_STAFF_ID=staff.STAFFID " +
                "LEFT JOIN dt_venueinformation dv on dv.SITEID=empower.SITE_ID where empower.STAFF_KEY=? and dv.SITETYPE='2'";

        List<BaseBean> listBeanByHqlAndParams = beandao.getListBeanBySqlAndParams(sql, new Object[]{staffKey});
        return listBeanByHqlAndParams;
    }

    @Override
    public Map<String,Integer> findFaceResultPersonNumber(String personName, String timeNumber) {
        Map<String,Integer> map=new HashMap<>();
        Integer enterCount = getCount(personName, timeNumber, 1);//1进门
        Integer outCount = getCount(personName, timeNumber, 0);//0进门
        map.put("enterCount",enterCount);
        map.put("outCount",outCount);
        return map;
    }

    @Override
    public List<BaseBean> findERInfoList(String erId) {
        /*String sql="select t.erid,t.ername,t.itslocation from DT_ExaminationRoom t where  t.companyid = ? and WHETHERSTATUS='00'";*/
        String sql="select dv.siteid,dv.sitename,dv.itslocation from dt_venueinformation dv where  dv.companyid = ? and dv.SITETYPE='2'";
        List<BaseBean> listBeanBySqlAndParams = beandao.getListBeanBySqlAndParams(sql, new Object[]{erId});
        return listBeanBySqlAndParams;
    }

    @Override
    public List<String> findCompanyIdByAccount(String sccId) {
        StringBuffer str = new StringBuffer();
        List<String> params = new ArrayList<String>();
        List<String> list = new ArrayList<String>();

        str.append(" select com.companyname,com.companyid from T_ESHOP_CUSCOM cus, dtCompany com, dt_ccom_com d, dtcontactcompany t where cus.companyid = com.companyid ");
        str.append(" and d.ccompany_id = t.ccompanyid and com.companyid = d.compnay_id and cus.account in (select j.account  from T_ESHOP_CUSCOM j  where j.sccid = ?) union  select com.companyname,com.companyid from  dt_ccom_com d, ");
        str.append(" dtCompany com,dtcontactcompany t,dtcos cos,T_ESHOP_CUSCOM cus, dt_hr_Staff staff, T_ESHOP_CUSCOM   ccus where staff.staffid = cos.staffid and staff.reference = cus.account and cos.companyid=com.companyid and cos.status='01' and cos.cosStatus='50' and com.companyid=d.compnay_id ");
        str.append("  and d.ccompany_id=t.ccompanyid  and com.companyid = ccus.companyid and cus.sccid = ? ");

        params.add(sccId);
        params.add(sccId);
        List<String> lists= new ArrayList<String>();
        List<BaseBean> beanlist1 = beandao.getListBeanBySqlAndParams(str.toString(), params.toArray());
        for (int i = 0; i < beanlist1.size(); i++) {
            Object[] obj3 = (Object[]) (Object) beanlist1.get(i);
            lists.add(isNull(obj3[1]).toString());
        }
        return lists;
    }

    @Override
    public String findStaffInfoById(String staffid) {
        String StaffName=null;
        List<BaseBean> beanlist = beandao.getListBeanBySqlAndParams("select STAFFNAME,STAFFID from DT_HR_STAFF where STAFFID=?", new Object[]{staffid});
        for (int i = 0; i < beanlist.size(); i++) {
            Object[] obj3 = (Object[]) (Object) beanlist.get(i);
            StaffName=obj3[0]==null?"":obj3[0].toString();
        }
        return StaffName;
    }

    @Override
    public String findCompanyIdByERId(String erId) {
        String hql="from TbFaceDevice where siteId=?";
        List<BaseBean> listBeanByHqlAndParams = beandao.getListBeanByHqlAndParams(hql, new Object[]{erId});
        TbFaceDevice device=null;
        if(listBeanByHqlAndParams!=null){
            device=(TbFaceDevice)listBeanByHqlAndParams.get(0);
        }
        return device!=null?device.getCompanyId():null;
    }

    @Override
    public void deletedEmpowerById(String staffKey) {
        String hql="DELETE FROM OfficeFaceEmpower WHERE staffKey=?";
        beandao.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql}, new Object[]{staffKey});
    }

    @Override
    public List<BaseBean> findDeviceBySiteid(String erId) {
        String sql="select FACE_DEVICE_ID,Device_name from OFFICE_FACEDEVICE where SITE_ID=? and FACE_DEVICE_ID is not NULL";
        List<BaseBean> listBeanBySqlAndParams = beandao.getListBeanBySqlAndParams(sql, new Object[]{erId});
        return listBeanBySqlAndParams;
    }

    private Integer getCount(String personName, String timeNumber,Integer inoutFlag){
    Map<String,Date> timeMap = DateUtils.determineTimeNumber(timeNumber);
    List<Object> list=new ArrayList<>();
    String sql="";
    if(personName!=null){
        sql="select count(*) from (select PERSON_ID from OFFICE_FACE_RESULT where INOUT_FLAG=? and  PERSON_NAME like ? and SNAP_TIME BETWEEN ? and ? GROUP BY PERSON_ID)";
        list.add(inoutFlag);
        list.add("%"+personName+"%");
        list.add(timeMap.get("startTime"));
        list.add(timeMap.get("endTime"));
    }else{
        sql="select count(*) from (select PERSON_ID from OFFICE_FACE_RESULT where INOUT_FLAG=? and SNAP_TIME BETWEEN ? and ? GROUP BY PERSON_ID)";
        list.add(inoutFlag);
        list.add(timeMap.get("startTime"));
        list.add(timeMap.get("endTime"));
    }
    Integer count = beandao.getConutByBySqlAndParams(sql, list.toArray());// 总条数
    return count;
}
    /**
     * 分页查询
     * @param pageNumber
     * @param pageSize
     * @param sql
     * @param sqlcount
     * @param params
     * @return
     */
    public PageForm getPageFormBySQL(int pageNumber, int pageSize, String sql,
                                     String sqlcount, Object[] params) {
        int count = beandao.getConutByBySqlAndParams(sqlcount, params);// 总条数
        if (count == 0)
            return null;
        int pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
        if (pageNumber < 1)
            pageNumber = 1;
        if (pageNumber > pageCount)
            pageNumber = pageCount;
        int firstResult = pageSize * (pageNumber - 1);
        int maxResult = Math.min(pageSize, count - firstResult);
        List<BaseBean> listBaseBean = beandao.getConutByBySqlAndParamsAndPage(
                sql, params, firstResult, maxResult);
        PageForm pageForm = new PageForm();
        pageForm.setPageSize(pageSize);
        pageForm.setRecordCount(count);
        pageForm.setPageCount(pageCount);
        pageForm.setPageNumber(pageNumber);
        pageForm.setList(listBaseBean);
        return pageForm;
    }
    private Object isNull(Object tep){
        if(tep==null||"null".equals(tep)){
            return "";
        }else{
            return tep;
        }
    }
}
