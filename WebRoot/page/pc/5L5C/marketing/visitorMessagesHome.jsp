<%@page import="java.lang.String"%>
<%@page contentType="text/html;charset=UTF-8" session="false"%>
<%
  String contextPath = request.getServletContext().getContextPath();
  String urlPrefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath;
  request.setAttribute("urlPrefix", urlPrefix);
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://"
          + request.getServerName() + ":" + request.getServerPort()
          + path + "/";
%>
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

  <script src="<%=basePath %>js/scMobile/payBudget/budgetList/jquery-2.1.1.min.js"></script>
  <link href="${urlPrefix}/css/pc/5L5C/marketing/base.css" rel="stylesheet" type="text/css">
  <link href="${urlPrefix}/css/pc/5L5C/marketing/common.css" rel="stylesheet" type="text/css">
  <link href="${urlPrefix}/css/pc/5L5C/marketing/branchPage.css" rel="stylesheet" type="text/css">

  <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
  <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/crmCustomerPo1.css">

  <script src="${urlPrefix}/js/pc/5L5C/marketing/axios.min.js" type="module"></script>
  <script src="${urlPrefix}/js/pc/5L5C/marketing/petite-vue.es.js" type="module"></script>
<%--  <script src="${urlPrefix}/js/pc/5L5C/marketing/branch/visitorMessagesHome.es.js" type="module"></script>--%>
  <script src="${urlPrefix}/js/pc/5L5C/marketing/branch/visitorMessagesHome.es1.js" type="module"></script>
  <title>标准模板</title>
</head>

<body>

