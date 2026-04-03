<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>加入平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/bootstrap.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/WFJClient/style_platform.css">
	<script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/toucher.js"></script>
</head>

<body>
<div class="content-b1">
	<header>
	    <ul class="pub_top1">
	        <li style="width: 10%;">
	            <img src="<%=basePath%>/images/WFJClient/PersonalJoining/left_jt.png">
	        </li>
	       	<li style="width: 80%;">加入平台</li>
	        <li style="width: 10%;"></li>
	        <div class="clearfix"></div>
	    </ul>
	</header>
	<div class="content">
        <div class="pub_banner">
            <img width="100%" src="<%=basePath%>/images/WFJClient/PersonalJoining/pla-banner.png">
        </div>
        <ul class="pla_con">
            <img src="<%=basePath%>/images/WFJClient/PersonalJoining/pingtaiyoushi.png" width="100%">
            <li>
                <div><img src="<%=basePath%>/images/WFJClient/PersonalJoining/1.png"></div>
                <div>
                    <h2>低成本</h2>
                    <p>免费加入会员，100元即可成为代理商</p>
                </div>
                <div class="clearfix"></div>
            </li>
            <li>
                <div><img src="<%=basePath%>/images/WFJClient/PersonalJoining/2.png"></div>
                <div>
                    <h2>会员种类多</h2>
                    <p>平台拥有多种会员类型，面向公司、个人不同的需求</p>
                </div>
                <div class="clearfix"></div>
            </li>
            <li>
                <div><img src="<%=basePath%>/images/WFJClient/PersonalJoining/3.png"></div>
                <div>
                    <h2>管理模块化</h2>
                    <p>较其它管理系统，我们将企业管理分为人事、财务、办公室、生产、营销模块</p>
                </div>
                <div class="clearfix"></div>
            </li>
            <li>
                <div><img src="<%=basePath%>/images/WFJClient/PersonalJoining/4.png"></div>
                <div>
                    <h2>智能吸粉系统</h2>
                    <p>各种方式推广会员名片，增加会员粉丝获取“金粉丝”经济</p>
                </div>
                <div class="clearfix"></div>
            </li>
            <li>
                <div><img src="<%=basePath%>/images/WFJClient/PersonalJoining/5.png"></div>
                <div>
                    <h2>轻松做老板</h2>
                    <p>底下会员购买产品产生交易，获取丰厚佣金，在家轻松做老板</p>
                </div>
                <div class="clearfix"></div>
            </li>
            <li>
                <div><img src="<%=basePath%>/images/WFJClient/PersonalJoining/6.png"></div>
                <div>
                    <h2>扩大公司收益</h2>
                    <p>公司底下加入更多会员，会员相互宣传，购买企业产品，产生更多利益</p>
                </div>
                <div class="clearfix"></div>
            </li>
        </ul>
        <div class="pub_banner">
            <img width="100%" src="<%=basePath%>/images/WFJClient/PersonalJoining/pla-banner2.png">
        </div>
        <div class="pla_mil" align="center">
            <img src="<%=basePath%>/images/WFJClient/PersonalJoining/pla1.png" width="60%">
            <form>
                <div><span>姓名</span><input type="text"></div>
                <hr style="border-top:1px solid #ddd;width: 90%;margin: 0.5rem 0;">
                <div><span>电话</span><input type="text"></div>
                <hr style="border-top:1px solid #ddd;width: 90%;margin: 0.5rem 0;">
                <div><span>公司名称</span><input type="text"></div>
                <hr style="border-top:1px solid #ddd;width: 90%;margin: 0.5rem 0;">
            </form>
        </div>
        <div class="pla_buy">
            <p>购买的会员类型</p><img src="<%=basePath%>/images/WFJClient/PersonalJoining/jiantou-right.png">
            <div class="clearfix"></div>
        </div>
        <div class="pla_pay">
            <div><img src="<%=basePath%>/images/WFJClient/PersonalJoining/zhifubao.png"><span>支付方式</span></div>
        </div>
    </div>
</div>

