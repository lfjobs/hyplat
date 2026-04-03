<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>
<html>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/pdfh5/css/pdfh5.css" />
<script type="text/javascript" src="<%=basePath %>js/jquery-2.1.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/pdfView.css"/>

<script src="<%=basePath%>js/ea/office_ea/contract/pdfView.js" type="text/javascript" charset="utf-8"></script>

<head>
    <title>文件详情</title>


</head>
<body>
<header>
    <ul class="clearfix">
        <li class="back-li">
            <a href="javascript:history.go(-1)" target="_self">

                <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li>
            ${param.title}
        </li>

        <li>
            <a style="color:#ffffff;" href="javascript:doPrint();">打印</a>
        </li>

    </ul>
</header>
<object
        id="viewerObject"  style=" margin: 0 auto;"
        data="<%=basePath%>js/pdfjs-3.7.107-dist/web/viewer.html?file=<%=basePath%>${param.pdfpath}"
        width="100%"
        type="text/html"
></object>

<c:if test='${param.isRead ne "1"}'>
<c:if test="${param.status eq 'I'||param.status eq 'R'}">

<section class="sec-tg">
    <ul class="clearfix">
        <li class="submitAudit">
            <img src="<%=basePath%>images/ea/office/contract/img_16.png"/>
           提交审批
        </li>
        <li class="passDraft">
            <img src="<%=basePath%>images/ea/office/contract/img_15.png"/>
            传阅他人
        </li>
    </ul>
</section>
</c:if>

<c:if test="${param.filetype eq 2}">
<c:if test="${param.status eq 'S'||param.status eq 'T'}">
<section class="sec-audit" >
    <ul class="clearfix">
        <li class="reject">
            <img src="<%=basePath%>images/ea/office/contract/img_16.png"/>
            审核驳回
        </li>
        <li class="adopt">
            <img src="<%=basePath%>images/ea/office/contract/img_15.png"/>
            同意至盖章签字
        </li>
        <li class="transfer">
            <img src="<%=basePath%>images/ea/office/contract/img_15.png"/>
            转交审批
        </li>
    </ul>
</section>
</c:if>
<c:if test="${param.status eq 'S'||param.status eq 'T'}">
<section class="sec-tg" style="display:none;">
    <ul class="clearfix">
        <li>
            <img src="<%=basePath%>images/ea/office/contract/zj.png"/>
            转自己盖章签字
        </li>
        <li>
            <img src="<%=basePath%>images/ea/office/contract/tr.png"/>
            转他人盖章签字
        </li>
    </ul>
</section>
</c:if>
<c:if test="${param.status eq 'A'}">
<section class="sec-audit sec-four">
    <ul class="clearfix">
        <li class="stampReject">
            <img src="<%=basePath%>images/ea/office/contract/img_16.png"/>
            驳回
        </li>
        <li class="toStamp">
            <img src="<%=basePath%>images/ea/office/contract/img_15.png"/>
           盖章签字
        </li>
        <li class="transferStamp">
            <img src="<%=basePath%>images/ea/office/contract/img_15.png"/>
            转交他人
        </li>
        <li class="transferPublish">
            <img src="<%=basePath%>images/ea/office/contract/img_15.png"/>
            至分发人
        </li>
    </ul>
</section>
</c:if>
<c:if test="${param.status eq 'A'}">
<section class="sec-tg" style="display:none;">
    <ul class="clearfix">
        <li class="transferStamp">
        <img src="<%=basePath%>images/ea/office/contract/img_16.png"/>
        转交他人盖章签字
    </li>
        <li class="transferPublish">
            <img src="<%=basePath%>images/ea/office/contract/img_15.png"/>
            转至分发人
        </li>
    </ul>
</section>
</c:if>

<c:if test="${param.status eq 'P'}">
<section class="sec-zf">
    <p>
        分发文件
    </p>
</section>
</c:if>
<c:if test="${param.status eq 'O'}">

