package hy.ea.production.service.impl;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.WfjGuize;
import com.tiantai.wfj.bo.WfjJifen;
import com.tiantai.wfj.bo.WfjJifenDetail;
import com.tiantai.wfj.bo.WfjTask;

import hy.ea.bo.company.DepotManage;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.bo.production.recruit.TalentPool;
import hy.ea.production.service.BidCommonService;
import hy.ea.util.Constant;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;

@Service
public class BidCommonServiceImpl implements BidCommonService {

	@Resource
	private BaseBeanDao beandao;
	@Resource
	private ServerService idgec;

	@Transactional
	@Override
	public synchronized String consumeWfjJinbi(TEshopCusCom cus,String resumeIDs,String amt) throws Exception{
		
		TalentPool tp = null;
		List<BaseBean> beans = new ArrayList<BaseBean>();
		String hqltal = "from TalentPool t where staffID = ? and resumeID in(";
		List<Object> paramtal= new ArrayList<Object>();
		paramtal.add(cus.getStaffid());
		int total = 0;
		if (resumeIDs != null && !"".equals(resumeIDs)) {
			String[] arrayriId = resumeIDs.split(",");
			
			for (int i = 0; i < arrayriId.length; i++) {
				   if(i!=arrayriId.length-1){
					   hqltal+="?,";
				   }else{
					   hqltal+="?)";
				   }
				   
				   paramtal.add(arrayriId[i].trim());
			   }
		   List<BaseBean> existlist = beandao.getListBeanByHqlAndParams(hqltal, paramtal.toArray());
		   List<String> exist = new ArrayList<String>();
		   
		    for (BaseBean b:existlist) {
			   TalentPool t = (TalentPool) b;
			   exist.add(t.getResumeID());
		    }
         
			for (int i = 0; i < arrayriId.length; i++) {
				if(!exist.contains(arrayriId[i].trim())){
				tp = new TalentPool();
				tp.setTpId(idgec.getServerID("tpId"));
				tp.setStaffID(cus.getStaffid());
				tp.setState("00");
				tp.setType("01");// 邀请
				tp.setResumeID(arrayriId[i]);
				tp.setPostDate(new Date());
				tp.setCompanyId(cus.getCompanyId());
			    	beans.add(tp);
			    	
			    	total++;
				}
			}
			
			if(total==0){
				return "";
			}
		}
		String wfj_comid = "";
		String staffId = cus.getStaffid();
		if(cus.getCompanyId()==null){
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT t.staffid, t.companyid, t.state,t.superioragent,");
			sb.append("t.pseudo_company_name,t.custype,t.sccid,t.account");
			sb.append(" FROM T_ESHOP_CUSCOM t where t.state=? START WITH t.ACCOUNT = ?");
			sb.append(" CONNECT BY PRIOR t.SUPERIORAGENT = t.ACCOUNT");
			List<BaseBean> baseBeans=beandao.getListBeanBySqlAndParams(sb.toString(),new Object[]{"2",cus.getAccount()});
			if(baseBeans.size()>0){
				Object object=baseBeans.get(0);
				Object[] objects=(Object[])object;
				wfj_comid=objects[1].toString();
			}
		}else{
			wfj_comid=cus.getCompanyId();
		}
		
		//责任人name,责任人编号
		String sql ="select sta.staffname,sta.recordcode from dt_hr_staff sta where sta.staffid = ?";
		Object ret=beandao.getObjectBySqlAndParams(sql, new Object[]{cus.getStaffid()});
		String wfj_comname="";
		
		wfj_comname=(String) beandao.getObjectBySqlAndParams("select c.companyname from dtcompany c where c.companyid=?",new Object[]{wfj_comid});

		Object[] arr=(Object[]) ret;
		String comId=Constant.COMPAYN_ID;
		String comName="北京天太世统科技有限公司";
		String comgn="groupcompany20120523G3VR9PXHZD0000000021";
		String staname=arr[0].toString();
		String cbid1=idgec.getServerID("CashierBills");//用户  金币出库单
		String cbid2=idgec.getServerID("CashierBills");//平台 金币入库单
		String pzid1=idgec.getBillID(comId);
		String pzid2=idgec.getBillID(comId);


		//查询产品 微分金金币
		String hql ="select m from ProductPackaging m where m.goodsName =? and m.companyID=?";
		ProductPackaging gm= (ProductPackaging) beandao.getBeanByHqlAndParams(hql, new Object[]{"微分金金币",comId});
		if(gm==null){
			return null;
		}
		
	
		/*****用户***金币出库单*******start**************/
		CashierBills  cb=new CashierBills();
		cb.setCashierBillsID(cbid1);
		
		cb.setCashierDate(new Date());// 单据日期  下单日期
		
		cb.setBillsType("金币出库单");// 单据类型
		cb.setJournalNum(pzid1);// 凭证号
		System.out.println("金币出库单:"+cb.getJournalNum());

		cb.setStaffID(staffId);// 责任人ID
		cb.setStaffName(staname);//责任人name

		cb.setCompanyID(wfj_comid);
		cb.setCompanyName(wfj_comname);//当前公司name
		
		/**************************制单人信息************************/
		cb.setInputid(staffId);  //制单人员id
		cb.setInputName(staname);//制单人名称
		cb.setInputCompanyid(wfj_comid); //制单人公司id
		cb.setInputCompanyName(wfj_comname); //制单人公司名称
		
		/***********************状态**********************/
		cb.setStatus("16");// 15：已入库 16：已出库  
		
		/**************往来单位****************/
		cb.setCcompanyID(Constant.COMPAYN_ID);          //往来单位ID (如果是订单这个字段存的是商户id)
		cb.setCcompanyName("北京天太世统科技有限公司");        //往来单位name (如果是订单这个字段存的是（商户编号，商户名称）)
		cb.setCompanyAddr("东直门外大街42号宇飞大厦801室");         //具体地址  (如果是订单这个字段存的是发货地址)
		cb.setCompanyTel("010-64167113");          //公司电话  (如果是订单这个字段存的是发货人电话)
		cb.setCresponsible("刘太平");        //负责人 (如果是订单这个字段存的是发货人名称)
		cb.setResponsibleTel("15810799888");      //负责人电话	
		
		cb.setAppstyle("12");  //12：招聘消耗
		cb.setStatusbill("04"); //单据状态判断单据来源      04：用户下单
		cb.setPriceSub(amt);//价钱总和 邀请消耗1元
		cb.setProID("006");
		cb.setProjectName("招聘消耗金币");

		beans.add(cb);
		
		
		 GoodsBills gb=new GoodsBills();
		 gb.setCashierBillsID(cbid1);
		 gb.setGoodsBillsID(idgec.getServerID("GoodsBills"));
		 //填充产品
		 gb.setGoodsID(gm.getGoodsID());
		 gb.setGoodsNum(gm.getGoodsNum());
		 gb.setGoodsName(gm.getGoodsName());
		 gb.setStandard(gm.getStandard());
		 gb.setGoodsVariableID(gm.getVariableID());
		 gb.setWeight(gm.getWeight());
		 gb.setPrice("0.01");
		 gb.setQuantity(new BigDecimal(amt).multiply(new BigDecimal(100).multiply(new BigDecimal(total))).toString());
		 gb.setMoney((new BigDecimal(gb.getPrice()).multiply(new BigDecimal(gb.getQuantity())).toString()));
		 
		 
		 gb.setStartDate(null);// 款源日期
		 gb.setEndDate(null);// 报账日期
		 gb.setBatchNumber(null);// 批号或期号
		 gb.setPeriodDate(null);// 有效日期
		 gb.setRemindedContent(null);// 提醒内容
		 gb.setCostType(null);// 费用类别
		 gb.setPayType("12");// 付款方式
		 gb.setPriceManage(null);// 单价管理
		 gb.setAccompanyDate(null);
		 gb.setEntryTime(null);
		 gb.setRegistrationDate(null);
		
		 
		 
		
		/******************库存流程字段*****************/
		 gb.setKcStatus("16");			//状态15：已入库 16：已出库
		 gb.setGoodstatus("00");            //状态00正常
		 gb.setSortCode(null);              //统一分类条码
		 gb.setPpID(gm.getPpID());  //项目ID也就是产品ID；

		 beans.add(gb);


		/************end*********/
		
		
		
		/*****平台***金币入库单**********/
	    CashierBills cb1=new CashierBills();
	    //BeanUtils.copyProperties(cb1,cb);
	    cb1 = (CashierBills) cb.cloneCashierBills();
		cb1.setCashierBillsID(cbid2);
		cb1.setCashierBillsKey(null);
		cb1.setBillsType("金币入库单");
		cb1.setStatus("15");
		cb1.setJournalNum(pzid2);// 凭证号
		System.out.println("金币入库单:"+cb1.getJournalNum());
		cb1.setStaffID(null);// 责任人ID
		cb1.setStaffName(null);//责任人name
		cb1.setStaffCode(null);// 责任人编号
		cb1.setCompanyID(comId);
		cb1.setCompanyName(comName);
		cb1.setGroupCompanySn(comgn);
		/**************************制单人信息************************/
		cb1.setInputid(null);  //制单人员id
		cb1.setInputName("系统生成");//制单人名称
		cb1.setInputCompanyid(null); //制单人公司id
		cb1.setInputCompanyName(null); //制单人公司名称
		
		/**************往来单位****************/

		cb1.setCcompanyID(wfj_comid);          //往来单位ID (如果是订单这个字段存的是商户id)
		cb1.setCcompanyName(wfj_comname);        //往来单位name (如果是订单这个字段存的是（商户编号，商户名称）)
		cb1.setCompanyAddr(null);         //具体地址  (如果是订单这个字段存的是发货地址)
		cb1.setCompanyTel(null);          //公司电话  (如果是订单这个字段存的是发货人电话)
		cb1.setCresponsible(null);        //负责人 (如果是订单这个字段存的是发货人名称)
		cb1.setResponsibleTel(null);      //负责人电话
		/**************往来个人****************/
		cb1.setContactUserID(staffId);
		cb1.setCtUserName(staname);
		beans.add(cb1);
		
		GoodsBills gb1=new GoodsBills();
		try {
			//BeanUtils.copyProperties(gb1,gb);
			gb1 = (GoodsBills)gb.cloneGoodsBills();

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		gb1.setGoodsBillsKey(null);
		gb1.setGoodsBillsID(idgec.getServerID("GoodsBills"));
		gb1.setCashierBillsID(cbid2);
		gb1.setPrice("0.01");
		gb1.setQuantity(new BigDecimal(amt).multiply(new BigDecimal(100).multiply(new BigDecimal(total))).toString());
		gb1.setMoney((new BigDecimal(gb1.getPrice()).multiply(new BigDecimal(gb1.getQuantity())).toString()));
		gb1.setKcStatus("15");
		beans.add(gb1);
		

		//金币仓库
		boolean flag2=ruku(cbid2,gb1.getGoodsBillsID(),beans,comId,comgn,staffId,staname,"金币入库单","0.01",Float.parseFloat(amt)*100*total,amt,gm.getGoodsID(),"金币仓库",false);


		if(flag2==false){
			beans=null;
			return null;
		}

		
		/************end*********/
        
		
		//第二步生成金币记录
		//金币提现 id= WfjGuize20160628DTRG5CV5MS0000000002
		//积分明细
		WfjJifenDetail edetail=new WfjJifenDetail();
		edetail.setJifenDetailId(idgec.getServerID("WfjJifenDetail"));
		String temp=null;
		BigDecimal tempjifen=new BigDecimal(0);
		BaseBean bguize=beandao.getBeanByHqlAndParams("from WfjGuize where companyId=? and wfjGuizeId=?", new Object[]{Constant.COMPAYN_ID,"WfjGuize20160628DTRG5CV5MS0000000002"});
		WfjGuize eguize=(WfjGuize) bguize;
		
		WfjJifen wfjJifen= (WfjJifen)beandao.getBeanByHqlAndParams("from WfjJifen where staffId=?" , new Object[]{staffId});
		String jifenId=wfjJifen.getWfjJifenId();
		temp="减少"+staname+"的金币,招聘消耗";
		tempjifen=new BigDecimal(amt).multiply(new BigDecimal(100).multiply(new BigDecimal(total)));
		
		edetail.setJifenDetailScore(tempjifen.toString());
		edetail.setWfjGuizeId(eguize.getWfjGuizeId());
		edetail.setWfjJifenId(jifenId);
		edetail.setJifenDetailState(0);
		edetail.setJifenDetailName(temp);
		edetail.setJifenDetailDate(new Date());
		edetail.setWfjCashId(null);
		beans.add(edetail);
		//积分任务
		WfjTask wt=new WfjTask();
		wt.setWfjTaskId(idgec.getServerID("WfjTask"));
		wt.setStaffId(staffId);
		wt.setWfjGuizeId(eguize.getWfjGuizeId());
		wt.setWfjTaskDate(new Date());
		beans.add(wt);
		String hql1 ="update WfjJifen u set u.wfjJifenScore=u.wfjJifenScore-? where u.staffId=?";
		
		beandao.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql1},new Object[]{tempjifen+"",staffId});

