<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    
    <title>添加运单信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/admin_main111.css" />
<script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
			<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />


<script src="<%=basePath%>js/ea/finance/BenDis/addrefund.js"
	type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/popLayer/css/popstyle.css" />
<script src="<%=basePath%>js/popLayer/js/popLayer.js" type="text/javascript"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
<script type="text/javascript">
var id="${id}";
var basePath="<%=basePath%>";
	
</script>
  </head>
  
  <body>
   <div class="jqmWindow "
		style=" background-color:white; right: 20%; top: 10%;width: 600px;height: 450px; margin: 0 auto;border:1px solid gray;" id="addApply">
            <div  style="height: 50px;background-color:#00CCFF;text-align: center;font-size: 25px;line-height: 50px;">
           添 加 运 单 信 息
            </div>
            
		<form name="addApplyForm" id="addApplyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			
			<center>
			<table width="100%" style="height: 300px;" id="SearchTable2" cellspacing="20"
				cellpadding="20">

				<tr>

					<td align="right" style="width:150px;">快递公司：</td>
					<td>
					
					<input style="width:150px;height: 20px;" name="refundSheet.express" id="express"/>
							
				</tr>
				<tr >
					<td align="right" style="width:150px;">运单号：</td>
					<td>
					
					<input style="width:150px;height: 20px;" name="refundSheet.waybillno" id="waybillno" />
							
							</td>
				</tr>
				<tr >
					<td align="right" style="width:150px;">退货地址：</td>
					<td align="left"><input class="put3" id="refundAddress"  style="width:250px;height: 20px;"
						value="${rs.refundAddress}" class="put3"/></td>
				</tr>
				<tr>
				<td align="right" style="width:150px;">退货地址邮编：</td>
				<td><input  id="postcode" value="${rs.postcode}" style="width:100px;height: 20px;"/></td>
				</tr>
				<tr >
					<td align="right" style="width:150px;">收货人：</td>
					<td align="left"><input   id="receiverName"
						value="${rs.receiverName}"  style="width:100px;height: 20px;"/></td>
				</tr>
				<tr>
				<td align="right" style="width:150px;">收件人电话：</td>
				<td><input  id="receiverTel" value="${rs.receiverTel}" style="width:100px;height: 20px;"/></td>
				</tr>
			
				</table>
				</center>
				</form>	
                <div style="text-align: center;height: 100px;line-height: 100px;">
                 <input type="button" class="submitResult"  value="提交申请" style="background-color: #00CCFF; "/>
                 &nbsp;&nbsp;&nbsp;
                  <input type="button" class="submitClose" id="nopass" value="退      出" style="background-color: #00CCFF; "/>
                 </div>
				
			</div>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
  </body>
</html>