<section class="sec-tg">
    <ul class="clearfix">
        <li class="read-zf">
            <img src="<%=basePath%>images/ea/office/contract/img_16.png"/>
           转发文件
        </li>
        <li class="read-share">
            <img src="<%=basePath%>images/ea/office/contract/img_15.png"/>
          共享文件
        </li>
    </ul>
</section>
</c:if>
</c:if>
</c:if>
<div class="div-zffs seal" style="display: none;">
    <div class="box">
        <p>
            选择盖章人员
            <img id="inp-close" src="<%=basePath%>images/ea/office/contract/img_03.png"/>
        </p>
        <ul>
            <li class="clearfix">
                <div>
                    <img src="<%=basePath%>images/ea/office/contract/zj.png"/>
                </div>
                <p>转自己盖章</p>
                <div class="sex">
                    <input id="female-1" type="radio" name="pay"  checked="checked" class="input-male" data-index="0">
                    <span class="female-custom active"></span>
                </div>
            </li>
            <li class="clearfix">
                <div>
                    <img src="<%=basePath%>images/ea/office/contract/tr.png"/>
                </div>
                <p>转他人盖章</p>
                <div class="sex">
                    <input id="female-2" type="radio" name="pay" class="input-male" data-index="1">
                    <span class="female-custom"></span>
                </div>
            </li>

        </ul>
        <section>
            <input type="button"  id="inp-yes" value="确定提交" />
        </section>
    </div>
</div>
<div class="div-zffs share" style="display: none;">
    <div class="box">
        <p>
            选择共享范围
            <img id="share-close" src="<%=basePath%>images/ea/office/contract/img_03.png"/>
        </p>
        <ul>
            <li class="clearfix">
                <div>
                    <img src="<%=basePath%>images/ea/office/contract/gs.png"/>
                </div>
                <p>当前公司</p>
                <div class="sex">
                    <input id="c" type="radio" name="pay"  checked="checked" class="input-share" data-value="current">
                    <span class="female-custom active"></span>
                </div>
            </li>
            <li class="clearfix">
                <div>
                    <img src="<%=basePath%>images/ea/office/contract/jt.png"/>
                </div>
                <p>集团公司</p>
                <div class="sex">
                    <input id="c-2" type="radio" name="pay" class="input-share" data-value="group">
                    <span class="female-custom"></span>
                </div>
            </li>

        </ul>
        <section>
            <input type="button"  id="inp-share" value="确定共享" />
        </section>
    </div>
</div>

<!--表单提示-->
<div class="div-tingyong div-tip">
    <div class="box">
        <p>温馨提示</p>
        <div class="div-box">
            <p class="titlep">操作成功</p>
            <div class="clearfix">
                <p class="left close-tingyong">取消</p>
                <p class="right close-tingyong">确定</p>
            </div>
        </div>
    </div>
</div>
<!--表单提示-->
<div class="div-tingyong div-pro">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
        <div class="div-box">
            <p>身份信息尚未完善无法签约</p>
            <div class="clearfix">
                <p class="left close-tingyong">暂不</p>
                <p class="right close-edit">立即完善</p>
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


<script type="text/javascript">
    var module = "<%=session.getAttribute("module")%>"
    var sccid = "<%=session.getAttribute("module")%>";
    var pdfpath = "${param.pdfpath}";
    var  docId = "${param.docId}";
    var status = "${param.status}";
    var basePath = "<%=basePath%>";


    window.onload =  function () {
        $(function () {
            $("#viewerObject").attr("height",$(window).height());


        })
        var clientWidth = document.documentElement.clientWidth;
        if(clientWidth>=960){

            $("body").append("<iframe style='display:none' id='printIframe' src=\'"+basePath+pdfpath+"\'></iframe>");

        }



    }
    function doPrint(){
        var u = navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端


        if(isAndroid==true||isiOS==true){
            alert("请在电脑端连接打印机打印");

        }else{
            $("#printIframe")[0].contentWindow.print();
        }


    }

</script>


</body>
</html>
