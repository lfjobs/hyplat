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

<title>预约教练确认订单</title>
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/production/drivestyle12.css"/>
<link rel="stylesheet" href="<%=basePath%>css/ea/production/driveindex.css"/>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/drive/payfor.js"></script>
<script type="text/javascript">

var basePath="<%=basePath%>";	

</script>
</head>

<body>
	<div class="wfj12_002">
    	<div class="wfj12_002_top">
        	<ul id="tops">
            	<li><a href="选择场地和客服.html" target="_self"><img src="<%=basePath%>images/ea/production/drive/wfj_return_01.png" /></a></li>
            	<li>确认订单</li>
            	<li><a href="javascript:;"><img src="<%=basePath%>images/ea/production/drive/1.png" /></a></li>
            </ul>
        </div>

        <div class="wfj12_002_content">
            <div class="wfj12_002_con_hidden">

                <div class="qrdd_grd1">
                    <img src="<%=basePath%>images/ea/production/drive/querendingdan1.png">
                    <h3>天太胜威驾校</h3>
                </div>
                <div class="qrdd_grd2">
                    <div class="qrdd_grd2_left">
                        <ul>
                            <li><img src="<%=basePath%>images/ea/production/drive/2.png"> </li>
                            <li><img src="<%=basePath%>images/ea/production/drive/yuyue_07.jpg"> </li>

                        </ul>
                    </div>
                    <div class="qrdd_grd2_right">
                        <div class="qrdd_grd2_mil">
                            <div class="jiantou"></div>
                            <h3>科目一</h3>
                            <div class="qrdd_grd2_d">
                                <div class="qrdd_grd2_dl">
                                    <p>￥<span>0.00</span></p>
                                </div>
                                <div class="qrdd_grd2_dr">
                                    <p>销量00</p>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="qrdd_grd2_mil2">
                            <div style="float: left;">
                                <div class="jiantou"></div>
                                <h3>XX教练</h3>
                                <div class="qrdd_d2">
                                  <p>购买数量：<span>00</span></p>
                                </div>
                                <div class="qrdd_grd2_d">
                                    <div class="qrdd_d2 ">
                                        <p>授课时间：<span>00.00--00.00</span></p>
                                    </div>
                                </div>
                            </div>
                            <button><p>立即<br>预约</p></button>
                            <div class="clearfix"></div>
                        </div>

                    </div>
                    <div class="clearfix"></div>
                </div>

                <div class="qrdd_grd3">
                    <p>合计：<span>￥0.0</span></p>
                    <button >立即支付</button>
                </div>
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
				$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;color:#FFF;");
				$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
				$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");

				$(".wfj12_002_top").css("height",$(window).height()*0.08+"px");
				$(".wfj12_002_top").css("lineHeight",$(window).height()*0.08+"px");
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

				/*滚动位置 end*/

                // 驾校
                $(".rukou_text>div:first-child h1").css({"line-height":$(".rukou_text_left").height()+"px"})

            });
        </script>
        <script src="../script/Product.js"></script>
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
