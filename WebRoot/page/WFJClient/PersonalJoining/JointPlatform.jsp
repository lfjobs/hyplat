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
<title>县域联营经济平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/bootstrap.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/WFJClient/style_platform.css">
	<script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/toucher.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/WFJClient/search_x.css">
    <script type="text/javascript" src="<%=basePath%>/js/common/common.js"></script>
</head>

<body>
<header>
    <ul class="pub_top1">
        <li style="width: 10%;">
            <img src="<%=basePath%>/images/WFJClient/PersonalJoining/left_jt.png">
        </li>
        <li style="width: 80%;">${productDesign.goodsName }</li>
        <li style="width: 10%;" class="head_Rimg">
        	<img src="<%=basePath%>/images/WFJClient/PersonalJoining/att_search.png">
        </li>
        <div class="clearfix"></div>
    </ul>
</header>
<div class="content">
    <div class="china_ban">
        <img src="<%=basePath%>/${productDesign.image }">
    </div>
    <div class="china_nav">
        <ul>
            <li class="col-lg-2 col-md-4 col-sm-4 col-xs-4">
                <a href="#;">
                <img src="<%=basePath%>/images/WFJClient/PersonalJoining/ico-nav1.png">
                <h5>简介</h5>
                </a>
            </li>
            <li class="col-lg-2 col-md-4 col-sm-4 col-xs-4">
                <a href="#;">
                <img src="<%=basePath%>/images/WFJClient/PersonalJoining/ico-nav2.png">
                <h5>文化</h5>
                </a>
            </li>
            <li class="col-lg-2 col-md-4 col-sm-4 col-xs-4">
                <a href="#;">
                <img src="<%=basePath%>/images/WFJClient/PersonalJoining/ico-nav3.png">
                <h5>新闻资讯</h5>
                </a>
            </li>
            <li class="col-lg-2 col-md-4 col-sm-4 col-xs-4">
                <a href="#;">
                <img src="<%=basePath%>/images/WFJClient/PersonalJoining/ico-nav4.png">
                <h5>公告</h5>
                </a>
            </li>
            <%-- <li class="col-lg-2 col-md-4 col-sm-4 col-xs-4">
                <a href="#;">
                <img src="<%=basePath%>/images/WFJClient/PersonalJoining/ico-nav7.png">
                <h5>加入平台</h5>
                </a>
            </li> --%>
            <li class="col-lg-2 col-md-4 col-sm-4 col-xs-4">
                <a href="#;">
                <img src="<%=basePath%>/images/WFJClient/PersonalJoining/ico-nav6.png">
                <h5>入驻公司</h5>
                </a>
            </li>
            <div class="clearfix"></div>
        </ul>
    </div>
    <div class="china_grd">
        <div class="china_txt_hed">
            <p></p>
            <h5>经济联营平台</h5>
            <p></p>
            <h4>Platform</h4>
        </div>
        <div class="zwdiv"></div>
        <div class="china_case">
            <ul>
            <c:forEach items="${list }" var="list" varStatus="st">
            	<c:if test="${st.index<6 }">
	            	<li id="${list[1] }" class="col-lg-2 col-md-4 col-sm-4 col-xs-4">
	                    <div>
	                        <h4>${list[0] }</h4>
	                        <p>经济联营平台</p>
	                        <img src="<%=basePath%>/${list[2] }">
	                    </div>
	                </li>
	                <c:if test="${(st.index+1)%3 ==0 }">
	                	<div class="clearfix"></div>
	                </c:if>
	            </c:if>
            </c:forEach>
            </ul>
            <div class="clearfix"></div>
        </div>
        <div class="china_look china_look1">
            <h4><span>查看更多</span><img src="<%=basePath%>/images/WFJClient/PersonalJoining/cz.png"></h4>
        </div>
    </div>
    <div class="china_grd china_grd2">
        <div class="china_txt_hed">
            <h5>新闻资讯</h5>
            <h4>NEWS</h4>
        </div>
        <div class="zwdiv"></div>
        <s:iterator value="maplist1" var="ml">
        	<div class="china_mil" id="${ml.value[3] }">
	            <img src="<%=basePath%>${ml.value[2] }" alt="">
	            <div class="china_mil_txt china_mil_txt2">
	                <h4>${ml.value[0] }</h4>
	                <h5 class="news_info" limit="30">${ml.value[5] }</h5>
	            </div>
	            <div class="clearfix"></div>
	            <p><c:if test="${ml.value[4]!='' }">${productDesign.goodsName }平台</c:if> 
	            <c:if test="${ml.value[4]=='' }">${ml.value[6] }</c:if> 
	            </p>
	            <p>${fn:substring(ml.value[1], 0, 10)}</p>
	        </div>
        </s:iterator>
        <div class="china_look">
            <h4><a href="<%=basePath%>ea/wfjplatform/ea_platformNews.jspa?ppid=${ppid }"><span>查看更多</span><img src="<%=basePath%>/images/WFJClient/PersonalJoining/cz.png"></a></h4>
        </div>
    </div>
    <div>
        <img width="100%" src="<%=basePath%>/images/WFJClient/PersonalJoining/video.jpg">
    </div>
    <div class="china_txt_hed" align="center">
        <h5>平台公告</h5>
        <h4>ANNOUNCEMENT</h4>
    </div>
    <div class="zwdiv"></div>
    
    <div id="myCarousel" class="carousel slide carousel2" data-ride="carousel" data-interval="3000" data-pause="hover">
        <!-- 轮播（Carousel）指标 -->
        <ol class="carousel-indicators carousel-indicators2">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
        </ol>
        <!-- 轮播（Carousel）项目 -->
        <div class="carousel-inner">
        	<s:iterator value="maplist2"  var="ml">
	            <div class="item" id='${ml.value[3] }'>
	                <div class="china_mil">
	                    <div class="china_mil_txt china_mil_txt3">
	                        <h4>${ml.value[0] }</h4>
	                        <h5 limit="40">${ml.value[4] }</h5>
	                    </div>
	                    <div class="clearfix"></div>
	                    <p>${fn:substring(ml.value[1], 0, 10)}</p>
	                </div>
	            </div>
            </s:iterator>
        </div>
        <!-- 轮播（Carousel）导航 -->
        <a class="carousel-control left left2" id="carleft" href="#myCarousel"
           data-slide="prev">&lsaquo;</a>
        <a class="carousel-control right right2" id="carright" href="#myCarousel"
           data-slide="next">&rsaquo;</a>
    </div>
    
    <div class="china_look" align="center">
        <h4><a href="<%=basePath%>ea/wfjplatform/ea_platformNotice.jspa?ppid=${ppid }"><span>查看更多</span><img src="<%=basePath%>/images/WFJClient/PersonalJoining/cz.png"></a></h4>
    </div>
    <hr style="border-top: 3px solid #ddd;margin-bottom: 0;">
    <div class="china_txt_hed" align="center">
        <h5>平台简介</h5>
        <h4>INTRODUCTION</h4>
    </div>
    <div class="zwdiv"></div>
    <div class="china_intro" limit="235">
        <s:iterator value="maplist3" var="ml">
	    	${ml.value[1]}
	    </s:iterator>
    </div>
    <div class="china_look" align="center">
        <h4><a href="<%=basePath%>ea/wfjplatform/ea_platformSummary.jspa?ppid=${ppid }&type=summary"><span>查看更多</span><img src="<%=basePath%>/images/WFJClient/PersonalJoining/cz.png"></a></h4>
    </div>
    <div>
        <img width="100%" src="<%=basePath%>/images/WFJClient/PersonalJoining/china-banner2.jpg">
    </div>
    <div class="china_txt_hed" align="center">
        <h5>入驻公司</h5>
        <h4>COMPANY</h4>
    </div>
    <div class="zwdiv"></div>
    <div class="china_ruzhu">
        <ul></ul>
        <div class="clearfix"></div>
        <div class="china_look china_look2" style="margin-bottom:30px" align="center">
        	<h4><a href="<%=basePath%>ea/wfjplatform/ea_platformCompany.jspa?ppid=${ppid }"><span>查看更多</span><img src="<%=basePath%>/images/WFJClient/PersonalJoining/cz.png"></a></h4>
	    </div>
    </div>
