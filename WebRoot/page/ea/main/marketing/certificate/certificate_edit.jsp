<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no"/>
    <title>证件信息展示</title>
    <style>
        body {
            font-family: "Microsoft YaHei", SimSun, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #ffffff;
        }

        .document {
            width: 100%;
            min-height: 100%;
            margin: 0 auto;
            padding: 40px;
            background-color: white;
            box-sizing: border-box;
        }

        h1 {
            text-align: center;
            margin-bottom: 30px;
            color: #333333;
            border-bottom: 2px solid #333333;
            padding-bottom: 10px;
            font-size: 24pt;
            page-break-after: avoid;
        }

        .info-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
            page-break-inside: avoid;
        }

        .info-table td {
            padding: 8px 12px;
            border: 1px solid #dddddd;
            vertical-align: top;
        }

        .info-table td:first-child {
            width: 150px;
            background-color: #f9f9f9;
            font-weight: bold;
        }

        .section {
            margin-bottom: 25px;
            page-break-inside: avoid;
        }

        .section-title {
            font-weight: bold;
            font-size: 16pt;
            margin-bottom: 10px;
            color: #333333;
            border-left: 4px solid #333333;
            padding-left: 10px;
        }

        .footer {
            margin-top: 30px;
            text-align: right;
            font-size: 12pt;
            color: #666666;
        }

        /* Simplified attachment styles for PDF */
        .attachments {
            margin-top: 15px;
        }

        .attachment-item {
            margin-bottom: 10px;
            padding: 10px;
            border: 1px solid #eeeeee;
            background-color: #fafafa;
        }

        .attachment-preview img {
            max-width: 200px;
            max-height: 200px;
            border: 1px solid #eeeeee;
        }
    </style>
</head>
<body>
<div class="document">
    <h1>证件信息</h1>

    <div class="section">
        <div class="section-title">基本信息</div>
        <table class="info-table">
            <tr>
                <td>用户</td>
                <td id="staffName">${staff.staffName}</td>
            </tr>
            <tr>
                <td>证件名称</td>
                <td id="credentialsName">${staffCertificate.credentialsName}</td>
            </tr>
            <tr>
                <td>证件类型</td>
                <td id="credentialsType">${staffCertificate.credentialsType}</td>
            </tr>
            <tr>
                <td>有效期限</td>
                <td id="invalidateRange">
                    <fmt:formatDate value="${staffCertificate.invalidateStart}" pattern="yyyy-MM-dd"/>
                    至
                    <fmt:formatDate value="${staffCertificate.invalidateEnd}" pattern="yyyy-MM-dd"/>
                </td>
            </tr>
            <tr>
                <td>证件编号</td>
                <td id="credentialsNo">${staffCertificate.credentialsNo}</td>
            </tr>
            <tr>
                <td>档案编号</td>
                <td id="recordsNumber">${staffCertificate.recordsNumber}</td>
            </tr>
            <tr>
                <td>住址</td>
                <td id="address">${staffCertificate.address}</td>
            </tr>
        </table>
    </div>

    <div class="section">
        <div class="section-title">发证信息</div>
        <table class="info-table">
            <tr>
                <td>发证机关(单位)</td>
                <td id="organ">${staffCertificate.organ}</td>
            </tr>
            <tr>
                <td>证件资料文号</td>
                <td id="credentialsrecordNo">${staffCertificate.credentialsrecordNo}</td>
            </tr>
            <tr>
                <td>附件编号</td>
                <td id="appendixNumber">${staffCertificate.appendixNumber}</td>
            </tr>
        </table>
    </div>

    <div class="section">
        <div class="section-title">其他信息</div>
        <table class="info-table">
            <tr>
                <td>备注</td>
                <td id="credentialsDesc">${staffCertificate.credentialsDesc}</td>
            </tr>
        </table>
    </div>

    <!-- 新增附件展示区域 -->
    <div class="section">
        <div class="section-title">附件信息</div>
        <div id="attachments" class="attachments">
            <!-- 附件将通过JavaScript动态添加 -->
            <s:if test="staffCertificate.photo!=null&& staffCertificate.photo!=''">
                <s:if test="staffCertificate.photo.split('.')=='jpg'||staffCertificate.photo.split('.')=='png'">
                    <div>
                        <img src="${staffCertificate.photo}" alt=""/>
                    </div>
                </s:if>
            </s:if>
        </div>
    </div>

    <%--<div class="footer">
        <p>生成时间: <span id="currentTime"></span></p>
    </div>--%>
</div>
</body>
</html>
