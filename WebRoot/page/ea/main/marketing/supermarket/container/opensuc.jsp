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
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/supermarket/container/opensuc.css"/>

<script>
    var basePath = "<%=basePath%>";
    var cplist = "";
    var   weights = "";
    var staffID = "${staffID}";
    var posNum = "${param.posNum}";
    var sccId = "${sccId}";
    var loginMode = "${param.loginMode}";
    var  hgcode = "${hgcode}";
    var nowWeight = 0;
    var initWeight = 0;
    var  t = 0;
    var status = "";
    var hrId = "${hrId}";
    if(hrId==""){
        hrId = "${param.hrId}";
	}
    var door="${param.door}";
    var opened  = "";
    var ii = 0;

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
            //  posNum = "26a261b7fe51032df994ec8bf9b107083";
        if(posNum!=null&&posNum!=""){
//            cplist="000002,0-1234567,0-123456,0-000007,0-000009,0";
//
//           setTimeout(function(){  onDoorsClosed("") }, 3000);

        }else{

            checkStatus();


        }

    })
    //
function checkStatus(){
    //手机端 判断是否关门
    var url = basePath + "ea/sm/sajax_ea_checkStatus.jspa";
    $.ajax({
        type: "POST",
        url: url,
        async: false,
        dataType: "json",
        data: {
            hgcode:hgcode
        },
        success: function (data) {
            var mem = eval("("+data+")");
            var doorState = mem.doorState;

            if(doorState=="0"){//没开成功  关门了，清空了
                document.location.href = basePath+"page/ea/main/marketing/supermarket/container/openfail.jsp";

            }else if(doorState==""||doorState==null||doorState=="null") {
                checkStatus();//说明开的过程中
            }else if(doorState=="1"){
                //开门成功后等着关门
                setInterval(function(){  jumpNext() }, 2000);

            }

        },
        error: function (data) {

        }
    });

}

	//跳转到支付或者成功页面或者未购物
   function jumpNext(){
                //说明开门又有关门了
            var url = basePath + "ea/sm/sajax_ea_jumpNext.jspa";
            $.ajax({
                type: "POST",
                url: url,
                async: false,
                dataType: "json",
                data: {
                    hgcode:hgcode,
					sccId:sccId,
                    hrId:hrId
                },
                success: function (data) {
                    var mem = eval("("+data+")");
                    var cashid  = mem.cashid;
                    if(cashid!="") {
                        if (cashid == "03") {
                            //未买东西
                            document.location.href = basePath + "page/ea/main/marketing/supermarket/container/mmdxsuc.jsp";

                        } else if (cashid == "04") {
                            //正在购买中
                        } else {

                            //未付款，跳转付款页面
                            document.location.href = basePath + "ea/pobuy/ea_getCashBill.jspa?cbid=" + cashid + "&sccId=" + sccId+"&lastPay=close";

                        }
                    }


                },
                error: function (data) {

                }
            });




   }

