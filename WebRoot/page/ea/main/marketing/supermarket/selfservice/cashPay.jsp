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
<title>现金支付</title>
<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/marketing/supermarket/cashPay.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/marketing/supermarket/Popt.js"></script>
<script src="<%=basePath%>js/ea/marketing/supermarket/citySet.js"></script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/style-csp.css">

<script>
        var basePath = "<%=basePath%>";
		var journalNum = "${param.journalNum}";
		var totalMoney = "${param.totalMoney}";
		var totalNum = "${param.totalNum}";
		var staffID= "${staffID}";
        var staffName= "${staffName}";

        var token = 0;
		var comID = "${comID}";
		var posNum = "${param.posNum}";
		var cardNum = "${param.cardNum}";
		var address="${param.address}";
		var fh = "${param.fh}";
		var ppid = "${param.ppid}";
		var carmID = "${param.carmID}";
        var fhform = "${param.fhform}";
        var remainMoney = "${param.remainMoney}";

        $(function(){
            if(remainMoney!=""&&remainMoney!=null&&Number(remainMoney)<Number(totalMoney)){
                    $(".tlm").text(remainMoney);
            }


            $("#city").click(function (e) {
                SelCity(this,e);

            });
        })
</script>

</head>
<body>

<section class="code-pay mm">
	<!--扫码支付内容-->
	<article>
		<!--头部-->
		<header class="teshu1">
			<img src="<%=basePath%>images/supermarket/mm.png" alt="">
			<p>请在支付安全的情况下<br/>
				<span>进行密码操作</span></p>
			<a href="javascript: window.history.go(-1);"><input type="button" value="返回"></a>
		</header>
		<!--头部 end-->
		<!--内容-->
		<figure>
			<ul class="mm-text">
				<li class="ys">应收：<span class="tlm">${totalMoney}</span>（共${totalNum}件）</li>
				<li class="oop"><p>实收：</p><input type="text" value="" class="txtNum2 bgc" onkeyup="checkBlus()" id="txtNum" readonly="readonly">元</li>
				<li class="total">找零：<span class="zlprice">0</span>元</li>
				<li style="display:none;" class="zc p">请操作员输入微分金账号</li>
				<li style="display:none;" class="zc p"><input type="password" value="" class="tel" readonly="readonly"> </li>
				<li  class="p">请操作员输入交易密码确认收款</li>
			</ul>
			<p style="width: 480px;height: 60px;margin: 3% auto 0 auto;position: relative;" id="srk">
				<input type="password" readonly="readonly" class="txtNum"  />
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
					<td><input id="Buttond" type="button" value="." onclick="return dian()"/></td>
				</tr>
				<tr>
					<td><input id="ButtonClear" type="button" value="清空" onclick="return clearText()"/></td>
					<td colspan="2"><input id="btnEnter" type="button" value="确定" onclick="confirm();"/></td>
				</tr>

			</table>
		</figure>
		<!--内容 end-->


		<!--确认收货地址弹框 2019.1.25-->
		<div class="alert_address_" style="display: none;"></div>


		<!--填写收货地址弹框 2019.1.25-->
		<div class="alert_address alert_t-address" id="t2" style="display: none;">
			<form id="addressform">
				<h1 class="adtitle">请填写您的收货信息</h1>
				<ul class="text">
					<li>姓名：<input type="text" value="" placeholder="收货人姓名" name="staffAddress.consignee" class="verification name"></li>
					<li>电话：<input type="number" value="" placeholder="收货人联系方式" name="staffAddress.phone" class="verification tel"></li>
					<li>地址：<input type="text" value="" placeholder="请您选择省/市/区" readonly id="city" > </li>
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
		<h1>请重新输入</h1>
		<h5 class="ct">你输入的实收金额少于应收金额</h5>
		<input type="button" value="确定">
	</div>
