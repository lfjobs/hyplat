$(function(){
    var ua = navigator.userAgent.toLowerCase();
    var isWeixin = ua.indexOf('micromessenger') != -1;
	object.push("khd=",khd);
	object.push("&flag=",flag);		
	object.push("&identifying=",identifying);
	object.push("&mark=",mark);


    var cus = userInfo();
    var btnType;//解绑方式
    var bindWay;
    var isBind = false;//默认解绑 ,true绑定，false解绑
    var bankID;
	//删除银行卡
	$(".relieve_btn").click(function(){
        btnType = $(this).find("input").attr("id");
        if(btnType =="bankId"){
            bankID = $(this).find("input").attr("value")
        }
	    codeShow(cus.account);
        // var btnType = $(this).find("input").attr("id");
        // if(btnType == "reWeChat"){
        //
		 //    //解除微信
        //     if(isWeixin){
        //         alert("公众账号微信不能解除！")
        //     }else {
        //         if (confirm("你确定解除绑定该微信吗？")) {
        //             removeBind("02");
        //         }
        //     }
        // }else if(btnType == "reAli"){
		 //    //解除支付宝
        //     if (confirm("你确定解除绑定该支付宝吗？")) {
        //         removeBind("01");
        //     }
        // }else {
        //     var bankAccountID = $(this).find("#bankId").val();
        //     if (confirm("你确定解除绑定该银行卡吗？")) {
        //         var url = basePath + "ea/perinfor/sajax_ea_deleteBankCard.jspa?bankAccountID=" + bankAccountID;
        //         $.ajax({
        //             url: url,
        //             type: "get",
        //             async: false,
        //             dataType: "json",
        //             success: function (data) {
        //                 var member = eval("(" + data + ")");
        //                 var flag = member.flag;
        //                 if (flag == 'ok') {
        //                     window.location.href = basePath + "/ea/perinfor/ea_getBankCardInformation.jspa?" + object.join("") + "&staffId=" + staffid + "&sccid=" + sccid + "&bankId=" + bankId + "&user=" + user + "&editType=00";
        //
        //                 } else {
        //                     alert("银行卡未解除绑定！");
        //                 }
        //             }
        //         });
        //     }
        // }
	});
	if(mark == '00'){
		$(".bank_box").click(function(){
			var bankAccountID = $(this).parent().attr("id");
            $.ajax({
                url:basePath+"/ea/jinbi/sajax_chooseWay.jspa?withDraway=03&sccid="+sccid,
                type:"post",
                dataType:"json",
                asnyc:false,
                success:function (data) {
                    var  member = eval("("+data+")");
                    if(member.isOK==true){
                        window.location.href = basePath + "/ea/jinbi/ea_getwfjtixian.jspa?"+object.join("")+"&user="+user+"&sccid="+sccid +"&staffid="+staffid+"&bankId="+bankAccountID;
                    }else {
                        alert("选择失败");
                    }
                }
            });
		});   			
	}

    $("#getCode").click(function () {
        var account = cus.account;

        $.ajax({
            cache : true,
            type :"POST",
            url : basePath+"/ea/android/sajax_ea_getduanxin.jspa?pahe="+account,
            async :false,
            dataType : "json",
            success :function(data){
                //  var member = eval("(" + data + ")");
                code = data.returna;
                console.info(code);
            }
        });
    });

    $("#confirm").click(function () {
        var inputCode = $(".val-item").text();
        if(inputCode == code){
            $("#Bbox_close").click();
            $("#val-code-input").val("");
            if(isBind){
                if(bindWay == "ali"){
                    if (confirm("确定绑定支付宝账号吗？")) {
                        authorByAli()
                    }
                }else if(bindWay == "weChat"){
                    if (confirm("确定绑定微信账号吗？")) {
                        authorByWeChat();
                    }
                }
            }else {
                if(btnType == "reWeChat"){

                    //解除微信
                    if(isWeixin){
                        alert("公众账号微信不能解除！")
                    }else {
                        if (confirm("你确定解除绑定该微信吗？")) {
                            removeBind("02");
                        }
                    }
                }else if(btnType == "reAli"){
                    //解除支付宝
                    if (confirm("你确定解除绑定该支付宝吗？")) {
                        removeBind("01");
                    }
                }else {
                    if (confirm("你确定解除绑定该银行卡吗？")) {
                        var url = basePath + "ea/perinfor/sajax_ea_deleteBankCard.jspa?bankAccountID=" + bankID;
                        $.ajax({
                            url: url,
                            type: "get",
                            async: false,
                            dataType: "json",
                            success: function (data) {
                                var member = eval("(" + data + ")");
                                var flag = member.flag;
                                if (flag == 'ok') {
                                    window.location.href = basePath + "/ea/perinfor/ea_getBankCardInformation.jspa?" + object.join("") + "&staffId=" + staffid + "&sccid=" + sccid + "&bankId=" + bankId + "&user=" + user + "&editType=00";

                                } else {
                                    alert("银行卡未解除绑定！");
                                }
                            }
                        });
                    }
                }
            }

        }else {
            alert("验证码输入错误")
        }

    });

	if(cus.userId==null || cus.userId==""){
        $(".Alipay .state").text("(尚未绑定)")
    }else {
        $(".Alipay .state").text("(已绑定)")
    }

    if(isWeixin){
        if(cus.openId==null || cus.openId==""){
            $(".weChat .state").text("(尚未绑定)")
        }else {
            $(".weChat .state").text("(已绑定)")
        }
    }else {
        if(cus.appOpenId==null || cus.appOpenId==""){
            $(".weChat .state").text("(尚未绑定)")
        }else {
            $(".weChat .state").text("(已绑定)")
        }
    }

    $(".btn_jc .state").each(function () {
        if($(this).text()=="(已绑定)"){
            var $btn = $(this).parents(".btn_jc");
            // Zepto(this.parentElement).swipeLeft(function(){
            //     $btn_jc.parent().addClass("bank_box_L");
            // });
            // Zepto(this.parentElement).swipeRight(function(){
            //     $btn_jc.parent().removeClass("bank_box_L")
            // })
            $btn.touchwipe({
                min_move_x : 30, // 灵敏度

                wipeLeft : function() {
                    $btn.addClass("bank_box_L");

                },
                wipeRight : function() {
                    $btn.removeClass("bank_box_L")

                },
            });
        }
    })


    $(".Alipay").click(function () {
        if($(".Alipay .state").text() =="(已绑定)"){
            if(mark == '00'){
                $.ajax({
                    url:basePath+"/ea/jinbi/sajax_chooseWay.jspa?withDraway=01&sccid="+sccid,
                    type:"post",
                    dataType:"json",
                    asnyc:false,
                    success:function (data) {
                        var  member = eval("("+data+")");
                        if(member.isOK==true){
                            window.location.href = basePath + "/ea/jinbi/ea_getwfjtixian.jspa?"+object.join("")+"&user="+user+"&sccid="+sccid +"&staffid="+staffid;
                        }else {
                            alert("授权失效请解除绑定再重新绑定");
                        }
                    }
                })
            }

        }else {
            bindWay = "ali";
            codeShow(cus.account);
            isBind = true;
        }
    })

    $(".weChat").click(function () {
        if($(".weChat .state").text() =="(已绑定)"){
            if(mark == '00'){
                $.ajax({
                    url:basePath+"/ea/jinbi/sajax_chooseWay.jspa?withDraway=02&sccid="+sccid,
                    type:"post",
                    dataType:"json",
                    asnyc:false,
                    success:function (data) {
                        var  member = eval("("+data+")");
                        if(member.isOK==true){
                            window.location.href = basePath + "/ea/jinbi/ea_getwfjtixian.jspa?"+object.join("")+"&user="+user+"&sccid="+sccid +"&staffid="+staffid;
                        }else {
                            alert("授权失效请解除绑定再重新绑定");
                        }
                    }
                })
            }
        }else {
            if(!isWeixin){
                bindWay = "weChat";
                codeShow(cus.account);
                isBind = true;
            }
        }
    })

});


