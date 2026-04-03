<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="hy.ea.bo.human.Staff" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>微分金数字地球联营平台</title>
    <meta http-equiv="keywords" content="中联园区,微分金,中联园区微分金,数字地球,北京天太世统科技有限公司"/>
    <link rel="stylesheet" href="<%=basePath %>page/newMyapp/lunbo/focusStyle.css">
    <link href="<%=basePath %>page/newMyapp/css/newStyle.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>page/newMyapp/css/banner.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath %>page/newMyapp/css/download_pc.css" rel="stylesheet" type="text/css" >
    <script src="<%=basePath %>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="<%=basePath %>page/newMyapp/js/banner2.js" type="text/javascript"></script>
    <script src="<%=basePath %>page/newMyapp/js/banner.js" type="text/javascript"></script>

    <!--ie8及以下浏览器不兼容-->
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<%=basePath %>page/newMyapp/js/html5shiv.min.js"></script>
    <script src="<%=basePath %>page/newMyapp/js/respond.min.js"></script>
    <![endif]-->
    <%-- <script src="<%=basePath %>page/newMyapp/lunbo/lunbo.js"></script> --%>
    <script src="<%=basePath %>page/newMyapp/js/toShareCode.js" type="text/javascript"></script>
    
    <script src="<%=basePath %>page/newMyapp/js/newPCHomepage.js" type="text/javascript"></script>
    	<script>
		var basePath = '<%=basePath%>';
		var pageNumber = 0;
		var pageCount;
		var titleJudge = '00';

		//ajax触发一次限定条件
		var search = "smart";
		
		var a = '<%=session.getAttribute("key_staff")==null?"":((Staff) session.getAttribute("key_staff")).getStaffName()%>';
	</script>
</head>
<body>
<div id="header">
    <ul>
        <li class="logo">
            <a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa"><img src="<%=basePath%>page/newMyapp/images/wfj.png" alt="" class="log"></a>
        </li>
        <li class="scroll">
            <img src="<%=basePath%>page/newMyapp/images/scroll.png" alt="">
        </li>
    </ul>
