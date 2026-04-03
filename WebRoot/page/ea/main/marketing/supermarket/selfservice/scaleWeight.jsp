<%@ page language="java" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
    <title>称重打印标签</title>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/style-cs.css">
    <script type="application/javascript" src="<%=basePath%>js/jquery-2.1.1.min.js"></script>
    <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/scaleWeight.js"></script>
    <script language="javascript" src="<%=basePath%>js/ea/marketing/supermarket/LodopFuncs.js"></script>
    <object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
        <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
    </object>
    <!-- 兼容Electron -->
    <script>if(typeof module === 'object') {window.module = module; module = undefined;}</script>


    <!-- 兼容Electron -->
    <script>if(window.module) module = window.module;</script>

</head>
<body  onbeforeunload="checkLeave()" style="min-width: auto;min-height: auto;">

<section class="comm wp">
    <!--扫码支付内容-->
    <article>
        <header></header>
        <!--内容-->
        <figure>
            <div class="alert_wed">
                <h2>称重打印标签</h2>
                <div class="wed_btn">
                    <input type="text" class = "parameter" value="" placeholder="plu/名称/拼音码" id="wed_ipt">
                    <input type="button" value="确定" id="wed_btn" class="cfm">
                    <%--<img src="<%=basePath%>images/supermarket/ico-jp.png" alt="">--%>
                </div>
                <ul class="content">
                    <li class="left">
                        <h3>热销产品</h3>
                        <ul class="con hotsale">
                            <%--<li>苹果</li>--%>

                        </ul>
                    </li>
                    <li class="right">
                        <h3 class="scale1">请选择需要称重的商品</h3>
                        <h3 class="scale2" style="display:none;">请输入商品数量</h3>
                        <ul class="con scalemain">
                            <li>
                                <h4>名称：<span class="goodsname"></span><span class="plu"></span></h4>
                            </li>
                            <li>
                                <h4>单价：<span class="price"></span>元</h4>
                                <span class="hhh" style="display: none;"></span>
                            </li>
                            <li class="weight">
                                <h4>重量：<span><input class= "wvalue" type="text" value="0" readonly="readonly">KG</span></h4>
                            </li>
                            <li class="num" style="display: none;">
                                <h4>个数：<span><input type="text" id="inputnum">pcs</span></h4>
                            </li>
                            <li>
                                <h4>合计：<span class="totalMoney"></span>元</h4>
                            </li>
                        </ul>
                        <input type="button" value="打印" class="btn" id="print">
                    </li>
                </ul>
            </div>
        </figure>
        <!--内容 end-->
        <!--键盘弹框-->
        <table class="jp-se">
            <tbody><tr>
                <td> <input id="Button1" type="button" value="1" onclick="return btnNum_onclick(1)"></td>
                <td> <input id="Button2" type="button" value="2" onclick="return btnNum_onclick(2)"></td>
                <td> <input id="Button3" type="button" value="3" onclick="return btnNum_onclick(3)"></td>
            </tr>
            <tr>
                <td> <input id="Button4" type="button" value="4" onclick="return btnNum_onclick(4)"></td>
                <td> <input id="Button5" type="button" value="5" onclick="return btnNum_onclick(5)"></td>
                <td> <input id="Button6" type="button" value="6" onclick="return btnNum_onclick(6)"></td>
            </tr>
            <tr>
                <td><input id="Button7" type="button" value="7" onclick="return btnNum_onclick(7)"></td>
                <td><input id="Button8" type="button" value="8" onclick="return btnNum_onclick(8)"></td>
                <td><input id="Button9" type="button" value="9" onclick="return btnNum_onclick(9)"></td>
            </tr>
            <tr>
                <td><input id="ButtonDel" type="button" value="删除" onclick="return delText()"></td>
                <td><input id="Button0" type="button" value="0" onclick="return btnNum_onclick(0)"></td>
                <td><input id="Buttond" type="button" value="." onclick="return dian()"></td>
            </tr>
            <tr>
                <td><input id="ButtonClear" type="button" value="清空" onclick="return clearText()"></td>
                <%--<td colspan="2"><input id="btnEnter" type="button" value="确定" ></td>--%>
            </tr>

            </tbody>
        </table>
        <!--搜索商品 2019.3.12-->
        <div class="alert_sssp_">
            <div class="alert_sssp">
                <img src="<%=basePath%>images/supermarket/ico-del-2.png" alt="" id="del-3">
                <div class="wed_btn">
                    <input type="text" class="parameter" value="" placeholder="输入plu/名称/拼音码" id="wed_ipt-3">
                    <input type="button" value="确定" id="wed_btn-3" class="cfm">
                    <img src="<%=basePath%>images/supermarket/ico-jp.png" alt="">
                </div>
                <div class="tab">
                    <table width="870" class="tab-tit">
                        <thead>
                        <tr>
                            <th style="width: 50%;" class="name">商品名称</th>
                            <th style="width: 12%;">plu</th>
                            <th style="width: 19%;">单价</th>
                            <th style="width: 19%;">计价单位</th>
                        </tr>
                        </thead>
                    </table>
                    <div class="tab-box">
                        <table width="870">
                            <tbody class="salegoods">
                            <%--<tr>--%>
                                <%--<td class="name">红富士大苹果脆甜可脆甜可</td>--%>
                                <%--<td class="hh">1</td>--%>
                                <%--<td class="tm">600元</td>--%>
                                <%--<td class="dj">KGM</td>--%>
                            <%--</tr>--%>

                            </tbody>
                        </table>
                    </div>
                    <input type="button" value="确定" id="sure2">
                </div>
            </div>
        </div>

    </article>
    <!--扫码支付内容 end-->
</section>
<script type="text/javascript">
    var basePath="<%=basePath%>";
    var companyID = "${companyID}";

</script>

<script>
$(function () {
//100毫秒一次获取秤的重量和稳定值
setInterval(function () {

$.ajax({
url: "http://127.0.0.1:5017/api/Scale/Weight",
dataType: "json",
type: 'get',
cache: false,//IE下要关闭cache，否则不会刷新
data: null,
success: function (data) {
  //  var v = parseFloat(value / 1000).toFixed(3);
    var  v = data.Weight.toFixed(3);

    if (v!="0.000") {
        $(".wvalue").val(v);
        var price = $(".scalemain .price").text();
        if(!$(".weight").is(':hidden')) {
            $(".totalMoney").text((Number(price) * Number(v)).toFixed(2));
        }
    }


},
error: function (XMLHttpRequest) {
alert("获取重量失败, " + XMLHttpRequest.responseJSON.Message);
}
});
}, 100);
})
</script>




<div class="alert_weigh_">
    <div class="alert_weigh">
        <h2 class="tipcon">搜索无结果</h2>
        <input type="button" value="确定" id="confirm">
    </div>
</div>
</body>
</html>