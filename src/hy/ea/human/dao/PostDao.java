package hy.ea.human.dao;

import hy.ea.bo.human.DepartmentPost;
import hy.plat.bo.PageForm;

import java.util.Map;

public interface PostDao {
    /**
     * 查询岗位列表
     * @param departmentPost
     * @param search
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageForm getDeployList(DepartmentPost departmentPost,String search,Integer pageNumber,Integer pageSize,String companyId);

    /**
     * 查询部门数据
     * @param companyId
     * @return
     */
    Map<String,String> getOrganizationByCompanyId(String companyId);


    /**
     * 查询需要入职的人员
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageForm getNeedJoinStaffData(String companyId,Integer pageNumber,Integer pageSize);

    /**
     * 根据部门获取岗位数据
     * @param deptIdArr
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageForm getPostListByDeptIds(String[] deptIdArr, Integer pageNumber, Integer pageSize,String companyId);

    /**
     * 根据id获取岗位数据
     * @param postId
     * @param companyId
     * @return
     */
    DepartmentPost getDepartmentPostById(String postId,String companyId);
}

