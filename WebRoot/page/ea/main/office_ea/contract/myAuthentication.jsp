<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
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
    <title>我的认证</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base2.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/navigationBar.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <style>
        .imgDiv {
            position: absolute;
            right: 0;
        }

        .imgRZ {
            position: absolute;
            right: 30px;
            height: 50px;
        }

        .spanInfo {
            position: absolute;
            right: 40%;
            color: green;
        }

        .carImg {
            width: 40px;
            height: 40px;
        }

        .carDiv {
            width: 90%;
            margin-left: 10px;
            border-bottom: 1px solid #d9d9d9; /* 设置下边框的样式、宽度和颜色 */
            display: flex;
            align-items: center;
            padding: 10px;
            position: relative;
        }

        span {
            margin-left: 20px;
        }

        .popup {
            display: none; /* 初始状态隐藏 */
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 50%;
            height: 200px;
            background-color: #fff;
            border: 1px solid #ccc;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            border-top-left-radius: 10px;
            border-top-right-radius: 10px;
            border-bottom-right-radius: 10px;
            border-bottom-left-radius: 10px;
        }

        .popupThoroughfare {
            display: none; /* 初始状态隐藏 */
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 80%;
            height: 70%;
            background-color: #fff;
            border: 1px solid #ccc;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            border-top-left-radius: 10px;
            border-top-right-radius: 10px;
            border-bottom-right-radius: 10px;
            border-bottom-left-radius: 10px;
        }

        .show {
            display: block; /* 显示弹窗 */
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

        button {
            margin: auto;
            width: 70px;
            height: 30px;
            background: #F74C32;
            border-radius: 6px;
            color: white;
            font-size: 16px;
            border: none;
            outline: none; /* 可选，用于移除点击按钮时产生的轮廓 */
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
            background: green; /* 选中状态下的背景色 */
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

        .authorizeDiv {
            margin-top: 10px;
            margin-bottom: 10px;
            font-size: 20px;
        }

        .close-button {
            position: absolute;
            right: 10px;
            top: 10px;
            cursor: pointer;
            font-size: 30px;
            color: red;
        }
    </style>
</head>
<body id="" scroll="no">
<div class="pc-box">
    <div class="div-box">
        <header>
            <ul class="clearfix">
                <li>
                    <a onclick="homePage()" target="_self">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
                    </a>
                </li>
                <li>
                    我的认证
                </li>
            </ul>
        </header>
    </div>
</div>
<div style="font-size: 11pt">
    <div class="carDiv" id="file-selector">
        <c:if test="${personObj[0] eq null}">
            <img id="headAuthenticationImg" class="carImg" src="<%=basePath%>images/WFJClient/pc/newimg/touXiang.jpeg"></c:if>
        <c:if test="${personObj[0] ne null}">
            <img id="headAuthenticationImg" class="carImg" src="<%=basePath%>${personObj[0]}" class="rz finish"></c:if>
        <span>头像认证</span>
        <c:if test="${personObj[0] eq null}">
            <img class="imgRZ" src="<%=basePath%>images/ea/office/contract/selectp/wrz.png" class="rz"></c:if>
        <c:if test="${personObj[0] ne null}">
            <img class="imgRZ" src="<%=basePath%>images/ea/office/contract/selectp/yrz.png" class="rz finish"></c:if>
        <img class="imgDiv" src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png">
    </div>
    <div class="carDiv" id="file-faceAuthentication">
        <c:if test="${personObj[1] eq null}">
            <img id="faceAuthenticationImg" class="carImg" id="face-img" src="<%=basePath%>images/WFJClient/pc/newimg/rlsb.png" ></c:if>
        <c:if test="${personObj[1] ne null}">
            <img id="faceAuthenticationImg" class="carImg" src="<%=basePath%>${personObj[1]}" ></c:if>

        <span>刷脸认证</span>
        <%--<span class="spanInfo" onclick="showThoroughfare()">通道</span>--%>
        <c:if test="${personObj[1] eq null}">
            <img class="imgRZ" src="<%=basePath%>images/ea/office/contract/selectp/wrz.png" class="rz"></c:if>
        <c:if test="${personObj[1] ne null}">
            <img class="imgRZ" src="<%=basePath%>images/ea/office/contract/selectp/yrz.png" class="rz finish"></c:if>
        <img class="imgDiv" src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png" >

    </div>
    <div class="carDiv" onclick="getInfo()">
        <c:if test="${frontCardUrl[0] eq null}">
            <img class="carImg" src="<%=basePath%>images/WFJClient/pc/newimg/sfz.jpg"></c:if>
        <c:if test="${frontCardUrl[0] ne null}">
            <img class="carImg" src="<%=basePath%>${frontCardUrl[0]}"></c:if>

        <span>身份认证</span>
        <c:if test="${personObj[2] eq null}">
            <img class="imgRZ" src="<%=basePath%>images/ea/office/contract/selectp/wrz.png" class="rz"></c:if>
        <c:if test="${personObj[2] ne null}">
            <img class="imgRZ" src="<%=basePath%>images/ea/office/contract/selectp/yrz.png" class="rz finish"></c:if>
        <img class="imgDiv" src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png">
    </div>
    <div class="carDiv" onclick="signAuthentication()">
        <img class="carImg" src="<%=basePath%>images/WFJClient/pc/newimg/gaiZhang.jpeg">
        <span>签字印章</span>
        <c:if test="${personSeal eq null}"><img class="imgRZ"
                                                src="<%=basePath%>images/ea/office/contract/selectp/wrz.png" class="rz"></c:if>
        <c:if test="${personSeal ne null}"><img class="imgRZ"
                                                src="<%=basePath%>images/ea/office/contract/selectp/yrz.png"
                                                class="rz finish"></c:if>
        <img class="imgDiv" src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png">
    </div>

    <div class="carDiv" onclick="bankCardInfo()">
        <img class="carImg" src="<%=basePath%>images/WFJClient/pc/newimg/yhk.jpg">
        <span>银行卡</span>
        <span class="spanInfo">${personBank}</span>
        <c:if test="${personBank eq null}"><img class="imgRZ"
                                                src="<%=basePath%>images/ea/office/contract/selectp/wrz.png" class="rz"></c:if>
        <c:if test="${personBank ne null}"><img class="imgRZ"
                                                src="<%=basePath%>images/ea/office/contract/selectp/yrz.png"
                                                class="rz finish"></c:if>
        <img class="imgDiv" src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png">
        <input type="hidden" id="staffId" value="${staffId}">
        <input type="hidden" id="sccid" value="${sccid}">
        <input type="hidden" id="account" value="${account}">
    </div>

    <div class="carDiv" onclick="worktypeInfo()">
        <%--<img class="carImg" src="<%=basePath%>images/WFJClient/pc/newimg/yhk.jpg">--%>
        <svg t="1757562876578" class="icon carImg" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
             p-id="1633" width="200" height="200">
            <path d="M477.866667 877.226667H109.226667c-17.066667 0-30.72-13.653333-30.72-30.72v-750.933334c0-17.066667 13.653333-30.72 30.72-30.72h593.92c17.066667 0 30.72 13.653333 30.72 30.72v266.24c0 17.066667 13.653333 30.72 30.72 30.72s30.72-13.653333 30.72-30.72V95.573333c0-51.2-40.96-92.16-92.16-92.16H109.226667C58.026667 0 13.653333 44.373333 13.653333 95.573333v750.933334c0 51.2 40.96 92.16 92.16 92.16H477.866667c17.066667 0 30.72-13.653333 30.72-30.72s-13.653333-30.72-30.72-30.72z"
                  fill="#228AD9" p-id="1634"></path>
            <path d="M631.466667 139.946667H163.84c-17.066667 0-30.72 13.653333-30.72 30.72s13.653333 30.72 30.72 30.72h467.626667c17.066667 0 30.72-13.653333 30.72-30.72s-13.653333-30.72-30.72-30.72z m0 126.293333H163.84c-17.066667 0-30.72 13.653333-30.72 30.72s13.653333 30.72 30.72 30.72h467.626667c17.066667 0 30.72-13.653333 30.72-30.72s-13.653333-30.72-30.72-30.72z m286.72 703.146667H614.4c-17.066667 0-27.306667 13.653333-27.306667 27.306666 0 17.066667 13.653333 27.306667 27.306667 27.306667h303.786667c17.066667 0 27.306667-13.653333 27.306666-30.72 0-13.653333-10.24-23.893333-27.306666-23.893333z m-6.826667-225.28h-61.44c-6.826667-17.066667 3.413333-51.2 27.306667-92.16 10.24-20.48 20.48-47.786667 20.48-81.92 0-75.093333-58.026667-136.533333-136.533334-136.533334-75.093333 0-136.533333 61.44-136.533333 136.533334 0 34.133333 10.24 58.026667 20.48 81.92v3.413333c23.893333 40.96 34.133333 75.093333 27.306667 92.16h-61.44c-34.133333 0-61.44 27.306667-61.44 61.44v98.986667c0 13.653333 10.24 23.893333 23.893333 23.893333h361.813333c13.653333 0 23.893333-10.24 23.893334-23.893333v-102.4c13.653333-34.133333-13.653333-61.44-47.786667-61.44z m3.413333 129.706666H614.4v-68.266666c0-3.413333 3.413333-6.826667 3.413333-6.826667h85.333334l3.413333-3.413333c44.373333-37.546667 40.96-98.986667-6.826667-177.493334-10.24-20.48-17.066667-34.133333-17.066666-54.613333 0-44.373333 34.133333-78.506667 78.506666-78.506667 44.373333 0 78.506667 34.133333 78.506667 78.506667 0 20.48-6.826667 34.133333-17.066667 54.613333-47.786667 78.506667-51.2 143.36-6.826666 177.493334l3.413333 3.413333h85.333333c3.413333 0 3.413333 3.413333 3.413334 6.826667l6.826666 68.266666zM307.2 607.573333c-30.72-6.826667-51.2-30.72-51.2-61.44l-20.48-10.24c-3.413333-3.413333-6.826667-6.826667-6.826667-13.653333V512c0-6.826667 3.413333-13.653333 13.653334-17.066667 3.413333-27.306667 20.48-54.613333 44.373333-68.266666 3.413333-6.826667 10.24-13.653333 17.066667-13.653334h30.72c6.826667 0 13.653333 3.413333 17.066666 13.653334 23.893333 13.653333 44.373333 40.96 47.786667 68.266666 6.826667 3.413333 10.24 10.24 10.24 17.066667v13.653333c0 6.826667-3.413333 10.24-6.826667 13.653334-6.826667 3.413333-10.24 6.826667-17.066666 10.24 0 30.72-23.893333 54.613333-51.2 61.44H375.466667c30.72 0 54.613333 17.066667 68.266666 44.373333l20.48 54.613333c6.826667 17.066667 0 37.546667-17.066666 44.373334-3.413333-3.413333-10.24-3.413333-13.653334-3.413334H211.626667c-17.066667 0-34.133333-13.653333-34.133334-34.133333 0-3.413333 0-6.826667 3.413334-13.653333l20.48-51.2c10.24-27.306667 37.546667-44.373333 68.266666-44.373334H307.2z m64.853333-51.2v-6.826666c-17.066667 3.413333-34.133333 6.826667-51.2 6.826666s-34.133333 0-51.2-6.826666c0 6.826667 3.413333 17.066667 10.24 20.48v3.413333c10.24 10.24 23.893333 17.066667 37.546667 17.066667h13.653333c3.413333 0 10.24-3.413333 13.653334-6.826667 0 0 3.413333 0 3.413333-3.413333 6.826667-6.826667 10.24-10.24 13.653333-17.066667v-3.413333c0-3.413333 6.826667 0 10.24-3.413334z m23.893334-34.133333V512l-10.24-3.413333-3.413334-10.24c-3.413333-23.893333-17.066667-40.96-34.133333-54.613334v40.96H341.333333v-44.373333l-3.413333-6.826667s0-3.413333-3.413333-3.413333h-30.72s-3.413333 0-3.413334 3.413333l-3.413333 6.826667v44.373333h-6.826667V443.733333c-17.066667 13.653333-30.72 34.133333-34.133333 54.613334l-3.413333 10.24-6.826667 3.413333v13.653333c20.48 13.653333 54.613333 17.066667 75.093333 17.066667 34.133333 0 61.44-6.826667 75.093334-20.48z m-126.293334 102.4c-23.893333 0-40.96 13.653333-51.2 34.133333l-20.48 54.613334c-3.413333 6.826667 0 17.066667 10.24 20.48h228.693334c10.24 0 17.066667-6.826667 17.066666-17.066667v-6.826667l-20.48-54.613333c-6.826667-20.48-27.306667-34.133333-51.2-34.133333l-112.64 3.413333z"
                  fill="#228AD9" p-id="1635"></path>
        </svg>
        <span>用工认证</span>
        <span class="spanInfo">${twslist}</span>
        <c:if test="${twslist eq null}"><img class="imgRZ"
                                             src="<%=basePath%>images/ea/office/contract/selectp/wrz.png" class="rz"></c:if>
        <c:if test="${twslist ne null}"><img class="imgRZ"
                                             src="<%=basePath%>images/ea/office/contract/selectp/yrz.png"
                                             class="rz finish"></c:if>
        <img class="imgDiv" src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png">
        <input type="hidden" id="staffId" value="${staffId}">
        <input type="hidden" id="sccid" value="${sccid}">
        <input type="hidden" id="account" value="${account}">
    </div>
</div>

<%--头像认证--%>
<div style="display: flex;justify-content: center;align-items: center;">
    <form id="headAuthenticationForm" method="post" enctype="multipart/form-data">
        <div class="flex flex-row" style="position: relative;">
            <input type="file" name="photo" id="photo" accept="image/jpg,image/png;capture=camera" style="display:none;">
        </div>
    </form>
</div>
<%--刷脸认证--%>
<div style="display: flex;justify-content: center;align-items: center;">
    <form id="faceAuthenticationForm" method="post" enctype="multipart/form-data">
        <div class="flex flex-row" style="position: relative;">
            <input type="file" name="personImageInfo" id="personImageInfo" accept="image/jpg,image/png;capture=camera" style="display:none;">
        </div>
    </form>
</div>
<%--授权刷脸信息的通道列表--%>
<%--<div class="popupThoroughfare" id="myPopupThoroughfare">
    <div class="close-button" onclick="showThoroughfare()">&times;</div>
    <div class="authorizeDiv">
        <div class="div-name">
            <label for="">已授权通道：</label>
            <div class="authorizeDiv"><label class="custom-checkbox"><input class="checkbox-class" type="checkbox" value="" checked disabled/><span class="checkmark"></span> 通道2</label></div>
            <div class="authorizeDiv"><label class="custom-checkbox"><input class="checkbox-class" type="checkbox" value="" checked disabled/><span class="checkmark"></span> 通道2</label></div>
        </div>

        <div class="div-name">
            <label for="">未授权通道：</label>
            <div class="authorizeDiv"><label class="custom-checkbox"><input class="checkbox-class" type="checkbox" value="" disabled/><span class="checkmark"></span> 通道2</label></div>
            <div class="authorizeDiv"><label class="custom-checkbox"><input class="checkbox-class" type="checkbox" value="" disabled/><span class="checkmark"></span> 通道2</label></div>
        </div>
    </div>
</div>--%>
<%--加载请求动画--%>
<div id="loading"></div>

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
    </div>
</div>
<script type="text/javascript">
    $("#loading").fadeOut();
    var basePath = "<%=basePath%>";
    var headImg = false;
    var faceImg = false;

    function homePage() {
        document.location.href = basePath + "ea/mycenter/ea_myIndex.jspa";
    }

    window.onload = function () {
        // 获取所有复选框
        var checkboxes = document.getElementsByClassName('checkbox-class');
        // 循环遍历复选框，如果它们被选中，则添加一个自定义的类
        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                checkboxes[i].classList.add('checked');
            }
        }
    };


    /*document.querySelector('.close-button').addEventListener('click', function() {
        window.close();
    });
*/


    function getInfo() {
        document.location.href = basePath + "ea/mycenter/ea_getInfo.jspa?op=view";
    }

    //头像上传
    document.getElementById('file-selector').addEventListener('click', function() {
        console.log("123")
        document.getElementById('photo').click();
    });
    document.getElementById('photo').addEventListener('change', function(event) {
        headSubmit();
    });

    function headSubmit(){
        // 阻止表单默认提交行为
        event.preventDefault();
        //获取头像是否已经有文件，有则上传。
        $("#loading").fadeIn();
        $("#headAuthenticationForm").attr("action",basePath+"ea/mycenter/ea_uploadMyPhoto.jspa");
        $("#headAuthenticationForm").submit();
    }


    //人脸识别上传
    document.getElementById('file-faceAuthentication').addEventListener('click', function() {
        document.getElementById('personImageInfo').click();
    });
    document.getElementById('personImageInfo').addEventListener('change', function(event) {
        //开始上传并将图片显示到图片框中
        faceSubmit();
    });
    function faceSubmit(){
        // 阻止表单默认提交行为
        event.preventDefault();
        $("#loading").fadeIn();
        $("#faceAuthenticationForm").attr("action",basePath+"ea/mycenter/ea_uploadMyPhoto.jspa");
        $("#faceAuthenticationForm").submit();
    }


    $('#photo').change(function() {
        var file = this.files[0];
        var reader = new FileReader();

        reader.onload = function() {
            // 通过 reader.result 来访问生成的 DataURL
            document.getElementById("headAuthenticationImg").src = reader.result;
        };
        reader.readAsDataURL(file);
    });


    $('#personImageInfo').change(function() {
        var file = this.files[0];
        var reader = new FileReader();
        reader.onload = function() {
            // 通过 reader.result 来访问生成的 DataURL
            document.getElementById("faceAuthenticationImg").src = reader.result;
        };
        reader.readAsDataURL(file);
    });

    function signAuthentication() {
        document.location.href = basePath + "ea/enterprisestamp/ea_getStampList.jspa";
    }


    function showThoroughfare() {
        var popup = document.getElementById("myPopupThoroughfare"); // 获取弹窗元素
        if (popup.classList.contains('show')) {
            popup.classList.remove('show'); // 如果已经显示则移除 show 类名，使其隐藏
        } else {
            popup.classList.add('show'); // 否则添加 show 类名，使其显示
        }
    }

    function bankCardInfo() {
        var staffId = document.getElementById("staffId").value;
        var sccid = document.getElementById("sccid").value;
        var account = document.getElementById("account").value;
        document.location.href = basePath + "ea/perinfor/ea_getBankCardInformation.jspa?khd=0&flag=&identifying=&ccompanyId=&staffId=" + staffId + "&sccid=" + sccid + "&user=" + account + "&editType=00&mark=03";
        //
    }



    function worktypeInfo() {
        var staffId = document.getElementById("staffId").value;
        var sccid = document.getElementById("sccid").value;
        var user = document.getElementById("account").value;
        document.location.href = basePath + "page/ea/main/marketing/edmandServe/workType_list.jsp?originPage=individual";
        //&staffId=" + staffId + "&sccid=" + sccid + "&user=" + user
    }
</script>
</body>
</html>
