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
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base2.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/navigationBar.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/percardinfo.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/percardinfo.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="https://cdn.tailwindcss.com"></script>
    <title>&lrm;</title>
</head>
<body>

<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self" >

                <img  style="margin-top: 11px;margin-left: 5px" src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li>
            授权人员信息
        </li>
    </ul>
</header>

<div>
    <div class="flex flex-row"  style="height: 40px;text-align: center;">
        <p class="mt-2" style="font-weight: bold;margin-left: 5%;" onclick="alreadyEmpower(2,false)">已授权</p>
        <p class="mt-2" style="font-weight: bold;margin-left: 5%;" onclick="alreadyEmpower(1,false)">未授权</p>
        <p class="mt-2" style="font-weight: bold;margin-left: 5%;" onclick="togglePopup()">新增人员</p>
        <p class="mt-2" style="font-weight: bold;margin-left: 5%;" onclick="faceResult()">识别结果</p>
        <p class="mt-2" style="font-weight: bold;margin-left: 5%;" onclick="importPersonnel()">导入</p>
    </div>
    <div class="ml-1 mr-1">
        <input type="search" id="mySearch" name="q" placeholder="搜索...">
        <input id="mySearchSubmit" type="submit" onclick="searchInfo()" value="搜索">
    </div>
    <div>
        <table class="border-collapse border border-slate-400" style="width: 100%;text-align: center;">
            <thead>
            <tr style="height: 50px">
                <th class="border border-slate-300">图片</th>
                <th class="border border-slate-300">姓名</th>
                <th class="border border-slate-300">授权公司</th>
                <th class="border border-slate-300">状态</th>
            </tr>
            </thead>
        </table>
    </div>
    <div class="scrollable-div" id="scrollable-div" style="">
        <table class="border-collapse border border-slate-400" style="width: 100%;text-align: center;">
            <tbody id="empowerInfo">
            </tbody>
        </table>
    </div>
</div>
<div class="pc-box">
    <div class="div-box">
        <div class="footer div-bottom">
            <ul class="clearfix">
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_23.png" alt="">
                    </div>
                    <p>
                        消息
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_38.png" alt="">
                    </div>
                    <p>
                        通讯
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_08.jpg" alt="">
                    </div>
                    <p>
                        数字
                    </p>
                </li>
                <li class="active">
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_37.png" alt="">
                    </div>
                    <p>
                        5L5C
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_11.png" alt="">
                    </div>
                    <p>
                        我的
                    </p>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="popup" id="myPopup">
    <form id="form" method="post" enctype="multipart/form-data">
        <div class="flex flex-row" style="position: relative;">
            <label for="personImage" style="margin-top: 8px;">真实头像：</label>
            <img id="personImage" src="" class="relative  rounded-lg bg-red-100" style="width: 40px; height: 40px;margin-bottom: 10px;margin-left: 140px;">
            <input type="file" name="photoFiles" id="photoFiles" accept="image/jpg,image/png;capture=camera" onchange="myFile()" style="margin-left:180px;position: absolute;width: 6rem;height: 3rem;float:right;margin-right:-5rem;opacity: 0;">
        </div>
        <label for="personName">真实姓名：</label>
        <input id="personName" name="personName" type="text" class="border-2 border-rose-100" placeholder="请输入人员名字" style="width: 180px"><br><br>

        <label for="payMoney">缴费金额：</label>
        <input id="payMoney" name="payMoney" class="border-2 border-rose-100" placeholder="请输入缴费金额" style="width: 180px"><br><br>

        <label for="payStatus">缴费状态：</label>
        <select id="payStatus" name="payStatus" class="border-2 border-rose-100" style="width: 177px">
            <option class="border-2 border-rose-100"  value="2">未交费</option>
            <option class="border-2 border-rose-100"  value="1">已缴费</option>
            <option class="border-2 border-rose-100"  value="3">不用缴费</option>
        </select><br><br>
        <label for="notes">缴费备注：</label>
        <textarea id="notes" name="notes" class="border-2 border-rose-100" style="width: 177px"></textarea>
        <br><br>
        <label for="erId">授权场所：</label>
        <select id="erId" name="erId" class="border-2 border-rose-100" style="width: 177px;">
            <%--<s:iterator value="objectList" var="item">
                <option style="font-size: 12px;" class="border-2 border-rose-100"  value="${item[0]}">${item[1]}</option>
            </s:iterator>--%>
        </select><br><br>
        <div class="space-y-2" style="margin: auto;text-align: center;">
            <button type="button" onclick="togglePopup()">取消</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <button type="button" onclick="personSubmit()">确定</button>
        </div>
    </form>
