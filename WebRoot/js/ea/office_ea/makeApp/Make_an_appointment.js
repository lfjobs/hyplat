/**
 * Created by Administrator on 2016/10/24 0024.
 */
var posNum = "";
$(document).ready(function() {
    try {
        posNum = Android.forAndroidDeviceId();

      if (posNum != "" && posNum != null) {
            posNum = isExistPosNum(posNum);
      }

    }catch(error){
        if(($(window).width()==1080&&$(window).height()==1546)||($(window).width()==534&&$(window).height()==636)) {
            posNum = 123;

        }
    }

    if(posNum!=null&&posNum!=""){
        $(".li-name").show();
    }else{
        $(".li-name").hide();
    }

	/*搜索*/
    $(".head_top ul li:nth-child(2) input").click(function () {
        $(".alert_search").show();
        $(".alert_search input:nth-child(1)").focus();
    });

    $(".alert_search input:nth-child(3)").click(function () {
        $(".alert_search").hide();
        var sea_txt = $(".alert_search input:nth-child(1)").val();
        var sea_txt2 = $(".head_top ul li:nth-child(2) input");
        if (sea_txt == "") {
            sea_txt2.val("");
            sea_txt2.addClass("bgt");
            console.log(1)
        } else {
            sea_txt2.val(sea_txt);
            sea_txt2.removeClass("bgt");
            console.log(2)
        }
    });
    $(".alert_search input:nth-child(1)").keyup(function () {
        var t = $(".alert_search input:nth-child(1)");
        if (t.val() == "") {
            $(".alert_search .top #qx").show();
            $(".alert_search .top #ss").hide();
        }
        else{
            $(".alert_search .top #ss").show();
            $(".alert_search .top #qx").hide();
        }

    });

    //绑定滚动条事件
    $(".con").bind("scroll", function () {
        var sTop = $(".con").scrollTop();
        var sTop = parseInt(sTop);
        var height = $(window).height() * 1;
		/*console.log(sTop);*/
        if (sTop >= height) {
            $("#return").slideDown();
            $("#return").show();
        }
        else {
            $("#return").hide();
        }
    });

	/*搜索*/
    $("header ul li:nth-child(3) #search").click(function(){
        $(".alert").show();
        $(".alert_search").show();
    });

    //查看订单/打印小票
    $(".check").click(function () {
        if(posNum!=null&&posNum!=""){
            printTicket();
        }else {
            document.location.href = basePath + "/ea/mappointment/ea_bookingDetails.jspa?cbId=" + cbId + "&sccId=" + sccId;

        }

    })



});

//判断是否是终端机器
function isExistPosNum(posNum){
    var url = basePath + "ea/smg/sajax_sm_isExistPosNum.jspa";
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

 //打印小票
function printTicket(){
    var url = basePath + "/ea/mappointment/sajax_ea_printInfo.jspa";
    $.ajax({
        url: encodeURI(url),
        type: "post",
        dataType: "json",
        data: {
            journalNum: ddid
        },
        async: false,
        success: function (data) {
            var jsonresult = eval("(" + data + ")");
            var obj = jsonresult.result;
            var array = {};
            array["companyname"] = obj[10];
            array["journalNum"] = obj[0];
            array["date"] = obj[3]
            array["stuname"] = obj[1];
            array["stuacc"] = obj[2];
            array["coachname"] = obj[7];
            array["directname"] = obj[6];
            array["watchacc"] = obj[8];
            array["watchname"] = obj[9];
            array["placeNum"] = obj[4];
            array["placeName"] = obj[5];
            array["ercode"] = "99"+obj[0];



            var u = window.navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                try {
                    if (isAndroid == true) {

                        Android.printTicketPre(JSON.stringify(array));
                        Android.speechOutputForAndroid("小票正在打印请稍后");
                    } else {
                        console.log("请在安卓设备访问！");
                    }

                }catch(error){

                }
            setInterval(function(){
                document.location.href = basePath+"ea/mappointment/ea_theTestTime.jspa?sccId=&posNum="+posNum;

            }, 2000);

        }, error: function (data) {

        }
    });
}


var ntoken = 0;
var clock = '';
var nums = 60;
function sendCode(thisBtn) {
    var tel = $("#tel").val();
    if(tel ==""){
        prompt("手机号不能为空");
        return false;
    }

    var Sreg= /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\d{8})$/;

     if(!Sreg.test(tel)){
        prompt("请输入正确格式电话号！");
        return false;
    }
    //发送短信
    $.ajax({
        cache : true,
        type :"POST",
        url : basePath+"/ea/android/sajax_ea_getduanxin.jspa?pahe="+tel,
        async :false,
        dataType : "json",
        success :function(data){
            var member = data;
            i = member.returna;
        }
    });
    $("#ver_btn").attr("disabled",true); //将按钮置为不可点击
    $("#ver_btn").val("剩余"+nums + '秒');
    clock = setInterval(doLoop, 1000); //一秒执行一次

}




