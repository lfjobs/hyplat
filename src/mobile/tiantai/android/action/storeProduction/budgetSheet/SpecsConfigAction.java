package mobile.tiantai.android.action.storeProduction.budgetSheet;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.finance.PerformanceManagement;
import hy.ea.bo.finance.vo.SpecsConfigAddDTO;
import hy.ea.bo.finance.vo.SpecsConfigListVO;
import hy.ea.bo.human.Staff;
import hy.ea.util.StringUtil;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;
import mobile.tiantai.android.service.storeProduction.budgetSheet.SpecsConfigService;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
public class SpecsConfigAction {
    @Resource
    private SpecsConfigService specsConfigService;
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;

    private List<SpecsConfigListVO> specsList;

    private SpecsConfigAddDTO specsConfigAddDTO;

    private String delSpecsInfo;
    private String toUpdateSpecsInfo;


    private String specs;

    private String result;

    private PerformanceManagement pm;


    /**
     * 配置列表页面
     *
     * @return
     */
    public String specsConfigList() {
        try {
            specsList = specsConfigService.getSpecsList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "specsConfigList";
    }


    /**
     * 跳转到添加页面
     *
     * @return
     */
    public String specsConfigAdd() {
        try {
            specsList = specsConfigService.getSpecsList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "specsConfigAdd";
    }


    /**
     * 执行添加方法
     *
     * @return
     */
    public String addSpecsConfig() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String specs1 = specs;
            //新增配置信息==构造配置参数
            SpecsConfigAddDTO specsConfigAddDTO = this.specsConfigAddDTO;
            specsConfigService.addSpecsConfig(specsConfigAddDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.specsConfigList();
        return "specsConfigList";
    }

    /**
     * 跳转到修改页面
     *
     * @return
     */
    public String specsConfigUpdate() {
        try {
            String updateSpecsInfo = this.toUpdateSpecsInfo;
            String[] split = updateSpecsInfo.split(",");
            SpecsConfigAddDTO specsConfigAddDTO1 = new SpecsConfigAddDTO();
            specsConfigAddDTO1.setSpecsType(split[0]);
            specsConfigAddDTO1.setSpecsCode(split[1]);
            specsConfigAddDTO = specsConfigService.updateSpecsInfo(specsConfigAddDTO1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "specsConfigUpdate";
    }

    /**
     * 执行修改方法
     *
     * @return
     */
    public String updateSpecsConfig() {
        this.specsConfigList();
        return "specsConfigList";
    }

    /**
     * 执行删除方法
     *
     * @return
     */
    public String delSpecsConfig() {
        try {
            String delSpecsInfo = this.delSpecsInfo;
            String[] split = delSpecsInfo.split(",");
            SpecsConfigAddDTO specsConfigAddDTO = new SpecsConfigAddDTO();
            specsConfigAddDTO.setSpecsType(split[0]);
            specsConfigAddDTO.setSpecsCode(split[1]);
            specsConfigService.delSpecsConfig(specsConfigAddDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.specsConfigList();
        return "specsConfigList";
    }


    /**
     * 执行修改方法1
     *
     * @return
     */
    public String updateSpecsConfig1() {
        try {
            SpecsConfigAddDTO specsConfigAddDTO = this.specsConfigAddDTO;
            specsConfigService.updateSpecsConfig(specsConfigAddDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.specsConfigList();
        return "specsConfigList";
    }


    /*****************************************激励绩效考评管理**********************************************/
    /**
     * 激励绩效根节点数据查询
     */
    public String getPerformanceManagementRootList() {
        List<BaseBean> list = specsConfigService.getPerformanceManagementRootList();
        JSONArray arr = JSONArray.fromObject(list);
        result = arr.toString();
        return "success";
    }

    /**
     * 激励绩效根据父编号id查询子数据
     */
    public String getPerformanceManagementByParentId() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String id = request.getParameter("id");
        List<BaseBean> list = specsConfigService.getPerformanceManagementByParentId(id);
        JSONArray arr = JSONArray.fromObject(list);
        result = arr.toString();
        return "success";
    }

    /**
     * 生成激励绩效数据
     */
    public String createPerformanceManagement() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        SessionWrap sw = SessionWrap.getInstance();
        Map<String, Object> session = ActionContext.getContext().getSession();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);

        String parentId = request.getParameter("parentId");
        String name = request.getParameter("name");

        //校验同级目录下是否有相同数据
        if (specsConfigService.isExistName(name, parentId)) {
            result = "exist";
            return "success";
        }

        // 查询登录人信息
        Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",
                new Object[]{tc.getStaffid()});

        Date date = new Date();
        PerformanceManagement info = new PerformanceManagement();
        info.setId(serverService.getServerID("bType"));
        info.setName(name);
        info.setDelFlag("1");
        info.setCreateName(staff.getStaffName());
        info.setUpdateName(staff.getStaffName());
        info.setCreateTime(date);
        info.setUpdateTime(date);

        if (StringUtils.isNotEmpty(parentId)) {
            info.setParentId(parentId);
        }
        specsConfigService.createPerformanceManagement(info);
        result = "success";
        return "success";
    }

    /**
     * 修改激励绩效数据
     */
    public String updatePerformanceManagement() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        SessionWrap sw = SessionWrap.getInstance();
        Map<String, Object> session = ActionContext.getContext().getSession();
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);

        String key = request.getParameter("key");
        String parentId = request.getParameter("parentId");
        String name = request.getParameter("name");

        PerformanceManagement info = specsConfigService.getPerformanceManagementByKey(key);

        if (info == null) {
            result = "noExist";
            return "success";
        }

        //校验同级目录下是否有相同数据
        if (!info.getName().equals(name) && specsConfigService.isExistName(name, parentId)) {
            result = "exist";
            return "success";
        }

        // 查询登录人信息
        Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",
                new Object[]{tc.getStaffid()});

        Date date = new Date();
        info.setName(name);
        info.setUpdateName(staff.getStaffName());
        info.setUpdateTime(date);
        specsConfigService.updatePerformanceManagement(info);
        result = "success";
        return "success";
    }

    /**
     * 根据主键查询激励绩效
     *
     * @return
     */
    public String getPerformanceManagementByKey() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        String key = request.getParameter("key");
        pm = specsConfigService.getPerformanceManagementByKey(key);
        if(StringUtils.isNotEmpty(pm.getParentId())){
            PerformanceManagement info = specsConfigService.getPerformanceManagementById(pm.getParentId());
            pm.setParentName(info.getName());
        }
        return "pmEdit";
    }


