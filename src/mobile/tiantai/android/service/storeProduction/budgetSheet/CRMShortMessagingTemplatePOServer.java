package mobile.tiantai.android.service.storeProduction.budgetSheet;

import hy.plat.bo.PageForm;
import mobile.tiantai.android.action.storeProduction.budgetSheet.IntendedCustomersDto;
import mobile.tiantai.android.action.storeProduction.budgetSheet.TemplateDto;
import mobile.tiantai.android.bo.CRMShortMessagingTemplatePO;
import mobile.tiantai.android.bo.StaffSend;

import java.util.List;
import java.util.Map;

/**
 * CRMShortMessagingTemplatePOServer
 *
 * @author zch
 */
public interface CRMShortMessagingTemplatePOServer {

    String saveTemplate(TemplateDto templateDtoList);

    PageForm getTemplate(Integer pageNumber, Integer pageSize);

    String deleteTemplate(String id);

    String updateTemplateDate(CRMShortMessagingTemplatePO old);

    CRMShortMessagingTemplatePO selectById(String id);

    PageForm selectTemplate(Integer pageNumber, Integer pageSize, String templateHeadline);

    Map<String, String> getNum();

    PageForm getCompany(Integer pageNumber, Integer pageSize);

    PageForm getStaffList(String companyId,Integer pageNumber, Integer pageSize);

    PageForm getStaffListByName(String staffName, String companyId, Integer pageNumber, Integer pageSize);

    String send(String localTemplateText, String firstName,List<StaffSend> StaffSendList);

    List<String> getCategoryList(String companyId);

    PageForm getStaffListByRelation(String relation, String companyId, Integer pageNumber, Integer pageSize);

    PageForm getSendStaffList(String isSend,String companyId, Integer pageNumber, Integer pageSize);

    String saveIntendedCustomers(String type1, IntendedCustomersDto intendedCustomersList);

    PageForm getIntendedCustomersList(String typeValue, String sccId, Integer pageNumber, Integer pageSize);
}
