package hy.ea.human.action;

import cn.hutool.http.server.HttpServerRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysl.bo.administrative.DtMytravel;
import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.base.action.BaseAction;
import hy.ea.bo.CAccount;
import hy.ea.human.service.OutworkApplyMobileService;
import hy.ea.human.service.OutworkingMobileService;
import hy.ea.human.service.SignInCheckOnService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
public class OutworkingMobileAction  {
	@Resource
	private BaseBeanService baseBeanService;
	@Autowired
	private SignInCheckOnService signInCheckOnService;
	@Autowired
	private OutworkApplyMobileService outworkApplyMobileService;

	@Resource
	private OutworkingMobileService outworkingMobileService;
	private PageForm pageForm;
	private String result;
	private DtMytravel outworkApply;
	private String outworkApplyTime;
	private String sccId;


	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = ServletActionContext.getRequest().getSession();
	SessionWrap sw = SessionWrap.getInstance();

	public String findCurrentUser(){
		result = signInCheckOnService.findCurrentUser();
		return "success";
	}



	//查询打卡列表
	public String queryWorkingList(){
		CAccount account = (CAccount) session.getAttribute("account");
		String staffID = account.getStaffID();
		String companyID = account.getCompanyID();
		List<BaseBean> beans = outworkingMobileService.workingList(staffID, companyID);
		result= JSON.toJSONString(beans);
		return "success";
	}
	//打卡
	public String working(){
		String local = request.getParameter("local");
		String img=request.getParameter("img");
		String type=request.getParameter("type");
		String id=request.getParameter("id");
		CAccount account = (CAccount) session.getAttribute("account");
		TEshopCusCom ShopCusCom =(TEshopCusCom) session.getAttribute("key_shop_cus_com");

		outworkingMobileService.working(local,img,type,account,ShopCusCom,id);
		return "success";
	}
	//查询打开详情
	public String queryWorkingDetailList(){
		outworkingMobileService.queryWorkingListDetail(sccId);
		return "detailList";
	}
	public String workingList() {
		return "list";



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

	public DtMytravel getOutworkApply() {
		return outworkApply;
	}

	public void setOutworkApply(DtMytravel outworkApply) {
		this.outworkApply = outworkApply;
	}

	public String getOutworkApplyTime() {
		return outworkApplyTime;
	}

	public void setOutworkApplyTime(String outworkApplyTime) {
		this.outworkApplyTime = outworkApplyTime;
	}
}
