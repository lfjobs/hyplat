var key = "c3fee0fda57aca810041e172ae85503b";
var page = 0;
var type = "";
var t = "";
var tt = "";
var head = "";

$(function () {
    if (dwLnglatX === "") {
        getLocation();
    } else {
        console.log("2")
        regeocode();
        getNewsList();
        getPOIs(cate);
    }

    $("#search_button").on("click", function () {
        getPOIs(type);
        $("#clear_button").show()
    });

    $("#clear_button").on("click", function () {
        $("#clear_button").hide();
        $("#keywords").val('');
        getPOIs(type);
    });

    //更多
    $(".morre").click(function () {
        document.location.href = basePath + "page/scMobile/qyrz/cate.jsp?dwLnglatX=" + dwLnglatX + "&dwLnglatY=" + dwLnglatY + "&head=" + head;
    });

    //查看
    $(document).on("click", "#ttsw_zbsj_list li", function () {
        var gdID = $(this).attr("id");
        var allphoto = $(this).find(".allphoto").val();
        var photoys = $(this).find(".photoys").val();
        var name = $(this).find(".name").text();
        var site = $(this).find(".site").text();
        var distance = $(this).find(".distance").text();
        var tel = $(this).find(".tel").val();
        var location = $(this).find(".location").val();
        var jd = location.split(",")[0];
        var wd = location.split(",")[1];
        var pname = $(this).find(".pname").val();
        var cityname = $(this).find(".cityname").val();
        var adname = $(this).find(".adname").val();
        var address = $(this).find(".address").val();
        var type = $(this).find(".type").val();
        var typecode = $(this).find(".typecode").val();
        console.log("jd" + jd);
        console.log("wd" + wd);
        var url = basePath + "/ea/qyrz/sajax_ea_toDetail.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "GET",
            async: false,
            dataType: "json",
            data: {
                gdID: gdID
            },
            success: function (data) {
                var m = eval("(" + data + ")");
                var result = m.result;
                if (result == "0") {
                    document.location.href = basePath + "page/scMobile/qyrz/detail.jsp?head=show&gdID=" + gdID + "&allphoto=" + allphoto + "&name=" + name + "&site=" + site + "&distance=" + distance + "&tel=" + tel + "&dwLnglatX=" + dwLnglatX + "&dwLnglatY=" + dwLnglatY + "&head=" + head + "&jd=" + jd + "&wd=" + wd + "&photoys=" + photoys + "&cityname=" + cityname + "&pname=" + pname + "&adname=" + adname + "&address=" + address + "&location=" + location + "&type=" + type + "&typecode=" + typecode;
                } else {
                    document.location.href = basePath + "ea/industry/ea_getCompanyHome.jspa?ccompanyId=" + result + "&industryType=&etype=&sc=web";
                }
            },
            error: function (data) {
                console.log("验证失败");
            }
        })
    });
});

