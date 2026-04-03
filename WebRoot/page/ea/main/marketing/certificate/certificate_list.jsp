<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/"; %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>证件管理</title>
    <%--<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css">--%>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap.css">
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

        .navbar-toggle {
            border-color: var(--white);
        }

        .navbar-toggle .icon-bar {
            background-color: var(--white);
        }

        .btn-primary {
            background-color: var(--primary-color);
            border-color: var(--primary-dark);
        }

        .btn-primary:hover, .btn-primary:focus {
            background-color: var(--primary-dark);
            border-color: var(--primary-dark);
        }

        .footer {
            position: fixed;
            bottom: 0;
            width: 100%;
            height: 50px;
            background-color: var(--primary-color);
            color: var(--white);
            text-align: center;
            line-height: 50px;
            z-index: 1000;
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
            background-color: var(--primary-color);
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

        .search-box {
            padding: 10px;
            background-color: var(--white);
            margin-bottom: 15px;
            border-radius: 4px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
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

        /* 文件上传按钮样式 */
        .file-upload-wrapper {
            position: relative;
            overflow: hidden;
            display: inline-block;
            width: 100%;
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

        .file-upload-btn {
            display: block;
            padding: 8px 12px;
            background: #f5f5f5;
            border: 1px solid #ddd;
            border-radius: 4px;
            text-align: center;
            color: #666;
        }
    </style>
</head>
<body>
<!-- 顶部导航栏 -->
<nav class="navbar navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header" style="width: 100%;">
            <!-- 返回按钮 -->
            <button type="button" class="navbar-back btn btn-link"
                    style="position: absolute; left: 15px; top: 0; color: white; padding: 15px;">
                <span class="glyphicon glyphicon-chevron-left"></span>
            </button>
            <!-- 居中的标题 -->
            <a class="navbar-brand" href="#" style="text-align: center; display: block;">证件管理</a>
        </div>
    </div>
</nav>

<!-- 主内容区 -->
<div class="container">
    <!-- 搜索区域 -->
    <div class="search-box">
        <div class="row">
            <div class="col-xs-6">
                <div class="form-group">
                    <select class="form-control credentialsType" id="docType"></select>
                </div>
            </div>
            <div class="col-xs-6">
                <div class="form-group">
                    <input type="text" class="form-control credentialsType" id="docNumber" placeholder="证件编号">
                </div>
            </div>
        </div>
        <button type="button" class="btn btn-primary btn-block" id="btn-search">搜索</button>
    </div>

    <!-- 证件列表 -->
    <div class="table-responsive">
        <table class="excel-table" id="certificate-table">
            <thead>
            <tr>
                <th>序号</th>
                <th>证件名称</th>
                <th>证件类型</th>
                <th>有效起时间</th>
                <th>有效止时间</th>
                <th>证件编号</th>
                <th>档案编号</th>
                <th>住址</th>
                <th>发证机关（单位）</th>
                <th>证件资料文号</th>
                <th>附件编号</th>
                <th>备注</th>
                <th>审核状态</th>
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

<!-- 证件详情模态框 -->
<div class="modal fade" id="certificateDetailModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header" style="background-color: #e74c3c; color: white;">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">证件详情</h4>
            </div>
            <div class="modal-body" id="certificate-detail-content">
                <!-- 动态加载的证件详情内容 -->
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="btn-edit-certificate">编辑</button>
                <button type="button" class="btn btn-danger" id="btn-delete-certificate">删除</button>
            </div>
        </div>
    </div>
</div>

<!-- 添加/编辑证件模态框 -->
<div class="modal fade" id="certificateEditModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header" style="background-color: #e74c3c; color: white;">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="edit-modal-title">新增证件</h4>
            </div>
            <div class="modal-body">
                <form id="certificate-form">
                    <input type="hidden" id="credentialskey" name="credentialskey">
                    <input type="hidden" id="credentialsID" name="credentialsID">
                    <input type="hidden" id="staffID" name="staffID">
                    <input type="hidden" id="companyID" name="companyID">

                    <!-- 证件名称 -->
                    <div class="form-group">
                        <div class="row">
                            <label for="credentialsName" class="col-xs-3 control-label">证件名称</label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="credentialsName" name="credentialsName"
                                       required>
                            </div>
                        </div>
                    </div>

                    <!-- 证件类型 -->
                    <div class="form-group">
                        <div class="row">
                            <label for="credentialsType" class="col-xs-3 control-label">证件类型</label>
                            <div class="col-xs-9">
                                <select class="form-control" id="credentialsType" name="credentialsType" required></select>
                            </div>
                        </div>
                    </div>

                    <!-- 证件编号 -->
                    <div class="form-group">
                        <div class="row">
                            <label for="credentialsNo" class="col-xs-3 control-label">证件编号</label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="credentialsNo" name="credentialsNo"
                                       required>
                            </div>
                        </div>
                    </div>

                    <!-- 有效时间 -->
                    <div class="form-group">
                        <div class="row">
                            <label for="invalidateStart" class="col-xs-3 control-label">
                                有效起时间
                                <span class="glyphicon glyphicon-calendar"></span>
                            </label>
                            <div class="col-xs-9">
                                <input type="date" class="form-control" id="invalidateStart" name="invalidateStart">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <label for="invalidateEnd" class="col-xs-3 control-label">
                                有效止时间
                                <span class="glyphicon glyphicon-calendar"></span>
                            </label>
                            <div class="col-xs-9">
                                <input type="date" class="form-control" id="invalidateEnd" name="invalidateEnd">
                            </div>
                        </div>
                    </div>

                    <!-- 档案编号 -->
                    <div class="form-group">
                        <div class="row">
                            <label for="recordsNumber" class="col-xs-3 control-label">档案编号</label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="recordsNumber" name="recordsNumber">
                            </div>
                        </div>
                    </div>

                    <!-- 住址 -->
                    <div class="form-group">
                        <div class="row">
                            <label for="address" class="col-xs-3 control-label">住址</label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="address" name="address">
                            </div>
                        </div>
                    </div>

                    <!-- 证件资料文号 -->
                    <div class="form-group">
                        <div class="row">
                            <label for="credentialsrecordNo" class="col-xs-3 control-label">证件资料文号</label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="credentialsrecordNo" name="credentialsrecordNo">
                            </div>
                        </div>
                    </div>

                    <!-- 附件编号 -->
                    <div class="form-group">
                        <div class="row">
                            <label for="appendixNumber" class="col-xs-3 control-label">附件编号</label>
                            <div class="col-xs-9">
                                <input type="text" class="form-control" id="appendixNumber" name="appendixNumber">
                            </div>
                        </div>
                    </div>

                    <!-- 备注 -->
                    <div class="form-group">
                        <div class="row">
                            <label for="credentialsDesc" class="col-xs-3 control-label">备注</label>
                            <div class="col-xs-9">
                                    <textarea class="form-control" id="credentialsDesc" name="credentialsDesc"
                                              rows="3"></textarea>
                            </div>
                        </div>
                    </div>

                    <!-- 附件 -->
                    <div class="form-group">
                        <div class="row">
                            <label for="photos" class="col-xs-3 control-label">附件</label>
                            <div class="col-xs-9">
                                <input name="photos" id="photos" type="file" contentEditable="false" size="10"/>
                                <%--<div class="file-upload-wrapper">
                                    <div class="file-upload-btn">
                                        <span class="glyphicon glyphicon-upload"></span> 选择文件
                                    </div>
                                    <input name="photos" id="photos" type="file" contentEditable="false" size="10"/>
                                </div>
                                <p class="help-block">请上传证件照片 (JPEG/PNG格式)</p>--%>
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

<!-- 加载必要的JS库 -->
<%--<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.3/dist/jquery.validate.min.js"></script>--%>
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
    let basePath = "<%=basePath%>";
    let staffID = "${param.staffid}";

    $(document).ready(function () {

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
            $('#edit-modal-title').text('新增证件');
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

        // 保存证件按钮点击事件
        $('#btn-save-certificate').click(function() {
            if ($('#certificate-form').valid()) {
                saveCertificate();
            }
        });

        // 表单验证
        $('#certificate-form').validate({
            min:30,
            rules: {
                credentialsName: "required",
                credentialsType: "required",
                credentialsNo: "required",
                /*photos: {
                    extension: "jpg|jpeg|png"
                }*/
            },
            messages: {
                credentialsName: "请输入证件名称",
                credentialsType: "请选择证件类型",
                credentialsNo: "请输入证件编号",
                /*photos:  "请上传有效的文件(JPG/JPEG/PNG/WORD/EXCEL)"*/
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

    });

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

        var credentialsType = $('.credentialsType').val();
        var credentialsNo = $('.credentialsNo').val();

        isLoading = true;
        if (!isAppend) {
            $('#certificate-list').html('<tr><td colspan="6" class="loading-indicator"><span class="glyphicon glyphicon-refresh glyphicon-spin"></span> 加载中...</td></tr>');
        } else {
            $('#load-more-btn').html('<span class="glyphicon glyphicon-refresh glyphicon-spin"></span> 加载中...').addClass('loading-more');
        }

        $.ajax({
            url: basePath + '/ea/cp/sajax_ea_list.jspa',
            type: 'GET',
            data: {
                credentialsType: credentialsType,
                credentialsNo: credentialsNo,
                pageNumber: pageNumber || 1
            },
            dataType: 'json',
            success: function (data) {
                var member = eval("(" + data + ")");
                if (member) {
                    if (member.credentialsTypelist){
                        let openthtml='<option value="">请选择证件类型</option>';
                        for (var i = 0; i < member.credentialsTypelist.length; i++) {
                            openthtml+='<option value="'+member.credentialsTypelist[i].codeValue+'">'+member.credentialsTypelist[i].codeValue+'</option>';
                        }
                        $('select').html(openthtml);
                    }
                    if (member.pageForm){
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
            var cert = list[i];
            html += '<tr data-id="' + cert.credentialskey + '">';
            html += '  <td>' + ((pageNumber - 1) * 15 + i + 1) + '</td>';
            html += '  <td>' + (cert.credentialsName || '--') + '</td>';//证件名称
            html += '  <td>' + (cert.credentialsType || '--') + '</td>';//证件类型
            html += '  <td>' + formatDate(cert.invalidateStart) + '</td>';//有效起时间
            html += '  <td>' + formatDate(cert.invalidateEnd) + '</td>';//有效止时间
            html += '  <td>' + (cert.credentialsNo || '--') + '</td>';//证件编号
            html += '  <td>' + (cert.recordsNumber || '--') + '</td>';//档案编号
            html += '  <td>' + (cert.address || '--') + '</td>';//住址
            html += '  <td>' + (cert.organ || '--') + '</td>';//发证机关（单位）
            html += '  <td>' + (cert.credentialsrecordNo || '--') + '</td>';//证件资料文号
            html += '  <td>' + (cert.appendixNumber || '--') + '</td>';//附件编号
            html += '  <td>' + (cert.credentialsDesc || '--') + '</td>';//备注
            html += '  <td>' + (cert.auditorType || '--') + '</td>';//审核状态
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
            url: basePath + '/ea/cp/sajax_ea_view.jspa',
            type: 'GET',
            data: {
                'staffCertificate.credentialskey': id
            },
            dataType: 'json',
            beforeSend: function () {
                $('#certificate-detail-content').html('<div class="text-center" style="padding: 20px;"><span class="glyphicon glyphicon-refresh glyphicon-spin"></span> 加载中...</div>');
            },
            success: function (data) {
                var member = eval("(" + data + ")");
                if (member && member.cert) {
                    let cert = member.cert;
                    let staff=member.staff;
                    var html = '<div class="certificate-detail">';
                    html += '  <h4 style="color: #e74c3c;">' + cert.credentialsName + ' <small>' + cert.credentialsType + '</small></h4>';
                    html += '  <div class="row">';
                    html += '    <div class="col-xs-6"><p><strong>用户名:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + (staff.staffName || '--') + '</p></div>';
                    html += '  </div>';
                    html += '  <div class="row">';
                    html += '    <div class="col-xs-6"><p><strong>证件编号:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + (cert.credentialsNo || '--') + '</p></div>';
                    html += '  </div>';
                    html += '  <div class="row">';
                    html += '    <div class="col-xs-6"><p><strong>有效期起:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + formatDate(cert.invalidateStart) + '</p></div>';
                    html += '  </div>';
                    html += '  <div class="row">';
                    html += '    <div class="col-xs-6"><p><strong>有效期止:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + formatDate(cert.invalidateEnd) + '</p></div>';
                    html += '  </div>';
                    html += '  <div class="row">';
                    html += '    <div class="col-xs-6"><p><strong>档案编号:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + (cert.recordsNumber || '--') + '</p></div>';
                    html += '  </div>';
                    html += '  <div class="row">';
                    html += '    <div class="col-xs-6"><p><strong>住址:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + (cert.address || '--') + '</p></div>';
                    html += '  </div>';
                    html += '  <div class="row">';
                    html += '    <div class="col-xs-6"><p><strong>发证机关（单位）:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + (cert.organ || '--') + '</p></div>';
                    html += '  </div>';
                    html += '  <div class="row">';
                    html += '    <div class="col-xs-6"><p><strong>证件资料文号:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + (cert.credentialsrecordNo || '--') + '</p></div>';
                    html += '  </div>';
                    html += '  <div class="row">';
                    html += '    <div class="col-xs-6"><p><strong>附件编号:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + (cert.appendixNumber || '--') + '</p></div>';
                    html += '  </div>';
                    html += '    <div class="col-xs-6"><p><strong>附件:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + (cert.appendix || '--') + '</p></div>';
                    html += '  </div>';
                    html += '  <div class="row">';
                    html += '    <div class="col-xs-6"><p><strong>备注:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + (cert.credentialsDesc || '--') + '</p></div>';
                    html += '  </div>';
                    html += '</div>';
                    html += '  <div class="row">';
                    html += '    <div class="col-xs-6"><p><strong>审核状态:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + (cert.auditorType || '--') + '</p></div>';
                    html += '  </div>';
                    html += '  <div class="row">';
                    html += '    <div class="col-xs-6"><p><strong>审核人:</strong></p></div>';
                    html += '    <div class="col-xs-6"><p>' + ('--') + '</p></div>';
                    html += '  </div>';
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

    // 编辑证件按钮点击事件
    $(document).on('click', '#btn-edit-certificate', function () {
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
    });

    // 删除证件按钮点击事件
    $(document).on('click', '#btn-delete-certificate', function () {
        var id = $(this).data('id')
        showConfirm('确认删除', '确定要删除这条证件信息吗？', function() {
            $.ajax({
                url: basePath + '/ea/cp/sajax_ea_delete.jspa',
                type: 'POST',
                data: {
                    'staffCertificate.credentialsID': id
                },
                dataType: 'json',
                success: function (data) {
                    var member = eval("(" + data + ")");
                    if (member!=null && member.success) {
                        showAlert('success', '成功','删除成功！');
                        $('#certificateDetailModal').modal('hide');
                        currentPage = 1;
                        hasMoreData = true;
                        $('#certificate-list').empty();
                        $('#load-more-container').html('<div class="load-more" id="load-more-btn">加载更多</div>');
                        loadCertificateList(1);
                    } else {
                        //alert('删除失败: ' + (member.message || '未知错误'));

                        showAlert('error','失败','删除失败: ' + (member.message || '未知错误'));
                    }
                },
                error: function () {
                    showAlert('error','失败','删除失败，请稍后重试');
                }
            });
        });
    });

    // 保存证件
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
        formData.append('staffCertificate.credentialskey', $('#credentialskey').val());
        formData.append('staffCertificate.credentialsID', $('#credentialsID').val());
        formData.append('staffCertificate.staffID', $('#staffID').val());
        formData.append('staffCertificate.companyID', $('#companyID').val());
        formData.append('staffCertificate.credentialsName', $('#credentialsName').val());
        formData.append('staffCertificate.credentialsType', $('#credentialsType').val());
        formData.append('staffCertificate.credentialsNo', $('#credentialsNo').val());
        formData.append('staffCertificate.invalidateStart', $('#invalidateStart').val());
        formData.append('staffCertificate.invalidateEnd', $('#invalidateEnd').val());
        formData.append('staffCertificate.recordsNumber', $('#recordsNumber').val());
        formData.append('staffCertificate.address', $('#address').val());
        formData.append('staffCertificate.organ', $('#organ').val());
        formData.append('staffCertificate.credentialsDesc', $('#credentialsDesc').val());

        var photoFile = $('#photos')[0].files[0];
        if (photoFile) {
            formData.append('staffCertificate.photos', photoFile);
        }

        $.ajax({
            url: basePath + 'ea/cp/sajax_ea_add.jspa',
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
                if (member && member.success) {
                    /*if (member.id){
                        localStorage.setItem("title", "证件添加");//标题
                        localStorage.setItem("source", "证件管理");//来源
                        localStorage.setItem("htmlUrl", "ea/cp/ea_viewToPage.jspa?staffCertificate.credentialskey="+member.id);//编译页面url
                        localStorage.setItem("tableName", "dt_hr_staff_Certificate");//表名
                        localStorage.setItem("idName", "credentialskey");//表id
                        localStorage.setItem("idValue", member.id);//id值
                        localStorage.setItem("stateName", "state");//状态名称
                        localStorage.setItem("stateValue", "00");//通过状态
                        localStorage.setItem("refundValue", "04");//失败状态
                        window.location.href = basePath + "page/ea/main/office_ea/contract/selectCompany.jsp?typee=L";

                    }*/
                    showAlert('success', '成功','保存成功');
                    $('#certificateEditModal').modal('hide');
                    currentPage = 1;
                    hasMoreData = true;
                    $('#certificate-list').empty();
                    $('#load-more-container').html('<div class="load-more" id="load-more-btn">加载更多</div>');



                    loadCertificateList(1);
                } else {
                    showAlert('error','失败','保存失败: ' + (member.message || '未知错误'));
                }
            },
            error: function () {
                showAlert('error','失败','保存失败，请稍后重试');
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

</script>
</body>
</html>
