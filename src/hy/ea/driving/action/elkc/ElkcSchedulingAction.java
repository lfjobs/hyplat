package hy.ea.driving.action.elkc;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.DrivingSchool.elyc.TbElycReservationDetail;
import hy.ea.bo.DrivingSchool.elyc.TbElycReservationMode;
import hy.ea.bo.DrivingSchool.elyc.TbElycScheduling;
import hy.ea.bo.DrivingSchool.elyc.TbElycSchedulingDetail;
import hy.ea.driving.service.ElkcReservModeService;
import hy.ea.driving.service.ElkcSchedulingService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import sun.swing.BakedArrayList;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
public class ElkcSchedulingAction {

    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;

    private TbElycScheduling scheduling;
    private TbElycSchedulingDetail schedulingDetail;
    @Resource
    private ElkcSchedulingService schedulingService;
    private List<TbElycReservationMode> modelist;
    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount) session.get("account");
    private String result;
    private PageForm pageForm;
    private int pageNumber;
    private String search;

    /**
     * 保存班次以及关联时间模式
     *
     * @return
     */
    public String saveScheduling() {
        if (account == null) {
            return "login";
        }
        schedulingService.saveScheduling(scheduling,schedulingDetail,account.getCompanyID(), account.getStaffID());

        return "success";
    }

    /**
     * 列表
     *
     * @return
     */
    public String findSchedulingList() {
        if (account == null) {
            return "login";
        }

        pageForm = getList();
        modelist = schedulingService.getModeList(account.getCompanyID());


        return "schedulelist";
    }

    public String toSearch() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("tablesearch", scheduling);
        return findSchedulingList();
    }

    private PageForm getList() {
        String companyId = account.getCompanyID();
        StringBuilder sb = new StringBuilder();
        sb.append("select a.classId,a.className,a.status,a.createdate,a.updatedate,b.reservationModeId,c.modeName from tb_elyc_scheduling a,tb_elyc_scheduling_detail b,Tb_Elyc_Reservation_Mode c where a.classId = b.classId and b.reservationModeId =c.reservationModeId and a.companyId = ?");

        List<Object> params = new ArrayList<Object>();
        params.add(companyId);
        if (search != null && search.equals("search")) {
            scheduling = (TbElycScheduling) session.get("tablesearch");
            if (scheduling.getClassName() != null
                    && !"".equals(scheduling.getClassName())) {
                sb.append(" and a.className like ?");
                params.add("%" + scheduling.getClassName() + "%");
            }
            if (scheduling.getStatus() != null
                    && !"".equals(scheduling.getStatus())) {
                sb.append(" and a.status = ?");
                params.add(scheduling.getStatus());
            }
        }
        pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
                (pageNumber == 0 ? 10 : pageNumber), sb.toString(), "select count(*) "+sb.toString().substring(sb.toString().indexOf("from")), params.toArray());


        return pageForm;
    }

    /**
     *
     * 删除
     * @return
     */
    public String deleteSchedul() {
        if (scheduling != null && !scheduling.getClassId().equals("")) {
            try {
                schedulingService.deleteSchedul(scheduling.getClassId());
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

    public TbElycScheduling getScheduling() {
        return scheduling;
    }

    public void setScheduling(TbElycScheduling scheduling) {
        this.scheduling = scheduling;
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

    public TbElycSchedulingDetail getSchedulingDetail() {
        return schedulingDetail;
    }

    public void setSchedulingDetail(TbElycSchedulingDetail schedulingDetail) {
        this.schedulingDetail = schedulingDetail;
    }

    public List<TbElycReservationMode> getModelist() {
        return modelist;
    }

    public void setModelist(List<TbElycReservationMode> modelist) {
        this.modelist = modelist;
    }
}
