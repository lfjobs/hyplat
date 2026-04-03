/*公司产品*/
var posNum = "";
$(document).ready(function () {
    getPosNum();
    loaded();

    if (pos == "1") {

        $(".backt").find("a").removeAttr("href");
        $(".backt").html("&nbsp;");
        //   $(".backt").find("a").attr("onclick","javascript:window.history.go(-1);return false;");

    }


    $("header").attr("style", "height:" + $(window).height() * 0.08 + "px;line-height:" + $(window).height() * 0.08 + "px;position:fixed;");
    $(".content_hidden").attr("style", ";overflow: auto;position: relative;" + "top:" + $(window).height() * 0.08 + "px");
    $(".content").attr("style", "height:" + $(window).height() * 0.92 + "px;overflow: auto;");
    var topMain = $('#myCarousel').height() + $('.js-shop').height(); //是头部的高度加头部与nav导航之间的距离
    var nav = $(".nav");
    $(".content").scroll(function () {
        if ($(".content").scrollTop() > topMain) {//如果滚动条顶部的距离大于topMain则就nav导航就添加类.nav_scroll，否则就移除

            $(".nav").attr("style", " background: #fff; position: fixed;top:" + $("header").height() + "px;")
        } else {
            $(".nav").attr("style", " background: #fff; position: relative;;top:0;")
        }
    });
    //轮播获取焦点
    var myTouch = util.toucher(document.getElementById('myCarousel'));
    myTouch.on('swipeLeft', function (e) {
        $('#carright').click();
    }).on('swipeRight', function (e) {
        $('#carleft').click();
    });

    $("#img1").click(function () {
        $("#context1").css("display", "none");
        $("#context2").show();
        $("#img2").show();
        $("#img1").hide();
    })
    $("#img2").click(function () {
        $("#context2").css("display", "none");
        $("#context1").show();
        $("#img1").show();
        $("#img2").hide();
    })

    $(".Sort").click(function () {
        if ($('.Sort-eject').hasClass('grade-w-roll')) {
            $('.Sort-eject').removeClass('grade-w-roll');
        } else {
            $('.Sort-eject').addClass('grade-w-roll');
        }
    });


    $(".Sort").click(function () {
        if ($('.Category-eject').hasClass('grade-w-roll')) {
            $('.Category-eject').removeClass('grade-w-roll');
        }

    });
    $(".Sort").click(function () {
        if ($('.grade-eject').hasClass('grade-w-roll')) {
            $('.grade-eject').removeClass('grade-w-roll');
        }


    });
    //产品详情
    $(document).on("click", ".goodsDetail", function () {
        var type = $(this).find("#type").val();
        var parms = [];
        var url = "";
        parms.push("ppid=" + $(this).find("#ppid").val());
        parms.push("&ccompanyId=" + ccompanyId);
        if (type == "学员报名") {
            parms.push("&goodsName=" + $(this).find("#goodsName").val());
            parms.push("&companyID=" + companyId);
            parms.push("&photo=" + $(this).find("#photo").val());
            parms.push("&remark=" + $(this).find("#remark").val());
            if (posNum != null && posNum != "") {
                if ($(this).find("#priceType").val() == "2") {
                    parms.push("&price=" + $(this).find("#cost").val());
                } else {
                    parms.push("&price=" + $(this).find("#price").val());
                }
            } else {
                parms.push("&price=" + $(this).find("#price").val());
            }

            parms.push("&goodsID=" + $(this).find("#goodsid").val());
            parms.push("&licenceType=" + $(this).find("#type").val())
            parms.push("&brand=" + $(this).find("#brand").val());
            parms.push("&categoryName=" + $(this).find("#categoryName").val());
            parms.push("&categoryId=" + $(this).find("#categoryId").val());
            parms.push("&priceType=" + $(this).find("#priceType").val());
            parms.push("&activityid=" + $(this).find("#activityid").val());
            parms.push("&cost=" + $(this).find("#cost").val());
            url = basePath + "/st/enroll/ea_getEnroll.jspa?" + parms.join("");
        } else {
            parms.push("&goodsid=" + $(this).find("#goodsid").val());
            parms.push("&companyId=" + companyId);
            parms.push("&pos=" + pos);
            url = basePath + "ea/wfjshop/ea_doodsDetail.jspa?" + parms.join("");
        }
        window.location.href = url;
    });
    //产品分类
    $(document).on("click", ".js-shop li", function () {
        flag = 'categories';
        ppIds = "";
        var ppId = $(this).find("input").val();
        var url = basePath + "ea/industry/sajax_ea_searchCategories.jspa?";
        $.ajax({
            url: url,
            type: "get",
            async: false,
            dataType: "json",
            data: {"ppId": ppId},
            success: function (data) {
                var member = eval("(" + data + ")");
                var list = member.list;
                var str = "";
                if (list != null && list.length > 0) {
                    if (list.length == 1) {
                        alert("此分类无下级分类");
                    } else {
                        $(".js-shop ul").empty();
                        for (var i = 0; i < list.length; i++) {
                            var entity = list[i];
                            if (entity[4] == ppId) {
                                str += "<li>";
                                str += "<input type='hidden' value='" + entity[0] + "'/>";
                                str += "<div><div>" + entity[1] + "</div></div></li>";
                                if (i % 3 == 0) {
                                    str += "<hr style='border-top: 1px solid #ddd;clear: both;margin: 0;'>";
                                }
                                ppIds += entity[0] + ",";
                            }
                        }
                        str += "<div class='clearfix'></div>"
                    }
                    $(".js-shop ul").append(str);
                }
            }
        });
        pagenumber = 0;
        $(".style1_con").html("");
        $(".style2_con").html("");
        loaded();
    });
    //加入购物车


    $(document).on("click", ".addcar", function (event) {
        event.stopPropagation();
        ppid = $(this).parents("li").find("#ppid").val();
        price = $(this).parents("li").find("#price").val();
        style1_img = $(this).parents("li").find(".style1_img").find("img").attr("src");
        var goodsid = $(this).parents("li").find("#goodsid").val();
        var typeID = $(this).parents("li").find("#typeID").val();
        if (typeID != null && (typeID.indexOf("学员报名") != -1)) {
            alert("报名产品无须加入购物车，请直接购买");
            return;
        }


        getProAttriList(goodsid, ppid, $(this));


    });


    //选择规格后确定加入购物车
    $(".jrconfirm").click(function () {
        var str = "";
        var textname = "";
        var textdx = "";
        if (!$("#yanse").is(":hidden")) {

            var yanse = $("#set-meal").text();
            textname = $("#yanse").find("h2").text();
            if (yanse == "") {
                alert("请选择" + textname);
                return false;
            }
            str = textname + ":" + yanse;

        }

        if (!$("#daxiao").is(":hidden")) {
            var daxiao = $("#size").text();
            textdx = $("#daxiao").find("h2").text();
            if (daxiao == "") {
                alert("请选择" + textdx);
                return false;
            }
            if (textname != null && textname != "") {
                str += "," + textdx + ":" + daxiao;
            } else {
                str = textdx + ":" + daxiao;
            }
        }
        stardard = str;

        setAssiShopCart(ppid, $(this), $(".number").find("h5").text());


    });


    // 数量选择
    var old;
    var novo;
    $(".box2 .increase").click(function () {

        old = $(this).prev("h5").html();
        if (parseInt(old) == "99") {
            novo = old;
        } else {
            novo = parseInt(old) + 1;

        }
        $(this).prev("h5").html(novo);
        $("#shuliang").val(novo);

    });

    $(".box2 .decrease").click(function () {

        old = $(this).next("h5").html();
        if (parseInt(old) == "1") {
            novo = old;
        } else {
            novo = parseInt(old) - 1;

        }
        $(this).next("h5").html(novo);
        $("#shuliang").val(novo);
    });


    /*选规格*/
    $(".norms").click(function () {
        $(".box2").show();
        $(".alert_new_").show();
    });
    $(".alert_new_").click(function () {
        $(".box2").hide();
        $(".alert_new_").hide();
    });
    $(".sback img").click(function () {
        $(".box2").hide();
        $(".alert_new_").hide();
    });

    $(document).on("click", ".items label", function () {

        $(this).parent().find("label").css({"background": "#fff", "color": "#666"});
        $(this).parent().find("label").removeClass("xz");

        $(this).css({"background": "#f74c31", "color": "#fff"});
        $(this).addClass("xz");

        var tupurl = $(this).find(".tp").text();
        if (tupurl != "") {

            $(".box2 .img").find("img").attr("src", basePath + tupurl);
        }

        //this.className = "xz"
    });

    $(document).on("click", ".size label", function () {
        $(".box2 .summary2 .stock p span:nth-child(1)").text("已选择");
        $(".box2 .summary2 .stock p span").css("padding", "0");
        var col = $(this).text();
        //  alert(col)
        $("#size").text(col);
    });
    $(document).on("click", ".set-meal label", function () {
        $(".box2 .summary2 .stock p span:nth-child(1)").text("已选择");
        $(".box2 .summary2 .stock p span").css("padding", "0");
        var set = $(this).find("span").eq(0).text();

        $("#set-meal").text(set);
    });
    //搜索
    $(".search").focus(function () {
        $(".search_overly").hide();
    });
    $(".search").blur(function () {
        var val = $(this).val();
        if (val == '') {
            $(".search_overly").show();
        }
    });

    $(".search").on("input", function () {
        $(".style1_con").empty();
        pagenumber = 0;
        pagecount = 0;
        search = $(".search").val();
        loaded();
    });
});//加载完毕