<div class="content-b2">
    <header>
        <ul>
            <li style="width: 10%;">
                <img src="<%=basePath%>/images/WFJClient/PersonalJoining/left_jt.png">
            </li>
            <li style="width: 80%;">选择会员类型</li>
            <li style="width: 10%;"></li>
            <div class="clearfix"></div>
        </ul>
    </header>
    <div class="content">
        <ul class="cho_mil">
            <li>
                <img src="images/cho1.png">
                <div class="cho_txt">
                    <h3>中国经济联营平台</h3>
                    <h4>&#65509;10000000</h4>
                    <img class="cho_img" src="images/gou.png">
                    <div class="clearfix"></div>
                </div>
                <div class="clearfix"></div>
                <hr style="width: 97%;border-top: 1px solid #ddd;margin: 0 0 0 3%;">
            </li>
            <li>
                <img src="images/cho2.png">
                <div class="cho_txt">
                    <h3>大型企业平台管理系统</h3>
                    <h4>&#65509;36000</h4>
                    <img class="cho_img" src="images/gou.png">
                </div>
                <div class="clearfix"></div>
                <hr style="width: 97%;border-top: 1px solid #ddd;margin: 0 0 0 3%;">
            </li>
            <li>
                <img src="images/cho3.png">
                <div class="cho_txt">
                    <h3>中型企业平台管理系统</h3>
                    <h4>&#65509;18000</h4>
                    <img class="cho_img" src="images/gou.png">
                </div>
                <div class="clearfix"></div>
                <hr style="width: 97%;border-top: 1px solid #ddd;margin: 0 0 0 3%;">
            </li>
            <li>
                <img src="images/cho4.png">
                <div class="cho_txt">
                    <h3>小型企业平台管理系统</h3>
                    <h4>&#65509;18000</h4>
                    <img class="cho_img" src="images/gou.png">
                </div>
                <div class="clearfix"></div>
                <hr style="width: 97%;border-top: 1px solid #ddd;margin: 0 0 0 3%;">
            </li>
            <li>
                <img src="images/cho5.png">
                <div class="cho_txt">
                    <h3>微型企业平台管理系统</h3>
                    <h4>&#65509;18000</h4>
                    <img class="cho_img" src="images/gou.png">
                </div>
                <div class="clearfix"></div>
                <hr style="width: 97%;border-top: 1px solid #ddd;margin: 0 0 0 3%;">
            </li>
            <li>
                <img src="images/cho6.png">
                <div class="cho_txt">
                    <h3>小微型企业平台管理系统</h3>
                    <h4>&#65509;18000</h4>
                    <img class="cho_img" src="images/gou.png">
                </div>
                <div class="clearfix"></div>
                <hr style="width: 97%;border-top: 1px solid #ddd;margin: 0 0 0 3%;">
            </li>
            <li>
                <img src="images/cho7.png">
                <div class="cho_txt">
                    <h3>供应商企业平台管理系统</h3>
                    <h4>&#65509;18000</h4>
                    <img class="cho_img" src="images/gou.png">
                </div>
                <div class="clearfix"></div>
                <hr style="width: 97%;border-top: 1px solid #ddd;margin: 0 0 0 3%;">
            </li>
            <li>
                <img src="images/cho8.png">
                <div class="cho_txt">
                    <h3>创业商城业主会员</h3>
                    <h4>&#65509;1680</h4>
                    <img class="cho_img" src="images/gou.png">
                </div>
                <div class="clearfix"></div>
                <hr style="width: 97%;border-top: 1px solid #ddd;margin: 0 0 0 3%;">
            </li>
            <li>
                <img src="images/cho9.png">
                <div class="cho_txt">
                    <h3>微分金商城业主会员</h3>
                    <h4>&#65509;680</h4>
                    <img class="cho_img" src="images/gou.png">
                </div>
                <div class="clearfix"></div>
                <hr style="width: 97%;border-top: 1px solid #ddd;margin: 0 0 0 3%;">
            </li>
            <li>
                <img src="images/cho10.png">
                <div class="cho_txt">
                    <h3>代理商商城业主会员</h3>
                    <h4>&#65509;100</h4>
                    <img class="cho_img" src="images/gou.png">
                </div>
                <div class="clearfix"></div>
                <hr style="width: 97%;border-top: 1px solid #ddd;margin: 0 0 0 3%;">
            </li>
            <li>
                <img src="images/cho11.png">
                <div class="cho_txt">
                    <h3>粉丝</h3>
                    <h4>&#65509;0</h4>
                    <img class="cho_img" src="images/gou.png">
                </div>
                <div class="clearfix"></div>
                <hr style="width: 97%;border-top: 1px solid #ddd;margin: 0 0 0 3%;">
            </li>
        </ul>
    </div>
</div>
<script>
    var basePath="<%=basePath%>";
	var ppid = "${ppid }"   
	$(".pub_top1").find("li").eq(0).click(function(){
		document.location.href=basePath+"ea/wfjplatform/ea_getPlatformByPpid.jspa?ppid="+ppid+"&type=summary";
	});
</script>

<script>
    $(document).ready(function(){
        $("header").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
        $(".content").attr("style","margin-top:"+$(window).height()*0.08+"px;height:"+$(window).height()*0.92+"px;overflow: auto;");
        $(".content2").attr("style","margin-top:"+$(window).height()*0.08+"px;height:"+$(window).height()*0.92+"px;overflow: auto;");


        $(".pla_buy").click(function(){
            $(".content-b1").css("display","none");
            $(".content-b2").show();
        });
        $(".content-b2 header ul li:nth-child(1) img").click(function(){
            $(".content-b2").css("display","none");
            $(".content-b1").show();
        });

        $(".cho_mil li").click(function(){
            var cho=$(this).find("h3").text();
            $(".pla_buy p").text(cho);
            $(".content-b2").css("display","none");
            $(".content-b1").show();
            $(this).attr("style","background: url('images/gou.png') 14rem 18px no-repeat;background-size: 1rem;").siblings().attr("style","");
        });
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