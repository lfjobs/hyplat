<%@page import="hy.ea.bo.Company"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache"/> 
<title>组织机构</title>
<!-- 所有部门组织机构tree -->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Company c = (Company)session.getAttribute("currentcompany"); 
	
%>
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />
<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css">
	<script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
 var basePath="<%=basePath%>";
 var companyID= "<%=session.getAttribute("organizationID")%>";
 var companyName= "<%=session.getAttribute("organizationName")%>";
 var parentID;
 var treeid = null;
 var treename;
 var parentid;
 var parentname;
 var organizationid;
 var tree;
 
 
 $(document).ready(function(){
     tree = new dhtmlXTreeObject("aadTree", "100%", "100%", 0);
     tree.enableDragAndDrop(false);
     tree.enableHighlighting(1);
     tree.enableCheckBoxes(0);
     tree.enableThreeStateCheckboxes(false);
     tree.setSkin(basePath + 'js/tree/dhx_skyblue');
     tree.setImagePath(basePath + "js/tree/codebase/imgs/");
     tree.loadXML(basePath + "js/tree/common/tree_b.xml");
     tree.insertNewChild("0", companyID, companyName, 0, 0, 0, 0);
     tree.setOnClickHandler(function(){
         treeid = tree.getSelectedItemId();
         treename = tree.getItemText(treeid);
         parentid = tree.getParentId(treeid);
         parentname = tree.getItemText(parentid);
         //Submit_onclick();
         tree.deleteChildItems(treeid);
//         parent.document.getElementById("leftFrame").style.display = "none";

        var url = basePath + "ea/corganization/sajax_ea_getOrganizationList.jspa?";
				$.ajax({
					url: encodeURI(url),
					data:"oID="+treeid+"&date="+new Date(),
					type:"get",
                        success: function cbf(data){	
             var member = eval("(" + data + ")");
              var nologin = member.nologin;
			                  if(nologin){
			                  document.location.href = basePath + "page/ea/not_login.jsp";
			                  }
             var organizationList = member.organizationList;
             if (null == organizationList) {
                 return;
             }
             for (var i = 0; i < organizationList.length; i++) {
                 tree.insertNewChild(treeid, organizationList[i].organizationID, organizationList[i].organizationName, 0, 0, 0, 0);
                 
             }
         }, 
                error: function cbf(data){
						         alert("数据获取失败！");
						 }
				});
         $("#mainframe11").attr("src",  basePath + "ea/organization/ea_subordinateList.jspa?pageNumber=${pageNumber}&organizationID=" +
         treeid +
         "&treename=" +
         treename);// 点击刷新机构列表
     });
     
 });
 
  function Submit_onclick(){
    if(window.parent.document.getElementById("leftFrame").style.display == "block") {
	    window.parent.document.getElementById("bgMainContent").cols="0,5,*";
	    window.parent.document.getElementById("leftFrame").style.display = "none"; 
	}
 }
</script>
<body>
<form name="organizationform" method="post">
<table style="width:100%;overflow:hidden;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td  id="qh_sw" style="width: 15%;margin-left: 5px;" valign="top">    
    <div style="width: 100%;" class="qh_gg_nav">&nbsp;机构</div>
	<div id="aadTree" style="width: 100%;z-index:99;"></div> 
    <input type="text" style="display: none;" id="treeid" />
    <input type="text" style="display: none;" id="parentid" />
    <input type="text" style="display: none;" id="treename" />
    <input type="text" style="display: none;" id="parentname" />
    <input type="text" style="display: none;" id="unitsID" />
    </td>
    <td style="width: 85%;" valign="top">
        <iframe id="mainframe11" style="margin:0px;width:100%;height:auto;" name="admin" scrolling="no" frameBorder="0"></iframe>
    </td>
  </tr>
</table>
</form>

<script type="text/javascript">
     $(function(){
         $(window).resize(function(){
             setTimeout(function () {                 
                 $("#aadTree").height($(window).height()- 30);
                 $("#mainframe11").css({"height" : $(window).height() + "px"});
             },100);
         });
         $("#aadTree").height($(window).height()- 30);
         $("#mainframe11").css({"height" : $(window).height() + "px"}); 
     });
</script>
</body>
</html>