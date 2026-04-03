package hy.ea.marketing.service.impl;

import com.tiantai.wfj.bo.PresetKey;
import com.tiantai.wfj.bo.PresetPage;
import com.tiantai.wfj.bo.Scale;
import hy.ea.bo.human.Staff;
import hy.ea.marketing.service.ScaleManageSerivce;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 电子秤管理
 *
 * @author [mz]
 * @version [1.0, 2018-07-28]
 * @see
 * @since
 */
@Service
@Transactional
public class ScaleManageServiceImpl implements ScaleManageSerivce {
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanService baseBeanService;

    private Logger logger = Logger.getLogger(ScaleManageServiceImpl.class);


    @Override
    public void addOrUpdate(Scale scale, String companyID, String staffID) {
        if (scale != null) {
            if (scale.getScId() == null || scale.getScId().equals("")) {
                scale.setScId(serverService.getServerID("scid"));

            }
                String hql = "from Staff where staffID = ?";
                Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{staffID});
                scale.setStaffID(staffID);
                scale.setCompanyID(companyID);
                if (staff != null) {
                    scale.setStaffname(staff.getStaffName());
                }
                scale.setCdate(new Date());
                baseBeanDao.update(scale);
            }


    }

    @Override
    public void delete(String scalekey) {

        baseBeanDao.deleteBeanByKey(Scale.class,scalekey);

    }

    @Override
    public Map<String,Object> getGoodsList(String companyID){
        Map<String,Object> map = new HashMap<String,Object>();


        //获取当前时间
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());

        StringBuilder sql = new StringBuilder();

        sql.append("select c.plu,c.alternativeItemID,p.goodsName,c.unitOfMeasureCode,");
        sql.append("case when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)   else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price ");
        sql.append(" from DT_PRO_SETUP ps inner join dt_ProductPackaging p on ps.ppid = p.ppid and ps.state = '00'");
        sql.append("inner join dtScaleGoods c on c.goodsID = p.goodsID ");

        sql.append("left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state = '00'");
        sql.append("left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01'");
        sql.append("and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

        sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state = '00'");
        sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00'");


        sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

        sql.append("left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
        sql.append("left join dtCompany t on t.companyid = cc.compnay_id ");
        sql.append("left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid ");
        sql.append("left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");
        sql.append("where c.companyID = ? and p.showweixin=? ");




        List<Object> goodslist =  baseBeanDao.getListObjectBySqlAndParams(sql.toString(),new Object[]{currentTime,currentTime,currentTime,currentTime,companyID,"01"});
        List<Object> list = new ArrayList<Object>();
        for (Object obj : goodslist) {
            Object[] arr = (Object[]) obj;
            JSONObject temp = new JSONObject();
            temp.accumulate("plu", arr[0] == null ? "" : arr[0]);
            temp.accumulate("alternativeItemID", arr[1] == null ? "" : arr[1]);
            temp.accumulate("goodsName", arr[2] == null ? "" : arr[2]);
            temp.accumulate("unitOfMeasureCode", arr[3] == null ? "" : arr[3]);
            temp.accumulate("price", arr[4] == null ? "" : arr[4]);
            list.add(temp);
        }
       map.put("goodslist",list);
      return map;
    }
    /**
     *    添加预置键
     */

    public  void  addPreOrUpdate(Map<String, PresetKey> presetKeyMap, PresetPage presetPage, String companyID, String staffID){
        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        List<Object> params = new ArrayList<Object>();
        List<String> hqls = new ArrayList<String>();
        if(presetPage!=null&&presetPage.getScpId()!=null&&!presetPage.getScpId().equals("")){
            presetPage = (PresetPage)baseBeanDao.getBeanByHqlAndParams("from PresetPage where scpId = ?",new Object[]{presetPage.getScpId()});
            String hqldelete = "delete from PresetKey where  scpId = ?";
            params.add(presetPage.getScpId());
            hqls.add(hqldelete);
        }else{
            presetPage.setScpId(serverService.getServerID("scpid"));
            List<BaseBean> prelist = baseBeanDao.getListBeanByHqlAndParams("from PresetPage where companyID = ?",new Object[]{companyID});
            DecimalFormat form = new DecimalFormat("000");
            String ss = form.format(prelist.size() + 1);
            presetPage.setPreCode(ss);

        }
        presetPage.setCdate(new Date());
        presetPage.setCompanyID(companyID);
        presetPage.setStaffID(staffID);

        String hql = "from Staff where staffID = ?";
        Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{staffID});
        if(staff!=null) {
            presetPage.setStaffname(staff.getStaffName());
        }
        baseBeanList.add(presetPage);

        for (PresetKey presetKey : presetKeyMap.values()) {
            presetKey.setPrId(serverService.getServerID("prid"));
            presetKey.setScpId(presetPage.getScpId());
            presetKey.setCompanyID(companyID);

            baseBeanList.add(presetKey);

        }
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList,hqls.toArray(new String[]{}),params.toArray());

    }

    /**
     *
     * 删除预置键
     * @param scpId
     */
    @Override
    public  void  deletePreset(String scpId){

        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        List<Object> params = new ArrayList<Object>();
        List<String> hqls = new ArrayList<String>();
        String hql1 = "delete from PresetKey where  scpId = ?";
        params.add(scpId);
        hqls.add(hql1);

        String hql2 = "delete from PresetPage where  scpId = ?";
        hqls.add(hql2);
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, hqls.toArray(new String[]{}), params.toArray());

    }

    //根据父查子
    @Override
    public List<BaseBean> getPreKeyList(String scpId){
       String hql = "from PresetKey where scpId = ? order by cast(keyNo as integer)";
       List<BaseBean> prekeylist =  baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{scpId});

       return prekeylist;

    }
    /**
     * 查询热销称重商品
     * @param companyID
     * @return
     */
    public List<BaseBean> findHotSaleGoods(String companyID){
        StringBuilder sql = new StringBuilder();
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());


        sql.append("select ps.ppid,p.goodsname,p.goodsID,");
        sql.append("case when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price,");
        sql.append("p.companyid,s.unitOfMeasureCode,s.plu,s.alternativeItemID,");
        sql.append("case when tpa.type is not null then '4' when pa.type is not null then '3' else '0' end pricetype,");
        sql.append("case when tpa.type is not null then to_number(tpap.factory) when pa.type is not null then to_number(pap.factory) else to_number(ps.ef_price) end costmoney,");
        sql.append("case when tpa.type is not null then to_char(tpap.actPriceId) when pa.type is not null then to_char(pap.actPriceId) else ps.suid end activityID");

        sql.append(" from DT_PRO_SETUP ps left join dt_ProductPackaging p on ps.ppid = p.ppid and ps.state = '00'");
        sql.append(" inner join dtScaleGoods s on s.goodsID = p.goodsID ");
        //特价
        sql.append(" left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state = '00'");
        sql.append(" left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01'");
        sql.append(" and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS')");

        //促销
        sql.append(" left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state = '00'");
        sql.append(" left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00'");
        sql.append(" and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS')");
        //消费红包
        sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid");
        sql.append(" left join dtCompany t on t.companyid = cc.compnay_id");
        sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
        sql.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
        sql.append("  where ps.fcom_id is null and p.isScale = ? and p.companyID = ? and p.showweixin = ? and s.unitOfMeasureCode = ? and rownum<=10 order by monthSales desc");





        List<BaseBean>  list = baseBeanDao.getListBeanBySqlAndParams(sql.toString(),new Object[]{currentTime,currentTime,currentTime,currentTime,"0",companyID,"01","KGM"});

        return  list;
    }


    /**
     * 查询称重商品
     * @param companyID
     * @return
     */
    public List<BaseBean> findGoods(String companyID,String parameter){
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());
//        sql.append("select ps.ppid,p.goodsname,p.goodsID,ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2) pri,p.companyid,s.unitOfMeasureCode,s.plu,s.alternativeItemID");
//        sql.append("case when  tpap.actPrice is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pap.actPrice is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price,");

