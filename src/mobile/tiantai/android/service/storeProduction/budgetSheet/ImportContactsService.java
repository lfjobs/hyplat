package mobile.tiantai.android.service.storeProduction.budgetSheet;

import hy.plat.bo.PageForm;
import mobile.tiantai.android.action.storeProduction.budgetSheet.AllocationContacts;
import mobile.tiantai.android.action.storeProduction.budgetSheet.ImportContactsDto;
import mobile.tiantai.android.bo.StaffSend;

import java.util.List;

/**
 * ImportContactsService
 *
 * @author zch
 */
public interface ImportContactsService {
    String importContacts(List<ImportContactsDto> importContactsDto);

    PageForm selectImportContactsList(Integer pageNumber, Integer pageSize);

    String deleteImportContacts(String id);

    String updateImportContacts(String id, String name, String phone);

    String allocationImportContacts(List<StaffSend> staffSendList,List<AllocationContacts>   allocationContacts);

    PageForm selectImportContacts(Integer pageNumber, Integer pageSize);
}
