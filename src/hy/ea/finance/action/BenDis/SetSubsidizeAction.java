package hy.ea.finance.action.BenDis;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.finance.BenDis.SetSubsidize;
import hy.ea.finance.service.SetSubsidizeService;
import hy.plat.bo.PageForm;
import hy.plat.common.util.GUID;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018-01-06.
 */
public class SetSubsidizeAction {
    @Resource
    private SetSubsidizeService ssService;
    @Resource
    private ServerService serverService;
    public SetSubsidize setSubsidize;
    public int pageNumber;
    public PageForm pageForm;
    private String result;

    /**
     * 保存
     * @return
     */
    public String saveSub(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        if(account == null){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }

        setSubsidize.setAdddate(new Date());
        setSubsidize.setSsid(serverService.getServerID("setSubsidize"));
        setSubsidize.setStaffid(account.getStaffID());
        setSubsidize.setCompanyid(account.getCompanyID());
        setSubsidize.setStutas("01");
        JSONObject oj = ssService.saveSetSubsidize(setSubsidize);
        result = oj.toString();
        return "success";
    }

    /**
     * 修改
     * @return
     */
    public String UpdateSub(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        if(account == null){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        JSONObject oj=null;
        if(setSubsidize.getSskey()!=null&&!setSubsidize.getSskey().equals("")){
            SetSubsidize ssubsidize=ssService.getSubsidize(setSubsidize.getSskey());
            ssubsidize.setSlPct(setSubsidize.getSlPct());
            ssubsidize.setTotalPct(setSubsidize.getTotalPct());
            ssubsidize.setGtid(setSubsidize.getGtid());
            ssubsidize.setFlPct(setSubsidize.getFlPct());
            ssubsidize.setXfPct(setSubsidize.getXfPct());
            ssubsidize.setXbPct(setSubsidize.getXbPct());
            ssubsidize.setFsPct(setSubsidize.getFsPct());
            //ssubsidize.setFbPct(setSubsidize.getFbPct());
            if(!ssubsidize.getStid().equals(setSubsidize.getStid())){
                ssubsidize.setStid(setSubsidize.getStid());
                ssService.handleHistory(ssubsidize);
            }
            oj = ssService.saveSetSubsidize(ssubsidize);
        }else{
            setSubsidize.setSskey(null);
            setSubsidize.setAdddate(new Date());
            setSubsidize.setSsid(serverService.getServerID("setSubsidize"));
            setSubsidize.setStaffid(account.getStaffID());
            setSubsidize.setCompanyid(account.getCompanyID());
            setSubsidize.setStutas("01");
            setSubsidize.setCodeNum("0"+(ssService.subsidizeByCount()+1));
            oj = ssService.saveSetSubsidize(setSubsidize);
        }

        result = oj.toString();
        return "success";
    }

    /**
     * 列表查询
     * @return
     */
    public String listSub(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        if(account == null){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        pageForm=ssService.ListSetSubsidize(pageForm,pageNumber);
        return "list";
    }

    /**
     * 查询消费补助类别
     * @return
     */
    public String ajaxTypeSubsidize(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        if(account == null){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        JSONObject oj = ssService.ListTypeSubsidize();
        result = oj.toString();
        return "success";
    }

    /**
     * 查询行业类别
     * @return
     */
    public String ajaxScode(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        if(account == null){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        HttpServletRequest request= ServletActionContext.getRequest();
        String codePid=request.getParameter("codePid");
        JSONObject oj = ssService.getCCodeListByPID(codePid);
        result = oj.toString();
        return "success";
    }

    /**
     * 删除
     * @return
     */
    public String delSetSubsidize(){
        HttpServletRequest request= ServletActionContext.getRequest();
        String ssid=request.getParameter("ssid");
        JSONObject oj = ssService.delSetSubsidize(ssid);
        result = oj.toString();
        return "success";
    }

    /**
     * 查询消费补助行业是否添加过
     * @return
     */
    public String getsstype(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        if(account == null){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        HttpServletRequest request= ServletActionContext.getRequest();
        String gtid=request.getParameter("gtid");
        JSONObject oj = ssService.getsstype(gtid);
        result = oj.toString();
        return "success";
    }

    /**
     * 发红包t
     * @return
     */
    public String laskdf(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        if(account == null){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        HttpServletRequest request= ServletActionContext.getRequest();

        String jinbi= request.getParameter("jinbi");
        String Ssid= request.getParameter("ssid");
        String gtid=request.getParameter("gtid");
        String sccid=request.getParameter("sccid");
        String strnum= request.getParameter("strnum");
        String endnum=request.getParameter("endnum");
        ssService.slkdfj(jinbi,sccid,Ssid, gtid,strnum,endnum);
        return "success";
    }

    /**
     * 根据帐号验证交易密码
     * @return
     */
    public String ajaxsdf(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        if(account == null){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        HttpServletRequest request= ServletActionContext.getRequest();
        String zh= request.getParameter("zh");
        String mm= request.getParameter("mm");
        JSONObject oj =ssService.getmm(zh,mm);
        result = oj.toString();
        return "success";
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public SetSubsidize getSetSubsidize() {
        return setSubsidize;
    }

    public void setSetSubsidize(SetSubsidize setSubsidize) {
        this.setSubsidize = setSubsidize;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
