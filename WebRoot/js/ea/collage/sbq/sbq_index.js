$(function(){
    //tab标签
    $(".content").hide();
    $(".content").eq(0).show();
    $("header h2 div").click(function(){
        $(this).parent("h2").find("div").removeClass("active");
        $(this).addClass("active");
        $(".content").hide();
        $(".content").eq($(this).index()).show();
       var active = $(".head .active").text();
        if(active=="附近"){
            if($(".fjmenu").children().length==0) {
                reLoadFj();
            }
        }else if(active=="发现"){
            if($(".fxmenu").children().length==0) {
                reLoadFind();
            }
        }

    });
    //轮播功能绑定
    $(".content:first-of-type menu li").each(function(){
        $(this).find(".click_slick").attr('id',"txt"+($(this).index()+1));
    });

    //红色点赞图片路径
    var src1=basePath+"images/ea/collage/fabulous_01.png";
    //灰色点赞图片路径
    var src2=basePath+"images/ea/collage/fabulous.png";
    //点赞更换图片
    $(document).on("click",".fabulous",function(event){
        var ppid = $(this).parents("li").attr("id");
        var goodsid = $(this).parents("li").find(".goodsid").val();


        event.stopPropagation();
        if($(this).find("img").attr("src")==src2){
            $(this).find("img").attr("src",src1);
            $(this).find("img").addClass("animated pulse");
            $(this).find("span").text(Number($(this).find("span").text())+1);

        }else{
            $(this).find("img").attr("src",src2);
            $(this).find("img").removeClass("animated pulse");
            $(this).find("span").text(Number($(this).find("span").text())-1);

        }
        var url = basePath+"ea/sbq/sajax_ea_dzOpr.jspa";
        $.ajax({
            url : url,
            type : "get",
            async : false,
            data : {
                "productPackaging.ppID":ppid,
                "productPackaging,goodsID":goodsid

            },
            dataType : "json",
            success:function(data){
                var m = eval("("+data+")");
                var login = m.login;
                if(login=="login"){
                    document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
                    return;
                }
            },
            error:function(data){
                consoleLog("点赞失败");
            }
        });
    })

    //点击查看详情
    $(document).on("click",".mitem",function(){
        var ppId = $(this).attr("id");
        var ccompanyId = null;
       if($(this).find(".cc").val().indexOf("contact")!=-1){
         ccompanyId = $(this).find(".cc").val();
       }
        document.location.href = basePath+"ea/industry/ea_informationDetails.jspa?ppId="+ppId+"&ccompanyId="+ccompanyId+"&type=time&miniSystemJudge=03";

    })

    //发动态
    $(".dynamic").click(function(){
        var url = basePath+"ea/sbq/sajax_ea_sendDynamic.jspa";
        $.ajax({
            url : url,
            type : "get",
            async : false,
            dataType : "json",
            success:function(data){
                var m = eval("("+data+")");
                var login = m.login;
                if(login=="login"){
                    document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
                    return;
                }else{
                    var sccid = m.sccid;
                    document.location.href = basePath+"/ea/qrshare/ea_qrshareEdit.jspa?skipJudge=00&miniSystemJudge=03&productPackaging.sccid="+sccid+"&ccomIDPlatform="+ccomIDPlatform;

                }
            },
            error:function(data){
                consoleLog("点赞失败");
            }
        });

    });



    loaded();
});

function getHeight(){

    if($(".last").length>0){
        if($(".last").offset().top-$(document).scrollTop()<=$(window).height()){

            if(pagenumber<pagecount){
                loaded();
            }else{
                clearInterval(t);
                }
            }
        }

}


