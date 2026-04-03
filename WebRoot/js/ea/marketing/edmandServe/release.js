var u = navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

$(function () {
    var btn = document.getElementById("send_code");
    var tel = $("#tel");
    var _name = $("#ddcontactname");
    var valnum = document.getElementById("valnum");
    let otype;
    //提示框
    $("#prompt").css("position", "absolute").css("top", $(window).height() * 0.09 + "px");
    $("#prompt").find("div").css("height", $(window).height() * 0.06 + "px").css("font-size", $(window).height() * 0.0285 + "px").css("color", "#FFFFFF");
    $("#prompt").find("div").css("-moz-border-radius", $(window).height() * 0.015 + "px").css("-webkit-border-radius", $(window).height() * 0.015 + "px");


    localforage.getItem('gz').then(function (value) {
        if (value && typeof value === 'object' && Object.keys(value).length > 0) {
            $("#selsct_classify").val(value["val"]);
            $("#ddscodeid").val(value["id"]);
            originPage=value["originPage"];
            $("#originPage").val(value["originPage"]);

            if (originPage != "" && originPage != null) {
                if (originPage.indexOf("enterprise") !== -1) {
                    $("h1").text("企业应急发布");
                    otype = "2";
                    $(".companyname").show();
                    $("#companyname").val(JSON.parse(localStorage.getItem("selectedMenuName"))[0]);
                } else {
                    otype = "1";
                    $(".companyname").hide();
                    $("h1").text("家应急发布");
                }
            }
        }
    }).catch(function (err) {
        console.error("存储参数时出错:", err);
        // 提示用户或采取其他措施
        alert("数据传输失败，请稍后再试。");
    });




    //行业
    $("#selsct_classify").click(function () {
        $(".overlay_text").show();
        $(".nest_bd").find(".classify_wrap").remove();

        /*$("#iframe-").attr("src", basePath + "/ea/dserve/ea_wtypeListBySccid.jspa?originPage=win-fb&staffid=" + staffid);*/
        $("#iframe-").attr("src", basePath + "page/ea/main/marketing/edmandServe/workType_save.jsp?originPage=win-fb");
        $(".nest_hd").show();
        $(".nest_bd").css("margin","20% auto 0 auto").css("margin-top","2.16rem");
        $(".nest_page").show();
        $(".overlay_text").hide();
    });

    $(".nest_back").click(function () {
        $(".nest_page").hide();
    });

    $(".release_rec").click(function () {
        document.location.href = basePath + "/ea/dserve/ea_detailListBySccid.jspa?";
    });

    $(".d_r_site").click(function () {
       /* if (isAndroid) {
            Android.callgetRoundLocal();
        } else if (isiOS) {
            var url = "func=" + 'iosMapaddress';
            window.webkit.messageHandlers.Native.postMessage(url);
        }else{*/
            $("#iframe-").attr("src", basePath + "page/ea/main/marketing/edmandServe/locationMap.jsp?originPage=win-fb");
            $(".nest_hd").hide();
            $(".nest_bd").css("margin","0").css("margin-top","0");
            $(".nest_page").show();
            $(".overlay_text").hide();
        /*}*/
    });

    //valnum.addEventListener("blur", verCode);

    //处理浏览器输入法遮挡
    var screenH = window.innerHeight;
    window.onresize = function () {
        var t = window.innerHeight;
        console.log(t);
        console.log(screenH);
        var inp = $("input:focus")[0];
        if (t < screenH) {
            inp.scrollIntoView(false);
        }
    };

    //选择时间插件
    var currYear = (new Date()).getFullYear();
    var opt = {};
    opt.date = {
        preset: 'date'
    };

    opt.default = {
        theme: 'android-ics light', //皮肤样式
        display: 'modal', //显示方式
        mode: 'scroller', //日期选择模式
        dateFormat: 'yyyy-mm-dd',
        lang: 'zh',
        showNow: true,
        nowText: "今天",
        startYear: currYear - 10, //开始年份
        endYear: currYear + 10 //结束年份
    };


    var optDate = $.extend(opt['date'], opt['default']);
    $("#begin_time").mobiscroll(optDate).date(optDate);

    //时间判断

    $("#begin_time").change(function () {
        var cur_time = getNowFormatDate();

        //console.log(cur_time);
        var t = $(this).val();
        var d = new Date(t).getTime(); //开始时间
        //console.log(d);
        if (d < cur_time) {
            console.log("请选择大于当前时间");
            $(this).val('');
        }
    })
    $("#end_time").change(function () {
        var begin = $("#begin_time").val(); //开始时间
        var end = $(this).val(); //结束时间
        if (begin) {
            if (end < begin) {
                console.log("请选择大于开始时间的时间");
                $(this).val('');
            }
        } else {
            console.log("请先选择开始时间");
            $(this).val('');
        }

    });

});



