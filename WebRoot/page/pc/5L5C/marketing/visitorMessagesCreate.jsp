<%@ page import="java.lang.String" %>
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
    <script src="<%=basePath %>js/scMobile/payBudget/budgetList/jquery-2.1.1.min.js"></script>
    <script src="${urlPrefix}/js/pc/5L5C/marketing/store.es.js" type="module"></script>
    <script src="${urlPrefix}/js/pc/5L5C/marketing/branch/visitorMessagesCreate.es.js" type="module"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/crmCustomerPo1.css">
    <title>标准模板</title>
</head>

<body>

<article class="layout-flex-column mobile-screen">
    <%@ include file="./template/layout-header.jsp" %>
    <main class="page-create-messages-template layout-flex-content" id="main">
        <div class="above">
            <div class="drop-down-select-container" id="dropDownSelectContainer">
                <div class="display-header layout-flex-row-lr" @click="eventAnimationSwitch">
                    <div class="add-context">
                        <span v-if="optionIndex === -1" class="placeholder">请选择模板分类</span>
                        <span v-else class="text-select">{{optionValue}}</span>
                    </div>
                    <div class="icon-svg" rel="el">
                        <svg width="40" height="40" viewBox="0 0 1024 1024">
                            <path d="M317.44 972.8c-20.48 0-30.72-10.24-40.96-20.48-20.48-20.48-20.48-61.44 0-81.92L624.64 512l-358.4-358.4c-20.48-20.48-20.48-61.44 0-81.92s61.44-20.48 81.92 0l399.36 399.36c20.48 20.48 20.48 61.44 0 81.92L358.4 952.32c-10.24 10.24-30.72 20.48-40.96 20.48"></path>
                        </svg>
                    </div>
                </div>
                <div class="display-select">
                    <div class="select-options">
                        <div v-for="(item,index) in options" class="option" @click="eventSelectOption(index)">
                            {{item.name}}
                        </div>
                    </div>
                </div>
                <div v-if="selectSwitch" class="door-closer" @click="eventAnimationSwitch()"></div>
            </div>

            <div class="text-title-box">
                <label>
                    <input type="text" name="title" placeholder="请在这里输入短信模板标题" v-model="formHeadline"
                           maxlength="32"/>
                </label>
            </div>

            <div class="text-field-box">
                <label>
                    <textarea @input="eventTextareaChange" class="textarea" name="comments"
                              placeholder="请在这里添加短信模板内容" maxlength="200" minlength="2" required
                              v-model="formMessage"></textarea>
                </label>
                <p class="word-count">{{formMessageCount}}/{{formMessageMaxLength}}</p>
            </div>

            <div class="text-title-box">
                <label>
                    <input required type="text" name="money" placeholder="请在这里输入发送短信所需的积分数" v-model="formMoney"
                           maxlength="32"/>
                </label>
            </div>


            <ul class="text-fill-box layout-flex-row">
                <li>@公司名称</li>
                <li>@公司地址</li>
                <li>@手机号</li>
            </ul>
            <div class="tips-box">
                <h4>温馨提示：</h4>
                <ol>
                    <li>每个短信模板都需审核后才能使用，请确保短信内容不设计黄赌毒以及时政等等敏感信息。详细信息可以阅读<a
                            href="${urlPrefix}/page/pc/5L5C/marketing/document/norm.jsp">《网络信息安全告知书》</a></li>
                    <li>
                        所有短信群发需要进行签名报备后发送，短信签名是指短信内容里中括号里的内容【签名】，如：【工商银行】你的验证码为：52545，5分钟内有效。【工商银行】就是短信签名。
                    </li>
                </ol>
            </div>
        </div>
        <div class="below">
            <div class="button-base button-border-round button-confirm" @click="eventFormConfirm">保存模板</div>
        </div>
    </main>
    <%@ include file="./template/layout-footer.jsp" %>
    <%@ include file="./template/layout-alert.jsp" %>
</article>

</body>
</html>