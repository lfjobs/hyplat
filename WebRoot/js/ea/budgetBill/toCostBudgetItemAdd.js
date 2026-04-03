var reader = new FileReader();
var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
var iswin = u.indexOf('Windows') > 1;
var k = 0;
var isSubmit = false;
var isupdate = true;
$(function () {
    getItemInfoPersonal();
    $("#prompt").css("position", "absolute").css("top", $(window).height() * 0.09 + "px");
    $("#prompt").find("div").css("height", $(window).height() * 0.06 + "px").css("font-size", $(window).height() * 0.0285 + "px").css("color", "#FFFFFF");
    $("#prompt").find("div").css("-moz-border-radius", $(window).height() * 0.015 + "px").css("-webkit-border-radius", $(window).height() * 0.015 + "px");

    if (true) {
        // $("#isupdate").show();
        // $(".mingcheng1").hide();
        // $(".div-tijiao").hide();
        $(".mingcheng2").show();
        if ($("#isScale").val() != null && $("#isScale").val() != "") {
            if ($("#isScale").val() == "0") {
                $(".chengzhong2 input").val("是");
                var c = $("#unitOfMeasureCode").val();
                if (c == null || c == "") {
                    $(".jijia1").show();
                } else {
                    if (c == "KGM") {
                        $(".jijia2 input").val(c + "(以重计价)");
                    } else if (c == "PCS") {
                        $(".jijia2 input").val(c + "(以数计价)");
                    }
                    $(".jijia1").hide();
                    $(".jijia2").show();
                }
                if ($("#barCode").val() != null && $("#barCode").val() != "") {
                    $("#barCode").attr("readonly", "readonly");
                    $(".hangye1").hide();
                    $(".hangye2").show();
                } else {
                    $(".tiaoma1").find("p:eq(1)").text("系统生成");
                    /*$(".tiaoma2").hide();
                     $(".tiaoma1").show();*/
                }
            } else {
                $(".chengzhong2 input").val("否");
                if (isAndroid) {
                    if ($(".barCode").val() == null || $(".barCode").val() == "") {
                        $(".tiaoma1").find("p:eq(1)").text("扫一扫");
                        $(".tiaoma2").hide();
                        $(".tiaoma1").show();
                    } else {
                        $(".tiaoma1").hide();
                        $(".tiaoma2").show();
                    }
                } else if (iswin) {
                    $(".tiaoma1").hide();
                    $(".tiaoma2").show();
                }
                $("#isScale").val("1");
                $(".jijia").hide();
            }
            $(".chengzhong1").hide();
            $(".chengzhong2").show();
        }
        if ($("#producttype").val() != null && $("#producttype").val() != "") {
            $(".xiangmu1").hide();
            $(".xiangmu2").show();
        }
        if ($("#tradeName").val() != null && $("#tradeName").val() != "") {
            $(".hangye1").hide();
            $(".hangye2").show();
        }
        if ($("#typeID").val() != null && $("#typeID").val() != "") {
            $(".wupin1").hide();
            $(".wupin2").show();
        }
        if ($("#functionList").text() != null && $("#functionList").text() > 0) {
            $(".detailed1").hide();
            $(".detailed2").show();
        }
        if ($("#arrilist").text() != null && $("#arrilist").text() > 0) {
            $(".tupian1").hide();
            $(".tupian2").show();
        }
        if ($("#arrvlist").text() != null && $("#arrvlist").text() > 0) {
            $(".shipin1").hide();
            $(".shipin2").show();
        }
        if ($("#brand").val() != null && $("#brand").val() != "") {
            $(".pinpai1").hide();
            $(".pinpai2").show();
        }
        if ($("#variableID").val() != null && $("#variableID").val() != "") {
            $(".danwei1").hide();
            $(".danwei2").show();
        }
        if ($("#gradeName").val() != null && $("#gradeName").val() != "") {
            $(".dengji1").hide();
            $(".dengji2").show();
        }
        $(".div-tijiao").show();
    } else {
        // isupdate = true;
        // $("#isupdate").hide();
        $(".div-tijiao").show();
    }

    $("#isupdate").click(function () {
        isupdate = true;
        $(".div-tijiao").show();
    });

    // 云计称重

    $(document).on("click", ".search_box", function () {
        $(".div-search").show();
    });
    // 	显示
    $(document).on("click", ".chengzhong1,.chengzhong2", function () {
        if (isupdate) {
            if (ppid == null || ppid == "") {
                $(".div-chengzhong").show();
            }
        }
    });
    // 	切换选项
    $(document).on("click", ".div-chengzhong .div-middle p", function (event) {
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
        // event.stopPropagation();//阻止冒泡
    });
    // 	关闭
    $(document).on("click", ".div-chengzhong", function (event) {
        if(event.target==this){
            $(".div-chengzhong").hide();
        }
    });

    // 	关闭
    $(document).on("click", ".div-chengzhong .div-close", function (event) {
        $(".div-chengzhong").hide();
    });

    // 	传入选项 更改显示模块
    $(document).on("click", ".div-chengzhong .div-middle>p", function (event) {
        if ($(".div-chengzhong .div-middle .active").text() == "是") {
            $("#isScale").val("0");
            $(".jijia1").show();
            // $(".tiaoma1").find("p:eq(1)").text("系统生成");
            $(".tiaoma2").hide();
            // $(".tiaoma1").show();
        } else {
            if (isAndroid) {
                $(".tiaoma1").find("p:eq(1)").text("扫一扫");
                $(".tiaoma2").hide();
                $(".tiaoma1").show();
            } else if (iswin) {
                $(".tiaoma1").hide();
                $(".tiaoma2").show();
            }
            $("#isScale").val("1");
            $(".jijia").hide();
        }
        $(".chengzhong2 input").val($(".div-chengzhong .div-middle .active").text());
        $(".div-chengzhong").hide();
        $(".chengzhong1").hide();
        $(".chengzhong2").show();
        // event.stopPropagation();//阻止冒泡
    });

    // $(document).on("click", ".div-chengzhong .div-bottom", function () {
    //     if ($(".div-chengzhong .div-middle .active").text() == "是") {
    //         $("#isScale").val("0");
    //         $(".jijia1").show();
    //         $(".tiaoma1").find("p:eq(1)").text("系统生成");
    //         $(".tiaoma2").hide();
    //         $(".tiaoma1").show();
    //     } else {
    //         if (isAndroid) {
    //             $(".tiaoma1").find("p:eq(1)").text("扫一扫");
    //             $(".tiaoma2").hide();
    //             $(".tiaoma1").show();
    //         } else if (iswin) {
    //             $(".tiaoma1").hide();
    //             $(".tiaoma2").show();
    //         }
    //         $("#isScale").val("1");
    //         $(".jijia").hide();
    //     }
    //     $(".chengzhong2 input").val($(".div-chengzhong .div-middle .active").text());
    //     $(".div-chengzhong").hide();
    //     $(".chengzhong1").hide();
    //     $(".chengzhong2").show();
    // });
    // end

    // 计价单位

    // 	显示
    $(document).on("click", ".jijia1,.jijia2", function () {
        if (isupdate) {
            if (ppid == null || ppid == "") {
                $(".div-jijia").show();
            }
        }
    });
    // 	切换选项
    $(document).on("click", ".div-jijia .div-middle p", function () {
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
    });
    // 	关闭
    $(document).on("click", ".div-jijia", function (event) {
        if(event.target==this){
            $(".div-jijia").hide();
        }
    });

    $(document).on("click", ".div-jijia .div-close", function () {
        $(".div-jijia").hide();
    });
    // 	传入选项 更改显示模块
    $(document).on("click", ".div-jijia .div-middle>p", function () {
        $(".jijia2 input").val($(".div-jijia .div-middle .active").text());
        $("#unitOfMeasureCode").val($(".div-jijia .div-middle .active").text().trim().substring(0, 3));
        $(".div-jijia").hide();
        $(".jijia1").hide();
        $(".jijia2").show();
    });
    // end

    // 物品条码
    // 	显示
    if (isAndroid) {
        $(".tiaoma1").find("p:eq(1)").text("扫一扫");
        $(".tiaoma2").hide();
        $(".tiaoma1").show();
    } else if (iswin) {
        $(".tiaoma1").hide();
        $(".tiaoma2").show();
    }
    // 	传入选项 更改显示模块
    $(document).on("click", ".tiaoma1,.tiaoma2", function () {
        if (isupdate) {
            if (isAndroid) {
                Android.callcamera();
            } else if (iswin) {
            }
        }
    });
    // end

    // 物品名称

    // 	显示
    // $(document).on("click", ".mingcheng1,.mingcheng2", function () {
    //     if (isupdate) {
    //         $(".div-mingcheng").show();
    //         /*$(".mingcheng2 input").focus();
    //         $(".mingcheng1").hide();
    //         $(".mingcheng2").show();*/
    //     }
    // });
    // 	关闭
    $(document).on("click", ".div-mingcheng .div-close", function () {
        $(".div-mingcheng").hide();
    });
    // 	传入选项 更改显示模块
    $(document).on("click", ".div-mingcheng .keep", function () {
        var b = true;
        var a = /^[\u4e00-\u9fa5_a-zA-Z0-9]+$/;
        //var reg = new RegExp(a);//商品名称
        if ($("#input-mingcheng").val() == "") {
            prompt("请输入商品名称");
            b = false;
        } else if (!a.test($("#input-mingcheng").val())) {
            prompt("商品名称只支持数字，英文，中文");
            b = false;
        } else {
            var url = basePath + "ea/productslaunch/sajax_ea_checkProName.jspa?name=" + $("#input-mingcheng").val() + "&companyId=" + companyID;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                success: function (data) {
                    var member = eval("(" + data + ")");
                    var s = member.s;
                    if (s == "0") {
                        prompt("商品名称不允许重复");
                        $("#input-mingcheng").focus();
                        b = false;
                    }
                },
                error: function () {
                    prompt("验证失败");
                    b = false;
                }
            });
        }
        if (b) {
            let vale = $(".div-mingcheng input").val();
            if (!vale == "") {
                $(".mingcheng2 input").val(vale);
                $(".div-mingcheng").hide();
                $(".mingcheng1").hide();
                $(".mingcheng2").show();
            }
        }

    });

    /*$("#input-mingcheng").blur(function () {
        var a = "/^[//(//)\u4e00-\u9fa5_a-zA-Z0-9]+$/";
        var reg = new RegExp(a);//商品名称
        if ($(this).val() == "") {
            prompt("请输入商品名称");

        } else if (!reg.test($(this).val())) {
            prompt("商品名称只支持数字，英文，中文");

        } else {
            var url = basePath + "ea/productslaunch/sajax_ea_checkProName.jspa?name=" + $(this).val() + "&companyId=" + companyID;
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
                    prompt("验证失败");
                }
            });
        }
    });*/

    // end

    // 品牌管理

    // 	显示
    $(document).on("click", ".pinpai1,.pinpai2", function () {
        if (isupdate) {
            $(".div-pinpai").show();
        }
    });
    // 	关闭
    $(document).on("click", ".div-pinpai .div-close", function () {
        $(".div-pinpai").hide();
    });
    // 	传入选项 更改显示模块
    $(document).on("click", ".div-pinpai .div-bottom", function () {
        let vale = $(".pinpaival").val();
        if (!vale == "") {
            $(".pinpai2 input").val(vale);
            $(".div-pinpai").hide();
            $(".pinpai1").hide();
            $(".pinpai2").show();
        }
    });
    // end

    // 项目分类

    // 	显示
    $(document).on("click", ".xiangmu1,.xiangmu2", function () {
        if (isupdate) {
            $(".xmfl").empty();
            getProductType("scode201410284shpd9x4fa0000000005", "", 1, 1);
            $(".div-xiangmu").show();
        }
    });
    // 	关闭
    $(document).on("click", ".div-xiangmu .div-close", function () {
        $(".div-xiangmu").hide();
        $(".xmfl").empty();
    });
    // 	切换选项
    $(document).on("click", ".div-xiangmu .ul-left li", function () {
        $(".div-right").remove();
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
        $(".div-con").removeClass("ul1 ul2 ul3");
        $(".div-con").addClass("ul1");
    });
    // 	缩进
    $(document).on("click", ".div-xiangmu .div-right .ul-02 li", function () {
        $(".div-xiangmu .div-right .ul-03").remove();
        $(".div-xiangmu .div-right .ul-04").remove();
        $(".div-con").removeClass("ul1 ul2 ul3");
        $(".div-con").addClass("ul2");
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
    });
    $(document).on("click", ".div-xiangmu .div-right .ul-03 li", function () {
        $(".div-xiangmu .div-right .ul-04").remove();
        $(".div-con").removeClass("ul1 ul2 ul3");
        $(".div-con").addClass("ul3");
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
    });
    $(document).on("click", ".div-xiangmu .div-right .ul-04 li", function () {
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
        $(".xiangmu2 #producttype").val($(this).find(".codeValue").text());
        $(".xiangmu2 #productCode").val($(this).find(".codeSn").text());
        $(".div-xiangmu").hide();
        $(".xiangmu1").hide();
        $(".xiangmu2").show();
        $(".xmfl").empty();
    });
    // end

    // 行业分类

    // 	显示
    $(document).on("click", ".hangye1,.hangye2", function () {
        if (isupdate) {
            $(".hyfl").empty();
            getProductType("scode20170714cnjcrn5jm20000000067", "", 1, 2);
            $(".div-hangye").show();
        }
    });
    // 	关闭
    $(document).on("click", ".div-hangye .div-close", function () {
        $(".div-hangye").hide();
        $(".hyfl").empty();
    });
    // 	切换选项
    $(document).on("click", ".div-hangye .ul-left li", function () {
        $(".div-right").remove();
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
    });
    // 	传入选项 更改显示模块
    $(document).on("click", ".div-hangye .div-right>ul li", function () {
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
        $(".hangye2 #tradeName").val($(this).find(".pvalue").text());
        $(".hangye2 #tradeID").val($(this).attr("id"));
        $(".hangye2 #tradeCode").val($(this).find(".codeSn").text() + $(this).find(".codeValue").text());
        $(".div-hangye").hide();
        $(".hangye1").hide();
        $(".hangye2").show();
        $(".hyfl").empty();
    });
    // end


    // 首页图片

    // 	显示
    $(document).on("click", ".tupian1,.tupian2", function () {
        $(".div-tupian2").css({"opacity": "1", "transform": 'translate(0)'});
    });
    // 	关闭
    $(".div-tupian2 .div-close").on("click", function () {
        $(".div-tupian2").css({"opacity": "0", "transform": 'translate(1000000px)'});
    });
    // 	上方切换
    $(".div-tupian2 .ul-top li").on("click", function () {
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
        $(".div-tupian2 .div-tab .div-01").hide();
        $(".div-tupian2 .div-tab .div-01").eq($(this).index()).show();
    });

    // 	传入选项 更改显示模块
    $(document).on("click", ".div-tupian2 .p-tijiao", function () {
        $(".div-tupian2").css({"opacity": "0", "transform": 'translate(1000000px)'});
        $(".diyImagesPath").each(function () {
            $(".tupian2 div").append("<img src='" + basePath + $(this).text() + "'/>");
        });
        $(".tupian1").hide();
        $(".tupian2").show();
    });


    // 	显示
    $(document).on("click", ".shipin1,.shipin2", function () {
        $(".div-shipin2").css({"opacity": "1", "transform": 'translate(0)'});
    });
    // 	关闭
    $(document).on("click", ".div-shipin2 .div-close", function () {
        $(".div-shipin2").css({"opacity": "0", "transform": 'translate(1000000px)'});
    });
    // 	上方切换
    $(document).on("click", ".div-shipin2 .ul-top li", function () {
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
        $(".div-shipin .div-tab .div-01").hide();
        $(".div-shipin .div-tab .div-01").eq($(this).index()).show();
    });
    // 	传入选项 更改显示模块
    $(document).on("click", ".div-shipin2 .p-tijiao", function () {
        $(".div-shipin2").css({"opacity": "0", "transform": 'translate(1000000px)'});
        $(".diyVideoPath").each(function () {
            $(".shipin2 div").append("<img src='" + basePath + "/images/ea/production/drive/k2_top03.png'/>");
        });
        $(".shipin1").hide();
        $(".shipin2").show();
    });

    // 单位

    // 	显示
    $(document).on("click", ".danwei1,.danwei2", function () {
        if (isupdate) {
            $(".dwgl").empty();
            getProductType("scode20101014v5zed7cukk0000000003", "", 1, 4);
            $(".div-danwei").show();
        }
    });
    // 	切换选项
    $(document).on("click", ".div-danwei .div-middle p", function () {
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
    });
    // 	关闭
    $(document).on("click", ".div-danwei", function (event) {
        if(event.target==this){
            $(".div-danwei").hide();
            $(".dwgl").empty();
        }
    });
    $(document).on("click", ".div-danwei .div-close", function () {
        $(".div-danwei").hide();
        $(".dwgl").empty();
    });
    // 	传入选项 更改显示模块
    $(document).on("click", ".div-danwei .div-middle p", function () {
        $(".danwei2 input").val($(".div-danwei .div-middle .active .codeValue").text());
        $(".div-danwei").hide();
        $(".danwei1").hide();
        $(".danwei2").show();
        $(".dwgl").empty();
    });
    // end

    // 等级名称
    // 	显示
    $(document).on("click", ".dengji1,.dengji2", function () {
        if (isupdate) {
            $(".djgl").empty();
            getProductType("scode20230220rshfv95t550000000002", "", 1, 5);
            $(".div-dengji").show();
        }
    });
    // 	关闭
    // $(".div-danwei .div-close").on("click",function(){
    // 	$(".div-danwei").hide();
    // })

    $(document).on("click", ".div-dengji", function (event) {
        if(event.target==this){
            $(".div-dengji").hide();
        }
    });
    $(document).on("click", ".div-dengji .div-close", function (event) {
        $(".div-dengji").hide();
    });
    // 	传入选项 更改显示模块
    $(document).on("click", ".div-dengji ul li", function () {
        $(".dengji2 #gradeName").val($(this).find(".codeValue").text());
        $(".dengji2 #gradeid").val($(this).attr("id"));
        $(".div-dengji").hide();
        $(".dengji1").hide();
        $(".dengji2").show();
        $(".djgl").empty();
    });
    // end

    // 规格

    // 	显示
    $(document).on("click", ".guige1,.guige2", function () {
        // if ($("#tradeName").val().indexOf('机械工业加工') > 1) {
        //     getAttri2($("#goodsID").val());
        //     $(".div-guige2").show();
        // } else {
        //     getAttri1($("#goodsID").val());
        //     $(".div-guige1").show();
        // }
        $(".div-guigeType").show();
        //$(".div-guige").show();
    });

    // 	传入选项 更改显示模块
    $(document).on("click", ".div-guigeType ul li", function () {
        $("#guigeType").val($(this).text());
        var type = $("#guigeType").val();
        $(".div-dengji").hide();
        $(".dengji1").hide();
        $(".dengji2").show();
        $(".djgl").empty();
    });

    // 	关闭
    $(document).on("click", ".div-guigeType", function (event) {
        if(event.target==this){
            $(".div-guigeType").hide();
        }
    });
    $(document).on("click", ".div-guigeType .div-close", function () {
        $(".div-guigeType").hide();
    });

    // 	关闭
    $(document).on("click", ".div-guige .div-close", function () {
        $(".div-guige").hide();
    });
    // 	传入选项 更改显示模块
    $(document).on("click", ".div-guige .keep", function () {
        colorvalue = "";
        sizevalue = "";
        $(".color").find(".active").each(function () {
            colorvalue += $(this).text().trim() + ",";
        });
        $(".size").find(".active").each(function () {
            sizevalue += $(this).text().trim() + ",";
        });
        $(".color").find(".input_xz").each(function () {
            colorvalue += $(this).val() + ",";
        });
        $(".size").find(".input_xz").each(function () {
            sizevalue += $(this).val() + ",";
        });
        var attrinames = $(".color").find(".sezi_title").val();
        var attrinamec = $(".size").find(".sezi_title").val();
        $("#colorvalue").val(colorvalue);
        $("#sizevalue").val(sizevalue);
        $("#attrinames").val(attrinames);
        $("#attrinamec").val(attrinamec);
        if ((colorvalue != null && colorvalue != "") || (sizevalue != null && sizevalue != "")) {
            $(".div-guige").hide();
            $(".guige1").hide();
            $(".guige2").show();
        } else if ((colorvalue == null || colorvalue == "") && (sizevalue == null || sizevalue == "")) {
            $(".div-guige").hide();
        }

        $(".product_size").hide();
    });

    $(document).on("click", ".div-guige .p-bottom", function () {
        $(".div-guige2 .ul-con .clearfix").each(function () {
            // console.log($(this).html());
            // console.log($(this).find(".attri1 ").val());
            $(this).find(".attri2 ").val($(this).find(".attri2 ").val() + $(this).find(".attri1 ").val());
            // console.log($(this).find(".attri2 ").val());
        });
        var attri = "";
        $(".div-guige2").find(".attri2").each(function () {
            attri += $(this).val().trim() + ",";
        });
        $("#attri").val(attri);
        $(".div-guige").hide();
        $(".guige1").hide();
        $(".guige2").show();
    });

    $(document).on("click", ".div-guige1 ul li", function () {
        if ($(this).is(".active")) {
            $(this).removeClass("active");
        } else {
            $(this).addClass("active");
        }
    });
    $(document).on("click", ".div-guige1 .add-input", function () {
        const divInput = `
				<div class="div-input">
					<input type="text" placeholder="自定义" class="input_xz">
					<p class="p-delete">
						<img src="` + basePath + `images/WFJClient/PersonalJoining/size_del.png" >
					</p>
				</div>
			`
        $(this).siblings('.inpAdd').append(divInput)
    });
    $(document).on("click", '.inpAdd .div-input .p-delete', function (e) {
        $(this).parent().remove();
        e.stopPropagation();
    });

    // end

    /*产品详情*/
    $(document).on("click", ".detailed1,.detailed2", function () {
        $(".product_describe").show();
    });
    // 	关闭
    $(".product_describe .div-close").on("click", function () {
        $(".product_describe").hide();
    });
    $(document).on('click', '.moren', function () {
        ($(this).text() == '此处添加文字描述') && $(this).text('')
    });
    //多图片上传预览
    $(".foot_g").click(function () {
        d = $(".content").length;
        d = d - 1;
        $("#content" + d).append("<div class='p' id='p" + d + "'>" +
            " <input id='file" + d + "' type='file' name='pic' style='display:none' accept='image/png,image/jpeg,image/gif,image/jpg,image/bmp,image/webp' onchange='setImagePreviews(this.id)'/></div>");
        $("#file" + d).click();
    });

    $(document).on("click", ".editablesmall", function () {
        if ($.trim($(this).text()) == '此处添加文字描述') {
            $(this).find(".moren").text("");
        }
    });

    $(document).on("click", ".foot_r", function () {
        $(".pic img").each(function () {
            $(this).attr("id", "img" + k + "");
            k++;
        });
        $(".editablesmall p").each(function () {
            // console.log($.trim($(this).text()));
            if ($.trim($(this).text()) == "此处添加文字描述") {
                $(".editable").find(".editablesmall p").text("");
            }
        });
        if ($("#edit").find("input").length == 0) {
            prompt("请添加图片");
        } else {
            $(".product_describe").hide();
            $(".detailed1").hide();
            $(".detailed2").show();
        }
    });

    $(document).on("click", ".des", function () {
        $(".product_describe").hide();
    });

    $(document).on("click", ".pic img", function () {
        $(this).parent().attr("class", "delpic");
        bgstr = '<div id="ImgZoomInBG" style=" background:#000000; filter:Alpha(Opacity=70); opacity:0.7; position:fixed; left:0; top:0; z-index:10000; width:100%; height:100%; display:none;"><iframe src="about:blank" frameborder="5px" scrolling="yes" style="width:100%; height:100%;"></iframe></div>';
        imgstr = '<img id="ImgZoomInImage2" src="' + $(this).attr("src") + '" data-id="' + $(this).attr("data-id") + '" onclick="quxiao()" style="cursor:pointer; display:none; position:absolute; z-index:10000;" /><img id="deletes" style="position: absolute;display:none;" onclick="Delete()" src="' + basePath + 'images/WFJClient/PersonalJoining/cutout.png"/>';
        if ($('#ImgZoomInBG').length < 1) {
            $('body').append(bgstr);
        }
        if ($('#ImgZoomInImage2').length < 1) {
            $('body').append(imgstr);
        } else {
            $('#ImgZoomInImage2').attr('src', $(this).attr('src'));
            $('#ImgZoomInImage2').attr('data-id', $(this).attr('data-id'));
        }
        $('#ImgZoomInImage2').css('left', $(window).scrollLeft() + ($(window).width() - $('#ImgZoomInImage2').width()) / 2);
        $('#ImgZoomInImage2').css('top', $(window).scrollTop() + ($(window).height() - $('#ImgZoomInImage2').height()) / 2);
        $('#delete').css({
            "position": "absolute",
            'right': $(window).scrollLeft() + ($(window).width() - $('#ImgZoomInImage2').width() - $(this).width() * 0.1) / 2,
            'top': $(window).scrollTop() + ($(window).height() - $('#ImgZoomInImage2').height() - $(this).height() * 0.2) / 2,
            "width": "30px",
            "z-index": "10001"
        })
        $('#deletes').css({"position": "absolute", 'right': "50%", 'top': "50%", "width": "30px", "z-index": "10001"})
        $('#deletes').show();
        $('#delete').show();
        $('#ImgZoomInBG').show();
        $('#ImgZoomInImage2').show();
    });

});
//
// function calltiaoma(tiaoma) {
//     var params = [];
//     params.push("user=");
//     params.push("&companyId=" + companyId);
//     var member = eval("(" + tiaoma + ")");
//     var p1, p3;
//     p1 = member.code;
//     p3 = member.name;
//     params.push("&barcode=" + p1);
//     params.push("&name=" + p3);
//     window.location.href = basePath + "ea/productsmanag/ea_toProductsLaunch.jspa?" + params.join("");
// }

