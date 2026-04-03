<%@ page import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta name="viewport"
      content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
<title>支付方式</title>
<%--<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/style-csp.css">--%>
<script src="<%=basePath%>js/tailwindcss/tailwindcss-3.4.3.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<script>

    var basePath = "<%=basePath%>";
    var journalNum = "${param.journalNum}";
    var totalMoney = "${param.totalMoney}";
    var remainMoney = "${param.remainMoney}";
    var totalNum = "${param.totalNum}";
    var cardNum = "${param.cardNum}";
    var posNum = "${param.posNum}";
    var ccompanyID = "${param.ccompanyID}";
    var comID = "${param.comID}";
    var directUrl = "${param.directUrl}";
    var type = "${param.type}";
    var companyName = "${param.companyName}";
    var fh = "${param.fh}";
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var tipcontent = "";
    var fhform = "${param.fhform}";


    var sccId = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
    var staffId = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid():"" %>';
    var user = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getAccount():"" %>';

    var paymentList = []
   var  timer = "";
        $(function () {

        if(fhform=="3"){
            timer = setInterval(searchResult,2000);
             if(cardNum!=""){
                 $(".back-a").show();
             }else{
                 $(".back-a").hide();
             }
            $(".flex").css("justify-content","center");

            $(".flex").eq(3).css("padding-left","1.5rem");
            var lastPay = "${param.lastPay}";
            if(lastPay=="yes"){
                prompt("您上次购物未完成支付，请先支付上次购物待支付金额！");
            }else{

                if(remainMoney!=""&&remainMoney!=null){
                    if(remainMoney!=totalMoney){

                        prompt("积分和金币不够抵扣，请支付剩余金额");


                    }
                }
            }


        }



        try {
            if (isAndroid == true) {
                Android.speechOutputForAndroid("请选择支付方式");
            } else {
                console.log("请在安卓设备访问！");
            }
        } catch (error) {

        }
    });
    /**
     *
     * 定时查询支付结果
     */
    function searchResult(){
        var ulp = basePath
            + "/ea/sm/sajax_ea_searchPayResult.jspa";
        $.ajax({
            type : "GET",
            url : ulp,
            async : true,
            dataType : "json",
            data:{
                journalNum:journalNum
            },
            success : function(data) {
                var member = eval('(' + data + ')');
                var result = member.result;
                if(result==true){
                    window.clearInterval(timer);

                    document.location.href = basePath+"ea/sm/ea_viewOrderDetail.jspa?journalNum="+journalNum+"&posNum="+posNum+"&fhform="+fhform

                }

            },
            error : function(data) {
                console.log("查询支付结果失败");
            }
        });


    }
    function payMode(mode) {
        if (mode == "scan") {
            if (directUrl != "" && directUrl != null) {
                document.location.href = basePath + "ea/sm/ea_showErCode.jspa?journalNum=" + journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum + "&cardNum=" + cardNum + "&posNum=" + posNum + "&ccompanyID=" + ccompanyID + "&directUrl=" + encodeURIComponent(directUrl) + "&fh=" + fh+"&fhform="+fhform+"&remainMoney="+remainMoney;
            } else {
                document.location.href = basePath + "ea/sm/ea_showErCode.jspa?journalNum=" + journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum + "&cardNum=" + cardNum + "&posNum=" + posNum + "&ccompanyID=" + ccompanyID + "&fh=" + fh+"&fhform="+fhform+"&remainMoney="+remainMoney;
            }
            tipcontent = "扫码支付，请扫描微信或者支付宝付款码";
        }
        if (mode == "cash") {
            if (directUrl != "" && directUrl != null) {
                var backurls = window.location.href;
                document.location.href = directUrl + "&mode=" + mode + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum + "&posNum=" + posNum + "&journalNum=" + journalNum + "&backurls=" + encodeURIComponent(backurls) + "&fh=" + fh+"&fhform="+fhform+"&remainMoney="+remainMoney;
            } else {
                document.location.href = basePath + "ea/sm/ea_showCash.jspa?journalNum=" + journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum + "&cardNum=" + cardNum + "&posNum=" + posNum + "&comID=" + comID + "&fh=" + fh+"&fhform="+fhform+"&remainMoney="+remainMoney;
            }
            tipcontent = "现金支付，请输入实收金额以及操作员交易密码";
        }
        if (mode == "scard") {
            if (directUrl != "" && directUrl != null) {
                document.location.href = basePath + "/page/ea/main/marketing/supermarket/selfservice/scardPay.jsp?journalNum=" + journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum + "&posNum=" + posNum + "&directUrl=" + encodeURIComponent(directUrl) + "&type=" + type + "&fh=" + fh+"&fhform="+fhform+"&remainMoney="+remainMoney;
            } else {
                document.location.href = basePath + "/page/ea/main/marketing/supermarket/selfservice/scardPay.jsp?journalNum=" + journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum + "&posNum=" + posNum + "&fh=" + fh+"&fhform="+fhform+"&remainMoney="+remainMoney;
            }
            tipcontent = "购物卡支付，请扫描您的购物卡";
        }
        if (mode == "face") {
            if (directUrl != "" && directUrl != null) {
                var backurls = window.location.href;
                document.location.href = directUrl + "&mode=" + mode + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum + "&posNum=" + posNum + "&journalNum=" + journalNum + "&backurls=" + encodeURIComponent(backurls) + "&fh=" + fh + "&comID=" + comID + "&companyName=" + companyName;
            } else {
                document.location.href = basePath + "/page/ea/main/marketing/supermarket/selfservice/facePay.jsp?journalNum=" + journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum + "&cardNum=" + cardNum + "&posNum=" + posNum + "&comID=" + comID + "&companyName=" + companyName + "&fh=" + fh;
            }
            tipcontent = "刷脸支付，请选择微信刷脸支付";
        }
        if (mode == "facecard") {
            document.location.href = basePath + "/page/ea/main/marketing/supermarket/selfservice/facePay.jsp?journalNum=" + journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum + "&cardNum=" + cardNum + "&posNum=" + posNum + "&comID=" + comID + "&companyName=" + companyName + "&fh=" + fh + "&wxbind=gw&directUrl=" + encodeURIComponent(directUrl);
            tipcontent = "刷脸购物卡支付，请选择微信刷脸";
        }
        if (mode == "other") {
            document.location.href = basePath + "/page/ea/main/marketing/supermarket/selfservice/transferPay.jsp?journalNum=" + journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalNum + "&cardNum=" + cardNum + "&posNum=" + posNum + "&comID=" + comID + "&companyName=" + companyName + "&fh=" + fh + "&wxbind=gw&directUrl=" + encodeURIComponent(directUrl);
            tipcontent = "转他人付款";
        }

        try {
            if (isAndroid == true) {
                Android.speechOutputForAndroid(tipcontent);
            } else {
                console.log("请在安卓设备访问！");
            }
        } catch (error) {

        }
    }

    function payMode1(id) {
        var payOffline = paymentList.filter(value => value.payOfflineId === id)[0]
        var paymentName = payOffline.paymentName
        var paymentType = payOffline.paymentType
        var params = payOffline.params
        tipcontent = "线下支付";

        if (params === "") {
            alert("请设置对应的收款信息。")
            return
        }

        document.location.href = basePath + "/page/ea/main/marketing/supermarket/selfservice/offlinePay.jsp?journalNum=" + journalNum
            + "&totalMoney=" + totalMoney
            + "&totalNum=" + totalNum
            + "&cardNum=" + cardNum
            + "&posNum=" + posNum
            + "&comID=" + comID
            + "&companyName=" + companyName
            + "&fh=" + fh
            + "&payId=" + id
            + "&paymentType=" + paymentType
            + "&paymentName=" + encodeURIComponent(paymentName)
            + "&params=" + params;

        try {
            if (isAndroid == true) {
                Android.speechOutputForAndroid(tipcontent);
            } else {
                console.log("请在安卓设备访问！");
            }
        } catch (error) {

        }
    }
