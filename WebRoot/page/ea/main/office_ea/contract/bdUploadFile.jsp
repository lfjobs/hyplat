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

    String poe=request.getParameter("poe");
    if(poe!=null&&!poe.equals("")) {
        DocumentFileAttach   attach = (DocumentFileAttach)request.getAttribute("docFileAttach");
        if(attach!=null){
            fileType = attach.getFileType();
            docPath =  attach.getFilePath();
        }

    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/bdUploadFile.css"/>
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/bdUploadFile.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>/js/My97DatePicker/WdatePicker.js"></script>
    <link href="<%=basePath%>js/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css">
    <title>&lrm;</title>
</head>
<body>
<header>
    <ul class="clearfix">
        <li class="back-li">
            <a onclick="javascript: window.history.go(-1);return false;"  target="_self">

            <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li>
            ${param.opr eq 'bd'?'本地上传':'在线编辑'}
        </li>
    </ul>
</header>
<form name="form" id="form" method="post" enctype="multipart/form-data">
    <input type="submit" name="submit" style="display: none;"/>

<div class="content">
    <div class="div-leixing clearfix gry">
        <div class="div-left">
            <label for="">文书类别</label>
            <%--<input type="text" value="" disabled placeholder="请选择文书合同类别" />--%>
        </div>
        <div class="div-right clearfix" id="div-ws">
            <p class="wlb">
                <img src="<%=basePath%>images/ea/office/contract/selectp/dxseleted.png" class="docyxz">
                <img src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png" class="ddc" style="display:none;"><label class="ddcl">文书</label>
                <img src="<%=basePath%>images/ea/office/contract/selectp/dxseleted.png" class="ccyxz" style="display:none;">
                <img src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png" class="ccc"><label class="cccl">合同</label>
            </p>
            <%--<p class="p-ws">${document.module=="contract"?"合同":"文书"}</p>--%>
            <input type="hidden" id="module"  name="document.module" value="${document.module eq null?'doc':'contract'}"/>

            <%--//   <img src="<%=basePath%>images/ea/office/contract/stamp/img_02.png" id="img-ws"/>--%>

        </div>
    </div>
    <c:if test="${param.opr eq 'edit'}">
        <div class="div-name">
            <label for="">文件模板</label>
            <input type="text"  placeholder="请选择文件模板" value="${param.tempName eq null ||param.tempName eq ""?documentTemplate.fileShowName:param.tempName}" readonly  id="specificTemplate" />

            <input type="text" style="display:none;" value="${param.specificTemplateID eq null ||param.specificTemplateID eq ""?documentTemplate.templateId:param.specificTemplateID}" name="document.specificTemplate" />


        </div>
    </c:if>

    <div class="div-name">
        <label for="">文件标题</label>
        <input type="text"  placeholder="请填写文件标题"  name="document.title" id="title" value="${document.title}"/>
        <input type="hidden" id="companyID"  name="companyID" value="${param.companyID}"/>

        <input type="hidden" id="staffID"  name="staffID" value="${param.staffID}"/>
        <input type="hidden"  name="document.docId" value="${document.docId}" id="docId"/>
    </div>
    <%--<div class="div-name">--%>
        <%--<label for="">主题词</label>--%>
        <%--<input type="text"  placeholder="请填写主题词"   name="document.theme" id="theme" value="${document.theme}"/>--%>


    <%--</div>--%>

    <div class="div-leixing clearfix">
        <div class="div-left">
            <label for="">文件缓急</label>
            <%--<input type="text" value="" readonly placeholder="请选择文件缓急类型" class="hj"/>--%>
        </div>
        <div class="div-right clearfix" id="div-leixing">
            <p class="p-leixing">普通</p>
            <input type="hidden" id="emergencyType"  name="document.emergencyType" value="${document.emergencyType}"/>
            <input type="hidden"  value = "${param.sccId}" name="sccId"/>
            <img src="<%=basePath%>images/ea/office/contract/stamp/img_02.png"/>
            <input type="hidden" id="fileType"  name="docFileAttach.fileType" value="${param.fileType eq null||param.fileType eq ''?docFileAttach.fileType:param.fileType}" />
            <input type="hidden" name="docFileAttach.fileId"
                   value="${docFileAttach.fileId}" id="fileId" />


        </div>
    </div>
    <div class="div-leixing clearfix">
        <div class="div-left">
            <label for="">公文类型</label>
            <%--<input type="text" value="" readonly placeholder="请选择公文类型" class="lx"/>--%>
        </div>
        <div class="div-right clearfix" id="div-docType">
            <p class="p-docType">董事长会议决定文件</p>
            <input type="hidden" id="docType"  name="document.docType" value="${document.docType}"/>
            <img src="<%=basePath%>images/ea/office/contract/stamp/img_02.png"/>

        </div>
    </div>

    <div class="div-name docNum" style="display:none;">
        <label for="">正式编号</label>
        <p>

        <input type="text"  placeholder="请填写"  name="document.numWord" value="${document.numWord}"  id="numWord"/>字【<span class="year">${document.numTime}</span>】第<input type="text" readonly  style="width:1rem;" name="document.numCode" /> 号
            <input type="hidden" id="docNum" name="document.docNum"
                   value="${document.docNum}" />
            <input type="hidden" id="numTime" name="document.numTime"
                   value="${document.numTime}"  />
        </p>
    </div>


    <div class="div-name jyf-div contract">
        <label for="">甲方</label>
        <label for="">公司</label>  <input type="text"  placeholder="甲方公司若为个人可不填写"  name="document.partyAName" id="partyAName" value="${document.partyAName}" autocomplete="off"/>
        <input type="hidden" name="document.partyA" id="partyA" value="${document.partyA}">

        <label for="">责任人</label>  <input type="text"  placeholder="甲方责任人"  name="document.partyAstaffnames" id="partyAstaffnames" value="${document.partyAstaffnames}" autocomplete="off"/>
        <input type="hidden" name="document.partyAstaff" id="partyAstaff" value="${document.partyAstaff}">

        <label for="">身份证号</label>  <input type="text"  placeholder="甲方身份证号"  name="document.staffIdentityCardA" id="staffIdentityCardA" value="${document.staffIdentityCardA}" autocomplete="off"/>

    </div>

    <div class="div-name jyf-div contract">
        <label for="">乙方</label>
        <label for="">公司</label>  <input type="text"  placeholder="乙方公司若为个人可不填写"  name="document.partyBName" id="partyBName" value="${document.partyBName}" autocomplete="off"/>
        <input type="hidden" name="document.partyB" id="partyB" value="${document.partyB}">

        <label for="">责任人</label>  <input type="text"  placeholder="乙方责任人"  name="document.partyBstaffnames" id="partyBstaffnames" value="${document.partyBstaffnames}" autocomplete="off"/>
        <input type="hidden" name="document.partyBstaff" id="partyBstaff" value="${document.partyBstaff}">

        <label for="">身份证号</label>  <input type="text"  placeholder="乙方身份证号"  name="document.staffIdentityCardB" id="staffIdentityCardB" value="${document.staffIdentityCardB}" autocomplete="off"/>

    </div>
    <div class="div-name time-Div contract">
        <label for="">有效期</label>
        <p>

            从<input type="text"  autocomplete="off"  onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', onpicked:function(){endDate.focus();}})"  placeholder="开始时间"  name="document.startValidity" value="${fn:substring(document.startValidity,0,10)}" id="startDate"/>至<input type="text"   id="endDate" onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'startDate\')}'})" name="document.endValidity"  autocomplete="off" value="${fn:substring(document.endValidity,0,10)}" placeholder="结束时间" />

        </p>
    </div>

    <c:if test="${param.opr eq 'bd'&&param.poe ne 'edit'}">
    <div class="div-img clearfix">
        <div class="div-left div-qswj">
            <label for="">上传签署文件</label>
            <input type="text" value="" disabled placeholder="支持doc/docx,xls/xlsx,pdf" />
        </div>
        <input type="file" name="image" id="sdfFile" value="" onchange="f_change(this);"  accept="application/pdf,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" >
        <img alt="图片" src="<%=basePath%>images/ea/office/contract/uattach.png" id="imgSdf">
    </div>
    </c:if>

    <c:if test="${param.opr eq 'edit'||param.poe eq 'edit'}">
        <div class="div-img clearfix">
            <div class="div-left">
                <label for="">文件正文</label>

            </div>
            <p class="qczw">
                <a id="text"  href="<%=PageOfficeLink.openWindow(request, basePath+"page/ea/main/office_ea/document/wordpc.jsp?docPath="+docPath+"&fileType="+fileType+"&isRead=2&stage=拟稿","width=1080px;height=900px;")%>">起草正文</a>

            </p>
            <p class="qczwed" style="display:none;">
            <a id="filename"   href="<%=PageOfficeLink.openWindow(request, basePath+"page/ea/main/office_ea/document/wordpc.jsp?docPath="+docPath+"&fileType="+fileType+"&isRead=2&stage=拟稿","width=1080px;height=900px;")%>"></a>
            </p>
            <input type="hidden" name="docFileAttach.filePath"
                   value="${param.docPath}" id="filepath" />
        </div>
    </c:if>
    <div class="div-bottom first-bottom">
        <p class="saveDraft">
            保存草稿
        </p>

    </div>

    <div class="div-bottom">

        <p class="passDraft">
            保存并传阅草稿
        </p>
    </div>
    <div class="div-bottom last-bottom">

        <p class="submitAudit">
            保存并提交审批
        </p>
    </div>

    <section class="sec-ul">
        <p class="title-p">请在以下搜索结果进行选择</p>
        <ul class="ul-list">

        </ul>
    </section>
