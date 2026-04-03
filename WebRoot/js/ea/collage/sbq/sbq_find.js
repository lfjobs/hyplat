$(function(){

    //点看查看网站
    $(document).on("click",".fxmenu li",function(){
        var ccompanyID = $(this).attr("id");

        document.location.href = basePath+"ea/industry/ea_getCompanyHome.jspa?ccompanyId="+ccompanyID+"&industryType=&etype=";



    });

    //关注轮换
    $(document).on("click",".fxmenu span",function(event){
        event.stopPropagation();
        if($(this).is(".active")){
            $(this).removeClass("active")
            $(this).text("关注")
        }else{
            $(this).addClass("active")
            $(this).text("已关注")
        }
        var ccomIDPlatform = $(this).parents("li").attr("id");
        var url = basePath+"ea/sbq/sajax_ea_followPlatForm.jspa";
        $.ajax({
            url : url,
            type : "get",
            async : false,
            data : {
                ccomIDPlatform:ccomIDPlatform,

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
                consoleLog("关注失败");
            }
        });


    })

});


function getHeight2(){

    if($(".last2").length>0){
        if($(".last2").offset().top-$(document).scrollTop()<=$(window).height()){

            if(pagenumber2<pagecount2){
                reLoadFind();
            }else{
                clearInterval(t2);
            }
        }
    }

}



//发现
function reLoadFind(){
    pagenumber2 += 1;
    var url = basePath+"ea/sbq/sajax_ea_allPlatForm.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : false,
        data : {
            "pageForm.pageNumber":pagenumber2

        },
        dataType : "json",
        success : function(data) {
            var member = eval("(" + data + ")");
            var login = member.login;
            if(login=="login"){
                document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
                return;
            }
            var pageForm = member.pageForm;
            var htmlstr=new Array();
            var obj;
            if(pageForm!=null){
                pagecount2= pageForm.pageCount;
                $(".last2").removeClass("last2");
                for ( var i = 0; i <  pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    if(i==pageForm.list.length-1){
                        htmlstr.push("<li class='last2 clearfix' id='"+obj[2]+"'>");
                    }else{
                        htmlstr.push("<li class='clearfix' id='"+obj[2]+"'>");

                    }
                    if(obj[1]==null||obj[1]==""){
                        htmlstr.push("<img src='"+basePath+"images/ea/driving/elkc/head.png'/>");

                    }else{
                        htmlstr.push("<img src='"+basePath+obj[1]+"'/>");

                    }
                    htmlstr.push("<p class='txt'>"+obj[0]+"</p>");
                    if(obj[6]==null||obj[6]==""){
                      htmlstr.push("<span>关注</span>");
                    }else{
                        htmlstr.push("<span class='active'>已关注</span>");
                    }
                    htmlstr.push("<li>");

                }
                $(".fxmenu").append(htmlstr.join(""));



                if(pagenumber2==1&&pagecount2>pagenumber2){
                    t2=setInterval("getHeight2()", 200);
                }
            }


        },
        error:function(data){
            console.log("加载下一页失败");
        }
    });

}
