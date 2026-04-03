package hy.ea.invoicing.service.impl;

import hy.ea.bo.CAccount;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.InvCheckGoods;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.InvtFbCheck;
import hy.ea.dao.CCodeDao;
import hy.ea.invoicing.service.CheckInvService;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.bean.checkInv.CheckInvAddBean;
import mobile.tiantai.android.bean.checkInv.GoodListAddBean;
import mobile.tiantai.android.util.MapSesssionUtil;
import net.sf.json.JSONObject;
import org.hibernate.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2019-10-23.
 */
@Service
@Transactional
public class CheckInvServiceImpl implements CheckInvService {
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private CLogBookService logBookService;
    @Resource
    private CCodeDao codeDao;
    private String barcode;//扫码枪的编码
    private List<BaseBean> pricelist;//价格和库存集合
    private String companyid;//公司id手机端传过来的
    private String depotName;//盘库名称

    @Override
    /*
    列表展示list
    * */
    public PageForm getCheckInvList(String comid,String staffid,String orgid,Integer pageNumber,boolean showFlag){
   /*一级菜单栏的数据*/     //TODO 配置信息暂时写死
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        if (parmaInfor == null) {//未存入session，则将数据存入session
            Map<String, Object> map = MapSesssionUtil.saveYsdSessionForMap(comid, staffid);
            parmaInfor = MapSesssionUtil.saveSessionForMap("paramMap", map);
        } else if (StringHelper.isNotEmpty(comid) && StringHelper.isNotEmpty(staffid)) {
                //判断传过来的参数与session中的参数不一致
                if (!parmaInfor.get("companyId").toString().equals(comid) || !parmaInfor.get("staffId").toString().equals(staffid)) {
                    MapSesssionUtil.removeSession("paramMap");
                    Map<String, Object> map = MapSesssionUtil.saveYsdSessionForMap(comid, staffid);
                    parmaInfor = MapSesssionUtil.saveSessionForMap("paramMap", map);
                }
            }
        /*
        * 分页对列表的数据进行展示
        * */
        PageForm pageForm =new PageForm();
        List<Object> params=new ArrayList<Object>();
        StringBuffer hql=new StringBuffer("from InvtFbCheck where companyid=? order by billsdate desc");
        params.add(comid);
        int count = baseBeanDao.getConutByByHqlAndParams("select count(*) "+hql.toString(),params.toArray());// 总条数
        if (count == 0) {
            return null;
        }
        int pageCount = count / 10 + ((count % 10) == 0 ? 0 : 1);
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        if (pageNumber > pageCount) {
            pageNumber = pageCount;
        }
        int firstResult = 10 * (pageNumber - 1);
        int maxResult = Math.min(10, count - firstResult);
        List<BaseBean> listBaseBean = baseBeanDao.getConutByByHqlAndParamsAndPage(hql.toString(),
                        params.toArray(), firstResult,
                        maxResult);
        pageForm.setPageSize(10);
        pageForm.setRecordCount(count);
        pageForm.setPageCount(pageCount);
        pageForm.setPageNumber(pageNumber);
        pageForm.setList(listBaseBean);
        return pageForm;
    }
    @Override
    public String findOrgByAcc(String organizationID, String companyId, String staffId) throws Exception {
        //1.根据手机端传过来的staffid查询状态为在职的account表数据
        //TODO 先按下面查询
        StringBuffer sb = new StringBuffer();
        sb.append("from COrganization c");
        sb.append(" where 1=1 ");
        sb.append(" and c.organizationPID = ? ");
        sb.append(" and c.Status = '00'");
        sb.append(" order by c.organizationNumber");
        List<Object> params = new ArrayList<Object>();
        params.add(organizationID);
        List<BaseBean> beanList = baseBeanDao.getListBeanByHqlAndParams(sb.toString(), params.toArray());
        Map<String, Object> map = new HashMap<String, Object>(1);
        if (null != beanList) {
            map.put("organizationList", beanList);
        }
        JSONObject oj = JSONObject.fromObject(map);
        return oj.toString();
    }

