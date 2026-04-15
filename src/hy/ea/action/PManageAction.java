package hy.ea.action;

import com.opensymphony.xwork2.ActionSupport;
import hy.ea.bo.CAccount;
import hy.ea.bo.portmanage.APIDocuments;
import hy.ea.bo.portmanage.PortParameter;
import hy.ea.bo.portmanage.PortParticulars;
import hy.ea.bo.portmanage.Project;
import hy.ea.service.PManageService;
import hy.ea.util.elkc.DateUtilElkc;
import hy.ea.util.savereadfile.JsonCache;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 创建人: lnn
 * 创建时间: 2018/5/15
 * 实现:接口管理
 */
@Controller
@Scope("prototype")
public class PManageAction extends ActionSupport {
    @Resource
    private PManageService pManageService;
    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanService baseBeanService;
    //ajax响应结果
    private String result;
    //项目ID
    private String projectId;
    //API文档id
    private String documentsId;
    //API文档名称
    private String documentsName;
    //接口名称
    private String portName;
    //项目
    private Project project;
    //模糊查询
    private String search;
    //API文档
    private APIDocuments apiDocuments;
    //接口ID
    private String portId;
    //接口参数ID
    private String parameterId;
    //是否查询所有api文档下的所有接口[allport=all]
    private String allport;
    //添加(修改)接口及参数[前端返回json字符串(要求属性用双引号)]
    private String portAndParameter;
    //分页实现
    private PageForm pageForm;

    private int pageNumber;
    //// 添加日志实例对象
    private static final Logger logger = LoggerFactory.getLogger(PManageAction.class);

    /**
     * 获取所有项目
     *
     * @return
     */
    public String selectProjects() {
        //获取后台用户登录信息
        HttpSession session = ServletActionContext.getRequest().getSession();
        CAccount cac = (CAccount) session.getAttribute("account");
        //非空校验
        if (cac == null) {
            //未登录
            return "failed";
        } else {
            StringBuilder hql = new StringBuilder("from Project ");
            //参数非空校验
            if (search != null && !search.equals("")) {
                hql.append("where projectName like '%" + search + "%'");
                pageForm = this.baseBeanService.getPageForm((pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), hql.toString(), null);
            } else {
                pageForm = this.baseBeanService.getPageForm((pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), hql.toString(), null);
            }
            return "portmanage_project";
        }

    }

