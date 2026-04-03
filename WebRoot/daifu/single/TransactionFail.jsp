<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>TransactionFail.jsp</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body><br><br>
 	   <center><strong><font size="3"> Chinapay控台返回信息：</font></strong></center><br>
  <table  border="1" cellspacing="0" cellpadding="0" width="350"  bordercolor="#2B91D5" align="center">
	   <tr>
	     <td colspan="2"><center><font color="blue">交易失败信息 </font></center></td>
	  </tr>
	  <tr>
		  <td width="100"><center>交易应答码:</center></td>
		  <td>${payInput.responseCode }</td>
	  </tr>
	  <tr>
	     <td colspan="2"><center><font color="red"> 验签数据正确！</font></center></td>
	  </tr>
	  </table>
	  <br><br><br>
	  <center><strong><font size="3">交易错误码表</font></strong></center>
	  <table  border="1" cellspacing="0" cellpadding="0" width="350"  bordercolor="#2B91D5" align="center">
	  <tr>
		  <td width="100"><center>交易应答码:</center></td>
		  <td><center>描述</center></td>
	  </tr>
	  <tr>
		  <td><center>0100</center></td>
		  <td>商户提交的字段长度、格式错误</td>
	  </tr>
	  <tr>
		  <td><center>0101</center></td>
		  <td>商户验签错误</td>
	  </tr>
	  <tr>
		  <td><center>0102</center></td>
		  <td>手续费计算出错</td>
	  </tr>
	  <tr>
		  <td><center>0103</center></td>
		  <td>商户备付金帐户金额不足</td>
	  </tr>
	  <tr>
		  <td><center>0104</center></td>
		  <td>操作拒绝</td>
	  </tr>
	  <tr>
		  <td><center>0105</center></td>
		  <td>重复交易</td>
	  </tr>
  
  
  </table>

	   <br>
       <center>
  	<a href="./index.jsp">返回</a></center><br><br><br>
  </body>
</html>
