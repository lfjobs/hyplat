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
<title>切换平台</title>
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
        <div class="back"></div>
        <h1>切换平台</h1>
    </header>
    <!--  header end  -->
    <!-- 页面内容 start  -->
    <div class="wrap_page">
        <section class="plam_list">
        	<c:forEach items="${list }" var="list" varStatus="st">
        		<div class="plam_box">
        		<input id="sccid" value="${list[0] }" style="display:none" />
                <div class="m_list_L sign_blue"></div>
                <div class="m_list_M flex_1">
					<span>${list[5]}</span>
	                
					所在平台：
					<c:if test="${list[3]==null }">
                    	<small>软件网络计算机联营平台</small>
                    </c:if>
                    <c:if test="${list[3]!='' }">
                    	<small>${list[3] }</small>
                    </c:if>
                </div>
                <c:if test="${list[0] ==cuscom.sccId}">
                	<div class="m_list_R plam_cur">&nbsp;</div>
                </c:if>
                
            </div>
        	</c:forEach>
        </section>
    </div>
    <!--  页面内容 end -->
    <script>
    	var basePath='<%=basePath%>';
    	var sccid="${sccid }";
    	var staffid="${staffid }";
    	var type="${type }";
    	var flag = "${flag}";
    	var miniSystemJudge='${param.miniSystemJudge}';
    	$(".plam_box").click(function(){
    		var sccid=$(this).find("#sccid").val();
    		if(type==1){
    			var url = basePath +"ea/vipcenter/ea_changeToConWealth.jspa?sccid="+sccid+"&flag="+flag;
    		}
    		if(type==2){
    			var url = basePath +"ea/vipcenter/ea_ChangeToQrcode.jspa?sccid="+sccid;
    		}
    		if(type==3){
    			var url = basePath +"ea/qrshare/ea_updateLogin.jspa?sccid="+sccid+"&miniSystemJudge="+miniSystemJudge;
    		}
    		document.location.href = url;
    	});
    	$(".back").click(function(){
    		if(type==1){
    			var url = basePath +"ea/vipcenter/ea_findconWealth.jspa?sccid="+sccid;
    		}
    		if(type==2){
    			var url = basePath +"ea/vipcenter/ea_QRcode.jspa?staffid="+staffid;
    		}
    		if(type==3){
    			var url = basePath +"ea/qrshare/ea_qrshareList.jspa?flag="+flag+"&miniSystemJudge="+miniSystemJudge;
    		}
    		document.location.href = url;
    	});
    </script>
	<script>
	$(document).ready(function(){
		$(".plam_box").each(function(){
			//随机颜色
			var str=$(this).find("span").eq(0).text();
            var str2=(str.substr(0, 1));
            $(this).find("div").eq(0).html(str2);
            var string = "#FF501A,#68B83A,#78CF7F,#53BBCB,#ED837B,#C5E6F9,#1F75BB,#B41717";   //原始数据
            var array = string.split(",");    
            var string1 = "#F7C2B1,#B2E893,#C3EEC7,#C4EBF1,#F5C1BD,#EEF5F9,#AAD0F1,#E4A0A0";   //原始数据
            var array1 = string1.split(",");        //转化为数组
            var iii=Math.round(Math.random()*(array.length-1));
            var col = array[iii];  //随机抽取一个值
            var col1 = array1[iii]; 
            $(this).find(".m_list_L").attr("style","border:2px solid "+col+";color:"+col+";background:"+col1+";"); 
        });
	});
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