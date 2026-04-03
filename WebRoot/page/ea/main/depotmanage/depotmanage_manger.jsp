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
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css">

<style type="text/css">
<!--
.STYLE1 {color: #27B3FE;
}

-->
</style>
<script type="text/javascript">
var token = 0
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
						  	            var getListCCodeurl=basePath+"ea/depotmanage/sajax_ea_getListDepotmanageByPID.jspa?depotID="+treeid+"&date="+new Date().toLocaleString();
									     $.ajax({
						                        url: encodeURI(getListCCodeurl),
						                        type: "get",
						                        async: false,
						                        dataType: "json",
						                        success: function cbf(data){
										           var member = eval("("+data+")");
										            var nologin = member.nologin;
								                  if(nologin){
								                  document.location.href ="<%=basePath%>page/ea/not_login.jsp";
								                  }
										           var depotManagelist = member.depotManagelist;
										          
										           if(null == depotManagelist){
										              return;
										           }    
										            for(var i=0;i<depotManagelist.length;i++)
												   {
												       if(depotManagelist[i].depotType=='1'){
                                                           tree.insertNewChild(treeid,depotManagelist[i].depotID,depotManagelist[i].depotName,0,0,0,0);
                                                         //  tree.setStdImages("dept.gif");
                                                       }
                                                       else if(depotManagelist[i].depotType=='2'){
                                                           tree.insertNewChild(treeid,depotManagelist[i].depotID,depotManagelist[i].depotName,0, "area.gif","area.gif","area.gif");
                                                       }else if(depotManagelist[i].depotType=='3'){
                                                           tree.insertNewChild(treeid,depotManagelist[i].depotID,depotManagelist[i].depotName,0, "shelves.gif","shelves.gif","shelves.gif");
                                                       }else if(depotManagelist[i].depotType=='4'){
                                                           tree.insertNewChild(treeid,depotManagelist[i].depotID,depotManagelist[i].depotName,0, "booth.gif","booth.gif","booth.gif");
                                                       }
                                                       tree.setUserData(depotManagelist[i].depotID,"depotState",depotManagelist[i].depotState);
                                                       tree.setUserData(depotManagelist[i].depotID,"depotType",depotManagelist[i].depotType);
										           }
										            token = 0;
												  
								            },
						                        error: function cbf(data){
						                           alert("数据获取失败！")
						                        }
						                    });
						     
			         $("#mainframe").attr(
				 				"src","ea/depotmanage/ea_getListDepotManageTree.jspa?pageNumber=${pageNumber}&depotID="+ treeid + "&treename=" + treename);
				 	$(window).resize();
				 				
			});
});
</script>
<script type="text/javascript">
  var basePath="<%=basePath%>";
  var pNumber=${pageNumber};
   var treeid;
   var treename;
   var tree;
   var type;
</script>
</head>
<body>
<form name="codeForm" method="post"></form>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td  id="qh_sw" style="width: 15%;" valign="top">    
    <div class="qh_gg_nav">&nbsp;仓库管理</div>
   <div id="aadTree" style="border: 0px solid #000000;"></div> 
    </td>
    <td style="width: 85%;" valign="top">
        <iframe src="" name="ccode" style="width:100%;height:0" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize"  vspale="0"></iframe>
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
         $("#mainframe").css({"height" : $(window).height() - 5 + "px"}); 
     });
</script>
</body>
</html>
