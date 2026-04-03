<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no, email=no" />
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<script
	src="<%=basePath%>js/ea/production/cprocedure/qrshare/setHtmlFont.js"
	type="text/javascript"></script>
<link rel="stylesheet" href="<%=basePath%>/css/BuildPlatform/base.css">
<link rel="stylesheet"
	href="<%=basePath%>/css/ea/production/qrshare/site_manger.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/css/ea/production/qrshare/flip.css" />
<script src="<%=basePath%>/js/jquery.min.js"></script>
<script src="<%=basePath%>/js/qrcode.js"></script>

<title>场地管理详情</title>
</head>

<script>
     var basePath = '<%=basePath%>';
     var siteid = '${obj[0]}';
     
     var judge = "${judge}";
     var result = "${result}";
     var ppid = "${ppid}";
     var carmID = "${carManage.carmID}";
    
     
</script>

<body>
	<header class="com_head">
		<a href="javascript:void(0);" class="back"
			onclick="javascript: window.history.go(-1);return false;"></a>
		<h1>
			<c:if test="${param.paysuc eq 'suc'}">支付成功</c:if>
			<c:if test="${param.paysuc ne 'suc'}">场地管理详情</c:if>
		</h1>
	</header>
	<div class="alert_">
		<div class="alert">
			<img src="<%=basePath%>/images/ico-t.png" class="left">
			<p>提示:未设置收费标准,请前往系统-场地管理中设置</p>
			<img src="<%=basePath%>/images/ico-delete.png" class="delete">
		</div>
	</div>
	<div class="wrap_page" style="margin-top: 1.16rem">
		<dl class="site_det_box">
			<dt>
				<div class="tip">${obj[2]}</div>
       <c:if test="${result ne '1' }">
				<div class="site_address">${obj[6]}</div>
          </c:if>
					<div id="qrcode"></div>
			</dt>
			<c:if test="${result ne '1' }">
				<dd>
					<span class="dd_L"> 停车位： </span> <span class="dd_R">
						${parkingCode} </span>
				</dd>
				<dd>
					<span class="dd_L"> 编号： </span> <span class="dd_R"> <c:choose>
							<c:when test="${obj[7]==null}">
								<c:choose>
									<c:when test="${obj[3]==null}">
		   						   识别错误
		   						</c:when>

									<c:otherwise>
		   						   未出库
		   						</c:otherwise>
								</c:choose>
							</c:when>

							<c:otherwise>
   						   ${obj[7]}
   						</c:otherwise>
						</c:choose> </span>
				</dd>
				<dd>
					<span class="dd_L"> 车牌号： </span> <span class="dd_R">
						${obj[1]} </span>
				</dd>
				<dd>
					<span class="dd_L"> 进入时间： </span> <span class="dd_R">
						${obj[3]} </span>
				</dd>
				<dd>
					<span class="dd_L"> 离开时间： </span> <span class="dd_R">
						${obj[4]} </span>
				</dd>
				<dd>
					<span class="dd_L"> 停留时间： </span> <span class="dd_R">
						${obj[5]} </span>
				</dd>
				<dd class="paydd">
					<span class="dd_L"> 支付类型： </span> <span class="dd_R"> <c:if
							test="${obj[11] eq null}">
							<c:choose>
								<c:when test="${obj[10]=='00'}">
						   现金支付
						</c:when>
								<c:when test="${obj[10]=='01'}">
						   金币/积分支付
						</c:when>
								<c:otherwise>
						   未离开
						</c:otherwise>
							</c:choose>
						</c:if> <c:if test="${obj[11] ne null}">
							<c:choose>
								<c:when test="${obj[11]=='00' }">
						    未结算
						  </c:when>
								<c:when test="${obj[11]=='01' }">
						  包天或包月或包年
						  </c:when>
								<c:when test="${obj[11]=='02' }">
						   金币/积分收费
						  </c:when>
								<c:when test="${obj[11]=='03' }">
						   扫码支付
						  </c:when>
								<c:when test="${obj[11]=='04' }">
						 现金支付
						  </c:when>
						  <c:when test="${obj[11]=='05' }">
						 消费0元
						  </c:when>
								<c:otherwise>
						   未离开
						</c:otherwise>
							</c:choose>
						</c:if> </span>
				</dd>
				<dd>
					<span class="dd_L"> 支付金额： </span> <span class="dd_R"> <c:choose>
							<c:when test="${obj[9]==null}">
						   0元
						</c:when>

							<c:otherwise>
						   ${obj[9]}元
						</c:otherwise>
						</c:choose> </span>
				</dd>
				<dd>
					<span class="dd_L"> 停车流水号： </span> <span class="dd_R">
						${obj[8]} </span>
				</dd>
				<dd></dd>
			</c:if>
		</dl>
		<div class="pay">
			<input type="button" value="打印小票" id="topay" />
		</div>

		<c:if test="${param.auditStatus eq '00'}">
		<div  class="fee">
			<input type="button" value="设置免费时长" id="t1" onclick="feeset()"/>
		</div>
		</c:if>
		<c:if test="${param.auditStatus eq '01'}">
			<div  class="fee">
				设置免费时长审核中......
			</div>
		</c:if>
		<c:if test="${param.auditStatus eq '02'}">
			<div  class="fee">
				       设置免费时长审核通过
			</div>
		</c:if>
		<c:if test="${param.auditStatus eq '03'}">
			<div  class="fee">
				设置免费时长被驳回
			</div>
		</c:if>
