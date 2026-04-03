$(document).ready(function() {
	information();
})
function information() {
    var url = basePath + "ea/newpcend/sajax_ea_cnRecommendation.jspa?";
    $
        .ajax({
            url : url,
            type : "post",
            async : true,
            dataType : "json",
            data : {
                "pageForm.pageNumber" : pageNumber,
                "pageForm.pageSize":5,
                "ccompanyId":ccompanyId
            },
            success : function(data) {
                var news = eval("(" + data + ")");
                var pageForm = news.pageForm;
                var list = news.list;
                var news1 = [];
                $(".web_con_left4").empty();
                if (pageForm != null && pageForm.list != null
                    && pageForm.list.length > 0) {
                    $(pageForm.list)
                        .each(
                            function(i, dom) {
                                news1.push("<a href='javascript:void(0);' onclick='details(this)'>");
                                news1.push("<li>");
                                news1.push("<input class='ppID' type='hidden' value='"+ this[3] + "'/>");
                                news1.push("<h3>"+this[0]+"</h3>");
                                news1.push("<p><img src='"+basePath+this[2]+"' alt='' onerror='this.src=\""
                                    + basePath
                                    + "/images/ea/production/forum/reportAnError.png\"'><span limit='17'>"+list[i]+"</span></p>");
                                news1.push("</li>");
                                news1.push("</a>");
                                news1.push("<hr style='border-top: 1px solid #ddd;width: 80%;margin: 20px auto 0 auto;'>");
                            })
                }
                $(".web_con_left4").append(news1.join(""));
            },
            error : function(data) {
                alert("获取失败");
            }
        });
}



function details(obj){
    var ppID = $(obj).find(".ppID").val();
    document.location.href = basePath + "ea/newpcend/ea_companyWebsite.jspa?titleJudge=04&seven=01&ppk.ppID="+ppID+"&ccompanyId="+ccompanyId;
}