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
    Staff s = (Staff) session.getAttribute("key_staff");
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>工种管理</title>
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

        .excel-table thead {
            background-color: #9c9b9b;
            color: var(--white);
        }

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

        /* 悬浮按钮样式 */
        .floating-jspa-btn {
            position: fixed;
            bottom: 70px;
            right: 20px;
            width: 56px;
            height: 56px;
            border-radius: 50%;
            background-color: var(--primary-color);
            color: white;
            text-align: center;
            line-height: 56px;
            font-size: 24px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
            z-index: 1000;
            cursor: pointer;
            transition: all 0.3s;
        }

        .floating-jspa-btn:hover {
            background-color: var(--primary-dark);
            transform: scale(1.1);
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
            <a class="navbar-brand" href="#" style="text-align: center; display: block;">用工认证</a>
        </div>
    </div>
</nav>

<!-- 主内容区 -->
<div class="container">
    <!-- 工种列表 -->
    <div class="table-responsive">
        <table class="excel-table" id="certificate-table">
            <thead>
            <tr>
                <th>序号</th>
                <th>姓名</th>
                <%--<th>身份证号</th>--%>
                <%--<th>添加时间</th>--%>
                <th>项目工种</th>
                <th>证件</th>
                <th>电话</th>
                <th>地址</th>
                <th>状态</th>
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

<!-- 右下角悬浮按钮 -->
<div class="floating-jspa-btn" id="btn-add-certificate">
    <span class="glyphicon glyphicon-plus"></span>
</div>

<!-- 工种详情模态框 -->
<div class="modal fade" id="certificateDetailModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header" style="background-color: #e74c3c; color: white;">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">工种详情</h4>
            </div>
            <div class="modal-body" id="certificate-detail-content">
                <!-- 动态加载的证件详情内容 -->
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <%--<button type="button" class="btn btn-primary" id="btn-edit-certificate">编辑</button>--%>
                <button type="button" class="btn btn-danger" id="btn-delete-certificate">删除</button>
            </div>
        </div>
    </div>
</div>

<!-- 添加/编辑工种模态框 -->
<div class="modal fade" id="certificateEditModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header" style="background-color: #e74c3c; color: white;">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="edit-modal-title">新增工种</h4>
            </div>
            <div class="modal-body">
                <form id="certificate-form">
                    <input type="hidden" id="cwkey" name="cwkey"/>
                    <input type="hidden" id="cwid" name="cwid"/>
                    <input type="hidden" id="cwstaffid" name="cwstaffid" value="<%=t.getStaffid()%>"/>
                    <input type="hidden" id="cwcustid" name="cwcustid" value="<%=t.getSccId()%>"/>

                    <!-- 姓名 -->
                    <div class="form-group">
                        <div class="row">
                            <label for="staffName" class="col-xs-3 control-label">姓名</label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="staffName" name="staffName"
                                       required/>
                            </div>
                        </div>
                    </div>

                    <!-- 身份证号 -->
                    <%--<div class="form-group">
                        <div class="row">
                            <label for="cwcode" class="col-xs-3 control-label">身份证号</label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="cwcode" name="cwcode"/>
                            </div>
                        </div>
                    </div>--%>

                    <!-- 手机号 -->
                    <div class="form-group">
                        <div class="row">
                            <label for="cwphone" class="col-xs-3 control-label">手机号</label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="cwphone" name="cwphone"
                                       value="<%=t.getAccount()%>"
                                       required/>
                            </div>
                        </div>
                    </div>

                    <!-- 项目工种 -->
                    <div class="form-group">
                        <div class="row">
                            <label for="cwvalue" class="col-xs-3 control-label">项目工种</label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control clickInput" id="cwvalue" name="cwvalue"/>
                                <input type="hidden" id="cwscodeid" name="cwscodeid"/>
                            </div>
                        </div>
                    </div>

                    <!-- 证件 -->
                    <div class="form-group">
                        <div class="row">
                            <label for="cwvalue" class="col-xs-3 control-label">
                                证件
                            </label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control clickInput" id="cwcredentialsVal"
                                       name="cwcredentialsVal" required/>
                                <input type="hidden" id="cwcredentialsID" name="cwcredentialsID"/>
                            </div>
                        </div>
                    </div>
                    <!-- 地址 -->
                    <div class="form-group">
                        <div class="row">
                            <label for="ddaddress" class="col-xs-3 control-label">
                                地址
                            </label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control ddaddress" id="ddaddress" name="ddaddress"
                                       placeholder="点击定位选择地址"/>
                                <input type="hidden" name="coordinate" id="coordinate"/>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="btn-save-certificate">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- iframe -->
<div class="nest_page" style="background: #f3f3f3;">
    <div class="nest_hd">
        <a href="###" class="nest_back"></a>
        <span>选择项目工种</span>
    </div>
    <div class="nest_bd" style="margin: 2.16rem auto 0px;">
        <iframe name="main" marginwidth="0" scrolling="yes" marginheight="0" frameborder="0" id="iframe-" border="0"
                framespacing="0" noresize="noResize" vspale="0"></iframe>
    </div>
</div>

<!-- 遮罩层 -->
<div class="modal-overlay" id="modalOverlay"></div>

<!-- 证件 -->
<div class="cwmodal" id="documentModal">
    <div class="modal-header">
        选择证件
        <span class="modal-close" id="modalClose">×</span>
    </div>
    <div class="search-box">
        <input type="text" class="search-input" id="searchInput" placeholder="搜索证件编号或用户名">
    </div>
    <div class="documents-list"></div>
    <div class="modal-footer">
        <button class="btn" id="cancelBtn">取消</button>
        <button class="btn btn-primary" id="confirmBtn">确认选择</button>
    </div>
</div>


<script type="text/javascript" src="<%=basePath%>js/pdfh5/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.validate.js"></script>
<!-- 添加在head部分 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    // 全局变量
    var pageNumber = 1;
    var totalPages = 1;
    var isLoading = false;
    var hasMoreData = true;
    let originPage="${param.originPage}";
    let basePath = "<%=basePath%>";
    let staffID = "<%=t.getStaffid()%>";

    $(document).ready(function () {

        if (staffID == null || staffID == "") {
            window.location.href = basePath + "page/WFJClient/pc/pc_login.jsp";
        }

        // 返回按钮点击事件
        $('.navbar-back').click(function () {
            // 返回上一页
            window.history.back();
        });

        // 初始化页面时加载证件列表
        loadCertificateList(1);

        // 搜索按钮点击事件
        $('#btn-search').click(function () {
            pageNumber = 1;
            hasMoreData = true;
            $('#certificate-list').empty();
            $('#load-more-container').html('<div class="load-more" id="load-more-btn">加载更多</div>');
            loadCertificateList(1);
        });

        // 新增证件按钮点击事件
        $('#btn-add-certificate').click(function () {
            $('#certificate-form')[0].reset();
            $('#certificate-form').validate().resetForm();
            $('#edit-modal-title').text('新绑定工种');
            $('#credentialskey').val('');
            $('#credentialsID').val('');
            $('#staffID').val(staffID);
            $('#companyID').val('');
            $('#certificateEditModal').modal('show');
        });

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

        // 保存工种按钮点击事件
        $('#btn-save-certificate').click(function () {
            if ($('#certificate-form').valid()) {
                saveCertificate();
            }
        });

        // 表单验证
        $('#certificate-form').validate({
            min: 30,
            rules: {
                staffName: "required",
                cwcode: "required",
                cwvalue: "required",
                cwcredentialsVal: "required",
                ddaddress: "required",
            },
            messages: {
                staffName: "请输入姓名",
                cwcode: "请输入身份证号",
                cwvalue: "请选择工种",
                cwcredentialsVal: "请选择工种对应的证件",
                ddaddress: "请选择地址"
            },
            errorElement: "em",
            errorPlacement: function (error, element) {
                error.addClass("help-block");
                error.insertAfter(element);
            },
            highlight: function (element, errorClass, validClass) {
                $(element).parents(".form-group").addClass("has-error").removeClass("has-success");
            },
            unhighlight: function (element, errorClass, validClass) {
                $(element).parents(".form-group").addClass("has-success").removeClass("has-error");
            }
        });

        //加载项目工种类别
        $("#cwvalue").click(function () {
            $("#iframe-").attr("src", basePath + "/page/ea/main/marketing/edmandServe/workType_save.jsp?originPage=win-gz&staffid=" + staffID);
            $(".nest_hd").show();
            $(".nest_bd").css("margin", "20% auto 0 auto").css("margin-top", "2.16rem");
            $(".nest_page").show();
            $(".overlay_text").hide();
        });

        //加载地图
        $("#ddaddress").click(function () {
            $("#iframe-").attr("src", basePath + "page/ea/main/marketing/edmandServe/locationMap.jsp?originPage=win-gz&staffid=" + staffID);
            $(".nest_hd").hide();
            $(".nest_bd").css("margin", "0").css("margin-top", "0");
            $(".nest_page").show();
            $(".overlay_text").hide();
        });

        $(document).on("click", ".document-item", function () {
            $(".document-item").removeClass("selected");
            $(this).add('selected');
        });


    });

    document.addEventListener('DOMContentLoaded', function () {
        // 获取DOM元素
        const openModalBtn = document.getElementById('cwcredentialsVal');
        const modalOverlay = document.getElementById('modalOverlay');
        const documentModal = document.getElementById('documentModal');
        const modalClose = document.getElementById('modalClose');
        const cancelBtn = document.getElementById('cancelBtn');
        const searchInput = document.getElementById('searchInput');

        // 打开弹框
        openModalBtn.addEventListener('click', function () {
            getcer();
            documentModal.style.display = 'block';
            modalOverlay.style.display = 'block';
            document.body.style.overflow = 'hidden'; // 禁止背景滚动
        });

        // 关闭弹框
        function closeModal() {
            documentModal.style.display = 'none';
            modalOverlay.style.display = 'none';
            document.body.style.overflow = ''; // 恢复背景滚动
        }

        // 关闭弹框事件
        modalClose.addEventListener('click', closeModal);
        cancelBtn.addEventListener('click', closeModal);
        modalOverlay.addEventListener('click', closeModal);

        // 阻止弹框内部点击事件冒泡到遮罩层
        documentModal.addEventListener('click', function (e) {
            e.stopPropagation();
        });

        // 搜索功能
        searchInput.addEventListener('keyup', function () {
            const searchText = this.value.toLowerCase();
            items.forEach(item => {
                const text = item.textContent.toLowerCase();
                item.style.display = text.includes(searchText) ? 'block' : 'none';
            });
        });

        // 确认选择按钮
        document.getElementById('confirmBtn').addEventListener('click', function () {
            const selectedItem = document.querySelector('.document-item.selected');
            if (selectedItem) {
                const docType = selectedItem.querySelector('.doc-type').textContent;
                const docInfo = selectedItem.querySelector('.doc-info').textContent;
                $("#cwcredentialsVal").val(docType);
                $("#cwcredentialsID").val($(".document-item.selected").find(".cwcredentialsID").val());
                closeModal();
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

    function gzfun(data) {
        $("#cwvalue").val(data.val);
        $("#cwscodeid").val(data.id);
        $('iframe').attr("src", "");
        $('iframe').empty();
        $(".nest_page").hide();
    }

    function page_address(param) {
        var address = param.address;
        var coordinate = param.coordinate;
        $("#ddaddress").val(address);
        $("#coordinate").val(coordinate);
        $('iframe').attr("src", "");
        $('iframe').empty();
        $(".nest_page").hide();
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
        let type="";
        if (originPage != "" && originPage != null) {
            if (originPage=="enterprise"){
                type="2";
            }else {
                type="1";
            }
        }
        $.ajax({
            url: basePath + 'ea/dserve/sajax_ea_wtypeListBySccid.jspa',
            type: 'GET',
            data: {
                pageNumber: pageNumber || 1,
                type:type
            },
            dataType: 'json',
            success: function (data) {
                var member = eval("(" + data + ")");
                if (member) {
                    staffName=member.staff.staffName;
                    staffIdentityCard=member.staff.staffIdentityCard;
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
            switch (t.cwstatus) {
                case '0':
                    status='使用中';
                    break;
                default:
                    status='已删除';
                    break;
            }
            html += '<tr data-id="' + t.cwkey + '">';
            html += '  <td>' + ((pageNumber - 1) * 15 + i + 1) + '</td>';
            html += '  <td>' + (t.cwstaffname || '--') + '</td>';//姓名
            /*html += '  <td>' + (t.cwcode || '--') + '</td>';*///身份证号
            /*html += '  <td>' + (adate|| '--') + '</td>';//时间*/
            html += '  <td>' + (t.cwvalue || '--') + '</td>';//项目工种
            html += '  <td>' + (t.cwcredenname || '--') + '</td>';//证件
            html += '  <td>' + (t.cwphone || '--') + '</td>';//手机号
            html += '  <td>' + (t.ddaddress || '--') + '</td>';//地址
            html += '  <td>' + (status || '--') + '</td>';//审核状态
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
            viewCertificate(id);
        });
    }

    // 查看证件详情
    function viewCertificate(id) {
        $.ajax({
            url: basePath + '/ea/dserve/sajax_ea_view.jspa',
            type: 'GET',
            data: {
                'tcw.cwkey': id
            },
            dataType: 'json',
            beforeSend: function () {
                $('#certificate-detail-content').html('<div class="text-center" style="padding: 20px;"><span class="glyphicon glyphicon-refresh glyphicon-spin"></span> 加载中...</div>');
            },
            success: function (data) {
                var member = eval("(" + data + ")");
                if (member && member.cert) {
                    let cert = member.cert;
                    let staff = member.staff;
                    var html = '<div class="certificate-detail">';
                    html += '  <h4 style="color: #e74c3c;">' + cert.cwvalue  + '</h4>';
                    html += '  <div class="row">';
                    html += '    <div class="col-xs-6"><p><strong>用户名:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + (cert.cwstaffname || '--') + '</p></div>';
                    html += '  </div>';
                    html += '  <div class="row">';
                    html += '    <div class="col-xs-6"><p><strong>身份证号:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + (cert.cwcode || '--') + '</p></div>';
                    html += '  </div>';
                    html += '  <div class="row">';
                    html += '    <div class="col-xs-6"><p><strong>电话:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + (cert.cwphone || '--') + '</p></div>';
                    html += '  </div>';
                    html += '  <div class="row">';
                    html += '    <div class="col-xs-6"><p><strong>证件:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + (cert.cwcredenname || '--') + '</p></div>';
                    html += '  </div>';
                    html += '  <div class="row">';
                    html += '    <div class="col-xs-6"><p><strong>地址:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + (cert.ddaddress || '--') + '</p></div>';
                    html += '  </div>';
                    /*html += '  <div class="row">';
                    html += '    <div class="col-xs-6"><p><strong>审核状态:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + (cert.auditorType || '--') + '</p></div>';
                    html += '  </div>';
                    html += '  <div class="row">';
                    html += '    <div class="col-xs-6"><p><strong>审核人:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + ('--') + '</p></div>';
                    html += '  </div>';*/
                    html += '  <div class="row">';

                    $('#certificate-detail-content').html(html);
                    $('#btn-edit-certificate').data('id', id);
                    $('#btn-delete-certificate').data('id', id);
                    $('#certificateDetailModal').modal('show');
                } else {
                    $('#certificate-detail-content').html('<div class="alert alert-danger">加载证件详情失败</div>');
                }
            },
            error: function () {
                $('#certificate-detail-content').html('<div class="alert alert-danger">加载失败，请稍后重试</div>');
            }
        });
    }

    // 编辑工种按钮点击事件
    /*$(document).on('click', '#btn-edit-certificate', function () {
        var id = $(this).data('id');
        $('#certificateDetailModal').modal('hide');
        $('#certificate-form')[0].reset();
        $('#certificate-form').validate().resetForm();
        $.ajax({
            url: basePath + '/ea/cp/sajax_ea_view.jspa',
            type: 'GET',
            data: {
                'staffCertificate.credentialskey': id
            },
            dataType: 'json',
            success: function (data) {
                var member = eval("(" + data + ")");
                if (member && member.cert) {
                    var cert = member.cert;
                    $('#credentialskey').val(cert.credentialskey);
                    $('#companyID').val(cert.companyID);
                    $('#credentialsID').val(cert.credentialsID);
                    $('#staffID').val(cert.staffID);
                    $('#credentialsName').val(cert.credentialsName);//证件名称
                    $('#credentialsType').val(cert.credentialsType);//证件类型
                    $('#credentialsNo').val(cert.credentialsNo);//证件编号
                    $('#invalidateStart').val(formatDateForInput(cert.invalidateStart));//有效起时间
                    $('#invalidateEnd').val(formatDateForInput(cert.invalidateEnd));//有效止时间
                    $('#recordsNumber').val(cert.recordsNumber);//档案编号
                    $('#address').val(cert.address);//住址
                    $('#organ').val(cert.organ);//发证机关（单位）
                    $('#credentialsrecordNo').val(cert.credentialsrecordNo);//证件资料文号
                    $('#appendixNumber').val(cert.appendixNumber);//附件编号
                    $('#photos').val(cert.photo);//附件
                    $('#credentialsDesc').val(cert.credentialsDesc); //备注

                    $('#edit-modal-title').text('编辑证件');
                    $('#certificateEditModal').modal('show');
                }
            }
        });
    });*/

    // 删除工种按钮点击事件
    $(document).on('click', '#btn-delete-certificate', function () {
        var id = $(this).data('id')
        showConfirm('确认删除', '确定要删除这条工种认证信息吗？', function () {
            $.ajax({
                url: basePath + 'ea/dserve/sajax_ea_delWtype.jspa',
                type: 'POST',
                data: {
                    cwtid: id,
                },
                dataType: 'json',
                success: function (data) {
                    var member = eval("(" + data + ")");
                    if (member != null && member.flag) {
                        showAlert('success', '成功', '删除成功！');
                        $('#certificateDetailModal').modal('hide');
                        currentPage = 1;
                        hasMoreData = true;
                        $('#certificate-list').empty();
                        $('#load-more-container').html('<div class="load-more" id="load-more-btn">加载更多</div>');
                        loadCertificateList(1);
                    } else {
                        //alert('删除失败: ' + (member.message || '未知错误'));

                        showAlert('error', '失败', '删除失败: ' + (member.message || '未知错误'));
                    }
                },
                error: function () {
                    showAlert('error', '失败', '删除失败，请稍后重试');
                }
            });
        });
    });

    // 保存工种
    function saveCertificate() {
        //var photoFile = $('#photos')[0].files[0];

        /*$("#certificate-form").find('input').each(function () {
            $(this).attr("name","staffCertificate." + this.name);
            /!*if ($(this).attr("type")=="file"){
                $(this).val(photoFile);
            }*!/
        });
        $('#credentialsType').attr("name","staffCertificate.credentialsType");*/
        var formData = new FormData();
        formData.append('tcw.cwkey', $('#cwkey').val());
        formData.append('tcw.cwid', $('#cwid').val());
        formData.append('tcw.cwstaffid', $('#cwstaffid').val());
        formData.append('tcw.cwcustid', $('#cwcustid').val());
        formData.append('tcw.cwstaffname', $('#staffName').val());
        /*formData.append('tcw.cwcode', $('#cwcode').val());*/
        formData.append('tcw.cwvalue', $('#cwvalue').val());
        formData.append('tcw.cwscodeid', $('#cwscodeid').val());
        formData.append('tcw.cwcredentialsID', $('#cwcredentialsID').val());
        formData.append('tcw.cwcredenname', $('#cwcredentialsVal').val());
        formData.append('tcw.ddaddress', $('#ddaddress').val());
        formData.append('tcw.coordinate', $('#coordinate').val());
        formData.append('tcw.cwphone', $('#cwphone').val());

        $.ajax({
            url: basePath + '/ea/dserve/sajax_ea_saveWtype.jspa',
            type: 'POST',
            data: formData,
            dataType: 'json',
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            beforeSend: function () {
                $('#btn-save-certificate').prop('disabled', true).html('<span class="glyphicon glyphicon-refresh glyphicon-spin"></span> 保存中...');
            },
            complete: function () {
                $('#btn-save-certificate').prop('disabled', false).text('保存');
            },
            success: function (data) {
                var member = eval("(" + data + ")");
                if (member && member.flag) {
                    /*if (member.id) {
                        localStorage.setItem("title", "证件添加");//标题
                        localStorage.setItem("source", "证件管理");//来源
                        localStorage.setItem("htmlUrl", "ea/cp/ea_viewToPage.jspa?staffCertificate.credentialskey=" + member.id);//编译页面url
                        localStorage.setItem("tableName", "dt_hr_staff_Certificate");//表名
                        localStorage.setItem("idName", "credentialskey");//表id
                        localStorage.setItem("idValue", member.id);//id值
                        localStorage.setItem("stateName", "state");//状态名称
                        localStorage.setItem("stateValue", "00");//通过状态
                        localStorage.setItem("refundValue", "04");//失败状态
                        window.location.href = basePath + "page/ea/main/office_ea/contract/selectCompany.jsp?typee=L";

                    }*/
                    showAlert('success', '成功', '保存成功');
                    $('#certificateEditModal').modal('hide');
                    currentPage = 1;
                    hasMoreData = true;
                    $('#certificate-list').empty();
                    $('#load-more-container').html('<div class="load-more" id="load-more-btn">加载更多</div>');


                    loadCertificateList(1);
                } else {
                    showAlert('error', '失败', '保存失败: ' + (member.message || '未知错误'));
                }
            },
            error: function () {
                showAlert('error', '失败', '保存失败，请稍后重试');
            }
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

    function getcer() {
        $.ajax({
            url: basePath + '/ea/cp/sajax_ea_lists.jspa',
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                var member = eval("(" + data + ")");
                if (member) {
                    if (member.list) {
                        if (member.list && member.list.length > 0) {
                            var html = '';
                            var list = member.list;
                                for (var i = 0; i < list.length; i++) {
                                    // 保留前6位和后4位，中间用*代替
                                    let t = list[i];
                                    let credentialsNo = t.credentialsNo.substring(0, 6) + '*'.repeat(Math.max(0, t.credentialsNo.length - 10)) + t.credentialsNo.substring(t.credentialsNo.length - 4);

                                    html += '<div class="document-item" onclick="selectdic();">';
                                    html += '  <input type="hidden" class="cwcredentialsID" value="' + t.credentialsID + '"/>';
                                    html += '  <div class="doc-type">' + t.credentialsName + ' · ' + t.credentialsType + '</div>';
                                    html += '  <div class="doc-info">';//姓名
                                    html += '  <span>'+member.staffname+'</span>';//身份证号
                                    html += '  <span>' + credentialsNo + '</span>';//证件编号
                                    html += '  <span>' + formatDate(t.invalidateStart) + ' 至 ' + formatDate(t.invalidateEnd) + '</span>';//地址
                                    html += '</div>';
                                    $('.documents-list').html(html);
                                }
                            }else {
                            showConfirm('没有证件', '还没有添加证件信息，快去添加证件吧', function () {
                                $("#iframe-").attr("src", basePath + "/page/ea/main/marketing/certificate/certificate_list.jsp?originPage=win-gz&staffid=" + staffID);

                                $(".nest_hd").hide();
                                $(".nest_bd").css("margin", "0").css("margin-top", "0");
                                $(".nest_page").show();
                                $(".overlay_text").hide();
                            });
                        }
                    }
                }
                isLoading = false;
            }
        });
    }

</script>
</body>
</html>
