package com.tiantai.wfj.front;

import com.faceSDK.faceUtil.FaceUtils;
import com.faceSDK.faceUtil.IdCardOcrUtils;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.edmandServe.service.DserveService;
import com.tiantai.wfj.service.EarthIndexService;
import com.tiantai.wfj.service.MyCenterService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffCard;
import hy.ea.bo.office.face.PublicMenuRecommend;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.bo.IdentityCard;
import mobile.tiantai.android.service.DouService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@Scope("prototype")
public class MyCenterAction {
	private static final Logger logger = LoggerFactory.getLogger(MyCenterAction.class);
	private final Logger logger = LoggerFactory.getLogger(MyCenterAction.class);
	@Resource
	private EarthIndexService earthIndexService;

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private MyCenterService myCenterService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private ServerService serverService;
	@Resource
	private DserveService dserveService;
	private String user;

	private File photo;
	private String photoFileName;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = ServletActionContext.getRequest().getSession();
	SessionWrap sw = SessionWrap.getInstance();
	private Staff staff;
	private String result;
	private StaffCard staffCard;
	@Autowired
	private DouService douService;
	/**
	 * 
	 * 我的中心
	 * @return
	 */
	public String  myIndex(){

//		String pc = request.getParameter("pc");
//		if(user!=null&&!user.equals("")&&"pc".equals(pc)){
//			 TEshopCusCom tc = myCenterService.getCusCom(user);
//			 TEshopCustomer customer = myCenterService.getCustomer(user);
//			 sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
//	         sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, tc);
//		}

		if(user!=null&&!user.equals("")){
//			String url = request.getRequestURL()+"";
//			session.setAttribute("url", url);
//			return "login";
			logger.info("调试信息");
			TEshopCusCom tc = myCenterService.getCusCom(user);
			if(tc!=null){
				logger.info("调试信息");
			}
		  TEshopCustomer customer = myCenterService.getCustomer(user);

			if(customer!=null){
				logger.info("调试信息");
			}
	     sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer);
	         sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, tc);
		}

		 TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
	             SessionWrap.KEY_SHOPCUSCOM);

		if(tc!=null){
			
			//logger.info("调试信息");
			Object obj = myCenterService.getUserInfo(tc.getAccount());
			request.setAttribute("userinfo", obj);
			//查询推荐信息
			List<Object> menuInfo = myCenterService.getMenuInfo(tc.getAccount(), tc.getStaffid(), 5);
			request.setAttribute("recommendList", menuInfo);
		}else{

			String url = request.getRequestURL()+"?"+request.getQueryString();
			session.setAttribute("url", url);
			return "login";
		}
		CAccount account = new CAccount();
		account.setStaffID(tc.getStaffid());
		HttpSession session1 = ServletActionContext.getRequest().getSession();
		session1.setAttribute("account",account);


		
		 return "mycenter";
	}


	/**
	 *
	 * 保存
	 * @return
     */
	public String editInfo(){
		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		StaffCard staffCard=new StaffCard();
		if (personImageInfo != null) {
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			//String photoPath = fileService.savePhoto(path,personImageInfoFileName, personImageInfo, tc.getStaffid(),"/staff/headimage/"
					//+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			//压缩文件
			String  photoPath= FaceUtils.scaleImage(path,personImageInfoFileName, personImageInfo, "faceImage","staff/headimage/"
					+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			staff.setHeadimage(photoPath);//这里是人脸识别的图片（当前为头像，后续修改为对应图片是需要改动人脸识别取值的对应字段）

		}
		if(fileFront != null){
			//身份证正面图片上传
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			//压缩文件
			String  fileFrontPath= FaceUtils.scaleImage(path,fileFrontFileName, fileFront, "cardImage","staff/card/"
					+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			staffCard.setCardFrontUrl(fileFrontPath);
		}
		if(fileReverseSide != null){
			//身份证反面上传
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			//压缩文件
			String  fileReverseSidePath= FaceUtils.scaleImage(path,fileReverseSideFileName, fileReverseSide, "cardImage","staff/card/"
					+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			staffCard.setCardReverseUrl(fileReverseSidePath);
		}
		if(photo!=null){
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path,photoFileName, photo, tc.getStaffid(),"/staff/headimage/"
			+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			staff.setHeadimage(photoPath);
		}
		staffCard.setStaffID(tc.getStaffid());
		myCenterService.editInfo(staff,tc.getSccId());
		myCenterService.editSatffCardInfo(staffCard);

		request.setAttribute("message","11");
         return "success";
	}
	public String uploadPhotoFront(){
		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		StaffCard staffCard=new StaffCard();
		if(fileFront != null){
			//身份证正面图片上传
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			//压缩文件
			String fileFrontPath = FaceUtils.scaleImage(path,fileFrontFileName, fileFront, "cardImage","staff/card/"
					+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			staffCard.setCardFrontUrl(fileFrontPath);
			//图片识别
			String contextPath = ServletActionContext.getRequest().getContextPath();
			String basePath = ServletActionContext.getRequest().getScheme() + "://"
					+ ServletActionContext.getRequest().getServerName() + ":"
					+ ServletActionContext.getRequest().getServerPort() + contextPath
					+ "/";
			IdentityCard identityCard = douService.importCustomerCardByImage(basePath + fileFrontPath);
			staff.setRealname(identityCard.getName());
			staff.setStaffIdentityCard(identityCard.getIdentityCardNO());
		}
		staffCard.setStaffID(tc.getStaffid());
		myCenterService.editInfo(staff,tc.getSccId());
		myCenterService.editSatffCardInfo(staffCard);
		return "success";
	}
	public File getFileFront() {
		return fileFront;
	}

	public void setFileFront(File fileFront) {
		this.fileFront = fileFront;
	}

	public String getFileFrontFileName() {
		return fileFrontFileName;
	}

	public void setFileFrontFileName(String fileFrontFileName) {
		this.fileFrontFileName = fileFrontFileName;
	}

	public File getFileReverseSide() {
		return fileReverseSide;
	}

	public void setFileReverseSide(File fileReverseSide) {
		this.fileReverseSide = fileReverseSide;
	}

	public String getFileReverseSideFileName() {
		return fileReverseSideFileName;
	}

	public void setFileReverseSideFileName(String fileReverseSideFileName) {
		this.fileReverseSideFileName = fileReverseSideFileName;
	}

	/**
	 *
	 * 获取个人认证信息
	 * @return
     */
	public String getInfo(){

		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);

		if(tc!=null){

			staff = (Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{tc.getStaffid()});
			staffCard=(StaffCard) baseBeanService.getBeanByHqlAndParams("from StaffCard where staffID = ?",new Object[]{tc.getStaffid()});
		}
		return "tocardinfo";

	}



	/**
	 *
	 * 获取个人认证信息
	 * @return
	 */
	public String getMyCode(){

		String sccid = request.getParameter("sccid");

		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
        String account = "";
		if(sccid!=null&&!sccid.equals("")){
			tc = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?",new Object[]{sccid});
			account = tc.getAccount();
		}else if(tc!=null){
			account = tc.getAccount();
		}

		if(!"".equals(account)){

			Object obj = myCenterService.getUserInfo(account);
			request.setAttribute("userinfo", obj);

		}
		return "mycode";

	}
	/**
	 *
	 * 获取个人认证信息
	 * @return
	 */
	public String getBaseInfo(){

		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);

		if(tc!=null){

			staff = (Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{tc.getStaffid()});
		}
		return "baseinfo";

	}

	/**
	 *
	 * 修改登录密码
	 * @return
     */
    public String updateLoginPsw(){

		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		String yspsw = request.getParameter("yspsw");
		String newpsw = request.getParameter("newpsw");
            String  re = myCenterService.updatePsw(tc.getAccount(),"login",yspsw,newpsw);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("re", re);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();


      return "success";
	}
	/**
	 *
	 * 修改交易密码
	 * @return
	 */
	public String updatePayPsw(){

		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		String yspsw = request.getParameter("yspsw");
		String newpsw = request.getParameter("newpsw");
		String  re = myCenterService.updatePsw(tc.getAccount(),"pay",yspsw,newpsw);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("re", re);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 *
	 * 账号与安全
	 * @return
     */
    public String getAccountInfo(){
		TEshopCustomer customer = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);

          if(customer!=null) {
			  customer = (TEshopCustomer)baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0",new Object[]{customer.getAccount()});

			  request.setAttribute("account", customer.getAccount());
			  request.setAttribute("isSet", (customer.getPaymentCode() != null && !customer.equals("")) ? "yes" : "no");
		  }else{
			  return "login";
		  }
         return "ascurity";
	}

    /**
	 *
	 * 设置
	 * @return
	 */
	public String getSetlist(){

		TEshopCustomer customer = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);

		if(customer!=null){
			//logger.info("调试信息");
			Object[] obj =(Object[]) myCenterService.getUserInfo(customer.getAccount());
			request.setAttribute("rzstatus", ((obj[4] + "").isEmpty())?"wrz":"rz");
		}

		return "setlist";
	}



	private File photoPositiveInfo;
	private String photoPositiveInfoFileName;
	private File personImageInfo;//人脸认证
	private File fileFront;//身份证正面
	private String fileFrontFileName;//身份证正面文件名称
	private File fileReverseSide;//身份证反面
	private String fileReverseSideFileName;//身份证反面面文件名称
	private String personImageInfoFileName;//人脸认证
	private File photoBacInfo;
	private String photoBacInfoFileName;
	public String getIdCardInfo(){
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			//Date date = new Date();
			String type = photoPositiveInfoFileName.substring(photoPositiveInfoFileName.lastIndexOf(".") + 1);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedImage imageAll = ImageIO.read(photoPositiveInfo);
			BufferedImage image = IdCardOcrUtils.setWH(imageAll);
			IdCardOcrUtils.lumAdjustment(image, -20);
			BufferedImage bufferedImage = IdCardOcrUtils.enhanceContrast(image);
			ImageIO.write(bufferedImage, type, baos);
			byte[] bytes = baos.toByteArray();
			//Date date1 = new Date();
			Map<String, String> userInfoMap = IdCardOcrUtils.getStringStringMap(bytes);
			JSONObject js=JSONObject.fromObject(userInfoMap);
			result=js.toString();
			//Date date2 = new Date();
			//logger.info("调试信息");
			//logger.info("调试信息");
		}catch (Exception e){
			//logger.info(e.getMessage());
		}
		return "success";
	}
	/**
	 *
	 * 退出登录
	 * @return
     */
	public String exitLogin(){
        session.invalidate();

		return "login";
	}


	/**
	 * 注销
	 * @return String
	 */
	public String setLogOff(){
		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		tc.setLogOff("1");
		baseBeanService.updateAndExecute(tc);
		TEshopCustomer customer = (TEshopCustomer)sw.getObject(session,SessionWrap.KEY_CUSTOMER);
		customer.setLogOff("1");
		baseBeanService.updateAndExecute(customer);
		session.invalidate();
		return "login";
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public StaffCard getStaffCard() {
		return staffCard;
	}

	public void setStaffCard(StaffCard staffCard) {
		this.staffCard = staffCard;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public File getPhotoPositiveInfo() {
		return photoPositiveInfo;
	}

	public void setPhotoPositiveInfo(File photoPositiveInfo) {
		this.photoPositiveInfo = photoPositiveInfo;
	}

	public String getPhotoPositiveInfoFileName() {
		return photoPositiveInfoFileName;
	}

	public void setPhotoPositiveInfoFileName(String photoPositiveInfoFileName) {
		this.photoPositiveInfoFileName = photoPositiveInfoFileName;
	}

	public File getPersonImageInfo() {
		return personImageInfo;
	}

	public void setPersonImageInfo(File personImageInfo) {
		this.personImageInfo = personImageInfo;
	}

	public String getPersonImageInfoFileName() {
		return personImageInfoFileName;
	}

	public void setPersonImageInfoFileName(String personImageInfoFileName) {
		this.personImageInfoFileName = personImageInfoFileName;
	}

	public File getPhotoBacInfo() {
		return photoBacInfo;
	}

	public void setPhotoBacInfo(File photoBacInfo) {
		this.photoBacInfo = photoBacInfo;
	}

	public String getPhotoBacInfoFileName() {
		return photoBacInfoFileName;
	}

	public void setPhotoBacInfoFileName(String photoBacInfoFileName) {
		this.photoBacInfoFileName = photoBacInfoFileName;
	}

	//========================================================================================================================

	/**
	 * 单独上传人脸识别图片或者头像，更新到数据库中
	 * @return
	 */
	public String uploadMyPhoto(){
		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		if(tc != null){
			String hql="from Staff where staffId=?";
			Staff staffInfo = (Staff) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{tc.getStaffid()});
			if (personImageInfo != null) {
				String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
				String  personImageInfoPath= FaceUtils.scaleImage(path,personImageInfoFileName, personImageInfo, "faceImage","staff/headimage/"
						+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
				//更新人脸识别数据
				staffInfo.setFacePicture(personImageInfoPath);
				baseBeanService.update(staffInfo);
			}

			if(photo!=null){
				String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
				String photoPath = FaceUtils.scaleImage(path,photoFileName, photo, tc.getStaffid(),"/staff/headimage/"
						+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
				//更新头像数据

				staffInfo.setHeadimage(photoPath);
				baseBeanService.update(staffInfo);
			}
			return findMyAuthentication();
		}else{
			return "login";
		}

	}

	/**
	 * 获取个人认证信息
	 * @return
	 */
	public String findMyAuthentication(){
		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		if(tc != null){
			Map<String,Boolean> map=new HashMap<>();
			String sqlAll = "SELECT f.headimage,f.FACEPICTURE,f.realname FROM dt_hr_staff f WHERE f.staffId=?";
			Object objAll = baseBeanService.getObjectBySqlAndParams(sqlAll, new Object[]{tc.getStaffid()});
			request.setAttribute("personObj",objAll);
			//身份证正面照片
			String frontSqlAll = "select CARD_FRONT_URL,CARD_REVERSE_URL from DT_HR_STAFF_CARDINFO where STAFF_ID=?";
			Object frontAll = baseBeanService.getObjectBySqlAndParams(frontSqlAll, new Object[]{tc.getStaffid()});
			request.setAttribute("frontCardUrl",frontAll);
			//查询印章或签名
			String personSealSql="select SCANNINGACCESSORIES from DT_OA_ENTERPRISESTAMP where RESPONSIBLEID=? and STAMPTYPE='个人印章'  and DELSTATUS is null ";
			List listBeanBySqlAndParams = baseBeanService.getListBeanBySqlAndParams(personSealSql, new Object[]{tc.getStaffid()});
			if(listBeanBySqlAndParams!=null && listBeanBySqlAndParams.size()>0){
				request.setAttribute("personSeal",listBeanBySqlAndParams);
			}else{
				request.setAttribute("personSeal",null);
			}

			String bankSql = "SELECT sb.BANKACCOUNT FROM dt_hr_staff f, DT_HR_STAFF_BANKACCOUNT sb WHERE sb.STAFFID = f.STAFFID AND f.staffId = ?";
			List<Object> listObjectBySqlAndParams = baseBeanService.getListBeanBySqlAndParams(bankSql, new Object[]{tc.getStaffid()});
			if(listObjectBySqlAndParams!=null && listObjectBySqlAndParams.size()>0){
				request.setAttribute("personBank",listObjectBySqlAndParams.size());
			}else{
				request.setAttribute("personBank",null);
			}
			//人员信息
			request.setAttribute("staffId",tc.getStaffid());
			request.setAttribute("sccid",tc.getSccId());
			request.setAttribute("account",tc.getAccount());

			List<BaseBean> beanList = dserveService.wtypeListBySccid(tc.getStaffid(),tc.getAccount(),"1");
			if(beanList!=null && beanList.size()>0){
				request.setAttribute("twslist",beanList.size());
			}else{
				request.setAttribute("twslist",null);
			}
			return "myAuthentication";
		}else{
			return "login";
		}

	}

	//我的功能点查询
	public String findMenuInfo(){
		//查询一级菜单
		String sqlAll = "SELECT MENU_KEY,MENU_ID,MENU_TITLE,MENU_URL FROM DT_PUBLIC_MENU_ONE";
		List<Object> menuOne = baseBeanService.getListBeanBySqlAndParams(sqlAll, new Object[]{});
		Map<String,Object> mapAll=new HashMap<String, Object>();
		mapAll.put("list", menuOne);
		JSONObject js=JSONObject.fromObject(mapAll);
		result=js.toString();
		return "success";
	}
	public String findMenuInfoTwo(){
		//查询二级三级级菜单
		List<Object> listTwo=new LinkedList<>();
		String sqlTwo = "SELECT MENU_KEY,MENU_ID,MENU_TITLE,MENU_URL,MENU_PARENT_ID,MENU_SORT,MENU_LEVEL FROM DT_PUBLIC_MENU_TWO  WHERE MENU_PARENT_ID=? ORDER BY MENU_SORT";
		listTwo = baseBeanService.getListBeanBySqlAndParams(sqlTwo, new Object[]{pmr.getMenuId()});
		//组装查询条件
		String MENU_ID="";
		String sqlThree = "SELECT MENU_KEY,MENU_ID,MENU_TITLE,MENU_URL,MENU_PARENT_ID,MENU_SORT,MENU_LEVEL FROM DT_PUBLIC_MENU_THREE  WHERE MENU_PARENT_ID IN (";
		for (int i = 0; i < listTwo.size(); i++) {
			Object obj = listTwo.get(i);
			Object[] info = (Object[]) obj;
			if(info[1]!=null){
				if(i==listTwo.size()-1){
					sqlThree+=info[1].toString();
				}else{
					sqlThree+=info[1].toString()+",";
				}
			}
		}
		sqlThree+=") ORDER BY MENU_SORT";
		List<Object> listThree=new LinkedList<>();
		listThree = baseBeanService.getListBeanBySqlAndParams(sqlThree, new Object[]{});
			Map<String,List<Object>> map=new LinkedHashMap<>();
		for (int i=0;i<listTwo.size();i++){
			Object obj = listTwo.get(i);
			Object[] menuTwo = (Object[]) obj;
			List<Object> twoList=new ArrayList<>();
			String Key="";
			Key=menuTwo[2].toString();
			for(int j=0;j<listThree.size();j++){
				Object obj2 = listThree.get(j);
				Object[] menuTwo2 = (Object[]) obj2;
				if(menuTwo2[4].equals(menuTwo[1].toString())){
					twoList.add(obj2);
					listThree.remove(obj2);
					j--;
				}
			}
			map.put(Key,twoList);
		}
		Map<String,Object> mapAll=new HashMap<String, Object>();
		mapAll.put("list", map);
		JSONObject js=JSONObject.fromObject(mapAll);
		result=js.toString();
		return "success";
	}



	public PublicMenuRecommend pmr=new PublicMenuRecommend();
	public String saveMenuRecommend(){
		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		if(tc != null){
			//判断当前用户是否已经有当前推荐的id了,如果有，直接将时间修改即可，没有则添加数据
			String hql="from PublicMenuRecommend where menuId=? and staffId=?";
			PublicMenuRecommend beanByHqlAndParams =(PublicMenuRecommend) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{pmr.getMenuId(),tc.getStaffid()});
			if(beanByHqlAndParams!=null){
				//修改时间
				beanByHqlAndParams.setUpdateTime(new Date());
				baseBeanService.update(beanByHqlAndParams);
			}else{
				pmr.setUpdateTime(new Date());
				pmr.setStaffId(tc.getStaffid());
				pmr.setRecommendId(serverService.getServerID("rec"));
				baseBeanService.save(pmr);
			}
			return "success";
		}else{
			return "login";
		}
	}
	public String findMenuInfoTwoAll(){
		TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		if(tc != null){
			String sql = "select mt.MENU_TITLE,mt.MENU_URL,mt.MENU_ID from DT_PUBLIC_MENU_RECOMMEND  mr LEFT JOIN DT_PUBLIC_MENU_THREE  mt on mr.MENU_ID=mt.MENU_ID where mr.STAFF_ID=? ORDER BY mr.UPDATE_TIME DESC";
			List<Object> listObjectBySqlAndParams = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{tc.getStaffid()});
			Map<String,Object> mapAll=new HashMap<String, Object>();
			mapAll.put("list", listObjectBySqlAndParams);
			JSONObject js=JSONObject.fromObject(mapAll);
			result=js.toString();
			return "success";
		}else{
			return "login";
		}
	}
}
