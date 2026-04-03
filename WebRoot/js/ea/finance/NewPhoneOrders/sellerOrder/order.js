
var url;
var isAndroid;
var isiOS;

$(document).ready(function(){
    var u = navigator.userAgent;
    isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    if(isAndroid==true){
        var obj = document.getElementById("fuzhi");

        if(obj!=undefined&&obj!=null){
            obj.setAttribute("onclick", "Androidcopy(this)");
        }
        // var oop = document.getElementById("types");
        // if(oop!=undefined&&oop!=null){
        //     oop.setAttribute("onclick", "AndroidtoChat()");
        // }

    }else if(isiOS==true){
        var obj = document.getElementById("fuzhi");
        if(obj!=undefined&&obj!=null){
            obj.setAttribute("onclick", "IOSCopy(this)");
        }

        // var obj2 = document.getElementById("types");
        // if(obj!=undefined&&obj2!=null){
        //     oop.setAttribute("onclick", "AndroidtoChat()");
        // }

    }

});

(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURI(r[2]);
        return null;
    };
})(jQuery);

//更改地址 
function share(obj) {

	var journalNum = $(obj).parents(".grd").find(".sel_nub #journalNum").val();
	var casid = $(obj).parents(".grd").find(".sel_nub #casid").val();
	url = basePath+ "ea/seller/ea_getCashBill.jspa?parameter=ADDRESS&oaBillId="
			+ journalNum + "&casid=" + casid + "&companyid=" + companyid+"&staffid="+staffid;
	document.location.href = url;
}

//更改地址 详情跟
function shareAddress(obj){
	url = basePath+"ea/seller/ea_getCashBill.jspa?parameter=ADDRESS&oaBillId="+journalNum+"&staffid="+staffid;
	document.location.href=url;
}
//返回点击判断
function dianji(){
	 
	if(type=="05"){
		url = basePath+"ea/seller/ea_getcomporder.jspa?companyid="+companyid+"&staffid="+staffid;
		document.location.href=url;
	}else{
		history.go(-1);
	}
}
// 发货
function cheg(obj) {
	var journalNum = $(obj).parents(".grd").find(".sel_nub #journalNum").val();
	var casid = $(obj).parents(".grd").find(".sel_nub #casid").val();
	url = basePath
			+ "ea/seller/ea_getCashBill.jspa?parameter=BILL&partem=Myorder&cashierBillsID="
			+ casid + "&oaBillId=" + journalNum + "&staffid=" + staffid
			+ "&companyid=" + companyid;
	document.location.href = url;
}
// 物流
function wuliu(obj) {

	if(cashierBillsID  == ""){//从列表页进入
        cashierBillsID = $(obj).parents(".grd").find(".sel_nub #casid").val();
	}
    document.location.href=basePath+"ea/pobuy/ea_toGetWuliu.jspa?cbid="+cashierBillsID;
	//原来方法
    // var waybillNumber = $(obj).parents(".grd").find(" #bianh").val();
	// var exCode = $(obj).parents(".grd").find(" #bz").val();
	// url = basePath + "ea/seller/ea_logisticsQuery.jspa?waybillNumber="
	// 		+ waybillNumber + "&exCode=" + exCode + "&type=02";
	// document.location.href = url;

}
// 订单详情
function CashBill(onb) {
	var casid = $(onb).parents(".grd").find(".sel_nub #casid").val();
	var journalNum = $(onb).parents(".grd").find("#journalNum").val();
	
	var fkStatus = $(onb).parents(".grd").find(".sel_nub #fkStatus").val();
	var companyId = $(onb).parents(".grd").find(".sel_nub #companyId").val();
	var plt = $(onb).parent(".grd").find("#pl").val();
	var ppid = $(onb).parents(".grd").find(" #ppid").val();
	var goodid = $(onb).parents(".grd").find(" #goodid").val();

	console.log(ppid+""+goodid)
	url = basePath + "ea/seller/ea_getCashBill.jspa?cashierBillsID="
			+ casid + "&oaBillId=" + journalNum + "&parameter=BILL&plt=" + plt
			+ "&fkStatus=" + fkStatus + "&companyId=" + companyId + "&ppid="
			+ ppid+"&staffid="+staffid;
	document.location.href = url;
	
}
// 同意退货/拒绝退货

