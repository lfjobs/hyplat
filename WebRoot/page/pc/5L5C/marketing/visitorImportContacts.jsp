<%@page import="java.lang.String" %>
<%@page contentType="text/html;charset=UTF-8" session="false" %>
<%
    String contextPath = request.getServletContext().getContextPath();
    String urlPrefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath;
    request.setAttribute("urlPrefix", urlPrefix);
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<script src="<%=basePath %>js/scMobile/payBudget/budgetList/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var companyID = "${param.companyID}";
    var staffID = "${param.staffID}";
</script>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1,shrink-to-fit=no">
    <link rel="icon" href="${urlPrefix}/images/icon/png/favicon-32x32.png" type="image/png">
    <link rel="apple-touch-icon" sizes="48x48" href="${urlPrefix}/images/icon/png/icon-48x48.png">
    <link rel="apple-touch-icon" sizes="72x72" href="${urlPrefix}/images/icon/png/icon-72x72.png">

    <link href="${urlPrefix}/css/pc/5L5C/marketing/base.css" rel="stylesheet" type="text/css">
    <link href="${urlPrefix}/css/pc/5L5C/marketing/common.css" rel="stylesheet" type="text/css">
    <link href="${urlPrefix}/css/pc/5L5C/marketing/branchPage.css" rel="stylesheet" type="text/css">

    <script src="${urlPrefix}/js/pc/5L5C/marketing/axios.min.js" type="module"></script>
    <script src="${urlPrefix}/js/pc/5L5C/marketing/petite-vue.es.js" type="module"></script>
    <script src="${urlPrefix}/js/pc/5L5C/marketing/xlsx.es.js" type="module"></script>
    <script src="${urlPrefix}/js/pc/5L5C/marketing/store.es.js" type="module"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="${urlPrefix}/js/pc/5L5C/marketing/branch/visitorImportContacts.es.js" type="module"></script>

    <title>标准模板</title>
</head>

<body>

<article class="layout-flex-column mobile-screen" >
    <%@ include file="./template/layout-header.jsp" %>
    <main class="page-visitor-import layout-flex-content" id="main" @mounted="onMounted()">
        <section class="upload-file-box" v-if="showUploadExcel" >
            <div class="main-icon-svg">
                <svg viewBox="0 0 1068 1024" xmlns="http://www.w3.org/2000/svg" width="80" height="80">
                    <path d="M73.813513 1023.554804S0 1035.129902 0 936.029251V89.800505S1.647226 0.494168 91.843955 0.494168H416.703549s39.355335-8.280647 72.166288 42.961423a2652.47836 2652.47836 0 0 1 49.238688 81.02569s11.44154 13.222324 37.70811 13.222324h418.306255s73.813513-8.280647 73.813513 74.347749v728.830534s11.486059 82.672916-67.224611 82.672916H73.768994z m857.892884-672.691306a36.50608 36.50608 0 0 0-36.060884-36.283482H173.893596a36.906757 36.906757 0 0 0-37.752629 36.328001v3.294452c0 21.458452 16.427736 37.975227 37.752629 37.975227h721.751917c19.677668 0 36.060884-16.516775 36.060884-37.975227v-3.294452z m-216.409824 278.959876l-171.400498-171.489538a34.413658 34.413658 0 0 0-48.526375 0l-171.400499 171.489538a34.2801 34.2801 0 0 0 48.526375 48.481855l112.901731-112.946251v294.452701a34.2801 34.2801 0 1 0 68.560199 0v-294.452701l112.857212 112.946251a34.2801 34.2801 0 1 0 48.481855-48.526375z" ></path>
                </svg>
            </div>
            <div class="hint-text">
                <p class="text-center">“数据”批量上传</p>
                <p class="text-center">请使用指定的 Excel 模板文件上传</p>
            </div>
            <div class="action-button">
                <label class="uploader-click">
                    <span>上传文件</span>
                    <input class="hide-element" type="file" name="uploader" @change="eventReaderFile"/>
                </label>
            </div>
        </section>
    </main>
    <%@ include file="./template/layout-footer.jsp" %>
</article>
<template id="template">
    <section class="component-excel-table-root" v-if="showExcelTable">
        <div class="table-box" ref="tableBox">
            <table class="table-nowrap table-warm-theme">
                <thead>
                <tr v-for="row in table.sheetAt.header">
                    <th></th>
                    <th>序号</th>
                    <th v-for="th in row">{{th}}</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(row,index) in table.sheetAt.body">
                    <th>
                        <label>
                            <input type="checkbox" :value="index" v-model="table.sheetAt.check[index]">
                        </label>
                    </th>
                    <th>{{index+1}}</th>
                    <td v-for="td in row">{{td}}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="button-box">
            <div class="button-item cancel-button" @click="eventButtonTable('cancel')">取消提交</div>
            <div class="button-item confirm-button" @click="eventButtonTable('confirm')">确定提交</div>
        </div>
    </section>

    <section v-if="showExcelHint">
        <div>数据上传中请耐心等待...</div>
        <div>数据上传完成后会自动转跳...</div>
        <div>数据上传时间计时：{{uploadTimer}}秒</div>
        <div v-html="requestMessage"></div>
    </section>
</template>
</body>
</html>