function f_change1(file) {
    var img = document.getElementById('imgSdf1');
    //读取File对象的数据
    reader.onload = function (evt) {
        //data:img base64 编码数据显示
        img.width = "300";
//		        img.height =  "100";
        img.src = evt.target.result;
    }
    reader.readAsDataURL(file.files[0]);
}

function f_change2(file) {
    var img = document.getElementById('imgSdf2');
    //读取File对象的数据
    reader.onload = function (evt) {
        //data:img base64 编码数据显示
        img.width = "300";
//		        img.height =  "100";
        img.src = evt.target.result;
    }
    reader.readAsDataURL(file.files[0]);
}

function f_change3(file) {
    var img = document.getElementById('imgSdf3');
    //读取File对象的数据
    reader.onload = function (evt) {
        //data:img base64 编码数据显示
        img.width = "200";
        img.height = "100";
        img.src = evt.target.result;
    }
    reader.readAsDataURL(file.files[0]);
}

//项目分类、行业类别数据查询
function getProductType(pid, pvalue, ti, type) {
    var getcodeurl = basePath + "ea/productsmanag/sajax_ea_getListCCodeByPID.jspa?date="
        + new Date().toLocaleString();
    $.ajax({
        url: encodeURI(getcodeurl),
        type: "get",
        async: true,
        dataType: "json",
        data: {
            codeID: pid,
            companyID: companyID
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var oList = member.codeList;
            var listhtml = "";
            var tii = ti + 1;
            var claname = "";
            var $divHtml;
            var str = [];
            if (null == oList || oList.length <= 0) {
                if (type == 1 && ti != 5) {
                    $(".xiangmu2 #producttype").val($("#" + pid).find(".codeValue").text());
                    $(".xiangmu2 #productCode").val($("#" + pid).find(".codeSn").text());
                    $(".div-xiangmu").hide();
                    $(".xiangmu1").hide();
                    $(".xiangmu2").show();
                }
                return;
            }
            if (type == 1) {
                claname = "xmfl-right";
                $divHtml = $(".xmfl");
            } else if (type == 2) {
                claname = "hyfl-right";
                $divHtml = $(".hyfl");
            } else if (type == 3) {
                claname = "wplb-right";
                $divHtml = $(".wplb");
            } else if (type == 4) {
                $divHtml = $(".dwgl");
            } else if (type == 5) {
                $divHtml = $(".djgl");
            }
            if (ti == 1) {
                str.push("<ul class='ul-left'>");
            } else if (ti == 2) {
                str.push("<div class='div-right " + claname + "'> <ul class='ul-02'>");
            } else if (ti == 3) {
                str.push("<ul class='clearfix ul-03'>");
            } else if (ti == 4) {
                str.push("<ul class='clearfix ul-04'>");
            }
            if (pvalue != null && pvalue != "") {
                pvalue = pvalue + "\>";
            }
            for (var i = 0; i < oList.length; i++) {

                if (type == 4) {
                    str.push("<p id='" + oList[i].codeID + "'>");
                    str.push("<span class='codeValue'>" + oList[i].codeValue + "</span>");
                    str.push("<span class='codeSn' style='display: none'>" + oList[i].codeSn + "</span>");
                    str.push("<span class='pvalue' style='display: none'>" + pvalue + oList[i].codeValue + "</span>");
                    str.push("</p>");
                } else {
                    str.push("<li id='" + oList[i].codeID + "' onclick='getProductType(" + "\"" + oList[i].codeID + "\",\"" + pvalue + oList[i].codeSn + oList[i].codeValue + "\"," + tii + "," + type + ")'>");
                    str.push("<span class='codeValue'>" + oList[i].codeValue + "</span>");
                    str.push("<span class='codeSn' style='display: none'>" + oList[i].codeSn + "</span>");
                    str.push("<span class='pvalue' style='display: none'>" + pvalue + oList[i].codeSn + oList[i].codeValue + "</span>");
                    str.push("</li>");
                    //(" + oList[i].codeSn + ")
                }
            }
            if (type == 4) {
                $divHtml.append(str.join(""));
            } else {
                if (ti == 1) {
                    str.push("</ul>");
                    $divHtml.append(str.join(""));
                } else if (ti == 2) {
                    str.push("</ul></div>");
                    $divHtml.append(str.join(""));
                } else if (ti == 3 || ti == 4) {
                    str.push("</ul>");
                    $("." + claname + "").append(str.join(""));
                }
            }
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

function getAttri1(g) {
    if (g != "") {
        $.ajax({
            url: basePath
                + "ea/productsmanag/sajax_ea_specifications.jspa",
            type: "post",
            async: false,
            data: {
                "goodsId": g,
                "v": 1
            },
            datatype: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                var t = member.map;
                if (typeof t == "object" && !(t instanceof Array)) {
                    var hasProp = false;
                    for (var prop in t) {
                        hasProp = true;
                        break;
                    }
                    if (hasProp) {
                        var str = [];
                        var arr = [];
                        $(".color").html("");
                        $(".size").html("");
                        $.each(t, function (i, teach) {
                            var at = i.charAt(i.length - 1);
                            if (at == "1") {
                                str.push('<input class="sezi_title"  value="' + i.substring(0, i.lastIndexOf('1')) + '">');
                                if (teach != null) {
                                    str.push('<div class="inpAdd clearfix"></div><p class="add-input">自定义</p>');
                                    str.push('<ul class="clearfix size_old">');
                                    for (var j = 0; j < teach.length; j++) {
                                        str.push('<li class="active">' + teach[j].attrivalue + '</li>');
                                    }
                                    str.push('</ul>');
                                }
                            } else if (at == "0") {
                                arr.push('<input class="sezi_title"  value="' + i.substring(0, i.lastIndexOf('0')) + '">');
                                if (teach != null) {
                                    arr.push('<div class="inpAdd clearfix"></div><p class="add-input">自定义</p>');
                                    arr.push('<ul class="clearfix size_old">');
                                    for (var j = 0; j < teach.length; j++) {
                                        arr.push('<li class="active">' + teach[j].attrivalue + '</li>');
                                    }
                                    arr.push('</ul>');
                                }
                            }

                        });
                        /*if (str.length == 0) {
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
                         }*/
                        $(".color").append(str.join(""));
                        $(".size").append(arr.join(""));
                    }
                }
            }
        });
    }
}

function getAttri2(g) {
    if (g != "") {
        $.ajax({
            url: basePath
                + "ea/productsmanag/sajax_ea_specifications.jspa",
            type: "post",
            async: false,
            data: {
                "goodsId": g,
                "v": 4
            },
            datatype: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                var t = member.map.attri;
                var str = [];
                if (t != null && t != "") {
                    $(".ul-con").html("");
                    for (var i = 0, len = t.length; i < len; i++) {
                        str.push('<li class="clearfix">');
                        str.push('<p>' + t[i].attriname + '</p>');
                        str.push('<input type="number" class="attri1" value="' + t[i].attrivalue + '">');
                        str.push('<input type="hidden" class="attri2" value="' + t[i].attriname + '-' + t[i].attrivalue + '">');
                        str.push('<p>cm</p></li>');
                    }
                    $(".ul-con").append(str.join(""));
                }
            }
        });
    }
}

function setImagePreviews(id) {
    //获得input=file对象
    var docObj = document.getElementById(id);
    var fileList = docObj.files;
    for (var i = 0; i < fileList.length; i++) {
        $("#p" + d).append("<div class='pic' style='float:left; width:100%;padding-top:1%;' > <img id='img" + (d + i) + "' data-id='" + id + "'/> </div>");
        //获得该input下一个兄弟节点img
        var imgObjPreview = docObj.parentNode.childNodes[i + 2].childNodes[1];
        if (docObj.files && docObj.files[i]) {
            //火狐下，直接设img属性
            imgObjPreview.style.display = 'block';
            imgObjPreview.style.width = '100%';
            imgObjPreview.style.height = 'auto';
            //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
            imgObjPreview.src = window.URL.createObjectURL(docObj.files[i]);
        } else {
            //IE下，使用滤镜
            docObj.select();
            var imgSrc = document.selection.createRange().text;
            var localImagId = docObj.parentNode.childNodes[i + 2].childNodes[1];
            //必须设置初始大小
            localImagId.style.width = "100%";
            localImagId.style.height = "auto";
            //图片异常的捕捉，防止用户修改后缀来伪造图片
            try {
                localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
            } catch (e) {
                prompt("您上传的图片格式不正确，请重新选择!");
                return false;
            }
            imgObjPreview.style.display = 'none';
            document.selection.empty();
        }
    }
    $("#edit").append("<div class='content' id='content" + (d + 1) + "'><div contenteditable='true' class='editablesmall'><p class='moren'>此处添加文字描述</p></div></div>");
    $(".editablesmall").css({"height": $(window).height() * 0.1 + "px"});
    return true;
}

function Delete() {
    if (window.confirm('确定删除图片？')) {
        var apId = $("#ImgZoomInImage2").attr("src");
        if (apId.indexOf("blob:") == 0) {
            $("#" + $("#ImgZoomInImage2").attr("data-id")).parent().siblings().remove();
            $("#" + $("#ImgZoomInImage2").attr("data-id")).parent().remove();
            /* $("#"+$("#ImgZoomInImage").attr("data-id")).parents("content").remove();*/
            prompt("操作成功！");
            $(".delpic").remove();
        } else {
            var url = basePath + "ea/productslaunch/sajax_ea_delPhoto.jspa?apId=" + apId;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                success: function (data) {
                    var member = eval("(" + data + ")");
                    var flag = member.flag;
                    if (flag == "2") {
                        prompt("操作成功！");
                    }
                },
                error: function () {
                    prompt("操作失败！");
                }
            });
            $(".delpic").remove();
        }
    }
    $('#delete').hide();
    $("#deletes").hide();
    $('#ImgZoomInBG').hide();
    $('#ImgZoomInImage2').hide();
    $(".ImgZoomInImage2").attr("src", "");
    /* $("#edit").append("<div class='content' id='content"+(d)+"'><div contenteditable='true' class='editablesmall'><p class='moren'>此处添加文字描述</p></div></div>");
     $(".editablesmall").css({"height":$(window).height()*0.1+"px"});*/
}

