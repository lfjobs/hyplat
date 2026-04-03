<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<title>上传图片</title>

<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no, email=no" />
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<link rel="stylesheet"
	href="<%=basePath%>css/ea/production/scmanage/base.css">
<link rel="stylesheet" href="<%=basePath%>css/ea/production/scmanage/swiper-3.3.1.min.css">
<link rel="stylesheet"
	href="<%=basePath%>css/ea/production/scmanage/sc_manger.css">
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/scmanage/swiper-3.3.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/scmanage/haveuploadpic.js"></script>
<script type="text/javascript">

var basePath="<%=basePath%>";
var pagecount = 0;
var pageSize = 0;
var pagenumber = 0;
var count = 0;
var groupname = "${materialGroup.groupname}";
var mgId = "${materialGroup.mgId}";

</script>
</head>
<body>
    <!-- header start  -->
    <header class="com_head">
        <a href="javascript:history.go(-1)" class="back"></a>
        <h1>${materialGroup.groupname}</h1>
        <a href="<%=basePath%>ea/scm/ea_newOrEditorGroup.jspa?materialGroup.mgId=${materialGroup.mgId}&type=nopic" class="head_R">编辑分组</a>
    </header>
    <div class="wrap_page">
        <a href="<%=basePath%>ea/scm/ea_uploadFileIndex.jspa?materialGroup.mgId=${materialGroup.mgId}&materialGroup.groupname=${materialGroup.groupname}&type=nopic" class="group_btn">上 传 照 片</a>
        <ul class="photos_wrap clearfix">
        <%-- <c:forEach  items="${pageForm.list}" var="item" varStatus="status">
             <c:if test="${fn:length(pageForm.list)-1 eq status.index}">
               <li class="photo_sbox last">
             </c:if> 
             <c:if test="${fn:length(pageForm.list)-1 ne status.index}"> 
                <li class="photo_sbox">
             </c:if> 
                <img src="<%=basePath%>${item.filepath}" alt="">
                <input type="hidden"  value="${status.index+1 }"  class="serialnum"/>
            </li>
         </c:forEach>   --%>
        </ul>
        <div class="load_all">已显示全部</div>
        <!--图片点击放大 开始-->
        <div class="overlay_block" id="overlay">
            <div class="overlay_block_head">
                <a href="javascript:;" class="back_overlay"></a>
                <h1>${materialGroup.groupname}<p style="position:absolute;top:100%;left:0;text-align:center;width:100%"><span class="sx">1</span>/<span class="totalsx"></span></p></h1>
                
            </div>
            <div class="swiper-container gallery-top">
                <div class="swiper-wrapper">
                   <%--  <div class="kelong1" style="background-image:url(<%=basePath%>images/ea/production/scmanage/demo_01.jpg);display:none;">
                        <div class="photo_overlay">
                            <div class="photo_con">
                                <input type="text" placeholder="添加名称…">
                                <div class="photo_fd">
                                    <span class="photo_time">2016年8月17日上传</span>
                                    <a href="###" class="photo_move"></a>
                                    <i class="photo_dele"></i>
                                </div>
                            </div>
                        </div>
                    </div> --%>
                <%--    <div class="swiper-slide" style="background-image:url(<%=basePath%>images/ea/production/scmanage/demo_04.jpg)">
                        <div class="photo_overlay">
                            <div class="photo_con">
                                <input type="text" placeholder="添加名称…">
                                <div class="photo_fd">
                                    <span class="photo_time">2016年8月17日上传</span>
                                    <a href="###" class="photo_move"></a>
                                    <i class="photo_dele"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="swiper-slide" style="background-image:url(<%=basePath%>images/ea/production/scmanage/photo_s03.jpg)">
                        <div class="photo_overlay">
                            <div class="photo_con">
                                <input type="text" placeholder="添加名称…">
                                <div class="photo_fd">
                                    <span class="photo_time">2016年8月17日上传</span>
                                    <a href="###" class="photo_move"></a>
                                    <i class="photo_dele"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="swiper-slide" style="background-image:url(<%=basePath%>images/ea/production/scmanage/photo_s01_200x200.jpg)">
                        <div class="photo_overlay">
                            <div class="photo_con">
                                <input type="text" placeholder="添加名称…">
                                <div class="photo_fd">
                                    <span class="photo_time">2016年8月17日上传</span>
                                    <a href="###" class="photo_move"></a>
                                    <i class="photo_dele"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="swiper-slide" style="background-image:url(<%=basePath%>images/ea/production/scmanage/photo_s02.jpg)">
                        <div class="photo_overlay">
                            <div class="photo_con">
                                <input type="text" placeholder="添加名称…">
                                <div class="photo_fd">
                                    <span class="photo_time">2016年8月17日上传</span>
                                    <a href="###" class="photo_move"></a>
                                    <i class="photo_dele"></i>
                                </div>
                            </div>
                        </div>
                    </div> --%>
                </div>
                <!-- Add Arrows -->
                <div class="swiper-button-next swiper-button-white"></div>
                <div class="swiper-button-prev swiper-button-white"></div>
            </div>
          <%--   <div class="swiper-container gallery-thumbs">
                <div class="swiper-wrapper">
                    <div class="kelong2" style="display:none;"></div>
                    <div class="swiper-slide" style="background-image:url(<%=basePath%>images/ea/production/scmanage/demo_04.jpg)"></div>
                    <div class="swiper-slide" style="background-image:url(<%=basePath%>images/ea/production/scmanage/photo_s03.jpg)"></div>
                    <div class="swiper-slide" style="background-image:url(<%=basePath%>images/ea/production/scmanage/photo_s01_200x200.jpg)"></div>
                    <div class="swiper-slide" style="background-image:url(<%=basePath%>images/ea/production/scmanage/photo_s02.jpg)"></div>
                </div>
            </div> --%>
        </div>
    </div>
    <!--图片点击放大 结束-->
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
