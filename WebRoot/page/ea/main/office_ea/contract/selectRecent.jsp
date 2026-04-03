<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom"%>
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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/selectRecent.css?version=20230517"/>
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/selectRecent.js" type="text/javascript" charset="utf-8"></script>

    <title>&lrm;</title>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self" >
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
            <li class="jtn" style="display: none;"><p><a href="javascript:nav('1','${param.docId}','${param.typee}')"><img src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">集团内部</a></p></li>
            <li><p><a href="javascript:nav('2','${param.docId}','${param.typee}')"><img src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">往来公司</a></p></li>
            <li><p><a href="javascript:nav('3','${param.docId}','${param.typee}')"><img src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">往来个人</a></p></li>
            <li><p><a href="javascript:nav('4','${param.docId}','${param.typee}')"><img src="<%=basePath%>images/ea/office/contract/selectp/dxseleted.png">最近联系</a></p></li>

        </ul>
    </section>
    <section class="sec-ul">
        <ul class="ul-list">
          <%--  <li class="clearfix active">
                <div class="sex">
                    <img class="img-01" src="<%=basePath%>images/ea/office/contract/selectp/img_02.png"/>
                    <img class="img-02" src="<%=basePath%>images/ea/office/contract/selectp/img_03.png"/>
                </div>
                <div class="div-img">
                    <img src="<%=basePath%>images/ea/office/contract/selectp/list_03.png"/>
                </div>
                <p>
                    深圳－张总
                </p>
            </li>
            <li class="clearfix">
                <div class="sex">
                    <img class="img-01" src="<%=basePath%>images/ea/office/contract/selectp/img_02.png"/>
                    <img class="img-02" src="<%=basePath%>images/ea/office/contract/selectp/img_03.png"/>
                </div>
                <div class="div-img">
                    <img src="<%=basePath%>images/ea/office/contract/selectp/list_06.png"/>
                </div>
                <p>
                    深圳－张总
                </p>
            </li>
            <li class="clearfix active">
                <div class="sex">
                    <img class="img-01" src="<%=basePath%>images/ea/office/contract/selectp/img_02.png"/>
                    <img class="img-02" src="<%=basePath%>images/ea/office/contract/selectp/img_03.png"/>
                </div>
                <div class="div-img">
                    <img src="<%=basePath%>images/ea/office/contract/selectp/list_08.png"/>
                </div>
                <p>
                    北京－赵总
                </p>
            </li>
            <li class="clearfix">
                <div class="sex">
                    <img class="img-01" src="<%=basePath%>images/ea/office/contract/selectp/img_02.png"/>
                    <img class="img-02" src="<%=basePath%>images/ea/office/contract/selectp/img_03.png"/>
                </div>
                <div class="div-img">
                    <img src="<%=basePath%>images/ea/office/contract/selectp/list_10.png"/>
                </div>
                <p>
                    林静雯
                </p>
            </li>--%>
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
        <p>提交中...</p>
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
    var sccid='<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';

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
        else if(c=="4"){
            document.location.replace("<%=basePath%>page/ea/main/office_ea/contract/selectRecent.jsp?docId="+docId+"&typee="+typee);

        }

    }
</script>
</html>

