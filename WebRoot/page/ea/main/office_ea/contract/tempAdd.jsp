<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page language="java" import="hy.ea.bo.office.DocumentFileAttach"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java"
         import="java.util.*,com.zhuozhengsoft.pageoffice.*"
         %>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String fileType=request.getParameter("fileType");


    String docPath = request.getParameter("docPath");

%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/tempAdd.css"/>
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/tempAdd.js" type="text/javascript" charset="utf-8"></script>

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
            ${param.opr eq 'bd'?'本地上传':param.opr eq 'edit'?'在线编辑':'编辑查看'}
        </li>
    </ul>
</header>

<form name="form" id="form" method="post" enctype="multipart/form-data">
    <input type="submit" name="submit" style="display: none;"/>

<div class="content">
<c:if test="${param.isSet eq '0'}">
    <div class="div-leixing clearfix">
        <div class="div-left">
            <label for="">是否共享</label>
        </div>
        <div class="div-right clearfix" id="div-leixing">
            <p class="p-leixing">${param.sysSet eq null||param.sysSet eq ''||param.sysSet eq '01'?"否":"是"}</p>
            <input type="hidden" id="sysSet"  name="documentTemplate.sysSet" value="${param.sysSet eq null||param.sysSet eq ''?'01':param.sysSet}"/>


        </div>
    </div>
</c:if>
    <div class="div-name">
            <label for="">模板分类</label>
            <input type="text"  placeholder="请选择模板分类" readonly  id="specificTemplate" value="${param.templateTypeName}"/>

            <input type="hidden"  name="documentTemplate.temptId" id="temptId" value="${param.temptId}"/>
            <input type="hidden" id="fileType"  name="documentTemplate.fileType" value="${param.fileType}"/>
            <input type="hidden" id="templateId"  name="documentTemplate.templateId" value="${param.templateId}"/>
            <input type="hidden"  name="documentTemplate.module" value="${param.module}"/>
        <input type="hidden"  name="documentTemplate.contractType" value="${param.contractType}"/>
        <input type="hidden"  name="documentTemplate.contractTypeName" value="${param.fileShowName}"/>


    </div>

    <div class="div-name">
        <label for="">模板名称</label>
        <input type="text"  placeholder="请填写模板名称"  name="documentTemplate.fileShowName" id="fileShowName" value="${param.fileShowName}"/>
    </div>




    <c:if test="${param.opr eq 'bd'}">
    <div class="div-img clearfix">
        <div class="div-left">
            <label for="">上传模板文件</label>
            <input type="text" value="" disabled placeholder="支持doc/docx,xls/xlsx" />
        </div>
        <input type="file" name="image" id="sdfFile" value="" onchange="f_change(this);"  accept="application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" >
        <img alt="图片" src="<%=basePath%>images/ea/office/contract/uattach.png" id="imgSdf">
    </div>
    </c:if>

      <c:if test="${param.opr eq 'edit'||param.opr eq 'update'}">
        <div class="div-leixing clearfix">
            <div class="div-left">
                <label for="">文件格式</label>
          <c:if test="${param.opr eq 'edit'}">
                <input type="text" value="" disabled placeholder="请选择文件格式" />
          </c:if>

            </div>
            <div class="div-right clearfix" id="div-docType">
                <p class="p-docType">${param.fileType eq 'W'?"Word":"Excel"}</p>
        <c:if test="${param.opr eq 'edit'}">
                <img src="<%=basePath%>images/ea/office/contract/stamp/img_02.png"/>
        </c:if>
            </div>
        </div>
       <%-- <div class="div-img clearfix" id="setTemp" >
            <div class="div-left">
                <label for="">模板正文</label>

            </div>
            <p class="qczw" >
                <a id="text"  href="<%=PageOfficeLink.openWindow(request, basePath+"page/ea/main/office_ea/document/wordpc.jsp?docPath="+docPath+"&fileType="+fileType+"&isRead=2&stage=设置模板","width=1080px;height=900px;")%>">编辑正文</a>
            </p>

            <input type="hidden" name="documentTemplate.templatePath"
                   value="${param.docPath}" id="templatePath" />
        </div>--%>
          <div class="div-bottom" >
              <p id="contract-view" style="background-color: #fff;color: #008000;border: 1px solid #008000;" onclick="viewContent()"> 查看 </p>


          </div>
          <div class="div-bottom" >
              <p  style="background-color: #fff;color: #008000;border: 1px solid #008000;">
                  <a id="contract_edit"  style="color:#008000" href="<%=PageOfficeLink.openWindow(request, basePath+"page/ea/main/office_ea/document/wordpc.jsp?docPath="+docPath+"&fileType="+fileType+"&isRead=2&stage=设置模板","width=1080px;height=900px;")%>">编辑正文</a> </p>
          </div>
          <input type="hidden" name="documentTemplate.templatePath"
                 value="${param.docPath}" id="templatePath" />
    </c:if>
    <input type="hidden" name="documentTemplate.templatePath"
           value="${param.docPath}" id="templatePath" />
    <div class="div-bottom">
        <p class="saveDraft">
            保存
        </p>

    </div>

