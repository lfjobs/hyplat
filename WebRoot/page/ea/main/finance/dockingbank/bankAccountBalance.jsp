<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.tiantai.nwa.tbank.bo.BankAccount" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>银行账户余额</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/validate.css"  />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
	<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>        
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>        
	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
	<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>           
		
	<script src="<%=basePath%>js/ea/finance/bankAccountBalance.js"></script>

	<script type="text/javascript">
	var basePath = "<%=basePath%>";		 
	</script>
</head>
<%
	List accountList  = (List)request.getAttribute("accountList");
	Iterator iter = accountList.iterator();
	String accountName = (String)request.getAttribute("accountName");
%>
<body style="background-image:url(<%=basePath%>/images/bg01.jpg)">
<form  name="SearchForm" method="post" id="SearchForm">
<input type="submit" name="submit" style="display:none"/>
<input type="hidden" name="bankAccount.account" id="account" value="" />
<input type="hidden" name="bankAccount.banksx" id="banksx"  value=""/>
<input type="hidden" name="bankAccount.provcode" id="provcode"  value=""/>
<input type="hidden" name="bankAccount.currency" id="currency" value="" />
<input type="hidden" name="innerTransCode" id="innerTransCode" value="0001" />
<input type="hidden" name="accountName" id="accountName" value="<%=accountName%>" />

<div class="main" >
<table width="98%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#D1DDE9" style="margin-top:8px" >
		<tr bgcolor="#E7E7E7" >
            <td height="24" width="20%" align="left" bgcolor="#d8e6f4">&nbsp;<span class="txt">银行余额查询</span>&nbsp;</td>
            <td width="60%" height="24" bgcolor="#d8e6f4">&nbsp;</td>
			<td width="20%"  height="24" align="right" bgcolor="#d8e6f4">
           </td>
  		</tr>
</table>
		 <table id="cataffSearchTable" width="98%" height="180" align="center" style="border:#D1DDE9 1px solid;">
                    
                    <tr height="40">
                        <td width="35%"  align="right" class="txt02">选择银行账号：
                        </td>
                        <td width="40%">                            			
							<select name="accountlist" id="accountlist">
								<% while (iter.hasNext()){
									BankAccount bankAccount = (BankAccount)iter.next();
								%>
                            		<option value="<%=bankAccount.getPkey()%>"><%=bankAccount.getAccount()%>|<%=bankAccount.getBanksx()%>|<%=bankAccount.getProvcode()%>|<%=bankAccount.getCurrency()%></option>
								<%}%>
							</select>
                        </td>
                    </tr>
                     <tr height="40" align="center">
                        <td  colspan="2"  align="center">
                            <input type="button" class="input-button"  name="tosearch" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                        </td>
                    </tr>
                </table>
</div>
</form>

	<div class="contentbannb jqmWindow " style="top: 10%;width: 500px;right: 30%" id="jqModel">            
                <div class="drag">
                    详细信息
                    <div class="close">
                    </div>
                </div>
  				<table cellpadding="0px" cellspacing="0px" name="stafftable" id="stafftable" width="418" style="margin-left: 70px;" height="103"> 
				    <tr>
				    	<td width="104" height="37" align="right">账户性质：</td>
				    	<td width="104" id="accountBalanceBean-accType"></td>
				    	<td width="100" height="37" align="right">账户状态：</td>
				    	<td width="108" id="accountBalanceBean-accSts">    </td>
				    </tr>
				    <tr>
				    	<td width="104" height="37" align="right">人工冻结金额：</td>
				    	<td id="accountBalanceBean-frzAmt">	</td>
				    	<td width="100" height="37" align="right">自动冻结金额：</td>
				    	<td id="accountBalanceBean-frzBal">	</td>
				    </tr>
				    <tr>
				      <td height="37" align="right">限制可用额：</td>
				      <td id="accountBalanceBean-valUDLmt"></td>
				      <td height="37" align="right">当月可用额：</td>
				      <td id="accountBalanceBean-valMonthLmt"></td>
			      </tr>
				    <tr>
				      <td height="37" align="right">当日可用额：</td>
				      <td id="accountBalanceBean-valDayLmt"></td>
				      <td height="37" align="right">上日可用余额：</td>
				      <td id="accountBalanceBean-lastAvailBal"></td>
			      </tr>
				    <tr>
				      <td height="37" align="right">上日余额：</td>
				      <td id="accountBalanceBean-lastBal"></td>
				      <td height="37" align="right">可用余额：</td>
				      <td id="accountBalanceBean-availBal"></td>
			      </tr>
				    <tr>
				      <td height="37" align="right">余额：</td>
				      <td height="37" colspan="3" id="accountBalanceBean-bal"></td>
			      </tr>
				    <tr>
			    	  <td colspan="4" align="center" >				    		
						    <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;" value="关闭" />
							<input type="button"  class="input-button JQueryPrinter" style="cursor:pointer;width:80px;" value="打印" />
					  </td>
				    </tr>
				  </table>		    
			
	</div>
</body>
</html>