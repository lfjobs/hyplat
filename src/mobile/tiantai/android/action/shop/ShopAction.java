package mobile.tiantai.android.action.shop;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.tiantai.wfj.bo.Order;
import com.tiantai.wfj.bo.OrderProduct;
import com.tiantai.wfj.bo.TEshopADDress;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.bo.TEshoptuihuan;

import hy.ea.bo.Company;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.finance.RelatedBill;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.bo.CartProduct;
import mobile.tiantai.android.bo.CusToCom;
import net.sf.json.JSONObject;

/**
 * 微分金接口
 * 
 * @author zg
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class ShopAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware {
	@Resource
	protected BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	protected int pageNumber;
	protected HttpServletRequest request;
	private PageForm  PageForm;
	protected HttpServletResponse response;
	private Object result;
	private SimpleDateFormat   sf=new SimpleDateFormat("yyyy-MM-dd-HH-mm:ss");

	/** 根据关键字查询公司 ***/
	public String findComBy() {
		String word = request.getParameter("word");
		try {
			if (word != null && !word.trim().equals("")) {
				String hql = "select c.companyID,c.companyName,c.industryType from Company c where c.companyStatus=? and c.companyName like ?";
				List<BaseBean> list = baseBeanService
						.getListBeanByHqlAndParams(hql, new Object[] { "00",
								"%" + word + "%" });
				if (list != null) {

					JSONObject jo = new JSONObject();
					for (Object bb : list) {
						Object[] c = (Object[]) bb;
						JSONObject temp = new JSONObject();
						temp.accumulate("companyID", c[0] == null ? "" : c[0]);
						temp.accumulate("companyName", c[1] == null ? "" : c[1]);
						temp.accumulate("industryType", c[2] == null ? ""
								: c[2]);
						jo.accumulate("company", temp);
					}
					result = jo;
					System.out.println("结果：" + result);
				}
			} else {
				result = "word参数必传";
			}
		} catch (Exception e) {
			System.out.println("findComBy异常");
		}

		return Action.SUCCESS;
	}

	/**
	 * 根据公司查询店铺
	 * 
	 * @param companyId
	 *            公司Id
	 * @param search
	 *            值：searchShops需要跟orgName一块传
	 * @param pno
	 *            展示页数默认1
	 * @param orgName
	 *            店铺名称（部门名称）
	 * @param sort
	 *            值：bhjx时ocode降序 值：dmjx时 部门名称降序
	 * @return
	 */
	public String findShopBy() {
		String companyId = request.getParameter("companyId");
		
		String search = request.getParameter("search");
		String pn = request.getParameter("pno");
		String  name=request.getParameter("dianpu");
		String orgName = request.getParameter("orgName") == null ? "" : request
				.getParameter("orgName");
		String sort = request.getParameter("sort");
		List<Object> list = new ArrayList<Object>();
		try {
			List<Object> parms = new ArrayList<Object>();
			String sql = "select co.organizationid, co.organizationname,co.ocode,sh.star,sh.titleimage,co.companyID ,sh.address"
					+ " from dtcorganization co,T_ESHOP_EXTINFO sh "
					+ " where co.organizationid=sh.organizationid and co.iswfj='是' and sh.eshopstatus='0' and sh.inused='1' and co.Status='00' ";
			String sc = "select count(*)"
					+ " from dtcorganization co,T_ESHOP_EXTINFO sh "
					+ " where co.organizationid=sh.organizationid and co.iswfj='是' and sh.eshopstatus='0' and sh.inused='1' and co.Status='00' ";
			if (search != null && "searchShops".equals(search)
					&& !orgName.equals("")) {
				sql += " and ( co.organizationName like ?";
				parms.add("%" + orgName + "%");
				sql += " or co.ocode like ?";
				parms.add("%" + orgName + "%");
				sql += " or sh.address like ?";
				parms.add("%" + orgName + "%");
				sql += ")";

				sc += " and ( co.organizationName like ?";
				sc += " or co.ocode like ?";
				sc += " or sh.address like ?";
				sc += ")";
			}
			if (null != companyId && !"null".equals(companyId)) {
				sql += " and co.companyid=? ";
				sc += " and co.companyid=? ";
				parms.add(companyId);
			}
			if(name!=null&&!"".equals(name))
			{
				sql += " and co.organizationName  like ? ";
				sc += " and co.organizationName  like ? ";
				parms.add("%"+name+"%");
				
			}
			if (sort != null && sort != "" && "bhjx".equals(sort)) {
				sql += " order by  co.ocode desc";
			} else if (sort != null && sort != "" && "dmjx".equals(sort)) {
				sql += " order by  co.organizationname desc";
			} else {
				sql += " order by  co.ocode ,co.organizationname ";
			}
			PageForm pf = baseBeanService.getPageFormBySQL(
					(pn != null ? Integer.parseInt(pn) : 1), 5, sql, sc,
					parms.toArray());
			JSONObject jo = new JSONObject();
			if (pf != null && !"".equals(pf)) {
				for (Object bb : pf.getList()) {
					Object[] c = (Object[]) bb;
					JSONObject temp = new JSONObject();
					temp.accumulate("organizationid", c[0] == null ? "" : c[0]);
					temp.accumulate("organizationname", c[1] == null ? ""
							: c[1]);
					temp.accumulate("ocode", c[2] == null ? "" : c[2]);
					temp.accumulate("star", c[3] == null ? "" : c[3]);
					temp.accumulate("titleimage", c[4] == null ? "" : c[4]);
					temp.accumulate("companyID", c[5] == null ? "" : c[5]);
					temp.accumulate("address", c[6] == null ? "" : c[6]);
					list.add(temp);
				}
				jo.accumulate("Data", list);
				jo.accumulate("resultValue", "1");
			} else {
				JSONObject temp = new JSONObject();
				temp.accumulate("organizationid", "");
				temp.accumulate("organizationname", "");
				temp.accumulate("ocode", "");
				temp.accumulate("star", "");
				temp.accumulate("titleimage", "");
				temp.accumulate("companyID", "");
				temp.accumulate("address", "");
				list.add(temp);
				jo.accumulate("Data", list);
				jo.accumulate("resultValue", "0");
			}
			result = jo;
		} catch (NumberFormatException e) {
			System.out.println("findShopBy异常");
		}
		return Action.SUCCESS;
	}

	/**
	 * 根据店铺查询产品
	 * 
	 * @param search
	 *            值：search
	 * @param orgId
	 *            必传
	 * @param comId
	 *            必传
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findProductList() {
		String search = request.getParameter("search");
		String organizationID = request.getParameter("orgId");// 必传
		String goodName = request.getParameter("goodName");
		String comId = request.getParameter("comId");// 必传
		String hqlorg = "from COrganization where organizationID=?";
		JSONObject jo = new JSONObject();
		List<Object> list = new ArrayList<Object>();
		try {
			COrganization org = (COrganization) baseBeanService
					.getBeanByHqlAndParams(hqlorg,
							new Object[] { organizationID });
			// SessionWrap.getInstance().setShopName(ActionContext.getContext().getSession(),
			// org.getOrganizationName());//把店铺名称存入session
			if (org != null) {
				jo.accumulate("shopName", org.getOrganizationName());
			}
			// 公司产品
			List<Object> parms = new ArrayList<Object>();
			String sql = "select  pp.ppid,pp.goodsname,pp.image,pc.price,co.companyname,pp.goodsID,trim(pp.virtual) "
					+ " from dt_productpackaging pp, dt_productpricecategory pc ,dtcompany co"
					+ " where pp.ppid=pc.ppid and co.companyid=pp.companyID and pc.category='零售价' and  pp.showweixin='01' and pp.pptype='1'";
			if (!"".equals(search) && "search".equals(search)
					&& !goodName.equals("") && goodName != null) {// 按产品名称搜索
				sql += " and pp.goodsname like ?";
				parms.add("%" + goodName + "%");
			}
			sql += " and pp.companyid=?";
			parms.add(comId);

			String sql1 = "select goodsid, sum(invenQuantity) nums from dt_inv_invt where companyID = ? group by goodsid";
			sql = "select p.*,vv.nums from (" + sql + ") p left join (" + sql1
					+ ") vv on vv.goodsid = p.goodsid";
			parms.add(comId);
			List<BaseBean> productList = baseBeanService
					.getListBeanBySqlAndParams(sql, parms.toArray());
			if (productList != null && productList.size() > 0) {
				for (Object bb : productList) {
					Object[] c = (Object[]) bb;
					JSONObject temp = new JSONObject();
					temp.accumulate("ppid", c[0] == null ? "" : c[0]);
					temp.accumulate("goodsname", c[1] == null ? "" : c[1]);
					temp.accumulate("image", c[2] == null ? "" : c[2]);
					temp.accumulate("price", c[3] == null ? "" : c[3]);
					temp.accumulate("companyname", c[4] == null ? "" : c[4]);
					temp.accumulate("goodsID", c[5] == null ? "" : c[5]);
					temp.accumulate("virtual", c[6] == null ? "" : c[6]);
					temp.accumulate("nums", c[7] == null ? "" : c[7]);
					list.add(temp);
				}
				jo.accumulate("Data", list);
				jo.accumulate("resultValue", "1");

			} else {
				JSONObject temp = new JSONObject();
				temp.accumulate("ppid", "");
				temp.accumulate("goodsname", "");
				temp.accumulate("image", "");
				temp.accumulate("price", "");
				temp.accumulate("companyname", "");
				temp.accumulate("goodsID", "");
				temp.accumulate("virtual", "");
				temp.accumulate("nums", "");
				list.add(temp);
				jo.accumulate("Data", list);
				jo.accumulate("resultValue", "0");
			}
		} catch (Exception e) {
			System.out.println("findProductList异常");
		}
		if (jo.size() > 0) {
			result = jo;
		}
		return Action.SUCCESS;
	}

	/**
	 * 根据产品查询详情
	 * 
	 * @param ppid
	 *            必传
	 * @param orgId
	 *            必传
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findProductDetail() {
		String ppid = request.getParameter("ppid");// 必传
		String organizationID = request.getParameter("orgId");// 必传
		JSONObject jo = new JSONObject();
		try {
			// 产品明细
			String sql = "select  pp.ppid,pp.goodsname,pp.image,pp.productDetail,pp.stockSize,pp.monthSales, pc.price,pp.virtual "
					+ " from dt_productpackaging pp,dt_productpricecategory pc"
					+ " where pp.ppid=pc.ppid and pc.category='零售价' and  pp.ppid=?";
			String[] params = { ppid };
			List<Object> productList = baseBeanService
					.getListBeanBySqlAndParams(sql, params);
			if (productList != null) {
				for (Object bb : productList) {
					Object[] c = (Object[]) bb;
					JSONObject temp = new JSONObject();
					temp.accumulate("ppid", c[0] == null ? "" : c[0]);
					temp.accumulate("goodsname", c[1] == null ? "" : c[1]);
					temp.accumulate("image", c[2] == null ? "" : c[2]);
					temp.accumulate("productDetail", c[3] == null ? "" : c[3]);
					temp.accumulate("stockSize", c[4] == null ? "" : c[4]);
					temp.accumulate("monthSales", c[5] == null ? "" : c[5]);
					temp.accumulate("price", c[6] == null ? "" : c[6]);
					temp.accumulate("virtual", c[7] == null ? "" : c[7]);
					jo.accumulate("productinfo", temp);
				}
			}
			// 店铺信息
			String sql2 = "select co.organizationid, co.organizationname,sh.star,sh.address ,"
					+ " (select st.num from  t_eshop_starlevel st,T_ESHOP_EXTINFO sh where sh.star between st.beginmark and st.endmark)as cnum,"
					+ " (select st.imagepath from  t_eshop_starlevel st,T_ESHOP_EXTINFO sh where sh.star between st.beginmark and st.endmark)as imagepath,"
					+ " co.companyid from dtcorganization co,T_ESHOP_EXTINFO sh "
					+ " where co.organizationid=sh.organizationid and co.organizationID=? and sh.inused='1'";
			List<Object> beans = baseBeanService.getListBeanBySqlAndParams(
					sql2, new Object[] { organizationID });
			if (beans != null) {
				for (Object bb : beans) {
					Object[] c = (Object[]) bb;
					JSONObject temp = new JSONObject();
					temp.accumulate("organizationid", c[0] == null ? "" : c[0]);
					temp.accumulate("organizationname", c[1] == null ? ""
							: c[1]);
					temp.accumulate("star", c[2] == null ? "" : c[2]);
					temp.accumulate("address", c[3] == null ? "" : c[3]);
					temp.accumulate("cnum", c[4] == null ? "" : c[4]);
					temp.accumulate("imagepath", c[5] == null ? "" : c[5]);
					temp.accumulate("companyid", c[6] == null ? "" : c[6]);
					jo.accumulate("shopinfo", temp);
				}
			}
		} catch (Exception e) {
			System.out.println("findProductDetail异常");
		}
		if (jo.size() > 0) {
			result = jo;
		}
		return Action.SUCCESS;
	}

	/**
	 * 根据用户名保存关注的公司 zll
	 * 
	 * @return 0 异常 1 成功 2 已存在
	 * 
	 */
	public String saveCusAttentionCom() {
		JSONObject jo = new JSONObject();

		String user = request.getParameter("user");
		String companyID = request.getParameter("companyID");
		String companyName = request.getParameter("companyName");
		if (user != null && companyID != null && companyName != null) {
			CusToCom cusToCom = (CusToCom) baseBeanService
					.getBeanByHqlAndParams(
							" from CusToCom where account=? and comId=? and logOff=0",
							new Object[] { user, companyID });
			if (cusToCom != null) {
				jo.accumulate("result", "2");
			} else {
				CusToCom cTc = new CusToCom();
				cTc.setCtcId(serverService.getServerID("custocus"));
				cTc.setAccount(user);
				cTc.setComId(companyID);
				cTc.setComName(companyName);
				baseBeanService.save(cTc);
				jo.accumulate("result", "1");
			}
		} else {
			jo.accumulate("result", "0");
		}

		result = jo;
		return Action.SUCCESS;
	}

	/**
	 * 根据登陆用户查关注公司
	 * 
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getComByAccount() {
		JSONObject jo = new JSONObject();
		String user = request.getParameter("user");
		String sql = "select comId,comName from dt_custocom c where c.account=? ";
		List<BaseBean> comList = baseBeanService.getListBeanBySqlAndParams(sql,
				new Object[] { user });
		List<Object> list = new ArrayList<Object>();
		if (comList != null && comList.size() > 0) {
			JSONObject temp = null;
			for (Object bb : comList) {
				Object[] c = (Object[]) bb;
				temp = new JSONObject();
				temp.accumulate("companyId", c[0] == null ? "" : c[0]);
				temp.accumulate("companyName", c[1] == null ? "" : c[1]);
				list.add(temp);
			}
			jo.accumulate("companyInfo", list);
			jo.accumulate("resultValue", "1");
		} else {
			JSONObject temp = new JSONObject();
			temp.accumulate("companyId", "");
			temp.accumulate("companyName", "");
			list.add(temp);
			jo.accumulate("companyInfo", list);
			jo.accumulate("resultValue", "0");
		}
		result = jo;
		return Action.SUCCESS;
	}

	// /**
	// * 加入购物车 zll
	// * @return
	// * http://192.168.0.8/ea/android/shop_ajax_putInCart.jspa
	// */
	// public String putInCart() {
	// JSONObject jo = new JSONObject();
	// String ppId=request.getParameter("ppId");
	// String customer=request.getParameter("customer");
	// if( ppId!=null && customer!=null){
	// CartProduct cartProduct=new CartProduct();
	// cartProduct.setCartId(serverService.getServerID("cartproduct"));
	// cartProduct.setPpId(ppId);
	// cartProduct.setCustomer(customer);
	// baseBeanService.save(cartProduct);
	// jo.accumulate("statue", "true");
	// }else{
	// if( ppId==null)
	// {
	// jo.accumulate("message", "产品ID等于空");
	// }
	// if( customer==null)
	// {
	// jo.accumulate("message","用户ID等于空等于空");
	// }
	// jo.accumulate("statue", "false");
	// }
	// result = jo;
	// return Action.SUCCESS;
	//
	// }
   
	/**
	 * wk
	 * 获取收货地址根据用户ID
	 * http://192.168.0.8/ea/android/shop_ajax_getaddress.jspa?customer=根据谁查询
	 * @return
	 */
	
	public String getaddress()
	{
		JSONObject jo = new JSONObject();
		// 用户ID
		String customer = request.getParameter("customer");
		List<Object> list = new ArrayList<Object>();
		
		String hql="from TEshopADDress where userID=?";
		List<BaseBean>  address=(List<BaseBean>) baseBeanService.getListBeanByHqlAndParams(hql, new String[]{customer});
		if(address.size()>0)
		{
			for(BaseBean addressa :address)
			{	
				TEshopADDress address1=(TEshopADDress) addressa;
				JSONObject temp = new JSONObject();
				
				temp = new JSONObject();
				temp.accumulate("asID",
						address1.getAsID() == null ? "" : address1.getAsID());
				temp.accumulate("customer", address1.getUserID() == null ? ""
						: address1.getUserID());
				temp.accumulate("city",
						address1.getAsshi() == null ? "" : address1.getAsshi());
				temp.accumulate("area", address1.getAsquxian() == null ? ""
						: address1.getAsquxian());
				temp.accumulate("prov", address1.getAssheng() == null ? ""
						: address1.getAssheng());
				temp.accumulate("add", address1.getAsxiangx() == null ? ""
						: address1.getAsxiangx());
				temp.accumulate("number", address1.getAsdianhua() == null ? ""
						: address1.getAsdianhua());
				temp.accumulate("name", address1.getAsname() == null ? ""
						: address1.getAsname());
				list.add(temp);
			}
			
			jo.accumulate("statue", "success");
			jo.accumulate("message", "");
			jo.accumulate("data", list);
			
		}
		else{

			jo.accumulate("message", "参数有空值");
			jo.accumulate("statue", "fail");
			jo.accumulate("data", "");
			
		}
		result = jo;
		return Action.SUCCESS;	
	}
	/**
	 * 添加收货地址 wk
	 * http://192.168.0.8/ea/android/shop_ajax_addaddress.jspa?customer
	 * =用户ID&prov=省&city=市&area=区县&add=详细地址&number=电话&name=收货人姓名 * @return
	 * addreslist json返回添加地址的信息
	 */

	public String addaddress() {
		JSONObject jo = new JSONObject();
		// 用户ID
		String customer = request.getParameter("customer");
		// 省
		String prov = request.getParameter("prov");
		// 市
		String city = request.getParameter("city");
		// 区县
		String area = request.getParameter("area");
		// 详细地址
		String add = request.getParameter("add");
		// 收货人电话
		String number = request.getParameter("number");
		// 收货人姓名
		String name = request.getParameter("name");
		
		if (yanzheng(new String[]{customer,prov,city,area,add,number,name})) {
			String hql1 = "from TEshopCustomer where  staffid = ? ";
			TEshopCustomer customer1 = (TEshopCustomer) baseBeanService
					.getBeanByHqlAndParams(hql1, new Object[] { customer });
			String hql2 = "from Staff where staffID=?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
					new Object[] { customer });
				            if(staff!=null&&customer1!=null){
									TEshopADDress address = new TEshopADDress();
									address.setAsID(serverService.getServerID("TEshopADDress"));
									address.setUserID(customer);
									address.setAssheng(prov);
									address.setAsshi(city);
									address.setAsquxian(area);
									address.setAsxiangx(add);
									address.setAsdianhua(number);
									address.setAsname(name);
									baseBeanService.save(address);
									jo.accumulate("statue", "true");
									String hql = "from TEshopADDress  where userid=?";
									TEshopADDress address1 = new TEshopADDress();
									address1 = (TEshopADDress) baseBeanService.getBeanByHqlAndParams(
											hql, new String[] { customer });
									JSONObject temp = null;
									List<Object> list = new ArrayList<Object>();
									temp = new JSONObject();
									temp.accumulate("key",
											address1.getAskey() == null ? "" : address1.getAskey());
									temp.accumulate("customer", address1.getAsID() == null ? ""
											: address1.getAsID());
									temp.accumulate("city",
											address1.getAsshi() == null ? "" : address1.getAsshi());
									temp.accumulate("area", address1.getAsquxian() == null ? ""
											: address1.getAsquxian());
									temp.accumulate("prov", address1.getAssheng() == null ? ""
											: address1.getAssheng());
									temp.accumulate("add", address1.getAsxiangx() == null ? ""
											: address1.getAsxiangx());
									temp.accumulate("number", address1.getAsdianhua() == null ? ""
											: address1.getAsdianhua());
									temp.accumulate("name", address1.getAsname() == null ? ""
											: address1.getAsname());
									list.add(temp);
								
									jo.accumulate("statue", "success");
									jo.accumulate("message", "");
									jo.accumulate("data", list);
				            }
				            else
				            {
				            	
				            
								jo.accumulate("statue", "fail");
								jo.accumulate("message", "用户为空");
								jo.accumulate("data", "");
				            }
		} else {
			
			jo.accumulate("message", "参数有空值");
			jo.accumulate("statue", "fail");
			jo.accumulate("data", "");
		}
		result = jo;
		return Action.SUCCESS;

	}

	/**
	 * 检测是否有存库WK
	 * 
	 * http://192.168.0.8/ea/android/shop_ajax_getnum.jspa?prodID=产品id
	 * 
	 * @return
	 */
	public String getnum() {
		JSONObject jo = new JSONObject();
		String prodID = request.getParameter("prodID");

		String hql = "from ProductPackaging   where ppID=?";

		ProductPackaging pk = new ProductPackaging();
		pk = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql,
				new String[] { prodID });
		if(pk!=null)
		{
		if (0 < Integer.parseInt(pk.getQuantity())) {
			JSONObject temp = null;
			List<Object> list = new ArrayList<Object>();
			temp = new JSONObject();
			temp.accumulate("ppID", pk.getPpID() == null ? "" : pk.getPpID());
			temp.accumulate("goodsName",
					pk.getGoodsName() == null ? "" : pk.getGoodsName());
			temp.accumulate("price", pk.getPrice() == null ? "" : pk.getPrice());
			temp.accumulate("weiDianType", pk.getWeiDianType() == null ? ""
					: pk.getWeiDianType());
			temp.accumulate("manualUrl",
					pk.getManualUrl() == null ? "" : pk.getManualUrl());
			temp.accumulate("image", pk.getImage() == null ? "" : pk.getImage());
			temp.accumulate("photo", pk.getPhoto() == null ? "" : pk.getPhoto());
			temp.accumulate("logo", pk.getLogo() == null ? "" : pk.getLogo());
			temp.accumulate("ppstatus",
					pk.getPpstatus() == null ? "" : pk.getPpstatus());
			temp.accumulate("ppwhether",
					pk.getPpwhether() == null ? "" : pk.getPpwhether());
			temp.accumulate("giveNumber",
					pk.getGiveNumber() == null ? "" : pk.getGiveNumber());
			temp.accumulate("stockSize",
					pk.getStockSize() == 0 ? "0" : pk.getStockSize());
			temp.accumulate("PackagingDate", pk.getPackagingDate() == null ? ""
					: pk.getPackagingDate());
			temp.accumulate("companyID",
					pk.getCompanyID() == null ? "" : pk.getCompanyID());
			list.add(temp);
			jo.accumulate("status", "success");
			jo.accumulate("message", "");
			jo.accumulate("data", list);
		} else {
			jo.accumulate("data", "");
			jo.accumulate("status", "fail");
			jo.accumulate("message", "没有存库了");
		}
		
		}
		else{
			
			jo.accumulate("data", "");
			jo.accumulate("status", "fail");
			jo.accumulate("message", "没有该产品");
		}
		// quantity
		result = jo;
		return Action.SUCCESS;

	}

	/**
	 * 加入购物车wk
	 * http://192.168.0.8/ea/android/shop_ajax_addCartProduct.jspa?customer
	 * =用户ID&proID商品ID
	 */
	@SuppressWarnings("unused")
	public String addCartProduct() {

		JSONObject jo = new JSONObject();
		String customer = request.getParameter("customer");
		String proID = request.getParameter("proID");
		String hql = "from ProductPackaging   where ppID=?";
		ProductPackaging pk = new ProductPackaging();
		pk = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql,
				new String[] { proID });
		String hql1 = "from TEshopCustomer where  staffid = ? ";
		TEshopCustomer customer1 = (TEshopCustomer) baseBeanService
				.getBeanByHqlAndParams(hql1, new Object[] { customer });
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { customer });

		if (staff != null) {
			if (pk != null) {
				String hql3 = "from CartProduct where  customer = ? and  ppId=?";
				CartProduct ct = (CartProduct) baseBeanService
						.getBeanByHqlAndParams(hql3, new Object[] { customer,
								proID });

				if (ct == null) {
					CartProduct cartProduct = new CartProduct();
					cartProduct.setCartId(serverService
							.getServerID("cartproduct"));
					cartProduct.setPpId(proID);
					cartProduct.setCustomer(customer);
					baseBeanService.save(cartProduct);
					jo.accumulate("status", "success");
					jo.accumulate("message"	, "");
					jo.accumulate("data", "");

				} else {
					jo.accumulate("status", "success");
					jo.accumulate("data", "");

				}
			} else {
				jo.accumulate("status", "fail");
				jo.accumulate("message", "没有该产品");
				jo.accumulate("data", "");
			}
		} else {
			jo.accumulate("status", "fail");
			jo.accumulate("message", "没有该用户");
			jo.accumulate("data", "");
		}
		result = jo;
		return Action.SUCCESS;

	}

	/**
	 * 删除购物车内产品ID ID可多个
	 * 
	 * http://192.168.0.8/ea/android/shop_ajax_deletedCartProduct.jspa?customer=
	 * 用户ID&PROid=商品ID-多个商品ID
	 * 
	 * @return
	 */
	public String deletedCartProduct() {
		JSONObject jo = new JSONObject();
		String customer = request.getParameter("customer");
		String proid = request.getParameter("proid");
		if(yanzheng(new String[]{proid,customer})){
				String[] pd = proid.split("-");
				String hql = "delete CartProduct   where ppID=? and customer=?";
				if (pd.length > 0) {
					for (String id : pd) {
						try {
							baseBeanService
									.saveBeansListAndexecuteHqlsByParams(null,
											new String[] { hql }, new String[] { id,
													customer });
							jo.accumulate("status", "success");
							jo.accumulate("message", "");
					
		
						} catch (Exception e) {
							// TODO: handle exception
							jo.accumulate("status", "fail");
							jo.accumulate("message", "");
					
						} finally {
							
							jo.accumulate("data", "");
						}
		
					}
				} else {
		
					jo.accumulate("status", "fail");
					jo.accumulate("message", "");
					jo.accumulate("data", "");
				}
		}
		else
		{
			
			jo.accumulate("status", "fail");
			jo.accumulate("message", "值不能为空");
			jo.accumulate("data", "");
			
		}

		result = jo;
		return Action.SUCCESS;
	}

	/**
	 * 查询购物车
	 *  http://192.168.0.8/ea/android/shop_ajax_getlistCartProduct.jspa?
	 * customer=用户ID
	 * @return
	 */
	public String getlistCartProduct() {
		JSONObject jo = new JSONObject();
		String customer = request.getParameter("customer");
		String sql = "from CartProduct where  customer = ?";
		List<BaseBean> proList = baseBeanService.getListBeanByHqlAndParams(sql,
				new Object[] { customer });
		List<Object> list = new ArrayList<Object>();
		if (proList != null && proList.size() > 0) {
		    String hql="from ProductPackaging   where PPID in(?)";
			JSONObject temp = null;
			String _temp="";
			for (Object bb : proList) {
				CartProduct c =   (CartProduct) bb;
				_temp+=""+c.getPpId()+",";
			}
		    String[] params= _temp.split(",");
			for (String  bb :params) {
				ProductPackaging pk1=(ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql, new String[]{bb});			
				ProductPackaging c =pk1;
				temp = new JSONObject();		
//				ProductPackaging pk12= (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql, new String[]{c.getPpId()});
						if(c!=null)
						{
							temp.accumulate("ppID",c.getPpID()  == null ? "" : c.getPpID());
						 temp.accumulate("goodsName",c.getGoodsName()  == null ? "" :c.getGoodsName() );					
						 temp.accumulate("money",c.getMoney() == null ? "" : c.getMoney());				
						 temp.accumulate("manualUrl",c.getManualUrl()  == null ? "" : c.getManualUrl());				
						 temp.accumulate("photo",c.getPhoto()  == null ? "" : c.getPhoto() );					
						 temp.accumulate("ppstatus",c.getPpstatus()  == null ? "" :c.getPpstatus());					
						 temp.accumulate("ppwhether",c.getPpwhether()  == null ? "" : c.getPpwhether());
						 temp.accumulate("giveNumber",c.getGiveNumber()  == null ? "" : c.getGiveNumber());				
						 temp.accumulate("ppwhether",c.getPpwhether()  == null ? "" : c.getPpwhether());		
						 temp.accumulate("stockSize", c.getStockSize());					
						 temp.accumulate("organizationID",c.getOrganizationID()  == null ? "" : c.getOrganizationID());					
		//				 temp.accumulate("ppID",pk1.getPpID()  == null ? "" : pk1.getPpID());					
		//				 temp.accumulate("ppID",pk1.getPpID()  == null ? "" : pk1.getPpID());
		//					
							//temp.accumulate("t", c);//				 
							//temp.accumulate("data", c);
						 
						}
						else
						{
							jo.accumulate("message", "");
						}
						list.add(temp);
				
			}	
			jo.accumulate("message", "");
			jo.accumulate("status", "success");	
			jo.accumulate("data", list);
		} else {
			JSONObject temp = new JSONObject();
			temp.accumulate("ppID","");
			 temp.accumulate("ppID","");
			 temp.accumulate("goodsName", "");					
			 temp.accumulate("money","");				
			 temp.accumulate("manualUrl","");				
			 temp.accumulate("photo","");					
			 temp.accumulate("ppstatus","" );					
			 temp.accumulate("ppwhether","");
			 temp.accumulate("giveNumber","");				
			 temp.accumulate("ppwhether","");		
			 temp.accumulate("stockSize", "");					
			 temp.accumulate("organizationID","");
			 list.add(temp);
			jo.accumulate("data", list);
		}
		result = jo;
		return Action.SUCCESS;
	}
	/**
	 * 
	 * 获取订单信息
	 * http://192.168.0.8/ea/android/shop_ajax_getdd.jspa?customer=用户id&status=订单状态
	 * 
	 */
	public String getdd()
	{
			
		JSONObject jo = new JSONObject();
		String customer = request.getParameter("customer");
		String status = request.getParameter("status");
		String pageNumber = request.getParameter("pageNumber");
		String pageSize = request.getParameter("pageSize");
		List<Object> list=new ArrayList<Object>();
		if(yanzheng(new String[]{customer,pageNumber,pageSize}))
		{
			if(status!=null)
			{
				
				String hql="from Order where userID=? ";
				List<Object>  lis1t=new ArrayList<Object>();
				lis1t.add(customer);
				if(!"".equals(status))
				{
					hql+=" and  orderType=?";
					lis1t.add(status);
					
				}						
				PageForm=baseBeanService.getPageForm(Integer.parseInt(pageNumber),Integer.parseInt(pageSize), hql,lis1t.toArray());
				if(PageForm!=null)
				{
				for (Object o:PageForm.getList()) {
					JSONObject temp = new JSONObject();
					Order  order=(Order)o;				
					String hql1="from ProductPackaging   where ppID=?";
					temp.accumulate("userID",order.getUserID()==null?"":order.getUserID());//用户ID
					temp.accumulate("supplierID",order.getSupplierID()==null?"":order.getSupplierID());//售货方ID
					temp.accumulate("receiveAddress",order.getReceiveAddress()==null?"":order.getReceiveAddress());//收货地址	
					temp.accumulate("createTime", sf.format(order.getCreateTime())==null?"":sf.format(order.getCreateTime()));//订单生成时间	
					temp.accumulate("orderType", order.getOrderType()==null?"":order.getOrderType());
					temp.accumulate("orderMoney", order.getOrderMoney()==null?"":order.getOrderMoney());
					temp.accumulate("paymentType", order.getPaymentType()==null?"":order.getPaymentType());
					temp.accumulate("paymentNumber", order.getPaymentNumber()==null?"":order.getPaymentNumber());
					temp.accumulate("paymentTime", order.getPaymentTime()==null?"":order.getPaymentTime());		
					temp.accumulate("updateTime", order.getUpdateTime()==null?"":order.getUpdateTime());
					temp.accumulate("updateUser", order.getUpdateUser()==null?"":order.getUpdateUser());
					temp.accumulate("sendProductID", order.getSendProductID()==null?"":order.getSendProductID());
					temp.accumulate("freight", order.getFreight()==null?"":order.getFreight());
					temp.accumulate("orderID",order.getOrderID()==null?"":order.getOrderID());
					String hlq="from OrderProduct  where orderID=?";
					List<BaseBean> lis2=baseBeanService.getListBeanByHqlAndParams(hlq, new String[]{order.getOrderID()});
					
					for (Object  obj:lis2) {
						OrderProduct  op=(OrderProduct) obj;
						//temp.accumulate("orderProductID",op.getOrderProductID()==null?"":op.getOrderProductID());	
						if(op.getProductID()!=null)
						{				
							ProductPackaging pk=(ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql1,new Object[]{op.getProductID()});
							JSONObject	temp1=new JSONObject();
							temp1.accumulate("ppID",pk.getPpID()==null?"":pk.getPpID());
							temp1.accumulate("image",pk.getImage()==null?"":pk.getImage());
							temp1.accumulate("productMoney",op.getProductMoney()==null?"":op.getProductMoney());
							temp1.accumulate("buyNum",op.getBuyNum()==null?"":op.getBuyNum());
							temp1.accumulate("type",op.getType()==null?"":op.getType());
							temp.accumulate("pklist",temp1);
						}
						else{
							
							}				
					}
					list.add(temp);
					}
	            }
				jo.accumulate("message","");
    			jo.accumulate("status", "SUCCESS");
    			jo.accumulate("data", list);	
			
			}
				
		}
		else
		{
			JSONObject temp = new JSONObject();
			temp.accumulate("status","fail");
			temp.accumulate("message","参数用户为空");
			list.add(temp);
			jo.accumulate("data", list);
			 
			
			
		}
		result = jo;
		return Action.SUCCESS;
		
	}


	/**
	 * 
	 * 获取退货信息 或者换货 wk
	 * http://192.168.0.8/ea/android/shop_ajax_getlisthh.jspa?customer
	 * =申请人type=表示退货还是换货
	 * 
	 * @return
	 */
	public String getlisthh() {

		JSONObject jo = new JSONObject();
		String customer = request.getParameter("customer");
		String type = request.getParameter("type");
		String hql = "from TEshoptuihuan where thsqID=? and  thstatus=?";
		List<BaseBean> proList = null;
		List<Object> list = new ArrayList<Object>();
		if (yanzheng(new String[] { customer, type })) {
			proList = baseBeanService.getListBeanByHqlAndParams(hql,
					new Object[] { customer, type });

			if (proList != null && proList.size() > 0) {
				JSONObject temp = null;
				for (Object bb : proList) {
					Object[] c = (Object[]) bb;
					temp = new JSONObject();
					temp.accumulate("thID", c[0] == null ? "" : c[0]);
					temp.accumulate("thddID", c[1] == null ? "" : c[1]);
					temp.accumulate("odID", c[2] == null ? "" : c[2]);
					temp.accumulate("ppjg", c[3] == null ? "" : c[3]);
					temp.accumulate("ppsl", c[4] == null ? "" : c[4]);
					temp.accumulate("thtqian", c[5] == null ? "" : c[5]);
					temp.accumulate("thdate", c[6] == null ? "" : c[6]);
					temp.accumulate("thtype", c[7] == null ? "" : c[7]);
					temp.accumulate("thsm", c[8] == null ? "" : c[8]);
					temp.accumulate("thuserID", c[9] == null ? "" : c[9]);
					temp.accumulate("thsqID", c[10] == null ? "" : c[10]);
					temp.accumulate("thwlID", c[11] == null ? "" : c[11]);
					temp.accumulate("thwldate", c[12] == null ? "" : c[12]);
					temp.accumulate("thcldate", c[13] == null ? "" : c[13]);
					temp.accumulate("thstatus", c[14] == null ? "" : c[14]);
					list.add(temp);
				}
				jo.accumulate("data", list);
				jo.accumulate("message", "");
				jo.accumulate("status", "success");
			} else {
				JSONObject temp = new JSONObject();

				temp.accumulate("thID", "");
				temp.accumulate("thddID", "");
				temp.accumulate("odID", "");
				temp.accumulate("ppjg", "");
				temp.accumulate("ppsl", "");
				temp.accumulate("thtqian", "");
				temp.accumulate("thdate", "");
				temp.accumulate("thtype", "");
				temp.accumulate("thsm", "");
				temp.accumulate("thuserID", "");
				temp.accumulate("thsqID", "");
				temp.accumulate("thwlID", "");
				temp.accumulate("thwldate", "");
				temp.accumulate("thcldate", "");
				temp.accumulate("thstatus", "");
				list.add(temp);
				jo.accumulate("data", list);
				jo.accumulate("status", "fail");
				jo.accumulate("message", "没有数据 ");
			}

		} else {
			jo.accumulate("status", "fail");
			jo.accumulate("message", "有值为空");
			jo.accumulate("data", "");

		}
		result = jo;
		return Action.SUCCESS;

	}

	/**
	 * 修改退货状态或者换货 wk
	 * http://192.168.0.8/ea/android/shop_ajax_uateTh.jspa?customer
	 * =申请人&id="退货记录ID" status要修改的状态 type=表示退货还是换货
	 * 
	 * @return
	 */
	@SuppressWarnings("null")
	public String uateTh() {
		JSONObject jo = new JSONObject();
		String id = request.getParameter("id");
		String customer = request.getParameter("customer");
		String status = request.getParameter("status");
		String type = request.getParameter("type");
		TEshoptuihuan th = new TEshoptuihuan();
		String hql = "from TEshoptuihuan where thID=? and thstatus=? ";
		JSONObject temp = null;
		List<Object> list = new ArrayList<Object>();
		if (yanzheng(new String[] { id, customer, status, type })) {
			th = (TEshoptuihuan) baseBeanService.getBeanByHqlAndParams(hql,
					new String[] { id, type });

			if (th != null) {
				if (th.getThtype() == "0" && "0".equals(th.getThtype())) {
					th.setThtype(status);
					th.setThcldate(new Date());
					baseBeanService.save(th);
					jo.accumulate("status", "success");
					temp = new JSONObject();
					temp.accumulate("odid",
							th.getOdID() == null ? "" : th.getOdID());
					temp.accumulate("thID",
							th.getThID() == null ? "" : th.getThID());
					temp.accumulate("thddID",
							th.getThddID() == null ? "" : th.getThddID());
					temp.accumulate("ppjg",
							th.getPpjg() == null ? "" : th.getPpjg());
					temp.accumulate("ppsl",
							th.getPpsl() == null ? "" : th.getPpsl());
					temp.accumulate("thtqian", th.getThtqian() == null ? ""
							: th.getThtqian());
					temp.accumulate("thdate",
							th.getThdate() == null ? "" : th.getThdate());
					temp.accumulate("thtype",
							th.getThtype() == null ? "" : th.getThtype());
					temp.accumulate("thsm",
							th.getThsm() == null ? "" : th.getThsm());
					temp.accumulate("thuserID", th.getThuserID() == null ? ""
							: th.getThuserID());
					temp.accumulate("thsqID",
							th.getThsqID() == null ? "" : th.getThsqID());
					temp.accumulate("thwlID",
							th.getThwlID() == null ? "" : th.getThwlID());
					temp.accumulate("thwldate", th.getThwldate() == null ? ""
							: th.getThwldate());
					temp.accumulate("thcldate", th.getThcldate() == null ? ""
							: th.getThcldate());
					temp.accumulate("thstatus", th.getThstatus() == null ? ""
							: th.getThstatus());
					list.add(temp);
					jo.accumulate("Data", list);
					jo.accumulate("message", "");
					jo.accumulate("status", "success");
				} else {

					jo.accumulate("status", "fail");
					jo.accumulate("message", "该信息状态为0");
					jo.accumulate("Data", "");
				}
			} else {
				jo.accumulate("status", "fail");
				jo.accumulate("data", "没有记录");
				jo.accumulate("Data", "");
			}
		}
		else {
			temp.accumulate("odid", "");
			temp.accumulate("thID", "");
			temp.accumulate("thddID", "");
			temp.accumulate("ppjg", "");
			temp.accumulate("ppsl", "");
			temp.accumulate("thtqian", "");
			temp.accumulate("thdate", "");
			temp.accumulate("thtype", "");
			temp.accumulate("thsm", "");
			temp.accumulate("thuserID", "");
			temp.accumulate("thsqID", "");
			temp.accumulate("thwlID", "");
			temp.accumulate("thwldate", "");
			temp.accumulate("thcldate", "");
			temp.accumulate("thstatus", "");
			list.add(temp);
			jo.accumulate("data", list);
			jo.accumulate("status", "fail");
			jo.accumulate("message", "有值为空");
		}
		result = jo;
		return Action.SUCCESS;
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
	/**
	 * 
	 * 根据产品ID 查询 产品
	 * http://192.168.0.8/ea/android/shop_ajax_getPk.jspa?ppID="产品ID"
	 
	 * @return
	 */
	
	public String getPk()
	{
		
		JSONObject jo = new JSONObject();
		String ppID = request.getParameter("ppID");
		String HQL="from ProductPackaging where ppID=?";
		List<Object> list=new ArrayList<Object>();
		if(yanzheng(new String[]{ppID}))
		{
		
			String[] pp=ppID.split("-");
			for (String params:pp) {
				JSONObject temp = new JSONObject();
				ProductPackaging  c=(ProductPackaging) baseBeanService.getBeanByHqlAndParams(HQL, new String[]{params});
				temp.accumulate("ppID",c.getPpID()  == null ? "" : c.getPpID());
				 temp.accumulate("goodsName",c.getGoodsName()  == null ? "" :c.getGoodsName() );					
				 temp.accumulate("money",c.getMoney() == null ? "" : c.getMoney());				
				 temp.accumulate("manualUrl",c.getManualUrl()  == null ? "" : c.getManualUrl());				
				 temp.accumulate("photo",c.getPhoto()  == null ? "" : c.getPhoto() );					
				 temp.accumulate("ppstatus",c.getPpstatus()  == null ? "" :c.getPpstatus());					
				 temp.accumulate("ppwhether",c.getPpwhether()  == null ? "" : c.getPpwhether());
				 temp.accumulate("giveNumber",c.getGiveNumber()  == null ? "" : c.getGiveNumber());				
				 temp.accumulate("ppwhether",c.getPpwhether()  == null ? "" : c.getPpwhether());		
				 temp.accumulate("stockSize", c.getStockSize());					
				 temp.accumulate("organizationID",c.getOrganizationID()  == null ? "" : c.getOrganizationID());
				list.add(temp);
			}
			
			jo.accumulate("status", "SUCCESS");
			jo.accumulate("message", "");
			jo.accumulate("data", list);
			
		}
		else
		{
			jo.accumulate("status", "fail");
			jo.accumulate("message", "有值为空");
			jo.accumulate("data", "");

			
		}
	
		result = jo;
		return Action.SUCCESS;
		
		
	}

	/**
	 * 录入退货信息或换货 wk
	 * http://192.168.0.8/ea/android/shop_ajax_addTEshoptuihuan.jspa
	 * ?type=类型1为退货2为换货
	 * customer=申请人&order=订单号prodID=产品ID-price=产品单价number=数量money
	 * =退款金
	 * 
	 * 
	 * 	
	 */
	public String addTEshoptuihuan() {
		JSONObject jo = new JSONObject();
		String type = request.getParameter("type");
		String customer = request.getParameter("customer");
		String order = request.getParameter("order");
		String prodID = request.getParameter("prodID");
		String price = request.getParameter("price");
		String number = request.getParameter("number");
		String money = request.getParameter("money");
		List<Object> list = new ArrayList<Object>();
		if (yanzheng(new String[] { type, customer, order, prodID, price,
				number, money })) {
			JSONObject temp = null;
			TEshoptuihuan th = new TEshoptuihuan();
			th.setThID(serverService.getServerID("TEshoptuihuan"));
			th.setThsqID(customer);
			th.setOdID(order);
			th.setThddID(prodID);
			th.setPpjg(price);
			th.setPpsl(number);
			th.setThtqian(money);
			th.setThdate(new Date());
			th.setThtype("0");
			th.setThstatus(type);
			baseBeanService.save(th);
			temp = new JSONObject();
			temp.accumulate("odid", th.getOdID() == null ? "" : th.getOdID());
			temp.accumulate("thID", th.getThID() == null ? "" : th.getThID());
			temp.accumulate("thddID",
					th.getThddID() == null ? "" : th.getThddID());
			temp.accumulate("ppjg", th.getPpjg() == null ? "" : th.getPpjg());
			temp.accumulate("ppsl", th.getPpsl() == null ? "" : th.getPpsl());
			temp.accumulate("thtqian",
					th.getThtqian() == null ? "" : th.getThtqian());
			temp.accumulate("thdate",
					th.getThdate() == null ? "" : th.getThdate());
			temp.accumulate("thtype",
					th.getThtype() == null ? "" : th.getThtype());
			temp.accumulate("thsm", th.getThsm() == null ? "" : th.getThsm());
			temp.accumulate("thuserID",
					th.getThuserID() == null ? "" : th.getThuserID());
			temp.accumulate("thsqID",
					th.getThsqID() == null ? "" : th.getThsqID());
			temp.accumulate("thwlID",
					th.getThwlID() == null ? "" : th.getThwlID());
			temp.accumulate("thwldate",
					th.getThwldate() == null ? "" : th.getThwldate());
			temp.accumulate("thcldate",
					th.getThcldate() == null ? "" : th.getThcldate());
			list.add(temp);
			jo.accumulate("data", list);
			jo.accumulate("status", "success");
		} else {
			jo.accumulate("status", "fail");
			jo.accumulate("data", "");
			jo.accumulate("message", "有空值");

		}

		result = jo;
		return Action.SUCCESS;
	}

	/**
	 * 
	 * 录入物流订单号
	 * http://192.168.0.8/ea/android/shop_ajax_getlistCartProduct.jspa?order=订单号
	 * id&customer=操作账号ID&company=物流公司&number=物流单号
	 * 
	 * 
	 * @return
	 */
	public String addOrder() {
		JSONObject jo = new JSONObject();
		String order = request.getParameter("order");
		String customer = request.getParameter("customer");
		String company = request.getParameter("company");
		String number = request.getParameter("number");
		String hql = "from  Order where  orderID=?";
		List<Object> list = new ArrayList<Object>();
		Order order1 = (Order) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { order });
		if (yanzheng(new String[] { order, customer, company, number })) {
			if (order1 != null) {
				JSONObject temp = null;
				
				order1.setOrderType("3");
				temp = new JSONObject();
				temp.accumulate("orderID", order1.getOrderID() == null ? ""
						: order1.getOrderID());
				temp.accumulate("userID", order1.getUserID() == null ? ""
						: order1.getUserID());
				temp.accumulate(
						"supplierID",
						order1.getSupplierID() == null ? "" : order1
								.getSupplierID());
				temp.accumulate("orderType", order1.getOrderType() == null ? ""
						: order1.getOrderType());
				temp.accumulate(
						"orderMoney",
						order1.getOrderMoney() == null ? "" : order1
								.getOrderMoney());
				temp.accumulate(
						"paymentType",
						order1.getPaymentType() == null ? "" : order1
								.getPaymentType());
				temp.accumulate(
						"paymentNumber",
						order1.getPaymentNumber() == null ? "" : order1
								.getPaymentNumber());
				temp.accumulate(
						"paymentTime",
						order1.getPaymentTime() == null ? "" : order1
								.getPaymentTime());
				temp.accumulate(
						"receiveAddress",
						order1.getReceiveAddress() == null ? "" : order1
								.getReceiveAddress());
				temp.accumulate(
						"createTime",
						order1.getCreateTime() == null ? "" : order1
								.getCreateTime());
				temp.accumulate(
						"updateTime",
						order1.getUpdateTime() == null ? "" : order1
								.getUpdateTime());
				temp.accumulate(
						"updateUser",
						order1.getUpdateUser() == null ? "" : order1
								.getUpdateUser());
				
				temp.accumulate(
						"sendProductID",
						order1.getSendProductID() == null ? "" : order1
								.getSendProductID());
				temp.accumulate("freight", order1.getFreight() == null ? ""
						: order1.getFreight());
				list.add(temp);
				jo.accumulate("status", "success");
				
				jo.accumulate("message", "");
				jo.accumulate("Data", list);

				baseBeanService.save(order1);
			} else {
				JSONObject temp = new JSONObject();
				;
				list.add(temp);
				jo.accumulate("message", "没有该订单");
				jo.accumulate("status", "fail");
				jo.accumulate("data", "");
			}
		} else {
			jo.accumulate("message", "有值是空的");
			jo.accumulate("status", "fail");
			jo.accumulate("data", "");
		}
		result = jo;
		return Action.SUCCESS;
	}

	// /**
	// * 根据人查购物车产品zll
	// * @return
	// * http://192.168.0.8/ea/android/shop_ajax_getCartProduct.jspa?customer=
	// */
	// @SuppressWarnings("unchecked")
	// public String getCartProduct() {
	// JSONObject jo = new JSONObject();
	// String customer=request.getParameter("customer");
	// String
	// sql="select c.goodsname,c.ppid,c.price,c.pic,c.shopid from 	 c where c.customer=?";
	// List <BaseBean> proList=baseBeanService.getListBeanBySqlAndParams(sql,
	// new Object[]{customer});
	// List<Object> list=new ArrayList<Object>();
	//
	// if(proList!=null&&proList.size()>0){
	// JSONObject temp=null;
	// for (Object bb : proList) {
	// Object[] c = (Object[]) bb;
	// temp=new JSONObject();
	// temp.accumulate("goodsname", c[0]==null?"":c[0]);
	// temp.accumulate("ppid", c[1]==null?"":c[1]);
	// temp.accumulate("price", c[2]==null?"":c[2]);
	// temp.accumulate("pic", c[3]==null?"":c[3]);
	// temp.accumulate("shopid", c[4]==null?"":c[4]);
	// list.add(temp);
	// }
	// jo.accumulate("proList", list);
	// jo.accumulate("resultValue", "1");
	// }else{
	// JSONObject temp=new JSONObject();
	// temp.accumulate("goodsname","");
	// temp.accumulate("ppid","");
	// temp.accumulate("price","");
	// temp.accumulate("pic", "");
	// temp.accumulate("shopid", "");
	// list.add(temp);
	// jo.accumulate("proList", list);
	// jo.accumulate("resultValue", "0");
	// }
	// result = jo;
	// return Action.SUCCESS;
	// }

	/**
	 * 根据店铺ID查询出 所在公司
	 * 
	 */
	public String getcomId(String org) {
		String hql = "from COrganization where organizationID=?";
		COrganization organization = (COrganization) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { org });
		if (organization.getOrganizationPID().equals(
				organization.getCompanyID())) {
			return organization.getCompanyID();
		} else {
			return getcomId(organization.getOrganizationPID());
		}
	}
	/**
	 * ty<br>
	 * 生成订单<br>
	 * 参数<br>
	 * customer={用户ID}&address={收货地址}&shopID={售货方ID}&prodList=产品ID-数量^产品ID-数量^··· ···<br>
	 * 地址<br>
	 * http://192.168.0.8/ea/android/shop_ajax_saveOrder.jspa?<br>
	 * **/
	public String saveOrder(){
		JSONObject jo = new JSONObject();
		//用户ID
		String customer=request.getParameter("customer");
		//售货方ID
		String shopID=request.getParameter("shopID");
		//产品ID+数量
		String prodList=request.getParameter("prodList");
		//收货地址
		String address=request.getParameter("address");
		if(yanzheng(new String[]{customer,shopID,prodList,address})){
			//用户是否存在
			String hql1="from TEshopCustomer where  staffid = ? ";
			String staff="from COrganization where organizationid=?";
		
			TEshopCustomer customer1=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams(hql1,new Object[]{customer});
			COrganization staff1=(COrganization) baseBeanService.getBeanByHqlAndParams(staff,new Object[]{shopID});
				
			if( customer1!=null){
				String hql="from ProductPackaging   where ppID=?";
				String[] proAndNum = prodList.split("_");
				//生成ID
				//会生成一条订单order数据，n条订单产品orderProduct数据
				Order order = new Order();
				//系统订单
				CashierBills cb=new CashierBills();
				order.setOrderNumber(serverService.getServerID("order"));
				//cb.setCashierBillsKey(order.getOrderNumber());
				order.setOrderID(serverService.getServerID("0"));
				
				//文汪订单跟   系统订单管理唯一标示
				cb.setCashierBillsID(serverService.getServerID("cashierTally"));
				
				order.setUserID(customer);
				cb.setInputName(customer1.getAccount());
				cb.setInputid(customer);
			    order.setSupplierID(shopID);
			    cb.setStaffID(shopID);
			    cb.setStaffName(staff1.getOrganizationManager());
			  cb.setOrganizationID(getPorg(shopID));
			    //以下为默认数据c
			    cb.setStartTime(new Date());
			    cb.setBillsType("项目收入预算单");
			    cb.setStatusbill("01");
			    COrganization cOrganization = (COrganization) baseBeanService
						.getBeanByHqlAndParams(
								"from COrganization where organizationID=?",
								new Object[] { shopID });
			    cb.setDepartmentID(shopID);
			     cb.setDepartmentName(cOrganization
						.getOrganizationName());
			     cb.setFkStatus("01");
			     cb.setWfStatus1("00");
			    String comhql = "from Company where companyID=?";
			    String comid=  getcomId(shopID);
			    Company co = (Company) baseBeanService.getBeanByHqlAndParams(
						comhql, new Object[] { comid });
			    cb.setCompanyID(comid); 
			    cb.setCompanyName(co.getCompanyName());
			    cb.setGroupCompanySn(co.getGroupCompanySn()) ;
			    
			    order.setOrderType("0");
			    cb.setStatus("40");
			    order.setReceiveAddress(address);
			    cb.setStaffAddress(address);
			    order.setCreateTime(new Date());
				for (int i=0;i<proAndNum.length;i++){
				    
				    String[] proNum = proAndNum[i].split("-");
				    OrderProduct orPro= new OrderProduct();
				    GoodsBills gb = new GoodsBills();
				    //查询产品
				    ProductPackaging  pk=new ProductPackaging();
					pk=(ProductPackaging)baseBeanService.getBeanByHqlAndParams(hql, new String[]{proNum[0]});	
					//String转float
					float prop = Float.parseFloat(pk.getMoney());
					float pron = Float.parseFloat(proNum[1]);
				    //订单金额
					gb.setGoodsBillsID(pk.getPpID());
					if(order.getOrderMoney()!=null&&!"".equals(order.getOrderMoney())){
						order.setOrderMoney(((prop*pron)+Float.parseFloat(order.getOrderMoney()))+"");//String.valueOf(prop*pron)	
					     cb.setPriceSub(((prop*pron)+Float.parseFloat(order.getOrderMoney()))+"");
					}else{
						order.setOrderMoney((prop*pron)+"");
						cb.setPriceSub((prop*pron)+"");
					}
					gb.setCashierBillsID(cb.getCashierBillsID());
					gb.setGoodsName(pk.getGoodsName());
					gb.setGoodsName(pk.getGoodsNum());
					gb.setGoodsBillsID(pk.getPpID());
					gb.setPpID(pk.getPpID());
					Calendar ca = Calendar.getInstance();
					ca.setTimeInMillis(new Date().getTime());
					ca.add(Calendar.DATE, 15);// 天后的日期
					Date date = new Date(ca.getTimeInMillis()); // 将c转换成Date
					gb.setTargetEnd(date);
					gb.setQuantity(pk.getQuantity());
					gb.setPriceManage("零售价");
					gb.setPrice(pk.getPrice());
					gb.setMoney(cb.getPriceSub());
					gb.setMoneySpent(pk.getImage());
					gb.setTargetDeptID(cb.getInputCompanyid());
					gb.setTargetDeptName(cb.getInputCompanyName());
					gb.setCompanyID(pk.getCompanyID());
					gb.setConnectName(pk.getStaffID());
					
				
					
					//订单产品表
				    orPro.setOrderProductID(serverService.getServerID("orderProduct"));
				    orPro.setOrderID(order.getOrderID());
				    orPro.setProductID(proNum[0]);
				    orPro.setBuyNum(proNum[1]);
				    baseBeanService.save(orPro);
				    baseBeanService.save(gb);
				}
			    cb.setStatus("40");// 确定预算
				cb.setZctype("y");
				cb.setJournalNum(serverService.getBillID(comid));
				cb.setCashierDate(new Date());
				RelatedBill relatedBill = new RelatedBill();
				relatedBill.setRbID(serverService.getServerID("relatedbill"));
				relatedBill.setCashid(order.getOrderID());
				relatedBill.setCashfid(cb.getCashierBillsID());
				relatedBill.setBilltype("文汪订单跟系统订单");
				baseBeanService.save(order);
				baseBeanService.save(cb);
				baseBeanService.save(relatedBill);
				jo.accumulate("message","订单生成成功");
				jo.accumulate("status", "success");
				JSONObject temp=null;
				List<Object> obj=new ArrayList<Object>();					
				temp=new JSONObject();	
				temp.accumulate("userID",order.getUserID()==null?"":order.getUserID());//用户ID
				temp.accumulate("supplierID",order.getSupplierID()==null?"":order.getSupplierID());//售货方ID
				temp.accumulate("receiveAddress",order.getReceiveAddress()==null?"":order.getReceiveAddress());//收货地址
				temp.accumulate("orderID", order.getOrderID()==null?"":order.getOrderID());
				temp.accumulate("createTime", sf.format(order.getCreateTime())==null?"":sf.format(order.getCreateTime()));//订单生成时间	
				temp.accumulate("orderType", order.getOrderType()==null?"":order.getOrderType());
				temp.accumulate("orderMoney", order.getOrderMoney()==null?"":order.getOrderMoney());
				temp.accumulate("paymentType", order.getPaymentType()==null?"":order.getPaymentType());
				temp.accumulate("paymentNumber", order.getPaymentNumber()==null?"":order.getPaymentNumber());
				temp.accumulate("paymentTime", order.getPaymentTime()==null?"":order.getPaymentTime());		
				temp.accumulate("updateTime", order.getUpdateTime()==null?"":order.getUpdateTime());
				temp.accumulate("updateUser", order.getUpdateUser()==null?"":order.getUpdateUser());
				temp.accumulate("sendProductID", order.getSendProductID()==null?"":order.getSendProductID());
				temp.accumulate("freight", order.getFreight()==null?"":order.getFreight());
				obj.add(temp);
				jo.accumulate("data", obj);
			}else{
				if( customer==null){
					jo.accumulate("message","用户不存在");
				}
				jo.accumulate("status", "fail");
			}
		}else{
			jo.accumulate("message","参数为空");
			jo.accumulate("status", "fail");
		}
		result = jo;
		return Action.SUCCESS;
	}
	/**
	 * 查询微分金店顶级部门
	 * 
	 */
	// 查询微店所属顶级部门
	public String getPorg(String org) {
		String hql = "from COrganization where organizationID=?";
		COrganization organization = (COrganization) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { org });
		if (organization.getOrganizationPID().equals(
				organization.getCompanyID())) {
			return org;
		} else {
			return getPorg(organization.getOrganizationPID());
		}
	}
	
	/**
	 * 根据订单ID 查询 订单下面的产品信息 跟订单信息
	 * 
	 * http://192.168.0.8/ea/android/shop_ajax_getORder.jspa?orderID=订单ID
	 * @return
	 */
	public String getORder(){
	
		JSONObject jo = new JSONObject();
		//订单ID
		String orderID=request.getParameter("orderID");
		if(yanzheng(new String[]{orderID})){
			//
			String hql1="from 	Order where  orderID = ? ";
			String hql2="from 	OrderProduct where  orderID = ? ";
			Order order=(Order) baseBeanService.getBeanByHqlAndParams(hql1,new Object[]{orderID});
            List<BaseBean>  list=baseBeanService.getListBeanByHqlAndParams(hql2, new String[]{orderID});
			List<Object> list1=new ArrayList<Object>();
            if( order!=null){	
            	JSONObject	temp=new JSONObject();
				String hql="from ProductPackaging   where ppID=?";
				temp.accumulate("userID",order.getUserID()==null?"":order.getUserID());//用户ID
				temp.accumulate("supplierID",order.getSupplierID()==null?"":order.getSupplierID());//售货方ID
				temp.accumulate("receiveAddress",order.getReceiveAddress()==null?"":order.getReceiveAddress());//收货地址	
				temp.accumulate("createTime", sf.format(order.getCreateTime())==null?"":sf.format(order.getCreateTime()));//订单生成时间	
				temp.accumulate("orderType", order.getOrderType()==null?"":order.getOrderType());
				temp.accumulate("orderMoney", order.getOrderMoney()==null?"":order.getOrderMoney());
				temp.accumulate("paymentType", order.getPaymentType()==null?"":order.getPaymentType());
				temp.accumulate("paymentNumber", order.getPaymentNumber()==null?"":order.getPaymentNumber());
				temp.accumulate("paymentTime", order.getPaymentTime()==null?"":order.getPaymentTime());		
				temp.accumulate("updateTime", order.getUpdateTime()==null?"":order.getUpdateTime());
				temp.accumulate("updateUser", order.getUpdateUser()==null?"":order.getUpdateUser());
				temp.accumulate("sendProductID", order.getSendProductID()==null?"":order.getSendProductID());
				temp.accumulate("freight", order.getFreight()==null?"":order.getFreight());
				temp.accumulate("orderID",order.getOrderID()==null?"":order.getOrderID());
				for (Object  obj:list) {
					OrderProduct  op=(OrderProduct) obj;
					//temp.accumulate("orderProductID",op.getOrderProductID()==null?"":op.getOrderProductID());	
					if(op.getProductID()!=null)
					{				
						ProductPackaging pk=(ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{op.getProductID()});
						JSONObject	temp1=new JSONObject();
						temp1.accumulate("ppID",pk.getPpID()==null?"":pk.getPpID());
						temp1.accumulate("image",pk.getImage()==null?"":pk.getImage());
						temp1.accumulate("productMoney",op.getProductMoney()==null?"":op.getProductMoney());
						temp1.accumulate("buyNum",op.getBuyNum()==null?"":op.getBuyNum());
						temp1.accumulate("type",op.getType()==null?"":op.getType());
						temp.accumulate("pklist",temp1);
					}
					else{
						
						}				
				}
				list1.add(temp);
				jo.accumulate("message","");
    			jo.accumulate("status", "SUCCESS");
    			jo.accumulate("data", list1);	
            }
            else{         	
            	jo.accumulate("message","数据为空");
    			jo.accumulate("status", "fail");	
    			jo.accumulate("data", "");          	
            }
		}
		else{
			jo.accumulate("message","参数为空");
			jo.accumulate("status", "fail");
			jo.accumulate("data", "");
		}
		result = jo;
		return Action.SUCCESS;
	}
	/**
	 * ty<br>
	 * 修改订单状态<br>
	 * 参数<br>
	 * order={订单号}&status={修改后的状态}<br>
	 * 订单状态： -1：无效订单  1：未付款  2：已付款，未发货<br>
	 * 3：已发货，未收货  4：已确认收货  5：申请退货中<br>
	 * 6：已退货<br>
	 **地址<br>
	 * http://192.168.0.8/ea/android/shop_ajax_updateOrderType.jspa?<br>
	 * @throws CloneNotSupportedException 
	 * 
	 */
	public String updateOrderType() throws CloneNotSupportedException{
		JSONObject jo = new JSONObject();
		//订单号
		String orderID=request.getParameter("order");
		//状态
		String orderType=request.getParameter("status");
		if(yanzheng(new String[]{orderID,orderType})){
		//订单是否有效	
			if("-1".equals(orderType) || "1".equals(orderType) || "2".equals(orderType) || "3".equals(orderType) || "4".equals(orderType) || "5".equals(orderType) || "6".equals(orderType)){
				String sql ="update dtOrder set orderType=? where orderID =? ";
				baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{orderType,orderID});
				String hql1="from Order where  orderID = ? ";
				Order order=(Order) baseBeanService.getBeanByHqlAndParams(hql1,new Object[]{orderID});
				String hql4="from RelatedBill where cashid=?";
				RelatedBill rb=(RelatedBill) baseBeanService.getBeanByHqlAndParams(hql4,new Object[]{order.getOrderID()});
				String  cashql="from  CashierBills where cashierBillsID=?";
				CashierBills  cb=(CashierBills)baseBeanService.getBeanByHqlAndParams(cashql,new Object[]{rb.getCashfid()});
				List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
				
				if("1".equals(orderType))
				{
					//未付款的訂單處理方式
					//暂未处理方式
					
				}else if("2".equals(orderType))
				{
					//已付款，未發貨的訂單處理方式
					//收货单。生成收款单  
					CashierBills  cbi = null;
					try {
							cbi=(CashierBills)cb.cloneCashierBills();
						cbi.setCashierBillsID(serverService.getServerID("cashierTally"));
						cbi.setJournalNum(serverService.getBillID(cbi
								.getCompanyID()));
						cbi.setCashierBillsKey(null);
						cbi.setBillsType("收款单");
						System.out.println("生成的收款单："+cbi.getJournalNum());
						cbi.setStatus("40");
						cbi.setCashierDate(new Date());
						cbi.setStatusbill("01");
						cbi.setWfStatus1(cb.getWfStatus1());
						List<BaseBean> gBillsList = baseBeanService
								.getListBeanByHqlAndParams(
										"from GoodsBills where cashierBillsID=?",
										new Object[] { cb.getCashierBillsID() });
						Float qian=0f;
					
						for (int i = 0; i < gBillsList.size(); i++) {
							GoodsBills gBills1 = (GoodsBills)((GoodsBills) gBillsList.get(i)).cloneGoodsBills();
							String goodsbills = serverService.getServerID("goodsbills");
							gBills1.setGoodsBillsID(goodsbills);
							gBills1.setGoodsBillsKey(null);
							gBills1.setCashierBillsID(cbi.getCashierBillsID());
							gBills1.setPlanGoodsBillsID(((GoodsBills) gBillsList.get(i)).getGoodsBillsID());
							gBills1.setStartDate(new Date());
							qian=qian+Float.parseFloat(gBills1.getMoney());
							if (gBills1.getTargetStart() != null) {
								cbi.setTargetStart(gBills1.getTargetStart());
								cbi.setTargetEnd(gBills1.getTargetEnd());
								cbi.setTargetDeptID(gBills1.getTargetDeptID());// 目标部门ID
								cbi.setTargetDeptName(gBills1.getTargetDeptName());// 目标部门name
								cbi.setTargetSalerID(gBills1.getTargetSalerID());// 目标业务员ID
								cbi.setTargetSalerName(gBills1.getTargetSalerName());// 目标业务员Name
							}
							baseBeanList.add(gBills1);
							
						}
						
						cbi.setPriceSub((String) (cbi.getPriceSub()!=null?cbi.getPriceSub():qian));
						
						cb.setFkStatus("00");
						baseBeanList.add(cb);
						baseBeanList.add(cbi);
						RelatedBill relatedBill = new RelatedBill();
						relatedBill.setRbID(serverService.getServerID("relatedbill"));
						relatedBill.setCashid(cb.getCashierBillsID());
						relatedBill.setCashfid(cbi.getCashierBillsID());
						relatedBill.setBilltype("收款单");
						baseBeanList.add(relatedBill);
						

					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}

				}else if("3".equals(orderType))
				{
					//已經发货未收货的订单处理方式
					String hql="from CashierBills where cashierBillsID = ?";
				
					List<BaseBean> list=new ArrayList<BaseBean>();
					CashierBills cba = (CashierBills) cb.cloneCashierBills();
					cba.setCashierBillsID(serverService.getServerID("cashierTally"));
					cba.setCashierBillsKey(null);
					cba.setBillsType("销售出库单");
					cba.setJournalNum(serverService.getBillID(cb.getCompanyID()));
					cba.setStatus("22");
					cba.setCashierDate(new Date());
					baseBeanList.add(cba);
					List<BaseBean> goodlist=baseBeanService
							.getListBeanByHqlAndParams(
									"from GoodsBills where cashierBillsID=?",
									new Object[] { cb.getCashierBillsID()});
					for (int i = 0; i < goodlist.size(); i++) {
						GoodsBills goodsBills = (GoodsBills) goodlist.get(i);
						GoodsBills goodsBills2 = null;
						goodsBills2 = (GoodsBills) goodsBills.cloneGoodsBills();
						goodsBills2.setCashierBillsID(cba.getCashierBillsID());
						goodsBills2.setGoodsBillsID(serverService
								.getServerID("goodsbills"));
						goodsBills2.setGoodsBillsKey(null);
						goodsBills2.setIdentifyingCode("00");// 00为单据原始物品
						baseBeanList.add(goodsBills2);
					}
					//保存单据子表 
					FinancialBill financialBill = new FinancialBill();
					financialBill.setFinancialbillID(serverService
							.getServerID("financial"));
					financialBill.setCashierBillsID(cba.getCashierBillsID()); // 出纳单单据外键
					financialBill.setGroupCompanySn(cb.getGroupCompanySn()); // 集团公司的标识
																				// 外键
					financialBill.setCompanyID(cb.getCompanyID()); // 公司 外键
					financialBill.setOrganizationID(cb.getOrganizationID()); // 单据所在部门
																				// 外键
					financialBill.setDepartmentID(cb.getDepartmentID()); // 子部门ID(限定部门单据)
																			// 外键
					financialBill.setBillsType("销售出库单"); // 单据类型
					financialBill.setJournalNum(cb.getJournalNum()); // 凭证号（单据编号）
					financialBill.setBillsDate(new Date()); // 制单日期
					financialBill.setBillStaffID(cb.getInputid()); // 制单人ID 外键
					financialBill.setBillStaffName(cb.getInputName()); // 制单人名称
					financialBill.setBillStatus("22");
					baseBeanList.add(financialBill);
					// 保存与预算单据关联单据单据表信息
					RelatedBill relatedBill2 = new RelatedBill();
					relatedBill2.setRbID(serverService.getServerID("relatedbill"));
					relatedBill2.setCashid(cb.getCashierBillsID());
					relatedBill2.setCashfid(cba.getCashierBillsID());
					relatedBill2.setCashfid("出库单跟与预算单");
					baseBeanList.add(relatedBill2);
					
				}else if("4".equals(orderType))
				{
					//已经确认收货
				}else if("5".equals(orderType))
				{
					//申请退货中
				}else if("6".equals(orderType))
				{
					//已经退货订单处理方式
				}else if("-1".equals(orderType))
				{
					  //无效订单处理方式
					
				}
				baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,
						null, null);
				jo.accumulate("message","成功修改订单状态");
				jo.accumulate("status", "success");
				JSONObject temp=null;
				List<Object> obj=new ArrayList<Object>();					
				temp=new JSONObject();
				temp.accumulate("userID",order.getUserID()==null?"":order.getUserID());//用户ID
				temp.accumulate("supplierID",order.getSupplierID()==null?"":order.getSupplierID());//售货方ID
				temp.accumulate("receiveAddress", order.getReceiveAddress()==null?"":order.getReceiveAddress());//收货地址
				temp.accumulate("orderID", order.getOrderID()==null?"":order.getOrderID());
				temp.accumulate("createTime", sf.format(order.getCreateTime())==null?"":sf.format(order.getCreateTime()));//订单生成时间	
				temp.accumulate("orderType", order.getOrderType()==null?"":order.getOrderType());
				temp.accumulate("orderMoney", order.getOrderMoney()==null?"":order.getOrderMoney());
				temp.accumulate("paymentType", order.getPaymentType()==null?"":order.getPaymentType());
				temp.accumulate("paymentNumber", order.getPaymentNumber()==null?"":order.getPaymentNumber());
				temp.accumulate("paymentTime", order.getPaymentTime()==null?"":order.getPaymentTime());
				temp.accumulate("updateTime", order.getUpdateTime()==null?"":order.getUpdateTime());
				temp.accumulate("updateUser", order.getUpdateUser()==null?"":order.getUpdateUser());
				temp.accumulate("sendProductID", order.getSendProductID()==null?"":order.getSendProductID());
				temp.accumulate("freight", order.getFreight()==null?"":order.getFreight());		
				obj.add(temp);
				jo.accumulate("data", obj);
			}else{
				jo.accumulate("message","订单状态错误");
				jo.accumulate("status", "fail");
			}
		}else{
			jo.accumulate("message","参数为空");
			jo.accumulate("status", "fail");
		}
		result = jo;
		return Action.SUCCESS;
	}
	/***
	 * ty<br>
	 * 修改产品库存<br>
	 * 参数<br>
	 * prodID={产品ID}&type={add/minus}&number={变化数量}<br>
	 * 地址<br>
	 * http://192.168.0.8/ea/android/shop_ajax_updateProductStockSize.jspa?<br>
	 */
	public String updateProductStockSize() {
		JSONObject jo = new JSONObject();
		// 产品ID
		String prodID = request.getParameter("prodID");
		// 状态{add/minus}
		String type = request.getParameter("type");
		// 变化数量
		String number = request.getParameter("number");
		if (yanzheng(new String[] { prodID, type, number })) {
			String hql = "from ProductPackaging   where ppID=?";
			ProductPackaging pk = new ProductPackaging();
			pk = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql,
					new String[] { prodID });
			// 如果是减必须库存>0
			String sql = "update dt_ProductPackaging set stockSize=? where ppID =? ";
			if ("minus".equals(type) || "add".equals(type)) {
				if ("minus".equals(type)) {
					if (pk.getStockSize() - Integer.parseInt(number) >= 0) {
						baseBeanService.saveBeansListAndexecuteSqlsByParams(
								null,
								new String[] { sql },
								new Object[] {
										(pk.getStockSize() - Integer
												.parseInt(number)), prodID });
						jo.accumulate("message", "成功修改库存");
						jo.accumulate("status", "success");
					} else {
						jo.accumulate("message", "库存不足");
						jo.accumulate("status", "fail");
					}
				} else if ("add".equals(type)) {
					baseBeanService.saveBeansListAndexecuteSqlsByParams(
							null,
							new String[] { sql },
							new Object[] {
									(pk.getStockSize() + Integer
											.parseInt(number)), prodID });
					jo.accumulate("message", "成功修改库存");
					jo.accumulate("status", "success");
				}
				JSONObject temp = null;
				List<Object> obj = new ArrayList<Object>();
				temp = new JSONObject();
				temp.accumulate("ppKey",
						pk.getPpKey() == null ? "" : pk.getPpKey());
				temp.accumulate("ppID",
						pk.getPpID() == null ? "" : pk.getPpID());
				temp.accumulate("goodsID",
						pk.getGoodsID() == null ? "" : pk.getGoodsID());
				temp.accumulate("companyID", pk.getCompanyID() == null ? ""
						: pk.getCompanyID());
				temp.accumulate(
						"organizationID",
						pk.getOrganizationID() == null ? "" : pk
								.getOrganizationID());

				temp.accumulate("staffID",
						pk.getStaffID() == null ? "" : pk.getStaffID());
				temp.accumulate(
						"PackagingDate",
						pk.getPackagingDate() == null ? "" : pk
								.getPackagingDate());
				temp.accumulate("goodsName", pk.getGoodsName() == null ? ""
						: pk.getGoodsName());
				temp.accumulate("quantity",
						pk.getQuantity() == null ? "" : pk.getQuantity());
				temp.accumulate("money",
						pk.getMoney() == null ? "" : pk.getMoney());
				temp.accumulate("weight",
						pk.getWeight() == null ? "" : pk.getWeight());
				temp.accumulate("price",
						pk.getPrice() == null ? "" : pk.getPrice());
				temp.accumulate("manualUrl", pk.getManualUrl() == null ? ""
						: pk.getManualUrl());
				temp.accumulate(
						"propagandaUrl",
						pk.getPropagandaUrl() == null ? "" : pk
								.getPropagandaUrl());
				temp.accumulate("fileUrl",
						pk.getFileUrl() == null ? "" : pk.getFileUrl());

				temp.accumulate("parentId",
						pk.getParentId() == null ? "" : pk.getParentId());
				temp.accumulate("image",
						pk.getImage() == null ? "" : pk.getImage());
				temp.accumulate("photo",
						pk.getPhoto() == null ? "" : pk.getPhoto());
				temp.accumulate("showweixin", pk.getShowweixin() == null ? ""
						: pk.getShowweixin());
				temp.accumulate("weiDianType", pk.getWeiDianType() == null ? ""
						: pk.getWeiDianType());
				temp.accumulate(
						"certificateCost",
						pk.getCertificateCost() == null ? "" : pk
								.getCertificateCost());
				temp.accumulate("ppstatus",
						pk.getPpstatus() == null ? "" : pk.getPpstatus());
				temp.accumulate(
						"shangjiastatus",
						pk.getShangjiastatus() == null ? "" : pk
								.getShangjiastatus());
				temp.accumulate("pptype",
						pk.getPptype() == null ? "" : pk.getPptype());
				temp.accumulate("ppcestuer", pk.getPpcestuer() == null ? ""
						: pk.getPpcestuer());
				temp.accumulate("xmtype",
						pk.getXmtype() == null ? "" : pk.getXmtype());
				temp.accumulate("xmtypename", pk.getXmtypename() == null ? ""
						: pk.getXmtypename());
				temp.accumulate("sorting",
						pk.getSorting() == 0 ? 0 : pk.getSorting());
				temp.accumulate("stockSize",
						pk.getStockSize() == 0 ? 0 : pk.getStockSize());
				temp.accumulate("monthSales",
						pk.getMonthSales() == 0 ? 0 : pk.getMonthSales());

				temp.accumulate(
						"productDetail",
						pk.getProductDetail() == null ? "" : pk
								.getProductDetail());
				temp.accumulate("accessories", pk.getAccessories() == null ? ""
						: pk.getAccessories());
				temp.accumulate("goodsNum",
						pk.getGoodsNum() == null ? "" : pk.getGoodsNum());
				temp.accumulate("remark",
						pk.getRemark() == null ? "" : pk.getRemark());
				temp.accumulate("standard",
						pk.getStandard() == null ? "" : pk.getStandard());

				temp.accumulate("type",
						pk.getType() == null ? "" : pk.getType());
				temp.accumulate("virtual",
						pk.getVirtual() == null ? "" : pk.getVirtual());
				temp.accumulate("logo",
						pk.getLogo() == null ? "" : pk.getLogo());
				temp.accumulate(
						"departmentState",
						pk.getDepartmentState() == null ? "" : pk
								.getDepartmentState());
				temp.accumulate("projectType", pk.getProjectType() == null ? ""
						: pk.getProjectType());

				temp.accumulate("fiveClear", pk.getFiveClear() == null ? ""
						: pk.getFiveClear());
				temp.accumulate("updateUser", pk.getUpdateUser() == null ? ""
						: pk.getUpdateUser());
				temp.accumulate("giveNumber", pk.getGiveNumber() == null ? ""
						: pk.getGiveNumber());
				temp.accumulate("createTime", pk.getCreateTime() == null ? ""
						: pk.getCreateTime());
				temp.accumulate("ppwhether", pk.getPpwhether() == null ? ""
						: pk.getPpwhether());

				obj.add(temp);
				jo.accumulate("data", obj);
			} else {
				jo.accumulate("message", "修改方式错误");
				jo.accumulate("status", "fail");
			}
		} else {
			jo.accumulate("message", "有值是空值");
			jo.accumulate("status", "fail");
		}
		result = jo;
		return Action.SUCCESS;
	}
	
	/****
	 * ty<br>
	 *录入支付信息 <br>
	 * 参数<br>
	 * order={订单号}&type={支付方式-银联、支付宝、微信}&number={支付流水号}<br>
	 * 地址<br>
	 * http://192.168.0.8/ea/android/shop_ajax_savePayment.jspa?<br>
	 */
	public String savePayment(){
		JSONObject jo = new JSONObject();
		//订单号
		String orderID=request.getParameter("order");
		//支付方式
		String paymentType=request.getParameter("type");
		//支付流水号
		String paymentNumber=request.getParameter("number");
		if(yanzheng(new String[]{orderID,paymentType,paymentNumber})){
				String sql ="update dtOrder set paymentType=?,paymentNumber=?,paymentTime=? where orderID =? ";
				baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{paymentType,paymentNumber,new Date(),orderID});
				String hql1="from Order where  orderID = ? ";
				Order order=(Order) baseBeanService.getBeanByHqlAndParams(hql1,new Object[]{orderID});
				jo.accumulate("message","成功录入支付信息");
				jo.accumulate("status", "success");
				JSONObject temp=null;
				List<Object> obj=new ArrayList<Object>();					
				temp=new JSONObject();
				temp.accumulate("userID",order.getUserID()==null?"":order.getUserID());//用户ID
				temp.accumulate("supplierID",order.getSupplierID()==null?"":order.getSupplierID());//售货方ID
				temp.accumulate("receiveAddress", order.getReceiveAddress()==null?"":order.getReceiveAddress());//收货地址
				temp.accumulate("orderID", order.getOrderID()==null?"":order.getOrderID());
				temp.accumulate("createTime", sf.format(order.getCreateTime())==null?"":sf.format(order.getCreateTime()));//订单生成时间	
				temp.accumulate("orderType", order.getOrderType()==null?"":order.getOrderType());
				temp.accumulate("orderMoney", order.getOrderMoney()==null?"":order.getOrderMoney());
				temp.accumulate("paymentType", order.getPaymentType()==null?"":order.getPaymentType());
				temp.accumulate("paymentNumber", order.getPaymentNumber()==null?"":order.getPaymentNumber());
				temp.accumulate("paymentTime", order.getPaymentTime()==null?"":order.getPaymentTime());
				temp.accumulate("updateTime", order.getUpdateTime()==null?"":order.getUpdateTime());
				temp.accumulate("updateUser", order.getUpdateUser()==null?"":order.getUpdateUser());
				temp.accumulate("sendProductID", order.getSendProductID()==null?"":order.getSendProductID());
				temp.accumulate("freight", order.getFreight()==null?"":order.getFreight());
				
				obj.add(temp);
				jo.accumulate("data", obj);
		}else{
			jo.accumulate("message","参数为空");
			jo.accumulate("status", "fail");
		}
		result = jo;
		return Action.SUCCESS;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public void setServletResponse(HttpServletResponse res) {
		this.response = res;
	}

	@Override
	public void setServletRequest(HttpServletRequest req) {
		this.request = req;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
}
