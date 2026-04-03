$(document).ready(function () {
    //$("header").css("height", $(window).height() * 0.08 - 1 + "px");
    //$("header menu li").css("height", $(window).height() * 0.08 - 1 + "px");
    //$("header menu li").css("line-height", $(window).height() * 0.08 - 1 + "px");
    $("header").hide();
    $(".content_search").css("height", "auto");
    var menu_hei = $(window).height() - $(".content_search section").outerHeight(true);
    $(".content_search menu").css("height", menu_hei + "px");


    //调用方法ajax获取超市商品有关数据追加展示
    supermarketGoodsList();

    //查询购物车商品数量
    shoppingCartGoodsNum();

    //数量增加
    $(document).on('click', '.add_number', function (event) {
        //获取当前商品id,产品id及价格
        var goodsId = $(this).parent("p").children(".goodsId").val();
        var ppid = $(this).parent("p").children(".ppid").val();
        var goodsPrice = $(this).parent().parent().find("span").text();
        goodsname = $(this).parents("section").children("h2").text();
        price = $(this).parents("section").find("p").eq(0).find("span").text().replace("￥","");
        var sgrId = $(this).parents("li").attr("id");
        var totalnum =  $(this).parents("li").find('.number').val();
        if (typeof(goodsId) == "undefined" || goodsId == "" || goodsId == null) {
            return "";
        } else {
            //获取商品属性[sku]信息
            var url = basePath + "ea/smg/sajax_sm_getGoodsSKU.jspa";
            $.ajax({
                type: "POST",
                url: url,
                async: false,
                dataType: "json",
                data: {
                    "goodsId": goodsId,
                },
                success: function (data) {
                    var json = eval('(' + data + ')');
                    if (json.code == '400') {//该商品无sku信息
                        $("#hiddenNumber").val(400);
                    } else {//商品sku展示
                        //在触发弹窗的时候禁止页面滚动
                        document.body.addEventListener('touchmove', bodyScroll, false);
                        $('body').css({'position': 'fixed', "width": "100%"});

                        $("#hiddenNumber").val(200);
                        $(".pecifications").empty();
                        console.log(json)
                        var gsku = new Array();
                        var sku0 = json.sku0;
                        var sku1 = json.sku1;
                        gsku.push('<div class="clearfix">')
                        gsku.push('<img src=""/>')
                        gsku.push('<input type="hidden" class="ppId" name="" value="' + ppid + '"/>')
                        gsku.push('<p>' + goodsPrice + '</p>')
                        gsku.push('<div>')
                        gsku.push('<span>选择</span>  ')
                        if (sku0 != null && sku0.length > 0) {
                            gsku.push('<span>' + sku0[0][1] + ' <span class="sku1"></span></span>  ')
                        }
                        if (sku1 != null && sku1.length > 0) {
                            gsku.push('<span>' + sku1[0][1] + ' <span class="sku2"></span></span>')
                            $(".skuId").val(2);//赋值[表示该商品有两个sku属性];
                        }
                        gsku.push('</div>')
                        gsku.push('<img class="pecifications_del" src="' + basePath + 'images/unmannedsupermarket/pecifications_del.png"/>')
                        gsku.push('</div>')

                        if (sku0 != null && sku0.length > 0) {
                            gsku.push('<section class="clearfix">')
                            gsku.push('<h4>' + sku0[0][1] + '</h4>')
                            gsku.push('<div class="choice_01">')
                            for (var i = 0; i < sku0.length; i++) {
                                gsku.push('<p>' + sku0[i][2] + '</p>')
                            }
                            gsku.push('</div>')
                            gsku.push('</section>')
                        }

                        if (sku1 != null && sku1.length > 0) {
                            gsku.push('<section class="clearfix">')
                            gsku.push('<h4>' + sku1[0][1] + '</h4>')
                            gsku.push('<div class="choice_02">')
                            for (var i = 0; i < sku1.length; i++) {
                                gsku.push('<p>' + sku1[i][2] + '</p>')
                            }
                            gsku.push('</div>')
                            gsku.push('</section>')
                        }

                        gsku.push('<section class="clearfix">')
                        gsku.push('<p>购买数量</p>')
                        gsku.push('<div>')
                        gsku.push('<img class="Specifications_reduce" src="' + basePath + 'images/unmannedsupermarket/Specifications_reduce.png"/>')
                        gsku.push('<input type="number" class="purchase_quantity" name="" id="" disabled value="1"/>')
                        gsku.push('<img class="Specifications_add" src="' + basePath + 'images/unmannedsupermarket/Specifications_add.png"/>')
                        gsku.push('</div>')
                        gsku.push('</section>')
                        gsku.push('<input type="button" name=""  id="sure" value="确定"/>')
                        $(".pecifications").append(gsku.join(""));
                    }
                },
                error: function (data) {
                    alert("数据获取失败！");
                }
            });
            //该商品无sku信息,直接加入购物车
            if ($("#hiddenNumber").val() == 400) {
                //购物车加入动画
                var offset = $("#end").offset(); //获取元素坐标
                var addcar = $(this);
                var img = addcar.parent().parent().parent().parent("li.clearfix").find('div:first-of-type img').attr('src'); //获取图片地址
                var flyer = $('<img class="u-flyer" src="' + img + '" height="30">'); //创建移动元素 height可控制移动物大小
                flyer.fly({ //jq插件使用
                    start: {
                        left: event.pageX, //开始位置（必填）#fly元素会被设置成position: fixed
                        top: event.pageY //开始位置（必填）
                    },
                    end: {
                        left: offset.left + 10, //结束位置（必填）
                        top: offset.top + 10, //结束位置（必填）
                        width: 0, //结束时宽度
                        height: 0 //结束时高度
                    },
                    onEnd: function () { //结束回调
                        //$(".u-flyer").remove()
                    }
                });


                var num = $(this).siblings(".number").val();
                if (num <= 99) {
                    if (typeof(ppid) == "undefined" || ppid == "" || ppid == null) {
                        return ""
                    } else {
                        //商品无sku信息直接加入购物车
                        var stardard = "默认规格";//无sku信息[默认规格]
                        var amount = 1;//数量为1
                        addShoppingCart(ppid, stardard, amount,goodsname,price,sgrId,Number(totalnum)+1);
                        num++;
                        var num_shop = $("#num_shop").text();//购物车数量增加
                        $("#num_shop").text(num_shop++)
                    }
                } else {
                    num = 100;
                    alert("商品数量选择已达上限！")
                }
                $(this).siblings(".number").val(num);
                if (num >= 1) {
                    $(this).siblings(".number").show();
                }
                if (num >= 1) {
                    $(this).siblings(".reduce_number").show();
                }
                $("#num_shop").text(num_shop)//购物车数量改动
            }
            //展示商品sku信息
            if ($("#hiddenNumber").val() == 200) {
                //点击出现选项
                $(".purchase_quantity").val(1);
                var img = $(this).parent().parent().parent().parent("li.clearfix").find('div:first-of-type img').attr('src');
                $(".pecifications>div>img:first-of-type").attr("src", img);
                $(".pecifications").animate({
                    bottom: 0
                }, "normal");
            }

        }

    })
    //数量减少
    $(document).on('click', '.reduce_number', function () {
        var ppid = $(this).parent("p").children(".ppid").val();
        var num = $(this).siblings(".number").val();
        var sgrId = $(this).parents("li").attr("id");
        var totalnum =  $(this).parents("li").find('.number').val();
        if (num > 0) {
            if (typeof(ppid) == "undefined" || ppid == "" || ppid == null) {
                return ""
            } else {
                //商品减少1
                var stardard = "默认规格";//无sku信息[默认规格]
                var amount = 1;//数量为1
                reduceShoppingCart(ppid, stardard, amount,sgrId,Number(totalnum)-1);
                num--;
                var num_shop = $("#num_shop").text();//购物车数量增加
                $("#num_shop").text(num_shop--);
            }

        } else {
            num = 0
        }
        $(this).siblings(".number").val(num);
        if (num == 0) {
            $(this).siblings(".number").hide();
        }
        if (num <= 0) {
            $(this).hide();
        }
        $("#num_shop").text(num_shop)//购物车数量改动
    })


    //返回页面删除多余元素
    $("header").click(function () {
        $(".u-flyer").remove()
    })

//绑定滚动事件
    $(".content_search>menu").scroll(function () {
        var scroll_height = $(".content_search>menu li").outerHeight(true) * ($(".content_search>menu li").length - 0) - $(this).outerHeight(true);//计算可滚动距离0代表提前0个加载
        if ($(this).scrollTop() >= scroll_height) {
            if (pagenumber < pagecount) {
                t = setTimeout(function () {
                    if (pagenumber < pagecount) {
                        supermarketGoodsList();
                    }
                }, 1000);
            }
        }
    })


});
$("#top").click(function () {
    alert($(this).scrollTop())
    $(this).scrollTop(0)
})


