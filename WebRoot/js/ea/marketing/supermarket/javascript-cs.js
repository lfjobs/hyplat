$(function () {
    /*登录验证回车按键*/
    $(document).keyup(function(event){
        if(event.keyCode ==13){
            $("#login").trigger("click");

        }
    });

    /*取消订单弹框*/
    $("#cancel").click(function () {
        $(".alert_cancel_").show();
        $(".alert_cancel").show();
    });
    /*关闭取消订单弹框*/
    $(".alert_cancel_").click(function () {
        $(this).hide();
        $(".alert_cancel").hide();
        $(".alert_download").hide();
    });
    $("#xx").click(function () {
        $(".alert_cancel_").hide();
        $(".alert_cancel").hide();
    });
    /*点击下载*/
    $(".code-pay figure .p1").click(function () {
        $(".alert_cancel_").show();
        $(".alert_download").show();
    });

    /*打印小票弹框*/
    $("#print").click(function () {
        $(".alert_suc_").show().delay(3000).fadeOut(1000);

    });

    /*商品数量加减*/
    $(".number .add").click(function () {
        var nub = $(this).parents(".number").find(".nub");  //当前数量
        var mon = $(this).parents("li").find(".mon");       //当前单价
        var mony = $(this).parents("li").find(".money span");//当前显示价格
        var tot = $(".total .piece span");
        nub.val(parseFloat(nub.val())+1);
        var dj = parseFloat(mon.text())*100*nub.val()/100;
        mony.text(dj.toFixed(2));
        tot.text(parseFloat(tot.text())+1);
        zj();
    });
    $(".number .min").click(function () {
        var nub = $(this).parents(".number").find(".nub");  //当前数量
        var mon = $(this).parents("li").find(".mon");       //当前单价
        var mony = $(this).parents("li").find(".money span");//当前显示价格
        var tot = $(".total .piece span");
        if (nub.val()==0){
            nub.val(0);
        }else {
            nub.val(parseFloat(nub.val())-1);
            var dj = parseFloat(mon.text())*100*nub.val()/100;
            mony.text(dj.toFixed(2));
            tot.text(parseFloat(tot.text())-1);
        }
        zj();
    });
    function zj() {
        var lijg = $(".comm figure .shop .money span");
        var sum=0;
        for(var i=0;i<lijg.length;i++){
            sum += parseFloat(lijg.get(i).innerHTML);
        }
        $(".comm figure .total .mony span").text(sum.toFixed(2));
    }

    /*购物袋数量加减*/
    $(".jj .add").click(function () {
        var nub = $(this).parents(".jj").find(".text");      //当前数量
        var mon = $(this).parents("li").find(".mony");       //当前单价
        var mony = $(this).parents("li").find(".money span");//当前显示价格
        var xz = $(this).parents("li").find(".xz");
        nub.val(parseFloat(nub.val())+1);
        var jg = parseFloat(mon.text())*100*nub.val()/100;
        mony.text(jg.toFixed(2));
        xz.addClass("active");
    });
    $(".jj .min").click(function () {
        var nub = $(this).parents(".jj").find(".text");  //当前数量
        var mon = $(this).parents("li").find(".mony");       //当前单价
        var mony = $(this).parents("li").find(".money span");//当前显示价格
        var xz = $(this).parents("li").find(".xz");
        if (nub.val()==0){
            nub.val(0);
            mony.text(0);
        }else if(nub.val()==1){
            xz.removeClass("active");
            nub.val(0);
            mony.text(0);
        }else {
            nub.val(parseFloat(nub.val())-1);
            var jg = parseFloat(mon.text())*100*nub.val()/100;
            mony.text(jg.toFixed(2));
            xz.addClass("active");
        }
    });
    $("#shop_car").click(function () {
        $(".alert_shopping_").show();
        $(".alert_shopping").show();
    });
    $("#sure").click(function () {
        $(".alert_shopping_").hide();
        $(".alert_shopping").hide();
    });
    /*购物袋选择*/
   /* $(".alert_shopping ul li .xz").click(function () {
        $(this).toggleClass("active");
    })*/

});

/*登录验证*/
function check(form) {
    var name = $("#name").val();
    var acc = $("#account").val();
    var pass = $("#password").val();
    if(name == ""){
        $(".login_alert2").show();

        return false;
    };
    if(acc == ""){
        $(".login_alert").show();
        $(".login_alert2").hide();
        return false;
    };
    if(pass == ""){
        $(".login_alert").show();
        $(".login_alert2").hide();
        return false;
    };

}

//设定倒数秒数
var t = 4;
function showTime(){
    t -= 1;
    $(".alert_weigh p span").text(t);

    //每秒执行一次,showTime()
    var s = setTimeout("showTime()",1000);

    if(t==0){
        t = 4;
        $(".alert_weigh_").hide();
        clearTimeout(s);
        $(".alert_weigh p span").text(t);
    }
    /*商品称重弹框*/
    $("#confirm").click(function () {
        $(".alert_weigh_").hide();
        clearTimeout(s);
        t = 4;
        $(".alert_weigh p span").text(t);
    });
}

//执行showTime()
$("#pay").click(function () {
    var shot = showTime();
    /*$(".alert_weigh p span").text(t);*/
    $(".alert_weigh_").show();
    shot;
});


