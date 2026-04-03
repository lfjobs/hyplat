<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";


    // 设置不要缓存页面
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // 设置过期时间为0.

%>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/entry/e_emplyment.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/5l5C/human/entry/e_emplyment.js" type="text/javascript" charset="utf-8"></script>
    <title>&lrm;</title>
    <script type="text/javascript">

        var basePath = "<%=basePath%>";
        var pageNumber  = ${pageForm==null?0:pageForm.pageNumber};
        var pageCount  = ${pageForm==null?0:pageForm.pageCount};

         var status="${status}";






    </script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li class="back-li">
            <a onclick="javascript: window.history.go(-1);return false;"  target="_self">

                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" >
            </a>
        </li>
        <li class="title-li">
           应来面试管理
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
            <input type="text" name="" id="search" placeholder="姓名/身份证号/手机号" />
        </div>
        <div><input type="button" name="" id="qsearch" value="搜索" /></div>
    </section>
    <section class="sec-nav sec-hide">
        <!--sec-hide-->
        <ul class="clearfix navs act nav2" >


            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
            </li>


        </ul>

        <ul class="clearfix navs nav4" >


            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
            </li>

            <li class="clearfix">
                <p class="ms" onclick="opr('m')"><img src="<%=basePath%>js/jqModal/css/images_blue/examine16.png"/>面试登记</p>
            </li>

            <%--<li class="clearfix">--%>
                <%--<p class="print"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>打印面试登记</p>--%>
            <%--</li>--%>
            <%--<li class="clearfix">--%>
                <%--<p class="print"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>导出</p>--%>
            <%--</li>--%>
        </ul>
        <ul class="clearfix navs nav5" >


            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
            </li>

            <li class="clearfix">
                <p class="ms" onclick="opr('m')"><img src="<%=basePath%>js/jqModal/css/images_blue/examine16.png"/>面试登记</p>
            </li>
            <%--<li class="clearfix">--%>
                <%--<p class="print"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>导出</p>--%>
            <%--</li>--%>



        </ul>
        <ul class="clearfix navs nav6" >


            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
            </li>
            <%--<li class="clearfix">--%>
                <%--<p class="ms" onclick="opr('m')"><img src="<%=basePath%>js/jqModal/css/images_blue/examine16.png"/>面试登记</p>--%>
            <%--</li>--%>
            <%--<li class="clearfix">--%>
                <%--<p class="print"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>打印面试登记</p>--%>
            <%--</li>--%>
            <%--<li class="clearfix">--%>
                <%--<p class="print"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>导出</p>--%>
            <%--</li>--%>



        </ul>
        <ul class="clearfix navs nav7">


            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
            </li>

            <li class="clearfix">
                <p class="bs" onclick="exam('b')"><img src="<%=basePath%>js/jqModal/css/images_blue/examine16.png"/>笔试结果</p>
            </li>
            <%--<li class="clearfix">--%>
                <%--<p class="print"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>导出</p>--%>
            <%--</li>--%>
            <%----%>
        </ul>

        <ul class="clearfix navs nav8 nav14 nav15">


            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
            </li>

        </ul>

        <ul class="clearfix navs nav9">


            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
            </li>

        </ul>
        <ul class="clearfix navs nav10" >


            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
            </li>

            <li class="clearfix">
                <p class="ks" onclick="exam('k')"><img src="<%=basePath%>js/jqModal/css/images_blue/examine16.png"/>口试结果</p>
            </li>

            <%--<li class="clearfix">--%>
                <%--<p class="print"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>导出</p>--%>
            <%--</li>--%>



        </ul>


        <ul class="clearfix navs nav13">


            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
            </li>
            <li class="clearfix">
                <p class="zhpd" onclick="exam('z')"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>综合评定</p>
            </li>
        </ul>

        <ul class="clearfix navs nav16">


            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
            </li>
            <li class="clearfix">
                <p class="zt"onclick="opr('zt')" ><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>转通知入职</p>
            </li>
        </ul>

        <ul class="clearfix navs nav17">


            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
            </li>

        </ul>
        <ul class="clearfix navs nav18">


            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
            </li>
            <li class="clearfix">
                <p class="zhpd" onclick="opr('zt')"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>转通知入职</p>
            </li>
        </ul>

        <ul class="clearfix navs nav19 nav20">


            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
            </li>
            <li class="clearfix">
                <p class="zhpd" onclick="exam('not')"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>入职通知</p>
            </li>
        </ul>

        <ul class="clearfix navs  nav21">


            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
            </li>

        </ul>
    </section>
    <section class="sec-list" id="pc-sec" >
        <ul class="ul">


            <li class="clearfix">
                <div class="title-pc">

                    <div class="sex">
                        选择
                    </div>
                    <div class="date-p" title="姓名">姓名</div>
                    <div class="date-s" title="身份证">身份证</div>
                    <div class="date-p pc" title="手机号">手机号</div>
                    <div class="date-p pc" title="应聘岗位">岗位</div>
                    <div class="date-p pc" title="面试地点">面试地点</div>
                    <div class="date-p pc" title="面试考官">面试考官</div>
                    <c:if test="${param.status eq '5'}">

                        <div class="date-s pc" title="预约时间">预约时间</div>
                    </c:if>
