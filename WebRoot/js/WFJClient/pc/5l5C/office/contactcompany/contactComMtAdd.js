var isupdate = false;
$(function () {
    if (ccomid != null && ccomid != "") {

    } else {
        isupdate = true;
    }

    //加载公司性质、规模数据
    selectList();

    //添加或修改公司名称判断
    $("input#companyName").blur(function () {
        var onlyCompany = $.trim(this.value);
        if (ccompanyID == '' && onlyCompany != '') {
            prompt(onlyCompany);
        } else {
            if (onlyCompany != $.trim(companyName) && onlyCompany != '') {
                prompt(onlyCompany);
            }
        }
    });

    //电话号码验证
    $("#companyTel,#responsibleTel").blur(function () {
        ver_phone($(this));
    });

    /*------------公司性质------------*/
    //公司性质选择框显示
    $(".comPro-div").click(function () {
        $(".div-comtype").show();
    });

    //公司性质传入选项
    $(document).on("click", ".div-comtype li", function () {
        $(this).parents(".div-comtype").hide();
        $(this).siblings().removeClass("active");
        $(this).addClass("active");
        $("#comPro").val($.trim($(this).text()));
    });
    /*-----------------end-----------------*/

    /*----------------公司规模--------------*/
    //公司规模选择框显示
    $(".comScale-div").click(function () {
        $(".div-scalelist").show();
    });

    //公司规模传入选项
    $(document).on("click", ".div-scalelist li", function () {
        $(this).parents(".div-scalelist").hide();
        $(this).siblings().removeClass("active");
        $(this).addClass("active");
        $("#comScale").val($.trim($(this).text()));
    });
    /*-----------------end-----------------*/

    /*----------------行业分类---------------*/
    // 	显示
    $(document).on("click", ".industryType-div", function () {
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
    //传入选项
    $(document).on("click", ".div-hangye .div-right>ul li", function () {
        $(this).addClass("active");
        $(this).siblings().removeClass("active");
        $("#industryType").val($(this).find(".pvalue").text());
        $("#industryId").val($(this).attr("id"));
        /*$(".hangye2 #tradeCode").val($(this).find(".codeSn").text() + $(this).find(".codeValue").text());*/
        $(".div-hangye").hide();
        $(".hyfl").empty();
    });
    /*-----------------end-----------------*/

    //显示地址选择框
    $("#companyAddr").click(function () {
        $(".div-address").show();
    });

    //切换加载地址数据
    $(".address-div").click(function () {
        var type;
        var sxqid;
        if($(this).attr("title")=="省"){
            type="s";
        }else if($(this).attr("title")=="县"&&$("#s").val().length>0){
            type="x";
            sxqid=$("#sid").val();
        }else if($(this).attr("title")=="区"&&$("#x").val().length>0){
            type="q";
            sxqid=$("#xid").val();
        }
        address(type,sxqid);/*
        var id=$(this).find(".c").val();
        $("li #"+id).addClass("active");*/
    });

    //地址传入选项
    $(document).on("click", ".div-sxq li", function () {
        $(this).siblings().removeClass("active");
        $(this).addClass("active");
        var id=$(this).parents().find("#type-input").val();
        $("#"+id).val($.trim($(this).text()));
        $("#"+id+"id").val($.trim($(this).attr("id")));
        $("#address").val($.trim($(this).attr("id")));
        $(this).parents(".div-sxq").hide();
    });
    
    $(".isok").click(function () {
        var isNoll=false;
        $(".notnull").each(function () {
            if ($(this).val().length <= 0) {
                isNoll = true;
            }
        });
        if (isNoll) {
            $(".titlep").text("地址信息不完整");
            $(".div-tingyong").show();
            return;
        } else {
            $("#companyAddr").val($("#s").val()+$("#x").val()+$("#q").val()+$("#xx").val());
            $(".div-address").hide();
        }
    });

    //关闭提示框
    $(".close-confirm").click(function () {
        $(".div-tingyong").hide();
        $(".loading").hide();
    });

    $(".div-close").click(function () {
        $(".div-data").hide();
    });

    //保存
    $(".submitAudit").click(function () {
        toSave("form");
    });

});

//公司名称判断是否存在
function prompt(onlyCompany) {
    var url = basePath
        + "ea/contactcompany/sajax_ea_isContactConnectionMt.jspa?"
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        data: {
            companyID: companyID,
            onlyCompany: encodeURI(onlyCompany),
            date: new Date()
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var result = member.c;
            if (result != 0) {
                $(".titlep").text(onlyCompany + "已存在");
                $(".div-tingyong").show();
                $("input#companyName").val("");
            }
        }, error: function (data) {
            alert("读取失败");
        }
    });
}