</div>
<div id="back_top"></div>

	<div id="search_wrap" style="display: none">
        <div class="sear_wrap flex flex_align_center">
            <div class="sear_box flex flex_align_center clearfix flex_1">
                <i></i>
                <input type="text" placeholder="搜索地区名称" class="flex_1 flex_2">
            </div>
            <a href="javascript:;" id="search_cancel">取消</a>
        </div>
        <ul class="sear_list">
        </ul>

    </div>
	<script>
		$(function(){
			var Top=$("#back_top").offset().top;
        	$("#back_top").hide();//初始化隐藏
       		 var cH=document.documentElement.clientHeight;
       		 $(".content").scroll(function(){
          	 	 var scrollT=$(this).scrollTop();
           		 if(scrollT>Top){
              		 $("#back_top").show();
           		 }else{
               		 $("#back_top").hide();
            }
        	})
        	$("#back_top").click(function(){
        		$(".content").scrollTop(0);
        	});
        	$(".head_Rimg").click(function(){
        		$("#search_wrap").show();
        		$("#search_cancel").one("click",function(){
        			$("#search_wrap").hide();
        		})
        	})
        	

	})
	</script>	
	<script>
	var basePath="<%=basePath%>";
	var ppid = "${ppid }"
	var goodsName = "${productDesign.goodsName }";
	var pagenumber = 0;
	var height = 0;
	var t;
	var pagecount;
	//返回按钮
	if(goodsName!="中国"){
		$(".pub_top1").find("li").eq(0).click(function(){
			document.location.href = basePath +"ea/wfjplatform/ea_backPlatform.jspa?ppid="+ppid+"&type=summary";
		});
	}else{
		$(".pub_top1").find("li").eq(0).click(function(){
			document.location.href = basePath +"ea/wfjshop/ea_getWFJshops.jspa";
		});
	}
	$(".flex_2").bind("propertychange input",function(){
		pagenumber=0;
		$(".sear_list").empty();
		loaded();
	});
	function loaded(){
		clearTimeout(t);
		pagenumber += 1;
		var content = $(".flex_2").val();
		var url = "sajax_ea_AjaxPlatform.jspa?content="+content;
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dateType : "json",
			data:{						
				"pageForm.pageNumber":pagenumber,
			},
			success : function (data) {
				var member = eval("("+data+")");
				var pageForm = member.pageForm;
				if (pageForm == null) {
					return;
				} else {
					var htl =new Array();
					var list = pageForm.list;
					pagenumber = pageForm.pageNumber;
					pagecount = pageForm.pageCount;
					for(var i = 0;i<list.length;i++){
						var search = list[i];
						if(search[2]==null){
							search[2]="";
						}else{
							search[2]="-"+search[2];
						}
						htl.push("<li id='"+search[1]+"'>"+search[0]+"<span style='color:rgb(163, 164, 144)'>"+search[2]+"</span></li>");
					}
					$(".sear_list").append(htl.join(""));
					getHeight(".sear_list",".content","loaded()");
				}
			},
			error:function(data){
				alert("获取项目失败");
			}
		});
		
	}	
	//搜索结果进入平台
	$(document).on("click",".sear_list li",function (){
		var ppid = $(this).attr("id");
		document.location.href=basePath+"ea/wfjplatform/ea_getPlatformByPpid.jspa?ppid="+ppid+"&type=summary";
	});
	//查看更多平台
	$(".china_look1").click(function(){
		var url = "ea/wfjplatform/sajax_ea_AjaxCity.jspa?ppid="+ppid;
		$.ajax({
			url : url,
			type : "get",
			async : false,
			dataType : "json",
			success : function (data) {
				var member = eval("(" + data + ")");
				var list = member.list;
				if (list.length == 0) {
					return;
				} else {
					$(".china_case").find("ul").empty();
					var htl = new Array();
					for ( var int = 0; int < list.length; int++) {
						var platform = list[int];
						htl.push("<li id='"+ platform[1]+"' class='col-lg-2 col-md-4 col-sm-4 col-xs-4'>");
						htl.push("<div><h4>"+platform[0]+"</h4><p>"+platform[3]+"</p>");
						htl.push("<img src='"+basePath+platform[2]+"'></div></li>");
						if((int+1)%3==0){
							htl.push("<div class='clearfix'></div>");
						}
					}
					$(".china_case").find("ul").append(htl.join(""));
				}
			},
			error:function(data){
				alert("获取项目失败");
			}
		});
	});
	$(document).on("click",".china_case li",function (){
		var ppid = $(this).attr("id");
		document.location.href=basePath+"ea/wfjplatform/ea_getPlatformByPpid.jspa?ppid="+ppid+"&type=summary";
	});
	//判断是否有下级平台
	var url = "ea/wfjplatform/sajax_ea_AjaxCity.jspa?ppid="+ppid;
	$.ajax({
		url : url,
		type : "get",
		async : false,
		dataType : "json",
		success : function (data) {
			var member = eval("(" + data + ")");
			var list = member.list;
			if (list.length == 0) {
				$(".china_grd").css("display","none");
				$(".china_admin").css("display","none");
				$(".china_adn").css("display","none");
				$(".china_grd2").css("display","block");
				return;
			}
		},
		error:function(data){
			alert("获取项目失败");
		}
	});
	
	//入驻公司展示
	var url = "ea/wfjplatform/sajax_ea_AjaxCompany.jspa?ppid="+ppid;
	$.ajax({
		url : url,
		type : "get",
		async : false,
		dataType : "json",
		data:{						
		  "pageForm.pageNumber":1,
		  "pageForm.pageSize":6
		},
		success : function (data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageForm;
			if (pageForm == null) {
				return;
			} else {
				var list=pageForm.list;
				var htl = new Array();
				for ( var int = 0; int < list.length; int++) {
					var company = list[int];
					htl.push("<li class='col-lg-2 col-md-4 col-sm-4 col-xs-4'>");
					htl.push("<img src='"+basePath+company[1]+"'>");
					htl.push("<h5>"+company[0]+"</h5>");
				}
				$(".china_ruzhu").find("ul").append(htl.join(""));
			}
		},
		error:function(data){
			alert("获取项目失败");
		}
	});
	$(".china_intro").attr("style","text-indent: 2em;font-size: 0.7rem;line-height: 1.7;color:#000");
	//页面头部导航
	$(".china_nav").find("li").click(function(){
		var name = $(this).find("h5").text();
		if(name == "公告"){
			document.location.href=basePath+"ea/wfjplatform/ea_platformNotice.jspa?ppid="+ppid;
		}else if(name == "入驻公司"){
			document.location.href=basePath+"ea/wfjplatform/ea_platformCompany.jspa?ppid="+ppid;
		}else if(name == "简介"){
			document.location.href=basePath+"ea/wfjplatform/ea_platformSummary.jspa?ppid="+ppid+"&type=summary";
		}else if(name == "文化"){
			document.location.href=basePath+"ea/wfjplatform/ea_platformSummary.jspa?ppid="+ppid+"&type=culture";
		}else if(name == "新闻资讯"){
			document.location.href=basePath+"ea/wfjplatform/ea_platformNews.jspa?ppid="+ppid;
		}else if(name == "加入平台"){
			alert("暂未开放");
			//document.location.href=basePath+"ea/wfjplatform/ea_platformAdd.jspa?ppid="+ppid;
		}
	});
	//进入新闻详情
	$(".china_mil").click(function(){
		var goodsid = $(this).attr("id");
		document.location.href=basePath+"ea/wfjplatform/ea_newdetail.jspa?ppid="+ppid+"&goodsid="+goodsid;
	});
	//点击公告进入详情
	$(".item").click(function(){
		var goodsid = $(this).attr("id");
		document.location.href=basePath+"ea/wfjplatform/ea_noticeDetail.jspa?ppid="+ppid+"&goodsid="+goodsid;
	});
	</script>

	<script>
	    $(document).ready(function(){
	        $("header").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
	        $(".content").attr("style","overflow:auto;margin-top:"+$(window).height()*0.08+"px;height:"+$(window).height()*0.92+"px");
			$(".carousel-inner").find("div").eq(0).addClass("active");
			loaded();
	        //轮播获取焦点
	        var myTouch = util.toucher(document.getElementById('myCarousel'));
	        myTouch.on('swipeLeft',function(e){
	            $('#carright').click();
	        }).on('swipeRight',function(e){
	            $('#carleft').click();
	        });

	    });
	</script>

	<script>
	    $(function(){
	        var n = $('header').height();
	        $('.china_admin').css('padding-top',n-20+"px");
	    })
	</script>
	<script>
	    jQuery.fn.limit=function(){
		    var self = $("[limit]");
		    self.each(function(){
			    var objString = $(this).text();
			    var objLength = $(this).text().length;
			    var num = $(this).attr("limit");
			    if(objLength > num){
			    $(this).attr("title",objString);
			    objString = $(this).text(objString.substring(0,num) + "...");
			    }
			    })
		    }
		    $(function(){
		    $("[limit]").limit();
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