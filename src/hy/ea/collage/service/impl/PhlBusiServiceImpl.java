package hy.ea.collage.service.impl;

import hy.ea.bo.human.StaffPosInfo;
import hy.ea.collage.service.PhlBusiSerivce;
import hy.ea.util.Constant;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.SDistrictService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * 拼货拉商家
 *
 * @author [mz]
 * @version [1.0, 2018-12-10]
 * @see
 * @since
 */
@Service

public class PhlBusiServiceImpl implements PhlBusiSerivce {
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanService baseBeanService;

    /**
     * 获取省份
     * @return
     */
    public List<BaseBean> getCDistricts(){
      String hql = "from SDistrict where districtPID = ? or districtPID = ? or districtPID = ? or districtPID = ? " +
                    " or districtPID = ? or districtPID = ?  order by districtName ";
      List<BaseBean>  distinctlist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{"sdistrict20140829779hcj26db0000000012", "sdistrict20140829779hcj26db0000000013",
                    "sdistrict20140829779hcj26db0000000014", "sdistrict20140829779hcj26db0000000015", "sdistrict20140829779hcj26db0000000016", "sdistrict20140829779hcj26db0000000011"});


        return distinctlist;
    }


    /**
     *
     * @param pageNumber
     * @param pageSize
     * @param industryID   行业  农贸市场
     * @param placeCrit   按地区查询
     * @param cateCrit    按货品类别查询
     * @param disCrit     按距离最近查询
     * @param saleCrit   按销量查询
     * @return
     */
    public PageForm  getPageFormBusi(int pageNumber,int pageSize,String industryID,String companymid,String placeCrit,String cateCrit,String disCrit,String saleCrit,String companyName,String staffID){
        StaffPosInfo sf = (StaffPosInfo)baseBeanDao.getBeanByHqlAndParams("from StaffPosInfo where staffID = ?",new Object[]{staffID});
        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();

        if(sf!=null){
            objList.add(sf.getLongitude());
            objList.add(sf.getLatitude());
        }else{
            objList.add("");
            objList.add("");

        }

    	
        sql.append("select c.ccompanyid,c.logopath,nvl(c.brandinfo,' '),c.companyname,nvl(c.companyaddr,' '),t.companyid,getdistance(?,?,c.accuracy,c.dimension) c");
        sql.append(" from Dtcontactcompany c, Dt_Ccom_Com m, Dtcompany t ");
        if(cateCrit!=null&&!cateCrit.equals("")){
            if(cateCrit.indexOf("pcf")==-1) {  //一级分类
                sql.append(",dt_ProCateRelate r");
            }else{
                sql.append(",(select distinct(df.companyid) from Dtccode f,dt_ProCateRelate df where f.codepid= ? and f.companyid=df.companyid and df.codeid=f.codeid) cf");
                objList.add(cateCrit.substring(3));
            }
        }
        sql.append(" where c.ccompanyid = m.ccompany_id  and m.compnay_id = t.companyid and c.industryid = ?");
    
        objList.add(industryID);
       
        if(companymid!=null&&!companymid.equals("")&&!companymid.equals(Constant.PHL_MID)){
        	
        	  sql.append(" and t.companymid = ?");
              objList.add(companymid);
        }

        //公司名称查询
        if(companyName!=null&&!companyName.equals("")){
            sql.append(" and c.companyName like ?");
            objList.add("%"+companyName+"%");
        }
        //按区域查询
        if(placeCrit!=null&&!placeCrit.equals("")){
            sql.append(" and c.companyaddr like ?");
            objList.add("%"+placeCrit+"%");
        }
        //按品类查询
        if(cateCrit!=null&&!cateCrit.equals("")){
            if(cateCrit.indexOf("pcf")==-1) {
                sql.append(" and r.codeid= ? and r.companyid = t.companyid");
                objList.add(cateCrit);
            }else{
                sql.append(" and cf.companyid = t.companyid");

            }
        }


        //随机查询
        sql.append(" order by c");
        //按公司总销量查询
        if(saleCrit!=null&&!saleCrit.equals("")){
            sql.append(",to_number(t.totalsales) desc");
        }

        //按距离查询
        if(disCrit!=null&&!disCrit.equals("")){
            String hql = "from StaffPosInfo where staffID = ?";
            StaffPosInfo ps = (StaffPosInfo) baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{staffID});
           if(ps!=null) {
               sql.append(",getdistance(?,?,c.accuracy,c.dimension)");
               objList.add(ps.getLongitude());
               objList.add(ps.getLatitude());
           }

        }

        PageForm pageForm = baseBeanService.getPageFormBySQL(
                pageNumber,pageSize, sql.toString(), "select count(*) from (" + sql + ")",
                objList.toArray());

        return pageForm;
    }
    
    /**
     * 
     * 查询公司产品
     * @param list
     * @return
     */
    public Map<String,List<BaseBean>> getShowProduct(List<BaseBean> list){
    	
    	Map<String,List<BaseBean>> m = new HashMap<String,List<BaseBean>>();
        StringBuilder sql = new StringBuilder();

        sql.append("select pp.image,sp.wholesale*(1+nvl(sz.total_pct,0)/100)");
        sql.append(" from dt_pro_wholesale sp  left join dt_productpackaging pp on sp.ppid = pp.ppid");
        sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = pp.companyid");
        sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
        
        sql.append("  where pp.showweixin = '01' and pp.companyID = ? and rownum < 4  order by dbms_random.value");
        List<BaseBean> listp =  null;
        for (int i = 0; i < list.size(); i++) {
        	Object obj = list.get(i);
        	Object[] objs = (Object[])obj;
        	listp =  baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{objs[5].toString()});
        	m.put(objs[5].toString(), listp);
		}
        return m;
       
    }



}
