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
    <title>考勤设置</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/checkOnSetupSave.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>/js/WFJClient/pc/5l5C/office/checkOnSetupSave.js"></script>
    <style type="text/css">
        .clearfix:after {
            content: "\00A0";
            display: block;
            visibility: hidden;
            width: 0;
            height: 0;
            clear: both;
            font-size: 0;
            line-height: 0;
            overflow: hidden;
        }
    </style>
</head>
<body>
<div class="pc-box">
    <div class="div-box">
        <header>
            <ul class="clearfix">
                <li>
                    <a onclick="window.history.go(-1);return false;" target="_self">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
                    </a>
                </li>
                <li>
                    考勤设置-保存
                </li>
            </ul>
        </header>
        <div class="checkOnSetup-top">
            <div class="con-one">
                <ul class="checkOnSetup-top-ul">
                    <li>
                        <label>考勤类别</label>
                        <select id="checkOnTypeId"></select>
                    </li>
                    <li>
                        <label>工资级别</label>
                        <select id="rankId"></select>
                    </li>
                    <li style="height: .1rem;border-bottom-style: solid;border-bottom-color: #f0f0f0;border-bottom-width: 0.1rem;margin-top: 8px;margin-bottom: 6px;"></li>
                    <li>
                        自定义设置 <input id="setup" name="rewardDeductType" type="radio" value="SETUP" />
                    </li>
                    <li>
                        <label>奖折金额</label>
                        <input id="rewardDeductAmount" type="text" value="" placeholder="请输入奖励或折扣金额" />
                    </li>
                    <li style="height: .1rem;border-bottom-style: solid;border-bottom-color: #f0f0f0;border-bottom-width: 0.1rem;margin-top: 8px;margin-bottom: 6px;"></li>
                    <li>
                        计算方式设置 <input id="compute" name="rewardDeductType" type="radio" value="COMPUTE" checked />
                    </li>
<%--                    <li>--%>
<%--                        <label>参考工资</label>--%>
<%--                        <input id="referenceBaseSalary" name="referenceBaseSalary" type="checkbox" value="0" checked />--%>
<%--                        <span id="baseSalary">参考基本工资金额: ￥0</span>--%>
<%--                    </li>--%>
                    <li>
                        <label>参考工资</label><input id="referenceGuaranteeSalary" name="referenceGuaranteeSalary" type="checkbox" value="0" checked />
                        <span id="guaranteeSalary">参考保障工资金额: ￥0.00 元</span>
                    </li>
                    <li>
                        <label></label><input id="referenceWelfareSalary" name="referenceWelfareSalary" type="checkbox" value="0" />
                        <span id="welfareSalary">参考福利工资金额: ￥0.00 元</span>
                    </li>
                    <li>
                        <label>参考工资合计</label>
                        <span id="total">￥0.00 元</span>
                    </li>
                    <li style="height: .1rem;border-bottom-style: solid;border-bottom-color: #f0f0f0;border-bottom-width: 0.1rem;margin-top: 8px;margin-bottom: 6px;"></li>
                    <li>
                        通用设置
                    </li>
                    <li>
                        <label>奖折倍数</label>
                        <input id="discountRate" type="text" value="" placeholder="请输入奖折倍数" />
                    </li>
                    <li>
                        <label>奖折方式</label>
                        <input id="discount" name="amountType" type="radio" value="DISCOUNT" checked /> 折扣
                        <input id="reward" name="amountType" type="radio" value="REWARD" /> 奖励
                    </li>
<%--                    <li id="checkOnAmountLi">--%>
<%--                        <label>奖励金额</label>--%>
<%--                        <input id="checkOnAmount" type="text" value="" placeholder="请输入奖励金额" />--%>
<%--                    </li>--%>
                    <li style="height: .1rem;border-bottom-style: solid;border-bottom-color: #f0f0f0;border-bottom-width: 0.1rem;margin-top: 8px;margin-bottom: 6px;"></li>
                    <li>
                        <label>设置人</label>
                        <input id="setupName" type="text" value="" readonly="readonly"/>
                    </li>
                    <li>
                        <label>审核</label>
                        <label>会议纪要</label>
                    </li>
                </ul>
            </div>
            <div class="con-two">
                <ul class="checkOnSetup-top-ul2">
                    <li class="top-right">
                        <a class="button-a">
                            <span class="button-span">新增</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="footer div-bottom">
            <ul class="clearfix">
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_23.png" alt="">
                    </div>
                    <p>
                        消息
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_38.png" alt="">
                    </div>
                    <p>
                        通讯
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_08.jpg" alt="">
                    </div>
                    <p>
                        数字
                    </p>
                </li>
                <li class="active">
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_37.png" alt="">
                    </div>
                    <p>
                        5L5C
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_11.png" alt="">
                    </div>
                    <p>
                        我的
                    </p>
                </li>
            </ul>
        </div>
    </div>
</div>

<script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>


</body>
</html>
