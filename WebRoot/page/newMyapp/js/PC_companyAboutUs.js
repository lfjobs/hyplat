$(document).ready(function () {
    profile("00");
})
function profile(judge) {
    var url = basePath + "ea/wfjplatform/sajax_ea_AjaxNews.jspa?";
    $
        .ajax({
            url : url,
            type : "post",
            async : false,
            dataType : "json",
            data : {
                "pageForm.pageNumber" : pageNumber,
                "ccompanyId":ccompanyId,
                "type" : "web",
                "miniSystemJudge" : judge
            },
            success : function(data) {
                var profile = eval("(" + data + ")");
                var list = profile.list;
                var profile1 = [];
                $(".about_con_no .right #intro").empty();
                if (list != null && list.length > 0) {
                    $(list)
                        .each(
                            function(i, dom) {
                                profile1.push("<a href='javascript:void(0)' target='_blank' data-ppid='"+this[8]+"' onclick='details(this)'>");
                                profile1.push("<div class='list'>");
                                profile1.push("<img src='"+basePath+this[2]+"'>");
                                profile1.push("<div class='text'>");
                                profile1.push("<div class='tit'>");
                                profile1.push("<h4>"+this[0]+"</h4>");
                                profile1.push("</div>");
                                profile1.push("<h6 limit='52'>"+this[5]+"</h6>");
                                profile1.push("</div>");
                                profile1.push("</div>");
                                profile1.push("</a>");
                            })
                }
                $(".about_con_no .right #intro").append(profile1.join(""));
                $(".about_con_no .right #intro .list .text").find("img").css("display",
                    "none");
                $(".about_con_no .right #intro .list .text").find("span").attr("style",
                    "");
                $(".pub_txt")
                    .attr(
                        "style",
                        "word-break: break-all;text-overflow: ellipsis;display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 2;overflow: hidden;text-align: left;height:"
                        + $(window).height()
                        * 0.06 + "px;")
            },
            error : function(data) {
                alert("获取失败");
            }
        });
    jQuery.fn.limit=function(){
        var self = $("[limit]");
        self.each(function(){
            $(this).find(".article_pro").remove();
            $(this).find(".more_pro").remove();
            var objString = $(this).text();
            var objLength = $(this).text().length;
            var num = $(this).attr("limit");
            if(objLength > num){
                $(this).attr("title",objString);
                objString = $(this).text(objString.substring(0,num) + "...");
            }
        })
    }
    $(function(){
        $("[limit]").limit();
    })
}


function details(obj) {
    var ppid = $(obj).attr("data-ppid");
    var url = basePath + "/ea/newpcend/sajax_ea_culturalDetails.jspa?";
    $
        .ajax({
            url : url,
            type : "post",
            async : true,
            dataType : "json",
            data : {
                "ppk.ppID":ppid
            },
            success : function(data) {
                var profile = eval("(" + data + ")");
                var st = profile.st;
                $(".about_con_no .right #intro").empty();
                $(".about_con_no .right #intro").append(st[0]);
                $(".article_pro").css("display","none");
                $(".more_pro").css("display","none");

            },
            error : function(data) {
                alert("获取失败");
            }
        });
}
