/**
 * Created by Administrator on 2016/10/24 0024.
 */
$(document).ready(function() {
    /*选择*/
    $(".head_top ul li:nth-child(1) dd").click(function () {
        $(".head_top ul li:nth-child(1) dl dt").toggle(200);
        $(".alert").show();
    });
    $(".head_top ul li:nth-child(1) dl dt").click(function () {
        var txt = $(this).text();
        $(".head_top ul li:nth-child(1) dl dd").text(txt);
        $(".head_top ul li:nth-child(1) dl dt").hide(200);
    });
    $(".alert").click(function () {
        $(".head_top ul li:nth-child(1) dl dt").hide(200);
        $(".alert").hide();
    });
    /*搜索*/
    $(".head_top ul li:nth-child(2) input").click(function () {
        $(".alert_search").show();
        $(".alert_search input:nth-child(1)").focus();
    });
    $(".alert_search input:nth-child(2)").click(function () {
        $(".alert_search").hide();
        var sea_txt = $(".alert_search input:nth-child(1)").val();
        var sea_txt2 = $(".head_top ul li:nth-child(2) input");
        if (sea_txt == "") {
            sea_txt2.val("");
            sea_txt2.addClass("bgt");
            console.log(1)
        } else {
            sea_txt2.val(sea_txt);
            sea_txt2.removeClass("bgt");
            console.log(2)
        }
    });
    $(".alert_search input:nth-child(3)").click(function () {
        $(".alert_search").hide();
        var sea_txt = $(".alert_search input:nth-child(1)").val();
        var sea_txt2 = $(".head_top ul li:nth-child(2) input");
        if (sea_txt == "") {
            sea_txt2.val("");
            sea_txt2.addClass("bgt");
            console.log(1)
        } else {
            sea_txt2.val(sea_txt);
            sea_txt2.removeClass("bgt");
            console.log(2)
        }
    });
    $(".alert_search input:nth-child(1)").keyup(function () {
        var t = $(".alert_search input:nth-child(1)");
        if (t.val() == "") {
            $(".alert_search .top #qx").show();
            $(".alert_search .top #ss").hide();
        }
        else{
            $(".alert_search .top #ss").show();
            $(".alert_search .top #qx").hide();
        }

    });

    //绑定滚动条事件
    $(".con").bind("scroll", function () {
        var sTop = $(".con").scrollTop();
        var sTop = parseInt(sTop);
        var height = $(window).height() * 1;
        /*console.log(sTop);*/
        if (sTop >= height) {
            $("#return").slideDown();
            $("#return").show();
        }
        else {
            $("#return").hide();
        }
    });


    // var num1=num2=num3=0
    window.onload = window.onresize = function(){     
        var clientWidth = document.documentElement.clientWidth;     
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
});