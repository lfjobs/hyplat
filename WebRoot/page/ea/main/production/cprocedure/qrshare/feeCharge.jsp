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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/production/qrshare/feeCharge.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/production/cprocedure/qrshare/feeCharge.js" type="text/javascript" charset="utf-8"></script>
    <title>&lrm;</title>
    <script type="text/javascript">

        var basePath = "<%=basePath%>";
        var pageNumber  = ${pageForm==null?0:pageForm.pageNumber};
        var pageCount  = ${pageForm==null?0:pageForm.pageCount};

        var totalPct = "${totalPct}";
        var companyId = "${companyId}";

        



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
        <li>
           收费标准
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
            <input type="text" name="" id="search" placeholder="场地编号/场地名称" />
        </div>
        <div><input type="button" name="" id="qsearch" value="搜索" /></div>
    </section>
    <section class="sec-nav sec-hide">
        <!--sec-hide-->
        <ul class="clearfix" >


            <li class="clearfix ">
                <p class="draft"><img src="<%=basePath%>js/jqModal/css/images_blue/add.png"/>添加</p>

            </li>

            <li class="clearfix">
                <p class="edit"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>修改</p>
            </li>


            <li class="clearfix">
                <p class="del"><img src="<%=basePath%>images/ea/office/contract/selectp/del.png"/>删除</p>
            </li>
            <li class="clearfix">
                <p class="submitAudit"><img src="<%=basePath%>js/jqModal/css/images_blue/examine16.png"/>审批</p>
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
                    <div class="date-p pc" title="编号">编号</div>
                    <div class="date-p" title="单价费用">单价费用</div>
                    <div class="date-p " title="时间单位">时间单位</div>
                    <div class="date-p pc" title="场地编号">场地编号</div>
                    <div class="date-p" title="场地名称">场地名称</div>
                    <div class="date-p pc" title="责任人">责任人</div>
                    <div class="date-p" title="状态">状态</div>
                    <div class="date-p " title="车类型">车类型</div>
                    <div class="date-p pc" title="免费时长(分钟)">免费时长(分钟)</div>

                </div>


            </li>
            <c:forEach  items="${pageForm.list}" var="f" varStatus="v">


                <c:if test="${v.index+1 eq fn:length(pageForm.list)}">
                    <li class="clearfix last1" id="${f[0]}">
                </c:if>
                <c:if test="${v.index+1 ne fn:length(pageForm.list)}">
                    <li class="clearfix" id="${f[0]}">

                </c:if>
                <div class="title-pc">
                    <div class="sex">
                        <span class="ppid" style="display: none;">${f[18]}</span>
                        <span class="goodsid" style="display: none;">${f[19]}</span>
                        <span class="startUsing" style="display: none;">${f[8]}</span>
                        <img class="img-01" src="<%=basePath%>/images/ea/office/contract/selectp/img_02.png">
                        <img class="img-02" src="<%=basePath%>/images/ea/office/contract/selectp/img_03.png"></div>

                    <div class="date-p pc" title="${f[1]}">${f[1]}</div>
                    <div class="date-p" title="${f[12]}"><fmt:formatNumber type="number" value="${f[13]*(1+totalPct/100)}" maxFractionDigits="2" minFractionDigits="2"/></div>
                    <div class="date-p" title="${f[13]}">
                        <c:choose>
                        <c:when test="${f[9] eq '0'}">
                            <span>小时</span>
                        </c:when>
                        <c:when test="${f[9] eq '1'}">
                            <span>包天</span>
                        </c:when>
                        <c:when test="${f[9] eq '2'}">
                            <span>包月</span>
                        </c:when>
                        <c:when test="${f[9] eq '3'}">
                            <span>包年</span>
                        </c:when>
                    </c:choose>

                        <c:choose>
                            <c:when test="${f[17] eq '0'}">
                                <span>-当天(00点结束)</span>
                            </c:when>
                            <c:when test="${f[17] eq '24'}">
                                <span>-24小时制</span>
                            </c:when>
                            <c:when test="${f[17] eq '8'}">
                                <span>-8小时制</span>
                            </c:when>
                            <c:otherwise>

                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="date-p pc" title="${f[4]}">${f[4]}</div>

                    <div class="date-p" title="${f[5]}">${f[5]}</div>

                    <div class="date-p pc" title="${f[7]}">${f[7]}</div>
                    <div class="date-p" >
                    <c:choose>
                        <c:when test="${f[8] eq '00'}">
                            <span class="start" style="color:#0D9908" >审核通过</span>
                        </c:when>
                        <c:when test="${f[8] eq '01'}">
                            <span class="start" style="color:#F00" >草稿</span>
                        </c:when>
                        <c:when test="${f[8] eq '03'}">
                            <span class="start" style="color:#F00" >驳回</span>
                        </c:when>
                        <c:when test="${f[8] eq '04'}">
                            <span class="start" style="color:#ffa91b" >审核中</span>
                        </c:when>
                    </c:choose>
                    </div>
                    <div class="date-p" >
                    <c:choose>
                        <c:when test="${f[10] eq 'p'}">
                            <span>私家车</span>
                        </c:when>
                        <c:when test="${f[10] eq 'c'}">
                            <span>教练车</span>
                        </c:when>
                    </c:choose>
                    </div>
                    <div class="date-p pc" title="${f[11]}">${f[11]}</div>
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
