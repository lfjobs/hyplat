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
        <title>项目管理</title>
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
			<div style="width:100%;text-align:left;" class="qh_gg_nav">&nbsp;项目管理</div>
		
			<div>
			  <img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif"/>
			  <a href="<%=basePath%>ea/projectmanager/ea_toDetailProject.jspa?project.proid=<%=request.getParameter("proid")%>" target="admin1"><span class="folder curor">${param.proname}</span></a>
			  <div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif" /><a href="<%=basePath%>ea/taskmanage/ea_getTaskNoticeList.jspa?myproject.proid=<%=request.getParameter("proid")%>" target="admin1" class="permission" >任务下达阶段</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif"/><a href="<%=basePath%>ea/taskmanage/ea_getListByTaskManageProduction.jspa?myproject.proid=<%=request.getParameter("proid")%>" target="admin1" >生产设计阶段</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif" /><a href="<%=basePath%>ea/taskmanage/ea_getListByTaskManageFinished.jspa?myproject.proid=<%=request.getParameter("proid")%>" target="admin1" class="permission" >设计完成阶段</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif"/><a href="<%=basePath%>ea/taskmanage/ea_getListByTaskManageResults.jspa?myproject.proid=<%=request.getParameter("proid")%>" target="admin1" class="permission" >提交成果阶段</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif" /><a href="<%=basePath%>ea/taskmanage/ea_getSzcostList.jspa?myproject.proid=<%=request.getParameter("proid")%>" target="admin1" class="permission" >收支费用管理</a></div>
					<div class="sec"><img src="<%=basePath%>js/tree/codebase/imgs/folderOpen.gif"/><a href="<%=basePath%>ea/taskmanage/ea_getListByTaskManageFile.jspa?myproject.proid=<%=request.getParameter("proid")%>" target="admin1" class="permission" >项目档案管理</a></div>
			  </div>
			</div>
	</div>
   <div style="width:86%;float:left;height:670px;">
       <iframe src="<%=basePath%>ea/projectmanager/ea_toDetailProject.jspa?project.proid=<%=request.getParameter("proid")%>" id="mainframe1" style="margin:0px;width:100%;height:670px;" name="admin1" scrolling="no" frameBorder="0"></iframe>
   </div>
   <script type="text/javascript">
   	 var   proid="<%=request.getParameter("proid")%>";
   	 var   basePath = "<%=basePath%>";
   	 var   notken=0;
	 		$("#g1").show();
			 $(function (){
					$("a.permission").mousedown(function(e){
							var url1 = basePath
							+ "ea/taskmanage/sajax_ea_getPermission.jspa?myproject.proid="
							+ proid + "&datesete=" + new Date();
							if(notken){
								return false;
							}
							notken=1;
							$.ajax({
										url : url1,
										type : "get",
										dataType : "json",
										async:false,
										success : function cbf(data) {
							
											var member = eval("(" + data + ")");
											var result = member.result;
											if (!result) {
												alert("只有负责人才可以查看!!");
												notken=0;
												return false;
											}else{
												notken=0;
												return true;
											};
										},
										error : function cbf(data) {
											alert("数据获取失败！");
											notken=0;
										}
							});
					});
			});
</script>
  </body>
</html>
