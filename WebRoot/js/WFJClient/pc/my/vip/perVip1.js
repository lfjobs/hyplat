$(function() {

    //行业
    $(".industryType").click(function(){
        $(".hyfl").show();
        $("#sel").text("请选择行业");
        getIndustry("");

    });

    //行业返回
    $(".hyback").click(function(){
        $(".hyfl").hide();
    });

    $(document).on("click",".hy-ul li",function() {
        $(".hy-ul .active").removeClass("active");
         $(this).addClass("active");

         var  industryID = $(this).attr("id");
         var industryName = $(this).text();

         $(".hy1 .industryId").val(industryID);
        $(".hy1 .industryName").val(industryName);

        $(".main").html("");
        $(".ul-m").html("");
        pageNumber = 0;
        pageCount = 0;
        var op = $(".searchtype").val();
        if(op=="1"){
            load();
        }else{
            loadpro();
        }


    })

    //查询子行业
    $(document).on("click",".hyfl li",function(){

        var codeID = $(this).attr("id");
        if(codeID!="") {
            $("#selid").val(codeID);
        }
        var codeValue = $(this).text();
        var codePID = $(this).attr("codepid-data");
        var codename = $(this).attr("typename-data");

        if($("#sel").text()=="请选择行业"){
            $("#sel").html("<span class='"+codePID+"' >"+codename+"</span>");
        }else {
            $("#sel").append("<span class='" + codePID + "' >/" + codename + "</span>");
            $(".industryName").val(codename);
        }

        getIndustry(codeID);
    });
    //按层级回退行业
    $(document).on("click","#sel span",function(){
        var codePID = $(this).attr("class");
        if(codePID!="") {
            $("#sel ." + codePID).nextAll().remove();
            $("#sel ." + codePID).remove();
        }else{
            $("#sel").text("请选择行业");
        }
        getIndustry(codePID);

    });

    load();
    $(window).scroll(function () {

        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度


        if(scroll>115){
            $(".sec-nav").addClass("nav");
        }else{
            $(".sec-nav").removeClass("nav");
        }

        var Top = $(".last").offset().top; //元素距离顶部距离


        if (Top - Height - scroll <= 50) {
            if (pageNumber < pageCount) {
                var op = $(".searchtype").val();
                if(op=="1"){
                    load();
                }else{
                    loadpro();
                }



            }
        }

    })
    $(document).on("click",".ul-m li,.p-fpro li",function(event){
        event.stopPropagation();
        var ppid = $(this).attr("id");
        var goodsid = $(this).find(".goodsid").text();
        var companyId = $(this).find(".companyId").text();
        var ccompanyId = $(this).find(".ccompanyId").text();
        document.location.href=basePath+"/ea/wfjshop/ea_doodsDetail.jspa?ppid="+ppid+"&goodsid="+goodsid+"&companyId="+companyId+"&ccompanyId="+ccompanyId;
    });


    $(".search").click(function(){
        $(".main").html("");
        $(".ul-m").html("");
        pageNumber = 0;
        pageCount = 0;
        var op = $(".searchtype").val();
        if(op=="1"){
            load();
        }else{
            loadpro();
        }

    });


});
var array = new Array();
var arraypro = new Array();

