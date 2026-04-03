<!DOCTYPE html>
<%@ page import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%@ page import="hy.ea.bo.CAccount" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <meta name="format-detection" content="telephone=no, email=no"/>
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/base.css">
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/demand.css">
    <link href="<%=basePath%>/css/ea/edmandServe/mobiscroll_002.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>/css/ea/edmandServe/mobiscroll.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>/css/ea/edmandServe/mobiscroll_003.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>/css/ea/edmandServe/mobiscroll_004.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="<%=basePath%>/css/ea/edmandServe/mobiscroll-select.min.css">

    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/jquery.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/WFJClient/localforage.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/setHtmlFont.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/release.js"></script>
    <!--选择日期时间插件 开始-->
    <script src="<%=basePath%>/js/ea/marketing/edmandServe/mobiscroll_002.js" type="text/javascript"></script>
    <script src="<%=basePath%>/js/ea/marketing/edmandServe/mobiscroll_004.js" type="text/javascript"></script>
    <script src="<%=basePath%>/js/ea/marketing/edmandServe/mobiscroll.js" type="text/javascript"></script>
    <script src="<%=basePath%>/js/ea/marketing/edmandServe/mobiscroll_003.js" type="text/javascript"></script>
    <script src="<%=basePath%>/js/ea/marketing/edmandServe/mobiscroll_005.js" type="text/javascript"></script>
    <script src="<%=basePath%>/js/ea/marketing/edmandServe/mobiscroll-select.min.js"></script>
    <!--选择日期时间插件 结束-->
    <title>需求发布</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var i;
        var nums = 60;
        var clock = '';
        var token = 0;/*
        var staffid = '${staffid}';
        var staffname='${name}';
        var sccId = '${sccId}';*/
        var user = '${user}';
        var tle = '${tle}';
        let originPage;
        var cid="${cid}";
    </script>

    <style type="text/css">
        #prompt div {
            width: 70%;
            background: rgba(0, 0, 0, 0.5);
        }
        .nest_page .nest_bd iframe {
            height: 100%;
            width: 100%;
        }
    </style>
<body>
<s:if test="tle==1">
    <header class="com_head">
        <a target="_self" class="back" id="ret" onclick="javascript:history.back(-1);"></a>
        <h1>应急发布</h1>
    </header>
</s:if>
<div class="wrap_page" <s:if test='tle==null||tle==0'>style="margin-top:0;" </s:if>>
    <form name="savedetailform" id="savedetailform" method="post">
        <input type="submit" name="submit" id="submit" style="display: none;"/>
        <%--<input type="hidden" name="demandDetail.ddsccid" value="${sccId}"/>
        <input type="hidden" name="demandDetail.ddstaffid" value="${staffid}"/>--%>
        <input type="hidden" name="originPage" id="originPage" value=""/>
        <div class="demand_release">
            <div class="d_r_box clearfix companyname">
                <span class="d_r_L">公司：</span>
                <span class="d_r_R">
                   <input type="text" class="d_r_inp" readonly id="companyname"/>
                </span>
            </div>
            <div class="d_r_box clearfix">
                <span class="d_r_L">地址：</span>
                <span class="d_r_R d_r_site">
                   <input type="text" class="d_r_inp notNull" placeholder="点击定位选择地址" name="demandDetail.ddaddress"
                          id="ddaddress"/>
                   <input type="hidden" name="demandDetail.coordinate" id="coordinate"/>
                </span>
            </div>
            <div class="d_r_box clearfix">
                <span class="d_r_L">项目工种：</span>
                <span class="d_r_R">
                   <input type="text" class="d_r_inp notNull" placeholder="点击选择工种" readonly id="selsct_classify"
                          name="demandDetail.ddworktype" readonly/>
                   <input type="hidden" name="demandDetail.ddscodeid" id="ddscodeid"/>
                </span>
            </div>
            <div class="d_r_box clearfix">
                <span class="d_r_L">项目说明：</span>
                <span class="d_r_R">
                   <input type="text" class="d_r_inp notNull" placeholder="请输入说明" name="demandDetail.ddtitle"
                          maxlength="15"/>
                </span>
            </div>
            <%--<div class="d_r_box clearfix">
                <span class="d_r_L">项目说明：</span>
                <span class="d_r_R">
                   <input type="text" class="d_r_inp notNull" placeholder="备注" name="demandDetail.ddremark"
                          maxlength="50"/>
                </span>
            </div>--%>
            <div class="d_r_box clearfix">
                <span class="d_r_L">图片：</span>
                <span class="d_r_R">
                   <input name="photos" id="photos" type="file" contentEditable="false" size="10"/>
                </span>
            </div>
            <%--<div class="d_r_box clearfix">
                <span class="d_r_L">期望时间：</span>
                <span class="d_r_R">
                   <input type="text" class="d_r_inp notNull" placeholder="请选择期望时间" name="demandDetail.ddexdate"
                          id="begin_time" readonly/>
                </span>
            </div>--%>
            <div class="d_r_box clearfix">
                <span class="d_r_L" style="width: 40%;">期望价格(元)：</span>
                <span class="d_r_R" style="width: 60%;">
                   <input type="text" class="d_r_inp notNull" placeholder="请输入期望价格" name="demandDetail.ddexpectprice"/>
                </span>
            </div>
            <div class="d_r_box clearfix">
                <span class="d_r_L">联系人：</span>
                <span class="d_r_R">
                   <input type="text" class="d_r_inp notNull" placeholder="请输入联系人" name="demandDetail.ddcontactname"
                          id="ddcontactname" value="${name}"/>
                </span>
            </div>
            <div class="d_r_box clearfix">
                <span class="d_r_L">联系电话：</span>
                <span class="d_r_R  clearfix">
                   <input type="tell" class="d_r_inp notNull" placeholder="请输入联系电话" id="tel"
                          name="demandDetail.ddcontactphone" value="${user}"/>
                   <%--<input type="tell" class="d_r_inp tell_inp" placeholder="请输入联系电话" id="tel"
                          name="demandDetail.ddcontactphone"/>
                   <input type="button" class="send_code" id="send_code" onclick="sendCode(this);return false;"
                          value="获取验证码">--%>
                </span>
            </div>
            <%--<div class="d_r_box clearfix">
                <span class="d_r_L">验证码：</span>
                <span class="d_r_R">
                   <input type="text" class="d_r_inp notNull" placeholder="请输入验证码" id="valnum"/>
                </span>
            </div>--%>
        </div>
        <div id="test_state"></div>
        <a class="sure_release" onclick="save_submit()">确认发布</a>
        <%--<a class="release_rec"></a>--%>
    </form>
</div>
<div class="nest_page" style="background: #f3f3f3;">
    <div class="nest_hd">
        <a href="###" class="nest_back"></a>
        <span>选择工种</span>
    </div>
    <div class="nest_bd">
        <iframe name="main" marginwidth="0" scrolling="yes" marginheight="0"  frameborder="0" id="iframe-" border="0" framespacing="0" noresize="noResize" vspale="0"></iframe>
    </div>
</div>
<!--正在加载/正在发布 遮罩层 开始-->
<div class="overlay_text">
    <span>正在加载，请稍候……</span>
</div>
<!--正在加载/正在发布 遮罩层 结束-->

<div id="prompt" style="width: 100%;display: none;">
    <center>
        <div>
            <span style="position: relative;top: 19.8%;"></span>
        </div>
    </center>
</div>
</body>
</html>
