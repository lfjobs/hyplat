<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%@ page import="hy.ea.bo.human.Staff" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    TEshopCusCom t = (TEshopCusCom) session.getAttribute("key_shop_cus_com");
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>抢单派单</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap.css"/>
    <style>
        :root {
            --primary-color: #e74c3c;
            --primary-dark: #c0392b;
            --primary-light: #ff6b6b;
            --text-color: #333;
            --light-gray: #f5f5f5;
            --white: #fff;
        }

        body {
            font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
            color: var(--text-color);
            background-color: var(--light-gray);
            padding-top: 50px;
            padding-bottom: 80px;
        }

        .navbar {
            background-color: var(--primary-color);
            border: none;
            border-radius: 0;
            margin-bottom: 0;
        }

        .navbar-header {
            width: 100%; /* 让导航头部占满宽度 */
        }

        .navbar-brand {
            color: var(--white) !important;
            text-align: center; /* 文本居中 */
            width: 100%; /* 占满整个宽度 */
        }

        .navbar-brand, .navbar-nav li a {
            color: var(--white) !important;
        }

        .btn {
            padding: 8px 16px;
            border: 1px solid #d9d9d9;
            border-radius: 4px;
            cursor: pointer;
            margin-left: 10px;
        }

        .btn-primary {
            background: #1890ff;
            color: white;
            border: none;
            background-color: var(--primary-color);
            border-color: var(--primary-dark);
        }

        .btn-primary:hover, .btn-primary:focus {
            background-color: var(--primary-dark);
            border-color: var(--primary-dark);
        }

        /* Excel表格样式 */
        .excel-table {
            width: 100%;
            border-collapse: collapse;
            background-color: var(--white);
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
            margin-bottom: 15px;
            overflow-x: auto;
            display: block;
        }

        /*.excel-table thead {
            background-color: var(--primary-color);
            color: var(--white);
        }*/

        .excel-table th, .excel-table td {
            padding: 8px 10px;
            border: 1px solid #ddd;
            text-align: left;
            font-size: 14px;
        }

        .excel-table th {
            font-weight: bold;
            white-space: nowrap;
        }

        .excel-table tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .excel-table tr {
            cursor: pointer;
            transition: background-color 0.2s;
        }

        .table-responsive {
            overflow-x: auto;
            -webkit-overflow-scrolling: touch;
        }

        .loading-indicator {
            text-align: center;
            padding: 20px;
        }

        .no-data {
            text-align: center;
            padding: 20px;
            background-color: var(--white);
            border: 1px solid #ddd;
        }

        /* 上拉加载更多样式 */
        .load-more {
            text-align: center;
            padding: 10px;
            color: #666;
            background-color: var(--white);
            border: 1px solid #ddd;
            margin-top: 10px;
            cursor: pointer;
        }

        .load-more:hover {
            background-color: #f5f5f5;
        }

        .loading-more {
            color: #999;
        }

        .no-more-data {
            color: #999;
            text-align: center;
            padding: 10px;
        }

        .modal-content {
            top: 1rem;
        }

        .form-group {
            margin-bottom: 0.1rem;
        }

        .form-group label {
            top: 0.5rem;
            font-size: 10px;
        }

        .file-upload-wrapper input[type=file] {
            position: absolute;
            left: 0;
            top: 0;
            opacity: 0;
            width: 100%;
            height: 100%;
            cursor: pointer;
        }

        .table-responsive {
            width: 100%;
            margin-bottom: 15px;
            margin-top: 2rem;
            overflow-y: hidden;
            -ms-overflow-style: -ms-autohiding-scrollbar;
            border: 1px solid #ddd;
        }

        .ddaddress {
            background: url(<%=basePath%>images/ea/edmandServe/site_ico.png) no-repeat right center;
            background-size: auto 2rem;
            padding-right: 3rem;
        }


        .clickInput {
            background: url(<%=basePath%>/images/WFJClient/pc/5l5c/pic_01.png) no-repeat right center;
            background-size: auto 2rem;
            padding-right: 3rem;
        }

        .modal-dialog {
            position: relative;
            width: auto;
            margin: 5rem 1rem;
        }

        .nest_page {
            position: fixed;
            width: 100%;
            height: 100%;
            top: 0;
            right: 0;
            z-index: 9999;
            background: #fff;
            overflow-y: scroll;
            display: none;
        }

        .nest_hd {
            line-height: 5rem;
            border-bottom: 1px solid #dcdcdc;
            text-align: center;
            font-size: 2rem;
            color: #222222;
            position: fixed;
            width: 100%;
            background: #fff;
            z-index: 9;
        }

        .nest_back {
            position: absolute;
            width: 2rem;
            height: 2rem;
            left: 1.5rem;
            top: 1.4rem;
            background: url(<%=basePath%>css/ea/images/back.png) no-repeat center;
            background-size: 1.5rem;
        }

        .nest_bd {
            margin-top: 2.16rem;
            /* width: 85%; */
            border-radius: 0.5rem;
            margin: 20% auto 0 auto;
            /* padding: 0 0.85rem 0.85rem 0.85rem; */
            background-color: #fff;
            height: 95%;
        }

        .nest_page .nest_bd iframe {
            height: 100%;
            width: 100%;
        }

        /* 遮罩层样式 */
        .modal-overlay {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: rgba(0, 0, 0, 0.6);
            display: none;
            z-index: 1051;
        }

        /* 弹框样式 */
        .cwmodal {
            position: fixed;
            top: 5rem;
            left: 1rem;
            background: white;
            border-radius: 6px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
            overflow: hidden;
            width: 95%;
            max-width: 600px;
            z-index: 1052;
            display: none;
            max-height: 80vh;
            overflow-y: auto;
        }

        .modal-header {
            background: #1890ff;
            color: white;
            padding: 16px;
            font-size: 16px;
            position: relative;
        }

        .modal-close {
            position: absolute;
            right: 16px;
            top: 16px;
            cursor: pointer;
            font-size: 18px;
        }

        .modal-close:hover {
            color: #f0f0f0;
        }

        .search-box {
            padding: 16px;
            background: white;
            border-bottom: 1px solid #e8e8e8;
        }

        .search-input {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #d9d9d9;
            border-radius: 4px;
            font-size: 14px;
        }

        .documents-list {
            padding: 0;
            max-height: 300px;
            overflow-y: auto;
        }

        .document-item {
            padding: 16px;
            border-bottom: 1px solid #e8e8e8;
            cursor: pointer;
            transition: background 0.2s;
        }

        .document-item:hover {
            background: #f0f7ff;
        }

        .document-item.selected {
            background: #e6f7ff;
            border-left: 3px solid #1890ff;
        }

        .doc-type {
            font-weight: bold;
            color: #262626;
            margin-bottom: 6px;
        }

        .doc-info {
            font-size: 13px;
            color: #595959;
            display: flex;
            flex-wrap: wrap;
            gap: 12px;
        }

        .doc-info span {
            margin-right: 15px;
        }

        .modal-footer {
            padding: 16px;
            text-align: right;
            background: white;
            border-top: 1px solid #e8e8e8;
        }

        .empty-state {
            text-align: center;
            padding: 40px 20px;
            color: #8c8c8c;
            font-size: 14px;
        }

    </style>