window.onload = window.onresize = function () {
    //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
    //获取窗口的尺寸
    var clientWidth = document.documentElement.clientWidth;
    //console.log(clientWidth);
    //通过屏幕宽度去设置不同的后台根字体的大小
    //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
    document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px'
}

function Sorts(sbj) {
    var arr = document.getElementById("Sort-Sort").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++) {
        var a = arr[i];
        a.style.borderBottom = "";
    }

    sbj.style.borderBottom = "solid 1px #ff7c08"
}

function getHeight() {
    t = setTimeout("getHeight()", 200);
    if ($("#context1").is(":hidden")) {
        if ($("#context2 .last").offset().top + $(".last").height() - $("#context2 .last").height() * 9 < $(window).height()) {
            if (pagenumber < pagecount) {
                loaded();
            }
        }
    } else {
        if ($("#context1 .last").offset().top + $(".last").height() - $("#context1 .last").height() * 4 < $(window).height()) {
            if (pagenumber < pagecount) {
                loaded();
            }
        }
    }
}

function loaded() {
    pagenumber++;
    clearTimeout(t);
    var url = basePath + "ea/industry/sajax_ea_getAjaxPorductsList.jspa?";
    $.ajax({
        url: url,
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            "pageForm.pageNumber": pagenumber,
            "ccompanyId": ccompanyId,
            "companyId": companyId,
            "search": search,
            "ppId": ppIds
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            //var Pct = 1;
            var setsubsidize = member.setsubsidize;
            /*if (setsubsidize != null) {
                Pct = setsubsidize.totalPct / 100 + 1;
            }*/
            if (pageForm != null && pageForm.recordCount > 0) {
                var productlist = pageForm.list;
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
                var prostr1 = "";
                var prostr2 = "";
                var prostr3 = "";
                if (productlist != null) {
                    $(".last").removeClass("last");

                    for (var i = 0; i < productlist.length; i++) {
                        var pro = productlist[i];
                        if (pro[6] == "学员协议") {
                            continue;
                        }
                        /*if (i == productlist.length - 1) {
                            prostr1 += "<li class='goodsDetail last'>";
                            prostr2 += "<li class='goodsDetail last'>";
                        } else {*/
                        prostr1 += "<li class='goodsDetail'>";
                        prostr2 += "<li class='goodsDetail'>";
                        //}
                        prostr1 += "<input type='hidden' value='" + pro[1] + "' id='ppid'/>";
                        prostr1 += "<input type='hidden' value='" + pro[4] + "' id='goodsid'/>";

                        prostr1 += "<input type='hidden' value='" + pro[0] + "' id='goodsName'/>";
                        prostr1 += "<input type='hidden' value='" + pro[3] + "' id='photo'/>";
                        prostr1 += "<input type='hidden' value='" + pro[13] + "' id='remark'/>";
                        prostr1 += "<input type='hidden' value='" + pro[6] + "' id='type'/>";
                        prostr1 += "<input type='hidden' value='" + pro[12] + "' id='brand'/>";
                        prostr1 += "<input type='hidden' value='" + pro[16] + "' id='categoryName'/>";
                        prostr1 += "<input type='hidden' value='" + pro[17] + "' id='categoryId'/>";
                        prostr1 += "<input type='hidden' value='" + pro[2] + "' id='cost'/>";

                        //普通零售
                        prostr1 += "<div class='style1_img'><img src='" + basePath + pro[3] + "'/></div>";
                        prostr1 += "<h3>" + pro[0] + "</h3>";
                        prostr1 += "<div class='clearfix'>"
                        if (pro[11] != null && pro[11] != "" && pro[9] != null && pro[9] != "") {
                            if (pro[11] == '00') {//促销活动
                                prostr1 += "<h4>" + "活动价:&yen;" + pro[9] + "</h4>";
                                prostr1 += "<input type='hidden' value='" + pro[9] + "' id='price'/>";
                                prostr1 += "<input type='hidden' value='"+pro[19]+"' id='activityid'/>";
                                prostr1 += "<input type='hidden' value='3' id='priceType'/>";
                            }
                            if (pro[11] == '01') {//特价活动
                                prostr1 += "<h4>" + "特价:&yen;" + pro[9] + "</h4>";
                                prostr1 += "<input type='hidden' value='" + pro[9] + "' id='price'/>";
                                prostr1 += "<input type='hidden' value='"+pro[19]+"' id='activityid'/>";
                                prostr1 += "<input type='hidden' value='4' id='priceType'/>";
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
                            })

                            if (pro[10] != null && pro[10] != "") {//vip活动
                                prostr1 += "<h4>" + "VIP价:&yen;" + pro[10] + "</h4>";
                                prostr1 += "<input type='hidden' value='" + pro[10] + "' id='price'/>";
                                prostr1 += "<input type='hidden' value='"+pro[18]+"' id='activityid'/>";
                                prostr1 += "<input type='hidden' value='2' id='priceType'/>";
                            } else {
                                //普通零售
                                prostr1 += "<h4>" + "零售价:&yen;" + pro[2] + "</h4>";
                                prostr1 += "<input type='hidden' value='" + pro[2] + "' id='price'/>";
                                prostr1 += "<input type='hidden' value='0' id='priceType'/>";
                            }
                        }
                        prostr1 += "</div>"
                        prostr1 += "<p>" + "<span>" + pro[8] + "人购买</span><img src='" + basePath + "images/WFJClient/PersonalJoining/companyHomepage/add.png' class='addcar'> </p></li>";

                        prostr2 += "<input type='hidden' value='" + pro[1] + "' id='ppid'/>";
                        prostr2 += "<input type='hidden' value='" + pro[4] + "' id='goodsid'/>";
                        prostr2 += "<input type='hidden' value='" + pro[2] + "' id='price'/>"
                        prostr2 += "<input type='hidden' value='" + pro[6] + "' id='type'/>";

                        prostr2 += "<input type='hidden' value='" + pro[0] + "' id='goodsName'/>";
                        prostr2 += "<input type='hidden' value='" + pro[4] + "' id='photo'/>";
                        prostr2 += "<input type='hidden' value='" + pro[9] + "' id='remark'/>";
                        prostr2 += "<input type='hidden' value='" + pro[3] + "' id='brand'/>";
                        prostr2 += "<input type='hidden' value='" + pro[13] + "' id='categoryName'/>";
                        prostr2 += "<input type='hidden' value='" + pro[14] + "' id='categoryId'/>";
                        prostr2 += "<input type='hidden' value='" + pro[2] + "' id='cost'/>";
                        prostr2 += "<div class='img'><img src='" + basePath + pro[3] + "'/></div>";
                        prostr2 += "<div class='text'>";
                        prostr2 += "<h3>" + pro[0] + "</h3>";
                        prostr2 += "<div class='clearfix h3_2_div'>"
                        if (pro[11] != null && pro[11] != "" && pro[9] != null && pro[9] != "") {
                            if (pro[11] == '00') {//促销活动
                                prostr2 += "<h4>" + "活动价:&yen;" + pro[9] + "</h4>";
                                prostr2 += "<input type='hidden' value='" + pro[9] + "' id='price'/>";
                                prostr2 += "<input type='hidden' value='"+pro[19]+"' id='activityid'/>";
                                prostr2 += "<input type='hidden' value='3' id='priceType'/>";
                            }
                            if (pro[11] == '01') {//特价活动
                                prostr2 += "<h4>" + "特价:&yen;" + pro[9] + "</h4>";
                                prostr2 += "<input type='hidden' value='" + pro[9] + "' id='price'/>";
                                prostr2 += "<input type='hidden' value='"+pro[19]+"' id='activityid'/>";
                                prostr2 += "<input type='hidden' value='4' id='priceType'/>";
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
                            if (pro[10] != null && pro[10] != "") {//vip活动
                                prostr2 += "<h4>" + "VIP价:&yen;" + pro[10] + "</h4>";
                                prostr2 += "<input type='hidden' value='" + pro[10] + "' id='price'/>";
                                prostr2 += "<input type='hidden' value='"+pro[18]+"' id='activityid'/>";
                                prostr2 += "<input type='hidden' value='2' id='priceType'/>";
                            } else {
                                //普通零售
                                prostr2 += "<h4>" + "零售价:&yen;" + pro[2] + "</h4>";
                                prostr2 += "<input type='hidden' value='" + pro[2] + "' id='price'/>";
                                prostr2 += "<input type='hidden' value='0' id='priceType'/>";
                            }
                        }
                        prostr2 += "</div>"
                        prostr2 += "<p>" + "<span>" + pro[8] + "人购买</span><img src='" + basePath + "images/WFJClient/PersonalJoining/companyHomepage/add.png' class='addcar'> </p></li>";
                        prostr2 += "</div></li>";
                        /*				           if (flag==''){
                         if (pagenumber == 1) {
                         if (i < 3) {
                         if (i == 0) {
                         prostr3 += "<div class='item active'>";
                         } else {
                         prostr3 += "<div class='item'>";
                         }
                         prostr3 += "<img src='"
                         + basePath
                         + pro[3]
                         + "' alt='First slide' style='width: 100%;'></div>";
                         }
                         }
                         }*/
                    }
                    if (pagenumber == pagecount) {
                        prostr1 += "<li style='height:" + $(window).height() * 0.05 + "px;width:100%;clear:both;'><div style='text-align:center;height:100px;padding-top:" + $(window).height() * 0.015 + "px;'>无更多产品</div></li>";
                        prostr2 += "<li style='height:" + $(window).height() * 0.05 + "px;'><div style='text-align:center;height:100px;padding-top:" + $(window).height() * 0.015 + "px;'>无更多产品</div></li>";
                    }
                    $(".style1_con").append(prostr1);
                    $(".style1_con").find("li").last().addClass("last");
                    $(".style2_con").append(prostr2);
                    $(".style2_con").find("li").last().addClass("last");
                    $(".carousel-inner").append(prostr3);
                    getHeight()
                }
            } else {
                prostr1 = "<li style='height:" + $(window).height() * 0.05 + "px;width:100%;clear:both;'><div style='text-align:center;height:100px;padding-top:" + $(window).height() * 0.015 + "px;'>无更多产品</div></li>";
                prostr2 = "<li style='height:" + $(window).height() * 0.05 + "px;'><div style='text-align:center;height:100px;padding-top:" + $(window).height() * 0.015 + "px;'>无更多产品</div></li>";
                $(".style1_con").append(prostr1);
                $(".style2_con").append(prostr2);
            }
        },
        error: function cbf(data) {
            alert("产品加载失败");
        }
    });
}

