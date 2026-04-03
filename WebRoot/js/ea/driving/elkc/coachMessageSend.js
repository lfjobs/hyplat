$(document).ready(function () {

    ajax();

    $(".task_search").on('input propertychange',function(){
        pageNumber = 0;
        $(".task_list").empty();
        ajax();
    });

});

function getHeight(){
    t=setTimeout("getHeight()", 200);
    if($(".last").length>0) {
        if ($(".last").offset().top < $(window).height()) {
            if (pageNumber < pageCount) {
                ajax();
            }
        }
    }
}

function ajax() {
    if(pageNumber==0){
        $(".send_con").html("");
    }
    var task_search = $(".task_search").val();
    var url = basePath
        + "ea/elkcInform/sajax_ea_ajaxMessageSent.jspa";
    $.ajax({
        url : url,
        type : "post",
        async : false,
        dataType : "json",
        data : {
            "edsn.theme":$.trim(task_search),
            "edsn.createperson":staffID,
            "pageForm.pageNumber":pageNumber+1,
            "pageForm.pageSize":5
        },
        success : function(data) {
            var news = eval("(" + data + ")");
            var map = news.map;
            var pageForm = map.pageForm;
            if (pageForm != null && pageForm.list != null && pageForm.list.length > 0) {
                var inform = [];
                var list = pageForm.list;
                $(".last").removeClass("last");
                $(pageForm.list).each(function(i,dom){
                    if($(pageForm.list).length-1==i){
                        inform.push("<div class='send_box last'>");
                    }else{
                        inform.push("<div class='send_box'>");
                    }
                    inform.push("<div class='send_tit'>");
                    inform.push("<span>"+this[1]+"</span>");
                    var dt = new Date(this[2].replace(/-/,"/"));//发布时间
                    var date = new Date();//当前时间
                    //getFullYear(); //获取完整的年份(4位,1970-????)
                    //getMonth(); //获取当前月份(0-11,0代表1月)
                    //getDate(); //获取当前日(1-31)
                    //getHours(); //获取当前小时数(0-23)
                    //getMinutes(); //获取当前分钟数(0-59)
                    if (dt.getFullYear()==date.getFullYear() && dt.getMonth()==date.getMonth() && dt.getDate()==date.getDate()){
                        inform.push("<span>"+dt.getHours()+":"+dt.getMinutes()+"</span>");
                    }else{
                        inform.push("<span>"+dt.getMonth()+"月"+dt.getDate()+"日"+"</span>");
                    }
                    inform.push("</div>");
                    inform.push("<p class='send_p'>"+this[3]+"</p>");
                    inform.push("<div class='assign_box clearfix'>");
                    inform.push("<div class='assign_img_wrap'>");
                    $(map[i]).each(function(j,dom){
                        inform.push("<img src='"+basePath+this[1]+"' class='assign_img' alt='' onerror='aaa(this)'>");
                    })
                    inform.push("</div>");
                    inform.push("<a href='"+basePath+"ea/elkcInform/ea_coachMessageSendDetails.jspa?edsn.edsnId="+this[0]+"&staffID="+staffID+"&companyid="+companyid+"' class='more_ico'>···</a>");
                    inform.push("</div></div>");
                })
                $(".send_con").append(inform.join(" "));

                pageNumber = pageForm.pageNumber;
                pageCount = pageForm.pageCount;
                if(pageNumber<pageCount){
                    getHeight();
                }
            }
        }
    });
}

function aaa(obj) {
    $(obj).attr("src",basePath+"images/ea/driving/elkc/head.png");
}