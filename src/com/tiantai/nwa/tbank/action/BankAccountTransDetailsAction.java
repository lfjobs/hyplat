package com.tiantai.nwa.tbank.action;

import hy.ea.bo.CAccount;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.nwa.tbank.bean.CallBankRequestBean;
import com.tiantai.nwa.tbank.bean.CallBankReturnBean;
import com.tiantai.nwa.tbank.bo.BankAccount;
import com.tiantai.nwa.tbank.bo.TAbcCashday;
import com.tiantai.nwa.tbank.bo.TAbcCashday2;
import com.tiantai.nwa.tbank.ftp.FTPClient;
import com.tiantai.nwa.tbank.ftp.FTPException;
import com.tiantai.nwa.tbank.service.CallBankClientService;
import com.tiantai.nwa.tbank.service.impl.CallBankClientServiceImpl;
import com.tiantai.nwa.util.CallBankServiceClientUtil;
import com.tiantai.nwa.util.DateUtil;
import com.tiantai.nwa.util.DockingBankInitUtil;

/**
 * 
 * @author mz
 * 
 */
@Controller
@Scope("prototype")
public class BankAccountTransDetailsAction {

	private String innerAction;
	private List<BaseBean> accountList;

	@Resource
	private BaseBeanService baseBeanService;
	private BankAccount bankAccount;
	private CallBankRequestBean requestBean;
	private String innerTransCode;
	
	private String account;
	private String banksx;
	private String currency;
	
	//导入各家银行交易明细的交易日期
	private Properties transDate;

	/**
	 * 
	 * @return
	 */
	public String getAccountTransDetails() {
		CallBankServiceClientUtil.setBaseBeanService(baseBeanService);
		if ("accountList".equals(innerAction)) {
			getAllBankAccountList();
			ServletActionContext.getRequest().setAttribute("accountList", accountList);
			return "transdetails_accountList";

		} else if ("accountTransDetailsQuery".equals(innerAction)) {
			getAccountTransDetailsQuery();
			return null;
		}

		return "transdetails_accountList";

	}