//电话号码验证
function ver_phone(tel) {
    var Sreg = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\d{8})$/;
    /*var Tele=/^((0\d{2,3})-?)(\d{7,8})(-(\d{3,}))?$/;*/
    console.log(tel.val());
    console.log(Sreg.test(tel.val()));
    /*console.log(Tele.test(tel.val()))*/
    if (tel.val() != "" && (!Sreg.test(tel.val())/*||!Tele.test(tel.val())*/)) {
        $(".titlep").text("请输入正确格式电话号！");
        $(".div-tingyong").show();
        tel.val("");
    }
};

//JS file 图片 即选即得 显示
//创建一个FileReader对象
var reader = new FileReader();

//图片上传获取缩略图
function f_change(file) {
    var input = document.getElementById('imgSdf');
    var name = file.files[0].name;
    //读取图片数据
    reader.onload = function (e) {
        var data = e.target.result;
        //加载图片获取图片真实宽度和高度
        var image = new Image();
        input.width = "100";
        input.height = "100";
        input.src = data;
        image.onload = function () {

            //var width = image.width;
            //var height = image.height;
            //alert(width+'======'+height+"====="+f.size);
            if (file.files[0].size > 1024 * 1024) {
                $(".titlep").text("公司logo的大小不能大于1024KB!请调整图片大小后上传!");
                $(".div-tingyong").show();
                $("#imgSdf").val("");
            }/* else if (image.width != "45" || image.height != "45") {
                $(".titlep").text("公司logo的尺寸应为45*45!请调整图片大小后上传!");
                $(".div-tingyong").show();
                image.src = "";
                $("#imgSdf").val("");
            }*/
        };
    };
    reader.readAsDataURL(file.files[0]);
}

//公司性质、规模
function selectList() {
    $.ajax({
        url: basePath + "/ea/contactcompany/sajax_ea_toSaveMtJsp.jspa",
        type: "get",
        dataType: "json",
        aysnc: false,
        success: function (data) {
            var m = eval("(" + data + ")");
            var comtypelist = m.comtypelist;
            var scalelist = m.scalelist;
            if (comtypelist != null) {
                var comtypeHtml = new Array();
                for (var i = 0; i < comtypelist.length; i++) {
                    comtypeHtml.push("<li>" + comtypelist[i].goodsName + "</li>");
                }
                $(".div-comtype").find("ul").append(comtypeHtml);
            }
            if (scalelist != null) {
                var scalelistHtml = new Array();
                for (var i = 0; i < scalelist.length; i++) {
                    scalelistHtml.push("<li>" + scalelist[i].goodsName + "</li>");
                }
                $(".div-scalelist").find("ul").append(scalelistHtml);

            }
        }, error: function (data) {
            console.log(data)
        }
    });
}

