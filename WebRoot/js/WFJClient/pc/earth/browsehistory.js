var pageNumber1 = 0;var pageCount1 = 0;
var pageNumber2 = 0;var pageCount2 = 0;
var pageNumber3 = 0;var pageCount3 = 0;
var pageNumber4 = 0;var pageCount4 = 0;
$(function(){


    load1();
   $(".section-head .ul-head li").click(function(){
       $(".section-head .active").removeClass("active");
       $(this).addClass("active");
       $(".content .actived").removeClass("actived");
       $("."+$(this).attr("id")).addClass("actived");

       var id=$(this).attr("id");
       if(id=="app-div"){
             load5();
       }else if(id=="good-div") {
           if(pageNumber4==0||pageNumber4<pageCount4){

           load4();
           }

       }else if(id=="sp-div") {
           if(pageNumber3==0||pageNumber3<pageCount3) {
               load3();
           }

       }else if(id=="zx-div") {
           if(pageNumber2==0||pageNumber2<pageCount2) {
               load2();
           }

       }else if(id=="sj-div") {
           if(pageNumber1==0||pageNumber1<pageCount1) {
               load1();
           }

       }
   });


    $(".content").scroll(function () {

        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度

        var id= $(".section-head .active").attr("id");
        if(id=="good-div") {

            var Top = $(".last4").offset().top; //元素距离顶部距离

            if (Top - Height - scroll <= 20) {
                if (pageNumber4 < pageCount4) {
                    load4();
                }
            }


        }else if(id=="sp-div") {
            var Top = $(".last3").offset().top; //元素距离顶部距离

            if (Top - Height - scroll <= 20) {
                if (pageNumber3 < pageCount3) {
                    load3();
                }
            }


        }else if(id=="zx-div") {
            var Top = $(".last2").offset().top; //元素距离顶部距离

            if (Top - Height - scroll <= 20) {
                if (pageNumber2 < pageCount2) {
                    load2();
                }
            }


        }else if(id=="sj-div") {
            var Top = $(".last1").offset().top; //元素距离顶部距离

            if (Top - Height - scroll <= 20) {
                if (pageNumber1 < pageCount1) {
                    load1();
                }
            }


        }



    })




    //查看商家
    $(document).on("click", ".sj-div .ul li", function () {
        var ccompanyId = $(this).attr("id");

          document.location.href = basePath+"ea/industry/ea_getCompanyHome.jspa?ccompanyId="+ccompanyId+"&industryType=&etype=&sc=web"
    });

    // 新闻详情
    $(document).on("click",".zx-div .ul li",function() {
        var ppid = $(this).attr("id");
        var ccompanyId = $(this).attr("data-cc");
        document.location.href = basePath + "/ea/industry/ea_informationDetails.jspa?ppId="+ppid+"&ccompanyId="+ccompanyId+"&type=time&miniSystemJudge=03";

       });
// 视频查看
    $(document).on("click",".sp-div .ul li",function() {
        var videoID = $(this).attr("id");
        window.location.href  = basePath + "ea/dsp/ea_getVideoViewListPage.jspa?videoID="+videoID;
    });


    // 商品详情
    $(document).on("click",".good-div .ul li",function() {
        var obj = $(this);
        var parms = new Array();
        parms.push("ppid=" + $(obj).find("#ppid").val());
        parms.push("&goodsid=" + $(obj).find("#goodsid").val());
        parms.push("&companyId=" + $(obj).find("#companyid").val());
        parms.push("&ccompanyId=" + $(obj).find("#ccompanyid").val());
        window.location.href  = basePath+ "ea/wfjshop/ea_doodsDetail.jspa?" + parms.join("");
      });
    });


//商家
function load1() {
    if(pageNumber1==0){
        $(".sj-div .ul li").remove();
    }
    pageNumber1 = pageNumber1 + 1;

    var ulp = basePath
        + "/ea/earth/sajax_ea_getBrowseCompanyList.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            pageNumber: pageNumber1,
            ajax:"ajax"

        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var pageForm = member.pageForm;
            var html = new Array();

            if (pageForm == null) {
                return false;

            }
            pageNumber1= pageForm.pageNumber;
            pageCount1 = pageForm.pageCount;
            $(".last1").removeClass("last1");
            var obj = "";


                for (var i = 0; i < pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    if ($("#" + obj[2]).length == 0) {
                        if (i == pageForm.list.length - 1) {

                            html.push("<li class='clearfix last1' id='" + obj[2]+ "'>");
                        } else {
                            html.push("<li class='clearfix' id='" + obj[2] + "'>");
                        }
                        html.push("<div class='left-div'>");
                        html.push("<img  src='" + basePath + obj[0]+"' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>");
                        html.push("</div>");
                        html.push("<div class='right-div'>");
                        html.push("<p>");

                        if(obj[3]==""||obj[3]==null){
                            html.push("<span style='background:#fff;'>");
                        }else{
                            html.push("<span>");
                        }

                        html.push(obj[3]);
                        html.push("</span>");
                        html.push("</p>");
                        html.push("<p>"+obj[1]+"</p>");
                        html.push(" </div>");

                        html.push("</li>");
                    }

                }


            $(".sj-div .ul").append(html.join(""));


        },
        error: function (data) {
            console.log("失败");
        }
    });


}