function bodyScroll(event) {
    event.preventDefault();
}

function supermarketGoodsList() {
    //ajax获取超市商品有关数据追加展示
    var url = basePath + "ea/smg/sajax_sm_getSupermarketGoodsList.jspa";
    //获取搜索的商品名称
    var search = $("#supermarketGoodsName").val();
    clearTimeout(t);
    pagenumber++;
    $.ajax({
        url: encodeURI(url),
        type: "POST",
        async: false,
        data: {
            "pageForm.pageNumber": pagenumber,
            "goodsName": search,
            "ccompanyID": ccompanyID,
            "industryType": industryType,
            "lxType":lxType
        },
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            //console.log(member);
            var companyId = member.companyId;
            $("#companyId").val(companyId);
            var supList = member.pageForm;
            if (supList != null && supList.recordCount > 0) {
                pagenumber = supList.pageNumber;
                pagecount = supList.pageCount;
                var sup = new Array();
                var supList = supList.list;
                for (var i = 0, len = supList.length; i < len; i++) {
                    if(supList[i][10] != null && supList[i][10] != ""){//批发价
                        sup.push('<li class="clearfix">');
                        sup.push('<div>');
                        // if (supList[i][9] != null && supList[i][9] != "" && supList[i][7] != null && supList[i][7] != "") {
                        //     if (supList[i][9] == '00') {//促销活动
                        //         sup.push('<span class="cx"><i></i></span>')
                        //     }
                        //     if (supList[i][9] == '01') {//特价活动
                        //         sup.push('<span class="tj"><i></i></span>')
                        //     }
                        // } else {
                        //     if (supList[i][8] != null && supList[i][8] != "") {//vip活动
                        //         sup.push('<span class="vip"><i></i></span>')
                        //     } else {
                        //         //普通零售
                        //     }
                        // }
                        //if (posNum != null && posNum != "") {
                            sup.push('<a class="txt">');
                        // } else {
                        //     sup.push('<a href="' + basePath + 'ea/wfjshop/ea_doodsDetail.jspa?' +
                        //         'ppid=' + supList[i][0] + '&goodsid=' + supList[i][2] + '&companyId=' + companyId + '&ccompanyId=' + ccompanyID + '" class="txt">');
                        // }
                        sup.push('<img src="' + basePath + '' + supList[i][4] + '" alt="" />');
                        sup.push('</a>');
                        sup.push('</div>');
                        sup.push('<section>');
                        sup.push('<h2>');
                        //if (posNum != null && posNum != "") {
                            sup.push('<a  class="txt">' + supList[i][3] + '</a>');
                        // } else {
                        //     sup.push('<a href="' + basePath + 'ea/wfjshop/ea_doodsDetail.jspa?' +
                        //         'ppid=' + supList[i][0] + '&goodsid=' + supList[i][2] + '&companyId=' + companyId + '&ccompanyId=' + ccompanyID + '" class="txt">' + supList[i][3] + '</a>');
                        // }
                        sup.push('</h2>');
                        sup.push('<div class="clearfix">');
                        // if (supList[i][9] != null && supList[i][9] != "" && supList[i][7] != null && supList[i][7] != "") {
                        //
                        //     if (supList[i][6] == null || supList[i][6] == undefined || supList[i][6] == "") {
                        //         sup.push('<p><span>￥' + supList[i][7] + '</span></p>')
                        //     } else {
                        //         sup.push('<p><span>￥' + supList[i][7] + '</span>/' + supList[i][6] + '</p>')
                        //     }
                        //     activeStateUpdate(supList[i][1]);
                        //
                        // } else {
                        //     //超过活动时间，不展示活动价》判断活动状态改为 03:结束
                        //     var url = basePath + "ea/digitalmall/sajax_ea_activeTimeoutStateUpdate.jspa";
                        //     $.ajax({
                        //         url: url,
                        //         type: 'POST',
                        //         data: {"ppid": supList[i][0]},
                        //         async: true,
                        //         success: function (data) {
                        //             var member = eval("(" + data + ")");
                        //             if (member.code == '200') {
                        //                 //活动状态更改成功
                        //             } else {
                        //                 //活动状态更改异常
                        //             }
                        //         },
                        //         error: function (err) {
                        //             console.log('err')
                        //         }
                        //     })
                        //     if (supList[i][8] != null && supList[i][8] != "") {//vip活动
                        //         if (supList[i][6] == null || supList[i][6] == undefined || supList[i][6] == "") {
                        //             sup.push('<p><span>￥' + supList[i][8] + '</span></p>')
                        //         } else {
                        //             sup.push('<p><span>￥' + supList[i][8] + '</span>/' + supList[i][6] + '</p>')
                        //         }
                        //     } else {
                        //         if (supList[i][6] == null || supList[i][6] == undefined || supList[i][6] == "") {
                        //             sup.push('<p><span>￥' + supList[i][1] + '</span></p>')
                        //         } else {
                        //             sup.push('<p><span>￥' + supList[i][1] + '</span>/' + supList[i][6] + '</p>')
                        //         }
                        //     }
                        // }
                        if (supList[i][6] == null || supList[i][6] == undefined || supList[i][6] == "") {//规格不为空
                            sup.push('<p><span>￥' + supList[i][10] + '</span></p>')
                        } else {
                            sup.push('<p><span>￥' + supList[i][10] + '</span>/' + supList[i][6] + '</p>')
                        }

                        sup.push('<p>');
                        sup.push('<img class="reduce_number" src="' + basePath + 'images/unmannedsupermarket/img_4_10.png"/>');
                        sup.push('<input readonly="readonly" type="number" value="0" class="number"/>');
                        sup.push('<input type="hidden" value="' + supList[i][0] + '" class="ppid">')
                        sup.push('<input type="hidden" value="' + supList[i][2] + '" class="goodsId">')
                        sup.push('<img class="add_number" src="' + basePath + 'images/unmannedsupermarket/img_4_12.png"/>');
                        sup.push('</p>');
                        sup.push('</div>');
                        sup.push('</section>');
                        sup.push('</li>');

                        $("#supermarketGoodsBox").append(sup.join(""));
                        sup.length = 0;
                        if (typeof(companyId) == "undefined" || companyId == "" || companyId == null) {
                            return ""
                        } else {
                            var url = basePath + "ea/smg/sajax_sm_shoppingCartGoodsNumByPpid.jspa";
                            $.ajax({
                                url: url,
                                type: "POST",
                                data: {
                                    "companyId": companyId,
                                    "ppid": supList[i][0],
                                    posNum: posNum

                                },
                                async: false,
                                dataType: "json",
                                success: function (data) {
                                    var member = eval("(" + data + ")");
                                    var goodNum = member.goodNum;
                                    var sgrId = member.sgrId;
                                    var login = member.login;
                                    if (login == '200') {
                                        if (goodNum > 0) {//购物车里存在此商品，给商品数量赋值
                                            $("input[value='" + supList[i][0] + "']").parent('p').find('.number').val(goodNum);
                                            var a1 = $("input[value='" + supList[i][0] + "']").parent('p').find('.number').val();
                                        } else {
                                            var newNumber = $("input[value='" + supList[i][0] + "']").parent('p').find('.number').val();
                                            if (newNumber == 0) {
                                                $("input[value='" + supList[i][0] + "']").parent('p').find('.number').hide();
                                                $("input[value='" + supList[i][0] + "']").parent('p').find('.reduce_number').hide();
                                            }
                                        }
                                        if(posNum!=null&&posNum!="") {
                                            $("input[value='" + supList[i][0]+ "']").parents('li').attr("id", sgrId);
                                        }
                                    } else {//未登录,去登陆页面
                                        document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";
                                        return;
                                    }
                                },
                                error: function () {
                                    alert("商品数量获取失败!");
                                }
                            });

                        }
                    }
                }
            } else {
                console.log("没有搜索到您要找的商品！")
            }
        },
        error: function (data) {
            alert("数据获取失败！");
        }

    });
}

