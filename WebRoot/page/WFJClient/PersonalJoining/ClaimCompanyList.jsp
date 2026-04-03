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
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/setHtmlFont.js"></script>
    <script src="<%=basePath%>js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/claim.css">
    <title>认领公司</title>
</head>
<script type="text/javascript">
    var basePath='<%=basePath%>';
    var ccompanyId="${param.ccompanyId}"
    var height = 0;
    var t;
    var pagenumber = 0;//第几页
    var pagecount;
    var cityselect = "${param.city}";
    var accuracy = "";//东经
    var dimension = "";//北纬
</script>
<body>
<!--
    <header class="com_head">
        <a href="javascript:;" class="back"></a>
        <h1>标题</h1>
        <a href="###" class="head_R">按钮</a>
    </header>
-->
<%--<div class="ss_head clearfix">--%>
    <%--<a href="javascript:history.go(-1);" class="ss_head_back"></a>--%>
    <%--<div class="ss_box">--%>
        <%--<input type="text" class="ss_head_search" placeholder="搜索公司名称">--%>
    <%--</div>--%>
    <%--<a href="javascript:;" class="ss_head_site">东直门外大街</a>--%>
<%--</div>--%>
<%--<div class="wrap_page">--%>
    <%--<div class="rl_list"></div>--%>
    <%--<a href="###" class="no_tip">若未找到您的公司，请新建公司</a>--%>
<%--</div>--%>
<header class="clearfix">
    <a href="javascript:history.go(-1)"><img src="<%=basePath%>/images/BuildPlatform/back_ico.png"></a>
    <section class="clearfix">
        <p class="box clearfix">
            <a href="<%=basePath%>page/WFJClient/PersonalJoining/CityList.jsp" class="clearfix"><span id="city" style="width: 50px;" class="box_cont txt">北京</span><img class="imgtop_1" src="<%=basePath%>/images/BuildPlatform/triangle.png" /></a>
            <input class="box_cont" type="search" name="" id="keyword" value="" />
            <img class="imgtop_2" style="margin-right: 5px;" src="<%=basePath%>/images/BuildPlatform/empty.png"/>
        </p>
    </section>
</header>
<script type="text/javascript">
    $(function () {
        $(".imgtop_2").click(function(){
            $("#keyword").val("");
        });
        $(".history p").not(".history p:first-of-type").click(function(){
            $("#keyword").val($(this).text());
            //此处触发搜索事件
        });
        $("header p input").on("input",function(){
            $(".content menu").empty();
            pagenumber = 0;
            pagecount = 0;
            loaded();
        });
    })
</script>
<div class="kong"></div>
<div class="content">
    <menu></menu>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        var winhei=$(window).height()*0.08-1;
        $("header").css("height",winhei+"px");
        $("header>a,header section").css("height",winhei+"px");
        $("header>a").css("line-height",winhei+"px");
        $(".imgtop_1").css("marginTop",($("p.box").height())*0.45+"px");
        $(".imgtop_2").css("marginTop",($("p.box").height())*(-0.60)+"px");
        $(".history").css("marginTop",winhei+1+"px");
        $("p.box").css("marginTop",winhei*0.15+"px");
        $("p.box").css("line-height",$("p.box").height()+"px");
        $(".box_cont").css('line-height',$("p.box").height()+"px")
        $("div.kong").css("height",winhei+"px");
    });
    $("#keyword").on('keypress',function(e) {
        var keycode = e.keyCode;
        var searchName = $(this).val();
        if(keycode=='13') {
            e.preventDefault();
            //请求搜索接口  (移动端点击搜索，pc端enter)
            $(".history").css("display","none")
        }
    })
