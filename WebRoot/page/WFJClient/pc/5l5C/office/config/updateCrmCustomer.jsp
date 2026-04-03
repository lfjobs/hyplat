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
    <title>未注册粉丝修改</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/crmCustomerPo.css">
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/crmCustomerPo.js"></script>
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
            粉丝详情
        </li>
        <li style="font-size: 1rem; display: none ;" class="edit">
            修改
        </li>
    </ul>
</header>
<div class="content up">
    <section class="sec-list">
        <div class="div-sec-data">
            <div class="container">
                <div class="dataPo">
                    <s:form id="updateCrmCustomerPO" action="sajax_ea_updateData.jspa" namespace="/ea/crmCustomerPO"
                            method="POST">
                        <div class="div-title-name"><label>基本信息</label></div>
                        <div class="div-name" style="display: none">
                            <label>id</label>
                            <input type="text" id="id" name="crmCustomerPO.id"
                                   value="${crmCustomerPO.id}"/>
                        </div>
                        <div class="div-name">
                            <label><span style="color:red">*</span>姓名</label>
                            <input type="text" placeholder="请输入姓名" id="userName" name="crmCustomerPO.userName"
                                   value="${crmCustomerPO.userName}"/>
                        </div>
                        <div class="div-name">
                            <label><span style="color:red">*</span>电话</label>
                            <input type="text" placeholder="请输入手机号" id="contactInfo"
                                   name="crmCustomerPO.contactInfo"
                                   value="${crmCustomerPO.contactInfo}"/>
                        </div>
                        <div class="div-name">
                            <label><span style="color:red">*</span>身份证</label>
                            <input type="text" placeholder="请输入身份证号码" id="cardNumber"
                                   name="crmCustomerPO.cardNumber"
                                   value="${crmCustomerPO.cardNumber}"/>
                        </div>
                        <div class="div-name">
                            <label>地址</label>
                            <input type="text" placeholder="请输入居住地址" id="residentialAddress"
                                   name="crmCustomerPO.residentialAddress"
                                   value="${crmCustomerPO.residentialAddress}"/>
                        </div>
                        <div class="div-name">
                            <label>备注</label>
                            <input type="text" placeholder="请填写备注" id="extendInfo" name="crmCustomerPO.extendInfo"
                                   value="${crmCustomerPO.extendInfo}"/>
                        </div>
                        <div class="div-name">
                            <label>社会身份</label>
                            <input type="text" placeholder="请输入身份" id="socialStatus"
                                   name="crmCustomerPO.socialStatus"
                                   value="${crmCustomerPO.socialStatus}"/>
                        </div>
                        <div class="div-name">
                            <label>所在单位</label>
                            <input type="text" placeholder="请输入所在单位" id="unitCompany"
                                   name="crmCustomerPO.unitCompany"
                                   value="${crmCustomerPO.unitCompany}"/>
                        </div>
                        <div class="div-name">
                            <label>是否电话联系</label>
                            <input type="text" placeholder="无" id="byPhone"
                                   name="crmCustomerPO.byPhone"
                                   value="${crmCustomerPO.byPhone}" disabled/>
                        </div>
                        <div class="div-name">
                            <label>是否发送短信</label>
                            <input type="text" placeholder="无" id="sendMessage"
                                   name="crmCustomerPO.sendMessage"
                                   value="${crmCustomerPO.sendMessage}" disabled/>
                        </div>
                        <div class="div-name">
                            <label>粉丝意见</label>
                            <input type="text" placeholder="无" id="staffOpinions"
                                   name="crmCustomerPO.staffOpinions"
                                   value="${crmCustomerPO.staffOpinions}" disabled/>
                        </div>
                        <div class="div-name">
                            <label>是否意向客户</label>
                            <input type="text" placeholder="无" id="interestedParties"
                                   name="crmCustomerPO.interestedParties"
                                   value="${crmCustomerPO.interestedParties}" disabled/>
                        </div>
                        <div class="div-name">
                            <label>是否托管</label>
                            <input type="text" placeholder="无" id="isCustody"
                                   name="crmCustomerPO.isCustody"
                                   value="${crmCustomerPO.isCustody}" disabled/>
                        </div>
                        <div class="div-name">
                            <label>托管三方账号</label>
                            <input type="text" placeholder="无" id="custodyAccount"
                                   name="crmCustomerPO.custodyAccount"
                                   value="${crmCustomerPO.custodyAccount}" disabled/>
                        </div>
                        <div class="div-name">
                            <label>托管三方账号密码</label>
                            <input type="text" placeholder="无" id="custodyAccountPwd"
                                   name="crmCustomerPO.custodyAccountPwd"
                                   value="${crmCustomerPO.custodyAccountPwd}" disabled/>
                        </div>
                        <div class="div-name">
                            <label>三方平台是否发视频</label>
                            <input type="text" placeholder="无" id="isSend"
                                   name="crmCustomerPO.isSend"
                                   value="${crmCustomerPO.isSend}" disabled/>
                        </div>

                        <%--隐藏--%>
                        <div class="div-name" style="display: none">
                            <label>社会身份</label>
                            <input type="text" placeholder="请输入身份" id="importerMode"
                                   name="crmCustomerPO.importerMode"
                                   value="${crmCustomerPO.importerMode}"/>
                        </div>
                        <div class="div-name" style="display: none">
                            <label>社会身份</label>
                            <input type="text" placeholder="请输入身份" id="importerId" name="crmCustomerPO.importerId"
                                   value="${crmCustomerPO.importerId}"/>
                        </div>
                        <div class="div-name" style="display: none">
                            <label>社会身份</label>
                            <input type="text" placeholder="请输入身份" id="memberLevel"
                                   name="crmCustomerPO.memberLevel"
                                   value="${crmCustomerPO.memberLevel}"/>
                        </div>
                        <div class="div-name" style="display: none">
                            <label>社会身份</label>
                            <input type="text" placeholder="请输入身份" id="createdAt" name="crmCustomerPO.createdAt"
                                   value="${crmCustomerPO.createdAt}"/>
                        </div>
                        <div class="div-name" style="display: none">
                            <label>社会身份</label>
                            <input type="text" placeholder="请输入身份" id="cardType" name="crmCustomerPO.cardType"
                                   value="${crmCustomerPO.cardType}"/>
                        </div>

                    </s:form>
                </div>

            </div>
        </div>
    </section>

