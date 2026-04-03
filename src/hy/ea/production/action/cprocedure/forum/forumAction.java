package hy.ea.production.action.cprocedure.forum;

import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.ProductComment;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.production.service.forumService;
import hy.plat.bo.PageForm;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tiantai.wfj.bo.TEshopCusCom;



/**
 * 驾校信息
 */
@Controller
@Scope("prototype")
public class forumAction {
	@Resource
	private forumService forumServlet;
	
	private String result;
	private ContactCompany concom;
	private TEshopCusCom ccom;
	private PageForm pageForm;
	private String commonEssence;//00:查询根据时间查询,01:根据评论热度查询,02:查询回复
	private String judge;//00:关注,01:粉丝
	private String community;//00:我的信息,01:其他人的信息
	private ProductPackaging ppk;
	private ProductComment pct;
	private String companyid;
	
	
	
	/**
	 * 跳转登录
	 * @return
	 */
	public String goBackLogin() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.removeAttribute("url");
		HttpServletRequest request = ServletActionContext.getRequest();
		String url = request.getHeader("Referer");
		session.setAttribute("url", url);
		return "login";
	}
	
	/**
	 * 查询公司论坛信息
	 * @return
	 */
	public String forumMessage(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		//查询公司信息
		Object[] obj = forumServlet.forumMessage(concom.getCcompanyID());
		session.setAttribute("ccompanyid", concom.getCcompanyID());
		session.setAttribute("companyid", obj[2]);
		//查询公司帖子数量
		Object invitationCount = forumServlet.invitation(obj[2]);
		//查询粉丝数
		Object attentionCount = forumServlet.attention(obj[2]);
		//查询是否关注该公司
		Map<String, Object> attention = forumServlet.estimate(obj[2]);
		request.setAttribute("obj", obj);
		request.setAttribute("invitationCount", invitationCount);
		request.setAttribute("attentionCount", attentionCount);
		request.setAttribute("attention", attention);
		
		return "forumMessage";
	}
	
	/**
	 * 查询该公司论坛帖子
	 * @return
	 */
	public String invitationList(){
		
		Map<String, Object> map = forumServlet.invitationList(pageForm.getPageNumber(), pageForm.getPageSize(),companyid,commonEssence);
		
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	
	/**
	 * 关注公司
	 * @return
	 */
	public String follow(){
		Map<String, Object> map = forumServlet.estimate(companyid);
		if(map.get("userExist").equals(true)){
			forumServlet.follow(companyid);
		}else{
			forumServlet.storageUrl();
		}
		
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	/**
	 * 查询回复过的帖子
	 * @return
	 */
	public String reply(){
		TEshopCusCom cuscom = forumServlet.queryUser();
		Map<String, Object> map = new HashMap<String, Object>();
		if(cuscom!=null){
			pageForm = forumServlet.reply(cuscom.getStaffid(),pageForm.getPageNumber(),pageForm.getPageSize());
			map.put("replyJudge", true);
			map.put("pageForm", pageForm);
		}else{
			forumServlet.storageUrl();
			map.put("replyJudge", false);
		}
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	
	/**
	 * 查询回复过的帖子详情
	 * @return
	 */
	public String replyParticulars(){
		TEshopCusCom cuscom = forumServlet.queryUser();
		if(cuscom==null){
			return goBackLogin();
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		Object obj = forumServlet.replyParticulars(pct.getPcID());
		request.setAttribute("obj", obj);
		request.setAttribute("cuscom", cuscom);
		return "replyParticulars";
	}
	/**
	 * 查询帖子的所有回复
	 * @return
	 */
	public String invitationReply(){
		Map<String, Object> map = new HashMap<String, Object>();
		pageForm = forumServlet.invitationReply(pct.getPcID(),pageForm.getPageNumber(),pageForm.getPageSize());
		map.put("pageForm", pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	
	/**
	 * 删除二级回复
	 * @return
	 */
	public String delReply(){
		Map<String, Object> map = new HashMap<String, Object>();
		boolean bl = forumServlet.delReply(pct.getPcID());
		map.put("bl", bl);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	/**
	 * 删除一级回复
	 * @return
	 */
	public String delMyReply(){
		Map<String, Object> map = new HashMap<String, Object>();
		boolean bl = forumServlet.delMyReply(pct.getPcID());
		map.put("bl", bl);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	/**
	 * 用户回复
	 * @return
	 */
	public String userReply(){
		TEshopCusCom cuscom = forumServlet.queryUser();
		Map<String, Object> map = new HashMap<String, Object>();
		if(cuscom!=null){
			map = forumServlet.userReply(cuscom,pct);
			map.put("user", true);
		}else{
			forumServlet.storageUrl();
			map.put("user", false);
		}
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	
	
	/**
	 * 查询我的信息
	 * @return
	 */
	public String myMessage(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String sccid = "";
		TEshopCusCom cuscom = forumServlet.queryUser();
		if(community.equals("00")){
			if(cuscom==null){
				return goBackLogin();
			}
			sccid = cuscom.getSccId();
			request.setAttribute("whether", false);
		}else if(community.equals("01")){
			sccid = ccom.getSccId();
			if(cuscom.getSccId().equals(sccid)){
				community = "00";
			}
			request.setAttribute("whether", true);
		}
		Map<String, Object> map = forumServlet.myMessage(sccid,cuscom.getSccId());
		
		request.setAttribute("myMessage", map);
		request.setAttribute("sccid", sccid);
		return "myMessage";
	}
	
	
	/**
	 * 查询我的帖子
	 * @return
	 */
	public String myiNvitation(){
		String sccid = "";
		if(community.equals("00")){
			TEshopCusCom cuscom = forumServlet.queryUser();
			sccid = cuscom.getSccId();
		}else if(community.equals("01")){
			sccid = ccom.getSccId();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		
		pageForm = forumServlet.myiNvitation(sccid,pageForm.getPageNumber(),pageForm.getPageSize());
		
		map.put("pageForm", pageForm);
		
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	
	/**
	 * ajax跳转
	 * @return
	 */
	public String ajaxSkip(){
		if(judge.equals("00")){
			if(community.equals("00")){
				return "myAttention";
			}else if(community.equals("01")){
				return "otherAttention";
			}
		}else if(judge.equals("01")){
			if(community.equals("00")){
				return "myFans";
			}else if(community.equals("01")){
				return "otherFans";
			}
		}
		return "";
	}
	
	
	
	/**
	 * 查询关注
	 * @return
	 */
	public String myAttention(){
		Map<String, Object> map = new HashMap<String, Object>();
		
		pageForm = forumServlet.myAttention(ccom.getSccId(),pageForm.getPageNumber(),pageForm.getPageSize(),community);
		
		map.put("pageForm", pageForm);
		
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	
	/**
	 * 查询粉丝
	 * @return
	 */
	public String myFans(){
		Map<String, Object> map = new HashMap<String, Object>();
		
		pageForm = forumServlet.myFans(ccom.getSccId(),pageForm.getPageNumber(),pageForm.getPageSize(),community);
		
		map.put("pageForm", pageForm);
		
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	/**
	 * 添加/取消关注
	 * @return
	 */
	public String shift(){
		Map<String, Object> map = forumServlet.shift(ccom.getSccId());
		if(map.get("userExist").equals(false)){
			forumServlet.storageUrl();
		}
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	
	/**
	 * 查询用户是否登录
	 * @return
	 */
	public String whetherRegister(){
		boolean userExist = true;
		TEshopCusCom cuscom = forumServlet.queryUser();
		Map<String, Object> map = new HashMap<String, Object>();
		if(cuscom==null){
			forumServlet.storageUrl();
			userExist = false;
		}else{
			map.put("cuscom", cuscom);
		}
		map.put("userExist", userExist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public ContactCompany getConcom() {
		return concom;
	}

	public void setConcom(ContactCompany concom) {
		this.concom = concom;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getCommonEssence() {
		return commonEssence;
	}

	public void setCommonEssence(String commonEssence) {
		this.commonEssence = commonEssence;
	}

	public TEshopCusCom getCcom() {
		return ccom;
	}

	public void setCcom(TEshopCusCom ccom) {
		this.ccom = ccom;
	}

	public String getJudge() {
		return judge;
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public ProductPackaging getPpk() {
		return ppk;
	}

	public void setPpk(ProductPackaging ppk) {
		this.ppk = ppk;
	}

	public ProductComment getPct() {
		return pct;
	}

	public void setPct(ProductComment pct) {
		this.pct = pct;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	
}
