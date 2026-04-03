var isWeixin = "";
$(document).ready(function() {

    var ua = navigator.userAgent.toLowerCase();
     isWeixin = ua.indexOf('micromessenger') != -1;
    if (!isWeixin) { //不是微信
        $(".ver_wrap").show();

    }else{
        $(".ver_wrap").hide();
    }


//获取经纬度,城市名称
    obtain();

    ajax();
    //实时监听
   $('.carnum_inp').bind('input propertychange', function() {
        var s = $(this).val();
        if(s.length==7){
            var url = basePath +"/ea/mappointment/sajax_ea_validation.jspa?";
            $.ajax({
                url : encodeURI(url),
                type : "post",
                data : {
                    "carInformation.carNum" : s
                },
                dataType : "json",
                async : false,
                success : function(data) {
                    var member = eval("(" + data + ")");
                    bol = member.bl;
                }
            });
        }
    });
})



function getHeight() {
    t = setTimeout("getHeight()", 200);
    if ($(".last").length > 0) {
        if ($(".last").offset().top - $(".last").height() <= $(window)
                .height()) {
            if (pageNumber < pageCount) {
                ajax();
            }
        }
    }
}


function ajax() {
    var url = basePath + "/ea/mappointment/sajax_ea_HistoryBindingVehicle.jspa";
    $.ajax({
        url : encodeURI(url),
        type : "post",
        data : {
            "pageForm.pageNumber" : pageNumber+1,
            "pageForm.pageSize":pageSize,
        },
        dataType : "json",
        async : false,
        success : function(data) {
            var jsonresult = eval("(" + data + ")");
            var pageForm = jsonresult.pageForm;
            var test = [];
            $(".last").removeClass("last");
            if (pageForm != null && pageForm.list != null && pageForm.list.length > 0) {
                $(pageForm.list).each(function(i, dom) {
                    if ($(pageForm.list).length - 1 == i) {
                        test.push("<div class='bind_rec_box last'>");
                    } else {
                        test.push("<div class='bind_rec_box'>");
                    }
                    test.push("<div class='bind_num'>"+this[0]+"</div>");
                    test.push("<div class='bind_time clearfix'>");
                    test.push("<span>绑定时间："+this[1]+"</span>");
                    test.push("<span>解绑时间："+this[2]+"</span>");
                    test.push("</div></div>");
                })
            }

            $(".bind_rec_wrap").append(test.join(""));
            if (pageForm != null) {
                pageNumber = pageForm.pageNumber;
                pageCount = pageForm.pageCount;
                if (pageNumber < pageCount) {
                    getHeight();
                }
            }
        }
    })
}

function determine(){
    var carNum = $(".carnum_inp").val();
    var engineNum = $(".enginenum_inp").val();
    var n = $(".ver_inp").val();
    if ($.trim(carNum)!="" && carNum.length==7){
        if (bol){

            if (!isWeixin) { //不是微信
                if(n==i){
                    $("#bindForm").attr("target","hidden").attr("action",basePath+"/ea/mappointment/ea_addVehicle.jspa?message=11");
                    document.bindForm.submit.click();
                    token = 13;
                }else{
                    alert("验证码不正确")
                }

            }else{
                $("#bindForm").attr("target","hidden").attr("action",basePath+"/ea/mappointment/ea_addVehicle.jspa?message=11");
                document.bindForm.submit.click();
                token = 13;
            }

        }else{
            alert("该车牌已被绑定")
        }
    }else{
        alert("请填写正确的车牌号")
    }
}


function obtain(){
    try {
        var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        if (isAndroid == true) {
            console.log("安卓");
            var collection = Android.callgetLocal();//调用安卓接口
            if (collection != "-1") {
                var a = collection.split(",");
                city = a[0];//所在城市
                eastLongitude = a[1];//东经
                northLatitude = a[2];//北纬
                $(".park_area").text(city);
            } else {
                $(".park_area").text("未知");
            }
        } else if (isiOS == true) {
            console.log("IOS");
            var url = "func=" + 'calliosMapInfo';
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    }catch(error){
       console.log("网页");
    }
}

function calliosMapInfo(name){
    if(name!="-1"){
        var a = name.split(",");
        city = a[0];//所在城市
        eastLongitude = a[1];//东经
        northLatitude = a[2];//北纬
        $(".park_area").text(city);
    }else{
        $(".park_area").text("未知");
    }
}


function  re_load() {
    if(token){
        document.location.href = basePath+"ea/mappointment/ea_viewVehicle.jspa";
    }
}