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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/percardinfo.css"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <title>&lrm;</title>
</head>
<body>

<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self">
                <img  style="margin-top: 11px;margin-left: 5px"  src="<%=basePath%>images/ea/office/contract/stamp/return.png">
            </a>
        </li>
        <li>
            设备信息
        </li>
    </ul>
</header>

<div class="table-container p-2">
    <div class="space-y-2">
        <div style="width: 100%">
            <a onclick="togglePopup()" style="margin-left: 0%">设备绑定</a>
            <a onclick="personEmpower()" style="margin-left: 5%">人员授权</a>
        </div>
        <div>
            <input type="search" id="mySearch" name="q" placeholder="搜索...">
            <input id="mySearchSubmit" type="submit" onclick="searchInfo()" value="搜索">
        </div>
        <s:iterator value="pageForm.list" var="item">
            <div class="bg-gray-100 flex flex-row space-x-2">
                <div class="relative rounded-lg bg-red-500" style="width: 70px; height: 70px;">
                    <div class="absolute right-0 top-0 bg-green-500 rounded-full" style="width: 12px; height: 12px;"></div>
                </div>
                <div class="grow space-y-1">
                    <div class="text-sm">名称：${item[1]}</div>
                    <div class="text-sm">序列号：${item[2]}</div>
                    <div class="text-sm">所属公司：${item[3]}</div>
                </div>
            </div>
        </s:iterator>
    </div>
</div>
<div class="popup" id="myPopup">
    <ul class="clearfix"></ul>
    <form id="form" method="post" enctype="multipart/form-data">
        <div class="space-y-2" style="margin: auto;text-align: center;">
            <button style="width: 50px;" class="bg-gray-100 space-x-2" type="button" onclick="togglePopup()">取消</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <button style="width: 50px;" class="bg-gray-100 space-x-2" type="button" onclick="personSubmit()">保存</button>
        </div><br>
        <label for="deviceName">设备名称：</label>
        <input id="deviceName" name="deviceName" type="text" class="border-2 border-rose-100" placeholder="请输入设备名称" style="width: 180px"><br><br>

        <label for="deviceSN">序&nbsp;列&nbsp;号：&nbsp;</label>
        <input id="deviceSN" name="deviceSN" class="border-2 border-rose-100" placeholder="请输入设备序列号" style="width: 180px"><br><br>
        <label for="inoutFlag">出入标识：</label>
        <select id="inoutFlag" name="inoutFlag" class="border-2 border-rose-100" style="width: 177px">
            <option class="border-2 border-rose-100"  value="1">进</option>
            <option class="border-2 border-rose-100"  value="2">出</option>
        </select><br><br>
        <label for="companyId">绑定公司：</label>
        <input id="companyId" name="companyId" class="border-2 border-rose-100" autocomplete="off" placeholder="请输入公司名称" style="width: 180px">
        <br><br>
        <label for="erId">绑定场所：</label>
        <select id="erId" name="erId" class="border-2 border-rose-100" style="width: 177px">

        </select>

        <div id="results" class="companyIdInfo">
            <!-- 选项将被显示在这里 -->
        </div>

    </form>
</div>