</div>
<div class="empowerInfo" id="myEmpowerInfo" onclick="notShow()">

</div>
<div id="loading"></div>

<%--弹窗提示授权设备列表--%>
<div class="popupDevice" id="deviceList">
    <div>
        <input type="checkbox" id="checkAll" onclick="checkAll()">
        <label for="checkAll">全选/反选</label>
    </div>
    <div id="deviceInfo" style="margin-top: 20px;">

    </div>
    <div class="space-y-2" style="margin: auto;text-align: center;margin-top: 30px;">
        <button type="button" onclick="toggleDeviceList()">取消</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" onclick="deviceListSubmit()">确定</button>
    </div>
</div>
</body>
<script type="text/javascript">
    $("#loading").fadeOut();
    var basePath = "<%=basePath%>";
    var photo=0;
    var staffIdInfo="";
    var pageNumber = 1; // 初始化为第一页
    var pageCount=0;
    var pageSize=15;
    var timeNumber=100;
    var emoderStatenumber=0;
    var states=false;
    window.onload = function () {
        alreadyEmpower(0,false);
    };
    function personEmpower(){
        document.location.href = basePath+"ea/faceDevice/ea_findPersonEmpower.jspa";
    }
    function alreadyEmpower(emoderState,state){
        if(!state){
            pageNumber = 1;
        }
        states=state;
        emoderStatenumber=emoderState;
        $.ajax({
            url: basePath+"ea/faceDevice/sajax_ea_findPersonEmpowerInfo.jspa",
            type: "post",
            async: false,
            dataType: "json",
            data: {
                "emoderState": emoderState,
                "pageNumber": pageNumber,
                "pageSize": pageSize,
                "timeNumber": timeNumber
            },
            success: function (data) {
                resultsHTML(data);
                $("#loading").fadeOut();
            },
            error: function (data) {
                $("#loading").fadeOut();
                alert("查询错误"+data);
            }
        });
    }

    document.getElementById('scrollable-div').addEventListener('scroll', function() {
        var div = this;
        if (div.scrollHeight - div.scrollTop <= div.clientHeight) {
            if(pageNumber<pageCount){
                pageNumber=pageNumber+1;
                console.log("到达div底部，可以发起请求"+pageNumber+"===="+pageCount);
                alreadyEmpower(emoderStatenumber,true);
            }
        }
    });
    function resultsHTML(data){
        var resultsHTML="";
        var divp='';
        var json=JSON.parse(data);
        var list=[];
        $.each(json, function(key, value) {
            console.log(json)
            list= value["list"];
            pageCount=value["pageCount"];
            console.log(pageCount+"总页数")
        });
        for(var i=0;i<list.length;i++){
            var tr='';
            tr+='<tr id="'+list[i][0]+'">';
            tr+='<td onclick=showInfo("'+list[i][0]+'") class="border border-slate-300" style="width: 60px;"><div class="table-container p-1" style=""><img src="<%=basePath%>'+list[i][2]+'" class="relative rounded-lg bg-red-500" style="width: 50px; height: 50px;"></div></td>';
            tr+='<td onclick=showInfo("'+list[i][0]+'") class="border border-slate-300">'+list[i][1]+'</td>';
            tr+='<td onclick=showInfo("'+list[i][0]+'") class="border border-slate-300" style="width: 45%;"><p>'+list[i][4]+'</p></td>';
            tr+='<td class="border border-slate-300" style="width: 70px;">';
            if(list[i][3] == 1){
                tr+='<p style="font-weight: bold;" onclick=toggleDeviceList("'+list[i][0]+'","'+list[i][5]+'")>未授权</p>';

            }else{
                tr+='已授权';
            }
            tr+='</td>';
            tr+='</tr>';
            resultsHTML+=tr;
        }
        if(states){
            $('#empowerInfo').append(resultsHTML);
        }else{
            $('#empowerInfo').html(resultsHTML);
        }

    }
    function faceResult() {
        document.location.href = basePath + "page/ea/main/office_ea/contract/faceResult.jsp";
    }
    function togglePopup() {
        var popup = document.getElementById("myPopup"); // 获取弹窗元素

        if (popup.classList.contains('show')) {
            popup.classList.remove('show'); // 如果已经显示则移除 show 类名，使其隐藏
        } else {
            $.ajax({
                url: basePath+"ea/faceDevice/sajax_ea_findSiteInfo.jspa",
                type: "post",
                async: false,
                dataType: "json",
                success: function (data) {
                    var json=JSON.parse(data);
                    var list=json["SiteInfo"]
                    var html="";
                    for(var i=0;i<list.length;i++){
                        html+='<option value="'+list[i][0]+'">'+list[i][1]+'</option>';
                    }
                    $('#erId').html(html);
                },
                error: function (data) {
                    alert("查询错误"+data);
                }
            });
            popup.classList.add('show'); // 否则添加 show 类名，使其显示
        }
        var myEmpowerInfo = document.getElementById("myEmpowerInfo"); // 获取弹窗元素
        myEmpowerInfo.classList.remove('show'); // 如果已经显示则移除 show 类名，使其隐藏
    }
    function deviceListSubmit(){
        $("#loading").fadeIn();
        if(staffIdInfo!=""){
            var checkboxes = document.querySelectorAll('input[type=checkbox]');
            let list = [];
            checkboxes.forEach((checkbox) => {
                if (checkbox.checked == true) {
                    list.push(checkbox.value)
                }
            });
            var deviceIds=list.join(",");
            $.ajax({
                url: basePath+"ea/faceDevice/sajax_ea_empoder.jspa",
                type: "post",
                async: false,
                dataType: "json",
                data: {
                    "emoderState": 2,
                    "staffId": staffIdInfo,
                    "deviceIds": deviceIds
                },
                success: function (data) {
                    //隐藏当前数据
                    document.getElementById(staffIdInfo).style.display = "none"; // 获取弹窗元素
                    document.getElementById("deviceList").classList.remove('show');
                    $("#loading").fadeOut();
                },
                error: function (data) {
                    $("#loading").fadeOut();
                    alert("授权错误"+data);
                }
            });
        }else{
            alert("授权错误");
        }
    }
    function checkAll() {
        var checkboxes = document.querySelectorAll('input[type=checkbox]');
        checkboxes.forEach((checkbox) => {
            if (checkbox.checked == true) {
                checkbox.checked = false;
            } else {
                checkbox.checked = true;
            }
        });
    }
    function toggleDeviceList(staffId,siteId) {
        staffIdInfo=staffId;
        var popup = document.getElementById("deviceList"); // 获取弹窗元素
        if (popup.classList.contains('show')) {
            popup.classList.remove('show'); // 如果已经显示则移除 show 类名，使其隐藏
        } else {
            //查询授权的通道
            $.ajax({
                url: basePath+"ea/faceDevice/sajax_ea_findDeviceBySiteid.jspa",
                type: "post",
                async: true,
                dataType: "json",
                data: {
                    "erId": siteId
                },
                success: function (data) {
                    var json = JSON.parse(data);
                    var list = json["list"];
                    var html = "";
                    for (var i = 0; i < list.length; i++) {
                        html+='<label class="custom-checkbox"><input type="checkbox" value="' + list[i][0] + '"/><span class="checkmark"></span> </label>';
                        html+=list[i][1]+"</br>";
                    }
                    $('#deviceInfo').html(html);
                },
                error: function (data) {
                    alert("查询失败");
                }
            });
            popup.classList.add('show'); // 否则添加 show 类名，使其显示
        }

    }

    function personSubmit(){
        // 阻止表单默认提交行为
        event.preventDefault();
        if(photo==0){
            alert("请上传头像");
            return;
        }
        if($("#staffName").val()==""){
            alert("请输入姓名");
            $("#staffName").focus();
            return;
        }
        if($("#payMoney").val()==""){
            alert("请输入缴费金额，没有输入0");
            $("#payMoney").focus();
            return;
        }
        if($("#erId").val()==""){
            alert("请选择场所");
            $("#companyId").focus();
            return;
        }
        $("#loading").fadeIn();
        $("#form").attr("action",basePath+"ea/faceDevice/ea_savePersonEmpower.jspa?emoderState=1");
        $("#form").submit();
    }
    function myFile(){
        photo=1;
    }

    $('#photoFiles').change(function() {
        var file = this.files[0];
        var reader = new FileReader();
        reader.onload = function() {
            // 通过 reader.result 来访问生成的 DataURL
            var url = reader.result;
            setImageURL(url);
        };
        reader.readAsDataURL(file);
    });

    function importPersonnel(){
        document.location.href = document.location.href = basePath+"/page/ea/main/office_ea/contract/faceImportPersonnel.jsp";
    }

    var image = new Image();

    function setImageURL(url) {
        document.getElementById("personImage").src = url;
    }
    function searchInfo(){
        var companyName=$('#mySearch').val();
        document.location.href = document.location.href = basePath+"ea/faceDevice/ea_findPersonEmpower.jspa?emoderState=0&personName="+companyName;
    }
    function showInfo(staffKey) {
        //隐藏新增的div
        var popup = document.getElementById("myPopup"); // 获取弹窗元素
        popup.classList.remove('show'); // 如果已经显示则移除 show 类名，使其隐藏
        //操作展示信息的div
        var myEmpowerInfo = document.getElementById("myEmpowerInfo"); // 获取弹窗元素
        if (myEmpowerInfo.classList.contains('show')) {
            myEmpowerInfo.classList.remove('show'); // 如果已经显示则移除 show 类名，使其隐藏
            $('#myEmpowerInfo').html("");
        } else {
            showInfoAjax(staffKey);
            myEmpowerInfo.classList.add('show'); // 否则添加 show 类名，使其显示
        }
    }
    function showInfoAjax(staffKey){
        $.ajax({
            url: basePath+"ea/faceDevice/sajax_ea_findEmpowerById.jspa", // 替换为你的服务器端点
            type: 'GET',
            data: { staffKey : staffKey},
            success: function(data) {
                var json=JSON.parse(data);
                var resultsHTML="";
                $.each(json, function(key, value) {
                    for(var i=0;i<value.length;i++) {
                        resultsHTML += '<div><img class="imgClass" src="' + basePath + value[i][1] + '"></div>';
                        resultsHTML += '<div><b>真实姓名：</b>' + value[i][0] + '</div>';
                        var empowerState = value[i][12] == 1 ? "未授权" : "已授权"
                        resultsHTML += '<div><b>授权状态：</b>' + empowerState + '</div>';
                        var empowerStaffName = value[i][11] == null ? "未知授权人员" : value[i][11];
                        if (value[i][12] == 2) {
                            resultsHTML += '<div><b>授权人员：</b>' + empowerStaffName + '</div>';
                        }
                        var payNumber = value[i][2] == null ? "未知" : value[i][2] + "¥";
                        resultsHTML += '<div><b>缴费金额：</b>' + payNumber + '</div>';
                        var payStatus = value[i][3];
                        var payStatusStr = "";
                        if (payStatus == 1) {
                            payStatusStr = "已缴费";
                        } else if (payStatus == 2) {
                            payStatusStr = "未缴费";
                        } else {
                            payStatusStr = "无需缴费";
                        }
                        resultsHTML += '<div><b>缴费状态：</b>' + payStatusStr + '</div>';
                        var notes = value[i][4] == null ? "" : value[i][4];
                        resultsHTML += '<div><b>人员备注：</b>' + notes + '</div>';
                        var personTpye = value[i][5] == 1 ? "系统人员" : "临时人员";
                        resultsHTML += '<div><b>人员类型：</b>' + personTpye + '</div>';
                        //resultsHTML+='<div>三方平台Id：'+value[i][6]+'</div>';
                        resultsHTML += '<div><b>创建时间：</b>' + value[i][7] + '</div>';
                        resultsHTML += '<div><b>授权时间：</b>' + value[i][8] + '</div>';

                        var erName = value[i][13] == null ? "还未授权" : value[i][13];
                        resultsHTML += '<div><b>授权场地：</b>' + erName + '</div>';

                        var companyInfo = value[i][10] == null ? "还未授权" : value[i][10];
                        resultsHTML += '<div><b>所在公司：</b>' + companyInfo + '</div>';
                        resultsHTML += '</br><div>' +
                            '<button class="deletedButton" id="deletedInfo" onclick="deletedInfoById()">删除</button>' +
                            '<button class="deletedButton" >关闭</button>' +
                            '</div>';
                        resultsHTML += '<input type="hidden" id="deletedStaffKey" value="'+staffKey+'"/>';
                        resultsHTML += '<input type="hidden" id="empowerStaffId" value="'+value[i][6]+'"/>';
                    }
                });
                $('#myEmpowerInfo').html(resultsHTML);
            }
        });
    }

    function notShow() {
        myEmpowerInfo.classList.remove('show'); // 如果已经显示则移除 show 类名，使其隐藏
        $('#myEmpowerInfo').html("");
    }
    function deletedInfoById(){
        //删除信息
        var staffKey=$("#deletedStaffKey").val();
        var empowerStaffId=$("#empowerStaffId").val();
        $.ajax({
            url: basePath+"ea/faceDevice/sajax_ea_deletedEmpowerById.jspa",
            type: 'GET',
            data: { staffKey: staffKey,empowerStaffId:empowerStaffId},
            success: function(data) {
                $("#"+staffKey).hide();
            },
            error: function (data) {
                alert('删除失败');
            }
        });
    }
