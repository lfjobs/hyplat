package com.tiantai.wfj.front;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.EarthIndexService;
import hy.ea.bo.finance.BenDis.SetSubsidize;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tiantai.wfj.bo.SearchHistory;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;

import hy.ea.bo.production.GoodFunction;
import hy.ea.service.UploadContentToFileService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
public class DigitalMallAction {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private UploadContentToFileService contentToFileService;
    @Resource
    private EarthIndexService earthIndexService;
    private String result;
    private PageForm pageForm;
    private String proName;//产品名称
    private String search;//搜索名称
    private String flag;//判断标识
    private String region;//地域搜索
    private List<BaseBean> adList;//轮播图片广告位
    private String companyId;//公司ID;
    private String goodsId;//物品id;
    private Map<String, String> maplist;
    private List<BaseBean> functionlist;
    private String tradecode;//二级行业
    private String industry;//行业
    private String ccompanyId;//往來單位Id
    private String ppid;//产品id

    public PageForm getList() {
        List<Object> parms = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        //获取当前时间
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());
        sql.append("select p.goodsname,ps.ppid,ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2) price,p.brand,p.image,p.goodsid,p.companyid,p.stockSize,p.monthSales,");
        sql.append("cc.companyAddr,ccom.ccompany_Id,cc.industrytype ,ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2) actPrice,");
        sql.append("ROUND(pv.vip*(1+nvl(sz.total_pct,0)/100),2) vip ,pa.type ACTTYPE,P.TYPE,P.REMARK,P.CATEGORYNAME,P.CATEGORYID,pv.vipid,pap.actpriceid");
        sql.append(" from DT_PRO_SETUP ps ");
        sql.append("inner join dt_ProductPackaging p on p.ppid=ps.ppid ");
        sql.append("inner join DT_ccom_com ccom on p.companyid=ccom.compnay_id ");
        sql.append("inner join dtContactCompany cc on ccom.ccompany_Id=cc.ccompanyID ");
        sql.append("left join dt_set_subsidize sz on sz.gtid = cc.industrytype and sz.stutas = '01' ");
        sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state ='00' ");
        sql.append("left join dt_pro_vip pv on pv.ppid = p.ppid and pv.state='00' ");
        sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid ");
        sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
        sql.append("and pa.state <> '04' and pa.state <> '03' and pa.state <> '02' ");
        sql.append("where p.showweixin = ? and p.qualified=? ");
        sql.append(" and p.type not in(?,?,?,?,?,?,'扫码收款') ");
        sql.append(" and cc.industrytype not like '%餐饮%' ");
        sql.append(" and ps.state='00' ");
        parms.add(currentTime);
        parms.add(currentTime);
        parms.add("01");
        parms.add("1");
        String[] temp = {"个人会员", "公司会员", "省级城市", "县级城市", "乡镇城市", "村级新城"};
        Collections.addAll(parms, temp);
        if (proName != null && !"".equals(proName)) {
            sql.append(" and p.goodsname like ?");
            parms.add("%" + proName.trim() + "%");
        }
        if (tradecode != null && !tradecode.equals("")) {
            if (sql.indexOf("and p.goodsname like ?") != -1) {
                sql.replace(sql.indexOf(" and p.goodsname like ?"), sql.indexOf("and p.goodsname like ?") + 20, "and p.goodsname like ? and p.tradename like ?");
            } else {
                sql.append(" and cc.industrytype like ?");
            }
            parms.add("%" + tradecode + "%");
        }
        if (search != null) {
            if (search.equals("smart")) {
                sql.append(" order by cast(p.monthsales as int) desc");
            } else if (search.equals("praise")) {
                sql.append(" and cm.EVALUATION = ?  ");
                sql.append("group by p.goodsname,ps.ppid,ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2) price,");
                sql.append("p.brand,p.image,p.goodsid,p.companyid,p.stockSize, ");
                sql.append("p.monthSales,ccom.ccompany_Id,cc.industrytype, ");
                sql.append("ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2),");
                sql.append("ROUND(pv.vip*(1+nvl(sz.total_pct,0)/100),2),pa.type ACTTYPE,P.TYPE,");
                sql.append("P.REMARK,P.CATEGORYNAME,P.CATEGORYID order by count(ps.ppid) desc");
                parms.add("好评");
            } else if (search.equals("newest")) {
                sql.append(" order by p.packagingdate desc");
            } else if (search.equals("pop")) {
                sql.append(" order by cast(p.monthsales as int) desc");
            } else if (search.equals("plow")) {
                sql.append("order by cast(ps.re_price as number)");
            } else if (search.equals("ptop")) {
                sql.append("order by cast(ps.re_price as number) desc");
            }
        }

