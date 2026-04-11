package hy.ea.company.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import hy.ea.bo.CAccount;
import hy.ea.bo.tao.Cate;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
public class BabyClassSelectionAction {
	private static final Logger logger = LoggerFactory.getLogger(BabyClassSelectionAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	private List<BaseBean> arrayList; 
	private String type;
	private String result;
	private String catePid;
	private String catename;
	private String tp;
	
	public String  getTypePageList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account=(CAccount)session.get("account");
		String sql="select type from tao_cate where parentid=? and companyId=? and status=?";
		if("ajax".equals(tp)){
			sql+=" and catename like ? group by type";
			Object[] obj={"0",account.getCompanyID(),"00","%"+catename+"%"};
			arrayList=baseBeanService.getListBeanBySqlAndParams(sql, obj);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("list",arrayList);
			JSONObject js=JSONObject.fromObject(map);
			result=js.toString();
			return Action.SUCCESS;
		}
		sql+=" group by type";
		Object[] obj={"0",account.getCompanyID(),"00"};
		arrayList=baseBeanService.getListBeanBySqlAndParams(sql, obj);
		return "startPageList";
	}
	
	public String sajaxGetSubCategoryList(){
		Map<String,Object> cateMap=getSubCategoryMap();
		String hql=(String) cateMap.get("hql");
		Object[] obj=(Object[]) cateMap.get("obj");
		List<BaseBean> list=new ArrayList<BaseBean>();
		list=baseBeanService.getListBeanByHqlAndParams(hql, obj);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("list", list);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}
	
	public String sajaxGetFuzzyQueryList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account=(CAccount)session.get("account");
		List<BaseBean> list=new ArrayList<BaseBean>();
		String hql="from Cate where parentId=? and companyId=? and status=? and catename like ?";
		Object[] obj={catePid,account.getCompanyID(),"00","%"+catename+"%"};
		list=baseBeanService.getListBeanByHqlAndParams(hql, obj);
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("list",list);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}
	
	public String  sajaxGetGlobalFuzzyQueryList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account=(CAccount)session.get("account");
		List<String> arr=new ArrayList<String>();
		try {
			String sql="select max(hierarchy) from tao_cate where companyid=? and status=?";
			String max=baseBeanService.getObjectBySqlAndParams(sql, new Object[]{account.getCompanyID(),"00"}).toString();
			for(int i=Integer.valueOf(max);i>1001;i--){
				List<BaseBean> list=new ArrayList<BaseBean>();
				String hql="from Cate where companyid=? and status=? and hierarchy=? and catename like ?";
				list=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),"00",i+"",("%"+catename+"%")});
				for(int r=0;r<list.size();r++){
					Cate c=(Cate)list.get(r);
					String level=c.getCatename()+"+"+c.getCateID();
					String s=c.getParentId();
					while(true){
						String hql2="from Cate where companyid=? and status=? and cateID=?";
						Cate ct=(Cate)baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),"00",s});
						if(ct!=null&&!"".equals(ct)){
							level=(ct.getCatename()+" >> "+level);
							s=ct.getParentId();
						}else{  break;   }
					}
					arr.add(level);
				}
			}
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list",arr);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return Action.SUCCESS;
	}

	public Map<String,Object> getSubCategoryMap(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account=(CAccount)session.get("account");
		List<String> list=new ArrayList<String>();
		String hql="from Cate where parentId=? and companyId=? and status=?";
		list.add(catePid==null||"".equals(catePid)?"0":catePid);
		list.add(account.getCompanyID());
		list.add("00");
		if(type!=null&&!"".equals(type)){
			hql+=" and type=?";
			list.add(type);
		}
		if("ajax".equals(tp)&&catename!=null){
			hql+=" and catename like ?";
			list.add("%"+catename+"%");
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("hql",hql);
		map.put("obj",list.toArray());
		return map;
	}
	

	public List<BaseBean> getArrayList() {
		return arrayList;
	}
	public void setArrayList(List<BaseBean> arrayList) {
		this.arrayList = arrayList;
	}

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCatePid() {
		return catePid;
	}

	public void setCatePid(String catePid) {
		this.catePid = catePid;
	}

	public String getCatename() {
		return catename;
	}

	public void setCatename(String catename) {
		this.catename = catename;
	}

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}
	
}