</head>
<body>
<!-- 顶部导航栏 -->
<nav class="navbar navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header" style="width: 100%;margin-left: 0;">
            <!-- 返回按钮 -->
            <a class="navbar-back btn-link"
               style="position: absolute;left: 10px;top: 0;color: white;padding: 15px 0.5rem;">
                <span class="glyphicon glyphicon-chevron-left"></span>
            </a>
            <!-- 居中的标题 -->
            <a class="navbar-brand" href="#" style="text-align: center; display: block;">抢单派单</a>
        </div>
    </div>
</nav>

<!-- 主内容区 -->
<div class="container">
    <!-- 工种列表 -->
    <div class="table-responsive">
        <table class="excel-table" id="certificate-table">
            <thead style="background-color:#d2cece">
            <tr>
                <th>序号</th>
                <th>发单人</th>
                <th>时间</th>
                <th>项目</th>
                <th>工种</th>
                <th>金额</th>
                <th>地址</th>
                <th>进度</th>
            </tr>
            </thead>
            <tbody id="certificate-list">
            <!-- 动态加载的证件列表内容 -->
            <tr>
                <td colspan="6" class="loading-indicator">
                    <span class="glyphicon glyphicon-refresh glyphicon-spin"></span> 加载中...
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <!-- 上拉加载更多 -->
    <div id="load-more-container">
        <div class="load-more" id="load-more-btn">加载更多</div>
    </div>
