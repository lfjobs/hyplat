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
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/secure/nfc/InspectList.css"/>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/WFJClient/secure/nfc/InspectList.js"></script>
    <title>安全巡查列表</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var pageNumber = ${pageForm==null?0:pageForm.pageNumber};
        var pageCount = ${pageForm==null?0:pageForm.pageCount};
        var companyID = "${param.companyID}";
    </script>
</head>
<body>
<div class="content">
    <section class="sec-nav sec-hide">
        <input type="hidden" name="sDate" id="sDate"/>
        <input type="hidden" name="eDate" id="eDate"/>
        <ul>
            <li class="clearfix">
                <p class="passDraft p-today" >今日</p>
            </li>
            <li class="clearfix">
                <p class="passDraft p-yesterday">昨日</p>
            </li>
            <li class="clearfix">
                <p class="passDraft p-week">7日</p>
            </li>
            <li class="clearfix">
                <p class="passDraft p-month">30日</p>
            </li>
            <li class="clearfix">
                <p class="passDraft p-year">今年</p>
            </li>
            <li class="clearfix">
                <p class="passDraft p-lastYear">去年</p>
            </li>
            <li class="clearfix">
                <p class="edit"><img src="<%=basePath%>images/ea/production/drive/att_search.png">查询</p>
            </li>
        </ul>
    </section>
    <section class="sec-list" id="pc-sec">
        <ul class="ul">
            <li class="clearfix">
                <div class="title-pc">
                    <div class="ln-p" title="点编号">点编号</div>
                    <div class="sn-p" title="芯片号">芯片号</div>
                    <%--<div class="oask-p" title="安全类别">安全类别</div>--%>
                    <div class="siType-p" title="级别">级别</div>
                    <div class="siType-p" title="级别">级别</div>
                    <div class="siType-p" title="级别">级别</div>
                    <div class="staff-p" title="责任人">责任人</div>
                    <div class="img-p pc" title="图片">图片</div>
                    <div class="vodel-p pc" title="视频">视频</div>
                    <div class="state-p" title="状态">状态</div>
                </div>
            </li>
        </ul>
    </section>
    <section class="sec-list" id="phone-sec">
        <ul class="ul">
        </ul>
    </section>
</div>
<!--表单提示-->
<div class="div-tingyong">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt=""/></p>
        <div class="div-box">
            <p class="tittle-p"></p>
            <div class="clearfix">
                <p class="right close-tingyong">确定</p>
            </div>
        </div>
    </div>
</div>

<div class="div-chaxun">
    <div class="div-box">
        <p class="p-top">
            请输入查询信息
        </p>
        <div class="p-inp clearfix">
            <label>安全点编号</label>
            <input type="number" placeholder="请填写安全点编号" name="ln" id="ln" min="1"
                   oninput="this.value=this.value.replace(/\D/g);" value="${ln}" autocomplete="off"/>
        </div>
        <div class="p-inp clearfix">
            <label>责任人</label>
            <input type="text" placeholder="请填写巡查人" name="staffName" id="staffName" value="${staffName}"
                   autocomplete="off"/>
        </div>
        <div class="p-inp clearfix">
            <label>安全类别</label>
            <input type="text" placeholder="请填写安全类别" name="oaskName" id="oaskName" value="${oaskName}"
                   autocomplete="off"/>
        </div>
        <div class="p-inp clearfix">
            <%--<label>隐患级别</label>
            <div class="div-radio">--%>
                <div class="div-name radio">
                    <label for="radio-0">全部</label>
                    <input type="radio" name="siType" value="" class="my_radio" id="radio-0" checked/>
                </div>
                <div class="div-name radio">
                    <label for="radio-1">绿</label>
                    <input type="radio" name="siType" value="02" class="my_radio" id="radio-1"/>
                </div>
                <div class="div-name radio">
                    <label for="radio-2">黄</label>
                    <input type="radio" name="siType" value="01" class="my_radio" id="radio-2"/>
                </div>
                <div class="div-name radio">
                    <label for="radio-3">红</label>
                    <input type="radio" name="siType" value="00" class="my_radio" id="radio-3"/>
                </div>
            <%--</div>--%>
        </div>
        <p class="p-bottom">
            查询
        </p>
    </div>
</div>
</body>
</html>
