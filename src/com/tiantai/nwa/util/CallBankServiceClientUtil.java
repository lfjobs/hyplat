package com.tiantai.nwa.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.impl.ServerServiceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jdom.Document;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.nwa.tbank.bean.CallBankRequestBean;
import com.tiantai.nwa.tbank.bean.CallBankReturnBean;
import com.tiantai.nwa.tbank.bo.BankAccount;
import com.tiantai.nwa.tbank.bo.BankLinkParam;

/**
 * 
 * @author zhb
 *
 */

public class CallBankServiceClientUtil {
	private static final Logger logger = LoggerFactory.getLogger(CallBankServiceClientUtil.class);
	
	@Resource
	private static BaseBeanService baseBeanService;
		
	@Resource
	private static ServerServiceImpl serverService = new ServerServiceImpl();
	
	
	/**
	 *  将银行账户的相关信息赋值给请求bean
	 * @param bankAccount
	 * @param innerTransCode
	 * @return
	 */
	public static CallBankRequestBean makeCallBankRequestBean(BankAccount bankAccount,String innerTransCode)
	{
		CallBankRequestBean bean = new CallBankRequestBean();
		bean.setEsx(bankAccount.getBanksx());//银行缩写
		bean.setDbAccNo(bankAccount.getAccount());//借方账号
		bean.setDbProv(bankAccount.getProvcode());//借方省市代码
		bean.setDbCur(bankAccount.getCurrency());//借方货币号
		bean.setInnerTransCode(innerTransCode);//erp内部交易代码		
		return bean;
	}
	
	public static CallBankRequestBean makeCallBankRequestBean(CallBankRequestBean bean,BankAccount bankAccount,String innerTransCode,String strBeginDate,String strEndDate)
	{		
		bean.setEsx(bankAccount.getBanksx());//银行缩写
		bean.setDbAccNo(bankAccount.getAccount());//借方账号
		bean.setDbProv(bankAccount.getProvcode());//借方省市代码
		bean.setDbCur(bankAccount.getCurrency());//借方货币号
		bean.setInnerTransCode(innerTransCode);//erp内部交易代码	
		bean.setStartDate(strBeginDate);
		bean.setEndDate(strEndDate);
		return bean;
	}
	
	
	
