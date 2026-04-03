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

<title>驾照信息</title>



<link rel="stylesheet" href="<%=basePath%>css/bootstrap.css"/>
<link rel="stylesheet" href="<%=basePath%>css/ea/production/new_drive.css"/>
<link rel="stylesheet" href="<%=basePath%>css/ea/production/driveindex.css"/>
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/bootstrap.js"></script>
<script type="text/javascript">

var basePath="<%=basePath%>";	

</script>
</head>

<body>
<ul class="drive_top">
    <li class="arrow">
        <a href="基本资料_new.html"> <img src="<%=basePath%>images/ea/production/drive/wfj_return_02.png" alt=""/></a>
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
            <li class="lis2">
                <div class="drive_bottom_top_text" style="width: 26%;">有效开始时间</div>
                <div class="pull-right drive_bottom_top_right" style="width: 74%;">
                    <div class="jbzl2_d">XXXXXXX</div>
                    <!--<div class="drive_bottom_top_img"><img src="<%=basePath%>images/ea/production/drive/right_ico_03.png" alt=""/></div>-->
                </div>
            </li>
            <li class="lis2">
                <div class="drive_bottom_top_text" style="width: 18%;">报名来源</div>
                <div class="pull-right drive_bottom_top_right" style="width: 82%;">
                    <div class="jbzl2_d">XXXXXXX</div>
                    <!--<div class="drive_bottom_top_img"><img src="<%=basePath%>images/ea/production/drive/right_ico_03.png" alt=""/></div>-->
                </div>
            </li>
            <li class="lis2">
                <div class="drive_bottom_top_text" style="width: 18%;">报名日期</div>
                <div class="pull-right drive_bottom_top_right" style="width: 82%;">
                    <div class="jbzl2_d">XXXXXXX</div>
                    <div class="drive_bottom_top_img jbzl2_img"><img src="<%=basePath%>images/ea/production/drive/right_ico_03.png" alt=""/></div>
                </div>
            </li>
            <li class="lis2">
                <div class="drive_bottom_top_text" style="width: 18%;">现有驾照</div>
                <div class="pull-right drive_bottom_top_right" style="width: 82%;">
                    <div class="jbzl2_d">XXXXXXX</div>
                    <div class="drive_bottom_top_img jbzl2_img"><img src="<%=basePath%>images/ea/production/drive/right_ico_03.png" alt=""/></div>
                </div>
            </li>
            <li class="lis2">
                <div class="drive_bottom_top_text" style="width: 10%;">备注</div>
                <div class="pull-right drive_bottom_top_right" style="width: 90%;">
                    <textarea placeholder="XXXXXXXX"></textarea>
                    <div class="drive_bottom_top_img jbzl2_img"><img src="<%=basePath%>images/ea/production/drive/right_ico_03.png" alt=""/></div>
                </div>
            </li>
            <li class="lis3 jbzl2_l3">
                <div class="jbzl2_d1">
                    <div class="drive_bottom_top_text" style="width:40%;">是否党员</div>
                    <div class="drive_bottom_top_right" style="width: 60%;">
                        <div class="">
                            <label class="demo--label">
                                <input class="demo--radio" type="radio" name="demo-radio">
                                <span class="demo--radioInput"></span>是
                             </label>
                        </div>
                        <div class="">
                            <label class="demo--label">
                                <input class="demo--radio" type="radio" name="demo-radio">
                                <span class="demo--radioInput"></span>否
                            </label>
                        </div>
                    </div>
                </div>
                <div class="jbzl2_d1">
                    <div class="drive_bottom_top_text" style="width:40%;">已领驾驶证</div>
                    <div class="drive_bottom_top_right" style="width: 60%;">
                        <div class="">
                            <label class="demo--label">
                                <input class="demo--radio" type="radio" name="demo-radio">
                                <span class="demo--radioInput"></span>是
                            </label>
                        </div>
                        <div class="">
                            <label class="demo--label">
                                <input class="demo--radio" type="radio" name="demo-radio">
                                <span class="demo--radioInput"></span>否
                            </label>
                        </div>
                    </div>
                </div>
                <div class="jbzl2_d1">
                    <div class="drive_bottom_top_text" style="width:40%;">是否本地</div>
                    <div class="drive_bottom_top_right" style="width: 60%;">
                        <div class="">
                            <label class="demo--label">
                                <input class="demo--radio" type="radio" name="demo-radio">
                                <span class="demo--radioInput"></span>是
                            </label>
                        </div>
                        <div class="">
                            <label class="demo--label">
                                <input class="demo--radio" type="radio" name="demo-radio">
                                <span class="demo--radioInput"></span>否
                            </label>
                        </div>
                    </div>
                </div>
                <div class="jbzl2_d1">
                    <div class="drive_bottom_top_text" style="width:40%;">已领结业证</div>
                    <div class="drive_bottom_top_right" style="width: 60%;">
                        <div class="">
                            <label class="demo--label">
                                <input class="demo--radio" type="radio" name="demo-radio">
                                <span class="demo--radioInput"></span>是
                            </label>
                        </div>
                        <div class="">
                            <label class="demo--label">
                                <input class="demo--radio" type="radio" name="demo-radio">
                                <span class="demo--radioInput"></span>否
                            </label>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <ul id="myTab" class="nav nav-tabs jbzl2_nav">
        <li class="active">
            <a href="#home" data-toggle="tab">身份证</a>
        </li>
        <li>
            <a href="#ios" data-toggle="tab">体检表</a>
        </li>
        <li>
            <a href="#ejb" data-toggle="tab">暂住证</a>
        </li>
    </ul>
    <div id="myTabContent" class="tab-content">
        <div class="tab-pane fade in active jbzl2_d2" id="home">
            <div class="jbzl2_left">
                <div class="jbzl2_sfz1">
                    <img src="">
                </div>
                <button>上传图片</button>
            </div>
            <div class="jbzl2_right">
                <div class="jbzl2_sfz2">
                    <img src="">
                </div>
                <button>上传图片</button>
            </div>
        </div>
        <div class="tab-pane fade" id="ios">
            131
        </div>
        <div class="tab-pane fade" id="ejb">
           123
        </div>
    </div>
</div>
<script>
    $(document).ready(function(){
        $(".drive_top").css("position","fixed");
        $(".drive_top").css("width","100%");
        $(".drive_top").css("height",$(window).height()*0.08+"px");
        $(".drive_top li").css("line-height",2.5);

        $(".drive_bottom").css("position","absolute");
        $(".drive_bottom").css("overflow","auto");
        $(".drive_bottom").css("margin-top",$(window).height()*0.08+"px");
        $(".drive_bottom").css("height",$(window).height()*0.92+"px");
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
            