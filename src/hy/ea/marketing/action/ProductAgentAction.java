package hy.ea.marketing.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.service.EarthIndexService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.ea.marketing.bo.InvestApply;
import hy.ea.marketing.service.ProductAgentService;
import hy.plat.bo.PageForm;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("ProductAgentAction")
@Scope("prototype")
public class ProductAgentAction 
{
	private static final Logger logger = LoggerFactory.getLogger(ProductAgentAction.class);
    @Resource
    private ProductAgentService productAgentService;
    private String result;
    private PageForm pageForm;

    //公司id
    private String companyId;

    //判断标识
    private String flag;

    //佣金关系
    private ProSetupSub proSetupSub;

    //申请记录
    private InvestApply investApply;

    //产品id
    private String ppId;

    //招商要求
    private String html;

    //佣金关系id
    private String suid;

    //搜索
    private String search;
    @Resource
    private EarthIndexService earthIndexService;
    /**
     * 招商服务主页
     */
    public String productAgentList()
    {
        List<Object> list = productAgentService.getNavigation();
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("list",list);

        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        if(tc!=null) {

            earthIndexService.addBrowseHistory(tc.getSccId(),"招商","");
        }

        return "productAgentList";
    }

    /**
     * 招商服务主页ajax分页
     */
    public  String ajaxProAgentList()
    {

        pageForm = productAgentService.getAgentProductsPageForm(
                    pageForm != null ? pageForm.getPageNumber() : 1,
                    pageForm != null ? pageForm.getPageSize() : 10,
                    flag,search);
        JSONObject json = new JSONObject();
        json.accumulate("pageForm",pageForm == null ? new PageForm() : pageForm);
        result = json.toString();
        return "success";
    }

    /**
     * 招商详情
     */
    public  String proAgentDetail()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        if (ppId.length() > 0)
        {
            Map<String,Object> map = productAgentService.proAgentDetail(ppId);
            request.setAttribute("map",map);
        }
        return "proAgentDetail";
    }

    /**
     * 招商规则
     */
    public String investmentRules()
    {
        return "investmentRules";
    }

    /**
     * 招商要求
     */
    public String investmentDemand()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        String pid=request.getParameter("pid");
        request.setAttribute("de",productAgentService.demandsDetail(pid));
        return "investmentDemand";
    }
    /**
     * 公司招商服务
     */
    public String companyAgent()
    {
        return "companyAgent";
    }

    /**
     * ajax公司招商服务
     */
    public String ajaxCompanyAgent()
    {
        Map<String,Object> map = new HashMap<String,Object>();
        map = productAgentService.getAgentProPageFormByCom(
                pageForm != null ? pageForm.getPageNumber() : 1,
                pageForm != null ? pageForm.getPageSize() : 10,
                companyId,flag );
        JSONObject json = JSONObject.fromObject(map);
        result = json.toString();
        return "success";
    }

    /**
     * 公司招商列表
     */
    public String investmentPro()
    {
        return "investmentPro";
    }

    /**
     * ajax 公司招商列表
     */
    public String ajaxInvestmentPro()
    {
        Map<String,Object> map = productAgentService.getAgentProPageFormByCom(
                pageForm != null ? pageForm.getPageNumber() : 1,
                pageForm != null ? pageForm.getPageSize() : 10,
                companyId,
                flag
        );
        JSONObject json = new JSONObject();
        json.accumulate("pageForm",map.get("pageForm"));
        result = json.toString();
        return "success";
    }

    /**
     * 招商产品待发布
     */
    public  String investmentProducts(){
        return "investmentProducts";
    }

    /**
     * 选择产品发布
     */
    public String checkedProducts()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("list",productAgentService.proInfo(ppId));
        String pid=request.getParameter("pid");
        if (pid!=null && pid.length() > 0)
        {
            request.setAttribute("de",productAgentService.demandsDetail(pid));
        }
        return "investRelease";
    }

    /**
     * 查看招商
     */
    public String investmentInfo()
    {
        if (ppId!=null && ppId.length() > 0)
        {
            HttpServletRequest request = ServletActionContext.getRequest();
            request.setAttribute("map",productAgentService.getAgentDetail(ppId));
        }
        return "investmentInfo";
    }

    /**
     * 申请详情
     */
    public String applyDetail()
    {
        if (ppId!=null && ppId.length() > 0
               && investApply !=null && investApply.getInapId().length() > 0 )
        {
            HttpServletRequest request = ServletActionContext.getRequest();
            request.setAttribute("map",productAgentService.applyDetail(ppId,investApply.getInapId()));
        }
        return "applyDetail";
    }

    /**
     * 立即抢购
     */
    public String snapUp()
    {
        if(proSetupSub!=null && proSetupSub.getSusid().length() > 0)
        {
            String s = productAgentService.snapUp(proSetupSub.getSusid());
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("s",s);
            result = jsonObject.toString();
        }
        return "success";
    }

    /**
     * 查找区域是否审核通过
     * @return
     */
    public String getSnapCount(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String areappid=request.getParameter("areappid");
        if (ppId !=null && ppId.length() > 0 && areappid !=null && areappid.length() > 0)
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("count",productAgentService.getSnapCount(ppId,areappid));
            result = jsonObject.toString();
        }
        return "success";
    }

    /**
     * 保存招商发布
     * @return
     */
    public String saveProAgent()
    {
        logger.info("值：{}", ppId);
        if (ppId !=null && ppId.length() > 0)
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("flag",productAgentService.saveProAgent(ppId,html));
            result = jsonObject.toString();
        }
        return "success";
    }

    /**
     * 结束招商
     * @return
     */
    public String endInvest()
    {
     if (suid !=null && suid.length() > 0)
     {
         productAgentService.endInvest(suid,flag);
         result = "1";
     }
        return "success";
    }

    /**
     * 同意申请
     * @return
     */
    public String agreeApply(){
        productAgentService.agreeToInvest(investApply.getInapId());
        return investmentInfo();
    }

    //http://localhost:8080//ea/productAgent/ea_zsdl.jspa?comid=company20171204E5BXEPW7AR0000000022&sccid=A8F6A67664C64B4A94F8C3AC3017A7B6&ppid=p20170220ZVZR76B88M0000000018
    public String zsdl(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String comid=request.getParameter("comid");
        String sccid=request.getParameter("sccid");
        String ppid=request.getParameter("ppid");
        productAgentService.zsdl(comid,sccid,ppid);
        return "success";
    }


    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public String getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getFlag()
    {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public PageForm getPageForm()
    {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm)
    {
        this.pageForm = pageForm;
    }

    public ProSetupSub getProSetupSub()
    {
        return proSetupSub;
    }

    public void setProSetupSub(ProSetupSub proSetupSub)
    {
        this.proSetupSub = proSetupSub;
    }

    public String getPpId()
    {
        return ppId;
    }

    public void setPpId(String ppId)
    {
        this.ppId = ppId;
    }

    public String getHtml()
    {
        return html;
    }

    public void setHtml(String html)
    {
        this.html = html;
    }

    public String getSuid()
    {
        return suid;
    }

    public void setSuid(String suid)
    {
        this.suid = suid;
    }

    public InvestApply getInvestApply()
    {
        return investApply;
    }

    public void setInvestApply(InvestApply investApply)
    {
        this.investApply = investApply;
    }

    public String getSearch()
    {
        return search;
    }

    public void setSearch(String search)
    {
        this.search = search;
    }
}
