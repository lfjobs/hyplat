package hy.ea.production.service.impl;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.company.GoodsBarcode;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.production.service.GoodsManageSerivce;
import hy.ea.util.ExcelReadUtils;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * 物品设计
 *
 * @author [mz]
 * @version [1.0, 2018-08-17]
 * @see
 * @since
 */
@Service
@Transactional
public class GoodsManageServiceImpl implements GoodsManageSerivce {
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;


    private Logger logger = Logger.getLogger(GoodsManageServiceImpl.class);

    /**
     * 添加更多规格条码
     * @param goodsBarcodeMap
     * @param goodsManage
     */
    @Override
    public  void  addMoreBar(Map<String, GoodsBarcode> goodsBarcodeMap, GoodsManage goodsManage,List<BaseBean> beans,List<String> hqls){

     if(goodsManage.getGoodsKey()!=null&&!goodsManage.getGoodsKey().equals("")) {
         String hqldelete = "delete from GoodsBarcode where  goodsid = ?";
         hqls.add(hqldelete);

     }
     if(!"0".equals(goodsManage.getIsScale())){
        GoodsBarcode gb = new GoodsBarcode();
        gb.setBarcodeid(serverService.getServerID("bcid"));
        gb.setGoodsid(goodsManage.getGoodsID());
        gb.setBarcode(goodsManage.getBarCode());
        gb.setQuantity("1");
        gb.setSpcation(goodsManage.getStandard());
        gb.setVariable1Id(goodsManage.getVariableID());
        gb.setCompanyID(goodsManage.getCompanyID());

        gb.setStatus("00");
        beans.add(gb);


        for (GoodsBarcode goodsBarcode : goodsBarcodeMap.values()) {
            goodsBarcode.setBarcodeid(serverService.getServerID("bcid"));
            goodsBarcode.setGoodsid(goodsManage.getGoodsID());
            goodsBarcode.setStatus("01");
            goodsBarcode.setCompanyID(goodsManage.getCompanyID());
            beans.add(goodsBarcode);

        }
     }

    }

    /**
     *
     * 根据物品ID获取其他规格条码
     * @param goodsID
     * @return
     */
    public List<BaseBean> getGoodsBarList(String goodsID){

      String hql = "from GoodsBarcode where goodsid = ? and status = ?";
      List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{goodsID,"01"});

