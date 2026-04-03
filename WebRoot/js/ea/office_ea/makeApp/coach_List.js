$(document).ready(function() {
    //查询教练员list
    ajax(type);

    //实时监听
    $('.conditions').bind('input propertychange', function() {
        pageNumber = 0;
        $(".coach_list").empty();
        ajax(type);
    });
})



function getHeight() {
    t = setTimeout("getHeight()", 200);
    if ($(".last").length > 0) {
        if ($(".last").offset().top - $(".last").height()<= $(window)
                .height()) {
            if (pageNumber < pageCount) {
                ajax(type);
            }else{
                clearTimeout(t);
            }
        }
    }
}


function ajax(type) {
    var conditions = $(".conditions").val();
    var url = basePath + "/ea/mappointment/sajax_ea_coachOrheadList.jspa";
    $.ajax({
        url : encodeURI(url),
        type : "get",
        data : {
            "ppk.companyID":companyID,
            "pageForm.pageNumber" : pageNumber+1,
            "pageForm.pageSize":pageSize,
            "conditions":conditions,
            "type":type
        },
        dataType : "json",
        async : false,
        success : function(data) {
            var jsonresult = eval("(" + data + ")");
            var pageForm = jsonresult.pageForm;
            var test = [];
            $(".last").removeClass("last");
            if (pageForm != null && pageForm.list != null && pageForm.list.length > 0) {
                pageCount=pageForm.pageCount;
                pageNumber=pageForm.pageNumber;
                $(pageForm.list).each(function(i, dom) {
                    if ($(pageForm.list).length - 1 == i) {
                        test.push("<li class='last' onclick='choose(this)'>");
                    } else {
                        test.push("<li onclick='choose(this)'>");
                    }
                    test.push("<img src='"+basePath+this[1]+"' class='left' onerror=\"this.src=\'' + basePath + '/images/jl.png\'\">");
                    test.push("<div class='text'>");
                    test.push("<h4>"+this[2]+"</h4>");
                    test.push("<p class='tel'>联系方式：<span>"+(this[3]==null?'暂无':this[3])+"</span></p>");
                    test.push("</div>");
                    test.push("<img src='"+basePath+"images/ea/office/ico-gou.png' class='gou' data-staffid='"+this[0]+"'>");
                    test.push("</li>");
                })
            }

            $(".coach_list").append(test.join(""));
            if (pageForm != null) {
                if (pageNumber ==1&&pageNumber<pageCount) {
                    getHeight();
                }
            }
        }
    })
}

function choose(obj) {
    var text = $(obj).find("h4").text();
    var staffid = $(obj).find(".gou").attr("data-staffid");
    document.cookie = "staffid1="+encodeURI(staffid);
    document.cookie = "staffname1="+encodeURI(text);
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