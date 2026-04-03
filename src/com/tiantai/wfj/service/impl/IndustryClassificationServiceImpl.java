package com.tiantai.wfj.service.impl;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.IndustryClassificationService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.Company;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.ConsultingRegistration;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.BenDis.Rewarddetail;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.Constant;
import hy.ea.util.ImageCut;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class IndustryClassificationServiceImpl implements IndustryClassificationService {
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private UploadContentToFileService contentToFileService;
    @Resource
    private ServerService serverService;
    private List<BaseBean> listBaseBean;
    private List<Object> cclist;//新闻页面的二维码信息
    private List<Object> list;
    private List<BaseBean> productList;
    private List<BaseBean> beans;

    /**
     * 产品
     *
     * @param ccompanyId 往来单位id
     * @param pageNumber 当前页
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public PageForm getAjaxPorductsList(String ccompanyId, Integer pageNumber, String search, String ppId) {
        Company company = (Company) beandao.getBeanByHqlAndParams("select c from Company c ,CcomCom t where c.companyID=t.comanyId and t.ccompanyId=?", new Object[]{ccompanyId});
        StringBuilder sql = new StringBuilder();
        PageForm pageForm = new PageForm();
        //获取当前时间
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());
        List<Object> parms = new ArrayList<Object>();
        if (company != null) {
            sql.append("select ps.ppname,ps.ppid,ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2) re_price,p.image,p.goodsid,p.companyid,p.type,p.stockSize,p.monthSales,");
            sql.append("ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2) actPrice,ROUND(pv.vip*(1+nvl(sz.total_pct,0)/100),2) vip,pa.type actType,p.brand,p.remark ");
            sql.append(",cm.companyAddr,cc.ccompany_Id,p.categoryName,p.categoryId,pv.vipid,pap.actpriceid ");
            sql.append("from DT_PRO_SETUP ps ");
            sql.append("inner join dt_ProductPackaging p on p.ppid=ps.ppid ");
            sql.append("left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
            sql.append("left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid ");
            sql.append("left join dt_set_subsidize sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");
            sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state ='00' ");
            sql.append("left join dt_pro_vip pv on pv.ppid = p.ppid and pv.state ='00' ");
            sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid ");
            sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
            sql.append("and pa.state <> '04' and pa.state <> '03' and pa.state <> '02' ");
            sql.append("where ps.com_id=?  and p.showweixin = ? and  p.type!='扫码收款' and ps.state ='00'");
            parms.add(currentTime);
            parms.add(currentTime);
            parms.add(company.getCompanyID());
            parms.add("01");
            if (company.getCompanyID() != null && company.getCompanyID().equals("company201009046vxdyzy4wg0000000025")) {
                sql.append(" and p.model > all(?,?,?)");
                parms.add("0");
                parms.add("0.5");
                parms.add("1");
            }

            if (ppId != null && ppId.length() > 0) {
                sql.append(" and p.ppid in(");
                String[] temp = ppId.split(",");
                for (int i = 0; i < temp.length; i++) {
                    if (i == temp.length - 1) {
                        sql.append("?");
                    } else {
                        sql.append("?,");
                    }
                    parms.add(temp[i]);
                }
                sql.append(")");
            }


            if (search != null) {
                if (search.equals("smart")) {
                    //根据销量排序
                    sql.append(" order by cast(p.monthsales as int) desc");
                } else if (search.equals("praise")) {
                    //根据好评排序
                    sql.delete(0, sql.length());
                    parms.clear();
                    sql.append("select ps.ppname,ps.ppid,ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2) re_price,p.image,p.goodsid,p.companyid,p.type,p.stockSize,p.monthSales,count(ps.ppid),");
                    sql.append("ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2) actPrice,ROUND(pv.vip*(1+nvl(sz.total_pct,0)/100),2) vip ,pa.type actType,p.brand,p.remark ");
                    sql.append(",cm.companyAddr,cc.ccompany_Id,p.categoryName,p.categoryId,pv.vipid,pap.actpriceid ");
                    sql.append("from DT_PRO_SETUP ps ");
                    sql.append("inner join dt_ProductPackaging p on p.ppid=ps.ppid ");
                    sql.append("inner join DT_ccom_com ccom on p.companyid=ccom.compnay_id ");
                    sql.append("inner join dtContactCompany cc on ccom.ccompany_Id=cc.ccompanyID ");
                    sql.append("inner join DTComments cm on ps.ppid = cm.ppID ");
                    sql.append("left join dt_set_subsidize sz on sz.gtid = cc.industrytype and sz.stutas = '01' ");
                    sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state ='00' ");
                    sql.append("left join dt_pro_vip pv on pv.ppid = p.ppid and pv.state ='00' ");
                    sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid ");
                    sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
                    sql.append("and pa.state <> '04' and pa.state <> '03' and pa.state <> '02' ");
                    sql.append("where p.showweixin = ? and p.companyid=? and cm.EVALUATION = ? and  p.type!='扫码收款' and ps.state ='00'  ");
                    sql.append("group by ps.ppname,ps.ppid,ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2),p.image,p.goodsid,p.companyid,p.type,p.stockSize,p.monthSales , ");
                    sql.append("ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2),ROUND(pv.vip*(1+nvl(sz.total_pct,0)/100),2),pa.type ");
                    parms.add(currentTime);
                    parms.add(currentTime);
                    parms.add("01");
                    parms.add(company.getCompanyID());
                    parms.add("好评");
                    if (ppId != null && ppId.length() > 0) {
                        sql.append(" and p.ppid in(");
                        String[] temp = ppId.split(",");
                        for (int i = 0; i < temp.length; i++) {
                            if (i == temp.length - 1) {
                                sql.append("?");
                            } else {
                                sql.append("?,");
                            }
                            parms.add(temp[i]);
                        }
                        sql.append(")");
                    }
                    sql.append(" order by count(ps.ppid) desc");
                } else if (search.equals("newest")) {
                    //根据时间排序
                    sql.append(" order by p.createTime desc");
                } else if (search.equals("pop")) {
                    //根据销量排序
                    sql.append(" order by cast(p.monthsales as int) desc");
                } else if (search.equals("plow")) {
                    //根据价格从低向高排序
                    sql.append(" order by cast(ps.re_price as number)");
                } else if (search.equals("ptop")) {
                    //根据价格从高相低排序
                    sql.append(" order by cast(ps.re_price as number) desc");
                } else {
                    sql.append(" and ps.ppname like ? ");
                    parms.add("%" + search + "%");
                }
            }
            pageForm = getPageFormBySQL(pageNumber, 10, sql.toString(),
                    "select count(*) from (" + sql + ")", parms.toArray());
        }
        return pageForm;
    }

    /**
     * 公司网站的公司招聘
     *
     * @param ccompanyId 往来单位id
     * @param pageNumber 当前页
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public PageForm getRecruitIndex(String ccompanyId, Integer pageNumber) {
        StringBuilder sql = new StringBuilder();
        PageForm pageForm = new PageForm();
        sql.append("select c.logoPath,p.jobTitle,p.companyName,p.workCity,p.education,p.riId");
        sql.append(" from dtRecruitInfo p,dtContactCompany c where c.ccompanyID = p.ccompanyID and p.status = ? and c.ccompanyID=?");
        pageForm = getPageFormBySQL(pageNumber, 8, sql.toString(),
                "select count(*) from (" + sql + ")", new Object[]{"01", ccompanyId});
        return pageForm;
    }

    /**
     * 查询公司产品分类
     *
     * @param companyId
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<BaseBean> getProductCategories(String companyId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select pp.ppid,pp.goodsname,pp.companyid,pp.yjstatus,pp.parentid,pp.parentname,pp.image");
        sql.append(" from dt_productpackaging pp");
        sql.append(" connect by nocycle prior pp.parentid = pp.ppid");
        sql.append(" start with pp.parentname=?");
        sql.append(" and pp.companyid=?");
        List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql.toString(), new Object[]{"产品分类", companyId});
        return list;
    }

    /**
     * 根据ppid产品分类搜索
     *
     * @param ppid
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<BaseBean> searchCategories(String ppid) {
        StringBuilder sql = new StringBuilder();
        sql.append("select pp.ppid,pp.goodsname,pp.companyid,pp.yjstatus,pp.parentid,pp.parentname,pp.image");
        sql.append(" from dt_productpackaging pp");
        sql.append(" connect by nocycle prior pp.ppid = pp.parentid");
        sql.append(" start with pp.ppid=?");
        List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql.toString(), new Object[]{ppid});
        return list;
    }

    /**
     * 分页
     *
     * @param pageNumber 当前页
     * @param pageSize   显示条数
     * @param sql        sql语句
     * @param sqlcount   总记录数SQL语句
     * @param params     参数
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm getPageFormBySQL(int pageNumber, int pageSize, String sql,
                                     String sqlcount, Object[] params) {
        int count = beandao.getConutByBySqlAndParams(sqlcount, params);// 总条数
        if (count == 0)
            return null;
        int pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
        if (pageNumber < 1)
            pageNumber = 1;
        if (pageNumber > pageCount)
            pageNumber = pageCount;
        int firstResult = pageSize * (pageNumber - 1);
        int maxResult = Math.min(pageSize, count - firstResult);
        List<BaseBean> listBaseBean = beandao
                .getConutByBySqlAndParamsAndPage(sql, params, firstResult,
                        maxResult);
        PageForm pageForm = new PageForm();
        pageForm.setPageSize(pageSize);
        pageForm.setRecordCount(count);
        pageForm.setPageCount(pageCount);
        pageForm.setPageNumber(pageNumber);
        pageForm.setList(listBaseBean);
        return pageForm;
    }
    /*-----xgb-----*/

    /**
     * 根据txt URL 获取内容
     *
     * @param filepath
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    private String getContentFromFile(String filepath) {
        String path = ServletActionContext.getServletContext()
                .getRealPath("\\") + filepath;
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        try {
            return contentToFileService.getContent(path);

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    // 公司信息,联营,新闻,招商,招聘

    /**
     * @param ccompanyId :往来单位id
     * @param string     标识
     * @return 返回的数据
     * @Title: 查询
     * @Description: 查询公司资讯/简介信息
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Object> NewsList(String ccompanyId, String string) {
        StringBuilder sb1 = new StringBuilder();
        sb1.append("select p.goodsName,to_char(p.packagingDate,'YYYY-MM-DD HH24:MI:SS'),p.image,p.goodsID,p.model,t.companyName,p.ppID,p.staffName ");
        sb1.append("from ProductPackaging p,CcomCom c,ContactCompany t ");
        sb1.append("where p.fiveClear = ? and p.companyID=? and p.type=? and p.delStatus = ? ");
        sb1.append("and p.companyID=c.comanyId and c.ccompanyId=t.ccompanyID ");
        sb1.append("order by p.packagingDate desc");
        CcomCom ccomcom = (CcomCom) beandao.getBeanByHqlAndParams(
                "from CcomCom where ccompanyId = ?",
                new Object[]{ccompanyId});
        Company company = (Company) beandao.getBeanByHqlAndParams(
                "from Company where companyID = ?",
                new Object[]{ccomcom.getComanyId()});
        if (company != null) {
            listBaseBean = beandao.getListBeanByHqlAndParams(
                    sb1.toString(), new Object[]{"2", company.getCompanyID(),
                            string, "00"});
            list = new ArrayList<Object>();
            for (int i = 0; i < listBaseBean.size(); i++) {
                Object obj = listBaseBean.get(i);
                Object[] oo = (Object[]) obj;
                String sql = "select g.url from (select * from dtgoodfunction d where d.goodsid = ? order by d.gfkey) g where rownum=1";
                obj = beandao.getObjectBySqlAndParams(sql, new Object[]{oo[3]});
                list.add(
                        i,
                        new String[]{((oo[0] == null) ? "" : oo[0]).toString(), ((oo[1] == null) ? "" : oo[1]).toString(),
                                ((oo[2] == null) ? "" : oo[2]).toString(), ((oo[3] == null) ? "" : oo[3]).toString(),
                                ((oo[4] == null) ? "" : oo[4]).toString(),
                                obj == null ? "" : getContentFromFile(obj.toString()),
                                ((oo[5] == null) ? "" : oo[5]).toString(), ((oo[6] == null) ? "" : oo[6]).toString(), ((oo[7] == null) ? "" : oo[7]).toString()});
            }
        }
        return list;
    }

    /**
     * @param
     * @return 返回的数据
     * @Title: 查询
     * @Description: 查询用户信息
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public TEshopCustomer inquireUser() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                SessionWrap.KEY_CUSTOMER);
        return cus;
    }

    /**
     * @param hql :查询的hql,objects:查询条件
     * @return 返回的数据
     * @Title: 查询
     * @Description: 查询数据
     */
    @SuppressWarnings("rawtypes")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List transition(String hql, Object[] objects) {
        return beandao.getListBeanBySqlAndParams(hql, objects);
    }

    /**
     * @param ccompanyId :往来单位id
     * @return 返回的数据
     * @Title: 查询
     * @Description: 查询公司招商信息
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> getpk(String ccompanyId) {
        // 公司
        String hql = "from TEshopCusCom t where t.state = ? and t.companyId = (select c.comanyId from CcomCom c where c.ccompanyId = ?)";
        TEshopCusCom tcc = (TEshopCusCom) beandao.getBeanByHqlAndParams(hql,
                new Object[]{"2", ccompanyId});
        String cusType = "";
        String lowType = "";
        // 查找下级
        if (tcc != null) {
            cusType = tcc.getCusType();// 网站的
            hql = "select t.cusType from T_ESHOP_CUSCOM t connect by nocycle prior t.account = t.Superioragent start with t.account = ? order by t.cusType desc";
            @SuppressWarnings("unchecked")
            List<Object> list = transition(hql,
                    new Object[]{tcc.getAccount()});
            hql = null;
            lowType = list.get(0).toString();
            if (lowType.equals("0.5")) {

                lowType = "1";
            } else if (lowType.equals("0")) {
                lowType = "0.5";

            } else {
                lowType = Integer.parseInt(lowType) + 1 + "";
            }

        }

        StringBuilder sql = new StringBuilder();
        StringBuilder sqlgs = new StringBuilder();
        sql.append("select ps.ppname,ps.ppid,ps.re_price,p.image,p.model,p.goodsid,p.companyid ");
        sql.append("from DT_PRO_SETUP ps,dt_ProductPackaging p where ps.com_id= ? ");
        sql.append(" and p.ppid=ps.ppid and p.showweixin =? and p.type= ? and  ps.fcom_id is null ");

        sqlgs.append(sql);
        sqlgs.append(" and model != ?  order by sorting");
        sql.append(" and ((");
        if (cusType.equals("0")) {
            sql.append("p.model>=?");
            String tempsql = "select t.cusType from T_ESHOP_CUSCOM t where (t.cusType = ? or t.cusType = ?) connect by nocycle prior t.account = t.Superioragent start with t.account = ?";
            List<BaseBean> lists = beandao.getListBeanBySqlAndParams(tempsql,
                    new Object[]{"0.5", "1", tcc.getAccount()});

            if (lists.contains("0.5")) {
                sql.append(" and p.model!='0.5'");
            }
            if (lists.contains("1")) {
                sql.append(" and p.model!='1'");
            }
        } else {
            sql.append(" p.model>?");
        }
        // 查询个人会员列表
        beans = beandao.getListBeanBySqlAndParams(
                sql.append(") or model = '8') order by sorting").toString(),
                new String[]{"company201009046vxdyzy4wg0000000025", "01", "个人会员",
                        cusType});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("beans", beans);

        return map;
    }

    /**
     * @param ccompanyId :往来单位id
     * @return 返回的数据
     * @Title: 查询
     * @Description: 查询公司招聘信息
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BaseBean> attractEngage(String ccompanyId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select r.jobTitle,to_char(r.publishdate,'YYYY-MM-DD') ,r.companyname,");
        sb.append("r.salary,r.workcity,r.education,r.rikey,r.workPlace,r.riid ");
        sb.append("from dtRecruitInfo r ");
        sb.append("where r.status = ? and r.ccompanyid = ? order by  r.publishDate desc");
        listBaseBean = beandao.getListBeanBySqlAndParams(sb.toString(), new Object[]{"01", ccompanyId});
        return listBaseBean;
    }

    /**
     * @param ccompanyId :往来单位id
     * @return 返回的数据
     * @Title: 查询
     * @Description: 查询公司详细信息
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> companyMessage(String ccompanyId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select y.companyName,y.companyAddr,c.comanyId ");
        sb.append("from ContactCompany y,CcomCom c ");
        sb.append("where y.ccompanyID = ? and y.ccompanyID=c.ccompanyId ");
        Object object = beandao.getObjectByHqlAndParams(sb.toString(),
                new Object[]{ccompanyId});

        Map<String, Object> homepage = new HashMap<String, Object>();
        homepage.put("details", object);
        //新闻底部增加公司名片的二维码
        homepage.put("foundCode", foundCode(ccompanyId));
        return homepage;
    }

    /**
     * @param ccompanyId :往来单位id
     * @return 返回的数据
     * @Title: 查询
     * @Description: 查询公司二维码
     */
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Object foundCode(String ccompanyId) {
        HttpServletRequest request = ServletActionContext.getRequest();
        String basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";
        if (ccompanyId == null || ccompanyId.equals("")) {
            ccompanyId = "contactCompany20111102YFXHT4NASR0000011516";
        }
        cclist = transition("select ccompanyID,companyName,logoPath,qrCodePath,companyWeb from dtContactCompany where ccompanyID=?", new Object[]{ccompanyId});
        Object obj = null;
        if (cclist != null && cclist.size() > 0) {
            obj = cclist.get(0);
            Object[] objs = (Object[]) obj;
            String httpUrl = Constant.HTTP + "text=" + objs[4];
            String httpArg = "&logo=" + basePath + objs[2] + "&key=" + Constant.API_KEY;
            objs[3] = httpUrl + httpArg;
        }
        return obj;
    }

    /**
     * @param ccompanyId :往来单位id
     * @return 返回的数据
     * @Title: 查询
     * @Description: 查询公司简介
     */
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Object> abstruct(String ccompanyId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select g.url,g.gfid,p.ppid ");
        sb.append("from dt_ProductPackaging p,dtGoodFunction g ");
        sb.append("where p.goodsid=g.goodsid and p.type= ? and p.delStatus = ? ");
        sb.append("and p.companyid in (");
        sb.append("select y.companyid ");
        sb.append("from dtCompany y where y.companyid in (");
        sb.append("select d.compnay_id from DT_ccom_com d ");
        sb.append("where d.ccompany_id= ?)) order by p.packagingdate desc");
        List<Object> parms = new ArrayList<Object>();
        parms.add("公司简介");
        parms.add("00");
        parms.add(ccompanyId);
        productList = transition(sb.toString(), parms.toArray());
        list = new ArrayList<Object>();
        for (int i = 0; i < productList.size(); i++) {
            Object obj = productList.get(i);
            Object[] oo = (Object[]) obj;
            list.add(i, new String[]{getContentFromFile(oo[0].toString()), (String) oo[2]});
        }
        return list;
    }

    /**
     * 查看更多简介和文化
     *
     * @param companyId 公司id
     * @param flag      标识
     */

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Map<Integer, String[]> summaryOrCulture(String companyId, String flag) {
        Map<Integer, String[]> map = new HashMap<Integer, String[]>();
        StringBuilder sql = new StringBuilder();
        List<Object> parms = new ArrayList<Object>();
        sql.append("select g.url");
        sql.append(" from dt_ProductPackaging pp,dtGoodFunction g where pp.companyid=?");
        sql.append(" and pp.goodsID=g.goodsid and pp.type=? and pp.delStatus=? order by g.orders");
        parms.add(companyId);
        if (flag.equals("summary")) {
            parms.add("公司简介");
        } else if (flag.equals("culture")) {
            parms.add("公司文化");
        }
        parms.add("00");

        List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql.toString(), parms.toArray());
        map = new HashMap<Integer, String[]>();
        //获取每个功能信息的具体内容
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                Object obj = list.get(i);
                map.put(i, new String[]{"", getContentFromFile(obj == null ? "" : obj.toString())});
            }
        }
        return map;
    }


    /**
     * 查询公司咨询详情
     *
     * @param ppId:产品id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<Integer, String[]> informationDetails(String ppId) {
        StringBuilder sb1 = new StringBuilder();
        sb1.append("select g.url,g.name,to_char(p.packagingDate,'YYYY-MM-DD HH24:MI:SS') from ");
        sb1.append("dt_ProductPackaging p join dtGoodFunction g ");
        sb1.append("on p.goodsID=g.goodsid where p.ppID = ?");
        productList = beandao.getListBeanBySqlAndParams(sb1.toString(), new Object[]{ppId});
        Map<Integer, String[]> maplist1 = new HashMap<Integer, String[]>();
        //获取每个功能信息的具体内容
        if (productList != null && productList.size() != 0) {
            for (int i = 0; i < productList.size(); i++) {
                Object obj = productList.get(i);
                Object[] oo = (Object[]) obj;
                maplist1.put(i, new String[]{getContentFromFile(oo[0] == null ? "" : oo[0].toString()), oo[1] == null ? "" : oo[1].toString(), oo[2] == null ? "" : oo[2].toString()});
            }
        }

        return maplist1;
    }

    /**
     * 广告 ljc
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BaseBean> toADList(String type, String companyId) {
        StringBuffer sql = new StringBuffer();
        sql.append("select p.goodsname,p.goodsid,p.ppid,p.image,p.companyid from dt_productpackaging p where p.type =? and p.companyid =?");
        List<BaseBean> list = new ArrayList<BaseBean>();
        list = beandao.getListBeanBySqlAndParams(sql.toString(), new Object[]{type, companyId});
        return list;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Object content(String ppId) {
        ProductPackaging pp = (ProductPackaging) beandao.getBeanByHqlAndParams("from ProductPackaging where ppID = ? ", new Object[]{ppId});
        List<Object> olist = new ArrayList<Object>();
        olist.add(pp.getGoodsName());
        olist.add(pp.getPackagingDate());
        olist.add(pp.getImage());
        olist.add(pp.getModel());
        olist.add(pp.getTradeName());
        //如果产品没有sccid,则添加发布人账号
        Object staffid;
        if (pp.getSccid() == null || pp.getSccid().equals("")) {
            String sql = "select qrcodepath,sccid,staffid from t_eshop_cuscom where staffid=? order by  decode(custype,'1',1,'2',2,'3',3,'4',4,'5',5,'6',6,'7',7,'0',8) asc ";
            List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql, new Object[]{pp.getStaffID()});
            //如果发布人没有账号则添加公司账号的sccid
            if (list == null || list.size() == 0) {
                list = beandao.getListBeanBySqlAndParams("select qrcodepath,sccid,staffid from t_eshop_cuscom where companyId=? order by  decode(custype,'1',1,'2',2,'3',3,'4',4,'5',5,'6',6,'7',7,'0',8) asc ", new Object[]{pp.getCompanyID()});
            }
            Object obj = list.get(0);
            Object[] ob = (Object[]) obj;
            olist.add(ob[0]);
            olist.add(ob[1]);
            staffid = ob[2];
        } else {
            TEshopCusCom cc = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{pp.getSccid()});
            if (cc != null) {
                olist.add(cc.getQrcodePath());
                olist.add(cc.getSccId());
                staffid = cc.getStaffid();
            } else {
                String sql = "select qrcodepath,sccid,staffid from t_eshop_cuscom where staffid=? order by  decode(custype,'1',1,'2',2,'3',3,'4',4,'5',5,'6',6,'7',7,'0',8) asc ";
                List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql, new Object[]{pp.getStaffID()});
                //如果发布人没有账号则添加公司账号的sccid
                if (list == null || list.size() == 0) {
                    list = beandao.getListBeanBySqlAndParams("select qrcodepath,sccid,staffid from t_eshop_cuscom where companyId=? order by  decode(custype,'1',1,'2',2,'3',3,'4',4,'5',5,'6',6,'7',7,'0',8) asc ", new Object[]{pp.getCompanyID()});
                }
                Object obj = list.get(0);
                Object[] ob = (Object[]) obj;
                olist.add(ob[0]);
                olist.add(ob[1]);
                staffid = ob[2];
            }

        }


        olist.add(pp.getGoodsID());
        Staff staff = null;
        if(pp.getCompanyID()!=null&&!pp.getCompanyID().equals("")){
            Company company = (Company) beandao.getBeanByHqlAndParams("from Company where companyID = ?",new Object[]{pp.getCompanyID()});

            olist.add(company.getCompanyName());

        }else {


             staff = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ? ", new Object[]{(pp.getStaffID() != null) ? pp.getStaffID() : staffid});

            olist.add(staff.getStaffName());
        }

        int count=beandao.getConutByBySqlAndParams("select count(*) from DTREWARDDETAIL r where r.ppid=? and r.trade_Status=?",new Object[]{ppId,"01"});
        olist.add(count);

        olist.add(staffid);
        olist.add(pp.getCategoryName());
        if(staff!=null){
            olist.add(staff.getHeadimage());
        }

        Object object1 = olist;


        return object1;

    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BaseBean> splendid(String ccompanyId, String ppId, String mini, String fiveClear) {
        StringBuilder newsql = new StringBuilder();
        newsql.append("select p.goodsName,p.goodsID,p.ppID ");
        newsql.append("from dt_productpackaging p,dt_ccom_com m ");
        newsql.append("where p.companyID = m.compnay_id and m.ccompany_id = ? ");
        newsql.append("and p.type= ? and p.ppID <> ? and p.fiveClear = ? and p.delStatus=? ");
        listBaseBean = beandao.getListBeanBySqlAndParams(newsql.toString(), new Object[]{ccompanyId, mini, ppId, fiveClear, "00"});
        return listBaseBean;

    }

    /**
     * 查询往来单位
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ContactCompany getCompany(String ccompanyId) {

        ContactCompany con = (ContactCompany) beandao.getBeanByHqlAndParams("from ContactCompany y where y.ccompanyID = ?", new Object[]{ccompanyId});

        return con;
    }

    /**
     * 查询教练团队
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Object> exhibition(String ccompanyId) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select s.staffname,s.staffid,s.headimage,s.position, t.companyid,p.goodsname");
        sb.append(" from dt_hr_staff s,dtteambuild t, dt_productpackaging p ,dt_ccom_com d ");
        sb.append(" where p.staffid = s.staffid and p.staffid = t.staffid ");
        sb.append(" and t.companyid=d.compnay_id and d.ccompany_id = ? and p.type=?");
        list = transition(sb.toString(), new Object[]{ccompanyId, "团队管理"});
        return list;
    }

    /**
     * 查询公司关联账号
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> getTcc(String ccompanyId) {
        boolean b = false;
        String sql = "select t.sccid from dt_ccom_com m,t_eshop_cuscom t where m.ccompany_id=? and m.compnay_id=t.companyid";
        Object obj = beandao.getObjectBySqlAndParams(sql, new Object[]{ccompanyId});
        String sql1 = "select t.ppid from dt_ccom_com m,t_eshop_cuscom t where m.ccompany_id=? and m.compnay_id=t.companyid";
        Object obj2 = beandao.getObjectBySqlAndParams(sql1, new Object[]{ccompanyId});
        if (obj != null) {
            b = true;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tcc", b);
        map.put("obj", obj);
        map.put("obj2", obj2);
        return map;
    }


    /**
     * 查询产品platform，按platform跳转页面
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Object getField(String ccompanyId) {
        String sql = "select i.platforreturn from dt_ccom_com c,t_eshop_cuscom e,dt_industryrelation i  where e.ppid = i.ppid and e.companyid = c.compnay_id and e.custype= ? and  c.ccompany_id = ?";
        Object obj = beandao.getObjectBySqlAndParams(sql, new Object[]{"0", ccompanyId});
        return obj;
    }

    public Object getStCompany(String ccompanyId) {
        String sql = "select c.ccompanyid ,cc.compnay_id,c.companyname,c.logopath  from dtcontactcompany c,dt_ccom_com cc  where c.industrytype like ? and cc.ccompany_id = c.ccompanyid and  c.ccompanyid = ?";
        Object obj = beandao.getObjectBySqlAndParams(sql, new Object[]{"%汽车驾校%", ccompanyId});
        return obj;
    }

    @Override
    @Transactional
    public String addConsultant(ConsultingRegistration consultingRegistration) {

        String stuts = "0";//成功添加

        if (consultingRegistration != null) {
            consultingRegistration.setCrId(serverService.getServerID("crid"));
            consultingRegistration.setConsultingDate(new Date());
            consultingRegistration.setState("00");
            beandao.save(consultingRegistration);
        } else {
            stuts = "1";//添加失败
        }
        return stuts;
    }

    @Override
    @Transactional
    public void saveCompany(ContactCompany contactCompany, TEshopCusCom shopCusCom) {
        String path = ServletActionContext.getRequest()
                .getSession().getServletContext().getRealPath("/");
        if (contactCompany.getLogoPath() != null
                && contactCompany.getLogoPath().length() > 0
                && contactCompany.getLogoPath().indexOf("base64") != -1) {
            String imagePath = Base64(contactCompany.getLogoPath(), contactCompany.getCcompanyID(), shopCusCom.getStaffid());
            String jjPath = imagePath.split("\\.")[0] + "small." + imagePath.split("\\.")[1];
            ImageCut.scale(path + imagePath, path + jjPath, 300, 331);
            contactCompany.setLogoPath(jjPath);
        }
        beandao.saveOrUpdate(contactCompany);
    }

    /**
     * base64上传图片
     *
     * @param image 图片
     * @param companyId
     * @return
     */
    public String Base64(String image, String companyId, String staffId) {


        if (image.indexOf("jpeg") != -1) {
            image = image.replace("jpeg", "jpg");
        }

        // 图片存储路径
        String photoPath = "upload_files\\corporateLogo\\"
                + companyId + "\\" + staffId;
        // 重命名
        String upName = UUID.randomUUID().toString()
                + System.currentTimeMillis() + "." + image.substring(image.indexOf("image/") + 6, image.indexOf(";base64"));

        String path = ServletActionContext.getRequest().getSession()
                .getServletContext().getRealPath("/");
        String dir = path + photoPath;
        File fileLocation = new File(dir);
        // 判断上传路径是否存在，如果不存在就创建
        if (!fileLocation.exists()) {
            boolean isCreated = fileLocation.mkdirs();
            if (!isCreated) {
                return "";
            }
        }
        // 重命名
        FileOutputStream out;
        String iconBase64 = image.substring(22);
        try {
            byte[] buffer = new BASE64Decoder().decodeBuffer(iconBase64);
            out = new FileOutputStream(dir + "/" + upName);
            out.write(buffer);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return photoPath + upName;
    }

    /**
     * 添加打赏信息
     * @return
     */
    @Override
    @Transactional
    public String saveReward(String ppid,String money,String fbsccid,String rdsccid)throws Exception{
        Rewarddetail rewarddetail=new Rewarddetail();
        ProductPackaging packaging=(ProductPackaging)beandao.getBeanByHqlAndParams("from ProductPackaging where ppid=?",new Object[]{ppid});
        rewarddetail.setRdid(serverService.getServerID("reward"));
        rewarddetail.setFbSccid(fbsccid);
        TEshopCusCom t= (TEshopCusCom)beandao.getBeanByHqlAndParams("from TEshopCusCom where sccid=?",new Object[]{fbsccid});
        rewarddetail.setFbAcount(t.getAccount());
        rewarddetail.setRdSccid(rdsccid);
        TEshopCusCom tc= (TEshopCusCom)beandao.getBeanByHqlAndParams("from TEshopCusCom where sccid=?",new Object[]{rdsccid});
        rewarddetail.setRdAcount(tc.getAccount());
        rewarddetail.setPpid(ppid);
        rewarddetail.setOrdernum(serverService.getBillID(""));
        rewarddetail.setRddate(new Date());
        rewarddetail.setRdmoney(money);
        rewarddetail.setTradeStatus("00");
        beandao.save(rewarddetail);
        return rewarddetail.getOrdernum();
    }

    /**
     * 查询打赏信息
     * @param ordernum 订单号
     * @param tradeStatus //交易类型[00初始状态未生成订单，01已生成订单交易完成，02生产订单失败]
     * @return
     */
    public BaseBean getReward(String ordernum,String tradeStatus){
        List<Object> bemars=new ArrayList<Object>();
        StringBuilder sql=new StringBuilder("from Rewarddetail r where r.ordernum=?");
        bemars.add(ordernum);
        if (tradeStatus!=null){
            sql.append(" and r.tradeStatus=?");
            bemars.add(tradeStatus);
        }
        Rewarddetail rd=(Rewarddetail)beandao.getBeanByHqlAndParams(sql.toString(),bemars.toArray());
        return rd;
    }
}