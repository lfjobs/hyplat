var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
$(function () {

    // //获取扫描枪内容
    // window.onload = function (e) {
    //     var code = "";
    //     var lastTime, nextTime;
    //     var lastCode, nextCode;
    //
    //
    //         document.onkeypress = function (e) {
    //
    //             nextCode = e.which;
    //             console.log(nextCode + "------------");
    //             nextTime = new Date().getTime();
    //
    //             if (lastCode != null && lastTime != null && nextTime - lastTime <= 30) {
    //                 code += String.fromCharCode(lastCode);
    //             } else if (lastCode != null && lastTime != null && nextTime - lastTime > 100) {
    //                 code = "";
    //             }
    //
    //             lastCode = nextCode;
    //             lastTime = nextTime;
    //
    //         }
    //
    //             this.onkeypress = function (e) {
    //                 if (e.which == 13) {
    //                     if (code != "" && code.length <= 13) {
    //                         $(".barcode").val("");
    //                         searchGoods(code);
    //                     }
    //                     code = "";
    //
    //                 }
    //             }
    //
    //
    // }
    $.getFloat = function (number, n) {
        n = n ? parseInt(n) : 0;
        if (n <= 0) {
            return Math.round(number);
        }
        number = Math.round(number * Math.pow(10, n)) / Math.pow(10, n); //四舍五入
        //number = Number(number).toFixed(n); //补足位数
        return number;
    };



    //修改商品时调用
    if (posNum != null && posNum != "") {  //社区购物车
        sqCartlist(posNum);
        $("#wm").show();
    } else if (journalNum != null && journalNum != "") {
        $(".table").html("");
        number = 0;
        updateViewGoods(journalNum);
        barCode = "";
    }

    if (posNum != null && posNum != ""&&fhform!="3") {
        getScanRecord();
        $(".tr-span").show();
    } else {
        $(".tr-span").hide();
    }

    //首页第一次扫描
    if (barCode != null && barCode != "") {
        $(".shop").html("");
        if (nopay == "0") {
            var firstsearch = localStorage.getItem("firstsearch");
            if (firstsearch == "index") {
                searchGoods(barCode);
                localStorage.setItem("firstsearch", "");
            }
        }
    }

    //增加数量
    $(document).on("click", ".jia", function () {
        var $tr = $(this).parents("li");
        var sgrId = $tr.attr("id");

        var bc = $tr.find(".bc").text();
        var specNum = $tr.find(".specNum").val();
        if (bc.substring(0, 2) == "21") {

            if(fh=="1") {
                var shot = showTime();
                $(".mb").show();
                $(".tipcon").text("称重商品须依次扫描");
                $(".alert_weigh_").show();
                shot;
                return;
            }else{
                var  sendNum = 0;
                if (fh == "2"&& posNum != null && posNum != "") {
                    if ($(this).attr("class").indexOf("ps") != -1) {
                        if (Number($tr.find(".dnum").val()) == 0) {
                            $tr.find(".dnum").val(Number($tr.find(".dnum").val()) + 1);
                            $tr.find(".psn").val(Number($tr.find(".psn").val()) + 1);
                            $tr.find(".ztn").val(Number($tr.find(".ztn").val()) - 1);
                            sendNum = 1;

                        }else{
                                var shot = showTime();
                                $(".mb").show();
                                $(".tipcon").text("称重商品须依次扫描");
                                $(".alert_weigh_").show();
                                shot;
                                return;
                            }

                    }else{
                        if (Number($tr.find(".ztn").val()) == 0) {
                            $tr.find(".dnum").val(Number($tr.find(".dnum").val()) - 1);
                            $tr.find(".psn").val(Number($tr.find(".psn").val()) - 1);
                            $tr.find(".ztn").val(Number($tr.find(".ztn").val())+1);
                            sendNum = 0;

                        }else{
                            var shot = showTime();
                            $(".mb").show();
                            $(".tipcon").text("称重商品须依次扫描");
                            $(".alert_weigh_").show();
                            shot;
                            return;
                        }
                    }



                       var  itemNum =$tr.find(".inum").val();
                        var ppid = $tr.find(".ppID").text();
                        var stardard = $tr.find(".stardard").val();
                        if (stardard == null || stardard == "" || stardard == undefined) {
                            stardard = "默认规格";
                        }
                        var barCode = $tr.find(".bc").text();

                        //说明是社区首页入口，需要增加社区购物车
                        sqCartChange(ppid, stardard, itemNum, "jia", barCode, sendNum, "1", "");

                }
                return;
            }


        }

        $(this).prev(".num").val(Number($(this).prev(".num").val()) + 1);
        var itemNum = 1;
        var sendNum = null;
        if (specNum != "" && specNum != undefined && specNum != "null") {
            itemNum = Number(specNum);
            $tr.find(".inum").val(Number($tr.find(".inum").val()) + Number(specNum));
            if (fh == "2") {

                if ($(this).attr("class").indexOf("ps") != -1) {
                    $tr.find(".dnum").val(Number($tr.find(".dnum").val()) + Number(specNum));
                    sendNum = Number(specNum);
                }

            }

        } else {
            $tr.find(".inum").val(Number($tr.find(".inum").val()) + 1);
            if (fh == "2") {
                if ($(this).attr("class").indexOf("ps") != -1) {
                    $tr.find(".dnum").val(Number($tr.find(".dnum").val()) + 1);
                    sendNum = 1;
                }

            }
        }

        jisuan();

        if (posNum != null && posNum != "") {
            var ppid = $tr.find(".ppID").text();
            var stardard = $tr.find(".stardard").val();
            if (stardard == null || stardard == "" || stardard == undefined) {
                stardard = "默认规格";
            }
            var barCode = $tr.find(".bc").text();
            var showNum = 0;
            $tr.find(".num").each(function () {
                showNum += Number($(this).val());

            })
            //说明是社区首页入口，需要增加社区购物车
            sqCartChange(ppid, stardard, itemNum, "jia", barCode, sendNum, showNum, "");
        }

        joinOrReduceGoods(sgrId, $tr.find(".inum").val());

    });
    //减少数量
    $(document).on("click", ".jian", function () {

        var itemNum = 1;
        var $tr = $(this).parents("li");
        var sgrId = $tr.attr("id");
        var renum = 1;
        var specNum = $tr.find(".specNum").val();
        var bc = $tr.find(".bc").text();
        if (bc.substring(0, 2) == "21") {
            var shot = showTime();
            $(".mb").show();
            $(".tipcon").text("称重商品须依次扫描");
            $(".alert_weigh_").show();
            shot;

            return;
        }
        if (fh == "1" && Number($(this).next(".num").val()) == 1) {
            $tr.remove();
            renum = 0;
        } else {
            if (fh == "2") {

                var h = Number($(this).parents(".tr").find(".number1").find(".num").val()) + Number($(this).parents(".tr").find(".number2").find(".num").val());
                if (h == 1) {
                    return;

                }
                if (Number($(this).parent().find(".num").val()) == 0) {
                    return;
                }
            }

            var sendNum = null;
            $(this).next(".num").val(Number($(this).next(".num").val()) - 1);
            if (specNum != "" && specNum != undefined) {
                itemNum = Number(specNum);
                $tr.find(".inum").val(Number($tr.find(".inum").val()) - Number(specNum));

                if (fh == "2") {
                    if ($(this).attr("class").indexOf("ps") != -1) {
                        $tr.find(".dnum").val(Number($tr.find(".dnum").val()) - Number(specNum));
                        sendNum = Number(specNum);
                    }

                }


            } else {
                $tr.find(".inum").val(Number($tr.find(".inum").val()) - 1);
                if (fh == "2") {
                    if ($(this).attr("class").indexOf("ps") != -1) {
                        $tr.find(".dnum").val(Number($tr.find(".dnum").val()) - 1);
                        sendNum = 1;
                    }

                }
            }

        }

        jisuan();
        if (posNum != null && posNum != "") {
            var ppid = $tr.find(".ppID").text();
            var stardard = $tr.find(".stardard").val();
            if (stardard == null || stardard == "" || stardard == undefined) {
                stardard = "默认规格";
            }
            var barCode = $tr.find(".bc").text();
            //说明是社区首页入口，需要减少社区购物车
            var showNum = 0;
            $tr.find(".num").each(function () {
                showNum += Number($(this).val());

            })
            sqCartChange(ppid, stardard, itemNum, "jian", barCode, sendNum, showNum, "");

        }

        joinOrReduceGoods(sgrId, renum == 0 ? 0 : $tr.find(".inum").val());
    });

    //调价弹出输入密码层
    $(document).on("click", ".tr-span", function () {
        var $val = $(this).parent().clone();
        $("#sjvalue").html($val);
        $(".div-tiaojia").show();
        $(".txtNum").val("");
    });

    //关闭输入密码层
    $(document).on("click", ".close", function (event) {
        $(".txtNum").val("");
        $(".div-tiaojia").hide();
    });

    //关闭变价层
    $(document).on("click", ".close-2", function (event) {
        $(".barcode").focus();
        $(".container").hide();
        $("#sjvalue").html("");
        $(".container").html("");
    });

    //变价保存
    $(document).on("click", "#save", function () {
        var a = true;
        var je = $("#je").val();
        var dj = $("#xtdj").val();
        var cb = $("#cb").val();
        var sl = $("#sl").text();
        var sj = $("#dj").val();
        var cb = $("#cb").val();
        var yj = $("#yj").val();
        var dl = $("#dl").val();
        var pri = $("#pri").val();

        if(je==""||je==null||sj==""||sj==null||cb==""||cb==null){
            $(".mm-alert .ct").text("不可为空,必须为有效数字");
            $(".mm-alert").show();
            a = false;
            return false;
        }

        if (cb < 0 || je < 0 || dj < 0) {
            $(".mm-alert .ct").text("不可为负数,必须为有效数字");
            $(".mm-alert").show();
            a = false;
            return false;
        }

        if (dj == 0) {
            if (confirm("确定要把价格设置成0吗？")) {
                a = true;
            } else {
                a = false;
                return false;
            }
        }
        if (a) {
            var parme = {
                "dj": dj,
                "cb": cb,
                "yj": yj,
                "dl": dl,
                "pri": pri,
                "comid": $("#sjvalue").find(".companyID").text(),
                "userid": staffID,
                "priceid": $("#sjvalue").find(".activityID").val(),
                "ppid": $("#sjvalue").find(".ppID").text(),
                "pritype": $("#sjvalue").find(".pritype").val(),
                "jlid": $("#sjvalue").find(".sgrId").val()
            }
            var url = basePath + "ea/sm/sajax_ea_savePrice.jspa?";
            $.ajax({
                url: url,
                type: "get",
                async: false,
                dataType: "json",
                data: parme,
                success: function cbf(data) {
                    var member = eval("(" + data + ")");
                    var falg = member.falg;
                    if (falg) {
                        console.log("调价成功");
                        var pcid = member.pcid;
                        var sgrid = $("#sjvalue").find(".tr").attr("id");
                        $(".shop").find("#" + sgrid).find(".pritype").val(5);
                        $(".shop").find("#" + sgrid).find(".activityID").val(pcid);
                        $(".shop").find("#" + sgrid).find(".intPrice").val(sj);
                        $(".shop").find("#" + sgrid).find(".price").text(sj);
                        $(".shop").find("#" + sgrid).find(".costmoney").val(cb);
                        $(".shop").find("#" + sgrid).find(".tprice").text($("#je").val());
                        $(".close-2").click();
                        jisuan();
                    } else {
                        console.log("调价失败");
                    }
                },
                error: function cbf(data) {
                    console.log("失败");
                }
            });
        }
    });

    //购物袋
    $("#shop_car").click(function () {

        if (!$(".alert_shopping").is(":hidden")) {
            $(".alert_shopping_").hide();
            $(".alert_shopping").hide();
            return false;

        }
        $(".bagtbl").html("");

        var url = basePath + "ea/sm/sajax_ea_searchShoppBag.jspa?";
        $.ajax({
            url: url,
            type: "get",
            async: false,
            dataType: "json",
            data: {
                ccompanyID: ccompanyID,
                comID: companyID,
                lxType: lxType
            },
            success: function cbf(data) {
                var member = eval("(" + data + ")");
                var baglist = member.baglist;
                if (baglist.length < 2) {
                    // alert("暂无购物袋");
                    $(".mb").hide();
                    $(".tipcon").text("暂无购物袋");
                    $(".alert_weigh_").show();

                    return false;
                }
                var ar = new Array();
                for (i = 0; i < 2; i++) {
                    var obj1 = baglist[i];
                    ar.push("<li>");
                    ar.push("<div style='display:none;'><input type='hidden' class='pid' value='" + obj1[1] + "'><input type='hidden' class='companyid' value='" + obj1[3] + "'><input type='hidden' class='barCode' value='" + obj1[4] + "'></div>");
                    ar.push("<input type='hidden' class='pritype' value='" + obj1[5] + "'><input type='hidden' class='costmoney' value='" + obj1[6] + "'><input type='hidden' class='activityID' value='" + obj1[7] + "'>")

                    if (i == 0) {
                        ar.push("<div class='xz big'>");
                    } else {
                        ar.push("<div class='xz'>");
                    }
                    ar.push("<img src='" + basePath + "images/supermarket/gou2.png' alt='' class='gou'>");
                    ar.push("<img src='" + basePath + "images/supermarket/131.png' alt=''>");
                    ar.push("</div>");
                    ar.push("<h4><span class='goodsName'>" + obj1[0] + "</span></h4>");
                    ar.push("<p class='money'>&yen;<span class='price'>" + obj1[2] + "</span></p>");
                    ar.push("<div class='jj'>");
                    ar.push("<input type='button' value='-' class='min'>");
                    ar.push("<input type='text' value='0' class='text itemNum' readonly='readonly'>");
                    ar.push("<input type='button' value='+' class='add'>");
                    ar.push("</div>");
                    ar.push("</li>");

                }

                $(".bagtbl").append(ar.join(""));
                $(".alert_shopping_").show();
                $(".alert_shopping").show();
            },
            error: function cbf(data) {
                console.log("获取购物袋失败");
            }

        })

    });

    //返回首页
    $("#backhome").click(function () {
        if (posNum != null && posNum != "") {
            if (back == "2") {
                var backUrl = localStorage.getItem("backUrl");
                document.location.href = backUrl;
            } else {
                window.history.go(-1);
            }

        } else {
            document.location.href = basePath + "ea/sm/ea_index.jspa?cs=1";
        }

    });

    //确定查询
    $("#btn").click(function () {
        var barcode = $(".barcode").val();
        if (barcode != "" && barcode.length <= 13) {
            $(".barcode").val("");
            searchGoods(barcode);
        }

    });

    /*购物袋数量加减*/
    $(document).on("click", ".jj .add", function () {
        var nub = $(this).parents(".jj").find(".text");      //当前数量
        var xz = $(this).parents("li").find(".xz");
        nub.val(parseFloat(nub.val()) + 1);
        xz.addClass("active");

    });

    $(document).on("click", ".jj .min", function () {
        var nub = $(this).parents(".jj").find(".text");  //当前数量
        var xz = $(this).parents("li").find(".xz");
        if (nub.val() == 0) {
            nub.val(0);
        } else if (nub.val() == 1) {
            xz.removeClass("active");
            nub.val(0);

        } else {
            nub.val(parseFloat(nub.val()) - 1);
            xz.addClass("active");
        }

    });

    //不需要袋子
    $("#noneed").click(function () {
        $(".alert_shopping_").hide();
        $(".alert_shopping").hide();

    });
    //确定选择袋子
    $("#sure").click(function () {

        var bo = false;
        $(".bagtbl li").each(function () {
            var num = $(this).find(".itemNum").val();
            if (num != "0") {
                $(".alert_shopping_").hide();
                $(".alert_shopping").hide();
                bo = true;
                var goodsname = $(this).find(".goodsName").text();
                var price = $(this).find(".price").text();
                var barCode = $(this).find(".barCode").val();
                var companyID = $(this).find(".companyid").val();
                var ppID = $(this).find(".pid").val();
                var pritype = $(this).find(".pritype").val();
                var costmoney = $(this).find(".costmoney").val();
                var activityID = $(this).find(".activityID").val();


                var bool = false;
                $(".bc").each(function () {
                    if ($(this).text() == barCode) {
                        var $tr = $(this).parents("li");

                        if (fh == "1") {

                            $tr.find(".num").val(Number($tr.find(".num").val()) + Number(num));

                            $tr.find(".inum").val($tr.find(".num").val());

                        } else {
                            $tr.find(".number1").find(".num").val(Number($tr.find(".number1").find(".num").val()) + Number(num));

                            var num1 = 0;
                            $tr.find(".num").each(function () {
                                num1 += Number($(this).val());

                            })
                            $tr.find(".inum").val(num1);

                        }
                        bool = true;
                        return true; //跳出循环
                    }

                });
                if (bool) {
                    jisuan();
                } else {
                    number++;

                    var ar = new Array();

                    ar.push("<li class='tr'>");
                    if (posNum != null && posNum != "") {
                        ar.push("<a  class='tr-span1' style='float: left;font-weight: 500;font-size: 22px;'></a>");
                    }
                    ar.push("<h5 style='display:none;'><input name='selfCartmap[" + number + "].companyId' type='hidden' value='" + companyID + "'/><span class='companyID'>" + companyID + "</span><input name='selfCartmap[" + number + "].pid' type='hidden' value='" + ppID + "'/><span class='ppID'>" + ppID + "</span><input name='selfCartmap[" + number + "].barCode' type='hidden' value='" + barCode + "'/><span class='bc'>" + barCode + "</span></h5>");
                    ar.push("<h5 class='serial'>" + number + "</h5>");
                    ar.push("<h5 class='name'><input name='selfCartmap[" + number + "].goodsName' type='hidden' value='" + goodsname + "' class='goodsName'/>" + goodsname + "</h5>")

                    if (fh == "1") {
                        ar.push("<div class='number'><input name='selfCartmap[" + number + "].itemNum' class='inum' type='hidden' value='" + num + "'/><input type='button' value='-' class='min jian'> <input type='text' value='" + num + "' class='nub num' readonly='readonly'><input type='button' value='+' class='add jia'></div>");
                    } else {
                        ar.push("<div class='number number1'><input type='button' value='-' class='min jian'> <input type='text' value='" + num + "' class='nub num' readonly='readonly'><input type='button' value='+' class='add jia'></div>");
                        ar.push("<div class='number number2'><input name='selfCartmap[" + number + "].specNum' class='specNum' type='hidden' value=''/><input name='selfCartmap[" + number + "].sendNum' class='dnum' type='hidden' value='0'/><input name='selfCartmap[" + number + "].itemNum' class='inum' type='hidden' value='" + num + "'/><input type='button' value='-' class='min jian ps'> <input type='text' value='0' class='nub num' readonly='readonly'><input type='button' value='+' class='add jia ps'></div>");


                    }
                    ar.push("<p style='display:none;'><input name='selfCartmap[" + number + "].price' class='intPrice' type='hidden' value='" + price + "'/><span class='price'>" + price + "</span></p>");
                    ar.push("<p class='money'>&yen;<span class='tprice'>" + price + "</span><input name='selfCartmap[" + number + "].pritype' type='hidden' value='" + pritype + "' class='pritype'/><input name='selfCartmap[" + number + "].costmoney' type='hidden' value='" + costmoney + "' class='costmoney'/><input name='selfCartmap[" + number + "].activityID' class='activityID' type='hidden' value='" + activityID + "'/></p>");
                    ar.push("<img src='" + basePath + "images/supermarket/ico-del.png'class='del'>");
                    ar.push("</li>");
                    $(".shop").append(ar.join(""));
                    jisuan();

                }

                if (posNum != null && posNum != "") {
                    sqCartChange(ppID, "默认规格", num, "jia", barCode, "0", num, "");
                }
                if(fhform=="3"){

                    $(".jia").hide();
                    $(".jian").hide();

                    $(".number").css("border","none");

                    $(".nub").css("width","80px");
                    // $(".nub").css("font-size","18px");
                    $(".number span").css("font-size","15px");


                }

            }
        });
        if (bo == false) {
            var shot = showTime();
            $(".mb").show();
            $(".tipcon").text("请选择购物袋数量");
            $(".alert_weigh_").show();
            shot;
            return false;
        }

    });

    //去结算
    $("#pay").click(function () {


        try {
            var u = window.navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            if (isAndroid == true) {
                Android.speechOutputForAndroid("去付款");
            } else {
                console.log("请在安卓设备访问！");
            }
        } catch (error) {

        }
        if ($(".shop .tr").length == 0) {
            var shot = showTime();
            $(".mb").show();
            $(".tipcon").text("没有商品可结算");
            $(".alert_weigh_").show();
            shot;
            return;
        }
        if (Number($(".totalmoney")) == 0) {
            var shot = showTime();
            $(".mb").show();
            $(".tipcon").text("商品金额为0无法支付");
            $(".alert_weigh_").show();
            shot;
            return;
        }
        if(fhform=="3") {
            if (Number(nowWeight) < Number(initWeight)) {
                document.form.submit.click();
                token = 13;
            }else{
                //直接关门，没购物
                closeDoor();
            }


        }else{
            document.form.submit.click();
            token = 13;
        }

    });

    /*删除(垃圾桶)*/
    $(document).on("click", ".wet figure .shop .del", function () {
        var sgrId = $(this).parents("li").attr("id")


        if (posNum != null && posNum != "") {
            var bc = $(this).parents(".tr").find(".bc").text();
            var ppid = $(this).parents(".tr").find(".ppID").text();
            var stardard = $(this).parents(".tr").find(".stardard").val();
            if (stardard == null || stardard == "" || stardard == undefined) {
                stardard = "默认规格";
            }
            deleteCartGoods(ppid, stardard, posNum, bc);
        }

        //删除扫码记录
        deleteScanRecord(sgrId);
        $(this).parents("li").remove();
        jisuan();
    });

    /*无码称重*/
    $("#wm").click(function () {

        var totalNum = $(".totalnum").text();

        document.location.href = basePath + "page/ea/main/marketing/supermarket/selfservice/scaleWeightNew.jsp?companyID=" + companyID + "&totalNum=" + totalNum + "&posNum=" + posNum + "&ccompanyID=" + ccompanyID + "&relateID=" + relateID + "&lxType=" + lxType + "&staffID=" + staffID;


    });
    //关闭无码称重弹框
    $(".alert_wed #del").click(function () {
        $(".alert_wed_").hide();
        $(".jp-se").hide();
    });

    //确定查询
    $(".cfm").click(function () {
        var parameter = $.trim($(this).parent().find(".parameter").val());
        if (parameter != "") {
            findGoods(parameter);

        }

    });

    //关闭搜索框
    $("#del-3").click(function () {
        $(".alert_sssp_").hide();

    });

    //查询选择
    $(document).on("click", ".salegoods tr", function () {
        $(".active").removeClass("active");
        $(this).addClass("active");

    });

    //查询结果确定
    $("#sure2").click(function () {
        if ($(".alert_sssp_ .active").length == 0) {
            return;
        }
        $(".parameter").val("");
        $(".alert_sssp_").hide();
        var goodsname = $(".active").find(".name").text();
        var plu = $(".active").find(".hh").text();
        var price = $(".active").find(".tm").text();
        var kpc = $(".active").find(".dj").text();
        var aal = $(".active").find(".aal").text();
        var pppd = $(".active").find(".pppd").text();
        var pprit = $(".active").find(".pprit").text();
        var ccostm = $(".active").find(".ccostm").text();
        $(".scalemain .goodsname").text(goodsname + "(" + plu + ")");
        $(".scalemain .price").text(price);
        $(".scalemain .hhh").text(aal);
        $(".scalemain .pd").text(pppd);
        $(".scalemain .ppprit").text(pprit);
        $(".scalemain .cccostm").text(ccostm);
        showWeight(kpc);
    });

    /*无码称重-热销产品*/
    $(document).on("click", ".alert_wed .hotsale li", function () {
        $(".alert_wed .goodsname").text($(this).find(".gp").text());
        $(".alert_wed .price").text($(this).find(".pr").text());
        $(".alert_wed .hhh").text($(this).find(".al").text());
        $(".alert_wed .pd").text($(this).find(".ppd").text());
        $(".alert_wed .ppprit").text($(this).find(".prit").text());
        $(".alert_wed .cccostm").text($(this).find(".costm").text());
        $(".alert_wed .aaactivityID").text($(this).find(".activityIDm").text());

        showWeight($(this).find(".prc").text());
    });

    //无搜索结果关闭
    $("#confirm").click(function () {
        $(".alert_weigh_").hide();
        $(".parameter").val("");
    });

    /*称重后确定提示温馨提示*/
    $(".alert_wed #toCon").click(function () {
        var money = $(".scalemain .totalMoney").text();
        if (money == "" || Number(money) == 0) {
            return false;
        }
        $(".alert_wxts_").show();

        var name = $(".scalemain .goodsname").text();
        var goodsname = name.substring(0, name.indexOf("("));
        var al = $(".scalemain .hhh").text();
        var price = $(".scalemain .price").text();
        var ppID = $(".scalemain .pd").text();
        var barCode = getEnBarCode(al, money);
        var pritype = $(".scalemain .ppprit").text();
        var costmoney = $(".scalemain .cccostm").text();
        var activityID = $(".scalemain .aaactivityID").text();

        var value = "";
        if (!$(".scalemain .num").is(':hidden')) {
            value = $("#inputnum").val();
        } else {
            value = $(".scalemain .wvalue").val();
        }

        number++;

        var ar = new Array();

        ar.push("<li class='tr'>");
        ar.push("<h5 style='display:none;'><input name='selfCartmap[" + number + "].companyId' type='hidden' value='" + companyID + "'/><span class='companyID'>" + companyID + "</span><input name='selfCartmap[" + number + "].pid' type='hidden' value='" + ppID + "'/><span class='ppID'>" + ppID + "</span><input name='selfCartmap[" + number + "].barCode' type='hidden' value='" + barCode + "'/><span class='bc'>" + barCode + "</span></h5>");
        ar.push("<h5 class='serial'>" + number + "</h5>");
        ar.push("<h5 class='name'><input name='selfCartmap[" + number + "].goodsName' type='hidden' value='" + goodsname + "' class='goodsName'/>" + goodsname + "</h5>")


        if (fh == "1") {
            ar.push("<div class='number'><input name='selfCartmap[" + number + "].specNum' class='specNum' type='hidden' value=''/><input name='selfCartmap[" + number + "].itemNum' class='inum' type='hidden' value='" + value + "'/><input type='button' value='-' class='min jian'> <input type='text' value='1' class='nub num' readonly='readonly'><input type='button' value='+' class='add jia'></div>");
        } else {
            ar.push("<div class='number number1'><input type='button' value='-' class='min jian'> <input type='text' value='1' class='nub num' readonly='readonly'><input type='button' value='+' class='add jia'></div>");
            ar.push("<div class='number number2'><input name='selfCartmap[" + number + "].specNum' class='specNum' type='hidden' value=''/><input name='selfCartmap[" + number + "].sendNum' class='dnum' type='hidden' value='0'/><input name='selfCartmap[" + number + "].itemNum' class='inum' type='hidden' value='" + value + "'/><input type='button' value='-' class='min jian ps'> <input type='text' value='0' class='nub num' readonly='readonly'><input type='button' value='+' class='add jia ps'></div>");


        }


        ar.push("<p  style='display:none;'><input name='selfCartmap[" + number + "].price' class='intPrice' type='hidden' value='" + price + "'/><span class='price'>" + price + "</span></p>");
        ar.push("<p class='money'>&yen;<span class='tprice'>" + money + "</span><input name='selfCartmap[" + number + "].pritype' type='hidden' value='" + pritype + "' class='pritype'/><input name='selfCartmap[" + number + "].costmoney' type='hidden' value='" + costmoney + "' class='costmoney'/><input name='selfCartmap[" + number + "].activityID' class='activityID' type='hidden' value='" + activityID + "'/></p>");
        ar.push("<img src='" + basePath + "images/supermarket/ico-del.png'class='del'>");
        ar.push("</li>");
        $(".shop").append(ar.join(""));
        jisuan();
        if (posNum != null && posNum != "") {
            //说明是社区首页入口，需要增加社区购物车
            sqCartChange(ppID, "默认规格", value, "jia", barCode, "0", "1", "");
        }
    });

    /*温馨提示-继续称重*/
    $(".alert_wxts .jx").click(function () {
        $(".alert_wxts_").hide();
    });
    /*温馨提示-删除*/
    $(".alert_wxts .del-2").click(function () {
        $(".alert_wxts_").hide();
    });

    //立即结算
    $(".alert_wxts .lj").click(function () {
        $(".alert_wxts_").hide();
        $(".alert_wed_").hide();
        $(".jp-se").hide();

    });
    //输入数量
    $("#inputnum").on("input", function (e) {
        var price = $(".scalemain .price").text();
        if (!$(".scalemain .num").is(':hidden')) {
            $(".totalMoney").text((Number(price) * Number($("#inputnum").val())).toFixed(2));
        }
    });
    //输入数量键盘
    $("#inputnum").on("focus", function () {
        $(this).addClass("jp-s");
        $("#wed_ipt").removeClass("jp-s");
        $("#wed_ipt-3").removeClass("jp-s");
    });
    //输入条码/货号/名称/拼音码键盘
    $("#wed_ipt").on("focus", function () {
        $(this).addClass("jp-s");
        $("#inputnum").removeClass("jp-s");
        $("#wed_ipt-3").removeClass("jp-s");
    });
    //弹框输入条码/货号/名称/拼音码键盘
    $("#wed_ipt-3").on("focus", function () {
        $(this).addClass("jp-s");
        $("#inputnum").removeClass("jp-s");
        $("#wed_ipt").removeClass("jp-s");
    });

    //弹框提示
    $(".mm-alert div input").click(function () {
        //var ct = $(".mm-alert .ct").text();
        $(".mm-alert").hide();
    });

    $(document).on("input", ".upinput", function () {
        upinput($(this));
    });

    $(document).on("focus", ".upinput", function () {
        $(this).val(null);
    });

});
//扫码回车自动查询商品信息