// function regeocode() {
//     //   var location = "116.40387397,39.91488908"
//     var location = dwLnglatX + "," + dwLnglatY;//经纬度
//     var url = "https://restapi.amap.com/v3/geocode/regeo?key="+key+"&location=" + location;
//     $.ajax({
//         url: url,
//         type: "GET",
//         async: true,
//         dataType: "json",
//         success: function (data) {
//             console.log(data)
//             var addressComponent = data.regeocode.addressComponent;
//             var streetNumber = data.regeocode.addressComponent.streetNumber;
//             currentRegion = addressComponent.city;
//             document.getElementById("currentLocation").innerText = `${addressComponent.city}${addressComponent.district}${addressComponent.township}${streetNumber.street}${streetNumber.number}`;
//         }
//     });
// }
function regeocode() {
    // 检查经纬度是否有效
    if (!dwLnglatX || !dwLnglatY) {
        console.error("经纬度未定义");
        return;
    }

    var location = dwLnglatX + "," + dwLnglatY; // 经纬度

    // 构建API请求URL
    var url = "https://restapi.amap.com/v3/geocode/regeo?key=" + key + "&location=" + encodeURIComponent(location);

    // 发送GET请求获取逆地理编码数据
    $.ajax({
        url: url,
        type: "GET",
        async: true,
        dataType: "json",
        success: function (data) {
            if (data.status === '1' && data.info === 'OK') {
                var addressComponent = data.regeocode.addressComponent;
                var streetNumber = addressComponent.streetNumber || {};
                var formattedAddress = [
                    addressComponent.city || '',
                    addressComponent.district || '',
                    addressComponent.township || '',
                    streetNumber.street || '',
                    streetNumber.number || ''
                ].filter(Boolean).join(''); // 过滤掉空字符串并连接

                currentRegion = addressComponent.city;

                // 更新页面中的文本内容
                document.getElementById("currentLocation").innerText = formattedAddress || '无法获取详细地址';
            } else {
                console.error("逆地理编码失败:", data);
                document.getElementById("currentLocation").innerText = '无法获取详细地址';
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("请求逆地理编码数据失败:", textStatus, errorThrown);
            document.getElementById("currentLocation").innerText = '网络错误，请重试';
        }
    });
}
function getHeight() {

    if ($(".last").length > 0) {
        if ($(".last").offset().top - $(document).scrollTop() <= $(window).height()) {

            getPOI(type);
        }
    }
}

function getPOIs(types) {
    clearInterval(t);
    $("#ttsw_zbsj_list").html("");
    page = 0;
    type = types;
    getPOI(types);
}

function getPOI1(types) {
    var keywords = $.trim($("#keywords").val());
    if (keywords != "") {
        types = "";
    }
    page++;
    //   var location = "116.40387397,39.91488908"
    var location = dwLnglatX + "," + dwLnglatY;//经纬度

    var url = ""
    if (keywords == "") {
        url = "https://restapi.amap.com/v3/place/around?key=" + key + "&location=" + location + "&radius=10000&types=" + types + "&offset=20&page=" + page;
    } else {
        url = "https://restapi.amap.com/v5/place/text?key=" + key + "&keywords=" + keywords + "&offset=20&page=" + page;
    }
    $.ajax({
        url: encodeURI(url),
        type: "GET",
        async: true,
        dataType: "json",
        success: function (data) {
            var pois = data.pois;
            var innerHtml = new Array();

            if (pois == null) {
                return false;
            }
            $(".last").removeClass("last");
            //var url = basePath+"/ea/qyrz/sajax_ea_validateCom.jspa";
            var ids = new Array();

            for (var i = 0; i < pois.length; i++) {
                ids.push(pois[i].id);
            }

            $.ajax({
                url: basePath + "/ea/qyrz/sajax_ea_validateComBatch.jspa",
                type: "POST",
                data: {
                    "gdIds": ids,
                    "claim": $(".active").attr('name')
                },
                success: function (data) {
                    for (var k = 0; k < data.length; k++) {
                        for (var i = 0; i < pois.length; i++) {
                            if (pois[i] != undefined && data[k] == pois[i].id) {
                                var photos = pois[i].photos;
                                var photo = "";
                                var photoys = "";
                                var allphoto = "";
                                var id = pois[i].id;

                                if ($("#ttsw_zbsj_list li#" + id).length > 0) {
                                    clearInterval(t);
                                    tt = "clear";
                                    break;
                                }

                                if (photos != null && photos.length > 0) {
                                    photo = photos[0].url;
                                    photoys = photo;
                                    for (var j = 0; j < photos.length; j++) {

                                        if (j == photos.length - 1) {
                                            allphoto += photos[j].url;
                                        } else {
                                            allphoto += photos[j].url + ",";
                                        }
                                    }
                                } else {
                                    photo = basePath + "images/ea/production/forum/reportAnError.png";
                                }
                                //展示
                                var pname = pois[i].pname;
                                var cityname = pois[i].cityname;
                                var adname = pois[i].adname;
                                var address = pois[i].address;
                                var tel = pois[i].tel;
                                var distance = pois[i].distance;
                                var location = pois[i].location;
                                var type = pois[i].type;
                                var typecode = pois[i].typecode;
                                console.log(type);
                                console.log(typecode);
                                if (pname == cityname) {
                                    pname = "";
                                }
                                var site = adname + address;
                                var claimText = $(".active").attr('name') === "claim" ? "已认领" : "请认领"
                                if (i == pois.length - 1) {
                                    innerHtml.push(`<li class="clearfix last" id="${pois[i].id}">
                                                        <div class="div-img">
                                                        </div>
                                                        <div class="div-txt">
                                                            <p class="name">${pois[i].name}</p>
                                                        </div>
                                                        <div class="div-rl">
                                                        
                                                        </div>
                                                    </li>
                                                `);
                                } else {
                                    innerHtml.push(`<li class="clearfix" id="${pois[i].id}" >
                                                        <div class="div-img">
                                                       
                                                        </div>
                                                        <div class="div-txt">
                                                                <!-- 公司名称-->
                                                            <p class="name">${pois[i].name}</p>
                                                       
                                                        </div>
                                                        <div class="div-rl">
                                                        
                                                        </div>
                                                    </li>
                                                    `);
                                }
                                continue;
                            }
                        }
                    }
                    $("#ttsw_zbsj_list").append(innerHtml.join(""));
                    if (tt === "") {
                        t = setInterval("getHeight()", 500);
                    }
                }
            });
        }
    });
}
function getPOI(types) {
    var keywords = $.trim($("#keywords").val());
    if (keywords != "") {
        types = "";
    }
    page++;
    // 经纬度
    var location = dwLnglatX + "," + dwLnglatY;

    var url = "";
    if (keywords == "") {
        url = "https://restapi.amap.com/v3/place/around?key=" + key + "&location=" + location + "&radius=10000&types=" + types + "&offset=20&page=" + page;
    } else {
        url = "https://restapi.amap.com/v5/place/text?key=" + key + "&keywords=" + keywords + "&offset=20&page=" + page;
    }

    $.ajax({
        url: encodeURI(url),
        type: "GET",
        async: true,
        dataType: "json",
        success: function (data) {
            var pois = data.pois || [];
            var innerHtml = [];
            var selectListItems = [];

            if (pois.length === 0) {
                alert("没有找到匹配的兴趣点");
                return;
            }

            $(".last").removeClass("last");
            var ids = [];

            for (var i = 0; i < pois.length; i++) {
                ids.push(pois[i].id);

                var photos = pois[i].photos || [];
                var photo = photos.length > 0 ? photos[0].url : basePath + "images/ea/production/forum/reportAnError.png";
                var allphoto = photos.map(function (p) { return p.url }).join(",");
                var pname = pois[i].pname || "";
                var cityname = pois[i].cityname || "";
                var adname = pois[i].adname || "";
                var address = pois[i].address || "";
                var tel = pois[i].tel || "";
                var distance = pois[i].distance || "";
                var location = pois[i].location || "";
                var type = pois[i].type || "";
                var typecode = pois[i].typecode || "";

                if (pname === cityname) {
                    pname = "";
                }

                var site = adname + address;
                var claimText = $(".active").attr('name') === "claim" ? "已认领" : "请认领";

                // 构建ttsw_zbsj_list中的<li>元素
                innerHtml.push(`
                    <li class="clearfix ${i === pois.length - 1 ? 'last' : ''}" id="${pois[i].id}">
                        <div class="div-img">
                            <img src="${photo}" alt="Photo of ${pois[i].name}">
                        </div>
                        <div class="div-txt">
                            <p class="name">${pois[i].name}</p>
                        </div>
                        <div class="div-rl">
                            <span>${claimText}</span>
                        </div>
                    </li>
                `);

                // 构建选择列表中的<li>元素
                selectListItems.push(`
                    <li data-id="${pois[i].id}" data-name="${pois[i].name}" data-address="${site}">${pois[i].name}</li>
                `);
            }

            // 更新ttsw_zbsj_list的内容为最新的POI信息
            $("#ttsw_zbsj_list").html(innerHtml.join(""));

            // 清空现有的选择列表内容
            $('#selectList').empty();

            // 将构建的选择列表项添加到选择列表中
            $('#selectList').html(selectListItems.join(""));

            // 如果需要验证公司信息，发送POST请求
            if (ids.length > 0) {
                $.ajax({
                    url: basePath + "/ea/qyrz/sajax_ea_validateComBatch.jspa",
                    type: "POST",
                    data: {
                        "gdIds": ids,
                        "claim": $(".active").attr('name')
                    },
                    success: function (validationData) {
                        // 处理验证后的逻辑（如果需要）
                    }
                });
            }

            if (tt === "") {
                t = setInterval("getHeight()", 500); // 假设有getHeight函数用于调整高度
            }
            var lis = document.querySelectorAll('#selectList li');
            var keywordsInput = document.getElementById('keywords');

            // 为每个 li 添加点击事件监听器
            lis.forEach(function(li) {
                li.addEventListener('click', function() {
                    // 将被点击的 li 的文本内容设置为 input 框的值
                    keywordsInput.value = this.textContent || this.innerText;
                });
            });

        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("获取POI数据失败:", textStatus, errorThrown);
        }
    });
}





//点击认领跳转会员
function claim(id, name, photo, tel, pname, cityname, adname, address, location, type, typecode) {
    event.stopPropagation();

    var ua = navigator.userAgent.toLowerCase();
    var isWeixin = ua.indexOf('micromessenger') != -1;
    var wx = "0";
    if (isWeixin) {
        wx = "1";

    }
    var url = basePath + "/ea/qyrz/sajax_ea_validateCom.jspa";
    $.ajax({
        url: encodeURI(url),
        type: "GET",
        async: false,
        dataType: "json",
        data: {
            gdID: id,
            wx: wx
        },
        success: function (data) {
            var m = eval("(" + data + ")");
            var result = m.result;
            var login = m.login;
            if (login == "login") {
                document.location.href = basePath + "/page/WFJClient/NewLogin.jsp?loginPage=login";
                return false;
            }

            if (result != "0") {
                if (result == "1") {
                    //已被其他人认领
                    $("#btn_gwc").find("p").text("已被其他人认领");
                } else if (result == "2") {
                    //已经入驻过公司无法重复入驻
                    $("#btn_gwc").find("p").text("已经认领过公司无法重复");
                }

                if (!($("#btn_gwc").is(":animated"))) {
                    $("#btn_gwc").show();
                    setTimeout(function () {
                        $("#btn_gwc").animate({
                            opacity: "0",
                        }, 1000, function () {
                            $("#btn_gwc").css("opacity", "1");
                            $("#btn_gwc").hide();
                        })
                    }, 1000);
                }
            } else {
                var loc = location.split(",");
                var x = loc[0];
                var y = loc[1];
                tel = tel.split(";")[0];
                document.location.href = basePath + "ea/qyrz/ea_getpk.jspa?id=" + id + "&name=" + name + "&photo=" + photo + "&tel=" + tel + "&pname=" + pname + "&cityname=" + cityname + "&adname=" + adname + "&address=" + address + "&x=" + x + "&y=" + y + "&head=" + head + "&type=" + type + "&typecode=" + typecode;
            }
        },
        error: function (data) {
            console.log("验证失败");
        }
    })
}


function getNewsList() {
    var url = basePath + "/ea/qyrz/sajax_ea_getNewList.jspa?";
    $.ajax({
        url: encodeURI(url),
        type: "GET",
        async: true,
        dataType: "json",
        data: {
            dwLnglatX: dwLnglatX,
            dwLnglatY: dwLnglatY
        },
        success: function (data) {
            var m = eval("(" + data + ")");
            var newslist = m.newslist;
            var news = new Array();
            var obj = "";
            for (var i = 0; i < newslist.length; i++) {
                obj = newslist[i];
                news.push("<div class='swiper-slide'>");
                news.push("<a class='banner_img' style='background: url(" + basePath + obj[2] + ");background-repeat:no-repeat;background-position:center center;background-size: 100%;width: 100%;height:7rem;'  href='" + basePath + "ea/industry/ea_informationDetails.jspa?ppId=" + obj[5] + "&ccompanyId=&type=time&miniSystemJudge=03'>");
                news.push("</a>");
                news.push("</div>");
            }
            $("#news").html(news.join(""));
            //轮播图滚动效果
            var mySwiper = new Swiper('.swiper-container', {
                effect: 'fade',          //滑动效果
                direction: 'horizontal',
                loop: true,
                // 如果需要分页器
                pagination: {
                    el: '.swiper-pagination',
                },
                autoplay: {
                    stopOnLastSlide: true,
                },
            })
        }
    });

}

//获取定位
function getLocation() {
    var ua = window.navigator.userAgent;
    var isAndroid = ua.indexOf('Android') > -1 || ua.indexOf('Adr') > -1; //android终端
    var isiOS = !!ua.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    var isWeixin = ua.toLowerCase().indexOf('micromessenger') != -1;
    try {
        if (isAndroid === true) {
            console.log("安卓");
            var collection = Android.callgetAddrstr();//调用安卓接口
            if (collection != "-1") {
                var ar = collection.split(",");
                address = ar[0];//当前位置
                dwLnglatX = ar[1];//经度
                dwLnglatY = ar[2];//纬度
                regeocode();
                getNewsList();
                getPOI("");
            } else {
                console.log("经纬度获取失败!")
            }
        } else if (isiOS === true) {
            console.log("IOS");
            var url = "func=" + 'iosMapPoint';
            window.webkit.messageHandlers.Native.postMessage(url);
        } else {
            getBMapLocation();
        }
    } catch (error) {
        getBMapLocation();
    }
}

//需要先引用高德api
function getBMapLocation() {//调用浏览器定位服务
    $(".head").show();
    head = "show";

    // 百度地图API功能
    var map = new BMap.Map("allmap");
    var point = new BMap.Point(116.331398, 39.897445);
    map.centerAndZoom(point, 12);

    var geolocation = new BMap.Geolocation();
    geolocation.getCurrentPosition(function (r) {
        if (this.getStatus() == BMAP_STATUS_SUCCESS) {
            var mk = new BMap.Marker(r.point);
            map.addOverlay(mk);
            map.panTo(r.point);

            dwLnglatX = r.point.lng;
            dwLnglatY = r.point.lat;
            regeocode();
            getNewsList();
            getPOI("");
        } else {
            alert('failed' + this.getStatus());
        }
    }, {enableHighAccuracy: true})
}

function iosMapPoint(name) {
    if (name != "-1") {
        var ar = name.split(",");
        address = ar[0];//当前位置
        dwLnglatX = ar[1];//经度
        dwLnglatY = ar[2];//纬度
        getNewsList();
        getPOI("");
    } else {
        console.log("经纬度获取失败!");
    }
}