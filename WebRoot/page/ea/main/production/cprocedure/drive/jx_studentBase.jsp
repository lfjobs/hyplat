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

<title>基本信息</title>



<link rel="stylesheet" href="<%=basePath%>css/bootstrap.css"/>
<link rel="stylesheet" href="<%=basePath%>css/ea/production/new_drive.css"/>
<link rel="stylesheet" href="<%=basePath%>css/ea/production/driveindex.css"/>
<link rel="stylesheet" href="<%=basePath%>css/ea/production/phone.css"/>
<link rel="stylesheet" href="<%=basePath%>css/ea/production/zepto.mdater.css"/>
<link rel="stylesheet" href="<%=basePath%>css/ea/production/zepto.mdatetimer.css"/>


<script type="text/javascript">

var basePath="<%=basePath%>";	

</script>


</head>
<body>
<div class="body1">
    <ul class="drive_top">
        <li class="arrow">
            <a href="学员登录首页.html"> <img src="<%=basePath%>images/ea/production/drive/wfj_return_02.png" alt=""/></a>
        </li>
        <li>
            基本资料
        </li>
        <li class="drive_top_right">
            保存
        </li>
    </ul>
    <div class="drive_bottom">
        <div class="drive_bottom_top">
            <ul>
                <li class="lis1">
                    <div class="drive_bottom_top_text" style="width: 20%;">头像</div>
                    <div class="pull-right drive_bottom_top_right" style="width: 25%;">
                        <div class="jbzl_d jbzl_d2" id="logox">
                            <img src="<%=basePath%>images/ea/production/drive/touxiang1.png" class="pullImg" alt=""/>
                        </div>
                        <div class="drive_bottom_top_img">
                            <img src="<%=basePath%>images/ea/production/drive/right_ico_03.png" alt=""/>
                        </div>
                    </div>
                </li>
                <li class="lis2">
                    <div class="drive_bottom_top_text" style="width: 20%;">姓名</div>
                    <div class="pull-right drive_bottom_top_right jljbzl_inp" style="width: 40%;">
                        <input type="text" placeholder="XXXXXXX">
                    </div>
                </li>
                <li class="lis2">
                    <div class="drive_bottom_top_text" style="width: 20%;">编号</div>
                    <div class="pull-right drive_bottom_top_right jljbzl_inp" style="width: 40%;">
                        <input type="text" placeholder="XXXXXXX">
                    </div>
                </li>
                <li class="lis3">
                    <div class="drive_bottom_top_text" style="width: 20%;">性别</div>
                    <div class="drive_bottom_top_right" style="width: 40%;">
                        <div class=""><label class="demo--label">
                            <input class="demo--radio" type="radio" name="demo-radio">
                            <span class="demo--radioInput"></span>男
                        </label></div>
                        <div class=""><label class="demo--label">
                            <input class="demo--radio" type="radio" name="demo-radio">
                            <span class="demo--radioInput"></span>女
                        </label></div>
                    </div>
                </li>
            </ul>
        </div>

            <ul class="drive_bottom_bot">
                <li class="lis4">
                    <div class="drive_bottom_top_text" style="width: 20%;">身份信息</div>
                    <div class="pull-right drive_bottom_top_right" style="width: 40%;">
                        <div class=""></div>
                        <div class="drive_bottom_top_img text-right">
                            <a href="身份信息.html"><img src="<%=basePath%>images/ea/production/drive/right_ico_03.png" alt=""/></a>
                        </div>
                    </div>
                </li>
            </ul>


        <ul class="drive_bottom_bot peixun">
            <li class="lis4">
                <div class="drive_bottom_top_text" style="width: 20%;">驾照信息</div>
                <div class="pull-right drive_bottom_top_right" style="width: 40%;">
                    <div class=""></div>
                    <div class="drive_bottom_top_img text-right">
                        <a href="驾照信息.html"> <img src="<%=basePath%>images/ea/production/drive/right_ico_03.png" alt=""/></a>
                    </div>
                </div>
            </li>
        </ul>

    </div>
</div>
<div class="body2">
    <ul class="drive_top jljbzl_top">
        <li class="arrow">
            <img src="<%=basePath%>images/ea/production/drive/wfj_return_01.png" alt=""/>
        </li>
        <li>
            教练基本资料
        </li>
        <li class="drive_top_right jljbzl_li">
            <img src="<%=basePath%>images/ea/production/drive/more.png" alt=""/>
        </li>
    </ul>
    <div class="jljbzl_bot">
        <img src="<%=basePath%>images/ea/production/drive/jiaoliantouxiang.png">
        <div>
            <span></span>
            <p>拍照</p>
            <div class="uploader1 blue jljbzl_xq">
                <input type="button" name="file" class="button" value="相册选取"/>
                <input id="file" type="file" onchange="javascript:setImagePreview();" accept="image/*" multiple  />
            </div>
            <p class="quxiao">取消</p>
        </div>
    </div>
</div>
<div class="body3">
    <article class="htmleaf-container">
        <div id="clipArea"></div>
        <div class="foot-use">
            <div class="uploader1 blue">
                <button class="quxiao quxiao2">取消</button>
            </div>
            <button id="clipBtn">保存</button>
        </div>
        <div id="view"></div>
    </article>
</div>
<script src="<%=basePath%>js/ea/production/cprocedure/drive/jquery-2.1.1.min.js"></script>
<script src="<%=basePath%>js/ea/production/cprocedure/drive/iscroll-zoom.js"></script>
<script src="<%=basePath%>js/ea/production/cprocedure/drive/hammer.js"></script>
<script src="<%=basePath%>js/ea/production/cprocedure/drive/jquery.photoClip.js"></script>
<script src="<%=basePath%>js/ea/production/cprocedure/drive/phone.js"></script>
<script>
    $(document).ready(function() {
        $(".jbzl_d").click(function () {
            $(".body2").css("display","block");
            $(".body3").css("display","none");
        });
        $(".quxiao").click(function () {
            $(".body2").css("display","none");
            $(".body3").css("display","none");
        });
        $(".jljbzl_xq").click(function () {
            $(".body2").css("display","none");
            $(".body3").css("display","block");
        });


        $(".drive_top").css("position","fixed");
        $(".drive_top").css("width","100%");
        $(".drive_top").css("background","#eeeeee");
        $(".drive_top").css("height",$(window).height()*0.08+"px");

        $(".drive_bottom").css("margin-top",$(window).height()*0.08+"px")
    });
</script>


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