</script>

</head>
<body>

<section class="code-pay FS">
    <!--扫码支付内容-->
    <article>
        <!--头部-->
        <header>
            <div class="flex flex-row items-center justify-center justify-between p-8 bg-blue-500"
                 style="background-image: url(<%=basePath%>images/supermarket/code-pay-header_bg.png)">
                <img style="width: 150px" src="<%=basePath%>images/supermarket/FS.png" alt="">
                <div class="text-white">
                    <div class="text-2xl">支付多样化</div>
                    <div class="text-sm">请选择你喜欢的支付方式哦</div>
                    <a  class="back-a" href="javascript: window.history.go(-1);">
                        <button class="bg-white text-black text-sm rounded-lg p-2 pl-4 pr-4 m-2">返回</button>
                    </a>
                </div>
            </div>
        </header>
        <!--头部 end-->
        <!--内容-->
        <div class="div-box-ul p-4">
            <figure class="space-y-2">
                <c:if test="${param.fhform ne 3}">
                <h2 class="font-bold">线上支付</h2>
                </c:if>
                <ul class="fs_ul space-y-4" id="online_wrapper">
                </ul>
                <c:if test="${param.fhform ne 3}">
                <h2 class="font-bold">线下支付</h2>

                <ul class="fs_ul space-y-4" id="offline_wrapper">
                </ul>
                <input type="file" value="上传图片" hidden id="qrcodeFile" onchange="uploadImage(this)"/>
                </c:if>
            </figure>
        </div>
        <!--内容 end-->
    </article>
    <!--扫码支付内容 end-->

    <script>
        function initPayment() {
            var paymentList = [
                <c:if test="${param.fhform ne 3}">
                {mode: "face", icon: "<%=basePath%>images/supermarket/fs-3.png", name: "刷脸支付",},
                </c:if>
                {mode: "scan", icon: "<%=basePath%>images/supermarket/fs-1.png", name: "扫码支付",},
                {mode: "cash", icon: "<%=basePath%>images/supermarket/fs-2.png", name: "现金支付",},
                <c:if test="${param.cardNum == null || param.cardNum==''}">
                {mode: "scard", icon: "<%=basePath%>images/supermarket/fs-5.png", name: "购物卡支付",},
                </c:if>
                <c:if test="${param.fhform ne 3}">
                {mode: "facecard", icon: "<%=basePath%>images/supermarket/fs-7.png", name: "刷脸购物卡支付",},
                {mode: "other", icon: "<%=basePath%>images/supermarket/fs-8.png", name: "转他人支付",},
                </c:if>
            ]

            var htmls = "";
            for (let i = 0; i < paymentList.length; i++) {
                var payment = paymentList[i]
                htmls += `
                <div class="flex flex-row items-center space-x-4" onclick="payMode('` + payment.mode + `')">
                    <img class="w-16" src="` + payment.icon + `" alt="">
                    <div class="text-2xl">` + payment.name + `</div>
                </div>
                `
            }

            $("#online_wrapper").empty()
            $("#online_wrapper").append(htmls)
        }

        function getOfflinePaymentList() {
            var url = basePath + "ea/po/sajax_ea_getOfflinePaymentList.jspa?comID="+comID+"&staffId="+staffId;
            $.ajax({
                url: url,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    var htmls = ""
                    paymentList = JSON.parse(data).paymentList;
                    for (let i = 0; i < paymentList.length; i++) {
                        var payment = paymentList[i]
                        var payOfflineId = payment.payOfflineId
                        var paymentType = payment.paymentType
                        var paymentName = payment.paymentName
                        htmls += `
                        <div class="flex flex-row items-center space-x-4">
                            <div class="flex flex-row items-center space-x-4 grow">
                                <img class="w-16" src="<%=basePath%>images/supermarket/fs-5.png" alt="">
                                <div class="" onclick="payMode1('` + payOfflineId + `')">
                                    <div class="text-2xl">` + paymentName + `</div>
                                ` + ((paymentType === "offline_bank") ? y1(payment) : "") + `
                                </div>
                            </div>
                            ` + ((paymentType === "offline_bank") ? x1(payment) : x2(payment)) + `
                        </div>
                        `
                    }

                    $("#offline_wrapper").empty()
                    $("#offline_wrapper").append(htmls)
                },
                error: function (data) {
                }
            })
        }

        function y1(payment) {
            var paymentType = payment.paymentType
            var paymentName = payment.paymentName
            var [name, account] = payment.params.split(":")
            return payment.params === "" ? "" : `<div>` + name + " | " + account + `</div>`
        }

        function x1(payment) {
            var payOfflineId = payment.payOfflineId
            var paymentType = payment.paymentType
            var paymentName = payment.paymentName
            var params = payment.params
            var [name, account] = payment.params.split(",")
            var buttonText = (payment.params === "") ? "设置账号" : "修改账号"
            return `<div class="bg-blue-500 rounded-lg p-3 text-sm text-white font-bold"><button onclick="editBankInfo('` + payOfflineId + `', '` + params + `')">` + buttonText + `</button></div>`
        }

        function x2(payment) {
            var payOfflineId = payment.payOfflineId
            var imagePath = (payment.params === "" ? "" : payment.params)
            var buttonText = ((payment.params === "") ? "上传图片" : "修改图片")

            var imageHtml = (imagePath === "" ? "" : `<img className="absolute w-16 w-16" src="` + imagePath + `"/>`)

            return `
                <div class="w-16 h-16 rounded-lg">
                    <div class="relative w-16 h-16 bg-gray-100">
                        ` + imageHtml + `
                        <div class="absolute bottom-0 bg-gray-600 p-1 text-sm text-white font-bold"><button onclick="uploadFile('` + payOfflineId + `')">` + buttonText + `</button></div>
                    </div>
                </div>
                `
            // return `
            //     <div class="bg-blue-500 rounded-lg p-3 text-sm text-white font-bold">
            //         <button onclick="uploadFile('` + payOfflineId + `')">` + buttonText + `</button>
            //     </div>
            //     `
        }

        var payOfflineId = undefined

        function uploadFile(payOfflineId) {
            this.payOfflineId = payOfflineId
            $("#qrcodeFile").click()
        }

        function editBankInfo(payOfflineId, params) {
            this.payOfflineId = payOfflineId
            console.log(params)
            if (params !== "" && params.indexOf(":") > 0) {
                var [name, account] = params.split(":")
                $("#bankName").val(name)
                $("#bankAccount").val(account)
            }
            $("#bankDialog").toggle()
        }

        function uploadImage(e) {
            // 获取文件
            var file = e.files[0];
            var formData = new FormData();
            formData.append('file', file);
            formData.append('mode', "payoffline");

            var xhr = new XMLHttpRequest();
            xhr.open('POST', basePath + '/mobile/fileupload', true);

            xhr.onload = function () {
                if (this.status == 200) {
                    console.log(this.responseText);
                    updatePayOffline(payOfflineId, this.responseText)
                }
            };

            xhr.send(formData);
        }

        function updatePayOffline(payOfflineId, params) {
            var url = basePath + "ea/po/sajax_ea_setOfflinePayment.jspa";
            $.ajax({
                url: url,
                type: "POST",
                dataType: "json",
                async: true,
                data: {
                    payOfflineId: payOfflineId,
                    params: "<%=basePath%>/" + params
                },
                success: function (data) {
                    console.log()
                    getOfflinePaymentList()
                }
            })
        }

        function updateBankPayOffline() {
            var bankName = $("#bankName").val()
            var bankAccount = $("#bankAccount").val()

            if (bankName === "" || bankAccount === "") {
                alert("请完善你的银行账号信息")
                return
            }

            var url = basePath + "ea/po/sajax_ea_setOfflinePayment.jspa";
            $.ajax({
                url: url,
                type: "POST",
                dataType: "json",
                async: true,
                data: {
                    payOfflineId: payOfflineId,
                    params: bankName + ":" + bankAccount
                },
                success: function (data) {
                    $("#bankDialog").toggle()
                    getOfflinePaymentList()
                }
            })
        }

        function closeBankFormDialog() {
            $("#bankDialog").toggle()
        }

        initPayment()
        getOfflinePaymentList()
        function prompt(obj) {
            $("#prompt").find("span").text("");
            if ($("#prompt").attr("class").indexOf("hidden")==-1)
                return;
            $("#prompt").find("span").text(obj);
            $("#prompt").addClass("block");
            $("#prompt").removeClass("hidden")
            try {
                var u = window.navigator.userAgent;
                var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                if (isAndroid == true) {
                    Android.speechOutputForAndroid(obj);
                } else {
                    console.log("请在安卓设备访问！");
                }
            } catch (error) {

            }
            setTimeout(function () {
                $("#prompt").addClass("hidden");
                $("#prompt").removeClass("block");


            }, 4000);
        }
    </script>