    /**
     * 添加(创建)项目
     *
     * @return
     */
    public String addProject() throws ParseException {
        //获取后台用户登录信息
        HttpSession session = ServletActionContext.getRequest().getSession();
        CAccount cac = (CAccount) session.getAttribute("account");
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac == null) {
            //未登录
            map.put("code", "400");
            map.put("addproject", "未登录请您登陆！");
        } else {
            //获取后台登录用户ID(ackey)
            String ackey = cac.getAccountKey();
            logger.info("--用户ID--" + ackey);
            project.setProjectCreaterId(ackey);
            //获取后台登录用户名(acname)
            String acname = cac.getAccountName();
            project.setProjectCreaterName(acname);
            //调用日期工具类生成创建项目时间
            Date myDate = DateUtilElkc.getCurrentDateTime();
            project.setProjectCreatTime(myDate);
            //生成(获取)项目id
            String projectid = serverService.getServerID("projectid");
            project.setProjectId(projectid);
            //执行项目添加(创建)
            this.pManageService.insertProject(project);
            map.put("code", "200");
            map.put("addproject", "添加成功！");

        }
        JSONObject oj = JSONObject.fromObject(map);
        this.result = oj.toString();
        return "success";
    }

    /**
     * 删除项目(ByProjectId)
     *
     * @return
     */
    public String deleteProjectByProjectId() {
        Map<String, Object> map = new HashMap<String, Object>();
        //参数（documentsId）非空校验
        if (projectId != null && !projectId.equals("")) {
            String hql = "from APIDocuments where projectId = ?";
            List<BaseBean> APIDocumentsList = this.baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{projectId});
            //校验，判断集合是否为空
            if (APIDocumentsList.size() > 0) {
                map.put("boolean", false);
                //该项目下有文档不能删除
                map.put("code", "该项目下有文档您不能删除！");

            } else {
                //执行删除
                boolean deleteProject = this.pManageService.deleteProjectByProjectId(projectId);
                if (deleteProject == true) {
                    map.put("boolean", true);
                    map.put("code", "删除成功");

                } else {

                    map.put("boolean", false);
                    map.put("code", "删除失败");

                }

            }
        } else {
            map.put("boolean", false);
            //projectId为空无法删除
            map.put("code", "删除失败");
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 项目修改回显
     *
     * @return
     */
    public String selectProjectByProjectId() {
        Map<String, Object> map = new HashMap<String, Object>();
        if (projectId != null && !projectId.equals("")) {
            String hql = " from Project where projectId = ?";
            Project project = (Project) this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{projectId});
            map.put("project", project);
            map.put("code", "200");
        } else {
            //参数projectId为空无法获取项目数据>>返回400
            map.put("code", "400");
        }
        JSONObject oj = JSONObject.fromObject(map);
        this.result = oj.toString();
        return "success";
    }

    /**
     * 通过ID修改对应项目
     *
     * @return
     */
    public String updateProjectByprojectId() {
        //获取后台用户登录信息
        HttpSession session = ServletActionContext.getRequest().getSession();
        CAccount cac = (CAccount) session.getAttribute("account");
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac == null) {
            //未登录
            map.put("code", "400");
            map.put("updateProject", "未登录请您登陆");
        } else {
            //非空校验
            if (project != null) {
                //参数（projectId）非空校验
                if (project.getProjectId() != null && !project.getProjectId().equals("")) {
                    //获取后台登录用户ID(ackey)
                    String ackey = cac.getAccountKey();
                    //获取后台登录用户名(acname)
                    String acname = cac.getAccountName();
                    //生成修改项目时间
                    Date myDate = DateUtilElkc.getCurrentDateTime();
                    String hql = " from Project where projectId = ?";
                    Project project1 = (Project) this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{project.getProjectId()});
                    project1.setProjectName(project.getProjectName());
                    project1.setProjectUrl(project.getProjectUrl());
                    project1.setProjectDescription(project.getProjectDescription());
                    project1.setProjectModifierId(ackey);
                    project1.setProjectModifierName(acname);
                    project1.setProjectModificationTime(myDate);
                    //修改项目
                    this.baseBeanService.update(project1);
                    map.put("updateProject", "修改成功！");
                    map.put("code", "200");
                } else {
                    //projectId为空无法修改>>返回401
                    map.put("updateProject", "参数为空无法修改");
                    map.put("code", "401");
                }

            } else {
                //project为空无法修改>>返回402
                map.put("updateProject", "参数为空无法修改");
                map.put("code", "402");
            }
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 添加(创建)API文档
     *
     * @return
     */
    public String addAPIDocuments() {
        //获取后台用户登录信息
        HttpSession session = ServletActionContext.getRequest().getSession();
        CAccount cac = (CAccount) session.getAttribute("account");
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac == null) {
            //未登录
            String nologin = "未登录请您登陆";
            map.put("code", "400");
            map.put("addAPIDocuments", nologin);
        } else {
            //获取后台登录用户ID(ackey)
            String ackey = cac.getAccountKey();
            apiDocuments.setDocumentsCreaterId(ackey);
            //获取后台登录用户名(acname)
            String acname = cac.getAccountName();
            apiDocuments.setDocumentsCreaterName(acname);
            //执行API文档添加(创建)
            this.pManageService.insertAPIDocuments(apiDocuments);
            map.put("code", "200");
            map.put("addAPIDocuments", "添加成功！");
        }
        JSONObject oj = JSONObject.fromObject(map);
        this.result = oj.toString();
        return "success";
    }

    /**
     * 通过api文档名称,项目id获取该项目下的所有API文档(ByProjectId，bydocumentsName)
     *
     * @return
     */
    public String selectAPIDocuments() {
        pageForm = this.pManageService.selectAPIDocuments(projectId, search, (pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber));
        return "portmanage_documents";
    }

    /**
     * 通过ID删除对应API文档(ByDocumentsId)
     *
     * @return
     */
    public String deleteAPIDocumentsByDocumentsId() {
        Map<String, Object> map = new HashMap<String, Object>();
        //参数（documentsId）非空校验
        if (documentsId != null && !documentsId.equals("")) {
            String hql = "from PortParticulars where documentsId = ?";
            List<BaseBean> portParticularsList = this.baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{documentsId});
            if (portParticularsList.size() > 0) {
                map.put("boolean", false);
                //该api文档下有接口不能删除
                map.put("code", "该api文档下有接口您不能删除");

            } else {
                //删除API文档
                boolean deleteAPIDocuments = this.pManageService.deleteAPIDocumentsByDocumentsId(documentsId);
                if (deleteAPIDocuments == true) {
                    map.put("boolean", true);
                    map.put("code", "删除成功");

                } else {

                    map.put("boolean", false);
                    map.put("code", "删除失败");
                }

            }
        } else {
            map.put("boolean", false);
            //documentsId为空无法删除
            map.put("code", "删除失败");
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * APi文档修改回显
     *
     * @return
     */
    public String selectDocumentsBydocumentsId() {
        Map<String, Object> map = new HashMap<String, Object>();
        if (documentsId != null && !documentsId.equals("")) {
            String hql = " from APIDocuments where documentsId = ?";
            APIDocuments aDocuments = (APIDocuments) this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{documentsId});
            map.put("aDocuments", aDocuments);
            map.put("code", "200");
        } else {
            //参数documentsId为空无法获取文档数据>>返回400
            map.put("code", "400");
        }
        JSONObject oj = JSONObject.fromObject(map);
        this.result = oj.toString();
        return "success";
    }

    /**
     * 通过ID修改对应API文档(ByDocumentsId)
     *
     * @return
     */
    public String updateAPIDocumentsByDocumentsId() {
        //获取后台用户登录信息
        HttpSession session = ServletActionContext.getRequest().getSession();
        CAccount cac = (CAccount) session.getAttribute("account");
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac == null) {
            //未登录
            map.put("code", "400");
            map.put("updateAPIDocuments", "未登录请您登陆");
        } else {
            //非空校验
            if (apiDocuments != null) {
                //参数（documentsId）非空校验
                if (apiDocuments.getDocumentsId() != null && !apiDocuments.getDocumentsId().equals("")) {
                    //获取后台登录用户ID(ackey)
                    String ackey = cac.getAccountKey();
                    //获取后台登录用户名(acname)
                    String acname = cac.getAccountName();
                    //生成修改api文档时间
                    Date myDate = DateUtilElkc.getCurrentDateTime();
                    String hql = " from APIDocuments where documentsId = ?";
                    APIDocuments aDocuments = (APIDocuments) this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{apiDocuments.getDocumentsId()});
                    aDocuments.setDocumentsName(apiDocuments.getDocumentsName());
                    aDocuments.setDocumentsModifierId(ackey);
                    aDocuments.setDocumentsModifierName(acname);
                    aDocuments.setDocumentsModificationTime(myDate);
                    //修改api文档
                    try {
                        this.baseBeanService.update(aDocuments);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    map.put("updateAPIDocuments", "修改成功！");
                    map.put("code", "200");
                } else {
                    //documentsId为空无法修改>>返回401
                    map.put("updateAPIDocuments", "参数为空无法修改");
                    map.put("code", "401");
                }

            } else {
                //apiDocuments为空无法修改>>返回402
                map.put("updateAPIDocuments", "参数为空无法修改");
                map.put("code", "402");
            }
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 通过条件查询API文档下的接口(DocumentsId，PortName，allport)
     *
     * @return
     */
    public String selectPorts() {
        Map<String, Object> map = new HashMap<String, Object>();
        //当前页
        int pageNumber1 = pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1;
        //每页显示最大数【默认给10】
        int pageSize = pageNumber == 0 ? 10 : pageNumber;
        pageForm = this.pManageService.selectPorts(new Object[]{search, documentsId, allport, pageNumber1, pageSize});
        //参数校验
        if (pageForm != null && pageForm.getList() != null && pageForm.getList().size() > 0) {
            //已获取到数据
        } else {
            //查询结果为空
            ServletActionContext.getRequest().setAttribute("list", "没有获取到数据呢！");
        }
        return "portmanage_port";
    }

    /**
     * 去接口修改页面
     *
     * @return
     */
    public String toPortUpdate() {
        return "portUpdate";
    }

    /**
     * 接口详情及参数展示(ByportId)[数据回显]
     *
     * @return
     */
    public String selectPortParticularsParameters() {
        Map<String, Object> map = new HashMap<String, Object>();
        if (portId != null && !portId.equals("")) {
            List<BaseBean> PortParticularsParametersList = this.pManageService.selectPortParticularsParameters(portId);
            //调用保存和读取(json)文件工具类
            JsonCache jc = new JsonCache();
            //获取成功返回数据（json字符串）
            String success = jc.getDatafromFile(portId + "Success");
            //获取失败返回数据（json字符串）
            String fail = jc.getDatafromFile(portId + "Fail");
            map.put("successfilename", success);
            map.put("failfilename", fail);
            map.put("portParticularsParam", PortParticularsParametersList);
            map.put("code", "200");
        } else {
            //参数portId为空无法查询>>返回400
            map.put("code", "400");
        }
        JSONObject oj = JSONObject.fromObject(map);
        this.result = oj.toString();
        return "success";
    }

    /**
     * 通过ID删除该API文档下的接口(ByportId)
     *
     * @return
     */
    public String deletePortByPortId() {
        Map<String, Object> map = new HashMap<String, Object>();
        //参数（portId）非空校验
        if (portId != null && !portId.equals("")) {
            //删除接口
            boolean deleteport = this.pManageService.deletePortByPortId(portId);

            if (deleteport == true) {
                map.put("boolean", true);
                map.put("code", "删除成功");
            } else {
                map.put("boolean", false);
                map.put("code", "删除失败");
            }
        } else {
            //portId为空无法删除
            map.put("boolean", false);
            map.put("code", "参数为空，无法删除！");
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 通过ID删除该API文档下的接口参数(ByparameterId)
     * @return
     */
    public String deletePortParameterById() {
        Map<String, Object> map = new HashMap<String, Object>();
        //参数（parameterId）非空校验
        if (parameterId != null && !parameterId.equals("")) {
            //删除接口参数
            String hql = "delete from PortParameter where parameterId = ?";
            boolean bl = true;
            try {
                this.baseBeanService.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql}, new Object[]{parameterId});
            } catch (Exception e) {
                e.printStackTrace();
                bl=false;
            }

            if (bl == true) {
                map.put("boolean", true);
                map.put("code", "删除成功");
            } else {
                map.put("boolean", false);
                map.put("code", "删除失败");
            }
        } else {
            //parameterId为空无法删除
            map.put("boolean", false);
            map.put("code", "参数为空，无法删除！");
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 添加接口及批量添加接口参数
     * 【前端返回json字符串】
     *
     * @return
     */
    public String addPortAndParameters() {
        //获取后台用户登录信息
        HttpSession session = ServletActionContext.getRequest().getSession();
        CAccount cac = (CAccount) session.getAttribute("account");
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac == null) {
            //未登录
            String nologin = "未登录请您登陆";
            map.put("addPort", nologin);
            map.put("code", "400");
        } else {
            //获取后台登录用户ID(ackey)
            String ackey = cac.getAccountKey();
            //获取后台登录用户名(acname)
            String acname = cac.getAccountName();
            //生成创建接口时间
            Date myDate = DateUtilElkc.getCurrentDateTime();
            //生成(获取)接口id
            String portId = serverService.getServerID("portId");
            //将获取的json字符串(portAndParameter)转json对象
            JSONObject jsonObject = JSONObject.fromObject(portAndParameter);
            String portName = jsonObject.getString("portName");
            String portUrl = jsonObject.getString("portUrl");
            String portDescription = jsonObject.getString("portDescription");
            String portRequestType = jsonObject.getString("portRequestType");
            String dataFormat = jsonObject.getString("dataFormat");
            String successParameter = jsonObject.getString("successParameter");
            String failParameter = jsonObject.getString("failParameter");
            String documentsId = jsonObject.getString("documentsId");

            PortParticulars portParticulars = new PortParticulars();
            portParticulars.setPortId(portId);
            portParticulars.setPortName(portName);
            portParticulars.setPortUrl(portUrl);
            portParticulars.setPortDescription(portDescription);
            portParticulars.setPortRequestType(portRequestType);
            portParticulars.setDataFormat(dataFormat);
            //portParticulars.setSuccessParameter(successParameter);
            //portParticulars.setFailParameter(failParameter);
            portParticulars.setPortCreaterId(ackey);
            portParticulars.setPortCreaterName(acname);
            portParticulars.setPortCreatTime(myDate);
            portParticulars.setDocumentsId(documentsId);
            //调用保存和读取(json)文件工具类
            JsonCache jc = new JsonCache();
            //返回成功数据(json字符串)存入磁盘文件中
            jc.saveDataToFile(portId + "Success", successParameter);
            //返回失败数据(json字符串)存入磁盘文件中
            jc.saveDataToFile(portId + "Fail", failParameter);
            //添加接口
            this.baseBeanService.save(portParticulars);
            //接口中的参数parameters
            String parameters = jsonObject.getString("parameters");
            //将获取的json字符串(parameters)转json数组
            JSONArray jsonArray = JSONArray.fromObject(parameters);
            //非空校验
            if (jsonArray.size() > 0) {
                //循环遍历获取接口参数
                for (int i = 0, jsonay = jsonArray.size(); i < jsonay; i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    PortParameter portParameter = new PortParameter();
                    //生成(获取)接口参数id
                    String parameterid = serverService.getServerID("parameterid");
                    portParameter.setParameterId(parameterid);
                    if (jsonObject1.has("parameterName")) {
                        portParameter.setParameterName(jsonObject1.getString("parameterName"));
                    }
                    if (jsonObject1.has("parameterType")) {
                        portParameter.setParameterType(jsonObject1.getString("parameterType"));
                    }
                    if (jsonObject1.has("parameterExplain")) {
                        portParameter.setParameterExplain(jsonObject1.getString("parameterExplain"));
                    }
                    Integer reqlength = jsonObject1.getString("required").length();
                    if (reqlength > 1) {
                        portParameter.setRequired("1");
                    } else {
                        portParameter.setRequired(jsonObject1.getString("required"));
                    }
                    if (jsonObject1.has("example")) {
                        portParameter.setExample(jsonObject1.getString("example"));
                    }
                    portParameter.setPortId(portId);
                    //添加接口参数
                    this.baseBeanService.save(portParameter);
                }
                //添加成功
                map.put("addPort", "接口添加成功！");
                map.put("code", "200");
            } else {
                //添加失败
                map.put("addPort", "接口添加失败！");
                map.put("code", "401");
            }
        }
        JSONObject oj = JSONObject.fromObject(map);
        this.result = oj.toString();
        return "success";
    }

    /**
     * 修改接口及批量修改接口参数
     * 【前端返回json字符串】
     *
     * @return
     */
    public String updatePortAndParameters() {
        //获取后台用户登录信息
        HttpSession session = ServletActionContext.getRequest().getSession();
        CAccount cac = (CAccount) session.getAttribute("account");
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac == null) {
            //未登录
            String nologin = "未登录请您登陆";
            map.put("updatePort", nologin);
            map.put("code", "400");
        } else {
            //获取后台登录用户ID(ackey)
            String ackey = cac.getAccountKey();
            //获取后台登录用户名(acname)
            String acname = cac.getAccountName();
            //生成修改接口时间
            Date myDate = DateUtilElkc.getCurrentDateTime();
            //将获取的json字符串(portAndParameter)转json对象
            JSONObject jsonObject = JSONObject.fromObject(portAndParameter);
            String portId = jsonObject.getString("portId");
            String hql = " from PortParticulars where portId = ?";
            PortParticulars portParticulars = (PortParticulars) this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{portId});
            String portName = jsonObject.getString("portName");
            String portUrl = jsonObject.getString("portUrl");
            String portDescription = jsonObject.getString("portDescription");
            String portRequestType = jsonObject.getString("portRequestType");
            String dataFormat = jsonObject.getString("dataFormat");
            String successParameter = jsonObject.getString("successParameter");
            String failParameter = jsonObject.getString("failParameter");

            portParticulars.setPortName(portName);
            portParticulars.setPortUrl(portUrl);
            portParticulars.setPortDescription(portDescription);
            portParticulars.setPortRequestType(portRequestType);
            portParticulars.setDataFormat(dataFormat);
            //portParticulars.setSuccessParameter(successParameter);
            //portParticulars.setFailParameter(failParameter);
            portParticulars.setPortModifierId(ackey);
            portParticulars.setPortModifierName(acname);
            portParticulars.setPortModificationTime(myDate);
            //调用保存和读取(json)文件工具类
            JsonCache jc = new JsonCache();
            //修改成功返回数据(json字符串)到磁盘文件中
            jc.saveDataToFile(portId + "Success", successParameter);
            //修改失败返回数据(json字符串)到磁盘文件中
            jc.saveDataToFile(portId + "Fail", failParameter);
            //修改接口
            this.baseBeanService.update(portParticulars);
            //接口中的参数parameters
            String parameters = jsonObject.getString("parameters");
            //将获取的json字符串(parameters)转json数组
            JSONArray jsonArray = JSONArray.fromObject(parameters);
            if (jsonArray.size() > 0) {
                //循环遍历获取接口参数
                for (int i = 0, jsonay = jsonArray.size(); i < jsonay; i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    //参数校验
                    if (jsonObject1.has("parameterId")) {
                        //获取接口参数id
                        String parameterId = jsonObject1.getString("parameterId");
                        String hql1 = " from PortParameter where parameterId = ?";
                        //参数非空校验
                        if(parameterId != null && !parameterId.equals("")){
                            PortParameter portParameter = (PortParameter) this.baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{parameterId});
                            //参数校验
                            if (jsonObject1.has("parameterName")) {
                                portParameter.setParameterName(jsonObject1.getString("parameterName"));
                            }
                            if (jsonObject1.has("parameterType")) {
                                portParameter.setParameterType(jsonObject1.getString("parameterType"));
                            }
                            if (jsonObject1.has("parameterExplain")) {
                                portParameter.setParameterExplain(jsonObject1.getString("parameterExplain"));
                            }
                            Integer reqlength = jsonObject1.getString("required").length();
                            if (reqlength > 1) {
                                portParameter.setRequired("1");
                            } else {
                                portParameter.setRequired(jsonObject1.getString("required"));
                            }
                            if (jsonObject1.has("example")) {
                                portParameter.setExample(jsonObject1.getString("example"));
                            }
                            //修改接口参数
                            this.baseBeanService.update(portParameter);

                        }else{
                            PortParameter portParameter = new PortParameter();
                            //生成(获取)接口参数id
                            String parameterid = serverService.getServerID("parameterid");
                            portParameter.setParameterId(parameterid);
                            if (jsonObject1.has("parameterName")) {
                                portParameter.setParameterName(jsonObject1.getString("parameterName"));
                            }
                            if (jsonObject1.has("parameterType")) {
                                portParameter.setParameterType(jsonObject1.getString("parameterType"));
                            }
                            if (jsonObject1.has("parameterExplain")) {
                                portParameter.setParameterExplain(jsonObject1.getString("parameterExplain"));
                            }
                            Integer reqlength = jsonObject1.getString("required").length();
                            if (reqlength > 1) {
                                portParameter.setRequired("1");
                            } else {
                                portParameter.setRequired(jsonObject1.getString("required"));
                            }
                            if (jsonObject1.has("example")) {
                                portParameter.setExample(jsonObject1.getString("example"));
                            }
                            portParameter.setPortId(portId);
                            //添加接口参数
                            this.baseBeanService.save(portParameter);

                        }
                        //修改成功
                        map.put("updatePort", "接口修改成功！");
                        map.put("code", "200");
                    } else {
                        map.put("updatePort", "接口修改失败！");
                        map.put("code", "401");
                        break;

                    }

                }
            } else {
                map.put("updatePort", "接口修改失败！");
                map.put("code", "401");

            }
        }
        JSONObject oj = JSONObject.fromObject(map);
        this.result = oj.toString();
        return "success";
    }


    //////////////////////////////////////////Set Get///////////////////////////////////////////


    public PManageService getpManageService() {
        return pManageService;
    }

    public void setpManageService(PManageService pManageService) {
        this.pManageService = pManageService;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static Logger getLogger() {
        return logger;
    }

    public ServerService getServerService() {
        return serverService;
    }

    public void setServerService(ServerService serverService) {
        this.serverService = serverService;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public APIDocuments getApiDocuments() {
        return apiDocuments;
    }

    public void setApiDocuments(APIDocuments apiDocuments) {
        this.apiDocuments = apiDocuments;
    }

    public String getDocumentsId() {
        return documentsId;
    }

    public void setDocumentsId(String documentsId) {
        this.documentsId = documentsId;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    public BaseBeanService getBaseBeanService() {
        return baseBeanService;
    }

    public void setBaseBeanService(BaseBeanService baseBeanService) {
        this.baseBeanService = baseBeanService;
    }

    public String getDocumentsName() {
        return documentsName;
    }

    public void setDocumentsName(String documentsName) {
        this.documentsName = documentsName;
    }

    public String getAllport() {
        return allport;
    }

    public void setAllport(String allport) {
        this.allport = allport;
    }

    public String getPortAndParameter() {
        return portAndParameter;
    }

    public void setPortAndParameter(String portAndParameter) {
        this.portAndParameter = portAndParameter;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getParameterId() {
        return parameterId;
    }

    public void setParameterId(String parameterId) {
        this.parameterId = parameterId;
    }


    ////////////////////////////////Get Set//////////////////////////
}