</div>

    <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
            framespacing="0" height="0"></iframe>
</form>


<!--选择-->
<div class="div-yinzhang">
    <div class="box">
        <ul>
            <li data-value="p">
               普通
            </li>
            <li data-value="j">
                急件
            </li>
            <li data-value="t">
                特急
            </li>

        </ul>
    </div>
</div>
<!--公文类型-->

<div class="div-doctype">
    <div class="box">
        <ul>
            <li data-vaue="aa">
                董事会会议决定文件
            </li>
            <li  data-vaue="bb">
                董事长办公室文件
            </li>
            <li  data-vaue="cc">
                总裁办公室文件
            </li>
            <li  data-vaue="dd">
                总部人事处文件
            </li>
            <li  data-vaue="ee">
                总部办公室文件
            </li>
            <li  data-vaue="ff">
                总部财务处文件
            </li>
            <li  data-vaue="gg">
                总部教务(生产)处文件
            </li>
            <li  data-vaue="hh">
                总部营销处文件
            </li>
            <li  data-vaue="ii">
                总部服务(创收)平台
            </li>
            <li  data-vaue="jj">
                总部教务部文件
            </li>
        </ul>
    </div>
</div>
<%--<!--表单提示-->--%>
<%--<div class="div-tingyong">--%>
    <%--<div class="box">--%>
        <%--<p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>--%>
        <%--<div class="div-box">--%>
            <%--<p class="titlep">您确定要停用吗？</p>--%>
            <%--&lt;%&ndash;<div class="clearfix">&ndash;%&gt;--%>
                <%--&lt;%&ndash;<p class="right close-tingyong">确定</p>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>


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
<div class="loading">
    <div class="div-box">
    <img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/selectp/loading2.gif" alt="" />
    <p>保存中...</p>
        </div>
