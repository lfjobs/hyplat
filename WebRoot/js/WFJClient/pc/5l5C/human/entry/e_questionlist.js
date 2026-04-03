
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


        document.location.href = basePath+"ea/quest/ea_getQuesBaseInfo.jspa?type="+type;


    });



    //修改
    $(".edit").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }
        var tqID = $(".ul li.active").attr("id");

        if(tqID==""){
            $(".div-tingyong").show();
            $(".titlep").text("请选择");
            return false;
        }
        document.location.href = basePath+"ea/quest/ea_getQuesBaseInfo.jspa?totalQuestion.tqID="+tqID+"&type="+type;

    })





    //题库分类
    $(".fenlei").click(function(){

        document.location.href =basePath+"ea/quest/ea_getQueTypeList.jspa";




    })
    //删除
    var del = 0;
    $(".del").click(function(){
        var li = $(".ul li.active").length;
        if(li<1){
            return false;
        }

        $(".div-tingyong").show();
        $(".titlep").text("确定要删除题库?");
        del = 1;
        return false;



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
        var tqID = $(".ul li.active").attr("id");
        var $li = $(".ul li.active");


        var url = basePath + "/ea/quest/sajax_ea_delQues.jspa";
        $.ajax({
            url : url,
            type : "post",
            async : false,
            dataType : "json",
            data :{
                "totalQuestion.tqID":tqID
            },
            success : function(data) {
                $li.remove();
            },
            error : function(data) {
                alert("删除失败");
            }
        });
    })


    $(".close-tingyong").click(function(){

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
        + "ea/quest/sajax_ea_getQuestionBaseList.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: false,
        dataType: "json",
        data: {
            "pageNumber": pageNumber,
            sajax: "sajax",
            parameter: parameter,
            type:type
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
                if ($("#" + obj.tqID).length == 0) {

                    if (i == pageForm.list.length - 1) {

                        html.push("<li class='clearfix last1' id='" + obj.tqID + "'>");
                    } else {
                        html.push("<li class='clearfix' id='" + obj.tqID+ "'>");
                    }
                    html.push("<div class='title-pc'>");
                    html.push("<div class='sex'>");
                    html.push("<img class='img-01' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'>");
                    html.push("<img class='img-02' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'>");
                    html.push("</div>");

                    html.push("<div class='date-p' title='"+obj.titleBase+"'>"+nullv(obj.titleBase)+"</div>");

                    html.push("<div class='date-p' title='"+obj.typeName+"'>"+nullv(obj.typeName)+"</div>");

                    html.push("<div class='date-p pc' title='"+obj.duration+"'>"+nullv(obj.duration)+"</div>");

                    html.push("<div class='date-p pc' title='"+obj.totalscore+"'>"+nullv(obj.totalscore)+"</div>");
                    html.push("<div class='date-p pc' title='"+obj.qualifiedSocre+"'>"+nullv(obj.qualifiedSocre)+"</div>");

                    html.push("<div class='date-p pc' title='"+obj.totalQues+"'>"+nullv(obj.totalQues)+"</div>");
                    html.push("<div class='date-p pc' title='"+obj.staffName+"'>"+nullv(obj.staffName)+"</div>");
                    html.push("<div class='date-s pc' title='"+timestampToTime(obj.createDate)+"'>"+timestampToTime(obj.createDate)+"</div>");
                    html.push("<div class='date-p ' title='题目明细'><span class='span-view' onclick=\"viewContent('"+obj.tqID+"')\" >查看内容</span></div>");
                    // html.push("<div class='date-p pc' title='"+(obj.type=='00'?'笔试题':'口试题')+"'>"+(obj.type=='00'?'笔试题':'口试题')+"</div>");

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


