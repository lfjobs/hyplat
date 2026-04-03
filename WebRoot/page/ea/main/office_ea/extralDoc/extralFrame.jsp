<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="x-ua-compatible" content="ie=8" />
		<title>投诉流转</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>js/jquery-1.3.1.js" type="text/javascript"></script>

		<script src="<%=basePath%>js/ea/office_ea/extralDoc/extralFrame.js"></script>
		<link rel="STYLESHEET" type="text/css"
			href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
		<link rel="stylesheet"
			href="<%=basePath%>js/tree/common/css/style.css" type="text/css"
			media="screen" />
		<link rel="stylesheet"
			href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" type="text/css" />
		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>

		<script type="text/javascript">
   
          var basePath='<%=basePath%>'; 

              
          
   </script>

		<style type="text/css">
ul.draft li a {
	cursor: hand;
	font-size: 14px;
}

ul.draft li a.finish {
	margin-left: 35px;
	font-size: 12px;
}

a:hover {
	text-decoration: none;
}

#deal {
	padding-left: 30px;
	width: 220px;
	background: #FFFFFF;
	height: 432px;
	border-top: #DAE7F6 20px solid;
	border-right: #DAE7F6 2px solid;
	border-bottom: #DAE7F6 2px solid;
}

.draft {
	padding-top: 30px;
}

li {
	list-style: none;
}

.hr0 {
	background-color: #DAE7F6;
	width: 90%;
	height: 4px;
	margin-left: 10px;
	margin-bottom: 10px;
}

#webname {
	text-align: left;
	color: #15428b;
	font-size: 15px;
	margin: -20px 8px 10px 10px;
}
</style>


	</head>
	<body>

		<table width="100%" cellspacing="0" cellpadding="0"
			style="border-top: #DAE7F6 5px solid;">
			<tr>
				<td style="width: 220px;">
					<div id="deal">
						<div id="webname">
							<b>网站投诉箱</b>
						</div>

						<ul class="draft">
							<li>
								<img src="<%=basePath%>images/complaint/letter.png" width="25"
									height="20" />
								<b> <a href="#" onclick="tonclick('draft')">投诉处理箱<span
										id="result1"></span> </a> </b>
							</li>
							<li>
								&nbsp;
							</li>
							<li>
								<a class="finish" href="#" onclick="tonclick('dealed')">已处理箱</a>

							</li>

						</ul>
						<div class="hr0">
						</div>
						<div>
							<ul class="draft">
								<li>
									<img src="<%=basePath%>images/complaint/redgou.png" width="25"
										height="20" />
									<b><a href="#" onclick="tonclick('approvalno')">未审批投诉<span
											id="result2"></span> </a> </b>
								</li>
								<li>
									&nbsp;
								</li>
								<li>
									<a class="finish" href="#" onclick="tonclick('approvalye')">已审批投诉</a>

								</li>

							</ul>

							<ul class="draft">
								<li>
									<img src="<%=basePath%>images/complaint/stamp2.png" width="15"
										height="20" />
									<b><a href="#" onclick="tonclick('stampno')">未盖章投诉<span
											id="result3"></span> </a> </b>
								</li>
								<li>
									&nbsp;
								</li>
								<li>
									<a class="finish" href="#" onclick="tonclick('stampye')">已盖章投诉</a>

								</li>

							</ul>

							<ul class="draft">
								<li>
									<img src="<%=basePath%>images/complaint/set.gif" width="20"
										height="20" />

									<b><a href="#" onclick="tonclick('set')">设置</a> </b><span
										style="display: inline" id="a"></span>
								</li>

							</ul>

						</div>
					</div>
				</td>
				<td>
					<iframe
						src='<%=basePath%>page/ea/main/office_ea/extralDoc/index.jsp'
						id="mainframe"
						style="PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; height: 455px; WIDTH: 100%; PADDING-TOP: 0px;"
						name="admin" scrolling="no" frameBorder="0"></iframe>



				</td>
			</tr>

		</table>
		<script type="text/javascript">
     $(function(){
         $(window).resize(function(){
             setTimeout(function () {                 
                 $("#deal").height($(window).height()- 30);
                 $("#mainframe").css({"height" : $(window).height() - 5 + "px"});
             },100);
         });
         $("#doctree").height($(window).height()- 30);
         $("#mainframe").css({"height" : $(window).height() - 5 + "px"}); 
     });
</script>
	</body>

</html>
