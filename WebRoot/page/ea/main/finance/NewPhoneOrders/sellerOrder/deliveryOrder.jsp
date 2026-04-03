<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>发货订单详情</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/sellerOrder/style.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/sellerOrder/selle_style.css"/>
    <script type="text/javascript" src="<%=basePath %>js/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/common.js"></script>
    

</head>
<script>
var basePath = "<%=basePath%>";
var companyid = "${param.companyid}";
var journalNum ="${param.oaBillId}";
var casid ="${param.cashierBillsID}";
var staffid = "${param.staffid}";
</script>
<body>
<div class="header">
    <ul>
        <li style="width: 10%;"><a href="javascript:history.go(-1)"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/left.png"></a></li>
        <li style="width: 80%;text-align: center;">我的订单</li>
        <li style="width: 10%"></li>
    </ul>
</div>
<div class="content_hidden">
    <div class="content sel_con">
        <div class="rec_con sel_con2">
            <div class="rec_eva">
                <div class="grd Returns_number">
                    <ul class="sel_name">
                        <li><p> <span> </span> </p></li>
                        <li style="width: 85%;text-align: left"><span>${pb.companyName }</span></li>
                        <li style="width: 15%;text-align: right"><span>待发货</span></li>
                    </ul>
                   
                   <!-- 引入外部jsp -->
       <jsp:include page="/page/ea/main/finance/NewPhoneOrders/sellerOrder/Share/titleaddress.jsp"/>
       <!-- 引入外部jsp -->
       <jsp:include page="/page/ea/main/finance/NewPhoneOrders/sellerOrder/Share/titleCentral.jsp"/>
       <!-- 引入外部jsp -->
       <jsp:include page="/page/ea/main/finance/NewPhoneOrders/sellerOrder/Share/titleOrderDetails.jsp"/>
    	
    	<jsp:include page="/page/ea/main/finance/NewPhoneOrders/sellerOrder/Share/titlebottom.jsp"/>
        
                    
                    <div class="mil_r">
                        <ul>
                            <li><h4>发货方式</h4></li>
                            <li><p id="delivery"><span>选择物流</span></p></li>
                            <li><a><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/right.png"></a></li>
                        </ul>
                        <ul id="number" class="display">
                            <li><h4>运单号</h4></li>
                            <li><input type="text" placeholder="手输或扫描" id="yd" value="${param.pl}"></li>
                        </ul>
                    </div>
                    <input type="hidden" id="biaoshi" value="">
                    <input type="button" value="扫描运单条码" id="scan">
                   <!-- <div id="set"><span>设置默认物流...</span></div>-->
                </div>
            </div>
        </div>
        <a href="javascript:history.go(-1)">
            <div class="btn_thd">
                <p><a ><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/ico-ret.png" alt="">
                
                </a>发货</p>
            </div>
        </a>
    </div>
</div>
<div class="alert_tk">
    <ul class="delivery">
        <li id="no_display" class="active">选择物流<div><h5></h5></div></li>
        <li id="display">无需物流<div><h5></h5></div></li>
    </ul>
</div>
<script>
var basePath = "<%=basePath%>";

