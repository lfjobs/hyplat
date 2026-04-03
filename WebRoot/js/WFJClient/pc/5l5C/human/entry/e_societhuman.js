
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


    //新建
    $(".draft").click(function(event){


        document.location.href = basePath+"ea/entry/ea_getSBaseInfo.jspa";


    });



    //修改
    $(".edit").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var staffID = $(".ul li.active").attr("id");

        if(staffID==""){
            $(".div-tingyong").show();
            $(".titlep").text("请选择");
            return false;
        }
        document.location.href = basePath+"ea/entry/ea_getSBaseInfo.jspa?staffID="+staffID;

    })



    //查看
    $(".view").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var staffID = $(".ul li.active").attr("id");

        if(staffID==""){
            $(".div-tingyong").show();
            $(".titlep").text("请选择");
            return false;
        }
        document.location.href = basePath+"ea/entry/ea_getSBaseInfo.jspa?staffID="+staffID+"&view=view";




    })

    //转面试登记
    $(".zms").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var staffID = $(".ul li.active").attr("id");

        if(staffID==""){
            $(".div-tingyong").show();
            $(".titlep").text("请选择");
            return false;
        }

        //var staffIdentityCard = $("li#"+staffID).find(".staffIdentityCard").text();
        var staffIdentityCard = $("li#"+staffID).find(".staffIdentityCard").html();
        if(staffIdentityCard==""){
            $(".div-tingyong").show();
            $(".titlep").text("请完善身份证号");
            return false;
        }
        var ulp = basePath
            + "ea/entry/sajax_ea_zmsRegister.jspa";
        $.ajax({
            type: "GET",
            url: ulp,
            async: false,
            dataType: "json",
            data: {
               staffID: staffID
            },
            success: function (data) {
                var m = eval("("+data+")");
                var r = m.r;
                if(r=="1"){
                    $(".titlep").text("已经在职");
                }else if(r=="2"){

                    $(".titlep").text("已存在于面试登记");
                }else{
                    $(".titlep").text("操作成功");
                }
                $(".div-tingyong").show();
                return false;
            },
            error:function(data){
            }
        })


    })





    $(".close-tingyong").click(function(){

        $(".div-tingyong").hide();
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
        + "ea/entry/sajax_ea_getSocietHumanList.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: false,
        dataType: "json",
        data: {
            "pageNumber": pageNumber,
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

                        html.push("<li class='clearfix last1' id='" + obj.staffID + "'>");
                    } else {
                        html.push("<li class='clearfix' id='" + obj.staffID+ "'>");
                    }
                    html.push("<div class='title-pc'>");
                    html.push("<div class='sex'>");
                    html.push("<img class='img-01' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'>");
                    html.push("<img class='img-02' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'>");
                    html.push("</div>");

                    html.push("<div class='date-p' title='"+obj.staffCode+"'>"+nullv(obj.staffCode)+"</div>");

                    html.push("<div class='date-p' title='"+obj.staffName+"'>"+nullv(obj.staffName)+"</div>");

                    html.push("<div class='date-p pc' title='"+obj.sex+"'>"+nullv(obj.sex)+"</div>");

                    html.push("<div class='date-s staffIdentityCard' title='"+obj.staffIdentityCard+"'>"+nullv(obj.staffIdentityCard)+"</div>");
                    html.push("<div class='date-p pc' title='"+obj.reference+"'>"+nullv(obj.reference)+"</div>");

                    html.push("<div class='date-p pc' title='"+obj.birthday+"'>"+nullv(obj.birthday)+"</div>");
                    html.push("<div class='date-p pc' title='"+obj.nation+"'>"+nullv(obj.nation)+"</div>");
                    html.push("<div class='date-p pc' title='"+obj.source+"'>"+nullv(obj.source)+"</div>");

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