// 同意退款/拒绝退款

// 订单详情《退货单》
function OrderDetails(onb) {
	var cashierBillsID = $(onb).parent(".grd").find("#casid").val();
	var orderBill = $(onb).parent(".grd").find("#orderBill").val();
	
	url = basePath+ "ea/seller/ea_getCashBill.jspa?parameter=BILL&partem=OrderDetails&cashierBillsID="
			+ cashierBillsID + "&oaBillId=" + orderBill;
	document.location.href = url;

}
//同意退货《我的订单进》<进退货详情页面>
function orderReturns(obj) {
	var fkStatus = $(obj).parents(".grd").find(".sel_nub #fkStatus").val();
	var orderBill = $(obj).parents(".grd").find(".sel_nub #journalNum").val();
	var cashierBillsID = $(obj).parents(".grd").find(".sel_nub #casid").val();
	url = basePath+ "ea/seller/ea_getCashBill.jspa?parameter=BILL&partem=OrderDetails&cashierBillsID="
	+ cashierBillsID + "&partem=OrderDetails&type=01&oaBillId="
	+ orderBill+"&fkStatus="+fkStatus;

	document.location.href = url;

}
//同意退款《我的订单进》<进退款详情页面>
function moneyReturns(obj) {
	var fkStatus = $(obj).parents(".grd").find(".sel_nub #fkStatus").val();
	var orderBill = $(obj).parents(".grd").find(".sel_nub #journalNum").val();
	var cashierBillsID = $(obj).parents(".grd").find(".sel_nub #casid").val();
    var wfStatus4 = $(obj).parents(".grd").find(".sel_nub #wfStatus4").val();
    var trade_no = $(obj).parents(".grd").find("#trade_no").val();
	var wfStatus1= $(obj).parents(".grd").find("#wfStatus1").val();

    var param = new Array();
    param.push("parameter=BILL");
    param.push("&partem=OrderDetails");
    param.push("&cashierBillsID="+cashierBillsID);
    param.push("&type=01&oaBillId="+orderBill);
    param.push("&typel="+wfStatus4);
    param.push("&out_trade_no="+orderBill);
    param.push("&trade_no="+trade_no);
    param.push("&wfStatus1="+wfStatus1);

	document.location.href = basePath+ "ea/seller/ea_getCashBill.jspa?"+param.join("");

}
// 同意退货《退货单》
function agreetoReturns(onb) {
	var orderBill = $(onb).parents(".grd").find("#orderBill").val();
	var cashierBillsID = $(onb).parents(".grd").find("#casid").val();
	var wfStatus4 = $(onb).parents(".grd").find("#wfStatus4").val();
	var trade_no = $(onb).parents(".grd").find("#trade_no").val();
	var wfStatus1= $(onb).parents(".grd").find("#wfStatus1").val();

	var param = new Array();
	param.push("parameter=BILL");
	param.push("&partem=OrderDetails");
	param.push("&cashierBillsID="+cashierBillsID);
	param.push("&type=01&oaBillId="+orderBill);
	param.push("&typel="+wfStatus4);
	param.push("&out_trade_no="+orderBill);
	param.push("&trade_no="+trade_no);
	param.push("&wfStatus1="+wfStatus1);

	document.location.href = basePath+ "ea/seller/ea_getCashBill.jspa?"+param.join("");

}
// 拒绝退货《退货单》
function refusetoReturns(onb) {

	var orderBill = $(onb).parents(".grd").find("#orderBill").val();
	var cashierBillsID = $(onb).parents(".grd").find("#casid").val();

	url = basePath
			+ "ea/seller/ea_getCashBill.jspa?parameter=BILL&partem=OrderDetails&cashierBillsID="
			+ cashierBillsID + "&partem=OrderDetails&type=02&oaBillId="
			+ orderBill;
	document.location.href = url;
}

