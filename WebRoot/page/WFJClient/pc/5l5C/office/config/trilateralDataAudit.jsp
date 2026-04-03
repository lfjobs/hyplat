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
    <title>三方</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">

    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/trilateralDataNew.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/jQuery.print.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/afterSales.js"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
    </script>
    <style>
        .content .category {
            padding: 0.3rem;
            display: flex;
            align-items: center;
            justify-content: flex-start;
        }

        .content .category .box {
            background-color: #f6f6f6;
            border-radius: 1rem;
            width: 80%;
        }

        .content .category .box label img {
            margin-left: .65rem;
            width: .85rem;
        }

        img {
            -webkit-tap-highlight-color: transparent;
            vertical-align: middle;
            object-fit: cover;
        }

        .content .category .box input {
            float: left;
            height: 2.2rem;
            line-height: 2.2rem;
            font-size: 15px;
            color: #222;
            border: 0;
            background-color: transparent;
            width: 80%;
        }

        .content .category div:nth-of-type(2) input {
            width: 2.6rem;
            height: 1.6rem;
            background: #faf8f8;
            border: 1px solid #d1d1d1;
            margin-left: 0.5rem;
            font-size: 15px;
        }

        .content .category .box label {
            display: flex;
            align-items: center;
            height: 2rem;
            line-height: 2rem;
            float: left;
            width: 1.75rem;
            text-align: center;
        }

        .time li {
            flex: 1;
            text-align: center;
            color: black;
        }


        /*新增*/
        .sec-nav ul {
            display: flex !important;
            justify-content: space-around;
            align-items: center;
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .sec-nav ul li {
            flex: 1;
            /*text-align: center;*/
            margin: 0;
            padding: 8px 20px;
            color: #958383;
            font-weight: bold;
        }

        .sec-nav ul li p {
            margin: 0;
            cursor: pointer;
        }

        .active1 {
            background-color: #d939224f !important; /* 使用与主题一致的青绿色 */
            transition: background-color 0.2s ease; /* 添加平滑过渡效果 */
            color: white;
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
            三方平台推广审核
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content">
    <section class="category">
        <div class="box clearfix">
            <label for="search">
                <img src="http://localhost:8080/images/ea/office/contract/img_02.png" alt="搜索图标">
            </label>
            <input type="text" name="search" id="search" placeholder="请输入姓名或手机号">
        </div>
        <div><input type="button" name="qsearch" id="qsearch" value="搜索"></div>
    </section>
    <section class="sec-nav sec-hide ">
        <ul class="clearfix" style="display: flex">
            <li class="clearfix">
                <p class="audit1">审核</p>
            </li>
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
                        <li>公司名称</li>
                        <li>人员编号</li>
                        <li>姓名</li>
                        <li>性别</li>
                        <li>行业</li>
                        <li>电话</li>
                        <li>注册平台名称</li>
                        <li>注册号</li>
                        <li>注册账号</li>
                        <li>密码</li>
                        <li>推广方式</li>
                        <li>事由</li>
                        <li>分配</li>
                        <li>责任人</li>
                        <li>私信</li>
                        <li>合同</li>
                        <li>审核</li>
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
    var type = "all";
    var rowData;
    var trusteeship = "all";
    var staffName = "";
    var phone = "";
    $(function () {
        getEntryStaffData();
        bindEvents();
    });

    function getEntryStaffData() {
        const param = new Array();
        param.push("pageNumber=" + pageNumber);
        param.push("&&pageSize=" + pageSize);
        param.push("&&uploadProject=" + type);
        param.push("&&trusteeship=" + trusteeship);
        param.push("&&staffName=" + staffName);
        param.push("&&phone=" + phone);
        const url = basePath + "ea/trilateral/sajax_ea_getTrilateralAudit.jspa?" + param.join("");
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
                        //公司名称
                        htmlstr.push("<li name='" + (list[i][1] == null ? " " : list[i][1]) + "' class='push-li1'><div class='scrollable'>" + (list[i][1] == null ? " " : list[i][1]) + "</div></li>");
                        //人员编号
                        htmlstr.push("<li phone='" + (list[i][2] == null ? " " : list[i][2]) + "' class='push-li3'><div class='scrollable'>" + (list[i][2] == null ? " " : list[i][2]) + "</div></li>");
                        //姓名
                        htmlstr.push("<li phone='" + (list[i][3] == null ? " " : list[i][3]) + "' class='push-li3'><div class='scrollable'>" + (list[i][3] == null ? " " : list[i][3]) + "</div></li>");
                        //性别
                        htmlstr.push("<li phone='" + (list[i][4] == null ? " " : list[i][4]) + "' class='push-li3'><div class='scrollable'>" + (list[i][4] == null ? " " : list[i][4]) + "</div></li>");
                        //行业
                        htmlstr.push("<li phone='" + (list[i][5] == null ? " " : list[i][5]) + "' class='push-li3'><div class='scrollable'>" + (list[i][5] == null ? " " : list[i][5]) + "</div></li>");
                        //电话
                        htmlstr.push("<li phone='" + (list[i][6] == null ? " " : list[i][6]) + "' class='push-li3'><div class='scrollable'>" + (list[i][6] == null ? " " : list[i][6]) + "</div></li>");
                        //注册平台名称
                        htmlstr.push("<li phone='" + (list[i][7] == null ? " " : list[i][7]) + "' class='push-li3'><div class='scrollable'>" + (list[i][7] == null ? " " : list[i][7]) + "</div></li>");
                        //注册号
                        htmlstr.push("<li phone='" + (list[i][8] == null ? " " : list[i][8]) + "' class='push-li3'><div class='scrollable'>" + (list[i][8] == null ? " " : list[i][8]) + "</div></li>");
                        //注册账号
                        htmlstr.push("<li phone='" + (list[i][9] == null ? " " : list[i][9]) + "' class='push-li3'><div class='scrollable'>" + (list[i][9] == null ? " " : list[i][9]) + "</div></li>");
                        //密码
                        htmlstr.push("<li phone='" + (list[i][10] == null ? " " : list[i][10]) + "' class='push-li3'><div class='scrollable'>" + (list[i][10] == null ? " " : list[i][10]) + "</div></li>");
                        //推广方式
                        htmlstr.push("<li phone='" + (list[i][11] == null ? " " : list[i][11]) + "' class='push-li3'><div class='scrollable'>" + (list[i][11] == null ? " " : list[i][11]) + "</div></li>");
                        //事由
                        htmlstr.push("<li phone='" + (list[i][12] == null ? " " : list[i][12]) + "' class='push-li3'><div class='scrollable'>" + (list[i][12] == null ? " " : list[i][12]) + "</div></li>");
                        //分配
                        htmlstr.push("<li phone='" + (list[i][23] == null ? " " : list[i][23]) + "' class='push-li3'><div class='scrollable'>" +
                            (list[i][23] == null ? "未分配" : (list[i][23] == "1" ? "已分配" : (list[i][23] == "0" ? "未分配" : "待分配"))) + "</div></li>");
                        //责任人
                        htmlstr.push("<li phone='" + (list[i][27] == null ? " " : list[i][27]) + "' class='push-li3'><div class='scrollable'>" + (list[i][27] == null ? " " : list[i][27]) + "</div></li>");
                        //私信
                        htmlstr.push("<li phone='" + (list[i][19] == null ? " " : list[i][19]) + "' class='push-li3'><div class='scrollable'>" + (list[i][19] == null ? " " : list[i][19]) + "</div></li>");
                        //合同
                        htmlstr.push("<li phone='" + (list[i][21] == null ? " " : list[i][21]) + "' class='push-li3'><div class='scrollable'>" + (list[i][21] == null ? " " : list[i][21]) + "</div></li>");
                        //审核
                        htmlstr.push("<li phone='" + (list[i][20] == null ? " " : list[i][20]) + "' class='push-li3'><div class='scrollable'>" +
                            (list[i][23] == null ? "未审核" : (list[i][20] == "1" ? "已审核" : (list[i][20] == "0" ? "未审核" : "待审核"))) + "</div></li>");
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

    //点击事件
    function bindEvents() {
        // 监听滚动事件
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

        // 绑定搜索按钮点击事件
        $("#qsearch").on("click", function () {
            handleSearch();
        });
        $(".audit1").click(function () {
            if (selectedId == "") {
                layer.msg("请选择要审核的数据");
                return false;
            }
            document.location.href = basePath + "ea/trilateral/ea_trilateralDataUpdate.jspa?type=audit1&id=" + selectedId;
        });

    }

    function handleSearch() {
        var searchValue = $("#search").val().trim();

        if (searchValue === "") {
            prompt("请输入搜索内容");
            return;
        }

        // 判断是字符还是数字
        var isNumber = isNumeric(searchValue);

        if (isNumber) {
            phone = searchValue; // 设置为电话搜索
            staffName = "";
        } else {
            staffName = searchValue; // 设置为姓名搜索
            phone = "";
        }

        // 重置分页参数
        pageNumber = 1;
        // 重新获取数据
        getEntryStaffData();
    }

    function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        const regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(window.location.search);
        return results ? (results[2] ? decodeURIComponent(results[2].replace(/\+/g, " ")) : "") : "";
    }

    function isNumeric(value) {
        // 检查是否为纯数字（包括整数和小数）
        var numericRegex = /^\d+(\.\d+)?$/;
        return numericRegex.test(value.trim());
    }

    $(document).on("click", ".rowDiv", function (event) {
        let ulElement = $(this).closest('.data-ul');
        selectedId = ulElement.attr('id');
        selectCosId = ulElement.attr('cosid');
        // 检查当前元素是否已经有 active1 样式
        if (ulElement.hasClass('active1')) {
            document.location.href = basePath + "ea/trilateral/ea_trilateralDataUpdate.jspa?type=search&id=" + selectedId;
        } else {
            // 如果没有 active1 样式，仅设置选中状态
            $(".data-ul").removeClass("active1");
            ulElement.addClass("active1");
        }
    });
</script>
</html>