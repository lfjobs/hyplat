<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String cashierBillsID = request.getParameter("cashierBillsID");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>人员分配</title>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <style>
        body,html {
            -moz-user-select: none;
            -khtml-user-select: none;
            user-select: none;
            -webkit-overflow-scrolling: touch
        }

        body {
            margin: 0 auto;
            background-color: #fff;
            width: 100%;
            font-family: Arial, sans-serif; /* 设置字体 */
            text-align: center; /* 将文本居中 */
        }

        body,button,dd,div,dl,dt,form,h1,h2,h3,h4,h5,h6,html,iframe,input,label,li,menu,option,p,section,select,table,td,textarea,th,ul
        {
            margin: 0 0;
            padding: 0 0;
            font-family: '微软雅黑'
        }

        img {
            cursor: pointer;
            border: none;
            vertical-align: middle
        }

        li,ol,ul {
            list-style: none
        }

        input {
            outline: 0;
            /* -webkit-appearance: none;*/ /* 单选框小圆圈问题*/
            -webkit-tap-highlight-color: transparent
        }

        ::-webkit-scrollbar {
            width: 0
        }

        .clearfix:after,.clearfix:before {
            content: "";
            display: table
        }

        .clearfix:after {
            clear: both;
            overflow: hidden
        }

        .clearfix {
            zoom: 1
        }

        .pc-box {
            min-width: 100%;
            width: 100%;
            margin: 0 auto
        }

        .pc-box .div-box header {
            background-color: #f74c31
        }

        .pc-box .div-box header ul {
            padding: 0 20px
        }

        .pc-box .div-box header ul li {
            float: left;
            height: 3.4rem;
            line-height: 3.4rem;
            font-size: 1.2rem;
            color: #fff
        }

        .pc-box .div-box header ul li:nth-of-type(1) {
            width: 20%
        }

        .pc-box .div-box header ul li:nth-of-type(1) img {
            width: 0.8rem
        }

        .pc-box .div-box header ul li:nth-of-type(2) {
            text-align: center;
            width: 60%
        }

        .pc-box .div-box header ul li:nth-of-type(3) {
            width: 20%;
            float: right
        }

        .pc-box .div-box .content .div-nav {
            border-bottom: 1px solid #eee
        }

        .pc-box .div-box .content .div-nav ul li {
            text-align: center;
            float: left;
            width: 171px;
            height: 88px;
            line-height: 88px;
            font-size: 24px;
            color: #666
        }

        .pc-box .div-box .content .div-nav ul li img {
            width: 16px;
            margin-bottom: 5px
        }

        .pc-box .div-box .content .div-nav ul li:nth-of-type(2) {
            width: 408px
        }

        .pc-box .div-box .content .div-con ul li {
            padding-left: 1rem;
            border-bottom: 1px solid #eee
        }
        .pc-box .div-box .content .div-con ul li img {
            padding-top: 1rem;
            float: left;
            padding-right: 1rem
        }

        .pc-box .div-box .content .div-con ul li div {
            width: 95%
        }

        .pc-box .div-box .content .div-con ul li div .p-top {
            padding-top: 0.6rem;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            height: 2.5rem;
            line-height: 2.5rem;
            font-size: 1rem;
            color: #222
        }
        .pc-box .div-box .content .div-con ul li div .p-bottom {
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            height: 2rem;
            line-height: 2rem;
            font-size: 0.8rem;
            color: #999;
            padding-bottom: 0.5rem;
        }


        .pc-box .div-box .content .div-con ul li div ul li {
            margin-right: 24px;
            float: left;
            padding: 0 15px;
            background-color: #eee;
            border-radius: 6px;
            height: 44px;
            line-height: 44px;
            font-size: 24px;
            color: #999
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
            padding: 5px 10px; /* 添加内边距 */
            font-size: 16px; /* 设置字体大小 */
            border-radius: 5px; /* 添加圆角 */
            cursor: pointer; /* 添加手型光标 */
        }

        input[type="submit"]:hover {
            background-color: #0056b3; /* 鼠标悬停时改变按钮背景颜色 */
        }

        /* 隐藏员工类别和职务级别 */
        #employeeCategoryDiv {
            display: none;
        }

    </style>

    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var depPostID = "${param.depPostID}";
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
    <form method="post" id="myForm"> <!-- 提交表单的目标页面为processForm.jsp -->
        选择人员：
        <select name="person" required></select><br/>
        岗位类型:
        <label>
            <input type="radio"  name="jobType" value="part-time" checked> <span></span>
            兼岗
        </label>
        <label>
            <input type="radio"  name="jobType" value="full-time"> <span></span>
            专岗
        </label>
        <br/>
        <div id="employeeCategoryDiv">
            员工类别:
            <select name="personType" required></select>
            <br/>
            职务级别:
            <select name="jobLevel" required></select>
            <br/>
        </div>
        <input type="submit" value="确认分配"/>
    </form>
