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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/selectWldw.css"/>
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/selectWldw.js?version=20230517" type="text/javascript" charset="utf-8"></script>

    <title>&lrm;</title>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="back()" target="_self" >
            <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" >
                </a>
        </li>
        <li>
            选择收件人
        </li>
    </ul>
</header>
<div class="content">
    <section class="headNav">
        <ul>
            <li class="jtn" style="display: none"><p><a  href="javascript:nav('1','${param.docId}','${param.typee}')"><img src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">集团内部</a></p></li>
            <li><p><a  href="javascript:nav('2','${param.docId}','${param.typee}')"><img src="<%=basePath%>images/ea/office/contract/selectp/dxseleted.png">往来公司</a></p></li>
            <li><p><a href="javascript:nav('3','${param.docId}','${param.typee}')"><img src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">往来个人</a></p></li>

        </ul>
    </section>
    <section class="sec-search">
        <div class="div-name">
            <label for="">收件人公司</label>
            <input type="text"  placeholder="请填写收件人公司"  id="sjrgs" autocomplete="off"/>
            <input type="hidden"  id="comid"/>





        </div>
        <div class="div-name">
            <label for="">收件人</label>
            <input type="text"  placeholder="请填写收件人姓名或者手机号"  id="sjr" autocomplete="off"/>
            <input type="hidden"   id="staffid"/>
            <input type="hidden"   id="orgid"/>
            <input type="hidden"   id="staffname"/>
        </div>
    </section>

    <section class="sec-ul">
        <p class="title-p">请在以下搜索结果进行选择</p>
        <ul class="ul-list">

        </ul>
    </section>

    <p class="navrecent">以下是最近联系人：</p>
    <section class="sec-ul2">
        <ul class="ul-list2">
        </ul>
    </section>
    <section class="sec-bottom">
        <p>
           确定提交
        </p>
    </section>
</div>



<!--表单提示-->
<div class="div-tingyong">
    <div class="box">
        <p>温馨提示</p>
        <div class="div-box">
            <p class="titlep">操作成功</p>
            <div class="clearfix">
                <p class="right close-tingyong">确定</p>
            </div>
        </div>
    </div>
</div>


<div class="loading">
    <div class="div-box">
        <img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/selectp/loading2.gif" alt="" />
        <p>处理中...</p>
    </div>
</div>
</body>
<script type="text/javascript">
    var module = "<%=session.getAttribute("module")%>"
    var basePath = "<%=basePath%>";
    var companyID = "${param.companyID}";
    var orgID = "${param.orgID}";
    var parameter = "${param.parameter}";
    var typee = "${param.typee}";
    var docId = "${param.docId}";
    var module = "<%=session.getAttribute("module")%>"
    if(module==""){

        $(".jtn").hide();
    }else{
        $(".jtn").show();

    }



    function nav(c,docId,typee) {
        if(c=="1"){
            document.location.replace("<%=basePath%>page/ea/main/office_ea/contract/selectCompany.jsp?docId="+docId+"&typee="+typee);
        }else if(c=="2"){
            document.location.replace("<%=basePath%>page/ea/main/office_ea/contract/selectWldw.jsp?docId="+docId+"&typee="+typee);

        }
        else if(c=="3"){
            document.location.replace("<%=basePath%>page/ea/main/office_ea/contract/selectWlgr.jsp?docId="+docId+"&typee="+typee);

        }


    }
</script>
</html>

