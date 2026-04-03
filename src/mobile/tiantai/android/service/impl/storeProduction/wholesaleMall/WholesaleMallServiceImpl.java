package mobile.tiantai.android.service.impl.storeProduction.wholesaleMall;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.StaffAddress;
import hy.ea.bo.production.AttriProduction;
import hy.ea.service.CLogBookService;
import hy.ea.util.Constant;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mobile.tiantai.android.bean.wholesaleMall.AttriProductionBean;
import mobile.tiantai.android.bean.wholesaleMall.PfscShoppingCartBean;
import mobile.tiantai.android.bean.wholesaleMall.WholesaleMallBean;
import mobile.tiantai.android.bo.scMobile.PfscShoppingCart;
import mobile.tiantai.android.service.storeProduction.wholesaleMall.WholesaleMallService;
import mobile.tiantai.android.util.MapSesssionUtil;
import mobile.tiantai.android.util.PriceArithUtil;
import net.sf.json.JSONObject;

import org.hibernate.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tiantai.wfj.bo.TEshopCustomer;

/**
 * 批发商城serviceImpl
 * Created by lyc on 2018-10-08.
 */
@Service
public class WholesaleMallServiceImpl implements WholesaleMallService {

    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;
    //订单日志service
    @Resource
    private CLogBookService logBookService;

    private String intIds = "scode20190415raqvqk3uvs0000000762";

    /**
     * 根据公司id查询公司
     *
     * @param ccompanyID 公司id
     * @return 公司对象
     * @throws Exception
     */
    private Company getCompany(String ccompanyID) throws Exception {
        //ccompanyID不为空则是选择了公司，否则默认公司id
        //ccompanyID = StringHelper.isNotEmpty(ccompanyID) ? ccompanyID:"contactCompany20101230UB4U5884S30000000176";
        return (Company) baseBeanDao.getBeanByHqlAndParams("select c from Company c ,CcomCom t where c.companyID=t.comanyId and t.ccompanyId=?", new Object[]{ccompanyID});
    }

    /**
     *根据行业类别查询往来公司，根据往来公司查询公司id
     * @codePID 行业id
     * @return
     * @throws Exception
     */
    private String getComIdsSql()throws  Exception{
        //根据行业类别查询往来公司，根据往来公司查询公司id
        StringBuilder comSql = new StringBuilder();
        comSql.append(" select c.companyid from Dtcompany c ,dt_ccom_com  t,dtcontactcompany o ");
        comSql.append(" where c.companyid =t.compnay_id and  t.ccompany_id = o.ccompanyid and o.industryid = ?  " );
        return comSql.toString();
    }

    /**
     * 获取一级分类sql语句
     * @param term 查询条件
     * @return
     * @throws Exception
     */
    private String getOneGoodsSql(String term)throws  Exception{
        StringBuilder sql = new StringBuilder("select distinct y.codeid,y.codevalue,y.iconpath,y.codepid from DTCCODE y ,");
        sql.append(" (select distinct(c.codepid) from (select distinct(pcr.codeid)  from dt_ProCateRelate pcr left join dt_productpackaging p on p.ppid = pcr.ppid right join dt_pro_wholesale dpw on p.ppid = dpw.ppid and dpw.state = '00'");
        sql.append("  where p.showweixin = '01' and pcr.companyid in("+term+")) dc left join Dtccode c on c.codeid = dc.codeid where c.companyid in("+term+")) code");
        sql.append(" where code.codepid = y.codeid  and y.companyid in("+term+")");
        return sql.toString();
    }

    /**
     * 获取二级分类sql语句
     * @param term 查询条件
     * @return
     * @throws Exception
     */
    private String getTwoGoodsSql(String term)throws  Exception{
        StringBuilder sql = new StringBuilder("select distinct y.codeid,y.codevalue,y.codepid from dtScode y ,");
        sql.append("(select distinct ej.codeid from dtScode ej inner join dt_ProCateRelate pcr on ej.codeid = pcr.codeid");
        sql.append(" left join dt_productpackaging p on p.ppid=pcr.ppid  right join dt_pro_wholesale dpw on p.ppid = dpw.ppid and dpw.state = '00' where p.showweixin ='01'and pcr.companyid in("+term+")) code ");
        sql.append(" where code.codeid =y.codeid");
        return sql.toString();
    }

    /**
     * 获取三级分类sql语句
     * @param term 查询条件
     * @return
     * @throws Exception
     */
    private StringBuilder getThreeGoodsSql(String term)throws  Exception{
        StringBuilder sql = new StringBuilder();
        //商品类型id非空校验
        sql.append(" select dpp.ppid, dpw.wholesale, dpp.goodsid, dpp.goodsName,dpp.image,dpp.companyid,dpp.standard,");
        sql.append(" ROUND(dpw.wholesale*(nvl(sz.total_pct,0)/100),2) wholesalePrice,dpc.codeid,dpw.wholesaleId,dpp.barcode");//活动价=批发价*批发价的倍数
        sql.append(" from dt_ProductPackaging dpp");//查询产品表
        sql.append(" inner join dt_ProCateRelate dpc on dpp.ppid = dpc.ppid");//产品表主键id=产品和类目关联表产品id
        sql.append(" left join dt_pro_wholesale dpw on dpp.ppid = dpw.ppid and dpw.state = '00'");//产品表主键id=批发价格表产品id和批发价格表状态为未删除的
        sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = dpp.companyid");//往来单位公司关系中公司业务id = 产品表中公司id
        sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid ");//往来单位公司关系中往来单位id = 往来单位主键id
        sql.append(" left join dt_set_subsidize sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");//红包类中的行业分类=往来单位中的行业类别和活动状态为正常的
        sql.append(" where dpp.showweixin='01' ");//在微商店显示
        sql.append(" and dpw.wholesale is not null ");//批发价不为空
        sql.append(" and dpp.companyid in("+term+") ");
        return sql;
    }
    
    
    
    /**
     * 全部商品
     * @param term 查询条件
     * @return
     * @throws Exception
     */
    private StringBuilder getALLGoodsSql(String term)throws  Exception{
        StringBuilder sql = new StringBuilder();
        //商品类型id非空校验
        sql.append(" select dpp.ppid, dpw.wholesale, dpp.goodsid, dpp.goodsName,dpp.image,dpp.companyid,dpp.standard,");
        sql.append(" ROUND(dpw.wholesale*(nvl(sz.total_pct,0)/100),2) wholesalePrice,dpw.wholesaleId,dpw.wholesaleId,dpp.barcode");//活动价=批发价*批发价的倍数
        sql.append(" from dt_ProductPackaging dpp");//查询产品表
        sql.append(" left join dt_pro_wholesale dpw on dpp.ppid = dpw.ppid and dpw.state = '00'");//产品表主键id=批发价格表产品id和批发价格表状态为未删除的
        sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = dpp.companyid");//往来单位公司关系中公司业务id = 产品表中公司id
        sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid ");//往来单位公司关系中往来单位id = 往来单位主键id
        sql.append(" left join dt_set_subsidize sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");//红包类中的行业分类=往来单位中的行业类别和活动状态为正常的
        sql.append(" where dpp.showweixin='01' ");//在微商店显示
        sql.append(" and dpw.wholesale is not null ");//批发价不为空
        sql.append(" and dpp.companyid in("+term+") ");
        return sql;
    }

