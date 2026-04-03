package hy.ea.finance.action.percentage;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.finance.percentage.BPerHistory;
import hy.ea.bo.finance.percentage.BPercentage;
import hy.ea.bo.finance.percentage.PPerHistory;
import hy.ea.bo.finance.percentage.PPercentage;
import hy.ea.finance.service.percentage.PercentageService;
import hy.ea.service.CCodeService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.elkc.DateUtilElkc;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018-11-7.
 */
@Controller
@Scope("prototype")
public class PercentageAction {
    @Resource
    PercentageService percentageService;
    @Resource
    ServerService serverService;
    @Resource
    BaseBeanService baseBeanService;
    @Resource
    CCodeService codeService;
    @Resource
    private ShowExcelService excelService;
    private PageForm pageForm;
    //ajax响应结果
    private String result;
    private PPercentage ppercentage;
    private BPercentage bpercentage;
    private InputStream excelStream;
    private int pageNumber;//每页显示最大数[pageSize]
    private String codeID;
    //// 添加日志实例对象
    private static final Logger logger = LoggerFactory.getLogger(PercentageAction.class);

    //获取后台用户登录信息
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();
    CAccount cac = (CAccount) session.getAttribute("account");

    //////价格百分比模块//////

    /**
     * 查询各种价格百分比
     * 2018-11-8
     *
     * @return
     */
    public String selectPPercentage() {
        //非空校验
        if (cac == null) {
            //未登录
            return "failed";
        } else {
            //当前页
            int pageNumber1 = pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1;
            //每页显示最大数[默认显示10条]
            int pageSize = (pageNumber == 0 ? 10 : pageNumber);
            pageForm = this.percentageService.selectPPercentage(pageNumber1, pageSize, cac.getCompanyID());
            return "ppercentage_list";
        }
    }

