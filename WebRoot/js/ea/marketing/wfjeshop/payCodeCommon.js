$(document).ready(function(){
    //弹出层
    $("#prompt").css("position","absolute").css("top",$(window).height()*0.50+"px").css("z-index","999999");
    $("#prompt").find("div").css("height",$(window).height()*0.1+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
    $("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");

    //点击密码框触发输入框
    $(".pay_label").click(function() {
        $(".pay_inp").focus();
    })
    //输入密码弹窗关闭
    $(".close_btn").click(function() {
        $(".overlay").removeClass("active");
        $(".popup_pay").hide();
        initPay();
    })


});


//初始化密码输入弹窗
function initPay() {
    $(".overlay").removeClass("active");
    $(".popup_pay").hide();
    $(".pay_inp").val('');
    $(".pay_label li").each(function() {
        $(this).removeClass();
    })
}
function prompt(obj){
    if($("#prompt").css("display")!="none")
        return;
    $("#prompt").find("span").text(obj);
    $("#prompt").fadeIn(500);
    setTimeout(function(){
        $("#prompt").fadeOut(500);
        $("#prompt").find("span").text("");
    }, 4000);
}

