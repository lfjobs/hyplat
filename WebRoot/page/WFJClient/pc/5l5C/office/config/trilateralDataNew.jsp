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
        .tab-item1 {
            flex: 1;
            text-align: center;
            color: black;
        }

        .tab-item1.active {
            color: rgb(22, 186, 170);;
            font-weight: bold;
        }

        .tab-item2 {
            flex: 1;
            text-align: center;
            color: black;
        }

        .tab-item2.active {
            color: rgb(22, 186, 170);;
            font-weight: bold;
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
            text-align: center;
            margin: 0;
            padding: 8px 2px;
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
            三方平台推广
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content">
    <section class="search">
        <ul class="tabContainer1" style="display:flex;font-size: 0.81rem;height: 50px;align-items: center;">
            <li class="tab-item1 two active">全部账号</li>
            <li class="tab-item1 two">微信(订阅号)</li>
            <li class="tab-item1 two">抖音</li>
            <li class="tab-item1 two">小红书</li>
        </ul>
    </section>
    <section class="search">
        <ul class="tabContainer1" style="display:flex;font-size: 0.81rem;align-items: center;">
            <li class="tab-item2 two" style="height: 15px">已托管</li>
            <li class="tab-item2 two" style="height: 15px">未托管</li>
            <li class="tab-item2 two totals" style="height: 15px">汇总</li>
            <li class="tab-item2 two audit1" style="height: 15px">审核</li>
        </ul>
    </section>
    <section class="sec-nav sec-hide ">
        <ul class="clearfix" style="display: flex">
            <li class="clearfix">
                <p class="add">添加</p>
            </li>
            <li class="clearfix">
                <p class="edit">修改</p>
            </li>
            <li class="clearfix">
                <p class="del">删除</p>
            </li>
            <li class="clearfix">
                <p class="distribution" onclick="assignment()">分配</p>
            </li>
            <li class="clearfix">
                <p class="privateMessage">私信</p>
            </li>

            <li class="clearfix">
                <%--                <!-- 隐藏的 input 元素 -->--%>
                <%--                <input type="file" id="excelFile" accept=".xlsx,.xls" style="display: none">--%>
                <%--                <!-- 视觉上的“导入”按钮 -->--%>
                <p class="importData" onclick="importData()">导入</p>
            </li>
            <li class="clearfix">
                <p class="print" onclick="printDetail()">打印</p>
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
    $(function () {
        getEntryStaffData();
        bindEvents();
    });
    document.addEventListener('DOMContentLoaded', function () {
        const tabs = document.querySelectorAll('.tab-item1');
        tabs.forEach(tab => {
            tab.addEventListener('click', function () {
                tabs.forEach(t => t.classList.remove('active'));
                this.classList.add('active');
                const clickedValue = this.textContent.trim();
                if (clickedValue === '全部账号') {
                    type = 'all';
                } else if (clickedValue === '微信(订阅号)') {
                    type = '微信';
                } else if (clickedValue === '抖音') {
                    type = '抖音';
                } else if (clickedValue === '小红书') {
                    type = '小红书';
                }
                getEntryStaffData();
            });
        });
        const tabs1 = document.querySelectorAll('.tab-item2');
        tabs1.forEach(tab => {
            tab.addEventListener('click', function () {
                tabs1.forEach(t => t.classList.remove('active'));
                this.classList.add('active');
                const clickedValue = this.textContent.trim();
                if (clickedValue === '已托管') {
                    trusteeship = '1';
                } else if (clickedValue === '未托管') {
                    trusteeship = '2';
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
        // var type = getParameterByName("type");
        const param = new Array();
        param.push("pageNumber=" + pageNumber);
        param.push("&&pageSize=" + pageSize);
        param.push("&&uploadProject=" + type);
        param.push("&&trusteeship=" + trusteeship);
        // param.push("&&endTime=" + endTime);
        const url = basePath + "ea/trilateral/sajax_ea_getTrilateral.jspa?" + param.join("");
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

        // 添加
        $(".add").click(function () {
            document.location.href = basePath + "ea/trilateral/ea_trilateralDataAdd.jspa";
        })
        // 修改
        $(".edit").click(function () {
            if (selectedId == "") {
                layer.msg("请选择将要修改的数据");
                return false;
            }
            document.location.href = basePath + "ea/trilateral/ea_trilateralDataUpdate.jspa?id=" + selectedId;
        });
        //汇总
        $(".totals").click(function () {
            document.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/trilateralDataTotals.jsp";
        });
        //审核
        $(".audit1").click(function () {
            document.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/trilateralDataAudit.jsp";
        });

        // 删除
        $(".del").click(function () {
            if (selectedId == "") {
                layer.msg("请选择将要删除的数据");
                return false;
            }
            layer.confirm('确定删除', {
                title: '温馨提示',
                skin: 'delete-class',
                btn: ['取消', '确定']
            }, function () {
                layer.close(layer.index);
            }, function () {
                var url = basePath
                    + "ea/trilateral/sajax_ea_delTrilateralData.jspa?id=" + selectedId;
                $.ajax({
                    type: "GET",
                    url: url,
                    async: false,
                    dataType: "json",
                    success: function (data) {
                        pageNumber = 1, pageSize = 40, pageCount = 0;
                        getEntryStaffData();
                        layer.close(layer.index);
                        selectedId = "";
                    },
                    error: function (data) {
                        layer.msg("保存失败");
                    }
                })

            });
        });


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

    /**
     * 分配
     */
    function assignment() {
        if (selectedId == "") {
            layer.msg("请选择分配数据");
            return false;
        }
        localStorage.setItem("sfptId", selectedId);
        document.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/selectCompany.jsp?type=sfpt";
    }

    /**
     * 数据导入
     */
    function importData() {
        var url = "ea/trilateral/ea_importData.jspa";
        window.location.href = basePath + url;
    }

    function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        const regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(window.location.search);
        return results ? (results[2] ? decodeURIComponent(results[2].replace(/\+/g, " ")) : "") : "";
    }

    /*私信*/
    /*打印*/
    function printDetail() {
        if (selectedId == "") {
            layer.msg("请选择要打印的数据");
            return false;
        }
        if (selectedId != "" && selectedId != null) {
            var u = navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

            if (isAndroid || isiOS) {
                alert("请在电脑端进行打印操作");
                return;
            }
            var url = "ea/trilateral/ea_toTrilateral.jspa";
            var parameter = "?id=" + selectedId;
            // window.open = basePath + url + parameter;
            window.open(basePath + url + parameter);
        } else {
            alert("请选择要打印的数据");
            return false;
        }
    }


</script>
</html>