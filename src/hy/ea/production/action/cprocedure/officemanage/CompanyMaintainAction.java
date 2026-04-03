package hy.ea.production.action.cprocedure.officemanage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.production.GoodFunction;
import hy.ea.production.service.CompanyMaintainService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.tiantai.wfj.bo.TEshopCusCom;

/**
 * 公司维护
 */
@Controller
public class CompanyMaintainAction {
	@Resource
	private CompanyMaintainService cmService;

	private ProductPackaging productPackaging;
	private ContactCompany concom;
	private GoodFunction goodFunction;
	private PageForm pageForm;
	private String result;
	private Map<String, Object> map;
	private String webpageSkip;
	private Object obj;
	private List<Object> list;
	private String skipString;
	private TEshopCusCom cuscom;

	private String cropInp;// 新闻主图
	private String cropInpFileName;// 主图名称
	private String content;// 新闻内容

	// 安卓所用参数
	private String message;// 提示信息主标题
	private String type;// 信息类型
	private String body;// 具体消息内容,可以存放url
	private String id;// 推送的业务 id,指定某类型的页面跳转
	private String ssJudge;// 推送判断(暂定)0:全部,1:所在公司,2:所在部门,3:个人
	private String accountNumber;// 推送条件

	/**
	 * @Title: 跳转
	 * @Description: 用于跳转页面所用
	 * @param skipString
	 *            :用于判断跳回那个页面
	 * @return 返回页面
	 */
	public String newsSkip() {
		cuscom = cmService.queryUser();
		if (cuscom == null) {
			HttpSession session = ServletActionContext.getRequest()
					.getSession();
			session.removeAttribute("url");
			HttpServletRequest request = ServletActionContext.getRequest();
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
			return "login";
		} else {
			if (skipString.equals("allNews")) {
				return "allNews";// 跳转公司全部新闻页面
			} else if (skipString.equals("queryNews")) {
				concom = cmService.queryCompanyDetails(cuscom.getStaffid());
				return "queryNews";// 跳转添加新闻页面
			}
		}
		return "";
	}

	/**
	 * @Title: 删除
	 * @Description: 删除公司新闻
	 * @return 返回true:成功false:失败
	 */
	public String delNews() {
		boolean b = cmService.delNews(productPackaging.getPpID());
		map = new HashMap<String, Object>();
		map.put("b", b);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * @Title: 增加或修改
	 * @Description: 增加或修改公司新闻
	 * @return 返回true:成功false:失败
	 */
	public String saveOrUpdateNews() {
		map = cmService.saveOrUpdateNews(productPackaging.getPpID(),
				productPackaging.getGoodsName(), this.getCropInp(),
				this.getCropInpFileName(), cuscom.getStaffid(),
				cuscom.getCompanyId(), this.getContent());

		return "saveOrUpdateNews";
	}

	/**
	 * @Title: 查询
	 * @Description: 查询公司新闻详情
	 * @return 返回的数据集合
	 */
	public String queryNews() {
		concom = cmService.queryCompanyDetails(cuscom.getStaffid());
		list = cmService.queryNews(productPackaging.getPpID());

		return "queryNews";
	}

	/**
	 * @Title: 查询
	 * @Description: 查询全部公司新闻
	 * @return 返回的数据集合
	 */
	public String ajax_queryAllNews() {
		map = cmService.queryAllNews(pageForm.getPageNumber(), 6,
				cuscom.getCompanyId(), cuscom.getStaffid());
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	/**
	 * @Title: 查询
	 * @Description: 查询用户粉丝好友
	 * @return 返回的数据集合
	 */
	public String ajax_friend(){
		List<BaseBean> ufList = cmService.userFriend(cuscom.getStaffid());
		map = new HashMap<String, Object>();
		map.put("list", ufList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	/**
	 * @Title: 推送
	 * @Description: 安卓推送
	 * @return
	 */
	public String androidShoveSend() {
		cmService.androidShoveSend(this.getMessage(), this.getType(),
				this.getBody(), this.getId(), this.getSsJudge(),
				this.getAccountNumber(),cuscom.getStaffid(),cuscom.getCompanyId());

		return "ShoveSend";
	}

	public ProductPackaging getProductPackaging() {
		return productPackaging;
	}

	public void setProductPackaging(ProductPackaging productPackaging) {
		this.productPackaging = productPackaging;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public GoodFunction getGoodFunction() {
		return goodFunction;
	}

	public void setGoodFunction(GoodFunction goodFunction) {
		this.goodFunction = goodFunction;
	}

	public String getWebpageSkip() {
		return webpageSkip;
	}

	public void setWebpageSkip(String webpageSkip) {
		this.webpageSkip = webpageSkip;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getCropInp() {
		return cropInp;
	}

	public void setCropInp(String cropInp) {
		this.cropInp = cropInp;
	}

	public String getCropInpFileName() {
		return cropInpFileName;
	}

	public void setCropInpFileName(String cropInpFileName) {
		this.cropInpFileName = cropInpFileName;
	}

	public String getSkipString() {
		return skipString;
	}

	public void setSkipString(String skipString) {
		this.skipString = skipString;
	}

	public TEshopCusCom getCuscom() {
		return cuscom;
	}

	public void setCuscom(TEshopCusCom cuscom) {
		this.cuscom = cuscom;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ContactCompany getConcom() {
		return concom;
	}

	public void setConcom(ContactCompany concom) {
		this.concom = concom;
	}

	public String getSsJudge() {
		return ssJudge;
	}

	public void setSsJudge(String ssJudge) {
		this.ssJudge = ssJudge;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

}
