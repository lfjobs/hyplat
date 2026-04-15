package hy.ea.finance.action.BenDis;

import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.BenDis.PhoneBill;
import hy.ea.finance.service.transferService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.tiantai.telrec.tool.JsonDateValueProcessor;

/**
 * 手机页面订单管理
 * @author lf
 *
 */
public class ghs_phoneBillAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private transferService trService;
	
	private int pageNumber;
	private PageForm pageForm;
	private String result;
		
	/*
	 * http://localhost:8080/hyplat/ea/phonebill/ea_getcomporder.jspa?staid=cstaff20151104IF8I49HSDR0000000001
	 * 查询订单
	 */
	
	public String getcomporder() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pl=request.getParameter("pl");
		String staid=request.getParameter("staid");
		String ljly=request.getParameter("ljly");
		request.setAttribute("pl", pl);
		request.setAttribute("staid", staid);
		request.setAttribute("ljly", ljly);
		pageForm=getList(staid,pl);
		return "list";
	}
	
	public String getAjax(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String pl=request.getParameter("pl");
		String staid=request.getParameter("staid");
		request.setAttribute("pl", pl);
		request.setAttribute("staid", staid);
		pageForm=getList(staid,pl);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonDateValueProcessor("yyyy-MM-dd"));
		JSONObject jsonArray = JSONObject.fromObject(map, jsonConfig);
		result = jsonArray.toString();
		return "success";
	}
	
	/**
	 * 单据列表
	 * @param staid 公司id
	 * @param pl 单据跟踪状态
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageForm getList(String staid,String pl) {
		DetachedCriteria dc = DetachedCriteria.forClass(PhoneBill.class);
		dc.add(Restrictions.eq("companyid", staid));
		if(pl!=null&&!pl.equals("")){
			dc.add(Restrictions.eq("fkStatus", pl));
		}
		dc.addOrder(Order.desc("cashierdate"));
		try {
			pageForm =baseBeanService.getPageFormByDC(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					pageNumber == 0 ? 7 : pageNumber, dc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuffer goodhql=new StringBuffer();
		goodhql.append("select g.goodsbillsid,g.goodsbillskey," );
		goodhql.append("g.quantity,g.price,g.goodsnum,g.goodsname,p.image,g.ppid,c.ccompanyid,c.ccompanyname,g.standard");
		goodhql.append(" from dtgoodsbills g,dt_productpackaging p,dtcashierbills c");
		goodhql.append(" where g.ppid=p.ppid and g.cashierbillsid=c.cashierbillsid and g.cashierbillsid=?");
		//促销中主产品信息ljc
		StringBuffer ptsql=new StringBuffer();
		ptsql.append("select DISTINCT g.goodsbillsid,g.goodsbillskey,g.quantity,g.price,g.goodsnum,");
		ptsql.append(" g.goodsname,p.image,g.ppid,g.companyid,g.cashierbillsid,g.standard,pa.cashierbillsid as ptcashid,c.fkStatus");
        ptsql.append(" from dtgoodsbills g, dt_productpackaging p, dtcashierbills c,dt_promotionassociation pa");
        ptsql.append(" where g.ppid = p.ppid");
        ptsql.append(" and c.cashierbillsid = g.cashierbillsid");
        ptsql.append(" and c.cashierbillsid = pa.ptcashierbillsid");
        ptsql.append(" and pa.cashierbillsid =?");
        
		if(pageForm!=null){
			for(int i=0;i<pageForm.getList().size();i++){
				PhoneBill pb=(PhoneBill)pageForm.getList().get(i);
				/***0:id 1:key 2:数量  3:单价  4:产品编号  5:产品名称  6:产品图片  7:产品id 8:供应商id***/	
				List<Object[]> goodlist=baseBeanService.getListBeanBySqlAndParams(goodhql.toString(), new Object[]{pb.getCashierBillsID()});
				List<Object[]> ptlist=baseBeanService.getListBeanBySqlAndParams(ptsql.toString(), new Object[]{pb.getCashierBillsID()});
				pb.setGoodsList(goodlist);
				pb.setPtgoodsList(ptlist);//ljc
				pageForm.getList().remove(i);
				pageForm.getList().add(i, pb);
			}
		}
		return pageForm;
	}
	/**
	 * 单据详情
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCashBill(){
		HttpServletRequest request = ServletActionContext.getRequest();
		//String sccid = request.getParameter("sccid");
		String pl=request.getParameter("pl");
		String staid=request.getParameter("staid");
		request.setAttribute("pl", pl);
		request.setAttribute("staid", staid);
		StringBuffer goodhql=new StringBuffer();
		PhoneBill pb=(PhoneBill)baseBeanService.getBeanByHqlAndParams("from PhoneBill where cashierBillsID=?", new Object[]{staid});
		/***0:id 1:key 2:数量  3:单价  4:产品编号  5:产品名称  6:产品图片  7:产品id 8:供应商id 9:供货商名称  10:物品id***/
		goodhql.append("select g.goodsbillsid,g.goodsbillskey,"); 
		goodhql.append("g.quantity,g.price,g.goodsnum,g.goodsname,p.image,g.ppid,c.ccompanyid,c.ccompanyname,g.goodsID,p.companyId,g.standard");
		goodhql.append(" from dtgoodsbills g,dt_productpackaging p,dtcashierbills c");
		goodhql.append(" where g.ppid=p.ppid and g.cashierbillsid=c.cashierbillsid and g.cashierbillsid=?");
		List<Object[]> goodlist=baseBeanService.getListBeanBySqlAndParams(goodhql.toString(), new Object[]{pb.getCashierBillsID()});
		pb.setGoodsList(goodlist);
		request.setAttribute("pb", pb);
		goodhql.delete(0, goodhql.length());
		//促销主产品
		goodhql.append("select DISTINCT g.goodsbillsid,g.goodsbillskey,g.quantity,g.price,g.goodsnum,");
		goodhql.append(" g.goodsname,p.image,g.ppid,g.goodsid,g.companyid,g.cashierbillsid,g.standard,pa.cashierbillsid as ptcashid,c.fkStatus");
		goodhql.append(" from dtgoodsbills g, dt_productpackaging p, dtcashierbills c,dt_promotionassociation pa");
		goodhql.append(" where g.ppid = p.ppid");
		goodhql.append(" and c.cashierbillsid = g.cashierbillsid");
		goodhql.append(" and c.cashierbillsid = pa.ptcashierbillsid");
		goodhql.append(" and pa.cashierbillsid =?");
        List<Object[]> list=baseBeanService.getListBeanBySqlAndParams(goodhql.toString(), new Object[]{pb.getCashierBillsID()});
    	pb.setPtgoodsList(list);
    	request.setAttribute("pt", pb);
    	
        goodhql.delete(0, goodhql.length());
		goodhql.append(" select pp.ppid,pp.companyid,pp.goodsname,pp.quantity,pp.price,pp.image,pp.stocksize,pp.monthsales,");
		goodhql.append("pp.standard,pp.shangjiastatus,pp.logo,pp.photo,pp.tradecode,pp.tradename,pp.tradeid,pp.brand,pp.goodsID");
		goodhql.append(" from dt_productpackaging pp where pp.goodsid in  ");
		goodhql.append("(select d.goodsid from dt_inv_invt d where  d.warehouse in (select d.depotid from dtDepotManage d where d.companyid=? and d.depotname = '销售库')) and ");
		goodhql.append("pp.yjstatus='01' and pp.showweixin='01' and pp.companyid= ? and rownum<7");

		List<BaseBean> cplist=baseBeanService.getListBeanBySqlAndParams(goodhql.toString(), new Object[]{pb.getCompanyid(),pb.getCompanyid()});
		request.setAttribute("cplist", cplist);
		
		CashierBills cash = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where cashierBillsID = ?", new Object[]{pb.getCashierBillsID()});
		request.setAttribute("cash", cash);
		return "edit";
	}


	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