//活动状态更改[改为01:已开启]
function activeStateUpdate(ppid) {
    var url = basePath + "ea/digitalmall/sajax_ea_activeStateUpdate.jspa";
    $.ajax({
        url: url,
        type: 'POST',
        data: {"ppid": ppid},
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
    })


}


//综合排序搜索
function Sorts(id) {
    $("#" + id).css("border-bottom", "solid 1px #ff7c08");
    $("#" + id).siblings().css("border-bottom", "");
    $(".style1_con").html("");
    $(".style2_con").html("");
    var searchName = $("#" + id).attr("id");
    if (searchName != "" && searchName != null && searchName != undefined) { // "",null,undefined
        search = searchName;
    }
    pagenumber = 0;
    flag = "sort";
    loaded();
    $('.Sort-eject').removeClass('grade-w-roll');
    $('.Sort-eject').find("li").css("border-bottom", "");
}

//查询购物车助手商品数目
function shopAssiCartGoodNum() {
    var url = basePath + "ea/assicode/sajax_ea_shopAssiCartGoodNum.jspa?d=" + new Date();
    $.ajax({
        url: url,
        type: "get",
        async: false,
        dataType: "json",
        data: {
            companyId: companyId,
            pos: pos,
            posNum: posNum
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var goodNum = member.goodNum;
            $(".num0").find("span").text(goodNum);
            $(".num0").css("display", "inline");
        },
        error: function () {
            alert("查询购物车商品数失败");
        }
    });
}

