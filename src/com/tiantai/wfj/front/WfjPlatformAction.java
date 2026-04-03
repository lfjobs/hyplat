package com.tiantai.wfj.front;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hy.ea.bo.finance.ProductCommentMain;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.WfjJifenService;
import com.tiantai.wfj.util.SessionWrap;

import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.bo.finance.ProductComment;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.StaffAddress;
import hy.ea.service.UploadContentToFileService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
public class WfjPlatformAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private UploadContentToFileService contentToFileService;

	@Resource
	private WfjJifenService wfjJifenService;
	private ProductPackaging productDesign;
	private ProductPackaging productDesign1;
	private String type;// 简介还是文化
	private String ppid; // 产品id
	private String pcid;// 评论id
	private String goodsid;// 物品id
	private List<BaseBean> productList;// 产品列表
	private Map<String, String> maplist;
	private Map<Integer, String[]> maplist1;
	private Map<Integer, String[]> maplist2;
	private Map<Integer, String[]> maplist3;
	private Object result;// AJAX使用
	private String content;// 新闻评论内容
	private String count;// 新闻评论条数
	private PageForm pageForm;
	private List<BaseBean> list;
	private Object object1;
	private Object object2;
	private String typeNews;// 对新闻评论、对评论评论
	private List<BaseBean> list1;// 更多精彩
	private String flag;// 判断标识

	private String ccompanyId;
	private String companyId;// 公司ID-xgb
	private int back;// 标识-xgb
	private String search;// 公司名称-xgb
	private String miniSystemJudge;//00:简介,01:文化,02:新闻

	private CDetail cdl;// 公司详细信息
	private String model;
	private Company company;// 购买使用对象
	private String types;
	private String typeId;
	private String typemodel;
	private StaffAddress address;
	private List<BaseBean> beans;
	private String user;
	private String moneys;
	private String standard;
	private String ppids;
	private String industryType;

	/**
	 * 根据txt URL 获取内容
	 *
	 * @param filepath
	 * @return
	 */
	private String getContentFromFile(String filepath) {
		String path = ServletActionContext.getServletContext().getRealPath("\\") + filepath;
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			return contentToFileService.getContent(path);

		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	// 进入平台
	public String getPlatformByPpid() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String typeant = request.getParameter("typeant");
		HttpSession session =ServletActionContext.getRequest().getSession();
		session.setAttribute("typeant", typeant);

		StringBuffer hqk = new StringBuffer();
		StringBuffer hqk2 = new StringBuffer();
		// 平台信息
		if (ppid == null || ppid.equals("")) {
			productDesign = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(
					"from ProductPackaging where parentId is null and type = ?", new Object[] { "联营经济平台" });


			ppid = productDesign.getPpID();
		} else {
			productDesign = (ProductPackaging) baseBeanService
					.getBeanByHqlAndParams("from ProductPackaging where ppid = ?", new Object[] { ppid });


		}
		if(ppid != null&&ppid != ""){
			hqk.append(" from TEshopCusCom where ppid = ? and acquiesce='00'");
			TEshopCusCom the = (TEshopCusCom) baseBeanService.
					getBeanByHqlAndParams(hqk.toString(), new Object[]{ppid});
			if(the != null){

				hqk2.append(" select com.companyid,com.companyname,cay.ccompanyid from dtCompany com,dtContactCompany cay, DT_ccom_com c");
				hqk2.append(" where c.compnay_id=com.companyid and c.ccompany_id=cay.ccompanyid");
				hqk2.append(" and com.companyname=? ");
				Object obj = baseBeanService.getObjectBySqlAndParams(hqk2.toString(), new Object[]{the.getPseudoCompanyName()});
				request.setAttribute("obj", obj);


			}
		}

		PlatformInfo();
		return "Platform";
	}

	// 返回上级平台
	public String backPlatform() {
		String sql = "select p.ppid from dt_productpackaging p left join dt_productpackaging pp on p.ppid = pp.parentid where pp.ppid=? ";
		Object object = baseBeanService.getObjectBySqlAndParams(sql, new Object[] { ppid });
		ppid = object.toString();
		productDesign = (ProductPackaging) baseBeanService.getBeanByHqlAndParams("from ProductPackaging where ppid = ?",
				new Object[] { ppid });
		PlatformInfo();
		return "Platform";
	}

	@SuppressWarnings("unchecked")
	public void PlatformInfo() {
		// 案例展示
		StringBuilder sql = new StringBuilder();
		sql.append("select pp.goodsName,pp.ppID,pp.image,pp.type from");
		sql.append(" dt_ProductPackaging pp,dt_ProductPackaging p");
		sql.append(
				" where pp.parentId = p.ppid and pp.type=? and pp.parentId=? and pp.delStatus='00' order by pp.ppid");
		list = baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[] { "联营经济平台", ppid });
		// 新闻咨询
		StringBuilder newsql = new StringBuilder();
		newsql.append("select p.goodsName,p.PackagingDate,p.image,p.goodsID,p.parentid,g.url,dc.companyname");
		newsql.append(" from dt_productpackaging p left join dtcompany dc on dc.companyid = p.companyid");
		newsql.append(
				" left join (select p2.ppid from t_eshop_cuscom t left join dt_productpackaging p2 on t.companyid = p2.companyid and p2.type = '新闻'");
		newsql.append(
				" left join dtGoodFunction g1 on p2.goodsid = g1.goodsid where t.ppid=? and g1.orders = '1') pp on p.ppid = pp.ppid");
		newsql.append(
				" left join (select p3.ppid from dt_productpackaging p3 left join dtGoodFunction g2 on p3.goodsid = g2.goodsid");
		newsql.append(" where p3.parentid=? and p3.type = '新闻' and g2.orders = '1') pp2 on p.ppid = pp2.ppid");
		newsql.append(
				" left join dtGoodFunction g on g.goodsid = p.goodsid and g.orders = '1' where (pp.ppid is not null or pp2.ppid is not null) and p.delStatus='00'");
		newsql.append(
				" group by p.goodsName,p.PackagingDate,p.image,p.goodsID,p.parentid,g.url,dc.companyname order by p.packagingdate desc");
		pageForm = baseBeanService.getPageFormBySQL(1, 4, newsql.toString(), "select count(*) from (" + newsql + ")",
				new Object[] { ppid, ppid });
		maplist1 = new HashMap<Integer, String[]>();
		// 获取每个功能信息的具体内容
		if (pageForm != null) {
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object obj = (Object) pageForm.getList().get(i);
				Object[] oo = (Object[]) obj;
				maplist1.put(i,
						new String[] { oo[0] == null ? "" : oo[0].toString(), oo[1] == null ? "" : oo[1].toString(),
								oo[2] == null ? "" : oo[2].toString(), oo[3] == null ? "" : oo[3].toString(),
								oo[4] == null ? "" : oo[4].toString(),
								getContentFromFile(oo[5] == null ? "" : oo[5].toString()),
								oo[6] == null ? "" : oo[6].toString() });
			}
		}
		// 平台公告
		StringBuilder noticesql = new StringBuilder();
		noticesql.append("select pp.goodsName,pp.PackagingDate,pp.image,pp.goodsID,g.url");
		noticesql.append(" from dt_ProductPackaging pp,dtGoodFunction g where pp.parentId=? and");
		noticesql.append(
				" pp.goodsID=g.goodsid and pp.type='公告' and g.orders='1' and pp.delStatus='00' order by pp.PackagingDate desc");
		pageForm = baseBeanService.getPageFormBySQL(1, 3, noticesql.toString(),
				"select count(*) from (" + noticesql + ")", new Object[] { ppid });
		maplist2 = new HashMap<Integer, String[]>();
		// 获取每个功能信息的具体内容
		if (pageForm != null) {
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object obj = (Object) pageForm.getList().get(i);
				Object[] oo = (Object[]) obj;
				maplist2.put(i, new String[] { oo[0].toString(), oo[1].toString(), oo[2].toString(), oo[3].toString(),
						getContentFromFile(oo[4] == null ? "" : oo[4].toString()) });
			}
		}
		// 平台简介
		summary();
	}

	// 平台简介、文化
	@SuppressWarnings("unchecked")
	public void summary() {
		StringBuilder summarysql = new StringBuilder();
		summarysql.append("select pp.parentname,g.url");
		summarysql.append(" from dt_ProductPackaging pp,dtGoodFunction g where pp.parentId=? and");
		summarysql.append(" pp.goodsID=g.goodsid and pp.type=? and pp.delStatus='00' order by g.orders");
		String hql = "from ProductPackaging where parentId = ? and type=? and delStatus='00'";
		if (type.equals("culture")) {
			productDesign1 = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { ppid, "平台文化" });
			productList = baseBeanService.getListBeanBySqlAndParams(summarysql.toString(),
					new Object[] { ppid, "平台文化" });
		} else {
			productDesign1 = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { ppid, "平台简介" });
			productList = baseBeanService.getListBeanBySqlAndParams(summarysql.toString(),
					new Object[] { ppid, "平台简介" });
		}
		maplist3 = new HashMap<Integer, String[]>();
		// 获取每个功能信息的具体内容
		if (productList != null && productList.size() != 0) {
			for (int i = 0; i < productList.size(); i++) {
				Object obj = (Object) productList.get(i);
				Object[] oo = (Object[]) obj;
				maplist3.put(i, new String[] { oo[0] == null ? "" : oo[0].toString(),
						getContentFromFile(oo[1] == null ? "" : oo[1].toString()) });
			}
		}
	}

	public String platformSummary() {
		summary();
		return "PlatformSummary";
	}

	// 新闻页面
	public String platformNews() {


		return "Platformnews";
	}

	@SuppressWarnings("unchecked")
	public String newdetail() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);

		String sql1 = "select g.url,g.name from dt_ProductPackaging p join dtGoodFunction g on p.goodsID=g.goodsid  where p.type=? and p.parentid=? and p.delStatus='00' and p.goodsID=?";
		String sql2 = "select p.goodsName,p.PackagingDate,p.image,p.model,p.tradeName from dt_ProductPackaging p where p.type=? and p.delStatus='00' and p.goodsID=? and p.parentid=? ";
		list = baseBeanService.getListBeanBySqlAndParams(sql1, new Object[] { "新闻", ppid, goodsid });
		maplist1 = new HashMap<Integer, String[]>();
		// 获取每个功能信息的具体内容
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				Object obj = (Object) list.get(i);
				Object[] oo = (Object[]) obj;
				maplist1.put(i, new String[] { getContentFromFile(oo[0] == null ? "" : oo[0].toString()),
						oo[1] == null ? "" : oo[1].toString() });
			}
		}
		object1 = baseBeanService.getObjectBySqlAndParams(sql2, new Object[] { "新闻", goodsid, ppid });

		if (cus != null) {
			ProductComment pc = (ProductComment) baseBeanService.getBeanByHqlAndParams(
					"from ProductComment where goodsId=? and staffid=? and type=?",
					new Object[] { goodsid, cus.getStaffid(), "2" });
			if (pc != null) {
				flag = pc.getIscollect();
			} else {
				flag = "0";
			}
		} else {
			flag = "0";
		}

		Object[] oo1 = (Object[]) object1;
		// 更多精彩
		StringBuilder newsql = new StringBuilder();

		newsql.append("select p.goodsName,p.goodsID ");
		newsql.append(" from dt_productpackaging p left join dtcompany dc on dc.companyid = p.companyid");
		newsql.append(
				" left join (select p2.ppid from t_eshop_cuscom t left join dt_productpackaging p2 on t.companyid = p2.companyid and p2.type = '新闻'");
		newsql.append(
				" left join dtGoodFunction g1 on p2.goodsid = g1.goodsid where t.ppid=? and p2.tradename=? and g1.orders = '1') pp on p.ppid = pp.ppid");
		newsql.append(
				" left join (select p3.ppid from dt_productpackaging p3 left join dtGoodFunction g2 on p3.goodsid = g2.goodsid");
		newsql.append(
				" where p3.parentid=? and p3.tradename=? and p3.type = '新闻' and g2.orders = '1') pp2 on p.ppid = pp2.ppid");
		newsql.append(
				" left join dtGoodFunction g on g.goodsid = p.goodsid and g.orders = '1' where (pp.ppid is not null or pp2.ppid is not null) and p.delStatus='00' and g.goodsid <> ?");
		newsql.append(" group by p.goodsName,p.PackagingDate,p.goodsID order by p.packagingdate desc");
		list1 = baseBeanService.getListBeanBySqlAndParams(newsql.toString(), new Object[] { ppid,
				oo1[4] == null ? "" : oo1[4].toString(), ppid, oo1[4] == null ? "" : oo1[4].toString(), goodsid });
		return "newdetail";
	}

	// 评论列表
	public String AjaxComment() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);

		StringBuilder commentsql = new StringBuilder();
		if (typeNews.equals("0")) {// 对新闻进行评论列表
//			commentsql.append("select s.headimage,s.staffname,p.commentdate,p.content,p.praise,p.count,p.pcid");
//			commentsql.append(" from dt_hr_Staff s left join dt_ProductComment p on s.staffid = p.staffid");
//			commentsql.append(
//					" where p.goodsId=? and p.type=? and p.ppid=? and p.pcpid is null order by p.commentdate desc");
			commentsql.append("select a.headimage,a.staffname as aname,a.commentdate,a.content,a.praise,a.count,a.pcid,f.staffname as fname ");
			commentsql.append("from (select s.headimage,s.staffname,p.commentdate,p.content,p.praise, p.count,p.pcid,p.tostaffid ");
			commentsql.append("from dt_hr_Staff s left join dt_ProductComment p on s.staffid = p.staffid ");
			commentsql.append("where p.goodsId = ? and p.type = ? and p.ppid = ? and p.pcpid is null) a ");
			commentsql.append("left join dt_hr_Staff f on a.tostaffid = f.staffid order by a.commentdate desc ");
			pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
					(null != pageForm ? pageForm.getPageSize() : 15), commentsql.toString(),
					"select count(*) from (" + commentsql.toString() + ")", new Object[] { goodsid, "0", ppid });
		} else {// 对评论进行评论列表
//			commentsql.append("select s.headimage,s.staffname,p.commentdate,p.content,p.praise,p.count,p.pcid");
//			commentsql.append(" from dt_hr_Staff s left join dt_ProductComment p on s.staffid = p.staffid");
//			commentsql.append(" where p.pcpid=? and p.ppid=? and p.type='0' order by p.commentdate desc");
			commentsql.append("select a.headimage,a.staffname as aname,a.commentdate,a.content,a.praise,a.count,a.pcid,f.staffname as fname ");
			commentsql.append("from (select s.headimage,s.staffname,p.commentdate,p.content,p.praise,p.count,p.pcid,p.tostaffid ");
			commentsql.append("from dt_hr_Staff s left join dt_ProductComment p on s.staffid = p.staffid ");
			commentsql.append("where p.pcpid = ? and p.ppid = ? and p.type = ?) a ");
			commentsql.append("left join dt_hr_Staff f on a.tostaffid = f.staffid ");
			commentsql.append("order by a.commentdate desc");
			pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
					(null != pageForm ? pageForm.getPageSize() : 15), commentsql.toString(),
					"select count(*) from (" + commentsql + ")", new Object[] { pcid, ppid,"0" });
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		if (pageForm != null) {
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object obj = (Object) pageForm.getList().get(i);
				Object[] oo = (Object[]) obj;
				if (cus != null) {
					ProductComment pc = (ProductComment) baseBeanService.getBeanByHqlAndParams(
							"from ProductComment where pcPID=? and type=? and staffId=?",
							new Object[] { oo[6] == null ? "" : oo[6].toString(), "1", cus.getStaffid() });
					if (pc != null) {
						list.add(new String[] { oo[0] == null ? "" : oo[0].toString(),
								oo[1] == null ? "" : oo[1].toString(), oo[2] == null ? "" : oo[2].toString(),
								oo[3] == null ? "" : oo[3].toString(), oo[4] == null ? "" : oo[4].toString(),
								oo[5] == null ? "" : oo[5].toString(), oo[6] == null ? "" : oo[6].toString(),
										oo[7] == null ? "" : oo[7].toString(),pc.getIspraise() });
					} else {
						list.add(new String[] { oo[0] == null ? "" : oo[0].toString(),
								oo[1] == null ? "" : oo[1].toString(), oo[2] == null ? "" : oo[2].toString(),
								oo[3] == null ? "" : oo[3].toString(), oo[4] == null ? "" : oo[4].toString(),
								oo[5] == null ? "" : oo[5].toString(), oo[6] == null ? "" : oo[6].toString(),
										oo[7] == null ? "" : oo[7].toString()});
					}
				} else {
					list.add(new String[] { oo[0] == null ? "" : oo[0].toString(),
							oo[1] == null ? "" : oo[1].toString(), oo[2] == null ? "" : oo[2].toString(),
							oo[3] == null ? "" : oo[3].toString(), oo[4] == null ? "" : oo[4].toString(),
							oo[5] == null ? "" : oo[5].toString(), oo[6] == null ? "" : oo[6].toString(),
									oo[7] == null ? "" : oo[7].toString() });
				}
			}
			map.put("pagecount", pageForm.getPageCount());
			map.put("pagenumber", pageForm.getPageNumber());
		}
		map.put("list", list);
		String sql = "select count(*) from dt_hr_Staff s left join dt_ProductComment p on s.staffid = p.staffid where p.goodsId=? and p.ppid=? and p.type='0' and p.pcpid is null";
		count = (baseBeanService.getConutByBySqlAndParams(sql, new Object[] { goodsid, ppid })) + "";
		map.put("count", count);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	//ajax判断当前用户是否登陆
	public String ajaxWhetherTheLanding() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		boolean bt = true;
		if(cus==null){
			bt=false;
			session.removeAttribute("url");
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", bt);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	// 新闻列表
	public String AjaxNews() {
		String name = "";
		ProductPackaging pg = (ProductPackaging) baseBeanService
				.getBeanByHqlAndParams("from ProductPackaging where ppID = ?", new Object[] { ppid });
		if (pg != null) {
			name = pg.getGoodsName();
		}
		StringBuilder newsql = new StringBuilder();
		newsql.append("select p.goodsName,p.PackagingDate,p.image,p.goodsID,p.parentid,g.url,dc.companyname");
		newsql.append(" from dt_productpackaging p left join dtcompany dc on dc.companyid = p.companyid");
		newsql.append(
				" left join (select p2.ppid from t_eshop_cuscom t left join dt_productpackaging p2 on t.companyid = p2.companyid and p2.type = '新闻'");
		newsql.append(
				" left join dtGoodFunction g1 on p2.goodsid = g1.goodsid where t.ppid=? and g1.orders = '1') pp on p.ppid = pp.ppid");
		newsql.append(
				" left join (select p3.ppid from dt_productpackaging p3 left join dtGoodFunction g2 on p3.goodsid = g2.goodsid");
		newsql.append(" where p3.parentid=? and p3.type = '新闻' and g2.orders = '1') pp2 on p.ppid = pp2.ppid");
		newsql.append(
				" left join dtGoodFunction g on g.goodsid = p.goodsid and g.orders = '1' where (pp.ppid is not null or pp2.ppid is not null) and p.delStatus='00'");
		newsql.append(
				" group by p.goodsName,p.PackagingDate,p.image,p.goodsID,p.parentid,g.url,dc.companyname order by p.packagingdate desc");

		List<Object> parms = new ArrayList<Object>();
		parms.add(ppid);
		parms.add(ppid);
		/*--ljc--*/
		if (type != null && type.equals("web")) {
			newsql.delete(0, newsql.length());
			parms.removeAll(parms);
			newsql.append(
					"select p.goodsName,p.PackagingDate,p.image,p.goodsID,p.parentid,g.url,y.companyname,p.ppid,p.type,p.model ");
			newsql.append("from dt_ProductPackaging p,dtGoodFunction g,dt_ccom_com d,dtcompany y ");
			newsql.append("where ((p.fiveclear=? and p.type=?) ");
			parms.add("2");
			if(miniSystemJudge.equals("00")){
				newsql.append(")");
				parms.add("公司简介");
			}else if(miniSystemJudge.equals("01")){
				newsql.append(")");
				parms.add("公司文化");
			}else if(miniSystemJudge.equals("02") || miniSystemJudge.equals("03")){
				newsql.append(" or (p.type='会员分享' and p.review='00'))");
				parms.add("公司新闻");
			}
			newsql.append(" and p.goodsid=g.goodsid");
			newsql.append(" and y.companyid=p.companyid");
			newsql.append(" and d.compnay_id=y.companyid and p.delStatus = '00' ");
			if(industryType!=null && !"".equals(industryType)){
				if(miniSystemJudge.equals("00")){
					newsql.append(" and d.ccompany_id=? ");
					parms.add(ccompanyId);
				}
				newsql.append(" and y.industrytype = ? order by p.PackagingDate desc");
				parms.add("汽车交通工具/汽车驾校");
			}else {
				newsql.append(" and d.ccompany_id=? order by p.PackagingDate desc");
				parms.add(ccompanyId);
			}

		}
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 8,
				newsql.toString(), "select count(*) from (" + newsql + ")", parms.toArray());
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取每个功能信息的具体内容
		if (pageForm != null) {
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object obj = (Object) pageForm.getList().get(i);
				Object[] oo = (Object[]) obj;
				list.add(new String[] { oo[0] == null ? "" : oo[0].toString(), oo[1] == null ? "" : oo[1].toString(),
						oo[2] == null ? "" : oo[2].toString(), oo[3] == null ? "" : oo[3].toString(),
						oo[4] == null ? "" : oo[4].toString(),
						getContentFromFile(oo[5] == null ? "" : oo[5].toString()),
						oo[6] == null ? "" : oo[6].toString(), name, oo[7] != null ? oo[7].toString() : "",
						oo[8] != null ? oo[8].toString() : "", oo[9] != null ? oo[9].toString() : "" });
			}
			map.put("pagecount", pageForm.getPageCount());
			map.put("pagenumber", pageForm.getPageNumber());
			map.put("recordCount", pageForm.getRecordCount());
		}
		map.put("list", list);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	// 保存评论
	public String newsComment() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		Map<String, Object> map = new HashMap<String, Object>();
		if (cus == null) {
			HttpServletRequest request = ServletActionContext.getRequest();
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
			map.put("login", "login");
			JSONObject json = JSONObject.fromObject(map);
			result = json.toString();
			return "success";
		}
		List<BaseBean> beans = new ArrayList<BaseBean>();
		// 保存评论数据
		ProductComment pc = new ProductComment();
		pc.setPcID(serverService.getServerID("pcid"));
		pc.setGoodsId(goodsid);
		pc.setStaffId(cus.getStaffid());
		pc.setContent(content);
		pc.setPraise(0);
		pc.setCount(0);
		pc.setType("0");
		pc.setPpid(ppid);
		pc.setCommentdate(new Date());
		//保存评论总计数
		String hqlmain = "from ProductCommentMain where ppid = ?";
		ProductCommentMain pcm = (ProductCommentMain)baseBeanService.getBeanByHqlAndParams(hqlmain,new Object[]{ppid});
		if(pcm!=null){
			pcm.setPlcount(pcm.getPlcount()+1);
			
		}else{
			pcm = new ProductCommentMain();
			pcm.setPcmID(serverService.getServerID("pcmid"));
			pcm.setPpid(ppid);
			pcm.setPlcount(1);
			pcm.setPraise(0);
		}
		
		beans.add(pcm);
		if (!typeNews.equals("0")) {// 对评论进行评论
			pc.setPcPID(pcid);
			ProductComment pc1 = (ProductComment) baseBeanService
					.getBeanByHqlAndParams("from ProductComment where pcID=?", new Object[] { pcid });
			pc.setToStaffId(pc1.getStaffId());
			pc1.setCount(pc1.getCount() + 1);
			beans.add(pc1);
		}else{
			//xgb新增
			ProductPackaging ppk = (ProductPackaging) baseBeanService.getBeanByHqlAndParams("from ProductPackaging where ppID=?", new Object[]{ppid});
			pc.setToStaffId(ppk.getStaffID());
			Object obj =  baseBeanService.getObjectBySqlAndParams("select (nvl(max(t.whichFloor), 0)+1) as a from dt_productcomment t where t.ppid=? and t.pcpid is null", new Object[] { ppid });
			String whichFloor  = obj.toString();
			pc.setWhichFloor(whichFloor);
		}
		beans.add(pc);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		return "success";
	}

	// 点赞
	public String ajaxPrise() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		Map<String, Object> map = new HashMap<String, Object>();
		if (cus == null) {
			HttpServletRequest request = ServletActionContext.getRequest();
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
			map.put("login", "login");
			JSONObject json = JSONObject.fromObject(map);
			result = json.toString();
			return "success";
		}
		List<BaseBean> beans = new ArrayList<BaseBean>();
		ProductComment pc = (ProductComment) baseBeanService.getBeanByHqlAndParams("from ProductComment where pcID=?",
				new Object[] { pcid });
		if (pc != null) {
			pc.setPraise(pc.getPraise() + 1);
			beans.add(pc);
		}
		ProductComment pc2 = new ProductComment();
		pc2.setPcID(serverService.getServerID("pcid"));
		pc2.setGoodsId(goodsid);
		pc2.setStaffId(cus.getStaffid());
		pc2.setPcPID(pc.getPcID());
		pc2.setIspraise("1");
		pc2.setType("1");
		pc2.setPpid(ppid);
		beans.add(pc2);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		return "success";
	}

	// 新闻收藏
	public String ajaxCollect() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		Map<String, Object> map = new HashMap<String, Object>();
		if (cus == null) {
			HttpServletRequest request = ServletActionContext.getRequest();
			String url = request.getHeader("Referer");

			session.setAttribute("url", url);
			map.put("login", "login");
			JSONObject json = JSONObject.fromObject(map);
			result = json.toString();
			return "success";
		}
		String hql = "from ProductComment where goodsId=? and staffid=? and type=?";
		ProductComment pc = (ProductComment) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { goodsid, cus.getStaffid(), "2" });
		if (pc == null) {
			ProductComment pc2 = new ProductComment();
			pc2.setPcID(serverService.getServerID("pcid"));
			pc2.setGoodsId(goodsid);
			pc2.setStaffId(cus.getStaffid());
			pc2.setType("2");
			pc2.setIscollect("1");
			pc2.setPpid(ppid);
			baseBeanService.save(pc2);
		} else {
			if (pc.getIscollect().equals("0")) {
				pc.setIscollect("1");
			} else if (pc.getIscollect().equals("1")) {
				pc.setIscollect("0");
			}
			baseBeanService.update(pc);
		}
		return "success";
	}

	// 评论页面
	public String platformNewComment() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		Map<String, Object> map = new HashMap<String, Object>();
		if (cus == null) {
			HttpServletRequest request = ServletActionContext.getRequest();
			String url = request.getHeader("Referer");

			session.setAttribute("url", url);
			map.put("login", "login");
			JSONObject json = JSONObject.fromObject(map);
			result = json.toString();
			return "success";
		}
		ProductComment pc = (ProductComment) baseBeanService.getBeanByHqlAndParams(
				"from ProductComment where pcPID=? and type=? and staffId=?",
				new Object[] { pcid, "1", cus.getStaffid() });
		String sql = "select s.headimage,s.staffname,p.commentdate,p.content,p.praise from dt_hr_Staff s,dt_ProductComment p where s.staffid=p.staffid and p.pcid=? and p.ppid=? and p.type='0'";
		object1 = baseBeanService.getObjectBySqlAndParams(sql, new Object[] { pcid, ppid });
		if (type.equals("web") || type.equals("time")) {
			String st = null;
			if(miniSystemJudge.equals("00")){
				st = "公司简介";
			}else if(miniSystemJudge.equals("01")){
				st = "公司文化";
			}else if(miniSystemJudge.equals("02")){
				st = "公司新闻";
			}else if(miniSystemJudge.equals("03")){
				st = "会员分享";
			}else if(miniSystemJudge.equals("04")){
				st = "公司论坛";
			}
			String sql1 = "select g.url,p.image,p.type,p.goodsname from dt_ProductPackaging p join dtGoodFunction g on p.goodsID=g.goodsid and p.ppid=? where p.type=? and p.delStatus='00' and  p.goodsID=? and g.orders=?";
			object2 = baseBeanService.getObjectBySqlAndParams(sql1, new Object[] { ppid, st, goodsid, "1" });
		} else {
			String sql1 = "select g.url,p.image,p.type,p.goodsname from dt_ProductPackaging p join dtGoodFunction g on p.goodsID=g.goodsid and p.parentid=? where p.type=? and p.delStatus='00' and  p.goodsID=? and g.orders=?";
			object2 = baseBeanService.getObjectBySqlAndParams(sql1, new Object[] { ppid, "新闻", goodsid, "1" });
		}
		if (object2 != null) {
			Object[] oo = (Object[]) object2;
			oo[0] = getContentFromFile(oo[0] == null ? "" : oo[0].toString());
			if (pc != null) {
				oo[2] = pc.getIspraise();
			}
			object2 = (Object) oo;
		}
		return "platformnewscomment";
	}

	// 公告页面
	public String platformNotice() {
		return "Platformnotice";
	}

	// 公告详情
	@SuppressWarnings("unchecked")
	public String noticeDetail() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		if (cus != null) {
			String hql = "from ProductComment where goodsId=? and staffid=? and type=?";
			ProductComment pc = (ProductComment) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { goodsid, cus.getStaffid(), "3" });
			if (pc == null) {
				ProductComment pc2 = new ProductComment();
				pc2.setPcID(serverService.getServerID("pcid"));
				pc2.setGoodsId(goodsid);
				pc2.setStaffId(cus.getStaffid());
				pc2.setType("3");
				pc2.setIsread("1");
				pc2.setPpid(ppid);
				baseBeanService.save(pc2);
			}
		}
		StringBuilder summarysql = new StringBuilder();
		summarysql.append("select p.goodsName,p.PackagingDate,g.url");
		summarysql.append(" from dtGoodFunction g,dt_ProductPackaging p");
		summarysql.append(" where g.goodsid=p.goodsid and p.goodsid=? and p.parentid=? and p.delStatus='00'");
		String sql2 = "select p.goodsName,p.PackagingDate from dt_ProductPackaging p where p.type=? and p.delStatus='00' and p.goodsID=? and p.parentid=? ";

		list = baseBeanService.getListBeanBySqlAndParams(summarysql.toString(), new Object[] { goodsid, ppid });
		object1 = baseBeanService.getObjectBySqlAndParams(sql2, new Object[] { "公告", goodsid, ppid });

		maplist1 = new HashMap<Integer, String[]>();
		// 获取每个功能信息的具体内容
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				Object obj = (Object) list.get(i);
				Object[] oo = (Object[]) obj;
				maplist1.put(i,
						new String[] { oo[0] == null ? "" : oo[0].toString(), oo[1] == null ? "" : oo[1].toString(),
								getContentFromFile(oo[2] == null ? "" : oo[2].toString()) });
			}
		}

		return "noticeDetail";
	}

	// 公告列表
	public String AjaxNotice() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);

		StringBuilder noticesql = new StringBuilder();
		noticesql.append("select pp.goodsName,pp.PackagingDate,pp.image,pp.goodsID,g.url");
		noticesql.append(" from dt_ProductPackaging pp,dtGoodFunction g where pp.parentId=? and");
		noticesql.append(
				" pp.goodsID=g.goodsid and pp.type=? and g.orders='1' and pp.delStatus='00' order by pp.PackagingDate desc");
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 15,
				noticesql.toString(), "select count(*) from (" + noticesql + ")", new Object[] { ppid, "公告" });
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取每个功能信息的具体内容
		if (pageForm != null) {
			for (int i = 0; i < pageForm.getList().size(); i++) {
				Object obj = (Object) pageForm.getList().get(i);
				Object[] oo = (Object[]) obj;
				if (cus != null) {
					ProductComment pc = (ProductComment) baseBeanService.getBeanByHqlAndParams(
							"from ProductComment where goodsId=? and type=? and staffId=?",
							new Object[] { oo[3] == null ? "" : oo[3].toString(), "3", cus.getStaffid() });
					if (pc != null) {
						list.add(new String[] { oo[0].toString(), oo[1].toString(), oo[2].toString(), oo[3].toString(),
								getContentFromFile(oo[4] == null ? "" : oo[4].toString()), pc.getIsread() });
					} else {
						list.add(new String[] { oo[0].toString(), oo[1].toString(), oo[2].toString(), oo[3].toString(),
								getContentFromFile(oo[4] == null ? "" : oo[4].toString()) });
					}
				} else {
					list.add(new String[] { oo[0].toString(), oo[1].toString(), oo[2].toString(), oo[3].toString(),
							getContentFromFile(oo[4] == null ? "" : oo[4].toString()) });
				}
			}
			map.put("pagecount", pageForm.getPageCount());
			map.put("pagenumber", pageForm.getPageNumber());
		}
		map.put("list", list);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	// 加入平台页面
	public String platformAdd() {
		return "platformAdd";
	}

	// 进入公司页面
	public String platformCompany() {
		return "platformCompany";
	}

	// 入驻公司展示
	public String AjaxCompany() {
		StringBuilder sql = new StringBuilder();
		sql.append("select cc.companyName,cc.logopath,cc.ccompanyID");
		sql.append(" from T_ESHOP_CUSCOM t,dtContactCompany cc,DT_ccom_com ccom");
		sql.append(" where t.companyId=ccom.compnay_id and");
		sql.append(" ccom.ccompany_Id=cc.ccompanyID and t.ppid=?");

		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(null != pageForm ? pageForm.getPageSize() : 6), sql.toString(), "select count(*) from (" + sql + ")",
				new Object[] { ppid });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	// 行政区域
	@SuppressWarnings("unchecked")
	public String AjaxCity() {
		StringBuilder sql = new StringBuilder();
		sql.append("select pp.goodsName,pp.ppID,pp.image,pp.type,pp.model from");
		sql.append(" dt_ProductPackaging pp,dt_ProductPackaging p");
		sql.append(" where pp.parentId = p.ppid and pp.type=? and pp.parentId=? and pp.delStatus='00'");
		sql.append(" order by pp.ppid");
		if (ppid == null || ppid.equals("")) {
			productDesign = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(
					"from ProductPackaging where parentId is null and type = ?", new Object[] { "联营经济平台" });
			if (productDesign!=null){
				ppid = productDesign.getPpID();
			}
		}
		list = baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[] { "联营经济平台", ppid });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	// 模糊搜索平台
	public String AjaxPlatform() {

		String sql = "select p.goodsName,p.ppid,p.parentName from dt_ProductPackaging p where goodsName like ? and type=? and delStatus=?";
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 2, sql,
				"select count(*) from (" + sql + ")", new Object[] { "%" + content + "%", "联营经济平台", "00" });
		if (content.equals("") || content == null) {
			pageForm = null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	// 省市县
	@SuppressWarnings("unchecked")
	public String getPlatform() {
		HttpServletRequest request = ServletActionContext.getRequest();

		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		if(cus==null){
			if(user!=null)
			{
				cus = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0", new Object[] { user });
				sw.setObject(session, SessionWrap.KEY_CUSTOMER, cus);
			}
			else
			{
				String url = request.getHeader("Referer");
				session.setAttribute("url", url);
				return "login";
			}
		}

		StringBuilder sql = new StringBuilder();
		String halp = " from TEshopCusCom where account=? AND logOff=0 and acquiesce='01'";
		String user = request.getParameter("user");
		String staffid = request.getParameter("staffid");
		TEshopCusCom cussom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(halp, new Object[] { cus.getAccount() });

		sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cussom);

		String staff = null;
		if (cus == null && staffid == null) {
			staff = cussom.getStaffid();
		} else if (staffid != null) {
			staff = staffid;
		} else {
			staff = cus.getStaffid();
		}
		request.setAttribute("staffid", staff);
		// 平台展示Am
		if ("getPlatform".equals(type)) {
			sql.append("from CCode a where a.companyID = ? and a.codePID = ? order by a.codeNumber");
			Object[] params = { "company201009046vxdyzy4wg0000000025", "ccode20160909WNF2VFRSRZ0000005086" };
			list = baseBeanService.getListBeanByHqlAndParams(sql.toString(), params);
			String hql = " from StaffAddress where staffID = ?";

			address = (StaffAddress) baseBeanService.getBeanByHqlAndParams(hql, new Object[] { staff });
			if (list != null && list.size() > 0) {
				String myajax = request.getParameter("ajax");
				request.setAttribute("staffid",cus.getStaffid());
				if(myajax!=null && myajax.equals("myajax")){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("list", list);
					if(address!=null){
						map.put("address",address.toString());//将日期型转化成字符串，否则在json转化是报错
					}
					JSONObject obj = JSONObject.fromObject(map);
					result = obj.toString();
					return "success";
				}
				return "getPlatform";
			}
			// 平台展示B
		} else if ("qurey".equals(type)) {
			sql.append("select p.goodsName,p.ppID,s.re_Price,p.standard,p.standard from dt_ProductPackaging p,");
			sql.append(" DT_PRO_SETUP s where p.type=? and p.ppid=s.ppid order by s.re_Price desc");
			Object[] params2 = { content };
			list = baseBeanService.getListBeanBySqlAndParams(sql.toString(), params2);
			return "PlatformB";
			// 平台展示C
		} else if ("qureyy".equals(type)) {
			//查询代理资格列表
			if ("2".equals(goodsid)) {

				sql.append("select pp.goodsName,pp.ppID,pp.image,pp.type,pp.standard,pp.goodsid from");
				sql.append(" dt_ProductPackaging pp,dt_ProductPackaging p");
				sql.append(" where pp.parentId = p.ppid and pp.standard=? and pp.type='联营经济平台' and pp.delStatus='00' ");
				sql.append(" order by pp.ppid");
				String sousuo=request.getParameter("sousuo");
				if(sousuo ==null || sousuo.equals("")){
					pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 7,
							sql.toString(), "select count(*) from (" + sql + ")", new Object[] { standard });
				}else {
                    StringBuilder sql1 = new StringBuilder();
                    sql1.append("select pp.goodsName,pp.ppID,pp.image,pp.type,pp.standard,pp.goodsid from");
                    sql1.append(" dt_ProductPackaging pp,dt_ProductPackaging p");
                    sql1.append(" where pp.parentId = p.ppid and pp.standard=? and pp.type='联营经济平台' and pp.delStatus='00' ");
                    sql1.append(" and pp.goodsname like ? order by pp.ppid");
                    pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 7,
                            sql1.toString(), "select count(*) from (" + sql1 + ")", new Object[] { standard ,"%"+sousuo+"%"});
                }


				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pageForm", pageForm);
				JSONObject obj = JSONObject.fromObject(map);
				result = obj.toString();
				return "success";
			}

			return "PlatformC1";
			// 平台展示C
		}else if ("qureyd".equals(type)) {

			if ("2".equals(goodsid)) {
				sql.append("select pp.goodsName,pp.ppID,pp.image,pp.type,pp.standard from");
				sql.append(" dt_ProductPackaging pp,dt_ProductPackaging p");
				sql.append(" where pp.parentId = p.ppid and pp.standard=? and pp.type='联营经济平台' and pp.delStatus='00' ");
				sql.append(" order by pp.ppid");

				pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 7,
						sql.toString(), "select count(*) from (" + sql + ")", new Object[] { standard });

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pageForm", pageForm);
				JSONObject obj = JSONObject.fromObject(map);
				result = obj.toString();
				return "success";
			}

			return "PlatformC";

		}
		return "success";
	}

	// 模糊搜索平台zzl
	public String getPlatformB() {
		StringBuilder sql = new StringBuilder();
		sql.append("select pp.goodsName,pp.ppID,pp.image,pp.type,pp.standard,pp.goodsid from");
		sql.append(" dt_ProductPackaging pp,dt_ProductPackaging p");
		sql.append(" where pp.parentId = p.ppid  and pp.goodsname like ? and pp.standard=? and pp.type='联营经济平台' and pp.delStatus= ? ");
		sql.append(" order by pp.ppid");
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 7, sql.toString(),
				"select count(*) from (" + sql + ")", new Object[] { "%" + content + "%", standard, "00" });
		if (content.equals("") || content == null) {
			pageForm = null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	@SuppressWarnings("unchecked")
	public String zhifu() {
		// 分期付款数据,上级的id
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		if (cus == null) {
			return "login";
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select p.goodsName,p.ppID,s.re_Price from dt_ProductPackaging p,");
		sql.append(" DT_PRO_SETUP s where p.parentid=? and p.ppid=s.ppid  ");
		Object[] params2 = { ppid };
		list = baseBeanService.getListBeanBySqlAndParams(sql.toString(), params2);
		request.setAttribute("staffid",cus.getStaffid());
		request.setAttribute("content",content);
		request.setAttribute("platfromid",ppids);
		return "PlatformD";
	}

	// 支付
	public String getzhifu() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		String staffid = request.getParameter("staffid");
		String staff = null;
		if ((cus == null) && (staffid == null)) {
			staff = cus.getStaffid();
		} else if (staffid != null) {
			staff = staffid;
		} else {
			staff = cus.getStaffid();
		}
		String hqls = " from TEshopCusCom s where s.staffid=? and s.acquiesce='01'";
		request.setAttribute("staffid", staff);
		TEshopCusCom cuscom = (TEshopCusCom) this.baseBeanService.getBeanByHqlAndParams(hqls, new Object[] { staff });
		ProductPackaging ppk = (ProductPackaging) this.baseBeanService
				.getBeanByHqlAndParams("from ProductPackaging where ppid=?", new String[] { ppids });
		int typeNumber = 1;
		if ("0".equals(moneys)) {
			typeNumber = 0;
			cus.setStatus("1");
			baseBeanService.update(cus);
		}
		//cuscom.setStaffid("");
		cuscom.setPpid(ppids);

		wfjJifenService.registerCompanyInfo(ppk.getModel(), cuscom, company, cdl, typeNumber);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", "01");
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	/**
	 * 查询公司加入跟个人加入
	 *
	 */
	@SuppressWarnings("unchecked")
	public String getpk() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session, SessionWrap.KEY_CUSTOMER);
		String user = request.getParameter("user");
		if (cus == null) {
			if (user != null) {
				TEshopCustomer customer1 = (TEshopCustomer) baseBeanService
						.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0", new Object[] { user });

				TEshopCusCom cus1 = (TEshopCusCom) baseBeanService
						.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0", new Object[] { user });
				sw.setObject(session, SessionWrap.KEY_CUSTOMER, customer1);
				sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cus1);
			} else {
				return "login";
			}
		}

		// 公司
		String hql = "from TEshopCusCom t where t.state = ? and t.companyId = (select c.comanyId from CcomCom c where c.ccompanyId = ?)";
		TEshopCusCom tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[] { "2", ccompanyId });
		String cusType = "";
		String lowType = "";
		// 查找下级
		if (tcc != null) {
			cusType = tcc.getCusType();// 网站的
			hql = "select t.cusType from T_ESHOP_CUSCOM t connect by nocycle prior t.account = t.Superioragent start with t.account = ? order by t.cusType desc";
			List<Object> list = baseBeanService.getListBeanBySqlAndParams(hql, new Object[] { tcc.getAccount() });
			hql = null;
			lowType = list.get(0).toString();
			if (lowType.equals("0.5")) {

				lowType = "1";
			} else if (lowType.equals("0")) {
				lowType = "0.5";

			} else {
				lowType = Integer.parseInt(lowType) + 1 + "";
			}

		}

		StringBuilder sql = new StringBuilder();
		StringBuilder sqlgs = new StringBuilder();
		sql.append(
				"select ps.ppname,ps.ppid,ps.re_price,p.image,p.model,p.goodsid,p.companyid from DT_PRO_SETUP ps,dt_ProductPackaging p where ps.com_id= ? ");
		sql.append(" and p.ppid=ps.ppid and p.showweixin ='01' and p.type= ? and  ps.fcom_id is null ");
		sql.append("");
		sql.append("");
		sql.append("");
		sqlgs.append(sql);
		sqlgs.append(" and model != ?  order by sorting");
		sql.append(" and ((");
		if (cusType.equals("0")) {
			sql.append("p.model>=?");
			String tempsql = "select t.cusType from T_ESHOP_CUSCOM t where (t.cusType = ? or t.cusType = ?) connect by nocycle prior t.account = t.Superioragent start with t.account = ?";
			List<Object> lists = baseBeanService.getListBeanBySqlAndParams(tempsql,
					new Object[] { "0.5", "1", tcc.getAccount() });

			if (lists.contains("0.5")) {
				sql.append(" and p.model!='0.5'");
			}
			if (lists.contains("1")) {
				sql.append(" and p.model!='1'");
			}
		} else {
			sql.append(" p.model>?");
		}

		if ("MyjiaruCompany".equals(typeNews)) {
			// 查询公司会员列表
			productList = baseBeanService.getListBeanBySqlAndParams(sqlgs.toString(),
					new String[] { "company201009046vxdyzy4wg0000000025", "公司会员", "0" });
			return "MyjiaruCompany";
		} else {
			// 查询个人会员列表
			beans = baseBeanService.getListBeanBySqlAndParams(
					sql.append(") or model = '8') order by sorting").toString(),
					new String[] { "company201009046vxdyzy4wg0000000025", "个人会员", cusType });
			return "MyjiaruPersonal";
		}

	}

	public List<BaseBean> getProductList() {
		return productList;
	}

	public void setProductList(List<BaseBean> productList) {
		this.productList = productList;
	}

	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public ProductPackaging getProductDesign() {
		return productDesign;
	}

	public void setProductDesign(ProductPackaging productDesign) {
		this.productDesign = productDesign;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Map<String, String> getMaplist() {
		return maplist;
	}

	public void setMaplist(Map<String, String> maplist) {
		this.maplist = maplist;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public List<BaseBean> getList() {
		return list;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}

	public Map<Integer, String[]> getMaplist1() {
		return maplist1;
	}

	public void setMaplist1(Map<Integer, String[]> maplist1) {
		this.maplist1 = maplist1;
	}

	public Object getObject1() {
		return object1;
	}

	public void setObject1(Object object1) {
		this.object1 = object1;
	}

	public List<BaseBean> getList1() {
		return list1;
	}

	public void setList1(List<BaseBean> list1) {
		this.list1 = list1;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTypeNews() {
		return typeNews;
	}

	public void setTypeNews(String typeNews) {
		this.typeNews = typeNews;
	}

	public Map<Integer, String[]> getMaplist2() {
		return maplist2;
	}

	public void setMaplist2(Map<Integer, String[]> maplist2) {
		this.maplist2 = maplist2;
	}

	public ProductPackaging getProductDesign1() {
		return productDesign1;
	}

	public void setProductDesign1(ProductPackaging productDesign1) {
		this.productDesign1 = productDesign1;
	}

	public Map<Integer, String[]> getMaplist3() {
		return maplist3;
	}

	public void setMaplist3(Map<Integer, String[]> maplist3) {
		this.maplist3 = maplist3;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPcid() {
		return pcid;
	}

	public void setPcid(String pcid) {
		this.pcid = pcid;
	}

	public Object getObject2() {
		return object2;
	}

	public void setObject2(Object object2) {
		this.object2 = object2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCcompanyId() {
		return ccompanyId;
	}

	public void setCcompanyId(String ccompanyId) {
		this.ccompanyId = ccompanyId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public int getBack() {
		return back;
	}

	public void setBack(int back) {
		this.back = back;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public CDetail getCdl() {
		return cdl;
	}

	public void setCdl(CDetail cdl) {
		this.cdl = cdl;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypemodel() {
		return typemodel;
	}

	public void setTypemodel(String typemodel) {
		this.typemodel = typemodel;
	}

	public StaffAddress getAddress() {
		return address;
	}

	public void setAddress(StaffAddress address) {
		this.address = address;
	}

	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMoneys() {
		return moneys;
	}

	public void setMoneys(String moneys) {
		this.moneys = moneys;
	}

	public String getPpids() {
		return ppids;
	}

	public void setPpids(String ppids) {
		this.ppids = ppids;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getMiniSystemJudge() {
		return miniSystemJudge;
	}

	public void setMiniSystemJudge(String miniSystemJudge) {
		this.miniSystemJudge = miniSystemJudge;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
}
