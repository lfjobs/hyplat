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

<title>场地客服</title>
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>

<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css"/>
<link rel="stylesheet" href="<%=basePath%>css/ea/production/drive.css"/>
<link rel="stylesheet" href="<%=basePath%>css/ea/production/driveindex.css"/>

<script src="<%=basePath%>js/bootstrap.js"></script>
<script type="text/javascript">

var basePath="<%=basePath%>";	

</script>
</head>

<body style="">
    <div class="wfj12_002_top" style="background-color: #f74c31;">
        <ul id="tops">
            <li><a href="#；" target="_self"><img src="<%=basePath%>images/ea/production/drive/wfj_return_01.png" /></a></li>
            <li>四川胜威驾校</li>
            <li><a href="javascript:;"><img src="<%=basePath%>images/ea/production/drive/top_more.png" /></a></li>
        </ul>
    </div>

    <div class="main_auto wfj12_002_content">
        <div class="main_hidden wfj12_002_con_hidden">
            <div class="M_nav">
                <ul class="tabs" id="tabs01">
                    <li class="on current">选择设备</li>
                    <li>选择场地</li>
                    <li>选择客服</li>
                </ul>
            </div>
            <div class="M_main" id="container01">
                <div class="con shebei">
                    <div class="M_main_lis">
                        <div class="same_bot_lis_main">
                            <div class="imgs Img">
                                <img src="<%=basePath%>images/ea/production/drive/car1.png" alt=""/>
                            </div>
                            <div class="Text kf_txt1">
                                <h3>桑塔纳</h3>
                                <div class="Img_start">
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                </div>
                                <div class="Text_main"><img src="<%=basePath%>images/ea/production/drive/dingwei.png"><nobr>多提意见然而噶尔格二哥挖让给挖让给ear艾eagre尔</nobr></div>
                            </div>
                            <div class="Img_gou">
                                <div class="gou"></div>
                            </div>

                        </div>
                    </div>
                    <div class="M_main_lis">
                        <div class="same_bot_lis_main">
                            <div class="imgs Img">
                                <img src="<%=basePath%>images/ea/production/drive/car2.png" alt=""/>
                            </div>
                            <div class="Text kf_txt1">
                                <h3>桑塔纳</h3>
                                <div class="Img_start">
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                </div>
                                <div class="Text_main"><img src="<%=basePath%>images/ea/production/drive/dingwei.png"><nobr>多提意见然而噶尔格二哥挖让给挖让给ear艾eagre尔</nobr></div>
                            </div>
                            <div class="Img_gou">
                                <div class="gou"></div>
                            </div>

                        </div>
                    </div>
                    <div class="M_main_lis">
                        <div class="same_bot_lis_main">
                            <div class="imgs Img">
                                <img src="<%=basePath%>images/ea/production/drive/car3.png" alt=""/>
                            </div>
                            <div class="Text kf_txt1">
                                <h3>桑塔纳</h3>
                                <div class="Img_start">
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                </div>
                                <div class="Text_main"><img src="<%=basePath%>images/ea/production/drive/dingwei.png"><nobr>多提意见然而噶尔格二哥挖让给挖让给ear艾eagre尔</nobr></div>
                            </div>
                            <div class="Img_gou">
                                <div class="gou"></div>
                            </div>

                        </div>
                    </div>
                    <div class="M_main_lis">
                        <div class="same_bot_lis_main">
                            <div class="imgs Img">
                                <img src="<%=basePath%>images/ea/production/drive/car4.png" alt=""/>
                            </div>
                            <div class="Text kf_txt1">
                                <h3>桑塔纳</h3>
                                <div class="Img_start">
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                </div>
                                <div class="Text_main"><img src="<%=basePath%>images/ea/production/drive/dingwei.png"><nobr>多提意见然而噶尔格二哥挖让给挖让给ear艾eagre尔</nobr></div>
                            </div>
                            <div class="Img_gou">
                                <div class="gou"></div>
                            </div>

                        </div>
                    </div>
                    <div class="xz_btn">
                        <button>选择的项目</button>
                    </div>
                </div>


                <div class="con changdi">
                    <div class="M_main_lis">
                        <div class="same_bot_lis_main">
                            <div class="imgs Img">
                                <img src="<%=basePath%>images/ea/production/drive/changdi1.png" alt=""/>
                            </div>
                            <div class="Text kf_txt1">
                                <h3>A场地</h3>
                                <div class="Img_start">
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                </div>
                                <div class="Text_main"><img src="<%=basePath%>images/ea/production/drive/3.png"><nobr>多提意见然而噶尔格二哥挖让给挖让给ear艾eagre尔</nobr></div>
                            </div>
                            <div class="Img_gou">
                                <div class="gou"></div>
                            </div>

                        </div>
                    </div>

                    <div class="M_main_lis">
                        <div class="same_bot_lis_main">
                            <div class="imgs Img">
                                <img src="<%=basePath%>images/ea/production/drive/changdi2.png" alt=""/>
                            </div>
                            <div class="Text kf_txt1">
                                <h3>B场地</h3>
                                <div class="Img_start">
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                </div>
                                <div class="Text_main"><img src="<%=basePath%>images/ea/production/drive/3.png"><nobr>多提意见然而噶尔格二哥挖让给挖让给ear艾eagre尔</nobr></div>
                            </div>
                            <div class="Img_gou">
                                <div class="gou"></div>
                            </div>

                        </div>
                    </div>

                    <div class="M_main_lis">
                        <div class="same_bot_lis_main">
                            <div class="imgs Img">
                                <img src="<%=basePath%>images/ea/production/drive/changdi3.png" alt=""/>
                            </div>
                            <div class="Text kf_txt1">
                                <h3>C场地</h3>
                                <div class="Img_start">
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                </div>
                                <div class="Text_main"><img src="<%=basePath%>images/ea/production/drive/3.png"><nobr>多提意见然而噶尔格二哥挖让给挖让给ear艾eagre尔</nobr></div>
                            </div>
                            <div class="Img_gou">
                                <div class="gou"></div>
                            </div>

                        </div>
                    </div>

                    <div class="M_main_lis">
                        <div class="same_bot_lis_main">
                            <div class="imgs Img">
                                <img src="<%=basePath%>images/ea/production/drive/changdi4.png" alt=""/>
                            </div>
                            <div class="Text kf_txt1">
                                <h3>D场地</h3>
                                <div class="Img_start">
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                </div>
                                <div class="Text_main"><img src="<%=basePath%>images/ea/production/drive/3.png"><nobr>多提意见然而噶尔格二哥挖让给挖让给ear艾eagre尔</nobr></div>
                            </div>
                            <div class="Img_gou">
                                <div class="gou"></div>
                            </div>

                        </div>
                    </div>
                    <div class="xz_btn">
                        <button>选择的项目</button>
                    </div>
                </div>
                <div class="con kefu">
                    <div class="M_main_lis">
                        <div class="same_bot_lis_main">
                            <div class="imgs Img">
                                 <img src="<%=basePath%>images/ea/production/drive/kefu1.png" alt=""/>
                            </div>
                            <div class="Text kf_txt1">
                                <h3>A客服</h3>
                                <div class="Img_start">
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                </div>
                                <div class="Text_main"><img src="<%=basePath%>images/ea/production/drive/3.png"><nobr>多提意见然而噶尔格二哥挖让给挖让给ear艾eagre尔</nobr></div>
                            </div>
                            <div class="Img_gou">
                                <div class="gou"></div>
                            </div>

                        </div>
                    </div>

                    <div class="M_main_lis">
                        <div class="same_bot_lis_main">
                            <div class="imgs Img">
                                <img src="<%=basePath%>images/ea/production/drive/kefu2.png" alt=""/>
                            </div>
                            <div class="Text kf_txt1">
                                <h3>B客服</h3>
                                <div class="Img_start">
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                </div>
                                <div class="Text_main"><img src="<%=basePath%>images/ea/production/drive/3.png"><nobr>多提意见然而噶尔格二哥挖让给挖让给ear艾eagre尔</nobr></div>
                            </div>
                            <div class="Img_gou">
                                <div class="gou"></div>
                            </div>

                        </div>
                    </div>

                    <div class="M_main_lis">
                        <div class="same_bot_lis_main">
                            <div class="imgs Img">
                                <img src="<%=basePath%>images/ea/production/drive/kefu3.png" alt=""/>
                            </div>
                            <div class="Text kf_txt1">
                                <h3>C客服</h3>
                                <div class="Img_start">
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                </div>
                                <div class="Text_main"><img src="<%=basePath%>images/ea/production/drive/3.png"><nobr>多提意见然而噶尔格二哥挖让给挖让给ear艾eagre尔</nobr></div>
                            </div>
                            <div class="Img_gou">
                                <div class="gou"></div>
                            </div>

                        </div>
                    </div>

                    <div class="M_main_lis">
                        <div class="same_bot_lis_main">
                            <div class="imgs Img">
                                <img src="<%=basePath%>images/ea/production/drive/kefu4.png" alt=""/>
                            </div>
                            <div class="Text kf_txt1">
                                <h3>D客服</h3>
                                <div class="Img_start">
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                    <span><img src="<%=basePath%>images/ea/production/drive/start_03.png" alt=""/></span>
                                </div>
                                <div class="Text_main"><img src="<%=basePath%>images/ea/production/drive/3.png"><nobr>多提意见然而噶尔格二哥挖让给挖让给ear艾eagre尔</nobr></div>
                            </div>
                            <div class="Img_gou">
                                <div class="gou"></div>
                            </div>

                        </div>
                    </div>
                    <div class="xz_btn">
                        <a href="确认支付.html"> <button>选择的项目</button></a>
                    </div>
                </div>
            </div>
        </div>
    </div>

