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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>

<title>学员信息</title>

<link rel="stylesheet" href="<%=basePath%>css/ea/production/drive.css"/>
<link rel="stylesheet" href="<%=basePath%>css/ea/production/driveindex.css"/>
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>

<script src="<%=basePath%>js/ea/production/cprocedure/drive/jx_studentInfo.js" type="text/javascript"></script>

<script type="text/javascript">

var basePath="<%=basePath%>";	

</script>


</head>

<body>
<div class="drive_top">
    <div>
        <img src="<%=basePath%>images/ea/production/drive/driver_03.jpg" alt=""/>
    </div>
    <div>
        学员张利
    </div>
</div>
<div class="drive_top_d"></div>
<div class="drive_main">
    <div class="drive_main_lis">
        <div class="drive_main_lis_left">
            <img src="<%=basePath%>images/ea/production/drive/jiaolianyuan_03.jpg" alt=""/>
        </div>
        <div class="drive_main_lis_right jiaolianyuan">
            <div>
                <div class="jiaolian_lis">基本资料</div>
            </div>
            <div class="drive_main_lis_right_div">
                <a href="<%=basePath%>ea/drivem/ea_getStuBaseInfo.jspa">
                    <p  class="drive_main_lis_right_img">
                        <img src="<%=basePath%>images/ea/production/drive/driver_09.jpg" alt=""/>
                    </p>
                </a>
            </div>
        </div>
    </div>
    <div class="drive_main_lis">
        <div class="drive_main_lis_left">
            <img src="<%=basePath%>images/ea/production/drive/jiaolianyuan_06.jpg" alt=""/>
        </div>
        <div class="drive_main_lis_right jiaolianyuan">
            <div>
                <div class="jiaolian_lis">预约教练</div>
            </div>
            <div class="drive_main_lis_right_div">
                <p  class="drive_main_lis_right_img">
                    <a href="预约列表3.html"> <img src="<%=basePath%>images/ea/production/drive/driver_09.jpg" alt=""/></a>
                </p> </div>
        </div>
    </div>

    <div class="drive_main_lis">
        <div class="drive_main_lis_left">
            <img src="<%=basePath%>images/ea/production/drive/jiaolianyuan_10.jpg" alt=""/>
        </div>
        <div class="drive_main_lis_right jiaolianyuan">
            <div>
                <div class="jiaolian_lis">在线学习</div>
            </div>
            <div class="drive_main_lis_right_div">
                <p  class="drive_main_lis_right_img">
                    <a href="在线学习.html"> <img src="<%=basePath%>images/ea/production/drive/driver_09.jpg" alt=""/></a>
                </p>
            </div>
        </div>
    </div>
    <a href="学习进度.html">
    <div class="drive_main_lis">
        <div class="drive_main_lis_left">
            <img src="<%=basePath%>images/ea/production/drive/jiaolianyuan_08.jpg" alt=""/>
        </div>
        <div class="drive_main_lis_right jiaolianyuan">
            <div>
               <div class="jiaolian_lis">学员学习进度</div>
            </div>
            <div class="drive_main_lis_right_div">
                <p  class="drive_main_lis_right_img">
                    <a href="学习进度.html"> <img src="<%=basePath%>images/ea/production/drive/driver_09.jpg" alt=""/></a>
                </p>
            </div>
        </div>
    </div>
    </a>

    <div class="drive_main_lis">
        <div class="drive_main_lis_left">
            <img src="<%=basePath%>images/ea/production/drive/jiaolianyuan_12.jpg" alt=""/>
        </div>
        <div class="drive_main_lis_right jiaolianyuan">
            <div>
                <div class="jiaolian_lis">找考场</div>
            </div>
            <div class="drive_main_lis_right_div">
                <p  class="drive_main_lis_right_img">
                    <a href="考场列表.html"> <img src="<%=basePath%>images/ea/production/drive/driver_09.jpg" alt=""/></a>
                </p>
            </div>
        </div>
    </div>
    <div class="drive_main_lis">
        <div class="drive_main_lis_left">
            <img src="<%=basePath%>images/ea/production/drive/jiaolianyuan_14.jpg" alt=""/>
        </div>
        <div class="drive_main_lis_right jiaolianyuan">
            <div>
                <div class="jiaolian_lis">点评教练</div>
            </div>
            <div class="drive_main_lis_right_div">
                <p  class="drive_main_lis_right_img">
                    <a href="学员意见2.html"><img src="<%=basePath%>images/ea/production/drive/driver_09.jpg" alt=""/></a>
                </p>
            </div>
        </div>
    </div>
    <div class="drive_main_lis">
        <div class="drive_main_lis_left">
            <img src="<%=basePath%>images/ea/production/drive/jindu_03.png" alt=""/>
        </div>
        <div class="drive_main_lis_right jiaolianyuan">
            <div>
                <div class="jiaolian_lis">结业管理</div>
            </div>
            <div class="drive_main_lis_right_div">
                <p  class="drive_main_lis_right_img">
                    <a href="结业管理.html"><img src="<%=basePath%>images/ea/production/drive/driver_09.jpg" alt=""/></a>
                </p>
            </div>
        </div>
    </div>
    <div class="drive_main_lis">
        <div class="drive_main_lis_left">
            <img src="<%=basePath%>images/ea/production/drive/drive_08.jpg" alt=""/>
        </div>
        <div class="drive_main_lis_right jiaolianyuan">
            <div>
                <div class="jiaolian_lis">驾校通知</div>
            </div>
            <div class="drive_main_lis_right_div">
                <p  class="drive_main_lis_right_img">
                    <a href="驾校通知.html"> <img src="<%=basePath%>images/ea/production/drive/driver_09.jpg" alt=""/></a>
                </p>
            </div>
        </div>
    </div>
    <div class="drive_main_lis">
        <div class="drive_main_lis_left">
            <img src="<%=basePath%>images/ea/production/drive/jiaolianyuan_16.jpg" alt=""/>
        </div>
        <div class="drive_main_lis_right jiaolianyuan">
            <div>
                <div class="jiaolian_lis">学车赚钱</div>
            </div>
            <div class="drive_main_lis_right_div">
                <p  class="drive_main_lis_right_img">
                    <a href="学车赚钱2.html"> <img src="<%=basePath%>images/ea/production/drive/driver_09.jpg" alt=""/></a>
                </p>
            </div>
        </div>
    </div>
    <div class="drive_main_lis">
        <div class="drive_main_lis_left">
            <img src="<%=basePath%>images/ea/production/drive/jiaolianyuan_18.jpg" alt=""/>
        </div>
        <div class="drive_main_lis_right jiaolianyuan">
            <div>
                <div class="jiaolian_lis">缴费查询</div>
            </div>
            <div class="drive_main_lis_right_div">
                <p  class="drive_main_lis_right_img">
                    <img src="<%=basePath%>images/ea/production/drive/driver_09.jpg" alt=""/>
                </p>
            </div>
        </div>
    </div>

</div>

<script>
    // var num1=num2=num3=0
    window.onload = window.onresize = function(){
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //console.log(clientWidth);
        //通过屏幕宽度去设置不同的后台根字体的大小
        //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
</script>
</body>
</html>
