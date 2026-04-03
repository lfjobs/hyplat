<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>TransactionSuccess.jsp</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body><br><br>
 	   <center><strong><font size="3"> Chinapay控台返回信息：</font></strong></center><br>
  <table  border="1" cellspacing="0" cellpadding="0" width="550"  bordercolor="#2B91D5" align="center">
	   <tr>
	     <td colspan="2"><center><font color="blue"> 交易成功信息 </font></center></td>
	  </tr>
	  <tr>
		  <td>交易应答码:</td>
		  <td width="350">${payInput.responseCode}</td>
	  </tr>
		   
	  <tr>
		  <td>交易状态：</td>
		  <td>${payInput.stat}</td>
	   </tr>
	  
	  <tr>
	     <td colspan="2"><center><font color="blue"> 商户交易信息 </font></center></td>
	  </tr>  
	  <tr>
		  <td>商户号:</td>
		  <td>${payInput.merId}</td>
	  </tr>	
	   <tr>
		  <td>商户日期:</td>
		  <td>${payInput.merDate}</td>
	  </tr>	   
	  <tr>
		  <td>流水号:</td>
		  <td>${payInput.merSeqId}</td>
	   </tr>
	   <tr>
		  <td>Cp接收到交易的日期:</td>
		  <td>${payInput.cpDate}</td>
	  </tr>
	  <tr>
		  <td>Cp流水号:</td>
		  <td>${payInput.cpSeqId}</td>
	  </tr>
	  <tr>
		  <td>金额:</td>
		  <td>${payInput.transAmt}</td>
	  </tr>
	  <tr>
		  <td>收款账号：</td>
		  <td>${payInput.cardNo}</td>
	  </tr> 

	  <tr>
	     <td colspan="2"><center><font color="red"> 验签数据正确！</font></center></td>
	  </tr>
  </table>
	   <br>
       <center>
  	<a href="./index.jsp">返回</a></center><br><br><br>
  </body>
</html>
