<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/checkboxStyle.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/inventory/depotManageMobileAdd.css">
    <script type="text/javascript" src="//api.map.baidu.com/api?v=2.0&ak=AGLjXtGoLy3G7BBKEnMgDoHpt9G0wcGS"></script>
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/WFJClient/pc/5l5C/office/inventory/depotManageMobileAdd.js"></script>

    <title>库房信息</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>"
        var dataList;
        var depotType="${depotManage.depotType}";
        var typePID="${epotManage.depotPID==null||depotManage.depotPID==""?depotManage.depotPID:param.typePID}";
    </script>
</head>

<body>
<div id="btn_gwc" style=" opacity: 1;display:none;">
    <p></p>
</div>
<div id="allmap"></div>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self" >
                <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li>
            库房信息
        </li>
    </ul>
</header>
<form name="form_depot" id="form_depot" method="post">
    <input type="submit" name="formSubmit" id="formSubmit" style="display: none;">
    <input name="depotManage.depotID" id="depotID" type="hidden" value="${depotManage.depotID}"/>
    <input name="depotManage.depotKey" id="depotKey" type="hidden" value="${depotManage.depotKey}"/>
    <input name="depotManage.depotPID" id="depotPID" type="hidden" value="${depotManage.depotPID==null||depotManage.depotPID==""?param.typePID:depotManage.depotPID}"/>
    <input name="depotManage.depotshare" id="depotshare" type="hidden" value="${depotManage.depotshare}"/>

    <div class="content">
        <div class="div-name">
            <label for="">上级仓库</label>
            <input type="text"  value="${param.parentName}"/>
        </div>
        <div class="div-name">
            <label for="">仓库编号</label>
            <input type="text"  placeholder="请填写仓库编号" class="depotCoding"  name="depotManage.depotCoding" id="depotCoding" value="${param.typeNum==null||param.typeNum==""?depotManage.depotCoding:param.typeNum}"/>
        </div>
        <div class="div-name">
            <label for="">仓库名称</label>
            <input type="text"  placeholder="请填写仓库名称" class="depotName"  name="depotManage.depotName" id="depotName" value="${param.typeName==null||param.typeName==""?depotManage.depotName:param.typeName}"/>
        </div>
        <div class="div-name div-htfl">
            <label for="" class="htfl">仓库类别</label>
            <a id="itemID_a" href="javascript:void(0)">
                <dl>
                    <dd>请选择仓库类别</dd>
                    <input type="text"  style="display:none"  name="depotManage.itemID" id="itemID" value="${depotManage.itemID}"/>
                </dl>
            </a>
        </div>
        <div class="div-name div-htfl">
            <label for="" class="htfl">仓库类型</label>
            <a id="depotType_a" href="javascript:void(0)">
                <dl>
                    <dd>请选择仓库类型</dd>
                    <input type="text"  style="display:none"  name="depotManage.depotType" id="depotType" value="${depotManage.depotType}"/>
                </dl>
            </a>
        </div>
        <div class="div-name div-htfl">
            <label for="" class="htfl">使用状态</label>
            <div class="p-inp clearfix">
                <div class="div-radio">
                    <label for="radio-0">已启用</label>
                    <input type="radio" name="depotManage.useState" value="" class="my_radio" id="radio-0" checked="">
                </div>
                <div class="div-radio">
                    <label for="radio-1">未启用</label>
                    <input type="radio" name="depotManage.useState" value="02" class="my_radio" id="radio-1">
                </div>
            </div>
        </div>
        <div class="div-name div-htfl div-sfgx">
            <label for="" class="htfl">是否共享</label>
            <div class="main_inp_right">
                <p></p>
            </div>
            <div class="toggle toggle--switch toggle_ZDY">
                <input type="checkbox" id="toggle--switch" class="toggle--checkbox">
                <label class="toggle--btn" for="toggle--switch">
                    <span class="toggle--feature" data-label-on="是" data-label-off="否"></span>
                </label>
            </div>
        </div>
        <div class="div-name">
            <label for="">描述</label>
            <input type="text"  placeholder="请填写行业描述"  name="depotManage.remark" id="remark" value="${depotManage.remark}"/>
        </div>
        <div class="div-name">
            <label for="">定位</label>
            <input type="text" readonly="readonly" class="depotAddress" name="depotManage.depotAddress" id="depotAddress" value="${depotManage.depotAddress}"/>
            <input type="hidden" class="dwLnglatX" name="depotManage.dwLnglatX" id="dwLnglatX" value="${depotManage.dwLnglatX}"/>
            <input type="hidden" class="dwLnglatY" name="depotManage.dwLnglatY" id="dwLnglatY" value="${depotManage.dwLnglatY}"/>
        </div>
        <div class="div-bottom"  onclick="manageSave()">保存</div>
    </div>
</form>
<!--选择项目归属弹层-->
<section id="typeBelongLayer" class="express-belong-box">
    <header>
        <h3>选择仓库类别</h3>
        <a id="closeBelong" class="close" href="javascript:void(0)" title="关闭" style="background:  url(<%=basePath%>images/ea/office/contract/stamp/close.png) no-repeat center"></a>
    </header>
    <article id="belongBox">
        <ul id="belongList" class="belong-list"></ul>
    </article>
</section>
<!--选择项目类别弹层-->
<section id="projectTypeLayer" class="express-belong-box">
    <header>
        <h3>选择仓库类型</h3>
        <a id="closeType" class="close" href="javascript:void(0)" title="关闭"></a>
    </header>
    <article id="TypeBox">
        <ul id="TypeList" class="belong-list">
            <li value="1">库房</li>
            <li value="2">区域</li>
            <li value="3">货架</li>
            <li value="4">展位</li>
        </ul>
    </article>
</section>

<!--遮罩层-->
<div id="belongMask" class="mask"></div>
<!--表单提示-->
<div class="div-tingyong div-dqd">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
        <div class="div-box">
            <p class="titlep"></p>
            <div class="clearfix">
                <p class="left close-tingyong" onclick="close_div()">取消</p>
                <p class="right close-confirm" onclick="close_div()">确定</p>
            </div>
        </div>
    </div>
</div>
<div id="prompt" style="width: 100%; display: none;z-index: 1001">
    <center>
        <div>
            <span style="position: relative; top: 19.8%;"></span>
        </div>
    </center>
</div>
</body>

</html>
