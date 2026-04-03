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
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/drive/resest1.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/drive/mo_student.css">
    <script type="text/javascript" src="<%=basePath%>js/ea/production/resume/resumeManagement/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/drive/jx_learnCarMakeMone.js"></script>
    <title>学车赚钱</title>
</head>

<body>
    <!-- header 开始  -->
    <header class="mem_header">
        <a href="javascript:history.back(-1)" class="back"></a>
        <h1>学车赚钱</h1>
    </header>
    <!--  header 结束  -->
    <!-- 页面内容 开始  -->
    <div class="wrap_page">
        <section class="makemoney_wrap">
            <div class="mm_head">
                <div class="mm_headimg outer_img">
                    <img src="<%=basePath%>${map.userList[0] }" alt="">
                </div>
                <div>${map.userList[1] }</div>
                <div class="mm_type">
                	<c:choose> 
                	   <c:when test="${map.userList[3]==0 }">
					   		 平台  
					   </c:when> 
					   <c:when test="${map.userList[3]==1 }">
					   		 税务  
					   </c:when>
					   <c:when test="${map.userList[3]==2 }">
					   		公司
					   </c:when> 
					   <c:when test="${map.userList[3]==3 }">
					   		合伙人商城业主会员
					   </c:when> 
					   <c:when test="${map.userList[3]==4 }">
					   		微分金商城业主会员
					   </c:when> 
					   <c:when test="${map.userList[3]==5 }">
					   		代理商商城业主会员
					   </c:when> 
					   <c:when test="${map.userList[3]==6 }">
					   		vip客户
					   </c:when> 
					   <c:when test="${map.userList[3]==7 }">
					   		普通客户
					   </c:when>   
					   <c:otherwise>  
					   		未知
					   </c:otherwise>  
					</c:choose> 
                </div>
            </div>
            <div class="gold_wrap">
                <a href="###" class="gold_box">
                    <div class="gold_num">
                    	<c:if test="${map.goldBalance==null}">0</c:if>
                    	<c:if test="${map.goldBalance!=null}">${map.goldBalance}</c:if>
                    </div>
                    <div>金币余额</div>
                </a>
            </div>
            <div class="mm_notice_wrap clearfix">
                <i></i>
                <div class="notice_scroll">
                    <ul class="mm_notice_box">
                    	<c:forEach items="${map.goldNoticeList }" var="d">
                    		<li>恭喜${d[1]}获得${d[0]}枚金币</li>
                    	</c:forEach>
                    </ul>
                </div>
            </div>
            <hr class="mm_hr">
            <div class="mm_list_wrap">
            </div>
        </section>
    </div>
    <!--  页面内容 结束 -->
    <script>
    	var basePath = '<%=basePath%>';
    	var staffId = '${map.userList[2] }';
    	var pageNumber;
    	var pageCount;
		    	
        window.onload = window.onresize = function() {
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //通过屏幕宽度去设置不同的后台根字体的大小
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
        }
        $(function() {
            //金币公告滚动
            var $box = $(".mm_notice_box");
            var n = $box.find("li").length * 2.2;
            var a = 0;
            $(".mm_notice_box li").first().clone().appendTo(".mm_notice_box");

            setInterval(function() {
                noticeScroll();
            }, 2800)

            function noticeScroll() {
                if (a == n) {
                    $box.css("top", 0);
                    a = 2.2;
                } else {
                    a += 2.2;
                }
                $box.animate({
                    top: "-" + a + "rem"
                }, 1000);
            }
        })

    </script>

</body>

</html>
