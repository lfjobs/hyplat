package hy.ea.driving.service.elkc.impl;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.DrivingSchool.TbJpStudentInfo;
import hy.ea.bo.DrivingSchool.elyc.TbElycOrderDetailTime;
import hy.ea.bo.DrivingSchool.elyc.TbElycOrderRecord;
import hy.ea.bo.SubjectHour;
import hy.ea.driving.service.elkc.ElkcRecordService;
import hy.ea.util.Constant;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ElkcRecordServiceImpl implements ElkcRecordService {
	private Logger logger = LoggerFactory.getLogger(ElkcRecordServiceImpl.class);
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private ServerService serverService;


    /**
     * 转换list *
     *
     * @param sql
     *            Sql语句
     * @param object
     *            所需参数的数组
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List transition(String sql, Object[] object) {
        return beandao.getListBeanBySqlAndParams(sql, object);
    }

    /**
     * 分页
     *
     * @param pageNumber
     *            当前页
     * @param pageSize
     *            显示条数
     * @param sql
     *            sql语句
     * @param sqlcount
     *            总记录数SQL语句
     * @param params
     *            参数
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
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

    /**
     * 保存页面信息 *
     *
     * @return
     */
    @Override
    public String mobileLogin() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.removeAttribute("url");
        HttpServletRequest request = ServletActionContext.getRequest();
        String url = request.getHeader("Referer");
        session.setAttribute("url", url);
        return "mobileLogin";
    }

    /**
     * 获取当前登录对象 *
     *
     * @return
     */
    @Override
    public CAccount goCaccount() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        CAccount cacc = (CAccount) sw.getObject(session, SessionWrap.KEY_CACCOUNT);
        return cacc;
    }




    /**
     * 查询预约记录list *
     * @param studentId:学员id
     * @param pageForm:分页参数
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm reservationRecord(String studentId, PageForm pageForm) {

        StringBuilder sb = new StringBuilder();

        sb.append("select d.etoid,f.headimage,d.teachername,d.subject,to_char(d.lessionstarttime, 'yyyy-mm-dd hh24:mi:ss'), ");
        sb.append("to_char(d.lessionendtime, 'yyyy-mm-dd hh24:mi:ss'),g.canceldetail,y.companyaddr,d.status,y.companyname,d.studentNum,d.studentId,d.teacherId ");
        sb.append("from Tb_Elyc_Order_Record d,Tb_Elyc_Company_Config g, ");
        sb.append("Tb_Jp_Teacher r,dt_hr_staff f,dt_ccom_com c,dtcontactcompany y ");
        sb.append("where d.studentid = ? and d.companyid = g.companyid ");
        sb.append("and d.teacherid = r.teacherid and r.staffid = f.staffid ");
        sb.append("and d.companyid = c.compnay_id and c.ccompany_id = y.ccompanyid order by d.orderTime ");

        pageForm = getPageFormBySQL(pageForm.getPageNumber(),pageForm.getPageSize(),sb.toString(),"select count(*) from (" + sb.toString() + ")",new Object[]{studentId});

        return pageForm;
    }

    /**
     * 查询预约记录 *
     * @param etoId:预约记录id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Object bkDetails(String etoId) {

        StringBuilder sb = new StringBuilder();
        sb.append("select d.etoid,f.headimage,d.teachername, ");
        sb.append("d.subject,to_char(d.lessionstarttime, 'yyyy-mm-dd hh24:mi:ss'), ");
        sb.append("to_char(d.lessionendtime, 'yyyy-mm-dd hh24:mi:ss'), ");
        sb.append("g.canceldetail,y.companyaddr,d.status,y.companyname,d.teacherPhone,d.studentNum,d.studentId,d.teacherId ");
        sb.append("from Tb_Elyc_Order_Record d,Tb_Elyc_Company_Config g, ");
        sb.append("Tb_Jp_Teacher r,dt_hr_staff f,dt_ccom_com c,dtcontactcompany y ");
        sb.append("where d.etoid = ? and d.companyid = g.companyid ");
        sb.append("and d.teacherid = r.teacherid and r.staffid = f.staffid ");
        sb.append("and d.companyid = c.compnay_id and c.ccompany_id = y.ccompanyid ");

        Object object = beandao.getObjectBySqlAndParams(sb.toString(),new Object[]{etoId});

        return object;
    }

    /**
     * 取消预约记录 *
     * @param etoId:预约记录id
     * @return
     */
    @Override
    @Transactional
    public void cancellation(String etoId) {

        TbElycOrderRecord tbElycOrderRecord = (TbElycOrderRecord) beandao.getBeanByHqlAndParams("from TbElycOrderRecord where etoId = ? ",new Object[]{etoId});

        if (tbElycOrderRecord!=null)
        {
            //修改教练预约
            TbElycOrderDetailTime tbElycOrderDetailTime = (TbElycOrderDetailTime) beandao.getBeanByHqlAndParams("from TbElycOrderDetailTime where odtId = ? ",new Object[]{tbElycOrderRecord.getDetailId()});
            tbElycOrderDetailTime.setStatus("2");
            beandao.update(tbElycOrderDetailTime);

            //修改学员预约记录
            tbElycOrderRecord.setStatus("2");
            //添加修改时间
            tbElycOrderRecord.setCancelTime(new Date());
            beandao.update(tbElycOrderRecord);


        }
    }

    /**
     * 计时练车 *
     *
     * @return 00:未登录,01:未报名,02:未预约,03:时间未到,04:练车开始
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> timingToPracticeCar() {
        String status = "04";
        Object object = null;

        //判断是否登录
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cuscom = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
        if (cuscom==null)
        {
            status = "00";
        }
        else
        {
            //判断是否报名
            StringBuilder sb = new StringBuilder();
            sb.append("select count(*) ");
            sb.append("from dtEnroll e, dtCashierBills s ");
            sb.append("where e.staffid = ? and e.cashierbillsid = s.cashierbillsid ");
            sb.append("and s.fkstatus = ? ");
            int count = beandao.getConutByBySqlAndParams(sb.toString(),new Object[]{cuscom.getStaffid(),"00"});

            if (count==0)
            {
                status = "01";
            }
            else
            {
                //判断是否预约
                int whether = beandao.getConutByBySqlAndParams("select count(*) from Tb_Elyc_Order_Record where studentId = ? and status = ? ",new Object[]{cuscom.getStaffid(),"1"});

                if (whether==0)
                {
                    status = "02";
                }
                else
                {
                    //判断该时间是否到达练习时间
                    Date date = new Date();
                    String sql = "select subject from Tb_Elyc_Order_Record where studentId = ? and status = ? and(lessionStartTime >= ? and lessionEndTime <= ?)";
                    object = beandao.getObjectBySqlAndParams(sql,new Object[]{cuscom.getStaffid(),"1",date,date});

                    if (object==null)
                    {
                         status = "03";
                    }
                    else
                    {
                        status = "04";
                    }
                }
            }
        }

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("status",status);
        map.put("object",object);

        return map;
    }

    /**
     * 学员信息 *
     * @param staffID:人员id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public TbJpStudentInfo studentInfo(String staffID) {

        TbJpStudentInfo tbJpStudentInfo = (TbJpStudentInfo) beandao.getBeanByHqlAndParams("from TbJpStudentInfo where staffId=? ",new Object[]{staffID});

        return tbJpStudentInfo;
    }




    /**
     * 启动 *
     * @param dayBefore
     */
    @Override
    @Transactional
    public void bootUp(String dayBefore) throws IOException, ParseException {

        //获取今天预约练习的学生
        StringBuilder sb = new StringBuilder();
        sb.append("from TbElycOrderRecord s where s.status in(?,?) ");
        sb.append("and to_char(s.lessionStartTime, 'yyyy-mm-dd') = ? ");
        List<BaseBean> list = beandao.getListBeanByHqlAndParams(sb.toString(),new Object[]{"1","3",dayBefore});

        if (list.size()>0)
        {
            sb.delete(0,sb.length());
            for (BaseBean x:list)
            {
                TbElycOrderRecord tr = (TbElycOrderRecord) x;
                sb.append(tr.getStudentNum());
                sb.append(",");
            }
            sb.deleteCharAt(sb.length()-1);

            //获取今天终端签到签退的学生
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            String url = Constant.TIMING_DOMAIN+"/jptrainperiod/queryStudentByTime";
            PostMethod postMethod = new PostMethod(url);
            NameValuePair[] data = {
                    new NameValuePair("student",sb.toString()),
                    new NameValuePair("time",dayBefore),
            };
            postMethod.setRequestBody(data);
            httpClient.executeMethod(postMethod);
            // 读取内容
            InputStream resInputStream = null;
            resInputStream = postMethod.getResponseBodyAsStream();
            // 处理内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(resInputStream));
            String tempBf = null;
            StringBuffer html = new StringBuffer();
            while ((tempBf = reader.readLine()) != null) {

                html.append(tempBf);
            }
            String resMes = html.toString();
            JSONObject jsonObj_school = new JSONObject(resMes); //解析JSON字符串
            JSONArray jsonArr_school  =jsonObj_school.getJSONArray("periodDel");//接收JSON对象里的数组
            int jsonSize_school = jsonArr_school.length();//获取数组长度

            if (jsonSize_school>0)//判断获取的json是否有数据
            {
                //学习科目
                List<BaseBean> bbList = list;
                List<BaseBean> submitList = new ArrayList<BaseBean>();
                for (BaseBean x:list)
                {
                    TbElycOrderRecord tr = (TbElycOrderRecord) x;
                    for(int i = 0; i < jsonSize_school; i++ )//通过循环取出数组里的值
                    {
                        JSONObject jsonTemp = (JSONObject) jsonArr_school.getJSONObject(i);
                        String person_number = jsonTemp.getString("PERSON_NUMBER");//获取身份证
                        String subject = jsonTemp.getString("SUBJECT");//科目几
                        String starttime = jsonTemp.getString("STARTTIME");//实际开始时间
                        String endtime = jsonTemp.getString("ENDTIME");//实际结束时间
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

                        if (person_number.equals(tr.getStudentNum()))//判断身份证号是否相等
                        {
                            Date start = df.parse(starttime);
                            Date end = df.parse(endtime);
                            if (tr.getStatus().equals("1"))//已预约
                            {
                                Boolean flag = belongCalendar(start, tr.getLessionStartTime(), tr.getLessionEndTime());
                                if (flag)
                                {
                                    tr.setStatus("4");
                                    tr.setActualCheckInTime(start);
                                    tr.setActualCheckOutTime(end);
                                    submitList.add(tr);
                                }
                            }
                            else if (tr.getStatus().equals("3"))//已签到
                            {
                                Calendar Cal=Calendar.getInstance();
                                Cal.setTime(end);
                                Cal.add(Calendar.HOUR_OF_DAY,-1);
                                Boolean flag = belongCalendar(Cal.getTime(), tr.getLessionStartTime(), tr.getLessionEndTime());
                                if (flag)
                                {
                                    tr.setStatus("4");
                                    tr.setActualCheckOutTime(end);
                                    submitList.add(tr);
                                }
                            }
                        }
                    }
                }

                //超时关闭
                bbList.removeAll(submitList);
                List<BaseBean> timeoutList = new ArrayList<BaseBean>();
                for (BaseBean b:bbList){
                    TbElycOrderRecord tr = (TbElycOrderRecord) b;
                    tr.setStatus("6");
                    timeoutList.add(tr);
                }

                //结算学时
                List<String> stringList = new ArrayList<String>();
                for (BaseBean s:submitList){
                    TbElycOrderRecord tr = (TbElycOrderRecord) s;
                    stringList.add(tr.getStaffId());
                }
                StringBuilder ss = new StringBuilder();
                ss.append("from SubjectHour where staffId in(");
                for (int i = 0; i < stringList.size(); i++ ){
                    ss.append("?,");
                }
                ss.deleteCharAt(ss.length()-1);
                ss.append(")");
                List<BaseBean> subList = beandao.getListBeanByHqlAndParams(ss.toString(),stringList.toArray());
                List<BaseBean> clearingList = new ArrayList<BaseBean>();
                for (BaseBean s:subList){
                    SubjectHour sub = (SubjectHour) s;
                    for (BaseBean n:submitList){
                        TbElycOrderRecord tr = (TbElycOrderRecord) n;
                        if (sub.getStaffId().equals(tr.getStaffId()))
                        {
                            sub.setHasTime(sub.getHasTime()-1);
                        }
                    }
                    clearingList.add(sub);
                }

                //合并List
                List<BaseBean> coalescingList = new ArrayList<BaseBean>();
                coalescingList.addAll(submitList);
                coalescingList.addAll(timeoutList);
                coalescingList.addAll(clearingList);

                beandao.saveBeansListAndexecuteHqlsByParams(coalescingList,new String[]{},new Object[]{});
            }
        }
    }

    /**
     *   判断时间是否在时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
}
