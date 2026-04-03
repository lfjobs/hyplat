$(document).ready(function() {
    ajax();
    $(".order_btn").click(function () {
        if(companyId!=null && companyId!=""){
            if(ppID!=null && ppID!=""){
                document.location.href = basePath+"/ea/mappointment/ea_jumpToBuy.jspa?ppk.ppID="+ppID+"&companyId="+companyId;
            }else{
                alert("该驾校暂时未开放考车");
            }
        }else{


            document.location.href = basePath+"/ea/mappointment/ea_queryTheTest.jspa";
        }
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
    var url = basePath + "/ea/mappointment/sajax_ea_myRecord.jspa";
    $.ajax({
        url : encodeURI(url),
        type : "post",
        data : {
            "sccId":sccId,
            "companyId":companyId,
            "pageForm.pageNumber" : pageNumber+1,
            "pageForm.pageSize":pageSize,
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
                        test.push("<div class='exam_rec_box last' onclick='details(this)' data-cbid='"+this[9]+"' data-sccid='"+this[8]+"'>");
                    } else {
                        test.push("<div class='exam_rec_box' onclick='details(this)' data-cbid='"+this[9]+"' data-sccid='"+this[8]+"'>");
                    }
                    test.push("<div class='exam_info_h'>");
                    test.push("<img src='"+basePath+this[0]+"' class='exam_headimg' alt='' onerror=\"this.src=\'' + basePath + '/images/boy.png\'\">");
                    test.push("<span>"+this[1]+"</span>");
                    test.push("<span class='exam_info_time'>"+this[2]+"</span>");
                    test.push("</div>");
                    test.push("<div class='exam_time'>");
                    test.push("<div class='exam_time_h'>");
                    test.push("<i class='begin_time'>始</i>");
                    if(this[3]==null){
                        test.push("<span>未签到</span>");
                    }else{
                        test.push("<span>"+this[3]+"</span>");
                    }
                    test.push("</div>");
                    test.push("<div class='exam_time_h'>");
                    test.push("<i class='end_time'>终</i>");
                    if(this[4]==null){
                        test.push("<span>未签退</span>");
                    }else{
                        test.push("<span>"+this[4]+"</span>");
                    }
                    test.push("</div>");
                    var a;
                    if(this[5]=="00"){
                        if(this[2]==getNowFormatDate()){
                            a="未签到";
                        }else{
                            a="已失效";
                        }
                    }else if(this[5]=="01" || this[5]=="03"){
                        a="已签到";
                    }else if(this[5]=="02"){
                        a="已签退";
                    }else if(this[5]=="04"){
                        a="待支付";
                    }
                    test.push("<div class='seam_state finished'>"+a+"</div>");
                    test.push("</div>");
                    test.push("</div>");
                })
                $(".exam_rec_con").append(test.join(""));
            }else{
                $(".exam_rec_con").addClass("no_thing");
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





function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
    return currentdate;
}

function details(obj) {
    document.location.href = basePath+"/ea/mappointment/ea_bookingDetails.jspa?cbId="+$(obj).attr("data-cbid")+"&sccId="+$(obj).attr("data-sccid");
}

function back() {
    try {
        if (companyId != null && companyId != "") {
            window.history.go(-1);
            return false;
        } else {
        	
        	if(sc=="web"){
        		window.history.go(-1);
                return false;
        	}else{
                 var u = window.navigator.userAgent;
                 var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; // android终端
                  var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
                  if (isAndroid == true) {
                         console.log("安卓");
                     Android.callAndroidjianli();// 调用安卓接口
                   } else if (isiOS == true) {
                     console.log("IOS");
                     var url = "func=" + 'doneClose';
     				window.webkit.messageHandlers.Native.postMessage(url);
                }
        	}
        }
    }catch(error){
        window.history.go(-1);
        return false;

    }
}





