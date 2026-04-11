package hy.ea.websitemall.card.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.human.*;
import hy.ea.bo.human.vo.StaffVO;
import hy.ea.bo.production.resume.JobWanted;
import hy.ea.service.CCodeService;
import hy.ea.service.UpLoadFileService;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.BankCardVerifiUtil;
import hy.ea.util.RandomDatas;
import hy.ea.util.Utilities;
import hy.ea.websitemall.card.service.QRCodeService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;

/**
 * 个人网站商城名片
 * zj
 *
 */

public class PersonalInformationAction {
	private static final Logger logger = LoggerFactory.getLogger(PersonalInformationAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private QRCodeService qrCodeService;
	@Resource
	private UploadContentToFileService contentToFileService;
	@Resource
	private CCodeService codeService;
	
    //public static final String APPKEY ="6cb55407d7b0ad92c9050e2db4350f3b";
	public static final String APPKEY ="44767899f2b49dc91c3a97c3955dfa45";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
	public static final String DEF_CHATSET = "UTF-8";
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	private StaffVO staffVo;
	private String staffId;
	private String result;
	private String type; 		//类型 00：活动 01：论坛 02：文章
	private File positive;		//证件正面图片
	private String positiveFileName;
	private PageForm pageForm;
	private File back;			//证件背面图片
	private String backFileName;			//证件背面图片
	private File[] pictureList;  //图片集合
	private String[] pictureListFileName;
	private String[] wheelbases;
	private String editType;
	private String category;
	
	private String sccid;
	private String mark;
	private String bankId;//银行卡id
	private String province;//所属省份
	private String bankAddress;// 所属城市 
	private String identifying;//判断个人 登录    or 公司登录
	private String flag;//判断是移动办公进去   还是个人中心进入    标识
	private String khd; //0为网页查看  1为APP查看
	private String user;
	private StaffBankAccount staffbank;//银行卡管理
	
	public  void perForAction() {
		/*HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom te = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		String user=ServletActionContext.getRequest().getParameter("user");
		if(te==null||(!te.getAccount().equals(user)&&!te.getStaffid().equals(staffId))){
			setEditType("01");
		}*/
	}
	
	
	/*
	 * 获取主页面信息
	 */
	@SuppressWarnings("unchecked")
	public String getPageHomeData(){
		perForAction();
		HttpServletRequest re=ServletActionContext.getRequest();
		String path = re.getContextPath();
		String basePath = re.getScheme() + "://"
				+ re.getServerName() + ":" + re.getServerPort()
				+ path + "/";
		String sccid=re.getParameter("sccid");
		re.setAttribute("sccid", sccid);
		StringBuffer sql = new StringBuffer();
        sql.append("select staff.staffid,staff.staffname,staff.headimage,cuscom.Custype,staff.reference,staff.weixin,");
		sql.append("staff.whereCompany,staff.staffPost,staff.birthday,staff.qrCodePath,staff.industryType,staff.position");
        sql.append(" from dt_hr_Staff staff left join T_ESHOP_CUSCOM cuscom on staff.staffid = cuscom.staffid");
        sql.append(" and cuscom.acquiesce = ? where cuscom.sccid = ? or cuscom.staffid = ?");

		Object[] obj=(Object[]) baseBeanService.getObjectBySqlAndParams(sql.toString(),new Object[]{
                "01",sccid,staffId});
		re.setAttribute("obj",obj);

		if(obj!=null&&obj.length!=0){
		    sql.delete(0,sql.length());
			sql.append("select mate.route,mate.elaborate from dtMienStyle mien left join dtMaterial mate");
			sql.append(" on mien.materialid = mate.materialid where mien.relateid = ? and mate.status = ? and type = ?");
			List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{obj[0],"00","00"});
			if(list.size()>0){
				List<Object[]> arrayList=new ArrayList<Object[]>();
				for(int i=0;i<list.size();i=i+2){
					Object[] array=new Object[2];
					array[0]=((Object[])list.get(i))[0];
					array[1]=(i+1)==list.size()?null:((Object[])list.get(i+1))[0];
					arrayList.add(array);
				}
				re.setAttribute("list",arrayList);
			}
			if(!"".equals(obj[2])&&obj[2]!=null){
				if("".equals(obj[9])||obj[9]==null){
					String upSql="update dt_hr_Staff set qrCodePath=? where staffid=?";
					String qrCodePath=qrCodeService.createQRCode(basePath+"ea/perinfor/ea_getPageHomeData.jspa?sccid="+sccid
                            ,"png","businessCard",obj[2].toString());
					baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{upSql},new Object[]{qrCodePath,obj[0]});
					obj[9]=qrCodePath;
				}
			}

			//证件信息
			StaffPapers paper=(StaffPapers)baseBeanService.getBeanByHqlAndParams(
					"from StaffPapers where category=? and staffID=?", new Object[]{"001",obj[0]});
			re.setAttribute("paper", paper);

			//银行卡
			StaffBankAccount bank=(StaffBankAccount)baseBeanService.getBeanByHqlAndParams(
					"from StaffBankAccount where staffID=? and isdefault=?",new Object[]{obj[0],"1"});
			re.setAttribute("bank",bank);

			//收货地址
			StaffReceiptAddress addres=(StaffReceiptAddress)baseBeanService.getBeanByHqlAndParams(
					"from StaffReceiptAddress where staffID=? and defaults=?",new Object[]{obj[0],"00"});
			re.setAttribute("addres",addres);

			//兴趣风采
            sql.delete(0,sql.length());
			sql.append("select mate.route,mate.elaborate,mate.materialID from dtMienStyle mien  left join dtMaterial mate");
			sql.append(" on mien.materialid=mate.materialid where mien.relateid=? and mate.status=? and type=?");
			List<Object> styleList=baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{obj[0],"00","00"});
			re.setAttribute("styleList",styleList);

