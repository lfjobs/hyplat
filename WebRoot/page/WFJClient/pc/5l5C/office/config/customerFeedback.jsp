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
    <title>售后服务-客户意见</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/afterSales.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/afterSales.js"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
    </script>
    <style>
        .tab-item1 {
            flex: 1;
            text-align: center;
            color: black;
        }

        .tab-item1.active {
            color: rgb(22, 186, 170);;
            font-weight: bold;
        }

        .time li {
            flex: 1;
            text-align: center;
            color: black;
        }
    </style>
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
            售后服务
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content">
    <section class="search searTime" style="display: none">
        <ul class="tabContainer1 time" style="display:flex;font-size: 0.95rem;height: 50px;align-items: center;">
            <li>日期 :</li>
            <li>
                <input type="date" id="startDate" placeholder="开始日期"
                       value="<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>"/>
            </li>
            <li>--</li>
            <li>
                <input type="date" id="endDate" placeholder="结束日期"
                       value="<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>"/>
            </li>
            <li>
                <button onclick="searchByDateRange()">搜索</button>
            </li>
        </ul>
    </section>
    <section class="search">
        <ul class="tabContainer1" style="display:flex;font-size: 0.81rem;height: 50px;align-items: center;">
            <li class="tab-item1 two active">今日</li>
            <li class="tab-item1 two">昨日</li>
            <li class="tab-item1 two">本周</li>
            <li class="tab-item1 two">上周</li>
            <li class="tab-item1 two">本月</li>
            <li class="tab-item1 two">上月</li>
            <li class="tab-item1 two">本季</li>
            <li class="tab-item1 two">上季</li>
            <li class="tab-item1 two">本年</li>
            <li class="tab-item1 two">上年</li>
            <li class="tab-item1 two">自定义</li>

        </ul>
    </section>
    <section class="category">

        <ul class="tabContainer" style="display:flex;font-size: 0.95rem;height: 50px;align-items: center;">
            <li class="tab-item order two">已交费订单</li>
            <li class="tab-item opinion two">客户意见</li>
            <li class="tab-item isProcess two">已处理</li>
            <li class="tab-item noProcess two">未处理</li>
        </ul>
    </section>
    <section class="sec-list">

        <div class="div-sec-data"
             style="overflow-y: hidden;overflow-x: auto; width: 100%; max-height: 80vh; position: relative;">
            <!-- 将标题和列表合并到同一个横向滚动容器中 -->
            <div class="data-scroll-container" style="max-height: calc(100vh - 200px);width: max-content;">
                <!-- 标题：上下 sticky，左右随内容滚动 -->
                <div class="data-title"
                     style="position: sticky; top: 0; z-index: 10; background-color: #f8f8f8; width: 100%;">
                    <ul class="flex-container">
                        <li>序号</li>
                        <li>编号</li>
                        <li>名称</li>
                        <li></li>
                        <li>意见</li>
                    </ul>
                </div>

                <!-- 数据列表 -->
                <div class="data-list div-dataBack" style="overflow: auto;font-size: 15px;">
                    <!-- 数据项 -->
                </div>
            </div>
        </div>
    </section>
