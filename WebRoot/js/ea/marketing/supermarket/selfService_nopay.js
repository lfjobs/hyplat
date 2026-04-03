
$(function(){
    jisuan();
    audio();

    //提交
    $("#save").click(function(){
       var reason =  $("input[name='rea']:checked").val();
       if(reason==undefined){
             return false;
       }
       if(reason=="其他因素"){
        var other = $("#other").val();
        if(other==""){
            return false;
        }
           reason+=":"+other;
       }

       //提交
        $("#form").find("#relateID").val(relateID);
        $("#form").find("#reason").val(reason);
        document.form.submit.click();
        token = 13;
    });
});

function re_load(journalNum){
    if(token){
window.history.go(-1);
    }

}


//统计总数量/总金额
function jisuan(){

    var totalnum = 0;
    var totalmoney = 0;
    $(".tr").each(function(){
        var num =Number($(this).find(".num").text());
        var tprice = Number($(this).find(".tprice").text());
        var barCode = $(this).find(".barCode").text();
        var goodsName = $(this).find(".goodsName").text();
        if(barCode.substring(0,2)=="21"){
            $(this).find(".num").text("1");
        }

        var money = Number((num*tprice)).toFixed(2);
        $(this).find(".price").text(money);
        totalnum = totalnum+Number($(this).find(".num").text());
        totalmoney = Number(totalmoney)+Number(money);
       var ynum = $(this).find(".ynum").text();
       var xj = Number(num)-Number(ynum);
        $(this).find(".ynum").text("减操作个数："+xj);

    });
    $(".totalnum").text(totalnum);
    $(".totalmoney").text(Number(totalmoney).toFixed(2));


}

function round(value){

    var aStr = value.toString();
    var aArr = aStr.split('.');
    if(aArr.length > 1) {
        if(aArr[1].length>3) {
        	value = Number(aStr).toFixed(3);
        }
    }

    return Number(value);
}

function audio(){
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    $(function(){
        try {
            if (isAndroid == true) {
                Android.speechOutputForAndroid("上次购物有删除或未结算的商品，请说明理由");
            } else {
                console.log("请在安卓设备访问！");
            }
        }catch(error){

        }

    });
}