      return list;
    }

    /**
     * 读取excel-佣金
     * @return
     */
    public void jxexcelSetup(CAccount account){
        List<BaseBean> beans=new ArrayList<BaseBean>();
        List<BaseBean> productbeans = new ArrayList<BaseBean>();
        try {
            //产品名称	单位	条码号	供应价	毛利	贴牌	设备投资	省级代理	县级代理	村级代理	业务佣金	客户积分	批发价	平台服务	平台零售价	商品照片	详情	是否称重	计件方式
            String columns[] = {"条码号","产品名称","供应价","毛利","设备安装","贴牌","设备投资","村级代理","县级代理","省级代理","业务佣金","客户积分","平台零售价"};

            List<Map<String,String>> list= ExcelReadUtils.jxexcel("D:\\setup.xlsx",columns);
            //遍历解析出来的list
            String psql="delete dt_pro_setup ps where ps.ppid=?";
            String pssql="delete dt_pro_setup_sub pss where pss.ppid=?";
            String psbsql="delete dt_pro_setup_backup psb where psb.ppid=?";
            String pssbsql="delete dt_pro_setup_sub_backup pssb where pssb.ppid=?";
            List<String> sqlList=new ArrayList<String>();
            List<Object[]> parList=new ArrayList<Object[]>();
            for (Map<String,String> map : list) {
                if((map.get("产品名称")!=null&&!map.get("产品名称").replaceAll(" ", "").equals(""))||(map.get("条码号")!=null&&!map.get("条码号").replaceAll(" ", "").equals(""))){
                	ProductPackaging propack = null;
                	for (int j = 0; j < productbeans.size(); j++) {
                        ProductPackaging g = (ProductPackaging) productbeans.get(j);
                        if (g!=null&&( 
                        		(
                        			g.getGoodsName()!=null&&g.getGoodsName().equals(map.get("产品名称").replaceAll(" ", ""))
                        		)||(
                        			g.getBarCode()!=null&&g.getBarCode().equals(map.get("条码号").replaceAll(" ", ""))
                        		)
                        	       )
                        	) {
                        	propack = g;
                            break;
                        }
                    }
                	if(propack==null){
                		List<BaseBean> p=baseBeanDao.getListBeanByHqlAndParams("from ProductPackaging where (goodsName=? or barCode = ?) and companyID=?",new Object[]{map.get("产品名称").replaceAll(" ", ""),map.get("条码号").replaceAll(" ", ""),account.getCompanyID()});
                        if(p!=null){
                            for (int j=0;j<p.size();j++){
                                propack=(ProductPackaging)p.get(j);
                                if(propack!=null){
                                	productbeans.add(propack);
                                    sqlList.add(psql);
                                    sqlList.add(pssql);
                                    sqlList.add(psbsql);
                                    sqlList.add(pssbsql);

                                    parList.add(new Object[] {propack.getPpID()});
                                    parList.add(new Object[] {propack.getPpID()});
                                    parList.add(new Object[] {propack.getPpID()});
                                    parList.add(new Object[] {propack.getPpID()});
                                    String g=map.get("供应价").replaceAll(" ", "");
                                    String l=map.get("平台零售价").replaceAll(" ", "");
                                    BigDecimal gyj=new BigDecimal(g==null||g.equals("")?"0":g).setScale(4,BigDecimal.ROUND_HALF_UP);
                                    BigDecimal lsj=new BigDecimal(l==null||l.equals("")?"0":l).setScale(4,BigDecimal.ROUND_HALF_UP);


                                    //"供应价","1","贴牌","设备投资","省级代理","县级代理","村级代理","业务佣金","客户积分","2","3","平台零售价",
                                    //p20170220ZVZR76B88M0000000022	客户积分
                                    //p20170510SIR5KABJEP0000000003	未招标成功代理
                                    //p20170220ZVZR76B88M0000000017	设备安装
                                    //p20170220ZVZR76B88M0000000016	贴牌
                                    //p20170220ZVZR76B88M0000000019	县级代理
                                    //p20170220ZVZR76B88M0000000018	省级代理
                                    //p20170220ZVZR76B88M0000000020	村级代理
                                    //p20170220ZVZR76B88M0000000014	全国代理
                                    //p20170605KY3VAANZJG0000000003	设备投资
                                    Object[] yj = new Object[]{map.get("设备安装"), map.get("贴牌"), map.get("设备投资"), map.get("省级代理"), map.get("县级代理"), map.get("村级代理"), map.get("客户积分")};
                                    Object[] dlid = new Object[]{"p20170220ZVZR76B88M0000000017","p20170220ZVZR76B88M0000000016", "p20170605KY3VAANZJG0000000003", "p20170220ZVZR76B88M0000000018", "p20170220ZVZR76B88M0000000019", "p20170220ZVZR76B88M0000000020", "p20170220ZVZR76B88M0000000022"};
                                    if(lsj.compareTo(BigDecimal.ZERO)>0) {
                                        ProSetup proSetup = new ProSetup();
                                        proSetup.setSuid(serverService.getServerID("setup"));

                                        BigDecimal proxyprice = BigDecimal.ZERO;

                                        for (int i = 0; i < yj.length; i++) {
                                            ProSetupSub setupSub = new ProSetupSub();
                                            BigDecimal dl = null;
                                            setupSub.setSusid(serverService.getServerID("prosetupsub"));
                                            if ("".equals(yj[i])) {
                                                dl = BigDecimal.ZERO;
                                            } else {
                                                dl = new BigDecimal(yj[i].toString()).setScale(4, BigDecimal.ROUND_HALF_UP);
                                            }
                                            setupSub.setPpid(propack.getPpID());
                                            setupSub.setSuid(proSetup.getSuid());
                                            setupSub.setAmount(dl.toString());
                                            setupSub.setTypePpid(dlid[i].toString());
                                            setupSub.setSjdate(new Date());
                                            proxyprice = proxyprice.add(dl);
                                            beans.add(setupSub);
                                        }

                                        BigDecimal ywyj = lsj.subtract(gyj).subtract(proxyprice);
                                        proSetup.setEfPrice(gyj.toString());
                                        proSetup.setRePrice(lsj.toString());
                                        proSetup.setComId(account.getCompanyID());
                                        proSetup.setBrokerage(ywyj.toString());
                                        proSetup.setPpid(propack.getPpID());
                                        proSetup.setPpname(propack.getGoodsName());
                                        proSetup.setTzType("03");
                                        proSetup.setSjdate(new Date());
                                        proSetup.setProxyprice(proxyprice.toString());
                                        proSetup.setState("00");

                                        beans.add(proSetup);
                                    }
                                }
                            }
                        }
                	}
                }
            }
            baseBeanDao.executeSqlsByParmsList(beans, sqlList.toArray(new String[]{}),parList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