//资讯
function load2() {
    if(pageNumber2==0){
        $(".zx-div .ul li").remove();
    }
    pageNumber2 = pageNumber2 + 1;

    var ulp = basePath
        + "/ea/earth/sajax_ea_getBrowseNewsList.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            pageNumber: pageNumber2
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var pageForm = member.pageForm;
            var html = new Array();

            if (pageForm == null) {
                return false;

            }
            pageNumber2= pageForm.pageNumber;
            pageCount2 = pageForm.pageCount;
            $(".last2").removeClass("last2");
            var obj = "";


            for (var i = 0; i < pageForm.list.length; i++) {
                obj = pageForm.list[i];
                if ($("#" + obj[3]).length == 0) {
                    if (i == pageForm.list.length - 1) {

                        html.push("<li class='last2' id='" + obj[3]+ "' data-cc='"+obj[4]+"'>");
                    } else {
                        html.push("<li class='' id='" + obj[3] + "' data-cc='"+obj[4]+"'>");
                    }
                    html.push("<div class='left-div'>");
                    html.push("<img  src='" + basePath + obj[2]+"' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>");
                    html.push("</div>");
                    html.push("<div class='right-div'>");
                    html.push("<p>");
                    html.push(obj[0]);
                    html.push("</p>");
                    html.push("<p>");
                    html.push("<span>")
                    html.push(obj[5]);
                    html.push("</span>")
                    html.push("<span>")
                    html.push(obj[1]);
                    html.push("</span>")
                    html.push("</p>");
                    html.push(" </div>");

                    html.push("</li>");
                }

            }


            $(".zx-div .ul").append(html.join(""));


        },
        error: function (data) {
            console.log("失败");
        }
    });


}


//视频
function load3() {
    if(pageNumber3==0){
        $(".sp-div .ul li").remove();
    }
    pageNumber3 = pageNumber3 + 1;

    var ulp = basePath
        + "/ea/earth/sajax_ea_getBrowseVideoList.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            pageNumber: pageNumber3
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var pageForm = member.pageForm;
            var html = new Array();

            if (pageForm == null) {
                return false;

            }
            pageNumber3= pageForm.pageNumber;
            pageCount3 = pageForm.pageCount;
            $(".last3").removeClass("last3");
            var obj = "";


            for (var i = 0; i < pageForm.list.length; i++) {
                obj = pageForm.list[i];
                if ($("#" + obj[0]).length == 0) {
                    if (i == pageForm.list.length - 1) {

                        html.push("<li class='last3' id='" + obj[0]+ "'>");
                    } else {
                        html.push("<li  id='" + obj[2] + "'>");
                    }


                    html.push("<div class='left-div'>");
                    html.push("<img  class='zt' src='" + basePath + obj[3]+"' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>");
                    html.push("<div class='zan'>");
                    html.push("<img  src='" + basePath +"images/WFJClient/pc/earth/like.png'>");
                    html.push(formatNum(obj[4]));
                    html.push("</div>");
                    html.push("</div>");
                    html.push("</li>");
                }

            }


            $(".sp-div .ul").append(html.join(""));


        },
        error: function (data) {
            console.log("失败");
        }
    });


}


//商品
function load4() {
    if(pageNumber4==0){
        $(".good-div .ul li").remove();
    }
    pageNumber4 = pageNumber4 + 1;

    var ulp = basePath
        + "/ea/earth/sajax_ea_getBrowseGoodsList.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            pageNumber: pageNumber4
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var pageForm = member.pageForm;
            var html = new Array();

            if (pageForm == null) {
                return false;

            }
            pageNumber4= pageForm.pageNumber;
            pageCount4 = pageForm.pageCount;
            $(".last4").removeClass("last4");
            var obj = "";

            for (var i = 0; i < pageForm.list.length; i++) {
                obj = pageForm.list[i];
                if ($("#" + obj[1]).length == 0) {
                    if (i == pageForm.list.length - 1) {

                        html.push("<li class='last4' id='" + obj[1]+ "'>");
                    } else {
                        html.push("<li id='" + obj[1] + "'>");
                    }

                    html.push('<input type="hidden" value="' + obj[1] + '" id="ppid"/>');
                    html.push('<input type="hidden" value="' + obj[3] + '" id="goodsid"/>');
                    html.push('<input type="hidden" value="' + obj[4] + '" id="companyid"/>');
                    html.push('<input type="hidden" value="' + obj[5] + '" id="ccompanyid"/>');
                    html.push("<div class='left-div'>");
                    html.push("<img  class='zt'  src='" + basePath + obj[2]+"' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>");
                    html.push("<div class='title'>");
                    html.push("<p>");
                    html.push(obj[0]);
                    html.push("</p>");
                    html.push("<p>");
                    html.push("￥"+obj[6]);
                    html.push("</p>");
                    html.push("</div>");
                    html.push("</div>");
                    html.push("</li>");
                }

            }


            $(".good-div .ul").append(html.join(""));


        },
        error: function (data) {
            console.log("失败");
        }
    });


}


