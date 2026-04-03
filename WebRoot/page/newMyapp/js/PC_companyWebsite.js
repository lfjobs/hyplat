$(document).ready(function () {

    ajax();


    $(document).on("click",".activity_mil ul li a .list",function(){
        ppid = $(this).attr("data-ppid");
        var url=basePath+"/ea/newpcend/ea_companyWebsite.jspa?titleJudge=01&seven=01&ccompanyId="+ccompanyId+"&ppk.ppID="+ppid;
        window.open(url);
    });

    $(document).on("click",".message_mil ul li a .list",function(){
        ppid = $(this).attr("data-ppid");
        var url=basePath+"/ea/newpcend/ea_companyWebsite.jspa?titleJudge=04&seven=01&ccompanyId="+ccompanyId+"&ppk.ppID="+ppid;
        window.open(url);
    });
})

function ajax() {
    surl1=basePath+"ea/industry/sajax_ea_homepage.jspa";
    $.ajax({
        url:encodeURI(surl1),
        type:"post",
        data:{"ccompanyId":ccompanyId},
        dataType:"json",
        async:true,
        success:function (data) {
            var jsonresult = eval("(" + data + ")");
            var addTo = [];
            var news = [];
            companyId = jsonresult.companyMessage.details[2];
            if(jsonresult.jointOperation!=null){
                if(jsonresult.jointOperation.list!=null && jsonresult.jointOperation.list.length>0){
                    $(jsonresult.jointOperation.list).each(function(i,dom){
                        if(i<=7){
                            addTo.push("<li>");
                            addTo.push("<a href='#;'>");
                            addTo.push("<div class='list' data-ppID='"+this[1]+"' data-goodsID='"+this[4]+"' data-companyID='"+this[5]+"'>");
                            addTo.push("<img src="+basePath+this[3]+">");
                            addTo.push("<div class='text'>");
                            addTo.push("<h5>"+this[0]+"</h5>");
                            addTo.push("<p>&yen;"+this[2]+"</p>");
                            addTo.push("</div>");
                            addTo.push("</div>");
                            addTo.push("</a>");
                            addTo.push("</li>");
                        }
                    });
                    $("#shoppingMall .activity_mil ul").html(addTo.join(""));
                }else{
                    $("#shoppingMall").hide();
                }
            }else{
                $("#shoppingMall").hide();
            }
            if(jsonresult.press!=null && jsonresult.press.length>0){
                $(jsonresult.press).each(function(i,dom){
                    if(i<=3){
                        news.push("<li>");
                        news.push("<a href='#'>");
                        news.push("<div class='list' data-goodsID='"+this[3]+"' data-ppID='"+this[7]+"'>");
                        var t = "";
                        if(this[2]!=null){
                            t = this[2].split(".")[0]+"small."+this[2].split(".")[1];
                        }
                        news.push("<img src='"+basePath+t+"' alt='' onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'>");
                        news.push("<div class='text'>");
                        news.push("<h5>"+this[0]+"</h5>");
                        news.push("<p>"+this[1]+"</p>");
                        news.push("</div>");
                        news.push("</div>");
                        news.push("</a>");
                        news.push("</li>");
                    }
                });
                $("#news .message_mil ul").html(news.join(""));
            }else{
                $("#news").hide();
            }
        }
    });

};