/***
 *
 *
 * 获取商品销售属性
 *
 */
function getProAttriList(goodsid, ppid, obj) {
    var url = basePath + "ea/assicode/sajax_ea_getProAttriList.jspa?d=" + new Date();
    $.ajax({
        url: url,
        type: "get",
        async: false,
        dataType: "json",
        data: {
            goodsid: goodsid
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var color = member.color;
            var size = member.size;
            var listsize = member.listsize;
            var listcolor = member.listcolor;
            if (size != "" || color != "") {
                $(".box2").show();
                $(".alert_new_").show();
                $(".pricer").html("&yen" + price);
                $(".box2 .img").find("img").attr("src", style1_img);
                $("#size").text("");
                stardard = "";
                $("#set-meal").text("");
                $(".number").find("h5").text("1");
                $(".box2 .summary2 .stock p span:nth-child(1)").text("未选择属性");
                if (size != "") {
                    $("#daxiao").show();
                    $("#daxiao").find("h2").text(size);
                    var sizearray = [];
                    for (var i = 0; i < listsize.length; i++) {
                        sizearray.push("<label>" + listsize[i].attrivalue + "</label>");

                    }
                    $("#daxiao").find(".items").html(sizearray.join(""));

                }
                if (color != "") {
                    $("#yanse").show();
                    $("#yanse").find("h2").text(color);
                    var colorarray = [];
                    for (var i = 0; i < listcolor.length; i++) {
                        colorarray.push("<label><span>" + listcolor[i].attrivalue + "</span><span class='tp' style='display: none;'>" + listcolor[i].imgurl + "</span></label>");

                    }
                    $("#yanse").find(".items").html(colorarray.join(""));

                }
                return;

            }

            setAssiShopCart(ppid, obj, "1");

        },
        error: function () {
            alert("获取销售属性失败");
        }
    });

}

