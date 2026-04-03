package mobile.tiantai.android.action.storeProduction.budgetSheet;

import com.fasterxml.jackson.databind.ObjectMapper;
import hy.plat.bo.PageForm;
import mobile.tiantai.android.bo.CrmCustomerPO;
import mobile.tiantai.android.service.storeProduction.budgetSheet.CrmCustomerPoService;
import net.sf.json.JSONArray;
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
 * CrmCustomerPOAction
 *
 * @author zch
 */
@Controller
@Scope("prototype")
public class CrmCustomerPOAction extends HttpServlet {
    @Resource
    private CrmCustomerPoService crmCustomerService;
    private List<CrmCustomerPO> crmCustomerPOList;
    private CrmCustomerPO crmCustomerPO;
    private String id;
    private String result;
    private PageForm pageForm;
    private int pageNumber;

    private int pageSize;
    private String type;

    public String crmCustomerPOList() {
        crmCustomerPOList = crmCustomerService.selectCrmCustomerPOList();
        return "crmCustomer";
    }

    /**
     * 查询未注册粉丝
     *
     * @return
     */
    public String crmCustomerPOList1() {
        pageForm = crmCustomerService.selectCrmCustomerPOList1(pageNumber, pageSize,type);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    /**
     * 查询注册粉丝
     *
     * @return
     */
    public String crmCustomerPOList2() {
        pageForm = crmCustomerService.selectCrmCustomerPOList2(type, pageNumber, pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    /**
     * 删除未注册粉丝
     *
     * @return
     */
    public String deleteCrmCustomerPO() {
        result = crmCustomerService.deleteCrmCustomerPO(id);
        return "success";
    }


    /**
     * 跳转导入页面
     *
     * @return
     */
    public String importData() {
        return "visitorImport";
    }

    /**
     * 保存导入数据
     *
     * @return
     */
    public String saveImportDataPo() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String date = request.getParameter("data");
            if (date != null) {
                //json字符串转实体
                ObjectMapper objectMapper = new ObjectMapper();
                List<CrmCustomerVo> DataDTOList = objectMapper.readValue(date, objectMapper.getTypeFactory().constructCollectionType(List.class, CrmCustomerVo.class));
                result = crmCustomerService.saveImportData1(DataDTOList);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "success";
    }

    /**
     * 即将导入数据与原数据对比
     *
     * @return
     */

    public String contrastImportDataPo() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String date = request.getParameter("data");
            if (date != null) {
                List<CrmCustomerVo> crmCustomerVoNew = new ArrayList<>();
                //json字符串转实体
                ObjectMapper objectMapper = new ObjectMapper();
                List<CrmCustomerVo> DataDTOList = objectMapper.readValue(date, objectMapper.getTypeFactory().constructCollectionType(List.class, CrmCustomerVo.class));
                crmCustomerVoNew = crmCustomerService.contrastStaff(DataDTOList);
                result = JSONArray.fromObject(crmCustomerVoNew).toString();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "success";
    }

    /**
     * 即将导入数据与原数据对比
     *
     * @return
     */

    public String contrastImportDataPo1() {
        pageForm = crmCustomerService.selectreferenceList(pageNumber, pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }


    public String delCustomer() {
        crmCustomerService.deleteCustomer(id);
        return "crmCustomer";
    }

    /**
     * 修改页面
     *
     * @return
     */
    public String updCustomer() {
        crmCustomerPO = crmCustomerService.updCustomer(id);
        return "updateCrmCustomer";
    }

    /**
     * 修改方法
     *
     * @return
     */
    public String updateData() {
        CrmCustomerPO old = this.crmCustomerPO;
        result = crmCustomerService.updateData1(old);
        return "success";
    }

    /**
     * 新增页面
     *
     * @return
     */
    public String addCustomer() {
        return "addCrmCustomer";
    }

    /**
     * 新增方法
     *
     * @return
     */
    public String addData() {
        CrmCustomerPO addCustomerPO = this.crmCustomerPO;
        result = crmCustomerService.addData1(addCustomerPO);
        return "success";
    }

    public String queryCustomer() {
        crmCustomerPO = crmCustomerService.updCustomer(id);
        return "queryCrmCustomer";
    }

    /**
     * 注册粉丝查询详细
     *
     * @return
     */
    public String queryCustomer1() {
        crmCustomerPO = crmCustomerService.updCustomer(id);
        return "queryCrmCustomer1";
    }

//get set

    public List<CrmCustomerPO> getCrmCustomerPOList() {
        return crmCustomerPOList;
    }

    public void setCrmCustomerPOList(List<CrmCustomerPO> crmCustomerPOList) {
        this.crmCustomerPOList = crmCustomerPOList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CrmCustomerPO getCrmCustomerPO() {
        return crmCustomerPO;
    }

    public void setCrmCustomerPO(CrmCustomerPO crmCustomerPO) {
        this.crmCustomerPO = crmCustomerPO;
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

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
