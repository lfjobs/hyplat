<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

 


<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/WFJClient/bootstrap.css"></link>
	<link href="<%=basePath%>css/contacts/Restaurant/style13.css" rel="stylesheet"
		type="text/css" />
		<link href="<%=basePath%>css/contacts/Restaurant/xiugai.css" rel="stylesheet"
		type="text/css" />
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/WFJClient/bootstrap.js"></script>
	<%-- <script type="text/javascript" src="<%=basePath%>js/restaurant/xiugai.js"></script> --%>
<script type="text/javascript" src="<%=basePath%>/js/WFJClient/toucher.js"></script>
	<script type="text/javascript">
	
	var companyId='${companyId}';
    var ccompanyId='${ccompanyId}';
	var basePath='<%=basePath%>';
	var producttype = "${productDesign.type}";
</script>
<body>
	<div class="top">
		<ul>
			<li class="arrow" style="padding-left: 5px">
				<div>
					<img src="<%=basePath%>images/contacts/comments/wfj_return_02.png" />
				</div></li>
			<li class="text-center">${param.baojian}</li>
		</ul>
	</div>
	<div class="box">
		<div class="wfj_same_hidden">
			<div class="wfj_same_auto">
				<div id="carousel-example-generic" class="carousel slide both"
					data-ride="carousel">
					
					<!-- 轮播（Carousel）项目 -->
					<div class="carousel-inner" role="listbox" id="div1">
						<c:forEach items="${list}" var="ip">
							<div class="item">
								<img src="<%=basePath%>${ip[1]}" >
							</div>
						</c:forEach>
					</div>

					<a id="carleft" class="carousel-control left"
						href="#carousel-example-generic" data-slide="prev"></a> <a
						id="carright" class="carousel-control right"
						href="#carousel-example-generic" data-slide="next"></a>
				</div>
				<div class="xiangq_main">
					<div class="xq_header">
						<span id="remarkto"></span>
					</div>
					<div class="xq_header2">
						<span>-----</span><span>包间介绍</span><span>-----</span>
					</div>
					<div class="xq_jieshao">
						
						<p>${name}</p>
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="huaixin_footer">
		<span class="noxuan">
			<span class="xuan_yes">确认选择</span>
	</span>
	</div>
	<script>
	var basePath="<%=basePath%>";
		$(function() {
			$(".arrow").click(function() {
				history.go(-1)
			})
			
			var remark = $("#remark").val();
			
			$("#remarkto").val(remark);
		$(".xuan_yes").click(function() {
				var privateRoom=$(".text-center").html();
				var url = basePath + "ea/restaurant/ea_getChoice.jspa?ajax=pp&companyId="+companyId+"&ccompanyId="+ccompanyId+"&privateRoom="+privateRoom;
				document.location.href = url;
			})
		
			//轮播图追加样式
			$("#div1").find("div").eq(0).addClass("active");
			$(".box").css("height",$(window).height() - $(".top").height()- $(".huaixin_footer").height() + "px")
		})
		var myTouch = util.toucher(document
				.getElementById('carousel-example-generic'));
		$("#carright").css("background", "transparent")
		$("#carleft").css("background", "transparent")
		myTouch.on('swipeLeft', function(e) {
			$('#carright').click();
		}).on('swipeRight', function(e) {
			$('#carleft').click();
		});
	</script>
</body>
</html>