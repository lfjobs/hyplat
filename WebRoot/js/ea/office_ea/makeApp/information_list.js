$(document).ready(function() {
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

    //查询资讯列表
    ajax();


    //完成
    $(".sure_btn").click(function () {
        var $inp = $(".inp_check");
        var a = [];
        $($inp).each(function(i, dom) {
            if(this.checked){
                a.push($(this).attr("data-ppid"));
            }
        })
        var ppids = a.join(",");

        document.location.href = basePath + "ea/mappointment/ea_checkTheDetails.jspa?ppk.ppID="+ppids+"&sccId="+sccId+"&conditions="+conditions;
    })
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
    var url = basePath + "/ea/mappointment/sajax_ea_ajaxInformationList.jspa";
    $.ajax({
            url : encodeURI(url),
            type : "post",
            data : {
                "pageForm.pageNumber" : pageNumber+1,
                "pageForm.pageSize":pageSize,
                "sccId":sccId
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
                            test.push("<label class='new_box clearfix last'>");
                        } else {
                            test.push("<label class='new_box clearfix'>");
                        }
                        test.push("<img src='"+basePath+this[1]+"' class='new_img' alt=''>");
                        test.push("<div class='new_text'>");
                        test.push("<div class='new_tit'>"+this[2]+"</div>");
                        test.push("<div class='new_state'>");
                        test.push("<span class='new_name'>"+this[3]+"</span>");
                        test.push("<span class='new_time'>"+this[4]+"</span>");
                        test.push("</div></div>");
                        test.push("<input type='checkbox' class='inp_check' name='new' data-ppid='"+this[0]+"'>");
                        test.push("<i></i>");
                        test.push("</label>");
                    })
                    $(".news_list").append(test.join(""));
                }else{
                    test.push("<div class='all_load'>");
                    test.push("已展示全部新闻");
                    test.push("</div> ");
                    $(".news_list").append(test.join(""));
                }


                if (pageForm != null) {
                    pageNumber = pageForm.pageNumber;
                    pageCount = pageForm.pageCount;
                    if (pageNumber < pageCount) {
                        getHeight();
                    }
                }
            }
        })
}
