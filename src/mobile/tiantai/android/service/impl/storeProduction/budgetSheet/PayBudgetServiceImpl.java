package mobile.tiantai.android.service.impl.storeProduction.budgetSheet;

import cn.hutool.core.collection.CollectionUtil;
import com.mysl.bo.administrative.DtMyovertime;
import com.mysl.bo.administrative.DtMytravel;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.service.ProductsMmanagService;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.company.vo.ContactCompanyView;
import hy.ea.bo.finance.*;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.SocialSecuritySetup;
import hy.ea.bo.human.Staff;
import hy.ea.bo.production.AttriProduction;
import hy.ea.bo.production.GoodFunction;
import hy.ea.service.CLogBookService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.FileUtil;
import hy.ea.util.ImageCut;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.BusinessType;
import hy.plat.bo.BusinessTypeRecent;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.action.storeProduction.budgetSheet.GoodsBillsData;
import mobile.tiantai.android.bean.payBudget.CostBudgetAddBean;
import mobile.tiantai.android.bean.payBudget.PayBudgetAddBean;
import mobile.tiantai.android.bo.DtSpecsConfig;
import mobile.tiantai.android.service.storeProduction.budgetSheet.PayBudgetService;
import mobile.tiantai.android.util.MapSesssionUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.util.StringHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;
import java.util.*;

import static hy.ea.human.constant.Constant.ALL;
import static mobile.tiantai.android.constant.Constant.*;

/**
 * 预支付预算单serviceImpl
 * Created by lyc on 2018-10-08.
 */
@Service
public class PayBudgetServiceImpl implements PayBudgetService {

    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;
    //订单日志service
    @Resource
    private CLogBookService logBookService;

    @Resource
    private BaseBeanService baseBeanService;//基础beanservice

    @Resource
    private ProductsMmanagService pmService;

    @Resource
    private UpLoadFileService fileService;

    private final static String APPROVAL_RESULT_AGREE = "AGREE";
    private final static String APPROVAL_RESULT_REJECT = "REJECT";

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

    public String findOrgByAcc_back(String organizationID, String companyId, String staffId) throws Exception {
        //1.根据手机端传过来的staffid查询状态为在职的account表数据
        String sql = " from CAccount o where o.companyID = ? and o.staffID = ? and o.accountStatus = '00'";
        CAccount account = (CAccount) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{companyId, staffId});
        if (account != null) {
            //3.拼接sql语句查询一级菜单
            StringBuffer sb = new StringBuffer();
            sb.append("select c from COrganization c,COA coa");
            sb.append(" where coa.accountID = ?");
            sb.append(" and c.organizationPID = ? ");
            sb.append(" and coa.organizationID=c.organizationID and c.companyID=coa.companyID");
            sb.append(" and c.status = '00'");
            sb.append(" order by c.organizationNumber");
            List<Object> params = new ArrayList<Object>();
            params.add(account.getAccountID());
            params.add(organizationID);
            List<BaseBean> beanList = baseBeanDao.getListBeanByHqlAndParams(sb.toString(), params.toArray());
            Map<String, Object> map = new HashMap<String, Object>(1);
            if (null != beanList) {
                map.put("organizationList", beanList);
            }
            JSONObject oj = JSONObject.fromObject(map);
            return oj.toString();
        } else {
            return null;
        }
    }

    public DetachedCriteria getDc(String staffId, String companyId, String departmentID, boolean showFlag, String search, int searchType) throws Exception {
        //TODO 配置信息暂时写死
        //staffId = "cstaff201711286DJB2KGGH30000007128";
        //1.根据手机端传过来的参数判断是否已经放入session中
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        if (parmaInfor == null) {//未存入session，则将数据存入session
            Map<String, Object> map = MapSesssionUtil.saveYsdSessionForMap(companyId, staffId);
            parmaInfor = MapSesssionUtil.saveSessionForMap("paramMap", map);
        } else {
            //传过来的参数不为空
            if (StringHelper.isNotEmpty(companyId) && StringHelper.isNotEmpty(staffId)) {
                //判断传过来的参数与session中的参数不一致
                if (!parmaInfor.get("companyId").toString().equals(companyId) || !parmaInfor.get("staffId").toString().equals(staffId)) {
                    MapSesssionUtil.removeSession("paramMap");
                    Map<String, Object> map = MapSesssionUtil.saveYsdSessionForMap(companyId, staffId);
                    parmaInfor = MapSesssionUtil.saveSessionForMap("paramMap", map);
                }
            }
        }
        DetachedCriteria dc = DetachedCriteria.forClass(CashierBills.class);//将class转换成DetachedCriteria类
        // 项目预算列表
        dc.add(Restrictions.or(Restrictions.or(Restrictions.or(
                Restrictions.eq("status", "00"),
                Restrictions.eq("status", "01")), Restrictions.or(
                Restrictions.eq("status", "02"),
                Restrictions.eq("status", "03"))), Restrictions.or(
                Restrictions.eq("status", "04"),
                Restrictions.eq("status", "40"))));

        dc.add(Restrictions.eq("companyID", parmaInfor.get("companyId").toString()));
        // dc.add(Restrictions.eq("organizationID", organizationID));//不知道用处暂时注释掉
        dc.add(Restrictions
                .eq("billsType", "项目支出预算单"));
        dc.addOrder(Order.desc("cashierDate"));
        dc.add(Restrictions.eq("zctype", "cg"));
        //部门id为空则赋默认值
        if (StringHelper.isEmpty(departmentID)) {
            departmentID = "-1";
        }
        if (showFlag) {//showFlag为true则查看分列表
            dc.add(Restrictions.eq("departmentID", departmentID));//预算单上的部门id为组织架构id
        }
        searchSwitch(searchType, search, dc);
        return dc;
    }


    /**
     * 拼接模糊查询条件
     *
     * @param searchType 模糊查询类型
     * @param search     模糊查询
     * @param dc         DetachedCriteria类
     * @throws Exception
     */
    private void searchSwitch(int searchType, String search, DetachedCriteria dc) throws Exception {
        //拼接模糊查询条件
        if (StringHelper.isNotEmpty(search)) {
            //模糊查询
            switch (searchType) {
                case 2://项目名称
                    dc.add(Restrictions.like("projectName", search, MatchMode.ANYWHERE));
                    break;
                case 3://凭证号
                    dc.add(Restrictions.like("journalNum", search, MatchMode.ANYWHERE));
                    break;
                case 4://责任人
                    dc.add(Restrictions.like("staffName", search, MatchMode.ANYWHERE));
                    break;
                case 11://制单人
                    dc.add(Restrictions.like("inputName", search, MatchMode.ANYWHERE));
                    break;
                default:
                    dc.add(
                            Restrictions.or(
                                    Restrictions.or(
                                            Restrictions.like("projectName", search, MatchMode.ANYWHERE),
                                            Restrictions.like("journalNum", search, MatchMode.ANYWHERE)
                                    ),
                                    Restrictions.or(
                                            Restrictions.like("staffName", search, MatchMode.ANYWHERE),
                                            Restrictions.like("inputName", search, MatchMode.ANYWHERE)
                                    )
                            )
                    );
            }
        }
    }

    public void toAddPayBudget(Map<String, Object> scanningMap, PayBudgetAddBean addBean, String mapKey) throws Exception {
        // 判断是否是提交扫描数据
        if (addBean != null && addBean.isTzFlag()) {//为提交表单跳转
            scanningMap = MapSesssionUtil.getSessionForMap("scanningMap");
            if (scanningMap == null) {//未存入session，则将数据存入session
                scanningMap = new HashMap<String, Object>();
                MapSesssionUtil.saveSessionForMap("scanningMap", scanningMap);
                scanningMap.put("0", addBean);
            } else {
                //循环map获取最后一个key值
                for (String key : scanningMap.keySet()) {
                    mapKey = String.valueOf(Integer.valueOf(key) + 1);
                }
                scanningMap.put(mapKey, addBean);
            }
        }
    }

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
        Map<String, Object> map = new HashMap<String, Object>(1);
        if (null != beanList) {
            map.put("staffList", beanList);
        }
        JSONObject oj = JSONObject.fromObject(map);
        return oj.toString();
    }

    public DetachedCriteria getProDc(String search) throws Exception {
        //1.获取session数据
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        DetachedCriteria dc = DetachedCriteria
                .forClass(ProductPackaging.class);
        if (StringHelper.isNotEmpty(search)) {
            dc.add(Restrictions.or(Restrictions
                    .like("goodsName", search, MatchMode.ANYWHERE), Restrictions
                    .like("xmtypename", search, MatchMode.ANYWHERE)));
        }
        dc.add(Restrictions.eq("companyID", parmaInfor.get("companyId").toString()));
        dc.add(Restrictions.isNull("parentId"));
        return dc;
    }

    public void splicingAddBean(GoodsManage goodsManage, PayBudgetAddBean addBean) throws Exception {
        //1.根据公司id和扫描的条形码号查询货物信息
        if (goodsManage != null) {
            //2.根据货物id查询库存信息
            StringBuffer sb = new StringBuffer();
            List<Object> params = new ArrayList<Object>();
            sb.append(" select o.inventoryID, o.invenQuantity from Inventory o,DepotManage dm ");
            sb.append(" where 1=1 ");
            sb.append(" and o.goodsID = ? ");
            params.add(goodsManage.getGoodsID());//货物id
            sb.append(" and o.companyID = ? ");
            params.add(addBean.getCompanyId());//公司id
            sb.append(" and dm.depotID = o.warehouse ");
            sb.append(" and dm.depotName = '销售库' ");
            sb.append(" and dm.depotState = '00' ");
            List<BaseBean> beanList = baseBeanDao.getListBeanByHqlAndParams(sb.toString(), params.toArray());
            if (!beanList.isEmpty() && beanList.size() > 0) {
                //获取数据
                JSONArray jSONArray = JSONArray.fromObject(beanList.get(0));
                addBean.setInvInvenQuantity(jSONArray.get(1).toString());//库存数量
            } else {
                addBean.setInvInvenQuantity("0");//库存数量
            }
            //3.将参数封装成bean返回
            addBean.setGoodsId(goodsManage.getGoodsID());//货物id
            addBean.setGoodsPhotoPath(goodsManage.getPhotoPath());//图片路径
            addBean.setGoodsTypeId(goodsManage.getTypeID());//货物类别
            addBean.setGoodsBarCode(goodsManage.getBarCode());//货物条形码
            addBean.setGoodsGoodsCoding(goodsManage.getGoodsCoding());//货物品名编号
            addBean.setGoodsGoodsName(goodsManage.getGoodsName());//货物品名名称
            addBean.setGoodsStandard(goodsManage.getStandard());//货物规格
            addBean.setGoodsVariableId(goodsManage.getVariableID());//货物单位
        }
    }

    public DetachedCriteria getWlGsDc(String search) throws Exception {
        //1.获取session数据
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        // 查询往来单位
        DetachedCriteria dc = DetachedCriteria.forClass(ContactCompanyView.class);
        if (StringHelper.isNotEmpty(search)) {
            dc.add(Restrictions.or(Restrictions
                    .like("companyName", search, MatchMode.ANYWHERE), Restrictions
                    .like("contactConnections", search, MatchMode.ANYWHERE)));
        }
        dc.add(Restrictions.eq("companyID", parmaInfor.get("companyId").toString()));
        dc.addOrder(Order.desc("companyName"));
        return dc;
    }

    public void removeBeanForKey(String mapKey) throws Exception {
        Map<String, Object> scanningMap = MapSesssionUtil.getSessionForMap("scanningMap");
        if (scanningMap != null) {//未存入session，则将数据存入session
            scanningMap.remove(mapKey);
        }
    }

    @Transactional
    public void saveCostSheet(CashierBills cashierBills) throws Exception {
        //1.获取session数据
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        //2.根据手机端传过来的staffid查询状态为在职的account表数据
        String companyId = parmaInfor.get("companyId").toString();
        String staffId = parmaInfor.get("staffId").toString();
        // 通过staffid查找account用户信息
        String sql = " from CAccount o where o.companyID = ? and o.staffID = ? and o.accountStatus = '00'";
        CAccount account = (CAccount) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{companyId, staffId});
        // 查询登录人信息
        Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",
                new Object[]{staffId});
        // 标示数据属于哪个部门，是否就是所选部门id
        String organizationID = cashierBills.getDepartmentID();
        //3.保存订单信息
        cashierBills.setCashierBillsID(serverService.getServerID("cashierTally"));
        String parameter = "添加预算单据（凭证号：" + cashierBills.getJournalNum() + "）";
        cashierBills.setStatus("00");// 未审核
        cashierBills.setZctype("cg");//支出类型 分为常规支出cg;和非常规类型fcg;
        cashierBills.setBillsType("项目支出预算单");//单据类型
        //判断所选项目分类是否是人事项目
        if (compareBy(companyId, cashierBills.getXmtype())) {
            cashierBills.setPaystatus("00");// 工资状态
        }
        cashierBills.setGroupCompanySn(staff.getGroupCompanySn());//集团标识
        cashierBills.setCompanyID(companyId);//公司id
        cashierBills.setCashierDate(new Date());//单据日期  下单日期
        cashierBills.setOrganizationID(organizationID);//标示数据属于哪个部门
        // 3.1制单人信息START
        cashierBills.setInputName(staff.getStaffName());//制单人名称
        cashierBills.setInputid(staff.getStaffID());//制单人id
        cashierBills.setInputOrganizationID(organizationID);//制单人所属部门
        cashierBills.setInputOrganizationName(cashierBills.getDepartmentName());//制单人部门名称
        cashierBills.setInputCompanyid(companyId);//公司id
        cashierBills.setInputCompanyName(cashierBills.getCompanyName());//公司名称
        // 制单人信息END
        //4.创建保存订单的bean
        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        baseBeanList.add(cashierBills);
        //5.循环扫码枪获取到的数据
        saveGoodsBills(baseBeanList, cashierBills, companyId);
        //6.记录日志
