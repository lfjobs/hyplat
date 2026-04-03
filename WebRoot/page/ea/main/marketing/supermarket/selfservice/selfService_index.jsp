<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<title>微分金自助收银机</title>
<script type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/keyboard/vk_loader.js?vk_layout=CN%20Chinese%20Simpl.%20Pinyin&vk_skin=flat_gray" ></script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/style-cs.css">
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/keyboard2.css">
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/marketing/supermarket/selfService_index.js" type="text/javascript"></script>
<script language="javascript" src="<%=basePath%>js/ea/marketing/supermarket/LodopFuncs.js"></script>
<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
	<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</object>

<script type="text/javascript">
		var basePath = "<%=basePath%>";

	</script>


</head>
<body>

	 <section class="code-settle">
		 <!--扫码支付内容-->
		 <article>
			 <!--头部-->
			 <header>
				 <ul>
					 <li><img src="<%=basePath%>images/supermarket/1.png" alt=""></li>
					 <li><img src="<%=basePath%>images/supermarket/2.png" alt=""></li>
					 <li><img src="<%=basePath%>images/supermarket/3.png" alt=""></li>

				 </ul>
				 <span style="position: absolute;bottom: 6.66%;right: 7.7%;">
					 <a href="<%=basePath%>ea/sm/ea_openCard.jspa"><input style="position: static;" type="button" value="开通购物卡"></a>
					 <a href="javascript:backLogin()"><input style="position: static;" type="button" value="返回登陆"></a>
				 </span>
			 </header>
			 <!--头部 end-->
			 <!--内容-->
			 <figure>
				 <img src="<%=basePath%>images/supermarket/141.png" alt="" class="cen">
				 <div class="btn">
					 <input type="text"  class="barcode"  value="" placeholder="扫描或手动输入条码" id="ipt" onclick='test(this);' autofocus >
					 <%--onBlur="VirtualKeyboard.toggle('txt_Search','softkey');"--%>
					 <input type="button" value="确定" id="btn">


				 </div>
			 </figure>
			 <div id="softkey"></div>
			 <!--内容 end-->
		 </article>
		 <!--扫码支付内容 end-->
	 </section>

	 <div class="alert_weigh_">
		 <div class="alert_weigh">
			 <p><span>3</span>秒后自动关闭</p>
			 <h2 class="tipcon">此卡无效可以联系工作人员</h2>
			 <input type="button" value="确定" id="confirm">
		 </div>
	 </div>

	 <!--交接班弹框 2019.1.25-->
	 <div class="alert_hand_">
		 <div class="alert_address alert_hand">
			 <img src="<%=basePath%>images/supermarket/ico-del-2.png" alt="" id="del">
			 <h1>交接班</h1>
			 <ul class="text jjb">
				 <li>店名：<span class="companyName">溯源互帮超市</span></li>
				 <li>收银员：<span class="staffcode">1001</span></li>
				 <li>POS终端：<span class="pos">0001</span></li>
				 <li>交接班时间：<span class="changeDate"><fmt:formatDate value="<%=new Date()%>"  pattern="yyyy-MM-dd HH:mm:ss" /></span></li>
				 <li>交班地址：<span class="companyAddress">四川省成都市锦江区龙启小区1709</span></li>
			 </ul>
			 <div class="btn">
				 <input type="button" value="交接班并登出" style="width:98%;">
			 </div>
		 </div>
	 </div>
	 <!--交接班弹框 2019.1.25-->
	 <div class="alert_hand-pd_">

		 <div class="alert_hand-pd alert_hand-pd-1">
			 <form action="" onsubmit="return check(this)" method="post" autocomplete=off>
				 <img src="<%=basePath%>images/supermarket/ico-del-2.png" alt="" class="del">
				 <div class="text">
					 <p>输入收银员密码</p>
					 <input type="password" value="" placeholder="请输入密码" onclick='test(this);' id="psd" autocomplete=off>
				 </div>
				 <input type="submit" value="确定" id="cfmpsw">
			 </form>
		 </div>

		 <div class="alert_hand-pd alert_hand-pd-2">
			 <%--<img src="<%=basePath%>images/supermarket/ico-del-2.png" alt="" class="del">--%>
			 <div class="text">
				 <p>交接班完成</p>
				 <input type="text" value="" placeholder="输入打印份数默认为1" id="printNum" onclick='test(this);'>
			 </div>
			 <div class="btn">
				 <input type="button" value="再打印" class="printAgain">
				 <input type="button" value="直接退出" class="right exit">
			 </div>

		 </div>
	 </div>
</body>
</html>