var clickname;
$(function () {
    //点击切换
    $(document).on("click", ".ul-tab li p", function () {
        state = $(this).val();
        $(".ul-tab li p").removeClass("active");
        $(this).addClass("active");
    });


    $(document).on("click", ".ul-tab2 li p", function () {
        $(".ul-con").empty();
        var idvla = $(this).parent().attr("id");
        clickname=idvla;
        var ulHtml = "";
        if (idvla == "gys") {
            ulHtml += "<li>订单号：" + $.getUrlParam("journalNum") + "<input type='hidden' id='state' name='state' value='01'/>";
            ulHtml += "<input type='hidden' id='journalNum' name='journalNum' value='" + $.getUrlParam("journalNum") + "'/>";
            ulHtml += "<input type='hidden' id='totalMoney' name='totalMoney' value='" + $.getUrlParam("totalMoney") + "'/></li>";
            ulHtml += "<li><label>供货商公司</label>";
            ulHtml += "<input type='text' class='size-01 inputChange' id='' value='" + $.getUrlParam("companyName") + "' readonly/></li>";
            ulHtml += "<li><label>采购商（客户）公司</label>";
            ulHtml += "<input type='hidden' id='comID' name='comID'/>";
            ulHtml += "<input type='text' name='comname' class='size-01 inputChange' id='cgsgs' autocomplete='off'/></li>";
            ulHtml += "<li><label>下单责任人</label>";
            ulHtml += "<input type='hidden' id='zzrstaffid' name='zzrstaffid'/>";
            ulHtml += "<input type='text' name='zzrstaffname' class='size-02 inputChange' id='zrr' autocomplete='off'/></li>";
        } else if (idvla == "cgy") {
            ulHtml += "<li>订单号：" + $.getUrlParam("journalNum") + "<input type='hidden' id='state' name='state' value='02'/></li>";
            ulHtml += "<input type='hidden' id='journalNum' name='journalNum' value='" + $.getUrlParam("journalNum") + "'/>";
            ulHtml += "<input type='hidden' id='totalMoney' name='totalMoney' value='" + $.getUrlParam("totalMoney") + "'/></li>";
            ulHtml += "<li><label>采购商公司</label>";
            ulHtml += "<input type='hidden' id='comID' name='comID'/>";
            ulHtml += "<input type='text' name='comname' class='size-01 inputChange' id='cgsgs' autocomplete='off'/></li>";
            ulHtml += "<li><label>采购公司财务</label>";
            ulHtml += "<input type='hidden' id='cwstaffid' name='cwstaffid'/>";
            ulHtml += "<input type='text' name='cwstaffname' class='size-02 inputChange' id='cw' autocomplete='off'/></li>";
            ulHtml += "<li><label>采购员</label>";
            ulHtml += "<input type='hidden' id='sgystaffid' name='sgystaffid'/>";
            ulHtml += "<input type='text' name='sgystaffname' class='size-02 inputChange' id='cg' autocomplete='off'/></li>";
        }
        $(".ul-con").append(ulHtml);
        $(".ul-con").find(".inputChange").each(function () {
            var liWidth = $(this).parent().width();
            var labelWidth = $(this).parent().find("label").width();
            $(this).width(liWidth - labelWidth - 15);
        });
        $(".ul-tab2 li p").removeClass("active");
        $(this).addClass("active");
    });

    $("#cgy").find("p").click();

    $(document).on("change", ".inputChange", function () {
        var filter = $.trim($(this).val());
        var inputid = $(this).attr("id");
        var cid;
        var inputclass = $(this).attr("class");
        var url = "";
        if (inputid == "zrr") {
            cid = $.getUrlParam("comID");
        } else {
            cid = $("#comID").val();
        }

        if (filter != null && filter != "") {
            if ($(".div-gs").length <= 0) {
                if (inputclass == "size-01 inputChange") {
                    url = "ea/sm/sajax_ea_getCom.jspa";
                    if (inputid != "zrr") {
                        $(".size-02").each(function () {
                            $(this).val("");
                            $(this).prevAll().val("");
                        });
                    }
                } else if (inputclass == "size-02 inputChange") {
                    if (inputid == "zrr" || ($("#comID").val() != null && $("#comID").val() != "")) {
                        url = "ea/sm/sajax_ea_getStaff.jspa";
                    } else {
                        $(this).val("");
                        return false;
                    }
                }
                $.ajax({
                    url: basePath + url,
                    type: "post",
                    async: false,
                    data: {
                        "valString": filter,
                        "comid": cid
                    },
                    datatype: "json",
                    success: function (data) {
                        var member = eval("(" + data + ")");
                        var t = member.objvalue;
                        if (t != null && t.length > 0) {
                            var ulHtml = "";
                            ulHtml += "<div class='div-gs'><ul class='" + inputid + "'>";
                            $.each(t, function (i, teach) {
                                ulHtml += "<li id='" + teach[0] + "'>" + teach[1] + "</li>";
                            });
                            ulHtml += "</ul></div>";
                            $("#" + inputid).after(ulHtml);
                            $(".div-gs").show();
                        }
                    }
                });
            }
        } else {
            $(".div-gs").remove();
        }

        $list = $(this).parent().find(".div-gs");

        $("li", $list).each(function () {
            $(this).hide();
            if ($(this).text().indexOf($.trim(filter)) >= 0) {
                $(this).show();
            }
        });
        return false;
    });


    $(document).on("keyup", ".inputChange", function () {
        $(this).change();
    });


    $(document).on("click", ".div-gs li", function () {
        var inputid = $(this).parent().attr("class");
        $("#" + inputid).val($(this).text());
        var inputclass = $("#" + inputid).attr("class");
        $("#" + inputid).prev().val($(this).attr("id"));
        $(".div-gs").remove();
    });
});

//确认
function confirm() {
    var falg=true;
    if (clickname == "gys") {
        if ($("#comID").val() == null || $("#comID").val() == ""|| $("#cgsgs").val() == null|| $("#cgsgs").val() == ""|| $("#zzrstaffid").val() == null|| $("#zzrstaffid").val() == ""|| $("#zrr").val() == null|| $("#zrr").val() == "") {
            falg=false;
        }
    } else if (clickname == "cgy") {
        if ($("#comID").val() == null|| $("#comID").val() == ""|| $("#cgsgs").val() == null|| $("#cgsgs").val() == ""|| $("#cwstaffid").val() == null|| $("#cwstaffid").val() == ""|| $("#cw").val() == null|| $("#cw").val() == ""|| $("#sgystaffid").val() == null|| $("#sgystaffid").val() == ""|| $("#cg").val() == null|| $("#cg").val() == "") {
            falg=false;
        }
    }else{
        falg=false;
    }
    if(falg){
        var formData = $("#tpForm").serialize();
        var ulp = basePath + "/ea/sm/sajax_ea_transferPayOrder.jspa";
        $.ajax({
            type: "GET",
            url: ulp,
            async: true,
            dataType: "json",
            data: formData,
            success: function (data) {
                console.log("现金支付成功");
                document.location.href = basePath + "ea/sm/ea_viewOrderDetail.jspa?journalNum=" + $.getUrlParam("journalNum") + "&paytype=转他人支付&posNum=" + $.getUrlParam("posNum");
            },
            error: function (data) {
                console.log("现金支付失败");
            }
        });
    }else{
        alert("提交失败");
        return falg;
    }
}

(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURI(r[2]);
        return null;
    };
})(jQuery);
