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

</head>
<script type="text/javascript">
 var basePath="<%=basePath%>";
 var companyID= "<%=session.getAttribute("organizationID")%>";
 var companyName= "<%=session.getAttribute("organizationName")%>";
 var yanzheng="${param.yanzheng}";
 var parentID;
 var treeid = null;
 var treename;
 var parentid;
 var parentname;
 var organizationid;
 var tree;
 if(yanzheng=="01")
     {
      	   companyID="${companyID}";
      	  companyName="${currentcompany.companyName}";
       
    }
 
 $(document).ready(function(){
     tree = new dhtmlXTreeObject("aadTree", "100%", "100%", 0);
     tree.enableDragAndDrop(false);
     tree.enableHighlighting(1);
     tree.enableCheckBoxes(0);
     tree.enableThreeStateCheckboxes(false);
     tree.setSkin(basePath + 'js/tree/dhx_skyblue');
     tree.setImagePath(basePath + "js/tree/codebase/imgs/");
     tree.loadXML(basePath + "js/tree/common/tree_b.xml");    
     tree.insertNewChild("0",companyID,companyName, 0, 0, 0, 0);
     tree.setOnClickHandler(function(){
         treeid = tree.getSelectedItemId();
         treename = tree.getItemText(treeid);
         parentid = tree.getParentId(treeid);
         parentname = tree.getItemText(parentid);      
         tree.deleteChildItems(treeid);
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
				
				
				var parameter = window.parent.document.getElementById("parameterrm").value;
				
		 		parent.getStaffMember("parameter="+parameter+"&selectDept="+treeid);
		 		window.parent.document.getElementById("selectdept").value=treeid;
		 		window.parent.document.getElementById("selectdeptname").value=treename;
     });
     
 });

</script>
<body>

<table style="width:100%;overflow:hidden;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td  id="qh_sw" style="width: 15%;margin-left: 5px;" valign="top">    
    <div style="width: 100%;" class="qh_gg_nav">&nbsp;机构</div>
	<div id="aadTree" style="width: 100%;z-index:99;"></div> 

    </td>
    
  </tr>
</table>



</body>
</html>