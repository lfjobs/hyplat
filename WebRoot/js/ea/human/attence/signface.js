$(function () {
    var token = 0;
    $("#prompt").css("position", "absolute").css("top", $(window).height() * 0.2 + "px");
    $("#prompt").find("div").css("height", $(window).height() * 0.06 + "px").css("font-size", $(window).height() * 0.0285 + "px").css("color", "#FFFFFF");
    $("#prompt").find("div").css("-moz-border-radius", $(window).height() * 0.015 + "px").css("-webkit-border-radius", $(window).height() * 0.015 + "px");
    $("header").css("height", $(window).height() * 0.08 - 1 + "px");
    $("header").css("line-height", $(window).height() * 0.08 - 1 + "px");
    $("header ul li").css("height", $(window).height() * 0.08 - 1 + "px");
    $("header ul li").css("line-height", $(window).height() * 0.08 - 1 + "px");


    // 签到调用人脸
    $("#signface").click(function () {
//		dealFaceID("oXoxsuBfaCSsG51mTowkIKefn1q8");
        if (timejz == 1) {
            return false;
        }

        if (token == 0) {
            token = 1;
            prompt("人脸识别系统启动中");

            var storeID = companyId;
            if (companyId.length > 32) {
                storeID = companyId.substring(7);
            }
            try {
                Android.androidAcquireFaceID(storeID, companyname);
            } catch (error) {

            }
        }
        setTimeout(function () {
            token = 0;
        }, 4000);//3秒内不能重复点击
    });

});


var weekday = new Array(7)
weekday[0] = "星期日 "
weekday[1] = "星期一 "
weekday[2] = "星期二 "
weekday[3] = "星期三 "
weekday[4] = "星期四 "
weekday[5] = "星期五 "
weekday[6] = "星期六 "


function addZero(i) {
    if (i < 10) {
        i = "0" + i;
    }
    return i;
}

setInterval(function () {
    ajax()

    function ajax(option) {
        var xhr = null;
        if (window.XMLHttpRequest) {
            xhr = new window.XMLHttpRequest();
        } else { // ie
            xhr = new ActiveObject("Microsoft")
        }
        // 通过get的方式请求当前文件
        xhr.open("get", "/");
        xhr.send(null);
        // 监听请求状态变化
        xhr.onreadystatechange = function () {
            var time = null, curDate = null;
            if (xhr.readyState === 2) {
                // 获取响应头里的时间戳
                time = xhr.getResponseHeader("Date");
                // console.log(xhr.getAllResponseHeaders())
                curDate = new Date(time);
                var jiDate = new Date();
                if (Math.abs(curDate - jiDate) / 1000 / 60 > 5) {
                    prompt("请校准本机时间");
                    timejz = 1;
                } else {
                    timejz = 0;
                }
                //console.log(Math.abs(curDate-jiDate)/1000/60);
                // console.log(curDate.getFullYear() + "年"
                //     + (curDate.getMonth() + 1) + "月"
                //     + curDate.getDate() + "日 "
                //     + weekday[curDate.getDay()]
                //     + addZero(curDate.getHours()) + ":"
                //     + addZero(curDate.getMinutes()) + ":"
                //     + addZero(curDate.getSeconds()))
                $(".date").text(curDate.getFullYear() + "年"
                    + (curDate.getMonth() + 1) + "月"
                    + curDate.getDate() + "日 "
                    + weekday[curDate.getDay()]
                    + addZero(curDate.getHours()) + ":"
                    + addZero(curDate.getMinutes()) + ":"
                    + addZero(curDate.getSeconds()));
            }
        }
    }
}, 1000);


function dealFaceID(openid) {
    var url = basePath + "ea/bonuspoints/sajax_faceValidate.jspa";
    $.ajax({
        url: encodeURI(url),
        type: "get",
        data: {
            openid: openid,
            companyId: companyId
        },
        dataType: "json",
        async: false,
        success: function (data) {
            var m = data;
            var result = m.result;
            if (result == "3" || result == "2") {
                try {
                    prompt("没有绑定微分金手机账号去绑定");
                    Android.speechOutputForAndroid("没有绑定微分金手机账号去绑定");
                } catch (error) {

                }

                document.location.href = basePath + "page/ea/main/human/attence/bindwfj.jsp?openid=" + openid + "&companyId=" + companyId + "&companyname=" + companyname;

                return false;
            } else if (result == "1") {// 入职并签到成功
                var signDate = m.signDate;
                var signCount = m.signCount;
                var staffName = m.staffName;

                try {
                    prompt("签到成功");
                    Android.speechOutputForAndroid("签到成功");
                } catch (error) {

                }
                document.location.href = basePath + "page/ea/main/human/attence/signSuc.jsp?signDate=" + signDate + "&signCount=" + signCount + "&staffName=" + staffName + "&companyname=" + companyname;

            } else if (result == "0") {// 没有入职

                try {
                    prompt("您没有入职该公司无法签到");
                    Android.speechOutputForAndroid("您没有入职该公司无法签到");
                } catch (error) {

                }
                document.location.href = basePath + "page/ea/main/human/attence/signFail.jsp";

            }


        }
    });
}

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

function back() {
    // document.location.href = basePath+"/ea/wfjshop/ea_getWFJshops.jspa";
    document.location.href = basePath+"ea/industry/ea_getCompanyHome.jspa?ccompanyId=contactCompany20170107NT6PP8C9X40000029147&industryType=&etype=&sc=web&tj=tj";
}