$(document).ready(function() {

    loaded ();
    //取消
    $(document).on("click",".det_cancel",function() {
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



    $(document).on("click",".det_learning",function() {
        var subject = $(".o_det_wrap").attr("date-subject");
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

function loaded () {
    var url = basePath + "/ea/elkcRecord/sajax_ea_ajaxDetails.jspa?";
    http = $.ajax({
        url : url,
        type : "post",
        async : true,
        dataType : "json",
        data:{
            "tbElycOrderRecord.etoId":etoId
        },
        success : function (data) {
            var details = eval("(" + data + ")");
            var obj = details.object;
            var temporary = new Array();
            temporary.push("<div class='o_det_wrap' date-subject='"+obj[3]+"'>");
            temporary.push("<ul class='o_det_box'>");
            temporary.push("<li>预约驾校："+(obj[9]==null?"":obj[9])+"</li>");
            temporary.push("<li>驾校地址："+(obj[7]==null?"":obj[7])+"</li>");
            temporary.push("</ul>");
            temporary.push("<ul class='o_det_box'>");
            var sb;
            if (obj[3]=="1"){
                sb = "科目一"
            }else if (obj[3]=="2"){
                sb = "科目二"
            }else if (obj[3]=="3"){
                sb = "科目三"
            }else if (obj[3]=="4"){
                sb = "科目四"
            }
            temporary.push("<li>预约科目："+sb+"</li>");
            temporary.push("<li>预约时间："+obj[4].substring(0,16)+" — "+obj[5].substring(10,16)+"</li>");
            temporary.push("</ul>");
            temporary.push("<ul class='o_det_box'>");
            temporary.push("<li>教学教练："+(obj[2]==null?"":obj[2])+"</li>");
            temporary.push("<li>联系电话："+(obj[10]==null?"":obj[10])+"</li>");
            temporary.push("</ul>");
            temporary.push("</div>");
            if (obj[8]=="1"){
                var mydate = new Date();
                var hold = new Date(obj[4].replace("-", "/").replace("-", "/"));
                var end = new Date(obj[5].replace("-", "/").replace("-", "/"));
                var t = hold.getTime() - mydate.getTime();
                var s = obj[6]*60*60*1000;
                temporary.push("<div class='o_det_state'>");
                if (hold>mydate){
                    if (t>s){
                        temporary.push("<!--取消 开始-->");
                        temporary.push("<div class='o_det_hint'>");
                        temporary.push("<div class='hint_tit'>预约提示</div>");
                        temporary.push("<p>"+obj[4].substring(0,16)+" — "+obj[5].substring(10,16)+"，您在"+obj[9]+"预约练车。");
                        temporary.push("还有"+millisecondToDate(t-s)+"预约将不能取消。");
                        temporary.push("距离练车还有"+millisecondToDate(t)+"，请按时到达。");
                        temporary.push("逾期预约无效，谢谢您的配合。</p>");
                        temporary.push("</div>");
                        temporary.push("<a href='javascript:void(0);' class='det_cancel'>取消</a>");
                        temporary.push("<!--取消 结束-->");
                    }else{
                        temporary.push("<!--即将开始状态  开始-->");
                        temporary.push("<div class='o_det_hint'>");
                        temporary.push("<div class='hint_tit'>即将开始</div>");
                        temporary.push("</div>");
                        temporary.push("<!--即将开始状态  结束-->");
                    }
                }else if (mydate>hold && mydate<end){
                    temporary.push("<!--开始学习 开始-->");
                    temporary.push("<a href='javascript:void(0);' class='det_learning' data-studentnum='"+obj[11]+"'>开始学习</a>");
                    temporary.push("<!--开始学习 结束-->");
                }else if (end<mydate){
                    temporary.push("<!--超时关闭状态  开始-->");
                    temporary.push("<div class='o_det_hint'>");
                    temporary.push("<div class='hint_tit'>超时关闭</div>");
                    temporary.push("</div>");
                    temporary.push("<!--超时关闭状态  结束-->");
                }
                temporary.push("</div>");
            }else if (obj[8]=="2"){
                temporary.push("<!--已取消状态  开始-->");
                temporary.push("<div class='o_det_hint'>");
                temporary.push("<div class='hint_tit'>已取消</div>");
                temporary.push("</div>");
                temporary.push("<!--已取消状态  结束-->");
            }else if (obj[8]=="3"){
                temporary.push("<!--学习中状态  开始-->");
                temporary.push("<div class='o_det_hint'>");
                temporary.push("<div class='hint_tit'>学习中</div>");
                temporary.push("</div>");
                temporary.push("<!--学习中状态  结束-->");
            }else if (obj[8]=="4"){
                temporary.push("<!--待评价状态  开始-->");
                temporary.push("<div class='o_det_hint'>");
                temporary.push("<div class='hint_tit' onclick='aaa(this)' data-studentId='"+obj[12]+"' data-teacherId='"+obj[13]+"' data-etoId='"+etoId+"'>立即评价</div>");
                temporary.push("</div>");
                temporary.push("<!--待评价状态  结束-->");
            }else if (obj[8]=="5"){
                temporary.push("<!--已评价状态  开始-->");
                temporary.push("<div class='o_det_hint'>");
                temporary.push("<div class='hint_tit'>已评价</div>");
                temporary.push("</div>");
                temporary.push("<!--已评价状态  结束-->");
            }else if (obj[8]=="6"){
                temporary.push("<!--超时关闭状态  开始-->");
                temporary.push("<div class='o_det_hint'>");
                temporary.push("<div class='hint_tit'>超时关闭</div>");
                temporary.push("</div>");
                temporary.push("<!--超时关闭状态  结束-->");
            }
            $(".wrap_page").append(temporary.join(""));
        },
        error:function(data){
            alert("获取失败");
        }
    });
}

function millisecondToDate(msd) {
    var time = parseFloat(msd) /1000;
    if (null!= time &&""!= time) {
        if (time >60&& time <60*60) {
            time = parseInt(time /60.0) +"分钟"+ parseInt((parseFloat(time /60.0) -
                    parseInt(time /60.0)) *60) +"秒";
        }else if (time >=60*60&& time <60*60*24) {
            time = parseInt(time /3600.0) +"小时"+ parseInt((parseFloat(time /3600.0) -
                    parseInt(time /3600.0)) *60) +"分钟"+
                parseInt((parseFloat((parseFloat(time /3600.0) - parseInt(time /3600.0)) *60) -
                    parseInt((parseFloat(time /3600.0) - parseInt(time /3600.0)) *60)) *60) +"秒";
        }else {
            time = parseInt(time/60/60/24) +"天";
        }
    }else{
        time = "0 时 0 分0 秒";
    }
    return time;

}

function aaa(obj) {
    var studentId = $(obj).attr("data-studentId");
    var teacherId = $(obj).attr("data-teacherId");
    var etoId = $(obj).attr("data-etoId");
    document.location.href = basePath + "/ea/student/ea_addApprise.jspa?studentId="+studentId+"&teacherId="+teacherId+"&etoId="+etoId;
}