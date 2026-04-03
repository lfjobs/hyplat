/**
 * 产品发布
 */
const u = window.navigator.userAgent;
const isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
const isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
const iswin = u.indexOf('Windows') > 1;
var depotid = "";
var depotName = "";
var depotCoding = "";
var itemID = "";
var depotItemID = 'scode20241026gvvqfp64fk0000000004';//秤盘类别id
var posNum;//货柜终端机编号

/**
 * 交易类型预设数据
 * @type {{"11": string, "12": string, "13": string, "14": string, "15": string, "16": string}}
 */
const gkjy = {
    "11": "厂家",
    "12": "批发",
    "13": "零售",
    "14": "货柜自提",
    "15": "配送电商",
    "16": "门店自助"
};
/**
 * 交易类型预设数据
 * @type {{"22": string, "23": string, "24": string, "25": string, "21": string}}
 */
const zdjy = {
    "21": "厂家",
    "22": "批发",
    "23": "零售",
    "24": "往来企业",
    "25": "往来个人"
};

/**
 * 自动贩卖机操作员信息
 * @type {{posNum: (string|string), loginGuid: (string|string), companyid: (string|string), companyIdentifier: (string|string), staffName: (string|string)}}
 */
const config = {
    posNum: getcookie("posNum"), //货柜终端机编号
    hgcode: getcookie("hgcode"), //货柜库房编号
    loginGuid: getcookie("loginGuid"), //登录id
    companyid: getcookie("comID"), //公司id
    staffName: getcookie("staffName"), //人员姓名
    companyIdentifier: getcookie("companyIdentifier"), //公司标识
    source:getcookie("source") //来源 scan:扫码 login:登录
};

