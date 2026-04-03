package hy.ea.human.service;

import hy.ea.bo.human.Audition;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.DepartmentPost;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;

public interface PostService {

    /**
     * 查询岗位列表
     *
     * @param departmentPost
     * @param search
     * @param pageNumber
     * @param pageSize
     * @return
     */
    String getDeployList(DepartmentPost departmentPost, String search, Integer pageNumber, Integer pageSize);

    /**
     * 根据部门获取岗位数据
     * @param deptIds
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageForm getPostListByDeptIds(String deptIds,Integer pageNumber, Integer pageSize);
    /**
     * 查询需要入职的人员
     *
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageForm getNeedJoinStaffData(String companyId, Integer pageNumber, Integer pageSize);

    /**
     * 保存人员分配数据
     *
     * @return
     */
    String savePersonPostAllocation(COS cos, DepartmentPost departmentPost, Audition entity);

    /**
     * 根据id获取岗位数据
     * @param postId
     * @param companyId
     * @return
     */
    DepartmentPost getDepartmentPostById(String postId,String companyId);
    /**
     * 岗位职责——添加页面
     *
     * @return
     */
    String toSaveDepartmentPost();

    /**
     * 保存岗位职责
     *
     * @return
     */
    String saveDepartmentPost(DepartmentPost departmentPost, String orgName);

    /**
     * 查询指定岗位人员列表
     * @return
     */
    PageForm getListPerson(Integer pageNumber,Integer pageSize,DepartmentPost departmentPost,String search);

    /**
     * 根据id获取列表
     * @param ids
     * @param companyId
     * @return
     */
    List<BaseBean> getPostListByIds(String ids,String companyId);

    /**
     * 删除岗位
     * @param postId
     * @param companyId
     */
    void delPostDataById(String postId,String companyId);
}