//        CLogBook logBook = logBookService.saveCLogBook(organizationID,
//                parameter, account);
//        baseBeanList.add(logBook);
        //7.保存表
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,
                null);
    }

    /**
     * 判断公司下项目是否为人事项目
     */
    private boolean compareBy(String comId, String code) throws Exception {
        boolean flag = false;
        String sql = "select count(t.codeid) from dtccode t where t.companyid =? and t.codesn like ? and t.codesn=?";
        List<Object> parm = new ArrayList<Object>();
        parm.add(comId);
        parm.add("01%");// 人事项目codesn
        parm.add(code);
        int ret = baseBeanDao.getConutByBySqlAndParams(sql, parm.toArray());
        if (ret > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 循环扫码枪获取到的数据
     *
     * @param baseBeanList 最后保存用bean
     * @param cashierBills 保存的预算单
     * @param companyId    公司id
     * @throws Exception
     */
    private void saveGoodsBills(List<BaseBean> baseBeanList, CashierBills cashierBills, String companyId) throws Exception {
        Map<String, Object> scanningMap = MapSesssionUtil.getSessionForMap("scanningMap");
        if (scanningMap != null) {//未存入session，则将数据存入session
            GoodsBills goodsBills;
            for (Object obj : scanningMap.values()) {
                goodsBills = new GoodsBills();
                PayBudgetAddBean addBean = (PayBudgetAddBean) obj;
                goodsBills.setCashierBillsID(cashierBills.getCashierBillsID());//收支单管理id
                goodsBills.setGoodsBillsID(serverService.getServerID("goodsbills"));//物品单据id
                goodsBills.setCompanyID(companyId);//公司id
                goodsBills.setEndDate(new Date());// 入账时间
                goodsBills.setQuantity(addBean.getInvInvenQuantity());//库存数量
                goodsBills.setPrice(addBean.getBudgetUnitPrice());//预算单价
                goodsBills.setMoney(addBean.getBudgetMoney());//预算金额
                goodsBills.setTiaoQuantity(addBean.getBudgetNumber());//预算数量
//                goodsBills.setTiaoQuantity(goodsBills.getQuantity());// 初始时将调整信息设置为预算信息
                goodsBills.setTiaoPrice(goodsBills.getPrice());
                goodsBills.setTiaoMoney(goodsBills.getMoney());
                goodsBills.setGoodsID(addBean.getGoodsId());//货物id
                goodsBills.setGoodsNum(addBean.getGoodsGoodsCoding());//品名编号
                goodsBills.setGoodsName(addBean.getGoodsGoodsName());//物品名称
                goodsBills.setGoodsVariableID(addBean.getGoodsVariableId());//货物单位
                goodsBills.setCcompanyName(addBean.getCurrentCompany());//往来公司名称
                goodsBills.setCcompanyID(addBean.getCurrentCompanyId());//往来公司id
                goodsBills.setConnectID(addBean.getCurrentPersonId());//往来个人id
                goodsBills.setConnectName(addBean.getCurrentPerson());//往来个人姓名
                goodsBills.setPpID(cashierBills.getProID());//项目ID也就是产品ID；
                goodsBills.setPriceManage("人民币");//单价管理
                goodsBills.setTargetStart(new Date());//目标起时间
                goodsBills.setTargetEnd(new Date());//目标止时间
                goodsBills.setTargetDeptID(cashierBills.getInputOrganizationID());//目标部门id
                goodsBills.setTargetDeptName(cashierBills.getInputOrganizationName());//目标部门名称
                goodsBills.setTargetSalerID(cashierBills.getInputid());//目标业务员id
                goodsBills.setTargetSalerName(cashierBills.getInputName());//目标业务员名称
                baseBeanList.add(goodsBills);
            }
        }
    }

    @Transactional
    public void upCostSheet(CashierBills cashierForm) throws Exception {
        //1.获取session数据
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        //2.根据手机端传过来的staffid查询状态为在职的account表数据
        String companyId = parmaInfor.get("companyId").toString();
        String staffId = parmaInfor.get("staffId").toString();
        // 3.通过staffid查找account用户信息
        String sql = " from CAccount o where o.companyID = ? and o.staffID = ? and o.accountStatus = '00'";
        CAccount account = (CAccount) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{companyId, staffId});
        // 4.查询登录人信息
        Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",
                new Object[]{staffId});
        //5.根据传过来的预算单id查询预算单
        CashierBills cashierBills = (CashierBills) baseBeanDao.getBeanByHqlAndParams(" from CashierBills o where o.cashierBillsID = ?", new Object[]{cashierForm.getCashierBillsID()});
        if (cashierBills != null) {
            //6.保存订单信息
            // 标示数据属于哪个部门，是否就是所选部门id
            String organizationID = cashierForm.getDepartmentID();
            cashierBills.setCashierDate(new Date());
            String parameter = "修改预算单据（凭证号：" + cashierBills.getJournalNum() + "）";
            //6.1判断所选项目分类是否是人事项目
            if (compareBy(companyId, cashierForm.getXmtype())) {
                cashierBills.setPaystatus("00");// 工资状态
            }
            cashierBills.setGroupCompanySn(staff.getGroupCompanySn());//集团标识
            cashierBills.setCompanyID(companyId);//公司id
            cashierBills.setOrganizationID(organizationID);//标示数据属于哪个部门
            cashierBills.setDepartmentName(cashierForm.getDepartmentName());//所属部门名称
            cashierBills.setDepartmentID(cashierForm.getDepartmentID());//所选部门id
            cashierBills.setProjectName(cashierForm.getProjectName());//项目名称
            cashierBills.setXmtypename(cashierForm.getXmtypename());//项目分类名称
            cashierBills.setProID(cashierForm.getProID());//项目id
            cashierBills.setXmtype(cashierForm.getXmtype());//项目分类
            cashierBills.setProjectCode(cashierForm.getProjectCode());//项目编号
            cashierBills.setStaffID(cashierForm.getStaffID());//责任人id
            cashierBills.setStaffName(cashierForm.getStaffName());//责任人名称
            cashierBills.setStaffCode(cashierForm.getStaffCode());//责任人编号
            // 6.2制单人信息START
            cashierBills.setInputName(staff.getStaffName());//制单人名称
            cashierBills.setInputid(staff.getStaffID());//制单人id
            cashierBills.setInputOrganizationID(organizationID);//制单人所属部门
            cashierBills.setInputOrganizationName(cashierForm.getDepartmentName());//制单人部门名称
            cashierBills.setInputCompanyid(companyId);//公司id
            cashierBills.setInputCompanyName(cashierForm.getCompanyName());//公司名称
            // 制单人信息END
            //7.创建保存订单的bean
            List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
            baseBeanList.add(cashierBills);
            //8.删除并修改以保存的goodsbills表
            delAndUpGoodsBills(baseBeanList, cashierBills);
            //9.循环扫码枪获取到的数据
            saveGoodsBills(baseBeanList, cashierBills, companyId);
            //10.记录日志
            CLogBook logBook = logBookService.saveCLogBook(organizationID,
                    parameter, account);
            baseBeanList.add(logBook);
            //11.保存表
            baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,
                    null);
        }
    }

    /**
     * 删除并修改以保存的goodsbills表
     *
     * @param baseBeanList 最后保存用bean
     * @param cashierBills 保存的预算单
     */
    private void delAndUpGoodsBills(List<BaseBean> baseBeanList, CashierBills cashierBills) {
        // 修改原表数据
        List<BaseBean> goodBeanslist = baseBeanDao.getListBeanByHqlAndParams(" from GoodsBills gb where gb.cashierBillsID = ?", new Object[]{cashierBills.getCashierBillsID()});
        for (Object obj : goodBeanslist) {
            GoodsBills goodsBills = (GoodsBills) obj;
            goodsBills.setPpID(cashierBills.getProID());//项目ID也就是产品ID；
            goodsBills.setTargetDeptID(cashierBills.getInputOrganizationID());//目标部门id
            goodsBills.setTargetDeptName(cashierBills.getInputOrganizationName());//目标部门名称
            goodsBills.setTargetSalerID(cashierBills.getInputid());//目标业务员id
            goodsBills.setTargetSalerName(cashierBills.getInputName());//目标业务员名称
            baseBeanList.add(goodsBills);
        }
    }

    public DetachedCriteria getReleaseDc(int fbJumpType) throws Exception {
        //1.获取session数据
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        DetachedCriteria dc = DetachedCriteria.forClass(CashierBills.class);//将class转换成DetachedCriteria类
        if (fbJumpType == 1) {//已发布只查询03状态
            dc.add(Restrictions.eq("status", "03"));
        } else if (fbJumpType == 2) {//未发布查询00，01,02状态
            dc.add(
                    Restrictions.or(
                            Restrictions.eq("status", "00"),
                            Restrictions.or(
                                    Restrictions.eq("status", "01"),
                                    Restrictions.eq("status", "02")
                            )
                    )
            );
        }
        dc.add(Restrictions.eq("companyID", parmaInfor.get("companyId").toString()));
        dc.add(Restrictions
                .eq("billsType", "项目支出预算单"));
        dc.add(Restrictions.eq("zctype", "cg"));
        dc.addOrder(Order.desc("cashierDate"));
        return dc;
    }

    /**
     * 文件上传（分片上传合并文件）
     *
     * @param chunk     当前分片索引
     * @param chunks    分片总数量
     * @param fileName  原文件名
     * @param file      文件
     * @param path      路径
     * @param companyid 所在公司id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public synchronized Map upLoadFile(String chunk, String chunks, String fileName, File file, String path, String companyid) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        //生成新的文件名 UUID+文件后缀
        String name = UUID.randomUUID().toString().replaceAll("-", "");
        int index = fileName.lastIndexOf(".");
        String suffix = fileName.substring(index, fileName.length());
        String newFileName = name + suffix;
        String path2 = "/upload_files/" + companyid + "/product/"
                + Utilities.getDateString(new Date(), "yyyy-MM-dd");
        //文件未分片直接存储
        //chunk当前分片 chunks分片总数量 当这两个值为空的时候 文件未进行分片上传
        if (chunk == null && chunks == null) {
            transferTo(path + path2, newFileName, file, map);
            map.put("path", path2 + "/" + newFileName);
            map.put("name", fileName);
            return map;
        }

        //文件分片

        //创建临时存储文件夹 并且 上传文件名称的格式为  当前分片-新的文件名称   下面会根据当前分片进行分片文件的数组排序
        //把文件传到临时文件夹中
        transferTo(path + path2 + "/" + fileName, chunk + "-" + newFileName, file, map);
        //如果文件为最后一片
        if (chunk != null && chunks != null) {
            Integer c = Integer.parseInt(chunk);
            Integer cc = Integer.parseInt(chunks);
            if (c.equals(cc - 1)) {
                //获取分片文件存储的临时文件夹
                File fileDir = new File(path + path2 + "/" + fileName);

                //获取文件夹下的所有文件
                File[] files = fileDir.listFiles();
                //根据文件名称进行排序
                files = sort(files);
                String compoundName = "分片合成后的文件" + newFileName;
                //合成的文件
                File resultFile = new File(path + path2, compoundName);

                if (!resultFile.getParentFile().exists()) {
                    // 如果文件夹不存在则建一个
                    resultFile.getParentFile().mkdirs();
                }
                boolean b = mergeFiles(files, resultFile);
                System.out.println(b ? "合并成功" : "合并失败");
                //在合成文件的时候已经删除临时文件 现在删除临时文件夹
                fileDir.delete();
                map.put("siSuccess", true);
                map.put("path", path2 + "/" + compoundName);
                map.put("name", fileName);
            }
        }
        System.out.println(path + path2);
        return map;
    }


    /**
     * 文件上传
     *
     * @param savePath 文件保存路径
     * @param newName  新文件名称
     * @param photo    需要上传的文件
     * @param map      返回参数
     */
    public synchronized void transferTo(String savePath, String newName, File photo, Map<String, Object> map) throws Exception {
        String photoPath = savePath + "/" + newName;
        File dFile = new File(savePath);
        if (!dFile.exists()) {
            // 如果文件夹不存在则建一个
            dFile.mkdirs();
        }

        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fos = new FileOutputStream(photoPath);
            fis = new FileInputStream(photo);
            byte[] buffer = new byte[1024 * 8];
            int len = 0;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {

                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
        map.put("siSuccess", true);
    }

    /**
     * 根据文件名称进行排序
     *
     * @param array
     * @return
     */
    private File[] sort(File[] array) {
        //对数组用冒泡法进行从小到大的排序
        File temp;
        //定义一个整型临时变量temp
        //用两层循环比较两个相邻的元素，将值大的元素交换至右端，一直循环比较n-1趟，直至顺序排列完毕
        //外层循环控制排序趟数
        for (int i = 0; i < array.length; i++) {
            //内层循环控制排序趟数
            for (int j = i + 1; j < array.length; j++) {
                //若数组元素i大于数组元素j(即第i个数大于第i+1个数)，执行判断语句,调换两数位置，即将较小数往左移
                if (Integer.parseInt(array[i].getName().split("-")[0]) >
                        Integer.parseInt(array[j].getName().split("-")[0])) {
                    //若第i个数大于第i+1个数，则交换位置
                    //将第i+1个数放到temp中，array[i] —> temp
                    temp = array[i];
                    //第j个数的值等于第j+1个数的值; array[j]—> array[i]
                    array[i] = array[j];
                    //第i+1个数的值=原始存在temp的 值;temp—> array[j]
                    array[j] = temp;
                }
            }
        }
        for (File file : array) {
            System.out.println(file.getName());
        }
        return array;
    }

    /**
     * 合并文件
     *
     * @param files      分片文件集合
     * @param resultFile 合并文件
     * @return
     */
    private static boolean mergeFiles(File[] files, File resultFile) {
        try {
            FileChannel resultFileChannel = new FileOutputStream(resultFile, true).getChannel();

            for (int i = 0; i < files.length; i++) {
                FileChannel blk = new FileInputStream(files[i]).getChannel();
                resultFileChannel.transferFrom(blk, resultFileChannel.size(), blk.size());
                blk.close();
            }
            resultFileChannel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        for (int i = 0; i < files.length; i++) {
            System.out.println("临时文件:" + files[i].getName() + " 已删除");
            files[i].delete();
        }
        return true;
    }


    @Override
    public void toAddCostBudgetBill(Map<String, Object> cacheGoodsMap, Map<String, Object> scanGoodsMap) throws Exception {
        scanGoodsMap = MapSesssionUtil.getSessionForMap("scanGoodsMap");
        cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");

        //scanGoodsMap为空，代表中间页面没有添加数据
        if (scanGoodsMap != null) {
            if (cacheGoodsMap == null) {
                cacheGoodsMap = new LinkedHashMap<String, Object>();
                MapSesssionUtil.saveSessionForMap("cacheGoodsMap", cacheGoodsMap);
            }
            cacheGoodsMap.putAll(scanGoodsMap);

            Set<String> keySet = cacheGoodsMap.keySet();
            CostBudgetAddBean lastBean = null;
            for (String s : keySet) {
                CostBudgetAddBean bean = (CostBudgetAddBean) cacheGoodsMap.get(s);
                if (lastBean == null) {
                    bean.setBalance(bean.getBudgetAmount());
                    bean.setInitialBalance("0");
                } else {
                    bean.setInitialBalance(lastBean.getBalance());
                    bean.setBalance(new BigDecimal(bean.getBudgetAmount()).add(new BigDecimal(lastBean.getBalance())).toString());
                }
                lastBean = bean;
            }
            //清除scanGoodsMap的数据，避免污染下一次的数据
            MapSesssionUtil.removeSession("scanGoodsMap");
        }
    }

    @Override
    public String getOrganizationByStaff(String companyId, String staffId) {
//        StringBuffer sb = new StringBuffer();
////            select * from DT_HR_STAFF s left join DTCOS c on  C.STAFFID = S.STAFFID left join DTCORGANIZATION o on C.ORGANIZATIONID = O.ORGANIZATIONID
////            where c.COMPANYID='company201009046vxdyzy4wg0000000025' and s.STAFFID='cstaff20101106w4xvpsrkrx0000001989';
//        sb.append("select o from Staff s,COS c,COrganization o");
//        sb.append(" where s.staffID=c.staffID and o.organizationID=c.organizationID ");
//        sb.append(" and o.companyID = ? and s.staffID=? ");
//        sb.append(" and o.Status = '00'");
//        sb.append(" order by o.organizationNumber");
//        List<Object> params = new ArrayList<Object>();
//        params.add(companyId);
//        params.add(staffId);
        List<BaseBean> beanList = getOrganization(companyId, staffId);
        Map<String, Object> map = new HashMap<String, Object>(1);
        if (null != beanList) {
            map.put("organizationList", beanList);
        }
        JSONObject oj = JSONObject.fromObject(map);
        return oj.toString();
    }

    public List<BaseBean> getOrganization(String companyId, String staffId) {
        StringBuffer sb = new StringBuffer();
//            select * from DT_HR_STAFF s left join DTCOS c on  C.STAFFID = S.STAFFID left join DTCORGANIZATION o on C.ORGANIZATIONID = O.ORGANIZATIONID
//            where c.COMPANYID='company201009046vxdyzy4wg0000000025' and s.STAFFID='cstaff20101106w4xvpsrkrx0000001989';
        sb.append("select o from Staff s,COS c,COrganization o");
        sb.append(" where s.staffID=c.staffID and o.organizationID=c.organizationID ");
        sb.append(" and o.companyID = ? and s.staffID=? ");
        sb.append(" and o.Status = '00'");
        sb.append(" order by o.organizationNumber");
        List<Object> params = new ArrayList<Object>();
        params.add(companyId);
        params.add(staffId);
        List<BaseBean> beanList = baseBeanDao.getListBeanByHqlAndParams(sb.toString(), params.toArray());
        return beanList;
    }

    public DetachedCriteria getBudgetBillDc(String staffId, String companyId, String type, boolean showFlag, String search, int searchType, String tenantFlag, String billsType, String approvedStatus) throws Exception {
        //1.根据手机端传过来的参数判断是否已经放入session中
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        MapSesssionUtil.getSessionForObject("personalId");
        //非个人用户
        if (StringUtils.isNotBlank(tenantFlag)) {
            if (!"personal".equals(tenantFlag)) {
                if (parmaInfor == null) {//未存入session且公司不为空，则将数据存入session
                    Map<String, Object> map = MapSesssionUtil.saveYsdSessionForMapNew(companyId, staffId);
                    parmaInfor = MapSesssionUtil.saveSessionForMap("paramMap", map);
                } else {
                    //传过来的参数不为空
                    if (StringHelper.isNotEmpty(companyId) && StringHelper.isNotEmpty(staffId)) {
                        //判断传过来的参数与session中的参数不一致
                        if (!parmaInfor.get("companyId").toString().equals(companyId) || !parmaInfor.get("staffId").toString().equals(staffId)) {
                            MapSesssionUtil.removeSession("paramMap");
                            Map<String, Object> map = MapSesssionUtil.saveYsdSessionForMapNew(companyId, staffId);
                            parmaInfor = MapSesssionUtil.saveSessionForMap("paramMap", map);
                        }
                    }
                }
            } else {
                if (StringUtils.isNotBlank(staffId)) {
                    MapSesssionUtil.saveSessionForObject("personalId", staffId);
                }
            }
        }

        DetachedCriteria dc = DetachedCriteria.forClass(CashierBills.class);//将class转换成DetachedCriteria类
        if(INIT.equals(billsType) || INPUT.equals(billsType) || OUTPUT.equals(billsType) || StringUtils.isEmpty(billsType)){
            // 项目预算列表
            dc.add(Restrictions.or(Restrictions.or(Restrictions.or(
                    Restrictions.eq("status", "00"),
                    Restrictions.eq("status", "01")), Restrictions.or(
                    Restrictions.eq("status", "02"),
                    Restrictions.eq("status", "03"))), Restrictions.or(
                    Restrictions.eq("status", "04"),
                    Restrictions.eq("status", "40"))));
        }

        if ("other".equals(tenantFlag)) {
            dc.add(Restrictions.eq("inputid", parmaInfor.get("staffId").toString()))
                    .add(Restrictions.eq("companyID", parmaInfor.get("companyId").toString()));
        }

        if ("personal".equals(tenantFlag)) {
            dc.add(Restrictions.eq("inputid", staffId)).add(Restrictions.eq("companyID", " "));
        }

        if(Objects.nonNull(approvedStatus)){
            if(!STATUS_ALL.equals(approvedStatus)){
                if(STATUS_UNAPPROVED.equals(approvedStatus)){
                    dc.add(Restrictions.ne("status", "07"))
                            .add(Restrictions.ne("status", "15"))
                            .add(Restrictions.ne("status", "16"));
                }else{
                    dc.add(Restrictions.eq("status", "07"));
                }
            }
        }

        // dc.add(Restrictions.eq("organizationID", organizationID));//不知道用处暂时注释掉
        if(Objects.nonNull(billsType) && !"初始项目单".equals(billsType) && !"收入".equals(billsType) && !"支出".equals(billsType)){
            dc.add(Restrictions.in("billsType", new Object[]{billsType}));
        }else{
            dc.add(Restrictions.in("billsType", new Object[]{"收入", "支出"}));
        }
        dc.add(Restrictions.eq("zctype", "cg"));
        dc.addOrder(Order.desc("cashierDate"));
        searchSwitch(searchType, search, dc);
        return dc;
    }

    public void splicingAddBudgetBean(GoodsManage goodsManage, CostBudgetAddBean costAddBean) throws Exception {
        String[] functionList = null;

        //1.根据扫描或输入的条形码号查询货物信息
        if (costAddBean != null) {
            StringBuffer sql = new StringBuffer();
            List<Object> parame = new ArrayList<>();
            sql.append("select gm.goodsid,pp.ppid,gm.goodsname,pp.tradeID,pp.tradeCode,pp.tradename,pp.companyid,");
            sql.append(" gm.isscale,gm.brand,s.unitofmeasurecode,gm.barcode,");
            sql.append(" pp.producttype,pp.productCode,pp.type,pp.variableID,gm.logoPath,pp.gradeid,pp.gradeName,gm.typeID,gm.GOODSCODING,gm.STANDARD ");
            sql.append(" from dtGoodsManage gm left join dt_productpackaging pp on gm.goodsid = pp.goodsid");
            sql.append(" left join dtscalegoods s on s.goodsid = gm.goodsid");
            sql.append(" where 1=1 ");
            //如果根据条码和公司查询不到物品信息，那么就根据条码查询
            if (goodsManage != null) {
                sql.append(" and pp.companyid = ?");
                parame.add(goodsManage.getCompanyID());
            }

            sql.append(" and gm.barcode = ?");
            parame.add(costAddBean.getBarCode());
            sql.append(" order by gm.createdate ");
            //查询出基本信息
            List<Object[]> prolist = baseBeanService.getListBeanBySqlAndParams(sql.toString(), parame.toArray());

            Object[] base = null;
            String goodsId = null;
            if (CollectionUtil.isNotEmpty(prolist)) {
                base = ((Object[]) prolist.get(0));
                goodsId = base[0] + "";
                //首页图片
                List<BaseBean> photoList = baseBeanService
                        .getListBeanByHqlAndParams(
                                "from AttriProduction where goodsid=? and type='2' order by sort",
                                new Object[]{goodsId});
                //首页视频
                List<BaseBean> videoList = baseBeanService
                        .getListBeanByHqlAndParams(
                                "from AttriProduction where goodsid=? and type='3' order by sort",
                                new Object[]{goodsId});
                StringBuffer photoStr = new StringBuffer();
                StringBuffer videoStr = new StringBuffer();
                if (CollectionUtil.isNotEmpty(photoList)) {
                    for (BaseBean bean : photoList) {
                        AttriProduction info = (AttriProduction) bean;
                        photoStr.append(info.getImgurl() + ",");
                    }
                    if (photoStr.length() > 0) {
                        photoStr.substring(0, photoStr.lastIndexOf(","));
                    }
                }
                if (CollectionUtil.isNotEmpty(videoList)) {
                    for (BaseBean bean : videoList) {
                        AttriProduction info = (AttriProduction) bean;
                        videoStr.append(info.getImgurl() + ",");
                    }
                    if (videoStr.length() > 0) {
                        videoStr.substring(0, videoStr.lastIndexOf(","));
                    }
                }

                //宝贝详情
                String hql = "from GoodFunction where goodsid= ?";
                if (goodsId != null && !goodsId.equals("")) {
                    GoodFunction goodFun = (GoodFunction) baseBeanService
                            .getBeanByHqlAndParams(hql, new Object[]{goodsId});
                    if (goodFun != null) {
                        String str = pmService.getContentFromFile(goodFun.getUrl());
                        if (str.length() > 0) {
                            functionList = str.split("#z");
                        }
                    }
                }

                //2.根据货物id查询库存信息
                StringBuffer sb = new StringBuffer();
                List<Object> params = new ArrayList<Object>();
                sb.append(" select o.inventoryID, o.invenQuantity from Inventory o,DepotManage dm ");
                sb.append(" where 1=1 ");
                sb.append(" and o.goodsID = ? ");
                params.add(goodsId);//货物id
                sb.append(" and o.companyID = ? ");
                params.add(costAddBean.getCompanyId());//公司id
                sb.append(" and dm.depotID = o.warehouse ");
                sb.append(" and dm.depotName = '销售库' ");
                sb.append(" and dm.depotState = '00' ");
                List<BaseBean> beanList = baseBeanDao.getListBeanByHqlAndParams(sb.toString(), params.toArray());
                if (CollectionUtil.isNotEmpty(beanList)) {
                    //获取数据
                    JSONArray jSONArray = JSONArray.fromObject(beanList.get(0));
                    costAddBean.setInvInvenQuantity(jSONArray.get(1).toString());//库存数量
                } else {
                    costAddBean.setInvInvenQuantity("0");//库存数量
                }
                //3.将参数封装成bean返回
                costAddBean.setGoodsId(base[0] + "");//货物id
                costAddBean.setPpid(base[1] + "");
                costAddBean.setGoodsName(base[2] == null ? "" : String.valueOf(base[2]));
//                costAddBean.setTradeID(base[3] == null ? "" : String.valueOf(base[3]));
//                costAddBean.setTradeCode(base[4] == null ? "" : String.valueOf(base[4]));
//                costAddBean.setTradeName(base[5] == null ? "" : String.valueOf(base[5]));
                costAddBean.setTradeID(costAddBean.getGoodsPurposeId());
                costAddBean.setTradeCode(costAddBean.getGoodsPurposeId());
                costAddBean.setTradeName(costAddBean.getGoodsPurpose());
//                costAddBean.setCompanyId(String.valueOf(base[6]));
                costAddBean.setIsScale(String.valueOf(base[7] == null ? "" : base[7]));
                costAddBean.setBrand(String.valueOf(base[8] == null ? "" : base[8]));
                costAddBean.setUnitofmeasurecode(String.valueOf(base[9] == null ? "" : base[9]));
//                costAddBean.setBarCode(String.valueOf(base[10]));
//                costAddBean.setProducttype(String.valueOf(base[11] == null ? "" : base[11]));
//                costAddBean.setProductCode(String.valueOf(base[12] == null ? "" : base[12]));
                costAddBean.setProducttype(costAddBean.getGoodsPurpose());
                costAddBean.setProductCode(costAddBean.getGoodsPurposeId());
                costAddBean.setType(String.valueOf(base[13] == null ? "" : base[13]));
                costAddBean.setVariableId(String.valueOf(base[14] == null ? "" : base[14]));
                costAddBean.setLogoPath(String.valueOf(base[15] == null ? "" : base[15]));
                costAddBean.setGradeid(String.valueOf(base[16] == null ? "" : base[16]));
                costAddBean.setGradeName(String.valueOf(base[17] == null ? "" : base[17]));
//                costAddBean.setTypeID(String.valueOf(base[18] == null ? "" : base[18]));
                costAddBean.setTypeID(costAddBean.getGoodsPurpose());
//                costAddBean.setLoanDirection("收入".equals(costAddBean.getBillsType()) ? "借" : "贷");
                costAddBean.setGoodsCoding(String.valueOf(base[19] == null ? "" : base[19]));
                costAddBean.setStandard(String.valueOf(base[20] == null ? "" : base[20]));
                costAddBean.setPhotoList(photoList);
                costAddBean.setVideoList(videoList);
                costAddBean.setPhotoStr(photoStr.toString());
                costAddBean.setVideoStr(videoStr.toString());
//                costAddBean.setTypeID(goodsManage.getTypeID());//货物类别
//                costAddBean.setGoodsCoding(goodsManage.getGoodsCoding());//货物品名编号
//                costAddBean.setGoodsName(goodsManage.getGoodsName());//货物品名名称
//                costAddBean.setStandard(goodsManage.getStandard());//货物规格
                costAddBean.setFunctionList(functionList);
            } else {
                costAddBean.setInvInvenQuantity("0");//库存数量
            }
        }
    }

    @Transactional
    public void saveCostBudgetSheet(CashierBills cashierBills) throws Exception {
        //1.获取session数据
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        String tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
        Object personalId = MapSesssionUtil.getSessionForObject("personalId");
        //2.根据手机端传过来的staffid查询状态为在职的account表数据
        String companyId = null;
        String staffId = null;
        if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
            staffId = (String) parmaInfor.get("staffId");
            companyId = (String) parmaInfor.get("companyId");
        } else if (personalId != null && "personal".equals(tenantFlag)) {
            staffId = (String) personalId;
        }

        // 通过staffid查找account用户信息
//        String sql = " from CAccount o where o.companyID = ? and o.staffID = ? and o.accountStatus = '00'";
//        CAccount account = (CAccount) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{companyId, staffId});
        // 查询登录人信息
        Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",
                new Object[]{staffId});
        // 标示数据属于哪个部门，是否就是所选部门id
        String organizationID = cashierBills.getDepartmentID();
        //3.保存订单信息
        cashierBills.setCashierBillsID(serverService.getServerID("cashierTally"));
        String parameter = "添加预算单据（凭证号：" + cashierBills.getJournalNum() + "）";
        cashierBills.setStatus("00");// 未审核
        cashierBills.setZctype("cg");//支出类型 分为常规支出cg;和非常规类型fcg;
        //判断所选项目分类是否是人事项目
        if (compareBy(companyId, cashierBills.getXmtype())) {
            cashierBills.setPaystatus("00");// 工资状态
        }
        cashierBills.setGroupCompanySn(staff.getGroupCompanySn());//集团标识
        cashierBills.setCompanyID(StringUtils.isBlank(companyId) ? " " : companyId);//公司id
        cashierBills.setCashierDate(new Date());//单据日期  下单日期
        cashierBills.setOrganizationID(organizationID);//标示数据属于哪个部门
        // 3.1制单人信息START
        cashierBills.setInputName(staff.getStaffName());//制单人名称
        cashierBills.setInputid(staff.getStaffID());//制单人id
        cashierBills.setInputOrganizationID(organizationID);//制单人所属部门
        cashierBills.setInputOrganizationName(cashierBills.getDepartmentName());//制单人部门名称
        cashierBills.setInputCompanyid(StringUtils.isBlank(companyId) ? " " : companyId);//公司id
        cashierBills.setInputCompanyName(cashierBills.getCompanyName());//公司名称
        // 制单人信息END
        //4.创建保存订单的bean
        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        baseBeanList.add(cashierBills);
        //5.循环扫码枪获取到的数据
        saveGoodsBillsNew(baseBeanList, cashierBills, companyId);
        //6.记录日志
//        CLogBook logBook = logBookService.saveCLogBook(organizationID,
//                parameter, account);
//        baseBeanList.add(logBook);
        //7.保存表
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,
                null);
    }

    public void saveGoodsBillsNew(List<BaseBean> baseBeanList, CashierBills cashierBills, String companyId) throws Exception {
        Map<String, Object> cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
        if (cacheGoodsMap != null) {//未存入session，则将数据存入session
            GoodsBills goodsBills;

            String staffID = cashierBills.getStaffID();
            String staffName = cashierBills.getStaffName();
            GoodsBillsExt goodsBillsExt;
            String sql = null;
            String sql2 = null;
            String sql3 = null;
            String hql_del = null;
            String goodsbillsId = null;
            String goodsbillsExtId = null;
            for (Object obj : cacheGoodsMap.values()) {
                goodsBills = new GoodsBills();
                goodsBillsExt = new GoodsBillsExt();
                CostBudgetAddBean addBean = (CostBudgetAddBean) obj;

                //TODO:当前公司没有的物品需要新增到物品表、产品表
                //20240803刘总说，页面不能添加产品表没有的物品数据
//                GoodsManage goodsManage = (GoodsManage) baseBeanService.getBeanByHqlAndParams("from GoodsManage o where o.companyID = ? and o.barCode = ?", new Object[]{companyId, addBean.getBarCode()});
//                GoodsManage gm = null;
//                ProductPackaging pp = null;
//                if (goodsManage == null) {
//                    gm = new GoodsManage();
//                    pp = new ProductPackaging();
//                    addGoodsAndProduct(baseBeanList, gm, pp, companyId, addBean);
//                }


                goodsBills.setCashierBillsID(cashierBills.getCashierBillsID());//收支单管理id
                goodsbillsId = serverService.getServerID("goodsbills");
                goodsBills.setGoodsBillsID(goodsbillsId);//物品单据id

                if (goodsBills.getGoodsBillsID().startsWith("goodsbills")) {
                    GoodsBillsItemRecent itemRecent = new GoodsBillsItemRecent();
                    itemRecent.setId(serverService.getServerID("itemRecent"));
                    itemRecent.setCreateDate(new Date());
                    itemRecent.setStaffId(staffID);
                    itemRecent.setStaffName(staffName);
                    itemRecent.setFlag("budget");
                    itemRecent.setBarCode(addBean.getBarCode());
                    itemRecent.setGoodsName(addBean.getGoodsName());
                    itemRecent.setGoodsbillsId(goodsbillsId);

                    sql = "  from GoodsBillsItemRecent o where o.barCode=? and o.staffId=? and o.flag='budget' ";
                    GoodsBillsItemRecent itemOld = (GoodsBillsItemRecent) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{addBean.getBarCode(), staffID});
                    if (itemOld != null) {
                        itemRecent.setKey(itemOld.getKey());
                        baseBeanDao.update(itemRecent);
                    } else {
                        baseBeanDao.save(itemRecent);
                    }
                }

                goodsBills.setCompanyID(companyId);//公司id
                goodsBills.setEndDate(new Date());// 入账时间
                goodsBills.setQuantity(addBean.getInvInvenQuantity());//库存数量
                goodsBills.setPrice(addBean.getPrice());//预算单价
                goodsBills.setMoney(addBean.getPrice());//预算金额
                goodsBills.setTiaoQuantity(addBean.getBudgetNumber());// 初始时将调整信息设置为预算信息
                goodsBills.setTiaoPrice(goodsBills.getPrice());
                goodsBills.setTiaoMoney(goodsBills.getMoney());
//                goodsBills.setGoodsID(goodsManage == null ? gm.getGoodsID() : addBean.getGoodsId());//货物id
//                goodsBills.setGoodsNum(goodsManage == null ? gm.getGoodsCoding() : addBean.getGoodsCoding());//品名编号
                goodsBills.setGoodsID(addBean.getGoodsId());//货物id
                goodsBills.setGoodsNum(addBean.getGoodsCoding());//品名编号
                goodsBills.setGoodsName(addBean.getGoodsName());//物品名称
                goodsBills.setGoodsVariableID(addBean.getVariableId());//货物单位
                goodsBills.setCcompanyName(addBean.getCurrentCompany());//往来公司名称
                goodsBills.setCcompanyID(addBean.getCurrentCompanyId());//往来公司id
                goodsBills.setConnectID(addBean.getCurrentPersonId());//往来个人id
                goodsBills.setConnectName(addBean.getCurrentPerson());//往来个人姓名
                goodsBills.setPpID(cashierBills.getProID());//项目ID也就是产品ID；
                goodsBills.setPriceManage("人民币");//单价管理
                goodsBills.setTargetStart(new Date());//目标起时间
                goodsBills.setTargetEnd(new Date());//目标止时间
                goodsBills.setTargetDeptID(cashierBills.getInputOrganizationID());//目标部门id
                goodsBills.setTargetDeptName(cashierBills.getInputOrganizationName());//目标部门名称
                goodsBills.setTargetSalerID(cashierBills.getInputid());//目标业务员id
                goodsBills.setTargetSalerName(cashierBills.getInputName());//目标业务员名称
                goodsBills.setBarCode(addBean.getBarCode());
                goodsBills.setGradeid(addBean.getGradeid());
                goodsBills.setGradeName(addBean.getGradeName());
                goodsBills.setInvInvenQuantity(addBean.getInvInvenQuantity());
                goodsBills.setTradeID(addBean.getTradeID());
                goodsBills.setTradeCode(addBean.getTradeCode());
                goodsBills.setTradeName(addBean.getTradeName());
                goodsBills.setProductCode(addBean.getProductCode());
                goodsBills.setProducttype(addBean.getProducttype());
                goodsBills.setTypeID(addBean.getTypeID());
                goodsBills.setIsScale(addBean.getIsScale());
                goodsBills.setUnitofmeasurecode(addBean.getUnitofmeasurecode());
                goodsBills.setBrand(addBean.getBrand());
                goodsBills.setGuigeType(addBean.getGuigeType());
                goodsBills.setGuigeTypeId(addBean.getGuigeTypeId());
                goodsBills.setGuigeTypeValue(addBean.getGuigeTypeValue());
                goodsBills.setUnitPrice(addBean.getUnitPrice());
                goodsBills.setAmount(addBean.getAmount());
                goodsBills.setGoodsPurpose(addBean.getGoodsPurpose());
                goodsBills.setGoodsPurposeId(addBean.getGoodsPurposeId());

                goodsBills.setServiceStartDate(addBean.getServiceStartDate());
                goodsBills.setServiceEndDate(addBean.getServiceEndDate());
                goodsBills.setConnectName(addBean.getConnectName());


//                goodsBills.setCcompanyID(addBean.getCcompanyID());
//                goodsBills.setCcompanyName(addBean.getCcompanyName());
//                goodsBills.setConnectName(addBean.getConnectName());
//                goodsBills.setConnectID(addBean.getConnectID());

                goodsBillsExt.setGoodsBillsID(goodsbillsId);
                goodsbillsExtId = serverService.getServerID("goodsbillsExt");
                goodsBillsExt.setGoodsBillsExtId(goodsbillsExtId);
                goodsBillsExt.setAccountFlag(addBean.getAccountFlag());
                goodsBillsExt.setAccountFlagFrom(addBean.getAccountFlagFrom());
                if ("company".equals(addBean.getAccountFlag())) {
                    goodsBillsExt.setAccountName(addBean.getAccountName());
                    goodsBillsExt.setAccountNameId(addBean.getAccountNameId());
                    goodsBillsExt.setAccountNum(addBean.getAccountNum());
                    goodsBillsExt.setOpenBank(addBean.getOpenBank());
                    goodsBillsExt.setAccountPhone(addBean.getAccountPhone());
                } else {
                    goodsBillsExt.setAccountName(addBean.getAccountNameP());
                    goodsBillsExt.setAccountNameId(addBean.getAccountNameIdP());
                    goodsBillsExt.setAccountNum(addBean.getAccountNumP());
                    goodsBillsExt.setOpenBank(addBean.getOpenBankP());
                    goodsBillsExt.setAccountPhone(addBean.getAccountPhoneP());
                }

                if ("company".equals(addBean.getAccountFlagFrom())) {
                    goodsBillsExt.setAccountNameFrom(addBean.getAccountNameFrom());
                    goodsBillsExt.setAccountNameIdFrom(addBean.getAccountNameIdFrom());
                    goodsBillsExt.setAccountNumFrom(addBean.getAccountNumFrom());
                    goodsBillsExt.setOpenBankFrom(addBean.getOpenBankFrom());
                    goodsBillsExt.setAccountPhoneFrom(addBean.getAccountPhoneFrom());
                } else {
                    goodsBillsExt.setAccountNameFrom(addBean.getAccountNameFromP());
                    goodsBillsExt.setAccountNameIdFrom(addBean.getAccountNameIdFromP());
                    goodsBillsExt.setAccountNumFrom(addBean.getAccountNumFromP());
                    goodsBillsExt.setOpenBankFrom(addBean.getOpenBankFromP());
                    goodsBillsExt.setAccountPhoneFrom(addBean.getAccountPhoneFromP());
                }
                goodsBillsExt.setLoanDirection(addBean.getLoanDirection());
                goodsBillsExt.setBudgetAmount(addBean.getBudgetAmount());
                goodsBillsExt.setBorrowAmount(addBean.getBorrowAmount());
                goodsBillsExt.setLoanAmount(addBean.getLoanAmount());
                goodsBillsExt.setInitialBalance(addBean.getInitialBalance());
                goodsBillsExt.setBalance(addBean.getBalance());
                goodsBillsExt.setOrderNum(addBean.getOrderNum());
                goodsBillsExt.setSpecsParentId(addBean.getSpecsParentId());
                goodsBillsExt.setTeamPunishment(addBean.getTeamPunishment());
                goodsBillsExt.setTeamReward(addBean.getTeamReward());
                goodsBillsExt.setPersonalReward(addBean.getPersonalReward());
                goodsBillsExt.setPersonalPunishment(addBean.getPersonalPunishment());

                if (StringUtils.isNotEmpty(addBean.getLogoPath())) {
                    goodsBills.setLogoPath(addBean.getLogoPath());//品牌log路径
                    //在往物品表中插入logo时，会删除数据
//                    if (goodsManage == null) {
//                        goodsBills.setLogoPath(gm.getLogoPath());
//                    } else {
//                        try {
//                            Map<String, Object> map = pmService.upLoadFile(null, null, addBean.getFileLogo().getName(), addBean.getFileLogo(), ServletActionContext.getRequest().getSession()
//                                    .getServletContext().getRealPath("\\"), companyId);
//                            goodsBills.setLogoPath(map.get("path").toString());//品牌log路径
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
                }

                //图片、视频的保存
//                String photoStr = addBean.getPhotoStr();
//                String videoStr = addBean.getVideoStr();
                List<BaseBean> photoList = addBean.getPhotoList();
                List<BaseBean> videoList = addBean.getVideoList();

                // 保存物品主图（附件是在页面选中图片上传的时候就已经上传到服务器了，这里只是把对应的路径存储到相应的数据上）
                if (CollectionUtil.isNotEmpty(photoList)) {
                    saveAttriProductionOfCostBudgetNew(photoList, goodsbillsId, baseBeanList, "I");
                }
                // 保存视频
                if (CollectionUtil.isNotEmpty(videoList)) {
                    saveAttriProductionOfCostBudgetNew(videoList, goodsbillsId, baseBeanList, "V");
                }

                // 保存产品颜色，规格
//                sql = "delete AttriProduction where goodsid=? and type='1' ";
//                sql2 = "delete AttriProduction where goodsid=? and type='0' ";
//                sql3 = "delete AttriProduction where goodsid=? and type='4' ";
                //规格数据保存
//                if (StringUtils.isNotEmpty(addBean.getColorvalue())) {
//                    pmService.saveAttriProduction(addBean.getColorvalue(), addBean.getAttrinames(), goodsbillsId, baseBeanList, "1");
//                }
//                if (StringUtils.isNotEmpty(addBean.getSizevalue())) {
//                    pmService.saveAttriProduction(addBean.getSizevalue(), addBean.getAttrinamec(), goodsbillsId, baseBeanList, "0");
//                }
                if (StringUtils.isNotEmpty(addBean.getAttri())) {
                    pmService.saveAttriProduction(addBean.getAttri(), "", goodsbillsId, baseBeanList, "4");
                }

                // 保存产品描述
                List<Object[]> params = new ArrayList<Object[]>();
                if (StringUtils.isNotEmpty(addBean.getHtl())) {
                    if (StringUtils.isNotEmpty(goodsbillsId)) {
                        String filpath = ServletActionContext.getServletContext()
                                .getRealPath("/");
                        params.add(new Object[]{goodsbillsId});
                        GoodFunction gf = (GoodFunction) baseBeanService
                                .getBeanByHqlAndParams(
                                        "from GoodFunction where goodsid= ?",
                                        new Object[]{goodsbillsId});
                        if (gf != null) {
                            FileUtil.delete(filpath + gf.getUrl());
                        }
                        GoodFunction goodFun = new GoodFunction();
                        goodFun.setGfid(serverService.getServerID("gfid"));
                        goodFun.setUrl(addBean.getHtlPath());
                        goodFun.setName("物品功能");
                        goodFun.setType("1");
                        goodFun.setGoodsid(goodsbillsId);
                        baseBeanList.add(goodFun);
                    }
                }
//                hql_del = "delete GoodFunction where goodsid= ?";
//                String barCode = addBean.getBarCode();
                baseBeanList.add(goodsBills);
                baseBeanList.add(goodsBillsExt);
            }

        }
    }

