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
    <title>应急</title>

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
            top: 49.5%;
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
        var tt = "<%=t%>";
        var staffid = '${param.staffid}';
        var sccId = '${param.sccId}';
        var user = '${param.user}';
        var tle = '${param.tle}';
        let originPage = '${param.originPage}';
    </script>
</head>
<body>
<div class="com_head">
    <a target="_self" class="back" id="ret" onclick="javascript:history.back(-1);"></a>
    <h1>应急发布</h1>
</div>
<div class="wrap_page">
    <div class="tabs">
        <div class="tab" data-tab="release">发布</div>
        <div class="tab" data-tab="send">全部项目</div>
        <div class="tab" data-tab="receive">接单</div>
        <%--
                <div class="tab" data-tab="project">项目管理</div>--%>
        <div class="tab" data-tab="document">收支</div>
    </div>

    <!-- 发单子选项 -->
    <div class="subtabs" id="send-subtab">
        <div class="subtab" data-subtab="all-send">全部发单</div>
        <div class="subtab" data-subtab="grabbed">已抢单</div>
        <div class="subtab" data-subtab="not-grabbed">未抢单</div>
        <div class="subtab" data-subtab="settled">已结算</div>
    </div>

    <!-- 接单子选项 -->
    <div class="subtabs" id="receive-subtab" style="display:none">
        <div class="subtab" data-subtab="not-receiv">未确认单</div>
        <div class="subtab" data-subtab="receiving">已确认单</div>
        <%--<div class="subtab" data-subtab="receive-record">接单记录</div>--%>
    </div>

    <!-- 收支子选项 -->
    <div class="subtabs" id="document-subtab" style="display:none"></div>

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
</div>
<script>
    $(function () {
        let otype = "";//来源 1:个人 2:企业
        $.ajax({
            url: basePath + '/ea/dserve/sajax_ea_getSession.jspa',
            type: "get",
            dataType : "json",
            success: function (data) {
                var member=eval("("+data+")");
                if (member.nologin=="1") {
                    alert('Session已过期');
                    // 可以重定向到登录页面等操作
                    window.location.href = basePath + 'page/WFJClient/pc/pc_login.jsp';
                } else {
                    console.log('Session未过期');
                    if (originPage != "" && originPage != null) {
                        if (originPage == "enterprise") {
                            $("h1").text("企业应急发布");
                            otype = "2";
                            /*if (sccId == null || sccId == "" || staffid == null || staffid == "") {
                                sccId = "<%=t.getSccId()%>";
                                staffid = "<%=t.getStaffid()%>";
                            }*/
                        } else {
                            otype = "1";
                            $("h1").text("家应急发布");
                        }
                    }
                }
            },
            error: function () {
                console.log('请求失败');
            }
        });

        let url = "";
        // 主选项卡切换
        $('.tab').click(function () {
            $('.tab').removeClass('active');
            $(this).addClass('active');

            let tabId = $(this).data('tab');
            switch (tabId) {
                case "release":
                    //window.parent.location.href=basePath + "page/ea/main/marketing/edmandServe/workType_save.jsp";
                    url = basePath + "page/ea/main/marketing/edmandServe/workType_save.jsp?originPage=win-yjfb-" + originPage;
                    $("#iframe-").attr("src", url);
                    break;
                case "send":
                    //url = basePath + "/ea/dserve/ea_detailListBySccid.jspa?originPage=win-fb-" + originPage + "&staffid=" + staffid + "&sccId=" + sccId + "&tle=" + tle;
                    url = basePath + "/ea/dserve/ea_detailListBySccid.jspa?originPage=win-fb-" + originPage + "&tle=" + tle;
                    break;
                case "receive":
                    //url = basePath + "/ea/dserve/ea_toPage_demandListBydssccid.jspa?sccId=" + sccId + "&originPage=win-fb-" + originPage;
                    url = basePath + "/ea/dserve/ea_toPage_demandListBydssccid.jspa?originPage=win-fb-" + originPage;
                    break;
                case "document":
                default:
                    url = "";
                    alert("开发中");
                    $("#iframe-").attr("src", "");
                    break;
            }
            // 隐藏所有子选项
            $('.subtabs').hide();
            $('.subtab').removeClass('active');

            // 显示对应的子选项
            //$('#' + tabId + '-subtab').show().find('.subtab').first().addClass('active');
            $('#' + tabId + '-subtab').show().find('.subtab').first().trigger("click");
            // 隐藏所有内容
            $('.content-item').removeClass('active');

            // 显示对应内容
            let activeSubtab = $('#' + tabId + '-subtab').find('.subtab.active').data('subtab');
            $('#' + activeSubtab + '-content').addClass('active');
        });

        // 子选项切换
        $('.subtab').click(function () {
            $(this).siblings().removeClass('active');
            $(this).addClass('active');

            $('.content-item').removeClass('active');
            let subtab = $(this).data('subtab');
            let type = 0;
            switch (subtab) {
                case "all-send":
                    type = 3;
                    break;
                case "grabbed":
                    type = 0;
                    break;
                case "not-grabbed":
                    type = 1;
                    break;
                case "settled":
                    type = 2;
                    break;
                case "not-receiv":
                    type = 2;
                    break;
                case "receiving":
                    type = 1;
                    break;
                /*case "receive-record":
                    type = 1;
                    break;*/
                case "compare-project":
                case "re-project":
                case "inventory":
                case "document":
                default:
                    url = "";
                    alert("开发中");
                    $("#iframe-").attr("src", "");
                    break;
            }
            if (url != null && url != "") {
                $("#iframe-").attr("src", url + "&type=" + type);
                let tabId = ($(".tab.active").data('tab'));
                switch (tabId) {
                    case "release":
                        //window.parent.location.href=basePath + "page/ea/main/marketing/edmandServe/workType_save.jsp";
                        url = basePath + "page/ea/main/marketing/edmandServe/workType_save.jsp?originPage=win-yjfb-" + originPage;
                        break;
                    case "send":
                        //url = basePath + "/ea/dserve/ea_detailListBySccid.jspa?originPage=win-fb-" + originPage + "&staffid=" + staffid + "&sccId=" + sccId + "&tle=" + tle;
                        url = basePath + "/ea/dserve/ea_detailListBySccid.jspa?originPage=win-fb-" + originPage+ "&tle=" + tle;
                        break;
                    case "receive":
                        //url = basePath + "/ea/dserve/ea_toPage_demandListBydssccid.jspa?sccId=" + sccId + "&originPage=win-fb-" + originPage;
                        url = basePath + "/ea/dserve/ea_toPage_demandListBydssccid.jspa?originPage=win-fb-" + originPage;
                        break;
                    case "document":
                    default:
                        alert("开发中");
                        url = "";
                        break;
                }
            }
        });

        $(".tab:eq(0)").trigger("click");
        $(".subtab:eq(0)").trigger("click");

        $(".nest_back").click(function () {
            $(".nest_bd").empty();
            $(".nest_page").hide();
        });
    });

</script>
</body>
</html>
