$(document).ready(function() {
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    //获取经纬度,城市名称
    obtain(isAndroid,isiOS);

    //查询场地list
    ajax();

    //实时监听
    $('.park_search').bind('input propertychange', function() {
        pageNumber = 0;
        $(".park_list_wrap").empty();
        ajax();
    });


    //分享注册
    $(".QR_fixed").click(function () {
        var shareurl = basePath + "/ea/mappointment/ea_parkingRegistrationSharing.jspa?";
        if(isAndroid==true){
            shareurl+="sccId="+sccId;
            Android.showShare("","停车注册",shareurl,"","");
        }else if(isiOS==true){
            var url= "func=" + 'parkingRegistration';
            params={'title':'停车注册','url':shareurl,"sccId":sccId,"Markcallback":"2"};
            for(var i in params){
                url = url + "&" + i + "=" + params[i];
            }
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    })
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
    var park_search = $(".park_search").val();
    var url = basePath + "/ea/mappointment/sajax_ea_parkingSpacesList.jspa";
    $.ajax({
            url : encodeURI(url),
            type : "post",
            data : {
                "venueInformation.eastLongitude":eastLongitude,
                "venueInformation.northLatitude":northLatitude,
                "pageForm.pageNumber" : pageNumber+1,
                "pageForm.pageSize":pageSize,
                "venueInformation.siteName":park_search
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
                            test.push("<a href='javascript:void(0)' class='park_list_box last' data-siteid='"+this[0]+"' onclick='details(this)'>");
                        } else {
                            test.push("<a href='javascript:void(0)' class='park_list_box' data-siteid='"+this[0]+"' onclick='details(this)'>");
                        }
                        test.push("<div class='park_name_box clearfix'>");
                        test.push("<img src='"+basePath+this[1]+"' class='park_logo' alt=''>");
                        test.push("<span class='park_name'>"+this[2]+"</span>");
                        test.push("</div>");
                        test.push("<div class='park_info clearfix'>");
                        test.push("<span class='park_car_num'>车位数："+this[3]+"</span>");
                        test.push("</div>");
                        test.push("<div class='park_info clearfix'>");
                        var a;
                        if(this[4]!=null){
                            a = this[4]+"km";
                        }else{
                            a = "未知";
                        }
                        test.push("<span class='park_site'>"+a+" | "+this[5]+"</span>");
                        test.push("</div>");
                        test.push("</a>");
                    })
                }

                $(".park_list_wrap").append(test.join(""));
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

//场地详情
function details(obj) {
    var siteId = $(obj).attr("data-siteid");
    document.location.href = basePath +"ea/mappointment/ea_parkingSpacesDetails.jspa?venueInformation.siteId="+siteId+"&venueInformation.eastLongitude="+eastLongitude+"&venueInformation.northLatitude="+northLatitude;
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
    }catch(error){
        gdWeblocation();

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

//百度地图
function gdWeblocation(){

    // 百度地图API功能
    var map = new BMap.Map("allmap");
    var point = new BMap.Point(116.331398,39.897445);
    map.centerAndZoom(point,12);

    var geolocation = new BMap.Geolocation();
    geolocation.getCurrentPosition(function(r){
        if(this.getStatus() == BMAP_STATUS_SUCCESS){
            var mk = new BMap.Marker(r.point);
            map.addOverlay(mk);
            map.panTo(r.point);
            eastLongitude = r.point.lng;
            northLatitude = r.point.lat;
            var pt = new BMap.Point(r.point.lng,r.point.lat);

            var geoc = new BMap.Geocoder();

            geoc.getLocation(pt, function(rs){
                var addComp = rs.addressComponents;
                var province = addComp.province;

                city = province;
                $(".park_area").text(province);
            });

        }
        else {
            alert('failed'+this.getStatus());
        }
    },{enableHighAccuracy: true})


}