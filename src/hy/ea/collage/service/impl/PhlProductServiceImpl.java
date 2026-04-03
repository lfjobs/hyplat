package hy.ea.collage.service.impl;

import hy.ea.bo.human.StaffPosInfo;
import hy.ea.collage.service.PhlProductSerivce;
import hy.ea.util.Constant;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 拼货拉批发商城商品
 *
 * @author [mz]
 * @version [1.0, 2018-12-13]
 * @see
 * @since
 */
@Service

public class PhlProductServiceImpl implements PhlProductSerivce {
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanService baseBeanService;


    /**
     *
     * @param pageNumber
     * @param pageSize
     * @param industryID   行业  农贸市场
     * @param placeCrit   按地区查询
     * @param cateCrit    按货品类别查询
     * @param disCrit     按距离最近查询
     * @param saleCrit   按销量查询
     * @param priceCrit   按价格查询
     * @return
     */
    public PageForm  getPageFormPro(int pageNumber,int pageSize,String industryID,String placeCrit,String cateCrit,String disCrit,String saleCrit,String priceCrit,String goodsName,String staffID){
        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();

        sql.append("select distinct(pp.ppid),pp.goodsid,pp.goodsname,pp.image,pp.variableid,round(sp.wholesale*(1+nvl(sz.total_pct,0)/100),2) pri,cm.companyaddr,cc.compnay_id,cc.ccompany_id,pp.monthSales,cm.accuracy,cm.dimension,t.totalsales,pp.packagingDate");
        sql.append(" from dt_pro_wholesale sp  left join dt_productpackaging pp on sp.ppid = pp.ppid ");
        sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = pp.companyid left join dtCompany t on t.companyid = cc.compnay_id");
        sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");

        if(cateCrit!=null&&!cateCrit.equals("")){
            if(cateCrit.indexOf("pcf")==-1) {  //一级分类
                sql.append(" right join dt_ProCateRelate pr on pr.ppid = pp.ppid and pr.codeid=?");
                objList.add(cateCrit);
            }else{
                sql.append(" right join (select df.ppid from Dtccode f,dt_ProCateRelate df where f.codepid= ? and f.companyid=df.companyid and df.codeid=f.codeid) cf on pp.ppid = cf.ppid");
                objList.add(cateCrit.substring(3));
            }
        }

        sql.append("  where cm.industryid = ? and  pp.showweixin = '01' and sp.state='00'");
        objList.add(industryID);


        //商品名称查询
        if(goodsName!=null&&!goodsName.equals("")){
            sql.append(" and pp.goodsName like ?");
            objList.add("%"+goodsName+"%");
        }
        //按区域查询
        if(placeCrit!=null&&!placeCrit.equals("")){
            sql.append(" and cm.companyaddr like ?");
            objList.add("%"+placeCrit+"%");
        }

		if ((saleCrit != null && !saleCrit.equals("")) || (priceCrit != null
				&& !priceCrit.equals(""))) {
			// 产品销量查询
			if (saleCrit != null && !saleCrit.equals("")) {
				sql.append("order by pp.monthSales desc");
			}
			// 价格
			if (priceCrit != null && !priceCrit.equals("")) {
				if (saleCrit != null && !saleCrit.equals("")) {
					sql.append(",pri");
				} else {
					sql.append("order by pri");
				}
			}
		} else {

			// 随机查询
//			sql.append(" order by dbms_random.value");
           sql.append(" order by pp.packagingDate desc");

		}

        //按距离查询
        if(disCrit!=null&&!disCrit.equals("")){
            String hql = "from StaffPosInfo where staffID = ?";
            StaffPosInfo ps = (StaffPosInfo) baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{staffID});
           if(ps!=null) {
               sql.append(",getdistance(?,?,cm.accuracy,cm.dimension)");
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
     * 搜索热门分类搜索
     * @param ccompanyID
     * @return
     */
    public List<BaseBean> hotCateSearch(String ccompanyID){
        String sql = "select h.codeID,c.codeValue from dt_phl_ProductCateHot h,DTSCODE c where h.codeID = c.codeID and ";
        if(Constant.PHL_ID.equals(ccompanyID)){
            sql += " ccompanyPID = ?";

        }else{
            sql += " ccompanyID = ?";

        }
        sql+=" and rownum <= 20 order by hotnum desc";
        List<BaseBean> list =  baseBeanDao.getListBeanBySqlAndParams(sql,new Object[]{ccompanyID});

        return list;

    }

}
