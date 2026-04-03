package hy.ea.finance.action.brokerage;

import hy.ea.bo.CAccount;
import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.ea.bo.finance.BenDis.ProSetupSubBackup;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.finance.ProSetupBackup;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.finance.service.brokerage.RetailService;
import hy.ea.finance.service.brokerage.WholesaleService;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2019-1-15.
 */
@Controller
@Scope("prototype")
public class RetailAction {
    @Resource
    ServerService serverService;
    @Resource
    BaseBeanService baseBeanService;
    @Resource
    RetailService retailService;
    @Resource
    WholesaleService wholesaleService;
    @Resource
    private ShowExcelService excelService;

    private PageForm pageForm;
    //ajax响应结果
    private String result;
    private String search;//模糊查询参数-商品名称或条码
    private String showweixin; //查询参数-物品状态 ‘’：全部  ‘01’：已发布 否则 未发布
    private String flag;//查看数据标识 flag=01[数据设置为只读]
    private int pageNumber;//每页显示最大数[pageSize]
    private Map<String, String> mapPro;//代理佣金
    private Map<String, String> mapId;//代理类别id //or代理商佣金susid
    private ProSetup proSetup;
    private ProductPackaging productPackaging;
    private InputStream excelStream;
    //// 添加日志实例对象
    private static final Logger logger = LoggerFactory.getLogger(RetailAction.class);

    //获取后台用户登录信息
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();
    CAccount cac = (CAccount) session.getAttribute("account");

    //////商品零售价模块//////

    /**
     * 零售产品[佣金...]查询
     * 2019-1-19
     *
     * @return
     */
    public String selectRetailList() {
        //非空校验
        if (cac == null) {
            //未登录
            return "failed";
        } else {
            //当前页
            int pageNumber1 = pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1;
            //每页显示最大数[默认显示30条]
            int pageSize = (pageNumber == 0 ? 30 : pageNumber);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageNumber", pageNumber1);
            map.put("pageSize", pageSize);
            map.put("search", search);
            map.put("showweixin", showweixin);
            map.put("companyId", cac.getCompanyID());
            pageForm = this.retailService.selectRetailList(map);
            return "retail_list";
        }
    }

    /**
     * 获取没有设置零售价佣金的产品
     * 2019-1-19
     *
     * @return
     */
    public String getProductByStatus() {
        Map<String, Object> map2 = new HashMap<String, Object>();
        //非空校验
        if (cac != null && cac.getCompanyID() != null && !"".equals(cac.getCompanyID())) {
            //当前页
            int pageNumber1 = pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1;
            //每页显示最大数[默认显示20条]
            int pageSize = (pageNumber == 0 ? 20 : pageNumber);
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("pageNumber", pageNumber1);
            map1.put("pageSize", pageSize);
            map1.put("search", search);
            map1.put("companyId", cac.getCompanyID());
            pageForm = this.retailService.getProductByStatus(map1);
            map2.put("pageForm", pageForm);
            map2.put("search", search);
            map2.put("code", "200");
        } else {
            logger.error("登录异常,重新登录");
            map2.put("code", "400");
        }

        JSONObject jsonObject = JSONObject.fromObject(map2);
        this.result = jsonObject.toString();
        return "success";
    }

    /**
     * 去添加零售商品价格佣金页面
     * 2019-1-19
     *
     * @return
     */
    public String toRetailAdd() {
        //非空校验
        if (cac == null) {
            //未登录
            return "failed";
        } else {
            //获取设计零售商品佣金时间
            SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String myDate = e.format(new Date());
            request.setAttribute("cac", cac);
            request.setAttribute("myDate", myDate);
            //代理类别
            List<BaseBean> agencyList = this.wholesaleService.agencyTypeList();
            request.setAttribute("agencyTypeList", agencyList);
            return "retail_add";
        }
    }

