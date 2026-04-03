<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>代码管理</title>
<!-- 后台基础数据维护》》代码管理 -->
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />
<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css">
<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_manger.js"></script>
<style type="text/css">
<!--
.STYLE1 {color: #27B3FE;
}

-->
</style> 
</head>
<body>
<form name="codeForm" method="post">
<input name="sub" value="${session_value}" type="hidden" />
<!-- 代替token--></form>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td  id="qh_sw" style="width: 15%;margin-left: 5px;" valign="top">    
    <div class="qh_gg_nav">&nbsp;代码维护</div>
   <div id="aadTree" style="overflow:auto;z-index:99;display:yes; border: 0px solid #000000;"></div> 
    </td>
    <td style="width: 85%;" valign="top">
       <iframe src="" name="ccode" style="width:100%;height:0;" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize"  vspale="0"> </iframe>
    </td>
  </tr>
</table>
<script type="text/javascript">
	var basic="${basic}";
	var basMap="${basMap}";
	var basicMap=${basicMap};
	var basePath="<%=basePath%>";
	var pNumber='${pageNumber}';
	var codeHylb="${codeHylb}";
	var treeid;
	var treename;
	var tree;
	var date;
     $(function(){
        $(window).resize(function(){
             setTimeout(function () {                 
                 $("#aadTree").height($(window).height()- 30);
                 $("#mainframe").css({"height" : $(window).height() - 5 + "px"});
             },100);
         });
         $("#aadTree").height($(window).height()- 30);
         $("#mainframe").css({"height" : $(window).height() - 5 + "px"}); 
         //去排序下属机构页面
		$("#sort").click(function(){
			treeid = tree.getSelectedItemId();
			if(treeid==""){
			return;
			}
			document.all.ccode.src ="<%=basePath%>ea/cccode/ea_toSortChildCode.jspa?pageNumber=${pageNumber}&codeID="+treeid;        
		}); 
     });
     
     function Submit_onclick(){
	     if(window.parent.document.getElementById("leftFrame").style.display == "block") {
		    window.parent.document.getElementById("bgMainContent").cols="0,5,*";
			window.parent.document.getElementById("leftFrame").style.display = "none";
			//document.getElementById("ImgArrow").src=basePath + "images/plat/main/switch_right.gif";
			//document.getElementById("ImgArrow").alt="打开左侧导航栏";
		} 
	}
 </script>
</body>
</html>