</section>

<dialog id="bankDialog" style="background: rgba(185,185,185,0.52); z-index: 999999"
        class="fixed place-content-center w-full h-full top-0">
    <div class="bg-white rounded-lg m-4 p-4 space-y-4 shadow-md">
        <h2 class="text-2xl">设置银行账号</h2>

        <div class="space-y-2 m-4">
            <div>
                <p>银行名称</p>
                <input placeholder="请输入你的开户行名称" class="border-2 border-gray-600 rounded-lg p-2 w-full"
                       id="bankName">
            </div>

            <div>
                <p>银行账号</p>
                <input placeholder="请输入银行收款账号" type="number"
                       class="border-2 border-gray-600 rounded-lg p-2 w-full" id="bankAccount">
            </div>

            <div class="content-right">
                <button class="pl-4 pr-4 pt-2 pb-2 border-2 border-blue-500 rounded-lg" onclick="closeBankFormDialog()">
                    取消
                </button>
                <button class="pl-4 pr-4 pt-2 pb-2 bg-blue-500 rounded-lg text-white" onclick="updateBankPayOffline()">
                    保存
                </button>
            </div>
        </div>
    </div>
</dialog>

<div id="prompt" class="w-full absolute hidden top-0 bg-black bg-opacity-50 text-center h-screen flex justify-center items-center" >
        <div  class="bg-black bg-opacity-50 rounded-lg h-100 w-100 text-white p-4 flex justify-center items-center">
            <span class="text-[24px]"></span>
        </div>
</div>
</body>
</html>