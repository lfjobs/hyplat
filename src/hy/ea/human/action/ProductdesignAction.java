package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.ea.bo.finance.BenDis.SetSubsidize;
import hy.ea.bo.finance.ProMain;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.finance.ProductPriceCategory;
import hy.ea.bo.finance.Profitshare;
import hy.ea.bo.finance.TPrdcodeRel;
import hy.ea.bo.finance.TPrdcodeRelId;
import hy.ea.bo.finance.productPackajiage;
import hy.ea.bo.human.CustomersForms;
import hy.ea.bo.human.Staff;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.DateJsonValueProcessor;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.service.ProductlaunchService;

@Controller
@Scope("prototype")
/**
 * 	产品包装设计
 */
public class ProductdesignAction {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private CLogBookService logBookService;
    @Resource
    private CCodeService codeService;
    @Resource
    private UploadContentToFileService contentToFileService;
    @Resource
    private ProductlaunchService productLaunchService;
    @Resource
    private ShowExcelService excelService;
    private ProductPriceCategory productPriceCategory;
    private String parameter;
    private ProductPackaging productDesign;
    private ProductPackaging productpeijian;
    private PageForm pageForm;
    private PageForm pageFormpeijian;
    private String butt;
    private InputStream excelStream;
    private String result;
    private String search;
    private List<BaseBean> staffList;
    private int pageNumber;
    private List<BaseBean> beans;
    private List<BaseBean> productPackagingList;
    private List<BaseBean> productPriceCategoryList;
    private String sdate;
    private String edate;
    private File image;
    private String imageFileName;
    private productPackajiage productPackajiage;
    private BaseBean baseBean;
    private productPackajiage beanjage;
    private String typeString;
    private String gn; //goodsname简写  产品名称--用于模糊查询
    private String showweixin;
    private String no;
    private String activityid;
    private String todayPrice;
    /**
     * 物品类别
     */
    private List<CCode> codeList;
    private List<CCode> priceManageList;// 价格类别
    private String printDate; // 打印时间
    private List<BaseBean> printList;// 打印列表
    private Object obj;// 打印公司 部门
    private String printname;// 责任人
    private GoodsManage goodsManage;

    public String title;
    private String showType;
    private String priceType;
    private CustomersForms customersForms;
    private String produce;
    private String flexbutton;// 区分产品设计和发布
    /**
     * 产品在线编辑内容
     */
    private String htmlContent;
    private String actionName;
    private Profitshare profitshare;
    /**
     * 产品分类
     */
    private String[] prdCategory;

    private String billID;
    private Staff staff;
    private Map<String, ProductPackaging> productPackagingMap;
    private Map<String, ProductPriceCategory> productPriceCategoryMap;

    private CCode ccode;
    private List<CCode> proType;
    private String fiveClear;

    private Map<String, ProSetup> str;
    private String devide;
    private String xmtype;
    private String flag;
    private String chaoshi;

    private String[] ppIDList;

    /**
     * 打印预览
     *
     * @return
     */
    @SuppressWarnings({"unchecked", "deprecation"})
    public String toProPrint() {

        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return "not_login";
        }
        List<Object> list = (List<Object>) session.get("sqllist");
        String sql = (String) list.get(0);

