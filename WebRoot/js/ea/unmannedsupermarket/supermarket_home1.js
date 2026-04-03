// $(document).ready(function () {
//     try {
//         //获取当前位置，经纬度
//         getLocation();
//     } catch (e) {
//         console.log(e.name + ": " + e.message);
//     }
//
//     // $("header").css("height", $(window).height() * 0.08 - 1 + "px");
//     // $("header menu li").css("height", $(window).height() * 0.08 - 1 + "px");
//     // $("header menu li").css("line-height", $(window).height() * 0.08 - 1 + "px");
//     // $("header").hide();
//
//     // 列表高度计算
//     var menu_hei = $("header").outerHeight() + $(".content>section:first-of-type").outerHeight();
//     $(".content .menu").css("height", $(window).height() - menu_hei + "px");
//
//     // 滚动兼容
//     $(".content .menu").on("scroll", function () {
//         var $this = $(this);
//         var scrollTop = $this.scrollTop();
//         var scroll_height = $this.find("li").outerHeight(true) * ($this.find("li").length - 1) - $this.outerHeight(true);
//
//         console.log("scrollTop:", scrollTop, "scroll_height:", scroll_height);
//
//         if (scrollTop >= scroll_height) {
//             if (pagenumber < pagecount) {
//                 t = setTimeout(function () {
//                     if (pagenumber < pagecount) {
//                         if (!isLoad) {
//                             console.log("正在加载");
//                             isLoad = true;
//                             pagenumber++;
//                             supermarketList(st);
//                         }
//                     }
//                 }, 1000);
//             }
//         }
//     });
//
//     //调用方法ajax获取超市相关数据追加展示
//     supermarketList(st);
// });

let pageNumber = 1;
let pageCount = 5; // 假设总共 5 页
let isLoading = false;
let currentType = "食品";
let sort="newest";