$(function () {
    try {
		if (config.posNum!=null&&config.posNum!=""){
            $(".footer_right").css("width","100%");
            $(".footer_left").hide();
            posNum = config.posNum;
            MQTTconnect();
        }
        if (typez == "tc") {
            $("#iframe").attr("height", $(window).height());
            $(".ftc").remove();
            $(".tc").show();
            $(".btzd").hide();
            $("#lkfb").text("提交审核");
            $("#frkc").text("保存");
            site();
        } else {
            $(".ftc").show();
            $(".tc").remove();
        }

        $("#prompt").css("position", "absolute").css("top", $(window).height() * 0.09 + "px");
        $("#prompt").find("div").css("height", $(window).height() * 0.06 + "px").css("font-size", $(window).height() * 0.0285 + "px").css("color", "#FFFFFF");
        $("#prompt").find("div").css("-moz-border-radius", $(window).height() * 0.015 + "px").css("-webkit-border-radius", $(window).height() * 0.015 + "px");
        $(".main").css({"height": $(window).height() - $("header").height() - $("footer").height() + "px"});
        $("#abc").css("width", "100%");
        $(".carousel-inner .item img").css({
            "width": $(window).width() + "px",
            "height": $(window).height() * 0.4 + "px"
        });
        (function ($) {
            /**
             * 为jQuery添加一个获取URL参数的方法
             * 这个方法使得开发者可以轻松地从当前页面的URL中提取出指定的查询参数
             *
             * @param {string} name - 想要获取的查询参数的名称
             * @return {string|null} - 返回指定查询参数的值；如果没有找到该参数，则返回null
             */
            (function ($) {
                $.getUrlParam = function (name) {
                    // 创建一个正则表达式，用于匹配URL中的查询参数
                    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                    // 使用正则表达式在当前页面的URL查询字符串中寻找匹配的参数
                    var r = window.location.search.substr(1).match(reg);
                    // 如果找到了匹配的参数，就解码并返回该参数的值
                    if (r != null) return decodeURI(r[2]);
                    // 如果没有找到，返回null
                    return null;
                }
            })(jQuery);
        })(jQuery);

        $("#zrr").click(function () {

            $(".iframecom").show();
        });
        if (sys == null || sys == "") {
            sys = $.getUrlParam("sys");
        }

        if ($.getUrlParam("weight") != null && $.getUrlParam("weight") != "") {
            $(".kc").val($.getUrlParam("weight"));
        }

        if (ppId != null && ppId != "") {
            depotid = $("#depotID").val();
            let stanpro = $("#stanpro").val();
            let variableID = $("#variableID").val();
            if (depotid != null && depotid != "") {
                $("#stanpro_div").show();
                if (stanpro == "0") {
                    $("#toggle--switch_stanpro").trigger("click");
                    $(".singleWeight_input").attr("readonly", "readonly");
                    if (variableID != null && variableID != "") {
                        $("#singleWeight-p").text("KG");
                        $("#variableID").text("KG");
                        $("#variable").val(variableID + "/KG");
                    }
                    $("#variable").show();
                    $("#singleWeight").show();
                } else {

                }
            }
            //$("footer").hide();

        } else {
            //$("footer").show();
        }

        if ($(".kc").val() == null || $(".kc").val() == "" || $(".kc").val() == "0") {
            $(".kc").removeAttr("readonly");
        }

        $(".dlyj").each(function () {
            if ($(this).val() == '') $(this).val(0);
        });

        $(".kc").focus(function () {
            if ($(this).val() == 0) $(this).val('');
        });

        $(".kc").blur(function () {
            if ($(this).val() == '') $(this).val(0);
        });

        $("input").blur(function () {
            $("html, body").animate({scrollTop: 0}, {duration: 0, easing: "swing"});
            return false;
        });

        $(".dlyj").focus(function () {
            if ($(this).val() == 0) $(this).val('');
        });

        $(".dlyj").blur(function () {
            if ($(this).val() == '') $(this).val(0);
        });

        $(document).on("input", ".xtsj", function () {
            var xtsj = $(this).val();
            $(".sj").val($(this).val() * (totalPct + 1));
            $(".xfhb").val($(this).val() * totalPct);
        });

        $(".products .product_lei").click(function () {
            $(".product").empty();
            loaded();
        });

        //选择后赋值
        $(document).on("click", ".product li", function () {
            if ($(".top ul").find("#save").length == 0) {
                $(".product_lei").text($(this).find(".typeName").val());
                $("#categoryId").val($(this).find(".categoryId").val());
                $("#categoryName").val($(this).find(".typeName").val());
                $(".addCategorie").hide();
                pagenumber = 0;
                pagecount = 0;
            }
        });

        //弹出产品规格
        $(document).on("click", "#productsize", function () {
            if (goodsId != "") {
                $.ajax({
                    url: basePath
                        + "ea/restaurant/sajax_ea_specifications.jspa",
                    type: "post",
                    async: false,
                    data: {
                        "goodsid": goodsId
                    },
                    datatype: "json",
                    success: function (data) {
                        var member = eval("(" + data + ")");
                        var t = member.map;
                        if (typeof t === "object" && !(t instanceof Array)) {
                            var hasProp = false;
                            for (var prop in t) {
                                hasProp = true;
                                break;
                            }
                            if (hasProp) {
                                var str = new Array();
                                var arr = new Array();
                                $(".color").html("");
                                $(".size").html("");
                                $.each(t, function (i, teach) {
                                    var at = i.charAt(i.length - 1);
                                    if (at == "1") {
                                        str.push('<input class="sezi_title"  value="' + i.substring(0, i.lastIndexOf('1')) + '">');
                                        if (teach != null) {
                                            str.push('<ul class="size_new">');
                                            str.push('<li><input class="seze_edit" placeholder="自定义"></li>');
                                            str.push('</ul>');
                                            str.push(' <ul class="size_old">');
                                            for (var j = 0; j < teach.length; j++) {
                                                str.push('<li><input class="input_xz" readonly value="' + teach[j] + '"></li>');
                                            }
                                            str.push('</ul>');
                                        }
                                    } else if (at == "0") {
                                        arr.push('<input class="sezi_title"  value="' + i.substring(0, i.lastIndexOf('0')) + '">');
                                        if (teach != null) {
                                            arr.push('<ul class="size_new">');
                                            arr.push('<li><input class="seze_edit" placeholder="自定义"></li>');
                                            arr.push('</ul>');
                                            arr.push('<ul class="size_old">');
                                            for (var j = 0; j < teach.length; j++) {
                                                arr.push('<li><input class="input_xz" readonly value="' + teach[j] + '"></li>');
                                            }
                                            arr.push('</ul>');
                                        }
                                    }

                                });
                                if (str.length == 0) {
                                    str.push(' <input class="sezi_title" value="颜色"  >');
                                    str.push('<ul class="size_new">');
                                    str.push('<li><input class="seze_edit" placeholder="自定义颜色"></li>');
                                    str.push('</ul>');
                                    str.push('<ul class="size_old">');
                                    str.push('<li><input value="白色" readonly name="old_1"></li>');
                                    str.push('<li><input value="粉色" readonly name="old_2" class=""></li>');
                                    str.push('<li><input value="米黄色" readonly name="old_3"></li>');
                                    str.push('<li><input value="蓝色" readonly name="old_4" class=""></li>');
                                    str.push('<li><input value="紫色" readonly name="old_5"></li>');
                                    str.push('<li><input value="黑色" readonly name="old_6"></li>');
                                    str.push('<div class="clear"></div>');
                                    str.push('</ul>');
                                }
                                if (arr.length == 0) {
                                    arr.push('<input class="sezi_title" value="尺码大小" >');
                                    arr.push(' <ul class="size_new">');
                                    arr.push('<li><input class="seze_edit" placeholder="自定义大小"></li>');
                                    arr.push(' </ul>');
                                    arr.push('<ul class="size_old">');
                                    arr.push('<li><input value="S" readonly name="old_1"></li>');
                                    arr.push('<li><input value="M" readonly name="old_2" class=""></li>');
                                    arr.push('<li><input value="L" readonly name="old_3"></li>');
                                    arr.push(' <li><input value="XL" readonly name="old_4" class=""></li>');
                                    arr.push('<li><input value="XXL" readonly name="old_5"></li>');
                                    arr.push('<li><input value="XXXL" readonly name="old_6"></li>');
                                    arr.push('</ul>');
                                }
                                $(".color").append(str.join(""));
                                $(".size").append(arr.join(""));
                            } else {
                                $(".product_size").show();
                                return false;
                            }
                        }

                    }
                });
            }
            $(".product_size").show();
        });

        //弹出投资类型
        $(document).on("click", "#investment", function () {
            $(".investment").addClass("act");
        });

        $(".investment").click(function () {
            $(".investment").removeClass("act");
        });

        $(".investment_con").click(function (e) {
            e.stopPropagation();
        });

        $(".investment_con ul li").click(function (e) {
            e.stopPropagation();
            var t = $(this).text();
            $("#tz_type").text($(this).text());
            $("#tzType").val("0" + $(this).val());
            $(".investment").removeClass("act");
        });

        //弹出产品描述
        $(document).on("click", "#productdescribe", function () {
            if (j != 0) {
                $("#edit").append("<div class='content' id='content" + j + "'></div>");
            }
            $(".product_describe").show();
        });

        $(document).on("click", ".actives img", function () {
            bgstr = '<div id="ImgZoomInBG" style=" background:#000000; filter:Alpha(Opacity=70); opacity:0.7; position:fixed;' +
                ' left:0; top:0; z-index:10000; width:100%; height:100%; display:none;">' +
                '<iframe src="about:blank" frameborder="5px" scrolling="yes" style="width:100%; ' +
                'height:100%;"></iframe></div>';
            imgstr = '<img id="ImgZoomInImage" src="' + $(this).attr("src") + '" data-id="' + $(this).attr("data-id") + '" onclick=$(\'#delete2\').hide();' +
                '$(\'#ImgZoomInImage\').hide();' +
                '$(\'#ImgZoomInBG\').hide(); ' +
                'style="cursor:pointer; display:none; position:absolute; z-index:10001;width:80%;" />' +
                '<img id="delete2" style="position: absolute;display:none;" onclick="DeletePhoto()" ' +
                'src="' + basePath + 'images/WFJClient/PersonalJoining/cutout.png"/>';
            if ($('#ImgZoomInBG').length < 1) {
                $('body').append(bgstr);
            }

            if ($('#ImgZoomInImage').length < 1) {
                $('body').append(imgstr);
            } else {
                $('#ImgZoomInImage').attr('src', $(this).attr('src'));
                $('#ImgZoomInImage').attr('data-id', $(this).attr('data-id'));
            }

            $('#ImgZoomInImage').css('left', $(window).scrollLeft() + ($(window).width() - $('#ImgZoomInImage').width()) / 2);

            $('#ImgZoomInImage').css('top', $(window).scrollTop() + ($(window).height() - $('#ImgZoomInImage').height()) / 2);

            $('#delete').css({
                "position": "absolute",
                'right': $(window).scrollLeft()
                    + ($(window).width()
                        - $('#ImgZoomInImage')
                            .width() - $(this)
                            .width() * 0.1) / 2,
                'top': $(window).scrollTop() + ($(window).height() - $('#ImgZoomInImage').height()) / 2 - 30,
                "width": "30px",
                "z-index": "10001"
            });
            $('#delete2').css({
                "position": "absolute",
                'right': "50%",
                'top': "50%",
                "width": "30px",
                "z-index": "10001"
            });

            $('#delete').show();
            $('#delete2').show();
            $('#ImgZoomInBG').show();
            $('#ImgZoomInImage').show();
        });

        //弹出计价单位
        $(document).on("click", "#jjdw", function () {
            $("#jjdw_xz").show();
        });

        $(document).on("click", "#jjdw_xz li", function () {
            $("#jjdw_xz").hide();
            $("#jjdw_p").text($.trim($(this).text()));
            if ($.trim($(this).text()) == "KGM以重计价") {
                $("#depotManage").show();
                $(".kc").attr("readonly", "readonly");
            }else{
                $(".kc").attr("readonly", "");
                $("#dmname .js-marquee").html("");
                $("#depotID").val("");
                $("#depotName").val("");
                $("#depotCoding").val("");
                $("#depotManage").hide("");
                $("#v-p").html("");
                $("#stanpro").val("");
                $("#stanpro_div").hide();
                $("#variableID").val("");
                $("#variable").hide();
                $("#singleWeight-p").text("");
                $(".singleWeight_input").val("");
                $("#singleWeight").hide();
            }
            $("#unitOfMeasureCode").val($(this).text().trim().substring(0, 3));
            $("#jjdw_xz li").css("background", "#fff");
            $(this).css("background", "#f1f1f1");
        });

        if (isAndroid == true) {
            var obj = document.getElementById("returnClick");
            obj.setAttribute("href", "#");
            if (sys != null && sys != "") {
                obj.setAttribute("onclick", "retAndroid()");
            } else {
                obj.setAttribute("onclick", "goBack()");
            }
        } else if (isiOS == true) {
            var obj = document.getElementById("returnClick");
            obj.setAttribute("href", "#");
            if (sys != null && sys != "") {
                obj.setAttribute("onclick", "retIOS()");
            } else {
                obj.setAttribute("onclick", "goBack()");
            }
            /*var obj_sao = document.getElementById("sao");
            obj_sao.setAttribute("onclick", "callIOScamera()");*/
        }

        if ($(".item").length > 0) {
            $(".bannerc").css({"line-height": $(window).width() + "px"});
            $(".carousel-inner .item").css({"line-height": $(window).width() + "px", "text-align": "center"});
            $(".carousel-inner .item img").css({
                "width": $(window).width() + "px",
                "max-height": $(window).width() + "px",
                "height": "auto",
                "display": "inline-block"
            });
            $(".main_img").css({"width": $(window).width() + "px", "height": $(window).width() + "px"});

        }

        $("#ppname").blur(function () {
            var a = "/^[//(//)\u4e00-\u9fa5_a-zA-Z0-9]+$/";
            var reg = new RegExp(a);//商品名称
            if ($(this).val() == "") {
                prompt("请输入商品名称");
                return;
            } else if (!reg.test($(this).val())) {
                prompt("商品名称只支持数字，英文，中文");
                return;
            } else {
                var url = basePath + "ea/productslaunch/sajax_ea_checkProName.jspa?name=" + $(this).val() + "&companyId=" + companyId;
                $.ajax({
                    url: url,
                    type: "get",
                    async: false,
                    success: function (data) {
                        var member = eval("(" + data + ")");
                        var s = member.s;
                        if (s == "0") {
                            prompt("商品名称不允许重复");
                            $("#ppname").focus();
                        }
                    },
                    error: function () {
                        alert("验证失败");
                    }
                });
            }
        });

        if (j > 0) {
            $("#complete").text("已编辑");
            $("#pt").hide();
        }

        if (ptcount > 0) {
            $("#complete_pt").text("已编辑");
            $("#ptt").hide();
        }

        //是否称重
        $(document).on("click", "#toggle--switch", function () {
            if ($(this).is(":checked")) {
                $("#isScale").val("0");
                $("#jjdw").show();
                cz = true;
                $("#barCode").val($("#codeSpan").text());
                $("#barCode").attr("placeholder", "系统自动生成");
                $("#barCode").attr("readonly", "readonly");
                $(".kc").attr("readonly", "readonly");
                $(".tiaoma_img").hide();
            } else {
                $("#isScale").val("1");
                $(".kc").removeAttr("readonly");
                $("#jjdw").hide();
                cz = false;
                $("#barCode").attr("placeholder", "输入或扫描条码");
                $("#barCode").removeAttr("readonly");
                $("#jjdw_p").text("");
                $("#unitOfMeasureCode").val("");
                $(".tiaoma_img").show();

                $("#dmname .js-marquee").html("");
                $("#depotID").val("");
                $("#depotName").val("");
                $("#depotCoding").val("");
                $("#depotManage").hide("");
                $("#v-p").html("");
                $("#stanpro").val("");
                $("#stanpro_div").hide();
                $("#variableID").val("");
                $("#variable").hide();
                $("#singleWeight-p").text("");
                $(".singleWeight_input").val("");
                $("#singleWeight").hide();
            }
        });

        //是否标品
        $(document).on("click", "#toggle--switch_stanpro", function () {
            if ($(this).is(":checked")) {
                $("#stanpro").val("0");
                $("#barCode").attr("placeholder", "输入或扫描条码");
                $("#barCode").removeAttr("readonly");
                $(".tiaoma_img").show();
                $("#variable").show();
                $("#singleWeight").show();
                $("#singleWeight-p").text("KG");
                $(".kc_p").text("件");
            } else {
                $("#stanpro").val("1");
                $("#barCode").val($("#codeSpan").text());
                $("#barCode").attr("placeholder", "系统自动生成");
                $("#barCode").attr("readonly", "readonly");
                $(".tiaoma_img").hide();
                $(".kc_p").text("KG");
                $("#variableID").val("KG");
                $("#v-p").html("");
                $("#variable").hide();
                $("#singleWeight").hide();
                $(".singleWeight_input").val("");
                $("#singleWeight-p").text("");
            }
        });

        /*********************************计量单位开始*********************************/
        //弹出投资类型
        $(document).on("click", "#variable", function () {
            $(".variable").addClass("act");
        });

        $(".variable").click(function () {
            $(".variable").removeClass("act");
        });

        $(".variable_con").click(function (e) {
            e.stopPropagation();
        });

        $(".variable_con ul li").click(function (e) {
            e.stopPropagation();
            $("#v-p").text($(this).text());
            $("#singleWeight-p").text($(this).text());
            $("#variableID").val($(this).attr("title"));
            $(".kc_p").text($(this).attr("title"));
            $(".variable").removeClass("act");
        });
        /**********************************计量单位结束********************************/

        //无码称重
        $(document).on("click", ".kc,#singleWeight", function () {
            console.log($("#dmname").text());
            console.log($("#stanpro").val());
            console.log($("#v-p").text());
            if ($("#dmname").text() != "" && $("#dmname").text() != null) {
                if ($("#stanpro").val() == "0" && ($("#v-p").text() == "" || $("#v-p").text() == null)) {
                    prompt("请选择计量单位");
                    return false;
                } else {
                    // 构建参数对象
                    let params = {
                        'depotID': $("#depotID").val(),
                        'depotName': $("#depotName").val(),
                        'depotCoding': $("#depotCoding").val(),
                        'stanpro': $("#stanpro").val() == "0" ? "是" : $("#stanpro").val() == "1" ? "否" : "",
                        'variableID': $("#variableID").val(),
                        'ppId': $("#ppId").val(),
                        'goodsName': $("#ppname").val(),
                        'barCode': $("#barCode").val(),
                        'quantity': $(".kc").val(),
                        'singleWeight': $(".singleWeight_input").val()
                    };

                    // 存储参数到 localforage，并处理成功和失败的情况
                    localforage.setItem('params', params)
                        .then(function (value) {
                            console.log(value);
                            // 继续执行其他操作
                            $(".div-iframe li:nth-of-type(2)").text("发布");
                            $("#iframe-").attr("src", basePath + "/page/WFJClient/ProductsLaunch/ProductCalculationNum.jsp?originPage=win-rk");
                            $(".div-iframe").show();
                        }).catch(function (err) {
                        console.error("存储参数时出错:", err);
                        // 提示用户或采取其他措施
                        alert("保存数据失败，请稍后再试。");
                    });
                }
            } else {
                if (cz && $("#unitOfMeasureCode").val() == "KGM") {
                    var goods = {
                        'main_img': $(".main_img").html(),
                        'isScale': $("#isScale").val(),
                        'jjdw_p': $("#jjdw_p").text(),
                        'unitOfMeasureCode': $("#unitOfMeasureCode").val(),
                        'product_lei': $(".product_lei").text(),
                        'categoryId': $("#categoryId").val(),
                        'categoryName': $("#categoryName").val(),
                        'colorvalue': $("#colorvalue").val(),
                        'sizevalue': $("#sizevalue").val(),
                        'attrinames': $("#attrinames").val(attrinames),
                        'attrinamec': $("#attrinamec").val(attrinamec),
                        'tz_type': $("#tz_type").text(),
                        'tzType': $("#tzType").val(),
                        '编辑': $("#edit").html(),
                        'complete': $("#complete").text(),
                        'depotID': $("#depotID").val(),
                        'depotName': $("#depotName").val(),
                        'depotCoding': $("#depotCoding").val(),
                        'itemID': $("#itemID").val(),
                        'stanpro': $("#stanpro").val(),
                        'variableID': $("#variableID").val(),
                        'paytype': $("#paytype").val()
                    };
                    localforage.setItem('goods', goods).then(function (value) {
                        // 当值被存储后，可执行其他操作
                        console.log(value);
                    }).catch(function (err)  {    // 当出错时，此处代码运行
                        console.log(err);
                    });

                    var $windowObj = window.open(basePath + "/page/WFJClient/ProductsLaunch/ProductsWeighing.jsp?companyID=" + companyId + "&ppname=" + $("#ppname").val());
                }
            }
        });

        window.addEventListener('pageshow', function (e) {
            localforage.getItem('goods').then(function (value) {
                //当离线仓库中的值被载入时，此处代码运行
                if (value != null && value != "") {
                    //主图
                    //alert("value.main_img:"+value.main_img);
                    $(".main_img").html(value.main_img != null && value.main_img != "" ? value.main_img : $(".main_img").html());
                    //电子秤打条码
                    //alert("value.isScale:"+value.isScale);
                    $("#isScale").val(value.isScale != null && value.isScale != "" ? value.isScale : ("#isScale").val());
                    getisScale();
                    //计价单位
                    //alert("value.jjdw_p:"+value.jjdw_p+"-value.unitOfMeasureCode:"+value.unitOfMeasureCode);
                    $("#jjdw_p").text(value.jjdw_p != null && value.jjdw_p != "" ? value.jjdw_p : ("#jjdw_p").val());
                    $("#unitOfMeasureCode").val(value.unitOfMeasureCode != null && value.unitOfMeasureCode != "" ? value.unitOfMeasureCode : ("#unitOfMeasureCode").val());
                    //分类
                    //alert("value.product_lei:"+value.product_lei+"-value.categoryId:"+value.categoryId+"-value.categoryName:"+value.categoryName);
                    $(".product_lei").text(value.product_lei != null && value.product_lei != "" ? value.product_lei : $(".product_lei").text());
                    $("#categoryId").val(value.categoryId != null && value.categoryId != "" ? value.categoryId : $("#categoryId").val());
                    $("#categoryName").val(value.categoryName != null && value.categoryName != "" ? value.categoryName : $("#categoryName").val());
                    //规格
                    //alert("value.colorvalue:"+value.colorvalue+"-value.sizevalue:"+value.sizevalue);
                    $("#colorvalue").val(value.colorvalue != null && value.colorvalue != "" ? value.colorvalue : $("#colorvalue").val());
                    $("#sizevalue").val(value.sizevalue != null && value.sizevalue != "" ? value.sizevalue : $("#sizevalue").val());
                    $("#attrinames").val(value.attrinames != null && value.attrinames != "" ? value.attrinames : $("#attrinames").val());
                    $("#attrinamec").val(value.attrinamec != null && value.attrinamec != "" ? value.attrinamec : $("#attrinamec").val());
                    if ((value.colorvalue != null && value.colorvalue != "") || (value.sizevalue != null && value.sizevalue != "")) {
                        $("#ps").text(value.colorvalue + value.sizevalue + "...");
                    } else if ((value.colorvalue == null || value.colorvalue == "") && (value.sizevalue == null || value.sizevalue == "")) {
                        $("#ps").text();
                    }
                    //设备投资
                    //alert("value.tz_type:"+value.tz_type+"-value.tzType:"+value.tzType);
                    $("#tz_type").text(value.tz_type != null && value.tz_type != "" ? value.tz_type : $("#tz_type").text());
                    $("#tzType").val(value.tzType != null && value.tzType != "" ? value.tzType : $("#tzType").val());
                    //详情
                    //alert("value.编辑:"+value.编辑+"-value.complete:"+value.complete);
                    $("#edit").html(value.编辑 != null && value.编辑 != "" ? value.编辑 : $("#edit").html());
                    $("#complete").text(value.complete != null && value.complete != "" ? value.complete : $("#complete").text());
                    //仓库
                    $("#depotID").val(value.depotID != null && value.depotID != "" ? value.depotID : $("#depotID").val());
                    $("#depotName").val(value.depotName != null && value.depotName != "" ? value.depotName : $("#depotName").val());
                    $("#depotCoding").val(value.depotCoding != null && value.depotCoding != "" ? value.depotCoding : $("#depotCoding").val());
                    if (value.depotName != null && value.depotName != "") {
                        $("#dmname").text("(" + value.depotCoding + ")" + value.depotName);
                    }
                    if (value.itemID == depotItemID) {
                        $("#stanpro_div").show();
                        $(".kc").attr("readonly", "readonly");
                        $(".singleWeight_input").attr("readonly", "readonly");
                    } else {
                        $("#stanpro_div").hide();
                        $(".kc").attr("readonly", "");
                    }

                    $("#stanpro").val(value.stanpro != null && value.stanpro != "" ? value.stanpro : $("#stanpro").val());
                    if (value.stanpro != null && value.stanpro != "" && value.stanpro == "0") {
                        $("#toggle--switch").prop("checked", true);
                        if (value.depotName != null && value.depotName != "") {
                            $("#v-p").text(value.variableID);
                        }
                        $("#variable").show();
                    }
                    $("#variableID").val(value.variableID != null && value.variableID != "" ? value.variableID : $("#variableID").val());

                    localforage.removeItem('goods').then(function () {

                    }).catch(function (err) {
                        // 当出错时，此处代码运行
                        console.log(err);
                    });
                }
            }).catch(function (err) {
                // 当出错时，此处代码运行
                alert(err);
            });

            localforage.getItem('params').then(function (value) {
                //当离线仓库中的值被载入时，此处代码运行
                if (value != null && value != "") {
                    var w_ppid = value.ppId;
                    var w_weight = value.weight;
                    var w_goodid = value.goodid;
                    if (w_ppid != "" && w_ppid != null) {
                        var pa = "user=" + user + "&companyId=" + companyId + "&ppId=" + w_ppid + "&goodsId=" + w_goodid + "&weight=" + w_weight;
                        window.location.href = basePath + "ea/productslaunch/ea_toProductsLaunch.jspa?" + pa;
                    } else {
                        if ($("#invNum").val() != null && $("#invNum").val() != "") {
                            prompt("初始库存不可重复设置！");
                            return false;
                        } else {
                            $(".kc").val(w_weight);
                            $("#toggle--switch").removeAttr("disabled");
                        }
                    }
                    localforage.removeItem('params').then(function () {
                    }).catch(function (err) {
                        // 当出错时，此处代码运行
                        console.log(err);
                    });
                }
            }).catch(function (err) {    // 当出错时，此处代码运行
                alert(err);
            });
        });

        //切换时间制
        $(".main_hidden #timeUnits").change(function () {
            if ($(this).val() == "1") {
                $(".main_hidden  #timeType").parents(".mil").show();
            } else {
                $(".main_hidden  #timeType").parents(".mil").hide();
            }
        });

        //仓库管理
        $(document).on("click", "#depotManage", function () {
            $(".div-iframe li:nth-of-type(2)").text("仓库管理");
            $("#iframe-").attr("src", basePath + "/page/WFJClient/pc/5l5C/office/inventory/depotManageTransfer.jsp?originPage=win-rk");
            $(".div-iframe").show();
        });

        //iframe关闭
        $(document).on("click", ".div-iframe .close", function () {
            $('.div-iframe').hide();
            $('.div-iframe').find("iframe").attr("src", "");
        });

        // 	显示
        $(".paytype").click(function () {
            var str = [];
            str.push("<ul class='ul-left'>");
            str.push("<li value='01'>公开交易</li>");
            str.push("<li value='02'>针对交易</li>");
            str.push("</ul>");
            $(".jylx").append(str.join(""));
            //getPaytype("01");
            $(".div-paytype").show();
        });
        // 	关闭
        $(document).on("click", ".div-paytype .div-close", function () {
            $(".div-paytype").hide();
            $(".div-right").remove();
        });
        // 	切换选项
        $(document).on("click", ".div-paytype .ul-left li", function () {
            $(".div-right").remove();
            $(this).addClass("active");
            $(this).siblings().removeClass("active");
            getPaytype($(this).val());
        });
        // 	传入选项 更改显示模块
        $(document).on("click", ".div-paytype .div-right>ul li", function () {
            $(this).addClass("active");
            $(this).siblings().removeClass("active");
            $("#paytype-p").text($(this).find(".codeValue").text());
            $("#paytype").val($(this).attr("id"));
            $(".div-paytype").hide();
            $(".jylx").empty();
        });

        getisScale();
    } catch (e) {
        alert("发生错误: " + e.message);
    }
});//加载完毕

