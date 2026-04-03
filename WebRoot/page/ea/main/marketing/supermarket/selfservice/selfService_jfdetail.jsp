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
<title>购物卡账单查询</title>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/marketing/supermarket/selfService_jfdetail.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/style-csp.css">


<script>
        var basePath = "<%=basePath%>";
        var pagenumber=0;
        var pagecount=0;
        var sccid = "${param.sccId}";
        var  bpoint =  "${bp.bonusPointScore}";
        var posNum = "${param.posNum}";
		var ccompanyID = "${ccompanyID}"
</script>

<body>
<section class="code-pay zd">
	<!--扫码支付内容-->
	<article>
		<!--头部-->
		<header>
			<img src="<%=basePath%>images/supermarket/zd.png" alt="">
			<p>账单、积分、佣金<br/>快捷、方便、查看<br/></p>
		<span class="btn">
			<a href="<%=basePath%>ea/sm/ea_integralTopup.jspa?sccId=${param.sccId}&staffName=${param.staffName}&posNum=${posNum}&ccompanyID=${ccompanyID}"><input style="position: static;" type="button" value="充值"></a>
			<input style="position: static;" type="button" value="返回" id="backhome">
		</span>
		</header>
		<!--头部 end-->
		<!--内容-->
		<figure>
			<div class="tab">
				<table width="900">
					<caption style="line-height: 65px;margin: 35px 0;text-align: left;"><span >用户：${param.staffName}</span><span>总金额：&yen;<em class="mem">${bp.bonusPointScore/100}</em>元</span><br><span>折合积分数：<em class="jfem">${bp.bonusPointScore}</em></span></caption>
					<thead>
					<tr>
						<th style="width: 50%;" class="jf">积分获赠</th>
						<th style="width: 25%;text-indent: -30px;">积分数量</th>
						<th style="width: 25%;text-indent: -30px;">明细时间</th>
					</tr>
					</thead>
				</table>
				<div class="tab-box">
					<table width="900">
						<tbody id="jifen">
						<%--<tr>--%>
							<%--<td class="jf">心心相印—客户积分佣金</td>--%>
							<%--<td>+20</td>--%>
							<%--<td>2018-10-20</td>--%>
						<%--</tr>--%>

						</tbody>
					</table>
				</div>
			</div>
		</figure>
		<!--内容 end-->
	</article>
	<!--扫码支付内容 end-->
</section>

</body>
</html>