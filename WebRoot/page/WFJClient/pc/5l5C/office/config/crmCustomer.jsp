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
    <title>未注册粉丝导入</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/crmCustomerPo.css">
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
            未注册粉丝导入
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content">
    <section class="sec-nav sec-hide ">
        <ul class="clearfix">
            <li class="clearfix">
                <p onclick="addDate()" class="add">添加</p>
            </li>
            <li class="clearfix">
                <p onclick="updateData()" class="edit">修改</p>
            </li>
            <li class="clearfix">
                <p onclick="delData()" class="del">删除</p>
            </li>
            <li class="clearfix">
                <p onclick="queryDate()" class="query">查询</p>
            </li>
            <li class="clearfix">
                <p onclick="importData()" class="importData">导入</p>
            </li>
        </ul>
    </section>
    <section class="sec-list">
        <div class="div-sec-data">
            <div class="data-title">
                <ul class="flex-container">
                    <li>姓名</li>
                    <li>电话</li>
                    <li>身份证</li>
                    <li>地址</li>
                    <li>导入人</li>
                </ul>
            </div>
            <div class="container">
                <div class="dataPo">
                    <c:forEach var="item" items="${crmCustomerPOList}">
                        <ul class="flex-container" data-id="${item.id}">
                            <li style="display: none">${item.id}</li>
                            <li>${item.userName}</li>
                            <li>${item.contactInfo}</li>
                            <li>${item.cardNumber}</li>
                            <li>${item.residentialAddress}</li>
                            <li>${item.importerId}</li>
                        </ul>
                    </c:forEach>
                </div>
            </div>
        </div>
    </section>

</div>
<script>
    function importData() {
        var url = "ea/crmCustomerPO/ea_importData.jspa";
        window.location.href = basePath + url;
    }

    var selectedId = null;
    $(document).ready(function () {
        // 绑定点击事件到所有可选中的 ul 元素
        $('.dataPo .flex-container').click(function () {
            var $this = $(this);
            var id = $this.data('id'); // 获取 data-id 属性值作为 ID
            if (selectedId === id) {
                // 如果点击的是当前已选中的项，则取消选中
                $this.removeClass('gray');
                selectedId = null;
            } else {
                // 否则，取消之前选中的项，并选中当前项
                if (selectedId !== null) {
                    $('[data-id="' + selectedId + '"]').removeClass('gray');
                }
                $this.addClass('gray');
                selectedId = id;
            }
        });
    });

    function delData() {
        if (selectedId != "" && selectedId != null) {
            var deleteUrl = "ea/crmCustomerPO/ea_delCustomer.jspa?id=" + selectedId;
            $.ajax({
                url: basePath + deleteUrl,
                type: 'GET',
                success: function (response) {
                    // 删除成功后，刷新页面
                    var refreshUrl = "ea/crmCustomerPO/ea_crmCustomerPOList.jspa";
                    window.location.href = basePath + refreshUrl;
                },
                error: function (xhr, status, error) {
                    layer.msg("删除失败，请重试");
                }
            });
        } else {
            layer.msg("请选择需要删除的数据");
            return;
        }

    }

    function updateData() {
        if (selectedId != "" && selectedId != null) {
            var url = "ea/crmCustomerPO/ea_updCustomer.jspa?id=" + selectedId;
            window.location.href = basePath + url;
        } else {
            layer.msg("请选择需要修改的数据");
            return;
        }
    }

    function addDate() {
        var url = "ea/crmCustomerPO/ea_addCustomer.jspa";
        window.location.href = basePath + url;
    }

    function queryDate() {
        if (selectedId != "" && selectedId != null) {
            var url = "ea/crmCustomerPO/ea_queryCustomer.jspa?id=" + selectedId;
            window.location.href = basePath + url;
        } else {
            layer.msg("请选择需要查询的数据");
            return;
        }
    }

</script>
</body>
</html>
