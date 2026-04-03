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
    <title>房源展示</title>
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
                    房源展示
                </li>
                <li>

                </li>
            </ul>
        </header>

        <strong>户型</strong>
        <div class="btn-group houseType">
            <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown">不限
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li><a href="#">不限</a></li>
                <li><a href="#">一室</a></li>
                <li><a href="#">二室</a></li>
                <li><a href="#">三室</a></li>
            </ul>
        </div>


        <strong>朝向</strong>
        <div class="btn-group orientation">
            <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown">不限
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li><a href="#">不限</a></li>
                <li><a href="#">东</a></li>
                <li><a href="#">南</a></li>
                <li><a href="#">西</a></li>
                <li><a href="#">北</a></li>
                <li><a href="#">南北</a></li>
            </ul>
        </div>


        <strong>房源特色</strong>
        <div class="btn-group feature">
            <button type="button" class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown">不限
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li><a href="#">不限</a></li>
                <li><a href="#">配套齐全</a></li>
                <li><a href="#">精装修</a></li>
                <li><a href="#">独立卫生间</a></li>
                <li><a href="#">押一付一</a></li>
                <li><a href="#">临地铁</a></li>
            </ul>
        </div>

        <ul class="list-group" style="margin-top: 10px">
            <li class="list-group-item">
                <a href="housingDetail.jsp">
                    <div class="row">
                        <div class="col-xs-5">
                            <img style="width: 100%" src="/images/houseRent/1.jpg" class="img-rounded">
                        </div>
                        <div class="col-xs-7">
                            <div><h5>精装电梯房主卧带阳台~三瓦窑~火车南站~东湖公园~高新金石路</h5></div>
                            <%--                    <div>3室1厅|20平米|中层(共6层) </div>--%>
                            <%--                    <div style="margin-top: 10px;">南新逸苑   高新区-南延线 -梓州大道7000号 </div>--%>
                            <div class="mt10" style="margin-top: 20px;font-weight: bold">
                                <div class="row">
                                    <div class="col-xs-5 text-primary">
                                        王五
                                    </div>
                                    <div class="col-xs-7"
                                         style="font-weight: bold;font-size: 16px;text-align: right;color: chocolate">
                                        900元/月
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </a>
            </li>

            <li class="list-group-item">
                <div class="row">
                    <div class="col-xs-5">
                        <img style="width: 100%" src="/images/houseRent/2.jpg" class="img-rounded">
                    </div>
                    <div class="col-xs-7">
                        <div><h5>精装电梯房主卧带阳台~三瓦窑~火车南站~东湖公园~高新金石路</h5></div>
                        <div class="mt10" style="margin-top: 16px;font-weight: bold">
                            <div class="row">
                                <div class="col-xs-5 text-primary">
                                    张三
                                </div>
                                <div class="col-xs-7"
                                     style="font-weight: bold;font-size: 20px;text-align: right;color: chocolate">
                                    1000元/月
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>


            <li class="list-group-item">
                <div class="row">
                    <div class="col-xs-5">
                        <img style="width: 100%" src="/images/houseRent/3.jpg" class="img-rounded">
                    </div>
                    <div class="col-xs-7">
                        <div><h5>一号线十八号线海昌路华阳地铁口 世纪城 孵化园 天府软件园</h5></div>
                        <div class="mt10" style="margin-top: 16px;font-weight: bold">
                            <div class="row">
                                <div class="col-xs-5 text-primary">
                                    李四
                                </div>
                                <div class="col-xs-7"
                                     style="font-weight: bold;font-size: 20px;text-align: right;color: chocolate">
                                    800元/月
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>

            <li class="list-group-item">
                <div class="row">
                    <div class="col-xs-5">
                        <img style="width: 100%" src="/images/houseRent/1.jpg" class="img-rounded">
                    </div>
                    <div class="col-xs-7">
                        <div><h5>一号线十八号线海昌路华阳地铁口 世纪城 孵化园 天府软件园</h5></div>
                        <div class="mt10" style="margin-top: 16px;font-weight: bold">
                            <div class="row">
                                <div class="col-xs-5 text-primary">
                                    李四
                                </div>
                                <div class="col-xs-7"
                                     style="font-weight: bold;font-size: 20px;text-align: right;color: chocolate">
                                    800元/月
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>

            <li class="list-group-item">
                <div class="row">
                    <div class="col-xs-5">
                        <img style="width: 100%" src="/images/houseRent/3.jpg" class="img-rounded">
                    </div>
                    <div class="col-xs-7">
                        <div><h5>一号线十八号线海昌路华阳地铁口 世纪城 孵化园 天府软件园</h5></div>
                        <div class="mt10" style="margin-top: 16px;font-weight: bold">
                            <div class="row">
                                <div class="col-xs-5 text-primary">
                                    李四
                                </div>
                                <div class="col-xs-7"
                                     style="font-weight: bold;font-size: 20px;text-align: right;color: chocolate">
                                    800元/月
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>


            <li class="list-group-item">
                <div class="row">
                    <div class="col-xs-5">
                        <img style="width: 100%" src="/images/houseRent/2.jpg" class="img-rounded">
                    </div>
                    <div class="col-xs-7">
                        <div><h5>一号线十八号线海昌路华阳地铁口 世纪城 孵化园 天府软件园</h5></div>
                        <div class="mt10" style="margin-top: 16px;font-weight: bold">
                            <div class="row">
                                <div class="col-xs-5 text-primary">
                                    李四
                                </div>
                                <div class="col-xs-7"
                                     style="font-weight: bold;font-size: 20px;text-align: right;color: chocolate">
                                    800元/月
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>


            <li class="list-group-item">
                <div class="row">
                    <div class="col-xs-5">
                        <img style="width: 100%" src="/images/houseRent/1.jpg" class="img-rounded">
                    </div>
                    <div class="col-xs-7">
                        <div><h5>一号线十八号线海昌路华阳地铁口 世纪城 孵化园 天府软件园</h5></div>
                        <div class="mt10" style="margin-top: 16px;font-weight: bold">
                            <div class="row">
                                <div class="col-xs-5 text-primary">
                                    李四
                                </div>
                                <div class="col-xs-7"
                                     style="font-weight: bold;font-size: 20px;text-align: right;color: chocolate">
                                    800元/月
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>

        </ul>
    </div>
</div>


<script src="/js/jQuery1.12.4.min.js"></script>
<script src="/js/bootstrap/bootstrap.min.js"></script>

</body>

<style>
    .content {
        padding: 6px;
    }
</style>

<script type="text/javascript">
    var pageNumber = 1;
    var pageCount;
    var basePath = "<%=basePath%>";
    var htl;
    var j = 1;
    $(function () {
        // $("#dateShow").text(getDate());

        $('.houseType a').click(function () {
            var getTxt = $(this).text();
            // $('.houseType button').text(getTxt);
            $('.houseType button').html("" + getTxt + " <span class='caret'></span>");
        });


        $('.orientation a').click(function () {
            var getTxt = $(this).text();
            // $('.houseType button').text(getTxt);
            $('.orientation button').html("" + getTxt + " <span class='caret'></span>");
        });

        $('.feature a').click(function () {
            var getTxt = $(this).text();
            // $('.houseType button').text(getTxt);
            $('.feature button').html("" + getTxt + " <span class='caret'></span>");
        });

    })

</script>
</html>
