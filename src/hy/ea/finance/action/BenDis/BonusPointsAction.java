 package hy.ea.finance.action.BenDis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import hy.ea.bo.finance.BenDis.OperatorInfo;
import hy.plat.bo.BaseBean;
import net.sf.json.JSONArray;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.struts2.ServletActionContext;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.util.SessionWrap;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.finance.BenDis.BonusPoints;
import hy.ea.bo.human.Staff;
import hy.ea.bo.lottery.PrizeActivityBean;
import hy.ea.bo.lottery.SignScoreBean;
import hy.ea.finance.service.BonusPointsService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;

/*
 * 积分
 * */
public class BonusPointsAction {
	
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private BonusPointsService bonusPointsService;	
	
	private Object result;
	private String account;//签到人帐号
	private String sccid;
	private String staffid;
	private String ccompanyId;//被签到的往来公司id	
	private String ppid;//产品表ppid（作用：分享文章中需要）
	private String toSign;//是否签到 yes 签过了  no 还没有签到
	private Integer jiFenNum;//签到积分数
	private BonusPoints bp;
	private PageForm pageForm;
	private int pageNumber;
	//金币池一系列的标识
	private String khd;//去除表头
	private String flag;//进入小系统的标识
	private String identifying;//判断 个人  登录还是公司登录（移动办公）    toSignPhone
	private String charge;//判断是跳转到积分充值或积分详情 charge 跳转到充值页面

	private String sdate;
	private String edate;
	private String type;
	private String operator;
	private String inAndExp;
	private String schType;

	/**
	 * 跳转到积分充值页面
	 */
	public String getWfjJifen(){
		//获取登录这信息
		getScoreData();
		return "jifenchongzhi";
	}
	
	/**
	 * 跳转到积分详情页面
	 */
	public String getWfjScore(){
		getScoreData();
		return "scoreDetail";
	}
	
