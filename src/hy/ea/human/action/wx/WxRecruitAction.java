package hy.ea.human.action.wx;

import hy.base.action.BaseAction;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.wx.WXDescribe;
import hy.ea.bo.human.wx.WXRecruit;
import hy.ea.service.CCodeService;
import hy.plat.bo.BaseBean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;


/***
 * 招聘微信
 * @author lwz
 *
 */
@SuppressWarnings("serial")
public class WxRecruitAction extends BaseAction<WXRecruit> {

	private WXRecruit wxRecruit;
	private Map<String, WXDescribe> wxdmap;
	private List<BaseBean> beans;
	private String search;
	private String companyId;
	private String recID;
	@Resource
	private CCodeService ccodeService;

	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch", wxRecruit);
		return findItem();
	}

	public String findItem() {
		try {
			pageForm = baseBeanService.getPageFormByDC(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 10 : pageNumber), getList());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "itemList";
	}

	private DetachedCriteria getList() throws ParseException {
		Map<String, Object> session = ActionContext.getContext().getSession();
		DetachedCriteria dc = DetachedCriteria.forClass(WXRecruit.class);
		dc.add(Restrictions.eq("companyID", this.getCurrentAccount()
				.getCompanyID()));
		dc.addOrder(Order.asc("recState"));

		if (search != null && search.equals("search")) {
			wxRecruit = (WXRecruit) session.get("tablesearch");
			if(wxRecruit.getRecName()!=null && !wxRecruit.getRecName().equals("")){
				dc.add(Restrictions.like("recName",wxRecruit.getRecName().trim(),MatchMode.ANYWHERE));
			}
			if(wxRecruit.getRecAdd()!=null && !wxRecruit.getRecAdd().equals("")){
				dc.add(Restrictions.like("recAdd",wxRecruit.getRecAdd().trim(),MatchMode.ANYWHERE));
			}
			
		}
		return dc;
	}

	public String saveItem() {
		beans = new ArrayList<BaseBean>();
		String[] hqls = null;
		String parameter;
		if(wxRecruit.getWxRecID() == null || "".equals(wxRecruit.getWxRecID())){
			wxRecruit.setWxRecID(serverService.getServerID("wxrecruit"));
			wxRecruit.setCompanyID(this.getCurrentAccount().getCompanyID());
			wxRecruit.setGroupCompanySn(this.getGroupCompanySn());
			wxRecruit.setRecAdate(new Date());
			wxRecruit.setRecAname(this.getCurrentAccount().getAccountEmail());
			
			wxRecruit.setRecState(Long.parseLong("0"));
			wxRecruit.setRecNum(Integer.parseInt(wxRecruit.getRecNumT().trim()));
			parameter = "添加微信招聘";
		}else{
			wxRecruit.setRecUdate(new Date());
			wxRecruit.setRecUname(this.getCurrentAccount().getAccountEmail());
			wxRecruit.setRecState(Long.parseLong("0"));
			wxRecruit.setRecNum(Integer.parseInt(wxRecruit.getRecNumT().trim()));
			parameter = "修改微信招聘";
			hqls = new String[]{"delete from WXDescribe d where d.wxRecID = ? and d.companyID = ?"};
		}
		
		beans.add(wxRecruit);
		if(wxdmap != null){
			for(WXDescribe wxd : wxdmap.values()){
				wxd.setWxDesID(serverService.getServerID("wxdescribe"));
				wxd.setCompanyID(this.getCurrentAccount().getCompanyID());
				wxd.setGroupCompanySn(this.getGroupCompanySn());
				wxd.setWxRecID(wxRecruit.getWxRecID());
				beans.add(wxd);
			}
		}
		CLogBook logBook = logBookService.saveCLogBook(null, parameter, this.getCurrentAccount());
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hqls,  new Object[] { wxRecruit.getWxRecID() ,this.getCurrentAccount().getCompanyID()});

		return toAdd();
	}

	public String delItem() {
		beans = new ArrayList<BaseBean>();
		String hql = "delete from WXRecruit c where c.wxRecID = ? and c.companyID = ?";
		String hqls = "delete from WXDescribe d where d.wxRecID = ? and d.companyID = ?";
		CLogBook logBook = logBookService.saveCLogBook(null, "删除微信招聘", this.getCurrentAccount());
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql , hqls }, new Object[] { wxRecruit.getWxRecID() ,this.getCurrentAccount().getCompanyID()});
		return findItem();
	}

	public String upItem() {
		beans = new ArrayList<BaseBean>();
		String hql = "update WXRecruit c set c.recState = ? where c.wxRecID = ?";
		CLogBook logBook = logBookService.saveCLogBook(null, "停用微信招聘",
				this.getCurrentAccount());
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, new Object[] { Long.parseLong("9"),
						wxRecruit.getWxRecID() });
		return findItem();
	}
	/***
	 * 添加、修改
	 * @return
	 */
	public String toAdd(){
		// 经验
		List<CCode> codeExp = ccodeService.getCCodeListByPID(this.getCurrentAccount()
				.getCompanyID(), "scode20150204ddkndqynah0000000002");
		// 薪水
		List<CCode> codePay = ccodeService.getCCodeListByPID(this.getCurrentAccount()
				.getCompanyID(), "scode20150204ddkndqynah0000000003");
		// 行业
		List<BaseBean> lbb	= baseBeanService.getListBeanByHqlAndParams("from CCode c where c.isLeaf = ? and c.groupSn = ? and c.companyID = ? order by c.codeDesc"
				, new Object[]{"00","hylb",this.getCurrentAccount().getCompanyID()});
		List<CCode> codeInd = new ArrayList<CCode>();
		if(lbb != null){
			for(int i = 0 ; i<lbb.size() ; i++){
				codeInd.add((CCode)lbb.get(i));
			}
		}
		// 学历
		List<CCode> codeEdu = ccodeService.getCCodeListByPID(this.getCurrentAccount()
				.getCompanyID(), "scode20100331mk6yn5b5f60000000008");
		request.setAttribute("codeExp", codeExp);
		request.setAttribute("codePay", codePay);
		request.setAttribute("codeInd", codeInd);
		request.setAttribute("codeEdu", codeEdu);
		
		if(!"".equals(wxRecruit.getWxRecID())){
			wxRecruit = (WXRecruit)baseBeanService.getBeanByHqlAndParams("from WXRecruit r where r.wxRecID = ? and r.companyID = ?", new Object[]{wxRecruit.getWxRecID(),this.getCurrentAccount().getCompanyID()});
			beans = new ArrayList<BaseBean>();
			beans = baseBeanService.getListBeanByHqlAndParams("from WXDescribe d where d.wxRecID = ? and d.companyID = ?", new Object[]{wxRecruit.getWxRecID(),this.getCurrentAccount().getCompanyID()});
			request.setAttribute("beans", beans);
		}
		return "toAdd";
	} 
	/**
	 * 微信招聘信息展示
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPosition(){
		String sql="select re.wx_rec_i_d,re.rec_name from DT_HUMAN_WX_RECRUIT re where re.rec_state='0' and  re.company_i_d=? ";
		beans = new ArrayList<BaseBean>();
		beans=baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{companyId});
		
		return "positions";
	}
	/**
	 * 招聘信息
	 * @return
	 */
	public String getPositionItem(){
		beans = new ArrayList<BaseBean>();
		String hql="from WXRecruit where wxRecID=?";
		wxRecruit = (WXRecruit) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{recID});
		String hql2="from WXDescribe where wxRecID=?";
		beans=baseBeanService.getListBeanByHqlAndParams(hql2, new Object[]{recID});
		return "positionItem";
	}
	public WXRecruit getWxRecruit() {
		return wxRecruit;
	}

	public void setWxRecruit(WXRecruit wxRecruit) {
		this.wxRecruit = wxRecruit;
	}

	public Map<String, WXDescribe> getWxdmap() {
		return wxdmap;
	}

	public void setWxdmap(Map<String, WXDescribe> wxdmap) {
		this.wxdmap = wxdmap;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getRecID() {
		return recID;
	}

	public void setRecID(String recID) {
		this.recID = recID;
	}

}
	