function load() {
    pageNumber = pageNumber + 1;
    var s = $(".sousuo input").val();
    var industryId = $(".industryId").val();

    var ulp = basePath
        + "ea/productslaunch/sajax_ea_getVipList1.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            pageNumber: pageNumber,
            parameter:s,
            industryId:industryId
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var pageForm = member.pageForm;//公司列表
            if (pageForm == null) {
                $(".sresult").show();
                $(".main").hide();
                return false;

            }
            $(".sresult").hide();
            $(".main").show();
            var clist = member.clist;//主产品
            var cxlist = member.cxlist;//促销品
            var html = new Array();


            pageNumber = pageForm.pageNumber;
            pageCount = pageForm.pageCount;
            $(".last").removeClass("last");

            var arrayList = new Array();
            if (pageForm.list != null && pageForm.list.length > 0) {
                for (var j = 0; j < pageForm.list.length; j++) {
                    var obj = pageForm.list[j];
                    var companyID = obj[0];


                    if ($("div#" + obj[1]).length == 0) {
                        if (j == pageForm.list.length - 1) {
                            html.push("<div id='" + companyID + "' class='mm last' onclick='prolist(\"" + companyID + "\")'>");
                        } else {
                            html.push("<div id='" + companyID + "' class='mm' onclick='prolist(\"" + companyID + "\")'>");

                        }


                    html.push("<div class='left'>");
                    html.push("<div class='div1'>");
                    html.push("<img src = '" + basePath + obj[3] + "'    onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>");
                    html.push("</div>");
                    var cc = "购业务会员赠送产品";
                    var img = "hy.png";
                    if (companyID != "company201009046vxdyzy4wg0000000025") {
                        cc = "购物赠送业务会员";
                        img = "gw.png";
                    }
                    html.push("<div  class='div2'> <div class='div3'></div><div class='div4'>" + cc + "</div><img src='" + basePath + "images/WFJClient/pc/my/" + img + "' ></div>");
                    html.push("</div>");
                    html.push("<div class='right'>");
                    html.push("<div class='com'>" + obj[1] + "</div>");
                    html.push("<p class='p-address'>" + obj[4] + "</p>");
                    html.push("<p class='p-hy'>" + obj[5] + "</p>");


                    html.push("<p class='p-totalSales'>已售" + format(obj[6]) + "</p>");
                    html.push("<div class='p-pro'>");
                    html.push("<ul class='p-main'>");
                    $.each(clist, function (index, value) {
                        if (index == companyID) {
                            for (var i = 0; i < value.length; i++) {
                                var objc = value[i];
                                var zppid = objc[1];

                                html.push("<li>");
                                html.push("<ul class='p-fpro'>");

                                html.push("<li id='" + objc[1] + "'>");
                                html.push("<span style='display:none;' class='goodsid'>" + objc[5] + "</span>");
                                html.push("<span style='display:none;' class='companyId'>" + objc[6] + "</span>")
                                html.push("<span style='display:none;' class='ccompanyId'>" + objc[8] + "</span>");
                                html.push("<div class='pro-img'><img src = '" + basePath + objc[3] + "'    onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"' /></div>");
                                html.push("</li>");
                                html.push("<li>" + objc[0] + "</li>");
                                var ccx = "";
                                html.push("<li>");
                                for (var k = 0; k < cxlist.length; k++) {

                                    var objcx = cxlist[k];
                                    var ppid = objcx[3];
                                    if (zppid == ppid) {

                                        ccx += objcx[0];
                                        ccx += "、";
                                    }

                                }
                                if (ccx == "") {
                                    html.push("&nbsp;");
                                } else {
                                    ccx = ccx.substring(0, ccx.length - 1);
                                    html.push("<span>赠送</span>");
                                    html.push(ccx);

                                }

                                html.push("</li>");
                                html.push("<li>￥" + objc[2] + "</li>");
                                html.push("</ul>");
                                html.push("</li>");


                            }
                        }


                    });


                    html.push("</ul>");

                    html.push("</div>");
                    html.push("</div>");
                    html.push("</div>");
                }
                }

            }


         $(".main").append(html.join(""));




        },
        error: function (data) {
            console.log("失败");
        }
    });


}