//    public void addGoodsAndProduct(List<BaseBean> baseBeanList, GoodsManage gm, ProductPackaging pp, String companyId, CostBudgetAddBean addBean) {
//        gm.setGoodsID(serverService.getServerID("goodsID"));
//        String goodsId = gm.getGoodsID();
//        System.out.print(goodsId);
//
//        if (companyId != null && !companyId.equals("")) {
//            gm.setGoodsName(addBean.getGoodsName());
//            if (addBean.getTypeID() == null || addBean.getTypeID().equals("")) {
//                gm.setTypeID("其他");
//            } else {
//                gm.setTypeID(addBean.getTypeID());
//            }
//
//            Company company = (Company) baseBeanService
//                    .getBeanByHqlAndParams(
//                            "from Company where companyID= ?",
//                            new Object[]{companyId});
//            Object[] params = {company.getGroupCompanySn(), gm.getTypeID()};
//            // 获取拼音码
//            String pinyin = ToChineseFirstLetter.getFirstLetter(gm
//                    .getTypeID());
//            String Upstr = pinyin.toUpperCase();// 转换为大写
//            // 编号处理
//            String hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.companyID in (select c.companyID from Company c where c.groupCompanySn=?)  and vt.typeID=? ";
//            int goodscodingnum = baseBeanService.getConutByByHqlAndParams(
//                    hql, params);
//            DecimalFormat form = new DecimalFormat("000000");
//            String ss = form.format(goodscodingnum + 1);
//            gm.setGoodsCoding(Upstr + "_" + ss);
//            gm.setFiveClear("4");
//            gm.setCompanyID(companyId);
//            gm.setGoodsState("00");
//            gm.setCreatedate(new Date());
//            gm.setTradeID(addBean.getTradeID());
//            gm.setTradeName(addBean.getTradeName());
//            gm.setTradeCode(addBean.getTradeCode());
//            gm.setBarCode(addBean.getBarCode());// 条码
//            gm.setBrand(addBean.getBrand());//品牌
//            if (addBean.getFileLogo() != null) {
//                gm.setLogoPath(addBean.getLogoPath());
//            }
//            gm.setIsScale(addBean.getIsScale());//是否需要电子秤打印条码
//            gm.setVariableID(addBean.getVariableId());//单位
//
//            //图片、视频的保存
////            String photoStr = addBean.getPhotoStr();
////            String videoStr = addBean.getVideoStr();
//            List<BaseBean> photoList = addBean.getPhotoList();
//            List<BaseBean> videoList = addBean.getVideoList();
//
//
//            // 保存物品主图
//            if (CollectionUtil.isNotEmpty(photoList)) {
//                saveAttriProductionOfCostBudgetNew(photoList, gm.getGoodsID(), baseBeanList, "I");
//            }
//            // 保存视频
//            if (CollectionUtil.isNotEmpty(videoList)) {
//                saveAttriProductionOfCostBudgetNew(videoList, gm.getGoodsID(), baseBeanList, "V");
//            }
//            baseBeanList.add(gm);
//            // 产品设计
//            if (gm != null && !gm.getGoodsID().equals("")) {
//                pp.setPackagingDate(new Date());
//                pp.setPpID(serverService.getServerID("p"));
//                pp.setProductCode(addBean.getProductCode());
//                pp.setProducttype(addBean.getProducttype());
//                pp.setGradeid(addBean.getGradeid());//产品等级id
//                pp.setGradeName(addBean.getGradeName());//产品等级
//                pp.setGoodsID(gm.getGoodsID());
//                pp.setGoodsName(gm.getGoodsName());
//                pp.setImage(gm.getPhotoPath());
//                pp.setTradeCode(addBean.getTradeCode());
//                pp.setTradeID(addBean.getTradeID());
//                pp.setTradeName(addBean.getTradeName());
//                pp.setType(gm.getTypeID());
//                pp.setVariableID(gm.getVariableID());
//                pp.setShowweixin("00");
//                pp.setDelStatus("00");
//                pp.setProductstate("01");//生产流程状态 初始：00；模拟：01   05：已设置利润率
//                pp.setFiveClear("4");
//                pp.setCompanyID(companyId);
//                pp.setPackagingDate(new Date());
//                pp.setBarCode(addBean.getBarCode());
//                pp.setBrand(addBean.getBrand());
//                pp.setLogo(addBean.getLogoPath());//品牌log路径
//                pp.setIsScale(addBean.getIsScale());
//                pp.setYjstatus("00");// 未设置佣金
//                //未设置佣金默认给:00
//                pp.setWholesaleStatus("00");
//                pp.setVipStatus("00");
//                pp.setActivityStatus("00");
//                pp.setField("01");//00： 字段  01：产品  02：组织机构
//                pp.setInvNum("0");//初始库存
//                pp.setStockSize(0);//库存数量
//                pp.setQualified("1");//用于产品是否合格ljc  0不合格，1合格
//                pp.setTemporary("0");//用于上传图片记忆0：未发布，1：发布成功 ljc
//                baseBeanList.add(pp);
//            }
//
//
//            if (StringUtils.isNotEmpty(addBean.getColorvalue())) {
//                pmService.saveAttriProduction(addBean.getColorvalue(), addBean.getAttrinames(), goodsId, baseBeanList, "1");
//            }
//            if (StringUtils.isNotEmpty(addBean.getSizevalue())) {
//                pmService.saveAttriProduction(addBean.getSizevalue(), addBean.getAttrinamec(), goodsId, baseBeanList, "0");
//            }
////            if (attri != null && !attri.equals("")) {
////                pmService.saveAttriProduction(attri, "", goodsId, beans, "4");
////            }
//
//            // 保存产品描述
//            List<Object[]> params2 = new ArrayList<Object[]>();
//            if (StringUtils.isNotEmpty(addBean.getHtl())) {
//
//                String hql_del = "delete GoodFunction where goodsid= ?";
//                if (goodsId != null && !goodsId.equals("")) {
//                    String filpath = ServletActionContext.getServletContext()
//                            .getRealPath("/");
//                    params2.add(new Object[]{goodsId});
//                    GoodFunction gf = (GoodFunction) baseBeanService
//                            .getBeanByHqlAndParams(
//                                    "from GoodFunction where goodsid= ?",
//                                    new Object[]{goodsId});
//                    if (gf != null) {
//                        FileUtil.delete(filpath + gf.getUrl());
//                    }
//                    GoodFunction goodFun = new GoodFunction();
//                    goodFun.setGfid(serverService.getServerID("gfid"));
//                    goodFun.setUrl(addBean.getHtlPath());
//                    goodFun.setName("物品功能");
//                    goodFun.setType("1");
//                    goodFun.setGoodsid(goodsId);
//                    baseBeanList.add(goodFun);
//                }
//            }
//            String s = "1";
//
//            if (pp != null && !pp.getPpID().equals("")) {
//                //模拟测试
//                BsimTest bt = (BsimTest) baseBeanService.getBeanByHqlAndParams("from BsimTest where id=?", new Object[]{pp.getPpID()});
//                if (bt == null) {
//                    bt = new BsimTest();
//                    bt.setBsimTestId(serverService.getServerID("testid"));
//                    bt.setGoodBar(pp.getBarCode());
//                    bt.setGoodName(pp.getGoodsName());
//                    bt.setGoodStandard(pp.getStandard());
//                    bt.setIndustryClassification(pp.getTradeCode());
//                    bt.setPrice(pp.getPrice());
//                    bt.setMoney(pp.getMoney());
//                    bt.setBtnumber(pp.getQuantity());
//                    bt.setItemNumber(pp.getProductCode());
//                    bt.setStatus("02");//合格
//                    bt.setId(pp.getPpID());
//                    bt.setAuditTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//                    baseBeanList.add(bt);
//                }
//            }
//            //保存电子秤相关
//            if ("0".equals(gm.getIsScale())) {
//                ScaleGoods scaleGoods = (ScaleGoods) baseBeanService.getBeanByHqlAndParams("from ScaleGoods where goodsID=?", new Object[]{gm.getGoodsID()});
//                if (scaleGoods == null) {
//                    scaleGoods = new ScaleGoods();
//                    scaleGoods.setSgID(serverService.getServerID("sgid"));
//                    scaleGoods.setCompanyID(companyId);
//
//                    String phql = "select count(*) from ScaleGoods where companyID = ? ";
//                    int pcount = baseBeanService.getConutByByHqlAndParams(phql, new Object[]{companyId});
//                    if (pcount != 0) {
//                        String ScaleGoodSsql = "select max(to_number(plu)) from dtScaleGoods where companyID= ?";
//                        pcount = baseBeanService.getConutByBySqlAndParams(ScaleGoodSsql,
//                                new Object[]{companyId});
//                    }
//                    //自定生成条码
//                    scaleGoods.setPlu(pcount + 1);
//                    DecimalFormat fofrm = new DecimalFormat("00000");
//                    String ap = fofrm.format(pcount + 1);
//                    scaleGoods.setAlternativeItemID(ap);//货号
//                    gm.setBarCode(ap);
//                    scaleGoods.setGoodsID(gm.getGoodsID());
//                    pp.setBarCode(ap);
//                    scaleGoods.setUnitOfMeasureCode(addBean.getUnitofmeasurecode());//计价单位前台传
//                    baseBeanList.add(scaleGoods);
//                }
//            }
//        }
//    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveAttriProduction(String filepath, String goodsbillsId, List<BaseBean> beans, String type) {
        List<BaseBean> aplist = new ArrayList<BaseBean>();
        aplist = baseBeanService
                .getListBeanByHqlAndParams(
                        "from AttriProduction where goodsid=? and type=? order by sort",
                        new Object[]{goodsbillsId, type.equals("I") ? "2" : "3"});
        String path = ServletActionContext.getRequest()
                .getSession().getServletContext().getRealPath("/");
        String photopath = "";
        AttriProduction attrp = null;
        String[] photos = filepath.split(",");
        for (int i = 0; i < photos.length; i++) {
            attrp = new AttriProduction();
            photopath = photos[i];
            attrp.setApid(serverService.getServerID("apid"));
            attrp.setGoodsid(goodsbillsId);
            switch (type) {
                case "I":
                    String jjPath = photopath.split("\\.")[0] + "small." + photopath.split("\\.")[1];
                    ImageCut.scale(path + photopath, path + jjPath, 414, 431);
//                    if (i == 0) {// 第一张图片保存在goodsmanage表，同时也保存attriproduction表
//                        gm.setPhotoPath(jjPath);
//                    }
                    attrp.setType("2");
                    attrp.setImgurl(jjPath);
                    break;
                case "V":
                    attrp.setType("3");
                    attrp.setImgurl(photopath);
                    break;
            }
            if (aplist != null && aplist.size() > 0) {
                AttriProduction a = (AttriProduction) aplist.get(aplist.size() - 1);
                attrp.setSort(a.getSort() + i + 1);
            } else {
                attrp.setSort(i + 1);
            }
            beans.add(attrp);
        }
    }

    public void getBudgetItemInfo(GoodsBills goodsBills, GoodsBillsExt ext, CostBudgetAddBean costAddBean) {
        String[] functionList = null;
        if (goodsBills != null) {
            BeanUtils.copyProperties(goodsBills, costAddBean);
            costAddBean.setGoodsId(goodsBills.getGoodsID());
            costAddBean.setGoodsBillsExtId(ext.getGoodsBillsExtId());
            costAddBean.setAccountFlag(ext.getAccountFlag());
            costAddBean.setAccountFlagFrom(ext.getAccountFlagFrom());
            costAddBean.setTeamPunishment(ext.getTeamPunishment());
            costAddBean.setTeamReward(ext.getTeamReward());
            costAddBean.setPersonalPunishment(ext.getPersonalPunishment());
            costAddBean.setPersonalReward(ext.getPersonalReward());

            if ("company".equals(ext.getAccountFlag()) || "companyPlan".equals(ext.getAccountFlag())) {
                costAddBean.setAccountNameId(ext.getAccountNameId());
                costAddBean.setAccountName(ext.getAccountName());
                costAddBean.setAccountNum(ext.getAccountNum());
                costAddBean.setAccountPhone(ext.getAccountPhone());
                costAddBean.setOpenBank(ext.getOpenBank());
            } else {
                costAddBean.setAccountNameIdP(ext.getAccountNameId());
                costAddBean.setAccountNameP(ext.getAccountName());
                costAddBean.setAccountNumP(ext.getAccountNum());
                costAddBean.setAccountPhoneP(ext.getAccountPhone());
                costAddBean.setOpenBankP(ext.getOpenBank());
            }

            if ("company".equals(ext.getAccountFlagFrom()) || "companyPlan".equals(ext.getAccountFlagFrom())) {
                costAddBean.setAccountNameIdFrom(ext.getAccountNameIdFrom());
                costAddBean.setAccountNameFrom(ext.getAccountNameFrom());
                costAddBean.setAccountNumFrom(ext.getAccountNumFrom());
                costAddBean.setAccountPhoneFrom(ext.getAccountPhoneFrom());
                costAddBean.setOpenBankFrom(ext.getOpenBankFrom());
            } else {
                costAddBean.setAccountNameIdFromP(ext.getAccountNameIdFrom());
                costAddBean.setAccountNameFromP(ext.getAccountNameFrom());
                costAddBean.setAccountNumFromP(ext.getAccountNumFrom());
                costAddBean.setAccountPhoneFromP(ext.getAccountPhoneFrom());
                costAddBean.setOpenBankFromP(ext.getOpenBankFrom());
            }

            costAddBean.setBorrowAmount(ext.getBorrowAmount());
            costAddBean.setLoanAmount(ext.getLoanAmount());
            costAddBean.setBudgetAmount(ext.getBudgetAmount());
            costAddBean.setLoanDirection(ext.getLoanDirection());
            costAddBean.setBalance(ext.getBalance());
            costAddBean.setInitialBalance(ext.getInitialBalance());
            costAddBean.setSpecsParentId(ext.getSpecsParentId());
            costAddBean.setTeamPunishmentCut(ext.getTeamPunishment());
            costAddBean.setTeamReward(ext.getTeamReward());
            costAddBean.setPersonalReward(ext.getPersonalReward());
            costAddBean.setPersonalPunishment(ext.getPersonalPunishment());

            String goodsId = goodsBills.getGoodsBillsID();
            //首页图片
            List<BaseBean> photoList = baseBeanService
                    .getListBeanByHqlAndParams(
                            "from AttriProduction where goodsid=? and type='2' order by sort",
                            new Object[]{goodsId});
            //首页视频
            List<BaseBean> videoList = baseBeanService
                    .getListBeanByHqlAndParams(
                            "from AttriProduction where goodsid=? and type='3' order by sort",
                            new Object[]{goodsId});
            StringBuffer photoStr = new StringBuffer();
            StringBuffer videoStr = new StringBuffer();
            if (CollectionUtil.isNotEmpty(photoList)) {
                for (BaseBean bean : photoList) {
                    AttriProduction info = (AttriProduction) bean;
                    photoStr.append(info.getImgurl() + ",");
                }
                if (photoStr.length() > 0) {
                    photoStr.substring(0, photoStr.lastIndexOf(","));
                }
            }
            if (CollectionUtil.isNotEmpty(videoList)) {
                for (BaseBean bean : videoList) {
                    AttriProduction info = (AttriProduction) bean;
                    videoStr.append(info.getImgurl() + ",");
                }
                if (videoStr.length() > 0) {
                    videoStr.substring(0, videoStr.lastIndexOf(","));
                }
            }

            //规格
            List<BaseBean> attriList = baseBeanService
                    .getListBeanByHqlAndParams(
                            "from AttriProduction where goodsid=? and type='4' order by sort",
                            new Object[]{goodsId});
            StringBuffer attri = new StringBuffer();
            HashMap<String, String> attriMap = new HashMap<>();

            if (CollectionUtil.isNotEmpty(attriList)) {
                for (BaseBean bean : attriList) {
                    AttriProduction info = (AttriProduction) bean;
                    attriMap.put(info.getAttriname(), info.getAttrivalue());
                    attri.append(info.getAttriname() + "-" + info.getAttrivalue() + ",");
                }
                if (attri.length() > 0) {
                    attri.substring(0, attri.lastIndexOf(","));
                }
                costAddBean.setAttriJson(JSONObject.fromObject(attriMap).toString());
                costAddBean.setAttri(attri.toString());
            }

            //宝贝详情
            String hql = "from GoodFunction where goodsid= ?";
            if (goodsId != null && !goodsId.equals("")) {
                GoodFunction goodFun = (GoodFunction) baseBeanService
                        .getBeanByHqlAndParams(hql, new Object[]{goodsId});
                if (goodFun != null) {
                    String str = pmService.getContentFromFile(goodFun.getUrl());
                    if (str.length() > 0) {
                        functionList = str.split("#z");
                    }
                }
            }

            //3.将参数封装成bean返回
//            costAddBean.setGoodsId(base[0] + "");//货物id
//            costAddBean.setPpid(base[1] + "");
//            costAddBean.setGoodsName(String.valueOf(base[2]));
//            costAddBean.setTradeID(String.valueOf(base[3]));
//            costAddBean.setTradeCode(String.valueOf(base[4]));
//            costAddBean.setTradeName(String.valueOf(base[5]));
////                costAddBean.setCompanyId(String.valueOf(base[6]));
//            costAddBean.setIsScale(String.valueOf(base[7] == null ? "" : base[7]));
//            costAddBean.setBrand(String.valueOf(base[8] == null ? "" : base[8]));
//            costAddBean.setUnitofmeasurecode(String.valueOf(base[9] == null ? "" : base[9]));
////                costAddBean.setBarCode(String.valueOf(base[10]));
//            costAddBean.setProducttype(String.valueOf(base[11] == null ? "" : base[11]));
//            costAddBean.setProductCode(String.valueOf(base[12] == null ? "" : base[12]));
//            costAddBean.setType(String.valueOf(base[13] == null ? "" : base[13]));
//            costAddBean.setVariableId(String.valueOf(base[14] == null ? "" : base[14]));
//            costAddBean.setLogoPath(String.valueOf(base[15] == null ? "" : base[15]));
//            costAddBean.setGradeid(String.valueOf(base[16] == null ? "" : base[16]));
//            costAddBean.setGradeName(String.valueOf(base[17] == null ? "" : base[17]));
//            costAddBean.setTypeID(String.valueOf(base[18] == null ? "" : base[18]));
            costAddBean.setPhotoList(photoList);
            costAddBean.setVideoList(videoList);
            costAddBean.setPhotoStr(photoStr.toString());
            costAddBean.setVideoStr(videoStr.toString());
            costAddBean.setVariableId(goodsBills.getGoodsVariableID());
            costAddBean.setBudgetNumber(goodsBills.getTiaoQuantity());
            costAddBean.setPrice(goodsBills.getPrice());
//            costAddBean.setTypeID(goodsManage.getTypeID());//货物类别
//            costAddBean.setGoodsCoding(goodsManage.getGoodsCoding());//货物品名编号
//            costAddBean.setGoodsName(goodsManage.getGoodsName());//货物品名名称
//            costAddBean.setStandard(goodsManage.getStandard());//货物规格
            costAddBean.setFunctionList(functionList);
        }
    }

    @Transactional
    @Override
    public void getUpdateCostBudgetItem(Map<String, Object> cacheGoodsMap, List<BaseBean> list, CostBudgetAddBean costAddBean) {
        cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
        Map<String, Object> scanGoodsMap = MapSesssionUtil.getSessionForMap("scanGoodsMap");
        //每次请求结束时，临时文件会被删除，所以在这里就开始上传到系统操作
        if (costAddBean != null) {
            if (costAddBean.getFileLogo() != null) {
                try {
                    Map<String, Object> map = pmService.upLoadFile(null, null, costAddBean.getFileLogo().getName(), costAddBean.getFileLogo(), ServletActionContext.getRequest().getSession()
                            .getServletContext().getRealPath("\\"), costAddBean.getCompanyId());
                    costAddBean.setLogoPath(map.get("path").toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 保存产品描述
            List<Object[]> params = new ArrayList<Object[]>();
            String url = "", newhtl = "", oldhtl = "", temp = "", photopath = "", htl = "";
            String path = ServletActionContext.getRequest().getContextPath();
            String basePath = ServletActionContext.getRequest().getScheme() + "://"
                    + ServletActionContext.getRequest().getServerName() + ":"
                    + ServletActionContext.getRequest().getServerPort() + path
                    + "/";
            if (StringUtils.isNotEmpty(costAddBean.getHtl())) {
                if (costAddBean.getPic() != null && costAddBean.getPic().length > 0) {
                    for (int i = 0; i < costAddBean.getPic().length; i++) {
                        newhtl = costAddBean.getHtl().substring(0, costAddBean.getHtl().indexOf("img" + i));
                        temp = costAddBean.getHtl().substring(costAddBean.getHtl().indexOf("img" + i));
                        oldhtl = temp.substring(temp.indexOf(" height: auto;\">"));
                        String path1 = ServletActionContext.getRequest()
                                .getSession().getServletContext().getRealPath("/");
                        photopath = fileService.savePhoto(
                                path1,
                                costAddBean.getPicFileName()[i],
                                costAddBean.getPic()[i],
                                costAddBean.getCompanyId(),
                                "/goodfunction/"
                                        + Utilities.getDateString(new Date(),
                                        "yyyy-MM-dd"));
                        htl = newhtl + "img" + i + "\" src=\"" + basePath
                                + photopath
                                + "\" style=\"display: block; width: 100%;"
                                + oldhtl;
                    }
                } else {
                    htl = costAddBean.getHtl();
                }
                url = pmService.saveContentToFile(htl.trim());
                costAddBean.setHtlPath(url);
                //宝贝详情
                String str = pmService.getContentFromFile(costAddBean.getHtlPath());
                if (str.length() > 0) {
                    costAddBean.setFunctionList(str.split("#z"));
                }
            }
            //如果是新增功能里面的规格，先不管，处理起来太麻烦，保存之后就有了
//            if (StringUtils.isNotEmpty(addBean.getColorvalue())) {
//                pmService.saveAttriProduction(addBean.getColorvalue(), addBean.getAttrinames(), goodsbillsId, baseBeanList, "1");
//            }
//            if (StringUtils.isNotEmpty(addBean.getSizevalue())) {
//                pmService.saveAttriProduction(addBean.getSizevalue(), addBean.getAttrinamec(), goodsbillsId, baseBeanList, "0");
//            }

            //首页图片
            String photoStr = costAddBean.getPhotoStr();
            String videoStr = costAddBean.getVideoStr();

            List<BaseBean> photoList = new ArrayList<>();
            // 保存物品主图（附件是在页面选中图片上传的时候就已经上传到服务器了，这里只是把对应的路径存储到相应的数据上）
            if (StringUtils.isNotEmpty(photoStr)) {
                AttriProduction photo = null;
                String[] photoArr = photoStr.split(",");
                for (String s : photoArr) {
                    photo = new AttriProduction();
                    photo.setImgurl(s);
                    photoList.add(photo);
                }
            }
            costAddBean.setPhotoList(photoList);

            //首页视频
            List<BaseBean> videoList = new ArrayList<>();
            // 保存视频
            if (StringUtils.isNotEmpty(videoStr)) {
                AttriProduction video = null;
                String[] videoArr = videoStr.split(",");
                for (String s : videoArr) {
                    video = new AttriProduction();
                    video.setImgurl(s);
                    videoList.add(video);
                }
            }
            costAddBean.setVideoList(videoList);


            if (StringUtils.isNotEmpty(costAddBean.getAttri())) {
                String attri = costAddBean.getAttri();
                String[] values = attri.split(",");
                HashMap<String, String> attriMap = new HashMap<>();
                for (int i = 0; i < values.length; i++) {
                    String[] a = values[i].split("-", 2);
                    attriMap.put(a[0], a[1]);
                }
                costAddBean.setAttriJson(JSONObject.fromObject(attriMap).toString());
            }
        }


        if (cacheGoodsMap == null) {//未存入session，则将数据存入session
            cacheGoodsMap = new LinkedHashMap<String, Object>();
            MapSesssionUtil.saveSessionForMap("cacheGoodsMap", cacheGoodsMap);
        }

        if (scanGoodsMap != null) {
            cacheGoodsMap.putAll(scanGoodsMap);
            MapSesssionUtil.removeSession("scanGoodsMap");
        }

        boolean flag = true;
        for (String s : cacheGoodsMap.keySet()) {
            if (s.startsWith("goodsbills")) {
                flag = false;
                break;
            }
        }


        if (CollectionUtil.isNotEmpty(list) && flag) {
            CostBudgetAddBean info = null;
            GoodsBills bill = null;
            GoodsBillsExt ext = null;
            for (int i = 0; i < list.size(); i++) {
                JSONArray arr = JSONArray.fromObject(list.get(i));
                bill = (GoodsBills) JSONObject.toBean(JSONObject.fromObject(arr.get(0)), GoodsBills.class);
                ext = (GoodsBillsExt) JSONObject.toBean(JSONObject.fromObject(arr.get(1)), GoodsBillsExt.class);
                info = new CostBudgetAddBean();
                getBudgetItemInfo(bill, ext, info);
                cacheGoodsMap.put(bill.getGoodsBillsID(), info);
            }
        }
        //goodsBillsID不为空，证明是修改操作
        if (costAddBean != null) {
            if (StringUtils.isNotEmpty(costAddBean.getGoodsBillsID())) {
                cacheGoodsMap.put(costAddBean.getGoodsBillsID(), costAddBean);
            } else {
                String key = UUID.randomUUID().toString();
                costAddBean.setGoodsBillsID(key);
                costAddBean.setOrderNum(new Date().getTime());
                cacheGoodsMap.put(key, costAddBean);
            }

            //账户信息不为空时，将账户信息保存到最近联系人
            //根据账号和创建人id查询最近填写的账户数据是否存在，若存在，则删除之前的，插入现在最新的，若不存在，直接插入
            if (StringUtils.isNotEmpty(costAddBean.getAccountFlag())) {
                Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
                String tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
                Object personalId = MapSesssionUtil.getSessionForObject("personalId");
                //2.根据手机端传过来的staffid查询状态为在职的account表数据
                String companyId = null;
                String staffId = null;
                if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                    staffId = (String) parmaInfor.get("staffId");
                    companyId = (String) parmaInfor.get("companyId");
                } else if (personalId != null && "personal".equals(tenantFlag)) {
                    staffId = (String) personalId;
                }

                // 查询登录人信息
                Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",
                        new Object[]{staffId});


                if (StringUtils.isNotEmpty(costAddBean.getAccountNumP()) || StringUtils.isNotEmpty(costAddBean.getAccountNum())) {
                    GoodsBillsContactRecent recent = new GoodsBillsContactRecent();

                    recent.setId(serverService.getServerID("recent"));
                    recent.setAccountFlag(costAddBean.getAccountFlag());
                    if ("company".equals(costAddBean.getAccountFlag()) || "companyPlan".equals(costAddBean.getAccountFlag())) {
                        recent.setAccountName(costAddBean.getAccountName());
                        recent.setAccountNameId(costAddBean.getAccountNameId());
                        recent.setAccountNum(costAddBean.getAccountNum());
                        recent.setOpenBank(costAddBean.getOpenBank());
                        recent.setAccountPhone(costAddBean.getAccountPhone());
                    } else {
                        recent.setAccountName(costAddBean.getAccountNameP());
                        recent.setAccountNameId(costAddBean.getAccountNameIdP());
                        recent.setAccountNum(costAddBean.getAccountNumP());
                        recent.setOpenBank(costAddBean.getOpenBankP());
                        recent.setAccountPhone(costAddBean.getAccountPhoneP());
                    }

                    recent.setCreateDate(new Date());
                    recent.setStaffId(staffId);
                    recent.setStaffName(staff.getStaffName());

                    String sql = null;
                    if ("company".equals(costAddBean.getAccountFlag()) || "personal".equals(costAddBean.getAccountFlag())) {
                        sql = "  from GoodsBillsContactRecent o where o.accountNum=? and o.staffId=?  and o.accountFlag in ('personal','company')";
                    } else if ("companyPlan".equals(costAddBean.getAccountFlag()) || "personalPlan".equals(costAddBean.getAccountFlag())) {
                        sql = "  from GoodsBillsContactRecent o where o.accountNum=? and o.staffId=?  and o.accountFlag in ('personalPlan','companyPlan')";
                    }


                    GoodsBillsContactRecent recentOld = (GoodsBillsContactRecent) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{recent.getAccountNum(), staffId});
                    if (recentOld != null) {
                        recent.setKey(recentOld.getKey());
                        baseBeanDao.update(recent);
                    } else {
                        baseBeanDao.save(recent);
                    }
                }

                if (StringUtils.isNotEmpty(costAddBean.getAccountNumFromP()) || StringUtils.isNotEmpty(costAddBean.getAccountNumFrom())) {
                    GoodsBillsContactRecent recent = new GoodsBillsContactRecent();

                    recent.setId(serverService.getServerID("recent"));
                    recent.setAccountFlag(costAddBean.getAccountFlagFrom());
                    if ("company".equals(costAddBean.getAccountFlagFrom()) || "companyPlan".equals(costAddBean.getAccountFlagFrom())) {
                        recent.setAccountName(costAddBean.getAccountNameFrom());
                        recent.setAccountNameId(costAddBean.getAccountNameIdFrom());
                        recent.setAccountNum(costAddBean.getAccountNumFrom());
                        recent.setOpenBank(costAddBean.getOpenBankFrom());
                        recent.setAccountPhone(costAddBean.getAccountPhoneFrom());
                    } else {
                        recent.setAccountName(costAddBean.getAccountNameFromP());
                        recent.setAccountNameId(costAddBean.getAccountNameIdFromP());
                        recent.setAccountNum(costAddBean.getAccountNumFromP());
                        recent.setOpenBank(costAddBean.getOpenBankFromP());
                        recent.setAccountPhone(costAddBean.getAccountPhoneFromP());
                    }

                    recent.setCreateDate(new Date());
                    recent.setStaffId(staffId);
                    recent.setStaffName(staff.getStaffName());

                    String sql = null;
                    if ("company".equals(costAddBean.getAccountFlagFrom()) || "personal".equals(costAddBean.getAccountFlagFrom())) {
                        sql = "  from GoodsBillsContactRecent o where o.accountNum=? and o.staffId=?  and o.accountFlag in ('personal','company')";
                    } else if ("companyPlan".equals(costAddBean.getAccountFlagFrom()) || "personalPlan".equals(costAddBean.getAccountFlagFrom())) {
                        sql = "  from GoodsBillsContactRecent o where o.accountNum=? and o.staffId=?  and o.accountFlag in ('personalPlan','companyPlan')";
                    }
                    GoodsBillsContactRecent recentOld = (GoodsBillsContactRecent) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{recent.getAccountNum(), staffId});
                    if (recentOld != null) {
                        recent.setKey(recentOld.getKey());
                        baseBeanDao.update(recent);
                    } else {
                        baseBeanDao.save(recent);
                    }
                }
            }
        }

        Set<String> keySet = cacheGoodsMap.keySet();
        CostBudgetAddBean lastBean = null;
        for (String s : keySet) {
            CostBudgetAddBean bean = (CostBudgetAddBean) cacheGoodsMap.get(s);
            bean.setBudgetAmount(StringUtils.isNotEmpty(bean.getAmount()) ? ("收".equals(bean.getLoanDirection()) ? bean.getAmount() : "-" + bean.getAmount()) : "");
            if (lastBean == null) {
                bean.setBalance(bean.getBudgetAmount());
                bean.setInitialBalance("0");
            } else {
                bean.setInitialBalance(lastBean.getBalance());
                bean.setBalance(new BigDecimal(bean.getBudgetAmount()).add(new BigDecimal(lastBean.getBalance())).toString());
            }
            lastBean = bean;
        }

    }

    @Transactional
    public void updateCostBudgetSheet(CashierBills cashierBills, String menuId) throws Exception {
        //1.获取session数据
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        String tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
        Object personalId = MapSesssionUtil.getSessionForObject("personalId");
        //2.根据手机端传过来的staffid查询状态为在职的account表数据
        String companyId = null;
        String staffId = null;
        if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
            staffId = (String) parmaInfor.get("staffId");
            companyId = (String) parmaInfor.get("companyId");
        } else if (personalId != null && "personal".equals(tenantFlag)) {
            staffId = (String) personalId;
        }
        // 通过staffid查找account用户信息
