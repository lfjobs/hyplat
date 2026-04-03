<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %> 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/"; 
	String filepath = request.getSession().getServletContext().getRealPath("/");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>客户咨询处理</title>
<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/admin_mains.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/photoup/CJL.0.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/photoup/ImagePreviewd.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/office_ea/contactcompany/contactcompany_add.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>

<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var retoken = 0;
		var showType ='${showType}'; 
		var select = 1;
		var companyName = '${cusAsk.customerName }';
		var ccompanyID = '${cusAsk.askID }';
	    var str="";
	    var temp = "";</script>
</head>

<body>
	<div class="content" style="width:850px;margin-left:0px;">
		<div class="contentbannb">
			<div class="divtx">&nbsp;客户咨询处理 </div>
		</div>
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="biaoti box1">
			<tr>
				<td height="27" class="txt03">单位基本信息</td>
			</tr>
		</table>
		<div id="box1">
			<form name="box1Form" id="box1Form" method="post" enctype="multipart/form-data" >
				<s:token/>
				<table id="contacttable" width="99%" align="center" cellpadding="0"
					cellspacing="0" class="table">
					<tr class="trheight">
						<td width="12%" align="right">咨询单位：</td>
						<td width="43%">
						<input type="hidden" id="askID" name="cusAsk.askID" value="${cusAsk.askID }"/>
							<span class="isShow">${cusAsk.customerName }</span>
						</td>
						<td width="15%" align="right">单位联系人：</td>
						<td width="40%">
							<span class="isShow">${cusAsk.linkman }</span>
						</td>
					</tr>
					<tr class="trheight">
						<td width="12%" align="right">联系人电话：</td>
						<td>
							<span class="isShow">${cusAsk.phone }</span>	
						</td>
						<td width="12%" align="right">联系人性别：</td>
						<td>
							<span class="isShow">${cusAsk.sax }</span>		
						</td>
					</tr>
					<tr class="trheight">
					<td width="12%" align="right">联系人QQ：</td>
						<td>
							<span class="isShow">${cusAsk.qq }</span>	
						</td>
						<td width="12%" align="right">联系人微信：</td>
						<td>
							<span class="isShow">${cusAsk.wxNumber }</span>		
						</td>
					</tr>
					<tr class="trheight">
						<td width="12%" align="right">咨询问题：</td>
						<td>
							<span class="isShow">${cusAsk.content }</span>	
						</td>
						<td width="12%" align="right">咨询时间：</td>
						<td>
							<span class="isShow">${cusAsk.createTime }</span>		
						</td>
					</tr>
					<tr class="trheight">
						<td width="12%" align="right">是否已处理：</td>
						<td>
						<s:if test="cusAsk.askType=='0'">
							<span class="isShow">未处理</span>
						</s:if><s:else>
							<span class="isShow">已处理</span>
						</s:else>	
						</td>
						<s:if test="cusAsk.askType=='0'">
						</s:if><s:else>
						<td width="12%" align="right">处理时间：</td>
						<td>
							<span class="isShow">${cusAsk.answerTime }</span>		
						</td>
						</s:else>
					</tr>
					
				</table>
				<s:if test="cusAsk.askType=='0'">
				<tr class="trheight">
						<td>
							<input type="button" onclick="checkAsk()"  value="处理" style="width:70px;height:24px;font-size: 12px;"/>
						</td>
					</tr>
				</s:if>
			</form>
		</div>
    </div>
    <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
<script type="text/javascript">

$(function(){   
	setTimeout(function(){ 
        $("div.gdkd").css({"height":GetPageSize()[3]-180+"px"});
 	},100);
	$(window).resize(function(){ 		
		 setTimeout(function(){ 
		        $("div.gdkd").css({"height":GetPageSize()[3]-180+"px"});
		 },100);
	}); 	
});	
		
</script>
</body>
</html>