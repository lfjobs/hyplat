package hy.ea.collage.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.company.CcomCom;
import hy.ea.bo.finance.RecentViewProCate;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffPosInfo;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.production.GoodFunction;
import hy.ea.collage.service.PhlIndexSerivce;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.Constant;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tiantai.wfj.bo.PhlTopics;

/**
 * 拼货拉首页
 *
 * @author [mz]
 * @version [1.0, 2018-11-24]
 * @see
 * @since
 */
@Service
public class PhlIndexServiceImpl implements PhlIndexSerivce {
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanService baseBeanService;
    
    @Resource
    private UploadContentToFileService contentToFileService;

    private Logger logger = Logger.getLogger(PhlIndexServiceImpl.class);
    /**
     * 获取轮播图
     * @return
     */
    public List<BaseBean> getRotationPicList(String ccompanyID,String pos){

        return null;
    }
    /**
     * 获取拼货拉农贸市场分类
     * @param ccompanyID
     * @return
     */
    public List<BaseBean> getPhlProCate(String ccompanyID){

        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();

        sql.append("select t.codevalue,t.codeid,t.iconpath");
        sql.append(" from dtCcode t, dt_phl_ProductCateHot h where t.companyid = (select cm.compnay_id from dt_ccom_com cm where cm.ccompany_id=?) and h.ccompanypid = ?");
        sql.append(" and h.codeid = t.codeid and rownum<? order by dbms_random.value  desc");
        objList.add(ccompanyID);
        objList.add(ccompanyID);
        objList.add(10);
        List<BaseBean>  hotcatelist = baseBeanDao.getListBeanBySqlAndParams(sql.toString(),objList.toArray());


        return hotcatelist;
    }

    /**
     *
     * 查询农贸市场分类
     * @param codeID
     * @return
     */
    public List<BaseBean> getProCate(String ccompanyID,String codeID){


        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();

        sql.append("select t.codevalue,t.codeid");
        sql.append(" from dtCcode t where t.companyid = (select cm.compnay_id from dt_ccom_com cm where cm.ccompany_id=?) and t.codepid = ?");
        objList.add(ccompanyID);
        objList.add(codeID);

        List<BaseBean>  catelist = baseBeanDao.getListBeanBySqlAndParams(sql.toString(),objList.toArray());

        return catelist;


    }

    /**
     *
     * 根据一级分类查询二级三级
     * @param ccompanyID
     * @param codePID
     * @return
     */
    public Map<String,Object> getProSubCate(String ccompanyID,String codePID){
          String hql = "from CcomCom where ccompanyId  = ?";
          CcomCom cc = (CcomCom) baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{ccompanyID});
          String sql = "select t.codevalue, t.codeid from Dtccode t where t.companyid = '' and t.codepid= ?";
          List<BaseBean> twolist = baseBeanDao.getListBeanBySqlAndParams(sql,new Object[]{cc.getComanyId(),codePID});

