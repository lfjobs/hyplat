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
    <title>员工</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/visitorMessagesHome2.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/staff/selectData.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>

    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
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
            选择联系人
        </li>
        <li>

        </li>
    </ul>
</header>
<div class="content" style="height: 884px;">
    <div class="input-box1">
        <input id="searchIn" class="input-11" type="text" placeholder="搜索">
    </div>
    <div class="input-box2">
        <ul style="display: flex; ">
            <li>选择当前所有联系人</li>
            <%--                    <li><img src="<%=basePath%>js/tree/codebase/imgs/iconCheckAll.gif" alt="全选"></li>--%>
            <li class="selectedAll" style="margin-left: auto"><img
                    src="<%=basePath%>js/tree/codebase/imgs/iconUncheckAll.gif" alt="全不选"></li>
        </ul>
    </div>
    <section class="sec-list" style=" overflow: hidden auto;">
        <div class="div-sec-data">
            <div class="data-title1">
            </div>
            <div class="data-list div-data" style="overflow-y:auto;overflow-x:hidden;height: 700px;padding-left: 25px;">
            </div>
        </div>
    </section>
    <div class="but">
        <div class="btn-submit">确定</div>
    </div>
</div>
<script>

    let pageNumber = 1, pageSize = 40, pageCount = 0;
    let selectedId = "", selectCosId = "", scrollBool = true;

    let item = localStorage.getItem("selectedIdCompany");
    let localTemplateText = localStorage.getItem("localTemplateText");
    let firstNumber = localStorage.getItem("firstNumber");
    //filterValue 筛选条件值
    let selectdeData = [], staffIds = [], names = [], filterValue = "";
    let selectedData = [];

    $(function () {
        initCss();
        bindEvents();
        renderSelectedData()
    });
    function initCss() {
        $(".content").height($(".body").height() - $("header").height());
        $(".sec-list").height($(".content").height() - $(".input-box1").height() - $(".input-box1").height() - $(".but").height() - 25);
    }

    /**
     * 点击事件
     */
    function bindEvents() {
        $(document).on("click", ".data-ul", function (event) {
            let selectedId = event.currentTarget.id;
            let selectCosId = event.currentTarget.attributes["cosid"].value;

            // 获取所有带有 'selected' 类的列表项中的图片
            let imgElements = $(this).find(".selected img");

            imgElements.each(function () {
                let imgSrc = $(this).attr('src'); // 获取当前图片的 src 属性
                // 判断当前图片是否为 'iconUncheckAll.gif'
                if (imgSrc && imgSrc.includes('iconUncheckAll.gif')) {
                    // 更换为另一张图片
                    $(this).attr('src', basePath + 'js/tree/codebase/imgs/iconCheckAll.gif');
                } else {
                    // 如果不是，则还原为原始图片
                    $(this).attr('src', basePath + 'js/tree/codebase/imgs/iconUncheckAll.gif');
                }
            });
        });
        // 监听滚动事件
        const divElement = document.querySelector('.data-list');
        divElement.addEventListener('scroll', function () {
            if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight || 1 == 1) {
                if (pageNumber < pageCount && scrollBool) {
                    scrollBool = false;
                    pageNumber++;
                    if (filterValue == "") {
                        renderSelectedData();
                    } else {
                        renderSelectedData();
                    }

                }

            }
        })
    }


    const searchIn = document.getElementById('searchIn');
    // 添加失焦事件监听器
    searchIn.addEventListener('blur', function () {
        const query = searchIn.value.trim();
        if (query) {
            performSearch(query);
        } else {
            getEntryStaffData();
        }
    });
    $(document).on("click", ".selectedAll", function (event) {
        // 获取所有带有 'selected' 类的列表项中的图片
        var selectedAll = document.querySelectorAll('.selectedAll img');

        var selectedItems = document.querySelectorAll('.selected img');

        selectedAll.forEach(function (item) {
            // 判断当前图片是否为 'iconUncheckAll.gif'
            if (item.src.includes('iconUncheckAll.gif')) {
                selectedItems.forEach(function (item) {
                    // 判断当前图片是否为 'iconUncheckAll.gif'
                    // 更换为另一张图片
                    item.src = basePath + 'js/tree/codebase/imgs/iconCheckAll.gif';
                });
                // 更换为另一张图片
                item.src = basePath + 'js/tree/codebase/imgs/iconCheckAll.gif';
            } else {
                selectedItems.forEach(function (item) {
                    // 更换为另一张图片
                    item.src = basePath + 'js/tree/codebase/imgs/iconUncheckAll.gif';
                });
                // 如果不是，则还原为原始图片
                item.src = basePath + 'js/tree/codebase/imgs/iconUncheckAll.gif';
            }
        });
    });


    //确定发送按钮
    $(document).on("click", ".btn-submit", function (event) {
        let selectedDatas2 =[];
        var selectedItems = document.querySelectorAll('.selected img');
        for (let i = 0; i < selectedItems.length; i++) {
            if (selectedItems[i].src.includes('iconCheckAll.gif')) {
                const ul = selectedItems[i].closest('ul');
                const phone = ul.querySelector('.push-li3').getAttribute('phone');
                const name = ul.querySelector('.push-li1').getAttribute('name');
                const companyId = ul.querySelector('.push-li4').getAttribute('companyId');
                const staffId = ul.id;
                const ulData = {
                    phone: phone,
                    name: name,
                    staffId: staffId,
                    companyId: companyId
                };
                selectedDatas2.push(ulData);
            }
        }
        var typeValue = getParameterByName("type");
        if (typeValue == "person") {
            localStorage.setItem("selectedDatas2", JSON.stringify(selectedDatas2));
            document.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/setAutomaticDialing.jsp?type=select";
        } else {
            localStorage.setItem("selectedDatas3", JSON.stringify(selectedDatas2));
            document.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/setAutomaticDialing.jsp?type=lv";
        }
    })
    //查询
    function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        const regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(window.location.search);
        return results ? (results[2] ? decodeURIComponent(results[2].replace(/\+/g, " ")) : "") : "";
    }
    function renderSelectedData() {
        const storedData = localStorage.getItem('selectedDatas1');
        const myObject = storedData ? JSON.parse(storedData) : null;
        const htmlstr = [];
        if (myObject == null) {
            $(".data-list").html("暂无数据");
            $(".data-list").css({"display": "flex", "align-items": "center", "justify-content": "center"});
        } else {
            let counter = 1; // 初始化计数器
            myObject.forEach(data => {
                htmlstr.push("<ul id='" + data.staffId + "' cosid='" + data.staffId + "' class='data-ul data-ul-" + data.staffId + "'>");
                htmlstr.push("<li style='text-align: center;'>" + counter + "</li>"); // 使用计数器
                htmlstr.push("<li name='" + data.name + "' class='push-li1'>" + data.name + "</li>");
                htmlstr.push("<li phone='" + data.phone + "' class='push-li3'>" + data.phone + "</li>");
                htmlstr.push("<li class='selected'><img src=" + basePath + "js/tree/codebase/imgs/iconUncheckAll.gif></li>");
                htmlstr.push("<li style='display: none' companyId='" + data.companyId + "' class='push-li4'>" + data.companyId + "</li>");
                htmlstr.push("</ul>");
                counter++; // 递增计数器
            });
            const moreData = document.getElementById('more-data');
            if (moreData != null) {
                moreData.remove()
            }
            if (pageNumber > 1) {
                $(".data-list").append(htmlstr.join(""));
            } else {
                $(".data-list").html(htmlstr.join(""));
            }
            $(".data-list").css("display", "block");
        }
        scrollBool = true;
    }
</script>
</body>
</html>