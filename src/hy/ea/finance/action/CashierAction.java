package hy.ea.finance.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.vo.ContactUser;
import hy.ea.bo.driving.DrivingDeal;
import hy.ea.bo.driving.DtDrivingAllInformation;
import hy.ea.bo.driving.DtDrivingPrincipal;
import hy.ea.bo.driving.DtDrivingPrincipalType;
import hy.ea.bo.driving.Studentarchives;
import hy.ea.bo.finance.BillCheck;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.RelatedBill;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
//import hy.ea.bo.onlineExam.CarStudent;
import hy.ea.service.CLogBookService;
import hy.ea.util.DateJsonValueProcessor;
import hy.ea.util.DateUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.opensymphony.xwork2.ActionContext;

/**
 * 待出纳审核管理：CashierAction
 * 
 * @author cxf
 * 
 */
public class CashierAction {
	private static final Logger logger = LoggerFactory.getLogger(CashierAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;

	@Resource
	private ServerService serverService;

	private String result;
	private String cashierBillsID;
	private CashierBills cashierBills;
	private CashierBillsVO cashierBillsVO;

	private String billTypese;  //生成的单据类别

	/**
	 * 标志审核学员是否转科一培训
	 */
	private String statusForPerson;


	/**
	 * 
	 * 
	 * 生成销售明细单
	 * @return
	 */
	public String generateSaleSheet(){
		List<BaseBean> beans = new ArrayList<BaseBean>();
		String hql = "from CashierBills where cashierBillsID = ?";
		CashierBills cb = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{cashierBills.getCashierBillsID()});
        
	
		if(cb.getBillsType().equals("预入款单")||cb.getBillsType().equals("欠款单")){
			if(cb.getStatusbill().equals("00")){
				saveCasherbill(cb,billTypese.equals("采购单")?"22":"46",billTypese,beans);
			}
		}
		
		//生成销售出库单
	
		if(cb.getBillsType().equals("收款单")){
			if(cb.getStatusbill().equals("01")){
				saveCasherbill(cb,"22","销售出库单",beans);
				cb.setStatus("07");
				beans.add(cb);
				
				try {
					/*处理  主项目名称 为 "订单管理"  子项目名称 为 "学员收费" 的项目预算单据  （）*/
					addBaoMingOfStudentByCostSheet(cb,null,beans);
					/*处理  主项目名称 为 "订单管理"  子项目名称 为 "学员收费" 的项目预算单据  （报开学）*/
					addBaoKaiXueOfStudentByCostSheet(cb,null,beans);
				} catch (Exception e) {
					ActionContext.getContext().put("message", "数据错误！！请联系管理员");
					return "fail";
				}
			}
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	
	}
	
	/**
	 * 生成单据及物品信息
	 * @param cb 前单据信息
	 * @param status 单据
	 * @param billstype
	 * @param beans
	 * @return
	 */
	public String saveCasherbill(CashierBills cb,String status,String billstype,List<BaseBean> beans){
		try {
			/**********生成单据数据********/
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			CashierBills cashierBills=(CashierBills)cb.cloneCashierBills();
			cashierBills.setCashierBillsID(serverService.getServerID("cashierTally"));
			cashierBills.setCashierBillsKey(null);
			if("生产资金申请单".equals(cb.getRemark())){
				billstype="生产采购单";
				status="12";
				cashierBills.setType("01");
				cashierBills.setFiveClear("p20160810KG86S4SMQT0000003644");
			}
			cashierBills.setBillsType(billstype);
			cashierBills.setJournalNum(serverService.getBillID(account.getCompanyID()));
			cashierBills.setStatus(status);
			cashierBills.setCashierDate(new Date());
			beans.add(cashierBills);
			
			/**********生成采购、出库子单据数据********/
			if(billstype.equals("采购单")||billstype.equals("销售出库单")||billstype.equals("生产采购单")){
				saveFinbill(cashierBills,status,billstype,beans);
			}
			
			// 保存与预算单据关联单据单据表信息
			RelatedBill relatedBill2 = new RelatedBill();
			relatedBill2.setRbID(serverService.getServerID("relatedbill"));
			relatedBill2.setCashid(cb.getCashierBillsID());
			relatedBill2.setCashfid(cashierBills.getCashierBillsID());
			relatedBill2.setBilltype(cashierBills.getBillsType());
			beans.add(relatedBill2);
			
			/**********生成采购物品数据********/
			List<BaseBean> goodlist=baseBeanService.getListBeanByHqlAndParams("from GoodsBills where cashierBillsID=?", new Object []{cb.getCashierBillsID() });
			for (int i = 0; i < goodlist.size(); i++) {
				GoodsBills goodsBills=(GoodsBills)goodlist.get(i);
				GoodsBills goodsBills2=null;
				try {
					goodsBills2=(GoodsBills)goodsBills.cloneGoodsBills(); 
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					logger.error("操作异常", e);
				}
				goodsBills2.setCashierBillsID(cashierBills.getCashierBillsID());
				goodsBills2.setGoodsBillsID(serverService.getServerID("goodsbills"));
				goodsBills2.setPlanGoodsBillsID(goodsBills.getGoodsBillsID());
				goodsBills2.setGoodsBillsKey(null);
				goodsBills2.setIdentifyingCode("00");//00为单据原始物品
				beans.add(goodsBills2);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}
	
	/**
	 * 保存单据子表（库存单据表）
	 * @param cb 单据表
	 * @param status 单据状态
	 * @param billstype  单据类别
	 * @param beans
	 * @return
	 */
	public void saveFinbill(CashierBills cb,String status,String billstype,List<BaseBean> beans){
		FinancialBill financialBill=new FinancialBill();
		financialBill.setFinancialbillID(serverService.getServerID("financial"));
		financialBill.setCashierBillsID(cb.getCashierBillsID());          //出纳单单据外键
		financialBill.setGroupCompanySn(cb.getGroupCompanySn());     		//集团公司的标识  外键
		financialBill.setCompanyID(cb.getCompanyID());          		//公司  外键
		financialBill.setOrganizationID(cb.getOrganizationID());     		//单据所在部门  外键
		financialBill.setDepartmentID(cb.getDepartmentID());       		//子部门ID(限定部门单据) 外键
		financialBill.setBillsType(billstype);           //单据类型
		financialBill.setJournalNum(cb.getJournalNum());          //凭证号（单据编号）
		financialBill.setBillsDate(new Date());           //制单日期
		financialBill.setBillStaffID(cb.getInputid());         //制单人ID  外键
		financialBill.setBillStaffName(cb.getInputName());       //制单人名称
		financialBill.setBillStatus(status);
		beans.add(financialBill);
	}

	/**
	 * 判断是否生成采购单
	 */
	public String IsGenerate(){

        int count =  baseBeanService.getConutByBySqlAndParams("select count(*) from Dtrelatedbill r where r.cashid = ? and r.billtype  = ? ",new Object[]{cashierBillsID,"采购单"});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("count", count);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

	
	/**
	 * JSON查询审核信息
	 * @return
	 */
	public String AjxaGetCheckbill(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql="from BillCheck where newBillsID=?";
		BillCheck bCheck=(BillCheck)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cashierBillsID.substring(0,40)});
		Map<String, Object> map=new HashMap<String, Object>();
		if(bCheck!=null){
			String hql2="from BillCheck where firstBillsID=? order by audittime desc";
			List<BaseBean> checkList=baseBeanService.getListBeanByHqlAndParams(hql2, new Object[]{bCheck.getFirstBillsID()});
			map.put("checkList", checkList);
		}else{
			map.put("checkList", null);
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject obj = JSONObject.fromObject(map,jsonConfig);
		result = obj.toString();
		return "success";
	}

	/**
	 * @time 2015-01-09
	 * @describe 处理  主项目名称 为 "订单管理"  子项目名称 为 "学员收费" 的项目预算单据  （报名缴费）
	 * @return
	 */
	public void addBaoMingOfStudentByCostSheet(CashierBills cb,Map<String, GoodsBills> lbp,List<BaseBean> baseBeanList ){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(cb!=null&&baseBeanList!=null){
			if("生产项目".equals(cb.getXmtypename())&&"收款单".equals(cb.getBillsType())){
				
				List<BaseBean> goodsBills=baseBeanService.getListBeanByHqlAndParams(" from GoodsBills gb where gb.cashierBillsID=?", new Object[]{cb.getCashierBillsID()});
				for (BaseBean baseBean : goodsBills) {
					GoodsBills gb=(GoodsBills)baseBean;
					CCode code;
					try {
						code = (CCode)baseBeanService.getBeanByHqlAndParams(" from CCode where companyID=? and codePID=? and codeValue like ?", new Object[]{account.getCompanyID(),"scode201211095p8932ixtz0000000014","%"+gb.getGoodsName()+"%"});
					} catch (Exception e) {
						code=null;
					}
					if(code!=null &&gb.getConnectID()!=null){
					
						ContactUser cu=(ContactUser)baseBeanService.getBeanByHqlAndParams(" from ContactUser where companyID=? and relation=? and relationID=?  ", new Object[]{account.getCompanyID(),"学员",gb.getConnectID()});
						/*新增学员帐号*/
						//addAccountOfStudentOfpracticeByCashier(cu,baseBeanList);
						/*新增学员收费信息*/
						addCostOfStudentOfDriverByCashier(gb,cu,baseBeanList);	
						/*新增学员客户信息*/
						addCustomerOfStudentOfDriverByCashier(gb,cu,baseBeanList);
						/*新增学员教务生产基本信息*/
						addProductionDataOfStudentOfDriverByCashier(cb, gb, cu,baseBeanList);
					
					}
				}
			}
		}
	}
	
	/**
	 * @time 2015-01-07
	 * @describe 处理  主项目名称 为 "订单管理"  子项目名称 为 "学员收费" 的项目预算单据  （报开学）
	 * @return void
	 */
	public void addBaoKaiXueOfStudentByCostSheet(CashierBills cashierBills,Map<String, GoodsBills> logbookmap,List<BaseBean> baseBeanList ){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(cashierBills!=null&&logbookmap!=null&&baseBeanList!=null){
			if("生产项目".equals(cashierBills.getXmtypename())&&"支款单".equals(cashierBills.getBillsType())){
				List<BaseBean> goodsBills_List=baseBeanService.getListBeanByHqlAndParams(" from GoodsBills gb where gb.cashierBillsID=?", new Object[]{cashierBills.getCashierBillsID()});
				for (BaseBean baseBean : goodsBills_List) {
					GoodsBills goodsBills=(GoodsBills)baseBean;
					if(goodsBills.getGoodsName().contains("报开学")&&goodsBills.getConnectID()!=null){
						ContactUser cu=(ContactUser)baseBeanService.getBeanByHqlAndParams(" from ContactUser where companyID=? and relation=? and relationID=?  ", new Object[]{account.getCompanyID(),"学员",goodsBills.getConnectID()});
						DtDrivingPrincipal dp=(DtDrivingPrincipal)baseBeanService.getBeanByHqlAndParams(" from DtDrivingPrincipal where studentid=?", new Object[]{cu.getStaffID()});
						dp.setStudentCode(goodsBills.getStudentCode());
						dp.setStudentPeriods(goodsBills.getStudentPeriods());
						dp.setSchoolopendate(goodsBills.getSchoolopendate());
						baseBeanList.add(dp);
						DtDrivingPrincipalType dpt=(DtDrivingPrincipalType)baseBeanService.getBeanByHqlAndParams(" from DtDrivingPrincipalType where docstatus=? and  drivingprincipalid=?", new Object[]{"01",dp.getDrivingprincipalid()});
						dpt.setStudentstatus("05");
						baseBeanList.add(dpt);
					}
				}
			}
		}
	}
	/**
	 * @time 2015-01-09
	 * @author zc
	 * @describe 新增学员在线科目练习题帐号
	 * @return
	 */
	/*public void addAccountOfStudentOfpracticeByCashier(ContactUser cu,List<BaseBean> beans){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		CarStudent cs = (CarStudent) baseBeanService.getBeanByHqlAndParams("from  CarStudent  where companyID = ?  and studentUser = ? ", new Object[]{account.getCompanyID(),cu.getStaffIdentityCard() });
		if (cs == null) {
			CarStudent cs1 = new CarStudent();
			cs1.setCarStudentId(serverService.getServerID("carStudent"));
			cs1.setCompanyId(account.getCompanyID());
			cs1.setStudentUser(cu.getStaffIdentityCard());
			cs1.setStudentName(cu.getStaffName());
			cs1.setStudentPassword("00000000");
			cs1.setCarState("00");
			beans.add(cs);
		}	
	}*/
	/**
	 * @time 2015-01-09
	 * @author zc
	 * @describe 新增学员收费信息
	 * @return
	 */
	public void addCostOfStudentOfDriverByCashier(GoodsBills gb,ContactUser cu,List<BaseBean> beans){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DtDrivingAllInformation dtDrivingAllInformation = new DtDrivingAllInformation();
		dtDrivingAllInformation
				.setDrivingAllInformationID(serverService
						.getServerID("dtDrivAllInform"));
		dtDrivingAllInformation.setCashierBillsID(gb.getCashierBillsID());
		dtDrivingAllInformation.setCompanyID(account
				.getCompanyID());
		dtDrivingAllInformation.setStaffID(cu
				.getStaffID());
		dtDrivingAllInformation.setCodeID(gb
				.getGoodsID());
		dtDrivingAllInformation.setCodeValue(gb
				.getGoodsName());
		dtDrivingAllInformation.setChargeName(gb
				.getGoodsName());
		dtDrivingAllInformation.setChargeMoney(Double
				.parseDouble(gb.getMoney()==null?"0":gb.getMoney()));
		dtDrivingAllInformation.setChargeTime(new Date());
		dtDrivingAllInformation.setDataTitle("05");
		beans.add(dtDrivingAllInformation);
		
	}
	/**
	 * @time 2015-01-09
	 * @author zc
	 * @describe 新增学员预订信息或成交信息
	 * @return
	 */
	public void addCustomerOfStudentOfDriverByCashier(GoodsBills gb,ContactUser cu,List<BaseBean> beans){
		CAccount account= (CAccount) ActionContext.getContext().getSession().get("account");
		DrivingDeal beanDrivingDeal = (DrivingDeal) baseBeanService.getBeanByHqlAndParams(" from DrivingDeal where companyID = ? and staffID=?  ",
						new Object[] { account.getCompanyID(),cu.getStaffID() });// ----查询当前学员
		if (beanDrivingDeal == null) {// 00 预订客户  01成交客户
			beanDrivingDeal = new DrivingDeal();
			beanDrivingDeal.setDrivingDealid(serverService.getServerID("DrivingDeal"));
			beanDrivingDeal.setCompanyid(account.getCompanyID());
			beanDrivingDeal.setStaffID(cu.getStaffID());
			beanDrivingDeal.setStates("01");
			beans.add(beanDrivingDeal);
		}
	}
	/**
	 * @time 2015-01-09
	 * @author zc
	 * @describe 新增学员教务生产基本信息
	 * @return
	 */
	public void addProductionDataOfStudentOfDriverByCashier(CashierBills cb,GoodsBills gb,ContactUser cu,List<BaseBean> beans){
		CAccount account= (CAccount) ActionContext.getContext().getSession().get("account");
		/**开始 -------------收集-学员主表信息------------ */
		DtDrivingPrincipal dtDrivingPrincipal = new DtDrivingPrincipal();
		dtDrivingPrincipal.setDrivingprincipalid(serverService.getServerID("dtDrivingPrincipal"));
		dtDrivingPrincipal.setCompanyid(account.getCompanyID());
		dtDrivingPrincipal.setOrganizationID(cb.getDepartmentID());
		dtDrivingPrincipal.setResponsiblePersonsID(cb.getStaffID());

		// ----开始-------根据 财务单据-》物品单据
		// 获得学员培训证件类型------待定---//
		dtDrivingPrincipal.setRegistrationcarid(gb.getGoodsID());
		dtDrivingPrincipal.setRegistrationcarname(gb.getGoodsName());
		// ----结束-------根据 财务单据-》物品单据 获得学员培训证件类型---------//

		dtDrivingPrincipal.setRegistrationdate(new Date());// 报名时间
		dtDrivingPrincipal.setOperationdate(new Date());// 操作时间
		dtDrivingPrincipal.setResgistrationmedicaldate(new Date());// 体检时间
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=? ",new Object[] { cu.getStaffID() });
		dtDrivingPrincipal.setStudentid(staff.getStaffID());
		dtDrivingPrincipal.setStudentname(staff.getStaffName());
		dtDrivingPrincipal.setStudentsex(staff.getSex());
		dtDrivingPrincipal.setStudentphone(staff.getReference());
		dtDrivingPrincipal.setStudentcard(staff.getStaffIdentityCard());
		dtDrivingPrincipal.setCompanygroupid(staff.getGroupCompanySn());
		beans.add(dtDrivingPrincipal);
		/**结束 -------------收集-学员主表信息------------ */
		
		/**开始 -------------操作日志记录------------ */
		CLogBook logBook1 = logBookService.saveCLogBook(cb.getOrganizationID(), "添加学员收集", account);
		beans.add(logBook1);
		/**结束 -------------操作日志记录------------ */
		
		/**开始 -------------科目 一二三四 关系表------------ */
		DtDrivingPrincipalType dtDringPrialType = new DtDrivingPrincipalType();
		dtDringPrialType.setPrincipaltypeid(serverService.getServerID("principaltype"));
		dtDringPrialType.setDrivingprincipalid(dtDrivingPrincipal.getDrivingprincipalid());
		dtDringPrialType.setCompanyid(account.getCompanyID());
		dtDringPrialType.setTestsum("0");
		dtDringPrialType.setDocstatus("01");
		dtDringPrialType.setStudentstatus("04");
		dtDringPrialType.setInputTime(new Date());
		beans.add(dtDringPrialType);
		/**结束 -------------科目 一二三四 关系表------------ */
		
		/**开始 -------------学生归档档案生成------------ */
		//学生归档档案生成
		Studentarchives sas = new Studentarchives();
		sas.setStudentarchivesid(serverService.getServerID("studentarchive"));
		sas.setCompanyid(account.getCompanyID());
		sas.setStaffid(dtDrivingPrincipal.getStudentid());
		sas.setDrivingprincipalid(dtDrivingPrincipal.getDrivingprincipalid());
		sas.setUname(account.getAccountEmail());
		sas.setUtime(new Date());
		beans.add(sas);
		/**结束 -------------学生归档档案生成------------ */
		
		//招生数量计件
		//addOrUpdatePieceworkOfAdmissions( dtDrivingPrincipal, dtDringPrialType,gb,beans);
		
	}
	
	/**
	 * @author zc
	 * @time 2015-01-21
	 * @describe 招生数量计件
	 * @param //DtDrivingPrincipal
	 * @param //DtDrivingPrincipalType
	 * @param //GoodsBills
	 * @param //List
	 * @return
	 */
	public void  addOrUpdatePieceworkOfAdmissions(DtDrivingPrincipal ddp,DtDrivingPrincipalType ddpt,GoodsBills gb1,List<BaseBean> beans){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String organizationName = (String) session.get("organizationName");
		/**start 人事计件工资比例计算*/
		String yyyyCofDate=DateUtil.getCurrentDate("yyyy");
		String goodsName=ddp.getRegistrationcarname();
		//String deppostID= and deppostID=? 岗位暂未做查询条件
		String jjCodeName="招生";
		String money_wcjj="原来查询内容删除了.计件需要重新规划需求";//原来查询内容删除了.计件需要重新规划需求
		/**end 人事计件工资比例计算*/
		
		/**start 单据*/
		CashierBills cb=new CashierBills();
		cb.setCashierBillsID(serverService.getServerID("cashierTally"));
		
		cb.setBillsType("项目支出预算单");
		
		cb.setGroupCompanySn((String)session.get("groupCompanySn"));
		cb.setCompanyID(account.getCompanyID());
		cb.setCompanyName(account.getCompanyName());
		cb.setCashierDate(new Date());
		//根据登陆账号信息  设置单据部门ID与NAME与CODE
		cb.setDepartmentID(organizationID);
		cb.setDepartmentName(organizationName);
		
		cb.setOrganizationID(organizationID);
		
		//根据登陆账号信息  设置单据制单人ID与NAME
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		cb.setInputName(sta.getStaffName());
		cb.setInputid(account.getStaffID());
		//根据登陆账号信息  设置单据录入机构ID与NAME
		cb.setInputOrganizationID(organizationID);
	    cb.setInputOrganizationName(organizationName);
		
	    cb.setInputCompanyid(account.getCompanyID());
	    cb.setInputCompanyName(account.getCompany().getCompanyName());
	    cb.setJournalNum(serverService.getBillID(account.getCompanyID()));
	    
	    /**
	     * 项目名称与项目code与项目ID都为固定设置 实际使用应区分公司
	     */
		cb.setProID("proid201501223I3KHYYMGQ0000000004");
		cb.setProjectCode("0031");
		cb.setProjectName("招生计件");
		
		cb.setRemark("招生计件");
		//根据登陆账号信息  设置单据责任人ID与NAME与CODE
		cb.setStaffID(sta.getStaffID());
		cb.setStaffCode(sta.getStaffCode());
		cb.setStaffName(sta.getStaffName());
		cb.setStatus("00");//未审核
		
		/**
	     * 项目分类与项目分类code都为固定设置 实际使用应区分公司
	     */
		cb.setXmtype("043");
		cb.setXmtypename("综合办证管理");
		
		cb.setZctype("cg");
		/**end 单据*/
		
		/**start 物品明细*/
		GoodsBills gb=new GoodsBills();
		gb.setCashierBillsID(cb.getCashierBillsID());
		gb.setCcompanyID(account.getCompanyID());
		gb.setCcompanyName(account.getCompanyName());
		gb.setCompanyID(account.getCompanyID());
		//根据学员ID  设置往来个人ID与NAME
		ContactUser cu=(ContactUser)baseBeanService.getBeanByHqlAndParams(" from ContactUser where companyID=? and relation=? and staffID=?  ", new Object[]{account.getCompanyID(),"学员",ddp.getStudentid()});
		
		gb.setConnectID(cu.getRelationID());
		gb.setConnectName(cu.getStaffName());
		
		gb.setEndDate(new Date());
		gb.setGoodsBillsID(serverService.getServerID("goodsbills"));
		
		gb.setGoodsID(ddp.getRegistrationcarid());
		gb.setGoodsName(ddp.getRegistrationcarname());
		gb.setGoodsNum("");
		gb.setGoodsVariableID("");
		
		gb.setMoney(money_wcjj);
		gb.setPrice(money_wcjj);
		gb.setPriceManage("人民币");
		gb.setQuantity("1");
		
		gb.setStudentCode(ddp.getStudentCode());
		gb.setStudentPeriods(ddp.getStudentPeriods());
		
		
		gb.setTargetDeptID(gb1.getTargetDeptID());
		gb.setTargetDeptName(gb1.getTargetDeptName());
		
		gb.setTargetStart(new Date());
		gb.setTargetEnd(new Date());
		
		
		gb.setTargetSalerID(gb1.getTargetSalerID());
		gb.setTargetSalerName(gb1.getTargetSalerName());
		/**end 物品明细*/
		beans.add(cb);
		beans.add(gb);
	}


	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public CashierBills getCashierBills() {
		return cashierBills;
	}

	public void setCashierBills(CashierBills cashierBills) {
		this.cashierBills = cashierBills;
	}

	public CashierBillsVO getCashierBillsVO() {
		return cashierBillsVO;
	}

	public void setCashierBillsVO(CashierBillsVO cashierBillsVO) {
		this.cashierBillsVO = cashierBillsVO;
	}

	/**
	 * 标志审核学员是否转科一培训
	 * 
	 * @return
	 */
	public String getStatusForPerson() {
		return statusForPerson;
	}

	public void setStatusForPerson(String statusForPerson) {
		this.statusForPerson = statusForPerson;
	}

	public String getCashierBillsID() {
		return cashierBillsID;
	}

	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}

	public String getBillTypese() {
		return billTypese;
	}

	public void setBillTypese(String billTypese) {
		this.billTypese = billTypese;
	}
}