//        sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
//        sql.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
//        sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
//        sql.append("  where ps.fcom_id is null and p.isScale = ? and p.companyID = ? and p.showweixin = ?");


        sql.append("select ps.ppid,p.goodsname,p.goodsID,");
        sql.append("case when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price,");
        sql.append("p.companyid,s.unitOfMeasureCode,s.plu,s.alternativeItemID,");
        sql.append("case when tpa.type is not null then '4' when pa.type is not null then '3' else '0' end pricetype,");
        sql.append("case when tpa.type is not null then to_number(tpap.factory) when pa.type is not null then to_number(pap.factory) else to_number(ps.ef_price) end costmoney,");
        sql.append("case when tpa.type is not null then to_char(tpap.actPriceId) when pa.type is not null then to_char(pap.actPriceId) else ps.suid end activityID");

        sql.append(" from DT_PRO_SETUP ps left join dt_ProductPackaging p on ps.ppid = p.ppid and ps.state = '00'");
        sql.append(" inner join dtScaleGoods s on s.goodsID = p.goodsID ");
        //特价
        sql.append(" left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state = '00'");
        sql.append(" left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01'");
        sql.append(" and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS')");

        //促销
        sql.append(" left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state = '00'");
        sql.append(" left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00'");
        sql.append(" and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS')");
        //消费红包
        sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid");
        sql.append(" left join dtCompany t on t.companyid = cc.compnay_id");
        sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
        sql.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
        sql.append("  where ps.fcom_id is null and p.isScale = ? and p.companyID = ? and p.showweixin = ?");


        params.add(currentTime);
        params.add(currentTime);
        params.add(currentTime);
        params.add(currentTime);

        params.add("0");
        params.add(companyID);
        params.add("01");


        if(parameter!=null&&!parameter.equals("")){

       //     sql.append(" and (p.goodsname like ? or fgetpy(p.goodsname) like ?");
       //     params.add("%"+parameter+"%");
         //   params.add("%"+parameter+"%");
            if(isNumeric(parameter)){
                sql.append(" or s.plu = ?");
                params.add(parameter);
            }
            sql.append(")");



        }

        List<BaseBean>  list = baseBeanDao.getListBeanBySqlAndParams(sql.toString(),params.toArray());

        return  list;
    }

    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }


    /***
     * 查询有产品的称重的产品分类
     * @return
     */
    public List<BaseBean> findScaleGoodsCate(String companyID){

         StringBuilder sql = new StringBuilder();
         sql.append("select dd.codeid,dd.codevalue");
         sql.append(" from(select distinct(d.codepid) codepid from (select distinct(c.codeid) codeid  from dt_ProCateRelate c");
         sql.append(" left join dt_productpackaging p on p.ppid = c.ppid where c.companyid = ? and p.showweixin = '01' and  p.isscale='0') k");
         sql.append(" left join Dtccode d on k.codeid = d.codeid where d.companyid = ?) t");
         sql.append(" left join Dtccode dd on t.codepid = dd.codeid where dd.companyid = ?");
         List<BaseBean> list = baseBeanDao.getListBeanBySqlAndParams(sql.toString(),new Object[]{companyID,companyID,companyID});

          return list;
    }

    /**
     * 根据一级分类获取二级分类
     * @param companyID
     * @param codePID
     * @return
     */
    public List<BaseBean> findSecondCate(String companyID,String codePID){
        StringBuilder sql = new StringBuilder();
        sql.append("select c.codeid,c.codevalue from Dtccode c");
        sql.append("  where c.companyid = ?");
        sql.append("  and c.codepid = ? and c.codeid in");
        sql.append(" (select distinct (r.codeid) from dt_ProCateRelate r left join dt_productpackaging p on p.ppid = r.ppid where r.companyid = ? and p.showweixin = '01' and  p.isscale='0')");


        List<BaseBean> list = baseBeanDao.getListBeanBySqlAndParams(sql.toString(),new Object[]{companyID,codePID,companyID});
        return  list;
    }

    /**
     *
     * 根据分类查询商品
     * @param companyID
     * @param codeID
     * @return
     */

    public PageForm findProductByCate(String companyID, String codeID,String parameter,int pageNumber,int pageSize,int lxType){
        List<Object> params = new ArrayList<Object>();
        HttpServletRequest request = ServletActionContext.getRequest();
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());
        StringBuilder sql = new StringBuilder();
        sql.append("select ps.ppid,p.goodsname,p.goodsID,p.image,");
        sql.append("case when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price,");
        sql.append("p.companyid,s.unitOfMeasureCode,s.plu,s.alternativeItemID,");
        sql.append("case when tpa.type is not null then '4' when pa.type is not null then '3' else '0' end pricetype,");
        sql.append("case when tpa.type is not null then to_number(tpap.factory) when pa.type is not null then to_number(pap.factory) else to_number(ps.ef_price) end costmoney,");
        sql.append("case when tpa.type is not null then to_char(tpap.actPriceId) when pa.type is not null then to_char(pap.actPriceId) else ps.suid end activityID ");
        if(codeID!=null&&!codeID.equals("")) {
            sql.append(" from dt_ProCateRelate cc left join DT_PRO_SETUP ps on cc.ppid = ps.ppid");
        }else{
            sql.append(" from  DT_PRO_SETUP ps ");
        }
        sql.append("  left join dt_ProductPackaging p on ps.ppid = p.ppid and ps.state = '00'");
        sql.append(" inner join dtScaleGoods s on s.goodsID = p.goodsID ");
        //特价
        sql.append(" left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state = '00'");
        sql.append(" left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01'");
        sql.append(" and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS')");

        //促销
        sql.append(" left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state = '00'");
        sql.append(" left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00'");
        sql.append(" and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS')");
        //消费红包
        sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid");
        sql.append(" left join dtCompany t on t.companyid = cc.compnay_id");
        sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
        sql.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
        sql.append("  where ps.fcom_id is null");
        //如果类型为1，sql语句变为查询批发商城信息
        if (lxType == 1) {//查询类型，0：其他入口进入1：批发商城进入
            getWmczShoppingCarSql(codeID,sql);
        }else{
            params.add(currentTime);
            params.add(currentTime);
            params.add(currentTime);
            params.add(currentTime);
        }
        if(codeID!=null&&!codeID.equals("")){
            sql.append(" and cc.codeid = ?");
            params.add(codeID);
        }
        params.add("0");
        params.add(companyID);
        params.add("01");

        sql.append("  and p.isScale = ? and p.companyID = ? and p.showweixin = ?");

        if(parameter!=null&&!parameter.equals("")){

             if(isNumeric(parameter)){
                sql.append(" and (s.plu = ? or p.goodsname like ?)");
                 params.add(parameter);
                 params.add("%"+parameter+"%");
             }else{
                 sql.append(" and  p.goodsname like ?");
                 params.add("%"+parameter+"%");
             }
        }

        PageForm pageForm = baseBeanService.getPageFormBySQL(
                pageNumber,pageSize, sql.toString(), "select count(*) from (" + sql + ")",
                params.toArray());
        return pageForm;
    }

    /**
     * 拼接批发商城sql语句
     *
     * @return
     */
    private void getWmczShoppingCarSql(String codeID,StringBuilder sql) {
        sql.delete(0,sql.length());
        sql.append(" select ps.ppid,p.goodsname,p.goodsID,p.image,");
        sql.append(" ROUND(dpw.wholesale * (1 + nvl(sz.total_pct, 0) / 100), 2) price,");
        sql.append(" p.companyid,s.unitOfMeasureCode,s.plu,s.alternativeItemID,");
        sql.append(" '1' pricetype,");
        sql.append(" to_number(dpw.wholesale) costmoney,");
        sql.append(" to_char(dpw.wholesaleId) activityID ");
        if(codeID!=null&&!codeID.equals("")) {
            sql.append(" from dt_ProCateRelate cc left join DT_PRO_SETUP ps on cc.ppid = ps.ppid");
        }else{
            sql.append(" from  DT_PRO_SETUP ps ");
        }
        sql.append("  left join dt_ProductPackaging p on ps.ppid = p.ppid and ps.state = '00'");
        sql.append(" inner join dtScaleGoods s on s.goodsID = p.goodsID ");
        sql.append(" left join dt_pro_wholesale dpw on p.ppid = dpw.ppid  and dpw.state = '00'");
        //消费红包
        sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid");
        sql.append(" left join dtCompany t on t.companyid = cc.compnay_id");
        sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
        sql.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
        sql.append("  where ps.fcom_id is null and dpw.wholesale is not null ");
    }




    /**
     *
     * 根据plu查询
     * @param companyID
     * @return
     */
    public Object findProductPLU(String companyID, String plu){
        List<Object> params = new ArrayList<Object>();
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());
        StringBuilder sql = new StringBuilder();
        sql.append("select ps.ppid,p.goodsname,p.goodsID,p.image,");
        sql.append("case when tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price,");
        sql.append("p.companyid,s.unitOfMeasureCode,s.plu,s.alternativeItemID,");
        sql.append("case when tpa.type is not null then '4' when pa.type is not null then '3' else '0' end pricetype,");
        sql.append("case when tpa.type is not null then to_number(tpap.factory) when pa.type is not null then to_number(pap.factory) else to_number(ps.ef_price) end costmoney,");
        sql.append("case when tpa.type is not null then to_char(tpap.actPriceId) when pa.type is not null then to_char(pap.actPriceId) else ps.suid end activityID,");
        sql.append("p.goodsNum ");
        sql.append(" from  DT_PRO_SETUP ps ");
        sql.append(" left join dt_ProductPackaging p on ps.ppid = p.ppid and ps.state = '00'");
        sql.append(" inner join dtScaleGoods s on s.goodsID = p.goodsID ");
        //特价
        sql.append(" left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state = '00'");
        sql.append(" left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01'");
        sql.append(" and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS')");

        //促销
        sql.append(" left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state = '00'");
        sql.append(" left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00'");
        sql.append(" and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS')");
        //消费红包
        sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid");
        sql.append(" left join dtCompany t on t.companyid = cc.compnay_id");
        sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
        sql.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
        sql.append("  where ps.fcom_id is null");
        params.add(currentTime);
        params.add(currentTime);
        params.add(currentTime);
        params.add(currentTime);
        params.add("0");
        params.add(companyID);
        params.add("01");

        sql.append("  and p.isScale = ? and p.companyID = ? and p.showweixin = ?");

        if(plu!=null&&!plu.equals("")){

            if(isNumeric(plu)){
                sql.append(" and s.plu = ?");
                params.add(plu);
            }
        }

        Object obj = baseBeanService.getObjectBySqlAndParams(sql.toString(),params.toArray());
        return obj;
    }

    /**
     *
     * 移动端根据分类查询商品
     * @param companyID 公司id
     * @param codeID  类目id
     * @param ppname  产品名称
     * @param searchtype 查询类型  0或null:上架产品  1：全部产品
     * @return
     */
    @Override
    public PageForm findProductByCatePhone(String companyID, String codeID,String ppname,String searchtype,String parameter,int pageNumber,int pageSize){
        List<Object> params = new ArrayList<Object>();

        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());
        StringBuilder sql = new StringBuilder();
        sql.append("select p.ppid,p.goodsname,p.goodsID,p.image,");
        sql.append("p.companyid,s.unitOfMeasureCode,s.plu,s.alternativeItemID, i.invenquantity,p.variableid,p.brand,");
        if(codeID!=null&&!codeID.equals("")) {
            sql.append("cc.codeid,ccc.codeValue,");
        }
        sql.append("p.yjstatus,p.wholesaleStatus");
        if(codeID!=null&&!codeID.equals("")) {
            sql.append(" from dt_ProCateRelate cc left join dt_ProductPackaging p on cc.ppid = p.ppid");
            sql.append(" left join dtccode ccc on ccc.codeID = cc.codeID");
        }else{
            sql.append(" from dt_ProductPackaging p ");
        }

        sql.append(" left join dt_inv_invt i on p.ppid = i.productid ");
        sql.append(" inner join dtScaleGoods s on s.goodsID = p.goodsID ");

        sql.append(" where 1=1");
        if(codeID!=null&&!codeID.equals("")){
            sql.append(" and cc.codeid = ?");
            params.add(codeID);
        }
        params.add("0");
        params.add(companyID);
        sql.append(" and p.isScale = ? and p.companyID = ?");
        if(searchtype==null||searchtype.equals("")||searchtype.equals("0")){
        	params.add("01");
        	sql.append(" and p.showweixin = ?");
        }        
        if(parameter!=null&&!parameter.equals("")){
            if(isNumeric(parameter)){
                sql.append(" and s.plu = ?");
                params.add(parameter);
            }
        }
        if(ppname!=null&&!"".equals(ppname)){
            sql.append(" and p.goodsname like ?");
            params.add("%"+ppname+"%");
        }

        PageForm pageForm = baseBeanService.getPageFormBySQL(
                pageNumber,pageSize, sql.toString(), "select count(*) from (" + sql + ")",
                params.toArray());
        return pageForm;
    }

}