</div>
<script language="javascript" type="text/javascript">
	/*数字1-9*/
    function btnNum_onclick(i) {
        var values = document.getElementById("txtNum").value;

        txtNum.value=txtNum.value+i;
        if($("#txtNum").attr("class").indexOf("txtNum2")!=-1){

            checkBlus();
            sf();

		}else{
            s();
		}
    }
	/*点*/
    function dian() {
        var values = document.getElementById("txtNum").value;
        txtNum.value=txtNum.value+".";
        s();
        checkBlus()
    }
	/*清空*/
    function clearText() {
        document.getElementById("txtNum").value = "";
        sf()
    }
	/*删除*/
    function delText() {
        var value = document.getElementById("txtNum").value;
        var str = value.substring(0,value.length-1);
        document.getElementById("txtNum").value = str;
        sf()
    }
	/*选取实付*/
    $(".mm-text .oop input").click(function () {
        $(".txtNum").removeAttr('id');
        $(".mm-text .p .tel").removeAttr('id');
        $(this).attr('id',"txtNum");
        $(this).addClass('bgc');
        $(".mm-text .p .tel").removeClass('bgc');
    });

	/*选取密码*/
    $(".txtNum").click(function () {
        $(".mm-text .oop input").removeAttr('id');
        $(".mm-text .p .tel").removeAttr('id');
        $(this).attr('id',"txtNum");
        var ssprice = Number($(".txtNum2").val());
        var zlprice = $(".zlprice").text();
        var f = false;
        if(!$(".zc").is(":hidden")) {
			f=true;
        }
        var ssprice2 = $(".tel").val();

        if(ssprice==""||ssprice=="0"){
            $(".mm-alert .ct").text("请先输入实收金额");
            $(".mm-alert").show();
            $(".mm-text .oop input").trigger("click");
            return;
		}else if(zlprice.indexOf("-")!=-1||Number(zlprice)<0){
            $(".mm-alert .ct").text("实收金额少于应收金额");
            $(".mm-alert").show();
            $(".mm-text .oop input").trigger("click");
            return;
		}else if(f==true&&ssprice2==""){
            $(".mm-alert .ct").text("请输入微分金账号");
            $(".mm-alert").show();
            $(".tel").trigger("click");
            return;
		}

    });
	/*选取微分金账号*/
    $(".mm-text .p .tel").click(function () {
        $(this).attr('id',"txtNum");
        $(".txtNum").removeAttr('id');
        $(".mm-text .oop input").removeAttr('id');
        $(this).addClass('bgc');
        $(".txtNum2").removeClass('bgc');

    });
	/*密码现在6位数*/
    function s() {
        var cla = $("#txtNum");
        var values = document.getElementById("txtNum").value;
        if (cla.hasClass('txtNum')&&values.length>5){
            values = values.substring(0,6);
            cla.val(values);
        }
    }
	/*找零计算*/
    function sf() {
        var ys = parseFloat($(".ys span").text()*100);
        var sf = parseFloat($(".txtNum2").val()*100);
        var zl = $(".mm-text .total span");
        var zl2 = parseFloat(sf.toFixed(2)-ys.toFixed(2))/100;
        zl.text(zl2.toFixed(2));
        if (sf==""){
            zl.text(0)
        }
    }




	 function alert(){
	 var cla = $("#txtNum");
	 var ys = $(".ys span").text();
	 var sf = $(".mm-text .oop input").text();
	 if (sf<ys){
	 $(".mm-alert").show();
	 }
	 };
//	 $(".mm-text .oop input").blur(function () {
//	 alert();
//
//	 });

    //校验文本框中输入的数字是否以0开头
    function checkNum(value){
        var str = value;
        var len1 = str.substr(0,1);
        var len2 = str.substr(1,1);

        //如果第一位是0，第二位不是点，就用数字把点替换掉
        if(str.length > 1 && len1==0 && len2 != '.'){
            str = str.substr(1,1);
        }

        //第一位不能是.
        if(len1=='.'){
            str = '';
        }

        //限制只能输入一个小数点
        if(str.indexOf(".")!=-1){
            var str_=str.substr(str.indexOf(".")+1);
            //限制只能输入一个小数点
            if(str_.indexOf(".")!=-1){
                str=str.substr(0,str.indexOf(".")+str_.indexOf(".")+1);
            }
        }

        return str;
    }

    function checkBlus(){
        var rechargeMoney = $("#txtNum").val();
        var checkedValue = checkNum(rechargeMoney);
        $("#txtNum").val(checkedValue);
    }

</script>
</body>
</html>