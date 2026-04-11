package hy.ea.human.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.CSP;
import hy.ea.bo.human.LogBook;
import hy.ea.bo.human.PayScale;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAppraisal;
import hy.ea.bo.human.vo.CSPPayScaleVO;
import hy.ea.bo.human.wage.PKGold;
import hy.ea.bo.human.wage.PKGoldPool;
import hy.ea.bo.human.wage.PSHistory;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/*
 * yjg
 * 人员考评 StaffAppraisalAction
 */
@Controller
@Scope("prototype")
public class StaffAppraisalAction {
	private static final Logger logger = LoggerFactory.getLogger(StaffAppraisalAction.class);
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	/**
	 * 通过连接符'-'连接 0:奖励工资得分 1:安全金额得分 2:得分(月考评得分) 3:任务得分 4:职责得分 5:特殊人才得分 6:保密金额得分
	 */
	private String result;
	private String parameter;
	private StaffAppraisal staffappraisal;
	private List<BaseBean> logLocklist;
	private CSPPayScaleVO payvo;
	private PageForm pageForm;
	private String startdate;
	private String enddate;
	private int pageNumber;
	private List<BaseBean> beans;

	/*
	 * 保存人员考评
	 */

	@SuppressWarnings("deprecation")
	public String saveStaffAppraisal() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String[] remi = result.split("-");
		// logger.info("值：{}", result);
		beans = new ArrayList<BaseBean>();
		if (null == staffappraisal.getAppraisalID()
				|| "".equals(staffappraisal.getAppraisalID())) {
			staffappraisal.setAppraisalID(serverService
					.getServerID("staffappraisal"));
			parameter = "添加人员考评";
		} else {
			String[] hqllog = { "delete LogBook where logBookID = ? and companyID = ? and staffID=?" };
			Object[] params = { staffappraisal.getAppraisalID(),
					account.getCompanyID(), staffappraisal.getStaffID() };
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null, hqllog,
					params);
			parameter = "修改人员考评";
		}
		String hql2 = "from Staff where staffID=?";
		staffappraisal.setCompanyID(account.getCompanyID());
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { staffappraisal.getStaffID() });
		parameter += "(人员名称:" + staff.getStaffName() + ")";
		beans.add(staffappraisal);
		/*String sql = "select to_char(t.becomesdate,'yyyy-mm-dd') from dtcos c,dtaudition t where c.cosstatus=? and c.status=? and t.status = ? "
				+ " and t.companyid=c.companyid and t.staffid=c.staffid and t.staffid=? and t.companyid=? ";
		List<BaseBean> auditionList = baseBeanService
				.getListBeanBySqlAndParams(sql, new Object[] { "50", "01",
						"22", staffappraisal.getStaffID(),
						account.getCompanyID() });
		if (auditionList.toArray()[0] == null) {
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("message", "请在入职管理处填写当前员工正确的入职时间与转正时间！！！");
			return "fail";
		}
		String becomesdate = auditionList.toArray()[0].toString();
		Date date1 = Utilities.getDateFromString(becomesdate, "yyyy-MM-dd");
*/
		for (int i = 0; i < 11; i++) {
			LogBook logBook = new LogBook();
			logBook.setLogBookID(staffappraisal.getAppraisalID());
			logBook.setCompanyID(account.getCompanyID());
			logBook.setStaffID(staffappraisal.getStaffID());
			logBook.setTodaydate(staffappraisal.getAppraisalDate());
			logBook.setStatus("01");
			/*int number1 = logBook.getTodaydate().compareTo(date1);
			if (number1 == -1) {
				logBook.setLogoStatus("00");// 转正的日志状态（入职前6个月的）
			} else {
				logBook.setLogoStatus("01");// 转正后的日志状态（入职后6个月的）
			}*/
			if (i == 0) {
				logBook.setScoreSort("scode20100812ikdv89y6kt0000000031");
				logBook.setJobContent("奖励得分");
				logBook.setBisect(remi[0]);
			}
			if (i == 1) {
				logBook.setScoreSort("scode20100812ikdv89y6kt0000000030");
				logBook.setJobContent("安全得分");
				logBook.setBisect(remi[1]);
			}
			if (i == 2) {
				logBook.setScoreSort("scode201007306kdf8m76me0000000006");
				logBook.setJobContent("月考评");
				logBook.setBisect(remi[2]);
			}
			if (i == 3) {
				logBook.setScoreSort("scode20100812ikdv89y6kt0000000029");
				logBook.setJobContent("任务得分");
				logBook.setBisect(remi[3]);
			}
			if (i == 4) {
				logBook.setScoreSort("scode201007306kdf8m76me0000000003");
				logBook.setJobContent("职务职责得分");
				logBook.setBisect(remi[4]);
			}
			if (i == 5) {
				logBook.setScoreSort("scode201202157awfwsxchm0000000005");
				logBook.setJobContent("特殊人才得分");
				logBook.setBisect(remi[5]);
			}
			if (i == 6) {
				logBook.setScoreSort("scode201202157awfwsxchm0000000006");
				logBook.setJobContent("保密工资得分");
				logBook.setBisect(remi[6]);
			}
			
			/**************************/
			if (i == 7) {
				logBook.setScoreSort("scode20140116jjp47y7tv80000000002");
				logBook.setJobContent("孝道金得分");
				logBook.setBisect(remi[7]);
			}
			if (i == 8) {
				logBook.setScoreSort("scode20140116jjp47y7tv80000000003");
				logBook.setJobContent("竞职金得分");
				logBook.setBisect(remi[8]);
			}
			if (i == 9) {
				logBook.setScoreSort("scode20140116jjp47y7tv80000000005");
				logBook.setJobContent("通讯补助得分");
				logBook.setBisect(remi[9]);
			}
//			if (i == 10) {
//				logBook.setScoreSort("scode20140116jjp47y7tv80000000004");
//				logBook.setJobContent("PK金得分");
//				logBook.setBisect(remi[10]);
//			}
			if (i == 10) {
				logBook.setScoreSort("scode20140116jjp47y7tv80000000006");
				logBook.setJobContent("生活补助得分");
				logBook.setBisect(remi[10]);
			}
			beans.add(logBook);
		}
		
		/**
		 * pk金
		 */
		depkBYD(account, staffappraisal.getStaffID(), staffappraisal.getAppraisalDate().toLocaleString().split(" ")[0]);
		String sql = "select distinct(s.staffID),s.staffname,o.organizationid,o.organizationname,d.deppostid,d.postname,p.pkpay" +
				" from dtcos c left join dt_hr_staff s on c.staffid = s.staffid" +
				" left join dtaudition a on c.staffid = a.staffid" +
				" left join dt_hr_deptpost d on c.depPostID = d.depPostID" +
				" left join dtcorganization o on c.organizationID = o.organizationID " +
				" left join dtCsp csp on c.staffid = csp.staffid" +
				" left join dtPayScale p on  csp.payscaleid = p.payscaleid " +
				" where c.companyid = ?" +
				" and c.cosStatus = ?" +
				" and c.status = ?" +
				" and a.status = ?" +
				" and c.staffid = ?"+
				" and p.companyid = ?";
		Object[] o = (Object[])baseBeanService.getObjectBySqlAndParams(sql,  new Object[]{account.getCompanyID(),"50","01","22",staffappraisal.getStaffID(),account.getCompanyID()});
		PKGold pkgold = new PKGold();
		pkgold.setPkgoldID(serverService.getServerID("pkgold"));
		pkgold.setCompanyID(account.getCompanyID());
		pkgold.setCompanyName(account.getCompanyName());
		pkgold.setStaffID(o[0].toString());
		pkgold.setStaffName(o[1].toString());
		if(o[2] != null)
			pkgold.setOrganizationID(o[2].toString());
		if(o[3] != null)
			pkgold.setOrganizationName(o[3].toString());
		if(o[4] != null)
			pkgold.setDepID(o[4].toString());
		if(o[5] != null)
			pkgold.setDepName(o[5].toString());
		if(o[6] != null)
			pkgold.setGold(o[6].toString());
		pkgold.setStatus("00");
		String dt = staffappraisal.getAppraisalDate().toLocaleString().split(" ")[0];
		//dt 当前时间月份
		dt = dt.split("-")[0]+"-"+dt.split("-")[1];
		pkgold.setPkDate(dt);
		beans.add(pkgold);
		//pk汇总
		String hql = "from PKGoldPool p where p.companyID = ? and p.pkDate like ?";
		String d2 = staffappraisal.getAppraisalDate().toLocaleString().split(" ")[0]; //2012-1-1
		
		if(d2.split("-")[1] == "1"){
			d2 =(Integer.parseInt(d2.split("-")[0])-1) + "-12";
		}else{
			d2 = d2.split("-")[0] + "-" + (Integer.parseInt(d2.split("-")[1]) - 1);
		}
		//d2 上月时间月份
		//上月
		PKGoldPool pools = (PKGoldPool)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),"%"+d2+"%"});
		//本月

		PKGoldPool pool = (PKGoldPool)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),"%"+dt+"%"});
		if(pool == null){
			PKGoldPool gp = new PKGoldPool();
			gp.setCompanyID(account.getCompanyID());
			gp.setCompanyName(account.getCompanyName());
			gp.setPkDate(dt);
			gp.setPkgoldpoolID(serverService.getServerID("pkgoldpool"));
//			总额
			gp.setGoldpool(pkgold.getGold());
//			支出
			gp.setGoldpaypool("0");
//			余额
			if(pools != null){
				gp.setGoldbalpool(Integer.parseInt(pkgold.getGold()) + Integer.parseInt(pools.getGoldbalpool())+"");
			}else{
				gp.setGoldbalpool(pkgold.getGold());
			}
			beans.add(gp);
		}else{
//			总额
			pool.setGoldpool(Integer.parseInt(pkgold.getGold())+Integer.parseInt(pool.getGoldpool())+"");
//			余额
			pool.setGoldbalpool(Integer.parseInt(pkgold.getGold())+Integer.parseInt(pool.getGoldbalpool())+"");
			beans.add(pool);
		}
		
			
		CLogBook log = logBookService.saveCLogBook(null, parameter, account);
		beans.add(log);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return getListStaffAppraisal();
	}

	/*
	 * 删除人员考评
	 */

	public String delStaffAppraisal() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		//删除pk
		depkBYID(account,staffappraisal.getStaffID(),staffappraisal.getAppraisalID());
		String hql = "delete StaffAppraisal where appraisalID= ? and companyID = ? and staffID=?";
		Object[] params = { staffappraisal.getAppraisalID(),
				account.getCompanyID(), staffappraisal.getStaffID() };
		String hqllog = "delete LogBook where logBookID = ? and companyID = ? and staffID=?";
		String[] hqls = { hql, hqllog };
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { staffappraisal.getStaffID() });
		CLogBook logBook = logBookService.saveCLogBook(null, "删除人员考评(人员名称："
				+ staff.getStaffName() + ")", account);
		beans = new ArrayList<BaseBean>();
		beans.add(logBook);
		baseBeanService
				.saveBeansListAndexecuteHqlsByParams(beans, hqls, params);
		return "success";
	}
	/**
	 * 根据id删除pk金
	 */
	@SuppressWarnings("deprecation")
	public void depkBYID(CAccount account,String staffID,String appid){
		String hql = "from StaffAppraisal a where a.appraisalID = ?";
		StaffAppraisal app = (StaffAppraisal)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{appid});
		String d = app.getAppraisalDate().toLocaleString();
		d = d.split(" ")[0];
		d = d.split("-")[0]+"-"+Integer.parseInt(d.split("-")[1]);
		String hql1 = "from PKGold g where g.companyID = ? and g.staffID = ? and g.pkDate like ?";
		PKGold pkg = (PKGold)baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{account.getCompanyID(),staffID,"%"+d+"%"});
		if(pkg != null){
			String hql2 = "from PKGoldPool g where g.companyID = ? and g.pkDate like ?";
			PKGoldPool pkgp = (PKGoldPool)baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),"%"+d+"%"});
			pkgp.setGoldpool(Integer.parseInt(pkgp.getGoldpool())-Integer.parseInt(pkg.getGold())+"");
			pkgp.setGoldbalpool(Integer.parseInt(pkgp.getGoldbalpool())-Integer.parseInt(pkg.getGold())+"");
			String dhql = "delete from PKGold g where g.companyID = ? and g.staffID = ? and g.pkDate like ?";
			beans = new ArrayList<BaseBean>();
			beans.add(pkgp);
			List<Object[]> parms = new ArrayList<Object[]>();
			parms.add(new Object[]{account.getCompanyID(),staffID,"%"+d+"%"});
			baseBeanService.executeHqlsByParamsList(beans, new String[]{dhql}, parms);
		}
	}
	/**
	 * 根据时间删除pk金
	 * @return
	 */
	public void depkBYD(CAccount account,String staffID,String d){
		d = d.split("-")[0]+"-"+Integer.parseInt(d.split("-")[1]);
		String hql1 = "from PKGold g where g.companyID = ? and g.staffID = ? and g.pkDate like ?";
		PKGold pkg = (PKGold)baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{account.getCompanyID(),staffID,"%"+d+"%"});
		if(pkg != null){
			String hql2 = "from PKGoldPool g where g.companyID = ? and g.pkDate like ?";
			PKGoldPool pkgp = (PKGoldPool)baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),"%"+d+"%"});
			pkgp.setGoldpool(Integer.parseInt(pkgp.getGoldpool())-Integer.parseInt(pkg.getGold())+"");
			pkgp.setGoldbalpool(Integer.parseInt(pkgp.getGoldbalpool())-Integer.parseInt(pkg.getGold())+"");
			String dhql = "delete from PKGold g where g.companyID = ? and g.staffID = ? and g.pkDate like ?";
			ArrayList<BaseBean> beanses = new ArrayList<BaseBean>();
			beanses.add(pkgp);
			List<Object[]> parms = new ArrayList<Object[]>();
			parms.add(new Object[]{account.getCompanyID(),staffID,"%"+d+"%"});
			baseBeanService.executeHqlsByParamsList(beanses, new String[]{dhql}, parms);
		}
	}
	
	/*
	 * 得到人员考评列表
	 */

	public String getListStaffAppraisal() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (startdate == null || enddate == null || startdate.equals("")
				|| enddate.equals("")) {
			Object[] params = { account.getCompanyID(),
					staffappraisal.getStaffID() };
			pageForm = baseBeanService
					.getPageForm(
							(null != pageForm ? pageForm.getPageNumber() : 1),
							(pageNumber == 0 ? 4 : pageNumber),
							" from StaffAppraisal where companyID = ? and staffID=? order by appraisalDate desc",
							params);
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Object[] params = { account.getCompanyID(),
						staffappraisal.getStaffID(),
						dateFormat.parse(startdate), dateFormat.parse(enddate) };
				pageForm = baseBeanService
						.getPageForm(
								(null != pageForm ? pageForm.getPageNumber()
										: 1),
								(pageNumber == 0 ? 4 : pageNumber),
								" from StaffAppraisal where companyID = ? and staffID=? and appraisalDate between ? and ? order by appraisalDate desc",
								params);
			} catch (ParseException e) {
				logger.error("操作异常", e);
			}
		}
		return "list";
	}

	/*
	 * 导出人员考评
	 */

	public String showExcel() throws ParseException {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<BaseBean> list;
		if (startdate == null || enddate == null || startdate.equals("")
				|| enddate.equals("")) {
			Object[] params = { account.getCompanyID(),
					staffappraisal.getStaffID() };
			String hql = "from StaffAppraisal where companyID = ? and staffID=?";
			list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Object[] params = { account.getCompanyID(),
					staffappraisal.getStaffID(), dateFormat.parse(startdate),
					dateFormat.parse(enddate) };
			String hql = " from StaffAppraisal where companyID = ? and staffID=? and appraisalDate>=? and appraisalDate<=?";
			list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		}
		excelStream = excelService.showExcel(StaffAppraisal.columnHeadings(),
				list);
		String organizationID = (String) session.get("organizationID");
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出人员考评", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * Ajax 查询员工级别
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String getCSPayScale() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}

		String hql_pshistory = "from PSHistory where companyID = ? and staffID = ? and status = ? ";
		PSHistory PSHistoryHtr = (PSHistory) baseBeanService
				.getBeanByHqlAndParams(hql_pshistory, new Object[] {
						account.getCompanyID(), staffappraisal.getStaffID(),
						"03" });
		if (PSHistoryHtr != null) {
			// 级别生效月份等于当前月（不存在级别生效月份小于当前月份）
			if (PSHistoryHtr.getEffectiveDate().getMonth() == new Date()
					.getMonth()) {
				String[] hql_forDel = null;
				List<Object[]> pars = new ArrayList<Object[]>();
				List<BaseBean> beans = new ArrayList<BaseBean>();

				hql_forDel = new String[] { "update PSHistory set status = ? where companyID = ? and staffID = ?  and status = ? " };
				Object[] obj = new Object[] { "02", account.getCompanyID(),
						staffappraisal.getStaffID(), "01" };
				pars.add(obj);

				String hqlCsp = " from CSP cs where cs.companyID=? and cs.staffID=?";
				CSP csp = (CSP) baseBeanService.getBeanByHqlAndParams(hqlCsp,
						new Object[] { account.getCompanyID(),
								staffappraisal.getStaffID() });
				csp.setPayScaleID(PSHistoryHtr.getPayScaleID());
				beans.add(csp);

				PSHistoryHtr.setStatus("01");
				beans.add(PSHistoryHtr);
				baseBeanService
						.executeHqlsByParamsList(beans, hql_forDel, pars);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == staffappraisal.getPayScaleID()
				|| "".equals(staffappraisal.getPayScaleID())) {
			payvo = (CSPPayScaleVO) baseBeanService.getBeanByHqlAndParams(
					"from CSPPayScaleVO where companyID = ? and staffID = ?",
					new Object[] { account.getCompanyID(),
							staffappraisal.getStaffID() });
		} else {
			// logger.info("调试信息");
			PayScale payScale = (PayScale) baseBeanService
					.getBeanByHqlAndParams(
							"from PayScale where  payScaleID = ? ",
							new Object[] { staffappraisal.getPayScaleID() });
			payvo = null;
			payvo = new CSPPayScaleVO();
			payvo.setScale(payScale.getScale());
			payvo.setPositionPay(payScale.getPositionPay());
			payvo.setPushMoney(payScale.getPushMoney());
			payvo.setTimingMoney(payScale.getTimingMoney());
			payvo.setAwardPay(payScale.getAwardPay());
			payvo.setStPay(payScale.getStPay());
			payvo.setSecrecyPay(payScale.getSecrecyPay());
			payvo.setSafetyAward(payScale.getSafetyAward());
		}

		map.put("payvo", payvo);
		map.put("logLocklist", logLocklist);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public StaffAppraisal getStaffappraisal() {
		return staffappraisal;
	}

	public void setStaffappraisal(StaffAppraisal staffappraisal) {
		this.staffappraisal = staffappraisal;
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

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<BaseBean> getLogLocklist() {
		return logLocklist;
	}

	public void setLogLocklist(List<BaseBean> logLocklist) {
		this.logLocklist = logLocklist;
	}

}
