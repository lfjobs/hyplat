
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
    <title>房租收付</title>
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
            <ul class="clearfix">
                <li>
                    <a onclick="window.history.go(-1);return false;" target="_self">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
                    </a>
                </li>
                <li>
                    房租收付
                </li>
                <li>

                </li>
            </ul>
        </header>

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
                        <div class="row"  style="background-color: rgb(246,246,220);padding: 10px;">
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
                                        <strong>房号</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        12-21-1
                                    </div>
                                </div>
                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>房源名称</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        12栋21号
                                    </div>
                                </div>
                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>款源时间</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        2024-12-12 12:12:11
                                    </div>
                                </div>

                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>初始余额</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        23
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>借方</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        张三
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>贷方</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        李四
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>余额</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        12
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>房东银行名称</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        中国银行
                                    </div>
                                </div>



                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>房东账号</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        2534623236642653632
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>租客银行名称</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        建设银行
                                    </div>
                                </div>



                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>租客账号</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        62634253637715262
                                    </div>
                                </div>



                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>结清状态</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        已结清
                                    </div>
                                </div>



                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>绑定水费</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        已绑定
                                    </div>
                                </div>



                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>绑定电费</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        已绑定
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>审核意见</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        同意
                                    </div>
                                </div>


                                <div class="row" style="margin-bottom: 5px">
                                    <div class="col-xs-6">
                                        <strong>审核人</strong>
                                    </div>
                                    <div class="col-xs-6">
                                        李凯
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
    .itemTime{
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
