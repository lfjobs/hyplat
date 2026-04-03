<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>搜索</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/collage/phl/phl_prosearch.css">
	<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		var ccompanyID = "${ccompanyID}";
        var basePath="<%=basePath%>";
		$(function(){
            $('#search').keydown(function(event){
                if(event.keyCode==13){
                    var search  = $.trim($("#search").val());
                    var term = $("#search").attr("placeholder");
                    if(search==""&&term!="搜索商品"){
                       search = term;
                    }

                    try {
                        var u = window.navigator.userAgent;
                        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
                        if (isAndroid == true) {
                            console.log("安卓");
                            var collection = Android.keyboardHide();//调用安卓接口

                        }
                    }catch(error){

                    }
                    document.location.href = basePath+"page/ea/collage/phl/phl_product.jsp?ccompanyID="+ccompanyID+"&goodsName="+search
                }
            });

		});

	</script>
</head>
<body>
<header>
	<section>
		<form action="" onsubmit="return false;">
		<input type="search" name="" id="search" placeholder="${param.term eq null?'搜索商品':param.term}" />
		</form>
	</section>
</header>

<section>
	<p>
		热门搜索
	</p>
	<ul>
		<c:forEach items="${hotlist}" var="item">
		<li>
			<a href="<%=basePath%>page/ea/collage/phl/phl_product.jsp?ccompanyID=${ccompanyID}&cateCrit=${item[0]}&cateName=${item[1]}">
				${item[1]}
			</a>
		</li>
		</c:forEach>

	</ul>
</section>

</body>
</html>