// 初始化加载第一页
$(function () {
    const params = new URLSearchParams(window.location.search);
    keyword = params.get("keyword");
    console.log("其他", keyword);
    if (keyword)
    {
        currentType=keyword;
    }
    ClassifyComponent.init({
        callImpl: function (item) {
            if (item.level>4){
                console.log("JSP 页面处理点击：>4", item.name);
                currentType = item.name
                hidewin()
              supermarketSearch()
            }else {
                console.log("其他", item.name);

            }

        }
    });
    $(".case dd").click(function () {
        var id = $(this).attr("id");
        console.log("当前点击的排序方式ID = " + id);

        setParameters(sort=id);
        supermarketList();
        // 这里写你后续要做的操作
    });
// 初始调用
    adjustSupermarketBox();

// 浏览器窗口大小变化时重新计算
    window.addEventListener('resize', adjustSupermarketBox);

    latitude=0.0;
    longitude=0.0;
    supermarketList(currentType, pageNumber);
    bindScroll();
// 获取元素
    const sales = document.getElementById('sales');
    // const caseBox = sales.querySelector('.case');

// 点击切换显示/隐藏
//     sales.addEventListener('click', function(e) {
//         e.stopPropagation(); // 阻止事件冒泡
//         if (caseBox.style.display === 'block') {
//             caseBox.style.display = 'none';
//         } else {
//             caseBox.style.display = 'block';
//         }
//     });
//
// // 点击页面其他地方隐藏弹窗
//     document.addEventListener('click', function() {
//         caseBox.style.display = 'none';
//     });





    /*筛选分类*/
    $("#screen").click(function () {
        search = '';
        $(".overlay_text").show();
        $(".nest_bd").find(".classify_wrap").remove();
        $.ajax({
            cache: true,
            type: "POST",
            url: basePath + "ea/dserve/sajax_ea_industryList.jspa?level=3",
            async: false,
            dataType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                var beanList = member.beanList;

                if (beanList.length > 0) {
                    var htmls = "<div class='classify_wrap'>";
                    var numl = 0;
                    for (var i = 0; i < beanList.length; i++) {
                        var a = beanList[i];
                        var codeid = a[0];
                        var ans = a[2];
                        if (ans == "2") {
                            numl = numl + 1;
                            htmls += "<div class='level_wrap'><div class='level_box'><i class='classify_ico list_ico_0" + numl + "'></i>";
                            htmls += "<div class='level_text'><div class='level_1'>" + a[3] + "<input type='hidden' class='oid' value='" + a[0] + "'/></div>";
                            htmls += "<div class='level_2'>";
                            for (var j = 0; j < beanList.length; j++) {
                                var b = beanList[j];
                                var codepid = b[1];
                                var bns = b[2];
                                if (codeid == codepid) {
                                    htmls += b[3] + "/<input type='hidden' class='tid' value='" + b[0] + "'/>";
                                }
                            }
                            htmls += "</div></div><i class='fold_ico'></i></div><div class='level_fold'></div></div>";
                        }
                    }
                    htmls += "</div>";
                    $(".nest_bd").append(htmls);
                }
            }
        });
        $(".nest_page").show();
        $(".overlay_text").hide();
    });

    $(".nest_back").click(function () {
        $(".nest_page").hide();
        packInit();
    });


    //加载三四级行业
    $(document).on("click", ".level_box", function () {
        $(".overlay_text").show();
        var pid = $(this).find(".oid").val();
        var level_fold = $(this).parent().find(".level_fold");
        $(this).parent().find(".level_3").remove();
        $.ajax({
            cache: true,
            type: "POST",
            url: basePath + "ea/dserve/sajax_ea_industryList.jspa?level=5&pid=" + pid,
            async: false,
            dataType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                var beanList = member.beanList;

                if (beanList.length > 0) {
                    var htmls = "";

                    for (var i = 0; i < beanList.length; i++) {
                        var a = beanList[i];
                        var codeid = a[0];
                        var ans = a[2];
                        if (ans == "4") {
                            var numl = 0;
                            var claval = "no_level_4";
                            htmls += "<div class='level_3 '><div class='level_3_tit '>" + a[3] + "<input type='hidden' class='tid' value='" + a[0] + "'/></div>";
                            htmls += "<div style='width: 17.2rem;' class='level_4 clearfix'>";
                            // for (var j=0;j<beanList.length;j++){
                            //     var b = beanList[j];
                            //     var codepid=b[1];
                            //     var bns=b[2];
                            //     if(bns=="5"){
                            //         if(codeid==codepid){
                            //             numl=numl+1;
                            //             htmls+="<div class='level_4_box'>"+b[3]+"<input type='hidden' class='fid' value='"+b[0]+"'/></div>";
                            //         }
                            //     }
                            // }
                            htmls += "</div>";
                            claval = "";
                            htmls += "</div>";
                        }
                    }
                    level_fold.append(htmls);
                    level_fold.find(".level_3").each(function () {
                        var level_4 = $(this).find(".level_4").html();
                        if (level_4 == "") {
                            $(this).addClass("no_level_4");
                            $(this).find(".level_4").remove();
                        }
                    });

                }
            }
        });

        $(".level_4").each(function () {
            $(this).slideUp(200);
        });

        $(this).parent().find(".level_fold").slideToggle(200)
            .end().find(".fold_ico").toggleClass("fold_up")
            .end().siblings().find(".level_fold").slideUp(200)
            .end().find(".fold_ico").removeClass("fold_up");

        $(".fold_level3").each(function () {
            $(this).removeClass("fold_up");
        });
        $(".overlay_text").hide();
    });

    $(document).on("click", ".level_3_tit", function () {
        console.log("level_3_tit")
        var L_4 = $(this).parent().find(".level_4");
        if (L_4.length) {
            $(this).find(".fold_level3").toggleClass("fold_up").parent().parent().siblings().find(".fold_level3").removeClass("fold_up");
            L_4.slideToggle(200).parent().siblings().find(".level_4").slideUp(200);
        } else {
            var t = $(this).text();
            var b = $(this).find(".tid").val();
            tradecode = $(this).text();
            pagenumber = 0;
            console.log(t+"  "+b)
            $(".con_shop ul").empty();
            setParameters(pageNumber=1,currentType=t)
            supermarketList()
            $("#ddscodeid").val(b);
            $(".nest_page").hide();
            packInit();
        }
    });

    $(document).on("click", ".level_4_box", function () {
        var a = $(this).parent().parent().find(".level_3_tit").text();
        var t = $(this).text();
        console.log(t)
        var b = $(this).find(".fid").val();
        $("#selsct_classify").val(a + ">" + t);
        $("#ddscodeid").val(b);
        $(".nest_page").hide();
        tradecode = $(this).text();
        pagenumber = 0;
        packInit();
        $(".con_shop ul").empty();
        supermarketList(t)
    });
    getLocation();
    hidewin()
});
function adjustSupermarketBox() {
    console.log("aaaa");
    const supermarketBox = document.getElementById('supermarketBox');
    // 获取顶部固定栏
    const topFixed = document.querySelector('.top-fixed');
    const topHeight = topFixed.offsetHeight; // top-fixed 高度
    supermarketBox.style.top = topHeight + 'px'; // 距离顶部
    supermarketBox.style.height = (window.innerHeight - topHeight) + 'px'; // 占满剩余高度
}
// 点击菜单时重置分页并重新加载
function resetAndLoad(type) {
    currentType = type;
    pageNumber = 1;
    $("#supermarketBox").empty();
    supermarketList(type, pageNumber);
}