function getisScale() {
    if ($("#isScale").val() != null && $("#isScale").val() == "0") {
        //alert("选中");
        if ($("#isScale").val() == "0") {
            cz = true;
            $("#toggle--switch").attr("disabled", "disabled");
        }
        $("#toggle--switch").attr("checked", true);//选中
        $("#barCode").attr("placeholder", "系统自动生成");
        $("#barCode").attr("readonly", "readonly");
        $("#jjdw").show();
        //$(".tiaoma_img").hide();
    } else {
        if ($("#isScale").val() == "1") {
            cz = false;
            $("#toggle--switch").attr("disabled", "disabled");
        }
        $("#toggle--switch").attr("checked", false);//选中
        $("#jjdw").hide();
        $("#barCode").attr("placeholder", "输入或扫描条码");
        //$(".tiaoma_img").show();
        $("#jjdw").hide();
    }

    if (config.loginGuid != null && config.loginGuid != "") {
        if ($("#toggle--switch").not(":checked")) {
            $("#toggle--switch").trigger("click");
            $("#jjdw_xz li:first-child").trigger("click");
        }
    }
}

//扫码枪扫码搜索
document.onkeydown = function (event) {
    event = document.all ? window.event : event;
    if ((event.keyCode || event.which) == 13) {
        document.activeElement.blur();
        var params = new Array();
        params.push("barcode=" + $("#barCode").val());
        params.push("&user=" + user);
        params.push("&companyId=" + companyId);
        params.push("&interval=" + "");
        params.push("&name=" + "");
        window.location.href = basePath + "ea/productslaunch/ea_toProductsLaunch.jspa?" + params.join("");
        return false;
    }
}

