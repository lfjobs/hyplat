package com.tiantai.wfj.service.impl;

import com.tiantai.wfj.bo.InspectAnnex;
import com.tiantai.wfj.bo.InspectKind;
import com.tiantai.wfj.bo.NfcChip;
import com.tiantai.wfj.bo.SafetyInspect;
import com.tiantai.wfj.service.SafetyInspectService;
import hy.ea.bo.human.Staff;
import hy.ea.util.ImageCut;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SafetyInspectServiceImpl implements SafetyInspectService {
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanService baseBeanService;


    /**
     * 保存
     *
     * @param si
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean saveInspect(SafetyInspect si, String ImagesPath, String VideoPath, String oask) {
        try {
            List<BaseBean> beans = new ArrayList<>();
            si.setSiDate(new Date());
            /*Staff s = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID=?",new Object[]{si.getStaffID()});
            si.setStaffName(s.getStaffName());*/
            if (ImagesPath != null && !"".equals(ImagesPath)) {
                saveAnnex(ImagesPath, si.getSiID(), beans, "I");
            }
            if (VideoPath != null && !"".equals(VideoPath)) {
                saveAnnex(VideoPath, si.getSiID(), beans, "V");
            }
            if (oask != null && !"".equals(oask)) {
                String[] oasks = oask.split(",");
                for (int i = 0; i < oasks.length; i++) {
                    String[] oaskss=oasks[i].split("-");
                    beans.add(saveOask(si.getSiID(),oaskss[0],oaskss[1]));
                }
            }
            beans.add(si);
            beandao.executeHqlsByParmsList(beans,null,null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 保存图片和视频
     *
     * @param filepath
     * @param siid
     * @param beans
     * @param type
     */
    private void saveAnnex(String filepath, String siid, List<BaseBean> beans, String type) {

        String path = ServletActionContext.getRequest()
                .getSession().getServletContext().getRealPath("/");
        String photopath = "";
        InspectAnnex ia = null;
        String[] photos = filepath.split(",");
        for (int i = 0; i < photos.length; i++) {
            ia = new InspectAnnex();
            photopath = photos[i];
            ia.setIaid(serverService.getServerID("InspectAnnex"));
            ia.setSiid(siid);
            switch (type) {
                case "I":
                    String jjPath = photopath.split("\\.")[0] + "small." + photopath.split("\\.")[1];
                    ImageCut.scale(path + photopath, path + jjPath, 414, 431);
                    ia.setFildType("01");
                    ia.setFildPath(jjPath);
                    break;
                case "V":
                    ia.setFildType("02");
                    ia.setFildPath(photopath);
                    break;
            }
            beans.add(ia);
        }
    }

    /**
     * 保存安全分类
     * @param siid
     * @param kid
     * @param name
     * @return
     */
    private InspectKind saveOask( String siid,String kid,String name) {
        InspectKind ik=new InspectKind();
        ik.setIkid(serverService.getServerID("InspectKind"));
        ik.setSiID(siid);
        ik.setKid(kid);
        ik.setKname(name);
        return ik;
    }

    /**
     * 巡查信息列表
     *
     * @param Comid      公司id
     * @param oaskName   安全类别
     * @param staffname  巡查人
     * @param pageNumber 当前页数
     * @param pageSize   每页条数
     * @return
     */
    @Override
    public PageForm getInspectList(String Comid,String ln, String staffname, String oaskName,String sDate,String eDate,String siType, int pageNumber, int pageSize) {
        StringBuffer sql = new StringBuffer("select s.siid,n.nfcid,n.sn,n.ln,n.model,n.oaskname,n.en,n.bindlocation,");
        sql.append("s.illustrate,s.sitype,s.companyid,s.companyname,s.staffid,s.staffname");
        sql.append(" from dt_safetyinspect s left join dt_nfcchip n on s.nfcid = n.nfcid");
        sql.append(" where n.bindstate =? and s.companyid=?");
        List param = new ArrayList();
        param.add("00");
        param.add(Comid);
        if (ln != null && !ln.equals("")) {
            sql.append(" and n.ln =?");
            param.add(staffname);
        }
        if (staffname != null && !staffname.equals("")) {
            sql.append(" and s.staffName like '%?%'");
            param.add(staffname);
        }
        if (oaskName != null && !oaskName.equals("")) {
            sql.append(" and n.oaskName like ?");
            param.add("%"+oaskName+"%");
        }
        if (siType != null && !siType.equals("")) {
            sql.append(" and s.siType = ?");
            param.add(siType);
        }
        if (sDate!=null&&!"".equals(sDate)&&eDate!=null&&!"".equals(eDate)){
            sql.append(" and siDate between ? and ?");
            param.add(Utilities.getDateFromString(sDate,"yyyy-MM-dd HH:mm:ss"));
            param.add(Utilities.getDateFromString(eDate,"yyyy-MM-dd HH:mm:ss"));
        }
        sql.append(" order by siDate desc");
        PageForm pageForm = baseBeanService.getPageFormBySQL((0 < pageNumber ? pageNumber : 1),
                (pageSize == 0 ? 10 : pageSize),
                sql.toString(),
                "select count(1) from (" + sql + " )",
                param.toArray());
        return pageForm;
    }

    /**
     *巡查信息列表
     * @param id
     * @return
     */
    public Object getInspect(String id){
        StringBuffer sql = new StringBuffer("select s.siid,n.nfcid,n.sn,n.ln,n.model,n.oaskname,n.en,n.bindlocation,");
        sql.append("s.illustrate,s.sitype,s.companyid,s.companyname,s.staffid,s.staffname");
        sql.append(" from dt_safetyinspect s left join dt_nfcchip n on s.nfcid = n.nfcid");
        sql.append(" where s.siid=?");
        List param = new ArrayList();
        param.add(id);
        Object i=baseBeanService.getObjectBySqlAndParams(sql.toString(),param.toArray());
        return i;
    }

    public List getAnnex(String id){
        DetachedCriteria dc = DetachedCriteria.forClass(InspectAnnex.class);
        dc.add(Restrictions.eq("siid", id));
        List<BaseBean> iaList=beandao.getListByDC(dc);
        return iaList;
    }

    public List<BaseBean> getInspectKind(String siid){
        DetachedCriteria dc = DetachedCriteria.forClass(InspectKind.class);
        dc.add(Restrictions.eq("siID", siid));
        List<BaseBean> ikList=beandao.getListByDC(dc);
        return ikList;
    }

}