	/**
	 * 
	 * 
	 * 将head.xml赋值并与各个子交易的xml拼接
	 * @param bean
	 * @throws Exception
	 */
	public static void makeRequestDatagramWithBean(CallBankRequestBean bean) throws Exception
	{
		try{
		Map<String,Map<String,String>> map = DockingBankInitUtil.getSysMap();		
		
		String path = CallBankServiceClientUtil.class.getClassLoader().getResource("").getPath().replaceAll("%20", " ");
		path += "com/tiantai/nwa/tbank/" + bean.getEsx().toLowerCase() + "/datagram/";		
		XMLReader xmlString = new XMLReader(path + bean.getInnerTransCode() + ".xml");//0001.xml   0002.xml
		
		xmlString.m_RootElement.getChild("CCTransCode").setText(map.get(bean.getEsx()).get(bean.getEsx().toLowerCase()+"."+bean.getInnerTransCode()+".CCTransCode"));
		//xmlString.m_RootElement.getChild("ProductID").setText(map.get(bean.getEsx()).get(bean.getEsx()+"."+bean.getInnerTransCode()+".ProductID"));
		//xmlString.m_RootElement.getChild("ChannelType").setText(map.get(bean.getEsx()).get(bean.getEsx()+"."+bean.getInnerTransCode()+".ChannelType"));
		
		
		xmlString.m_RootElement.getChild("CorpNo").setText(bean.getCorpNo());
		xmlString.m_RootElement.getChild("OpNo").setText(bean.getOpNo());
		xmlString.m_RootElement.getChild("AuthNo").setText("");//留空,ICT负责
		xmlString.m_RootElement.getChild("ReqSeqNo").setText(serverService.getServerID("tr"));	
		xmlString.m_RootElement.getChild("ReqDate").setText(Utilities.getDateString(Calendar.getInstance().getTime(), "yyyyMMdd"));
		xmlString.m_RootElement.getChild("ReqTime").setText(Utilities.getDateString(Calendar.getInstance().getTime(), "HHmmss"));
		xmlString.m_RootElement.getChild("Sign").setText("");//留空,ICT负责
		
		
		makeTransXML(bean,xmlString);
		
        bean.setReqDatagram(xmlString.toString());
        
		}catch(Exception e){
			logger.error("操作异常", e);
		}
	}
	
	
	/**
	 * 
	 * 根据银行缩写和内部交易代码分别生成相应的请求体
	 * @return
	 */
	public static void makeTransXML(CallBankRequestBean bean,XMLReader xmlString){
		
	  if(bean.getEsx().equals("ABC")){
		 switch(Integer.parseInt(bean.getInnerTransCode())){
		 case 0001:
			 xmlString.m_RootElement.getChild("Cmp").getChild("DbAccNo").setText(bean.getDbAccNo());
			 xmlString.m_RootElement.getChild("Cmp").getChild("DbProv").setText(bean.getDbProv());
			 xmlString.m_RootElement.getChild("Cmp").getChild("DbCur").setText(bean.getDbCur());
		   break;
		 case 0002:
			 xmlString.m_RootElement.getChild("Corp").getChild("StartDate").setText(bean.getStartDate());
			 xmlString.m_RootElement.getChild("Corp").getChild("EndDate").setText(bean.getEndDate());		 
			 
			 xmlString.m_RootElement.getChild("Channel").getChild("LastJrnNo").setText("0");			 
			 
			 xmlString.m_RootElement.getChild("Cmp").getChild("DbAccNo").setText(bean.getDbAccNo());
			 xmlString.m_RootElement.getChild("Cmp").getChild("DbProv").setText(bean.getDbProv());
			 xmlString.m_RootElement.getChild("Cmp").getChild("DbCur").setText(bean.getDbCur());			 
			 xmlString.m_RootElement.getChild("Cmp").getChild("StartTime").setText("000000");			 
			 break;	 
		  
		 }
		  
	  }
	  
      if(bean.getEsx().equals("ICBC")){
		  
		  
	  }
		
		
		
	}
	
	

	
	public static CallBankReturnBean makeCallBankReturnBean(String returnXML,CallBankRequestBean reqBean) throws Exception 
	{
		if ("".equals(returnXML)) return null;		
		CallBankReturnBean returnBean = new CallBankReturnBean();
		try {
			//截取前7个字节
			String seven = returnXML.substring(0, 7);
			int data_length = Integer.parseInt(seven.substring(1, 7).trim());	
			byte[] source_byte = returnXML.getBytes("gb2312");
			byte[] target_byte = new byte[data_length];
			for (int i = 0; i < target_byte.length; i++) {
				target_byte[i] = source_byte[i+7];				
			}
			returnXML = new String(target_byte,"gb2312");					
			//returnXML = returnXML.substring(6, 1308);
			
			XMLReader reader = new XMLReader();		
			Document doc;		
			doc = reader.string2Doc(returnXML);
			judgeReturnRight(doc);
			setProperty(doc,returnBean,reqBean);
		} catch (Exception e) {				
			logger.error("操作异常", e);				
			throw e;				
		}		
		
		return returnBean;
	}
	