<script>
    $(document).ready(function() {
        $(".gou").click(function () {
            $(this).toggleClass("gou2");
        });
    });
</script>

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
            $(".wfj12_002_top").css("width",$(window).width()+"px");
            $(".wfj12_002_top").css("position","fixed");
            $(".wfj12_002_top_img").attr("style","margin-top:"+$(window).height()*0.01+"px;");
            /*顶部 end*/
            /*滚动位置*/
            $(".wfj12_002_content").attr("style","width:"+$(window).width()+"px;height:100%;");
            $(".wfj12_002_con_hidden").css("height",$(window).height()*0.92-$(".xz_btn").height()+"px");
            $(".wfj12_002_con_hidden").css("margin-top",$(window).height()*0.08+"px");

            var h = $(".wfj12_002_comnav").height()+$(".wfj12_002_mainimg").height()+$(".wfj12_002_com_bottom").height();

            if(h < $(".wfj12_002_content").height()){
                $(".wfj12_002_con_hidden").css("width",$(window).width()+"px");
            }else{
                $(".wfj12_002_con_hidden").css("width",parseInt($(window).width()+17)+"px");
            }

            /*滚动位置 end*/
        });
    </script>
    <script>
        $(function(){
           /* $(".main_auto").css("height",$(window).height()-$("#top").height()-$("footer").height()+"px")*/
            $(".Img_gou").css({"height":$(".same_bot_lis_main").height()+"px"})
            $(".M_nav ul li").click(function(){
                $(".M_nav ul li").removeClass("on")
                $(this).addClass("on")
            })


//            tab
            var tabs = function(tab, con){
                tab.click(function(){
                    var indx = tab.index(this);
                    tab.removeClass('current');
                    $(this).addClass('current');
                    con.hide();
                    con.eq(indx).show();
                })
            }

            tabs($("#tabs01 li"), $('#container01 .con'));
        })
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