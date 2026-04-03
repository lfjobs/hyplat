package hy.ea.company.action;

import hy.base.action.BaseAction;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.ContactImg;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * 单位图片
 * 
 * @author l
 * 
 */
@SuppressWarnings("serial")
public class ContactImgAction extends BaseAction<ContactImg> {
	@Resource
	private UpLoadFileService fileService;
	private String search;
	private ContactImg cimg;
	private List<BaseBean> cimgList;
	private Map<String, ContactImg> imgmap;
	private List<BaseBean> beans;
	private String parameter;
	/**
	 * 查询
	 * 
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch", cimg);
		return findItem();
	}

	/**
	 * 加载
	 * 
	 * @return
	 */
	public String findItem() {
		cimgList = new ArrayList<BaseBean>();
		cimgList = baseBeanService.getListBeanByHqlAndParams(
				" from ContactImg where ccompanyID = ? order by ctime desc",
				new Object[] { cimg.getCcompanyID() });
		return "itemList";
	}

	/**
	 * 添加或修改地址 参数：addressmap 
	 * 返回：getListAddress()
	 */
	public String saveImg() {
		beans = new ArrayList<BaseBean>();
		
		if(null == cimg.getContactimgID() || cimg.getContactimgID().equals("")){
			cimg.setContactimgID(serverService.getServerID("cimg"));
			cimg.setCname(this.getCurrentAccount().getAccountEmail());
			cimg.setCtime(new Date());
			parameter = "添加图片";
		}else{
			cimg.setUname(this.getCurrentAccount().getAccountEmail());
			cimg.setUtime(new Date());
			parameter = "修改图片";
		}
		
		//图片保存
		String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
		String photoPath = fileService
				.savePhoto(
						path,
						cimg.getPhotoFileName(),
						cimg.getPhoto(),
						this.getCurrentAccount().getCompanyID(),
						"/contact/personPhotos/"
								+ Utilities.getDateString(new Date(),
										"yyyy-MM-dd"));
		if (cimg.getImgFile() != null && !"".equals(cimg.getImgFile())) {
			ArrayList<String> paths = new ArrayList<String>();
			paths.add(path + cimg.getImgFile());
			fileService.deletePhotos(paths);
		}
		
		cimg.setImgFile(photoPath);
		beans.add(cimg);
		CLogBook logBook = logBookService.saveCLogBook(null, parameter,
				this.getCurrentAccount());
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
				null);
		return findItem();
	}

	// 删除某条地址信息
	public String delImg() {
		
		//删除图片
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		if (cimg.getImgFile() != null && !"".equals(cimg.getImgFile())) {
			ArrayList<String> paths = new ArrayList<String>();
			paths.add(path + cimg.getImgFile());
			fileService.deletePhotos(paths);
		}
		
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(null, "删除图片(人员名称："
				+ this.getCurrentAccount().getAccountEmail() + ")", this.getCurrentAccount());
		beans.add(logBook);
		String hql = "delete ContactImg i where i.contactimgID = ?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, new Object[]{cimg.getContactimgID()});
		return findItem();
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public ContactImg getCimg() {
		return cimg;
	}

	public void setCimg(ContactImg cimg) {
		this.cimg = cimg;
	}

	public List<BaseBean> getCimgList() {
		return cimgList;
	}

	public void setCimgList(List<BaseBean> cimgList) {
		this.cimgList = cimgList;
	}

	public Map<String, ContactImg> getImgmap() {
		return imgmap;
	}

	public void setImgmap(Map<String, ContactImg> imgmap) {
		this.imgmap = imgmap;
	}

}