//商品加入购物车
function addShoppingCart(ppid, stardard, amount,goodsname,price,sgrId,totalnum) {
        if(posNum!=null&&posNum!="") {
            if(sgrId==""){
                sgrId = joinOrReduceStardard(sgrId,amount,posNum,ppid,companyId,amount,"",stardard,price,goodsname,relateID)
            }else{
                var idli = sgrId;
                if(Number(totalnum)>1) {
                    joinOrReduceGoods(sgrId, totalnum);
                }else{
                    sgrId =  joinScanRecord(posNum, ppid, companyId, totalnum, "", stardard, price, goodsname, relateID,idli);
                }
            }
        }
    var url = basePath + "ea/smg/sajax_sm_addShoppingCart.jspa";
    $.ajax({
        type: "POST",
        url: url,
        async: false,
        dataType: "json",
        data: {
            "ppid": ppid,
            "stardard": stardard,
            "itemNum": amount,
            "pos": "0" ,//正常购物车[0]
             posNum:posNum,
            totalNum:amount,
            sgrId:sgrId,
            relateID:relateID
        },
        success: function (data) {
            var json = eval('(' + data + ')');
            // console.log(json);
            if (json.login == "login") {
                document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";
                return;
            }
            /*if (json.sect == '1') {
             $("#ptppid").val("");
             cxpcart = "1";
             alert("成功加入购物车");
             }
             if (json.sect == '2') {
             alert("已加入购物车");
             }*/
        },
        error: function (data) {
            //商品加入购物车失败
            alert("数据处理失败!");
            return;
        }
    });
}
//购物车商品数量减少1
function reduceShoppingCart(ppid, stardard, amount,sgrId,totalnum) {
        if(posNum!=null&&posNum!="") {
            joinOrReduceGoods(sgrId, totalnum);
        }
    var url = basePath + "ea/smg/sajax_sm_reduceShoppingCart.jspa";
    $.ajax({
        type: "POST",
        url: url,
        async: false,
        dataType: "json",
        data: {
            "ppid": ppid,
            "stardard": stardard,
            "itemNum": amount,
            "totalNum":amount,
            "pos": "0",//正常购物车[0]
            posNum: posNum
        },
        success: function (data) {
            var json = eval('(' + data + ')');
            // console.log(json);
            if (json.login == "login") {
                document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";
                return;
            }
            /*if (json.sect == '1') {
             $("#ptppid").val("");
             cxpcart = "1";
             alert("成功加入购物车");
             }
             if (json.sect == '2') {
             alert("已加入购物车");
             }*/
        },
        error: function (data) {
            //购物车商品数量更改失败
            alert("数据处理失败!");
        }
    });
}
//去超市购物车页面展示购物车商品
function getShoppingCar() {
    //判断是否是大屏终端
    //var posNum = Android.forAndroidDeviceId();
    var url = "";
    if (posNum == null || posNum == "") {//跳转小屏
        url = basePath + "ea/wholesaleMall/ea_shoppingCartList.jspa?ccompanyID=" + ccompanyID;
    } else {//跳转大屏
        posNum = isExistPosNum(posNum);
        //url = basePath + "ea/wholesaleMall/ea_shoppingCartForDpList.jspa?posNum=" + posNum + "&ccompanyID=" + ccompanyID;
        url = basePath + "ea/sm/ea_qdlist.jspa?posNum=" + posNum + "&ccompanyID=" + ccompanyID+"&relateID="+relateID+"&lxType=1" ;
    }
    window.location.href = url;
}
//判断是否是终端机器
function isExistPosNum(posNum){
    var url = basePath + "ea/smg/sajax_sm_isExistPosNum.jspa";
    $.ajax({
        url : url,
        type : "get",
        dataType : "json",
        async:false,
        data : {
            posNum:posNum
        },
        success : function(data) {
            var m = eval("(" + data + ")");
            var result = m.result;
            if(result!="0"){
                posNum = "";
            }
        },
        error : function(data) {
            // alert("验证失败");
        }
    });
    return posNum;
}
//查询购物车商品数目
function shoppingCartGoodsNum() {
    var companyId = $("#companyId").val();
    if (typeof(companyId) == "undefined" || companyId == "" || companyId == null) {
        return ""
    } else {
        var url = basePath + "ea/smg/sajax_sm_shoppingCartGoodsNum.jspa";
        $.ajax({
            url: url,
            type: "POST",
            data: {
                "companyId": companyId,
                "ccompanyID": ccompanyID,
                posNum: posNum
            },
            async: false,
            dataType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                var goodNum = member.goodNum;
                relateID = member.relateID;
                $("#num_shop").text(goodNum);
            },
            error: function () {
                alert("查询购物车商品数量失败!");
            }
        });

    }
}


