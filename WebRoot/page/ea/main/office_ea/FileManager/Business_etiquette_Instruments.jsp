<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	
        <title>企业礼仪文书</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
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
			<div style="width:100%;text-align:left;" class="qh_gg_nav">&nbsp;企业礼仪文书</div>
		
			<div >
			  <img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif" name="j1"/><a href="#" id="jia">企业礼仪文书管理</a>
			  <div id="g1">
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif" /><a href="<%=basePath%>ea/documenttemplate/ea_getDocTemplateList.jspa?receiptType=欢迎词" target="admin4">欢迎词</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif"/><a href="<%=basePath%>ea/documenttemplate/ea_getDocTemplateList.jspa?receiptType=欢送词" target="admin4">欢送词</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif" /><a href="<%=basePath%>ea/documenttemplate/ea_getDocTemplateList.jspa?receiptType=答谢词" target="admin4">答谢词</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif" /><a href="<%=basePath%>ea/documenttemplate/ea_getDocTemplateList.jspa?receiptType=祝贺词" target="admin4">祝贺词</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif"/><a href="<%=basePath%>ea/documenttemplate/ea_getDocTemplateList.jspa?receiptType=请柬词" target="admin4">请柬词</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif" /><a href="<%=basePath%>ea/documenttemplate/ea_getDocTemplateList.jspa?receiptType=邀请词" target="admin4">邀请信</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif" /><a href="<%=basePath%>ea/documenttemplate/ea_getDocTemplateList.jspa?receiptType=感谢词" target="admin4">感谢信</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif"/><a href="<%=basePath%>ea/documenttemplate/ea_getDocTemplateList.jspa?receiptType=慰问词" target="admin4">慰问信</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif" /><a href="<%=basePath%>ea/documenttemplate/ea_getDocTemplateList.jspa?receiptType=介绍词" target="admin4">介绍信</a></div>
			        <div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif"/><a href="<%=basePath%>ea/documenttemplate/ea_getDocTemplateList.jspa?receiptType=证明词" target="admin4">证明信</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif" /><a href="<%=basePath%>ea/documenttemplate/ea_getDocTemplateList.jspa?receiptType=推荐词" target="admin4">推荐信</a></div>
			  </div>
			</div>
			
	</div>
   <div style="width:85%;float:left;">
       <iframe src="<%=basePath%>ea/informbills/ea_getInformBillsList.jspa?" id="mainframe1" style="margin:0px;width:100%;height:650px;" name="admin4" scrolling="no" frameBorder="0"></iframe>
   </div>
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