        //行业搜索
//		if(industry!=null&&industry.equals("industry")){
//			sql.delete(0, sql.length());
//			parms.clear();
//			sql.append("select ps.ppname,ps.ppid,ps.re_price,p.brand,p.image,p.goodsid,p.companyid,p.stockSize,p.monthSales,cc.companyAddr,ccom.ccompany_Id from");
//			sql.append(" DT_PRO_SETUP ps,dt_ProductPackaging p,DT_ccom_com ccom,dtContactCompany cc where"); 
//			sql.append(" p.ppid=ps.ppid and p.showweixin = ? and p.companyid=ccom.compnay_id and ccom.ccompany_Id=cc.ccompanyID ");
//			sql.append(" and p.tradecode like ?");
//			parms.add("01");
//			if(tradecode!=null&&!tradecode.equals("")){  
//				parms.add("%"+tradecode+"%");
//			}
//			if(region!=null&&!region.equals("")){
//				sql.append("and cc.companyAddr like ?");
//				parms.add("%"+region+"%");
//			}
//			if(search!=null){
//				if(search.equals("smart")||search.equals("pop")){
//					sql.append(" order by cast(p.monthsales as int) desc");
//				}else if(search.equals("newest")){
//					sql.append(" order by p.packagingdate desc");
//				}else if(search.equals("plow")){
//					sql.append(" order by cast(ps.re_price as number)");
//				}else if(search.equals("ptop")){
//					sql.append(" order by cast(ps.re_price as number) desc");
//				}else if(search.equals("praise")){
//					sql.delete(0, sql.length());
//					parms.clear();
//					sql.append("select ps.ppname,ps.ppid,ps.re_price,p.brand,p.image,p.goodsid,p.companyid,p.stockSize,p.monthSales,cm.EVALUATION,ccom.ccompany_Id,count(ps.ppid)");
//					sql.append(" from DT_PRO_SETUP ps, dt_ProductPackaging p, DTComments cm,DT_ccom_com ccom ");
//					sql.append(" where p.ppid = ps.ppid and p.showweixin = ? and ps.ppid = cm.ppID and ps.COM_ID=ccom.compnay_id and cm.EVALUATION = ? and p.tradecode=?");
//					sql.append(" group by ps.ppname,ps.ppid,ps.re_price,p.brand,p.image,p.goodsid,p.companyid,p.stockSize,p.monthSales,cm.EVALUATION,ccom.ccompany_Id");
//					sql.append(" order by count(ps.ppid) desc");
//					parms.add("01");
//					parms.add("好评");
//					parms.add("%"+tradecode+"%");
//				}
//			}			
//		}
        if (search == null || search.equals("")) {
            sql.append(" order by p.packagingdate desc");
        }
        PageForm pf = new PageForm();
        pf = baseBeanService.getPageFormBySQL(
                (null != pageForm ? pageForm.getPageNumber() : 1), 10, sql.toString(), "select count(*) from (" + sql + ")", parms.toArray());
        //非空校验
        /*if (pf != null) {
            List pflist = pf.getList();
            //非空校验
            if (pflist.size() > 0) {
                List a = new ArrayList();
                List<BaseBean> ssl = baseBeanService.getListBeanByHqlAndParams("from SetSubsidize where stutas=?", new Object[]{"01"});
                for (int i = 0; i < pflist.size(); i++) {
                    Object[] pfo = (Object[]) pflist.get(i);
                    for (int j = 0; j < ssl.size(); j++) {
                        SetSubsidize s = (SetSubsidize) ssl.get(j);
                        BigDecimal pct = new BigDecimal(s.getTotalPct()).divide(new BigDecimal(100)).add(BigDecimal.ONE);
                        if (s.getGtid().equals(pfo[11])) {
                            pfo[2] = new BigDecimal(pfo[2].toString()).multiply(pct).setScale(2, BigDecimal.ROUND_HALF_UP);
                            break;
                        }
                    }
                    a.add(pfo);
                }
                pf.setList(a);
            }

        }*/

//		if(pf==null){
//			parms.clear();
//			parms.add("01");
//			Collections.addAll(parms, temp);
//			parms.add("%"+proName+"%");			
//			String newsql=sql.toString().replace(" and ps.ppname like ?", " and p.tradename like ?");
//			pf=baseBeanService.getPageFormBySQL((null!=pageForm?pageForm.getPageNumber():1), 10, newsql,"select count(*) from ("+newsql+")",parms.toArray() );
//		}
        if (proName != null && proName.length() > 0) {
            SaveSearchHistory();
        }
        return pf;
    }

    //地球数字商城首页及搜索页面
    public String DigitalMall() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        if(tc!=null) {

            earthIndexService.addBrowseHistory(tc.getSccId(),"购物","");
        }
        return "digitalmall";
    }

    //上拉加载
    public String ajaxDigitalMall() {
        pageForm = getList();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        map.put("flag", flag);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 查询购物车商品数目
     */

    public String shopCartGoodNum() {

        HttpSession session = ServletActionContext.getRequest().getSession();
        HttpServletRequest request  = ServletActionContext.getRequest();
        String pricetype = request.getParameter("pricetype");
        String posNum = request.getParameter("posNum");
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
        Map<String, Object> map = new HashMap<String, Object>();
        int goodNum = 0;

        try {
            if(posNum!=null&&!posNum.equals("")){
                String sql = "select nvl(sum(to_number(c.itemNum)),0) from DT_SqSelfCart c,dt_ProductPackaging  p where p.ppID = c.pid and c.posNum = ? and p.showweixin = ?";
                goodNum = baseBeanService.getConutByBySqlAndParams(sql, new Object[]{posNum, "01"});

            }else if (cus != null) {

            String sql = "select nvl(sum(c.itemNum),0) from dt_Cart c,dt_ProductPackaging  p where p.ppID = c.pid and c.staff_Id = ? and p.showweixin = ? and (c.pos is null or c.pos = '0')";
            goodNum = baseBeanService.getConutByBySqlAndParams(sql, new Object[]{cus.getStaffid(), "01"});

        }
            map.put("goodNum", goodNum);
        } catch (Exception e) {
            map.put("goodNum", goodNum);
            //  e.printStackTrace();
        }

        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    //地球数字商城广告
    @SuppressWarnings("unchecked")
    public String toDigitalMallAD() {
        String sql = "select p.goodsName,ap.imgurl from dt_ProductPackaging p,dtAttriProduction ap"
                + " where p.goodsID=ap.goodsid and ap.type=? and p.companyID=? and p.goodsID=? ";
        if (goodsId != null && !goodsId.equals("")) {
            adList = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{"1", "company201009046vxdyzy4wg0000000065", goodsId});
            functionlist = baseBeanService.getListBeanByHqlAndParams("from GoodFunction where goodsid = ? order by orders", new Object[]{goodsId});
            if (functionlist.size() > 0) {
                maplist = new HashMap<String, String>();
                for (int i = 0; i < functionlist.size(); i++) {
                    GoodFunction goodFun = (GoodFunction) functionlist.get(i);
                    maplist.put(goodFun.getGfid(), getContentFromFile(goodFun.getUrl()));
                }
            }
        }
        return "digitalmallad";
    }

    /**
     * 搜索历史查询
     */
    @SuppressWarnings({"static-access"})
    public String ajaxSearchHistory() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sw.getObject(session, sw.KEY_SHOPCUSCOM);
        String hql = "from SearchHistory where staffId=? order by shdate desc";
        List<BaseBean> shlist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{cus != null ? cus.getStaffid() : ""});
        JSONObject json = new JSONObject();
        json.accumulate("shlist", shlist);
        result = json.toString();
        return "success";
    }

    /**
     * 搜索历史保存
     */
    @SuppressWarnings("static-access")
    public void SaveSearchHistory() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sw.getObject(session, sw.KEY_SHOPCUSCOM);
        SearchHistory sh = null;
        if (cus != null) {
            List<BaseBean> shlist = baseBeanService.getListBeanByHqlAndParams("from SearchHistory where staffId=? order by shdate desc", new Object[]{cus.getStaffid()});
            if (shlist == null || shlist.size() == 0) {
                sh = new SearchHistory();
                sh.setShId(serverService.getServerID("sh"));
                sh.setShname(proName);
                sh.setStaffId(cus.getStaffid());
                sh.setShdate(new Date());
            } else {
                sh = (SearchHistory) baseBeanService.getBeanByHqlAndParams("from SearchHistory where staffId=? and shname=?", new Object[]{cus.getStaffid(), proName});
                if (sh != null) {
                    sh.setShdate(new Date());
                } else {
                    if (shlist.size() >= 5) {
                        SearchHistory s = (SearchHistory) shlist.get(4);
                        baseBeanService.deleteBeanByKey(SearchHistory.class, s.getShkey());
                    }
                    sh = new SearchHistory();
                    sh.setShId(serverService.getServerID("sh"));
                    sh.setShname(proName);
                    sh.setStaffId(cus.getStaffid());
                    sh.setShdate(new Date());
                }
            }
            baseBeanService.saveOrUpdate(sh);
        }

    }

    /**
     * 删除历史
     */
    @SuppressWarnings("static-access")
    public String delSearchHistory() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sw.getObject(session, sw.KEY_SHOPCUSCOM);
        if (cus != null) {
            String hql = "delete SearchHistory where staffId=?";
            List<Object[]> param = new ArrayList<Object[]>();
            Object[] obj = {cus.getStaffid()};
            param.add(obj);
            baseBeanService.executeHqlsByParamsList(null, new String[]{hql}, param);
            result = "1";
        }

        return "success";
    }

    /**
     * 根据txt URL 获取内容
     *
     * @param filepath
     * @return
     */
    private String getContentFromFile(String filepath) {
        String path = ServletActionContext.getServletContext()
                .getRealPath("\\")
                + filepath;
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        try {
            return contentToFileService.getContent(path);

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 活动状态更改
     * 活动状态[00:初始 01:已开启 02:暂停 03:结束 04:已删
除]
     * 2019-2-26
     *
     * @return
     */
    public String activeStateUpdate() {
        Map<String, Object> map = new HashMap<String, Object>();
        String code = "201";
        //获取产品活动信息
        String sql = "select pa.activityid,pa.state from dt_pro_activity pa ,dt_pro_activity_price ap" +
                " where pa.activityid=ap.activityid and ap.ppid= ?";
        Object objects = this.baseBeanService.getObjectBySqlAndParams(sql, new Object[]{ppid});
        if (objects != null) {//判断活动是否存在
            Object[] obj1 = (Object[]) objects;
            if (obj1[1] != null && !obj1[1].equals("") && !obj1[1].equals("01")&&obj1[1].equals("00")) {//更改活动状态为01
                String sql1 = "update dt_pro_activity set state = '01' where activityid =?";
                this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql1}, new Object[]{obj1[0]});
                code = "200";
            }
        }
        map.put("code", code);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 活动状态更改
     * 活动状态[00:初始 01:已开启 02:暂停 03:结束 04:已删除]
     * 超过活动时间，不展示活动价》判断活动状态,改为 03:结束
     * 2019-3-04
     *
     * @return
     */
    public String activeTimeoutStateUpdate() {
        Map<String, Object> map = new HashMap<String, Object>();
        //获取当前时间
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());
        String code = "201";
        //获取产品活动信息
        String sql = "select pa.activityid,pa.state from dt_pro_activity pa ,dt_pro_activity_price ap" +
                " where pa.activityid = ap.activityid and ap.ppid= ? and pa.endtime < to_date(?,'yyyy-MM-dd HH24:MI:SS')";
        Object objects = this.baseBeanService.getObjectBySqlAndParams(sql, new Object[]{ppid,currentTime});
        if (objects != null) {//判断活动是否存在
            Object[] obj1 = (Object[]) objects;
            if (obj1[1] != null && !obj1[1].equals("")) {//更改活动状态为03:结束
                String sql1 = "update dt_pro_activity set state = '03' where activityid =?";
                this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql1}, new Object[]{obj1[0]});
                code = "200";
            }
        }
        map.put("code", code);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public List<BaseBean> getAdList() {
        return adList;
    }

    public void setAdList(List<BaseBean> adList) {
        this.adList = adList;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Map<String, String> getMaplist() {
        return maplist;
    }

    public void setMaplist(Map<String, String> maplist) {
        this.maplist = maplist;
    }

    public List<BaseBean> getFunctionlist() {
        return functionlist;
    }

    public void setFunctionlist(List<BaseBean> functionlist) {
        this.functionlist = functionlist;
    }

    public String getTradecode() {
        return tradecode;
    }

    public void setTradecode(String tradecode) {
        this.tradecode = tradecode;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCcompanyId() {
        return ccompanyId;
    }

    public void setCcompanyId(String ccompanyId) {
        this.ccompanyId = ccompanyId;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }
}
