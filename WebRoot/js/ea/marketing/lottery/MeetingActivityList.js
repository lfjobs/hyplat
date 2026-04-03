/**
 * Created by jcy on 2017/8/04 0024.
 */
$(function() {
    $(document).ready(function(){
        $("header").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;position:fixed;");
        $(".content_hidden").attr("style",";overflow: auto;position: relative;"+"top:"+$(window).height()*0.08+"px");
        $(".content").attr("style",";overflow: auto;");
        $(".content").css("height",$(window).height()*0.92-1+"px");
        $(".china_grd").css("height",$(window).height()*0.92-$(".selection").height()+"px")     ;

        //全类型
        $(".selection ul li").eq(0).click(function(){
            $(".selection ul div").eq(0).show();
            $(".selection ul div").eq(1).css("display","none");
            $(".selection ul div").eq(2).css("display","none");
            $(".selection ul div").toggleClass("yinc");

        });
        $(".selection ul div:nth-child(2) p").click(function(){
            var zhi1=$(this).text();
            $(".selection ul li span").eq(0).text(zhi1);
            $(".selection ul div").addClass("yinc");
        });
        //全时段
        $(".selection ul li").eq(1).click(function(){
            $(".selection ul div").eq(1).show();
            $(".selection ul div").eq(0).css("display","none");
            $(".selection ul div").eq(2).css("display","none");
            $(".selection ul div").toggleClass("yinc");
        });
        $(".selection ul div:nth-child(4) p").click(function(){
            var zhi1=$(this).text();
            $(".selection ul li span").eq(1).text(zhi1);
            $(".selection ul div").addClass("yinc");
        });
        //全价位
        $(".selection ul li").eq(2).click(function(){
            $(".selection ul div").eq(2).show();
            $(".selection ul div").eq(0).css("display","none");
            $(".selection ul div").eq(1).css("display","none");
            $(".selection ul div").toggleClass("yinc");
        });
        $(".selection ul div:nth-child(6) p").click(function(){
            var zhi1=$(this).text();
            $(".selection ul li span").eq(2).text(zhi1);
            $(".selection ul div").addClass("yinc");
        });
        $(document).on("click",".china_mil",function () {
            var activityId = $(this).attr("id");
            window.location.href=basePath+"ea/lottery/ea_selMeetingDetail.jspa?&activityId="+activityId;
        })


    });


    // var num1=num2=num3=0
    window.onload = window.onresize = function(){
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //console.log(clientWidth);
        //通过屏幕宽度去设置不同的后台根字体的大小
        //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
})

$("#meetingList").scroll(function(){
    var lastH = $(".china_mil").last().outerHeight();
    var eh = $(".china_mil").last().offset().top;
    var windowH = window.screen.height;
    if(eh<=(windowH-lastH)){
        loaded()
    }
})

loaded();
function loaded(){
    pagenumber++;
    var url = basePath+"/ea/lottery/sajax_ea_selMeetingPage.jspa?";
    $.ajax({
        url : url,
        type : "post",
        async : false,
        dataType : "json",
        data : {
            "pageNumber":pagenumber
        },
        success : function(data){
            var member = eval("("+data+")");
            var pageForm = member.pageForm;
            var str = new Array();

            if(pageForm != null&&pageForm.recordCount>0)
            {
                var lists = pageForm.list;
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;
                for(var i = 0; i<lists.length;i++)
                {
                    var list = lists[i];
                    str.push('<div class="china_mil" id="'+list[0]+'">');
                    str.push('<a href="#;">');
                    str.push('<img src="' +basePath + list[2] + '" alt="">');
                    str.push('<div class="china_mil_txt">');
                    str.push('<h4>'+list[1]+'</h4>');
                    str.push('</div>');
                    str.push('<div class="clearfix"></div>');
                    str.push('<p>'+list[5]+' <span>'+timeStamp2String(list[3].time)+'</span></p>');
                    str.push('</a>');
                    str.push('</div>');
                }
                $("#meetingList").append(str.join(""));
                //getHeight();
            }
        }
    });
}

//timestamp转换成datetime
function timeStamp2String (time)
{
    var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1;
    var date = datetime.getDate();
    var hour = datetime.getHours();
    var minute = datetime.getMinutes();
    var second = datetime.getSeconds();
    var mseconds = datetime.getMilliseconds();
    //return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second+"."+mseconds;
    return year + "/" + month + "/" + date;
}