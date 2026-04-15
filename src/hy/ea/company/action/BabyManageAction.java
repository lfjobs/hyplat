package hy.ea.company.action;





import hy.ea.bo.tao.AttrSize;
import hy.ea.bo.tao.AttriBaby;
import hy.ea.bo.tao.AttriCate;
import hy.ea.bo.tao.Baby;
import hy.ea.service.UpLoadFileService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 商品（宝贝）管理
 */
@Controller
@Scope("prototype")
public class BabyManageAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private UpLoadFileService fileService;
	private PageForm pageForm;
	private String result;
	private int pageNumber;
	private String search;
	private Baby baby;
	private AttriBaby attriBaby;
	private List<BaseBean> list;
	private Map<String,List<BaseBean>> mapvalue;
	private List<BaseBean> colortypelist;
	private List<BaseBean> colorlist;
	private List<String> sizetypelist;
	private List<BaseBean> sizelist;
	
	
	
	
	/**
	 * 
	 * 
	 * 点击发布 获取发布页面的内容
	 * @return
	 */
	public String getBabyPublishPage(){
		String cateID = "3";
		getAttriCateList(cateID);
		getColor();
		getSize();
		
		return "babypub";
		
		
	}
	
	/**
	 * 
	 * 
	 * 根据类目获取属性对
	 * @return
	 */
	public String getAttriCateList(String cateID){
		String hql = "from AttriCate where cateID = ? and status = ? order by orders";
		list = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{cateID,"0"});
		String hqlvalue = "from AttriValueCate where acID = ? and status = ?";
		mapvalue = new HashMap<String, List<BaseBean>>();
		List<BaseBean> valuelist = null;
		for(BaseBean b:list){
			AttriCate attr = (AttriCate) b;
			System.out.println(attr.getAttriname());
			if(attr.getIsMultiValue().equals("0")){
				valuelist = baseBeanService.getListBeanByHqlAndParams(hqlvalue,new Object[]{attr.getAcID(),"0"});
			   mapvalue.put(attr.getAcID(),valuelist);
			}
			
		}

		return "";
	}
	
	
	public String getColor(){
		String hql = "from AttrColorType order by orders";
		colortypelist = baseBeanService.getListBeanByHqlAndParams(hql,null);
		String hqlcolor = "from AttrColor order by orders";
		colorlist = baseBeanService.getListBeanByHqlAndParams(hqlcolor, null);
		return "";
	}
	
	public String getSize(){
		String hql = "from AttrSize order by orders";
		sizelist = baseBeanService.getListBeanByHqlAndParams(hql,null);
		sizetypelist = new ArrayList<String>();
		for(BaseBean b:sizelist){
			AttrSize size = (AttrSize) b;
			if(!sizetypelist.contains(size.getAstype())){
				sizetypelist.add(size.getAstype());
			}
		}
		return "";
	}
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
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
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public Baby getBaby() {
		return baby;
	}
	public void setBaby(Baby baby) {
		this.baby = baby;
	}
	public AttriBaby getAttriBaby() {
		return attriBaby;
	}
	public void setAttriBaby(AttriBaby attriBaby) {
		this.attriBaby = attriBaby;
	}

	public List<BaseBean> getList() {
		return list;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}

	public Map<String, List<BaseBean>> getMapvalue() {
		return mapvalue;
	}

	public void setMapvalue(Map<String, List<BaseBean>> mapvalue) {
		this.mapvalue = mapvalue;
	}

	public List<BaseBean> getColortypelist() {
		return colortypelist;
	}

	public void setColortypelist(List<BaseBean> colortypelist) {
		this.colortypelist = colortypelist;
	}

	public List<BaseBean> getColorlist() {
		return colorlist;
	}

	public void setColorlist(List<BaseBean> colorlist) {
		this.colorlist = colorlist;
	}

   

	public List<String> getSizetypelist() {
		return sizetypelist;
	}

	public void setSizetypelist(List<String> sizetypelist) {
		this.sizetypelist = sizetypelist;
	}

	public List<BaseBean> getSizelist() {
		return sizelist;
	}

	public void setSizelist(List<BaseBean> sizelist) {
		this.sizelist = sizelist;
	}

    

}
