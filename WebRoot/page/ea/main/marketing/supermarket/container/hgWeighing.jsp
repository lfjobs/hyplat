<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<meta name="viewport"
      content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
<title>管理员开门显示秤盘重量时时变化</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>js/BuildPlatform/setHtmlFont.js"></script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/container/adminOpen.css">
<style type="text/css">
    body {
        background-color: #f4f4f4;
        margin: 0;
        padding: 0;
    }

    .info {
        width: 100%;
        font-size: 0.75rem;
        color: #555;
        display: flex;
        justify-content: space-around
    }

    .scale-container {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
    }

    .scale {
        width: 30%;
        margin: 0 1% 0.35rem 1%;
        background-color: white;
        border-radius: 0.2rem;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        text-align: center;
        transition: transform 0.3s ease, box-shadow 0.3s ease;
        border: 0.025rem solid transparent;
    }

    .scale:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
    }

    .scale.active {
        border: 0.025rem solid #f74c32;
    }

    .scale.active .weight {
        color: #f74c32;
    }

    .scale p:first-child {
        font-size: 0.7rem;
        font-weight: bold;
        color: #333;
        margin-bottom: 0;
    }

    .scale p {
        font-size: 0.7rem;
        font-weight: bold;
    }

    .scale p:last-child {
        font-size: 0.55rem;
        color: #29b706;
        margin: 0;
    }

    .row {
        margin-top: 0 !important;
    }

    .row li {
        margin-top: 0.5rem !important;
        display: flex !important;
        align-items: center !important;
        justify-content: center !important;
    }

    /*  一行显示  */
    .one p.weight {
        font-size: 0.65rem;
    }

    .one .scale {
        width: 100%;
        height: 1.4rem;
        display: flex;
        align-items: center;
        justify-content: space-around;
    }