//应用
function load5() {

   $(".app-div .ul li").remove();


    var ulp = basePath
        + "/ea/earth/sajax_ea_getBrowseAppList.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        success: function (data) {
            var member = eval('(' + data + ')');
            var applist = member.list;
            var html = new Array();


            var obj = "";


            for (var i = 0; i < applist.length; i++) {
                obj = applist[i];

                    html.push("<li onclick='detail(\""+obj.appName+"\")'>");

                    html.push("<div class='left-div'>");
                   if(obj.appName=="商家") {
                       html.push("<img class='zt'  src='" + basePath + "images/home/ico-6.png'>");
                   }else if(obj.appName=="资讯") {
                       html.push("<img class='zt'  src='" + basePath + "images/home/ico-3.png'>");
                   }else if(obj.appName=="视频") {
                       html.push("<img class='zt'  src='" + basePath + "images/ea/collage/phl/navigation_10.gif'>");
                   }else if(obj.appName=="购物") {

                       html.push("<img class='zt'  src='" + basePath + "images/home/ico-4.png'>");
                   }else if(obj.appName=="超市") {

                       html.push("<img class='zt'  src='" + basePath + "images/home/ico-9.png'>");//
                   }else if(obj.appName=="周边") {

                       html.push("<img class='zt'  src='" + basePath + "images/home/ico-12.png'>");//
                   }else if(obj.appName=="约车训练") {

                       html.push("<img class='zt'  src='" + basePath + "images/home/yc1.png'>");//
                   }else if(obj.appName=="市场") {

                       html.push("<img class='zt'  src='" + basePath + "images/home/phl.png'>");//
                   }else if(obj.appName=="招聘") {

                       html.push("<img class='zt'  src='" + basePath + "images/ea/bids/fenlei_05.png'>");//
                   }else if(obj.appName=="招商") {

                       html.push("<img class='zt'  src='" + basePath + "images/ea/bids/zhaoshang.png'>");//
                   }else if(obj.appName=="招标") {

                       html.push("<img class='zt'  src='" + basePath + "images/ea/bids/fenlei_03.png'>");//
                   }else if(obj.appName=="e路快车") {

                       html.push("<img class='zt'  src='" + basePath + "images/WFJClient/pc/newimg/index_29.png'>");//
                   }else if(obj.appName=="慈善") {

                       html.push("<img class='zt'  src='" + basePath + "images/WFJClient/pc/earth/loveyy.jpg'>");//
                   }else if(obj.appName=="会员") {

                       html.push("<img class='zt'  src='" + basePath + "images/home/ico-5.png'>");//
                   }
                html.push("</div>");

                    html.push("<div class='title'>");
                    html.push("<p>");

                    html.push(obj.appName);

                    html.push("</p>");

                    html.push(" </div>");

                    html.push("</li>");


            }


            $(".app-div .ul").append(html.join(""));


        },
        error: function (data) {
            console.log("失败");
        }
    });


}
//点击应用跳转应用详情
function detail(appName){
    var url = "";
    if(appName=="商家") {
       url="ea/industry/ea_getAllCompanyList.jspa?industryType=";
    }else if(appName=="资讯") {
        url="ea/wfjshop/ea_getNewsList.jspa?typeNews=";
    }else if(appName=="视频") {

    }else if(appName=="购物") {
          url="ea/digitalmall/ea_DigitalMall.jspa?back=index";
    }else if(appName=="超市") {
         url="page/ea/main/marketing/unmannedsupermarket/supermarket_home.jsp";
    }else if(appName=="周边") {
          url="ea/qyrz/ea_toPeriphery.jspa";
    }else if(appName=="约车训练") {
        url = "ea/mappointment/ea_theTestTime.jspa?sccId="+sccId+"&sc=web";
    }else if(appName=="市场") {
        url="ea/phl/ea_getPhlIndex.jspa";

    }else if(appName=="招聘") {
        url="ea/bidrecruit/ea_getRecruitIndex.jspa?lei=gs";
    }else if(appName=="招商") {
           url="ea/productAgent/ea_productAgentList.jspa";
    }else if(appName=="招标") {
           url="ea/purchasebids/ea_findGoodbidList.jspa";
    }else if(appName=="e路快车") {
           url="ea/industry/ea_getCompanyHome.jspa?ccompanyId=contactCompany20170107NT6PP8C9X40000029147&industryType=&etype=&sc=web";
    }else if(appName=="慈善") {
           url="ea/wfjshop/ea_getNewsList.jspa?typeNews=&cate=慈善捐赠";
    }else if(appName=="会员") {
           url="ea/consignee/ea_toVipCenter.jspa?backu=2";

    }

    document.location.href = basePath+url;
}


function formatNum(num){
    var s = "";
    if(Number(num)>10000){
        s = Number(num)/10000;
        s = s.toFixed(1)+"w";

    }else{
        s = num;
    }
    return s;
}