function yanzhen(){
	var number = $("#yd").val();
	if(number!=null&&number!=""){
		var url= basePath+"ea/seller/sajax_ea_numberRecognition.jspa?number="+number;
		$.ajax({
			url : encodeURI(url),
			type : "get",
			async : false,
			dataType : "json",
			success : function(data) {
				var member = eval("(" + data + ")");
				var shippers = member.Shippers;
				var success = member.Success;
				var code = member.Code;
				 if(success==true){
					var Identification = shippers[0];
					if(Identification!=null&&Identification!=""&&Identification!=undefined){
						var biaoshi = Identification.ShipperCode;
						var name = Identification.ShipperName;
						$("#biaoshi").val(biaoshi);
						if($("#express").text()==name){
							console.log("成功");
						}
					}
				}else{
					return
					console.log(code);
				} 
			},
				error : function(data) {
			}
		});
	}
}


    $(document).ready(function(){
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".header").css("height",$(window).height()*0.08+"px");
        $(".content_hidden").css("height",$(window).height()*0.92+"px");
        $(".content").css("height",$(window).height()*0.92+"px");
        $(".sel_con2").css("height",$(window).height()*0.88-$(".btn_thd").height()+"px");
        $("#beizhu2").text($("#beizhu").val());
        $("#money2").text($("#money").val());
        
        
      //2017.1.24 手机端输入法遮盖输入框
        $(".Returns_number .mil_r #number li:nth-child(2) input").focus(function(){
            $(".rec_con").css("height",$(window).height()*1.5+"px");
        });
        $(".Returns_number .mil_r #number li:nth-child(2) input").blur(function(){
            $(".rec_con").css("height",$(window).height()*0.92-$(".btn_thd").height()+"px");
        });
        
        
        
        var u = navigator.userAgent;
    	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    	
    	
    	if(isAndroid==true){		
    		var obj = document.getElementById("scan");
    		
    		obj.setAttribute("onclick", "retAndroid()");    		   		
    	}else if(isiOS==true){ 
    		var obj_sao=document.getElementById("scan");
    		obj_sao.setAttribute("onclick", "callIOSyundan()");
    	}
    	
    	
        $(".btn_thd").click(function(){
        	var url;
        	var Waybillno = $("#yd").val();//运单hao 
        	var name= $("#express").text();
        	var ExCode = $("#biaoshi").val();
        	var aa = $("#delivery").text();
        	if(aa=="选择物流"){
        		if(Waybillno==null||Waybillno==""){
            		alert("运单号不能为空");
            		return;
            	}else{
            		yanzhen();
            	}
        	}
        	
        	
        	url = basePath+"ea/seller/ea_deliverGoods.jspa?staffId="+staffid+"&companyid="+companyid+
	      		"&cashierBillsID="+casid+"&Waybillno="+Waybillno+"&ExCode="+ExCode+"&name="+name;	
        	
		  			document.location.href=url;
        });
        
        $("#delivery").click(function(){
            $(".alert_tk").show();
            $(".delivery").show();
            $(".express").hide();
        });
        $(".alert_tk .delivery #no_display").click(function(){
            $(".mil_r .display").show();
            $("#scan").show();
        });
        $(".alert_tk .delivery #display").click(function(){
            $(".mil_r .display").hide();
            $("#scan").hide();

        });
        $(".alert_tk .delivery li").click(function(){
            var kd_txt=$(this).text();
           
            $(this).addClass("active").siblings().removeClass("active");
            $("#delivery").text(kd_txt);
           
            $(".alert_tk").hide();
            $(".delivery").hide();
        });
        $("#express").click(function(){
            $(".alert_tk").show();
            $(".express").show();
            $(".delivery").hide();
        });
        $(".alert_tk .express li").click(function(){
            var kd_txt=$(this).text();
            var aak = $(this).find("input").val();
            console.log(aak);
            $(this).addClass("active").siblings().removeClass("active");
            $("#express").text(kd_txt);
            $("#bs").val(aak);
            $(".alert_tk").hide();
            $(".express").hide();
        });
        $(".alert_tk").click(function(){
            $(".alert_tk").hide();
        });

        $(".up_btn #up").click(function(){
            $(this).hide().siblings().show();
            $(".mon .txt h5").hide();
            $(".code h4").hide();
            $(".code .code_").hide();
        });
        $(".up_btn #down").click(function(){
            $(this).hide().siblings().show();
            $(".mon .txt h5").show();
            $(".code h4").show();
            $(".code .code_").show();
        });

    })
    function callIOSyundanhao(danhao){	
		var member=eval("("+danhao+")");
	 window.location.href=basePath+"ea/seller/ea_getCashBill.jspa?parameter=BILL&partem=Myorder&cashierBillsID="+casid+"&oaBillId="+journalNum+"&staffid="+staffid+"&companyid="+companyid+"&pl="+member;				
	 }
    function yundan(tiaoma){
		var member=eval("("+tiaoma+")");
		 window.location.href=basePath+"ea/seller/ea_getCashBill.jspa?parameter=BILL&partem=Myorder&cashierBillsID="+casid+"&oaBillId="+journalNum+"&staffid="+staffid+"&companyid="+companyid+"&pl="+member;				
	}
    	
    function retAndroid(){
			Android.callYunDan();
	}
    function callIOSyundan(){
	var url= "func=" + 'calliosyundan';                
    window.webkit.messageHandlers.Native.postMessage(url);
	//callioscamera('');
	}
   
</script>

<script>
    
    	window.onload = window.onresize = function(){
          var clientWidth = document.documentElement.clientWidth;
        
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
</script>
</body>
</html>