function quxiao() {
    $("#delete").hide();
    $("#deletes").hide();
    $("#ImgZoomInImage2").hide();
    $("#ImgZoomInBG").hide();
    $(".delpic").attr("class", "pic");
}

//弹框
function prompt(obj) {
    if ($("#prompt").css("display") != "none")
        return;
    $("#prompt").find("span").text(obj);
    $("#prompt").fadeIn(500);
    $("#prompt").show();
    setTimeout(function () {
        $("#prompt").fadeOut(500);
        $("#prompt").find("span").text("");
    }, 3000);
}

//保存
function toLaunchOrcang(f) {
    if (isSubmit) return false;
    var reg = new RegExp(/^[1-9][0-9]*$/);//数量
    var reg4 = new RegExp(/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,4})?$/);//称重商品重量
    var reg1 = new RegExp(/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,4})?$/);
    var reg2 = new RegExp(/^[//(//)\u4e00-\u9fa5_a-zA-Z0-9]+$/);//商品名称
    var reg3 = new RegExp(/^((?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?)|0$/);
    var dlsum = 0;
    var htlstr = '';
    var a = true;
    var aa = '';


    if ($("#ppname").val() == "") {
        prompt("请输入商品名称");
    } else if (!reg2.test($("#ppname").val())) {
        prompt("商品名称只支持数字，英文，中文");
    } else {
        $("#edit").find(".content").each(function () {
            htlstr += $(this).html();
        });
        $("#editcontent").val(htlstr);
        isSubmit = true;
        var url = basePath + "ea/scBudget/ea_toAddCostBudgetBill.jspa";
        if(editFlag == "edit"){
            url = basePath + "ea/scBudget/ea_toUpdateCostBudgetBill.jspa?cashierBillsId="+cashierBillsId;
        }
        $("#launchForm").attr('action',url);    //通过jquery为action属性赋值
        $("#launchForm").submit();    //提交ID为myform的表单
        // var formData = new FormData($("#launchForm")[0]);
        // $.ajax({
        //     url: encodeURI(url),
        //     type: 'POST',
        //     data: formData,
        //     async: true,
        //     beforeSend: function () {
        //         $(".overlay").addClass("active");
        //         $(".overlay span").text("产品存入中，请稍后！！！");
        //         $(".loading").show();
        //     },
        //     cache: false,
        //     contentType: false,
        //     processData: false,
        //     beforeSend: function () {
        //         $(".div-tijiao").text("正在保存...").attr('disabled', true);
        //     },
        //     complete: function () {
        //         $(".div-tijiao").text("提交").attr('disabled', false);
        //     },
        //     success: function (data) {
        //         var member = eval("(" + data + ")");
        //         var boo = member.s;
        //         if (boo == "1") {
        //             isSubmit = false;
        //             prompt("保存成功");
        //             window.location.href = basePath + "/page/WFJClient/ProductsLaunch/ProductManage.jsp?companyID=" + companyID + "&staffID=" + staffID;
        //         } else {
        //             isSubmit = false;
        //             prompt("保存失败");
        //             $(".overlay").removeClass("active");
        //             $(".loading").hide();
        //         }
        //     },
        //     error: function (data) {
        //         prompt("保存失败！");
        //     }
        // });
    }


}

// 商品点击选中
$(document).on("click", ".ul_con aside", function () {
    if ($(this).is(".aside_yes")) {
        $(this).removeClass().addClass("aside_no");
    } else {
        $(this).removeClass().addClass("aside_yes");
    }
})

$(document).ready(function () {
    // console.log("计算");
    countPrice();//计算商品价格
});

/**
 * 计算金额
 */
function countPrice() {
    var vals = 0;//总金额
    var num = 0;//商品数量
    $(".ttsw_jj").each(function () {
        num++;
        vals = parseFloat(vals) + (parseFloat(isNumber($(this).val()) ? $(this).val() : 0));
        console.log(vals+";"+$(this).val());
    });
    $("#ttsw_allPrice").text(vals);
    $("#ttsw_num_pro").text(num);
}
function isNumber(str) {
    return /^[+-]?[0-9]*(\.[0-9]+)?$/.test(str);
}
//商品点击选中
$(document).on("click","#sec-checked",function(){
    if($(this).find("aside").is(".aside_yes")){
        $(this).find("aside").removeClass().addClass("aside_no");
    }else{
        $(this).find("aside").removeClass().addClass("aside_yes");
    }
})


/**
 * 添加
 */
function toAddItemBack(){
    var url = "ea_scanningCostBudgetInfo.jspa";
    $("#f1").attr('action',url);    //通过jquery为action属性赋值
    $("#f1").submit();    //提交ID为myform的表单
}

function toAddItem(){
    var wupinType = $("#wupinType").val();
    var wupinTypeId = $("#wupinTypeId").val();
    if(!wupinTypeId){
        alert("请选择物品分类");
        return false;
    }
    $("#ttsw_hid_goodsPurpose").val(wupinType);
    $("#ttsw_hid_goodsPurposeId").val(wupinTypeId);
    $(".div-name").show();
    $("body").addClass("body_yc");
    // var url = "ea_scanningCostBudgetInfo.jspa";
    // $("#f1").attr('action',url);    //通过jquery为action属性赋值
    // $("#f1").submit();    //提交ID为myform的表单
}

function closeInfo(){
    $(".div-name").hide();
    $("body").removeClass("body_yc");
    // var url = "ea_scanningCostBudgetInfo.jspa";
    // $("#f1").attr('action',url);    //通过jquery为action属性赋值
    // $("#f1").submit();    //提交ID为myform的表单
}
//商品点击选中
$(document).on("click","#sec-checked",function(){
    if($(this).find("aside").is(".aside_yes")){
        $(this).find("aside").removeClass().addClass("aside_no");
    }else{
        $(this).find("aside").removeClass().addClass("aside_yes");
    }
})

function initItemInfo() {
    pagenumber = 1;
    var search = $('#ttsw_item_search_id').val();//取录入的模糊查询条件
    var url = basePath + "ea/scBudget/sajax_ea_getCostBudgetInfo.jspa";
    $.ajax({
        url: url,
        type: "POST",
        data: {
            "costAddBean.companyId": companyId,
            "search": search.trim(),
        },
        dataType: "json",
        success: function cbf(data) {
            var member = (new Function("return " + data))();//格式化返回参数
            var oList = member.goods;
            //拼接html页面
            var innerHtml="";
            if (oList != null && oList.length>0) {
                innerHtml = '<tr>'
                    + '<th colspan="2"></th>'
                    + '<th>物品条码</th>'
                    + '<th>物品名称</th>'
                    + '</tr>';
                // pagecount = oList.pageCount;
                // count = oList.recordCount;
                // pageSize = oList.pageSize;
                // var pageNum = oList.list.length - 1;
                getHeight();//启动定时器刷新下拉距离刷新用
                innerHtml += itemHtml(oList);
            } else {
                innerHtml += nullHtml();
            }
            $('#ttsw_item_id').html(innerHtml);
            //初始化显示页面
            $(".div-name").show();
            $("body").addClass("body_yc");
        }, error: function cbf(data) {
            alert("数据获取失败！");
        }
    });
}

/**
 * 无数据拼接页面
 */
function nullHtml() {
    var innerHtml = '<tr><td colspan="4">未查询到该条件相关数据，请联系公司负责人在 办公室>规划管理>项目资产>项目物品>物品管理 中进行添加</td></tr>';
    return innerHtml;
}

/**
 * 异步获取加载数据
 */
function ajaxItemInfor() {
    pagenumber += 1;
    var search = $('#ttsw_item_search_id').val();//取录入的模糊查询条件
    var url = basePath + "ea/scBudget/sajax_ea_getCostBudgetInfo.jspa";
    $.ajax({
        url: url,
        type: "POST",
        async: true,//默认设置为true，所有请求均为异步请求
        data: {
            "costAddBean.companyId": companyId,
            "search": search,
        },
        dataType: "json",
        success: function (data) {
            var member = (new Function("return " + data))();//格式化返回参数
            var oList = member.goods;
            $(".ttsw_last").removeClass("ttsw_last");//清空样式
            var innerHtml = '';
            if (oList != null) {
                // pagecount = oList.pageCount;
                // count = oList.recordCount;
                // pageSize = oList.pageSize;
                // var pageNum = oList.list.length - 1;
                $(".ttsw_last").removeClass("ttsw_last");
                innerHtml += itemHtml(oList);
            } else {
                innerHtml += nullHtml();
            }
            $('#ttsw_item_id').append(innerHtml);
        },
        error: function (data) {
            alert("加载下一页失败");
        }
    });
}


//将选中的项目在父级页面呈现
$("#div_table").height(($(".div-name").height() - $(".header").outerHeight(true) - $(".button").outerHeight(true)) * 0.835 + "px");
$("#p_add").click(function () {
    clearTimeout(timer);//暂时定时器
    timer = null;
    var barCode = $("#div_table").find(".active").children("td:nth-of-type(1)").attr("id");
    $("#ttsw_hid_barcode").val(barCode);//所选项目id
    $("#div_table").find(".active").children("td:nth-of-type(2)").text();
    if(barCode){
        var url = "ea_scanningCostBudgetInfo.jspa?editFlag="+editFlag+"&cashierBillsId="+cashierBillsId+"&billsType="+billsType;
        $("#f1").attr('action',url);    //通过jquery为action属性赋值
        $("#f1").submit();    //提交ID为myform的表单
    }else{
        alert("该商品没有条形码，请联系管理员进行处理");
        return;
    }
    $(this).parents(".div-name").hide();
    $("body").removeClass("body_yc");
});


/**
 * 根据数据拼接项目页面
 * @param oList 异步获取信息
 * @param pageNum 总数据数-1
 * @returns {string} 返回拼接好的td 页面
 */
function itemHtml(oList) {
    var innerHtml = '';
    for (var i = 0; i < oList.length; i++) {
        obj = oList[i];
        // if (obj.barCode != null && obj.barCode != "") {
            innerHtml += '<tr class="ttsw_last">';
            innerHtml += '<td colspan="2" id="' + obj.barCode + '" cusXmtype="' + obj.xmtype + '" cusXmcode="' + obj.goodsNum + '">'
                + '<aside class="aside_no">'
                + '<img class="img_no" src="' + basePath + 'images/scMobile/payBudget/addBudget/wupinguanli_07.png"/>'
                + '<img class="img_yes" src="' + basePath + 'images/scMobile/payBudget/addBudget/wupinguanli_10.png"/>'
                + '</aside>'
                + '</td>'
                + '<td>' + (obj.barCode != null ? obj.barCode : "") + '</td>'
                + '<td' +
                '' +
                '>' + (obj.goodsName != null ? obj.goodsName : "") + '</td>'
                + '</tr>';
        // }
    }
    return innerHtml;
}


$(document).on("click", "#div_table tr", function () {
    if ($(this).find(".img_no").is(":hidden")) {
        $(this).find(".img_no").show();
        $(this).find(".img_yes").hide();
        $(this).removeClass("active");
    } else {
        $("#div_table aside .img_yes").hide();
        $("#div_table aside .img_no").show();
        $("#div_table tr").removeClass("active");
        $(this).find(".img_no").hide();
        $(this).find(".img_yes").show();
        $(this).addClass("active");
    }
})

/**
 * 定时刷新判断页面距底高度
 */
function getHeight() {
    clearTimeout(timer);//清空定时器
    timer = setTimeout("getHeight()", 1000);
    if ($(".ttsw_last").length > 0) {
        //li偏移量-li的3倍高度<=页面高度时
        if ($(".ttsw_last").offset().top - $(".ttsw_last").height() * 3 <= $(window).height()) {
            if (pagenumber < pagecount) {
                ajaxItemInfor();
            }
        }
    }
}

//物品分类
// 	显示
// $(document).on("click", "#wupinType", function () {
//     $(".wplb").empty();
//     getProductTypeNew("", "", 1, 3);
//     $(".div-wupin").show();
// });
// 	关闭
$(document).on("click", ".div-wupin .div-close", function () {
    $(".div-wupin").hide();
    $(".wplb").empty();
});
// 	切换选项
$(document).on("click", ".div-wupin .div-right .ul-02 li", function () {
    $(".div-wupin .div-right .ul-03").remove();
    $(".div-wupin .div-right .ul-04").remove();
    $(".div-con").removeClass("ul1 ul2 ul3");
    $(".div-con").addClass("ul2");
    $(this).addClass("active");
    $(this).siblings().removeClass("active");
});
$(document).on("click", ".div-wupin .div-right .ul-03 li", function () {
    $(".div-wupin .div-right .ul-04").remove();
    $(".div-con").removeClass("ul1 ul2 ul3");
    $(".div-con").addClass("ul3");
    $(this).addClass("active");
    $(this).siblings().removeClass("active");
});
// 	切换选项
$(document).on("click", ".div-wupin .ul-left li", function () {
    $(".div-right").remove();
    $(this).addClass("active");
    $(this).siblings().removeClass("active");
});
// 	传入选项 更改显示模块
$(document).on("click", ".div-wupin .div-right .ul-04 li", function () {
    $(this).addClass("active");
    $(this).siblings().removeClass("active");
    // $("#wupinType").val($(this).find(".codeValue").text());
    // $("#wupinTypeId").val($(this).attr("id"));
    $(".div-wupin").hide();
    $(".wplb").empty();
});

function getProductTypeNew(pid, pvalue, ti, type) {
    var getcodeurl = basePath + "ea/scBudget/sajax_ea_getBusinessTypeByPID.jspa";
    $.ajax({
        url: encodeURI(getcodeurl),
        type: "get",
        async: true,
        dataType: "json",
        data: {
            codeID: pid
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var oList = member.codeList;
            var listhtml = "";
            var tii = ti + 1;
            var claname = "";
            var $divHtml;
            var str = [];
            if (null == oList || oList.length <= 0) {
                if (type == 1 && ti != 5) {
                    $(".xiangmu2 #producttype").val($("#" + pid).find(".codeValue").text());
                    $(".xiangmu2 #productCode").val($("#" + pid).find(".codeSn").text());
                    $(".div-xiangmu").hide();
                    $(".xiangmu1").hide();
                    $(".xiangmu2").show();
                }
                return;
            }
            if (type == 1) {
                claname = "xmfl-right";
                $divHtml = $(".xmfl");
            } else if (type == 2) {
                claname = "hyfl-right";
                $divHtml = $(".hyfl");
            } else if (type == 3) {
                claname = "wplb-right";
                $divHtml = $(".wplb");
            }

            if (ti == 1) {
                str.push("<ul class='ul-left'>");
            } else if (ti == 2) {
                str.push("<div class='div-right " + claname + "'> <ul class='ul-02'>");
            } else if (ti == 3) {
                str.push("<ul class='clearfix ul-03'>");
            } else if (ti == 4) {
                str.push("<ul class='clearfix ul-04'>");
            }
            if (pvalue != null && pvalue != "") {
                pvalue = pvalue + "\>";
            }else{
                pvalue="";
            }
            for (var i = 0; i < oList.length; i++) {
                str.push("<li id='" + oList[i].typeId + "' onclick='getProductTypeNew(" + "\"" + oList[i].typeId + "\",\"" + pvalue + oList[i].typeNum + oList[i].typeName + "\"," + tii + "," + type + ")'>");
                str.push("<span class='codeValue'>" + oList[i].typeName + "</span>");
                str.push("<span class='codeSn' style='display: none'>" + oList[i].typeShowNum + "</span>");
                str.push("<span class='pvalue' style='display: none'>" + pvalue + oList[i].codeSn + oList[i].typeName + "</span>");
                str.push("</li>");
                //(" + oList[i].codeSn + ")
            }
            if (ti == 1) {
                str.push("</ul>");
                $divHtml.append(str.join(""));
            } else if (ti == 2) {
                str.push("</ul></div>");
                $divHtml.append(str.join(""));
            } else if (ti == 3 || ti == 4) {
                str.push("</ul>");
                $("." + claname + "").append(str.join(""));
            }
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

function getItemInfoPersonal(){
    $.ajax({
        url:basePath+"ea/scBudget/sajax_ea_getGoodsBillsItemRecent.jspa",
        type:"get",
        dataType:"json",
        aysnc:false,
        data:{
            flag:"budget"//初始项目——往来个人最近联系人
        },
        success:function(data){
            if(data!=null) {
                var htmlstr = new Array();
                var m = eval("("+data+")");
                var arry = m.list;
                if(arry==null || arry.length==0){
                    return false;
                    $(".navrecent").hide();
                }
                $(".navrecent").show();
                console.log(arry[0].goodsbillsId);
                for(var i=0;i<arry.length;i++){
                    htmlstr.push("<li class='clearfix' id='"+arry[i].barCode+"' goodsName='"+arry[i].goodsName+"' goodsBillsId='"+arry[i].goodsbillsId+"' >");

                    htmlstr.push("<div class='sex'>");
                    htmlstr.push("<img class='img-01' src='"+basePath+"images/ea/office/contract/selectp/img_02.png'/>");
                    htmlstr.push("<img class='img-02' src='"+basePath+"images/ea/office/contract/selectp/img_03.png'/>")
                    htmlstr.push("</div>");
                    htmlstr.push("<p>");
                    htmlstr.push(arry[i].goodsName + "(" + arry[i].barCode + ")");
                    htmlstr.push("</p>")

                    // if(orgID==null||orgID==""){
                    //     if(arry[i][4]!=null&&arry[i][4]!="") {
                    //         htmlstr.push("<p orgid-data='"+arry[i][1]+"' class='orgID'>");
                    //         htmlstr.push(arry[i][4]);
                    //         htmlstr.push("</p>")
                    //     }
                    //     if(arry[i][5]!=null&&arry[i][5]!="") {
                    //         htmlstr.push("<p comid-data='"+arry[i][2]+"' class='comID'>");
                    //         htmlstr.push(arry[i][5]);
                    //         htmlstr.push("</p>")
                    //     }
                    // }
                    htmlstr.push("</li>")
                }

                $(".ul-list2").html(htmlstr.join(""));
            }
        },
        error:function (data) {
        }
    });
}


$(document).on("click",".ul-list2 li",function(){
    console.log("点击了");
    console.log($(this).attr("id"));
    // $("#accountFlag").val($(this).text());
    // $("#guigeTypeId").val($(this).attr("id"));
    if($(this).is(".active")){
        $(this).removeClass("active");
    }else{
        $(".ul-list2 .active").removeClass("active");
        $(this).addClass("active");
        var goodsBillsId = $(this).attr("goodsBillsId");
        console.log(goodsBillsId);
        window.location.href = basePath + "ea/scBudget/ea_getCostBudgetItem.jspa?keyNum=" + goodsBillsId +"&editFlag="+editFlag+"&cashierBillsId="+cashierBillsId+"&billsType="+billsType;//&showFlag=${showFlag}&departmentID=${departmentID}&cashierBillsId=${cashierBillsId}";//&menuId="+menuId;
        $(this).removeClass("active");
    }
})