    //扫描枪扫描的数据
    @Override
    public void splicingAddBean(Inventory inventory, CheckInvAddBean addBean) throws Exception {
        if (inventory != null) {
            //1.从session中获取保存的数据信息
            Map<String , Object> map =  MapSesssionUtil.getSessionForMap("scanningMap");
            CheckInvAddBean checkInvAddBean = (CheckInvAddBean) map.get("invAddBean");
            //2.判断是否已存在扫描的盘库信息
            List<GoodListAddBean> goodsList ;
            if(checkInvAddBean.getGoodListAddBean() !=null){//已存在，则取出盘库集合
                goodsList = checkInvAddBean.getGoodListAddBean();
            }else{//不存在则创建集合下方进行保存
                goodsList = new ArrayList<>();
            }
            //3.将扫描出的盘库信息放入创建好的addbean中
            GoodListAddBean  goods= new GoodListAddBean();
            goods.setGoodsId(inventory.getGoodsID());//货物id
            goods.setGoodsName(inventory.getGoodsName());//货物品名名称
            goods.setUnitPrice(inventory.getUnitPrice());//商品单价
            goods.setInvenQuantity(inventory.getInvenQuantity());//商品库存数量
            goodsList.add(goods);
            List<String> depotNUms = addBean.getDepotNum();
            double total = 0;
            if(depotNUms!=null&&depotNUms.size()>0){//已存在，则取出盘库集合
                for (int i = 0; i < depotNUms.size(); i++) {
                    String num = depotNUms.get(i);
                    //把盘库数放进商品集合的每个商品的数据
                    GoodListAddBean addBean1 = goodsList.get(i);
                    addBean1.setRealQuantity(num);
                    total += Double.parseDouble((goodsList.get(i)).getUnitPrice()) * Integer.parseInt(num);
                }
            }else{
                depotNUms= new ArrayList<>();
                addBean.setDepotNum(depotNUms);
            }
            addBean.setTotal(total);
            checkInvAddBean.setGoodListAddBean(goodsList);
            map.put("invAddBean",checkInvAddBean);
            addBean.setGoodListAddBean(goodsList);
            map.put("invAddBean",addBean);
            if (depotNUms.size()<goodsList.size()){
                addBean.getDepotNum().add("0");
            }

        }
    }

