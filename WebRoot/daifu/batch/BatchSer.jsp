<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<jsp:useBean id="chargeInput" scope="request" class="com.batch.chinapay.bean.BatchPayBean" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>BatchSer.jsp</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  </head>
  
  <body><br><br>
 	   <center><strong><font size="3">ORA批量交易信息</font></strong></center><br>
 	   <form action="<%=basePath %>ea/jifen/ea_B.jspa" method="post" >
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
  <table  border="1" cellspacing="0" cellpadding="0" width="900"  bordercolor="#2B91D5" align="center">
	   <tr>
	     <td colspan="2"><center><font color="blue">批量代付上传信息</font></center></td>
	  </tr>
	  <tr>
		  <td width="100">文件名:</td>
		  <td><%= chargeInput.getData() %></td>
	  </tr>   
	  <tr>
		  <td>文件内容：</td>
		  <td><%= Plain %></td>
	   </tr>
  </table>  
  <input type=hidden name="fileName" value="<%= chargeInput.getData() %>">
  <input type=hidden name="fileContent" value="<%= chargeInput.getPlain() %>">
  <br><center><input type="submit" name="submitButton" value="提交" class="cmd"/></center>
  </form>
       <center>
  	<a href="./index.jsp">返回</a></center><br>
  	<center>
</center>
<p><font color="red"> 
文件命名规范：MERID_YYYYMMDD_XXXXXX.txt </font></p>
<p>MERID:&nbsp;&nbsp;&nbsp;&nbsp;15位的商户号 <br> 
YYYYMMDD: 商户生成文件的日期，共8位 <br> 
XXXXXX:&nbsp;&nbsp; 6位的批次号 <br> 

文件内容格式规范：文件内容包括文件头和文件体。文件头与文件体由回车换行符分割。 <br> 
文件头：文件的第一行，包含：<font color="red">商户号|批次号|总笔数|总金额 </font><br> 
文件体：一行代表一笔交易记录。在每一行交易记录中，交易所需的各项都用&ldquo;|&rdquo;隔开 <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
<font color="red">商户日期|流水号|收款账号|收款人姓名|开户银行|省份|城市|开户支行名|金额|用途|对公对私标志 </font><br>   
 对公对私标志(对公:01 ,对私:00;不填默认为对私)         
</p>
  </body>
</html>
