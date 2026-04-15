package hy.ea.driving.action.elkc;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.DrivingSchool.elyc.TbElycTeacherRScheduling;
import hy.ea.driving.service.ElkcTeacherPbService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 教练排班
 */
@Controller
@Scope("prototype")
public class ElkcTeacherSchedulingAction {

    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private ElkcTeacherPbService teacherPbService;

    private TbElycTeacherRScheduling teacherRScheduling;

    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount) session.get("account");
    private String result;
    private PageForm pageForm;
    private int pageNumber;
    private String search;
    private List<BaseBean> schedullist;
    private String teachers;//,.,.,.
    private String classId;

    /**
     * 保存预约模式
     *
     * @return
     */
    public String saveTeacherPb() {
        if (account == null) {
            return "login";
        }
        teacherPbService.saveTeacherPb(teachers, classId, account.getCompanyID(), account.getStaffID());

        return "success";
    }


    /**
     * 列表
     *
     * @return
     */
    public String findTeacherPbList() {
        if (account == null) {
            return "login";
        }

        pageForm = getList();

        //查询出班次
        schedullist = teacherPbService.getSchedulingList(account.getCompanyID());

        return "tslist";
    }

    public String toSearch() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("tablesearch", teacherRScheduling);
        return findTeacherPbList();
    }

    private PageForm getList() {

        String companyId = account.getCompanyID();
        StringBuilder sb = new StringBuilder();
        sb.append("select t.teacherid,s.trsid,e.classid,t.name, t.mobile, t.idcard,s.updatedate,e.className,s.status from tb_jp_teacher t");
        sb.append(" left join Tb_Elyc_Teacher_RScheduling s on t.teacherid = s.teacherid");
        sb.append(" left join tb_elyc_scheduling e on s.classid = e.classid where t.companyId = ?");

        List<Object> params = new ArrayList<Object>();
        params.add(companyId);
        if (search != null && search.equals("search")) {
            teacherRScheduling = (TbElycTeacherRScheduling) session.get("tablesearch");
            if (teacherRScheduling.getTeacherName() != null
                    && !"".equals(teacherRScheduling.getTeacherName())) {
                sb.append(" and t.name like ?");
                params.add("%" + teacherRScheduling.getTeacherName() + "%");
            }
            if (teacherRScheduling.getIdcard() != null
                    && !"".equals(teacherRScheduling.getIdcard())) {
                sb.append(" and t.idcard = ?");
                params.add(teacherRScheduling.getIdcard());
            }
            if (teacherRScheduling.getClassName() != null
                    && !"".equals(teacherRScheduling.getClassName())) {
                sb.append(" and e.classname = ?");
                params.add(teacherRScheduling.getClassName());
            }
            if (teacherRScheduling.getClassId() != null
                    && !"".equals(teacherRScheduling.getClassId())) {
               if(teacherRScheduling.getClassId().equals("00")){
                   sb.append(" and s.classId is null");
               }else{
                   sb.append(" and s.classId is not null");


               }

            }
        }
        pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
                (pageNumber == 0 ? 10 : pageNumber), sb.toString(), "select count(*) " + sb.toString().substring(sb.toString().indexOf("from")), params.toArray());


        return pageForm;
    }

    /**
     * 删除
     *
     * @return
     */
    public String deleteTeacherPb() {
        if (teacherRScheduling != null && !teacherRScheduling.getTrsId().equals("")) {
            try {
                teacherPbService.deleteTeacherPbById(teacherRScheduling.getTrsId());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return "success";
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public TbElycTeacherRScheduling getTeacherRScheduling() {
        return teacherRScheduling;
    }

    public void setTeacherRScheduling(TbElycTeacherRScheduling teacherRScheduling) {
        this.teacherRScheduling = teacherRScheduling;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<BaseBean> getSchedullist() {
        return schedullist;
    }

    public void setSchedullist(List<BaseBean> schedullist) {
        this.schedullist = schedullist;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }
}