</div>
<div style="display: none;background-color: #f74c32;gap: 80px;border-radius: 40px;" class="inputBut">
    <%--                            <input type=submit value="确认修改">--%>
    <div class="updateData" onclick="updateData1()">确认修改</div>
    <div class="updateData"><input style="background: #f74c32;" type="reset" value="数据重置"></div>
</div>
<script>
    function updateData1() {
        var form = document.getElementById('updateCrmCustomerPO');
        var formData = new FormData(form);
        fetch(form.action, {
            method: 'POST',
            body: formData
        }).then(response => {
            if (response.ok) {
                // 提交成功后可以刷新页面或显示成功消息
                // var refreshUrl = "ea/crmCustomerPO/ea_crmCustomerPOList.jspa";
                // window.location.href = basePath + refreshUrl;
                window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/crmCustomer1.jsp";
            } else {
                layer.msg("修改失败，请重试");
            }
        })
            .catch(error => {
                console.error('Error:', error);
                layer.msg("修改失败，请重试");
            });
    }

    $(document).ready(function () {
        $(document).on("click", ".edit", function (event) {
            var $button = $(this); // 缓存按钮引用

            $(".inputBut").slideToggle(300, function () {
                // 动画完成后执行回调
                if ($(this).is(":visible")) {
                    $button.text("取消修改");
                } else {
                    $button.text("修改");
                }
            });
        });

        // 重置按钮状态（可选）
        function resetEditButton() {
            $(".inputBut").hide();
            $(".edit").text("修改");
        }
    });
</script>
</body>
</html>
