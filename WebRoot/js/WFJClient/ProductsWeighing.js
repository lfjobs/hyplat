var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
var iswin = u.indexOf('Windows') >1;
var goods='';
var host='127.0.0.1:33582';
$(function () {
    //高度计算
    var secconhei = $(window).height() - $("header").height() - $(".header").outerHeight(true) - $(".nav-1").outerHeight(true) -        $(".nav-2").outerHeight(true) - $(".p-button").outerHeight(true);
    $(".sec-con").css("height", secconhei - 10);
    codeID = "";
    var tt = "";
    //弹框提示
    $(".mm-alert div input").click(function () {
        //var ct = $(".mm-alert .ct").text();
        $(".mm-alert").hide();
    });

    if (showButton != null && showButton != "") {
        $(".p-button").hide();
        $(".sec-con").height(610) + "px";
    }

    if (ppname != null && ppname != "") {
        $("#ppname").attr("placeholder", ppname);
    }
    ppname = productCate(codeID, ppname);
    firstCate();


    if (isAndroid == true) {
        Android.weighingInitAndroid();
    }else if(iswin){
        //100毫秒一次获取秤的重量和稳定值
        setInterval(function() {
            $.ajax({
                url: "http://" + host + "/api/Scale/Weight",
                dataType: "json",
                type: 'get',
                cache: false,//IE下要关闭cache，否则不会刷新
                data: null,
                success: function (data) {
                    /**
                     * 服务安装文件地址“http://www.impf2010.com/upload_files\ADSService\ADSService.7z”
                     * 下载后解压，按照安装说明配置
                     **/
                    $(".weight").val(data.Weight.toFixed(3));
                    if (data.IsSteady == 0) {
                        $(".weight").css("color", "red");
                    }
                    else {
                        $(".weight").css("color", "green");
                    }
                },
                error: function (XMLHttpRequest) {
                    alert("获取重量失败, " + XMLHttpRequest.responseJSON.Message);
                }
            });
        }, 100);
    }

    localforage.getItem('hiddenform').then(function (value) {
        //当离线仓库中的值被载入时，此处代码运行
        console.log(value);
        if (value != null && value != "") {
            goods = value.goods;
        }
    }).catch(function (err) {
        // 当出错时，此处代码运行
        console.log(err);
    });

    //一级标签击选中
    $(document).on("click", ".nav-1 li", function () {
        $(this).parent().children("li").removeClass("active");
        $(this).addClass("active");
        // alert($(this).index());
        if ($(this).index() == 0) {//点击全部
            $(".ul_list").hide();
            codeID = "";
            pageNumber = 0;
            pageCount = 0;
            $(".ul_list_sp").html("");
            $(".ul_list").html("");
            if (tt != null && tt != "") {
                clearTimeout(tt);
            }
            ppname = productCate(codeID, ppname);
        } else {
            $(".ul_list").show();
            var codePID = $(this).find(".codePID").text();
            secondCate(codePID);
        }
    });
    //二级标签点击选中
    $(document).on("click", ".nav-2 li", function () {
        pageNumber = 0;
        pageCount = 0;
        $(this).parent().children("li").removeClass("active");
        $(this).addClass("active");
        codeID = $(this).find(".codeID").text();
        productCate(codeID, ppname);

    });
    //商品点击
    $(document).on("click", ".ul_list_sp li", function () {
        $(".invCheck .goodsName").val($(this).find(".gp").text());
        $(".invCheck .plu").val($(this).find(".plu").text()); //plu
        $(".invCheck .hhh").val($(this).find(".al").text()); //alternativeItemID 01126
        $(".invCheck .prcc").val($(this).find(".prc").text()); //kgm 计价单位
        $(".invCheck .pd").val($(this).find(".ppd").text()); //ppid
        $(".invCheck .goodsid").val($(this).find(".gid").text());

        if ($(this).find(".prc").text() == "KGM") {
            $(".invCheck .weightLi").show();
            $(".invCheck .weight").show();
            $(".invCheck .inputnum").hide();
            $(".invCheck .inputnumLi").hide();
            //$(".invCheck .wvalue").text("0.00");
        } else {
            $(".invCheck .weightLi").hide();
            $(".invCheck .inputnum").show();
            $(".invCheck .inputnumLi").show();
            $(".invCheck .inputnum").val("");
            $(".invCheck .inputnum").focus();
        }
        $(".invCheck").show();
        $("body").addClass("body_yc");
    });

    $(".p-button").click(function () {
        $(".weigh").show();
        $("body").addClass("body_yc");
    });

    //点击确定按钮
    $(".isok").click(function () {
        document.activeElement.blur();
        var reg = new RegExp(/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,4})?$/);//称重商品重量
        $product = $(this).parent().parent().parent().parent();
        var ppid = ($product.find(".pd").val() == undefined ? null : $product.find(".pd").val());
        var goodid = ($product.find(".goodsid").val() == undefined ? null : $product.find(".goodsid").val());
        var prcc = ($product.find(".prcc").val() == undefined ? null : $product.find(".prcc").val());
        var al = ($product.find(".al").val() == undefined ? null : $product.find(".al").val());
        var num;
        if (prcc == "PCS") {
            num = $product.find(".inputnum").val();
        } else {
            num = $product.find(".weight").val();
            if (num == null || num == "") {
                alert("请确保称已经连接！");
                return false;
            }
        }

        if (!reg.test(num)) {
            alert("请确保数据有效！");
            $product.find(".inputValue").val("");
            return false;
        }

        var fhnum;
        $.each(goods, function (index, item) {
            if (item != null && item.pid == ppid) {
                fhnum = item.gqnum;
                return false;
            }
        });


        var params = {
            'ppId': ppid == undefined ? null : ppid,
            'goodid': goodid,
            'weight': num,
            'fhnum': fhnum
        };

        localforage.setItem('params', params).then(function (value) {    // 当值被存储后，可执行其他操作
                if (localforage.getItem('hiddenform') != null && sort == 1) {
                window.location.href = basePath + "ea/ruku/ea_getProductByComid.jspa?ppid=" + ppid + "&companyid=" + companyID + "&staffid=" + staffid + "&sccid=" + sccid;
            } else {
                $.ajax({
                    url: "/ea/initialize/sajax_ea_getProduct.jspa",
                    type: "get",
                    async: false,
                    dataType: "json",
                    data: {
                        "compayid": companyID,
                        "ppid": ppid
                    },
                    success: function (data) {
                        var member = eval("(" + data + ")");
                        var error = member.error;
                        if (error != null && error != "") {
                            alert(error);
                        } else {
                            var objList = member.objList;
                            var depotid = member.销售库;
                            var obj = objList[0];
                            if (obj[7] == "00" && obj[8] == "00") {
                                alert("没有设置佣金");
                                return false;
                            } else {
                                var formgoods = [];
                                formgoods.push({"name": "ppid", "value": obj[0]});
                                formgoods.push({"name": "goods", "value": obj[1]});
                                formgoods.push({"name": "goodsname", "value": obj[2]});
                                formgoods.push({"name": "invenquantity", "value": obj[3]});
                                formgoods.push({"name": "variableid", "value": obj[4]});
                                formgoods.push({"name": "brand", "value": obj[5]});
                                formgoods.push({"name": "cskc", "value": num});
                                formgoods.push({"name": "kfid", "value": depotid});
                                formgoods.push({"name": "kfname", "value": "销售库"});
                                formgoods.push({"name": "codeid", "value": obj[6]});
                                formgoods.push({"name": "codevalue", "value": obj[7]});
                                formgoods.push({"name": "barcode", "value": al});
                                localforage.setItem('formgoods', formgoods).then(function (value) {
                                    // 当值被存储后，可执行其他操作
                                    console.log(value);
                                }).catch(function (err) {    // 当出错时，此处代码运行
                                    console.log(err);
                                });
                            }
                        }
                    }
                });

                if (isAndroid == true) {
                    Android.weighingCloseAndroid();
                } else {
                    alert("请在安卓设备访问");
                }
                if (localforage.getItem('formjum') != null && sort == 1) {
                    window.location.href = "/page/ea/main/finance/invoicing/InitialiaeAdd_Good.jsp";
                } else {
                    window.history.go(-1);
                    /*window.close();*/
                }
            }
        }).catch(function (err) {    // 当出错时，此处代码运行
            console.log(err);
        });
        return false;
    });

    //点击选中
    $(document).on("click", ".nav-1 ul li,.nav-2 ul li,section.sec-con ul li", function () {
        $(this).parents("ul").find("li").removeClass("active");
        $(this).addClass("active");
        var thiseq = $(this).index();
        if (thiseq > 0) {
            var scrleft = 0;
            $(this).parents("ul").children("li").each(function () {
                if ($(this).index() < thiseq) {
                    scrleft += $(this).outerWidth(true);
                }
            });
            console.log(scrleft);
            $(this).parents("div").scrollLeft(scrleft);
        }
    });

    //关闭弹框展示
    $(".inspection-sheet .sec-btn div:last-of-type input,.weigh .sec-btn div:last-of-type input").click(function () {
        $(".inspection-sheet").hide();
        $(".weigh").hide();
        $("body").removeClass("body_yc");
    });

    //清零
    $("input.zero").click(function () {
        if (isAndroid == true) {
            Android.weighingZeroAndroid();
            if (!$(".invCheck .inputnum").is(':hidden')) {
                $(".invCheck .inputnum").val("0");
            } else {
                $(".invCheck .weight").val("0.0");
            }
        } else if(iswin){
            $.ajax({
                url: "http://" + host + "/api/Scale/Zero",
                dataType: "json",
                type: 'post',
                success: function (_data) {
                    if (!$(".invCheck .inputnum").is(':hidden')) {
                        $(".invCheck .inputnum").val("0");
                    } else {
                        $(".invCheck .weight").val("0.0");
                    }
                },
                error: function (XMLHttpRequest) {
                    $(".mm-alert .ct").text("归零失败, " + XMLHttpRequest.responseJSON.Message);
                    $(".mm-alert").show();
                }
            })
        }
    });

    //去皮
    $("input.peeled").click(function () {
        if (isAndroid == true) {
            Android.weighingTareAndroid();
        } else if(iswin){
            $.ajax({
                url: "http://" + host + "/api/Scale/Tare",
                dataType: "json",
                type: 'post',
                success: function (_data) {
                    $(".mm-alert .ct").text("去皮成功");
                    $(".mm-alert").show();
                },
                error: function (XMLHttpRequest) {
                    $(".mm-alert .ct").text("去皮失败, " + XMLHttpRequest.responseJSON.Message);
                    $(".mm-alert").show();
                }
            })
        }
    });

    //搜索
    $("#ppname").keydown(function (event) {
        event = document.all ? window.event : event;
        if ((event.keyCode || event.which) == 13) {
            //window.location.href = basePath + "/page/WFJClient/ProductsLaunch/ProductsWeighing.jsp?companyID=" + companyID + "&ppname=" + $("#ppname").val();
            pageNumber = 0;
            pageCount = 0;
            productCate(null, $("#ppname").val());
            //$("#ppname")
            document.activeElement.blur();
            return false;
        }
    });

});
//加载称重一级分类
function firstCate() {
    var ulp = basePath
        + "/ea/scale/sajax_ea_findScaleGoodsCate.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: false,
        dataType: "json",
        data: {
            companyID: companyID
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var catelist = member.catelist;
            var htmlstr = new Array();
            for (var i = 0; i < catelist.length; i++) {
                htmlstr.push("<li>");
                htmlstr.push("<span>" + catelist[i][1] + "</span>");
                htmlstr.push("<span class='codePID'style='display: none;'>" + catelist[i][0] + "</span>");
                htmlstr.push("</li>");

            }
            $(".fcate").append(htmlstr.join(""));
            //nav-1宽度计算
            var listWidth_1 = $(".nav-1 ul li").length;
            var listWidth = 0;
            for (var i = 0; i < listWidth_1; i++) {
                listWidth += $(".nav-1 ul").children("li").eq(i).outerWidth(true);
            }
            $(".nav-1 ul").width(listWidth + 6);

        },
        error: function (data) {
            console.log("加载一级分类失败");
        }
    });

}


