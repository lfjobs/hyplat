<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/salary/salaryUnitsAdd.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/5l5C/human/salary/salaryUnitsAdd.js" type="text/javascript" charset="utf-8"></script>

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
        <li class="titleli">


        </li>
    </ul>
</header>
<form name="form" id="form" method="post">
    <input type="submit" name="submit" style="display: none;"/>

<div class="content">
<c:if test="${param.id2 eq null||param.id2 eq ''}">
    <c:if test="${param.id ne null&&param.id ne ''}">
    <div class="div-name">
        <label for="">父级单元名称</label>
        <input type="hidden"   name="salaryUnits.parentslID"  value="${param.id}"/>
        <input type="text"   value="${param.name}" readonly/>
    </div>
    </c:if>
</c:if>
    <div class="div-name">
        <label for="">薪资单元名称</label>
        <input type="hidden"   name="salaryUnits.suID"  value="${param.id2}" id="bsid"/>
        <input type="text"  placeholder="薪资单元名称"  name="salaryUnits.unitName" id="name" value="${param.name2}"/>
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
var id2 = "${param.id2}";
var id  = "${param.id}";
var jj = "${param.jj}";

$(function(){

    setTitle();

})
function setTitle(){

    var t1 = "";
    var t2 = "";
    if(id2!=null&&id2!=""){
        t1 = "修改";
        if(jj==1){
            t2 = "一级薪资单元";
        }else{
            t2 = "二级薪资单元";
        }
    }else{

        t1 = "添加";
        if(id!=null&&id!=""){
            t2 = "二级薪资单元";
        }else{
            t2 = "一级薪资单元";
        }
    }


    $(".titleli").text(t1+t2);

}
</script>
</html>
