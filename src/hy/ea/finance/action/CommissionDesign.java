package hy.ea.finance.action;

import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.finance.ProLayout;
import hy.ea.bo.finance.ProMain;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.finance.service.CommissionService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class CommissionDesign
{

  @Resource
  private BaseBeanService baseBeanService;

  @Resource
  private ServerService serverService;
  @Resource
  private CommissionService commissionService;
  private PageForm       pageForm;      
  private String         bsName;              //佣金设计方案名称
  private String         data;                //佣金比例数据
  private String         result;
  private int         pageNumber;

  /**
   * 查询佣金设计列表
   * @return
   */
  @SuppressWarnings("unchecked")
public String selCommissionList()
  {
	HttpServletRequest re=ServletActionContext.getRequest ();
    Map<String,Object> session = ActionContext.getContext().getSession();
    CAccount account = (CAccount)session.get("account");    
    Map<String,Object> map=commissionService.GetDesignHomePageList(account.getCompanyID());
    if(((List<Object[]>) map.get("data")).size()==0&&!"company201009046vxdyzy4wg0000000025".equals(account.getCompanyID())){
    	cloneAllCommissionData();
    }
    List<Object[]> dataList=(List<Object[]>) map.get("data");
    List<Object[]> levelList=(List<Object[]>) map.get("level");
    re.setAttribute("dataList", dataList);
    re.setAttribute("levelList", levelList);
    re.setAttribute("length",dataList.size()==0?0:dataList.get(0).length);
	return "selList";
  }
  
  
  /**
   * 佣金设计添加数据
   * @return
   */
  @SuppressWarnings("unchecked")
public String addCommissionData(){
	  HttpServletRequest re=ServletActionContext.getRequest();
	  Map<String,Object> session = ActionContext.getContext().getSession();
	  CAccount account = (CAccount)session.get("account");  
	  String bsId=re.getParameter("bsId");
	  String behavior=re.getParameter("behavior");
	  //从前台传过来的字符串进行分解成数组
	  String[] strs=data.split(",");
	  //获取会员序号的最大值
	  String s=(String) baseBeanService.getObjectBySqlAndParams("select max(me_number) from dt_pro_main where com_id=? and dc_status=? and status=?",
			  		new Object[]{account.getCompanyID(),"00","00"});
	  //获取方案的名称和序号
	  List<String[]> prola=baseBeanService.getListBeanBySqlAndParams("select yj_name,me_number,yj_id from dt_pro_main where com_id=? and status=? and dc_status=? group by yj_name,me_number,yj_id", 
			  		new Object[]{account.getCompanyID(),"00","01"});
	  List<BaseBean> baseList=new ArrayList<BaseBean>();
	  int srs=0;
	  for(int i=0;i<prola.size();i++){
		  Object[] obj=prola.get(i);
		  if(Integer.parseInt(obj[1].toString())>srs){
			  srs=Integer.parseInt(obj[1].toString());
		  }
	  }
	  String[] member1=new String[2];
	  String memb="";
	  //为Main表 添加对应相连的数据
	  if("true".equals(behavior)){
		  memb=serverService.getServerID("ProMain");
		  ProMain pm=new ProMain();
		  pm.setComId(account.getCompanyID());
		  pm.setDcStatus("01");
		  pm.setMeNumber(srs+1+""); 
		  pm.setStatus("00");
		  pm.setCodeId(bsId);
		  pm.setYjId(memb);
		  pm.setYjName(bsName);
		  baseList.add(pm);
	  }else{
		  for(int i=0;i<2;i++){
			  member1[i]=serverService.getServerID("ProMain");
			  ProMain pm=new ProMain();
			  pm.setComId(account.getCompanyID());
			  pm.setDcStatus("0"+i);
			  if(i==0){
				  pm.setMeNumber(Integer.parseInt(s)+1+""); 
			  }else{
				  pm.setMeNumber(srs+1+""); 
			  }
			  pm.setStatus("00");
			  pm.setCodeId(bsId);
			  pm.setYjId(member1[i]);
			  pm.setYjName(bsName);
			  baseList.add(pm);
		  }
	  }
	  
	  
	  
	  //添加一个方案，并写进各个会员
	  List<String[]> member=baseBeanService.getListBeanBySqlAndParams("select yj_name,me_number,yj_id from dt_pro_main where com_id=? and dc_status=? and status=? group by yj_name,me_number,yj_id order by me_number",
			  		new Object[]{account.getCompanyID(),"00","00"});
	
	  for(int i=0;i<member.size();i++){
		  if(!strs[i].equals("null")){
			  Object[] obj=member.get(i);
			  ProLayout pl=new ProLayout();
			  pl.setLoid(serverService.getServerID("ProLayout"));
			  pl.setComId(account.getCompanyID());
			  pl.setProMoney(strs[i]);
			  pl.setPmStatu("00");
			  pl.setStatu("00");
			  pl.setBsid( "true".equals(behavior)?memb:member1[1]);
			  pl.setMeid((String)obj[2]);
			  baseList.add(pl);
		  } 
	  }
	  //为新添加的方案写入自身会员
	  if(!"true".equals(behavior)){
		  if(!"null".equals(strs[member.size()])){
			  ProLayout pl=new ProLayout();
			  pl.setLoid(serverService.getServerID("ProLayout"));
			  pl.setComId(account.getCompanyID());
			  pl.setProMoney(strs[member.size()]);
			  pl.setPmStatu("00");
			  pl.setStatu("00");
			  pl.setMeid(member1[0]);
			  pl.setBsid(member1[1]);
			  baseList.add(pl);
		  }
	  }
	 
	baseBeanService.executeHqlsByParamsList(baseList,null,null);
	  return "success";
  }
  
  /**
   * 佣金设计修改数据
   */
  
public String modifyCommissionData(){
	HttpServletRequest re=ServletActionContext.getRequest();
	Map<String,Object> session = ActionContext.getContext().getSession();
	CAccount account = (CAccount)session.get("account");  
	String meid=re.getParameter("meId");
	String bsid=re.getParameter("bsId");
	String[] meids=meid.split(",");
	String[] strs=data.split(",");
	int iirs=0;
	String sql="from ProLayout where comId=? and bsid=?";
	List<BaseBean> listSize=baseBeanService.getListBeanByHqlAndParams(sql, new Object[]{account.getCompanyID(),bsid});
	iirs=listSize.size();
	
	String[] sqls=new String[iirs];
	List<Object[]> list=new ArrayList<Object[]>();
	List<BaseBean> arrayList=new ArrayList<BaseBean>();
	int iris=0;
	for(int i=0;i<strs.length;i++){
		String hql="from ProLayout where comId=? and bsid=? and meid=?";
		ProLayout yout=(ProLayout) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),bsid,meids[i]});
		if(yout==null){
			if(!strs[i].equals("null")&&!strs[i].equals("")){
				String hqlr="from ProMain where comId=? and yjId=? and dcStatus=? and status=?";
				ProMain pm=(ProMain) baseBeanService.getBeanByHqlAndParams(hqlr, new Object[]{account.getCompanyID(),meids[i],"00","00"});
				
				ProLayout pl=new ProLayout();
				pl.setLoid(serverService.getServerID("ProLayout"));
				pl.setBsid(((ProLayout)listSize.get(0)).getBsid());
				pl.setComId(account.getCompanyID());
				pl.setMeid(pm.getYjId());
				pl.setPmStatu("00");
				pl.setProMoney(strs[i]);
				pl.setStatu("00");
				arrayList.add(pl);
			}	
		}else{
			if(!strs[i].equals("null")&&!strs[i].equals("")){
				sqls[iris]="update dt_pro_layout set pro_money=? where com_id=? and bsid=? and meid=?";
				Object[] obj={strs[i].equals("null")||strs==null?" ":strs[i],account.getCompanyID(),bsid,meids[i]};
				list.add(obj);iris++;
			}else{
				sqls[iris]="delete dt_pro_layout where com_id=? and bsid=? and meid=?";
				Object[] obj={account.getCompanyID(),bsid,meids[i]};
				list.add(obj);iris++;
			}
		}
	}
	 
	baseBeanService.executeSqlsByParmsList(arrayList, sqls, list);
	
	return "success";
}