//加载称重二级分类
function secondCate(codePID) {
    var ulp = basePath
        + "/ea/scale/sajax_ea_findSecondCate.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            companyID: companyID,
            codePID: codePID
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var twolist = member.twolist;
            var htmlstr = new Array();
            for (var i = 0; i < twolist.length; i++) {
                if (i == 0) {
                    htmlstr.push("<li class='active'>");
                } else {
                    htmlstr.push("<li>");
                }
                htmlstr.push(twolist[i][1]);
                htmlstr.push("<span class='codeID'style='display: none;'>" + twolist[i][0] + "</span>");
                htmlstr.push("</li>");

            }
            $(".scate").html(htmlstr.join(""));
            //ulwidth();
            $(".ul_list .active").click();
            //nav-2宽度计算
            var listWidth_1 = $(".nav-2 ul li").length;
            var listWidth = 0;
            for (var i = 0; i < listWidth_1; i++) {
                listWidth += $(".nav-2 ul").children("li").eq(i).outerWidth(true);
            }
            $(".nav-2 ul").width(listWidth + 6);
        },
        error: function (data) {
            console.log("加载二级分类失败");
        }
    });

}


//加载称重商品
function productCate(codeID, ppname) {
    if (pageNumber == 0) {
        $(".ul_list_sp").html("");
    }
    pageNumber += 1;
    var ulp = basePath
        + "/ea/scale/sajax_ea_findProductByCatePhone.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            companyID: companyID,
            codeID: codeID,
            "pageForm.pageNumber": pageNumber,
            "pageForm.pageSize": pageSize,
            "ppname": ppname,
            "searchtype": "1"
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var pageForm = member.pageForm;
            var htmlstr = new Array();
            $(".last").removeClass("last");

            if (pageForm != null && pageForm.list != null && pageForm.list.length > 0) {
                var list = pageForm.list;
                pageNumber = pageForm.pageNumber;
                pageCount = pageForm.pageCount;
                if (pageCount <= 0) {
                    htmlstr.push("暂无产品");
                }
                for (var i = 0; i < list.length; i++) {
                    //var fhnum=getfhnum(list[i][0]);
                    if (i == list.length - 1) {
                        htmlstr.push("<li data-name='" + list[i][0] + "' class='last goodsli'>");
                    } else {
                        htmlstr.push("<li data-name='" + list[i][0] + "' class='goodsli'>");
                    }
                    htmlstr.push("<p><img src='" + basePath + list[i][3] + "'/></p>");
                    htmlstr.push("<p>" + list[i][1] + "</p>");
                    htmlstr.push("<span class='gp' style='display: none;'>" + list[i][1] + "</span>");
                    //htmlstr.push("<span class='pr'style='display: none;'>"+list[i][4]+"</span>");    金额
                    htmlstr.push("<span class='gid'style='display: none;'>" + list[i][2] + "</span>");   //goodsid
                    htmlstr.push("<span class='prc'style='display: none;'>" + list[i][5] + "</span>");   //kgm 计价单位
                    htmlstr.push("<span class='plu'style='display: none;'>" + list[i][6] + "</span>");   //plu
                    htmlstr.push("<span class='al'style='display: none;'>" + list[i][7] + "</span>");    //alternativeItemID 01126
                    htmlstr.push("<span class='ppd'style='display: none;'>" + list[i][0] + "</span>");   //ppid
                    //htmlstr.push("<span class='fhnum'style='display: none;'>" + fhnum + "</span>");   //ppid
                    //htmlstr.push("<span class='prit'style='display: none;'>"+list[i][9]+"</span>");   pricetype 0
                    //htmlstr.push("<span class='costm'style='display: none;'>"+list[i][10]+"</span>"); 出厂价 23
                    //htmlstr.push("<span class='activityIDm'style='display: none;'>"+list[i][11]+"</span>"); setup20190121K7AZ99YZWM0000052127
                    htmlstr.push("<input" + list[i][0] + "/>");   //ppid
                    htmlstr.push("</li>");
                }
            } else {
                htmlstr.push("<span>暂无产品</span>");
            }
            $(".ul_list_sp").append(htmlstr.join(""));
            if (ppname != null && ppname != "") {
                ppname = "";
            }
        },
        error: function (data) {
            console.log("加载商品");
        }
    });
    return null;
}

