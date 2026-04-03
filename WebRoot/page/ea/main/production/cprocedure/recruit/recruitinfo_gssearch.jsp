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

<title>公司招聘搜索</title>


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
<div id="myModal" class="modal">
	<div class="modal-content">
		<span class="close">&times;</span>
		<p>招聘业务正在完善中，暂不支持。</p>
		<button id="confirmBtn">确认</button>
	</div>
</div>
	<body>
	<header class="fixed">
	<div class="header">
		<ul>
		   		
		    <li class="arrar" style="width:2%;">
				&nbsp;
			</li>
			<li style="width: 18%;" class="dropdown">
			<span class="dropdown-toggle" data-toggle="dropdown"><span class="toggle_text" style="font-size: 0.8rem;">找工作</span><span class="caret"></span></span>
					<ul class="dropdown-menu">
				         <li><a href="#">找工作</a></li>
				         <li><a href="#">找人才</a></li>
				      </ul>
				      </li>
			<li class="header_c" style="width: 68%;">
				<span class="header_c_tet"><img src="<%=basePath%>images/ea/recruit/search999.png"/><span></span></span>
				<div class="search_k">
				<input type="search" name="" id="search" placeholder="搜索职位/公司" value="" />
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
	< class="gong_search_main">
<form id="searchForm" name="searchForm" method="post">
	<input type="submit" style="display:none;" name="submit"/>
	<input type="hidden"  name="searchCriteria.industry" id="industry"/>
	<input type="hidden"  name="searchCriteria.position" id="pos"/>
	<input type="hidden"  name="searchCriteria.city" id="citys"/>
    <input type="hidden"  name="searchCriteria.keyword" id="keyword"/>
	<input type="hidden"  name="type" value="gs"/>
		<div class="search_top">
			<ul class="list-group">
				<li class="list-group-item hangye">
					<span style="vertical-align: middle;">行业类别</span>
					<p class="pull-right">
					<img class="text-right" src="<%=basePath%>images/ea/recruit/wfj_return_07.png" alt="" />
					</p>
					<span class="xx"  id="hangye"></span>
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

		</div>
		<div class="search_bot">
			<ul class="list-group">
			 <c:forEach items="${historylist}" var="item"  varStatus="status">
				
				
				<li class="list-group-item">
					<img src="<%=basePath%>images/ea/recruit/time_03.png" alt="" />
					<span style="vertical-align: middle;">
					${item.keyword3}
					<c:if test='${item.keyword2!=""&&item.keyword2!=null&&item.keyword3!=""&&item.keyword3!=null}'>
					 <c:if test='${item.keyword2!=""&&item.keyword2!=null}'> +</c:if>
					</c:if>
					${item.keyword2}
					<c:if test='${(item.keyword3!=""&&item.keyword3!=null)||(item.keyword2!=""&&item.keyword2!=null)}'>
					<c:if test='${item.keyword1!=""&&item.keyword1!=null}'> +</c:if>
					</c:if>
					${item.keyword1}</span>
				</li>
			
			
			</c:forEach>	
			</ul>
			<p class="clear_time">清空搜索记录</p>
		</div>
</form>
	</div>

	</body>
</html>
<script>
	// 获取弹框元素
	var modal = document.getElementById('myModal');
	// 获取关闭按钮元素
	var closeBtn = document.getElementsByClassName('close')[0];
	// 获取确认按钮元素
	var confirmBtn = document.getElementById('confirmBtn');

	// 显示弹框
	function showModal() {
		if (typeof Android !=='undefined'){
			modal.style.display = "block";
		}else {
			modal.style.display ="none"
		}
	}

	// 点击关闭按钮时关闭弹框
	closeBtn.onclick = function () {
		window.history.back();
	}

	// 点击确认按钮时关闭弹框
	confirmBtn.onclick = function () {
		window.history.back();
	}

	// 点击弹框外部区域时关闭弹框
	window.onclick = function (event) {
		if (event.target === modal) {
			window.history.back();
		}
	}

	// 示例：页面加载完成后显示弹框
	window.onload = function () {
		showModal();
	}
</script>
<!-- ... existing code ... -->
<!-- ... existing code ... -->
<style>
	/* 弹框背景 */
	.modal {
		display: none;
		position: fixed;
		z-index: 1;
		left: 0;
		top: 0;
		width: 100%;
		height: 100%;
		overflow: auto;
		background-color: rgba(0, 0, 0, 0.4);
	}

	/* 弹框内容 */
	.modal-content {
		background-color: #fefefe;
		margin: 15% auto;
		padding: 20px;
		border: 1px solid #888;
		width: 30%;
		text-align: center;
	}

	/* 关闭按钮 */
	.close {
		color: #aaa;
		float: right;
		font-size: 28px;
		font-weight: bold;
	}

	.close:hover,
	.close:focus {
		color: black;
		text-decoration: none;
		cursor: pointer;
	}

	/* 确认按钮 */
	#confirmBtn {
		background-color: #4CAF50;
		color: white;
		padding: 10px 20px;
		border: none;
		cursor: pointer;
	}

	#confirmBtn:hover {
		background-color: #45a049;
	}
</style>