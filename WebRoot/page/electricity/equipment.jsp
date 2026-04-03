<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>

    <meta charset="UTF-8">
    <title>用电设备</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/hqinmanage.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>

    <link rel="stylesheet" href="/css/bootstrap/bootstrap.min.css">


</head>


<body>

<div class="pc-box">
    <div class="div-box">

        <header>
            <ul class="clearfix" style="height: 3.4rem">
                <li>
                    <a onclick="window.history.go(-1);return false;" target="_self">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
                    </a>
                </li>
                <li>
                    用电设备
                </li>
                <li>

                </li>
            </ul>
        </header>

        <%--        <div style="margin: 6px;margin-bottom: 20px;">--%>
        <%--            <strong>时间</strong>--%>
        <%--            <div class="btn-group selectDate">--%>
        <%--                <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown">昨日--%>
        <%--                    <span class="caret"></span>--%>
        <%--                </button>--%>
        <%--                <ul class="dropdown-menu" role="menu">--%>
        <%--                    <li><a href="#">昨日</a></li>--%>
        <%--                    <li><a href="#">本周</a></li>--%>
        <%--                    <li><a href="#">上周</a></li>--%>
        <%--                    <li class="divider"></li>--%>
        <%--                    <li><a href="#">本月</a></li>--%>
        <%--                    <li><a href="#">上月</a></li>--%>
        <%--                    <li class="divider"></li>--%>
        <%--                    <li><a href="#">本季</a></li>--%>
        <%--                    <li><a href="#">上季</a></li>--%>
        <%--                    <li class="divider"></li>--%>
        <%--                    <li><a href="#">本年</a></li>--%>
        <%--                    <li><a href="#">上年</a></li>--%>
        <%--                </ul>--%>

        <%--            </div>--%>
        <%--        </div>--%>
        <div class="container-fluid">
            <div class="row">
                <div class="col-12">
                    <div class="scroll-container">
                        <!-- 内容过多，需要横向滚动 -->
                        <div class="scroll-content">
                            <!-- 这里放置你的内容 -->
                            <span class="itemTime">今日</span>
                            <span class="itemTime">昨日</span>
                            <span class="itemTime">本周</span>
                            <span class="itemTime">上周</span>
                            <span class="itemTime">本月</span>
                            <span class="itemTime">上月</span>
                            <span class="itemTime">本季</span>
                            <span class="itemTime">上季</span>

                            <span class="itemTime">本年</span>
                            <span class="itemTime">上年</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <div id="accordion">
            <div class="card">
                <div class="card-header">
                    <a class="card-link" data-toggle="collapse" href="#collapseOne">
                        <div class="row" style="background-color: rgb(246,246,220);padding: 10px;">
                            <div class="col-xs-4">张三</div>
                            <div class="col-xs-4">2434号房</div>
                            <div class="col-xs-4" style="text-align: right">更多></div>
                        </div>
                    </a>
                </div>
                <div id="collapseOne" class="collapse" data-parent="#accordion" style="margin-bottom: 3px">
                    <div class="card-body">
                        <div class="row" style="background-color: rgb(246,246,246);padding: 10px;">
                            <div class="col-xs-12" style="margin: 5px">
                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6 ">
                                        <strong>绑定设备号</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        25255245235252
                                    </div>
                                </div>
                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>用房号</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        20
                                    </div>
                                </div>
                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>用房名称</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        二楼304
                                    </div>
                                </div>

                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>绑定时间</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        2024-12-12 12:12:11
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>初始电度</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        10
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>绑定房租</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        已绑定
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>绑定用水</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        已绑定
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>使用状态</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        正常
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>审核意见</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        通过
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>审核人</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        李四
                                    </div>
                                </div>

                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="accordion">
            <div class="card">
                <div class="card-header">
                    <a class="card-link" data-toggle="collapse" href="#collapse2">
                        <div class="row" style="background-color: rgb(246,246,220);padding: 10px; margin-top: 5px">
                            <div class="col-xs-4">李四</div>
                            <div class="col-xs-4">34号房</div>
                            <div class="col-xs-4" style="text-align: right">更多></div>
                        </div>
                    </a>
                </div>
                <div id="collapse2" class="collapse" data-parent="#accordion" style="margin-bottom: 3px">
                    <div class="card-body">
                        <div class="row" style="background-color: rgb(246,246,246);padding: 10px;">
                            <div class="col-xs-12" style="margin: 5px">
                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6 ">
                                        <strong>绑定设备号</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        25255245235252
                                    </div>
                                </div>
                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>用房号</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        20
                                    </div>
                                </div>
                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>用房名称</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        二楼304
                                    </div>
                                </div>

                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>绑定时间</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        2024-12-12 12:12:11
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>初始电度</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        10
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>绑定房租</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        已绑定
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>绑定用水</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        已绑定
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>使用状态</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        正常
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>审核意见</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        通过
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>审核人</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        李四
                                    </div>
                                </div>

                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>


        <div id="accordion">
            <div class="card">
                <div class="card-header">
                    <a class="card-link" data-toggle="collapse" href="#collapse3">
                        <div class="row" style="background-color: rgb(246,246,220);padding: 10px;margin-top: 5px">
                            <div class="col-xs-4">王五</div>
                            <div class="col-xs-4">2号房</div>
                            <div class="col-xs-4" style="text-align: right">更多></div>
                        </div>
                    </a>
                </div>
                <div id="collapse3" class="collapse" data-parent="#accordion" style="margin-bottom: 3px">
                    <div class="card-body">
                        <div class="row" style="background-color: rgb(246,246,246);padding: 10px;">
                            <div class="col-xs-12" style="margin: 5px">
                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6 ">
                                        <strong>绑定设备号</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        25255245235252
                                    </div>
                                </div>
                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>用房号</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        20
                                    </div>
                                </div>
                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>用房名称</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        二楼304
                                    </div>
                                </div>

                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>绑定时间</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        2024-12-12 12:12:11
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>初始电度</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        10
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>绑定房租</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        已绑定
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>绑定用水</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        已绑定
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>使用状态</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        正常
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>审核意见</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        通过
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>审核人</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        李四
                                    </div>
                                </div>

                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>





    </div>


</div>

</div>

<script src="/js/jQuery1.12.4.min.js"></script>
<script src="/js/bootstrap/bootstrap.min.js"></script>

</body>

<style>

    /**
       顶部时间滚动
    */
    .scroll-container {
        overflow-x: auto;
        white-space: nowrap;
        margin: 10px 5px;
        padding: 10px;

    }

    .scroll-content {
        display: inline-block;
    }

    .itemTime {
        padding: 5px 5px;
        font-size: 16px;
    }
</style>

<script type="text/javascript">
    var pageNumber = 1;
    var pageCount;
    var basePath = "<%=basePath%>";
    var htl;
    var j = 1;
    $(function () {
        $('.selectDate a').click(function () {
            var getTxt = $(this).text();
            $('.selectDate button').html("" + getTxt + " <span class='caret'></span>");
        });

    })
</script>
</html>
