

var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端getExaminePage

$(document).ready(function () {


    //新建
    $(".draft").click(function(event){
        $(".ul .active").removeClass("active");
        document.location.href  = basePath+"page/ea/main/office_ea/carmanage/feeAuditAdd.jsp";

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





    //删除
    $(".del").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }

        var cfID = $(".ul li.active").attr("id");


        $(".div-tingyong").show();
        $(".titlep").text("确定删除？");
        return false;


    })



    $(".close-tingyong").click(function () {
        $(".div-tingyong").hide();
    })
    $(".close-confirm").click(function(){
        $(".div-tingyong").hide();

        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var cfID = $(".ul li.active").attr("id");
        var $li = $(".ul li.active");


        var url = basePath + "ea/carmanage/sajax_ea_removeFeeCar.jspa";
        $.ajax({
            url : url,
            type : "post",
            async : false,
            dataType : "json",
            data :{
                cfID:cfID
            },
            success : function(data) {
                $li.remove();
            },
            error : function(data) {
                alert("删除失败");
            }
        });
    })



    $(window).scroll(function () {




        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度



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
        + "ea/carmanage/sajax_ea_getFeeAuditList.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            pageNumber: pageNumber,
            ajax: "ajax",
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
                if ($("#" + obj.cfID).length == 0) {

                    if (i == pageForm.list.length - 1) {

                        html.push("<li class='clearfix last1' id='" + obj.cfID + "'>");
                    } else {
                        html.push("<li class='clearfix' id='" + obj.cfID + "'>");
                    }

                    html.push("<div class='title-pc'>");
                    html.push("<div class='sex'>");

                    html.push("<img class='img-01' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'>");
                    html.push("<img class='img-02' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'>");
                    html.push("</div>");




                    html.push("<div class='date-p' title='"+obj.carNumber+"'>"+obj.carNumber+"</div>");
                    html.push("<div class='content-p' title='" + obj.siteName+ "'>" + obj.siteName + "</div>");
                    html.push("<div class='content-p' title='" + timestampToTime(obj.createdate)+ "'>" + timestampToTime(obj.createdate) + "</div>");



                    html.push("<div class='date-p' title='"+obj.staffName+"'>"+obj.staffName+"</div>");


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

    return Y+M+D;//不显示时分秒
}





