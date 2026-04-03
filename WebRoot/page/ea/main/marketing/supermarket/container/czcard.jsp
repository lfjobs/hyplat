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
<title>购物卡充值</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>

<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/supermarket/container/scancard.css"/>

<script>
    var basePath = "<%=basePath%>";
    var stoken = 0;
    var posNum = "${param.posNum}";
    var sccId = "";
    var paymentCode = "";

    //扫描购物卡自动结算
    document.onkeydown = function(evt){//捕捉回车
        evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
        var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
        if (key == 13) { //判断是否是回车事件。
         var   scard = $(".scard").val();
            if(scard!="") {


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
                        var sccId = member.sccId;



                            var ct = "";
                            if (result == "1") {
                                ct = "此卡无效可联系工作人员确认";
                                $(".scard").val("");
                                stoken = "0";
                                alertDiv(ct);
                                return false;
                            }
                            document.location.href = basePath+"ea/sm/ea_integralTopup.jspa?sccId="+sccId+"&posNum="+posNum+"&fhform=3";






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

        $(".close-tingyong").click(function(){
            $(".div-tingyong").hide();

        })


		$(".scard").blur(function(){
            $(".scard").focus();

		})


    })
    function alertDiv(t){

        $(".titlep").text(t);
        $(".div-tingyong").show();
	}

    //模拟安卓开门接口
	function  openDoor(){
           alert("开门成功");

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
			购物卡充值
		</li>
		<li>


		</li>
	</ul>
</header>
<div class="main-content">
	<p class="p-img1"><img src="<%=basePath%>images/supermarket/container/czcard.png"></p>
	<p class="p-title">请对准扫描口扫描要充值的购物卡条码</p>
	<p class="p-detail">购物卡：<input type="password" autofocus class="scard"></p>

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