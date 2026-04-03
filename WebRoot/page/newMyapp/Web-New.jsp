<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=9" />
    <title>网站资讯</title>

    <link href="<%=basePath %>page/newMyapp/css/style.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<%=basePath %>page/newMyapp/js/html5shiv.min.js"></script>
    <script src="<%=basePath %>page/newMyapp/js/respond.min.js"></script>
    <![endif]-->
</head>
<script>
var basePath = "<%=basePath%>";
</script>
<body>
<div id="header">
    <ul>
        <li class="logo">
           <a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025&search=中联园区"><img src="<%=basePath %>page/newMyapp/images/wfj.png" alt="" class="log"></a>

         
        </li>
        <li class="name">
            <div>
                <h3>中国互联网行业领导品牌</h3>
                <h5>北京天太世统科技有限公司</h5>
            </div>
        </li>
        <li class="login">
            <!--  <img src="images/phone.png" alt="">
              <div>
                  <h5>天太世统诚挚招商热线</h5>
                  <h3>010-64167133</h3>
              </div>-->

            <a id="login"><input type="button" value="登录"></a>
            <a id="register" ><input type="button" value="注册" id="zc"></a>
        </li>
    </ul>
</div>
    <div class="content con">
        <div class="ind_con_head">
            <ul>
                <a id="index" ><li>网站首页</li></a>
                <a id="Web-New" ><li class="active">网站资讯</li></a>
                <a id="bg" ><li>办公</li></a>
                <a id="Dress-custom" ><li>服务专区</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=03&seven=00" class='investment'><li>中联园招商</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=05" class='mall'><li>数字地球商城</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=07&seven=00" class='recruitment'><li>招标招聘</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=09" class='aboutUs'><li>关于我们</li></a>
            </ul>
        </div>
        <div class="web_con">
            <div class="web_con_left">
                <div class="web_con_left1">
                    网站资讯
                </div>
                <div class="web_con_left2">
                    <p class="active Front">电商资讯</p>
                  
                </div>
                
                <%-- <ul class="web_con_left4">
                    <a href="#;">
                        <li>
                            <h3>交通运输部关于修改修改</h3>
                            <p><img src="<%=basePath %>page/newMyapp/images/news.png" alt=""><span limit="17">《交通运输部关于修改《道路运输从业人才</span></p>
                        </li>
                    </a>
                    <hr style="border-top: 1px solid #ddd;width: 80%;margin: 20px auto 0 auto;">
                    <a href="#;">
                        <li>
                            <h3>交通运输部关于修改修改</h3>
                            <p><img src="<%=basePath %>page/newMyapp/images/news.png" alt=""><span limit="17">《交通运输部关于修改《道路运输从业人才</span></p>
                        </li>
                    </a>
                    <hr style="border-top: 1px solid #ddd;width: 80%;margin: 20px auto 0 auto;">
                    <a href="#;">
                        <li>
                            <h3>交通运输部关于修改修改</h3>
                            <p><img src="<%=basePath %>page/newMyapp/images/news.png" alt=""><span limit="17">《交通运输部关于修改《道路运输从业人才</span></p>
                        </li>
                    </a>
                    <hr style="border-top: 1px solid #ddd;width: 80%;margin: 20px auto 0 auto;">
                    <a href="#;">
                        <li>
                            <h3>交通运输部关于修改修改</h3>
                            <p><img src="<%=basePath %>page/newMyapp/images/news.png" alt=""><span limit="17">《交通运输部关于修改《道路运输从业人才</span></p>
                        </li>
                    </a>
                </ul> --%>
            </div>
           
            	
            
            <div class="web_con_right">
                <div id="Front">
                 <c:forEach items="${pageForm.list}" var="a">
                    <a >
                        <div class="mil" onclick="dianji(this)">
                        <input type="hidden" id="goodid" value="${a[3]}">
                    <input type="hidden" id="share" value="${a[0]}">
                            <img src="<%=basePath %>${a[2]}" alt="">
                            <div class="text">
                                <h2>${a[0]}</h2>
                                <c:if test="${a[4]=='新闻' }">
                                <p limit="35">资讯</p>
                                </c:if>
                                <span>${fn:substring(a[1],0,10)}</span>
                            </div>
                        </div>
                       
                        <hr style="border-top: 1px solid #ddd;width: 90%;margin: 0 auto 20px auto;">
                    </a>
                     </c:forEach>
                </div>
                
            </div>
        </div>
        <a href="#header" class="return"><img src="<%=basePath %>page/newMyapp/images/return.png" alt=""></a>
    </div>
    <div id="footer">
        <div class="text footer_txt">
           
            <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<span style="padding-left: 50px;">京ICP备10034132号 </span></p>
            <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室热线<span>电话:010-64167113</span></p>
            <p>版权所有：北京天太世统科技有限公司</p>
            
        </div>
    </div>
<script>
var ccompanyId = "${ccompanyId }";
var search = "${search }";
function dianji(obj){

var goods=$(obj).find("#goodid").val();
	
	var share = $(obj).find("#share").val();
	
	document.location.href = basePath
					+ "ea/wfjshop/ea_getWFJnews.jspa?ccompanyId="+ccompanyId+"&search="+search+"&goodsid=" + goods + "&share="
					+ share;
 
}
    $(document).ready(function(){
	
	
	
        $(".ind_con_head ul li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });
         $("#login").click(function(){
            var url = basePath+"page/newMyapp/login.jsp";
            document.location.href = url;
        });
         $("#index").click(function(){
            var url = basePath+"ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025&search=中联园区";
            document.location.href = url;
        });
         $("#bg").click(function(){
            var url = basePath+"page/ea/index.jsp";
            document.location.href = url;
        });
         $("#register").click(function(){
            var url = basePath+"page/newMyapp/register.jsp";
            document.location.href = url;
        });
        $("#Web-New").click(function(){
             var url = basePath+"ea/wfjshop/ea_getNews.jspa";
            document.location.href = url;
        });
         $("#Dress-custom").click(function(){
            var url = basePath+"page/newMyapp/Dress-custom.jsp";
            document.location.href = url;
        });

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

        //多少字后隐藏
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
        });

        //选项卡
        $(".web_con_left2 p").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });
        $(".Front").click(function(){
            $("#Front").show().siblings().css("display","none");
        });
        $(".message").click(function(){
            $("#message").show().siblings().css("display","none");
        });
        $(".notice").click(function(){
            $("#notice").show().siblings().css("display","none");
        });
    })
</script>
</body>
</html>