function loaded() {
    clearTimeout(t);
    pagenumber++;
    var url = basePath + "ea/productslaunch/sajax_ea_ajaxProductType.jspa?";
    $.ajax({
        url: url,
        type: "get",
        async: false,
        dataType: "json",
        data: {
            "pageForm.pageNumber": pagenumber,
            "companyId": companyId
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            if (member != null) {
                var pageForm = member.pageForm;
                var prostr = "";
                if (pageForm != null && pageForm.recordCount > 0) {
                    var producttypelist = pageForm.list;
                    pagenumber = pageForm.pageNumber;
                    pagecount = pageForm.pageCount;
                    if (producttypelist != null) {
                        if (pagenumber == 1) {
                            $(".product").html("");
                        }
                        $(".last").removeClass("last");
                        for (var i = 0; i < producttypelist.length; i++) {
                            var pro = producttypelist[i];
                            prostr += "<li class='last'>";
                            prostr += " <div class='txt'>"
                            prostr += "<input type='hidden' value='" + pro[0] + "'>";
                            prostr += "<input type='hidden' class='categoryId' value='" + pro[1] + "'>";
                            prostr += "<input type='hidden' value='" + pro[2] + "'>";
                            prostr += "<input type='hidden' value='" + pro[4] + "'>";
                            prostr += "<input class='typeName' type='text' value='" + pro[3] + "' placeholder='' readonly='readonly'>";
                            prostr += "<img src='" + basePath + "images/ea/production/ico_right.png' class='right'>";
                            prostr += "</div>";
                            prostr += "</li>";
                        }
                        $(".product").append(prostr);
                        getHeight();
                    }
                } else {
                    $(".product").append("<div class='no'><img src='" + basePath + "images/ea/production/no.png' width='100%'><p>您还没有添加分类，赶快添加吧</p></div>");
                    $(".redact").text("保存");
                    $(".redact").removeClass().attr("id", "save");
                    $(".addCategorie .top ul").children("li").eq(2).hide();
                    $(".add").show();
                }
            }
        },
        error: function cbf(data) {
            alert("产品加载失败");
        }
    });
    $(".addCategorie").show();
}