// 绑定滚动监听
function bindScroll() {

    $("#supermarketBox").on("scroll", function () {
        let scrollTop = $(this).scrollTop();
        let scrollHeight = this.scrollHeight;
        let clientHeight = $(this).outerHeight();

        if (scrollTop + clientHeight >= scrollHeight - 2) { // 距底部 5px 以内
                console.log("页码"+pageNumber+"========"+"count"+pageCount)
            if (pageNumber < pageCount && !isLoading) {
                pageNumber=pageNumber+1;
                console.log("scroll触发了底部了"+pageNumber);

                supermarketList(currentType);
            }
        }
    });
}
function packInit() {
    $(".level_fold").each(function () {
        $(this).slideUp(200);
    });
    $(".level_4").each(function () {
        $(this).slideUp(200);
    });
    $(".fold_ico,.fold_level3").each(function () {
        $(this).removeClass("fold_up");
    });
}
function setParameters(pageNumber,currentType,sort){
    this.pageNumber=pageNumber;
    this.currentType=currentType;
    this.sort=sort;
    $("#supermarketBox").empty();
}
function showHidden(){
    console.log("追加页面");
    $(".con_shop ul").css("display","block");
    $(".con_shop ul").empty();
    $(".con_shop ul").append('<div><img style="width: 60%;margin: 50% 20% 0 20%;" src="' + basePath + 'images/WFJClient/DigitalMall/kongbai.png"/></div>');
    $(".con_shop ul").css("background", "#fff");
    $("#supermarketBox").css("display","none");
    pageNumber = 0;
    pageCount = 0;
    isLoading = false;

}
function showList(){
    console.log("追加页面");
    $(".con_shop ul").css("display","none");


    $("#supermarketBox").css("display","block");
    pageNumber = 0;
    pageCount = 0;
    isLoading = false;

}
function supermarketList(shopType) {
    clearTimeout(t);
    if (isLoading) return;
    isLoading = true;

    //ajax获取超市相关数据追加展示
    var url = basePath+"ea/smg/sajax_sm_getShopByType.jspa";
    //获取搜索的超市名称
    var supermarketName = $("#supermarketName").val();
    $.ajax({
        url: encodeURI(url),
        type: "POST",
        async: true,
        data: {
            "pageForm.pageNumber": pageNumber,
            "longitude": longitude,
            "latitude": latitude,
            "shopType":currentType,
            "sort":sort
        },
        dataType: "json",
        success: function (data) {
            //$("#supermarketBox").empty();
            var member = eval("(" + data + ")");
            if (!member||!member.pageForm)
            {
                showHidden();
                return;
            }
            if (member.recordCount > 0) {
                showList();
                adjustSupermarketBox();
                var pageForm = member.pageForm;

                pageNumber = member.pageNumber;
                pageCount = member.pageCount;
                isLoading = false;
                var sup = [];
                for (var i in pageForm) {
                    sup.push('<li class="company-card">');
                    sup.push('<a href="'+ basePath +'/ea/industry/ea_CompanyProducts.jspa?ccompanyId=' + pageForm[i]['ccompanyId'] +'">');

                    // 图片部分
                    sup.push('<div class="card-image">');

                    if (!pageForm[i]['logoPath']) {
                        sup.push('<img src="https://impf2010.com/images/ea/production/forum/reportAnError.png" alt="暂无图片" />');
                    } else {

                        sup.push('<img src="https://impf2010.com/' + pageForm[i]['logoPath'] + '" alt="' + pageForm[i]['logoPath'] + '" />');

                    }

                    sup.push('</div>');

                    // 内容部分
                    sup.push('<div class="card-content">');
                    sup.push('<h2>' + pageForm[i]['companyName'] + '</h2>');

                    // if (pageForm[i]['phone']) {
                    //     sup.push('<p class="tel">联系电话：' + pageForm[i]['phone'] + '</p>');
                    // }
                    if (sup[i]['companyName']){
                        sup.push('<p class="tel">企业：'+ pageForm[i]['companyName'] + '</p>');
                    }
                    // ===== 横向商品展示（最多 3 个） =====
                    if (pageForm[i]['goodsList'] && pageForm[i]['goodsList'].length > 0) {
                        sup.push('<div class="goods-preview">');
                        for (var j = 0; j < pageForm[i]['goodsList'].length && j < 4; j++) {
                            var g = pageForm[i]['goodsList'][j];
                            sup.push('<div class="goods-item">');
                            if (g['photoPath']) {
                                sup.push('<img src="https://impf2010.com/' + g['photoPath'] + '" alt="' + g['goodsName']+'" />');
                            } else {
                                sup.push('<img src="https://impf2010.com/images/ea/production/forum/reportAnError.png" alt="暂无图片" />');
                            }
                            sup.push('<p>'+(g['goodsName']||'未命名商品')+'</p>');
                            sup.push('</div>');
                        }
                        sup.push('</div>');
                    }

                    sup.push('</div>'); // end card-content
                    sup.push('</a>');
                    sup.push('</li>');
                }
                $("#supermarketBox").append(sup.join(""));
            } else {
                console.log("没有查询到商户！")
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
    $("#locationText").text("定位中...");
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    if (typeof Android !=='undefined') {
        console.log("安卓");
        var collection = Android.callgetAddrstr();//调用安卓接口
        if (collection != "-1") {
            var ar = collection.split(",");
            address = ar[0];//当前位置
            longitude = ar[1];//经度
            latitude = ar[2];//纬度
            city=ar[3];
            console.log("定位"+city);

            $("#locationText").text(city);
            console.log("当前位置,经纬度"+collection);
            supermarketSearch()
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

function getLocationSuccess( collection){
    if (collection != "-1") {
        var ar = collection.split(",");
        address = ar[0];//当前位置
        longitude = ar[1];//经度
        latitude = ar[2];//纬度
        city=ar[3];
        console.log("定位"+city);

        $("#locationText").text(city);
        supermarketSearch()

        console.log("当前位置,经纬度"+collection);
        // $("#getLocation").text(address.substring(address.indexOf("市")+1));
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


function ajax() {
    clearTimeout(t);
    pagenumber++;
    var url = basePath + "ea/digitalmall/sajax_ea_ajaxDigitalMall.jspa?";
    $.ajax({
        url: url,
        type: "post",
        async: false,
        dataType: "json",
        data: {
            "pageForm.pageNumber": pagenumber,
            "proName": ppname,
            "tradecode": tradecode,
            "search": search
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            if (pageForm != null && pageForm.recordCount > 0) {
                var productlist = pageForm.list;
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
                var prostr = [];
                $(".last").removeClass("last");
                for (var i = 0; i < productlist.length; i++) {
                    var pro = productlist[i];
                    if (i == productlist.length - 1) {
                        prostr.push('<li class="last" onclick="goodsDetail(this)">');
                    } else {
                        prostr.push('<li onclick="goodsDetail(this)">');
                    }
                    prostr.push('<input type="hidden" value="' + pro[1] + '" id="ppid"/>');
                    prostr.push('<input type="hidden" value="' + pro[5] + '" id="goodsid"/>');
                    prostr.push('<input type="hidden" value="' + pro[6] + '" id="companyid"/>');
                    prostr.push('<input type="hidden" value="' + pro[10] + '" id="ccompanyid"/>');

                    prostr.push('<input type="hidden" value="' + pro[0] + '" id="goodsName"/>');
                    prostr.push('<input type="hidden" value="' + pro[4] + '" id="photo"/>');
                    prostr.push('<input type="hidden" value="' + pro[16] + '" id="remark"/>');
                    prostr.push('<input type="hidden" value="' + pro[15] + '" id="type"/>');
                    prostr.push('<input type="hidden" value="' + pro[3] + '" id="brand"/>');
                    prostr.push('<input type="hidden" value="' + pro[17] + '" id="categoryName"/>');
                    prostr.push('<input type="hidden" value="' + pro[18] + '" id="categoryId"/>');
                    prostr.push('<input type="hidden" value="' + pro[2] + '" id="cost"/>');
                    prostr.push('<input type="hidden" value="' + pro[14] + '" id="hdtype"/>');
                    prostr.push('<img src="' + basePath + pro[4] + '" onerror="this.src="' + basePath + '/images/ea/production/forum/reportAnError.png\'" alt="">');
                    prostr.push('<div class="txt">');
                    prostr.push('<h4>' + pro[0] + '</h4>');
                    prostr.push('<div class="clearfix">');
                    if (pro[14] != null && pro[14] != "" && pro[12] != null && pro[12] != "") {
                        if (pro[14] == '00') {//促销活动
                            prostr.push('<p>活动价:&yen;<span>' + pro[12] + '</span></p>');
                            prostr.push('<input type="hidden" value="' + pro[12] + '" id="price"/>');
                            prostr.push('<input type="hidden" value="' + pro[20] + '" id="activityid"/>');
                            prostr.push('<input type="hidden" value="3" id="priceType"/>');
                        }
                        if (pro[14] == '01') {//特价活动
                            prostr.push('<p>特价:&yen;<span>' + pro[12] + '</span></p>');
                            prostr.push('<input type="hidden" value="' + pro[12] + '" id="price"/>');
                            prostr.push('<input type="hidden" value="' + pro[20] + '" id="activityid"/>');
                            prostr.push('<input type="hidden" value="4" id="priceType"/>');
                        }
                        activeStateUpdate(pro[1]);
                    } else {
                        //超过活动时间，不展示活动价》判断活动状态改为 03:结束
                        var url = basePath + "ea/digitalmall/sajax_ea_activeTimeoutStateUpdate.jspa";
                        $.ajax({
                            url: url,
                            type: 'POST',
                            data: {"ppid": pro[1]},
                            async: true,
                            success: function (data) {
                                var member = eval("(" + data + ")");
                                if (member.code == '200') {
                                    //活动状态更改成功
                                } else {
                                    //活动状态更改异常
                                }
                            },
                            error: function (err) {
                                console.log('err')
                            }
                        });
                    }
                    if (pro[13] != null && pro[13] != "") {//vip活动
                        prostr.push('<p>VIP价:&yen;<span>' + pro[13] + '</span></p>');
                        prostr.push('<input type="hidden" value="' + pro[13] + '" id="price"/>');
                        prostr.push('<input type="hidden" value="' + pro[19] + '" id="activityid"/>');
                        prostr.push('<input type="hidden" value="2" id="priceType"/>');
                    } else {
                        prostr.push('<input type="hidden" value="' + pro[2] + '" id="price"/>');
                        prostr.push('<input type="hidden" value="0" id="priceType"/>');
                        prostr.push('<p>零售价:&yen;<span>' + pro[2] + '</span></p>');
                    }
                    prostr.push('</div>');
                    prostr.push('<p class="shop"><span>' + pro[8] + '</span>人已购买</p></div></li>');
                }
                $(".con_shop ul").append(prostr.join(""));
                $(".con_shop").attr("style", "padding-top:" + $(window).height() * 0.08 + "px;");
                $(".con_shop ul li img").css("height", $(".con_shop ul li img").width() + "px");
                $(".con_shop ul").css("background", "#fff");
                getHeight();
            } else {
                $(".con_shop ul").empty();
                $(".con_shop ul").append('<div><img style="width: 60%;margin: 20% 20% 0 20%;" src="' + basePath + 'images/WFJClient/DigitalMall/kongbai.png"/></div>');
                $(".con_shop ul").css("background", "#fff");
            }
        },
        error: function () {
            alert("产品加载失败");
        }
    });


}