//规格相关操作
$(document).on('click', '.choice_01 p', function () {
    $(".choice_01 p").removeClass("active");
    $(this).addClass("active");
    $(".sku1").html($(this).html());
})
$(document).on('click', '.choice_02 p', function () {
    $(".choice_02 p").removeClass("active");
    $(this).addClass("active");
    $(".sku2").html($(this).html());
})
//数量增加
$(document).on('click', '.Specifications_add', function () {
    var num = $(this).siblings(".purchase_quantity").val();
    if (num <= 99) {
        num++;
    } else {
        num = 100
    }
    $(this).siblings(".purchase_quantity").val(num);
})
//数量减少
$(document).on('click', '.Specifications_reduce', function () {
    var num = $(this).siblings(".purchase_quantity").val();
    if (num > 1) {
        num--;
    } else {
        num = 1
    }
    $(this).siblings(".purchase_quantity").val(num);
})
var pecifications_hei = $(".pecifications").outerHeight(true);
$(".pecifications").css("bottom", -pecifications_hei + "px")
$(document).on('click', '.pecifications_del', function () {
    //关闭弹框时开启页面滚动
    document.body.removeEventListener('touchmove', bodyScroll, false);
    $("body").css({"position": "initial", "height": "auto"});

    var pecifications_hei = $(".pecifications").outerHeight(true);
    $(".pecifications").animate({
        bottom: -pecifications_hei
    }, "normal");
})
$(document).on('click', '#sure', function () {
    //关闭弹框时开启页面滚动
    document.body.removeEventListener('touchmove', bodyScroll, false);
    $("body").css({"position": "initial", "height": "auto"});

    if ($(".skuId").val() == 2) {//该商品有两个sku属性
        if ($(".sku1").html() == null || $(".sku1").html() == "" || $(".sku2").html() == null || $(".sku2").html() == "") {
            alert("请选择商品属性！");
            return "";
        }
    } else {//该商品有1个sku属性
        if ($(".sku1").html() == null || $(".sku1").html() == "") {
            alert("请选择商品属性！");
            return "";
        }
    }
    var ppid = $(this).parent(".pecifications").find(".ppId").val();
    if (typeof(ppid) == "undefined" || ppid == "" || ppid == null) {
        return ""
    } else {
        var num = parseFloat($(this).prev().find(".purchase_quantity").val());
        if (num > 0) {
            var txt = "";
            $("#sure").parent(".pecifications").find("p.active,h4").each(function (i, j) {
                txt += $(this).text() + ":"
            });
            var result = txt.split(":");
            for (var i = 0; i < result.length - 1; i++) {
                result[i];
            }
            for (var i = 0; i < $(".pecifications section").length - 1; i++) {
                var results = result.splice(0, 2);
                $("#specifications").append("<li>" + results.join(":") + "</li>");
            }
            var stardard = ""
            $('#specifications li').each(function (index) {
                //循环获取每个li元素的值
                stardard = stardard + "," + $(this).text();
            });
            stardard = stardard.substring(1);//商品规格[sku信息]
            addShoppingCart(ppid, stardard, num,goodsname,price,"","");
            var num_shop = $("#num_shop").text();//购物车数量增加
            var num_shop = Number(num_shop) + num;//获取超市购物车商品总数量
            $("#num_shop").text(num_shop)//购物车数量改动

        } else {
            return ""
        }
        var pecifications_hei = $(".pecifications").outerHeight(true);
        $(".pecifications").animate({
            bottom: -pecifications_hei
        }, "normal");
        $("#specifications").empty()

    }

})
/*//定位实现
 function getLocation() {
 var u = window.navigator.userAgent;
 var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
 var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
 if (isAndroid == true) {
 console.log("安卓");
 var collection = Android.callgetAddrstr();//调用安卓接口
 if (collection != "-1"){
 var ar = collection.split(",");
 address = ar[0];//当前位置
 $("#getLocation").text(address.substring(address.indexOf("市")+1));
 } else {
 $("#getLocation").text("定位失败");
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
 address = ar[0];//当前位置
 $("#getLocation").text(address.substring(address.indexOf("市")+1));
 } else {
 $("#getLocation").text("定位失败");
 }
 }*/