    /**
     * 添加零售商品价格佣金
     * 2019-1-19
     *
     * @return
     */
    public String addRetailBrokerage() {
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac != null && cac.getCompanyID() != null && !"".equals(cac.getCompanyID())) {
            String flag = this.retailService.getPRetailByPpid(proSetup, mapPro);
            if ("200".equals(flag)) {
                //零售价添加
                String retailId = serverService.getServerID("retailId");
                proSetup.setSuid(retailId);
                proSetup.setComId(cac.getCompanyID());
                Date dateTime = DateUtilElkc.getCurrentDateTime();
                proSetup.setSjdate(dateTime);
                proSetup.setPrincipal(cac.getStaffName());
                proSetup.setState("00");//默认给:00
                this.baseBeanService.save(proSetup);
                //将mapId中的对象添加到mapPro中
                mapPro.putAll(mapId);
                //零售价佣金添加
                ProSetupSub proSetupSub = new ProSetupSub();
                proSetupSub.setPpid(proSetup.getPpid());
                proSetupSub.setSuid(retailId);
                String retailbroId = serverService.getServerID("retailbroId");
                proSetupSub.setSusid(retailbroId);
                proSetupSub.setAmount(mapPro.get("sbtz"));
                proSetupSub.setTypePpid(mapPro.get("sbtzId"));
                this.baseBeanService.save(proSetupSub);

                ProSetupSub proSetupSub1 = new ProSetupSub();
                proSetupSub1.setPpid(proSetup.getPpid());
                proSetupSub1.setSuid(retailId);
                String retailbroId1 = serverService.getServerID("retailbroId");
                proSetupSub1.setSusid(retailbroId1);
                proSetupSub1.setAmount(mapPro.get("tp"));
                proSetupSub1.setTypePpid(mapPro.get("tpId"));
                this.baseBeanService.save(proSetupSub1);

                ProSetupSub proSetupSub2 = new ProSetupSub();
                proSetupSub2.setPpid(proSetup.getPpid());
                proSetupSub2.setSuid(retailId);
                String retailbroId2 = serverService.getServerID("retailbroId");
                proSetupSub2.setSusid(retailbroId2);
                proSetupSub2.setAmount(mapPro.get("sbaz"));
                proSetupSub2.setTypePpid(mapPro.get("sbazId"));
                this.baseBeanService.save(proSetupSub2);

                ProSetupSub proSetupSub3 = new ProSetupSub();
                proSetupSub3.setPpid(proSetup.getPpid());
                proSetupSub3.setSuid(retailId);
                String retailbroId3 = serverService.getServerID("retailbroId");
                proSetupSub3.setSusid(retailbroId3);
                proSetupSub3.setAmount(mapPro.get("sjdl"));
                proSetupSub3.setTypePpid(mapPro.get("sjdlId"));
                this.baseBeanService.save(proSetupSub3);

                ProSetupSub proSetupSub4 = new ProSetupSub();
                proSetupSub4.setPpid(proSetup.getPpid());
                proSetupSub4.setSuid(retailId);
                String retailbroId4 = serverService.getServerID("retailbroId");
                proSetupSub4.setSusid(retailbroId4);
                proSetupSub4.setAmount(mapPro.get("xjdl"));
                proSetupSub4.setTypePpid(mapPro.get("xjdlId"));
                this.baseBeanService.save(proSetupSub4);

                ProSetupSub proSetupSub5 = new ProSetupSub();
                proSetupSub5.setPpid(proSetup.getPpid());
                proSetupSub5.setSuid(retailId);
                String retailbroId5 = serverService.getServerID("retailbroId");
                proSetupSub5.setSusid(retailbroId5);
                proSetupSub5.setAmount(mapPro.get("cjdl"));
                proSetupSub5.setTypePpid(mapPro.get("cjdlId"));
                this.baseBeanService.save(proSetupSub5);

                ProSetupSub proSetupSub6 = new ProSetupSub();
                proSetupSub6.setPpid(proSetup.getPpid());
                proSetupSub6.setSuid(retailId);
                String retailbroId6 = serverService.getServerID("retailbroId");
                proSetupSub6.setSusid(retailbroId6);
                proSetupSub6.setAmount(mapPro.get("khjf"));
                proSetupSub6.setTypePpid(mapPro.get("khjfId"));
                this.baseBeanService.save(proSetupSub6);
                //更改产品表佣金状态 yjstatus为01
                this.retailService.updateretailStatus(proSetup.getPpid());
                map.put("code", "200");
            } else if ("401".equals(flag)) {
                map.put("code", "401");//数据异常
            } else if ("201".equals(flag)) {
                map.put("code", "200");
            }

        } else {
            logger.error("登录异常,重新登录");
            map.put("code", "400");//登录异常
        }
        JSONObject jsonObject = JSONObject.fromObject(map);
        this.result = jsonObject.toString();
        return "success";
    }

    /**
     * 去修改零售商品价格佣金页面
     * 2019-1-21
     *
     * @return
     */
    public String toRetailUpdate() {
        //非空校验
        if (cac == null) {
            //未登录
            return "failed";
        } else {
            //获取设计零售商品佣金时间
            SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String myDate = e.format(new Date());
            //Date dateTime = DateUtilElkc.getCurrentDateTime();
            request.setAttribute("cac", cac);
            request.setAttribute("myDate", myDate);
            //代理类别
            List<BaseBean> agencyList = this.wholesaleService.agencyTypeList();
            //零售价
            Object[] pRetails = this.retailService.PRetailById(productPackaging.getPpID());
            //零售价代理佣金
            List<BaseBean> brokerageList = this.retailService.brokerageListById((String) pRetails[0]);
            request.setAttribute("agencyTypeList", agencyList);
            request.setAttribute("pRetails", pRetails);
            request.setAttribute("ppid", productPackaging.getPpID());

            if (brokerageList != null && brokerageList.size() == 7) {
                //零售价代理佣金都存在不需要添加
            } else {//添加不存在的代理佣金
                Object objects = null;
                List<String> list1 = new ArrayList<String>();
                for (int i = 0; i < brokerageList.size(); i++) {
                    objects = brokerageList.get(i);
                    Object[] objs = (Object[]) objects;
                    list1.add(objs[2].toString());
                }
                List<String> list2 = new ArrayList<String>();
                list2.add("p20170605KY3VAANZJG0000000003"); //设备投资
                list2.add("p20170220ZVZR76B88M0000000016"); //贴牌
                list2.add("p20170220ZVZR76B88M0000000017"); //设备安装
                list2.add("p20170220ZVZR76B88M0000000018"); //省级代理
                list2.add("p20170220ZVZR76B88M0000000019"); //县级代理
                list2.add("p20170220ZVZR76B88M0000000020"); //村级代理
                list2.add("p20170220ZVZR76B88M0000000022"); //客户积分
                //将第一个List赋给第三个List
                List<String> list = new ArrayList<>(list1);

                //将第二个List放进第三个List
                for (int i = 0; i < list2.size(); i++) {
                    //如果第三个List已经存在，则不添加，如果不存在就添加
                    if (!list.contains(list2.get(i))) {
                        list.add(list2.get(i));
                    }
                }
                //删除第三个里面包含第一个和第二个List的元素
                for (int i = 0; i < list.size(); i++) {
                    //将第一，第二个List和第三个比较，如果第一第二个都有的，则从第三个删除
                    if (list1.contains(list.get(i)) && list2.contains(list.get(i))) {
                        list.remove(i);
                        i--;//如果删除了一个元素，就继续从这个数组下标开始比较
                    }
                }
                for (int i = 0; i < list.size(); i++) {
                    ProSetupSub proSetupSub = new ProSetupSub();
                    //生成(获取)零售价佣金id
                    String retbroId = this.serverService.getServerID("retbroId");
                    proSetupSub.setSusid(retbroId);
                    proSetupSub.setAmount("0");
                    proSetupSub.setSuid(pRetails[0].toString());
                    proSetupSub.setPpid(productPackaging.getPpID());
                    proSetupSub.setTypePpid(list.get(i));
                    proSetupSub.setState("00");
                    this.baseBeanService.save(proSetupSub);
                }
                //查询添加后的零售价代理佣金
                brokerageList = this.retailService.brokerageListById((String) pRetails[0]);
            }
            request.setAttribute("brokerageList", brokerageList);
            request.setAttribute("product", productPackaging);
            request.setAttribute("flag", flag);
            return "retail_update";
        }
    }

    /**
     * 修改零售商品价格佣金
     * 2019-1-21
     *
     * @return
     */
    public String updateRetailBrokerage() {
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac != null) {
            //获取零售价修改回显数据
            ProSetup proSetups = this.retailService.getPRetailById(proSetup.getSuid());

            ProSetupBackup proSetupBackup = new ProSetupBackup();
            proSetupBackup.setSubId(proSetup.getSuid());
            proSetupBackup.setRePrice(proSetups.getRePrice());
            proSetupBackup.setEfPrice(proSetups.getEfPrice());
            proSetupBackup.setBrokerage(proSetups.getBrokerage());
            proSetupBackup.setProxySumPrice(proSetups.getProxyprice());
            proSetupBackup.setTzType(proSetups.getTzType());
            proSetupBackup.setSjDate(proSetups.getSjdate());
            proSetupBackup.setEditDate(proSetups.getEditDate());
            proSetupBackup.setPrincipal(proSetups.getPrincipal());
            proSetupBackup.setPpid(proSetups.getPpid());
            proSetupBackup.setComId(proSetups.getComId());
            proSetupBackup.setState(proSetups.getState());
            this.baseBeanService.save(proSetupBackup);//保存零售价到历史记录表
            //生成(获取)零售价id
            String retailId = serverService.getServerID("retailId");
            proSetups.setSuid(retailId);
            proSetups.setRePrice(proSetup.getRePrice());
            proSetups.setEfPrice(proSetup.getEfPrice());
            proSetups.setBrokerage(proSetup.getBrokerage());
            proSetups.setProxyprice(proSetup.getProxyprice());
            proSetups.setTzType(proSetup.getTzType());
            proSetups.setPrincipal(proSetup.getPrincipal());
            //生成当前修改时间
            Date dateTime = DateUtilElkc.getCurrentDateTime();
            proSetups.setEditDate(dateTime);
            this.baseBeanService.update(proSetups); //零售价修改
            //将mapId中的对象添加到mapPro中
            mapPro.putAll(mapId);

            //零售价佣金修改
            String sbtzId = mapPro.get("sbtzId");
            ProSetupSub ProSetupSub1 = this.retailService.getPRetBrokerageById(sbtzId);
            if (ProSetupSub1 != null) {
                ProSetupSubBackup proSetupSubBackup1 = new ProSetupSubBackup();
                //生成(获取)零售价佣金id
                String retbroId1 = this.serverService.getServerID("retbroId");
                proSetupSubBackup1.setSusbId(retbroId1);
                proSetupSubBackup1.setAmount(ProSetupSub1.getAmount());
                proSetupSubBackup1.setPpid(ProSetupSub1.getPpid());
                proSetupSubBackup1.setTypePpid(ProSetupSub1.getTypePpid());
                proSetupSubBackup1.setSubId(ProSetupSub1.getSuid());
                this.baseBeanService.save(proSetupSubBackup1);//保存零售价佣金到历史记录表
                ProSetupSub1.setAmount(mapPro.get("sbtz"));
                ProSetupSub1.setSuid(retailId);
                this.baseBeanService.update(ProSetupSub1);//修改零售价佣金
            }

            String tpId = mapPro.get("tpId");
            ProSetupSub ProSetupSub2 = this.retailService.getPRetBrokerageById(tpId);
            if (ProSetupSub2 != null) {
                ProSetupSubBackup proSetupSubBackup2 = new ProSetupSubBackup();
                //生成(获取)零售价佣金id
                String retbroId2 = this.serverService.getServerID("retbroId");
                proSetupSubBackup2.setSusbId(retbroId2);
                proSetupSubBackup2.setAmount(ProSetupSub2.getAmount());
                proSetupSubBackup2.setPpid(ProSetupSub2.getPpid());
                proSetupSubBackup2.setTypePpid(ProSetupSub2.getTypePpid());
                proSetupSubBackup2.setSubId(ProSetupSub2.getSuid());
                this.baseBeanService.save(proSetupSubBackup2);//保存零售价佣金到历史记录表
                ProSetupSub2.setAmount(mapPro.get("tp"));
                ProSetupSub2.setSuid(retailId);
                this.baseBeanService.update(ProSetupSub2);//修改零售价佣金
            }

            String sbazId = mapPro.get("sbazId");
            ProSetupSub ProSetupSub3 = this.retailService.getPRetBrokerageById(sbazId);
            if (ProSetupSub3 != null) {
                ProSetupSubBackup proSetupSubBackup3 = new ProSetupSubBackup();
                //生成(获取)零售价佣金id
                String retbroId3 = this.serverService.getServerID("retbroId");
                proSetupSubBackup3.setSusbId(retbroId3);
                proSetupSubBackup3.setAmount(ProSetupSub3.getAmount());
                proSetupSubBackup3.setPpid(ProSetupSub3.getPpid());
                proSetupSubBackup3.setTypePpid(ProSetupSub3.getTypePpid());
                proSetupSubBackup3.setSubId(ProSetupSub3.getSuid());
                this.baseBeanService.save(proSetupSubBackup3);//保存零售价佣金到历史记录表
                ProSetupSub3.setAmount(mapPro.get("sbaz"));
                ProSetupSub3.setSuid(retailId);
                this.baseBeanService.update(ProSetupSub3);//修改零售价佣金
            }
            String sjdlId = mapPro.get("sjdlId");
            ProSetupSub ProSetupSub4 = this.retailService.getPRetBrokerageById(sjdlId);
            if (ProSetupSub4 != null) {
                ProSetupSubBackup proSetupSubBackup4 = new ProSetupSubBackup();
                //生成(获取)零售价佣金id
                String retbroId4 = this.serverService.getServerID("retbroId");
                proSetupSubBackup4.setSusbId(retbroId4);
                proSetupSubBackup4.setAmount(ProSetupSub4.getAmount());
                proSetupSubBackup4.setPpid(ProSetupSub4.getPpid());
                proSetupSubBackup4.setTypePpid(ProSetupSub4.getTypePpid());
                proSetupSubBackup4.setSubId(ProSetupSub4.getSuid());
                this.baseBeanService.save(proSetupSubBackup4);//保存零售价佣金到历史记录表
                ProSetupSub4.setAmount(mapPro.get("sjdl"));
                ProSetupSub4.setSuid(retailId);
                this.baseBeanService.update(ProSetupSub4);//修改零售价佣金
            }
            String xjdlId = mapPro.get("xjdlId");
            ProSetupSub ProSetupSub5 = this.retailService.getPRetBrokerageById(xjdlId);
            if (ProSetupSub5 != null) {
                ProSetupSubBackup proSetupSubBackup5 = new ProSetupSubBackup();
                //生成(获取)零售价佣金id
                String retbroId5 = this.serverService.getServerID("retbroId");
                proSetupSubBackup5.setSusbId(retbroId5);
                proSetupSubBackup5.setAmount(ProSetupSub5.getAmount());
                proSetupSubBackup5.setPpid(ProSetupSub5.getPpid());
                proSetupSubBackup5.setTypePpid(ProSetupSub5.getTypePpid());
                proSetupSubBackup5.setSubId(ProSetupSub5.getSuid());
                this.baseBeanService.save(proSetupSubBackup5);//保存零售价佣金到历史记录表
                ProSetupSub5.setAmount(mapPro.get("xjdl"));
                ProSetupSub5.setSuid(retailId);
                this.baseBeanService.update(ProSetupSub5);//修改零售价佣金
            }

            String cjdlId = mapPro.get("cjdlId");
            ProSetupSub ProSetupSub6 = this.retailService.getPRetBrokerageById(cjdlId);
            if (ProSetupSub6 != null) {
                ProSetupSubBackup proSetupSubBackup6 = new ProSetupSubBackup();
                //生成(获取)零售价佣金id
                String retbroId6 = this.serverService.getServerID("retbroId");
                proSetupSubBackup6.setSusbId(retbroId6);
                proSetupSubBackup6.setAmount(ProSetupSub6.getAmount());
                proSetupSubBackup6.setPpid(ProSetupSub6.getPpid());
                proSetupSubBackup6.setTypePpid(ProSetupSub6.getTypePpid());
                proSetupSubBackup6.setSubId(ProSetupSub6.getSuid());
                this.baseBeanService.save(proSetupSubBackup6);//保存零售价佣金到历史记录表
                ProSetupSub6.setAmount(mapPro.get("cjdl"));
                ProSetupSub6.setSuid(retailId);
                this.baseBeanService.update(ProSetupSub6);//修改零售价佣金
            }

            String khjfId = mapPro.get("khjfId");
            ProSetupSub ProSetupSub7 = this.retailService.getPRetBrokerageById(khjfId);
            if (ProSetupSub7 != null) {
                ProSetupSubBackup proSetupSubBackup7 = new ProSetupSubBackup();
                //生成(获取)零售价佣金id
                String retbroId7 = this.serverService.getServerID("retbroId");
                proSetupSubBackup7.setSusbId(retbroId7);
                proSetupSubBackup7.setAmount(ProSetupSub7.getAmount());
                proSetupSubBackup7.setPpid(ProSetupSub7.getPpid());
                proSetupSubBackup7.setTypePpid(ProSetupSub7.getTypePpid());
                proSetupSubBackup7.setSubId(ProSetupSub7.getSuid());
                this.baseBeanService.save(proSetupSubBackup7);//保存零售价佣金到历史记录表
                ProSetupSub7.setAmount(mapPro.get("khjf"));
                ProSetupSub7.setSuid(retailId);
                this.baseBeanService.update(ProSetupSub7);//修改零售价佣金
            }
            map.put("code", "200");
        } else {
            logger.error("登录异常,重新登录");
            map.put("code", "400");//登录异常
        }
       /* baseBeanService.executeSqlsByParmsList();
        baseBeanService.saveBeansListAndexecuteSqlsByParams();*/
        JSONObject jsonObject = JSONObject.fromObject(map);
        this.result = jsonObject.toString();
        return "success";
    }

    /**
     * 产品零售价Excel导出
     * 2019-1-19
     *
     * @return
     */
    public String RetailShowExcel() {
        //非空校验
        if (cac != null) {

            excelStream = retailService.RetailShowExcel(search,showweixin,cac.getCompanyID());
            return "showexcel";

        } else {
            //未登录
            return "failed";
        }

    }

    /**
     * 删除当前零售产品及佣金[更改状态为:01]
     * 更改产品表相应产品零售佣金状态为 00
     * 2019-05-23
     *
     * @return
     */
    public String retailDeleteById() {
        Map<String, Object> map = this.retailService.retailDeleteById(proSetup);
        JSONObject jsonObject = JSONObject.fromObject(map);
        this.result = jsonObject.toString();
        return "success";
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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Map<String, String> getMapPro() {
        return mapPro;
    }

    public void setMapPro(Map<String, String> mapPro) {
        this.mapPro = mapPro;
    }

    public Map<String, String> getMapId() {
        return mapId;
    }

    public void setMapId(Map<String, String> mapId) {
        this.mapId = mapId;
    }

    public ProductPackaging getProductPackaging() {
        return productPackaging;
    }

    public void setProductPackaging(ProductPackaging productPackaging) {
        this.productPackaging = productPackaging;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    public ProSetup getProSetup() {
        return proSetup;
    }

    public void setProSetup(ProSetup proSetup) {
        this.proSetup = proSetup;
    }

    public String getShowweixin() {
        return showweixin;
    }

    public void setShowweixin(String showweixin) {
        this.showweixin = showweixin;
    }
}
