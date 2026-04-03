<%@ page import="hy.ea.bo.CAccount" %>
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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/stampUpload.css"/>
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/stampUpload.js" type="text/javascript" charset="utf-8"></script>

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
            签约文件公章
        </li>
    </ul>
</header>
<form name="form" id="form" method="post" enctype="multipart/form-data">
    <input type="submit" name="submit" style="display: none;"/>
<div class="content">
    <div class="div-name">
        <label for="">印章名称</label>
        <input type="text"  placeholder="请填写您的名称"  value="${enterpriseStamp.stampName}" name="enterpriseStamp.stampName" id="stampName"/>
    </div>
    <div class="div-img clearfix">
        <div class="div-left">
            <label for="">上传印章图片</label>
            <input type="text" value="" disabled placeholder="180*180PX，透明，PNG，不留边缘" />
        </div>
        <input type="file" name="photo" id="sdfFile" value="" onchange="f_change(this);" accept="image/png">
        <img alt="图片" src="<%=basePath%>${enterpriseStamp.scanningAccessories ne null?enterpriseStamp.scanningAccessories:'images/ea/office/contract/stamp/img_09.png'}" id="imgSdf">
    </div>
    <div class="div-leixing clearfix">
        <div class="div-left">
            <label for="">印章类型</label>
            <input type="text" value="" disabled placeholder="请选择您的公章类型" />
        </div>
        <div class="div-right clearfix" id="div-leixing">
            <p class="p-leixing">${enterpriseStamp.stampType ne null ?enterpriseStamp.stampType:'印章类型'}</p>
            <input type="hidden" id="stampType"  name="enterpriseStamp.stampType" value="${enterpriseStamp.stampType}"/>
            <input type="hidden"   name="enterpriseStamp.enterpriseStampID"  value="${enterpriseStamp.enterpriseStampID}"/>
            <input type="hidden"  value = "${param.sccId}" name="sccId"/>
            <img src="<%=basePath%>images/ea/office/contract/stamp/img_02.png"/>
        </div>
    </div>
    <div class="div-name qzr-div">
        <label for="">签章人</label>
        <input type="hidden"  value="${enterpriseStamp.responsibleID}" name="enterpriseStamp.responsibleID" id="responsibleID"/>
        <input type="hidden"  name="message" value="11"/>

        <input type="text"  readonly placeholder="请填写签章人"  value="${enterpriseStamp.responsibleName}" name="enterpriseStamp.responsibleName" id="responsibleName"/>
    </div>
    <div class="div-bottom">
        <p class="submitAudit">
            提交
        </p>
    </div>
</div>
    <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
            framespacing="0" height="0"></iframe>
</form>
<!--选择-->
<div class="div-yinzhang">
    <div class="box">
        <ul class="gszlx">


            <li>
               企业公章
            </li>
            <li>
                财务专用章
            </li>
            <li>
                合同专用章
            </li>

            <li>
                法定代表人章
            </li>
            <li>
                发票专用章
            </li>
            <li>
                法人签名
            </li>
        </ul>

        <ul class="grzlx" style="display: none;">


            <li>
               个人印章
            </li>

        </ul>
    </div>
</div>
<!--表单提示-->
<div class="div-tingyong">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
        <div class="div-box">
            <p class="titlep">操作成功</p>
            <div class="clearfix">
                <p class="left close-tingyong">取消</p>
                <p class="right close-tingyong">确定</p>
            </div>
        </div>
    </div>
    </div>
</div>
<div class="iframecom" >
    <iframe id="iframe" src="<%=basePath%>page/ea/main/office_ea/contract/selectMember.jsp?typee=xr" width="100%" height="100%" frameborder="0"></iframe>
</div>
<!--表单提示-->
<div class="div-tingyong2 div-pro">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
        <div class="div-box">
            <p>身份信息尚未完善无法签约</p>
            <div class="clearfix">
                <p class="left close-zb">暂不</p>
                <p class="right close-edit">立即完善</p>
            </div>
        </div>
    </div>
</div>

<div class="div-tingyong2 div-pro1">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
        <div class="div-box">
            <p class="title-p"></p>
            <div class="clearfix">
                <p class="left close-zb">返回</p>
                <p class="right close-edits"></p>
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
var  companyID =  "<%=((CAccount)session.getAttribute("account")).getCompanyID()%>";
var  noauth="${noauth}";
var authState = "${authState}";

if(companyID!=null&&companyID!=""){
    $(".grzlx").hide();
    $(".gszlx").show();
    $(".div-leixing .p-leixing").text("印章类型");
    $(".qzr-div").show();
    if(authState!="02"){
           if(authState=="00"){
               $(".title-p").text("企业尚未认证，认证后才可以上传印章");
               $(".close-edits").text("立即去认证");

           }else if(authState=="01"){

               $(".title-p").text("企业认证审核中，审核通过后才能上传印章");
               $(".close-edits").text("立即查看");
           }else if(authState=="03"){

               $(".title-p").text("企业认证审核失败，审核通过后才能上传印章");
               $(".close-edits").text("立即查看");
           }else if(authState=="04"){

               $(".title-p").text("企业认证尚未审核完全，可联系工作人员处理");
               $(".close-edits").text("立即查看");
           }

           $(".div-pro1").show();
    }

}else{
    $(".grzlx").show();
    $(".gszlx").hide();
    $(".div-leixing .p-leixing").text("个人印章");
    $(".qzr-div").hide();
}

    $("#div-leixing").click(function(){
        $(".div-yinzhang").show();
    })
    $(".div-yinzhang li").click(function(){
        $(this).parents(".div-yinzhang").hide();
        $(this).siblings().removeClass("active");
        $(this).addClass("active");
        $(".p-leixing").text($.trim($(this).text()));
    })
    //JS file 图片 即选即得 显示
    //创建一个FileReader对象
    var reader = new FileReader();
    function f_change(file){
        var img = document.getElementById('imgSdf');
        //读取File对象的数据
        reader.onload = function(evt){
            //data:img base64 编码数据显示
            img.width  =  "100";
            img.height =  "100";
            img.src = evt.target.result;
        }
        reader.readAsDataURL(file.files[0]);
    }
</script>
</html>
