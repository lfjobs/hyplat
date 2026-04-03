package hy.ea.marketing.action.supermaket;

import hy.ea.marketing.bo.DtPayOffline;
import hy.ea.service.CompanyService;
import hy.ea.service.UpLoadFileService;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

/**
 * 刷脸设备维护
 */
@Controller
@Scope("prototype")
public class PayOfflineAction {
    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private CompanyService companyService;
    @Resource
    private UpLoadFileService fileService;

    private String result;
    private File file;
    private String fileFileName;

    private String payOfflineKey;
    private String payOfflineId;
    private String staffId;// Staffid
    private String comID;// companyid
    private String paymentType;// 线下支付方式
    private String params;//线下支付配置参数

//    CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
    private String parameter;

    public String setOfflinePayment() {
        DtPayOffline payOffline = (DtPayOffline) baseBeanService.getBeanByHqlAndParams("from DtPayOffline po where po.payOfflineId = ?", new Object[]{payOfflineId});

        payOffline.setParams(params);
        baseBeanService.update(payOffline);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", true);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    public String getOfflinePaymentList() {
        List list = baseBeanService.getListBeanByHqlAndParams("from DtPayOffline op where op.staffId = ? and op.companyId = ?", new Object[]{staffId, comID});

        if (list.isEmpty()) {
            initPayOfflineList(staffId, comID);
            list = baseBeanService.getListBeanByHqlAndParams("from DtPayOffline op where op.staffId = ? and op.companyId = ?", new Object[]{staffId, comID});
        }

        Map<String, Object> map = new HashMap<>();
        map.put("paymentList", list);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 初始化线下支付方式
     *
     * @param staffId   创建人员ID
     * @param companyId 公司ID
     */
    public void initPayOfflineList(String staffId, String companyId) {
        String[] types = new String[]{"offline_bank:银行收款", "offline_wxpay:微信二维码", "offline_alipay:支付宝二维码", "offline_universal:通用收款码"};
        for (String type : types) {
            String[] payment = type.split(":");
            DtPayOffline payOffline = new DtPayOffline(staffId, companyId);
            payOffline.setPayOfflineId(serverService.getServerID("payoffline"));
            payOffline.setStaffId(staffId);
            payOffline.setCompanyId(companyId);
            payOffline.setPaymentType(payment[0]);
            payOffline.setPaymentName(payment[1]);
            payOffline.setParams("");
            baseBeanService.save(payOffline);
        }
    }

    public ServerService getServerService() {
        return serverService;
    }

    public void setServerService(ServerService serverService) {
        this.serverService = serverService;
    }

    public BaseBeanService getBaseBeanService() {
        return baseBeanService;
    }

    public void setBaseBeanService(BaseBeanService baseBeanService) {
        this.baseBeanService = baseBeanService;
    }

    public CompanyService getCompanyService() {
        return companyService;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public UpLoadFileService getFileService() {
        return fileService;
    }

    public void setFileService(UpLoadFileService fileService) {
        this.fileService = fileService;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getPayOfflineKey() {
        return payOfflineKey;
    }

    public void setPayOfflineKey(String payOfflineKey) {
        this.payOfflineKey = payOfflineKey;
    }

    public String getPayOfflineId() {
        return payOfflineId;
    }

    public void setPayOfflineId(String payOfflineId) {
        this.payOfflineId = payOfflineId;
    }


    public String getComID() {
        return comID;
    }

    public void setComID(String comID) {
        this.comID = comID;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}