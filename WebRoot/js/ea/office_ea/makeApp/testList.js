$(document).ready(function() {
    //查询场地list
    ajax();
    delCookie();
    //实时监听
    $('.conditions').bind('input propertychange', function() {
        pageNumber = 0;
        $(".coach_list").empty();
        ajax();
    });
})



function getHeight() {
    t = setTimeout("getHeight()", 200);
    if ($(".last").length > 0) {
        if ($(".last").offset().top - $(".last").height() <= $(window)
                .height()) {
            if (pageNumber < pageCount) {
                ajax();
            }
        }
    }
}


function ajax() {
    var conditions = $(".conditions").val();
    var url = basePath + "/ea/mappointment/sajax_ea_testList.jspa";
    $.ajax({
            url : encodeURI(url),
            type : "post",
            data : {
                "ppk.companyID":companyID,
                "pageForm.pageNumber" : pageNumber+1,
                "pageForm.pageSize":pageSize,
                "conditions":conditions
            },
            dataType : "json",
            async : false,
            success : function(data) {
                var jsonresult = eval("(" + data + ")");
                var pageForm = jsonresult.pageForm;
                var test = [];
                $(".last").removeClass("last");
                if (pageForm != null && pageForm.list != null && pageForm.list.length > 0) {
                    $(pageForm.list).each(function(i, dom) {
                        if ($(pageForm.list).length - 1 == i) {
                            test.push("<li class='last' onclick='choose(this)'>");
                        } else {
                            test.push("<li onclick='choose(this)'>");
                        }
                        test.push("<img src='"+basePath+this[1]+"' class='left' onerror=\"this.src=\'' + basePath + '/images/cd.png\'\">");
                        test.push("<div class='text'>");
                        test.push("<h4>"+this[2]+"</h4>");
                        test.push("<p>￥<span>"+this[6]+"</span>/小时</p>")
                        test.push("<p class='tel'><img src='"+basePath+"images/ea/office/ico-locat.png'>"+this[3]+"</p>");
                        test.push("</div>");
                        test.push("<img src='"+basePath+"images/ea/office/ico-gou.png' class='gou' data-erid='"+this[0]+"' data-price='"+this[6]+"' data-ppid='"+this[7]+"' data-companyid='"+this[8]+"'>");
                        test.push("</li>");
                    })
                }

                $(".coach_list").append(test.join(""));
                /*if (pageForm != null) {
                    pageNumber = pageForm.pageNumber;
                    pageCount = pageForm.pageCount;
                    if (pageNumber < pageCount) {*/
                        getHeight();
               /*     }
                }*/
            }
        })
}

function choose(obj) {
    var text = $(obj).find("h4").text();
    var erid = $(obj).find(".gou").attr("data-erid");
    var price = $(obj).find(".gou").attr("data-price");
    var ppid=$(obj).find(".gou").attr("data-ppid");
    var companyid=$(obj).find(".gou").attr("data-companyid");
    document.cookie = "erid="+encodeURI(erid);
    document.cookie = "erName="+encodeURI(text);
    document.cookie = "price="+encodeURI(price);
    document.cookie="ppid="+encodeURI(ppid);
    document.cookie="companyid="+encodeURI(companyid);
    document.location.href = basePath + "ea/mappointment/ea_jumpToBuy.jspa?companyId="+companyID;

}
function delCookie()
{
    var myDate=new Date();
    myDate.setTime(-10000);//设置时间
    var data=document.cookie;
    var dataArray=data.split("; ");
    for(var i=0;i<dataArray.length;i++){
        var varName=dataArray[i].split("=");
        document.cookie=varName[0]+"=''; expires="+myDate.toGMTString()+"; path=/ea/mappointment";
    }
}