<c:if test="${param.status ne '5'}">
                    <div class="date-p pc" title="面试登记时间">登记时间</div>
</c:if>
                    <div class="date-p" title="状态">状态</div>

                </div>


            </li>
            <c:forEach  items="${pageForm.list}" var="f" varStatus="v">


                <c:if test="${v.index+1 eq fn:length(pageForm.list)}">
                    <li class="clearfix last1" id="${f.auditionID}">
                </c:if>
                <c:if test="${v.index+1 ne fn:length(pageForm.list)}">
                    <li class="clearfix" id="${f.auditionID}">

                </c:if>
                <div class="title-pc">
                    <div class="sex">

                        <img class="img-01" src="<%=basePath%>/images/ea/office/contract/selectp/img_02.png">
                        <img class="img-02" src="<%=basePath%>/images/ea/office/contract/selectp/img_03.png">
                    </div>
                    <span class="sts" style="display: none;">${f.status}</span>
                    <span class="zts" style="display: none;">${f.ztState}</span>
                    <div class="date-p" title="${f.staffName}">${f.staffName}</div>
                    <div class="date-s " title="${f.staffIdentityCard}">${f.staffIdentityCard eq null?'无':f.staffIdentityCard}</div>
                    <div class="date-p pc" title="${f.reference}">${f.reference eq null?'无':f.reference}</div>
                    <div class="date-p pc" title="${f.auditionPost}">${f.auditionPost eq null?'无':f.auditionPost}</div>
                    <div class="date-p pc" title="${f.place}">${f.place eq null?'无':f.place}</div>
                    <div class="date-p pc" title="${f.examiner}">${f.examiner eq null?'无':f.examiner}</div>

                    <c:if test="${param.status eq '5'}">
                        <div class="date-s pc" title="${fn:substring(f.auditionDate,0, 16)}">${ f.auditionDate eq null?'无':fn:substring(f.auditionDate,0, 16)}</div>


                    </c:if>
                    <c:if test="${param.status ne '5'}">

                    <div class="date-p pc" title="${fn:substring(f.regisDate,0, 10)}">${ f.regisDate eq null?'无':fn:substring(f.regisDate,0, 10)}</div>
                    </c:if>
                    <c:if test="${param.status eq '13'||param.status eq '14'||param.status eq '15'}">
                        <div class="date-p" title="${f.zpState eq '1'?'评定合格':f.zpState eq '2'?'评定不合格':'未判定'}">${f.zpState eq '1'?'评定合格':f.zpState eq '2'?'评定不合格':'未判定'}</div>


                    </c:if>
                    <c:if test="${param.status eq '16'||param.status eq '17'||param.status eq '18'}">
                        <div class="date-p" title="${f.ztState eq null?'未转':'已转'}">${f.ztState eq null?'未转':'已转'}</div>


                    </c:if>

                    <c:if test="${param.status eq '19'||param.status eq '20'||param.status eq '21'}">
                        <div class="date-p" title="${f.ztState eq '3'?'已通知':'未通知'}">${f.ztState eq '3'?'已通知':'未通知'}</div>


                    </c:if>

                    <c:if test="${param.status eq '4'||param.status eq '5'||param.status eq '6'}">
                        <div class="date-p" title="${f.status eq '01'?'待登记':'已登记'}">${f.status eq '01'?'待登记':'已登记'}</div>


                    </c:if>
                    <c:if test="${param.status eq '2'}">
                        <div class="date-p" title="已邀请">已邀请</div>


                    </c:if>
                    <c:if test="${param.status eq '7'||param.status eq '8'||param.status eq '9'}">
                        <div class="date-p" title="${f.bsState eq '2'?'笔试不合格':f.bsState eq '1'?'笔试合格':'未判定'}">${f.bsState eq '2'?'笔试不合格':f.bsState eq '1'?'笔试合格':'未判定'}</div>


                    </c:if>
                    <c:if test="${param.status eq '10'||param.status eq '11'||param.status eq '12'}">
                        <div class="date-p" title="${f.ksState eq '2'?'口试不合格':f.ksState eq '1'?'口试合格':'未判定'}">${f.ksState eq '2'?'口试不合格':f.ksState eq '1'?'口试合格':'未判定'}</div>


                    </c:if>
                </div>
                </li>
            </c:forEach>



        </ul>
    </section>



</div>


<!--表单提示-->
<div class="div-tingyong">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
        <div class="div-box">
            <p class="titlep"></p>
            <div class="clearfix">
                <p class="left close-tingyong">取消</p>
                <p class="right close-confirm">确定</p>
            </div>
        </div>
    </div>
</div>

</body>
<script>
    window.onload =  function () {



        var clientWidth = document.documentElement.clientWidth;
        if(clientWidth>=960){

            $(".pc").show();
        }else {
            $(".pc").hide();
        }
    }



</script>
</html>
