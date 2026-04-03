package mobile.tiantai.android.action.storeProduction.budgetSheet;

import com.fasterxml.jackson.databind.ObjectMapper;
import hy.plat.bo.PageForm;
import mobile.tiantai.android.bo.StaffSend;
import mobile.tiantai.android.service.storeProduction.budgetSheet.ImportContactsService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * ImportContactsAction
 *
 * @author zch
 */
@Controller
@Scope("prototype")
public class ImportContactsAction extends HttpServlet {
    @Resource
    private ImportContactsService importContactsService;
    private String result;
    private PageForm pageForm;
    private int pageNumber;

    private int pageSize;
    private String id;

    public String importContacts() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String date = request.getParameter("data");
            //json字符串转实体
            ObjectMapper objectMapper = new ObjectMapper();
            List<ImportContactsDto> importContactsDto = objectMapper.readValue(date, objectMapper.getTypeFactory().constructCollectionType(List.class, ImportContactsDto.class));
            result = importContactsService.importContacts(importContactsDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "success";
    }

    public String selectImportContactsList() {
        pageForm = importContactsService.selectImportContactsList(pageNumber, pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    public String selectImportContacts() {
        pageForm = importContactsService.selectImportContacts(pageNumber, pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    /**
     * 删除未注册粉丝
     *
     * @return
     */
    public String deleteImportContacts() {

        result = importContactsService.deleteImportContacts(id);
        return "success";
    }

    public String updateImportContacts() {
        HttpServletRequest request = ServletActionContext.getRequest();
        id = request.getParameter("id");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        result = importContactsService.updateImportContacts(id, name, phone);
        return "success";
    }

    public String allocationImportContacts() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String selectedData = request.getParameter("selectedData");
        String fp = request.getParameter("fp");
        ObjectMapper objectMapper = new ObjectMapper();
        List<StaffSend> StaffSendList = new ArrayList<StaffSend>();
        List<AllocationContacts> allocationContacts = new ArrayList<AllocationContacts>();
        try {
            StaffSendList = objectMapper.readValue(selectedData, objectMapper.getTypeFactory().constructCollectionType(List.class, StaffSend.class));
            allocationContacts = objectMapper.readValue(fp, objectMapper.getTypeFactory().constructCollectionType(List.class, AllocationContacts.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        result = importContactsService.allocationImportContacts(StaffSendList, allocationContacts);
        return "success";
    }

    //get set
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