</body>
<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=basePath%>js/ea/office_ea/contract/percardinfo.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    function personEmpower(){
        document.location.href = basePath+"page/ea/main/office_ea/contract/faceEmpower.jsp";
    }
    function togglePopup() {
        var popup = document.getElementById("myPopup"); // 获取弹窗元素

        if (popup.classList.contains('show')) {
            popup.classList.remove('show'); // 如果已经显示则移除 show 类名，使其隐藏
        } else {
            popup.classList.add('show'); // 否则添加 show 类名，使其显示
        }
    }
    function personSubmit(){
        // 阻止表单默认提交行为
        event.preventDefault();
        if($("#deviceName").val()==""){
            alert("请输入设备名称");
            $("#deviceName").val("");
            return;
        }
        if($("#deviceSN").val()==""){
            alert("请输入设备序列号");
            $("#deviceSN").val("");
            return;
        }
        if($("#companyId").val()==""){
            alert("请选择公司");
            $("#companyId").val("");
            return;
        }
        if($("#deviceSN").val()!=""&&$("#deviceSN").val()!=""&&$("#companyId").val()!=""){
            var info="";
            $.ajax({
                url: basePath+"ea/faceDevice/sajax_ea_findDeviceInfo.jspa",
                type: 'GET',
                data: { deviceSN: $("#deviceSN").val()},
                success: function(data) {
                    var member = eval("(" + data + ")");
                    var list=member.list;
                    for(var i=0;i<list.length;i++){
                        info="设备序列号："+$("#deviceSN").val()+"已绑定到："+list[i][0]+",请输入行的设备序列号！"
                    }
                    if(info!=""){
                        alert(info)
                        $("#deviceSN").val("");
                        return;
                    }else{
                        $("#form").attr("action",basePath+"ea/faceDevice/ea_saveFaceDevice.jspa");
                        $("#form").submit();
                    }
                }
            });
        }
    }

    function addCustomOption(select, text) {
        var option = document.createElement("option");
        option.value = text;
        option.text = text;
        select.appendChild(option);
    }

    function handleInputKeyPress(event) {
        var select = document.getElementById("companyId");
        var input = event.target;
        if (event.key === "Enter") {
            var optionExists = select.options.namedItem(input.value) != null;
            if (!optionExists) {
                addCustomOption(select, input.value);
            }
            input.value = "";
        }
    }


    $(document).ready(function() {
        $('#companyId').on('input', function() {
            var inputVal = $(this).val();
            if (inputVal.length >= 2 && /[\u4e00-\u9fa5]/.test(inputVal)) { // 设置最小字符长度为2
                $.ajax({
                    url: basePath+"ea/faceDevice/sajax_ea_findCompanyList.jspa", // 替换为你的服务器端点
                    type: 'GET',
                    data: { companyName: inputVal },
                    success: function(data) {
                        var member = eval("(" + data + ")");
                        var list=member.list;
                        var resultsHTML="";
                        var res=$('#results')
                        for(var i=0;i<list.length;i++){
                            var name=list[i][1];
                            var id=list[i][0]
                            var a='<a style="font-size: 13px" id="'+id+'" onclick=xuanze("'+name+','+id+'")>'+name+'</a><br>';
                            resultsHTML+=a;
                        }
                        $('#results').html(resultsHTML);
                    }
                });
            } else {
                $('#results').html(''); // 清空结果
            }
        });
    })

    function xuanze(name){
        document.getElementById("companyId").value = name;
        $('#results').html(''); // 清空结果
        myFunction();
    }
    function myFunction() {
        var name=document.getElementById("companyId").value;
        //查询并绑定场所
        var info=name.split(',');
        var erId = info[info.length - 1];
        $.ajax({
            url: basePath+"ea/faceDevice/sajax_ea_findERInfoList.jspa", // 替换为你的服务器端点
            type: 'GET',
            data: { erId: erId },
            success: function(data) {
                var member = eval("(" + data + ")");
                var list=member.list;
                var html='';
                for(var i=0;i<list.length;i++){
                    var name=list[i][1];
                    var id=list[i][0]
                    html+='<option value='+id+'>'+name+'</option>'
                }
                $('#erId').html(html);
            }
        });
    }

    function searchInfo(){
        var companyName=$('#mySearch').val();
        document.location.href = basePath+"ea/faceDevice/ea_findFaceDeviceInfo.jspa?companyName="+companyName;
    }
</script>
<style>
    .popup {
        display: none; /* 初始状态隐藏 */
        position: fixed;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        width: 90%;
        height: 85%;
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

    .companyIdInfo {
        position: fixed;
        top: 258px;
        left: 105px;
        background-color: #fff;
        padding-left: 3px;
    }
</style>
</html>
