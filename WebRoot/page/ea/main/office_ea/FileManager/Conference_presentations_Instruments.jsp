<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %><html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>演讲会议文书</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blues.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
          <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
          <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
         <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>

		
		<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet" type="text/css" />
		
		
		
<style>
	.sec{
		margin-left:20px;
	}
	a:link,a:visited{
		text-decoration:none;
		color:black;
	}
</style>
	
</head>
  
  <body>
    <div style="width:14%;float:left;">    
			<div style="width:100%;text-align:left;" class="qh_gg_nav">&nbsp;演讲会议文书</div>
		
			<div >
			  <img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif" name="j1"/><a href="#" id="jia">演讲会议文书管理</a>
			  <div id="g1">
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif" /><a href="javascript:void(0);" target="admin6">竞聘演讲</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif"/><a href="javascript:void(0);" target="admin6">就职演讲</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif" /><a href="javascript:void(0);" target="admin6">即兴演讲</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif" /><a href="javascript:void(0);" target="admin6">大会发言</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif"/><a href="javascript:void(0);" target="admin6">领导发言稿</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif" /><a href="javascript:void(0);" target="admin6">会议开幕词</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif" /><a href="javascript:void(0);" target="admin6">会议闭幕词</a></div>
			  </div>
			</div>
			
	</div>
   <div style="width:85%;float:left;">
       <%--<iframe src="<%=basePath%>ea/informbills/ea_getInformBillsList.jspa?" id="mainframe1" style="margin:0px;width:100%;height:650px;" name="admin6" scrolling="no" frameBorder="0"></iframe>
   --%></div>
   <script type="text/javascript">
   var basepath="<%=basePath%>";
	 $("#g1").show();
		$(function ()
		{
			$("#jia").toggle(
				function ()
				{
					$("#g1").hide();
					$("img[name='j1']").each(
						function (i)
						{
							this.src=basepath+"js/tree/codebase/imgs/folderClosed.gif";
						}
					)
				},function ()
				  {
					  $("#g1").show();
					  $("img[name='j1']").each(
						function (i)
						{
							this.src=basepath+"js/tree/codebase/imgs/folderOpen.gif";
						}
					)
				  }
			);
		})
</script>
  </body>
</html>