			//求职简历
			JobWanted job=(JobWanted)baseBeanService.getBeanByHqlAndParams(
					"from JobWanted where staffID=?",new Object[]{obj[0]});
			re.setAttribute("job",job);

			//个人文章
			List<Object> articleList=baseBeanService.getListBeanBySqlAndParams(
					"select activitiesID,title,describe,picture from dtActivities where staffId=? and type=? and category=? and rownum<=? order by releaseTime desc"
                    , new Object[]{obj[0],"01","02","3"});
			re.setAttribute("articleList",articleList);

			//个人论坛
			List<Object> forumList=baseBeanService.getListBeanBySqlAndParams(
					"select activitiesID,title,describe,picture from dtActivities where staffId=? and type=? and category=? and rownum<=?  order by releaseTime desc"
                    , new Object[]{obj[0],"01","01","3"});
			re.setAttribute("forumList",forumList);

		}
		return "pageData_m";
	}
	/*
	 * 获取详细资料编辑页面信息
	 */
	public String getPersonalData(){
		perForAction();
		Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?",new Object[]{staffId});
		ServletActionContext.getRequest().setAttribute("staff",staff);
		if("01".equals(editType))
			return getPageHomeData();
		else
			return "personalData_m";
	}
	/*
	 * 保存详细资料
	 */
	public String SaveBusinessCard() throws NumberFormatException, IOException{
		HttpServletRequest re=ServletActionContext.getRequest();
		List<BaseBean> list=new ArrayList<BaseBean>();
		Staff sta=staffVo.getCstaff();
		Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?", 
				new Object[]{sta.getStaffID()});
		String route=re.getParameter("original");
		String path = re.getSession()
				.getServletContext().getRealPath("/");
		int iir=0;
		if(pictureList!=null){
			for(int i=0;i<pictureList.length;i++){
				int Pos=pictureList[i].getPath().lastIndexOf("\\");
				String[] str=wheelbases[i].split("-");
				String Suffix=pictureListFileName[i].substring(pictureListFileName[i].lastIndexOf(".") + 1);
				route=fileService.interceptionSavePictures(Suffix,path, pictureList[0].getPath().substring(Pos+1), 
						pictureList[i], "personal/activities", staffId+"/"+Utilities.getDateString(new Date(), "yyyy-MM-dd")
						,Double.parseDouble(str[0]),Double.parseDouble(str[1]),Double.parseDouble(str[2]),Double.parseDouble(str[3]),Double.parseDouble(str[4]));
				iir++;
			}
		}
		staff.setStaffName(sta.getStaffName());  //姓名	
		staff.setHeadimage(route);					//头像
		staff.setBirthday(sta.getBirthday()==null?staff.getBirthday():sta.getBirthday());		//生日
		staff.setReference(sta.getReference());		//电话
		staff.setWeixin(sta.getWeixin()==null?staff.getWeixin():sta.getWeixin());				//微信
		staff.setPosition(sta.getPosition());		//职位
		staff.setReferenceOrganization(
				sta.getReferenceOrganization()==null?staff.getReferenceOrganization():sta.getReferenceOrganization());	//邮箱
		staff.setReferenceCode(sta.getReferenceCode()==null?staff.getReferenceCode():sta.getReferenceCode());		//QQ
		staff.setWhereCompany(sta.getWhereCompany());  //所在公司
		staff.setIndustryType(sta.getIndustryType());	//行业
		staff.setLocalAreaValue(sta.getLocalAreaValue()==null?staff.getLocalAreaValue():sta.getLocalAreaValue());	//详细地址
		staff.setProvinceAddress(sta.getProvinceAddress()==null?staff.getProvinceAddress():sta.getProvinceAddress());//省份		
		staff.setEducationValue(sta.getEducationValue()==null?staff.getEducationValue():sta.getEducationValue());			//学历
		if(iir>0){
			staff.setQrCodePath("");
		}
		list.add(staff);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
		return "success";
	}
	/*
	 * 获取银行卡信息
	 */
	public String getBankCardInformation(){
		perForAction();
		HttpServletRequest re=ServletActionContext.getRequest();
		HttpSession session = re.getSession();
		SessionWrap sw = SessionWrap.getInstance();		
		String cardType=re.getParameter("cardType");
				 
		if("".equals(cardType)||cardType==null){
			cardType="00";
		}

		if("".equals(staffId)||staffId == null ){
			CAccount cAccount = (CAccount)sw.getObject(session, SessionWrap.KEY_CACCOUNT);
			staffId = cAccount.getStaffID();
			editType = "00";			
			mark = "02";
			flag="sys";
		}
		Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?",new Object[]{staffId});
		String hql="from StaffBankAccount where staffID=?";
		List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{staffId});
		TEshopCusCom tEshopCusCom = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?",new Object[]{sccid});
		re.setAttribute("cardType",cardType);
		re.setAttribute("staff",staff);
		re.setAttribute("list",list);
		re.setAttribute("userId",tEshopCusCom.getUserId());
		if("01".equals(editType))
			return getPageHomeData();
		else
			return "bankData_m";
	}
	/*
	 * 获取添加银行卡页面信息
	 */
	
	public String getaddBankCardInformation(){
		perForAction();
		HttpServletRequest re=ServletActionContext.getRequest();
		staffVo=new StaffVO();
		String bankID=re.getParameter("bankID");
		if(!"".equals(bankID)&&bankID!=null){
			StaffBankAccount staffBank=(StaffBankAccount)baseBeanService.getBeanByHqlAndParams(
						"from StaffBankAccount where bankAccountID=?",new Object[]{bankID});
			Map<String,StaffBankAccount> map=new HashMap<String, StaffBankAccount>();
			map.put("staffBank",staffBank);
			staffVo.setBankSn(map);
		}
		Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?",new Object[]{staffId});
		staffVo.setCstaff(staff);
		if("01".equals(editType))
			return getPageHomeData();
		else
			return "addbankData_m";
	}
	
	//登录银行和所对应的银行相对应	
	String http = "http://apis.haoservice.com/creditop/BankCardQuery/QryBankCardBy4Element";
	String key = "45154aa01bbb4d8f84b108935680a5fd";
	private String name;//姓名
	private String idCardCode;//身份证号
	private String accountNo;//银行卡号
	private String bankPreMobile;//银行预留电话 
		/** 
		 * @param
		 *            :请求接口
		 * @param
		 *            :参数
		 * @return 返回结果
		 */
	public String checkBankNum(){
	    Map<String,String> map=new HashMap<>();
        /*map.put("bankcard", "6222600260001072444");
        map.put("idcard", "230103196104153314");
        map.put("mobile", "13565548304");
        map.put("name", "张三");*/

        map.put("bankcard", accountNo);
        map.put("idcard", idCardCode);
        map.put("mobile", bankPreMobile);
        map.put("name", name);
        try {
            result= BankCardVerifiUtil.jiekou(map);
        } catch (NoSuchAlgorithmException e) {
            logger.error("操作异常", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("操作异常", e);
        } catch (InvalidKeyException e) {
            logger.error("操作异常", e);
        }
        return "success";
	}

	public static  String checkNum(String http, String key,String name,
			String idCardCode,String accountNo, String bankPreMobile) {
					
		OkHttpClient client = new OkHttpClient();

		 Request request = new Request.Builder()
		  .url("http://apis.haoservice.com/creditop/BankCardQuery/QryBankCardBy4Element?accountNo="+accountNo+"&name="+name+"&idCardCode="+idCardCode+"&bankPreMobile="+bankPreMobile+"&key="+key)
		  .get()
		  .build();

		 Response response = null;
		 String res = "";
		try {
			response = client.newCall(request).execute();
			res = response.body().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		}		
		
		return res;	
		
		}

	
	//查询银行卡的开口名字，卡类型
	
	/*String httpUrl = "http://api.avatardata.cn/Bank/Query";
	String bkey = "32e3e8dfc09b4aa2b09d17382ff85d14";*/

	//20180321银行卡归属地查询接口重新换
	String httpUrl = "http://apis.haoservice.com/lifeservice/bankcard/query";
	String bkey = "103303bcfda84fdf919d7c7103133951";

	public String checkBank(){
		String s=verifyingBankCard(httpUrl,bkey,accountNo);
		result=s;
		return "success";
	}
			
	public String verifyingBankCard(String httpUrl,String bkey,String accountNo){
		
	 	BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?card="+accountNo+"&key="+bkey;
	    //httpUrl = httpUrl + "?key="+bkey+"&cardnum="+accountNo;
	    logger.info("调试信息");
	    try { 
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");	       
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        logger.error("操作异常", e);
	    }
	    return result;				
	}
	
	
	 
	/*
	 * 添加或修改银行卡信息
	 */
	public String addBankCardInformation(){
		
		JSONObject obj=new JSONObject();
		String msg = "ok";
		staffbank.setBankAccountID(serverService.getServerID("StaffBankAccount"));
		staffbank.setProvince(province);
		staffbank.setBankAddress(bankAddress);
		staffbank.setAddTime(new Date());
		staffbank.setStaffID(staffId);
		staffbank.setType("01");
		staffbank.setCardType("00");
		StaffBankAccount sbankaccount=(StaffBankAccount)baseBeanService.getBeanByHqlAndParams(" from StaffBankAccount where staffID = ? and bankAccount = ?", new Object[]{staffId,staffbank.getBankAccount()});
		if(sbankaccount != null){
			 msg = "cunzai";
			 obj.accumulate("msg", msg);
			 result=obj.toString();		
			return "success";
		}
		try {
			baseBeanService.save(staffbank);
		} catch (Exception e) {			
			logger.error("操作异常", e);
			msg = "no";
		}	
		obj.accumulate("msg", msg);
		result=obj.toString();		
		return "success";		
	}
	/*
	 * 删除银行卡
	 */
	public String deleteBankCard(){
		HttpServletRequest re=ServletActionContext.getRequest();
		String bankAccountID=re.getParameter("bankAccountID");
		String[] sqls={"delete dt_hr_staff_BankAccount where bankAccountID=?"};
		Object[] obj={bankAccountID};
		String flag = "ok";			
		try {			
			baseBeanService.saveBeansListAndexecuteSqlsByParams(null, sqls, obj);
		} catch (Exception e) {
			flag = "no";
		}		
		JSONObject json = new JSONObject();
		json.accumulate("flag", flag);
		result = json.toString();
		return "success";
	}

	/*
	 * 设置默认银行卡
	 */
	public String setTheDefaultBankCard(){
		HttpServletRequest re=ServletActionContext.getRequest();
		String bankAccountID=re.getParameter("bankAccountID");
		String bankHql="from StaffBankAccount where staffID=? and type=? and isdefault=?";
		List<BaseBean> bankList=baseBeanService.getListBeanByHqlAndParams(bankHql, new Object[]{staffId,"01","1"});
		List<BaseBean> list=new ArrayList<BaseBean>();
		for(int i=0;i<bankList.size();i++){
			StaffBankAccount bank=(StaffBankAccount) bankList.get(i);
			bank.setIsdefault("0");
			list.add(bank);
		}
		String hql="from StaffBankAccount where staffID=? and bankAccountID=?";
		StaffBankAccount bank=(StaffBankAccount)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{staffId,bankAccountID});
		bank.setIsdefault("1");
		list.add(bank);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
		return "success";
	}
	/*
	 * 获取证件列表信息
	 */
	public String getPapersData(){
		perForAction();
		String hql="from Staff where staffID=?";
		Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{staffId});
		staffVo=new StaffVO();
		staffVo.setCstaff(staff);
		if("01".equals(editType))
			return getPageHomeData();
		else
			return "papersData_m";
	}
	/*
	 * 获取证件详细信息
	 */
	public String getPapersInformation(){
		perForAction();
		HttpServletRequest re=ServletActionContext.getRequest();

		TEshopCusCom 	cuscom=(TEshopCusCom)baseBeanService.getBeanByHqlAndParams(
				"from TEshopCusCom where staffid=?", new Object[]{staffId});
		re.setAttribute("cuscom",cuscom);
		String hql2="from Staff where staffID=?";
		Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams(hql2,new Object[]{staffId});
		re.setAttribute("staff",staff);
		String hql="from StaffPapers where category=? and staffID=?";
		StaffPapers paper=(StaffPapers)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{category,staffId});
		re.setAttribute("paper", paper);
		if("01".equals(editType))
			return getPageHomeData();
		else
			return "paperInf_m";
	}
	/*
	 * 添加证件信息
	 */
	public String addPapersData(){
		HttpServletRequest re=ServletActionContext.getRequest();
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		String positivePath="",backPath="";
		if(positive==null){
			positivePath=re.getParameter("originalPositive");
		}else{
			positivePath=fileService.savePhoto(path, positiveFileName, 
					positive, "personal/identification", staffId+"/"+Utilities.getDateString(new Date(), "yyyy-MM-dd"));
		}
		if(back==null){
			backPath=re.getParameter("originalBack");
		}else{
			backPath=fileService.savePhoto(path, backFileName, 
					back, "personal/identification", staffId+"/"+Utilities.getDateString(new Date(), "yyyy-MM-dd"));
		}
		String hql="from StaffPapers where category=? and staffID=?";
		StaffPapers paper=(StaffPapers)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{category,staffId});
		if(paper==null){
			paper=new StaffPapers();
			paper.setPapersID(serverService.getServerID("StaffPapers"));
			paper.setCategory(category);
			paper.setStaffID(staffId);
		}
		paper.setBack(backPath);
		paper.setPositive(positivePath);
		baseBeanService.saveOrUpdate(paper);
		return "success";
	}
	/*
	 * 获取风采列表信息
	 */
	@SuppressWarnings("unchecked")
	public String getMienList(){
		perForAction();
		HttpServletRequest re=ServletActionContext.getRequest();
		String sql="select staff.staffid,staff.staffname,staff.headimage,cuscom.Custype,staff.qrCodePath from dt_hr_Staff staff" +
				" left join T_ESHOP_CUSCOM cuscom on staff.staffid = cuscom.staffid" +
				" where staff.staffid=?";
		Object[] obj=(Object[]) baseBeanService.getObjectBySqlAndParams(sql,new Object[]{staffId});
		String sql2="select mate.route,mate.elaborate,mate.materialID from dtMienStyle mien  left join dtMaterial mate" +
				" on mien.materialid=mate.materialid where mien.relateid=? and mate.status=? and type=?";
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql2, new Object[]{obj[0],"00","00"});

		re.setAttribute("obj",obj);
		re.setAttribute("list",list);
		return "mienList_m";
	}
	/*
	 * 获取风采添加页面信息
	 */
	public String getAddMienData(){
		perForAction();
		String sql="select staff.staffid,staff.staffname,staff.headimage,cuscom.Custype,staff.qrCodePath from dt_hr_Staff staff" +
				" left join T_ESHOP_CUSCOM cuscom on staff.staffid = cuscom.staffid" +
				" where staff.staffid=?";
		Object[] obj=(Object[]) baseBeanService.getObjectBySqlAndParams(sql,new Object[]{staffId});
		ServletActionContext.getRequest().setAttribute("obj",obj);
		if("01".equals(editType))
			return getPageHomeData();
		else
			return "addMienData_m";
	}
	/*
	 * 添加风采信息
	 */
	public String addMienData() throws NumberFormatException, IOException{
		List<BaseBean> list=new ArrayList<BaseBean>();
		HttpServletRequest re=ServletActionContext.getRequest();
		String path = re.getSession()
				.getServletContext().getRealPath("/");
		String elaborate=re.getParameter("elaborate");
		if(elaborate==null||"".equals(elaborate))
			elaborate=" ";
		for(int i=0;i<pictureList.length;i++){
			if(pictureList[i]==null||"".equals(pictureList[i])){
				continue;
			}
			int Pos=pictureList[i].getPath().lastIndexOf("\\");
			String[] str=wheelbases[i].split("-");
			String Suffix=pictureListFileName[i].substring(pictureListFileName[i].lastIndexOf(".") + 1);

			String route=fileService.interceptionSavePictures(Suffix,path, pictureList[0].getPath().substring(Pos+1), 
					pictureList[i], "personal/activities", staffId+"/"+Utilities.getDateString(new Date(), "yyyy-MM-dd")
					,Double.parseDouble(str[0]),Double.parseDouble(str[1]),Double.parseDouble(str[2]),Double.parseDouble(str[3]),Double.parseDouble(str[4]));
			
			CardMaterial mate=new CardMaterial();
			mate.setMaterialID(serverService.getServerID("Material"));
			mate.setStaffID(staffId);
			mate.setTitle("个人风采");
			mate.setElaborate(elaborate);
			mate.setRoute(route);
			mate.setStatus("00");
			mate.setType("00");
			list.add(mate);
			MienStyle mien=new MienStyle();
			mien.setMienStyleID(serverService.getServerID("MienStyle"));
			mien.setRelateID(staffId);
			mien.setMaterialID(mate.getMaterialID());
			list.add(mien);
		}
		if(list.size()>0){
			baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
		}
		return "success";
	}
	/*
	 * 删除风采信息
	 */
	public String removeMienData(){
		String[] materialIds=ServletActionContext.getRequest().getParameter("photoRemove").split(",");
		String[] sqls=new String[materialIds.length];
		List<Object[]> obj=new ArrayList<Object[]>();
		for(int i=0;i<materialIds.length;i++){
			sqls[i]="update dtmaterial set status=? where materialID=? and staffID=?";
			obj.add(new Object[]{"01",materialIds[i],staffId});
		}
		baseBeanService.executeSqlsByParmsList(null, sqls, obj);
		return "success";
	}
	/*
	 * 获取个人履历信息
	 */
	public String getPersonalHistoryData(){
		perForAction();
		staffVo=new StaffVO();
		String staffHql="from Staff where staffID=?";
		staffVo.setCstaff((Staff)baseBeanService.getBeanByHqlAndParams(staffHql, new Object[]{staffId}));
		String resumeHql="from StaffResume where staffID=?";
		List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(resumeHql, new Object[]{staffId});
		ServletActionContext.getRequest().setAttribute("list",list);
		return "historyData_m";
	}
	/*
	 * 添加个人履历信息
	 */
	public String addPersonalHistoryData(){
		HttpServletRequest re=ServletActionContext.getRequest();
		int select=Integer.parseInt(re.getParameter("select"));
		List<BaseBean> list=new ArrayList<BaseBean>();
		Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?",
				new Object[]{staffId});
		for(int i=0;i<select;i++){
			StaffResume resume=staffVo.getRecord().get(i+"");
			StaffResume resu=(StaffResume)baseBeanService.getBeanByHqlAndParams("from StaffResume where recordID=?",
					new Object[]{resume.getRecordID()});
			if(resu==null){
				resu=new StaffResume();
				resu.setRecordID(serverService.getServerID("StaffResume"));
			}
			resu.setStaffID(staffId);
			resu.setStartTime(resume.getStartTime());
			resu.setEndTime(resume.getEndTime());
			resu.setCompanyName(resume.getCompanyName());
			resu.setPostName(resume.getPostName());
			resu.setPostCase(resume.getPostCase());
			resu.setPosition(resume.getPosition());
			resu.setPostAddress(resume.getPostAddress());
			list.add(resu);
		}

		list.add(staff);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(list,null,null);
		return "success";
	}
	/*
	 * 获取个人活动、论坛、文章列表
	 */
	public String getPersonalActivityList(){
		perForAction();
		String ssj=ServletActionContext.getRequest().getParameter("ssj");
		String hql="from Activities where staffId=? and type=? and category=? order by releaseTime desc";
		pageForm=baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1),20,hql, new Object[]{staffId,"01",type});
		if("ajax".equals(ssj)){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("pageForm",pageForm);
			JSONObject js=JSONObject.fromObject(map);
			result=js.toString();
			return "success";
		}else
			return "activityList_m";
	}
	/*
	 * 获取个人活动、论坛、文章详细信息
	 */
	public String getPersonalActivityData(){
		perForAction();
		HttpServletRequest re=ServletActionContext.getRequest();
		String status=re.getParameter("status");
		if("00".equals(status)){
			String activitiesID=re.getParameter("activitiesID");
			Activities ac=(Activities)baseBeanService.getBeanByHqlAndParams("from Activities where activitiesID=?",
					new Object[]{activitiesID});
			List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(
					"from UpLoadFile where parmeterID=? order by filedesc", new Object[]{ac.getActivitiesID()});
			re.setAttribute("list",list);
			re.setAttribute("ac",ac);
			re.setAttribute("date",new SimpleDateFormat("yyyy年MM月dd日").format(ac.getReleaseTime()));
		}else{   
			re.setAttribute("date",new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
		}
		return "activityData_m";		
	}
	/*
	 * 添加或修改个人活动、论坛、文章信息
	 */
	public String addPersonalActivityList() throws NumberFormatException, IOException{
		HttpServletRequest re=ServletActionContext.getRequest();
		List<BaseBean> list=new ArrayList<BaseBean>();
		Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams(
					"from Staff where staffID=?",new Object[]{staffId});
		Activities ac=(Activities)baseBeanService.getBeanByHqlAndParams(
					"from Activities where activitiesID=?",new Object[]{re.getParameter("activitiesID")});
		String route="",sql="";
		if(ac==null){
			ac=new Activities();
			ac.setActivitiesID(serverService.getServerID("Activities"));
			ac.setReleaseTime(new Date());
			ac.setType("01");
			ac.setStaffId(staffId);
			ac.setAuthor(staff.getStaffName());
			ac.setCategory(type);
		}
		ArrayList<String> array=new ArrayList<String>();
		String fileID=re.getParameter("fileID");
		if(!"".equals(fileID)){
			String[] strs=fileID.split(",");
			String str="";
			for(int i=0;i<strs.length;i++){
				if(i==0)
					str+="?";
				else
					str+=",?";
				array.add(strs[i]);
			}
			sql="delete dtUpLoadFile where fileID in ("+str+")";
			String sql2="from UpLoadFile where fileID not in ("+str+") order by filedesc";
			List<BaseBean> arrayList=baseBeanService.getListBeanByHqlAndParams(sql2,strs);
			for(int i=0;i<arrayList.size();i++){
				UpLoadFile upFile=(UpLoadFile) arrayList.get(i);
				upFile.setFiledesc(i+"");
				list.add(upFile);
			}
			fileService.deletePhotos(array);
		}
		Object filedesc= baseBeanService.getObjectBySqlAndParams(
				"select max(filedesc) from dtUpLoadFile where  parmeterID=?",new Object[]{ac.getActivitiesID()});
		String path = re.getSession()
				.getServletContext().getRealPath("/");
		
		//将描述，图片链接保存成txt
		StringBuffer txtcontent=new StringBuffer();
		txtcontent.append("<p>");
		if(pictureList!=null){
			for(int i=0;i<pictureList.length;i++){
				int Pos=pictureList[i].getPath().lastIndexOf("\\");
				String[] str=wheelbases[i].split("-");
				String Suffix=pictureListFileName[i].substring(pictureListFileName[i].lastIndexOf(".") + 1);

				route=fileService.interceptionSavePictures(Suffix,path, pictureList[0].getPath().substring(Pos+1), 
						pictureList[i], "personal/activities", staffId+"/"+Utilities.getDateString(new Date(), "yyyy-MM-dd")
						,Double.parseDouble(str[0]),Double.parseDouble(str[1]),Double.parseDouble(str[2]),Double.parseDouble(str[3]),Double.parseDouble(str[4]));
				UpLoadFile up=new UpLoadFile();
				up.setFileID(serverService.getServerID("UpLoadFile"));
				up.setParmeterID(ac.getActivitiesID());
				up.setFilename("00".equals(type)?"个人活动":"01".equals(type)?"个人论坛":"个人文章");
				up.setFilepath(route);
				up.setFiledesc((Integer.parseInt(filedesc==null?"0":filedesc.toString())-(fileID.split(",").length-1)+i+1)+"");
				list.add(up);
				
				txtcontent.append("<img src='");
				txtcontent.append(route);
				txtcontent.append("'/><br/>");
				
			}
		}
			txtcontent.append("</p>");
			txtcontent.append("<p>");
			txtcontent.append(re.getParameter("describe"));
			txtcontent.append("</p>");
			String txtpath=saveContentToFile(txtcontent.toString());
			ac.setTxturl(txtpath);
		
		ac.setTitle(re.getParameter("title"));
		ac.setDescribe(re.getParameter("describe"));
		ac.setPicture(route);
		ac.setShareLink(re.getParameter("shareLink"));
		list.add(ac);
		if("".equals(sql))
			baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
		else
			baseBeanService.saveBeansListAndexecuteSqlsByParams(list, new String[]{sql},fileID.split(","));
		return "success";
	}
	
	/*
	 * 删除个人活动、论坛、文章信息
	 */
	public String deletePersonalActivityList(){
		HttpServletRequest re=ServletActionContext.getRequest();
		String del=re.getParameter("del");
		String[] dels=del.split(" - ");
		List<String> sqls=new ArrayList<String>();
		List<Object[]> params=new ArrayList<Object[]>();
		for(int i=0;i<dels.length;i++){
			sqls.add("delete dtActivities where activitiesID=? and type=? and staffId=?");
			params.add(new Object[]{dels[i],"01",staffId});
			sqls.add("delete dtUpLoadFile where parmeterID=?");
			params.add(new Object[]{dels[i]});
			@SuppressWarnings("unchecked")
			List<String> array=baseBeanService.getListBeanBySqlAndParams(
					"select filepath from dtUpLoadFile where parmeterID=?", new Object[]{dels[i]});
			String path = re.getSession().getServletContext().getRealPath("/");
			for(int r=0;r<array.size();r++){
				String s=path+array.get(r);
				array.remove(r);
				array.add(r,s);
			}
			fileService.deletePhotos((ArrayList<String>)array);
		}
		baseBeanService.executeSqlsByParmsList(null, sqls.toArray(new String[]{}), params);
		return "success";
	} 
	/*
	 * 获取收货地址列表
	 */
	public String getReceiptAddressList(){
		perForAction();
		HttpServletRequest re=ServletActionContext.getRequest();
		String hql="from StaffAddress where staffID=? order by isDefault";
		List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{staffId});
		re.setAttribute("list",list);
		if("01".equals(editType))
			return getPageHomeData();
		else
			return "addressList_m";
	}
	/*
	 * 获取收货地址详细信息
	 */
	public String getReceivingAddressDetails(){
		perForAction();
		HttpServletRequest re=ServletActionContext.getRequest();
		String addressID=re.getParameter("addressID");
		StaffAddress addres=(StaffAddress)baseBeanService.getBeanByHqlAndParams(
				"from StaffAddress where addressID=? and staffID=?",new Object[]{addressID,staffId});
		re.setAttribute("addres",addres);
		if("01".equals(editType))
			return getPageHomeData();
		else
			return "addressDetails_m";
	}
	/*
	 * 设置默认或删除收货地址
	 */
	public String ReceivingAddressDefaultOrDelete(){
		HttpServletRequest re=ServletActionContext.getRequest();
		String stauts=re.getParameter("stauts");
		String addressID=re.getParameter("addressID");
		if("del".equals(stauts)){
				List<BaseBean> listbean=new ArrayList<BaseBean>();
				String hqldel = "from StaffAddress where addressID=?";
				StaffAddress staffAddress = (StaffAddress)baseBeanService.getBeanByHqlAndParams(hqldel,new Object[]{addressID});
				if("是".equals(staffAddress.getIsDefault())){
					String hql="from StaffAddress where staffID=? order by isDefault";
					List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{staffId});
					if(list.size()>1){
						StaffAddress address =  (StaffAddress)list.get(1);
						address.setIsDefault("是");
						listbean.add(address);
					}
				}
				String sql="delete dt_hr_staff_address where addressID=? and staffid=?";
				baseBeanService.saveBeansListAndexecuteSqlsByParams(listbean, new String[]{sql},new Object[]{addressID,staffId});
		}else{
			String sql="update dt_hr_staff_address set isDefault=? where staffId=? and isDefault=?";
			baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{"",staffId,"是"});
			StaffAddress addres=(StaffAddress)baseBeanService.getBeanByHqlAndParams(
					"from StaffAddress where staffID=? and addressID=?", new Object[]{staffId,addressID});
			addres.setIsDefault("是");
			baseBeanService.update(addres);
		}
		return "success";
	}
	/*
	 * 添加或修改收货地址
	 */
	public String ReceivingAddressAddOrEdit(){
		HttpServletRequest re=ServletActionContext.getRequest();
		String addressID=re.getParameter("addressID");
		StaffAddress staffAdd=staffVo.getAddress().get("0");
		StaffAddress add=(StaffAddress)baseBeanService.getBeanByHqlAndParams("from StaffAddress where addressID=?",new Object[]{addressID});
		if(add==null){
			add=new StaffAddress();
			add.setAddressID(serverService.getServerID("StaffAddress"));
			add.setStaffID(staffId);
			List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams("from StaffAddress where staffID=?", new Object[]{staffId});
			add.setIsDefault(list.size()>0?"":"是");
		}
		add.setAddressDetailed(staffAdd.getAddressDetailed());
		add.setArea(staffAdd.getArea());
		add.setConsignee(staffAdd.getConsignee());
		add.setPhone(staffAdd.getPhone());
		baseBeanService.saveOrUpdate(add);
		return "success";
	}
	
	/*
	 * 省市区列表
	 * */
	public String getCitiesList(){
		 	String s =null;
	        String url ="http://v.juhe.cn/postcode/pcd";//请求接口地址
	        Map<String,Object> params = new HashMap<String,Object>();//请求参数
	            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
	            params.put("dtype","");//返回数据的格式,xml或json，默认json	 
	        try {
	            s =net(url, params, "GET");
	            JSONObject object = JSONObject.fromObject(s);
	            if(object.getInt("error_code")==0){
	                result=object.toString();
	            }else{
	            }
	        } catch (Exception e) {
	            logger.error("操作异常", e);
	        }
	        ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		return "success";
	}
	/*
	 * 邮编查询
	 * */
	public String getZipCode(){
		HttpServletRequest re=ServletActionContext.getRequest();
		String provinceID=re.getParameter("provinceID");
		String cityID=re.getParameter("cityID");
		String areaID=re.getParameter("areaID"); 
		String address=re.getParameter("address");
		String s=null;
		String url ="http://v.juhe.cn/postcode/search";//请求接口地址
        Map<String,Object> params = new HashMap<String,Object>();//请求参数
            params.put("pid",provinceID);//省份ID
            params.put("cid",cityID);//城市ID
            params.put("did",areaID);//区域ID
            params.put("q", address);//关键词
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
            params.put("dtype","");//返回数据的格式,xml或json，默认json
 
        try {
            s =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(s);
            if(object.getInt("error_code")==0){              
                result=object.toString();
            }else{
            }
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        	
		return "success";
	}
	public static String net(String strUrl, Map<String,Object> params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                        out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    logger.info("值：{}", e);
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            logger.error("操作异常", e);
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }
	@SuppressWarnings("rawtypes")
	public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                logger.error("操作异常", e);
            }
        }
        return sb.toString();
    }
	/**
	 * 
	 * 保存文本编辑器内容
	 * @param content
	 * @return
	 */
	private String saveContentToFile(String content) {
		String id = RandomDatas.getUUID();
		String path = ServletActionContext.getServletContext().getRealPath("/")
				+ "/upload_files/activitiesDetails/";
		try {
			contentToFileService.saveContent(id,
					content, path);
		} catch (IOException e) {
			logger.error("操作异常", e);
	
		}	
		return "/upload_files/activitiesDetails/"+id+UploadContentToFileService.suffix;
	}
	/**
	 * 根据companyID和codeID查询其子节点
	 */
	public String getListCCodeByPID() {
		HttpServletRequest re=ServletActionContext.getRequest();
		String codeID=re.getParameter("codeID");
		List<CCode> codeList = codeService.getCCodeListByPID("company201009046vxdyzy4wg0000000025", codeID);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codeList", codeList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	public StaffVO getStaffVo() {
		return staffVo;
	}
	public void setStaffVo(StaffVO staffVo) {
		this.staffVo = staffVo;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public File getPositive() {
		return positive;
	}
	public void setPositive(File positive) {
		this.positive = positive;
	}
	public File getBack() {
		return back;
	}
	public void setBack(File back) {
		this.back = back;
	}
	public File[] getPictureList() {
		return pictureList;
	}
	public void setPictureList(File[] pictureList) {
		this.pictureList = pictureList;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String[] getWheelbases() {
		return wheelbases;
	}
	public void setWheelbases(String[] wheelbases) {
		this.wheelbases = wheelbases;
	}
	public String[] getPictureListFileName() {
		return pictureListFileName;
	}
	public void setPictureListFileName(String[] pictureListFileName) {
		this.pictureListFileName = pictureListFileName;
	}
	public String getEditType() {
		return editType;
	}
	public void setEditType(String editType) {
		this.editType = editType;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPositiveFileName() {
		return positiveFileName;
	}
	public void setPositiveFileName(String positiveFileName) {
		this.positiveFileName = positiveFileName;
	}
	public String getBackFileName() {
		return backFileName;
	}
	public void setBackFileName(String backFileName) {
		this.backFileName = backFileName;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}	
	public String getSccid() {
		return sccid;
	}
	public void setSccid(String sccid) {
		this.sccid = sccid;

	}	
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}	
	public String getBankAddress() {
		return bankAddress;
	}
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	public String getIdentifying() {
		return identifying;
	}
	public void setIdentifying(String identifying) {
		this.identifying = identifying;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getKhd() {
		return khd;
	}
	public void setKhd(String khd) {
		this.khd = khd;
	}	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getIdCardCode() {
		return idCardCode;
	}


	public void setIdCardCode(String idCardCode) {
		this.idCardCode = idCardCode;
	}


	public String getAccountNo() {
		return accountNo;
	}


	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}


	public String getBankPreMobile() {
		return bankPreMobile;
	}


	public void setBankPreMobile(String bankPreMobile) {
		this.bankPreMobile = bankPreMobile;
	}
	public StaffBankAccount getStaffbank() {
		return staffbank;
	}
	public void setStaffbank(StaffBankAccount staffbank) {
		this.staffbank = staffbank;
	}	
	
}