</div>

    <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
            framespacing="0" height="0"></iframe>
</form>
<!--选择-->
<div class="div-yinzhang">
    <div class="box">
        <ul>
            <li data-value="00">
               是
            </li>
            <li data-value="01">
              否
            </li>


        </ul>
    </div>
</div>

<div class="div-doctype">
    <div class="box">
        <ul>
            <li data-value="W">
                Word
            </li>
            <li  data-value="E">
                Excel
            </li>

        </ul>
    </div>
</div>

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
<div class="iframecom" >
    <iframe id="iframe" src="<%=basePath%>/ea/androiddoc/ea_getSelectTempType.jspa?module=${param.module}" width="100%" height="100%" frameborder="0"></iframe>
</div>
</body>
<script type="text/javascript">
var basePath = "<%=basePath%>";
var module = "${param.module}";
var opr = "${param.opr}";
var ifr="${param.ifr}";
var fileType = "${param.fileType}";
var fileName ="${param.fileShowName}";
var  docPath = "${param.docPath}";
var  templateId = "${param.templateId}"
$(function(){

    var u = navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

    if(!isAndroid&&!isiOS) {

       $("#setTemp").show();
        $("#text").text("编辑正文");

        if(opr=="update"){

            $("#text").text("已编辑");
        }

    }else{
        $("#setTemp").hide();
        $("#contract_edit").attr("href","javascript:showtip('"+fileType+"');");

    }







});

function viewContent(){

    var ulp = basePath
        + "ea/androiddoc/sajax_ea_getPdfTempView.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: false,
        dataType: "json",
        data: {
            templateId: templateId
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var pdfpath = member.pdfpath;
            document.location.href = basePath + "page/ea/main/office_ea/contract/pdfTempView.jsp?pdfpath="+pdfpath+"&title="+fileName;
        },
        error: function (data) {

            console.log("获取链接失败");
        }


    });

}
/**
 *
 * PDF无法编辑
 */
function showtip(fileType){
    if (fileType == "W") {
        $(".titlep").text("只能在PC端编辑Word文档");
    } else if (fileType == "E") {
        $(".titlep").text("只能在PC端编辑Excel文档");
    }else if (fileType == "P") {
        $(".titlep").text("不可编辑PDF格式文件");
    }
    $(".div-tingyong").show();


}

    //JS file 图片 即选即得 显示
    //创建一个FileReader对象
    var reader = new FileReader();
    function f_change(file){

        var img = document.getElementById('imgSdf');

        var name  = file.files[0].name;

        //读取File对象的数据
        reader.onload = function(evt) {
            //data:img base64 编码数据显示
            img.width = "100";
            img.height = "100";
            var ext = evt.target.result;
            var fileType = "M";
            if (name.indexOf("xls") != -1 || name.indexOf("xls") != -1) {
                ext = basePath + "images/ea/office/contract/excel-ext.png";
                fileType = "E";
            } else if (name.indexOf("doc") != -1 || name.indexOf("docx") != -1){

                ext = basePath + "images/ea/office/contract/word.png";
                fileType = "W";
            }
            else if (name.indexOf("pdf") != -1 || name.indexOf("PDF") != -1){
                ext = basePath + "images/ea/office/contract/PDF.png";
                fileType = "P";
            }
            img.src = ext;

            $("#fileType").val(fileType);
        }
        reader.readAsDataURL(file.files[0]);
    }




</script>
</html>
