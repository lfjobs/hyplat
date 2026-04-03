package mobile.tiantai.android.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import mobile.tiantai.android.bo.DtWechatMenu;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;

import com.opensymphony.xwork2.ActionContext;
@Scope("prototype")
public class WechatMenuAction {
	
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private String menuPid; //区别二级菜单
	private PageForm pageForm;
	private String companyId;
	private int pageNumber;
	private DtWechatMenu wechatMenu;
	private List<BaseBean> beans;
	private List<BaseBean> listmenu;
	private String menuId;
	private String parameter;
	private String ACTIVITY_ROOT = "upload_files/wechatmenu/";
	private String SUFFIX = ".txt";
	private char SPT= '/';
	private String ENCODING="GBK";
	/**
	 * 查询
	 * @return
	 */
	public String getMenuList(){	
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		
		String sql=" select w.menuid, w.menuname,w.createdate,w.content,c.companyName,w.image,me.menuname as aa,w.topmenu" +
				" from dtwechatmenu w " +
				" left join dtwechatmenu me on  w.menupid=me.menuid " +
				" left join dtcompany c on  w.companyId=c.companyId" +
				" where c.companyid=? order by w.createdate desc";
		pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber==0?15:pageNumber), sql, "select count(1) "
				+ sql.substring(sql.indexOf("from")), new Object[]{account.getCompanyID()});	
		String sql1="from DtWechatMenu where companyId=?";	
		listmenu=baseBeanService.getListBeanByHqlAndParams(sql1, new Object[]{account.getCompanyID()});
		
		return "getMenuList";

	}
	/**
	 * 添加  修改
	 * @return
	 */
	public String addWechatMenu(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String id=serverService.getServerID("wechatmenu");
		//内容存为txt文件
		HttpServletRequest request = ServletActionContext.getRequest();
		String url= request.getSession().getServletContext().getRealPath("/");
		String contenttxt=wechatMenu.getContent();
		if(contenttxt==null){
			contenttxt="";
		}
		String realPath="";
		realPath = getRealPath(url, id);
		File f = new File(realPath);
		try {
			FileUtils.writeStringToFile(f, contenttxt,ENCODING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
		String path="";
		String hidIdList = request.getParameter("hidIdList");
		
		if(wechatMenu.getMenuId()==null || "".equals(wechatMenu.getMenuId())){
			String pid=wechatMenu.getMenuPid();
			path=sb.append(id).append(SUFFIX).toString();
			wechatMenu.setCompanyId(account.getCompanyID());
			wechatMenu.setMenuId(id);
			wechatMenu.setMenuPid(pid);
			wechatMenu.setCreateDate(new Date());
			wechatMenu.setStaffId(account.getStaffID());
			wechatMenu.setContent(path);
			
			if (hidIdList!=null) {
				String[] imageArray = hidIdList.split(",");
				wechatMenu.setImage(imageArray[0]);				
			}
			beans = new ArrayList<BaseBean>();
			beans.add(wechatMenu);
			baseBeanService.executeHqlsByParamsList(beans, null, null);
		}else{
			
			 String hql2="from DtWechatMenu where companyId=?  and menuId=?";
			 DtWechatMenu aff=(DtWechatMenu) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{ account.getCompanyID(),wechatMenu.getMenuId() });
			 parameter = "修改菜单名称(菜单名称:"+aff.getMenuName()+")";
			 if (hidIdList!="" && !"".equals(hidIdList)) {
					String[] imageArray = hidIdList.split(",");
					String[] hql ={"update DtWechatMenu set menuName=?,menuPid=?,content=?,image=?,topmenu=? where menuId=?"};
					path=sb.append(id).append(SUFFIX).toString();
					baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, new Object[]{wechatMenu.getMenuName(),wechatMenu.getMenuPid(),path,imageArray[0],wechatMenu.getTopmenu(),wechatMenu.getMenuId()});
				}else{
					 String[] hql ={"update DtWechatMenu set menuName=?,menuPid=?,content=?,topmenu=? where menuId=?"};
					 path=sb.append(id).append(SUFFIX).toString();
					 baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql, new Object[]{wechatMenu.getMenuName(),wechatMenu.getMenuPid(),path,wechatMenu.getTopmenu(),wechatMenu.getMenuId()});
				
				}
			}		
		return "success";
	}
	@SuppressWarnings("unchecked")
	public String getMenuById(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		
		if("front".equals(parameter)){
			String sql=" select  w.menuname,w.createdate,w.content,c.companyName from dtwechatmenu w ,dtcompany c " +			
			" where w.menuId=? and  w.companyId=c.companyId";
			listmenu=baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{menuId});
			return "menuContent";
		}else{
			String hqlmenu="from DtWechatMenu  where menuId=?";
			wechatMenu=(DtWechatMenu) baseBeanService.getBeanByHqlAndParams(hqlmenu, new Object[]{menuId});
			
			String sql1="from DtWechatMenu where companyId=?";	
			listmenu=baseBeanService.getListBeanByHqlAndParams(sql1, new Object[]{account.getCompanyID()});
			return "menuEdit";
		}
	}

	
	private String getRealPath(String root,String id){
		StringBuilder sb = new StringBuilder(root);
		sb.append(relPath(id));
		return sb.toString().replace(SPT, File.separatorChar);
	}
	public String relPath(String id){
		StringBuilder sb = new StringBuilder(ACTIVITY_ROOT);
		sb.append(id).append(SUFFIX);
		return sb.toString();
	}
	/**
	 * 删除
	 * @return
	 */
	public String delMenu(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params={menuId,account.getCompanyID()};
		String hql1=" from DtWechatMenu where menuId=? and  companyId=? ";
		DtWechatMenu aff=(DtWechatMenu) baseBeanService.getBeanByHqlAndParams(hql1, params);
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "删除菜单(菜单名称:"+aff.getMenuName()+")", account);
		beans.add(logBook);
		String[] hql2={"delete  from DtWechatMenu where menuId=? and companyId=? "};
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hql2, params);
		return getMenuList();
	}
	/**
	 * 前台微信二级菜单
	 * @return
	 */
	public String wechatMenuShow(){
		String hql1="from DtWechatMenu where menuPid=? and companyId=? and topmenu='01' order by createDate desc";
		beans=baseBeanService.getListBeanByHqlAndParams(hql1, new Object[]{menuId,companyId});
		String hql="from DtWechatMenu where menuPid=? and companyId=? and topmenu='02'";
		listmenu=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{menuId,companyId});
		return "wechatMenuShow";
	}
	public String getMenuPid() {
		return menuPid;
	}
	public void setMenuPid(String menuPid) {
		this.menuPid = menuPid;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public DtWechatMenu getWechatMenu() {
		return wechatMenu;
	}
	public void setWechatMenu(DtWechatMenu wechatMenu) {
		this.wechatMenu = wechatMenu;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public List<BaseBean> getListmenu() {
		return listmenu;
	}
	public List<BaseBean> getBeans() {
		return beans;
	}
	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}
	public void setListmenu(List<BaseBean> listmenu) {
		this.listmenu = listmenu;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
}
