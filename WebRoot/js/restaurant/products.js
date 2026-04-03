$(function () {
    initLoaded();//初始加载菜单




    //获取扫描枪内容
    window.onload = function (e) {
        var code = "";
        var lastTime, nextTime;
        var lastCode, nextCode;

        document.onkeypress = function (e) {

            nextCode = e.which;
            console.log(nextCode + "------------");
            nextTime = new Date().getTime();

            if (lastCode != null && lastTime != null && nextTime - lastTime <= 30) {
                code += String.fromCharCode(lastCode);
            } else if (lastCode != null && lastTime != null && nextTime - lastTime > 100) {
                code = "";
            }

            lastCode = nextCode;
            lastTime = nextTime;

        }

        this.onkeypress = function (e) {
            if (e.which == 13) {
                if (code != "") {
                    if (code.length == 20) {
                        checkScardJiFen(code);
                        return false;
                    }
                    if(barcode.length<=13){
                        document.location.href = basePath+"ea/sm/ea_qdlist.jspa?posNum="+posNum+"&ccompanyID="+ccompanyID+"&barCode="+code;

                    }
                }
                code = "";
            }
        }
    }





    //点击左侧导航菜单
    $("#product li").on("click", function () {
        pn = 1;
        pagenumber = 1;// 第几页

        $("#product li").attr("style", "border-left: 2px solid transparent;");
        $(this).attr("style", "background:#fff;border-left: 2px solid #f97b7b;");
        $(".pro").removeClass("pro");
        $(this).addClass("pro");
        $("#pp").empty();
        $(".right").css("height", $("#pp").height() + "px");

        // 右侧
        $(".right dl").css({"height": $("#form1 dl dd h1").height() + $("#form1 dl dd p").height() + $("#form1 dl dd h2").height() + "px"});

        //获取点击的文本框
        name = $(this).find("span").eq(0).text();
        var parentId = $(this).find("span").eq(0).attr("class");
        $.ajax({
            url: basePath
            + "ea/restaurant/sajax_ea_products.jspa?",
            type: "get",
            async: false,
            data: {
                "parentName": name,
                parentId: parentId,
                ccompanyId: ccompanyId,
                companyId: companyId,
                posNum:posNum
            },
            dataType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                var pageForm = member.pageForm;
                $(".last").removeClass("last");
                var Pct = 1;
                if (pageForm == null) {
                    return;
                }
                var setsubsidize = member.setsubsidize;
                if (setsubsidize != null) {
                    Pct = setsubsidize.totalPct / 100 + 1;
                }
                pagecount = pageForm.pageCount;
                var map1 = member.map1;
                var mapat = member.mapat;
                count = pageForm.recordCount;
                pageSize = pageForm.pageSize;
                var length = pageForm.list.length;


                $("#pp").empty();
                $(".right").css("height", $("#pp").height() + "px");
                // 拼接字符串
                var htmlString = "";
                htmlString += "<form  method='post' id='form1' name='form1' >";
                $
                    .each(
                        pageForm.list,
                        function (i, n) {
                            if (i == length - 1) {
                                htmlString += "<dl class='last'>";
                            } else {
                                htmlString += "<dl>";
                            }
                            htmlString += "<dt><div class='hide_kuang'>";
                            if (n[9] != null && n[9] != "" && n[7] != null && n[7] != "") {
                                if (n[9] == '00') {//促销活动
                                    htmlString += "<img src='" + basePath + n[0] + "'/> <span class='cx'><i></i></span>";
                                    activeStateUpdate(n[2]);
                                }
                                if (n[9] == '01') {//特价活动
                                    htmlString += "<img src='" + basePath + n[0] + "'/> <span class='tj'><i></i></span>";
                                    activeStateUpdate(n[2]);
                                }
                                htmlString += "</div></dt>";
                                htmlString += "<dd><input type='hidden' value='" + n[2] + "'  class='" + n[2] + "' id='ppid'/>" +
                                    "<input type='hidden' value='" + n[4] + "'  id='goodsID'/>" +
                                    "<input type='hidden' value='" + (n[6] != null ? n[6] : "nullType") + "'  id='parentId'/>" +
                                    "<h1>" + n[1] + "</h1>";
                                htmlString += "<p>销量<span>" + n[5] + "</span></p>";
                                htmlString += "<h2>￥<span>" + (n[7] * Pct).toFixed(2) + "</span></h2>";
                            } else {
                                //超过活动时间，不展示活动价》判断活动状态改为 03:结束
                                var url = basePath + "ea/digitalmall/sajax_ea_activeTimeoutStateUpdate.jspa";
                                $.ajax({
                                    url: url,
                                    type: 'POST',
                                    data: {"ppid": n[2]},
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
                                if (n[8] != null && n[8] != "") {//vip活动
                                    htmlString += "<img src='" + basePath + n[0] + "'/> <span class='vip'><i></i></span>";
                                    htmlString += "</div></dt>";
                                    htmlString += "<dd><input type='hidden' value='" + n[2] + "'  class='" + n[2] + "' id='ppid'/>" +
                                        "<input type='hidden' value='" + n[4] + "'  id='goodsID'/>" +
                                        "<input type='hidden' value='" + (n[6] != null ? n[6] : "nullType") + "'  id='parentId'/>" +
                                        "<h1>" + n[1] + "</h1>";
                                    htmlString += "<p>销量<span>" + n[5] + "</span></p>";
                                    htmlString += "<h2>￥<span>" + (n[8] * Pct).toFixed(2) + "</span></h2>";
                                } else {
                                    //零售
                                    htmlString += "<img src='" + basePath + n[0] + "'/>";
                                    htmlString += "</div></dt>";
                                    htmlString += "<dd><input type='hidden' value='" + n[2] + "'  class='" + n[2] + "' id='ppid'/>" +
                                        "<input type='hidden' value='" + n[4] + "'  id='goodsID'/>" +
                                        "<input type='hidden' value='" + (n[6] != null ? n[6] : "nullType") + "'  id='parentId'/>" +
                                        "<h1>" + n[1] + "</h1>";
                                    htmlString += "<p>销量<span>" + n[5] + "</span></p>";
                                    htmlString += "<h2>￥<span>" + (n[3] * Pct).toFixed(2) + "</span></h2>";
                                }

                            }
                            htmlString += "<div class='span' >";
                            if (mapat[n[4]] !== "" && mapat[n[4]] != null) {
                                htmlString += "<input type='button' class='gg' onclick='Specifications(event,this)' value='选规格'/>";
                            } else {
                                htmlString += "<input type='button' class='jia' onclick='jia(event,this)' value='+'/>";
                                htmlString += "<h5 class='hh'>"
                                    + map1[n[2]]
                                    + "</h5>";
                                htmlString += "<input type='button' class='jian' onclick='jian(event,this)' value='-'/>";
                            }
                            htmlString += "</div><input type='submit' id='tijiao' style='display: none;'></dd></dl>";

                        });

                htmlString += "</form>";

                $("#pp").append(htmlString);
                $(".right").css("height", $("#pp").height() + "px");

                //处理右边菜数量为0的情况
                $(".hh").each(function () {
                    var value = $(this).text();
                    if (value == 0) {
                        $(this).css("display", "none");
                        $(this).parent("div.span").find("input.jian").css("display", "none");

                    }
                });

                $(".right dl").css("width", $(window).width() - $(window).width() * 0.22 + "px");
                /*//重复调用
                 getHeight();*/
                zong();
            }
        });

    });
    //点击每个菜，跳转到产品详情
    $(document).on("click", "#pp dl img", function () {
        var ppid = $(this).parents("dl").find("#ppid").val();
        var goodsid = $(this).parents("dl").find("#goodsID").val();
        window.location.href = basePath
            + "ea/wfjshop/ea_doodsDetail.jspa?result=result&ppid="
            + ppid + "&goodsid=" + goodsid + "&companyId="
            + companyId + "&ccompanyId=" + ccompanyId;
    });

    //点击结算
	$(".goshopping").click(function() {
        var money = $(".money").text();
        var directUrl = basePath
            + "ea/restaurant/ea_queryLoginName.jspa?companyId="+companyId+"&ccompanyId=" + ccompanyId + "&morre="
            + money+"&scandc="+scandc;
            if(posNum!=null&&posNum!="") {
                backUrl();
                document.location.href = basePath+"ea/sm/ea_qdlist.jspa?posNum="+posNum+"&ccompanyID="+ccompanyId+"&directUrl="+encodeURIComponent(directUrl);

            }else{
                document.location.href  = directUrl;

            }



    });
});
function a() {
}
//加数量
function jia(event, obj) {
    /*event.stopPropagation();*/

    var money = $(".money").text();

    $old = $(obj).siblings('.hh').html();

    if ($old == '99') {
        $new = $old;
    } else {
        $new = parseInt($old) + 1;
        if ($new == '99') {
            $(obj).css({
                "border": "1px solid rgba(0,0,0,0.3)",
                "color": "rgba(0,0,0,0.3)"
            });
        }

    }
    if ($new > '0') {
        $(obj).siblings('.hh').css("display", "block");
        $(obj).siblings(".jian").css("display", "block");
    }
    $(obj).siblings('.hh').html($new);
    $(obj).siblings(".jian").css({
        "border": "1px solid #ff4800",
        "color": "#ff4800"
    });

    var number1 = $(".pro").find("span#span").text();

    number1 = parseInt(1) + parseInt(number1);
    if (number1 >= 0) {

        $(".pro").find(".num2").css("display", "block");
        $(".pro").find("span#span").text(number1);

    } else if (number1 == 0) {
        $(".pro").find(".num2").css("display", "none");
    }
    var number = $(obj).parent("div").find(".hh").text();

    var ppid = $(obj).parents("dd").find("#ppid").val();
    var pname = $(obj).parents("dd").find("#pname").val();
    var pic = $(obj).parents("dd").find("#pic").val();

    var url = basePath
        + "ea/restaurant/sajax_ea_gateringOrders.jspa?cart.itemNum="
        + number + "&cart.pid=" + ppid;
    $.ajax({
        url: encodeURI(url),
        type: "post",
        async: true,
        data: {
            ccompanyId: ccompanyId,
            companyId: companyId,
            "stardard": "默认规格",
            "cart.pname": pname,
            "cart.pic": pic,
            posNum:posNum
        },
        dataType: "json",
    });

    // 计算总金额

    var mon = parseFloat($(obj).parents("dd").find("h2").find("span").text())
        + parseFloat(money);
    $(".money").text(mon.toFixed(2));

    if (mon == "0") {
        $(".nomoney").css("display", "block");
        $(".goshopping").css("display", "none");
    } else if (mon != "0") {
        $(".nomoney").css("display", "none");
        $(".goshopping").css("display", "block");
    }


}
//减数量
function jian(event, obj) {
    /*event.stopPropagation();*/
    var money = $(".money").text();

    $old = $(obj).siblings('.hh').html();
    if ($old == '0') {
        $new = $old;
        $(obj).attr('style', '');
    } else {
        $new = parseInt($old) - 1;
        if (parseInt($new) == 0) {
            $(obj).attr('style', '');
        }
    }
    $(obj).siblings('.hh').html($new);
    if ($new == '0') {
        $(obj).css("display", "none");
        $(obj).siblings('.hh').css("display", "none");
    }

    var number1 = $(".pro").find("span#span").text();

    number1 = parseInt(number1) - parseInt(1);

    if (number1 > 0) {

        $(".pro").find("span#span").text(number1);
        $(".pro").find(".num2").css("display", "block");
    }
    if (number1 == 0) {
        $(".pro").find("span#span").text(number1);
        $(".pro").find(".num2").css("display", "none");
    }
    var number = $(obj).parent("div").find(".hh").text();

    var ppid = $(obj).parents("dd").find("#ppid").val();

    var url = basePath
        + "ea/restaurant/sajax_ea_gateringOrders2.jspa?cart.itemNum="
        + number + "&cart.pid=" + ppid;
    $.ajax({
        url: encodeURI(url),
        type: "post",
        data: {
            ccompanyId: ccompanyId,
            companyId: companyId,
            "stardard": "默认规格",
            posNum:posNum

        },
        async: true,
        dataType: "json",
    });

    // 计算总金额
    var mon1 = $(obj).parents("dd").find("h2").find("span").text();
    mon = parseFloat(money) - parseFloat(mon1);

    $(".money").text(mon.toFixed(2));

    if (mon == "0") {
        $(".nomoney").css("display", "block");
        $(".goshopping").css("display", "none");
    } else if (mon != "0") {
        $(".nomoney").css("display", "none");
        $(".goshopping").css("display", "block");
    }
}

var height = 0;
var t;
/*function loaded() {
 clearTimeout(t);
 pagenumber += 1;

 var name = $(".pro").find("span").eq(0).text();
 var parentId = $(".pro").find("span").eq(0).attr("class");

 $.ajax({
 url: basePath
 + "ea/restaurant/sajax_ea_products.jspa?pageForm.pageNumber="
 + pagenumber,
 type: "get",
 async: false,
 data: {
 "parentName": name,
 parentId: parentId,
 ccompanyId: ccompanyId,
 companyId: companyId

 },
 dataType: "json",
 success: function (data) {
 var member = eval("(" + data + ")");
 var pageForm = member.pageForm;
 $(".last").removeClass("last");
 var Pct = 1;
 if (pageForm == null) {
 return;
 }
 var setsubsidize = member.setsubsidize;
 if (setsubsidize != null) {
 Pct = setsubsidize.totalPct / 100 + 1;
 }
 var map1 = member.map1;
 var mapat = member.mapat;
 pageSize = pageForm.pageSize;
 var length = pageForm.list.length;

 // 拼接字符串
 var htmlString = "";
 htmlString += "<form  method='post' id='form1' name='form1' >";
 $
 .each(
 pageForm.list,
 function (i, n) {
 if (i == length - 1) {
 htmlString += "<dl class='last'>";
 } else {
 htmlString += "<dl>";
 }
 htmlString += "<dt><div class='hide_kuang'>";
 htmlString += "<img src='" + basePath
 + n[0] + "'/>";
 htmlString += "</div></dt>";
 htmlString += "<dd><input type='hidden' value='" + n[2] + "' class='" + n[2] + "'id='ppid'/><input type='hidden' value='"
 + n[4]
 + "'  id='goodsID'/><input type='hidden' value='" + (n[6] != null ? n[6] : "nullType") + "'  id='parentId'/><h1>"
 + n[1] + "</h1>";
 htmlString += "<p>销量<span>" + n[5] + "</span></p>";
 htmlString += "<h2>￥<span>" + (n[3] * Pct).toFixed(2)
 + "</span></h2>";
 htmlString += "<div class='span' >";
 if (mapat[n[4]] !== "" && mapat[n[4]] != null) {
 htmlString += "<input type='button' class='gg' onclick='Specifications(event,this)' value='选规格'/>";
 } else {
 htmlString += "<input type='button' class='jia' onclick='jia(event,this)' value='+'/>";
 htmlString += "<h5 class='hh'>"
 + map1[n[2]] + "</h5>";
 htmlString += "<input type='button' class='jian'onclick='jian(event,this)' value='-'/>";
 }
 htmlString += "</div><input type='submit' id='tijiao' style='display: none;'></dd></dl>";

 });

 htmlString += "</form>";

 // 将拼接好的html字符串追加到body中
 $("#pp").append(htmlString);
 $(".right").css("height", $("#pp").height() + "px");

 //处理数量为0的情况
 $(".hh").each(
 function () {

 var value = $(this).text();
 if (value == 0) {
 $(this).css("display", "none");
 $(this).parent("div.span").find(
 "input.jian")
 .css("display", "none");

 }
 });

 },
 error: function (data) {
 //alert("获取项目失败");
 }
 });

 var mon = $(".money").text();
 if (mon == "0") {
 $(".nomoney").css("display", "block");
 $(".goshopping").css("display", "none");
 } else if (mon != "0") {
 $(".nomoney").css("display", "none");
 $(".goshopping").css("display", "block");
 }
 /!*getHeight();	*!/
 zong();
 }*/
//初始加载菜单
function initLoaded() {
    $.ajax({
        url: basePath + "ea/restaurant/sajax_ea_products.jspa?",
        type: "post",
        async: false,
        data: {
            "parentName": "菜品",
            "ccompanyId": ccompanyId,
            companyId: companyId,
            posNum:posNum
        },
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var login = member.login;
            if (login == "login") {
                document.location.href = basePath
                    + "page/WFJClient/NewLogin.jsp?loginPage=login";
                return;
            }
            var lists = member.lists;
            var map2 = member.map2;
            var mon = member.mon;

            $("#product").empty();

            var htmlString = "";
            for (var i = 0; i < lists.length; i++) {
                var obj = lists[i];
                htmlString += "<li class='product' onclick='a();'><span class='"
                    + obj[1]
                    + "'>"
                    + obj[0]
                    + "</span><div class='num0 num2'><span id='span'>"
                    + map2[obj[1]] + "</span></div></li>";

            }

            $("#product").append(htmlString);

            //加载完显示当前用户每样菜的总个数
            $(".product").each(function () {
                var i = $(this).find("span#span").text();
                var name = $(this).find("span").eq(0).text();
                if (i > 0) {
                    $(this).find("div.num0").css("display", "block");
                }
            });

            $(".money").text(mon);

        }
    });
    //判断结算按钮显示问题
    var moneys = $(".money").text();
    if (moneys != 0.00 || moneys != 0) {
        $(".nomoney").css("display", "none");
        $(".goshopping").css("display", "block");
    }

    //第一次加载显示第一种类别的菜
    var name = $("ul#product").find("li").eq("0").find("span").eq(0).text();
    $("#product").find("li").eq("0").addClass("pro");
    $("#product").find("li").eq("0").attr("style", "background:#fff;border-left: 2px solid #f97b7b;");

    var parentId = $("#product").find("li").eq("0").find("span").eq(0).attr("class");

    $.ajax({
        url: basePath
        + "ea/restaurant/sajax_ea_products.jspa",
        type: "post",
        async: false,
        data: {
            "parentName": name,
            parentId: parentId,
            ccompanyId: ccompanyId,
            companyId: companyId,
            posNum:posNum

        },
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            $(".last").removeClass("last");
            var Pct = 1;
            if (pageForm == null) {
                $(".food_alert").hide();
                $(".alert_food").hide();
                return;
            }
            var setsubsidize = member.setsubsidize;
            if (setsubsidize != null) {
                Pct = setsubsidize.totalPct / 100 + 1;
            }
            pagecount = pageForm.pageCount;
            var map1 = member.map1;
            var mapat = member.mapat;
            count = pageForm.recordCount;
            pageSize = pageForm.pageSize;
            var length = pageForm.list.length;

            $("#pp").empty();
            $(".right").css("height", $("#pp").height() + "px");
            //拼接字符串
            var htmlString = "";
            htmlString += "<form  method='post' id='form1' name='form1' >";
            $.each(pageForm.list, function (i, n) {
                if (i == length - 1) {
                    htmlString += "<dl class='last'>";
                } else {
                    htmlString += "<dl>";
                }
                htmlString += "<dt><div class='hide_kuang'>";
                if (n[9] != null && n[9] != "" && n[7] != null && n[7] != "") {
                    if (n[9] == '00') {//促销活动
                        htmlString += "<img src='" + basePath + n[0] + "'/> <span class='cx'><i></i></span>";
                        activeStateUpdate(n[2]);
                    }
                    if (n[9] == '01') {//特价活动
                        htmlString += "<img src='" + basePath + n[0] + "'/> <span class='tj'><i></i></span>";
                        activeStateUpdate(n[2]);
                    }
                    htmlString += "</div></dt>";
                    htmlString += "<dd><input type='hidden' value='" + n[2] + "'  class='" + n[2] + "' id='ppid'/>" +
                        "<input type='hidden' value='" + n[4] + "'  id='goodsID'/>" +
                        "<input type='hidden' value='" + (n[6] != null ? n[6] : "nullType") + "'  id='parentId'/>" +
                        "<h1>" + n[1] + "</h1>";
                    htmlString += "<p>销量<span>" + n[5] + "</span></p>";
                    htmlString += "<h2>￥<span>" + (n[7] * Pct).toFixed(2) + "</span></h2>";
                } else {
                    //超过活动时间，不展示活动价》判断活动状态改为 03:结束
                    var url = basePath + "ea/digitalmall/sajax_ea_activeTimeoutStateUpdate.jspa";
                    $.ajax({
                        url: url,
                        type: 'POST',
                        data: {"ppid": n[2]},
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
                    if (n[8] != null && n[8] != "" && member.cusType == 'vip') {//vip活动
                        htmlString += "<img src='" + basePath + n[0] + "'/> <span class='vip'><i></i></span>";
                        htmlString += "</div></dt>";
                        htmlString += "<dd><input type='hidden' value='" + n[2] + "'  class='" + n[2] + "' id='ppid'/>" +
                            "<input type='hidden' value='" + n[4] + "'  id='goodsID'/>" +
                            "<input type='hidden' value='" + (n[6] != null ? n[6] : "nullType") + "'  id='parentId'/>" +
                            "<h1>" + n[1] + "</h1>";
                        htmlString += "<p>销量<span>" + n[5] + "</span></p>";
                        htmlString += "<h2>￥<span>" + (n[8] * Pct).toFixed(2) + "</span></h2>";
                    } else {
                        //零售
                        htmlString += "<img src='" + basePath + n[0] + "'/>";
                        htmlString += "</div></dt>";
                        htmlString += "<dd><input type='hidden' value='" + n[2] + "'  class='" + n[2] + "' id='ppid'/>" +
                            "<input type='hidden' value='" + n[4] + "'  id='goodsID'/>" +
                            "<input type='hidden' value='" + (n[6] != null ? n[6] : "nullType") + "'  id='parentId'/>" +
                            "<h1>" + n[1] + "</h1>";
                        htmlString += "<p>销量<span>" + n[5] + "</span></p>";
                        htmlString += "<h2>￥<span>" + (n[3] * Pct).toFixed(2) + "</span></h2>";
                    }

                }
                htmlString += "<div class='span' >";
                if (mapat[n[4]] !== "" && mapat[n[4]] != null) {
                    htmlString += "<input type='button' class='gg' onclick='Specifications(event,this)' value='选规格'/>";
                } else {
                    htmlString += "<input type='button' class='jia' onclick='jia(event,this)' value='+'/>";
                    htmlString += "<h5 class='hh'>"
                        + map1[n[2]]
                        + "</h5>";
                    htmlString += "<input type='button' class='jian' onclick='jian(event,this)' value='-'/>";
                }
                htmlString += "</div><input type='submit' id='tijiao' style='display: none;'></dd></dl>";

            });

            htmlString += "</form>";

            //将拼接好的html字符串追加到body中
            $("#pp").append(htmlString);

            //处理菜的数量为0的情况
            $(".hh").each(function () {
                var value = $(this).text();
                if (value == 0) {
                    $(this).css("display", "none");
                    $(this).parent("div.span").find(
                        "input.jian")
                        .css("display", "none");

                }
            });

            /*getHeight();*/
            $(".food_alert").hide();
            $(".alert_food").hide();
        }
    });
    //查询购物车菜品
    $.ajax({
        url: basePath
        + "ea/restaurant/sajax_ea_findCar.jspa",
        type: "post",
        async: false,
        data: {
            "parentName": name,
            parentId: "0",
            ccompanyId: ccompanyId,
            companyId: companyId,
            posNum:posNum

        },
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            $(".last").removeClass("last");
            var Pct = 1;
            if (pageForm == null) {

                return;
            }
            var setsubsidize = member.setsubsidize;
            if (setsubsidize != null) {
                Pct = setsubsidize.totalPct / 100 + 1;
            }
            pagecount = pageForm.pageCount;
            var map1 = member.map1;
            var gapat = member.gapat;
            $.each(pageForm.list, function (i, n) {
                /*if(pageForm.list.length>0){*/
                var list = "";
                if (n[11] != null && n[11] != "" && n[9] != null && n[9] != "") {
                    if (n[11] == '00') {//促销活动

                        if (n[7] == "默认规格") {
                            list = "<li><input type='hidden'value='" + n[2] + "'  class='" + n[2] + "' id='ppid'/> <input type='hidden' value='" + n[4] + "'  id='goodsID'/><input type='hidden' value='" + (n[6] != null ? n[6] : "nullType") + "'  id='parentId'/><p class='name'style='line-height: 3.5rem; '>" + n[1] + "</p><p class='cash'>&yen;<span>" + (n[9] * Pct).toFixed(2) + "</span><a>" + (n[3] * Pct).toFixed(2) + "</a></p><div class='nub'><a class='min'>-</a><input class='nub_txt' type='text' value='" + n[8] + "' readonly='readonly' onfocus='this.blur()'><a style='background-color: #ffc958;' class='add'>+</a></div>";
                        } else {
                            var gh = n[7].split("+").join("");
                            list = "<li><input type='hidden'value='" + n[2] + "'  class='" + n[2] + "' id='ppid'/> <input type='hidden' value='" + n[4] + "'  id='goodsID'/><input type='hidden' value='" + (n[6] != null ? n[6] : "nullType") + "'  id='parentId'/><p class='name'>" + n[1] + "<br/><span class='guig' style='font-size:10px' value='" + n[7] + "' id='" + gh + "'>" + n[7] + "</span></p><p class='cash'>&yen;<span>" + (n[9] * Pct).toFixed(2) + "</span><a>" + (n[9] * Pct).toFixed(2) + "</a></p><div class='nub'><a class='min'>-</a><input class='nub_txt' type='text' value='" + n[8] + "' readonly='readonly' onfocus='this.blur()'><a style='background-color: #ffc958;' class='add'>+</a></div>";
                        }
                        activeStateUpdate(n[2]);
                    }
                    if (n[11] == '01') {//特价活动

                        if (n[7] == "默认规格") {
                            list = "<li><input type='hidden'value='" + n[2] + "'  class='" + n[2] + "' id='ppid'/> <input type='hidden' value='" + n[4] + "'  id='goodsID'/><input type='hidden' value='" + (n[6] != null ? n[6] : "nullType") + "'  id='parentId'/><p class='name'style='line-height: 3.5rem; '>" + n[1] + "</p><p class='cash'>&yen;<span>" + (n[9] * Pct).toFixed(2) + "</span><a>" + (n[3] * Pct).toFixed(2) + "</a></p><div class='nub'><a class='min'>-</a><input class='nub_txt' type='text' value='" + n[8] + "' readonly='readonly' onfocus='this.blur()'><a style='background-color: #ffc958;' class='add'>+</a></div>";
                        } else {
                            var gh = n[7].split("+").join("");
                            list = "<li><input type='hidden'value='" + n[2] + "'  class='" + n[2] + "' id='ppid'/> <input type='hidden' value='" + n[4] + "'  id='goodsID'/><input type='hidden' value='" + (n[6] != null ? n[6] : "nullType") + "'  id='parentId'/><p class='name'>" + n[1] + "<br/><span class='guig' style='font-size:10px' value='" + n[7] + "' id='" + gh + "'>" + n[7] + "</span></p><p class='cash'>&yen;<span>" + (n[9] * Pct).toFixed(2) + "</span><a>" + (n[9] * Pct).toFixed(2) + "</a></p><div class='nub'><a class='min'>-</a><input class='nub_txt' type='text' value='" + n[8] + "' readonly='readonly' onfocus='this.blur()'><a style='background-color: #ffc958;' class='add'>+</a></div>";
                        }
                        activeStateUpdate(n[2]);
                    }
                } else {
                    //超过活动时间，不展示活动价》判断活动状态改为 03:结束
                    var url = basePath + "ea/digitalmall/sajax_ea_activeTimeoutStateUpdate.jspa";
                    $.ajax({
                        url: url,
                        type: 'POST',
                        data: {"ppid": n[2]},
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
                    if (n[10] != null && n[10] != "") {//vip活动

                        if (n[7] == "默认规格") {
                            list = "<li><input type='hidden'value='" + n[2] + "'  class='" + n[2] + "' id='ppid'/> <input type='hidden' value='" + n[4] + "'  id='goodsID'/><input type='hidden' value='" + (n[6] != null ? n[6] : "nullType") + "'  id='parentId'/><p class='name'style='line-height: 3.5rem; '>" + n[1] + "</p><p class='cash'>&yen;<span>" + (n[10] * Pct).toFixed(2) + "</span><a>" + (n[10] * Pct).toFixed(2) + "</a></p><div class='nub'><a class='min'>-</a><input class='nub_txt' type='text' value='" + n[8] + "' readonly='readonly' onfocus='this.blur()'><a style='background-color: #ffc958;' class='add'>+</a></div>";
                        } else {
                            var gh = n[7].split("+").join("");
                            list = "<li><input type='hidden'value='" + n[2] + "'  class='" + n[2] + "' id='ppid'/> <input type='hidden' value='" + n[4] + "'  id='goodsID'/><input type='hidden' value='" + (n[6] != null ? n[6] : "nullType") + "'  id='parentId'/><p class='name'>" + n[1] + "<br/><span class='guig' style='font-size:10px' value='" + n[7] + "' id='" + gh + "'>" + n[7] + "</span></p><p class='cash'>&yen;<span>" + (n[10] * Pct).toFixed(2) + "</span><a>" + (n[10] * Pct).toFixed(2) + "</a></p><div class='nub'><a class='min'>-</a><input class='nub_txt' type='text' value='" + n[8] + "' readonly='readonly' onfocus='this.blur()'><a style='background-color: #ffc958;' class='add'>+</a></div>";
                        }

                    } else {
                        if (n[7] == "默认规格") {
                            list = "<li><input type='hidden'value='" + n[2] + "'  class='" + n[2] + "' id='ppid'/> <input type='hidden' value='" + n[4] + "'  id='goodsID'/><input type='hidden' value='" + (n[6] != null ? n[6] : "nullType") + "'  id='parentId'/><p class='name'style='line-height: 3.5rem; '>" + n[1] + "</p><p class='cash'>&yen;<span>" + (n[3] * Pct).toFixed(2) + "</span><a>" + (n[3] * Pct).toFixed(2) + "</a></p><div class='nub'><a class='min'>-</a><input class='nub_txt' type='text' value='" + n[8] + "' readonly='readonly' onfocus='this.blur()'><a style='background-color: #ffc958;' class='add'>+</a></div>";

                        } else {
                            var gh = n[7].split("+").join("");
                            list = "<li><input type='hidden'value='" + n[2] + "'  class='" + n[2] + "' id='ppid'/> <input type='hidden' value='" + n[4] + "'  id='goodsID'/><input type='hidden' value='" + (n[6] != null ? n[6] : "nullType") + "'  id='parentId'/><p class='name'>" + n[1] + "<br/><span class='guig' style='font-size:10px' value='" + n[7] + "' id='" + gh + "'>" + n[7] + "</span></p><p class='cash'>&yen;<span>" + (n[3] * Pct).toFixed(2) + "</span><a>" + (n[3] * Pct).toFixed(2) + "</a></p><div class='nub'><a class='min'>-</a><input class='nub_txt' type='text' value='" + n[8] + "' readonly='readonly' onfocus='this.blur()'><a style='background-color: #ffc958;' class='add'>+</a></div>";
                        }
                    }

                }

                $(".shop_footer .shop_car").append(list);
                $(".shop_footer .grid .cart .cart_").attr("style", "background-color: #ffc958;");
                $(".shop_footer .grid .goshop	ping").attr("style", "background-color: #ffc958;");
                $(".shop_footer .grid .goshopping p").text("去结算");
                $(".shop_footer .grid .goshopping p").attr("style", "color:#000;");
                $(".shop_footer .grid .cart .cakkrt_ img").attr("src", basePath + "images/contacts/restaurant/ico-cart.png");
                /* }*/
            });
        }

    });
    getNotType();//判断公司有无分类产品
    zong();
}
//未分类菜品
function getNotType() {
    var url = basePath
        + "ea/restaurant/sajax_ea_getNotTypeByProduct.jspa?companyId=" + companyId;
    $.ajax({
        url: url,
        type: "post",
        async: false,
        success: function (data) {
            var member = eval("(" + data + ")");
            var count = member.count;
            var temp = member.temp
            if (count.length > 0) {
                var str = "<li class='product' onclick='a();'><span class='nullType'>未分类"
                    + "</span><div class='num0 num2'><span id='span'>" + (temp.length != 0 ? temp[0][1] : 0) + "</span></div></li>";
                $("#product").append(str);

                $(".product").each(function () {
                    var i = $(this).find("span#span").text();
                    var name = $(this).find("span").eq(0).text();
                    if (i > 0) {
                        $(this).find("div.num0").css("display", "block");
                    }
                });
            }
        }
    })
}
//活动状态更改
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

function Specifications(event, obj) {
    var price = $(obj).parents("dd").find("h2").find("span").text();
    var name = $(obj).parents("dd").find("h1").text();
    var goodsID = $(obj).parents("dd").find("#goodsID").val();
    var ppid = $(obj).parents("dd").find("#ppid").val();
    var parentId = $(obj).parents("dd").find("#parentId").val();
    $.ajax({
        url: basePath
        + "ea/restaurant/sajax_ea_specifications.jspa",
        type: "post",
        async: false,
        data: {
            "goodsid": goodsID
        },
        datatype: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var t = member.map;
            var str = new Array();
            $(".food_alert").html("");
            str.push('<input type="hidden" value="' + ppid + '" class="' + ppid + '"id="ppid"/>');
            str.push('<input type="hidden" value="' + goodsID + '" id="goodsID"/>');
            str.push('<input type="hidden" value="' + parentId + '"  id="parentId"/>');
            str.push('<h5 class="title">' + name + '</h5>');
            str.push('<img src="' + basePath + "images/contacts/restaurant/ico_delete_.png" + '" id="delete">');
            str.push('<div class="spec">');
            $.each(t, function (i, teach) {
                str.push('<h5>' + i.substr(0, i.length - 1) + '</h5>');
                if (teach != null) {
                    str.push('<ul>');
                    var arry = teach.shift();
                    str.push('<li class="active" value="' + arry + '">' + arry + '</li>');
                    for (var j = 0; j < teach.length; j++) {
                        str.push('<li value="' + teach[j] + '" >' + teach[j] + '</li>');
                    }
                    str.push('</ul>');
                }

            });
            str.push('</div>');
            str.push('<div class="list">');
            str.push('<p class="money1">&yen;<span>' + price + '</span></p>');
            str.push('<input type="button" class="Shopping" value="加入购物车">');
            str.push('</div>');
            $(".food_alert").append(str.join(""));
        }
    });
    $(".alert_food").show();
    $(".food_alert").show();

}

/*规格弹框*/
$(document).on("click", ".food_alert #delete", function () {
    $(".alert_food").hide();
    $(".food_alert").hide();
});
$(document).on("click", ".food_alert .spec ul li", function () {
    $(this).addClass("active").siblings().removeClass("active");
});

$(document).on("click", ".Shopping", function () {
    var gu = $(".spec").children("ul").find(".active");
    var guig = "";
    for (var i = 0; i < gu.length; i++) {
        guig += gu[i].getAttribute("value") + "+";
    }
    if (guig == "") {
        confirm("请选择规格");
        return
    }

    $(".alert_food").hide();
    $(".food_alert").hide();
    var money = $(".money").text();
    var ppid = $(this).parent().siblings("#ppid").val();
    var $old = $("." + ppid).siblings(".span").find(".gg").prev("h5").html();
    if ($old == '99') {
        $new = $old;
    } else {
        $new = parseInt($old) + 1;
        if ($new == '99') {
            $("." + ppid).siblings(".span").find(".gg").css({
                "border": "1px solid rgba(0,0,0,0.3)",
                "color": "rgba(0,0,0,0.3)"
            });
        }
    }
    if ($new > '0') {
        $("." + ppid).siblings(".span").find(".gg").prev().css("display", "block");
        $("." + ppid).siblings(".span").find(".gg").prev().prev().css("display", "block");
    }
    $("." + ppid).siblings(".span").find(".gg").prev('h5').html($new);
    $("." + ppid).siblings(".span").find(".gg").prev().prev(".jian").css({
        "border": "1px solid #ff4800",
        "color": "#ff4800"
    });
    var number1 = $(".pro").find("span#span").text();

    number1 = parseInt(1) + parseInt(number1);
    if (number1 >= 0) {

        $(".pro").find(".num2").css("display", "block");
        $(".pro").find("span#span").text(number1);

    } else if (number1 == 0) {
        $(".pro").find(".num2").css("display", "none");
    }
    var number = $("." + ppid).siblings(".span").find(".hh").text();
    if(number==""||number==null){
        number=1;
    }
    var url = basePath
        + "ea/restaurant/sajax_ea_gateringOrders.jspa?cart.itemNum="
        + number + "&cart.pid=" + ppid;
    $.ajax({
        url: encodeURI(url),
        type: "post",
        async: true,
        data: {
            ccompanyId: ccompanyId,
            companyId: companyId,
            "stardard": guig.substr(0, guig.length - 1),
             posNum:posNum
        },
        dataType: "json",
    });
    // 计算总金额

    var mon = parseFloat($("." + ppid).siblings("h2").find("span").text())
        + parseFloat(money);
    $(".money").text(mon.toFixed(2));

    if (mon == "0") {
        $(".nomoney").css("display", "block");
        $(".goshopping").css("display", "none");
    } else if (mon != "0") {
        $(".nomoney").css("display", "none");
        $(".goshopping").css("display", "block");
    }

});
function zong() {
    var tId = $(".shop_footer .shop_car").children("li").find("#parentId");
    var partId = "";
    for (var i = 0; i < tId.length; i++) {
        partId += tId[i].value + ",";
    }
    var parentId = partId.split(",");
    var new_arr = [];
    for (var i = 0; i < parentId.length; i++) {
        var items = parentId[i];
        //判断元素是否存在于new_arr中，如果不存在则插入到new_arr的最后
        if ($.inArray(items, new_arr) == -1) {
            if (items != "" && items != null && items != "null" && items != " ") {
                new_arr.push(items);
            }

        }
    }
    var sum = 0;
    for (var i = 0; i < new_arr.length; i++) {
        var pare = new_arr[i];
        if (pare != "") {
            var number1 = $("." + pare).siblings().children().text();
            sum = parseInt(sum) + parseInt(number1);
            $(".cart_ .num0").children().html(sum);
            $(".cart_ .num0").css("display", "block");
        }
    }
}


//记录要返回的页面
function backUrl(){
    if(posNum!=null&&posNum!="") {
        if(localStorage!=null) {
            localStorage.setItem("backUrl", window.location.href);
        }
    }
}