         return null;
    }

    /**
     *
     * 最近浏览
     * @param staffID
     * @return
     */
    public List<BaseBean> getRecentViewList(String staffID,String ccompanyID){
        String hql = "from RecentViewProCate where staffID = ? and ccompanyID = ? and rownum < ? order by viewDate desc";
        List<BaseBean>  viewlist = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{staffID,ccompanyID,new Long(8)});

        return  viewlist;

    }
    

    /**
    *
    * 最近浏览添加
    * @param staffID
    * @return
    */
    @Transactional
   public void addRecentView(String staffID,String ccompanyID,String codeID,String codeValue){
	   
	   String hql = "from RecentViewProCate where codeID = ? and ccompanyID = ?";
	   RecentViewProCate rp = (RecentViewProCate)baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{codeID,ccompanyID});
	   if(rp!=null){
		   
		   rp.setViewDate(new Date());
		   baseBeanDao.update(rp);
	   }else{
		   RecentViewProCate rpc = new RecentViewProCate();
		   rpc.setCcompanyID(ccompanyID);
		   rpc.setCodeID(codeID);
		   rpc.setCodeValue(codeValue);
		   rpc.setRvpID(serverService.getServerID("rvpID"));
		   rpc.setStaffID(staffID);
		   rpc.setViewDate(new Date());
		   baseBeanDao.save(rpc);
		   
	   }
	   
   }
    
    /**
     *
     * 获取展馆分类
     * @return
     */
    public Map<String,Object> getExhiProduct(String companyID){
    	Map<String,Object> mp = new HashMap<String,Object>();
    	String hql= "from PhlTopics where companyID = ? order by hotnum desc";
        List<BaseBean> phlist = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{companyID});
        String hqlp = "select goodsID,goodsName,ppid from dt_ProductPackaging where type = ? and goodsName like ? order by dbms_random.value";
        String hqlg = "from GoodFunction where goodsid = ?";
        GoodFunction goodFunction = null;
        
        for (int i = 0; i < phlist.size(); i++) {
        	PhlTopics pt = (PhlTopics)phlist.get(i);
		        List<BaseBean> prolist = baseBeanDao.getListBeanBySqlAndParams(hqlp,new Object[]{"会员分享","%"+pt.getTopic()+"%"});
		         if(prolist.size()>0){
		        Object p = (Object)prolist.get(0);
		        Object[] objs = (Object[])p;
		        
		        goodFunction = (GoodFunction) baseBeanService.getBeanByHqlAndParams(
		        		hqlg, new Object[]{objs[0].toString()});
		        String txturl = goodFunction.getUrl();
		        try {
		            String path1 = ServletActionContext.getServletContext()
		                    .getRealPath("\\");
		           String content = contentToFileService.getContent(path1
		                    + txturl);
		           if(content!=null&&!"".equals(content)){
		           String html = content.substring(content.indexOf("article_pro"));
		           List<String> listimg = getImageUrl(html);
		           if(listimg.size()==2){
		           listimg.add(objs[1].toString());
		           listimg.add(objs[2].toString());
		           
		           mp.put(pt.getTopic(), listimg);
		           }
		           }
		           
		        } catch (IOException e) {
		            logger.error("操作异常", e);
		        }
		         }
		}
        

        return mp;
    }
    

    /**
     * 获取展馆分类
     *
     * @return
     */
    public  Map<String,String> getAdvert(String companyID){
		Map<String, String> mp = new HashMap<String, String>();
		String sql = "select topic from dt_PhlTopics where companyID = ? order by dbms_random.value";
		List<Object> phlist = baseBeanDao.getListObjectBySqlAndParams(sql,
				new Object[] { companyID });
		String hqlp = "select image,ppid from dt_ProductPackaging where type = ? and goodsName like ? order by dbms_random.value";

		for (int i = 0; i < phlist.size(); i++) {
	
			Object pt = (Object) phlist.get(i);
			List<BaseBean> prolist = baseBeanDao.getListBeanBySqlAndParams(
					hqlp, new Object[] { "会员分享", "%" + pt.toString() + "%" });
			if (prolist.size() > 0) {
				Object p = (Object) prolist.get(0);
				Object[] objs = (Object[]) p;

				mp.put(objs[1].toString(), objs[0].toString());

			}
		}

		return mp;
    }

    

    /*** 
     * 获取ImageUrl地址 
     * 
     * @param HTML 
     * @return 
     */
    private List<String> getImageUrl(String content) { 
    	
			List<String> srcList = new ArrayList<String>(); //用来存储获取到的图片地址
			Pattern p = Pattern.compile("<(img|IMG)(.*?)(>|></img>|/>)");//匹配字符串中的img标签
			Matcher matcher = p.matcher(content);
			boolean hasPic = matcher.find();
			if(hasPic == true)//判断是否含有图片
			{
				while(hasPic) //如果含有图片，那么持续进行查找，直到匹配不到
				{
					String group = matcher.group(2);//获取第二个分组的内容，也就是 (.*?)匹配到的
					Pattern srcText = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");//匹配图片的地址
					Matcher matcher2 = srcText.matcher(group);
					if( matcher2.find() ) 
					{  
						if(srcList.size()<2){
						srcList.add( matcher2.group(3) );//把获取到的图片地址添加到列表中
						}else{
							break;
						}
					}
					hasPic = matcher.find();//判断是否还有img标签
				}
					
			}
			//logger.info("调试信息");
      return srcList; 
    } 
    

    /**
     *
     * 最新资讯
     * @param ccompanyID
     * @return
     */
    public List<BaseBean> getRecentNews(String ccompanyID){

        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();

        sql.append("select * from(select p.goodsname,p.image,p.ppid,cm.ccompany_id");
        sql.append(" from dt_productpackaging p");
        sql.append(" inner join dt_phl_platformpackage pc on p.ppid = pc.ppid");
        sql.append(" and pc.ccomidplatform = ? left join dt_ccom_com cm on cm.compnay_id = p.companyid");
        sql.append(" where p.type = ? and p.review = ?");
        sql.append("  order by p.packagingdate desc) t where rownum<8");
        objList.add(ccompanyID);
        objList.add("会员分享");
        objList.add("00");

        List<BaseBean> newslist = baseBeanDao.getListBeanBySqlAndParams(sql.toString(),objList.toArray());

        return newslist;
    }


    /**
     *
     * 首页有货拉物流
     */
    public List<BaseBean> getIndexLogisList(String companyID){


        return null;
    }


    /**
     * 获取首页商家
     * @return
     */
    public List<BaseBean> getIndexBusiComList(String ccomIDPlatform){
    	  List<BaseBean> list = new ArrayList<BaseBean>();
        if(ccomIDPlatform.equals(Constant.PHL_ID)){
        	 String  sql = "select tt.ccompanyid,tt.logopath,nvl(tt.brandinfo,' '),tt.companyname,nvl(tt.companyaddr,' '),m.compnay_id from (select t.companyid from dtCompany t where t.companymid in (select kk.companyid from Dtcompany kk where kk.industryid=?) order by dbms_random.value) t,dt_ccom_com m,Dtcontactcompany tt where rownum<? and  m.ccompany_id=tt.ccompanyid and m.compnay_id = t.companyid and tt.ccompanyId!= ?";
             list = baseBeanDao.getListBeanBySqlAndParams(sql,new Object[]{Constant.NMSC_ID,new Long(5),ccomIDPlatform});

        }else{
        	 String  sql = "select tt.ccompanyid,tt.logopath,nvl(tt.brandinfo,' '),tt.companyname,nvl(tt.companyaddr,' '),m.compnay_id from (select  t.companyid from dtCompany t where t.companymid = (select mm.compnay_id from Dt_Ccom_Com mm where mm.ccompany_id=?) order by dbms_random.value) t,dt_ccom_com m,Dtcontactcompany tt where rownum<? and  m.ccompany_id=tt.ccompanyid and m.compnay_id = t.companyid";
             list = baseBeanDao.getListBeanBySqlAndParams(sql,new Object[]{ccomIDPlatform,new Long(5)});

        }

        return list;
    }

    /**
     *
     * 获取首页市场
     * @param industryID
     * @return
     */
    public List<BaseBean> getIndexMarketList(String industryID,String staffID){
        StaffPosInfo sf = (StaffPosInfo)baseBeanDao.getBeanByHqlAndParams("from StaffPosInfo where staffID = ?",new Object[]{staffID});
        List<Object> objList = new ArrayList<Object>();
        if(sf!=null){
            objList.add(sf.getLongitude());
            objList.add(sf.getLatitude());
        }else{
            objList.add("");
            objList.add("");

        }
        String sql = "select d.ccompanyid,d.companyname,d.logopath,d.companyAddr,getdistance(?,?,d.accuracy,d.dimension) c from Dtcontactcompany d where d.industryid = ? and rownum < ? order by c";
        objList.add(industryID);
        objList.add(new Long(5));

        List<BaseBean> list = baseBeanDao.getListBeanBySqlAndParams(sql,objList.toArray());

        return list;
    }

    /**
     *
     * 获取素有农贸市场
     * @param industryID
     * @return
     */
    public PageForm  getPageFormMarket(int pageNumber,int pageSize,String industryID,String staffID){
        StaffPosInfo sf = (StaffPosInfo)baseBeanDao.getBeanByHqlAndParams("from StaffPosInfo where staffID = ?",new Object[]{staffID});
        List<Object> objList = new ArrayList<Object>();
        if(sf!=null){
            objList.add(sf.getLongitude());
            objList.add(sf.getLatitude());
        }else{
            objList.add("");
            objList.add("");

        }
        String sql = "select d.ccompanyid,d.companyname,d.logopath,d.companyAddr,getdistance(?,?,d.accuracy,d.dimension) c from Dtcontactcompany d where d.industryid = ? order by c";
        objList.add(industryID);


        PageForm pageForm = baseBeanService.getPageFormBySQL(
                pageNumber,pageSize, sql.toString(), "select count(*) from (" + sql + ")",
                objList.toArray());

        return pageForm;
    }


    /**
     *
     * 获取首页采购
     * @param ccompanyID
     * @return
     */
    public List<BaseBean> getIndexPurchaseList(String ccompanyID){


         return null;
    }



    /**
     *
     * 获取首页分类
     * @return
     */
    public Map<String,Object> getIndexProOrCate(String ccompanyID){
        String hql = "from CcomCom where ccompanyId = ?";
        CcomCom cc = (CcomCom)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{ccompanyID});
        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();
        sql.append("select t.codevalue, t.codeid");
        sql.append(" from dtCcode t, dt_phl_ProductCateHot h");
        sql.append(" where t.companyid = ?  and h.ccompanypid = ? and h.codeid = t.codeid and rownum < 15 order by dbms_random.value");
        objList.add(cc.getComanyId());
        objList.add(ccompanyID);
        List<BaseBean> list = baseBeanDao.getListBeanBySqlAndParams(sql.toString(),objList.toArray());
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("catelist",list);

        return map;
    }

    /**
     *
     * 首页分类商品
     * @param pageNumber
     * @param pageSize
     * @param cate
     * @return
     */
    public PageForm getIndexProduct(int pageNumber,int pageSize,String cate,String industryID,String companyID){
        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();

        sql.append("select pp.ppid,pp.goodsid,pp.goodsname,pp.image,pp.variableid,sp.wholesale*(1+nvl(sz.total_pct,0)/100),cm.companyaddr,cc.compnay_id,cc.ccompany_id");
        sql.append(" from dt_pro_wholesale sp  left join dt_productpackaging pp on sp.ppid = pp.ppid");
        sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = pp.companyid");
        sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
        if(cate!=null&&!cate.equals("")){
            sql.append(" right join dt_ProCateRelate pr on pr.ppid = pp.ppid and pr.codeid in (select c.codeid from Dtccode c where c.codepid=? and c.companyid = ?)");
            objList.add(cate);
            objList.add(companyID);
        }
        sql.append("  where cm.industryid = ? and  pp.showweixin = '01' order by dbms_random.value");
        objList.add(industryID);

        PageForm pageForm = baseBeanService.getPageFormBySQL(
                pageNumber,pageSize, sql.toString(), "select count(*) from (" + sql + ")",
                objList.toArray());

       return pageForm;
    }


    /**
     * 有车加入
     * @param carInformation
     * @param staff
     * @return
     */
    public String addCarJoin(CarInformation carInformation, Staff staff){

         return null;
    }
    /**
     * 查询往来单位信息
     * @param ccompanyID
     * @return
     */
    public Object getContactCom(String ccompanyID){

         String sql = "select cm.companyname,cc.compnay_id from Dt_Ccom_Com cc,Dtcontactcompany cm where cc.ccompany_id = cm.ccompanyid and cc.ccompany_id=?";
         Object jo = baseBeanDao.getObjectBySqlAndParams(sql,new Object[]{ccompanyID});

         return jo;
    }


}