//        String sql = " from CAccount o where o.companyID = ? and o.staffID = ? and o.accountStatus = '00'";
//        CAccount account = (CAccount) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{companyId, staffId});
        // 查询登录人信息
        Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",
                new Object[]{staffId});
        //创建保存订单的bean
        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        // 标示数据属于哪个部门，是否就是所选部门id
        String organizationID = cashierBills.getDepartmentID();
        //3.保存订单信息
//        cashierBills.setCashierBillsID(serverService.getServerID("cashierTally"));
//        String parameter = "添加预算单据（凭证号：" + cashierBills.getJournalNum() + "）";

        //收件页面的修改功能
        if ("receive".equals(menuId)) {
            CashierBills oldCashierBills = (CashierBills) baseBeanDao.getBeanByHqlAndParams(" from CashierBills o where o.cashierBillsID = ?", new Object[]{cashierBills.getCashierBillsID()});
//            oldCashierBills.setGroupCompanySn(staff.getGroupCompanySn());//集团标识
//            oldCashierBills.setCompanyID(companyId);//公司id
//            oldCashierBills.setCashierDate(new Date());//单据日期  下单日期
//            oldCashierBills.setOrganizationID(organizationID);//标示数据属于哪个部门
//            oldCashierBills.setInputName(staff.getStaffName());//制单人名称
//            oldCashierBills.setInputid(staff.getStaffID());//制单人id
//            oldCashierBills.setInputOrganizationID(organizationID);//制单人所属部门
//            oldCashierBills.setInputOrganizationName(cashierBills.getDepartmentName());//制单人部门名称
//            oldCashierBills.setInputCompanyid(companyId);//公司id
//            oldCashierBills.setInputCompanyName(cashierBills.getCompanyName());//公司名称
//            baseBeanList.add(oldCashierBills);
            cashierBills.setStatus(oldCashierBills.getStatus());
            cashierBills.setZctype(oldCashierBills.getZctype());//支出类型 分为常规支出cg;和非常规类型fcg;
            cashierBills.setResponsible(oldCashierBills.getResponsible());
            //判断所选项目分类是否是人事项目
            if (compareBy(companyId, cashierBills.getXmtype())) {
                cashierBills.setPaystatus("00");// 工资状态
            }
            cashierBills.setGroupCompanySn(staff.getGroupCompanySn());//集团标识
            cashierBills.setCompanyID(StringUtils.isBlank(companyId) ? " " : companyId);//公司id
            cashierBills.setCashierDate(new Date());//单据日期  下单日期
            cashierBills.setOrganizationID(organizationID);//标示数据属于哪个部门
            // 3.1制单人信息START
            cashierBills.setInputName(staff.getStaffName());//制单人名称
            cashierBills.setInputid(staff.getStaffID());//制单人id
            cashierBills.setInputOrganizationID(organizationID);//制单人所属部门
            cashierBills.setInputOrganizationName(cashierBills.getDepartmentName());//制单人部门名称
            cashierBills.setInputCompanyid(StringUtils.isBlank(companyId) ? " " : companyId);//公司id
            cashierBills.setInputCompanyName(cashierBills.getCompanyName());//公司名称
            // 制单人信息END
            baseBeanList.add(cashierBills);
        } else {
            cashierBills.setStatus("00");// 未审核
            cashierBills.setZctype("cg");//支出类型 分为常规支出cg;和非常规类型fcg;
            //判断所选项目分类是否是人事项目
            if (compareBy(companyId, cashierBills.getXmtype())) {
                cashierBills.setPaystatus("00");// 工资状态
            }
            cashierBills.setGroupCompanySn(staff.getGroupCompanySn());//集团标识
            cashierBills.setCompanyID(StringUtils.isBlank(companyId) ? " " : companyId);//公司id
            cashierBills.setCashierDate(new Date());//单据日期  下单日期
            cashierBills.setOrganizationID(organizationID);//标示数据属于哪个部门
            // 3.1制单人信息START
            cashierBills.setInputName(staff.getStaffName());//制单人名称
            cashierBills.setInputid(staff.getStaffID());//制单人id
            cashierBills.setInputOrganizationID(organizationID);//制单人所属部门
            cashierBills.setInputOrganizationName(cashierBills.getDepartmentName());//制单人部门名称
            cashierBills.setInputCompanyid(StringUtils.isBlank(companyId) ? " " : companyId);//公司id
            cashierBills.setInputCompanyName(cashierBills.getCompanyName());//公司名称
            // 制单人信息END
            baseBeanList.add(cashierBills);
        }
        //5.循环扫码枪获取到的数据
        updateGoodsBillsNew(baseBeanList, cashierBills, companyId);
        //6.记录日志
