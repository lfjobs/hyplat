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
<title>刷卡开门</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>

<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/supermarket/container/scancard.css"/>

<script>
    var basePath = "<%=basePath%>";
    var stoken = 0;
    var posNum = "${param.posNum}";
    var hgcode = "${param.hgcode}";
    var sccId = "";
    var paymentCode = "";

    //扫描购物卡自动结算
    document.onkeydown = function(evt){//捕捉回车
        evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
        var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
        if (key == 13) { //判断是否是回车事件。
            if(stoken==0) {
                scard = $(".scard").val();

            }else{
                return false;
            }
            if(scard!="") {
                stoken  = 1;

                var ulp = basePath
                    + "/ea/sm/sajax_ea_checkHgcard.jspa";
                $.ajax({
                    type : "GET",
                    url : ulp,
                    async : true,
                    dataType : "json",
                    data:{
                        scard:scard
                    },
                    success : function(data) {
                        var member = eval('(' + data + ')');
                        var result = member.result;

                        paymentCode = member.paymentCode;//2009201806378943612

                        if(result!="0") {
                            var ct = "";
                            if (result == "1") {
                                ct = "此卡无效可联系工作人员确认";
                                $(".scard").val("");
                                stoken = "0";
                                alertDiv(ct);
                                return false;
                            }
                            var r = member.r;

                            if(r=="1"){

                                var journalNum = member.journalNum;
                                var totalMoney = member.totalMoney;
                                var totalNum = member.totalNum;
                                var ccompanyID = member.ccompanyID;
                                var comID = member.comID;
                                var companyName = member.companyName;
                                var remainMoney = member.remainMoney;
							  	document.location.href = basePath + "page/ea/main/marketing/supermarket/selfservice/payMode.jsp?journalNum="+journalNum+"&totalMoney="+totalMoney+"&totalNum="+totalNum+"&posNum="+posNum+"&staffID=&ccompanyID="+ccompanyID+"&comID="+comID+"&directUrl=&companyName="+companyName+"&fh=1&fhform=3&remainMoney="+remainMoney+"&lastPay=yes";
                                 return false;
                            }

                            if (result == "2") {
                                ct = "余额不足请充值或者更换开门方式";
                                $(".scard").val("");
                                stoken = "0";
                                alertDiv(ct);
                            }


                        }else{

                            if (paymentCode != "" && paymentCode != null) {
                                $(".mmshow").show();
                                sccId = member.sccId;
                            }



                        }


                    },
                    error : function(data) {
                        console.log("查询支付结果失败");
                    }
                });
            }
            return false;
        }
    }
    $(function () {
        //输入密码后确定
        $("#btnEnter").click(function(){

            var pw = $(".txtNum").val();
            if(pw==""){
                alertDiv("请输入密码");

            }else if(pw.length!=6){
                alertDiv("请输入6位密码");

                $(".txtNum").val("");

            }else if(pw!=paymentCode){
                alertDiv("交易密码错误 请重新输入");
                $(".txtNum").val("");
            }else{
                //可正常开门
                  openDoor();
//                document.location.href = basePath+"ea/sm/ea_getOpenSuc.jspa?posNum="+posNum+"&sccId="+sccId+"&loginMode=scard";

            }


        });
        $(".close-tingyong").click(function(){
            $(".div-tingyong").hide();

        })


		$(".scard").blur(function(){
            $(".scard").focus();

		})
		$(".cz").click(function(){

            document.location.href = basePath+"page/ea/main/marketing/supermarket/container/czcard.jsp?posNum="+posNum;

        })

    })
    function alertDiv(t){

        $(".titlep").text(t);
        $(".div-tingyong").show();
	}

    //模拟安卓开门接口
	function  openDoor(){
        var url = basePath + "ea/sm/sajax_ea_addHgRelateUser.jspa";
        $.ajax({
            type: "POST",
            url: url,
            async: false,
            dataType: "json",
            data: {
                hgcode:hgcode,
                sccId:sccId,
				loginMode:"scard"
            },
            success: function (data) {
                document.location.href = basePath+"/page/ea/main/marketing/supermarket/container/selectDoor.jsp?hgcode=" + hgcode + "&sccId=" + sccId+"&loginMode=scard&posNum="+posNum;



            },
            error: function (data) {

            }
        });

	}


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

</head>
<body>
<header>
	<ul class="clearfix">
		<li>
			<a onclick="javascript: window.history.go(-1);return false;"  target="_self">
				<img src="<%=basePath%>images/ea/office/contract/selectp/return.png" />
			</a>
		</li>
		<li>
			刷购物卡开门
		</li>
		<li class="cz">

			购物卡充值
		</li>
	</ul>
</header>
<div class="main-content">
	<p class="p-img"><img src="<%=basePath%>images/supermarket/container/scancard.png"></p>
	<p class="p-title">请对准扫描口扫描购物卡条码</p>
	<p class="p-detail">购物卡：<input type="password" autofocus class="scard"></p>
	<!--输入密码-->
	<div class="mmshow" style="display: none;">

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


</div>


<!--表单提示-->
<div class="div-tingyong">
	<div class="box">
		<p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
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