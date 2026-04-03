<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<html>
<head>
    <meta charset="utf-8" />
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/production/recruit/posPub.css">
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/BuildPlatform/setHtmlFont.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/production/cprocedure/recruit/positionPublish.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/comm.js" type="text/javascript" charset="utf-8"></script>

    <!--下拉框插件-->
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/swiper/css/public.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/swiper/css/swiper.min.css"/>
   <script src="<%=basePath%>js/swiper/js/swiper.min.js" type="text/javascript" charset="utf-8"></script>
   <script src="<%=basePath%>js/swiper/js/dySelect.js" type="text/javascript" charset="utf-8"></script>
    <!--地址联动插件-->
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/layout/css/layout.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/layout/css/scs.min.css"/>
    <script src="<%=basePath%>js/layout/js/jquery.scs.min.js" type="text/javascript" charset="utf-8"></script>
   <script src="<%=basePath%>js/layout/js/CNAddrArr.min.js" type="text/javascript" charset="utf-8"></script>
    <title>发布职位</title>

    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var sccId = "${param.sccId}";
        var  pos = "${param.pos}";
        var codeid = "${param.codeid}";
        var riId =  "${recruitInfo.riId}";
        var back = "${param.back}";
        var pst = "${param.pst}";
    </script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="backs()">
                <img src="<%=basePath%>css/ea/production/back.png" >
            </a>
        </li>
        <li>
            发布职位
        </li>
        <li>

        </li>
    </ul>
</header>

<div class="content">
    <form id="mainForm" name="mainForm" method="post">
        <input type="submit" style="display: none" name="submit">
    <section class="sec-zwlb">
        <div class="clearfix">

            <p class="p-1">职位类别</p>
            <p class="p-2">${recruitInfo.positionName}</p>
            <input type="hidden" name="recruitInfo.positionName" id="positionName" value="${recruitInfo.positionName}"/>
            <input type="hidden" id="positionCode"
                   name="recruitInfo.positionCode" value="${recruitInfo.positionCode}" />
            <input type="hidden" id="riId"
                   name="recruitInfo.riId" value="${recruitInfo.riId}"/>
            <input type="hidden" id="rikey"
                   name="recruitInfo.rikey" value="${recruitInfo.rikey}"/>
            <p class="p-3">
                <img src="<%=basePath%>images/resume/a.png"/>
            </p>
        </div>
    </section>
    <section class="sec-01">
        <h2>职位名称</h2>
        <div class="div-bottom clearfix">
            <div class="div-left">
                <input type="text" name="recruitInfo.jobTitle" placeholder="请填写您要招聘的职位"   value="${recruitInfo.jobTitle}" class="jobTitle"  autocomplete="off"/>
            </div>
            <div class="div-right">
                <div class="divxl clearfix">
                    <p class="btn2">${recruitInfo.partorfull eq null ?"全职":recruitInfo.partorfull}</p>
                    <input type="hidden"
                           value="${recruitInfo.partorfull}"  name="recruitInfo.partorfull" class="partorfull"/>
                </div>
                <div>
                    <img src="<%=basePath%>images/resume/img_44.png"/>
                </div>
            </div>
        </div>
    </section>
    <div class="select_box select_box4"></div>
    <section class="sec-02 clearfix">
        <div class="divxl">
            <p class="p-top">经验要求</p>
            <p class="p-bottom btn4">${recruitInfo.workYears eq null ?"请选择":recruitInfo.workYears}</p>
            <input type="hidden"
                   value="${recruitInfo.workYears}"   name="recruitInfo.workYears" class="workYears"/>
        </div>
        <div class="divxl">
            <p class="p-top">学历要求</p>
            <p class="p-bottom btn6">${recruitInfo.education eq null ?"请选择":recruitInfo.education}</p>
            <input type="hidden"
                   value="${recruitInfo.education}" name = "recruitInfo.education" class="education" />
        </div>
        <div class="divxl">
            <p class="p-top">职位月薪</p>
            <p class="p-bottom btn7">${recruitInfo.salary eq null ?"请选择":recruitInfo.salary}</p>
            <input type="hidden"
                   value="${recruitInfo.salary}" class="salary"  name="recruitInfo.salary" />
        </div>
    </section>
    <div class="select_box select_box2"></div>

    <div class="select_box select_box6"></div>


    <div class="select_box select_box7"></div>
    <section class="sec-03">
        <p>职位要求</p>
        <textarea   name="recruitInfo.jobRequire" class="jobRequire" placeholder="请填写职位要求">${recruitInfo.jobRequire}</textarea>
        <%--<input type="text" placeholder=""  name="recruitInfo.jobRequire" class="jobRequire"   value="${recruitInfo.jobRequire}" />--%>
    </section>
    <section class="sec-03">
        <p>工作城市</p>
      <input type="text"  id="myAddrs" name="addr" data-key="1-36-37" placeholder="请选择" value="${recruitInfo.workCity}" autocomplete="off"/>
        <!--<input type="text" placeholder="请选择" />-->
        <input type="hidden" class="inputtext"
               name="recruitInfo.workCity"  id="workCity"   value="${recruitInfo.workCity}"  />
    </section>
    <section class="sec-03">
        <p>详细地址</p>
        <input type="text"  name="recruitInfo.workPlace"  placeholder="请输入上班位置" class="workPlace"  value="${recruitInfo.workPlace}" autocomplete="off"/>
    </section>
    <section class="sec-05 clearfix">
        <p>招聘人数</p>
        <div class="div-num">
            <img class="img-reduce" src="<%=basePath%>images/resume/img_45.png"/>
            <span class="span-num">${recruitInfo.personNumber eq null?1:recruitInfo.personNumber}</span>
            <input type="hidden"
                   value="${recruitInfo.personNumber}" class="personNumber"  name="recruitInfo.personNumber" />
            <img class="img-add" src="<%=basePath%>images/resume/img_46.png"/>
        </div>
    </section>
    <footer>
        <p class="pb">
            立即发布
        </p>
    </footer>
         <input type="hidden" name="sccId" value="${param.sccId}" />
        <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
                framespacing="0" height="0"></iframe>
    </form>
</div>
<div class="div-del">
    <div class="box">
        <p class="titlep">确定要删除吗？</p>
        <div class="div-yq clearfix">
            <p class="p-c">取消</p>
            <p class="p-c">确定</p>
        </div>
    </div>
</div>
</body>
<script src="<%=basePath%>js/swiper/js/index.js" type="text/javascript" charset="utf-8"></script>


</html>
