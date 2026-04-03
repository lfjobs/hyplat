<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>会员二维码分享</title>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
	<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/resest.css">
	<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/mem_center.css">
	<script type="text/javascript" src="<%=basePath%>/js/WFJClient/zepto.min.js"></script>
</head>

<body>
    <!-- header start  -->
    <header class="mem_header">
        <a href="<%=basePath%>/ea/consignee/ea_toVipCenter.jspa?sccid=${sccid}" class="back"></a>
        <h1>会员二维码分享</h1>
    </header>
    <!--  header end  -->
    <!-- 页面内容 start  -->
    <div class="wrap_page">
        <!--    头像信息  start   -->
        <section>
            <div class="head_img head_small">
                <img src="<%=basePath%><c:if test='${cuscom.state=="1"}'><s:if test="staff.headimage!=null">${staff.headimage}</s:if><s:else>images/contacts/Network/defaultbig.png</s:else></c:if><c:if test='${cuscom.state=="2"}'><s:if test="contactCompany.logoPath!=null">${contactCompany.logoPath}</s:if><s:else>images/contacts/recepit/wfj_huiyuan_01.png</s:else></c:if>">
            </div>
            <div class="head_name head_txt_s">
				<c:if test='${cuscom.state=="1"}'>${staff.staffName } </c:if>
                <c:if test='${cuscom.state=="2"}'>${contactCompany.cresponsible }</c:if>
			</div>
            <div class="head_type head_txt_ss">
				<c:if test='${cuscom.cusType=="0"}'>中联园区平台</c:if>
                <c:if test='${cuscom.cusType=="0.5"}'>国税</c:if>
                <c:if test='${cuscom.cusType=="1"}'>地税</c:if>
               	<c:if test='${cuscom.cusType=="2"}'>公司商城业主会员</c:if>
               	<c:if test='${cuscom.cusType=="3"}'>合伙创业商城业主会员</c:if>
               	<c:if test='${cuscom.cusType=="4"}'>微分金商城业主会员</c:if>
               	<c:if test='${cuscom.cusType=="5"}'>代理商商城业主会员</c:if>
               	<c:if test='${cuscom.cusType=="6"}'>Vip客户</c:if>
               	<c:if test='${cuscom.cusType=="7"}'>普通客户</c:if>
			</div>
        </section>
        <!--    头像信息 end    -->
        <section>
            <div class="QR_share">
                <span class="plam_type">
					<s:if test="productPackaging!=null">${productPackaging.goodsName}经济联营平台</s:if><s:else>中联园区平台</s:else>
				</span>
                <a href="<%=basePath%>ea/vipcenter/ea_Platform.jspa?staffid=${cuscom.staffid}&sccid=${cuscom.sccId}&type=2" class="QR_togglebtn">切换平台<i></i></a>
            </div>
        </section>
        <hr class="QR_hr">
        <section class="QR_share_wrap">
            <div class="QR_share_box">
            	<img src="${qrcode }" alt="">
            </div>
            <p>扫二维码，交换名片，加入微分金</p>
        </section>
    </div>
    <!--  页面内容 end -->
	<script>
		
	</script>
	
    <script>
        window.onload = window.onresize = function() {
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //通过屏幕宽度去设置不同的后台根字体的大小
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
        }
    </script>
</body>
</html>