<article class="layout-flex-column mobile-screen" >
  <%@ include file="./template/layout-header.jsp" %>
  <main class="page-visitor-messages layout-flex-content" id="main">
    <div class="messages-above">
      <div class="search-box">
        <div class="before">
          <div class="icon-svg">
            <svg viewBox="0 0 1024 1024" width="80" height="80">
              <path d="M863.03994311 814.77632a470.51935289 470.51935289 0 0 0 96.03208534-175.43259022c12.582912-42.96248889 18.96174933-87.67260445 18.96174933-132.907008 0-258.76525511-209.07440355-469.26688711-466.03377778-469.26688711S45.96622222 247.67146667 45.96622222 506.43672178c0 258.736128 209.07440355 469.26688711 466.03377778 469.26688711a463.41233778 463.41233778 0 0 0 135.35368533-20.09770667 43.69066667 43.69066667 0 1 0-25.48622222-83.56568177c-35.47682133 10.80615822-72.43912533 16.31118222-109.86746311 16.31118222-208.78313245 0-378.65244445-171.32566755-378.65244445-381.88555378 0-210.58901333 169.869312-381.91468089 378.65244445-381.91468089s378.65244445 171.32566755 378.65244445 381.88555378a382.61373155 382.61373155 0 0 1-115.22685156 274.29000533c-0.81555911 0.81555911-1.10683022 1.89326222-1.86413511 2.73794844-1.19421155 0.99032178-2.65056711 1.45635555-3.72827023 2.62144a43.60328533 43.60328533 0 0 0 1.39810134 61.74947556l132.93613511 127.05245867c8.44686222 8.09733689 19.31127467 12.11687822 30.17568711 12.11687822 11.534336 0 23.01041778-4.51470222 31.57378844-13.51497955a43.60328533 43.60328533 0 0 0-1.39810133-61.74947556l-101.47885511-96.96415289z"></path>
              <path d="M790.396928 592.53646222a29.12711111 29.12711111 0 0 0 33.93308445-23.330816c6.00018489-32.41847467 8.94202311-62.15725511 3.84477866-101.77012622a28.95234845 28.95234845 0 0 0-32.62236444-25.165824 29.12711111 29.12711111 0 0 0-25.165824 32.62236445c4.16517689 32.18545778 1.80588089 55.89492622-3.32049067 83.74044444a29.06885689 29.06885689 0 0 0 23.330816 33.90395733zM781.80443022 397.55958045a29.12711111 29.12711111 0 0 0 12.20425956-39.35072712A317.95154489 317.95154489 0 0 0 512 187.93176178c-70.69149867 0-137.65472711 22.69001955-193.60790755 65.56512711a29.12711111 29.12711111 0 0 0 35.4185671 46.25385244A257.74580622 257.74580622 0 0 1 512 246.185984a259.81383111 259.81383111 0 0 1 230.45370311 139.198464 29.12711111 29.12711111 0 0 0 39.35072711 12.17513245z"></path>
            </svg>
          </div>
        </div>
        <div class="target">
          <label>
            <input type="text" placeholder="通过模板名称进行搜索">
          </label>
        </div>
      </div>
    </div>
    <div class="messages-content layout-flex-content">
      <ul class="desc-ul layout-flex-row-center" style="display:none;">
        <li class="desc-minor text-limited">免费短信：----</li>
        <li class="desc-minor text-limited">积分余额：----</li>
      </ul>
      <ul class="template-preview">
        <li v-for="item of smsList">
          <div class="header">
            <div class="caption text-limited">{{item.templateHeadline}}</div>
            <div class="menu-delete" @click="eventDeleteTemplate($event,item)">
              <svg viewBox="0 0 1024 1024" width="80" height="80">
                <path d="M332.19610283 692.92417138L332.19610283 692.92417138c14.28348701 14.28348701 37.24909454 14.28348701 51.53258154 0l128.55138418-128.55138418 128.55138418 128.55138418c14.28348701 14.28348701 37.24909454 14.28348701 51.53258154 0l0 0c14.28348701-14.28348701 14.28348701-37.24909454 0-51.53258155L563.8126501 512.84020565l128.55138417-128.55138418c14.28348701-14.28348701 14.28348701-37.24909454 0-51.53258154l0 0c-14.28348701-14.28348701-37.24909454-14.28348701-51.53258154 0l-128.55138418 128.55138418L383.72868323 332.75623879c-14.28348701-14.28348701-37.24909454-14.28348701-51.53258154 0s-14.28348701 37.24909454 0 51.53258155l128.55138418 128.55138417-128.55138418 128.55138418C317.91261582 655.67507683 317.91261582 678.64068323 332.19610283 692.92417138z"></path>
                <path d="M512.28006855 3.81593145c-191.00663239 0-357.50728249 105.16567495-444.468513 260.88369266l0.14003428 0.14003427c-2.80068323 5.18126478-4.34106027 10.92266667-4.34106026 17.22420565 0 20.16492317 16.24396573 36.40888889 36.40888888 36.4088889 14.70358983 0 27.30666667-8.68211939 33.04806856-21.14516196 75.05832505-131.77217138 216.77292317-220.55384633 379.21258154-220.55384633 240.85880377 0 436.20649529 195.34769266 436.20649529 436.20649529S753.13887232 949.04670094 512.28006855 949.04670094c-239.59849529 0-433.96594915-193.10714539-436.20649529-432.14550471l-0.14003427 0c-1.12027307-19.18468323-16.9441371-34.30837589-36.40888889-34.30837589-20.16492317 0-36.40888889 16.24396573-36.40888889 36.40888889 0 0.98023879 0 2.10051299 0.14003428 3.08075178l0 0c4.90119623 276.84758983 230.91637589 499.92205085 508.88423878 499.92205085 281.1886501 0 509.02427307-227.83562411 509.02427307-509.02427307S793.46871751 3.81593145 512.28006855 3.81593145z"></path>
              </svg>
            </div>
          </div>
          <div class="main">
            <div class="about text-limited">{{item.templateText}}</div>
          </div>
          <div class="tail layout-flex-row-lr">
            <div>{{item.templateTypeName}}</div>
            <div>{{item.updatedAt}}</div>
          </div>
        </li>
      </ul>
    </div>
    <div class="messages-below">
      <div class="button-base button-border-round button-confirm" @click="eventRouterPush($event)">添加模板</div>
    </div>
  </main>
  <%@ include file="./template/layout-footer.jsp" %>
</article>

</body>
</html>