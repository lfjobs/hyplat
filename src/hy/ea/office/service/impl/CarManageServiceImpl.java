package hy.ea.office.service.impl;

import com.tiantai.wfj.bo.PosDevice;
import com.tiantai.wfj.service.GoldOrderService;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.*;
import hy.ea.bo.production.AttriProduction;
import hy.ea.office.action.CarMqttService;
import hy.ea.office.service.CarManageService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.ImageCut;
import hy.ea.util.Similarity;
import hy.ea.util.ToChineseFirstLetter;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.util.JushMain;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
public class CarManageServiceImpl implements CarManageService {

	private Logger logger = LoggerFactory.getLogger(CarManageServiceImpl.class);
	@Resource
	private BaseBeanDao beandao;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private GoldOrderService soService;
	@Resource
	private UpLoadFileService fileService;

	private static String timing = "金币计时";
	/**
	 * 转换list *
	 * 
	 * @param hql
	 *            hql语句
	 * @param object
	 *            所需参数的数组
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List transition(String hql, Object[] object) {
		return beandao.getListBeanBySqlAndParams(hql, object);
	}
	
	/**
	 * 分页
	 * 
	 * @param pageNumber
	 *            当前页
	 * @param pageSize
	 *            显示条数
	 * @param sql
	 *            sql语句
	 * @param sqlcount
	 *            总记录数SQL语句
	 * @param params
	 *            参数
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm getPageFormBySQL(int pageNumber, int pageSize, String sql,
			String sqlcount, Object[] params) {
		int count = beandao.getConutByBySqlAndParams(sqlcount, params);// 总条数
		if (count == 0)
			return null;
		int pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
		if (pageNumber < 1)
			pageNumber = 1;
		if (pageNumber > pageCount)
			pageNumber = pageCount;
		int firstResult = pageSize * (pageNumber - 1);
		int maxResult = Math.min(pageSize, count - firstResult);
		List<BaseBean> listBaseBean = beandao.getConutByBySqlAndParamsAndPage(
				sql, params, firstResult, maxResult);
		PageForm pageForm = new PageForm();
		pageForm.setPageSize(pageSize);
		pageForm.setRecordCount(count);
		pageForm.setPageCount(pageCount);
		pageForm.setPageNumber(pageNumber);
		pageForm.setList(listBaseBean);
		return pageForm;
	}
	

	/**
	 * 判断时间是否在时间段内 *
	 * 
	 * @param date
	 *            当前时间 yyyy-MM-dd HH:mm:ss
	 * @param strDateBegin
	 *            开始时间
	 * @param strDateEnd
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean isInDate(Date date, String strDateBegin, String strDateEnd) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(date);

		// 截取当前时间时分秒
		int strDateH = Integer.parseInt(strDate.substring(11, 13));
		int strDateM = Integer.parseInt(strDate.substring(14, 16));
		int strDateS = Integer.parseInt(strDate.substring(17, 19));

		// 截取开始时间时分秒
		int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
		int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));
		int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));

		// 截取结束时间时分秒
		int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
		int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
		int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));

		if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {

			// 当前时间小时数在开始时间和结束时间小时数之间
			if (strDateH > strDateBeginH && strDateH < strDateEndH) {
				return true;
				// 当前时间小时数等于开始时间小时数，分钟数在开始和结束之间
			} else if (strDateH == strDateBeginH && strDateM >= strDateBeginM
					&& strDateM <= strDateEndM) {
				return true;
				// 当前时间小时数等于开始时间小时数，分钟数等于开始时间分钟数，秒数在开始和结束之间
			} else if (strDateH == strDateBeginH && strDateM == strDateBeginM
					&& strDateS >= strDateBeginS && strDateS <= strDateEndS) {
				return true;
				// 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数小等于结束时间分钟数
			} else if (strDateH >= strDateBeginH && strDateH == strDateEndH
					&& strDateM <= strDateEndM) {
				return true;
				// 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数等于结束时间分钟数，秒数小等于结束时间秒数
			} else if (strDateH >= strDateBeginH && strDateH == strDateEndH
					&& strDateM == strDateEndM && strDateS <= strDateEndS) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	/**
	 * 获取总时间 *
	 * 
	 * @param date
	 *            结束时间
	 * @param Indate
	 *            开始时间
	 * @return
	 */
	public String change(Date date, Date Indate) {
		long seconds = (date.getTime() - Indate.getTime()) / 1000;// 除以1000是为了转换成秒搜索
		long temp=0;
		StringBuffer sb=new StringBuffer();
		temp = seconds/3600;
		sb.append((temp<10)?"0"+temp+"时":""+temp+"时");

		temp=seconds%3600/60;
		sb.append((temp<10)?"0"+temp+"分":""+temp+"分");

		temp=seconds%3600%60;
		sb.append(((temp<10)?"0"+temp:""+temp)+"秒");

		return sb.toString();
	}
	
	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * 
	 * @param str1
	 *            时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2
	 *            时间参数 2 格式：2009-01-01 12:00:00
	 * @return String 返回值为：xx天xx小时xx分xx秒
	 */
	@SuppressWarnings("unused")
	private static long[] getDistanceTime(String str1, String str2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long[] times = { day, hour, min, sec };
		return times;
	}
	
	

