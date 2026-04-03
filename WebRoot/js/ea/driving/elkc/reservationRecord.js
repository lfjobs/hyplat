$(document).ready(function() {

    loaded ();

    //取消
    $(".cancel_popup .c_p_btn").click(function () {
        var etoId = $(this).attr("data-etoId");
        var url = basePath + "/ea/elkcRecord/sajax_ea_ajaxCancellation.jspa?";
        http = $.ajax({
            url : url,
            type : "post",
            async : true,
            dataType : "json",
            data:{
                "tbElycOrderRecord.etoId":etoId
            },
            success : function (data) {
                window.location.reload();
            },
            error:function(data){
                alert("取消失败");
            }
        });
    })


    //预约详情
    $(document).on("click",".state_ready",function() {
        var etoId = $(this).attr("data-etoId");
        document.location.href = basePath + "/ea/elkcRecord/ea_bookingDetails.jspa?tbElycOrderRecord.etoId="+etoId;
    })

    $(document).on("click",".rec_learning",function() {
        var subject = $(this).parents(".state_ready").attr("date-subject");
        var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        var card = $(this).attr("data-studentnum");
        if(isAndroid==true){
            Android.changetoJiShiLianChe(card,subject);
        }else if(isiOS==true){
            var url= "func=" + 'jslc';
            params={'card':card,'subject':subject};
            for(var i in params){
                url = url + "&" + i + "=" + params[i];
            }
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    })





})

function getHeight(){
    height = parseInt(Math.abs($(".rec_wrap").height()-($(window).height()-$(".rec_wrap").offset().top)));
    t=setTimeout("getHeight()", 200);
    if(height<$(window).height()){
        if(pagenumber<pagecount){
            loaded();
        }
    }
}
function loaded () {
    clearTimeout(t);
    http.abort();
    pagenumber += 1;
    var url = basePath + "/ea/elkcRecord/sajax_ea_ajaxReservationRecord.jspa?";
    http = $.ajax({
        url : url,
        type : "post",
        async : true,
        dataType : "json",
        data:{
            "pageForm.pageNumber":pagenumber,
            "pageForm.pageSize":8,
            "tbElycOrderRecord.studentId":studentId
        },
        success : function (data) {
            var record = eval("(" + data + ")");
            var pageForm = record.pageForm;
            var temporary = new Array();
            if(pageForm != null && pageForm.list != null){
                var list=pageForm.list;
                pagecount=pageForm.pageCount;
                pagenumber=pageForm.pageNumber;
                $(list).each(function(i, dom) {
                    temporary.push("<a href='javascript:void(0)' class='rec_box clearfix state_ready' data-etoId='"+this[0]+"' date-subject='"+this[3]+"'>");
                    temporary.push("<img src='"+basePath+(this[1]==null?"images/contacts/Network/defaultbig.png":this[1])+"' class='rec_img' alt=''>");
                    temporary.push("<div class='rec_T'>");
                    temporary.push("<div class='rec_name'>");
                    var sb;
                    if (this[3]=="1"){
                        sb = "科目一"
                    }else if (this[3]=="2"){
                        sb = "科目二"
                    }else if (this[3]=="3"){
                        sb = "科目三"
                    }else if (this[3]=="4"){
                        sb = "科目四"
                    }
                    temporary.push("<span>"+(this[2]==null?"":this[2])+"</span><i>"+sb+"</i>");
                    temporary.push("</div>");
                    var myDate = new Date();
                    var beginDate=new Date(this[4].replace("-", "/").replace("-", "/"));
                    var endDate=new Date(this[5].replace("-", "/").replace("-", "/"));
                    var difference = parseInt(beginDate - myDate) / 1000 / 60 / 60;
                    var diffmss = parseInt(endDate - myDate) / 1000 / 60 / 60;
                    if (this[8]=="1"){
                        if (difference<this[6]){
                            temporary.push("<div class='ready_time' data-startTime='"+this[4]+"' data-endTime='"+this[5]+"'>");
                            temporary.push("开始倒计时");
                            temporary.push("<span class='days'></span>天<span class='hours'></span>时<span class='minutes'></span>分<span class='seconds'></span>秒");
                            temporary.push("</div>");
                        }else{
                            temporary.push("<div class='ready_display'>");
                            temporary.push(""+this[4].substring(0,this[4].length-3)+"—"+this[5].substring(this[5].length-8,this[5].length-3)+"");
                            temporary.push("</div>");
                        }
                    }else{
                        temporary.push("<div class='ready_display'>");
                        temporary.push(""+this[4].substring(0,this[4].length-3)+"—"+this[5].substring(this[5].length-8,this[5].length-3)+"");
                        temporary.push("</div>");
                    }
                    temporary.push("<div class='rec_site'>"+(this[7]==null?"未设置":this[7])+"</div>");
                    temporary.push("</div>");
                    if (this[8]=="1"){
                        if(difference>0){
                            if (difference<this[6]){
                                temporary.push("<div class='rec_btn rec_ready'>即将开始</div>");
                            }else{
                                temporary.push("<div class='rec_btn rec_cancel' data-companyname='"+this[9]+"'>取消</div>");
                            }
                        }else{
                            if (diffmss>0){
                                temporary.push("<div class='rec_btn rec_learning' data-studentnum='"+this[10]+"'>开始学习</div>");
                            }else{
                                temporary.push("<div class='rec_btn rec_ready'>超时关闭</div>");
                            }
                        }

                    }else if (this[8]=="2"){
                        temporary.push("<div class='rec_btn rec_no'>已取消</div>");
                    }else if (this[8]=="3"){
                        temporary.push("<div class='rec_btn rec_no'>学习中</div>");
                    }else if (this[8]=="4"){
                        temporary.push("<div class='rec_btn rec_no' onclick='aaa(this)' data-studentId='"+this[11]+"' data-teacherId='"+this[12]+"'>待评价</div>");
                    }else if (this[8]=="5"){
                        temporary.push("<div class='rec_btn rec_no'>已评价</div>");
                    }else if (this[8]=="6"){
                        temporary.push("<div class='rec_btn rec_no'>未出勤</div>");
                    }
                    temporary.push("</a>");
                })
            }
            $(".rec_wrap").append(temporary.join(""));
            getHeight();
        },
        error:function(data){
            alert("获取失败");
        }
    });
}

function aaa(obj) {
    var studentId = $(obj).attr("data-studentId");
    var teacherId = $(obj).attr("data-teacherId");
    document.location.href = basePath + "/ea/student/ea_addApprise.jspa?studentId="+studentId+"&teacherId="+teacherId;
}