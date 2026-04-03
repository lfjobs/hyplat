
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
    $(".exam").click(function(event){

        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var tqeID = $(".ul li.active").attr("id");

        var status = $("li#"+tqeID).find(".status").text();
        if(status=="03"){
            $(".div-tingyong").show();
            $(".titlep").text("超时未交卷无法进入考试");
            return false;
        }else if(status=="02"){
            $(".div-tingyong").show();
            $(".titlep").text("已完成考试不能重复考试");
            return false;
        }else{
            document.location.href = basePath+"ea/quest/ea_startExam.jspa?totalQuestionExam.tqeID="+tqeID;

        }


    });



    //查看
    $(".view").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var tqeID = $(".ul li.active").attr("id");

        if(tqeID==""){
            $(".div-tingyong").show();
            $(".titlep").text("请选择");
            return false;
        }
        document.location.href = basePath+"ea/quest/ea_startExam.jspa?totalQuestionExam.tqeID="+tqeID;

    })






    $(".close-tingyong,.close-confirm").click(function(){

        $(".div-tingyong").hide();
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
        + "ea/quest/sajax_ea_myExam.jspa";
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
                if ($("#" + obj[9]).length == 0) {

                    if (i == pageForm.list.length - 1) {

                        html.push("<li class='clearfix last1' id='" + obj[9] + "'>");
                    } else {
                        html.push("<li class='clearfix' id='" + obj[9]+ "'>");
                    }
                    html.push("<div class='title-pc'>");
                    html.push("<div class='sex'>");
                    html.push("<img class='img-01' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'>");
                    html.push("<img class='img-02' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'>");
                    html.push("</div>");
                    html.push("<span class='status' style='display: none;'>"+obj[8]+"</span>");

                    html.push("<div class='date-p' title='"+obj[5]+"'>"+nullv(obj[5])+"</div>");

                    html.push("<div class='date-p' title='"+obj[1]+"'>"+obj[1]+"</div>");

                    html.push("<div class='date-p pc' title='"+obj[2]+"'>"+obj[2]+"</div>");
                    html.push("<div class='date-p pc' title='"+obj[3]+"'>"+obj[3]+"</div>");

                    html.push("<div class='date-p pc' title='"+obj[4]+"'>"+obj[4]+"</div>");
                    html.push("<div class='date-p pc' title='"+obj[6]+"'>"+(obj[6]=="00"?"合格":obj[6] =="01"?"不合格":"未考试")+"</div>");
                    html.push("<div class='date-p' title='"+obj[7]+"'>"+(obj[7]==null?"0":obj[7])+"</div>");
                    html.push("<div class='date-p' title='"+obj[8]+"'>"+(obj[8]=="00"?"未考试":obj[8] =="01"?"考试中":obj[8] =="02"?"已完成":"已超时")+"</div>");
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
    return  (value==null||value=="")?"未设定":value;
}
function timestampToTime(d) {
    if(d==null||d==""){
        return "";
    }
    var date = new Date(d.time);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate())   + ' ';
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var seconds = date.getSeconds();
    return Y+M+D+" "+hours+":"+minutes+":"+seconds;//不显示时分秒
}


function viewContent(tqID){

      document.location.href = basePath+"ea/quest/ea_getQuestionsList.jspa?totalQuestion.tqID="+tqID;
}


