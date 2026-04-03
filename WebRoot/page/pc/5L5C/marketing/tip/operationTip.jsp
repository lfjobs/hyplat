<%@ page import="java.lang.String" %>
<%@ page contentType="text/html;charset=UTF-8" session="false" %>
<%
    String contextPath = request.getServletContext().getContextPath();
    String urlPrefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath;
    request.setAttribute("urlPrefix", urlPrefix);
%>
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

    <script src="${urlPrefix}/js/pc/5L5C/marketing/petite-vue.es.js" type="module"></script>
    <script src="${urlPrefix}/js/pc/5L5C/marketing/store.es.js" type="module"></script>
    <title>标准模板</title>
</head>

<body>
<article class="layout-flex-column mobile-screen" >
    <main class="page-operation-tip layout-flex-content" id="main">
        <section id="vessel" class="above-tip-section">

        </section>
        <section class="below-tip-section">
            <a class="button-base button-border-square button-confirm" href="${urlPrefix}/page/pc/5L5C/marketing/visitorMessages.jsp">返回短信模板首页</a>
        </section>
    </main>
    <%@ include file="/page/pc/5L5C/marketing/template/layout-footer.jsp" %>
</article>

<template id="succeed">
    <div class="icon-area">
        <div class="icon-svg">
            <svg viewBox="0 0 1024 1024" width="80" height="80">
                <path d="M512 64a448 448 0 1 1 0 896 448 448 0 0 1 0-896m-55.808 536.384-99.52-99.584a38.4 38.4 0 1 0-54.336 54.336l126.72 126.72a38.272 38.272 0 0 0 54.336 0l262.4-262.464a38.4 38.4 0 1 0-54.272-54.336z"></path>
            </svg>
        </div>
    </div>
    <h2>操作成功</h2>
    <p>----</p>
</template>

<script type="module">
    import {getUrlParam} from "/js/pc/5L5C/marketing/stdlib.js";

    let elMian = document.getElementById("main");
    let elVessel = document.getElementById("vessel");
    let elTempSucceed = document.getElementById("succeed")

    // 读取模板中的内容
    const clone = elTempSucceed.content.cloneNode(true);
    clone.querySelector("p").textContent = getUrlParam("message");

    // 将模板中的元素放入#vessel元素中
    elVessel.append(clone)
</script>

</body>
</html>