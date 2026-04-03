<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<title>现金支付</title>
<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/tailwindcss/tailwindcss-3.4.3.js"></script>
<%--<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/style-csp.css">--%>

<script>
        var basePath = "<%=basePath%>";
		var journalNum = "${param.journalNum}";
		var totalMoney = "${param.totalMoney}";
		var totalNum = "${param.totalNum}";
		var staffID= "${param.staffID}";
        var staffName= "${param.staffName}";

        var token = 0;
		var comID = "${param.comID}";
		var posNum = "${param.posNum}";
		var cardNum = "${param.cardNum}";
		var fh = "${param.fh}";
		var ppid = "${param.ppid}";
		var carmID = "${param.carmID}";

		var paymentName = "${paymentName}"

        $(function(){
            $("#city").click(function (e) {
                SelCity(this,e);

            });
        })
</script>

</head>
<body>

<section>
	<!--扫码支付内容-->
	<article>
		<!--头部-->
		<header class="p-8 flex flex-row  justify-between bg-blue-500 text-white">
			<img src="<%=basePath%>images/supermarket/xxzf.jpg" alt="" class="w-36">
			<div class="flex flex-col space-y-8">
				<div>
					<div class="text-sm">请确认以下收款信息</div>
					<div class="text-2xl">线下支付</div>
				</div>
				<a href="javascript: window.history.go(-1);">
					<button class="bg-white text-black text-sm rounded-lg p-2 pl-4 pr-4">返回</button>
				</a>
			</div>
		</header>
		<!--头部 end-->
		<!--内容-->
		<figure>
			<div class="p-8 space-y-4">
				<div>
					<div class="text-3xl font-bold">应付金额 <span class="text-red-500 font-bold">￥${param.totalMoney}</span>（共<span class="text-red-500 font-bold">${param.totalNum}</span>件）</div>
				</div>

				<div class="border border-top-1 border-gray-100"></div>

				<div class="text-1xl">收款信息</div>
				<div class="text-2xl">
					<div class="text-2xl text-gray-500">收款方式
						<span class="text-black">${param.paymentName}</span>
					</div>
					<c:choose>
						<c:when test="${param.paymentType == 'offline_bank' }">
							<div class="flex flex-row  space-x-2">
								<div class="text-2xl text-gray-500">银行名称</div>
								<div class="text-2xl text-black">${param.params.split(":")[0]}</div>
							</div>
							<div class="flex flex-row  space-x-2">
								<div class="text-2xl text-gray-500">银行卡号</div>
								<div class="text-2xl text-black">${param.params.split(":")[1]}</div>
							</div>
						</c:when>
						<c:otherwise>
							<img style="margin: auto" src="${param.params}">
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</figure>
		<!--内容 end-->
	</article>
	<!--扫码支付内容 end-->
</section>

<div class="fixed left-0 bottom-0 w-full">
	<div class="m-2">
		<button class="w-full bg-red-600 text-white rounded-lg p-4 font-bold" onclick="offlinePay()">
			确认
		</button>
	</div>
</div>
<script language="javascript" type="text/javascript">

	function offlinePay() {
		var url = basePath + "/ea/sm/sajax_ea_offlinePayOrder.jspa";

		var data = {
			comID: comID,
			totalMoney: totalMoney,
			journalNum: journalNum,
			ppid: "${param.payId}"
		}
		console.log(data)
		$.ajax({
			type: "POST",
			url: url,
			async: true,
			dataType: "json",
			data: data,
			success: function (data) {
				console.log("线下支付成功");
				document.location.href = basePath + "ea/sm/ea_viewOrderDetailOffline.jspa?journalNum=" + journalNum + "&posNum=" + posNum + "&payId="+"${param.payId}";
			},
			error: function (data) {
				console.log("线下支付失败");
			}
		});
	}

</script>
</body>
</html>