<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/production/qrshare/news_search.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

    <title>&lrm;</title>
    <script type="text/javascript">

        var basePath = "<%=basePath%>";


    </script>
</head>
<body>
<header>
    <ul class="clearfix">

        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self" >

                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" />

            </a>
        </li>

        <li>
         资讯搜索
        </li>
        <li>

        </li>
    </ul>
</header>
<div class="content">

    <section class="sec-search">
        <div class="box clearfix">
            <label for="">
                <img src="<%=basePath%>images/ea/office/contract/img_02.png"/>
            </label>
            <input type="text" name="" id="search" placeholder="输入关键词" />
        </div>
        <div><input type="button" name="" id="cancel" value="取消" /></div>
    </section>

    <section class="search1">
     <div class="pbr-div"><label>发布人</label><input type="text" id="fbr"></div>
    <div>
        <p class="date-p">发布时间</p>

        <p class="time-p">起时间<input type="text" id="start" readonly autocomplete="off" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'end\')}'})" ></p>
        <p class="time-p">止时间<input type="text" id="end" readonly autocomplete="off" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'start\')}'})"></p>
    </div>

    </section>

    <section class="search2">
        <div class="title-div"> <label for="">
            <img src="<%=basePath%>images/ea/office/contract/img_02.png"/>资讯类别
        </label></div>
    <ul>

       <li><div>培训报名</div><div>生日报名</div><div>婚宴报名</div><div>活动报名</div><div>宴请报到</div></li>
        <li><div>其他报到</div><div>资讯带货</div><div>资讯文章</div><div>其它广告</div><div>慈善捐赠</div></li>
   </ul>



    </section>
    <section class="sec-bottom">
        <p>
            搜索
        </p>
    </section>

</div>

<script type="text/javascript">

$(function(){

    $(".search2 li div").click(function(){
       if($(this).hasClass("active")){
           $(this).removeClass("active");
       }else{
           $(this).addClass("active");
       }


    })
    $("#cancel").click(function(){
        window.history.go(-1);return false;

    })
    $(".sec-bottom p").click(function(){
        var  searchc = $("#search").val();
        var  fbr = $("#fbr").val();
        var  start = $("#start").val();
        var  end = $("#end").val();
        var  cate = "";
        $(".search2 li .active").each(function(){

            cate+=$.trim($(this).text())+",";
        });
        if(cate!=""){
            cate = cate.substring(0,cate.length-1);
        }

        document.location.replace(basePath+"ea/wfjshop/ea_getNewsList.jspa?typeNews=&query=query&searchc="+searchc+"&fbr="+fbr+"&start="+start+"&end="+end+"&cate="+cate);

    });

})
</script>
</body>

</html>
