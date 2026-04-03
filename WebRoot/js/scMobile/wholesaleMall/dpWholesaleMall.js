if(dpFlag){
    // $("#ttsw_dp_gsmc").html('批发商城');
    // $("#ttsw_html").addClass("ttsw_Html_dp_css");
    // $(".ttsw_dpzj_hide").hide();

    // var gsHtml = '批发商城'
    //     +'<img src="'+basePath+'images/scMobile/wholesaleMall/qiehuan.png" />'
    //     +'<span onclick="toBussList();">'+companyName+'</span>'
    // $("#ttsw_dp_gsmc").html(gsHtml);
    $("#ttsw_ts_hide").hide();//隐藏提示
    posNum = isExistPosNum(posNum);
    console.log("大屏");
    //首先，建立一个函数
    function bodyScroll(event) {
        event.preventDefault();
    }
    //保存当前连接
    $(function () {
        FastClick.attach(document.body)
        backUrl();
    });
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
                    // if(code.length<=13){
                    //     document.location.href = basePath+"ea/sm/ea_qdlist.jspa?posNum="+posNum+"&ccompanyID="+ccompanyID+"&barCode="+code;
                    //
                    // }
                }
                code = "";
            }
        }
    }
    $(document).ready(function () {

        /*   try {
         //获取当前位置，经纬度
         getLocation();
         } catch (e) {
         console.log(e.name + ": " + e.message);
         }*/
        //调用方法ajax获取超市商品，及商品分类有关数据追加展示
        allGoodsClassifyList()
        oneGoodsClassifyList();
        //获取一级菜单商品分类默认选择的第一个值
        var codeId = $("#ttsw_one_goods_Classify li:first-child input").val();
        //点击全部时直接显示第三级数据
        if(codeId == "all"){
            threeGoodsClassifyList(null);
        }
        //非空校验
        if (typeof(codeId) == "undefined" || codeId == "" || codeId == null) {
            //return "";
        } else {
            //获取商品二级菜单
            twoGoodsClassifyList(codeId);
        }
        //获取二级菜单商品分类默认选择的第一个值
        var codeId = $("#ttsw_two_goods_Classify li:first-child input").val();
        //非空校验
        if (typeof(codeId) == "undefined" || codeId == "" || codeId == null) {
            //return "";
        } else {
            //获取超市相应商品
            threeGoodsClassifyList(codeId);
        }
        //查询购物车商品数量
        shoppingCartGoodsNum();
        var offset = $("#end").offset(); //获取元素坐标

        $(document).on('click', '.add_number', function (event) {

            //获取当前商品id,产品id及价格
            var goodsId = $(this).parent("p").children(".goodsId").val();
            var ppid = $(this).parent("p").children(".ppid").val();
            goodsname = $(this).parents("section").children("h2").text();
            if($(this).parents("li").find(".vip").length!=0){
                price = $(this).parents("li").find(".yj").val();
            }else{
                price = $(this).parents("li").find("p").eq(0).find("span").text().replace("￥","");
            }
            var sgrId = $(this).parents("li").attr("id");
            var totalnum =  $(this).parents("li").find('.number').val();
            var goodsPrice = $(this).parent().parent().find("span").text();
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
                            //之后在触发弹窗的时候禁止页面滚动
                            document.body.addEventListener('touchmove', bodyScroll, false);
                            $('body').css({'position': 'fixed', "width": "100%"});

                            $("#hiddenNumber").val(200);
                            $(".pecifications").empty();
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
                                $(".skuId").val(2);//赋值[表示该商品有两个sku属性]
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
                    var addcar = $(this);
                    var img = addcar.parent().parent().parent().parent("li.clearfix").find('div:first-of-type img').attr('src'); //获取图片地址
                    var flyer = $('<img class="u-flyer" src="' + img + '" height="30">'); //创建移动元素 height可控制移动物大小
                    flyer.fly({ //jq插件使用
                        start: {
                            left: event.pageX, //开始位置（必填）#fly元素会被设置成position: fixed
                            top: event.pageY - getScrollTop() //开始位置（必填）
                        },
                        end: {
                            left: offset.left + 10, //结束位置（必填）
                            top: offset.top + 10, //结束位置（必填）
                            width: 0, //结束时宽度
                            height: 0 //结束时高度
                        },
                        onEnd: function () { //结束回调
                            if ($(".u-flyer").length >= 7) {
                                $(".u-flyer").not(":last").remove()
                            }
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
                            //未添加参数方法
                           // addShoppingCart(ppid, stardard, amount);
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
        $(document).on('click', '.reduce_number', function (event) {
            var ppid = $(this).parent("p").children(".ppid").val();
            var sgrId = $(this).parents("li").attr("id");
            var totalnum =  $(this).parents("li").find('.number').val();
            var num = $(this).siblings(".number").val();
            if (num > 0) {
                if (typeof(ppid) == "undefined" || ppid == "" || ppid == null) {
                    return ""
                } else {
                    //商品减少1
                    var stardard = "默认规格";//无sku信息[默认规格]
                    var amount = 1;//数量为1
                    //reduceShoppingCart(ppid, stardard, amount);
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
        //tab标签 垂直
        $(document).on('click', '.element li', function () {
            $(this).parent(".element").find("li").removeClass("active");
            $(this).addClass("active");
        })
        $(document).on('click', '#ttsw_one_goods_Classify_All li', function () {
            //关闭弹框时开启页面滚动
            // document.body.removeEventListener('touchmove', bodyScroll, false);
            // $("body").css({"position": "initial", "height": "auto"});
            $("body").css("overflow", "auto");
            $(".all_classification").css("height", "0");
            //alert(1);
        })
        $(document).on('click', '.all_classification menu li', function () {
            var scrleft1 = $(".tab_level_father li:first-of-type").outerWidth(true);
            var scrleft2 = $(".tab_level_father li").eq(2).outerWidth(true);
            scrleft = scrleft1 + (scrleft2 * ($(this).index() - 1)) - 15;
            $(".tab_level_father").parent().scrollLeft(scrleft);
            $(".tab_level_father li").eq($(this).index()+1).trigger('click');
        })

        //绑定下拉刷新页面
        $(".box_right").scroll(function () {
            //var scroll_height = $(document).height() - $(window).height();//计算可滚动距离
            var scroll_height = $(".box_right menu").outerHeight(true) - $(this).outerHeight(true) - ($(".box_right menu li:first-of-type").outerHeight(true) * 6);
            //滑动到底部自动加载下一页内容
            if ($(window).scrollTop() >= scroll_height) {
                if (pagenumber < pagecount) {
                    var codeId = $(".active").children(".codeId").val();
                    t = setTimeout(function () {
                        if (pagenumber < pagecount) {
                            threeGoodsClassifyList(codeId)
                        }
                    }, 1000);
                }
            }
        })
    });

    //获取超市商品全部分类
    function allGoodsClassifyList() {
        //获取超市商品全部分类
        var url = basePath + "ea/smg/sajax_sm_getGoodsClassify.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "POST",
            async: true,
            data: {
                "codePID": "scode20190415raqvqk3uvs0000000762",//物类id[该超市所有商品分类的父类id]
                "ccompanyID": ccompanyID,
            },
            dataType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                //$("#allGoodsClassify").empty();
                $("#ttsw_one_goods_Classify_All").empty();
                var gClassList = member.goodsClassifyList;
                if (gClassList != null && gClassList.length > 0) {
                    var sup = new Array();
                    for (var i = 0, len = gClassList.length; i < len; i++) {
                        sup.push('<li class="ttsw_one_li_active">');
                        sup.push('<img src="' + basePath + '' + gClassList[i][2] + '"/>');
                        sup.push('<p>' + gClassList[i][1] + '</p>');
                        sup.push('<input type="hidden" class="codeId" value="' + gClassList[i][0] + '">');
                        sup.push('</li>');
                    }
                    //$("#allGoodsClassify").append(sup.join(""));
                    $("#ttsw_one_goods_Classify_All").append(sup.join(""));
                } else {
                    console.log("没有获取到该超市的商品分类！")
                }
            },
            error: function (data) {
                alert("数据获取失败！");
            }

        });
    }
    //商品一级菜单展示
    function oneGoodsClassifyList() {
        //获取超市一级菜单商品分类
        var url = basePath + "ea/smg/sajax_sm_getGoodsClassify.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "POST",
            async: false,
            data: {
                "codePID": "scode20190415raqvqk3uvs0000000762",//物类id[该超市所有商品分类的父id]
                "ccompanyID": ccompanyID,
            },
            dataType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                //$("#oneGoodsClassify").empty();
                $("#ttsw_one_goods_Classify").empty();

                var gClassList = member.goodsClassifyList;
                if (gClassList != null && gClassList.length > 0) {
                    var sup = new Array();
                    sup.push('<menu class="clearfix tab_level_father" >');
                    for (var i = 0, len = gClassList.length; i < len; i++) {
                        if (i == 0) {
                            sup.push('<li class="ttsw_one_li_active active">');
                            /*sup.push('<a onclick="threeGoodsClassifyList(null)">全部</a>');*/
                            sup.push('<a>全部</a>');
                            sup.push('<input type="hidden" class="codeId" value="all">');
                            sup.push('</li>');

                            sup.push('<li class="ttsw_one_li_active ">');
                            sup.push('<a>' + gClassList[i][1] + '</a>');
                            sup.push('<input type="hidden" class="codeId" value="' + gClassList[i][0] + '">');
                            sup.push('</li>');
                        } else {
                            sup.push('<li class="ttsw_one_li_active ">');
                            sup.push('<a>' + gClassList[i][1] + '</a>');
                            sup.push('<input type="hidden" class="codeId" value="' + gClassList[i][0] + '">');
                            sup.push('</li>');
                        }
                    }
                    sup.push('</menu>');
                   // $("#oneGoodsClassify").append(sup.join(""));
                    $("#ttsw_one_goods_Classify").append(sup.join(""));
                } else {
                    console.log("没有获取到该超市的一级商品分类！")
                    alert("全球批发城配送信息交易平台正在拔地升启！");
                    var sup11 = new Array();
                    sup11.push('<menu class="clearfix tab_level_father" >');
                    sup11.push('<li class="ttsw_one_li_active">');
                    sup11.push('<a onclick="toGoodsSearch()">全部</a>');
                    sup11.push('<input type="hidden" class="codeId" value="">');
                    sup11.push('</li>');
                    sup11.push('</menu>');
                    // $("#oneGoodsClassify").append(sup.join(""));
                    $("#ttsw_one_goods_Classify").append(sup11.join(""));
                    threeGoodsClassifyList(null);
                    //修改样式将三级产品列表向右看齐
                    $(".ttsw_wfl").addClass("div-ones")
                }
            },
            error: function (data) {
                alert("数据获取失败！");
            }

        });
    }
    //商品二级菜单展示
    function twoGoodsClassifyList(codeId) {
        //获取超市二级菜单商品分类
        var url = basePath + "ea/smg/sajax_sm_getGoodsClassify.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "POST",
            async: false,
            data: {
                "codePID": codeId,
                "ccompanyID": ccompanyID,
            },
            dataType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                //$("#twoGoodsClassify").empty();
                $("#ttsw_two_goods_Classify").empty();
                var gClassList = member.goodsClassifyList;
                if (gClassList != null && gClassList.length > 0) {
                    var sup = new Array();
                    sup.push('<menu class="element">');
                    for (var i = 0, len = gClassList.length; i < len; i++) {
                        if (i == 0) {
                            sup.push('<li class="ttsw_two_li_active active">');
                            sup.push('<a>' + gClassList[i][1] + '</a>');
                            sup.push('<input type="hidden" class="codeId" value="' + gClassList[i][0] + '">');
                            sup.push('</li>');
                        } else {
                            sup.push('<li class="ttsw_two_li_active">');
                            sup.push('<a>' + gClassList[i][1] + '</a>');
                            sup.push('<input type="hidden" class="codeId" value="' + gClassList[i][0] + '">');
                            sup.push('</li>');
                        }
                    }
                    sup.push('</menu>');
                    //$("#twoGoodsClassify").append(sup.join(""));
                    $("#ttsw_two_goods_Classify").append(sup.join(""));
                } else {
                    console.log("没有获取到商品分类！")
                }
            },
            error: function (data) {
                alert("数据获取失败！");
            }

        })
    }
    //商品(三级菜单)展示
    function threeGoodsClassifyList(codeId) {
        clearTimeout(t);
        pagenumber++;
        //商品名称[搜索条件]
        var goodsName = $("#ttsw_search_id").val();
        var url = basePath + "ea/smg/sajax_sm_getSupermarketGoodsList.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "POST",
            async: false,
            data: {
                "pageForm.pageNumber": pagenumber,
                "goodsType": codeId,
                "ccompanyID": ccompanyID,
                "goodsName": goodsName,
                "industryType": industryType,
                "lxType":"1"
            },
            dataType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                var pageForm = member.pageForm;
                var companyId = member.companyId;
                $("#companyId").val(companyId);
                if (pageForm != null && pageForm.recordCount > 0) {
                    var gGoodslist = pageForm.list;
                    pagenumber = pageForm.pageNumber;
                    pagecount = pageForm.pageCount;
                    var goodsList = new Array();

                    if(codeId == null || codeId == ""){//一级选中
                        $(".ttsw_one_li_active").removeClass("active");
                        $("#ttsw_two_goods_Classify").empty();
                        //修改样式将三级产品列表向右看齐
                        $(".ttsw_wfl").addClass("div-ones");
                    }else{
                        $(".ttsw_wfl").removeClass("div-ones");
                    }

                    for (var i = 0; i < gGoodslist.length; i++) {
                        var goods = gGoodslist[i];
                        if(goods[10] != null && goods[10] != ""){//批发价
                            goodsList.push('<li class="clearfix" class="oods[1]">')
                            goodsList.push('<div>')
                            goodsList.push('<a class="txt">')
                            goodsList.push('<img src="' + basePath + '' + goods[4] + '" alt="" />');
                            goodsList.push('</a>')
                            goodsList.push('</div>')
                            goodsList.push('<section>')
                            goodsList.push('<h2><a class="txt">' + goods[3] + '</a></h2><input type="hidden"  value="'+goods[1]+'" class="yj"/>')
                            goodsList.push('<div class="clearfix">')
                            if (goods[6] == null || goods[6] == undefined || goods[6] == "") {//规格不为空
                                goodsList.push('<p><span>￥' + goods[10] + '</span></p>')
                            } else {
                                goodsList.push('<p><span>￥' + goods[10] + '</span>/' + goods[6] + '</p>')
                            }
                            goodsList.push('<p>')
                            goodsList.push('<a class="reduce_number" style="display: inline-block; float: left">')
                            goodsList.push('<img src="' + basePath + 'images/unmannedsupermarket/img_4_10.png">')
                            goodsList.push('</a>')
                            goodsList.push('<input readonly="readonly" type="number" value="0" class="number">')
                            goodsList.push('<input type="hidden" value="' + goods[0] + '" class="ppid">')
                            goodsList.push('<input type="hidden" value="' + goods[2] + '" class="goodsId">')
                            goodsList.push('<a  class="add_number">')
                            goodsList.push('<img src="' + basePath + 'images/unmannedsupermarket/img_4_12.png">')
                            goodsList.push('</a>')
                            goodsList.push('</p>')
                            goodsList.push('</div>')
                            goodsList.push('</section>')
                            goodsList.push('</li>')
                            $("#ttsw_three_goods_Classify").append(goodsList.join(""));
                            //购物车
                            goodsList.length = 0;
                            if (typeof(companyId) == "undefined" || companyId == "" || companyId == null) {
                                return ""
                            } else {
                                var url = basePath + "ea/smg/sajax_sm_shoppingCartGoodsNumByPpid.jspa";
                                $.ajax({
                                    url: url,
                                    type: "POST",
                                    data: {
                                        "companyId": companyId,
                                        "ppid": goods[0],
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
                                                $("input[value='" + goods[0] + "']").parent('p').find('.number').val(goodNum);
                                                var a1 = $("input[value='" + goods[0] + "']").parent('p').find('.number').val();
                                            } else {
                                                var newNumber = $("input[value='" + goods[0] + "']").parent('p').find('.number').val();
                                                if (newNumber == 0) {
                                                    $("input[value='" + goods[0] + "']").parent('p').find('.number').hide();
                                                    $("input[value='" + goods[0] + "']").parent('p').find('.reduce_number').hide();
                                                }
                                            }
                                            if(posNum!=null&&posNum!="") {
                                                $("input[value='" + goods[0] + "']").parents('li').attr("id", sgrId);
                                            }
                                        } else {//未登录,去登陆页面
                                            document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";
                                            return;
                                        }
                                    },
                                    error: function () {
                                        console.log("商品数量获取失败!");
                                    }
                                });

                            }
                        }
                    }

                } else {
                    console.log("没有查询到商品");
                }
            },
            error: function (data) {
                console.log("数据获取失败！");
            }

        })
    }
    //获取二级菜单商品分类及相应商品
    $(document).on('click', '#ttsw_one_goods_Classify li', function () {
        //初始化选中状态
        $(".ttsw_one_li_active").removeClass("active");
        $(this).addClass("active");
        $(".box_right").scrollTop(0);
        //获取当前选择的一级菜单商品分类id
        var codeId = $(this).children(".codeId").val();
        //点击全部时直接显示第三级数据
        if(codeId == "all"){
            threeGoodsClassifyList(null);
            return false;
        }
        //获取二级菜单商品分类
        twoGoodsClassifyList(codeId);
        //获取二级菜单商品分类默认选择的第一个id值
        var codeId = $("#ttsw_two_goods_Classify li:first-child input").val();
        $("#ttsw_three_goods_Classify").empty();
        if (typeof(codeId) == "undefined" || codeId == "" || codeId == null) {
            // return "";
        } else {
            pagenumber = 0
            //获取超市相应商品
            threeGoodsClassifyList(codeId);
        }
    })
    //获取超市相应商品(三级菜单)
    $(document).on('click', '#ttsw_two_goods_Classify li', function () {
        //初始化选中状态
        $(".ttsw_two_li_active").removeClass("active");
        $(this).addClass("active");
        $(".box_right").scrollTop(0);
        //获取当前选择的二级菜单商品分类id
        var codeId = $(this).children(".codeId").val();
        $("#ttsw_three_goods_Classify").empty();
        pagenumber = 0
        //获取超市相应商品
        threeGoodsClassifyList(codeId);
    })


    //商品加入购物车
    //function addShoppingCart(ppid, stardard, amount) {
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
                }}
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
                "pos": "0" ,//正常购物车[0]，
                posNum:posNum,
                totalNum:amount,
                sgrId:sgrId,
                relateID:relateID
            },
            success: function (data) {
                var json = eval('(' + data + ')');
                if (json.login == "login") {
                    document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";
                    return;
                }
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
    //function reduceShoppingCart(ppid, stardard, amount) {
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
                "pos": "0", //正常购物车[0]
                posNum: posNum
            },
            success: function (data) {
                var json = eval('(' + data + ')');
                if (json.login == "login") {
                    document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";
                    return;
                }
            },
            error: function (data) {
                //购物车商品数量更改失败
                alert("数据处理失败!");
            }
        });
    }
    //去超市购物车页面展示购物车商品
    function getShoppingCar() {
        if (posNum == null || posNum == "") {
            var companyId = $("#companyId").val();
            if (typeof(companyId) == "undefined" || companyId == "" || companyId == null) {
                return ""
            } else {
                location.href = basePath + "ea/wfjshop/ea_getcity.jspa?state=0&companyId=" + companyId;
            }
        } else {
            document.location.href = basePath + "ea/sm/ea_qdlist.jspa?posNum=" + posNum + "&ccompanyID=" + ccompanyID+"&relateID="+relateID;

        }


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
                console.log('-----'+stardard);
                stardard = stardard.substring(1);//商品规格[sku信息]
                //addShoppingCart(ppid, stardard, num)
                addShoppingCart(ppid, stardard, num,goodsname,price,"","")
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


    //超市商品搜索
    function toGoodsSearch() {
        try {
            var u = window.navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            if (isAndroid == true) {
                console.log("安卓");
                var collection = Android.keyboardHide();//调用安卓接口

            }
        }catch(error){

        }
        //商品名称[搜索条件]
        var goodsName = $("#ttsw_search_id").val();
        location.href = basePath + "ea/wholesaleMall/ea_toGoodsSearch.jspa?ccompanyID="
            + ccompanyID + "&goodsName=" + goodsName + "&companyName=" + companyName + "&industryType=" + industryType + "&posNum=" + posNum+"&lxType=1"+"&companyId="+companyId;
    }

    //跳转购物车列表
    function toShopCartList(){
            //判断是否是大屏终端
            //var posNum = Android.forAndroidDeviceId();
            var url = "";
        if (posNum == null || posNum == "") {//跳转小屏
                url = basePath + "ea/wholesaleMall/ea_shoppingCartList.jspa?companyName=" + companyName + "&ccompanyID=" + ccompanyID;
            } else {//跳转大屏
                posNum = isExistPosNum(posNum);
                //url = basePath + "ea/wholesaleMall/ea_shoppingCartForDpList.jspa?posNum=" + posNum + "&ccompanyID=" + ccompanyID;
                url = basePath + "ea/sm/ea_qdlist.jspa?posNum=" + posNum + "&ccompanyID=" + ccompanyID+"&relateID="+relateID +"&lxType=1&companyId="+companyId ;
            }
        window.location.href = url;
    }


    //解决浏览器偏移问题
    function getScrollTop() {
        var scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
        return scrollTop;
    }

    //核对购物卡积分
    function checkScardJiFen(scard) {
        if (tt == 1) {
            return false;
        }
        $(".alert_weigh_").show();
        $(".tipcon").text("正在查询账单请稍后..");

        tt = 1;
        var ulp = basePath
            + "/ea/sm/sajax_ea_checkScardJiFen.jspa";
        $.ajax({
            type: "GET",
            url: ulp,
            async: true,
            dataType: "json",
            data: {
                scard: scard
            },
            success: function (data) {
                var member = eval('(' + data + ')');
                var result = member.result;
                if (result == "0") {
                    // var sccId = member.sccId;
                    // var staffName = member.staffName;
                    // document.location.href = basePath + "ea/sm/ea_getbpDetail.jspa?sccId=" + sccId + "&staffName=" + staffName + "&posNum=" + posNum+"&ccompanyID="+ccompanyID;


                    var sccId = member.sccId;
                    var staffName = member.staffName;
                    var account = member.account;
                    var staffid = member.staffId;
                    // document.location.href = basePath + "ea/sm/ea_getbpDetail.jspa?sccId=" + sccId+"&staffName="+staffName+"&" ;
                    document.location.href = basePath + "ea/bonuspoints/ea_getbpDetail.jspa?account="+account+"&sccid="+sccId+"&khd=0&flag=&staffid="+staffid+"&staffName="+staffName+"&platType=screen&posNum=" + posNum+"&ccompanyID="+ccompanyID;

                } else {
                    var shot = showTime();
                    $(".alert_weigh_").show();
                    $(".tipcon").text("此卡无效可以联系工作人员");
                    tt = 1;
                    shot;
                }


            },
            error: function (data) {
                console.log("查询支付结果失败");
            }
        });
    }

    //设定倒数秒数
    var t = 4;
    function showTime() {
        t -= 1;
        $(".alert_weigh p span").text(t);

        //每秒执行一次,showTime()
        var s = setTimeout("showTime()", 1000);

        if (t == 0) {
            t = 4;
            $(".alert_weigh_").hide();
            clearTimeout(s);
            $(".alert_weigh p span").text(t);
        }
        /*商品称重弹框*/
        $("#confirm").click(function () {
            $(".alert_weigh_").hide();
            clearTimeout(s);
            t = 4;
            $(".alert_weigh p span").text(t);
        });
    }

    //记录要返回的页面
    function backUrl(){
        if(posNum!=null&&posNum!="") {
            if(localStorage!=null) {
                localStorage.setItem("backUrl", window.location.href);
            }
        }
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

    //跳转查询全部商家
    function toBussList(){
        window.location.href = basePath + "ea/wholesaleMall/ea_toBussList.jspa";
    }
}