	/**
	 * 查询积分基础信息
	 */
	private void getScoreData(){
	    HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletRequest request=ServletActionContext.getRequest();
		SessionWrap sw = SessionWrap.getInstance();
		String user=request.getParameter("user");
		String sccid=request.getParameter("sccid");
		Object syncpay=request.getAttribute("syncpay");
		//进入小系统
		if(user==null||user.equals("")){
			CAccount account = (CAccount)sw.getObject(session, SessionWrap.KEY_CACCOUNT);
			if(account!=null){
				TEshopCusCom cus=(TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where companyId = ? ", new Object[]{account.getCompanyID()});
				CcomCom ccomcom=(CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where comanyId=?", new Object[]{account.getCompanyID()});
				ccompanyId=ccomcom.getCcompanyId();
				//判断是否是公司登录   相等  则是公司登录
				if(account.getStaffID().equals(cus.getStaffid())){
					identifying = "company";
				}
			    user = cus.getAccount();
		        sccid = cus.getSccId();
		        request.setAttribute("khd","0");			      
		        flag = "sys";
			}
		}
		if(syncpay!=null&&"syncpay".equals(syncpay.toString())){
			user=request.getAttribute("syncuser").toString();
			sccid=request.getAttribute("syncuserid").toString();
		}
		request.setAttribute("user",user);
		request.setAttribute("sccid",sccid);
		TEshopCustomer customer=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0", new Object[]{user});
		TEshopCusCom cusCom=(TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccid=?", new Object[]{sccid});
		request.setAttribute("staffid", cusCom.getStaffid());
		request.setAttribute("state", cusCom.getState()); 
		ProductPackaging pp=(ProductPackaging)baseBeanService.getBeanByHqlAndParams("from ProductPackaging where type=? and model=?", new Object[]{"会员类型级别",cusCom.getCusType()});
		request.setAttribute("pp", pp);
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
				"from Staff where staffID=?",
				new Object[] { customer.getStaffid() });
		request.setAttribute("staff", staff);
		bp = (BonusPoints)baseBeanService.getBeanByHqlAndParams("from BonusPoints where sccid=? ", new Object[]{sccid});
	}
	
	/**
	 * 生成订单号
	 * @return
	 */
	public String getJum(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String user=request.getParameter("user");
		TEshopCustomer customer=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0", new Object[]{user});
		if(customer!=null){
			String jum=serverService.getBillID("company201009046vxdyzy4wg0000000025");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("jum", jum);
			map.put("wfj_staffid",customer.getStaffid());
//			if("sys".equals(flag)){
				OperatorInfo operator = new OperatorInfo(serverService.getServerID("operator"),customer.getStaffid(),"",jum,"","00");
				baseBeanService.save(operator);
//			}
			JSONObject oj = JSONObject.fromObject(map);
			result = oj.toString();
		}
		return "success";
	}
	
	
	
	/**
	 * 活动 		
	 @return 字符串 flag 返回是  no 该公司没有活动   without 该公司没有积分；
	 *         notEnough 积分不够  ；  without 该公司没有积分；notEnough 积分不够  ；notSet 没有设置签到积分； 
	 *         sign 有积分，有活动 （可以进行签到活动）；end 时间活动结束 
	 */
	public String getPrizeActivity(){
		String flag = "";			
		flag = bonusPointsService.getPrizeActivity(ccompanyId,null,null);
		JSONObject obj = new JSONObject();
		obj.accumulate("flag", flag);
		result = obj;	
		return "success";
	}
	
	
	
	//跳转签到页面
	public String getSign() throws Exception{
		//获取登录这信息
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();		
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom cc = (TEshopCusCom)sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
		if(cc == null){
			String sccid = request.getParameter("sccid");
			cc = (TEshopCusCom) baseBeanService
					.getBeanByHqlAndParams("from TEshopCusCom where sccid=?", new Object[]{sccid}); 
		}
		//登录者的信息
		sccid = cc.getSccId();
		staffid = cc.getStaffid();
		account = cc.getAccount();
		String companyId = request.getParameter("companyId");
		CcomCom com = null;
		if(companyId==null){
			//往来公司表
			com = (CcomCom) baseBeanService
					.getBeanByHqlAndParams("from CcomCom where ccompanyId=?", new Object[]{ccompanyId});
			
			companyId = com.getComanyId();
		}else{
			com = (CcomCom) baseBeanService
					.getBeanByHqlAndParams("from CcomCom where comanyId=?", new Object[]{companyId});
			ccompanyId = com.getCcompanyId();
		}
		toSign = bonusPointsService.getSign(companyId,account);	
		bp = (BonusPoints)baseBeanService
        		.getBeanByHqlAndParams("from BonusPoints where sccid=? ", new Object[]{sccid});
		khd = request.getParameter("khd");
		return "getbonusPoints"; 
	}				
	
	//签到
	public String toSign()
	{
		String flag = "";

		CcomCom com = (CcomCom)this.baseBeanService
				.getBeanByHqlAndParams("from CcomCom where ccompanyId=?", new Object[] { this.ccompanyId });
		this.toSign = this.bonusPointsService.getSign(com.getComanyId(), this.account);
		JSONObject jsonObj = new JSONObject();
		if ("no".equals(this.toSign))
		{
			PrizeActivityBean pab = (PrizeActivityBean)this.baseBeanService
					.getBeanByHqlAndParams("from PrizeActivityBean where companyId= ? and trim(activityType) = ? and trim(status)= ? ",
							new Object[] { com.getComanyId(), "1", "0" });

			SignScoreBean ssb = (SignScoreBean)this.baseBeanService
					.getBeanByHqlAndParams("from SignScoreBean where activityId=? ", new Object[] { pab.getActivityId() });
			flag = this.bonusPointsService.toSign(this.sccid, this.staffid, this.account, com.getComanyId(), ssb.getScore(), null);
			Company cp = (Company)this.baseBeanService.getBeanByHqlAndParams(" from Company where companyID= ?", new Object[] { com.getComanyId() });
			jsonObj.accumulate("flag", flag);
			jsonObj.accumulate("companyname", cp.getCompanyName());
			jsonObj.accumulate("jiFenNum", ssb.getScore());
		}
		else
		{
			jsonObj.accumulate("flag", this.toSign);
		}
		this.result = jsonObj;
		return "success";
	}
	public String phoneSignShare()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String companyId = request.getParameter("companyId");
		Map<String, Object> map = this.bonusPointsService.isPhoneSignCount(companyId, this.account);
		String signFlag = "";
		for (String str : map.keySet()) {
			if (str == "signFlag") {
				signFlag = map.get(str).toString();
			}
		}
		JSONObject jsonObj = JSONObject.fromObject(map);
		if ("yes".equals(signFlag))
		{
			List<Object> param = new ArrayList<Object>();
			param.add(companyId);
			param.add("1");
			param.add("0");
			PrizeActivityBean pab = (PrizeActivityBean)this.baseBeanService
					.getBeanByHqlAndParams("from PrizeActivityBean where companyId=? and trim(showStatus)=? and trim(status)=? ",
							((List)param).toArray());
			SignScoreBean ssb = (SignScoreBean)this.baseBeanService
					.getBeanByHqlAndParams("from SignScoreBean where activityId=?", new Object[] { pab.getActivityId() });
			this.flag = this.bonusPointsService.toSign(this.sccid, this.staffid, this.account, companyId, ssb.getScore(), "phone");
			Company cp = (Company)this.baseBeanService.getBeanByHqlAndParams(" from Company where companyID= ?", new Object[] { companyId });
			if ("签到成功".equals(this.flag)) {
				jsonObj.accumulate("flag", "积分赠送成功");
			} else {
				jsonObj.accumulate("flag", "积分赠送失败");
			}
			jsonObj.accumulate("companyname", cp.getCompanyName());
			jsonObj.accumulate("jiFenNum", ssb.getScore());
		}
		this.result = jsonObj;
		return "success";
	}

	public String toSignPhone()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String companyId = request.getParameter("companyId");
		String signSite = request.getParameter("localinfo");
		String signInformation = request.getParameter("signinfo");
		String signImagesPath = request.getParameter("picinfo");
		Map<String, Object> map = this.bonusPointsService.saveSign(this.sccid, this.account, companyId, 0,
				signSite, signInformation, signImagesPath);
		JSONObject jsonObj = JSONObject.fromObject(map);
		this.result = jsonObj;
		return "success";
	}
	/**
	 * mz
	 * 终端机签到验证这个账号是否入职过该公司否则无法签到
	 * @return
	 */
	public String toSignValidate(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String companyId = request.getParameter("companyId");
		String re = bonusPointsService.toSignValidate(sccid,account,companyId);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("result", re);
		JSONObject jsonObj = JSONObject.fromObject(map);
		this.result = jsonObj;
		return "success";
	}
	
