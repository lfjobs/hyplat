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
    <title>发布房源</title>
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
                    发布房源
                </li>
                <li>

                </li>
            </ul>
        </header>
        <div class="form-v" style="padding: 10px">


            <form class="form-horizontal" role="form">
                <div class="form-group">
                    <label for="firstname" class="col-sm-2 control-label">房东姓名</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="firstname" placeholder="请输入房东姓名">
                    </div>
                </div>
                <div class="form-group">
                    <label for="" class="col-sm-2 control-label">房东电话</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="" placeholder="请输入房东电话">
                    </div>
                </div>

                <div class="form-group">
                    <label for="" class="col-sm-2 control-label">面积</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" placeholder="请输入面积">
                    </div>
                </div>

                <div class="form-group">
                    <label for="" class="col-sm-2 control-label">朝向或户型</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" placeholder="请输入朝向或户型">
                    </div>
                </div>

                <div class="form-group">
                    <label for="" class="col-sm-2 control-label">楼层</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" placeholder="请输入楼层">
                    </div>
                </div>

                <div class="form-group">
                    <label for="" class="col-sm-2 control-label">楼栋号</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" placeholder="请输入楼栋号">
                    </div>
                </div>

                <div class="form-group">
                    <label for="" class="col-sm-2 control-label">月租金</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" placeholder="请输入月租金">
                    </div>
                </div>


                <div class="form-group">
                    <label for="" class="col-sm-2 control-label">房源描述</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" placeholder="请输入房源描述">
                    </div>
                </div>


                <div class="form-group">
                    <label for="" class="col-sm-2 control-label">照片信息</label>
                    <div class="col-sm-10">
                        <input type="file" class="form-control" placeholder="请选择照片信息">
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-12">
                        <button type="submit" class="btn btn-lg btn-block btn-primary">发布房源</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

</div>


<script src="/js/jQuery1.12.4.min.js"></script>

<script src="/js/bootstrap/bootstrap.min.js"></script>

</body>

<style>
    .content {
        margin: 20px;
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

        $('#date-v .item').click(function () {
            $(this).siblings().removeClass('clicked');
            $(this).addClass('clicked');
        });

        $('#date-v .item').click(function () {

            var index = $(this).index();//所选时间下标

            var url = basePath + "/ea/electricity/sajax_ea_electricity_getSuccess.jspa?index=" + index;
            $.ajax({
                url: encodeURI(url),
                type: "get",
                async: false,
                dataType: "json",
                traditional: true,
                data: {
                    "companyid": "company20180510CQZCDKTT690000006064"
                },
                success: function (data) {


                    console.log("22", data)
                }
            })
        })

    })

    function init() {
        var url = basePath + "/ea/electricity/sajax_ea_todayPrice.jspa?pageNumber=" + pageNumber;
        $.ajax({
            url: encodeURI(url),
            type: "get",
            async: false,
            dataType: "json",
            traditional: true,
            data: {
                "companyid": "company20180510CQZCDKTT690000006064"
            },
            success: function (data) {

            }
        })
    }
</script>
</html>
