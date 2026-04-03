<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:useBean id="chargeInput" scope="request" class="com.batch.chinapay.bean.BatchPayBean" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>Response.jsp</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  </head>
  
  <body><br><br>
  <%
	String  Plain = chargeInput.getPlain();
	StringBuffer sTmp = new StringBuffer();
	for(int i=0;i<Plain.length();i++){
	//如果遇到'\n'转换为<br>
	if(Plain.charAt(i)=='\n'){
	sTmp = sTmp.append("<br>");
	//如果遇到' '转换为 
	}
	else if (Plain.charAt(i)==' '){
	sTmp = sTmp.append(" ");
	//如果是普通字符，则添加到临时字符串 
	}
	else{ 
	sTmp = sTmp.append(Plain.substring(i,i+1)); 
	}
	} 
	//返回结果
	Plain=sTmp.toString(); 
%>	
 	   <center><strong><font size="3"> 返回报文验签</font></strong></center><br>
  <table  border="1" cellspacing="0" cellpadding="0" width="950"  bordercolor="#2B91D5" align="center">
	   <tr>
	     <td colspan="2"><center><font color="blue"> 上传成功返回信息 </font></center></td>
	  </tr>
	  <tr>
		  <td>交易应答码:</td>
		  <td><%= chargeInput.getResponseCode() %></td>
	  </tr>   
	  <tr>
		  <td>交易信息：</td>
		  <td><%= chargeInput.getMessage() %></td>
	   </tr>
	  <tr>
		  <td>验签明文：</td>
		  <td width="800" style="word-break:break-all"><%= Plain %></td>
	  </tr>
	  <tr>
		  <td>系统返回报文：</td>
		  <td width="800" style="word-break:break-all"><%= chargeInput.getData() %></td>
	  </tr>
	  <tr>
	     <td colspan="2"><center><font color="red"> 验签数据正确！</font></center></td>
	  </tr>
  </table>
	   <br>
       <center>
  	<a href="./index.jsp">返回</a></center><br><br><br>
  	<center>
 <p><font color="red">
备注： 调用ChinaPay提供的 chinapay.util.DigestMD5 类中的 MD5Verify 方法验签
</font></p>	</center>
  </body>
</html>