//        CLogBook logBook = logBookService.saveCLogBook(organizationID,
//                parameter, account);
//        baseBeanList.add(logBook);
        //先删除之前的明细数据
        String sql2 = " delete from GoodsBills o where o.cashierBillsID=? ";

        //7.保存表
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList, new String[]{sql2},
                new Object[]{cashierBills.getCashierBillsID()});
    }

    public void updateGoodsBillsNew(List<BaseBean> baseBeanList, CashierBills cashierBills, String companyId) throws Exception {
        Map<String, Object> cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
        if (cacheGoodsMap != null) {//未存入session，则将数据存入session
            GoodsBills goodsBills;
            GoodsBillsExt goodsBillsExt;
            String staffID = cashierBills.getStaffID();
            String staffName = cashierBills.getStaffName();
//            String sql = null;
//            String sql2 = null;
//            String sql3 = null;
//            String hql_del = null;
            String goodsbillsId = null;

            for (Object obj : cacheGoodsMap.values()) {
                CostBudgetAddBean addBean = (CostBudgetAddBean) obj;

                //预算单（招标投标）关联的明细项目编号如果以goodsbills，代表是已经存在的记录
                String goodsBillsID = addBean.getGoodsBillsID();
                String goodsBillsExtId = addBean.getGoodsBillsExtId();
                if (StringUtils.isNotEmpty(goodsBillsID) && goodsBillsID.startsWith("goodsbills")) {
                    goodsBills = (GoodsBills) baseBeanService
                            .getBeanByHqlAndParams("from GoodsBills where goodsBillsID=?",
                                    new Object[]{goodsBillsID});
                    goodsbillsId = goodsBills.getGoodsBillsID();
                } else {
                    goodsBills = new GoodsBills();
                    goodsbillsId = serverService.getServerID("goodsbills");
                    goodsBills.setGoodsBillsID(goodsbillsId);//物品单据id

                    if (goodsBills.getGoodsBillsID().startsWith("goodsbills")) {
                        GoodsBillsItemRecent itemRecent = new GoodsBillsItemRecent();
                        itemRecent.setId(serverService.getServerID("itemRecent"));
                        itemRecent.setCreateDate(new Date());
                        itemRecent.setStaffId(staffID);
                        itemRecent.setStaffName(staffName);
                        itemRecent.setFlag("budget");
                        itemRecent.setBarCode(addBean.getBarCode());
                        itemRecent.setGoodsName(addBean.getGoodsName());
                        itemRecent.setGoodsbillsId(goodsbillsId);

                        String sql = "  from GoodsBillsItemRecent o where o.barCode=? and o.staffId=? and o.flag='budget' ";
                        GoodsBillsItemRecent itemOld = (GoodsBillsItemRecent) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{addBean.getBarCode(), staffID});
                        if (itemOld != null) {
                            itemRecent.setKey(itemOld.getKey());
                            baseBeanDao.update(itemRecent);
                        } else {
                            baseBeanDao.save(itemRecent);
                        }
                    }

                    //TODO:当前公司没有的物品需要新增到物品表、产品表
                    //20240803刘总说，页面不能添加产品表没有的物品数据
//                    GoodsManage goodsManage = (GoodsManage) baseBeanService.getBeanByHqlAndParams("from GoodsManage o where o.companyID = ? and o.barCode = ?", new Object[]{companyId, addBean.getBarCode()});
//                    GoodsManage gm = null;
//                    ProductPackaging pp = null;
//                    if (goodsManage == null) {
//                        gm = new GoodsManage();
//                        pp = new ProductPackaging();
//                        addGoodsAndProduct(baseBeanList, gm, pp, companyId, addBean);
//                    }
                }

                if (StringUtils.isNotEmpty(goodsBillsExtId) && goodsBillsExtId.startsWith("goodsbillsExt")) {
                    goodsBillsExt = (GoodsBillsExt) baseBeanService
                            .getBeanByHqlAndParams("from GoodsBillsExt where goodsBillsExtId=?",
                                    new Object[]{goodsBillsExtId});
                } else {
                    goodsBillsExt = new GoodsBillsExt();
                    goodsBillsExtId = serverService.getServerID("goodsbillsExt");
                    goodsBillsExt.setGoodsBillsExtId(goodsBillsExtId);
                    goodsBillsExt.setGoodsBillsID(goodsbillsId);//物品单据id
                }
                goodsBills.setCashierBillsID(cashierBills.getCashierBillsID());//收支单管理id
                goodsBills.setCompanyID(companyId);//公司id
                goodsBills.setEndDate(new Date());// 入账时间
                goodsBills.setQuantity(addBean.getInvInvenQuantity());//库存数量
                goodsBills.setPrice(addBean.getPrice());//预算单价
                goodsBills.setMoney(addBean.getPrice());//预算金额
                goodsBills.setTiaoQuantity(addBean.getBudgetNumber());// 初始时将调整信息设置为预算信息
                goodsBills.setTiaoPrice(goodsBills.getPrice());
                goodsBills.setTiaoMoney(goodsBills.getMoney());
                if(StringUtils.isNotEmpty(addBean.getGoodsId())){
                    goodsBills.setGoodsID(addBean.getGoodsId());//货物id
                }
                goodsBills.setGoodsNum(addBean.getGoodsCoding());//品名编号
                goodsBills.setGoodsName(addBean.getGoodsName());//物品名称
                goodsBills.setGoodsVariableID(addBean.getVariableId());//货物单位
                goodsBills.setCcompanyName(addBean.getCurrentCompany());//往来公司名称
                goodsBills.setCcompanyID(addBean.getCurrentCompanyId());//往来公司id
                goodsBills.setConnectID(addBean.getCurrentPersonId());//往来个人id
                goodsBills.setConnectName(addBean.getCurrentPerson());//往来个人姓名
                goodsBills.setPpID(cashierBills.getProID());//项目ID也就是产品ID；
                goodsBills.setPriceManage("人民币");//单价管理
                goodsBills.setTargetStart(new Date());//目标起时间
                goodsBills.setTargetEnd(new Date());//目标止时间
                goodsBills.setTargetDeptID(cashierBills.getInputOrganizationID());//目标部门id
                goodsBills.setTargetDeptName(cashierBills.getInputOrganizationName());//目标部门名称
                goodsBills.setTargetSalerID(cashierBills.getInputid());//目标业务员id
                goodsBills.setTargetSalerName(cashierBills.getInputName());//目标业务员名称
                goodsBills.setBarCode(addBean.getBarCode());
                goodsBills.setGradeid(addBean.getGradeid());
                goodsBills.setGradeName(addBean.getGradeName());
                goodsBills.setInvInvenQuantity(addBean.getInvInvenQuantity());
                goodsBills.setTradeID(addBean.getTradeID());
                goodsBills.setTradeCode(addBean.getTradeCode());
                goodsBills.setTradeName(addBean.getTradeName());
                goodsBills.setProductCode(addBean.getProductCode());
                goodsBills.setProducttype(addBean.getProducttype());
                goodsBills.setTypeID(addBean.getTypeID());
                goodsBills.setIsScale(addBean.getIsScale());
                goodsBills.setUnitofmeasurecode(addBean.getUnitofmeasurecode());
                goodsBills.setBrand(addBean.getBrand());
                goodsBills.setGuigeType(addBean.getGuigeType());
                goodsBills.setGuigeTypeId(addBean.getGuigeTypeId());
                goodsBills.setGuigeTypeValue(addBean.getGuigeTypeValue());
                goodsBills.setUnitPrice(addBean.getUnitPrice());
                goodsBills.setAmount(addBean.getAmount());
                goodsBills.setGoodsPurpose(addBean.getGoodsPurpose());
                goodsBills.setGoodsPurposeId(addBean.getGoodsPurposeId());
                goodsBills.setServiceStartDate(addBean.getServiceStartDate());
                goodsBills.setServiceEndDate(addBean.getServiceEndDate());
                goodsBills.setConnectName(addBean.getConnectName());
//                goodsBills.setCcompanyID(addBean.getCcompanyID());
//                goodsBills.setCcompanyName(addBean.getCcompanyName());
//                goodsBills.setConnectName(addBean.getConnectName());
//                goodsBills.setConnectID(addBean.getConnectID());

                goodsBillsExt.setAccountFlag(addBean.getAccountFlag());
                goodsBillsExt.setAccountFlagFrom(addBean.getAccountFlagFrom());
                if ("company".equals(addBean.getAccountFlag())) {
                    goodsBillsExt.setAccountName(addBean.getAccountName());
                    goodsBillsExt.setAccountNameId(addBean.getAccountNameId());
                    goodsBillsExt.setAccountNum(addBean.getAccountNum());
                    goodsBillsExt.setOpenBank(addBean.getOpenBank());
                    goodsBillsExt.setAccountPhone(addBean.getAccountPhone());
                } else {
                    goodsBillsExt.setAccountName(addBean.getAccountNameP());
                    goodsBillsExt.setAccountNameId(addBean.getAccountNameIdP());
                    goodsBillsExt.setAccountNum(addBean.getAccountNumP());
                    goodsBillsExt.setOpenBank(addBean.getOpenBankP());
                    goodsBillsExt.setAccountPhone(addBean.getAccountPhoneP());
                }
                if ("company".equals(addBean.getAccountFlagFrom())) {
                    goodsBillsExt.setAccountNameFrom(addBean.getAccountNameFrom());
                    goodsBillsExt.setAccountNameIdFrom(addBean.getAccountNameIdFrom());
                    goodsBillsExt.setAccountNumFrom(addBean.getAccountNumFrom());
                    goodsBillsExt.setOpenBankFrom(addBean.getOpenBankFrom());
                    goodsBillsExt.setAccountPhoneFrom(addBean.getAccountPhoneFrom());
                } else {
                    goodsBillsExt.setAccountNameFrom(addBean.getAccountNameFromP());
                    goodsBillsExt.setAccountNameIdFrom(addBean.getAccountNameIdFromP());
                    goodsBillsExt.setAccountNumFrom(addBean.getAccountNumFromP());
                    goodsBillsExt.setOpenBankFrom(addBean.getOpenBankFromP());
                    goodsBillsExt.setAccountPhoneFrom(addBean.getAccountPhoneFromP());
                }
                goodsBillsExt.setLoanDirection(addBean.getLoanDirection());
                goodsBillsExt.setBudgetAmount(addBean.getBudgetAmount());
                goodsBillsExt.setBorrowAmount(addBean.getBorrowAmount());
                goodsBillsExt.setLoanAmount(addBean.getLoanAmount());
                goodsBillsExt.setInitialBalance(addBean.getInitialBalance());
                goodsBillsExt.setBalance(addBean.getBalance());
                goodsBillsExt.setOrderNum(addBean.getOrderNum());
                goodsBillsExt.setSpecsParentId(addBean.getSpecsParentId());
                goodsBillsExt.setTeamPunishment(addBean.getTeamPunishment());
                goodsBillsExt.setTeamReward(addBean.getTeamReward());
                goodsBillsExt.setPersonalReward(addBean.getPersonalReward());
                goodsBillsExt.setPersonalPunishment(addBean.getPersonalPunishment());

                if (StringUtils.isNotEmpty(addBean.getLogoPath())) {
                    goodsBills.setLogoPath(addBean.getLogoPath());//品牌log路径
                }

                //图片、视频的保存
//                String photoStr = addBean.getPhotoStr();
//                String videoStr = addBean.getVideoStr();
                List<BaseBean> photoList = addBean.getPhotoList();
                List<BaseBean> videoList = addBean.getVideoList();


                // 保存物品主图
                if (CollectionUtil.isNotEmpty(photoList)) {
                    String sql3 = "delete AttriProduction where goodsid=? and type='2' ";
                    baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{sql3}, new Object[]{goodsbillsId});
                    saveAttriProductionOfCostBudgetNew(photoList, goodsbillsId, baseBeanList, "I");
                }else{
                    String sql3 = "delete AttriProduction where goodsid=? and type='2' ";
                    baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{sql3}, new Object[]{goodsbillsId});
                }
                // 保存视频
                if (CollectionUtil.isNotEmpty(videoList)) {
                    String sql3 = "delete AttriProduction where goodsid=? and type='3' ";
                    baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{sql3}, new Object[]{goodsbillsId});
                    saveAttriProductionOfCostBudgetNew(videoList, goodsbillsId, baseBeanList, "V");
                }else{
                    String sql3 = "delete AttriProduction where goodsid=? and type='3' ";
                    baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{sql3}, new Object[]{goodsbillsId});
                }

                // 保存产品颜色，规格
//                sql = "delete AttriProduction where goodsid=? and type='1' ";
//                sql2 = "delete AttriProduction where goodsid=? and type='0' ";
//                sql3 = "delete AttriProduction where goodsid=? and type='4' ";
                //规格数据保存