	/**
	 * 离开停车场时获取该车的停车费用 *
	 * 
	 * @param indate
	 *            进入停车场的时间
	 * @param outdate
	 *            离开停车场的时间
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String amount(Date indate, Date outdate, String equipmentNumber, String carNumber,CarManage cm) {
		
		String money = null;
		
		// 获取场地信息
		VenueInformation vf = (VenueInformation) beandao.getBeanByHqlAndParams("select v from EquipmentInformation e,VenueInformation v where e.equipmentNumber=? and e.siteId=v.siteId", new Object[]{equipmentNumber});
		//查询是否为包月/包年用户
		TimingCharging tc = (TimingCharging) beandao.getBeanByHqlAndParams("from TimingCharging where carNumber=? and siteId=? and state=?", new Object[]{carNumber,vf.getSiteId(),"00"});
		//查询公司收费标准
		String carType = "p";
		if(carNumber.indexOf("学")!=-1){
			carType = "c";

		}
		Object ppko = null;//天
		//String hql = "select p.price,f.timeUnits,f.feeMini from FeeScale f,ProductPackaging p where f.siteId=? and f.startUsing = ? and f.goodsID=p.goodsID and p.type like ?  and f.carType = ? and f.timeUnits = ?";
	//	ppko =  beandao.getObjectByHqlAndParams(hql, new Object[]{vf.getSiteId(),"00","%金币计时%",carType,"0"});
		StringBuilder sb = new StringBuilder();
		sb.append("select to_char(s.re_price * (1 + nvl(sz.total_pct, 0) / 100),'fm999999990.00'),f.timeUnits,f.feeMini from dt_feescale f");
		sb.append(" left join dt_productpackaging p on f.goodsid = p.goodsid left join dt_pro_setup s on p.ppid = s.ppid");
		sb.append(" left join dt_ccom_com m on f.companyid = m.compnay_id");
		sb.append(" left join dtcontactcompany y on m.ccompany_id = y.ccompanyid left join dt_set_subsidize sz on y.industrytype = sz.gtid and stutas = '01'");
		sb.append(" where f.siteid = ? and f.carType = ? and f.startUsing = '00' order by f.timeunits");
		List<Object> ppklist = baseBeanService.getListBeanBySqlAndParams(sb.toString(),new Object[]{vf.getSiteId(),carType});
		String timeUnits = "0";
		String feeMini = "0";
		Object[] ppk = null;
		if(ppklist.size()>0){
			 ppk = (Object[])ppklist.get(0);
			 timeUnits = ppk[1]==null?"":ppk[1].toString();
		     feeMini = ppk[2]==null?"":ppk[2].toString();
		}
		DecimalFormat df = new DecimalFormat("0.00");
		if(tc!=null)
		{
			if (indate.getTime()>tc.getEndDate().getTime()) //过期了
			{

				if(ppk!=null){
					double i = (outdate.getTime() - indate.getTime()) /1000;
					if(i/60<Double.parseDouble(feeMini)){
						//小于免费时长不收费
						cm.setChargeState("05");
						cm.setChargeType("01");
						money = "0";
						System.out.println("过期发现小于免费时长");
					}else {
						double c = 0;
						if (timeUnits.equals("0")) { //如果按小时
							c = Math.ceil(i / 3600) * Double.parseDouble(ppk[0] != null ? ppk[0].toString() : "0");
						} else if (timeUnits.equals("1")) {//按天收
							c = Math.ceil(i / 3600/24) * Double.parseDouble(ppk[0] != null ? ppk[0].toString() : "0");
						}
						cm.setChargeState("00");
						cm.setChargeType("00");
						money = String.valueOf(df.format(c));
					}

				}
			}
			else
			{
				if(tc.getStartDate().getTime()>indate.getTime() || tc.getEndDate().getTime()<outdate.getTime())
				{
					if(ppk!=null){
						double a = ((tc.getStartDate().getTime()>indate.getTime())?(tc.getStartDate().getTime()-indate.getTime())/1000:0);
						double b = ((tc.getEndDate().getTime()<outdate.getTime())?(outdate.getTime()-tc.getEndDate().getTime())/1000:0);
						if((a+b)/60<Double.parseDouble(feeMini)){
							//小于免费时长不收费
							cm.setChargeState("05");
							cm.setChargeType("01");
							money = "0";
							System.out.println("否则过期发现小于免费时长");
						}else {
							double c = 0;
							if (timeUnits.equals("0")) { //如果按小时
								c = Math.ceil((a+b)/3600)*Double.parseDouble(ppk[0]!=null?ppk[0].toString():"0");
							} else if (timeUnits.equals("1")) {//按天收
								c = Math.ceil((a+b) / 3600/24) * Double.parseDouble(ppk[0] != null ? ppk[0].toString() : "0");
							}
							cm.setChargeState("00");
							cm.setChargeType("00");
							money = String.valueOf(df.format(c));
						}

					}


					else
					{
						money = "0";
					}
				}
			 	else
				{
					if(tc.getRemark()!=null &&tc.getRemark().equals("人工审核通过")){
						cm.setChargeState("06");
					}else{
						cm.setChargeState("01");
					}
					cm.setChargeType("01");
					money = "0";
				}
			}
		}
		else
		{
			//没有买包天/包月/包年

			if(ppk!=null){
				double i = (outdate.getTime() - indate.getTime()) /1000;
				if(i/60<Double.parseDouble(feeMini)){
					//小于免费时长不收费
					cm.setChargeState("05");
					cm.setChargeType("01");
					money = "0";
					System.out.println("没包月现小于免费时长");
				}else {
					double c = 0;
					if (timeUnits.equals("0")) { //如果按小时
						c = Math.ceil(i / 3600) * Double.parseDouble(ppk[0] != null ? ppk[0].toString() : "0");
					} else if (timeUnits.equals("1")) {//按天收
						 c = Math.ceil(i / 3600/24) * Double.parseDouble(ppk[0] != null ? ppk[0].toString() : "0");
					}
					cm.setChargeState("00");
					cm.setChargeType("00");
					money = String.valueOf(df.format(c));
				}

			}
		}
		return money;
	}




	public PageForm getFeeList(int pageNumber,int pageSize,VenueInformation vf,String companyID){

		List<String> list = new ArrayList<String>();

		StringBuilder sb = new StringBuilder();
		sb.append("select e.feecid,e.chargenumber,p.price,e.siteid,v.sitenumber, ");
		sb.append("v.sitename,e.staffid,e.staffname,e.startusing,e.timeUnits,e.carType,e.feeMini,ps.ef_price,ps.re_price,ps.brokerage,ps.suid,e.isTotalPct,e.timeType,p.ppid,p.goodsid ");
		sb.append("from dt_feescale e, dt_productpackaging p,dt_pro_setup ps,dt_venueinformation v ");
		sb.append("where e.companyid = ? and e.startusing != ? and v.status = ?  and v.SITETYPE=1 ");
		sb.append("and e.goodsid = p.goodsid and e.siteid = v.siteid and ps.ppid = p.ppid ");
		list.add(companyID);
		list.add("02");
		list.add("00");
		if (vf!=null && vf.getSiteNumber()!=null && !vf.getSiteNumber().equals(""))
		{
			sb.append("and (v.siteNumber like ? or  v.siteName like ?)");
			list.add("%"+vf.getSiteNumber()+"%");
			list.add("%"+vf.getSiteNumber()+"%");
		}
		sb.append("order by p.packagingdate desc");

		PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber,pageSize,sb.toString(),
				"select count(*) from (" + sb.toString() + ")",list.toArray());


		return  pageForm;
	}

	/**
	 *
	 * 同一个公司同一个场地同一种类型同一个时间单位只能有一条数据
	 * @param companyID
	 * @param siteID
	 * @param carType
	 * @param timeUnits
	 * @return
	 */
	public boolean checkAddFee(String companyID,String siteID,String carType,String timeUnits,String feecID){
      boolean  bool = true;
      List<Object> params = new ArrayList<Object>();
      String hql = "from FeeScale where (startUsing = ? or startUsing = ?) and siteId = ? and CompanyID = ? and carType = ? and  timeUnits = ?";
		params.add("00");
		params.add("01");
		params.add(siteID);
		params.add(companyID);
		params.add(carType);
		params.add(timeUnits);
      if(feecID!=null&&!feecID.equals("")){
		  hql+=" and FeecID!=?";
		  params.add(feecID);
	  }
       List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,params.toArray());
       if(list.size()>0){
		   bool = false;
	   }
		return bool;
	}

	/**
	 * 添加修改停车标准 *
	 * 
	 * @param feeScale
	 *            停车标准参数
	 * @return
	 */
	@Override
	@Transactional
	public boolean addOrUpdatefeescale(FeeScale feeScale,ProSetup proSetup,Map<String, ProSetupSub> pssMap) {
		
		try {
			if (feeScale != null) 
			{

				if(!"1".equals(feeScale.getIsTotalPct())){
					feeScale.setIsTotalPct("0");
				}
				if(!"1".equals(feeScale.getTimeUnits())){
					feeScale.setTimeType("");
				}
				List<BaseBean> baselist = new ArrayList<BaseBean>();
				List<String> hqls = new ArrayList<String>();
				List<Object> parms = new ArrayList<Object>();
				//修改停车标准
				if (feeScale.getFeecID() != null
						&& !feeScale.getFeecID().equals("")) 
				{
					FeeScale fs = (FeeScale) beandao.getBeanByHqlAndParams(
							"from FeeScale where feecID=?",
							     new Object[] { feeScale.getFeecID() });
					fs.setStaffID(feeScale.getStaffID());
					fs.setStaffName(feeScale.getStaffName());
					fs.setCompanyID(feeScale.getCompanyID());
					fs.setTimeUnits(feeScale.getTimeUnits());
					fs.setFeeMini(feeScale.getFeeMini());
					fs.setCarType(feeScale.getCarType());
					fs.setTimeType(feeScale.getTimeType());
					fs.setIsTotalPct(feeScale.getIsTotalPct());
					baselist.add(fs);
					//修改资费
					ProductPackaging productPackaging = (ProductPackaging) beandao.getBeanByHqlAndParams("from ProductPackaging where goodsID=?", new Object[]{fs.getGoodsID()});
					productPackaging.setPrice(proSetup.getEfPrice());

					if("0".equals(feeScale.getTimeUnits())) {
						productPackaging.setType(timing);
						productPackaging.setGoodsName(timing);

					}else if("1".equals(feeScale.getTimeUnits())) {
						productPackaging.setType("包天计时");
						productPackaging.setGoodsName("包天计时");

					}else if("2".equals(feeScale.getTimeUnits())) {
						productPackaging.setType("包月计时");
						productPackaging.setGoodsName("包月计时");

					}else if("3".equals(feeScale.getTimeUnits())) {
						productPackaging.setType("包年计时");
						productPackaging.setGoodsName("包年计时");


					}
					baselist.add(productPackaging);



					GoodsManage goodsManage = (GoodsManage) beandao.getBeanByHqlAndParams("from GoodsManage where goodsID=?", new Object[]{fs.getGoodsID()});
					if("0".equals(feeScale.getTimeUnits())) {
						goodsManage.setTypeID(timing);
						goodsManage.setGoodsName(timing);
					}else if("1".equals(feeScale.getTimeUnits())) {
						goodsManage.setTypeID("包天计时");
						goodsManage.setGoodsName("包天计时");
					}else if("2".equals(feeScale.getTimeUnits())) {
						goodsManage.setTypeID("包月计时");
						goodsManage.setGoodsName("包月计时");
					}else if("3".equals(feeScale.getTimeUnits())) {
						goodsManage.setTypeID("包年计时");
						goodsManage.setGoodsName("包年计时");


					}
					baselist.add(goodsManage);
					ProSetup ps = (ProSetup) beandao.getBeanByHqlAndParams(
				                "from ProSetup where ppid=?",
				                new Object[]{productPackaging.getPpID()});
					if(ps!=null){
				      	ps.setEfPrice(proSetup.getEfPrice());
				    	ps.setRePrice(proSetup.getRePrice());
						ps.setBrokerage(proSetup.getBrokerage());
					
					}else{
						
						// 佣金设计
					    ps = new ProSetup();
						ps.setSuid(serverService.getServerID("setup"));
						ps.setComId(productPackaging.getCompanyID());
						ps.setPpid(productPackaging.getPpID());

						ps.setState("00");// 给状态赋初始值:00

						ps.setPpname(productPackaging.getGoodsName());
						ps.setEfPrice(proSetup.getEfPrice());
						ps.setRePrice(proSetup.getRePrice());
						ps.setBrokerage(proSetup.getBrokerage());
						ps.setTzType("00");
						ps.setSjdate(new Date());
					}

					BigDecimal dlsum=new BigDecimal(0);
					if(pssMap!=null)
					{
						BigDecimal dl=null;
						for (ProSetupSub pss:pssMap.values())
						{
							ProSetupSub pross = null;
							if(pss.getSuskey() != null){
								pross = (ProSetupSub) beandao.getBeanByHqlAndParams(
										"from ProSetupSub where suskey=?",
										new Object[]{pss.getSuskey()});
							}
							if(pross == null){
								pross = new ProSetupSub();
								pross.setSusid(serverService.getServerID("prosetupsub"));
							}
							if("".equals(pss.getAmount())){
								dl=new BigDecimal(0);
							}else {
								dl=new BigDecimal(pss.getAmount());
							}
							dlsum=dlsum.add(dl);
							pross.setAmount(dl.toString());
							pross.setTypePpid(pss.getTypePpid());
							pross.setPpid(productPackaging.getPpID());
							pross.setSjdate(new Date());
							pross.setSuid(ps.getSuid());
							baselist.add(pross);
							ps.setProxyprice(dlsum.toString());
						}


					}
					baselist.add(ps);
				}
				else 
				{
					//添加停车标准
					GoodsManage goodsManage = new GoodsManage();
					goodsManage.setGoodsID(serverService.getServerID("goodsID"));
					goodsManage.setGoodsName(timing);
					goodsManage.setTypeID(timing);
					goodsManage.setCompanyID(feeScale.getCompanyID());
					// 编号处理
					String hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.typeID=?";
					// 获取拼音码
					String pinyin = ToChineseFirstLetter.getFirstLetter(goodsManage
							.getTypeID());
					String Upstr = pinyin.toUpperCase();// 转换为大写
					int goodscodingnum = beandao.getConutByByHqlAndParams(hql,
							new Object[] { goodsManage.getTypeID() });
					DecimalFormat form = new DecimalFormat("000000");
					String ss = form.format(goodscodingnum + 1);
					goodsManage.setGoodsCoding(Upstr + "_" + ss);
					goodsManage.setFiveClear("2");
					goodsManage.setCreatedate(new Date());
					baselist.add(goodsManage);
					
					// 产品
                    ProductPackaging productPackaging = new ProductPackaging();
                    productPackaging.setDelStatus("00");
                    productPackaging.setProductstate("01");
                    productPackaging.setFiveClear("2");
                    productPackaging.setCompanyID(feeScale.getCompanyID());
                    productPackaging.setStaffID(feeScale.getStaffID());
                    productPackaging.setStaffName(feeScale.getStaffName());
                    productPackaging.setPackagingDate(new Date());
                    productPackaging.setAssemble("00");
                    productPackaging.setGoodsID(goodsManage.getGoodsID());
                    productPackaging.setPrice(proSetup.getEfPrice());
                    productPackaging.setPpID(serverService.getServerID("p"));

                    productPackaging.setType(timing);
					productPackaging.setGoodsName(timing);
                    baselist.add(productPackaging);
                    if("1".equals(feeScale.getTimeUnits())) {
						productPackaging.setType("包天计时");
						goodsManage.setTypeID("包天计时");
						productPackaging.setGoodsName("包天计时");
						goodsManage.setGoodsName("包天计时");
					}else if("2".equals(feeScale.getTimeUnits())) {
						productPackaging.setType("包月计时");
						goodsManage.setTypeID("包月计时");
						productPackaging.setGoodsName("包月计时");
						goodsManage.setGoodsName("包月计时");
					}else if("3".equals(feeScale.getTimeUnits())) {
						productPackaging.setType("包年计时");
						goodsManage.setTypeID("包年计时");
						productPackaging.setGoodsName("包年计时");
						goodsManage.setGoodsName("包年计时");

					}
                    
					// 佣金设计
					ProSetup ps = new ProSetup();
					ps.setSuid(serverService.getServerID("setup"));
					ps.setComId(productPackaging.getCompanyID());
					ps.setPpid(productPackaging.getPpID());

					ps.setState("00");// 给状态赋初始值:00

					ps.setPpname(productPackaging.getGoodsName());
					ps.setEfPrice(proSetup.getEfPrice());
					ps.setRePrice(proSetup.getRePrice());
					ps.setBrokerage(proSetup.getBrokerage());

					ps.setTzType("00");
					ps.setSjdate(new Date());
					BigDecimal dlsum=new BigDecimal(0);
					if(pssMap!=null)
					{
						BigDecimal dl=null;
						for (ProSetupSub pss:pssMap.values())
						{
							ProSetupSub pross = null;
							if(pross == null){
								pross = new ProSetupSub();
								pross.setSusid(serverService.getServerID("prosetupsub"));
							}
							if("".equals(pss.getAmount())){
								dl=new BigDecimal(0);
							}else {
								dl=new BigDecimal(pss.getAmount());
							}
							dlsum=dlsum.add(dl);
							pross.setAmount(dl.toString());
							if(pross.getAmount()!=null&&!pross.getAmount().equals("")&&!pross.getAmount().equals("0")){
								proSetup.setTzType("01");
							}
							pross.setTypePpid(pss.getTypePpid());
							pross.setPpid(productPackaging.getPpID());
							pross.setSjdate(new Date());
							pross.setSuid(ps.getSuid());
							baselist.add(pross);
						}

						ps.setProxyprice(dlsum.toString());

					}
					 baselist.add(ps);
                    
                    
                    
					
					//计时收费
					String hql1 = "from FeeScale where siteId=? and CompanyID=? and startUsing=?";

					FeeScale fs = new FeeScale();
					fs.setFeecID(serverService.getServerID("fs"));
					Calendar calendar = Calendar.getInstance();
					fs.setChargeNumber(String.valueOf(calendar.getTime().getTime()));
					fs.setSiteId(feeScale.getSiteId());
					fs.setStaffID(feeScale.getStaffID());
					fs.setStaffName(feeScale.getStaffName());
					fs.setCompanyID(feeScale.getCompanyID());
					fs.setGoodsID(goodsManage.getGoodsID());
					fs.setTimeUnits(feeScale.getTimeUnits());
					fs.setTimeType(feeScale.getTimeType());
					fs.setIsTotalPct(feeScale.getIsTotalPct());
					fs.setFeeMini(feeScale.getFeeMini());
					fs.setCarType(feeScale.getCarType());
					fs.setStartUsing("00");
					baselist.add(fs);
				}
				beandao.saveBeansListAndexecuteHqlsByParams(baselist,
						hqls.toArray(new String[] {}), parms.toArray());
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 添加修改停车标准 *
	 *
	 *
	 * @return
	 */
	@Override
	@Transactional
	public String  updatefeescale(FeeScale feeScale,ProductPackaging productPackaging,GoodsManage goodsManage) {
		String feecID = "";

		try {
			if (feeScale != null)
			{

				List<BaseBean> baselist = new ArrayList<BaseBean>();

				//修改停车标准
				if (feeScale.getFeecID() != null
						&& !feeScale.getFeecID().equals("")) {
					FeeScale fs = (FeeScale) beandao.getBeanByHqlAndParams(
							"from FeeScale where feecID=?",
							new Object[]{feeScale.getFeecID()});
					fs.setStaffID(feeScale.getStaffID());
					fs.setStaffName(feeScale.getStaffName());
					fs.setCompanyID(productPackaging.getCompanyID());
					fs.setTimeUnits(feeScale.getTimeUnits());
					fs.setFeeMini(feeScale.getFeeMini());
					fs.setCarType(feeScale.getCarType());
					fs.setTimeType(feeScale.getTimeType());
					fs.setIsTotalPct(feeScale.getIsTotalPct());
					fs.setStartUsing("01");
					baselist.add(fs);
					feecID = fs.getFeecID();
				}

				else
				{


					FeeScale fs = new FeeScale();

					if(!"1".equals(feeScale.getIsTotalPct())){
						feeScale.setIsTotalPct("0");
					}
					if(!"1".equals(feeScale.getTimeUnits())){
						feeScale.setTimeType("");
					}
					fs.setFeecID(serverService.getServerID("fs"));
					Calendar calendar = Calendar.getInstance();
					fs.setChargeNumber(String.valueOf(calendar.getTime().getTime()));
					fs.setSiteId(feeScale.getSiteId());
					fs.setStaffID(feeScale.getStaffID());
					fs.setStaffName(feeScale.getStaffName());
					fs.setCompanyID(productPackaging.getCompanyID());
					fs.setGoodsID(goodsManage.getGoodsID());
					fs.setTimeUnits(feeScale.getTimeUnits());
					fs.setTimeType(feeScale.getTimeType());
					fs.setIsTotalPct(feeScale.getIsTotalPct());
					fs.setFeeMini(feeScale.getFeeMini());
					fs.setCarType(feeScale.getCarType());
					fs.setStartUsing("01");
					baselist.add(fs);
					feecID = fs.getFeecID();
				}


				if ("0".equals(feeScale.getTimeUnits())) {
					productPackaging.setType(timing);
					productPackaging.setGoodsName(timing);
					goodsManage.setTypeID(timing);
					goodsManage.setGoodsName(timing);

				} else if ("1".equals(feeScale.getTimeUnits())) {
					productPackaging.setType("包天计时");
					productPackaging.setGoodsName("包天计时");
					goodsManage.setTypeID("包天计时");
					goodsManage.setGoodsName("包天计时");

				} else if ("2".equals(feeScale.getTimeUnits())) {
					productPackaging.setType("包月计时");
					productPackaging.setGoodsName("包月计时");
					goodsManage.setTypeID("包月计时");
					goodsManage.setGoodsName("包月计时");

				} else if ("3".equals(feeScale.getTimeUnits())) {
					productPackaging.setType("包年计时");
					productPackaging.setGoodsName("包年计时");
					goodsManage.setTypeID("包年计时");
					goodsManage.setGoodsName("包年计时");


				}


				beandao.saveBeansListAndexecuteHqlsByParams(baselist,
					null, null);
			}

		} catch (Exception e) {

		}

		return feecID;
	}
	/**
	 *
	 * 获取各种代理佣金
	 * @param suid
	 * @return
	 */
	public List<BaseBean>  allProSetupSub(String suid){
		String hql  ="from ProSetupSub where suid = ?";
		List<BaseBean> list = beandao.getListBeanByHqlAndParams(hql,new Object[]{suid});
        return list;

	}

	/**
	 * 启用收费标准 *
	 * 
	 * @param feecID
	 *         收费标准id
	 * @return
	 */
	@Override
	@Transactional
	public boolean startUsing(String feecID,String startUsing) {
		boolean b = true;
		try {
			FeeScale fs = (FeeScale) beandao.getBeanByHqlAndParams("from FeeScale where feecID=?", new Object[]{feecID});
			fs.setStartUsing(startUsing);
			beandao.update(fs);
		} catch (Exception e) {
			b = false;
		}
		return b;
	}


	/**
	 * 查询公司场地编号 *
	 * 
	 * @param companyID
	 *            公司id
	 * @param    siteId
	 *             场地id
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> queryNumber(String companyID,String siteId) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("select v.siteNumber,v.siteId,v.siteName from ");
		sb.append("dt_VenueInformation v where v.companyId = ? and v.SITETYPE=1 ");
		sb.append("and v.status = ? ");
		List<Object> arr = new ArrayList<Object>();
		arr.add(companyID);
		arr.add("00");
		if (siteId!=null && !siteId.equals(""))
		{
			sb.append("and v.siteId = ? ");
			arr.add(siteId);
		}
		
		List<BaseBean> list = beandao.getListBeanBySqlAndParams(sb.toString(),arr.toArray());
		
		return list;
	}

	/**
	 * 查询公司员工 *
	 * 
	 * @param companyID
	 *            公司id
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> queryStaff(String companyID,String staffname) {
		
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		params.add(companyID);
		params.add("90");
		params.add("50");
		params.add("01");
		
		sb.append("select s.staffid,s.staffname,c.companyid,s.headimage from ");
		sb.append("dtcos c,dt_hr_staff s ,T_ESHOP_CUSCOM t ");
		sb.append("where c.staffid = s.staffid and s.sccid=t.sccid and c.companyid = ? and ");
		sb.append("c.organizationid != ? and c.cosstatus = ? and c.status=? ");

		if(staffname!=null&&!staffname.equals("")){
			sb.append(" and (s.reference = ? or s.staffname like ?)");
			params.add(staffname);
			params.add("%"+staffname+"%");
		}
		
		List<BaseBean> list = beandao.getListBeanBySqlAndParams(sb.toString(), params.toArray());
		
		return list;
	}

	/**
	 * 查询收费标准 *
	 * 
	 * @param feecID
	 *            收费标准id
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FeeScale queryStandard(String feecID) {
		
		String hql = "from FeeScale where feecID=?";
		
		FeeScale feeScale = (FeeScale) beandao.getBeanByHqlAndParams(hql, new Object[]{feecID});
		
		return feeScale;
	}

	/**
	 * 删除收费标准 *
	 * 
	 * @param feecID
	 *            收费标准id
	 * @return
	 */
	@Override
	@Transactional
	public boolean delStandard(String feecID) {
		boolean b = true;
		try {
			List<BaseBean> baselist = new ArrayList<BaseBean>();
			List<String> hqls = new ArrayList<String>();
			List<Object> parms = new ArrayList<Object>();
			
			//删除收费标准表
			String hql = "from FeeScale where feecID = ?";
			FeeScale fs = (FeeScale) beandao.getBeanByHqlAndParams(hql, new Object[]{feecID});
			fs.setStartUsing("02");
			baselist.add(fs);
			
			//删除产品表
			ProductPackaging productPackaging = (ProductPackaging) beandao.getBeanByHqlAndParams("from ProductPackaging where goodsID=?", new Object[]{fs.getGoodsID()});
			productPackaging.setDelStatus("01");
			baselist.add(productPackaging);
			
			beandao.saveBeansListAndexecuteHqlsByParams(baselist,
					hqls.toArray(new String[] {}), parms.toArray());
		} catch (Exception e) {
			b = false;
		}
		return b;
	}

	/**
	 * 删除停车信息 *
	 * 
	 * @param carmID
	 *            停车管理id
	 * @return
	 */
	@Override
	@Transactional
	public boolean delVehicleInformation(String carmID) {
		boolean b = true;
		try {
			String hql = "delete from CarManage where carmID = ?";
			beandao.saveBeansListAndexecuteHqlsByParams(null,
					new String[] { hql }, new Object[] { carmID });
		} catch (Exception e) {
			b = false;
		}
		return b;
	}
	
	/**
	 * 查询车辆停车信息 *
	 * 
	 * @param carmID
	 *            车辆停车id
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Object message(String carmID) {
		StringBuilder sb = new StringBuilder();
		sb.append("select c.carmid, c.carmnumber,to_char(c.indate, 'YYYY-MM-DD HH24:MI:SS'),");
		sb.append("to_char(c.outdate, 'YYYY-MM-DD HH24:MI:SS'),v.sitenumber,v.sitename,c.equipmentnumber, ");
		sb.append("f.staffname,c.status,c.carNumber,c.time,v.itslocation,c.money,c.serialNumber,v.siteid,c.panorama,c.picture ");
		sb.append("from dt_carmanage c,dt_equipmentinformation e,dt_venueinformation v,dt_hr_staff f ");
		sb.append("where c.carmid = ? and c.equipmentnumber=e.equipmentnumber and e.siteid=v.siteid and v.SITETYPE=1 ");
		sb.append("and v.staffid=f.staffid order by c.indate desc ");
		return beandao.getObjectBySqlAndParams(sb.toString(), new Object[]{carmID});
	}
	

	/**
	 * 查询车辆停车信息以及对应的信息 *
	 * 
	 * @param carmID
	 *            车辆停车id
	 * @param companyId
	 *            公司id
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> queryVehicleInformation(String carmID,String companyId) {
		Object obj = message(carmID);
		Object[] ob = (Object[]) obj;
		String status = (String) ob[8];
		String s = "";
		if (status.equals("0")) 
		{
			s = "1";
		}
		else if (status.equals("1"))
		{
			s = "0";
		}
		String sql = "select c.carNumber from dt_VenueInformation v,dt_EquipmentInformation e,dt_CarManage c where v.companyId=? and v.siteId=e.siteId and e.equipmentNumber=c.equipmentNumber and c.status=? and v.SITETYPE=1 ";
		List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql, new Object[]{companyId,s});
	 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("obj", obj);
		map.put("list", list);
		return map;
	}

	/**
	 * 修改车辆管理信息 *
	 * 
	 * @param cm
	 *         车辆停车参数
	 * @return
	 */
	@Override
	@Transactional
	public boolean addOrUpdateVehicle(CarManage cm,String companyId) {
		boolean b = true;
		try {
			if (cm != null) {
				if (cm.getCarmID() != null && !cm.getCarmID().equals(""))//修改
				{
					CarManage cmg = (CarManage) beandao.getBeanByHqlAndParams(
							"from CarManage where carmID=?",
							new Object[] { cm.getCarmID() });
					cmg.setCarNumber(cm.getCarNumber());
					beandao.update(cmg);
				}
				else
				{//新增
					CarManage car = new CarManage();
					Date date = new Date();
					if (cm.getStatus().equals("1"))
					{//进入
						StringBuilder sb = new StringBuilder();
						sb.append("select c from CarManage c, EquipmentInformation e, VenueInformation f ");
						sb.append("where c.carNumber = ? and c.status=? and ");
						sb.append("c.equipmentNumber = e.equipmentNumber and e.siteId=f.siteId ");
						sb.append("and f.companyId=? ");
						  
						CarManage cc = (CarManage) beandao.getBeanByHqlAndParams(sb.toString(), new Object[]{cm.getCarNumber(),"1",companyId});
						if (cc!=null)
						{
							cc.setStatus("0");
							beandao.update(cc);
						}
						car.setCarmID(serverService.getServerID("carm"));
						Calendar calendar = Calendar.getInstance();
						car.setCarmNumber(String.valueOf(calendar.getTime().getTime()));
						car.setCarNumber(cm.getCarNumber());
						car.setIndate(date);
						car.setEquipmentNumber(cm.getEquipmentNumber());
						String sql = "select nvl(max(c.serialNumber),0) from dt_VenueInformation v,dt_EquipmentInformation e,dt_CarManage c where v.companyId=? and v.siteId=e.siteId and e.equipmentNumber=c.equipmentNumber and v.SITETYPE=1 ";
						Object digital = beandao.getObjectBySqlAndParams(sql, new Object[]{companyId});
						int  a= Integer.parseInt(digital+"")+1;
						String s = String.valueOf(a);
						car.setSerialNumber(s);
						car.setStatus(cm.getStatus());
						car.setModel(cm.getModel());
						car.setCreatedate(date);
						beandao.save(car);
					}
					else if (cm.getStatus().equals("0"))
					{//离开
						String hql = "from CarManage where carNumber=? and status=?";
						car = (CarManage) beandao.getBeanByHqlAndParams(hql,
								new Object[] { cm.getCarNumber(), "1" });
						if (car!=null)
						{
							car.setOutdate(date);
							car.setMoney(amount(car.getIndate(), car.getOutdate(),car.getEquipmentNumber(), car.getCarNumber(),car));
							car.setTime(change(car.getOutdate(), car.getIndate()));
							car.setStatus(cm.getStatus());
							car.setModel(cm.getModel());
							car.setCreatedate(date);
							beandao.update(car);
						}
						else
						{
							car = new CarManage();
							car.setCarmID(serverService.getServerID("carm"));
							Calendar calendar = Calendar.getInstance();
							car.setCarmNumber(String.valueOf(calendar.getTime().getTime()));
							car.setCarNumber(cm.getCarNumber());
							car.setOutdate(date);
							car.setEquipmentNumber(cm.getEquipmentNumber());
							car.setMoney("0");
							car.setTime("0时0分");
							car.setStatus("2");
							car.setModel(cm.getModel());
							car.setCreatedate(date);
							beandao.save(car);
						}
					}
				}
			}
			else
			{
				b = false;
			}
		} catch (Exception e) {
			b = false;
		}
		return b;
	}

	/**
	 * 查询场地详情以及人员信息 *
	 * 
	 * @param siteId
	 *            场地id
	 * @param companyId
	 *            公司id
	 * @param siteJudge
	 *            用于判断需不需要查询场地详情,true:查,false:不查
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> siteDetails(String siteId, boolean siteJudge,
			String companyId) {
		StringBuilder sb = new StringBuilder();
		Object obj = null;
		if (siteJudge)
		{
			sb.append("select v.siteid,y.companyname,v.sitenumber,v.sitename, ");
			sb.append("v.itslocation,v.sitearea,v.fieldcapacity,v.staffid,v.sitekey,v.eastLongitude,v.northLatitude ");
			sb.append("from dt_venueinformation v, dt_ccom_com c, dtcontactcompany y ");
			sb.append("where v.siteid = ? and v.companyid = c.compnay_id and v.SITETYPE=1 ");
			sb.append("and c.ccompany_id = y.ccompanyid ");
			obj = beandao.getObjectBySqlAndParams(sb.toString(), new Object[]{siteId});
		}
		else
		{
			sb.append("select c.compnay_id,y.companyname ");
            sb.append("from dt_ccom_com c,dtcontactcompany y ");
            sb.append("where c.compnay_id=? and c.ccompany_id=y.ccompanyid ");
            obj = beandao.getObjectBySqlAndParams(sb.toString(), new Object[]{companyId});
		}
		
		List<BaseBean> list = queryStaff(companyId,"");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("obj", obj);
		map.put("list", list);
		return map;
	}

	/**
	 * 添加修改场地信息 *
	 * 
	 * @param vf
	 *         场地参数
	 * @return
	 */
	@Override
	@Transactional
	public boolean addOrUpdateSiteDetails(VenueInformation vf) {
		boolean b = true;
		try {
			if (vf != null) {
				vf.setSiteDate(new Date());
				vf.setStatus("00");
				if (vf.getSitekey() == null || vf.getSitekey().equals(""))
				{
					if (vf.getSitekey().equals("")) {
						vf.setSitekey(null);
					}
					vf.setSiteId(serverService.getServerID("vf"));
				}
				beandao.saveOrUpdate(vf);
			}
		} catch (Exception e) {
			b = false;
		}
		return b;
	}

	/**
	 * 删除场地 *
	 * 
	 * @param siteId
	 *         场地id
	 * @return
	 */
	@Override
	@Transactional
	public boolean delSite(String siteId) {
		boolean b = true;
		try {
			String sql = "update dt_VenueInformation v set v.status = ? where siteId = ? and v.SITETYPE=1 ";
			beandao.saveBeansListAndexecuteSqlsByParams(null,
					new String[] { sql }, new Object[] { "01",siteId });
		} catch (Exception e) {
			b = false;
		}
		return b;
	}

	/**
	 * 删除设备 *
	 * 
	 * @param equipmentId
	 *         设备id
	 * @return
	 */
	@Override
	@Transactional
	public boolean removeEquipment(String equipmentId) {
		boolean b = true;
		try {
			String sql = "delete from dt_EquipmentInformation e where equipmentId = ? ";
			beandao.saveBeansListAndexecuteSqlsByParams(null,
					new String[] { sql }, new Object[] { equipmentId });
		} catch (Exception e) {
			b = false;
		}
		return b;
	}

	/**
	 * 查询设备详情以及场地信息 *
	 * 
	 * @param equipmentId
	 *            设备id
	 * @param companyId
	 *            公司id
	 * @param siteJudge
	 *            用于判断需不需要查询设备详情,true:修改,false:添加
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> deviceLnfo(String equipmentId,
			boolean siteJudge, String companyId) {
		StringBuilder sb = new StringBuilder();
		Object obj = null;
		if (siteJudge)
		{
			sb.append("select e.equipmentkey,e.equipmentid,y.companyname,e.equipmentNumber, ");
			sb.append("e.devicename,e.unittype,e.manufacturer,v.siteid,v.sitename,e.status,e.channel ");
			sb.append("from dt_equipmentinformation e,dt_venueinformation v,dt_ccom_com c,dtcontactcompany y ");
			sb.append("where e.equipmentid = ? and e.siteid = v.siteid and v.companyid = c.compnay_id and v.SITETYPE=1 ");
			sb.append("and c.ccompany_id = y.ccompanyid ");
			obj = beandao.getObjectBySqlAndParams(sb.toString(), new Object[]{equipmentId});
		}
		else
		{
			sb.append("select c.compnay_id,y.companyname ");
			sb.append("from dt_ccom_com c,dtcontactcompany y ");
			sb.append("where c.compnay_id=? and c.ccompany_id=y.ccompanyid ");
			obj = beandao.getObjectBySqlAndParams(sb.toString(), new Object[]{companyId});
		}
		
		String sql = "select v.siteid,v.sitename from dt_venueinformation v where v.companyid=? and v.status=? and v.SITETYPE=1 ";
		List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql, new Object[]{companyId,"00"});
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("obj", obj);
		map.put("list", list);
		return map;
	}

	/**
	 * 添加或修改设备详情 *
	 * 
	 * @param ef
	 *         设备参数
	 * @return
	 */
	@Override
	@Transactional
	public boolean addOrUpdateDeviceLnfo(EquipmentInformation ef) {
		boolean b = true;
		try {
			if (ef != null) {
				ef.setEquipmentDate(new Date());
				if (ef.getEquipmentkey() == null || ef.getEquipmentkey().equals(""))
				{
					if (ef.getEquipmentkey().equals("")) 
					{
						ef.setEquipmentkey(null);
					}
					ef.setEquipmentId(serverService.getServerID("ef"));
				}
				if (ef.getStatus().equals("00") || ef.getStatus().equals("02")) 
				{
					ef.setInstallationTime(new Date());
				}
				beandao.saveOrUpdate(ef);
			}
		} catch (Exception e) {
			b = false;
		}
		return b;
	}


	/**
	 * 实时效验设备编号 *
	 * 
	 * @param numberOrName
	 *            设备编号
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public boolean verificationfacility(String numberOrName) {
		boolean b;
		EquipmentInformation ef = (EquipmentInformation) beandao.getBeanByHqlAndParams("from EquipmentInformation where equipmentNumber=?", new Object[]{numberOrName});
		
		if (ef!=null)
		{
			b = false;
		}
		else
		{
			b = true;
		}
		return b;
	}
	/**
	 * 实时效验场地编号 *
	 * 
	 * @param numberOrName
	 *            场地编号
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public boolean verificationsite(String numberOrName) {
		boolean b;
		VenueInformation vf = (VenueInformation) beandao.getBeanByHqlAndParams("from VenueInformation where siteNumber=?", new Object[]{numberOrName});
		
		if (vf!=null)
		{
			b = false;
		}
		else
		{
			b = true;
		}
		return b;
	}

	/**
	 * 查询设备编号 *
	 * 
	 * @param siteId
	 *            场地编号
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> queryequipmentNumber(String siteId) {
		
		String sql = "select equipmentNumber from dt_EquipmentInformation where status=? and siteId = ? ";
		List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql, new Object[]{"00",siteId});
		return list;
	}

	/**
	 * 查询车辆记录  *
	 * 
	 * @param companyId 公司id
	 * @param siteId 场地id
	 * @param pageForm 分页信息
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm InformationList(String companyId, String status,
			String siteId,String numberOrName, PageForm pageForm,int pageNumber) {
		List<String> list = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		sb.append("select c.carmID,c.carNumber,to_char(c.indate,'YYYY-MM-DD HH24:MI:SS'),v.siteNumber, ");
		sb.append("v.sitename,e.equipmentNumber,f.staffname,c.status,c.carmNumber,to_char(c.outdate,'YYYY-MM-DD HH24:MI:SS'),c.model,c.chargeType from ");
		sb.append("dt_VenueInformation v,dt_EquipmentInformation e,Dt_Carmanage c,dt_hr_staff f ");
		sb.append("where v.siteid=e.siteid and e.equipmentnumber=c.equipmentnumber and v.staffId=f.staffId and v.SITETYPE=1 ");
		sb.append("and v.companyId = ? and c.status = ? ");
		list.add(companyId);
		list.add(status);
		if (siteId!=null && !siteId.equals(""))
		{
			sb.append("and v.siteId=? ");
			list.add(siteId);
		}
		if ( numberOrName!=null && !numberOrName.equals("") )
		{
			sb.append("and c.carNumber like ? ");
			list.add("%"+numberOrName+"%");
		}
		sb.append("order by c.indate desc");
		PageForm pf = getPageFormBySQL(
				null != pageForm ? pageForm.getPageNumber() : 1,
				pageNumber == 0 ? 10 : pageNumber, sb.toString(),
				"select count(*) from (" + sb.toString() + ")",list.toArray());
		return pf;
	}

	/**
	 * 车牌对比 *
	 * 
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Object> licensePlateComparison(String companyId, String siteId,
			String carNumber) {
		List<Object> list = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("select c.carmID,c.carNumber,to_char(c.indate,'YYYY-MM-DD HH24:MI:SS'),v.siteNumber, ");
		sb.append("v.sitename,e.equipmentNumber,f.staffname,c.status,c.carmNumber, ");
		sb.append("to_char(c.outdate,'YYYY-MM-DD HH24:MI:SS'),c.model,c.panorama,c.picture from ");
		sb.append("dt_VenueInformation v,dt_EquipmentInformation e,Dt_Carmanage c,dt_hr_staff f ");
		sb.append("where v.siteid=e.siteid and e.equipmentnumber=c.equipmentnumber and v.staffId=f.staffId and v.SITETYPE=1 ");
		sb.append("and v.companyId = ? and c.status = ? ");
		list.add(companyId);
		list.add("1");
		if (siteId!=null && !siteId.equals(""))
		{
			sb.append("and v.siteId=? ");
			list.add(siteId);
		}
		sb.append("order by c.indate desc");
		
		List<BaseBean> list1 = beandao.getListBeanBySqlAndParams(sb.toString(), list.toArray());
		list.removeAll(list);
		
		if (list1!=null)
		{
			for (int i = 0; i < list1.size(); i++) {
				Object obj = list1.get(i);
				Object[] obj1 = (Object[]) obj;
				double a = Similarity.SimilarDegree(obj1[1].toString(), carNumber);
				if (a>=0.7)
				{
					list.add(obj);
				}
			}
		}
		
		return list;
	}


	/**
	 * 匹配车辆识别数据 *
	 * 
	 * @param carmID1:错误数据id,carmID2:匹配到的数据id
	 * @return
	 */
	@Override
	@Transactional
	public boolean errorCorrection(String carmID1, String carmID2) {
		boolean b = true;
		try {
			//异常数据
			CarManage cm1 = (CarManage) beandao.getBeanByHqlAndParams("from CarManage where carmID=?", new Object[]{carmID1});
			//匹配数据
			CarManage cm2 = (CarManage) beandao.getBeanByHqlAndParams("from CarManage where carmID=?", new Object[]{carmID2});
			//修改车牌号
			cm2.setCarNumber(cm1.getCarNumber());
			//添加离开时间
			cm2.setOutdate(cm1.getOutdate());
			//修改离开时经过的设备编号
			cm2.setEquipmentNumber(cm1.getEquipmentNumber());
			//计算总时长
			cm2.setTime(change(cm2.getOutdate(),cm2.getIndate()));
			//计算金额
			cm2.setMoney(amount(cm2.getIndate(), cm2.getOutdate(),cm2.getEquipmentNumber(),cm1.getCarNumber(),cm2));
			//修改停车状态
			cm2.setStatus("0");
			//修改创建时间
			cm2.setCreatedate(cm1.getCreatedate());
			//收费方式
			if (cm2.getMoney()!=null)
			{
				
				if (cm2.getMoney().equals("0"))
				{
					logger.error("0元,该车辆已购买包月或包年!");
					//收费方式
					cm2.setChargeType("01");
					cm2.setChargeState("01");//包月或包年
				}
				else
				{
					//获取用户sccid
					Object obj1 = beandao.getObjectByHqlAndParams("select t.sccId from CarInformation c,TEshopCusCom t where c.carNum=? and c.staffID=t.staffid and t.acquiesce=? ", new Object[]{cm2.getCarNumber(),"01"});
					
					if (obj1!=null)
					{
						//获取公司sccid
						StringBuilder sb = new StringBuilder();
						sb.append("select m.sccid ");
						sb.append(" from dt_equipmentinformation e, dt_venueinformation v, t_eshop_cuscom m ");
						sb.append("where e.Equipmentnumber = ? and e.siteid = v.siteid and v.SITETYPE=1 ");
						sb.append("and v.companyid = m.companyid ");
						Object obj2 = beandao.getObjectBySqlAndParams(sb.toString(), new Object[]{cm2.getEquipmentNumber()});
						
						BigDecimal picture = new BigDecimal(cm2.getMoney());   

						logger.error("用户sccid:"+obj2.toString()+"-------公司sccid:"+obj1.toString()+"---------扣除金额:"+picture);
						 /**
					     * 计时收费
					     * @param paySccid 交钱人的paySccid
					     * @param colSccid 收钱的colSccid
					     * @param money 需要支付的金额
					     * @return 01扣金币成功，10扣积分成功，11扣钱不成功（出现异常），00金币和积分都不够
					     */
						String st = soService.timeCharge(obj1.toString(),obj2.toString(),picture,null);
						if(st.equals("01") || st.equals("10"))
						{
							logger.error("扣除成功");
							//收费方式
							cm2.setChargeType("01");
							cm2.setChargeState("02");//积分金币扣除成功
						}
						else if(st.equals("11"))
						{
							logger.error("扣除失败,异常!");
							//收费方式
							cm2.setChargeType("00");
						}
						else if(st.equals("00"))
						{
							logger.error("扣除失败,金币不足!");
							//收费方式
							cm2.setChargeType("00");
							cm2.setChargeState("00");//金币不足未支付
						}
					}
					else
					{
						logger.error("车辆未注册");
						//收费方式
						cm2.setChargeType("00");
						cm2.setChargeState("00");
					}
				}
			}
			else
			{
				logger.error("未设置收费标准");
				//收费方式
				cm2.setChargeType("00");
			}
			//修改匹配数据
			beandao.update(cm2);
			//删除错误数据
			beandao.deleteBeanByKey(CarManage.class, cm1.getCarmkey());
		} catch (Exception e) {
			b = false;
		}
		return b;
	}

	/**
	 * 请求方法 *
	 * 
	// * @param chLicense:车牌号,date:时间
	 * @return 
	 * 
	 * @throws ParseException 
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String programCalls(String carmID) throws ParseException {
		logger.error("进入请求方法");
		String state = "";

		String hql = "from CarManage e where carmID = ?";
		
		CarManage cm = (CarManage) beandao.getBeanByHqlAndParams(hql, new Object[]{carmID});




		if(cm==null){
			return "02";
		}


		String isAudit = "00";//总开关控制是否开启人工审核 00审核
		String numberAudit = "00";//不免人工审核
		VenueInformation venueInformation = (VenueInformation)beandao.getBeanByHqlAndParams("from VenueInformation v where v.siteId = (select n.siteId from EquipmentInformation n where n.equipmentNumber = ?)",new Object[]{cm.getEquipmentNumber()});
		if(venueInformation!=null){
			if(venueInformation.getIsAudit()!=null&&!venueInformation.getIsAudit().equals("")){
				isAudit = venueInformation.getIsAudit();
			}

			CarFeeAudit carFeeAudit = (CarFeeAudit)baseBeanService.getBeanByHqlAndParams("from CarFeeAudit c where c.carNumber = ? and siteId = ?",new Object[]{cm.getCarNumber(),venueInformation.getSiteId()});
			if(carFeeAudit!=null){
				numberAudit = "01";//免人工审核
			}
		}


		//处理消费未0的情况
//		if (cm.getMoney() != null && !cm.getMoney().equals("")) {
//
//			if (new BigDecimal(cm.getMoney()).compareTo(BigDecimal.ZERO) == 0) {
//				cm.setChargeState("05");
//				cm.setChargeType("01");
//				cm.setOpen("1");
//				beandao.update(cm);
//				return "01"; // 消费0元直接开门
//
//			}
//		}
//		if (cm.getStatus().equals("2"))
//		{
//			logger.error("异常数据");
//			state = "00";
//			cm.setOpen("0");
//		}
//		else if (cm.getStatus().equals("0"))
//		{
//			if(cm.getMoney()!=null && !cm.getMoney().equals(""))
//			{
//				//获取用户sccid
//				Object obj1 = beandao.getObjectByHqlAndParams("select t.sccId from CarInformation c,TEshopCusCom t where c.carNum=? and c.staffID=t.staffid and t.acquiesce=? ", new Object[]{cm.getCarNumber(),"01"});
//
//				if (obj1!=null)
//				{
//
//
//					if (!cm.getMoney().equals("0"))
//					{
//						logger.error("金币扣除");
//						//获取公司sccid
//						StringBuilder sb = new StringBuilder();
//						sb.append("select m.sccid ");
//						sb.append(" from dt_equipmentinformation e, dt_venueinformation v, t_eshop_cuscom m ");
//						sb.append("where e.Equipmentnumber = ? and e.siteid = v.siteid ");
//						sb.append("and v.companyid = m.companyid ");
//						Object obj2 = beandao.getObjectBySqlAndParams(sb.toString(), new Object[]{cm.getEquipmentNumber()});
//
//						BigDecimal picture = new BigDecimal(cm.getMoney());
//
//						logger.error("用户sccid:"+obj2.toString()+"-------公司sccid:"+obj1.toString()+"---------扣除金额:"+picture);
//						 /**
//					     * 计时收费
//					     * @param paySccid 交钱人的payScci
//					     * @param colSccid 收钱的colSccid
//					     * @param money 需要支付的金额
//					     * @return 01扣金币成功，10扣积分成功，11扣钱不成功（出现异常），00金币和积分都不够
//					     */
//						String st = soService.timeCharge(obj1.toString(),obj2.toString(),picture,null);
//
//						if(st.equals("01") || st.equals("10"))
//						{
//							logger.error("扣除成功");
//							state = "01";
//							cm.setChargeState("02");//收费具体00 未支付，01:包月或包年 02：积分金币  03：扫码支付 04：现金支付
//							cm.setOpen("1");
//						}
//						else if(st.equals("11"))
//						{
//							logger.error("扣除失败,异常!");
//							state = "00";
//							cm.setChargeState("00");
//							cm.setOpen("0");
//						}
//						else if(st.equals("00"))
//						{
//							logger.error("扣除失败,金币不足!");
//							state = "00";
//							cm.setChargeState("00");
//							cm.setOpen("0");
//
//						}
//					}
//					else
//					{
//						logger.error("0元,该车辆已购买包天包月或包年!");
//						state = "01";
//						cm.setOpen("1");
//
//					}
//				}
//				else {
//					  if(cm.getMoney().equals("0")){
//						state = "01";
//						cm.setOpen("1");
//				      }else{
//						  logger.error("没注册且不等于0");
//						  state = "00";
//						  cm.setChargeState("00");
//						  cm.setOpen("0");
//					  }
//
//
//
//
//				}
//			}
//			else
//			{
//				state = "00";
//				logger.error("公司未设置金额");
//				cm.setOpen("0");
//			}
//			cm.setChargeType(state);
//			beandao.update(cm);
//		}
//		else if (cm.getStatus().equals("1"))
//		{
//			logger.error("进入车辆");
//			state = "01";
//		}

		if(cm.getStatus().equals("0")){
			//保证出去
			if(cm.getMoney()!=null) {
				if (!cm.getMoney().equals("0")) {
					//不等于0
					state = "00";
					cm.setOpen("0");


					if(isAudit.equals("00")&&numberAudit.equals("00")){//开启人工审核并且不是免审核的车 正常判断
						cm.setAuditStatus("00");
					}else{
						cm.setAuditStatus("");
					}

				} else {
					state = "01";
					cm.setOpen("1");
					if("00".equals(cm.getAuditStatus())){
						cm.setAuditStatus("");
					}
				}

				beandao.update(cm);
			}else{
                 System.out.println("金额为空了");
				logger.error("金额为空了");
			}

		}else if (cm.getStatus().equals("1"))
		{
			logger.error("进入车辆");
			state = "01";
		}
		
		return state;
	}
	
	
	
	

	/**
	 * 查询场地列表 *
	 * 
	 * @param hasBeenUnder:已满未满,companyId:公司id,numberOrName:场地名称或编号,pageForm:分页信息
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm siteList(String hasBeenUnder, String companyId,
			String numberOrName, PageForm pageForm,int pageNumber) {
		
		List<String> list = new ArrayList<String>();
		
		StringBuilder sb = new StringBuilder();
		if (hasBeenUnder!=null && !hasBeenUnder.equals("")) 
		{
			sb.append("select * from (");
		}
		sb.append("select a.siteid,y.companyname,a.sitenumber,a.sitename,a.itslocation,a.sitearea,");
		sb.append("a.fieldcapacity,a.sitedate,a.eastLongitude,a.northLatitude,count(d.carmid),(case when count(d.carmid) >= to_number(a.fieldcapacity) then ");
		sb.append("? else ? end) s,abs(count(d.carmid) - a.fieldcapacity),a.isAudit from (select v.siteid,");
		sb.append("v.sitenumber,v.sitename,v.itslocation,v.sitearea,v.fieldcapacity,c.ccompany_id,e.equipmentnumber, ");
		sb.append("to_char(v.sitedate,'YYYY-MM-DD HH24:MI:SS') as sitedate,v.eastLongitude,v.northLatitude,v.isAudit ");
		sb.append("from dt_venueinformation v join dt_ccom_com c on v.companyid = ? and v.status = ? and v.SITETYPE=1 ");
		sb.append("and v.companyid = c.compnay_id left join dt_equipmentinformation e on v.siteid = e.siteid ");
		sb.append(") a left join dtcontactcompany y on a.ccompany_id = y.ccompanyid left join dt_carmanage d on a.equipmentnumber = d.equipmentnumber ");
		list.add("已满");
		list.add("未满");
		list.add(companyId);
		list.add("00");
		if (numberOrName!=null && !numberOrName.equals(""))
		{
			sb.append("where (a.sitename like ? or a.sitenumber like ?) ");
			list.add("%"+numberOrName+"%");
			list.add("%"+numberOrName+"%");
		}
		sb.append("group by a.siteid,y.companyname,a.sitenumber,a.sitename,a.itslocation,a.isAudit,");
		sb.append("a.sitearea,a.fieldcapacity,a.sitedate,a.eastLongitude,a.northLatitude  order by a.sitedate desc ");
		if (hasBeenUnder!=null && !hasBeenUnder.equals("")) 
		{
			sb.append(") b where b.s = ? ");
			list.add(hasBeenUnder);
		}
		pageForm = getPageFormBySQL(
				null != pageForm ? pageForm.getPageNumber() : 1,
				pageNumber == 0 ? 10 : pageNumber, sb.toString(),
				"select count(*) from (" + sb.toString() + ")",list.toArray());
          
		return pageForm;
	}
	
	/**
	 * 停车位管理
	 * 
	 * @param :已满未满,companyId:公司id,numberOrName:场地名称或编号,pageForm:分页信息
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm parkingSpaceList(ParkingSpace parkingSpace,int pageSize,int pageNumber,String companyId) {
		String sql = "select p.parksId,p.parkingCode,p.parkingName,v.siteNumber,v.siteName,p.carType,p.parkingLength,p.parkingWidth,p.staffName,p.createDate,p.status from DT_ParkingSpace p,DT_VENUEINFORMATION v where p.siteId = v.siteId and p.companyId = ? and v.SITETYPE=1 ";
		List<Object> paramlist = new ArrayList<Object>();
		paramlist.add(companyId);
		if(parkingSpace!=null){
		if(parkingSpace.getSiteId()!=null&&!parkingSpace.getSiteId().equals("")){
			sql+=" and (v.siteNumber = ? or v.siteName like ?)";
			paramlist.add(parkingSpace.getSiteId());
			paramlist.add("%"+parkingSpace.getSiteId()+"%");
		}
		if(parkingSpace.getParkingCode()!=null&&!parkingSpace.getParkingCode().equals("")){
			sql+=" and (p.parkingCode = ? or p.parkingName like ?)";
			paramlist.add(parkingSpace.getParkingCode());
			paramlist.add("%"+parkingSpace.getParkingCode()+"%");
		}
		if(parkingSpace.getStatus()!=null&&!parkingSpace.getStatus().equals("")){
			sql+=" and p.status = ?";
			paramlist.add(parkingSpace.getStatus());
		}
		}
		
		PageForm pageForm = getPageFormBySQL(pageSize, pageNumber,sql,
				"select count(*) from (" + sql + ")",paramlist.toArray());
		
		return pageForm;
	}
	
	/**
	 * 
	 * 添加修改停车位
	 * @param parkingSpace
	 * @param companyId
	 * @param staffId
	 */
	public  void addUpdateSpace(ParkingSpace parkingSpace,String companyId,String staffId){
		Staff staff  = (Staff)beandao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staffId});
		
		if(parkingSpace.getParksId()!=null&&!parkingSpace.getParksId().equals("")){
			    String hql = "from ParkingSpace where parksId = ?";
			    ParkingSpace ps = (ParkingSpace)beandao.getBeanByHqlAndParams(hql,new Object[]{parkingSpace.getParksId()});
		        ps.setParkingCode(parkingSpace.getParkingCode());
		        ps.setParkingName(parkingSpace.getParkingName());
		        ps.setSiteId(parkingSpace.getSiteId());
		        ps.setStatus(parkingSpace.getStatus());
		        ps.setCarType(parkingSpace.getCarType());
		        ps.setCreateDate(new Date());
		        ps.setCompanyId(companyId);
		        parkingSpace.setStaffId(staffId);
				parkingSpace.setStaffName(staff.getStaffName());
		        ps.setParkingLength(parkingSpace.getParkingLength());
		        ps.setParkingWidth(parkingSpace.getParkingWidth());
		        beandao.update(ps);
		         
		  }else{
			  
			  parkingSpace.setParksId(serverService.getServerID("parksId"));
			  parkingSpace.setCompanyId(companyId);
			  parkingSpace.setCreateDate(new Date());
			  parkingSpace.setStaffId(staffId);
			  parkingSpace.setStaffName(staff.getStaffName());
			  beandao.save(parkingSpace);
		  }
		
	}
	
	
	
	/**
	 * 
	 * 批量新增停车位
	 * @param parkingSpace
	 * @param companyId
	 * @param staffId
	 */
	public  void batAddSpace(ParkingSpace parkingSpace,String companyId,String staffId,String startNum,String endNum){
		 List<BaseBean> beanlist = new ArrayList<BaseBean>();
		  
		 
		  Staff staff  = (Staff)beandao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staffId});
		  
		  int start = (int) Integer.parseInt(startNum);
		  int end = (int) Integer.parseInt(endNum);
		  String hql = "from ParkingSpace where parkingCode = ? and siteId = ?";

		  for (int i = start; i <= end; i++) {
		        List<BaseBean> spacelist = beandao.getListBeanByHqlAndParams(hql,new Object[]{i+"",parkingSpace.getSiteId()});
		        if(spacelist.size()>0){ 
		        	 continue;
		        }
			  ParkingSpace ps = new ParkingSpace();
			  ps.setParksId(serverService.getServerID("parksId"));
			  ps.setStatus("00");
			  ps.setCarType(parkingSpace.getCarType());
			  ps.setSiteId(parkingSpace.getSiteId());
			  ps.setCompanyId(companyId);
			  ps.setCreateDate(new Date());
			  ps.setStaffId(staffId);
			  ps.setStaffName(staff.getStaffName());
			  ps.setParkingCode(i+"");
			  ps.setParkingName(i+"");
			  ps.setParkingLength(parkingSpace.getParkingLength());
			  ps.setParkingWidth(parkingSpace.getParkingWidth());

			  beanlist.add(ps);
			  
	    	
		  }
		  beandao.saveBeansListAndexecuteHqlsByParams(beanlist, null, null);

	}
	
	/**
	 * 
	 * 删除停车位
	 * @param parksId
	 */
	public void deleteSpace(String parksId){
		String hql = "delete from ParkingSpace where parksId = ?";
		List<Object[]> parmsList = new ArrayList<Object[]>();
		parmsList.add(new Object[]{parksId});
		beandao.executeHqlsByParmsList(null, new String[]{hql} , parmsList);
		

	}
	
	/**
	 * 
	 *更新状态
	 */
	public void updateStatus(String parksId,String status){


	            String sql = "update DT_ParkingSpace e set e.status = ? where e.parksId = ? ";
	            beandao.saveBeansListAndexecuteSqlsByParams(null,
	                    new String[] { sql }, new Object[] { status,parksId });
	       
	}
	/**
	 * 
	 * 获取场地
	 * @return
	 */
	public List<BaseBean> getSiteList(String companyId){

		String sql = "select v.siteid,v.sitename,v.siteNumber,v.EASTLONGITUDE,v.NORTHLATITUDE from dt_venueinformation v where v.companyid=? and v.status=? and v.SITETYPE=1 ";
		List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql, new Object[]{companyId,"00"});

		return list;
	}
	
	/**
	 * 根据场地查询设备
	 * @param  siteId
	 * @return
	 */
	public List<BaseBean> getEquipList(String siteId){
		
		String sql = "select v.siteid,v.equipmentNumber from DT_EquipmentInformation v where  v.status=? and v.siteId = ? and v.SITETYPE=1 ";
		List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql, new Object[]{"00",siteId});
		
		return list;
	}
	
	 /**
     * 停车位编号不重复
     * @param
     * @return
     */
    public String checkParkingNum(String parkingCode,String parksId,String siteId){
        if(parksId==null||parksId.equals("")){
        	parksId= " ";
        }
        String hql = "from ParkingSpace where parkingCode = ? and parksId!=? and siteId = ?";
        List<BaseBean> poslist = beandao.getListBeanByHqlAndParams(hql,new Object[]{parkingCode,parksId,siteId});
        if(poslist.size()==0){

            return "0";//不重复
        }else{
            return "1";//重复
        }

    }
    
    /**
	 * 
	 * 根据设备号查询所在场地有没有停车位了
	 * @param equipmentNumber
	 * @return
	 */
	public 	Map<String,String> searchParkingCode(String equipmentNumber){
		Map<String,String> map = new HashMap<String,String>();
		
		String sql = "select p.parkingCode,p.parksId from DT_ParkingSpace p,DT_EquipmentInformation e where p.siteId = e.siteId and e.equipmentNumber = ? and p.status = ?  order by to_number(p.parkingCode)";
        List<Object> spacelist = beandao.getListObjectBySqlAndParams(sql, new Object[]{equipmentNumber, "00"});
        Object space = null;
        if(spacelist!=null&&spacelist.size()>0){
			space = spacelist.get(0);
		}
        Object[] spaces = (Object[])space;
        if(space!=null){
        	map.put("parkingCode",spaces[0]+"");
        	map.put("parksId",spaces[1]+"");
        	map.put("result","suc");
        	System.out.println(spaces[0]);
			System.out.println(spaces[1]);
        	
        }else{
        	map.put("result","fail");
        }

        return map;
	}
	
	/**
	 * 
	 * 
	 * @param companyId
	 * @param status in 进入 ：out 出
	 */
	public String pushJG(String companyId, String status, String parkingCode,
			String carmID, String equipmentNumber,String tip) {
		String content = "";
		try {
			System.out.println("进入推送");
			System.out.println("carmID:" + carmID);

			String jiguangMark = "";

			String hql = "from PosDevice p where p.accessCcomID = (select c.ccompanyId from CcomCom c where comanyId = ?) and p.equipmentNumber = ?";

			String type = "";
			JSONObject json = new JSONObject();
			List<String> slist = new ArrayList<String>();// 极光推送设备号
			Object[] spbjs = null;

			String state = null;
			String chargeType = null;
			String chargeState = null;
			String time = null;
			String money = null;
			String carNum = null;
			String equipin = null;
			String equipout = null;
			String auditStatus = null;
			String serialNumber = null;
			String indate = null;
			String outdate = null;
			String siteName = null;
			String siteAddress = null;
			String chargeState1 = "";



			if (carmID != null && !carmID.equals("")) {
				String sql = "select c.serialNumber,c.carNumber,p.parkingCode,to_char(c.indate,'YYYY-MM-DD HH24:MI:SS'),to_char(c.outdate,'YYYY-MM-DD HH24:MI:SS'),c.time,c.money,v.siteName,v.ItsLocation,c.status,c.chargeType,c.chargeState,c.equipmentNumber,v.companyid,c.equipin,c.equipout,c.auditStatus,c.chargeState1  from DT_CARMANAGE c  left join DT_EquipmentInformation e on e.equipmentnumber = c.equipmentnumber left join DT_VENUEINFORMATION v  on e.siteid = v.siteId left join DT_ParkingSpace p  on  c.parksId = p.parksId where carmID = ?";
				Object spobj = beandao.getObjectBySqlAndParams(sql,
						new Object[]{carmID});
				System.out.println("查询到记录");

				if (spobj != null) {

					spbjs = (Object[]) spobj;
					equipmentNumber = spbjs[12].toString();
					if (companyId == null || companyId.equals("")) {
						companyId = spbjs[13] != null ? spbjs[13].toString() : "";
					}
					carNum = spbjs[1].toString();
					state = spbjs[9] != null ? spbjs[9].toString() : "";
					chargeType = spbjs[10] != null ? spbjs[10].toString() : "";
					chargeState = spbjs[11] != null ? spbjs[11].toString() : "";
					time = spbjs[5] != null ? spbjs[5].toString() : "";
					money = spbjs[6] != null ? spbjs[6].toString() : "";
					equipin = spbjs[14] != null ? spbjs[14].toString() : "";
					equipout = spbjs[15] != null ? spbjs[15].toString() : "";
					serialNumber = spbjs[0] != null ? spbjs[0].toString() : "";
					indate = spbjs[3] != null ? spbjs[3].toString() : "";
					indate = spbjs[4] != null ? spbjs[4].toString() : "";
					siteName = spbjs[7] != null ? spbjs[7].toString() : "";
					siteAddress = spbjs[8] != null ? spbjs[8].toString() : "";
					auditStatus = spbjs[16] != null ? spbjs[16].toString() : "";
					chargeState1 = spbjs[17] != null ? spbjs[17].toString() : "";



				}


// 查询是否绑定终端机

				List<BaseBean> poslist = beandao.getListBeanByHqlAndParams(hql,
						new Object[]{companyId, equipmentNumber});

				if (poslist == null || poslist.size() == 0) {
					poslist = beandao.getListBeanByHqlAndParams(hql,
							new Object[]{companyId, equipin});
				}

				if (poslist == null || poslist.size() == 0) {
					poslist = beandao.getListBeanByHqlAndParams(hql,
							new Object[]{companyId, equipout});
				}

				if (poslist != null && poslist.size() > 0) {
					for (int i = 0; i < poslist.size(); i++) {
						PosDevice obj = (PosDevice) poslist.get(i);
						if (obj != null) {
							if (i == poslist.size() - 1) {
								jiguangMark += obj.getPosNum();
							} else {
								jiguangMark += obj.getPosNum() + ",";
							}
						}
					}

				}

				// 保存账号
				String[] arr = jiguangMark.split(",");
				slist = Arrays.asList(arr);
				System.out.println("jiguangMark" + jiguangMark);
				if ("in".equals(status)) {
					// 如果是进入的时候

					if (parkingCode == null || parkingCode.equals("")) {
						// 没有停车位的情况
						content = "对不起，没有停车位了";
						type = "0";// 无停车位，语音播报，跳转页面
					} else {

						if ("2".equals(tip)) {
							content = carNum + "进门请预付款或者联系工作人员人工审核";

						} else if ("3".equals(tip)) {
							content = carNum + "工作人员已提交人工审核请等待审核结果，也可以直接扫码付款";
						} else if ("4".equals(tip)) {
							content = carNum + "给您提交的免费时长设置审核被上级驳回，您可以直接扫码付款。";
						} else {
							if ("0".equals(tip)) {
								//费时长审核已通过可以进入

								if(auditStatus!=null&&!auditStatus.equals("")){
									content = carNum + "人工审核通过，为您分配的停车位编号为" + parkingCode + "正在打印小票请稍后";

								}else{
									if("06".equals(chargeState1)){
										content = carNum + "免费临时车，为您分配的停车位编号为" + parkingCode + "正在打印小票请稍后";

									}
								}
							} if ("8".equals(tip)) {
								  //关闭审核或者免人工审核的时候进门直接进

								content = carNum + "为您分配的停车位编号为" + parkingCode + "正在打印小票请稍后";
							} else {
								if("06".equals(chargeState1)){
									content = carNum + "免费临时车，为您分配的停车位编号为" + parkingCode + "正在打印小票请稍后";

								}else {
									content = carNum + "套餐会员用户，为您分配的停车位编号为" + parkingCode + "正在打印小票请稍后";
								}

							}
							json.accumulate("carmID", carmID); // 跳转页面需要的ID
							json.accumulate("serialNumber", serialNumber);// 停车流水号
							json.accumulate("carNum", carNum);// 车牌号
							json.accumulate("parkingCode", parkingCode);// 停车位编号
							json.accumulate("indate", indate);// 进入时间
							json.accumulate("siteName", siteName);// 场地名称
							json.accumulate("siteAddress", spbjs[8] != null ? spbjs[8].toString() : "");// 场地地址

							//type = "1";// 有停车位，语音播报，打印小票，跳转页面

						}

						type = "0";

					}

				} else if ("out".equals(status)) {
					// 出来
					// 识别车牌后，播报用时，金额，并跳转页面，如果是金币付款，直接提醒金币或积分扣款成功或者包月直接打印小票，否则等待扫码付款

					// 推送播放内容


					if ("2".equals(state)) {

						content = "车牌" + carNum + "出现错误数据,请工作人员处理";// 跳转页面无需打印
						type = "0";
					} else if ("1".equals(state)) {
						content = "车牌" + carNum + "没有出去记录,请重新识别车牌"; // 跳转页面无需打印
						type = "0";

					} else if ("0".equals(state)) {
						if ("01".equals(chargeType)) {// 扣款成功
							if ("01".equals(chargeState)) { // 包月
								content = carNum + "是套餐会员免支付";// 跳转页面无需打印
								type = "0";
							}
							if ("05".equals(chargeState)) { // 消费0元
								content = carNum + "消费0元无需收费";//  跳转页面无需打印
								type = "0";
							}
							if ("06".equals(chargeState)) {
								content = carNum + "免费临时车";//  跳转页面无需打印
								type = "0";
							} else if ("02".equals(chargeState)) {// 积分金币 跳转页面并打印
								content = "车牌" + carNum + ",用时" + time + "共计" + money
										+ "元" + ",积分或金币扣款成功";
								json.accumulate("carmID", carmID); // 跳转页面需要的ID
								json.accumulate("serialNumber", serialNumber);// 停车流水号
								json.accumulate("carNum", spbjs[1].toString());// 车牌号
								json.accumulate("parkingCode", parkingCode);// 停车位编号
								json.accumulate("indate", spbjs[3].toString());// 进入时间
								if (spbjs[4] != null && spbjs[4] != "") {
									json.accumulate("outdate", spbjs[4].toString());// 出去时间
									json.accumulate("time", spbjs[5].toString());// 用时
									json.accumulate("money", spbjs[6].toString());// 金额

								}

								json.accumulate("chargeState", "金币积分支付");// 支付方式
								json.accumulate("siteName", spbjs[7] != null ? spbjs[7].toString() : "");// 场地名称
								json.accumulate("siteAddress", spbjs[8] != null ? spbjs[8].toString() : "");// 场地地址
								//type = "1";
								type = "0";
							}
						} else if ("02".equals(chargeType)) { // 扫码
							content = carNum + "扫码支付成功";//
							type = "0";

						}else if ("03".equals(chargeType)) { // 免费
							if(auditStatus!=null&&auditStatus.equals("02")){
								content = carNum + "人工审核通过";
							}else{
								content = carNum + "免费临时车";
							}

							type = "0";

						}else if ("00".equals(chargeType)) { // 未支付
							if ("00".equals(chargeState)) { // 未支付提示扫码支付
								String t = "";
								if ("00".equals(auditStatus)) {
									t = "请扫描二维码支付或联系工作人员审核";
								} else if ("01".equals(auditStatus)) {
									t = "请扫描二维码支付或等待人工审核";
								} else if ("03".equals(auditStatus)) {
									t = "人工审核驳回，请扫码二维码支付";
								} else {
									t = "请扫描二维码支付";
								}
								content = "车牌" + carNum + ",用时" + time + "共计" + money
										+ "元" + "," + t;
								json.accumulate("carmID", carmID); // 跳转页面需要的ID
								type = "0";


							}

						}

					}
				} else if ("pay".equals(status)) {//付款后推送
					//单独调用mqtt让道闸开门
					String hour="";
					String minute="";
					String second="";
					if(time!=null){
						String[] split = time.split("时");
						if(split.length>0 && split[0]!=null){
							hour = formatNumberToChinese(Integer.parseInt(split[0]));
							if(hour.length()>1){
								hour = hour.replaceAll("0*$", "");
							}
							String[] split1 = split[1].split("分");
							minute = formatNumberToChinese(Integer.parseInt(split1[0]));
							if(minute.length()>1){
								minute = minute.replaceAll("0*$", "");
							}
							String[] split2 = split1[1].split("秒");
							second = formatNumberToChinese(Integer.parseInt(split2[0]));
							if(second.length()>1){
								second = second.replaceAll("0*$", "");
							}
						}
					}
					String moneyTwo="";
					if(money!=null){
						BigDecimal number = new BigDecimal(money);
						number = number.stripTrailingZeros(); // 去除尾随的零
						moneyTwo = number.toPlainString(); // 转换为字符串，此时不再包含小数点和尾随的零
						moneyTwo = formatNumberToChinese(Integer.parseInt(moneyTwo));
						if(moneyTwo.length()>1){
							moneyTwo = moneyTwo.replaceAll("0*$", "");
						}
					}

					LinkedHashMap<String,String> pronunciationgMap=new LinkedHashMap<>();
					LinkedHashMap<String,String> showTextMap=new LinkedHashMap<>();
					LinkedHashMap<String,Object> resultMap=new LinkedHashMap<>();
					pronunciationgMap.put("1",carNum+"_1");
					pronunciationgMap.put("2","2E_2");//停车2E,
					pronunciationgMap.put("3",hour+"_1");//,
					pronunciationgMap.put("4","2F_2");//小时2F,
					pronunciationgMap.put("5",minute+"分"+second+"秒_1");
					pronunciationgMap.put("6","6A_2");//缴费,
					pronunciationgMap.put("7",moneyTwo+"元_1");//多少元,
					pronunciationgMap.put("8","18_2");//成功,
					showTextMap.put(carNum,"01");
					showTextMap.put("停车："+time,"02");
					showTextMap.put("成功缴费："+money+"元","03");
					showTextMap.put("一路平安","04");

					CarMqttService.pronunciation("1234567890",equipmentNumber,pronunciationgMap);//语音
					CarMqttService.xianshishuju("1234567890",equipmentNumber,showTextMap,"ok");//显示屏
					CarMqttService.isOpen("1234567890",equipmentNumber);//开门



					//原始极光推送数据暂时不管，后期可以删除
					content = "车牌" + carNum + "成功支付金额" + money;
					json.accumulate("carmID", carmID); // 跳转页面需要的ID
					json.accumulate("serialNumber", serialNumber);// 停车流水号
					json.accumulate("carNum", spbjs[1].toString());// 车牌号
					json.accumulate("parkingCode", parkingCode);// 停车位编号
					json.accumulate("indate", spbjs[3].toString());// 进入时间
					if (spbjs[4] != null && spbjs[4] != "") {
						json.accumulate("outdate", spbjs[4].toString());// 出去时间
						json.accumulate("time", spbjs[5].toString());// 用时
						json.accumulate("money", spbjs[6].toString());// 金额

					}


					if (("03").equals(chargeState)) {
						json.accumulate("chargeState", "扫码支付");// 支付方式
					} else if (("04").equals(chargeState)) {
						json.accumulate("chargeState", "现金支付");// 支付方式
					}
					json.accumulate("siteName", spbjs[7].toString());// 场地名称
					json.accumulate("siteAddress", spbjs[8].toString());// 场地地址
					//type = "1";
					type = "0";
				}
				System.out.println("content" + content);
				System.out.println(type);
				System.out.println(json.toString());


			} else {
				List<BaseBean> poslist = beandao.getListBeanByHqlAndParams(hql,
						new Object[]{companyId, equipmentNumber});
				type = "0";
				if (poslist != null && poslist.size() > 0) {
					for (int i = 0; i < poslist.size(); i++) {
						PosDevice obj = (PosDevice) poslist.get(i);
						if (obj != null) {
							if (i == poslist.size() - 1) {
								jiguangMark += obj.getPosNum();
							} else {
								jiguangMark += obj.getPosNum() + ",";
							}
						}
					}

				}
				// 保存账号
				String[] arr = jiguangMark.split(",");
				slist = Arrays.asList(arr);
				System.out.println("无记录-----------------------");
			}


			if(content!=null&&!content.equals("")) {
				// 极光推送
				JushMain.sendjiguangMessage(content, type, json.toString(), "parking",
						slist);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
              return content;
	}

	/**
	 * mqttt推送
	 * @param companyId
	 * @param status
	 * @param parkingCode
	 * @param carmID
	 * @param equipmentNumber
	 * @param tip
	 * @return
	 */
	public Map<String,Object> pushMqtt(String companyId, String status, String parkingCode,
						 String carmID, String equipmentNumber,String tip) {
		//组装语音和显示的文字，不再进行极光推送
		LinkedHashMap<String,String> pronunciationgMap=new LinkedHashMap<>();
		LinkedHashMap<String,String> showTextMap=new LinkedHashMap<>();
		LinkedHashMap<String,Object> resultMap=new LinkedHashMap<>();
		String content = "";
		try {

			System.out.println("进入推送");
			System.out.println("carmID:" + carmID);

			String jiguangMark = "";

			String hql = "from PosDevice p where p.accessCcomID = (select c.ccompanyId from CcomCom c where comanyId = ?) and p.equipmentNumber = ?";

			String type = "";
			JSONObject json = new JSONObject();
			List<String> slist = new ArrayList<String>();// 极光推送设备号
			Object[] spbjs = null;

			String state = null;
			String chargeType = null;
			String chargeState = null;
			String time = null;
			String hour=null;
			String minute=null;
			String second=null;
			String money = null;
			String carNum = null;
			String equipin = null;
			String equipout = null;
			String auditStatus = null;
			String serialNumber = null;
			String indate = null;
			String outdate = null;
			String siteName = null;
			String siteAddress = null;
			String chargeState1 = "";
			String dayInfo=null;
			String dayInfos=null;



			if (carmID != null && !carmID.equals("")) {
				String sql = "select c.serialNumber,c.carNumber,p.parkingCode,to_char(c.indate,'YYYY-MM-DD HH24:MI:SS'),to_char(c.outdate,'YYYY-MM-DD HH24:MI:SS'),c.time,c.money,v.siteName,v.ItsLocation,c.status,c.chargeType,c.chargeState,c.equipmentNumber,v.companyid,c.equipin,c.equipout,c.auditStatus,c.chargeState1  from DT_CARMANAGE c  left join DT_EquipmentInformation e on e.equipmentnumber = c.equipmentnumber left join DT_VENUEINFORMATION v  on e.siteid = v.siteId left join DT_ParkingSpace p  on  c.parksId = p.parksId where carmID = ?";
				Object spobj = beandao.getObjectBySqlAndParams(sql,
						new Object[]{carmID});
				System.out.println("查询到记录");

				if (spobj != null) {

					spbjs = (Object[]) spobj;
					equipmentNumber = spbjs[12].toString();
					if (companyId == null || companyId.equals("")) {
						companyId = spbjs[13] != null ? spbjs[13].toString() : "";
					}
					carNum = spbjs[1].toString();
					state = spbjs[9] != null ? spbjs[9].toString() : "";
					chargeType = spbjs[10] != null ? spbjs[10].toString() : "";
					chargeState = spbjs[11] != null ? spbjs[11].toString() : "";
					time = spbjs[5] != null ? spbjs[5].toString() : "";
					if(time!=null && time!=""){
						String[] split = time.split("时");
						if(split.length>0 && split[0]!=null){
							hour = formatNumberToChinese(Integer.parseInt(split[0]));
							if(hour.length()>1){
								hour = hour.replaceAll("0*$", "");
							}
							String[] split1 = split[1].split("分");
							minute = formatNumberToChinese(Integer.parseInt(split1[0]));
							if(minute.length()>1){
								minute = minute.replaceAll("0*$", "");
							}
							String[] split2 = split1[1].split("秒");
							second = formatNumberToChinese(Integer.parseInt(split2[0]));
							if(second.length()>1){
								second = second.replaceAll("0*$", "");
							}
						}
					}
					money = spbjs[6] != null ? spbjs[6].toString() : "";
					if(money!=null && money!=""){
						BigDecimal number = new BigDecimal(money);
						number = number.stripTrailingZeros(); // 去除尾随的零
						money = number.toPlainString(); // 转换为字符串，此时不再包含小数点和尾随的零
						money = formatNumberToChinese(Integer.parseInt(money));
						if(money.length()>1){
							money = money.replaceAll("0*$", "");
						}
					}
					equipin = spbjs[14] != null ? spbjs[14].toString() : "";
					equipout = spbjs[15] != null ? spbjs[15].toString() : "";
					serialNumber = spbjs[0] != null ? spbjs[0].toString() : "";
					indate = spbjs[3] != null ? spbjs[3].toString() : "";
					indate = spbjs[4] != null ? spbjs[4].toString() : "";
					siteName = spbjs[7] != null ? spbjs[7].toString() : "";
					siteAddress = spbjs[8] != null ? spbjs[8].toString() : "";
					auditStatus = spbjs[16] != null ? spbjs[16].toString() : "";
					chargeState1 = spbjs[17] != null ? spbjs[17].toString() : "";



				}



				//查询车牌号是否是会员车，时会员车辆则计算出会员车辆剩余的时间，
				//先查询当前车牌号所在的场地id
				String findSiteidSQL = "select * from (SELECT e.siteid  FROM DT_CARMANAGE c LEFT JOIN DT_EquipmentInformation e ON c.equipmentnumber = e.equipmentnumber WHERE c.CARNUMBER = ? and c.equipmentnumber = e.equipmentnumber ORDER BY INDATE desc) tmp where ROWNUM = 1";
				Object siteidInfo = beandao.getObjectBySqlAndParams(findSiteidSQL,new Object[]{carNum});
				String yzSQL = "SELECT p.startdate, p.enddate  FROM dt_timingcharging p LEFT JOIN dt_venueinformation v ON p.siteid = v.siteid  WHERE p.state = '00' AND v.status = '00' AND v.companyid = ? AND v.SITETYPE = 1 AND p.carNumber = ? AND v.siteid = ? AND ROWNUM = 1";
				Object yz = beandao.getObjectBySqlAndParams(yzSQL,new Object[]{companyId,carNum,siteidInfo});
				if(yz!=null){
					Object[] yzs =(Object[]) yz;
					Object startdate = yzs[0];
					Object enddate = yzs[1];

					//计算时间：月天时分
					LocalDate date1 = ((Timestamp)enddate).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().toLocalDate();
					//LocalDate date1 = LocalDate.parse(enddate.toString()); // 开始时间
					LocalDate date2 = LocalDate.now(); // 结束时间
					long days = ChronoUnit.DAYS.between(date1, date2); // 计算相差的天数
					int day= (int) days;
					if(day<=0){
						//实际会员天数
						int abs = Math.abs(day);
						dayInfo=abs+"天";
						char[] charArray = dayInfo.toCharArray();
						for (int i=0;i<charArray.length;i++){
							if(charArray.length==3){
								dayInfos=charArray[0]+"十"+charArray[1]+charArray[2];
							}else if(charArray.length==4){
								dayInfos=charArray[0]+"百"+charArray[1]+"十"+charArray[2]+charArray[3];
							}else if(charArray.length==5){
								dayInfos=charArray[0]+"千"+charArray[1]+"百"+charArray[2]+"十"+charArray[3]+charArray[4];
							}else{
								dayInfos=dayInfo;
							}
						}
					}
				}
				// 查询是否绑定终端机
				List<BaseBean> poslist = beandao.getListBeanByHqlAndParams(hql,
						new Object[]{companyId, equipmentNumber});

				if (poslist == null || poslist.size() == 0) {
					poslist = beandao.getListBeanByHqlAndParams(hql,
							new Object[]{companyId, equipin});
				}

				if (poslist == null || poslist.size() == 0) {
					poslist = beandao.getListBeanByHqlAndParams(hql,
							new Object[]{companyId, equipout});
				}

				if (poslist != null && poslist.size() > 0) {
					for (int i = 0; i < poslist.size(); i++) {
						PosDevice obj = (PosDevice) poslist.get(i);
						if (obj != null) {
							if (i == poslist.size() - 1) {
								jiguangMark += obj.getPosNum();
							} else {
								jiguangMark += obj.getPosNum() + ",";
							}
						}
					}

				}

				// 保存账号
				String[] arr = jiguangMark.split(",");
				slist = Arrays.asList(arr);
				System.out.println("jiguangMark" + jiguangMark);
				if ("in".equals(status)) {
					// 如果是进入的时候

					if (parkingCode == null || parkingCode.equals("")) {
						// 没有停车位的情况
						//content = "对不起，没有停车位了";
						pronunciationgMap.put("1",carNum+"_1");
						pronunciationgMap.put("2","0A2B_2");//车位已满0A,禁止通行2B
						showTextMap.put(carNum,"01");
						showTextMap.put("车位已满","02");
						showTextMap.put("禁止通行","03");
						showTextMap.put("请联系管理员","04");
						type = "0";// 无停车位，语音播报，跳转页面
					} else {

						if ("2".equals(tip)) {
							//content = carNum + "进门请预付款或者联系工作人员人工审核";
							pronunciationgMap.put("1",carNum+"_1");
							pronunciationgMap.put("2","211C03_2");//临时车21,无权限1C,请等待人工确认03
							showTextMap.put(carNum,"01");
							showTextMap.put("临时车辆","02");
							showTextMap.put("暂无权限","03");
							showTextMap.put("请联系管理员","04");
						} else if ("3".equals(tip)) {
							//content = carNum + "工作人员已提交人工审核请等待审核结果，也可以直接扫码付款";
							pronunciationgMap.put("1",carNum+"_1");
							pronunciationgMap.put("2","2103_2");//临时车21,请等待人工确认03
							showTextMap.put(carNum,"01");
							showTextMap.put("临时车辆","02");
							showTextMap.put("已提交审核","03");
							showTextMap.put("等待通过","04");
						} else if ("4".equals(tip)) {
							//content = carNum + "给您提交的免费时长设置审核被上级驳回，您可以直接扫码付款。";
							pronunciationgMap.put("1",carNum+"_1");
							pronunciationgMap.put("2","21070B_2");//临时车21,此车无权限07,请付费0B
							showTextMap.put(carNum,"01");
							showTextMap.put("临时车辆","02");
							showTextMap.put("审核驳回","03");
							showTextMap.put("扫码付费","04");
						} else {
							if ("0".equals(tip)) {
								//费时长审核已通过可以进入

								if(auditStatus!=null&&!auditStatus.equals("")){
									//content = carNum + "人工审核通过，为您分配的停车位编号为" + parkingCode + "正在打印小票请稍后";
									pronunciationgMap.put("1",carNum+"_1");
									pronunciationgMap.put("2","2128_2");//临时车21,请通行28
									showTextMap.put(carNum,"01");
									showTextMap.put("临时车辆","02");
									showTextMap.put("请入场停车","03");
									showTextMap.put("人工审核通过，为您分配的停车位编号为" + parkingCode,"04");
								}else{
									if("06".equals(chargeState1)){
										//content = carNum + "免费临时车，为您分配的停车位编号为" + parkingCode + "正在打印小票请稍后";
										pronunciationgMap.put("1",carNum+"_1");
										pronunciationgMap.put("2","2428_2");//临时车21,请通行28
										showTextMap.put(carNum,"01");
										showTextMap.put("免费临时车","02");
										showTextMap.put("请入场停车","03");
										showTextMap.put("为您分配的停车位编号为" + parkingCode,"04");
									}
								}
							} if ("8".equals(tip)) {
								//关闭审核或者免人工审核的时候进门直接进
								//content = carNum + "为您分配的停车位编号为" + parkingCode + "正在打印小票请稍后";
								pronunciationgMap.put("1",carNum+"_1");
								pronunciationgMap.put("2","2128_2");//临时车21,请通行28
								showTextMap.put(carNum,"01");
								showTextMap.put("临时车辆","02");
								showTextMap.put("请入场停车","03");
								showTextMap.put("为您分配的停车位编号为" + parkingCode,"04");

							} else {
								if("06".equals(chargeState1)){
									//content = carNum + "免费临时车，为您分配的停车位编号为" + parkingCode + "正在打印小票请稍后";
									pronunciationgMap.put("1",carNum+"_1");
									pronunciationgMap.put("2","2428_2");//免费车24,请通行28
									showTextMap.put(carNum,"01");
									showTextMap.put("免费临时车","02");
									showTextMap.put("请入场停车","03");
									showTextMap.put("为您分配的停车位编号为" + parkingCode,"04");
								}else {
									//content = carNum + "套餐会员用户，为您分配的停车位编号为" + parkingCode + "正在打印小票请稍后";
									pronunciationgMap.put("1",carNum+"_1");
									showTextMap.put(carNum,"01");
									if(dayInfo!=null){
										pronunciationgMap.put("2","223D_2");//月租车22,剩余
										pronunciationgMap.put("3",dayInfos+"_1");//天数
										pronunciationgMap.put("4","28_2");//请通行28
										showTextMap.put("会员车辆","02");
										showTextMap.put("剩余"+dayInfo,"03");
									}else{
										pronunciationgMap.put("2","220F28_2");//月租车22，已过期0F
										showTextMap.put("临时车辆","02");
										showTextMap.put("请入场停车","03");
									}

									showTextMap.put("为您分配的停车位编号为" + parkingCode,"04");
								}

							}
							json.accumulate("carmID", carmID); // 跳转页面需要的ID
							json.accumulate("serialNumber", serialNumber);// 停车流水号
							json.accumulate("carNum", carNum);// 车牌号
							json.accumulate("parkingCode", parkingCode);// 停车位编号
							json.accumulate("indate", indate);// 进入时间
							json.accumulate("siteName", siteName);// 场地名称
							json.accumulate("siteAddress", spbjs[8] != null ? spbjs[8].toString() : "");// 场地地址

							//type = "1";// 有停车位，语音播报，打印小票，跳转页面

						}

						type = "0";

					}

				} else if ("out".equals(status)) {
					// 出来
					// 识别车牌后，播报用时，金额，并跳转页面，如果是金币付款，直接提醒金币或积分扣款成功或者包月直接打印小票，否则等待扫码付款

					// 推送播放内容


					if ("2".equals(state)) {
						//content = "车牌" + carNum + "出现错误数据,请工作人员处理";// 跳转页面无需打印
						type = "0";
						pronunciationgMap.put("1",carNum+"_1");
						pronunciationgMap.put("2","7E19_2");//支付2c,失败19
						showTextMap.put(carNum,"01");
						showTextMap.put("临时车辆","02");
						showTextMap.put("出现错误","03");
						showTextMap.put("请工作人员处理","04");
					} else if ("1".equals(state)) {
						//content = "车牌" + carNum + "没有出去记录,请重新识别车牌"; // 跳转页面无需打印
						type = "0";
						pronunciationgMap.put("1",carNum+"_1");
						pronunciationgMap.put("2","19_2");//支付2c,失败19
						showTextMap.put(carNum,"01");
						showTextMap.put("临时车辆","02");
						showTextMap.put("没有出去的记录","03");
						showTextMap.put("请重新识别车牌","04");

					} else if ("0".equals(state)) {
						if ("01".equals(chargeType)) {// 扣款成功
							if ("01".equals(chargeState)) { // 包月
								//content = carNum + "是套餐会员免支付";// 跳转页面无需打印
								type = "0";
								pronunciationgMap.put("1",carNum+"_1");
								pronunciationgMap.put("2","223D_2");//月租车22,剩余
								pronunciationgMap.put("3",dayInfos+"_1");//天数
								pronunciationgMap.put("4","28_2");//请通行28
								showTextMap.put(carNum,"01");
								showTextMap.put("会员车辆","02");
								showTextMap.put("剩余"+dayInfo,"03");
								showTextMap.put("一路平安","04");
							}
							if ("05".equals(chargeState)) { // 消费0元
								//content = carNum + "消费0元无需收费";//  跳转页面无需打印
								type = "0";
								pronunciationgMap.put("1",carNum+"_1");
								showTextMap.put(carNum,"01");
								if(dayInfo!=null){
									pronunciationgMap.put("2","223D_2");//月租车22,剩余3D
									pronunciationgMap.put("3",dayInfos+"_1");//天数
									pronunciationgMap.put("4","28_2");//请通行28
									showTextMap.put("会员车辆","02");
									showTextMap.put("剩余"+dayInfo,"03");
								}else{
									pronunciationgMap.put("2","2102_2");//临时车21，一路平安02
									showTextMap.put("临时车辆","02");
									showTextMap.put("减速慢行","03");
								}
								showTextMap.put("一路平安","04");
							}
							if ("06".equals(chargeState)) {
								//content = carNum + "免费临时车";//  跳转页面无需打印
								type = "0";
								pronunciationgMap.put("1",carNum+"_1");
								showTextMap.put(carNum,"01");
								if(dayInfo!=null){
									pronunciationgMap.put("2","223D_2");//月租车22,剩余3D
									pronunciationgMap.put("3",dayInfos+"_1");//天数
									pronunciationgMap.put("4","28_2");//请通行28
									showTextMap.put("会员车辆","02");
									showTextMap.put("剩余"+dayInfo,"03");
								}else{
									pronunciationgMap.put("2","2102_2");//临时车22，一路平安02
									showTextMap.put("临时车辆","02");
									showTextMap.put("减速慢行","03");
								}
								showTextMap.put("一路平安","04");
							} else if ("02".equals(chargeState)) {// 积分金币 跳转页面并打印
								//content = "车牌" + carNum + ",用时" + time + "共计" + money + "元" + ",积分或金币扣款成功";
								pronunciationgMap.put("1",carNum+"_1");
								pronunciationgMap.put("2","2E_2");//停车2E,
								pronunciationgMap.put("3",hour+"_1");//,
								pronunciationgMap.put("4","2F_2");//小时2F,
								pronunciationgMap.put("5",minute+"分"+second+"秒_1");
								pronunciationgMap.put("6","6A_2");//缴费,
								pronunciationgMap.put("7",money+"元_1");//多少元,
								pronunciationgMap.put("8","18_2");//成功,
								showTextMap.put(carNum,"01");
								showTextMap.put("停车："+time,"02");
								showTextMap.put("成功缴费："+money+"元","03");
								showTextMap.put("一路平安","04");


								json.accumulate("carmID", carmID); // 跳转页面需要的ID
								json.accumulate("serialNumber", serialNumber);// 停车流水号
								json.accumulate("carNum", spbjs[1].toString());// 车牌号
								json.accumulate("parkingCode", parkingCode);// 停车位编号
								json.accumulate("indate", spbjs[3].toString());// 进入时间
								if (spbjs[4] != null && spbjs[4] != "") {
									json.accumulate("outdate", spbjs[4].toString());// 出去时间
									json.accumulate("time", spbjs[5].toString());// 用时
									json.accumulate("money", spbjs[6].toString());// 金额

								}

								json.accumulate("chargeState", "金币积分支付");// 支付方式
								json.accumulate("siteName", spbjs[7] != null ? spbjs[7].toString() : "");// 场地名称
								json.accumulate("siteAddress", spbjs[8] != null ? spbjs[8].toString() : "");// 场地地址
								//type = "1";
								type = "0";
							}
						} else if ("02".equals(chargeType)) { // 扫码
							//content = carNum + "扫码支付成功";//
							type = "0";
							pronunciationgMap.put("1",carNum+"_1");
							pronunciationgMap.put("2","2E_2");//停车2E,
							pronunciationgMap.put("3",hour+"_1");//,
							pronunciationgMap.put("4","2F_2");//小时2F,
							pronunciationgMap.put("5",minute+"分"+second+"秒_1");
							pronunciationgMap.put("6","6A_2");//缴费,
							pronunciationgMap.put("7",money+"元_1");//多少元,
							pronunciationgMap.put("8","18_2");//成功,
							showTextMap.put(carNum,"01");
							showTextMap.put("停车："+time,"02");
							showTextMap.put("成功缴费："+money+"元","03");
							showTextMap.put("一路平安","04");
						}else if ("03".equals(chargeType)) { // 免费
							if(auditStatus!=null&&auditStatus.equals("02")){
								//content = carNum + "人工审核通过";
							}else{
								//content = carNum + "免费临时车";
							}
							type = "0";
							pronunciationgMap.put("1",carNum+"_1");
							pronunciationgMap.put("2","28_2");//请通行,
							showTextMap.put(carNum,"01");
							showTextMap.put("临时车辆","02");
							showTextMap.put("减速慢行","03");
							showTextMap.put("一路平安","04");

						}else if ("00".equals(chargeType)) { // 未支付
							if ("00".equals(chargeState)) { // 未支付提示扫码支付
								String t = "";
								if ("00".equals(auditStatus)) {
									t = "请扫描二维码支付或联系工作人员审核";
								} else if ("01".equals(auditStatus)) {
									t = "请扫描二维码支付或等待人工审核";
								} else if ("03".equals(auditStatus)) {
									t = "人工审核驳回，请扫码二维码支付";
								} else {
									t = "请扫描二维码支付";
								}
								//content = "车牌" + carNum + ",用时" + time + "共计" + money+ "元" + "," + t;
								pronunciationgMap.put("1",carNum+"_1");
								pronunciationgMap.put("2","2E_2");//停车2E,
								pronunciationgMap.put("3",hour+"_1");//,
								pronunciationgMap.put("4","2F_2");//小时2F,
								pronunciationgMap.put("5",minute+"分"+second+"秒_1");
								pronunciationgMap.put("6","0B_2");//请缴费0B,
								pronunciationgMap.put("7",money+"元_1");//多少元
								showTextMap.put(carNum,"01");
								showTextMap.put("停车:"+time,"04");
								showTextMap.put("请缴费"+money+"元","03");
								showTextMap.put("扫码支付","02");

								json.accumulate("carmID", carmID); // 跳转页面需要的ID
								type = "0";
							}

						}

					}
				} else if ("pay".equals(status)) {//付款后推送
					//单独调用mqtt让道闸开门
					pronunciationgMap.put("1",carNum+"_1");
					pronunciationgMap.put("2","2E_2");//停车2E,
					pronunciationgMap.put("3",hour+"_1");//,
					pronunciationgMap.put("4","2F_2");//小时2F,
					pronunciationgMap.put("5",minute+"分"+second+"秒_1");
					pronunciationgMap.put("6","6A_2");//缴费,
					pronunciationgMap.put("7",money+"元_1");//多少元,
					pronunciationgMap.put("8","18_2");//成功,
					showTextMap.put(carNum,"01");
					showTextMap.put("停车："+time,"02");
					showTextMap.put("成功缴费："+money+"元","03");
					showTextMap.put("一路平安","04");

					CarMqttService.pronunciation("1234567890",equipmentNumber,pronunciationgMap);//语音
					CarMqttService.xianshishuju("1234567890",equipmentNumber,showTextMap,"ok");//显示屏
					CarMqttService.isOpen("1234567890",equipmentNumber);//开门




					/*content = "车牌" + carNum + "成功支付金额" + money;
					json.accumulate("carmID", carmID); // 跳转页面需要的ID
					json.accumulate("serialNumber", serialNumber);// 停车流水号
					json.accumulate("carNum", spbjs[1].toString());// 车牌号
					json.accumulate("parkingCode", parkingCode);// 停车位编号
					json.accumulate("indate", spbjs[3].toString());// 进入时间
					if (spbjs[4] != null && spbjs[4] != "") {
						json.accumulate("outdate", spbjs[4].toString());// 出去时间
						json.accumulate("time", spbjs[5].toString());// 用时
						json.accumulate("money", spbjs[6].toString());// 金额
					}
					if (("03").equals(chargeState)) {
						json.accumulate("chargeState", "扫码支付");// 支付方式
					} else if (("04").equals(chargeState)) {
						json.accumulate("chargeState", "现金支付");// 支付方式
					}
					json.accumulate("siteName", spbjs[7].toString());// 场地名称
					json.accumulate("siteAddress", spbjs[8].toString());// 场地地址
					//type = "1";
					type = "0";*/
				}
				System.out.println("content" + content);
				System.out.println(type);
				System.out.println(json.toString());


			} else {
				List<BaseBean> poslist = beandao.getListBeanByHqlAndParams(hql,
						new Object[]{companyId, equipmentNumber});
				type = "0";
				if (poslist != null && poslist.size() > 0) {
					for (int i = 0; i < poslist.size(); i++) {
						PosDevice obj = (PosDevice) poslist.get(i);
						if (obj != null) {
							if (i == poslist.size() - 1) {
								jiguangMark += obj.getPosNum();
							} else {
								jiguangMark += obj.getPosNum() + ",";
							}
						}
					}

				}
				// 保存账号
				String[] arr = jiguangMark.split(",");
				slist = Arrays.asList(arr);
				System.out.println("无记录-----------------------");
			}


			if(content!=null&&!content.equals("")) {
				// 极光推送
				//JushMain.sendjiguangMessage(content, type, json.toString(), "parking",slist);
				//进行mqtt显示屏和语音的对送，但无法进行自定义语音推送

			}
		}catch (Exception e){
			e.printStackTrace();
		}
		resultMap.put("pronunciationgMap",pronunciationgMap);
		resultMap.put("showTextMap",showTextMap);
		return resultMap;
	}
	/**
	 *
	 *
	 * @param
	 * @param status in 进入 ：out 出
	 */
	public String pushSz( String status, String parkingCode,
						 String carmID) {
		String content = "";
		try{
		System.out.println("进入推送");
		Object[] spbjs = null;

		String state = null;
		String chargeType = null;
		String chargeState = null;
		String time = null;
		String money = null;
		String carNum = null;



		if (carmID != null && !carmID.equals("")) {
			String sql = "select c.serialNumber,c.carNumber,p.parkingCode,to_char(c.indate,'YYYY-MM-DD HH24:MI:SS'),to_char(c.outdate,'YYYY-MM-DD HH24:MI:SS'),c.time,c.money,v.siteName,v.ItsLocation,c.status,c.chargeType,c.chargeState,c.equipmentNumber,v.companyid,c.equipin,c.equipout from DT_CARMANAGE c  left join DT_EquipmentInformation e on e.equipmentnumber = c.equipmentnumber left join DT_VENUEINFORMATION v  on e.siteid = v.siteId left join DT_ParkingSpace p  on  c.parksId = p.parksId where carmID = ?";
			Object spobj = beandao.getObjectBySqlAndParams(sql,
					new Object[]{carmID});
			System.out.println("查询到记录");

			if (spobj != null) {

				spbjs = (Object[]) spobj;

				carNum = spbjs[1].toString();
				state = spbjs[9] != null ? spbjs[9].toString() : "";
				chargeType = spbjs[10] != null ? spbjs[10].toString() : "";
				chargeState = spbjs[11] != null ? spbjs[11].toString() : "";
				time = spbjs[5] != null ? spbjs[5].toString() : "";
				money = spbjs[6] != null ? spbjs[6].toString() : "";


			}


			String type = "";
			JSONObject json = new JSONObject();
			if ("in".equals(status)) {
				// 如果是进入的时候

				if (parkingCode == null || parkingCode.equals("")) {
					// 没有停车位的情况
					content = "对不起，没有停车位了";
					type = "0";// 无停车位，语音播报，跳转页面
				} else {

					content = "为您分配的停车位编号为" + parkingCode + "正在打印小票请稍后";

					//type = "1";// 有停车位，语音播报，打印小票，跳转页面
					type = "0";
				}

			} else if ("out".equals(status)) {
				// 出来
				// 识别车牌后，播报用时，金额，并跳转页面，如果是金币付款，直接提醒金币或积分扣款成功或者包月直接打印小票，否则等待扫码付款

				// 推送播放内容


				if ("2".equals(state)) {

					content = "车牌" + carNum + "出现错误数据,请工作人员处理";// 跳转页面无需打印
					type = "0";
				} else if ("1".equals(state)) {
					content = "车牌" + carNum + "没有出去记录,请重新识别车牌"; // 跳转页面无需打印
					type = "0";

				} else if ("0".equals(state)) {
					if ("01".equals(chargeType)) {// 扣款成功
						if ("01".equals(chargeState)) { // 包月


							content = carNum + "是套餐会员免支付";// 跳转页面无需打印

							type = "0";
						}
						if ("05".equals(chargeState)) { // 消费0元
							content = carNum + "消费0元无需收费";//  跳转页面无需打印
							type = "0";
						} else if ("02".equals(chargeState)) {// 积分金币 跳转页面并打印
							content = "车牌" + carNum + ",用时" + time + "共计" + money
									+ "元" + ",积分或金币扣款成功";


							type = "0";
						}
					} else if ("00".equals(chargeType)) { // 未支付
						if ("00".equals(chargeState)) { // 未支付提示扫码支付
							content = "车牌" + carNum + ",用时" + time + "共计" + money
									+ "元" + ",请扫描二维码支付";
							json.accumulate("carmID", carmID); // 跳转页面需要的ID
							type = "0";
						}

					}

				}
			} else if ("pay".equals(status)) {//付款后推送
				content = "车牌" + carNum + "成功支付金额" + money;


				type = "0";
			}
			System.out.println("content" + content);

		   }
		}catch(Exception es){

             es.printStackTrace();


		}
		return content;
	}
	 /**
	   * 查询付款状态
	   * @param carmID
	   * @return
	   */
	   public  String searchFeeState(String carmID){
	    	
	    	String hql = "from CarManage where carmID = ?";
	    	CarManage cm = (CarManage)beandao.getBeanByHqlAndParams(hql, new Object[]{carmID});
	    	
	    	return cm.getChargeState();
	    }

	
	
	/**
	 * 添加计时收费 *
	 * 
	 * @param carid:车辆id,ppid:产品id,journalNum:订单凭证号
	 * @return
	 */
	@Override
	@Transactional
	public void addTiming(String carid, String ppid, String journalNum,String carNumber,String staffID,Date indate) {
		
		 ProductPackaging ppk = (ProductPackaging) beandao.getBeanByHqlAndParams("from ProductPackaging where ppID=?", new Object[]{ppid});
         String type = ppk.getType();
         if (type.indexOf("包天")!=-1 ||type.indexOf("包月")!=-1 || type.indexOf("包年")!=-1)
         {
			 FeeScale fs = (FeeScale) beandao.getBeanByHqlAndParams("from FeeScale where goodsID=?", new Object[]{ppk.getGoodsID()});
			 TimingCharging tc = new TimingCharging();
			 tc.setTcId(serverService.getServerID("tc"));
			 tc.setSiteId(fs.getSiteId());
			 tc.setJournalnum(journalNum);
			 tc.setState("01");
			 tc.setStartDate(indate);
         	if(!"".equals(carid)&&carid!=null) {
				CarInformation cn = (CarInformation) beandao.getBeanByHqlAndParams("from CarInformation where carID=?", new Object[]{carid});
				tc.setCarNumber(cn.getCarNum());
				tc.setStaffId(cn.getStaffID());

			}else{
				tc.setCarNumber(carNumber);
				tc.setStaffId(staffID);
			}
         	beandao.save(tc);
         }
	}
	
	
	/**
	 * 修改计时收费 *
	 * 
	 * @param journalNum:订单凭证号,type:类型
	 * @return
	 */
	@Override
	@Transactional
	public void updateTiming(String journalNum,String type) {
		 
		List<BaseBean> list1 = beandao.getListBeanByHqlAndParams("select tcId,carNumber,siteId,staffId,journalnum,startDate from TimingCharging where journalnum=? and state=?", new Object[]{journalNum,"01"});
		Object obj = list1.get(0);
		Object[] obj1 = (Object[]) obj;
		TimingCharging tc = (TimingCharging) beandao.getBeanByHqlAndParams("from TimingCharging where carNumber=? and siteId=? and  state=?", new Object[]{obj1[1],obj1[2],"00"});
		int i = list1.size();
        String timeType = "";
		if(type.indexOf("包天")!=-1)
		{
			String sql = "select f.timetype from dt_feescale f,Dtgoodsbills g,Dtcashierbills c where c.cashierbillsid= g.cashierbillsid and g.goodsid=f.goodsid and c.journalnum = ?";

			timeType = beandao.getObjectBySqlAndParams(sql,new Object[]{journalNum}).toString();

		}


        Date newDate = new Date();

      	if(obj1[5]!=null&&!obj1[5].equals("")) {
			newDate = Utilities.getDateFromString(obj1[5].toString(), "yyyy-MM-dd HH:mm:ss");
		}

		if(tc!=null)
		{
			Calendar cl = Calendar.getInstance();
			Date date = null;
			if (newDate.after(tc.getEndDate()))  //说明之前的已经过期了
			{
				tc.setStartDate(newDate);    //把新购买时间作为新的开始时间

				Date current = new Date();
				cl.setTime(current);


				if(type.indexOf("包月")!=-1)
				{
			        cl.add(Calendar.MONTH, +i);
					date = cl.getTime();
				}
				else if(type.indexOf("包年")!=-1)
				{
			        cl.add(Calendar.YEAR, +i);
					date = cl.getTime();
				}
				else if(type.indexOf("包天")!=-1)
				{
					if("0".equals(timeType)) {
						logger.error("包天");
						System.out.println("包天");
						date = getCurrentDay(current);
					}else{
						date = getDayEndDate(current,timeType);

					}

				}
			}
			else
			{  //还没到期包天无需购买
				cl.setTime(tc.getEndDate());
				if(type.indexOf("包月")!=-1)
				{
					cl.add(Calendar.MONTH, +i);
					date = cl.getTime();
				}
				else if(type.indexOf("包年")!=-1)
				{
					cl.add(Calendar.YEAR, +i);
					date = cl.getTime();
				}else if(type.indexOf("包天")!=-1)
				{

					Date current = new Date();
					if("0".equals(timeType)) {

						date = getCurrentDay(current);
					}else{
						date = getDayEndDate(current,timeType);

					}

				}
			}
			tc.setEndDate(date);
		}
		else
		{//没有买过会员
			tc = (TimingCharging) beandao.getBeanByHqlAndParams("from TimingCharging where tcId=?", new Object[]{obj1[0]});
			tc.setStartDate(newDate);


			Calendar cl = Calendar.getInstance();
			Date current = new Date();
			cl.setTime(current);
			Date date1 = null;
			if(type.indexOf("包月")!=-1)
			{
		        cl.add(Calendar.MONTH, +i);
				date1 = cl.getTime();
			}
			else if(type.indexOf("包年")!=-1)
			{
		        cl.add(Calendar.YEAR, +i);
				date1 = cl.getTime();
			}else if(type.indexOf("包天")!=-1){
				if("0".equals(timeType)) {

					date1 = getCurrentDay(current);
				}else{
					date1 = getDayEndDate(current,timeType);

				}
			}
			tc.setEndDate(date1);
			tc.setState("00");
		}
		tc.setJournalnum(obj1[4].toString());
      	tc.setStaffId(obj1[3].toString());
		beandao.update(tc);
		logger.error("成功");
		logger.error("getEndDate()"+tc.getEndDate());
		System.out.println("getEndDate()"+tc.getEndDate());
	}

	/**
	 *
	 * 获取当天最后时间
	 * @return
	 */
	private Date getCurrentDay(Date newDate){
		Date date1 =  null;

		try {
		 String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(newDate).toString().split("-");
		 Integer year = Integer.parseInt(strNow[0]);
		 Integer month = Integer.parseInt(strNow[1]);
		 Integer day = Integer.parseInt(strNow[2]);
		 String endStr = year+"-"+month+"-"+day+" "+"23:59:59";
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		 date1 =  sdf.parse(endStr);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  date1;
	}


	/**
	 *
	 * 30分钟前
	 * @param newDate
	 * @return
	 */
	private Date getCurrentStart(Date newDate){
		Date date1 = null;
		try {

			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MINUTE, (calendar.get(Calendar.MINUTE) - 30));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date11 = df.format(calendar.getTime());
			date1 = df.parse(date11);
		} catch (ParseException e) {

		}
		return date1;
	}

   private Date getDayEndDate(Date dt,String hour){

	   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH");
	   Calendar rightNow = Calendar.getInstance();
	   rightNow.setTime(dt);
	   rightNow.add(Calendar.HOUR, +Integer.parseInt(hour));
	   Date dt1=rightNow.getTime();
	   return dt1;
   }



    /**
     * 查询场地列表 *
     *
     * @param companyID:公司id
     * @param numberOrName:场地名称或编号
     * @param pageForm:分页信息
     * @return
     */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm examinationRoomList(String companyID, String numberOrName, PageForm pageForm, int pageNumber) {

	    StringBuilder sb = new StringBuilder();
        List<Object> list = new ArrayList<Object>();


	    sb.append("select e.erid, e.ernumber, e.ername, y.companyname,f.staffname,t.account,e.whetherstatus,e.ItsLocation,e.siteType ");
        sb.append("from dt_examinationroom e, dt_ccom_com c, dtcontactcompany y,dt_hr_staff f,T_ESHOP_CUSCOM t ");
        sb.append("where e.companyid = ? and e.status = ? ");
        list.add(companyID);
        list.add("00");
        if (numberOrName!=null && !numberOrName.equals(""))
        {
            sb.append("and (e.ernumber like ? or e.ername like ?) ");
            list.add("%"+numberOrName+"%");
            list.add("%"+numberOrName+"%");
        }
        sb.append("and e.companyid = c.compnay_id and c.ccompany_id = y.ccompanyid ");
        sb.append("and  t.sccid=f.sccid and e.reviewerstaffid = f.staffid ");
        sb.append("order by e.erdate desc ");


        pageForm = getPageFormBySQL(
                null != pageForm ? pageForm.getPageNumber() : 1,
                pageNumber == 0 ? 10 : pageNumber, sb.toString(),
                "select count(*) from (" + sb.toString() + ")",list.toArray());

		return pageForm;
	}

    /**
     * 查询场地详情 *
     *
     * @param companyID:公司id
     * @param erId:考场id
     * @param siteJudge:判断
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, Object> erDetails(String erId, boolean siteJudge, String companyID) {
        StringBuilder sb = new StringBuilder();
        Object obj = null;
        if (siteJudge)
        {
            sb.append("select e.erkey,e.erid,e.ernumber,e.ername,e.companyid,y.companyname,e.ItsLocation,");
            sb.append("e.siteType,e.reviewerstaffid,e.staffid ");
            sb.append("from dt_examinationroom e left join dt_ccom_com c on e.companyid = c.compnay_id ");
            sb.append("left join dtcontactcompany y on c.ccompany_id = y.ccompanyid ");
            //sb.append("left join dt_hr_staff f on e.reviewerstaffid = f.staffid left join dt_hr_staff z on e.staffid = z.staffid ");
            sb.append("where e.erid = ? ");
            obj = beandao.getObjectBySqlAndParams(sb.toString(), new Object[]{erId});
        }
        else
        {
            sb.append("select c.compnay_id,y.companyname ");
            sb.append("from dt_ccom_com c,dtcontactcompany y ");
            sb.append("where c.compnay_id=? and c.ccompany_id=y.ccompanyid ");
            obj = beandao.getObjectBySqlAndParams(sb.toString(), new Object[]{companyID});
        }

        List<BaseBean> list = queryStaff(companyID,"");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("obj", obj);
        map.put("list", list);
        return map;
    }

    /**
     * 添加修改考场信息 *
     *
     * @param exRoom
     *         考场参数
     * @return
     */
    @Override
    @Transactional
    public boolean addOrUpdateErDetails(ExaminationRoom exRoom) {
        boolean b = true;
        try {
            if (exRoom != null) {
                exRoom.setErDate(new Date());
                exRoom.setStatus("00");
                exRoom.setWhetherStatus("00");
                if (exRoom.getErkey() == null || exRoom.getErkey().equals(""))
                {
                    if (exRoom.getErkey().equals(""))
                    {
                        exRoom.setErkey(null);
                    }
                    exRoom.setErId(serverService.getServerID("er"));
                }
                beandao.saveOrUpdate(exRoom);
            }
        } catch (Exception e) {
            b = false;
        }
        return b;
    }

    /**
     * 删除考场 *
     *
     * @param erId
     *         考场id
     * @return
     */
    @Override
    @Transactional
    public boolean delExoom(String erId) {
        boolean b = true;
        try {
            String sql = "update dt_ExaminationRoom e set e.status = ? where e.erId = ? ";
            beandao.saveBeansListAndexecuteSqlsByParams(null,
                    new String[] { sql }, new Object[] { "01",erId });
        } catch (Exception e) {
            b = false;
        }
        return b;
    }

	/**
	 * 实时效验考场编号 *
	 * @param numberOrName:考场编号
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public boolean verificationerRoom(String numberOrName) {
        boolean b;
        ExaminationRoom er = (ExaminationRoom) beandao.getBeanByHqlAndParams("from ExaminationRoom where erNumber=?", new Object[]{numberOrName});

        if (er!=null)
        {
            b = false;
        }
        else
        {
            b = true;
        }
        return b;
	}


    /**
     * 启用考场 *
     *@param erId:考场id
     * @return
     */
    @Override
    @Transactional
    public boolean enableTheTest(String erId) {
        boolean b = true;
        try {
            ExaminationRoom er = (ExaminationRoom) beandao.getBeanByHqlAndParams("from ExaminationRoom where erId=?", new Object[]{erId});
            if(er.getWhetherStatus().equals("00"))
            {
                er.setWhetherStatus("01");
            }
            else if(er.getWhetherStatus().equals("01"))
            {
                er.setWhetherStatus("00");
            }
            beandao.update(er);
        } catch (Exception e) {
            b = false;
        }
        return b;
    }

    /**
     * 查询公司考场编号 *
     *
     * @param companyID
     *            公司id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BaseBean> queryerNumber(String companyID) {

        StringBuilder sb = new StringBuilder();

        sb.append("select r.erNumber,r.erId,r.erName from ");
        sb.append("dt_ExaminationRoom r where r.companyId = ? ");
        sb.append("and r.status = ? ");
        List<Object> arr = new ArrayList<Object>();
        arr.add(companyID);
        arr.add("00");

        List<BaseBean> list = beandao.getListBeanBySqlAndParams(sb.toString(),arr.toArray());

        return list;
    }

    /**
     * 查询考场收费标准详情 *
     * @param ercID:收费id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ExaminationRoomCharge testStandard(String ercID) {

        String hql = "from ExaminationRoomCharge where ercID=?";

        ExaminationRoomCharge erCharge = (ExaminationRoomCharge) beandao.getBeanByHqlAndParams(hql, new Object[]{ercID});

        return erCharge;
    }

    /**
     * 查询考场详情 *
     * @param companyID:公司ID
     * @param erNumber:考场编号
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<BaseBean> queryerNumber(String companyID, String erNumber) {
        StringBuilder sb = new StringBuilder();

        sb.append("select v.erNumber,v.erId,v.erName from ");
        sb.append("dt_ExaminationRoom v where v.companyId = ? ");
        sb.append("and v.status = ? ");
        List<Object> arr = new ArrayList<Object>();
        arr.add(companyID);
        arr.add("00");
        if (erNumber!=null && !erNumber.equals(""))
        {
            sb.append("and v.erNumber = ? ");
            arr.add(erNumber);
        }

        List<BaseBean> list = beandao.getListBeanBySqlAndParams(sb.toString(),arr.toArray());

        return list;
    }


    /**
     * 添加修改考场收费标准 *
     *
     * @param erCharge
     *            考场停车标准参数
     * @param ppk :产品
     * @return
     */
    @Override
    @Transactional
    public boolean addOrUpdateExRoomCharge(ExaminationRoomCharge erCharge, ProductPackaging ppk,File photo,String photoFileName,Map<String, ProSetupSub> pssMap,String brokerage) {

        try {
            if (erCharge != null)
            {
                List<BaseBean> baselist = new ArrayList<BaseBean>();
                List<String> hqls = new ArrayList<String>();
                List<Object> parms = new ArrayList<Object>();
                //修改停车标准
                if (erCharge.getErcID() != null
                        && !erCharge.getErcID().equals(""))
                {
                    ExaminationRoomCharge fs = (ExaminationRoomCharge) beandao.getBeanByHqlAndParams(
                            "from ExaminationRoomCharge where ercID=?",
                            new Object[] { erCharge.getErcID() });
                    fs.setStaffID(erCharge.getStaffID());
                    fs.setStaffName(erCharge.getStaffName());
                    fs.setCompanyID(erCharge.getCompanyID());
                    baselist.add(fs);
                    //修改资费
                    ProductPackaging productPackaging = (ProductPackaging) beandao.getBeanByHqlAndParams("from ProductPackaging where goodsID=?", new Object[]{fs.getGoodsID()});
					ProSetup proSetup = (ProSetup) beandao.getBeanByHqlAndParams("from ProSetup where ppID=?", new Object[]{productPackaging.getPpID()});
					proSetup.setEfPrice(ppk.getPrice());
					proSetup.setRePrice(ppk.getPrice());
					proSetup.setBrokerage(brokerage==""?"0":brokerage);
					BigDecimal dlsum=new BigDecimal(0);
					BigDecimal rebig=new BigDecimal(ppk.getPrice());
					BigDecimal brbig=new BigDecimal(proSetup.getBrokerage());
					if(pssMap!=null)
					{
						BigDecimal dl=null;
						for (ProSetupSub pss:pssMap.values())
						{
							ProSetupSub pross = null;
							if(pss.getSuskey() != null){
								pross = (ProSetupSub) beandao.getBeanByHqlAndParams(
										"from ProSetupSub where suskey=?",
										new Object[]{pss.getSuskey()});
							}
							if(pross == null){
								pross = new ProSetupSub();
								pross.setSusid(serverService.getServerID("prosetupsub"));
							}
							if("".equals(pss.getAmount())){
								dl=new BigDecimal(0);
							}else {
								dl=new BigDecimal(pss.getAmount());
							}
							dlsum=dlsum.add(dl);
							pross.setAmount(dl.toString());
							pross.setTypePpid(pss.getTypePpid());
							pross.setPpid(productPackaging.getPpID());
							pross.setSjdate(new Date());
							pross.setSuid(proSetup.getSuid());
							baselist.add(pross);


							proSetup.setProxyprice(dlsum.toString());
							BigDecimal efbig = rebig.subtract(dlsum).subtract(brbig);//总金额-代理和-业务佣金 = 成本
							proSetup.setEfPrice(efbig.toString());
							productPackaging.setPrice(efbig.toString());
						}


					}
					baselist.add(proSetup);
					GoodsManage gm = (GoodsManage) beandao
							.getBeanByHqlAndParams("from GoodsManage where goodsID=?",
									new Object[]{erCharge.getGoodsID()});
					AttriProduction ap = null;
					List<BaseBean> aplist = new ArrayList<BaseBean>();
					aplist = beandao
							.getListBeanByHqlAndParams(
									"from AttriProduction where goodsid=? and type=? order by sort",
									new Object[]{erCharge.getGoodsID(), "2"});
					if (photo != null )
					{
						String path = ServletActionContext.getRequest().getSession()
								.getServletContext().getRealPath("/");
						String photopath = "";
							photopath = fileService.savePhoto(
									path,
									photoFileName,
									photo,
									erCharge.getCompanyID(),
									"/gooddesign/"
											+ Utilities.getDateString(new Date(),
											"yyyy-MM-dd"));
							String jjPath = photopath.split("\\.")[0] + "small." + photopath.split("\\.")[1];
							ImageCut.scale(path + photopath, path + jjPath, 414, 431);
							ap = new AttriProduction();
							ap.setApid(serverService.getServerID("apid"));
							ap.setImgurl(jjPath);
							ap.setGoodsid(erCharge.getGoodsID());
							ap.setType("2");

							if (aplist != null && aplist.size() > 0)
							{
								AttriProduction a = (AttriProduction) aplist.get(aplist.size() - 1);
								ap.setSort(a.getSort()+ 1);
							}
							else
							{
								ap.setSort(1);
								gm.setPhotoPath(jjPath);
								productPackaging.setImage(jjPath);
							}
						baselist.add(ap);

					}
					baselist.add(productPackaging);
					baselist.add(gm);
                }
                else
                {
                    //添加停车标准
                    GoodsManage goodsManage = new GoodsManage();
                    goodsManage.setGoodsID(serverService.getServerID("goodsID"));
                    goodsManage.setGoodsName(timing);
                    goodsManage.setTypeID(timing);
                    goodsManage.setCompanyID(erCharge.getCompanyID());
                    // 编号处理
                    String hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.typeID=?";
                    // 获取拼音码
                    String pinyin = ToChineseFirstLetter.getFirstLetter(goodsManage
                            .getTypeID());
                    String Upstr = pinyin.toUpperCase();// 转换为大写
                    int goodscodingnum = beandao.getConutByByHqlAndParams(hql,
                            new Object[] { goodsManage.getTypeID() });
                    DecimalFormat form = new DecimalFormat("000000");
                    String ss = form.format(goodscodingnum + 1);
                    goodsManage.setGoodsCoding(Upstr + "_" + ss);
                    goodsManage.setFiveClear("2");
                    goodsManage.setCreatedate(new Date());
					// 保存物品主图
					String jjPath ="";
					if (photo != null)
					{
						String path = ServletActionContext.getRequest()
								.getSession().getServletContext().getRealPath("/");
						String photopath = "";
						AttriProduction attrp = null;
							photopath = fileService.savePhoto(
									path,
									photoFileName,
									photo,
									erCharge.getCompanyID(),
									"/gooddesign/"
											+ Utilities.getDateString(new Date(),
											"yyyy-MM-dd"));
							jjPath = photopath.split("\\.")[0] + "small." + photopath.split("\\.")[1];
							ImageCut.scale(path + photopath, path + jjPath, 414, 431);
							goodsManage.setPhotoPath(jjPath);
							attrp = new AttriProduction();
							attrp.setApid(serverService.getServerID("apid"));
							attrp.setType("2");
							attrp.setImgurl(jjPath);
							attrp.setGoodsid(goodsManage.getGoodsID());
							attrp.setSort(1);
							baselist.add(attrp);
						baselist.add(goodsManage);
					}
                    // 产品
                    ProductPackaging productPackaging = new ProductPackaging();
                    productPackaging.setDelStatus("00");
                    productPackaging.setProductstate("01");
                    productPackaging.setFiveClear("2");
                    productPackaging.setCompanyID(erCharge.getCompanyID());
                    productPackaging.setStaffID(erCharge.getStaffID());
                    productPackaging.setStaffName(erCharge.getStaffName());
                    productPackaging.setPackagingDate(new Date());
                    productPackaging.setAssemble("00");
                    productPackaging.setGoodsID(goodsManage.getGoodsID());
                    productPackaging.setPpID(serverService.getServerID("p"));
                    productPackaging.setImage(jjPath);
                    productPackaging.setType(timing);
					productPackaging.setGoodsName(timing);

                    //设置佣金
					ProSetup proSetup=new ProSetup();
					proSetup.setComId(erCharge.getCompanyID());
					proSetup.setPpid(productPackaging.getPpID());
					proSetup.setRePrice(ppk.getPrice());
					proSetup.setBrokerage(brokerage==""?"0":brokerage);
					proSetup.setSuid(serverService.getServerID("setup"));
					proSetup.setPpname(timing);
					proSetup.setSjdate(new Date());
					BigDecimal dlsum=new BigDecimal(0);
					BigDecimal rebig=new BigDecimal(ppk.getPrice());
					BigDecimal brbig=new BigDecimal(proSetup.getBrokerage());
					if(pssMap!=null)
					{
						BigDecimal dl=null;
						for (ProSetupSub pss:pssMap.values())
						{
							ProSetupSub pross = null;
							if(pross == null){
								pross = new ProSetupSub();
								pross.setSusid(serverService.getServerID("prosetupsub"));
							}
							if("".equals(pss.getAmount())){
								dl=new BigDecimal(0);
							}else {
								dl=new BigDecimal(pss.getAmount());
							}
							dlsum=dlsum.add(dl);
							pross.setAmount(dl.toString());
							if(pross.getAmount()!=null&&!pross.getAmount().equals("")&&!pross.getAmount().equals("0")){
								proSetup.setTzType("01");
							}
							pross.setTypePpid(pss.getTypePpid());
							pross.setPpid(productPackaging.getPpID());
							pross.setSjdate(new Date());
							pross.setSuid(proSetup.getSuid());
							baselist.add(pross);
						}

						proSetup.setProxyprice(dlsum.toString());

						BigDecimal efbig = rebig.subtract(dlsum).subtract(brbig);//总金额-代理和-业务佣金 = 成本
						proSetup.setEfPrice(efbig.toString());
						productPackaging.setPrice(efbig.toString());

					}
					baselist.add(productPackaging);
					baselist.add(proSetup);

                    //计时收费
                    String hql1 = "from ExaminationRoomCharge where erId=? and CompanyID=? and startUsing=?";
                    ExaminationRoomCharge fse = (ExaminationRoomCharge) beandao.getBeanByHqlAndParams(hql1,
                            new Object[] { erCharge.getErId(), erCharge.getCompanyID(),"00" });

                    ExaminationRoomCharge fs = new ExaminationRoomCharge();
                    fs.setErcID(serverService.getServerID("erc"));
                    Calendar calendar = Calendar.getInstance();
                    fs.setChargeNumber(String.valueOf(calendar.getTime().getTime()));
                    fs.setErId(erCharge.getErId());
                    fs.setStaffID(erCharge.getStaffID());
                    fs.setStaffName(erCharge.getStaffName());
                    fs.setCompanyID(erCharge.getCompanyID());
                    fs.setGoodsID(goodsManage.getGoodsID());
                    if (fse == null)
                    {
                        fs.setStartUsing("00");
                    }
                    else
                    {
                        fs.setStartUsing("01");
                    }
                    baselist.add(fs);
                }
                beandao.saveBeansListAndexecuteHqlsByParams(baselist,
                        hqls.toArray(new String[] {}), parms.toArray());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除收费标准 *
     *
     * @param ercID
     *            考场收费标准id
     * @return
     */
    @Override
    @Transactional
    public boolean  delErCharge(String ercID) {
        boolean b = true;
        try {
            List<BaseBean> baselist = new ArrayList<BaseBean>();
            List<String> hqls = new ArrayList<String>();
            List<Object> parms = new ArrayList<Object>();

            //删除收费标准表
            String hql = "from ExaminationRoomCharge where ercID = ?";
            ExaminationRoomCharge fs = (ExaminationRoomCharge) beandao.getBeanByHqlAndParams(hql, new Object[]{ercID});
            fs.setStartUsing("02");
            baselist.add(fs);

            //删除产品表
            ProductPackaging productPackaging = (ProductPackaging) beandao.getBeanByHqlAndParams("from ProductPackaging where goodsID=?", new Object[]{fs.getGoodsID()});
            productPackaging.setDelStatus("01");
            baselist.add(productPackaging);

            beandao.saveBeansListAndexecuteHqlsByParams(baselist,
                    hqls.toArray(new String[] {}), parms.toArray());
        } catch (Exception e) {
            b = false;
        }
        return b;
    }


    /**
     * 启用收费标准 *
     *
     * @param ercID
     *         考场收费标准id
     * @return
     */
    @Override
    @Transactional
    public boolean examIsEnabled(String ercID) {
        boolean b = true;
        try {
            ExaminationRoomCharge fs = (ExaminationRoomCharge) beandao.getBeanByHqlAndParams("from ExaminationRoomCharge where ercID=?", new Object[]{ercID});
            fs.setStartUsing("00");
            String sql = "update dt_ExaminationRoomCharge f set f.startusing=?  where f.erId=? and f.CompanyID=? and f.startusing=? ";
            List<BaseBean> list = new ArrayList<BaseBean>();
            list.add(fs);
            beandao.saveBeansListAndexecuteSqlsByParams(list,
                    new String[] { sql }, new Object[] { "01",fs.getErId(),fs.getCompanyID(),"00" });
        } catch (Exception e) {
            b = false;
        }
        return b;
    }

    /**
     * 考场记录 *
     * @param companyID:公司ID
     * @param account:个人账号
     * @param signInState:签到状态
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm testRecordList(String companyID, String account, String signInState,PageForm pageForm,int pageNumber) {

        StringBuilder sb = new StringBuilder();
        List<Object> list = new ArrayList<Object>();
        sb.append("select b.bifId,b.journalnum,f.staffname fname,m.account macct, ");
        sb.append("to_char(b.checkintime, 'YYYY-MM-DD HH24:MI:SS'),to_char(b.signbacktime, 'YYYY-MM-DD HH24:MI:SS'), ");
        sb.append("b.howMuchTime,b.money,e.ernumber,e.ername,d.staffname dnaem,s.staffname sname, ");
        sb.append("c.account cacct,a.staffname aname,b.signInState from ");
        sb.append("dt_examinationroom e,dt_BookingInformation b,t_eshop_cuscom m,dt_hr_staff f, ");
        sb.append("dt_hr_staff d,dt_hr_staff s,t_eshop_cuscom c,dt_hr_staff a ");
		sb.append("where e.erid = b.erid and b.sccid=m.sccid and m.staffid=f.staffid and a.sccid=c.sccid ");
		sb.append("and e.reviewerstaffid=a.staffid and d.staffid=b.directorstaffid and s.staffid=b.coachstaffid ");
		sb.append("and e.companyid=? and e.status=? and c.acquiesce=? ");
		list.add(companyID);
		list.add("00");
		list.add("01");
		if(signInState!=null && !signInState.equals(""))
		{
			sb.append("and b.signInState = ? ");
			list.add(signInState);
		}
		if(account!=null && !account.equals(""))
		{
			sb.append("and m.account like ? ");
			list.add("%"+account+"%");
		}
		sb.append("order by b.biftime desc ");
		pageForm = getPageFormBySQL(
				null != pageForm ? pageForm.getPageNumber() : 1,
				pageNumber == 0 ? 10 : pageNumber, sb.toString(),
				"select count(*) from (" + sb.toString() + ")",list.toArray());

        return pageForm;
    }



    /**
     * 删除停车信息 *
     *
     * @param bifId
     *            考场记录ID
     * @return
     */
    @Override
    @Transactional
    public boolean delBookingInformation(String bifId) {
        boolean b = true;
        try {
            String hql = "delete from BookingInformation where bifId = ?";
            beandao.saveBeansListAndexecuteHqlsByParams(null,
                    new String[] { hql }, new Object[] { bifId });
        } catch (Exception e) {
            b = false;
        }
        return b;
    }


    /**
     * 考场记录 *
     * @param bifId:记录ID
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Object testRecord(String bifId) {

        StringBuilder sb = new StringBuilder();
		sb.append("select b.bifId,b.journalnum,f.staffname fname,m.account macct, ");
		sb.append("to_char(b.checkintime, 'YYYY-MM-DD HH24:MI:SS'),to_char(b.signbacktime, 'YYYY-MM-DD HH24:MI:SS'), ");
		sb.append("b.howMuchTime,b.money,e.ernumber,e.ername,d.staffname dnaem,s.staffname sname, ");
		sb.append("c.account cacct,a.staffname aname,b.signInState,ct.CASHIERBILLSID,b.sccId from ");
		sb.append("dt_examinationroom e,dt_BookingInformation b,t_eshop_cuscom m,dt_hr_staff f, ");
		sb.append("dt_hr_staff d,dt_hr_staff s,t_eshop_cuscom c,dt_hr_staff a,Dtcashierbills ct ");
		sb.append("where e.erid = b.erid and b.sccid=m.sccid and m.staffid=f.staffid and a.sccid=c.sccid and ct.journalnum = b.journalnum ");
		sb.append("and e.reviewerstaffid=a.staffid and d.staffid=b.directorstaffid and s.staffid=b.coachstaffid ");
		if(bifId.indexOf("bif")!=-1) {
			sb.append("and b.bifid = ?");
		}else{
			sb.append("and b.journalNum = ?");
		}
		sb.append(" and e.status=? and c.acquiesce=? ");
        Object object = beandao.getObjectBySqlAndParams(sb.toString(),new Object[]{bifId,"00","01"});

        return object;
    }

	/**
	 * 佣金查询
	 * @param ercid
	 * @return
	 */
	@Override
	public List<BaseBean> queryProSetup(String ercid) {
    	String sql="select e.ercid,d.ef_price,d.re_price,t.type_ppid,t.amount,p.ppid,t.susid,t.suskey from DT_PRO_SETUP d,DT_PRO_SETUP_SUB t,DT_ExaminationRoomCharge e,dt_ProductPackaging p where e.goodsid=p.goodsid  and p.ppid=d.ppid and d.ppid=t.ppid and e.ercid=?";
    	List <BaseBean> list=beandao.getListBeanBySqlAndParams(sql,new Object[]{ercid});
		return list;
	}

	@Override
	public List<BaseBean> brokerage(String ercid) {
    	String sql="select d.brokerage from DT_PRO_SETUP d,DT_ExaminationRoomCharge e,dt_ProductPackaging p where e.goodsid=p.goodsid  and p.ppid=d.ppid  and e.ercid=?";
    	List<BaseBean> list=beandao.getListBeanBySqlAndParams(sql,new Object[]{ercid});
		return list;
	}

	/**
	 *
	 * 进入的时候判断
	 * * @param number
	 * @param date
	 * @return
	 */
		public Map<String,Object> checkCarRecord(String number,String date,String photopath,String upName1,String  upName2,String equipmentNumber,String companyID, String channel, String numberType, String parksId){
           System.out.println("checkCarRecord");
		String carmID = "";
		Map<String, Object> map = new HashMap<String, Object>();
		int Open = 0;
		String tip = "0";
		String siteid = "";

			String isAudit = "00";//总开关控制是否开启人工审核 00审核
			String numberAudit = "00";//不免人工审核
			VenueInformation venueInformation = (VenueInformation)beandao.getBeanByHqlAndParams("from VenueInformation v where v.siteId = (select n.siteId from EquipmentInformation n where n.equipmentNumber = ?) and companyId=?",new Object[]{equipmentNumber,companyID});
			if(venueInformation!=null){
				siteid = venueInformation.getSiteId();
				if(venueInformation.getIsAudit()!=null&&!venueInformation.getIsAudit().equals("")){
					isAudit = venueInformation.getIsAudit();
				}

				CarFeeAudit carFeeAudit = (CarFeeAudit)baseBeanService.getBeanByHqlAndParams("from CarFeeAudit c where c.carNumber = ? and siteId = ?",new Object[]{number,venueInformation.getSiteId()});
				if(carFeeAudit!=null){
					numberAudit = "01";//免人工审核
				}
			}


		//先查询是否在会员期间
		TimingCharging tca = (TimingCharging)baseBeanService.getBeanByHqlAndParams("from TimingCharging c where  c.siteId = (select n.siteId from EquipmentInformation n where n.equipmentNumber = ?) and c.carNumber = ? and c.state = '00'",new Object[]{equipmentNumber,number});
		List<BaseBean> clist = baseBeanService.getListBeanByHqlAndParams("from CarManage c where c.carNumber = ? and  c.status = '1' and c.equipmentNumber in (select n.equipmentNumber from EquipmentInformation n where n.siteId = ?)  order by c.indate desc",new Object[]{number,siteid});

		if(tca!=null){
			System.out.println("tca1");
			Date indate = tca.getStartDate();
			Date outdate = tca.getEndDate();
			Date date1 = Utilities.getDateFromString(date,"yyyy-MM-dd HH:mm:ss");

			if (date1.compareTo(indate) >= 0&&date1.compareTo(outdate)<=0) {
				//进入时间在会员期间
				Open = 1;// 开门有停车位   会员期间，看看是否有审核进入的记录

				if(clist.size()>0){
					CarManage cm = (CarManage) clist.get(0);
					if(cm.getAuditStatus()!=null&&!cm.getAuditStatus().equals("")){
						if("02".equals(cm.getAuditStatus())){
							//审核通过
							updateInCar(cm.getCarmID(),date,photopath,upName1,upName2);
							tip = "0"; //免费时长审核已通过可以进入
							carmID = cm.getCarmID();
							System.out.println("1updateInCar"+date);
						}
					}else{
						//有进来的记录但是自动审核的情况
						updateInCar(cm.getCarmID(),date,photopath,upName1,upName2);//进门识别后没进去又识别更改进入时间
						carmID = cm.getCarmID();
						System.out.println("2updateInCar"+date);
					}


				}else {
					//没有提交的审核记录，就正常进入
					 carmID = addManageCar(number, date, equipmentNumber, photopath, upName1, upName2, companyID, channel, numberType, parksId);
					tip = "";//有会员正常进入
					System.out.println("1addManageCar"+date);
				}
                   //会员期间
			} else{
				Open = 0;
				//进入时间不在会员期间就当过期了，而不是没到时间
				if(clist.size()>0){
					CarManage cm = (CarManage) clist.get(0);


                    if(isAudit.equals("00")&&numberAudit.equals("00")) {//开启人工审核了并且车牌不在免审核中，正常判断
						if (cm.getAuditStatus() != null && !cm.getAuditStatus().equals("")) {
							if ("00".equals(cm.getAuditStatus())) {
								//尚未提交审核  不增加进入记录 也不开门

								tip = "2";//进门请预付款，或者联系工作人员人工审核
								carmID = cm.getCarmID();
							} else if ("01".equals(cm.getAuditStatus())) {
								//审核中 不增加进入记录 也不开门

								tip = "3";//工作人员已提交人工审核请等待审核结果，也可以直接扫码付款
								carmID = cm.getCarmID();
							} else if ("02".equals(cm.getAuditStatus())) {
								//说明通过一次，但是
								carmID = addManageCar(number, date, equipmentNumber, photopath, upName1, upName2, companyID, channel, numberType, parksId);
								tip = "2";//进门请扫码付款，或者联系工作人员人工审核

							} else if ("03".equals(cm.getAuditStatus())) {
								//如果这条记录已经驳回了 可以重新生成一条进入记录

								carmID = addManageCar(number, date, equipmentNumber, photopath, upName1, upName2, companyID, channel, numberType, parksId);
								tip = "4";//给您提交的免费时长设置审核被上级驳回。您可以直接扫码付款。
							}
						} else {
							//过期没有带提交记录 生成一条
							carmID = addManageCar(number, date, equipmentNumber, photopath, upName1, upName2, companyID, channel, numberType, parksId);
							tip = "2";//进门请扫码付款，或者联系工作人员人工审核
							System.out.println("2addManageCar"+date);


						}
					}else{
						updateInCar(cm.getCarmID(),date,photopath,upName1,upName2);
                    	//关闭人工审核或者加入到了免人工审核中
						//还有进入的记录
						tip = "8";//不人工审核的话，进去的时候直接进
						carmID = cm.getCarmID();
						Open = 1;//直接进
						System.out.println("3updateInCar"+date);


					}

				}else{
					carmID = addManageCar(number, date, equipmentNumber, photopath, upName1, upName2, companyID, channel, numberType, parksId);
					System.out.println("3addManageCar"+date);

					if(isAudit.equals("00")&&numberAudit.equals("00")) {
						//过期没有带提交记录 生成一条

						tip = "2";//进门请扫码付款，或者联系工作人员人工审核
					}else {
						tip = "8";//不人工审核的话，进去的时候直接进
						Open = 1;//直接进
					}

				}

			}
		}else{
			System.out.println("tca2");

			// 没有预付过
			Open = 0;
			if(clist.size()>0){
				CarManage cm = (CarManage) clist.get(0);
				if(isAudit.equals("00")&&numberAudit.equals("00")) {//开启人工审核了并且车牌不在免审核中，正常判断
					if (cm.getAuditStatus() != null && !cm.getAuditStatus().equals("")) {
						if ("00".equals(cm.getAuditStatus())) {
							//尚未提交审核  不增加进入记录 也不开门

							tip = "2";//进门请预付款，或者联系工作人员人工审核
							carmID = cm.getCarmID();
						} else if ("01".equals(cm.getAuditStatus())) {
							//审核中 不增加进入记录 也不开门

							tip = "3";//工作人员已提交人工审核请等待审核结果，也可以直接扫码付款
							carmID = cm.getCarmID();
						} else if ("03".equals(cm.getAuditStatus())) {
							//如果这条记录已经驳回了 可以重新生成一条进入记录

							carmID = addManageCar(number, date, equipmentNumber, photopath, upName1, upName2, companyID, channel, numberType, parksId);
							tip = "4";//给您提交的免费时长设置审核被上级驳回。您可以直接扫码付款。
						}
					}

				}else{
					updateInCar(cm.getCarmID(),date,photopath,upName1,upName2);
                   //关闭人工审核或者加入到了免人工审核中
					//还有进入的记录
					tip = "8";//不人工审核的话，进去的时候直接进
					carmID = cm.getCarmID();
					Open = 1;//直接进
					System.out.println("4updateInCar"+date);


				}

			}else{


				carmID = addManageCar(number, date, equipmentNumber, photopath, upName1, upName2, companyID, channel, numberType, parksId);

				if(isAudit.equals("00")&&numberAudit.equals("00")) {
					//过期没有带提交记录 生成一条

					tip = "2";//进门请扫码付款，或者联系工作人员人工审核
				}else {
					tip = "8";//不人工审核的话，进去的时候直接进
					Open = 1;//直接进
				}

				System.out.println("4addManageCar"+date);

			}

			// 未提交审核 提交中，审核通过，审核驳回， 这几个阶段中都可以直接去付款。
		}
		map.put("Open",Open);
		map.put("tip",tip);
			map.put("carmID",carmID);
			if(clist.size()>0) {
				CarManage cm = (CarManage) clist.get(0);
				if(Open==1){

				cm.setOpen1("1");

					beandao.update(cm);
				}


			}
			System.out.println("tca1111");

			return map ;
	}
	
	/**
	 * 
	 * 进入保存记录
	 */
	 public  String addManageCar(String number,String date,String equipmentNumber,String photopath,String upName1,String upName2,String companyId,String channel,String numberType,String parksId){
		List<BaseBean> beans = new ArrayList<BaseBean>();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
		CarManage car = new CarManage();
		Calendar calendar = Calendar.getInstance();
		car.setCarmNumber(String.valueOf(calendar
				.getTime().getTime()));
		car.setCarmID(serverService
				.getServerID("carm"));
		car.setCarNumber(number);
		Date date1 = null;
		try {
			date1 = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		car.setIndate(date1);
		car.setEquipmentNumber(equipmentNumber);
		car.setEquipin(equipmentNumber);
		car.setPanorama(photopath + "\\" + upName1);
		car.setPicture(photopath + "\\" + upName2);

		String sql = "select nvl(max(to_number(c.serialNumber)),0) from dt_VenueInformation v,dt_EquipmentInformation e,dt_CarManage c where v.companyId=? and v.siteId=e.siteId and e.equipmentNumber=c.equipmentNumber and v.SITETYPE=1 ";
		Object count = beandao
				.getObjectBySqlAndParams(sql,
						new Object[] {companyId });
		int a = Integer.parseInt(count + "") + 1;
		String s = String.valueOf(a);
		car.setSerialNumber(s);
		car.setStatus(channel);
		car.setModel("0");
		car.setCreatedate(date1);
		car.setNumberType(numberType);
		car.setParksId(parksId);

		 String isAudit = "00";//总开关控制是否开启人工审核 00审核
		 String numberAudit = "00";//不免人工审核
		 VenueInformation venueInformation = (VenueInformation)beandao.getBeanByHqlAndParams("from VenueInformation v where v.siteId = (select n.siteId from EquipmentInformation n where n.equipmentNumber = ?) and companyId=?",new Object[]{equipmentNumber,companyId});
		 if(venueInformation!=null){
			 if(venueInformation.getIsAudit()!=null&&!venueInformation.getIsAudit().equals("")){
				 isAudit = venueInformation.getIsAudit();
			 }

			 CarFeeAudit carFeeAudit = (CarFeeAudit)baseBeanService.getBeanByHqlAndParams("from CarFeeAudit c where c.carNumber = ? and siteId = ?",new Object[]{number,venueInformation.getSiteId()});
            if(carFeeAudit!=null){
				numberAudit = "01";//免人工审核
			}
		 }

		 //判断进入时间是否在会员期间
		 TimingCharging tca = (TimingCharging)baseBeanService.getBeanByHqlAndParams("from TimingCharging  c where c.siteId = (select n.siteId from EquipmentInformation n where n.equipmentNumber = ?) and  c.carNumber = ? and c.state = '00'",new Object[]{equipmentNumber,number});
		 if(tca!=null){
			 Date indate = tca.getStartDate();
			 Date outdate = tca.getEndDate();

			 if (date1.compareTo(indate) >= 0&&date1.compareTo(outdate)<=0) {
				 //进入时间在会员期间
				 if(tca.getRemark()!=null &&tca.getRemark().equals("人工审核通过")){
                      car.setChargeState1("06");
				 }else{
					 car.setChargeState1("01");
				 }
                  //无需变动

			 } else{
				 //进入时间不在会员期间就当过期了，而不是没到时间
				 if(isAudit.equals("01")){//已关闭人工审核
					 car.setAuditStatus("");//都变为系统自动审核
				 }else{
					 //如果开启的人工审核判断这个车是否是免人工审核
					 if(numberAudit.equals("01")){//免人工审核
						 car.setAuditStatus("");//都变为系统自动审核
					 }else{
						 car.setAuditStatus("00"); //需要付款未提交的状态 //如果付款了，把状态置空 未提交审核

					 }


				 }
				 car.setChargeState1("00");
				 car.setOpen1("0");
			 }
		 }else{
			 // 没有过会员
			 //进入时间不在会员期间就当过期了，而不是没到时间
			 if(isAudit.equals("01")){//已关闭人工审核
				 car.setAuditStatus("");//都变为系统自动审核
			 }else{
			 	//如果开启的人工审核判断这个车是否是免人工审核
				 if(numberAudit.equals("01")){//免人工审核
					 car.setAuditStatus("");//都变为系统自动审核
				 }else{
					 car.setAuditStatus("00"); //需要付款未提交的状态 //如果付款了，把状态置空 未提交审核

				 }


			 }
			 car.setChargeState1("00");
			 car.setOpen1("0");
		 }



		beans.add(car);
		
		ParkingSpace sp = (ParkingSpace)beandao.getBeanByHqlAndParams("from ParkingSpace where parksId = ?",new Object[]{parksId});
		sp.setStatus("01");
		beans.add(sp);

		//把之前的进入记录处理掉
		 String hqlc = "from EquipmentInformation c where c.equipmentNumber = ?";

		 EquipmentInformation equipmentInformation = (EquipmentInformation)baseBeanService.getBeanByHqlAndParams(hqlc,new Object[]{equipmentNumber});


		 String hql =  "update CarManage c set c.status = ?,c.open = ? where c.status = ? and c.carNumber = ? and carmID!=? and c.equipmentNumber in (select n.equipmentNumber from EquipmentInformation n where n.siteId = ?)";
		 String hql2 = "update ParkingSpace p set p.status = ? where p.parksId!= ? and p.parksId in(select c.parksId from CarManage c where c.carNumber = ? and c.outdate is null)";
		 String[] hqls={hql,hql2};

		 List<Object[]> parmsList = new ArrayList<Object[]>();
		 List<Object> param  = new ArrayList<Object>();
		 param.add("0");
		 param.add("1");
		 param.add("1");
		 param.add(number);
		 param.add(car.getCarmID());
		 param.add(equipmentInformation.getSiteId());
		 parmsList.add(param.toArray());




		 List<Object> param1  = new ArrayList<Object>();
		 param1.add("00");
		 param1.add(parksId);
		 param1.add(number);
		 parmsList.add(param1.toArray());




		 beandao.executeHqlsByParmsList(beans,hqls,parmsList);










		 return car.getCarmID();
		
	}

		/**
		 * 
		 * 出去时更新数据
		 * @param cm
		 * @param number
		 * @param date
		 * @param equipmentNumber
		 * @param photopath
		 * @param upName1
		 * @param upName2
		 * @param channel
		 */
	public void updateManageCar(CarManage cm,String number,String date,String equipmentNumber,String photopath,String upName1,String upName2,String channel){
		
		try{
		System.out.println(cm.getIndate());
		System.out.println(number);
		System.out.println(date);
		System.out.println(equipmentNumber);
		System.out.println(photopath);
		System.out.println(upName1);
		System.out.println(upName2);
		System.out.println("channel"+channel);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟
		Date date1 = null;
		try {
			date1 = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cm.setOutdate(date1);
		cm.setEquipmentNumber(equipmentNumber);
		cm.setEquipout(equipmentNumber);
		cm.setMoney(amount(cm.getIndate(), cm.getOutdate(),
				cm.getEquipmentNumber(), cm.getCarNumber(),cm));
		System.out.println("出去装填ChargeState："+cm.getChargeState());
		System.out.println("出去装填ChargeType："+cm.getChargeType());
		cm.setTime(change(date1, cm.getIndate()));
		cm.setPanorama(photopath + "\\" + upName1);
		cm.setPicture(photopath + "\\" + upName2);
		cm.setStatus(channel);
		cm.setModel("0");
		cm.setCreatedate(date1);
		
		List<BaseBean> beans = new ArrayList<BaseBean>();
		if (cm.getParksId() != null && !cm.getParksId().equals("")) {
			ParkingSpace sp = (ParkingSpace) beandao.getBeanByHqlAndParams(
					"from ParkingSpace where parksId = ?",
					new Object[] { cm.getParksId() });
			sp.setStatus("00");
			System.out.println("修改停车位状态");
			beans.add(sp);
		}
		beans.add(cm);
			String hqlc = "from EquipmentInformation c where c.equipmentNumber = ?";

			EquipmentInformation equipmentInformation = (EquipmentInformation)baseBeanService.getBeanByHqlAndParams(hqlc,new Object[]{equipmentNumber});

			//把之前的进入记录处理掉
			String hql =  "update CarManage c set c.status = ?,c.open = ? where c.status = ? and c.carNumber = ? and carmID!=? and c.equipmentNumber in (select n.equipmentNumber from EquipmentInformation n where n.siteId = ?)";
			String[] hqls={hql};
			List<Object> param  = new ArrayList<Object>();
			param.add("0");
			param.add("1");
			param.add("1");
			param.add(number);
			param.add(cm.getCarmID());
			param.add(equipmentInformation.getSiteId());

		//	beandao.saveBeansListAndexecuteHqlsByParams(beans,null, null);

		beandao.saveBeansListAndexecuteHqlsByParams(beans,hqls, param.toArray());
		System.out.println("更新出去记录");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    /**
     * 
     * 根据终端编号查询公司ID
     * @param posNum
     * @return
     */
    public String getCompanyByPosNum(String posNum){
    	
    	String hql = "from CcomCom c where c.ccompanyId = (select p.accessCcomID from PosDevice p where p.posNum = ?)";
    	CcomCom ccom = (CcomCom)beandao.getBeanByHqlAndParams(hql,new Object[]{posNum});
    	if(ccom!=null){
    		return ccom.getComanyId();
    	}
    	return "";
    	
    }
    /**
     * 
     * 查询打印
     * @param carmID
     * @return
     */
    public Object getCarManageInfo(String carmID){
    	
    	String sql = "select c.serialNumber,c.carNumber,p.parkingCode,to_char(c.indate,'YYYY-MM-DD HH24:MI:SS'),to_char(c.outdate,'YYYY-MM-DD HH24:MI:SS'),c.time,c.money,v.siteName,v.ItsLocation,c.chargeState,c.status from DT_CARMANAGE c,DT_ParkingSpace p,DT_VENUEINFORMATION v where c.parksId = p.parksId and p.siteId = v.siteId and carmID = ? and v.SITETYPE=1 ";
		Object spobj = beandao.getObjectBySqlAndParams(sql,
				new Object[] { carmID });
		
		return spobj;
    	
    }
    /**
     * 
     * 出入没有进入记录时纪录异常数据
     * @param number
     * @param date
     * @param equipmentNumber
     * @param photopath
     */
    public String addErrorOut(String number,String date,String equipmentNumber,String photopath,String upName1,String upName2,String companyId,String channel,String numberType,String parksId){
    	
    	CarManage car = new CarManage();

    	try{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟

		car.setCarmID(serverService.getServerID("carm"));
		Calendar calendar = Calendar.getInstance();
		car.setCarmNumber(String.valueOf(calendar.getTime().getTime()));
		car.setCarNumber(number);
		car.setNumberType(numberType);
		car.setStatus(channel);
		Date date1 = sdf.parse(date);
		car.setOutdate(date1);
		car.setEquipmentNumber(equipmentNumber);
		car.setEquipin(equipmentNumber);
		car.setEquipout(equipmentNumber);
		car.setPanorama(photopath + "\\" + upName1);
		car.setPicture(photopath + "\\" + upName2);
	    car.setIndate(getCurrentStart(new Date()));
		car.setMoney(amount(car.getIndate(), car.getOutdate(),
					car.getEquipmentNumber(), car.getCarNumber(),car));
		car.setTime(change(date1, car.getIndate()));
	    String sql = "select nvl(max(to_number(c.serialNumber)),0) from dt_VenueInformation v,dt_EquipmentInformation e,dt_CarManage c where v.companyId=? and v.siteId=e.siteId and e.equipmentNumber=c.equipmentNumber and v.SITETYPE=1 ";
			Object count = beandao
					.getObjectBySqlAndParams(sql,
							new Object[] {companyId});
		int a = Integer.parseInt(count + "") + 1;
		String s = String.valueOf(a);
		car.setSerialNumber(s);
		car.setStatus("0");
		car.setModel("0");
		car.setParksId(parksId);
		car.setCreatedate(date1);
		beandao.save(car);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return car.getCarmID();
    }
    
    
    /**
     * 
     * 根绝设备号获取当前车牌
     * @param equipmentNumber
     * @return
     */
    public String getCarNumByEq(String equipmentNumber,String status){
		String hql = "";
		if(status==null||status.equals("")){
			status = "0";
		}
       if("0".equals(status)) {
		    hql = "from CarManage where equipout = ? and status = ? and chargeState = ? and outdate is not null and carNumber is not null and open = ? order by outdate desc";
	   }else{

			   hql = "from CarManage where equipin = ? and status = ? and chargeState1 = ? and outdate is null and carNumber is not null and open1 = ? order by indate desc";


	   }
    	List<BaseBean> list = beandao.getListBeanByHqlAndParams(hql, new Object[]{equipmentNumber,status,"00","0"});
    	String carNum = "";
    	if(list.size()>0){
			CarManage car = (CarManage) list.get(0);

				if (car != null) {
					if("0".equals(status)) {
					Date end = getCurrentYMD(car.getOutdate());
					Date today = getCurrentYMD(new Date());
				      	if (end.compareTo(today) == 0) {
						//说明是在当天出去
					    	carNum = car.getCarNumber();
				    	}
				    }else{
						carNum = car.getCarNumber();
					}
			}
    	}

    	return carNum;
    }
	private Date getCurrentYMD(Date date){
		Date newDate = null;
		try {
			String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(date).toString().split("-");
			Integer year = Integer.parseInt(strNow[0]);
			Integer month = Integer.parseInt(strNow[1]);
			Integer day = Integer.parseInt(strNow[2]);
			String endStr = year+"-"+month+"-"+day;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			newDate =  sdf.parse(endStr);

		} catch (ParseException e) {

		}
		return  newDate;
	}
	/**
	 *获取消费红包比例
	 *
	 * @param companyID
	 * @return
	 */
    public String getTotalPct(String companyID){
		StringBuilder sqlc = new StringBuilder();
		sqlc.append("select nvl(s.total_pct,0)");
		sqlc.append(" from dtCompany c left join dt_ccom_com cc on c.companyId = cc.compnay_id");
		sqlc.append(" left join dtContactCompany dc on dc.ccompanyId = cc.ccompany_Id");
		sqlc.append(" left join dt_set_subsidize s");
		sqlc.append(" on dc.industrytype = s.gtid and s.stutas=? where c.companyId = ?");
		Object objcom = beandao.getObjectBySqlAndParams(sqlc.toString(), new Object[]{"01", companyID});
		return objcom==null?"":objcom.toString();
	}


	/**
	 *
	 * @param pageSize
	 * @param pageNumber
	 * @param companyId
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm getVipFeeList(TimingCharging timingCharging,int pageSize,int pageNumber,String companyId) {
		String sql = " select p.tcid,p.carnumber,p.startdate,p.enddate,v.sitenumber,v.sitename,p.journalnum,p.tcKey,p.siteId,p.state,p.staffId,p.remark  from dt_timingcharging p left join dt_venueinformation v on p.siteid = v.siteid  where  p.state='00' and v.status='00' and v.companyid = ? and v.SITETYPE=1 ";
		List<Object> paramlist = new ArrayList<Object>();
		paramlist.add(companyId);
		if(timingCharging!=null){
			if(timingCharging.getSiteId()!=null&&!timingCharging.getSiteId().equals("")){
				sql+=" and (v.siteNumber = ? or v.siteName like ?)";
				paramlist.add(timingCharging.getSiteId());
				paramlist.add("%"+timingCharging.getSiteId()+"%");
			}

			if(timingCharging.getCarNumber()!=null&&!timingCharging.getCarNumber().equals("")){
				sql+=" and p.carNumber = ?";
				paramlist.add(timingCharging.getCarNumber());
			}
		}
		sql+=" order by p.tcid desc";

		PageForm pageForm = getPageFormBySQL(pageSize, pageNumber,sql,
				"select count(*) from (" + sql + ")",paramlist.toArray());

		return pageForm;
	}


	/**
	 *
	 * 添加修改收费VIP套餐

	 */
	public  void addUpdateVIP(TimingCharging timingCharging){
		 if(timingCharging.getTcId()==null||timingCharging.getTcId().equals("")){

			timingCharging.setTcId(serverService.getServerID("tcId"));
	  	}
		  beandao.update(timingCharging);

	}

	/**
	 *
	 * 删除VIP
	 * @param tcId
	 */
	public void deleteVIP(String tcId){
		String hql = "delete from TimingCharging where tcId = ?";
		List<Object[]> parmsList = new ArrayList<Object[]>();
		parmsList.add(new Object[]{tcId});
		beandao.executeHqlsByParmsList(null, new String[]{hql} , parmsList);


	}


	/**
	 * 停车位编号不重复
	 * @param
	 * @return
	 */
	public String checkcarNum(String carNum,String tcId,String siteId){

		if(tcId==null||tcId.equals("")){
			tcId= " ";
		}
		String hql = "from TimingCharging where carNumber = ? and tcId!=? and siteId = ? and state = ?";
		List<BaseBean> carlist = beandao.getListBeanByHqlAndParams(hql,new Object[]{carNum,tcId,siteId,"00"});
		if(carlist.size()==0){
			return "0";//不重复
		}else{
			return "1";//重复
		}

	}

	/**
	 * 修改进入记录

	 * @return
	 */
	public String updateInCar(String carmID,String date,String photopath,String upName1,String upName2){
		String hql = "from CarManage where carmID = ?";
		CarManage carManage = (CarManage)beandao.getBeanByHqlAndParams(hql,new Object[]{carmID});
		carManage.setPanorama(photopath + "\\" + upName1);
		carManage.setPicture(photopath + "\\" + upName2);
		carManage.setChargeState1("06");
		carManage.setIndate(Utilities.getDateFromString(date,"yyy-MM-dd HH:mm:ss"));
		beandao.update(carManage);
		System.out.println("updateInCar"+date);



		return carmID;
	}

	/**
	 *
	 * 不需要审核的车辆列表
	 * @return
	 */
	public PageForm getFeeAuditList(int pageNumber,int pageSize,String parameter,String companyID){
		List<Object> params = new ArrayList<Object>();

       String hql = "from CarFeeAudit where companyID = ? ";
		params.add(companyID);
       if(parameter!=null&&!parameter.equals("")) {
		   hql += " and (siteName like ? or carNumber like ?)";
		   params.add("%" + parameter + "%");
		   params.add("%" + parameter + "%");
	   }
		hql+=" order by createDate desc";
		PageForm pageForm = baseBeanService.getPageForm(pageNumber,pageSize,hql,params.toArray());
		return  pageForm;
	}


	/**
	 *
	 * 添加免审核的
	 * @param carNumber
	 * @param siteId
	 * @param companyID
	 * @return
	 */
	public String addFeeCar(String carNumber,String siteId,String companyID,String staffID,String siteName){

           String hql = "from CarFeeAudit where carNumber = ? and siteId = ? and companyID = ?";
		CarFeeAudit carFeeAudit = (CarFeeAudit) beandao.getBeanByHqlAndParams(hql,new Object[]{carNumber,siteId,companyID});

		if(carFeeAudit!=null){

			return "1";
		}else{
			carFeeAudit = new CarFeeAudit();
			carFeeAudit.setCfID(serverService.getServerID("cfid"));
			carFeeAudit.setStaffID(staffID);
			carFeeAudit.setCompanyID(companyID);
			carFeeAudit.setSiteName(siteName);
			carFeeAudit.setSiteId(siteId);
			carFeeAudit.setCarNumber(carNumber);
			carFeeAudit.setCreatedate(new Date());
			Staff staff = (Staff)beandao.getBeanByHqlAndParams("from Staff where staffID=?",new Object[]{staffID});
			carFeeAudit.setStaffName(staff.getStaffName());
			beandao.save(carFeeAudit);
			return "0";
		}
	}

	/**
	 *
	 * 删除免审核
	 * @param cfID
	 */
	public void removeFeeCar(String cfID){
      String hql = "delete from CarFeeAudit where cfID = ?";
      beandao.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql},new Object[]{cfID});

	}
	@Override
	public boolean uppdateSiteDetails(VenueInformation vf) {
		try {
			String sql = "update dt_VenueInformation v set v.status = ? where siteId = ? and v.SITETYPE=1 ";
			beandao.saveBeansListAndexecuteSqlsByParams(null,
					new String[] { sql }, new Object[] { vf.getStatus(),vf.getSiteId() });
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 查询场地列表 *
	 *
	 * @param hasBeenUnder:已满未满,companyId:公司id,numberOrName:场地名称或编号,pageForm:分页信息
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm siteListInfo(String hasBeenUnder, String companyId,
							 String numberOrName, PageForm pageForm,int pageNumber) {

		List<String> list = new ArrayList<String>();

		StringBuilder sb = new StringBuilder();
		if (hasBeenUnder!=null && !hasBeenUnder.equals(""))
		{
			sb.append("select * from (");
		}
		sb.append("select a.siteid,y.companyname,a.sitenumber,a.sitename,a.itslocation,a.sitearea,");
		sb.append("a.fieldcapacity,a.sitedate,a.eastLongitude,a.northLatitude,count(d.carmid),(case when count(d.carmid) >= to_number(a.fieldcapacity) then ");
		sb.append("? else ? end) s,abs(count(d.carmid) - a.fieldcapacity),a.status,a.staffName from (select v.siteid,");
		sb.append("v.sitenumber,v.sitename,v.itslocation,v.sitearea,v.fieldcapacity,c.ccompany_id,e.equipmentnumber, ");
		sb.append("to_char(v.sitedate,'YYYY-MM-DD HH24:MI:SS') as sitedate,v.eastLongitude,v.northLatitude,v.status,s.staffName ");
		sb.append("from dt_venueinformation v join dt_ccom_com c on v.companyid = ? and v.SITETYPE=1 ");
		sb.append("and v.companyid = c.compnay_id left join dt_equipmentinformation e on v.siteid = e.siteid LEFT JOIN dt_hr_staff s on v.staffid=s.staffid ");
		sb.append(") a left join dtcontactcompany y on a.ccompany_id = y.ccompanyid left join dt_carmanage d on a.equipmentnumber = d.equipmentnumber ");
		list.add("已满");
		list.add("未满");
		list.add(companyId);
		if (numberOrName!=null && !numberOrName.equals(""))
		{
			sb.append("where (a.sitename like ? or a.sitenumber like ?) ");
			list.add("%"+numberOrName+"%");
			list.add("%"+numberOrName+"%");
		}
		sb.append("group by a.siteid,y.companyname,a.sitenumber,a.sitename,a.itslocation,");
		sb.append("a.sitearea,a.fieldcapacity,a.sitedate,a.eastLongitude,a.northLatitude,a.status,a.staffName  order by a.sitedate desc ");
		if (hasBeenUnder!=null && !hasBeenUnder.equals(""))
		{
			sb.append(") b where b.s = ? ");
			list.add(hasBeenUnder);
		}
		pageForm = getPageFormBySQL(
				null != pageForm ? pageForm.getPageNumber() : 1,
				pageNumber == 0 ? 10 : pageNumber, sb.toString(),
				"select count(*) from (" + sb.toString() + ")",list.toArray());

		return pageForm;
	}

	@Override
	public boolean mobileDelSite(VenueInformation vfInfo) {
		boolean b = true;
		try {
			String hql = "delete from VenueInformation where siteId = ?";
			List<Object[]> parmsList = new ArrayList<Object[]>();
			parmsList.add(new Object[]{vfInfo.getSiteId()});
			beandao.executeHqlsByParmsList(null, new String[]{hql} , parmsList);
		} catch (Exception e) {
			b = false;
		}
		return b;
	}

	@Override
	public Object getParksByParksId(String parksId) {
		String sql="SELECT PARKINGCODE,PARKINGNAME,CARTYPE,PARKINGLENGTH,PARKINGWIDTH,STATUS,siteId,PARKSID FROM DT_PARKINGSPACE WHERE PARKSID = ?";
		Object objectBySqlAndParams = beandao.getObjectBySqlAndParams(sql, new Object[]{parksId});
		return objectBySqlAndParams;
	}

	@Override
	public boolean mobileDelParks(ParkingSpace parkingSpace) {
		boolean b = true;
		try {
			String hql = "delete from ParkingSpace WHERE PARKSID = ?";
			List<Object[]> parmsList = new ArrayList<Object[]>();
			parmsList.add(new Object[]{parkingSpace.getParksId()});
			beandao.executeHqlsByParmsList(null, new String[]{hql} , parmsList);
		} catch (Exception e) {
			b = false;
		}
		return b;
	}

	@Override
	public boolean mobileUpdateParksStatus(ParkingSpace parkingSpace) {
		try {
			String sql = "update DT_PARKINGSPACE p set p.STATUS = ? where PARKSID = ?";
			beandao.saveBeansListAndexecuteSqlsByParams(null,
					new String[] { sql }, new Object[] { parkingSpace.getStatus(),parkingSpace.getParksId() });
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Object getDeviceByEquipmentId(String equipmentId) {
		String sql="SELECT e.EQUIPMENTKEY,e.EQUIPMENTID,e.SITEID,e.DOOR,e.STATUS,e.EQUIPMENTNUMBER,e.DEVICENAME,e.UNITTYPE,e.MANUFACTURER,e.CHANNEL,v.SITENAME FROM dt_equipmentinformation e " +
				"LEFT JOIN dt_venueinformation v on e.SITEID=v.SITEID  " +
				"WHERE e.EQUIPMENTID = ?";
		Object objectBySqlAndParams = beandao.getObjectBySqlAndParams(sql, new Object[]{equipmentId});
		return objectBySqlAndParams;
	}

	@Override
	public void addParkingSpaceEmpty(ParkingSpaceEmpty emp) {
		emp.setEmptyId(serverService.getServerID("emptyId"));
		beandao.save(emp);
	}

	@Override
	public List<BaseBean> findParkingCodeBySiteId(String siteId) {
		String sql = "select e.PARKSID,e.PARKINGCODE from DT_PARKINGSPACE e where e.SITEID=? and e.STATUS=? ";
		List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql, new Object[]{siteId,"00"});
		return list;
	}

	@Override
	public List<BaseBean> findParkingSpaceEmptyByParksId(ParkingSpaceEmpty emp,String startTime,String endTime) {
		String sql = "select e.PARKS_ID from DT_PARKINGSPACE_EMPTY e where e.PARKS_ID=? and e.END_TIME BETWEEN TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS') AND TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS')";
		List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql, new Object[]{emp.getParksId(),startTime,endTime});
		return list;
	}


	/**
	 *
	 * 获取场地
	 * @return
	 */
	public List<String> getNewSiteList(String sccId){

		StringBuffer str = new StringBuffer();
		List<String> params = new ArrayList<String>();
		List<String> list = new ArrayList<String>();

		str.append(" select com.companyname,com.companyid from T_ESHOP_CUSCOM cus, dtCompany com, dt_ccom_com d, dtcontactcompany t where cus.companyid = com.companyid ");
		str.append(" and d.ccompany_id = t.ccompanyid and com.companyid = d.compnay_id and cus.account in (select j.account  from T_ESHOP_CUSCOM j  where j.sccid = ?) union  select com.companyname,com.companyid from  dt_ccom_com d, ");
		str.append(" dtCompany com,dtcontactcompany t,dtcos cos,T_ESHOP_CUSCOM cus, dt_hr_Staff staff, T_ESHOP_CUSCOM   ccus where staff.staffid = cos.staffid and staff.reference = cus.account and cos.companyid=com.companyid and cos.status='01' and cos.cosStatus='50' and com.companyid=d.compnay_id ");
		str.append("  and d.ccompany_id=t.ccompanyid  and com.companyid = ccus.companyid and cus.sccid = ? ");

		params.add(sccId);
		params.add(sccId);
		List<String> lists= new ArrayList<String>();
		List<BaseBean> beanlist1 = beandao.getListBeanBySqlAndParams(str.toString(), params.toArray());
		for (int i = 0; i < beanlist1.size(); i++) {
			Object[] obj3 = (Object[]) (Object) beanlist1.get(i);
			lists.add(isNull(obj3[1]).toString());
		}
		return lists;
	}
	private Object isNull(Object tep){
		if(tep==null||"null".equals(tep)){
			return "";
		}else{
			return tep;
		}
	}



	public static String formatNumberToChinese(int number) {
		if (number < 0) {
			return "负" + formatNumberToChinese(-number);
		}

		String[] units = {"", "十", "百", "千", "万", "十万", "百万", "千万", "亿"};
		String numberStr = String.valueOf(number);
		StringBuilder result = new StringBuilder();
		int length = numberStr.length();
		int unitIndex = 0;

		for (int i = 0; i < length; i++) {
			int digit = numberStr.charAt(i) - '0'; // 获取当前位的数字
			if (digit == 0) {
				// 如果当前数字为0，且不是个位，则跳过该单位（例如“1万0千”应简化为“1万千”）
				if (i != length - 1) {
					continue;
				} else { // 对于个位，即使是0也要加上单位“十”或“零”
					result.append("0");
				}
			} else {
				result.append(digit);
				if (i == length - 1) { // 个位直接加单位“十”或“零”
					result.append("");
				} else { // 其他位根据剩余长度和单位数组选择合适的单位
					int remainingLength = length - i - 1; // 剩余位数（不包括当前位）
					unitIndex = remainingLength % 4; // 计算应该在哪个单位上，例如12345中的5应该在“万”上，所以取余数3对应的单位
					if (unitIndex == 0 && result.charAt(result.length() - 1) != '十') { // 如果当前是万位但不是个位，需要添加“万”前缀
						result.append("万");
					} else if (unitIndex > 0) { // 添加其他单位（除了“万”）
						result.append(units[unitIndex]);
					}
				}
			}
		}
		return result.toString();
	}
}