document.onkeydown = function (evt) {//捕捉回车
    evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    if (key == 13) { //判断是否是回车事件。
        var barcode = $(".barcode").val();
        if (barcode != "" && barcode.length <= 13) {
            $(".barcode").val("");

            if (posNum != null && posNum != "") {
                closeKeyBoard();
            }
            searchGoods(barcode);

        }else{
            $(".barcode").val("");
        }
        return false;
    } else if (key == 190) {
        console.log("小数点");
    }
}

function searchGoods(barCode) {
    var bool = false;
    var ppID = "";
    var $tr = "";
    $(".bc").each(function () {
        if (barCode.substring(0, 2) != "21") {
            if ($(this).text() == barCode) {
                $tr = $(this).parents("li");
                var specNum = $tr.find(".specNum").val();
                ppID = $tr.find(".ppID").text();
                if (fh == "1") {
                    $tr.find(".num").val(Number($tr.find(".num").val()) + 1);
                } else {
                    $tr.find(".number1").find(".num").val(Number($tr.find(".number1").find(".num").val()) + 1);

                }
                if (specNum != "" && specNum != undefined) {
                    $tr.find(".inum").val(Number($tr.find(".inum").val()) + Number(specNum));
                    num = Number(specNum);
                } else {
                    if (fh == "1") {
                        $tr.find(".inum").val($tr.find(".num").val());
                    } else {

                        var num1 = 0;
                        $tr.find(".num").each(function () {
                            num1 += Number($(this).val());

                        })
                        $tr.find(".inum").val(num1);
                    }
                    num = 1;
                }


                bool = true;
                return false; //跳出循环
            }
        }


    });
    if (bool) {
        jisuan();
        $(".comm figure .shop").scrollTop(9999999999);
        if (posNum != null && posNum != "") {
            //说明是社区首页入口，需要增加社区购物车
            var sendNum = null;
            if (fh == "2") {
                sendNum = 0;
            }
            var showNum = 0;
            $tr.find(".num").each(function () {
                showNum += Number($(this).val());
            })
            sqCartChange(ppID, "默认规格", num, "jia", barCode, sendNum, showNum, "");
        }


        joinOrReduceGoods($tr.attr("id"), $tr.find(".inum").val());
        return false;
    }


    var url = basePath + "ea/sm/sajax_ea_searchProduct.jspa";
    $.ajax({
        url: url,
        type: "get",
        async: false,
        dataType: "json",
        data: {
            barCode: barCode,
            ccompanyID: ccompanyID,
            lxType:lxType
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var result = member.result;
            if (result == "0") {
                number++;
                var uuid = member.uuid;
                var goodsname = member.goodsname;
                var price = member.price;
                var barCode = member.barCode;
                var companyID = member.companyID;
                var ppID = member.ppID;
                var money = member.money;
                var quantity = member.quantity;
                var pritype = member.pritype;
                var costmoney = member.costmoney;
                var activityID = member.activityID;
                var num = 1;
                if (money != "") {
                    num = round(Number(money / price));


                }
                if (quantity != "") {
                    num = quantity;
                }
                var ar = new Array();


                ar.push("<li class='tr' id='" + uuid + "'>");
                if (uuid != null && uuid != "" && posNum != null && posNum != "") {
                    ar.push("<a  class='tr-span' style='float: left;font-weight: 500;font-size: 22px;'>调价</a>");
                }
                ar.push("<h5 style='display:none;'><input name='selfCartmap[" + number + "].sgrId' class='sgrId' type='hidden' value='" + uuid + "'/><input name='selfCartmap[" + number + "].companyId' type='hidden' value='" + companyID + "'/><span class='companyID'>" + companyID + "</span><input name='selfCartmap[" + number + "].pid' type='hidden' value='" + ppID + "'/><span class='ppID'>" + ppID + "</span><input name='selfCartmap[" + number + "].barCode' type='hidden' value='" + barCode + "'/><span class='bc'>" + barCode + "</span></h5>");
                ar.push("<h5 class='serial'>" + number + "</h5>");
                ar.push("<h5 class='name'><input name='selfCartmap[" + number + "].goodsName' type='hidden' value='" + goodsname + "' class='goodsName'/>" + goodsname + "</h5>")
                if (fh == "1") {
                    ar.push("<div class='number'><input name='selfCartmap[" + number + "].specNum' class='specNum' type='hidden' value='" + quantity + "'/><input name='selfCartmap[" + number + "].itemNum' class='inum' type='hidden' value='" + num + "'/><input type='button' value='-' class='min jian'> <input type='text' value='1' class='nub num' readonly='readonly'><input type='button' value='+' class='add jia'></div>");
                } else {
                    ar.push("<div class='number number1'><input type='button' value='-' class='min jian'> <input type='text' value='1' class='nub num' readonly='readonly'><input type='button' value='+' class='add jia'></div>");
                    ar.push("<div class='number number2'><input name='selfCartmap[" + number + "].specNum' class='specNum' type='hidden' value='" + quantity + "'/><input name='selfCartmap[" + number + "].sendNum' class='dnum' type='hidden' value='0'/><input name='selfCartmap[" + number + "].itemNum' class='inum' type='hidden' value='" + num + "'/><input type='button' value='-' class='min jian ps'> <input type='text' value='0' class='nub num' readonly='readonly'><input type='button' value='+' class='add jia ps'></div>");


                }
                ar.push("<p  style='display:none;'><input name='selfCartmap[" + number + "].price' class='intPrice' type='hidden' value='" + price + "'/><span class='price'>" + price + "</span></p>");

                ar.push("<p class='money'>&yen;<span class='tprice'>" + price + "</span><input name='selfCartmap[" + number + "].pritype' type='hidden' value='" + pritype + "' class='pritype'/><input name='selfCartmap[" + number + "].costmoney' type='hidden' value='" + costmoney + "' class='costmoney'/><input name='selfCartmap[" + number + "].activityID' class='activityID' type='hidden' value='" + activityID + "'/></p>");
                ar.push("<img src='" + basePath + "images/supermarket/ico-del.png'class='del'>");
                ar.push("</li>");
                $(".shop").append(ar.join(""));
                jisuan();

                if (posNum != null && posNum != "") {
                    //说明是社区首页入口，需要增加社区购物车
                    var sgrId = joinScanRecord(posNum, ppID, companyID, num, barCode, "默认规格", price, goodsname, relateID, uuid);
                    sqCartChange(ppID, "默认规格", num, "jia", barCode, 0, 1, sgrId);
                } else {
                    joinScanRecord(posNum, ppID, companyID, num, barCode, "默认规格", price, goodsname, relateID, uuid);

                }

                //第一次扫码记录


            }

        },
        error: function cbf(data) {
            console.log("失败")
        }

    });
}

