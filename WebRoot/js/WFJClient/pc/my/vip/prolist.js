$(function() {
    shopInf();

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
                load();
            }
        }

    })
    $(document).on("click",".ul-m li",function(event){
        var ppid = $(this).attr("id");
        var goodsid = $(this).find(".goodsid").text();
        var companyId = $(this).find(".companyId").text();
        var ccompanyId = $(this).find(".ccompanyId").text();
        document.location.href=basePath+"/ea/wfjshop/ea_doodsDetail.jspa?ppid="+ppid+"&goodsid="+goodsid+"&companyId="+companyId+"&ccompanyId="+ccompanyId;
    });

    $('.sousuo input').on('input', function() {
        pageNumber = 0;
        pageCount = 0;
        console.log($('.sousuo input').val());
        $('.ul-m li').not('.sresult').remove();
        load();
    });

    //关注取消关注
    $("#id-gz").click(function () {
        var ulp = basePath
            + "ea/productslaunch/sajax_ea_addJoinFans.jspa";
        $.ajax({
            type: "GET",
            url: ulp,
            async: true,
            dataType: "json",
            data: {
                companyId: companyId
            },
            success: function (data) {
                var member = eval('(' + data + ')');
                var login = member.login;
                var  result = member.result;
                if(login=="login"){
                    //登陆
                    document.location.href = basePath+"page/WFJClient/pc/pc_login.jsp";

                }else{
                    var  spanfs = $(".span-fs").text();

                    if(result=="0"){
                        $("#gz").attr("src",basePath+"images/WFJClient/pc/my/gzt.png");//关注按钮
                        $(".span-gz").text("关注");
                        $("#id-gz").addClass("gz-div");
                        $("#id-gz").removeClass("ygz-div");

                        $(".span-fs").text(Number(spanfs)-1);


                    }else{
                        $("#gz").attr("src",basePath+"images/WFJClient/pc/my/ygz.png");//已关注按钮
                        $(".span-gz").text("已关注");
                        $("#id-gz").addClass("ygz-div");
                        $("#id-gz").removeClass("gz-div");

                        $(".span-fs").text(Number(spanfs)+1);


                    }
                }




            }, error: function (data) {
            }
        })

    })

});
var array = new Array();
var arraypro = new Array();
function shopInf(){

    var ulp = basePath
        + "ea/productslaunch/sajax_ea_getShopInfo.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            companyId: companyId
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var contactCompany = member.contactCompany;
            var g = member.g;
            var c = member.c;
            $("#imglogo").attr("src",basePath+contactCompany.logoPath);
            $(".p-com").text(contactCompany.companyName);
            $(".p-hy").text(contactCompany.industryType);
            $(".span-fs").text(c);

            if(c=="0"){
                $("#gz").attr("src",basePath+"images/WFJClient/pc/my/gzt.png");//关注按钮
                $(".span-gz").text("关注");
                $("#id-gz").addClass("gz-div");
                $("#id-gz").removeClass("ygz-div");

            }else{
                $("#gz").attr("src",basePath+"images/WFJClient/pc/my/ygz.png");//已关注按钮
                $(".span-gz").text("已关注");
                $("#id-gz").addClass("ygz-div");
                $("#id-gz").removeClass("gz-div");


            }



      }, error: function (data) {
        }
    })


}
function load() {
    pageNumber = pageNumber + 1;

var parameter = $(".sousuo input").val();
    var ulp = basePath
        + "ea/productslaunch/sajax_ea_getVipListCompany.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            pageNumber: pageNumber,
            companyId:companyId,
            "productPackaging.goodsName":parameter
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var pageForm = member.pageForm;
            if (pageForm == null) {

                $(".sresult").show();
                return false;

            }
            $(".sresult").hide();

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




