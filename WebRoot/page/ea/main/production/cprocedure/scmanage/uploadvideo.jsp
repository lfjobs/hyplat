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
<title>上传视频</title>

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
<link rel="stylesheet"
	href="<%=basePath%>css/ea/production/scmanage/sc_manger.css">
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>

<script type="text/javascript">

var basePath="<%=basePath%>";
</script>
</head>

<body>
    <!-- header start  -->
    <header class="com_head">
         <a href="javascript:leavePage();" class="back"></a>
        <h1>上传视频</h1>
        <a href="javascript:;" class="head_R">保存</a>
    </header>
    <div class="wrap_page">
         <form name="formsave" id="formsave"  action="<%=basePath%>ea/scm/ea_saveContent.jspa" method="post">
            <textarea class="photo_des" placeholder="添加视频描述…" name="materialContent.describe"></textarea>
            <div class="up_group_wrap clearfix">
               <a href="<%=basePath%>/ea/scm/ea_selectGroupList.jspa?type=${type}" style="color:#333333;">
                <span>上传到</span>
                <span class="up_group">${materialGroup.groupname}</span>
                 <input type="hidden" value="${materialGroup.mgId}" name="materialGroup.mgId" id="groupID"/>
                 <input type="hidden" value="${materialGroup.mgId}" name="materialContent.groupID" />
                 <input type="hidden" value="01" name="materialContent.fileType" />
                </a>
            </div>
            <ul class="photos_wrap clearfix">
                  <c:forEach items="${list}" var="item">
               <li style="background-image: url(<%=basePath%>/images/ea/production/scmanage/demo_01.jpg)">
                 <i class="up_dele"></i>
                 <i class="video_ico"></i>
                 <div class="progress">
                 <input type="hidden" class="path" value="${item.filepath}"><span>上传成功</span></div>
                 <div class="size">${item.filesize}</div>
                </li>
            </c:forEach>
                <li class="photo_sbox add_photo" id="add_photo">                  
                    <input  type="file"  id="choose" accept="video/*" multiple>
                </li>
            </ul>
            	<input type="hidden" name="sub" value="${session_value}"/>
        </form>
    </div>
    <!--  header end  -->
    <script>
        window.onload = window.onresize = function() {
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //通过屏幕宽度去设置不同的后台根字体的大小
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
        }
        
        $(function(){
        	$(".head_R").click(function(){
        		document.formsave.submit();
        		
        	});
        	
        });
    </script>
    <script src="<%=basePath%>js/ea/production/cprocedure/scmanage/vedio-upload.js"></script>
    <script>
        $(document).on("click",".up_dele",function(){
            $(this).parent().detach();
        })
    </script>
</body>

</html>
