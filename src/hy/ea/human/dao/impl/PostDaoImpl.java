package hy.ea.human.dao.impl;

import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.DepartmentPost;
import hy.ea.human.dao.PostDao;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PostDaoImpl implements PostDao {
    @Resource
    private BaseBeanService baseBeanService;

    @Override
    public DepartmentPost getDepartmentPostById(String postId, String companyId) {
        String hql = "from DepartmentPost where companyID=? and depPostID=?";
        return (DepartmentPost) baseBeanService.getBeanByHqlAndParams(hql,new Object[] { companyId,postId});
    }

    @Override
    public PageForm getDeployList(DepartmentPost departmentPost, String search, Integer pageNumber, Integer pageSize,String companyId) {
        String hql = "from DepartmentPost d where d.companyID = ? ";
        List<Object> parms = new ArrayList<Object>();
        parms.add(companyId);
        if(departmentPost.getOrganizationID().contains("company") == false){
            hql += " and d.organizationID = ?";
            parms.add(departmentPost.getOrganizationID());
        }
        if(search != null && !search.equals("")) {
            hql += " and ( d.postNum like ? or d.postName like ?)";
            parms.add("%" + search + "%");
            parms.add("%" + search + "%");
        }
        return baseBeanService.getPageForm(
                (null != pageNumber ? pageNumber : 1), (pageSize==0?20:pageSize), hql+" order by d.organizationID", parms.toArray());

    }
    @Override
    public Map<String,String> getOrganizationByCompanyId(String companyId) {
        Map<String,String> data = new HashMap<>();
        List<BaseBean> olist= baseBeanService.getListBeanByHqlAndParams("from COrganization o where o.companyID = ?  ",new Object[]{ companyId});
        for (int i = 0; i < olist.size(); i++) {
            data.put(((COrganization)olist.get(i)).getOrganizationID(), ((COrganization)olist.get(i)).getOrganizationName());
        }
        return data;
    }

    @Override
    public PageForm getNeedJoinStaffData(String companyId, Integer pageNumber, Integer pageSize) {
        String hql = "select s from Staff s,Audition a where a.staffID = s.staffID and" +
                " a.companyID = ? and a.status between ? and ? and a.registerDate is not null order by s.verifyTime desc";
        return baseBeanService.getPageForm((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 5 : pageSize),
                hql,"select count(s) "+hql.substring(hql.indexOf("from")),
                new String[]{companyId,"21","22"});
    }

    @Override
    public PageForm getPostListByDeptIds(String[] deptIdArr, Integer pageNumber, Integer pageSize,String companyId) {
        StringBuilder hql = new StringBuilder(128);
        hql.append("  SELECT post.DEPPOSTID,post.POSTNAME,dept.ORGANIZATIONID,dept.ORGANIZATIONNAME FROM ")
        .append(" dt_hr_deptpost post left join dtCOrganization dept on post.ORGANIZATIONID = dept.ORGANIZATIONID")
        .append(" and post.COMPANYID = dept.COMPANYID where post.companyID = ?  ");
        List<Object> parms = new ArrayList<Object>();
        parms.add(companyId);
        int length = deptIdArr.length;
        if (length > 0 ){
            hql.append("  and post.organizationID in (");
            for (int i = 0; i < length; i++){
                if (!"".equals(deptIdArr[i])){
                    hql.append("?");
                    parms.add(deptIdArr[i]);
                    if (i < length - 1){
                        hql.append(",");

                    }
                }

            }
            hql.append(" )");
        }

        hql.append(" order by post.organizationID");
        String hql2 = " SELECT COUNT(*) FROM (" + hql + ")";
        return baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1), (pageSize==0?20:pageSize), hql.toString(),hql2,
                parms.toArray());
    }
}
