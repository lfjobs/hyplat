
<%@page import="java.util.Date"%><html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="Cache-Control" content="no-cache" />
        <title>组织机构</title>
        <%@ page import="java.net.URLEncoder" %>
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
      <script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
      <script type="text/javascript" src="<%=basePath%>js/curvycorners.js"></script>
      <link href="<%=basePath%>css/mobilecss.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />
	<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
	<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css">
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
    </head>
    <%-- <style type="text/css">
		body {
			background-image: url(<%=basePath%>images/r_2_06.gif);
			background-repeat: repeat-x;
		}
	</style> --%>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
		addEvent(window, 'load', initCorners);
		function initCorners() {
		var setting = {
		tl: { radius: 6 },
		tr: { radius: 6 },
		bl: { radius: 6 },
		br: { radius: 6 },
		antiAlias: true
		}
		curvyCorners(setting, ".r_t_c_left");
		}
		/*
		function hide(){
		if(parent.parent.document.getElementById("topdiv").style.height == "70px"){
		    parent.parent.document.getElementById("topdiv").style.height = "0px";
		    $("#hide").text("显示头部");
		 }else{
		    parent.parent.document.getElementById("topdiv").style.height = "70px";
		     $("#hide").text("隐藏头部");
		 }
		}
		*/
    </script>
    <body>
	<!--
	<div class="r_t_c_left" style="background:#FFFFFF; margin-top:1px;">
    	<div class="rtcl_top01">
        	机构菜单
        	<a style="margin-right:2px;" id="hide" href="#" onclick="hide()">隐藏头部</a>
        </div>
        <div id="aadTree" style="overflow:auto;z-index:99;display:yes;margin-top:3px;height:582px; border: 0px solid #000000;"></div>
  </div>
  -->
  <div class="r_t_c_left" style="background:#FFFFFF;">
        <div id="aadTree" style="overflow:auto;z-index:99;display:yes;margin-top:3px;height:582px;"></div>
  </div>
</body>
<script>
    	var treeid = '${company.companyID}';
	    var tree;
        tree = new dhtmlXTreeObject("aadTree", "100%", "100%", 0);
        tree.enableDragAndDrop(false);
        tree.enableHighlighting(1);
        tree.enableCheckBoxes(0);
        tree.enableThreeStateCheckboxes(false);
        tree.setSkin('<%=basePath%>js/tree/dhx_skyblue');
        tree.setImagePath("<%=basePath%>js/tree/codebase/imgs/");
        tree.loadXML("<%=basePath%>js/tree/common/tree_b.xml");
        tree.insertNewChild("0",'${company.companyID}','${company.companyName}',0,0,0,0);
            var url1 = basePath +"/ea/office/sajax_ea_getOrganizationList.jspa?organizationID="+encodeURI(treeid)+"&datesete=<%=new Date()%>";
            $.ajax({
                        url: url1,
                        type: "get",
                        async: true,
                        dataType: "json",
                        success: function cbf(data){
                          var member = eval("(" + data + ")");
                           var nologin = member.nologin;
			                  if(nologin){
			                  document.location.href ="<%=basePath%>page/ea/not_login.jsp";
			                  }
			               var organizationList = member.organizationList;
			               var companylist = member.companylist;
				                 if (null != organizationList) {
				                      for (var i = 0; i < organizationList.length; i++) {
				                      tree.insertNewChild(treeid, organizationList[i].organizationID, organizationList[i].organizationName, 0, 0, 0, 0);
				                      tree.setUserData(organizationList[i].organizationID,"InterfaceUrl",organizationList[i].organizationUrl);
				                      }
				                } 
			                if (null != companylist) {
			                    for (var i = 0; i < companylist.length; i++) {
			                      tree.insertNewChild(treeid, companylist[i].companyID, companylist[i].companyName, 0, 0, 0, 0);
			                    }
			                }
			                status = 0;
                        },
                        error: function cbf(data){
                           alert("数据获取失败！")
                        }
                    });
        tree.setOnClickHandler(function(){
            parent.document.getElementById("daohang").src= "";
            treeid = tree.getSelectedItemId();
            var InterfaceUrl = tree.getUserData(treeid,"InterfaceUrl");
            if(InterfaceUrl){
            }else{
            InterfaceUrl = "/ea/office/ea_toSersonnelSystemsum";
            }
            var url = basePath + InterfaceUrl + ".jspa?organizationID="+ treeid;
           parent.document.getElementById("daohang").src= encodeURI(url);
           parent.document.getElementById("officeFrame").cols  = "0,*";
        })
        //在Opera浏览中无法调用top方法
        /*function top(url){
            parent.document.getElementById("daohang").src= encodeURI(url);
	        parent.document.getElementById("mainFrame_body").src= "";
        }*/
    </script>
</html>