/**
 * 佣金设计删除数据
 */
public String deleteCommissionData(){
	HttpServletRequest re=ServletActionContext.getRequest();
	Map<String,Object> session = ActionContext.getContext().getSession();
	CAccount account = (CAccount)session.get("account");  
	String sta=re.getParameter("sta");
	String bsid=re.getParameter("bsid");
	String meid=re.getParameter("meid");
	//判断是删除单行，还是删除行+列，column为删除行+列
	if("column".equals(sta)){
		String json=re.getParameter("json");
		JSONArray array=JSON.parseArray(json);
		// 获取需要修改的数据，若没有，则不进行任何操作
		for(int i=0;i<(array==null?0:array.size());i++){
			JSONArray js=(JSONArray) array.get(i);
			String[] sqls=new String[js.size()-1];
			List<Object[]> list=new ArrayList<Object[]>();
			List<BaseBean> baseList=new ArrayList<BaseBean>();
			int irdds=0;
			for(int r=1;r<js.size();r++){
				String[] str=js.get(r).toString().split("-");
				String hql="from ProLayout where comId=? and bsid=? and meid=?";
				ProLayout layou=(ProLayout) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{account.getCompanyID(),js.get(0).toString(),str[1]});
				if(layou==null){
					ProMain main=(ProMain) baseBeanService.getBeanByHqlAndParams("from ProMain where comId=? and yj_id=? and dc_status=? and status=?", new Object[]{account.getCompanyID(),str[1],"00","00"});
					List<BaseBean> llist=baseBeanService.getListBeanByHqlAndParams("from ProLayout where comId=? and bsid=?",new Object[]{account.getCompanyID(),js.get(0).toString()});
					ProLayout pl=new ProLayout();
					pl.setLoid(serverService.getServerID("ProLayou"));
					pl.setBsid(((ProLayout)llist.get(0)).getBsid());
					pl.setComId(account.getCompanyID());
					pl.setMeid(main.getYjId());
					pl.setPmStatu("00");
					pl.setProMoney(str[0]);
					pl.setStatu("00");
					baseList.add(pl);
				}else{
					sqls[irdds]="update dt_pro_layout set pro_money=? where com_id=? and bsid=? and meid=?";
					Object[] obj={str[0],account.getCompanyID(),js.get(0).toString(),str[1]};
					list.add(obj);irdds++;
				}
			}
			baseBeanService.executeSqlsByParmsList(baseList, sqls, list);
		}
		//删除对应的行和列
		String[] sqls=new String[4];
		List<Object[]> list=new ArrayList<Object[]>();
		sqls[0]="delete dt_pro_layout where com_id=? and (bsid=? or meid=?)";
		list.add(new Object[]{account.getCompanyID(),bsid,bsid});
		sqls[1]="delete dt_pro_main where com_id=? and (yj_id=? or yj_id=?)";
		list.add(new Object[]{account.getCompanyID(),bsid,meid});
		//删除列时，若存在比该列序号大的数据，则其-1
		String sql=" select me_number from dt_pro_main where com_id=? and yj_id=? and dc_status=? and status=? group by me_number";
		String intr=(String) baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{account.getCompanyID(),meid,"00","00"}).get(0);
		sqls[2]="update dt_pro_layout set me_number=me_number-1 where com_id=? and me_number>?";
		list.add(new Object[]{account.getCompanyID(),intr});
		sqls[3]="update dt_pro_main set me_number=me_number-1 where com_id=? and dc_status=? and me_number>?";
		list.add(new Object[]{account.getCompanyID(),"00",intr});
		
		baseBeanService.executeSqlsByParmsList(null, sqls, list);
	}else{
		//删除方案行的数据
		String[] sqls=new String[2];
		List<Object[]> list=new ArrayList<Object[]>();
		sqls[0]="delete dt_pro_layout where com_id=? and bsid=?";
		list.add(new Object[]{account.getCompanyID(),bsid});
		sqls[1]="delete dt_pro_main where com_id=? and yj_id=? and dc_status=?";
		list.add(new Object[]{account.getCompanyID(),bsid,"01"});
		baseBeanService.executeSqlsByParmsList(null, sqls, list);
	}
	
	return "success";
}
/**
 * 获取佣金结构里的数据
 */
