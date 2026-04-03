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
            导入人员信息
        </li>
    </ul>
    <style>
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
            border-bottom: 1px solid #9d9d9d; /* 仅显示下边框 */
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
        .progress-container {
            margin-top: 2px;
            width: 100%;
            background-color: #eee;
            border-radius: 10px;
            overflow: hidden; /* To avoid floating of inner div */
        }

        .progress-bar {
            height: 30px;
            width: 0%; /* Initially empty */
            background-color: #76B900;
            border-radius: 10px;
            transition: width 1s ease-in-out; /* Animation effect */
            text-align: center; /* Center the text inside */
            line-height: 30px; /* Vertical center text */
            color: white;
            font-weight: bold;
        }

        /* Just for demonstration */
        .progress-container:hover .progress-bar {
            width: 100%; /* Expand to full width on hover */
        }
    </style>
</header>
<div style="padding: 5px;">
    <div>
        <input style="width: 250px;" type="file" id="zipFile" />
        <button onclick="uploadPhoto()">开始上传图片</button>
        <div class="progress-container">
            <div id="myJDT" class="progress-bar" style="width: 0%;">0%</div>
        </div>
    </div>
    <div style="margin-top: 30px;">
        <input type="file" id="input-excel" />
        <br>
        <label for="erId">授权场所：</label>
        <select id="erId" name="erId" class="border-2 border-rose-100" style="width: 177px;" onchange="setDeviceInfo()">
        </select>
        <button id="importButton" onclick="importPersonnel()" disabled>开始导入</button>
    </div>
    <div style="margin-top: 5px;">
        需要导入的设备列表：<div>
            <input type="checkbox" id="checkAll" onclick="checkAll()">
            <label for="checkAll">全选/反选</label>
        </div>
        <div id="deviceInfo">

        </div>
    </div>

    <div id="excel-data" style="margin-top: 5px;"></div>
</div>


</body>
<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=basePath%>js/ea/office_ea/contract/percardinfo.js" type="text/javascript" charset="utf-8"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.17.5/xlsx.full.min.js"></script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var files=null;
    var uploadState=false;
    var myJDT=document.getElementById("myJDT");
    function uploadPhoto(){
        uploadState=true;
        const input = document.getElementById('zipFile');
        const file = input.files[0];
        if(file==null){
            alert("请选择上传的文件压缩包！");
            return;
        }
        const chunkSize = 512 * 1024; // 1MB
        const chunkCount = Math.ceil(file.size / chunkSize);
        var number=0;
        const tasks = []
        for (var i = 0; i < chunkCount; i++) {
            var chunk = file.slice(i * chunkSize, (i + 1) * chunkSize);
            var formData = new FormData();
            formData.append('chunkFile', chunk);
            formData.append('chunkFilename', file.name);
            formData.append('chunkNumber', i);
            formData.append('totalChunks', chunkCount);
            tasks.push(upload(formData, ()=>{
                number=number+1;
                console.log(number+"--"+tasks.length+'======='+((number / tasks.length) * 100).toFixed(0));
                const  progress= Math.round(((number / tasks.length) * 100).toFixed(0));
                jdt(progress,myJDT)
            }))
        }

        Promise.all(tasks)
            .then(()=>{
                document.getElementById("importButton").disabled = false;
            })
            .catch((reason)=>{
                console.log(reason);
            })
    }

    function upload(formData,callback) {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: basePath+'ea/faceDevice/sajax_ea_handleChunkUpload.jspa',
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                success: function(response) {
                    callback()
                    resolve()
                },
                error: function() {
                    reject("Error uploading chunk")
                }
            });
        })
    }

     function jdt(progress,myJDT){
         myJDT.style.width=progress +"%";
         myJDT.textContent=progress +"%";
     }
    window.onload = function () {
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
        setDeviceInfo();
    }
    function setDeviceInfo(){
        var erId=document.getElementById('erId').value;
        $.ajax({
            url: basePath+"ea/faceDevice/sajax_ea_findDeviceBySiteid.jspa",
            type: "post",
            async: true,
            dataType: "json",
            data: {
                "erId": erId
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
    var personnelJsonArray = [];
    document.getElementById('input-excel').addEventListener('change', function(e) {
        const file = e.target.files[0];
        personnelJsonArray = [];
        var reader = new FileReader();
        reader.onload = function(e) {
            var data = new Uint8Array(e.target.result);
            var workbook = XLSX.read(data, {type: 'array'});

            // 假设我们只读取第一个工作表
            var firstSheetName = workbook.SheetNames[0];
            var worksheet = workbook.Sheets[firstSheetName];
            // 将工作表转换为JSON
            var jsonArray = XLSX.utils.sheet_to_json(worksheet);
            //var jsonArray=JSON.stringify(json)
            // 遍历JSON数组
            var html="";
            jsonArray.forEach(function(item) {
                // 遍历对象的键值对
                var tr="<tr class='dataClass'>";
                var name="";
                var imageUrl="";

                Object.keys(item).forEach(function(key) {
                    if(key=="姓名"){
                        tr+='<td>'+item[key]+'</td>'
                        name=item[key];
                    }else if(key=="人脸图片"){
                        tr+='<td style="padding-left: 20pt;padding-top: 5pt;">'+item[key]+'</td>'
                        imageUrl=item[key];
                    }
                });
                var obj = {
                    name,
                    imageUrl
                };
                personnelJsonArray.push(obj);
                tr+="</tr>"
                html+=tr;
            });
            // 将完成的信息div添加到列表中
            $("#excel-data").html(html);
            // 显示数据
           // document.getElementById('excel-data').innerText = JSON.stringify(json);
        };
        reader.readAsArrayBuffer(file);
    });

    function importPersonnel(){
        //判断图片是否已上传
        if(uploadState){
            alert("请先上传图片，否则无法将找打人员对应的头像!");
            return;
        }
        var checkboxes = document.querySelectorAll('input[type=checkbox]');
        let list = [];
        checkboxes.forEach((checkbox) => {
            if (checkbox.checked == true) {
                list.push(checkbox.value)
            }
        });
        if(list.length<=0){
            alert("请选择场地中需要发下人脸的设备！");
            return;
        }else if(personnelJsonArray.length<=0){
            alert("没有需要导入的数据，请重新选择需要导入的文件！");
            return;
        }
        var deviceIds=list.join(",");
        const input = document.getElementById('input-excel');
        const file = input.files[0]; // 获取文件
        const formData = new FormData(); // 创建FormData对象
        formData.append('photoFiles', file); // 将文件添加到FormData对象中
        formData.append('infoList',JSON.stringify(personnelJsonArray));
        formData.append('erId',document.getElementById('erId').value);
        formData.append('deviceIds',deviceIds);
        fetch(basePath+"ea/faceDevice/sajax_ea_importPersonnel.jspa", { // 后端上传地址
            method: 'POST',
            body: formData
        })
            .then(response => response.json()) // 如果后端返回JSON，解析它
            .then(data => console.log(data)) // 处理返回的数据
            .catch(error => console.error('Error:', error)); // 处理错误
        /*$.ajax({
            url: basePath+"ea/faceDevice/sajax_ea_importPersonnel.jspa", // 替换为你的服务器端点
            type: "POST",
            dataType: "json",
            data:{
                    "infoList":JSON.stringify(personnelJsonArray),
                    "erId":document.getElementById('erId').value,
                    "photoFiles":file,
                    "deviceIds":deviceIds
                },
            success: function(data) {
                console.log("导入成功")
            }
        });*/
    }

</script>

</html>
