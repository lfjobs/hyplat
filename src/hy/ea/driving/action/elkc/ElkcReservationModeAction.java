package hy.ea.driving.action.elkc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.DrivingSchool.elyc.TbElycCompanyConfig;
import hy.ea.bo.DrivingSchool.elyc.TbElycReservationDetail;
import hy.ea.bo.DrivingSchool.elyc.TbElycReservationMode;
import hy.ea.driving.service.ElkcCompanyConfigService;
import hy.ea.driving.service.ElkcReservModeService;
import hy.ea.util.elkc.DateUtilElkc;
import hy.ea.util.elkc.JsonUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.*;

@Controller
@Scope("prototype")
public class ElkcReservationModeAction {
	private static final Logger logger = LoggerFactory.getLogger(ElkcReservationModeAction.class);

    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;

    private TbElycReservationMode reservationMode;
    @Resource
    private ElkcReservModeService reservModeService;
    @Resource
    private ElkcCompanyConfigService configService;

    private List<TbElycReservationDetail> detailList;

    private PageForm pageForm;
    private int pageNumber;
    private String search;
    private String modeId;


    Map<String, Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount) session.get("account");

    private String result;

    /**
     * 保存预约模式
     *
     * @return
     */
    public String saveReservMode() {
        if (account == null) {
            return "login";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        String  msg = "";
        try{
             msg = this.reservModeService.updateReservationMode(this.reservationMode, this.detailList, account.getStaffID(), account.getCompanyID());


        }catch (Exception e){
            logger.error("操作异常", e);
        }
        map.put("msg",msg);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }



    /**
     * 修改时获取内容
     *
     * @return
     */
    public String reservationModeEdit() {
        if (account == null) {
            return "login";
        }

            if(reservationMode!=null&&StringUtils.isNotBlank(reservationMode.getReservationModeId())) {
                this.reservationMode = this.reservModeService.getReservModeById(reservationMode.getReservationModeId());


                this.detailList = this.reservModeService.getDetailObjectsByModeId(reservationMode.getReservationModeId());
                Collections.sort(this.detailList, new Comparator<TbElycReservationDetail>() {

                    public int compare(TbElycReservationDetail arg0, TbElycReservationDetail arg1) {
                        logger.info("调试信息");
                        logger.info("调试信息");
                        Date date1 = DateUtilElkc.toDateTime("2015-11-11 " + arg0.getStartTime() + ":00");
                        Date date2 = DateUtilElkc.toDateTime("2015-11-11 " + arg1.getStartTime() + ":00");
                        return date1.compareTo(date2);
                    }
                });
            } else {
                this.reservationMode = new TbElycReservationMode();
                this.detailList = new ArrayList();
            }

            return "modeadd";
    }


    /**
     * 列表
     *
     * @return
     */
    public String findReservationModeList() {
        if (account == null) {
            return "login";
        }


        pageForm = baseBeanService.getPageFormByDC(
                (null != pageForm ? pageForm.getPageNumber() : 1),
                (pageNumber == 0 ? 10 : pageNumber), getList());


        return "modelist";
    }


    public String toSearch() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("tablesearch", reservationMode);
        return findReservationModeList();
    }

    private DetachedCriteria getList() {

        String companyId = account.getCompanyID();

        DetachedCriteria dc = DetachedCriteria.forClass(TbElycReservationMode.class);
        dc.add(Restrictions.eq("companyId", companyId));

        if (search != null && search.equals("search")) {
            reservationMode = (TbElycReservationMode) session.get("tablesearch");
            if (reservationMode.getModeName() != null
                    && !"".equals(reservationMode.getModeName())) {
                dc.add(Restrictions.like("modeName", reservationMode.getModeName(), MatchMode.ANYWHERE));
            }
            if (reservationMode.getStatus() != null
                    && !"".equals(reservationMode.getStatus())) {
                dc.add(Restrictions.eq("status", reservationMode.getStatus()));
            }
        }
        return dc;
    }

    /**
     * 点击一件生成
     *
     * @return
     * @throws Exception
     */
    public String showReservationInit() throws Exception {


        TbElycCompanyConfig config = configService.findCompanyConfig(account.getCompanyID());
        Map<String, Object> map = new HashMap<String, Object>();
        if (config == null) {
            map.put("msg", "请先完善约车配置");

        } else {
            if (StringUtils.isNotBlank(config.getStartTime()) && StringUtils.isNotBlank(config.getEndTime())) {
                Date startdate = DateUtilElkc.toDateTime("2015-11-11 " + config.getStartTime());
                Date enddate = DateUtilElkc.toDateTime("2015-11-11 " + config.getEndTime());
                this.detailList = new ArrayList();
                if (enddate.compareTo(startdate) > 0) {
                    int between = (int) DateUtilElkc.getDifftime(enddate, startdate, "hh");

                    for (int i = 0; i < between; ++i) {
                        TbElycReservationDetail detail = new TbElycReservationDetail();
                        detail.setStartTime(DateUtilElkc.getDateTimeByPattern(DateUtilElkc.addminute(startdate, i * 60), "HH:mm"));
                        detail.setEndTime(DateUtilElkc.getDateTimeByPattern(DateUtilElkc.addminute(startdate, (i + 1) * 60), "HH:mm"));
                        this.detailList.add(detail);
                    }
                }

                //	this.modeId = JsonUtil.getJsonString4JavaList(this.detailList, "yyyy-MM-dd");
                map.put("msg", "");
                map.put("detailList", detailList);
            } else {
                map.put("msg", "请先完善约车配置");
            }
        }

        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();

        return "success";
    }

    /**
     *
     * 删除
     * @return
     */
    public String deleteMode(){
        if(reservationMode!=null&&!reservationMode.getReservationModeId().equals("")){
            try {
                reservModeService.deleteModeById(reservationMode.getReservationModeId());
            } catch (Exception e) {
                logger.error("操作异常", e);
            }

        }

     return  "success";
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public TbElycReservationMode getReservationMode() {
        return reservationMode;
    }

    public void setReservationMode(TbElycReservationMode reservationMode) {
        this.reservationMode = reservationMode;
    }

    public List<TbElycReservationDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<TbElycReservationDetail> detailList) {
        this.detailList = detailList;
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


    public String getModeId() {
        return modeId;
    }

    public void setModeId(String modeId) {
        this.modeId = modeId;
    }
}
