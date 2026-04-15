package hy.ea.company.action;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.PosDevice;
import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.DepotManage;
import hy.ea.company.service.DepotManageService;
import hy.ea.service.CCodeService;
import hy.ea.util.Constant;
import hy.ea.util.CookieUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepotManageMobileAction {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private CCodeService codeService;
    @Resource
    private ServerService serverService;
    @Resource
    private DepotManageService depotManageService;
    private PageForm pageForm;
    private String parameter;
    private int pageNumber;
    private String childrenID;
    private String depotID;
    private Object result;
    private DepotManage depotManage;
    private CAccount account;
    private TEshopCusCom tEshopCusCom;
    private String companyID;
    private String hgcode; //pos设备编号,秤盘编号

    /**
     * 获取库房类别
     *
     * @return
     */
    public String getTypelist() {
        Map<String, Object> map = new HashMap();
        companyID = getCompanyID();
        if (companyID == null) {
            map.put("nologin", 1);
        } else {
            List<CCode> typelist = codeService.getCCodeListByPID(companyID, "scode20101014v5zed7cukk0000000004");
            map.put("typelist", typelist);
        }
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 根据companyID和depotID查询其子节点
     */
    public String getListDepotmanageByPID() {
        Map<String, Object> map = new HashMap<String, Object>();
        companyID = getCompanyID();
        if (companyID == null) {
            map.put("nologin", 1);
        } else {
            String hql = " from DepotManage where  depotPID = ? and companyID = ? and depotState!=? order by depotNum ";
            Object[] params = {depotID, companyID, "01"};
            List<BaseBean> depotManagelist = baseBeanService.getListBeanByHqlAndParams(hql, params);
            map.put("depotManagelist", depotManagelist);
        }
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 根据名称查询数据条数（验证重名）
     *
     * companyId 公司id
     * name      仓库名称
     * @return
     */
    public String getCountByName() {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpServletRequest request = ServletActionContext.getRequest();
        String name = request.getParameter("name");
        companyID = getCompanyID();
        if (companyID == null) {
            map.put("nologin", 1);
        } else {
            map = depotManageService.getCountByName(companyID, name);
        }
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 查看
     */
    public String getListDepotmanageByID() {
        companyID = getCompanyID();
        if (companyID == null) {
            return "succ";
        }
        String hql = " from DepotManage where depotID = ? and companyID = ? and depotState!=?";
        Object[] params = {depotID, companyID, "01"};
        depotManage = (DepotManage) baseBeanService.getBeanByHqlAndParams(hql, params);
        return "view";
    }

    //ajax添加库房
    public String saveDepotByAjax() {
        String falg = "200";
        companyID = getCompanyID();
        try {
            if (companyID == null) {
                falg = "1";
            }
            if (null == depotManage.getDepotID() || "".equals(depotManage.getDepotID())) {
                depotManage.setDepotID(serverService.getServerID("depotManage"));
            }
            if (depotManage.getItemID().equals(Constant.DEPOT_SCALE) && (depotManage.getDepotCoding() == null || depotManage.getDepotCoding().equals(""))) {
                Map<String, Object> map = new HashMap<String, Object>();
                int count = depotManageService.getCountByItemId();

                // 使用正则表达式来进行补位，"0"代表需要补的位
                String paddedBinaryString = String.format("%0" + Constant.TARGETLENGTH + "d", count);
                depotManage.setDepotCoding(paddedBinaryString);
            }
            depotManage.setCompanyID(companyID);
            depotManage.setDepotState("00");
            baseBeanService.update(depotManage);
        } catch (Exception p) {
            falg = "2";
            p.printStackTrace();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("falg", falg);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    // 删除某条库房管理
    public String delDepotManage() {
        Map<String, Object> map = new HashMap<String, Object>();
        companyID = getCompanyID();
        if (companyID == null) {
            map.put("falg", "0");
        } else {
            Object[] params = {companyID, depotID};
            String hql = " from DepotManage where companyID = ? and depotPID = ? and depotState='00' order by depotNum ";
            List<BaseBean> depotManagelist = baseBeanService.getListBeanByHqlAndParams(hql, params);
            if (depotManagelist != null && depotManagelist.size() > 0) {
                map.put("falg", "1");
            } else {
                String hql1 = "update DepotManage set depotState='01' where companyID = ?  and depotID = ?";
                String hql2 = "from DepotManage where companyID=? and depotID=? and depotState='00'";
                DepotManage bank = (DepotManage) baseBeanService.getBeanByHqlAndParams(hql2, params);
                List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
                baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, new String[]{hql1}, params);
                map.put("falg", "2");
            }
        }
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     * 获取销售库数据
     *
     * @return
     */
    public String getXiaoshouku() {
        companyID = getCompanyID();
        Map<String, Object> map = new HashMap<String, Object>();
        String hql = " from DepotManage where depotName=? and companyID = ? and depotState!='01'";
        Object[] params = {"销售库", companyID};
        DepotManage depotManage = (DepotManage) baseBeanService.getBeanByHqlAndParams(hql, params);
        map.put("depotManage", depotManage);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 根据公司id查询成品库房
     *
     * companyId
     * @return
     */
    public String getListDepotToComid() {
        companyID = getCompanyID();
        Map<String, Object> map = new HashMap<String, Object>();
        if (companyID == null) {
            map.put("code", 3);
            map.put("meg", "登录信息错误");
            map.put("flag", 1);
        }
        map = depotManageService.getParenByDepotid(companyID);
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 库房列表
     * 终端调用
     * @return
     */
    public String getListDepotmanage() {
        JSONObject temp = new JSONObject();
        HttpServletRequest request = ServletActionContext.getRequest();
        companyID = getCompanyID();
        String posNum=request.getParameter("posNum");

        if (hgcode == null || hgcode.equals("")) {
            hgcode = CookieUtil.getCookieValue("hgcode", request);
            if (posNum !=null&&!posNum.equals("")){
                try {
                    PosDevice pos = (PosDevice) depotManageService.getPosDeviceByPosNum(posNum);
                    if(pos!=null){
                        hgcode=pos.getHgcode();
                    }
                }catch (Exception e){
                    temp.accumulate("msg", "2");
                    temp.accumulate("error", "没有找到秤盘信息");
                }
            }
        }
        if (companyID == null || companyID.equals("")) {
            companyID = CookieUtil.getCookieValue("comID", request);
        }
        DepotManage depotManage = (DepotManage) depotManageService.getDepotToCoding(hgcode, companyID, 1);
        if (depotManage != null) {
            temp.accumulate("depotManage", depotManage);
            List<BaseBean> doorList = depotManageService.getListDepotToPid(depotManage.getDepotID(), null, 2);
            if (doorList == null) {
                temp.accumulate("msg", "3");
                temp.accumulate("error", "没有找到柜门信息");
                temp.accumulate("flag", 1);
            }else {
                temp.accumulate("doorList",doorList);
                List<BaseBean> bl=new ArrayList<>();
                for (int i = 0; i < doorList.size(); i++) {
                    DepotManage door=(DepotManage) doorList.get(i);
                    bl.addAll(depotManageService.getListDepotToPid(door.getDepotID(), companyID, 3));
                }
                temp.accumulate("depotManagelist", bl);
                temp.accumulate("flag", 0);
            }
        } else {
            temp.accumulate("msg", "1");
            temp.accumulate("error", "没有找到此货柜信息");
        }
        result = temp;
        return "success";
    }


    /**
     * 根据设备号查询设备仓库信息
     */
    public String getListDepotmanageByHgcode() {
        companyID = getCompanyID();
        Map<String, Object> map = new HashMap<String, Object>();
        HttpServletRequest request = ServletActionContext.getRequest();
        if (hgcode == null || hgcode.equals("")) {
            hgcode = CookieUtil.getCookieValue("hgcode", request);
        }
        DepotManage d = (DepotManage) depotManageService.getDepotToCoding(hgcode, null, 1);
        if (d == null) {
            map.put("code", "01");
            map.put("msg", "没有找到货柜信息");
            map.put("flag", 1);
        } else {
            map.put("depotManage",d);
            List<BaseBean> doorList = depotManageService.getListDepotToPid(d.getDepotID(), null, 2);
            if (doorList == null) {
                map.put("code", "02");
                map.put("msg", "没有找到柜门信息");
                map.put("flag", 1);
            }else {
                map.put("doorList",doorList);
                List<BaseBean> depotList=new ArrayList<>();
                for (int i = 0; i < doorList.size(); i++) {
                    DepotManage door=(DepotManage) doorList.get(i);
                    depotList.addAll(depotManageService.getListDepotToPid(door.getDepotID(), null, 3));
                    map.put("depotList", depotList);
                }
                map.put("flag", 0);
            }
        }
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 根据posNum获取pos设备信息
     * @return
     */
    public String getPosDeviceByPosNum() {
        companyID = getCompanyID();
        Map<String, Object> map = new HashMap<String, Object>();
        HttpServletRequest request = ServletActionContext.getRequest();
        String posNum=request.getParameter("posNum");
        try {
            PosDevice pos = (PosDevice) depotManageService.getPosDeviceByPosNum(posNum);
            if (pos == null) {
                map.put("code", "01");
                map.put("msg", "没有找到货柜信息");
                map.put("flag", 1);
            } else {
                map.put("pos", pos);
                map.put("flag", 0);
            }
        }catch (Exception e){
            map.put("code", "02");
            map.put("msg", e.getMessage());
            map.put("flag", 1);
        }
        JSONObject oj = JSONObject.fromObject(map);
        result = oj.toString();
        return "success";
    }

    /**
     * 库房升级
     *
     * @return
     */
    public String dataUpgrade() {
        Map<String, Object> map = new HashMap<String, Object>();
        companyID = getCompanyID();
        if (companyID == null) {
            map.put("falg", "1");
        } else {
            map.put("falg", depotManageService.dataUpgrade(companyID));
        }
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }


    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getChildrenID() {
        return childrenID;
    }

    public void setChildrenID(String childrenID) {
        this.childrenID = childrenID;
    }

    public String getDepotID() {
        return depotID;
    }

    public void setDepotID(String depotID) {
        this.depotID = depotID;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public DepotManage getDepotManage() {
        return depotManage;
    }

    public void setDepotManage(DepotManage depotManage) {
        this.depotManage = depotManage;
    }

    public CAccount getAccount() {
        if (account == null) setAccount(null);
        return account;
    }

    public void setAccount(CAccount account) {
        if (account == null) {
            this.account = (CAccount) ActionContext.getContext().getSession().get("account");
        } else {
            this.account = account;
        }
    }

    public TEshopCusCom gettEshopCusCom() {
        if (tEshopCusCom == null) settEshopCusCom(null);
        return tEshopCusCom;
    }

    public void settEshopCusCom(TEshopCusCom tEshopCusCom) {
        if (tEshopCusCom == null) {
            this.tEshopCusCom = (TEshopCusCom) ActionContext.getContext().getSession().get("key_shop_cus_com");
        } else {
            this.tEshopCusCom = tEshopCusCom;
        }
    }

    public String getCompanyID() {
        if (companyID == null) setCompanyID(null);
        return companyID;
    }

    public void setCompanyID(String companyID) {
        if (companyID == null || companyID.equals("")) {
            tEshopCusCom = gettEshopCusCom();
            account = getAccount();
            if (account != null) {
                if (account.getCompanyID() != null && !account.getCompanyID().equals("")) {
                    this.companyID = account.getCompanyID();
                }
            } else if (tEshopCusCom != null) {
                if (tEshopCusCom.getCompanyId() != null && !tEshopCusCom.getCompanyId().equals("")) {
                    this.companyID = tEshopCusCom.getCompanyId();
                }
            }
        } else {
            this.companyID = companyID;
        }
    }

    public String getHgcode() {
        return hgcode;
    }

    public void setHgcode(String hgcode) {
        this.hgcode = hgcode;
    }
}
