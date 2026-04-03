package hy.ea.finance.action.BenDis;

import hy.ea.bo.Company;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.lottery.PrizeActivityBean;
import hy.ea.bo.lottery.SignScoreBean;
import hy.ea.finance.service.BonusPointsService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingInAction {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private BonusPointsService bonusPointsService;

    private Object result;

    public String isPhoneSignCount(){
        Map<String, Object> map = new HashMap();
        map.put("oi",signList("company201009046vxdyzy4wg0000000025","A8F6A67664C64B4A94F8C3AC3017A7B6"));
        map.put("oz",signList("company201009046vxdyzy4wg0000000025","TEshopCusCom202011066V6GKD9SWM0000090221"));
        map.put("og",signList("company201009046vxdyzy4wg0000000025","TEshopCusCom20170803P46FMBT7FS0000000159"));
        JSONObject jsonObj = JSONObject.fromObject(map);
        this.result = jsonObj;
        return "success";
    }


    public String toSignPhone()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        String signSite = request.getParameter("signSite");
        String account = null;//签到人帐号
        String sccid = null;
        if (signSite .equals("01")) {
            account="15801151282";
            sccid="A8F6A67664C64B4A94F8C3AC3017A7B6";
        }else if (signSite .equals("02")) {
            account="13641106263";
            sccid="TEshopCusCom202011066V6GKD9SWM0000090221";
        }else if (signSite .equals("03")) {
            account="17610651655";
            sccid="TEshopCusCom20170803P46FMBT7FS0000000159";
        }
        if (sccid != null) {
            Map<String, Object> map = this.bonusPointsService.saveSign(sccid, account, "company201009046vxdyzy4wg0000000025", 0,
                    null, null, "pos");
            String signId= map.get("signId").toString();
            if (signId != null && !signId.equals("")) {
                map.put("signlist",signList("company201009046vxdyzy4wg0000000025",sccid));
            }
            JSONObject jsonObj = JSONObject.fromObject(map);
            this.result = jsonObj;
        }
        return "success";
    }

    private List signList(String companyId,String sccid){
        //查询次数
        String hqlsign = "from Sign where companyId = ? and sccid = ? and signDate  between ? and ?";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format(new Date());
        Date start = Utilities.getDateFromString(time + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        Date end = Utilities.getDateFromString(time + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
        List<BaseBean> signlist = baseBeanService.getListBeanByHqlAndParams(hqlsign, new Object[]{companyId,sccid,start,end});
        return signlist;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