//统计总数量/总金额
function jisuan() {

    totalnum = 0;
    totalmoney = 0;
    var c = 0;
    $(".shop .tr").each(function () {
        c++;
        var num = Number($(this).find(".inum").val());
        var num1 = 0;
        var variableID = $(this).find(".variableID").val();
        $(this).find(".num").each(function () {
            var n = $(this).val();
            if(fhform=="3"){

                if(variableID=="KG"||variableID=="kg"){
                    n = 1;
                }
            }
            num1 += Number(n);

        })
        var price = Number($(this).find(".price").text());

        var money = Number(num * price).toFixed(2);
        $(this).find(".tprice").text(money);
        totalnum = totalnum + num1;
        totalmoney = Number(totalmoney) + Number(money);

    });

    $(".totalnum").text(totalnum);
    $(".totalmoney").text(Number(totalmoney).toFixed(2));
    $("#morre").val($(".totalmoney").text());

}


function updateViewGoods(journalNum) {

    var url = basePath + "ea/sm/sajax_ea_ajaxOrderDetail.jspa?";
    $.ajax({
        url: url,
        type: "get",
        async: false,
        dataType: "json",
        data: {
            journalNum: journalNum
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var goodlist = member.goodlist;
            var ar = new Array();
            for (var i = 0; i < goodlist.length; i++) {
                var bar = goodlist[i].barCode;
                var num = goodlist[i].itemNum;
                var snum = goodlist[i].sendNum;
                var specNum = goodlist[i].specNum;
                var ynum = Number(num) - Number(snum);
                var goodsName = goodlist[i].goodsName;
                var sgrIds = member.sgrId;
                if (bar.substring(0, 2) == "21") {
                    num = 1;
                }
                if (goodsName.substring(0, 1) == "#") {
                    num = Number(num) / Number(specNum);
                    snum = Number(snum) / Number(specNum);
                    ynum = num - snum;
                }
                number++;
                ar.push("<li class='tr' id='" + sgrIds + "'>");
                ar.push("<h5 style='display:none;'><input name='selfCartmap[" + number + "].sgrId' type='hidden' class='sgrId' value='" + sgrIds + "'/><input name='selfCartmap[" + number + "].companyId' type='hidden' value='" + goodlist[i].companyId + "'/><span class='companyID'>" + goodlist[i].companyId + "</span><input name='selfCartmap[" + number + "].pid' type='hidden' value='" + goodlist[i].pid + "'/><span class='ppID'>" + goodlist[i].pid + "</span><input name='selfCartmap[" + number + "].barCode' type='hidden' value='" + goodlist[i].barCode + "'/><span class='bc'>" + goodlist[i].barCode + "</span></h5>");
                ar.push("<h5 class='serial'>" + number + "</h5>");
                ar.push("<h5 class='name'><input name='selfCartmap[" + number + "].goodsName' type='hidden' value='" + goodlist[i].goodsName + "' class='goodsName'/>" + goodlist[i].goodsName + "</h5>")

                if (fh == "1") {
                    ar.push("<div class='number'><input name='selfCartmap[" + number + "].specNum' class='specNum' type='hidden' value='" + specNum + "'/><input name='selfCartmap[" + number + "].itemNum' class='inum' type='hidden' value='" + goodlist[i].itemNum + "'/><input type='button' value='-' class='min jian'> <input type='text' value='" + num + "' class='nub num' readonly='readonly'><input type='button' value='+' class='add jia'></div>");
                } else {
                    ar.push("<div class='number number1'><input type='button' value='-' class='min jian'> <input type='text' value='" + ynum + "' class='nub num' readonly='readonly'><input type='button' value='+' class='add jia'></div>");
                    ar.push("<div class='number number2'><input name='selfCartmap[" + number + "].specNum' class='specNum' type='hidden' value='" + specNum + "'/><input name='selfCartmap[" + number + "].sendNum' class='dnum' type='hidden' value='" + goodlist[i].sendNum + "'/><input name='selfCartmap[" + number + "].itemNum' class='inum' type='hidden' value='" + num + "'/><input type='button' value='-' class='min jian ps'> <input type='text' value='" + snum + "' class='nub num' readonly='readonly'><input type='button' value='+' class='add jia ps'></div>");


                }
                ar.push("<p style='display: none;'><input name='selfCartmap[" + number + "].price' class='intPrice' type='hidden' value='" + goodlist[i].price + "'/><span class='price'>" + goodlist[i].price + "</span></p>");
                ar.push("<p class='money'>&yen;<span class='tprice'>" + goodlist[i].price + "</span><input name='selfCartmap[" + number + "].pritype' type='hidden' value='" + goodlist[i].pritype + "' class='pritype'/><input name='selfCartmap[" + number + "].costmoney' type='hidden' value='" + goodlist[i].costmoney + "' class='costmoney'/><input name='selfCartmap[" + number + "].activityID' class='activityID' type='hidden' value='" + goodlist[i].activityID + "'/></p>");

                ar.push("<img src='" + basePath + "images/supermarket/ico-del.png'class='del'>");
                ar.push("</li>");


            }
            $(".shop").append(ar.join(""));
            jisuan();


        },
        error: function cbf(data) {
            console.log("失败");
        }

    })
}