    /**
     * 删除激励绩效
     *
     * @return
     */
    public String delPerformanceManagement() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        String key = request.getParameter("key");

        if (StringUtil.isEmpty(key)) {
            result = "noId";
            return "success";
        }
        PerformanceManagement info = specsConfigService.getPerformanceManagementByKey(key);
        info.setDelFlag("0");
        specsConfigService.updatePerformanceManagement(info);
        result = "success";
        return "success";
    }

    public List<SpecsConfigListVO> getSpecsList() {
        return specsList;
    }

    public void setSpecsList(List<SpecsConfigListVO> specsList) {
        this.specsList = specsList;
    }

    public SpecsConfigAddDTO getSpecsConfigAddDTO() {
        return specsConfigAddDTO;
    }

    public void setSpecsConfigAddDTO(SpecsConfigAddDTO specsConfigAddDTO) {
        this.specsConfigAddDTO = specsConfigAddDTO;
    }

    public String getDelSpecsInfo() {
        return delSpecsInfo;
    }

    public void setDelSpecsInfo(String delSpecsInfo) {
        this.delSpecsInfo = delSpecsInfo;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getToUpdateSpecsInfo() {
        return toUpdateSpecsInfo;
    }

    public void setToUpdateSpecsInfo(String toUpdateSpecsInfo) {
        this.toUpdateSpecsInfo = toUpdateSpecsInfo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public PerformanceManagement getPm() {
        return pm;
    }

    public void setPm(PerformanceManagement pm) {
        this.pm = pm;
    }
}
