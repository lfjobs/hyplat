$(document).ready(function() {

    //获取经纬度,城市名称
   // obtain();

    ajax();
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
    var url = basePath + "/ea/mappointment/sajax_ea_parkingRecord.jspa";
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
                            test.push("<div class='park_list_box last' data-carmid='"+this[0]+"'>");
                        } else {
                            test.push("<div class='park_list_box' data-carmid='"+this[0]+"'>");
                        }
                        test.push("<div class='park_reclist'>");
                        test.push("<div class='rec_name'>"+this[1]+"</div>");
                        test.push("<div class='rec_info'>");
                        test.push("<div class='rec_h'>");
                        test.push("<i class='rec_ru'>入</i>");
                        test.push("<span>"+this[3]+"</span>");
                        test.push("</div>");
                        test.push("<div class='rec_h'>");
                        test.push("<i class='rec_chu'>出</i>");
                        test.push("<span>"+this[4]+"</span>");
                        test.push("</div>");
                        test.push("<div class='rec_cost'>停车收费：<span>￥"+this[5]+" </span>");
                        test.push("</div></div>");
                        test.push("</div></div>");
                    })
                }

                $(".park_rec_con").append(test.join(""));
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