    public String ajaxGetOneGoodsClassify(String ccompanyID, String codePID) throws Exception {
        List<BaseBean> goodsClassifyList = null;//商品分类
        if (StringHelper.isNotEmpty(ccompanyID)) {
            //ccompanyId为all,全部商家
            String selectSql = "";
            if(!"all".equals(ccompanyID)){//不为全部商家
                //根据公司id查询公司
                Company company = getCompany(ccompanyID);
                if (company != null && StringHelper.isNotEmpty(company.getCompanyID()) && StringHelper.isNotEmpty(codePID)) {
                    //通过判断获取相应商品分类
                    if (codePID.equals("scode20190415raqvqk3uvs0000000762")) {//查询商品一级分类
                        selectSql = getOneGoodsSql("?");//获取一级分类sql语句
                        goodsClassifyList = this.baseBeanDao.getListBeanBySqlAndParams(selectSql, new Object[]{company.getCompanyID(), company.getCompanyID(), company.getCompanyID()});
                    }
                }
            }else{//为全部商家
                if (StringHelper.isNotEmpty(codePID)) {
                    //通过判断获取相应商品分类
                    if (codePID.equals("scode20190415raqvqk3uvs0000000762")) {//查询商品一级分类
                        //查询一级分类
                        selectSql = getOneGoodsSql(getComIdsSql());//获取一级分类sql语句
                        goodsClassifyList = this.baseBeanDao.getListBeanBySqlAndParams(selectSql,  new Object[]{codePID, codePID,codePID});
                    }
                }
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodsClassifyList", goodsClassifyList);
        JSONObject obj = JSONObject.fromObject(map);
        return obj.toString();
    }
    
    /**
     * 
     * 
     * @param ccompanyID
     * @param codePID
     * @return
     * @throws Exception
     */
    public String ajaxGetOneGoodsClassifyPlat(String ccompanyID, String codePID) throws Exception{
    	String term = "";
    	List<Object> params = new ArrayList<Object>();
    	if(ccompanyID.equals(Constant.PHL_ID)){
    		
    		  term = "select c.companyid from Dtcompany c where c.industryid = ?";
    		  params.add(codePID);
    		  params.add(codePID);
    		  params.add(Constant.PHL_ID);
    	}else{
    		 term = "select c.companyid from Dtcompany c where c.companymid = (select m.compnay_id from DT_ccom_com m where m.ccompany_id = ?)";
    		 params.add(ccompanyID);
    		 params.add(ccompanyID);
    		 params.add(Constant.PHL_ID);
    		
    	}
    	
        List<BaseBean> goodsClassifyList = null;//商品分类
        StringBuilder sql = new StringBuilder("select distinct y.codeid,y.codevalue,y.iconpath,y.codepid from DTCCODE y ,");
        sql.append(" (select distinct(c.codepid) from (select distinct(pcr.codeid)  from dt_ProCateRelate pcr left join dt_productpackaging p on p.ppid = pcr.ppid right join dt_pro_wholesale dpw on p.ppid = dpw.ppid and dpw.state = '00'");
        sql.append("  where p.showweixin = '01' and pcr.companyid in("+term+")) dc left join Dtccode c on c.codeid = dc.codeid where c.companyid in("+term+")) code");
        sql.append(" where code.codepid = y.codeid  and y.companyid  = (select m.compnay_id from DT_ccom_com m where m.ccompany_id = ?)");
       
        
        goodsClassifyList = this.baseBeanDao.getListBeanBySqlAndParams(sql.toString(),  params.toArray());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodsClassifyList", goodsClassifyList);
        JSONObject obj = JSONObject.fromObject(map);
        return obj.toString();
    }

    public String ajaxGetTwoGoodsClassify(String ccompanyID, String codePID,String ccomIDPlatform) throws Exception {
        List<BaseBean> goodsClassifyList = null;//商品分类
        String selectSql = "";
        if (StringHelper.isNotEmpty(ccompanyID)) {
            if(!"all".equals(ccompanyID)) {//不为全部商家
                //根据公司id查询公司
                Company company = getCompany(ccompanyID);
                if (company != null && StringHelper.isNotEmpty(company.getCompanyID()) && StringHelper.isNotEmpty(codePID)) {
                    //通过判断获取相应商品分类
                    selectSql = getTwoGoodsSql("?");
                    if (!codePID.equals("scode20190415raqvqk3uvs0000000762") && !codePID.equals("all")) {//根据一级分类查询商品
                    	 if(ccomIDPlatform!=null&&!ccomIDPlatform.equals("")){
                           	String term = "";
                           	List<Object> param = new ArrayList<Object>();
                           	if(ccomIDPlatform.equals(Constant.PHL_ID)){
                           		//说明是总平台 、、如果以后有其他平台这个地方需要修改
                           		//查询该平台下的所有商家的产品
                           		term = "select c.companyid from Dtcompany c where c.industryid = ?"; 
                           	    param.add(Constant.NY_ID);
                           	  	
                                  
                           	}else{
                           		//总平台下的市场，
                           		//查询该市场下的所有商家的产品
                           		 term = "select c.companyid from Dtcompany c where c.companymid = (select m.compnay_id from DT_ccom_com m where m.ccompany_id = ?)";
                           		param.add(ccomIDPlatform);
                           	
                           		
                           	}
                           	
                           	   selectSql = getTwoGoodsSql(term)+" and y.codepid = ? ";
                             	param.add(codePID);
                           	 
                              goodsClassifyList = this.baseBeanDao.getListBeanBySqlAndParams(selectSql,param.toArray());

                           }else{
                             selectSql = selectSql+" and y.codepid = ? ";
                            goodsClassifyList = this.baseBeanDao.getListBeanBySqlAndParams(selectSql, new Object[]{company.getCompanyID(), company.getCompanyID(), codePID});
                           }
                           
                     }else if(codePID.equals("all")){//全部分类
                    	  if(ccomIDPlatform!=null&&!ccomIDPlatform.equals("")){
                          	String term = "";
                          	List<Object> param = new ArrayList<Object>();
                          	if(codePID.equals("all")){
                          		//说明是总平台 、、如果以后有其他平台这个地方需要修改
                          		//查询该平台下的所有商家的产品
                          		  term = "select c.companyid from Dtcompany c where c.industryid = ?"; 
                          	    	param.add(Constant.NY_ID);
                          	//  	param.add(Constant.NY_ID);
                                 
                          	}else{
                          		//总平台下的市场，
                          		//查询该市场下的所有商家的产品
                          		 term = "select c.companyid from Dtcompany c where c.companymid = (select m.compnay_id from DT_ccom_com m where m.ccompany_id = ?)";
                          		param.add(ccomIDPlatform);
                          	//	param.add(ccomIDPlatform);
                          		
                          	}
                          	
                          	 selectSql = getTwoGoodsSql(term);
                          	 
                             goodsClassifyList = this.baseBeanDao.getListBeanBySqlAndParams(selectSql,param.toArray() );

                          }else{
                        	  
                              goodsClassifyList = this.baseBeanDao.getListBeanBySqlAndParams(selectSql, new Object[]{company.getCompanyID()});
  
                          }
                    	
                    	
                    }
                }
            }else{
                //String intIds = "scode20190415raqvqk3uvs0000000762";
                selectSql = getTwoGoodsSql(getComIdsSql());//通过判断获取相应商品分类
                if (!codePID.equals("scode20190415raqvqk3uvs0000000762") && !codePID.equals("all")) {//根据一级分类查询商品
                    selectSql = selectSql+" and y.codepid = ? ";
                    goodsClassifyList = this.baseBeanDao.getListBeanBySqlAndParams(selectSql, new Object[]{intIds,codePID});
                }else if(codePID.equals("all")){//全部分类
                    goodsClassifyList = this.baseBeanDao.getListBeanBySqlAndParams(selectSql, new Object[]{intIds});
                }
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodsClassifyList", goodsClassifyList);
        JSONObject obj = JSONObject.fromObject(map);
        return obj.toString();
    }

    public Map<String, Object> ajaxGetSupermarketGoodsList(String ccompanyID, String codePID, String search, String industryType,String ccomIDPlatform,String staffId,String companyId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        //1.参数非空校验
        if (StringHelper.isNotEmpty(ccompanyID)) {
            //2.根据公司id查询公司
            Company company = getCompany(ccompanyID);
            List<Object> parms = new ArrayList<Object>();
            //3.公司非空校验
            if ("all".equals(ccompanyID) || (company != null && StringHelper.isNotEmpty(company.getCompanyID()))) {
                StringBuilder sql = new StringBuilder();
                
                if(ccomIDPlatform!=null&&!ccomIDPlatform.equals("")){
                	String term = "";
                	if(ccomIDPlatform.equals(Constant.PHL_ID)){
                		//说明是总平台 、、如果以后有其他平台这个地方需要修改
                		//查询该平台下的所有商家的产品
                		  term = "select c.companyid from Dtcompany c where c.industryid = ?"; 
                          parms.add(Constant.NY_ID);
                	}else{
                		//总平台下的市场，
                		//查询该市场下的所有商家的产品
                		 term = "select c.companyid from Dtcompany c where c.companymid = (select m.compnay_id from DT_ccom_com m where m.ccompany_id = ?)";
                		 parms.add(ccomIDPlatform);
                	}
                	
                	 if (!"all".equals(codePID)) {//值为all为查询时传的参数
                		 sql = getThreeGoodsSql(term);
                	 }else{
                		 sql = getALLGoodsSql(term);
                	 }
                	
                	 
                }else{
                //商品类型id非空校验
                if (StringHelper.isNotEmpty(codePID)) {//按商品分类id查询商品
                    if("all".equals(ccompanyID)){//全部商家
                        sql = getThreeGoodsSql(getComIdsSql());//根据行业类别查询往来公司，根据往来公司查询公司id
                        //String intIds = "scode20190415raqvqk3uvs0000000762";
                        parms.add(intIds);
                    }else{//单一商家
                    	 if (!"all".equals(codePID)) {//值为all为查询时传的参数
                    		 sql = getThreeGoodsSql("?");
                             parms.add(company.getCompanyID());
                         }else{
                        	 sql = getALLGoodsSql("?");
                             parms.add(company.getCompanyID());
                        	 
                         }
                        
                    }
          
                }
         
                }
                //商品名称非空校验
                if (StringHelper.isNotEmpty(search)) {
                    sql.append(" and dpp.goodsname like ?");
                    parms.add("%" + search.trim() + "%");
                }
                if (!"all".equals(codePID)) {//值为all为查询时传的参数
                    sql.append(" and dpc.codeid = ?");
                    parms.add(codePID);
                }
                List<BaseBean> beanList = baseBeanDao.getListBeanBySqlAndParams(sql.toString(), parms.toArray());
                //循环获取goodsid，依据goodsid查询颜色分类
                Object[] objs;
                double allPrice = 0;
                WholesaleMallBean mallBean = null;
                List<WholesaleMallBean> mallBeanList = new ArrayList<WholesaleMallBean>();
                for (int i = 0, j = beanList.size(); i < j; i++) {
                    objs = (Object[]) (Object) beanList.get(i);
                    //计算总价
                    allPrice = PriceArithUtil.add(objs[1] != null ? Double.valueOf(objs[1].toString().trim()) : 0, objs[7] != null ? Double.valueOf(objs[7].toString().trim()) : 0);
                    mallBean = new WholesaleMallBean(objs[0] != null ? objs[0].toString() : null, objs[1] != null ? Double.valueOf(objs[1].toString().trim()) : 0, objs[2] != null ? objs[2].toString() : null,
                            objs[3] != null ? objs[3].toString() : null, objs[4] != null ? objs[4].toString() : null, objs[5] != null ? objs[5].toString() : null,
                            objs[6] != null ? objs[6].toString() : null, objs[7] != null ? Double.valueOf(objs[7].toString().trim()) : 0, allPrice);
                    mallBean.setCodeId(objs[8] != null ? objs[8].toString() : null);
                    mallBean.setWholesaleId(objs[9] != null ? objs[9].toString() : null);
                    mallBean.setBarCode(objs[10] != null ? objs[10].toString() : null);//条码
                    mallBeanList.add(mallBean);
                }
                map.put("mallBeanList", mallBeanList);
                map.put("companyId", "all".equals(ccompanyID) ? "all":company.getCompanyID());
                //获取购物车信息
                getShopCartInfor(map, null,staffId,companyId);
            } else {
                map.put("mallBeanList", null);
            }
        } else {
            map.put("mallBeanList", null);
        }
        return map;
    }

    public String ajaxGetGGFlList(String goodsId) throws Exception {
        //根据货物id查询产品规格分类
        String attriSql = "from AttriProduction o where o.goodsid = '" + goodsId + "'";
        List<BaseBean> beanList = baseBeanDao.getListBeanByHqlAndParams(attriSql, null);
        //拼接bean集合
        AttriProduction attri = null;
        AttriProductionBean attriBean = null;
        List<AttriProductionBean> attriBeanList = new ArrayList<AttriProductionBean>();
        for (int i = 0, j = beanList.size(); i < j; i++) {
            attri = (AttriProduction) beanList.get(i);
            attriBean = new AttriProductionBean(attri.getApid(), attri.getAttriname(), attri.getAttrivalue(), attri.getImgurl(), attri.getGoodsid(), attri.getType(), attri.getSort());
            attriBeanList.add(attriBean);
        }
        //存入map返回
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("attriBeanList", attriBeanList);
        JSONObject obj = JSONObject.fromObject(map);
        return obj.toString();
    }
    
    /**
     * 
     * 根据产品ID获取产品详细信息
     * @param pfscShoppingCart
     * @return
     */
    
    
    public  PfscShoppingCart getProCartInfo(PfscShoppingCart pfscShoppingCart){
    	
    	PfscShoppingCart pfs = new PfscShoppingCart();
    	pfs.setPpid(pfscShoppingCart.getPpid());
    	pfs.setStandard(pfscShoppingCart.getStandard());
    	pfs.setTjNum(pfscShoppingCart.getTjNum());
    	pfs.setFtStr(pfscShoppingCart.getFtStr());
    	pfs.setTjFlag(pfscShoppingCart.getTjFlag());
    	
    	String hql = "from ProductPackaging p where p.ppID = ?";
    	ProductPackaging pp = (ProductPackaging)baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{pfscShoppingCart.getPpid()});
    	pfs.setGoodsName(pp.getGoodsName());
    	pfs.setGoodsid(pp.getGoodsID());
    	pfs.setCompanyid(pp.getCompanyID());
    	pfs.setBarCode(pp.getBarCode());
    	pfs.setImage(pp.getImage());
    
    	
    	
    	return pfs;
    	
    	
    }

    @Transactional
    public String ajaxAddShoppingCart(PfscShoppingCart shoppingCartFrom,String companyId,String staffId) throws Exception {
        //1.获取session数据
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        if(parmaInfor!=null){
        	  //2.根据手机端传过来的staffid查询状态为在职的account表数据
            companyId = parmaInfor.get("companyId").toString();
             staffId = parmaInfor.get("staffId").toString();
            // 3.通过staffid查找account用户信息
        }
      
        String sql = " from CAccount o where o.companyID = ? and o.staffID = ? and o.accountStatus = '00'";
        CAccount account = (CAccount) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{companyId, staffId});
        //4.根据传过来的codeId、goodsid、tjFlag、staffComId、staffId查询购物车信息
        if (shoppingCartFrom.getTjFlag() == 0) {//0:直接添加
            //String scSql = "from PfscShoppingCart o where o.codeId = ? and o.goodsid = ? and o.tjFlag = ? and o.staffComId = ? and o.staffId= ? ";
            //PfscShoppingCart shoppingCart = (PfscShoppingCart) baseBeanDao.getBeanByHqlAndParams(scSql, new Object[]{shoppingCartFrom.getCodeId(), shoppingCartFrom.getGoodsid(), 0, companyId, staffId});
            String scSql = "from PfscShoppingCart o where o.goodsid = ? and o.tjFlag = ? and o.staffComId = ? and o.staffId= ? ";
            PfscShoppingCart shoppingCart = (PfscShoppingCart) baseBeanDao.getBeanByHqlAndParams(scSql, new Object[]{shoppingCartFrom.getGoodsid(), 0, companyId, staffId});
            if (shoppingCart != null) {//已有数据
                String parameter = "修改批发商城购物车（购物车id：" + shoppingCart.getPscId() + "）";
                //4.1.1 修改购物车信息
                updateShoppingCart(shoppingCart, shoppingCartFrom);
                //4.1.2 创建保存/更新订单信息
                saveOrUpdateShopCart(shoppingCart, companyId, parameter, account);
                return shoppingCart.getPscId();
            } else {
                //4.2.1 填充购物车其他信息
                addShoppingCart(shoppingCartFrom, companyId, staffId);
                String parameter = "添加批发商城购物车（购物车id：" + shoppingCartFrom.getPscId() + "）";
                //4.2.2 创建保存/更新订单信息
                saveOrUpdateShopCart(shoppingCartFrom, companyId, parameter, account);
                return shoppingCartFrom.getPscId();
            }
        } else {//1：选中产品规格、颜色添加
            StringBuffer scSql = new StringBuffer("from PfscShoppingCart o  where 1=1 ");
            //scSql.append(" and o.codeId = ? ");
            scSql.append(" and o.goodsid = ? ");
            scSql.append(" and o.tjFlag = ? ");
            scSql.append(" and o.staffComId = ? ");
            scSql.append(" and o.staffId = ? ");
            scSql.append(" and o.standard = ? ");
//            scSql.append(" and o.cmApid " + (StringHelper.isNotEmpty(shoppingCartFrom.getCmApid()) ? "= '" + shoppingCartFrom.getCmApid() + "'" : "is null"));
//            scSql.append(" and o.ysApid " + (StringHelper.isNotEmpty(shoppingCartFrom.getYsApid()) ? "= '" + shoppingCartFrom.getYsApid() + "'" : "is null"));
//            scSql.append(" and o.ftApid " + (StringHelper.isNotEmpty(shoppingCartFrom.getFtApid()) ? "= '" + shoppingCartFrom.getFtApid() + "'" : "is null"));
//            scSql.append(" and o.spApid " + (StringHelper.isNotEmpty(shoppingCartFrom.getSpApid()) ? "= '" + shoppingCartFrom.getSpApid() + "'" : "is null"));
            //Object[] params = new Object[]{shoppingCartFrom.getCodeId(), shoppingCartFrom.getGoodsid(), 1, companyId, staffId};
            Object[] params = new Object[]{shoppingCartFrom.getGoodsid(), 1, companyId, staffId,shoppingCartFrom.getStandard()};
            PfscShoppingCart shoppingCart = (PfscShoppingCart) baseBeanDao.getBeanByHqlAndParams(scSql.toString(), params);
            String parameter = null;
            String pscId = "";
            if (shoppingCart != null) {
                // 修改购物车信息
                updateShoppingCart(shoppingCart, shoppingCartFrom);
                parameter = "修改批发商城购物车（购物车id：" + shoppingCart.getPscId() + "）";
                // 创建保存/更新订单信息
                saveOrUpdateShopCart(shoppingCart, companyId, parameter, account);
                pscId = shoppingCart.getPscId();
            } else {
                //4.2.1 填充购物车其他信息
                addShoppingCart(shoppingCartFrom, companyId, staffId);
                parameter = "添加批发商城购物车（购物车id：" + shoppingCartFrom.getPscId() + "）";
                //4.1.2 创建保存/更新订单信息
                saveOrUpdateShopCart(shoppingCartFrom, companyId, parameter, account);
                pscId = shoppingCartFrom.getPscId();
            }
            return pscId;
        }
    }

    /**
     * 判断已保存购物车中的数据与传过来的数据是否一致
     *
     * @param shoppingCart     查询出的以保存购物车信息
     * @param shoppingCartFrom 页面传过来的货物信息
     * @throws Exception
     */
    private boolean addOrUpdateJudge(PfscShoppingCart shoppingCart, PfscShoppingCart shoppingCartFrom) {
        boolean judgeFlag = false;//判断标识
        int judgeNum = 0;//差异数
        //1.查询出的规格不为空并且传过来的规格不为空并且查询出的规格不等于传过来的规格
        if ((StringHelper.isNotEmpty(shoppingCartFrom.getCmApid()) && StringHelper.isNotEmpty(shoppingCart.getCmApid()) && !shoppingCart.getCmApid().equals(shoppingCartFrom.getCmApid()))) {
            judgeNum++;
        }
        //2.查询出的颜色不为空并且传过来的颜色不为空并且查询出的颜色不等于传过来的颜色
        if ((StringHelper.isNotEmpty(shoppingCartFrom.getYsStr()) && StringHelper.isNotEmpty(shoppingCart.getYsStr()) && !shoppingCart.getYsStr().equals(shoppingCartFrom.getYsStr()))) {
            judgeNum++;
        }
        //3.查询出的副图不为空并且传过来的副图不为空并且查询出的副图不等于传过来的副图
        if ((StringHelper.isNotEmpty(shoppingCartFrom.getFtApid()) && StringHelper.isNotEmpty(shoppingCart.getFtApid()) && !shoppingCart.getFtApid().equals(shoppingCartFrom.getFtApid()))) {
            judgeNum++;
        }
        //4.查询出的视频不为空并且传过来的视频不为空并且查询出的视频不等于传过来的视频
        if ((StringHelper.isNotEmpty(shoppingCartFrom.getSpApid()) && StringHelper.isNotEmpty(shoppingCart.getSpApid()) && !shoppingCart.getSpApid().equals(shoppingCartFrom.getSpApid()))) {
            judgeNum++;
        }
        if (judgeNum > 0) {
            judgeFlag = true;
        }
        return judgeFlag;
    }


    /**
     * 修改购物车信息
     *
     * @param shoppingCartFrom 页面传过来的货物信息
     * @param companyId        登录人公司id
     * @param staffId          登录人id
     * @throws Exception
     */
    private void addShoppingCart(PfscShoppingCart shoppingCartFrom, String companyId, String staffId) throws Exception {
        //4.2.1 填充购物车其他信息
        shoppingCartFrom.setPscId(serverService.getServerID("pfscsc"));//批发商城购物车id
        shoppingCartFrom.setCreateDate(new Date());//创建时间
        //shoppingCartFrom.setTjNum(1);//添加数量
        shoppingCartFrom.setStaffComId(companyId);//登录人公司id
        shoppingCartFrom.setStaffId(staffId);//登录人id
    }

    /**
     * 修改购物车信息
     *
     * @param shoppingCart     查询出的以保存购物车信息
     * @param shoppingCartFrom 页面传过来的货物信息
     * @throws Exception
     */
    private void updateShoppingCart(PfscShoppingCart shoppingCart, PfscShoppingCart shoppingCartFrom) throws Exception {
        //4.2修改购物车数据
        shoppingCart.setTjNum(shoppingCart.getTjNum() + shoppingCartFrom.getTjNum());//添加个数加一
        shoppingCart.setCodeId(shoppingCartFrom.getCodeId());//二级商品分类id
        shoppingCart.setPpid(shoppingCartFrom.getPpid());//产品id
        shoppingCart.setWholesale(shoppingCartFrom.getWholesale());//批发价
        shoppingCart.setWholesaleId(shoppingCartFrom.getWholesaleId());//批发价id
        shoppingCart.setTotalWhoPrice(shoppingCartFrom.getTotalWhoPrice());//使用红包后增幅价格
        shoppingCart.setAllPrice(shoppingCartFrom.getAllPrice());//总价=批发价+使用红包后增幅价格
        shoppingCart.setGoodsid(shoppingCartFrom.getGoodsid());//货物id
        shoppingCart.setGoodsName(shoppingCartFrom.getGoodsName());//货物名称
        shoppingCart.setImage(shoppingCartFrom.getImage());//图片路径
        shoppingCart.setCompanyid(shoppingCartFrom.getCompanyid()); //公司id
        shoppingCart.setStandard(shoppingCartFrom.getStandard());//规格
        //有产品规格及颜色
        shoppingCart.setCmStr(shoppingCartFrom.getCmStr());//选中尺码
        shoppingCart.setCmApid(shoppingCartFrom.getCmApid());//选中尺码apid
        shoppingCart.setYsStr(shoppingCartFrom.getYsStr());//选中颜色
        shoppingCart.setYsApid(shoppingCartFrom.getYsApid());//选中颜色apid
        shoppingCart.setFtStr(shoppingCartFrom.getFtStr());//选中副图
        shoppingCart.setFtApid(shoppingCartFrom.getFtApid());//选中副图apid
        shoppingCart.setSpStr(shoppingCartFrom.getSpStr());//选中视频
        shoppingCart.setSpApid(shoppingCartFrom.getSpApid());//选中视频apid
        shoppingCart.setTjFlag(shoppingCartFrom.getTjFlag());//添加标识
        shoppingCart.setCreateDate(new Date());//创建时间
        shoppingCart.setBarCode(shoppingCartFrom.getBarCode());//条码
        shoppingCart.setYqNum(shoppingCartFrom.getYqNum());//已取数量
    }

    /**
     * 保存或更新购物车数据库信息
     *
     * @param shoppingCart 需操作的购物车数据
     * @param companyId    公司id
     * @param parameter    信息
     * @param account      账号
     * @throws Exception
     */
    public void saveOrUpdateShopCart(PfscShoppingCart shoppingCart, String companyId, String parameter, CAccount account) throws Exception {
        //4.1.2 创建保存订单的bean
        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        baseBeanList.add(shoppingCart);
        //4.1.3 记录日志
        /// CLogBook logBook = logBookService.saveCLogBook(companyId, parameter, account);
        // baseBeanList.add(logBook);
        //4.1.4 保存表
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList, null, null);
    }


    @Transactional
    public void getUpShopCartInfor(WholesaleMallBean mallBean, PfscShoppingCart shoppingCart, String companyId, CAccount account) throws Exception {
        String parameter = "修改批发商城购物车（购物车id：" + shoppingCart.getPscId() + "）";
        shoppingCart.setWholesale(mallBean.getWholesale());//批发价
        shoppingCart.setWholesaleId(mallBean.getWholesaleId());//批发价id
        shoppingCart.setTotalWhoPrice(mallBean.getTotalWhoPrice());//使用红包后增幅价格
        shoppingCart.setAllPrice(mallBean.getAllPrice());////总价=批发价+使用红包后增幅价格
        shoppingCart.setYqNum(0);//已取数量
        // 创建保存/更新订单信息
        saveOrUpdateShopCart(shoppingCart, companyId, parameter, account);
    }

    /**
     * 拼接批发商城购物车与批发价表查询sql
     * @return
     * @throws Exception
     */
    private StringBuilder getPfscSelectSql()throws  Exception{
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        sb.append("o.psckey,o.pscid,o.codeid,o.ppid, ");
        //原批发商城购物车上直接取的金额
        //sb.append("o.wholesale,o.totalwhoprice,o.allprice,");
        //现从批发价格表中取值
        sb.append("dpw.wholesale, ");//批发价
        sb.append("ROUND(dpw.wholesale*(nvl(sz.total_pct,0)/100),2) totalwhoprice, ");//活动价
        sb.append("ROUND(dpw.wholesale+ROUND(dpw.wholesale*(nvl(sz.total_pct,0)/100),2),2) allprice, ");//总价
        sb.append("o.goodsid,o.goodsname,o.image,o.companyid,o.standard,o.tjnum,o.tjflag, ");
        sb.append("o.cmstr,o.cmapid,o.ysstr,o.ysapid,o.ftstr,o.ftapid,o.spstr,o.spapid, ");
        sb.append("o.imgurl,o.type,o.sort,o.createdate,o.staffcomid,o.staffid,o.wholesaleid, ");
        sb.append("o.barcode,o.yqnum ");
        sb.append("from dt_pfscshoppingcart o  ");
        sb.append("left join dt_pro_wholesale dpw on o.ppid = dpw.ppid and dpw.state = '00' ");
        sb.append("left join Dt_Ccom_Com cc on cc.compnay_id = o.companyid ");
        sb.append("left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid ");
        sb.append("left join dt_set_subsidize sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");
        return sb;
    }

    public String getShopCartInfor(Map<String, Object> map, String pscId,String staffId,String companyId) throws Exception {
        //1.获取session数据
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        //2.获取购物车商品个数、金额总数
        StringBuilder sb = getPfscSelectSql();
        sb.append("where o.staffComId = ? and o.staffId= ? ");
        List<Object> parms = new ArrayList<Object>();
        if(companyId!=null&&!companyId.equals("")){
        	
        	parms.add(companyId);
        	
        }else{
        	 parms.add(parmaInfor.get("companyId").toString());
        }
        
        if(staffId!=null&&!staffId.equals("")){
        	
        	parms.add(staffId);
        }else{
        	
        	 parms.add(parmaInfor.get("staffId").toString());
        }

       
        List<BaseBean> shoppingCartList = baseBeanDao.getListBeanBySqlAndParams(sb.toString(), parms.toArray());
        Object[] objs = null;
        int shopNum = 0;
        double shopPrice = 0;
        for (int i = 0, j = shoppingCartList.size(); i < j; i++) {
            objs = (Object[]) (Object) shoppingCartList.get(i);
            //计算总价
            shopNum += objs[12] != null ?  Integer.valueOf(objs[12].toString().trim()) : 0;//购物车总数
            shopPrice = PriceArithUtil.add(shopPrice, PriceArithUtil.mul(objs[6] != null ? Double.valueOf(objs[6].toString().trim()) : 0, objs[12] != null ? Integer.valueOf(objs[12].toString().trim()) : 0));//购物车总金额
        }
        map.put("shoppingCartList", shoppingCartList);
        map.put("shopNum", shopNum);//购物车总数
        map.put("shopPrice", shopPrice);//购物车总金额
        map.put("shopTypeNum", shoppingCartList.size());//购物车商品种类
        map.put("pscId", pscId);//购物车商品种类
        //获取购物车信息
        map.put("success", true);
        JSONObject obj = JSONObject.fromObject(map);
        return obj.toString();
    }

    /**
     * 过时方法，暂时无用，改为上面查询购物车信息时结合批发价表获取批发金额
     */
    @Deprecated
    public String getShopCartInfor_back(Map<String, Object> map, String pscId) throws Exception {
        //1.获取session数据
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        //2.获取购物车商品个数、金额总数
        List<BaseBean> shoppingCartList = baseBeanDao.getListBeanByHqlAndParams("from PfscShoppingCart o where o.staffComId = ? and o.staffId= ? ", new Object[]{parmaInfor.get("companyId").toString(), parmaInfor.get("staffId").toString()});
        PfscShoppingCart shoppingCart = null;
        int shopNum = 0;
        double shopPrice = 0;
        for (int i = 0, j = shoppingCartList.size(); i < j; i++) {
            shoppingCart = (PfscShoppingCart) shoppingCartList.get(i);
            shopNum += shoppingCart.getTjNum();//购物车总数
            shopPrice = PriceArithUtil.add(shopPrice, PriceArithUtil.mul(shoppingCart.getAllPrice(), shoppingCart.getTjNum()));//购物车总金额
        }
        map.put("shoppingCartList", shoppingCartList);
        map.put("shopNum", shopNum);//购物车总数
        map.put("shopPrice", shopPrice);//购物车总金额
        map.put("shopTypeNum", shoppingCartList.size());//购物车商品种类
        map.put("pscId", pscId);//购物车商品种类
        //获取购物车信息
        map.put("success", true);
        JSONObject obj = JSONObject.fromObject(map);
        return obj.toString();
    }

    @Transactional
    public void jianShoppingCart(PfscShoppingCart shoppingCartFrom) throws Exception {
        //1.获取session数据
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        //2.根据手机端传过来的staffid查询状态为在职的account表数据
        String companyId = parmaInfor.get("companyId").toString();
        String staffId = parmaInfor.get("staffId").toString();
        // 3.通过staffid查找account用户信息
        String sql = " from CAccount o where o.companyID = ? and o.staffID = ? and o.accountStatus = '00'";
        CAccount account = (CAccount) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{companyId, staffId});
        //查询数据
        String scSql = "from PfscShoppingCart o where o.codeId = ? and o.goodsid = ? and o.staffComId = ? and o.staffId= ? and o.pscId=?";
        PfscShoppingCart shoppingCart = (PfscShoppingCart) baseBeanDao.getBeanByHqlAndParams(scSql, new Object[]{shoppingCartFrom.getCodeId(), shoppingCartFrom.getGoodsid(), companyId, staffId, shoppingCartFrom.getPscId()});
        if (shoppingCart != null && shoppingCart.getTjNum() > 0) {
            shoppingCart.setTjNum(shoppingCart.getTjNum() - 1);
        }
        String parameter = "修改批发商城购物车（购物车id：" + shoppingCart.getPscId() + "）";
        // 创建保存/更新订单信息
        saveOrUpdateShopCart(shoppingCart, companyId, parameter, account);
    }