</script>
<script type="text/javascript">
    $(document).ready(function () {
        loaded();
        initload();
        $(".ss_head_site").click(function(){
            if(isAndroid){
                Android.callgetRoundLocal();
            }else if(isiOS){
                var url= "func=" + 'iosMapaddress';
                window.webkit.messageHandlers.Native.postMessage(url);
            }
        });
        $(".ss_head_search").on("input",function(){
            $(".rl_list").empty();
            pagenumber = 0;
            pagecount = 0;
            loaded();
        });

    });

    function obtain(){
        var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        if (isAndroid == true) {
            console.log("安卓");
            var collection = Android.callgetLocal();//调用安卓接口
            if(collection!="-1"){
                var a = collection.split(",");
                city = a[0];//所在城市
                accuracy = a[1];//东经
                dimension = a[2];//北纬
                //$(".head_R").text(city);
            }else{
               // $(".head_R").text("未知");
            }
        } else if (isiOS == true) {
            console.log("IOS");
            var url= "func=" + 'calliosMapInfo';
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    }

    function calliosMapInfo(name){
        if(name!="-1"){
            var a = name.split(",");
            city = a[0];//所在城市
            accuracy = a[1];//东经
            dimension = a[2];//北纬
            //$(".head_R").text(city);
        }else{
            //$(".head_R").text("未知");
        }
    }

    function getHeight(){
        height = parseInt(Math.abs($(".content menu").height()-($(window).height()-$(".content menu").offset().top)));
        t=setTimeout("getHeight()", 200);
        if(height<$(window).height()){
            if(pagenumber<pagecount){
                loaded();
            }
        }
    }
    function loaded() {
        clearTimeout(t);
        pagenumber += 1;
        var companyname=$("#keyword").val();
        var city = (cityselect==""? $("#city").text():cityselect);
        var url = basePath+"/ea/industry/sajax_ea_AjaxClaimClompanyList.jspa?search="+companyname+"&city="+city;
        $.ajax({
            url: url,
            type: "get",
            async: false,
            data:{
                "pageForm.pageNumber":pagenumber,
                "accuracy":accuracy,
                 "dimension":dimension
            },
            success: function (data) {
                var member = eval("(" + data + ")");
                var pageForm = member.pageForm;
                var companystr = new Array();
                if(pageForm!=null&&pageForm.recordCount>0){
                    var companylist=pageForm.list;
                    pagecount=pageForm.pageCount;
                    pagenumber=pageForm.pageNumber;
                    for(var i = 0; i < companylist.length; i++){
                        companystr.push("<li class='clearfix'>");
                        companystr.push("   <aside>");
                        companystr.push(" <img src='"+basePath+(companylist[i][3]==null ? '/images/WFJClient/PersonalJoining/logo@2x.png':companylist[i][3])+"'>");
                        companystr.push(" </aside>");
                        companystr.push(" <section>");
                        companystr.push(" <h3 class='txt'>"+companylist[i][1]+"</h3>");
                        companystr.push(" <p class='txt'>"+companylist[i][2]+"</p>");
                        var num = companylist[i][5];
                        if(num!=null){
                            if(num>1000){
                                num = num/1000;
                                num = parseFloat(num.toFixed(2));
                                companystr.push(" <p class='txt'>"+num+"千米</p>");
                            }else{
                                companystr.push(" <p class='txt'>"+num+"米</p>");
                            }
                        }
                        companystr.push((companylist[i][4]=='00'?"<p><a href='"+basePath+"ea/industry/ea_getPerfectInformation.jspa?ccompanyID="+companylist[i][0]+"&companyName="+companylist[i][1]+"&companyAttr="+companylist[i][2]+"' class='rl_btn'>认领</a></p>":"<p style='border: .05rem solid #a0938e'><a class='rl_btn' disabled='disabled' style='color:#a0938e' >已认领</a></p>"));
                        companystr.push(" </section>");
                        companystr.push("</li>");
                    }
                    $(".content menu").append(companystr.join(""));
                    getHeight();
                }
            }
        });

    }
//    function claim(companyName,companyAttr) {
//        var url = basePath+"ea/industry/sajax_ea_getAjaxPerfectInformation.jspa";
//        $.ajax( {
//            url:url,
//            type: "get",
//            async: false,
//            success: function (data) {
//                var member = eval("(" + data + ")");
//                var flag = member.flag;
//                if(flag==1){
//                    window.location.href = basePath+"ea/industry/ea_Login.jspa";
//                }else if(flag==3){
//                    window.location.href = basePath+"ea/industry/ea_perfectInformation.jspa?companyName="+companyName+"&companyAttr="+companyAttr;
//                }
//            }
//        })
//    }


    //定位城市，选择城市
    function initload(){
        if(cityselect!=""){
            $("#city").text(cityselect);
        }else{
            $.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js', function(_result) {
                if (remote_ip_info.ret == '1') {
                    var city = remote_ip_info.city;
                    $("#city").text(city);
                } else {
                    alert('没有找到匹配的IP地址信息！');
                }
            });
        }
    }
</script>
</body>
</html>
