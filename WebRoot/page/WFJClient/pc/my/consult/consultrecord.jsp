<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/my/consult/consultrecord.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/my/consult/consultrecord.js" type="text/javascript" charset="utf-8"></script>

    <title>&lrm;</title>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self" >

            <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li>
            ${param.name}回访记录
        </li>
    </ul>
</header>
<form name="form" id="form" method="post">
    <input type="submit" name="submit" style="display: none;"/>
<input type="hidden" name="consult.crId" value="${consult.crId}">
<div class="content">


    <div class="div-name">
        <label class="label-t" for="">是否接听：</label>
       <div><input type="radio" name="consult.islisten" value="01" id="yes">&nbsp;<label for="yes">是</label>&nbsp;&nbsp;&nbsp;<input type="radio" name="consult.islisten" value="00" id="no">&nbsp;<label for="no">否</label></div>
    </div>
    <div class="div-name">
        <label class="label-t" for="">客户类型：</label>
        <div><input type="radio" name="consult.clientType" value="02" id="gr">&nbsp;<label for="gr">个人</label>&nbsp;&nbsp;&nbsp;<input type="radio" name="consult.clientType" value="01" id="gs">&nbsp;<label for="gs">公司</label></div>
    </div>
    <div class="div-name">
        <label class="label-t" for="">合作意向：</label>
        <div><input type="radio" name="consult.isIntentCustomer" value="00" id="bg">&nbsp;<label for="bg">不感兴趣</label>&nbsp;<input type="radio" name="consult.isIntentCustomer"  value="01" id="yx">&nbsp;<label for="yx">意向客户</label>&nbsp; <input type="radio" name="consult.isIntentCustomer"  value="02" id="cj">&nbsp;<label for="cj">成交客户</label> </div>
    </div>
    <div class="div-area">
        <label class="label-t" for="">通话记录：</label>
        <div><textarea name="consult.visitContent">${consult.visitContent}</textarea></div>
    </div>
    <div class="div-bottom">
        <p class="save">
            保存
        </p>

    </div>

</div>

    <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
            framespacing="0" height="0"></iframe>
</form>



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

</body>
<script type="text/javascript">
var basePath = "<%=basePath%>";

var islisten = "${consult.islisten}";
var clientType = "${consult.clientType}";
var isIntentCustomer = "${consult.isIntentCustomer}";
    
    $(function () {
        if(islisten=="01"){
            $("#yes").attr("checked",true);
        }else{
            $("#no").attr("checked",true);
        }
        if(clientType=="01"){
            $("#gs").attr("checked",true);
        }else{
            $("#gr").attr("checked",true);
        }

        if(isIntentCustomer=="00"){
            $("#bg").attr("checked",true);
        }else if(isIntentCustomer=="01") {
            $("#yx").attr("checked", true);
        }else{
            $("#cj").attr("checked",true);
        }
    })

</script>
</html>
