$(document).ready(function() {
    //获取经纬度,城市名称
   // obtain();

    ajax();
    //解除绑定
    $(".popup_yes").click(function(){
        $("#ubindForm").attr("target","hidden").attr("action",basePath+"/ea/mappointment/ea_delVehicle.jspa?message=11");
        document.ubindForm.submit.click();
        token = 13;
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

function obtain(){
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    if (isAndroid == true) {
        console.log("安卓");
        var collection = Android.callgetLocal();//调用安卓接口
        if(collection!="-1"){
            var a = collection.split(",");
            city = a[0];//所在城市
            eastLongitude = a[1];//东经
            northLatitude = a[2];//北纬
            $(".park_area").text(city);
        }else{
            $(".park_area").text("未知");
        }
    } else if (isiOS == true) {
        console.log("IOS");
        var url= "func=" + 'calliosMapInfo';
        window.webkit.messageHandlers.Native.postMessage(url);
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

 //新增车辆
function newBind(){

    document.location.href = basePath+"ea/mappointment/ea_newVehicle.jspa";

}
function unbind(carID){
 $("#ubindForm #carID").val(carID);

}

function  re_load() {
    if(token){
        document.location.href = basePath+"ea/mappointment/ea_viewVehicle.jspa";
    }
}