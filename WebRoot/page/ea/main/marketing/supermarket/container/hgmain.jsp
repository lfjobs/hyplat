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
<title>数字地球智能货柜</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>

<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/supermarket/container/hgmain.css"/>

<script>
    var basePath = "<%=basePath%>";
    var posNum = "26a261b7fe51032df994ec8bf9b107083";//     26a261b7fe51032df994ec8bf9b107083
    $(function(){
        try {
            posNum = avm.getDeviceId();


            if (posNum != "" && posNum != null) {
                posNum = isExistPosNum(posNum);

            }
        } catch (error) {

            if ($(window).width() == 1080 && $(window).height() == 1546) {
                posNum = 123;

            }
        }
    })
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
   //配送或则自助结算
	function zy(p){
      $.ajax({
          url:basePath+"ea/sm/sajax_ea_getCcomByPosNum.jspa",
		  type:"get",
		  dataType:"json",
		  async:false,
		  data:{
             posNum:posNum
		  },
		  success:function(data){
                var m = eval("("+data+")");
                var ccompanyID = m.ccompanyID;
                var companyName = m.companyName;
                var industryType = m.industryType;

                if(p=="p"){
                   document.location.href = basePath+"ea/smg/sm_toSupermarketGoods.jspa?ccompanyID="+ccompanyID+"&industryType="+industryType+"&companyName="+companyName+"&posNum="+posNum;
				}else{
                    document.location.href = basePath+"ea/sm/ea_qdlist.jspa?posNum="+posNum+"&ccompanyID="+ccompanyID+"&fhform=1";
					//document.location.href = basePath+"page/ea/main/marketing/supermarket/container/productCodeOpenDoor.jsp?posNum="+posNum+"&ccompanyID="+ccompanyID;

				}
		  },
		  error:function(data){

		  }

	  })

	}
</script>

</head>
<body>
<header>
	<ul class="clearfix">
		<li>
		&nbsp;
		</li>
		<li>
			数字地球智能货柜
		</li>
	</ul>
</header>
<div class="main-content">
	<p class="p-title">请选择购物方式：</p>
	<p class="p-img p-img1"><a href="<%=basePath%>page/ea/main/marketing/supermarket/container/opendType.jsp"><img src="<%=basePath%>images/supermarket/container/znhg.png"><span>货柜自提购物</span></a></p>
	<p class="p-img p-img1"><a onclick=zy("p")><img src="<%=basePath%>images/supermarket/container/peisong.png"><span>配送电商购物</span></a></p>
	<p class="p-img p-img2"><a <%--onclick=zy("z")--%> href="<%=basePath%>page/ea/main/marketing/supermarket/container/productCodeOpenDoor.jsp"><img src="<%=basePath%>images/supermarket/container/mendian.png"><span>自助扫码购物</span></a></p>
</div>
</body>
</html>