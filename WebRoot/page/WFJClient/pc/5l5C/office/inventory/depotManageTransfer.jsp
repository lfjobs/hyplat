<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head lang="en">
    <meta charset="UTF-8"/>
    <meta name="viewport"
          content="width=device-width, user-scalable=0, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
    <title>库房</title>

    <link rel="stylesheet" type="text/css" href="<%=basePath %>util/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/depotManageTransfer.css">

    <script type="text/javascript" src="<%=basePath %>js/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/comm.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/BuildPlatform/setHtmlFont.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/WFJClient/localforage.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/paho-mqtt/1.0.1/mqttws31.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/container/mqttLock.js"></script>

    <script>
        var ppId;
        var user = '${user}';
        var companyId = '${companyId}';
        var basePath = '<%=basePath%>';
        var originPage = '${param.originPage}';
        var index = 0;
    </script>

    <style>

    </style>
</head>
<body>
<form enctype="multipart/form-data" name="form1" id="launchForm" action="" method="post">


    <header>
        <p><a id="returnClick" onclick="goBack()">
            <img src="<%=basePath%>images/WFJClient/PersonalJoining/back_03.png" alt=""/>
        </a></p>
        <span>库房</span>
    </header>

    <!--------------------------------------------- 主内容开始 ------------------------------------------------->
    <div class="content">
        <section class="sec-list">
            <div class="dtd-oa-search-bar">
                <div class="dtd-oa-search-bar-icon-wrapper">
                    <i class="layui-icon">&#xe615;</i>
                </div>
                <input type="text" class="dtd-oa-search-bar-input" placeholder="搜索：公司名称" name="companyName"
                       id="companyName" autocomplete=“off”/>
                <div class="dtd-oa-search-bar-icon-wrapper close-company" style="left:90%">
                    <i class="layui-icon layui-del">&#x1006;</i>
                </div>
            </div>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show div-company">
                    <ul class="company-ul">
                    </ul>
                </div>
            </div>
        </section>
        <section class="sec-bottom">
            <p>
                确定
            </p>
        </section>
    </div>
    <!--------------------------------------------- 主内容结束 ------------------------------------------------->
    <div id="prompt" style="width: 100%; display: none;z-index: 1001">
        <center>
            <div>
                <span style="position: relative; top: 19.8%;"></span>
            </div>
        </center>
    </div>
</form>

