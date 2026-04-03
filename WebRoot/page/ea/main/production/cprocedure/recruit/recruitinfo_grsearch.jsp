<!DOCTYPE html>
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

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>

<title>工作搜索</title>


<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/my_css.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/zhaopin.css" />
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/recruit/recruitinfo_search.js"></script>

<script type="text/javascript">

var basePath="<%=basePath%>";
var industys = "${param.industys}";
var pos = "${param.pos}";
var citys = "${param.citys}";


</script>


</head>
	<body>
			<header class="fixed">
	<div class="header">
		<ul>
			<li class="arrar" style="width:2%;">
				&nbsp;
			</li>
			<li style="width: 18%;" class="dropdown">
			<span class="dropdown-toggle" data-toggle="dropdown"><span class="toggle_text" style="font-size: 0.8rem;">找人才</span><span class="caret"></span></span>
					<ul class="dropdown-menu">
				         <li><a href="#">找工作</a></li>
				         <li><a href="#">找人才</a></li>
				      </ul>
				      </li>
			<li class="header_c" style="width: 68%;">
				<span class="header_c_tet"><img src="<%=basePath%>images/ea/recruit/search999.png"/><span></span></span>
				<div class="search_k">
				<input type="search" name="" id="search" placeholder="搜索职位" value="" />
				</div>
			</li>
			<li class="asd" style="text-align: center;right: 0;position: absolute;">
				<div class="header_c_text">
					<a  href="#" class="sousuo"> 搜索</a>
					<a  href="#"class="cancel"> 取消</a>
				</div>
			</li>
		</ul>
	</div>
	<div class="search_down">

   </div>
</header>
<div class="main">
	<div class="gong_search_main">
	<form id="searchForm" name="searchForm" method="post">
	<input type="submit" style="display:none;" name="submit"/>
	<input type="hidden"  name="searchCriteria.industry" id="industry"/>
	<input type="hidden"  name="searchCriteria.position" id="pos"/>
	<input type="hidden"  name="searchCriteria.city" id="citys"/>
    <input type="hidden"  name="searchCriteria.keyword" id="keyword"/>
	<input type="hidden"  name="type" value="gr"/>
		<div class="search_top">
			<ul class="list-group">
				<li class="list-group-item hangye">
					<span style="vertical-align: middle;">行业类别</span>
					<p class="pull-right">
					<img class="text-right" src="<%=basePath%>images/ea/recruit/wfj_return_07.png" alt="" />
					</p>
					<span class="xx" id="hangye"></span>
				</li>
				<li class="list-group-item zhiwei">
					<span style="vertical-align: middle;">职位类别</span>
					<p class="pull-right">
					<img class="text-right" src="<%=basePath%>images/ea/recruit/wfj_return_07.png" alt="" />
					</p>
					<span class="xx" id="poss"></span>
				</li>
				<li class="list-group-item city">
					<span style="vertical-align: middle;">工作地点</span>
					
					<p class="pull-right">
					<img class="text-right" src="<%=basePath%>images/ea/recruit/wfj_return_07.png" alt="" />
					</p>
					<span class="xx" id="workplace"></span>
				</li>
			</ul>
			<div class="big_search">搜索</div>
			</form>
		</div>
		<div class="ge_search_bot">
			<p>最近搜索</p>
			<ul>
			 <c:forEach items="${historylist}" var="item">
				<li>
					<div>${item.keyword2}</div>
				</li>
		   </c:forEach>
			</ul>
			<p>热门搜索</p>
			<ul>
			 <c:forEach items="${hotlist}" var="item">
				<li>
					<div>${item}</div>
				</li>
			 </c:forEach>	
			</ul>
		</div>
	</div>
	</div>

	</body>
</html>
