/**
 * Created by Administrator on 2016/3/21.
 */
$(function(){
    $(".nav_main_img").click(function(){
        $(".nav").toggleClass("on")
    })
    $(".nav_main ul li").click(function(){
        $(".nav_main ul li").removeClass("on")
        $(this).addClass("on")
    })
    $(".drive_zheng .zheng_top").css("height",$(this).width()*0.35+"px")
    $(".drive_zheng .zheng_bot").css("height",$(this).width()*0.35+"px")
    $(".drive_zheng .zheng_b").css({"height":$(".drive_zheng").height()*0.06+"px","line-height":$(".drive_zheng").height()*0.06+"px"})
    $(".drive_top_d").css("height",$(".drive_top").height()+"px")
    $(".ziliao .ziliao_top .ziliao_left").css({"height":$(this).width()*0.32+"px"})

    $(".drive_main_nav ul li").click(function(){
        $(".drive_main_nav ul li").removeClass("on")
        $(this).addClass("on")
    })

   /* $(".drive_main_lis_right_img").css("line-height",$(".drive_main_lis").height()+"px")
    $(".jiaolian_lis").css("line-height",$(".drive_main_lis").height()+"px")*/

})