</div>
    <div class="content">
        <div class="ind_con_head">
            <ul>
                <a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa" class='homePage'><li>网站首页</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=01" class='information'><li>网站资讯</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=02" class='work'><li>中联园办公</li></a>
                <a href="<%=basePath%>/page/newMyapp/Dress-custom.jsp"><li>服务专区</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=03&seven=00" class='investment'><li>中联园招商</li></a>
                <!-- <a href="#;" class='member'><li>会员中心</li></a> -->
               	<a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=05" class='mall'><li>数字地球商城</li></a> 
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=06" class='readExtensively'><li>中联园博览</li></a>
              	<a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=07&seven=00" class='recruitment'><li>招标招聘</li></a>
           		<!-- <a href="#;" class='platform'><li>县域联营经济平台</li></a> -->
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=09" class='aboutUs'><li>关于我们</li></a>
           </ul>
        </div>
		<!--banner图-->
        <div class="banner_con">
            <div class="banner_con_">
                <div class="wrap" id="wrap">

                        <div class="slide" id="slide">
                            <ul>
                                	<!-- 共17组 js拼接 -->
                            </ul>
                            <div class="arrow" id="arrow">
                                <a href="javascript:" class="prev"></a>
                                <a href="javascript:" class="next"></a>
                            </div>
                        </div>
                </div>
            </div>
        </div>

        <!--热门活动-->
        <!-- <div class="mil">
            <ul class="mil_head">
                <li class="active">热门活动</li>

                <a href="#;" class="right">更多 >></a>
            </ul>

            <div class="activity_mil">
                <p class="tit">积分活动</p>
                <ul>
                    <li>
                    <a href="#;">
                        <img src="new_images/activity_1.png">
                        <p>超值积分兑换豪礼</p>
                    </a>
                    </li>
                    <li>
                    <a href="#;">
                        <img src="new_images/activity_1.png">
                        <p>超值积分兑换豪礼</p>
                    </a>
                    </li>
                    <li>
                    <a href="#;">
                        <img src="new_images/activity_1.png">
                        <p>超值积分兑换豪礼</p>
                    </a>
                    </li>
                    <li>
                    <a href="#;">
                        <img src="new_images/activity_1.png">
                        <p>超值积分兑换豪礼</p>
                    </a>
                    </li>
                    <li>
                    <a href="#;">
                        <img src="new_images/activity_1.png">
                        <p>超值积分兑换豪礼</p>
                    </a>
                    </li>
                </ul>
            </div>
            <div class="activity_mil draw">
                <p class="tit">会员抽奖</p>
                <ul>
                    <li>
                    <a href="#;">
                        <img src="new_images/draw_1.png">
                        <p>超值积分兑换豪礼</p>
                    </a>
                    </li>
                    <li>
                    <a href="#;">
                        <img src="new_images/draw_1.png">
                        <p>超值积分兑换豪礼</p>
                    </a>
                    </li>
                    <li>
                    <a href="#;">
                        <img src="new_images/draw_1.png">
                        <p>超值积分兑换豪礼</p>
                    </a>
                    </li>
                    <li>
                    <a href="#;">
                        <img src="new_images/draw_1.png">
                        <p>超值积分兑换豪礼</p>
                    </a>
                    </li>
                    <li>
                    <a href="#;">
                        <img src="new_images/draw_1.png">
                        <p>超值积分兑换豪礼</p>
                    </a>
                    </li>
                </ul>
            </div>
            <div class="activity_mil upgrade">
                <p class="tit">会员升级</p>
                <ul>
                    <li>
                    <a href="#;">
                        <img src="new_images/draw_1.png">
                        <p>会员升级省11</p>
                    </a>
                    </li>
                    <li>
                    <a href="#;">
                        <img src="new_images/draw_1.png">
                        <p>会员升级省11</p>
                    </a>
                    </li>
                    <li>
                    <a href="#;">
                        <img src="new_images/draw_1.png">
                        <p>会员升级省11</p>
                    </a>
                    </li>
                    <li>
                    <a href="#;">
                        <img src="new_images/draw_1.png">
                        <p>会员升级省11</p>
                    </a>
                    </li>
                    <li>
                    <a href="#;">
                        <img src="new_images/draw_1.png">
                        <p>会员升级省11</p>
                    </a>
                    </li>
                </ul>
            </div>

        </div> -->

        <!--网站资讯 / 新闻文章-->
        <div class="mil Web_news">
            <ul class="mil_head">
                <li id="web_news" class="active">资讯文章</li>
            </ul>
            <div class="Web_news_con">
                <div class="left">
                    <!--网站资讯-->
                    <div class="web_news_mil web_news">
                        <ul class="Web_news_con_c">
							<!-- js拼接 --> 
                        </ul>
                        <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=01" class="more">查看更多>></a>
                    </div>

                </div>
                <div class="right">
                    <div class="title">
                        <h4>热门推广</h4>
                        <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=01">更多>></a>
                    </div>
                    <ul class="hot">
                        <!-- js拼接 -->
                    </ul>
                </div>
            </div>
        </div>
        <!--中联园周边-->
        <div class="mil_ rim">
            <div class="mil">
                <ul class="mil_head">
                    <li class="active">中联园周边</li>

                    <!-- <a href="#;" class="right">更多 >></a> -->
                </ul>
                <div class="rim_con">
                    <div class="rim_swiper">
                        <ul>
                            <!-- js拼接 -->
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <!--数字地球商城-->
        <div class="mil shopMall">
            <ul class="mil_head">
                <li class="active">数字地球商城</li>

                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=05" class="right">更多 >></a>
            </ul>
        </div>
        <div class="mil_shop_con">
            <ul>
                <li class="shop_li no1">
                    <h3 style="color: #ff0d5d">精品商城</h3>
                    <ul class="shop_con">
                        <!-- js拼接 -->
                    </ul>
                </li>
                <li class="shop_li no2">
                    <h3 style="color: #ee33b7">溯源商城</h3>
                    <ul class="shop_con">
                        <!-- js拼接 -->
                    </ul>
                </li>
                <li class="shop_li no3">
                    <h3 style="color: #be6cee">热门推荐</h3>
                    <ul class="shop_con">
                        <!-- js拼接 -->
                    </ul>
                </li>
                <li class="shop_li no4" style="border-right: none;">
                    <h3 style="color: #ff7ab5">新品推广</h3>
                    <ul class="shop_con">
                        <!-- js拼接 -->
                    </ul>
                </li>
            </ul>
        </div>
	    <!--招商招标-->
        <div class="mil recruit">
            <ul class="mil_head">
                <li class="active">招商招标</li>

                <%-- <a href="<%=basePath%>ea/newpcend/ea_skip.jspa?titleJudge=07&seven=00" class="right">更多 >></a> --%>
            </ul>
            <div class="attract_con">
                <div class="left">
                    <div class="top">
                        <h2 style="color: #ff0d5d;">招标商品</h2>
                        <ul>
                            <!-- js拼接 -->
                        </ul>
                    </div>
                    <div class="bottom">
                        <h2 style="color: #ee33b7;">招商服务</h2>
                        <ul>
                            <!-- js拼接 -->
                        </ul>
                    </div>
                </div>
                <div class="right">
                    <h2 style="color: #be6cee;">招聘人才</h2>
                    <div class="swiper-container-att">
                        <ul>
                            <!-- js拼接 -->
                        </ul>
                    </div>
                </div>
            </div>
        </div>

