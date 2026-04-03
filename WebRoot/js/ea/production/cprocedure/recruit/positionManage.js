var status = "01";
var pageNumber2 = 0;
var pageCount2 = 0;
var cz = 0;
$(function () {
    $(window).scroll(function () {
        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度
        var index = $(".sec-nav .active").index();
        if (index == 0) {
            var Top = $(".last1").offset().top; //元素距离顶部距离

         //   console.log(Top + '  ' + scroll);
            if (Top - Height - scroll <= 20) {
                if (pageNumber1 < pageCount1) {
                    load();
                }


            }

        } else {
            var Top = $(".last2").offset().top; //元素距离顶部距离

           // console.log(Top + '  ' + scroll);
            if (Top - Height - scroll <= 20) {
                if (pageNumber2 < pageCount2) {
                    load();
                }


            }
        }


    })
    //查看
    $(document).on("click", ".ul-con li", function () {
        var riId = $(this).attr("id");

        document.location.href = basePath+"ea/bidrecruit/ea_getViewRecruit.jspa?sccId="+sccId+"&recruitInfo.riId="+riId;

    })

    //修改
    $(document).on("click", ".p-edit", function (e) {
        var riId = $(this).parents("li").attr("id");
        e.preventDefault();
        e.stopPropagation();

        document.location.href = basePath+"ea/bidrecruit/ea_getPositionPub.jspa?sccId="+sccId+"&recruitInfo.riId="+riId;

    })

    //删除弹框
    $(document).on("click", ".p-del", function (e) {
        e.preventDefault();
        e.stopPropagation();
        cz = 0;
        riId = $(this).parents("li").attr("id");
        $(".div-del").show();
        $(".titlep").text("确定要删除吗?");
    })
    // 上线下线
    $(document).on("click", ".p-onoff", function (e) {
        e.preventDefault();
        e.stopPropagation();
        cz = 1;
        $(".titlep").text("确定要"+$(this).text()+"吗?");
         riId = $(this).parents("li").attr("id");
        $(".div-del").show();

    })

    //取消
    $(".div-del .p-c").eq(0).click(function () {

        $(this).parents(".div-del").hide();

    })

    //删除确定按钮
    $(".div-del .p-q").click(function () {
        $("li#" + riId).remove();


       if(cz==0) {
           var ulp = basePath
               + "ea/bidrecruit/sajax_ea_deleteRecruitInfo.jspa";
       }else{
           var ulp = basePath
               + "ea/bidrecruit/sajax_ea_onOfflineRecruit.jspa?sccId="+sccId;
       }
           $(this).parents(".div-del").hide();

           $.ajax({
               type: "GET",
               url: ulp,
               async: true,
               dataType: "json",
               data: {
                   "recruitInfo.riId": riId
               },
               success: function (data) {


               },
               error: function (data) {

               }
           });



    })
    //tab
    $(".sec-nav li").click(function () {
        $(this).parents(".sec-nav").find("li").removeClass("active");
        $(this).addClass("active");
        $(".content>div").hide();
        $(".content>div").eq($(this).index()).show();
        if ($(this).index() == 0) {
            pageNumber1 = 0;
            pageCount1 = 0;
            $(".div-tab1 .ul-con").html("")
            status = "01";
        } else {
            pageNumber2 = 0;
            pageCount2 = 0;
            $(".div-tab2 .ul-con").html("")
            status = "00";
        }
        load();
    })

    //立即发布

    $(".publish").click(function(){

       document.location.href = basePath+"ea/bidrecruit/ea_getPositionPub.jspa?sccId="+sccId+"&back="+back+"&pst=1";


    });



});


function load() {

    if (status == "01") {
        pageNumber = pageNumber1 + 1;

    } else {
        pageNumber = pageNumber2 + 1;

    }

    var ulp = basePath
        + "ea/bidrecruit/sajax_ea_findPositionList.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: false,
        dataType: "json",
        data: {
            sccId: sccId,
            status: status,
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
            if (status == "01") {
                pageNumber1 = pageForm.pageNumber;
                pageCount1 = pageForm.pageCount;
                $(".last1").remove();
            } else {
                pageNumber2 = pageForm.pageNumber;
                pageCount2 = pageForm.pageCount;
                $(".last2").remove();
            }

            var obj = "";
            for (var i = 0; i < pageForm.list.length; i++) {
                obj = pageForm.list[i];
                if (i == pageForm.list.length - 1) {

                    if (status == "01") {
                        html.push("<li class='last1' id='" + obj.riId + "'>");
                    } else {
                        html.push("<li class='last2' id='" + obj.riId + "'>");
                    }
                } else {
                    html.push("<li id='" + obj.riId + "'>");
                }

                html.push("<h3>" + obj.jobTitle + "</h3>");
                html.push("<div class='div-01 clearfix'>");
                html.push("<p>" + obj.salary + "</p><span></span>");
                html.push("<p>" + obj.education + "</p><span></span>");
                html.push("<p>" + obj.workYears + "</p>");
                html.push("</div>");

                if (status == "01") {
                    html.push(" <p class='p-xm'>发布人员：" + obj.staffName + "<span style='float:right;'>发布日期：" + obj.publishDate + "</span></p>");
                } else {
                    html.push(" <p class='p-xm'>下线人员：" + obj.offlineName + "<span style='float:right;'>下线日期：" + obj.offlineDate + "</span></p>");
                }

                html.push("<section class='clearfix'>");
                html.push("<p class='p-del'>删除</p><p class='p-edit'>修改</p>");
                if (status == "01") {
                    html.push("<p class='p-onoff'>下线</p>");
                } else {
                    html.push("<p class='p-onoff'>上线</p>");

                }


                html.push("</section>");
                html.push("</li>");
            }
            if (status == "01") {
                $(".div-tab1 .ul-con").append(html.join(""));
            } else {
                $(".div-tab2 .ul-con").append(html.join(""));
            }


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

    window.history.go(-1);
    return false;
    }