public String ajaxGetCodeList() {
	Map<String, Object> session = ActionContext.getContext().getSession();
	CAccount account = (CAccount) session.get("account");
	String hql = "from ProductPackaging p where type = ? and goodsName != ? and goodsName != ? order by model";
	int i = baseBeanService.getConutByByHqlAndParams(
			"select count(*) from ProductPackaging p where type = ?" +
			" and goodsName != ? and goodsName != ?",
			new Object[] { "会员类型级别","会员中心","粉丝名片" });
	pageForm = baseBeanService.getPageForm(
			pageForm.getPageNumber() == 0 ? 1 : pageForm.getPageNumber(),
			pageNumber == 0 ? 10 : pageNumber, hql,
			new Object[] { "会员类型级别","会员中心","粉丝名片" });
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("pageForm", pageForm);
	map.put("saech", i);
	JSONObject js = JSONObject.fromObject(map);
	result = js.toString();

	return "success";
}

/**
 * 
 * 克隆数据
 */

@SuppressWarnings("unchecked")
public String cloneAllCommissionData(){
	Map<String,Object> session = ActionContext.getContext().getSession();
	CAccount account = (CAccount)session.get("account");  
	HttpServletRequest re=ServletActionContext.getRequest();
	List<BaseBean> layoutList=new ArrayList<BaseBean>();
	List<BaseBean> baselist=new ArrayList<BaseBean>();
	String hql0="from ProLayout where comId=? and statu=?";
	layoutList=baseBeanService.getListBeanByHqlAndParams(hql0, new Object[]{"company201009046vxdyzy4wg0000000025","00"});
	for(int i=0;i<layoutList.size();i++){
		ProLayout pl=new ProLayout();
		pl.setBsid(((ProLayout)layoutList.get(i)).getBsid());
		pl.setComId(account.getCompanyID());
		pl.setLoid(serverService.getServerID("ProLayout"));
		pl.setMeid(((ProLayout)layoutList.get(i)).getMeid());
		pl.setPmStatu(((ProLayout)layoutList.get(i)).getPmStatu());
		pl.setProMoney(((ProLayout)layoutList.get(i)).getProMoney());
		pl.setStatu(((ProLayout)layoutList.get(i)).getStatu());
		baselist.add(pl);
	}
	baseBeanService.saveBeansListAndexecuteHqlsByParams(baselist,null, null);
	List<BaseBean> mainList=new ArrayList<BaseBean>();
	String hql="from ProMain where comId=?";
	mainList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{"company201009046vxdyzy4wg0000000025"});
	
	List<String> strList=new ArrayList<String>();
	List<Object[]> objList=new ArrayList<Object[]>();
	baselist=new ArrayList<BaseBean>();
	for(int i=0;i<mainList.size();i++){
		String str1=serverService.getServerID("ProMain");
		ProMain pm=new ProMain();
		pm.setCodeId(((ProMain)mainList.get(i)).getCodeId());
		pm.setComId(account.getCompanyID());
		pm.setDcStatus(((ProMain)mainList.get(i)).getDcStatus());
		pm.setMeNumber(((ProMain)mainList.get(i)).getMeNumber());
		pm.setStatus(((ProMain)mainList.get(i)).getStatus());
		pm.setYjName(((ProMain)mainList.get(i)).getYjName());
		pm.setYjId(str1);
		if("00".equals(((ProMain)mainList.get(i)).getDcStatus())){
			strList.add("update ProLayout set meid=? where meid=? and comId=?");
			objList.add(new Object[]{str1,((ProMain)mainList.get(i)).getYjId(),account.getCompanyID()});
		}else{
			strList.add("update ProLayout set bsid=? where bsid=? and comId=?");
			objList.add(new Object[]{str1,((ProMain)mainList.get(i)).getYjId(),account.getCompanyID()});
		}
		baselist.add(pm);
	}
	baseBeanService.executeHqlsByParamsList(baselist,Arrays.asList(strList.toArray()).toArray(new String[0]),objList);
	
		return selCommissionList();
}

/**
 * ajax判断登录人员的账号是否是'sa'
 */
public String authority(){
	CAccount account=(CAccount) ActionContext.getContext().getSession().get("account");
	String authority;
	if(!"sa".equals(account.getAccountEmail())){
		authority="not";		
	}else{
		authority="yes";
	}
	Map<String,Object> map=new HashMap<String, Object>();
	map.put("authority",authority);
	JSONObject obj=JSONObject.fromObject(map);
	result=obj.toString();
	return "success";
}
public PageForm getPageForm() {
	return pageForm;
}
public void setPageForm(PageForm pageForm) {
	this.pageForm = pageForm;
}
public String getBsName() {
	return bsName;
}
public void setBsName(String bsName) {
	this.bsName = bsName;
}
public String getData() {
	return data;
}
public void setData(String data) {
	this.data = data;
}
public String getResult() {
	return result;
}
public void setResult(String result) {
	this.result = result;
}
public int getPageNumber() {
	return pageNumber;
}
public void setPageNumber(int pageNumber) {
	this.pageNumber = pageNumber;
}


}