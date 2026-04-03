$(document).ready(function () {
    try {
        //获取当前位置，经纬度
        getLocation();
    } catch (e) {
        console.log(e.name + ": " + e.message);
    }

    $("header").css("height", $(window).height() * 0.08 - 1 + "px");
    $("header menu li").css("height", $(window).height() * 0.08 - 1 + "px");
    $("header menu li").css("line-height", $(window).height() * 0.08 - 1 + "px");
    $("header").hide();
    //列表高度计算
    var menu_hei = $("header").outerHeight() + $(".content>section:first-of-type").outerHeight();
    $(".content menu").css("height", $(window).height() - menu_hei + "px");
    //滚动兼容
    var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
    $(".content>menu").scroll(function () {
        var scroll_height = $(".content>menu li").outerHeight(true) * ($(".content>menu li").length - 1) - $(this).outerHeight(true);//计算可滚动距离0代表提前0个加载
        if ($(this).scrollTop() >= scroll_height) {
            if (pagenumber < pagecount) {
                t = setTimeout(function () {
                    if (pagenumber < pagecount) {
                        supermarketList();
                    }
                }, 1000);
            }
        }
    })

    //调用方法ajax获取超市相关数据追加展示
    supermarketList();
});
function supermarketList() {
    clearTimeout(t);
    pagenumber++;
    //ajax获取超市相关数据追加展示
    var url = basePath+"ea/smg/sajax_sm_getSupermarketList.jspa";
    //获取搜索的超市名称
    var supermarketName = $("#supermarketName").val();
    $.ajax({
        url: encodeURI(url),
        type: "POST",
        async: true,
        data: {
            "pageForm.pageNumber": pagenumber,
            "industryType": "scode20190415raqvqk3uvs0000000762",//所有超市的行业类型id
            "companyName": supermarketName,
            "longitude": longitude,
            "latitude": latitude,
        },
        dataType: "json",
        success: function (data) {
            //$("#supermarketBox").empty();
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            console.log(pageForm)
            if (pageForm != null && pageForm.recordCount > 0) {
                var supList = pageForm.list;
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
                var sup = new Array();
                for (var i in supList) {
                    sup.push('<li>');
                    sup.push('<a  class="txt clearfix" style="display: block;" href="'+basePath+'ea/smg/sm_toSupermarketGoods.jspa?ccompanyID=' + supList[i][0] + '&industryType=' + supList[i][4] + '&companyName=' + supList[i][1] + '&posNum='+posNum+'">');

                    sup.push('<div>');
                     //sup.push('<a href="'+basePath+'ea/smg/sm_toSupermarketGoods.jspa?ccompanyID='
                     //    + supList[i][0] + '&industryType=' + supList[i][4] + '&companyName=' + supList[i][1] + '" class="txt">');
                    if(supList[i][3]==null||supList[i][3]==""){
                        sup.push('<img src="'+basePath+'/images/ea/production/forum/reportAnError.png" alt="" />');
                    }else{
                        sup.push('<img src="'+basePath+'' + supList[i][3] + '" alt="" />');

                    }
                    //sup.push('</a>');
                    sup.push('</div>');
                    sup.push('<section>');
                    sup.push('<h2>');
                    sup.push(supList[i][1]);
                    sup.push('</h2>');
                    if (supList[i][2] != null && supList[i][2] != undefined && supList[i][2] != "") {
                        sup.push('<p class="txt"><img src="'+basePath+'images/unmannedsupermarket/img_1_17.png"/>' + supList[i][2] + '</p>');
                    }
                    sup.push('</section>');
                    if (member.code == '200') {
                        var lengths = supList[i][5] + "".length
                        if (lengths > 1) {
                            sup.push('<span>' + parseFloat(supList[i][5]).toFixed(1) + 'km </span>');
                        } else if (lengths == 1) {
                            sup.push('<span>' + supList[i][5] + 'km </span>');
                        }
                    }
                    sup.push('</a>');
                    sup.push('</li>');

                }
                $("#supermarketBox").append(sup.join(""));
            } else {
                console.log("没有查询到超市！")
            }
        },
        error: function (data) {
            alert("数据获取失败！");
        }

    });
}

//超市搜素
function supermarketSearch() {
    //清空元素中内容
    $("#supermarketBox").empty();
    pagenumber = 0;
    //调用方法ajax获取超市相关数据追加展示
    supermarketList();
}
//获取定位
function getLocation() {
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    if (isAndroid == true) {
        console.log("安卓");
        var collection = Android.callgetAddrstr();//调用安卓接口
        if (collection != "-1") {
            var ar = collection.split(",");
            //address = ar[0];//当前位置
            longitude = ar[1];//经度
            latitude = ar[2];//纬度
            console.log("当前位置,经纬度"+collection);
           // $("#getLocation").text(address.substring(address.indexOf("市")+1));
        } else {
            console.log("经纬度获取失败!")
        }
    } else if (isiOS == true) {
        console.log("IOS");
        var url = "func=" + 'iosMapPoint';
        window.webkit.messageHandlers.Native.postMessage(url);
    }
}

function iosMapPoint(name) {
    if (name != "-1") {
        var ar = name.split(",");
        //address = ar[0];//当前位置
        longitude = ar[1];//经度
        latitude = ar[2];//纬度
        console.log("当前位置,经纬度"+collection);
        //$("#getLocation").text(address.substring(address.indexOf("市")+1));
    } else {
        console.log("经纬度获取失败!");
    }
}