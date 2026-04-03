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
    <title>个人身份证认证</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/percardinfo.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/percardinfo.js" type="text/javascript" charset="utf-8"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/exif-js/2.3.0/exif.min.js"></script>
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
            个人身份证认证
        </li>
    </ul>
</header>
<form name="form" id="form" method="post" enctype="multipart/form-data">
    <div class="content">
        <div class="div-name">
            <label for="">真实姓名：</label>
            <input onchange="triggerInfo()" type="text"  placeholder="请填写真实姓名"   name = "staff.realname" id="realname" value="${staff.realname}"  <c:if test="${param.op eq 'view'}"> </c:if>/>
            <input type="hidden"    name = "staff.staffID" value="${staff.staffID}"/>
        </div>
        <div class="div-name">
            <label for="">身份证号码：</label>
            <input onchange="triggerInfo()" type="text"  placeholder="填写或扫描身份证号码"  name="staff.staffIdentityCard" id="staffIdentityCard" value="${staff.staffIdentityCard}"  <c:if test="${param.op eq 'view'}"> </c:if>/>
            <div class="popup" id="popup" <c:if test="${param.op eq 'view'}"> style="display:none;"</c:if>>
                <img id="photoPositive" src="<%=basePath%>images/sm.jfif" class="relative  rounded-lg bg-red-100" style="width: 25px; height: 25px;">
                <input class="photoPositiveInfo" type="file" id="photoPositiveInfo" accept="image/jpg,image/png;capture=camera" style="margin-left:-50px;position: absolute;width: 6rem;height:3rem;float:right;margin-right:-2rem;opacity: 0;">
            </div>
        </div>
        <div class="div-name" style="display: flex;"><label for="">上传身份证照片：</label></div>
        <div class="div-name" style="display: flex;gap: 12px">
            <div style="display: inline-block; width: 60%;height: 130px;" id="frontCard" class="panel">
                <div class="panel-footer">
                    <c:if test="${staffCard.cardFrontUrl eq null}">
                        <img  id="IDCardPhotoFront" src="<%=basePath%>images/WFJClient/pc/newimg/ZhengMian.jpeg" style="width: 100%;height: 130px; "></c:if>
                    <c:if test="${staffCard.cardFrontUrl ne null}">
                        <img  id="IDCardPhotoFront" src="<%=basePath%>${staffCard.cardFrontUrl}" style="width: 100%;height: 130px; "></c:if>
                    <input type="file" name="fileFront" id="fileFront" accept="image/jpg,image/png;capture=camera" style="display:none;">
                </div>
            </div>
            <div style="display: inline-block; width: 60%;height: 130px;" id="reverseSideCard" class="panel">
                <div class="panel-footer">
                        <c:if test="${staffCard.cardReverseUrl eq null}">
                            <img id="IDCardPhotoReverseSide" src="<%=basePath%>images/WFJClient/pc/newimg/FanMian.jpeg" style="width: 100%;height: 130px; "></c:if>
                        <c:if test="${staffCard.cardReverseUrl ne null}">
                            <img id="IDCardPhotoReverseSide" src="<%=basePath%>${staffCard.cardReverseUrl}" style="width: 100%;height: 130px; "></c:if>
                    <input type="file" name="fileReverseSide" id="fileReverseSide" accept="image/jpg,image/png;capture=camera" style="display:none;">
                </div>
            </div>
        </div>
        <div class="div-button"><button type="button" onclick="submitSave()">保存</button></div>
    </div>
    <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
            framespacing="0" height="0"></iframe>
</form>