    /**
     * ajax查询获取各种价格百分比
     * 2018-11-8
     *
     * @return
     */
    public String ajaxSelectPPercentage() {
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac == null) {
            //未登录
            map.put("code", "400");
        } else {
            //当前页
            int pageNumber = pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1;
            //每页显示最大数[默认显示10条]
            int pageSize = 10;
            pageForm = this.percentageService.selectPPercentage(pageNumber, pageSize, cac.getCompanyID());
            map.put("pageForm", pageForm);
            map.put("code", "200");//已登录
        }
        JSONObject json = JSONObject.fromObject(map);
        this.result = json.toString();
        return "success";
    }

    /**
     * 去价格百分比添加页面
     * 2018-11-8
     *
     * @return
     */
    public String toPPercentageAdd() {
        //非空校验
        if (cac == null) {
            //未登录
            return "failed";
        } else {
            SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String myDate = e.format(new Date());
            request.setAttribute("cac", cac);
            request.setAttribute("myDate", myDate);
            return "ppercentage_add";
        }
    }

    /**
     * 添加价格及佣金百分比
     * 2019-07-16
     *
     * @return
     */
    public String PPercentageAdd() throws ParseException {
        Map<String, Object> map = new HashMap<String, Object>();
        PPercentage pPercentage = this.percentageService.getPPercentage(ppercentage);
        if (pPercentage != null) {//该公司之前添加过百分比
            map.put("code", "401");
        } else {//该公司之前没有添加过百分比--可以添加
            //生成(获取)佣金百分比id
            String brokerageId = this.serverService.getServerID("brokerageid");
            bpercentage.setBrokerageId(brokerageId);
            //调用日期工具类生成百分比时间
            Date myDate = DateUtilElkc.getCurrentDateTime();
            bpercentage.setTimes(myDate);
            this.percentageService.BPercentageAdd(bpercentage);

            //生成(获取)价格百分比id
            String percentageId = this.serverService.getServerID("percentageid");
            ppercentage.setPercentageId(percentageId);
            ppercentage.setBrokerageId(brokerageId);
            ppercentage.setTimes(myDate);
            boolean flag = this.percentageService.PPercentageAdd(ppercentage);
            if (flag = true) {
                map.put("code", "200");//添加成功
            } else {
                map.put("code", "400");//添加失败
            }
        }
        JSONObject json = JSONObject.fromObject(map);
        this.result = json.toString();
        return "success";
    }

    /**
     * 去价格百分比修改页面
     * 2018-11-10
     *
     * @return
     */
    public String toPPercentageUpdate() {
        String percentageId = ppercentage.getPercentageId();
        request.setAttribute("percentageId", percentageId);
        return "ppercentage_update";

    }

    /**
     * 价格、佣金百分比修改回显[byId]
     * 2018-11-10
     *
     * @return
     */
    public String getPPercentagebyId() {
        String percentageId = ppercentage.getPercentageId();
        PPercentage pPercentage = this.percentageService.getPPercentagebyId(percentageId);
        BPercentage bPercentage = this.percentageService.getBPercentagebyId(pPercentage.getBrokerageId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pPercentage", pPercentage);
        map.put("bPercentage", bPercentage);
        JSONObject json = JSONObject.fromObject(map);
        this.result = json.toString();
        return "success";
    }

    /**
     * 修改价格、佣金百分比
     * 2018-11-9
     *
     * @return
     */
    public String PPercentageUpdate() {
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac == null) {
            //未登录
            map.put("code", "400");//未登录不能修改
        } else {
            PPercentage pPercentage = this.percentageService.getPPercentagebyId(ppercentage.getPercentageId());
            //生成(获取)价格百分比id
            String percentageid = this.serverService.getServerID("percentageid");
            PPerHistory pPerHistory = new PPerHistory();
            pPerHistory.setPercentageId(percentageid);
            pPerHistory.setRetail(pPercentage.getRetail());
            pPerHistory.setActivity(pPercentage.getActivity());
            pPerHistory.setVip(pPercentage.getVip());
            pPerHistory.setWholesale(pPercentage.getWholesale());
            pPerHistory.setSpecial(pPercentage.getSpecial());
            pPerHistory.setTimes(pPercentage.getTimes());
            pPerHistory.setPrincipal(pPercentage.getPrincipal());
            pPerHistory.setCompanyId(pPercentage.getCompanyId());
            pPerHistory.setCodeId(pPercentage.getCodeId());
            pPerHistory.setPpId(pPercentage.getPpId());
            pPerHistory.setBrokerageId(pPercentage.getBrokerageId());
            this.baseBeanService.save(pPerHistory);//保存价格百分比到历史记录表
            //调用日期工具类生成修改价格百分比时间
            Date myDate = DateUtilElkc.getCurrentDateTime();
            pPercentage.setRetail(ppercentage.getRetail());
            pPercentage.setActivity(ppercentage.getActivity());
            pPercentage.setVip(ppercentage.getVip());
            pPercentage.setWholesale(ppercentage.getWholesale());
            pPercentage.setSpecial(ppercentage.getSpecial());
            pPercentage.setPrincipal(cac.getStaffName());
            pPercentage.setTimes(myDate);
            this.percentageService.PPercentageuUpdate(pPercentage);//修改价格百分比

            BPercentage bPercentage = this.percentageService.getBPercentagebyId(pPercentage.getBrokerageId());
            //生成(获取)佣金百分比id
            String brokerageid = this.serverService.getServerID("brokerageid");
            BPerHistory bPerHistory = new BPerHistory();
            bPerHistory.setBrokerageId(brokerageid);
            bPerHistory.setCjdl(bPercentage.getCjdl());
            bPerHistory.setXjdl(bPercentage.getXjdl());
            bPerHistory.setSjdl(bPercentage.getSjdl());
            bPerHistory.setKhjf(bPercentage.getKhjf());
            bPerHistory.setSbaz(bPercentage.getSbaz());
            bPerHistory.setSbtz(bPercentage.getSbtz());
            bPerHistory.setTp(bPercentage.getTp());
            bPerHistory.setYwyj(bPercentage.getYwyj());
            bPerHistory.setPrincipal(bPercentage.getPrincipal());
            bPerHistory.setTimes(bPercentage.getTimes());
            bPerHistory.setCompanyId(bPercentage.getCompanyId());
            this.baseBeanService.save(bPerHistory);//保存佣金百分比到历史记录表
            bPercentage.setCjdl(bpercentage.getCjdl());
            bPercentage.setXjdl(bpercentage.getXjdl());
            bPercentage.setSjdl(bpercentage.getSjdl());
            bPercentage.setTp(bpercentage.getTp());
            bPercentage.setKhjf(bpercentage.getKhjf());
            bPercentage.setSbtz(bpercentage.getSbtz());
            bPercentage.setSbaz(bpercentage.getSbaz());
            bPercentage.setYwyj(bpercentage.getYwyj());
            bPercentage.setPrincipal(cac.getStaffName());
            bPercentage.setTimes(myDate);
            boolean flag = this.percentageService.BPercentageuUpdate(bPercentage);//修改佣金百分比
            if (flag = true) {
                map.put("code", "200");//修改成功
            } else {
                map.put("code", "401");//修改失败
            }

        }
        JSONObject json = JSONObject.fromObject(map);
        this.result = json.toString();
        return "success";
    }

    /**
     * 价格百分比Excel导出
     * 2018-11-12
     *
     * @return
     */
    public String PPercentageShowExcel() {
        String sql = "select d.companyname,p.retail||'%',p.activity||'%',p.vip||'%',p.wholesale||'%',p.special||'%',p.principal,p.times " +
                "from p_percentage p,dtCompany d where p.companyid=d.companyid";
        List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql, null);

        excelStream = excelService.showExcel(new String[]{"序号", "公司名称", "零售价百分比", "活动价百分比", "vip价百分比", "批发价百分比", "特价百分比", "责任人", "设置时间"}, list);
        return "showexcel";
    }

    //////佣金百分比模块/////

    /**
     * 查询各种佣金百分比
     * 2018-11-13
     *
     * @return
     */
    public String selectBPercentage() {
        //非空校验
        if (cac == null) {
            //未登录
            return "failed";
        } else {
            //当前页
            int pageNumber1 = pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1;
            //每页显示最大数[默认显示10条]
            int pageSize = (pageNumber == 0 ? 10 : pageNumber);
            pageForm = this.percentageService.selectBPercentage(pageNumber1, pageSize, cac.getCompanyID());
            return "bpercentage_list";
        }
    }

    /**
     * ajax查询获取各种佣金百分比
     * 2018-11-13
     *
     * @return
     */
    public String ajaxSelectBPercentage() {
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac == null) {
            //未登录
            map.put("code", "400");
        } else {
            //当前页
            int pageNumber = pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1;
            //每页显示最大数[默认显示10条]
            int pageSize = 10;
            pageForm = this.percentageService.selectBPercentage(pageNumber, pageSize, cac.getCompanyID());
            map.put("pageForm", pageForm);
            map.put("code", "200");//已登录
        }
        JSONObject json = JSONObject.fromObject(map);
        this.result = json.toString();
        return "success";
    }

    /**
     * 去佣金百分比添加页面
     * 2018-11-13
     *
     * @return
     */
    public String toBPercentageAdd() {
        //非空校验
        if (cac == null) {
            //未登录
            return "failed";
        } else {
            SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String myDate = e.format(new Date());
            request.setAttribute("cac", cac);
            request.setAttribute("myDate", myDate);
            return "bpercentage_add";
        }
    }

    /**
     * 添加佣金百分比
     * 2018-11-13
     *
     * @return
     */
    public String BPercentageAdd() throws ParseException {
        //生成(获取)佣金百分比id
        String brokerageId = this.serverService.getServerID("brokerageid");
        bpercentage.setBrokerageId(brokerageId);
        //调用日期工具类生成添加佣金百分比时间
        Date myDate = DateUtilElkc.getCurrentDateTime();
        bpercentage.setTimes(myDate);
        boolean flag = this.percentageService.BPercentageAdd(bpercentage);
        Map<String, Object> map = new HashMap<String, Object>();
        if (flag = true) {
            map.put("code", "200");//添加成功
        } else {
            map.put("code", "400");//添加失败
        }
        JSONObject json = JSONObject.fromObject(map);
        this.result = json.toString();
        return "success";
    }

    /**
     * 去佣金百分比修改页面
     * 2018-11-13
     *
     * @return
     */
    public String toBPercentageUpdate() {
//        HttpServletRequest request = ServletActionContext.getRequest();
        String brokerageId = bpercentage.getBrokerageId();
        request.setAttribute("brokerageId", brokerageId);
        return "bpercentage_update";

    }

    /*
     * 佣金百分比修改回显[byId]
     * 2018-11-13
     *!!!!!!!!!
     * @return
     * */
    public String getBPercentagebyId() {
        String brokerageId = bpercentage.getBrokerageId();
        BPercentage bPercentage = this.percentageService.getBPercentagebyId(brokerageId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bPercentage", bPercentage);
        JSONObject json = JSONObject.fromObject(map);
        this.result = json.toString();
        return "success";
    }

    /**
     * 修改佣金百分比
     * 2018-11-13
     *
     * @return
     */
    public String BPercentageUpdate() {
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac == null) {
            //未登录
            map.put("code", "400");//未登录不能修改
        } else {
            String brokerageId = bpercentage.getBrokerageId();
            BPercentage bPercentage = this.percentageService.getBPercentagebyId(brokerageId);
            //参数校验
            if (bPercentage != null) {
                if (!bPercentage.getCjdl().equals(bpercentage.getCjdl()) ||
                        !bPercentage.getXjdl().equals(bpercentage.getXjdl()) ||
                        !bPercentage.getSjdl().equals(bpercentage.getSjdl()) ||
                        !bPercentage.getSbaz().equals(bpercentage.getSbaz()) ||
                        !bPercentage.getSbtz().equals(bpercentage.getSbtz()) ||
                        !bPercentage.getKhjf().equals(bpercentage.getKhjf()) ||
                        !bPercentage.getTp().equals(bpercentage.getTp()) ||
                        !bPercentage.getYwyj().equals(bpercentage.getYwyj())
                        ) {
                    //生成(获取)佣金百分比id
                    String brokerageid = this.serverService.getServerID("brokerageid");
                    BPerHistory bPerHistory = new BPerHistory();
                    bPerHistory.setBrokerageId(brokerageid);
                    bPerHistory.setCjdl(bPercentage.getCjdl());
                    bPerHistory.setXjdl(bPercentage.getXjdl());
                    bPerHistory.setSjdl(bPercentage.getSjdl());
                    bPerHistory.setKhjf(bPercentage.getKhjf());
                    bPerHistory.setSbaz(bPercentage.getSbaz());
                    bPerHistory.setSbtz(bPercentage.getSbtz());
                    bPerHistory.setTp(bPercentage.getTp());
                    bPerHistory.setYwyj(bPercentage.getYwyj());
                    bPerHistory.setPrincipal(bPercentage.getPrincipal());
                    bPerHistory.setTimes(bPercentage.getTimes());
                    bPerHistory.setCompanyId(bPercentage.getCompanyId());
                    this.baseBeanService.save(bPerHistory);//保存佣金百分比到历史记录表
                    //调用日期工具类生成修改佣金百分比时间
                    Date myDate = DateUtilElkc.getCurrentDateTime();
                    bPercentage.setCjdl(bpercentage.getCjdl());
                    bPercentage.setXjdl(bpercentage.getXjdl());
                    bPercentage.setSjdl(bpercentage.getSjdl());
                    bPercentage.setTp(bpercentage.getTp());
                    bPercentage.setKhjf(bpercentage.getKhjf());
                    bPercentage.setSbtz(bpercentage.getSbtz());
                    bPercentage.setSbaz(bpercentage.getSbaz());
                    bPercentage.setYwyj(bpercentage.getYwyj());
                    bPercentage.setPrincipal(cac.getStaffName());
                    bPercentage.setTimes(myDate);
                    boolean flag = this.percentageService.BPercentageuUpdate(bPercentage);//修改佣金百分比
                    if (flag = true) {
                        map.put("code", "200");//修改成功
                    } else {
                        map.put("code", "401");//修改失败
                    }
                } else {
                    map.put("code", "402");//没有更改数据不能修改
                }
            } else {
                map.put("code", "401");//无法修改
            }

        }
        JSONObject json = JSONObject.fromObject(map);
        this.result = json.toString();
        return "success";
    }

    /**
     * 佣金百分比Excel导出
     * 2018-11-13
     *
     * @return
     */
    public String BPercentageShowExcel() {
        String sql = "select d.companyname,b.tp||'%',b.sbaz||'%',b.sbtz||'%',b.sjdl||'%',b.xjdl||'%',b.cjdl||'%',b.khjf||'%',b.ywyj||'%',b.principal,b.times " +
                "from b_percentage b ,dtcompany d where b.companyid =d.companyid";
        List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql, null);
        excelStream = excelService.showExcel(new String[]
                {"序号", "公司名称", "贴牌佣金百分比", "设备安装佣金百分比", "设备投资佣金百分比",
                        "省级代理佣金百分比", "县级代理佣金百分比", "村级代理佣金百分比",
                        "客户积分佣金百分比", "业务佣金百分比", "责任人", "设置时间"}, list);
        return "showexcel";
    }

    /**
     * 根据companyID和codeID查询其子节点
     */
    public String getListCCodeByPID() {
//        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        if (cac == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        String codetype = "FL";//分类
        List<CCode> codeList = codeService.getCCodeListNewByPID(cac
                .getCompanyID(), codeID, codetype);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("codeList", codeList);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }


    ////***********set  get*************
    public PercentageService getPercentageService() {
        return percentageService;
    }

    public void setPercentageService(PercentageService percentageService) {
        this.percentageService = percentageService;
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

    public PPercentage getPpercentage() {
        return ppercentage;
    }

    public void setPpercentage(PPercentage ppercentage) {
        this.ppercentage = ppercentage;
    }

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    public BPercentage getBpercentage() {
        return bpercentage;
    }

    public void setBpercentage(BPercentage bpercentage) {
        this.bpercentage = bpercentage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getCodeID() {
        return codeID;
    }

    public void setCodeID(String codeID) {
        this.codeID = codeID;
    }
}