//    //读取称重数量 data 编号，重量|编号，重量|编号，重量 比如是这种形式
    function onWeightChange(data) {
         //  alert(data);

      //  if(t==1){
         //   return "OK";
		//}

        weights = "";
//
  //var data = '[{ "code":"000002", "weight":4.46, "tare":1, "startAd":1, "zeroAd":1, "currentAd":1},{"code":"123457", "weight":0, "tare":1, "startAd":1, "zeroAd":1, "currentAd":1},{"code":"123456", "weight":0, "tare":1, "startAd":1, "zeroAd":1, "currentAd":1}]';

     var  jsonArray = JSON.parse(data);
     var bc = 1;
        for (var  i = 0; i < jsonArray.length; i++) {
            var arry = jsonArray[i];
            var code = arry.scaleCode;
			var weight = arry.weight;
			var stability = arry.stability;

//			if(stability==false){
//                bc = 0;
//			}
         //   $(".fs").text(code+":"+stability);

//            alert(weight);
            weights+=code+","+weight+"#";
          //  $(".span-weight").text(weights);

        }
      //  alert(bc);
  //  weights+="000002,0.43-1234567,0-123456,0-";

      //  if(bc==1){
		   if(ii==0) {
               cplist=weights;
               $(".span-weight").text(weights);
               ii=1;
           }
        //    compare(cplist,weights);
	//	}

        return "OK"
    }
   var close = "0";
    function onDoorsClosed(data){

        close = "1";
        weights = "";
        var  jsonArray = JSON.parse(data);
        var bc = 1;
        for (var  i = 0; i < jsonArray.length; i++){
             var arry = jsonArray[i];
             var code = arry.scaleCode;
            var weight = arry.weight;

            weights+=code+","+weight+"#";
            $(".span-weight").text(weights);


        }
       // 5weights+="123456,0-000008,0-000009,0-000007,0-000002,0-12347,0";
     // weights+="000002,2.66-1234567,0-123456,0-000007,0-000009,2.038";
     //  $(".span-weight").text(jsonArray.length+"="+weights);2.805
        compare(cplist,weights)

        return "OK"

	}
    /**
	 *
	 * 开门失败openfail
     */
    function openfail(){
        var url = basePath + "ea/sm/sajax_ea_mmdx.jspa";
        $.ajax({
            type: "POST",
            url: url,
            async: false,
            dataType: "json",
            data: {
                hgcode:hgcode
            },
            success: function (data) {
                document.location.href = basePath+"page/ea/main/marketing/supermarket/container/openfail.jsp?posNum="+posNum;



            },
            error: function (data) {

            }
        });
	}


    function compare(cplist,weights) {
//        var bool = false;
//        var arr = weights.substring(0, weights.length - 1).split('-');//秤
//        var arr2 = cplist.substring(0, cplist.length - 1).split('-');//库存
//        var i = 0;
//        for (p in arr) {
//            var arr1 = arr[p].split(',');
//            var cpcode = arr1[0];
//            var cpweight = arr1[1];
//            nowWeight = nowWeight + Number(cpweight);//把所有秤现在的重量累加
//
//            console.log(arr1[0]);
//            console.log(arr1[1]);
//            for (p1 in arr2) { //盘点库存的
//                var arr21 = arr2[p1].split(',');
//                if (arr21 != "") {
//                    var cpcode1 = arr21[0];
//                    var cpweight1 = arr21[1];
//                    if (i == 0) {
//                        initWeight = initWeight + Number(cpweight1);//初始库存
//                    }
//                    if (cpcode1 == cpcode) {//库存和秤盘对上了
//                        if (Number(cpweight1) > Number(cpweight)) {//重量减少了
//                            bool = true
//                        }
//
//                    }
//                }
//            }
//
//            i++;
//        }

   //     if (bool == true && nowWeight < initWeight) {

//             if(cplist==""){
//                 cplist = weights;
//			 }

            //说明重量有变化，有检出拿货了，加入到购物车，跳转到商品列表
            var url = basePath + "ea/sm/sajax_ea_addCartHg.jspa?n=" + new Date();
            $.ajax({
                type: "POST",
                url: url,
                async: false,
                dataType: "json",
                data: {
                    weights: weights,
                    cplist: cplist,
                    posNum: posNum,
                    staffID: staffID,
                    hgcode: hgcode,
                    close: close,
                    hrId:hrId


                },
                success: function (data) {
                    var m = eval("(" + data + ")");
                   // var ccompanyID = m.ccompanyID;
//                    if (ccompanyID != "1") {
//                        $(".sucs").text("zz" + weights);
//                        //t = 1;
//                        //     document.location.href = basePath+"ea/sm/ea_qdlist.jspa?posNum="+posNum+"&ccompanyID="+ccompanyID+"&fhform=3&sccId="+sccId+"&loginMode="+loginMode+"&hgcode="+hgcode+"&nowWeight="+nowWeight+"&cplist="+cplist+"&initWeight="+initWeight;
//
//                    }

                },
                error: function (data) {
                    //商品加入购物车失败
                    console.log("数据处理失败!");
                    return;
                }
            });

     //   }
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
			<%--<a onclick="javascript: window.history.go(-1);return false;"  target="_self">--%>
				<%--<img src="<%=basePath%>images/ea/office/contract/selectp/return.png" />--%>
			<%--</a>--%>
		</li>
		<li>
			开门成功
		</li>
	</ul>
</header>
<div class="main-content">
	<p class="p-img"><img src="<%=basePath%>images/supermarket/container/open.png"></p>
	<p class="p-title">货柜编号：<span class="span-name">${hgcode}</span><span class="span-weight"></span></p>
	<p class="p-detail">当前用户：<span class="span-name">${staffname}</span><span class="fs"></span><span class="sucs"></span>&nbsp;正在选购商品</p>
	<p class="p-detail">请勿关闭该页面</p>


</div>
</body>
</html>