</style>
<script>
    var basePath = "<%=basePath%>";
    var hgcode = "${param.hgcode}";
    var companyId = "${param.companyId}";
    var ntoken = 0;
    var clock = '';
    var staffName = "${param.staffName}";
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    var iswin = u.indexOf('Windows') > 1;

    function prompt(c) {
        $(".titlep").text(c);
        $(".div-tingyong").show();
    }

    function onWeightChange(data) {
        try {
            var jsonArray = JSON.parse(data);
            for (var i = 0; i < jsonArray.length; i++) {
                const {
                    /**
                     * 货柜
                     */
                    containerCode,

                    /**
                     * 创建时间
                     */
                    createdDate,

                    /**
                     * 当前AD值
                     */
                    currentAd,

                    /**
                     * 初始AD值
                     */
                    initAd,

                    /**
                     * 初始置零范围报警（0：正常 ，1：报警）-------E1
                     */
                    initZeroAlarm,

                    isActive,
                    /**
                     * 超载标志（0：表示在允许的测量范围内，1：则表示超过允许的测量范围内）F…H
                     */
                    overload,

                    /**
                     * 单双量程状态（0：单量程 ，1：双量程）
                     */
                    range,

                    /**
                     * 去皮操作标志（0：表示无去皮操作，1：则表示有去皮操作）
                     */
                    removePeel,

                    /**
                     * 秤盘编码
                     */
                    scaleCode,

                    /**
                     * 稳定 （0：称重不稳定，1：称重稳定）
                     */
                    stability,

                    /**
                     * 皮重
                     */
                    tare,

                    /**
                     * 当前重量
                     */
                    weight,

                    /**
                     * 负重标志    （0：未负重，1重量值为负重）
                     */
                    weighting,

                    /**
                     * 零位指示    （0：非零位 ，1：零位 ）
                     */
                    zero,

                    /**
                     * 去皮零点AD值
                     */
                    zeroAd
                } = jsonArray[i];
                if (isActive) {
                    $("#" + scaleCode).removeClass("active");
                }else {
                    $("#" + scaleCode).addClass("active");
                }
                $("#" + scaleCode).find(".weight").text("重量：" + weight + "KG");
            }
        } catch (e) {
            console.log(e);
        }
    }

    $(function () {
        const scaleContainer = document.getElementById('scale-container');
        $(".close-tingyong").click(function () {
            $(".div-tingyong").hide();
        });

        $.ajax({
            url: basePath + "ea/depotmanage/sajax_mobile_getListDepotmanage.jspa",
            type: 'GET',
            dataType: "json",
            data: {
                "companyId": companyId,
                "hgcode": hgcode
            },
            success: function (data) {
                var member = data;
                var msg = member.msg;
                var error = member.error;
                if (msg) {
                    prompt(error);
                } else {
                    var depotManagelist = member.depotManagelist;
                    var htmlstr = new Array();
                    for (var i = 0; i < depotManagelist.length; i++) {
                        var proWeight = depotManagelist[i];
                        const scaleDiv = document.createElement('div');
                        scaleDiv.classList.add('scale');
                        scaleDiv.setAttribute("id", proWeight.depotCoding);
                        scaleDiv.innerHTML = "<p>(" + proWeight.depotCoding + ")</p><p>" + proWeight.depotName + "</p><p class='weight'>重量：0KG</p>";
                        scaleContainer.appendChild(scaleDiv);
                    }
                    try {
                        var jsonArray = JSON.parse(avm.getWeights());
                        for (var i = 0; i < jsonArray.length; i++) {
                            const {
                                /*** 货柜*/
                                containerCode,
                                /*** 创建时间*/
                                createdDate,
                                /*** 当前AD值*/
                                currentAd,
                                /*** 初始AD值*/
                                initAd,
                                /*** 初始置零范围报警（0：正常 ，1：报警）-------E1*/
                                initZeroAlarm,

                                isActive,
                                /*** 超载标志（0：表示在允许的测量范围内，1：则表示超过允许的测量范围内）F…H*/
                                overload,
                                /*** 单双量程状态（0：单量程 ，1：双量程）*/
                                range,
                                /*** 去皮操作标志（0：表示无去皮操作，1：则表示有去皮操作）*/
                                removePeel,
                                /*** 秤盘编码*/
                                scaleCode,
                                /*** 稳定 （0：称重不稳定，1：称重稳定）*/
                                stability,
                                /*** 皮重*/
                                tare,
                                /*** 当前重量*/
                                weight,
                                /*** 负重标志    （0：未负重，1重量值为负重）*/
                                weighting,
                                /*** 零位指示    （0：非零位 ，1：零位 ）*/
                                zero,
                                /*** 去皮零点AD值*/
                                zeroAd
                            } = jsonArray[i];
                            if (!isActive) {
                                $("#" + scaleCode).addClass("active");
                            }else {
                                $("#" + scaleCode).removeClass("active");
                            }
                            $("#" + scaleCode).find(".weight").text("重量：" + weight + "KG");
                        }
                    } catch (e) {
                        console.log(e);
                    }
                }
            }, error: function cbf(data) {
                prompt("出错");
            }
        });

    });

</script>

</head>
<body>
<%--<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png"/>
            </a>
        </li>
        <li>
            管理员补货
        </li>
    </ul>
</header>--%>
<div class="content_hidden">
    <div class="content">
        <div>
            <ul class="row"> <%-- one为单行显示样式 --%>
                <li>
                    <%--<h1 id="hgcode">货柜号：${param.hgcode}</h1><h1 id="staffname"></h1>--%>
                    <div class="info">
                        <p>货柜号: <span id="container-number">${param.hgcode}</span></p>
                        <p>登录管理员: <span id="staffname">${param.staffName}</span></p>
                        <%--                        <p><span onclick="window.location.reload();">刷新</span></p>--%>
                    </div>
                </li>
                <li>
                    <div class="scale-container" id="scale-container"></div>
                </li>
            </ul>
            <form name="formData" id="formData" style="display: none;">
                <input type="submit" style="display: none;" name="submit" id="submit"/>
                <input type="hidden" name="posNum" value="${param.posNum}"/>
                <input type="hidden" name="account.companyID" class="comid"/>
                <input type="hidden" name="account.companyName" class="comname"/>
                <input type="hidden" name="account.staffID" class="staffid"/>
                <input type="hidden" name="account.staffName" class="staffname"/>
            </form>
        </div>
    </div>
</div>

<!--表单提示-->
<div class="div-tingyong">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt=""/></p>
        <div class="div-box">
            <p class="titlep"></p>
            <div class="clearfix">
                <p class="left close-tingyong">取消</p>
                <p class="right close-tingyong">确定</p>
            </div>
        </div>
    </div>
</div>
</body>
</html>