
var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

$(function(){
    clearAllCookie();
    //弹出层

    $("#prompt").find("div").css("height",$(window).height()*0.1+"px").css("line-height",$(window).height()*0.1+"px").css("font-size","0.9rem").css("color","#FFFFFF").css("padding","0 0.5rem");
    $("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");



    var ua = navigator.userAgent.toLowerCase();
    var isWeixin = ua.indexOf('micromessenger') != -1;

    if (!isWeixin) {

        try {
            Android.jumpToLoginActivity();
        } catch (e) {


        }

    }else{

        var   url = window.location.href;

        if (url.indexOf("exitLogin") == -1) {
            url = basePath+"/ea/wfjlogin/ea_validateAccount.jspa";
            window.location.href = basePath + "/ea/wfjlogin/ea_wxloginNew.jspa?redirectUrl=" + encodeURIComponent(url);
        }


    }
    //协议同意
    $(".xy").click(function () {
        if($(".xy_no").is(":hidden")){

            $(".xy_no").show();
            $(".xy_yes").hide();
        }else{
            $(".xy_no").hide();
            $(".xy_yes").show();
        }
    });

});




function mlogin() {
    document.location.href = basePath+'/ea/earth/ea_earthIndex.jspa';
    // if (isAndroid == true || isiOS == true) {
    //     document.location.href = basePath+'/ea/earth/ea_earthIndex.jspa';
    //
    //
    // } else {
    //
    //     window.open(basePath+'/ea/earth/ea_earthIndex.jspa', '', 'width=424px,height=756px,scrollbars=yes,resizable=yes,channelmode,location=no,toolbar=no');
    //     // window.open('', '_self');
    //
    //     // window.location.href="about:blank";
    //     //	   window.location.href = '<%=basePath%>/ea/earth/ea_earthIndex.jspa';
    //     // window.close();
    // }


}


function login() {
    var reg = new RegExp("^1[3|4|5|6|7|8|9][0-9]\\d{8}$");
    if ($("#count").val() == '') {
        prompt("请输入账号");
        $("#count").focus();
        return;
    }
    if (!reg.test($("#count").val())) {
        prompt("手机号格式不正确！");
        $("#count").focus();
        return;
    }
    if ($("#pw_pwd").val() == '') {
        prompt("请输入密码");
        return;
    }
    if($(".xy_yes").is(":hidden")){
        prompt("请勾选我已同意隐私政策和用户协议");
        return;
    }
    var formData = $("#form").serialize();
    var url = basePath + "/ea/wfj/sajax_ea_pcLogin.jspa";

    var user = $("#count").val();
    $.ajax({
        url: url,
        type: "POST",
        data: formData,
        dataType: "json",
        success: function cbf(data) {
            var m = eval("(" + data + ")");
            var result = m.result;
            var path = "/ea/earth/ea_earthIndex.jspa?";
            if (result == "suc") {
                if (jumpType == "inspect") {
                    // 获取数据时检查过期时间
                    localforage.getItem('dataKey').then(function (data) {
                        if (data && new Date().getTime() < data.expires) {
                            console.log('Data not expired');
                            console.log(data.formVal);
                            console.log(data.formVal.sn);
                            if(data.formVal.sn!=null&&data.formVal.sn!=""){
                                console.log(data.formVal.sn);
                                var htmlstr = [];
                                htmlstr.push("<form action='" + basePath + "/ea/bindnfc/ea_readNfc.jspa' id='subForm' name='subForm' METHOD='post'>");
                                htmlstr.push("<input type='hidden' name='sn' value='" + data.formVal.sn + "'/>");
                                htmlstr.push("<input type='hidden' name='model' value='" + data.formVal.model + "'/>");
                                htmlstr.push("</form>");
                                $("body").push(htmlstr);
                                document.subForm.submit();
                            }else {
                                console.log("空值");
                                localforage.removeItem('dataKey');
                                loginMain(user);
                            }
                        } else {
                            console.log('Data expired or not found');
                            localforage.removeItem('dataKey');
                            loginMain(user);
                        }
                    }).catch(function (err) {
                        console.log('程序错误！请重新读取芯片');
                        localforage.removeItem('dataKey');
                        loginMain(user);
                    });
                } else {
                    saveAutoLoginUser();
                    loginMain(user);
                }
            } else {
                prompt("账号或密码错误");
            }

        }, error: function cbf(data) {
            alert("出错");
        }
    });


}

function saveAutoLoginUser() {
    var account = $("#count").val();
    var password = $("#pw_pwd").val();
    localStorage.setItem("autoLoginUser", `${account}|${password}`);
}

function loginMain(user){
    // //跳转首页
    // const isWebview = localStorage.getItem('isWebview');
    // const isWebviewBool = isWebview === 'true';
    // if (isWebviewBool) {
    //     document.addEventListener('UniAppJSBridgeReady', function() {
    //         uni.switchTab({
    //             url: '/pages/index/index'
    //         })
    //         console.log('uniapp-index')
    //     })
    // }
   // document.location.href = basePath + "/ea/earth/ea_earthIndex.jspa?login=login";
    console.log("preUrl"+preUrl);
    if(preUrl!=""&&preUrl!="null"&&preUrl!=null&&preUrl.indexOf("pc_login")==-1&&preUrl.indexOf("exitLogin")==-1&&preUrl.indexOf("earthIndex")==-1){
        document.location.href = preUrl;
    }else {


        if(!(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent))) {
            // pc
            window.open(basePath + '/ea/earth/ea_earthIndex.jspa?login=login', '数字地球',
                'width=410,height=830,top=100,left=500')
        } else {
            // 移动
            document.location.href = basePath + "/ea/earth/ea_earthIndex.jspa?login=login";
        }
    }
 //   document.location.href = basePath + "/ea/earth/ea_earthIndex.jspa?login=login";
    //
    // if (isAndroid == true || isiOS == true) {
    //     document.location.href = basePath+"/ea/earth/ea_earthIndex.jspa?login=login";
    // } else {
    //     window.open(basePath+'/ea/earth/ea_earthIndex.jspa?user=' + user + "&pc=pc", '', 'width=424px,height=756px,scrollbars=yes,resizable=yes,channelmode,location=no,toolbar=no');
    //     //  window.open('', '_self');
    //     //   window.location.href = "<%=basePath%>/ea/earth/ea_earthIndex.jspa?user="+user+"&pc=pc";
    //     // window.location.href="about:blank";
    //     //   window.close();
    // }


}

function prompt(obj){
    if($("#prompt").css("display")!="none")
        return;
    $("#prompt").find("span").text(obj);
    $("#prompt").fadeIn(500);
    setTimeout(function(){
        $("#prompt").fadeOut(50);
        $("#prompt").find("span").text("");

    }, 3000);
}