</script>
<style>
    .empowerInfo {
        display: none; /* 初始状态隐藏 */
        position: fixed;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        width: 95%;
        background-color: #fff;
        border: 1px solid #ccc;
        padding: 20px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
        border-bottom-right-radius: 10px;
        border-bottom-left-radius: 10px
    }
    .popup {
        display: none; /* 初始状态隐藏 */
        position: fixed;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        width: 88%;
        height: 430px;
        background-color: #fff;
        border: 1px solid #ccc;
        padding: 20px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
        border-bottom-right-radius: 10px;
        border-bottom-left-radius: 10px
    }
    .popupDevice {
        display: none; /* 初始状态隐藏 */
        position: fixed;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        width: 88%;
        background-color: #fff;
        border: 1px solid #ccc;
        padding: 20px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
        border-bottom-right-radius: 10px;
        border-bottom-left-radius: 10px
    }
    .show {
        display: block; /* 显示弹窗 */
    }
    #mySearch {
        border: 1px solid #0e8fe2; /* 设置边框为1像素，实线，蓝色 */
        border-radius: 5px; /* 设置边框圆角 */
        padding: 5px; /* 设置内边距 */
        width: 87%; /* 设置宽度 */
    }
    #mySearchSubmit{
        background: #0e8fe2;
        color:white;
        border-radius: 5px;
        padding: 5px;
        float: right;
    }
    textarea {
        vertical-align: middle;
        display: inline-block;
    }
    .imgClass{
        width: 60px;
        height: 60px;
        border: 1px solid rgba(0, 0, 0, 0.5);
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

    @keyframes spin {
        0% {
            transform: rotate(0deg);
        }
        100% {
            transform: rotate(360deg);
        }
    }
    .deletedButton{
        margin-left: 23%;
    }
    button{
        margin: auto;
        width: 70px;
        height: 30px;
        background: #F74C32;
        border-radius: 10px;
        color: white;
        font-size: 20px;
    }

    /* 隐藏原生复选框 */
    .custom-checkbox input[type="checkbox"] {
        display: none;
    }

    /* 创建自定义复选框样式 */
    .custom-checkbox .checkmark {
        display: inline-block;
        width: 20px; /* 复选框的宽度 */
        height: 20px; /* 复选框的高度 */
        background: #eee; /* 未选中状态下的背景色 */
        margin-right: 8px; /* 与文本的距离 */
        border-radius: 4px; /* 圆角 */
        position: relative;
    }

    /* 当复选框被选中时 */
    .custom-checkbox input[type="checkbox"]:checked + .checkmark {
        background: #2196F3; /* 选中状态下的背景色 */
    }
    /* 创建复选框内部的对勾 */
    .custom-checkbox .checkmark::after {
        content: "";
        position: absolute;
        left: 7px;
        top: 3px;
        width: 6px; /* 对勾的宽度 */
        height: 10px; /* 对勾的高度 */
        border: solid white; /* 对勾的颜色 */
        border-width: 0 2px 2px 0; /* 对勾的边框 */
        transform: rotate(45deg); /* 对勾的旋转角度 */
    }



    table, th, td {
        border: none;
        border-collapse: collapse;
        font-size: 10pt;
    }

    .scrollable-div {
        width: 100%; /* 设置div的宽度 */
        height: 600px; /* 设置div的高度 */
        overflow-y: scroll; /* 添加纵向滚动条 */
    }
</style>
</html>
