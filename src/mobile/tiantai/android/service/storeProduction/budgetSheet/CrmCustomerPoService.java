package mobile.tiantai.android.service.storeProduction.budgetSheet;


import hy.plat.bo.PageForm;
import mobile.tiantai.android.action.storeProduction.budgetSheet.CrmCustomerVo;
import mobile.tiantai.android.bo.CrmCustomerPO;

import java.util.List;

/**
 * CrmCustomerPoService
 *
 * @author zch
 */
public interface CrmCustomerPoService {
    /**
     * 查询数据
     *
     * @return
     */
    List<CrmCustomerPO> selectCrmCustomerPOList();


    void saveImportData(List<CrmCustomerVo> crmCustomerVoList);

    void deleteCustomer(String id);

    void updateData(CrmCustomerPO crmCustomerPO);

    CrmCustomerPO updCustomer(String id);

    void addData(CrmCustomerPO addCustomerPO);

    PageForm selectCrmCustomerPOList1(Integer pageNumber, Integer pageSize, String type);

    String deleteCrmCustomerPO(String id);

    String updateData1(CrmCustomerPO old);

    String addData1(CrmCustomerPO addCustomerPO);

    String saveImportData1(List<CrmCustomerVo> dataDTOList);

    PageForm selectCrmCustomerPOList2(String type, Integer pageNumber, Integer pageSize);

    CrmCustomerPO queryCustomer(String id);

    List<CrmCustomerVo> contrastStaff(List<CrmCustomerVo> dataDTOList);


    PageForm selectreferenceList(Integer pageNumber, Integer pageSize);
}