<c:if test="${isfAudit eq '00'}">
		<div  class="fee">
			<input type="button" value="添加至免人工审核" class="addFee"/>
		</div>
</c:if>
	</div>
	<form name="form" id="form" method="post">
		<input type="submit" name="submit" style="display: none;"/>
		<input type="hidden" id="carNumber" name="carNumber"  value="${obj[1]}"/>
		<input type="hidden" name="posNum"  value="${param.posNum}"/>
		<input type="hidden"  id="siteId"  name="siteId"  value="${obj[15]}"/>
		<input type="hidden"  id="siteName"  name="siteName"  value="${obj[2]}"/>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" id="myIframe"
				framespacing="0" height="0"></iframe>
	</form>

</body>
<script>
	$(".alert_ .alert").attr("style","min-height:"+$(window).width()*0.12+"px;");
    $(".alert_ .alert p").attr("style","font-size:"+$(window).width()*0.039+"px;line-height:"+$(".alert_ .alert").height()+"px;");
    $(".alert_ .alert .left").attr("style","margin-top:-"+$(".alert_ .alert .left").height()*0.5+"px;");
    var content = "${param.content}";
    var paysuc = "${param.paysuc}";
    var companyId = "${obj[12]}";
	if(judge!=null && judge!=""){
		$(".alert_").hide();
	}else{
	   if(result=="1"){
	     $(".alert_").hide();
	  }     
	}
	
	if(result=="1"){
	  $(".tip").text(content);
	  $(".site_det_box dt").css("border-bottom","none");
	  $(".site_det_box div").css({"white-space":"normal","word-break":"break-all"});
	}
	$(".alert_").find(".delete").click(function(){
		$(this).parents(".alert_").hide();
	})
    var posNum = "${param.posNum}";
	$(function(){

	if(posNum!=null&&posNum!=""){
	   if(content!=null&&content.indexOf("二维码支付")!=-1){
	     var carNum = content.substr(2,content.indexOf(",")-2);
	    
	   	       // 创建二维码
          var   qrcode = new QRCode(document.getElementById("qrcode"), {
            width : 400,//设置宽高
            height :400
         });

           qrcode.makeCode(basePath + "ea/restaurant/ea_scancode.jspa?scancode=09f&equip=${obj[13]}&carNum="+encodeURI(carNum));
      //终端机自动跳转
	      setTimeout(function () {
                document.location.href = basePath+"ea/qrshare/ea_jumpManagement.jspa?posNum="+posNum;
            }, 600000);
	

	   }else{
	   
	      //终端机自动跳转
	      setTimeout(function () {
                document.location.href = basePath+"ea/qrshare/ea_jumpManagement.jspa?posNum="+posNum;
            }, 20000);
	
	   }
	
	
	     
	  if($(".site_det_box").find("dd").length>0){
	     $(".pay").show();
	   }
    	
	}else{
	 $(".pay").hide();
	}

	 if($(".paydd").length>0){
	    if(paysuc=="suc"){
	      $("#topay").val("打印小票");
	      $(".paydd").find(".dd_R").text("现金支付");
	    }else{
	     if($.trim($(".paydd").find(".dd_R").text())=="未结算"){
	         $("#topay").val("现金支付");
	    }else{
	         $("#topay").val("打印小票");
	    }
	    }
	  }
//现金支付或者打印小票
$("#topay").click(function(){
	if($(this).val()=="现金支付"){
	 var journalNum = "";
	 var totalMoney ="${obj[9]}";
	 
	// 生成订单号
	$.ajax({
		url : basePath + "/ea/assicode/sajax_ea_getJum.jspa",
		type : "get",
		aysnc : false,
		data : {
		     posNum:posNum,
            "payBackupBill.coID":carmID

		},
		success : function suc(data) {
			var mb = eval("(" + data + ")");
			   journalNum = mb.jum;
	       document.location.href=basePath+"ea/sm/ea_showCash.jspa?journalNum="+journalNum+"&totalMoney="+totalMoney+"&totalNum=1&posNum="+posNum+"&comID="+companyId+"&ppid="+ppid+"&carmID="+carmID;

		}
	});
     }else{
        //打印小票
        printTicket();
     }
    });


      //添加至人工免审核
        $(".addFee").click(function(){

            $("#form").attr("target", "hidden").attr("action",
                basePath + "ea/carmanage/ea_addFeeCar.jspa");

            document.form.submit.click();
            token = 13;
        });



    });
	 
