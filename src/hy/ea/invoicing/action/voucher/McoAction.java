package hy.ea.invoicing.action.voucher;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hy.ea.bo.CAccount;
import hy.ea.bo.invoicing.voucher.DtInvMco;
import hy.ea.bo.invoicing.voucher.FiscalPeriod;
import hy.ea.invoicing.service.McoService;
import hy.ea.util.Utilities;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/**
 * 月结
 * @author lf
 *
 */
@Controller
@Scope("prototype")
public class McoAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private McoService mcoService;
	@Resource
	private ServerService serverService;

	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private String result;
	private String stime;//判断时间
	private String etime;//判断时间
	private DtInvMco dtInvMco;
	private String mco_id;
	private String mco_ym;
	/**
	 * 添加
	 */
	public String save(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		String groupcsn=session.get("groupCompanySn").toString();
		dtInvMco.setGroupcsn(groupcsn);
		dtInvMco.setMcoid(serverService.getServerID("mco"));
		mcoService.proDMCO(groupcsn, dtInvMco.getYearmonth(), dtInvMco.getCompanyid());
		baseBeanService.save(dtInvMco);
		return "success";
	}
	
	/**
	 * 列表查询
	 * @return
	 */
	public String getSerch(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> beams=new ArrayList<Object>();
		String sql="select m.mcokey, cast(m.mcodate as varchar2(14)),cast(m.yearmonth as varchar2(6)),c.companyName,s.staffName,s.staffCode from DT_INV_MCO m" +
				" left join dtCompany c on c.companyID=m.companyid" +
				" left join dt_hr_Staff s on s.staffID=m.mcostaffid where m.companyid=? ";
		beams.add(account.getCompanyID());
		if(search!=null&&!search.equals("")){
			if(stime!=null&&!stime.equals("")&&etime!=null&&!etime.equals("")){
				sql+=" and m.yearmonth>=? and m.yearmonth<=?";
				beams.add(stime);
				beams.add(etime);
			}
		}
		sql+="  order by m.mcodate desc";
		pageForm=baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(*) "
						+ sql.substring(sql.indexOf("from")),beams.toArray());
		return "list";
	}
	
	/**
	 * 删除月结数据
	 * @return
	 */
	public String delGetMco(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		baseBeanService.deleteBeanByKey(DtInvMco.class, mco_id);
		return "success";
	}
	
	/**
	 * 保存前判断年月时间是否有效
	 * @return
	 */
	public String getmy(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hqlf="from FiscalPeriod where companyID=? order by year desc";
		FiscalPeriod fPeriod=(FiscalPeriod)baseBeanService.getBeanByHqlAndParams(hqlf, new Object[]{account.getCompanyID()});
		boolean b=false;
		if(fPeriod!=null){
			String stime=fPeriod.getStartDate();
			String etime=fPeriod.getEndDate();
			if(fPeriod!=null){
				b=Utilities.isPeriodTime(mco_ym, stime, etime, "yyyyMM");
			}
		}
		String hql="select count(*) from DtInvMco where yearmonth=?";
		int count=baseBeanService.getConutByByHqlAndParams(hql, new Object[]{mco_ym});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("c", count);
		map.put("b", b);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public DtInvMco getDtInvMco() {
		return dtInvMco;
	}

	public void setDtInvMco(DtInvMco dtInvMco) {
		this.dtInvMco = dtInvMco;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getMco_id() {
		return mco_id;
	}

	public void setMco_id(String mco_id) {
		this.mco_id = mco_id;
	}

	public String getMco_ym() {
		return mco_ym;
	}

	public void setMco_ym(String mco_ym) {
		this.mco_ym = mco_ym;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