<!--         县域经济联营平台
        <div class="mil">
            <ul class="mil_head">
                <li class="active">县域经济联营平台</li>

                <a href="#;" class="right">更多 >></a>
            </ul>
            <div class="mil_terrace">
                省级经济联营平台
                <div class="mil_list sheng">
                    <h2 style="color: #599b67;">省级经济联营平台</h2>
               		<ul class="mil_con">
                       js拼接        
                    </ul>                   
                </div>
                县级经济联营平台
                <div class="mil_list xian">
                    <h2 style="color: #d53535;">县级经济联营平台</h2>
                    <ul class="mil_con">
                         js拼接    
                    </ul>
                </div>
                村级经济联营平台
                <div class="mil_list cun">
                    <h2 style="color: #845d2f;">村级经济联营平台</h2>
                    <ul class="mil_con">
                        js拼接    
                    </ul>
                </div>
                行业经济联营平台
                <div class="mil_list hang">
                    <h2 style="color: #e32728;">行业经济联营平台</h2>
                    <ul class="mil_con">
                        js拼接    
                    </ul>
                </div>
            </div>
        </div>

        国际经济联营平台
        <div class="mil">
            <ul class="mil_head">
                <li class="active">国际经济联营平台</li>

                <a href="#;" class="right">更多 >></a>
            </ul>
            <div class="internation">
                <ul class="mil_con min_inter">
                	js拼接
                </ul>
            </div>
        </div>
 -->




    </div>
    <div id="footer">
        <div class="text footer_txt">
            <!-- <p><a href="#;">天使云服务</a><a href="#;">联系我们</a><a href="#;">商务合作</a><a href="#;">网站地图</a><a href="#;">免责声明</a></p> -->
            <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<a href="https://beian.miit.gov.cn/" target="_blank"><span style="padding-left: 50px;">京ICP备10034132号 </span></a></p>
            <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室<span>热线电话:010-64167113</span></p>
            <p>版权所有：北京天太世统科技有限公司</p>
        </div>
    </div>
<div class="download_pc">
    <div class="box clearfix">
        <section>
            <img src="<%=basePath%>images/WFJClient/zhuce/download_pc_01.png"/>
        </section>
        <section>
            <h2>数字地球—共享资源共享金</h2>
            <p>成功从这里开始</p>
        </section>
<%--        <section>--%>
<%--            <div>--%>
<%--                <a href="https://apps.apple.com/cn/app/shu-zi-de-qiu/id1182214522?l=en"><img src="<%=basePath%>images/WFJClient/zhuce/download_pc_ios.png"/>--%>
<%--                iphone 版下载--%>
<%--                </a>--%>
<%--            </div>--%>
<%--            <div>--%>
<%--                <a href="<%=basePath %>upload_files/company201009046vxdyzy4wg0000000025/upload_files/ea/Android/wfj.apk">--%>
<%--                <img src="<%=basePath%>images/WFJClient/zhuce/download_pc_android.png"/>--%>
<%--                Android 版下载--%>
<%--                </a>--%>
<%--            </div>--%>
<%--        </section>--%>
        <section>
            <img src="<%=basePath%>images/WFJClient/zhuce/download_pc_code.png"/>
            <img class="download_pc_close" src="<%=basePath%>images/WFJClient/zhuce/download_pc_close.png"/>
        </section>
    </div>
</div>
<!--返回顶部-->
        <a href="#header" class="return"><img src="<%=basePath%>/page/newMyapp/images/return.png" alt=""></a>
<script>
    $(document).ready(function(){
        $(".mil_head li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });

     
        $(".banner_con .top .right img").css("height","156px");

     
        //绑定滚动条事件
        $(window).bind("scroll", function () {
            var sTop = $(window).scrollTop();
            var sTop = parseInt(sTop);

            if (sTop >= 1100) {
                $(".return").slideDown();
                $(".return").show();
            }
            else {
                $(".return").hide();
            }
        });
        
        
    })


</script>
<script type="text/javascript">
    //下方留足够的空间显示下载信息
    if($("body").find("download_pc").length==1){
        $("body").css("margin-bottom","0");
    }else{
        $("body").css("margin-bottom","144px");
    }
    //关闭
    $(".download_pc_close").click(function(){
        $(this).parents(".download_pc").remove();
        $("body").css("margin-bottom","0");
    })
</script>
</body>
</html>