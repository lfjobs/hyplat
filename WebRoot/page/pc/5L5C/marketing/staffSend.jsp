<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>发送记录</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staffSend.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>

    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
        let pageNumber = 1, pageSize = 40, pageCount = 0;
        let scrollBool = true;
        var query = null;
    </script>
</head>
<body class="body">
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            短信发送记录
        </li>
        <li>
        </li>
    </ul>
</header>

<div class="center">
    <div class="title">
        <div class="isSend clicked">已发送短信</div>
        <div class="noSend">未发送短信</div>
    </div>
    <section class="sec-list" style=" overflow: hidden auto; height: 80%;">
        <div class="div-sec-data">
            <div class="data-title1">
                <ul class="average-distribution">
                    <li>序号</li>
                    <li>姓名</li>
                    <li>手机号</li>
                </ul>
            </div>
            <div class="data-list div-data" style="overflow-y:auto;overflow-x:hidden;height: 790px;">
            </div>
        </div>
    </section>
</div>

</body>
<script>

    $(function () {
        initCss();
        bindEvents();
        getEntryStaffData("已发送短信");
    });
    function initCss() {
        $(".center").height($(".body").height() - $("header").height());
        $(".sec-list").height($(".center").height() - 100);
    }

    function bindEvents() {
        // 监听滚动事件
        const divElement = document.querySelector('.data-list');
        divElement.addEventListener('scroll', function () {
            if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight || 1 == 1) {
                if (pageNumber < pageCount && scrollBool) {
                    scrollBool = false;
                    pageNumber++;
                    if (query === "已发送短信") {
                        getEntryStaffData("已发送短信");
                    }
                    if (query === "未发送短信") {
                        getEntryStaffData("未发送短信");
                    }
                }
            }
        })
    }

    document.addEventListener('DOMContentLoaded', () => {
        const isSend = document.querySelector('.isSend');
        const noSend = document.querySelector('.noSend');
        // 点击 isSend 时的逻辑
        isSend.addEventListener('click', () => {
            query = isSend.textContent.trim();
            if (query) {
                $(".data-list").empty();
                pageNumber = 1, pageSize = 40, pageCount = 0;
                getEntryStaffData(query);
            }
            isSend.classList.add('clicked'); // 给 isSend 添加 active 类
            noSend.classList.remove('clicked'); // 移除 noSend 的 active 类
        });
        // 点击 noSend 时的逻辑
        noSend.addEventListener('click', () => {
            query = noSend.textContent.trim();
            if (query) {
                $(".data-list").empty();
                pageNumber = 1, pageSize = 40, pageCount = 0;
                getEntryStaffData(query);
            }
            noSend.classList.add('clicked'); // 给 noSend 添加 active 类
            isSend.classList.remove('clicked');
        });
    });

    //查询
    function getEntryStaffData(isSend) {
        const param = new Array();
        param.push("pageNumber=" + pageNumber);
        param.push("&&pageSize=" + pageSize);
        param.push("&&isSend=" + isSend);
        const url = basePath + "ea/cRMShortMessagingTemplatePO/sajax_ea_getSendStaffList.jspa?" + param.join("");
        $.ajax({
            url: encodeURI(url),
            type: "get",
            async: true,
            dataType: "json",
            success: function cbf(data) {
                const codeList = eval("(" + data + ")");
                if (codeList == null) {
                    $(".data-list").html("暂无数据");
                    $(".data-list").css({"display": "flex", "align-items": "center", "justify-content": "center"});
                } else {
                    const list = codeList.list;
                    pageCount = codeList["pageCount"];
                    const length = list.length;
                    const htmlstr = new Array();
                    // let name= "";
                    for (let i = 0; i < length; i++) {
                        htmlstr.push("<ul id='" + list[i][0] + "' cosid='" + list[i][0] + "' class='average-distribution data-ul data-ul-" + list[i][0] + "'>");
                        htmlstr.push("<li>" + (pageSize * (pageNumber - 1) + i + 1) + "</li>");
                        htmlstr.push("<li name='" + (list[i][2] == null ? " " : list[i][2]) + "' class='push-li1'>" + (list[i][2] == null ? " " : list[i][2]) + "</li>");
                        htmlstr.push("<li phone='" + (list[i][5] == null ? " " : list[i][5]) + "' class='push-li3'>" + (list[i][5] == null ? " " : list[i][5]) + "</li>");
                        htmlstr.push("</ul>")
                    }
                    const moreData = document.getElementById('more-data');
                    if (moreData != null) {
                        moreData.remove()
                    }
                    if (pageNumber > 1) {
                        $(".data-list").append(htmlstr.join(""));
                    } else {
                        $(".data-list").html(htmlstr.join(""));
                    }
                    $(".data-list").css({"display": "block"})
                }
                scrollBool = true;
            },
            error: function cbf(data) {
                prompt("数据获取失败！");
            }
        });
    }
</script>
</html>
