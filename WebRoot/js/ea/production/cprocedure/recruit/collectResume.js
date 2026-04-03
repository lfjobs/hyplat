 var resumeID = "";
$(function () {
    //简历详情
    $(document).on("click", ".rurl li", function () {
            var resumeID = $(this).attr("id");
            var position = $(this).find(".p-zw").text();
            window.open(basePath+"ea/bidrecruit/ea_showResumedetail.jspa?resumeID="+resumeID+"&position="+position+"&type=抢人才&back=3","_self");



    });

    //取消收藏
    $(document).on("click", ".img-del", function (e) {
        e.preventDefault();
        e.stopPropagation();

        resumeID = $(this).parents("li").attr("id");

         $(".div-del").show();

    });



    //取消
    $(".div-del .p-c").eq(0).click(function () {

        $(this).parents(".div-del").hide();

    })

    //删除确定按钮
    $(".div-del .p-q").click(function () {
        $("li#" + resumeID).remove();

            var ulp = basePath
                + "ea/bidrecruit/sajax_ea_cancelCollectR.jspa";

        $(this).parents(".div-del").hide();

        $.ajax({
            type: "GET",
            url: ulp,
            async: true,
            dataType: "json",
            data: {
                resumeID: resumeID,
                sccId:sccId
            },
            success: function (data) {


            },
            error: function (data) {

            }
        });



    })







        $(window).scroll(function () {
        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度

            var Top = $(".last1").offset().top; //元素距离顶部距离

            console.log(Top + '  ' + scroll);
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
        + "ea/bidrecruit/sajax_ea_getCollectResume.jspa";
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

                    html.push("<li class='last1 clearfix' id='" + obj[0] + "'>"  );
                } else {
                    html.push("<li  class='clearfix' id='" + obj[0] + "'>");
                }
                html.push("<section class='sec-left'>");
                html.push("<img  src='"+basePath+obj[5]+"' onerror='this.src=\""+ basePath+ "images/ea/driving/elkc/head.png\"'/>");
                html.push("<h3> "+ obj[1]+"</h3>");
                html.push("<p class='p-zw'>"+obj[7]+"</p>");
                html.push("<div class='div-01 clearfix'>");
                html.push("<p>" + obj[2] + "</p><span></span>");
                html.push("<p>" + obj[6] + "岁</p><span></span>");
                html.push("<p>工作" + obj[3] + "年</p><span></span>");
                html.push("<p>" + (obj[7]==null?"":obj[7]) + "</p>");

                html.push("</div>");

                html.push("</section>");
                html.push("<section class='sec-right'>");
                html.push("<p>"+obj[8]+"</p>");

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
    // if(back=="1") {
    //     document.location.href = basePath + "ea/bidrecruit/ea_getResumeIndex.jspa?lei=gr&current=zw";
    // }else {
    //     document.location.href = basePath + "page/ea/main/production/resume/resumeManagement/gscandidates.jsp?sccId=" + sccId+"&back="+back;
    // }
    window.history.back();
}