//打印小票
function printTicket(){
    var url = basePath + "/ea/carmanage/sajax_ea_printInfo.jspa";
    $.ajax({
        url: encodeURI(url),
        type: "post",
        dataType: "json",
        data: {
            carmID: carmID
        },
        async: false,
        success: function (data) {
            var jsonresult = eval("(" + data + ")");
            var obj = jsonresult.result;
            var array = {};
            array["carmID"] = carmID;
            array["siteName"] = obj[7];
            array["parkingCode"] = obj[2];
            array["serialNumber"] = obj[0];
            array["carNum"] = obj[1];
            array["indate"] = obj[3];
            if(obj[10]=="0"){
                array["outdate"] = obj[4];
                array["time"] = obj[5];
                array["money"] = obj[6];
            }
            if(obj[9]!=null&&obj[9]!=""){
               var chargeState = "";
               if(obj[9]=="01"){
                  chargeState = "包天或包月或包年";
               }else if(obj[9]=="02"){
                  chargeState = "金币积分支付";
               }else if(obj[9]=="03"){
                  chargeState = "扫码支付";
               }else if(obj[9]=="04"){
                  chargeState = "现金支付";
               }else if(obj[9]=="05"){
                  chargeState = "不超过半小时不收费";
               }
                array["chargeState"] = chargeState;
            }
           
            array["siteAddress"] = obj[8];


            var u = window.navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端

            try {
                if (isAndroid == true) {
                    Android.printTicketCar(JSON.stringify(array));
                    Android.speechOutputForAndroid("小票正在打印请稍后");
                } else {
                    console.log("请在安卓设备访问！");
                }

            }catch(error){

            }
            setInterval(function(){
                document.location.href = basePath+"ea/qrshare/ea_jumpManagement.jspa?posNum="+posNum;

            }, 3000);

        }, error: function (data) {

        }
    });
}


function feeset(){

    document.location.replace(basePath+"ea/qrshare/ea_getTimeHistory.jspa?carmID=${obj[0]}&posNum="+posNum);
}

    function re_load() {

        var iframeWindow = document.getElementById('myIframe').contentWindow;


        var financialbillID = iframeWindow.financialbillID;
        if(financialbillID=="login"){
              document.location.href = basePath+"page/WFJClient/pc/pc_login.jsp"
		}else {
            alert("操作成功");

            window.history.back();
            return false;
        }
    }



</script>
</html>