function getHeight() {
    t = setTimeout("getHeight()", 200);
    if ($(".product").height() - ($(window).height() - $(".product").offset().top) < $(window).height()) {
        if (pagenumber < pagecount) {
            loaded();
        }
    }
}

function DeletePhoto() {
    if (window.confirm('确定删除吗？')) {
        if ($(".active").eq(1).find("input[name='apId']").length > 0) {
            var apId = $(".active").eq(1).find("input[name='apId']").val();
            var sort = $(".active").eq(1).find("input[class='sort']").val();
            var url = basePath + "ea/productslaunch/sajax_ea_delPhoto.jspa?apId=" + apId + "&sort=" + sort;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                success: function cbf(data) {
                    var member = eval("(" + data + ")");
                    var flag = member.flag;
                    if (flag == '1') {
                        alert("操作成功！");
                    }
                },
                error: function () {
                    alert("操作失败！");
                }
            });
        }

        $(".active").remove();
        $("#" + $("#ImgZoomInImage").attr("data-id")).remove();

        $('#delete').hide();
        $('#delete2').hide();
        $('#ImgZoomInBG').hide();
        $('#ImgZoomInImage').hide();
        $(".ImgZoomInImage").attr("src", "");
        $("#abc").find(".carousel-indicators").find("li").eq(0).attr("class", "active");
        $("#abc").find(".carousel-inner").find(".item").eq(0).attr("class", "active item actives");
    }

    if ($(".item").length < "2") {
        $(".carousel-indicators").css("display", "none")
        $(".main_img_but").css("display", "none")
        $(".carousel-inner .item").css({"line-height": $(window).width() + "px", "text-align": "center"});
        $(".carousel-inner .item img").css({"width": $(window).width() + "px", "max-height": $(window).width() + "px"});
    } else if ($(".item").length > "2") {
        $(".carousel-indicators").css("display", "block")
        $(".main_img_but").css("display", "block")
        $(".carousel-inner .item").css({"line-height": $(window).width() + "px", "text-align": "center"});
        $(".carousel-inner .item img").css({"width": $(window).width() + "px", "max-height": $(window).width() + "px"});
    }
    if ($(".item").length == "0") {
        $(".main_img").css({"height": "150px"})
        $(".main_img>p").css({"display": "block"})
        $(".file1").css("display", "none")
    }
}

