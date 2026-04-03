
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

    //查看
    $(".view").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var tpId = $(".ul li.active").attr("id");

        if(tpId==""){
            $(".div-tingyong").show();
            $(".titlep").text("请选择");
            return false;
        }
       var resumeID = $("#"+tpId).find(".resId").text();
        var resumeID1 = $("#"+tpId).find(".resIds").text();
        var type = $("#"+tpId).find(".type").text();
        var r = "";
        if(type=="00"){
            r =resumeID;
        }else{
            r =resumeID1;
        }
        window.location.href = basePath+"ea/bidrecruit/ea_resumedetail.jspa?sccId="+sccId+"&resumeID="+r+"&tpId="+tpId;

    })
    //面试通知
    $(".ms").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var tpId = $(".ul li.active").attr("id");

        if(tpId==""){
            $(".div-tingyong").show();
            $(".titlep").text("请选择");
            return false;
        }
        var resumeID = $("#"+tpId).find(".resId").text();
        var resumeID1 = $("#"+tpId).find(".resIds").text();
        var type = $("#"+tpId).find(".type").text();
        var state = $("#"+tpId).find(".state").text();
        if(state=="01"){
            $(".div-tingyong").show();
            $(".titlep").text("已通知邀请面试");
            return false;
        }
        var r = "";
        if(type=="00"){
            r =resumeID;
        }else{
            r =resumeID1;
        }
        window.location.href = basePath+"ea/bidrecruit/ea_getInventInfo.jspa?sccId="+sccId+"&tpId="+tpId+"&resumeID="+r;

    })





    $(".close-tingyong").click(function(){
        del = 0;
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
        + "ea/tresume/sajax_ea_getListPage.jspa?talentPool.state="+state;
    $.ajax({
        type: "GET",
        url: ulp,
        async: false,
        dataType: "json",
        data: {
            "pageForm.pageNumber": pageNumber,
            sajax: "sajax",
            parameter: parameter
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

                    html.push("<span class='resId' style='display: none'>"+obj[12]+"</span>");
                    html.push("<span class='resIds' style='display: none'>"+obj[13]+"</span>");
                    html.push("<span class='type' style='display: none'>"+obj[8]+"</span>");
                    html.push("<span class='state' style='display: none'>"+obj[7]+"</span>");
                    html.push("<img class='img-01' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'>");
                    html.push("<img class='img-02' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'>");
                    html.push("</div>");

                    html.push("<div class='date-p' title='"+obj[1]+"'>"+nullv(obj[1])+"</div>");

                    html.push("<div class='date-s' title='"+obj[10]+"'>"+nullv(obj[10])+"</div>");

                    html.push("<div class='date-p pc' title='"+obj[11]+"'>"+nullv(obj[11])+"</div>");

                    html.push("<div class='date-p pc' title='"+obj[2]+"'>"+nullv(obj.auditionPost)+"</div>");
                    html.push("<div class='date-p pc' title='"+obj[3]+"'>"+nullv(obj.place)+"</div>");

                    html.push("<div class='date-p pc' title='"+obj[4]+"'>"+nullv(obj.auditionDept)+"</div>");
                    html.push("<div class='date-p pc' title='"+timestampToTime(obj[6])+"'>"+nullv(timestampToTime(obj[6]))+"</div>");
                    html.push("<div class='date-p pc' title='"+obj[7]+"'>"+(obj[7]=='00'?'未通知':'已邀请')+"</div>");
                    html.push("<div class='date-p ' title='"+(obj[8]=='00'?'投递':'抢人才')+"'>"+(obj[8]=='00'?'投递':'抢人才')+"</div>");

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

function nullv(value){
    return  (value==null||value=="")?"无":value;
}
function timestampToTime(d) {
    if(d==null||d==""){
        return "";
    }
    var date = new Date(d.time);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate())   + ' ';

    return Y+M+D;//不显示时分秒
}



