<!DOCTYPE html>
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

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/resest.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/mem_center.css">
    <script type="text/javascript" src="<%=basePath%>js/ea/production/resume/resumeManagement/zepto.min.js"></script>
    <script src="<%=basePath%>js/ea/production/cprocedure/recruit/details.js" type="text/javascript"
            charset="utf-8"></script>
 <title>详情</title>

<script type="text/javascript">
	var basePath="<%=basePath%>";
	var staffid = "${param.staffid}";
	var tpId = "${param.tpId}";
	var type="${param.type}";
	
</script>
</head>
<body>
    <!-- header start  -->
    <header class="mem_header">
        <a id="returnClick" class="back"></a>
        <h1>职位详情</h1>
    </header>
    <!--  header end  -->
    <!-- 页面内容 start  -->
    <div class="wrap_page">
        <section class="job_det_wrap">
            <div class="jdet_top">
                <div class="jdet_top_tit clearfix">
                    <span>${obj2[5]}</span>
                    <span>[${obj2[4]}]</span>
                </div>
                <div class="jdet_top_m clearfix">
                    <span class="jdet_ico01 jdetico_wrap">
                        <i></i><span>${obj2[0]}</span>
                    </span>
                    <span class="jdet_ico02 jdetico_wrap">
                        <i></i><span>${obj2[1]}</span>
                    </span>
                    <span class="jdet_ico03 jdetico_wrap">
                        <i></i><span>${obj2[2]}</span>
                    </span>
                    <span class="jdet_ico04 jdetico_wrap">
                        <i></i><span>${obj2[3]}</span>
                    </span>
                </div>
                
            </div>
            <div class="job_des_wrap">
                <div class="job_des_tit">
                    <i></i><span>职位描述</span>
                </div>
                <div class="job_des_con">
                    岗位描述：
                    <br>${obj2[8]}
                </div>
            </div>
            <div class="det_company" >
               <a href="###" class="flex flex_align_center">
                <div class="det_company_img">
                    <img src="<%=basePath%>${obj2[14]}" alt="">
                </div>
                <span class="flex_1">${obj2[13]}</span>

                </a>
            </div>
            <div class="jdet_infos_wrap">
                <div class="jdet_info_tit word_place">
                    <i></i><span>工作地点</span>
                </div>
                <p>${obj2[9]}</p>
            </div>

            <c:if test="${obj2[15] eq '01'}">
                <div class="jdet_infos_wrap">
                    <div class="jdet_info_tit interviewtime">
                        <i></i><span>面试时间</span>
                    </div>
                    <p>${obj2[6]}</p>
                </div>
            <div class="jdet_infos_wrap">
                <div class="jdet_info_tit interviewplace">
                    <i></i><span>面试地点</span>
                </div>
                <p>${obj2[10]}</p>
            </div>
            <div class="jdet_content clearfix">
                <div class="jdet_content_L">联系方式</div>
                <div class="jdet_content_m">
                    <span>${obj2[11]}</span>
                    <span id="jdet_tell">${obj2[12]}</span>
                </div>
                <a href="###"></a>
            </div>
            </c:if>
        </section>
        <c:if test="${obj2[15] eq '01'}">
        <div class="jdet_btn_wrap">
            <a  onclick="operate('05')" class="no_interview">拒绝面试</a><a onclick="operate('03')" class="yes_interview">接受面试</a>
          </div>
         </c:if>
         <!--已过期 开始-->
          <c:if test="${resumeName=='被查看'}">
        <div class="jdet_btn_wrap">
            <i class="past_due"></i><span>已过期</span>
        </div>
   		</c:if>
        <!--已过期 结束-->
        <!--已接受 开始-->
         <c:if test="${obj2[15]=='03'}">
        <div class="jdet_btn_wrap" >
            <i class="accpet"></i><span>已接受</span>
        </div>
        </c:if>
        <!--已接受 结束-->
        <!--已拒绝 开始-->
         <c:if test="${obj2[15]=='05'}" >
        <div class="jdet_btn_wrap" >
            <i class="refuse"></i><span>已拒绝</span>
        </div>
        </c:if>
    </div>
    <!--  页面内容 end -->
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