// 同意退货《退货单的》
function agreetoReturn(obj) {
	var text = $(obj).find("#tongyi02").val();// 同意退款
	var text2 = $(obj).find("#jujue02").val();// 拒绝退款
	var wfStatus4 = $(obj).parents(".grd").find("#wfStatus4").val();
	var trade_no = $(obj).parents(".grd").find("#trade_no").val();
	var orderBill = $(obj).parents(".grd").find("#orderBill").val();
	var money = $(obj).parents(".grd").find("#money").val();
	console.log(trade_no);
	var cashierBillsID;
	cashierBillsID= $(obj).parents(".grd").find("#casid").val();
	if(cashierBillsID==null||cashierBillsID==undefined){
		cashierBillsID=cashierBillsIDs;
	}
	
	if(wfStatus4==null||wfStatus4==undefined){
		wfStatus4=s1;
	}
	if(trade_no==null||trade_no==undefined){
		trade_no=s2;
	}
	if(orderBill==null||orderBill==undefined){
		orderBill=s3;
	}
	if(money==null||money==undefined){
		money=s4;
	}
	var textto;
	var url;
	var types;
	if (text == "同意退货") {
		textto = "你确定要同意退货吗？";
		types = "01";
	} else if (text2 == "拒绝退货") {
		textto = "你确定要拒绝退货吗？";
		types = "02";
	}
	//00 01 02 03 04
	if (window.confirm(textto)) {
		if(wfStatus4=="01"){
			url = basePath + "ea/seller/sajax_ea_agreetoReturn.jspa?oaBillId="+cashierBillsID+
			"&types="+types+"&types=" + types+"&typel="+wfStatus4+
			"&out_trade_no="+orderBill+"&trade_no="+trade_no+"&refund_amount="+money+"&fkStatus="+wfStatus4;
		}else if(wfStatus4=="00"){
			url = basePath + "ea/seller/sajax_ea_agreetoReturn.jspa?oaBillId="+cashierBillsID+
			"&types="+types+"&types=" + types+"&typel="+wfStatus4+
			"&orderId="+orderBill+"&totalFee="+money+"&totalFee="+money+"&fkStatus="+wfStatus4;
		}else if(wfStatus4=="02"){
			url = basePath + "ea/seller/sajax_ea_agreetoReturn.jspa?oaBillId="+cashierBillsID+
			"&types="+types+"&types=" + types+"&typel="+wfStatus4+
			"&out_trade_no="+orderBill+"&trade_no="+trade_no+"&refund_amount="+money+"&fkStatus="+wfStatus4;
		}else{
			url = basePath + "ea/seller/sajax_ea_agreetoReturn.jspa?oaBillId="+cashierBillsID+
			"&types="+types+"&types=" + types+"&typel="+wfStatus4+"&fkStatus="+wfStatus4;
		}
		
		
		$.ajax({
			url : encodeURI(url),
			type : "get",
			dataType : "json",
			
			success : function(data) {
				var member = eval("(" + data + ")");
				if (member.map == "01") {// 成功
					$("#ty02").css({
						display : "block"
					});
				} else if (member.map == "02") {// 失败
					$("#ju01").css({
						display : "block"
					});

				}
			},
			error : function(data) {

			}
		});
	}

}
// 拒绝\同意\退款《退货单的》
function refusetoReturn(obj) {
	var text = $(obj).find("#tongyi").val();// 同意退款
	var text2 = $(obj).find("#jujue").val();// 拒绝退款
	/*var cashierBillsID = $(obj).parents(".grd").find("#casid").val();
	var wfStatus4 = $(obj).parents(".grd").find("#wfStatus4").val();
	var trade_no = $(obj).parents(".grd").find("#trade_no").val();
	var orderBill = $(obj).parents(".grd").find("#orderBill").val();
	var money = $(obj).parents(".grd").find("#money").val();
	*/
	var textto;
	var url;
	var types;
	
	if (text == "同意退款") {
		textto = "你确定要同意退款吗？";
		types = "01";
	} else if (text2 == "拒绝退款") {
		textto = "你确定要拒绝退款吗？";
		types = "02";
	}

	if (window.confirm(textto)) {

	    var param = new Array();
	    param.push("oaBillId=" + cashierBillsIDs);
	    param.push("&types=" + types);
	    param.push("&typel=" + wfStatus4);
	    param.push("&fkStatus=" + fkStatus);
	    param.push("&out_trade_no=" + orderBill);
	    param.push("&trade_no=" + trade_no);
	    param.push("&refundCode=" + refundCode);
	    param.push("&wfStatus1=" + wfStatus1);
	    param.push("&refund_amount=" + money);

		url = basePath + "ea/seller/sajax_ea_refusetoReturn.jspa?"+ param.join("");

		$.ajax({
			url : encodeURI(url),
			type : "get",
			dataType : "json",
			success : function(data)
			{
				var member = eval("(" + data + ")");

                // 成功
				if (member.map == "01")
				{
					$("#ty02").css({
						display : "block"
					});
					$("#ty021").css({
						display : "block"
					});
					$("#ty022").css({
						display : "block"
					});
					alert("退款成功！");
					$("#jujue").hide();
					$("#tongyi").hide();
				}

                // 失败
				else if (member.map == "02")
				{
                    alert("退款失败！");
					$("#ju01").css({
						display : "block"
					});
					$("#ju011").css({
						display : "block"
					});
                    $("#jujue02").hide();
                    $("#tongyi02").hide();
				}
			},
			error : function(data) {
                    alert("退款请求失败！");
			}
		});
	}

}