//                if (StringUtils.isNotEmpty(addBean.getColorvalue())) {
//                    pmService.saveAttriProduction(addBean.getColorvalue(), addBean.getAttrinames(), goodsbillsId, baseBeanList, "1");
//                }
//                if (StringUtils.isNotEmpty(addBean.getSizevalue())) {
//                    pmService.saveAttriProduction(addBean.getSizevalue(), addBean.getAttrinamec(), goodsbillsId, baseBeanList, "0");
//                }
                if (StringUtils.isNotEmpty(addBean.getAttri())) {
                    String sql3 = "delete AttriProduction where goodsid=? and type='4' ";
                    baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{sql3}, new Object[]{goodsbillsId});
                    pmService.saveAttriProduction(addBean.getAttri(), "", goodsbillsId, baseBeanList, "4");
                }
                // 保存产品描述
                List<Object[]> params = new ArrayList<Object[]>();
                if (StringUtils.isNotEmpty(addBean.getHtl())) {
                    if (StringUtils.isNotEmpty(goodsbillsId)) {
                        String filpath = ServletActionContext.getServletContext()
                                .getRealPath("/");
                        params.add(new Object[]{goodsbillsId});
                        GoodFunction gf = (GoodFunction) baseBeanService
                                .getBeanByHqlAndParams(
                                        "from GoodFunction where goodsid= ?",
                                        new Object[]{goodsbillsId});
                        if (gf != null) {
                            String sql3 = "delete GoodFunction where goodsid=? ";
                            baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{sql3}, new Object[]{goodsbillsId});
                            FileUtil.delete(filpath + gf.getUrl());
                        }
                        GoodFunction goodFun = new GoodFunction();
                        goodFun.setGfid(serverService.getServerID("gfid"));
                        goodFun.setUrl(addBean.getHtlPath());
                        goodFun.setName("物品功能");
                        goodFun.setType("1");
                        goodFun.setGoodsid(goodsbillsId);
                        baseBeanList.add(goodFun);
                    }
                }
                baseBeanList.add(goodsBills);
                baseBeanList.add(goodsBillsExt);
            }

        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveAttriProductionOfCostBudget(String filepath, String goodsbillsId, List<BaseBean> beans, String type) {
        List<BaseBean> aplist = new ArrayList<BaseBean>();
        aplist = baseBeanService
                .getListBeanByHqlAndParams(
                        "from AttriProduction where goodsid=? and type=? order by sort",
                        new Object[]{goodsbillsId, type.equals("I") ? "2" : "3"});
        String path = ServletActionContext.getRequest()
                .getSession().getServletContext().getRealPath("/");
        String photopath = "";
        AttriProduction attrp = null;
        String[] photos = filepath.split(",");
        for (int i = 0; i < photos.length; i++) {
            attrp = new AttriProduction();
            photopath = photos[i];
            attrp.setApid(serverService.getServerID("apid"));
            attrp.setGoodsid(goodsbillsId);
            switch (type) {
                case "I":
                    String jjPath = photopath.split("\\.")[0] + "." + photopath.split("\\.")[1];
                    ImageCut.scale(path + photopath, path + jjPath, 414, 431);
//                    if (i == 0) {// 第一张图片保存在goodsmanage表，同时也保存attriproduction表
//                        gm.setPhotoPath(jjPath);
//                    }
                    attrp.setType("2");
                    attrp.setImgurl(jjPath);
                    break;
                case "V":
                    attrp.setType("3");
                    attrp.setImgurl(photopath);
                    break;
            }
            if (aplist != null && aplist.size() > 0) {
                AttriProduction a = (AttriProduction) aplist.get(aplist.size() - 1);
                attrp.setSort(a.getSort() + i + 1);
            } else {
                attrp.setSort(i + 1);
            }
            beans.add(attrp);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveAttriProductionOfCostBudgetNew(List<BaseBean> list, String goodsbillsId, List<BaseBean> beans, String type) {
        List<BaseBean> aplist = new ArrayList<BaseBean>();
        aplist = baseBeanService
                .getListBeanByHqlAndParams(
                        "from AttriProduction where goodsid=? and type=? order by sort",
                        new Object[]{goodsbillsId, type.equals("I") ? "2" : "3"});
        String path = ServletActionContext.getRequest()
                .getSession().getServletContext().getRealPath("/");
        String photopath = "";
        AttriProduction attrp = null;
//        String[] photos = filepath.split(",");
        AttriProduction info = null;
        for (int i = 0; i < list.size(); i++) {
            info = (AttriProduction) list.get(i);
            attrp = new AttriProduction();
            photopath = info.getImgurl();
            attrp.setApid(serverService.getServerID("apid"));
            attrp.setGoodsid(goodsbillsId);
            switch (type) {
                case "I":
                    String jjPath = photopath.split("\\.")[0] + "." + photopath.split("\\.")[1];
                    File file = new File(path + photopath);
                    if (file.exists()) {
                        ImageCut.scale(path + photopath, path + jjPath, 414, 431);
                    }
//                    if (i == 0) {// 第一张图片保存在goodsmanage表，同时也保存attriproduction表
//                        gm.setPhotoPath(jjPath);
//                    }
                    attrp.setType("2");
                    attrp.setImgurl(jjPath);
                    break;
                case "V":
                    attrp.setType("3");
                    attrp.setImgurl(photopath);
                    break;
            }
            if (aplist != null && aplist.size() > 0) {
                AttriProduction a = (AttriProduction) aplist.get(aplist.size() - 1);
                attrp.setSort(a.getSort() + i + 1);
            } else {
                attrp.setSort(i + 1);
            }
            beans.add(attrp);
        }
    }

    @Transactional
    public void toAddCostBudgetItem(Map<String, Object> scanGoodsMap, CostBudgetAddBean addBean, String mapKey) throws Exception {
        // 判断是否是提交扫描数据
        if (addBean != null && addBean.isTzFlag()) {//为提交表单跳转
            scanGoodsMap = MapSesssionUtil.getSessionForMap("scanGoodsMap");
            Map<String, Object> cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
            addBean.getBudgetAmount();

            //每次请求结束时，临时文件会被删除，所以在这里就开始上传到系统操作
            if (addBean.getFileLogo() != null) {
                try {
                    Map<String, Object> map = pmService.upLoadFile(null, null, addBean.getFileLogo().getName(), addBean.getFileLogo(), ServletActionContext.getRequest().getSession()
                            .getServletContext().getRealPath("\\"), addBean.getCompanyId());
                    addBean.setLogoPath(map.get("path").toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 保存产品描述
            List<Object[]> params = new ArrayList<Object[]>();
            String url = "", newhtl = "", oldhtl = "", temp = "", photopath = "", htl = "";
            String path = ServletActionContext.getRequest().getContextPath();
            String basePath = ServletActionContext.getRequest().getScheme() + "://"
                    + ServletActionContext.getRequest().getServerName() + ":"
                    + ServletActionContext.getRequest().getServerPort() + path
                    + "/";
            if (StringUtils.isNotEmpty(addBean.getHtl())) {
                if (addBean.getPic() != null && addBean.getPic().length > 0) {
                    for (int i = 0; i < addBean.getPic().length; i++) {
                        newhtl = addBean.getHtl().substring(0, addBean.getHtl().indexOf("img" + i));
                        temp = addBean.getHtl().substring(addBean.getHtl().indexOf("img" + i));
                        oldhtl = temp.substring(temp.indexOf(" height: auto;\">"));
                        String path1 = ServletActionContext.getRequest()
                                .getSession().getServletContext().getRealPath("/");
                        photopath = fileService.savePhoto(
                                path1,
                                addBean.getPicFileName()[i],
                                addBean.getPic()[i],
                                addBean.getCompanyId(),
                                "/goodfunction/"
                                        + Utilities.getDateString(new Date(),
                                        "yyyy-MM-dd"));
                        htl = newhtl + "img" + i + "\" src=\"" + basePath
                                + photopath
                                + "\" style=\"display: block; width: 100%;"
                                + oldhtl;
                    }
                }
                url = pmService.saveContentToFile(htl.trim());
                addBean.setHtlPath(url);

                String str = pmService.getContentFromFile(url);
                if (str.length() > 0) {
                    addBean.setFunctionList(str.split("#z"));
                }
            }
            //如果是新增功能里面的规格，先不管，处理起来太麻烦，保存之后就有了
//            if (StringUtils.isNotEmpty(addBean.getColorvalue())) {
//                pmService.saveAttriProduction(addBean.getColorvalue(), addBean.getAttrinames(), goodsbillsId, baseBeanList, "1");
//            }
//            if (StringUtils.isNotEmpty(addBean.getSizevalue())) {
//                pmService.saveAttriProduction(addBean.getSizevalue(), addBean.getAttrinamec(), goodsbillsId, baseBeanList, "0");
//            }
            if (StringUtils.isNotEmpty(addBean.getAttri())) {
                String attri = addBean.getAttri();
                String[] values = attri.split(",");
                HashMap<String, String> attriMap = new HashMap<>();
//                HashMap<String, String> attriMap = null;
                for (int i = 0; i < values.length; i++) {
//                    attriMap = new HashMap<>();
                    String[] a = values[i].split("-", 2);
                    attriMap.put(a[0], a[1]);
//                    attriList.add(attriMap);
                }
                addBean.setAttriJson(JSONObject.fromObject(attriMap).toString());
            }

            //首页图片
            String photoStr = addBean.getPhotoStr();
            String videoStr = addBean.getVideoStr();

            List<BaseBean> photoList = new ArrayList<>();
            // 保存物品主图（附件是在页面选中图片上传的时候就已经上传到服务器了，这里只是把对应的路径存储到相应的数据上）
            if (StringUtils.isNotEmpty(photoStr)) {
                AttriProduction photo = null;
                String[] photoArr = photoStr.split(",");
                for (String s : photoArr) {
                    photo = new AttriProduction();
                    photo.setImgurl(s);
                    photoList.add(photo);
                }
            }
            addBean.setPhotoList(photoList);

            //首页视频
            List<BaseBean> videoList = new ArrayList<>();
            // 保存视频
            if (StringUtils.isNotEmpty(videoStr)) {
                AttriProduction video = null;
                String[] videoArr = videoStr.split(",");
                for (String s : videoArr) {
                    video = new AttriProduction();
                    video.setImgurl(s);
                    videoList.add(video);
                }
            }
            addBean.setVideoList(videoList);


            addBean.setBudgetAmount(StringUtils.isNotEmpty(addBean.getAmount()) ? ("收".equals(addBean.getLoanDirection()) ? addBean.getAmount() : "-" + addBean.getAmount()) : "");
            if (scanGoodsMap == null) {//未存入session，则将数据存入session
                scanGoodsMap = new LinkedHashMap<String, Object>();
                MapSesssionUtil.saveSessionForMap("scanGoodsMap", scanGoodsMap);
                String key = UUID.randomUUID().toString();
                scanGoodsMap.put(key, addBean);
                addBean.setOrderNum(new Date().getTime());
                addBean.setGoodsBillsID(key);
            } else {
                //goodsBillsID不为空，证明是修改操作
                if (StringUtils.isNotEmpty(addBean.getGoodsBillsID())) {
                    scanGoodsMap.put(addBean.getGoodsBillsID(), addBean);
                } else {
                    //循环map获取最后一个key值
//                    for (String key : scanGoodsMap.keySet()) {
//                        mapKey = String.valueOf(Integer.valueOf(key) + 1);
//                    }
                    String key = UUID.randomUUID().toString();
                    addBean.setGoodsBillsID(key);
                    addBean.setOrderNum(new Date().getTime());
                    scanGoodsMap.put(key, addBean);
                }
            }

            //如果已经有提交的明细项
            String initialBalance = "0";
            if (CollectionUtil.isNotEmpty(cacheGoodsMap)) {
                Set<String> keySet = cacheGoodsMap.keySet();
                CostBudgetAddBean bean = null;
                for (String s : keySet) {
                    bean = (CostBudgetAddBean) cacheGoodsMap.get(s);
                }
                initialBalance = bean.getBalance();
            }
            Set<String> keySet = scanGoodsMap.keySet();
            CostBudgetAddBean lastBean = null;
            for (String s : keySet) {
                CostBudgetAddBean bean = (CostBudgetAddBean) scanGoodsMap.get(s);
                if (lastBean == null) {
                    bean.setBalance(new BigDecimal(initialBalance).add(new BigDecimal(bean.getBudgetAmount())).toString());
                    bean.setInitialBalance(initialBalance);
                } else {
                    bean.setInitialBalance(lastBean.getBalance());
                    bean.setBalance(new BigDecimal(bean.getBudgetAmount()).add(new BigDecimal(lastBean.getBalance())).toString());
                }
                lastBean = bean;
            }
// 查询登录人信息
            //2.根据手机端传过来的staffid查询状态为在职的account表数据
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            String tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffId = null;
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffId = (String) parmaInfor.get("staffId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffId = (String) personalId;
            }
            Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",
                    new Object[]{staffId});

            //保存最近添加的条目数据
//            GoodsBillsItemRecent itemRecent = new GoodsBillsItemRecent();
//            itemRecent.setId(serverService.getServerID("itemRecent"));
//            itemRecent.setCreateDate(new Date());
//            itemRecent.setStaffId(staffId);
//            itemRecent.setStaffName(staff.getStaffName());
//            itemRecent.setFlag("budget");
//            itemRecent.setBarCode(addBean.getBarCode());
//            itemRecent.setGoodsName(addBean.getGoodsName());
//            itemRecent.setGoodsbillsId(addBean.getGoodsBillsID());
//
//            String sql = "  from GoodsBillsItemRecent o where o.barCode=? and o.staffId=? ";
//            GoodsBillsItemRecent itemOld = (GoodsBillsItemRecent) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{addBean.getBarCode(), staffId});
//            if (itemOld != null) {
//                itemRecent.setKey(itemOld.getKey());
//                baseBeanDao.update(itemRecent);
//            } else {
//                baseBeanDao.save(itemRecent);
//            }


            //账户信息不为空时，将账户信息保存到最近联系人
            //根据账号和创建人id查询最近填写的账户数据是否存在，若存在，则删除之前的，插入现在最新的，若不存在，直接插入
            if (StringUtils.isNotEmpty(addBean.getAccountFlag())) {


                if (StringUtils.isNotEmpty(addBean.getAccountNumP()) || StringUtils.isNotEmpty(addBean.getAccountNum())) {
                    GoodsBillsContactRecent recent = new GoodsBillsContactRecent();

                    recent.setId(serverService.getServerID("recent"));
                    recent.setAccountFlag(addBean.getAccountFlag());
                    if ("company".equals(addBean.getAccountFlag())) {
                        recent.setAccountName(addBean.getAccountName());
                        recent.setAccountNameId(addBean.getAccountNameId());
                        recent.setAccountNum(addBean.getAccountNum());
                        recent.setOpenBank(addBean.getOpenBank());
                        recent.setAccountPhone(addBean.getAccountPhone());
                    } else {
                        recent.setAccountName(addBean.getAccountNameP());
                        recent.setAccountNameId(addBean.getAccountNameIdP());
                        recent.setAccountNum(addBean.getAccountNumP());
                        recent.setOpenBank(addBean.getOpenBankP());
                        recent.setAccountPhone(addBean.getAccountPhoneP());
                    }
                    recent.setCreateDate(new Date());
                    recent.setStaffId(staffId);
                    recent.setStaffName(staff.getStaffName());

                    String sql = "  from GoodsBillsContactRecent o where o.accountNum=? and o.staffId=?  and o.accountFlag in ('personal','company')";
                    GoodsBillsContactRecent recentOld = (GoodsBillsContactRecent) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{recent.getAccountNum(), staffId});
                    if (recentOld != null) {
                        recent.setKey(recentOld.getKey());
                        baseBeanDao.update(recent);
                    } else {
                        baseBeanDao.save(recent);
                    }
                }

            }

            if (StringUtils.isNotEmpty(addBean.getAccountFlagFrom())) {


                if (StringUtils.isNotEmpty(addBean.getAccountNumFromP()) || StringUtils.isNotEmpty(addBean.getAccountNumFrom())) {
                    GoodsBillsContactRecent recent = new GoodsBillsContactRecent();

                    recent.setId(serverService.getServerID("recent"));
                    recent.setAccountFlag(addBean.getAccountFlagFrom());
                    if ("company".equals(addBean.getAccountFlagFrom())) {
                        recent.setAccountName(addBean.getAccountNameFrom());
                        recent.setAccountNameId(addBean.getAccountNameIdFrom());
                        recent.setAccountNum(addBean.getAccountNumFrom());
                        recent.setOpenBank(addBean.getOpenBankFrom());
                        recent.setAccountPhone(addBean.getAccountPhoneFrom());
                    } else {
                        recent.setAccountName(addBean.getAccountNameFromP());
                        recent.setAccountNameId(addBean.getAccountNameIdFromP());
                        recent.setAccountNum(addBean.getAccountNumFromP());
                        recent.setOpenBank(addBean.getOpenBankFromP());
                        recent.setAccountPhone(addBean.getAccountPhoneFromP());
                    }
                    recent.setCreateDate(new Date());
                    recent.setStaffId(staffId);
                    recent.setStaffName(staff.getStaffName());

                    String sql = "  from GoodsBillsContactRecent o where o.accountNum=? and o.staffId=? and o.accountFlag in ('personal','company') ";
                    GoodsBillsContactRecent recentOld = (GoodsBillsContactRecent) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{recent.getAccountNum(), staffId});
                    if (recentOld != null) {
                        recent.setKey(recentOld.getKey());
                        baseBeanDao.update(recent);
                    } else {
                        baseBeanDao.save(recent);
                    }
                }

            }
        }
    }

    public DetachedCriteria getBudgetBillReceive(String staffId, String companyId, String search, int searchType) throws Exception {
        DetachedCriteria dc = DetachedCriteria.forClass(CashierBills.class);//将class转换成DetachedCriteria类
        // 初始项目单接收列表
        dc.add(Restrictions.eq("status", "50"));

        if (StringUtils.isNotEmpty(companyId)) {
            dc.add(Restrictions.eq("receiverCompanyID", companyId));
        } else {
            //往来个人没有公司
            dc.add(Restrictions.isNull("receiverCompanyID"));
        }
        dc.add(Restrictions.eq("receiverID", staffId));
        // dc.add(Restrictions.eq("organizationID", organizationID));//不知道用处暂时注释掉
        dc.add(Restrictions.in("billsType", new Object[]{"收入", "支出"}));
        dc.addOrder(Order.desc("cashierDate"));
        dc.add(Restrictions.eq("zctype", "cg"));
        searchSwitch(searchType, search, dc);
        return dc;
    }

    public DetachedCriteria getBudgetBillSent(String staffId, String companyId, String search, int searchType) throws Exception {
        DetachedCriteria dc = DetachedCriteria.forClass(CashierBills.class);//将class转换成DetachedCriteria类
        // 初始项目单接收列表
        dc.add(Restrictions.eq("status", "50"));

//        dc.add(Restrictions.or(Restrictions.eq("fromMember", staffId), Restrictions.eq("inputid", staffId)));

        if (StringUtils.isNotEmpty(companyId)) {
            dc.add(Restrictions.eq("companyID", companyId));
        } else {
            //个人没有公司
            dc.add(Restrictions.eq("companyID", " "));
        }

        // dc.add(Restrictions.eq("organizationID", organizationID));//不知道用处暂时注释掉
        dc.add(Restrictions.in("billsType", new Object[]{"收入", "支出"}));
        dc.addOrder(Order.desc("cashierDate"));
        dc.add(Restrictions.eq("zctype", "cg"));
        searchSwitch(searchType, search, dc);
        return dc;
    }

    public List<GoodsBillsContactRecent> getGoodsBillsContactRecent(String staffId, String accountFlag) {
        List<GoodsBillsContactRecent> list = new ArrayList<>();
        GoodsBillsContactRecent recent = null;

        List<Object> result = baseBeanDao.getListObjectBySqlAndParams("select o.ACCOUNTNAMEId,o.ACCOUNTNAME,o.ACCOUNTFLAG,o.ACCOUNTNUM,o.ACCOUNTPHONE,o.OPENBANK " +
                "from DTGOODSBILLSCONTACTRECENT o where o.STAFFID=? and o.ACCOUNTFLAG=? and rownum<=20 order by o.CREATEDATE desc  ", new Object[]{staffId, accountFlag});
        if (CollectionUtil.isNotEmpty(result)) {
            for (Object obj : result) {
                Object[] o = (Object[]) obj;
                recent = new GoodsBillsContactRecent();
                recent.setAccountNameId(o[0] + "");
                recent.setAccountName(o[1] + "");
                recent.setAccountFlag(o[2] + "");
                recent.setAccountNum(o[3] + "");
                recent.setAccountPhone(o[4] + "");
                recent.setOpenBank(o[5] + "");
                list.add(recent);
            }
        }
        return list;
    }

    public List<GoodsBillsItemRecent> getGoodsBillsItemRecent(String staffId, String flag) {
        List<GoodsBillsItemRecent> list = new ArrayList<>();
        GoodsBillsItemRecent recent = null;

        List<Object> result = baseBeanDao.getListObjectBySqlAndParams("select o.BARCODE,o.GOODSNAME,o.GOODSBILLSID " +
                "from DTGOODSBILLSITEMRECENT o where o.STAFFID=? and o.FLAG=? and rownum<=20 order by o.CREATEDATE desc  ", new Object[]{staffId, flag});
        if (CollectionUtil.isNotEmpty(result)) {
            for (Object obj : result) {
                Object[] o = (Object[]) obj;
                recent = new GoodsBillsItemRecent();
                recent.setBarCode(o[0] + "");
                recent.setGoodsName(o[1] + "");
                recent.setGoodsbillsId(o[2] + "");
                list.add(recent);
            }
        }
        return list;
    }

    @Override
    public CostBudgetAddBean getCostBudgetItemById(String goodsbillsId) {
        String hql = " from GoodsBills gb,GoodsBillsExt ext where gb.goodsBillsID=ext.goodsBillsID and gb.goodsBillsID = ? order by ext.orderNum ";
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{goodsbillsId});
        CostBudgetAddBean info = null;
        if (CollectionUtil.isNotEmpty(list)) {

            GoodsBills bill = null;
            GoodsBillsExt ext = null;
            JSONArray arr = JSONArray.fromObject(list.get(0));
            bill = (GoodsBills) JSONObject.toBean(JSONObject.fromObject(arr.get(0)), GoodsBills.class);
            ext = (GoodsBillsExt) JSONObject.toBean(JSONObject.fromObject(arr.get(1)), GoodsBillsExt.class);
            info = new CostBudgetAddBean();
            getBudgetItemInfo(bill, ext, info);

        }
        return info;
    }

    public List<BaseBean> getUnitData() {
        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(" from DtSpecsConfig o where o.specsLevel='1' order by o.id  ", null);
        return list;
    }

    public List<HashMap<String, String>> getSpecsByParent(String parentId) {
        List<HashMap<String, String>> list = new ArrayList<>();
        HashMap<String, String> map = null;

        List<Object> result = baseBeanDao.getListObjectBySqlAndParams("SELECT parent_id,specs_code,REPLACE(wm_concat(specs), ',', ' x ')" +
                "FROM DT_SPECS_CONFIG where parent_id=? group by specs_code,parent_id  ", new Object[]{parentId});
        if (CollectionUtil.isNotEmpty(result)) {
            for (Object obj : result) {
                Object[] o = (Object[]) obj;
                map = new HashMap<String, String>();
                map.put("parentId", o[0] + "");
                map.put("code", o[1] + "");
                map.put("info", (o[2] + "").replace("X", "x"));
                list.add(map);
            }
        }
        return list;
    }

    public List<BaseBean> getSpecsInfo(String code, String parentId) {
        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(" from DtSpecsConfig o where o.specsCode=? and o.parentId=? order by o.id  ", new Object[]{code, parentId});
        return list;
    }

    public DetachedCriteria getPlanBudgetBillDc(String staffId, String companyId, String type, boolean showFlag, String search, int searchType, String tenantFlag) throws Exception {
        //1.根据手机端传过来的参数判断是否已经放入session中
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        MapSesssionUtil.getSessionForObject("personalId");
        //非个人用户
        if (StringUtils.isNotBlank(tenantFlag)) {
            if (!"personal".equals(tenantFlag)) {
                if (parmaInfor == null) {//未存入session且公司不为空，则将数据存入session
                    Map<String, Object> map = MapSesssionUtil.saveYsdSessionForMapNew(companyId, staffId);
                    parmaInfor = MapSesssionUtil.saveSessionForMap("paramMap", map);
                } else {
                    //传过来的参数不为空
                    if (StringHelper.isNotEmpty(companyId) && StringHelper.isNotEmpty(staffId)) {
                        //判断传过来的参数与session中的参数不一致
                        if (!parmaInfor.get("companyId").toString().equals(companyId) || !parmaInfor.get("staffId").toString().equals(staffId)) {
                            MapSesssionUtil.removeSession("paramMap");
                            Map<String, Object> map = MapSesssionUtil.saveYsdSessionForMapNew(companyId, staffId);
                            parmaInfor = MapSesssionUtil.saveSessionForMap("paramMap", map);
                        }
                    }
                }
            } else {
                if (StringUtils.isNotBlank(staffId)) {
                    MapSesssionUtil.saveSessionForObject("personalId", staffId);
                }
            }
        }

        DetachedCriteria dc = DetachedCriteria.forClass(CashierBills.class);//将class转换成DetachedCriteria类
        // 项目预算列表
        dc.add(Restrictions.or(Restrictions.or(Restrictions.or(
                Restrictions.eq("status", "00"),
                Restrictions.eq("status", "01")), Restrictions.or(
                Restrictions.eq("status", "02"),
                Restrictions.eq("status", "03"))), Restrictions.or(
                Restrictions.eq("status", "04"),
                Restrictions.eq("status", "40"))));

        if ("other".equals(tenantFlag)) {
            dc.add(Restrictions.eq("companyID", parmaInfor.get("companyId").toString()));
        }

        if ("personal".equals(tenantFlag)) {
            dc.add(Restrictions.eq("inputid", staffId)).add(Restrictions.eq("companyID", " "));
        }

        // dc.add(Restrictions.eq("organizationID", organizationID));//不知道用处暂时注释掉
        dc.add(Restrictions.in("billsType", new Object[]{"计划"}));
        dc.add(Restrictions.eq("zctype", "cg"));
        dc.addOrder(Order.desc("cashierDate"));
        searchSwitch(searchType, search, dc);
        return dc;
    }

    public DetachedCriteria getPlanBudgetBillReceive(String staffId, String companyId, String search, int searchType) throws Exception {
        DetachedCriteria dc = DetachedCriteria.forClass(CashierBills.class);//将class转换成DetachedCriteria类
        // 初始项目单接收列表
        dc.add(Restrictions.eq("status", "50"));

        if (StringUtils.isNotEmpty(companyId)) {
            dc.add(Restrictions.eq("receiverCompanyID", companyId));
        } else {
            //往来个人没有公司
            dc.add(Restrictions.isNull("receiverCompanyID"));
        }
        dc.add(Restrictions.eq("receiverID", staffId));
        // dc.add(Restrictions.eq("organizationID", organizationID));//不知道用处暂时注释掉
        dc.add(Restrictions.in("billsType", new Object[]{"计划"}));
        dc.addOrder(Order.desc("cashierDate"));
        dc.add(Restrictions.eq("zctype", "cg"));
        searchSwitch(searchType, search, dc);
        return dc;
    }

    public DetachedCriteria getPlanBudgetBillSent(String staffId, String companyId, String search, int searchType) throws Exception {
        DetachedCriteria dc = DetachedCriteria.forClass(CashierBills.class);//将class转换成DetachedCriteria类
        // 初始项目单接收列表
        dc.add(Restrictions.eq("status", "50"));

        dc.add(Restrictions.or(Restrictions.eq("fromMember", staffId), Restrictions.eq("inputid", staffId)));

        if (StringUtils.isNotEmpty(companyId)) {
            dc.add(Restrictions.eq("companyID", companyId));
        } else {
            //个人没有公司
            dc.add(Restrictions.eq("companyID", " "));
        }

        // dc.add(Restrictions.eq("organizationID", organizationID));//不知道用处暂时注释掉
        dc.add(Restrictions.in("billsType", new Object[]{"计划"}));
        dc.addOrder(Order.desc("cashierDate"));
        dc.add(Restrictions.eq("zctype", "cg"));
        searchSwitch(searchType, search, dc);
        return dc;
    }

    @Transactional
    public void toAddPlanCostBudgetItem(Map<String, Object> scanGoodsMap, CostBudgetAddBean addBean, String mapKey) throws Exception {
        // 判断是否是提交扫描数据
        if (addBean != null && addBean.isTzFlag()) {//为提交表单跳转
            scanGoodsMap = MapSesssionUtil.getSessionForMap("scanGoodsMap");
            Map<String, Object> cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
            addBean.getBudgetAmount();

            //每次请求结束时，临时文件会被删除，所以在这里就开始上传到系统操作
            if (addBean.getFileLogo() != null) {
                try {
                    Map<String, Object> map = pmService.upLoadFile(null, null, addBean.getFileLogo().getName(), addBean.getFileLogo(), ServletActionContext.getRequest().getSession()
                            .getServletContext().getRealPath("\\"), addBean.getCompanyId());
                    addBean.setLogoPath(map.get("path").toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 保存产品描述
            List<Object[]> params = new ArrayList<Object[]>();
            String url = "", newhtl = "", oldhtl = "", temp = "", photopath = "", htl = "";
            String path = ServletActionContext.getRequest().getContextPath();
            String basePath = ServletActionContext.getRequest().getScheme() + "://"
                    + ServletActionContext.getRequest().getServerName() + ":"
                    + ServletActionContext.getRequest().getServerPort() + path
                    + "/";
            if (StringUtils.isNotEmpty(addBean.getHtl())) {
                if (addBean.getPic() != null && addBean.getPic().length > 0) {
                    for (int i = 0; i < addBean.getPic().length; i++) {
                        newhtl = addBean.getHtl().substring(0, addBean.getHtl().indexOf("img" + i));
                        temp = addBean.getHtl().substring(addBean.getHtl().indexOf("img" + i));
                        oldhtl = temp.substring(temp.indexOf(" height: auto;\">"));
                        String path1 = ServletActionContext.getRequest()
                                .getSession().getServletContext().getRealPath("/");
                        photopath = fileService.savePhoto(
                                path1,
                                addBean.getPicFileName()[i],
                                addBean.getPic()[i],
                                addBean.getCompanyId(),
                                "/goodfunction/"
                                        + Utilities.getDateString(new Date(),
                                        "yyyy-MM-dd"));
                        htl = newhtl + "img" + i + "\" src=\"" + basePath
                                + photopath
                                + "\" style=\"display: block; width: 100%;"
                                + oldhtl;
                    }
                }
                url = pmService.saveContentToFile(htl.trim());
                addBean.setHtlPath(url);

                String str = pmService.getContentFromFile(url);
                if (str.length() > 0) {
                    addBean.setFunctionList(str.split("#z"));
                }
            }
            //如果是新增功能里面的规格，先不管，处理起来太麻烦，保存之后就有了
//            if (StringUtils.isNotEmpty(addBean.getColorvalue())) {
//                pmService.saveAttriProduction(addBean.getColorvalue(), addBean.getAttrinames(), goodsbillsId, baseBeanList, "1");
//            }
//            if (StringUtils.isNotEmpty(addBean.getSizevalue())) {
//                pmService.saveAttriProduction(addBean.getSizevalue(), addBean.getAttrinamec(), goodsbillsId, baseBeanList, "0");
//            }
            if (StringUtils.isNotEmpty(addBean.getAttri())) {
                String attri = addBean.getAttri();
                String[] values = attri.split(",");
                HashMap<String, String> attriMap = new HashMap<>();
//                HashMap<String, String> attriMap = null;
                for (int i = 0; i < values.length; i++) {
//                    attriMap = new HashMap<>();
                    String[] a = values[i].split("-", 2);
                    attriMap.put(a[0], a[1]);
//                    attriList.add(attriMap);
                }
                addBean.setAttriJson(JSONObject.fromObject(attriMap).toString());
            }

            //首页图片
            String photoStr = addBean.getPhotoStr();
            String videoStr = addBean.getVideoStr();

            List<BaseBean> photoList = new ArrayList<>();
            // 保存物品主图（附件是在页面选中图片上传的时候就已经上传到服务器了，这里只是把对应的路径存储到相应的数据上）
            if (StringUtils.isNotEmpty(photoStr)) {
                AttriProduction photo = null;
                String[] photoArr = photoStr.split(",");
                for (String s : photoArr) {
                    photo = new AttriProduction();
                    photo.setImgurl(s);
                    photoList.add(photo);
                }
            }
            addBean.setPhotoList(photoList);

            //首页视频
            List<BaseBean> videoList = new ArrayList<>();
            // 保存视频
            if (StringUtils.isNotEmpty(videoStr)) {
                AttriProduction video = null;
                String[] videoArr = videoStr.split(",");
                for (String s : videoArr) {
                    video = new AttriProduction();
                    video.setImgurl(s);
                    videoList.add(video);
                }
            }
            addBean.setVideoList(videoList);


            addBean.setBudgetAmount(StringUtils.isNotEmpty(addBean.getAmount()) ? ("收".equals(addBean.getLoanDirection()) ? addBean.getAmount() : "-" + addBean.getAmount()) : "");
            if (scanGoodsMap == null) {//未存入session，则将数据存入session
                scanGoodsMap = new LinkedHashMap<String, Object>();
                MapSesssionUtil.saveSessionForMap("scanGoodsMap", scanGoodsMap);
                String key = UUID.randomUUID().toString();
                scanGoodsMap.put(key, addBean);
                addBean.setOrderNum(new Date().getTime());
                addBean.setGoodsBillsID(key);
            } else {
                //goodsBillsID不为空，证明是修改操作
                if (StringUtils.isNotEmpty(addBean.getGoodsBillsID())) {
                    scanGoodsMap.put(addBean.getGoodsBillsID(), addBean);
                } else {
                    //循环map获取最后一个key值
//                    for (String key : scanGoodsMap.keySet()) {
//                        mapKey = String.valueOf(Integer.valueOf(key) + 1);
//                    }
                    String key = UUID.randomUUID().toString();
                    addBean.setGoodsBillsID(key);
                    addBean.setOrderNum(new Date().getTime());
                    scanGoodsMap.put(key, addBean);
                }
            }

            //如果已经有提交的明细项
            String initialBalance = "0";
            if (CollectionUtil.isNotEmpty(cacheGoodsMap)) {
                Set<String> keySet = cacheGoodsMap.keySet();
                CostBudgetAddBean bean = null;
                for (String s : keySet) {
                    bean = (CostBudgetAddBean) cacheGoodsMap.get(s);
                }
                initialBalance = bean.getBalance();
            }
            Set<String> keySet = scanGoodsMap.keySet();
            CostBudgetAddBean lastBean = null;
            for (String s : keySet) {
                CostBudgetAddBean bean = (CostBudgetAddBean) scanGoodsMap.get(s);
                if (lastBean == null) {
                    bean.setBalance(new BigDecimal(initialBalance).add(new BigDecimal(bean.getBudgetAmount())).toString());
                    bean.setInitialBalance(initialBalance);
                } else {
                    bean.setInitialBalance(lastBean.getBalance());
                    bean.setBalance(new BigDecimal(bean.getBudgetAmount()).add(new BigDecimal(lastBean.getBalance())).toString());
                }
                lastBean = bean;
            }
// 查询登录人信息
            //2.根据手机端传过来的staffid查询状态为在职的account表数据
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            String tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
            Object personalId = MapSesssionUtil.getSessionForObject("personalId");
            String staffId = null;
            if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
                staffId = (String) parmaInfor.get("staffId");
            } else if (personalId != null && "personal".equals(tenantFlag)) {
                staffId = (String) personalId;
            }
            Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",
                    new Object[]{staffId});

            //账户信息不为空时，将账户信息保存到最近联系人
            //根据账号和创建人id查询最近填写的账户数据是否存在，若存在，则删除之前的，插入现在最新的，若不存在，直接插入
            if (StringUtils.isNotEmpty(addBean.getAccountFlag())) {


                if (StringUtils.isNotEmpty(addBean.getAccountNumP()) || StringUtils.isNotEmpty(addBean.getAccountNum())) {
                    GoodsBillsContactRecent recent = new GoodsBillsContactRecent();

                    recent.setId(serverService.getServerID("recent"));
                    recent.setAccountFlag(addBean.getAccountFlag());
                    if ("companyPlan".equals(addBean.getAccountFlag())) {
                        recent.setAccountName(addBean.getAccountName());
                        recent.setAccountNameId(addBean.getAccountNameId());
                        recent.setAccountNum(addBean.getAccountNum());
                        recent.setOpenBank(addBean.getOpenBank());
                        recent.setAccountPhone(addBean.getAccountPhone());
                    } else {
                        recent.setAccountName(addBean.getAccountNameP());
                        recent.setAccountNameId(addBean.getAccountNameIdP());
                        recent.setAccountNum(addBean.getAccountNumP());
                        recent.setOpenBank(addBean.getOpenBankP());
                        recent.setAccountPhone(addBean.getAccountPhoneP());
                    }
                    recent.setCreateDate(new Date());
                    recent.setStaffId(staffId);
                    recent.setStaffName(staff.getStaffName());

                    String sql = "  from GoodsBillsContactRecent o where o.accountNum=? and o.staffId=? and o.accountFlag in ('personalPlan','companyPlan')";
                    GoodsBillsContactRecent recentOld = (GoodsBillsContactRecent) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{recent.getAccountNum(), staffId});
                    if (recentOld != null) {
                        recent.setKey(recentOld.getKey());
                        baseBeanDao.update(recent);
                    } else {
                        baseBeanDao.save(recent);
                    }
                }

            }

            if (StringUtils.isNotEmpty(addBean.getAccountFlagFrom())) {


                if (StringUtils.isNotEmpty(addBean.getAccountNumFromP()) || StringUtils.isNotEmpty(addBean.getAccountNumFrom())) {
                    GoodsBillsContactRecent recent = new GoodsBillsContactRecent();

                    recent.setId(serverService.getServerID("recent"));
                    recent.setAccountFlag(addBean.getAccountFlagFrom());
                    if ("companyPlan".equals(addBean.getAccountFlagFrom())) {
                        recent.setAccountName(addBean.getAccountNameFrom());
                        recent.setAccountNameId(addBean.getAccountNameIdFrom());
                        recent.setAccountNum(addBean.getAccountNumFrom());
                        recent.setOpenBank(addBean.getOpenBankFrom());
                        recent.setAccountPhone(addBean.getAccountPhoneFrom());
                    } else {
                        recent.setAccountName(addBean.getAccountNameFromP());
                        recent.setAccountNameId(addBean.getAccountNameIdFromP());
                        recent.setAccountNum(addBean.getAccountNumFromP());
                        recent.setOpenBank(addBean.getOpenBankFromP());
                        recent.setAccountPhone(addBean.getAccountPhoneFromP());
                    }
                    recent.setCreateDate(new Date());
                    recent.setStaffId(staffId);
                    recent.setStaffName(staff.getStaffName());

                    String sql = "  from GoodsBillsContactRecent o where o.accountNum=? and o.staffId=?  and o.accountFlag in ('personalPlan','companyPlan')";
                    GoodsBillsContactRecent recentOld = (GoodsBillsContactRecent) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{recent.getAccountNum(), staffId});
                    if (recentOld != null) {
                        recent.setKey(recentOld.getKey());
                        baseBeanDao.update(recent);
                    } else {
                        baseBeanDao.save(recent);
                    }
                }

            }
        }
    }

    @Transactional
    public void savePlanCostBudgetSheet(CashierBills cashierBills) throws Exception {
        //1.获取session数据
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        String tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
        Object personalId = MapSesssionUtil.getSessionForObject("personalId");
        //2.根据手机端传过来的staffid查询状态为在职的account表数据
        String companyId = null;
        String staffId = null;
        if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
            staffId = (String) parmaInfor.get("staffId");
            companyId = (String) parmaInfor.get("companyId");
        } else if (personalId != null && "personal".equals(tenantFlag)) {
            staffId = (String) personalId;
        }

        // 通过staffid查找account用户信息
