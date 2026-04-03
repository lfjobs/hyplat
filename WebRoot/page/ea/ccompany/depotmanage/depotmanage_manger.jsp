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
<title>仓库管理</title>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />
<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css">

<style type="text/css">
<!--
.STYLE1 {color: #27B3FE;
}

-->
</style>
<script type="text/javascript">
var basePath = '<%=basePath%>';
var date;
var token = 0;
$(document).ready(function(){   
     tree=new dhtmlXTreeObject("aadTree","100%","100%",0);
		    tree.enableDragAndDrop(false); 
		    tree.enableHighlighting(1);
	        tree.enableCheckBoxes(0);
			tree.enableThreeStateCheckboxes(false);
			tree.setSkin(basePath+'js/tree/dhx_skyblue');
			tree.setImagePath(basePath+"js/tree/codebase/imgs/");
			tree.loadXML(basePath+"js/tree/common/tree_b.xml");
			tree.insertNewChild("0","001","实物仓库",0,0,0,0);
			tree.insertNewChild("0","002","资料仓库",0,0,0,0);
			tree.insertNewChild("0","003","财务仓库",0,0,0,0);
			tree.setOnClickHandler(function(){
			                        if(token)return;
			                        token = 1;
			                        $(".input01").attr("value","");
                                    $("#desc").attr("html","");
			                       treeid= tree.getSelectedItemId();
			                       treename = tree.getItemText(treeid);
			                        $("#codeName").attr("value",treename);
							           tree.deleteChildItems(treeid);
						  	            var getListCCodeurl=basePath+"ea/cdepotmanage/sajax_ea_getListDepotmanageByPID.jspa?depotID="+treeid+"&date="+new Date().toLocaleString();
									     $.ajax({
						                        url: encodeURI(getListCCodeurl),
						                        type: "get",
						                        async: true,
						                        dataType: "json",
						                        success: function cbf(data){
										           var member = eval("("+data+")");
										            var nologin = member.nologin;
								                  if(nologin){
								                  document.location.href =basePath + "page/ea/not_login.jsp";
								                  }
										           var depotManagelist = member.depotManagelist;
										          
										           if(null == depotManagelist){
										              return;
										           }    
										            for(var i=0;i<depotManagelist.length;i++)
												   {
										             tree.insertNewChild(treeid,depotManagelist[i].depotID,depotManagelist[i].depotName,0,0,0,0);
										             tree.setUserData(depotManagelist[i].depotID,"depotState",depotManagelist[i].depotState);
										           }
										            token = 0;
												  
								            },
						                        error: function cbf(data){
						                           alert("数据获取失败！");
						                        }
						                    });
						     
			         $("#mainframe").attr(
				 				"src","ea/cdepotmanage/ea_getListDepotManageTree.jspa?pageNumber=${pageNumber}&depotID="+ treeid + "&treename=" + treename);
				 	 $(window).resize(); 
				 				
			});
});
</script>
<script type="text/javascript">
  var basePath="<%=basePath%>";
  var pNumber='${pageNumber}';
   var treeid;
   var treename;
   var tree;
</script>

</head>
<body>
<form name="codeForm" method="post"></form>
<div class="main_main">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td  id="qh_sw" style="width: 15%;" valign="top">    
    <div class="qh_gg_nav">&nbsp;仓库管理</div>
   <div id="aadTree" style="border: 0px solid #000000;"></div> 
    </td>
    <td style="width: 85%;" valign="top">
       <iframe src="" name="ccode" width="100%" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize"  vspale="0"> </iframe>
       
    </td>
  </tr>
</table>
</div>
<script type="text/javascript">
    setTimeout(function(){ 
	    $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 - 30 + "px"});
    },100);
    
    $(window).resize(function(){ 
		setTimeout(function(){ 					    
		   $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 - 30 + "px"});
		},100);
    }); 
</script>   
</body>
</html>
