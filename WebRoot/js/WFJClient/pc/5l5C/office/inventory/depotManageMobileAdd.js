var accuracy = "";//东经
var dimension = "";//北纬
var city = "";//所在城市


function initData() {
    var belongStr = "";
    for (var i = 0; i < dataList.length; i++) {
        var codeValue = dataList[i].codeValue;
        belongStr += '<li onClick="selectBelong(' + i + ');" value="'+dataList[i].codeID+'">' + codeValue + '</li>';
        if($("#itemID").val()==dataList[i].codeID){
            $("#itemID_a dd").html(dataList[i].codeValue);
        }
    }
    $("#belongList").html(belongStr);
    $("#belongBox").scrollTop(0);

}

function selectBelong(i){
    clockBelong();
    $("#itemID_a dl dd").html(dataList[i].codeValue);
    $("#itemID").val(dataList[i].codeID);
    if (dataList[i].codeID=="scode20241026gvvqfp64fk0000000004"){
        $("#depotCoding").val("自动生成");
        $("#depotCoding").attr("disabled","disabled");
    }
}

/*保存库房信息*/
function manageSave() {
    $("select#depotType").removeAttr("disabled");
    if ($("form .error").length) {
        return;
    }
    if ($("#depotType").val()!="scode20241026gvvqfp64fk0000000004"&&$("input.depotCoding").val() == '') {
        remind_div("仓库编码不能为空");
        $("input.depotCoding").css("background-color", "red");
        return;
    }

    if ($("input.depotName").val() == '') {
        remind_div("仓库名称不能为空");
        $("input.depotName").css("background-color", "red");
        return;
    }

    //验证名称是否重复
    $.ajax({
        url:encodeURI(basePath+"ea/depotmanage/sajax_mobile_getCountByName.jspa"),
        type: "get",
        async: true,
        dataType: "json",
        data: {
            "date":new Date().toLocaleString(),
            "name":$("input.depotName").val()
        },
        success:function (data) {
            var member = eval("("+data+")");
            var nologin = member.nologin;
            if(nologin){
                document.location.href =basePath+"page/WFJClient/pc/pc_login.jsp";
            }
            var count = member.count;
            if(count>0) {
                remind_div("仓库名称不可重复");
                $("input.depotName").css("background-color", "red");
                return;
            }
        },
        error:function (data) {
            remind_div(data);
        }
    });

    $.ajax({
        url:encodeURI(basePath+"ea/depotmanage/sajax_mobile_getListDepotmanageByPID.jspa?depotID="+typePID+"&date="+new Date().toLocaleString()),
        type: "get",
        async: true,
        dataType: "json",
        success:function (data) {
            var member = eval("("+data+")");
            var nologin = member.nologin;
            if(nologin){
                document.location.href =basePath+"page/WFJClient/pc/pc_login.jsp";
            }
            var depotManagelist = member.depotManagelist;
            for(var i=0;i<depotManagelist.length;i++) {
                if(depotManagelist[i].depotCoding==$("#depotCoding").val()) {
                    remind_div("同级别仓库编码不能重复");
                    $("input.depotCoding").css("background-color", "red");
                    return;
                }
            }
        }
    });

    $.ajax({
        url: encodeURI(basePath+"ea/depotmanage/sajax_mobile_saveDepotByAjax.jspa?"),
        type: "get",
        async: true,
        dataType: "json",
        data:$("#form_depot").serialize(),
        success:function (data) {
            var falg=eval("(" + data + ")").falg;
            console.log(falg);
            if (falg=="200"){
                document.location.href = basePath + "/page/WFJClient/pc/5l5C/office/inventory/depotManageMobile.jsp";
            }
        },
        error:function (data) {
            remind_div(data);
        }
    });
}

/*关闭项目归属选项*/
function clockBelong() {
    $("#belongMask").fadeOut();
    $("#typeBelongLayer").animate({"bottom": "-100%"});
    $("#projectTypeLayer").animate({"bottom": "-100%"});
    initData();
}

/**
 * 提示框
 * @param data 提示信息
 */
function remind_div(data) {
    $(".div-tingyong .titlep").text(data);
    $(".div-tingyong").show();
}

/*关闭提示框*/
function close_div() {
    $(".div-tingyong .titlep").text("");
    $(".div-tingyong").hide();
}

//获取定位
function getLocation() {
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    try{
        if (isAndroid == true) {
            var collection = Android.callgetAddrstr();//调用安卓接口
            if (collection != "-1") {
                var ar = collection.split(",");
                $(".depotAddress").text(ar[0]);//当前位置
                $(".dwLnglatX").text(ar[1]);//经度
                $(".dwLnglatY").text(ar[2]);//纬度
            } else {
                $(".depotAddress").text("经纬度获取失败!");
            }
        } else if (isiOS == true) {
            var url = "func=" + 'iosMapPoint';
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    }catch(error){
        getCurrentPosition();
    }
}

//调用浏览器定位服务
function getCurrentPosition() {
    // 百度地图API功能
    var map = new BMap.Map("allmap");
    var point = new BMap.Point(116.331398, 39.897445);
    map.centerAndZoom(point, 12);

    var geolocation = new BMap.Geolocation();
    geolocation.getCurrentPosition(function (r) {
        if (this.getStatus() === BMAP_STATUS_SUCCESS) {
            var mk = new BMap.Marker(r.point);
            map.addOverlay(mk);
            map.panTo(r.point);
            $(".depotAddress").val(r.address.city+r.address.district+r.address.province+r.address.street+r.address.street_number);//当前位置
            $(".dwLnglatX").text(r.point.lng);//经度
            $(".dwLnglatY").text(r.point.lat);//纬度
        } else {
            alert('failed' + this.getStatus());
        }
    }, function (positionError) {
        console.log("定位失败")
        console.log(positionError)
    }, {enableHighaccuracy: true})
}


$(function() {
    var url ="ea/depotmanage/sajax_mobile_getTypelist.jspa?";
    $.ajax({
        url: encodeURI(basePath+url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            dataList = eval("(" + data + ")").typelist;
            initData();
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });

    switch (depotType){
        case undefined :
            $("#TypeList li").attr("disabled","false");
            break;
        case '2' :
            $("#TypeList li[value='1']").remove();
            $("#TypeList li[value='2']").remove();
            break;
        case '3' :
            $("#depotType_a dd").html("展位");
            $("input#depotType").val("4");
            $("#TypeList li[value='1']").remove();
            $("#TypeList li[value='2']").remove();
            $("#TypeList li[value='3']").remove();
            break;
        case '4' :
            //alert("展位没有下级，请重新选择！");
            //return;
            break;
    }

    getLocation();

    $("#TypeList li").each(function () {
        if ($(this).val()==$("#depotType").val()){
            $("#depotType_a dd").html($(this).text());
        }
    });

    /*打开项目归属选项*/
    $("#itemID_a").click(function() {
        $("#belongMask").fadeIn();
        $("#typeBelongLayer").animate({"bottom": 0});
    });

    $("#depotType_a").click(function() {
        $("#belongMask").fadeIn();
        $("#projectTypeLayer").animate({"bottom": 0});
    });

    $("#TypeList li").on("click", function(){
        clockBelong();
        $("#depotType_a dl dd").html($(this).text());
        $("#depotType").val($(this).attr("value"));
    });

    /*关闭项目归属选项*/
    $("#belongMask, #closeBelong,#closeType").click(function() {
        clockBelong();
    });
});