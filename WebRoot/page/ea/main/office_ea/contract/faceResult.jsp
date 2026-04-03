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
    <%--<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/hqinmanage.css">--%>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/percardinfo.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/percardinfo.js" type="text/javascript" charset="utf-8"></script>
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
            识别结果
        </li>
    </ul>
</header>
<div class="pc-box">
    <div style="margin-bottom: 81px;">
        <div class="flex flex-row"  style="height: 60px;text-align: center;">
            <p class="mt-2" style="font-weight: bold;margin-left: 1%;" onclick="searchResultByTime(100)">所有</p>
            <p class="mt-2" style="font-weight: bold;margin-left: 4%;" onclick="searchResultByTime(1)">今日</p>
            <p class="mt-2" style="font-weight: bold;margin-left: 4%;" onclick="searchResultByTime(2)">昨日</p>
            <p class="mt-2" style="font-weight: bold;margin-left: 4%;" onclick="searchResultByTime(3)">本周</p>
            <p class="mt-2" style="font-weight: bold;margin-left: 4%;" onclick="searchResultByTime(4)">上周</p>
            <p class="mt-2" style="font-weight: bold;margin-left: 4%;" onclick="searchResultByTime(5)">本月</p>
            <p class="mt-2" style="font-weight: bold;margin-left: 4%;" onclick="searchResultByTime(6)">上月</p>
            <p class="mt-2" style="font-weight: bold;margin-left: 4%;" onclick="searchResultByTime(7)">本季</p>
            <p class="mt-2" style="font-weight: bold;margin-left: 4%;" onclick="searchResultByTime(8)">上季</p>
            <p class="mt-2" style="font-weight: bold;margin-left: 4%;" onclick="searchResultByTime(9)">本年</p>
            <p class="mt-2" style="font-weight: bold;margin-left: 4%;" onclick="searchResultByTime(10)">去年</p>
        </div>
        <div id="countInfo" class="flex flex-row"  style="height: 35px;text-align: center;">
        </div>

        <div class="ml-1 mr-1">
            <input type="search" id="mySearch" name="q" placeholder="人名搜索...">
            <input id="mySearchSubmit" type="submit" onclick="searchInfo()" value="搜索">
        </div>
        <div class="mt-1 ml-1 mr-1" style="overflow-x: auto;white-space: nowrap;width: 98%;">
            <table class="border-collapse border border-slate-400" style="text-align: center;width: 100%;">
                <thead>
                <tr style="height: 50px;">
                    <th class="border border-slate-300">图片</th>
                    <th class="border border-slate-300">姓名</th>
                    <th class="border border-slate-300">抓拍时间</th>
                    <th class="border border-slate-300">进/出</th>
                </tr>
                </thead>
                <tbody id="dataTable" >
                </tbody>

            </table>
            <!-- 生成分页导航按钮 -->
            <div style="margin-top: 16px;">
                <button style="margin-left: 2%;">当前页:<a id="pageNumber"></a></button>
                <button style="margin-left: 2%;" onclick="firstPage()"><b>首页</b></button>
                <button style="margin-left: 2%;" onclick="previousPage()"><b>上一页</b></button>
                <button style="margin-left: 2%;" onclick="nextPage()"><b>下一页</b></button>
                <button style="margin-left: 2%;" onclick="lastPage()"><b>尾页</b></button>
                <button style="margin-left: 2%;">总页数:<a id="pageCount"></a></button>
            </div>
        </div>
    </div>



    <div class="popup" id="myPopup">

    </div>
    <%--<div class="footer div-bottom">
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
    </div>--%>
