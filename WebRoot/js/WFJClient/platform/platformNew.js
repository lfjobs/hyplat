$(function(){
    $("#prompt").css("position", "absolute").css("top", $(window).height() * 0.2+ "px");
    $("#prompt").find("div").css("height", $(window).height() * 0.06 + "px").css("font-size", $(window).height() * 0.0285 + "px").css("color", "#FFFFFF");
    $("#prompt").find("div").css("-moz-border-radius", $(window).height() * 0.015 + "px").css("-webkit-border-radius", $(window).height() * 0.015 + "px");
    $(".div_xz").click(function(){
        if($(".div_i").is(":hidden")){
            $(".div_i").show();
        }else{
            $(".div_i").hide();
        }
    })
    getBm();
    getrews();
      //提交咨询
    $("#submit_btn").click(function(){
        var _name = $("#name").val();
        if(_name== ""){
            prompt("姓名不能为空");
        //    $("#name").focus();
            return false;

        }
        var tel = $("#tel").val();
        if(tel ==""){
            prompt("手机号不能为空");
       //     $("#tel").focus();
            return false;
        }
        if(!ver_phone(tel)){

      //      $("#tel").focus();
            return false;
        }
        var valnum = $("#valnum").val();
        if(!verCode(valnum)){

         //   $("#valnum").focus();
            return false;
        }
        var tcontent = $("input[name='tc']:checked").val();
        if(tcontent ==""||tcontent==undefined){
            prompt("请选择咨询内容");

            return false;
        }
        $("#tcontent").val(tcontent);
        if(ntoken==1){
            return false;
        }
        ntoken = 1;





        var url = basePath + "/ea/consult/sajax_ea_saveConsult.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "post",
            data: $('#form1').serialize(),
            dataType: "json",
            async: true,
            success: function (data) {
                prompt("提交成功！");
                document.form1.reset();
                clearInterval(clock); //清除js定时器
                $("#ver_btn").attr("disabled",false);
                $("#ver_btn").val('获取验证码');
                nums = 60; //重置时间
                $(".zxcount").text(Number($(".zxcount").text())+1)
                ntoken = 0;
            }
        })

    });

    $(".ds-div").click(function () {
        $(".addCategorie").show();
    });
    $("#back").click(function () {
        $(".addCategorie").hide();
    });
});
var ntoken = 0;
var clock = '';
var nums = 60;
function sendCode(thisBtn) {
    var _name = $("#name").val();
    if(_name == ""){
        prompt("姓名不能为空");
        return false;
    }
    var tel = $("#tel").val();
    if(tel ==""){
        prompt("手机号不能为空");
        return false;
    }
    if(!ver_phone(tel)){
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
    $("#ver_btn").val(nums + '秒');
    clock = setInterval(doLoop, 1000); //一秒执行一次

}

//验证码失去焦点验证
// valnum.addEventListener("blur",verCode);

// //处理浏览器输入法遮挡
// var screenH = window.innerHeight;
// window.onresize = function() {
//     var t = window.innerHeight;
//     console.log(t);
//     console.log(screenH);
//     var inp = $("input:focus")[0];
//     if (t < screenH) {
//         inp.scrollIntoView(false);
//     }
// };



function prompt(obj) {
    if ($("#prompt").css("display") != "none")
        return;
    $("#prompt").find("span").text(obj);
    $("#prompt").fadeIn(500);
    setTimeout(function () {
        $("#prompt").fadeOut(500);
        $("#prompt").find("span").text("");
    }, 2000);
}

//手机号验证
function ver_phone(tel) {
    var Sreg= /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\d{8})$/;
    if(tel ==""){
        prompt("手机号不能为空");
        return false;
    }else if(!Sreg.test(tel)){
        prompt("请输入正确格式电话号！");
        return false;
    }else {
        if(isCheckin(tel,ppId)){
            return true;
        }else{
            return false;
        }

    }
}

function doLoop() {
    nums--;
    if (nums > 0) {
       $("#ver_btn").val(nums + '秒');
    } else {
        clearInterval(clock); //清除js定时器
        $("#ver_btn").attr("disabled",false);
        $("#ver_btn").val('获取验证码');
        nums = 60; //重置时间
    }
}

//验证码验证
function verCode(valnum) {
    if(valnum==""){
        prompt("请填写验证码");
        return false;
    } else if(valnum!=i){
        prompt("验证码不正确");
        return false;
    }else {
        return true;
    }
}



// 判断手机号登记过
function isCheckin(tel,ppId){
    var bool = null;
        $.ajax({
            type :"POST",
            url : basePath+"/ea/consult/sajax_ea_checkIn.jspa",
            async :false,
            dataType : "json",
            data:{
                "consult.consultantPhone":tel,
                 "consult.ppid":ppId
            },
            success :function(data){
                var member = eval("(" + data + ")");
                if(member.result==0){
                    console.log("未登记，可以使用");
                    bool = true;
                }
                else{
                    prompt("您已提交过，无需重复提交！");
                    $("#tel").val("");
                    $("#tel").focus();

                    bool = false;
                }
            },
            error:function(data){
                console.log("验证失败");
            }
        });

        return bool;

}
//打开链接
function openUrl(url,text){
    document.location.href = basePath+"page/ea/main/production/cprocedure/qrshare/openurl.jsp?title="+text+"&url="+url;
}


function getBm() {


    var ulp = basePath
        + "ea/consult/sajax_ea_getConBmlist.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {

            ppid: ppId



        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var list = member.list;
            var html = [];

            if (list == null||list.length==0) {


                return false;

            }else{
              $(".ybm").show();
                $(".wbm").hide();

            }

            var obj = "";


            for (var i = 0; i < list.length; i++) {
                obj = list[i];
                html.push("<div class='border-outline'><div class='radis'><img src='" + basePath + obj[1]+"' onerror=\"this.src=\'' + basePath + '/images/ea/production/qrshare/default.png\'\"/></div><h4>"+obj[0]+"</h4></div>");

            }


            $(".tab-div").append(html.join(""));


        },
        error: function (data) {
            console.log("失败");
        }
    });


}

function getrews(){
    var ulp = basePath
        + "/ea/wfjshop/sajax_ea_getReward.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            ppid: ppId
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var list = member.list;
            var html = [];
            var liHtml=[];
            var obj = "";
            if(list.length>0){
                for (var i = 0; i < list.length; i++) {
                    r = list[i];
                    html.push("<div class='border-outline'><div class='radis'>");
                    html.push("<img src='" + basePath + r[3]+"' onerror=\"this.src=\'' + basePath + '/images/ea/production/qrshare/default.png\'\"/>");
                    html.push("</div><h4>"+r[2]+"</h4></div>");
                    liHtml.push("<li>");
                    liHtml.push("<div class='staff-div' style='width: 25%'>");
                    liHtml.push("<img src='" + basePath + r[3]+"' onerror=\"this.src=\'' + basePath + '/images/ea/production/qrshare/default.png\'\"/>");
                    liHtml.push("<span>"+r[2]+"</span></div>");
                    liHtml.push("<div class='jkname' style='width: 25%;text-align: center;'>现金</div>");
                    liHtml.push("<div class='jznum' style='width: 15%;text-align: center;'>一笔</div>");
                    liHtml.push("<div class='money-div' style='width: 35%;text-align: center;'>"+r[1]+"元</div>");
                    liHtml.push("</li>");
                }
                $(".ds-div").append(html.join(""));
                $(".product").append(liHtml.join(""));
            }
        },
        error: function (data) {
            console.log("失败");
        }
    });
}

