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
<head lang="zh">
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/ProductManageMobileList.css"/>
    <script type="text/javascript" src="<%=basePath%>js/BuildPlatform/setHtmlFont.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/comm.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/WFJClient/localforage.min.js"></script>

    <title></title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        const companyID = "${param.companyID}";
        var pageCount = ${pageForm==null?0:pageForm.pageCount};//总页面数（选择项目页分也用）
        var count=0;//总条数（选择产品页分也用）
        var pageSize = 25;//每页多少条（选择产品页分也用）
        var pageNumber = 0;//第几页（选择产品页分也用）
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
            user: getcookie("user"), //人员帐号
            companyIdentifier: getcookie("companyIdentifier") //公司标识
        }
    </script>

    <style type="text/css">
        .content>section:first-of-type {
            padding: 3rem .25rem 1rem .25rem;
            position: relative
        }

        .content>section:first-of-type input {
            height: 1.5rem;
            line-height: 1.5rem;
            font-size: .55rem;
            color: #b9b9b9;
            border: 0;
            background-color: #f3f3f3;
            border-radius: .15rem
        }

        .content>section:first-of-type input:first-of-type {
            float: left;
            width: 55%;
            padding-left: 10%
        }

        .content>section:first-of-type input:nth-of-type(2) {
            float: right;
            width: 15%;
            color: #222;
            margin-left: 0.5rem;
        }

        .content>section:first-of-type input:last-of-type {
            float: right;
            width: 15%;
            color: #222;
        }

        .content>section:first-of-type img {
            position: absolute;
            left: 4.5%;
            top: 63%;
            width: .7rem
        }

        .content>section.button {
            padding: 1.5rem 0 1.5rem 0;
            width: 100%
        }

        .content>section.button p {
            width: 80%;
            margin: 0 auto;
            text-align: center;
            border-radius: .15rem;
            height: 1.6rem;
            line-height: 1.6rem;
            font-size: .6rem;
            color: #fff;
            background-color: #f74c32
        }

        .content .sec-list .ul {
            padding-top: 2rem;
        }

        .content .sec-list ul li div.sex {
            height: 1.5rem;
            line-height: 1.5rem;
            word-wrap: break-word;
            float: left;
            /*padding-right: .3rem*/
            padding: 0 0.2rem;
            display: flex;
            align-items: center;
            justify-content: center;
            width: 3%;
        }

        .content .title-pc .depot-p {
            width: 30%;
        }

        .content .title-pc .quantity-p {
            width: 5%;
        }

        .content .title-pc .show-p {
            width: 10%;
        }
    </style>
</head>
<body id="overfover">
<%--http://www.impf2010.com/page/WFJClient/ProductsLaunch/ProductManageMobileList.jsp--%>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>商品列表</li>
        <a href="<%=basePath%>/ea/productslaunch/ea_toProductsLaunch.jspa?"><li>发布产品</li></a>
    </ul>
</header>
<div class="content">
    <section class="clearfix header">
        <%--<section class="clearfix header">--%>
            <img src="<%=basePath%>images/scMobile/payBudget/addBudget/wupinguanli_03.png">
            <input type="text" id="ttsw_item_search_id" placeholder="搜索商品名称/条码号">
            <input type="button" value="取消" onclick="closeInfo();">
            <input type="button" value="查询" onclick="initItemInfo();">
        <%--</section>--%>
    </section>
    <section class="sec-list" id="pc-sec">
        <ul class="ul">
            <li class="clearfix">
                <div class="title-pc"></div>
            </li>
        </ul>
    </section>
    <section class="button fixed_bottom">
        <p id="p_add">
            确定
        </p>
    </section>
