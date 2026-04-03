$(function(){
    var u = navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    $("#registeredSubmit").click(function(e){
        var telReg = /^1(3|4|5|6|7|8|9)\d{9}$/;
        var isIDCardReg=/(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/;
        //购物卡检验
        if($("#shopping_card_number").val()==null || $("#shopping_card_number").val()==""){
            message("购物卡为空，请重新输入！");
            e.preventDefault();
            return;
        }
        //姓名校验
        if($("#name").val()==null || $("#name").val()==""){
            message("姓名为空，请重新输入！");
            e.preventDefault();
            return;
        }
        //手机号校验
        if($("#cell_phone_number").val()==null || $("#cell_phone_number").val()==""){
            message("手机号为空，请重新输入！");
            e.preventDefault();
            return;
        }else if(!$("#cell_phone_number").val().match(telReg)){
            message("手机号输入有误，请重新输入！");
            e.preventDefault();
            return;
        }
        //邀请人手机号校验
        if($("#invitation_code").val()==null || $("#invitation_code").val()==""){
            message("邀请码为空，请重新输入！");
            e.preventDefault();
            return;
        }else if(!$("#invitation_code").val().match(telReg)){
            message("邀请码输入有误，请重新输入！");
            e.preventDefault();
            return;
        }

        //密码校验
        if($("#password").val()==null || $("#password").val()==""){
            message("密码为空，请重新输入！");
            e.preventDefault();
            return;
        }else if($("#password").val()!=($("#confirm_password").val())){
            message("两次密码输入的不一致，请重新输入！");
            e.preventDefault();
            return;
        }
        //支付密码校验
        if($("#paymentCode").val()==null || $("#paymentCode").val()==""){
            message("支付密码为空，请重新输入！");
            e.preventDefault();
            return;
        }else if(!$("#paymentCode").val().match(/^\d{6}$/)){
            message("支付密码只能输入6位数字！");
            e.preventDefault();
            return;
        }
        //收货人校验
        if($("#consignee").val()==null || $("#consignee").val()==""){
            message("收货人为空，请重新输入！");
            e.preventDefault();
            return;
        }
        //地图地址校验
        if($("#map_address").val()==null || $("#map_address").val()==""){
            message("地图地址为空，请重新输入！");
            e.preventDefault();
            return;
        }
        //详细地址校验
        if($("#detailed_address").val()==null || $("#detailed_address").val()==""){
            message("详细地址为空，请重新输入！");
            e.preventDefault();
            return;
        }
        $("#detailed_address").val($("#map_address").val()+$("#detailed_address").val());
        var dataFrom = $("#regform").serialize();
        var url =basePath+"ea/giftcard/sajax_ea_registered.jspa";
        $.ajax({
            url:encodeURI(url),
            type:"post",
            data:dataFrom,
            async:false,
            dataType:"json",
            success:function (data) {
                var member = eval("("+data+")");
                var isRegister = member.isRegister;
                var mes = member.message;
                if(isRegister==false){
                    message(mes);
                }else if(isRegister==true){
                    //message(mes);
                    window.location.href=basePath+"page/conacts/restaurant/paySuc.jsp?flag=isCard";
                }
            }
        });
    });
    //点击屏幕关闭弹框
    $(".shelter_layer").click(function(){
        $('body').css("overflow","auto");
        $(this).hide();
    })

    $("#map_address").click(function(){
        if(isAndroid){
            Android.callgetRoundLocal();
        }else if(isiOS){
            var url= "func=" + 'iosMapaddress';
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    });
});
function message(text) {
    $(".shelter_layer .message").text(text);
    $(".shelter_layer").show();
    $('body').css("overflow","hidden");
}


function ios_address(param){
    var p=param.substring(0,param.indexOf(">"));
    var address=p.substring(0,p.indexOf("<"));
    var jv=p.substring(p.indexOf("<")+1);
    var j=jv.substring(1,jv.indexOf(","));
    var v=jv.substring(jv.indexOf(",")+2);
    var area  = param.substring(param.indexOf("[")+1,param.indexOf("]"));
    $("#area").val(area);
    $("#map_address").val(address);
}

function a_address(param){
    var address=param.substring(0,param.indexOf(","));
    var coordinate=param.substring(param.indexOf(",")+1);
    var area  = param.substring(param.indexOf("[")+1,param.indexOf("]"));
    $("#area").val(area);
    $("#map_address").val(address);
}