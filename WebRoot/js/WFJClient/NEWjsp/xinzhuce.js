
$(function () {
    $("#prompt").css("position","absolute").css("top",$(window).height()*0.15+"px");
    $("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
    $("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
})
//密码
function ver_password() {

    var flag = false;
    var message = "";

    if(password.value == ''){
        prompt("请输入密码！");
        return false;
    }else if(password.value.length<6){
        prompt("密码长度不安全");
        return false;
    }else if(confirmPassword.value == ''){
        prompt("请输入确认密码");
    }else if(confirmPassword.value != password.value){
        prompt("二次密码输入不一致，请重新输入！");
        confirmPassword.value="";
        confirmPassword.focus();
        return false;
    }else{
        return true;
    }
}


function doLoop() {
    nums--;
    if (nums > 0) {
        btn.innerHTML = nums + '秒';
    } else {
        clearInterval(clock); //清除js定时器
        btn.disabled = false;
        btn.innerHTML = '获取验证码';
        nums = 60; //重置时间
    }
}

//验证码验证
function verCode() {
    if(valnum.value==""){
        prompt("请填写验证码");
        return false;
    } else if(valnum.value!=i){
        prompt("验证码不正确");
        return false;
    }else {
        return true;
    }
}

// 判断手机号是否注册
function isRegister(){
    if(tel.value != ''&& ver_phone()){
        $.ajax({
            cache : true,
            type :"POST",
            url : basePath+"/ea/android/sajax_ea_isacounnt.jspa?pahe="+tel.value,
            async :false,
            dataType : "json",
            success :function(data){
                var member = eval("(" + data + ")");
                if(member.result==0){
                    d=1;
                    console.log("未注册，可以使用");
                }
                else{
                    prompt("已被注册,请更换手机号码！");
                    tel.value="";
                    tel.focus();
                    d=2;
                    return;
                }
            }
        });
    }else{
        tel.value="";
    }
}

//手机号验证
function ver_phone() {
    var Sreg= /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\d{8})$/;
    if(tel.value ==""){
        prompt("手机号不能为空");
        return false;
    }else if(!Sreg.test(tel.value)){
        prompt("请输入正确格式电话号！");
        return false;
    }else {
        return true;
    }
};

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