</div>
<!--表单提示-->
<div class="div-tingyong">
    <div class="box">
        <div class="div-header">
            温馨提示
            <p>x</p>
            <%--            <img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt=""/>--%>
        </div>
        <div class="div-box">
            <p class="title-p"></p>
            <div class="clearfix">
                <p class="right close-tingyong">确定</p>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

    function closeInfo(){
        $(".div-name").hide();
        $("body").removeClass("body_yc");
        // var url = "ea_scanningCostBudgetInfo.jspa";
        // $("#f1").attr('action',url);    //通过jquery为action属性赋值
        // $("#f1").submit();    //提交ID为myform的表单
    }

    //分页滚动
    $(window).scroll(function () {
        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度
        var Top = $(".last1").offset().top; //元素距离顶部距离
        if (Top - Height - scroll <= 20) {
            if (pageNumber < pageCount) {
                getList();
            }
        }
    });

    //选中
    $(document).on("click", ".ul li", function (event) {
        event.stopPropagation();
        if ($(this).is(".active")) {
            $(this).removeClass("active");
        } else {
            $(".ul .active").removeClass("active");
            $(this).addClass("active");
        }
    });

    $("#p_add").click(function () {
        const htmlvalue=$(this).html();
        if (htmlvalue=="确定") {
            // 获取当前选中的元素，并确保其 ID 存在且有效
            let $activeElement = $(".active");
            let id = $activeElement.attr("id");

            if (!id) {
                $(".title-p").text("请选择商品入库"); // 修正拼写错误
                $(".div-tingyong").show();
                return false;
            }
            var goodsId = $("#" + id).find(".goodid").val();
            var params = new Array();
            params.push("user=" + config.user);
            params.push("&companyId=" + config.companyid);
            params.push("&ppId=" + id);
            params.push("&goodsId=" + goodsId);
            window.location.href = basePath + "ea/productslaunch/ea_toProductsLaunch.jspa?" + params.join("");
        }else {
            window.parent.location.href = basePath + "/ea/productslaunch/ea_toProductsLaunch.jspa?";
        }
    });

    /*function initItemInfo() {
        pageNumber = 0;
        $(".ul").empty();
        $(".ul").append("<li class='clearfix'><div class='title-pc'></div></li>");
        pageNumber++;
        var url = "/ea/productslaunch/sajax_ea_getProductByParam.jspa";
        console.log(pageNumber);
        $.ajax({
            url: basePath + url,
            type: "get",
            dataType: "json",
            aysnc: false,
            data: {
                "pageForm.pageNumber": pageNumber,
                "pageForm.pageSize": pageSize,
                "param": $("#ttsw_item_search_id").val()
            },
            success: function (data) {
                if (data != null) {
                    var member = eval("(" + data + ")");
                    if (member != null) {
                        var flag = member.flag;
                        if (flag == "1") {
                            var error = member.error;
                            if (error == "0") {
                                if (config.loginGuid != null && config.loginGuid != "") {
                                    document.location.href = basePath + "page/ea/main/marketing/supermarket/container/adminOpen.jsp";
                                } else {
                                    document.location.href = basePath + "page/WFJClient/pc/pc_login.jsp";
                                }
                            } else {
                                $(".title-p").text(member.msg);
                                $(".div-tingyong").show();
                                return false;
                            }
                        } else {
                            var pageForm = member.pageForm;
                            var htmlstr = new Array();
                            var divlstr = new Array();
                            if (pageForm != null && pageForm.recordCount > 0) {
                                $("#p_add").html("确定");
                                pageCount = pageForm.pageCount;
                                console.log(pageNumber);
                                $(".last1").remove();
                                if (pageNumber == 1) {
                                    divlstr.push("<div class='sex'> 选择</div>");
                                    divlstr.push("<div class='depot-p'> 入库仓库</div>");
                                    divlstr.push("<div class='barCode-p pc'> 产品条码</div>");
                                    divlstr.push("<div class='name-p'> 产品名称</div>");
                                    divlstr.push("<div class='quantity-p'> 库存量</div>");
                                    divlstr.push("<div class='show-p'> 是否上架</div>");
                                    $(".title-pc").append(divlstr.join(""));
                                }
                                for (var i = 0; i < pageForm.list.length; i++) {
                                    if (i == pageForm.list.length - 1) {
                                        htmlstr.push("<li class= 'clearfix last1' id='" + pageForm.list[i][2] + "'>");
                                    } else {
                                        htmlstr.push("<li class='clearfix' id='" + pageForm.list[i][2] + "'>");
                                    }
                                    htmlstr.push("<input class='goodid' type='hidden' value='" + pageForm.list[i][3] + "'/>");
                                    htmlstr.push("<div class='title-pc'><div class='sex'>");
                                    htmlstr.push("<img class='img-01' src='" + basePath + "images/ea/office/contract/selectp/img_02.png'/>");
                                    htmlstr.push("<img class='img-02' src='" + basePath + "images/ea/office/contract/selectp/img_03.png'/>");
                                    htmlstr.push("</div>");
                                    //(" + pageForm.list[i][1] +")
                                    htmlstr.push("<div class='depot-p' title='" + pageForm.list[i][5] + "'>"+pageForm.list[i][5] + "</div>");
                                    htmlstr.push("<div class='barCode-p pc' title='" + pageForm.list[i][1] + "'>" + (pageForm.list[i][1] == null ? "-" : pageForm.list[i][1]) + "</div>");
                                    htmlstr.push("<div class='name-p' title='" + pageForm.list[i][0] + "'>" + pageForm.list[i][0] + "</div>");
                                    htmlstr.push("<div class='quantity-p'>" + pageForm.list[i][6] + "</div>");
                                    htmlstr.push("<div class='show-p'>" + (pageForm.list[i][8] == "01" ? "是" : pageForm.list[i][21] == "8" ? "否" : "-") + "</div>");
                                    htmlstr.push("</div></li>");
                                }
                                $(".ul").append(htmlstr.join(""));
                            }else {
                                $(".title-pc").append("查询到该条件相关商品");
                                $("#p_add").html("发布新产品");
                            }
                        }
                    }
                }
            },
            error: function (data) {
                console.log(data);
            }
        });

    }*/

    function initItemInfo() {
        $(".ul").empty();
        $(".ul").append("<li class='clearfix'><div class='title-pc'></div></li>");
        var url = "/ea/productslaunch/sajax_ea_getProductByParam.jspa";
        $.ajax({
            url: basePath + url,
            type: "get",
            dataType: "json",
            aysnc: false,
            data: {
                "param": $("#ttsw_item_search_id").val()
            },
            success: function (data) {
                if (data != null) {
                    var member = eval("(" + data + ")");
                    if (member != null) {
                        var flag = member.flag;
                        if (flag == "1") {
                            var error = member.error;
                            if (error == "0") {
                                if (config.loginGuid != null && config.loginGuid != "") {
                                    document.location.href = basePath + "page/ea/main/marketing/supermarket/container/adminOpen.jsp";
                                } else {
                                    document.location.href = basePath + "page/WFJClient/pc/pc_login.jsp";
                                }
                            } else {
                                $(".title-p").text(member.msg);
                                $(".div-tingyong").show();
                                return false;
                            }
                        } else {
                            var list = member.list;
                            var htmlstr = new Array();
                            var divlstr = new Array();
                            if (list != null && list.length > 0) {
                                $("#p_add").html("确定");
                                $(".last1").remove();
                                divlstr.push("<div class='sex'> 选择</div>");
                                divlstr.push("<div class='depot-p'> 入库仓库</div>");
                                divlstr.push("<div class='barCode-p pc'> 产品条码</div>");
                                divlstr.push("<div class='name-p'> 产品名称</div>");
                                divlstr.push("<div class='quantity-p'> 库存量</div>");
                                divlstr.push("<div class='show-p'> 是否上架</div>");
                                $(".title-pc").append(divlstr.join(""));
                                for (var i = 0; i < list.length; i++) {
                                    if (i == list.length - 1) {
                                        htmlstr.push("<li class= 'clearfix last1' id='" + list[i][2] + "'>");
                                    } else {
                                        htmlstr.push("<li class='clearfix' id='" + list[i][2] + "'>");
                                    }
                                    htmlstr.push("<input class='goodid' type='hidden' value='" + list[i][3] + "'/>");
                                    htmlstr.push("<div class='title-pc'><div class='sex'>");
                                    htmlstr.push("<img class='img-01' src='" + basePath + "images/ea/office/contract/selectp/img_02.png'/>");
                                    htmlstr.push("<img class='img-02' src='" + basePath + "images/ea/office/contract/selectp/img_03.png'/>");
                                    htmlstr.push("</div>");
                                    //(" + pageForm.list[i][1] +")
                                    htmlstr.push("<div class='depot-p' title='" + list[i][5] + "'>"+list[i][5] + "</div>");
                                    htmlstr.push("<div class='barCode-p pc' title='" + list[i][1] + "'>" + (list[i][1] == null ? "-" : list[i][1]) + "</div>");
                                    htmlstr.push("<div class='name-p' title='" + list[i][0] + "'>" + list[i][0] + "</div>");
                                    htmlstr.push("<div class='quantity-p'>" + list[i][6] + "</div>");
                                    htmlstr.push("<div class='show-p'>" + (list[i][8] == "01" ? "是" : list[i][21] == "8" ? "否" : "-") + "</div>");
                                    htmlstr.push("</div></li>");
                                }
                                $(".ul").append(htmlstr.join(""));
                            }else {
                                $(".title-pc").append("查询到该条件相关商品");
                                $("#p_add").html("发布新产品");
                            }
                        }
                    }
                }
            },
            error: function (data) {
                console.log(data);
            }
        });

    }

    //调用扫码枪获取货品信息
    function toAndroidCallcamera() {
        var ua = navigator.userAgent.toLowerCase();
        var isWeixin = ua.indexOf('micromessenger') != -1;
        if(isWeixin){
            var url = basePath
                + "ea/qrshare/sajax_ea_getJssdkConfig.jspa";
            var retUrl = location.href.split('#')[0];
            $.ajax({
                url : url,
                type : "post",
                async : false,
                dataType : "json",
                data:{
                    retUrl: retUrl
                },
                success : function(data) {
                    var m = eval("("+data+")");
                    wx.config({
                        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                        appId: m.appId, // 必填，公众号的唯一标识
                        timestamp:m.timestamp , // 必填，生成签名的时间戳
                        nonceStr: m.nonceStr, // 必填，生成签名的随机串
                        signature: m.signature,// 必填，签名
                        jsApiList: ["scanQRCode"] // 必填，需要使用的JS接口列表
                    });
                    wx.error(function (res) {
                        console.log(res);
                        alert("错误");
                        alert(res);
                    });
                    wx.ready(function () {
                        wx.scanQRCode({
                            needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                            scanType: ["qrCode","barCode","datamatrix","pdf417"], // 可以指定扫二维码还是一维码，默认二者都有
                            success: function (res) {
                                var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                                // alert(result);
                                if(result.indexOf("http")!=-1){
                                    window.location.href = result;
                                }else{
                                    var barcode = "";
                                    if(result.indexOf(",")!=-1){
                                        barcode = result.substring(result.indexOf(",")+1);
                                    }else{
                                        barcode = result;
                                    }
                                    $("#ttsw_hid_barcode").val(barcode);

                                    var wupinType = $("#wupinType").val();
                                    var wupinTypeId = $("#wupinTypeId").val();
                                    var billsType = $("#billsType").val();
                                    if(!wupinTypeId){
                                        alert("请选择物品分类");
                                        return false;
                                    }
                                    $("#ttsw_hid_goodsPurpose").val(wupinType);
                                    $("#ttsw_hid_goodsPurposeId").val(wupinTypeId);
                                    $("#ttsw_hid_billsType").val(billsType);
                                    // console.log("条码值："+$("#ttsw_hid_barcode").val());
                                    //公司id
                                    //treeid = "company20180510CQZCDKTT690000006064";//测试用
                                    // $("#ttsw_hid_companyId").val(treeid);
                                    var url = "ea_scanningCostBudgetInfo.jspa?editFlag="+editFlag+"&cashierBillsId="+cashierBillsId;
                                    $("#f1").attr('action',url);    //通过jquery为action属性赋值
                                    $("#f1").submit();    //提交ID为myform的表单
                                }
                            }
                        });
                    });
                }
            });
        }else {
            var u = window.navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            if (isAndroid == true) {
                Android.callcamera();
            } else {
                var url = "func=" + 'calltiaomaIOS';
                window.webkit.messageHandlers.Native.postMessage(url);
            }
        }
    }
</script>
</body>
</html>