function gzfun(data) {
    $("#selsct_classify").val(data.val);
    $("#ddscodeid").val(data.id);
    $(".nest_page").hide();
}

//发送验证码
function sendCode(thisBtn) {
    if ($("#ddcontactname").val() == "") {
        prompt("姓名不能为空");
        return false;
    }
    if ($("#tel").val() == "") {
        prompt("手机号不能为空");
        return false;
    }
    if (!ver_phone()) {
        return false;
    }
    //发送短信
    $.ajax({
        cache: true,
        type: "POST",
        url: basePath + "/ea/android/sajax_ea_getduanxin.jspa?pahe=" + tel.value,
        async: false,
        dataType: "json",
        success: function (data) {
            var member = data;
            i = member.returna;
            alert(i);
        }
    });

    btn = thisBtn;
    btn.disabled = true; //将按钮置为不可点击
    btn.innerHTML = nums + '秒重新获取';
    clock = setInterval(doLoop, 1000); //一秒执行一次
    btn.className = "send_code disabled";
}

//验证码验证
function verCode() {
    if ($("#valnum").val() == "") {
        prompt("请填写验证码");
        return false;
    } else if ($("#valnum").val() != i) {
        prompt("验证码不正确");
        return false;
    } else {
        return true;
    }
}

function doLoop() {
    nums--;
    if (nums > 0) {
        btn.innerHTML = nums + '秒重新获取';
    } else {
        clearInterval(clock); //清除js定时器
        btn.disabled = false;
        btn.innerHTML = '获取验证码';
        nums = 60; //重置时间
        btn.className = "send_code";
    }
}


//手机号验证
function ver_phone() {
    var Sreg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    if ($("#tel").val() == "") {
        prompt("手机号不能为空");
        return false;
    } else if (!Sreg.test($("#tel").val())) {
        prompt("请输入正确格式电话号！");
        return false;
    } else {
        return true;
    }
}

function save_submit() {
    var b = false;
    $(".notNull").each(function () {
        var nnval = $(this).val();
        if (nnval == null || nnval == "") {
            prompt("请把信息填写完整");
            b = true;
            return false;
        }
    });

    if (b) {
        return false;
    }

    if ($("#valnum").val() != i) {
        prompt("验证码不正确");
        return false;
    }
    $(".overlay_text").show();
    $(".nest_bd").find(".classify_wrap").remove();

    var formData = new FormData();

    $("#savedetailform").find("input").each(function () {
        if (typeof $(this).attr("name") =="undefined"){
        }else {
            formData.append($(this).attr("name"), $(this).val());
        }
    })
    var photoFile = $('#photos')[0].files[0];
    if (photoFile) {
        formData.append('photos', photoFile);
    }
    $.ajax({
        url: basePath + "ea/dserve/sajax_ea_saveDetail.jspa?",
        type: "POST",
        dataType: "json",
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        data:formData,
        success: function (data) {
            let flag = data.flag;
            switch (flag){
                case "1":
                    document.location.href = basePath + "/page/WFJClient/pc/pc_login.jsp";
                    break;
                case "操作成功":
                    document.location.href = basePath + "ea/dserve/ea_toPage_success.jspa?originPage="+originPage+"&tle=" + tle;
                    break;
                default:
                    prompt(flag);
                    break;
            }
        }
    });
    $(".overlay_text").hide();
}

/**************************定位获取地址开始************************/
function ios_address(param) {
    var p = param.substring(0, param.indexOf(">"));
    var address = p.substring(0, p.indexOf("<"));
    var jv = p.substring(p.indexOf("<") + 1);
    var j = jv.substring(1, jv.indexOf(","));
    var v = jv.substring(jv.indexOf(",") + 2);
    $("#ddaddress").val(address);
    $("#coordinate").val(j + "," + v);
}

function a_address(param) {
    var address = param.substring(0, param.indexOf(","));
    var coordinate = param.substring(param.indexOf(",") + 1);
    $("#ddaddress").val(address);
    $("#coordinate").val(coordinate);
}

function page_address(param) {
    var address = param.address;
    var coordinate = param.coordinate;
    $("#ddaddress").val(address);
    $("#coordinate").val(coordinate);
    $('iframe').attr("src", "");
    $(".nest_page").hide();
}

/**************************定位获取地址结束************************/

//时间
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;

    var n = new Date(currentdate).getTime();
    //console.log(n);
    return n;
}

//提示框样式
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