//上传图片
function fileSelect() {
    var clone = "<input style='display: none;' id='fileToUpload" + d + "' type='file' name='photo' class='toUploadFile' multiple accept='image/png,image/jpeg,image/gif,image/jpg,image/bmp,image/webp' onchange='setImagePreviews_p(this.id)'/>"
    if ($(".item").length < 5) {
        $(".carousel-inner").append(clone);
        //$(".main_img").css({"height":"300px"});
        $("#fileToUpload" + d).click();
        d++;
    } else {
        alert("商品主图只允许5张！");
    }
}

function setImagePreviews_p(id) {
    //获得input=file对象
    var docObj = document.getElementById(id);
    var fileList = docObj.files;
    for (var i = 0; i < fileList.length; i++) {
        $(".item").attr("class", "item imgs actives");
        $(".carousel-indicators").find("li").attr("class", "");
        $("#" + id).parent().append("<div class='active item actives'><img id='img" + (d + i - 1) + "' data-id='" + id + "'/>");
        //获得该input下一个兄弟节点img
        var imgObjPreview = docObj.parentNode.lastChild.childNodes[0];
        if (docObj.files && docObj.files[i]) {
            //火狐下，直接设img属性
            imgObjPreview.style.display = 'inline-block';
            //imgObjPreview.style.width = '100%';
            imgObjPreview.style.height = 'auto';
            //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
            imgObjPreview.src = window.URL.createObjectURL(docObj.files[i]);
        } else {
            //IE下，使用滤镜
            docObj.select();
            var imgSrc = document.selection.createRange().text;
            var localImagId = docObj.parentNode.lastChild.childNodes[0];
            //必须设置初始大小
            //localImagId.style.width = "100%";
            localImagId.style.height = "auto";
            //图片异常的捕捉，防止用户修改后缀来伪造图片
            try {
                localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
            } catch (e) {
                alert("您上传的图片格式不正确，请重新选择!");
                return false;
            }
            imgObjPreview.style.display = 'none';
            document.selection.empty();
        }
        var c = $(".carousel-indicators li").length;
        $(".carousel-indicators").append("<li data-target='#abc' class='active' data-slide-to=" + (c + 1) + "></li>");
    }

    $(".main_img p").css({"display": "none"});
    $(".file1").css("display", "block");
    $("#abc").css({"display": "block"});
    $(".bannerc").css({"line-height": $(window).width() + "px"})
    $(".carousel-indicators").css({"display": "block"});
    $(".carousel-inner .item").css({"line-height": $(window).width() + "px", "text-align": "center"});
    $(".carousel-inner .item img").css({"width": $(window).width() + "px", "max-height": $(window).width() + "px"});
    $(".main_img").css({"width": $(window).width() + "px", "height": $(window).width() + "px"});
    if ($(".item").length < "2") {
        $(".carousel-indicators").css("display", "none")
        $(".main_img_but").css("display", "none")
    } else {
        $(".carousel-indicators").css("display", "block")
        $(".main_img_but").css("display", "block")
    }

}

