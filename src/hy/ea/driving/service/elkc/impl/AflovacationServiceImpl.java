package hy.ea.driving.service.elkc.impl;

import hy.ea.bo.CAccount;
import hy.ea.bo.DrivingSchool.TbJpTeacher;
import hy.ea.bo.DrivingSchool.elyc.ElycTrainerLeave;
import hy.ea.bo.DrivingSchool.elyc.ElycTrainerVacation;
import hy.ea.bo.DrivingSchool.elyc.TbElycSchoolRest;
import hy.ea.driving.dao.AflovacationDao;
import hy.ea.driving.service.elkc.AflovacationService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AflovacationServiceImpl implements AflovacationService {
	private Logger logger = LoggerFactory.getLogger(AflovacationServiceImpl.class);
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private ServerService serverService;

    @Resource
    private AflovacationDao aflovacationDao;


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
     * 获取登录人的公司信息 *
     * @return
     */
    public CAccount companyInformation(){
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();

        CAccount caccount = (CAccount) session.getAttribute("account");

        return caccount;
    }

    /**
     * 获取驾校休假列表
     * @param  companyID :公司id
     * @param pageForm :分页信息
     * @param pageSize :长度
     * @param holidayBegin :开始时间
     * @param holidayEnd :结束时间
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm avacation(String companyID, PageForm pageForm, int pageSize, String holidayBegin, String holidayEnd) {

        StringBuilder sb = new StringBuilder();
        List<Object> list = new ArrayList<Object>();
        sb.append("select t.esrid, cf.staffname,to_char(t.holidaybegin,'YYYY-MM-DD') ,to_char(t.holidayend,'YYYY-MM-DD') , t.status ");
        sb.append("from tb_elyc_school_rest t left join dt_ccom_com c on t.companyid = c.compnay_id ");
        sb.append("left join dtcontactcompany y on c.ccompany_id = y.ccompanyid left join dt_hr_staff cf ");
        sb.append("on t.createperson = cf.staffid where t.companyid = ? ");
        list.add(companyID);
        if(holidayBegin!=null && !holidayBegin.equals(""))
        {
            sb.append("and t.holidaybegin >= to_date(?,'yyyy-mm-dd') ");
            list.add(holidayBegin);
        }
        if(holidayEnd!=null && !holidayEnd.equals(""))
        {
            sb.append("and t.holidayend <= to_date(?,'yyyy-mm-dd') ");
            list.add(holidayEnd);
        }
        sb.append("order by t.createdate desc ");

        PageForm pf = getPageFormBySQL(
                null != pageForm ? pageForm.getPageNumber() : 1,
                pageSize == 0 ? 10 : pageSize, sb.toString(),
                "select count(*) from (" + sb.toString() + ")",list.toArray());
        return pf;
    }

    /**
     * 获取驾校休假信息
     * @param esrId :驾校休假id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Object leaveTheDetails(String esrId) {

        StringBuilder sb = new StringBuilder();
        sb.append("select t.esrKey,t.esrId,to_char(t.holidaybegin,'YYYY-MM-DD'),to_char(t.holidayend,'YYYY-MM-DD') ");
        sb.append("from tb_elyc_school_rest t where t.esrId = ?  ");

        Object object = beandao.getObjectBySqlAndParams(sb.toString(),new Object[]{esrId});

        return object;
    }

    /**
     * 删除驾校休假信息
     * @param esrId :驾校休假id
     * @return
     */
    @Override
    @Transactional
    public boolean delLeaveThe(String esrId) {

        boolean bl = true;

        String hql = "from TbElycSchoolRest where esrId = ? ";

        TbElycSchoolRest tbElycSchoolRest = (TbElycSchoolRest) beandao.getBeanByHqlAndParams(hql,new Object[]{esrId});

        if(tbElycSchoolRest!=null)
        {
            beandao.deleteBeanByKey(tbElycSchoolRest.getClass(),tbElycSchoolRest.getEsrKey());
        }
        else
        {
            bl = false;
        }

        return bl;
    }

    /**
     * 添加修改驾校休假信息
     * @param tbElycSchoolRest :驾校休假信息
     * @param beginDate
     * @param endDate
     * @param account
     * @return 00:成功,01:时间格式不正确,02:已有教练排班无法休假
     */
    @Override
    @Transactional
    public String addOrUpdateLeaveThe(TbElycSchoolRest tbElycSchoolRest, String beginDate, String endDate, CAccount account) {
        String status = "00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = null;
        Date end = null;
        try {
            begin = sdf.parse(beginDate);
            end = sdf.parse(endDate);
        } catch (ParseException e) {
            status = "01";
            return status;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("select count(*) from Tb_Elyc_School_Rest t where t.companyId = ? ");
        sql.append(" and ((? >= t.holidayBegin  and  ? < t.holidayEnd)"); //9:00 =start10:00
        sql.append(" or (? > t.holidayBegin and ? <= t.holidayEnd)");//9:00 end 10:00
        sql.append(" or (t.holidayBegin >= ? and t.holidayEnd <= ?))");
        int cct = beandao.getConutByBySqlAndParams(sql.toString(),new Object[]{account.getCompanyID(),begin,begin,end,end,begin,end});
        if (cct>0)
        {
            status = "03";
            return status;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from Tb_Elyc_Order_Detail_Time t ");
        sb.append("where t.companyid=? ");
        sb.append("and ((to_char(t.lessionstarttime,'yyyy-mm-dd') <= ? and to_char(t.lessionendtime,'yyyy-mm-dd') >= ?) ");
        sb.append("or (to_char(t.lessionstarttime,'yyyy-mm-dd') <= ? and to_char(t.lessionendtime,'yyyy-mm-dd') >= ?) ");
        sb.append("or (to_char(t.lessionstarttime,'yyyy-mm-dd') <= ? and to_char(t.lessionendtime,'yyyy-mm-dd') >= ?)) ");
        int count = beandao.getConutByBySqlAndParams(sb.toString(),new Object[]{account.getCompanyID(),beginDate,beginDate,endDate,endDate,beginDate,endDate});
        if (count==0)
        {
                if (tbElycSchoolRest.getEsrId()!=null && !tbElycSchoolRest.getEsrId().equals(""))
                {
                    tbElycSchoolRest = (TbElycSchoolRest) beandao.getBeanByHqlAndParams("from TbElycSchoolRest where esrId = ?",new Object[]{tbElycSchoolRest.getEsrId()});
                    //修改
                    tbElycSchoolRest.setHolidayBegin(begin);
                    tbElycSchoolRest.setHolidayEnd(end);
                    tbElycSchoolRest.setUpdatedate(new Date());
                    tbElycSchoolRest.setUpdateperson(account.getStaffID());
                }
                else
                {
                    //添加
                    tbElycSchoolRest.setEsrKey(null);
                    tbElycSchoolRest.setEsrId(serverService.getServerID("esrid"));
                    tbElycSchoolRest.setCreatedate(new Date());
                    tbElycSchoolRest.setHolidayBegin(begin);
                    tbElycSchoolRest.setHolidayEnd(end);
                    tbElycSchoolRest.setStatus("00");
                    tbElycSchoolRest.setCreateperson(account.getStaffID());
                    tbElycSchoolRest.setCompanyId(account.getCompanyID());
                }
                beandao.saveOrUpdate(tbElycSchoolRest);
        }
        else
        {
            status = "02";
        }
        return status;
    }

    /**
     * 获取教练休班信息
     * @param companyID :公司id
     * @param pageForm
     * @param pageSize
     * @param date
     * @param tbJpTeacher
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm cVeave(String companyID, PageForm pageForm, int pageSize, String date, TbJpTeacher tbJpTeacher) {

        StringBuilder sb = new StringBuilder();
        List<Object> list = new ArrayList<Object>();
        sb.append("select v.etvid,t.name,t.sex,t.idcard,t.mobile, ");
        sb.append("to_char(v.start_time,'YYYY-MM-DD'),to_char(v.end_time,'YYYY-MM-DD'),v.status ");
        sb.append("from tb_elyc_trainer_Vacation v, tb_jp_teacher t ");
        sb.append("where v.company_id = ? ");
        list.add(companyID);
        if(date!=null && !date.equals(""))
        {
            sb.append("and v.start_time<= to_date(?,'yyyy-mm-dd') and v.end_time>= to_date(?,'yyyy-mm-dd') ");
            list.add(date);
            list.add(date);
        }
        if (tbJpTeacher!=null)
        {
            if (tbJpTeacher.getName()!=null && !tbJpTeacher.getName().equals(""))
            {
                sb.append("and t.name like ? ");
                list.add("%"+tbJpTeacher.getName()+"%");
            }
            else if (tbJpTeacher.getIdcard()!=null && !tbJpTeacher.getIdcard().equals(""))
            {
                sb.append("and t.idcard like ? ");
                list.add("%"+tbJpTeacher.getIdcard()+"%");
            }
        }
        sb.append("and v.trainer_id = t.teacherid order by v.createdate desc ");

        PageForm pf = getPageFormBySQL(
                null != pageForm ? pageForm.getPageNumber() : 1,
                pageSize == 0 ? 10 : pageSize, sb.toString(),
                "select count(*) from (" + sb.toString() + ")",list.toArray());
        return pf;
    }

    /**
     * 获取教练休班详情
     * @param etvId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Object coachVeaveDetails(String etvId) {

        StringBuilder sb = new StringBuilder();

        sb.append("select v.etvkey,v.etvid,t.name,t.sex,t.idcard,t.mobile, ");
        sb.append("to_char(v.start_time,'YYYY-MM-DD'),to_char(v.end_time,'YYYY-MM-DD'),v.trainer_id ");
        sb.append("from tb_elyc_trainer_Vacation v, tb_jp_teacher t ");
        sb.append("where v.etvid = ? and v.trainer_id = t.teacherid");

        Object object = beandao.getObjectBySqlAndParams(sb.toString(),new Object[]{etvId});

        return object;
    }



    /**
     * 删除教练休班信息
     * @param etvId :教练休假id
     * @return
     */
    @Override
    @Transactional
    public boolean delCoachVeave(String etvId) {

        boolean bl = true;

        String hql = "from ElycTrainerVacation where etvId = ? ";

        ElycTrainerVacation elycTrainerVacation = (ElycTrainerVacation) beandao.getBeanByHqlAndParams(hql,new Object[]{etvId});

        if(elycTrainerVacation!=null)
        {
            beandao.deleteBeanByKey(elycTrainerVacation.getClass(),elycTrainerVacation.getEtvKey());
        }
        else
        {
            bl = false;
        }

        return bl;
    }



    /**
     * 获取教练员列表
     * @param companyID :公司id
     * @param pageForm
     *@param tbJpTeacher @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm coachList(String companyID, PageForm pageForm, TbJpTeacher tbJpTeacher) {

        StringBuilder sb = new StringBuilder();
        List<Object> list = new ArrayList<Object>();
        sb.append("select r.teacherId,r.name,r.idcard,r.mobile,r.sex ");
        sb.append("from tb_jp_teacher r ");
        sb.append("where r.companyId = ? ");
        list.add(companyID);
        if (tbJpTeacher!=null && tbJpTeacher.getName()!=null && !tbJpTeacher.getName().equals(""))
        {
            sb.append("and r.name like ? ");
            list.add("%"+tbJpTeacher.getName()+"%");
        }
        sb.append("order by r.createperson desc ");


        PageForm pf = getPageFormBySQL( pageForm.getPageNumber(),
                pageForm.getPageSize()==0?10 : pageForm.getPageSize(), sb.toString(),
                "select count(*) from (" + sb.toString() + ")",list.toArray());
        return pf;
    }

    /**
     * 添加修改教练休班信息
     * @param elycTrainerVacation :教练休假信息
     * @param beginDate
     * @param endDate
     * @param account
     * @return 00:成功,01:时间格式不正确,02:教练已排班无法休班
     */
    @Override
    @Transactional
    public String addOrUpdateCoach(ElycTrainerVacation elycTrainerVacation, String beginDate, String endDate, CAccount account) {
        String status = "00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = null;
        Date end = null;
        try {
            begin = sdf.parse(beginDate);
            end = sdf.parse(endDate);
        } catch (ParseException e) {
            status = "01";
            return status;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("select count(*) from tb_elyc_trainer_Vacation t where t.trainer_id = ? and t.company_id = ? ");
        sql.append(" and ((? >= t.start_time  and  ? < t.end_time)"); //9:00 =start10:00
        sql.append(" or (? > t.start_time and ? <= t.end_time)");//9:00 end 10:00
        sql.append(" or (t.start_time >= ? and t.end_time <= ?))");
        int cct = beandao.getConutByBySqlAndParams(sql.toString(),new Object[]{elycTrainerVacation.getTrainer_id(),account.getCompanyID(),begin,begin,end,end,begin,end});
        if (cct>0)
        {
            status = "03";
            return status;
        }


        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from Tb_Elyc_Order_Detail_Time t ");
        sb.append("where t.companyid=? and t.teacherId = ? ");
        sb.append("and ((to_char(t.lessionstarttime,'yyyy-mm-dd') <= ? and to_char(t.lessionendtime,'yyyy-mm-dd') >= ?) ");
        sb.append("or (to_char(t.lessionstarttime,'yyyy-mm-dd') <= ? and to_char(t.lessionendtime,'yyyy-mm-dd') >= ?) ");
        sb.append("or (to_char(t.lessionstarttime,'yyyy-mm-dd') <= ? and to_char(t.lessionendtime,'yyyy-mm-dd') >= ?)) ");
        int count = beandao.getConutByBySqlAndParams(sb.toString(),new Object[]{account.getCompanyID(),elycTrainerVacation.getTrainer_id(),beginDate,beginDate,endDate,endDate,beginDate,endDate});
        if (count==0)
        {
            if (elycTrainerVacation.getEtvId()!=null && !elycTrainerVacation.getEtvId().equals(""))
            {
                ElycTrainerVacation ev = (ElycTrainerVacation) beandao.getBeanByHqlAndParams("from ElycTrainerVacation where etvId = ?",new Object[]{elycTrainerVacation.getEtvId()});
                //修改
                ev.setTrainer_id(elycTrainerVacation.getTrainer_id());
                ev.setStart_time(begin);
                ev.setEnd_time(end);
                ev.setUpdatedate(new Date());
                ev.setUpdateperson(account.getStaffID());
                beandao.update(ev);
            }
            else
            {
                //添加
                elycTrainerVacation.setEtvKey(null);
                elycTrainerVacation.setEtvId(serverService.getServerID("etvid"));
                elycTrainerVacation.setCreatedate(new Date());
                elycTrainerVacation.setStart_time(begin);
                elycTrainerVacation.setEnd_time(end);
                elycTrainerVacation.setStatus("00");
                elycTrainerVacation.setCreateperson(account.getStaffID());
                elycTrainerVacation.setCompany_id(account.getCompanyID());
                beandao.save(elycTrainerVacation);
            }
        }
        else
        {
            status = "02";
        }
        return status;
    }

    /**
     * 获取教练请假信息
     * @param companyID :公司id
     * @param pageForm
     * @param pageSize
     * @param date
     * @param tbJpTeacher
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm cafLeave(String companyID, PageForm pageForm, int pageSize, String date, TbJpTeacher tbJpTeacher) {
        StringBuilder sb = new StringBuilder();
        List<Object> list = new ArrayList<Object>();
        sb.append("select v.etlid,r.name as rname,r.idcard,r.mobile, ");
        sb.append("to_char(v.leave_date, 'YYYY-MM-DD'),to_char(v.start_time, 'YYYY-MM-DD HH24:MI:SS'), ");
        sb.append("to_char(v.end_time, 'YYYY-MM-DD HH24:MI:SS'),e.name as ename,v.status ");
        sb.append("from tb_elyc_Trainer_Leave v left join tb_jp_teacher r on v.trainer_id = r.teacherid ");
        sb.append("left join tb_jp_teacher e on v.relay_trainer_id = e.teacherid where v.company_id = ? ");
        list.add(companyID);
        if(date!=null && !date.equals(""))
        {
            sb.append("and v.start_time<= to_date(?,'YYYY-MM-DD HH24:MI:SS') and v.end_time>= to_date(?,'YYYY-MM-DD HH24:MI:SS') ");
            list.add(date);
            list.add(date);
        }
        if (tbJpTeacher!=null)
        {
            if (tbJpTeacher.getName()!=null && !tbJpTeacher.getName().equals(""))
            {
                sb.append("and r.name like ? ");
                list.add("%"+tbJpTeacher.getName()+"%");
            }
            else if (tbJpTeacher.getIdcard()!=null && !tbJpTeacher.getIdcard().equals(""))
            {
                sb.append("and r.idcard like ? ");
                list.add("%"+tbJpTeacher.getIdcard()+"%");
            }
        }
        sb.append("order by v.createdate desc ");

        PageForm pf = getPageFormBySQL(
                null != pageForm ? pageForm.getPageNumber() : 1,
                pageSize == 0 ? 10 : pageSize, sb.toString(),
                "select count(*) from (" + sb.toString() + ")",list.toArray());
        return pf;
    }

    /**
     * 删除教练请假信息
     * @param etlId:请假信息id
     * @return
     */
    @Override
    @Transactional
    public boolean delCafLeave(String etlId) {
        boolean bl = true;


        String hql = "from ElycTrainerLeave where etlId = ? ";

        ElycTrainerLeave elycTrainerLeave = (ElycTrainerLeave) beandao.getBeanByHqlAndParams(hql,new Object[]{etlId});

        if(elycTrainerLeave!=null)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update tb_elyc_order_record d set d.replaceTeacherId = ?,d.replaceTeacherName = ?  ");
            sb.append("where d.teacherid = ? and d.status = ? ");
            sb.append("and d.lessionstarttime between ");
            sb.append("? and ? ");
            beandao.saveBeansListAndexecuteSqlsByParams(null,new String[]{sb.toString()},new Object[]{"","",elycTrainerLeave.getTrainer_id(),"1",elycTrainerLeave.getStart_time(),elycTrainerLeave.getEnd_time()});

            StringBuffer sb2 = new StringBuffer();
            sb2.append("update Tb_Elyc_Order_Detail_Time t set t.status = ? where t.teacherId = ? and t.companyId = ? and t.status = ?");
            sb2.append(" and ((? >= t.lessionStartTime  and  ? < t.lessionEndTime)"); //9:00 =start10:00
            sb2.append(" or (? > t.lessionStartTime and ? <= t.lessionEndTime)");//9:00 end 10:00
            sb2.append(" or (t.lessionStartTime >= ? and t.lessionEndTime <= ?))");
            beandao.saveBeansListAndexecuteSqlsByParams(null,new String[]{sb2.toString()},new Object[]{"2",elycTrainerLeave.getTrainer_id(),elycTrainerLeave.getCompany_id(),"3",elycTrainerLeave.getStart_time(),elycTrainerLeave.getStart_time(),elycTrainerLeave.getEnd_time(),elycTrainerLeave.getEnd_time(),elycTrainerLeave.getStart_time(),elycTrainerLeave.getEnd_time()});

            beandao.deleteBeanByKey(elycTrainerLeave.getClass(),elycTrainerLeave.getEtlKey());
        }
        else
        {
            bl = false;
        }

        return bl;
    }

    /**
     * 获取教练请假详情
     * @param etlId :请假信息id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Object cafLeaveDetails(String etlId) {

        StringBuilder sb = new StringBuilder();
        sb.append("select v.etlkey,v.etlid,r.name as rname,r.sex,r.idcard,r.mobile, ");
        sb.append("to_char(v.leave_date, 'YYYY-MM-DD'),to_char(v.start_time, 'YYYY-MM-DD HH24:MI:SS'), ");
        sb.append("to_char(v.end_time, 'YYYY-MM-DD HH24:MI:SS'),v.relay_trainer_id,e.name as ename,v.trainer_id ");
        sb.append("from tb_elyc_Trainer_Leave v, tb_jp_teacher r, tb_jp_teacher e ");
        sb.append("where v.etlid = ? and v.trainer_id = r.teacherid and v.relay_trainer_id = e.teacherid ");

        Object object = beandao.getObjectBySqlAndParams(sb.toString(),new Object[]{etlId});

        return object;
    }

    /**
     * 获取替班教练列表
     * @param companyID :公司id
     * @param pageForm
     * @param date
     * @param tbJpTeacher
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm offDutyCoachList(String companyID, PageForm pageForm, String date, TbJpTeacher tbJpTeacher) {

        StringBuilder sb = new StringBuilder();
        List<Object> list = new ArrayList<Object>();
        sb.append("select r.teacherId, r.name, r.idcard, r.mobile, r.sex ");
        sb.append("from tb_jp_teacher r, tb_elyc_trainer_Vacation v ");
        sb.append("where r.companyid = ? ");
        sb.append("and r.teacherid = v.trainer_id and v.status = ? ");
        list.add(companyID);
        list.add("00");
        if (tbJpTeacher!=null && tbJpTeacher.getName()!=null && !tbJpTeacher.getName().equals(""))
        {
            sb.append("and r.name like ? ");
            list.add("%"+tbJpTeacher.getName()+"%");
        }
        sb.append("and trunc(v.start_time, 'dd') = trunc(to_date( ? , 'yyyy-mm-dd hh24:mi:ss'), 'dd') ");
        list.add(date);
        sb.append("and r.teacherid!= ? and r.teacherId not in ");
        list.add(tbJpTeacher.getTeacherId());
        sb.append("(select l.relay_trainer_id from tb_elyc_trainer_leave l ");
        sb.append(" where trunc(l.start_time, 'dd') = trunc(to_date( ? , 'yyyy-mm-dd hh24:mi:ss'), 'dd') ");
        list.add(date);
        sb.append("and l.company_id = ?) order by v.start_time ");
        list.add(companyID);
        PageForm pf = getPageFormBySQL(
                null != pageForm ? pageForm.getPageNumber() : 1,
                pageForm.getPageSize() == 0 ? 10 : pageForm.getPageSize(), sb.toString(),
                "select count(*) from (" + sb.toString() + ")",list.toArray());

        return pf;
    }

    /**
     * 添加修改教练请假信息
     * @param elycTrainerLeave :教练请假信息
     * @param beginDate
     * @param endDate
     * @param leaveData
     * @param account
     * @param substitutename
     * @return 00:成功,01:时间格式不正确,02:请勿重复添加请假信息
     */
    @Override
    @Transactional
    public String addOrUpdateAskForLeave(ElycTrainerLeave elycTrainerLeave, String beginDate, String endDate, String leaveData, CAccount account, String substitutename) {
        String status = "00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = null;
        Date end = null;
        Date leave = null;
        try {
            begin = sdf.parse(beginDate);
            end = sdf.parse(endDate);
            leave = sd.parse(leaveData);
        } catch (ParseException e) {
            status = "01";
            return status;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from tb_elyc_Trainer_Leave t where t.trainer_id = ? and t.company_id = ? ");
        sb.append(" and ((? >= t.start_time  and  ? < t.end_time)"); //9:00 =start10:00
        sb.append(" or (? > t.start_time and ? <= t.end_time)");//9:00 end 10:00
        sb.append(" or (t.start_time >= ? and t.end_time <= ?))");
        int count = beandao.getConutByBySqlAndParams(sb.toString(),new Object[]{elycTrainerLeave.getTrainer_id(),account.getCompanyID(),begin,begin,end,end,begin,end});

        if (count>0)
        {
            status = "02";
            return status;
        }

        if (elycTrainerLeave.getRelay_trainer_id()!=null && !elycTrainerLeave.getRelay_trainer_id().equals(""))
        {
            aflovacationDao.updateOrderRecord(elycTrainerLeave.getTrainer_id(),substitutename,elycTrainerLeave.getRelay_trainer_id(),begin,end);
        }
        else
        {
            StringBuffer sb2 = new StringBuffer();
            sb2.append("update Tb_Elyc_Order_Detail_Time t set t.status = ?  where t.teacherId = ? and t.companyId = ? and t.status = ?");
            sb2.append(" and ((? >= t.lessionStartTime  and  ? < t.lessionEndTime)"); //9:00 =start10:00
            sb2.append(" or (? > t.lessionStartTime and ? <= t.lessionEndTime)");//9:00 end 10:00
            sb2.append(" or (t.lessionStartTime >= ? and t.lessionEndTime <= ?))");//=start9:00  10：00 end=
            beandao.saveBeansListAndexecuteSqlsByParams(null,new String[]{sb2.toString()},new Object[]{"3",elycTrainerLeave.getTrainer_id(),account.getCompanyID(),"2",begin,begin,end,end,begin,end});
        }


        if (elycTrainerLeave.getEtlId()!=null && !elycTrainerLeave.getEtlId().equals(""))
        {
            ElycTrainerLeave etLeave = (ElycTrainerLeave) beandao.getBeanByHqlAndParams("from ElycTrainerLeave where etlId = ?",new Object[]{elycTrainerLeave.getEtlId()});
            //修改
            etLeave.setTrainer_id(elycTrainerLeave.getTrainer_id());
            etLeave.setLeave_date(leave);
            etLeave.setStart_time(begin);
            etLeave.setEnd_time(end);
            etLeave.setRelay_trainer_id(elycTrainerLeave.getRelay_trainer_id());
            etLeave.setUpdateperson(account.getStaffID());
            etLeave.setUpdatedate(new Date());
            beandao.update(etLeave);
        }
        else
        {
            //添加
            elycTrainerLeave.setEtlKey(null);
            elycTrainerLeave.setEtlId(serverService.getServerID("etlid"));
            elycTrainerLeave.setLeave_date(leave);
            elycTrainerLeave.setStart_time(begin);
            elycTrainerLeave.setEnd_time(end);
            elycTrainerLeave.setStatus("00");
            elycTrainerLeave.setCompany_id(account.getCompanyID());
            elycTrainerLeave.setCreateperson(account.getStaffID());
            elycTrainerLeave.setCreatedate(new Date());
            beandao.save(elycTrainerLeave);
        }
        return status;
    }


}
