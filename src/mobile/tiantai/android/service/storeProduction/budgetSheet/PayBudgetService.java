package mobile.tiantai.android.service.storeProduction.budgetSheet;

import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.*;
import hy.ea.bo.finance.GoodsBillsContactRecent;
import hy.plat.bo.BaseBean;
import hy.plat.bo.BusinessTypeRecent;
import mobile.tiantai.android.action.storeProduction.budgetSheet.GoodsBillsData;
import mobile.tiantai.android.bean.payBudget.CostBudgetAddBean;
import mobile.tiantai.android.bean.payBudget.PayBudgetAddBean;
import org.hibernate.criterion.DetachedCriteria;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预支付预算单service
 * Created by lyc on 2018-10-08.
 */
public interface PayBudgetService {

    /**
     * 根据登录帐号查询展示组织机构名称
     *
     * @param organizationID
     * @return
     * @throws Exception
     */
    String findOrgByAcc(String organizationID, String companyId, String staffId) throws Exception;

    /**
     * 将参数信息格式化成DetachedCriteria返回
     *
     * @param staffId      登录id
     * @param companyId    公司id
     * @param departmentID 部门id
     * @param showFlag     false查看总列表true查看分列表
     * @param search       模糊查询
     * @param searchType   模糊查询类型
     * @return 拼接好的DetachedCriteria对象
     * @throws Exception
     */
    DetachedCriteria getDc(String staffId, String companyId, String departmentID, boolean showFlag, String search, int searchType) throws Exception;

    /**
     * 判断是否是提交扫描数据
     *
     * @param scanningMap 存入session的map集合
     * @param addBean     添加预算单参数bean
     * @param mapKey      sessiong中map的key值
     * @throws Exception
     */
    void toAddPayBudget(Map<String, Object> scanningMap, PayBudgetAddBean addBean, String mapKey) throws Exception;

    /**
     * 异步根据部门id查询部门下在职员工信息
     *
     * @param departmentID 部门id
     * @return 将结果以字符串形式返回
     * @throws Exception
     */
    String ajaxStaffForDep(String departmentID) throws Exception;

    /**
     * 将产品信息信息格式化成DetachedCriteria返回
     *
     * @param search 模糊查询条件
     * @return 拼接好的DetachedCriteria对象
     * @throws Exception
     */
    DetachedCriteria getProDc(String search) throws Exception;

    /**
     * 判断商品是否存在拼接扫描参数信息到bean中
     *
     * @param goodsManage 商品表
     * @param addBean     添加预算单参数bean
     * @throws Exception
     */
    void splicingAddBean(GoodsManage goodsManage, PayBudgetAddBean addBean) throws Exception;

    /**
     * 将往来公司信息信息格式化成DetachedCriteria返回
     *
     * @param search 模糊查询条件
     * @return 拼接好的DetachedCriteria对象
     * @throws Exception
     */
    DetachedCriteria getWlGsDc(String search) throws Exception;

    /**
     * 异步根据key值删除session中的map的值
     *
     * @param mapKey 选中key值
     * @throws Exception
     */
    void removeBeanForKey(String mapKey) throws Exception;

    /**
     * 保存生成预算单信息
     *
     * @param cashierBills 前台传过来的bean
     * @throws Exception
     */
    void saveCostSheet(CashierBills cashierBills) throws Exception;

    /**
     * 修改预算单信息
     *
     * @param cashierForm 前台传过来的bean
     * @throws Exception
     */
    void upCostSheet(CashierBills cashierForm) throws Exception;

    /**
     * 将已发布/未发布参数信息格式化成DetachedCriteria返回
     *
     * @param fbJumpType 发布跳转标识0：发布页；1：已发布页；2：未发布页
     * @return 拼接好的DetachedCriteria对象
     * @throws Exception
     */
    DetachedCriteria getReleaseDc(int fbJumpType) throws Exception;

    /**
     * 上传文件
     *
     * @param chunk
     * @param chunks
     * @param fileName
     * @param file
     * @param path
     * @param companyid
     * @return
     * @throws Exception
     */
    Map upLoadFile(String chunk, String chunks, String fileName, File file, String path, String companyid) throws Exception;

    /**
     * 将中间页面缓存中的数据放入主页面的缓存中
     *
     * @param scanningMap 存入session的map集合
     * @throws Exception
     */
    void toAddCostBudgetBill(Map<String, Object> cacheGoodsMap, Map<String, Object> scanningMap) throws Exception;


    /**
     * 根据员工ID和公司id查询匹配的部门信息
     *
     * @param companyId
     * @param staffId
     * @return
     */
    String getOrganizationByStaff(String companyId, String staffId);

    /**
     * 招标投标
     *
     * @param staffId
     * @param companyId
     * @param type
     * @param showFlag
     * @param search
     * @param searchType
     * @return
     * @throws Exception
     */
    DetachedCriteria getBudgetBillDc(String staffId, String companyId, String type, boolean showFlag, String search, int searchType, String tenantFlag, String billsType, String approvedStatus) throws Exception;

    void splicingAddBudgetBean(GoodsManage goodsManage, CostBudgetAddBean addBean) throws Exception;

