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
<title>菜单导航管理页面</title>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />
<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
<script src="<%=basePath%>js/ea/modal_manger.js"></script>
</head>
<body>
<form name="organizationform" method="post">
<table style="width:100%;overflow:hidden;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td  id="qh_sw" style="width: 15%;" valign="top">
    <div style="width: 100%;" class="qh_gg_nav">&nbsp;机构</div>
	<div id="aadTree" style="border: 0px solid #000000;"></div>
		    <input type="text" style="display: none;" id="treeid" />
		    <input type="text" style="display: none;" id="parentid" />
		    <input type="text" style="display: none;" id="treename" />
		    <input type="text" style="display: none;" id="parentname" />
		    <input type="text" style="display: none;" id="unitsID" />
    </td>
 <td style="width: 85%;" valign="top">
        <iframe id="mainframe" style="margin:0px;width:100%;height:0;" name="admin" scrolling="no" frameBorder="0"></iframe>
    </td>
  </tr>
</table>

<script type="text/javascript">
 var basePath="<%=basePath%>"; 
 var companyID='${company.companyID}'; 
 var companyName='${company.companyName}';
 var parentID;
 var treeid = null;
 var treename;
 var parentid;
 var parentname;
 var organizationid;
 var tree;
 
 function Submit_onclick(){
	if(window.parent.document.getElementById("leftFrame").style.display == "block") {
	     window.parent.document.getElementById("bgMainContent").cols="0,5,*";
		 window.parent.document.getElementById("leftFrame").style.display = "none";
	} 
}
$(function(){
         $(window).resize(function(){
             setTimeout(function () {                 
                 $("#aadTree").height($(window).height()- 30);
                 $("#mainframe").css({"height" : $(window).height() - 5 + "px"});
             },100);
         });
         $("#aadTree").height($(window).height()- 30);
         $("#mainframe").css({"height" : $(window).height() - 5 + "px"}); 
     });
</script>
</form>
</body>
</html>