function ajaxUploadPic() {
    if (checkInput()) {
        return;
    } else {
        var url = basePath + "/ea/productslaunch/sajax_ea_ajaxUploadPic.jspa?"
        var formData = new FormData($("#launchForm")[0]);
        $.ajax({
            url: encodeURI(url),
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                var member = eval("(" + data + ")");
                var boo = member.boo;
                if (boo == "1") {
                    alert("保存成功");
                } else {
                    alert("保存失败");
                }
            },
            error: function (data) {
                alert("保存失败！");
            }
        });
    }

}

function checkInput() {
    var f = true;
    $.each($(".toUploadFile"), function (i, a) {
        if (a != "") {
            f = false;
        }
    });
    return f;
}

function checkNull() {
    var f = true;
    $(".carousel-inner active").each(function () {
        if ($(".carousel-inner").find("input").length == 0 && $.trim($(this).text()) == "") {
            f = false;
            prompt("请添加图片");
        }
        return false;
    });
    return f;
}

function FeedbackDepot(data) {

    if (data.itemID == depotItemID) {
        $("#toggle--switch_stanpro").trigger("click");
        $("#stanpro_div").show();
        $(".kc").attr("readonly", "readonly");
        $(".singleWeight_input").attr("readonly", "readonly");
    } else {
        $("#stanpro").val("");
        $("#variableID").val("");
        $("#v-p").text("");
        $("#singleWeight_input").val("");
        $("#stanpro_div").hide();
        $("#variable").hide();
        $("#singleWeight").hide();
        $(".kc").attr("readonly", "");
    }
    $("#depotID").val(data.depotID);
    $("#depotName").val(data.depotName);
    $("#depotCoding").val(data.depotCoding);
    $(".marquee").text(data.text);
    //$(".depotManage").removeClass("act");
    $(".depotManage_div").hide();
}

function FeedbackInv(data) {
    const stanpro = $("#stanpro").val();
    if (stanpro == "0"){
        $(".singleWeight_input").val(data.singleWeight);
    }
    $(".kc").val(data.invNum);
}