//商品搜索
function supermarketGoodsSearch() {
    //清空元素中内容
    $("#supermarketGoodsBox").empty();
    pagenumber = 0;
    //调用方法ajax获取超市商品有关数据追加展示
    supermarketGoodsList();

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


//第一次加入购物车
function  joinScanRecord(posNum,ppID,companyID,num,barCode,stardard,price,goodsname,relateID,idli){
    var url = basePath + "ea/sm/sajax_ea_joinScanRecord.jspa";
    var sgrId  = "";
    $.ajax({
        type: "POST",
        url: url,
        async: false,
        dataType: "json",
        data: {
            posNum:posNum,
            "scanGoods.posNum":posNum,
            "scanGoods.pid":ppID,
            "scanGoods.companyId":companyID,
            "scanGoods.itemNum":num,
            "scanGoods.barCode":barCode,
            "scanGoods.stardard":stardard,
            "scanGoods.price":price,
            "scanGoods.goodsName":goodsname,
            "scanGoods.relateID":relateID,

        },
        success: function (data) {
            var me = eval("("+data+")");
            sgrId = me.sgrId;
            $("li#"+idli).attr("id",sgrId);

        },
        error: function (data) {
            console.log("扫码记录失败");
        }



    });
    return sgrId;

}

//第二次以后
function  joinOrReduceGoods(sgrId,itemNum){
    var url = basePath + "ea/sm/sajax_ea_joinOrReduceGoods.jspa";
    $.ajax({
        type: "POST",
        url: url,
        async: false,
        dataType: "json",
        data: {
            sgrId:sgrId,
            itemNum:itemNum
        },
        success: function (data) {


        },
        error: function (data) {
            console.log("扫码记录失败");
        }
    });


}



//选规格
function  joinOrReduceStardard(sgrId,itemNum,posNum,ppID,companyID,num,barCode,stardard,price,goodsname,relateID){
    var id = "";
    var url = basePath + "ea/sm/sajax_ea_joinOrReduceGoods.jspa";
    $.ajax({
        type: "POST",
        url: url,
        async: false,
        dataType: "json",
        data: {
            sgrId:sgrId,
            itemNum:itemNum,
            "scanGoods.posNum":posNum,
            "scanGoods.pid":ppID,
            "scanGoods.companyId":companyID,
            "scanGoods.itemNum":itemNum,
            "scanGoods.barCode":barCode,
            "scanGoods.stardard":stardard,
            "scanGoods.price":price,
            "scanGoods.goodsName":goodsname,
            "scanGoods.relateID":relateID,
        },
        success: function (data) {
            var me = eval("("+data+")");
            id  = me.id;

        },
        error: function (data) {
            console.log("扫码记录失败");
        }
    });

    return id;
}