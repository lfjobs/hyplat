package hy.ea.invoicing.action.voucher;

import hy.ea.bo.CAccount;
import hy.ea.bo.invoicing.voucher.DtInvCcbsgl;
import hy.ea.bo.invoicing.voucher.DtInvResult;
import hy.ea.invoicing.service.CcpbsglService;
import hy.ea.invoicing.service.McoService;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class CcpbsglAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CcpbsglService ccpbsglService;
	@Resource
	private McoService mcoService;
	@Resource
	private ServerService serverService;
	
	public InputStream excelStream;
	
	public String toPage(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpServletRequest request=ServletActionContext.getRequest();
		CAccount account = (CAccount) session.get("account");
		//营业收入净值
		String chql="select count(*) from DtInvCcbsgl where comId=? and bsAtion=?";
		int cc=baseBeanService.getConutByByHqlAndParams(chql, new Object[]{account.getCompanyID(),"C"});
		request.setAttribute("cc", cc);
		//本期纯益
		String dhql="select count(*) from DtInvCcbsgl where comId=? and bsAtion=?";
		int dc=baseBeanService.getConutByByHqlAndParams(dhql, new Object[]{account.getCompanyID(),"D"});
		request.setAttribute("dc", dc);
		return"toPage";
	}
	
	/**
	 * 查询资产负债表资产类名称
	 * @param comid
	 * @return
	 */
	private Object[][] getzc(String comid){
		String hql="from DtInvCcbsgl where comId=? and bsAtion=? and tabType=? order by sequence";
		List<BaseBean> clist=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{comid,"A","A"});
		Object[][] titles=new Object[clist.size()][];
		for (int i = 0; i < clist.size(); i++) {
			String [] title =new String[3];
			DtInvCcbsgl ccbsgl=(DtInvCcbsgl)clist.get(i);
			title[0]=ccbsgl.getSequence();
			title[1]=ccbsgl.getTabSCaption();
			title[2]=ccbsgl.getTabSymbol();
			titles[i]=title;
		}
		return titles;
	}
	
	/**
	 * 查询资产负债表负债类名称
	 * @param comid
	 * @return
	 */
	private Object[][] getfz(String comid){
		String hql="from DtInvCcbsgl where comId=? and bsAtion=? and tabType=? order by sequence";
		List<BaseBean> clist=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{comid,"B","A"});
		Object[][] titles=new Object[clist.size()][];
		for (int i = 0; i < clist.size(); i++) {
			String [] title =new String[3];
			DtInvCcbsgl ccbsgl=(DtInvCcbsgl)clist.get(i);
			title[0]=ccbsgl.getSequence();
			title[1]=ccbsgl.getTabSCaption();
			title[2]=ccbsgl.getTabSymbol();
			titles[i]=title;
		}
		return titles;
	}
	/**
	 * 查询损益表名称
	 * @param comid
	 * @return
	 */
	private List<Object[]> getsy(String comid){
		String hql="from DtInvCcbsgl where comId=? and tabType=? order by sequence";
		List<BaseBean> clist=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{comid,"B"});
		List<Object[]> titles=new ArrayList<Object[]>();
		for (int i = 0; i < clist.size(); i++) {
			String [] title =new String[3];
			DtInvCcbsgl ccbsgl=(DtInvCcbsgl)clist.get(i);
			title[0]=ccbsgl.getSequence();
			title[1]=ccbsgl.getTabSCaption();
			title[2]=ccbsgl.getTabSymbol();
			titles.add(title);
		}
		return titles;
	}
	
	/**
	 * 查询资产负债表数据
	 * @param comid
	 * @return
	 */
	private Map<String, Object[]> getzfMoney(String comid,String reid,String ym){
		String hqlVal="from DtInvResult where comId=? and reId=? and tabType=? order by reFm";
		List<BaseBean> clistVal=baseBeanService.getListBeanByHqlAndParams(hqlVal, new Object[]{comid,reid,"A"});
		String hqlncVal="from DtInvResult where comId=? and enddate=? and tabType=? order by reFm";
		String nm=String.valueOf(Integer.parseInt(ym.substring(0,4))-1)+"12";
		List<BaseBean> clistncVal=baseBeanService.getListBeanByHqlAndParams(hqlncVal, new Object[]{comid,nm,"A"});
		Map<String, Object[]> titles = new HashMap<String, Object[]>();
		NumberFormat currency = NumberFormat.getInstance();
		for(int d=0;d<clistVal.size();d++){
			Object [] title =new Object[3];
			DtInvResult dResult=(DtInvResult)clistVal.get(d);
			title[0]=dResult.getReFm();
			title[1]=currency.format(dResult.getReMoney()==null?0:dResult.getReMoney());
			for(int z=0;z<clistncVal.size();z++){
				DtInvResult dResult2=(DtInvResult)clistncVal.get(z);
				if(dResult!=null){
					if(dResult2.getTabSymbol()!=null&&dResult2.getTabSymbol().equals(dResult.getTabSymbol())){
						title[2]=currency.format(dResult2.getReMoney()==null?0:dResult2.getReMoney());
					}
				}
			}
			titles.put(dResult.getTabSymbol(), title);
		}
		return titles;
	}
	
	/**
	 * 查询损益表数据
	 * @param comid
	 * @return
	 */
	private Map<String, Object[]> getsyMoney(String comid,String reid,String ym){
		String hqlVal="from DtInvResult where comId=? and reId=? and tabType=? order by reFm";
		List<BaseBean> clistVal=baseBeanService.getListBeanByHqlAndParams(hqlVal, new Object[]{comid,reid,"A"});
		String hqlncVal="from DtInvResult where comId=? and enddate=? and tabType=? order by reFm";
		String nm=String.valueOf(Integer.parseInt(ym.substring(0,4))-1)+"12";
		List<BaseBean> clistncVal=baseBeanService.getListBeanByHqlAndParams(hqlncVal, new Object[]{comid,nm,"A"});
		Map<String, Object[]> titles = new HashMap<String, Object[]>();
		NumberFormat currency = NumberFormat.getInstance();
		for(int d=0;d<clistVal.size();d++){
			Object [] title =new Object[4];
			DtInvResult dResult=(DtInvResult)clistVal.get(d);
			title[0]=dResult.getReFm();
			title[1]=currency.format(dResult.getReMoney()==null?0:dResult.getReMoney());
			title[2]=currency.format(dResult.getRePet()==null?0:dResult.getRePet());
			for(int z=0;z<clistncVal.size();z++){
				DtInvResult dResult2=(DtInvResult)clistncVal.get(z);
				if(dResult!=null){
					if(dResult2.getTabSymbol()!=null&&dResult2.getTabSymbol().equals(dResult.getTabSymbol())){
						title[3]=currency.format(dResult2.getReMoney()==null?0:dResult2.getReMoney());
					}
				}
			}
			titles.put(dResult.getTabSymbol(), title);
		}
		return titles;
	}
	
	public List<Object[]> fzpage(String reid,String ym){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		Map<String, Object[]> title3=getzfMoney(account.getCompanyID(), reid, ym);
		Object [][] title =getzc(account.getCompanyID());
		Object [][] title2=getfz(account.getCompanyID());
		int j=title.length;
		int z=title2.length;
		int a=0;
		String b="";
		if(z-j>0){
			b="z";
			a=z;
		}else{
			b="j";
			a=j;
		}
		List<Object[]> objlist=new ArrayList<Object[]>();
		for (int i = 0; i < a; i++) {
			Object[] obj=new Object[8];
			if(b.equals("z")){
				if(i+1>j){
					obj[0]="";
					obj[1]="";
					obj[2]="";
					obj[3]="";
				}else{
					Object[] sj=title3.get(title[i][2]);
					if(sj.length>0){
						obj[2]=Double.valueOf( sj[2]==null?"0.00":sj[2].toString());
						obj[3]=Double.valueOf( sj[1]==null?"0.00":sj[1].toString());
					}
					obj[0]=title[i][0];
					obj[1]=title[i][1];
				}
				Object[] sj=title3.get(title2[i][2]);
				if(sj.length>0){
					obj[6]=Double.valueOf( sj[2]==null?"0.00":sj[2].toString());
					obj[7]=Double.valueOf( sj[1]==null?"0.00":sj[1].toString());
				}
				obj[4]=title2[i][0];
				obj[5]=title2[i][1];
			}else{
				if(i+1>z){
					obj[4]="";
					obj[5]="";
					obj[6]="";
					obj[7]="";
				}else{
					Object[] sj=title3.get(title2[i][2]);
					if(sj.length>0){
						obj[6]=Double.valueOf( sj[2]==null?"0.00":sj[2].toString());
						obj[7]=Double.valueOf( sj[1]==null?"0.00":sj[1].toString());
					}
					obj[4]=title2[i][0];
					obj[5]=title2[i][1];
				}
				Object[] sj=title3.get(title[i][2]);
				if(sj.length>0){
					obj[2]=Double.valueOf( sj[2]==null?"0.00":sj[2].toString());
					obj[3]=Double.valueOf( sj[1]==null?"0.00":sj[1].toString());
				}
				obj[0]=title[i][0];
				obj[1]=title[i][1];
			}
			objlist.add(obj);
		}
		return objlist;
	}
	
	public List<Object[]> sypage(String id,String yid){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object[]> title =getsy(account.getCompanyID());
		String hqlVal="from DtInvResult where comId=? and reId=? and tabType=? order by reFm";
		List<BaseBean> clistVal=baseBeanService.getListBeanByHqlAndParams(hqlVal, new Object[]{account.getCompanyID(),id,"B"});
		List<BaseBean> clistncVal=baseBeanService.getListBeanByHqlAndParams(hqlVal, new Object[]{account.getCompanyID(),yid,"B"});
		List<Object[]> titles = new ArrayList<Object[]>();
		NumberFormat currency = NumberFormat.getInstance();
		for(int d=0;d<title.size();d++){
			Object [] t=new Object[6];
			t[0]=title.get(d)[0];
			t[1]=title.get(d)[1];
			t[2]=title.get(d)[2];
			if(clistVal.size()>0){
				for(int z=0;z<clistncVal.size();z++){
					DtInvResult dResult2=(DtInvResult)clistVal.get(z);
					if(dResult2!=null){
						if(dResult2.getTabSymbol()!=null&&dResult2.getTabSymbol().equals(title.get(d)[2])){
							t[3]=currency.format(dResult2.getReMoney()==null?0:dResult2.getReMoney());
							t[4]=currency.format(dResult2.getRePet()==null?0:dResult2.getRePet());
						}
					}
				}
				
			}
			if(clistncVal.size()>0){
				for(int z=0;z<clistncVal.size();z++){
					DtInvResult dResult2=(DtInvResult)clistncVal.get(z);
					if(dResult2!=null){
						if(dResult2.getTabSymbol()!=null&&dResult2.getTabSymbol().equals(title.get(d)[2])){
							t[5]=currency.format(dResult2.getReMoney()==null?0:dResult2.getReMoney());
						}
					}
				}
			}
			titles.add(t);
		}
		return title;
	}
	
	public String gettozf(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setAttribute("cname", account.getCompanyName());
		String bt=request.getParameter("bt")==null?"":request.getParameter("bt").toString();
		String dw=request.getParameter("dw")==null?"":request.getParameter("dw").toString();
		String mx=request.getParameter("mx")==null?"":request.getParameter("mx").toString();
		String st=request.getParameter("st")==null?"":request.getParameter("st").toString();
		String et=request.getParameter("et")==null?"":request.getParameter("et").toString();
		String ym=request.getParameter("ym")==null?"":request.getParameter("ym").toString();
		request.setAttribute("bt", bt);
		if(et!=null&&!et.equals("")){
			request.setAttribute("et", et.substring(0,6));
		}
		if(ym!=null&&!ym.equals("")){
			request.setAttribute("ym", ym);
		}
		if(dw!=null&&dw.equals("Y")){
			request.setAttribute("dw", "千元");
		}else{
			request.setAttribute("dw","元");
		}
		String rdid=serverService.getServerID("re");
		request.setAttribute("re",rdid);
		mcoService.probcflexp(new Object[]{ym,ym,bt,dw,mx,rdid,account.getCompanyID()});
		List<Object[]> title=fzpage(rdid,ym);
		request.setAttribute("objlist",title);
		return "fz";
	}
	
	public String gettosy(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setAttribute("cname", account.getCompanyName());
		String bt=request.getParameter("bt")==null?"":request.getParameter("bt").toString();
		String dw=request.getParameter("dw")==null?"":request.getParameter("dw").toString();
		String mx=request.getParameter("mx")==null?"":request.getParameter("mx").toString();
		String st=request.getParameter("st")==null?"":request.getParameter("st").toString();
		String et=request.getParameter("et")==null?"":request.getParameter("et").toString();
		String ym=request.getParameter("ym")==null?"":request.getParameter("ym").toString();
		request.setAttribute("bt", bt);
		if(et!=null&&!et.equals("")){
			request.setAttribute("et", et.substring(0,6));
		}
		if(ym!=null&&!ym.equals("")){
			request.setAttribute("ym", ym);
		}
		if(dw!=null&&dw.equals("Y")){
			request.setAttribute("dw", "千元");
		}else{
			request.setAttribute("dw","元");
		}
		String sql="SELECT STARTDATE FROM DT_INV_FISPERIOD WHERE companyid = ? AND ? between startdate AND enddate";
		Object ss=baseBeanService.getObjectBySqlAndParams(sql,new Object[]{account.getCompanyID(),st});
		String rdid=serverService.getServerID("re");
		mcoService.probcflexp(new Object[]{st,et,bt,dw,mx,rdid,account.getCompanyID()});
		
		String rdid2=serverService.getServerID("re");
		mcoService.probcflexp(new Object[]{st,et,bt,dw,mx,rdid2,account.getCompanyID()});
		List<Object[]> title=sypage(rdid,rdid2);
		request.setAttribute("objlist",title);
		return "sy";
	}
	
	public String zfshowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest request=ServletActionContext.getRequest();
		String td=request.getParameter("td")==null?"":request.getParameter("td").toString();
		String reid=request.getParameter("re")==null?"":request.getParameter("re").toString();
		String dw=request.getParameter("dw")==null?"":request.getParameter("dw").toString();
		String ym=request.getParameter("ym")==null?"":request.getParameter("ym").toString();
		Object[] objs={dw,td,account.getCompanyName()};
		excelStream = ccpbsglService.zfShowExcel(getzc(account.getCompanyID()),getfz(account.getCompanyID()),getzfMoney(account.getCompanyID(),reid,ym),objs);
		return "showexcel";
	}
	
	public String syshowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest request=ServletActionContext.getRequest();
		String td=request.getParameter("td")==null?"":request.getParameter("td").toString();
		String reid=request.getParameter("re")==null?"":request.getParameter("re").toString();
		String dw=request.getParameter("dw")==null?"":request.getParameter("dw").toString();
		Object[] objs={dw,td,account.getCompanyName()};
		Map<String, Object[]> syval=getsyMoney(account.getCompanyID(),reid,td);
		Object [][] str_a = (Object[][]) getsy(account.getCompanyID()).toArray(new Object[0][0]);
		excelStream = ccpbsglService.syShowExcel(str_a,objs,syval);
		return "showexcel";
	}
	
	public String syzfshowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest request=ServletActionContext.getRequest();
		String td=request.getParameter("td")==null?"":request.getParameter("td").toString();
		String reid=request.getParameter("re")==null?"":request.getParameter("re").toString();
		String dw=request.getParameter("dw")==null?"":request.getParameter("dw").toString();
		Object[] objs={dw,td,account.getCompanyName()};
		Map<String, Object[]> syval=getsyMoney(account.getCompanyID(),reid,td);
		excelStream = ccpbsglService.syzfExcel(getzc(account.getCompanyID()),
				getfz(account.getCompanyID()),
				getsy(account.getCompanyID()).size() <= 0 ? null
						: (Object[][]) getsy(account.getCompanyID()).toArray(),
				getsyMoney(account.getCompanyID(), reid, td),syval, objs);
		return "showexcel";
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
}