</div>

<!-- 工种详情模态框 -->
<%--<div class="modal fade" id="certificateDetailModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header" style="background-color: #e74c3c; color: white;">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">详情</h4>
            </div>
            <div class="modal-body" id="certificate-detail-content">
                <!-- 动态加载的证件详情内容 -->
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                &lt;%&ndash;<button type="button" class="btn btn-primary" id="btn-edit-certificate">编辑</button>&ndash;%&gt;
                <button type="button" class="btn btn-danger" id="btn-delete-certificate">删除</button>
            </div>
        </div>
    </div>
</div>--%>


<!-- 遮罩层 -->
<div class="modal-overlay" id="modalOverlay"></div>


<script type="text/javascript" src="<%=basePath%>js/pdfh5/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/edit.js"></script>
<!-- 添加在head部分 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    // 全局变量
    var pageNumber = 1;
    var totalPages = 1;
    var isLoading = false;
    var hasMoreData = true;
    let basePath = "<%=basePath%>";
    let staffID = "<%=t.getStaffid()%>";
    let originPage = '${param.originPage}';
    let company ='';
    var sccid = "<%=t.getSccId()%>";
    $(document).ready(function () {

        if (originPage != "" && originPage != null) {
            requestPage = originPage.split("-");
            if (requestPage[0] == "win") {
                $("body").css("padding-top", "0");
                $(".navbar").hide();
            }
        }

        // 返回按钮点击事件
        $('.navbar-back').click(function () {
            // 返回上一页
            window.history.back();
        });

        // 初始化页面时加载证件列表
        loadCertificateList(1);

        // 加载更多按钮点击事件
        $(document).on('click', '#load-more-btn', function () {
            if (!isLoading && hasMoreData) {
                loadMoreData();
            }
        });

        // 滚动加载更多
        $(window).scroll(function () {
            if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
                if (!isLoading && hasMoreData) {
                    loadMoreData();
                }
            }
        });

    });


    // 选择证件功能
    function selectdic() {
        const items = document.querySelectorAll('.document-item');
        // 选择证件功能
        items.forEach(item => {
            item.addEventListener('click', function () {
                // 移除之前的选择
                items.forEach(i => i.classList.remove('selected'));
                // 添加新选择
                this.classList.add('selected');
            });
        });
    }

    // 加载更多数据
    function loadMoreData() {
        if (pageNumber < pageCount) {
            pageNumber++;
            loadCertificateList(pageNumber, true);
        } else {
            hasMoreData = false;
            $('#load-more-container').html('<div class="no-more-data">没有更多数据了</div>');
        }
    }

    // 加载证件列表
    function loadCertificateList(pageNumber, isAppend) {
        if (isLoading) return;

        isLoading = true;
        if (!isAppend) {
            $('#certificate-list').html('<tr><td colspan="6" class="loading-indicator"><span class="glyphicon glyphicon-refresh glyphicon-spin"></span> 加载中...</td></tr>');
        } else {
            $('#load-more-btn').html('<span class="glyphicon glyphicon-refresh glyphicon-spin"></span> 加载中...').addClass('loading-more');
        }

        $.ajax({
            url: basePath + '/ea/dserve/sajax_ea_detailListBywts.jspa',
            type: 'GET',
            data: {
                pagenumber: pageNumber || 1,
                pageSize: 30,
                wtype: "1"
            },
            dataType: 'json',
            success: function (data) {
                var member = eval("(" + data + ")");
                if (member) {
                    if (member.pageForm) {
                        pageCount = member.pageForm.pageCount || 1;
                        pageNumber = member.pageForm.pageNumber || 1;
                        if (member.pageForm.list && member.pageForm.list.length > 0) {
                            renderCertificateList(member.pageForm, isAppend);

                            if (pageNumber >= pageCount) {
                                hasMoreData = false;
                                $('#load-more-container').html('<div class="no-more-data">没有更多数据了</div>');
                            } else {
                                $('#load-more-container').html('<div class="load-more" id="load-more-btn">加载更多</div>');
                            }
                        } else {
                            if (!isAppend) {
                                $('#certificate-list').html('<tr><td colspan="13" class="no-data">没有找到任何证件信息</td></tr>');
                            }
                            hasMoreData = false;
                            $('#load-more-container').html('<div class="no-more-data">没有更多数据了</div>');
                        }
                    } else {
                        if (!isAppend) {
                            $('#certificate-list').html('<tr><td colspan="13" class="no-data">没有找到任何证件信息</td></tr>');
                        }
                    }
                } else {
                    if (!isAppend) {
                        $('#certificate-list').html('<tr><td colspan="13" class="no-data">加载失败，请稍后重试</td></tr>');
                    }
                }
                isLoading = false;
            },
            error: function () {
                if (!isAppend) {
                    $('#certificate-list').html('<tr><td colspan="13" class="no-data">加载失败，请稍后重试</td></tr>');
                }
                isLoading = false;
                $('#load-more-btn').text('加载更多').removeClass('loading-more');
            }
        });
    }

    // 渲染证件列表
    function renderCertificateList(pageForm, isAppend) {
        var html = '';
        var list = pageForm.list;

        for (var i = 0; i < list.length; i++) {
            var t = list[i];
            /*let adate;
            if (t.cwdate!=null&&t.cwdate!=""){
                adate=formatTimestamp(new Date(t.cwdate),'YYYY-MM-DD');
            }*/
            let status;
            switch (t.ddstatus) {
                case '0':
                    status = '抢单中';
                    break;
                case '1':
                    status = '已确认订单';
                    break;
                case '2':
                    status = '过期';
                    break;
                case '3':
                    status = '移除';
                    break;
                case '4':
                    status = '结算';
                    break;
                default:
                    status = '__';
                    break;
            }
            /*状态 0：抢单中 1：已确认订单 2：过期 3：移除 4：结算*/
            html += '<tr data-id="' + t.ddid + '">';
            html += '  <td>' + ((pageNumber - 1) * 15 + i + 1) + '</td>';
            html += '  <td>' + (t.ddcontactname || '--') + '</td>';//发单人
            html += '  <td>' + (t.ddadddate || '--') + '</td>';//时间
            html += '  <td>' + (t.ddtitle || '--') + '</td>';//项目
            html += '  <td>' + (t.ddworktype || '--') + '</td>';//工种
            html += '  <td>' + (t.ddexpectprice || '--') + '</td>';//金额
            html += '  <td>' + (t.ddaddress || '--') + '</td>';//地址
            html += '  <td>' + (status || '--') + '</td>';//进度
            html += '</tr>';
        }

        if (isAppend) {
            $('#certificate-list').append(html);
            $('#load-more-btn').text('加载更多').removeClass('loading-more');
        } else {
            $('#certificate-list').html(html);
        }

        // 绑定行点击事件
        $('#certificate-list tr[data-id]').click(function () {
            var id = $(this).data('id');
            xp(id);
        });
    }

    // 格式化日期显示
    function formatDate(dateStr) {
        if (!dateStr) return '--';
        var date = new Date(dateStr);
        return date.getFullYear() + '-' +
            padZero(date.getMonth() + 1) + '-' +
            padZero(date.getDate());
    }

    // 格式化日期为input[type=date]需要的格式
    function formatDateForInput(dateStr) {
        if (!dateStr) return '';
        var date = new Date(dateStr);
        return date.getFullYear() + '-' +
            padZero(date.getMonth() + 1) + '-' +
            padZero(date.getDate());
    }

    // 补零
    function padZero(num) {
        return num < 10 ? '0' + num : num;
    }

    // 替换alert
    function showAlert(type, title, message) {
        Swal.fire({
            icon: type,
            title: title,
            text: message,
            toast: true,
            //position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true
        });
    }

    // 替换confirm
    function showConfirm(title, text, confirmCallback) {
        Swal.fire({
            title: title,
            text: text,
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#e74c3c',
            cancelButtonColor: '#7f8c8d',
            confirmButtonText: '确定',
            cancelButtonText: '取消'
        }).then((result) => {
            if (result.isConfirmed) {
                confirmCallback();
            }
        });
    }

    $(".nest_back").click(function () {
        $(".nest_page").hide();
    });

</script>
</body>
</html>
