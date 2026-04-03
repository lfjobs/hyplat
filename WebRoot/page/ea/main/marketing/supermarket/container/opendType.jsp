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
<title>智能货柜开门方式</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/supermarket/container/opendtype.css"/>

<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript"></script>

<script>
    var basePath = "<%=basePath%>";
    var posNum = "";

    var hgcode = "";
    $(function(){

        try {
            posNum = avm.getDeviceId();

            if (posNum != "" && posNum != null) {
                posNum = isExistPosNum(posNum);

            }
        } catch (error) {
         //   if ($(window).width() == 1080 && $(window).height() == 1546) {
                posNum = "d0ebe619a81bdee4";
                posNum = isExistPosNum(posNum);


         //   }
        }

		try{
			var u = window.navigator.userAgent;
			var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
			if (isAndroid == true) {
			//	Android.getScalesList();
			}else{
			//	alert("不是安卓设备");
			}
		}catch(e){
			alert("失败:"+e);
		}

	})

      //获取二维码
    function getCodeHg(){
        document.location.href = basePath+"ea/sm/ea_getCodeHg.jspa?hgcode="+hgcode;
    }

    //获取二维码
    function getYzphone(){
        document.location.href = basePath+"page/ea/main/marketing/supermarket/container/phoneopen.jsp?posNum="+posNum+"&hgcode="+hgcode;
    }

    //刷卡开门
    function  getScanCard() {
        document.location.href = basePath+"page/ea/main/marketing/supermarket/container/scancard.jsp?posNum="+posNum+"&hgcode="+hgcode;
    }


	function setScalesList(data) {
		try{
			Android.connectDevice(data.list[0].deviceId, data.list[0].deviceIdport, 9600, true);
		}catch(e){
			alert("失败:"+e);
		}
	}

	function operationalStatus(data) {
		alert(data);
	}

    //判断设备号
    function isExistPosNum(posNum){
        var url = "<%=basePath%>ea/smg/sajax_sm_isExistPosNum.jspa";
        $.ajax({
            url : url,
            type : "get",
            dataType : "json",
            async:false,
            data : {
                posNum:posNum
            },
            success : function(data) {
                var m = eval("(" + data + ")");
                var result = m.result;
                hgcode = m.hgcode;
                if(result!="0"){
                    posNum = "";
                }

            },
            error : function(data) {
                // alert("验证失败");
            }

        });
        return posNum;
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
<div class="main-content" margin>
	<p class="p-title">请选择智能货柜开门方式：</p>
	<p class="p-img"><a href="javascript:getCodeHg();"><img src="<%=basePath%>images/supermarket/container/scan.png"><span>扫描二维码开门</span></a></p>
	<%--<p class="p-img"><a href="javascript:getYzphone();"><img src="<%=basePath%>images/supermarket/container/phone.png"><span>验证手机号开门</span></a></p>--%>
	<p class="p-img"><a href="javascript:getScanCard();"><img src="<%=basePath%>images/supermarket/container/gwk.png"><span>扫描购物卡开门</span></a></p>
</div>
</body>
</html>