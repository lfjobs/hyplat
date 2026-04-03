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
<head lang="zh">
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/tempSort.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>

    <script src="<%=basePath%>js/ea/office_ea/contract/tempSort.js" type="text/javascript" charset="utf-8"></script>

    <title>&lrm;</title>
    <script type="text/javascript">


        var basePath = "<%=basePath%>";
        var module = "${param.module}";

    </script>
</head>
<body>
<header>
    <ul class="clearfix">

        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self" >

                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" />

            </a>
        </li>

        <li>
            模板排序
        </li>
        <li>

        </li>
    </ul>
</header>
<div class="content">
    <form name="childrenform" method="post" id="childrenform">
        <input type="submit" name="submit" style="display:none"/>
        <input id="childrenID" name="childrenID" type="hidden">
        <s:token></s:token>
    </form>
    <table width="99%" height="37" border="0" align="center" cellpadding="0" cellspacing="0" class="ttable">
        <tr>
            <td width="33.33%" align="right">当前排序</td>
            <td width="33.33%" align="center">&nbsp;</td>

            <td width="33.33%" align="left">重新排序</td>
        </tr>
    </table>
    <table width="99%" height="204" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td width="46%" align="right">
                <ul class="ul-select" id="select1">
                    <s:iterator value="childlist" var="item">
                        <li id="${item.templateId}">${item.fileShowName}</li>
                    </s:iterator>

                </ul>

            </td>
            <td width="4%" align="center">
                <div class="right_dan" id="add"></div>
                <div class="left_dan" id="del"></div>
                <div class="right_suang"  id="add_all"></div>
                <div class="left_suang" id="del_all"></div>
            </td>
            <td width="50%">
                <ul class="ul-select" id="select2">


                </ul>
            </td>
        </tr>
    </table>
    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td height="30" align="center"><input type="button" id="save_all" class="btn02" name="button" value="保存" />

        </tr>
    </table>
</div>

<!--表单提示-->
<div class="div-tingyong div-dqd">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
        <div class="div-box">
            <p class="titlep">更换模板会清空内容确定更换么？</p>
            <div class="clearfix">
                <p class="left close-tingyong">取消</p>
                <p class="right close-confirm">确定</p>
            </div>
        </div>
    </div>
</div>

<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
        framespacing="0" height="0"></iframe>
</body>

</html>
