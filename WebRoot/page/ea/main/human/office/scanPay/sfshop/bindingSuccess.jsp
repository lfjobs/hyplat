<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>${tip==null?"验证成功":tip}</title>
    <script type="text/javascript" src="<%=basePath%>js/restaurant/sfshop/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/office_ea/scanPay/sfshop/new_style.css">
    <script type="text/javascript" src="<%=basePath%>js/restaurant/sfshop/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/restaurant/sfshop/new-page.js"></script>
</head>
<body>
<%--<header>--%>
    <%--<ul>--%>
        <%--<li style="width: 10%;">--%>
            <%--<a href="javascript:history.go(-1)" class="wechat"><img src="<%=basePath%>/images/ea/office/scanPay/sfshop/left_jt.png"></a>--%>
        <%--</li>--%>
        <%--<li style="width: 80%;" class="tiw">${tip==null?"验证成功":tip}</li>--%>
        <%--<li style="width: 10%;" class="wechat"></li>--%>
    <%--</ul>--%>
<%--</header>--%>
<div class="content_hidden">
    <div class="content">
        <div class="con Binding">
            <img src="<%=basePath%>/images/ea/office/scanPay/sfshop/ico-Congratulations.png" class="congra">
            <br/>
            <c:if test='${mealNum!="-1"}'>
                <div class="Take">
                    <p class="ta">取餐号<span id="meid">${mealNum}</span></p>
                    <p class="ke">商家叫号后，请凭取餐号取餐</p>
                </div>
            </c:if>
            <input type="button" id="download"  class="wechat" onclick="download()" value="下载微分金APP方便您下次使用">
            <%--<div class="btn_wrap clearfix">
                <a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.xiaofeng.androidframework&ckey=pybnRePxIn5iTwvS" class="android"></a>
                <a href="https://itunes.apple.com/cn/app/shu-zi-de-qiu/id1182214522?l=en&mt=8" class="ios"></a>
            </div>--%>
        </div>
    </div>
</div>

<script>
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".con").css("height",$(window).height()*0.92+"px");

    });
function download() {
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    if(isAndroid==true){
        window.location.href=basePath+"upload_files/company201009046vxdyzy4wg0000000025/upload_files/ea/Android/wfj.apk";
    }
  else if(isiOS==true) {
        window.location.href="https://itunes.apple.com/cn/app/shu-zi-de-qiu/id1182214522?l=en&mt=8";
    }
}

</script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var mealNum = "${mealNum}";
	var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    if(mealNum==null||mealNum==""){
        $("#meid").text("自动生成中请稍后...");
        ajaxMealNum();

    }

    if(mealNum=="-1") {
        // 3秒后执行函数
        setTimeout(function () {
            document.location.href = basePath
                + "ea/earth/ea_earthIndex.jspa";
            return false;
        }, 1000); // 单位：毫秒
    }
    try {
		if (isAndroid == true) {
			Android.speechOutputForAndroid("支付成功");
		} else if (isiOS == true) {
			console.log("声音提醒开发中");
		}
	} catch (err) {
		console.log("报错了");
	}
    
    $(function(){
        var ua = navigator.userAgent.toLowerCase();
        var isWeixin = ua.indexOf('micromessenger') != -1;
        if (!isWeixin) {

            $(".wechat").hide();
            $(".tiw").css("width","100%");
        }else{
                $(".wechat").show();

            }

    });
    function  ajaxMealNum(){

        $.ajax({
            type :"get",
            url : basePath+"/ea/wfjshop/sajax_ea_ajaxMealNum.jspa",
            async :true,
            dataType : "json",
            data:{
                ddid:ddid
            },
            success :function(data){
                var member = eval("(" + data + ")");
                var result = member.result;
                if(result==null||result==""){
                    ajaxMealNum();
                }else{
                    $("#meid").text(result);
                }

            }
        });

    }
</script>

</body>
</html>