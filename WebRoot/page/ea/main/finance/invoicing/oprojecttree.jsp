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

<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />
<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css">


<script type="text/javascript">
 var basePath="<%=basePath%>";
  var codeID = "<%=request.getParameter("codeID")%>";
  var title = "<%=request.getParameter("title")%>";
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
    
 			
 			
 			var getcodeurl = basePath
 					+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID="+codeID+"&date="
 					+ new Date().toLocaleString();
 			tree.insertNewChild(0,
 					codeID, title, 0,
 					0, 0, 0);
 			$.ajax({
 				url : encodeURI(getcodeurl),
 				type : "POST",
 				async : true,
 				dataType : "json",
 				success : function cbf(data) {
 					var member = eval("(" + data + ")");
 					var nologin = member.nologin;
 					if (nologin) {
 						document.location.href = basePath
 								+ "page/ea/not_login.jsp";
 					}
 					var oList = member.codeList;
 					if (null == oList) {
 						return;
 					}
 					for ( var i = 0; i < oList.length; i++) {
                     
 						tree.insertNewChild(
 								codeID,
 								oList[i].codeID,
 								"("+oList[i].codeSn+")"+oList[i].codeValue, 0, 0, 0, 0);
 						tree.setUserData(oList[i].codeID,
 								"codeNumber", oList[i].codeNumber);

 					}
 					
 					
 					

 				},
 				error : function cbf(data) {
 					alert("数据获取失败！");
 				}
 			});
 			
 tree.setOnClickHandler(function() {
 tree.deleteChildItems(tree.getSelectedItemId());
 var getcodeurl = basePath
 				+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?date="
 				+ new Date().toLocaleString();
 		
 		$.ajax({
 			url : encodeURI(getcodeurl),
 			type : "get",
 			async : true,
 			dataType : "json",
 			data:{
 				
 		     codeID:tree.getSelectedItemId()
 			},
 			success : function cbf(data) {
 				var member = eval("(" + data + ")");
 				var nologin = member.nologin;
 				if (nologin) {
 					document.location.href = basePath
 							+ "page/ea/not_login.jsp";
 				}
 				var oList = member.codeList;
 				if (null == oList) {
 					return;
 				}
 				for ( var i = 0; i < oList.length; i++) {

 					tree.insertNewChild(
 							tree.getSelectedItemId(),
 							oList[i].codeID,
 							"("+oList[i].codeSn+")"+oList[i].codeValue, 0, 0, 0, 0);
 					

 				}
 				
 			},
 			error : function cbf(data) {
 				alert("数据获取失败！");
 			}
 			

 			});
 		var codeSn="";
 		var codeValue="";
 		if(title=="项目分类"){
 		
 		if(tree.getSelectedItemId()!=codeID){
 			var code = tree.getSelectedItemText();
 	 		codeSn = code.substring(1,code.indexOf(")"));
 	 		codeValue = code.substring(code.indexOf(")")+1);
 		} 
 		/* var parameter = window.parent.document.getElementById("parameterxm").value;
 		parent.getProjectByxmtype("parameter="+parameter+"&xmtype="+codeSn); */
 		window.parent.document.getElementById("selectxm").value=codeSn;
 		
 		window.parent.document.getElementById("selectxms").value=codeValue;
 		}
 		if(title=="设备管理"){
 			
 			window.parent.document.getElementById("codeID").value=tree.getSelectedItemId();
 		}
     });
 });
 

</script>
</head>
<body>

<table style="width:100%;overflow:hidden;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td  id="qh_sw" margin-left: 5px;" valign="top">    
	<div id="aadTree" style="width: 100%;z-index:99;overflow:hidden;"></div> 

    </td>
    
  </tr>
</table>



</body>
</html>