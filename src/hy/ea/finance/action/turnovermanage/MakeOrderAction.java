package hy.ea.finance.action.turnovermanage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hy.ea.bo.finance.BenDis.DtOrderBillAdd;
import hy.ea.finance.action.response.OrderDetailResponse;
import hy.ea.finance.service.turnovermanage.MakeOrderService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Action;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 成交订单
 */
@Controller
@Scope("prototype")
public class MakeOrderAction {

    @Resource
    private MakeOrderService makeOrderService;
    private String result;
    private String detail;

    /**
     * 成交订单 - 列表展示
     * @return
     */
    public String getOrderList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String fkStatus = request.getParameter("fkStatus");
        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        String companyid = request.getParameter("companyid");
        PageForm pageForm = makeOrderService.getList(pageNum, pageSize, fkStatus, companyid);
        Map<String, Object> map = new HashMap<>();
        map.put("pageForm", pageForm);
        JSONObject json = JSONObject.fromObject(map);
        result = json.toString();
        return "success";
    }

    /**
     * 成交订单 - 订单详情
     * @return
     */
    public String getOrderDetail(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String cashierBillsID = request.getParameter("cashierBillsID");
        OrderDetailResponse orderDetail = makeOrderService.getOrderDetail(cashierBillsID);
        Map<String, Object> map = new HashMap<>();
        map.put("orderDetail", orderDetail);
        JSONObject json = JSONObject.fromObject(map);
        detail = json.toString();
        return "success";
    }

    public MakeOrderService getMakeOrderService() {
        return makeOrderService;
    }

    public void setMakeOrderService(MakeOrderService makeOrderService) {
        this.makeOrderService = makeOrderService;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