</div>
</body>
<script type="text/javascript">
var basePath = "<%=basePath%>";
var module = "<%=session.getAttribute("module")%>";

var typee = "";
var opr = "${param.opr}";//用于区分是本地上传还是在线编辑 bd  edit
var poe = "${param.poe}";//用于区分是否是编辑状态edit
var trans = "${param.trans}";
if(trans==1){
$("#fileId").val("")

$("#docId").val("")


}
//如果是编辑状态
if(poe=="edit"){
  var m = "${document.module}";
    if(m=="contract"){
        $(".contract").show();



        $(".ddc").show();
        $(".docyxz").hide();

        $(".ccc").hide();
        $(".ccyxz").show();


    }else{
        $(".contract").hide();

        $(".ddc").hide();
        $(".docyxz").show();

        $(".ccc").show();
        $(".ccyxz").hide();

    }

    $("#img-ws").hide();

}else{//如果是第一次上传状态
    if(module=="contract"){
        $(".contract").show();
    }else{
        $(".contract").hide();//如果是doc 或者空字符串 都不显示


    }


}

if(module==""){
    $(".gry").show();

}else{

    $(".gry").hide();
    $("#module").val(module);
}

var fileShowName = "${docFileAttach.fileShowName}";
var ext = "${docFileAttach.ext}";
var fileType = "${docFileAttach.fileType}";
var rz = "${param.rz}";

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
