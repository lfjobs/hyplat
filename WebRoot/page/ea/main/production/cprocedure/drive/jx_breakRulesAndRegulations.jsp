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
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>车辆违章</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/contacts/Restaurant/style12.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/production/drive/index.css"/>
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.6.1.min.js"></script>
</head>

<body>
	<div class="wfj12_002">
    	<div class="wfj12_002_top wzcx_002_top">
        	<ul id="tops">
            	<li><a href="javascript:history.back(-1)" target="_self"><img src="<%=basePath %>images/ea/production/left_jt.png" /></a></li>
            	<li>违章查询</li>
            </ul>
        </div>
        <div class="wfj12_002_content wzcx_content">
            <div class="wfj12_002_con_hidden">
                <iframe  name="weizhang" src="http://m.cheshouye.com/api/weizhang/?t=416081" width="100%" height="100%"  frameborder="0" scrolling="no"></iframe>
            </div>
        </div>
        
		<script type="text/javascript">
            $(document).ready(function(e) {
                $("body").css("height",$(window).height());
                $("body").css("height",$(window).height());
                
				/*顶部*/
				$("#tops").find("li").attr("style","float:left;");
				$("#tops").find("li").eq(0).attr("style","width:15%;");
				$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
				$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;color:#000;");
				$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
				$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");

				$(".wfj12_002_top").css("height",$(window).height()*0.08+"px");
				$(".wfj12_002_top").css("lineHeight",$(window).height()*0.08+"px");
                $(".wfj12_002_top").css("position","fixed");
				$(".wfj12_002_top_img").attr("style","margin-top:"+$(window).height()*0.01+"px;");
				/*顶部 end*/



				/*滚动位置*/
				$(".wfj12_002_content").attr("style","width:"+$(window).width()+"px;height:100%;");
				$(".wfj12_002_con_hidden").attr("style","width:"+parseInt($(".wfj12_002_content").width()+17)+"px;height:"+$(".wfj12_002_content").height()+"px;");

				var h = $(".wfj12_002_comnav").height()+$(".wfj12_002_mainimg").height()+$(".wfj12_002_com_bottom").height();

				if(h < $(".wfj12_002_content").height()){
					$(".wfj12_002_con_hidden").css("width",$(window).width()+"px");
				}else{
					$(".wfj12_002_con_hidden").css("width",parseInt($(window).width()+17)+"px");
				}
                $(".wfj12_002_con_hidden").css("height",$(window).height()*0.92+"px");
                $(".wfj12_002_con_hidden").css("margin-top",$(window).height()*0.08+"px");
				/*滚动位置 end*/

                // 驾校
                $(".rukou_text>div:first-child h1").css({"line-height":$(".rukou_text_left").height()+"px"})
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
	</div>
</body>
</html>
