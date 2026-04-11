package hy.ea.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.portmanage.APIDocuments;
import hy.ea.bo.portmanage.Project;
import hy.ea.service.PManageService;
import hy.ea.util.elkc.DateUtilElkc;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/5/17 0017.
 */
@Service
@Transactional
public class PManageServiceImpl implements PManageService {
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    ServerService serverService;
    @Resource
    private BaseBeanService baseBeanService;

    /**
     * 通过创建人id获取所有有关项目
     *
     * @param hql
     * @param acid
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BaseBean> selectGetProject(String hql, String acid) {

        return this.baseBeanDao.getListBeanByHqlAndParams(hql, new Object[]{acid});
    }

    /**
     * 添加（创建）项目
     *
     * @param project
     */
    @Override
    @Transactional
    public void insertProject(Project project) {
        this.baseBeanDao.save(project);
    }

    /**
     * 通过项目id,api文档名称获取所有有关api文档
     *
     * @param projectId
     * @param pageNumber
     * @param pageSize   @return
     */
    @Override
    public PageForm selectAPIDocuments(String projectId, String documName, int pageNumber, int pageSize) {
        StringBuilder hql = new StringBuilder("from APIDocuments where projectId = ?");
        if (documName != null && !documName.equals("")) {
            hql.append("and documentsName like '%" + documName + "%'");
            return this.baseBeanService.getPageForm(pageNumber, pageSize, hql.toString(), new Object[]{projectId});
        } else {
            return this.baseBeanService.getPageForm(pageNumber, pageSize, hql.toString(), new Object[]{projectId});
        }
    }

    /**
     * 添加（创建）API文档
     *
     * @param apiDocuments
     */

    @Override
    public void insertAPIDocuments(APIDocuments apiDocuments) {
        //生成创建项目时间
        Date myDate = DateUtilElkc.getCurrentDateTime();
        //生成(获取)api文档id
        String documentsid = serverService.getServerID("documentsid");
        apiDocuments.setDocumentsCreatTime(myDate);
        apiDocuments.setDocumentsId(documentsid);
        this.baseBeanDao.save(apiDocuments);
    }

    /**
     * 获取API文档下的所有有关接口
     *
     * @param objects
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm selectPorts(Object[] objects) {
        Object portname = objects[0];
        Object documentsId = objects[1];
        Object allport = objects[2];
        int pageNumber = (int) objects[3];
        int pageSize = (int) objects[4];
        StringBuilder hql = new StringBuilder("from PortParticulars");
        //参数校验[判断参数allport的值如果等于all则查询所有接口]
        if (allport != null && allport.equals("all")) {
            //参数portname非空校验
            if (portname != null && !portname.equals("")) {
                hql.append(" where portName like '%" + portname + "%'");
            }
            return this.baseBeanService.getPageForm(pageNumber, pageSize, hql.toString(), null);
        } else {
            //参数documentsId非空校验
            if (documentsId != null && !documentsId.equals("")) {
                //参数portname非空校验
                if (portname != null && !portname.equals("")) {
                    hql.append(" where  documentsId = ? and portName like '%" + portname + "%'");
                    return this.baseBeanService.getPageForm(pageNumber, pageSize, hql.toString(), new Object[]{documentsId});
                } else {
                    hql.append(" where  documentsId = ?");
                    return this.baseBeanService.getPageForm(pageNumber, pageSize, hql.toString(), new Object[]{documentsId});
                }

            } else {
                //参数portname非空校验
                if (portname != null && !portname.equals("")) {
                    hql.append(" where portName like '%" + portname + "%'");
                }
                return this.baseBeanService.getPageForm(pageNumber, pageSize, hql.toString(), null);

            }

        }

    }

    /**
     * 通过ID删除该API文档下的接口(ByportId)
     *
     * @return
     */

    @Override
    @Transactional
    public boolean deletePortByPortId(String portId) {
        boolean bl = true;
        try {
            String hql = "delete from PortParticulars where portId = ?";
            String hql1 = "delete from PortParameter where portId = ?";
            this.baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, new Object[]{portId});
            this.baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql1}, new Object[]{portId});
        } catch (Exception e) {
            logger.error("操作异常", e);
            bl = false;
        }
        return bl;
    }

    /**
     * 接口详情及参数展示(ByportId)
     *
     * @param portId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BaseBean> selectPortParticularsParameters(String portId) {
        String sql = "select pl.portkey,pl.portid,pl.portname,pl.porturl,pl.portdescription,pl.portrequesttype," +
                "pl.dataformat,pl.documentsid,pm.parameterkey,pm.parameterid,pm.parametername,pm.parametertype,pm.parameterexplain," +
                "pm.required,pm.example from port_particulars pl left join port_parameters pm on" +
                " pl.portid=pm.portid where pl.portid = ?";
        return this.baseBeanDao.getListBeanBySqlAndParams(sql, new Object[]{portId});
    }

    /**
     * 通过ID删除对应API文档(ByDocumentsId)
     *
     * @return
     */
    @Override
    @Transactional
    public boolean deleteAPIDocumentsByDocumentsId(String documentsId) {
        boolean bl = true;
        try {
            String hql = "delete from APIDocuments where documentsId = ?";
            this.baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, new Object[]{documentsId});
        } catch (Exception e) {
            logger.error("操作异常", e);
            bl = false;
        }
        return bl;

    }

    @Override
    public boolean deleteProjectByProjectId(String projectId) {
        boolean bl = true;
        try {
            String hql = "delete from Project where projectId = ?";
            this.baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, new Object[]{projectId});
        } catch (Exception e) {
            logger.error("操作异常", e);
            bl = false;
        }
        return bl;
    }
}