//        String sql = " from CAccount o where o.companyID = ? and o.staffID = ? and o.accountStatus = '00'";
//        CAccount account = (CAccount) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{companyId, staffId});
        // 查询登录人信息
        Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",
                new Object[]{staffId});
        // 标示数据属于哪个部门，是否就是所选部门id
        String organizationID = cashierBills.getDepartmentID();
        //3.保存订单信息
        cashierBills.setCashierBillsID(serverService.getServerID("cashierTally"));
        String parameter = "添加项目计划单据（凭证号：" + cashierBills.getJournalNum() + "）";
        cashierBills.setStatus("00");// 未审核
        cashierBills.setZctype("cg");//支出类型 分为常规支出cg;和非常规类型fcg;
        //判断所选项目分类是否是人事项目
        if (compareBy(companyId, cashierBills.getXmtype())) {
            cashierBills.setPaystatus("00");// 工资状态
        }
        cashierBills.setGroupCompanySn(staff.getGroupCompanySn());//集团标识
        cashierBills.setCompanyID(StringUtils.isBlank(companyId) ? " " : companyId);//公司id
        cashierBills.setCashierDate(new Date());//单据日期  下单日期
        cashierBills.setOrganizationID(organizationID);//标示数据属于哪个部门
        // 3.1制单人信息START
        cashierBills.setInputName(staff.getStaffName());//制单人名称
        cashierBills.setInputid(staff.getStaffID());//制单人id
        cashierBills.setInputOrganizationID(organizationID);//制单人所属部门
        cashierBills.setInputOrganizationName(cashierBills.getDepartmentName());//制单人部门名称
        cashierBills.setInputCompanyid(StringUtils.isBlank(companyId) ? " " : companyId);//公司id
        cashierBills.setInputCompanyName(cashierBills.getCompanyName());//公司名称
        // 制单人信息END
        //4.创建保存订单的bean
        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        baseBeanList.add(cashierBills);
        //5.循环扫码枪获取到的数据
        savePlanGoodsBillsNew(baseBeanList, cashierBills, companyId);
        //6.记录日志
//        CLogBook logBook = logBookService.saveCLogBook(organizationID,
//                parameter, account);
//        baseBeanList.add(logBook);
        //7.保存表
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,
                null);
    }

    public void savePlanGoodsBillsNew(List<BaseBean> baseBeanList, CashierBills cashierBills, String companyId) throws Exception {
        Map<String, Object> cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
        if (cacheGoodsMap != null) {//未存入session，则将数据存入session
            GoodsBills goodsBills;

            String staffID = cashierBills.getStaffID();
            String staffName = cashierBills.getStaffName();
            GoodsBillsExt goodsBillsExt;
            String sql = null;
            String sql2 = null;
            String sql3 = null;
            String hql_del = null;
            String goodsbillsId = null;
            String goodsbillsExtId = null;
            for (Object obj : cacheGoodsMap.values()) {
                goodsBills = new GoodsBills();
                goodsBillsExt = new GoodsBillsExt();
                CostBudgetAddBean addBean = (CostBudgetAddBean) obj;

                //TODO:当前公司没有的物品需要新增到物品表、产品表
                //20240803刘总说，页面不能添加产品表没有的物品数据
//                GoodsManage goodsManage = (GoodsManage) baseBeanService.getBeanByHqlAndParams("from GoodsManage o where o.companyID = ? and o.barCode = ?", new Object[]{companyId, addBean.getBarCode()});
//                GoodsManage gm = null;
//                ProductPackaging pp = null;
//                if (goodsManage == null) {
//                    gm = new GoodsManage();
//                    pp = new ProductPackaging();
//                    addGoodsAndProduct(baseBeanList, gm, pp, companyId, addBean);
//                }


                goodsBills.setCashierBillsID(cashierBills.getCashierBillsID());//收支单管理id
                goodsbillsId = serverService.getServerID("goodsbills");
                goodsBills.setGoodsBillsID(goodsbillsId);//物品单据id

                if (goodsBills.getGoodsBillsID().startsWith("goodsbills")) {
                    GoodsBillsItemRecent itemRecent = new GoodsBillsItemRecent();
                    itemRecent.setId(serverService.getServerID("itemRecent"));
                    itemRecent.setCreateDate(new Date());
                    itemRecent.setStaffId(staffID);
                    itemRecent.setStaffName(staffName);
                    itemRecent.setFlag("plan");
                    itemRecent.setBarCode(addBean.getBarCode());
                    itemRecent.setGoodsName(addBean.getGoodsName());
                    itemRecent.setGoodsbillsId(goodsbillsId);

                    sql = "  from GoodsBillsItemRecent o where o.barCode=? and o.staffId=? and o.flag='plan' ";
                    GoodsBillsItemRecent itemOld = (GoodsBillsItemRecent) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{addBean.getBarCode(), staffID});
                    if (itemOld != null) {
                        itemRecent.setKey(itemOld.getKey());
                        baseBeanDao.update(itemRecent);
                    } else {
                        baseBeanDao.save(itemRecent);
                    }
                }

                goodsBills.setCompanyID(companyId);//公司id
                goodsBills.setEndDate(new Date());// 入账时间
                goodsBills.setQuantity(addBean.getInvInvenQuantity());//库存数量
                goodsBills.setPrice(addBean.getPrice());//预算单价
                goodsBills.setMoney(addBean.getPrice());//预算金额
                goodsBills.setTiaoQuantity(addBean.getBudgetNumber());// 初始时将调整信息设置为预算信息
                goodsBills.setTiaoPrice(goodsBills.getPrice());
                goodsBills.setTiaoMoney(goodsBills.getMoney());
//                goodsBills.setGoodsID(goodsManage == null ? gm.getGoodsID() : addBean.getGoodsId());//货物id
//                goodsBills.setGoodsNum(goodsManage == null ? gm.getGoodsCoding() : addBean.getGoodsCoding());//品名编号
                goodsBills.setGoodsID(addBean.getGoodsId());//货物id
                goodsBills.setGoodsNum(addBean.getGoodsCoding());//品名编号
                goodsBills.setGoodsName(addBean.getGoodsName());//物品名称
                goodsBills.setGoodsVariableID(addBean.getVariableId());//货物单位
                goodsBills.setCcompanyName(addBean.getCurrentCompany());//往来公司名称
                goodsBills.setCcompanyID(addBean.getCurrentCompanyId());//往来公司id
                goodsBills.setConnectID(addBean.getCurrentPersonId());//往来个人id
                goodsBills.setConnectName(addBean.getCurrentPerson());//往来个人姓名
                goodsBills.setPpID(cashierBills.getProID());//项目ID也就是产品ID；
                goodsBills.setPriceManage("人民币");//单价管理
                goodsBills.setTargetStart(new Date());//目标起时间
                goodsBills.setTargetEnd(new Date());//目标止时间
                goodsBills.setTargetDeptID(cashierBills.getInputOrganizationID());//目标部门id
                goodsBills.setTargetDeptName(cashierBills.getInputOrganizationName());//目标部门名称
                goodsBills.setTargetSalerID(cashierBills.getInputid());//目标业务员id
                goodsBills.setTargetSalerName(cashierBills.getInputName());//目标业务员名称
                goodsBills.setBarCode(addBean.getBarCode());
                goodsBills.setGradeid(addBean.getGradeid());
                goodsBills.setGradeName(addBean.getGradeName());
                goodsBills.setInvInvenQuantity(addBean.getInvInvenQuantity());
                goodsBills.setTradeID(addBean.getTradeID());
                goodsBills.setTradeCode(addBean.getTradeCode());
                goodsBills.setTradeName(addBean.getTradeName());
                goodsBills.setProductCode(addBean.getProductCode());
                goodsBills.setProducttype(addBean.getProducttype());
                goodsBills.setTypeID(addBean.getTypeID());
                goodsBills.setIsScale(addBean.getIsScale());
                goodsBills.setUnitofmeasurecode(addBean.getUnitofmeasurecode());
                goodsBills.setBrand(addBean.getBrand());
                goodsBills.setGuigeType(addBean.getGuigeType());
                goodsBills.setGuigeTypeId(addBean.getGuigeTypeId());
                goodsBills.setGuigeTypeValue(addBean.getGuigeTypeValue());
                goodsBills.setUnitPrice(addBean.getUnitPrice());
                goodsBills.setAmount(addBean.getAmount());
                goodsBills.setGoodsPurpose(addBean.getGoodsPurpose());
                goodsBills.setGoodsPurposeId(addBean.getGoodsPurposeId());

                goodsBills.setServiceStartDate(addBean.getServiceStartDate());
                goodsBills.setServiceEndDate(addBean.getServiceEndDate());
                goodsBills.setConnectName(addBean.getConnectName());

//                goodsBills.setCcompanyID(addBean.getCcompanyID());
//                goodsBills.setCcompanyName(addBean.getCcompanyName());
//                goodsBills.setConnectName(addBean.getConnectName());
//                goodsBills.setConnectID(addBean.getConnectID());

                goodsBillsExt.setGoodsBillsID(goodsbillsId);
                goodsbillsExtId = serverService.getServerID("goodsbillsExt");
                goodsBillsExt.setGoodsBillsExtId(goodsbillsExtId);
                goodsBillsExt.setAccountFlag(addBean.getAccountFlag());
                goodsBillsExt.setAccountFlagFrom(addBean.getAccountFlagFrom());
                if ("companyPlan".equals(addBean.getAccountFlag())) {
                    goodsBillsExt.setAccountName(addBean.getAccountName());
                    goodsBillsExt.setAccountNameId(addBean.getAccountNameId());
                    goodsBillsExt.setAccountNum(addBean.getAccountNum());
                    goodsBillsExt.setOpenBank(addBean.getOpenBank());
                    goodsBillsExt.setAccountPhone(addBean.getAccountPhone());
                } else {
                    goodsBillsExt.setAccountName(addBean.getAccountNameP());
                    goodsBillsExt.setAccountNameId(addBean.getAccountNameIdP());
                    goodsBillsExt.setAccountNum(addBean.getAccountNumP());
                    goodsBillsExt.setOpenBank(addBean.getOpenBankP());
                    goodsBillsExt.setAccountPhone(addBean.getAccountPhoneP());
                }

                if ("companyPlan".equals(addBean.getAccountFlagFrom())) {
                    goodsBillsExt.setAccountNameFrom(addBean.getAccountNameFrom());
                    goodsBillsExt.setAccountNameIdFrom(addBean.getAccountNameIdFrom());
                    goodsBillsExt.setAccountNumFrom(addBean.getAccountNumFrom());
                    goodsBillsExt.setOpenBankFrom(addBean.getOpenBankFrom());
                    goodsBillsExt.setAccountPhoneFrom(addBean.getAccountPhoneFrom());
                } else {
                    goodsBillsExt.setAccountNameFrom(addBean.getAccountNameFromP());
                    goodsBillsExt.setAccountNameIdFrom(addBean.getAccountNameIdFromP());
                    goodsBillsExt.setAccountNumFrom(addBean.getAccountNumFromP());
                    goodsBillsExt.setOpenBankFrom(addBean.getOpenBankFromP());
                    goodsBillsExt.setAccountPhoneFrom(addBean.getAccountPhoneFromP());
                }
                goodsBillsExt.setLoanDirection(addBean.getLoanDirection());
                goodsBillsExt.setBudgetAmount(addBean.getBudgetAmount());
                goodsBillsExt.setBorrowAmount(addBean.getBorrowAmount());
                goodsBillsExt.setLoanAmount(addBean.getLoanAmount());
                goodsBillsExt.setInitialBalance(addBean.getInitialBalance());
                goodsBillsExt.setBalance(addBean.getBalance());
                goodsBillsExt.setOrderNum(addBean.getOrderNum());
                goodsBillsExt.setSpecsParentId(addBean.getSpecsParentId());
                goodsBillsExt.setTeamPunishment(addBean.getTeamPunishment());
                goodsBillsExt.setTeamReward(addBean.getTeamReward());
                goodsBillsExt.setPersonalReward(addBean.getPersonalReward());
                goodsBillsExt.setPersonalPunishment(addBean.getPersonalPunishment());

                if (StringUtils.isNotEmpty(addBean.getLogoPath())) {
                    goodsBills.setLogoPath(addBean.getLogoPath());//品牌log路径
                    //在往物品表中插入logo时，会删除数据
//                    if (goodsManage == null) {
//                        goodsBills.setLogoPath(gm.getLogoPath());
//                    } else {
//                        try {
//                            Map<String, Object> map = pmService.upLoadFile(null, null, addBean.getFileLogo().getName(), addBean.getFileLogo(), ServletActionContext.getRequest().getSession()
//                                    .getServletContext().getRealPath("\\"), companyId);
//                            goodsBills.setLogoPath(map.get("path").toString());//品牌log路径
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
                }

                //图片、视频的保存
//                String photoStr = addBean.getPhotoStr();
//                String videoStr = addBean.getVideoStr();
                List<BaseBean> photoList = addBean.getPhotoList();
                List<BaseBean> videoList = addBean.getVideoList();

                // 保存物品主图（附件是在页面选中图片上传的时候就已经上传到服务器了，这里只是把对应的路径存储到相应的数据上）
                if (CollectionUtil.isNotEmpty(photoList)) {
                    saveAttriProductionOfCostBudgetNew(photoList, goodsbillsId, baseBeanList, "I");
                }
                // 保存视频
                if (CollectionUtil.isNotEmpty(videoList)) {
                    saveAttriProductionOfCostBudgetNew(videoList, goodsbillsId, baseBeanList, "V");
                }

                // 保存产品颜色，规格
//                sql = "delete AttriProduction where goodsid=? and type='1' ";
//                sql2 = "delete AttriProduction where goodsid=? and type='0' ";
//                sql3 = "delete AttriProduction where goodsid=? and type='4' ";
                //规格数据保存
//                if (StringUtils.isNotEmpty(addBean.getColorvalue())) {
//                    pmService.saveAttriProduction(addBean.getColorvalue(), addBean.getAttrinames(), goodsbillsId, baseBeanList, "1");
//                }
//                if (StringUtils.isNotEmpty(addBean.getSizevalue())) {
//                    pmService.saveAttriProduction(addBean.getSizevalue(), addBean.getAttrinamec(), goodsbillsId, baseBeanList, "0");
//                }
                if (StringUtils.isNotEmpty(addBean.getAttri())) {
                    pmService.saveAttriProduction(addBean.getAttri(), "", goodsbillsId, baseBeanList, "4");
                }

                // 保存产品描述
                List<Object[]> params = new ArrayList<Object[]>();
                if (StringUtils.isNotEmpty(addBean.getHtl())) {
                    if (StringUtils.isNotEmpty(goodsbillsId)) {
                        String filpath = ServletActionContext.getServletContext()
                                .getRealPath("/");
                        params.add(new Object[]{goodsbillsId});
                        GoodFunction gf = (GoodFunction) baseBeanService
                                .getBeanByHqlAndParams(
                                        "from GoodFunction where goodsid= ?",
                                        new Object[]{goodsbillsId});
                        if (gf != null) {
                            FileUtil.delete(filpath + gf.getUrl());
                        }
                        GoodFunction goodFun = new GoodFunction();
                        goodFun.setGfid(serverService.getServerID("gfid"));
                        goodFun.setUrl(addBean.getHtlPath());
                        goodFun.setName("物品功能");
                        goodFun.setType("1");
                        goodFun.setGoodsid(goodsbillsId);
                        baseBeanList.add(goodFun);
                    }
                }
//                hql_del = "delete GoodFunction where goodsid= ?";
//                String barCode = addBean.getBarCode();
                baseBeanList.add(goodsBills);
                baseBeanList.add(goodsBillsExt);
            }

        }
    }

    @Transactional
    public void updatePlanCostBudgetSheet(CashierBills cashierBills, String menuId) throws Exception {
        //1.获取session数据
        Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
        String tenantFlag = (String) MapSesssionUtil.getSessionForObject("tenantFlag");
        Object personalId = MapSesssionUtil.getSessionForObject("personalId");
        //2.根据手机端传过来的staffid查询状态为在职的account表数据
        String companyId = null;
        String staffId = null;
        if (CollectionUtil.isNotEmpty(parmaInfor) && "other".equals(tenantFlag)) {
            staffId = (String) parmaInfor.get("staffId");
            companyId = (String) parmaInfor.get("companyId");
        } else if (personalId != null && "personal".equals(tenantFlag)) {
            staffId = (String) personalId;
        }
        // 通过staffid查找account用户信息
//        String sql = " from CAccount o where o.companyID = ? and o.staffID = ? and o.accountStatus = '00'";
//        CAccount account = (CAccount) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{companyId, staffId});
        // 查询登录人信息
        Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",
                new Object[]{staffId});
        //创建保存订单的bean
        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        // 标示数据属于哪个部门，是否就是所选部门id
        String organizationID = cashierBills.getDepartmentID();
        //3.保存订单信息
//        cashierBills.setCashierBillsID(serverService.getServerID("cashierTally"));
//        String parameter = "添加预算单据（凭证号：" + cashierBills.getJournalNum() + "）";

        //收件页面的修改功能
        if ("receive".equals(menuId)) {
            CashierBills oldCashierBills = (CashierBills) baseBeanDao.getBeanByHqlAndParams(" from CashierBills o where o.cashierBillsID = ?", new Object[]{cashierBills.getCashierBillsID()});
            oldCashierBills.setGroupCompanySn(staff.getGroupCompanySn());//集团标识
            oldCashierBills.setCompanyID(companyId);//公司id
            oldCashierBills.setCashierDate(new Date());//单据日期  下单日期
            oldCashierBills.setOrganizationID(organizationID);//标示数据属于哪个部门
            oldCashierBills.setInputName(staff.getStaffName());//制单人名称
            oldCashierBills.setInputid(staff.getStaffID());//制单人id
            oldCashierBills.setInputOrganizationID(organizationID);//制单人所属部门
            oldCashierBills.setInputOrganizationName(cashierBills.getDepartmentName());//制单人部门名称
            oldCashierBills.setInputCompanyid(companyId);//公司id
            oldCashierBills.setInputCompanyName(cashierBills.getCompanyName());//公司名称
            baseBeanList.add(oldCashierBills);
        } else {
            cashierBills.setStatus("00");// 未审核
            cashierBills.setZctype("cg");//支出类型 分为常规支出cg;和非常规类型fcg;
            //判断所选项目分类是否是人事项目
            if (compareBy(companyId, cashierBills.getXmtype())) {
                cashierBills.setPaystatus("00");// 工资状态
            }
            cashierBills.setGroupCompanySn(staff.getGroupCompanySn());//集团标识
            cashierBills.setCompanyID(StringUtils.isBlank(companyId) ? " " : companyId);//公司id
            cashierBills.setCashierDate(new Date());//单据日期  下单日期
            cashierBills.setOrganizationID(organizationID);//标示数据属于哪个部门
            // 3.1制单人信息START
            cashierBills.setInputName(staff.getStaffName());//制单人名称
            cashierBills.setInputid(staff.getStaffID());//制单人id
            cashierBills.setInputOrganizationID(organizationID);//制单人所属部门
            cashierBills.setInputOrganizationName(cashierBills.getDepartmentName());//制单人部门名称
            cashierBills.setInputCompanyid(StringUtils.isBlank(companyId) ? " " : companyId);//公司id
            cashierBills.setInputCompanyName(cashierBills.getCompanyName());//公司名称
            // 制单人信息END
            baseBeanList.add(cashierBills);
        }
        //5.循环扫码枪获取到的数据
        updatePlanGoodsBillsNew(baseBeanList, cashierBills, companyId);
        //6.记录日志
