var del = 0;

var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端getExaminePage

$(document).ready(function () {

    showContent(status);


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
        var auditionID = $(".ul li.active").attr("id");

        if(auditionID==""){
            $(".div-tingyong").show();
            $(".titlep").text("请选择");
            return false;
        }

        window.location.href = basePath+"ea/entry/ea_getView.jspa?audition.auditionID="+auditionID+"&vm=v";

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
        + "ea/entry/sajax_ea_getAuditionList.jspa?status="+status;
    $.ajax({
        type: "GET",
        url: ulp,
        async: false,
        dataType: "json",
        data: {
            pageNumber: pageNumber,
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
                if ($("#" + obj.auditionID).length == 0) {

                    if (i == pageForm.list.length - 1) {

                        html.push("<li class='clearfix last1' id='" + obj.auditionID + "'>");
                    } else {
                        html.push("<li class='clearfix' id='" + obj.auditionID + "'>");
                    }

                    html.push("<div class='title-pc'>");

                    html.push("<div class='sex'>");
                    html.push("<img class='img-01' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'>");
                    html.push("<img class='img-02' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'>");
                    html.push("</div>");
                    html.push("<span class='sts' style='display: none;'>"+obj.status+"</span>");
                    html.push("<span class='zts' style='display: none;'>"+obj.ztState+"</span>");


                    html.push("<div class='date-p' title='"+obj.staffName+"'>"+nullv(obj.staffName)+"</div>");

                    html.push("<div class='date-s' title='"+obj.staffIdentityCard+"'>"+nullv(obj.staffIdentityCard)+"</div>");

                    html.push("<div class='date-p pc' title='"+obj.reference+"'>"+nullv(obj.reference)+"</div>");

                    html.push("<div class='date-p pc' title='"+obj.auditionPost+"'>"+nullv(obj.auditionPost)+"</div>");
                    html.push("<div class='date-p pc' title='"+obj.place+"'>"+nullv(obj.place)+"</div>");
                    html.push("<div class='date-p pc' title='"+obj.examiner+"'>"+nullv(obj.examiner)+"</div>");
                    if(status=="5"){
                        html.push("<div class='date-p pc' title='"+timestampToTime2(obj.auditionDate)+"'>"+nullv(timestampToTime(obj.auditionDate))+"</div>");

                    }
                    if(status!="5") {
                        html.push("<div class='date-p pc' title='" + timestampToTime(obj.regisDate) + "'>" + nullv(timestampToTime(obj.regisDate)) + "</div>");
                    }

                    if(status=="13"||status=="14"||status=="15"){
                        html.push("<div class='date-p' title='"+(obj.zpState=='1'?'评定合格':obj.zpState=='2'?'评定不合格':'未判定')+"'>"+(obj.zpState=='1'?'评定合格':obj.zpState=='2'?'评定不合格':'未判定')+"</div>");

                    }else if(status=="16"||status=="17"||status=="18"){
                        html.push("<div class='date-p' title='"+(obj.ztState==null||obj.ztState==''?'未转':'已转')+"'>"+(obj.ztState==null||obj.ztState==''?'未转':'已转')+"</div>");

                    }else if(status=="19"||status=="20"||status=="21"){
                        html.push("<div class='date-p' title='"+(obj.ztState=='3'?'已通知':'未通知')+"'>"+(obj.ztState=='3'?'已通知':'未通知')+"</div>");

                    }else if(status=="4"||status=="5"||status=="6"){
                        html.push("<div class='date-p' title='"+(obj.status=='01'?'待登记':'已登记')+"'>"+(obj.status=='01'?'待登记':'已登记')+"</div>");

                    }else if(status=="2"){
                        html.push("<div class='date-p' title='已邀请'>已邀请</div>");

                    }else if(status=="7"||status=="8"||status=="9"){
                        html.push("<div class='date-p' title='"+(obj.bsState=='2'?'笔试不合格':obj.bsState=='1'?'笔试合格':'未判定')+"'>"+(obj.bsState=='2'?'笔试不合格':obj.bsState=='1'?'笔试合格':'未判定')+"</div>");

                    }else if(status=="10"||status=="11"||status=="12"){
                        html.push("<div class='date-p' title='"+(obj.ksState=='2'?'口试不合格':obj.ksState=='1'?'口试合格':'未判定')+"'>"+(obj.ksState=='2'?'口试不合格':obj.ksState=='1'?'口试合格':'未判定')+"</div>");

                    }

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
function timestampToTime2(d) {
    if(d==null||d==""){
        return "";
    }
    var date = new Date(d.time);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate())   + ' ';
    var hours = date.getHours();
    var minutes = date.getMinutes();
    return Y+M+D+" "+hours+":"+minutes;
}

function  showContent(status) {

          $(".act").removeClass("act");
        $(".nav"+status).addClass("act");

        if(status=="2"){
            $(".title-li").text("已通知面试");
        } else if(status=="4"){
          $(".title-li").text("应来面试管理");
         }else if(status=="5"){
            $(".title-li").text("未来面试管理");
        }else if(status=="6"){
            $(".title-li").text("已来面试管理");
        }else if(status=="7"){
            $(".title-li").text("笔试结果");
        }else if(status=="8"){
            $(".title-li").text("笔试合格");
        }else if(status=="9"){
            $(".title-li").text("笔试不合格");
        }else if(status=="10"){
            $(".title-li").text("口试结果");
        }else if(status=="11"){
            $(".title-li").text("口试合格");
        }else if(status=="12"){
            $(".title-li").text("口试不合格");
        }else if(status=="13"){
            $(".title-li").text("综合评定");
        }else if(status=="14"){
            $(".title-li").text("已合格评定");
        }else if(status=="15"){
            $(".title-li").text("不合格评定");
        }else if(status=="16"){
            $(".title-li").text("应转入职通知");
        }else if(status=="17"){
            $(".title-li").text("已转入职通知");
        }else if(status=="18"){
            $(".title-li").text("未转通入职知");
        }else if(status=="19"){
            $(".title-li").text("应通知入职");
        }else if(status=="20"){
            $(".title-li").text("未通知入职");
        }else if(status=="21"){
            $(".title-li").text("已通知入职");
        }

}

//笔试，口试，综合评定
function exam(opr){
    //面试
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var auditionID = $(".ul li.active").attr("id");

        if(auditionID==""){
            $(".div-tingyong").show();
            $(".titlep").text("请选择");
            return false;
        }

        window.location.href = basePath+"ea/entry/ea_getView.jspa?audition.auditionID="+auditionID+"&vm="+opr+"&status="+status;


}

//面试登记，转通知
function opr(vm){

        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var auditionID = $(".ul li.active").attr("id");

        if(auditionID==""){
            $(".div-tingyong").show();
            $(".titlep").text("请选择");
            return false;
        }

        if(vm=="m"){
            var sts = $("#"+auditionID).find(".sts").text();
            if(sts!="01"){
                $(".div-tingyong").show();
                $(".titlep").text("已面试登记");
                return false;
            }

        }else if(vm=="zt"){
            var zts = $("#"+auditionID).find(".zts").text();
            if(zts=="1"||zts=="3"){
                $(".div-tingyong").show();
                $(".titlep").text("已转通知请勿重复操作");
                return false;
            }


        }

        window.location.href = basePath+"ea/entry/ea_getView.jspa?audition.auditionID="+auditionID+"&vm="+vm;




}

