package hy.ea.human.action.salary;

import hy.base.action.BaseAction;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.salary.WageCofi;
import hy.ea.bo.human.salary.vo.WageCofiVO;
import hy.ea.service.CCodeService;
import hy.ea.util.Constant;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;

import java.math.BigDecimal;
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
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.ActionContext;


/**
 * 工资构成
 * @author 
 *
 */
@SuppressWarnings("serial")
public class WageCofiAction extends BaseAction<WageCofiVO> {
	@Resource
	private CCodeService ccodeService;
	private WageCofiVO wcofi=this.getModel();
	private String codeID;
	private Map<String, WageCofiVO> wcofimap;
	private String parameter;
	private String search;
	public String toSearch() {
		
		ActionContext.getContext().getSession().put("tablesearch", wcofi);
		return findItem();
	}	
	public String findItem() {
		//单位
		List<CCode> codeList = ccodeService.getCCodeListByPID(this.getCurrentAccount().getCompanyID(), "scode20101216zgkfwy4y8p0000000002");
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1),(pageNumber==0?10:pageNumber),getList());
		ActionContext.getContext().put("wageitemlist", Constant.WAGE_ITEM_LX);//工资分类 
		ActionContext.getContext().put("codeList", codeList);//单位
	
		return "itemList";
	}	
	private DetachedCriteria getList() {
		Map<String,Object> session =ActionContext.getContext().getSession();
		DetachedCriteria dc=DetachedCriteria.forClass(WageCofi.class);
		dc.add(Restrictions.eq("companyId", this.getCurrentAccount().getCompanyID()));
		dc.addOrder(Order.asc("wageCofState"));
		dc.addOrder(Order.asc("wageCofSortSn"));
		if (search != null && search.equals("search")) {
			 wcofi = (WageCofiVO) session.get("tablesearch");
			if(wcofi.getCodeValue()!=null && !wcofi.getCodeValue().equals("")){
				dc.add(Restrictions.like("codeValue",wcofi.getCodeValue().trim(),MatchMode.ANYWHERE));
			}			
			if(wcofi.getCofDate()!=null && !wcofi.getCofDate().equals("")){
				try {
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
					java.util.Date date=sdf.parse(wcofi.getCofDate()+"-1-1");  
					java.util.Date date1=sdf.parse(wcofi.getCofDate()+"-12-31");  
					dc.add(Restrictions.between("wageCofDate", date, date1));
				} catch (Exception e) {
				}
			}
		}
		return dc;
	}
	public String saveItem() throws Exception{
		List<BaseBean> beans = new ArrayList<BaseBean>();
		if(wcofimap!=null){
			WageCofi r=null;
			for(WageCofiVO wcof:wcofimap.values()){
				r=new WageCofi();
				BeanUtils.copyProperties(r, wcof);
				if (null == wcof.getWageCofId() || "".equals(wcof.getWageCofId())) {
					r.setWageCofId(serverService.getServerID("wagcofi"));
					r.setCompanyId(this.getCurrentAccount().getCompanyID());
					r.setGroupCompanySn(this.getGroupCompanySn());
					r.setWcAdate(new Date());	
					r.setWcAname(this.getCurrentAccount().getAccountEmail());
					r.setWageCofState(0);
					if(r.getWcXslb()==1){						
						r.setWcTcxs(Double.parseDouble(wcof.getWcTcxs()));
					}
					r.setWageCofDate(DateUtil.parseDate("yyyy", wcof.getCofDate()));
					if(null != wcof.getWageCofSortSn() && !"".equals(wcof.getWageCofSortSn().trim())){
						r.setWageCofSortSn(Long.parseLong(wcof.getWageCofSortSn().trim()));
					}
					if(null != wcof.getPrice() && !"".equals(wcof.getPrice().trim())){
						r.setPrice(r.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					if(null != wcof.getNums() && !"".equals(wcof.getNums().trim())){
						r.setNums(r.getNums().setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					if(null != wcof.getMoneys() && !"".equals(wcof.getMoneys().trim())){
						r.setMoneys(r.getMoneys().setScale(2, BigDecimal.ROUND_HALF_UP));
					}
					beans.add(r);
				}
				else
				{
					r.setWcAdate(DateUtil.parseDate("yyyy-MM-dd hh:mm:ss", wcof.getAddDate()));
					r.setWageCofDate(DateUtil.parseDate("yyyy",wcof.getCofDate()));
					r.setPrice(r.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
					r.setNums(r.getNums().setScale(2, BigDecimal.ROUND_HALF_UP));
					r.setMoneys(r.getMoneys().setScale(2, BigDecimal.ROUND_HALF_UP));
					r.setWcUname(this.getCurrentAccount().getAccountEmail());
					r.setWcUdate(new Date());
					beans.add(r);
				}
			}
			CLogBook logBook = logBookService.saveCLogBook(null, "添加修改工资基础", this.getCurrentAccount());
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
			}	
		return findItem();
	}	
	public String delItem(){
		String id= request.getParameter("id");
		List<BaseBean> beans = new ArrayList<BaseBean>();
		String hql = "delete from WageCofi c where c.wageCofId = ?";
		CLogBook logBook = logBookService.saveCLogBook(null, "删除工资基础", this.getCurrentAccount());
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, new Object[]{id});
		return findItem();
	}	
	public String upItem(){
		String id= request.getParameter("id");
		List<BaseBean> beans = new ArrayList<BaseBean>();
		String hql = "update WageCofi c set c.wageCofState = ? where c.wageCofId = ?";
		CLogBook logBook = logBookService.saveCLogBook(null, "停用工资基础", this.getCurrentAccount());
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, new Object[]{1,id});
		return findItem();
	}	
	@SuppressWarnings("unchecked")
	public String getXmtypeByCode(){
		//类型
		String xmtype = request.getParameter("xmtype");
		String sql ="select pp.goodsname,pp.money, gm.goodscoding from dt_productpackaging pp" +
				" left join dtgoodsmanage gm" +
				" on gm.goodsid = pp.goodsid where pp.companyid = ? and pp.xmtype like ?" +
				" and (pp.goodsname like ? or gm.goodscoding like ?)";
		List<BaseBean> beans = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{this.getCurrentAccount().getCompanyID(),xmtype+"%","%"+parameter.trim()+"%","%"+parameter.trim()+"%"});
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("xmlist",beans);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj;
		return "success";
	}
	public String getCodeID() {
		return codeID;
	}
	public void setCodeID(String codeID) {
		this.codeID = codeID;
	}
	public Map<String, WageCofiVO> getWcofimap() {
		return wcofimap;
	}
	public void setWcofimap(Map<String, WageCofiVO> wcofimap) {
		this.wcofimap = wcofimap;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
}
