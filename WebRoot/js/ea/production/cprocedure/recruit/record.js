var state = "";
$(function () {
    $("body").scroll(function () {
        var Height = $(window).height();
        var scroll = $("body").scrollTop(); //滚动高度

        var Top = $(".last1").offset().top; //元素距离顶部距离

        console.log(Top + '  ' + scroll);
        if (Top - Height - scroll <= 20) {
            if (pageNumber < pageCount) {
                load();
            }

        }


    })
    $("#returnClicks").click(function () {
        if(back=="1"){
            document.location.href = basePath+"ea/bidrecruit/ea_getRecruitIndex.jspa?lei=gs&current=jl";
        }else {
            history.back();
            return false;
        }
    });



    $(".send_menu dd a").click(function () {
        $(this).parents(".send_filt_box").find("a").removeClass("active");
        $(this).addClass("active");
    })

    $(".send_menu a").on("tap", function () {
        var _index = $(".send_menu a").index($(this));
        var $filtclass = $('.' + 'sjob_' + _index + '');
        if (_index == 0) {
            $(".over_lay").hide();
            $(".sjob_rec_box").show();
        } else {
            $(".over_lay").hide();
            $(".sjob_rec_box").hide();
            $filtclass.parent().show();
        }
        $("#valp").html("");

        state = $(this).attr("state-data");
        pageNumber = 0
        pageCount = 0;
        load();

    });


});


function load() {

    pageNumber = pageNumber + 1;

    var ulp = basePath
        + "ea/resumes/sajax_ea_getRecord.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: false,
        dataType: "json",
        data: {
            staffid: staffid,
            pageNumber: pageNumber,
            ajax: "ajax",
            type:type,
            state:state
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

                    html.push(" <a onclick='dianji(this)' class='sjob_rec_box  flex flex_align_center last1' id='"+obj[3]+"'>");
                } else {
                    html.push(" <a onclick='dianji(this)' class='sjob_rec_box  flex flex_align_center' id='"+obj[3]+"'>");
                }

                html.push(" <input type='hidden' id='rcid' value='"+obj[3]+"'>");
                html.push(" <input type='hidden' id='tpId' value='"+obj[8]+"'>");
                html.push("<div class='sjob_L'>");
                html.push("<span id='image'><img src='" + basePath + obj[4] + "' alt=''></span>");
                html.push("</div>");
                html.push("<div class='sjob_m flex_1'>");
                html.push("<p class='sjob_name'>");
                html.push(" <span>" + obj[0] + "</span>");
                html.push("</p>");
                html.push("<p class='sjob_com'>");
                html.push("<span id='companyName'>" + obj[1] + "</span>");
                html.push("</p>");
                html.push("<p class='sjob_area'>");
                html.push("<i class='area_ico'></i><span>" + obj[6] + "</span>");
                html.push("<i class='edu_ico'></i><span>" + obj[7] + "</span>");
                html.push("</p>");
                html.push("</div>");
                if (obj[2] == "05") {
                    html.push(" <input type='hidden' id='vap' value='05'>");
                    html.push("<div style='padding-top: 1.1rem;float: left;padding-right: 0.8rem;'>");
                    html.push("<div class='sjob_r  sjob_1'>");
                    html.push("已拒绝");
                    html.push("</div>");
                    html.push(" <p style='font-size: 0.6rem;line-height: 1rem;height: 1rem;color: #a8a8a8;text-align: center;'>" + obj[5] + "</p>");
                    html.push("</div>");
                } else if (obj[2] == "03") {

                    html.push(" <input type='hidden' id='vap' value='03'>");
                    html.push("<div style='padding-top: 1.1rem;float: left;padding-right: 0.8rem;'>");
                    html.push("<div class='sjob_r  sjob_1'>");
                    html.push("已接受");
                    html.push("</div>");
                    html.push(" <p style='font-size: 0.6rem;line-height: 1rem;height: 1rem;color: #a8a8a8;text-align: center;'>" + obj[5] + "</p>");
                    html.push("</div>");
                } else if (obj[2] == "04") {

                    html.push(" <input type='hidden' id='vap' value='04'>");
                    html.push("<div style='padding-top: 1.1rem;float: left;padding-right: 0.8rem;'>");
                    html.push("<div class='sjob_r  sjob_2'>");
                    html.push("不合适");
                    html.push("</div>");
                    html.push(" <p style='font-size: 0.6rem;line-height: 1rem;height: 1rem;color: #a8a8a8;text-align: center;'>" + obj[5] + "</p>");
                    html.push("</div>");
                } else if (obj[2] == "01") {

                    html.push(" <input type='hidden' id='vap' value='01'>");
                    html.push("<div style='padding-top: 1.1rem;float: left;padding-right: 0.8rem;'>");
                    html.push("<div class='sjob_r  sjob_3'>");
                    html.push("被邀面试");
                    html.push("</div>");
                    html.push(" <p style='font-size: 0.6rem;line-height: 1rem;height: 1rem;color: #a8a8a8;text-align: center;'>" + obj[5] + "</p>");
                    html.push("</div>");
                } else if (obj[2] == "00") {

                    html.push("<input type='hidden' id='vap' value='00'>");
                    html.push("<div style='padding-top: 1.1rem;float: left;padding-right: 0.8rem;'>");
                    html.push("<div class='sjob_r  sjob_4'>");
                    html.push("已投递");
                    html.push("</div>");
                    html.push(" <p style='font-size: 0.6rem;line-height: 1rem;height: 1rem;color: #a8a8a8;text-align: center;'> "+ obj[5] +"</p>");
                    html.push("</div>");
                }
                html.push("</a>");
            }

            $("#valp").append(html.join(""));
        },error: function (data) {

        }
    });


}


function dianji(obj) {

    var tpId = $(obj).find("#tpId").val();

    var url = basePath + "ea/resumes/ea_getDetails.jspa?tpId=" + tpId+"&staffid="+staffid+"&type="+type+"&back="+back;
    document.location.href = url;
}
