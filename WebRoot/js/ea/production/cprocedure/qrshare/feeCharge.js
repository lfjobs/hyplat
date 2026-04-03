var del = 0;

var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端getExaminePage

$(document).ready(function () {


    //新建
     $(".draft").click(function(event){
         $(".ul .active").removeClass("active");
         document.location.href  = basePath+"ea/productslaunch/ea_toProductsLaunch.jspa?typez=tc";

     });

   $("#qsearch").click(function() {
       $(".sec-list .ul li").not(":first").remove();
        pageNumber = 0;
        pageCount = 0;
        load();
    });


    $("#search").bind('keyup', function() {
        var parameter = $("#search").val();
        if(parameter=="") {

           $(".sec-list .ul li").not(":first").remove();

            pageNumber = 0;
            pageCount = 0;
            load();
        }
    });

    //修改
    $(".edit").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var feecID = $(".ul li.active").attr("id");
        var ppId = $("#"+feecID).find(".ppid").text();
        var goodsID =$("#"+feecID).find(".goodsid").text();
        var startUsing =$("#"+feecID).find(".startUsing").text();
        if(startUsing!="01"){
            $(".div-tingyong").show();
            $(".titlep").text("只有草稿状态可以修改");
            return false;
        } 

        window.location.href = basePath+"ea/productslaunch/ea_toProductsLaunch.jspa?ppId="+ppId+"&goodsId="+goodsID+"&typez=tc";

    })



    //提交审核至领导审批
    $(".submitAudit").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var feecID = $(".ul li.active").attr("id");
        var startUsing =$("#"+feecID).find(".startUsing").text();
        if(startUsing=="00"){
            $(".div-tingyong").show();
            $(".titlep").text("已审核通过请勿重复提交");
            return false;
        } else if(startUsing=="04"){
            $(".div-tingyong").show();
            $(".titlep").text("审核中请勿重复提交");
            return false;
        }else if(startUsing=="03"){
            $(".div-tingyong").show();
            $(".titlep").text("已被驳回请勿重复提交");
            return false;
        }

        var ppId = $("#"+feecID).find(".ppid").text();
        var goodsID =$("#"+feecID).find(".goodsid").text();

        localStorage.setItem("title", "停车收费收费标准");
        localStorage.setItem("source","tc");
        localStorage.setItem("htmlUrl","ea/productslaunch/ea_toProductsLaunch.jspa?ppId="+ppId+"&goodsId="+goodsID+"&audit=tc&typez=tc&companyId="+companyId);
        localStorage.setItem("tableName","FeeScale");
        localStorage.setItem("idName","feecID");
        localStorage.setItem("idValue",feecID);
        localStorage.setItem("stateName","startUsing");
        localStorage.setItem("stateValue","00");
        localStorage.setItem("refundValue","03");
        window.location.href=basePath+"page/ea/main/office_ea/contract/selectCompany.jsp?typee=L";


    })

    //删除
    $(".del").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }

        var feecID = $(".ul li.active").attr("id");
        var startUsing =$("#"+feecID).find(".startUsing").text();
        if(startUsing=="04"){
            $(".div-tingyong").show();
            $(".titlep").text("审核中无法删除");
            return false;
        }



        $(".div-tingyong").show();
        $(".titlep").text("确定删除？");
        del = 1;
        return false;


    })


    $(".close-tingyong").click(function(){
        del = 0;
        $(".div-tingyong").hide();
    })

    $(".close-confirm").click(function(){
        $(".div-tingyong").hide();
        if(del==0){
            return false;
        }

        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var feecID = $(".ul li.active").attr("id");
        var $li = $(".ul li.active");


        var url = basePath + "ea/carmanage/sajax_ea_delStandard.jspa";
        $.ajax({
            url : url,
            type : "post",
            async : false,
            dataType : "json",
            data :{
                "feeScale.feecID":feecID
            },
            success : function(data) {
                var standard = eval("(" + data + ")");
                if(standard.boolean){
                    $li.remove();
                }
            },
            error : function(data) {
                alert("删除失败");
            }
        });
    })

    $(".close-tingyong1").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }

        $(".div-tingyong1").hide();
    })



    $(window).scroll(function () {
        



        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度


        if(scroll>115){
            $(".sec-nav").addClass("nav");
        }else{
          $(".sec-nav").removeClass("nav");
        }

        var Top = $(".last1").offset().top; //元素距离顶部距离


        if (Top - Height - scroll <= 20) {
            if (pageNumber < pageCount) {
                load();
            }
        }

    })



    //选中
    $(document).on("click",".ul li",function(event){
          event.stopPropagation();
        if($(this).is(".active")){
            $(this).removeClass("active");
        }else{

           $(".ul .active").removeClass("active");

            $(this).addClass("active");
        }
    })





})
function load() {
    if(pageNumber==0){
        $(".sec-list .ul li").not(":first").remove();
    }
    pageNumber = pageNumber + 1;

    var parameter = $.trim($("#search").val());

    var ulp = basePath
        + "ea/carmanage/sajax_ea_feescale.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            pageNumber: pageNumber,
            sajax: "sajax",
            "vf.siteNumber": parameter
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var pageForm = member.pageForm;
            var totalPct = member.totalPct;
            var html = new Array();

            if (pageForm == null) {
                return false;

            }
            pageNumber = pageForm.pageNumber;
            pageCount = pageForm.pageCount;
            $(".last1").removeClass("last1");
            var obj = "";

                for (var i = 0; i < pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    if ($("#" + obj[0]).length == 0) {

                        if (i == pageForm.list.length - 1) {

                            html.push("<li class='clearfix last1' id='" + obj[0] + "'>");
                        } else {
                            html.push("<li class='clearfix' id='" + obj[0] + "'>");
                        }

                        html.push("<div class='title-pc'>");
                        html.push("<div class='sex'>");
                        html.push("<span class='ppid' style='display: none;'>" + obj[18] + "</span>");
                        html.push("<span class='goodsid' style='display: none;'>" + obj[19] + "</span>");

                        html.push("<img class='img-01' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'>");
                        html.push("<img class='img-02' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'>");
                        html.push("</div>");





                        html.push("<div class='date-p pc' title='" + obj[1]+ "'>" + obj[1] + "</div>");
                        html.push("<div class='date-p' title='"+obj[13]+"'>" + obj[13]*(1+totalPct/100) + "</div>");
                        html.push("<div class='date-p' title='"+obj[9]+"'>");
                        if (obj[9] == "0") {
                            html.push('<span>小时</span>');
                        } else if (obj[9] == "1") {
                            html.push('<span>包天</span>');

                        } else if (obj[9] == "2") {
                            html.push('<span>包月</span>');

                        } else {
                            html.push('<span>包年</span>');
                        }

                        if (obj[17] == "0") {
                            html.push('<span>-当天(00点结束)</span>');
                        } else if (obj[17] == "24") {
                            html.push('<span>-24小时制</span>');

                        } else if (obj[17] == "8") {
                            html.push('<span>-8小时制</span>');

                        }

                        html.push("</div>");
                        html.push("<div class='date-p pc' title='"+obj[4]+"'>"+obj[4]+"</div>");

                        html.push("<div class='date-p' title='"+obj[5]+"'>"+obj[5]+"</div>");

                        html.push("<div class='date-p pc' title='"+obj[7]+"'>"+obj[7]+"</div>");
                        html.push("<div class='date-p' title='"+obj[8]+"'>");



                        if (obj[8] == "00") {
                            html.push("<span style='color:#0d9908'>审核通过</span>");
                        } else if (obj[8] == "01") {
                            html.push("<span style='color:#F00'>草稿</span>");

                        } else if (obj[8] == "03") {
                            html.push("<span  style='color:#F00'>驳回</span>");

                        } else if (obj[8] == "04") {
                            html.push("<span style='color:#ffa91b'>审核中</span>");

                        }
                        html.push("</div>");
                        html.push("<div class='date-p' title='"+obj[10]+"'>");
                        if (obj[10] == "p") {
                            html.push('<span>私家车</span>');
                        } else if (obj[10] == "c") {
                            html.push('<span>教练车</span>');

                        }
                        html.push("</div>");
                        html.push("<div class='date-p pc' title='"+obj[11]+"'>"+obj[11]+"</div>");


                        html.push("</li>");
                    }


                }


            $(".sec-list .ul").append(html.join(""));
            var clientWidth = document.documentElement.clientWidth;
            if(clientWidth>=960){

                $(".pc").show();
            }else {
                $(".pc").hide();
            }

        },
        error: function (data) {
            console.log("失败");
        }
    });


}