        Object[] parms = (Object[]) list.get(1);
        printList = new ArrayList<BaseBean>();
        printList = baseBeanService.getListBeanBySqlAndParams(sql, parms);
        obj = new Object();
        obj = printList.get(0);
        printname = account.getAccountName();
        printDate = new Date().toLocaleString().substring(0, 10);
        return "toProPrint";
    }

    public String toSearch() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("tablesearch", productDesign);
        return getListProductdesign();
    }

    private List<Object> getList() {

        Map<String, Object> session = ActionContext.getContext().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        typeString = request.getParameter("pptype");
        no = request.getParameter("no");
        if (no != null && ("0".equals(no) || "01".equals(no) || "1".equals(no))) {
            request.setAttribute("butt", "check");
        } else {
            request.removeAttribute("butt");
        }

        List<Object> result = new ArrayList<Object>();
        CAccount account = (CAccount) session.get("account");
        List<Object> parms = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct(pp.ppid),cp.companyname,co.organizationname,hs.staffname, pp.packagingdate,pp.productcode,pp.goodsname,");
        sql.append(" gm.variableID,gm.acquiescestandard,pp.quantity,pp.weight,pp.manualurl,pp.propagandaurl,pp.fileurl,gm.goodsid,gm.typeid,");
        sql.append(" to_char(nvl(ps.re_price*(1+sb.total_pct*0.01),ps.re_price),'fm999999990.00') as price,to_char(ps.ef_price,'fm999999990.00'),pp.showweixin ,pp.shangjiastatus,pp.pptype,pp.yjstatus,pp.barcode,");
        // 在goodsid 之后, p.category, p.price,p.money,
        sql.append("to_char(pw.wholesale*(1+nvl(sb.total_pct,0)/100),'fm999999990.00') wholesale,to_char(vip.vip*(1+nvl(sb.total_pct,0)/100),'fm999999990.00') vipprice,");
        sql.append("case when sysdate between pa.starttime and pa.endtime then to_char(ap.actPrice*(1+nvl(sb.total_pct,0)/100),'fm999999990.00') else '' end  as actprice");
        sql.append(" from dt_productpackaging pp");
        sql.append(" left join dtcompany cp on cp.companyid=pp.companyid");
        sql.append(" left join dtcorganization co on co.organizationid=pp.organizationid");
        sql.append(" left join dt_hr_staff hs on hs.staffid=pp.staffid");
        sql.append(" left join dtgoodsmanage gm on gm.goodsid=pp.goodsid ");
        // +" left join dt_productPriceCategory p on p.ppid=pp.ppid ";
        sql.append(" left join dt_inv_invt i on i.goodsid = pp.goodsid");
        sql.append(" left join dt_ccom_com cco on cco.compnay_id = pp.companyid");
        sql.append(" left join dtcontactcompany cc on cc.ccompanyid = cco.ccompany_id");
        sql.append(" left join dt_set_subsidize sb on sb.gtid = cc.industrytype and sb.stutas = '01'");
        sql.append(" left join dt_pro_wholesale pw on pp.ppid = pw.ppid");
        sql.append(" left join dt_pro_vip vip on vip.ppid=pp.ppid");
        sql.append(" left join dt_pro_activity_price ap on ap.ppid=pp.ppid");
        sql.append(" left join dt_pro_activity pa on pa.activityId = ap.activityid ");
        //nvl(ps.re_price*(1+sb.total_pct*0.01),ps.re_price) as price

        //用于区分是公司汇总 集团汇总 部门汇总

        if (devide != null && devide.equals("group")) {
            sql.append("where pp.companyid in(select dt.companyid from dtcompany dt where dt.groupCompanySn = ?) ");
            parms.add((String) session.get("groupCompanySn"));

        } else {
            if (chaoshi != null && chaoshi.equals("platform")) {
                sql.append(" left join DT_PRO_SETUP_BACKUP sp on pp.ppid=sp.ppid");//ljc
            }
            sql.append(" left join dt_pro_setup ps on pp.ppid=ps.ppid");//ljc

            sql.append(" where pp.companyid ");
        }
        if (no != null && no.equals("5") && account.getCompanyID().equals("company201009046vxdyzy4wg0000000025")) {
            sql.append("in(select  companyid from dtCompany where groupCompanySn=?)");
            String sql1 = "select groupCompanySn from dtCompany where companyID=?";
            Object oj = baseBeanService.getObjectBySqlAndParams(sql1, new String[]{account.getCompanyID()});

            parms.add(oj.toString());
        } else {
            if (devide == null || devide.equals("")) {
                sql.append(" = ?");
                parms.add(account.getCompanyID());
            }
        }

        // pp.ppcestuer = 2 判断此产品 是否合格  pp.productstate=02 模拟测试过的   pp.yjstatus =01 设计过零售价佣金的
        //pp.wholesaleStatus=01 设计过批发价佣金的
        // 未经过测试的 产品就是为合格
        //如果no不等null就是合格产品
        if (no != null && !no.equals("")) {
            sql.append(" and  (pp.yjstatus='01' or pp.wholesaleStatus='01') ");

            if (!no.equals("0"))//1为已发布
            {
                sql.append(" and pp.ppid = ps.ppid and pp.showweixin='01'");//ljc

            } else {
                sql.append(" and pp.ppid = ps.ppid and (pp.showweixin!='01' or pp.showweixin is null)");//ljc

            }
        }
       
	
	/*	if (typeString != null) {

			if (typeString.equals("0")) {
				sql += " and (pp.showweixin = '01' or  pp.ppEnterprise='01' or  pp.ppDifferential='01' or pp.ppDifferentialshop ='01' or pp.ppweb='01' or pp.ppother='01')";
			}
			
			
		}*/
        // sql+=" and pp.organizationid=?";
        // parms.add(organizationID);
        //营销处产品设计查询条件
        if (search != null && search.equals("search")) {
            productDesign = (ProductPackaging) session.get("tablesearch");
            if (productDesign.getGoodsName() != null
                    && !productDesign.getGoodsName().equals("")) {
                sql.append(" and pp.goodsname like ?");
                parms.add("%" + productDesign.getGoodsName() + "%");
            }
            if (sdate != null && edate != null && !("").equals(sdate)
                    && !("").equals(edate)) {
                sql.append(" and pp.packagingdate between ? and ? ");

                parms.add(Utilities.getDateFromString(sdate + " 00:00:00",
                        "yyyy-MM-dd hh:mm:ss"));

                parms.add(Utilities.getDateFromString(edate + " 23:59:59",
                        "yyyy-MM-dd hh:mm:ss"));
            }
            if (showweixin != null && !showweixin.equals("")) {
                if (showweixin.equals("01")) {
                    sql.append(" and pp.showweixin = ?");
                } else {
                    sql.append(" and pp.showweixin != ?");
                }
                parms.add("01");
            }
        }

        //产品发布 已发布产品信息汇总查询条件
        if (search != null && "001".equals(search)) {
            productDesign = (ProductPackaging) session.get("tablesearch");
            if (productDesign.getGoodsName() != null && !productDesign.getGoodsName().equals("")) {
                sql.append(" and pp.goodsname like ?");
                parms.add("%" + productDesign.getGoodsName() + "%");
            }
            if (productDesign.getStaffName() != null && !productDesign.getStaffName().equals("")) {
                sql.append(" and pp.staffname like ?");
                parms.add("%" + productDesign.getStaffName() + "%");
            }
            if (productDesign.getBarCode() != null && !productDesign.getBarCode().equals("")) {
                sql.append(" and pp.barCode like ?");
                parms.add("%" + productDesign.getBarCode() + "%");
            }
            if (showweixin != null && !showweixin.equals("")) {
                if (showweixin.equals("01")) {
                    sql.append(" and pp.showweixin = ?");
                } else {
                    sql.append(" and pp.showweixin != ?");
                }
                parms.add("01");
            }
        }


        // sql+=" and p.category='零售价'";
        //sql += " and pp.parentId is null ";
        if (fiveClear != null && !fiveClear.equals("") && !fiveClear.equals("undefined")) {
            sql.append(" and pp.fiveClear=? ");
            parms.add(fiveClear);
        }

        if (flexbutton != null && flexbutton.equals("yongjin")) {
            sql.append(" and pp.yjstatus=?");
            parms.add("01");
        }
        if (chaoshi != null && chaoshi.equals("platform")) {
            sql.append(" and pp.platform=?");
            parms.add("1");
        }

        sql.append(" and pp.delstatus ='00' order by pp.packagingdate desc");
        //System.out.println(sql.toString());
        result.add(sql.toString());
        result.add(parms.toArray());
        return result;
    }

    /**
     * 公司id、产品id 查询产品
     *
     * @return
     */
    public String getProductdesignByParam() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String ppidString = request.getParameter("ppID");

        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return "not_login";
        }
        String sql = "select pp.ppid,pp.companyid,cp.companyname,co.organizationname,hs.staffname, pp.packagingdate,gm.goodscoding,pp.goodsname,"
                + " gm.variableID,gm.acquiescestandard,pp.quantity,pp.weight,pp.manualurl,pp.propagandaurl,pp.fileurl,gm.goodsid,gm.typeid, pp.price,pp.money,pp.showweixin "
                +
                // 在goodsid 之后, p.category, p.price,p.money,
                " from dt_productpackaging pp"
                + " left join dtcompany cp on cp.companyid=pp.companyid"
                + " left join dtcorganization co on co.organizationid=pp.organizationid"
                + " left join dt_hr_staff hs on hs.staffid=pp.staffid"
                + " left join dtgoodsmanage gm on gm.goodsid=pp.goodsid ";

        sql += " where pp.ppid = ? ";
        codeList = codeService.getCCodeListByPID(account.getCompanyID(),
                "scode20101014v5zed7cukk0000000002");
        //查询 定价表  hql getCompanyID
        String hql = "from productPackajiage where ppId = ? and compId = ?";
        productPackajiage = (productPackajiage) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ppidString, account.getCompanyID()});

        if (productPackajiage == null) {
            productPackajiage = new productPackajiage();
        }

        // 单价管理
        priceManageList = codeService.getCCodeListByPID(account.getCompanyID(),
                "scode20101101dfs3uhdprp0000000032");
        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageForm ? pageForm.getPageNumber() : 1),
                (pageNumber == 0 ? 15 : pageNumber), sql, "select count(1) "
                        + sql.substring(sql.indexOf("from")),
                new Object[]{ppidString});

        return "addjiagePage";

    }

    public String getListProductdesign() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return "not_login";
        }
        HttpServletRequest request = ServletActionContext.getRequest();
        String typeString = request.getParameter("pptype");
        ActionContext.getContext().put("typeString", typeString);
        List<Object> list = getList();
        session.put("sqllist", list);

        String sql = (String) list.get(0);

        Object[] parms = (Object[]) list.get(1);
        codeList = codeService.getCCodeListByPID(account.getCompanyID(),
                "scode20101014v5zed7cukk0000000002");
        // 单价管理
        priceManageList = codeService.getCCodeListByPID(account.getCompanyID(),
                "scode20101101dfs3uhdprp0000000032");
        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageForm ? pageForm.getPageNumber() : 1),
                (pageNumber == 0 ? 15 : pageNumber), sql, "select count(distinct(pp.ppid)) "
                        + sql.substring(sql.indexOf("from")), parms);
        return "productDesign";
    }

    public String showExcel() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return "not_login";
        }
        String organizationID = (String) session.get("organizationID");
        //List<BaseBean> list=(List<BaseBean>)session.get("showList");
        List<Object> sqllist = (List<Object>) session.get("sqllist");
        if (sqllist != null) {
            sqllist = (List<Object>) session.get("sqllist");
        } else {
            return "not_login";
        }
        String excelSql = (String) sqllist.get(0);
        String sel = "select distinct(pp.ppid),cp.companyname,co.organizationname,hs.staffname, pp.packagingdate,pp.productcode," +
                "pp.barcode,pp.goodsname, gm.variableID,gm.typeid,pp.quantity,to_char(ps.ef_price,'fm999999990.00'),to_char(nvl(ps.re_price*(1+sb.total_pct*0.01),ps.re_price),'fm999999990.00') as price," +
                "to_char(pw.wholesale*(1+nvl(sb.total_pct,0)/100),'fm999999990.00') wholesale,to_char(vip.vip*(1+nvl(sb.total_pct,0)/100),'fm999999990.00') vipprice,"+
                "case when sysdate between pa.starttime and pa.endtime then to_char(ap.actPrice*(1+nvl(sb.total_pct,0)/100),'fm999999990.00') else '' end  as actprice,"+
                "pp.weight,to_multi_byte(case when pp.showweixin ='01' then '已发布' else '未发布' end) ," +
                "to_multi_byte(case when pp.weight ='00' then '未设计佣金' else '已设计佣金' end)";
        String frm = excelSql.substring(excelSql.indexOf("from"), excelSql.indexOf("where"));
        String join = excelSql.substring(excelSql.indexOf("where") + 5);
        String where = "where";
        excelSql = sel + " " + frm + " " + where + " " + join;
        Object[] params = (Object[]) sqllist.get(1);

		/*pageForm = baseBeanService.getPageFormBySQL(
                (null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 15 : pageNumber), excelSql, "select count(1) "
						+ excelSql.substring(excelSql.indexOf("from")), parms);*/
