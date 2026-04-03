<%@ page language="java" pageEncoding="UTF-8"%>
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
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<title>购物卡支付</title>
<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/marketing/supermarket/scardPay.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/style-csp.css">
<script src="<%=basePath%>js/ea/marketing/supermarket/Popt.js"></script>
<script src="<%=basePath%>js/ea/marketing/supermarket/citySet.js"></script>
<script type="text/javascript">
    $(function(){
        $("#city").click(function (e) {
            SelCity(this,e);
        });
    })
</script>

<script>
        var basePath = "<%=basePath%>";
		var journalNum = "${param.journalNum}";
		var totalMoney = "${param.totalMoney}";
		var totalNum = "${param.totalNum}";
		var token = 0;
		var stoken = 0;
        var paymentCode = "";
        var addressID = "";
        var  sccId = "";
        var posNum = "${param.posNum}";
        var directUrl = "${param.directUrl}";
        var type = "${param.type}";
        var cardNum = "${param.cardNum}";
        var paymentCode = "${param.paymentCode}";
        var vipmoney = "${param.vipmoney}";
        var sccId = "${param.sccId}";
        var fh = "${param.fh}";
	    var  openid = "${param.openid}";
	    var wxbind = "${param.wxbind}"
        var fhform = "${param.fhform}";
        var remainMoney = "${param.remainMoney}";
        $(function(){
            if(remainMoney!=""&&remainMoney!=null&&Number(remainMoney)<Number(totalMoney)){
                $(".orim").text(remainMoney);
            }



        })


</script>

</head>
<body>
<section class="code-pay card FS">
	<!--扫码支付内容-->
	<article>
		<!--头部-->
		<header>
			<img src="<%=basePath%>images/supermarket/card.png" alt="">
			<p><span>请将您的购物卡，</span><br/>
				放在扫描枪上，刷一下即可。</p>
			<a href="javascript: window.history.go(-1);"><input type="button" value="返回"></a>
		</header>
		<!--头部 end-->
		<!--内容-->
		<figure>
			<ul class="card-text">
				<li class="je">金额：<span class="orim">${param.totalMoney}</span>（共${param.totalNum}件）</li>
				<li style="display:none;" class="je hyj">会员专享价：<span class="vipmoney"></span></li>
			</ul>
			<div class="card-g">
				<p>购物卡：</p>
				<input type="password"   class="scard"   autofocus >
			</div>
			<!--输入密码-->
			<div class="mmshow" style="display:none;">
			<h3 id="mm-name"></h3>
			<p class="mm-p">请输入您的交易密码</p>
			<p style="width: 480px;height: 60px;margin: 3% auto 0 auto;position: relative;" id="srk">
				<input type="password" readonly="readonly" class="txtNum" id="txtNum"/>
				<i class="i-1"></i>
				<i class="i-2"></i>
				<i class="i-3"></i>
				<i class="i-4"></i>
				<i class="i-5"></i>
			</p>
			<table class="jp">
				<tr>
					<td> <input id="Button1" type="button" value="1" onclick="return btnNum_onclick(1)" /></td>
					<td> <input id="Button2" type="button" value="2" onclick="return btnNum_onclick(2)"/></td>
					<td> <input id="Button3" type="button" value="3" onclick="return btnNum_onclick(3)"/></td>
				</tr>
				<tr>
					<td> <input id="Button4" type="button" value="4" onclick="return btnNum_onclick(4)"/></td>
					<td> <input id="Button5" type="button" value="5" onclick="return btnNum_onclick(5)"/></td>
					<td> <input id="Button6" type="button" value="6" onclick="return btnNum_onclick(6)"/></td>
				</tr>
				<tr>
					<td><input id="Button7" type="button" value="7" onclick="return btnNum_onclick(7)"/></td>
					<td><input id="Button8" type="button" value="8" onclick="return btnNum_onclick(8)"/></td>
					<td><input id="Button9" type="button" value="9" onclick="return btnNum_onclick(9)"/></td>
				</tr>
				<tr>
					<td><input id="ButtonDel" type="button" value="删除" onclick="return delText()"/></td>
					<td><input id="Button0" type="button" value="0" onclick="return btnNum_onclick(0)"/></td>
					<td><input id="Buttond" type="button" value="."/></td>
				</tr>
				<tr>
					<td><input id="ButtonClear" type="button" value="清空" onclick="return clearText()"/></td>
					<td colspan="2"><input id="btnEnter" type="button" value="确定" /></td>
				</tr>
			</table>
			</div>
		</figure>
		<!--内容 end-->


		<!--确认收货地址弹框 2019.1.25-->
		<div class="alert_address_" style="display: none;"></div>
		<div class="alert_address" id="t1" style="display: none;">
             <h1>请确认您的收货信息</h1>
             <ul class="text">
				 <li>姓名：<span class="name"></span></li>
                 <li>电话：<span class="tel"></span></li>
                 <li>详细地址：<span class="detail"></span></li>
             </ul>
             <div class="btn">
                 <input type="button" value="修改" id="editBtn">
                 <input type="button" value="确定" id="confirmBtn">
				 <span style="display:none;" class="addressID"></span>
             </div>
         </div>

            <!--填写收货地址弹框 2019.1.25-->
	    <div class="alert_address alert_t-address" id="t2" style="display: none;">
			<form id="addressform">
              <h1 class="adtitle">请填写您的收货信息</h1>
              <ul class="text">
                  <li>姓名：<input type="text" value="" placeholder="收货人姓名" name="staffAddress.consignee" class="verification name"></li>
                  <li>电话：<input type="number" value="" placeholder="收货人联系方式" name="staffAddress.phone" class="verification tel"></li>
                  <li>地址：<input type="text" value="" placeholder="请您选择省/市/区" readonly  id="city" > </li>
                  <li>详细地址：<textarea placeholder="填写详细地址如门牌号" name="staffAddress.addressDetailed" class="verification det"></textarea></li>
              </ul>
              <div class="btn">
                  <input type="button" value="确定" onclick="addAddress()">
				  <input id="address" type="hidden" name="staffAddress.area" value="">
				  <input id="addressID" type="hidden" name="staffAddress.addressID" value="">

				  <input id="sccId" type="hidden" name="sccId" value="">
			  </div>
			</form>
          </div>
	</article>
	<!--扫码支付内容 end-->

</section>

<div class="mm-alert">
	<div>
		<h1 class="ttip">请重新扫描购物卡</h1>
		<h5 class="ct"></h5>
		<input type="button" value="确定">
	</div>
</div>

<script type="text/javascript">
    $(function () {
        x();
		/*变成星号*/
        function x() {
            var value=$('.card-g input').val();
            $('.card-g input').val(value.replace(/./g,'✲'));
        }

    });
	/*键盘*/
	/*数字1-9*/
    function btnNum_onclick(i) {
        var values = document.getElementById("txtNum").value;
        txtNum.value=txtNum.value+i;
        s();
    }
	/*点*/
    function dian() {
        var values = document.getElementById("txtNum").value;
        txtNum.value=txtNum.value+".";
    }
	/*清空*/
    function clearText() {
        document.getElementById("txtNum").value = "";
    }
	/*删除*/
    function delText() {
        var value = document.getElementById("txtNum").value;
        var str = value.substring(0,value.length-1);
        document.getElementById("txtNum").value = str;
    }
	/*密码限制6位数*/
    function s() {
        var cla = $("#txtNum");
        var values = document.getElementById("txtNum").value;
        if (cla.hasClass('txtNum')&&values.length>5){
            values = values.substring(0,6);
            cla.val(values);
        }
    }

</script>

</body>
</html>