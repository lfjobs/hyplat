$(document).ready(function() {
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    //获取经纬度,城市名称
    /*obtain(isAndroid,isiOS);*/

    ajax();
    //实时监听
    $('.search').bind('input propertychange', function() {
        pageNumber = 0;
        $(".coach_list").empty();
        ajax();
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
    var conditions = $(".search").val();
    var url = basePath + "/ea/mappointment/sajax_ea_ajaxDrivingList.jspa";
    $.ajax({
        url : encodeURI(url),
        type : "post",
        data : {
            "conditions":conditions,
            "pageForm.pageNumber" : pageNumber+1,
            "pageForm.pageSize":pageSize,
            "concom.companyAddr":city
        },
        dataType : "json",
        async : false,
        success : function(data) {
            var jsonresult = eval("(" + data + ")");
            var pageForm = jsonresult.pageForm;
            var test = [];
            $(".last").removeClass("last");
            if (pageForm != null && pageForm.list != null && pageForm.list.length > 0) {
                pageCount=pageForm.pageCount;
                pageNumber=pageForm.pageNumber;
                $(pageForm.list).each(function(i, dom) {
                    if ($(pageForm.list).length - 1 == i) {
                        test.push("<li class='last' onclick='jump(this)' data-ppid='"+this[0]+"'>");
                    } else {
                        test.push("<li onclick='jump(this)' data-ppid='"+this[0]+"'>");
                    }
                    test.push("<img src='"+basePath+this[1]+"' class='left' onerror=\"this.src=\'' + basePath + '/images/ea/production/forum/reportAnError.png\'\">");
                    test.push("<div class='text'>");
                    test.push("<h4>"+this[2]+"</h4>");
                    test.push("<p></p>");
                    test.push("<p class='tel'>");
                    if(this[3]!=null){
                        test.push("<img src='"+basePath+"images/ea/office/ico-locat.png'>"+this[3]+"</p>");
                    }else {
                        test.push("<img src='"+basePath+"images/ea/office/ico-locat.png'>未知</p>");
                    }
                    test.push("</div>");
                    test.push("<img src='"+basePath+"images/ea/office/ico-gou.png' class='gou'>");
                    test.push("</li>");
                })
            }
            $(".coach_list").append(test.join(""));

            /*
             if (pageForm != null) {
             pageNumber = pageForm.pageNumber;
             pageCount = pageForm.pageCount;
             if (pageNumber < pageCount) {*/
            getHeight();
            /*     }
             }*/
        }
    })
}



function obtain(isAndroid,isiOS){
    try {
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
    }catch (e){

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

function jump(obj) {
    document.location.href = basePath+"/ea/mappointment/ea_jumpToBuy.jspa?companyId="+$(obj).attr("data-ppid")+"&type=00";
}
