package hy.ea.invoicing.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.company.vo.ContactCompanyView;
import hy.ea.bo.company.vo.ContactUser;
import hy.ea.bo.finance.GoodsNum;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.FinancialBillsGood;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.Warehouse;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.util.Utilities;
import hy.ea.util.VOtool;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionContext;

/**
 * 进销存 入库
 * @author Administrator
 *
 */
public class InvoicingStorageAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	@Resource
	private ServerService serverService;
	public InputStream excelStream;
	private FinancialBill financialBill;  //进销存单据表(采购单据表)
	private FinancialBillsGood financialBillsGood;   //进销存子类(物品单据表)
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private Map<String, FinancialBillsGood> goodsmap;
	private List<CCode> payTypeList;
	private List<CCode> logisticsList;
	private List<CCode> connectionlist;
	private List<CCode> codeRelationList;
	private String companyname;
	private String organizationname;
	/**
	 * 仓库列表中--库列表
	 */
	private List<BaseBean> warehouseList;
	private List<FinancialBillsGood> BillsGoodList;
	private ContactUser contactUser1;
	private ContactCompanyView contactCompanyView1;
	private Inventory   inventoryParam;
	private String sdate;
	private String edate;
	private String print;
	private stockInv stockinv;
	/**
	 * 责任人
	 */
	private List<BaseBean> stafflist;
	
	
	/**
	 * 查询列表（可根据条件查询）被调用
	 * 
	 * @return
	 */
	public List<Object> getList() {
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		String sql=VOtool.getCashierBillVO(4);
		sql+=" where f.groupCompanySn = ?";
		parms.add(groupCompanySn);
		sql += " and f.companyid = ? ";
		parms.add(account.getCompanyID());
		sql += " and f.organizationID=?";
		parms.add(organizationID);
		sql+="and f.billStatus=?";
		parms.add("02");
		if (search != null && search.equals("search")) {
			FinancialBill  financialBill=(FinancialBill)session.get("financialBill");
			if (financialBill.getJournalNum() != null
					&& !financialBill.getJournalNum().trim().equals("")) {
				sql += " and f.journalNum like ? ";
				parms.add("%" + financialBill.getJournalNum().trim() + "%");
			}
			if (financialBill.getStaffID() != null
					&& !financialBill.getStaffID().equals("")) {
				sql += " and f.staffID = ? ";
				parms.add(financialBill.getStaffID());
			}
			if (financialBill.getCcompanyRelationship() != null
					&& !financialBill.getCcompanyRelationship().equals("")) {
				sql += " and f.ccompanyRelationship = ? ";
				parms.add(financialBill.getCcompanyRelationship());
			}
			if (financialBill.getCstaffRelationship() != null
					&& !financialBill.getCstaffRelationship().equals("")) {
				sql += " and f.cstaffRelationship = ? ";
				parms.add(financialBill.getCstaffRelationship());
			}
			if (sdate != null && edate != null && !"".equals(sdate)
					&& !"".equals(edate)) {
				sql += " and f.billsDate between ? and ?";
				parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
				parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
			}
			if (financialBill.getCcompanyName() != null
					&& !financialBill.getCcompanyName().trim().equals("")) {
				sql += " and f.ccompanyName like ? ";
				parms.add("%" + financialBill.getCcompanyName().trim() + "%");
			}
			if (financialBill.getCstaffName() != null
					&& !financialBill.getCstaffName().trim().equals("")) {
				sql += " and f.cstaffName like ? ";
				parms.add("%" + financialBill.getCstaffName().trim() + "%");
			}
		}
		sql+="order by f.billsDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	
/**********************************************************选择入库********************************************************/
	
	/**
	 * 选择入库查询方法
	 */
	public String toSearchChooseWarehousing() {
		ActionContext.getContext().getSession().put("financialBillsGood",
				financialBillsGood);
		return getChooseWarehousingList();
	}
	/**
	 * 选择入库获取List
	 */
	public String getChooseWarehousingList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql="from Warehouse where companyID=? and wareType='1'";
		warehouseList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID()});
		List<Object> list = getChooseWarehousing();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);
		if(print!=null&&"print".equals("print")){
			return "choosewarehousingBill";
		}
		return "choosewarehousing";
	}
	/**
	 * 选择入库	
	 * @return
	 */
	public List<Object> getChooseWarehousing() {
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		//String sql = "select g.financialgoodid,g.goodsnum,g.sortcode,g.goodsname,"
				//+ " g.type,g.brand,g.modelnumber,g.unit,g.quantity,g.requantity,g.unitprice,"
				//+ " g.amount,g.paymenttype,g.logisticstype,g.remindcontent,g.isQualify,g.storageQuantity"
				//+ " from dt_inv_fbg g"
				//+ " where exists"
				//+ " (select f.financialbillid from dt_inv_fb f "
				//+ " where g.financialbillid=f.financialbillid and f.groupCompanySn=? and f.companyid=? and f.organizationid=? and f.billStatus=?) and g.status=?";
		//parms.add(groupCompanySn);
		//parms.add(account.getCompanyID());
		//parms.add(organizationID);
		//parms.add("01");
		//parms.add("02");
		String sql = "select g.financialgoodid,g.goodsnum,g.sortcode,g.goodsname,"
				+ " g.type,g.brand,g.modelnumber,g.unit,g.quantity,g.requantity,g.unitprice,"
				+ " g.amount,g.paymenttype,g.logisticstype,g.remindcontent,g.isQualify,case when g.status='00' then '未收货' " 
				+ " when g.status='01' then '已收货' when g.status='02' then '已验库'  when g.status='03' then '已入库' when g.status='07' then '出库' " 
				+ " when g.status='08' then '入库失败' when g.status='09' then '出库审核' else '' end, "
				+ " case when g.goodstatus='00' then '正常' when g.goodstatus='01' then '维修'" 
				+ " when g.goodstatus='02' then '报废' else '' end, f.journalNum,f.StaffName, f.billStaffName, f.staffsName,g.storageQuantity "
				+ " from dt_inv_fbg g"
				+ " left join dt_inv_fb f on g.financialbillid=f.financialbillid";
		sql+=" where f.groupCompanySn = ?";
		parms.add(groupCompanySn);
		sql += " and f.companyid = ? ";
		parms.add(account.getCompanyID());
		sql += " and f.organizationID=?";
		parms.add(organizationID);
		//已收货
		sql+=" and f.billStatus=?";
		parms.add("01");
		sql+=" and g.status=?";
		parms.add("02");
		if(search!=null&&search.equals("search")){
			FinancialBillsGood financialBillsGood=(FinancialBillsGood)session.get("financialBillsGood");
			if(financialBillsGood!=null&&financialBillsGood.getGoodsName()!=null&&!"".equals(financialBillsGood.getGoodsName().trim())){
				sql+=" and g.goodsName like ?";
				parms.add("%"+financialBillsGood.getGoodsName().trim()+"%");
			}
			if(financialBillsGood!=null&&financialBillsGood.getType()!=null&&!"".equals(financialBillsGood.getType().trim())){
				sql+=" and g.type like ?";
				parms.add("%"+financialBillsGood.getType().trim()+"%");
			}
			if(financialBillsGood!=null&&financialBillsGood.getGoodsNum()!=null&&!"".equals(financialBillsGood.getGoodsNum().trim())){
				sql+=" and g.goodsNum like ?";
				parms.add("%"+financialBillsGood.getGoodsNum().trim()+"%");
			}
		}
		
		sql += " order by g.financialgoodid desc ";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	
	/**
	 * 本方法从SplitBillAction类中拷贝部分而来，作用是在入库时，对每一个入库的物品生成序列号（GoodsNum对象记录），存放到数据对象的List中(baseBeanList).
	 * @param baseBeanList  
	 * @param Scb  cashierBillsID (null)
	 * @param Sgb  goodsBillsID
	 * @param Sgb2 goodsBillsID (null)
	 * @param Sg  goodsID
	 * @param Sc  companyID
	 * @param Ss status
	 * @param Sn True
	 * @param quantity 物品数量
	 * @param num 序列号 (null)
	 * @see hy.ea.finance.action.SplitBillAction
	 * dd(baseBeanList,"","","",goods.getGoodsID(),account.getCompanyID(),"02",true,inventoryParam.getInvenQuantity(),null);
	 */
	private void dd(List<BaseBean> baseBeanList,String Scb,String Sgb,String Sgb2,String Sg,String Sc,String Ss,Boolean Sn,int quantity,String num){
		GoodsNum goodsNum=null;
		String num3="";
		Object gval=null;
		if(Sn){
			goodsNum=new GoodsNum();
			goodsNum.setGnID(serverService.getServerID("goodsnum"));
			goodsNum.setGnPID(goodsNum.getGnID());
			goodsNum.setCashierBillsID(Scb);
			goodsNum.setGoodsBillsID(Sgb);
			goodsNum.setGoodsID(Sg);
			goodsNum.setCompanyID(Sc);
			goodsNum.setPhaseDate(new Date());
			goodsNum.setStatus(Ss);
			GoodsManage goodsManage=(GoodsManage)baseBeanService.getBeanByHqlAndParams("from GoodsManage where goodsID=?", new Object[]{Sg});
			goodsNum.setGoodsCoding(goodsManage.getGoodsCoding());
			if(num!=null&&!num.equals("")){
				num3=Integer.toString(Integer.parseInt(num) + 1);
			}else{
				gval=baseBeanService.getObjectBySqlAndParams("select max(goodsnumber) from dtgoodsnum", null);
				if (gval == null) {
					num3 = "0001";
				} else {
					num3 = Integer.toString(Integer.parseInt(gval.toString()) + 1);
				}
			}
			if (gval != null||num3!=null) {
				int num1=num3.length();
				for(int i=0;i<4-num1;i++){
					num3="0"+num3;
				}
			}
			goodsNum.setGoodsnumber(num3);
			baseBeanList.add(goodsNum);
		}
				
		if(quantity>1){
			dd(baseBeanList,Scb,Sgb,Sgb2,Sg,Sc,Ss,Sn,quantity-1,num3);
		}
	}
	/**
	 * 选择入库保存方法
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String saveChooseWarehousing(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		FinancialBillsGood goods= (FinancialBillsGood) baseBeanService.getBeanByHqlAndParams("from FinancialBillsGood where financialgoodID=?",new Object[] {financialBillsGood.getFinancialgoodID()});
		FinancialBill bills=(FinancialBill) baseBeanService.getBeanByHqlAndParams("from FinancialBill where financialbillID=?",new Object[] {goods.getFinancialbillID()});
		String strInventoryID = null;
		if(goods!=null){
			Inventory   inventory=new Inventory();
			double  unitPrices=Double.parseDouble(goods.getUnitPrice());
			//System.out.print(String.valueOf(unitPrices));
			if(bills.getStaffID().equals(bills.getStaffsID())){
				if(inventoryParam.getWeizhi().equals("") ){
					String hql1=" from Inventory where goodsID=?  and unitPrice=? and status=?  and staffID is null and weishi is null";
					inventory=(Inventory)baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{goods.getGoodsID(),String.valueOf(unitPrices),"03"});
				}else{
					String hql1=" from Inventory where goodsID=? and weizhi=? and unitPrice=? and status=?  and staffID is null";
					inventory=(Inventory)baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{goods.getGoodsID(),inventoryParam.getWeizhi(),String.valueOf(unitPrices),"03"});
				}
				
			}else{
				if(inventoryParam.getWeizhi().equals("") ){
					String hql1=" from Inventory where goodsID=?  and staffID=? and unitPrice=? and status=? and weizhi is null";
					inventory=(Inventory)baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{goods.getGoodsID(),bills.getStaffsID(),String.valueOf(unitPrices),"03"});
				}else{
					String hql1=" from Inventory where goodsID=? and weizhi=? and staffID=? and unitPrice=? and status=?";
					inventory=(Inventory)baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{goods.getGoodsID(),inventoryParam.getWeizhi(),bills.getStaffsID(),String.valueOf(unitPrices),"03"});
				}
			
			}
			String hqle="from Inventory where goodsID=?";
			Inventory Inventorys=new Inventory();
			Inventorys=(Inventory) baseBeanService.getBeanByHqlAndParams(hqle, new Object[]{goods.getGoodsID()});
			if(inventory==null){
				
				Inventory  inven=new Inventory();
				inven.setInventoryID(serverService.getServerID("inventory"));
				goods.setInventoryID(inven.getInventoryID());
				inven.setCompanyID(account.getCompanyID());
				inven.setGroupCompanySn(groupCompanySn);
				inven.setWeizhi(inventoryParam.getWeizhi());
				//根据责任人和接收人判断存入库存
				if(bills.getStaffID().equals(bills.getStaffsID())){
				//当责任人等于接收人的时 物品存入公司库存
				//存入公司库存  单据id 单据子id
				inven.setStaffID("");
				inven.setStaffName("");
				}else{
					//不一样的时 存入接收人的库存
					inven.setStaffID(bills.getStaffsID());
					inven.setStaffName(bills.getStaffsName());
				}
				if(Inventorys !=null){
				if(Inventorys.getInvenOnline() != null){
					inven.setInvenOnline(Inventorys.getInvenOnline());
					inven.setInvenUnderline(Inventorys.getInvenUnderline());
				}
				}
				inven.setGoodstatus("00");
				inven.setStatus("03");
				inven.setBadQuantity("0");
				inven.setGoodsID(goods.getGoodsID());
				inven.setGoodsType(goods.getType());
				inven.setGoodsName(goods.getGoodsName());
				inven.setSubjectsID(goods.getSubjectsID());
				inven.setSubjectsName(goods.getSubjectsName());
				inven.setBarcode(goods.getSortCode());
				inven.setDefaultStorage(goods.getDefaultStorage());
				inven.setStandard(goods.getSortCode());
				inven.setUnit(goods.getUnit());
				inven.setGoodsCoding(goods.getGoodsNum());
				inven.setUnitPrice(String.valueOf(Double.parseDouble(goods.getUnitPrice())));
				int storageQuantity=Integer.parseInt(goods.getStorageQuantity());//入库操作之前的未入库物品剩余数量
				int storageQuantity1=Integer.parseInt(financialBillsGood.getStorageQuantity());//入库操作之后的未入库物品剩余数量
				int invenQuantity=storageQuantity-storageQuantity1;//入库操作之前的未入库物品剩余数量-入库操作之后的未入库物品剩余数量=正要入库商品数量
				inven.setInvenQuantity(String.valueOf(invenQuantity));
				double  unitPrice=Double.parseDouble(goods.getUnitPrice());//商品单价
				inven.setSumPrice(String.valueOf(invenQuantity*unitPrice));//商品总金额
				goods.setStorageQuantity(financialBillsGood.getStorageQuantity());
				parameter = "员工："+account.getAccountName()+"选择入库（新增库房物品 ID：" + inven.getGoodsID() + "）";
				CLogBook cLogBook=logBookService.saveCLogBook(organizationID,parameter, account);	
				baseBeanList.add(cLogBook);
				baseBeanList.add(inven);
				
				strInventoryID = inven.getInventoryID();
				
			}else{
						int  invenQuant=Integer.parseInt(inventory.getInvenQuantity());//结存商品数量
						int storageQuantity=Integer.parseInt(goods.getStorageQuantity());//入库操作之前的未入库物品剩余数量
						int storageQuantity1=Integer.parseInt(financialBillsGood.getStorageQuantity());//入库操作之后的未入库物品剩余数量
						int invenQuantity=storageQuantity-storageQuantity1;//入库操作之前的未入库物品剩余数量-入库操作之后的未入库物品剩余数量=正要入库商品数量
						
						
						double  sumPrice=Double.parseDouble(inventory.getSumPrice());//结存商品总金额
						double  unitPrice=Double.parseDouble(goods.getUnitPrice());//商品单价
						double  amount=unitPrice*invenQuantity;//收入合格商品总金额
						
						double  averagePrice=(sumPrice+amount)/(invenQuant+invenQuantity);//移动加权平均单价
						BigDecimal   b   =   new   BigDecimal(averagePrice);
						double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();//四舍五入后保留小数后俩位
						int sum=invenQuant+invenQuantity;//入库后库存商品总数量
						double sumPrice1=f1*sum;//入库后库存商品总金额
						goods.setInventoryID(inventory.getInventoryID());
						inventory.setSumPrice(String.valueOf(sumPrice1));
						inventory.setUnitPrice(String.valueOf(f1));
						inventory.setInvenQuantity(String.valueOf(sum));
						goods.setStorageQuantity(financialBillsGood.getStorageQuantity());
						parameter = "员工："+account.getAccountName()+"选择入库（更新库房物品 ID：" + inventory.getGoodsID() + "）";
						CLogBook cLogBook=logBookService.saveCLogBook(organizationID,parameter, account);	
						baseBeanList.add(cLogBook);
						baseBeanList.add(inventory);
						
						strInventoryID = inventory.getInventoryID();
				
			}
			baseBeanList.add(goods);	
			stockInv stockinvs=new stockInv();
			stockinvs.setStockinvID(serverService.getServerID("stockInv"));
			stockinvs.setCompanyID(account.getCompanyID());
			stockinvs.setGroupCompanySn(groupCompanySn);
			stockinvs.setGoodsID(goods.getGoodsID());
			stockinvs.setGoodsType(goods.getType());
			stockinvs.setGoodsName(goods.getGoodsName());
			stockinvs.setInvenQuantity(inventoryParam.getInvenQuantity());
			if(Inventorys !=null){
			if(Inventorys.getInvenOnline() != null){
				stockinvs.setInvenOnline(Inventorys.getInvenOnline());
				stockinvs.setInvenUnderline(Inventorys.getInvenUnderline());
			}
			}
			double  unitPrice=Double.parseDouble(goods.getUnitPrice());//商品单价
			stockinvs.setSummoney(String.valueOf(Integer.parseInt(inventoryParam.getInvenQuantity())*unitPrice));//商品总金额
			stockinvs.setIntime(new Date());
			stockinvs.setType("00");
			baseBeanList.add(stockinvs);
			
			dd(baseBeanList,strInventoryID,null,null,goods.getGoodsID(),account.getCompanyID(),"03",true,Integer.parseInt(inventoryParam.getInvenQuantity()),null);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,
				null);	
		return "success";
	}
	/**********************************************************入库管理********************************************************/

	/**
	 * 入库管理——条件查询(保存条件)
	 * 
	 * @return
	 */
	public String toSearchWare() {
		ActionContext.getContext().getSession().put("financialBill",
				financialBill);
		return getWareManagementList();
	}

	/**
	 * 入库管理——列表
	 * 
	 * @return
	 */
	public String getWareManagementList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), organizationID};
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus='50' and c.organizationID=?)";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql, params);
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(), "scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);
		return "warelist";
	}
	
	
	/**
	 * 入库管理——查看
	 * @return
	 */
	public String toWareManagement(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		
		String hql="from FinancialBill where groupCompanySn = ? and companyid = ? and organizationID=? and financialbillID=?";
		financialBill=(FinancialBill)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{groupCompanySn,account.getCompanyID(),organizationID,financialBill.getFinancialbillID()});
		BillsGoodList=new ArrayList<FinancialBillsGood>();
		String hqlg="from FinancialBillsGood where financialbillID=? order by numbers";
		List<BaseBean> financialBillsGoodList=baseBeanService.getListBeanByHqlAndParams(hqlg, new Object[]{financialBill.getFinancialbillID()});
			if(financialBillsGoodList!=null&&financialBillsGoodList.size()!=0&&financialBillsGoodList.get(0)!=null){
				for (int i = 0; i < financialBillsGoodList.size(); i++) {
					FinancialBillsGood  financialBillsGood=(FinancialBillsGood)financialBillsGoodList.get(i);
					String hql1=" from Inventory where inventoryID=?";
					Inventory   inventory=(Inventory)baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{financialBillsGood.getInventoryID()});
					if(inventory!=null){
						financialBillsGood.setPware(inventory.getWarehouse());
						financialBillsGood.setParea(inventory.getArea());
						financialBillsGood.setPframe(inventory.getFrame());
						financialBillsGood.setPlace(inventory.getPosition());
						BillsGoodList.add(financialBillsGood);	
					}
				}
			}
		String hql1="from Warehouse where companyID=?";
		List<BaseBean>   warehouseList1=baseBeanService.getListBeanByHqlAndParams(hql1, new Object[]{account.getCompanyID()});
		Map<String, String> map=new HashMap<String, String>();
		for (int i = 0; i < warehouseList1.size() ; i++) {
			Warehouse warehouse=(Warehouse)warehouseList1.get(i);
			map.put(warehouse.getWareID(), warehouse.getWareName());
		}
		FinancialBillsGood.setOrgMap(map);
		
		String hql2 = " from ContactUser where relationID=? ";
		contactUser1=(ContactUser)baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{financialBill.getCstaffID()});
		
		String hql3 = " from ContactCompanyView where ccompanyID=? ";
		contactCompanyView1=(ContactCompanyView)baseBeanService.getBeanByHqlAndParams(hql3, new Object[]{financialBill.getCcompanyID()});
		
		String comhql = "from Company where companyID=?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(
				comhql, new Object[] { financialBill.getCompanyID() });
		companyname = company.getCompanyName();
		
		String orghql = "from COrganization where companyID=? and organizationID=?";
		COrganization cOrganization = (COrganization) baseBeanService
				.getBeanByHqlAndParams(orghql, new Object[] {
						financialBill.getCompanyID(),
						financialBill.getOrganizationID()});
		organizationname = cOrganization.getOrganizationName();
		return "wareedit";
	}
	/**
	 * 入库管理,添加页面
	 * @return
	 */
	public String addWareManagement(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		payTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000031");
		logisticsList = codeService.getCCodeListByPID(companyID,
				"scode20110106hfjes5ucxp0000000021");
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "sode20110106hfjes5ucxp0000000017");	
		
		String hql="from Warehouse where companyID=? and  wareType=? and states=?";
		warehouseList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),"1","00"});
		return "wareadd";
	}
	
	
	/**
	 * 入库管理——保存
	 */
	public String saveWareManagement() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		financialBill.setFinancialbillID(serverService.getServerID("financial"));
		parameter = "添加采购单据（凭证号" + financialBill.getJournalNum() + "）";
		financialBill.setOrganizationID(organizationID);
		financialBill.setCompanyID(account.getCompanyID());
		financialBill.setGroupCompanySn(groupCompanySn);
		financialBill.setBillsType("入库单");
		financialBill.setBillStatus("02");
		financialBill.setBillsDate(new Date());
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,new Object[] { account.getStaffID() });		
		financialBill.setBillStaffName(sta.getStaffName());
		financialBill.setBillStaffID(account.getStaffID());
		if (financialBill.getStaffName() != null
				&& !financialBill.getStaffName().equals("")) {
			String staffname = financialBill.getStaffName().substring(0,
					financialBill.getStaffName().indexOf("-"));
			financialBill.setStaffName(staffname);
		}
		if(financialBill.getCstaffID()!=null&&!"".equals(financialBill.getCstaffID())){
			String hql2 = " from ContactUser where relationID=? ";
			ContactUser contactUser1=(ContactUser)baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{financialBill.getCstaffID()});
			financialBill.setCstaffName(contactUser1.getStaffName());
			financialBill.setCstaffRelationship(contactUser1.getRelation());
		}
		if(financialBill.getCcompanyID()!=null&&!"".equals(financialBill.getCcompanyID())){
			String hql3 = " from ContactCompanyView where ccompanyID=? ";
			ContactCompanyView contactCompanyView1=(ContactCompanyView)baseBeanService.getBeanByHqlAndParams(hql3, new Object[]{financialBill.getCcompanyID()});
			financialBill.setCcompanyName(contactCompanyView1.getCompanyName());
			financialBill.setCcompanyRelationship(contactCompanyView1.getContactConnections());
			financialBill.setCcompanyTel(contactCompanyView1.getCompanyTel());
		}
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		baseBeanList.add(financialBill);
		if (goodsmap != null) {
			for (FinancialBillsGood goods : goodsmap.values()) {
				goods.setFinancialbillID(financialBill.getFinancialbillID());
				goods.setFinancialgoodID(serverService
						.getServerID("financialgood"));
				String hql = "from GoodsManage g where g.goodsID=?";
				GoodsManage goodsManage = (GoodsManage) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { goods
								.getGoodsID() });
				goods.setGoodsName(goodsManage.getGoodsName());
				goods.setGoodsNum(goodsManage.getGoodsCoding());
				goods.setModelNumber(goodsManage.getModel());
				goods.setBrand(goodsManage.getMnemonicCode());
				goods.setType(goodsManage.getTypeID());
				FinancialBillsGood.setOrgMap(null);
				if(goods.getGoodsID()!=null&&!"".equals(goods.getGoodsID())){
					String hql1=" from Inventory where goodsID=? and warehouse=? and area=? and frame=? and position=?";
					Inventory   inventory=(Inventory)baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{goods.getGoodsID(),goods.getPware(),goods.getParea(),goods.getPframe(),goods.getPlace()});
						if(inventory==null){
							/*String hql11=" from Inventory where warehouse=? and area=? and frame=? and position=?";
							Inventory   inventory1=(Inventory)baseBeanService.getBeanByHqlAndParams(hql11, new Object[]{goods.getPware(),goods.getParea(),goods.getPframe(),goods.getPlace()});
							if(inventory1!=null){
								message="所选库房中已存放  "+inventory1.getGoodsName()+"!  请选择其他位置存放...";
								return "failed";
							}*/
							Inventory  inven=new Inventory();
							inven.setInventoryID(serverService.getServerID("inventory"));
							goods.setInventoryID(inven.getInventoryID());
							inven.setCompanyID(account.getCompanyID());
							inven.setGroupCompanySn(groupCompanySn);
							inven.setWarehouse(goods.getPware());
							inven.setArea(goods.getParea());
							inven.setFrame(goods.getPframe());
							inven.setPosition(goods.getPlace());
							inven.setGoodsID(goods.getGoodsID());
							inven.setGoodsType(goods.getType());
							inven.setGoodsName(goods.getGoodsName());
							inven.setBarcode(goods.getSortCode());
							inven.setStandard(goods.getSortCode());
							inven.setUnit(goods.getUnit());
							inven.setUnitPrice(String.valueOf(Double.parseDouble(goods.getUnitPrice())));
							inven.setInvenQuantity(goods.getQuantity());
							inven.setSumPrice(String.valueOf(Double.parseDouble(goods.getAmount())));
							String hql2="from Warehouse where companyID=? and wareID=?";
							String[] arys=new String[]{goods.getPware(),goods.getParea(),goods.getPframe(),goods.getPlace()};
							String[] arys1=new String[4];
							for (int i = 0; i < arys.length; i++) {
								arys1[i]=((Warehouse)baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),arys[i]})).getWareName();
							}
							inven.setWarehouseName(arys1[0]);
							inven.setAreaName(arys1[1]);
							inven.setFrameName(arys1[2]);
							inven.setPositionName(arys1[3]);
							baseBeanList.add(inven);
						}else{
							int  invenQuant=Integer.parseInt(inventory.getInvenQuantity());//结存商品数量
							int  quantity=Integer.parseInt(goods.getQuantity());//收入商品数量
							
							double  sumPrice=Double.parseDouble(inventory.getSumPrice());//结存商品总金额
							double  amount=Double.parseDouble(goods.getAmount());//收入商品总金额
							
							double  averagePrice=(sumPrice+amount)/(invenQuant+quantity);//移动加权平均单价
							BigDecimal   b   =   new   BigDecimal(averagePrice);//转换类型
							double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();//四舍五入后保留小数点后俩位
							int sum=invenQuant+quantity;//入库后库存商品总数量
							double sumPrice1=f1*sum;//入库后库存商品总金额
							goods.setInventoryID(inventory.getInventoryID());
							inventory.setSumPrice(String.valueOf(sumPrice1));
							inventory.setUnitPrice(String.valueOf(f1));
							inventory.setInvenQuantity(String.valueOf(sum));
							baseBeanList.add(inventory);
						}
					baseBeanList.add(goods);
				}
			}
		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,
				null);			
		return "success";
	}
	//入库驳回操作  ct
	public String updatestatus(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object[]> list=new ArrayList<Object[]>();
		List<BaseBean> listbabe=new ArrayList<BaseBean>(); 
		parameter = "员工："+account.getAccountName();
		CLogBook cLogBook=logBookService.saveCLogBook(organizationID,
				parameter, account);
		String hql="update FinancialBillsGood set status=? where financialgoodID=? ";
		String[] hqls={hql};
		list.add(new Object[]{"08",financialBillsGood.getFinancialgoodID()});
		listbabe.add(cLogBook);
		baseBeanService.executeHqlsByParamsList(listbabe,hqls,list);
		return getChooseWarehousingList();
	} 
	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public FinancialBill getFinancialBill() {
		return financialBill;
	}

	public void setFinancialBill(FinancialBill financialBill) {
		this.financialBill = financialBill;
	}

	public FinancialBillsGood getFinancialBillsGood() {
		return financialBillsGood;
	}

	public void setFinancialBillsGood(FinancialBillsGood financialBillsGood) {
		this.financialBillsGood = financialBillsGood;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
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

	public Map<String, FinancialBillsGood> getGoodsmap() {
		return goodsmap;
	}

	public void setGoodsmap(Map<String, FinancialBillsGood> goodsmap) {
		this.goodsmap = goodsmap;
	}

	public List<CCode> getPayTypeList() {
		return payTypeList;
	}

	public void setPayTypeList(List<CCode> payTypeList) {
		this.payTypeList = payTypeList;
	}

	public List<CCode> getLogisticsList() {
		return logisticsList;
	}

	public void setLogisticsList(List<CCode> logisticsList) {
		this.logisticsList = logisticsList;
	}

	public List<CCode> getConnectionlist() {
		return connectionlist;
	}

	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}

	public List<CCode> getCodeRelationList() {
		return codeRelationList;
	}

	public void setCodeRelationList(List<CCode> codeRelationList) {
		this.codeRelationList = codeRelationList;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public List<BaseBean> getWarehouseList() {
		return warehouseList;
	}

	public void setWarehouseList(List<BaseBean> warehouseList) {
		this.warehouseList = warehouseList;
	}

	public List<FinancialBillsGood> getBillsGoodList() {
		return BillsGoodList;
	}

	public void setBillsGoodList(List<FinancialBillsGood> billsGoodList) {
		BillsGoodList = billsGoodList;
	}

	public ContactUser getContactUser1() {
		return contactUser1;
	}

	public void setContactUser1(ContactUser contactUser1) {
		this.contactUser1 = contactUser1;
	}

	public ContactCompanyView getContactCompanyView1() {
		return contactCompanyView1;
	}

	public void setContactCompanyView1(ContactCompanyView contactCompanyView1) {
		this.contactCompanyView1 = contactCompanyView1;
	}

	public Inventory getInventoryParam() {
		return inventoryParam;
	}

	public void setInventoryParam(Inventory inventoryParam) {
		this.inventoryParam = inventoryParam;
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

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public List<BaseBean> getStafflist() {
		return stafflist;
	}

	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
	}

	public stockInv getStockinv() {
		return stockinv;
	}

	public void setStockinv(stockInv stockinv) {
		this.stockinv = stockinv;
	}

	
}
