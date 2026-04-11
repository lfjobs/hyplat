package hy.ea.human.action.salary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.base.action.BaseAction;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.DepartmentPost;
import hy.ea.bo.human.salary.WageCofJJ;
import hy.ea.service.CCodeService;
import hy.plat.bo.BaseBean;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class WageCofJJAction extends BaseAction<WageCofJJ> {

	private WageCofJJ wcofjj;
	private Map<String, WageCofJJ> wcofjjmap;
	private List<BaseBean> beans;
	private String parameter;
	private List<CCode> codeList;//单位
	private List<DepartmentPost> dpostList;//部门
	private String search;
	@Resource
	private CCodeService ccodeService;
	private String xmtype; //
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch", wcofjj);
		return findItem();
	}
	
	
	public String findItem() {
		codeList = ccodeService.getCCodeListByPID(this.getCurrentAccount().getCompanyID(), "scode20101216zgkfwy4y8p0000000002");
		beans = baseBeanService.getListBeanByHqlAndParams("from DepartmentPost p where p.companyID = ?",new Object[]{this.getCurrentAccount().getCompanyID()} );
		dpostList = new ArrayList<DepartmentPost>();
		if(null != beans){
			for(int i = 0 ; i < beans.size() ; i ++){
				dpostList.add((DepartmentPost)beans.get(i));
			}
		}
		
		try {
			pageForm = baseBeanService
					.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1),
							(pageNumber==0?10:pageNumber),getList());
		} catch (ParseException e) {
			logger.error("操作异常", e);
		}
		
		return "itemList";
	}
	
	private DetachedCriteria getList() throws ParseException{
		Map<String,Object> session =ActionContext.getContext().getSession();
		DetachedCriteria dc=DetachedCriteria.forClass(WageCofJJ.class);
		dc.add(Restrictions.eq("companyID", this.getCurrentAccount().getCompanyID()));
		dc.addOrder(Order.asc("confJjState"));
		
		if (search != null && search.equals("search")) {
			wcofjj = (WageCofJJ) session.get("tablesearch");
			if(wcofjj.getGoodsName()!=null && !wcofjj.getGoodsName().equals("")){
				dc.add(Restrictions.like("goodsName",wcofjj.getGoodsName().trim(),MatchMode.ANYWHERE).ignoreCase());
			}
			if(wcofjj.getYyyyCofDate()!=null && !wcofjj.getYyyyCofDate().equals("")){
				try {
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
					java.util.Date date=sdf.parse(wcofjj.getYyyyCofDate()+"-1-1");  
					java.util.Date date1=sdf.parse(wcofjj.getYyyyCofDate()+"-12-31");  
					dc.add(Restrictions.between("cofJjDate", date, date1));
				} catch (Exception e) {
					logger.error("操作异常", e);
				}
			}
		}
		return dc;
	}
	
	public String saveItem() {
		beans = new ArrayList<BaseBean>();
		if(wcofjjmap!=null){
			for(WageCofJJ wcof:wcofjjmap.values()){
				if (null == wcof.getCofJjID()|| "".equals(wcof.getCofJjID())) {
					wcof.setCofJjID(serverService.getServerID("wagcofjj"));
					wcof.setCompanyID(this.getCurrentAccount().getCompanyID());
					wcof.setGroupCompanySn(this.getGroupCompanySn());
					wcof.setCjAdate(new Date());
					wcof.setCjAname(this.getCurrentAccount().getAccountEmail());
					wcof.setConfJjState(Long.parseLong("0"));
					wcof.setCjState(Long.parseLong(wcof.getCjStateT()));
					try {
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
						java.util.Date date=sdf.parse(wcof.getYyyyCofDate()+"-1-1");  
						wcof.setCofJjDate(date);
					} catch (ParseException e) {
						logger.error("操作异常", e);
					}
						
					if(null != wcof.getCofSortSnT() && !"".equals(wcof.getCofSortSnT().trim())){
						wcof.setCofSortSn(Integer.parseInt(wcof.getCofSortSnT().trim()));
					}
					
					if(null != wcof.getCjProPriceT() && !"".equals(wcof.getCjProPriceT().trim())){
						//构造以字符串内容为值的BigDecimal类型的变量bd   
						BigDecimal bd=new BigDecimal(wcof.getCjProPriceT().trim());   
						//设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)   
						bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);
						wcof.setCjProPrice(bd);
					}
					if(null != wcof.getCjTcxsT() && !"".equals(wcof.getCjTcxsT().trim())){
						//构造以字符串内容为值的BigDecimal类型的变量bd   
						BigDecimal bd=new BigDecimal(wcof.getCjTcxsT().trim());   
						//设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)   
						bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);
						wcof.setCjTcxs(bd);
					}
					beans.add(wcof);
				}
				else
				{
					wcof.setConfJjState(Long.parseLong("0"));
					wcof.setCofSortSn(Integer.parseInt(wcof.getCofSortSnT()));
					wcof.setCjState(Long.parseLong(wcof.getCjStateT()));
					try {
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
						java.util.Date date=sdf.parse(wcof.getYyyyCofDate()+"-1-1");  
						wcof.setCofJjDate(date);
					} catch (ParseException e) {
						logger.error("操作异常", e);
					}
					BigDecimal bdprice=new BigDecimal(wcof.getCjProPriceT().trim());   
					wcof.setCjProPrice(bdprice.setScale(2, BigDecimal.ROUND_HALF_UP));
					BigDecimal bdnums=new BigDecimal(wcof.getCjTcxsT().trim());   
					wcof.setCjTcxs(bdnums.setScale(2, BigDecimal.ROUND_HALF_UP));
					wcof.setCjUname(this.getCurrentAccount().getAccountEmail());
					wcof.setCjUdate(new Date());
					beans.add(wcof);
				}
			}
			CLogBook logBook = logBookService.saveCLogBook(null, "添加修改计件工资", this.getCurrentAccount());
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
			}
		
		return findItem();
	}
	
	public String delItem(){
		beans = new ArrayList<BaseBean>();
		String hql = "delete from WageCofJJ c where c.cofJjID = ?";
		CLogBook logBook = logBookService.saveCLogBook(null, "删除计件工资", this.getCurrentAccount());
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, new Object[]{wcofjj.getCofJjID()});
		return findItem();
	}

	public String upItem(){
		beans = new ArrayList<BaseBean>();
		String hql = "update WageCofJJ c set c.confJjState = ? where c.cofJjID = ?";
		CLogBook logBook = logBookService.saveCLogBook(null, "停用工资基础", this.getCurrentAccount());
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, new Object[]{ Long.parseLong("1"),wcofjj.getCofJjID()});
		return findItem();
	}
	
	@SuppressWarnings({ "rawtypes"})
	public String getCofjjByI(){
		String sql = "select c.deppostid, d.postName,c.status from dtCos c" +
				" left join dt_hr_deptpost d on c.deppostid = d.deppostid" +
				" where c.companyid = ?" +
				" and c.cosStatus = ? and c.status = ? and c.staffid = ?";
		List l = baseBeanService.getListBeanBySqlAndParams(sql, 
				new Object[]{this.getCurrentAccount().getCompanyID(),"50",
				"01",wcofjj.getCompanyID()});
		Object[] obj = (Object[] )l.get(0);
		
		String hql = "from WageCofJJ j where j.companyID= ? and j.goodsName = ?" +
				" and j.deppostID = ? and j.jjCodeID = ?" +
				" and j.confJjState = ? ";
		beans = baseBeanService.getListBeanByHqlAndParams(hql, 
				new Object[]{this.getCurrentAccount().getCompanyID(),wcofjj.getCjTcxsT(),
				obj[0],wcofjj.getGoodsName(),Long.parseLong("0")});
		WageCofJJ jj = new WageCofJJ();
		if(beans.size() > 0){
			jj = (WageCofJJ)beans.get(0);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("jj",jj);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj;
		return "success";
	}
	
	
	public Map<String, WageCofJJ> getWcofjjmap() {
		return wcofjjmap;
	}

	public void setWcofjjmap(Map<String, WageCofJJ> wcofjjmap) {
		this.wcofjjmap = wcofjjmap;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public List<CCode> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<CCode> codeList) {
		this.codeList = codeList;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public WageCofJJ getWcofjj() {
		return wcofjj;
	}

	public void setWcofjj(WageCofJJ wcofjj) {
		this.wcofjj = wcofjj;
	}

	public List<DepartmentPost> getDpostList() {
		return dpostList;
	}

	public void setDpostList(List<DepartmentPost> dpostList) {
		this.dpostList = dpostList;
	}

	public String getXmtype() {
		return xmtype;
	}

	public void setXmtype(String xmtype) {
		this.xmtype = xmtype;
	}



}
