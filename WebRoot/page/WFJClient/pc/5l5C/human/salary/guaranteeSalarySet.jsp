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
    <title>保障工资设置</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/salary/guaranteeSalarySet.css">
    <script src="<%=basePath%>util/layui/layui.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/salary/guaranteeSalarySet.js"></script>

</head>
<body class="body">
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1);  return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li id="headerTitle">
            基本工资
        </li>
        <li>
        </li>
    </ul>
</header>

<div class="content" >
    <!--工资设置-->
    <section  class="setting">
        <div class="layui-form div-set div-form" >
            <div class="layui-form-item level-data" >
                <label class="form-label" style="color:#249133">时间</label>
                <div class="layui-input-block">
                    <label class="form-label" id="date">${param.date}</label>
                </div>
            </div>
            <div class="layui-form-item level-data">
                <label class="form-label " style="color:#0d9b21">级别序号</label>
                <div class="layui-input-block">
                    <label class="form-label" id="gradeName">${param.gradeName}</label>
                </div>
            </div>
            <div class="layui-form-item level-data">
                <label class="form-label level-data" style="color:#0d9b21">级别编码</label>
                <div class="layui-input-block">
                    <label class="form-label" id="gradeNumStr">${param.gradeNumStr}</label>
                    <label class="form-label" id="gradeNum" style="display:none">${param.gradeNum}</label>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label label-explain" >1级工资级别级差 按时间计算</label>
                <label class="layui-form-label label-explain" >2级工资级别级差 按计件计算</label>
                <label class="layui-form-label label-explain" >3级工资级别级差 按天/半天计算</label>
                <label class="layui-form-label label-explain" >4级工资级别级差 按当年最低基本工资计算</label>
            </div>
            <div class="layui-form-item item-input basic-salary">
                <label class="layui-form-label set-label" style="width:80px">基本工资</label>
                <div class="layui-input-block set-block" style="margin-left:100px" >
                    <input type="text"   lay-verify="number"  class="layui-input" id="basicSalary">
                    <input type="hidden"   lay-verify="number"  class="layui-input" id="basicSalaryDictId">
                    <input type="hidden"   lay-verify="number"  class="layui-input" id="basicSalaryDictKey">
                </div>
            </div>
            <div class="layui-form-item item-input level-salary">
                <label class="layui-form-label set-label" style="width:80px">调平工资</label>
                <div class="layui-input-block set-block" style="margin-left:100px" >
                    <input type="text"   lay-verify="number"  class="layui-input" id="levelSalary">
                    <input type="hidden"   lay-verify="number"  class="layui-input" id="levelSalaryDictId">
                    <input type="hidden"   lay-verify="number"  class="layui-input" id="levelSalaryDictKey">
                </div>
            </div>
            <div class="layui-form-item item-input upgrade-salary">
                <label class="layui-form-label set-label label-salary" >5级JB-005起职能工资</label>
                <div class="layui-input-block set-block"  style="margin-left:180px">
                   <%-- <input type="text"   lay-verify="number" placeholder="每升一级加10/20/30/40/50" class="layui-input" id="salary">--%>
                    <input type="hidden"   lay-verify="number"  class="layui-input" id="salaryDictKey">
                    <input type="hidden"   lay-verify="number"  class="layui-input" id="salaryDictId">
                    <select name="level-select" lay-verify="required" id ="salary"  placeholder="每升一级加10/20/30/40/50" lay-filter="salarySet" >
                        <option value=""></option>
                        <option value="10">10元</option>
                        <option value="20">20元</option>
                        <option value="30">30元</option>
                        <option value="40">40元</option>
                        <option value="50">50元</option>
                        <option value="-1">自定义</option>
                    </select>
                </div>
                <label class="layui-form-label set-label label-remark" >(每升一级加10/20/30/40/50元)</label>
            </div>
            <div class="layui-form-item item-input upgrade-salary">
                <label class="layui-form-label set-label" >升级递增倍数</label>
                <div class="layui-input-block set-block" style="margin-left:180px">
                    <input type="hidden"   lay-verify="number"  class="layui-input" id="multiplierDictKey">
                    <input type="hidden"   lay-verify="number"  class="layui-input" id="multiplierDictId">
                    <select name="level-select" lay-verify="required" id ="multiplier" >
                        <option value="1">1倍</option>
                        <option value="2">2倍</option>
                        <option value="3">3倍</option>
                        <option value="4">4倍</option>
                        <option value="5">5倍</option>
                        <option value="6">6倍</option>
                        <option value="7">7倍</option>
                        <option value="8">8倍</option>
                        <option value="9">9倍</option>
                        <option value="10">10倍</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item item-input start-data" style="display:none">
                <label class="layui-form-label set-label" style="width:100px">4级初始值(元)</label>
                <div class="layui-input-block set-block" style="margin-left:180px" >
                    <input type="text"   lay-verify="number"  class="layui-input" value="0" id="startValue" >
                    <input type="hidden"   lay-verify="number"  class="layui-input" id="startValueDictKey">
                    <input type="hidden"   lay-verify="number"  class="layui-input" id="startValueDictId">
                </div>
            </div>
            <div class="layui-form-item item-input basic-salary">
                <label class="layui-form-label" style="margin-top:15px;width:80px">时间设置</label>
                <input type="hidden"   lay-verify="number"  class="layui-input" id="dateSetDictKey">
                <input type="hidden"   lay-verify="number"  class="layui-input" id="dateSetDictId">
                <div class="layui-input-block">
                    <input type="radio" name="dateSet" value="1" title="21.75\天"  checked>
                </div>
                <div class="layui-input-block">
                    <input type="radio" name="dateSet" value="2" title="日历\27\28\29\30\31"  >
                </div>
            </div>
            <div class="layui-form-item" >
                <label class="layui-form-label" style="width:100%;color:green">正常5天\周\月</label>
            </div>

            <div class="layui-form-item salary-data-set">
                <label class="layui-form-label label-month" >基本工资金额 \ 月</label>
                <div class="layui-input-block">
                    <input type="text" name="salaryData" required  lay-verify="required" value="自动生成" disabled autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item salary-data-set">
                <label class="layui-form-label label-day" >基本工资金额 \ 天</label>
                <div class="layui-input-block">
                    <input type="text" name="salaryData" required  lay-verify="required" value="自动生成" disabled  autocomplete="off" class="layui-input">
                </div>
            </div>

        </div>
        <div class="layui-form-item-button div-save">
            <div class="layui-input-button">
                <button class="layui-btn" onclick="saveData()" >保存</button>
            </div>
        </div>
    </section>
    <section  class="sec-customize-custom custom div-sec-data" style="display:none">
        <div class="layui-form-item div-custom-set" >
            <div class="layui-inline" style="margin-bottom: 0px;">
                <div class="layui-input-inline" style="width: 130px;float: left;margin-left: 50px;margin-top:15px">
                    <input type="text" name="price_max" id ="numSet" style="text-align:center" placeholder="请填写自定义值" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-form-mid" style="margin-left:0px;top:20px">(元)</div>
            </div>
        </div>
        <div class="layui-form-item div-custom-button" style="    width: 100%;margin-top: 10px;margin-bottom: 0px;border-top: 0.025rem solid #eee;">
            <div class="layui-input-block" style="margin-left: 0px;min-height: 53px;">
                <button class="layui-btn-cancel" onclick="cancel()" style="color: #666;border-right: 0.025rem solid #eee;">取消</button>
                <button class="layui-btn-save" onclick="saveCustom()">确定</button>

            </div>
        </div>
    </section>