//加载称重商品
function searchPlu(plu) {
    var ulp = basePath
        + "/ea/scale/sajax_ea_findProductPLU.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            companyID: companyID,
            plu: plu
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var objs = member.objs;
            if (objs == null) {
                return;
            }
            $(".chengzhong_js .goodsname").text(objs[1]);
            $(".chengzhong_js .plu").text(objs[7]);
            $(".chengzhong_js .pr").text(objs[4]);
            $(".chengzhong_js .prcc").text(objs[6]);
            $(".chengzhong_js .hhh").text(objs[8]);
            $(".chengzhong_js .pd").text(objs[0]);
            $(".chengzhong_js .ppprit").text(objs[9]);
            $(".chengzhong_js .cccostm").text(objs[10]);
            $(".chengzhong_js .aaactivityID").text(objs[11]);

            $(".chengzhong").show();
            if (objs[6] == "KGM") {
                $(".chengzhong_js .weight").show();
                $(".chengzhong_js .inputnum").hide();
                $(".chengzhong_js .wvalue").text("0.00");
                getWeight();

            } else {
                $(".chengzhong_js .weight").hide();
                $(".chengzhong_js .inputnum").show();
                $(".chengzhong_js #inputnum").val("");
                $(".chengzhong_js #inputnum").focus();
            }

            $(".totalMoney").text("0.00");
            $(".cz").fadeOut('fast');

        },
        error: function (data) {
            console.log("查询PLU商品");
        }
    });

}

//称重
function getWeight(data) {
    var member = eval("(" + data + ")");
    var type = member.type;
    /*$(".mm-alert .ct").text(member.type);
    $(".mm-alert").show();*/
    var weight = member.weight;
    $(".weight").val(member.weight);
}

function getWebWeight() {
    $.ajax({
        url: "http://" + host + "/api/Scale/Weight",
        dataType: "json",
        type: 'get',
        cache: false,//IE下要关闭cache，否则不会刷新
        data: null,
        success: function (data) {
            $(".weight").val(data.Weight.toFixed(3));
            if (data.IsSteady == 0) {
                $(".mm-alert .ct").text("称加载失败！");
                $(".mm-alert").show();
            }
        },
        error: function (XMLHttpRequest) {
            alert("获取重量失败, " + XMLHttpRequest.responseJSON.Message);
        }
    });
}


/*function clearNoNum(obj) {
 obj.value = obj.value.replace(/[^\d.]/g, "");  //清除“数字”和“.”以外的字符
 obj.value = obj.value.replace(/^\./g, "");  //验证第一个字符是数字而不是.
 obj.value = obj.value.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的.
 //obj.value = obj.value.replace(/b(0+)/g,""); //清除多余的'0'
 obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");

 }*/


