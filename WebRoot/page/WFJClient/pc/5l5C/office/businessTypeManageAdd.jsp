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
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/businessType/businessTypeAdd.css">
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/businessType/businessTypeAdd.js" type="text/javascript" charset="utf-8"></script>

    <title>项目设计添加</title>
    <style>
        body{overflow:hidden}
        #prompt div {
            width: 80%;
            background: rgba(0, 0, 0, 0.5);
        }
        ul, ol { list-style-type: none; }
        a { color: #333; text-decoration: none; outline: none; }
        a:link, a:visited, a:hover, a:active { color: #333; outline: none; }
        .express-belong-box article { height: 250px; overflow-y: scroll; }
        .belong-list li { padding: .5rem; border-bottom: 1px solid #e2e2e2; text-align: justify; font-size: .9rem; line-height: 1.25rem; }
        /*选择地区弹层 */
        .express-belong-box { position: absolute; bottom: -100%; z-index: 15; width: 100%; background-color: #fff; color: #4d525d; }
        .express-belong-box header { position: relative; border-top: 1px solid #e2e2e2; border-bottom: 1px solid #e2e2e2; display: block; background-color: #fff;
        }
        .express-belong-box header h3 { margin: 0 1.675rem; text-align: center; font-size: .8rem; line-height: 2.25rem; }
        .express-belong-box header .close { position: absolute; top: 0; width: 1.675rem; height: 2.25rem; }
        .express-belong-box header .close { right: 0; background: url(<%=basePath%>images/ea/office/contract/stamp/close.png) no-repeat center; background-size: .675rem .675rem; }
        .express-belong-box article { height: 250px; overflow-y: scroll; }
        .belong-list li { padding: .6rem; border-bottom: 1px solid #e2e2e2; text-align: justify; font-size: .9rem; line-height: 1.45rem;text-align:center }
        /*遮罩层*/
        .mask { display: none; position: absolute; top: 0; left: 0; z-index: 12; width: 100%; height: 100%; background-color: rgba(0,0,0,.5); }
    </style>

</head>

<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.back();return false;" target="_self" >

            <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li>
            项目设计添加
        </li>
    </ul>
</header>
<form name="form" id="form" method="post">
    <div class="content">
        <div class="div-name div-htfl">
            <label for="" class="htfl">项目归属</label>
            <a id="expressBelong" href="javascript:void(0)">
                <dl>
                    <dd>请选择项目归属</dd>
                    <input type="text"  style="display:none"  name="typePID" id="typePID"/>
                </dl>
            </a>
        </div>

        <div class="div-name">
            <label for="">项目编号</label>
            <input type="text"  placeholder="请填写项目编号"  name="typeNum" id="typeNum" value="${param.typeNum}"/>
        </div>

        <div class="div-name">
            <label for="">行业名称</label>
            <input type="text"  placeholder="请填写行业名称"  name="typeName" id="typeName" value="${param.typeName}"/>
        </div>
        <div class="div-name">
            <label for="">行业描述</label>
            <input type="text"  placeholder="请填写行业描述"  name="typeDesc" id="typeDesc" value="${param.typeDesc}"/>
        </div>

        <div class="div-bottom"  onclick="manageSave()">保存</div>

    </div>

    <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
            framespacing="0" height="0"></iframe>
</form>
<!--选择项目归属弹层-->
<section id="typeBelongLayer" class="express-belong-box">
    <header>
        <h3>选择项目归属</h3>
        <a id="closeBelong" class="close" href="javascript:void(0)" title="关闭"></a>
    </header>
    <article id="belongBox">
        <ul id="belongList" class="belong-list"></ul>
    </article>
</section>
<!--选择项目类别弹层-->
<section id="projectTypeLayer" class="express-belong-box">
    <header>
        <h3>选择项目类别</h3>
        <a id="closeType" class="close" href="javascript:void(0)" title="关闭"></a>
    </header>
    <article id="TypeBox">
        <ul id="TypeList" class="belong-list"></ul>
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
                <p class="left close-tingyong">取消</p>
                <p class="right close-confirm">确定</p>
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
<script type="text/javascript">
    var basePath = "<%=basePath%>"
    var dataList = null;
    var url ="<%=basePath%>ea/industry/sajax_ea_getBusinessTypeDivisionList.jspa?";
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            dataList = eval("(" + data + ")");
            initData();

        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
    function initData() {
        var belongStr = "";
        for (var i = 0; i < dataList.length; i++) {
            var typeName = dataList[i].typeName;
            belongStr += '<li onClick="selectBelong(' + i + ');">' + typeName + '</li>';
        }
        $("#belongList").html(belongStr);
        $("#belongBox").scrollTop(0);

    }
    function selectBelong(i){
        clockBelong();
        $("#expressBelong dl dd").html(dataList[i].typeName);
        $("#typePID").val(dataList[i].typeId);
    }


    /*关闭项目归属选项*/
    function clockBelong() {
        $("#belongMask").fadeOut();
        $("#typeBelongLayer").animate({"bottom": "-100%"});
        $("#projectTypeLayer").animate({"bottom": "-100%"});
        initData();
    }
    $(function() {
        /*打开项目归属选项*/
        $("#expressBelong").click(function() {
            $("#belongMask").fadeIn();
            $("#typeBelongLayer").animate({"bottom": 0});
        });

        /*关闭项目归属选项*/
        $("#belongMask, #closeBelong,#closeType").click(function() {
            clockBelong();
        });
    })



</script>
</html>
