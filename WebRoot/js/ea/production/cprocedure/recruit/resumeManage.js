
$(function () {
    $(".shaix").click(function(){
          document.location.href = basePath+"ea/bidrecruit/ea_getFilterResume.jspa?sccId="+sccId+"&back="+back;

    });

    //简历详情
    $(document).on("click", ".rurl li", function () {
        var resumeID = $(this).attr("id");
        var tpId = $(this).attr("tpid-data");

        document.location.href = basePath+"ea/bidrecruit/ea_resumedetail.jspa?sccId="+sccId+"&resumeID="+resumeID+"&tpId="+tpId+"&back="+back;


    });
    $(window).scroll(function () {
        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度

            var Top = $(".last1").offset().top; //元素距离顶部距离

          //  console.log(Top + '  ' + scroll);
            if (Top - Height - scroll <= 20) {
                if (pageNumber < pageCount) {
                    load();
                }


            }



    })

});


function load() {

    pageNumber = pageNumber + 1;

    var ulp = basePath
        + "ea/bidrecruit/sajax_ea_getTalentResumeList.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: false,
        dataType: "json",
        data: {
            sccId: sccId,
            pageNumber: pageNumber,
            ajax: "ajax"
        },
        success: function (data) {
            var member = eval('(' + data + ')');
            var pageForm = member.pageForm;
            var html = new Array();

            if (pageForm == null) {
                return false;

            }
            pageNumber = pageForm.pageNumber;
            pageCount = pageForm.pageCount;
            $(".last1").remove();
            var obj = "";
            for (var i = 0; i < pageForm.list.length; i++) {
                obj = pageForm.list[i];
                if (i == pageForm.list.length - 1) {

                    html.push("<li class='last1 clearfix' id='" + obj[10] + "'   tpId-data='" + obj[0] + "'>");
                } else {
                    html.push("<li  class='clearfix' id='" + obj[10] + "' tpId-data='" + obj[0] + "' >");
                }

                html.push("<section class='sec-left'>");
                html.push("<h3>");
                html.push(obj[1]);
                if (obj[11] == "00") {
                    html.push("<img class='img-bq' src='" + basePath + "images/resume/ds_17.png'/>");
                }else{
                    html.push("<img class='img-bq' src='" + basePath + "images/resume/ds_18.png'/>");
                }
                html.push("</h3>");
                html.push("<div class='div-01 clearfix'>");
                html.push("<p>" + obj[2] + "</p><span></span>");
                html.push("<p>" + obj[9] + "岁</p><span></span>");
                html.push("<p>工作" + obj[4] + "年</p><span></span>");
                html.push("<p>" + (obj[5]==null?"":obj[5]) + "</p>");

                html.push("</div>");

                html.push("<p class='p-zw'>应聘职位："+obj[3]+"</p>");
                html.push("<p class='p-zw'>投递日期： "+obj[6]+obj[7]+"</p>");
                html.push("</section>");
                html.push("<section class='sec-right'>");
                html.push("<img class='img-tx' src='"+basePath+obj[8]+"' onerror='this.src=\""+ basePath+ "images/ea/driving/elkc/head.png\"'/>");

              //  state;//状态  00:未发送面试通知 /不合适   01：已发送面试通知 /邀请面试 02:被查看 03：有意向
                if(obj[7]=="00"||obj[7]==""){
                    html.push("<img class='img-bq' src='"+basePath+"images/resume/img_37.png'/>");  //待处理
                }else if(obj[7]=="01"){
                    html.push("<img class='img-bq' src='"+basePath+"images/resume/img_38.png'/>");//已邀请

                }else if(obj[7]=="04"){
                    html.push("<img class='img-bq' src='"+basePath+"images/resume/img_36.png'/>");//拒绝

                }else if(obj[7]=="03"){
                    html.push("<img class='img-bq' src='"+basePath+"images/resume/img_48.png'/>");//接受邀请

                }else if(obj[7]=="05"){
                    html.push("<img class='img-bq' src='"+basePath+"images/resume/img_49.png'/>");//拒绝邀请

                }

                 html.push("</section>");
                html.push("</li>");
            }
            $(".rurl").append(html.join(""));


        },
        error: function (data) {

        }
    });


}
function backs(){
    // if(back=="1"){
    //     document.location.href = basePath+"ea/bidrecruit/ea_getResumeIndex.jspa?lei=gr&current=zw";
    //
    // }else {
    //
    //     document.location.href = basePath + "page/ea/main/production/resume/resumeManagement/gscandidates.jsp?sccId=" + sccId+"&back="+back;
    // }


    window.history.go(-1);
    return false;
}