	// 获取账户列表
	private void getAllBankAccountList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		String hql = " from BankAccount where companyid = ? and orgid = ? ";
		Object[] params = {
				((CAccount) (session.get("account"))).getCompanyID(),
				session.get("organizationID") };
		accountList = baseBeanService.getListBeanByHqlAndParams(hql, params);
	}

	// 查询交易明细
	private void getAccountTransDetailsQuery() {
		//自动接收交易请求数据  bankAccount  innerTransCode startDate endDate
		CallBankRequestBean reqBean = CallBankServiceClientUtil.makeCallBankRequestBean(bankAccount, innerTransCode);
		try {
			reqBean.setStartDate(requestBean.getStartDate());
			reqBean.setEndDate(requestBean.getEndDate());
			reqBean = CallBankServiceClientUtil.makeBankParamBean(reqBean);
			CallBankServiceClientUtil.makeRequestDatagramWithBean(reqBean);
			
			CallBankClientService cbcs = new CallBankClientServiceImpl();
			CallBankReturnBean returnBean = cbcs.CallBankClientNIOSocket(reqBean);
			
			//returnBean包含了返回的结果，视不同交易返回的结果报文不同，需要分别处理			
			List<BaseBean> list = new ArrayList<BaseBean>();
			makeAccountTransDetailList(reqBean,returnBean,list,bankAccount.getBanksx());
			
			JSONArray json = JSONArray.fromObject(list);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");			
			response.getWriter().print(json.toString());			
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
	private void makeTAbcCashdayBean(String line,TAbcCashday2 bean)
	{
		if ("".equals(line)) return;
		String[] arr = line.split("\\|");
		bean.setPid("");
		bean.setBanksx("ABC");
		bean.setAccountno(arr[1]);
		bean.setGetdatadate(DateUtil.getDefaultDateTime());
		bean.setProv(arr[0]);
		bean.setAccno(arr[1]);
		bean.setCur(arr[2]);
		bean.setTrdate(arr[3]);
		bean.setTimestab(arr[4]);
		bean.setTrjrn(arr[5]);
		bean.setTrtype(arr[6]);
		bean.setTrbankno(arr[7]);
		bean.setAccname(arr[8]);
		bean.setAmtindex(arr[9]);
		bean.setOppprov(arr[10]);
		bean.setOppaccno(arr[11]);
		bean.setOppcur(arr[12]);
		bean.setOppname(arr[13]);
		bean.setOppbkname(arr[14]);
		bean.setCshindex(arr[15]);
		bean.setTrdate(arr[16]);
		bean.setErrvchno(arr[17]);
		bean.setAmt(arr[18]);
		bean.setBal(arr[19]);
		bean.setPreamt(arr[20]);
		bean.setTotchg(arr[21]);
		bean.setVouchertype(arr[22]);
		bean.setVoucherprov(arr[23]);
		bean.setVoucherbat(arr[24]);
		bean.setVoucherno(arr[25]);
		bean.setCustref(arr[26]);
		bean.setTranscode(arr[27]);
		bean.setTeller(arr[28]);
		bean.setVchno(arr[29]);
		bean.setAbs(arr[30]);
		bean.setPostscript(arr[31]);
		bean.setTrfrom(arr[32]);
		
	}
	
	private void makeTAbcCashdayBean(String line,TAbcCashday bean)
	{
		if ("".equals(line)) return;
		String[] arr = line.split("\\|");
		bean.setPid("");
		bean.setBanksx("ABC");
		bean.setAccountno(arr[1]);
		bean.setGetdatadate(Calendar.getInstance().getTime());
		bean.setProv(arr[0]);
		bean.setAccno(arr[1]);
		bean.setCur(arr[2]);
		bean.setTrdate(DateUtil.convertDateTime(arr[3], DateUtil.Date_Parse_Format_YYYYMMDD));
		bean.setTimestab(arr[4]);
		bean.setTrjrn(arr[5]);
		bean.setTrtype(arr[6]);
		bean.setTrbankno(arr[7]);
		bean.setAccname(arr[8]);
		bean.setAmtindex(arr[9]);
		bean.setOppprov(arr[10]);
		bean.setOppaccno(arr[11]);
		bean.setOppcur(arr[12]);
		bean.setOppname(arr[13]);
		bean.setOppbkname(arr[14]);
		bean.setCshindex(arr[15]);
		bean.setErrdate(DateUtil.convertDateTime(arr[16], DateUtil.Date_Parse_Format_YYYYMMDD));
		bean.setErrvchno(arr[17]);
		bean.setAmt(Double.parseDouble(arr[18]));
		bean.setBal(Double.parseDouble(arr[19]));
		bean.setPreamt(Double.parseDouble(arr[20]));
		bean.setTotchg(Double.parseDouble(arr[21]));
		bean.setVouchertype(arr[22]);
		bean.setVoucherprov(arr[23]);
		bean.setVoucherbat(arr[24]);
		bean.setVoucherno(arr[25]);
		bean.setCustref(arr[26]);
		bean.setTranscode(arr[27]);
		bean.setTeller(arr[28]);
		bean.setVchno(arr[29]);
		bean.setAbs(arr[30]);
		bean.setPostscript(arr[31]);
		bean.setTrfrom(arr[32]);		
	}	
	
	//从返回报文中构造出List<TAbcCashday>
	private void makeAccountTransDetailList(CallBankRequestBean reqBean,
			CallBankReturnBean returnBean, List<BaseBean> list, String banksx)
			throws Exception {
		if ("1".equals(returnBean.getFileFlag())) {// 返回数据被保存在了文件中
			Map<String, String> map = DockingBankInitUtil.getBankClientMap()
					.get(banksx);
			String server = reqBean.getServer();
			String deploy = map.get(server + ".ICT.Deploy");
			String fileName = returnBean.getCmp().getChildTextTrim("BatchFileName");
			String filepath = "";
			if ("local".equals(deploy)) {// 文件从本地目录中获取
				filepath = map.get(server + ".ICT.FileLocation");								
			}else// 文件从远程目录中获取
			{
				getDataFileFromRemote(map,fileName,server);
				filepath = map.get(server + ".ICT.LocalDir");	
				
			}
			if (!filepath.endsWith("\\")) filepath += "\\";
			filepath += fileName;
			BufferedReader in = new BufferedReader(new FileReader(filepath));
			while (true) {
				String line = in.readLine();
				if (null != line) {
					TAbcCashday2 bean = new TAbcCashday2();
					makeTAbcCashdayBean(line, bean);
					list.add(bean);
				} else {
					break;
				}
			}
		}
	}
	
	private Date addDays(Date date,int days)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}
	
	//每天定时执行的对象方法，从银行查询交易明细数据到数据库表中
	public void importAccountTransDetails()
	{
		//导入交易日期是trdate的银行日结数据。注意这里应该考虑多家银行的情况。
		System.out.println("import begin");
		//
		setInnerTransCode("0002");
		CallBankServiceClientUtil.setBaseBeanService(baseBeanService);
		Enumeration<?> enumer =  getTransDate().propertyNames();//spring自动注入properties
		while (enumer.hasMoreElements())
		{
			Object obj = enumer.nextElement();//银行缩写
			getBankAccountListByBanksx(obj.toString());			
			for (Iterator<BaseBean> iterator = accountList.iterator(); iterator.hasNext();) {
				try{
					BankAccount bankaccount = (BankAccount) iterator.next();
					
					String transDate = (String)getTransDate().get(obj);
					if ("default".equals(transDate)) 
					{
						SimpleDateFormat sdf = (SimpleDateFormat)DateFormat.getDateTimeInstance();
						sdf.applyPattern("yyyyMMdd");
						transDate = sdf.format(addDays(Calendar.getInstance().getTime(),-1));//导入前一天的(计划每天凌晨2点开始导入前一天的交易流水)
					}					
					
					List<CallBankRequestBean> _list = CallBankServiceClientUtil.makeBankParamBean(bankaccount);
					if (null==_list || _list.size()==0) return;
					Iterator<CallBankRequestBean> iter = _list.iterator();
					while (iter.hasNext()) {
						CallBankRequestBean reqBean = (CallBankRequestBean) iter.next();
						CallBankServiceClientUtil.makeCallBankRequestBean(reqBean,bankaccount, innerTransCode,transDate,transDate);
						
						CallBankServiceClientUtil.makeRequestDatagramWithBean(reqBean);						
						CallBankClientService cbcs = new CallBankClientServiceImpl();
						CallBankReturnBean returnBean = cbcs.CallBankClientNIOSocket(reqBean);
						//在导入数据之前，应该先做一次删除，以免导入已经存在的重复数据。
						deleteCashDayFromDatabase(reqBean,transDate);
						//导入
						List<BaseBean> list = new ArrayList<BaseBean>();
						getCashDayList(reqBean,returnBean,list,obj.toString());
						importCashDayToDatabase(list);						
					}
					
				}catch(Exception e)
				{
					e.printStackTrace();
				}				
			}			
		}
		System.out.println("import ok");
	}
	//在导入数据之前，应该先做一次删除，以免导入已经存在的重复数据。
	private void deleteCashDayFromDatabase(CallBankRequestBean reqBean,String transDate)
	{
		String[] hql = {" delete from TAbcCashday where banksx=? and accno=? and trdate=? "};
		Object[] params = {reqBean.getEsx(),reqBean.getDbAccNo(),DateUtil.convertDateTime(transDate, DateUtil.Date_Parse_Format_YYYYMMDD)};
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, hql, params);		
	}
	//将银行日结数据写入到数据库中,没20条数据执行一次插入操作
	private void importCashDayToDatabase(List<BaseBean> list)
	{
		
		if (null!=list && list.size()>0){
			List<BaseBean> beans = new ArrayList<BaseBean>();
			int i = 0;
			ListIterator<BaseBean> liter = list.listIterator();
			while (liter.hasNext())
			{
				beans.add((TAbcCashday)liter.next());
				i++;
				if (i==20){
					baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
					beans.clear();
					i = 0;
				}
			}
			if (beans.size()>0)
				baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		}		
	}
	
	//从返回的结果报文中构造出银行日结数组list
	private void getCashDayList(CallBankRequestBean reqBean,CallBankReturnBean returnBean,List<BaseBean> list,String banksx) throws Exception {
		
		if ("1".equals(returnBean.getFileFlag())) {// 返回数据被保存在了文件中
			Map<String, String> map = DockingBankInitUtil.getBankClientMap()
					.get(banksx);
			String server = reqBean.getServer();
			String deploy = map.get(server + ".ICT.Deploy");
			String fileName = returnBean.getCmp().getChildTextTrim("BatchFileName");
			String filepath = "";
			if ("local".equals(deploy)) {// 文件从本地目录中获取
				filepath = map.get(server + ".ICT.FileLocation");								
			}else// 文件从远程目录中获取
			{
				getDataFileFromRemote(map,fileName,server);
				filepath = map.get(server + ".ICT.LocalDir");				
			}
			if (!filepath.endsWith("\\")) filepath += "\\";
			filepath += fileName;
			BufferedReader in = new BufferedReader(new FileReader(filepath));
			while (true) {
				String line = in.readLine();
				if (null != line) {
					TAbcCashday bean = new TAbcCashday();
					makeTAbcCashdayBean(line, bean);
					list.add(bean);
				} else {
					break;
				}
			}
		}
	}
	
	//通过FTP从远程服务器上将文件下载到本地目录
	public void getDataFileFromRemote(Map<String, String> map,String fileName,String server) throws NumberFormatException, IOException, FTPException
	{
		String port = map.get(server + ".ICT.Port");
		String userName = map.get(server + ".ICT.UserName");
		String password = map.get(server + ".ICT.Password");
		String localDir = map.get(server + ".ICT.LocalDir"); 
		if (!localDir.endsWith("\\")) localDir += "\\";
		FTPClient ftpClient = new FTPClient(server,Integer.parseInt(port));
		ftpClient.login(userName, password);
		ftpClient.get(localDir+fileName, fileName);
		ftpClient.quit();		
	}
	
	
	//获取账户列表
	private void getBankAccountListByBanksx(String banksx) {			
		String hql = " from BankAccount where banksx = ? ";
		Object[] params = {banksx};
		accountList = baseBeanService.getListBeanByHqlAndParams(hql, params);
	}

	public String getInnerAction() {
		return innerAction;
	}

	public void setInnerAction(String innerAction) {
		this.innerAction = innerAction;
	}

	public List<BaseBean> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<BaseBean> accountList) {
		this.accountList = accountList;
	}

	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}

	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getInnerTransCode() {
		return innerTransCode;
	}

	public void setInnerTransCode(String innerTransCode) {
		this.innerTransCode = innerTransCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBanksx() {
		return banksx;
	}

	public void setBanksx(String banksx) {
		this.banksx = banksx;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public CallBankRequestBean getRequestBean() {
		return requestBean;
	}

	public void setRequestBean(CallBankRequestBean requestBean) {
		this.requestBean = requestBean;
	}	

	public Properties getTransDate() {
		return transDate;
	}

	public void setTransDate(Properties transDate) {
		this.transDate = transDate;
	}
}