function loadpro() {
    pageNumber = pageNumber + 1;

    var s = $(".sousuo input").val();
    var industryId = $(".industryId").val();
    var ulp = basePath
        + "ea/productslaunch/sajax_ea_getVipList2.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            pageNumber: pageNumber,
            industryId,industryId,
            parameter:s
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var pageForm = member.pageForm;
            if (pageForm == null) {

                $(".sresult").show();
                $(".main").hide();
                return false;

            }
            $(".sresult").hide();
            $(".main").show();

            var cxlist = member.cxlist;
            var html = new Array();


            pageNumber = pageForm.pageNumber;
            pageCount = pageForm.pageCount;
            $(".last").removeClass("last");
            var obj = "";
            var arrayList = new Array();
            if (pageForm.list != null && pageForm.list.length > 0) {


                for (var j = 0; j < pageForm.list.length; j++) {
                    var obj = pageForm.list[j];
                    var zppid = obj[1];
                    if ($("li#" + obj[1]).length == 0) {
                        if (j == pageForm.list.length - 1) {

                            html.push("<li class='last' id='" + obj[1] + "'>");
                        } else {
                            html.push("<li id='" + obj[1] + "'>");
                        }


                        html.push("<span style='display:none;' class='goodsid'>" + obj[5] + "</span>");
                        html.push("<span style='display:none;' class='companyId'>" + obj[6] + "</span>")
                        html.push("<span style='display:none;' class='ccompanyId'>" + obj[8] + "</span>");

                        html.push("<div class='ul-l'>");
                        html.push("<div class='left-div'>");
                        html.push("<div class='img-ul'>");
                        html.push("<img src = '" + basePath + obj[3] + "'    onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>");
                        html.push("</div>");
                        html.push("</div>");
                        html.push("<div class='right-div'>");
                        html.push("<p class='p-1'>" + obj[0] + "</p>");
                        html.push("<p class='p-2'></p>");
                        var ccx = "";
                        for (var k = 0; k < cxlist.length; k++) {

                            var objcx = cxlist[k];
                            var ppid = objcx[3];
                            if (zppid == ppid) {

                                ccx += objcx[0];
                                ccx += "、";
                            }

                        }
                        if (ccx == "") {
                            html.push("&nbsp;");
                        } else {
                            ccx = ccx.substring(0, ccx.length - 1);
                            html.push("<p class='p-3'><span>赠送</span>" + ccx + "</p>");
                        }

                        html.push("<p class='p-4'>￥" + obj[2] + "</p>");
                        html.push("<p class='p-5'>已售：" + obj[9] + "</p>");
                        html.push("</div>");
                        html.push("</div>");
                        html.push("</li>");

                    }
                }
                $(".ul-m").append(html.join(""));
            }


        },
        error: function (data) {
            console.log("失败");
        }
    });


}




//获取行业
function getIndustry(codePID) {
    $.ajax({
        url: basePath + "/ea/qyrz/sajax_ea_getIndustry.jspa",
        type: "POST",
        async: false,
        dataType: "json",
        data: {
            codePID: codePID
        },
        success: function (data) {
            var member = eval("(" + data + ")");
            var industryList = member.industryList;
            if (industryList == null || industryList.length == 0) {
                $(".hyfl").hide();
                $(".industryId").val($("#selid").val());
                $(".hy1 .active").removeClass("active");
                $(".tj").after("<li class='active' id='"+$("#selid").val()+"'>"+  $(".industryName").val()+"</li>");
              //  $('.hy-ul li:eq(-2)').remove();
                $(".main").html("");
                $(".ul-m").html("");
                pageNumber = 0;
                pageCount = 0;
                var op = $(".searchtype").val();
                if(op=="1"){
                    load();
                }else{
                    loadpro();
                }
            }
            var html = new Array();
            var obj;
            for (var i = 0; i < industryList.length; i++) {
                obj = industryList[i];
                html.push("<li class='clearfix' id='" + obj.typeId + "' codepid-data='" + obj.typePID + "' typeName-data='" + obj.typeName + "'>");
                html.push("<p>" + obj.typeNum+obj.typeName + "</p>");
                html.push("<p><img src='" + basePath + "/images/scMobile/qyrz/a.png'/></p>");
                html.push("</li>");

            }
            $(".hy").html(html.join(""));
        },
        error: function (data) {
            console.log("获取行业失败");
        }
    });

}

function prolist(companyID){
    document.location.href = basePath+"page/WFJClient/pc/my/vip/prolist.jsp?companyId="+companyID;
}
function format(num){
    var n = Math.floor(num);
    if(n>=10000){
        n = Math.floor(n/10000)+"万+";
    }
    return n;
}

