package hy.ea.office.service.impl;

import com.tiantai.wfj.service.GoldOrderService;
import hy.ea.bo.finance.BenDis.BonusPoints;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.FeeMessage;
import hy.ea.bo.office.ShortUrl;
import hy.ea.bo.office.TelMessage;
import hy.ea.bo.office.TquickMessage;
import hy.ea.office.service.TelMessageService;
import hy.ea.util.MobileMessagenew;
import hy.ea.util.RandomDatas;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class TelMessageServiceImpl implements TelMessageService {

	private Logger logger = LoggerFactory.getLogger(TelMessageServiceImpl.class);
	@Resource
	private BaseBeanDao beandao;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;

	@Resource
	private GoldOrderService goldOrderService;
	@Autowired
	private MobileMessagenew mobileMessage;
	private final int totalNum = 100;  //免费条数预设
	private final String colSccid = "sccid201601239QHAIZP9560000000191";
	private final BigDecimal price = new BigDecimal(0.08);

	/**
	 * 增加短信模板
	 *
	 * @param qmsID
	 * @param titleName
	 * @param content
	 */
	@Override
	public void addTelTemp(String qmsID, String titleName, String content, String staffID,String surl,String cate,String companyID) {
		TquickMessage tquickmessage = null;
		if (qmsID == null || qmsID.equals("")) {
			tquickmessage = new TquickMessage();
			tquickmessage.setQmsID(serverService.getServerID("qmsID"));

		} else {
			tquickmessage = (TquickMessage) beandao.getBeanByHqlAndParams("from TquickMessage where qmsID = ?", new Object[]{qmsID});

		}

		tquickmessage.setCreaterID(staffID);
		tquickmessage.setCreateTime(new Date());
		tquickmessage.setContent(content);
		tquickmessage.setTitleName(titleName);
		tquickmessage.setSurl(surl);
		tquickmessage.setCate(cate);
		tquickmessage.setCompanyID(companyID);
		beandao.update(tquickmessage);


	}

	/**
	 * 群发短息
	 *
	 * @param tels
	 * @param content
	 * @return
	 */
	@Override
	public String groupSendTel(String tels, String content, String staffID, String sccid,String companyID) {

		String reStr = "";    //消息发送返回的状态
		String t = "";
		try {
			String d = tels.replaceAll("[^0-9-]+", " ").trim();
			t = d.replaceAll("[ ]+", ","); //第二个参数是短信发送需要的电话号码分割url。etc.136855,11255
			mobileMessage.setContent(content);
			mobileMessage.setMobile(t);
			reStr = mobileMessage.sendMsg();
			logger.info("值：{}", reStr);

		} catch (Exception e) {
			logger.info("调试信息");
			logger.error("操作异常", e);
		}

		// 记录消息到系统
		List<BaseBean> beans = new ArrayList<BaseBean>();

		//记录系统外的短消息
		String[] tel = tels.split(",");
		for (int i = 0; i < tel.length; i++) {
			TelMessage tmsg = new TelMessage();
			String TEL = tel[i].trim();
			if (TEL.length() > 0) {
				String telID = serverService.getServerID("telmessage");
				tmsg.setStaffID(staffID);
				Staff ff = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staffID});
				tmsg.setStaffName(ff.getStaffName());
				tmsg.setTelMessageID(telID);
				tmsg.setContent(content);
				tmsg.setTelNum(TEL);
				tmsg.setStatus("01");
				tmsg.setSendDate(new Date());
				tmsg.setMsgStatus(reStr);
				tmsg.setSendTime(new Date());
				tmsg.setCompanyID(companyID);
				beans.add(tmsg);

			}

		}
		FeeMessage feeMessage = (FeeMessage) beandao.getBeanByHqlAndParams("from FeeMessage where staffID = ?", new Object[]{staffID});
		int sendnum = (content.length()/60+1)*tel.length;


		if (feeMessage == null) {
			feeMessage = new FeeMessage();
			feeMessage.setFmId(serverService.getServerID("fmid"));
			feeMessage.setStaffID(staffID);
			feeMessage.setTotalNum(totalNum);
			feeMessage.setSurplusNum(totalNum - sendnum);

		} else {
			feeMessage.setSurplusNum(feeMessage.getSurplusNum() - sendnum);
		}
		int yx = 0;
		if (feeMessage.getSurplusNum() < sendnum) {  //如果免费条数不够
			yx = yx - feeMessage.getSurplusNum();
			feeMessage.setSurplusNum(0);
		}
		beans.add(feeMessage);


		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);

		//扣积分
		if(yx>0) {
			goldOrderService.messageCharge(sccid, colSccid, price.multiply(new BigDecimal(yx)));
		}


		return reStr;
	}


	/**
	 * 获取短信模板列表不分页
	 *
	 * @param staffID
	 * @return
	 */
	@Override
	public List<BaseBean> getdxTemp(String staffID,String companyID,String parameter,String cate) {
		String hql = "from TquickMessage where";
		List<Object> params = new ArrayList<Object>();
		if(companyID!=null&&!companyID.equals("")){
			hql += " companyID = ?";
			params.add(companyID);

		}else{
			hql += " createrID = ?";
			params.add(staffID);
		}

		if(parameter!=null&&!parameter.equals("")){
			hql += " and (titleName like ? or content like ?)";
			params.add("%"+parameter+"%");
			params.add("%"+parameter+"%");

		}

		if(cate!=null&&!cate.equals("")){
			hql += " and cate = ?";
			params.add(cate);
		}


		hql+=" order by createTime desc";
		List<BaseBean> list = beandao.getListBeanByHqlAndParams(hql, params.toArray());
		return list;
	}

	/**
	 * 删除短信模板
	 *
	 * @param qmsID
	 */
	@Override
	public void deleteTelTemp(String qmsID) {
		String hql = "delete from TquickMessage where qmsID = ?";
		List<Object[]> params = new ArrayList<Object[]>();
		params.add(new Object[]{qmsID});
		beandao.executeHqlsByParmsList(null, new String[]{hql}, params);

	}

	/**
	 * 保存短链接
	 *
	 * @param curl
	 */
	public String saveShortUrl(String curl, String staffID) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/";

		String hql = "from ShortUrl where  curl = ?";

		String[] curls = curl.split(",");
		ShortUrl shortUrl = null;
		List<BaseBean> beans = new ArrayList<BaseBean>();
		String surl = "";
		try {
			for (int i = 0; i < curls.length; i++) {
				String c = curls[i];


				shortUrl = (ShortUrl) beandao.getBeanByHqlAndParams(hql, new Object[]{c});
				if (shortUrl == null) {
					shortUrl = new ShortUrl();
					shortUrl.setSuId(serverService.getServerID("suid"));
					String s = genScurl();
					shortUrl.setSurl("n/v.jspa?v=" + s);
					shortUrl.setCurl(java.net.URLDecoder.decode(c, "utf-8"));
					shortUrl.setStaffID(staffID);
					shortUrl.setSeq(s);
					beans.add(shortUrl);
					surl += basePath + shortUrl.getSurl() + ",";
				} else {
					surl += basePath + shortUrl.getSurl() + ",";
				}
			}
		}catch (Exception e){

		}
		surl = surl.substring(0, surl.lastIndexOf(","));


		beandao.saveBeansListAndexecuteHqlsByParams(beans, null, null);

		return surl;


	}

	/**
	 * 生成短链接
	 *
	 * @return
	 */
	private String genScurl() {
		String seq = RandomDatas.getRandomString(6);
		String hql = "from ShortUrl where  seq = ?";
		ShortUrl shortUrl = (ShortUrl) beandao.getBeanByHqlAndParams(hql, new Object[]{seq});
		if (shortUrl == null) {
			return seq;
		} else {
			genScurl();

		}

		return null;

	}


	/**
	 * 访问短链接
	 *
	 * @param surl
	 * @return
	 */
	public String accessSurl(String surl) {
		String seq = surl.substring(surl.indexOf("?") + 3);
		String hql = "from ShortUrl where  seq = ?";
		ShortUrl shortUrl = (ShortUrl) beandao.getBeanByHqlAndParams(hql, new Object[]{seq});

		return shortUrl != null ? shortUrl.getCurl() : "";
	}

	/**
	 * 获取剩余的免费短信条数
	 *
	 * @param staffID
	 * @return
	 */
	public Map<String, Object> getSyMessage(String staffID, String sccid) {
		Map<String, Object> map = new HashMap<String, Object>();
		int surplusnum = 0;
		String hql = "from FeeMessage where staffID = ?";
		FeeMessage feeMessage = (FeeMessage) beandao.getBeanByHqlAndParams(hql, new Object[]{staffID});
		if (feeMessage == null) {
			surplusnum = totalNum;
		} else {
			surplusnum = feeMessage.getSurplusNum();
		}
		map.put("ysnum", surplusnum);
		BonusPoints bp = (BonusPoints) beandao.getBeanByHqlAndParams("from BonusPoints where sccid = ?", new Object[]{sccid});
		map.put("scorenum", bp==null?"0":bp.getBonusPointScore());

		return map;
	}


	/**
	 * 短信发送记录
	 *
	 * @param pageNumber
	 * @param pageSize
	 * @param parameter  type:00 推荐 01 关注
	 * @param staffID
	 * @return
	 */
	public PageForm getDxRecordlist(int pageNumber, int pageSize, String parameter, String staffID,String companyID) {
		List<Object> params = new ArrayList<Object>();
		String hql = "";
		if(companyID!=null&&!companyID.equals("")){
			params.add(companyID);
			 hql = "from TelMessage where companyID = ?";
		}else{
			params.add(staffID);
			 hql = "from TelMessage where staffID = ?";

		}
		if (parameter != null && !parameter.equals("")) {
			hql += " and telNum = ?";
			params.add(parameter);

		}
		hql+=" order by sendDate desc";

		PageForm pageForm = baseBeanService.getPageForm(pageNumber, pageSize, hql, params.toArray());
		return pageForm;

	}
	/**
	 *
	 * 获取公司
	 * @param pageNumber
	 * @param pageSize
	 * @param parameter

	 * @return
	 */
	public PageForm getDxCompanylist(int pageNumber, int pageSize, String  parameter){
		List<Object> params = new ArrayList<Object>();
		String sql = "select c.ccompanyid,c.companyname,c.logopath from  t_Eshop_Cuscom t ,dt_ccom_com m,Dtcontactcompany c where t.companyid=m.compnay_id and m.ccompany_id = c.ccompanyid and c.webstatus='01' and c.companyname is not null";

		if (parameter != null && !parameter.equals("")) {
			sql += " and c.companyname like ?";
			params.add("%"+parameter+"%");

		}
		PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")", params.toArray());
		return pageForm;

	}

	/**
	 * 获取平台短信在第三方剩余金额
	 *
	 * @return
	 */

	public String getPlatMes() {
		String reStr = "";

		try {
			reStr = mobileMessage.queryOverage();
			if(Float.parseFloat(reStr)<100){
				mobileMessage.setContent("短信小余平台短信余额不足为："+reStr);
				mobileMessage.setMobile("15210904250");
				 mobileMessage.sendMsg();
			}


		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return reStr;

	}


	/**
	 *
	 *获取高德一级分类
	 * @return
	 */
	public List<Object> getCateList(){
        String  sql = "select codeValue from dtScode where codePID = ? order by codeNumber";
		List<Object> list = baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{"scode20201106rnzhjs7ap60000000001"});

		return list;
	}


	/**
	 *
	 * 获取手机号分类及往来单位和往来个人
	 * @return
	 */
	public List<Object> getRelatelist(String companyID){
          String sql = "select  c.codeValue from dtCCode c where  codePID = ? and c.companyID= ? union select  c.codeValue from dtCCode c where  codePID = ? and c.companyID= ? order by codeValue";
		  List<Object> list = baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{"scode20110224xpd2t2jvda0000000002",companyID,"scode20110106hfjes5ucxp0000000017",companyID});
		return list;
	}

	/***
	 *  根据分类获取往来个人和往来单位电话
	 *
	 * @param ralate
	 * @return
	 */
	public PageForm getTellistByCate(String ralate,String companyID,int pageNumber, int pageSize, String parameter){
		List<Object> params = new ArrayList<Object>();
		String sql = "select  ?,?,c.STAFFNAME,c.REFERENCE from view_conuser c where  c.companyID = ? ";
		params.add("");
		params.add("");

		params.add(companyID);
		if(ralate!=null&&!ralate.equals("")){
			sql+=" and c.relation = ? ";
			params.add(ralate);
		}
		if(parameter!=null&&!parameter.equals("")){
			sql+=" and (c.reference = ? or c.staffName like ?)";
			params.add(parameter);
			params.add("%"+parameter+"%");
		}

		sql+=" union ";
		sql+=" select v.COMPANYNAME,v.COMPANYTEL,v.CRESPONSIBLE,v.RESPONSIBLETEL from  view_concom v where  v.companyid=?";

		params.add(companyID);
		if(ralate!=null&&!ralate.equals("")){
			sql+=" and v.contactconnections = ? ";
			params.add(ralate);
		}
		if(parameter!=null&&!parameter.equals("")){
			sql+=" and (v.RESPONSIBLETEL = ? or v.CRESPONSIBLE like ? or v.COMPANYTEL = ? or v.COMPANYNAME like ? )";
			params.add(parameter);
			params.add("%"+parameter+"%");
			params.add(parameter);
			params.add("%"+parameter+"%");
		}
		PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber,pageSize,sql,"select count(*) from (" + sql.toString() + ")",params.toArray());
		return pageForm;

	}

	/**
	 *
	 * 获取在职员工电话
	 * @param companyID
	 * @return
	 */
	public PageForm getStaffTelList(String companyID,int pageNumber, int pageSize, String parameter){
	String sql = "select s.staffName, s.reference,d.postName from dtcos c left join dt_hr_deptpost d on c.depPostID = d.depPostID left join dtcorganization o on c.organizationID = o.organizationID right join dt_hr_staff s on c.staffID = s.staffID left join dtaudition da on c.staffID = da.staffID  where da.companyID = ? and da.status = '22' and c.companyID = ? and c.cosStatus = '50'  and c.status = '01' ";
	  List<Object> params = new ArrayList<Object>();
		params.add(companyID);
		params.add(companyID);
	   if(parameter!=null&&!parameter.equals("")){
		  sql+=" and (s.reference = ? or s.staffName like ?)";
		   params.add(parameter);
		   params.add("%"+parameter+"%");
	  }
	  PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber,pageSize,sql,"select count(*) from (" + sql.toString() + ")",params.toArray());
		return pageForm;

	}

}