function re_load(journalNum) {
    if (token) {
        var totalmoney = $(".totalmoney").text();
        //查询需要配送的数量 如果没有后续不显示地址
        var nb = 0;
        if (fh == "2") {
            $(".number2 .nub").each(function () {
                nb += Number($(this).val());
            });
            if (nb > 0) {
                fh = "2";
            } else {
                fh = "1";
            }
        }
        if(fhform == "3"){

            getAuthcc(journalNum);
            return false;
        }
        document.location.href = basePath + "page/ea/main/marketing/supermarket/selfservice/payMode.jsp?journalNum=" + journalNum + "&totalMoney=" + totalmoney + "&totalNum=" + totalnum + "&posNum=" + posNum + "&staffID=" + staffID + "&ccompanyID=" + ccompanyID + "&comID=" + companyID + "&directUrl=" + encodeURIComponent(directUrl) + "&companyName=" + companyName + "&fh=" + fh+ "&fhform=" + fhform;
        // document.location.href = basePath+"ea/sm/ea_showErCode.jspa?journalNum="+journalNum+"&totalMoney="+totalmoney+"&totalNum="+totalnum;
    }

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

function round(value) {

    var aStr = value.toString();
    var aArr = aStr.split('.');
    if (aArr.length > 1) {
        if (aArr[1].length > 3) {
            value = Number(aStr).toFixed(3);
        }
    }

    return Number(value);
}

//社区购物车
function sqCartlist(posNum) {

    var url = basePath + "/ea/smg/sajax_sm_getSqCartList.jspa?d=" + new Date();
    $.ajax({
        url: url,
        type: "get",
        async: false,
        dataType: "json",
        data: {
            posNum: posNum,
            ccompanyID: ccompanyID,
            lxType: lxType,
            fhform:fhform
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var goodlist = member.goodlist;

            var ar = new Array();


            for (var i = 0; i < goodlist.length; i++) {
                var bar = goodlist[i][4];//条码
                var num = goodlist[i][13];//活动id
                var snum = goodlist[i][7];//已取数量
                var specNum = goodlist[i][8] == null ? "" : goodlist[i][8];
                var spcation = goodlist[i][9] == null ? "" : goodlist[i][9];
                var ynum = Number(num) - Number(snum);
                var goodsName = goodlist[i][1];
                var sgrId = goodlist[i][14];
                var variableID = goodlist[i][16];

                if (specNum != "") {
                    goodsName = "#" + spcation + goodsName;
                }
                // if (bar != null && bar.length > 2) {
                //     if (bar.substring(0, 2) == "21") {
                //         ynum = 1;
                //     }
                // }

                if (goodsName.substring(0, 1) == "#") {
                    num = Number(num) / Number(specNum);
                    snum = Number(snum) / Number(specNum);
                    ynum = num - snum;
                }
                number++;
                ar.push("<li class='tr' id='" + sgrId + "'>");
                if (sgrId != null && sgrId != "" && posNum != null && posNum != "") {
                    if(fhform!="3"){
                        ar.push("<a  class='tr-span' style='float: left;font-weight: 500;font-size: 22px;'>调价</a>");

                    }else{
                        ar.push("<a  class='tr-span1' style='float: left;font-weight: 500;font-size: 22px;'></a>");

                    }
                }else  if ((sgrId == null || sgrId == "") && posNum != null && posNum != ""){
                    ar.push("<a  class='tr-span1' style='float: left;font-weight: 500;font-size: 22px;'></a>");

                }
                ar.push("<h5 style='display:none;'><input name='selfCartmap[" + number + "].sgrId' class='sgrId' type='hidden' value='" + sgrId + "'/><input class='stardard' name='selfCartmap[" + number + "].stardard' type='hidden' value='" + goodlist[i][6] + "'/><input name='selfCartmap[" + number + "].companyId' type='hidden' value='" + goodlist[i][5] + "'/><span class='companyID'>" + goodlist[i][5] + "</span><input name='selfCartmap[" + number + "].pid' type='hidden' value='" + goodlist[i][0] + "'/><span class='ppID'>" + goodlist[i][0] + "</span><input name='selfCartmap[" + number + "].barCode' type='hidden' value='" + goodlist[i][4] + "'/><span class='bc'>" + goodlist[i][4] + "</span></h5>");
                ar.push("<h5 class='serial'>" + number + "</h5>");
                ar.push("<h5 class='name'><input name='selfCartmap[" + number + "].goodsName' type='hidden' value='" + goodlist[i][1] + "' class='goodsName'/>" + goodsName + (goodlist[i][6] == '默认规格' || goodlist[i][6] == '' ? "" : "*" + goodlist[i][6]) + "</h5>")

                ar.push("<input name='selfCartmap[" + number + "].variableID' class='variableID' type='hidden' value='" +variableID + "'/>");
                if (fh == "1") {
                    ar.push("<input name='selfCartmap[" + number + "].totalNum' type='hidden'  class='totalNums'/>");

                    ar.push("<div class='number'><input name='selfCartmap[" + number + "].specNum' class='specNum' type='hidden' value='" + specNum + "'/><input name='selfCartmap[" + number + "].itemNum' class='inum' type='hidden' value='" + goodlist[i][3] + "'/><input type='button' value='-' class='min jian'> <input type='text' value='" + num +"' class='nub num' readonly='readonly'><p class='vaid' style='display:none;'>"+(variableID==null?'&nbsp;':variableID)+"</p><input type='button' value='+' class='add jia'></div>");
                } else {
                    ar.push("<div class='number number1'><input type='button' value='-' class='min jian'> <input type='text' value='" + ynum + "' class='ztn nub num' readonly='readonly'><input type='button' value='+' class='add jia'></div>");
                    ar.push("<div class='number number2'><input name='selfCartmap[" + number + "].specNum' class='specNum' type='hidden' value='" + specNum + "'/><input name='selfCartmap[" + number + "].sendNum' class='dnum' type='hidden' value='" + goodlist[i][7] + "'/><input name='selfCartmap[" + number + "].itemNum' class='inum' type='hidden' value='" + goodlist[i][3] + "'/><input type='button' value='-' class='min jian ps'> <input type='text' value='" + snum + "' class='psn nub num' readonly='readonly'><input type='button' value='+' class='add jia ps'></div>");
                }
                ar.push("<p style='display: none;'><input name='selfCartmap[" + number + "].price' class='intPrice' type='hidden' value='" + goodlist[i][2] + "'/><span class='price'>" + goodlist[i][2] + "</span></p>");
                ar.push("<p class='money'>&yen;<span class='tprice'>" + goodlist[i][2] + "</span><input name='selfCartmap[" + number + "].pritype' type='hidden' value='" + goodlist[i][10] + "' class='pritype'/><input name='selfCartmap[" + number + "].costmoney' type='hidden' value='" + goodlist[i][11] + "' class='costmoney'/><input name='selfCartmap[" + number + "].activityID' class='activityID' type='hidden' value='" + goodlist[i][12] + "'/></p>");
                if(fhform=="3") {
                    if(goodlist[i][1]=="大购物袋"||goodlist[i][1]=="小购物袋"){
                        ar.push("<img src='" + basePath + "images/supermarket/ico-del.png'class='del'>");

                    }else{
                        ar.push("<img src='" + basePath + "images/supermarket/ico-del.png'class='del' style='display:none;'>");

                    }

                }else{
                    ar.push("<img src='" + basePath + "images/supermarket/ico-del.png'class='del'>");

                }
            }
            $(".shop").append(ar.join(""));
            if(fhform=="3"){
                $(".vaid").show();
                $(".jia ").hide();
                $(".jian").hide();
                $(".number").css("border","none");
                $(".number").addClass("number-x")
                $(".nub").css("width","80px");
                $(".nub").css("margin-right","0.1rem");
                // $(".nub").css("font-size","18px");
                $(".number span").css("font-size","15px");


            }
            jisuan();


        },
        error: function cbf(data) {
            console.log("失败");
        }

    })
}

/**
 *
 * 获取返回的首页参数
 * @param posNum
 */
function ajaxGetAccess(posNum) {

    var ulp = basePath
        + "ea/smg/sajax_sm_ajaxGetAccess.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        dataType: "json",
        data: {
            posNum: posNum
        },
        success: function (data) {
            var me = eval("(" + data + ")");

            document.location.href = basePath + "ea/smg/sm_toSupermarketGoods.jspa?ccompanyID=" + me.ccompanyID + "&industryType=" + me.industryType + "&companyName=" + me.companyName + "&posNum=" + posNum;

        },
        error: function (data) {
            console.log("获取失败入口");
        }
    });
}

