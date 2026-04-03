package hy.ea.driving.service.impl;

import hy.ea.bo.DrivingSchool.elyc.TbElycCompanyConfig;
import hy.ea.bo.DrivingSchool.elyc.TbElycScheduling;
import hy.ea.bo.DrivingSchool.elyc.TbElycTeacherRScheduling;
import hy.ea.driving.service.ElkcSchedulingService;
import hy.ea.driving.service.ElkcTeacherPbService;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ElkcTeacherPbServiceImpl implements ElkcTeacherPbService {
    @Resource
    private BaseBeanDao basedao;
    @Resource
    private ServerService serverService;


    /***
     *
     * 保存班次
     * @param companyID
     * @param staffID
     */
    @Transactional
    @Override
    public void saveTeacherPb(String teachers, String classID, String companyID, String staffID) {
        if (teachers != null && !teachers.equals("")) {
            List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
            String[] teacherarry = teachers.split(",");
            TbElycTeacherRScheduling rscheduling = null;
            String hql = "from TbElycTeacherRScheduling where teacherId = ?";
            TbElycScheduling scheduling = null;
            String hqlsc = "from TbElycScheduling where classId = ?";
            for (int i = 0; i < teacherarry.length; i++) {
                rscheduling = (TbElycTeacherRScheduling) basedao.getBeanByHqlAndParams(hql, new Object[]{teacherarry[i]});
                if (rscheduling == null) {
                    rscheduling = new TbElycTeacherRScheduling();
                    rscheduling.setTrsId(serverService.getServerID("trsId"));
                    rscheduling.setCompanyId(companyID);
                    rscheduling.setTeacherId(teacherarry[i]);
                    rscheduling.setCreateperson(staffID);
                    rscheduling.setCreatedate(new Date());
                    rscheduling.setStatus("00");
                    scheduling = (TbElycScheduling)basedao.getBeanByHqlAndParams(hqlsc,new Object[]{classID});
                    scheduling.setStatus("01");
                    baseBeanList.add(scheduling);

                }
                rscheduling.setClassId(classID);
                rscheduling.setUpdateperson(staffID);
                rscheduling.setUpdatedate(new Date());
                baseBeanList.add(rscheduling);

            }

                String hqlconfig = "from TbElycCompanyConfig where companyId = ?";
                TbElycCompanyConfig config = (TbElycCompanyConfig)basedao.getBeanByHqlAndParams(hqlconfig,new Object[]{companyID});
                if(config!=null&&config.getIsUsed()!=null&&config.getIsUsed().equals("0")){
                    config.setIsUsed("1");
                    baseBeanList.add(config);
                }


            basedao.saveBeansListAndexecuteHqlsByParams(baseBeanList, null, null);
        }

    }

    /**
     * 获取当前公司的班次启用的班次
     *
     * @param companyID
     * @return
     */
    public List<BaseBean> getSchedulingList(String companyID) {

        String hql = "from TbElycScheduling where companyId = ? and status = ?";
        List<BaseBean> list = basedao.getListBeanByHqlAndParams(hql, new Object[]{companyID, "01"});
        return list;
    }


    /**
     *
     * 删除
     * @param trsId
     */
    @Transactional
    public void deleteTeacherPbById(String trsId){
        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        TbElycTeacherRScheduling rscheduling = (TbElycTeacherRScheduling)basedao.getBeanByHqlAndParams("from TbElycTeacherRScheduling s where s.trsId = ? ",new Object[]{trsId});
        String hql1 = "from TbElycTeacherRScheduling t where t.classId = ? and trsId != ?";
        List<BaseBean> list = basedao.getListBeanByHqlAndParams(hql1,new Object[]{rscheduling.getClassId(),trsId});
        if(list.size()==0){
            TbElycScheduling tb = (TbElycScheduling)basedao.getBeanByHqlAndParams("from  TbElycScheduling where classId = ?",new Object[]{rscheduling.getClassId()});
            tb.setStatus("00");
            baseBeanList.add(tb);

        }
        String hql = "delete from TbElycTeacherRScheduling where trsId = ?";

        basedao.saveBeansListAndexecuteHqlsByParams(baseBeanList,new String[]{hql},new Object[]{trsId});

    }



}