/**
 *
 * 加入购物车助手
 * @param ppid
 * @param stardard
 */
function setAssiShopCart(ppid, obj, itemNum) {


    var ulp = basePath + "/ea/wfjshop/sajax_ea_setcity.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: false,
        dataType: "json",
        data: {
            ppid: ppid,
            stardard: (stardard == "" ? "默认规格" : stardard),
            itemNum: itemNum == "" ? "1" : itemNum,
            pos: pos,
            posNum: posNum
        },
        success: function (data) {
            var json = eval('(' + data + ')');
            if (json.login == "login") {

                document.location.href = basePath
                    + "page/WFJClient/NewLogin.jsp?loginPage=login";
                return;

            }

            var offset = $("#search").offset();
            var addcar = $(obj);
            var img = addcar.parent().find('img').attr('src');
            var flyer = $('<img class="u-flyer" src="' + basePath + 'images/WFJClient/PersonalJoining/companyHomepage/yuan2.png">');
            flyer.fly({
                start: {
                    left: event.pageX - 8,
                    top: event.pageY - 20
                },
                end: {
                    left: offset.left + 10,
                    top: offset.top + 10,
                    width: 0,
                    height: 0
                },

            });
            $(".num0").find("span").text(Number($(".num0").find("span").text()) + Number(itemNum));
            $(".box2").hide();
            $(".alert_new_").hide();

        }
    });

}

