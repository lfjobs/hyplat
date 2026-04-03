<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String backurl=request.getParameter("backurl");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<script src="<%=basePath%>js/jquery-1.6.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/websitemall/card/android/personalInformation_address_list.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/websitemall/card/manage_address.css"/>

<title>管理收货地址</title>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	var editType="${editType}";
	var backurl="<%=backurl%>";
    var flag="${param.flag}";
</script>
</head>

<body>
<div class="main">
	<div class="top">
        	<ul>
            	<li class="arrow"><a href="javascript:;"><img src="<%=basePath%>images/ea/websitemall/card/arrow.png"/></a></li>
            	<li>管理收货地址<input type="hidden" name="staffId" id="staffId" value="${staffId}"></li>
            	<li><a href="javascript:;"><img src="<%=basePath%>images/ea/websitemall/card/dot.png"/></a></li>
                <div class="clear"></div>
            </ul>
    </div>
    <div class="add">
    	<a href="<%=basePath%>page/ea/main/websitemall/card/android/personalInformation_address_add.jsp?staffId=${staffId}&editType=${editType}&backurl=<%=backurl%>&flag=${param.flag}"><button>添加新地址</button></a>
    </div>
    <div class="line"></div>
    <div class="content">
		<div class="new_"></div>
    	<ul>
    		<c:forEach items="${list}" var="l">
				<a href="javascript:;" class="information" id="${l.addressID}">
					<li>
						<div class="left">
							<h3>
								<p class="people address" style="width:60%;overflow:hidden;">收货人：${l.consignee}</p>
								<p class="tel address"  style="width:34%;overflow:hidden;">${l.phone}</p>
							</h3>
							<h6>收货地址：${l.area}${l.addressDetailed}</h6>
							<input type="hidden" class="area" value="${l.area}"/>
						</div>
						<div class="right"><img src="<%=basePath%>images/ea/websitemall/card/tick.png" /></div>
					</li>
				</a>
				<div class="xian"></div>
        	</c:forEach>
            <div class="xian"></div>
            <div class="clear"></div>
        </ul>
    </div>
</div>
</body>
</html>
