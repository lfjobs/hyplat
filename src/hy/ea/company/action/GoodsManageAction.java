package hy.ea.company.action;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.vo.GoodsCashierBillsVO;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.CarInformation;
import hy.ea.service.*;
import hy.ea.util.Constant;
import hy.ea.util.ToChineseFirstLetter;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import com.unionpay.payMeth.unionpayMeth;

/**
 * 商品管理
 */
@Controller
@Scope("prototype")
public class GoodsManageAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CCodeService codeService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CompanyService companyserverService;
	@Resource
	private UpLoadFileService fileService;
	private InputStream excelStream;
	private GoodsManage goodsManage;
	private PageForm pageForm;
	private List<CCode> typelist;
	private List<CCode> variablelist;
	private List<CCode> standardslist;
	private String parameter;
	// 在财务下的固定资产下的资产报表中增加打印条码功能，需要同时打印部门名称和物品名称，所以增加两个成员变量
	private String departmentName;
	private String goodsName;
	// end
	private String staffName;
	private String pName;
	private String result;
	private String iid;
	private int pageNumber;
	private String search;
	private GoodsCashierBillsVO goodsCashierBillsVO;
	private CCode code;
	/**
	 * 责任人
	 */
	private List<BaseBean> stafflist;
	/**
	 * 费用类别--GoodsBills
	 */
	private List<CCode> costTypeList;
	private List<CCode> billTypes;
	private CCode ccode;
	private boolean export = false;
	private String sdate;
	private String edate;
	private String treeName="";
	/**
	 * 物品状态：00表示正常，01表示删除
	 */
	
    //wk
	private String ACTIVITY_ROOT = "upload_files/wechatmenu/";//缓存地址
	private String SUFFIX = ".txt";//缓存类型
	private char SPT= '/';
	private String ENCODING="GBK";//存储格式
	// 车辆信息
	private CarInformation carInformation;
	private List<BaseBean> carInformationList;

	private String codepid;

	/**
	 * 生成条码
	 */
	public String barcode() {
		return "bar";
	}

	/**
	 * 前台获取科目
	 * 
	 * @return
	 */
	public String getToSubject() {
		String subhql = "from GoodsManage where goodsID = ?";
		GoodsManage goodsManages = (GoodsManage) baseBeanService
				.getBeanByHqlAndParams(subhql,
						new Object[] { goodsManage.getGoodsID() });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjectsName", goodsManages.getSubjectsName());
		map.put("subjectsID", goodsManages.getSubjectsID());
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 前台获取车辆信息
	 * 
	 * @returnujh
	 */
	public String getToCarinformation() {
		String carhql = "from CarInformation where carFrameNum = ? or engineNum = ?";
		CarInformation carmation = (CarInformation) baseBeanService
				.getBeanByHqlAndParams(carhql,
						new Object[] { carInformation.getCarFrameNum(),
								carInformation.getEngineNum() });
		Map<String, Object> map = new HashMap<String, Object>();
		if (carmation != null) {
			map.put("carmation", carmation);
			if (carmation.getReleaseDate() != null) {

				map.put("releaseDate", (new SimpleDateFormat("yyyy-MM-dd"))
						.format(carmation.getReleaseDate()));
			}
			if (carmation.getOperationDate() != null) {
				map.put("operationDate", (new SimpleDateFormat("yyyy-MM-dd"))
						.format(carmation.getOperationDate()));
			}
			if (carmation.getBuyDate() != null) {
				map.put("buyDate", (new SimpleDateFormat("yyyy-MM-dd"))
						.format(carmation.getBuyDate()));
			}
		}
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * wk 增加物品管理时AJAX添加图片然后选择图片时直接跟后台交互 吧图片保存在服务器中然后 地址拿出返回到JSP页面上然后 显示图片 注。
	 * 只能显示主题图片最大的那个显示
	 * 
	 * @return
	 */
	// ajax 选择图片时返回 图片地址显示在添加页面上。
	public String ajaxshangchuan() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (goodsManage.getFileLogo() != null) {
			System.out.println("logo");
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String logopath = fileService.savePhoto(path,
					goodsManage.getFileLogoFileName(),
					goodsManage.getFileLogo(), account.getCompanyID(),
					"upload_files/ea/human/goodsmanage/");
			result = logopath.toString();

		}
		if (goodsManage.getFilePhoto() != null) {
			System.out.println("photo");
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photopath = fileService.savePhoto(path,
					goodsManage.getFilePhotoFileName(),
					goodsManage.getFilePhoto(), account.getCompanyID(),
					"upload_files/ea/human/goodsmanage/");
			result = photopath.toString();
		}
		// 此处返回视频地址 未解决
		// if(goodsManage.getFileship()!=null)
		// {
		//
		// String
		// path=ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
		// String
		// shippath=fileService.savePhoto(path,goodsManage.getFileshipName(),goodsManage.getFileship(),account.getCompanyID(),"upload_files/ea/human/goodsmanage/");
		// result=shippath.toString();
		//
		// }
		// result =
		else {
			result = null;
		}
		return "success";
	}
	//创建文本子方法
	private String getRealPath(String root,String id){
		StringBuilder sb = new StringBuilder(root);
		sb.append(relPath(id));
		return sb.toString().replace(SPT, File.separatorChar);
	}
	//创建文本子方法
	public String relPath(String id){
		StringBuilder sb = new StringBuilder(ACTIVITY_ROOT);
		sb.append(id).append(SUFFIX);
		return sb.toString();
	}
   //创建文本方法传入一个内容 跟一个识别。好在物品ID基础上加上识别号
	public String text(String contenttxt,int bie)
	{	HttpServletRequest request = ServletActionContext.getRequest();
		String url= request.getSession().getServletContext().getRealPath("/");
		if(contenttxt==null){
			contenttxt="";
		}
		String realPath="";
		realPath = getRealPath(url,goodsManage.getGoodsID()+bie);
		File f = new File(realPath);
		try {
			FileUtils.writeStringToFile(f, contenttxt,ENCODING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();				
		return sb.append(goodsManage.getGoodsID()+bie).append(SUFFIX).toString();
	}
	// 添加或修改商品管理
	public synchronized String saveGoodsManage() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (goodsManage.getFilePhoto() != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path,
					goodsManage.getFilePhotoFileName(),
					goodsManage.getFilePhoto(), account.getCompanyID(),
					"upload_files/ea/human/goodsmanage/");
			goodsManage.setPhotoPath(photoPath);
		}
		if (goodsManage.getFileLogo() != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String logoPath = fileService.savePhoto(path,
					goodsManage.getFileLogoFileName(),
					goodsManage.getFileLogo(), account.getCompanyID(),
					"upload_files/ea/human/goodsmanage/");
			goodsManage.setLogoPath(logoPath);
		}
		 if (goodsManage.getFileship() != null) {
		 String path =
		 ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
		 String logoPath = fileService.savePhoto(path,
		 goodsManage.getFileshipName(),
		 goodsManage.getFileship(), account.getCompanyID(),
		 "upload_files/ea/human/goodsmanage/");
		 goodsManage.setLogoPath(logoPath);
		 }
		if (null == goodsManage.getGoodsID()
				|| "".equals(goodsManage.getGoodsID())) {
			goodsManage.setGoodsID(serverService.getServerID("goodsManage"));				
			String wupjjtext=goodsManage.getWupjj();
			String wupyttext=goodsManage.getWupyt();
			String wupgntext=goodsManage.getWupgn();
			goodsManage.setWupjj(text(wupjjtext,1));
			goodsManage.setWupyt(text(wupyttext,2));
			goodsManage.setWupgn(text(wupgntext,3));
			parameter = "添加商品管理(商品名称:" + goodsManage.getGoodsName() + ")";
			if (carInformation != null) {
				carInformation.setCarID(serverService
						.getServerID("carInformation"));
				carInformation.setCarFrameNum(goodsManage.getManufacturers());
				carInformation.setEngineNum(goodsManage.getMnemonicCode());
				carInformation.setGoodsID(goodsManage.getGoodsID());
			}
		} else {
			
			String wupjjtext=goodsManage.getWupjj();
			String wupyttext=goodsManage.getWupyt();
			String wupgntext=goodsManage.getWupgn();
			goodsManage.setWupjj(text(wupjjtext,1));
			goodsManage.setWupyt(text(wupyttext,2));
			goodsManage.setWupgn(text(wupgntext,3));
			Object[] params = { account.getCompanyID(),
					goodsManage.getGoodsID() };
			String hql1 = "from GoodsManage where companyID=? and goodsID=? and goodsState ='00'";
			GoodsManage goods = (GoodsManage) baseBeanService
					.getBeanByHqlAndParams(hql1, params);
			parameter = "修改商品管理(商品名称:" + goods.getGoodsName() + ")";
			if (carInformation != null) {
				carInformation.setCarFrameNum(goodsManage.getManufacturers());
				carInformation.setEngineNum(goodsManage.getMnemonicCode());
				carInformation.setGoodsID(goodsManage.getGoodsID());
			}
		}
		String hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.companyID in (select c.companyID from Company c where c.groupCompanySn=?)  and vt.typeID=? ";
		Object[] params = { session.get("groupCompanySn"),
				goodsManage.getTypeID() };
		// 获取拼音码
		String pinyin = ToChineseFirstLetter.getFirstLetter(goodsManage
				.getTypeID());
		String Upstr = pinyin.toUpperCase();// 转换为大写
		int goodscodingnum = baseBeanService.getConutByByHqlAndParams(hql,
				params);
		DecimalFormat form = new DecimalFormat("000000");
		String ss = form.format(goodscodingnum + 1);
		goodsManage.setGoodsCoding(Upstr + "_" + ss);
		goodsManage.setCompanyID(account.getCompanyID());
		goodsManage.setGoodsState("00");
		CLogBook cLogBook = logBookService.saveCLogBook(null, parameter,
				account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		baseBeansList.add(goodsManage);
		carInformation.setGoodsCoding(goodsManage.getGoodsCoding());
		baseBeansList.add(carInformation);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,
				null, null);	
	return "succ";
	}

	// 删除某条商品管理
	public String delGoodsManage() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Object[] params = { account.getCompanyID(), goodsManage.getGoodsID() };
		String hql1 = "update GoodsManage set goodsState='01' where companyID = ?  and goodsID = ?";
		String hql2 = "from GoodsManage where goodsState = '00' and companyID=? and goodsID=? ";
		GoodsManage goods = (GoodsManage) baseBeanService
				.getBeanByHqlAndParams(hql2, params);
		CLogBook cLogBook = logBookService.saveCLogBook(null, " 删除商品管理(商品名称："
				+ goods.getGoodsName() + ")", account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,
				new String[] { hql1 }, params);
		goods = null;
		return "succ";
	}

	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", goodsManage);
		return getListGoodsManage();
	}

	public String toupdate(){
		HttpServletRequest request = ServletActionContext.getRequest();
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		String goodsID = request.getParameter("goodsID");
		String ids = request.getParameter("ids");
		String treeid1 = ids.substring(0, 33);
		String treeid2 = ids.substring(33, 66);
		String treeid3 = ids.substring(66, 99);
		String hql=" from CCode where companyID = ? and codeID= ? ";
		CCode ccode1 = (CCode) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),treeid1});
		CCode ccode2 = (CCode) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),treeid2});
		CCode ccode3 = (CCode) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),treeid3});
		String sql="update dtGoodsManage set typeID=?,tradeCode=?,bigClass=? where goodsID=?";
		baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{ccode2.getCodeValue(),ccode1.getCodeValue(),ccode3.getCodeValue(),goodsID});
		return getListGoodsManage();
	}
	@SuppressWarnings("unchecked")
	public DetachedCriteria getListBYDC() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String treename = request.getParameter("treename");
		String treeid = request.getParameter("iid");
		Map<String, Object> session = ActionContext.getContext().getSession();
		DetachedCriteria dc = DetachedCriteria.forClass(GoodsManage.class);
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		// 判断是否有子公司
		Company com = companyserverService.getCompanyByCompanyID(account
				.getCompanyID());
		result = com.getCompanyName();
		List<BaseBean> companylist = new ArrayList<BaseBean>();
		ArrayList<String> cids = new ArrayList<String>();
		if (com.getCompanyPID().startsWith("pcompany")) {
			companylist = companyserverService.getCompanyAndItsChildren(account
					.getCompanyID());
			parameter = com.getCompanyID();
		} else {
			companylist = companyserverService.getCompanyAndItsChildren(com
					.getCompanyPID());
			parameter = com.getCompanyPID();
		}
		Map<String, String> map = new HashMap<String, String>();
		for (BaseBean baseb : companylist) {
			Company b = (Company) baseb;
			map.put(b.getCompanyID(), b.getCompanyName());
			cids.add(b.getCompanyID());
			if (parameter.equals(b.getCompanyID())) {
				pName = b.getCompanyName();
			}
		}
		if (treename != null) {
			dc.add(Restrictions.eq("tradeCode", treename));
		}
		if(treeid!=null &&!treeid.equals("")|| session.get("treeidsearch")!=null && !"".equals(session.get("treeidsearch"))){
			String treeid1 = treeid.substring(0, 33);
			String treeid2 = treeid.substring(33, 66);
			String treeid3 = treeid.substring(66, 99);
			
			String hql=" from CCode where codeID=? and companyID=?";
			CCode ccode1=(CCode) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{treeid1,account.getCompanyID()});
			CCode ccode2=(CCode) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{treeid2,account.getCompanyID()});
			CCode ccode3=(CCode) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{treeid3,account.getCompanyID()});
			dc.add(Restrictions.eq("bigClass", ccode3.getCodeValue()));
			dc.add(Restrictions.eq("typeID", ccode2.getCodeValue()));
			List<BaseBean> ccode4=baseBeanService.getListBeanBySqlAndParams("select distinct codeValue from dtCCode where codePID=? and companyID=?", new Object[]{treeid1,account.getCompanyID()});
			ArrayList<String> list = new ArrayList<String>();
			list.add(ccode1.getCodeValue());
			for(Object ob : ccode4){
				String ob1 = ob.toString();
				list.add(ob1);
			}
			dc.add(Restrictions.in("tradeCode", list));
		}
		GoodsManage.setOMap(map);
		dc.add(Restrictions.in("companyID", cids));
		dc.add(Restrictions.eq("goodsState", "00"));
		if (search != null && search.equals("search")) {
			this.goodsManage = (GoodsManage) session.get("tablesearch");
			if (null != goodsManage.getGoodsCoding()
					&& !"".equals(goodsManage.getGoodsCoding())) {
				dc.add(Restrictions.like("goodsCoding",
						goodsManage.getGoodsCoding(), MatchMode.ANYWHERE));
			}
			if (null != goodsManage.getGoodsName()
					&& !"".equals(goodsManage.getGoodsName())) {
				dc.add(Restrictions.like("goodsName",
						goodsManage.getGoodsName(), MatchMode.ANYWHERE));
			}
			if (null != goodsManage.getCompanyID()
					&& !"".equals(goodsManage.getCompanyID())) {
				dc.add(Restrictions.eq("companyID", goodsManage.getCompanyID()));
			}
			if (null != goodsManage.getTypeID()
					&& !"".equals(goodsManage.getTypeID())) {
				dc.add(Restrictions.like("typeID", goodsManage.getTypeID(),
						MatchMode.ANYWHERE));
			}
		}
		dc.addOrder(Order.asc("companyID"));
		return dc;
	}

	/**
	 * wk 页面查看方法 goodsid传过来的值 然后 查看相应的物品
	 * 
	 * @return
	 */
	public String chaGoodsMangejsp()

	{
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		String goodsID = request.getParameter("goodsID");
		CAccount account = (CAccount) session.get("account");
		List<CCode>	priceManageList = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000032");
		String hqlForMan = "from Staff where staffID = ?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		if (goodsID != null && !goodsID.equals("")) {
			String subhql = "from GoodsManage where goodsID = ?";
			// 根据GOODS ID查询 物品里面的GOODSID 然后 返回一个物品实体提供给查询车辆使用
			goodsManage = (GoodsManage) baseBeanService.getBeanByHqlAndParams(
					subhql, new Object[] { goodsID });
			String carhql = "from CarInformation where carFrameNum = ? or engineNum = ?";
			
			if (goodsManage.getManufacturers() != null
					&& !goodsManage.getManufacturers().equals("")
					&& goodsManage.getMnemonicCode() != null
					&& !goodsManage.getMnemonicCode().equals("")) {
				// 根据车牌号跟车架子号 然后 查询该有没有该车。了必须两个条件同时存在
				carInformation = (CarInformation) baseBeanService
						.getBeanByHqlAndParams(carhql,
								new Object[] { goodsManage.getManufacturers(),
								goodsManage.getMnemonicCode() });
			}
		}	
	    //物品查看价格类别
		ActionContext.getContext().put("priceManageList", priceManageList);
		// 查询点击查看1人的NAME
		ActionContext.getContext().put("staff", staff);
		return "chaGoodsmanage";
	}

	/**
	 * wk 物品管理JSP 添加或者修改是操作 判断GOODSID 传进来的是选择的ID 判断有否 然后 在选 修改还是增加页面
	 * 
	 * @return
	 */
	public String addGoodsManagejsp() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		HttpServletRequest request = ServletActionContext.getRequest();
		String goodsID = request.getParameter("goodsID");
	   String  sz[]={"scode20101216zgkfwy4y8p0000000002","scode20101014v5zed7cukk0000000003"};
	      //查询CCODE里面PID等于sz里面的值 然后  
		List<CCode>  CCodes=codeService.getCCodeListByPIDs(account.getCompanyID(),sz);
		CarInformation CarInformation = null;
		GoodsManage goodsManages = null;
		// 判断GOODSID
		if (goodsID != null && !goodsID.equals("")) {
			String subhql = "from GoodsManage where goodsID = ?";
			// 根据GOODS ID查询 物品里面的GOODSID 然后 返回一个物品实体提供给查询车辆使用
			goodsManages = (GoodsManage) baseBeanService.getBeanByHqlAndParams(
					subhql, new Object[] { goodsID });
			String carhql = "from CarInformation where carFrameNum = ? or engineNum = ?";
			if (goodsManages.getManufacturers() != null
					&& !goodsManages.getManufacturers().equals("")
					&& goodsManages.getMnemonicCode() != null
					&& !goodsManages.getMnemonicCode().equals("")) {
				// 根据车牌号跟车架子号 然后 查询该有没有该车。了必须两个条件同时存在
				CarInformation = (CarInformation) baseBeanService
						.getBeanByHqlAndParams(carhql,
								new Object[] { goodsManages.getManufacturers(),
										goodsManages.getMnemonicCode() });
			}
		}
		if(iid!=null&&iid!=""){
			String hql = " from CCode where companyID = ? and codeID = ? ";
			String[] ii=iid.split("scode");
			for(String i:ii){
				if(!"".equals(i)&&i!=null){
					ccode = (CCode) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),"scode"+i});
					treeName+=ccode.getCodeValue()+",";
				}
				
			}
		}
		// 返回GOODSID 然后 让页面选择增加还是修改
		ActionContext.getContext().put("goodsID", goodsID);
		// 提供给修改页面的车辆实体
		ActionContext.getContext().put("CarInformation", CarInformation);
		//提供给页面下拉框
		ActionContext.getContext().put("CCodes", CCodes);
	
		// 修改返回的实体
		ActionContext.getContext().put("goodsManage", goodsManages);
		// 返回addgoodsmange返回添加
		return "addGoodsmanage";

	}

	// 商品管理列表
	public String getListGoodsManage() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		typelist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101014v5zed7cukk0000000002");
		standardslist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101216zgkfwy4y8p0000000002");
		variablelist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101014v5zed7cukk0000000003");
		/*
		 * Object[] params = {account.getCompanyID()}; pageForm =
		 * baseBeanService .getPageForm( (null != pageForm ?
		 * pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber),
		 * " from GoodsManage where companyID = ? and goodsState = '00'",
		 * params);
		 */
		pageForm = baseBeanService.getPageFormByDC(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), getListBYDC());
		ActionContext.getContext().getSession().put("session_value",
				Math.random() + "");// 在不使用token的情况下,手动防止重复提交;
		HttpServletRequest req=ServletActionContext.getRequest();
		String basic =req.getParameter("basic");
		if(basic!=null && !"".equals(basic)){
			code = (CCode) codeService.getCCodeByID(account.getCompanyID(), basic);
			if(code!=null&&"004".equals(code.getCodePID())){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(basic,code.getCodeValue());
				JSONObject oj = JSONObject.fromObject(map);
				ActionContext.getContext().put("basicMap", oj.toString());
			}
			else{
				ActionContext.getContext().put("basicMap", JSONObject.fromObject(Constant.BASIC_CONFIG).toString());
			}
			ActionContext.getContext().put("basic", basic);
			ActionContext.getContext().put("codeHylb", Constant.CODE_HYLB);
		}
		return "list";

	}

	public String getListGoodsManageBygoodsCodingOrgoodsName() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = " from GoodsManage where ( goodsCoding like ? or goodsName like ? ) and companyID = ? ";
		Object[] parms = { "%" + parameter + "%", "%" + parameter + "%",
				account.getCompanyID() };
		List<BaseBean> baseBeanList = baseBeanService
				.getListBeanByHqlAndParams(hql, parms);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsManageList", baseBeanList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	public String showExcel() {
		excelStream = excelService.showExcel(GoodsManage.columnHeadings(),
				baseBeanService.getListByDC(getListBYDC()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		CLogBook cLogBook = logBookService
				.saveCLogBook(null, "导出物品管理", account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}

	public String getAssetsWaterList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		Object[] params = { account.getCompanyID() };
		String staffhql = "from Staff where staffID in ( select staffID from COS where companyID=? and  cosStatus='50' )";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql, params);
		billTypes = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000029");
		costTypeList = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000030");
		getList();
		return "assetslist";
	}

	/**
	 * @author lou
	 * @param name
	 *            :查询物品类别
	 * @return
	 */
	public String AjaxgoodType() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List<CCode> gotypelist = codeService.getCCodeListByPID(account
				.getCompanyID(),
				codepid != null && !codepid.equals("") ? codepid
						: "scode20101014v5zed7cukk0000000002");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gotypelist", gotypelist);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	public String showExcelAssetsWater() {
		List<String> list = new ArrayList<String>();
		list.add("序号");
		list.add("部门");
		list.add("责任人");
		list.add("款源时间");
		list.add("入账时间");
		list.add("费用类别");
		list.add("单据类别");
		list.add("黏贴单编号");
		list.add("物品编号");
		list.add("物品名称");
		list.add("单位");
		list.add("出库");
		list.add("入库");
		list.add("库存");
		list.add("单价");
		list.add("借方金额");
		list.add("贷方金额");
		list.add("库存余额");
		list.add("审核情况");
		list.add("原因备注");
		String[] obj = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			obj[i] = list.get(i);
		}
		export = true;
		excelStream = excelService.showExcel(obj, getList());
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		CLogBook cLogBook = logBookService.saveCLogBook(null, "导出资产管理明细流水",
				account);
		baseBeanService.update(cLogBook);
		return "showexcel";
	}

	@SuppressWarnings("unchecked")
	public List<BaseBean> getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String sql = "select t.departmentname,t.staffname,t.startDate,t.endDate,t.costType,t.billsType,t.journalNum,t.goodscoding,t.goodsname,t.variableid,"
				+ "case when t.depotType = '出库' then t.quantity else '0' end,case when t.depotType = '入库' then t.quantity else '0' end,"
				+ " case when t.depotType = '入库' then t.quantity else '0' end-case when t.depotType = '出库' then t.quantity else '0' end,"
				+ "t.price, case when t.direction = '借' then t.money else '0' end,case when t.direction = '贷' then t.money else '0' end,"
				+ "case when t.direction = '贷' then t.money else '0' end-case when t.direction = '借' then t.money else '0' end,"
				+ "case when t.status = '04' then '已审不报税' else '已审报税' end";
		String sql1 = " from goods_cashiervo t where (t.status = '04' or t.status = '05') and (t.depotType = '入库' or t.depotType = '出库') and t.companyID = ? and t.organizationID = ?";
		List<Object> list = new ArrayList<Object>();
		list.add(account.getCompanyID());
		list.add(organizationID);
		if (search != null && "search".equals(search)) {
			goodsCashierBillsVO = (GoodsCashierBillsVO) session
					.get("tablesearch");
			if (goodsCashierBillsVO.getDepartmentID() != null
					&& !"".equals(goodsCashierBillsVO.getDepartmentID())) {
				sql1 += " and t.departmentID=?";
				list.add(goodsCashierBillsVO.getDepartmentID());
			}
			if (goodsCashierBillsVO.getJournalNum() != null
					&& !"".equals(goodsCashierBillsVO.getJournalNum())) {
				sql1 += " and t.journalNum=?";
				list.add(goodsCashierBillsVO.getJournalNum());
			}
			if (goodsCashierBillsVO.getStaffID() != null
					&& !"".equals(goodsCashierBillsVO.getStaffID())) {
				sql1 += " and t.staffID=?";
				list.add(goodsCashierBillsVO.getStaffID());
			}
			if (goodsCashierBillsVO.getBillsType() != null
					&& !"".equals(goodsCashierBillsVO.getBillsType())) {
				sql1 += " and t.billsType=?";
				list.add(goodsCashierBillsVO.getBillsType());
			}
			if (goodsCashierBillsVO.getCostType() != null
					&& !"".equals(goodsCashierBillsVO.getCostType())) {
				sql1 += " and t.costType=?";
				list.add(goodsCashierBillsVO.getCostType());
			}
			if (sdate != null && !"".equals(sdate) && edate != null
					&& !"".equals(edate)) {
				sql1 += " and  t.startDate between to_date(?,'yyyy-mm-dd') and  to_date(?,'yyyy-mm-dd')";
				list.add(sdate.trim());
				list.add(edate.trim());
			}
		}
		if (export) {
			List<BaseBean> list1 = baseBeanService.getListBeanBySqlAndParams(
					sql + sql1, list.toArray());
			return list1;
		} else {
			pageForm = baseBeanService.getPageFormBySQL(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 10 : pageNumber), sql + sql1,
					"select count(1) " + sql1, list.toArray());
			return pageForm == null ? null : pageForm.getList();
		}
	}

	// 中文字母转换
	/*
	 * public String SavdFileAjax(){ CAccount account = (CAccount)
	 * ActionContext.getContext().getSession().get("account"); Map<String,
	 * Object> map = new HashMap<String, Object>(); Map<String, Object> session
	 * = ActionContext.getContext().getSession(); map.put("nologin", 0);
	 * if(account == null){ map.put("nologin", 1); JSONObject obj =
	 * JSONObject.fromObject(map); result = obj.toString();22222 return
	 * "success"; } String hql=
	 * "select count(vt.goodsCoding) from GoodsManage vt where vt.companyID in (select c.companyID from Company c where c.groupCompanySn=?)  and vt.typeID=? "
	 * ; String type=goodsManage.getTypeID();
	 * type=type.substring(type.lastIndexOf(")")+1); Object[]
	 * params={session.get("groupCompanySn"),type}; //ChineseSpelling
	 * chinesespelling=new ChineseSpelling(); //获取拼音码 String
	 * pinyin=ToChineseFirstLetter.getFirstLetter(type); String Upstr =
	 * pinyin.toUpperCase();//转换为大写 int goodsManage=
	 * baseBeanService.getConutByByHqlAndParams(hql, params); DecimalFormat
	 * form=new DecimalFormat("000000"); String ss=form.format(goodsManage+1);
	 * String sss=Upstr+"_"+ss; map.put("goodsManage",sss ); JSONObject obj =
	 * JSONObject.fromObject(map); result = obj.toString(); return "success"; }
	 */
	public String pinyinchang() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nologin", 0);
		if (account == null) {
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String pinyin = ToChineseFirstLetter.getFirstLetter(goodsManage
				.getTypeID());
		map.put("goodsManage", pinyin);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	public String toSearchAssetsWater() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", goodsCashierBillsVO);
		return getAssetsWaterList();
	}

	/**
	 * 
	 * 判断芯片是否重复
	 * 
	 * @return
	 */
	public String IsChipRepeat() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String chipid = request.getParameter("chipid");
		String hql = "from GoodsManage where defaultStorage = ?";
		List<BaseBean> archivelist = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { chipid.trim() });
		Map<String, Object> map = new HashMap<String, Object>();
		if (archivelist.size() == 0) {
			map.put("result", "success");// 不重复
		} else {
			map.put("result", "fail");
		}
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";
	}
	public void unionpay(){
		//交易测试
	/*	String orderID= unionpayMeth.frontConsume("http://test.impf2010.com//ea/productdesign/ea_getFilePackageProduct.jspa?produce=group","http://test.impf2010.com//ea/productdesign/ea_getFilePackageProduct.jspa?produce=group", "100","");
		System.out.println(orderID);*/
		/*//查询测试
		try {
			Map<String, String> order= unionpayMeth.query("20150625100528", "20150625100528");
			System.out.println(order.get("respMsg"));
			System.out.println(order.get("queryId"));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//撤销交易测试（和退货测试只能执行一个）
		Map<String, String> consumeUndo= unionpayMeth.consumeUndo("http://localhost:8080/hyplat//ea/wfjcustomer/ea_addcpjsp.jspa",  "0", "201506251005286856648", "1");
		System.out.println(consumeUndo.get("respMsg"));
		//退货测试（和撤销交易测试只能执行一个）		
		try {
			Map<String, String> Refund= unionpayMeth.Refund("http://localhost:8080/hyplat//ea/wfjcustomer/ea_addcpjsp.jspa",  "0", "201506251005286856648", "1");
			System.out.println(Refund.get("respMsg"));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		
		
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public GoodsManage getGoodsManage() {
		return goodsManage;
	}

	public void setGoodsManage(GoodsManage goodsManage) {
		this.goodsManage = goodsManage;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public List<CCode> getTypelist() {
		return typelist;
	}

	public void setTypelist(List<CCode> typelist) {
		this.typelist = typelist;
	}

	public List<CCode> getVariablelist() {
		return variablelist;
	}

	public void setVariablelist(List<CCode> variablelist) {
		this.variablelist = variablelist;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<CCode> getStandardslist() {
		return standardslist;
	}

	public void setStandardslist(List<CCode> standardslist) {
		this.standardslist = standardslist;
	}

	public String getPName() {
		return pName;
	}

	public void setPName(String name) {
		pName = name;
	}

	public List<BaseBean> getStafflist() {
		return stafflist;
	}

	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
	}

	public List<CCode> getBillTypes() {
		return billTypes;
	}

	public void setBillTypes(List<CCode> billTypes) {
		this.billTypes = billTypes;
	}

	public GoodsCashierBillsVO getGoodsCashierBillsVO() {
		return goodsCashierBillsVO;
	}

	public void setGoodsCashierBillsVO(GoodsCashierBillsVO goodsCashierBillsVO) {
		this.goodsCashierBillsVO = goodsCashierBillsVO;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public List<CCode> getCostTypeList() {
		return costTypeList;
	}

	public void setCostTypeList(List<CCode> costTypeList) {
		this.costTypeList = costTypeList;
	}

	public CarInformation getCarInformation() {
		return carInformation;
	}

	public void setCarInformation(CarInformation carInformation) {
		this.carInformation = carInformation;
	}

	public List<BaseBean> getCarInformationList() {
		return carInformationList;
	}

	public void setCarInformationList(List<BaseBean> carInformationList) {
		this.carInformationList = carInformationList;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getCodepid() {
		return codepid;
	}

	public void setCodepid(String codepid) {
		this.codepid = codepid;
	}

	public String getIid() {
		return iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}

	public CCode getCcode() {
		return ccode;
	}

	public void setCcode(CCode ccode) {
		this.ccode = ccode;
	}

	public String getTreeName() {
		return treeName;
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	public CCode getCode() {
		return code;
	}

	public void setCode(CCode code) {
		this.code = code;
	}
	

}