</div>
</body>
<script type="text/javascript">
    $(function () {
        getEntryStaffData();
    });
    document.addEventListener('DOMContentLoaded', function () {
        const tabs = document.querySelectorAll('.tab-item1');
        tabs.forEach(tab => {
            tab.addEventListener('click', function () {
                tabs.forEach(t => t.classList.remove('active'));
                this.classList.add('active');
                const selectedValue = this.textContent.trim();

                switch (selectedValue) {
                    case "自定义": {
                        $('.searTime').show();
                        break;
                    }
                    case "今日": {
                        $('.searTime').hide();
                        const today = new Date();
                        startTime = endTime = formatDate(today);
                        break;
                    }
                    case "昨日": {
                        $('.searTime').hide();
                        const yesterday = new Date();
                        yesterday.setDate(yesterday.getDate() - 1);
                        startTime = endTime = formatDate(yesterday);
                        break;
                    }
                    case "本周": {
                        $('.searTime').hide();
                        const today = new Date();
                        const dayOfWeek = today.getDay();
                        const startOfWeek = new Date(today);
                        const endOfWeek = new Date(today);
                        startOfWeek.setDate(today.getDate() - (dayOfWeek === 0 ? 6 : dayOfWeek - 1));
                        endOfWeek.setDate(today.getDate() + (dayOfWeek === 0 ? 0 : 7 - dayOfWeek));
                        startTime = formatDate(startOfWeek);
                        endTime = formatDate(endOfWeek);
                        break;
                    }
                    case "上周": {
                        $('.searTime').hide();
                        const today = new Date();
                        const dayOfWeek = today.getDay();
                        const startOfLastWeek = new Date(today);
                        const endOfLastWeek = new Date(today);
                        startOfLastWeek.setDate(today.getDate() - dayOfWeek - 6);
                        endOfLastWeek.setDate(today.getDate() - dayOfWeek);
                        startTime = formatDate(startOfLastWeek);
                        endTime = formatDate(endOfLastWeek);
                        break;
                    }
                    case "本月": {
                        $('.searTime').hide();
                        const today = new Date();
                        [startTime, endTime] = getMonthRange(today.getFullYear(), today.getMonth() + 1);
                        break;
                    }
                    case "上月": {
                        $('.searTime').hide();
                        const today = new Date();
                        const year = today.getMonth() === 0 ? today.getFullYear() - 1 : today.getFullYear();
                        const month = today.getMonth() === 0 ? 12 : today.getMonth();
                        [startTime, endTime] = getMonthRange(year, month);
                        break;
                    }
                    case "本季": {
                        $('.searTime').hide();
                        const today = new Date();
                        const quarter = Math.floor(today.getMonth() / 3) + 1;
                        [startTime, endTime] = getQuarterRange(today.getFullYear(), quarter);
                        break;
                    }
                    case "上季": {
                        $('.searTime').hide();
                        const today = new Date();
                        let quarter = Math.floor(today.getMonth() / 3);
                        let year = today.getFullYear();
                        if (quarter === 0) {
                            quarter = 4;
                            year -= 1;
                        }
                        [startTime, endTime] = getQuarterRange(year, quarter);
                        break;
                    }
                    case "本年": {
                        $('.searTime').hide();
                        const year = new Date().getFullYear();
                        startTime = `${year}-01-01`;
                        endTime = `${year}-12-31`;
                        break;
                    }
                    case "上年": {
                        $('.searTime').hide();
                        const year = new Date().getFullYear() - 1;
                        startTime = `${year}-01-01`;
                        endTime = `${year}-12-31`;
                        break;
                    }
                }
                getEntryStaffData();
            });
        });
        // 获取所有的.option元素（在当前页面中存在）
        const options = document.querySelectorAll('.tab-item');

        // 默认选中第一个.option元素
        if (options.length > 0) {
            // options[0].style.backgroundColor = '#4a8e4a';
            options[1].style.color = '#16baaa';
            options[1].style.borderBottom = '2px solid #16baaa';
            options[1].style.fontweight = 'bold';
        }

        options.forEach(option => {
            option.addEventListener('click', function () {
                // 清除所有.option元素的背景色
                options.forEach(opt => {
                    opt.style.backgroundColor = '';
                });

                // this.style.backgroundColor = '#4a8e4a';
                this.style.color = '#16baaa';
                options[1].style.borderBottom = '2px solid #16baaa';
                options[1].style.fontweight = 'bold';
            });
        });
    });

    function getEntryStaffData() {
        const param = new Array();
        param.push("pageNumber=" + pageNumber);
        param.push("&&pageSize=" + pageSize);
        param.push("&&startTime=" + startTime);
        param.push("&&endTime=" + endTime);
        const url = basePath + "ea/afterSales/sajax_ea_getCashierBillsList1.jspa?" + param.join("");
        $.ajax({
            url: encodeURI(url),
            type: "get",
            async: true,
            dataType: "json",
            success: function cbf(data) {
                const codeList = eval("(" + data + ")");
                if (codeList == null) {
                    $(".div-dataBack").html("暂无数据");
                    $(".div-dataBack").css({"display": "flex", "align-items": "center", "justify-content": "center"});
                } else {
                    const list = codeList.list;
                    pageCount = codeList["pageCount"];
                    const length = list.length;
                    const htmlstr = new Array();
                    // let name= "";
                    for (let i = 0; i < length; i++) {
                        htmlstr.push("<ul id='" + list[i][0] + "' cosid='" + list[i][0] + "' class='data-ul data-ul-" + list[i][0] + "'>");
                        htmlstr.push("<div class='rowDiv' style='display: contents;'>")
                        //序号
                        htmlstr.push("<li><div class='scrollable'>" + (pageSize * (pageNumber - 1) + i + 1) + "</div></li>");
                        //编号
                        htmlstr.push("<li name='" + (list[i][1] == null ? " " : list[i][1]) + "' class='push-li1'><div class='scrollable'>" + (list[i][1] == null ? " " : list[i][1]) + "</div></li>");
                        //名称
                        htmlstr.push("<li phone='" + (list[i][2] == null ? " " : list[i][2]) + "' class='push-li3'><div class='scrollable'>" + (list[i][2] == null ? " " : list[i][2]) + "</div></li>");
                        if (window.innerWidth > 420) { // 替换 yourDeviceWidthThreshold 为具体数值
                            htmlstr.push("<li class='additional-info'><div class='scrollable'>更多&nbsp;></div></li>");
                        }
                        //意见
                        htmlstr.push("<li phone='" + (list[i][6] == null ? " " : list[i][6]) + "' class='push-li3'><div class='scrollable'>" + (list[i][6] == null ? " " : list[i][6]) + "</div></li>");
                        htmlstr.push("</div>")
                        htmlstr.push("</ul>");
                    }
                    const moreData = document.getElementById('more-data');
                    if (moreData != null) {
                        moreData.remove()
                    }
                    if (pageNumber > 1) {
                        $(".div-dataBack").append(htmlstr.join(""));
                    } else {
                        $(".div-dataBack").html(htmlstr.join(""));
                    }
                    $(".div-dataBack").css({"display": "block"});
                }
                scrollBool = true;
            },
            error: function cbf(data) {
                prompt("数据获取失败！");
            }
        });
    }

    const divElement = document.querySelector('.data-list');
    divElement.addEventListener('scroll', function () {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight || 1 == 1) {
            if (pageNumber < pageCount && scrollBool) {
                scrollBool = false;
                pageNumber++;
                getEntryStaffData();
            }

        }
    })
</script>
</html>