//社区加减购物车
function sqCartChange(ppid, stardard, amount, opr, barCode, sendNum, showNum, sgrId) {

    var url = basePath + "ea/smg/sajax_sm_addShoppingCart.jspa";
    if (opr == "jian") {
        url = basePath + "ea/smg/sajax_sm_reduceShoppingCart.jspa";

    }
    $.ajax({
        type: "POST",
        url: url,
        async: true,
        dataType: "json",
        data: {
            ppid: ppid,
            stardard: stardard,
            totalNum: amount,
            posNum: posNum,
            barCode: barCode,
            sendNum: sendNum,
            showNum: showNum,
            sgrId: sgrId,
            relateID: relateID

        },
        success: function (data) {


        },
        error: function (data) {
            //商品加入购物车失败
            alert("数据处理失败!");
            return;
        }
    });
}

//查询热销称重商品
function findHotScaleGoods() {


    var url = basePath + "ea/scale/sajax_ea_findHotSaleGoods.jspa";
    $.ajax({
        url: url,
        type: "get",
        async: false,
        dataType: "json",
        data: {
            companyID: companyID
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var prolist = member.prolist;
            var htmlstr = new Array();
            for (var i = 0; i < prolist.length; i++) {
                htmlstr.push("<li>");
                htmlstr.push("<span class='gp'>" + prolist[i][1] + "(" + prolist[i][6] + ")</span>");
                htmlstr.push("<span class='pr'style='display: none;'>" + prolist[i][3] + "</span>");
                htmlstr.push("<span class='prc'style='display: none;'>" + prolist[i][5] + "</span>");
                htmlstr.push("<span class='al'style='display: none;'>" + prolist[i][7] + "</span>");
                htmlstr.push("<span class='ppd'style='display: none;'>" + prolist[i][0] + "</span>");
                htmlstr.push("<span class='prit'style='display: none;'>" + prolist[i][8] + "</span>");
                htmlstr.push("<span class='costm'style='display: none;'>" + prolist[i][9] + "</span>");
                htmlstr.push("<span class='activityIDm'style='display: none;'>" + prolist[i][10] + "</span>");

                htmlstr.push("</li>");

            }
            $(".hotsale").html(htmlstr.join(""));


        },
        error: function cbf(data) {
            console.log("失败")
        }

    })

}

