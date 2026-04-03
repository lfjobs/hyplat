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
<title>二维码</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>

<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/supermarket/container/codehg.css"/>

	<title> 扫二维码 </title>
	<script src="<%=path%>/js/qrcode.js"></script>
	<script type="text/javascript" src="<%=path%>/js/jquery-2.0.0.min.js"></script>
	<script type="text/javascript">
        var basePath="<%=basePath%>";
        var hgcode = "${hgcode}";
        var posNum = "${param.posNum}";
        window.onload = function() {

            var qrcode;
            var width=$(window).width();

            if(width>960){
                width=width/3;
            }

            qrcode = new QRCode(document.getElementById("qrcode"), {
                width: width*0.7,//设置宽高
                height: width*0.7
            });
            qrcode.makeCode(basePath + "/ea/sm/ea_getScanhg.jspa?hgcode="+hgcode);



        }
        var timer = "";
       $(function(){

          //  timer = setInterval(searchResult,2000);

	   });


        /**
         *
         * 定时查询扫码结果
         */
        function searchResult(){
            var ulp = basePath
                + "/ea/sm/sajax_ea_searchScanResult.jspa";
            $.ajax({
                type : "GET",
                url : ulp,
                async : true,
                dataType : "json",
                data:{
                    hgcode:hgcode
                },
                success : function(data) {
                    var member = eval('(' + data + ')');
                    var sccId = member.sccId;
                    if(sccId!=""){
                        window.clearInterval(timer);
                        document.location.href = basePath+"ea/sm/ea_getOpenSuc.jspa?hgcode="+hgcode+"&posNum="+posNum+"&loginMode=scan&sccId="+sccId;
					}


                },
                error : function(data) {
                    console.log("查询结果失败");
                }
            });


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
			数字地球智能货柜
		</li>
	</ul>
</header>
<div class="main-content">


	<p class="p-title">智能货柜编号：${hgcode}</p>
	<div  id="qrcode" >

	</div>

	<p class="p-detail">请用微信/支付宝/数字地球APP扫描上方二维码</p>

</div>

</body>
</html>
