package hy.ea.production.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hy.ea.bo.production.drive.TeachingContent;
import hy.ea.production.service.CarSchoolService;
import hy.ea.service.UploadContentToFileService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;

@Service
public class CarSchoolServiceImpl implements CarSchoolService {

	@Resource
	private BaseBeanDao beandao;
	@Resource
	private ServerService serverService;
	@Resource
	private UploadContentToFileService contentToFileService;
	private List<Object> list;
	private List<BaseBean> baseBeanList;
	private PageForm pageForm;
	private Object obj;
	//查询违章信息
	private static String URL ="http://www.cheshouye.com";
	private static String appId = "2068";
	private static String appKey= "97712cb42e3957691512860e1d520438";
	//查询学员所在位置
	private static String URL1 ="http://api.map.baidu.com/geocoder/v2/?";
	private static String ak = "lhGNzoBuTvf04RDeshNtUG3KOmEaSV28";
	private static String output = "json";

	/**
	 * @Title: 查询
	 * @Description: 获取txt文件信息
	 * @return 返回文件信息
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	private String getContentFromFile(String filepath) {
		String path = ServletActionContext.getServletContext()
				.getRealPath("\\") + filepath;
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			return contentToFileService.getContent(path);

		} catch (IOException e) {
			logger.error("操作异常", e);
			return "";
		}
	}

	/**
	 * @Title: 查询
	 * @Description: 获取当前用户信息
	 * @return 返回当前用户对象
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public TEshopCusCom queryUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		return cus;
	}

	/**
	 * @Title: 查询
	 * @Description: 该方法只是转换List集合
	 * @return 返回list集合
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List transitionList(String sql, Object[] obj) {
		return beandao.getListBeanBySqlAndParams(sql, obj);

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

	// 驾校通知
	/**
	 * @Title: 查询
	 * @Description: 查询用户的全部通知信息
	 * @return 返回map集合
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> queryNews(int pageNumber, int pageSize,TEshopCusCom cusCom) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p.ppID,p.goodsname,g.url,p.packagingDate,c.state,c.csiId from ");
		sb.append("dtCarSchoolInform c,dt_ProductPackaging p,");
		sb.append("(select n.name,n.url,n.goodsid from dtGoodFunction n where n.orders='1') g ");
		sb.append("where p.goodsID=g.goodsid and c.ppId=p.ppID and p.ppID in ");
		sb.append("(select c.ppId from dtCarSchoolInform c where c.staffid=? and c.companyID=?) ");
		sb.append("and p.type=? order by p.packagingDate desc");
		pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(),
				"select count(*) from (" + sb.toString() + ")", new Object[] {
			cusCom.getStaffid(), cusCom.getCompanyId(), "驾校通知" });
		if(pageForm!=null){
			list = new ArrayList<Object>();
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object obj = (Object) pageForm.getList().get(i);
				Object[] oo = (Object[]) obj;
				list.add(i, new String[] { oo[0].toString(), oo[1].toString(),
						getContentFromFile(oo[2].toString()), oo[3].toString(),
						oo[4].toString(), oo[5].toString() });
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageSize", pageForm.getPageSize());
			map.put("count", pageForm.getRecordCount());
			map.put("pageCount", pageForm.getPageCount());
			map.put("pageNumber", pageForm.getPageNumber());
			map.put("list", list);
			return map;
		}
		return null;
	}

	/**
	 * @Title: 删除
	 * @Description: 删除通知信息csiIds为id集合串
	 * @return 返回list集合
	 */
	@Override
	@Transactional
	public void delNews(String csiIds) {

		String sql = "delete from dtCarSchoolInform c where c.csiId in (";
		List<Object[]> list = new ArrayList<Object[]>();
		Object[] csiId = csiIds.split(",");
		for (int i = 0; i < csiId.length; i++) {
			sql+="?,";
		}
		sql = sql.substring(0,sql.length()-1);
		sql+=")";
		list.add(csiId);
		beandao.executeSqlsByParmsList(null, new String[] { sql }, list);
	}

