<!DOCTYPE html>
<%@ page import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    TEshopCusCom t = (TEshopCusCom) session.getAttribute("key_shop_cus_com");
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>派单</title>

    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/demand.css"/>
    <script src="<%=basePath%>/js/ea/marketing/edmandServe/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/setHtmlFont.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/edit.js"></script>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: sans-serif;
        }

        body {
            background: #f0f0f0;
            padding: 10px;
        }

        .com_head {
            border-bottom: 1px solid #d0d0d0;
            position: relative;
            background: white;
            position: fixed;
            width: 100%;
            top: 0;
            left: 0;
            background: #fff;
            z-index: 970;
            padding: 5px;
            margin-bottom: 10px;
        }

        .back {
            position: absolute;
            display: block;
            top: 45%;
            height: 1rem;
            width: 0.6rem;
            background: url(<%=basePath%>/css/ea/images/back.png) no-repeat center;
            background-size: contain;
            left: .8rem;
            margin-top: -0.42rem;
        }

        .com_head h1 {
            text-align: center;
            font-size: 0.7rem;
            font-weight: 400;
            color: #2c2c2c;
            line-height: 1.8rem;
        }

        .wrap_page {
            margin-top: 2rem;
            overflow: hidden;
        }

        .tabs {
            display: flex;
            background: #fff;
            border-radius: 0.05;
            margin-bottom: 0.25rem;
            padding-right: 15px;
            padding-left: 15px;
            margin-right: auto;
            margin-left: auto;
        }

        .tab {
            flex: 1;
            padding: 0.5rem 0;
            text-align: center;
            cursor: pointer;
            font-size: 0.65rem;
        }

        .tab.active {
            background: #c92525;
            font-weight: bold;
            color: white;
        }

        .subtabs {
            display: flex;
            background: white;
            border-radius: 5px;
            margin-bottom: 10px;
            overflow-x: auto;
        }

        .subtab {
            padding: 0.35rem;
            white-space: nowrap;
            cursor: pointer;
            font-size: 0.65rem;
        }

        .subtab.active {
            color: #d81e06;
            font-weight: bold;
            border-bottom: 2px solid #d81e06;
        }

        .content {
            background: white;
            border-radius: 5px;
            padding: 10px;
            width: 100%;
            height: 80vh;
        }

        iframe {
            width: 100%;
            height: 100%;
        }

        .my_rel_btn {
            position: absolute;
            width: 4rem;
            line-height: 1.4rem;
            background: #f74c31;
            right: .6rem;
            top: 53.5%;
            /* -webkit-transform: translate3d(0, -50%, 0); */
            transform: translate3d(0, -50%, 0);
            color: #fff;
            text-align: center;
            border-radius: .15rem;
            font-size: .64rem;
        }
    </style>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var token = 0;
        var originPage = '${param.originPage}';
        var sccid = "<%=t.getSccId()%>";
    </script>
</head>
<body>
<div class="com_head">
    <a target="_self" class="back" id="ret" onclick="javascript:history.back(-1);"></a>
    <h1>派单抢单</h1>
</div>
<div class="wrap_page">
    <div class="tabs">
        <div class="tab" data-tab="all">全部订单</div>
        <div class="tab" data-tab="enterprise">企业派单抢单</div>
        <div class="tab" data-tab="personal">个人派单抢单</div>
    </div>

    <!-- 发单子选项 -->
    <div class="subtabs" id="subtab">
        <div class="subtab" data-subtab="settled">工认证</div>
        <div class="subtab" data-subtab="demand">订单</div>
        <div class="subtab" data-subtab="dispatch">派单</div>
        <div class="subtab" data-subtab="grab">抢单</div>
    </div>

    <div class="content">
        <iframe name="main" marginwidth="0" scrolling="yes" marginheight="0" frameborder="0" id="iframe-" border="0"
                framespacing="0" noresize="noResize" vspale="0"></iframe>
    </div>
</div>

<div class="nest_page">
    <div class="nest_hd">
        <a href="###" class="nest_back"></a>
        <span>详情</span>
    </div>
    <div class="nest_bd"></div>
    <input type="hidden"/>
</div>
<script>
    $(function () {


        let url = "";
        // 主选项卡切换
        $('.tab').click(function () {
            $('.tab').removeClass('active');
            $(this).addClass('active');

            let tabId = $(this).data('tab');
            switch (tabId) {
                case "all": //全部
                    url=basePath + "page/ea/main/marketing/edmandServe/order_list.jsp?originPage=win-pd";
                    break;
                case "personal": //个人
                    url=basePath + "page/ea/main/marketing/edmandServe/order_list.jsp?originPage=win-pd";
                    break;
                case "enterprise": //企业
                default:
                    url="";
                    alert("开发中");
                    $("#iframe-").attr("src", "");
                    break;
            }
            // 隐藏所有子选项
            //$('.subtab').removeClass('active');

            // 隐藏所有内容
            $('.content-item').removeClass('active');

        });

        // 子选项切换
        $('.subtab').click(function () {
            $(this).siblings().removeClass('active');
            $(this).addClass('active');

            $('.content-item').removeClass('active');
            let subtab = $(this).data('subtab');
            let type=0;
            switch (subtab) {
                case "settled": //用工认证
                case "demand": //订单
                    $("#iframe-").attr("src", url);
                    break;
                case "dispatch": //派单
                case "grab": //抢单
                default:
                    url="";
                    alert("开发中");
                    $("#iframe-").attr("src", "");
                    break;
            }
            if (url!=null&&url!=""){
                $("#iframe-").attr("src", url + "&type=" + type);
                let tabId = ($(".tab.active").data('tab'));
            }
        });

        $(".tab:eq(0)").trigger("click");
        $(".subtab:eq(1)").trigger("click");

        $(".nest_back").click(function () {
            $(".nest_bd").empty();
            $(".nest_page").hide();
        });
    });

</script>
</body>
</html>
