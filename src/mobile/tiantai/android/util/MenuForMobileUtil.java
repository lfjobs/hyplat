package mobile.tiantai.android.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import mobile.tiantai.android.service.storeProduction.budgetSheet.PayBudgetService;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.*;

/**
 * 获取当前登录人菜单权限
 * Created by Administrator on 2019-10-18.
 */
@Controller
@Scope("prototype")
public class MenuForMobileUtil {
	private static final Logger logger = LoggerFactory.getLogger(MenuForMobileUtil.class);
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private PayBudgetService payBudgetService;//预支付预算单service

    private String result;//异步返回结果
    private String organizationID;

    /**
     * 根据登录帐号查询展示组织机构名称
     *
     * @return
     */
    public String findOrgByAcc() {
        try {
            Map<String, Object> parmaInfor = MapSesssionUtil.getSessionForMap("paramMap");
            if (parmaInfor != null) {//未存入session，则将数据存入session
                result = payBudgetService.findOrgByAcc(organizationID, parmaInfor.get("companyId").toString(), parmaInfor.get("staffId").toString());
            }
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return Action.SUCCESS;
    }


    /**
     * GET AND SET METHOD
     */
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }
}