	/**
	 * @Title: 查询
	 * @Description: 查询用户通知详情
	 * @return 返回list集合
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Object> queryNewsDetails(String ppID) {

		StringBuilder sb = new StringBuilder();
		sb.append("select p.goodsname,p.packagingDate,n.url from ");
		sb.append("dt_ProductPackaging p,dtGoodFunction n ");
		sb.append("where p.goodsID=n.goodsid and p.ppID=? ");
		sb.append("and p.type=?");

		baseBeanList = beandao.getListBeanBySqlAndParams(sb.toString(),
				new Object[] { ppID, "驾校通知" });
		list = new ArrayList<Object>();
		for (int i = 0; i < baseBeanList.size(); i++) {
			Object obj = (Object) baseBeanList.get(i);
			Object[] oo = (Object[]) obj;
			list.add(i, new String[] { oo[0].toString(), oo[1].toString(),
					getContentFromFile(oo[2].toString()) });
		}
		return list;
	}

	/**
	 * @Title: 修改
	 * @Description: 修改用户通知状态00为未读,01为已读
	 * @return 无返回
	 */
	@Override
	@Transactional
	public void updateNewsDetails(String csiId) {

		String sql = "update dtCarSchoolInform c set c.state = ? where c.csiId = ?";
		List<Object[]> list = new ArrayList<Object[]>();
		Object[] obj = new Object[] { "01", csiId };
		list.add(obj);

		beandao.executeSqlsByParmsList(null, new String[] { sql }, list);

	}

