package mobile.tiantai.android.service.storeProduction.budgetSheet;

import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import mobile.tiantai.android.action.storeProduction.budgetSheet.DataDTO;
import mobile.tiantai.android.bo.StaffSend;
import mobile.tiantai.android.bo.TrilateralData;

import java.util.List;

/**
 * TrilateralDataService
 *
 * @author zch
 */
public interface TrilateralDataService {
    /**
     * 列表
     *
     * @return
     */
    List<TrilateralData> getTrilateralDataList(String uploadProject);

    void addTrilateralData(TrilateralData addTrilateralData, String photoPath);

    TrilateralData findTrilateralDataByPhone(String phone, String registrationNo, String registerAccount, String uploadProject);

    String delTrilateralData(String id);

    TrilateralData selTrilateralDataBy(String id);

    void updateTrilateralData(TrilateralData updateTrilateralData, String photoPath);

    void saveImportData(List<DataDTO> dataDTOList);

    void saveTrilateralData(TrilateralData trilateralData);

    List<TrilateralData> getTrilateralData(String companyName);

    PageForm getTrilateral(Integer pageNumber, Integer pageSize, String uploadProject, String trusteeship);


    PageForm searchTrilateralByName(Integer pageNumber, Integer pageSize, String name);

    String allocationImportContacts(List<StaffSend> staffSendList, String sfpt);

    PageForm getTrilateralTotals(Integer pageNumber, Integer pageSize, String uploadProject, String trusteeship, String staffName, String phone);

    PageForm getTrilateralAudit(Integer pageNumber, Integer pageSize, String uploadProject, String trusteeship, String staffName, String phone);

    String submitAuditOpinion(String auditOpinion, String auditStatus, String id);

    List<BaseBean> auditResult(String id);
}