function prompt(obj){
    if($("#prompt").css("display")!="none")
        return;
    $("#prompt").find("span").text(obj);
    $("#prompt").fadeIn(500);
    setTimeout(function(){
        $("#prompt").fadeOut(500);
        $("#prompt").find("span").text("");
    }, 2000);
}
function doLoop() {
    nums--;
    if (nums > 0) {
        $("#ver_btn").val("剩余"+nums + '秒');
    } else {
        clearInterval(clock); //清除js定时器
        $("#ver_btn").attr("disabled",false);
        $("#ver_btn").val('获取验证码');
        nums = 60; //重置时间
    }
}
/**
 * 绑定手机号
 */
function bindAccount(){

    var tel = $("#tel").val();
    if(tel ==""){
        prompt("手机号不能为空");

        return false;
    }
    var valnum = $("#valnum").val();
    if(valnum==""){
        prompt("请填写验证码");
        return false;
    } else if(valnum!=i){
        prompt("验证码不正确");
        return false;
    }
    if(ntoken==1){
        return false;
    }
    ntoken = 1;
    var url = basePath + "/ea/mappointment/sajax_ea_bindWfjAccount.jspa";
    $.ajax({
        url: encodeURI(url),
        type: "post",
        data: {
            openid:openid,
            tel:tel
        },
        dataType: "json",
        async: false,
        success: function (data) {
            var jsonresult = eval("(" + data + ")");
            var result = jsonresult.result;
            clearInterval(clock); //清除js定时器
            $("#ver_btn").attr("disabled",false);
            $("#ver_btn").val('获取验证码');
            nums = 60; //重置时间
            $("#valnum").val("");
               if(result=="0"){
                   ntoken=0
                   Android.speechOutputForAndroid("该手机号没有预约练车，请确认手机号或先预约");
                   prompt("该手机号没有预约练车!");


               }else if(result=="1"){
                   ntoken=0
                   Android.speechOutputForAndroid("该手机账号已绑定其他微信账号，不能重复绑定");
                   prompt("该手机账号已绑定其他微信账号!");


               }else if(result=="3"){
                   var bifId = jsonresult.bifId;
                   searchOrder(basePath+"/ea/restaurant/sajaxj_ea_scancode.jspa?scancode=04"+bifId);

               }

        }
        ,
        error:function(data){

        }
    })

}


function  searchOrder(code){
    var url = code;
    $.ajax({
        url : encodeURI(url),
        type : "get",
        data : {
            "pos":"pos",
        },
        dataType : "json",
        async : false,
        success : function(data) {
            var bl = data.bl;
            var os = data.os;
            var cbId = os[15];
            var xysccid = os[16];
            document.location.href = basePath+"/ea/mappointment/ea_bookingDetails.jspa?cbId="+cbId+"&sccId="+xysccid+"&dp=1&sm=1";
        }
    })
}

function  bindCard(){
    Android.speechOutputForAndroid("切换至购物卡绑定");
   document.location.href = basePath + "/page/ea/main/marketing/supermarket/selfservice/scardPay.jsp?openid="+openid+"&wxbind=lc&posNum="+posNum;

}

