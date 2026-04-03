$(function() {
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

//批发市场
function loaded(){
    var url = document.location.toString();
    var arrUrl = url.split("=");
    var ccompanyID = arrUrl[1];
    pagenumber += 1;

    var url = basePath+"ea/phljoin/sajax_ea_getPhlCarList.jspa?marketId="+ccompanyID;
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
                        htmlstr.push("<li class='last clearfix'>");
                    }else{
                        htmlstr.push("<li class='clearfix'>");
                    }
                    htmlstr.push("<img src='"+basePath+obj[0]+"'/>");
                    /*if(obj[0]==null||obj[0]==""){
                        htmlstr.push("<img src='"+basePath+"images/ea/collage/phl/img_06.png'/>");

                    }else{
                        htmlstr.push("<img src='"+basePath+obj[0]+"'/>");
                    }*/
                    /*for(var j=0;j<obj.length;j++){
                        if(obj[j]==null){
                            obj[j]="----";
                        }
                    }*/
                    htmlstr.push("<menu>");
                    htmlstr.push("<li>车主："+obj[1]+"</li>");
                    htmlstr.push("<li>车牌号："+obj[2]+"</li>");
                    htmlstr.push("<li>载重："+obj[3]+"公斤</li>");
                    htmlstr.push("<li>载重体积："+obj[4]+"方</li>");
                    htmlstr.push("</menu>");
                    htmlstr.push("<menu>");
                    htmlstr.push("<li>车型："+obj[5]+"</li>");
                    htmlstr.push("<li>长宽高："+obj[6]+"</li>");
                    htmlstr.push("<li>市场："+obj[7]+"</li>");
                    htmlstr.push("</menu>");
                }
                $("#carul").append(htmlstr.join(""));

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

