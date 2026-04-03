var pageNumber = 0;
var pageCount = 0;
var  ob = "";
$(function () {
    ajac(ob);
    //点击导航切换样式以及下边的显示部分
    $(".inv_nav a").tap(function () {
        var _index = $(".inv_nav a").index($(this));
        $(this).addClass("inv_navcur").siblings("a").removeClass();
        $(".inv_con").eq(_index).show().siblings().hide();
    })
    //后退
    $("#returnClick").click(function () {
        history.go(-1)
    });

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


function ajac(ob) {
    pageNumber = pageNumber + 1;

    var staffid = $("#staffid").val();
    var url = basePath + "ea/resumes/sajax_ea_getRecord.jspa?type=ac&resumeName=" + ob + "&staffid=" + staffid + "&sccId=" + sccId + "&jitype=" + jitype;
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: false,
        dataType: "json",
        success: function (data) {
            var member = eval("(" + data + ")");
            var page = member.listobj;
            var str = "";

            pageNumber = pageForm.pageNumber;
            pageCount = pageForm.pageCount;
            $(".last1").remove();
            for (var i = 0; i < page.length; i++) {
                var obj = page[i];
                if (obj[5].hours < 10) {
                    obj[5].hours = "0" + obj[5].hours;
                }
                if (obj[5].minutes < 10) {
                    obj[5].minutes = "0" + obj[5].minutes;
                }
                var time = obj[5].hours + ":" + obj[5].minutes;
                if (obj[5].month < 9) {
                    if (obj[5].date < 10) {
                        var date = (obj[5].year + 1900) + "-0" + (obj[5].month + 1) + "-0" + obj[5].date;
                    } else {
                        var date = (obj[5].year + 1900) + "-0" + (obj[5].month + 1) + "-" + obj[5].date;
                    }
                } else {
                    var date = (obj[5].year + 1900) + "-" + (obj[5].month + 1) + "-" + obj[5].date;
                }

                if (i == pageForm.list.length - 1) {
                    str += "<a  class='last1 sjob_rec_box flex flex_align_center' id='"+obj[0]+"'>";
                } else {
                    str += "<a  class='last1 sjob_rec_box flex flex_align_center' id='"+obj[0]+"'>";
                }
                str += " <div class='sjob_L'>";
                if (obj[4] == null) {
                    str += "<img src='" + basePath + "page/ea/main/production/resume/resumeManagement/images/sjob_01.png' > </div>";
                }
                str += "<img src=" + basePath + obj[4] + " > </div>";
                str += "<div class='sjob_m flex_1'>";
                str += "<p class='sjob_name'>";
                str += "<span>" + obj[0] + "</span>";
                str += " </p><p class='sjob_com'>";
                str += "<span>" + obj[1] + "</span></p>";
                str += " <p class='sjob_area'>";
                str += " <span>" + (ob == '03' ? "投递时间：" : "面试时间：") + " " + date + "   " + time + "</span>";

                str += " </p> </div></a>";
            }


             $("#div0").append(str);

        },
        error: function (data) {
        }
    });


}