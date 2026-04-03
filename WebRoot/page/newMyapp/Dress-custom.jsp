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
    <title>服务专区</title>

    <link href="<%=basePath %>page/newMyapp/css/style.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath %>page/newMyapp/css/newStyle.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>page/newMyapp/js/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="<%=basePath %>page/newMyapp/js/toShareCode.js" type="text/javascript"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<%=basePath %>page/newMyapp/js/html5shiv.min.js"></script>
    <script src="<%=basePath %>page/newMyapp/js/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
    var basePath = "<%=basePath%>";
    var titleJudge = null;
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
    <div class="content con">
        <div class="ind_con_head">
            <ul>
                <a id="index"><li>网站首页</li></a>
                <a id="Web-New"><li>网站资讯</li></a>
                <a id="bg"><li>中联园办公</li></a>
                <a id="Dress-custom"><li class="active">服务专区</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=03&seven=00" class='investment'><li>中联园招商</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=05" class='mall'><li>数字地球商城</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=07&seven=00" class='recruitment'><li>招标招聘</li></a>
                <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=09" class='aboutUs'><li>关于我们</li></a>
            </ul>
        </div>
        <div class="web_con">
            <%@ include file="/page/newMyapp/left1.jsp" %>
            <div class="web_con_right Dress_con_right">
                <%@ include file="/page/newMyapp/reg1.jsp" %> 
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
function dianji(obj){
	var url;
	if(obj=="SEO优化"){
  		url = basePath+"page/newMyapp/Web-New-details.jsp?type=SEO优化&nu=服务";
    }else if(obj=="黑科技"){
    	url = basePath+"page/newMyapp/Web-New-details.jsp?type=黑科技&nu=服务";
    }else if(obj=="003"){
    	url = basePath+"page/newMyapp/Web-New-details.jsp?type=003&nu=服务";
    }else if(obj=="004"){
    	url = basePath+"page/newMyapp/Web-New-details.jsp?type=004&nu=服务";
    }else if(obj=="005"){
    	url = basePath+"page/newMyapp/Web-New-details.jsp?type=005&nu=服务";
    }else if(obj=="006"){
    	url = basePath+"page/newMyapp/Web-New-details.jsp?type=006&nu=服务";
    }else if(obj=="007"){
    	url = basePath+"page/newMyapp/Web-New-details.jsp?type=007&nu=服务";
    }else if(obj=="008"){
    	url = basePath+"page/newMyapp/Web-New-details.jsp?type=008&nu=服务";
    }else if(obj=="009"){
    	url = basePath+"page/newMyapp/Web-New-details.jsp?type=009&nu=服务";
    }else if(obj=="010"){
    	url = basePath+"page/newMyapp/Web-New-details.jsp?type=010&nu=服务";
    }
		document.location.href = url;
}
function diasnji2(obj){
	var url;
	if(obj=="01"){
  		url = basePath+"page/newMyapp/Web-New-details.jsp?type0=01&nup=资料";
    }else if(obj=="02"){
    	url = basePath+"page/newMyapp/Web-New-details.jsp?type0=02&nup=资料";
    }else if(obj=="03"){
    	url = basePath+"page/newMyapp/Web-New-details.jsp?type0=03&nup=资料";
    }else if(obj=="04"){
    	url = basePath+"page/newMyapp/Web-New-details.jsp?type0=04&nup=资料";
    }else if(obj=="05"){
    	url = basePath+"page/newMyapp/Web-New-details.jsp?type0=05&nup=资料";
    }else if(obj=="06"){
    	url = basePath+"page/newMyapp/Web-New-details.jsp?type0=06&nup=资料";
    }else if(obj=="07"){
    	url = basePath+"page/newMyapp/Web-New-details.jsp?type0=07&nup=资料";
    }else if(obj=="08"){
    	url = basePath+"page/newMyapp/Web-New-details.jsp?type0=08&nup=资料";
    }else if(obj=="09"){
    	url = basePath+"page/newMyapp/Web-New-details.jsp?type0=09&nup=资料";
    }else if(obj=="10"){
    	url = basePath+"page/newMyapp/Web-New-details.jsp?type0=10&nup=资料";
    }
    document.location.href = url;
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
            var url = basePath+"/ea/wfjshop/ea_getWFJshops.jspa";
            document.location.href = url;
        });
        
         $("#bg").click(function(){
             var url = basePath+"/ea/newpcend/ea_skip.jspa?titleJudge=02";
            document.location.href = url;
        });
        
         $("#Web-New").click(function(){
             var url = basePath+"/ea/newpcend/ea_skip.jspa?titleJudge=01";
            document.location.href = url;
        });
          $("#Dress-custom").click(function(){
            var url = basePath+"page/newMyapp/Dress-custom.jsp";
            document.location.href = url;
        });
          $("#register").click(function(){
            var url = basePath+"page/newMyapp/register.jsp";
            document.location.href = url;
        });
           /* $("#Web-New-details").click(function(){
            var url = basePath+"page/newMyapp/Web-New-details.jsp";
            document.location.href = url;
        }); */
        
        

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



        //选项卡
        $(".web_con_left2 p").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });
        $(".Front").click(function(){
            $("#Front").show().siblings().css("display","none");
            $("#ziliao1").show();
            $("#ziliao2").hide();

        });
        $(".message").click(function(){
            $("#message").show().siblings().css("display","none");
             $("#ziliao2").show();
            $("#ziliao1").hide();
        });
        $(".notice").click(function(){
            $("#notice").show().siblings().css("display","none");
        });
    })
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
</script>
</body>
</html>