//查询称重商品
function findGoods(parameter) {


    var url = basePath + "ea/scale/sajax_ea_findGoods.jspa";
    $.ajax({
        url: url,
        type: "get",
        async: false,
        dataType: "json",
        data: {
            companyID: companyID,
            parameter: parameter
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var prolist = member.prolist;
            $(".salegoods").html("");
            if (prolist != null && prolist.length == 1) {
                //如果查询结果只有一个直接跳转回称重页面
                $(".scalemain .goodsname").text(prolist[0][1] + "(" + prolist[0][6] + ")");
                $(".scalemain .price").text(prolist[0][3]);
                $(".scalemain .hhh").text(prolist[0][7]);
                $(".scalemain .pd").text(prolist[0][0]);
                $(".scalemain .ppprit").text(prolist[0][8]);
                $(".scalemain .cccostm").text(prolist[0][9]);
                $(".scalemain .aaactivityID").text(prolist[0][10]);

                showWeight(prolist[0][5]);
            } else if (prolist.length == 0) {
                var shot = showTime();
                $(".mb").show();
                $(".tipcon").text("搜索无结果");
                $(".alert_weigh_").show();
                shot;
            } else {
                //否则跳转到查询页面
                var html = new Array();
                for (var i = 0; i < prolist.length; i++) {
                    html.push("<tr>");
                    html.push("<td class='name'>" + prolist[i][1] + "</td>");
                    html.push("<td class='hh'>" + prolist[i][6] + "</td>");
                    html.push("<td class='tm'>" + prolist[i][3] + "</td>");
                    html.push("	<td class='dj'>" + prolist[i][5] + "</td>");
                    html.push("	<td class='aal' style='display: none;'>" + prolist[i][7] + "</td>");
                    html.push("	<td class='pppd' style='display: none;'>" + prolist[i][0] + "</td>");
                    html.push("	<td class='pprit' style='display: none;'>" + prolist[i][8] + "</td>");
                    html.push("	<td class='ccostm' style='display: none;'>" + prolist[i][9] + "</td>");
                    html.push("	<td class='aactivityIDm' style='display: none;'>" + prolist[i][10] + "</td>");
                    html.push("</tr>");

                }
                $(".salegoods").html(html.join(""));
                $(".alert_sssp_").show();
            }


        },
        error: function cbf(data) {
            console.log("失败")
        }

    })

}

//补齐0
function pad2(num, n) {
    if ((num + "").length >= n) return num;
    return pad2("0" + num, n);
}

//计算生成条码
function getEnBarCode(al, money) {
    var C = "21" + al + pad2(Number(money) * 100, 5);
    var C1 = 0;
    var C2 = 0;
    var V = 0;
    for (var i = 1; i < C.length + 1; i++) {
        if (i % 2 == 1) {
            C1 += Number(C[i - 1]);
        } else {
            C2 += Number(C[i - 1]);
        }
    }
    var G1 = (Number(C1) + Number(C2) * 3).toString();
    var V = 10 - Number(G1.substring(G1.length - 1));

    return C + V + "";

}

//称重页面隐藏显示
function showWeight(kpc) {
    if (kpc == "KGM") {
        $(".scalemain .weight").show();
        $(".scalemain .num").hide();
        $(".alert_wed .scale1").show();
        $(".alert_wed .scale2").hide();

    } else {
        $(".scalemain .weight").hide();
        $(".scalemain .num").show();
        $(".alert_wed .scale1").hide();
        $(".alert_wed .scale2").show();
        $(".scalemain .num").find("input").focus();


    }
    $(".scalemain .totalMoney").text("0.00");
    $(".wvalue").val("0");
    $("#inputnum").val("");
    $(".scale1").text("请将以下商品置于电子称上");
    $(".parameter").val("");

}