// 商城点击
function mallClick(obj) {
	var ppid = $(obj).find("#ppid").val();
	var goodsid = $(obj).find("#goodsid").val();
	var companyId = $(obj).find("#companyId").val();
	window.location.href = basePath + "/ea/wfjshop/ea_doodsDetail.jspa?ppid="
			+ ppid + "&goodsid=" + goodsid + "&companyId=" + companyId;

}

//复制
function IOSCopy(obj){
	var content=$(obj).prev('h4').find("#bianhao").val();
	 var url= "func=" + 'iosstick';                
    params={'content':content};
    for(var i in params){
     url = url + "&" + i + "=" + params[i];
    }   
    alert("复制成功");
    window.webkit.messageHandlers.Native.postMessage(url); 
}
function Androidcopy(obj){
	var content=$(obj).prev('h4').find("#bianhao").val();
	Android.callcopy(content);
}



//联系商家
function ioschat(obj){
    var url= "func=" + 'ioschat';
    params={'sccid':$(obj).siblings().eq(0).val(),
        'account' :$(obj).siblings().eq(1).val(),
        'username' :$(obj).siblings().eq(2).val()};
    for(var i in params){
        url = url + "&" + i + "=" + params[i];
    }
    window.webkit.messageHandlers.Native.postMessage(url);

}
function AndroidtoChat(obj){
    var userId= $(obj).siblings().eq(1).val();
    var sccId= $(obj).siblings().eq(0).val();
    var userPickeName= $(obj).siblings().eq(2).val();

    Android.toChat(userId,sccId,userPickeName);
}

//提交拣货
function delivery(obj){
	var cashid=$.getUrlParam("cashierBillsID");
	var url=basePath + "ea/seller/sajax_ea_saveSorting.jspa?";
	$.ajax({
		url : encodeURI(url),
		type : "get",
		dataType : "json",
		data:{
			'cashid':cashid
		},
		success : function(data){
			var member = eval("("+data+")");
			var flag=member.flag;
			alert(flag);
            // 成功
			if (flag=="操作成功"){
				url = basePath + "ea/seller/ea_getCashBill.jspa?cashierBillsID="
					+ cashid + "&oaBillId=" + $.getUrlParam("oaBillId")+ "&parameter="+$.getUrlParam("parameter")+"&plt=" + $.getUrlParam("plt")
					+ "&fkStatus=" + $.getUrlParam("fkStatus") + "&companyId=" + $.getUrlParam("companyId") + "&ppid="
					+ $.getUrlParam("ppid")+"&staffid="+$.getUrlParam("staffid");
				document.location.href = url;
			}
		},
		error : function(data) {
                alert("操作失败！");
		}
	});
}