    @Transactional
    public void delShoppingCart(PfscShoppingCart shoppingCartFrom) throws Exception {
        //1.获取session数据
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        //2.根据手机端传过来的staffid查询状态为在职的account表数据
        String companyId = parmaInfor.get("companyId").toString();
        String staffId = parmaInfor.get("staffId").toString();
        //查询数据
        String scSql = "from PfscShoppingCart o where o.codeId = ? and o.goodsid = ? and o.staffComId = ? and o.staffId= ? and o.pscId=?";
        PfscShoppingCart shoppingCart = (PfscShoppingCart) baseBeanDao.getBeanByHqlAndParams(scSql, new Object[]{shoppingCartFrom.getCodeId(), shoppingCartFrom.getGoodsid(), companyId, staffId, shoppingCartFrom.getPscId()});
        if (shoppingCart != null && shoppingCart.getTjNum() == 0) {
            String hql = "delete from PfscShoppingCart o where o.codeId = ? and o.goodsid = ? and o.staffComId = ? and o.staffId= ? and o.pscId=?";
            List<Object[]> params = new ArrayList<Object[]>();
            params.add(new Object[]{shoppingCartFrom.getCodeId(), shoppingCartFrom.getGoodsid(), companyId, staffId, shoppingCartFrom.getPscId()});
            baseBeanDao.executeHqlsByParmsList(null, new String[]{hql}, params);
        }
    }

