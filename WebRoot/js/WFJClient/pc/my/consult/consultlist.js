
$(document).ready(function () {

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

    $(".returnv").change(function(){
        var returnVisit = $(".returnv").val()
        $(".sec-list .ul li").not(":first").remove();
        pageNumber = 0;
        pageCount = 0;
        load();

    });
    //查看
    $(".view").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var crId = $(".ul li.active").attr("id");

        document.location.href = basePath
            + "ea/consult/ea_viewDetail.jspa?consult.crId="+crId;


    })
    //回访记录
    $(".edit").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var crId = $(".ul li.active").attr("id");
        var name = $.trim($(".ul li.active").find(".docNum-p").text());
        document.location.href = basePath
            + "ea/consult/ea_viewRecord.jspa?consult.crId="+crId+"&name="+name;


    })

    //导出
    $(".excel").click(function(){

        var returnVisit = $(".returnv").val();
        var parameter = $("#search").val();
        document.location.href = basePath
            + "ea/consult/ea_showExcel.jspa?parameter="+parameter+"&returnVisit="+returnVisit;


    })
    //打印
    $(".print").click(function(){


        var u = navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端





        if(isAndroid||isiOS) {

            $(".div-tingyong").show();

            $(".titlep").text("请到电脑端打印");
            return false;
        }




        var returnVisit = $(".returnv").val();
        var parameter = $("#search").val();
        $("#printIframe").attr("src",basePath
            + "ea/consult/ea_printData.jspa?parameter="+parameter+"&returnVisit="+returnVisit);

        printIframe.onload = function(){
            $("#printIframe")[0].contentWindow.print();

            console.log('iframe加载完成');
        }
    })








    $(".close-tingyong,.close-confirm").click(function(){

        $(".div-tingyong").hide();
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
    $(document).on("click","ul li",function(event){
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
    var returnVisit = $(".returnv").val()
    var ulp = basePath
        + "ea/consult/sajax_ea_getConsultslist.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            pageNumber: pageNumber,
            ajax: "ajax",
            pageSize:20,
            parameter: parameter,
            returnVisit:returnVisit

        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var pageForm = member.pageForm;
            var html = new Array();

            if (pageForm == null) {
                return false;

            }
            pageNumber = pageForm.pageNumber;
            pageCount = pageForm.pageCount;
            $(".last1").remove();
            var obj = "";


                for (var i = 0; i < pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    if ($("#" + obj.docId).length == 0) {
                        if (i == pageForm.list.length - 1) {

                            html.push("<li class='clearfix last1' id='" + obj.crId + "'>");
                        } else {
                            html.push("<li class='clearfix' id='" + obj.crId + "'>");
                        }
                        html.push("<div class='title-pc'>");
                        html.push("<div class='sex'>");
                        html.push("<img class='img-01' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'>");
                        html.push("<img class='img-02' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'>");
                        html.push("</div>");

                        html.push("<div class='docNum-p' title='" + obj.consultantName + "'>" + obj.consultantName + "</div>");
                        html.push("<div class='title-p' title='"+obj.consultantPhone+"'><a href='tel:"+obj.consultantPhone+"'>"+obj.consultantPhone+"</div>");

                        html.push("<div class='theme-p' title='"+obj.consultingDate+"'>"+timestampToTime(obj.consultingDate)+"</div>");
                        html.push("<div class='docType-p' title='" + ((obj.consultantContent == null ||obj.consultantContent == "")?'其他' : obj.consultantContent) + "'>" + ((obj.consultantContent == null ||obj.consultantContent == "") ? '其他' : obj.consultantContent) + "</div>");

                        if (obj.returnVisit == "00") {
                            html.push("<div class='emergencyType-p' title='否'>否</div>");
                        } else if (obj.returnVisit == "01") {
                            html.push("<div class='emergencyType-p' title='是'>是</div>");

                        }


                        html.push("</div>");


                        html.push("</li>");
                    }


                }


            $(".sec-list .ul").append(html.join(""));


        },
        error: function (data) {
            console.log("失败");
        }
    });


}




function timestampToTime(d) {
    if(d==null||d==""){
        return "";
    }
    var date = new Date(d.time);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate())   + ' ';
    var h = date.getHours()+":";
    var mm = date.getMinutes()+":";
    var ss = date.getSeconds();
    return Y+M+D+h+mm+ss;//不显示时分秒
}
