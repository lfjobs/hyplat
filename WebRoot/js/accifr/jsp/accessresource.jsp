<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache"/> 
<title>选择人员单位物品框架</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<script src="<%=basePath%>js/accifr/js/accessresource.js"></script>
<style type="text/css">
.btn02 {
    background: url("../../images/admin_images/act_btn00.gif") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
    border: 0 solid #111;
    cursor: pointer;
    font-size: 12px;
    height: 24px;
    line-height: 24px;
    width: 70px;
}
.qh_gg_nav{
	background-color: #abc3cf;
	padding: 0px;
	margin:0px;
	color:#1E5494;
}
a:HOVER {
	cursor:pointer;
}
.body_02cu{
	margin:0px;
	padding: 0px;
}
.table td,th{
	margin:0px;
	padding: 0px;
	border: 1px solid #abc3cf;
	
}
#body_02cu td{ overflow: auto;}
#body_02cu table{ overflow: auto;}

</style>
<script type="text/javascript">
 	var basePath="<%=basePath%>"; 
	var stuts = '${stuts}';
	var pID = '${pid}';
	var pName = '${pname}';
	var treeid = '${pid}';
	var stutsurl = '${stutsurl}';
	var searchvalue = '${searchvalue}';
	var ccodesvalue = '${ccodesvalue}';
	var ccodecvalue = '${ccodecvalue}';
	var e = "";
	var pareID = "";
	var jsptype = '${jsptype}';
</script>
</head>
<body style="">
<input type="hidden" id="pareID"/>
<form name="accessform" method="post">
<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
<tr>
		<td width="25%" height="30px" style="border-right: 1px "><div style="width: 100%;" class="drag">&nbsp;菜单</div></td>
		<td><div class="drag" id="body_02cu_h">请选择</div></td>
	</tr>
	<tr>
		<td height="45%" id="tdheight">
			<div id="aadTree" style="border: 1px solid #eff; width: 100%;  overflow: auto;"></div>
		</td>
		<td rowspan="3" height="100%">
			<div id="body_02cu" style=" overflow: auto; height:100%;"></div>
		</td>
	</tr>
	<tr> 
		<td width="25%" height="30px" class="wlgx"><div style="width: 100%;" class="drag">往来关系客户分类</div></td>
	</tr>
	<tr>
		<td style="vertical-align: top;">
			<div id="ccodec" style="display: none">
			<s:iterator value="ccodec"><!-- 单位关系 -->
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<img alt="" src="../../images/ea/human/opened.gif">
				<a href="#" style="text-decoration: none;" onclick="javascript:contact('${codeValue}')">
				<font color="black"><s:property value="codeValue"/></font></a><p></p>
			</s:iterator>
			</div>
			<div id="ccodes" style="display: none">
			<s:iterator value="ccodes"><!-- 人员关系 -->
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<img alt="" src="../../images/ea/human/opened.gif">
				<a href="#" style="text-decoration: none;" onclick="javascript:contact('${codeValue}')">
				<font color="black"><s:property value="codeValue"/></font></a><p></p>
			</s:iterator>
			</div>
		</td>
	</tr>
</table>

<script type="text/javascript">
$(function(){
         $(window).resize(function(){
             setTimeout(function () {                 
                 $("#aadTree").height($(window).height()- 30);
                 $("#mainframe").css({"height" : $(window).height() - 5 + "px"});
             },100);
         });
         $("#aadTree").height($(window).height()- 30);
     });
</script>
</form>
</body>
</html>