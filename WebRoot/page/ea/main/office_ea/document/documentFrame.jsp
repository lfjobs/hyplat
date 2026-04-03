<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>公文导航树</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet"
			href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
		<script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
			type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/office_ea/document/documentFrame.js"></script>

		<script type="text/javascript">
   
             var basePath='<%=basePath%>';  
             var module = '<%=session.getAttribute("module")%>'; 
             var journalNum = "${param.journalNum}";
             var projectName = "${param.projectName}";
        </script>
		<style type="text/css">
#doctree {
	position: absoute;
	padding-top: 0cm;
	margin-top: 0px;
	width: 100%;
	background-color: #FFFFFF;
	float: left;
}

#qh_sw {
	width: 15%;
	border: 1px solid #DAE7F6;
}

.treeview li {
	margin: 0;
	padding: 1px 0 1px 16px;
}

.numcss {
	color: red;
}
</style>


	</head>
	<body>
		<div class="main_main">
			<table width="100%" cellspacing="0" cellpadding="0" "border="2">
				<tr>
					<td id="qh_sw" style="width: 15%" valign="top">
						<div class="qh_gg_nav">
							&nbsp;
							<span id="frametitle">公文流转</span>

						</div>
						<!--左边的树 -->

						<ul id="navigation" style="width: 180px;"
							class="filetree">
							<li>
								<span class="folder" id="tit">公文电子化传输</span>
								<ul>
									<li>
										<span class="folder">拟稿</span>
										<ul>
											<li>
												<a href="#" onclick="tonclick('draft')"><span
													class="file">草稿箱</span><span class="numcss" id="draft">(0)</span>
												</a>
											</li>
											<li>
												<a href="#" onclick="tonclick('receivebox')"><span
													class="file">收件箱</span><span class="numcss" id="receivebox">(0)</span>
												</a>
											</li>
											<li>
												<a href="#" onclick="tonclick('yessenddraft')"><span
													class="file">已发送</span> </a>
											</li>
										</ul>
									</li>
									<li>
										<span class="folder">审批</span>
										<ul>
											<li>
												<a href="#" onclick="tonclick('approvalno')"><span
													class="file" id="unex">未审批公文</span><span class="numcss"
													id="approvalno">(0)</span> </a>
											</li>
											<li>
												<a href="#" onclick="tonclick('aprrovalyes')"><span
													class="file" id="ex">已审批公文</span> </a>
											</li>
										</ul>
									</li>
									<c:if test="${param.module != 'news'&&param.module != 'AnnNoti'}">
									<li>
										<span class="folder">盖章</span>
										<ul>
											<li>
												<a href="#" onclick="tonclick('stampno')"><span
													class="file" id="unseal">未盖章公文</span><span class="numcss" id="stampno">(0)</span>
												</a>
											</li>
											<li>
												<a href="#" onclick="tonclick('stampyes')"><span
													class="file" id="seal">已盖章公文</span> </a>
											</li>
										</ul>
									</li>
									<li>
										<span class="folder" id="gl">发文管理</span>
										<ul>
											<li>
												<a href="#" onclick="tonclick('nosenddoc')"><span
													class="file" id="unpub">未发公文</span><span class="numcss" id="publish">(0)</span>
												</a>
											</li>
											<li>
												<a href="#" onclick="tonclick('yessenddoc')"><span
													class="file" id="pub">已发公文</span> </a>
											</li>
										</ul>
									</li>
									<li>
										<span class="folder">阅读管理</span>
										<ul>
											<li>
												<a href="#" onclick="tonclick('readno')"><span
													class="file" id="unread">未阅读</span><span class="numcss" id="read">(0)</span>
												</a>
											</li>
											<li>
												<a href="#" onclick="tonclick('readyes')"><span
													class="file" id="readed">已阅读</span> </a>
											</li>
										</ul>
									</li>
									<li>
										<span class="folder" id="gd">公文归档</span>
										<ul>
											<li>
												<a href="#" onclick="tonclick('archive')"><span
													class="file">已归档</span> </a>
											</li>
										</ul>
									</li>
									<li>
										<a href="#" onclick="tonclick('share')"><span
											class="folder">共享池</span> </a>
									</li>
									<li>
										<a href="#" onclick="tonclick('template')"><span
											class="folder">模板管理</span> </a>
									</li>
									<li>
										<a href="#" onclick="tonclick('litter')"><span
											class="folder">回收站</span> </a>
									</li>
							</c:if>
							
							</li>
						</ul>

						
					</td>
					<td style="width: 85%;" valign="top">
					<c:if test="${param.module == 'news'||param.module == 'AnnNoti'}">
					<iframe id="mainframe"
							src="<%=basePath%>ea/documentinfo/ea_getDocDraftList.jspa"
							frameborder="0" style="width: 100%;"></iframe>
					</c:if>
					<c:if test="${param.module != 'news'&&param.module != 'AnnNoti'}">
						<iframe id="mainframe"
						    src="<%=basePath%>page/ea/main/office_ea/document/index.jsp"
							frameborder="0" style="width: 100%;"></iframe>
					</c:if>
					</td>
				</tr>
			</table>
		</div>
		<script type="text/javascript">
     $(function(){
         $(window).resize(function(){
             setTimeout(function () {                 
                 $("#navigation").height($(window).height()- 30);
                // $("#mainframe").css({"height" : $(window).height() - 5 + "px"});
             },100);
         });
        $("#navigation").height($(window).height()- 30);
        $("#mainframe").css({"height" : $(window).height()-30+ "px"}); 
     });
</script>
	</body>


</html>