//发布及验证
function toLaunchOrcang(f) {
    var reg = new RegExp(/^[1-9][0-9]*$/);//正整数数量
    var reg1 = new RegExp(/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,4})?$/);//称重商品重量、价格
    var reg2 = new RegExp(/^[//(//)\u4e00-\u9fa5_a-zA-Z0-9]+$/);//商品名称
    var reg3 = new RegExp(/^((?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?)|0$/);
    var dlsum = 0;
    var htlstr = '';
    var a = true;
    var aa = '';

    //代理佣金
    $(".dlyj").each(function () {
        dlsum = dlsum + parseFloat($(this).val());
    });

    //佣金
    $(".ver:gt(0)").each(function () {
        console.log("var+" + $(this).val());
        a = true;
        var num = $(this).val();
        if ($(this).val() == '') {
            aa = "请输入佣金金额";
        }else if ($(".carousel-inner").find("input").length == 0 && $.trim($(this).text()) == "") {
            aa = "请上传主图片";
        } else if (parseFloat($(".ver").eq(0).val()) < parseFloat($(this).val())) {
            console.log($(".ver").eq(0).val());
            aa = "佣金不能大于销售价格";
        } else if (!reg3.test($(this).val())) {
            aa = "佣金必须为有效佣金";
        } else if ($(this).attr("id") == "p20170605KY3VAANZJG0000000003" && parseFloat(num) > 0 && $("#tz_type").html() == "无") {
            aa = "请选择正确的投资设备类型";
        } else {
            a = false;
        }
        if (a) return false;
    });

    if ($(".ver").eq(1).val() == '')
        prompt("请输入系统销售价格");
    else if (!reg1.test($(".ver").eq(0).val())) {
        console.log($(".ver").eq(0).val());
        prompt("系统销售价格必须为有效价格！");
    } else if (a)
        prompt(aa);
    else if (parseFloat($(".ver").eq(0).val()) < dlsum)
        prompt("佣金之和不能大于销售价格");
    else if ($(".ver").eq(3).val() == '')
        prompt("请输入库存数量");
    //else if (($("#stanpro").val() == null || $("#stanpro").val() == "" || $("#stanpro").val() == "0") && !reg.test($(".kc").val()))
    //    prompt("库存必须为大于零整数！");
    else if ($("#stanpro").val() != null && $("#stanpro").val() == "1" && !reg1.test($(".kc").val()))
        prompt("库存必须为大于零！");
    /*else if ($("#complete").text() != "已编辑")
        prompt("请编辑产品描述");*/
    else if ($("#ppname").val() == "") {
        prompt("请输入商品名称");
    } else if (!reg2.test($("#ppname").val())) {
        prompt("商品名称只支持数字，英文，中文");
    } else {
        $("#edit").find(".content").each(function () {
            htlstr += $(this).html();
        });
        $("#editcontent").val(htlstr);
//		var url=basePath+"ea/productslaunch/ea_productslaunch.jspa?flag="+f;
//		$("#launchForm").attr("action",url);		
//		$("#submit").click();
        //$(".fabu").unbind('click');
        //$(".fabu").css("background", "#ddd");
        var url = basePath + "/ea/productslaunch/sajax_ea_phproductslaunch.jspa?flag=" + f;
        var formData = new FormData($("#launchForm")[0]);
        $.ajax({
            url: encodeURI(url),
            type: 'POST',
            data: formData,
            async: true,
            beforeSend: function () {
                $(".overlay").addClass("active");
                $(".loading").show();
            },
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                var member = eval("(" + data + ")");
                var boo = member.s;
                if (boo == "1") {
                    prompt("保存成功");
					//更新显示屏商品信息
                    updatePro($("#depotCoding").val());
                    ///http://localhost:8080/ea/productslaunch/ea_productsManage.jspa?user=
                    window.location.href = basePath + "/page/WFJClient/ProductsLaunch/ProductManageMobileList.jsp?";
                } else {
                    prompt("保存失败");
                    $(".overlay").removeClass("active");
                    $(".loading").hide();
                }
            },
            error: function (data) {
                prompt("保存失败！");
                $(".overlay").removeClass("active");
                $(".loading").hide();
            }
        });
    }
}

function html5Reader($file) {
    var file = $file.prop("files")[0];
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function (e) {
        var pic = document.getElementById("preview");
        pic.src = this.result;
    }
}

function prompt(obj) {
    if ($("#prompt").css("display") != "none")
        return;
    $("#prompt").find("span").text(obj);
    $("#prompt").fadeIn(500);
    setTimeout(function () {
        $("#prompt").fadeOut(500);
        $("#prompt").find("span").text("");
    }, 2000);
}

function calltiaoma(tiaoma) {
    var params = new Array();
    params.push("user=" + user);
    params.push("&companyId=" + companyId);
    var member = eval("(" + tiaoma + ")");
    var p1, p2, p3;
    p1 = member.code;
    p2 = member.price;
    p3 = member.name;
    params.push("&barcode=" + p1);
    params.push("&interval=" + p2);
    params.push("&name=" + p3);
    window.location.href = basePath + "ea/productslaunch/ea_toProductsLaunch.jspa?" + params.join("");
}

function calltiaomaIOS(tiaoma) {
    var params = new Array();
    params.push("user=" + user);
    params.push("&companyId=" + companyId);
    var member = eval("(" + tiaoma + ")");
    var p1, p2, p3;
    p1 = member.barcode;
    p2 = member.interval;
    p3 = member.name;
    params.push("&barcode=" + p1);
    params.push("&interval=" + p2);
    params.push("&name=" + p3);
    window.location.href = basePath + "ea/productslaunch/ea_toProductsLaunch.jspa?" + params.join("");
}

//设置促销产品
$(function () {
    ppId = $("#ppId").val();
    user = $("#user").val();
    $("#promotionalProducts").click(function () {
        var url = basePath + "ea/productslaunch/ea_productInquiry.jspa?productPackaging.ppID=" + ppId + "&user=" + user;
        document.location.href = url;
    });
});

//安卓，苹果返回
function retAndroid() {
    /*try{
     ajaxUploadPic();
     Android.callAndroidjianli();
     }catch(err){*/
    $("#returnClick").removeAttr("onclick");
    //$("#returnClick").attr("href",basePath+"ea/vipcenter/ea_vipDemand.jspa");
    if (sys == 'sys') {
        $("#returnClick").attr("href", basePath + "mobile/office/mobileoffice_fastApplication.jspa?");
    } else {
        $("#returnClick").attr("href", basePath + "ea/vipcenter/ea_vipDemand.jspa");
    }
    /*}*/
}

function retIOS() {
    if (ret == 'ret') {
        ajaxUploadPic();
        //calFaBu('');
        var url = "func=" + "calFaBu";
        window.webkit.messageHandlers.Native.postMessage(url);
    } else {
        $("#returnClick").removeAttr("onclick");
        if (sys == 'sys') {
            $("#returnClick").attr("href", basePath + "mobile/office/mobileoffice_fastApplication.jspa?");
        } else {
            $("#returnClick").attr("href", basePath + "ea/vipcenter/ea_vipDemand.jspa");
        }


    }
}

function goBack() {
    try {
        var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        if (isAndroid == true) {
            console.log("安卓");
            Android.callAndroidjianli();//调用安卓接口
        } else if (isiOS == true) {
            console.log("IOS");
            var url = "func=" + 'callIOSjianli';
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    } catch (error) {
        window.history.go(-1);
        return false;
    }
}

function callIOScamera() {
    var url = "func=" + 'callioscamera';
    window.webkit.messageHandlers.Native.postMessage(url);
    //callioscamera('');
}

//场地
function site() {


    var url = basePath + "ea/carmanage/sajax_ea_queryNumber.jspa";
    $.ajax({
        url: url,
        type: "post",
        async: true,
        dataType: "json",
        success: function (data) {
            var standard = eval("(" + data + ")");
            var nologin = standard.nologin;
            var number = standard.number;
            if (nologin) {
                document.location.href = basePath + "/page/WFJClient/pc/pc_login.jsp";
            }

            if (standard != null) {
                $(".siteName").attr({readonly: 'true'});

                var a = [];
                $(number).each(function (i, dom) {
                    a.push("<option date-siteId='" + this[1] + "' date-siteName='" + this[2] + "' value='" + this[1] + "'>" + this[2] + "</option>");

                });
                $(".site").append(a.join(""));
                if (siteId != "" && siteId != null && siteId != "null") {
                    $(".siteId").val(siteId);
                }
                if (carType != "" && carType != null && carType != "null") {
                    $("#carType").val(carType);
                }
                if (timeUnits != "" && timeUnits != null && timeUnits != "null") {
                    $("#timeUnits").val(timeUnits);
                }
                if (timeType != "" && timeType != null && timeType != "null") {
                    $("#timeType").val(timeType);
                }

            }
        },
        error: function (data) {
            alert("获取失败");
        }
    });
}

function getelement(data) {
    var $weitht;
    if ($('.singleWeight_input').is(':focus')) {
        $weitht = $('.singleWeight_input');
    }
    return $weitht;
}

function getPaytype(data) {
    let json = (data == "01" ? gkjy : zdjy);
    var str = [];
    str.push("<div class='div-right'> <ul class='clearfix ul-02'>");
    $.each(json, function (key, value) {
        str.push("<li id='" + key + "'>");
        str.push("<span class='codeValue'>" + value + "</span>");
        str.push("</li>");
    });
    str.push("</ul></div>");
    $(".jylx").append(str.join(""));
}

function scanCode() {
    const ua = navigator.userAgent.toLowerCase();
    const isWeixin = ua.indexOf('micromessenger') != -1;
    if (isWeixin) {
        var url = basePath
            + "ea/qrshare/sajax_ea_getJssdkConfig.jspa";
        var retUrl = location.href.split('#')[0];
        try {
            $.ajax({
                url: url,
                type: "post",
                async: false,
                dataType: "json",
                data: {
                    retUrl: retUrl
                },
                success: function (data) {
                    var m = eval("(" + data + ")");

                    wx.config({
                        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                        appId: m.appId, // 必填，公众号的唯一标识
                        timestamp: m.timestamp, // 必填，生成签名的时间戳
                        nonceStr: m.nonceStr, // 必填，生成签名的随机串
                        signature: m.signature,// 必填，签名
                        jsApiList: ["scanQRCode"] // 必填，需要使用的JS接口列表
                    });

                    wx.error(function (res) {
                        console.log(res);
                    });
                    wx.ready(function () {
                        wx.scanQRCode({
                            needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
                            scanType: ["qrCode", "barCode", "datamatrix", "pdf417"], // 可以指定扫二维码还是一维码，默认二者都有
                            success: function (result) {
                                var result = result.resultStr; // 当needResult 为 1 时，扫码返回的结果
                                if (result.indexOf(",") != -1) {
                                    $("#barCode").val(result.substring(result.indexOf(",")+1,result.length));
                                }else {
                                    console.log(res);
                                }
                            }
                        });
                    });
                }
            });
        }catch (e) {
            console.log(e);
        }
    } else {
        if (isAndroid == true) {
            Android.callcamera();
        } else if (isiOS == true) {
            callIOScamera();
        } else {
            var url = "func=" + 'calltiaomaIOS';
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    }
}