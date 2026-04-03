var reader = new FileReader();
var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
var iswin = u.indexOf('Windows') > 1;
var k = 0;
var isSubmit = false;
var isupdate = true;
$(function () {
    $("#prompt").css("position", "absolute").css("top", $(window).height() * 0.09 + "px");
    $("#prompt").find("div").css("height", $(window).height() * 0.06 + "px").css("font-size", $(window).height() * 0.0285 + "px").css("color", "#FFFFFF");
    $("#prompt").find("div").css("-moz-border-radius", $(window).height() * 0.015 + "px").css("-webkit-border-radius", $(window).height() * 0.015 + "px");

    if (true) {
        setGoods();
        setBusinessType();
        delQueryParam();
        // $("#isupdate").show();
        // $(".mingcheng1").hide();
        // $(".div-tijiao").hide();
        $(".mingcheng2").show();
        var loanDirection = $("#loanDirection").val();
        nav(loanDirection);
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
                $("#jijiadanwei").removeAttr("required");
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
            $("#unitOld").val($("#variableID").val());
            $(".danwei1").hide();
            $(".danwei2").show();
        }
        if ($("#gradeName").val() != null && $("#gradeName").val() != "") {
            $(".dengji1").hide();
            $(".dengji2").show();
        }
        if ($("#accountShow").val() != null && $("#accountShow").val() != "") {
            $(".wldw1").hide();
            $(".wldw2").show();
        }
        if ($("#accountShowFrom").val() != null && $("#accountShowFrom").val() != "") {
            $(".wldw3").hide();
            $(".wldw4").show();
        }
        if($("#guigeTypeId").val()!=null && $("#guigeTypeId").val()!=""){
            var ggT = $("#guigeTypeId").val();
            $(".guige1").hide();
            $(".guige2").show();
            var code = $("#guigeTypeId").val();
            var parentId = $("#specsParentId").val();
            var unit = $("#variableID").val();
            getSpecsInfo(code,parentId,unit);
            $(".ggn2").show();
            $(".ggn1").hide();
            console.log("测试");
            $("#guigeTypeNew").attr("code",code);
            $("#guigeTypeNew").attr("parentId",parentId);
            // $(".div-guige-new").show();
            //div-guige-mianji
            $(".ul-con.ggn li").each(function () {

                console.log($(this).html());
                // console.log($(this).find(".attri1 ").val());
                // $(this).find(".attri2 ").val().split("-")[0]
                var attr2 = $(this).find(".attri2 ").val();
                attr2 = attr2.substring(0,attr2.length-1);
                console.log("打印："+attr2);
                // var amap = JSON.parse(attriMap);
                $(this).find(".attri1 ").val(amap[attr2]);
            });
            var attri = "";
            $(".div-guige-new").find(".attri2").each(function () {
                attri += $(this).val().trim() +($(this).prev().val())+ ",";
                console.log($(this).prev().val());
            });
            $("#attri").val(attri);
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
            getProductTypeNew("", "", 1, 1);
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
            getProductTypeNew("", "", 1, 2);
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
        $(".div-con").removeClass("ul1 ul2 ul3");
        $(".div-con").addClass("ul1");
    });
    // 	缩进
    $(document).on("click", ".div-hangye .div-right .ul-02 li", function () {
        $(".div-hangye .div-right .ul-03").remove();
        $(".div-hangye .div-right .ul-04").remove();
        $(".div-con").removeClass("ul1 ul2 ul3");
        $(".div-con").addClass("ul2");
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
    });
    $(document).on("click", ".div-hangye .div-right .ul-03 li", function () {
        $(".div-hangye .div-right .ul-04").remove();
        $(".div-con").removeClass("ul1 ul2 ul3");
        $(".div-con").addClass("ul3");
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
    });
    // 	传入选项 更改显示模块
    $(document).on("click", ".div-hangye .div-right .ul-04 li", function () {
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
        $(".hangye2 #tradeName").val($(this).find(".codeValue").text());
        $(".hangye2 #tradeID").val($(this).attr("id"));
        $(".hangye2 #tradeCode").val($(this).find(".pvalue").text());
        $(".div-hangye").hide();
        $(".hangye1").hide();
        $(".hangye2").show();
        $(".hyfl").empty();
    });
    // end

    // 物品类别

    // 	显示
    $(document).on("click", ".wupin1,.wupin2", function () {
        if (isupdate) {
            $(".wplb").empty();
            getProductTypeNew("", "", 1, 3);
            $(".div-wupin").show();
        }
    });
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
        $(".wupin2 input").val($(this).find(".codeValue").text());
        $(".div-wupin").hide();
        $(".wupin1").hide();
        $(".wupin2").show();
        $(".wplb").empty();
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

    $('#as').diyUpload({
        url: basePath + 'ea/productsmanag/sajax_ea_uplodFile.jspa?companyID=' + companyID,
        success: function (data) {
            var member = eval("(" + data + ")");
            var path = member.path;
            var name = member.name;
            var siSuccess = member.siSuccess;
            if (siSuccess) {
                $(".diyFileName").each(function () {
                    if ($(this).text() == name) {
                        $(this).after("<div class='diyImagesPath' style='display: none;'>" + path + "</div>");
                        var ImagesPath = $("#ImagesPath").val();
                        ImagesPath += path + ",";
                        $("#ImagesPath").val(ImagesPath);
                    }
                });
            }
        },
        error: function (err) {
            console.info(err);
        },
        accept: {
            title: "Images",
            extensions: "gif,jpg,jpeg,bmp,png",
            mimeTypes: "image/*"
        },
        buttonText: '选择图片',
        chunked: false,
        // 分片大小
        chunkSize: 512 * 1024,
        //最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
        fileNumLimit: 9,
        fileSizeLimit: 9 * 1024 * 1024,
        fileSingleSizeLimit: 1 * 1024 * 1024
    });

    // end

    // 首页视频

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

    $('#ass').diyUpload({
        url: basePath + 'ea/productsmanag/sajax_ea_uplodFile.jspa?companyID=' + companyID,
        success: function (data) {
            var member = eval("(" + data + ")");
            var path = member.path;
            var name = member.name;
            var siSuccess = member.siSuccess;
            if (siSuccess) {
                $(".diyFileName").each(function () {
                    if ($(this).text() == name) {
                        $(this).after("<div class='diyVideoPath' style='display: none;'>" + path + "</div>");
                        var VideoPath = $("#VideoPath").val();
                        VideoPath += path + ",";
                        $("#VideoPath").val(VideoPath);
                    }
                });
            }
        },
        error: function (err) {
            console.info(err);
        },
        accept: {
            title: "Video",
            extensions: "wmv,asf,asx,rm,rmvb,mp4,3gp,mov,m4v,avi,dat,mkv,flv,vob",
            mimeTypes: "video/*"
        },
        buttonText: '选择视频',
        chunked: true,
        // 分片大小
        chunkSize: 512 * 1024,
        //最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
        fileNumLimit: 3,
        fileSizeLimit: 105 * 1024 * 1024,
        fileSingleSizeLimit: 35 * 1024 * 1024
    });
    // end

    // 单位

    // 	显示
    $(document).on("click", ".danwei1,.danwei2", function () {
        if (isupdate) {
            $(".dwgl").empty();
            // getProductType("scode20101014v5zed7cukk0000000003", "", 1, 4);
            getUnitData();
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
        //单位变化时，要将规格置空
        var oldDw = $(".danwei2 input").val();
        var newDw = $(".div-danwei .div-middle .active .codeValue").text();
        var specsId = $(".div-danwei .div-middle .active .codeValue").attr("id");
        if(oldDw != newDw){
            $("#guigeTypeValue").val("");
            $(".ggn1").show();
            $(".ggn2").hide();
            $("#guigeTypeNew").val("");
            $("#ggn").html("");
            $()
        }
        $(".danwei2 input").val(newDw);
        $("#specsId").val(specsId);
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
        $(".div-guige-new").show();
        var specsId = $("#specsId").val();
        if(specsId){
            getSpecsByParent2(specsId);
        }else{
            prompt("请先选择单位");
        }

    });

    //点击规格类型
    $(document).on("click", ".ggn1,.ggn2", function () {
        $(".div-guige-new").show();
        var specsId = $("#specsId").val();
        if(specsId){
            getSpecsByParent(specsId);
        }else{
            prompt("请先选择单位");
        }

    });

    // 	传入选项 更改显示模块
    //规格类型选择
    $(document).on("click", ".div-guigeType ul li", function () {
        // console.log($(this).text());
        $("#guigeTypeNew").val($(this).text());
        $("#guigeTypeNew").attr("code",$(this).attr("code"));
        $("#guigeTypeNew").attr("parentId",$(this).attr("id"));
        var unit = $("#variableID").val();
        getSpecsInfo($(this).attr("code"),$(this).attr("id"),unit);
        $(".ggn2").show();
        $(".ggn1").hide();
        $(".div-guigeType").hide();
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
    $(document).on("click", ".div-guige-new .div-close", function () {
        $(".div-guige-new").hide();
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
            //$(".div-guige").hide();
            $(".guige1").hide();
            $(".guige2").show();
        } else if ((colorvalue == null || colorvalue == "") && (sizevalue == null || sizevalue == "")) {
            //$(".div-guige").hide();
        }

        $(".product_size").hide();
    });

    function hoursBetweenDates(date1, date2) {
        const oneHour = 60 * 60 * 1000; // 每天的毫秒数
        const diff = Math.abs(new Date(date2).getTime() - new Date(date1).getTime()); // 计算两个日期的时间戳差
        return (diff / oneHour).toFixed(2); // 转换为小时
    }

    var regNum = new RegExp(/^[//(//)\u4e00-\u9fa5_a-zA-Z0-9]+$/);
    $(document).on("click", ".div-guige-new .p-bottom", function (event) {
        var variableID = $("#variableID").val();
        var commitFlag = true;
        if(variableID == "m3" || variableID == "m2"){
            var guigeValue=1;
            $(".div-guige-new .ul-con.ggn .clearfix").each(function () {
                // console.log($(this).html());
                // console.log($(this).find(".attri1 ").val());
                // $(this).find(".attri2 ").val().split("-")[0]
                $(this).find(".attri2 ").val($(this).find(".attri2 ").val() + $(this).find(".attri1 ").val());
                if(!regNum.test($(this).find(".attri1 ").val())){
                    prompt("请输入数字");
                    commitFlag = false;
                }
                guigeValue = guigeValue*parseFloat($(this).find(".attri1 ").val());
            });
            if(!commitFlag){
                return;
            }

            $("#guigeTypeValue").val(guigeValue+" "+variableID);
        } else if(variableID == "时间") {
            // var guigeValue="";
            // $(".div-guige-new .ul-con.ggn .clearfix").each(function () {
            //     // console.log($(this).html());
            //     // console.log($(this).find(".attri1 ").val());
            //     // $(this).find(".attri2 ").val().split("-")[0]
            //     $(this).find(".attri2 ").val($(this).find(".attri2 ").val() + $(this).find(".attri1 ").val());
            //     if(!$(this).find(".attri1 ").val()){
            //         prompt("请选择时间");
            //         commitFlag = false;
            //     }
            //     guigeValue += " ~ " +$(this).find(".attri1 ").val().substring(0,10) ;
            // });
            // if(!commitFlag){
            //     return;
            // }
            // $("#guigeTypeValue").val(guigeValue.substring(3));
            var guigeValue="";
            var guigeTypeNew = $("#guigeTypeNew").val();
            var guigeFlag = false;
            var start;
            var end;
            // if(!$("#unitPrice2").val()){
            //     prompt("请输入单价");
            //     return;
            // }
            if(guigeTypeNew=="天"||guigeTypeNew=="半天"||guigeTypeNew=="小时"){
                guigeFlag = true;
            }
            $(".div-guige-new .ul-con.ggn .clearfix").each(function () {
                console.log($(this).html());
                console.log($(this).find("input").hasClass("attri1"));
                // $(this).find(".attri2 ").val().split("-")[0]

                if($(this).find("input").hasClass("attri1") && !$(this).find(".attri1 ").val()){
                    prompt("请选择时间");
                    commitFlag = false;
                }
                var desc = $(this).find(".attri1 ").attr("info");
                if($(this).find("input").hasClass("attri1")){
                    $(this).find(".attri2 ").val($(this).find(".attri2 ").val() + $(this).find(".attri1 ").val());
                    if(guigeFlag){
                        if(desc!="开始时间" && desc!="截止时间"){
                            guigeValue = guigeValue+" "+($(this).find(".attri1 ").val())+" "+$(this).find(".attri1 ").attr("info");
                        }
                    }else{
                        guigeValue += " ~ " +($(this).find(".attri1 ").val().substring(0,10)) ;
                    }

                    if(desc!="开始时间"){
                        start=$(this).find(".attri1 ").val();
                    }else if(desc!="截止时间"){
                        end=$(this).find(".attri1 ").val();
                    }
                }

            });
            if(!commitFlag){
                return;
            }
            // $("#budgetNumber").val($("#budgetNumber2").val());
            // $("#unitPrice").val($("#unitPrice2").val());
            if(guigeFlag){
                $("#guigeTypeValue").val(guigeValue.substring(1));
            }else{
                $("#guigeTypeValue").val(guigeValue.substring(3));
            }
            if(start && end){
                $("#budgetNumber").val(hoursBetweenDates(start, end)+" 小时");
            }else{
                $("#budgetNumber").val($("#guigeTypeValue").val());
            }
        } else {
            var guigeValue="";
            $(".div-guige-new .ul-con.ggn .clearfix").each(function () {
                // console.log($(this).html());
                // console.log($(this).find(".attri1 ").attr("info"));
                // console.log($(this).find(".attri1 ").val());
                // $(this).find(".attri2 ").val().split("-")[0]
                $(this).find(".attri2 ").val($(this).find(".attri2 ").val() + $(this).find(".attri1 ").val());
                // console.log($(this).find(".attri1 ").val());
                if(!regNum.test($(this).find(".attri1 ").val())){
                    prompt("请勿输入数字、英文、中文以外的字符");
                    commitFlag = false;
                }
                guigeValue = guigeValue+" x "+($(this).find(".attri1 ").val())+" "+$(this).find(".attri1 ").attr("info");
            });
            if(!commitFlag){
                return;
            }
            $("#guigeTypeValue").val(guigeValue.substring(3));
        }

        $("#guigeType").val($("#guigeTypeNew").val());
        $("#guigeTypeId").val($("#guigeTypeNew").attr("code"));
        $("#specsParentId").val($("#guigeTypeNew").attr("parentId"));

        var attri = "";
        $(".div-guige-new").find(".attri2").each(function () {
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

    //往来关系显示(收)
    $(document).on("click", ".wldw1,.wldw2", function () {
        $(".div-accountType").show();
    });

    //往来关系显示(付)
    $(document).on("click", ".wldw3,.wldw4", function () {
        $(".div-accountTypeFrom").show();
    });

    // 	传入选项 更改显示模块
    $(document).on("click", ".div-accountType ul li", function () {
        // console.log($(this).attr("id"));
        // $("#accountFlag").val($(this).text());
        // $("#guigeTypeId").val($(this).attr("id"));
        var accountType = $(this).text();
        $(".div-accountType").hide();
        // console.log(accountType);
        var accountFlagOld = $("#accountFlag").val();
        var accountFlagNew = $(this).attr("atype");
        $("#accountFlag").val(accountFlagNew);
        // $("#accountShow").val($(this).text());

        if (accountType=="单位") {
            // getAttri2($("#goodsID").val());
            // if(accountFlagOld != accountFlagNew){
            //     $("#sjrgs").val("");
            //     $("#comid").val("");
            //     $("#accountPhone").val("");
            //     $("#accountNum").val("");
            //     $("#openBank").val("");
            // }

            $(".div-account-company").show();
            getRecentInfoCompany(".div-account-company");
        } else if(accountType=="个人"){
            // getAttri1($("#goodsID").val());
            // if(accountFlagOld != accountFlagNew){
            //     $("#wlgr").val("");
            //     $("#wlgrId").val("");
            //     $("#accountPhoneP").val("");
            //     $("#accountNumP").val("");
            //     $("#openBankP").val("");
            // }

            $(".div-account-personal").show();
            getRecentInfoPersonal(".div-account-personal");
        }
    });

    // 	传入选项 更改显示模块
    $(document).on("click", ".div-accountTypeFrom ul li", function () {
        // console.log($(this).attr("id"));
        // $("#accountFlag").val($(this).text());
        // $("#guigeTypeId").val($(this).attr("id"));
        var accountType = $(this).text();
        $(".div-accountTypeFrom").hide();
        // console.log(accountType);
        $("#accountFlagFrom").val($(this).attr("atype"));
        // $("#accountShowFrom").val($(this).text());

        if (accountType=="单位") {
            // getAttri2($("#goodsID").val());
            // $("#sjrgsFrom").val("");
            // $("#comidFrom").val("");
            // $("#accountPhoneFrom").val("");
            // $("#accountNumFrom").val("");
            // $("#openBankFrom").val("");
            $(".div-account-companyFrom").show();
            getRecentInfoCompany(".div-account-companyFrom");
        } else if(accountType=="个人"){
            // getAttri1($("#goodsID").val());
            // $("#wlgrFrom").val("");
            // $("#wlgrIdFrom").val("");
            // $("#accountPhoneFromP").val("");
            // $("#accountNumFromP").val("");
            // $("#openBankFromP").val("");
            $(".div-account-personalFrom").show();
            getRecentInfoPersonal(".div-account-personalFrom");
        }
    });

    function getRecentInfoCompany(comClass){
        $.ajax({
            url:basePath+"ea/scBudget/sajax_ea_getGoodsBillsContactRecent.jspa",
            type:"get",
            dataType:"json",
            aysnc:false,
            data:{
                accountFlag:"company"//初始项目——往来单位最近联系人
            },
            success:function(data){
                if(data!=null) {
                    var htmlstr = new Array();
                    var m = eval("("+data+")");
                    var arry = m.list;
                    if(arry==null){
                        return false;
                        $(".navrecent").hide();
                    }
                    $(".navrecent").show();
                    // console.log(arry[0].accountNameId);
                    for(var i=0;i<arry.length;i++){
                        htmlstr.push("<li class='clearfix' id='"+arry[i].accountNameId+"' staffname='"+arry[i].accountName+"' phone='"+arry[i].accountPhone+"' openBank='"+arry[i].openBank+"' accountNum='"+arry[i].accountNum+"'>");

                        htmlstr.push("<div class='sex'>");
                        htmlstr.push("<img class='img-01' src='"+basePath+"images/ea/office/contract/selectp/img_02.png'/>");
                        htmlstr.push("<img class='img-02' src='"+basePath+"images/ea/office/contract/selectp/img_03.png'/>")
                        htmlstr.push("</div>");
                        htmlstr.push("<p>");
                        htmlstr.push(arry[i].accountName);
                        htmlstr.push("</p>")
                        htmlstr.push("<p class='comID'>");
                        htmlstr.push( arry[i].accountNum);
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

                    $(comClass +" .company-content .ul-list2").html(htmlstr.join(""));
                }
            },
            error:function (data) {
            }
        });
    }

    function getRecentInfoPersonal(perClass){
        $.ajax({
            url:basePath+"ea/scBudget/sajax_ea_getGoodsBillsContactRecent.jspa",
            type:"get",
            dataType:"json",
            aysnc:false,
            data:{
                accountFlag:"personal"//初始项目——往来个人最近联系人
            },
            success:function(data){
                if(data!=null) {
                    var htmlstr = new Array();
                    var m = eval("("+data+")");
                    var arry = m.list;
                    if(arry==null){
                        return false;
                        $(".navrecent").hide();
                    }
                    $(".navrecent").show();
                    // console.log(arry[0].accountNameId);
                    for(var i=0;i<arry.length;i++){
                        htmlstr.push("<li class='clearfix' id='"+arry[i].accountNameId+"' staffname='"+arry[i].accountName+"' phone='"+arry[i].accountPhone+"' openBank='"+arry[i].openBank+"' accountNum='"+arry[i].accountNum+"'>");

                        htmlstr.push("<div class='sex'>");
                        htmlstr.push("<img class='img-01' src='"+basePath+"images/ea/office/contract/selectp/img_02.png'/>");
                        htmlstr.push("<img class='img-02' src='"+basePath+"images/ea/office/contract/selectp/img_03.png'/>")
                        htmlstr.push("</div>");
                        htmlstr.push("<p>");
                        htmlstr.push(arry[i].accountName + "(" + arry[i].accountNum + ")");
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

                    $(perClass+" .personal-content .ul-list2").html(htmlstr.join(""));
                }
            },
            error:function (data) {
            }
        });
    }

    // 	关闭
    $(document).on("click", ".div-accountType", function (event) {
        if(event.target==this){
            $(".div-accountType").hide();
        }
    });
    $(document).on("click", ".div-accountType .div-close", function () {
        $(".div-accountType").hide();
    });

    // 	关闭
    $(document).on("click", ".div-accountTypeFrom", function (event) {
        if(event.target==this){
            $(".div-accountTypeFrom").hide();
        }
    });
    $(document).on("click", ".div-accountTypeFrom .div-close", function () {
        $(".div-accountTypeFrom").hide();
    });


    function checkWl(clsName){
        var cf = false;
        $(clsName+" .company-content .sec-search .div-name").each(function (e) {
            console.log($(this).find("input").val());
            if($(this).find("input").val()=="" && $(this).find("label").text() != "手机号" && $(this).find("label").text() != "联系人"){
                prompt($(this).find("label").text()+"不能为空");
                cf=true;
                return cf;
            }
        })
        return cf;
    }

    // 	关闭
    $(document).on("click", ".div-account.div-account-company .div-close", function () {
        if(checkWl(".div-account-company")){
            return;
        }
        setAccountShowOfCompany();
        $(".div-account-company").hide();
        $(".wldw1").hide();
        $(".wldw2").show();
    });

    $(document).on("click", ".div-account.div-account-company .sec-bottom", function () {
        if(checkWl(".div-account-company")){
            return;
        }
        setAccountShowOfCompany();
        $(".div-account-company").hide();
        $(".wldw1").hide();
        $(".wldw2").show();
    });
    $(document).on("click", ".div-account.div-account-personal .div-close", function () {
        if(checkWl(".div-account-personal")){
            return;
        }
        setAccountShowOfPersonal();
        $(".div-account-personal").hide();
        $(".wldw1").hide();
        $(".wldw2").show();
    });

    $(document).on("click", ".div-account.div-account-personal .sec-bottom", function () {
        if(checkWl(".div-account-personal")){
            return;
        }
        setAccountShowOfPersonal();
        $(".div-account-personal").hide();
        $(".wldw1").hide();
        $(".wldw2").show();
    });

    $(document).on("click", ".div-account.div-account-companyFrom .div-close", function () {
        if(checkWl(".div-account-companyFrom")){
            return;
        }
        setAccountShowFromOfCompany();
        $(".div-account-companyFrom").hide();
        $(".wldw3").hide();
        $(".wldw4").show();
    });

    $(document).on("click", ".div-account.div-account-companyFrom .sec-bottom", function () {
        if(checkWl(".div-account-companyFrom")){
            return;
        }
        setAccountShowFromOfCompany();
        $(".div-account-companyFrom").hide();
        $(".wldw3").hide();
        $(".wldw4").show();
    });
    $(document).on("click", ".div-account.div-account-personalFrom .div-close", function () {
        if(checkWl(".div-account-personalFrom")){
            return;
        }
        setAccountShowFromOfPersonal();
        $(".div-account-personalFrom").hide();
        $(".wldw3").hide();
        $(".wldw4").show();
    });

    $(document).on("click", ".div-account.div-account-personalFrom .sec-bottom", function () {
        if(checkWl(".div-account-personalFrom")){
            return;
        }
        setAccountShowFromOfPersonal();
        $(".div-account-personalFrom").hide();
        $(".wldw3").hide();
        $(".wldw4").show();
    });
    //end

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

function calltiaoma(tiaoma) {
    var params = [];
    params.push("user=");
    params.push("&companyId=" + companyId);
    var member = eval("(" + tiaoma + ")");
    var p1, p3;
    p1 = member.code;
    p3 = member.name;
    params.push("&barcode=" + p1);
    params.push("&name=" + p3);
    window.location.href = basePath + "ea/productsmanag/ea_toProductsLaunch.jspa?" + params.join("");
}

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
    var commitFlag=false;

    $(".tianjia-content>ul li").each(function (e) {
        // $(this).find(".attri2 ").val($(this).find(".attri2 ").val() + $(this).find(".attri1 ").val());
        // console.log("打印："+$(this).find("span").html());
        // console.log($(this).find("input").attr("required"));
        // console.log("打印2："+$(this).find("input").val());
        // console.log($(this).find("span").parent().text());
        if($(this).find("span").html()=="*" && $(this).find("input").attr("required")=="required"){
            if($(this).find("input").val()==""){
                prompt($(this).find("span").parent().text().substring(1)+"不能为空");
                commitFlag=true;
                return;
            }
        }
    })

    if(commitFlag){
        return;
    }

    if ($("#ppname").val() == "") {
        prompt("请输入商品名称");
    } else if (!reg2.test($("#ppname").val())) {
        prompt("商品名称只支持数字，英文，中文");
    } else if ($("#budgetNumber").val() == "") {
        prompt("数量不能为空");
    } else if ($("#unitPrice").val() == "") {
        prompt("单价不能为空");
    } else {
        $("#edit").find(".content").each(function () {
            htlstr += $(this).html();
        });
        $("#editcontent").val(htlstr);
        isSubmit = true;
        var goodsPurpose = $("#goodsPurpose").val();
        var goodsPurposeId = $("#goodsPurpose").val();
        var url = basePath + "ea/scBudget/ea_toAddProjectItem.jspa?billsType="+billsType;
        $("#launchForm").attr('action',url);    //通过jquery为action属性赋值
        $("#launchForm").submit();    //提交ID为myform的表单
    }
}

function computeAmount2(){
    var budgetNumber = $("#budgetNumber").val();
    var unitPrice = $("#unitPrice").val();

    if(!budgetNumber){
        budgetNumber=0;
    }
    if(!unitPrice){
        unitPrice=0;
    }
    var guigeType = $("#guigeType").val();
    var amount=parseFloat(budgetNumber)*parseFloat(unitPrice);
    $("#amount").val(amount);
    $("#budgetAmount").val(amount);
    budgetAmountChg();
}

//计算金额
function computeAmount(){
    var budgetNumber = $("#budgetNumber").val();
    var unitPrice = $("#unitPrice").val();

    if(!budgetNumber){
        prompt("数量不能为空");
        return;
    }
    if(!unitPrice){
        prompt("请输入单价");
        return;
    }
    var amount=parseFloat(budgetNumber)*parseFloat(unitPrice);

    $("#amount").val(amount);
    $("#budgetAmount").val(amount);
    budgetAmountChg();
}
function nav(selectId) {
    var amount = $("#amount").val();
    $("#borrowAmount").val("");
    $("#loanAmount").val("");
    var initialBalance = $("#initialBalance").val();
    if (selectId == "收") {
        $("#loan").attr("src", basePath+"images/ea/office/contract/selectp/dxwselet.png");
        $("#borrow").attr("src", basePath+"images/ea/office/contract/selectp/dxseleted.png");
        $("#borrowAmount").val(amount);
        $("#budgetAmount").val(amount);
        if(amount){
            $("#balance").val(parseFloat(initialBalance)+parseFloat(amount));
        }
    } else if(selectId == "付"){
        $("#borrow").attr("src", basePath+"images/ea/office/contract/selectp/dxwselet.png");
        $("#loan").attr("src", basePath+"images/ea/office/contract/selectp/dxseleted.png");
        $("#loanAmount").val(amount);
        $("#budgetAmount").val("-"+amount);
        if(amount){
            $("#balance").val(parseFloat(initialBalance)+parseFloat("-"+amount));
        }
    }
    $("#loanDirection").val(selectId);
}

function budgetAmountChg(){
    var amount = $("#amount").val();
    // console.log("借贷改变:"+amount);
    var initialBalance = $("#initialBalance").val();
    if(amount){
        $("#borrowAmount").val("");
        $("#loanAmount").val("");
        var direction = $("#loanDirection").val();
        if("收" == direction){
            $("#borrowAmount").val(amount);
            $("#balance").val(parseFloat(initialBalance)+parseFloat(amount));
        }else {
            $("#loanAmount").val(amount);
            $("#balance").val(parseFloat(initialBalance)+parseFloat("-"+amount));
        }
    }
}

$(document).on("click",".div-account-personal .personal-content .ul-list2 li",function(){
    // console.log("点击了");
    // console.log($(this).attr("id"));
    // $("#accountFlag").val($(this).text());
    // $("#guigeTypeId").val($(this).attr("id"));
    if($(this).is(".active")){
        $(this).removeClass("active");
    }else{
        $(".ul-list2 .active").removeClass("active");
        $(this).addClass("active");
        $("#wlgrId").val($(this).attr("id"));
        $("#wlgr").val($(this).attr("staffname"));
        var num = $(this).attr("accountNum");
        $("#accountNumP").val(num);
        $("#accountPhoneP").val($(this).attr("phone") == "null" ? "" : $(this).attr("phone"));
        $("#openBankP").val($(this).attr("openBank"));
        // $("#accountShow").val($(this).attr("staffname")+"("+num.substring(num.length-4)+")");
    }
})


$(document).on("click",".div-account-company .company-content .ul-list2 li",function(){
    // console.log("公司点击了");
    // console.log($(this).attr("id"));
    // $("#accountFlag").val($(this).text());
    // $("#guigeTypeId").val($(this).attr("id"));
    if($(this).is(".active")){
        $(this).removeClass("active");
    }else{
        $(".ul-list2 .active").removeClass("active");
        $(this).addClass("active");
        $("#comid").val($(this).attr("id"));
        $("#sjrgs").val($(this).attr("staffname"));
        var num = $(this).attr("accountNum");
        $("#accountNum").val(num);
        $("#accountPhone").val($(this).attr("phone") == "null" ? "" : $(this).attr("phone"));
        $("#openBank").val($(this).attr("openBank"));
        // var name = $(this).attr("staffname");
        // var showName = "";
        // if(name.length<10){
        //     showName = name;
        // }else{
        //     showName = name.substring(0,10)+"**";
        // }
        // $("#accountShow").val(showName+"("+num.substring(num.length-4)+")");
    }
})


$(document).on("click",".div-account-personalFrom .personal-content .ul-list2 li",function(){
    // console.log("点击了");
    // console.log($(this).attr("id"));
    // $("#accountFlag").val($(this).text());
    // $("#guigeTypeId").val($(this).attr("id"));
    if($(this).is(".active")){
        $(this).removeClass("active");
    }else{
        $(".ul-list2 .active").removeClass("active");
        $(this).addClass("active");
        $("#wlgrIdFrom").val($(this).attr("id"));
        $("#wlgrFrom").val($(this).attr("staffname"));
        var num = $(this).attr("accountNum");
        $("#accountNumFromP").val(num);
        $("#accountPhoneFromP").val($(this).attr("phone") == "null" ? "" : $(this).attr("phone"));
        $("#openBankFromP").val($(this).attr("openBank"));
        // var name = $(this).attr("staffname");
        // var showName = "";
        // if(name.length<10){
        //     showName = name;
        // }else{
        //     showName = name.substring(0,10)+"**";
        // }
        // console.log("赋值");
        // $("#accountShowFrom").val(showName+"("+num.substring(num.length-4)+")");
    }
})


function setAccountShowFromOfPersonal(){
    var name = $("#wlgrFrom").val();
    var num = $("#accountNumFromP").val();
    var showName = "";
    if(name.length<10){
        showName = name;
    }else{
        showName = name.substring(0,10)+"**";
    }
    console.log("赋值2");
    $("#accountShowFrom").val(showName+"("+num.substring(num.length-4)+")");
}


function setAccountShowOfPersonal(){
    var name = $("#wlgr").val();
    var num = $("#accountNumP").val();
    var showName = "";
    if(name.length<10){
        showName = name;
    }else{
        showName = name.substring(0,10)+"**";
    }
    console.log("赋值2");
    $("#accountShow").val(showName+"("+num.substring(num.length-4)+")");
}

$(document).on("click",".div-account-companyFrom .company-content .ul-list2 li",function(){
    // console.log("公司点击了");
    // console.log($(this).attr("id"));
    // $("#accountFlag").val($(this).text());
    // $("#guigeTypeId").val($(this).attr("id"));
    if($(this).is(".active")){
        $(this).removeClass("active");
    }else{
        $(".ul-list2 .active").removeClass("active");
        $(this).addClass("active");
        $("#comidFrom").val($(this).attr("id"));
        $("#sjrgsFrom").val($(this).attr("staffname"));
        var num = $(this).attr("accountNum");
        $("#accountNumFrom").val(num);
        $("#accountPhoneFrom").val($(this).attr("phone") == "null" ? "" : $(this).attr("phone"));
        $("#openBankFrom").val($(this).attr("openBank"));
        // var name = $(this).attr("staffname");
        // var showName = "";
        // if(name.length<10){
        //     showName = name;
        // }else{
        //     showName = name.substring(0,10)+"**";
        // }
        // console.log("赋值2");
        // $("#accountShowFrom").val(showName+"("+num.substring(num.length-4)+")");
    }
})

function setAccountShowFromOfCompany(){
    var name = $("#sjrgsFrom").val();
    var num = $("#accountNumFrom").val();
    var showName = "";
    if(name.length<10){
        showName = name;
    }else{
        showName = name.substring(0,10)+"**";
    }
    console.log("赋值2");
    $("#accountShowFrom").val(showName+"("+num.substring(num.length-4)+")");
}


function setAccountShowOfCompany(){
    var name = $("#sjrgs").val();
    var num = $("#accountNum").val();
    var showName = "";
    if(name.length<10){
        showName = name;
    }else{
        showName = name.substring(0,10)+"**";
    }
    console.log("赋值2");
    $("#accountShow").val(showName+"("+num.substring(num.length-4)+")");
}

//选中公司
$(document).on("click",".div-account-company .ul-list li.company",function(){

    $("#comid").val($(this).attr("id"));

    $("#sjrgs").val($(this).find("p").text());
    $(".div-account-company .sec-ul").hide();

})

//选中个人
$(document).on("click",".div-account-personal .ul-list li.personal",function(){

    $("#wlgrId").val($(this).attr("id"));
    $("#wlgr").val($(this).find("p").text());
    $("#accountPhoneP").val($(this).attr("phone"));
    $(".div-account-personal .sec-ul").hide();

})


//选中公司
$(document).on("click",".div-account-companyFrom .ul-list li.company",function(){

    $("#comidFrom").val($(this).attr("id"));

    $("#sjrgsFrom").val($(this).find("p").text());
    $(".div-account-companyFrom .sec-ul").hide();

})

//选中个人
$(document).on("click",".div-account-personalFrom .ul-list li.personal",function(){

    $("#wlgrIdFrom").val($(this).attr("id"));
    $("#wlgrFrom").val($(this).find("p").text());
    $("#accountPhoneFromP").val($(this).attr("phone"));
    $(".div-account-personalFrom .sec-ul").hide();

})
$(document).ready(function () {
    //getRecentInfo();
    $('#wlgr').bind('input propertychange', function() {

        // $("#wlgr").val("");
        $("#wlgrId").val("");

        var left = $(this).position().left;
        var top = $(this).position().top;
        $(".div-account-personal .ul-list").html("");
        $(".div-account-personal .sec-ul").hide();

        var $p = $(this);
        if($.trim($p.val())==""||$.trim($p.val()).length<2){
            return false;

        }
        var url = basePath
            + "ea/android/video_getPersonList.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "post",
            async: true,
            dataType: "json",
            data:{
                parameter:$p.val()
            },
            success: function cbf(data) {
                if(data!=null) {
                    var arry = data.result;
                    if(arry &&arry.length>0){
                        // console.log(arry);
                        var str = "";
                        for (var i = 0; i < arry.length; i++) {
                            var obj = arry[i];
                            str += "<li class='personal' id='" + obj.staffID + "' phone='"+obj.tel+"'><p>" + obj.staffName + "</p></li>";
                        }
                        $(".div-account-personal .ul-list").html(str);
                        if(str!="") {
                            $(".div-account-personal .sec-ul").css({
                                position: "absolute",
                                left: left - 12,
                                top: top + 45
                            }).show();
                        }
                    }
                }
            },
            error: function cbf(data) {
                console.log("数据获取失败！")
            }
        });

    });

    $('#wlgrFrom').bind('input propertychange', function() {

        // $("#wlgr").val("");
        $("#wlgrIdFrom").val("");

        var left = $(this).position().left;
        var top = $(this).position().top;
        $(".div-account-personalFrom .ul-list").html("");
        $(".div-account-personalFrom .sec-ul").hide();

        var $p = $(this);
        if($.trim($p.val())==""||$.trim($p.val()).length<2){
            return false;

        }
        var url = basePath
            + "ea/android/video_getPersonList.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "post",
            async: true,
            dataType: "json",
            data:{
                parameter:$p.val()
            },
            success: function cbf(data) {
                if(data!=null) {
                    var arry = data.result;
                    if(arry &&arry.length>0){
                        // console.log(arry);
                        var str = "";
                        for (var i = 0; i < arry.length; i++) {
                            var obj = arry[i];
                            str += "<li class='personal' id='" + obj.staffID + "' phone='"+obj.tel+"'><p>" + obj.staffName + "</p></li>";
                        }
                        $(".div-account-personalFrom .ul-list").html(str);
                        if(str!="") {
                            $(".div-account-personalFrom .sec-ul").css({
                                position: "absolute",
                                left: left - 12,
                                top: top + 45
                            }).show();
                        }
                    }
                }
            },
            error: function cbf(data) {
                console.log("数据获取失败！")
            }
        });

    });

    $('#sjrgs').bind('input propertychange', function() {

        $("#staffid").val("");
        $("#orgid").val("");
        $("#comid").val("");
        $("#sjr").val("");
        $("#staffname").val("");

        var left = $(this).position().left;
        var top = $(this).position().top;
        $(".div-account-company .ul-list").html("");
        $(".div-account-company .sec-ul").hide();

        var $p = $(this);
        if($.trim($p.val())==""||$.trim($p.val()).length<4){
            return false;

        }
        var url = basePath
            + "ea/documentcommon/sajax_ea_getAllCompany.jspa?date="
            + new Date().toLocaleString();
        $.ajax({
            url: encodeURI(url),
            type: "post",
            async: true,
            dataType: "json",
            data:{
                "document.companyName":$p.val()
            },
            success: function cbf(data) {
                var member = eval("(" + data + ")");
                var companylist = member.companylist;
                var str = "";
                for (var i = 0; i < companylist.length; i++) {
                    var obj = companylist[i];
                    str += "<li class='company' id='" + obj.companyID + "'><p>" + obj.companyName + "</p></li>";
                }
                $(".div-account-company .ul-list").html(str);
                if(str!="") {
                    $(".div-account-company .sec-ul").css({
                        position: "absolute",
                        left: left - 12,
                        top: top + 45
                    }).show();
                }

            },
            error: function cbf(data) {
                console.log("数据获取失败！")
            }
        });

    });

    $('#sjrgsFrom').bind('input propertychange', function() {

        // $("#staffid").val("");
        // $("#orgid").val("");
        $("#comidFrom").val("");
        $("#sjrFrom").val("");
        // $("#staffname").val("");

        var left = $(this).position().left;
        var top = $(this).position().top;
        $(".div-account-companyFrom .ul-list").html("");
        $(".div-account-companyFrom .sec-ul").hide();

        var $p = $(this);
        if($.trim($p.val())==""||$.trim($p.val()).length<4){
            return false;

        }
        var url = basePath
            + "ea/documentcommon/sajax_ea_getAllCompany.jspa?date="
            + new Date().toLocaleString();
        $.ajax({
            url: encodeURI(url),
            type: "post",
            async: true,
            dataType: "json",
            data:{
                "document.companyName":$p.val()
            },
            success: function cbf(data) {
                var member = eval("(" + data + ")");
                var companylist = member.companylist;
                var str = "";
                for (var i = 0; i < companylist.length; i++) {
                    var obj = companylist[i];
                    str += "<li class='company' id='" + obj.companyID + "'><p>" + obj.companyName + "</p></li>";
                }
                $(".div-account-companyFrom .ul-list").html(str);
                if(str!="") {
                    $(".div-account-companyFrom .sec-ul").css({
                        position: "absolute",
                        left: left - 12,
                        top: top + 45
                    }).show();
                }

            },
            error: function cbf(data) {
                console.log("数据获取失败！")
            }
        });

    });
});



//单位数据查询
function getUnitData() {
    var getcodeurl = basePath + "ea/scBudget/sajax_ea_getUnitData.jspa";
    $.ajax({
        url: encodeURI(getcodeurl),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var oList = member.list;
            var listhtml = "";
            var claname = "";
            var $divHtml;
            var str = [];
            if (null == oList || oList.length <= 0) {
                return;
            }
            $divHtml = $(".dwgl");
            str.push("<ul class='clearfix ul-04'>");
            for (var i = 0; i < oList.length; i++) {

                str.push("<p id='" + oList[i].codeID + "'>");
                str.push("<span class='codeValue' id='"+oList[i].id+"'>" + oList[i].specs + "</span>");
                // str.push("<span class='codeSn' style='display: none'>" + oList[i].codeSn + "</span>");
                // str.push("<span class='pvalue' style='display: none'>" + oList[i].id + "</span>");
                str.push("</p>");
            }
            $divHtml.append(str.join(""));
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

function getSpecsByParent2(parentId) {
    var getcodeurl = basePath + "ea/scBudget/sajax_ea_getSpecsByParent.jspa";
    $.ajax({
        url: encodeURI(getcodeurl),
        type: "get",
        async: false,
        data: {parentId:parentId},
        dataType: "json",
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var oList = member.list;
            var $divHtml;
            var str = [];
            if (null == oList || oList.length <= 0) {
                return;
            }
            //规格管理
            $divHtml = $(".guigeType");
            $divHtml.html("");
            str.push("<ul>");

            if(oList.length==1){
                for (var i = 0; i < oList.length; i++) {
                    str.push("<li id='"+oList[i].parentId+"' code='"+oList[i].code+"'>"+oList[i].info+"</li>");
                }
                str.push("</ul>")
                $divHtml.append(str.join(""));
                $("#guigeTypeNew").val(oList[0].info);
                $("#guigeTypeNew").attr("code",oList[0].code);
                $("#guigeTypeNew").attr("parentId",oList[0].parentId);
                var unitOld = $("#unitOld").val();
                var unitNew = $("#variableID").val();
                console.log("比较");
                console.log(unitOld);
                console.log(unitNew);

                if(unitOld != unitNew){
                    getSpecsInfo(oList[0].code,oList[0].parentId,'');
                }

                $(".ggn2").show();
                $(".ggn1").hide();
                $(".div-guigeType").hide();
            }
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

function getSpecsByParent(parentId) {
    var getcodeurl = basePath + "ea/scBudget/sajax_ea_getSpecsByParent.jspa";
    $.ajax({
        url: encodeURI(getcodeurl),
        type: "get",
        async: true,
        data: {parentId:parentId},
        dataType: "json",
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var oList = member.list;
            var $divHtml;
            var str = [];
            if (null == oList || oList.length <= 0) {
                return;
            }
            //规格管理
            $divHtml = $(".guigeType");
            $divHtml.html("");
            str.push("<ul>");
            for (var i = 0; i < oList.length; i++) {
                str.push("<li id='"+oList[i].parentId+"' code='"+oList[i].code+"'>"+oList[i].info+"</li>");
            }
            str.push("</ul>")
            $divHtml.append(str.join(""));

            $(".div-guigeType").show();
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

function getSpecsInfo(code,parentId,unit) {
    console.log("单位："+unit);
    var getcodeurl = basePath + "ea/scBudget/sajax_ea_getSpecsInfo.jspa";
    $.ajax({
        url: encodeURI(getcodeurl),
        type: "get",
        async: false,
        data: {parentId:parentId, code:code},
        dataType: "json",
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var oList = member.list;
            var $divHtml;
            var str = [];
            if (null == oList || oList.length <= 0) {
                return;
            }
            //规格管理
            $divHtml = $("#ggn");
            $divHtml.html("");
            if(unit != "时间"){
                for (var i = 0; i < oList.length; i++) {
                    str.push("<li class='clearfix'>");
                    str.push("<p>"+oList[i].specs+"</p>");
                    str.push("<input type='text' info='"+oList[i].specs+"' class='attri1'>");
                    str.push("<input type='hidden' class='attri2' value='"+oList[i].specs+"-'>");
                    str.push("</li>");
                    // str.push("<p>m</p>");
                }
            }else{
                // for (var i = 0; i < oList.length; i++) {
                //     str.push("<li class='clearfix'>");
                //     str.push("<p>"+oList[i].specs+"</p>");
                //     // str.push("<input type='text' info='"+oList[i].specs+"' class='attri1'>");
                //     if(i==0){
                //         str.push("<input type='text' info='"+oList[i].specs+"' id="+i+" class='attri1' readonly autocomplete='off' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', maxDate:'#F{$dp.$D(\\'"+(i+1)+"\\')}'})\" >");
                //     } else {
                //         str.push("<input type='text' info='"+oList[i].specs+"' id="+i+" class='attri1' readonly autocomplete='off' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\\'"+(i-1)+"\\')}'})\" >");
                //     }
                //
                //     str.push("<input type='hidden' class='attri2' value='"+oList[i].specs+"-'>");
                //     str.push("</li>");
                //     // str.push("<p>m</p>");
                // }

                var currentDate = formatDate(new Date());
                // var currentDate = formatDate(new Date("2024-12-20"));
                var start = currentDate+" 00:00:00";
                var end = currentDate+" 23:59:59";
                if(oList[0].specs=="起止时间" || oList[0].specs=="截止时间"){
                    for (var i = 0; i < oList.length; i++) {
                        str.push("<li class='clearfix'>");
                        str.push("<p>"+oList[i].specs+"</p>");
                        // str.push("<input type='text' info='"+oList[i].specs+"' class='attri1'>");
                        if(i==0){
                            str.push("<input type='text' info='"+oList[i].specs+"' value='"+start+"' id="+i+" class='attri1' readonly autocomplete='off' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', maxDate:'#F{$dp.$D(\\'"+(i+1)+"\\')}'})\" >");
                        } else {
                            str.push("<input type='text' info='"+oList[i].specs+"' value='"+end+"' id="+i+" class='attri1' readonly autocomplete='off' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\\'"+(i-1)+"\\')}'})\" >");
                        }

                        str.push("<input type='hidden' class='attri2' value='"+oList[i].specs+"-'>");
                        str.push("</li>");
                        // str.push("<p>m</p>");
                    }
                }else{
                    var v='';
                    if(oList[0].specs!="小时"){
                        v='1';
                    }
                    for (var i = 0; i < oList.length; i++) {
                        str.push("<li class='clearfix'>");
                        str.push("<p>"+oList[i].specs+"</p>");
                        str.push("<input type='text' info='"+oList[i].specs+"' class='attri1' value='"+v+"'>");
                        str.push("<input type='hidden' class='attri2' value='"+oList[i].specs+"-'>");
                        str.push("</li>");
                        // str.push("<p>m</p>");
                    }
                    if(oList[0].specs!="小时"){
                        var arrList = [{"specs":"开始时间"},{"specs":"截止时间"}];
                        for (var i = 0; i < arrList.length; i++) {
                            str.push("<li class='clearfix'>");
                            str.push("<p>"+arrList[i].specs+"</p>");
                            // str.push("<input type='text' info='"+oList[i].specs+"' class='attri1'>");
                            if(i==0){
                                str.push("<input type='text' info='"+arrList[i].specs+"' value='"+start+"' id="+i+" class='attri1' readonly autocomplete='off' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', maxDate:'#F{$dp.$D(\\'"+(i+1)+"\\')}'})\" >");
                            } else {
                                str.push("<input type='text' info='"+arrList[i].specs+"' value='"+end+"' id="+i+" class='attri1' readonly autocomplete='off' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\\'"+(i-1)+"\\')}'})\" >");
                            }

                            str.push("<input type='hidden' class='attri2' value='"+arrList[i].specs+"-'>");
                            str.push("</li>");
                            // str.push("<p>m</p>");
                        }
                    }
                }
            }

            $divHtml.append(str.join(""));
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

function removeExist(id){
    $("#"+id).remove();
    var paths="";
    $(".div-tupian2 .diyFilePath").each(function () {
        var path = $(this).text().trim();
        paths += path + ",";
    });
    $("#ImagesPath").val(paths);
    console.log("最终："+$("#ImagesPath").val());
}

function removeVideo(id){
    $("#"+id).remove();
    var paths="";
    $(".div-shipin2 .diyFilePath").each(function () {
        var path = $(this).text().trim();
        paths += path + ",";
    });
    $("#VideoPath").val(paths);
    console.log("最终："+$("#VideoPath").val());
}

function formatDate(date){
    var year = date.getFullYear();
    var month = `0${date.getMonth()+1}`.slice(-2);
    var day = `0${date.getDate()}`.slice(-2);
    return `${year}-${month}-${day}`;
}

function searchGoods(){
    var typeId = $("#tradeID").val();
    var typeName = $("#tradeName").val();
    window.location.href = basePath + "ea/scBudget/ea_toGoodsSearch.jspa?editFlag=add&billsType="+billsType+"&typeId="+typeId+"&typeName="+typeName;
}
function setGoods(){
    if(null != goodsId && "" != goodsId){
        $("#goodsID").val(goodsId);
    }
    if(null != goodsName && "" != goodsName){
        $("#goodsName").val(goodsName);
    }
    if(null != barCode && "" != barCode) {
        $("#barCode").val(barCode);
    }
}

function setBusinessType(){
    if(null != typeId && "" != typeId){
        $("#tradeID").val(typeId);
        $("#productCode").val(typeId);
    }
    if(null != typeName && "" != typeName) {
        $("#tradeName").val(typeName);
        $("#typeID").val(typeName);
        $("#producttype").val(typeName);
    }
}
function delQueryParam() {
    var searchParams = new URLSearchParams(window.location.search);
    var params = new Map();
    searchParams.forEach(function(value, key) {
        params.set(key,value);
    });
    if(params.size > 0){
        params.delete("goodsId");
        params.delete("goodsName");
        params.delete("barCode");
        params.delete("typeId");
        params.delete("typeName");
    }
}