//行业类别数据查询
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
            var tii = ti + 1;
            var str = [];
            if (ti == 1) {
                str.push("<ul class='ul-left'>");
            } else if (ti == 2) {
                str.push("<div class='div-right hyfl-right'> <ul class='clearfix ul-02'>");
            }/* else if (ti == 3) {
                str.push("<ul class='clearfix ul-03'>");
            } else if (ti == 4) {
                str.push("<ul class='clearfix ul-04'>");
            }*/
            if (pvalue != null && pvalue != "") {
                pvalue = pvalue + "\>";
            }
            for (var i = 0; i < oList.length; i++) {
                str.push("<li id='" + oList[i].codeID + "' onclick='getProductType(" + "\"" + oList[i].codeID + "\",\"" + pvalue + oList[i].codeSn + oList[i].codeValue + "\"," + tii + "," + type + ")'>");
                str.push("<span class='codeValue'>" + oList[i].codeValue + "</span>");
                str.push("<span class='codeSn' style='display: none'>" + oList[i].codeSn + "</span>");
                str.push("<span class='pvalue' style='display: none'>" + pvalue + oList[i].codeSn + oList[i].codeValue + "</span>");
                str.push("</li>");
            }
            str.push("</ul></div>");
            $(".hyfl").append(str.join(""));
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

/**
 *省、市、区地址联动
 * type s:省  x：县  q：区
 * sxqid 选中地址id
 */
function address(type,sxqid) {
    /*if (type!="stop"){*/
        $(".div-sxq").find("ul").empty();
    /*}*/
    var url = "";
    var showParam=true;
    if (type == "s") {
        url = "ea/newpcend/sajax_ea_ajaxSelDistrictCity.jspa";
    } else if (type == "x") {
        url = "ea/newpcend/sajax_ea_ajaxSelDistrictByID.jspa";
    } else {
        showParam=false;
        url = "ea/newpcend/sajax_ea_ajaxSelDistrictByID.jspa";
    }
    $.ajax({
        url: basePath + url,
        type: "post",
        async: true,
        data: {
            "sdistrict.districtID": sxqid,
            "showParam": showParam
        },
        dataType: "json",
        success: function (data) {
            var subMember = eval("(" + data + ")");
            var country;
            if (type=="s"){
                country=subMember.city.districtCity
            }else {
                country=subMember.district.country;
            }
            var comtypeHtml = [];
            if (country != null && country.length > 0) {
                $("#type-input").val(type);
                $(country).each(function () {
                    comtypeHtml.push("<li id='" + $(this)[0] + "'>" + $(this)[1] + "</li>");
                });
                $(".div-sxq").find("ul").append(comtypeHtml);
                $(".div-sxq").show();
            }
        },
        error: function () {
            alert("地址获取失败！");
        }
    });
}

//保存
function toSave(formID){
    $(".loading").show();
    $('form#' + formID).trigger("blur");
    $("input#responsibleTel").trigger("blur");
    $("input#companyTel").trigger("blur");
    var isNoll = false;
    $(".isNotnull", 'form#' + formID).each(function () {
        if ($(this).val().length <= 0) {
            $(this).parent().find("label").attr("style","color:#f74c32");
            isNoll = true;
        }
    });
    if (isNoll) {
        $(".titlep").text("请完善所有信息");
        $(".div-tingyong").show();
        return;
    } else {
        var url = basePath + "/ea/contactcompany/sajax_ea_saveContactCompany.jspa?date=" + new Date() + "&companyID=" + companyID;
        var formData = new FormData($("form#" + formID)[0]);
        $.ajax({
            url: encodeURI(url),
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                $(".loading").hide();
                var member = eval("(" + data + ")");
                var ccompanyID = member.ccompanyID;
                var flag = member.flag;
                /*$(".titlep").text("保存成功！");
                $(".div-tingyong").show();*/
                window.location.href = basePath + "/page/WFJClient/pc/5l5C/office/contactcompany/contactComMtList.jsp?companyID=" + ccompanyID + "&flag=" + flag;
            },
            error: function (data) {
                $(".titlep").text("保存失败！");
                $(".div-tingyong").show();
            }
        });
    }
}