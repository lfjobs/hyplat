<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/my/myRelated.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <style>
        table{
            width: 100%;
            border-collapse: collapse; /* 去除表格间隙 */
            table-latheRightt: fixed; /* 设置表格布局为固定，确保单元格宽度一致 */
            margin-top: 5px;
        }
        tr{
            margin-top: 0.4rem;
            display: flex;
            margin-bottom: 10px;
            width: 100%;
        }
        td{
            display: flex;
            align-items: center; /* 垂直居中 */
        }
        .total{
            display: flex;
            width: 100%;
        }

        .theRight{
            width: 100%;
            padding: 10px;
        }
        .user{
            width: 100%;
            display: flex;
            font-size: 1rem;
            border-bottom: 1px solid #b0b0b0;
            margin-bottom: 10px;
            font-weight: 700; /* 使用数值 */
        }
        .user div{
            flex: 1; /* 两个div平等共享空间 */
            padding: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .gongNeng{
            display: flex;
            white-space: nowrap;
            flex-flow: wrap;
        }
        .gongNeng table tr td{
            display: flex;
            white-space: nowrap;
            flex-flow: wrap;
            gap: 5px;
        }
        .menu_three{
            width: 80%;
        }
        .gongNeng table tr td div{
            flex: 1; /* 两个div平等共享空间 */
            padding: 7px;

        }
        .menu_two{
            font-size: 0.98rem;
            color: #0C0C0C;
        }
        .menu_three{
            font-size: 0.93rem;
            color: #4d4d4d;
        }
        .user-tuiJian{
            margin-top: -10px;
        }
        .bold {
            font-weight: bold;
        }
    </style>
</head>
<body id="">
<div class="pc-box">
    <div class="div-box">
        <header>
            <ul class="clearfix">
                <li>
                    <a onclick="window.history.go(-1);return false;" target="_self">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
                    </a>
                </li>
                <li>
                    我的
                </li>
            </ul>
        </header>
        <%--<div>
            <table id="tableInfo1" class="info">

            </table>
        </div>--%>
        <div class="total">
            <div class="theLeft">
                <div class="text-container" style="margin-top: 7px" onclick="show(1,this)"></div>
                <div class="text-container" onclick="show(3,this)"></div>
            </div>
            <div class="theRight" id="tableInfo">

            </div>
        </div>
    </div>
</div>
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
                <img src="<%=basePath%>images/WFJClient/pc/newimg/img_07.jpg" alt="">
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
        <li>
            <div>
                <img src="<%=basePath%>images/WFJClient/pc/newimg/img_10.jpg" alt="">
            </div>
            <p>
                5L5C
            </p>
        </li>
        <li class="active">
            <div>
                <img src="<%=basePath%>images/WFJClient/pc/newimg/img_40.png" alt="">
            </div>
            <p>
                我的
            </p>
        </li>
    </ul>
</div>
<script type="text/javascript">
    var sccid = '<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
    var companyID = '<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getCompanyId():"" %>';
    var user = '<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getAccount():"" %>';
    var staffID = '<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid():"" %>';
    var basePath = "<%=basePath%>";
    var menuIdText='2025040117091';
    window.onload = function() {
        const textContainers = document.querySelectorAll('.text-container');
        var number = getParameterByName('number');
        if(number==4){
            //初始化样式
            textContainers[3].classList.add('bold');
            recommend();
        }else{
            //初始化样式
            textContainers[0].classList.add('bold');
            myProject();
        }
    };

    function goTo(url, menuId) {
        //请求数据库添加数据并跳转地址
        $.ajax({
            url: basePath + "ea/mycenter/sajax_ea_saveMenuRecommend.jspa",
            type: "POST",
            dataType: "json",
            aysnc: false,
            data: {
                "pmr.menuId": menuId
            },
            success: function (data) {
                document.location.href = url;
            },
            error: function (data) {
                document.location.href = basePath+"page/WFJClient/pc/pc_login.jsp";
            }
        })
    }

    function show(number,div){
        document.querySelectorAll('.bold').forEach(function(element) {
            element.classList.remove('bold');
        });
        div.classList.add('bold'); // 添加bold类来加粗字体
        if(number==1){
            //显示我的项目
            myProject();
        }else if(number==3){
            //显示关注
            recommend();
        }else{
            alert("错误！")
        }
    }
    function myProject(){
        $.ajax({
            url: basePath + "ea/mycenter/sajax_ea_findMenuInfoTwo.jspa",
            type: "POST",
            dataType: "json",
            aysnc: false,
            data: {
                "pmr.menuId": menuIdText
            },
            success: function (data) {
                var member = eval("(" + data + ")");
                var list = member.list;
                setInfo(list);
            }
        });
        function setInfo(list) {
            //var htmltb = '<div id="one" class="user"><div onclick=personalMethods("'+1+'") style="color: #0d9908"><b>个人</b></div><div onclick=personalMethods("'+2+'")>家庭</div><div onclick=personalMethods("'+3+'")>家族</div><div onclick=personalMethods("'+4+'")>民族</div></div><div>';
            var htmltb = '';
            for (let key in list) {
                htmltb += '<div class="gongNeng"><table>';
                var htmltr = '<tr class="tr">';
                if (list.hasOwnProperty(key)) {
                    htmltb += '<td class="menu_two"><div>' + key + '</div></td>';
                    var array = list[key];
                    htmltb += '<td class="menu_three">';
                    for (var i = 0; i < array.length; i++) {
                        var arr = array[i];
                        var url = null;
                        if (arr[2] != null) {
                            var url = arr[3];
                            if (url != null) {
                               url=tihuan(url);
                                htmltb += ' <div onclick=goTo("' + basePath + url + '","' + arr[1] + '")>' + arr[2] + '</div>';
                            } else {
                                htmltb += '<div>' + arr[2] + '</div></a>';
                            }
                        }
                    }
                    htmltr += '</td>';
                    htmltr += '</tr></table></div></div>';
                }
                htmltb += htmltr;
            }
            var tableInfo = document.getElementById('tableInfo');
            tableInfo.innerHTML = htmltb+"<div style='height: 100px'></div>";
        }
    }
    function recommend(){
        //查询推荐信息
        $.ajax({
            url: basePath + "ea/mycenter/sajax_ea_findMenuInfoTwoAll.jspa",
            type: "POST",
            dataType: "json",
            aysnc: false,
            data: {
                "pmr.menuId": menuIdText
            },
            success: function (data) {
                var member = eval("(" + data + ")");
                var list = member.list;
                var htmltb = '<div class="user-tuiJian"><div id="one" class="user"><div class="gongNeng">';
                for (let key in list) {
                    var array = list[key];
                    var url=array[1];
                    if(url!=null){
                        url=tihuan(url);
                    }
                    htmltb += ' <div onclick=goTo("' + basePath + url + '","' + array[2] + '")>' + array[0] + '</div>';
                }
                htmltb+='</div></div><div>';
                var tableInfo = document.getElementById('tableInfo');
                tableInfo.innerHTML = htmltb;
            }
        });
    }
    function tihuan(url){
        const regex = /\((.*?)\)/;
        let match = url.match(regex);
        while (match) {
            const content = match[1]; // 括号内的内容
            var replacement = "";
            if ("sccid=" == content) {
                replacement = "sccid=" + sccid;
            }
            if ("staffid=" == content) {
                replacement = "staffid=" + staffID;
            }
            if ("user=" == content) {
                replacement = "user=" + user;
            }
            if ("companyId=" == content) {
                replacement = "companyId=" + companyID;
            }
            url = url.replace("(" + content + ")", replacement);
            match = url.match(regex);
        }
        return url;
    }

    function personalMethods(number){
        if(number==1){
            //个人
            myProject();
        }else if(number==2 || number==3){
            //家庭
            family(number);
        }else if (number==4){
            //名族
            famousClan();
        }
    }
    function family(number){
        var htmltb ='';
        if(number==2){
            htmltb = '<div id="one" class="user">' +
                '<div onclick=personalMethods("'+1+'")>个人</div>' +
                '<div onclick=personalMethods("'+2+'") style="color: #0d9908"><b>家庭</b></div>' +
                '<div onclick=personalMethods("'+3+'")>家族</div>' +
                '<div onclick=personalMethods("'+4+'")>民族</div>' +
                '</div>';
        }else if(number==3){
            htmltb = '<div id="one" class="user">' +
                '<div onclick=personalMethods("'+1+'")>个人</div>' +
                '<div onclick=personalMethods("'+2+'")>家庭</div>' +
                '<div onclick=personalMethods("'+3+'") style="color: #0d9908"><b>家族</b></div>' +
                '<div onclick=personalMethods("'+4+'")>民族</div>' +
                '</div>';
        }

        var tableInfo = document.getElementById('tableInfo');
        tableInfo.innerHTML = htmltb;
    }
    function famousClan(){
       var htmltb = '<div id="one" class="user">' +
           '<div onclick=personalMethods("'+1+'")>个人</div>' +
            '<div onclick=personalMethods("'+2+'")>家庭</div>' +
            '<div onclick=personalMethods("'+3+'")>家族</div>' +
            '<div onclick=personalMethods("'+4+'") style="color: #0d9908"><b>民族</b></div>' +
            '</div>';
        var tableInfo = document.getElementById('tableInfo');
        tableInfo.innerHTML = htmltb;
    }

    // 解析URL参数
    function getParameterByName(name, url = window.location.href) {
        name = name.replace(/[\[\]]/g, '\\$&');
        var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, ' '));
    }
</script>
</body>
</html>