//支付宝获取授权信息
function authorByAli() {
    var anthInfo = aliUrl();
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    try {
        if (isAndroid == true) {
            Android.getUserInfoFromAuthV2(anthInfo+"",mark+"");//调用安卓接口
        }else if (isiOS == true) {
            var url= "func=" + 'iosSetAPAuth';
            params={'anthInfo':anthInfo,'mark':mark};
            for(var i in params){
                url = url + "&" + i + "=" + params[i];
                console.log(url);
            }
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    }catch(e){
       // window.history.go(-1);return false;
    }
}


//微信获取授权信息
function authorByWeChat() {
    var anthInfo = weChatUrl();
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    try {
        if (isAndroid == true) {
            Android.getUserInfoFromWeiXin(mark);//调用安卓接口
        }else if (isiOS == true) {
            var url= "func=" + 'iosSetWXAuth';
            params={'mark':mark};
            for(var i in params){
                url = url + "&" + i + "=" + params[i];
                console.log(url);
            }
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    }catch(e){
        // window.history.go(-1);return false;
    }
}


//支付宝授权参数拼接
function aliUrl() {
    var anth = "";
    $.ajax({
        url: basePath + "/ea/jinbi/sajax_getAuthInfo.jspa",
        type:"get",
        async : false,
        dataType:"json",
        success:function (data) {
        	console.info(data)
            anth = data
        }
    })
    return anth;
}


//微信授权参数拼接
function weChatUrl() {
    var anth = "";
    $.ajax({
        url: basePath + "/ea/jinbi/sajax_getWeAuthInfo.jspa",
        type:"get",
        async : false,
        dataType:"json",
        success:function (data) {
            console.info(data)
            anth = data
        }
    })
    return anth;
}
//用户信息
function userInfo() {
	var customer;
    $.ajax({
        url:basePath + "/ea/jinbi/sajax_getUserInfo.jspa?sccid="+sccid,
        type:"get",
        dataType:"json",
        async:false,
        success:function (data) {
            var member = eval("(" + data + ")");
			customer = member.customer;
        }
    })
	return customer;
}

function removeBind(type) {
    $.ajax({
        url:basePath + "/ea/jinbi/sajax_removeBind.jspa?withDraway="+type+"&sccid="+sccid,
        type:"post",
        dataType:"json",
        async:false,
        success:function (data) {
            var member = eval("(" + data + ")");
            if(member.isOK==true){
                alert("解除成功");
                if(type=="01"){
                    $(".Alipay .state").text("(尚未绑定)")
                }else if(type=="02"){
                    $(".weChat .state").text("(尚未绑定)")
                }
                $(".btn_jc").removeClass("bank_box_L");
            }else {
                alert("解除失败")
            }
        }
    })
}

function codeShow(account) {
    $("#phone").text(account);
    $(".val-item").text("").removeClass('available');
    $("#Bbox").show();
}

window.onload = function() {
    $(".bank_btn_wrap").each(function () {
        var $bankbox=$(this).find(".bank_box");
        $bankbox.touchwipe({
            min_move_x : 30, // 灵敏度
            wipeLeft : function() {
                $bankbox.addClass("bank_box_L").parent().siblings().find(".bank_box").removeClass("bank_box_L");
            },
            wipeRight : function() {
                $(".bank_box").removeClass("bank_box_L")
            },
        });
    })
}