//		List<BaseBean> list=pageForm.getList();

        List<BaseBean> list = (List<BaseBean>) baseBeanService.getListBeanBySqlAndParams(excelSql, params);

        for (int i = 0; i < list.size(); i++) {
            Object obj = (Object) list.get(i);
            Object[] os = (Object[]) obj;
            for (int j = 0; j < os.length; j++) {
                if (j < os.length - 1) {
                    os[j] = os[j + 1];
                } else {
                    os[j] = null;
                }
            }
        }
        excelStream = excelService.showExcel(new String[]{"序号", "公司名称", "部门", "责任人", "包装日期", "产品编号", "条码", "产品名称", "产品单位", "产品类型",
                "产品数量", "成本价", "零售价","批发价","vip价","活动价","产品重量", "发布状态", "佣金状态"}, list);

        CLogBook logBook = logBookService.saveCLogBook(organizationID, "产品管理导出", account);
        baseBeanService.update(logBook);
        return "showexcel";
    }
    //标签打印//活动价打印
    public String getPrintLabelList() {
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select distinct(p.ppid),p.goodsid ,p.goodsname,nvl(pss.re_price*(1+sz.total_pct*0.01),pss.re_price) as price ,");//零售价
        sql.append(" i.areaname,i.framename,i.positionname,p.standard,p.tradename,p.barCode,p.variableid,");
        sql.append(" ps.actPrice * (1 + nvl(sz.total_pct, 0) / 100) actprice,");//活动价
        sql.append(" vip.vip*(1+nvl(sz.total_pct,0)/100) vipprice");//vip价
        sql.append(" from dt_ProductPackaging p left join dt_pro_activity_price ps on ps.ppid = p.ppid");
        sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid");
        sql.append(" left join dtCompany t on t.companyid = cc.compnay_id");
        sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
        sql.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = ?");
        sql.append(" left join dt_inv_invt i on i.goodsid = p.goodsid and i.goodstatus = ?");
        sql.append(" left join dt_pro_vip vip on vip.ppid = p.ppid left join dt_pro_setup pss on  p.ppid = pss.ppid");
        sql.append(" where price is not null");
        param.add("01");
        param.add("00");
        if(activityid!=null&&!"".equals(activityid)){
            sql.append(" and ps.activityid =?");
            param.add(activityid);
        }else{
            sql.append(" and p.ppid  in(");
            for (int i = 0; i < ppIDList.length; i++) {
                if (i == (ppIDList.length - 1)) {
                    sql.append("?)");
                } else {
                    sql.append("?,");
                }
                param.add(ppIDList[i]);
            }
        }
        List<Object> printList = baseBeanService.getListBeanBySqlAndParams(sql.toString(), param.toArray());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("printList", printList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    //今日价格表
    public String todayPrice(){
        List<Object> param = new ArrayList<Object>();
        HttpServletRequest request = ServletActionContext.getRequest();
        String companyid = request.getParameter("companyid");
        StringBuffer sql = new StringBuffer();
        sql.append("select distinct(p.ppid),p.goodsid ,p.goodsname,nvl(pss.re_price*(1+sz.total_pct*0.01),pss.re_price) as price ,");//零售价
        sql.append(" p.standard,p.tradename,p.barCode,p.variableid,ps.actPrice * (1 + nvl(sz.total_pct, 0) / 100) actprice,");//活动价
        sql.append(" vip.vip*(1+nvl(sz.total_pct,0)/100) vipprice, to_char(pa.starttime,'MM/dd HH24:MI') stime,to_char(pa.endtime,'MM/dd HH24:MI') etime ,pa.type");//时间
        sql.append(" from dt_pro_setup pss left join dt_ProductPackaging p on p.ppid = pss.ppid ");
        sql.append(" left join dt_pro_activity_price ps on ps.ppid = p.ppid and ps.state=? ");
        sql.append(" left join dt_pro_activity pa on pa.activityId = ps.activityid and pa.state<> ? and pa.state<> ? and pa.state<> ?");
        sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid");
        sql.append(" left join dtCompany t on t.companyid = cc.compnay_id");
        sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
        sql.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = ?");
        sql.append(" left join dt_pro_vip vip on vip.ppid = p.ppid and vip.state=?");
        sql.append(" where price is not null and p.showweixin = ? and pss.state=?");
        sql.append(" and ((TO_CHAR(pa.starttime,'DS') is null) or (TO_CHAR(SYSDATE,'DS')>=TO_CHAR(pa.starttime,'DS') and sysdate<pa.endtime))");
        //sql.append(" and pss.sjdate in (select max(sjdate) from dt_pro_setup group by ppid)");
        param.add("00");
        param.add("02");
        param.add("03");
        param.add("04");
        param.add("01");
        param.add("00");
        param.add("01");
        param.add("00");
        if(companyid!=null&&!"".equals(companyid)){
            sql.append(" and t.companyid = ?");
            param.add(companyid);
        }

        sql.append(" order by stime");

        int pageSize=10;
        //pageNumber = pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1;
        pageForm = this.baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")", param.toArray());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        //map.put("priceList",pageForm.getList());
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }



    /**
     * @return
     */
    public String getListSupermarketDesign() {

        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return "not_login";
        }
        HttpServletRequest request = ServletActionContext.getRequest();
        String typeString = request.getParameter("pptype");
        ActionContext.getContext().put("typeString", typeString);
        List<Object> list = getList();
        session.put("sqllist", list);
        String sql = (String) list.get(0);
        Object[] parms = (Object[]) list.get(1);
        codeList = codeService.getCCodeListByPID(account.getCompanyID(),
                "scode20101014v5zed7cukk0000000002");
        // 单价管理
        priceManageList = codeService.getCCodeListByPID(account.getCompanyID(),
                "scode20101101dfs3uhdprp0000000032");
        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageForm ? pageForm.getPageNumber() : 1),
                (pageNumber == 0 ? 15 : pageNumber), sql, "select count(1) "
                        + sql.substring(sql.indexOf("from")), parms);

        return "supermarketDesign";
    }

    /**
     * 暂时用于分离数据用，
     *
     * @return
     */
    public String updateCate() {
        try {
            String hql = "update ProductPackaging set fiveClear = ? where ppID = ?";
            baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, new Object[]{fiveClear, productDesign.getPpID()});
        } catch (Exception e) {

            e.printStackTrace();
        }

        return "success";


    }

    /**
     * 产品价格设置
     */
    public String getpackegejiage() {

        productPackajiage.setPckdeta(new Date());

        beans = new ArrayList<BaseBean>();
        if (productPackajiage.getPckkey() == null || "".equals(productPackajiage.getPckkey())) {

            productPackajiage.setPckID(serverService.getServerID("productPackajiage"));
            beans.add(productPackajiage);
            baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);

        } else {
            beans.add(productPackajiage);
            baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        }
        return "success";
    }

    /**
     * 进入带有菜单数树的产品包装设计推广页面
     *
     * @return
     */

    public String getFilePackageProduct() {
        if (null != produce && produce.equals("Company")) {
            return "cList";
        }
        if (null != produce && produce.equals("group")) {
            return "gList";
        }
        return "list";
    }


    public String addProductdesign() {

        if ("saveContentToFile".equals(getActionName())) {
            return saveContentToFile();
        }

        ActionContext actionContext = ActionContext.getContext();
        Map<String, Object> session = actionContext.getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return "not_login";
        }
        String organizationID = (String) session.get("organizationID");
        HttpServletRequest request = ServletActionContext.getRequest();
        beans = new ArrayList<BaseBean>();
        String ppId = serverService.getServerID("productpackaging");

        if (productDesign.getPpID() == null
                || "".equals(productDesign.getPpID())) {
            productDesign.setPpID(ppId);
            parameter = "添加产品包装设计(商品名称:" + productDesign.getGoodsName() + ")";
            productDesign.setOrganizationID(organizationID);
            productDesign.setCompanyID(account.getCompanyID());
            productDesign.setStaffID(account.getStaffID());

            // 保存图片
            String hidIdList = request.getParameter("hidIdList");
            String[] imageArray = hidIdList.split(",");
            if (imageArray.length > 0) {
                productDesign.setImage(imageArray[0]);
            }
            productDesign.setPackagingDate(new Date());
            beans.add(productDesign);

            // 添加产品类别
            if (prdCategory != null && prdCategory.length > 0) {
                for (String item : prdCategory) {
                    TPrdcodeRelId cid = new TPrdcodeRelId(ppId, item);
                    TPrdcodeRel tpr = new TPrdcodeRel(cid);
                    beans.add(tpr);
                }
            }
            // 结束
        } else {
            String hql2 = "from ProductPackaging where companyID=?  and ppID=?";
            ProductPackaging aff = (ProductPackaging) baseBeanService
                    .getBeanByHqlAndParams(hql2, new Object[]{
                            account.getCompanyID(), productDesign.getPpID()});
            productDesign.setPpKey(aff.getPpKey());
            parameter = "修改产品包装设计(商品名称:" + aff.getGoodsName() + ")";

            // 修改
            String hidIdList = request.getParameter("hidIdList");
            String[] imageArray = hidIdList.split(",");
            if (imageArray[0] != "") {
                String[] hql22 = {"update ProductPackaging set money=?,quantity=?,image=?,showweixin=?,weiDianType=?,certificateCost=?  where  companyID=?  and ppID=?"};
                Object[] paramss = {productDesign.getMoney(),
                        productDesign.getQuantity(), imageArray[0],
                        productDesign.getShowweixin(),
                        productDesign.getWeiDianType(),
                        productDesign.getCertificateCost(),
                        account.getCompanyID(), productDesign.getPpID()};
                baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
                        hql22, paramss);
            } else {
                String[] hql22 = {"update ProductPackaging set money=?,quantity=?,showweixin=?,weiDianType=?,certificateCost=? where  companyID=?  and ppID=?"};
                Object[] paramss = {productDesign.getMoney(),
                        productDesign.getQuantity(),
                        productDesign.getShowweixin(),
                        productDesign.getWeiDianType(),
                        productDesign.getCertificateCost(),
                        account.getCompanyID(), productDesign.getPpID()};
                baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
                        hql22, paramss);
            }

            Object[] params = {productDesign.getPpID()};
            String[] hql3 = {"delete from ProductPriceCategory where  ppID=?"};
            baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql3,
                    params);
            String[] hql4 = {"delete  from ProductPackaging where parentId=?"};
            baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql4,
                    params);
        }

        // 保存配件
        if (productpeijian != null && productDesign.getGoodsID().length() > 0) {
            String[] pgoodsID = productpeijian.getGoodsID().split(",");
            String[] pname = productpeijian.getGoodsName().split(",");
            String[] pweight = productpeijian.getWeight().split(",");
            String[] pquantity = productpeijian.getQuantity().split(",");
            String[] pprice = productpeijian.getPrice().split(",");
            String[] pmoney = productpeijian.getMoney().split(",");
            ProductPackaging products = null;
            for (int i = 0; i < pquantity.length; i++) {
                products = new ProductPackaging();
                products.setOrganizationID(organizationID);
                products.setCompanyID(account.getCompanyID());
                products.setStaffID(account.getStaffID());
                products.setParentId(productDesign.getPpID());
                products.setGoodsID(pgoodsID[i].trim());
                products.setGoodsName(pname[i].trim());
                products.setWeight(pweight[i].trim());
                productDesign.setPptype("1");
                products.setQuantity(pquantity[i].trim());
                products.setPrice(pprice[i].trim());
                products.setMoney(pmoney[i].trim());
                products.setPackagingDate(new Date());
                beans.add(products);
            }
        }

        // 保存价格类型
        String[] category = productPriceCategory.getCategory().split(",");
        String[] price = productPriceCategory.getPrice().split(",");
        String[] money = productPriceCategory.getMoney().split(",");
        ProductPriceCategory priceCategory = null;
        for (int i = 0; i < price.length; i++) {
            priceCategory = new ProductPriceCategory();
            priceCategory.setPcID(serverService
                    .getServerID("productPriceCategory"));
            priceCategory.setCategory(category[i].trim());
            priceCategory.setPrice(price[i].trim());
            priceCategory.setMoney(money[i].trim());
            priceCategory.setPpID(productDesign.getPpID());

            beans.add(priceCategory);
        }

        CLogBook logBook = logBookService.saveCLogBook(organizationID,
                parameter, account);
        beans.add(logBook);
        baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        if ("title".equals(title)) {
            showType = "edit";
            return getProductdesignAddorEdit();
        }
        return "success";
    }

    public String delProductdesign() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return "not_login";
        }
        String organizationID = (String) session.get("organizationID");
        Object[] params = {account.getCompanyID(), productDesign.getPpID()};
        String hql2 = "from ProductPackaging where companyID=?  and ppID=?";
        ProductPackaging aff = (ProductPackaging) baseBeanService
                .getBeanByHqlAndParams(hql2, params);
        beans = new ArrayList<BaseBean>();
        CLogBook logBook = logBookService.saveCLogBook(organizationID,
                "删除商品包装设计(商品名称:" + aff.getGoodsName() + ")", account);
        beans.add(logBook);
        String hql4 = "delete  from ProductPackaging where parentId=? ";
        String[] hql = {"delete from ProductPackaging where companyID=?  and ppID=? "};
        baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, params);
        String hql3 = "delete from ProductPriceCategory where  ppID=? ";
        String hql6 = "delete from Profitshare where ppid=? ";
        baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
                new String[]{hql4, hql3, hql6}, new Object[]{productDesign
                        .getPpID()});
        // 删除产品分类与产品对应关系(t_prdcode_rel)
        String[] hql5 = {"delete from t_prdcode_rel where ppid=?"};
        List<Object[]> parmsList = new ArrayList<Object[]>();
        Object[] objArray = new Object[]{productDesign.getPpID()};
        parmsList.add(objArray);
        baseBeanService.executeSqlsByParmsList(null, hql5, parmsList);

        return "success";
    }

    public String getProductdesignAddorEdit() {

        if ("getContentFromFile".equals(getActionName())) {
            return getContentFromFile();
        }

        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return "not_login";
        }
        customersForms = (CustomersForms) baseBeanService
                .getBeanByHqlAndParams(
                        " from CustomersForms where companyID=? ",
                        new Object[]{account.getCompanyID()});
        if ("edit".equals(showType)) {
            String hql2 = "from ProductPackaging where companyID=?  and ppID=?";
            Object[] params = {account.getCompanyID(), productDesign.getPpID()};
            productDesign = (ProductPackaging) baseBeanService
                    .getBeanByHqlAndParams(hql2, params);

            String hqlgood = "from GoodsManage where goodsID=?";
            Object[] paramsgood = {productDesign.getGoodsID()};
            goodsManage = (GoodsManage) baseBeanService.getBeanByHqlAndParams(
                    hqlgood, paramsgood);
            String sqlCategory = "select pg.ppid,pg.category,pg.price,pg.money from dt_productPriceCategory pg where pg.ppID=?";
            Object[] paramCategory = {productDesign.getPpID()};
            priceManageList = codeService.getCCodeListByPID(account
                    .getCompanyID(), "scode20101101dfs3uhdprp0000000032");
            pageForm = baseBeanService.getPageFormBySQL(
                    (null != pageForm ? pageForm.getPageNumber() : 1),
                    (pageNumber == 0 ? 15 : pageNumber), sqlCategory,
                    "select count(1) "
                            + sqlCategory
                            .substring(sqlCategory.indexOf("from")),
                    paramCategory);
            // 配件
            String sqlpeijian = "select g.goodsid, g.goodscoding,g.goodsname,g.variableid,g.standard,g.typeid,p.weight,p.quantity,p.price,p.money "
                    + " from dt_productpackaging p ,dtgoodsmanage g where p.goodsid=g.goodsid and p.parentid=?";
            pageFormpeijian = baseBeanService.getPageFormBySQL(
                    (null != pageForm ? pageForm.getPageNumber() : 1),
                    (pageNumber == 0 ? 15 : pageNumber), sqlpeijian,
                    "select count(1) "
                            + sqlpeijian.substring(sqlpeijian.indexOf("from")),
                    paramCategory);
        }
        codeList = codeService.getCCodeListByPID(account.getCompanyID(),
                "scode20101014v5zed7cukk0000000002");
        if ("type".equals(priceType)) {
            return "productdesignEdit";
        }
        return "productdesignAddorEdit";
    }

    /**
     * 保存客户分类报表菜单启用项
     *
     * @return
     */
    public String saveHumanCollect() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        String organizationID = (String) session.get("organizationID");
        Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
                "from Staff where staffID=?", new Object[]{account
                        .getStaffID()});
        if (customersForms.getCustomersFormsid() == null
                || "".equals(customersForms.getCustomersFormsid())) {
            customersForms.setCustomersFormsid(serverService
                    .getServerID("customersForms"));
            parameter = staff.getStaffName() + "添加了客户分类报表菜单启用项";
        } else {
            parameter = staff.getStaffName() + "修改了客户分类报表菜单启用项";
        }
        customersForms.setCompanyid(account.getCompanyID());
        beans = new ArrayList<BaseBean>();
        beans.add(customersForms);
        CLogBook logBook = logBookService.saveCLogBook(organizationID,
                parameter, account);
        beans.add(logBook);
        baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        return "success";
    }

    /**
     * 保存在线编辑的产品说明内容到磁盘物理文件。 数据库的PRODUCTDETAIL字段只记录存放的物理文件位置（相对位置）
     *
     * @return
     */
    private String saveContentToFile() {
        String path = ServletActionContext.getServletContext().getRealPath("")
                + "/upload_files/productDetail";
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("GBK");

        try {
            contentToFileService.saveContent(productDesign.getPpID(),
                    getHtmlContent(), path);
            response.getWriter().print("success");

            updateProductDetail("upload_files\\\\productDetail\\\\"
                    + productDesign.getPpID()
                    + UploadContentToFileService.suffix);

            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将文件相对路径保存到productdetail字段
     *
     * @param path
     */
    private void updateProductDetail(String path) {
        String[] sqlArray = {"update dt_productpackaging dp set dp.productdetail=? where dp.ppid=?"};
        List<Object[]> list = new ArrayList<Object[]>();
        Object[] objArray = {path, productDesign.getPpID()};
        list.add(objArray);
        baseBeanService.executeSqlsByParmsList(null, sqlArray, list);
    }

    /**
     * 读文件内容，由ajax脚本付给UE控件
     *
     * @return
     */
    private String getContentFromFile() {
        String path = ServletActionContext.getServletContext()
                .getRealPath("\\")
                + productDesign.getProductDetail();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().print(contentToFileService.getContent(path));
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @return
     * @author zc
     * @time 2015-02-02
     * @describe 产品设计新增与修改（佣金设计添加）
     */

    @SuppressWarnings("unchecked")
    public String toAddOrEditProductDesign() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        String type = request.getParameter("pptype");
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return "not_login";
        }
        String companyID = account.getCompanyID();

        //代理类别
        List<BaseBean> dlList = baseBeanService.getListBeanByHqlAndParams("from ProductPackaging where type=? and goodsname<>? order by sorting", new Object[]{"佣金分配代理类别", "佣金分配代理类别"});
        request.setAttribute("dlList", dlList);

        // 单价管理
        priceManageList = codeService.getCCodeListByPID(companyID,
                "scode20101101dfs3uhdprp0000000032");

        billID = serverService.getBillID(account.getCompanyID());
        // 商品分类
        proType = codeService.getCCodeListByPID(companyID,
                "scode20150601pqmwffduns0000000002");
        // 责任人name
        String hqlForMan = "from Staff where staffID = ?";
        staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
                new Object[]{account.getStaffID()});
        String addOrEdit = request.getParameter("addOrEdit");
        StringBuilder tsql = new StringBuilder("select 1+nvl(s.total_pct,0)/100");
        tsql.append(" from dtContactCompany dc left join dt_ccom_com cc on dc.ccompanyId = cc.ccompany_Id");
        tsql.append(" left join dt_set_subsidize s");
        tsql.append(" on dc.industrytype = s.gtid  and s.stutas= ? where cc.compnay_id = ?");
        Object pct = baseBeanService.getObjectBySqlAndParams(tsql.toString(), new Object[]{"01", companyID});
        ActionContext.getContext().put("pct", pct);
        if ("edit".equals(showType) || "editYj".equals(showType)) {
            String hql = "from ProductPackaging where companyID=?  and ppID=?";
            Object[] params = {account.getCompanyID(), productDesign.getPpID()};
            productDesign = (ProductPackaging) baseBeanService
                    .getBeanByHqlAndParams(hql, params);

            String hqlpeijian = "from ProductPackaging where companyID=?  and parentId=? order by sorting asc ";
            Object[] paramspeijian = {account.getCompanyID(),
                    productDesign.getPpID()};
            productPackagingList = baseBeanService.getListBeanByHqlAndParams(
                    hqlpeijian, paramspeijian);
            String hqlprice = "from ProductPriceCategory where  ppID=?";
            Object[] paramsprice = {productDesign.getPpID()};
            productPriceCategoryList = baseBeanService
                    .getListBeanByHqlAndParams(hqlprice, paramsprice);
            String hqltrp = "from TPrdcodeRel where  id.ppid=?";
            Object[] paramstrp = {productDesign.getPpID()};
            TPrdcodeRel trp = (TPrdcodeRel) baseBeanService
                    .getBeanByHqlAndParams(hqltrp, paramstrp);
            if (trp != null) {
                ccode = (CCode) codeService.getCCodeByID(
                        account.getCompanyID(), trp.getId().getCodeid());
            }
            if (flexbutton != null && "publish".equals(flexbutton)) {
                String hqlpub = "from Profitshare where ppid=?";
                profitshare = (Profitshare) baseBeanService
                        .getBeanByHqlAndParams(hqlpub, paramsprice);
            }
            String pc = "from ProSetup where ppid=? and comId=?";
            List<BaseBean> pclist = baseBeanService.getListBeanByHqlAndParams(pc, new Object[]{productDesign.getPpID(), account.getCompanyID()});
            List<ProSetup> ps = new ArrayList<ProSetup>();
            for (int j = 0; j < pclist.size(); j++) {
                ProSetup prosetup = (ProSetup) pclist.get(j);
                StringBuilder psssql = new StringBuilder("select pss.susid,p.goodsname,pss.amount,pss.type_ppid");
                psssql.append(" from dt_pro_setup s, dt_pro_setup_sub pss, dt_productpackaging p");
                psssql.append(" where s.suid = pss.suid");
                psssql.append(" and pss.type_ppid = p.ppid");
                psssql.append(" and s.suid=?");
                List<Object> dlyjlist = baseBeanService.getListBeanBySqlAndParams(psssql.toString(), new Object[]{prosetup.getSuid()});
                prosetup.setVallist(dlyjlist);
                String bs_name = "select new ProMain(m.yjKey,m.yjId,m.meNumber,m.status,m.dcStatus,m.comId,m.codeId,p.goodsName)" +
                        " from ProMain m,ProductPackaging p where m.codeId=p.ppID and m.comId=?" +
                        " and m.dcStatus=? and m.status=? order by m.meNumber";
                String comid = prosetup.getFcomId() == null || prosetup.getFcomId().equals("") ? account.getCompanyID() : prosetup.getFcomId();
                List<BaseBean> bsnames = baseBeanService.getListBeanByHqlAndParams(bs_name, new Object[]{comid, "01", "00"});
                prosetup.setPmlist(bsnames);
                List<Object> pamer = new ArrayList<Object>();
                String yj = "select p1.goodsname";
                for (int i = 0; i < bsnames.size(); i++) {
                    ProMain proMain = (ProMain) bsnames.get(i);
                    yj += " ,max(decode(t1.bsid, ?, PRO_MONEY))";
                    yj += " val" + i;

                    pamer.add(proMain.getYjId());
                }

                yj += " from dt_pro_layout t1 ,dt_pro_main m1,dt_productpackaging p1" +
                        " where t1.com_Id = ? and m1.yj_id=t1.meid and p1.ppid=m1.code_id" +
                        " and t1.statu = ?" +
                        " group by p1.goodsname order by max(m1.me_number)";
                pamer.add(comid);
                pamer.add("01");
                List<Object> yjlist = baseBeanService.getListBeanBySqlAndParams(yj, pamer.toArray());
                prosetup.setYjlist(yjlist);
                ps.add(prosetup);
            }
            ActionContext.getContext().put("pclist", ps);
            //System.out.println(ps.size());
        } else {
            getval(account.getCompanyID());
        }

        ActionContext.getContext().put("pptype", type);
        session.put("session_value", Math.random() + "");
        if (flexbutton != null && "yongjin".equals(flexbutton)
                || "type".equals(flexbutton)) {
            request.setAttribute("addOrEdit", addOrEdit);
            return "yongjin";
        }
        if (flexbutton != null && "publish".equals(flexbutton)
                || "type".equals(flexbutton)) {
            return "productPublish";
        } else {
            return "toAddOrEditProductDesign";
        }
    }


    public String toAddOrEditPercentageDesign() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        String type = request.getParameter("pptype");
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return "not_login";
        }
        String companyID = account.getCompanyID();

        //代理类别
        List<BaseBean> dlList = baseBeanService.getListBeanByHqlAndParams("from ProductPackaging where type=? and goodsname<>? order by sorting", new Object[]{"佣金分配代理类别", "佣金分配代理类别"});
        request.setAttribute("dlList", dlList);

        // 单价管理
        priceManageList = codeService.getCCodeListByPID(companyID,
                "scode20101101dfs3uhdprp0000000032");

        billID = serverService.getBillID(account.getCompanyID());
        // 商品分类
        proType = codeService.getCCodeListByPID(companyID,
                "scode20150601pqmwffduns0000000002");
        // 责任人name
        String hqlForMan = "from Staff where staffID = ?";
        staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
                new Object[]{account.getStaffID()});
        String addOrEdit = request.getParameter("addOrEdit");
        StringBuilder tsql = new StringBuilder("select 1+nvl(s.total_pct,0)/100");
        tsql.append(" from dtContactCompany dc left join dt_ccom_com cc on dc.ccompanyId = cc.ccompany_Id");
        tsql.append(" left join dt_set_subsidize s");
        tsql.append(" on dc.industrytype = s.gtid  and s.stutas= ? where cc.compnay_id = ?");
        Object pct = baseBeanService.getObjectBySqlAndParams(tsql.toString(), new Object[]{"01", companyID});
        ActionContext.getContext().put("pct", pct);
        if ("edit".equals(showType) || "editYj".equals(showType)) {
            String hql = "from ProductPackaging where companyID=?  and ppID=?";
            Object[] params = {account.getCompanyID(), productDesign.getPpID()};
            productDesign = (ProductPackaging) baseBeanService
                    .getBeanByHqlAndParams(hql, params);

            String hqlpeijian = "from ProductPackaging where companyID=?  and parentId=? order by sorting asc ";
            Object[] paramspeijian = {account.getCompanyID(),
                    productDesign.getPpID()};
            productPackagingList = baseBeanService.getListBeanByHqlAndParams(
                    hqlpeijian, paramspeijian);
            String hqlprice = "from ProductPriceCategory where  ppID=?";
            Object[] paramsprice = {productDesign.getPpID()};
            productPriceCategoryList = baseBeanService
                    .getListBeanByHqlAndParams(hqlprice, paramsprice);
            String hqltrp = "from TPrdcodeRel where  id.ppid=?";
            Object[] paramstrp = {productDesign.getPpID()};
            TPrdcodeRel trp = (TPrdcodeRel) baseBeanService
                    .getBeanByHqlAndParams(hqltrp, paramstrp);
            if (trp != null) {
                ccode = (CCode) codeService.getCCodeByID(
                        account.getCompanyID(), trp.getId().getCodeid());
            }
            if (flexbutton != null && "publish".equals(flexbutton)) {
                String hqlpub = "from Profitshare where ppid=?";
                profitshare = (Profitshare) baseBeanService
                        .getBeanByHqlAndParams(hqlpub, paramsprice);
            }
            String pc = "from ProSetup where ppid=? and comId=?";
            List<BaseBean> pclist = baseBeanService.getListBeanByHqlAndParams(pc, new Object[]{productDesign.getPpID(), account.getCompanyID()});
            List<ProSetup> ps = new ArrayList<ProSetup>();
            for (int j = 0; j < pclist.size(); j++) {
                ProSetup prosetup = (ProSetup) pclist.get(j);
                StringBuilder psssql = new StringBuilder("select pss.susid,p.goodsname,pss.amount,pss.type_ppid");
                psssql.append(" from dt_pro_setup s, dt_pro_setup_sub pss, dt_productpackaging p");
                psssql.append(" where s.suid = pss.suid");
                psssql.append(" and pss.type_ppid = p.ppid");
                psssql.append(" and s.suid=?");
                List<Object> dlyjlist = baseBeanService.getListBeanBySqlAndParams(psssql.toString(), new Object[]{prosetup.getSuid()});
                prosetup.setVallist(dlyjlist);
                String bs_name = "select new ProMain(m.yjKey,m.yjId,m.meNumber,m.status,m.dcStatus,m.comId,m.codeId,p.goodsName)" +
                        " from ProMain m,ProductPackaging p where m.codeId=p.ppID and m.comId=?" +
                        " and m.dcStatus=? and m.status=? order by m.meNumber";
                String comid = prosetup.getFcomId() == null || prosetup.getFcomId().equals("") ? account.getCompanyID() : prosetup.getFcomId();
                List<BaseBean> bsnames = baseBeanService.getListBeanByHqlAndParams(bs_name, new Object[]{comid, "01", "00"});
                prosetup.setPmlist(bsnames);
                List<Object> pamer = new ArrayList<Object>();
                String yj = "select p1.goodsname";
                for (int i = 0; i < bsnames.size(); i++) {
                    ProMain proMain = (ProMain) bsnames.get(i);
                    yj += " ,max(decode(t1.bsid, ?, PRO_MONEY))";
                    yj += " val" + i;

                    pamer.add(proMain.getYjId());
                }

                yj += " from dt_pro_layout t1 ,dt_pro_main m1,dt_productpackaging p1" +
                        " where t1.com_Id = ? and m1.yj_id=t1.meid and p1.ppid=m1.code_id" +
                        " and t1.statu = ?" +
                        " group by p1.goodsname order by max(m1.me_number)";
                pamer.add(comid);
                pamer.add("01");
                List<Object> yjlist = baseBeanService.getListBeanBySqlAndParams(yj, pamer.toArray());
                prosetup.setYjlist(yjlist);
                ps.add(prosetup);
            }
            ActionContext.getContext().put("pclist", ps);
            //System.out.println(ps.size());
        } else {
            getval(account.getCompanyID());
        }

        ActionContext.getContext().put("pptype", type);
        session.put("session_value", Math.random() + "");
        if (flexbutton != null && "yongjin".equals(flexbutton)
                || "type".equals(flexbutton)) {
            request.setAttribute("addOrEdit", addOrEdit);
            return "yongjinPercent";
        }
        if (flexbutton != null && "publish".equals(flexbutton)
                || "type".equals(flexbutton)) {
            return "productPublish";
        } else {
            return "toAddOrEditPercentageDesign";
        }
    }

    @SuppressWarnings("unchecked")
    private List<Object> getval(String comid) {
        List<Object> pames = new ArrayList<Object>();

        String bs_name = "select new ProMain(m.yjKey,m.yjId,m.meNumber,m.status,m.dcStatus,m.comId,m.codeId,p.goodsName)" +
                " from ProMain m,ProductPackaging p where m.codeId=p.ppID and m.comId=?" +
                " and m.dcStatus=? and m.status=? order by m.meNumber";
        List<BaseBean> bsnames = baseBeanService.getListBeanByHqlAndParams(bs_name, new Object[]{comid, "01", "00"});
        pames.add(bsnames);
        ActionContext.getContext().put("main", bsnames);
        List<Object> pamer = new ArrayList<Object>();
        String yj = "select max(t1.meid), p1.goodsname";
        for (int i = 0; i < bsnames.size(); i++) {
            ProMain proMain = (ProMain) bsnames.get(i);
            yj += " ,max(nvl(decode(t1.bsid, ?, PRO_MONEY),' '))";
            yj += " val" + i;

            pamer.add(proMain.getYjId());
        }

        yj += " from dt_pro_layout t1 ,dt_pro_main m1,dt_productpackaging p1" +
                " where t1.com_Id = ? and m1.yj_id=t1.meid and p1.ppid=m1.code_id and t1.statu = ?" +
                " group by p1.goodsname order by max(m1.me_number)";
        pamer.add(comid);
        pamer.add("00");
        List<Object> yjlist = baseBeanService.getListBeanBySqlAndParams(yj, pamer.toArray());
        pames.add(yjlist);
        ActionContext.getContext().put("yjlist", yjlist);
        return pames;
    }

    @SuppressWarnings("unchecked")
    public String AjxaVal() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        String comid = request.getParameter("comid");
        List<Object> pames = getval(comid);
        Map<String, Object> map = new HashMap<String, Object>();
        List<BaseBean> bsnames = (List<BaseBean>) pames.get(0);
        map.put("main", bsnames);
        List<Object> yjlist = (List<Object>) pames.get(1);
        map.put("yjlist", yjlist);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 佣金设置保存
     *
     * @return
     */
    public String addyj() {
        List<BaseBean> beyj = new ArrayList();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount)session.get("account");
        if (account == null) {
            return "not_login";
        }
        String ppid = "";
        BigDecimal dlsum = new BigDecimal(0);
        List<ProSetupSub> pssList = new ArrayList();
        for (ProSetup setup : this.str.values())
        {
            ppid = setup.getPpid();
            ProSetup ps = (ProSetup)this.baseBeanService.getBeanByHqlAndParams("from ProSetup where ppid=?", new Object[] { ppid });
            if (ps != null)
            {
                setup.setSuid(ps.getSuid());
                setup.setSukey(ps.getSukey());
                setup.setEditDate(new Date());
            }
            else
            {
                setup.setSuid(this.serverService.getServerID("setup"));
                setup.setSjdate(new Date());
            }
            if ((setup.getState() == null) || ("".equals(setup.getState()))) {
                setup.setState("00");
            }
            setup.setComId(account.getCompanyID());
            if ((setup.getFcomId() != null) && (!setup.getFcomId().equals("")))
            {
                Company company = (Company)this.baseBeanService.getBeanByHqlAndParams("from Company where companyID=?", new Object[] { setup.getFcomId() });
                setup.setFcomName(company.getCompanyName());
            }
            List<BaseBean> ssList;
            BigDecimal dl;
            if (setup.getSetupSub() != null)
            {
                ssList = this.baseBeanService.getListBeanByHqlAndParams("from ProSetupSub where ppid=?", new Object[] { ppid });


                dl = null;
                for (ProSetupSub pss : setup.getSetupSub().values())
                {
                    ProSetupSub pss1 = null;
                    boolean flag = false;
                    for (BaseBean bb : ssList)
                    {
                        pss1 = (ProSetupSub)bb;
                        if (pss.getTypePpid().equals(pss1.getTypePpid()))
                        {
                            flag = true;
                            break;
                        }
                    }
                    if (flag)
                    {
                        pss.setSusid(pss1.getSusid());
                        pss.setSuskey(pss1.getSuskey());
                    }
                    else
                    {
                        pss.setSusid(this.serverService.getServerID("prosetupsub"));
                        pss.setSjdate(new Date());
                    }
                    pss.setPpid(ppid);
                    dl = new BigDecimal(pss.getAmount());
                    dlsum = dlsum.add(dl);
                    pss.setSuid(setup.getSuid());
                    beyj.add(pss);
                    pssList.add(pss);
                }
            }
            setup.setProxyprice(dlsum.toString());
            beyj.add(setup);
            this.productLaunchService.savePssb(setup, pssList, beyj);
        }
        this.productDesign = ((ProductPackaging)this.baseBeanService.getBeanByHqlAndParams("from ProductPackaging where ppID=?", new Object[] { ppid }));
        this.productDesign.setYjstatus("01");
        beyj.add(this.productDesign);
        this.baseBeanService.saveBeansListAndexecuteHqlsByParams(beyj, null, null);
        return "success";
    }

    public String addOrEditProductDesign() {

        if ("saveContentToFile".equals(getActionName())) {
            return saveContentToFile();
        }

        ActionContext actionContext = ActionContext.getContext();
        Map<String, Object> session = actionContext.getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return "not_login";
        }
        String organizationID = (String) session.get("organizationID");
        HttpServletRequest request = ServletActionContext.getRequest();
        beans = new ArrayList<BaseBean>();

        if (productDesign.getPpKey() == null || "".equals(productDesign.getPpKey())) {
            parameter = "添加产品包装设计(商品名称:" + productDesign.getGoodsName() + ")";
        } else {
            String hql2 = "from ProductPackaging where companyID=?  and ppID=?";
            ProductPackaging aff = (ProductPackaging) baseBeanService
                    .getBeanByHqlAndParams(hql2, new Object[]{
                            account.getCompanyID(), productDesign.getPpID()});
            productDesign.setPpKey(aff.getPpKey());
            productDesign.setShowweixin(aff.getShowweixin());
            productDesign.setProductDetail(aff.getProductDetail());
            parameter = "修改产品包装设计(商品名称:" + aff.getGoodsName() + ")";
        }
        productDesign.setYjstatus("00");//未设置佣金
        productDesign.setDelStatus("00");
        productDesign.setOrganizationID(organizationID);
        productDesign.setCompanyID(account.getCompanyID());
        productDesign.setStaffID(account.getStaffID());
        productDesign.setWeiDianType(productDesign.getWeiDianType());
        productDesign.setFiveClear(productDesign.getFiveClear());
        productDesign.setPpcestuer("1");
        // 保存图片列表
        String hidIdList = request.getParameter("hidIdList");
        if (hidIdList != null && !"".equals(hidIdList)) {
            String[] imageArray = hidIdList.split(",");
            if (imageArray.length > 0) {
                productDesign.setImage(imageArray[imageArray.length - 1]);
            }
        }
        productDesign.setPackagingDate(new Date());
        beans.add(productDesign);

        // 之前所属删除产品类别数据
        String hqltrp = "delete from TPrdcodeRel where  id.ppid=?";
        Object[] paramstrp = {productDesign.getPpID()};

        // 保存产品类别
        if (prdCategory != null && prdCategory.length > 0) {
            for (String item : prdCategory) {
                TPrdcodeRelId cid = new TPrdcodeRelId(productDesign.getPpID(),
                        item);
                TPrdcodeRel tpr = new TPrdcodeRel(cid);
                beans.add(tpr);
            }
        }

        // 保存配件列表
        if (productPackagingMap != null) {
            for (ProductPackaging pp : productPackagingMap.values()) {
                pp.setOrganizationID(organizationID);
                pp.setCompanyID(account.getCompanyID());
                pp.setStaffID(account.getStaffID());
                pp.setPackagingDate(new Date());
                beans.add(pp);
            }
        }

        // 保存价格类型
        String hql = "from ProductPriceCategory where ppID=? and category=?";
        ProductPriceCategory p = (ProductPriceCategory) baseBeanService
                .getBeanByHqlAndParams(hql, new Object[]{
                        productDesign.getPpID(), "零售价"});

        if (p == null) {
            ProductPriceCategory ppc = new ProductPriceCategory();
            ppc.setPcID(serverService.getServerID("productPriceCategory"));
            ppc.setCategory("零售价");
            ppc.setMoney(productDesign.getMoney());
            ppc.setPrice(productDesign.getPrice());
            ppc.setPpID(productDesign.getPpID());
            beans.add(ppc);
        } else {
            p.setMoney(productDesign.getMoney());
            p.setPrice(productDesign.getPrice());
            beans.add(p);
        }
        CLogBook logBook = logBookService.saveCLogBook(organizationID,
                parameter, account);
        beans.add(logBook);
        baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
                new String[]{hqltrp}, paramstrp);
        return "success";
    }

    /**
     * @return ID
     * @time 2015-02-04
     * @describe ajax根据父ID获得子物品
     */
    public String getProductPackagingListByPID() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        List<BaseBean> ProductPackagingList = null;
        if (productDesign != null && productDesign.getPpID() != null
                && !"".equals(productDesign.getPpID())) {
            ProductPackagingList = baseBeanService
                    .getListBeanByHqlAndParams(
                            "from ProductPackaging where companyID=?  and parentId=?  order by sorting desc ",
                            new Object[]{account.getCompanyID(),
                                    productDesign.getPpID()});
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productPackagingList", ProductPackagingList);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * @return ID
     * @time 2015-02-04
     * @describe ajax删除物品以及物品下的所有子物品
     */
    public String deleteProductPackagingByPID() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        if (productDesign != null && productDesign.getPpID() != null
                && !"".equals(productDesign.getPpID())) {
            deleteProductPackagingByPIDByRecursion(productDesign.getPpID());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", "success");
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * @param PID
     * @time 2014-02-05
     * @describe 递归删除
     * @author zc
     */
    public void deleteProductPackagingByPIDByRecursion(String PID) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        List<BaseBean> ProductPackagingList = baseBeanService
                .getListBeanByHqlAndParams(
                        " from ProductPackaging where companyID=?  and parentId=?",
                        new Object[]{account.getCompanyID(), PID});
        for (BaseBean baseBean : ProductPackagingList) {
            ProductPackaging pp = (ProductPackaging) baseBean;
            baseBeanService.deleteBeanByKey(ProductPackaging.class, pp
                    .getPpKey());
            deleteProductPackagingByPIDByRecursion(pp.getPpID());
        }
        baseBeanService
                .saveBeansListAndexecuteHqlsByParams(
                        null,
                        new String[]{" delete from ProductPackaging where companyID=?  and ppID=? "},
                        new Object[]{account.getCompanyID(), PID});
    }

    /**
     * @return ID
     * @time 2015-02-04
     * @describe ajax获得产品设计ID
     */
    public String getServerID() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("serverID", serverService.getServerID("productpackaging"));
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        //System.out.println(result);
        return "success";
    }

    /**
     * @return ID
     * @time 2015-02-04
     * @describe 删除ajax产品下的价格
     */
    public String deleteProductPriceCategoryByID() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        if (productPriceCategory != null
                && productPriceCategory.getPcID() != null
                && !"".equals(productPriceCategory.getPcID())) {
            baseBeanService
                    .saveBeansListAndexecuteHqlsByParams(
                            null,
                            new String[]{" delete from ProductPriceCategory   where  pcID=? "},
                            new Object[]{productPriceCategory.getPcID()});
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", "success");
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 产品发布
     *
     * @return
     */
    public String productPublish() {
        beans = new ArrayList<BaseBean>();
        HttpServletRequest request = ServletActionContext.getRequest();
        String typeString = request.getParameter("pptype");
        // 原产品价格设置
        // Profitshare profit = null;
        // String hqlpub = "from Profitshare where ppid=?";
        // profit = (Profitshare) baseBeanService.getBeanByHqlAndParams(hqlpub,
        // new Object[] { profitshare.getPpid() });
        // if (profit == null) {
        // profit = new Profitshare();
        // profit.setId(serverService.getServerID("profitshare"));
        // }
        // profit.setAgent(profitshare.getAgent());
        // profit.setCompany(profitshare.getCompany());
        // profit.setIntegral(profitshare.getIntegral());
        // profit.setPartner(profitshare.getPartner());
        // profit.setPpid(profitshare.getPpid());
        // profit.setSalesman(profitshare.getSalesman());
        // profit.setShop(profitshare.getShop());
        // profit.setRemark(profitshare.getRemark());
        // profit.setPublishDate(new Date());
        // beans.add(profit);
        String hql = "from ProductPackaging where ppID=? ";
        ProductPackaging p = (ProductPackaging) baseBeanService
                .getBeanByHqlAndParams(hql, new Object[]{profitshare
                        .getPpid()});
        if (typeString.equals("1")) {
            p.setShowweixin("01");

        } else {
            p.setShowweixin("02");

        }
        p.setPpEnterprise(productDesign.getPpEnterprise());
        p.setPpDifferential(productDesign.getPpDifferential());
        p.setPpDifferentialshop(productDesign.getPpDifferentialshop());
        p.setPpWeb(productDesign.getPpWeb());
        p.setPpOther(productDesign.getPpOther());
        p.setPptype(typeString);
        beans.add(p);
        baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        return "success";
    }

    /**
     * ty
     * 人事，办公室，财务，教务处，营销 查询
     **/

    private List<Object> getProductList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String typeString = request.getParameter("pptype");
        List<Object> result = new ArrayList<Object>();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        @SuppressWarnings("unused")
        String organizationID = (String) session.get("organizationID");
        List<Object> parms = new ArrayList<Object>();
        String sql = "select pp.ppid,cp.companyname,co.organizationname,hs.staffname, pp.packagingdate,gm.goodscoding,pp.goodsname,"
                + " gm.variableID,gm.acquiescestandard,pp.quantity,pp.weight,pp.manualurl,pp.propagandaurl,pp.fileurl,gm.goodsid,gm.typeid, pp.price,pp.money,pp.showweixin ,pp.shangjiastatus,pp.pptype,pp.fiveClear"
                +
                // 在goodsid 之后, p.category, p.price,p.money,
                " from dt_productpackaging pp"
                + " left join dtcompany cp on cp.companyid=pp.companyid"
                + " left join dtcorganization co on co.organizationid=pp.organizationid"
                + " left join dt_hr_staff hs on hs.staffid=pp.staffid"
                + " left join dtgoodsmanage gm on gm.goodsid=pp.goodsid ";
        // +" left join dt_productPriceCategory p on p.ppid=pp.ppid ";
        // pp.ppcestuer = 2  判断此产品是否合格
        //未经过测试的 产品就是为合格
        sql += " where pp.companyid = ? and pp.ppcestuer = 2 ";

        parms.add(account.getCompanyID());

        //ty 人事，办公室，财务，教务处，营销 查询
        sql += " and pp.fiveClear = ?";
        parms.add(fiveClear);
        if (typeString != null) {

            if (typeString.equals("0")) {
                sql += " and pp.showweixin = 1 ";
            }
        }

        // sql+=" and pp.organizationid=?";
        // parms.add(organizationID);
        if (search != null && search.equals("search")) {
            productDesign = (ProductPackaging) session.get("tablesearch");
            if (productDesign.getGoodsName() != null
                    && !productDesign.getGoodsName().equals("")) {
                sql += " and pp.goodsname like ?";
                parms.add("%" + productDesign.getGoodsName() + "%");
            }
            if (sdate != null && edate != null && !("").equals(sdate)
                    && !("").equals(edate)) {
                sql += " and pp.packagingdate between ? and ? ";

                parms.add(Utilities.getDateFromString(sdate + " 00:00:00",
                        "yyyy-MM-dd hh:mm:ss"));

                parms.add(Utilities.getDateFromString(edate + " 23:59:59",
                        "yyyy-MM-dd hh:mm:ss"));
            }
        }

        // sql+=" and p.category='零售价'";
        sql += " and pp.parentId is null ";
//		if (fiveClear != null && !fiveClear.equals("")) {
//			sql += " and pp.fiveClear=? ";
//			parms.add(fiveClear);
//		}
        sql += " order by pp.packagingdate desc";

        result.add(sql);
        result.add(parms.toArray());
        //System.out.println(sql);
        return result;
    }

    public String getProductdesign() {

        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return "not_login";
        }
        HttpServletRequest request = ServletActionContext.getRequest();
        String typeString = request.getParameter("pptype");
        ActionContext.getContext().put("typeString", typeString);
        List<Object> list = getProductList();
        session.put("sqllist", list);
        String sql = (String) list.get(0);
        Object[] parms = (Object[]) list.get(1);
        codeList = codeService.getCCodeListByPID(account.getCompanyID(),
                "scode20101014v5zed7cukk0000000002");
        // 单价管理
        priceManageList = codeService.getCCodeListByPID(account.getCompanyID(),
                "scode20101101dfs3uhdprp0000000032");
        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageForm ? pageForm.getPageNumber() : 1),
                (pageNumber == 0 ? 15 : pageNumber), sql, "select count(1) "
                        + sql.substring(sql.indexOf("from")), parms);
        return "productByFive";
    }

    /************************************************佣金设置*************************************************/
    /**
     * 根据产品parentId获取子产品，也就是子项目
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getProjectByParent() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        try {
			/*String sql = "select t.organizationID,t.organizationName,organizationpid" +
					" from dtCOrganization t connect by nocycle prior" +
					" t.organizationID = t.organizationpid" +
					" start with t.companyId = ?" +
					" and t.organizationid =" +
					" (select t.organizationID from dtCOrganization t " +
					" where t.organizationurl = ? and t.companyId = ?" +
					" and t.organizationpid =? and t.Status = ?)" +
					" and t.Status = ?";
			List<BaseBean> productlist = baseBeanService
					.getListBeanBySqlAndParams(sql,
							new Object[] {account.getCompanyID(),"/ea/office/ea_toIncomeDepartment",
							account.getCompanyID(),account.getCompanyID(),"00","00"});*/
            String sql = "select c.companyID,c.companyName,c.companyPID" +
                    " from dtcompany c connect by nocycle prior" +
                    " c.companyid=c.companypid" +
                    " start with c.companyId = ?" +
                    " and c.companyStatus=? and c.companyType=?";
            List<Object> productlist = baseBeanService
                    .getListBeanBySqlAndParams(sql,
                            new Object[]{account.getCompanyID(), "00", "00"});//"/ea/office/ea_toIncomeDepartment",
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("productlist", productlist);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 根据项目分类查询项目列表pageForm
     */
    public String getProjectList() {
        try {
            Map<String, Object> session = ActionContext.getContext()
                    .getSession();
            HttpServletRequest request = ServletActionContext.getRequest();
            String yj = request.getParameter("yj");
            DetachedCriteria dc = DetachedCriteria
                    .forClass(ProductPackaging.class);
            if (yj != null && yj.equals("01")) {
                dc.add(Restrictions.or(Restrictions.eq("yjstatus", "00"), Restrictions.isNull("yjstatus")));
            }
            if (xmtype != null && !xmtype.equals("")) {
                dc.add(Restrictions.or(Restrictions.eq("xmtype", xmtype),
                        Restrictions.eq("xmtype", xmtype)));
            }

            if (parameter == null) {
                parameter = "";
            }
            dc.add(Restrictions
                    .like("goodsName", parameter, MatchMode.ANYWHERE));
            dc.add(Restrictions.eq("companyID",
                    (String) session.get("companyID")));
		/*	dc.add(Restrictions.isNull("parentId"));*/
            dc.add(Restrictions.or(Restrictions.eq("productstate", "01"),
                    Restrictions.eq("productstate", "05")));
            // dc.addOrder(Order.asc("projectCode"));

            pageForm = baseBeanService.getPageFormByDC(
                    (null != pageForm ? pageForm.getPageNumber() : 1),
                    (pageNumber == 0 ? 20 : pageNumber), dc);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageForm", pageForm);
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class,
                    new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
            JSONObject obj = JSONObject.fromObject(map, jsonConfig);
            this.result = obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";
    }

    public String getSupermarketList() {
        try {
            Map<String, Object> session = ActionContext.getContext()
                    .getSession();
            HttpServletRequest request = ServletActionContext.getRequest();
            String yj = request.getParameter("yj");
            DetachedCriteria dc = DetachedCriteria
                    .forClass(ProductPackaging.class);
			/*if(yj!=null&&yj.equals("01")){
				dc.add(Restrictions.or(Restrictions.eq("yjstatus", "00"), Restrictions.isNull("yjstatus")));
			}*/
            if (xmtype != null && !xmtype.equals("")) {
                dc.add(Restrictions.or(Restrictions.eq("xmtype", xmtype),
                        Restrictions.eq("xmtype", xmtype)));
            }

            if (parameter == null) {
                parameter = "";
            }
            if (chaoshi != null && chaoshi.equals("platform")) {
                dc.add(Restrictions.eq("platform", "1"));
            }
            if (parameter != "") {
                dc.add(Restrictions
                        .like("goodsName", parameter, MatchMode.ANYWHERE));
            }
            dc.add(Restrictions.eq("companyID",
                    (String) session.get("companyID")));
		/*	dc.add(Restrictions.isNull("parentId"));*/
			/*dc.add(Restrictions.or(Restrictions.eq("productstate", "01"),
					Restrictions.eq("productstate", "05")));*/
            // dc.addOrder(Order.asc("projectCode"));
            //System.out.print(dc.toString());
            pageForm = baseBeanService.getPageFormByDC(
                    (null != pageForm ? pageForm.getPageNumber() : 1),
                    (pageNumber == 0 ? 20 : pageNumber), dc);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageForm", pageForm);
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.registerJsonValueProcessor(Date.class,
                    new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
            JSONObject obj = JSONObject.fromObject(map, jsonConfig);
            this.result = obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";
    }

    //查询该产品出厂价和零售价
    public String ajaxgetprice() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nologin", 1);
            JSONObject obj = JSONObject.fromObject(map);
            result = obj.toString();
            return "success";
        }
        HttpServletRequest request = ServletActionContext.getRequest();
        String pid = request.getParameter("pid");
        Object o = baseBeanService.getObjectBySqlAndParams(
                "select p.price,sp.RE_PRICE from dt_ProductPackaging p left join DT_PRO_SETUP sp on p.ppid=sp.ppid" +
                        " where p.companyid=? and p.ppid=?", new Object[]{account.getCompanyID(), pid});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ps", o);
        JSONObject obj = JSONObject.fromObject(map);
        this.result = obj.toString();
        return "success";
    }


    /**
     * 产品发布以及下架
     *
     * @return
     */
    public String productOffLine() {
        String[] ppids = productDesign.getPpID().split(",");
        Map<String, Object> session = ActionContext.getContext().getSession();
        String allowId = (String) session.get("allowId");
        if (!"".equals(allowId) && "01".equals(productDesign.getShowweixin()) && allowId != null) {
            ppids = allowId.split(",");
        }
        String hql = "update ProductPackaging set showweixin = ?,pptype = ? where ppID = ?";

        for (String p : ppids) {
            if (p != null && !"".equals(p)) {
                List<Object[]> objlist = new ArrayList<Object[]>();
                objlist.add(new Object[]{productDesign.getShowweixin(), productDesign.getShowweixin(), p});
                baseBeanService.executeHqlsByParamsList(null, new String[]{hql}, objlist);
            } else {
                break;
            }
        }
        return "success";
    }

    public String getActivityid() {
        return activityid;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

    public ProductPackaging getProductDesign() {
        return productDesign;
    }

    public void setProductDesign(ProductPackaging productDesign) {
        this.productDesign = productDesign;
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

    public List<BaseBean> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<BaseBean> staffList) {
        this.staffList = staffList;
    }

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
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

    public List<CCode> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<CCode> codeList) {
        this.codeList = codeList;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    public List<BaseBean> getPrintList() {
        return printList;
    }

    public void setPrintList(List<BaseBean> printList) {
        this.printList = printList;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getPrintname() {
        return printname;
    }

    public void setPrintname(String printname) {
        this.printname = printname;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public GoodsManage getGoodsManage() {
        return goodsManage;
    }

    public void setGoodsManage(GoodsManage goodsManage) {
        this.goodsManage = goodsManage;
    }

    public CustomersForms getCustomersForms() {
        return customersForms;
    }

    public void setCustomersForms(CustomersForms customersForms) {
        this.customersForms = customersForms;
    }

    public String getProduce() {
        return produce;
    }

    public void setProduce(String produce) {
        this.produce = produce;
    }

    public List<CCode> getPriceManageList() {
        return priceManageList;
    }

    public void setPriceManageList(List<CCode> priceManageList) {
        this.priceManageList = priceManageList;
    }

    public ProductPriceCategory getProductPriceCategory() {
        return productPriceCategory;
    }

    public void setProductPriceCategory(
            ProductPriceCategory productPriceCategory) {
        this.productPriceCategory = productPriceCategory;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public List<BaseBean> getProductPackagingList() {
        return productPackagingList;
    }

    public void setProductPackagingList(List<BaseBean> productPackagingList) {
        this.productPackagingList = productPackagingList;
    }

    public List<BaseBean> getProductPriceCategoryList() {
        return productPriceCategoryList;
    }

    public void setProductPriceCategoryList(
            List<BaseBean> productPriceCategoryList) {
        this.productPriceCategoryList = productPriceCategoryList;
    }

    public ProductPackaging getProductpeijian() {
        return productpeijian;
    }

    public void setProductpeijian(ProductPackaging productpeijian) {
        this.productpeijian = productpeijian;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public PageForm getPageFormpeijian() {
        return pageFormpeijian;
    }

    public void setPageFormpeijian(PageForm pageFormpeijian) {
        this.pageFormpeijian = pageFormpeijian;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String[] getPrdCategory() {
        return prdCategory;
    }

    public void setPrdCategory(String[] prdCategory) {
        this.prdCategory = prdCategory;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Map<String, ProductPackaging> getProductPackagingMap() {
        return productPackagingMap;
    }

    public void setProductPackagingMap(
            Map<String, ProductPackaging> productPackagingMap) {
        this.productPackagingMap = productPackagingMap;
    }

    public Map<String, ProductPriceCategory> getProductPriceCategoryMap() {
        return productPriceCategoryMap;
    }

    public void setProductPriceCategoryMap(
            Map<String, ProductPriceCategory> productPriceCategoryMap) {
        this.productPriceCategoryMap = productPriceCategoryMap;
    }

    public CCode getCcode() {
        return ccode;
    }

    public void setCcode(CCode ccode) {
        this.ccode = ccode;
    }

    public String getFlexbutton() {
        return flexbutton;
    }

    public void setFlexbutton(String flexbutton) {
        this.flexbutton = flexbutton;
    }

    public Profitshare getProfitshare() {
        return profitshare;
    }

    public void setProfitshare(Profitshare profitshare) {
        this.profitshare = profitshare;
    }

    public List<CCode> getProType() {
        return proType;
    }

    public void setProType(List<CCode> proType) {
        this.proType = proType;
    }

    public String getFiveClear() {
        return fiveClear;
    }

    public void setFiveClear(String fiveClear) {
        this.fiveClear = fiveClear;
    }

    public productPackajiage getProductPackajiage() {
        return productPackajiage;
    }

    public void setProductPackajiage(productPackajiage productPackajiage) {
        this.productPackajiage = productPackajiage;
    }

    public BaseBean getBaseBean() {
        return baseBean;
    }

    public void setBaseBean(BaseBean baseBean) {
        this.baseBean = baseBean;
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public productPackajiage getBeanjage() {
        return beanjage;
    }

    public void setBeanjage(productPackajiage beanjage) {
        this.beanjage = beanjage;
    }

    public Map<String, ProSetup> getStr() {
        return str;
    }

    public void setStr(Map<String, ProSetup> str) {
        this.str = str;
    }

    public String getDevide() {
        return devide;
    }

    public void setDevide(String devide) {
        this.devide = devide;
    }

    public String getXmtype() {
        return xmtype;
    }

    public void setXmtype(String xmtype) {
        this.xmtype = xmtype;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getChaoshi() {
        return chaoshi;
    }

    public void setChaoshi(String chaoshi) {
        this.chaoshi = chaoshi;
    }

    public String[] getPpIDList() {
        return ppIDList;
    }

    public void setPpIDList(String[] ppIDList) {
        this.ppIDList = ppIDList;
    }

    public String getButt() {
        return butt;
    }

    public void setButt(String butt) {
        this.butt = butt;
    }

    public String getTodayPrice() {
        return todayPrice;
    }

    public void setTodayPrice(String todayPrice) {
        this.todayPrice = todayPrice;
    }

    public String getGn() {
        return gn;
    }

    public void setGn(String gn) {
        this.gn = gn;
    }

    public String getShowweixin() {
        return showweixin;
    }

    public void setShowweixin(String showweixin) {
        this.showweixin = showweixin;
    }
}
