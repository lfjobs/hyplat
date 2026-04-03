$(function(){


    //点看查看名片
    $(document).on("click",".fjmenu li",function(){
        var staffID = $(this).attr("id");
        var sccid = $(this).find(".sccid").val();
        var ccompanyID =$(this).find("ccomID").val();
//        if(ccompanyID==null||ccompanyID==""){
//            document.location.href = basePath+"/ea/perinfor/ea_getPageHomeData.jspa?sccid="+sccid+"&staffId="+staffID+"&from=singlemessage&isappinstalled=0"
//        }else{
//            document.location.href = basePath+"ea/industry/ea_getCompanyHome.jspa?ccompanyId="+ccompanyID+"&industryType=&etype=";
//        }


    })


});


function getHeight1(){

    if($(".last1").length>0){
        if($(".last1").offset().top-$(document).scrollTop()<=$(window).height()){

            if(pagenumber1<pagecount1){
                reLoadFj();
            }else{
                clearInterval(t1);
            }
        }
    }

}



//附近的人
function reLoadFj(){
    pagenumber1 += 1;
    var url = basePath+"ea/sbq/sajax_ea_getFjPeo.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : false,
        data : {
            "pageForm.pageNumber":pagenumber1

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
                pagecount1 = pageForm.pageCount;
                $(".last1").removeClass("last1");
                for ( var i = 0; i <  pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    if(i==pageForm.list.length-1){
                        htmlstr.push("<li class='last1 clearfix' id='"+obj[0]+"'>");
                    }else{
                        htmlstr.push("<li class='clearfix' id='"+obj[0]+"'>");

                    }
                    htmlstr.push("<input type='hidden' class='sccid' value='"+obj[7]+"'>");
                    htmlstr.push("<input type='hidden' class='ccomID' value='"+obj[8]+"'>");
                    htmlstr.push("<span>"+formatDistance(obj[4])+"</span>");
                    if(obj[3]==null||obj[3]==""){
                        htmlstr.push("<img src='"+basePath+"images/ea/driving/elkc/head.png'/>");

                    }else{
                        htmlstr.push("<img src='"+basePath+obj[3]+"'/>");

                    }
                    htmlstr.push("<h3>"+obj[1]+"</h3>");
                    var sex = obj[6];
                    if(obj[6]==null||obj[6]==""){
                        sex="保密";
                    }
                    if(obj[2]==""||obj[2]==null){
                        htmlstr.push("<p>"+obj[6]+"</p>");

                    }else if(obj[2]=="女"){
                        htmlstr.push("<p class='female'><img src='"+basePath+"images/ea/collage/female.png'/>"+sex+"</p>");

                    }else if(obj[2]=="男"){
                        htmlstr.push("<p><img src='"+basePath+"images/ea/collage/male.png'/>"+sex+"</p>");

                    }
                    if(obj[5]==null||obj[5]=="") {
                        htmlstr.push("<p class='txt'>这家伙很懒，什么都没有留下。</p>");
                    }else{
                        htmlstr.push("<p class='txt'>"+obj[5]+"</p>");
                    }
                    htmlstr.push("</li>");
                }
                $(".fjmenu").append(htmlstr.join(""));



                if(pagenumber1==1&&pagecount1>pagenumber1){
                    t1=setInterval("getHeight1()", 200);
                }
            }


        },
        error:function(data){
            console.log("加载下一页失败");
        }
    });

}

//格式化距离
function formatDistance(distance){
    if(distance==null){

        return "";
    }
    if(distance<1){
        distance  = Math.round(Number(distance)*1000)+"m";
    }else{
        distance= Number(distance).toFixed(1)+"km";
    }

   return distance;
}