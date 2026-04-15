package hy.ea.production.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import hy.ea.bo.CAccount;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.production.GoodFunction;
import hy.ea.production.service.CompanyMaintainService;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.FileUtil;
import hy.ea.util.RandomDatas;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mobile.tiantai.android.util.JushMain;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cloopen.rest.sdk.utils.encoder.BASE64Decoder;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;

@Service
public class CompanyMaintainServiceImpl implements CompanyMaintainService {
	@Resource
	private BaseBeanDao beandao;
	@Resource
	private ServerService serverService;
	@Resource
	private UploadContentToFileService contentToFileService;

	private ProductPackaging productPackaging;
	private GoodFunction goodFunction;
	private ContactCompany concom;

	private List<Object> list;
	private PageForm pageForm;

	/*-----xgb-----*/
	/**
	 * 
	 * 保存文本编辑器内容
	 * 
	 * @param content
	 * @return
	 */
	private String saveContentToFile(String content) {
		String id = RandomDatas.getUUID();
		String path = ServletActionContext.getServletContext().getRealPath("")
				+ "/upload_files/goodDetail/";
		try {
			contentToFileService.saveContent(id, content, path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/upload_files/goodDetail/" + id
				+ UploadContentToFileService.suffix;
	}

	/**
	 * 根据txt URL 获取内容
	 * 
	 * @param url
	 * @return
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
			e.printStackTrace();
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
	 * @Title: 转换
	 * @Description: 获取图片路径
	 * @param cropInp
	 *            :图片地址,photopath:要存储的地方,begin-end:从几截取到几,several:从第几截取
	 * @return 返回数据
	 */
	public String obtainPicture(String cropInp, String photopath, int begin,
			int end, int several) {
		String picpath = "";
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		// 图片路径
		String dir = path + photopath;
		// 转换图片
		File fileLocation = new File(dir);
		// 判断上传路径是否存在，如果不存在就创建
		if (!fileLocation.exists() && !fileLocation.isDirectory()) {
			fileLocation.mkdirs();
		}
		if (cropInp.indexOf("jpeg") != -1) {
			cropInp = cropInp.replaceFirst("jpeg", "jpg");
		}
		// 重命名
		String upName = UUID.randomUUID().toString()
				+ System.currentTimeMillis() + "."
				+ cropInp.substring(begin, end);
		FileOutputStream out;
		String iconBase64 = cropInp.substring(several);
		try {
			byte[] buffer = new BASE64Decoder().decodeBuffer(iconBase64);
			picpath = photopath + "\\" + upName;
			out = new FileOutputStream(dir + "\\" + upName);
			out.write(buffer);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return picpath;
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
	 * @Title: 删除
	 * @Description: 删除公司新闻
	 * @param ppID
	 *            :产品id
	 * @return 返回true:成功false:失败
	 */
	@Override
	@Transactional
	public boolean delNews(String ppID) {
		String sql = "update dt_productpackaging p set p.delstatus = '01' where p.ppid in (";
		Object[] obj = ppID.split(",");
		for (int i = 0; i < obj.length; i++) {
			sql += "?,";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += ")";
		boolean b = true;
		try {
			beandao.saveBeansListAndexecuteSqlsByParams(null,
					new String[] { sql }, obj);
		} catch (Exception e) {
			b = false;
		}
		return b;
	}

	/**
	 * @Title: 查询
	 * @Description: 查询全部公司新闻
	 * @param pageNumber
	 *            :当前页,pageSize:每页几条数据,companyId:公司id
	 * @return 返回的数据集合
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Map<String, Object> queryAllNews(int pageNumber, int pageSize,
			String companyId, String staffid) {
		StringBuilder sb1 = new StringBuilder();
		sb1.append("select p.goodsName,to_char(p.packagingDate,'YYYY-MM-DD HH24:MI:SS'),p.image,p.goodsID,p.model,");
		sb1.append("p.companyID,p.ppID,p.staffID,f.staffname first,s.staffname amend  ");
		sb1.append("from dt_ProductPackaging p,dt_hr_staff f,dt_hr_staff s ");
		sb1.append("where p.fiveClear = ? and p.companyID=? and p.type=? and p.delStatus = ? ");
		sb1.append("and p.staffID = f.staffID and p.amendStaffID = s.staffID order by p.packagingDate desc");
		// 判断如果当前用户companyId为空,则查询CAccount表将companyId赋值给当前用户
		if (companyId == null || companyId.equals("")) {
			CAccount cAccount = (CAccount) beandao.getBeanByHqlAndParams(
					"from CAccount c where c.staffID=?",
					new Object[] { staffid });
			companyId = cAccount.getCompanyID();
		}
		pageForm = getPageFormBySQL(pageNumber, pageSize, sb1.toString(),
				"select count(*) from (" + sb1.toString() + ")", new Object[] {
						"2", companyId, "公司新闻", "00" });
		if (pageForm != null) {
			list = new ArrayList<Object>();
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object obj = (Object) pageForm.getList().get(i);
				Object[] oo = (Object[]) obj;
				obj = beandao
						.getObjectBySqlAndParams(
								"select g.url from (select * from dtgoodfunction d where d.goodsid = ? order by d.gfkey) g where rownum=1",
								new Object[] { oo[3] });

				list.add(
						i,
						new String[] {
								((oo[0] == null) ? "" : oo[0]).toString(),
								((oo[1] == null) ? "" : oo[1]).toString(),
								((oo[2] == null) ? "" : oo[2]).toString(),
								((oo[3] == null) ? "" : oo[3]).toString(),
								((oo[4] == null) ? "" : oo[4]).toString(),
								obj == null ? "" : getContentFromFile(obj
										.toString()),
								((oo[5] == null) ? "" : oo[5]).toString(),
								((oo[6] == null) ? "" : oo[6]).toString(),
								((oo[7] == null) ? "" : oo[7]).toString(),
								((oo[8] == null) ? "" : oo[8]).toString(),
								((oo[9] == null) ? "" : oo[9]).toString() });
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageSize", pageForm.getPageSize());
			map.put("count", pageForm.getRecordCount());
			map.put("pagecount", pageForm.getPageCount());
			map.put("pagenumber", pageForm.getPageNumber());
			map.put("list", list);
			return map;
		}
		return null;
	}

	/**
	 * @Title: 查询
	 * @Description: 查询公司新闻详情
	 * @param ppID
	 *            :产品id
	 * @return 返回的数据集合
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Object> queryNews(String ppID) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p.ppid,p.goodsid,p.image,p.goodsname,g.url,");
		sb.append("to_char(p.packagingdate,'YYYY-MM-DD'),p.companyid ");
		sb.append("from dt_productpackaging p left join dtgoodfunction g ");
		sb.append("on p.goodsid=g.goodsid where p.ppid=?");
		List<BaseBean> listBaseBean = beandao.getListBeanBySqlAndParams(
				sb.toString(), new Object[] { ppID });
		list = new ArrayList<Object>();
		for (int i = 0; i < listBaseBean.size(); i++) {
			Object obj = (Object) listBaseBean.get(i);
			Object[] oo = (Object[]) obj;
			list.add(
					i,
					new String[] {
							((oo[0] == null) ? "" : oo[0]).toString(),
							((oo[1] == null) ? "" : oo[1]).toString(),
							((oo[2] == null) ? "" : oo[2]).toString(),
							((oo[3] == null) ? "" : oo[3]).toString(),
							getContentFromFile(((oo[4] == null) ? "" : oo[4])
									.toString()),
							((oo[5] == null) ? "" : oo[5]).toString(),
							((oo[6] == null) ? "" : oo[6]).toString() });
		}
		return list;
	}

	/**
	 * @Title: 增加或修改
	 * @Description: 增加或修改公司新闻
	 * @param ppID
	 *            :产品id,goodsName:产品名称,cropInp:图片地址,cropInpFileName:图片名称,staffid
	 *            :人员id,companyID:公司id,content:内容
	 * @return
	 * @return 返回数据
	 */
	@Override
	@Transactional
	public Map<String, Object> saveOrUpdateNews(String ppID, String goodsName,
			String cropInp, String cropInpFileName, String staffid,
			String companyID, String content) {
		String path = ServletActionContext.getServletContext().getRealPath("/");
		String basePath = ServletActionContext.getRequest().getScheme() + "://"
				+ ServletActionContext.getRequest().getServerName() + ":"
				+ ServletActionContext.getRequest().getServerPort() + "/hyplat";
		// 图片保存路径
		String photopath = "upload_files/gooddesign/"
				+ Utilities.getDateString(new Date(), "yyyy-MM-dd");
		// 查询产品
		productPackaging = (ProductPackaging) beandao.getBeanByHqlAndParams(
				"from ProductPackaging where ppID=? ", new Object[] { ppID });
		// 判断如果当前用户companyId为空,则查询CAccount表将companyId赋值给当前用户
		if (companyID == null || companyID.equals("")) {
			CAccount cAccount = (CAccount) beandao.getBeanByHqlAndParams(
					"from CAccount c where c.staffID=?",
					new Object[] { staffid });
			companyID = cAccount.getCompanyID();
		}
		// 判断产品是否存在
		if (productPackaging == null) {
			// 手机增加新闻
			productPackaging = new ProductPackaging();
			productPackaging.setPpID(serverService.getServerID("p"));
			productPackaging.setStaffID(staffid);
			productPackaging.setAmendStaffID(staffid);
			productPackaging.setPackagingDate(new Date());
			productPackaging.setType("公司新闻");
			productPackaging.setFiveClear("2");
			productPackaging.setDelStatus("00");
			productPackaging.setCompanyID(companyID);
			productPackaging.setGoodsID(serverService
					.getServerID("goodsManage"));
			// 添加新闻主图
			if (!cropInp.equals("") && cropInp != null) {
				String picpath = obtainPicture(cropInp, photopath, 11, 14, 22);
				productPackaging.setImage(picpath);
			}
		} else {
			// 手机修改新闻
			productPackaging.setPackagingDate(new Date());
			productPackaging.setAmendStaffID(staffid);
			productPackaging.setGoodsName(goodsName);
			// 删除新闻主图
			boolean b = cropInp.equals(productPackaging.getImage());
			if (!b) {
				FileUtil.delete(path + productPackaging.getImage());
				String picpath = obtainPicture(cropInp, photopath, 11, 14, 22);
				productPackaging.setImage(picpath);
			}
			// 删除txt中图片
			List<BaseBean> list = beandao.getListBeanByHqlAndParams(
					"from GoodFunction where goodsid = ?",
					new Object[] { productPackaging.getGoodsID() });
			for (int i = 0; i < list.size(); i++) {
				GoodFunction goodFunction = (GoodFunction) list.get(i);
				String[] t = getContentFromFile(goodFunction.getUrl()).split(
						"<img src=\"");
				for (int j = 1; j < t.length; j++) {
					String parh = t[j].substring(28,
							t[j].indexOf("\" style=\"max-width:90%"));
					boolean a = content.contains(parh);
					if (!a) {
						FileUtil.delete(path + parh);
					}
				}
				FileUtil.delete(path + goodFunction.getUrl());
			}
			// 删除与新闻关联的物品表
			beandao.saveBeansListAndexecuteSqlsByParams(
					null,
					new String[] { "delete from dtGoodFunction where goodsid = ?" },
					new Object[] { productPackaging.getGoodsID() });
		}
		// 添加与新闻关联的物品表
		if (!content.equals("") && content != null) {
			String[] proimg = content
					.split("<p style=\"display:none;\">,-</p>");
			StringBuilder htl = new StringBuilder();
			for (int i = 0; i < proimg.length; i++) {
				// 判断截取到的是否为img标签
				if (proimg[i].indexOf("<img src=\"data:image/") != -1) {
					// 截取到img的src值
					String imgsrc = proimg[i].substring(9,
							proimg[i].indexOf("\" style=\"max-width:90%"));
					String path1 = obtainPicture(imgsrc, photopath, 12, 15, 23);
					htl.append("<img src=\""
							+ basePath
							+ "/"
							+ path1
							+ "\" style=\"max-width:90%;display:block;margin:0.5rem auto;\">");
				} else {
					htl.append(proimg[i]);
				}
			}
			// 添加物品
			goodFunction = new GoodFunction();
			goodFunction.setGfid(serverService.getServerID("gfid"));
			goodFunction.setGoodsid(productPackaging.getGoodsID());
			goodFunction.setName(productPackaging.getGoodsName());
			goodFunction.setOrders(1);
			goodFunction.setType("1");
			String url = saveContentToFile(htl.toString().trim());
			goodFunction.setUrl(url);
			beandao.save(goodFunction);
		}
		// 添加或者修改新闻
		productPackaging.setGoodsName(goodsName);
		beandao.saveOrUpdate(productPackaging);
		// 安卓推送所用的参数
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		ContactCompany ccom = (ContactCompany) beandao
				.getBeanByHqlAndParams(
						"select y from CcomCom m,ContactCompany y where m.comanyId = ? and m.ccompanyId=y.ccompanyID",
						new Object[] { companyID });
		String body = basePath
				+ "ea/industry/ea_informationDetails.jspa?goodsid="
				+ goodFunction.getGoodsid() + "&ppId="
				+ productPackaging.getPpID() + "&companyId=" + companyID
				+ "&search=" + ccom.getCompanyName() + "&back=3&ccompanyId="
				+ ccom.getCcompanyID() + "&type=web";
		parameterMap.put("message", goodsName);
		parameterMap.put("type", "新闻");
		parameterMap.put("body", body);
		parameterMap.put("id", "news");
		parameterMap.put("staffid", staffid);
		parameterMap.put("companyID", companyID);
		return parameterMap;
	}

	/**
	 * @Title: 查询
	 * @Description: 查询个人所在公司(如果个人没有公司,那么查询其上级个人所在公司直至找到有公司为止)公众号二维码以及公司name
	 * @param
	 * @return 返回数据
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public ContactCompany queryCompanyDetails(String staffid) {
		concom = (ContactCompany) beandao
				.getBeanByHqlAndParams(
						"select c from TEshopCusCom t,CcomCom m,ContactCompany c where t.companyId = m.comanyId and m.ccompanyId = c.ccompanyID and t.staffid = ? ",
						new Object[] { staffid });
		// 如果当前用户所在公司没有公众二维码,则查找当前账户上级所在公司的公众二维码
		if (concom == null || concom.getPmCodePath() == null) {
			StringBuilder sb = new StringBuilder();
			// 正式发布时将t.account改成t.sccId
			sb.append("select y.companyname,y.pmcodepath ");
			sb.append("from (select * from t_eshop_cuscom t start wITh t.staffid = ? ");
			sb.append("connect by prior t.superioragent = t.account) t,DT_ccom_com m,Dtcontactcompany y ");
			sb.append("where t.companyid = m.compnay_id and m.ccompany_id = y.ccompanyid ");
			List<BaseBean> bbList = beandao.getListBeanBySqlAndParams(
					sb.toString(), new Object[] { staffid });
			for (int i = 0; i < bbList.size(); i++) {
				Object obj = bbList.get(i);
				Object[] oo = (Object[]) obj;
				if (oo[1] != null) {
					concom = new ContactCompany();
					concom.setCompanyName((String) oo[0]);
					concom.setPmCodePath((String) oo[1]);
					return concom;
				}
			}
			// 如果上级所在公司也没有公众二维码,则查询北京天太世统科技有限责任公司的公众二维码
			concom = (ContactCompany) beandao
					.getBeanByHqlAndParams(
							"from ContactCompany where ccompanyID = ?",
							new Object[] { "contactCompany20101230UB4U5884S30000000176" });
		}
		return concom;
	}

	/**
	 * @Title: 推送
	 * @Description: 安卓推送功能
	 * @param message
	 *            :提示信息主标题,type:信息类型,body:具体消息内容,可以存放url,id:推送的业务
	 *            id,指定某类型的页面跳转,ssJudge
	 *            :推送判断(暂定)0:全部,1:所在公司,2:所在部门,3:个人,accountNumber
	 *            :推送条件,staffid:用户id
	 * @return 返回数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void androidShoveSend(String message, String type, String body,
			String id, String ssJudge, String accountNumber, String staffid,String companyId) {
		List<String> slist = new ArrayList<String>();
		String ssql;
		if (ssJudge.equals("0")) {
			ssql = "select t.jiguangno  from t_eshop_customer t  where length(t.jiguangno) >1";
			slist = transitionList(ssql, null);
		} else if (ssJudge.equals("1")) {
			ssql = "select t.jiguangno from dtcaccount n,t_eshop_customer t where n.companyid = ? and n.staffid =t.staffid  and length(t.jiguangno) >1";
			slist = transitionList(ssql, new Object[] { companyId });
		} else if (ssJudge.equals("2")) {
			StringBuilder sb = new StringBuilder();
			sb.append("select distinct r.jiguangno ");
			sb.append("from dtCos c,(select s.organizationid from dtCos s ");
			sb.append("where s.staffid = ? and s.organizationid <> ? ");
			sb.append("and s.cosstatus = ?) o,t_eshop_customer r ");
			sb.append("where o.organizationid = c.organizationid ");
			sb.append("and c.staffid=r.staffid and length(t.jiguangno) >1");
			slist = transitionList(sb.toString(), new Object[]{staffid,"99","50"});
		}else if(ssJudge.equals("3")){
			String[] arr=accountNumber.split(",");
			for (int i = 0; i < arr.length; i++) {
				slist.add(arr[i]);
			}
		}
		JushMain.sendjiguangMessage(message, type, body, id, slist);	
	}
	/**
	 * @Title: 查询
	 * @Description: 查询用户粉丝好友
	 * @param staffid:用户id
	 * @return 返回数据
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> userFriend(String staffid) {
		StringBuilder sb = new StringBuilder();
		sb.append("select f.headimage,f.staffname,r.jiguangno ");
		sb.append("from t_Eshop_Cuscom m,dtJoinFans s,t_eshop_customer r,dt_hr_staff f ");
		sb.append("where m.staffid = ? and m.account = s.zaccount ");
		sb.append("and s.faccount = r.account and r.staffid = f.staffid ");
		List<BaseBean> ufList = beandao.getListBeanBySqlAndParams(sb.toString(), new Object[]{staffid});
		return ufList;
	}
}
