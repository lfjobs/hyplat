<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>公司</title>
		<link href="<%=basePath%>css/mobilecss.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript">
$(document).ready(function(){
	$(".mainNav a").click(function(){
		$(".mainNav a").attr("class","");
		$("a").css("color","");
	   $(this).css("color","red");
		$("#"+this.id).attr("class","actived");
		var currentMenuNo = parseInt(this.id.substring(1));
		$("#secondNav div").each(function(){
			$(this).hide();
			$("#subNav"+currentMenuNo).show();
			/*$("#subNav"+currentMenuNo).toggle();*/
		});
	});
	
	$("#secondNav a").click(function(){
	   $(this).parent().children("a").css("color","");
	   $(this).css("color","red");
	})
})
</script>
	</head>
	<body>
		<div class="header">
			<div class="padder">
				<div class="nav">
					<div class="navLaftBg">
						<div class="navRightBg">
							<div class="mainNav">
								<a href="#" id="n1" class="actived"><span>公司(后台)</span> </a>
							</div>
						</div>
					</div>
				</div>
				<div class="secondNav" id="secondNav">
					<%-- <div id="subNav1" class="subNav1"> --%>
					<div id="subNav1" style="display: none">
						<%-- <p class="secondNav">
							<a href="#" class="actived" id="t22"
								onclick="parent.daohang.document.location.href='<%=basePath%>ea/corganization/ea_getCompanyMessage.jspa'">公司部门管理</a>
						</p> --%>
						<p class="secondNav">
							<a href="#" id="t23"
								onclick="parent.daohang.document.location.href='<%=basePath%>/page/mobile/main/navigation/ccompanyDetil1.jsp'">公司信息管理</a>
						</p>
						<p class="secondNav">
							<a href="#" id="t24"
								onclick="parent.daohang.document.location.href='<%=basePath%>ea/ccaccount/ea_getListCAccount.jspa'">账号管理</a>
						</p>
						<p class="secondNav">
							<a href="#" id="t25"
								onclick="parent.daohang.document.location.href='<%=basePath%>ea/ccrole/ea_getListCRole.jspa'">角色管理</a>
						</p>
						<p class="secondNav">
							<a href="#" id="t26"
								onclick="parent.daohang.document.location.href='<%=basePath%>ea/cclogbook/ea_getListCLogBook.jspa'">系统日志查询</a>
						</p>
					</div>
				</div>
			</div>
			<div class="header">
				<div class="padder">
					<div class="nav">
						<div class="navLaftBg">
							<div class="navRightBg">
								<div class="mainNav">
									<a href="#" id="n2" class="actived"><span>公司(人事)</span> </a>
								</div>
							</div>
						</div>
					</div>
					<div class="secondNav" id="secondNav">
						<%-- <div id="subNav2" class="subNav2"> --%>
						<div id="subNav2" style="display: none">
							<p class="secondNav">
								<a href="#" class="actived" id="t2"
									onclick="parent.daohang.document.location.href='<%=basePath%>/page/mobile/main/navigation/companydepartment.jsp'">人事管理科</a>
							</p>
						</div>
					</div>
				</div>
				<div class="header">
					<div class="padder">
						<div class="nav">
							<div class="navLaftBg">
								<div class="navRightBg">
									<div class="mainNav">
										<a href="#" id="n3"><span>公司(办公室)</span> </a>
									</div>
								</div>
							</div>
						</div>
						<div class="secondNav" id="secondNav">
							<%-- <div id="subNav3" class="subNav3"> --%>
							<div id="subNav3" style="display: none">
								<p class="secondNav">
									<a href="#" id="t9">后勤管理科</a>
								</p>
							</div>
						</div>
					</div>
				</div>
				<div class="header">
					<div class="padder">
						<div class="nav">
							<div class="navLaftBg">
								<div class="navRightBg">
									<div class="mainNav">
										<a href="#" id="n4"><span>公司(财务)</span> </a>
									</div>
								</div>
							</div>
						</div>
						<div class="secondNav" id="secondNav">
							<%-- <div id="subNav4" class="subNav4"> --%>
							<div id="subNav4" style="display: none">
								<p class="secondNav">
									<a href="#" id="t16"
										onclick="parent.daohang.document.location.href='<%=basePath%>/page/mobile/main/navigation/companyfinance.jsp'">出纳管理</a>
								</p>
								<%-- <p class="secondNav">
									<a href="#"
										onclick="parent.daohang.document.location.href='<%=basePath%>/page/ea/main/navigation/finace_e.jsp'"
										class="actived" id="t12">税务管理处</a>
								</p> --%>
							</div>
						</div>
					</div>
				</div>
				<div class="header">
					<div class="padder">
						<div class="nav">
							<div class="navLaftBg">
								<div class="navRightBg">
									<div class="mainNav">
										<a href="#" id="n5"><span>公司(生产)</span> </a>
									</div>
								</div>
							</div>
						</div>
						<div class="secondNav" id="secondNav">
							<%-- <div id="subNav5" class="subNav5"> --%>
							<div id="subNav5" style="display: none">
								<p class="secondNav">
									<a href="#" class="actived" id="t17">人事管理科</a>
								</p>
								<p class="secondNav">
									<a href="#" id="t18">目标管理科</a>
								</p>
							</div>
						</div>
					</div>
				</div>
				<div class="header">
					<div class="padder">
						<div class="nav">
							<div class="navLaftBg">
								<div class="navRightBg">
									<div class="mainNav">
										<a href="#" id="n6"><span>公司(营销)</span> </a>
									</div>
								</div>
							</div>
						</div>
						<div class="secondNav" id="secondNav">
							<%-- <div id="subNav6" class="subNav6"> --%>
							<div id="subNav6" style="display: none">
								<p class="secondNav">
									<a href="#" class="actived" id="t18">售前服务管理科</a>
								</p>
								<p class="secondNav">
									<a href="#" class="actived" id="t18">售前服务管理科</a>
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
	</body>
</html>
