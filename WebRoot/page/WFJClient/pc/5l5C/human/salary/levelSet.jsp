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
    <title>级别设置</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/salary/levelSet.css">
        <script src="<%=basePath%>util/layui/layui.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
        <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/salary/levelSet.js"></script>
</head>
<body class="body">
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1); return false" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            级别级差
        </li>
        <li>
        </li>
    </ul>
</header>

<div class="content">
    <section id="dataLayer" class="sec-level-select" >
        <div class="layui-form" >
            <div class="layui-form-item">
                <label class="layui-form-label" style="color:#0d9b21">时间</label>
                <div class="layui-input-block">
                    <input type="text"   class="layui-input" id="date" value="${param.date}" disabled>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" style="color:#0d9b21">级别序号</label>
                <div class="layui-input-block">
                    <input type="text"   class="layui-input" id="oldGradeNumStr" value="${param.gradeNumStr}" disabled>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" style="color:#0d9b21">当前级别</label>
                <div class="layui-input-block">
                    <input type="text"   class="layui-input" id="oldGradeName" value="${param.gradeName}" disabled>
                    <input type="text"   class="layui-input" id="oldGradeNum" value="${param.gradeNum}" style="display:none">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">设置级别</label>
                <div class="layui-input-block">
                    <select name="level-select" lay-verify="required" id ="levelNum"lay-filter="levelSelect">
                        <option value=""></option>
                        <option value="10">1级 ~ 10级</option>
                        <option value="15">1级 ~ 15级</option>
                        <option value="20">1级 ~ 20级</option>
                        <option value="25">1级 ~ 25级</option>
                        <option value="30">1级 ~ 30级</option>
                        <option value="35">1级 ~ 35级</option>
                        <option value="40">1级 ~ 40级</option>
                        <option value="45">1级 ~ 45级</option>
                        <option value="50">1级 ~ 50级</option>
                        <option value="55">1级 ~ 55级</option>
                        <option value="60">1级 ~ 60级</option>
                        <option value="65">1级 ~ 65级</option>
                        <option value="70">1级 ~ 70级</option>
                        <option value="75">1级 ~ 75级</option>
                        <option value="80">1级 ~ 80级</option>
                        <option value="85">1级 ~ 85级</option>
                        <option value="90">1级 ~ 90级</option>
                        <option value="95">1级 ~ 95级</option>
                        <option value="100">1级 ~ 100级</option>
                        <option value="-1">自定义</option>
                    </select>
                </div>
            </div>

            <div class="layui-form-item-button">
                <div class="layui-input-button">
                    <button class="layui-btn" onclick="saveData()" >保存</button>
                </div>
            </div>
        </div>
    </section>
    <section  class="sec-level-custom custom div-sec-data" style="display:none">
        <div class="layui-form-item div-custom-set" >
            <div class="layui-inline" style="">
                <div class="layui-form-mid" style="margin-left:20px">1(级)</div>
                <div class="layui-form-mid" style="margin-left:0px;margin-right:0px">~</div>
                <div class="layui-input-inline" >
                    <input type="text" name="price_max" id ="levelNumSet" placeholder="请填写级别数" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-form-mid" style="margin-left:0px;top:0px">(级)</div>
            </div>
        </div>
        <div class="layui-form-item div-custom-button" >
            <div class="layui-input-block">
                <button class="layui-btn-cancel" onclick="cancel()">取消</button>
                <button class="layui-btn-save" onclick="saveCustom()">确定</button>

            </div>
        </div>
    </section>
</div>

<script type="text/javascript">
    var basePath = "<%=basePath%>";


</script>
</body>
</html>