		return "";
	}
	
	/**
	 * 处理库存
	 * @param cbid
	 * @param gbid
	 * @param list
	 * @param comId
	 * @param groupCompanySn
	 * @param staffId
	 * @param staName
	 * @param billType 进销存单据存储的单据类型
	 * @param price 单价
	 * @param count 数量 必须正数
	 * @param money 金额 必须正数
	 * @param goodsId 物品id
	 * @param kucunName 库存名称
	 * @param subtract 默认false 入库,,true为出库.
	 * @return true成功 false 失败
	 */
	private boolean ruku(String cbid,String gbid,List<BaseBean> list,String comId,String groupCompanySn,
			String staffId,String staName,String billType,String price,Float count,String money,
			String goodsId,String kucunName,boolean subtract){
		try {
			
			String stockInvId=idgec.getServerID("stockInv");
			String inventoryId=idgec.getServerID("Inventory");
			
			//获取库存名称的信息
			String depotHql="from DepotManage where depotPID=? and companyID=? and depotName=? and depotState=?";
			DepotManage depot=(DepotManage)beandao.getBeanByHqlAndParams(depotHql, new Object[]{"003",comId,kucunName,"00"});
			//进销存单据
			FinancialBill fin=new FinancialBill();
			fin.setFinancialbillID(idgec.getServerID("FinancialBill"));
			fin.setCashierBillsID(cbid);
			fin.setGroupCompanySn(groupCompanySn);
			fin.setCompanyID(comId);
			fin.setOrganizationID(null);
			fin.setStaffsID(staffId);			//接收人Id
			fin.setStaffsName(staName);      //接收人Name
			fin.setJournalNum(idgec.getBillID(comId));
			fin.setBillsDate(new Date());
			fin.setBillStaffID(staffId);
			fin.setBillStaffName(staName);
			fin.setBillsType(billType);
			fin.setDepotID(depot.getDepotID()); 
			fin.setDepotName(depot.getDepotName()); 
			list.add(fin);
			
			Object[] obj={comId, goodsId};
			//获取该物品类型的最高序号

			
			String invHql="from Inventory where companyID=? and goodsID=? and warehouse=?";
			Inventory inv=(Inventory)beandao.getBeanByHqlAndParams(invHql, new Object[]{comId,goodsId,depot.getDepotID()});
			
			//获取和goodsID对应的物品信息

			String manageHql="from ProductPackaging where companyID=? and goodsID=?";
			ProductPackaging manage=(ProductPackaging)beandao.getBeanByHqlAndParams(manageHql, obj);
			
			/**
			 * 入库动作
			 */
			
			//库存盘点表
			stockInv sto=new stockInv();
			sto.setStockinvID(stockInvId);
			sto.setCompanyID(comId);
			sto.setGroupCompanySn(groupCompanySn);
			sto.setGoodsBillsId(gbid);
			sto.setGoodsID(manage.getGoodsID());
			sto.setGoodsType(manage.getTradeID());
			sto.setGoodsName(manage.getGoodsName());
			sto.setInvenQuantity(count+"");			//物品数量
			sto.setSummoney(money);				//总价
			sto.setIntime(new Date());
			if(subtract){
				sto.setType("01");
			}else{
				sto.setType("00");
			}
			
			sto.setWarehouse(depot.getDepotID());
			sto.setWarehouseName(depot.getDepotName());
			list.add(sto);
			
			//库存表
			if(inv==null){
				inv=new Inventory();
				inv.setInventoryID(inventoryId);
				inv.setCompanyID(comId);
				inv.setGroupCompanySn(groupCompanySn);
				inv.setWarehouse(depot.getDepotID());
				inv.setWarehouseName(depot.getDepotName());
				inv.setGoodsID(manage.getGoodsID());
				inv.setGoodsName(manage.getGoodsName());
				inv.setGoodsType(manage.getTradeID());
				inv.setStandard(manage.getStandard());
				inv.setGoodsCoding(manage.getGoodsNum());
				inv.setUnitPrice(price);			//物品单价
				inv.setInvenQuantity(count+"");	//物品数量
				inv.setSumPrice(money);			//总价
				inv.setGoodstatus("00");
				list.add(inv);
			}else{
				inv.setInvenQuantity(Float.parseFloat(inv.getInvenQuantity())+count+"");
				BigDecimal ret=new BigDecimal(inv.getSumPrice());
				if(subtract){ //默认false 入库,,true为出库.
					ret.subtract(new BigDecimal(money));
				}else{
					ret.add(new BigDecimal(money));
				}
				inv.setSumPrice(ret.toString());
				list.add(inv);
			}
			subtract= true;
		} catch (Exception e) {
			e.printStackTrace();
			subtract= false;
		}
		return subtract;
	}
	

}