    public Map<String, Object> shoppingCartList() throws Exception {
        //1.获取session数据
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        //2.根据手机端传过来的staffid查询状态为在职的account表数据
        StringBuilder pfscSb = getPfscSelectSql();
        pfscSb.append("where o.staffComId = ? and o.staffId= ? ");
       // List<BaseBean> shoppingCartList = baseBeanDao.getListBeanByHqlAndParams("from PfscShoppingCart o where o.staffComId = ? and o.staffId= ? ", new Object[]{parmaInfor.get("companyId").toString(), parmaInfor.get("staffId").toString()});
        List<BaseBean> beanList = baseBeanDao.getListBeanBySqlAndParams(pfscSb.toString(), new Object[]{parmaInfor.get("companyId").toString(), parmaInfor.get("staffId").toString()});
        //3.查询公司
        List<String> comlist = new ArrayList<String>();
        List<Object> comps = new ArrayList<Object>();
        Object[] pfscObjs = null;
        Object objcom = null;
        //查询公司信息
        StringBuffer sb = new StringBuffer();
        sb.append("select c.companyId,c.companyName,dc.ccompanyid,dc.logoPath,1+nvl(s.total_pct,0)/100");
        sb.append(" from dtCompany c left join dt_ccom_com cc on c.companyId = cc.compnay_id");
        sb.append(" left join dtContactCompany dc on dc.ccompanyId = cc.ccompany_Id");
        sb.append(" left join dt_set_subsidize s");
        sb.append(" on dc.industrytype = s.gtid and s.stutas = ? where c.companyId = ?");
        String comId = null;
        List<PfscShoppingCartBean> shoppingCartList = new ArrayList<PfscShoppingCartBean>();
        PfscShoppingCartBean cartBean = null;
        for (int i = 0; i < beanList.size(); i++) {
            pfscObjs = (Object[])(Object) beanList.get(i);
            comId = pfscObjs[10] != null  ? pfscObjs[10].toString():"";//批发商城上公司id
            objcom = baseBeanDao.getObjectBySqlAndParams(sb.toString(), new Object[]{"01", comId});
            if (!comlist.contains(comId)) {
                comlist.add(comId);
                comps.add(objcom);
            }
            //将基础bean转换成PfscShoppingCartBean
            cartBean = addPfscShoppingCartBean(pfscObjs,cartBean);
            shoppingCartList.add(cartBean);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("shoppingCartList", shoppingCartList);
        map.put("comps", comps);
        return map;
    }

    /**
     * 将基础bean转换成PfscShoppingCartBean
     * @param pfscObjs bean数组数据
     * @return
     */
    private PfscShoppingCartBean addPfscShoppingCartBean(Object[] pfscObjs,PfscShoppingCartBean cartBean)throws Exception{
        //将基础bean转换成PfscShoppingCartBean
          cartBean = new  PfscShoppingCartBean(pfscObjs[0] != null  ? pfscObjs[0].toString():"", pfscObjs[1] != null  ? pfscObjs[1].toString():"",
                pfscObjs[2] != null  ? pfscObjs[2].toString():"",pfscObjs[3] != null  ? pfscObjs[3].toString():"",
                pfscObjs[4] != null  ? Double.valueOf(pfscObjs[4].toString().trim()):0, pfscObjs[5] != null  ? Double.valueOf(pfscObjs[5].toString().trim()):0,
                pfscObjs[6] != null  ?  Double.valueOf(pfscObjs[6].toString().trim()):0,  pfscObjs[7] != null  ? pfscObjs[7].toString():"",
                pfscObjs[8] != null  ? pfscObjs[8].toString():"",  pfscObjs[9] != null  ? pfscObjs[9].toString():"",
                pfscObjs[10] != null  ? pfscObjs[10].toString():"", pfscObjs[11] != null  ? pfscObjs[11].toString():"",
                pfscObjs[12] != null  ? Integer.valueOf(pfscObjs[12].toString().trim()):0, pfscObjs[13] != null  ? Integer.valueOf(pfscObjs[13].toString().trim()):0,
                pfscObjs[14] != null  ? pfscObjs[14].toString():"",  pfscObjs[15] != null  ? pfscObjs[15].toString():"",
                pfscObjs[16] != null  ? pfscObjs[16].toString():"",  pfscObjs[17] != null  ? pfscObjs[17].toString():"",
                pfscObjs[18] != null  ? pfscObjs[18].toString():"",  pfscObjs[19] != null  ? pfscObjs[19].toString():"",
                pfscObjs[20] != null  ? pfscObjs[20].toString():"", pfscObjs[21] != null  ? pfscObjs[21].toString():"",
                pfscObjs[22] != null  ? pfscObjs[22].toString():"",  pfscObjs[23] != null  ? pfscObjs[23].toString():"",
                pfscObjs[24] != null  ? Integer.valueOf(pfscObjs[24].toString().trim()):0,  pfscObjs[25] != null  ?new Date(DateUtil.string2Date(pfscObjs[25].toString()).getTime()) :null,
                pfscObjs[26] != null  ? pfscObjs[26].toString():"",  pfscObjs[27] != null  ? pfscObjs[27].toString():"",
                pfscObjs[28] != null  ? pfscObjs[28].toString():"",  pfscObjs[29] != null  ? pfscObjs[29].toString():"",
                pfscObjs[30] != null  ? Integer.valueOf(pfscObjs[30].toString().trim()):0);
        return cartBean;
    }

    @Transactional
    public void ajaxChangeCartNum(String shoppingCartParmStr) throws Exception {
        if (StringHelper.isNotEmpty(shoppingCartParmStr)) {
            //1.获取session数据
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            //2.根据手机端传过来的staffid查询状态为在职的account表数据
            String companyId = parmaInfor.get("companyId").toString();
            String staffId = parmaInfor.get("staffId").toString();
            // 3.通过staffid查找account用户信息
            String sql = " from CAccount o where o.companyID = ? and o.staffID = ? and o.accountStatus = '00'";
            CAccount account = (CAccount) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{companyId, staffId});
            //查询数据
            String scSql = "from PfscShoppingCart o where o.staffComId = ? and o.staffId= ? and o.pscId=?";
            String pscIdStr, pscNumStr, parameter = "";
            PfscShoppingCart shoppingCart = null;
            for (int i = 0, j = shoppingCartParmStr.split("@").length; i < j; i++) {
                pscIdStr = shoppingCartParmStr.split("@")[i].split("#")[0];
                pscNumStr = shoppingCartParmStr.split("@")[i].split("#")[1];
                shoppingCart = (PfscShoppingCart) baseBeanDao.getBeanByHqlAndParams(scSql, new Object[]{companyId, staffId, pscIdStr});
                if (shoppingCart != null && shoppingCart.getTjNum() > 0) {
                    shoppingCart.setTjNum(Integer.valueOf(pscNumStr.trim()));
                }
                parameter = "修改批发商城购物车（购物车id：" + shoppingCart.getPscId() + "）";
                // 创建保存/更新订单信息
                saveOrUpdateShopCart(shoppingCart, companyId, parameter, account);
            }
        }
    }

