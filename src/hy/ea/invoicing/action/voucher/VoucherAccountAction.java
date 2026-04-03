package hy.ea.invoicing.action.voucher;

import hy.ea.bo.CAccount;
import hy.ea.bo.invoicing.voucher.BcDay;
import hy.ea.bo.invoicing.voucher.DtInvVoucher;
import hy.ea.bo.invoicing.voucher.DtInvVouchers;
import hy.ea.bo.invoicing.voucher.FiscalPeriod;
import hy.ea.bo.invoicing.voucher.Subs;
import hy.ea.invoicing.service.CcpbsglService;
import hy.ea.invoicing.service.McoService;
import hy.ea.invoicing.service.impl.CcpbsglServiceImpl;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cloopen.rest.sdk.utils.CcopHttpClient;
import com.opensymphony.xwork2.ActionContext;
import com.sun.org.apache.bcel.internal.classfile.Code;



/**
 * 凭证总账,现金流水账，银行流水账
 * 
 * @author
 */
@Controller
@Scope("prototype")
public class VoucherAccountAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private McoService mcoService;
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private String result;
	private DtInvVoucher voucher;
	private DtInvVouchers vouchers;
	private Subs subs;
	private String kemu;//用于传值科目
	private String keid;//用于传值科目id
	private List<BaseBean> sublist;//科目id
	private String stime;//用于报表传值
	private String etime;//用于报表传值
	private String nian1;
	private String nian2;
	private String level;
	
	//现金流水账
	private InputStream excelStream;
	@Resource
	private CcpbsglService cpbsglService;
	private String zz;//用于判断总账以及明细账00作为现金流水账 01作为银行流水账
	private BcDay bcday;//日记账流水表
	private String sdate;//开始时间
	private String edate;//结束时间
	private String organizationname;//部门
	private String scodesub;//开始科目
	private String ecodesub;//终止科目
	private String yhzh;//银行账号
	private String type;//区分现金流水账和银行流水账
	/**
	 * 流水账
	 */
	public String getAccountList(){
		CAccount account=(CAccount)ActionContext.getContext().getSession().get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		//调用存储过程，00代表现金，01代表银行
		Object[] obj={serverService.getServerID("BcDay"),account.getCompanyID(),
			sdate,edate,scodesub,ecodesub,organizationname,zz.equals("00")?"B":"A"};
		Object[] object=new Object[2];
		object=mcoService.ProDays(obj,BcDay.class);
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setAttribute("bcdaylist",object[0]);
		request.setAttribute("detailedlist",object[1]);		
		return "printmoney";
	}
	
	/**
	 * 导出流水账excel
	 */
	public String AccountExcel() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String json1=request.getParameter("json1");
		String json2=request.getParameter("json2");
		excelStream=cpbsglService.syAccountExcel(json1,json2,type);
		return "showexcel";
	}
	/**
	 * 查询页面
	 * @return
	 */
	public String getVaccountList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql ="";
		if(zz.equals("00")){
		hql+= "from CSubjects dt where dt.companyID=? and  dt.subjectsPID in (select subjectsID  from CSubjects   where companyID = ? " +
				" and subjectsPID= '002' and subjectsStatus  != '02') and dt.subjectsStatus !='02' order by subjectsNumbers";
		}else{
			hql+= "from CSubjects s where s.companyID=?  order by subjectsNumbers";	
		}
		if(zz.equals("00")){
		sublist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),account.getCompanyID()});
		}else{
		sublist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID()});	
		}
		if(level!=null&&level.equals("allCompany")){
			return "clist";
		}else{
			return "list";
		}
	}
	/**
	 * 生成报表公共方法
	 * @return
	 */
	public List<Object> getLists() {
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(level==null){
			level=account.getCompanyID();
		}
		String sql="";
		if(zz.equals("00")){
		 sql += "select dt.subjectsnumbers,dt.subjectsname,dt.sdirection,dt.startcash,dt.edirection,dt.endcash,dt.cloan,dt.cforLoan from dt_inv_subs  dt where dt.subjectsnumbers in " +
		 		" (select subjectsnumbers from dtcsubjects  where subjectsPID in( select subjectsid from dtcsubjects   where " ;
		 sql +=	"companyID = ? ";
		 parms.add(level);
		 sql+=" and subjectsPID= '002' and subjectsStatus  != '02') and ";
		 sql+=" companyID = ? ";
		 parms.add(level);
		 sql+=" and subjectsStatus  != '02') and " ;
		 sql+=" dt.companyid=?" ;
		 parms.add(level);
		}else{
		sql+="select fu.tallyDate,fu.companyname,fu.vouchernum,zi.subjectsname,zi.reasonthing,zi.loan,zi.forloan from dt_inv_vouchers zi left join dt_inv_voucher fu  on zi.voucherid=fu.voucherid " ;
		 sql += " where fu.companyid  = ? ";
		 parms.add(level);
		 sql += " and fu.status =?";
		 parms.add("10");
		}
		if (keid != null && ! keid.equals("")) {
			if(zz.equals("00")){
				sql += " and dt.subjectsnumbers = ? ";
			}else{
				sql += " and zi.subjectsID = ? ";
			}
				parms.add( keid );
		}
		if (kemu!= null && ! kemu.equals("")) {
			if(zz.equals("00")){
				sql += " and dt.subjectsname = ? ";	
			}else{
				sql += " and zi.subjectsname = ? ";
			}
			parms.add(kemu);
		}
		if (sdate != null && !"".equals(sdate) ) {
			if(zz.equals("00")){
				sql += " and dt.datess between ? and ?";
			}else{
				sql += " and fu.tallyDate between ? and ?";
			}
			int tt=Utilities.getDayMouth(Integer.parseInt(sdate.substring(0, 4)),Integer.parseInt(sdate.substring(5, 7)));
			stime=(sdate.substring(0, 7)+"-01");
			etime=(sdate.substring(0, 7)+"-"+String.valueOf(tt));
			parms.add(Utilities.getDateFromString((sdate.substring(0, 7)+"-01"), "yyyy-MM-dd"));
			parms.add(Utilities.getDateFromString((sdate.substring(0, 7)+"-"+String.valueOf(tt)), "yyyy-MM-dd"));
		}
		if(zz.equals("00")){
		sql += "order by dt.subjectsnumbers";
		}else{
		sql += "order by  zi.subjectsid";	
		}
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	/**
	 * 生成报表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toPrint(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(level==null){
			level=account.getCompanyID();
		}
		List<Object> list = getLists();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),(pageNumber == 0 ? 100 : pageNumber), sql, "select count(1) "+ sql.substring(sql.indexOf("from")), parms);
		if(zz.equals("00")){
			return "printz";
		}else{
		String hql="from Subs where companyID=? and subjectsNumbers=? and datess=?";
		subs= (Subs) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{level,keid,(Utilities.getDateFromString((sdate.substring(0, 7)+"-"+"01"), "yyyy-MM-dd"))});
		String sql1 = "select sum(zi.loan) loan,sum(zi.forloan) forloan　from dt_inv_vouchers zi left join dt_inv_voucher fu on fu.voucherid=zi.voucherid where fu.companyid  = ? and fu.status =? and fu.tallyDate between ? and ? and  zi.subjectsid=? and zi.subjectsname=?";
		int tt=Utilities.getDayMouth(Integer.parseInt(sdate.substring(0, 4)),Integer.parseInt(sdate.substring(5, 7)));
		List<Object> sumlist = baseBeanService.getListBeanBySqlAndParams(sql1, new Object[]{level,"10"
				,Utilities.getDateFromString((sdate.substring(0, 4)+"-01"+"-01"), "yyyy-MM-dd"),
		Utilities.getDateFromString((sdate.substring(0, 7)+"-"+String.valueOf(tt)), "yyyy-MM-dd"),keid,kemu});
		for(int i=0;i<sumlist.size();i++){
		Object[] obj = (Object[])sumlist.get(i);
			for(int j = 0 ; j < obj.length; j ++){
				if(j==0){
					if((obj[j])!=null){
						 nian1=obj[j].toString();
					}else{
						nian1="0.0";
					}
				 }else if(j==1){
					 if((obj[j])!=null){
						nian2=obj[j].toString(); 
					 }else{
						nian2="0.0"; 
					 }
				}
			}
		}
		return "print";
		}
	}
	
	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}
	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
	}

	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	public ShowExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	
	public CLogBookService getLogBookService() {
		return logBookService;
	}
	public void setLogBookService(CLogBookService logBookService) {
		this.logBookService = logBookService;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getZz() {
		return zz;
	}
	public void setZz(String zz) {
		this.zz = zz;
	}
	public String getKemu() {
		return kemu;
	}
	public void setKemu(String kemu) {
		this.kemu = kemu;
	}
	public List<BaseBean> getSublist() {
		return sublist;
	}
	public void setSublist(List<BaseBean> sublist) {
		this.sublist = sublist;
	}
	public String getKeid() {
		return keid;
	}
	public void setKeid(String keid) {
		this.keid = keid;
	}
	public Subs getSubs() {
		return subs;
	}
	public void setSubs(Subs subs) {
		this.subs = subs;
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
	public String getNian1() {
		return nian1;
	}
	public void setNian1(String nian1) {
		this.nian1 = nian1;
	}
	public String getNian2() {
		return nian2;
	}
	public void setNian2(String nian2) {
		this.nian2 = nian2;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public DtInvVoucher getVoucher() {
		return voucher;
	}
	public void setVoucher(DtInvVoucher voucher) {
		this.voucher = voucher;
	}
	public DtInvVouchers getVouchers() {
		return vouchers;
	}
	public void setVouchers(DtInvVouchers vouchers) {
		this.vouchers = vouchers;
	}
	
	public BcDay getBcday() {
		return bcday;
	}

	public void setBcday(BcDay bcday) {
		this.bcday = bcday;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public String getScodesub() {
		return scodesub;
	}

	public void setScodesub(String scodesub) {
		this.scodesub = scodesub;
	}

	public String getEcodesub() {
		return ecodesub;
	}

	public void setEcodesub(String ecodesub) {
		this.ecodesub = ecodesub;
	}
	public String getYhzh() {
		return yhzh;
	}
	public void setYhzh(String yhzh) {
		this.yhzh = yhzh;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}