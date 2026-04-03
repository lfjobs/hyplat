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
<title>授权</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>

<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/supermarket/container/auth.css"/>

<script>
        var basePath = "<%=basePath%>";
        var hgcode = "${hgcode}";
        var door = "${param.door}";
        var sccId = "${sccId}";
	$(function () {

	    //暂时不开通
        $(".close-tingyong").click(function(){
            $(".div-tingyong").hide();
            if(door!=null&&door!=""){
                var url = basePath+"ea/sm/sajax_ea_openDoor.jspa";
                $.ajax({
                    url:url,
                    type:"get",
                    dataType:"json",
                    async:false,
                    data:{
                        door:door,
                        hgcode:hgcode,
						sccId:sccId
                    },
                    success:function (data) {
                        var  me = eval("("+data+")");
                        var hrId = me.hrId;
                        //手机端显示
                        document.location.href = basePath+"ea/sm/ea_getOpenSuc.jspa?hgcode=${hgcode}&sccId="+sccId+"&loginMode=scan&hrId="+hrId;

                    },
                    error:function (data) {

                    }


                })

            }else{
                document.location.href = basePath+"/page/ea/main/marketing/supermarket/container/selectDoor.jsp?hgcode=${hgcode}&sccId="+sccId+"&loginMode=scan";
            }



        })

    })

	function setKt(coinfee){
        var url = basePath+"ea/sm/sajax_ea_setKt.jspa";
        $.ajax({
            url:url,
            type:"get",
            dataType:"json",
            async:false,
            data:{
                coinfee:coinfee,
                hgcode:hgcode
            },
            success:function (data) {
                $(".div-tingyong").show();
                $(".titlep").text("操作成功");
            },
            error:function (data) {

            }


        })
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
			用户授权
		</li>
	</ul>
</header>
<div class="main-content">
	<p class="p-img"><img src="<%=basePath%>images/supermarket/container/auth.png"></p>
    <p class="p-title">开通"数字地球"货柜购物关门自动扣费结算服务?</p>
	<p class="p-detail">开通后关门结算会自动优先扣除数字地球账户的积分或者金币。</p>

	<p class="p-btn"><input type="button" value="暂不开通" onclick="setKt('0')"/><input type="button" value="立即开通" onclick="setKt('1')"/></p>
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