    @Transactional
    public void delShoppingCarts(String pscId, String companyId, String staffId) throws Exception {
        String hql = "delete from PfscShoppingCart o where  o.staffComId = ? and o.staffId= ? and o.pscId=?";
        List<Object[]> params = new ArrayList<Object[]>();
        params.add(new Object[]{companyId, staffId, pscId});
        baseBeanDao.executeHqlsByParmsList(null, new String[]{hql}, params);
        params = null;
        hql = null;
    }

    public Map<String, Object> toSettlement(StaffAddress staffAddress, String shoppingCartParmStr) throws Exception {
        //1.获取session数据
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        //2.根据手机端传过来的staffid查询状态为在职的account表数据
        String companyId = parmaInfor.get("companyId").toString();
        String staffId = parmaInfor.get("staffId").toString();
        // 3.通过staffid查找account用户信息
       // String sql = " from CAccount o where o.companyID = ? and o.staffID = ? and o.accountStatus = '00'";
        String sql = " from TEshopCustomer o where o.staffid = ? ";
        TEshopCustomer cus = (TEshopCustomer) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{staffId});
        //4.购买地址查询
        if (staffAddress == null) {
            String staffaddhql = "from StaffAddress where staffID=? and isDefault='是'";
            staffAddress = (StaffAddress) baseBeanDao.getBeanByHqlAndParams(staffaddhql, new String[]{staffId});
        } else {
            String staffaddhql = "from StaffAddress where  addressID=? ";
            staffAddress = (StaffAddress) baseBeanDao.getBeanByHqlAndParams(staffaddhql, new String[]{staffAddress.getAddressID()});
        }
        //5.拼接公司相关sql语句
        StringBuilder comSql = new StringBuilder();
        comSql.append("select c.companyId,c.companyName,dc.ccompanyid,dc.logoPath,1+nvl(s.total_pct,0)/100");
        comSql.append(" from dtCompany c left join dt_ccom_com cc on c.companyId = cc.compnay_id");
        comSql.append(" left join dtContactCompany dc on dc.ccompanyId = cc.ccompany_Id");
        comSql.append(" left join dt_set_subsidize s");
        comSql.append(" on dc.industrytype = s.gtid and s.stutas=? where c.companyId = ?");
        //6.初始化信息
        double sumprice = 0, shopAllPrice = 0;//初始化结算金额，购物车单个商品金额
        Object comObj = null;//初始化公司相关对象
        //PfscShoppingCart shoppingCart = null;//初始化购物车对象
        Object[] objs = null;
        List<String> comList = new ArrayList<String>();//公司集合
        List<Object> comps = new ArrayList<Object>();//公司对象集合
        //7.根据传过来的选中的购物车ids获取数据
//        String pscIdStr = "";
//        for (String pscId : shoppingCartParmStr.split("@")) {
//            pscIdStr += "'" + pscId + "',";
//        }
        //查询购物车sql
       //String pfscSql = "from PfscShoppingCart o where  o.staffComId = '" + companyId + "' and o.staffId= '" + staffId + "' and o.pscId in(" + pscIdStr.substring(0, pscIdStr.length() - 1) + ")";
        StringBuilder pfscSb = getPfscSelectSql();
        List<Object> params = new ArrayList<Object>();//公司对象集合
        pfscSb.append(" where  o.staffComId= ? and o.staffId= ? ");
        params.add(companyId);
        params.add(staffId);
        pfscSb.append(" and ( ");
        for (String pscId : shoppingCartParmStr.split("@")) {
            pfscSb.append(" o.pscId= ? or");
            params.add(pscId);
        }
        String zzSql = pfscSb.substring(0,pfscSb.length() - 2) + ")";
        List<BaseBean> baseBeanList = baseBeanDao.getListBeanBySqlAndParams(zzSql, params.toArray());
        //方法1
        String comId = null;
        List<PfscShoppingCartBean> shoppingCartList = new ArrayList<PfscShoppingCartBean>();
        PfscShoppingCartBean cartBean = null;
        for (int i = 0, j = baseBeanList.size(); i < j; i++) {
            objs = (Object[]) (Object) baseBeanList.get(i);
            comId = objs[10] != null  ? objs[10].toString():"";//批发商城上公司id
            comObj = baseBeanDao.getObjectBySqlAndParams(comSql.toString(), new Object[]{"01", comId});
            if (!comList.contains(comId)) {
                comList.add(comId);
                comps.add(comObj);
            }
            //单个商品总金额 = 购物车商品总额 * 个数
            shopAllPrice = PriceArithUtil.mul(objs[6] != null ? Double.valueOf(objs[6].toString().trim()) : 0, objs[12] != null ? Integer.valueOf(objs[12].toString().trim()) : 0);
            //单个商品总金额 = 购物车商品总额 * 个数
            sumprice = PriceArithUtil.add(sumprice, shopAllPrice);
            //将基础bean转换成PfscShoppingCartBean
            cartBean = addPfscShoppingCartBean(objs,cartBean);
            shoppingCartList.add(cartBean);
        }
        //用上面方法代替了
//        for (int i = 0, j = shoppingCartList.size(); i < j; i++) {
//            shoppingCart = (PfscShoppingCart) shoppingCartList.get(i);
//            comObj = baseBeanDao.getObjectBySqlAndParams(comSql.toString(), new Object[]{"01", shoppingCart.getCompanyid()});
//            if (!comList.contains(shoppingCart.getCompanyid())) {
//                comList.add(shoppingCart.getCompanyid());
//                comps.add(comObj);
//            }
//            //单个商品总金额 = 购物车商品总额 * 个数
//            shopAllPrice = PriceArithUtil.mul(shoppingCart.getAllPrice(), shoppingCart.getTjNum());
//            //单个商品总金额 = 购物车商品总额 * 个数
//            sumprice = PriceArithUtil.add(sumprice, shopAllPrice);
//        }
        //总价格
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("compList", comps);//公司列表
        map.put("shoppingCartList", shoppingCartList);//产品列表
        map.put("total", sumprice);//总价
        map.put("staffID", staffId);//登录人id
        if(cus!=null){
        map.put("user1",cus.getAccount());//账号
        }
        map.put("staffAddress", staffAddress);//购物地址
        return map;
    }

    public Map<String, Object> toBussList(List<Object> parms, String longitude, String latitude, String industryType, String search) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        //经度参数非空校验
        StringBuilder sb = null;
        String code = null;
        if (StringHelper.isNotEmpty(longitude) && StringHelper.isNotEmpty(latitude)) {
            sb = new StringBuilder("select s.ccompanyid,s.companyname,s.companyaddr,s.logopath,s.industrytype ," +
                    "(2*asin(sqrt(power(sin((" + longitude + "-s.accuracy)*3.14159265359/180/2),2)+cos(" + latitude + "*3.14159265359/180)" +
                    "*cos(s.dimension*3.14159265359/180)*power(sin((" + latitude + "-s.dimension)*3.14159265359/180/2),2)))*6378.137*1000)" +
                    "as distance from dtcontactcompany s where 1=1");
            //参数非空校验
            if (StringHelper.isNotEmpty(industryType)) {//行业类型id校验
                sb.append(" and s.industryid = ?");
                parms.add(industryType);
            }
            if (StringHelper.isNotEmpty(search)) {
                sb.append(" and companyname like ?");
                parms.add("%" + search.trim() + "%");
            }
            sb.append(" order by distance");
            code = "200";//自动定位成功返回200
        } else {
            sb = new StringBuilder("select ccompanyid,companyname,companyaddr,logopath,industrytype from dtcontactcompany where 1=1");
            //参数非空校验
            if (StringHelper.isNotEmpty(industryType)) {//行业类型id校验
                sb.append(" and industryid = ?");
                parms.add(industryType);
            }
            if (StringHelper.isNotEmpty(search)) {
                sb.append(" and companyname like ?");
                parms.add("%" + search.trim() + "%");
            }
            code = "201";//自动定位失败返回201
        }
        //通过查询条件获取相应超市
        map.put("sql", sb.toString());
        map.put("code", code);
        return map;
    }

    public List<BaseBean> dpShoppingCartList() throws Exception {
        //1.获取session数据
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        //2.根据手机端传过来的staffid查询状态为在职的account表数据
        StringBuilder sb = new StringBuilder("select");
        sb.append(" o.ppid,o.goodsName,o.allPrice,o.tjNum,");
        sb.append(" o.barcode,o.companyid,o.standard,o.yqNum,");
        sb.append(" '','1','','',o.wholesaleId,''");
        sb.append(" from dt_PfscShoppingCart o ");
        sb.append(" where 1=1 ");
        sb.append(" and o.staffComId = ? and o.staffId= ? ");
        List<Object> parms = new ArrayList<>();
        parms.add(parmaInfor.get("companyId").toString());
        parms.add(parmaInfor.get("staffId").toString());
        List<BaseBean> beanList = baseBeanDao.getListBeanBySqlAndParams(sb.toString(), parms.toArray());
        return beanList;
    }
    
    /**
     * 
     * 获取批发商城广告图
     * @param ccompanyID
     * @param ccomIDPlatform
     * @return
     */
	public Map<String, Object> getAdvertPic(String ccompanyID,
			String ccomIDPlatform) {
		Map<String, Object> mp = new HashMap<String, Object>();
		if (ccomIDPlatform != null && !ccomIDPlatform.equals("")) {

			String sql = "select p.topic from dt_PhlTopics p where p.companyID = (select m.compnay_id from DT_ccom_com m where m.ccompany_Id = ?) order by dbms_random.value";

			List<Object> phlist = baseBeanDao.getListObjectBySqlAndParams(sql,
					new Object[] { ccomIDPlatform });

			String hqlp = "select image,ppid from dt_ProductPackaging where type = ? and goodsName like ? order by dbms_random.value";
			if (phlist.size() > 0) {
				Object pt = (Object) phlist.get(0);
				List<BaseBean> prolist = baseBeanDao.getListBeanBySqlAndParams(
						hqlp,
						new Object[] { "会员分享", "%" + pt.toString() + "%" });
				if (prolist.size() > 0) {
					Object p = (Object) prolist.get(0);
					Object[] objs = (Object[]) p;
					mp.put("ppid", objs[1].toString());
					mp.put("image", objs[0].toString());
				} else {
					mp.put("ppid", "");
					mp.put("image", "");
				}
			} else {
				mp.put("ppid", "");
				mp.put("image", "");
			}

		} else {

			String hqlp = "select image,ppid from dt_ProductPackaging where type = ? and companyID = (select m.compnay_id from DT_ccom_com m where m.ccompany_Id = ?)  order by dbms_random.value";
			List<BaseBean> prolist = baseBeanDao.getListBeanBySqlAndParams(
					hqlp, new Object[] { "会员分享", ccompanyID });
			if (prolist.size() > 0) {
				Object p = (Object) prolist.get(0);
				Object[] objs = (Object[]) p;
				mp.put("ppid", objs[1].toString());
				mp.put("image", objs[0].toString());
			} else {
				mp.put("ppid", "");
				mp.put("image", "");
			}
		}

		return mp;

	}
}
