<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html lang="en">
<head>
<meta charset='utf-8'>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
<script src="<%=basePath%>js/jquery-2.1.1.min.js"></script>
<script src="<%=basePath%>js/font-size.js"></script>
<link rel="stylesheet"
	href="<%=basePath%>css/ea/office_ea/makeApp/parkingpay.css">
<title>选择车牌号</title>
<script type="text/javascript">
        var basePath="<%=basePath%>";
        var staffID = "${param.staffID}";
        var equip = "${param.equip}";
        var carNum = "${param.carNum}";
        var status = "${param.status}";

        $(function(){
            $(".alert_w input").click(function() {
                $(".alert_w_").hide();
            })

        })

 </script>
	<script type="text/javascript">
	function back() {
		document.location.href = basePath+"ea/restaurant/ea_scancode.jspa?scancode=09f&carNum="+carNum+"&equip="+equip+"&status="+status;
	}
	function add() {
		document.location.href = basePath
				+ "page/WFJClient/ShopOwner/parking/parkingPay.jsp?newAdd=new&equip="+equip+"&status="+status;
	}



	function select(carNum) {

		var url = basePath + "/ea/mappointment/sajax_ea_validateCarNum.jspa";
		$.ajax({
			url : encodeURI(url),
			type : "post",
			data : {
				carNum : carNum,
                status:status,
                equip:equip
			},
			dataType : "json",
			async : false,
			success : function(data) {
				var member = eval("(" + data + ")");
				var result = member.result;
				if (result == "2"||result == "0"||result == "3"||result == "4"||result == "1") {
					document.location.href = basePath
							+ "/ea/mappointment/ea_parkingIndex.jspa?carNum="
							+ carNum+"&staffID="+staffID+"&equip="+equip+"&status="+status;
				} else {
					var tipcontent = "";
					if (result == "0") {
						tipcontent = "不存在出入记录,请确认正确车牌";
					} else if (result == "1") {
						tipcontent = "只有进入记录没有出记录，请识别车牌";
					} else if (result == "3") {
						tipcontent = "没有进入记录";
					} else if (result == "4") {
						tipcontent = "记录有误差请联系工作人员";
					}
					$(".tipcontent").text(tipcontent);

					$(".alert_w_").show();
					$(".alert_w").show();
				}
			}
		});
	}
</script>

</head>

<body class="lic">
	<ul class="header">
		<li><img src="<%=basePath%>images/parkkingpay/ico-left.png"
			alt="" onclick="back();">
		</li>
		<li>选择车牌号</li>
	</ul>
	<div class="lic_con">
		<ul>
			<c:forEach items="${carlist}" var="item">

				<li onclick="select('${item}')">车牌号：${item}</li>
			</c:forEach>

		</ul>
		<input type="button" value="新增" onclick="add()">
	</div>
	<div class="alert_w_">
		<div class="alert_w">
			<h4>温馨提示</h4>
			<p class="tipcontent">该车牌没有进入记录，请确定正确的车牌号。</p>
			<input type="button" value="确定">
		</div>
	</div>
</body>


</html>
