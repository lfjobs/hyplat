<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
    <meta http-equiv="expires" content="0">
    <link rel="stylesheet" type="text/css" href="/css/WFJClient/base.css"/>
    <link rel="stylesheet" type="text/css" href="/css/ea/finance/invoicing/InitialiaeAdd.css"/>
    <script type="text/javascript" charset="utf-8" src="/js/font-size.js"></script>
    <script type="text/javascript" charset="utf-8" src="/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="/js/WFJClient/localforage.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="/js/ea/finance/invoicing/InitialiaeAdd.js"></script>

    <title>初始库存添加</title>
    <script type="text/javascript">
        var comid = '${param.compayid}';
        var staffid = '${param.staffid}';
        var cache = '${param.cache}';
    </script>
    <script type="text/javascript">
        function onFocus() {
            var target = event.target
            setTimeout(function () {
                target.readOnly = false
            }, 0)
        }
        function onBlur() {
            event.target.readOnly = true
        }
        var staffid = "${param.staffid}";
        var sort = "${param.sort}";
        //大屏用
        var posNum = "";//大屏id
        var dpFlag = false;//大屏标识
        try {
            //判断是否是大屏终端
            posNum = Android.forAndroidDeviceId();
            var url = "ea/smg/sajax_sm_isExistPosNum.jspa";
            $.ajax({
                url: url,
                type: "get",
                dataType: "json",
                async: true,
                data: {
                    posNum: posNum
                },
                success: function (data) {
                    var m = eval("(" + data + ")");
                    var result = m.result;
                    if (result != "0") {
                        posNum = "";
                    }
                },
                error: function (data) {
                    // alert("验证失败");
                    posNum = "";
                }
            });
            console.log('---' + posNum);
            if (posNum == null || posNum == "") {//跳转小屏
                dpFlag = false;
            } else {//跳转大屏
                dpFlag = true;
            }
        } catch (e) {
            dpFlag = false;
        }
    </script>
</head>
<body class="fixedhea">
<header>
    <ul class="clearfix">
        <li onclick="toBack();">
            <img src="/images/ea/finance/NewPhoneOrders/sellerOrder/Office/img-1.png">
        </li>
        <li>
            初始库存
        </li>
    </ul>
</header>
<div class="fixedhea_box"></div>
<div class="div-zx">
    <div class="box">
        <h4 class="">大波浪薯片烧烤味</h4>
        <ul>
            <li class="clearfix">
                <p>未启用</p>
                <div>
                    <img src="/images/ea/finance/invoicing/xuan_03.png"/>
                </div>
            </li>
            <li class="clearfix">
                <p class="active">启用</p>
                <div>
                    <img src="/images/ea/finance/invoicing/xuan_06.png"/>
                </div>
            </li>
        </ul>
        <div class="div-tj">
            <p>提交</p>
        </div>
    </div>
</div>
<form id="cskcForm">
    <div class="content">
        <ul>
            <li>初始库存单号<p id="jum"></p></li>
            <li>初始库存时间<p id="inidate"></p></li>
            <li>商品类别<p></p></li>
            <li>初始库存责任人<p id="staffname"></p></li>
        </ul>
        <div class="div-box">
        </div>
        <section class="clearfix">
            <p class="p-tj">提交</p>
            <p id="sm">扫描选物</p>
            <p id="wei">无码称重</p>
            <input type="text" id="displayIndex" style="opacity: 0;" onfocus="onFocus()" onblur="onBlur()"/>
        </section>
    </div>
</form>
<form id="formjum">
    <input type="hidden" class="companyid" name="companyid" value="${param.compayid}"/>
    <input type="hidden" class="jum" name="jum"/>
    <input type="hidden" class="inidate" name="inidate"/>
    <input type="hidden" class="staffname" name="staffname"/>
    <input type="hidden" class="staffid" name="staffid"/>
</form>
<%--<form id="formgoods">
    <input type="hidden" class="ppid" name="ppid"/>
    <input type="hidden" class="goodsname" name="goodsname"/>
    <input type="hidden" class="invenquantity" name="invenquantity"/>
    <input type="hidden" class="variableid" name="variableid"/>
    <input type="hidden" class="brand" name="brand"/>
    <input type="hidden" class="cskc" name="cskc"/>&lt;%&ndash;初始库存&ndash;%&gt;
    <input type="hidden" class="fbtype" name="fbtype"/>
    <input type="hidden" class="fbname" name="fbname"/>
    <input type="hidden" class="kfid" name="kfid"/>
    <input type="hidden" class="kfname" name="kfname"/>
    <input type="hidden" class="codeid" name="codeid"/>
    <input type="hidden" class="codevalue" name="codevalue"/>
</form>--%>
</body>
</html>