/*键盘*/
/*数字1-9*/
function btnNum_onclick(i) {
    var values = $('.jp-s').val();
    if (values.length < 6) {
        $('.jp-s').val(values + i);
    }
    var id = $('.jp-s').attr("id");
    if (id == "inputnum") {
        var price = $(".scalemain .price").text();
        if (!$(".scalemain .num").is(':hidden')) {
            $(".totalMoney").text((Number(price) * Number($("#inputnum").val())).toFixed(2));
        }
    }

}

/*点*/
function dian() {
    var values = $('.jp-s').val();
    var dian = ".";
    $('.jp-s').val(values + dian);

}

/*清空*/
function clearText() {
    $('.jp-s').val("");
}

/*删除*/
function delText() {
    var value = $('.jp-s').val();
    var str = value.substring(0, value.length - 1);
    $('.jp-s').val(str);
}

/*确认*/
function confirm() {
    if ($(".container").html() == "") {
        var pw = $(".txtNum").val();
        if (pw == "") {
            $(".mm-alert .ct").text("请输入密码");
            $(".mm-alert").show();
            return;
        } else if (pw.length != 6) {
            $(".mm-alert .ct").text("密码为6位");
            $(".mm-alert").show();
            $(".txtNum").val("");
            return;
        }

        $.ajax({
            type: "GET",
            url: basePath + "/ea/sm/sajax_ea_verification.jspa",
            async: false,
            dataType: "json",
            data: {
                "pw": pw,
                "staffID": staffID,
                "comID": companyID,
            },
            success: function (data) {
                var member = eval('(' + data + ')');
                var result = member.result;

                if (result != "2") {
                    var ct = "";
                    if (result == "1") {
                        ct = "操作员账户没有绑定微分金账户";

                    } else if (result == "3") {
                        ct = "密码错误";
                    }
                    $(".txtNum").val("");
                    $(".mm-alert .ct").text(ct);
                    $(".mm-alert").show();
                } else {
                    if (companyID == "" || companyID == null) {
                        companyID = member.comID;
                    }
                    if (staffID == "" || staffID == null) {
                        staffID = member.staffID;
                    }
                    var params = {
                        'comID': $("#sjvalue").find(".companyID").text(),
                        'ppid': $("#sjvalue").find(".ppID").text(),
                        'inum': $("#sjvalue").find(".inum").val(),
                        'price': $("#sjvalue").find(".price").text(),
                        'tprice': $("#sjvalue").find(".tprice").text(),
                        'costmoney': $("#sjvalue").find(".costmoney").val(),
                        'pritype': $("#sjvalue").find(".pritype").val(),
                        'activityID': $("#sjvalue").find(".activityID").val(),
                        'jlid': $("#sjvalue").find(".sgrId").val()
                    };
                    $.ajax({
                        url: basePath + "/ea/sm/sajax_ea_getProudct.jspa?&date=" + new Date().toLocaleString(),
                        type: "get",
                        async: false,
                        dataType: "json",
                        data: params,
                        success: function cbf(data) {
                            var member = eval("(" + data + ")");
                            var prolist = member.prolist;
                            if (prolist.length > 0) {
                                $(".container").html("");
                                var pro = prolist[0];
                                var a1 = (pro[9] == null || pro[9] == "") ? 0 : pro[9];
                                var a2 = (pro[10] == null || pro[10] == "") ? 0 : pro[10];
                                var a3 = (pro[11] == null || pro[11] == "") ? 0 : pro[11];
                                var a4 = (pro[4] == null) ? "" : pro[4];
                                var a5 = (pro[5] == null) ? "" : pro[5];
                                var a6 = (pro[6] == null) ? "" : pro[6];
                                var html = new Array();
                                html.push("<section><ul><a href='#' class='close-2'></a>");
                                html.push("<li class='clearfix'><div class='div-img'><img src='" + basePath + pro[1] + "'/></div>");
                                html.push("<p><input type='text' class='input-bold' disabled value='" + pro[2] + "'/></p></li>");
                                /*html.push("<li class='clearfix'><p>计价单位</p><div><p>" + pro[3] + "</p></div></li>");
                                 html.push("<li class='clearfix'><p>产品条码</p><div><p>" + pro[3] + "</p></div></li>");*/
                                html.push("<li class='clearfix'><p>产品分类</p><div><p>" + a4 + "</p></div></li>");
                                html.push("<li class='clearfix'><p>产品规格</p><div><p>" + a5 + "</p></div></li>");
                                html.push("<li class='clearfix'><p>产品品牌</p><div><p>" + a6 + "</p></div></li>");
                                html.push("<li class='clearfix'><p>总金额</p><p><input type='number' id='je' class='upinput' onclick='test\(this\)' value='" + params.tprice + "'/>元</p></li>");
                                html.push("<li class='clearfix'><p>数量/重量</p><p><span id='sl'>" + params.inum + "</span></p></li>");
                                html.push("<li class='clearfix'><p>售价</p><p><input type='number' id='dj' class='upinput'  onclick='test\(this\)'  value='" + params.price + "'/>元</p></li>");
                                html.push("<li class='clearfix'><p>成本价</p><p><input type='number' id='cb' class='upinput'  onclick='test\(this\)'  value='" + pro[8] + "'/>元</p>");
                                html.push("<input type='hidden' id='priceid' name='priceid' value='" + pro[12] + "'/>");
                                html.push("<input type='hidden' id='jlid' name='jlid' value='" + params.trid + "'/>");
                                html.push("<input type='hidden' id='ppid' name='ppid' value='" + pro[0] + "'/>");
                                html.push("<input type='hidden' id='sj' value='" + pro[7] + " '/>");//售价
                                html.push("<input type='hidden' id='yj' value='" + a1 + "'/>");//业务佣金
                                html.push("<input type='hidden' id='dl' value='" + a2 + "'/>");//代理佣金
                                html.push("<input type='hidden' id='pri' value='" + a3 + "'/>");//消费红包
                                html.push("<input type='hidden' id='xtdj' value='" + pro[7] + " '/><input type='hidden'  id='sgrid'/>");//系统单价
                                html.push("</ul><div class='footer'><input type='button' name='' id='save' value='确定'/></div></section>");
                                $(".txtNum").val("");
                                $(".close").click();
                                $(".container").html(html.join(""));
                                $(".container").show();
                            }
                        },
                        error: function (data) {
                            console.log("加载失败");
                        }
                    });
                }
            },
            error: function (data) {
                console.log("失败");
            }
        });
    } else {
        $(".mm-alert .ct").text("页面加载失败！");
        $(".mm-alert").show();
    }

}


//获取重量
function getWeight() {
    //100毫秒一次获取秤的重量和稳定值
    setInterval(function () {

        $.ajax({
            url: "http://127.0.0.1:5017/api/Scale/Weight",
            dataType: "json",
            type: 'get',
            cache: false,//IE下要关闭cache，否则不会刷新
            data: null,
            success: function (data) {

                var v = data.Weight.toFixed(3);


                $(".wvalue").val(v);
                var price = $(".scalemain .price").text();
                if (!$(".weight").is(':hidden')) {
                    $(".totalMoney").text((Number(price) * Number(v)).toFixed(2));
                }


            },
            error: function (XMLHttpRequest) {
                //  alert("获取重量失败, " + XMLHttpRequest.responseJSON.Message);
            }
        });
    }, 100);
}

function closeKeyBoard() {
    try {
        Android.keyboardHide();
    } catch (e) {

    }
}

/**
 * 删除单个商品
 */
function deleteCartGoods(ppid, stardard, posNum, barCode) {
    var url = basePath + "ea/smg/sajax_sm_deleteCartGoods.jspa";

    $.ajax({
        type: "POST",
        url: url,
        async: true,
        dataType: "json",
        data: {
            ppid: ppid,
            stardard: stardard,
            posNum: posNum,
            barCode: barCode
        },
        success: function (data) {


        },
        error: function (data) {
            console.log("垃圾桶回收失败");
        }
    });

}

//第一次扫描
function joinScanRecord(posNum, ppID, companyID, num, barCode, stardard, price, goodsname, relateID, uuid) {
    var sgrId = "";
    var url = basePath + "ea/sm/sajax_ea_joinScanRecord.jspa";
    $.ajax({
        type: "POST",
        url: url,
        async: false,
        dataType: "json",
        data: {
            posNum: posNum,
            "scanGoods.posNum": posNum,
            "scanGoods.pid": ppID,
            "scanGoods.companyId": companyID,
            "scanGoods.itemNum": num,
            "scanGoods.barCode": barCode,
            "scanGoods.stardard": stardard,
            "scanGoods.price": price,
            "scanGoods.goodsName": goodsname,
            "scanGoods.relateID": relateID,

        },
        success: function (data) {
            var me = eval("(" + data + ")");
            sgrId = me.sgrId;
            $("li#" + uuid).attr("id", sgrId);
            $("li#" + sgrId).find(".sgrId").val(sgrId);

        },
        error: function (data) {
            console.log("扫码记录失败");
        }
    });
    return sgrId;


}

//第二次以后
function joinOrReduceGoods(sgrId, itemNum) {
    var url = basePath + "ea/sm/sajax_ea_joinOrReduceGoods.jspa";
    $.ajax({
        type: "POST",
        url: url,
        async: true,
        dataType: "json",
        data: {
            sgrId: sgrId,
            itemNum: itemNum
        },
        success: function (data) {

        },
        error: function (data) {
            console.log("扫码记录失败");
        }
    });


}

