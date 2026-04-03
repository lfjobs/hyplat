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
    if($(".last").offset().top<$(window).height()){
        if(pageNumber<pageCount){
            ajax();
        }
    }
}

function ajax() {
    var task_search = $(".task_search").val();
    var url = basePath
        + "ea/elkcInform/sajax_ea_ajaxNotificationMessage.jspa";
    $.ajax({
        url : url,
        type : "post",
        async : false,
        dataType : "json",
        data : {
            "edsn.theme":$.trim(task_search),
            "elycNSAssociation.staffId":staffID,
            "pageForm.pageNumber":pageNumber+1,
            "pageForm.pageSize":5
        },
        success : function(data) {
            var news = eval("(" + data + ")");
            var pageForm = news.pageForm;
            if (pageForm != null && pageForm.list != null && pageForm.list.length > 0) {
                var inform = [];
                var list = pageForm.list;
                $(".last").removeClass("last");
                $(pageForm.list).each(function(i,dom){
                    if($(pageForm.list).length-1==i){
                        inform.push("<a href='"+basePath+"/ea/elkcInform/ea_detailsOfStudentInformation.jspa?edsn.edsnId="+this[0]+"' class='task_box last'>");
                    }else{
                        inform.push("<a href='"+basePath+"/ea/elkcInform/ea_detailsOfStudentInformation.jspa?edsn.edsnId="+this[0]+"' class='task_box'>");
                    }
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
                    inform.push("</a>");
                })
                $(".task_list").append(inform.join(" "));

                pageNumber = pageForm.pageNumber;
                pageCount = pageForm.pageCount;
                if(pageNumber<pageCount){
                    getHeight();
                }
            }
        }
    });
}