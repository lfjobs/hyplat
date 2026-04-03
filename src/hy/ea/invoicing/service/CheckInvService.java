package hy.ea.invoicing.service;

import hy.ea.bo.invoicing.InvCheckGoods;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.InvtFbCheck;
import hy.plat.bo.PageForm;
import mobile.tiantai.android.bean.checkInv.CheckInvAddBean;

/**
 * Created by Administrator on 2019-10-23.
 */
public interface CheckInvService {
     //列表展示
     PageForm getCheckInvList(String comid, String staffid,String orgid, Integer pageNumber,boolean showFlag);


     //添加数据
//     void toAddCheckInv(Map<String, Object> scanningMap, CheckInvAddBean addBean, String mapKey) throws Exception;

     void splicingAddBean(Inventory inventory, CheckInvAddBean addBean) throws Exception;


     /**
      * 根据登录帐号查询展示组织机构名称
      *
      * @param organizationID
      * @return
      * @throws Exception
      */
     String findOrgByAcc(String organizationID,String companyId,String staffId) throws Exception;

     /**
      * 异步根据部门id查询部门下在职员工信息
      *
      * @param departmentID 部门id
      * @return 将结果以字符串形式返回
      * @throws Exception
      */
     String ajaxStaffForDep(String departmentID) throws Exception;

     /**
      * 保存生成预算单信息
      *
      * @param cashierBills 前台传过来的bean
      * @param addBean
      * @throws Exception
      */
     void saveCostSheet(InvtFbCheck invtFbCheck, CheckInvAddBean addBean) throws Exception;

     void upCostSheet(InvtFbCheck invtFbForm) throws Exception;
     /**
      * 异步根据key值删除session中的map的值
      *
      * @param mapKey 选中key值
      * @throws Exception
      */
     void removeBeanForKey(String fbillid) throws Exception;

     void removeGoods(String goodsID, String fbillid) throws Exception;




}