function getPosNum() {

    try {
        posNum = Android.forAndroidDeviceId();

        if (posNum != "" && posNum != null) {
            posNum = isExistPosNum(posNum);
            if (posNum != "" && posNum != null) {
                var href = $(".cartList").find("a").eq(0).attr("href");
                $(".cartList").find("a").eq(0).attr("href", href + "&posNum=" + posNum);
            }
        }
    } catch (error) {
        if (($(window).width() == 1080 && $(window).height() == 1546) || ($(window).width() == 534 && $(window).height() == 636)) {
            posNum = 123;

            var href = $(".cartList").find("a").eq(0).attr("href");
            $(".cartList").find("a").eq(0).attr("href", href + "&posNum=" + posNum);


        }
    }
    shopAssiCartGoodNum();
}

//判断是否是终端机器
function isExistPosNum(posNum) {
    var url = basePath + "ea/smg/sajax_sm_isExistPosNum.jspa";
    $.ajax({
        url: url,
        type: "get",
        dataType: "json",
        async: false,
        data: {
            posNum: posNum
        },
        success: function (data) {
            var m = eval("(" + data + ")");
            var result = m.result;
            if (result != "0") {
                posNum = "";
            }

        },
        error: function (data) {
            // alert("验证失败");
        }

    });
    return posNum;
}


