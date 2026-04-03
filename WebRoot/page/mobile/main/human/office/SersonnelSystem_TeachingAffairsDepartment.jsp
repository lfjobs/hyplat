  <%@ page import="java.net.URLEncoder" %>
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>教务处</title>
<link href="<%=basePath%>css/mobilecss.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script language="javascript" type="text/javascript">
$(document).ready(function(){
	$(".mainNav a").click(function(){
		$(".mainNav a").attr("class","");
		$("a").css("color","");
	   $(this).css("color","red");
		$("#"+this.id).attr("class","actived");
		var currentMenuNo = parseInt(this.id.substring(1));
		$("#secondNav div").each(function(){
			/*$(this).hide();
			$("#subNav"+currentMenuNo).show();*/
			$("#subNav"+currentMenuNo).toggle();
		});
	});
	
	$("#secondNav a").click(function(){.02312222
	   $(this).parent().children("a").css("color","");
	   $(this).css("color","red");
	})
})
</script>
</head>
<body >
    <div class="header">
			<div class="padder">
				<div class="nav">
					<div class="navLaftBg">
						<div class="navRightBg">
							<div class="mainNav">
								<a href="#" id="n2" class="actived"><span>教务处(人事)</span> </a>
							</div>
						</div>
					</div>
				</div>
				<div class="secondNav" id="secondNav">
					<%-- <div id="subNav2" class="subNav2"> --%>
					<div id="subNav2" style="display: none">
						<p class="secondNav">
							<a href="#" class="actived" id="t3"
                                onclick="parent.daohang.document.location.href='<%=basePath%>ea/mobilecosincumbent/ea_getStaffList.jspa'">在职员工</a>
						</p>
						<p class="secondNav">
							<a href="#" class="actived" id="t4"
								onclick="parent.daohang.document.location.href='<%=basePath%>ea/mobilecosdimission/ea_getListCOSDimission.jspa'">离职员工</a>
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
									<a href="#" id="n3"><span>教务处(办公室)</span> </a>
								</div>
							</div>
						</div>
					</div>
					<div class="secondNav" id="secondNav">
						<%-- <div id="subNav3" class="subNav3"> --%>
						<div id="subNav3" style="display: none">
							<p class="secondNav">
								<a href="#" id="t9"
									onclick="parent.daohang.document.location.href='<%=basePath%>/page/mobile/main/navigation/logistics_management1.jsp'">后勤管理处</a>
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
									<a href="#" id="n4"><span>教务处(财务)</span> </a>
								</div>
							</div>
						</div>
					</div>
					<div class="secondNav" id="secondNav">
						<%-- <div id="subNav4" class="subNav4"> --%>
						<div id="subNav4" style="display: none">
							<p class="secondNav">
								<a href="#" id="t12"
									onclick="parent.daohang.document.location.href='<%=basePath%>/page/mobile/main/navigation/finace_d1.jsp'">出纳管理科</a>
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
									<a href="#" id="n5"><span>教务处(生产)</span> </a>
								</div>
							</div>
						</div>
					</div>
					<div class="secondNav" id="secondNav">
						<%-- <div id="subNav5" class="subNav5"> --%>
						<div id="subNav5" style="display: none">
							<p class="secondNav">
								<a href="#" id="t14"
									onclick="parent.daohang.document.location.href='<%=basePath%>/page/mobile/main/navigation/finace_d1.jsp'">出纳管理处</a>
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
									<a href="#" id="n6"><span>教务处(营销)</span> </a>
								</div>
							</div>
						</div>
					</div>
					<div class="secondNav" id="secondNav">
						<%-- <div id="subNav6" class="subNav6"> --%>
						<div id="subNav6" style="display: none">
							<p class="secondNav">
								<a href="#" class="actived" id="t18"
									onclick="parent.daohang.document.location.href='<%=basePath%>/page/mobile/main/navigation/SersonnelSystem_manage_a1.jsp'">售前服务管理科</a>
							</p>
							<p class="secondNav">
								<a href="#" id="t19"
									onclick="parent.daohang.document.location.href='<%=basePath%>/page/mobile/main/navigation/SersonnelSystem_manage_b1.jsp'">售中服务管理科</a>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
</body>
</html>
