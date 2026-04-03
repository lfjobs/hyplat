<%@ page contentType="text/html;charset=UTF-8" session="false" %>
<%@ page import="com.tiantai.wfj.bo.TEshopCustomer" %>
<%@ page import="com.tiantai.wfj.util.SessionWrap" %>
<%
    HttpSession session = request.getSession(false);

    if (session != null) {
        // 添加用户的 KEY 到 cookie 中
        TEshopCustomer cus = (TEshopCustomer) session.getAttribute(SessionWrap.KEY_CUSTOMER);
        Cookie cookie = new Cookie(SessionWrap.KEY_CUSTOMER, cus.getCustkey());
        response.addCookie(cookie);
    }
    else {
        //设置状态码 response.SC_MOVED_TEMPORARILY = 302
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);

        // 没有记录 session 则需要用户重新登录
        response.setHeader("Location", "/ea/earth/ea_earthIndex.jspa?login=login");
    }
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

    <script src="${urlPrefix}/js/pc/5L5C/marketing/axios.min.js" type="module"></script>
    <script src="${urlPrefix}/js/pc/5L5C/marketing/petite-vue.es.js" type="module"></script>
    <script src="${urlPrefix}/js/pc/5L5C/marketing/store.es.js" type="module"></script>
    <script src="${urlPrefix}/js/pc/5L5C/marketing/branch/visitorHome.es.js" type="module"></script>
    <title>标准模板</title>
</head>

<body>
<article class="layout-flex-column mobile-screen">
    <%@ include file="./template/layout-header.jsp" %>
    <main class="page-visitor-home layout-flex-content" id="main">
        <div class="single-row-nav" v-for="item in page.singleRowNavs" :key="item.text"
             @click="event.singleRowNavAction($event,item.to)">
            <div class="row-left">
                <span class="text">{{item.text}}</span>
            </div>
            <div class="row-right">
                <div class="icon-svg">
                    <svg viewBox="0 0 1024 1024" width="80" height="80">
                        <path d="M686.98625029 473.4125c-74.54999971-125.02500029-149.175-249.975-223.80000029-375.00000029A74.54999971 74.54999971 0 0 0 360.73625029 72.6875a75.18750029 75.18750029 0 0 0-25.57500029 102.89999971c66.89999971 112.12499971 133.875 224.25000029 200.85000029 336.375L335.16125 848.41250029a75.22499971 75.22499971 0 0 0 25.57500029 102.89999971c35.325 21.30000029 81.15000029 9.74999971 102.44999971-25.72499971 74.62500029-125.02500029 149.24999971-249.975 223.80000029-375.00000029 16.68750029-27.9 16.68750029-49.275 0-77.175z"></path>
                    </svg>
                </div>
            </div>
        </div>
    </main>
    <%@ include file="./template/layout-footer.jsp" %>
</article>
</body>
</html>