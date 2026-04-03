<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="/css/WFJClient/base.css"/>
    <link rel="stylesheet" type="text/css" href="/css/ea/finance/invoicing/InitialiaeAdd_Good.css"/>
    <script type="text/javascript" charset="utf-8" src="/js/font-size.js"></script>
    <script type="text/javascript" charset="utf-8" src="/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="/js/WFJClient/localforage.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="/js/ea/finance/invoicing/InitialiaeAdd_Good.js"></script>
    <title>初始库存提交</title>
</head>
<body class="fixedhea">
<header>
    <ul class="clearfix">
        <li onclick="toBack();">
            <img src="/images/ea/finance/NewPhoneOrders/sellerOrder/Office/img-1.png" >
        </li>
        <li>
            初始库存
        </li>
    </ul>
</header>
<div class="fixedhea_box"></div>
<div class="div-kc">
    <div class="box">
        <p>请选择库仓<span id="span-close"></span></p>
        <div id="kc-load">
            <iframe marginwidth=0 marginheight=0 width="100%" id="ckiframe" style="height: 92vh" src="" frameborder="no" <!--scrolling="no"-->></iframe>
        </div>
    </div>
</div>
<div class="div-zx">
    <div class="box">
        <h4 class="">是否启用在线</h4>
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
            <p>确定</p>
        </div>
    </div>
</div>
<div class="content">
    <ul class="ul-01">
        <li>初始库存单号<p id="jum"></p></li>
        <li>初始库存时间<p id="inidate"></p></li>
        <li>商品类目<p id="codevlaue"></p><input type="hidden" id="codeid"/></li>
        <li>初始库存责任人<p id="staffname"></p></li>
    </ul>
    <ul class="ul-02">
        <li class="clearfix">名称及规格<input type="hidden" id="ppid"/><p id="goodsname"></p></li>
        <li class="clearfix">单位<p id="variableid"></p></li>
        <li class="clearfix">初始库存<input class="inp-border" type="number" value="0" id="cskc"/></li>
        <li class="clearfix">产品分类<p id="s"></p></li>
        <li class="clearfix">产品品牌<p id="brand"></p></li>
        <li class="li-zx clearfix">是否启用在线<p><span>启用</span><input type="hidden" value="01"/></p></li>
        <li class="kf clearfix">库仓<p><span id="kfname"></span><input type="hidden" id="kfid"/></p></li>
    </ul>
</div>
<div class="footer" id="okbut">
    <p>
        确定
    </p>
</div>
</body>
</html>