    /**
     * 招标发布单提交
     *
     * @param cashierBills
     * @throws Exception
     */
    void saveCostBudgetSheet(CashierBills cashierBills) throws Exception;

    void saveAttriProduction(String filepath, String goodsbillsId, List<BaseBean> beans, String type);

    void getBudgetItemInfo(GoodsBills goodsBills, GoodsBillsExt ext, CostBudgetAddBean costAddBean);

    void getUpdateCostBudgetItem(Map<String, Object> scanGoodsMap, List<BaseBean> list, CostBudgetAddBean costAddBean);

    /**
     * 修改预算单（招标投标单）
     *
     * @param cashierBills
     */
    void updateCostBudgetSheet(CashierBills cashierBills, String menuId) throws Exception;

    /**
     * 预算（招标投标）附件上传
     *
     * @param filepath
     * @param goodsbillsId
     * @param beans
     * @param type
     */
    void saveAttriProductionOfCostBudget(String filepath, String goodsbillsId, List<BaseBean> beans, String type);

    void saveAttriProductionOfCostBudgetNew(List<BaseBean> list, String goodsbillsId, List<BaseBean> beans, String type);

    List<BaseBean> getOrganization(String companyId, String staffId);

    /**
     * 跳转招标投标中间页面
     *
     * @param scanningMap 存入session的map集合
     * @param costAddBean 添加招标投标单参数bean
     * @param mapKey      sessiong中map的key值
     * @throws Exception
     */
    void toAddCostBudgetItem(Map<String, Object> scanningMap, CostBudgetAddBean costAddBean, String mapKey) throws Exception;


    /**
     * 初始项目单接收列表
     *
     * @param staffId
     * @param companyId
     * @param search
     * @param searchType
     * @return
     */
    DetachedCriteria getBudgetBillReceive(String staffId, String companyId, String search, int searchType) throws Exception;

    /**
     * 初始项目单已发送列表
     *
     * @param staffId
     * @param companyId
     * @param search
     * @param searchType
     * @return
     * @throws Exception
     */
    DetachedCriteria getBudgetBillSent(String staffId, String companyId, String search, int searchType) throws Exception;

    List<GoodsBillsContactRecent> getGoodsBillsContactRecent(String staffId, String accountFlag);

    List<GoodsBillsItemRecent> getGoodsBillsItemRecent(String staffId, String flag);

    CostBudgetAddBean getCostBudgetItemById(String goodsbillsId);

    /**
     * 获取单位数据
     * @return
     */
    List<BaseBean> getUnitData();

    /**
     * 查询单位关联的规格数据
     * @param parentId
     * @return
     */
    List<HashMap<String, String>> getSpecsByParent(String parentId);

    /**
     * 查询具体的规格数据
     * @param code
     * @param parentId
     * @return
     */
    List<BaseBean> getSpecsInfo(String code, String parentId);

    /**
     * 项目计划
     *
     * @param staffId
     * @param companyId
     * @param type
     * @param showFlag
     * @param search
     * @param searchType
     * @return
     * @throws Exception
     */
    DetachedCriteria getPlanBudgetBillDc(String staffId, String companyId, String type, boolean showFlag, String search, int searchType, String tenantFlag) throws Exception;

    /**
     * 项目计划单接收列表
     *
     * @param staffId
     * @param companyId
     * @param search
     * @param searchType
     * @return
     */
    DetachedCriteria getPlanBudgetBillReceive(String staffId, String companyId, String search, int searchType) throws Exception;

    /**
     * 项目计划单已发送列表
     *
     * @param staffId
     * @param companyId
     * @param search
     * @param searchType
     * @return
     * @throws Exception
     */
    DetachedCriteria getPlanBudgetBillSent(String staffId, String companyId, String search, int searchType) throws Exception;

    /**
     * 项目计划中间页面
     *
     * @param scanningMap 存入session的map集合
     * @param costAddBean 添加招标投标单参数bean
     * @param mapKey      sessiong中map的key值
     * @throws Exception
     */
    void toAddPlanCostBudgetItem(Map<String, Object> scanningMap, CostBudgetAddBean costAddBean, String mapKey) throws Exception;

    /**
     * 项目计划单提交
     *
     * @param cashierBills
     * @throws Exception
     */
    void savePlanCostBudgetSheet(CashierBills cashierBills) throws Exception;

    /**
     * 修改预算单（项目计划）
     *
     * @param cashierBills
     */
    void updatePlanCostBudgetSheet(CashierBills cashierBills, String menuId) throws Exception;

    /**
     * 审核项目
     * @param cashierBillsId
     * @param approvalResult
     */
    String approval(String cashierBillsId, String approvalResult);

    String getDepotByCompanyId(String companyId, String depotPId);

    List<BaseBean> getDepot(String companyId, String depotPId);

    String changeBillsType(String cashierBillsId, String billsType);

    String saveCostBudgetBill(String billsType,String cashierBillsData,List<GoodsBillsData> goodsBillsDataList);

    void addBusinessTypeRecent(BusinessTypeRecent businessTypeRecent);

    void addGoodsRecent(GoodsBillsItemRecent goodsRecent);
}
