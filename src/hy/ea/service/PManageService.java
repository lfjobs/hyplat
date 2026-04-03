package hy.ea.service;

import hy.ea.bo.portmanage.APIDocuments;
import hy.ea.bo.portmanage.Project;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;

/**
 * Created by Administrator on 2018/5/17 0017.
 */
public interface PManageService {
    List<BaseBean> selectGetProject(String hql, String acid);

    void insertProject(Project project);

    PageForm selectAPIDocuments(String projectId, String documName, int pageNumber, int pageSize);

    void insertAPIDocuments(APIDocuments apiDocuments);

    PageForm selectPorts(Object[] objects);

    boolean deletePortByPortId(String portId);

    List<BaseBean> selectPortParticularsParameters(String portId);

    boolean deleteAPIDocumentsByDocumentsId(String documentsId);

    boolean deleteProjectByProjectId(String projectId);


}
