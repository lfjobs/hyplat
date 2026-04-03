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
    <title>模版修改</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/crmCustomerPo1.css">
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
            未注册粉丝-模版修改
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content">
    <s:form id="updateTemplate" action="sajax_ea_updateTemplate.jspa" namespace="/ea/cRMShortMessagingTemplatePO"
            method="POST">
        <section class="sec-nav sec-hide1 ">
            <div class="select-container">
                <input style="display: none" type="text"
                       value="${crmShortMessagingTemplatePO.id}"
                       name="crmShortMessagingTemplatePO.id" placeholder="请在这里输入短信模板标题">
                <select required name="crmShortMessagingTemplatePO.templateType" id="templateType">
                    <option value="" disabled selected>请选择模板分类</option>
                    <option value="通知模版" ${crmShortMessagingTemplatePO.templateType=="通知模版"?"selected":""}>
                        通知模版
                    </option>
                    <option value="营销模版" ${crmShortMessagingTemplatePO.templateType=="营销模版"?"selected":""}>
                        营销模版
                    </option>
                </select>
            </div>
        </section>
        <div class="input-box1">
            <input class="input-11" type="text" value="${crmShortMessagingTemplatePO.templateHeadline}"
                   name="crmShortMessagingTemplatePO.templateHeadline" placeholder="请在这里输入短信模板标题">
        </div>

        <section class="sec-list">
            <div class="textarea-container">
    <textarea
            name="crmShortMessagingTemplatePO.templateText"
            placeholder="请在这里添加短信模板内容"
            maxlength="200"
            minlength="2"
            required
    >${crmShortMessagingTemplatePO.templateText}</textarea>
                <div class="char-counter"><span>200</span>/200</div>
            </div>
        </section>
        <div class="input-box1">
            <input class="input-11" type="text" value="${crmShortMessagingTemplatePO.money}"
                   name="crmShortMessagingTemplatePO.money" placeholder="请在这里输入发送短信所需积分数">
        </div>
    </s:form>
    <ul class="ul1">
        <li>@公司名称</li>
        <li>@公司地址</li>
        <li>@手机号</li>
    </ul>
    <div class="tips-box">
        <h4 style="font-weight: bold;
    font-size: 16px;">温馨提示：</h4>
        <ol style="font-size: 15.6px;">
            <li style="color: #565656;
    margin-top: 4px;
    margin-bottom: 4px;">
                每个短信模板都需审核后才能使用，请确保短信内容不设计黄赌毒以及时政等等敏感信息。详细信息可以阅读<a style="color: #3498db;
    text-decoration: none;"
                                                                                                                 href="${urlPrefix}/page/pc/5L5C/marketing/document/norm.jsp">《网络信息安全告知书》</a>
            </li>
            <li style="color: #565656;
    margin-top: 4px;
    margin-bottom: 4px;">
                所有短信群发需要进行签名报备后发送，短信签名是指短信内容里中括号里的内容【签名】，如：【工商银行】你的验证码为：52545，5分钟内有效。【工商银行】就是短信签名。
            </li>
        </ol>
    </div>
    <div class="below">
        <div class="save_btn" onclick="updateData1()">保存模板</div>
    </div>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const textarea = document.querySelector('.textarea-container textarea');
        const counter = document.querySelector('.textarea-container .char-counter span');

        // 初始化计数器
        updateCounter();

        // 监听输入事件以更新计数器
        textarea.addEventListener('input', updateCounter);

        function updateCounter() {
            const textLength = textarea.value.length;
            counter.textContent = textLength;
        }
    });

    function updateData1() {
        var form = document.getElementById('updateTemplate');
        var formData = new FormData(form);
        fetch(form.action, {
            method: 'POST',
            body: formData
        }).then(response => {
            if (response.ok) {
                // 提交成功后可以刷新页面或显示成功消息
                // var refreshUrl = "ea/crmCustomerPO/ea_crmCustomerPOList.jspa";
                // window.location.href = basePath + refreshUrl;
                window.location.href = basePath + "page/pc/5L5C/marketing/visitorMessagesHome1.jsp";
            } else {
                layer.msg("修改失败，请重试");
            }
        })
            .catch(error => {
                console.error('Error:', error);
                layer.msg("修改失败，请重试");
            });
    }
</script>
</body>
</html>