    /**
     * 循环扫码枪获取到的数据
     * @param baseBeanList 最后保存用bean
     * @param
     * @param companyId    公司id
     * @throws Exception
     */
    private void saveGoodsBills(List<BaseBean> baseBeanList,InvtFbCheck invtFbCheck, String companyId,CheckInvAddBean addBean) throws Exception {
      Map<String, Object> scanningMap = MapSesssionUtil.getSessionForMap("scanningMap");
       if (scanningMap != null) {//未存入session，则将数据存入session
           CheckInvAddBean checkInvAddBean = (CheckInvAddBean) scanningMap.get("invAddBean");
           List<GoodListAddBean> goodList = checkInvAddBean.getGoodListAddBean();
           List<String> depotNumList = addBean.getDepotNum();
           if (goodList.size()==depotNumList.size()){
               for (int i = 0; i < goodList.size(); i++) {
                   InvCheckGoods invCheckGoods  = new   InvCheckGoods();
                   GoodListAddBean goods = goodList.get(i);
                   String depotNum = depotNumList.get(i);
                   //保存品信息到表中
                   invCheckGoods.setFBILLID(invtFbCheck.getFbillid());//FBILLID 盘库id
                   invCheckGoods.setCheckinvId(serverService.getServerID("invCheckGoods"));
                   invCheckGoods.setGoodsID(serverService.getServerID("goodsbills"));//物品单据id
                   invCheckGoods.setGoodsName(goods.getGoodsName());//物品名称
                   invCheckGoods.setPrice(goods.getUnitPrice());//总金额
                   invCheckGoods.setInvenQuantity(goods.getInvenQuantity());//库存数量
                   invCheckGoods.setRealQuantity(addBean.getDepotNum().get(i));//盘库数量
                   String goodsId = goods.getGoodsId();
                   invCheckGoods.setGoodsID(goodsId);//货物id
                   ProductPackaging productPackaging = (ProductPackaging) baseBeanDao.getBeanByHqlAndParams("from ProductPackaging where goodsID = ?",new Object[]{goodsId});
                   if (productPackaging!=null){
                       String ppID = productPackaging.getPpID();
                       invCheckGoods.setPpID(ppID);//项目ID也就是产品ID；
                   }
                   baseBeanList.add(invCheckGoods);
               }
           }
        }
    }
    //保存数据到数据库中
    @Override
    @Transactional
    public void saveCostSheet(InvtFbCheck invtFbCheck, CheckInvAddBean addBean) throws Exception {
        invtFbCheck = new InvtFbCheck();

       //1.获取session数据
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        //2.根据手机端传过来的staffid查询状态为在职的account表数据
        String companyId = parmaInfor.get("companyId").toString();
        String staffId = parmaInfor.get("staffId").toString();
        // 通过staffid查找account用户信息
        String sql = " from CAccount o where o.companyID = ? and o.staffID = ? and o.accountStatus = '00'";
        // 查询登录人信息
        Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",
                new Object[]{staffId});
        // 标示数据属于哪个部门，是否就是所选部门id
        String departmentId = addBean.getDepartmentID();
        //
        invtFbCheck.setFbillid(serverService.getServerID("invtFbCheck"));//盘库单id
        invtFbCheck.setGroupcompanysn(staff.getGroupCompanySn());//集团标识
        //TODO
        invtFbCheck.setWarehouse(addBean.getWarehouse());//仓库id
        invtFbCheck.setJournalnum(addBean.getBillId());//单据编号
        invtFbCheck.setCompanyid(companyId);//公司id
        invtFbCheck.setCompanyName(addBean.getCompanyName());//公司名称
        invtFbCheck.setOrganizationid(departmentId);//制单人所属部门
        invtFbCheck.setOrgName(addBean.getDepartmentName());//制单人部门名称
        invtFbCheck.setBillstype("盘库单");//单据类型
        invtFbCheck.setBillstatus("草稿");//单据状态 草稿
        invtFbCheck.setBillsdate(new Date());//盘库日期
        invtFbCheck.setWarehousename(addBean.getDepotName());//盘库仓库
        invtFbCheck.setStaffname(addBean.getStaffName());//制单人名称
        invtFbCheck.setStaffid(addBean.getStaffFzrId());//制单人id
        // 制单人信息EN
        //4.创建保存订单的bean
        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        baseBeanList.add(invtFbCheck);
        //5.循环扫码枪获取到的数据
        saveGoodsBills(baseBeanList, invtFbCheck, companyId,addBean);
        //7.保存表
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,
                null);
    }

    //更新数据
    @Override
    @Transactional
    public void upCostSheet(InvtFbCheck invtFbCheck) throws Exception {
        String hql = "update InvtFbCheck set orgName = ? where fbillid = ?";
        List<Object[]> params = new ArrayList<Object[]>();
        params.add(new Object[]{invtFbCheck.getOrgName(),invtFbCheck.getFbillid()});
        baseBeanService.executeHqlsByParamsList(null, new String[]{hql}, params);
    }
    //删除并修改以保存的表invCheckGoods
    @Override
    public void removeBeanForKey(String fbillid) throws Exception {
        String hql = "delete from InvtFbCheck o where o.fbillid = ? ";
        String hql1 = "delete from InvCheckGoods where  fbillid =?";
        List parmList = new ArrayList();
        parmList.add(new Object[]{fbillid});
        parmList.add(new Object[]{fbillid});
        baseBeanService.executeHqlsByParamsList(null, new String[]{hql, hql1}, parmList);
    }

    @Override
    public void removeGoods(String goodsID, String fbillid) throws Exception {
        String hql = "delete from InvCheckGoods where  goodsID=? and fbillid =?";
        List<Object[]> params = new ArrayList();
        params.add(new Object[] { goodsID, fbillid });
        this.baseBeanService.executeHqlsByParamsList(null, new String[] { hql }, params);
    }

    @Override
    public String ajaxStaffForDep(String departmentID) throws Exception {
        //1.获取session数据
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        //拼接sql语句查询
        StringBuffer sb = new StringBuffer();
        sb.append(" select s.staffID,s.staffName,s.staffCode from Staff s where exists ");
        sb.append("(select staffID from COS c ");
        sb.append(" where c.staffID = s.staffID and companyID = ?  and cosStatus = ? ");
        sb.append(" and (c.organizationID = ? or exists ");
        sb.append(" (select d.depPostID from DepartmentPost d where d.depPostID = c.depPostID and d.leveloneOrgID = ?)))");
        //拼接list集合
        List<Object> params = new ArrayList<Object>();
        params.add(parmaInfor.get("companyId").toString());//公司id
        params.add("50");//状态值为50在职
        params.add(departmentID);//岗位id
        params.add(departmentID);//岗位id
        List<BaseBean> beanList = baseBeanDao.getListBeanByHqlAndParams(sb.toString(), params.toArray());
        Map<String, Object> map = new HashMap<>(1);
        if (null != beanList) {
            map.put("staffList", beanList);
        }
        JSONObject oj = JSONObject.fromObject(map);
        return oj.toString();
    }


}
