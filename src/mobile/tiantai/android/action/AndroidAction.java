package mobile.tiantai.android.action;

import com.hx.httpclient.bi.EasemobIMUsers;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tiantai.wfj.bo.*;
import com.tiantai.wfj.edmandServe.bo.DemandDetail;
import com.tiantai.wfj.util.SessionWrap;
import com.tiantai.wfj.util.VerifyCodeManager;
import com.wechat.bo.Micropay;
import com.wechat.bo.WxpayFace;
import com.wechat.bo.WxpayFaceAuth;
import com.wechat.utils.WeixinUtil;
import com.wechatpay.bo.WxPayDto;
import com.wechatpay.service.WchatPay;
import com.wechatpay.utils.WeChatUtils;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.*;
import hy.ea.bo.finance.BenDis.JoinFans;
import hy.ea.bo.finance.InvestDeviceBind.DeviceBindStaff;
import hy.ea.bo.finance.PayBackupBill;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.*;
import hy.ea.bo.human.vo.SalaryIntegral;
import hy.ea.dao.CompanyDao;
import hy.ea.finance.service.AssiCartService;
import hy.ea.finance.service.BonusPointsService;
import hy.ea.marketing.service.PayFaceDeviceSerivce;
import hy.ea.production.service.ConsultManageService;
import hy.ea.production.service.MaterialManageService;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CLoginService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.*;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.bo.SDistrict;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import hy.tel.bo.TelInRecord;
import hy.tel.bo.TelInRecordDeal;
import hy.tel.bo.TelOutRecord;
import mobile.tiantai.android.bo.KuaiJieTianJia;
import mobile.tiantai.android.service.TelService;
import mobile.tiantai.android.util.JushMain;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@Scope("prototype")
public class AndroidAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private static int dx = 0;

	@Resource
	private VerifyCodeManager verifyCodeManager;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CompanyDao companyDao;
	@Resource
	private CCodeService codeService;
	@Resource
	private ServerService serverService;
	@Resource
	private CCodeService ccodeService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private CLoginService loginService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private BonusPointsService bpsi;
	@Resource
	private MaterialManageService scManageService;
	@Resource
	private PayFaceDeviceSerivce faceDeviceSerivce;
	@Resource
	private TelService telService;
	@Resource
	AssiCartService assiCartService;
	@Resource
	ConsultManageService consultManageService;
	private CAccount account;
	private PageForm pageForm;
	public List<String> list = new ArrayList<String>();
	private SDKTestSendTemplateSMS sdk = new SDKTestSendTemplateSMS();
	private File file;
	private List<File> pictureList;
	private String fileContentType;
	private String fileFileName;
	private TEshopCustomer customer;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private Object result;
	private String signature;// 微信加密签名
	private String timestamp;// 时间戳
	private String nonce;// 随机数
	private String echostr; // 随机字符串
	private String imagePath;
	private String staffid;
	private String staffidentitycard;

	private Logger log=LoggerFactory.getLogger(AndroidAction.class);
	//发送短信
	@Resource
	private MobileMessagenew msg;//短信接口
	@Autowired
	private MobileMessage msage;//发短信zzl

	/** 机构名 */
	private String companyIdentifier;
	/** 用户名 */
	private String user;
	/** sccId*/
	private String sccId;
	/** 密码 */
	private String password;
	/** 公司名称 */
	private String companyName;
	/** 部门名称 */
	private String organizationName;
	/** 人员名称 */
	private String staffName;
	private String sdate;
	private String edate;
	/** 每页显示条数 */
	private int pageSize;
	/** 页数 */
	private int pageNumber;
	/** 总数 */
	private int recordCount;
	/** 联系人姓名 */
	private String peopleName;
	/** 往来关系 */
	private String contact;
	/** 公司ID */
	private String companyID;
	/** 部门ID */
	private String organizationID;
	/** 计划实体 */
	private COSJobPlan jobPlans;
	/** 日志实体 */
	private LogBook logbooks;
	/** 行业类别 */
	private String industryType;

	public ServletRequest request = ServletActionContext.getRequest();
	private List<CCode> codeList;
	// 呼入电话
	private TelInRecord telInRecord;
	// 呼出电话
	private TelOutRecord telOutRecord;
	// 当前用户
	private String userId;
	// 类别
	private String type;// 个人01 公司02
	private String cusType;// 会员类别
	private ContactCompany contactCompany;
	private Staff cstaff;
	private String parameter;
	private String qqId;
	private String appOpenId;
	private String weiboId;
	private String longitude;//精读//最近一次登陆
	private String latitude;//纬度//最近一次登陆

	private String thirdId;

	private String eqId;	//QQ
	private String eOpenId;//微信
	private String eBlogId;//微博
	private String appType;//app类型



	private String code;
	private String selectType;//抽奖查询方式

	private static final String[] COMPANY_NAME_LIST = {"北京天太世统科技有限责任公司","中联园区微分金股份有限公司","四川省胜威驾校有限公司","资阳市胜威驾驶培训有限公司","绵阳市胜威驾培有限公司"};
	private static final String SELECT_TYPE_LOTTERY_DRAW = "lotteryDraw";

	/**
	 * 1 :登录企业不存在 2或者4:登录账号状态不正常 3或者5:登录账号密码不正确 97 :用户名和密码不能为空 0 :正常 String
	 * JSESSIONID=session.getId(); jsonObj.accumulate("JSESSIONID",
	 * JSESSIONID); 将account信息放入Session中
	 */
	public String login() {
		CAccount account = new CAccount();
		account.setAccountEmail(user);
		account.setAccountPassword(password);
		int status = 1;
		JSONObject jsonObj = new JSONObject();
		if (account.getAccountEmail() != null
				&& account.getAccountPassword() != null) {
			int result = loginService.login(companyIdentifier, account, "00");
			if (result == 1) {
				result = loginService.login(companyIdentifier, account, "01");
			}
			status = result;
			if (0 == status) {


				HttpSession session = ServletActionContext.getRequest()
						.getSession();
				if (account.getStaffID() != null
						&& account.getStaffID().length() > 0) {
					Staff s = (Staff) baseBeanService.getBeanByHqlAndParams(
							"from Staff where staffID=?",
							new Object[] { account.getStaffID() });
					account.setStaffName(s.getStaffName());
					account.setStaff(s);
				}
				account.setAccountOnLine("01");
				session.setAttribute("account", account);
				List<BaseBean> beans = new ArrayList<BaseBean>();
				beans.add(account);
				CLogBook logBook = logBookService.saveCLogBook(null, "登录系统",
						account);
				beans.add(logBook);
				baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
						null, null);
				jsonObj.accumulate("account", account);
			}
		}
		jsonObj.accumulate("result", status);
		result = jsonObj;
		return "success";
	}

	/**
	 * 修改密码 参数 一个 电话一个 密码 一个密码 密码是新 的w
	 *
	 */

	public String updatepwd() {
		String pahe = request.getParameter("pahe");
		JSONObject temp = new JSONObject();
		String hql = "update TEshopCustomer set password=? where account=? and logOff=0";
		try {
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
					new String[] { hql }, new String[] { password, pahe });
//			EasemobIMUsers.modifyIMUserPasswordWithAdminToken(pahe, password);
			temp.accumulate("result", "0");
		} catch (Exception e) {
			temp.accumulate("result", "1");
		}
		result = temp;
		return Action.SUCCESS;
	}
	
	/**
	 * 
	 * 用来处理批量处理环信用户密码数据
	 * @return
	 */
	public String dealHxPSW() {
		String pahe = request.getParameter("pahe");
		String full = request.getParameter("full");
		if (pahe != null && !pahe.equals("")) {
			JSONObject temp = new JSONObject();
			try {
				EasemobIMUsers.modifyIMUserPasswordWithAdminToken(pahe,
						"123456");
				logger.info("调试信息");
				temp.accumulate("result", "0");
			} catch (Exception e) {
				temp.accumulate("result", "1");
			}
			result = temp;
		} else {
			if ("all".equals(full)) {
				String hql = "from TEshopCustomer t";
				List<BaseBean> list = baseBeanService
						.getListBeanByHqlAndParams(hql, null);
				for (BaseBean bean : list) {
					TEshopCustomer tc = (TEshopCustomer) bean;
					if (tc != null) {
						if (tc.getAccount() != null
								&& tc.getAccount().length() == 11) {
							try {
								logger.info("调试信息");
								EasemobIMUsers
										.modifyIMUserPasswordWithAdminToken(
												tc.getAccount(), "123456");
							} catch (Exception e) {
								logger.error("操作异常", e);
							}
						}
					}
				}
			}
		}
		return Action.SUCCESS;
	}

	/**
	 *注册APP用户验证用户名
	 *
	 */
	public String isacounnt() {

		String pahe = request.getParameter("pahe");
		JSONObject temp = new JSONObject();
		String hql = "from TEshopCustomer where account=? AND logOff=0";

		TEshopCustomer tc = (TEshopCustomer) baseBeanService
				.getBeanByHqlAndParams(hql, new String[] { pahe });
		if (tc == null) {
			temp.accumulate("result", "0");
		} else {
			temp.accumulate("result", "1");
		}
		result = temp.toString();
		return Action.SUCCESS;

	}
	/**
	 * 判断是否是最新版的 安卓或者 IOS 参数 0 判断安卓最新版 1 IOS 最新版
	 */
	public String get() {
		String name = request.getParameter("name");
		String hql = "select aeedition,content,forceUpdate from dtAndroidEdition  where  aedate=(select  max(aedate) from dtAndroidEdition where   APPLogo=? and AEIOSORANDROID=? ) ";
		if(name==null||name.equals("")||name.equals("null")||name.equals("undefined")||name.equals("0")){
			name="wfj";
		}
		Object obk = baseBeanService.getObjectBySqlAndParams(hql,
				new String[] { name ,"0"});
		Object[] obks = (Object[])obk;
		String banben = obks[0].toString();
		String content = obks[1]==null?"":obks[1].toString();
		String forceUpdate = (obks[2]==null||obks[2]=="")?"00":obks[2].toString();
		JSONObject temp = new JSONObject();
		temp.accumulate("return", banben);
		temp.accumulate("content", content);
		temp.accumulate("forceUpdate", forceUpdate);
		result = temp;
		return Action.SUCCESS;
	}


	/**
	 * 查询个人名片信息
	 * @param
	 *  @user
	 * @return name手机号,type会员类型,companyName公司名称,post职务,weixinSn微信号,userId用户id
	 */
	public String toPageForStaff(){
		String hql ="select new TEshopCusCom(s) from TEshopCusCom m,Staff s where m.staffid=s.staffID and m.account=?";

		TEshopCusCom entity= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{user});

		if(entity!=null){
			JSONObject ret=new JSONObject();
			JSONObject jobj=new JSONObject();
			jobj.accumulate("name", isNull(entity.getStaff().getStaffName()));
			jobj.accumulate("headImage", isNull(entity.getStaff().getHeadimage()));
			jobj.accumulate("weixinSn", isNull(entity.getStaff().getWeixin()));
			jobj.accumulate("phoneNo", isNull(entity.getStaff().getReference()));
			jobj.accumulate("companyName", isNull(entity.getStaff().getWhereCompany()));
			jobj.accumulate("post", isNull(entity.getStaff().getStaffPost()));
			jobj.accumulate("birthday", isNull(entity.getStaff().getBirthday()));
			jobj.accumulate("province", isNull(entity.getStaff().getProvinceAddress()));
			jobj.accumulate("detialAddr", isNull(entity.getStaff().getStaffAddress()));
			jobj.accumulate("sfzh", isNull(entity.getStaff().getStaffIdentityCard()));
			jobj.accumulate("referenceCode", isNull(entity.getStaff().getReferenceCode()));
			jobj.accumulate("referenceOrganization", isNull(entity.getStaff().getReferenceOrganization()));
			jobj.accumulate("industryType", isNull(entity.getStaff().getIndustryType()));

			ret.accumulate("cardlist", jobj);
			result=ret;
		}

		return Action.SUCCESS;
	}

	/**
	 *
	 * 完善 APP中联园区会员中心资料
	 * @param @userId用户id,phoneNo电话,type会员类型,weixinSn微信号,companyName单位名称,post职位
	 * @param @name名字,headImage头像,birthday生日,province省份,detialAddr详细地址,sfzh身份证号
	 * @return result0:成功
	 */
	public String savestaff() {
		JSONObject temp = new JSONObject();
		try {

			String phoneNo = request.getParameter("phoneNo");// 电话
			String weixinSn = request.getParameter("weixinSn");// 微信号
			String whereCompany = request.getParameter("companyName");//用户现实生活中的公司名称
			String staffPost= request.getParameter("post");//用户现实生活中的公司职位
			String staffName = request.getParameter("name");// 名字
			String birthday = request.getParameter("birthday");// 生日
			String province= request.getParameter("province");//省份
			String detialAddr=request.getParameter("detialAddr");//详细地址
			String sfzh=request.getParameter("sfzh");//身份证号
			String referenceCode=request.getParameter("referenceCode"); //QQ
			String referenceOrganization=request.getParameter("referenceOrganization"); //邮箱
			String industryType=request.getParameter("industryType"); //行业类别

			TEshopCusCom tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(
					"from TEshopCusCom d where d.account=? ",new Object[] {user});

			Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?",
					new Object[]{tcc.getStaffid()});

			staff.setStaffName(staffName);  //姓名
			staff.setUsedNmae(staffName);	//登录姓名
			staff.setBirthday(birthday);		//生日
			staff.setReference(phoneNo);		//电话
			staff.setWeixin(weixinSn);				//微信
			staff.setStaffPost(staffPost);		//职位
			staff.setReferenceOrganization(referenceOrganization);	//邮箱
			staff.setReferenceCode(referenceCode);		//QQ
			staff.setWhereCompany(whereCompany);  //所在公司
			staff.setStaffIdentityCard(sfzh);				//身份证号码
			staff.setIndustryType(industryType);		//行业
			staff.setProvinceAddress(province);		//省份
			staff.setStaffAddress(detialAddr);			//详细地址
			baseBeanService.update(staff);
			temp.accumulate("result", "0");

		} catch (Exception e) {
			temp.accumulate("result", "1");
			logger.error("操作异常", e);
		}
		result = temp;
		return Action.SUCCESS;
	}

	/**
	 *
	 * 上传图片后保存
	 */
	public void saveHeadImage(){
		String hql = "from Staff s where s.staffID in (select t.staffid from TEshopCusCom t where t.account=?)";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{user});
		if(staff!=null){
			staff.setHeadimage(imagePath);
			baseBeanService.update(staff);
		}

	}


	/*
	 * 获取个人名片中活动、论坛、文章信息
	 */
	public String toPageForActivitiesOrList(){
		String category=request.getParameter("category");
		String activitiesID=request.getParameter("activitiesID");
		TEshopCusCom tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(
				"from TEshopCusCom d where d.account=? ",new Object[] {user});
		if(tcc!=null){
			JSONObject ret=new JSONObject();
			JSONObject jobj=new JSONObject();
			if(!"".equals(activitiesID)&&activitiesID!=null){
				Activities activities=(Activities)baseBeanService.getBeanByHqlAndParams("from Activities" +
						" where staffId=? and activitiesID=?", new Object[]{tcc.getStaffid(),activitiesID});
				if(activities!=null){
					jobj.accumulate("title", isNull(activities.getTitle()));
					jobj.accumulate("describe", isNull(activities.getDescribe()));
					jobj.accumulate("picture", isNull(activities.getPicture()));
					jobj.accumulate("releaseTime", isNull(activities.getReleaseTime()==null?"":
							new SimpleDateFormat("yyyy年MM月dd日").format(activities.getReleaseTime())));
					jobj.accumulate("author", isNull(activities.getAuthor()));
					jobj.accumulate("shareLink", isNull(activities.getShareLink()));
					jobj.accumulate("category", isNull(activities.getCategory()));
				}
			}else{
				List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams("from Activities" +
						" where staffId=? and type=? and category=?", new Object[]{tcc.getStaffid(),"01",category});
				jobj.accumulate("list", isNull(list));
			}
			ret.accumulate("cardlist", jobj);
			result=ret;
		}
		return Action.SUCCESS;
	}

	/*
	 * 保存
	 */
	public String saveOrUpdateActivities(){
		JSONObject temp = new JSONObject();
		try {
			String activitiesID=request.getParameter("activitiesID"); 	//ID
			String title=request.getParameter("title");					//标题
			String describe=request.getParameter("describe");			//描述
			String releaseTime=request.getParameter("releaseTime");//时间
			String shareLink=request.getParameter("shareLink");		//分享链接
			String category=request.getParameter("category");		//类别

			String hql ="select new TEshopCusCom(s) from TEshopCusCom m,Staff s where m.staffid=s.staffID and m.account=?";

			TEshopCusCom tcc= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{user});
			Activities ac=(Activities)baseBeanService.getBeanByHqlAndParams("from Activities where activitiesID=?",
					new Object[]{activitiesID});

			if(ac==null){
				ac=new Activities();
				ac.setActivitiesID(serverService.getServerID("Activities"));
			}
			ac.setTitle(title);
			ac.setDescribe(describe);
			String str="";
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("\\");
			for(int i=0;i<pictureList.size();i++){
				int Pos=pictureList.get(i).getPath().lastIndexOf("\\");
				String route=fileService.savePhoto(path, pictureList.get(i).getPath().substring(Pos+1),
						pictureList.get(i), "personal/activities", tcc.getStaffid()+"/"+Utilities.getDateString(new Date(), "yyyy-MM-dd"));
				str+="".equals(str)?route:"$$$$"+route;
			}
			ac.setPicture(str);
			ac.setReleaseTime(releaseTime==null?new Date():new SimpleDateFormat("yyyy年MM月dd日").parse(releaseTime));
			ac.setType("01");
			ac.setStaffId(tcc.getStaff().getStaffID());
			ac.setAuthor(tcc.getStaff().getStaffName());
			ac.setShareLink(shareLink);
			ac.setCategory(category);

			baseBeanService.saveOrUpdate(ac);

			temp.accumulate("result", "0");
		} catch (Exception e) {
			temp.accumulate("result", "1");
			logger.error("操作异常", e);
		}
		result = temp;
		return Action.SUCCESS;
	}

	/*
	 * 用户总金币数量
	 */

	public String toGoldSum(){
		String sccId=request.getParameter("sccId");
		String hql = " from TEshopCusCom where sccId=?";
		TEshopCusCom tcc=(TEshopCusCom)
				baseBeanService.getBeanByHqlAndParams(hql,new Object[] {sccId});
		if(tcc!=null){
			JSONObject ret =new JSONObject();
			JSONObject jobj=new JSONObject();

			WfjJifen wfjJifen =(WfjJifen) baseBeanService.getBeanByHqlAndParams(" from WfjJifen where staffId=? and sccid=?", new Object[]{tcc.getStaffid(),sccId});

			if(wfjJifen!=null){
				jobj.accumulate("wfjJifenScore", isNull(wfjJifen.getWfjJifenScore()));
				jobj.accumulate("type", "0");
			}else{

				jobj.accumulate("type", "1");
			}

			ret.accumulate("cardlist", jobj);
			result=ret;
		}

		return Action.SUCCESS;
	}

	//黑名单
	@SuppressWarnings("rawtypes")
	public String getdj(){
		JSONObject jobj=new JSONObject();
		List ss=baseBeanService.getListBeanBySqlAndParams("select account from t_eshop_customer where status is not null", null);
		jobj.accumulate("hmd", ss);
		result=jobj;
		return Action.SUCCESS;
	}


	/*
     *红包：（赠送金币数量，发送人，接收人，留言）
     *
     * */
	@SuppressWarnings("unchecked")
	public synchronized String redPacketFunction() {
		JSONObject ret = new JSONObject();
		JSONObject jobj = new JSONObject();
		/*try {
			String sender = request.getParameter("sccIdS");// 发送人
			String recipient = request.getParameter("sccIdR");// 接收人
			String goldNum = request.getParameter("goldNum");// 金币数量
			String liuYan = request.getParameter("liuyan");// 留言
            int i=   telService.redPacket(sender,recipient,goldNum,liuYan); 20180223暂时注释掉这行代码
            jobj.accumulate("type", i);
		} catch (Exception e) {
			logger.error("操作异常", e);
			jobj.accumulate("type", "1");
		}*/
		jobj.accumulate("type", "11");
		ret.accumulate("cardlist", jobj);
		result = ret;
		return Action.SUCCESS;
	}






	/*
	 *个人名片风采添加
	 */
	public String addMienData(){
		JSONObject temp = new JSONObject();
		try {
			List<BaseBean> arrayList=new ArrayList<BaseBean>();
			String routes=request.getParameter("routes");			//多个路径
			String elaborate=request.getParameter("elaborate");	//详细说明
			if(elaborate==null||"".equals(elaborate))
				elaborate=" ";
			String hql ="select new TEshopCusCom(s) from TEshopCusCom m,Staff s where m.staffid=s.staffID and m.account=?";
			TEshopCusCom tcc= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{user});
			if(routes!=null){
				String[] str=routes.split(",");
				for(int i=0;i<str.length;i++){
					CardMaterial mate=new CardMaterial();
					mate.setMaterialID(serverService.getServerID("Material"));
					mate.setStaffID(tcc.getStaff().getStaffID());
					mate.setTitle("个人风采");
					mate.setElaborate(elaborate);
					mate.setRoute(str[i]);
					mate.setStatus("00");
					mate.setType("00");
					arrayList.add(mate);
					MienStyle mien=new MienStyle();
					mien.setMienStyleID(serverService.getServerID("MienStyle"));
					mien.setRelateID(tcc.getStaff().getStaffID());
					mien.setMaterialID(mate.getMaterialID());
					arrayList.add(mien);
				}
				baseBeanService.saveBeansListAndexecuteHqlsByParams(arrayList, null, null);
				temp.accumulate("result", "0");
			}else{
				temp.accumulate("result", "1");
			}
		} catch (Exception e) {
			temp.accumulate("result", "1");
			logger.error("操作异常", e);
		}
		result = temp;
		return Action.SUCCESS;
	}
	/*
	 *个人名片风采解析
	 */
	@SuppressWarnings("unchecked")
	public String getMienList(){
		JSONObject ret=new JSONObject();
		String hql ="select new TEshopCusCom(s) from TEshopCusCom m,Staff s where m.staffid=s.staffID and m.account=?";
		TEshopCusCom tcc= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{user});

		String sql2="select mate.route,mate.elaborate,mien.mienStyleID from dtMienStyle mien  left join dtMaterial mate" +
				" on mien.materialid=mate.materialid where mien.relateid=? and mate.status=? and type=?";
		List<Object> l=baseBeanService.getListBeanBySqlAndParams(sql2, new Object[]{tcc.getStaff().getStaffID(),"00","00"});
		if(l.size()>0){
			ret.accumulate("list",isNull(l));
		}
		result=ret;
		return Action.SUCCESS;
	}
	/*
	 *个人名片风采删除
	 */
	public String delMienData(){
		String mienStyleID=request.getParameter("mienStyleID");			//风采ID
		String hql ="select new TEshopCusCom(s) from TEshopCusCom m,Staff s where m.staffid=s.staffID and m.account=?";
		TEshopCusCom tcc= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{user});
		String mienHql="from MienStyle where mienStyleID=? and relateID=?";
		MienStyle ms=(MienStyle)baseBeanService.getBeanByHqlAndParams(mienHql, new Object[]{mienStyleID,tcc.getStaff().getStaffID()});
		String[] sqls={"delete dtMienStyle where mienStyleID=? and relateID=?","delete dtMaterial where materialID=? and StaffID=?"};
		List<Object[]> obj=new ArrayList<Object[]>();
		obj.add(new Object[]{mienStyleID,tcc.getStaff().getStaffID()});
		obj.add(new Object[]{ms.getMaterialID(),tcc.getStaff().getStaffID()});
		baseBeanService.executeSqlsByParmsList(null, sqls, obj);
		return Action.SUCCESS;
	}

	/**
	 * 增加银行卡信息
	 *
	 * @return
	 */

	public String seveyhk()
	{
		String ifz="0";
		String userid = request.getParameter("userid");//当前用户的账号
		String bankNum = request.getParameter("bankNum");//银行卡号
		String province = request.getParameter("province");//银行卡所在市省
		String city = request.getParameter("city");//银行卡所在市
		JSONObject temp = new JSONObject();
		TEshopCusCom  tcc=(TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom d where d.account=? ",new Object[]{userid});
		Staff  sf=(Staff) baseBeanService.getBeanByHqlAndParams("from Staff d where d.staffID=? ",new Object[]{	tcc.getStaffid()});
		sf.setBankNum(bankNum);
		sf.setProvince(province);
		sf.setCity(city);
		temp.accumulate("return", ifz);
		result = temp.toString();
		return Action.SUCCESS;
	}



	/**
	 * 微分金注册
	 * @param
	 * @user用户名
	 * @password密码
	 * @pid推荐人账户
	 * @staffname 本人姓名
	 * @return 0:成功 1：失败(异常) 2:用户名密码为空 6.用户名存在，7：没有未使用充值卡
	 */
	public String wfjRegister() {
		List<BaseBean> beans = new ArrayList<BaseBean>();
		TEshopCustomer customer = new TEshopCustomer();
		TEshopCusCom tc = new TEshopCusCom();
		String tcsccid = serverService.getServerID("sccid");
		Map<String, String> map = new HashMap<String, String>();
		Staff staff = new Staff();
		String staffid = serverService.getServerID("cstaff");
		// 推荐人账号
		String pid = request.getParameter("pid");  //推荐人手机号
		String staffname = request.getParameter("staffname");
		try {
			if (user == null || user.trim().equals("") || password == null
					|| password.trim().equals("")) {
				map.put("returnCode", "2");// 用户名密码为空
				result = JSONObject.fromObject(map);
				return "success";
			} else {
				String hql = "from TEshopCustomer where  account = ? AND logOff=0";
				BaseBean bb = baseBeanService.getBeanByHqlAndParams(hql,
						new Object[] { user.trim() });
				if (bb != null) {
					map.put("returnCode", "6");// 用户名存在
					result = JSONObject.fromObject(map);
					return "success";
				}
			}
			String phql = "select count(*) from Staff ";
			int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
			staff.setStaffCode("NO" + pcount);
			staff.setRecordCode("NO" + pcount);
			staff.setVerifyTime(new Date());
			staff.setStaffID(staffid);
			staff.setReference(user);
			staff.setSource("app");
			staff.setGroupCompanySn("groupcompany20120523G3VR9PXHZD0000000021");
			staff.setStaffStatus("00");
			staff.setStatus("01");
			staff.setStaffName(staffname);
			staff.setVipno("1");
			staff.setSccid(tcsccid);
			beans.add(staff);
			customer.setStaffid(staffid);
			customer.setCustid(serverService.getServerID("TEshopCustomer"));
			customer.setAccount(user);
			customer.setPassword(password);
			customer.setAppOpenId(appOpenId);
			customer.setQqId(qqId);
			customer.setWeiboId(weiboId);
			customer.setAppOpenId(eqId);
			customer.setQqId(eOpenId);
			customer.setWeiboId(eBlogId);

			beans.add(customer);
			TEshopCusCom pcustom = new TEshopCusCom();
			if(pid !=null && !pid.equals("")){
				//根据推荐人手机号查处sccid,如果为空，给默认的sccid
				String hqlcust = "from TEshopCusCom  m where m.account = ? order by m.cusType desc";
				List<BaseBean> ab = baseBeanService.getListBeanByHqlAndParams(hqlcust,
						new Object[] { pid });
				pcustom = (TEshopCusCom) ab.get(0); //推荐人
				if(pcustom.getCusType().equals("0")){
					Object obj = baseBeanService
							.getObjectBySqlAndParams("SELECT T.SCCID,T.ACCOUNT" +
									" FROM T_ESHOP_CUSCOM T where t.custype='1' START WITH T.sccId=?" +
									" CONNECT BY PRIOR T.sccId=T.superSccId", new Object[] { pcustom.getSccId() });
					Object[] objs = (Object[])obj;
					tc.setSupperSccId(objs[0].toString());//推荐人上级
					tc.setSuperioragent(objs[1].toString());
				}else if ("6".equals(pcustom.getCusType()) || "7".equals(pcustom.getCusType())){
					//20180404修改为如果推荐人的级别为6或7就去找他的上级作为推荐人
					pcustom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=? and logOff=0", new Object[]{pcustom.getSupperSccId()});
					tc.setSuperioragent(pcustom.getAccount());
					tc.setSupperSccId(pcustom.getSccId());
				}else{
					tc.setSuperioragent(pcustom.getAccount());
					tc.setSupperSccId(pcustom.getSccId());
				}
			}else{
				//没有推荐人的默认账户
				pcustom = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom  m where m.sccId = ? and logOff=0",
						new Object[]{"TEshopCusCom20161010W9FXK9NJ450000011682"});
				tc.setSuperioragent(pcustom.getAccount());
				tc.setSupperSccId(pcustom.getSccId());
			}
			// 用户权限表
			tc.setSccId(tcsccid);
			tc.setStaffid(staffid);
			tc.setState("1");
			tc.setAccount(user);
			tc.setCusType("7");
			tc.setTeccDate(new Date());
			tc.setAcquiesce("01");
			tc.setPpid(pcustom.getPpid());//推荐人的ppid
			tc.setAppOpenId(appOpenId);
			tc.setQqId(qqId);
			tc.setWeiboId(weiboId);

			tc.setAppOpenId(eqId);
			tc.setQqId(eOpenId);
			tc.setWeiboId(eBlogId);
			beans.add(tc);
			// 粉丝相关
			JoinFans jf1 = new JoinFans();
			jf1.setJfID(serverService.getServerID("jfID"));
			jf1.setSource("app");
			jf1.setState("00");
			jf1.setFaccount(tc.getSuperioragent());
			jf1.setZaccount(tc.getAccount());
			jf1.setFsccId(tc.getSupperSccId());//xxxx
			jf1.setZsccId(tc.getSccId());
			jf1.setStaffid(pcustom.getStaffid());  //faccount对应的staffid
			JoinFans jf2 = new JoinFans();
			jf2.setJfID(serverService.getServerID("jfID"));
			jf2.setSource("app");
			jf2.setState("00");
			jf2.setFaccount(tc.getAccount());
			jf2.setZaccount(tc.getSuperioragent());
			jf2.setFsccId(tc.getSccId());
			jf2.setZsccId(tc.getSupperSccId());
			jf2.setStaffid(tc.getStaffid());//faccount对应的staffid
			beans.add(jf1);
			beans.add(jf2);
			MarKeting mk = new MarKeting();
			mk.setMkID(serverService.getServerID("MarKeting"));
			mk.setSource("app");
			mk.setUserID(tc.getAccount());//被邀请人账号
			mk.setUserSccId(tc.getSccId());//被邀请人sccId
			mk.setMkSccId(pcustom.getSccId());//邀请人sccId
			mk.setMkuserID(pcustom.getAccount());//邀请人账号
			mk.setMkdate(new Date());
			beans.add(mk);
			if(!pcustom.getSccId().equals("TEshopCusCom20161010W9FXK9NJ450000011682")){
				StringBuffer str=new StringBuffer();
				str.append(" select d.dbid,d.dbcarid from dt_deviceBind d, dt_devicebind_staff ds ");
				str.append(" where d.dbid = ds.dbsdbid and d.dbstatus = '1' ");
				str.append(" and ds.dbsstatus = '1' and ds.dbssccid = ? ");
				List<BaseBean> dlist=baseBeanService.getListBeanBySqlAndParams(str.toString(),new Object[]{pcustom.getSccId()});
				if(dlist != null && dlist.size()>0) {
					Object[] obj = (Object[]) (Object) dlist.get(0);
					DeviceBindStaff dstaff = new DeviceBindStaff();
					dstaff.setDbsKey(serverService.getServerID(""));
					dstaff.setDbsId(serverService.getServerID("DeviceBindStaff"));
					dstaff.setDbsDbId(obj[0].toString());
					dstaff.setDbsStaffId(tc.getStaffid());
					dstaff.setDbsSccId(tc.getSccId());
					dstaff.setDbsDate(new Date());
					dstaff.setDbsStatus("1");
					beans.add(dstaff);
				}
			}
			if("TEshopCusCom20161010W9FXK9NJ450000011682".equals(tc.getSupperSccId())){
				//没有推荐人时保存抢单需求
				DemandDetail demandDetail = new DemandDetail();
				demandDetail.setDdid(serverService.getServerID("ddetail"));
				demandDetail.setDdsccid(tcsccid);
				demandDetail.setDdtitle("新注册用户");
				demandDetail.setDdcontactname(staffname);
				demandDetail.setDdcontactphone(user);
				//demandDetail.setDdscodeid("scode20170714cnjcrn5jm20000000067");
				demandDetail.setDdscodeid("bType20250830AKEYDEP5ZJ0000001270");
				demandDetail.setDdworktype("服务平台");
				demandDetail.setDdstatus("0");
				demandDetail.setDdBool('Y');
				demandDetail.setDdadddate(new Date());
				demandDetail.setDscount( 0 );
				beans.add(demandDetail);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,null);
			//pushMwssage2();
			map.put("returnCode", "0");
			if("TEshopCusCom20161010W9FXK9NJ450000011682".equals(tc.getSupperSccId())){
				//没有推荐人时给级别大于1小于6的用户推送抢单服务
				String sqlslist=" select t.account from t_eshop_cuscom t  where t.custype<='5' and t.custype>'1' and t.sccid != ? ";
				List<String> list=new ArrayList<String>();
				SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
				String date = sdf.format(new Date());
				list=baseBeanService.getListBeanBySqlAndParams(sqlslist, new Object[]{"TEshopCusCom20161010W9FXK9NJ450000011682"});
				JushMain.sendjiguangMessage("有新用户注册，点击立即抢单，手慢无，快来抢！"+date, "抢下级", "有新的客户注册哦！", "lowerlevel", list);
			}
		} catch (Exception e) {
			logger.error("操作异常", e);
			map.put("returnCode", "1");
		}
		result = JSONObject.fromObject(map);
		return "success";
	}

	/**
	 *
	 * 注册成功推送短信
	 *
	 */
	private void pushMwssage2(){
		try {
			msage.setMobiles(user);
			msage.setMessage("恭喜您以成功注册微分金，欢迎您使用！！！");
			msage.sendMsg("【微分金平台】");
		} catch (IOException e) {
			logger.error("操作异常", e);
		}
	}

	/**
	 * 修改SSTAFF信息 staffID 根据传过来的staffID来进行修改 staffName 传过来的参数然后 根据staffid修改
	 * staffDesc 传过来的说明 修改
	 *
	 * @return
	 */
	public String updatestaff() {

		String staffID = request.getParameter("staffID");
		String staffName = request.getParameter("staffName");
		String staffDesc = request.getParameter("staffDesc");
		String photo = request.getParameter("photo");
		String staffsm = request.getParameter("staffsm");
		String staffupf = request.getParameter("staffupf");
		String hql = "from Staff where staffID=?";
		JSONObject temp = new JSONObject();
		if (yanzheng(new String[] { staffID })) {
			Staff sf = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
					new String[] { staffID });
			if (sf != null) {
				if (yanzheng(new String[] { photo }))
					sf.setPhoto(photo);
				if (yanzheng(new String[] { staffName }))
					sf.setStaffName(staffName);
				if (yanzheng(new String[] { staffDesc }))
					sf.setStaffDesc(staffDesc);
				if (yanzheng(new String[] { staffsm }))
					sf.setStaffsm(staffsm);
				if (yanzheng(new String[] { staffupf }))
					sf.setStaffupf(staffupf);
				List<BaseBean> base = new ArrayList<BaseBean>();
				base.add(sf);
				baseBeanService.saveBeansListAndexecuteHqlsByParams(base, null,
						new Object[] { sf.getStaffKey() });

				temp.accumulate("fan", "0");

			}
		} else {

			temp.accumulate("fan", "1");

		}
		result = temp;

		return "success";

	}

	/**
	 *
	 * 验证方法 掉方法 然后 (new String[]{参数,参数,参数以此类推})
	 *
	 * @param string
	 * @return
	 */
	private boolean yanzheng(String[] string) {
		boolean b = true;
		for (String s : string) {
			if (s == null || "".equals(s)) {
				b = false;
				break;
			}
		}
		return b;
	}

	//微分金支付密码（保存支付密码）
	public String wfjPaymentCode(){
		String sender = request.getParameter("sccIdS");
		String paymentCode=request.getParameter("paymentCode");
		JSONObject temp = new JSONObject();
		String hql=" from TEshopCusCom where sccId=?";
		TEshopCusCom tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{sender});
		if(tcc!=null){

			String mhql="from TEshopCustomer where account=? AND logOff=0";
			TEshopCustomer customer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams(mhql,new Object[] { tcc.getAccount() });

			if(customer!=null){
				customer.setPaymentCode(paymentCode);
				try {
					baseBeanService.update(customer);
					temp.accumulate("result", 0);
				} catch (Exception e) {
					temp.accumulate("result", 1);
				}
			}else{
				temp.accumulate("result", 1);
			}
		}else{
			temp.accumulate("result", 1);
		}
		result=temp;
		return "success";
	}

	public String wfjBinding(){
		Map<String, Object> map = new HashMap<String, Object>();
		String hql ="from TEshopCustomer where account = ? and logOff=0 and password = ?";
		String hqltc = "from TEshopCusCom where account = ? and logOff=0 ";
		TEshopCustomer customer=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{user,password});
		String s="0";
		if(customer!=null){
			s="3";//未绑定
			List<BaseBean> baseBeanList=new ArrayList<BaseBean>();
			if("eroad".equals(appType)){
				if( customer.geteOpenId()==null||"".equals(customer.geteOpenId())){
					customer.seteOpenId(eOpenId);
					s="2";//绑定成功一项
				}
				if(customer.getEqId()==null||"".equals(customer.getEqId())){
					customer.setEqId(eqId);
					s="2";
				}
				if(customer.geteBlogId()==null||"".equals(customer.geteBlogId())){
					customer.seteBlogId(eBlogId);
					s="2";
				}
				TEshopCusCom tcc= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hqltc, new Object[]{user});
				if(tcc!=null){
					if(tcc.geteOpenId()==null||"".equals(tcc.geteOpenId())){
						tcc.setAppOpenId(appOpenId);
						s="1";
					}
					if(tcc.getEqId()==null||"".equals(tcc.getEqId())){
						tcc.setEqId(eqId);
						s="1";
					}
					if(tcc.geteBlogId()==null||"".equals(tcc.geteBlogId())){
						tcc.seteBlogId(eBlogId);
						s="1";
					}
					baseBeanList.add(customer);
					baseBeanList.add(tcc);
					baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,null,null);

				}
			}else {
				if( customer.getAppOpenId()==null||"".equals(customer.getAppOpenId())){
					customer.setAppOpenId(appOpenId);
					s="2";//绑定成功一项
				}
				if(customer.getQqId()==null||"".equals(customer.getQqId())){
					customer.setQqId(qqId);
					s="2";
				}
				if(customer.getWeiboId()==null||"".equals(customer.getWeiboId())){
					customer.setWeiboId(weiboId);
					s="2";
				}
				TEshopCusCom tcc= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hqltc, new Object[]{user});
				if(tcc!=null){
					if(tcc.getAppOpenId()==null||"".equals(tcc.getAppOpenId())){
						tcc.setAppOpenId(appOpenId);
						s="1";
					}
					if(tcc.getQqId()==null||"".equals(tcc.getQqId())){
						tcc.setQqId(qqId);
						s="1";
					}
					if(tcc.getWeiboId()==null||"".equals(tcc.getWeiboId())){
						tcc.setWeiboId(weiboId);
						s="1";
					}
					baseBeanList.add(customer);
					baseBeanList.add(tcc);
					baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,null,null);

				}
			}
		}
		map.put("s",s);
		result = JSONObject.fromObject(map);
		return "success";
	}

	/**
	 * 微分金登录
	 * @param
	 * @user账户
	 * password密码
	 * @return 1:帐号不存在 2:密码为空 0:正常登录
	 */
	public String wfjLogin() {
		String hql = "";
		String parameter = "";
		boolean third = true;
		String hqltc = "";
		TEshopCusCom tcc = null;
//			String hqltype = "";

		if(qqId!=null&&!qqId.equals("")){ //QQ登陆
			hql ="from TEshopCustomer where qqId = ?";
			parameter = qqId;
			hqltc = "from TEshopCusCom where acquiesce = ? and qqId=?";
//				hqltype = "from TEshopCusCom where qqId = ? order by cusType";

		}else if(weiboId!=null&&!weiboId.equals("")){//微博登陆
			hql ="from TEshopCustomer where weiboId = ?";
			parameter = weiboId;
			hqltc = "from TEshopCusCom where acquiesce = ? and weiboId=?";
//				hqltype = "from TEshopCusCom where weiboId = ? order by cusType";
		}else if(appOpenId!=null&&!appOpenId.equals("")){//app微信登陆
			hql ="from TEshopCustomer where appOpenId = ? and logOff=0";
			parameter = appOpenId;
			hqltc = "from TEshopCusCom where acquiesce = ? and appOpenId=? and logOff=0";
//				hqltype = "from TEshopCusCom where appOpenId = ? order by cusType";
		}else{
			hql="from TEshopCustomer where account=? AND logOff=0";
			parameter = user;
			third = false;
		}
		TEshopCustomer customer=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{parameter});
		if(third){
			tcc= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hqltc, new Object[]{"01", parameter});

		}else{
			tcc=(TEshopCusCom)baseBeanService
					.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0 and acquiesce='01'",
							new Object[] { user });
		}

		/*TEshopCusCom tcc=(TEshopCusCom)baseBeanService
				.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0 and acquiesce='01'",
						new Object[] { user });
		TEshopCustomer customer = (TEshopCustomer) baseBeanService
				.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0",//
						new Object[] { user });*/
		boolean isCode = verifyCodeManager.verifyCode(user, code);
		Map<String, Object> map = new HashMap<String, Object>();
		if (customer == null || "".equals(customer)) {
			map.put("returnCode", "1");// 帐号不存在
		} else if (!third&&(password == null || !password.equals(customer.getPassword()))) {
			if (!isCode)
			{
				map.put("returnCode", "2");// 密码错误
			}else {
				map.put("returnCode", "0");
				if(tcc==null){
					List<BaseBean> list = baseBeanService.
							getListBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0 order  by cusType",
									new Object[]{user});
					tcc=(TEshopCusCom)list.get(0);
					tcc.setAcquiesce("01");
					baseBeanService.update(tcc);
				}

				map.put("returnCode", "0");// 正常登录
				map.put("cusType", isNull(tcc.getCusType()));
				map.put("pid", isNull(customer.getCustid()));
				map.put("state", isNull(tcc.getState()));//默认为1   1个人  2 为公司
				map.put("companyid", isNull(tcc.getCompanyId()));
				map.put("staffid",
						customer.getStaffid() == null ? "" : customer.getStaffid());
				map.put("sccId", isNull(tcc.getSccId()));
				if(third){
					map.put("account",customer.getAccount());
					map.put("password",customer.getPassword());
				}

				//增加往来单位id查询ljc
				if (tcc.getCompanyId() != null && tcc.getCompanyId().length() > 0 )
				{
					CcomCom ccomCom = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where comanyId = ?",new Object[]{ tcc.getCompanyId()});
					if (ccomCom !=null)
					{
						map.put("ccompanyid",isNull(ccomCom.getCcompanyId()));
					}
				}


				if(customer.getPaymentCode()==null){
					map.put("paymentCode", "");
				}else{
					map.put("paymentCode", customer.getPaymentCode());
				}

				ProductPackaging pp = (ProductPackaging)baseBeanService.getBeanByHqlAndParams("from ProductPackaging where model = ? and type = ?", new Object[]{tcc.getCusType(),"会员类型级别"});
				map.put("cusTypeName", isNull(pp.getGoodsName()));//会员级别名称

				String hqlstaff="from Staff where staffID = ?";
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff,new Object[]{customer.getStaffid()});
				if(customer.getPaymentCode()!=null){
					map.put("paymentCode", isNull(customer.getPaymentCode()));
				}else{
					map.put("paymentCode", isNull(""));
				}

				if(staff!=null){
					map.put("companyName", isNull(staff.getWhereCompany()));
					map.put("userName", isNull(staff.getStaffName()));
					map.put("headImage", isNull(staff.getHeadimage()));
				}

				// 根据用户id查询激活后的会员卡。
				if (staff.getVipno()!=null&&staff.getVipno().equals("0")) {// 会员卡卡密查询的是初始设置的。
					map.put("huiyuan", "会员已激活");
				}else{
					map.put("huiyuan", "会员未激活");
				}
			}
		} else if(third||(!third&&user.equals(customer.getAccount())
				&&password.equals(customer.getPassword()))){
			if(tcc==null){
				List<BaseBean> list = baseBeanService.
						getListBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0 order  by cusType",
								new Object[]{user});
				tcc=(TEshopCusCom)list.get(0);
				tcc.setAcquiesce("01");
				baseBeanService.update(tcc);
			}

			map.put("returnCode", "0");// 正常登录
			map.put("cusType", isNull(tcc.getCusType()));
			map.put("pid", isNull(customer.getCustid()));
			map.put("state", isNull(tcc.getState()));//默认为1   1个人  2 为公司
			map.put("companyid", isNull(tcc.getCompanyId()));
			map.put("staffid",
					customer.getStaffid() == null ? "" : customer.getStaffid());
			map.put("sccId", isNull(tcc.getSccId()));
			if(third){
				map.put("account",customer.getAccount());
				map.put("password",customer.getPassword());
			}


			//增加往来单位id查询ljc
			if (tcc.getCompanyId() != null && tcc.getCompanyId().length() > 0 )
			{
				CcomCom ccomCom = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where comanyId = ?",new Object[]{ tcc.getCompanyId()});
				if (ccomCom !=null)
				{
					map.put("ccompanyid",isNull(ccomCom.getCcompanyId()));
				}
			}


			if(customer.getPaymentCode()==null){
				map.put("paymentCode", "");
			}else{
				map.put("paymentCode", customer.getPaymentCode());
			}

			ProductPackaging pp = (ProductPackaging)baseBeanService.getBeanByHqlAndParams("from ProductPackaging where model = ? and type = ?", new Object[]{tcc.getCusType(),"会员类型级别"});
			map.put("cusTypeName", isNull(pp.getGoodsName()));//会员级别名称

			String hqlstaff="from Staff where staffID = ?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff,new Object[]{customer.getStaffid()});
			if(customer.getPaymentCode()!=null){
				map.put("paymentCode", isNull(customer.getPaymentCode()));
			}else{
				map.put("paymentCode", isNull(""));
			}

			if(staff!=null){
				map.put("companyName", isNull(staff.getWhereCompany()));
				map.put("userName", isNull(staff.getStaffName()));
				map.put("headImage", isNull(staff.getHeadimage()));
			}

			// 根据用户id查询激活后的会员卡。
			if (staff.getVipno()!=null&&staff.getVipno().equals("0")) {// 会员卡卡密查询的是初始设置的。
				map.put("huiyuan", "会员已激活");
			}else{
				map.put("huiyuan", "会员未激活");
			}
		}
		HttpSession session=ServletActionContext.getRequest().getSession();
		SessionWrap wrap=SessionWrap.getInstance();
		wrap.setObject(session,SessionWrap.KEY_SHOPCUSCOM, tcc);
		wrap.setObject(session,SessionWrap.KEY_CUSTOMER,customer);
		result = JSONObject.fromObject(map);

		return "success";
	}

	/** 添加好友 */
	public String addFriendBy() {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String userId = request.getParameter("userId");
			String friendId = request.getParameter("friendId");
			PhoneFriend pf = new PhoneFriend();
			pf.setPfId(serverService.getServerID("phoneFriend"));
			pf.setPfUserId(userId);
			pf.setPfFriendId(friendId);
			pf.setPfState("0");
			baseBeanService.save(pf);
			String sql = "select t.staffname,t.reference,vi.pf_voip_user,t.staffid,t.usednmae,t.staffdesc,t.photo,t.staffupf,t.staffsm  from dt_hr_staff t  join dt_voip_info vi on  vi.user_id=t.staffid  where t.staffid=?";
			Object friend = baseBeanService.getObjectBySqlAndParams(sql,
					new Object[] { friendId });
			if (friend != null) {
				Object[] f = (Object[]) friend;
				map.put("staffName", f[0] == null ? "" : f[0].toString());
				map.put("phoneNo", f[1] == null ? "" : f[1].toString());
				map.put("voipAccount", f[2] == null ? "" : f[2].toString());
				map.put("staffId", f[3] == null ? "" : f[3].toString());
				map.put("usedNmae", f[4] == null ? "" : f[4].toString());
				map.put("staffDesc", f[5] == null ? "" : f[5].toString());
				map.put("photo", f[6] == null ? "" : f[6].toString());
				map.put("staffupf", f[7] == null ? "" : f[7].toString());
				map.put("staffsm", f[8] == null ? "" : f[8].toString());
			}
			map.put("returnCode", "0");

		} catch (Exception e) {
			map.put("returnCode", "1");
		}
		result = JSONObject.fromObject(map);
		return "success";
	}

	/** 查询所有用户（手机号存在，并且注册过wfjRegister。存在第三方通讯帐号的人员信息） */
	@SuppressWarnings("unchecked")
	public String findFriend() {
		String phoneNo = request.getParameter("phoneNo");
		String userName = request.getParameter("userName");
		List<Object> parm = new ArrayList<Object>();
		String sql = "select t.staffname,t.reference,vi.pf_voip_user,t.staffid,t.usednmae,t.staffdesc,t.photo,t.staffupf,t.staffsm from dt_hr_staff t  join dt_voip_info vi on  vi.user_id=t.staffid  where (t.reference like ? or t.reference like ?) and rownum <16";
		parm.add("0%");
		parm.add("1%");
		if (phoneNo != null && phoneNo.trim().length() > 1) {
			sql += " and t.reference like ?";
			parm.add(phoneNo.trim() + "%");
		}
		if (userName != null && !userName.trim().equals("")) {
			sql += " and t.staffname like ?";
			parm.add(userName.trim() + "%");
		}
		List<Object> friends = baseBeanService.getListBeanBySqlAndParams(sql,
				parm.toArray());
		JSONObject jo = new JSONObject();
		List<Object> list = new ArrayList<Object>();
		for (Object obj : friends) {
			Object[] arr = (Object[]) obj;
			JSONObject temp = new JSONObject();
			temp.accumulate("staffName", arr[0] == null ? "" : arr[0]);
			temp.accumulate("phoneNo", arr[1] == null ? "" : arr[1]);
			temp.accumulate("voipAccount", arr[2] == null ? "" : arr[2]);
			temp.accumulate("staffId", arr[3] == null ? "" : arr[3]);
			temp.accumulate("usedNmae", arr[4] == null ? "" : arr[4]);
			temp.accumulate("staffDesc", arr[5] == null ? "" : arr[5]);
			temp.accumulate("photo", arr[6] == null ? "" : arr[6]);
			temp.accumulate("staffupf", arr[7] == null ? "" : arr[7]);
			temp.accumulate("staffsm", arr[8] == null ? "" : arr[8]);
			list.add(temp);
		}
		jo.accumulate("userInfo", list);
		result = jo;
		return "success";
	}

	/** 查询自己的好友 */
	@SuppressWarnings("unchecked")
	public String findOwnerFriend() {
		String staffId = request.getParameter("staffId");
		String sql = "select hs.staffid,hs.staffname,hs.reference,vi.pf_voip_user,"
				+ "hs.usednmae,hs.staffdesc,hs.photo,hs.staffupf,hs.staffsm from dt_phone_friend t"
				+ " join dt_voip_info vi on vi.user_id=t.pf_friend_id join dt_hr_staff hs on hs.staffid=t.pf_friend_id"
				+ "  where t.pf_user_id = ?";
		List<Object> friends = baseBeanService.getListBeanBySqlAndParams(sql,
				new Object[] { staffId });
		JSONObject ret = new JSONObject();
		List<Object> list = new ArrayList<Object>();
		for (Object obj : friends) {
			Object[] arr = (Object[]) obj;
			JSONObject jo = new JSONObject();
			jo.accumulate("staffId", arr[0] == null ? "" : arr[0]);
			jo.accumulate("staffName", arr[1] == null ? "" : arr[1]);
			jo.accumulate("phoneNo", arr[2] == null ? "" : arr[2]);
			jo.accumulate("voipAccount", arr[3] == null ? "" : arr[3]);
			jo.accumulate("usedNmae", arr[4] == null ? "" : arr[4]);
			jo.accumulate("staffDesc", arr[5] == null ? "" : arr[5]);
			jo.accumulate("photo", arr[6] == null ? "" : arr[6]);
			jo.accumulate("staffupf", arr[7] == null ? "" : arr[7]);
			jo.accumulate("staffsm", arr[8] == null ? "" : arr[8]);
			list.add(jo);
		}
		ret.accumulate("userInfo", list);
		result = ret;
		return Action.SUCCESS;
	}

	/**
	 * WK 标记一个手机号已注册（接口1） 参数 一个手机号 abphone 修改这个手机号的状态
	 */
	public String updateab() {
		String abphone = request.getParameter("abphone");
		String hql = "from AddressBook where abphone=? ";
		List<BaseBean> ab1 = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { abphone });
		JSONObject ret = new JSONObject();
		if (ab1 != null) {
			String hql1 = "UPDATE Person SET  abphonename=0 where abphone=?";
			try {
				baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
						new String[] { hql1 }, new String[] { abphone });
				ret.accumulate("return", "0");
			} catch (Exception e) {
				ret.accumulate("return", "1");
				ret.accumulate("why", "报错");

			}
		} else {
			ret.accumulate("return", "1");
			ret.accumulate("why", "未找到这个用户");
		}
		result = ret;
		return Action.SUCCESS;
	}

	/**
	 * 验证手机号是否被注册
	 *
	 */
	public String isExistab() {
		String abphone = request.getParameter("abphone");
		String hql = "from TEshopCusCom where  account = ? order by cusType desc";
		List<BaseBean> ab = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { abphone });

		JSONObject ret = new JSONObject();
		if (ab.size()>0&&ab!=null) {
			TEshopCusCom tec = (TEshopCusCom) ab.get(0);
			String custype = tec.getCusType();
			if (custype.equals("0.5") || custype.equals("1")){
				ret.accumulate("return", "2");//国税地税不能直接作为上级
			} else if (custype.equals("6") || custype.equals("7")){
				ret.accumulate("return", "3");//推荐人会员级别不够
			} else if(custype.equals("0")){
				Object obj = baseBeanService
						.getObjectBySqlAndParams("SELECT T.SCCID,T.ACCOUNT" +
								" FROM T_ESHOP_CUSCOM T where t.custype='1' START WITH T.account=?" +
								" CONNECT BY PRIOR T.account=T.SUPERIORAGENT", new Object[] { abphone });
				if(obj==null){
					ret.accumulate("return", "4");//没有国税地税
				}else{
					ret.accumulate("return", "1");
					ret.accumulate("why", "用户已存在");
				}
			} else{
				ret.accumulate("return", "1");
				ret.accumulate("why", "用户已存在");
			}
		} else {
			ret.accumulate("return", "0");//推荐人不存在
		}
		result = ret;
		return Action.SUCCESS;
	}

	/**
	 * 查询用户通讯记录 参数 一个电话PHONE
	 */
	public String getlisab() {
		String abuserphone = request.getParameter("abuserphone");
		String hql = "from AddressBook where abuserphone=? ";

		JSONObject ret = new JSONObject();
		List<Object> lis1 = new ArrayList<Object>();
		if (yanzheng(new String[] { abuserphone })) {
			List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(
					hql, new Object[] { abuserphone });
			for (BaseBean bb : list) {
				AddressBook ab = (AddressBook) bb;
				if (ab.getAbGrouping().equals("0")) {
					JSONObject ret1 = new JSONObject();
					ret1.accumulate("abid", isNull(ab.getAbid()));
					ret1.accumulate("abphonename", isNull(ab.getAbphonename()));
					ret1.accumulate("abdata", isNull(ab.getAbdata()).toString());
					for (BaseBean bb1 : list) {
						AddressBook ab1 = (AddressBook) bb1;
						if (ab1.getAbABid() != null&& ab1.getAbABid().equals(ab.getAbid())) {
							JSONObject ret11 = new JSONObject();
							ret11.accumulate("abid",  isNull(ab1.getAbid()));
							ret11.accumulate("abphone", isNull(ab1.getAbphone()));
							ret11.accumulate("abphonename", isNull(ab1.getAbphonename()));
							ret11.accumulate("abWhetherwfj", isNull(ab1.getAbWhetherwfj()));
							ret11.accumulate("abuserphone", isNull(ab1.getAbuserphone()));
							ret11.accumulate("abdata",isNull(ab1.getAbdata()));
							ret1.accumulate("list", ret11);
						}

					}

					lis1.add(ret1);
				}
			}
			ret.accumulate("list", lis1);
			ret.accumulate("return", "0");
		} else {
			ret.accumulate("voip", "参数为空");
			ret.accumulate("return", "1");
		}
		result = ret;
		return Action.SUCCESS;
	}

	private Object isNull(Object tep){
		if(tep==null||"null".equals(tep)){
			return "";
		}else{
			return tep;
		}
	}
	/**
	 * 增加通讯录别名 加手机号 参数 id 参数 被记录人的名字 要修改什么名字
	 */
	public String sevseabname() {
		String ABid = request.getParameter("ABid");
		String abphonename = request.getParameter("abphonename");
		JSONObject ret = new JSONObject();

		String hql = "from AddressBook where  abid=? ";

		if (yanzheng(new String[] { ABid, abphonename, })) {
			AddressBook ab = (AddressBook) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { ABid });
			if (ab != null) {
				ab.setAbphonename(abphonename);
				baseBeanService.saveOrUpdate(ab);
				ret.accumulate("return", "0");
			} else {
				ret.accumulate("return", "1");
				ret.accumulate("where", "未找到这条记录");
			}
		} else {
			ret.accumulate("return", "1");
			ret.accumulate("where", "参数值有空值");
		}
		result = ret;
		return Action.SUCCESS;
	}

	/**
	 * 上传通讯录电话 等信息 wk
	 *
	 */
	public String sevesab() {
		String abphone = request.getParameter("abphone");
		String abABid = request.getParameter("abABid");
		String abphonename = request.getParameter("abphonename");
		String abWhetherwfj = request.getParameter("abWhetherwfj");
		String abGrouping = request.getParameter("abGrouping");
		String abuserphone = request.getParameter("abuserphone");
		String hql = "from AddressBook where  abphone=?";
		AddressBook ab = (AddressBook) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { abphone });
		JSONObject ret = new JSONObject();
		if (ab == null) {
			AddressBook ab1 = new AddressBook();
			ab1.setAbid(serverService.getServerID("AddressBook"));
			// 判断他传进的是 分组还是电话0 分组 1电话
			if (abGrouping != null && abGrouping.equals("1")) {
				// 判断 他增加的电话 要是没上一级 就分到 未分组里面
				if (abABid != null)
					ab1.setAbABid(abABid);
				else
					ab1.setAbABid("");
				ab1.setAbphone(abphone);
				ab1.setAbWhetherwfj(abWhetherwfj);
			}
			ab1.setAbphonename(abphonename);
			ab1.setAbuserphone(abuserphone);
			ab1.setAbGrouping(abGrouping);
			ab1.setAbdata(new Date());
			try {
				baseBeanService.save(ab1);
				ret.accumulate("abkey",isNull(ab1.getAbkey()));
				ret.accumulate("abid",isNull(ab1.getAbid()));
				ret.accumulate("abABid",isNull(ab1.getAbABid()));
				ret.accumulate("abphone",isNull(ab1.getAbphone()));
				ret.accumulate("abphonename", isNull(ab1.getAbphonename()));
				ret.accumulate("abuserphone", isNull(ab1.getAbuserphone()));
				ret.accumulate("abdata", ab1.getAbdata().toString());
				ret.accumulate("return", "0");
			} catch (Exception e) {
				ret.accumulate("return", "1");
			}
		} else {
			ret.accumulate("return", "1");
			ret.accumulate("why", "用户已存在");
		}
		result = ret;
		return Action.SUCCESS;
	}

	/**
	 * 邀请微分金APP用户 MKPHONE 邀请人电话 指销售人员 Mkuserphjone 被邀请的人 指用户
	 *
	 * @return
	 */
	public String sevesMK() {
		String mkphone = request.getParameter("mkphone");
		String mkuserphone = request.getParameter("mkuserphone");

		if (mkuserphone != null && !mkuserphone.equals("15810799888")) {

			String hqlcust = "from TEshopCusCom m where m.account = ?";
			// 被邀请人
			TEshopCusCom tc = (TEshopCusCom) baseBeanService
					.getBeanByHqlAndParams(hqlcust,
							new Object[] { mkuserphone });
			// 邀请人
			TEshopCusCom pcustom = (TEshopCusCom) baseBeanService
					.getBeanByHqlAndParams(hqlcust, new Object[] { mkphone });
			String pcusttype = pcustom.getCusType();
			if (Integer.parseInt(pcusttype) > 5) {
				tc.setSuperioragent(pcustom.getSuperioragent());
			} else {
				// 等于5说明分享者级别是代理商，正好可以绑定上// 大于5说明分享者级别级别低于代理商有可能是客户或者VIP客户
				if (pcusttype == "0") {
					TEshopCusCom pCusCom = (TEshopCusCom) baseBeanService
							.getBeanByHqlAndParams(
									"SELECT T.STAFFID, T.COMPANYID, T.STATE,T.SUPERIORAGENT,"
											+ "T.PSEUDO_COMPANY_NAME,T.CUSTYPE,T.SCCID,T.ACCOUNT"
											+ " FROM T_ESHOP_CUSCOM T where t.custype='1' START WITH T.Account=?"
											+ " CONNECT BY PRIOR T.ACCOUNT=T.SUPERIORAGENT",
									new Object[] { mkphone });
					tc.setSuperioragent(pCusCom.getAccount());
				} else {
					tc.setSuperioragent(pcustom.getAccount());
				}
			}
		}
		JSONObject ret = new JSONObject();
		if (yanzheng(new String[] { mkphone, mkuserphone })) {
			MarKeting mk = new MarKeting();
			mk.setMkuserID(mkphone);
			mk.setUserID(mkuserphone);
			mk.setMkdate(new Date());
			mk.setMkID(serverService.getServerID("MarKeting"));
			try {
				/*baseBeanService.save(mk);*/

			} catch (Exception e) {

				ret.accumulate("return", "1");
				ret.accumulate("why", "错误");
			}
		}
		ret.accumulate("return", "0");

		result = ret;
		return Action.SUCCESS;

	}

	//提供给安卓 因用户注册 功能
	public String  getzhuce()

	{
		String abphone = request.getParameter("abphone");
		HttpServletResponse response = ServletActionContext.getResponse();
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("\\");
		JSONObject ret1 = new JSONObject();
		try {

			response.sendRedirect(path+"/ea/wfjshop/ea_getjspzc.jspa?indus="+abphone);

		} catch (IOException e) {
			logger.error("操作异常", e);
		}

		result = ret1;
		return Action.SUCCESS;

	}

	/**
	 *
	 * 微分金导入微分金微信页面
	 *
	 * @登陆账号 user登陆账号
	 *
	 */
	public String getpage() {
		HttpServletResponse response = ServletActionContext.getResponse();

		JSONObject ret1 = new JSONObject();
		HttpSession session = ServletActionContext.getRequest().getSession();
		customer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams(
				"from TEshopCustomer where account=? AND logOff=0", new Object[] { user });
		SessionWrap sw = SessionWrap.getInstance();
		sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
		try {
			response.sendRedirect("http://192.168.172.8/ea/wfjshop/ea_getWFJshops.jspa?user="
					+ user + "&indus=products");
		} catch (IOException e) {
			logger.error("操作异常", e);
		}
		result = ret1;
		return Action.SUCCESS;
	}

	/**
	 *
	 * 查询邀请信息
	 *
	 * @邀请人 比如 营销人员手机号 返回 list 查询的集合 加上 正确的O
	 * @return
	 */
	public String getmklist() {
		JSONObject ret1 = new JSONObject();
		String mkphone = request.getParameter("mkphone");
		List<Object> list1 = new ArrayList<Object>();
		if (yanzheng(new String[] { mkphone })) {
			String sql = "from  MarKeting  where m" + "kuserID=?";
			List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(
					sql, new String[] { mkphone });
			for (BaseBean ba : list) {
				MarKeting mk = (MarKeting) ba;
				JSONObject ret = new JSONObject();
				ret.accumulate("mkID", mk.getMkID() == null ? "" : mk.getMkID());
				ret.accumulate("mkuserID",
						mk.getMkuserID() == null ? "" : mk.getMkuserID());
				ret.accumulate("userID",
						mk.getUserID() == null ? "" : mk.getUserID());
				ret.accumulate("mkdate", mk.getMkdate() == null ? "" : mk
						.getMkdate().toString());
				list1.add(ret);
			}
		}
		ret1.accumulate("list", list1);
		ret1.accumulate("return", "0");
		result = ret1;
		return Action.SUCCESS;

	}

	/** 判断微分注册用户是否存在wfjRegister **/
	public String isExistWfj() {
		String hql = "from TEshopCustomer where  account = ? AND logOff=0";
		BaseBean bb = baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { user.trim() });
		Map<String, Object> map = new HashMap<String, Object>();
		if (bb != null) {
			map.put("returnCode", "1");// 用户名存在
		} else {
			map.put("returnCode", "0");// 用户名不存在
		}
		result = JSONObject.fromObject(map);
		return Action.SUCCESS;
	}

	/** 删除好友 */
	public String delFriendBy() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String userId = request.getParameter("userId");
			String friendId = request.getParameter("friendId");
			String hql = "delete PhoneFriend where pfUserId=? and pfFriendId=?";
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
					new String[] { hql }, new Object[] { userId, friendId });
			map.put("returnCode", "0");
		} catch (Exception e) {
			map.put("returnCode", "1");
		}
		result = JSONObject.fromObject(map);
		return Action.SUCCESS;
	}

	/** 将好友加入黑名单 */
	public String addBlackBy() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String userId = request.getParameter("userId");
			String friendId = request.getParameter("friendId");
			String hql = "update PhoneFriend set pfState = ? where pfUserId=? and pfFriendId=?";
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
					new String[] { hql },
					new Object[] { "1", userId, friendId });
			map.put("returnCode", "0");
		} catch (Exception e) {
			map.put("returnCode", "1");
		}
		result = JSONObject.fromObject(map);
		return Action.SUCCESS;
	}

	/**
	 *
	 * @author zc
	 * @param @
	 * 登陆成功后查询数据
	 *            加快登陆速度
	 */
	public String getCOrganizationAndCompanyList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount tempAccount = (CAccount) session.get("account");
		Company company = companyDao.getCompanyByCompanyID(tempAccount
				.getCompanyID());// 判断注册公司是否存在
		JSONObject jsonObj = new JSONObject();

		List<JSONObject> retJsonCompany = new ArrayList<JSONObject>();

		DetachedCriteria dcCompany = DetachedCriteria.forClass(Company.class);
		dcCompany.add(Restrictions.eq("companyStatus", "00"));
		dcCompany.add(Restrictions.or(
				Restrictions.eq("companyPID", company.getCompanyID()),
				Restrictions.eq("companyID", company.getCompanyID())));
		List<BaseBean> listCompany = baseBeanService.getListByDC(dcCompany);

		for (Iterator<BaseBean> iterator = listCompany.iterator(); iterator
				.hasNext();) {
			Company com = (Company) iterator.next();

			/**
			 * 公司Json字符串
			 */
			JSONObject jsonObjCom = new JSONObject();
			jsonObjCom.accumulate("companyPID", com.getCompanyPID());
			jsonObjCom.accumulate("companyID", com.getCompanyID());
			jsonObjCom.accumulate("companyName", com.getCompanyName());

			/**
			 * 根据公司ID查询公司下的一级部门
			 */
			List<JSONObject> retJsonCorganization = new ArrayList<JSONObject>();

			DetachedCriteria dcCOrganization = DetachedCriteria
					.forClass(COrganization.class);
			dcCOrganization
					.add(Restrictions.eq("companyID", com.getCompanyID()));
			dcCOrganization.add(Restrictions.eq("organizationPID",
					com.getCompanyID()));
			dcCOrganization.add(Restrictions.eq("Status", "00"));

			List<BaseBean> listCOrganization = baseBeanService
					.getListByDC(dcCOrganization);

			for (Iterator<BaseBean> iterator2 = listCOrganization.iterator(); iterator2
					.hasNext();) {
				COrganization COrgan = (COrganization) iterator2.next();

				/**
				 * 部门Json字符串
				 */
				JSONObject jsonObjCOrganization = new JSONObject();
				jsonObjCOrganization.accumulate("companyID",
						COrgan.getCompanyID());
				jsonObjCOrganization.accumulate("organizationID",
						COrgan.getOrganizationID());
				jsonObjCOrganization.accumulate("organizationPID",
						COrgan.getOrganizationPID());
				jsonObjCOrganization.accumulate("organizationName",
						COrgan.getOrganizationName());

				retJsonCorganization.add(jsonObjCOrganization);

			}

			jsonObjCom.accumulate("listCOrganization", retJsonCorganization);
			retJsonCompany.add(jsonObjCom);

		}

		String staffID = "";
		String staffName = "";
		String organtiozationName = "";
		String organizationID = "";
		if (tempAccount.getStaffID() != ""
				&& !"".equals(tempAccount.getStaffID())) {
			COS cos = (COS) baseBeanService
					.getBeanByHqlAndParams(
							" from COS where staffID=? and cosStatus=? and status=? and  companyID=?",
							new Object[] { tempAccount.getStaffID(), "50",
									"01", company.getCompanyID() });
			if (cos != null && cos.getOrganizationID() != null
					&& !"".equals(cos.getOrganizationID())) {
				COrganization corgan = (COrganization) baseBeanService
						.getBeanByHqlAndParams(
								" from COrganization where organizationID=? and Status=?",
								new Object[] { cos.getOrganizationID(), "00" });
				organtiozationName = corgan.getOrganizationName();
				organizationID = corgan.getOrganizationID();
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						" from Staff where staffID=? ",
						new Object[] { cos.getStaffID() });
				staffName = staff.getStaffName();
				staffID = staff.getStaffID();
			}
		}
		jsonObj.accumulate("companyID", company.getCompanyID());// 公司ID
		jsonObj.accumulate("companyName", company.getCompanyName());// 公司名称
		jsonObj.accumulate("organizationID", organizationID);// 登陆员工部门
		jsonObj.accumulate("organtiozationName", organtiozationName);// 登陆员工部门
		jsonObj.accumulate("staffName", staffName);// 绑定员工姓名
		jsonObj.accumulate("staffID", staffID);// 绑定员工ID

		jsonObj.accumulate("companyList", retJsonCompany);// 公司列表以及公司下所有部门

		List<CCode> typelist = codeService.getCCodeListByPID(
				company.getCompanyID(), "scode20110106hfjes5ucxp0000000003");// 查询行业类别
		/**
		 * 根据公司ID查询公司下的所有行业类别
		 */
		List<JSONObject> retJsonType = new ArrayList<JSONObject>();
		for (Iterator<CCode> iterator = typelist.iterator(); iterator.hasNext();) {
			CCode ccode = (CCode) iterator.next();
			JSONObject jsonObjType = new JSONObject();
			jsonObjType.accumulate("codeID", ccode.getCodeID());
			jsonObjType.accumulate("codeValue", ccode.getCodeValue());
			retJsonType.add(jsonObjType);

		}
		jsonObj.accumulate("typelist", retJsonType);

		List<CCode> connectionlist = codeService.getCCodeListByPID(
				company.getCompanyID(), "scode20110224xpd2t2jvda0000000002");// 查询往来单位关系
		/**
		 * 0 根据公司ID查询公司下的所有单位往来关系
		 */
		List<JSONObject> retJsonConnection = new ArrayList<JSONObject>();
		for (Iterator<CCode> iterator = connectionlist.iterator(); iterator
				.hasNext();) {
			CCode ccode = (CCode) iterator.next();
			JSONObject jsonObjConnection = new JSONObject();
			jsonObjConnection.accumulate("codeID", ccode.getCodeID());
			jsonObjConnection.accumulate("codeValue", ccode.getCodeValue());
			retJsonConnection.add(jsonObjConnection);
		}
		jsonObj.accumulate("connectionlist", retJsonConnection);

		List<CCode> codeRelationList = codeService.getCCodeListByPID(
				company.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		/**
		 * 根据公司ID查询公司下的所有人员往来关系
		 */
		List<JSONObject> retJsonRelation = new ArrayList<JSONObject>();
		for (Iterator<CCode> iterator = codeRelationList.iterator(); iterator
				.hasNext();) {
			CCode ccode = (CCode) iterator.next();
			JSONObject jsonObjRelation = new JSONObject();
			jsonObjRelation.accumulate("codeID", ccode.getCodeID());
			jsonObjRelation.accumulate("codeValue", ccode.getCodeValue());
			retJsonRelation.add(jsonObjRelation);

		}
		jsonObj.accumulate("codeRelationList", retJsonRelation);
		jsonObj.accumulate("userId", tempAccount.getAccountID());
		jsonObj.accumulate("customId", staffID);
		result = jsonObj;
		return "success";
	}

	/**
	 * 省市级联数据查询
	 * pid上级id,可以不传
	 * type类型可以不传,传递 =sheng 返回所有省
	 * @return
	 */
	public String getDistrict() {
		String pid = request.getParameter("pid");
		String type=request.getParameter("type");
		String hql = "from SDistrict where districtStatus=? and districtPID = ?";
		if (pid==null||pid.equals("")) {
			pid = "0";
		}
		if(type!=null&&type.equals("sheng")){
			hql="select m from SDistrict m where m.districtStatus=? and m.districtPID in (select w.districtID from SDistrict w where w.districtPID=? and w.districtStatus=m.districtStatus) ";
		}
		List<BaseBean> sdList = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[] { "00",pid });
		JSONObject jsonObjList = new JSONObject();
		List<JSONObject> retJson = new ArrayList<JSONObject>();
		JSONObject jsonObj = new JSONObject();
		if (sdList != null && sdList.size() > 0) {
			for (int i = 0; i < sdList.size(); i++) {
				SDistrict sdi = (SDistrict) sdList.get(i);
				jsonObj = new JSONObject();
				jsonObj.accumulate("districtID", sdi.getDistrictID());
				jsonObj.accumulate("districtName", sdi.getDistrictName());
				retJson.add(jsonObj);
			}
		}
		jsonObjList.accumulate("list", retJson);
		result = jsonObjList;
		return "success";
	}


	/**
	 * 获取电话薄
	 *
	 * @return
	 */
	public String getPhoneList() {
		String sql = "select s.staffName,s.photo,s.reference,s.referenceCode,s.address,s.referenceorganization,s.staffdesc "
				+ " from dtcos c "
				+ " left join dtcorganization o on c.organizationID = o.organizationID "
				+ " left join dt_hr_staff s on c.staffID = s.staffID "
				+ " left join dtcompany dc on c.companyid=dc.companyid "
				+ " where c.cosStatus = '50' and c.status = '01'";
		List<Object> array = new ArrayList<Object>();
		if (companyID != null && !"".equals(companyID)) {
			sql += " and c.companyid= ? ";
			array.add(companyID);
		}
		if (organizationID != null && !"".equals(organizationID)) {
			sql += " and c.organizationID= ? ";
			array.add(organizationID);
		}

		if (staffName != null && !"".equals(staffName)) {
			sql += " and s.staffName like ?";
			array.add("%" + staffName + "%");
		}
		/*
		 * if(companyName!=null&&!"".equals(companyName)){ sql+=" and
		 * dc.companyName like ?"; array.add("%"+companyName+"%"); }
		 * if(organizationName!=null&&!"".equals(organizationName)){ sql+=" and
		 * o.organizationName like ?"; array.add("%"+organizationName+"%"); }
		 */
		pageForm = baseBeanService.getPageFormBySQL(
				(pageNumber != 0 ? pageNumber : 1), (pageSize == 0 ? 10
						: pageSize), sql, "select count(1) from (" + sql + ")",
				array.toArray());
		JSONObject jsonObjList = new JSONObject();
		List<JSONObject> retJson = new ArrayList<JSONObject>();
		if (pageForm != null && pageForm.getList() != null
				&& pageForm.getList().size() > 0) {
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("staffName", obj[0]);
				jsonObj.accumulate("photo", obj[1]);
				jsonObj.accumulate("reference", obj[2]);
				jsonObj.accumulate("referenceCode", obj[3]);
				jsonObj.accumulate("address", obj[4]);
				jsonObj.accumulate("referenceorganization", obj[5]);
				jsonObj.accumulate("staffdesc", obj[6]);
				retJson.add(jsonObj);
			}
			jsonObjList.accumulate("list", retJson);
			jsonObjList.accumulate("pageNumber", pageForm.getPageNumber());
			jsonObjList.accumulate("pageSize", pageForm.getPageSize());
			jsonObjList.accumulate("recordCount", pageForm.getRecordCount());
			result = jsonObjList;
		} else {
			result = "noData";
		}
		return "success";
	}

	/**
	 * 获取工资列表
	 *
	 * @return
	 */
	public String getWagesList() {
		List<Object> parms = new ArrayList<Object>();
		StringBuffer sb_search = null;
		sb_search = new StringBuffer(VOtool.getWagesHql());
		StringBuffer sb_count = new StringBuffer(
				"select count(*) from LogBookSummary j where j.logBookKey in "
						+ "( select max(j.logBookKey) from LogBookSummary j ");

		sb_search.append(" where j.companyID = ? ");
		sb_count.append(" where j.companyID=? ");
		parms.add(companyID.trim());

		sb_search.append(" and todaydate between ? and ? ");
		sb_count.append(" and todaydate between ? and ? ");
		parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
		parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));

		String staffID = request.getParameter("staffID");
		if (staffID != null && !"".equals(staffID.trim())) {
			sb_search.append(" and j.staffID = ? ");
			sb_count.append(" and j.staffID = ? ");
			parms.add(staffID.trim());
		}

		sb_search.append(" and j.bisect is not null ");
		sb_count.append(" and j.bisect is not null ");

		sb_search
				.append(" group by j.companyID,j.staffID,j.staffName,j.categoryname  order by j.companyID,j.staffName ");
		sb_count.append(" group by j.companyID,j.staffID,j.categoryname ) ");

		pageForm = baseBeanService.getPageForm((pageNumber != 0 ? pageNumber
						: 1), (pageSize == 0 ? 10 : pageSize), sb_search.toString(),
				sb_count.toString(), parms.toArray());
		JSONObject jsonObjList = new JSONObject();
		List<JSONObject> retJson = new ArrayList<JSONObject>();
		if (pageForm != null && pageForm.getList() != null
				&& pageForm.getList().size() > 0) {
			for (BaseBean baseBean : pageForm.getList()) {
				SalaryIntegral salaryInt = (SalaryIntegral) baseBean;
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("staffName", salaryInt.getStaffName());
				jsonObj.accumulate("categoryname", salaryInt.getCategoryname());
				jsonObj.accumulate("weekendIntegral",
						salaryInt.getWeekendIntegral());
				jsonObj.accumulate("workNightIntegral",
						salaryInt.getWorkNightIntegral());
				jsonObj.accumulate("workHolidaysIntegral",
						salaryInt.getWorkHolidaysIntegral());
				jsonObj.accumulate("attendanceDiscount",
						salaryInt.getAttendanceDiscount());
				jsonObj.accumulate("personalDiscount",
						salaryInt.getPersonalDiscount());
				// jsonObj.accumulate("ObtainedMenoy",
				// salaryInt.getObtainedMenoy());实得工资
				retJson.add(jsonObj);
			}
			jsonObjList.accumulate("list", retJson);
			jsonObjList.accumulate("pageNumber", pageForm.getPageNumber());
			jsonObjList.accumulate("pageSize", pageForm.getPageSize());
			jsonObjList.accumulate("recordCount", pageForm.getRecordCount());
			result = jsonObjList;
		} else {
			result = "noData";
		}
		return "success";
	}

	/**
	 * 获取个人日志列表 staffID 为个人的ID
	 */
	public String getuserlolist() {
		String staffID = request.getParameter("staffID");
		String companyID = request.getParameter("companyID");
		String pageSize = request.getParameter("pageSize");
		String pageNumber = request.getParameter("pageNumber");
		String hqlString = "from LogBook where staffID=? and  todaydate is not null  and companyID='"
				+ companyID + "' order by todaydate  DESC";
		String contString = "select count(*) from LogBook where staffID=? ";
		pageForm = baseBeanService.getPageForm(
				Integer.parseInt(pageNumber == null ? "1" : pageNumber),
				Integer.parseInt(pageSize == null ? "10" : pageSize),
				hqlString, contString, new String[] { staffID });
		JSONObject jsonObj = new JSONObject();
		List<Object> list = new ArrayList<Object>();
		if (pageForm != null && pageForm.getList() != null) {
			for (BaseBean bb : pageForm.getList()) {
				LogBook lb = (LogBook) bb;
				JSONObject jsonObj1 = new JSONObject();
				jsonObj1.accumulate("logBookKey",
						lb.getLogBookKey() == null ? "" : lb.getLogBookKey());
				jsonObj1.accumulate("logBookID", lb.getLogBookID() == null ? ""
						: lb.getLogBookID());
				jsonObj1.accumulate("companyID", lb.getCompanyID() == null ? ""
						: lb.getCompanyID());
				jsonObj1.accumulate(
						"organizationID",
						lb.getOrganizationID() == null ? "" : lb
								.getOrganizationID());
				jsonObj1.accumulate("staffID", lb.getStaffID() == null ? ""
						: lb.getStaffID());
				jsonObj1.accumulate("inputDate", lb.getInputDate() == null ? ""
						: lb.getInputDate());
				jsonObj1.accumulate("startdate", lb.getStartdate() == null ? ""
						: lb.getStartdate());
				jsonObj1.accumulate("jobLocation",
						lb.getJobLocation() == null ? "" : lb.getJobLocation());
				jsonObj1.accumulate("enddate", lb.getEnddate() == null ? ""
						: lb.getEnddate());
				jsonObj1.accumulate("jobContent",
						lb.getJobContent() == null ? "" : lb.getJobContent());
				jsonObj1.accumulate("scoreSort", lb.getScoreSort() == null ? ""
						: lb.getScoreSort());
				jsonObj1.accumulate("bisect",
						lb.getBisect() == null ? "" : lb.getBisect());
				jsonObj1.accumulate("attachment",
						lb.getAttachment() == null ? "" : lb.getAttachment());
				jsonObj1.accumulate("supervisor",
						lb.getSupervisor() == null ? "" : lb.getSupervisor());
				jsonObj1.accumulate(
						"staffingManage",
						lb.getStaffingManage() == null ? "" : lb
								.getStaffingManage());
				jsonObj1.accumulate("manager", lb.getManager() == null ? ""
						: lb.getManager());
				jsonObj1.accumulate("todaydate", lb.getTodaydate() == null ? ""
						: sdf.format(lb.getTodaydate()));
				jsonObj1.accumulate("status",
						lb.getStatus() == null ? "" : lb.getStatus());
				jsonObj1.accumulate("photo",
						lb.getPhoto() == null ? "" : lb.getPhoto());
				jsonObj1.accumulate(
						"photoFileName",
						lb.getPhotoFileName() == null ? "" : lb
								.getPhotoFileName());
				jsonObj1.accumulate(
						"photoContentType",
						lb.getPhotoContentType() == null ? "" : lb
								.getPhotoContentType());
				list.add(jsonObj1);
			}
			jsonObj.accumulate("list", list);
			jsonObj.accumulate("reslut", "0");
		} else {
			jsonObj.accumulate("reslut", "1");
			jsonObj.accumulate("yuanyin", "没有数据");
		}
		result = jsonObj;
		return "success";
	}

	/**
	 * 删除日志 一个日志ID logBookid
	 */
	public String datelogbook() {
		String LBid = request.getParameter("logBookID");
		String hql = "DELETE  LogBook  where logBookID=?";
		String hql1 = "from LogBook where logBookID=?";
		JSONObject jsonObj = new JSONObject();
		if (yanzheng(new String[] { LBid })) {
			LogBook lk = (LogBook) baseBeanService.getBeanByHqlAndParams(hql1,
					new String[] { LBid });
			if (lk != null) {
				if (lk.getStatus().equals("01")) {
					jsonObj.accumulate("Reason", "锁定日志不允许删除");
					jsonObj.accumulate("result", "1");
				} else {
					baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
							new String[] { hql }, new String[] { LBid });
					jsonObj.accumulate("result", "0");
				}
			} else {
				jsonObj.accumulate("result", "1");
				jsonObj.accumulate("Reason", "未找到日志");
			}
		} else {
			jsonObj.accumulate("return", "1");
			jsonObj.accumulate("Reason", "参数为空");
		}
		result = jsonObj;
		return "success";
	}

	/**
	 * 增加个人日志 或者修改 修改穿进去一个 日志ID 人的ID 所选时间 截止时间 开始时间 工作地点 完成 工作内容 德妃类型ID 得分
	 *
	 * @throws ParseException
	 */
	public String addlogbook() {
		String LBid = request.getParameter("logBookID");
		String staffID = request.getParameter("staffID");
		String compid = request.getParameter("companyID");
		String inputDate = request.getParameter("inputDate");
		String enddate = request.getParameter("enddate");
		String startdate = request.getParameter("startdate");
		String jobLocation = request.getParameter("jobLocation");
		String jobContent = request.getParameter("jobContent");
		String neirong = request.getParameter("neirong");
		String scoreSort = request.getParameter("scoreSort");
		String bisect = request.getParameter("bisect");

		List<BaseBean> lst=new ArrayList<BaseBean>();
		List<String> hqls=new ArrayList<String>();
		List<Object[]> parms=new ArrayList<Object[]>();
		JSONObject jsonObj = new JSONObject();
		LogBook lb = new LogBook();
		if (LBid != null) {
			lb.setLogBookID(LBid);
			String hql = "from LogBook  where  logBookID=?";
			LogBook temp = (LogBook) baseBeanService.getBeanByHqlAndParams(hql,new String[] { LBid });
			if (temp != null) {
				if (temp.getStatus().equals("01")) {
					jsonObj.accumulate("where ", "锁定日志不允许修改");
					jsonObj.accumulate("result", 1);
					return "success";
				}
				try {
					BeanUtils.copyProperties(lb, temp);
				} catch (IllegalAccessException e) {
					logger.error("操作异常", e);
				} catch (InvocationTargetException e) {
					logger.error("操作异常", e);
				}
			}
		}else{
			lb.setLogBookID(serverService.getServerID("LogBook"));
		}
		if (!Utilities.isNumeric(bisect)) {
			bisect="";
		}

		lb.setStaffID(staffID);
		lb.setInputDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date()));
		lb.setStartdate(startdate);
		lb.setCompanyID(compid);
		lb.setOrganizationID(organizationID);
		lb.setJobContent(jobContent == null ? neirong : jobContent);
		lb.setJobLocation(jobLocation);
		lb.setEnddate(enddate);
		lb.setBisect(bisect);
		lb.setStatus("00");
		lb.setScoreSort(scoreSort);

		try {
			lb.setTodaydate(sdf.parse(inputDate));
		} catch (Exception e) {
			jsonObj.accumulate("result", 1);
			return "success";
		}
		lst.add(lb);

		hqls.add("delete from LogConten l where l.companyid = ? and l.logbookid = ?");
		parms.add(new Object[]{compid,LBid});

		LogConten lc=new LogConten();
		lc.setJobTaskID("其他");
		lc.setProjectID("其他");
		lc.setRemark(jobContent);
		lc.setContactcom("0%");

		lc.setLogcontenid(serverService.getServerID("logconten"));
		lc.setLogbookid(lb.getLogBookID());
		lc.setCtime(new Date());
		lc.setCompanyid(compid);
		lst.add(lc);
		baseBeanService.executeHqlsByParamsList(lst, hqls.toArray(new String[]{}), parms);
		jsonObj.accumulate("result", "0");
		result = jsonObj;
		return "success";
	}

	/**
	 * 获取日志列表
	 *
	 * @return
	 */
	public String getLogList() {
		List<Object> parms = new ArrayList<Object>();
		String hql = " from LogBook where companyID=?";
		parms.add(companyID.trim());
		if (sdate != null && !"".equals(sdate) && edate != null
				&& !"".equals(edate)) {
			hql += " and todaydate between ? and ? ";
			parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
			parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
		}
		String staffID = request.getParameter("staffID");
		if (staffID != null && !"".equals(staffID.trim())) {
			hql += " and staffID = ? ";
			parms.add(staffID.trim());
		}
		hql += "  order by todaydate  desc ";
		pageForm = baseBeanService.getPageForm((pageNumber != 0 ? pageNumber
				: 1), (pageSize == 0 ? 10 : pageSize), hql, parms.toArray());
		JSONObject jsonObjList = new JSONObject();
		List<JSONObject> retJson = new ArrayList<JSONObject>();
		if (pageForm != null && pageForm.getList() != null
				&& pageForm.getList().size() > 0) {
			for (BaseBean baseBean : pageForm.getList()) {
				LogBook logBook = (LogBook) baseBean;
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("todaydate", Utilities.getDateString(
						logBook.getTodaydate(), "yyyy-MM-dd"));
				jsonObj.accumulate("startdate", logBook.getStartdate());
				jsonObj.accumulate("enddate", logBook.getEnddate());
				jsonObj.accumulate("jobContent", logBook.getJobContent());
				jsonObj.accumulate("bisect", logBook.getBisect());

				jsonObj.accumulate("logBookKey", logBook.getLogBookKey());
				jsonObj.accumulate("logBookID", logBook.getLogBookID());
				jsonObj.accumulate("status", logBook.getStatus());
				retJson.add(jsonObj);
			}
			jsonObjList.accumulate("list", retJson);
			jsonObjList.accumulate("pageNumber", pageForm.getPageNumber());
			jsonObjList.accumulate("pageSize", pageForm.getPageSize());
			jsonObjList.accumulate("recordCount", pageForm.getRecordCount());
			result = jsonObjList;
		} else {
			result = "noData";
		}
		return "success";
	}

	/**
	 * 获取计划列表
	 *
	 * @return
	 */
	public String getJobPlanList() {
		DetachedCriteria dc = DetachedCriteria.forClass(COSJobPlan.class);
		dc.add(Restrictions.eq("companyID", companyID.trim()));
		if (sdate != null && !"".equals(sdate) && edate != null
				&& !"".equals(edate)) {
			dc.add(Restrictions.between("startDate",
					Utilities.getDateFromString(sdate, "yyyy-MM-dd"),
					Utilities.getDateFromString(edate, "yyyy-MM-dd")));
		}
		String staffID = request.getParameter("staffID");
		if (staffID != null && !"".equals(staffID.trim())) {
			dc.add(Restrictions.eq("staffID", staffID.trim()));
		}
		dc.addOrder(Order.desc("startDate"));
		pageForm = baseBeanService.getPageFormByDC(
				(pageNumber != 0 ? pageNumber : 1), (pageSize == 0 ? 10
						: pageSize), dc);
		JSONObject jsonObjList = new JSONObject();
		List<JSONObject> retJson = new ArrayList<JSONObject>();
		if (pageForm != null && pageForm.getList() != null
				&& pageForm.getList().size() > 0) {
			for (BaseBean baseBean : pageForm.getList()) {
				COSJobPlan jobPlan = (COSJobPlan) baseBean;
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("jobName", jobPlan.getJobName());
				jsonObj.accumulate("startdate", Utilities.getDateString(
						jobPlan.getStartDate(), "yyyy-MM-dd"));
				jsonObj.accumulate("enddate", Utilities.getDateString(
						jobPlan.getEndDate(), "yyyy-MM-dd"));
				jsonObj.accumulate("jobContent", jobPlan.getJobContent());
				jsonObj.accumulate("jobPlanID", jobPlan.getJobPlanID());
				jsonObj.accumulate("jobPlanKey", jobPlan.getJobPlanKey());
				retJson.add(jsonObj);
			}
			jsonObjList.accumulate("list", retJson);
			jsonObjList.accumulate("pageNumber", pageForm.getPageNumber());
			jsonObjList.accumulate("pageSize", pageForm.getPageSize());
			jsonObjList.accumulate("recordCount", pageForm.getRecordCount());
			result = jsonObjList;
		} else {
			result = "noData";
		}
		return "success";
	}

	/**
	 * 新增计划
	 *
	 * @return
	 */
	public String addJobPlanList() {
		int results = 0;
		try {
			if (jobPlans.getJobPlanID() == null
					|| "".equals(jobPlans.getJobPlanID())) {
				jobPlans.setJobPlanID(serverService.getServerID("jobPlan"));
			}
			baseBeanService.update(jobPlans);
			results = 1;
		} catch (RuntimeException e) {
			logger.error("操作异常", e);
		}
		JSONObject jsonObj = new JSONObject();
		jsonObj.accumulate("result", results);
		result = jsonObj;
		return "success";
	}

	/**
	 * 获取公共电话薄管理
	 *
	 * @return
	 */
	public String getPublicTelephoneList() {
		List<Object> arag = new ArrayList<Object>();
		String sql = "select * from  (select vm.COMPANYNAME  comName ,vm.contactconnections contact ,vm.CRESPONSIBLE peopleName ,vm.RESPONSIBLETEL peoplePhone,vm.REMARK peopleEmail,vm.companyAddr address,vm.companyid,vm.INDUSTRYTYPE  industryType  ,'暂未用' staffcode,'暂未用' staffIdentityCard from"
				+ " view_concom vm "
				+ " union all "
				+ " select (select dc.companyname from dtcompany dc where dc.companyid=vs.COMPANYID) comName,vs.RELATION contact,vs.STAFFNAME peopleName,vs.REFERENCE peoplePhone,vs.REFERENCEORGANIZATION peopleEmail,vs.STAFFADDRESS address,vs.COMPANYID,'暂未用' as industryType , vs.STAFFCODE staffcode,vs.STAFFIDENTITYCARD  staffIdentityCard "
				+ " from view_conuser vs ) tg where tg.companyid=?";
		arag.add(companyID.trim());
		try {
			request.setCharacterEncoding("gb2312");
		} catch (UnsupportedEncodingException e) {
			logger.error("操作异常", e);
		}
		String comName = request.getParameter("comName");
		String address = request.getParameter("address");
		String peoplePhone = request.getParameter("peoplePhone");
		String staffcode = request.getParameter("staffcode");
		String staffIdentityCard = request.getParameter("staffIdentityCard");
		if (comName != null && !"".equals(comName.trim())) {
			sql += " and tg.comName like ?";
			arag.add("%" + comName.trim() + "%");
		}
		if (address != null && !"".equals(address.trim())) {
			sql += " and tg.address like ?";
			arag.add("%" + address.trim() + "%");
		}
		if (peoplePhone != null && !"".equals(peoplePhone.trim())) {
			sql += " and tg.peoplePhone like ?";
			arag.add("%" + peoplePhone.trim() + "%");
		}
		if (staffcode != null && !"".equals(staffcode.trim())) {
			sql += " and tg.staffcode like ?";
			arag.add("%" + staffcode.trim() + "%");
		}
		if (staffIdentityCard != null && !"".equals(staffIdentityCard.trim())) {
			sql += " and tg.staffIdentityCard like ?";
			arag.add("%" + staffIdentityCard.trim() + "%");
		}
		if (peopleName != null && !"".equals(peopleName.trim())) {
			sql += " and tg.peopleName like ?";
			arag.add("%" + peopleName.trim() + "%");
		}
		if (contact != null && !"".equals(contact.trim())) {
			sql += " and tg.contact like ?";
			arag.add("%" + contact.trim() + "%");
		}
		if (industryType != null && !"".equals(industryType.trim())) {
			sql += " and tg.industryType like ?";
			arag.add("%" + industryType.trim() + "%");
		}
		pageForm = baseBeanService.getPageFormBySQL(
				(pageNumber != 0 ? pageNumber : 1), (pageSize == 0 ? 10
						: pageSize), sql, "select count(1) from (" + sql + ")",
				arag.toArray());
		JSONObject jsonObjList = new JSONObject();
		List<JSONObject> retJson = new ArrayList<JSONObject>();
		if (pageForm != null && pageForm.getList() != null
				&& pageForm.getList().size() > 0) {
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("comName", obj[0]);
				jsonObj.accumulate("contact", obj[1]);
				jsonObj.accumulate("peopleName", obj[2]);
				jsonObj.accumulate("peoplePhone", obj[3]);
				jsonObj.accumulate("peopleEmail", obj[4]);
				jsonObj.accumulate("address", obj[5]);
				jsonObj.accumulate("industryType", obj[7]);
				retJson.add(jsonObj);
			}
			jsonObjList.accumulate("list", retJson);
			jsonObjList.accumulate("pageNumber", pageForm.getPageNumber());
			jsonObjList.accumulate("pageSize", pageForm.getPageSize());
			jsonObjList.accumulate("recordCount", pageForm.getRecordCount());
			result = jsonObjList;
		} else {
			result = "noData";
		}

		return "success";
	}

	/**
	 * 接待个人记录
	 * http://localhost:8080/hyplat/ea/android/sajax_ea_personRecords.jspa
	 * ?companyID=company201007133na5ggn2u30000000001
	 *
	 * @return
	 */
	public String personRecords() {
		List<Object> list = new ArrayList<Object>();
		list.add("1");
		String search = request.getParameter("search");// 查询标识
		if (search == null) {

			getDataByCustmerType(" TelInRecord as s ",
					" and s.customType = ? order by s.recodeDate desc ",
					list.toArray());
		} else {
			getRecords(list);
		}

		return "success";
	}

	/**
	 * 构建接待记录查询
	 *
	 * @param list
	 */
	private void getRecords(List<Object> list) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(" and customType = ? ");
		String begin = request.getParameter("beginDate");// 查询标识
		String end = request.getParameter("endDate");// 查询标识
		String queryDateType = request.getParameter("queryDateType");// 查询标识
		// 日期
		Date from = DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT,
				null == begin ? null : begin.toString());
		Date to = DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT,
				null == end ? null : end.toString());
		if (from != null && to != null) {
			if (from.compareTo(to) > 0) {
				result = "noData";
				return;
			}
		}

		// 接待类型
		if (null != telInRecord && telInRecord.getRecordType() != null
				&& telInRecord.getRecordType().length() > 0) {
			buffer.append(" and recordType = ?");
			list.add(telInRecord.getRecordType());

		}

		// 是否处理
		if (null != telInRecord) {
			buffer.append(" and isDeal = ?");
			list.add(telInRecord.getIsDeal());

		}

		// 客户名称
		if (null != telInRecord && telInRecord.getCustomName() != null
				&& telInRecord.getCustomName().length() > 0) {
			buffer.append(" and customName = ?");
			list.add(telInRecord.getCustomName());
		}

		// 客户电话
		if (null != telInRecord && telInRecord.getTelNumber() != null
				&& telInRecord.getTelNumber().length() > 0) {
			buffer.append(" and customTel = ?");
			list.add(telInRecord.getTelNumber());
		}

		if (from != null) {
			// 时间处理
			if (null != queryDateType && "1".equals(queryDateType)) {
				buffer.append("  and recodeDate "); // 接待时间
			} else if (null != queryDateType && "2".equals(queryDateType)) {
				buffer.append("  and dealDate "); // 处理时间
			}

			buffer.append(" > to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
			list.add(begin.toString());
		}

		if (to != null) {
			// 时间处理
			if (null != queryDateType && "1".equals(queryDateType)) {
				buffer.append("  and recodeDate "); // 接待时间
			} else if (null != queryDateType && "2".equals(queryDateType)) {
				buffer.append("  and dealDate "); // 处理时间
			}

			buffer.append(" < to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
			list.add(end.toString());
		}

		buffer.append(" order by s.recodeDate desc ");

		getDataByCustmerType("TelInRecord as s  ", buffer.toString(),
				list.toArray());
	}

	/**
	 * 电话打出记录
	 *
	 * @return
	 */
	public String returnsVisit() {

		String search = request.getParameter("search");// 查询标识
		if (search == null) {
			getDataByCustmerType(" TelOutRecord as s ",
					" order by s.visitedTime desc ", null);
		} else {
			StringBuilder buffer = new StringBuilder();

			String begin = request.getParameter("beginDate");
			String end = request.getParameter("endDate");
			String returnVisits = request.getParameter("returnVisits");

			List<String> list = new ArrayList<String>();
			if (null != returnVisits && returnVisits.toString().length() > 0) {
				buffer.append("  and telType = ? ");
				list.add(returnVisits.toString());
			}
			// 日期
			Date from = DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT,
					null == begin ? null : begin.toString());
			Date to = DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT,
					null == end ? null : end.toString());
			if (from != null && to != null) {
				if (from.compareTo(to) > 0) {
					result = "noData";
					return "success";
				}
			}

			if (from != null) {
				buffer.append(" and visitedtime > to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
				list.add(begin.toString());
			}

			if (to != null) {
				buffer.append(" and visitedtime < to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
				list.add(end.toString());
			}

			buffer.append(" order by s.visitedTime desc ");
			getDataByCustmerType("TelOutRecord as s ", buffer.toString(),
					list.toArray());
		}

		return "success";
	}

	/**
	 * 构建分页
	 *
	 * @param Obj
	 *            查询所属对象
	 * @param where
	 *            更多条件
	 * @param obj
	 *            where参数列表
	 */
	private void getDataByCustmerType(String Obj, String where, Object[] obj) {
		/**
		 * 获取电话通话类型
		 *
		 * @param company
		 * @param pcode
		 */
		codeList = ccodeService.getCCodeListByPID(companyID,
				"scode20120511cyyypnpchw0000000002");

		String hql = " from @@ where s.company = ? and s.isDel = 0 ### "
				.replace("@@", Obj);
		List<Object> list = new ArrayList<Object>();
		list.add(companyID);

		if (where != null && where.length() > 0) {
			hql = hql.replace("###", where);

			if (obj != null) {
				for (Object item : obj) {
					list.add(item);
				}
				obj = null;
			}
		} else {
			hql = hql.replace("###", "");
		}

		pageForm = baseBeanService.getPageForm((pageNumber != 0 ? pageNumber
				: 1), (pageSize == 0 ? 10 : pageSize), hql, list.toArray());
		JSONObject jsonObjList;
		try {
			jsonObjList = new JSONObject();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,
					new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss")); // 当输出时间格式时，采用和JS兼容的格式输出
			if (pageForm != null && pageForm.getList() != null
					&& pageForm.getList().size() > 0) {
				JSONArray jsonArray2 = JSONArray.fromObject(pageForm.getList(),
						jsonConfig);
				jsonObjList.accumulate("list", jsonArray2);
				jsonObjList.accumulate("pageNumber", pageForm.getPageNumber());
				jsonObjList.accumulate("pageSize", pageForm.getPageSize());
				jsonObjList
						.accumulate("recordCount", pageForm.getRecordCount());
				jsonObjList.accumulate("codeList", codeList);
				result = jsonObjList;
			} else {
				result = "noData";
			}
		} catch (Exception e) {
			result = "abnormal";
			logger.error("操作异常", e);
		}

	}

	@SuppressWarnings("deprecation")
	public String uploadFile() {
		try {
			FileInputStream fis = new FileInputStream(file);
			String photospath = ServletActionContext.getRequest().getRealPath(
					"photos");
			File fs = new File(photospath, fileFileName);
			FileOutputStream fos = new FileOutputStream(fs);
			int len = 0;
			byte[] buffer = new byte[1024];

			while ((len = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			fos.flush();
			fos.close();
			fis.close();

		} catch (FileNotFoundException e) {
			logger.error("操作异常", e);
		} catch (IOException e) {
			logger.error("操作异常", e);
		}

		return "uploadfile";
	}

	public String httpAllImages() {
		String path = ServletActionContext.getServletContext().getRealPath(
				"/Upload_files/fileCabinet/systemIcom");
		File filePath = new File(path);
		File[] files = filePath.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (!file.isDirectory()) {
				String fileName = file.getName();
				String img = fileName.substring(fileName.lastIndexOf(".") + 1);
				if ("jpg".equals(img) || "jpeg".equals(img)
						|| "gif".equals(img) || "png".equals(img)) {
					list.add(fileName);
				}
			}
		}
		return "images";
	}

	/**
	 *
	 * 保存或修改打入记录
	 *
	 * @return customName telNumber customType customId recordType recordContent
	 *         recodeDate company userId
	 *         &telInRecord.beginTime="2013-03-21 14:56:21"
	 *         &telInRecord.dealDate="2013-03-21 14:56:21"
	 *         &telInRecord.endTime="2013-03-21 14:56:21"
	 *         http://localhost:8080/hyplat
	 *         /ea/android/sajax_ea_saveTelInRec.jspa
	 *         ?telInRecord.customName=ddd&telInRecord.recodeDate='2013-03-21
	 *         14:56:21'
	 *         &telInRecord.customId=errorcstaff20121012GIHJ67J6N20000003038
	 *         &telInRecord.company=company201007133na5ggn2u30000000001
	 *         &userId=caccount20121204J6H8KD89GY0000000002 &telInRecord.isDel=0
	 *         &telInRecord.savePath=\data\j.jpg &telInRecord.recordType=1
	 *         &telInRecord.telNumber='13810593241 &telInRecord.isDeal=1
	 *         &telInRecord.recordContent=厉害 &telInRecord.dealContent=未知
	 *         &telInRecord.customType=1 &telInRecord.dealUser=未
	 *         &companyID=company201007133na5ggn2u3000000001
	 *         &telInRecord.recodeDate=2013-03-21 14:56:21
	 *
	 */
	public String saveTelInRec() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			if (telInRecord != null
					&& (null == telInRecord.getId() || "".equals(telInRecord
					.getId()))) {
				if (telInRecord != null) {
					Staff user = (Staff) baseBeanService
							.getBeanByHqlAndParams(
									" from Staff s where s.staffID in( select t.staffID from CAccount t where t.accountID = ? ) ",
									new Object[] { this.userId });
					telInRecord.setUser(user);

					// 判断手机号是座机0还是手机1
					String regExp = "^[1]([3][0-9]{1}|59|58|88|89|51)[0-9]{8}$";
					Pattern p = Pattern.compile(regExp);
					Matcher m = p.matcher(telInRecord.getTelNumber());
					if (m.find()) {
						telInRecord.setTelcodeType("1");
					} else {
						telInRecord.setTelcodeType("0");
					}

					telInRecord.setId(serverService.getServerID("TelInRecord"));

					TelInRecordDeal deal = new TelInRecordDeal();
					deal.setId(serverService.getServerID("TelInRecordDeal"));
					deal.setCompany(telInRecord.getCompany());
					deal.setCompanyname("");
					deal.setDealcontent(telInRecord.getDealContent());
					deal.setDealdate(telInRecord.getDealDate());
					deal.setDealuser(telInRecord.getDealUser());
					deal.setIsdeal((int) telInRecord.getIsDeal());
					deal.setTelInRecord(telInRecord);
					baseBeanService.save(telInRecord);
					baseBeanService.save(deal);

					map.put("result", "1");
					map.put("id", telInRecord.getId());

				}
			} else {
				map.put("result", "0");
			}

		} catch (Exception e) {
			map.put("result", "0");
			logger.error("操作异常", e);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject json = JSONObject.fromObject(map);
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {

			logger.error("操作异常", e);
		}
		return null;
	}

	/**
	 * 保存或修改打出记录
	 *
	 * visitedTime visitedContent isDel company telNumber userId
	 * http://localhost
	 * :8080/hyplat/ea/android/sajax_ea_saveTelOutRec.jspa?telOutRecord
	 * .visitedContent
	 * =123141&telOutRecord.isDel=123&telOutRecord.company=123&telOutRecord
	 * .telNumber
	 * =1&userId=caccount20120331VCVX7IJXUZ0000000565&telOutRecord.visitedTime
	 * =2013-03-20 16:33:44
	 *
	 * @return
	 */
	public String saveTelOutRec() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (telOutRecord != null
					&& (null == telOutRecord.getId() || "".equals(telOutRecord
					.getId()))) {
				Staff user = (Staff) baseBeanService
						.getBeanByHqlAndParams(
								" from Staff s where s.staffID in( select t.staffID from CAccount t where t.accountID = ? ) ",
								new Object[] { this.userId });

				telOutRecord.setUser(user);

				// 判断手机号是座机0还是手机1
				String regExp = "^[1]([3][0-9]{1}|59|58|88|89|51)[0-9]{8}$";
				Pattern p = Pattern.compile(regExp);
				Matcher m = p.matcher(telOutRecord.getTelNumber());
				if (m.find()) {
					telOutRecord.setTelcodeType("1");
				} else {
					telOutRecord.setTelcodeType("0");
				}
				telOutRecord.setId(serverService.getServerID("TelOutRecord"));
				baseBeanService.save(telOutRecord);
				map.put("id", telOutRecord.getId());
				map.put("result", "1");
			} else {
				map.put("result", "0");
			}
		} catch (Exception e) {
			map.put("result", "0");
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		JSONObject json = JSONObject.fromObject(map);

		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {

			logger.error("操作异常", e);
		}
		return null;
	}

	/**
	 * 发送 验证码 第一 电话号码 第二 验证码
	 *
	 * @return
	 *
	 *
	 * @throws Exception
	 */
	public String getduanxin() {
		String sbhk = request.getParameter("sbhk");// 用于防止攻击

		Map<String, Object> session = ActionContext.getContext().getSession();
		String pahe = request.getParameter("pahe");// 电话号码
		String yanz = request.getParameter("yanz");// 验证码

		JSONObject temp = new JSONObject();
		if(yanz!=null)
		{
//			logger.info("调试信息");
//			logger.info("调试信息");
			if(Utilities.MD5(yanz+pahe+yanz).equals(sbhk)) {
				logger.info("带随机数调用");
				if (yanzheng(new String[]{pahe, yanz})) {
					sdk.getduan(pahe, yanz);
					temp.accumulate("return", "0");
				} else {
					temp.accumulate("return", "1");
				}
			}else{
				temp.accumulate("return", "1");

			}
		}else{

			logger.info("调试信息");
			if (sbhk != null && !sbhk.equals("")&&sbhk.equals(session.get("sbhk"))) {
				logger.info("不带随机数调用");
			}else{
				logger.info("不合理调用");
//				return "success";
			}

			java.util.Random random = new java.util.Random();
			int ir=100000;
			int is=999999;
			int i=random.nextInt(is)%(is-ir+1)+ir;
			String yz=""+i;
			sdk.getduan(pahe,yz);
			/* 国际短信 暂时不用
			 * String back = MobileSMS.sendMessage(pahe, yanz);
			if(back.equals("0")){*/
			temp.accumulate("returna",yz);
			/*}else{
				temp.accumulate("return", "error");
			}*/

		}
		result = temp;

		return "success";

	}

	/**
	 *
	 * e路快车
	 * @return
	 */
	public String getElkcVali() {
		String pahe = request.getParameter("pahe");// 电话号码
		JSONObject temp = new JSONObject();
		java.util.Random random = new java.util.Random();
		int ir=100000;
		int is=999999;
		int i=random.nextInt(is)%(is-ir+1)+ir;
		String yz=""+i;
		sdk.getElkcduan(pahe,yz);

		temp.accumulate("returna",yz);
		result = temp;
		return "success";

	}

	@SuppressWarnings("deprecation")
	/*public String getTelInRecordList() throws Exception {

		ObjectFactory factory = ObjectFactory.getObjectFactory();
		Class<?> classType = Class.forName("hy.tel.action.TelRecordAction"); // 通过反射得到类
		Object kk = factory.buildBean("hy.tel.action.TelRecordAction", null); // 得到action实例
		Method addMethod = classType
				.getMethod("companyRecords", new Class[] {}); // 蓝色部分是test的参数类型getDataByCustmerType
		addMethod.invoke(kk, new Object[] {}); // 实参
		SpringContextUtil.getBean("telRecordAction");
		return "success";
	}*/
	public String getMicMessageToken() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		// String TOKEN = "ttsw";
		// String[] str = { TOKEN, timestamp,nonce};
		// Arrays.sort(str); // 字典序排序
		// String bigStr = str[0] + str[1] + str[2];
		// SHA1加密
		// String digest = new
		// SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();

		// 确认请求来至微信
		// if (digest.equals(signature)) {用户名错误
		response.getWriter().print(echostr);
		// }

		return null;
	}


	/**
	 * 获取个人的基本资料
	 * 调用链接：http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_getPersonalData.jspa?zsccid=&bsccid=
	 */
	public String getPersonalData(){

		String sccid=request.getParameter("bsccid");
		String zsccid=request.getParameter("zsccid");
		JSONObject temp = new JSONObject();
		StringBuffer stringBuffer=new StringBuffer();
		Boolean b=true;
		stringBuffer.append(" select cus.account,cus.sccid,cus.staffid,staff.headimage,staff.staffname,cus.custype,pp.goodsname  ");
		stringBuffer.append(" from t_eshop_cuscom cus, dt_hr_Staff staff,dt_ProductPackaging pp  where cus.staffid = staff.staffid " );
		stringBuffer.append(" and cus.cusType = pp.model and pp.type ='会员类型级别'  and cus.sccid = ?  ");
		List<Object> slist=baseBeanService.getListBeanBySqlAndParams(stringBuffer.toString(),new Object[]{sccid});
		JoinFans joinFans= (JoinFans) baseBeanService.getBeanByHqlAndParams(" from JoinFans where zsccId = ? and fsccId = ?",new Object[]{sccid,zsccid});
		if(joinFans == null){
			b=false ;
		}
		if(slist != null & slist.size() != 0){
			Object[] objects= (Object[]) slist.get(0);
			JSONObject jsonObj = new JSONObject();
			jsonObj.accumulate("account",isNull(objects[0]));
			jsonObj.accumulate("sccid",isNull(objects[1]));
			jsonObj.accumulate("staffid",isNull(objects[2]));
			jsonObj.accumulate("headimage",isNull(objects[3]));
			jsonObj.accumulate("staffName",isNull(objects[4]));
			jsonObj.accumulate("custype",isNull(objects[5]));
			jsonObj.accumulate("custypeName",objects[6]);
			jsonObj.accumulate("guanxi",b);
			temp.accumulate("list",jsonObj);
		}else {
			temp.accumulate("list","1");
		}

		result=temp;
		return "success";
	}


	// ///////////////////////////////粉丝客户会员开始////////////////////////////////////////////////////



	/**
	 * 查询个人粉丝，公司粉丝
	 * @param
	 * @user用户名 15210904250
	 * @type类别 01:个人,02:公司
	 * @parameter模糊查询条件 名字或者电话
	 * 调用链接：http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_findFansList.jspa?user=15210904250&type=02
	 * @return
	 */
	public String findFansList() {

		if(type==null){
			result="参数[type]必须传递";
			return Action.SUCCESS;
		}

		List<BaseBean> list = null;
		String hql = "";
		List<String> params = new ArrayList<String>();
		params.add(user);
		//type=01查询个人粉丝
		if (type.equals("01")) {
			hql = "from  Staff s where s.accountID = ? and exists (select c.staffID from ContactRelation c where c.staffID = s.staffID and c.relation = ?)";
			params.add("个人粉丝");
			if (parameter != null && !parameter.equals("")) {
				hql += " and (s.staffName like ? or s.reference like ?)";
				params.add("%" + parameter + "%");
				params.add("%" + parameter + "%");
			}

		} else {
			//type=02查询公司粉丝
			hql = "from ContactCompany s where s.accountID = ? and exists (select c.ccompanyID from ContactConnection c where c.ccompanyID = s.ccompanyID and c.contactConnections = ?)";
			params.add("公司粉丝");
			if (parameter != null && !parameter.equals("")) {
				hql += " and (s.companyName like ? or s.responsibleTel like ?)";
				params.add("%" + parameter + "%");
				params.add("%" + parameter + "%");
			}


		}
		list = baseBeanService.getListBeanByHqlAndParams(hql,params.toArray());


		JSONObject jsonObjList = new JSONObject();
		List<Object> lists = new ArrayList<Object>();

		if (type.equals("01")) {
			for (int i = 0; i < list.size(); i++) {
				Staff staff = (Staff) list.get(i);
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("name", isNull(staff.getStaffName()));
				jsonObj.accumulate("cusType", "8");
				jsonObj.accumulate("picpath", isNull(staff.getHeadimage()));
				lists.add(jsonObj);
			}
		}else{

			for (int i = 0; i < list.size(); i++) {
				ContactCompany company = (ContactCompany) list.get(i);
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("name", isNull(company.getCompanyName()));
				jsonObj.accumulate("cusType", "8");
				jsonObj.accumulate("picpath", isNull(company.getLogoPath()));
				lists.add(jsonObj);
			}
		}



		jsonObjList.accumulate("fanslist", lists);
		result = jsonObjList;
		return "success";
	}

	/**
	 * 查询会员列表
	 * @param @user 用户
	 * @cusType会员类别2 3 4 5 6
	 * @type 01:个人,02:公司
	 * 调用链接：http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_findVipList.jspa?user=15210904250&cusType=3&type=01
	 * @return
	 */
	public String findVipList() {

		List<String> params = new ArrayList<String>();
		if(pageNumber==0){
			pageNumber=1;
		}
		String sql = "";
		try {
			if (type.equals("01")) {// 个人
				if (!cusType.equals("8")) {
					sql = "select s.staffname,t.cusType,s.headimage,t.account,s.staffPost,t.sccId from dt_hr_Staff s,T_ESHOP_CUSCOM t "
							+ "where t.staffid = s.staffid and  "
							+ "t.cusType = ? and state = ? and s.staffid in (select t.staffid from T_ESHOP_CUSCOM t where  t.cusType = ?  connect by nocycle prior t.account = t.Superioragent start with t.account = ?)";
					params.add(cusType);
					params.add("1");
					params.add(cusType);

				} else if (cusType.equals("8")) {// 粉丝
					sql = "select s.staffname,t.cusType,s.headimage,f.faccount,s.staffPost,t.sccId from dt_hr_Staff s,T_ESHOP_CUSCOM t,dtJoinFans f "
							+ "where s.staffid = t.staffid and t.account = f.faccount and t.state = ? and f.zaccount = ? ";
					params.add("1");

				}
				params.add(user);

				if (parameter != null && !parameter.equals("")) {

					sql += " and (t.account like ? or s.staffname like ? or s.staffPost like ?) ";
					params.add("%" + parameter + "%");
					params.add("%" + parameter + "%");
					params.add("%" + parameter + "%");
				}

				String sqlcount = "select count(1) "
						+ sql.substring(sql.indexOf("from"));
				pageForm = baseBeanService.getPageFormBySQL(pageNumber, 15,
						sql, sqlcount, params.toArray());
			} else if (type.equals("02")) {// 公司的
				if (!cusType.equals("8")) {
					sql = "select s.companyname,t.cusType,cc.logoPath,t.account,cc.industryType,t.sccId from dtCompany s,T_ESHOP_CUSCOM t,"
							+ "dt_ccom_com r,dtContactCompany cc "
							+ "where t.companyid = s.companyid "
							+ "and s.companyid=r.compnay_id "
							+ "and r.ccompany_id=cc.ccompanyid "
							+ " and t.cusType = ? and t.state = ? and s.companyid in (select t.companyid from T_ESHOP_CUSCOM t where t.cusType = ?  "
							+ "connect by nocycle prior t.account = t.Superioragent start with t.account = ?)";
					params.add(cusType);
					params.add("2");
					params.add(cusType);
				} else if (cusType.equals("8")) {// 粉丝
					sql = "select s.companyname,t.cusType,cc.logoPath,f.faccount,cc.industryType,t.sccId from dtCompany s,T_ESHOP_CUSCOM t,"
							+ "dt_ccom_com r,dtContactCompany cc,dtJoinFans f "
							+ "where t.companyid = s.companyid "
							+ "and s.companyid=r.compnay_id "
							+ "and r.ccompany_id=cc.ccompanyid "
							+ "and t.account=f.faccount "
							+ " and t.state = ? and f.zaccount = ?";
					params.add("2");

				}

				params.add(user);

				if (parameter != null && !parameter.equals("")) {

					sql += " and (t.account like ? or s.companyname like ? or cc.industryType like ?) ";
					params.add("%" + parameter + "%");
					params.add("%" + parameter + "%");
					params.add("%" + parameter + "%");
				}
				String sqlcount = "select count(1) "
						+ sql.substring(sql.indexOf("from"));
				pageForm = baseBeanService.getPageFormBySQL(pageNumber, 15,
						sql, sqlcount, params.toArray());
			}

			JSONObject jsonObjList = new JSONObject();
			List<Object> lists = new ArrayList<Object>();
			if (pageForm != null && pageForm.getList().size() > 0) {
				for (int i = 0; i < pageForm.getList().size(); i++) {
					Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
					JSONObject jsonObj = new JSONObject();
					jsonObj.accumulate("name", isNull(obj[0]));
					jsonObj.accumulate("cusType", isNull(obj[1]));
					jsonObj.accumulate("picpath", isNull(obj[2]));
					jsonObj.accumulate("account", isNull(obj[3]));
					jsonObj.accumulate("staffpost", isNull(obj[4]));
					jsonObj.accumulate("sccId", isNull(obj[5]));
					lists.add(jsonObj);
				}
			}
			jsonObjList.accumulate("VIPlist", lists);
			jsonObjList.accumulate("pageNumber",pageForm==null?"1":pageForm.getPageNumber());
			jsonObjList.accumulate("pageCount", pageForm==null?"0":pageForm.getPageCount());
			result = jsonObjList;
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return "success";
	}


	/**
	 *
	 * 粉丝人脉中： 参数：user cusType;2 3 4 5 6 查找vipaccout列表
	 * 调用链接：http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_findVipAccout.jspa?user=15210904250&cusType=3
	 * @return
	 * 调用方式
	 */
	public String findVipAccout() {

		StringBuffer str=new StringBuffer();
		List<String> params = new ArrayList<String>();
		String sqlcount="select count(1) from ";
		try {
			//代理商级别以上
			if (((int)Integer.parseInt(cusType))<=5) {

				str.append("with t as(");
				str.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state,t.sccId from T_ESHOP_CUSCOM t where t.cusType = ?");
				str.append(" connect by nocycle prior t.sccId = t.supperSccId");
				str.append(" start with t.sccId =?)");
				params.add(cusType);
				params.add(sccId);

				str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state,t.sccId,pp.goodsname,staff.staffid");
				str.append(" from t,dt_hr_staff staff,dt_ProductPackaging pp");
				str.append(" where staff.staffid = t.staffid and t.state = '1' and t.cusType = pp.model and pp.type ='会员类型级别'");

				str.append(" union all");
				str.append(" select t.account, t.cusType,company.companyname,cc.logopath,cc.industryType,t.state,t.sccId,pp.goodsname,r.ccompany_id");
				str.append(" from t,dt_ccom_com r ,dtcompany company,dtContactCompany cc,dt_ProductPackaging pp");
				str.append(" where t.companyid=company.companyid and company.companyid=r.compnay_id ");
				str.append(" and r.ccompany_id=cc.ccompanyid and t.state = '2' and t.cusType = pp.model and pp.type ='会员类型级别'");
				sqlcount=sqlcount+"("+str.toString()+")";
				pageForm = baseBeanService.getPageFormBySQL(pageNumber, 15, str.toString(), sqlcount, params.toArray());
			}else if(cusType.equals("6")){
				//查询VIP客户和客户
				str.append("with t as(");
				str.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state,t.sccId from T_ESHOP_CUSCOM t where (t.cusType = ? or t.cusType = ?)");

				str.append(" connect by nocycle prior t.sccId = t.supperSccId");
				str.append(" start with t.sccId =?)");
				params.add(cusType);
				params.add(String.valueOf((int)Integer.parseInt(cusType)+1));
				params.add(sccId);

				str.append(" select t.account,t.custype,staff.staffname, staff.headimage,staff.staffPost,t.state,t.sccId,pp.goodsname,staff.staffid");
				str.append(" from t, dt_hr_staff staff,dt_ProductPackaging pp");
				str.append(" where staff.staffid = t.staffid and t.state = '1'  and t.cusType = pp.model and pp.type ='会员类型级别'");
				sqlcount=sqlcount+"("+str.toString()+")";
				pageForm = baseBeanService.getPageFormBySQL(pageNumber, 15, str.toString(), sqlcount, params.toArray());
			}else if(cusType.equals("8")){
				//查询粉丝
				//查询当前用户所有的默认等级的会员
				str.append("with t as(");
				str.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state ,t.sccId from T_ESHOP_CUSCOM t,dtJoinFans f");
				str.append(" where f.fsccId = t.sccId and f.zsccId = ? and f.state='00' and f.source!='系统粉丝' and f.source!='移动粉丝')");
				params.add(sccId);

				str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state ,t.sccId,pp.goodsname,staff.staffid");
				str.append(" from t,dt_hr_staff staff,dt_ProductPackaging pp");
				str.append(" where staff.staffid = t.staffid and t.state = '1'  and t.cusType = pp.model and pp.type ='会员类型级别'");

				str.append(" union all");
				str.append(" select t.account, t.cusType,company.companyname,cc.logopath,cc.industryType,t.state ,t.sccId,pp.goodsname,r.ccompany_id");
				str.append(" from t,dt_ccom_com r ,dtcompany company,dtContactCompany cc,dt_ProductPackaging pp");
				str.append(" where t.companyid=company.companyid and company.companyid=r.compnay_id ");
				str.append(" and r.ccompany_id=cc.ccompanyid and t.state = '2'  and t.cusType = pp.model and pp.type ='会员类型级别'");
				sqlcount=sqlcount+"("+str.toString()+")";
				pageForm = baseBeanService.getPageFormBySQL(pageNumber, 15, str.toString(), sqlcount, params.toArray());
			}else if(cusType.equals("9")){
				//查询移动粉丝
				str.append("with t as (");
				str.append(" select t.account,t.cusType,t.staffid st,t.Superioragent,t.companyid,t.state,t.sccId,f.staffid sta,f.faccount,f.fsccid");
				str.append(" from T_ESHOP_CUSCOM t right join dtJoinFans f on f.fsccId = t.sccId");
				str.append(" where f.zsccId = ? and f.source = '移动粉丝')");
				params.add(sccId);

				str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state ,t.sccId,pp.goodsname,staff.staffid");
				str.append(" from t,dt_hr_staff staff,dt_ProductPackaging pp");
				str.append(" where staff.staffid = t.st and t.state = '1'  and t.cusType = pp.model and pp.type ='会员类型级别'");

				str.append(" union all");
				str.append(" select t.account, t.cusType,company.companyname,cc.logopath,cc.industryType,t.state ,t.sccId,pp.goodsname,r.ccompany_id");
				str.append(" from t,dt_ccom_com r ,dtcompany company,dtContactCompany cc,dt_ProductPackaging pp");
				str.append(" where t.companyid=company.companyid and company.companyid=r.compnay_id ");
				str.append(" and r.ccompany_id=cc.ccompanyid and t.state = '2' and t.cusType = pp.model and pp.type ='会员类型级别'");

				str.append(" union all");
				str.append(" select t.faccount,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state ,t.sccId,t.fsccId,staff.staffid");
				str.append(" from dt_hr_staff staff,t where staff.staffid = t.sta");

				sqlcount=sqlcount+"("+str.toString()+")";
				pageForm = baseBeanService.getPageFormBySQL(pageNumber, 15, str.toString(), sqlcount, params.toArray());
			}else if(cusType.equals("10")){
				//查询移动粉丝
				str.append("with t as (");
				str.append(" select t.account,t.cusType,t.staffid st,t.Superioragent,t.companyid,t.state,t.sccId,f.staffid sta,f.faccount,f.fsccid");
				str.append(" from T_ESHOP_CUSCOM t right join dtJoinFans f on f.fsccId = t.sccId");
				str.append(" where f.zsccId = ? and f.source = '系统粉丝')");
				params.add(sccId);

				str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state ,t.sccId,pp.goodsname,staff.staffid");
				str.append(" from t,dt_hr_staff staff,dt_ProductPackaging pp");
				str.append(" where staff.staffid = t.st and t.state = '1'  and t.cusType = pp.model and pp.type ='会员类型级别'");

				str.append(" union all");
				str.append(" select t.account, t.cusType,company.companyname,cc.logopath,cc.industryType,t.state ,t.sccId,pp.goodsname,r.ccompany_id");
				str.append(" from t,dt_ccom_com r ,dtcompany company,dtContactCompany cc,dt_ProductPackaging pp");
				str.append(" where t.companyid=company.companyid and company.companyid=r.compnay_id ");
				str.append(" and r.ccompany_id=cc.ccompanyid and t.state = '2' and t.cusType = pp.model and pp.type ='会员类型级别'");

				str.append(" union all");
				str.append(" select t.faccount,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state ,t.sccId,t.fsccid,staff.staffid");
				str.append(" from dt_hr_staff staff,t where staff.staffid = t.sta");

				sqlcount=sqlcount+"("+str.toString()+")";
				pageForm = baseBeanService.getPageFormBySQL(pageNumber, 15, str.toString(), sqlcount, params.toArray());
			}else if("15".equals(cusType)){
				String sql = "select ec.account,15,hs.staffname,hs.headimage,hs.staffPost, ec.state,ec.sccId,cast('购物卡' as varchar(10)) as rem, hs.staffid"
						+" from dt_giftcards gc left join t_eshop_cuscom ec on ec.staffid = gc.staffid and ec.state = ?" +
						" left join dt_hr_staff hs on hs.staffid = gc.staffid" +
						" where gc.operator in (select ec.staffid from t_eshop_cuscom ec where ec.sccid  = ?)";
				pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 15, sql, "select count(*) from ("+sql+")", new Object[]{"1",sccId});
			}

		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		if(pageForm!=null&&pageForm.getList().size()>0){
			JSONObject jsonObjList = new JSONObject();
			List<Object> lists = new ArrayList<Object>();
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("account", isNull(obj[0]));
				jsonObj.accumulate("cusType", isNull(obj[1]));
				jsonObj.accumulate("name", isNull(obj[2]));
				jsonObj.accumulate("picpath",isNull( obj[3]));
				jsonObj.accumulate("staffpost",isNull( obj[4]));
				jsonObj.accumulate("state",isNull( obj[5]));
				jsonObj.accumulate("sccId",isNull( obj[6]));
				jsonObj.accumulate("cusTypeName",isNull( obj[7]));
				jsonObj.accumulate("ccompanyid",isNull(obj[8]));
				lists.add(jsonObj);
			}
			jsonObjList.accumulate("VIPlist", lists);
			jsonObjList.accumulate("pageNumber",pageForm.getPageNumber());
			jsonObjList.accumulate("pageCount", pageForm.getPageCount());
			result = jsonObjList;
		}

		return "success";
	}

	/**
	 * 根据当前会员查询下级及其会员数量，以及第一个默认展示列表
	 * @param
	 * @user会员
	 * @cusType 3:合伙人商城业主会员,4:微分金商城业主会员,5:代理商商城业主会员(6:vip客户,7客户),8:粉丝好友
	 * 调用链接：http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_findVipNum.jspa?user=15210904250
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findVipNum() {

		String hql = "from ProductPackaging s where s.type=?";
		List<BaseBean>  viplist =  baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{"会员类型级别"});
		Map<String,String> namemap = new HashMap<String,String>();
		for (int i = 0; i < viplist.size(); i++) {
			ProductPackaging pp = (ProductPackaging) viplist.get(i);
			namemap.put(pp.getModel(),pp.getGoodsName());

		}
		TEshopCusCom tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom d where d.sccId=?",new Object[] { sccId });

		List<List<String>> listt = new ArrayList<List<String>>();
		try {
			List<String> params = new ArrayList<String>();
			params.add(sccId);

			String sql = "select t.account,t.cusType,t.staffid,t.sccId from T_ESHOP_CUSCOM t  connect by nocycle prior t.sccid = t.supperSccId start with t.sccid = ?";
			List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql,params.toArray());


			String fanshql = "from JoinFans f where f.zsccId = ? and f.source!='移动粉丝' and f.source!='系统粉丝' and state='00'";
			List<BaseBean> fanslist = baseBeanService.getListBeanByHqlAndParams(fanshql,params.toArray());

			String syshql = "from JoinFans jf where jf.zsccId=? and jf.source='系统粉丝' and jf.state='00'";
			List<BaseBean> syslist = baseBeanService.getListBeanByHqlAndParams(syshql, new Object[]{sccId});

			String telsql = "from JoinFans jf where jf.zsccId=? and jf.source='移动粉丝' and jf.state='00'";
			List<BaseBean> tellist = baseBeanService.getListBeanByHqlAndParams(telsql, new Object[]{sccId});

			int a = 0;
			int b = 0;
			int c = 0;
			int d = 0;
			int e= 0;//客户+VIP客户
			int f= 0;//粉丝
			int x = 0;//系统粉丝
			int t = 0;//移动粉丝

			//2-7会员个数
			for (int i = 0; i < list.size(); i++) {

				Object[] obj = (Object[]) (Object) list.get(i);

				if (obj[1].equals("2")) {
					a++;
				}
				if (obj[1].equals("3")) {
					b++;
				}
				if (obj[1].equals("4")) {
					c++;
				}
				if (obj[1].equals("5")) {
					d++;
				}
				if (obj[1].equals("6")||obj[1].equals("7")) {
					e++;
				}

			}
			//粉丝个数
			f=fanslist.size();
			//系统粉丝个数
			x=syslist.size();
			//移动粉丝个数
			t=tellist.size();

			//根据当前用户等级统计类型和会员等级
			String cusType = tcc.getCusType();
			log.error("cusType："+ cusType);
			List<String> temp = new ArrayList<String>();


			if (cusType.equals("6")||cusType.equals("7")) {
				//等级会员VIP客户或者普通客户 只显示粉丝
				temp = new ArrayList<String>();
				temp.add(namemap.get("8"));
				temp.add(f+"");//粉丝数
				temp.add("8");//临时当做8
				listt.add(temp);

				temp = new ArrayList<String>();
				temp.add(namemap.get("9"));
				temp.add(t+"");//移动粉丝数
				temp.add("9");//临时当做9
				listt.add(temp);

				temp = new ArrayList<String>();
				temp.add(namemap.get("10"));
				temp.add(x+"");//系统粉丝数
				temp.add("10");//临时当做10
				listt.add(temp);
			} if (cusType.equals("5")) {
				//等级会员是代理商 显示 客户(普通客户+VIP客户)，粉丝
				temp= new ArrayList<String>();
				temp.add(namemap.get("6"));
				temp.add(e+ "");
				temp.add("6");
				listt.add(temp);

				temp = new ArrayList<String>();
				temp.add(namemap.get("8"));
				temp.add(f+"");//粉丝数
				temp.add("8");//临时当做8
				listt.add(temp);

				temp = new ArrayList<String>();
				temp.add(namemap.get("9"));
				temp.add(t+"");//移动粉丝数
				temp.add("9");//临时当做9
				listt.add(temp);

				temp = new ArrayList<String>();
				temp.add(namemap.get("10"));
				temp.add(x + "");// 系统粉丝数
				temp.add("10");// 临时当做10
				listt.add(temp);
			}else if (cusType.equals("4")) {
				//等级会员是微分金显示 代理商商城业主会员 、客户(普通客户+VIP客户)，粉丝
				temp= new ArrayList<String>();
				temp.add(namemap.get("5"));
				temp.add(d + "");
				temp.add("5");
				listt.add(temp);

				temp= new ArrayList<String>();
				temp.add(namemap.get("6"));
				temp.add(e+ "");
				temp.add("6");
				listt.add(temp);

				temp = new ArrayList<String>();
				temp.add(namemap.get("8"));
				temp.add(f+"");//粉丝数
				temp.add("8");//临时当做8
				listt.add(temp);

				temp = new ArrayList<String>();
				temp.add(namemap.get("9"));
				temp.add(t+"");//移动粉丝数
				temp.add("9");//临时当做9
				listt.add(temp);

				temp = new ArrayList<String>();
				temp.add(namemap.get("10"));
				temp.add(x+"");//系统粉丝数
				temp.add("10");//临时当做10
				listt.add(temp);
			}else if (cusType.equals("3")) {
				//等级会员是合伙创业 显示微分金、 代理商商城业主会员 、客户(普通客户+VIP客户)，粉丝
				temp= new ArrayList<String>();
				temp.add(namemap.get("4"));
				temp.add(c + "");
				temp.add("4");
				listt.add(temp);

				temp= new ArrayList<String>();
				temp.add(namemap.get("5"));
				temp.add(d + "");
				temp.add("5");
				listt.add(temp);

				temp= new ArrayList<String>();
				temp.add(namemap.get("6"));
				temp.add(e+ "");
				temp.add("6");
				listt.add(temp);

				temp = new ArrayList<String>();
				temp.add(namemap.get("8"));
				temp.add(f+"");//粉丝数
				temp.add("8");//临时当做8
				listt.add(temp);

				temp = new ArrayList<String>();
				temp.add(namemap.get("9"));
				temp.add(t+"");//移动粉丝数
				temp.add("9");//临时当做9
				listt.add(temp);

				temp = new ArrayList<String>();
				temp.add(namemap.get("10"));
				temp.add(x+"");//系统粉丝数
				temp.add("10");//临时当做10
				listt.add(temp);
			}else if (cusType.equals("2")) {
				//等级会员是公司会员 显示合伙创业商城业主会员微分金、 代理商商城业主会员 、客户(普通客户+VIP客户)，粉丝
				temp = new ArrayList<String>();
				temp.add(namemap.get("3"));
				temp.add(b + "");
				temp.add("3");
				listt.add(temp);

				temp= new ArrayList<String>();
				temp.add(namemap.get("4"));
				temp.add(c + "");
				temp.add("4");
				listt.add(temp);

				temp= new ArrayList<String>();
				temp.add(namemap.get("5"));
				temp.add(d + "");
				temp.add("5");
				listt.add(temp);

				temp= new ArrayList<String>();
				temp.add(namemap.get("6"));
				temp.add(e+ "");
				temp.add("6");
				listt.add(temp);

				temp = new ArrayList<String>();
				temp.add(namemap.get("8"));
				temp.add(f+"");//粉丝数
				temp.add("8");//临时当做8
				listt.add(temp);

				temp = new ArrayList<String>();
				temp.add(namemap.get("9"));
				temp.add(t+"");//移动粉丝数
				temp.add("9");//临时当做9
				listt.add(temp);

				temp = new ArrayList<String>();
				temp.add(namemap.get("10"));
				temp.add(x+"");//系统粉丝数
				temp.add("10");//临时当做10
				listt.add(temp);
			}else if (cusType.equals("0")) {
				//等级会员是税务 显示公司 合伙创业商城业主会员微分金、 代理商商城业主会员 、客户(普通客户+VIP客户)，粉丝
				temp = new ArrayList<String>();
				temp.add(namemap.get("2"));
				temp.add(a+ "");
				temp.add("2");
				listt.add(temp);

				temp = new ArrayList<String>();
				temp.add(namemap.get("3"));
				temp.add(b + "");
				temp.add("3");
				listt.add(temp);

				temp= new ArrayList<String>();
				temp.add(namemap.get("4"));
				temp.add(c + "");
				temp.add("4");
				listt.add(temp);

				temp= new ArrayList<String>();
				temp.add(namemap.get("5"));
				temp.add(d + "");
				temp.add("5");
				listt.add(temp);

				temp= new ArrayList<String>();
				temp.add(namemap.get("6"));
				temp.add(e+ "");
				temp.add("6");
				listt.add(temp);

				temp = new ArrayList<String>();
				temp.add(namemap.get("8"));
				temp.add(f+"");//粉丝数
				temp.add("8");//临时当做8
				listt.add(temp);

				temp = new ArrayList<String>();
				temp.add(namemap.get("9"));
				temp.add(t+"");//移动粉丝数
				temp.add("9");//临时当做9
				listt.add(temp);

				temp = new ArrayList<String>();
				temp.add(namemap.get("10"));
				temp.add(x+"");//系统粉丝数
				temp.add("10");//临时当做10
				listt.add(temp);
			}
			int count = baseBeanService.getConutByBySqlAndParams("select count(*) from dt_giftcards gc where gc.operator in (select ec.staffid from t_eshop_cuscom ec where ec.sccid  = ?)",new Object[]{sccId});
			temp = new ArrayList<String>();
			temp.add("办理购物卡");
			temp.add(count+"");//开通购物卡数
			temp.add("15");//临时当做15
			listt.add(temp);
			temp=null;

			int count1 = baseBeanService.getConutByBySqlAndParams("select count(*) from DT_BManager where type='00' and staffID = ?",new Object[]{tcc.getStaffid()});

			temp = new ArrayList<String>();
			temp.add("周边粉丝");
			temp.add(count1+"");
			temp.add("16");
			listt.add(temp);



			//查询默认
			List<String> paramsdefault = new ArrayList<String>();
			int cusTypeint= Integer.parseInt(cusType);
			String defaultcusType="";
			if(cusTypeint==0){
				defaultcusType = (cusTypeint + 2) + "";
			}else{
				defaultcusType = (cusTypeint + 1) + "";
			}
			StringBuffer str=new StringBuffer();
			//代理商级别以上
			if (!defaultcusType.equals("8")&&!defaultcusType.equals("7")&&!defaultcusType.equals("6")) {
				//查询当前用户所有的默认等级的会员
				str.append("with t as(");
				str.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state ,t.sccId from T_ESHOP_CUSCOM t where t.cusType = ?");
				str.append(" connect by nocycle prior t.sccId = t.supperSccId");
				str.append(" start with t.sccid =?)");
				paramsdefault.add(defaultcusType);
				paramsdefault.add(tcc.getSccId());

				str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost ,t.state,t.sccId,pp.goodsname");
				str.append(" from t,dt_hr_staff staff,dt_ProductPackaging pp");
				str.append(" where staff.staffid = t.staffid and t.state = '1'");

				str.append(" union all");
				str.append(" select t.account, t.cusType,company.companyname,cc.logopath,cc.industryType,t.state,t.sccId,pp.goodsname");
				str.append(" from t,dt_ccom_com r ,dtcompany company,dtContactCompany cc,dt_ProductPackaging pp");
				str.append(" where t.companyid=company.companyid and company.companyid=r.compnay_id ");
				str.append(" and r.ccompany_id=cc.ccompanyid and t.state = '2' and t.cusType = pp.model and pp.type ='会员类型级别'");

			}else if(defaultcusType.equals("6")){
				//查询VIP客户和客户
				str.append("with t as(");
				str.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state ,t.sccId from T_ESHOP_CUSCOM t where (cusType = ? or cusType = ?)");

				str.append(" connect by nocycle prior t.sccid = t.supperSccId");
				str.append(" start with t.sccid =?)");
				paramsdefault.add("6");
				paramsdefault.add("7");
				paramsdefault.add(tcc.getSccId());


				str.append(" select t.account,t.custype,staff.staffname, staff.headimage,staff.staffPost,t.state,t.sccId,pp.goodsname");
				str.append(" from t, dt_hr_staff staff");
				str.append(" where staff.staffid = t.staffid and t.state = '1' and t.cusType = pp.model and pp.type ='会员类型级别'");


			}else if(defaultcusType.equals("7")||defaultcusType.equals("8")){
				//查询粉丝
				//查询当前用户所有的默认等级的会员
				str.append("with t as(");
				str.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state,t.sccId from T_ESHOP_CUSCOM t,dtJoinFans f");
				str.append(" where f.fsccid = t.sccid and f.zsccid = ?)");
				paramsdefault.add(tcc.getSccId());

				str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost ,t.state,t.sccId,pp.goodsname");
				str.append(" from t,dt_hr_staff staff,dt_ProductPackaging pp");
				str.append(" where staff.staffid = t.staffid and t.state = '1' and t.cusType = pp.model and pp.type ='会员类型级别'");

				str.append(" union all");
				str.append(" select t.account, t.cusType,company.companyname,cc.logopath,cc.industryType,t.state,t.sccId,pp.goodsname");
				str.append(" from t,dt_ccom_com r ,dtcompany company,dtContactCompany cc,dt_ProductPackaging pp");
				str.append(" where t.companyid=company.companyid and company.companyid=r.compnay_id ");
				str.append(" and r.ccompany_id=cc.ccompanyid and t.state = '2' and t.cusType = pp.model and pp.type ='会员类型级别'");

			}

			JSONObject jo = new JSONObject();
			jo.accumulate("countlist", listt);
			result = jo;
		} catch (NumberFormatException e) {
			logger.error("操作异常", e);
		}

		return "success";
	}

	// 切换平台
	@SuppressWarnings("unchecked")
	public String toChangePlatform() {
		String staffid = request.getParameter("staffid");
		String hql = "select tec.sccid,tec.custype,tec.ppid,p.goodsname,p.type,pp.goodsname vipname,tec.acquiesce from t_eshop_cuscom tec left join dt_productpackaging pp on pp.model = tec.custype left join dt_productpackaging p"
				+ " on tec.ppid=p.ppid where tec.staffid=? and pp.type=?";
		List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(hql,
				new Object[] { staffid, "会员类型级别" });
		List<Object> lists = new ArrayList<Object>();
		for (int i = 0; i < list.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			Object[] obj = (Object[]) (Object) list.get(i);
			jsonObj.accumulate("sccId", isNull(obj[0]));
			jsonObj.accumulate("platformName", isNull(obj[3]));
			jsonObj.accumulate("cusTypeName", isNull(obj[5]));
			jsonObj.accumulate("acquiesce", isNull(obj[6]));
			lists.add(jsonObj);
		}
		JSONObject jsonObjList = new JSONObject();
		jsonObjList.accumulate("platformLists", lists);
		result = jsonObjList;
		return Action.SUCCESS;
	}

	public String changePlatform() {
		List<BaseBean> list = new ArrayList<BaseBean>();
		String sccid = request.getParameter("sccid");
		TEshopCusCom cuscom = (TEshopCusCom) baseBeanService
				.getBeanByHqlAndParams("from TEshopCusCom where sccId=?",
						new Object[] { sccid });
		cuscom.setAcquiesce("00");
		list.add(cuscom);
		TEshopCusCom cuscom1 = (TEshopCusCom) baseBeanService
				.getBeanByHqlAndParams("from TEshopCusCom where sccId=?",
						new Object[] { sccId });
		cuscom1.setAcquiesce("01");
		list.add(cuscom1);
		baseBeanService.executeHqlsByParamsList(list, null, null);
		return Action.SUCCESS;
	}


	/**
	 * 传入参数：user type:01个人,02公司 保存fans 公司fans ;个人fans
	 * 调用链接：http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_saveFans.jspa?user=15210904250&type=01&cstaff.staffName=刘莉&cstaff.reference=15210903250
	 * http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_saveFans.jspa?user=15210904250&type=02&contactCompany.staffName=北京百度公司&contactCompany.reference=15210903251
	 * @return
	 */
	public String saveFans() {
		JSONObject jsonObjList = new JSONObject();
		try {
			List<BaseBean> beanlist = new ArrayList<BaseBean>();
			String hql = "from Staff s where exists(select t.staffid from TEshopCustomer t where t.account = ? and s.staffID = t.staffid)";

			Staff cust = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { user });
			String comID = findCompanyVip(user);// 当前会员所在
			String hqlc = "from Company where companyID = ?";
			Company com = (Company) baseBeanService.getBeanByHqlAndParams(hqlc,
					new Object[] { comID });

			if (type.equals("01")) {
				String phql = "select count(*) from Staff ";
				int pcount = baseBeanService.getConutByByHqlAndParams(phql,
						null);
				cstaff.setStaffCode("NO" + pcount);
				cstaff.setRecordCode("NO" + pcount);
				cstaff.setVerifyTime(new Date());
				cstaff.setStaffID(serverService.getServerID("cstaff"));
				cstaff.setGroupCompanySn(com == null ? "" : com
						.getGroupCompanySn());
				cstaff.setStaffStatus("00");
				cstaff.setAccountID(user);
				cstaff.setAccountName(cust.getStaffName());

				beanlist.add(cstaff);
				ContactRelation conRelation = new ContactRelation();
				conRelation.setCompanyID(comID);
				conRelation.setRelationID(serverService
						.getServerID("contactrelation"));
				conRelation.setRelation("个人粉丝");
				conRelation.setStaffID(cstaff.getStaffID());
				beanlist.add(conRelation);

			} else {
				// 公司
				contactCompany.setCcompanyID(serverService
						.getServerID("contactCompany"));
				contactCompany.setAccountID(user);
				contactCompany.setAccountName(cust.getStaffName());
				beanlist.add(contactCompany);

				ContactConnection cc = new ContactConnection();
				cc.setCcompanyID(contactCompany.getCcompanyID());
				cc.setContactConnectionID(serverService.getServerID("connID"));
				cc.setCompanyID(comID);
				cc.setContactConnections("公司粉丝");
				beanlist.add(cc);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beanlist, null,
					null);
			jsonObjList.accumulate("result", "success");
		} catch (Exception e) {
			jsonObjList.accumulate("result", "fail");
			logger.error("操作异常", e);
		}
		result = jsonObjList;
		return "success";

	}
	/**
	 *
	 * 找到一个用户所关联的公司
	 * @param user
	 * @return
	 */
	private String findCompanyVip(String user) {
		String hql = "from TEshopCusCom where account = ? and logOff=0";
		TEshopCusCom tcc = (TEshopCusCom) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { user });
		if (tcc != null) {
			if (tcc.getCompanyId() != null) {
				return tcc.getCompanyId();
			} else {

				if (tcc.getCusType().equals("2")) {
					return tcc.getCompanyId();
				} else {
					return findCompanyVip(tcc.getSuperioragent());
				}
			}
		} else {
			return "";
		}

	}

	/**
	 * 查询所有客户信息和粉丝和公司
	 * @param
	 *  @sccId 本人的sccId  pageNumber 页码   parameter 模糊查询条件
	 * 调用链接：http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_findAllVipAccount.jspa?sccId=15210904250&pageNumber=1&parameter=
	 * @return
	 */

	public String findAllVipAccount(){
		StringBuffer str=new StringBuffer();
		List<String> params = new ArrayList<String>();

		String sqlcount="select count(1) from ";

		try {

			str.append(" with t as (select t.account,t.cusType,t.staffid,t.Superioragent,t.companyid,t.state,t.sccId from T_ESHOP_CUSCOM t ");
			str.append("  where t.sccId ! = ? ");
			params.add(sccId);

			str.append(" connect by nocycle prior t.account = t.Superioragent start with t.sccId = ? ) ");
			params.add(sccId);

			str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state,t.sccId from t, dt_hr_staff staff ");
			str.append(" where staff.staffid = t.staffid  ");

			if(parameter != null && !parameter.equals("")){
				str.append(" and ( t.account like ? or staff.staffname like ?  ) ");
				params.add("%"+parameter+"%");
				params.add("%"+parameter+"%");
			}
			str.append(" union ");
			str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state,t.sccId from T_ESHOP_CUSCOM t, dt_hr_Staff staff, dtjoinfans fans ");
			str.append(" where fans.fsccid = t.sccid and t.staffid = staff.staffid and fans.state='00' and fans.source != '系统粉丝' and fans.source != '移动粉丝'");
			str.append(" and fans.zsccId = ? ");

			params.add(sccId);
			if(parameter != null && !parameter.equals("")){
				str.append(" and ( t.account like ? or staff.staffname like ? ) ");
				params.add("%"+parameter+"%");
				params.add("%"+parameter+"%");
			}

			sqlcount=sqlcount+"("+str.toString()+")";
			pageForm = baseBeanService.getPageFormBySQL(pageNumber, 15, str.toString(), sqlcount, params.toArray());
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		if (pageForm == null || pageForm.getList().size() == 0) {
			JSONObject jsonObjList = new JSONObject();
			List<Object> lists = new ArrayList<Object>();
			jsonObjList.accumulate("AllVIPlist", lists);
			result = jsonObjList;
		}
		if(pageForm!=null&&pageForm.getList().size()>0){
			JSONObject jsonObjList = new JSONObject();
			List<Object> lists = new ArrayList<Object>();
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("account", isNull(obj[0]));
				jsonObj.accumulate("cusType", isNull(obj[1]));
				jsonObj.accumulate("name", isNull(obj[2]));
				jsonObj.accumulate("picpath",isNull( obj[3]));
				jsonObj.accumulate("staffpost",isNull( obj[4]));
				jsonObj.accumulate("state",isNull( obj[5]));
				jsonObj.accumulate("sccId",isNull( obj[6]));
				lists.add(jsonObj);
			}
			jsonObjList.accumulate("AllVIPlist", lists);
			jsonObjList.accumulate("pageNumber",pageForm.getPageNumber());
			jsonObjList.accumulate("pageCount", pageForm.getPageCount());
			result = jsonObjList;
		}
		return "success";
	}
	/**
	 * http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_findPhoneAll.jspa?sccId=?&pageNumber=?
	 * 个人的手机联系人
	 * sccId 本人的sccId  pageNumber 页码
	 * @return
	 */

	public String findPhoneAll(){

		StringBuffer str=new StringBuffer();
		List<String> params = new ArrayList<String>();

		String sqlcount="select count(1) from ";

		try {

			str.append(" with t as (select t.account,t.cusType,t.staffid,t.Superioragent,t.companyid,t.state,t.sccId from T_ESHOP_CUSCOM t ");
			str.append("  where t.sccId ! = ? ");
			params.add(sccId);

			str.append(" connect by nocycle prior t.account = t.Superioragent start with t.sccId = ? ) ");
			params.add(sccId);

			str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state,t.sccId,pro.goodsname from t, dt_hr_staff staff,dt_ProductPackaging pro ");
			str.append(" where staff.staffid = t.staffid and pro.model=t.custype and pro.type= '会员类型级别' ");

			str.append(" union ");
			str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state,t.sccId ,pro.goodsname from T_ESHOP_CUSCOM t, dt_hr_Staff staff, dtjoinfans fans ,dt_ProductPackaging pro ");
			str.append(" where fans.fsccid = t.sccid and t.staffid = staff.staffid and fans.state='00' and fans.source != '系统粉丝' and fans.source != '移动粉丝' and pro.model=t.custype and pro.type='会员类型级别'");
			str.append(" and fans.zsccId = ? ");

			params.add(sccId);

			sqlcount=sqlcount+"("+str.toString()+")";
			pageForm = baseBeanService.getPageFormBySQL(pageNumber, 40, str.toString(), sqlcount, params.toArray());
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		if (pageForm == null || pageForm.getList().size() == 0) {
			JSONObject jsonObjList = new JSONObject();
			List<Object> lists = new ArrayList<Object>();
			jsonObjList.accumulate("AllVIPlist", lists);
			result = jsonObjList;
		}
		if(pageForm!=null&&pageForm.getList().size()>0){
			JSONObject jsonObjList = new JSONObject();
			List<Object> lists = new ArrayList<Object>();
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("account", obj[0]);
				jsonObj.accumulate("cusType", obj[1]);
				jsonObj.accumulate("name", isNull(obj[2]));
				jsonObj.accumulate("picpath",isNull( obj[3]));
				jsonObj.accumulate("staffpost",isNull( obj[4]));
				jsonObj.accumulate("state",isNull( obj[5]));
				jsonObj.accumulate("sccId",isNull( obj[6]));
				jsonObj.accumulate("custypeName",isNull( obj[7]));
				lists.add(jsonObj);
			}
			jsonObjList.accumulate("AllVIPlist", lists);
			jsonObjList.accumulate("pageNumber",pageForm.getPageNumber());
			jsonObjList.accumulate("pageCount", pageForm.getPageCount());
			result = jsonObjList;
		}
		return "success";
	}

	/**
	 * http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_findAccount.jspa?sccId=?
	 * 查询所有客户粉丝，不分页的，每次请求500
	 * @return
	 */

	public String findAccount(){
		StringBuffer str=new StringBuffer();
		List<String> params = new ArrayList<String>();

		String sqlcount="select count(1) from ";

		try {

			str.append(" with t as (select t.account,t.cusType,t.staffid,t.Superioragent,t.companyid,t.state,t.sccId from T_ESHOP_CUSCOM t ");
			str.append("  where t.sccId ! = ? ");
			params.add(sccId);

			str.append(" connect by nocycle prior t.account = t.Superioragent start with t.sccId = ? ) ");
			params.add(sccId);

			str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state,t.sccId from t, dt_hr_staff staff ");
			str.append(" where staff.staffid = t.staffid  ");

			str.append(" union all");
			str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state,t.sccId from T_ESHOP_CUSCOM t, dt_hr_Staff staff, dtjoinfans fans ");
			str.append(" where fans.fsccid = t.sccid and t.staffid = staff.staffid and fans.state='00' and fans.source != '系统粉丝' and fans.source != '移动粉丝'");
			str.append(" and fans.zsccId = ? ");

			params.add(sccId);

			sqlcount=sqlcount+"("+str.toString()+")";
			pageForm = baseBeanService.getPageFormBySQL(1, 500, str.toString(), sqlcount, params.toArray());
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		if(pageForm!=null&&pageForm.getList().size()>0){
			JSONObject jsonObjList = new JSONObject();
			List<Object> lists = new ArrayList<Object>();
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("account", obj[0]);
				jsonObj.accumulate("cusType", obj[1]);
				jsonObj.accumulate("name", isNull(obj[2]));
				jsonObj.accumulate("picpath",isNull( obj[3]));
				jsonObj.accumulate("staffpost",isNull( obj[4]));
				jsonObj.accumulate("state",isNull( obj[5]));
				jsonObj.accumulate("sccId",isNull( obj[6]));
				lists.add(jsonObj);
			}
			jsonObjList.accumulate("AllVIPlist", lists);
			result = jsonObjList;
		}
		return "success";
	}

	/**
	 * 推送单个或所有用户  此方法提供测试使用
	 *
	 * 调用链接：http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_pushAllUser.jspa?user=?&message=?&jtype=?&body=?&id=?
	 * message  推送消息主题信息
	 * jtype	信息类型
	 * body		具体信息内容体
	 * id		业务id,业务的类型
	 * @return
	 */
	public String pushAllUser(){

		JSONObject temp = new JSONObject();
		try {
			String message = request.getParameter("message");// 推送消息主题信息
			String jtype = request.getParameter("jtype");// 信息类型
			String body = request.getParameter("body");// 具体信息内容体
			String id = request.getParameter("id");// 业务id,业务的类型
			list.add(user);
			JushMain.sendjiguangMessage(message, jtype, body, id, list);
			temp.accumulate("result", "0");
		} catch (Exception e) {
			log.error("-----连接有问题-----");
			temp.accumulate("result", "1");
		}
		result=temp;
		return Action.SUCCESS;
	}

	/**
	 * sccID 本人的  sccid
	 * user  查询资源库的手机号码
	 * 数据库查询手机号码
	 * 调用连接  http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_findPhoneFriend.jspa?sccId=TEshopCusCom20161020FGVQFFSKYC0000000187&user=15810799888
	 * @return
	 */
	public String findPhoneFriend(){
		String sccid= request.getParameter("sccId");
		//查询人手机号
		String user=request.getParameter("user");
		List<JSONObject> list1 = new ArrayList<JSONObject>();
		List<BaseBean> list=new ArrayList<BaseBean>();
		TEshopCusCom cuscom = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?",new Object[]{sccid});
		String sql ="select t.sccId,p.goodsname,t.state,t.staffid,t.account,t.companyId from t_eshop_cuscom t,dt_productpackaging p where t.account=? and t.custype=p.model and p.type='会员类型级别'";
		list = baseBeanService.getListBeanBySqlAndParams( sql, new Object[]{user});
		JSONObject temp = new JSONObject();
		if(list!=null){
			for (int i = 0; i < list.size(); i++) {
				Object[] oo = (Object[]) (Object) list.get(i);
				if(oo[2].equals("1")){
					Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?",
							new Object[] { oo[3] });
					JoinFans jf = (JoinFans)baseBeanService.getBeanByHqlAndParams("from JoinFans where zsccId=? and fsccId=?", new Object[]{cuscom.getSccId(),oo[0].toString()});
					if(staff!=null){
						if(jf!=null){
							JSONObject jo = new JSONObject();
							jo.accumulate("account", isNull(oo[4]));
							jo.accumulate("goodsname", isNull(oo[1]));
							jo.accumulate("StaffName", isNull(staff.getStaffName()));
							jo.accumulate("Headimage", isNull(staff.getHeadimage()));
							jo.accumulate("State", isNull(jf.getState()));
							jo.accumulate("sccId", isNull(oo[0]));
							list1.add(jo);
						}else{
							JSONObject jo = new JSONObject();
							jo.accumulate("account", isNull(oo[4]));
							jo.accumulate("goodsname", isNull(oo[1]));
							jo.accumulate("StaffName", isNull(staff.getStaffName()));
							jo.accumulate("Headimage", isNull(staff.getHeadimage()));
							jo.accumulate("State", isNull(oo[4]));
							jo.accumulate("sccId", isNull(oo[0]));
							list1.add(jo);
						}
					}
				}else{
					contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany t where t.ccompanyID  = (select m.ccompanyId from CcomCom m where m.comanyId = ?)",
							new Object[] { oo[5].toString()});
					JoinFans jf = (JoinFans)baseBeanService.getBeanByHqlAndParams("from JoinFans where zsccId=? and fsccId=?", new Object[]{cuscom.getSccId(),
							oo[0].toString()});
					if(contactCompany!=null){
						if(jf!=null){
							JSONObject jo = new JSONObject();
							jo.accumulate("account", isNull(oo[4]));
							jo.accumulate("goodsname", isNull(oo[1]));
							jo.accumulate("StaffName", isNull(contactCompany.getCompanyName()));
							jo.accumulate("Headimage", isNull(contactCompany.getLogoPath()));
							jo.accumulate("State", isNull(jf.getState()));
							jo.accumulate("sccId", isNull(oo[0]));
							list1.add(jo);
						}else{
							JSONObject jo = new JSONObject();
							jo.accumulate("account", isNull(oo[4]));
							jo.accumulate("goodsname", isNull(oo[1]));
							jo.accumulate("StaffName", isNull(contactCompany.getCompanyName()));
							jo.accumulate("Headimage", isNull(contactCompany.getLogoPath()));
							jo.accumulate("State", isNull(oo[4]));
							jo.accumulate("sccId", isNull(oo[0]));
							list1.add(jo);
						}
					}
				}
			}
		}

		temp.accumulate("list", list1);
		result = temp;
		return "success";
	}

	/**
	 * sccID 本人的  sccid
	 * user  好友的手机号
	 * http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_AddFriend.jspa?sccId=TEshopCusCom20161020FGVQFFSKYC0000000187&user=15810799888
	 * 申请添加好友
	 * @return
	 */
	public String AddFriend(){
		String sccid= request.getParameter("sccId");
		String user=request.getParameter("user");
		JSONObject temp = new JSONObject();
		TEshopCusCom tcuscom = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0",new Object[]{user});
		String hql ="from JoinFans where zsccId=? and fsccId=? and state=?";
		JoinFans jf = (JoinFans)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{sccid,tcuscom.getSccId(),"01"});
		if(jf==null){
			jf = new JoinFans();
			jf.setJfID(serverService.getServerID("jfID"));
			jf.setSource("搜索添加粉丝");
			jf.setState("01");
			jf.setZsccId(sccid);
			jf.setFsccId(tcuscom.getSccId());
			baseBeanService.save(jf);
		}
		temp.accumulate("result", "0");
		result=temp;
		return Action.SUCCESS;
	}

	/**
	 *
	 *   http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_ReceiveFriend.jspa?sccId=TEshopCusCom20161020FGVQFFSKYC0000000187&user=15810799888
	 * sccID 本人的  sccid
	 * user  好友的手机号
	 * 接受好友申请
	 * @return
	 */
	public String ReceiveFriend(){
		String sccid= request.getParameter("sccId");
		String user=request.getParameter("user");
		TEshopCusCom tcuscom = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0",new Object[]{user});
		JSONObject temp = new JSONObject();
		String hql ="from JoinFans where zsccId=? and fsccId=? and state=?";
		JoinFans jf = (JoinFans)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{tcuscom.getSccId(),sccId,"01"});
		jf.setState("00");
		jf.setFansDate(new Date());
		String hql1 ="from JoinFans where zsccId=? and fsccId=? and state=?";
		JoinFans jf1 = (JoinFans)baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{sccId,tcuscom.getSccId(),"00"});
		if(jf1==null){
			jf1 = new JoinFans();
			jf1.setJfID(serverService.getServerID("jfID"));
			jf1.setSource("搜索添加粉丝");
			jf1.setState("00");
			jf1.setZsccId(sccid);
			jf1.setFsccId(tcuscom.getSccId());
			jf1.setFansDate(new Date());
			baseBeanService.save(jf1);
		}
		baseBeanService.update(jf);
		temp.accumulate("result", "0");
		result=temp;
		return Action.SUCCESS;
	}

	/**
	 * http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_deleteFriend.jspa?sccId=TEshopCusCom20161020FGVQFFSKYC0000000187&user=15810799888
	 * sccID 本人的  sccid
	 * user  拒绝好友请求手机号
	 *   删除好友请求
	 * @return
	 */

	public  String  deleteFriend(){
		String sccid=request.getParameter("sccId");
		String user=request.getParameter("user");
		JSONObject temp = new JSONObject();
		TEshopCusCom tcuscom = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0",new Object[]{user});
		String hql ="delete JoinFans where zsccId = ? and fsccId = ? and state = ?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql},new Object[]{tcuscom.getSccId(),sccid,"01"});
		temp.accumulate("result", "0");
		result=temp;
		return Action.SUCCESS;
	}

	/**
	 * 查询本人的申请好友列表
	 * sccId  本人的sccId
	 * http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_findapplyFriend.jspa?sccId=TEshopCusCom20160927IDI67UFCG30000000409
	 * @return
	 */
	public String findapplyFriend(){
		String sccid=request.getParameter("sccId");
		List <Object>list1 = new ArrayList<Object>();
		String hql ="from JoinFans where fsccId=? and state=?";
		JSONObject temp = new JSONObject();
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{sccid,"01"});
		for (int i = 0; i < list.size(); i++) {
			JoinFans jf = (JoinFans)list.get(i);
			JSONObject jo = new JSONObject();
			//先做的单身份后续在进行修改
			String sql ="select t.sccId,p.goodsname,t.state,t.staffid,t.account,t.companyId from t_eshop_cuscom t,dt_productpackaging p where t.sccid=? and t.custype=p.model and p.type='会员类型级别'";
			Object obj = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{jf.getZsccId()});
			Object[] oo=(Object[]) obj;
			if(oo[2].equals("1")){
				Staff	staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?",
						new Object[] { oo[3]});
				if(staff!=null){
					//	list1.add(new String[]{oo[4].toString(),oo[1].toString(),staff.getStaffName(),staff.getHeadimage(),oo[0].toString()});
					jo.accumulate("account", isNull(oo[4]));
					jo.accumulate("goodsname",isNull(oo[1]) );
					jo.accumulate("StaffName", isNull(staff.getStaffName()));
					jo.accumulate("Headimage", isNull(staff.getHeadimage()));
					jo.accumulate("sccId", isNull(oo[0]));
					jo.accumulate("fans", "nofans");
					list1.add(jo);
				}
			}else{
				contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany t where t.ccompanyID  = (select m.ccompanyId from CcomCom m where m.comanyId = ?)",
						new Object[] { oo[5].toString()});
				if(contactCompany!=null){
					jo.accumulate("account", isNull(oo[4]));
					jo.accumulate("goodsname",isNull(oo[1]) );
					jo.accumulate("StaffName", isNull(contactCompany.getCompanyName()));
					jo.accumulate("Headimage", isNull(contactCompany.getLogoPath()));
					jo.accumulate("sccId", isNull(oo[0]));
					jo.accumulate("fans", "nofans");
					list1.add(jo);
				}
			}
		}

		String hql2=" from JoinFans d where fsccid= ? and length(fansdate)>0 "
				+ "order by fansdate desc";
		List<BaseBean> list2 = baseBeanService.getListBeanByHqlAndParams(hql2, new Object[]{sccid});
		for (int i = 0; i < list2.size(); i++) {
			JoinFans jf = (JoinFans)list2.get(i);
			JSONObject jo = new JSONObject();
			//先做的单身份后续在进行修改
			String sql ="select t.sccId,p.goodsname,t.state,t.staffid,t.account,t.companyId from t_eshop_cuscom t,dt_productpackaging p where t.sccid=? and t.custype=p.model and p.type='会员类型级别'";
			Object obj = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{jf.getZsccId()});
			Object[] oo=(Object[]) obj;
			if(oo != null) {
				if (oo[2].equals("1")) {
					Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?",
							new Object[]{oo[3]});
					if (staff != null) {
						//	list1.add(new String[]{oo[4].toString(),oo[1].toString(),staff.getStaffName(),staff.getHeadimage(),oo[0].toString()});
						jo.accumulate("account", isNull(oo[4]));
						jo.accumulate("goodsname", isNull(oo[1]));
						jo.accumulate("StaffName", isNull(staff.getStaffName()));
						jo.accumulate("Headimage", isNull(staff.getHeadimage()));
						jo.accumulate("sccId", isNull(oo[0]));
						jo.accumulate("fans", "fans");
						list1.add(jo);
					}
				} else {
					contactCompany = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany t where t.ccompanyID  = (select m.ccompanyId from CcomCom m where m.comanyId = ?)",
							new Object[]{oo[5].toString()});
					if (contactCompany != null) {
						jo.accumulate("account", isNull(oo[4]));
						jo.accumulate("goodsname", isNull(oo[1]));
						jo.accumulate("StaffName", isNull(contactCompany.getCompanyName()));
						jo.accumulate("Headimage", isNull(contactCompany.getLogoPath()));
						jo.accumulate("sccId", isNull(oo[0]));
						jo.accumulate("fans", "fans");
						list1.add(jo);
					}
				}
			}
		}


		temp.accumulate("flist", list1);
		result=temp;
		return Action.SUCCESS;
	}

	/**
	 * http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_findCustomer.jspa?companyid=?
	 * 查询个人公司，公司所有人
	 * @return
	 */
	public String findCustomer(){
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jobj = new JSONObject();
		String companyid = request.getParameter("companyid");
		List<String> params2 = new ArrayList<String>();
		StringBuffer str2 = new StringBuffer();
		str2.append(" select s.reference, s.staffName, s.headimage,t.postname,d.companyname, d.companyid from dtCos c, dt_hr_staff s, dtcompany d ,dt_hr_deptpost t,t_Eshop_Cuscom m");
		str2.append("   where d.companyid = c.companyid and c.status = '01' and c.cosStatus = '50' and t.depPostID = c.depPostID and c.staffid = s.staffid and s.reference is not null and m.account=s.reference");
		str2.append("  and d.companyid = ? ");
		str2.append(" union ");
		str2.append("  select s.reference, s.staffName,s.headimage,'',c.pseudo_company_name,c.companyid from t_Eshop_Cuscom c ,dt_hr_staff s  ");
		str2.append(" where c.companyid = ? and c.staffid = s.staffid  and s.reference is not null");
		params2.add(companyid);
		params2.add(companyid);
		List<BaseBean> beanlist2 = baseBeanService.getListBeanBySqlAndParams(str2.toString(), params2.toArray());
		for (int j = 0; j < beanlist2.size(); j++) {
			JSONObject jsonObj1 = new JSONObject();
			Object[] obj2 = (Object[]) (Object) beanlist2.get(j);
			jsonObj1.accumulate("account", isNull(obj2[0]));
			jsonObj1.accumulate("staffname", isNull(obj2[1]));
			jsonObj1.accumulate("headimage", isNull(obj2[2]));
			jsonObj1.accumulate("staffpost", isNull(obj2[3]));
			list.add(jsonObj1);
		}

		jobj.accumulate("companyPeople", list);
		result = jobj;
		return "success";
	}

	/**
	 * 查询个人所属公司
	 * @param  @sccid 本人sccid
	 * @return  list 个人所属公司集合
	 * http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_findConpany.jspa?
	 *
	 * @return
	 */
	public String findConpany() {
		String sccid = request.getParameter("sccId");
		StringBuffer str = new StringBuffer();
		List<String> params = new ArrayList<String>();
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jobj = new JSONObject();

		str.append("select * from(SELECT COM.COMPANYNAME,\n" +
				"       COM.COMPANYID,\n" +
				"       CUS.ACCOUNT,\n" +
				"       CT.LOGOPATH,\n" +
				"       COM.HXID,\n" +
				"       CT.COMSCALE,\n" +
				"       CT.INDUSTRYTYPE,\n" +
				"       CT.COMPANYADDR,\n" +
				"       CUS.STAFFID,\n" +
				"       CT.CCOMPANYID,ct.seqs FROM T_ESHOP_CUSCOM_COMPANY CUSC\n" +
				"LEFT JOIN  DTCOMPANY COM ON COM.COMPANYID = CUSC.COMPANYID\n" +
				"LEFT JOIN T_ESHOP_CUSCOM CUS ON CUS.SCCID = CUSC.SCCID\n" +
				"LEFT JOIN  DT_CCOM_COM CCC ON CCC.COMPNAY_ID = COM.COMPANYID\n" +
				"LEFT JOIN  DTCONTACTCOMPANY CT ON  CT.CCOMPANYID = CCC.CCOMPANY_ID\n");
		str.append("WHERE CUSC.SCCID = ? union ");
		str.append(" select com.companyname, com.companyid, cus.account, t.logopath,com.hxID,t.comScale,t.industryType,t.companyAddr,cus.staffid,t.ccompanyID ,t.seqs from T_ESHOP_CUSCOM cus, dtCompany com, dt_ccom_com d, dtcontactcompany t where cus.companyid = com.companyid ");
		str.append(" and d.ccompany_id = t.ccompanyid and com.companyid = d.compnay_id and cus.account in (select j.account  from T_ESHOP_CUSCOM j  where j.sccid  = ?) union  select com.companyname, com.companyid, ccus.account, t.logopath,com.hxID,t.comScale,t.industryType,t.companyAddr,staff.staffid,ccompanyID ,t.seqs from  dt_ccom_com d, ");
		str.append(" dtCompany com,dtcontactcompany t,dtcos cos,T_ESHOP_CUSCOM cus, dt_hr_Staff staff, T_ESHOP_CUSCOM   ccus where staff.staffid = cos.staffid and staff.reference = cus.account and cos.companyid=com.companyid  and cos.cosStatus='50' and com.companyid=d.compnay_id ");
		str.append(" and d.ccompany_id=t.ccompanyid  and com.companyid = ccus.companyid and cus.sccid = ?");
		str.append(") sc order by to_number(sc.seqs)");
		params.add(sccid);
		params.add(sccid);
		params.add(sccid);


		List<String> lists= new ArrayList<String>();
		List<BaseBean> beanlist1 = baseBeanService.getListBeanBySqlAndParams(str.toString(), params.toArray());
		for (int i = 0; i < beanlist1.size(); i++) {
			Object[] obj3 = (Object[]) (Object) beanlist1.get(i);
			if(!lists.contains(isNull(obj3[1]))){
			JSONObject jsonObj = new JSONObject();
			jsonObj.accumulate("companyname", isNull(obj3[0]));
			jsonObj.accumulate("companyid", isNull(obj3[1]));
			jsonObj.accumulate("account", isNull(obj3[2]));
			jsonObj.accumulate("logopath", isNull(obj3[3]));
			jsonObj.accumulate("hxID", isNull(obj3[4]));
			jsonObj.accumulate("comScale", isNull(obj3[5]));
			jsonObj.accumulate("industryType", isNull(obj3[6]));
			jsonObj.accumulate("companyAddr", isNull(obj3[7]));
			jsonObj.accumulate("staffID", isNull(obj3[8]));
			jsonObj.accumulate("ccompanyID", isNull(obj3[9]));
			lists.add(isNull(obj3[1]).toString());
			list.add(jsonObj);
			}
		}
		jobj.accumulate("company", list);
		result = jobj;

		return "success";
	}

	/**
	 * 查询个人所属公司
	 * @param  @sccid 本人sccid
	 * @return  list 查询公司集合
	 * http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_findCompany.jspa?
	 *
	 * @return
	 */
	public String findCompany() {
		StringBuffer str = new StringBuffer();
		List<String> params = new ArrayList<String>();
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jobj = new JSONObject();
		String search = request.getParameter("search");

		str.append(" with a as ( ");
		str.append(" select r.compnay_id,cc.jjPath,cc.brandinfo,cc.compurpose,cc.logopath,cc.ccompanyid,cc.companyName,cc.industryType,CC.COMPANYADDR");
		str.append(" from dt_ccom_com r ,dtContactCompany cc");
		str.append(" where r.ccompany_id=cc.ccompanyid and cc.webstatus='01' and cc.companyName like ?");
		str.append(" ) select a.*,cus.account,cus.STAFFID ");
		str.append(" from a left join t_eshop_cuscom cus on cus.companyid=a.compnay_id");
		str.append(" order by cus.account nulls last");

		params.add("%" + (search != null ? search : "") + "%");
		List<String> lists= new ArrayList<String>();
		List<BaseBean> beanlist1 = baseBeanService.getListBeanBySqlAndParams(str.toString(), params.toArray());

		for (int i = 0; i < beanlist1.size(); i++) {
			Object[] obj3 = (Object[]) (Object) beanlist1.get(i);
			if(!lists.contains(isNull(obj3[0]))){
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("companyname", isNull(obj3[6]));
				jsonObj.accumulate("companyid", isNull(obj3[0]));
				jsonObj.accumulate("account", isNull(obj3[9]));
				jsonObj.accumulate("logopath", isNull(obj3[4]));
				jsonObj.accumulate("industryType", isNull(obj3[7]));
				jsonObj.accumulate("companyAddr", isNull(obj3[8]));
				jsonObj.accumulate("staffID", isNull(obj3[10]));
				jsonObj.accumulate("ccompanyID", isNull(obj3[5]));
				lists.add(isNull(obj3[1]).toString());
				list.add(jsonObj);
			}
		}
		jobj.accumulate("company", list);
		result = jobj;

		return "success";
	}

	/**
	 * 查询有抽奖活动的公司
	 * @param  @sccid 本人sccid
	 * @return  list 抽奖活动公司集合
	 * @return
	 */
	public String findCompanyActivity() {
		String sccid = request.getParameter("sccId");
		StringBuffer str = new StringBuffer();
		List<String> params = new ArrayList<String>();
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jobj = new JSONObject();

		str.append("select * from(SELECT " +
				"COM.COMPANYNAME," +
				"COM.COMPANYID," +
				"(select CUS.ACCOUNT from T_ESHOP_CUSCOM CUS " +
				"where CUS.sccid = ?) ACCOUNT," +
				"A.ACTIVITY_ID," +
				"A.ACTIVITY_NAME," +
				"A.STATUS," +
				"A.starting_Time," +
				"A.end_time," +
				"CT.LOGOPATH," +
				"COM.HXID," +
				"CT.COMSCALE," +
				"CT.INDUSTRYTYPE," +
				"CT.COMPANYADDR," +
				"(select CUS.STAFFID from T_ESHOP_CUSCOM CUS " +
				"where CUS.sccid = ?) STAFFID," +
				"CT.CCOMPANYID," +
				"ct.seqs " +
				"FROM DTCOMPANY COM " +
				"LEFT JOIN  DT_CCOM_COM CCC ON CCC.COMPNAY_ID = COM.COMPANYID " +
				"LEFT JOIN  DTCONTACTCOMPANY CT ON  CT.CCOMPANYID = CCC.CCOMPANY_ID " +
				"LEFT JOIN dt_prize_activity A ON A.COMPANY_ID = COM.COMPANYID " +
				"WHERE CCC.COMPNAY_ID is not NULL " +
				"AND A.ACTIVITY_ID IS NOT NULL " +
				"AND trim(A.activity_Type) = '0' " +
				"AND trim(A.status) = '0' " +
				"AND SYSDATE >= A.starting_Time " +
				"AND SYSDATE <= A.end_time" +
				") A order by A.starting_Time desc");
		params.add(sccid);
		params.add(sccid);

		List<String> lists= new ArrayList<String>();
		List<BaseBean> beanlist1 = baseBeanService.getListBeanBySqlAndParams(str.toString(), params.toArray());
		for (int i = 0; i < beanlist1.size(); i++) {
			Object[] obj3 = (Object[]) (Object) beanlist1.get(i);
			if(!lists.contains(isNull(obj3[1]))){
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("companyname", isNull(obj3[0]));
				jsonObj.accumulate("companyid", isNull(obj3[1]));
				jsonObj.accumulate("account", isNull(obj3[2]));
				jsonObj.accumulate("activityID", isNull(obj3[3]));
				jsonObj.accumulate("activityName", isNull(obj3[4]));
				jsonObj.accumulate("startingTime", isNull(obj3[6]));
				jsonObj.accumulate("endTime", isNull(obj3[7]));
				jsonObj.accumulate("logopath", isNull(obj3[8]));
				jsonObj.accumulate("hxID", isNull(obj3[9]));
				jsonObj.accumulate("comScale", isNull(obj3[10]));
				jsonObj.accumulate("industryType", isNull(obj3[11]));
				jsonObj.accumulate("companyAddr", isNull(obj3[12]));
				jsonObj.accumulate("staffID", isNull(obj3[13]));
				jsonObj.accumulate("ccompanyID", isNull(obj3[14]));
				lists.add(isNull(obj3[1]).toString());
				list.add(jsonObj);
			}
		}
		jobj.accumulate("company", list);
		result = jobj;
		return "success";
	}

	/**
	  * 将环信群和公司关联上
	 * @return
	 */
	public String relateGroup(){
		String hxID= request.getParameter("hxID");
		String companyid=request.getParameter("companyid");
		Map<String,Object> map=new HashMap<>();

       try {
		   Company company = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?", new Object[]{companyid});
		   if (company != null) {
			   company.setHxID(hxID);
			   baseBeanService.update(company);
		   }
		   map.put("result","0");

	   }catch (Exception e){
		   map.put("result","1");
	   }
		JSONObject jo = JSONObject.fromObject(map);
	     result = jo;

		return "success";
	}


	/**
	 * 查询个人的客户
	 * @param  @sccId 本人sccid
	 *http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_findPersonalCustomer.jspa?sccId=TEshopCusCom20160927IDI67UFCG30000000409&parameter=
	 * @return
	 */
	public String findPersonalCustomer(){
		StringBuffer str=new StringBuffer();
		StringBuffer str1=new StringBuffer();
		List<String> params = new ArrayList<String>();
		List<String> params1 = new ArrayList<String>();
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jobj = new JSONObject();


		str.append("with t as(");
		str.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state,t.sccId from T_ESHOP_CUSCOM t where (t.cusType = '2' or t.cusType = '3' or t.cusType = '4' or t.cusType = '5') ");
		str.append(" connect by nocycle prior t.sccId = t.supperSccId");
		str.append(" start with t.sccId =?)");
		params.add(sccId);

		str.append(" select t.account,t.custype,staff.staffname,staff.headimage,staff.staffPost,t.state,t.sccId,pp.goodsname");
		str.append(" from t,dt_hr_staff staff,dt_ProductPackaging pp");
		str.append(" where staff.staffid = t.staffid and t.state = '1' and t.cusType = pp.model and pp.type ='会员类型级别'");
		if(parameter != null && !parameter.equals("")){
			str.append(" and ( t.account like ? or staff.staffname like ?  ) ");
			params.add("%"+parameter+"%");
			params.add("%"+parameter+"%");
		}
		str.append(" union all");
		str.append(" select t.account, t.cusType,company.companyname,cc.logopath,cc.industryType,t.state,t.sccId,pp.goodsname");
		str.append(" from t,dt_ccom_com r ,dtcompany company,dtContactCompany cc,dt_ProductPackaging pp");
		str.append(" where t.companyid=company.companyid and company.companyid=r.compnay_id ");
		str.append(" and r.ccompany_id=cc.ccompanyid and t.state = '2' and t.cusType = pp.model and pp.type ='会员类型级别'");

		if(parameter != null && !parameter.equals("")){
			str.append(" and ( t.account like ? or company.companyname like ?  ) ");
			params.add("%"+parameter+"%");
			params.add("%"+parameter+"%");
		}

		str1.append("with t as(");
		str1.append(" select t.account, t.cusType,t.staffid,t.Superioragent,t.companyid,t.state,t.sccId from T_ESHOP_CUSCOM t where (t.cusType = '6' or t.cusType = '7')");

		str1.append(" connect by nocycle prior t.sccId = t.supperSccId");
		str1.append(" start with t.sccId =?)");
		params1.add(sccId);

		str1.append(" select t.account,t.custype,staff.staffname, staff.headimage,staff.staffPost,t.state,t.sccId,pp.goodsname");
		str1.append(" from t, dt_hr_staff staff,dt_ProductPackaging pp");
		str1.append(" where staff.staffid = t.staffid and t.state = '1'  and t.cusType = pp.model and pp.type ='会员类型级别'");

		if(parameter != null && !parameter.equals("")){
			str1.append(" and ( t.account like ? or staff.staffname like ?  ) ");
			params1.add("%"+parameter+"%");
			params1.add("%"+parameter+"%");
		}

		List<BaseBean> beanlist = baseBeanService.getListBeanBySqlAndParams(str.toString(), params.toArray());
		List<BaseBean> beanlist1 = baseBeanService.getListBeanBySqlAndParams(str1.toString(), params1.toArray());
		for (int i = 0; i < beanlist.size(); i++) {
			Object[] obj = (Object[]) (Object) beanlist.get(i);;
			JSONObject jsonObj = new JSONObject();
			jsonObj.accumulate("account", isNull(obj[0]));
			jsonObj.accumulate("cusType", isNull(obj[1]));
			jsonObj.accumulate("name", isNull(obj[2]));
			jsonObj.accumulate("picpath",isNull( obj[3]));
			jsonObj.accumulate("staffpost",isNull( obj[4]));
			jsonObj.accumulate("state",isNull( obj[5]));
			jsonObj.accumulate("sccId",isNull( obj[6]));
			jsonObj.accumulate("cusTypeName",isNull( obj[7]));
			list.add(jsonObj);
		}

		for (int i = 0; i < beanlist1.size(); i++) {
			Object[] obj1 = (Object[]) (Object) beanlist1.get(i);;
			JSONObject jsonObj1 = new JSONObject();
			jsonObj1.accumulate("account", isNull(obj1[0]));
			jsonObj1.accumulate("cusType", isNull(obj1[1]));
			jsonObj1.accumulate("name", isNull(obj1[2]));
			jsonObj1.accumulate("picpath",isNull( obj1[3]));
			jsonObj1.accumulate("staffpost",isNull( obj1[4]));
			jsonObj1.accumulate("state",isNull( obj1[5]));
			jsonObj1.accumulate("sccId",isNull( obj1[6]));
			jsonObj1.accumulate("cusTypeName",isNull( obj1[7]));
			list.add(jsonObj1);
		}
		jobj.accumulate("VIPlist", list);
		result = jobj;
		return "success";
	}

	/**
	 * 查看群成员的信息
	 *http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_getInformation.jspa?slist=json数据
	 * @param @slist 群成员电话号码的json数据
	 * @return  返回群成员 信息的集合
	 */
	public String getInformation(){

		String xlist=request.getParameter("slist");
		List<Object> li=telService.getPeopleInformation(xlist);
		List<Object> list=new ArrayList<Object>();
		JSONObject jobj = new JSONObject();
		for (int j = 0; j < li.size(); j++) {
			JSONObject jsonObj1 = new JSONObject();
			Object[] obj2 = (Object[]) (Object) li.get(j);
			jsonObj1.accumulate("phoneAccount", isNull(obj2[0]));
			jsonObj1.accumulate("staffname", isNull(obj2[1]));
			jsonObj1.accumulate("headimage", isNull(obj2[2]));
			list.add(jsonObj1);
		}
		//logger.info("调试信息");
		jobj.accumulate("slist", list);
		result=jobj;
		return "success";
	}



	// ///////////////////////////////粉丝客户会员结束////////////////////////////////////////////////////

	/**
	 * 点击切换身份
	 * staffID 本人的staffID
	 * http://192.168.4.4:8080/hyplat/ea/android/sajax_ea_ChangePlatform.jspa?staffID=cstaff20151102BAVVUWKPPB0000002939
	 * @return
	 */

	public String ChangePlatform(){
		List<JSONObject> list1 = new ArrayList<JSONObject>();
		JSONObject jobj = new JSONObject();
		String staffID = request.getParameter("staffID");
		String hql = "select tec.sccid,tec.custype,p.goodsname,pp.goodsname vipname from t_eshop_cuscom tec left join dt_productpackaging pp on pp.model = tec.custype left join dt_productpackaging p" +
				" on tec.ppid=p.ppid where tec.staffid=? and pp.type=?";
		List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(hql, new Object[]{staffID,"会员类型级别"});
		for (int i = 0; i < list.size(); i++) {
			Object[] obj1 = (Object[]) (Object) list.get(i);;
			JSONObject jsonObj1 = new JSONObject();
			jsonObj1.accumulate("sccId", isNull(obj1[0]));
			jsonObj1.accumulate("cusType", isNull(obj1[1]));
			jsonObj1.accumulate("goodsName", isNull(obj1[2]));
			jsonObj1.accumulate("vipname", isNull(obj1[3]));
			list1.add(jsonObj1);
		}
		jobj.accumulate("platform", list1);
		result = jobj;
		return "success";
	}

	/**
	 * 点击切换身份
	 * sccId  需要切换的sccId
	 * sccid  未切换前的sccid
	 * http://192.168.4.4:8080/hyplat/ea/android/sajax_ea_ChangeOver.jspa?sccId=&sccid=
	 * @return
	 */
	public String ChangeOver(){
		JSONObject jsonObj = new JSONObject();
		String sccid = request.getParameter("sccid");
		String sccId = request.getParameter("sccId");
		TEshopCusCom cuscom = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{sccid});
		TEshopCusCom cuscom1 = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{sccId});
		cuscom.setAcquiesce("00");
		cuscom1.setAcquiesce("01");
		baseBeanService.update(cuscom);
		baseBeanService.update(cuscom1);
		jsonObj.accumulate("sccId", isNull(cuscom1.getSccId()));
		jsonObj.accumulate("companyid", isNull(cuscom1.getCompanyId()));
		jsonObj.accumulate("cusType", isNull(cuscom1.getCusType()));
		jsonObj.accumulate("state", isNull(cuscom1.getState()));

		ProductPackaging pp = (ProductPackaging)baseBeanService.getBeanByHqlAndParams("from ProductPackaging where model = ? and type = ?", new Object[]{cuscom1.getCusType(),"会员类型级别"});
		jsonObj.accumulate("cusTypeName", isNull(pp.getGoodsName()));//会员级别名称

		result = jsonObj;
		return "success";
	}

	/**
	 * 活动
	 * @return 字符串 flag 返回是  no 该公司没有活动
	 *         notEnough 积分不够  ；  without 该公司没有积分；notSet 没有设置签到积分；
	 *         sign 有积分，有活动 （可以进行签到活动）；end 时间活动结束 ;no exist 该公司不存在
	 */
	public String getPrizeActivity(){
		String companyId = request.getParameter("companyId");
		String isflag = "android";
		String flag = bpsi.getPrizeActivity(null,isflag,companyId);
		JSONObject temp = new JSONObject();
		temp.put("flag", flag);
		result = temp;
		return "success";
	}

	/**
	 * 快捷应用新版接口
	 * http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_getCompanyList.jspa?sccid=
	 * @return
	 */
	public String getCompanyList(){
		String sccid=request.getParameter("sccid");
		StringBuffer str=new StringBuffer();
		StringBuffer str1=new StringBuffer();
		List<String> params = new ArrayList<String>();
		JSONObject jsonObjList = new JSONObject();
		String sqlcount="select count(1) from ";
		String sqlcount1="select count(1) from ";
		List<JSONObject> slist=new ArrayList<>();
		try {
			params.add(sccid);
			str1.append( " select cp.ccompanyid, p.companyid, p.companyname,   kj.mingcidate, p.companyidentifier, p.industrytype, cp.logopath ");
			str1.append("from dtcompany  p, dt_ccom_com  co, dtcontactcompany  cp, dt_kuaijietianjia kj ");
			str1.append("  where p.companyid = co.compnay_id  and cp.ccompanyid = co.ccompany_id and kj.companyid = p.companyid and kj.sccid = ?  order by kj.mingcidate desc ");
			sqlcount=sqlcount+"("+str1.toString()+")";
			PageForm spageForm=baseBeanService.getPageFormBySQL(1, 20, str1.toString(), sqlcount, params.toArray());
/*
             str.append("  select cp.ccompanyid, p.companyid,  p.companyname, p.companyregisterdate, p.companyidentifier, p.industrytype, cp.logopath ");
             str.append("  from dtcompany p, dt_ccom_com co, dtcontactcompany cp  where p.companyid = co.compnay_id ");
             str.append(" and cp.ccompanyid = co.ccompany_id  order by p.companyregisterdate ");

             sqlcount=sqlcount+"("+str.toString()+")";
             pageForm = baseBeanService.getPageFormBySQL(1, 20, str.toString(), sqlcount, null);*/
			if(spageForm!=null&&spageForm.getList().size()>0){
				for (int i = 0; i < spageForm.getList().size(); i++) {
					Object[] obj = (Object[]) (Object) spageForm.getList().get(i);
					JSONObject jsonObj = new JSONObject();
					jsonObj.accumulate("ccompanyid", isNull(obj[0]));
					jsonObj.accumulate("companyid", isNull(obj[1]));
					jsonObj.accumulate("companyname", isNull(obj[2]));
					jsonObj.accumulate("companyregisterdate", isNull(obj[3]).toString());
					jsonObj.accumulate("companyidentifier", isNull(obj[4]));
					jsonObj.accumulate("industrytype", isNull(obj[5]));
					jsonObj.accumulate("logopath", isNull(obj[6]));
					slist.add(jsonObj);
				}
			}


       /*  if(pageForm!=null&&pageForm.getList().size()>0){
             for (int i = 0; i < pageForm.getList().size(); i++) {
                 Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
                 JSONObject jsonObj = new JSONObject();
                 jsonObj.accumulate("ccompanyid", isNull(obj[0]));
                 jsonObj.accumulate("companyid", isNull(obj[1]));
                 jsonObj.accumulate("companyname", isNull(obj[2]));
                 jsonObj.accumulate("companyregisterdate", isNull(obj[3]).toString());
                 jsonObj.accumulate("companyidentifier", isNull(obj[4]));
                 jsonObj.accumulate("industrytype", isNull(obj[5]));
                 jsonObj.accumulate("logopath", isNull(obj[6]));
                 slist.add(jsonObj);
             }
         }*/
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		for (int i = 0; i < slist.size(); i++) {
			for (int j = slist.size() -1; j >i; j--) {
				if(slist.get(j).get("companyid").equals(slist.get(i).get("companyid"))){
					slist.remove(j);
				}
			}
		}

		jsonObjList.accumulate("companyList", slist);
		result = jsonObjList;
		return "success";
	}

	/**
	 *添加快捷应用点击的排行
	 * http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_addPersonKuaiJie.jspa?sccid=?&companyid=?
	 * @return
	 */
	public String addPersonKuaiJie(){
		HttpServletRequest request = ServletActionContext.getRequest();

		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom cus = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM );
		String sccid=request.getParameter("sccid");
		if(cus!=null){
			sccid=cus.getSccId();
		}
		String companyid=request.getParameter("companyid");
		JSONObject jsonObjList = new JSONObject();
		if (sccid != null && !sccid.equals("") && companyid != null && !companyid.equals("")) {
			KuaiJieTianJia kuaijie = (KuaiJieTianJia) baseBeanService.getBeanByHqlAndParams(" from KuaiJieTianJia where sccid = ? and companyId = ?", new Object[]{sccid, companyid});
			if (kuaijie == null) {
				KuaiJieTianJia kj = new KuaiJieTianJia();
				kj.setKuaiJieId(serverService.getBillID("keyid"));
				kj.setCompanyId(companyid);
				kj.setSccid(sccid);
				kj.setMingciDate(new Date());
				baseBeanService.save(kj);
			} else {
				kuaijie.setMingciDate(new Date());
				baseBeanService.update(kuaijie);
			}
		}else {
			jsonObjList.accumulate("fanhui", "1");
			result = jsonObjList;
			return "success";
		}
		jsonObjList.accumulate("fanhui", "0");
		result = jsonObjList;
		return "success";
	}


	/**
	 *快捷删除排行关注
	 * http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_deletePersonKuaiJie.jspa?sccid=?&companyid=?
	 * @return
	 */
	public String deletePersonKuaiJie(){

		String sccid=request.getParameter("sccid");
		String companyid=request.getParameter("companyid");
		JSONObject jsonObjList = new JSONObject();
		if (sccid != null && !sccid.equals("") && companyid != null && !companyid.equals("")) {
			KuaiJieTianJia kuaijie = (KuaiJieTianJia) baseBeanService.getBeanByHqlAndParams(" from KuaiJieTianJia where sccid = ? and companyId = ?", new Object[]{sccid, companyid});
			if (kuaijie == null) {
				jsonObjList.accumulate("fanhui", "1");
				result = jsonObjList;
				return "success";
			} else {
				baseBeanService.deleteBeanByKey(KuaiJieTianJia.class,kuaijie.getKuaiJiekey());
			}
		}else {
			jsonObjList.accumulate("fanhui", "1");
			result = jsonObjList;
			return "success";
		}
		jsonObjList.accumulate("fanhui", "0");
		result = jsonObjList;
		return "success";
	}

	/**
	 * http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_souSuoCompany.jspa?companyname=?
	 *搜索快捷应用公司
	 * @return
	 */
	public String souSuoCompany(){

		String companyname=request.getParameter("companyname");
		StringBuffer str=new StringBuffer();
		List<String> params = new ArrayList<String>();
		JSONObject jsonObjList = new JSONObject();
		String sqlcount="select count(1) from ";
		List<JSONObject> lists = new ArrayList<JSONObject>();
		try {
			str.append(" select cp.ccompanyid,p.companyid,p.companyname,p.companyregisterdate,p.companyidentifier,p.industrytype,cp.logopath from dtcompany p,dt_ccom_com co,dtcontactcompany cp ");
			str.append("  where p.companyid=co.compnay_id and cp.ccompanyid=co.ccompany_id and p.companyname like ?  order by p.companyregisterdate ");
			params.add("%"+companyname+"%");
			sqlcount = sqlcount + "(" + str.toString() + ")";
			pageForm = baseBeanService.getPageFormBySQL(1, 20, str.toString(), sqlcount, params.toArray());

			if(pageForm!=null&&pageForm.getList().size()>0){
				for (int i = 0; i < pageForm.getList().size(); i++) {
					Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
					JSONObject jsonObj = new JSONObject();
					jsonObj.accumulate("ccompanyid", isNull(obj[0]));
					jsonObj.accumulate("companyid", isNull(obj[1]));
					jsonObj.accumulate("companyname", isNull(obj[2]));
					jsonObj.accumulate("companyregisterdate", isNull(obj[3].toString()));
					jsonObj.accumulate("companyidentifier", isNull(obj[4]));
					jsonObj.accumulate("industrytype", isNull(obj[5]));
					jsonObj.accumulate("logopath", isNull(obj[6]));
					lists.add(jsonObj);
				}
			}
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		jsonObjList.accumulate("companyList", lists);
		result = jsonObjList;
		return "success";
	}

	/**
	 * http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_automaticLogon.jspa
	 *自动登录
	 * @return
	 */
	public Integer automaticLogon(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw=SessionWrap.getInstance();
		TEshopCusCom cus=(TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
		String hql="from Company where companyID=?";
		Company company=(Company) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cus.getCompanyId()});
		if(null==company){
			return 1;
		}

		//判断注册公司状态是否正常
		if(!"00".equals(company.getCompanyStatus())){
			return 2;
		}
		hql="from CAccount where staffid=?";
		CAccount caccount=(CAccount) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cus.getStaffid()});
		if(null==caccount){
			return 3;
		}else{
			sw.setObject(session, SessionWrap.KEY_CACCOUNT, caccount);
		}
		//账号状态不正常
		if(!"00".equals(caccount.getAccountStatus())){
			return 4;
		}
		String sql="select relation from  dtContactRelation where staffID=? and companyID=?  ";
		List<BaseBean> contactRelation= baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{caccount.getStaffID(),company.getCompanyID()});
		if(null!=contactRelation) {
			Iterator it1 = contactRelation.iterator();
			while(it1.hasNext()){
				if ("学员".equals(it1.next())) {

					return 8;
				}
			}
		}
		String sql1="select d.postName from dtcos c left join dt_hr_deptpost d on c.depPostID = d.depPostID where c.staffID=? and d.companyID=?";
		List<BaseBean> staff=baseBeanService.getListBeanBySqlAndParams(sql1, new Object[]{caccount.getStaffID(),company.getCompanyID()});
		if(null!=staff) {
			Iterator ite = staff.iterator();
			while(ite.hasNext()){
				if ("教练".equals(ite.next())) {

					return 9;
				}
			}
		}
		return 0;
	}
	/**
	 * http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_drivingSchoolLogin.jspa?user=?&password=?
	 *驾校app登录
	 * @return
	 */
	public String drivingSchoolLogin(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw=SessionWrap.getInstance();
		JSONObject json=new JSONObject();
		Map<String,Object> map=new HashMap<>();
		TEshopCustomer teshop = null;
		String hql = "";
		String parameter = "";
		boolean third = true;
		String hqltc = "";
		TEshopCusCom tsc = null;
		String hqltype = "";

		if(eqId!=null&&!eqId.equals("")){ //QQ登陆
			hql ="from TEshopCustomer where eqId = ?";
			parameter = eqId;
			hqltc = "from TEshopCusCom where acquiesce = ? and eqId=?";
			hqltype = "from TEshopCusCom where eqId = ? order by cusType";

		}else if(eOpenId!=null&&!eOpenId.equals("")){//app微信登陆
			hql ="from TEshopCustomer where eOpenId = ?";
			parameter = eOpenId;
			hqltc = "from TEshopCusCom where acquiesce = ? and eOpenId=?";
			hqltype = "from TEshopCusCom where eOpenId = ? order by cusType";
		}else if(eBlogId!=null&&!eBlogId.equals("")){//微博登陆
			hql ="from TEshopCustomer where eBlogId = ?";
			parameter = eBlogId;
			hqltc = "from TEshopCusCom where acquiesce = ? and eBlogId=?";
			hqltype = "from TEshopCusCom where eBlogId = ? order by cusType";
		}else{
			hql="from TEshopCustomer where account=? AND logOff=0";
			parameter = user;
			third = false;
		}
		teshop=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{parameter});
		if(third){
			tsc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hqltc, new Object[]{"01", parameter});

		}


		if(null==teshop){
			map.put("returnCode","1");
			map.put("message","帐号不存在或错误");
		}else {
			TEshopCustomer tecompany = null;
			if(!third) {
				String hql2 = "from TEshopCustomer where account=? AND logOff=0 and password=?";
				tecompany = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{user, password});
			}else{
				tecompany = teshop;
			}
			if(null==tecompany){
				map.put("returnCode","2");
				map.put("message","密码错误");
			}else{
				sw.setObject(session, SessionWrap.KEY_CUSTOMER, tecompany);
				TEshopCusCom teshopCusCom = null;
				if(!third) {
					String hql3 = "from TEshopCusCom where acquiesce = ? and account=?";
					teshopCusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql3, new Object[]{"01", tecompany.getAccount()});
				}else{
					teshopCusCom = tsc;

				}
				if(null==teshopCusCom){
					List<BaseBean> beans = null;
					if(!third) {
						beans = baseBeanService.getListBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 order by cusType", new Object[]{tecompany.getAccount()});
					}else{
						beans = baseBeanService.getListBeanByHqlAndParams(hqltype,new Object[]{parameter});

					}
					teshopCusCom=(TEshopCusCom)beans.get(0);
					teshopCusCom.setAcquiesce("01");
					baseBeanService.update(teshopCusCom);
				}else{
					sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, teshopCusCom);
				}
				String sql="select d.staffid ,b.companyid,d.sccid,d.staffname,d.photo,d.staffidentitycard,b.fkstatus from dtenroll t ,dtCashierBills b,dt_hr_Staff d where  d.staffid=t.staffid and t.cashierbillsid=b.cashierbillsid and  d.staffid=?  ";
				List<BaseBean> contactRelation= baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{tecompany.getStaffid()});
				if(contactRelation.size()>=0) {
					for (Object obj : contactRelation) {
						Object[] arr = (Object[]) obj;
						map.put("staffid",isNull(arr[0]));
						map.put("companyid",isNull(arr[1]));
						map.put("sccid",isNull(arr[2]));
						map.put("staffname",isNull(arr[3]));
						map.put("photo",isNull(arr[4]));
						map.put("staffidentitycard",isNull(arr[5]));
						map.put("returnCode","4");
						map.put("message","学员");//学员


					}
				}
				String sql1="select f.staffid,c.companyid,e.sccid,f.staffname,f.photo,d.postName from dtcos c , dt_hr_deptpost d , dt_hr_Staff f ,T_ESHOP_CUSCOM e where  c.DEPPOSTID = d.deppostid and f.staffid=c.staffid and e.sccid=f.sccid and e.sccid=?";
				List<BaseBean> staff=baseBeanService.getListBeanBySqlAndParams(sql1, new Object[]{teshopCusCom.getSccId()});
				if(staff.size()>=0 ) {
					for (Object obj : staff) {
						Object[] arr = (Object[]) obj;
						map.put("staffid",isNull(arr[0]));
						map.put("companyid",isNull(arr[1]));
						map.put("sccid",isNull(arr[2]));
						map.put("staffname",isNull(arr[3]));
						map.put("photo",isNull(arr[4]));
						if("教练".equals(arr[5])||"教练员".equals(arr[5])){
							map.put("returnCode","6");
							map.put("message","教练");//教练
							break;

						}else if("运管".equals(arr[5])||"运管员".equals(arr[5])){
							map.put("returnCode","7");
							map.put("message","运管");//运管
						}else {
							map.put("returnCode","8");
							map.put("message","员工");//员工
						}
					}
				}
				if(contactRelation.size()==0&&staff.size()==0){
					map.put("sccid",isNull(teshopCusCom.getSccId()));
					map.put("returnCode","5");
					map.put("staffid",teshop.getStaffid());
					map.put("message","游客");
				}
			}
		}
		result = JSONObject.fromObject(map);
		return "success";
	}
	/**http://127.0.0.1:8080/hyplat/ea/android/sajax_ea_timingToPracticeCar.jspa
	 * 计时练车 *
	 *
	 * @return 00:未登录,01:未报名,02:未预约,03:时间未到,04:练车开始
	 */
	/*@Transactional(propagation = Propagation.NOT_SUPPORTED)*/
	public String timingToPracticeCar() {
		/*String status = "04";*/
		Object object = null;
		JSONObject json=new JSONObject();
		Map<String,Object> map=new HashMap<>();
		//判断是否登录
		/*HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom cuscom = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);*/
		if (staffidentitycard.equals(""))
		{
			map.put("status", "00");
		}
		else
		{
			//判断是否报名
			StringBuilder sb = new StringBuilder();
			sb.append("select count(*) ");
			sb.append(" from dtEnroll e, dtCashierBills s ,dt_hr_Staff f ");
			sb.append(" where f.staffidentitycard = ? and e.cashierbillsid = s.cashierbillsid and e.staffid =f.staffid ");
			sb.append(" and s.fkstatus = ? ");
			int count = baseBeanService.getConutByBySqlAndParams(sb.toString(),new Object[]{staffidentitycard,"00"});

			if (count==0)
			{
				map.put("status", "01");
				map.put("subject","");
			}
			else
			{
				//判断是否预约
				int whether = baseBeanService.getConutByBySqlAndParams("select count(*) from Tb_Elyc_Order_Record where studentNum = ? and status = ? ",new Object[]{staffidentitycard,"1"});

				if (whether==0)
				{
					map.put("status", "02");
					map.put("subject","");
				}
				else
				{
					//判断该时间是否到达练习时间
					String sql = "select subject from Tb_Elyc_Order_Record where studentNum = ? and status = ? ";
					sql+= "and to_char(sysdate, 'yyyy-mm-dd hh24:mi:ss') BETWEEN to_char(lessionStartTime, 'yyyy-mm-dd hh24:mi:ss') AND to_char(lessionEndTime, 'yyyy-mm-dd hh24:mi:ss')";
					object = baseBeanService.getObjectBySqlAndParams(sql,new Object[]{staffidentitycard,"1"});

					if (object==null)
					{
						map.put("status","03");
						map.put("subject","");
					}
					else
					{
						map.put("status", "04");
						map.put("subject",object);
					}
				}
			}
		}
		result=JSONObject.fromObject(map) ;
		return "success";
	}

	public String getEqId() {
		return eqId;
	}

	public void setEqId(String eqId) {
		this.eqId = eqId;
	}

	public String geteOpenId() {
		return eOpenId;
	}

	public void seteOpenId(String eOpenId) {
		this.eOpenId = eOpenId;
	}

	public String geteBlogId() {
		return eBlogId;
	}

	public void seteBlogId(String eBlogId) {
		this.eBlogId = eBlogId;
	}

	//定位后更新数据库信息

	public String updatePosInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		if(staffid!=null&&longitude!=null&&latitude!=null&&!staffid.equals("")&&!longitude.equals("")&&!latitude.equals("")){
			try {

				HttpServletRequest request = ServletActionContext.getRequest();
				String hql = "from StaffPosInfo where staffID = ? and source = ?";
				StaffPosInfo staffPosInfo = (StaffPosInfo) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{staffid, "wfjapp"});
				if (staffPosInfo == null) {
					staffPosInfo = new StaffPosInfo();
					staffPosInfo.setSpiID(serverService.getServerID("spiid"));
					staffPosInfo.setPosDate(new Date());
					staffPosInfo.setLongitude(new BigDecimal(longitude));
					staffPosInfo.setLatitude(new BigDecimal(latitude));
					staffPosInfo.setSource("wfjapp");
					staffPosInfo.setStaffID(staffid);
					staffPosInfo.setIp(IPUtil.getIpAddr(request));
				} else {
					staffPosInfo.setPosDate(new Date());
					staffPosInfo.setLongitude(new BigDecimal(longitude));
					staffPosInfo.setLatitude(new BigDecimal(latitude));
					staffPosInfo.setIp(IPUtil.getIpAddr(request));

				}
				baseBeanService.update(staffPosInfo);
				map.put("result", 0);//成功
			} catch (Exception e) {
				logger.error("操作异常", e);
				map.put("result", 1);//失败

			}
		}else {
			map.put("result", 1);//失败
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return "success";
	}


	/**
	 * 分享列表
	 *
	 * @return
	 */
	public String memberShare() {
		if(pageNumber == 0){
			pageNumber = 1;
		}
		pageForm = scManageService.memberShare(pageNumber, 10, sccId,companyID);
		JSONObject jsonObjList = new JSONObject();
		List<Object> lists = new ArrayList<Object>();
		if (pageForm != null && pageForm.getList().size() > 0) {
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("goodsname", isNull(obj[0]));
				jsonObj.accumulate("image", isNull(obj[1]));
				jsonObj.accumulate("ppid", isNull(obj[2]));
				jsonObj.accumulate("goodsid", isNull(obj[3]));
				jsonObj.accumulate("ck", isNull(obj[7]));
				jsonObj.accumulate("dz", isNull(obj[6]));
				jsonObj.accumulate("pl", isNull(obj[5]));
				lists.add(jsonObj);
			}
		}

		jsonObjList.accumulate("sharelist", lists);
		jsonObjList.accumulate("pageNumber",pageForm==null?"1":pageForm.getPageNumber());
		jsonObjList.accumulate("pageCount", pageForm==null?"0":pageForm.getPageCount());
		result = jsonObjList;
		return "success";
	}



	/**
	 * 分享全部列表
	 *
	 * @return
	 */
	public String allShare() {
		if(pageNumber == 0){
			pageNumber = 1;
		}
		pageForm = scManageService.allShare(pageNumber, 10, parameter,staffid);
		JSONObject jsonObjList = new JSONObject();
		List<Object> lists = new ArrayList<Object>();
		if (pageForm != null && pageForm.getList().size() > 0) {
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("goodsname", isNull(obj[0]));
				jsonObj.accumulate("date", isNull(obj[1]));
				jsonObj.accumulate("image", isNull(obj[2]));
				jsonObj.accumulate("ppid", isNull(obj[3]));
				jsonObj.accumulate("type", isNull(obj[4]));
				jsonObj.accumulate("ccompanyId", isNull(obj[5]));
				jsonObj.accumulate("author", isNull(obj[6]));
				lists.add(jsonObj);
			}
		}

		jsonObjList.accumulate("sharelist", lists);
		jsonObjList.accumulate("pageNumber",pageForm==null?"1":pageForm.getPageNumber());
		jsonObjList.accumulate("pageCount", pageForm==null?"0":pageForm.getPageCount());
		result = jsonObjList;
		return "success";
	}
	//刷脸获取凭证

	public String getWxpayfaceAuthinfo(){
		String rawdata = request.getParameter("rawdata");
		String store_id = request.getParameter("store_id");
		String store_name = request.getParameter("store_name");
		String device_id = request.getParameter("device_id");

		WxpayFaceAuth mp = new WxpayFaceAuth();
		mp.setRawdata(rawdata);
		mp.setStore_id(store_id);
		mp.setStore_name(store_name);
		mp.setDevice_id(device_id);

		Object obj = faceDeviceSerivce.getWXData(device_id);
		Object[] objs = (Object[])obj;
		mp.setSub_appid(objs[0].toString());
		mp.setSub_mch_id(objs[1].toString());
		String authinfo = WeixinUtil.getWxpayfaceAuthinfo(mp);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("authinfo",authinfo);
		map.put("subAppID",objs[0].toString());
		map.put("subMchid",objs[1].toString());
		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return "success";

	}

	//发起刷俩支付

	public String wxpayface(){
		String openid = request.getParameter("openid");
		String face_code = request.getParameter("face_code");
		String comID = request.getParameter("comID");
		String store_name = request.getParameter("store_name");
		String out_trade_no = request.getParameter("out_trade_no");//商户订单号
		String total_fee = request.getParameter("total_fee"); //金额 单位 分
		String device_id = request.getParameter("device_id");

		WxpayFace mp = new WxpayFace();
		mp.setOpenid(openid);
		mp.setFace_code(face_code);
		mp.setOut_trade_no(out_trade_no);
		mp.setTotal_fee(total_fee);
		Object obj = faceDeviceSerivce.getWXData(device_id);
		Object[] objs = (Object[])obj;
		mp.setSub_appid(objs[0].toString());
		mp.setSub_mch_id(objs[1].toString());
		if(store_name!=null&&!store_name.equals("")){
			mp.setBody("数字地球-"+store_name+"-消费商品");
		}
		WxPayDto pto = WeixinUtil.wxpayface(mp);
		Map<String, Object> map = new HashMap<String, Object>();
		if(pto!=null&&pto.getReturn_code().equals("SUCCESS")){
			map.put("result_code",pto.getResult_code());
			String nickname = "";
			try {
				String access_Token = faceDeviceSerivce.getAccessTokenFromDataBase();
				if (openid != null&&!openid.equals("")) {
					nickname = WeixinUtil.getUserByToken(access_Token, openid);
				}
			}catch (Exception r){
				nickname = "佚名";
			}
			faceDeviceSerivce.savePayBill(out_trade_no,openid,nickname,pto.getTransaction_id(),total_fee,comID,device_id);//分单位
		}else{
			map.put("result_code","FAIL");
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return "success";

	}

	/**
	 *
	 * 根据设备号获取绑定的商户的微信APPID和商户号
	 * @return
	 */
	public String getWXData(){
		String device_id = request.getParameter("device_id");

		Object obj = faceDeviceSerivce.getWXData(device_id);
		Map<String, Object> map = new HashMap<String, Object>();
		if(obj!=null){
			Object[] objs = (Object[])obj;
			map.put("subAppID",objs[0].toString());
			map.put("subMchid",objs[1].toString());
		}

		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return "success";
	}

	/**
	 *
	 * 获取在线海报
	 * @return
	 */
	public String getPosterList(){

		List<Object>  list =   faceDeviceSerivce.getPosterList();

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("postlist",list);


		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return "success";
	}

	/**
	 *
	 * 微信付款码
	 * @return
	 */
	public String payCode(){
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String device_id = request.getParameter("device_id");
			String total_fee = request.getParameter("total_fee");
			String barCode = request.getParameter("barCode");
			String out_trade_no = request.getParameter("out_trade_no");

			if (total_fee != null && !total_fee.equals("") && Float.parseFloat(total_fee) > 0) {
				Object obj = faceDeviceSerivce.getWXData(device_id);
				String sub_appid = "";
				String sub_mch_id = "";
				if (obj != null) {
					Object[] objs = (Object[]) obj;
					sub_appid = objs[0].toString();
					sub_mch_id = objs[1].toString();
				}
				String openid = WeixinUtil.authcodeopenid(barCode, sub_appid, sub_mch_id);
				String access_Token = faceDeviceSerivce.getAccessTokenFromDataBase();
				String nickname = "";
				try {
					if (openid != null&&!openid.equals("")) {
						nickname = WeixinUtil.getUserByToken(access_Token, openid);
					}
				}catch (Exception r){
					nickname = "佚名";
				}
				Micropay micropay = new Micropay();
				micropay.setAuth_code(barCode);
				micropay.setAttach("selfpay");
				micropay.setBody("消费");
				micropay.setTotal_fee(total_fee);
				micropay.setSub_appid(sub_appid);
				micropay.setSub_mch_id(sub_mch_id);
				micropay.setOut_trade_no(out_trade_no);
				micropay.setDevice_info(device_id);

				WxPayDto dw = WeixinUtil.payCard(micropay);
				if (dw != null && dw.getResult_code().equals("SUCCESS")) {
					WxPayDto payDto = WchatPay.searchServOrder(micropay.getOut_trade_no(), micropay.getSub_appid(), micropay.getSub_mch_id());
					if (payDto != null && payDto.getReturn_code().equals("SUCCESS")) {
						if (payDto.getTrade_state().equals("SUCCESS")) {
							faceDeviceSerivce.recordTradeMoney(device_id, WeChatUtils.changeF2Y(total_fee), out_trade_no, "scancode", openid, nickname);
							map.put("result", "SUCCESS");
						}
					} else {
						map.put("result", payDto.getTrade_state());
					}
				} else {

					faceDeviceSerivce.savePayBill(out_trade_no, openid,nickname, dw.getTransaction_id(), total_fee, "", device_id);//分单位

					map.put("result", dw.getErr_code());
				}

			}
		}catch (Exception e){
			logger.error("操作异常", e);
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return "success";

	}





	/**
	 * 微信回调同步获取支付结果
	 */
	public String callBaseRep() {
		String device_id = request.getParameter("device_id");
		String total_fee = request.getParameter("total_fee");
		String out_trade_no = request.getParameter("out_trade_no");
		Object obj = faceDeviceSerivce.getWXData(device_id);
		String  sub_appid = "";
		String  sub_mch_id = "";
		if (obj != null) {
			Object[] objs = (Object[]) obj;
			sub_appid = objs[0].toString();
			sub_mch_id = objs[1].toString();
		}
		WxPayDto  payDto = WchatPay.searchServOrder(out_trade_no, sub_appid,sub_mch_id);
		Map<String, Object> map = new HashMap<String, Object>();
		if(payDto!=null&&payDto.getTrade_state().equals("SUCCESS")) {
			String openid = "";
			String nickname = "";
			PayBackupBill payBackupBill = assiCartService.getPayBakupByJum(out_trade_no);
			if(payBackupBill!=null) {
				openid = payBackupBill.getOpenId();
				nickname = payBackupBill.getNickname();
			}
			faceDeviceSerivce.recordTradeMoney(device_id,total_fee,out_trade_no,"scancode",openid,nickname);
			map.put("result","SUCCESS");
		}

		map.put("result", payDto.getTrade_state());
		JSONObject jo = JSONObject.fromObject(map);
		result = jo;

		return "success";

	}


	/**
	 *
	 * 咨询列表
	 * @return
	 */
	public String  getConsultList(){
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String isIntentCustomer = request.getParameter("isIntentCustomer");
		String returnVisit = request.getParameter("returnVisit");
		String companyId = request.getParameter("companyId");
		String staffId = request.getParameter("staffId");
		String parameter = request.getParameter("parameter");
		Map<String, Object> map = new HashMap<String, Object>();
		List<BaseBean> list = consultManageService.getConsultList(start,end,isIntentCustomer,returnVisit,companyId,staffId,parameter);
		List<Object> lists = new ArrayList<Object>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i <list.size(); i++) {
				ConsultingRegistration  consult = (ConsultingRegistration)list.get(i);
				JSONObject jsonObj = new JSONObject();
				jsonObj.accumulate("goodsname", isNull(consult.getGoodsname()));
				jsonObj.accumulate("date", isNull(Utilities.getDateString(consult.getConsultingDate(),"yyyy-MM-dd HH:mm:ss")));
				jsonObj.accumulate("tel", isNull(consult.getConsultantPhone()));
				jsonObj.accumulate("name", isNull(consult.getConsultantName()));
				jsonObj.accumulate("ppid", isNull(consult.getPpid()));
				jsonObj.accumulate("crId", isNull(consult.getCrId()));
				jsonObj.accumulate("isIntentCustomer", isNull(consult.getIsIntentCustomer()));
				lists.add(jsonObj);
			}
		}
		map.put("consultList", lists);
		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return  "success";

	}

	/**
	 *
	 * 添加咨询
	 * @return
	 */
	public String addConsult(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String crId = request.getParameter("crId");
			String islisten = request.getParameter("islisten");
			String visitContent = request.getParameter("visitContent");
			String clientType = request.getParameter("clientType");
			String isIntentCustomer = request.getParameter("isIntentCustomer");
			String visitStaffID = request.getParameter("visitStaffID");

			ConsultingRegistration consult = new ConsultingRegistration();
			consult.setCrId(crId);
			consult.setIslisten(islisten);
			consult.setVisitContent(visitContent);
			consult.setClientType(clientType);
			consult.setIsIntentCustomer(isIntentCustomer);
			consult.setVisitStaffID(visitStaffID);
			consultManageService.saveReturnVisit(consult);
			map.put("result", "SUCCESS");
		}catch (Exception e){
			logger.error("操作异常", e);
			map.put("result", "FAIL");

		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo;
		return  "success";

	}

	public String getCompanyIdentifier() {
		return companyIdentifier;
	}

	public void setCompanyIdentifier(String companyIdentifier) {
		this.companyIdentifier = companyIdentifier;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
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

	public TEshopCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(TEshopCustomer customer) {
		this.customer = customer;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public String getPeopleName() {
		return peopleName;
	}

	public void setPeopleName(String peopleName) {
		this.peopleName = peopleName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public COSJobPlan getJobPlans() {
		return jobPlans;
	}

	public void setJobPlans(COSJobPlan jobPlans) {
		this.jobPlans = jobPlans;
	}

	public LogBook getLogbooks() {
		return logbooks;
	}

	public void setLogbooks(LogBook logbooks) {
		this.logbooks = logbooks;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public List<CCode> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<CCode> codeList) {
		this.codeList = codeList;
	}

	public TelInRecord getTelInRecord() {
		return telInRecord;
	}

	public void setTelInRecord(TelInRecord telInRecord) {
		this.telInRecord = telInRecord;
	}

	public TelOutRecord getTelOutRecord() {
		return telOutRecord;
	}

	public void setTelOutRecord(TelOutRecord telOutRecord) {
		this.telOutRecord = telOutRecord;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public CAccount getAccount() {
		return account;
	}

	public void setAccount(CAccount account) {
		this.account = account;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getEchostr() {
		return echostr;
	}

	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCusType() {
		return cusType;
	}

	public void setCusType(String cusType) {
		this.cusType = cusType;
	}

	public ContactCompany getContactCompany() {
		return contactCompany;
	}

	public void setContactCompany(ContactCompany contactCompany) {
		this.contactCompany = contactCompany;
	}

	public Staff getCstaff() {
		return cstaff;
	}

	public void setCstaff(Staff cstaff) {
		this.cstaff = cstaff;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}



	public List<File> getPictureList() {
		return pictureList;
	}

	public void setPictureList(List<File> pictureList) {
		this.pictureList = pictureList;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getSccId() {
		return sccId;
	}

	public void setSccId(String sccId) {
		this.sccId = sccId;
	}

	public MobileMessagenew getMsg() {
		return msg;
	}

	public void setMsg(MobileMessagenew msg) {
		this.msg = msg;
	}

	public MobileMessage getMsage() {
		return msage;
	}

	public void setMsage(MobileMessage msage) {
		this.msage = msage;
	}

	public String getStaffid() {
		return staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getStaffidentitycard() {
		return staffidentitycard;
	}

	public void setStaffidentitycard(String staffidentitycard) {
		this.staffidentitycard = staffidentitycard;
	}

	public String getQqId() {
		return qqId;
	}

	public void setQqId(String qqId) {
		this.qqId = qqId;
	}

	public String getAppOpenId() {
		return appOpenId;
	}

	public void setAppOpenId(String appOpenId) {
		this.appOpenId = appOpenId;
	}

	public String getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getThirdId() {
		return thirdId;
	}

	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