<div id="loading"></div>

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
    var toSubmit=false;
    $('#photoPositiveInfo').change(function() {
        $("#loading").fadeIn();
        var formData = new FormData();
        var fileInput = document.getElementById('photoPositiveInfo');
        var img = document.getElementById('photoPositiveInfo');
        var file = fileInput.files[0];
        //correctOrientation(file);
        formData.append('photoPositiveInfo', file);
        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'ea/mycenter/sajax_ea_getIdCardInfo.jspa', true);
        xhr.timeout = 50000;//10000毫秒
        xhr.onload = function() {
            if (this.status == 200) {
                const data=JSON.parse(this.responseText);
                console.log(data);
                if(data!=null){
                    const dataJSON = JSON.parse(data);
                    $("#staffIdentityCard").val(dataJSON.cardNumber)
                    $("#realname").val(dataJSON.name)
                }else{
                    alert("没有获取到任何信息，请手动输入！")
                }
            } else {
                alert("获取失败，请手动输入！")
            }
            $("#loading").fadeOut();
        };
        xhr.ontimeout = function() {
            $("#loading").fadeOut();
            alert("自动获取失败，请手动输入！")
        };
        xhr.send(formData);
        var reader = new FileReader();
        reader.onload = function() {
            // 通过 reader.result 来访问生成的 DataURL
            var url = reader.result;
            document.getElementById("photoPositive").src = url;
        };
        reader.readAsDataURL(file);
    });
    //身份证正面
    document.getElementById('frontCard').addEventListener('click', function() {
        document.getElementById('fileFront').click();
    });
    document.getElementById('fileFront').addEventListener('change', function(event) {
        //开始上传并将图片显示到图片框中
        var file = this.files[0];
        var reader = new FileReader();
        reader.onload = function() {
            // 通过 reader.result 来访问生成的 DataURL
            document.getElementById("IDCardPhotoFront").src = reader.result;
        };
        toSubmit=true;
        reader.readAsDataURL(file);
        uploadPhotoFront();
    });
    //身份证反面
    document.getElementById('reverseSideCard').addEventListener('click', function() {
        document.getElementById('fileReverseSide').click();
    });
    document.getElementById('fileReverseSide').addEventListener('change', function(event) {
        //开始上传并将图片显示到图片框中
        var file = this.files[0];
        var reader = new FileReader();
        reader.onload = function() {
            // 通过 reader.result 来访问生成的 DataURL
            document.getElementById("IDCardPhotoReverseSide").src = reader.result;
        };
        toSubmit=true;
        reader.readAsDataURL(file);
    });

    function submitSave(){
        if(!toSubmit){
            alert("没有任何修改，无需提交");
            return false;
        }
        var img = document.getElementById('personImage');
        if ($.trim($("#realname").val()) == "") {
            $(".div-tingyong").show();
            $(".titlep").text("请填写真实姓名");
            return false;
        }

        if($.trim($("#staffIdentityCard").val())==""){
            $(".div-tingyong").show();
            $(".titlep").text("请填写身份证号");
            return false;
        }

        $("#form").attr("target", "hidden").attr("action",
            basePath + "ea/mycenter/ea_editInfo.jspa");
        $("#loading").fadeIn();
        $("#form").submit();
        window.location.href=basePath+"/ea/mycenter/ea_findMyAuthentication.jspa";
        //window.history.go(-1);
    }

    function uploadPhotoFront(){
        var form = document.getElementById('form');
        form.setAttribute("target", "hidden");
        form.setAttribute("action", basePath + "ea/mycenter/ea_uploadPhotoFront.jspa");
        var request = new XMLHttpRequest();
        request.open('POST', basePath + "ea/mycenter/ea_uploadPhotoFront.jspa");
        request.send(new FormData(form));
        request.onload = function() {
            if (request.status == 200) {
                window.location.href=basePath+"ea/mycenter/ea_getInfo.jspa?op=view";
            }
        };
        request.onerror = function() {
        };
    }

    function triggerInfo(){
        toSubmit=true;
    }
    $("#loading").fadeOut();
</script>
<style>
    .
    .popup {
        position: absolute; right: 1%;
        bottom: 10px;
        right: 10px;
    }
    #loading {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        z-index: 9999;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    #loading:before {
        content: "";
        width: 30px;
        height: 30px;
        border: 3px solid #fff;
        border-top-color: transparent;
        border-radius: 50%;
        animation: spin 1s ease-in-out infinite;
    }
    .panel{
        position: relative;
    }

    .panel::before {
        position: absolute;
        top: -3px;
        left: -3px;
        width: 10px;
        height: 10px;
        border-left: 2px solid #02a6b5;
        border-top: 2px solid #02a6b5;
        content: "";
    }
    .panel::after {
        position: absolute;
        top:-3px;
        right: -3px;
        width: 10px;
        height: 10px;
        border-right: 2px solid #02a6b5;
        border-top: 2px solid #02a6b5;
        content: "";
    }
    .panel .panel-footer {
        position: absolute;
        bottom: 0;
        left: 0;
        width: 100%;
    }
    .panel .panel-footer::before {
        position: absolute;
        bottom: -3px;
        left: -3px;
        width: 10px;
        height: 10px;
        border-left: 2px solid #02a6b5;
        border-bottom: 2px solid #02a6b5;
        content: "";
    }
    .panel .panel-footer::after {
        position: absolute;
        bottom: -3px;
        right: -3px;
        width: 10px;
        height: 10px;
        border-right: 2px solid #02a6b5;
        border-bottom: 2px solid #02a6b5;
        content: "";
    }
    @keyframes spin {
        0% {
            transform: rotate(0deg);
        }
        100% {
            transform: rotate(360deg);
        }
    }
</style>
</html>