//删除扫码记录
function deleteScanRecord(sgrId) {
    var url = basePath + "ea/sm/sajax_ea_deleteScanRecord.jspa";
    $.ajax({
        type: "POST",
        url: url,
        async: true,
        dataType: "json",
        data: {
            sgrId: sgrId
        },
        success: function (data) {

        },
        error: function (data) {
            console.log("删除扫码记录失败");
        }
    });


}

function getScanRecord() {
    if (nopay != "0") {
        document.location.href = basePath + "ea/sm/ea_getScanRecord.jspa?posNum=" + posNum + "&companyId=" + companyID;
    }
}

//变价计算
function upinput(event) {
    console.log(isNaN(event.val()));
    var je = "";
    var dj = "";
    var cb = "";
    var sl = parseFloat($("#sl").text());
    //alert("sl:"+sl);
    var sj = parseFloat($("#sj").val());
    //alert("sj:"+sj);
    var cb = parseFloat($("#cb").val());
    //alert("cb:"+cb);
    var yj = parseFloat($("#yj").val());
    //alert("yj:"+yj);
    var dl = parseFloat(($("#dl").val() == null || $("#dl").val()) == "" ? 0 : $("#dl").val());
    //alert("dl:"+dl);
    var pri = parseFloat($("#pri").val());
    //alert("pri:"+pri);
    //alert("id:"+event.attr("id"));
    if (event.attr("id") == "je") {
        je = $.getFloat(parseFloat(event.val()), 4);
        //alert("je:"+je);
        dj = $.getFloat(je / sl, 4);
        //alert("dj:"+dj);
        cb = $.getFloat($.getFloat(dj / pri, 4) - yj - dl, 4);
        //alert("cb:"+cb);
        $("#dj").val(dj);
        $("#cb").val(cb);
    } else if (event.attr("id") == "dj") {
        dj = $.getFloat(parseFloat(event.val()), 4);
        //alert("dj:"+dj);
        je = $.getFloat(dj * sl, 4);
        //alert("je:"+je);
        cb = $.getFloat($.getFloat(dj / pri, 4) - yj - dl, 4);
        //alert("cb:"+cb);
        $("#je").val(je);
        $("#cb").val(cb);
    } else if (event.attr("id") == "cb") {
        cb = $.getFloat(parseFloat(event.val()), 4);
        //alert("cb:"+cb);
        dj = $.getFloat((cb + yj + dl) * pri, 4);
        //alert("dj:"+dj);
        je = $.getFloat(dj * sl, 4);
        //alert("je:"+je);
        $("#je").val(je);
        $("#dj").val(dj);
    }
    //dj = $.getFloat((cb + yj + dl) * pri, 4);
    //alert("dj:"+(cb + yj + dl));
    //je = $.getFloat(dj * sl, 4);
    //alert("je:"+je);
    $("#xtdj").val($.getFloat((cb + yj + dl), 4));
    /*$("#je").val(je);
     $("#dj").val(dj);
     $("#cb").val(cb);*/
}
//获取权限或者判断购物卡
function getAuthcc(journalNum){
    var totalMoney = $(".totalmoney").text();
    var url = basePath + "ea/sm/sajax_ea_getAuthcc.jspa";
    $.ajax({
        type: "POST",
        url: url,
        async: false,
        dataType: "json",
        data: {
            sccId: sccId,
            loginMode:loginMode,
            totalMoney:totalMoney,
            journalNum:journalNum,
            hgcode:hgcode
        },
        success: function (data) {
           var m = eval("("+data+")");
           var result = m.result;
           var remainMoney = m.remainMoney;
           if(loginMode=="scard"){
               if(result=="3"){
                   //积分不够扣，扣除剩余，不够的跳转到其他支付方式或者充值后自动扣除
                   document.location.href = basePath + "page/ea/main/marketing/supermarket/selfservice/payMode.jsp?journalNum=" + journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalnum + "&posNum=" + posNum + "&staffID=" + staffID + "&ccompanyID=" + ccompanyID + "&comID=" + companyID + "&directUrl=" + encodeURIComponent(directUrl) + "&companyName=" + companyName + "&fh=" + fh+ "&fhform=" + fhform+"&remainMoney="+remainMoney;

               }else  if(result=="4"){
            //积分充足，直接扣除

                       if(sccId!="") {
                           var ulp = basePath
                               + "/ea/sm/sajax_ea_scardPayOrder.jspa";
                           $.ajax({
                               type : "GET",
                               url : ulp,
                               async : true,
                               dataType : "json",
                               data:{
                                   sccId:sccId,
                                   totalMoney:totalMoney,
                                   journalNum:journalNum,
                                   fhform:fhform

                               },
                               success : function(data) {
                                   if(data != null && data != "" && data =="YZF"){
                                       alert("该订单已支付，请查看详情");
                                   }
                                   console.log("购物卡支付成功");
                                   document.location.href = basePath + "ea/sm/ea_viewOrderDetail.jspa?journalNum=" + journalNum+"&paytype=购物卡支付&posNum="+posNum+"&fhform=3";

                               },
                               error : function(data) {
                                   console.log("购物卡支付失败");
                               }
                           });
                       }



               }

           }else  if(loginMode=="phone"||loginMode=="scan"){//验证手机号
               var coinfee = m.coinfee;
               if(coinfee=="0"){
                   document.location.href = basePath + "page/ea/main/marketing/supermarket/selfservice/payMode.jsp?journalNum=" + journalNum + "&totalMoney=" + totalMoney + "&totalNum=" + totalnum + "&posNum=" + posNum + "&staffID=" + staffID + "&ccompanyID=" + ccompanyID + "&comID=" + companyID + "&directUrl=" + encodeURIComponent(directUrl) + "&companyName=" + companyName + "&fh=" + fh+ "&fhform="+fhform+"&remainMoney="+remainMoney;


               }else{
                   document.location.href = basePath + "ea/sm/ea_viewOrderDetail.jspa?journalNum=" + journalNum+"&paytype=购物卡支付&posNum="+posNum+"&fhform=3";

               }

           }
        },
        error: function (data) {

        }
    });

}

//var w = 90;
// function timeWeight(){
//
//     if(hgcode!=""&&hgcode!=null) {
//         // if(w==0){
//         //     w=Number(nowWeight);
//         // }
//        //  var s = setInterval(function () {
//        // getWeight(1);
//        //
//        //
//        //  }, 5000);
//     }
//
// }

// //读取称重数量 data 编号，重量|编号，重量|编号，重量 比如是这种形式
// function getWeight(data) {
//
//     var x=0.5+w;
//     if(x>=0){
//         //     var member = eval("(" + data + ")");
//
//         //  var weights = member.weight;
//
//         weights = "cp001,"+x+"-"+"cp002,"+x+"-";
//         compare(cplist,weights);
//
//         w=w-1;
//     }
//
//
// }

function onWeightChange(data) {

    weights = "";
    var  jsonArray = JSON.parse(data);
    for (var  i = 0; i < jsonArray.length; i++) {
        const {code,weight,tare, startAd,zeroAd,currentAd} = jsonArray[i];
        weights+=code+","+weight+"-";
    }

    compare(cplist,weights);
    return "OK"
}
function compare(cplist,weights){
    var bool = false;
    var arr = weights.substring(0,weights.length-1).split('-');
    var arr2 = cplist.substring(0,cplist.length-1).split('-');
      var totalW = 0;
    for( p in arr) {
        var arr1 = arr[p].split(',');
        var cpcode =  arr1[0];
        var cpweight = arr1[1];

        totalW+=Number(cpweight);
        for (p1 in arr2) {//循环库存
            var arr21 = arr2[p1].split(',');
            if(arr21!="") {
                var cpcode1 = arr21[0];
                var cpweight1 = arr21[1];//初始库存


                if (cpcode1 == cpcode) {
                    if (Number(cpweight1)<Number(cpweight)) {//初始库存小于现在库存 说明肯定放错商品了。大于的时候也可能错放，但是评估不了

                        $(".mb").hide();
                        $(".tipcon").text("商品位置放错，请恢复至原位置");
                        $(".alert_weigh_").show();
                        try {

                            if (isAndroid == true) {
                                Android.speechOutputForAndroid("商品位置放错，请恢复至原位置");
                            } else {
                                console.log("请在安卓设备访问！");
                            }
                        } catch (error) {

                        }
                        return false;

                    }

                }
            }
        }
    }

    if(Number(totalW)!=Number(nowWeight&&totalW<initWeight&&totalW>=0)){
        nowWeight = totalW;
        //说明重量有变化，有检出拿货了，加入到购物车，跳转到商品列表
        var url = basePath + "ea/sm/sajax_ea_addCartHg.jspa";
        $.ajax({
            type: "POST",
            url: url,
            async: true,
            dataType: "json",
            data: {
                weights: weights,
                cplist: cplist,
                posNum: posNum,
                staffID:staffID,
                hgcode:hgcode


            },
            success: function (data) {
                $(".shop").html("");
                number = 0;
                sqCartlist(posNum);
            },
            error: function (data) {
                //商品加入购物车失败
                alert("数据处理失败!");
                return;
            }
        });
    }
}


/**
 *
 * 直接关门
 */
function closeDoor(){
    var url = basePath + "ea/sm/sajax_ea_mmdx.jspa";
    $.ajax({
        type: "POST",
        url: url,
        async: false,
        dataType: "json",
        data: {
            hgcode:hgcode
        },
        success: function (data) {
            document.location.href = basePath+"page/ea/main/marketing/supermarket/container/mmdxsuc.jsp?posNum="+posNum;



        },
        error: function (data) {

        }
    });
}