	private static void judgeReturnRight(Document doc) throws Exception
	{
		String respSource = doc.getRootElement().getChildText("RespSource");
		String respCode = doc.getRootElement().getChildText("RespCode");
		if ("1".equals(respSource) || "9999".equals(respCode)) throw new Exception("发送数据有误或通讯异常！");
		
	}
	
	
	private static void setProperty(Document doc,CallBankReturnBean returnBean,CallBankRequestBean reqBean)
	{
		if(reqBean.getEsx().equals("ABC")){
			returnBean.setcCTransCode(doc.getRootElement().getChildText("CCTransCode"));
			returnBean.setReqSeqNo(doc.getRootElement().getChildText("ReqSeqNo"));
			returnBean.setRespSource(doc.getRootElement().getChildText("RespSource"));			
			returnBean.setRespDate(doc.getRootElement().getChildText("RespDate"));
			returnBean.setRespTime(doc.getRootElement().getChildText("RespTime"));
			returnBean.setRespCode(doc.getRootElement().getChildText("RespCode"));
			returnBean.setRespInfo(doc.getRootElement().getChildText("RespInfo"));
			returnBean.setRxtInfo(doc.getRootElement().getChildText("RxtInfo"));		
			
			returnBean.setRespSeqNo(doc.getRootElement().getChildText("RespSeqNo"));//应答流水号			
			returnBean.setFileFlag(doc.getRootElement().getChildText("FileFlag"));
			
			switch(Integer.parseInt(reqBean.getInnerTransCode())){
			case 0001:
				returnBean.setCme(doc.getRootElement().getChild("Cme"));
				returnBean.setCmp(doc.getRootElement().getChild("Cmp"));
				returnBean.setCorp(doc.getRootElement().getChild("Corp"));
				returnBean.setAcc(doc.getRootElement().getChild("Acc"));
				
				break;
			
			case 0002:
				returnBean.setChannel(doc.getRootElement().getChild("Channel"));
				returnBean.setCmp(doc.getRootElement().getChild("Cmp"));
				returnBean.setCme(doc.getRootElement().getChild("Cme"));
				
				break;
			}
					
			
			
		}else if(reqBean.getEsx().equals("ICBC")){
			
		}else
		{
			
		}
		
	}
	
	
	/**
	 * 
	 * 作用：查询出当前公司当前银行的地址和端口号，同时将企业号和操作员编码赋予到请求bean中并返回
	 * @param bean
	 * @return
	 */
	public static CallBankRequestBean  makeBankParamBean(CallBankRequestBean bean)
	{
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		String hql = "from BankLinkParam where enameSx = ? and enterpriseID = ?";
		BankLinkParam blp = (BankLinkParam) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] {bean.getEsx(), account.getCompanyID() });
		
		bean.setServer(blp.getInterfaceIp());
		bean.setPort(blp.getPort());	
		bean.setCorpNo(blp.getCustomerCode());
		bean.setOpNo(blp.getUseCode());
		return bean;
	}
	
	public static List<CallBankRequestBean>  makeBankParamBean(BankAccount bankaccount)
	{		
		String hql = "from BankLinkParam where enameSx = ? ";
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[] {bankaccount.getBanksx()});
		if (null==list || list.size()==0) return null;
		
		List<CallBankRequestBean> resultList = new ArrayList<CallBankRequestBean>();
		Iterator<BaseBean> iter = list.iterator();
		while(iter.hasNext())
		{
			CallBankRequestBean bean = new CallBankRequestBean();
			BankLinkParam blp = (BankLinkParam)iter.next();
			bean.setServer(blp.getInterfaceIp());
			bean.setPort(blp.getPort());	
			bean.setCorpNo(blp.getCustomerCode());
			bean.setOpNo(blp.getUseCode());
			
			resultList.add(bean);
		}		
		return resultList;
	}
	
	public static BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}

	public static void setBaseBeanService(BaseBeanService baseBeanService) {
		CallBankServiceClientUtil.baseBeanService = baseBeanService;
	}
	
	public static void main(String[] args) throws Exception {
		CallBankRequestBean bean = new CallBankRequestBean();
		bean.setDbAccNo("1q2w3e4r");//借方账号
		bean.setDbProv("10");//借方省市代码
		bean.setDbCur("10");//借方货币号
		bean.setInnerTransCode("0001");//erp内部交易代码
		bean.setEsx("ABC");
		CallBankServiceClientUtil.makeRequestDatagramWithBean(bean);
	}	
}