</div>
</body>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var pageNumber = 1; // 初始化为第一页
    var pageCount=0;
    var pageSize=7;
    var timeNumber=100;
    function firstPage(){
        if (pageNumber > 1) {
            pageNumber=1;
            loadData();
        }else{
            alert("已经是第一页")
        }
    }
    function previousPage() {
        if (pageNumber > 1) {
            pageNumber--;
            loadData();
        }else{
            alert("已经是第一页")
        }
    }
    function nextPage() {
        if (pageNumber < pageCount) {
            pageNumber++;
            loadData();
        }else{
            alert("已经是最后一页");
        }
    }
    function lastPage() {
        if (pageNumber < pageCount) {
            pageNumber=pageCount;
            loadData();
        }else{
            alert("已经是最后一页");
        }
    }
    function loadData() {
        var personName=$('#mySearch').val();
        $.ajax({
            url: basePath+"ea/faceDevice/sajax_ea_findFaceResult.jspa", // 替换为你的服务器端点
            type: 'GET',
            data: { pageNumber: pageNumber,pageSize: pageSize,timeNumber:timeNumber,personName:personName},
            success: function(data) {
                resultsHTML(data);
            }
        });
    }
    function resultsHTML(data){
        var resultsHTML="";
        var divp='';
        var json=JSON.parse(data);
        $.each(json, function(key, value) {
            if(key=='enterCount') {
                divp+='<p className="mt-2" style="font-weight: bold;margin-left: 1%;color: grey;">进人次：'+value+'人</p>';
            } else if (key == 'outCount') {
                divp+='<p className="mt-2" style="font-weight: bold;margin-left: 10%;color: grey;">出人次：'+value+'人</p>';
            } else {
                var list = value["list"];
                var tr='';
                for(var i=0;i<list.length;i++){
                    tr+='<tr class="border border-slate-300" onclick=showResult("'+list[i][5]+'")>';
                    //onclick=showImage("'+basePath+list[i][0]+'")
                    tr+='<td class="border border-slate-300"><img class="imgClass" id="myImg" src="'+basePath+list[i][0]+'"></td>';
                    tr+='<td class="border border-slate-300">'+list[i][1]+'</td>';
                    tr+='<td class="border border-slate-300">'+list[i][3]+'</td>';
                    var state=list[i][4]==1?"进":"出";
                    tr+='<td class="border border-slate-300">'+state+'</td>';
                    tr+='</tr>';
                }
                resultsHTML=tr;

            }
            pageCount=value["pageCount"];
            $('#pageNumber').html(pageNumber);
            $('#pageCount').html(pageCount);
            $('#dataTable').html(resultsHTML);
            $('#countInfo').html(divp);

        });
    }
    function showResult(resultId){
        var popup = document.getElementById("myPopup"); // 获取弹窗元素
        if (popup.classList.contains('show')) {
            popup.classList.remove('show'); // 如果已经显示则移除 show 类名，使其隐藏
            $('#myPopup').html("");
        } else {
            $.ajax({
                url: basePath+"ea/faceDevice/sajax_ea_findFaceResultById.jspa", // 替换为你的服务器端点
                type: 'GET',
                data: { resultId : resultId},
                success: function(data) {
                    var json=JSON.parse(data);
                    var resultsHTML="";
                    $.each(json, function(key, value) {
                        for(var i=0;i<value.length;i++){
                            resultsHTML+='<div><img class="imgClass" src="'+basePath+value[i][5]+'"></div>';
                            resultsHTML+='<div>姓名：'+value[i][0]+'</div>';
                            var inoutFlag=value[i][3]==1?"进":"出";
                            resultsHTML+='<div>进/出：'+inoutFlag+'</div>';
                            resultsHTML+='<div>设备号：'+value[i][1]+'</div>';
                            resultsHTML+='<div>抓拍时间：'+value[i][2]+'</div>';
                            var status=value[i][4]==1?"识别成功":"识别失败";
                            resultsHTML+='<div>识别结果：'+status+'</div>';
                            var payNumber=value[i][6]==null?"未知":value[i][6]+"¥";
                            resultsHTML+='<div>缴费金额：'+payNumber+'</div>';
                            var payStatus="未知";
                            if(value[i][7]!=null && value[i][7]==1){
                                payStatus="已缴费";
                            }else if(value[i][7]!=null && value[i][7]==2){
                                payStatus="未缴费";
                            }else if(value[i][7]!=null && value[i][7]==3){
                                payStatus="不用缴费";
                            }
                            resultsHTML+='<div>缴费状态：'+payStatus+'</div>';
                        }
                    });
                    $('#myPopup').html(resultsHTML);
                }
            });
            popup.classList.add('show'); // 否则添加 show 类名，使其显示
        }
    }
    function noneImage(){
        var popup = document.getElementById("myPopup"); // 获取弹窗元素
        if (popup.classList.contains('show')) {
            popup.classList.remove('show'); // 如果已经显示则移除 show 类名，使其隐藏
        } else {
            popup.classList.add('show'); // 否则添加 show 类名，使其显示
        }
    }

    function searchResultByTime(number){
        timeNumber=number;
        pageNumber=1;
        $.ajax({
            url: basePath+"ea/faceDevice/sajax_ea_findFaceResult.jspa",
            type: 'GET',
            data: { pageNumber: pageNumber,pageSize: pageSize,timeNumber:timeNumber},
            success: function(data) {
                resultsHTML(data);
            }
        });
    }
    function searchInfo(){
        var personName=$('#mySearch').val();
        pageNumber=1;
        $.ajax({
            url: basePath+"ea/faceDevice/sajax_ea_findFaceResult.jspa",
            type: 'GET',
            data: { pageNumber: pageNumber,pageSize: pageSize,timeNumber:timeNumber,personName:personName},
            success: function(data) {
                resultsHTML(data);
            }
        });
    }
    //判断页面是否有底部导航
    if ($("*").is(".div-bottom")) {
        $(".container").addClass("pc-bottom");
    }
    loadData(); // 首次加载数据
</script>
<style>
    .popup {
        display: none; /* 初始状态隐藏 */
        position: fixed;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        width: 70%;
        height: 300px;
        background-color: #fff;
        border: 1px solid #ccc;
        padding: 1px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
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
    th{
        background-color: #f9f9f9;
        position: sticky;
        top: 0;
    }
    .imgClass{
        width: 50px;
        height: 50px;
        margin-top: 5px;
        margin-left: 5px;
        margin-right: 5px;
        margin-bottom: 5px;
    }
    table, th, td {
        border: none;
        border-collapse: collapse;
        font-size: 10pt;
    }
</style>
</html>