	// 学车赚钱
	/**
	 * @Title: 查询
	 * @Description: 查询用户详细信息
	 * @return 返回的数据
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Object> queryUserDetails(TEshopCusCom cusCom) {
		String sql = "select f.headimage,f.staffName,f.staffID from dt_hr_Staff f where f.staffID =?";
		obj = beandao.getObjectBySqlAndParams(sql,
				new Object[] { cusCom.getStaffid() });
		Object[] obj1 = (Object[]) obj;
		list = new ArrayList<Object>();
		for (int i = 0; i < obj1.length; i++) {
			list.add(obj1[i]);
		}
		list.add(cusCom.getCusType());
		return list;

	}

	/**
	 * @Title: 查询
	 * @Description: 查询用户通过驾校赚取到的金币数(该金币数非全部金币,并且只获取赚到的金币数,不包含支出的金币数)
	 * @return 返回的数据
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Object queryGold(TEshopCusCom cusCom) {
		StringBuilder sb = new StringBuilder();
		sb.append("select sum(k.jb_num) ");
		sb.append("from dt_member_backup k,dtgoodsbills d,dt_productpackaging g ");
		sb.append("where k.staff_id = ? ");
		sb.append("and k.jb_num is not null and k.jb_num not like ? ");
		sb.append("and k.cash_id=d.cashierbillsid and d.ppid=g.ppid ");
		sb.append("and g.tradename like ?");
		obj = beandao.getObjectBySqlAndParams(sb.toString(),
				new Object[] { cusCom.getStaffid(), "-%", "%驾校%" });
		
		return obj;
	}

	/**
	 * @Title: 查询
	 * @Description: 查询金币公告
	 * @return 返回的数据
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Object> goldAnnouncement() {
		StringBuilder sql = new StringBuilder();
		sql.append("select w.wfj_jifen_id from dt_wfj_jifen w,t_eshop_cuscom t");
		sql.append(" where w.staff_id=t.staffid");
		sql.append(" and ( t.account='15810799888' or t.custype='0.5' or t.custype='1')");
		List<Object> a = transitionList(sql.toString(), null);
		sql.delete(0, sql.length());
		sql.append("select jd.jifen_detail_score,s.staffname");
		sql.append(" from dt_wfj_jifen_detail jd,dt_wfj_jifen j,dt_hr_staff s");
		sql.append(" where jd.wfj_jifen_id=j.wfj_jifen_id and j.staff_id=s.staffid and j.wfj_jifen_score>?");

		for (int i = 0; i < a.size(); i++) {
			sql.append(" and j.wfj_jifen_id != ?");
		}
		a.add(0, 0);

		sql.append(" and rownum<=200 order by jd.jifen_detail_date desc");
		List<Object> detail = transitionList(sql.toString(), a.toArray());
		return detail;
	}

	/**
	 * @Title: 查询
	 * @Description: 查询获取金币的详细信息
	 * @return 返回的数据
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm relevanceInformation(int pageNumber, int pageSize,TEshopCusCom cusCom) {
		StringBuilder sb = new StringBuilder();
		sb.append("select s.ctusername,d.goodsname,k.jb_num,");
		sb.append("s.contactuserid,g.image,g.price ");
		sb.append("from dt_member_backup k,dtcashierbills s,dtgoodsbills d,dt_productpackaging g ");
		sb.append("where k.staff_id = ? and k.jb_num is not null ");
		sb.append("and k.jb_num not like ? and k.cash_id=s.cashierbillsid ");
		sb.append("and k.cash_id=d.cashierbillsid and d.ppid = g.ppid ");
		sb.append("and g.tradename like ? ");
		pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(),
				"select count(*) from (" + sb.toString() + ")", new Object[] {
			cusCom.getStaffid(), "-%", "%驾校%" });
		return pageForm;
	}
	
	//推荐好友
	/**
	 * @Title: 查询
	 * @Description: 查询推荐人是当前账号的所有购买过驾校产品的账号信息
	 * @return 返回的数据
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm recommendFriend(int pageNumber, int pageSize,TEshopCusCom cusCom) {
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct  f.headimage,f.staffname,m.custype, f.staffid,m.account ");
		sb.append("from t_Eshop_Cuscom m,dtcashierbills s,dtgoodsbills t,dt_productpackaging g,dt_hr_staff f,dt_order_bill_add o,dtmarketing d ");
		sb.append("where m.account=d.userid and d.mkuserid=? and m.staffid=f.staffid  and m.sccid = o.oa_sccid ");
		sb.append("and o.oa_bill_id=s.cashierbillsid and s.cashierbillsid = t.cashierbillsid ");
		sb.append("and t.ppid = g.ppid and g.tradename like ? and m.staffid = f.staffid ");
		pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(),
				"select count(*) from (" + sb.toString() + ")", new Object[] {
			cusCom.getAccount(), "%驾校" });
		return pageForm;
	}
	
	//查询违章信息
	/**
	 * title:获取违章信息
	 * 
	 * @param carInfo
	 * @return
	 */
	public String getWeizhangInfoPost(String carInfo) {
		long timestamp = System.currentTimeMillis();

		String line = null;
		String signStr = appId + carInfo + timestamp + appKey;
		String sign = md5(signStr);
		try {
			URL postUrl = new URL(URL + "/api/weizhang/query_task?");
			String content = "car_info=" + URLEncoder.encode(carInfo, "utf-8") + "&sign=" + sign + "&timestamp=" + timestamp + "&app_id=" + appId;
		
			logger.info("调试信息");
			
			line = post(postUrl, content);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return line;
	}

	/**
	 * title:获取省份城市对应ID配置
	 * 
	 * @return
	 * @throws IOException
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String getConfig() throws IOException {
		String line = null;
		try {
			URL postUrl = new URL(URL + "/api/weizhang/get_all_config?");
			String content = "";
			line = post(postUrl, content);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return line;

	}

	/**
	 * title:post请求
	 * 
	 * @param postUrl
	 * @param content
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	private String post(URL postUrl, String content) {
		String line = null;
		try {
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.connect();
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(content);
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

			while ((line = reader.readLine()) != null) {
				return line;
			}
			reader.close();
			connection.disconnect();

		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return line;

	}

	/**
	 * title:md5加密,应与 (http://tool.chinaz.com/Tools/MD5.aspx) 上32加密结果一致
	 * 
	 * @param password
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	private String md5(String msg) {
		try {
			MessageDigest instance = MessageDigest.getInstance("MD5");
			instance.update(msg.getBytes("UTF-8"));
			byte[] md = instance.digest();
			return byteArrayToHex(md);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return null;
	}
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	private String byteArrayToHex(byte[] a) {
		StringBuilder sb = new StringBuilder();
		for (byte b : a) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}
	/**
	 * @return 
	 * @Title: 查询
	 * @Description: 查询学员所在位置
	 * @return 返回的数据
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String location(String coordinate) {
		try {
			String uu = URL1+"ak="+ak+"&output="+output+"&location="+coordinate;
            URL url = new URL(uu);    // 把字符串转换为URL请求地址
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
            connection.connect();// 连接会话
            // 获取输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {// 循环读取流
                sb.append(line);
            }
            br.close();// 关闭流
            connection.disconnect();// 断开连接
            return sb.toString();
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
		return "";
	}
	//教练日志
	/**
	 * @Title: 查询
	 * @Description: 查询教练一天的所有日志
	 * @return 返回的数据
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> teaching(String staffcid, Date orderDate){
		StringBuilder sb = new StringBuilder();
		sb.append("select d.ocrId,f.headimage,f.staffname,d.wotistrdate,d.wotienddate ");
		sb.append("from dtordercoachrecord d, dt_hr_staff f ");
		sb.append("where d.staffcid = ? and d.orderdate = to_date( ? , 'yyyy-mm-dd') ");
		sb.append("and stustate in (?,?,?,?) and d.staffstid=f.staffid");
		baseBeanList = beandao.getListBeanBySqlAndParams(sb.toString(), new Object[]{staffcid,orderDate,"00", "01", "02", "04"});  
		return baseBeanList;
	}
	
	/**
	 * @return 
	 * @Title: 查询
	 * @Description: 查询教学内容
	 * @return 返回的数据
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public  Map<String, Object> teachingContent(String ocrId){
		StringBuilder sb = new StringBuilder();
		sb.append("select f.headimage,f.staffname,d.orderdate,");
		sb.append("d.wotistrdate,d.wotienddate,d.ocrId ");
		sb.append("from dtordercoachrecord d, dt_hr_staff f ");
		sb.append("where d.staffstid = f.staffid and d.ocrid = ?");
		obj = beandao.getObjectBySqlAndParams(sb.toString(), new Object[]{ocrId});
		String sql = "select t.tctkey,t.tctcontent from dtTeachingContent t where t.ocrId = ?";
		Object obj1 = beandao.getObjectBySqlAndParams(sql, new Object[]{ocrId});
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("ordercoachrecord", obj);
		map.put("TeachingContent", obj1);
		return map;
	}
	/**
	 * @return 
	 * @Title: 查询
	 * @Description: 查询教练信息
	 * @return 返回的数据
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> queryTrain(String staffid,String teachingDate) {
		StringBuilder sb = new StringBuilder();
		sb.append("select o.trainarea, t.integral ");
		sb.append("from Dtcoachinfo o,dtordercoachrecord d,dtTeachingContent t ");
		sb.append("where o.staffid = d.staffcid and o.staffid = ? ");
		sb.append("and d.ocrid = t.ocrid ");
		sb.append("and d.orderdate = to_date(?, 'yyyy-mm-dd')");
		baseBeanList = beandao.getListBeanBySqlAndParams(sb.toString(), new Object[]{staffid,teachingDate}); 
        return baseBeanList; 
        
	}
	/** 
	* @Title: 查询
	* @Description: 查询教练当月所有日志
	* @return 返回的数据   
	*/
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> allLog(String teachingDate,String staffid) {
		StringBuilder sb = new StringBuilder();
		sb.append("select to_char(a.orderdate, 'yyyy-mm-dd'),");
		sb.append("count(decode (a.stustate,?,?)),count(*) ");
		sb.append("from (select * from dtordercoachrecord d where d.orderdate between ");
		sb.append("(select trunc(to_date( ? , 'yyyy-mm'), 'mm') from dual) and ");
		sb.append("(select last_day(to_date( ? , 'yyyy-mm')) from dual) ");
		sb.append("and d.stustate <> ? and d.staffcid = ?) a ");
		sb.append("group by a.orderdate order by a.orderdate desc");
		Object[] objs = new Object[]{"04","04",teachingDate,teachingDate,"03",staffid};
		baseBeanList = beandao.getListBeanBySqlAndParams(sb.toString(), objs);
		return baseBeanList;
	}

	/** 
	* @Title: 添加或修改
	* @Description: 添加或修改日志
	* @return 
	*/
	@Override
	@Transactional
	public void saveOrUpdateLog(TeachingContent teachingContent,String teachingDate) {
		if(teachingContent.getTctKey()==null || teachingContent.getTctKey().equals("")){
			String sql = "select to_char(last_day(to_date( ? , 'yyyy-mm-dd')),'dd') from dual";
			Object a = beandao.getObjectBySqlAndParams(sql, new Object[]{teachingDate});
			double b = (double)70/(Integer.parseInt(String.valueOf(a)));
			double f = new BigDecimal(b).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			String integral = String.valueOf(f);
			teachingContent.setIntegral(integral);
			teachingContent.setTctId(serverService.getServerID("tctId"));
		}
		beandao.saveOrUpdate(teachingContent);
	}
	/** 
	* @Title: 查询
	* @Description: 查询日志内容
	* @return 返回的数据   
	*/
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public TeachingContent queryLog(String tctKey){
		String hql = "from TeachingContent t where t.tctKey = ?";
		baseBeanList = beandao.getListBeanByHqlAndParams(hql, new Object[]{tctKey});
		TeachingContent tct = (TeachingContent) baseBeanList.get(0);  
		return tct;
	}
}
