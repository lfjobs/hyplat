package hy.ea.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.human.COrganization;
import hy.ea.service.CCodeService;
import hy.ea.util.Constant;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class AccessResourceAction {
	private static final Logger logger = LoggerFactory.getLogger(AccessResourceAction.class);
	@Resource
	private CCodeService codeService;
	@Resource
	private BaseBeanService baseBeanService;
	private List<BaseBean> beans;
	private List<BaseBean> ccodeList;
	private PageForm pageForm;
	private String result;
	private int pageNumber;
	/**
	 * 01	往来单位
	 * 02	社会人力
	 * 03	员工
	 * 04	物品
	 * @return
	 */
	private String stuts;
	/**
	 * xml包名,Action跳转方法
	 */
	private String stutsurl;
	/**
	 * 树头id
	 */
	private String pid; 
	/**
	 * 树头name
	 */
	private String pname;
	/**
	 * 人员往来关系
	 */
	private List<CCode> ccodes; 
	/**
	 * 单位往来关系
	 */
	private List<CCode> ccodec; 
	/**
	 * 选中id
	 */
	private String selectedID;
	/**
	 * 模糊查询条件,文本框值
	 */
	private String searchvalue;
	/**
	 * 查询条件,往来人员关系ContactRelation
	 */
	private String ccodesvalue;
	/**
	 * 查询条件,往来单位关系
	 */
	private String ccodecvalue;
	/**
	 * jsp 跳转页面 00单独页面 01有rightFram框架页
	 */
	private String jsptype;
	public String getTreeHead(){
		stutsurl = Constant.TY.get(stuts);
		
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		ccodes = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110106hfjes5ucxp0000000017");
		ccodec = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		if(stuts.equals("01")){//01	往来单位
			pid = "scode20110106hfjes5ucxp0000000003";
			pname = "行业分类";
		}else if(stuts.equals("02")){//02	社会人力
			pid = "00000000AAA";
			pname = "人员分类";
		}else if(stuts.equals("03")){//03	员工
			pid = account.getCompanyID();
			pname = "部门分类";
		}else if(stuts.equals("04")){
			pid = "scode20101014v5zed7cukk0000000002";
			pname = "物品分类";
		}
		if(jsptype.equals("00")){
			return "tree";
		}else{
			return "treecontact";
		}
	}
	/**
	 * 01	往来单位
	 * 02	社会人力
	 * 03	员工
	 * 04	物品
	 * @return
	 */
	public String getTreebody(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		
		if(stuts.equals("01")){
			if(null == selectedID || "".equals(selectedID)){
				String hql1 = "from CCode c where c.codePID = ? and c.companyID = ? and c.codeStatus = ? order by codeNumber";
				Object[] obj = new Object[]{"scode20110106hfjes5ucxp0000000003",account.getCompanyID(),"00"};
				ccodeList = baseBeanService.getListBeanByHqlAndParams(hql1, obj);
			}else{
				String hql1 = "from CCode c where c.codePID = ? and c.companyID = ? and c.codeStatus < ? order by codeNumber";
				Object[] obj = new Object[]{selectedID.substring(1),account.getCompanyID(),"98"};
				ccodeList = baseBeanService.getListBeanByHqlAndParams(hql1, obj);
			}
		}else if(stuts.equals("02")){
			ccodeList = new ArrayList<BaseBean>();
			for(int i=0; i<7; i++){
				CCode c = new CCode();
				if(i==0){
					c.setCodeID("CodeID00");
					c.setCodeValue("一般人物");
				}else if(i==1){
					c.setCodeID("CodeID01");
					c.setCodeValue("政界人物");
				}else if(i==2){
					c.setCodeID("CodeID02");
					c.setCodeValue("商界人物");
				}else if(i==3){
					c.setCodeID("CodeID03");
					c.setCodeValue("学术界人物");
				}else if(i==4){
					c.setCodeID("CodeID04");
					c.setCodeValue("艺术届人物");
				}else if(i==5){
					c.setCodeID("CodeID05");
					c.setCodeValue("科学届人物");
				}else if(i==6){
					c.setCodeID("CodeID06");
					c.setCodeValue("其他人物");
				}
				ccodeList.add(c);
			}
		}else if(stuts.equals("03")){
			String hql = "from COrganization o where o.companyID = ? and o.organizationPID = ? and o.Status = ?";
			Object[] obj = new Object[]{account.getCompanyID(),account.getCompanyID(),"00"};
			beans = baseBeanService.getListBeanByHqlAndParams(hql, obj);
			ccodeList = new ArrayList<BaseBean>();
			for(int i=0; i<beans.size(); i++){
				COrganization o = (COrganization)beans.get(i);
				CCode c = new CCode();
				c.setCodeID(o.getOrganizationID());
				c.setCodeValue(o.getOrganizationName());
				ccodeList.add(c);
			}
		}else if(stuts.equals("04")){
			if(null == selectedID || "".equals(selectedID)){
				String hql1 = "from CCode c where c.codePID = ? and c.companyID = ? and c.codeStatus = ? order by codeNumber";
				Object[] obj = new Object[]{"scode20101014v5zed7cukk0000000002",account.getCompanyID(),"00"};
				ccodeList = baseBeanService.getListBeanByHqlAndParams(hql1, obj);
			}else{
				String hql1 = "from CCode c where c.codePID = ? and c.companyID = ? and c.codeStatus < ? order by codeNumber";
				Object[] obj = new Object[]{selectedID.substring(4),account.getCompanyID(),"98"};
				ccodeList = baseBeanService.getListBeanByHqlAndParams(hql1, obj);
			}
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("ccodeList",ccodeList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	
	/**
	 * 获取社会人力
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getSocial(){
		String groupCompanySn = ActionContext.getContext().getSession()
				.get("groupCompanySn").toString();
		String s  = selectedID;
		if(!s.equals("00000000AAA")){
			 s = s.substring(6);
		}
		if (groupCompanySn == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String sql = "select s.staffid,s.staffcode,s.staffname,s.staffidentitycard," +
				" s.reference,s.address,s.birthday,s.sex from dt_hr_staff s where s.groupCompanySn = ?" +
				" and s.staffStatus = ?";
		List parms = new ArrayList();
		parms.add(groupCompanySn);
		parms.add("00");
		if(s.equals("00000000AAA")){
		}else if(s.equals("06")){
			sql += " and s.status is null";
		}else {
			sql += " and s.status = ?";
			parms.add(s);
		}
		if(null != searchvalue && !"".equals(searchvalue)){
			sql += " and s.staffName like ?";
			parms.add("%"+searchvalue+"%");
		}
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql,"select count(1) "
				+ sql.substring(sql.indexOf("from")),parms.toArray());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		try {
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		}
		return "success";
	}
	/**
	 * 获取单位
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getCorporation(){  
		
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String s =selectedID;
		String sql = "select t.ccompanyid,t.companyname,c.contactconnections," +
				"t.industrytype,t.responsibletel,t.companyAddr from dtcontactcompany t left join" +
				" dtcontactconnection c on t.ccompanyid = c.ccompanyid" +
				" where c.companyid = ?";
		List parms = new ArrayList();
		parms.add(account.getCompanyID());
		if(s.equals("scode20110106hfjes5ucxp0000000003")){
		}else if(s.substring(1).contains("scode")){
			List<CCode> codes = codeService.getCCodeListByPID(account.getCompanyID(),s.substring(1));
			 sql += "  and (";
			 if(null == codes || codes.size() == 0){
				 sql += "t.industrytype = ?";
					parms.add("其他");
			 }
			for(int i=0 ; i<codes.size() ; i++){
				if(i == 0){
					sql += " t.industrytype = ?";
				}else{
					sql += " or t.industrytype = ?";
				}
				parms.add(codes.get(i).getCodeValue());
				
			}
			 sql += "  )";
		}else{
			sql += " and t.industrytype like ?";
			parms.add("%"+s+"%");
		}
		if(null != searchvalue && !"".equals(searchvalue)){
			sql += " and t.companyName like ?";
			parms.add("%"+searchvalue+"%");
		}
		if(null != ccodecvalue && !"".equals(ccodecvalue)){
			sql += " and c.contactConnections = ?";
			parms.add(ccodecvalue);
		}
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql,"select count(1) "
				+ sql.substring(sql.indexOf("from")),parms.toArray());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		
		
		return "success";
	} 
	/**
	 * 获取部门人员
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getPersonnel(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String s =selectedID;
		String sql = "select s.staffid,s.staffcode,s.staffname,s.reference,s.staffidentitycard,o.organizationname,d.postname,c.organizationid,c.deppostid" +
				" from dt_hr_staff s left join dtCos c on s.staffid = c.staffid " +
				" left join dtcorganization o on c.organizationid = o.organizationid" +
				" left join dt_hr_deptpost d on c.deppostid = d.deppostid" +
				" where c.companyID = ? and c.cosStatus = ?";
		List parms = new ArrayList();
		parms.add(account.getCompanyID());
		parms.add("50");
		if(!s.equals(account.getCompanyID())){
			sql += " and c.organizationID = ?";
			parms.add(s);
		}
		if(null != searchvalue && !"".equals(searchvalue)){
			sql += " and s.staffname like ?";
			parms.add("%"+searchvalue+"%");
		}
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql,"select count(1) "
				+ sql.substring(sql.indexOf("from")),parms.toArray());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**
	 * 获取物品明细
	 * GoodsManage
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes" })
	public String getGoods(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String s =selectedID;
		String sql = "select g.goodsID,g.goodsCoding,g.goodsName,g.typeID,g.num,g.variableID,g.acquiesceStandard,g.standard,g.mnemonicCode," +
				" g.model,g.defaultStorage,g.manufacturers,g.goodsvariable,g.subjectsName,g.subjectsID,g.companyStorsge," +
				" g.twoCode,g.barCode,g.logoPath,g.photoPath,g.tradeCode  from dtgoodsmanage g where g.goodsState = ?";
		
		List parms = new ArrayList();
		parms.add("00");
		if("scode20101014v5zed7cukk0000000002".equals(s)){
		}else if(s.substring(0, 4).equals("00no")){
			List<CCode> codes = codeService.getCCodeListByPID(account.getCompanyID(),s.substring(4));
			sql += " and g.typeid in (";
			 if(null == codes || codes.size() == 0){
				 sql += "?";
					parms.add("其他");
			 }
			for(int i=0 ; i<codes.size() ; i++){
				if(i == 0){
					sql += " ?" ;
				}else{
					sql += " ,?" ;
				}
				parms.add(codes.get(i).getCodeValue());
			}
			sql += " )"; 
		}else{
			sql += " and g.typeid = ?";
			parms.add(s);
		}
		if(null != searchvalue && !"".equals(searchvalue)){
			sql += " and (g.goodsCoding like ? or g.goodsName like ?)";
			parms.add("%"+searchvalue+"%");
			parms.add("%"+searchvalue+"%");
		}
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql,"select count(1) "
				+ sql.substring(sql.indexOf("from")),parms.toArray());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		
		return "success";
	}
	
	/**
	 * 微分金查询数据
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getWfjift(){
		String sql = "";
		List parms = new ArrayList();
		if(stuts.equals("01")){//01	往来单位
			sql = "select cc.ccompanyid,cc.companyname,cc.companyaddr,cc.companytel from dtcontactcompany cc where 1=1";
			if(null != searchvalue && !"".equals(searchvalue)){
				sql += " and cc.companyname like ?";
				parms.add("%"+searchvalue+"%");
			}
		}else if(stuts.equals("02")){//02社会人力
		}else if(stuts.equals("03")){//03员工
		}else if(stuts.equals("04")){//04物品分类
		}
		
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql,"select count(1) "
				+ sql.substring(sql.indexOf("from")),parms.toArray());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	
	
	public String getStuts() {
		return stuts;
	}
	public void setStuts(String stuts) {
		this.stuts = stuts;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}

	public List<BaseBean> getCcodeList() {
		return ccodeList;
	}

	public void setCcodeList(List<BaseBean> ccodeList) {
		this.ccodeList = ccodeList;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	public List<CCode> getCcodes() {
		return ccodes;
	}
	public void setCcodes(List<CCode> ccodes) {
		this.ccodes = ccodes;
	}
	public List<CCode> getCcodec() {
		return ccodec;
	}
	public void setCcodec(List<CCode> ccodec) {
		this.ccodec = ccodec;
	}
	public String getStutsurl() {
		return stutsurl;
	}
	public void setStutsurl(String stutsurl) {
		this.stutsurl = stutsurl;
	}
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public String getSelectedID() {
		return selectedID;
	}
	public void setSelectedID(String selectedID) {
		this.selectedID = selectedID;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getSearchvalue() {
		return searchvalue;
	}
	public void setSearchvalue(String searchvalue) {
		this.searchvalue = searchvalue;
	}
	public String getCcodesvalue() {
		return ccodesvalue;
	}
	public void setCcodesvalue(String ccodesvalue) {
		this.ccodesvalue = ccodesvalue;
	}
	public String getCcodecvalue() {
		return ccodecvalue;
	}
	public void setCcodecvalue(String ccodecvalue) {
		this.ccodecvalue = ccodecvalue;
	}
	public String getJsptype() {
		return jsptype;
	}
	public void setJsptype(String jsptype) {
		this.jsptype = jsptype;
	}

	
}