//        CLogBook logBook = logBookService.saveCLogBook(organizationID,
//                parameter, account);
//        baseBeanList.add(logBook);
        //先删除之前的明细数据
        String sql2 = " delete from GoodsBills o where o.cashierBillsID=? ";

        //7.保存表
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList, new String[]{sql2},
                new Object[]{cashierBills.getCashierBillsID()});
    }

    @Transactional
    public void updatePlanGoodsBillsNew(List<BaseBean> baseBeanList, CashierBills cashierBills, String companyId) throws Exception {
        Map<String, Object> cacheGoodsMap = MapSesssionUtil.getSessionForMap("cacheGoodsMap");
        if (cacheGoodsMap != null) {//未存入session，则将数据存入session
            GoodsBills goodsBills;
            GoodsBillsExt goodsBillsExt;
            String staffID = cashierBills.getStaffID();
            String staffName = cashierBills.getStaffName();
//            String sql = null;
//            String sql2 = null;
//            String sql3 = null;
//            String hql_del = null;
            String goodsbillsId = null;

            for (Object obj : cacheGoodsMap.values()) {
                CostBudgetAddBean addBean = (CostBudgetAddBean) obj;

                //预算单（招标投标）关联的明细项目编号如果以goodsbills，代表是已经存在的记录
                String goodsBillsID = addBean.getGoodsBillsID();
                String goodsBillsExtId = addBean.getGoodsBillsExtId();
                if (StringUtils.isNotEmpty(goodsBillsID) && goodsBillsID.startsWith("goodsbills")) {
                    goodsBills = (GoodsBills) baseBeanService
                            .getBeanByHqlAndParams("from GoodsBills where goodsBillsID=?",
                                    new Object[]{goodsBillsID});
                    goodsbillsId = goodsBills.getGoodsBillsID();
                } else {
                    goodsBills = new GoodsBills();
                    goodsbillsId = serverService.getServerID("goodsbills");
                    goodsBills.setGoodsBillsID(goodsbillsId);//物品单据id

                    if (goodsBills.getGoodsBillsID().startsWith("goodsbills")) {
                        GoodsBillsItemRecent itemRecent = new GoodsBillsItemRecent();
                        itemRecent.setId(serverService.getServerID("itemRecent"));
                        itemRecent.setCreateDate(new Date());
                        itemRecent.setStaffId(staffID);
                        itemRecent.setStaffName(staffName);
                        itemRecent.setFlag("plan");
                        itemRecent.setBarCode(addBean.getBarCode());
                        itemRecent.setGoodsName(addBean.getGoodsName());
                        itemRecent.setGoodsbillsId(goodsbillsId);

                        String sql = "  from GoodsBillsItemRecent o where o.barCode=? and o.staffId=? and o.flag='plan' ";
                        GoodsBillsItemRecent itemOld = (GoodsBillsItemRecent) baseBeanDao.getBeanByHqlAndParams(sql, new Object[]{addBean.getBarCode(), staffID});
                        if (itemOld != null) {
                            itemRecent.setKey(itemOld.getKey());
                            baseBeanDao.update(itemRecent);
                        } else {
                            baseBeanDao.save(itemRecent);
                        }
                    }

                    //TODO:当前公司没有的物品需要新增到物品表、产品表
                    //20240803刘总说，页面不能添加产品表没有的物品数据
//                    GoodsManage goodsManage = (GoodsManage) baseBeanService.getBeanByHqlAndParams("from GoodsManage o where o.companyID = ? and o.barCode = ?", new Object[]{companyId, addBean.getBarCode()});
//                    GoodsManage gm = null;
//                    ProductPackaging pp = null;
//                    if (goodsManage == null) {
//                        gm = new GoodsManage();
//                        pp = new ProductPackaging();
//                        addGoodsAndProduct(baseBeanList, gm, pp, companyId, addBean);
//                    }
                }

                if (StringUtils.isNotEmpty(goodsBillsExtId) && goodsBillsExtId.startsWith("goodsbillsExt")) {
                    goodsBillsExt = (GoodsBillsExt) baseBeanService
                            .getBeanByHqlAndParams("from GoodsBillsExt where goodsBillsExtId=?",
                                    new Object[]{goodsBillsExtId});
                } else {
                    goodsBillsExt = new GoodsBillsExt();
                    goodsBillsExtId = serverService.getServerID("goodsbillsExt");
                    goodsBillsExt.setGoodsBillsExtId(goodsBillsExtId);
                    goodsBillsExt.setGoodsBillsID(goodsbillsId);//物品单据id
                }
                goodsBills.setCashierBillsID(cashierBills.getCashierBillsID());//收支单管理id
                goodsBills.setCompanyID(companyId);//公司id
                goodsBills.setEndDate(new Date());// 入账时间
                goodsBills.setQuantity(addBean.getInvInvenQuantity());//库存数量
                goodsBills.setPrice(addBean.getPrice());//预算单价
                goodsBills.setMoney(addBean.getPrice());//预算金额
                goodsBills.setTiaoQuantity(addBean.getBudgetNumber());// 初始时将调整信息设置为预算信息
                goodsBills.setTiaoPrice(goodsBills.getPrice());
                goodsBills.setTiaoMoney(goodsBills.getMoney());
                goodsBills.setGoodsID(addBean.getGoodsId());//货物id
                goodsBills.setGoodsNum(addBean.getGoodsCoding());//品名编号
                goodsBills.setGoodsName(addBean.getGoodsName());//物品名称
                goodsBills.setGoodsVariableID(addBean.getVariableId());//货物单位
                goodsBills.setCcompanyName(addBean.getCurrentCompany());//往来公司名称
                goodsBills.setCcompanyID(addBean.getCurrentCompanyId());//往来公司id
                goodsBills.setConnectID(addBean.getCurrentPersonId());//往来个人id
                goodsBills.setConnectName(addBean.getCurrentPerson());//往来个人姓名
                goodsBills.setPpID(cashierBills.getProID());//项目ID也就是产品ID；
                goodsBills.setPriceManage("人民币");//单价管理
                goodsBills.setTargetStart(new Date());//目标起时间
                goodsBills.setTargetEnd(new Date());//目标止时间
                goodsBills.setTargetDeptID(cashierBills.getInputOrganizationID());//目标部门id
                goodsBills.setTargetDeptName(cashierBills.getInputOrganizationName());//目标部门名称
                goodsBills.setTargetSalerID(cashierBills.getInputid());//目标业务员id
                goodsBills.setTargetSalerName(cashierBills.getInputName());//目标业务员名称
                goodsBills.setBarCode(addBean.getBarCode());
                goodsBills.setGradeid(addBean.getGradeid());
                goodsBills.setGradeName(addBean.getGradeName());
                goodsBills.setInvInvenQuantity(addBean.getInvInvenQuantity());
                goodsBills.setTradeID(addBean.getTradeID());
                goodsBills.setTradeCode(addBean.getTradeCode());
                goodsBills.setTradeName(addBean.getTradeName());
                goodsBills.setProductCode(addBean.getProductCode());
                goodsBills.setProducttype(addBean.getProducttype());
                goodsBills.setTypeID(addBean.getTypeID());
                goodsBills.setIsScale(addBean.getIsScale());
                goodsBills.setUnitofmeasurecode(addBean.getUnitofmeasurecode());
                goodsBills.setBrand(addBean.getBrand());
                goodsBills.setGuigeType(addBean.getGuigeType());
                goodsBills.setGuigeTypeId(addBean.getGuigeTypeId());
                goodsBills.setGuigeTypeValue(addBean.getGuigeTypeValue());
                goodsBills.setUnitPrice(addBean.getUnitPrice());
                goodsBills.setAmount(addBean.getAmount());
                goodsBills.setGoodsPurpose(addBean.getGoodsPurpose());
                goodsBills.setGoodsPurposeId(addBean.getGoodsPurposeId());

                goodsBills.setServiceStartDate(addBean.getServiceStartDate());
                goodsBills.setServiceEndDate(addBean.getServiceEndDate());
                goodsBills.setConnectName(addBean.getConnectName());

//                goodsBills.setCcompanyID(addBean.getCcompanyID());
//                goodsBills.setCcompanyName(addBean.getCcompanyName());
//                goodsBills.setConnectName(addBean.getConnectName());
//                goodsBills.setConnectID(addBean.getConnectID());

                goodsBillsExt.setAccountFlag(addBean.getAccountFlag());
                goodsBillsExt.setAccountFlagFrom(addBean.getAccountFlagFrom());
                if ("companyPlan".equals(addBean.getAccountFlag())) {
                    goodsBillsExt.setAccountName(addBean.getAccountName());
                    goodsBillsExt.setAccountNameId(addBean.getAccountNameId());
                    goodsBillsExt.setAccountNum(addBean.getAccountNum());
                    goodsBillsExt.setOpenBank(addBean.getOpenBank());
                    goodsBillsExt.setAccountPhone(addBean.getAccountPhone());
                } else {
                    goodsBillsExt.setAccountName(addBean.getAccountNameP());
                    goodsBillsExt.setAccountNameId(addBean.getAccountNameIdP());
                    goodsBillsExt.setAccountNum(addBean.getAccountNumP());
                    goodsBillsExt.setOpenBank(addBean.getOpenBankP());
                    goodsBillsExt.setAccountPhone(addBean.getAccountPhoneP());
                }
                if ("companyPlan".equals(addBean.getAccountFlagFrom())) {
                    goodsBillsExt.setAccountNameFrom(addBean.getAccountNameFrom());
                    goodsBillsExt.setAccountNameIdFrom(addBean.getAccountNameIdFrom());
                    goodsBillsExt.setAccountNumFrom(addBean.getAccountNumFrom());
                    goodsBillsExt.setOpenBankFrom(addBean.getOpenBankFrom());
                    goodsBillsExt.setAccountPhoneFrom(addBean.getAccountPhoneFrom());
                } else {
                    goodsBillsExt.setAccountNameFrom(addBean.getAccountNameFromP());
                    goodsBillsExt.setAccountNameIdFrom(addBean.getAccountNameIdFromP());
                    goodsBillsExt.setAccountNumFrom(addBean.getAccountNumFromP());
                    goodsBillsExt.setOpenBankFrom(addBean.getOpenBankFromP());
                    goodsBillsExt.setAccountPhoneFrom(addBean.getAccountPhoneFromP());
                }
                goodsBillsExt.setLoanDirection(addBean.getLoanDirection());
                goodsBillsExt.setBudgetAmount(addBean.getBudgetAmount());
                goodsBillsExt.setBorrowAmount(addBean.getBorrowAmount());
                goodsBillsExt.setLoanAmount(addBean.getLoanAmount());
                goodsBillsExt.setInitialBalance(addBean.getInitialBalance());
                goodsBillsExt.setBalance(addBean.getBalance());
                goodsBillsExt.setOrderNum(addBean.getOrderNum());
                goodsBillsExt.setSpecsParentId(addBean.getSpecsParentId());
                goodsBillsExt.setTeamPunishment(addBean.getTeamPunishment());
                goodsBillsExt.setTeamReward(addBean.getTeamReward());
                goodsBillsExt.setPersonalReward(addBean.getPersonalReward());
                goodsBillsExt.setPersonalPunishment(addBean.getPersonalPunishment());

                if (StringUtils.isNotEmpty(addBean.getLogoPath())) {
                    goodsBills.setLogoPath(addBean.getLogoPath());//品牌log路径
                }

                //图片、视频的保存
//                String photoStr = addBean.getPhotoStr();
//                String videoStr = addBean.getVideoStr();
                List<BaseBean> photoList = addBean.getPhotoList();
                List<BaseBean> videoList = addBean.getVideoList();


                // 保存物品主图
                if (CollectionUtil.isNotEmpty(photoList)) {
                    String sql3 = "delete AttriProduction where goodsid=? and type='2' ";
                    baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{sql3}, new Object[]{goodsbillsId});
                    saveAttriProductionOfCostBudgetNew(photoList, goodsbillsId, baseBeanList, "I");
                }
                // 保存视频
                if (CollectionUtil.isNotEmpty(videoList)) {
                    String sql3 = "delete AttriProduction where goodsid=? and type='3' ";
                    baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{sql3}, new Object[]{goodsbillsId});
                    saveAttriProductionOfCostBudgetNew(videoList, goodsbillsId, baseBeanList, "V");
                }

                // 保存产品颜色，规格
//                sql = "delete AttriProduction where goodsid=? and type='1' ";
//                sql2 = "delete AttriProduction where goodsid=? and type='0' ";
//                sql3 = "delete AttriProduction where goodsid=? and type='4' ";
                //规格数据保存
//                if (StringUtils.isNotEmpty(addBean.getColorvalue())) {
//                    pmService.saveAttriProduction(addBean.getColorvalue(), addBean.getAttrinames(), goodsbillsId, baseBeanList, "1");
//                }
//                if (StringUtils.isNotEmpty(addBean.getSizevalue())) {
//                    pmService.saveAttriProduction(addBean.getSizevalue(), addBean.getAttrinamec(), goodsbillsId, baseBeanList, "0");
//                }
                if (StringUtils.isNotEmpty(addBean.getAttri())) {
                    String sql3 = "delete AttriProduction where goodsid=? and type='4' ";
                    baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{sql3}, new Object[]{goodsbillsId});
                    pmService.saveAttriProduction(addBean.getAttri(), "", goodsbillsId, baseBeanList, "4");
                }
                // 保存产品描述
                List<Object[]> params = new ArrayList<Object[]>();
                if (StringUtils.isNotEmpty(addBean.getHtl())) {
                    if (StringUtils.isNotEmpty(goodsbillsId)) {
                        String filpath = ServletActionContext.getServletContext()
                                .getRealPath("/");
                        params.add(new Object[]{goodsbillsId});
                        GoodFunction gf = (GoodFunction) baseBeanService
                                .getBeanByHqlAndParams(
                                        "from GoodFunction where goodsid= ?",
                                        new Object[]{goodsbillsId});
                        if (gf != null) {
                            String sql3 = "delete GoodFunction where goodsid=? ";
                            baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{sql3}, new Object[]{goodsbillsId});
                            FileUtil.delete(filpath + gf.getUrl());
                        }
                        GoodFunction goodFun = new GoodFunction();
                        goodFun.setGfid(serverService.getServerID("gfid"));
                        goodFun.setUrl(addBean.getHtlPath());
                        goodFun.setName("物品功能");
                        goodFun.setType("1");
                        goodFun.setGoodsid(goodsbillsId);
                        baseBeanList.add(goodFun);
                    }
                }
                baseBeanList.add(goodsBills);
                baseBeanList.add(goodsBillsExt);
            }

        }
    }

    public String approval(String cashierBillsId, String approvalResult){
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return "error";
        }
        CashierBills cashierBills = (CashierBills) baseBeanService
                .getBeanByHqlAndParams("from CashierBills o where o.cashierBillsID = ?", new Object[]{cashierBillsId});
        if (Objects.nonNull(cashierBills)) {
            String responsible = cashierBills.getResponsible();
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(responsible) && !responsible.equals(account.getStaffID())){
                return "userError";
            }
            if(APPROVAL_RESULT_AGREE.equals(approvalResult)){
                cashierBills.setStatus("07");//已审核
                cashierBills.setResponsible(account.getStaffID());
            }else{
                cashierBills.setStatus("11");//驳回待修改
                cashierBills.setResponsible(account.getStaffID());
            }
            baseBeanService.update(cashierBills);
            return "OK";
        }
        return "error";
    }

    public String changeBillsType(String cashierBillsId, String billsType){
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return "error";
        }
        CashierBills cashierBills = (CashierBills) baseBeanService
                .getBeanByHqlAndParams("from CashierBills o where o.cashierBillsID = ?", new Object[]{cashierBillsId});
        if (Objects.nonNull(cashierBills)) {
            cashierBills.setBillsType(billsType);
            cashierBills.setStatus("00");
            baseBeanService.update(cashierBills);
            return "OK";
        }
        return "error";
    }

    public String getDepotByCompanyId(String companyId, String depotPId) {
        List<BaseBean> beanList = getDepot(companyId, depotPId);
        Map<String, Object> map = new HashMap<String, Object>();
        if (null != beanList) {
            map.put("depotList", beanList);
        }
        JSONObject oj = JSONObject.fromObject(map);
        return oj.toString();
    }
    public List<BaseBean> getDepot(String companyId, String depotPId) {
        StringBuffer sb = new StringBuffer();
        sb.append("select d from DepotManage d ");
        sb.append("where d.depotPID = '001' and d.depotState = '00' and d.depotType = '1' ");
        sb.append("and d.companyID = ? ");
        sb.append("order by d.depotNum");
        List<Object> params = new ArrayList<Object>();
        params.add(companyId);
        List<BaseBean> beanList = baseBeanDao.getListBeanByHqlAndParams(sb.toString(), params.toArray());
        return beanList;
    }

    @Transactional
    @Override
    public String saveCostBudgetBill(String billsType,String cashierBillsData,List<GoodsBillsData> goodsBillsDataList) {
        if(StringUtils.isEmpty(billsType) || Objects.isNull(goodsBillsDataList) || goodsBillsDataList.size() == 0){
            return null;
        }
        String businessTypeName = null;
        String startDate = null;
        String endDate = null;
        String companyId = null;
        String staffId = null;
        String[] cashierBills = null;
        if(StringUtils.isNotEmpty(cashierBillsData)){
            cashierBills =  cashierBillsData.split(",");
        }else{
            cashierBills =  new String[8];
            cashierBills[5] = "酒、饮料及茶叶零售";
        }
        businessTypeName = cashierBills[5].replace("四级行业：","");
        startDate =  StringUtils.isNotEmpty(cashierBills[6]) && !"undefined".equalsIgnoreCase(cashierBills[6]) ?
                cashierBills[6].replace("单据日期：","").replace(org.apache.commons.lang3.StringUtils.SPACE,"") : null;
        if(StringUtils.isEmpty(startDate)){
            startDate = null;
        }

        String toAccountName = null;
        String toAccountTel = null;
        String toAccountBank = null;
        String toAccountNo = null;
        String toAccount = StringUtils.isNotEmpty(cashierBills[7]) && !"undefined".equalsIgnoreCase(cashierBills[7]) ? cashierBills[7].replace("户名：","").replace("账户：","") : null;
        if(Objects.nonNull(toAccount)){
            toAccountName = toAccount.split(org.apache.commons.lang3.StringUtils.SPACE)[0];
            toAccountBank = toAccount.split(org.apache.commons.lang3.StringUtils.SPACE)[1];
            toAccountNo = toAccount.split(org.apache.commons.lang3.StringUtils.SPACE)[2];
        }

        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.nonNull(account)) {
            companyId = account.getCompanyID();
            staffId = account.getStaffID();
            if(StringUtils.isEmpty(staffId)){
                return null;
            }
        }else{
            return null;
        }
        Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staffId});
        String organizationID = null;
        String organizationName = null;
        List<BaseBean> beans = this.getOrganization(companyId,staffId);
        if(Objects.nonNull(beans) && beans.size() > 0){
            organizationID = ((COrganization)beans.get(0)).getOrganizationID();
            organizationName = ((COrganization)beans.get(0)).getOrganizationName();
        }

        String cashierBillsID = serverService.getServerID("cashierTally");
        String journalNum = serverService.getBillID(companyId);

        Company company =  (Company) baseBeanService
                .getBeanByHqlAndParams("from Company where companyID = ?", new Object[]{companyId});
        String companyName = Objects.nonNull(company) ? company.getCompanyName() : null;
        String businessTypeId = null;
        String businessType = null;
        List<BaseBean> businessTypebeans = baseBeanService
                .getListBeanByHqlAndParams("from BusinessType where typeName = ? and status = '1'", new Object[]{businessTypeName});
        if(Objects.nonNull(businessTypebeans) && businessTypebeans.size() > 0){
            businessTypeId = ((BusinessType)businessTypebeans.get(0)).getTypeId();
            businessType = ((BusinessType)businessTypebeans.get(0)).getTypeName();
        }
        String specsCode = "65b2ae02e0604dc9aa4e69e2894d0ae0";

        List<BaseBean> baseBeanList = new ArrayList<>();
        List<String> sqls = new ArrayList<String>();
        List<Object[]> objs = new ArrayList<Object[]>();
        //goodsBills
        String initAmount = "0";
        int orderNum = 1;
        for(GoodsBillsData goodsBillsData : goodsBillsDataList){
            String goodsBillsId = serverService.getServerID("goodsbills");
            String goodsbillsExtId = serverService.getServerID("goodsbillsExt");

            String barCode = goodsBillsData.getBarCode();
            String goodsNameExcel = goodsBillsData.getGoodsName();
            String specs = StringUtils.isNotEmpty(goodsBillsData.getSpecs()) ? goodsBillsData.getSpecs().substring(2) : "1";
            String count = StringUtils.isNotEmpty(goodsBillsData.getCount()) ? goodsBillsData.getCount() : "0";
            String price = StringUtils.isNotEmpty(goodsBillsData.getPrice()) ? goodsBillsData.getPrice() : "0";
            String amount = new BigDecimal(price).multiply(new BigDecimal(count)).setScale(2, RoundingMode.HALF_UP).toString();
            String currentAmount = new BigDecimal("-" + amount).add(new BigDecimal(initAmount)).setScale(2, RoundingMode.HALF_UP).toString();

            String goodsID = null;
            String goodsName = null;
            List<BaseBean> goodsManagebeans = baseBeanService
                    .getListBeanByHqlAndParams("from GoodsManage where companyID = ? and barCode = ?", new Object[]{companyId,barCode});
            if(Objects.nonNull(goodsManagebeans) && goodsManagebeans.size() > 0){
                goodsID = ((GoodsManage)goodsManagebeans.get(0)).getGoodsID();
                goodsName = ((GoodsManage)goodsManagebeans.get(0)).getGoodsName();
            }
            if(StringUtils.isEmpty(goodsName)){
                goodsName = goodsNameExcel;
            }
            if(Objects.isNull(companyId)){
                companyId = org.apache.commons.lang3.StringUtils.SPACE;
            }

            String insertGoodsBillSql = "INSERT INTO HYPLAT.DTGOODSBILLS " +
                    "(GOODSBILLSKEY, GOODSBILLSID, COMPANYID, CASHIERBILLSID, GOODSID, SUBJECTSID, SUBJECTSNAME, STARTDATE, ENDDATE, PRICEMANAGE," +
                    " GOODSVARIABLEID,GOODSNAME, TYPEID,TARGETSTART,TARGETEND, TARGETDEPTID, TARGETDEPTNAME, TARGETSALERID, TARGETSALERNAME," +
                    "TIAOQUANTITY, BARCODE,TRADENAME, TRADEID, TRADECODE, PRODUCTTYPE, PRODUCTCODE, ISSCALE, UNITPRICE, AMOUNT, GOODSPURPOSE, GUIGETYPE, GUIGETYPEID," +
                    "GOODSPURPOSEID, GUIGETYPEVALUE, CONNECTNAME, SERVICESTARTDATE, SERVICEENDDATE) " +
                    "VALUES " +
                    "(sys_guid(),?,?,?,?,NULL, NULL, NULL, CURRENT_TIMESTAMP, '人民币','包',?,?,CURRENT_TIMESTAMP," +
                    "CURRENT_TIMESTAMP, NULL,?,?,?,?,?,?,?,?,?,?, '1',?,?,?,'个','4QYQL',?,?,?,NULL,NULL)";
            String insertGoodsBillExtSql = "INSERT INTO HYPLAT.DTGOODSBILLSEXT " +
                    "(GOODSBILLSEXTKEY, GOODSBILLSEXTID, GOODSBILLSID, ACCOUNTNAMEID, ACCOUNTNAME, ACCOUNTFLAG, ACCOUNTNUM," +
                    "ACCOUNTPHONE, OPENBANK, BUDGETAMOUNT, LOANDIRECTION, BORROWAMOUNT, LOANAMOUNT, ORDERNUM, BALANCE, INITIALBALANCE, SPECSPARENTID," +
                    "ACCOUNTNAMEID_FROM, ACCOUNTNAME_FROM, ACCOUNTFLAG_FROM, ACCOUNTNUM_FROM, ACCOUNTPHONE_FROM, OPENBANK_FROM) " +
                    "VALUES " +
                    "(sys_guid(),?,?, NULL,?, 'personal',?,?," +
                    "?,?, '付', NULL,?,?,?,?,?,?,?, 'company',?,?,?)";
            sqls.add(insertGoodsBillSql);
            objs.add(new Object[]{goodsBillsId,companyId,cashierBillsID,goodsID,goodsName,businessType,organizationName,staff.getStaffID(),
                    staff.getStaffName(),count,barCode,businessType,businessTypeId,businessTypeId,businessType,businessTypeId,price,
                    amount,businessType,businessTypeId,specs + " 个",toAccountName});
            sqls.add(insertGoodsBillExtSql);
            objs.add(new Object[]{goodsbillsExtId,goodsBillsId,toAccountName,toAccountNo,toAccountTel,toAccountBank,
                    "-" + amount,amount,String.valueOf(orderNum),currentAmount,initAmount,specsCode,ACCOUNT_COMPANY_ID,ACCOUNT_COMPANY_NAME,ACCOUNT_NO,ACCOUNT_TEL,ACCOUNT_BANK});
            initAmount = currentAmount;
            orderNum += 1;

            pmService.saveAttriProduction("个-" + specs, "", goodsBillsId, baseBeanList, "4");
            GoodFunction goodFun = new GoodFunction();
            goodFun.setGfid(serverService.getServerID("gfid"));
            goodFun.setName("物品功能");
            goodFun.setType("1");
            goodFun.setGoodsid(goodsBillsId);
            baseBeanList.add(goodFun);
        }
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList, null, null);

        String totalMoney = initAmount;
        String insertCostBudgetBillSql = "INSERT INTO HYPLAT.DTCASHIERBILLS " +
                "(CASHIERBILLSKEY, CASHIERBILLSID, COMPANYID," +
                "BILLSTYPE, JOURNALNUM, STAFFID, CASHIERDATE, DISCOUNTMONEY, AFTERDISCOUNT, STATUS, " +
                "COMPANYNAME, DEPARTMENTNAME, CTUSERNAME, CCOMPANYNAME, STAFFNAME, STAFFCODE," +
                "XMTYPE, XMTYPENAME, CSBJOURNALNUM, GROUPCOMPANYSN, CONTENT, INPUTID, INPUTNAME, INPUTCOMPANYID," +
                "INPUTCOMPANYNAME, INPUTORGANIZATIONID, INPUTORGANIZATIONNAME,ZCTYPE,PRICESUB," +
                "TRADEID, TRADENAME,STARTTIME,ENDTIME) " +
                "VALUES " +
                "(sys_guid(),?,?,?,?,?,CURRENT_TIMESTAMP, NULL, NULL, '00'," +
                "?,?,?, NULL,?,?,?,?,NULL,?, NULL,?,?,?," +
                "?,?,?, 'cg',?,?,?,TO_DATE(?, 'YYYY-MM-DD'),TO_DATE(?, 'YYYY-MM-DD'))";
        sqls.add(insertCostBudgetBillSql);
        objs.add(new Object[]{cashierBillsID,companyId,billsType,journalNum,staff.getStaffID(),companyName,organizationName,toAccountName,staff.getStaffName(),staff.getStaffCode(),
                businessTypeId,businessType,staff.getGroupCompanySn(),staff.getStaffID(),staff.getStaffName(),companyId,companyName,organizationID,
                organizationName,totalMoney,businessTypeId,businessType,startDate,endDate});
        baseBeanService.executeSqlsByParmsList(null, sqls.toArray(new String[]{}) , objs);
        return Action.SUCCESS;
    }

    @Override
    public void addBusinessTypeRecent(BusinessTypeRecent businessTypeRecent) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return;
        }
        businessTypeRecent.setStaffId(account.getStaffID());
        businessTypeRecent.setCreateDate(new Date());
        baseBeanService.save(businessTypeRecent);
    }
    @Override
    public void addGoodsRecent(GoodsBillsItemRecent goodsRecent) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return;
        }
        goodsRecent.setId(serverService.getServerID("itemRecent"));
        goodsRecent.setStaffId(account.getStaffID());
        goodsRecent.setStaffName(account.getStaffName());
        goodsRecent.setCreateDate(new Date());
        baseBeanService.save(goodsRecent);
    }

    public static void main(String[] args) {
        String str = "开始-2024-02-12 12:25";
        String[] split = str.split("-", 2);
        System.out.println(split[0]);
        System.out.println(split[1]);
    }
}