//商帮圈动态
function loaded(){
    pagenumber += 1;
    var url = basePath+"ea/sbq/sajax_ea_getAllInfo.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : false,
        data : {
            "pageForm.pageNumber":pagenumber,
            ccomIDPlatform:ccomIDPlatform

        },
        dataType : "json",
        success : function(data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            var piclist   = member.piclist;
            var dz = member.dz;

            var htmlstr=new Array();
            var obj;
            if(pageForm!=null){
                pagecount = pageForm.pageCount;
               $(".last").removeClass("last");
                for ( var i = 0; i <  pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    if(i==pageForm.list.length-1){
                        htmlstr.push("<li class='last mitem' id='"+obj[3]+"'>");
                    }else{
                        htmlstr.push("<li class='mitem' id='"+obj[3]+"'>");

                    }
                    htmlstr.push("<input type='hidden' class='goodsid' value='"+obj[2]+"'>");
                    htmlstr.push("<input type='hidden' class='cc' value='"+obj[4]+"'>");
                    htmlstr.push("<section>");
                    htmlstr.push("<div class='clearfix'>");
                    if(obj[6]==null||obj[6]==""){
                        htmlstr.push("<img src='"+basePath+"images/ea/driving/elkc/head.png'/>");

                    }else{
                        htmlstr.push("<img src='"+basePath+obj[6]+"'/>");

                    }
                    htmlstr.push("<h3>"+obj[5]+"</h3>");
                    htmlstr.push("<p><span>"+getDateDiff(obj[1])+"</span></p>");
                    htmlstr.push("</div>");
                    htmlstr.push("<p>"+obj[0]+"</p>");
                    htmlstr.push("</section>");


                    htmlstr.push("<ul class='click_slick clearfix'>");
                    for(var k = 0;k<piclist.length;k++) {
                        if(piclist[k][1]==obj[2]) {
                            htmlstr.push("<li>");
                            htmlstr.push("<a>");
                            htmlstr.push("<img src='" + basePath + piclist[k][0]+"'/>");
                            htmlstr.push("</a>");
                            htmlstr.push("</li>");
                        }
                    }
                    htmlstr.push("</ul>");

                    htmlstr.push("<div>");
                    htmlstr.push("<a class='fabulous'>");
                    if(dz[obj[3]]==undefined) {
                        htmlstr.push("<img src='" + basePath + "images/ea/collage/fabulous.png'/>");
                    }else{
                        htmlstr.push("<img src='" + basePath + "images/ea/collage/fabulous_01.png'/>");

                    }

                    htmlstr.push("<span>"+obj[7]+"</span>");
                    htmlstr.push("</a>");
                    htmlstr.push("<a>");
                    htmlstr.push("<img src='"+basePath+"images/ea/collage/comment.png'/>");
                    htmlstr.push("<span>"+obj[8]+"</span>");
                    htmlstr.push("</a>");
                    htmlstr.push("</div>");
                    htmlstr.push("</li>");




                }
              $(".tjmenu").append(htmlstr.join(""));

                if(pagenumber==1&&pagecount>pagenumber){
                    t=setInterval("getHeight()", 200);
                }
            }


        },
        error:function(data){
           console.log("加载下一页失败");
        }
    });

}


//格式化日期
function getDateDiff(dateStr){
    var minute = 1000 * 60;
    var hour = minute * 60;
    var day = hour * 24;
    var halfamonth = day * 15;
    var month = day * 30;
    var now = new Date().getTime();
    var diffValue = now - getDateTimeStamp(dateStr);
    if(diffValue < 0){
    
    	return  "刚刚";

    }
    var monthC =diffValue/month;
    var weekC =diffValue/(7*day);
    var dayC =diffValue/day;
    var hourC =diffValue/hour;
    var minC =diffValue/minute;
    if(monthC>=1){
        result= dateStr;
    }
    else if(weekC>=1){
        result= dateStr;
    }
    else if(dayC>=1){
        result=""+ parseInt(dayC) +"天前";
    }
    else if(hourC>=1){
        result=""+ parseInt(hourC) +"小时前";
    }
    else if(minC>=1){
        result=""+ parseInt(minC) +"分钟前";
    }else
        result="刚刚";
    return result;
}
function getDateTimeStamp(dateStr){
    return Date.parse(dateStr.replace(/-/gi,"/"));
}