    /**
    *
    * 人脸验证
    * @return
    */
  public String faceValidate(){
      HttpServletRequest request = ServletActionContext.getRequest();
      String  openid = request.getParameter("openid");
      String  companyId = request.getParameter("companyId");

      Map<String,Object> map = bonusPointsService.faceValidate(openid,companyId);
      JSONObject jo = JSONObject.fromObject(map);
      this.result = jo;
      return "success";
  }
  
  
	/**
	 * 
	 * 绑定微分金账号
	 * 
	 * @return
	 */
	public String bindWfj() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String tel = request.getParameter("tel");
		String openid = request.getParameter("openid");
		String companyId = request.getParameter("companyId");

		Map<String, Object> map = bonusPointsService.bindWfj(openid, tel,
				companyId);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}


	public String signDetail()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String companyId = request.getParameter("companyId");
		String number = request.getParameter("pageNumber");
		String isPerOrCom = request.getParameter("isPerOrCom");
		int pageNumber = 1;
		PageForm pageForm = null;
		JSONObject jo = new JSONObject();
		if ((number != null) || ("".equals(number))) {
			pageNumber = Integer.parseInt(number);
		}
		if ((!"person".equals(isPerOrCom)) && (!"company".equals(isPerOrCom)))
		{
			jo.accumulate("signlist", "操作有误");
		}
		else
		{
			if ("person".equals(isPerOrCom)) {
				pageForm = this.bonusPointsService.signPerDetail(this.sccid, this.account, companyId, pageNumber);
			} else {
				pageForm = this.bonusPointsService.signComDetail(companyId, pageNumber);
			}
			if (pageForm == null)
			{
				jo.accumulate("signlist", "没有数据");
			}
			else
			{
				List<BaseBean> list = pageForm.getList();
				JSONArray jsonArray = new JSONArray();
				for (int i = 0; i < list.size(); i++)
				{
					JSONObject jsonObj = new JSONObject();
					Object[] obj = (Object[])(Object)list.get(i);
					jsonObj.accumulate("dateinfo", obj[0].toString());
					if (obj[1] != null) {
						jsonObj.accumulate("localinfo", obj[1]);
					} else {
						jsonObj.accumulate("localinfo", "");
					}
					if (obj[2] != null) {
						jsonObj.accumulate("signinfo", obj[2]);
					} else {
						jsonObj.accumulate("signinfo", "");
					}
					if (obj[3] != null) {
						jsonObj.accumulate("picinfo", obj[3]);
					} else {
						jsonObj.accumulate("picinfo", "");
					}
					if (obj[4] != null) {
						jsonObj.accumulate("signtype", obj[4]);
					} else {
						jsonObj.accumulate("signtype", "");
					}
					if (obj[5] != null) {
						jsonObj.accumulate("staffname", obj[5]);
					} else {
						jsonObj.accumulate("staffname", "");
					}
					if (obj[6] != null) {
						jsonObj.accumulate("photo", obj[6]);
					} else {
						jsonObj.accumulate("photo", "");
					}
					jsonArray.add(jsonObj);
				}
				jo.accumulate("signlist", jsonArray);
				jo.accumulate("pageNumber", pageForm.getPageNumber());
				jo.accumulate("pageCount", pageForm.getPageCount());
			}
		}
		this.result = jo;
		return "success";
	}
	
	//分享文章获得积分
	public String shareArticle(){
		/*String flag = "";
		flag = bonusPointsService.shareArticle(sccid, staffid, account, ppid);
		JSONObject jsonObj = new JSONObject();
		jsonObj.accumulate("flag", flag);
		result = jsonObj;*/	
		return "success";
	}
	
	//积分详情	
	public String getbpDetail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		bp = (BonusPoints)baseBeanService.getBeanByHqlAndParams("from BonusPoints where sccid=? ", new Object[]{sccid});
		String sql = "select wg.wfj_guize_calc,sum(bdp.bonuspointsdetailscore)" +
				"  from dt_wfj_bonuspoints_detail bdp" +
				"  left join dt_wfj_bonuspoints bp on bp.bonuspointsid = bdp.bonuspointsid" +
				"  left join dt_wfj_guize wg on bdp.wfjguizeid = wg.wfj_guize_id" +
				" where bp.sccid = ?" +
				" group by wg.wfj_guize_calc";
		List<BaseBean> inAndExpList = baseBeanService.getListBeanBySqlAndParams(sql,new	Object[]{sccid});
		Map<String,String> inAndExpMap = new HashedMap<>();
		for (Object o :inAndExpList){
			Object [] objects = (Object[]) o;
			inAndExpMap.put(objects[0].toString(),objects[1].toString());
		}
		request.setAttribute("inexp",inAndExpMap);
		if("charge".equals(charge))
		{
			return "integralRecharge";
		}else
		{
			return "getbpDetail";
		}		
	}
	
	//积分详情列表
	public String DetailList() throws Exception{
		PageForm pageForm = bonusPointsService.getDetailList(pageNumber, sccid, sdate, edate, type, operator,inAndExp,"");
		String name = (String) baseBeanService.getObjectBySqlAndParams("select hs.staffname from dt_hr_staff hs where hs.staffid = ? ",new Object[]{staffid});
		ArrayList list = new ArrayList();
		if(pageForm!=null){
			for (Object o : pageForm.getList()){
				Object [] objects  = (Object[]) o;
				if(objects[6]==null){
					objects[6] = name;
				}
				list.add(objects);
			}
			pageForm.setList(list);
		}
		JSONObject obj = new JSONObject();
		obj.accumulate("pageForm", pageForm);
		result = obj.toString();
		return "success";
	}

	//收支
	public String inAndExp(){
		String sql = "select wg.wfj_guize_calc,sum(bdp.bonuspointsdetailscore)" +
				"  from dt_wfj_bonuspoints_detail bdp" +
				"  left join dt_wfj_bonuspoints bp on bp.bonuspointsid = bdp.bonuspointsid" +
				"  left join dt_wfj_guize wg on bdp.wfjguizeid = wg.wfj_guize_id" +
				" where bp.sccid = ?" +
				" group by wg.wfj_guize_calc";
		List<BaseBean> inAndExpList = baseBeanService.getListBeanBySqlAndParams(sql,new	Object[]{sccid});
		JSONObject obj = new JSONObject();
		obj.accumulate("inAndExp", inAndExpList);
		result = obj.toString();
		return "success";
	}

	//类型列表
	public String typeList(){
		String sql = "select wg.wfj_guize_id,case when wg.wfj_guize_name ='佣金' then nvl(pp.goodsname,wg.wfj_guize_name) when wg.wfj_guize_name !='佣金' then wg.wfj_guize_name end  from dt_wfj_guize wg left join dt_productpackaging pp on wg.wfj_guize_ppid = pp.ppid";
		List<BaseBean> ruleList = baseBeanService.getListBeanBySqlAndParams(sql,new	Object[]{ });
		JSONObject obj = new JSONObject();
		obj.accumulate("ruleList", ruleList);
		result = obj.toString();
		return "success";
	}

	//操作人列表
	public String operatorList(){
		String sql = "select hs.staffid,hs.staffname from dt_OperatorInfo oi left join dt_hr_staff hs  on oi.staffid = hs.staffid where oi.status = ? group by hs.staffid,hs.staffname";
		List<BaseBean> operatorList = baseBeanService.getListBeanBySqlAndParams(sql,new	Object[]{"01" });
		JSONObject obj = new JSONObject();
		obj.accumulate("operatorList", operatorList);
		result = obj.toString();
		return "success";
	}

	//打印
	public String printMsg() throws Exception {
		PageForm pageForm = bonusPointsService.getDetailList(pageNumber, sccid, sdate, edate, type, operator,inAndExp,schType);
		String name = (String) baseBeanService.getObjectBySqlAndParams("select hs.staffname from dt_hr_staff hs where hs.staffid = ? ",new Object[]{staffid});
		ArrayList list = new ArrayList();
		BigDecimal sumMoney = new BigDecimal("0");
		for (Object o : pageForm.getList()){
			Object [] objects  = (Object[]) o;
			Object [] objects1 = new Object[3];
			if(objects[6]==null){
				objects[6] = name;
			}
			objects1[0] = objects[2];
			objects1[1] = objects[1];
			objects1[2] = objects[6];
			sumMoney =  sumMoney.add(new BigDecimal(objects[1].toString()));
			list.add(objects1);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list",list);
		map.put("name",name);
		map.put("sumMoney",sumMoney);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}
	/**
	 * 
	 * 根据终端机编号获取公司信息
	 * @return
	 */
	public String getCurrCompany(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String posNum = request.getParameter("posNum");
		Company company = bonusPointsService.getCompanyByPosNum(posNum);
		request.setAttribute("company", company);
		request.setAttribute("posNum", posNum);
		return "signface";
	}

	public String getSccid() {
		return sccid;
	}
	public void setSccid(String sccid) {
		this.sccid = sccid;
	}
	public String getStaffid() {
		return staffid;
	}
	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}
	
	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}	
	public String getPpid() {
		return ppid;
	}
	public void setPpid(String ppid) {
		this.ppid = ppid;
	}	
	
	public String getToSign() {
		return toSign;
	}
	public void setToSign(String toSign) {
		this.toSign = toSign;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}		
	public String getCcompanyId() {
		return ccompanyId;
	}
	public void setCcompanyId(String ccompanyId) {
		this.ccompanyId = ccompanyId;
	}
	public BonusPoints getBp() {
		return bp;
	}
	public void setBp(BonusPoints bp) {
		this.bp = bp;
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
	public String getKhd() {
		return khd;
	}
	public void setKhd(String khd) {
		this.khd = khd;
	}
	public Integer getJiFenNum() {
		return jiFenNum;
	}
	public void setJiFenNum(Integer jiFenNum) {
		this.jiFenNum = jiFenNum;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getIdentifying() {
		return identifying;
	}
	public void setIdentifying(String identifying) {
		this.identifying = identifying;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getInAndExp() {
		return inAndExp;
	}

	public void setInAndExp(String inAndExp) {
		this.inAndExp = inAndExp;
	}

	public String getSchType() {
		return schType;
	}

	public void setSchType(String schType) {
		this.schType = schType;
	}
}
