package hy.ea.finance.action.BenDis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class wfjzhAction {
	private static final Logger logger = LoggerFactory.getLogger(wfjzhAction.class);
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    private PageForm pageForm;

    public String getlist() {
        try {
            HttpServletRequest request= ServletActionContext.getRequest();
            String mz=request.getParameter("mz");
            String zh=request.getParameter("zh");
            String type=request.getParameter("type");
            String isdx = request.getParameter("isdx");
            request.setAttribute("mz",mz);
            request.setAttribute("zh",zh);
            request.setAttribute("type",type);
            request.setAttribute("isdx",isdx);
            List<Object> obj = new ArrayList<Object>();
            StringBuilder sql =new StringBuilder("select t.sccid,t.account,s.staffname,p.goodsname,t.state,s.staffid" );
            sql.append(" from t_eshop_cuscom t, dt_hr_staff s, dt_productpackaging p" );
            sql.append(" where t.staffid = s.staffid" );
            sql.append(" and t.custype = p.model" );
            sql.append(" and p.type  =?" );
            obj.add("会员类型级别");
            if(mz!=null&&!mz.equals(""))
            {
                sql.append(" and s.staffname  =?" );
                obj.add(mz);
            }
            if(zh!=null&&!zh.equals(""))
            {
                sql.append(" and t.account  =?" );
                obj.add(zh);
            }
            if(type!=null&&!type.equals("")){
                sql.append(" and t.custype  =?" );
                obj.add(type);
            }
            sql.append(" order by t.teccdate desc " );
            pageForm = baseBeanService.getPageFormBySQL(
                    (null != pageForm ? pageForm.getPageNumber() : 1), 10, sql.toString(), "select count(*) from (" + sql.toString() + ")", obj.toArray());
            StringBuilder p_sql=new StringBuilder("select pp.goodsname,pp.model from dt_productpackaging pp ");
            p_sql.append("  where pp.type =? and pp.goodsname<>? and pp.goodsname<>? and pp.goodsname<>? and pp.goodsname<>?  order by pp.model" );
            @SuppressWarnings("unchecked")
			List<BaseBean> p_list=baseBeanService.getListBeanBySqlAndParams(p_sql.toString(),new Object[]{"会员类型级别","会员中心","系统粉丝","粉丝名片","移动粉丝"});
            request.setAttribute("p_list",p_list);
        }catch (Exception e){
            logger.error("操作异常", e);
        }
        return "list";
    }
    
 // 根据companyId orgID 查询部门在职员工
 		public String getPersonByDept() {
 			Map<String, Object> session = ActionContext.getContext().getSession();
 			 HttpServletRequest request= ServletActionContext.getRequest();
 			CAccount account = (CAccount) session.get("account");
 			if (account == null) {

 				return "not_login";
 			}
 			String companyID = account.getCompanyID();
 			String orgID = request.getParameter("orgID");
 			String mz=request.getParameter("mz");
            String zh=request.getParameter("zh");
            String type=request.getParameter("type");
            request.setAttribute("mz",mz);
            request.setAttribute("zh",zh);
            request.setAttribute("type",type);
            request.setAttribute("orgID",orgID);
 			StringBuffer sql = new StringBuffer();
 			sql.append("select s.staffname,s.staffid,u.sccid,u.goodsname,u.account,u.state,u.custype");
 			sql.append(" from dt_hr_staff s left join");
 			sql.append(" (select e.sccid,p.goodsname,e.account,e.state,e.custype from dt_productpackaging p,t_Eshop_Cuscom e where p.model = e.custype and p.type = ?) u on s.sccid = u.sccid");
 			sql.append(" where exists (select staffID");
 			sql.append(" from dtcos c where c.staffID = s.staffID");
 			sql.append(" and companyID = ? and cosStatus = ?");
 			sql.append(" and (c.organizationID like ? or exists");
 			sql.append(" (select d.depPostID from dt_hr_deptpost d where d.depPostID = c.depPostID");
 			sql.append(" and d.leveloneOrgID like ?)))");
 			List<Object> obj = new ArrayList<Object>();
 			obj.add("会员类型级别");
 			obj.add(companyID);
 			obj.add("50");
 			obj.add("%"+orgID+"%");
 			obj.add("%"+orgID+"%");
 			if(mz!=null&&!mz.equals(""))
            {
                sql.append(" and s.staffname  =?" );
                obj.add(mz);
            }
            if(zh!=null&&!zh.equals(""))
            {
                sql.append(" and u.account  =?" );
                obj.add(zh);
            }
            if(type!=null&&!type.equals("")){
                sql.append(" and u.custype  =?" );
                obj.add(type);
            }
 			pageForm = baseBeanService.getPageFormBySQL(
 					(null != pageForm ? pageForm.getPageNumber() : 1),10,sql.toString(), 
					"select count(1)"+ sql.substring(sql.toString().indexOf("from")), obj.toArray());
 		    request.setAttribute("relation","在职员工");
 		    StringBuilder p_sql=new StringBuilder("select pp.goodsname,pp.model from dt_productpackaging pp ");
            p_sql.append("  where pp.type =? and pp.goodsname<>? and pp.goodsname<>? and pp.goodsname<>? and pp.goodsname<>?  order by pp.model" );
            @SuppressWarnings("unchecked")
			List<BaseBean> p_list=baseBeanService.getListBeanBySqlAndParams(p_sql.toString(),new Object[]{"会员类型级别","会员中心","系统粉丝","粉丝名片","移动粉丝"});
            request.setAttribute("p_list",p_list);
 		    if(pageForm!=null)
 			{
 				session.put("RecordCount", pageForm.getRecordCount());
 			}else{
 				session.put("RecordCount", 0);
 			}
 			return "listuser";

 		}

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }
}
