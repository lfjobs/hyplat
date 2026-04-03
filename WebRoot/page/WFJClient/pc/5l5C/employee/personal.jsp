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
    <title>新增员工</title>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/selectCompany.css?version=1">
    <style>
        body {
            font-family: Arial, sans-serif; /* 设置字体 */
            text-align: center; /* 将文本居中 */
        }

        form {
            display: inline-block; /* 使表单内容居中 */
            text-align: left; /* 表单内文本居左 */
            border: 10px solid #ccc; /* 添加边框 */
            padding: 20px; /* 添加内边距 */
            border-radius: 10px; /* 添加圆角 */
            background-color: #f9f9f9; /* 设置背景颜色 */
            max-width: 400px; /* 设置最大宽度 */
            margin: 20px auto; /* 居中显示表单 */
        }

        input[type="text"],
        input[type="date"],
        input[type="tel"] {
            width: 150px; /* 让输入框占据整个宽度，减去边框和内边距的宽度 */
            padding: 10px; /* 添加内边距 */
            margin-bottom: 10px; /* 添加垂直间距 */
            border: 1px solid #ccc; /* 添加边框 */
            border-radius: 5px; /* 添加圆角 */
        }

        select {
            width: 40%; /* 让下拉框占据整个宽度 */
            padding: 10px; /* 添加内边距 */
            margin-bottom: 10px; /* 添加垂直间距 */
            border: 1px solid #ccc; /* 添加边框 */
            border-radius: 5px; /* 添加圆角 */
        }

        input[type="submit"] {
            background-color: #007bff; /* 设置按钮背景颜色 */
            color: #fff; /* 设置按钮文字颜色 */
            border: none; /* 移除按钮边框 */
            padding: 10px 20px; /* 添加内边距 */
            font-size: 16px; /* 设置字体大小 */
            border-radius: 5px; /* 添加圆角 */
            cursor: pointer; /* 添加手型光标 */
        }

        input[type="submit"]:hover {
            background-color: #0056b3; /* 鼠标悬停时改变按钮背景颜色 */
        }

        .order_head li {
            height: 30px;
            width: 8.83rem;
            float: left; /* 设置高度为40像素，根据您的需求调整值 */
        }

        hr {
            border-style: dashed;
            border-color: gray;
        }

    </style>
    <script type="text/javascript">
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
    </script>
</head>

<body>
<div class="pc-box">
    <div class="div-box">
        <header>
            <ul class="clearfix">
                <li>
                    <a onclick="javascript: window.history.go(-1);return false;" target="_self">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
                    </a>
                </li>
                <li>人员基本信息</li>
            </ul>
        </header>
    </div>
</div>

<div id="personalInformation">
    <form id="myForm" method="post"> <!-- 提交表单的目标页面为processForm.jsp -->
            姓名：<input type="text" name="name" required/><br/>
            身份证：<input type="text" name="staffIdentityCard" required/><br/>
            出生日期：<input type="date" name="birthDate" required/><br/>
            性别：
            <select name="gender" required>
                <option value="男">男</option>
                <option value="女">女</option>
            </select>
            <br/>
            民族：
            <select name="ethnicity" required>
                <option value="汉族">汉族</option>
                <option value="其他">其他</option>
            </select>
            <br/>
            联系方式：<input type="tel" name="contact" required/><br/>
            QQ号：<input type="text" name="qq" required/><br/>
            人员多级分类：
            <select name="classification" required>
                <option value="level1">一般人物</option>
                <option value="level2">政界人物</option>
                <option value="level3">商界人物</option>
                <option value="level4">学术界人物</option>
                <option value="level5">艺术界人物</option>
                <option value="level6">科学界人物</option>
            </select>
            <br/>
            人员往来关系：
            <select name="relationship" required>
                <option value="relationship1">员工</option>
                <option value="relationship2">合作</option>
                <option value="relationship3">企业合作创业商城会员</option>
                <option value="relationship4">微分金商城业主会员</option>
                <option value="relationship5">代理商商城业主会员</option>
                <option value="relationship6">个人客户</option>
                <option value="relationship7">个人粉丝</option>
                <option value="relationship8">其他</option>
                <option value="relationship9">学员</option>
                <option value="relationship10">供应商</option>
                <option value="relationship11">教练</option>
                <option value="relationship12">联合联盟研讨会会员</option>
                <option value="relationship13">主管</option>
                <option value="relationship14">客服</option>
            </select>
            <br/>
            信息类别：
            <select name="infoCategory" required>
                <option value="yes">确定人员信息</option>
                <option value="no">非确定人员信息</option>
            </select>
            <br/>
        <input type="hidden" name="staffid" id="staffidField" value="#{staffID}"/><br/>
        <input type="hidden" name="companyid" id="companyidField"/><br/>
        <input id = "submitBtn" type="submit" value="提交"/>
    </form>

</div>
<script>
    document.getElementById('staffidField').value = staffID;
    document.getElementById('companyidField').value = companyID;
</script>

<script>
    $("#myForm").submit(function(event) {

        event.preventDefault();  // 阻止表单默认提交行为

        var formData = $(this).serialize();  // 序列化表单数据

        $.ajax({
            type: 'POST',
            url: '<%=basePath%>ea/cosincumbent/sajax_n_ea_saveCStaff.jspa',
            data: formData,
            success: function(response) {
                window.location.href="<%=basePath%>page/WFJClient/pc/5l5C/employee/rzManage.jsp?companyID="+companyID+"&staffId="+staffID
            },
            error: function(xhr, status, error) {

            }

        });
    });
</script>

<%--
<script>
        // 当表单提交按钮被点击时
        // 获取表单元素和提交按钮
        var form = document.getElementById("myForm");

        // 添加点击事件监听器
        $("#submitBtn").click(function() {
            var formData = $("#myForm").serialize();  // 序列化表单数据

            // 阻止表单默认的提交行为

            // 发送AJAX请求
            $.ajax({
                type: 'POST',  // 或 'GET'，根据您的需要
                url: <%=basePath%> + "/ea/mobilecstaff/sajax_ea_saveCStaff.jspa", // 提交到的目标URL
                data: formData,
                success: function(response) {
                    // 处理成功响应
                    alert('表单提交成功！服务器返回的数据：' + response);
                    // 这里可以添加处理成功后的逻辑
                },
                error: function(xhr, status, error) {
                    // 处理错误响应
                    alert('表单提交失败。错误信息：' + error);
                    // 这里可以添加处理错误后的逻辑
                }
            });
        });
</script>
--%>

</body>


</html>
