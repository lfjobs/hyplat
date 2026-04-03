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
<title>开门成功</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>

<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/supermarket/container/selectDoor.css"/>

<script>
    var basePath = "<%=basePath%>";
    var hgcode = "${hgcode}";
    if(hgcode==""){
        hgcode="${param.hgcode}";
	}
    var sccId = "${sccId}";
    if(sccId==""){
        sccId="${param.sccId}";
    }
    var loginMode = "${param.loginMode}";
    var posNum = "${param.posNum}";
   function selectDoor(doornum){

         //调用后台进行推送到到终端机

       var url = basePath+"ea/sm/sajax_ea_openDoor.jspa";
       $.ajax({
           url:url,
           type:"get",
           dataType:"json",
           async:false,
           data:{
               hgcode:hgcode,
               door:doornum,
               sccId:sccId
           },
           success:function (data) {
               var  me = eval("("+data+")");
               var hrId = me.hrId;

               document.location.href = basePath+"ea/sm/ea_getOpenSuc.jspa?hgcode="+hgcode+"&posNum="+posNum+"&sccId="+sccId+"&loginMode="+loginMode+"&hrId="+hrId;
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
			选择柜门
		</li>
	</ul>
</header>
<div class="main-content">

	<p class="p-detail">货柜编号：<span class="span-name">${hgcode eq ""||hgcode eq null?param.hgcode:hgcode}</span></p>
	<p class="p-img"><img src="<%=basePath%>images/supermarket/container/door.png"></p>
	<p class="p-title">请选择开左边门还是右边门?</p>
	<p class="p-btn"><span onclick="selectDoor(1)">左门</span>&nbsp;&nbsp;&nbsp;<span onclick="selectDoor(2)">右门</span></p>



</div>
</body>
</html>