</div>
<script type="text/javascript">
    const basePath = "<%=basePath%>";
    const type = "${param.type}";
    const param = [];
    param.push("gradeName=" + "${param.gradeName}");
    param.push("&&gradeNum=" + "${param.gradeNum}");
    param.push("&&gradeNumStr=" + "${param.gradeNumStr}");
    param.push("&&date=" + "${param.date}");
    let headerTitle = "";
    if ("basic" == type){
        headerTitle = "基本工资";
        $(".basic-salary").show();
    } else if("level" == type){
        $(".level-salary").show();
    }else{
        $(".upgrade-salary").show();
    }
    if ("role" == type){
        headerTitle = "职能工资";
    } else if ("duty" == type){
        headerTitle = "职责工资";
    } else if ("compete" == type){
        headerTitle = "竞职金";
    } else if ("secrecy" == type){
        headerTitle = "保密工资";
    } else if ("level" == type){
        headerTitle = "调平工资";
    } else if ("welfare" == type){
        headerTitle = "非工资福利津贴";
        $(".start-data").show();
    }
    $("#headerTitle").html(headerTitle );
    $(".label-salary").html("5级JB-005起" + headerTitle);
    $(".label-month").html(headerTitle + "金额 \ 月");
    $(".label-day").html(headerTitle + "金额 \ 日");

</script>
</body>
</html>
