package hy.ea.marketing.action.supermaket;


import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.PosDevice;
import com.tiantai.wfj.bo.PresetPage;
import com.tiantai.wfj.bo.Scale;
import hy.ea.bo.CAccount;
import hy.ea.marketing.service.PosDeviceManageSerivce;
import hy.ea.marketing.service.SuperSelfSerivce;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 收银机设备
 */
@Controller
@Scope("prototype")
public class PosDeviceManageAction {
    @Resource
    private ServerService serverService;
    @Resource
    private PosDeviceManageSerivce posSerivce;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private SuperSelfSerivce smSerivce;
    private String result;
    private PosDevice posDevice;
    private String search;
    private PageForm pageForm;
    private int pageNumber;
    private String companyID;
    private CAccount account;

    public String toSearch() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("posDevice", posDevice);
        return getPosList();
    }



    private DetachedCriteria getList() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String companyID = account.getCompanyID();
        DetachedCriteria dc = DetachedCriteria.forClass(PosDevice.class);
        PosDevice posDevice = (PosDevice) session.get("posDevice");

        dc.add(Restrictions.eq("comID", companyID));
        if (search != null && search.equals("search")) {
            if (posDevice.getOrganizationName() != null && !"".equals(posDevice.getOrganizationName())) {
                dc.add(Restrictions.like("organizationName", posDevice.getOrganizationName(), MatchMode.ANYWHERE));

            }
            if (posDevice.getPosNum() != null && !"".equals(posDevice.getPosNum())) {
                dc.add(Restrictions.eq("posNum", posDevice.getPosNum()));

            }
            if (posDevice.getState()!= null && !"".equals(posDevice.getState())) {
                dc.add(Restrictions.eq("state", posDevice.getState()));

            }
        }
        return dc;
    }

    // POS终端列表
    public String getPosList() {
        pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), getList());
        return "poslist";
    }

    /**
     * 添加修改
     *
     * @return
     */
    public String addOrUpdate() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return "login";
        }
        posSerivce.addOrUpdate(posDevice, account.getCompanyID(), account.getStaffID());

        return "success";
    }

    /**
     * 删除POS终端
     *
     * @return
     */
    public String deletePos() {
        if (posDevice != null && posDevice.getPosKey() != null && !posDevice.getPosKey().equals("")) {
            posSerivce.delete(posDevice.getPosKey());
        }

        return "success";
    }


    /**
     *
     * 判断设备号是否重复
     * @return
     */
    public  String  checkRepPosNum(){

          String result = posSerivce.checkRepPosNum(posDevice.getPosNum(),posDevice.getPosID());
          Map<String,Object> map = new HashMap<String,Object>();
          map.put("result",result);
          JSONObject jo = JSONObject.fromObject(map);
          this.result = jo.toString();
          return "success";
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

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public PosDevice getPosDevice() {
        return posDevice;
    }

    public void setPosDevice(PosDevice posDevice) {
        this.posDevice = posDevice;
    }

    public CAccount getAccount() {
        return account;
    }

    public void setAccount(CAccount account) {
        this.account = account;
    }


}