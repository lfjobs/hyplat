$(function() {
    loaded();

    $(document).on("click",".mkul li",function(){
          var ccompanyID = $(this).attr("id");
          document.location.href = basePath+"/ea/phl/ea_getPhlIndex.jspa?ccompanyID="+ccompanyID;
    })
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


//批发市场
function loaded(){
    pagenumber += 1;
    var url = basePath+"ea/phl/sajax_ea_getAllNmMarket.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : false,
        data : {
            "pageForm.pageNumber":pagenumber

        },
        dataType : "json",
        success : function(data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;

            var htmlstr=new Array();
            var obj;
            if(pageForm!=null){
                pagecount = pageForm.pageCount;
                $(".last").removeClass("last");
                for ( var i = 0; i <  pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    if(i==pageForm.list.length-1){
                        htmlstr.push("<li class='last clearfix' id='"+obj[0]+"'>");
                    }else{
                        htmlstr.push("<li class='clearfix' id='"+obj[0]+"'>");

                    }

             
                    htmlstr.push("<img src='"+basePath+obj[2]+"'   onerror='this.src=\""+ basePath +"/images/ea/production/forum/reportAnError.png\"' />");

                   
                    htmlstr.push("<p>"+obj[1]+"</p>");
                    htmlstr.push("<p>");
                    htmlstr.push("<img src='"+basePath+"images/ea/collage/phl/location.png'/>");
                    htmlstr.push("<span class='txt'>"+(obj[3]==null?'':obj[3])+"</span>");
                    htmlstr.push("<span>"+formatDistance(obj[4])+"</span>");
                    htmlstr.push("</p>");
                    htmlstr.push("</li>");





                }
                $(".mkul").append(htmlstr.join(""));

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