</div>


<script>
    $(document).ready(function () {
        // 发送AJAX请求获取数据并填充下拉框
        var url = "ea/cosincumbent/sajax_n_ea_getStaffForCashierNew.jspa?";
        $.ajax({
            url: basePath + url, // 替换为你的后端接口URL
            method: 'POST',
            success: function (data) {
                // 成功获取数据后，填充人员选择下拉框
                var select = $('select[name="person"]');
                select.empty();
                var m = eval("(" + data + ")");
                var arry = m.pageForm;
                for (var i = 0; i < arry.list.length; i++) {
                    select.append($('<option></option>')
                        .attr('value', arry.list[i].staffID)
                        .text(arry.list[i].staffName));
                }

            },
            error: function (error) {
                console.error("请求失败！！！");
            }
        });
        // 当岗位类型发生变化时
        $('input[name="jobType"]').change(function () {
            if ($(this).val() === 'full-time') {
                // 如果选择了专岗，显示员工类别和职务级别
                $('#employeeCategoryDiv').show();
                // 发送AJAX请求获取数据并填充下拉框
                $.ajax({
                    url: basePath +'ea/cosincumbent/sajax_n_ea_getBillID.jspa?', // 替换为你的后端接口URL
                    method: 'POST',
                    success: function (data) {
                        // 填充员工类别下拉框
                        var personTypeSelect = $('select[name="personType"]');
                        personTypeSelect.empty(); // 清空之前的选项

                        var m = eval("(" + data + ")");
                        var arry = m.staffTypeList;
                        for (var i = 0; i < arry.length; i++) {
                            personTypeSelect.append($('<option></option>')
                                .attr('value', arry[i].codeValue+"_"+arry[i].codePID)
                                .text(arry[i].codeValue));
                        }
                    },
                    error: function (error) {
                        console.error("请求失败！！！");
                    }
                });
                $.ajax({
                    url: basePath +'ea/cosincumbent/sajax_n_ea_getStaffListForPost.jspa?', // 替换为你的后端接口URL
                    method: 'POST',
                    success: function (data) {
                        // 填充职务级别下拉框
                        var jobLevelSelect = $('select[name="jobLevel"]');
                        jobLevelSelect.empty(); // 清空之前的选项
                        var m = eval("(" + data + ")");
                        var arry = m.paylist;
                        for (var i = 0; i < arry.length; i++) {
                            jobLevelSelect.append($('<option></option>')
                                .attr('value', arry[i].scale+"_"+arry[i].payScaleID)
                                .text(arry[i].position));

                        }
                    },
                    error: function (error) {
                        console.error("请求失败！！！");
                    }
                });
            } else {
                // 否则，隐藏员工类别和职务级别
                $('#employeeCategoryDiv').hide();
            }
        });

        // 当表单提交时
        $('#myForm').submit(function (event) {
            event.preventDefault(); // 阻止表单的默认提交行为
            // 将表单数据封装成 JSON 对象
             var jobLevel=$('select[name="jobLevel"]').val();
             var personType = $('select[name="personType"]').val();
            // 发送 JSON 数据到后端
            $.ajax({
                url: basePath +'ea/cosincumbent/sajax_n_ea_orgPostEntry.jspa?', // 替换为你的后端接口URL
                type: "get",
                dataType: "json",
                aysnc: false,
                data: {
                    "staffId": $('select[name="person"]').val(),
                    "jobName": $('input[name="jobName"]').val(),
                    "status": $('input[name="jobType"]:checked').val(),
                    "categoryName": personType.split("_")[0],
                    "staffCategoryID": personType.split("_")[1],
                    "jobLevel": jobLevel.split("_")[0],
                    "payScaleID": jobLevel.split("_")[1],
                    "depPostID": depPostID
                },
                success: function (response) {
                    // 处理后端的响应
                    console.log('Response from server:', response);
                },
                error: function (error) {
                    console.error("请求失败！！！");
                }
            });
        });
    });


</script>

</body>


</html>
