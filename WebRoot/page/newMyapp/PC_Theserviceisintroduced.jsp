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
    <title>招商服务介绍</title>
    <link rel="stylesheet" href="<%=basePath %>/page/newMyapp/css/swiper.min.css" type="text/css">
    <link rel="stylesheet" href="<%=basePath %>/page/newMyapp/css/pagination.css" type="text/css">
    <link href="<%=basePath %>page/newMyapp/css/newStyle.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath %>/page/newMyapp/js/jquery.pagination.js"></script>
    <script type="text/javascript" src="<%=basePath %>/page/newMyapp/js/swiper.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>page/newMyapp/js/toShareCode.js"></script>
    <!--ie8及以下浏览器不兼容-->
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<%=basePath %>page/newMyapp/js/html5shiv.min.js"></script>
    <script src="<%=basePath %>page/newMyapp/js/respond.min.js"></script>
    <![endif]-->
    
    <script>
		var basePath = '<%=basePath%>';
		var pageNumber = 0;
		var pageCount;
		var suid = '${map.list[0][11]}';
		var goodsid = '${map.list[0][13]}';
		
		var titleJudge = '${titleJudge}';
		
		var a = '<%=((Staff) session.getAttribute("key_staff"))==null?"":((Staff) session.getAttribute("key_staff")).getStaffName()%>';
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
            <div class="bid_head">
                <h2>招标招聘</h2>
                <ul>
                    <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=07&seven=00"><li>招标商品</li></a>
                    <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=07&seven=01"><li>招聘人才</li></a>
                    <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=07&seven=02"><li class="active">招商产品</li></a>
                </ul>
            </div>

        </div>

    <div class="prod_int_con">
        <div class="prod_nav">招标招聘>招商服务><a href="#;">${map.list[0][2] }</a></div>
        <div class="top" style="min-height: 430px;">
            <div class="left">
                <div class="swiper-container prod_swiper-container">
                    <div class="swiper-wrapper">
                    	<div class="swiper-slide"><img src="<%=basePath%>${map.list[0][4]}"></div>
                    </div>
                    <div class="swiper-button-prev"><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-left.png"></div>
                    <div class="swiper-button-next"><img src="<%=basePath%>/page/newMyapp/images/newPCHomepage/ico-right.png"></div>
                </div>
                <div class="swiper-pagination"></div>
            </div>
            <div class="right">
                <i></i><h3>${map.list[0][2] }</h3>
                <div class="text" style="margin-top: 5px;height: 100px;">
                    <p>发布时间：${map.list[0][12]}</p>
                    <p>发布人：${map.comlist[0][1]}</p>
                </div>
                <div class="investment">
                    <h5 class="title">选择招商类别</h5>
                    <ul>
                    	<c:forEach items="${map.list}" var="s">
                    		<c:choose>  
							   <c:when test="${s[7] eq '00'}">
							       <li>
		                               <h5>${s[6] }元</h5>
			                           <p>贴牌佣金</p>
	                        	   </li>  
							   </c:when> 
							   <c:when test="${s[7] eq '01'}">
							       <li>
		                               <h5>${s[6] }元</h5>
			                           <p>设备安装佣金</p>
	                        	   </li>   
							   </c:when> 
							   <c:when test="${s[7] eq '02'}">
							       <li>
		                               <h5>${s[6] }元</h5>
			                           <p>省级代理佣金</p>
	                        	   </li>   
							   </c:when> 
							   <c:when test="${s[7] eq '03'}">
							       <li>
		                               <h5>${s[6] }元</h5>
			                           <p>县级代理佣金</p>
	                        	   </li>  
							   </c:when> 
							   <c:when test="${s[7] eq '04'}">
							       <li>
		                               <h5>${s[6] }元</h5>
			                           <p>村级代理佣金</p>
	                        	   </li>  
							   </c:when>  
							</c:choose>
                    		
                    	</c:forEach>
                    </ul>
                </div>
                <div class="btn">
                    <button>我要投标</button>
                    <h4>联系电话：<span>${map.comlist[0][5]}</span></h4>
                </div>
            </div>
        </div>
        <div class="bottom">
            <ul class="title">
                <li class="active" id="projects">招商信息</li>
                <li id="Investment">招商要求</li>
            </ul>
            <ul class="prod_btm_con">
                <li class="projects">
	                <c:forEach items="${map.func}" var="f">
	                	${f}
	                </c:forEach>
                </li>
                <li class="Investment" style="display: none;">
					<!-- js拼接 -->
                </li>
            </ul>
        </div>
    </div>

    </div>
    <div id="footer">
        <div class="text footer_txt">
            <!-- <p><a href="#;">天使云服务</a><a href="#;">联系我们</a><a href="#;">商务合作</a><a href="#;">网站地图</a><a href="#;">免责声明</a></p> -->
            <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<span style="padding-left: 50px;">京ICP备10034132号 </span></p>
            <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室热线<span>电话:010-64167113</span></p>
            <p>版权所有：北京天太世统科技有限公司</p>
        </div>
    </div>
<!--返回顶部-->
<a href="#header" class="return"><img src="<%=basePath%>/page/newMyapp/images/return.png" alt=""></a>
<script language="JavaScript">
    /*多个商品*/
    var mySwiper = new Swiper('.prod_swiper-container',{
        autoplay: 3000,
        autoplayDisableOnInteraction : false,
        loop: true,
        pagination : '.swiper-pagination',
        paginationType : 'fraction',
        simulateTouch : false,
        prevButton:'.swiper-button-prev',
        nextButton:'.swiper-button-next',
    })
</script>
<script>
    $(document).ready(function(){
    	ajax();
    
        /*头部*/
        $(".bid_head ul li").click(function(){
            $(this).addClass("active").parents(".bid_head ul a").siblings().find("li").removeClass("active");
        });

        /*选择招商类别*/
        $(".investment ul li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });

        /*招标规格参数选择*/
        $(".prod_int_con .bottom .title li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });
        $(".prod_int_con .bottom .title #projects").click(function(){
            $(".projects").show().siblings().hide();
        });
        $(".prod_int_con .bottom .title #specification").click(function(){
            $(".specification").show().siblings().hide();
        });
        $(".prod_int_con .bottom .title #Investment").click(function(){
            $(".Investment").show().siblings().hide();
        });


    })
		//根据suid查询招商要求
   		function ajax(){
			var url = basePath + "ea/newpcend/sajax_ea_InvestmentDemand.jspa";
			$.ajax({
				url : url,
				type : "post",
				async : true,
				dataType : "json",
				data:{
					"pss.suid":suid,
					"ppk.goodsID":goodsid
				},
				success : function(data) {
					var result = eval("(" + data + ")");
					if(result!=null){
						var map = result.map;
						var list = result.list;
						if(map!=null && map.func){
							var a =[];
							$(map.func).each(function(i,dom){
								a.push(this);
							})
							$(".prod_btm_con").find(".Investment").append(a.join(""));
						}
						if(list!=null){
							var b =[];
							$(list).each(function(i,dom){
								b.push("<div class='swiper-slide'>");
								b.push("<img src='"+basePath+this.imgurl+"'>");
								b.push("</div>");
							})
							$(".swiper-wrapper").append(b.join(""));
						}
						
					}
				},
				error : function(data) {
					console.log("获取失败");
				}
			});
		}

</script>

</body>
</html>