<script type="text/javascript">
    const $parentWin = $(window.parent.document);
    const $prompt = $("#prompt");
    const $promptSpan = $prompt.find("span");
    let depotid = "";
    let depotName = "";
    let depotCoding = "";
    let itemID = "";
    var posNum;

    /**
     * 自动贩卖机操作员信息
     * @type {{posNum: (string|string), loginGuid: (string|string), companyid: (string|string), companyIdentifier: (string|string), staffName: (string|string)}}
     */
    const config = {
        posNum: getcookie("posNum"), //货柜终端机编号
        hgcode: getcookie("hgcode"), //货柜库房编号
        loginGuid: getcookie("loginGuid"), //登录id
        companyid: getcookie("comID"), //公司id
        staffName: getcookie("staffName"), //人员姓名
        companyIdentifier: getcookie("companyIdentifier") //公司标识
    }

    window.onload = window.onresize = function () {
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //console.log(clientWidth);
        //通过屏幕宽度去设置不同的后台根字体的大小
        //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
        if (clientWidth >= 960) {
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 8 + 'px';

        } else {
            //通过屏幕宽度去设置不同的后台根字体的大小
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
        }
    }

    window.onload = function () {
        posNum = config.posNum;
        MQTTconnect();
        $("#prompt").css("position", "absolute").css("top", $(window).height() * 0.09 + "px");
        $("#prompt").find("div").css("height", $(window).height() * 0.06 + "px").css("font-size", $(window).height() * 0.0285 + "px").css("color", "#FFFFFF");
        $("#prompt").find("div").css("-moz-border-radius", $(window).height() * 0.015 + "px").css("-webkit-border-radius", $(window).height() * 0.015 + "px");

        if (typeof originPage === 'string' && originPage !== "") {
            try {
                requestPage = originPage.split("-");

                // 确保数组长度至少为2
                if (requestPage.length < 2) {
                    throw new Error("Invalid originPage format");
                }

                if (requestPage[0] === "win") {
                    $("header").hide();
                }

                if (requestPage[1] === "bh") {
                    $("#bh").show();
                    $("#isok").hide();
                } else if (requestPage[1] === "rk") {
                    $("#bh").hide();
                    $("#isok").show();
                }

                if (config.posNum != null && config.posNum != "") {
                    var Str = new Array();
                    Str.push("<li class='div-company-li' >");
                    Str.push("<div class='div-posNum-name' id='" + config.hgcode + "'>");
                    Str.push("<label>货柜号：" + config.hgcode + "</label>");
                    Str.push("</div><ul class='div-company-li-ul'></ul></li>");
                    $(".company-ul").html(Str.join(""));

                    $(".div-company-name").width("95%");
                    let url = basePath + "ea/depotmanage/sajax_mobile_getListDepotmanageByHgcode.jspa";
                    getDepot(config.hgcode, url);
                }

            } catch (error) {
                console.error("Error processing originPage:", error);
                // 可以根据需求决定是否需要回滚操作或显示错误信息
            }
        }


        //清空查询框
        $(".layui-del").click(function () {
            $("#companyName").val("");
            $(".company-ul").empty();
        });

        //公司名称查询
        $(document).on("input", "#companyName", function () {
            var $p = $(this);
            if ($.trim($p.val()) == "" || $.trim($p.val()).length < 4) {
                return false;
            }
            getCom($p.val());
        });

        //选择库房
        $(document).on("click", ".div-company-name", function () {
            var $p = $(this);
            let url = basePath + "/ea/depotmanage/sajax_mobile_getListDepotToComid.jspa";
            getDepot($p.attr("id"), url);
        });

        //点击选中
        $(document).on("click", ".div-door-li-ul li", function (event) {
            event.stopPropagation(); // 防止事件冒泡
            if ($(this).is(".active")) {
                $(this).removeClass("active");
            } else {
                const paramElement = $(this).find("#param"); // 假设将 ID 改为 class
                if (paramElement.length === 0) return; // 如果找不到 param 元素，直接返回

                const param = paramElement.val().split("-");
                if (param.length < 3) return; // 参数不足，直接返回

                var names = param[0].split(" > ");
                var index = names.length;
                depotid = $(this).attr("id");
                depotName = names[index - 1];
                depotCoding = param[1];
                itemID = param[2];

                // 只移除当前父级下的 .active 类
                $(".div-company-li-ul .active").removeClass("active");
                $(this).addClass("active");
            }
        });


        //库房关闭
        $(document).on("click", ".close", function () {
            $("#companyName").val();
            $(".company-ul").empty();
        });

        //仓库选择
        $(".sec-bottom").click(function () {
            // 确保所有依赖变量已正确定义并赋值
            if (!depotid || !depotName || !depotCoding || !itemID) {
                // 使用更友好的提示方式
                alert("请先选择仓库");
                return;
            }

            try {
                // 构建参数对象
                let depot = {
                    'depotID': depotid,
                    'depotName': depotName,
                    'depotCoding': depotCoding,
                    'itemID': itemID,
                    'text': $("#" + depotid).find("p").text() || ''
                };

                // 调用父窗口的方法并捕获异常
                window.parent.FeedbackDepot(depot);
            } catch (error) {
                console.error("FeedbackDepot 调用失败:", error);
            }

            // 隐藏 iframe 并重置其 src 属性
            $('.div-iframe', $parentWin).hide();
            $('.div-iframe', $parentWin).find("iframe").attr("src", "");
        });
    }

    /**
     * 提示框
     * @param obj
     */
    function prompt(obj) {
        // 检查提示框是否已经可见
        if ($prompt.is(":visible")) {
            return;
        }

        // 确保传入的内容是安全的
        const safeText = String(obj).replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
        $promptSpan.text(safeText);

        // 显示提示框
        $prompt.fadeIn(500);

        // 清除之前的定时器，确保只有一个定时器在运行
        clearTimeout($prompt.data('timeoutId'));

        // 设置新的定时器，在2秒后隐藏提示框并清空文本
        const timeoutId = setTimeout(function () {
            $prompt.fadeOut(500, function () {
                $promptSpan.text("");
            });
        }, 2000);

        // 存储定时器ID以便后续清除
        $prompt.data('timeoutId', timeoutId);
    }

    //选择库房加载公司名称
    function getCom(companyName) {
        $.ajax({
            url: encodeURI(basePath + "ea/documentcommon/sajax_ea_getAllCompany.jspa?"),
            type: "GET",
            async: false,
            dataType: "json",
            data: {
                "document.companyName": companyName
            },
            success: function (data) {
                var Str = new Array();
                var companyId = "";
                if ("" == data || "null" == data) {
                    Str.push("<li style='display:flex;align-items:center;justify-content:center'>暂无数据</li>");
                } else {
                    var list = eval("(" + data + ")");
                    var listData = list["companylist"];
                    let listDataLength = listData.length;
                    if (listDataLength > 0) {
                        for (var i = 0; i < listData.length; i++) {
                            companyId = listData[i].companyID;
                            Str.push("<li class='div-company-li' >");
                            Str.push("<div class='div-company-name' id='" + companyId + "'>");
                            Str.push("<label>" + listData[i].companyName + "</label>");
                            Str.push("</div><ul class='div-company-li-ul'></ul></li>");
                        }
                    } else {
                        Str.push("<li style='display:flex;align-items:center;justify-content:center'>暂无数据</li>");
                    }
                }

                $(".company-ul").html(Str.join(""));

                $(".div-company-name").width("95%");

            },
            error: function f() {
                console.log("获取数据错误");
            }
        });
    }

    //选择库房加载库房
    function getDepot(comid, url) {
        $.ajax({
            url: encodeURI(url),
            type: "GET",
            async: false,
            dataType: "json",
            data: {
                "companyID": comid
            },
            success: function (data) {
                var htmlstr = new Array();
                if ("" == data || "null" == data) {
                    htmlstr.push("<li style='display:flex;align-items:center;justify-content:center'><p>暂无数据</p></li>");
                } else {
                    var member = eval("(" + data + ")");
                    var flag = member.flag;
                    if (flag == "1") {
                        var meg = member.meg;
                        var error = member.error;
                        if (error == "3") {
                            document.location.href = basePath + "page/WFJClient/pc/pc_login.jsp";
                        } else {
                            htmlstr.push("<li><p style='text-align: center'>" + meg + "</p></li>");
                        }
                    } else {
                        let cabinetList = member["cabinetList"];
                        let d=member["depotManage"];
                        if (cabinetList != null && cabinetList != "undefined") {
                            let cabinetListLength = cabinetList.length;
                            if (cabinetListLength > 0) {
                                for (var j = 0; j < cabinetListLength; j++) {
                                    htmlstr.push("<li class='div-cabinet-li' >");
                                    htmlstr.push("<div class='div-cabinet-name' id='" + cabinetList[j].depotID + "'>");
                                    htmlstr.push("<label>" + cabinetList[j].depotName + "(" + cabinetList[j].depotCoding + ")</label></div>");
                                    htmlstr.push("<ul class='div-cabinet-li-ul'>");
                                    getDepotAnnex(htmlstr, cabinetList[j].depotID, member["doorList"], member["depotList"]);
                                    htmlstr.push("</ul></li>");
                                }
                            }
                        }else if(d != null && d != "undefined") {
                            getDepotAnnex(htmlstr, d.depotID, member["doorList"], member["depotList"]);
                        }else {
                            htmlstr.push("<li style='display:flex;align-items:center;justify-content:center'><p>暂无数据</p></li>");
                        }
                    }
                    const moreData = $(".div-company-li-ul");
                    if (moreData != null) {
                        moreData.empty();
                    }
                    $("#" + comid).parent().find("ul").html(htmlstr.join(""));
                }
            }
            ,
            error: function f() {
                console.log("获取数据错误");
            }
        });
    }

    function getDepotAnnex(htmlstr, id, doorList, depotList) {
        if (doorList != null && doorList != "undefined") {
            let doorListDataLength = doorList.length;
            if (doorListDataLength > 0) {
                let doorbool=true;
                for (var j = 0; j < doorListDataLength; j++) {
                    if (doorList[j].depotPID == id) {
                        doorbool=false;
                        htmlstr.push("<li class='div-door-li door-li' >");
                        htmlstr.push("<div class='div-door-name door-li' id='" + doorList[j].depotID + "'>");
                        htmlstr.push("<label>" + doorList[j].depotName + "(" + doorList[j].depotCoding + ")</label></div>");
                        htmlstr.push("<ul class='div-door-li-ul door-li'>");
                        if (depotList != null && depotList != "undefined") {
                            let listDataLength = depotList.length;
                            if (listDataLength > 0) {
                                let depotbool=true;
                                for (var i = 0; i < listDataLength; i++) {
                                    if (depotList[i].depotPID == doorList[j].depotID) {
                                        doorbool=false;
                                        htmlstr.push("<li class='clearfix' id='" + depotList[i].depotID + "'>");
                                        htmlstr.push("<div class='sex'>");
                                        htmlstr.push("<img class='img-01' src='" + basePath + "images/ea/office/contract/selectp/img_02.png'/>");
                                        htmlstr.push("<img class='img-02' src='" + basePath + "images/ea/office/contract/selectp/img_03.png'/>")
                                        htmlstr.push("</div>");
                                        htmlstr.push("<p onclick='updatePro(\"" + depotList[i].depotCoding + "\")'>" + depotList[i].depotName + "(" + depotList[i].depotCoding + ")</p>");
                                        htmlstr.push("<input type='hidden' id='param' value='" + depotList[i].depotName + "-" + depotList[i].depotCoding + "-" + depotList[i].itemID + "'/>");
                                        htmlstr.push("</li>");
                                }
                            }
                            if (doorbool){
                                    htmlstr.push("<li class=''><p style='text-align: center'>暂无数据</p></li>");
                                }
                            } else {
                                htmlstr.push("<li class=''><p style='text-align: center'>暂无数据</p></li>");
                            }
                        }
                        htmlstr.push("</ul></li>");
                    }
                }
                if (doorbool){
                    htmlstr.push("<li class=''><p style='text-align: center'>暂无数据</p></li>");
                }
            } else {
                htmlstr.push("<li class=''><p style='text-align: center'>暂无数据</p></li>");
            }
        } else {
            htmlstr.push("<li class=''><p style='text-align: center'>暂无数据</p></li>");
        }
    }
</script>
</body>
</html>